# A0003 — Precisão com Espadas

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência específica identificada:** nenhuma nesta auditoria documental.
- **Notion:** https://app.notion.com/p/3c569db9f0db816bab5bcc64e7081fe7
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0003
- **Nome:** Precisão com Espadas
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Espadas
- **Ramo:** Crítico e Precisão
- **Camada:** 2
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0001 Treino com Espadas I ≥ 1 rank.
- **Pré-requisitos:** A0001 Treino com Espadas I.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — pipeline crítico canônico.
- **Efeito:** +3% de chance de crítico com espadas por rank, até +9%.
- **Escalonamento:** até 3 ranks.
- **Gate:** Gateway `epic_sword` acessível + A0001 ≥ 1 rank; gateway da Árvore Exterior.
- **Hook:** classificação de espada + pipeline canônico de chance de crítico.
- **Fallback:** usar a chance crítica canônica somente em ataques diretos de espada; ignorar fontes não classificadas.
- **Regra:** chance específica para espadas; nunca criar segunda rolagem quando provider/RPG Skill Tree já resolveu o crítico. Uma ação elegível produz no máximo uma resolução crítica canônica.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** Exige A0001 e gateway correto.
2. **Integração global — PASS.** Reutiliza o pipeline crítico canônico e evita duplicação com outros sistemas.
3. **Qualidade/identidade — PASS.** É uma especialização ofensiva de precisão da disciplina, com identidade preservada pelo pipeline crítico específico de espada.
4. **Topologia — PASS.** Camada 2 e dependência de A0001 formam bifurcação coerente em relação ao ramo de ritmo.
5. **Especializações — PASS.** É ramo exterior, não classe automática derivada do provider.
6. **PT-BR — PASS.** Nome/efeito em português.
7. **Notion completo — PASS.** Campos necessários preenchidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS.** Epic Fight + pipeline universal de crítico são os sistemas pertinentes; integrações futuras devem compartilhar a mesma resolução.

## Contrato técnico esperado

- Bônus: `0,03 × rank(A0003)` de chance crítica elegível para espada.
- Uma ação ofensiva deve ter um único `rootActionId` para resolver crítico uma vez.
- Resultado do provider pode ser preservado; o RPG Skill Tree só acrescenta chance sem criar uma segunda resolução concorrente.
- Apenas ataques diretos classificados como espada são elegíveis.
- Server-authoritative, com deduplicação entre NeoForge e Epic Fight.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.criticalChanceBonus(...)` mapeia `WeaponFamily.SWORD -> A0003` e calcula +3% por rank.
- `A0001A0020CriticalService` fornece resolução crítica canônica/deduplicada.
- `A0001A0020EpicFightHooks.onCriticalHit(...)` resolve o evento NeoForge e registra a raiz crítica recente.
- `onDamagePre(...)` reutiliza/correlaciona a resolução no pipeline Epic Fight para evitar uma segunda rolagem.
- Existem `A0001A0020CriticalServiceTest`, `A0001A0020NotionContractTest` e testes de policy.

## Pendências técnicas

Nenhuma divergência específica encontrada. Qualquer novo adapter de arma/mod deve obrigatoriamente integrar-se ao mesmo serviço crítico em vez de lançar sua própria rolagem.

## Testes obrigatórios

- [x] coeficiente por rank no ruleset;
- [x] serviço crítico dedicado;
- [x] correlação NeoForge ↔ Epic Fight presente;
- [x] testes unitários do serviço crítico existentes;
- [ ] revalidar integração quando versão do Epic Fight ou pipeline crítico global mudar.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; chance, gate, hook, fallback e regra persistem sem divergência.
- **Mutação no Notion neste ciclo:** não necessária.
- **Pipeline canônico:** crítico permanece resolvido uma única vez por ação/root; callbacks NeoForge e Epic Fight devem compartilhar a mesma decisão.
- **Integrações:** atributos ou mods que possuam conceitos próprios de crítico não autorizam segunda rolagem MARTIAL. Crítico mágico/específico de outro provider não é fundido aqui sem adapter explícito e deduplicado.
- **Fail-closed:** fontes não classificadas como espada pelo provider não recebem o bônus.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010.

## Chat 2 — implementação, testes e merge — PR #221

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #221.

- [x] Pipeline crítico canônico único implementado.
- [x] Deduplicação/correlação NeoForge ↔ Epic Fight implementadas.
- [x] Gate/ranks/família provider-native preservados.
- [x] FAIL-CLOSED para fontes sem classificação inequívoca.
- [x] Nenhuma segunda rolagem crítica criada.
- [x] Testes do serviço crítico e policy presentes.
- [x] `RPG Skill Tree CI` #1996 verde no HEAD `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma no provider/versionamento atual.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — 2026-08-30

- **RPG Skill Tree:** `COBERTA POR PERK EXISTENTE`; authority do crítico é `A0001A0020CriticalService`/root action canônica. Um evento pode produzir no máximo uma decisão crítica.
- **Volcanoes:** `NÃO DEVE SER INTEGRADO`; pressão, O₂, gases, geologia, erupções e hazards não viram chance crítica marcial.
- **Enshrouded:** `NÃO DEVE SER INTEGRADO`; Shroud/Exposure/Flame/Story e `MagicResistanceService` não alimentam a rolagem crítica de espada.
- **Black Arcana:** boundary legítimo de exclusão. `ARCANE_BACKLASH` é terminal, não entra no resolver crítico, não crita e não concede Mastery/proc. Ataque direto do jogador contra entidade Black Arcana continua elegível como ação Epic Fight.
- **Mobstein 5.4.4:** alvo Mobstein é coberto pelo combate universal quando o jogador ataca diretamente. Dano de ally/bodyguard ressuscitado é Mobstein-owned, não entra no `rootActionId` crítico do dono e não herda A0003.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos nesta retroauditoria para tornar essas exclusions implementáveis sem transformar Black Arcana/Mobstein em providers da perk; re-fetch 2026-08-30 confirmou persistência.
- **Fail-closed:** origem terminal/secundária/companion ou fonte sem `SWORD` inequívoca fica inelegível; não há bridge substituta.
- **Estado histórico:** implementação da PR #221 já mergeada; retroauditoria não altera runtime.

## Reauditoria delta Simply Swords — 2026-08-30

- **Cobertura:** arma Simply só entra quando Epic Fight Compat resolve `SWORD` para o root direto do jogador.
- **Crítico:** Katana double damage, Warglaive double strike, execute, ability hit, Implicit e gem power não criam uma segunda resolução crítica MARTIAL.
- **Scaling:** helpers de Awakening/gem já escalados pelo provider não recebem scaling adicional do RPG.
- **Integrated/Cataclysm:** material/trait não muda autoria nem cria novo root.
- **Simply Tooltips:** `NÃO DEVE SER INTEGRADO`.
- **Simply More alpha:** Unique não comprovada permanece FAIL-CLOSED.
- **Notion:** provider/hook/fallback/regra corrigidos e re-fetch PASS.
- **Runtime:** inalterado; Chat 2 deve provar deduplicação provider-present.

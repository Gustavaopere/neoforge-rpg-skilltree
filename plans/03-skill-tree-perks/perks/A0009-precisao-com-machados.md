# A0009 — Precisão com Machados

## Status e proveniência

- **Design:** APROVADO/FECHADO.
- **Código relevante em `main`:** PRESENTE.
- **Pendência específica identificada:** nenhuma nesta auditoria documental.
- **Notion:** https://app.notion.com/p/3c569db9f0db813384c2f55b4c22b533
- **Critérios de aprovação:** https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567
- **Referência técnica auditada:** `main@54658e6f51d1862a267fdb26e4146466228b18cb`.

## Especificação canônica do Notion

- **Código:** A0009
- **Nome:** Precisão com Machados
- **Domínio:** MARTIAL
- **Árvore:** Epic Fight — Machados
- **Ramo:** Fúria e Pressão
- **Camada:** 2
- **Função na Árvore:** Ramo
- **Tier:** Pequeno
- **Faixa de Poder:** Médio
- **Ranks Máx.:** 3
- **Custo por Rank:** 1
- **Dependências Obrigatórias:** A0007 Treino com Machados I ≥ 1 rank.
- **Pré-requisitos:** A0007 Treino com Machados I.
- **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — pipeline crítico canônico.
- **Efeito:** +3% de chance de crítico com machados por rank, até +9%.
- **Escalonamento:** até 3 ranks.
- **Gate:** Gateway `epic_axe` acessível + A0007 ≥ 1 rank; gateway da Árvore Exterior.
- **Hook:** chance de crítico em ataques diretos com categoria de arma machado.
- **Fallback:** usar o pipeline crítico canônico apenas em ataques diretos classificados como machado.
- **Regra:** chance específica para machados; não criar segunda rolagem quando provider/RPG Skill Tree já resolveu o crítico. Uma ação elegível produz no máximo uma resolução crítica canônica.

## Auditoria obrigatória — 9 eixos

1. **Dependências/gates — PASS.** A0007 ≥ 1 rank e gateway correto são explícitos.
2. **Integração global — PASS.** Compartilha o pipeline crítico canônico e impede rolagens paralelas.
3. **Qualidade/identidade — PASS.** Especializa o ramo ofensivo de machados e alimenta a progressão de Fúria/Pressão posterior.
4. **Topologia — PASS.** Camada 2 após o treinamento basal abre o ramo Fúria e Pressão de forma coerente.
5. **Especializações — PASS.** Permanece ramo exterior, não classe derivada do mod.
6. **PT-BR — PASS.** Nome e efeito de jogador em português.
7. **Notion completo — PASS.** Campos necessários preenchidos.
8. **NeoVitae — PASS.** Ausente.
9. **Cobertura modlist — PASS.** Epic Fight e o serviço crítico global são os sistemas pertinentes; novos adapters devem reutilizar a mesma resolução.

## Contrato técnico esperado

- Bônus: `0,03 × rank(A0009)` de chance crítica elegível para machado.
- Apenas ataques diretos classificados como machado.
- Uma ação ofensiva deve possuir uma única resolução crítica canônica.
- Preservar resultado crítico do provider e correlacionar callbacks sem segunda rolagem.
- Server-authoritative e deduplicado por `rootActionId`.

## Evidência encontrada na `main`

- `NotionCombatPerkRules.criticalChanceBonus(...)` mapeia `WeaponFamily.AXE -> A0009` e calcula +3% por rank.
- `A0001A0020CriticalService` fornece resolução crítica canônica.
- `A0001A0020EpicFightHooks.onCriticalHit(...)` resolve o estágio NeoForge e registra a raiz recente.
- `onDamagePre(...)` reutiliza a correlação no pipeline Epic Fight e passa a chance específica da família.
- Existem testes de serviço crítico, contrato e policy para A0001–A0020.

## Pendências técnicas

Nenhuma divergência específica identificada. Qualquer futura integração de arma externa classificada como machado deve entrar no mesmo serviço crítico, nunca lançar uma rolagem própria.

## Testes obrigatórios

- [x] coeficiente por rank no ruleset;
- [x] serviço crítico dedicado;
- [x] correlação NeoForge ↔ Epic Fight presente;
- [x] testes unitários do serviço crítico existentes;
- [ ] revalidar quando versão do Epic Fight ou pipeline crítico global mudar.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; dependência, chance, gate, hook, fallback e regra permanecem coerentes.
- **Mutação no Notion neste ciclo:** não necessária.
- **Pipeline canônico:** uma ação de machado pode produzir no máximo uma resolução crítica; callbacks de providers diferentes devem correlacionar a mesma raiz.
- **Integrações:** crítico mágico ou crítico próprio de addons não vira automaticamente crítico MARTIAL. Novo adapter deve entrar no serviço canônico e provar deduplicação.
- **Fail-closed:** ataques/fontes sem classificação inequívoca de machado não recebem A0009.
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

**Pendências técnicas:** nenhuma.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — 2026-08-30

- **RPG Skill Tree:** `COBERTA POR PERK EXISTENTE`; `A0001A0020CriticalService`/root action permanece authority do crítico e da deduplicação.
- **Volcanoes:** `NÃO DEVE SER INTEGRADO`; hazards, pressão, gases, geologia e vulcanismo não geram chance crítica marcial.
- **Enshrouded:** `NÃO DEVE SER INTEGRADO`; Shroud/Exposure/Flame/Story e o reducer mágico de mobs não alimentam crítico de machado.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e fica fora do resolver crítico/Mastery/proc. Ataque direto do jogador contra entidade Black Arcana continua elegível como ação Epic Fight.
- **Mobstein 5.4.4:** ataque direto do jogador contra mob/boss é coberto universalmente; dano de ally/bodyguard ressuscitado permanece Mobstein-owned e não entra no `rootActionId` crítico do dono.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos nesta retroauditoria; re-fetch confirmou persistência.
- **Fail-closed:** origem terminal/secundária/companion ou fonte sem `AXE` inequívoca fica inelegível; não há segunda rolagem/fallback genérico.
- **Estado histórico:** implementação da #221 já mergeada; sem alteração runtime.

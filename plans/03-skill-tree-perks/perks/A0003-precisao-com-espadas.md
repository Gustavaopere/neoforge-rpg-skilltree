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

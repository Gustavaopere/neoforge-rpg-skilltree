# A0101 — Fortificação contra Projéteis

**Estado Chat 1:** DESIGN APROVADO  
**Runtime atual:** consumer ainda ausente; availability deve permanecer fail-closed até implementação do Chat 2.  
**Notion:** https://app.notion.com/p/3c569db9f0db8131ba86dbd530a50b0e

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY.
- Ramo: Mitigação por Tipo — Projéteis.
- Camada: 2.
- Função: Ramo.
- Ranks: 4; custo 1 PP/rank.
- Pré-requisito: A0089 Couro Endurecido ≥ 1 rank + Gateway VITALITY.

## Contrato congelado

Cada rank concede **2% de redução**, até **8%**, somente a dano cuja mesma fonte/root seja explicitamente classificada como `delivery=PROJECTILE` e `nature=PHYSICAL`. Projétil mágico não entra em A0101 apenas por ser projétil. Fontes compostas só recebem A0101 no componente/`DamageSource` que satisfaça ambas as classificações.

A0101 não altera Armor/Toughness, não cria cap defensivo global, não infere natureza pela arma equipada, modelo da entidade, velocidade, namespace ou animação.

## Provider, hook e authority

Owner do boundary: Minecraft/NeoForge 1.21.1, build atual NeoForge `21.1.248`. Consumer: RPG Skill Tree `DamageMitigationResolver` em `LivingDamageEvent.Pre`, uma contribuição por `DamageContainer`/root.

Epic Fight `21.17.3.1` pode alimentar a classificação apenas quando a fonte integrada chegar ao mesmo pipeline causal ou adapter versionado explícito. Presença de uma entidade projectile não é receipt suficiente.

## Causalidade, deduplicação e anti-abuso

- Um `DamageContainer`/root recebe no máximo uma contribuição A0101.
- Classificação deve ser calculada antes da contribuição; adapters não podem duplicar o mesmo root.
- Dano mágico, ambiental não físico, self/resource-cost e fonte desconhecida não são promovidos por heurística.
- A perk não produz proc, Mastery, crítico ou nova autoria.

## Availability e fail-closed

Enquanto o consumer A0101 não estiver implementado, o node deve ficar **indisponível/não comprável**. Nenhum gasto de PP pode produzir rank no-op. Fonte modded sem mapping seguro fica inelegível, não recebe fallback genérico.

## Projetos próprios / cobertura provider → árvore

- Volcanoes: hazards não entram por analogia; apenas DamageType explicitamente físico+projétil poderia ser mapeado no futuro.
- Enshrouded: client audio/particles e Shroud não fornecem classification authority para A0101.
- Black Arcana: Backlash/custos arcanos permanecem fora; forecast de Arcane Resistance é read-only.
- RPG Skill Tree: Stage 04/Progression continua authority de aquisição; A0101 apenas consome o pipeline de mitigação.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS, prerequisites explícitos e server-authoritative.
2. Integração global — PASS, um único resolver defensivo.
3. Qualidade/identidade — PASS, proteção balística física distinta de magia/Armor.
4. Topologia — PASS, ramo VITALITY coerente.
5. Especializações — PASS/N/A; não cria grant paralelo.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco 2026-08-31.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS com unknown fail-closed.

Causalidade, dedup, anti-abuso, fallback, authority, versões, lifecycle e purchase fail-closed estão definidos; nenhum bônus genérico substitui adapter ausente.

## Pendências para Chat 2

- `P-A0101-01`: implementar classifier `PROJECTILE + PHYSICAL` e consumer no `DamageMitigationResolver`, uma vez/root.
- `P-A0101-02`: materializar availability server-authoritative; sem consumer, purchase deve falhar antes do gasto.
- `P-A0101-03`: testes positivos/negativos para projétil físico, projétil mágico, fonte desconhecida, dedup e composição com A0092/A0096.

## Testes exigidos ao Chat 3

Validar provider-present/absent, uma aplicação por root, ordem do pipeline, fonte mágica inelegível, projectile modded desconhecido fail-closed, rank/respec/rules reload, multiplayer isolation, NeoForge GameTests, build, JAR e dedicated-server smoke.

## Atualização de implementação — Chat 2 (2026-09-02)

**Estado:** `CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3`.

- `P-A0101-01` e `P-A0101-02` foram resolvidas no runtime: A0101 contribui no `DamageMitigationResolver` somente quando a mesma fonte satisfaz `rpgskilltree:physical` e `DamageTypeTags.IS_PROJECTILE`.
- A contribuição é `0,02 × rank`, no mesmo `LivingDamageEvent.Pre` usado pelos demais reducers RPG; não foi criado segundo pipeline.
- Projétil mágico sem classificação física, fontes desconhecidas e rotas modded sem mapping seguro continuam fail-closed por classificação.
- `P-A0101-03` permanece como validação do Chat 3. Chat 2 não executou a bateria final, não declarou `IMPLEMENTAÇÃO CONFIRMADA` e não fez merge.

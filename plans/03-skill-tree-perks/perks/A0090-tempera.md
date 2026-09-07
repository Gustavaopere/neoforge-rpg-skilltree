# A0090 — Têmpera

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-8120-a929-fafebea41235`; fetch fresco PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Estado Chat 3:** **IMPLEMENTAÇÃO CONFIRMADA / AGUARDANDO MERGE DA PR #372**.

## Contrato canônico

- Gateway VITALITY + A0089 Couro Endurecido ≥2.
- 5 ranks: +2% relativo de Armor Toughness por rank, máximo +10%.
- Owner canônico: Minecraft/NeoForge `Attributes.ARMOR_TOUGHNESS`.
- Se toughness elegível for 0, bônus continua 0; a perk não cria toughness flat.
- Não reduz dano verdadeiro/não mitigável, não altera durabilidade/NBT e não toca `ARMOR`, `STUN_ARMOR` ou Resistência Física.

## Implementação Chat 2 — estado confirmado em código

- binding data-driven existente publica `rpgskilltree:node/combat/a0090/armor_toughness` em `minecraft:generic.armor_toughness`;
- operação `MULTIPLY_TOTAL` +0,02/rank preserva zero toughness→zero bônus;
- a dependência A0089≥2 continua resolvida pelo graph/requirements server-authoritative, sem gate paralelo;
- `AttributeNodeEffectRuntime` reaplica por effectId estável, evitando modifier órfão/duplicado;
- não existe código A0090 alterando `ARMOR`, `STUN_ARMOR`, Resistência Física, durabilidade, NBT ou damage sources não mitigáveis.

## Checklist Chat 2

- [x] Binding `ARMOR_TOUGHNESS` presente
- [x] +2% relativo/rank presente
- [x] zero toughness→zero bonus preservado
- [x] Dependência A0089≥2 preservada no graph
- [x] Modifier idempotente por effectId presente
- [x] Sem ARMOR/STUN_ARMOR/Resistência Física/NBT/durabilidade
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** dependência A0089≥2 em purchase/rank loss
- [ ] **VALIDAÇÃO CHAT 3:** composição com equipment/Apothic/outros modifiers
- [ ] **VALIDAÇÃO CHAT 3:** sources não mitigáveis permanecem inalteradas
- [ ] **VALIDAÇÃO CHAT 3:** respec/relog/respawn/reload/idempotência
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/GameTests aplicáveis
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | VITALITY + A0089≥2. |
| Integração global | PASS | usa toughness vanilla, sem segunda fórmula. |
| Qualidade/identidade | PASS | aprofundamento de armor existente. |
| Topologia | PASS | VITALITY/ARMOR, Camada 2. |
| Especializações | PASS | PP apenas quando semanticamente mapeado. |
| PT-BR | PASS | Têmpera/tenacidade consistentes. |
| Notion | PASS sem mudança | fetch fresco. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | composição nativa; adapters futuros somente explícitos. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.

## Validação Chat 3 — 2026-09-07

- `A0081A0100CombatPolicyTest` confirmou multiplicador relativo de A0090 em 1,10 no rank 5.
- O GameTest canônico de atributos, com `ServerPlayer` real, confirmou `MULTIPLY_TOTAL` em apply/reapply/remove/reapply sem stacking ou modifier órfão.
- A dependência A0089≥2 permanece no graph/requirements server-authoritative; o lote não criou gate paralelo nem bypass de purchase/rank loss.
- Toughness base zero permanece zero, e revisão do diff confirmou ausência de mutação em ARMOR, `STUN_ARMOR`, Resistência Física, NBT/durabilidade ou damage sources não mitigáveis.
- Baseline de validação `4502d1d253863f6f25e13e6a7284a0d53a3b0fd5`: RPG Skill Tree CI `34147843664` SUCCESS; Sonar `34147843581` SUCCESS; CodeQL `34147843620` SUCCESS.
- **Resultado:** implementação confirmada no owner canônico `Attributes.ARMOR_TOUGHNESS`, com dependência A0089≥2 e composição nativa de modifiers.

### Checklist final Chat 3

- [x] Design aprovado e código presente
- [x] Binding `ARMOR_TOUGHNESS` / +2% por rank confirmado
- [x] Dependência A0089≥2 preservada
- [x] zero base→zero bônus confirmado
- [x] composição/idempotência de modifiers confirmadas
- [x] Sem ARMOR/`STUN_ARMOR`/Resistência Física/NBT/durabilidade
- [x] Anti-abuso/Mastery: N/A — perk não concede Mastery nem recurso
- [x] Testes unitários/NeoForge JUnit e GameTests verdes
- [x] Build NeoForge e dedicated-server smoke verdes
- [x] SonarQube e CodeQL verdes no baseline funcional
- [x] **IMPLEMENTAÇÃO CONFIRMADA**

O HEAD documental final deve ser revalidado em CI antes do merge da PR #372.
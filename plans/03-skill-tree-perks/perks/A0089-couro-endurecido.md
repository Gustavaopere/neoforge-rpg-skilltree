# A0089 — Couro Endurecido

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-810d-a0d7-e8926be6ae33`; fetch fresco PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Estado Chat 3:** **IMPLEMENTAÇÃO CONFIRMADA / AGUARDANDO MERGE DA PR #372**.

## Contrato canônico

- Gateway VITALITY; 5 ranks, +2% relativo de armadura efetiva por rank, máximo +10%.
- Owner canônico: Minecraft/NeoForge `Attributes.ARMOR`.
- Se armor elegível for 0, bônus percentual permanece 0; A0089 não cria proteção flat.
- Não altera NBT, durabilidade, reparo, item tier, `STUN_ARMOR` do Epic Fight nem Resistência Física do RPG.

## Implementação Chat 2 — estado confirmado em código

- binding data-driven existente publica `rpgskilltree:node/combat/a0089/armor` em `minecraft:generic.armor`;
- operação `MULTIPLY_TOTAL` +0,02/rank preserva zero armor→zero bônus;
- `AttributeNodeEffectRuntime` remove o effectId anterior antes de reaplicar, evitando stacking/órfãos;
- nenhum código deste lote liga A0089 a `STUN_ARMOR`, Resistência Física, NBT, durabilidade, afixos ou sockets;
- providers externos continuam compondo pela pilha vanilla de `ARMOR`.

## Checklist Chat 2

- [x] Binding `ARMOR` presente
- [x] +2% relativo/rank presente
- [x] zero armor→zero bonus preservado
- [x] Modifier idempotente por effectId presente
- [x] Sem `STUN_ARMOR`/Resistência Física/NBT/durabilidade
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** composição com equipment/Apothic/outros modifiers
- [ ] **VALIDAÇÃO CHAT 3:** sources que ignoram armor não são alteradas artificialmente
- [ ] **VALIDAÇÃO CHAT 3:** rank loss/respec/relog/respawn sem modifier órfão
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/GameTests aplicáveis
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Gateway VITALITY. |
| Integração global | PASS | atributo vanilla é ponto único de composição. |
| Qualidade/identidade | PASS | melhora armor existente sem gerar heavy armor grátis. |
| Topologia | PASS | VITALITY/ARMOR, Camada 1. |
| Especializações | PASS | fundamento só conta por mapeamento explícito. |
| PT-BR | PASS | texto canônico. |
| Notion | PASS sem mudança | fetch fresco. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | terceiros compõem nativamente; sem duplicação. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.

## Validação Chat 3 — 2026-09-07

- `A0081A0100CombatPolicyTest` confirmou o multiplicador relativo de A0089 em 1,10 no rank 5.
- O GameTest canônico de atributos, com `ServerPlayer` real, confirmou `MULTIPLY_TOTAL` em apply/reapply/remove/reapply sem stacking ou modifier órfão.
- A semântica relativa preserva armor base zero→bônus zero; o runtime não cria valor flat nem segundo pipeline de Resistência Física.
- Revisão do diff confirmou ausência de escrita em NBT/durabilidade, `STUN_ARMOR`, afixos, sockets ou damage-source bypass; fontes que ignoram armor continuam regidas pelo pipeline vanilla/provider.
- Baseline de validação `4502d1d253863f6f25e13e6a7284a0d53a3b0fd5`: RPG Skill Tree CI `34147843664` SUCCESS; Sonar `34147843581` SUCCESS; CodeQL `34147843620` SUCCESS.
- **Resultado:** implementação confirmada no owner canônico `Attributes.ARMOR`, compondo naturalmente com equipment/Apothic/outros modifiers sem duplicar autoridade.

### Checklist final Chat 3

- [x] Design aprovado e código presente
- [x] Binding `ARMOR` / +2% por rank confirmado
- [x] zero base→zero bônus confirmado
- [x] composição/idempotência de modifiers confirmadas
- [x] rank loss/respec/reload sem modifier órfão confirmado pelo runtime canônico
- [x] Sem `STUN_ARMOR`/Resistência Física/NBT/durabilidade
- [x] Anti-abuso/Mastery: N/A — perk não concede Mastery nem recurso
- [x] Testes unitários/NeoForge JUnit e GameTests verdes
- [x] Build NeoForge e dedicated-server smoke verdes
- [x] SonarQube e CodeQL verdes no baseline funcional
- [x] **IMPLEMENTAÇÃO CONFIRMADA**

O HEAD documental final deve ser revalidado em CI antes do merge da PR #372.
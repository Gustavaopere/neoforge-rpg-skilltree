# A0095 — Tenacidade

## Estado

- **Design:** APROVADO após REDESIGN provider-native em 2026-08-31.
- **Notion:** `3c569db9-f0db-8171-8724-d67458cc1603`; mutado e re-fetch PASS.
- **Runtime observado:** fórmula antiga de redução linear de interrupção permanece apenas como código histórico; o contrato aprovado migra para `epicfight:stun_armor`.

## Contrato canônico

- Gateway VITALITY + A0091 Base Firme ≥2.
- 5 ranks, +0,25 de `epicfight:stun_armor` por rank, máximo +1,25.
- O Epic Fight continua sendo owner da fórmula não linear de stun reduction.
- A dependência antiga de A0094 foi removida: guarda/recovery não é requisito semântico para estabilidade corporal.

## Evidência provider

Epic Fight 21.17.3.1 registra `STUN_ARMOR` como atributo próprio e deriva seu baseline de stun reduction a partir de `stunArmor/(stunArmor+7.5)`. Armaduras provider-native já adicionam Stun Armor. Portanto criar um segundo `interruptionMultiplier` linear duplicaria semântica e quebraria provider-native first.

## Binding aprovado

Adicionar em `node_effects/a0081_a0100.json` um modifier estável para `rpgskilltree:combat/a0095`:

- `attributeId`: `epicfight:stun_armor`;
- `operation`: `ADD_FLAT`;
- `amountPerRank`: `0.25`;
- effectId estável e cleanup idempotente em rank loss/respec/rules reload/relog.

## Cobertura de providers

- Epic Fight é owner positivo de Stun Armor e stun reduction.
- Equipamentos vanilla/modded com capability de armadura Epic Fight compõem no mesmo atributo.
- A0091 Knockback Resistance permanece totalmente separada.
- Armor, Toughness, Impact, guarda/stamina, magic resistance e hazards não são substitutos.

## Pendências para Chat 2

- **P-A0095-01 BLOQUEANTE DE CONFORMIDADE:** substituir o antigo reducer paralelo por binding `epicfight:stun_armor` +0,25/rank.
- **P-A0095-02:** atualizar catálogo/runtime/tests para remover dependência A0094 e exigir somente A0091≥2 + Epic Fight compatível.
- **P-A0095-03:** availability provider-present/absent e lifecycle/idempotência do modifier.
- **P-A0095-04:** regressões provando que knockback, Armor/Toughness, Impact e guarda não são alterados.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS após correção | A0091≥2; A0094 removida. |
| Integração global | PASS | usa atributo provider-native real. |
| Qualidade/identidade | PASS | estabilidade/stun, distinta de knockback. |
| Topologia | PASS | VITALITY/STABILITY, Camada 2. |
| Especializações | PASS | PP somente por mapeamento de estabilidade. |
| PT-BR | PASS | Stun Armor explicado pelo efeito real. |
| Notion | PASS | redesign persistido e re-fetchado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | Epic Fight é owner; sem reducer paralelo. |

Os 18 critérios passam após o redesign provider-native; o runtime atual precisa ser alinhado pelo Chat 2.
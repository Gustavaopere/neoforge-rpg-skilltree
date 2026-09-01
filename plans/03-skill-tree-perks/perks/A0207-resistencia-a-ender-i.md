# A0207 — Resistência a Ender I

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-813f-89ac-dd6cca8e23bb; transitive availability, classifier, bucket e providers corrigidos; re-fetch PASS.
- **Runtime observado:** não há classificador hostil ENDER nem binding RPG_ENDER_RESISTANCE. A0207 é **UNAVAILABLE_NODE/não comprável**.
- **Dependência adiantada:** a rota A0205 permanece fechada. Gateway VITALITY não fabrica capability.

## Contrato canônico

- Até 4 ranks: +3% / +6% / +9% / +12%.
- Contribui para um único bucket RPG_ENDER_RESISTANCE.
- Mitiga somente componente hostil explicitamente classificado ENDER.
- Void, cold, teleport, dano genérico do End, resistência mágica e proteção contra deslocamento são eixos distintos.

## Gate e availability

A compra exige A0205 ≥1 legitimamente disponível **ou** Gateway VITALITY, além de adapter hostile_direct_ender_component e binding no DamageMitigationResolver. Sem classifier/binding, o node inteiro fica indisponível. O gateway topológico não permite rank no-op.

## Providers

- RPG Skill Tree: availability, bucket e aplicação.
- Fire's Ender Expansion 2.4.1 e Somake 1.0.8: somente outcomes hostis concretos versionados.
- Minecraft/NeoForge: damage boundary, nunca classificação temática.
- Black Arcana, Enshrouded e Volcanoes: N/A; hazards/resistências próprios.
- Tecnologia, mobs do End e gear temático: não classificam ENDER por si.

## Hook e lifecycle

AvailabilityResolver reavalia capability em login, provider/datapack reload, respec e perda de A0205/gateway. DamageMitigationResolver classifica uma vez, agrega A0207 ao bucket e o aplica uma vez apenas ao componente ENDER.

## Pendências para Chat 2

- **P-A0207-01 BLOQUEANTE:** unavailable-node transitivo de A0205/adapter.
- **P-A0207-02 BLOQUEANTE:** classifier hostile ENDER e bucket binding.
- **P-A0207-03:** composição com mitigadores distintos e mixed components.
- **P-A0207-04:** lifecycle/reload e testes negativos de Void/cold/End/teleport.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0205 ou VITALITY mais capability real. |
| Integração global | PASS | bucket defensivo canônico. |
| Qualidade/identidade | PASS | defesa Ender específica, não Void/cold. |
| Topologia | PASS | ponte VITALITY↔ARCANE/AGILITY. |
| Especializações | PASS | bridge PP sem dupla contagem. |
| PT-BR | PASS | Resistência a Ender sem equivalências falsas. |
| Notion | PASS após correção | availability regravada e relida. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | hostile component versionado obrigatório. |

Os 18 critérios passam **no design**; VITALITY não habilita node sem backend.

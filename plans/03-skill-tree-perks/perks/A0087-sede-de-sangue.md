# A0087 — Sede de Sangue

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability all-or-nothing em 2026-08-31.
- **Notion:** `3c569db9-f0db-819d-8f92-dd2b7f0c4ed8`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- A0087 permanece **indisponível/não comprável**; nenhum benefício parcial é ativado.

## Contrato canônico

- MARTIAL + A0075≥1 + A0081=3 + A0082≥2, todos legitimamente disponíveis/adquiridos.
- Após perder ≥25% da vida máxima por dano hostil válido em até 6 s, ativa por 6 s; cooldown 45 s.
- Durante a janela, golpes diretos de arma usam no mínimo 3% de coeficiente no `SustainResolver` e o jogador recebe +8% de cura recebida.
- Cada atividade física elegível aplica +20% de contribuição de calor metabólico via Cold Sweat 2.4.2 e +15% exhaustion via Minecraft/NeoForge.
- Hidratação +15% é opcional somente quando Thirst Was Reclaimed 3.0.4 fornecer adapter causal para a **mesma atividade**.
- Cap de sustain continua 3% max health/20 ticks.

## All-or-nothing e availability

Calor metabólico + exhaustion são tradeoffs obrigatórios. Se qualquer um não puder ser aplicado/manter, nenhum benefício pode existir e o node não pode ser comprado. Hydration é eixo separado e opcional por capacidade; ausência do adapter não pode ser mascarada por exhaustion.

A0087 também herda transitivamente A0075 e A0081. Enquanto A0075 continuar indisponível, A0081 e A0087 permanecem indisponíveis.

## +8% healing received

O contrato do Notion é geral: **+8% de cura recebida** durante a janela, não apenas +8% sobre a parcela Skill Tree. O Chat 2 não estreitou esse escopo. Como ainda não existe boundary geral de healing-received combinado ao BodyProvider obrigatório, A0087 permanece totalmente unavailable; `healingReceivedMultiplier()` não é usado para ativar benefício parcial.

## Implementação Chat 2 — 2026-09-01

- `CombatPerkAvailabilityRuntime` mantém A0087 unavailable por A0075/A0081 + ausência de BodyProvider/healing-received geral;
- `effectiveRanks` mascara qualquer A0087 persistida, então `BloodThirstService`, coeficiente mínimo 3% e healing multiplier não produzem efeito;
- `BloodThirstService` continua instanciado com `BodyProvider=null`, preservando o fail-closed all-or-nothing;
- `A0081A0090SustainRuntime` não aplica +8% nem 3% mínimo enquanto o rank efetivo estiver zero;
- Ignitium continua tratado no mesmo `SustainResolver` como native correlation ambígua, sem exceção para A0087;
- hydration não foi inferida a partir de exhaustion;
- nenhum bridge Cold Sweat/Thirst incompleto foi fabricado para tornar o node comprável.

## Checklist Chat 2

- [x] Availability transitiva/all-or-nothing implementada
- [x] Rank efetivo zero enquanto BodyProvider ausente
- [x] Purchase sem gasto/rank fantasma
- [x] Nenhum benefício parcial de Blood Thirst ativo
- [x] Ignitium/native lifesteal continua deduplicado/fail-closed
- [x] Hydration não inferida
- [x] Código presente em fail-closed
- [ ] **PENDÊNCIA:** BodyProvider Cold Sweat heat + vanilla exhaustion para a mesma atividade
- [ ] **PENDÊNCIA:** pipeline geral de +8% healing received exatamente uma vez
- [ ] **PENDÊNCIA:** adapter Thirst causal opcional
- [ ] **RETORNO AO CHAT 1:** somente se a implementação futura do +8% geral exigir alterar semântica/escopo do contrato
- [ ] **VALIDAÇÃO CHAT 3:** node unavailable e rank persistido com efeito zero
- [ ] **VALIDAÇÃO CHAT 3:** nenhum 3% mínimo/+8% parcial vaza
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/testes de integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0075/A0081/A0082 + BodyProvider real. |
| Integração global | PASS | sustain + body axes separados e coordenados. |
| Qualidade/identidade | PASS | burst sustain com custo metabólico real. |
| Topologia | PASS | terminal MARTIAL/SUSTAIN. |
| Especializações | PASS | terminal só satisfaz Specialist se mapeado. |
| PT-BR | PASS | heat/exhaustion/hydration sem confusão semântica. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | Cold Sweat/Thirst/Simply boundaries explícitas. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
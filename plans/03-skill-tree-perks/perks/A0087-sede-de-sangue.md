# A0087 — Sede de Sangue

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability all-or-nothing em 2026-08-31.
- **Notion:** `3c569db9-f0db-819d-8f92-dd2b7f0c4ed8`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** `BloodThirstService` existe, mas produção usa `new BloodThirstService(null)`; A0087 deve ficar **indisponível/não comprável**.

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

O contrato do Notion é geral: **+8% de cura recebida** durante a janela, não apenas +8% sobre a parcela Skill Tree. Portanto a implementação precisa de um pipeline canônico de healing received que aplique o multiplicador exatamente uma vez às curas elegíveis. O runtime atual expõe `healingReceivedMultiplier()` e o bridge o usa no sustain, mas não há prova de aplicação geral a potions/regen/provider heals. Silenciosamente reduzir o escopo para SustainResolver seria divergência de contrato.

## Cobertura de providers

- Cold Sweat 2.4.2: owner exclusivo do eixo térmico/metabolic heat.
- Minecraft/NeoForge: exhaustion e healing events.
- Thirst Was Reclaimed 3.0.4: hydration apenas por receipt causal; Thirst Was Fixed é correção/integration, não owner de semântica substituta.
- Epic Fight: ação marcial quando comprovada.
- Simply Swords: Cataclysm: lifesteal de Ignitium deve ser contabilizado no mesmo root/bucket, sem duplicação.
- Outros Simply: preservam implicits/runic/uniques; não inferir lifesteal.
- Black Arcana Backlash/BLOOD_MAGIC_COST, Enshrouded/Volcanoes hazards, summons e tech damage não ativam o gatilho.

## Evidência runtime

`BloodThirstService` exige `BodyProvider.acquire/maintain` para heat+exhaustion e encerra a janela se manutenção falhar. `A0081A0100RuntimeState` injeta `null` deliberadamente, confirmando fail-closed atual. A lógica de perda hostil/cooldown existe; o binding corporal e o healing-received global não.

## Pendências para Chat 2

- **P-A0087-01 BLOQUEANTE:** unavailable-node invariant transitivo A0075/A0081 + `BodyProvider`; current `null` não permite compra.
- **P-A0087-02:** implementar `BodyProvider` com Cold Sweat metabolic heat + vanilla exhaustion na mesma atividade, acquire/maintain/release e rollback seguro.
- **P-A0087-03:** hydration Thirst opcional somente por causal receipt; ausência é omitida, nunca inferida.
- **P-A0087-04 BLOQUEANTE DE CONFORMIDADE:** ligar +8% a um pipeline geral de healing received exatamente uma vez; se isso não for possível, voltar ao Chat 1 em vez de estreitar o contrato.
- **P-A0087-05:** dedup de Ignitium/native lifesteal e testes trigger window/cooldown/tradeoff loss/lifecycle/multiplayer.

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

Os 18 critérios passam **no design** porque benefício e custos são all-or-nothing e availability é explícita.
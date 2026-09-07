# A0081 — Recuperação de Combate

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-81cb-a393-d34c2a87af0d`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** `CombatRecoveryService` implementa a matemática e o parcelamento, mas A0081 deve permanecer **indisponível/não comprável** enquanto A0075 estiver indisponível.

## Contrato canônico

- MARTIAL + A0075 Ritmo Sustentado ≥1 legitimamente disponível e ativo.
- 3 ranks: 15% / 20% / 25% do dano corpo a corpo direto pós-mitigação realmente causado vira reserva.
- Reserva máxima: 8% da vida máxima; overkill é cortado por `min(dano, vida pré-impacto do alvo)`.
- Após 3 s sem receber dano hostil, congela `recovery_snapshot`; até 25% do snapshot por segundo, no máximo quatro parcelas.
- Só vida realmente restaurada reduz a reserva; sem overheal. Novo dano hostil interrompe a fase; reserva expira após 10 s fora de combate.
- Recuperação é pipeline próprio e **não** usa o bucket do `SustainResolver`.

## Availability transitiva

A0075 está atualmente indisponível/não comprável porque o contrato all-or-nothing de Stamina regen + contribuição térmica Cold Sweat + exhaustion ainda não possui binding térmico seguro. Logo A0081 herda a indisponibilidade. Matemática presente não autoriza compra, rank fantasma nem gasto de pontos.

Perda de A0075 por rank loss/respec/rules reload também invalida imediatamente qualquer reserva/snapshot A0081.

## Cobertura de providers

- Minecraft/NeoForge: dano/vida/cura e timing server-side.
- Epic Fight 21.17.3.1: apenas classificação da ação marcial quando comprovada.
- Simply Swords 1.70.2, Simply More 1.3.0 ALPHA e bridges: podem fornecer armas, mas habilidades/procs provider-native não viram melee direto apenas porque o jogador segura uma arma.
- Cold Sweat 2.4.2 e Thirst Was Reclaimed 3.0.4: não modulam A0081; o bloqueio vem somente da dependência A0075.
- Magia, summons, companions, Black Arcana Backlash, Enshrouded/Volcanoes hazards e dano de máquinas/contraptions: inelegíveis.

## Evidência runtime

`CombatRecoveryService` já implementa cap, snapshot, quatro parcelas, clipping de overkill, confirmação do heal e dedup por root. `A0081A0100CombatEvents` chama o serviço apenas em POST de dano físico capturado, mas o classificador atual `direct player + main hand não vazia` ainda precisa prova regressiva contra ability-derived hits e fontes provider-native.

## Pendências para Chat 2

- **P-A0081-01 BLOQUEANTE:** aplicar unavailable-node invariant transitivo A0075→A0081 no purchase/gate; nenhum gasto/rank enquanto A0075 não for legitimamente disponível.
- **P-A0081-02:** limpar reserva/snapshot em rank loss, respec, rules reload ou perda de availability de A0075, além do lifecycle já existente.
- **P-A0081-03:** provar root melee de arma real; não classificar proc/ability/summon como melee direto pela simples presença de item na mão.
- **P-A0081-04:** GameTests de overkill, quatro parcelas, dano hostil interrompendo fase, zero missing health, expiry, dedup e multiplayer.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0075≥1 e availability transitiva. |
| Integração global | PASS | Recuperação separada de vampirismo/SustainResolver. |
| Qualidade/identidade | PASS | sustain diferido pós-combate, distinto de lifesteal. |
| Topologia | PASS | MARTIAL/SUSTAIN, Camada 4. |
| Especializações | PASS | PP apenas por mapeamento semântico. |
| PT-BR | PASS | contrato e UI em PT-BR. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | removido/ausente. |
| Providers | PASS no design | provider-native first; fontes indiretas/hazards fail-closed. |

Os 18 critérios passam **no design** porque a indisponibilidade transitiva é explícita.
# A0098 — Defesa em Movimento

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-81d1-9530-c1c2aa50e07a`; fetch fresco PASS.
- **Runtime observado:** fallback server-authoritative já usa `player.isSprinting()`; cobertura ParCool permanece fail-closed.

## Contrato canônico

- Gateway VITALITY + A0088 Constituição ≥2 + acesso real ao corredor AGILITY.
- 3 ranks: enquanto em locomoção autopropelida reconhecida, −3% de dano hostil elegível por rank, máximo −9%.
- Sprint vanilla server-side é binding válido.
- Knockback, queda, montaria, veículo, contraption, belt, grappling ou deslocamento externo não ativam a perk.

## Evidência runtime

`A0081A0100CombatEvents` chama `movingDefenseMultiplier(ranks, player.isSprinting())`. A policy exige `authoritativeSelfPropelledSprint`; portanto o fallback vanilla está alinhado, mas não cobre movimentos especiais do ParCool sem adapter real.

## Cobertura de providers

- Minecraft/NeoForge: sprint server-side positivo.
- Epic Fight: contexto de combate apenas; não é owner universal de locomoção.
- ParCool 4.0.0.3 / Epic ParCool 21.0.0: candidatos somente quando houver estado server-authoritative inequívoco da ação específica.
- Create contraptions/belts, montarias e veículos são deslocamento externo, não movimento autopropelido do jogador.

## Pendências para Chat 2

- **P-A0098-01:** testar sprint vanilla, stop/start, knockback/queda/mount/vehicle/contraption/belt e dedup do pipeline defensivo.
- **P-A0098-02:** qualquer adapter ParCool deve provar estado mecânico server-side; animação/câmera/delta de posição não bastam.
- **P-A0098-03:** validar `BRIDGE_PP_POLICY` após Stage 04.02: pontos da bridge contam para no máximo um threshold explicitamente whitelisted, nunca ambos.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0088≥2 + corredor AGILITY. |
| Integração global | PASS | movimento autopropelido separado de deslocamento externo. |
| Qualidade/identidade | PASS | defesa móvel distinta de stationary. |
| Topologia | PASS | VITALITY↔AGILITY bridge. |
| Especializações | PASS | no máximo um threshold whitelisted. |
| PT-BR | PASS | condições explícitas. |
| Notion | PASS | fetch fresco sem drift. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | sprint vanilla positivo; ParCool condicional. |

Os 18 critérios passam no design; o fallback vanilla é implementável sem heurística.
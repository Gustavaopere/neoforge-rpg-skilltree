# A0077 — Postura Cautelosa

## Estado

- **Design:** APROVADO após correção de availability/boundary em 2026-08-31.
- **Notion:** `3c569db9-f0db-81b7-aaac-d1f391ec10aa`; Gate/Hook/Fallback/Provider/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

## Contrato canônico

- MARTIAL + A0067 Firmeza Ofensiva ≥2.
- 1 rank, custo 1.
- Enquanto `CAUTIOUS`: +8% resistência física elegível e −5% dano físico.
- Ocupa exclusivamente `MARTIAL_STANCE`; mutuamente exclusiva com A0076; cooldown 1,5 s.

## Availability e ativação

A0067 continua indisponível/não comprável até existir attack-window binding seguro, então A0077 herda esse bloqueio. O mesmo slot/runtime de A0076 já suporta `CAUTIOUS`, mas `CombatPerkAvailabilityRuntime` mantém o rank efetivo de A0077 em zero enquanto a cadeia A0067 não estiver legitimamente disponível.

## Implementação Chat 2 — 2026-09-01

- infraestrutura compartilhada `MARTIAL_STANCE` implementada com payload serverbound e authority do servidor;
- `MartialStanceRuntime` suporta ciclo determinístico `NONE → AGGRESSIVE → CAUTIOUS → NONE` quando ambas as perks estiverem disponíveis;
- no estado atual, A0077 é não comprável e não ativável porque A0067 permanece fail-closed;
- `effectiveRanks` impede benefício residual mesmo se existir alocação persistida antiga;
- policy/runtime já modelam −5% dano físico e +8% resistência física de `CAUTIOUS` sem confundir o canal com Armor, Stun Armor ou resistências mágicas;
- nenhuma regra genérica contorna A0067.

## Pendências para Chat 3

- validar purchase fail-closed e rank efetivo zero enquanto A0067 estiver indisponível;
- validar que `CAUTIOUS` não pode ser ativada no estado atual e que nenhum resíduo de stance sobrevive a perda de availability;
- validar exclusividade/cooldown/ciclo quando a infraestrutura for exercitada em cenário controlado;
- se tornar A0067 adquirível exigir redesign de provider/gate, devolver ao Chat 1 em vez de liberar A0077 por aproximação.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0067 + binding são obrigatórios. |
| Integração global | PASS | canal físico separado de outros sistemas. |
| Qualidade/identidade | PASS | stance defensiva com tradeoff ofensivo. |
| Topologia | PASS | Camada 3, `MARTIAL/POSTURE`. |
| Especializações | PASS | região de posturas explícita. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | RPG authority; Epic Fight não duplicado. |

Chat 2 conclui corretamente A0077 em fail-closed; não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.

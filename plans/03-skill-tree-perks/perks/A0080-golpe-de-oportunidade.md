# A0080 — Golpe de Oportunidade

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability e commit causal em 2026-08-31.
- **Notion:** `3c569db9-f0db-81e5-a39c-d1623fa37f5e`; Gate/Hook/Fallback/Provider/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

## Contrato canônico

- MARTIAL + A0078 Ataque em Movimento ≥2 + acesso semântico ao ramo/corredor AGILITY de esquiva.
- 1 rank, custo 2.
- Uma esquiva que **realmente evitou um ataque hostil** abre Janela de Oportunidade por 3 s.
- Próximo golpe físico direto elegível recebe +15% dano; uma janela por vez; cooldown 5 s após consumo ou expiração.

## Receipt obrigatório

Abertura exige receipt server-authoritative correlacionando `dodgeActionId`/`avoidedAttackId` ao ataque hostil que teria atingido e foi efetivamente evitado. Dodge executado, tecla, animação, i-frame teórico, posição ou simples ausência de dano não são prova.

Epic Fight, ParCool e Epic ParCool só podem convergir quando existir a mesma identidade causal do ataque evitado.

## Reservation → commit do golpe consumidor

O state service suporta reserva do próximo hit no PRE e commit/rollback no POST. Esse seam permanece latente enquanto o node está indisponível; nenhum producer falso é criado só para exercitar a matemática.

## Implementação Chat 2 — 2026-09-01

- `CombatPerkAvailabilityRuntime` marca A0080 indisponível e `effectiveRanks` mascara qualquer alocação persistida;
- purchase server-authoritative recusa o node enquanto o receipt de dodge-success não existir;
- `A0061A0080CombatState` recebeu `reserveOpportunity`, `commitOpportunity`, `rollbackOpportunity` e helpers bounded para o caminho de projectile;
- Epic Fight e projectile físicos possuem seam PRE→POST para o futuro consumer, mas com rank efetivo zero no estado atual;
- review P1 da PR #355 detectou que o antigo POST de projétil usava consumo actor-wide e poderia consumir a reservation de outra flecha/root; corrigido nos commits `b3fd4516a06ec7de3049ed64732b26cbcc5a4720` e `e7a102e9ca22c1065cfd62045fc4e5bb8689576a`: cada `arrow + target` guarda o `rootActionId` canônico e `commitOpportunity`/`rollbackOpportunity` recebem exatamente esse root;
- nenhum callback de dodge executado, tecla, animação ou ausência de dano foi promovido a `onConfirmedDodgeAvoidance`;
- Better Lock On/Lock-On Movement Fix continuam fora da authority de dodge-success.

## Pendências para Chat 3

- validar purchase recusada e contribuição runtime zero mesmo com rank persistido;
- validar que nenhum dodge genérico/tecla/i-frame/posição abre janela;
- validar o consumer latente reservation→POST commit/rollback sem falso consumo em dano zero/cancelamento e sem consumo cruzado entre projéteis/root simultâneos;
- quando/SE existir provider receipt real de `avoidedAttackId`, validar dedup Epic Fight/ParCool/Epic ParCool; se a solução alterar provider/gate/semântica, devolver ao Chat 1;
- validar lifecycle/expiry/cooldown e bridge PP AGILITY em cenário controlado.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0078≥2 + AGILITY + receipt dodge-success. |
| Integração global | PASS | não confunde dodge com lock-on/movimento. |
| Qualidade/identidade | PASS | recompensa somente esquiva realmente bem-sucedida. |
| Topologia | PASS | Camada 3, `MARTIAL_AGILITY_BRIDGE`. |
| Especializações | PASS | bridge PP sem dupla contagem. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Epic Fight/ParCool somente com receipt causal; ausência gera indisponibilidade. |

Chat 2 conclui corretamente A0080 em fail-closed; não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
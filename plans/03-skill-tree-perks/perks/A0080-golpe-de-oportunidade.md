# A0080 — Golpe de Oportunidade

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability e commit causal em 2026-08-31.
- **Notion:** `3c569db9-f0db-81e5-a39c-d1623fa37f5e`; Gate/Hook/Fallback/Provider/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** node deve permanecer **indisponível/não comprável**; o adapter atual não prova dodge-success causal.

## Contrato canônico

- MARTIAL + A0078 Ataque em Movimento ≥2 + acesso semântico ao ramo/corredor AGILITY de esquiva.
- 1 rank, custo 2.
- Uma esquiva que **realmente evitou um ataque hostil** abre Janela de Oportunidade por 3 s.
- Próximo golpe físico direto elegível recebe +15% dano; uma janela por vez; cooldown 5 s após consumo ou expiração.

## Receipt obrigatório

Abertura exige receipt server-authoritative correlacionando `dodgeActionId`/`avoidedAttackId` ao ataque hostil que teria atingido e foi efetivamente evitado. Dodge executado, tecla, animação, i-frame teórico, posição ou simples ausência de dano não são prova.

Epic Fight, ParCool e Epic ParCool devem convergir na mesma identidade causal; callbacks do mesmo `avoidedAttackId` não renovam/empilham.

## Reservation → commit do golpe consumidor

PRE pode reservar janela e aplicar +15%; POST com dano físico direto efetivo >0 commita consumo + cooldown. Cancelamento/dano zero faz rollback e preserva a janela até expirar.

## Evidência runtime

`A0061A0080CombatPolicy.onConfirmedDodgeAvoidance(...)` modela a confirmação, mas `A0061A0080EpicFightHooks` declara A0080 fail-closed e não possui producer de receipt de dodge-success. `consumeOpportunityDamageMultiplier(...)` hoje consumiria no PRE caso existisse janela, portanto o Chat 2 também precisa reservation→commit.

## Fallback

Sem receipt causal: A0080 indisponível/não comprável, sem silent no-op. Better Lock On/Lock-On Movement Fix não são providers de dodge-success.

## Pendências para Chat 2

- **P-A0080-01 BLOQUEANTE:** unavailable-node invariant enquanto não existir receipt server-authoritative de ataque hostil efetivamente evitado.
- **P-A0080-02:** implementar adapter Epic Fight/ParCool/Epic ParCool com `avoidedAttackId`/dodge identity real e dedup cross-provider.
- **P-A0080-03:** converter consumo do próximo hit para reservation→POST commit; cancel/zero rollback.
- **P-A0080-04:** testes lifecycle, cooldown/expiry, duplicate dodge callbacks, procs/summons/fake players e bridge PP AGILITY.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0078≥2 + AGILITY + receipt dodge-success. |
| Integração global | PASS | não confunde dodge com lock-on/movimento. |
| Qualidade/identidade | PASS | recompensa esquiva realmente bem-sucedida. |
| Topologia | PASS | Camada 3, `MARTIAL_AGILITY_BRIDGE`. |
| Especializações | PASS | bridge PP sem dupla contagem. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Epic Fight/ParCool somente com receipt causal; ausência gera indisponibilidade. |

Os 18 critérios passam **no design** porque o fail-closed/unavailable é explícito.
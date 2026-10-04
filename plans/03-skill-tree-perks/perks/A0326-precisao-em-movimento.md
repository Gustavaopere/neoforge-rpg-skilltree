# A0326 — Precisão em Movimento

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0326` — https://app.notion.com/3c569db9f0db81acb762d91151002adc
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0326 atua apenas em ação direta `RANGED_PHYSICAL` quando houver deslocamento **voluntário e causalmente comprovado no RELEASE/LAUNCH**. Possui dois componentes independentes:

1. **precisão:** reduz em 6% por rank somente a parcela NATIVA de penalidade de precisão/dispersão atribuída ao movimento;
2. **crítico:** adiciona +2 pontos percentuais por rank à chance crítica da mesma ação, antes da única rolagem crítica canônica.

Ranks 1–3:

- accuracy movement penalty: ×0,94 / ×0,88 / ×0,82;
- crit: +2 / +4 / +6 p.p.

A perk não reduz spread base/estacionário, recoil ou sway por aproximação e não altera MAGIC por inferência.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0318 Passo Leve ≥2.

Gate técnico obrigatório: snapshot server-authoritative no RELEASE/LAUNCH contendo identidade da ação e prova de locomoção voluntária no mesmo espaço/frame.

Movimento causado por knockback, veículo/montaria, conveyor/contraption, corrente, teleporte, queda ou movimento passivo do sublevel não conta.

No snapshot atual esse contexto causal não está operacional; portanto compra falha antes do gasto e allocation legada indisponível vale 0 PP para gates/thresholds.

## Providers e authority

- Minecraft/NeoForge bows/crossbows: podem ser integrados somente por adapter explícito de launch.
- Epic Fight 21.17.3.1: não é convertido automaticamente em provider de accuracy movement penalty.
- Pufferfish Attributes 0.8.3: projectile speed não deve ser reinterpretado como accuracy.
- Sable 2.0.5 / Create Aeronautics: exigem movimento analisado no frame/space local correto.
- RPG Skill Tree: owner da perk e composição futura.

### Correção documental obrigatória

A `main` auditada em 2026-09-05 **não contém `A0001A0020CriticalService`**. A referência anterior no Notion era obsoleta e foi corrigida. Chat 2 não deve criar esse serviço apenas para satisfazer o nome antigo.

Da mesma forma, não foi comprovado serviço genérico de root-action claim reutilizável na `main`.

## Contrato futuro — movimento

No commit RELEASE/LAUNCH, o adapter deve snapshotar uma única vez algo equivalente a:

`{root_action_id, owner_uuid, space_id, movement_context, ranged_lane}`

`movement_context` deve distinguir locomoção voluntária de movimento externo/passivo.

## Contrato futuro — accuracy

Somente se o provider expuser `movement_accuracy_penalty_native >= 0`:

`movement_penalty_final = movement_accuracy_penalty_native × (1 − 0.06 × rank)`

Qualquer parcela de spread/erro que não seja atribuída explicitamente ao movimento permanece intocada.

Sem decomposição provider-native, o eixo accuracy é omitido; nunca reduzir spread total como aproximação.

## Contrato futuro — crítico

Somente quando existir lane crítica canônica concreta para aquele provider, adicionar:

`critical_chance_candidate += 0.02 × rank`

antes do clamp e da **única** rolagem crítica da mesma ação/root. Nunca rerrolar no impacto.

Accuracy e crítico são componentes independentes depois que o snapshot causal de movimento existir: um pode ser omitido se seu binding específico estiver ausente sem fabricar o outro.

## Fallback / fail-closed

Enquanto faltar o snapshot causal de movimento no launch, o node inteiro permanece indisponível. Quando essa closure abrir:

- sem decomposição de accuracy: omitir accuracy;
- sem lane crítica canônica concreta: omitir crítico;
- nunca criar segunda rolagem;
- nunca inferir movimento voluntário por delta de posição isolado.

## Anti-abuso e deduplicação

- um snapshot por root/launch;
- uma contribuição accuracy por componente nativo de movimento;
- um additive crit antes da única decisão crítica;
- projétil reemitido/derived não reaplica por impacto;
- movimento do parent/sublevel não satisfaz o gate;
- nenhuma aplicação em MAGIC ou dano derived.

## Testes destinados ao Chat 3

1. snapshot atual: compra fail-before-spend;
2. allocation legada indisponível = 0 PP e migrável/refundável;
3. movimento externo/knockback/vehicle/contraption não satisfaz gate;
4. provider futuro com movement penalty: ranks 1–3 produzem ×0,94/0,88/0,82 somente nessa parcela;
5. spread base/recoil/sway permanecem intactos;
6. crit futuro adiciona +2/+4/+6 p.p. antes da única rolagem;
7. nenhuma segunda rolagem no impacto;
8. absence de um eixo não fabrica o outro;
9. mesmo root não duplica por adapters/projéteis irmãos;
10. multiplayer/dedicated-server com frame local correto.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE` enquanto faltar o snapshot causal de movimento no RELEASE/LAUNCH. Não implementar por `isMoving`, delta de posição, projectile speed ou serviço crítico documental inexistente.

# A0045 — Precisão com Arcos

## Estado

- **Design:** APROVADO; sem mutação no Notion nesta retroauditoria.
- **Implementação:** PRESENTE no resolver crítico canônico para projéteis BOW; aquisição depende de `P-A0043-01` para Mastery alcançável pela rota vanilla/NeoForge.
- **Notion:** `3c569db9-f0db-81c8-8eb0-d03018a9ddc6`.

## Contrato canônico

- A0043 ≥1 + gateway `epic_bow`.
- +3% de chance crítica com arcos por rank, até +9%.
- Cada projétil/root action elegível participa de no máximo uma resolução crítica canônica.
- Resultado crítico já produzido por provider/backend deve ser consumido pelo mesmo resolver, nunca por segundo roll.

## Evidência runtime

- `A0041A0060ProjectileEvents.onEntityJoin(...)` preserva o estado crítico no `ProjectileMeta`.
- O resolver `A0001A0020RuntimeState.critical()` recebe provider-critical + bônus BOW e mantém a identidade do root action.
- `onIncomingDamage(...)` aplica multiplicador crítico adicional apenas quando o resolver determinou crítico e o multiplicador ainda não foi aplicado pelo provider.
- A classificação vanilla usa `BowItem` e owner real do projétil.

## Provider→árvore

- **Black Arcana:** `ARCANE_BACKLASH` é terminal e nunca entra na resolução crítica de A0045.
- **Mobstein 5.4.4:** companion-owned projectile não herda crítico do dono.
- **Volcanoes / Enshrouded:** não produzem critical receipt BOW.
- **Stage 11.01 itemização:** sem projeção crítica ativa; não ler rolls diretamente para somar chance.

## Pendências Chat 2

- Revalidar gameplay/provider-present e deduplicação crítica em projétil real.
- A compra normal de A0045 continua dependente da correção `P-A0043-01` do gate de Mastery BOW.

## Testes exigidos

- crítico provider false/true;
- bônus A0045 rank 1/2/3;
- callback duplicado / mesma root action;
- projétil direto vs derived/spell/companion;
- coexistência com backend crítico sem segundo roll;
- dedicated server.

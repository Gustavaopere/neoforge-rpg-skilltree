# A0045 — Precisão com Arcos

## Estado

- **Design:** APROVADO; sem mutação no Notion nesta retroauditoria.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-81c8-8eb0-d03018a9ddc6`.

## Contrato canônico

- A0043 ≥1 + gateway `epic_bow`.
- +3% de chance crítica com arcos por rank, até +9%.
- Cada projétil/root action elegível participa de no máximo uma resolução crítica canônica.
- Resultado crítico já produzido por provider/backend deve ser consumido pelo mesmo resolver, nunca por segundo roll.

## Evidência runtime

- `A0041A0060ProjectileEvents.onEntityJoin(...)` preserva o estado crítico no `ProjectileMeta`.
- `A0001A0020RuntimeState.critical()` recebe provider-critical + bônus BOW e mantém a identidade do root action.
- `onIncomingDamage(...)` aplica multiplicador crítico adicional apenas quando o resolver determinou crítico e o multiplicador ainda não foi aplicado pelo provider.
- a classificação vanilla usa `BowItem` e owner real do projétil.
- o gate de Mastery BOW que historicamente impedia aquisição normal foi resolvido por `PhysicalProjectileMasteryEvents`/discovery persistente e pela chave canônica `epicfight:bow` na architecture atual.

## Provider→árvore

- **RPG Skill Tree:** authority do resolver crítico e dedup por root.
- **Minecraft/NeoForge:** projétil físico BOW e receipt de impacto.
- **Epic Fight:** provider-critical é entrada do mesmo resolver, não segunda rolagem.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e nunca entra na resolução crítica de A0045.
- **Mobstein 5.4.4:** companion-owned projectile não herda crítico do dono.
- **Volcanoes / Enshrouded:** não produzem critical receipt BOW.
- **Stage 11.01 itemização:** sem projeção crítica ativa; não ler rolls diretamente para somar chance.

## Pendência Chat 3

- validar provider-critical false/true e uma única resolução por root;
- validar ranks 1/2/3 e coexistência com backend crítico;
- validar projétil direct vs derived/spell/companion e dedicated server.

## Testes exigidos

- crítico provider false/true;
- bônus A0045 rank 1/2/3;
- callback duplicado / mesma root action;
- projétil direto vs derived/spell/companion;
- coexistência com backend crítico sem segundo roll;
- dedicated server.

## Fechamento Chat 2 — 2026-09-01

A pendência indireta de Mastery A0043 não bloqueia mais a aquisição. O Chat 2 não alterou o resolver crítico já existente e não executou a bateria final de validação.

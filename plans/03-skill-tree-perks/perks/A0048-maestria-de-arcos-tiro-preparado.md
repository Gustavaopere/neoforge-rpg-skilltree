# A0048 — Maestria de Arcos — Tiro Preparado

## Estado

- **Design:** APROVADO; sem mutação no Notion nesta retroauditoria.
- **Implementação:** PRESENTE no policy/projectile bridge; aquisição depende de `P-A0043-01` e falta prova gameplay/provider-present específica.
- **Notion:** `3c569db9-f0db-8121-b14f-d6e5a9fcbb55`.

## Contrato canônico

- A0046 + A0047 + `epicfight:bow` ≥80; terminal exterior de Arcos.
- Com ≥80 Foco e mira estável ≥1,25 s, o próximo disparo totalmente tensionado consome 50 Foco.
- Se o mesmo projétil atingir alvo elegível a ≥15 blocos: +20% dano físico e +15% penetração.
- Miss mantém o custo.
- Cooldown 8/7/6 s em mastery 80/90/100.

## Evidência runtime

- `tryPreparedShot(...)` valida rank/mastery, draw, stable aim, Foco ≥80 e cooldown; claim único, consome 50 e inicia cooldown.
- `A0041A0060ProjectileEvents.onArrowLoose(...)` tenta Tiro Preparado antes de Distância Dominada e registra o resultado no projétil correspondente.
- `resolveBowHit(...)` exige a distância mínima e aplica dano/penetração no mesmo `ProjectileMeta`.
- O custo ocorre no disparo e não é devolvido em miss/short hit.

## Provider→árvore

- **RPG Skill Tree:** authority de Foco, cooldown, root/projectile correlation e Mastery gate.
- **Black Arcana/Mobstein:** spell/Backlash/companion projectile não é Tiro Preparado do jogador.
- **Volcanoes/Enshrouded:** não modificam o shot; eventual corpo climático só vem de A0046.
- **Stage 11 itemização:** sem projeção direta neste terminal.

## Pendências Chat 2

- Depende de `P-A0043-01` para tornar `epicfight:bow` 80 alcançável pela rota de projétil BOW canônica.
- Adicionar prova gameplay/provider-present do disparo real, correlação com a flecha correta, miss, distância e cooldown.
- Se a penetração per-hit deixar de ser segura, manter o componente correspondente fail-closed; não substituir por modifier persistente.

## Testes exigidos

- Foco 79/80 e gasto 50;
- stable aim 1,249/1,25 s;
- fully drawn vs parcial;
- distância 14,99/15+;
- miss/short hit sem refund;
- cooldown 8/7/6;
- multi-arrow/derived sem herança;
- Mastery 79/80;
- dedicated server/multiplayer.

# A0048 — Maestria de Arcos — Tiro Preparado

## Estado

- **Design:** APROVADO; sem mutação no Notion nesta retroauditoria.
- **Implementação:** **NÃO CONFIRMADA / FAIL-CLOSED CORRETO VALIDADO PELO CHAT 3**.
- **Disponibilidade:** `UNAVAILABLE_NODE` por dependência estrutural de A0047 enquanto A0044/A0047 estiverem indisponíveis.
- **Notion:** `3c569db9-f0db-8121-b14f-d6e5a9fcbb55`.

## Contrato canônico

- A0046 + A0047 + `epicfight:bow` ≥80; terminal exterior de Arcos.
- Com ≥80 Foco e mira estável ≥1,25 s, o próximo disparo totalmente tensionado consome 50 Foco.
- Se o mesmo projétil atingir alvo elegível a ≥15 blocos: +20% dano físico e +15% penetração.
- Miss mantém o custo.
- Cooldown 8/7/6 s em mastery 80/90/100.

## Evidência runtime

- `tryPreparedShot(...)` implementa rank/mastery, draw, stable aim, Foco ≥80, custo e cooldown.
- `A0041A0060ProjectileEvents.onArrowLoose(...)` mantém Tiro Preparado antes de Distância Dominada e correlação pelo mesmo projétil/root.
- `resolveBowHit(...)` exige distância mínima e aplica dano/penetração no mesmo `ProjectileMeta`.
- `PhysicalProjectileMasteryEvents` tornou `epicfight:bow` 80 alcançável por discovery persistente.
- A0048 exige A0047, e `CombatPerkAvailabilityRuntime` mascara A0048 enquanto A0047 estiver estruturalmente indisponível. Assim não há gasto de 50 Foco, cooldown ou efeito por rank legado durante o bloqueio.

## Provider→árvore

- **RPG Skill Tree:** authority de Foco, cooldown, root/projectile correlation e Mastery gate.
- **Minecraft/NeoForge:** BOW físico e receipt do projétil real.
- **Black Arcana/Mobstein:** spell/Backlash/companion projectile não é Tiro Preparado do jogador.
- **Volcanoes/Enshrouded:** não modificam o shot; eventual corpo climático só vem de A0046.
- **Stage 11 itemização:** sem projeção direta neste terminal.

## Pendência técnica futura

O core da perk não exige redesign. Sua ativação depende exclusivamente da resolução legítima do chain gate A0044→A0047. Não criar bypass para habilitar A0048 isoladamente.

## Testes exigidos / estado atual

- availability transitiva A0047→A0048: **PASS**.
- compra indisponível sem gasto e rank legado mascarado sem Focus/cooldown: **PASS**.
- core de Tiro Preparado preservado sem efeito enquanto predecessor obrigatório estiver unavailable: **PASS**.
- Foco 79/80, stable aim 1,249/1,25 s, distância 14,99/15+, cooldown e miss sem refund: **N/A no runtime adquirível atual; cadeia estrutural indisponível**.

## Fechamento Chat 2 — 2026-09-01

A implementação funcional existente foi preservada, mas o node ficou corretamente fail-closed enquanto seu predecessor obrigatório estiver indisponível.

## Fechamento Chat 3 — 2026-09-05

- contrato revisado sem redesign;
- cadeia A0044→A0047→A0048 e ausência de gasto/efeito por ghost rank foram validadas;
- `RPG Skill Tree CI` #3467 / run `33986475213`: **SUCCESS**;
- `SonarQube Cloud` #703 / run `33986475341`: **SUCCESS**;
- **estado final:** `NÃO CONFIRMADA / FAIL-CLOSED CORRETO`; a ativação futura depende da habilitação legítima de A0044/A0047.

## Checklist Chat 3

- [x] Design aprovado pelo Chat 1
- [x] Código/fail-closed presente pelo Chat 2
- [x] Contrato revisado contra o código
- [x] Provider-native confirmado
- [x] Gate/dependências confirmados
- [x] Fallback/fail-closed confirmado
- [x] Deduplicação e autoria causal preservadas
- [x] Testes unitários verdes
- [x] GameTests verdes
- [x] Build NeoForge verde
- [x] Dedicated-server smoke verde
- [x] CI e Sonar verdes
- [ ] IMPLEMENTAÇÃO CONFIRMADA — **N/A enquanto a cadeia estrutural obrigatória permanecer indisponível**

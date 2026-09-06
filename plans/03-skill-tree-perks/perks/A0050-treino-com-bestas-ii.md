# A0050 — Treino com Bestas II

## Estado

- **Design:** APROVADO após correção de availability/fail-closed no review da PR #243.
- **Implementação:** **NÃO CONFIRMADA / FAIL-CLOSED CORRETO VALIDADO PELO CHAT 3**.
- **Disponibilidade:** `UNAVAILABLE_NODE` enquanto não existir binding server-authoritative semântico de reload/preparation speed.
- **Notion:** `3c569db9-f0db-812f-9e64-ca806740e883`; corrigido e re-fetch PASS em 2026-08-30.

## Contrato canônico

- A0049 ≥2 + gateway `epic_crossbow` + binding server-authoritative válido de reload/preparation speed.
- +2% de ritmo efetivo de recarga/preparo com bestas por rank, até +6%, somente quando provider expuser parâmetro server-authoritative com essa semântica.
- Projectile speed, mobilidade, stamina, dano, tooltip ou manipulação de timers por heurística não são substitutos.
- **Sem binding válido, A0050 é explicitamente INDISPONÍVEL/NÃO COMPRÁVEL:** nenhum ponto pode ser gasto e nenhum rank pode ser adquirido como no-op.
- Dependências posteriores que exijam A0050 não podem ser satisfeitas enquanto o nó estiver indisponível.

## Evidência runtime

- `CombatPerkAvailabilityRuntime` marca A0050 indisponível.
- `NodePurchaseRequestProcessor` rejeita o node antes de reservar request-id ou mutar pontos/ranks.
- o caminho trusted de `PlayerProgressionRuntime.purchaseNode(...)` usa o mesmo availability gate.
- `A0041A0060RuntimeState.ranks(...)` mascara rank legado de A0050.
- `A0041A0060ProjectileEvents.tickCrossbow(...)` observa estado carregado/uso para perks posteriores, mas não modifica reload/preparation time de A0050.
- nenhum provider seguro de reload speed foi inventado ou substituído por projectile speed/dano/Stamina/timer heurístico.

## Provider→árvore

- RPG Skill Tree permanece consumer apenas quando houver hook real; Stage 11 itemização não fornece reload projection.
- Volcanoes, Enshrouded, Black Arcana e Mobstein não são providers de recarga da besta.
- WoM/itens externos só participam se explicitamente classificados CROSSBOW; classificação não cria reload-speed API.

## Pendência técnica futura

`P-A0050-01` está resolvida quanto ao **availability gate**, mas a capability de reload/preparation speed continua inexistente. Reativação futura exige provider/version/semântica comprovados.

## Testes exigidos / estado atual

- provider ausente/incompatível → `UNAVAILABLE_NODE` sem gasto: **PASS**.
- rank legado mascarado: **PASS**.
- ausência de efeito alternativo em projectile speed/dano/Stamina: **PASS**.
- dependências posteriores continuam incapazes de satisfazer A0050 enquanto indisponível: **PASS** no availability estrutural.
- provider futuro presente, mainhand/offhand e redução real +2/+4/+6%: **N/A — provider semântico ainda inexistente**.

## Fechamento Chat 2 — 2026-09-01

O fail-closed foi enforceado na compra e no runtime efetivo. O Chat 2 não criou efeito alternativo.

## Fechamento Chat 3 — 2026-09-05

- availability server-authoritative, ausência de gasto/rank fantasma e masking de rank legado foram validados;
- nenhum substituto heurístico de reload speed foi introduzido;
- `RPG Skill Tree CI` #3467 / run `33986475213`: **SUCCESS**;
- `SonarQube Cloud` #703 / run `33986475341`: **SUCCESS**;
- **estado final:** `NÃO CONFIRMADA / FAIL-CLOSED CORRETO`; reativação exige binding real de reload/preparation speed.

## Checklist Chat 3

- [x] Design aprovado pelo Chat 1
- [x] Código/fail-closed presente pelo Chat 2
- [x] Contrato revisado contra o código
- [x] Provider-native confirmado
- [x] Gate/dependências confirmados
- [x] Fallback/fail-closed confirmado
- [x] Deduplicação/ausência de processamento alternativo confirmada
- [x] Autoria causal preservada
- [x] Testes unitários verdes
- [x] GameTests verdes
- [x] Build NeoForge verde
- [x] Dedicated-server smoke verde
- [x] CI e Sonar verdes
- [ ] IMPLEMENTAÇÃO CONFIRMADA — **N/A enquanto o provider obrigatório permanecer indisponível**

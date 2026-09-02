# A0050 — Treino com Bestas II

## Estado

- **Design:** APROVADO após correção de availability/fail-closed no review da PR #243.
- **Implementação:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
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
- `A0041A0060ProjectileEvents.tickCrossbow(...)` continua observando estado carregado/uso para perks posteriores, mas não modifica reload/preparation time de A0050.
- nenhum provider seguro de reload speed foi inventado ou substituído por projectile speed/dano/Stamina/timer heurístico.

## Provider→árvore

- RPG Skill Tree permanece consumer apenas quando houver hook real; Stage 11 itemização não fornece reload projection.
- Volcanoes, Enshrouded, Black Arcana e Mobstein não são providers de recarga da besta.
- WoM/itens externos só participam se explicitamente classificados CROSSBOW; classificação não cria reload-speed API.

## Pendência técnica futura

`P-A0050-01` está resolvida quanto ao **availability gate**, mas a capability de reload/preparation speed continua inexistente. Reativação futura exige provider/version/semântica comprovados.

## Pendência Chat 3

- validar provider ausente/incompatível retornando `UNAVAILABLE_NODE` sem gasto de ponto;
- validar rank legado mascarado;
- validar dependências A0051+ continuando insatisfeitas enquanto A0050 estiver indisponível;
- validar mainhand/offhand, multiplayer e dedicated server quando houver provider futuro.

## Testes exigidos

- provider ausente → A0050 não comprável e nenhum ponto gasto;
- provider incompatível → indisponível;
- provider futuro presente → rank 1/2/3 apenas com redução real de tempo de recarga/preparo +2/+4/+6%;
- dependências posteriores permanecem insatisfeitas enquanto A0050 estiver indisponível;
- nenhum efeito em projectile speed/dano/Stamina;
- mainhand/offhand, multiplayer e dedicated server.

## Fechamento Chat 2 — 2026-09-01

O fail-closed agora é enforceado na compra e no runtime efetivo. O Chat 2 não criou efeito alternativo e não executou a bateria final do Chat 3.

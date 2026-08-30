# 08.18 — MineColonies Society Adapter

## Goal
Usar MineColonies como provider da colônia física e sinais sociais reais, mantendo o Narrative Core como autoridade das camadas políticas/narrativas novas.

## Baseline
Auditar a versão exata da modlist antes de implementar. Em 2026-08-30, a release pública estável 1.21.1 observada era `1.1.1368`; existem snapshots posteriores. Não fazer downgrade automático do pack.

## Entregas
- [ ] Optional adapter e classloading seguro.
- [ ] Resolver settlement identity <-> colony identity persistente.
- [ ] Query bounded de owner/permissions/population/citizens/buildings/raids/deaths/happiness somente onde API real permitir.
- [ ] Converter apenas eventos semanticamente relevantes em Narrative events.
- [ ] Citizen UUID/identity como witness target quando estável e suportado.
- [ ] Não duplicar jobs, requests, buildings, raids, guard AI, citizen happiness ou lifecycle.
- [ ] Public opinion do Narrative Core permanece estado distinto.
- [ ] Colony deletion/abandonment/move/migration handling.
- [ ] Claims/protection respeitados por world effects; Narrative Core não contorna permissões.
- [ ] Dedicated-server e colônia com múltiplos players.

## Estratégia de população
Para grandes colônias, não manter simulação social pesada por cidadão. Usar:
- atores narrativos individuais somente para cidadãos relevantes;
- cohorts/aggregates para população comum;
- witnesses individuais apenas quando um evento real os seleciona.

## Acceptance
Duas colônias MineColonies com perfis narrativos distintos reagem de modo diferente à presença de Severin sem alterar a lógica interna do MineColonies. Remover MineColonies não impede o jar base de iniciar.
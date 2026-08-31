# A0150 — Estabilidade de Conjuração

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
Notable ARCANE/CASTING, 1 rank, 2 PP. A primeira interrupção elegível de um cast poderia ser convertida uma vez mediante tempo restante ×1,20 + débito atômico `quantize_up(8% × mana_paga)` da mesma action_id; cooldown interno 10 s; fisiologia severa bloqueia.

## Boundary
Iron's 3.16.3 expõe spell_id/mana cost (`SpellPreCastEvent`/`SpellOnCastEvent`) e cancelamento server-side interno (`CancelCastPacket.cancelCast`), mas a auditoria NÃO provou evento público/pre-cancelamento seguro para transformar a interrupção antes de `onServerCastComplete(..., true)`. Capability requerida: `CAST_INTERRUPTION_CONVERTIBLE` via adapter versionado futuro.

## Exclusões
Não interceptar após o cancelamento, não usar custo nominal, Source/Soul Energy/HP como mana, não mutar temperatura e não ignorar fome/sede/extremo térmico read-only.

## Chat 2
Manter compra desabilitada e allocation legado = 0 PP para Gate B. Não implementar A0150 até existir boundary versionado que prove motivo de interrupção, cast ativo, tempo restante e veto seguro ao cancelamento; então manter receipt MANA single-claim e débito extra atômico.
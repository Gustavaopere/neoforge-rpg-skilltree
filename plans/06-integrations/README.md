# 06 — Integrations

Integrar mods externos por adapters opcionais pequenos, testáveis e semanticamente corretos, sem acoplar o RPG Core às APIs externas.

Ordem histórica: contrato de adapters → Epic Fight → Iron's → Ars → Goety/Malum/Eidolon → morphs → Apothic Attributes → Create/AE2/Oritech → matriz de integração.

Subplanos adicionais:

- `✅-10-minecolonies-battle-mages.md` — **CONCLUÍDO** pela PR #288. Integra cidadãos/guardas MineColonies a spellbooks reais do Iron's, mantendo MineColonies como authority do cidadão/guard AI e Iron's como authority de spells, `MagicData` e cast lifecycle. O livro real define integralmente o repertório; casts autônomos não concedem Mastery ao jogador. O contrato original permanece preservado em `archive/10-minecolonies-battle-mages-plan.md`.
- `11-minecolonies-economy.md` — **V1 IMPLEMENTADA E VALIDADA NA PR #415; AGUARDANDO SYNC/CI FINAL/MERGE**. O runtime entregue é server-authoritative por colônia, com ledger virtual canônico, `MINT`/`RETIRE`, persistência, deduplicação/replay protection, capacidade econômica `Q` derivada read-only do MineColonies, inflação/deflação, snapshots/preflight, authority server-side e adapters provider-free/provider-present. Construção/upgrade monetário e UI custom no Town Hall permanecem fail-closed por ausência de hook/extensibility point público seguro; Banco físico, salários, impostos completos, comércio/câmbio e moeda física ficam fora da V1. Modlist/Notion atuais usam MineColonies `1.1.1376-1.21.1-snapshot`; a lane provider validada usa a build auditada `1.1.1375`, e a execução binária do JAR exato 1.1.1376 permanece pendência explícita de evidência. Ver `11-minecolonies-economy-reconciliation-1.1.1376.md` para o contrato efetivamente entregue e a evidência de validação.

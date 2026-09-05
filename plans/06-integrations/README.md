# 06 — Integrations

Integrar mods externos por adapters opcionais pequenos, testáveis e semanticamente corretos, sem acoplar o RPG Core às APIs externas.

Ordem histórica: contrato de adapters → Epic Fight → Iron's → Ars → Goety/Malum/Eidolon → morphs → Apothic Attributes → Create/AE2/Oritech → matriz de integração.

Subplanos adicionais abertos:

- `10-minecolonies-battle-mages.md` — integra cidadãos/guardas MineColonies a spellbooks reais do Iron's, mantendo MineColonies como authority do cidadão/guard AI e Iron's como authority de spells, `MagicData` e cast lifecycle. O livro real define integralmente o repertório; casts autônomos não concedem Mastery ao jogador.
- `11-minecolonies-economy.md` — adiciona uma economia server-authoritative por colônia, com moeda própria, Tesouro, oferta monetária, capacidade econômica, inflação/deflação, impostos e custos econômicos integrados sem substituir materiais/logística nativos. Town Hall inicia como interface administrativa; Banco/Tesouro físico, salários e câmbio ficam em fases posteriores. A implementação depende de auditoria da API real do MineColonies `1.1.1375-1.21.1-snapshot` e deve permanecer fail-closed onde não houver hook transacional seguro.

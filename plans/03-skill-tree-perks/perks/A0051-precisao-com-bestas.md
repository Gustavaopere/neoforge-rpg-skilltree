# A0051 — Precisão com Bestas

## Estado

- **Design:** APROVADO após reauditoria provider→árvore.
- **Notion:** `3c569db9-f0db-8135-903d-db954d9f8087`.
- **Runtime:** caminho crítico físico presente; implementação deve ser revalidada junto à ledger CROSSBOW canônica e ao producer de Mastery necessário para tornar A0049 adquirível.

## Contrato canônico

- A0049 ≥1 + gateway `epic_crossbow`.
- +3% de chance crítica por rank, máximo +9%.
- Somente projétil físico CROSSBOW causalmente atribuído ao `ServerPlayer`.
- Vanilla `CrossbowItem`/subclasse é classificação segura; externos exigem capability/categoria provider-native ou mapping versionado explícito.
- Uma única resolução crítica/root action; Apothic, quando usado como backend, participa da mesma resolução.
- `ARCANE_BACKLASH`, spell projectiles, ricochetes/derivados, fake players e projéteis de allies/bodyguards Mobstein são inelegíveis.

## Auditoria técnica

O adapter de projéteis já classifica vanilla por `CrossbowItem`, preserva owner real e correlaciona uma resolução crítica ao projectile/root. O antigo fallback documental `rpgskilltree:crossbows` foi removido do Notion: tag paralela não governada não é classificador canônico.

A0051 herda integralmente a alcançabilidade de A0049. Portanto, corrigir somente o namespace `combat:crossbow` ↔ `epicfight:crossbow` não basta: enquanto `P-A0049-01` não existir no runtime, um jogador novo não possui producer finite-discovery para alcançar a Mastery CROSSBOW 60 exigida por A0049 e não chega legitimamente a A0051 nem aos descendentes A0052–A0054.

### Provider→árvore

- RPG Skill Tree: resolver crítico e dedup são authority canônica.
- Black Arcana: Backlash terminal não é projectile/root CROSSBOW.
- Enshrouded/Volcanoes: não classificam projétil nem crítico.
- Mobstein 5.4.4: projectile de companion permanece Mobstein-owned; ataque direto do jogador contra entidade Mobstein continua cobertura universal.

## Pendências para Chat 2

- **Herdada P-A0049-01:** implementar producer finite-discovery da Mastery canônica `epicfight:crossbow`, deduplicado por tipo hostil inédito; 6 tipos → 60, 8 → 80. Sem isso A0049/A0051+ não são alcançáveis por progressão legítima.
- **Herdada P-A0049-02:** reconciliar `combat:crossbow` do architecture catalog com a ledger canônica `epicfight:crossbow`; não criar duas Masteries.
- Adicionar/regredir teste provider-present/absent para classificação CROSSBOW, produção de Mastery, gate A0049→A0051 e resolução crítica única.

## Notion

`Hook`, `Fallback` e `Regra` corrigidos em 2026-08-30; re-fetch PASS. O review da PR #249 adicionou somente blockers runtime herdados, sem necessidade de nova mutação de design nesta página.

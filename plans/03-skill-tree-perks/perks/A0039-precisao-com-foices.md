# A0039 — Precisão com Foices

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-81a5-9f5f-e5b382c32741`.

## Contrato canônico

- A0037 ≥1.
- +3% chance crítica SCYTHE/rank, máximo +9%.
- Uma root action produz no máximo uma resolução crítica canônica.
- Backlash terminal/secundário e companion-owned damage são inelegíveis.

## Evidência runtime

- `NotionCombatPerkRules.criticalChanceBonus(SCYTHE)` mapeia A0039.
- `A0021A0040EpicFightHooks` usa o critical service/root action canônico para famílias não-DAGGER.
- A resolução SCYTHE foi endurecida para category/capability provider-native; não existe fallback por tag/nome/hoe.

## Provider→árvore

Black Arcana `ARCANE_BACKLASH` nunca entra no crítico; Mobstein companions não recebem autoria do dono. Volcanoes/Enshrouded não fornecem critical receipt MARTIAL.

## Fechamento Chat 2

A dependência da classificação SCYTHE insegura foi resolvida sem criar novo critical pipeline. Chat 3 deve revalidar deduplicação/provenance, uma resolução por root e coexistência com A0062/A0063. O Chat 2 não executou a bateria final.

# Capability delta — A0101–A0110

> Evidência intermediária da tranche inicial; o ciclo ativo foi ampliado para A0101–A0150 e terá delta consolidado próprio.

| Projeto | Capacidade/delta observado | Estado real | Cobertura/decisão |
|---|---|---|---|
| RPG Skill Tree | Correções concorrentes A0021–A0030, MasteryLaneCatalog e Compendium | Integrado em `main`; sem consumer novo para A0101–A0110 | Sem nova perk neste range; manter boundaries definidos pelo Chat 1 |
| Volcanoes | Nenhum avanço pertinente à tranche desde baseline anterior | Sem delta mecânico aplicável | Baseline preservado |
| Enshrouded | Áudio/partículas e ambience client-side | Client/presentation | NÃO DEVE SER INTEGRADO como perk mecânica |
| Black Arcana | Forecast server-authored/read-only de Arcane Resistance | Implementado; presentation/read-only | COBERTO POR SISTEMA UNIVERSAL/provider-native; não fundir com A0102 |

## Disposições

- Arcane Resistance e Corruption Resistance continuam provider-owned no Black Arcana.
- A0102 é resistência mágica genérica e usa classificação causal de DamageType; não lê forecast como authority.
- Massa de contraption/Aeronautics não representa encumbrance corporal e não habilita A0109.
- Nenhum delta observado autoriza remover fail-closed de A0107–A0110.

# Auditoria delta — Simply Swords stack — A0031–A0040

## Escopo

Quarto sublote exato da reauditoria A0001–A0050 contra Simply Swords 1.70.2, Simply More 1.3.0 ALPHA, Integrated Simply Swords 1.4.0, Simply Swords: Cataclysm 1.0.2 e Simply Tooltips 0.1.5.

## Regra transversal

- MACE/SCYTHE continuam provider-native/mapping explícito; não inferir por namespace, tipo Simply, aparência ou tooltip.
- Pernach/arma Simply More só entra no ramo de Maças quando a integração provar `MACE`.
- Scythe Simply só entra no ramo de Foices quando Epic Fight Compat provar `SCYTHE`.
- Implicits, Uniques, Runic Powers, Awakening, sockets/gems e traits Cataclysm permanecem provider-owned.
- Simply Tooltips é apresentação e não provider mecânico.

## Matriz A0031–A0040

| Perk | Disposição | Resultado |
|---|---|---|
| A0031 — Treino com Maças I | COBERTA POR SISTEMA UNIVERSAL | Pernach/externos só por `MACE` seguro; Simply More alpha não autoriza mapping heurístico. `P-A0031-01/-02` permanecem. |
| A0032 — Treino com Maças II | COBERTA POR SISTEMA UNIVERSAL | Cadência é Epic Fight; efeito provider-owned não cria segundo modificador RPG. |
| A0033 — Precisão com Maças | COBERTA POR SISTEMA UNIVERSAL | Uma única resolução crítica por root MACE; ability/debuff provider não gera segundo crítico. |
| A0034 — Trauma Contundente | COBERTA POR SISTEMA UNIVERSAL | Trauma continua RPG-owned por root MACE direto; debuff/Implicit externo não concede carga adicional. |
| A0035 — Armadura Fendida | CORRIGIDA | Debuff/armor reduction provider-native não é Trauma nem `Sundered` RPG; Pernach só se MACE seguro. Commit causal A0035 continua obrigatório. |
| A0036 — Quebra-Ossos | CORRIGIDA | Armadura Fendida deve preexistir no estado RPG; debuff Simply não satisfaz Sundered/heavy/Descompasso. |
| A0037 — Treino com Foices I | COBERTA POR SISTEMA UNIVERSAL | Scythe Simply só via `SCYTHE` segura; execute não classifica arma e não concede Mastery adicional. |
| A0038 — Treino com Foices II | COBERTA POR SISTEMA UNIVERSAL | Cadência continua Epic Fight; provider effects não são segundo multiplier RPG. |
| A0039 — Precisão com Foices | COBERTA POR SISTEMA UNIVERSAL | Uma resolução crítica por root SCYTHE; execute/ability não gera crítico paralelo. |
| A0040 — Marca da Ceifa | CORRIGIDA | Execute Simply é provider-owned: não aplica/duplica Marca nem cria root; marca preexistente ainda observa crossing server-side de vida. |

## Notion

- Mutadas: **A0035, A0036 e A0040**.
- Campos: `Provider/Mods`, `Hook`, `Fallback`, `Regra`.
- Re-fetch pós-escrita: **3/3 PASS**.
- A0031–A0034 e A0037–A0039 não exigiram mutação funcional; os contratos provider-native/direct-root já são suficientes.

## Pendências preservadas

- `P-A0031-01/-02`: classificação MACE sem tag paralela + Mastery anti-farm.
- `P-A0035-01/-02`: boss Witherstein + commit causal pós-hit.
- `P-A0036-01/-02/-03`: heavy receipt, aplicação de Descompasso e Sundered preexistente.
- `P-A0037-01/-02`: classificação SCYTHE segura + Mastery anti-farm.
- `P-A0040-01`: cleanup bounded de Marca em target unload/despawn.
- `P-SIMPLY-A0001-50-01`: acceptance provider-present final deve provar mappings MACE/SCYTHE e ausência de double-root/double-dip.
- `P-SIMPLY-ALPHA-01`: comportamento Simply More alpha não comprovado permanece fail-closed.

## Resultado

**A0031–A0040: 10/10 reauditoradas; 3 contratos corrigidos; nenhuma nova perk necessária.** O stack Simply é conteúdo de arma/efeitos provider-owned, e a árvore continua consumindo somente as famílias/receipts canônicos.
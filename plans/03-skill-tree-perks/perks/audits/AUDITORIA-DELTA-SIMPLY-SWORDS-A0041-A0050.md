# Auditoria delta — Simply Swords stack — A0041–A0050

## Escopo

Quinto e último sublote exato da reauditoria A0001–A0050 contra Simply Swords 1.70.2, Simply More 1.3.0 ALPHA, Integrated Simply Swords 1.4.0, Simply Swords: Cataclysm 1.0.2 e Simply Tooltips 0.1.5.

## Matriz A0041–A0050

| Perk | Disposição | Resultado |
|---|---|---|
| A0041 — Corte de Ceifa | CORRIGIDA | Scythe Simply só via `SCYTHE`; execute provider-owned não cria segundo Corte/consumo. Pode coexistir no mesmo root direto sem reroll/reexecução. |
| A0042 — Colheita de Batalha | CORRIGIDA | Execute só pode participar do `eligible_kill` se a morte for inequivocamente correlacionada ao mesmo root direto SCYTHE e aprovada uma única vez pelo anti-abuso; evento derivado/ownership isolado é fail-closed. |
| A0043 — Treino com Arcos I | NÃO DEVE SER INTEGRADO | O stack auditado não fornece família BOW; nada do Simply classifica ou substitui arco/projétil físico. |
| A0044 — Treino com Arcos II | NÃO DEVE SER INTEGRADO | Sem draw/preparation provider BOW no stack Simply; manter availability/fail-closed existente. |
| A0045 — Precisão com Arcos | NÃO DEVE SER INTEGRADO | Implicits/Uniques Simply não são projéteis BOW nem critical receipt de arco. |
| A0046 — Foco de Mira | NÃO DEVE SER INTEGRADO | Stack Simply não fornece Foco corporal/aim receipt BOW; não converter attack speed, tooltip ou ability em foco. |
| A0047 — Distância Dominada | NÃO DEVE SER INTEGRADO | Stack Simply não fornece projectile speed BOW semântico; não reutilizar Chakram/ability/projectile Simply como arco. |
| A0048 — Tiro Preparado | NÃO DEVE SER INTEGRADO | Nenhum estado Simply substitui charge/preparation BOW ou Mastery do ramo. |
| A0049 — Treino com Bestas I | NÃO DEVE SER INTEGRADO | Stack auditado não fornece CROSSBOW; Mastery/launch provenance permanecem do pipeline de besta. |
| A0050 — Treino com Bestas II | NÃO DEVE SER INTEGRADO | Nenhum reload/preparation binding CROSSBOW é fornecido pelos cinco mods novos; availability fail-closed permanece. |

## A0041/A0042 — execute da Scythe

O execute Implicit do Simply Swords pertence ao provider. Ele não é automaticamente um novo golpe SCYTHE. Para A0041, o root direto pode receber o efeito RPG e o provider pode resolver seu execute separadamente, ambos uma única vez. Para A0042, uma morte causada pelo execute só pode ser submetida ao `eligible_kill` quando a integração comprovar que ela pertence ao mesmo root direto SCYTHE do jogador; um callback/derived damage separado sem correlação não recebe autoria MARTIAL.

## Arcos e Bestas

A taxonomia canônica auditada do stack Simply não acrescenta Bow/Crossbow. Portanto A0043–A0050 não recebem `Provider/Mods`, hooks ou fallbacks Simply. Fazer isso criaria integração artificial e violaria provider-native first.

## Notion

- Mutadas: **A0041 e A0042**.
- Campos: `Provider/Mods`, `Hook`, `Fallback`, `Regra`.
- Re-fetch pós-escrita: **2/2 PASS**.
- A0043–A0050: nenhuma mutação necessária; disposição explícita desta auditoria é `NÃO DEVE SER INTEGRADO`.

## Pendências preservadas

- `P-A0041-01`: reservation→commit da Marca pós-hit.
- `P-A0042-01/-02`: `eligible_kill` anti-abuso + unificação/dedup de producers.
- Pendências BOW/CROSSBOW A0043–A0050 permanecem inalteradas; os mods Simply não oferecem os receipts que faltam.
- `P-SIMPLY-A0001-50-01`: acceptance provider-present final deve cobrir SWORD/AXE/SPEAR/DAGGER/HAMMER/MACE/SCYTHE conforme mappings reais e provar ausência de double-root/double-dip.
- `P-SIMPLY-ALPHA-01`: comportamento específico não comprovado de Simply More alpha continua fail-closed.

## Resultado

**A0041–A0050: 10/10 reauditoradas; 2 contratos corrigidos; A0043–A0050 explicitamente não integram o stack Simply.** Com este sublote, a reauditoria dirigida A0001–A0050 está completa no design.
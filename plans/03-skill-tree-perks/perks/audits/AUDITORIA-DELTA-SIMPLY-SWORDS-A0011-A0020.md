# Auditoria delta — Simply Swords stack — A0011–A0020

## Escopo

Reauditoria dirigida das perks A0011–A0020 contra o stack instalado documentado no guia canônico de Gameplay:

- Simply Swords `1.70.2`;
- Simply More `1.3.0 ALPHA`;
- Integrated Simply Swords `1.4.0`;
- Simply Swords: Cataclysm `1.0.2`;
- Simply Tooltips `0.1.5`;
- Epic Fight Compat `1.1.0` como adapter de preset/capability, não como owner de Implicits/Uniques/Awakening.

## Regra transversal

O stack Simply não cria árvore paralela nem perks nominais. A cobertura é universal por família Epic Fight quando a capability server-side resolve inequivocamente `AXE`, `SPEAR` ou `DAGGER`. Namespace, nome, material, tooltip, alcance visual, Implicit e animação não classificam a arma. Implicits, Runic Powers, Awakening, sockets/gem powers, Unique abilities e traits Cataclysm permanecem provider-owned; hits/procs derivados não viram novos `rootActionId` MARTIAL.

Simply Tooltips é apresentação client-side e **NÃO DEVE SER INTEGRADO** como provider mecânico.

## Matriz A0011–A0020

| Perk | Disposição | Resultado da reauditoria |
|---|---|---|
| A0011 — Ruptura de Guarda | COBERTA POR SISTEMA UNIVERSAL | Armas Simply classificadas `AXE` podem usar a perk no root direto. Bleed/ability/trait não gastam Fúria nem constituem nova Ruptura. Implicit de armor-ignore/sunder de outra família não é receipt de guarda/postura. |
| A0012 — Frenesi do Saqueador | COBERTA POR SISTEMA UNIVERSAL | Root `AXE` direto pode receber Frenesi. Procs Simply não pagam novamente CORE/exhaustion, não ganham pacote ofensivo separado e não alteram Fúria. Cold Sweat continua authority corporal. |
| A0013 — Treino com Lanças I | COBERTA POR SISTEMA UNIVERSAL | Spear/Great Spear/Glaive/Halberd/Lance só entram se Epic Fight Compat resolver `SPEAR`; não mapear pelo tipo Simply diretamente. |
| A0014 — Treino com Lanças II | COBERTA POR SISTEMA UNIVERSAL | Cadência vem do Epic Fight. Implicit/Unique que altere attack speed não é recalculado nem retriggerado pela perk. |
| A0015 — Precisão com Lanças | COBERTA POR SISTEMA UNIVERSAL | Uma única resolução crítica por root `SPEAR`; armor-ignore/bleed/ability damage do provider não é novo crítico MARTIAL. |
| A0016 — Distância Ideal | COBERTA POR SISTEMA UNIVERSAL | Alcance efetivo continua `entityInteractionRange + capability.getReach()` do Epic Fight. Tooltip/weapon type/alcance nominal do Simply não substituem receipt server-side. |
| A0017 — Interceptação | COBERTA POR SISTEMA UNIVERSAL | Implicit de armor-ignore de Spear não é guard-pressure, deslocamento ofensivo nem receipt de avanço. `P-A0017-01` permanece fail-closed. |
| A0018 — Linha de Interceptação | COBERTA POR SISTEMA UNIVERSAL | Consumidores/lockout continuam por root SPEAR direto; Implicits e ability hits não financiam nem consomem cargas separadamente. |
| A0019 — Treino com Adagas I | COBERTA POR SISTEMA UNIVERSAL | Dagger/Sai/armas Simply More só entram se Epic Fight Compat resolver `DAGGER`; backstab Implicit não classifica a arma. |
| A0020 — Treino com Adagas II | COBERTA POR SISTEMA UNIVERSAL | Cadência usa o evento Epic Fight; bônus de attack speed/Implicit/Unique do provider compõe apenas pelo valor efetivo e não vira segundo modificador RPG. |

## Simply More alpha

O guia canônico confirma tipos adicionais, mas a build instalada é `1.3.0 ALPHA`. Qualquer Unique ou efeito específico não comprovado no artifact instalado permanece `FAIL-CLOSED`; a existência do namespace/mod não autoriza inferir família, passive, active ability ou trigger.

## Notion

**Nenhuma mutação necessária neste sublote.** Os contratos atuais já exigem família provider-native, autoria direta, deduplicação e fail-closed suficientes para a chegada do stack Simply. A integração nova é de cobertura/boundary, não de efeito da perk.

## Handoff

- `P-SIMPLY-A0001-50-01`: acceptance provider-present com os JARs exatos deve provar que armas Simply relevantes recebem a categoria Epic Fight esperada e que procs/abilities/Implicits não geram segundo root MARTIAL.
- `P-SIMPLY-ALPHA-01`: efeitos específicos de Simply More 1.3.0 ALPHA permanecem fail-closed até inspeção do artifact/contrato real.

## Resultado

**A0011–A0020: 10/10 reauditoradas; nenhuma precisa de uma nova perk nem de um efeito Simply específico.** O stack é coberto universalmente pela família Epic Fight quando disponível, com ownership preservado.
# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0030** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

A fonte canônica de design permanece o Notion. `IMPLEMENTAÇÃO CONFIRMADA` só é definitiva após contrato implementado, testes pertinentes, PR verde e merge em `main`.

| Código | Perk | Design | Estado técnico auditado | Pendências bloqueantes |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore VALIDADA EM CI na PR #234 | nenhuma |
| A0002 | Treino com Espadas II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore VALIDADA EM CI na PR #234 | nenhuma |
| A0003 | Precisão com Espadas | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de provenance/crítico VALIDADA EM CI na PR #234 | nenhuma |
| A0004 | Ritmo do Duelista | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de autoria/recursos VALIDADA EM CI na PR #234 | nenhuma |
| A0005 | Abertura de Guarda | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de defesa física VALIDADA EM CI na PR #234 | nenhuma |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de provenance/defesa VALIDADA EM CI na PR #234 | nenhuma |
| A0007 | Treino com Machados I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore VALIDADA EM CI na PR #234 | nenhuma |
| A0008 | Treino com Machados II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore VALIDADA EM CI na PR #234 | nenhuma |
| A0009 | Precisão com Machados | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de provenance/crítico VALIDADA EM CI na PR #234 | nenhuma |
| A0010 | Pressão do Carrasco | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de Fúria/autoria VALIDADA EM CI na PR #234 | nenhuma |
| A0011 | Ruptura de Guarda | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234; confirmação definitiva após merge | nenhuma |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234; Cold Sweat 2.4.2 exato + diagnóstico bounded | nenhuma |
| A0013 | Treino com Lanças I | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234; classificação provider-native/fail-closed | nenhuma |
| A0014 | Treino com Lanças II | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234 via `ModifyAttackSpeedEvent` | nenhuma |
| A0015 | Precisão com Lanças | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234; crítico canônico direto | nenhuma |
| A0016 | Distância Ideal | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234; cargas somente por ação direta do jogador | nenhuma |
| A0017 | Interceptação | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI NO FALLBACK CANÔNICO na PR #234 | nenhuma bloqueante; P-A0017-01 permanece fail-closed correto |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234; crossing/janela/consumo causais | nenhuma |
| A0019 | Treino com Adagas I | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234; classificação provider-native/fail-closed | nenhuma |
| A0020 | Treino com Adagas II | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #234 via `ModifyAttackSpeedEvent` | nenhuma |
| A0021 | Precisão com Adagas | APROVADO + boundary | Presente; crítico canônico direto | nenhuma exclusiva |
| A0022 | Ritmo das Sombras | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL | `P-A0022-01`: −2 Fluxo por stagger forte; `P-A0022-02`: fallback geométrico sem DodgeEvent; `P-A0022-03`: idle decay sem alvo hostil vivo |
| A0023 | Ataque ao Ponto Cego | APROVADO + boundary | Presente; orientação server-side | nenhuma exclusiva |
| A0024 | Maestria de Adagas — Dança das Sombras | APROVADO + boundary | Presente com fallback canônico de stamina | depende de A0022 não assumir rota geométrica ausente |
| A0025 | Treino com Martelos I | APROVADO após correção | NÃO CONFIRMADA | `P-A0025-01`: remover tag HAMMER paralela; `P-A0025-02`: Mastery anti-farm |
| A0026 | Treino com Martelos II | APROVADO | Presente via attack-speed, condicionado à família HAMMER segura | depende de `P-A0025-01` |
| A0027 | Precisão com Martelos | APROVADO + boundary | Presente no resolver crítico | depende de `P-A0025-01` |
| A0028 | Abalo Crescente | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL; Abalo é rastreado | `P-A0028-01`: guard pressure receipt ausente |
| A0029 | Quebra de Postura | APROVADO + boundary | NÃO CONFIRMADA | `P-A0029-01`: heavy receipt ausente; stamina refund é fallback opcional |
| A0030 | Maestria de Martelos — Golpe Demolidor | APROVADO + boundary | NÃO CONFIRMADA | `P-A0030-01`: guard-break caller/receipt + heavy receipt ausentes |

## Regras sistêmicas vigentes

- **Provider-native first:** famílias de arma desconhecidas ficam `FAIL-CLOSED`; tags paralelas não versionadas não são classificação canônica.
- **Versões exatas:** Epic Fight MARTIAL é registrado somente para `21.17.3.1`; A0012 aceita Cold Sweat somente em `2.4.2`.
- **Crítico:** uma única resolução/root action canônica; `ARCANE_BACKLASH` e companion-owned damage não entram como ataque direto do jogador.
- **Provenance em profundidade:** `A0001A0020CombatPolicy` retorna pacote totalmente neutro, inclusive chance crítica zero, quando `direct=false` ou `hostile=false`.
- **Mastery:** não pode ser obtida por spam de dano. Categorias auditadas usam milestones/descobertas persistentes.
- **Martelos:** `epicfight:heavy` passa a usar +10 uma única vez por tipo hostil inédito, persistido em `DiscoveryProgress`; gate 70 = 7 tipos, terminal 80 = 8 tipos.
- **Recursos:** Ímpeto, Fúria, Controle de Distância, Fluxo, Abalo e janelas são server-authoritative, deduplicados e não recebem autoria de companions.
- **Guarda/postura/heavy:** apenas receipts provider-native seguros; dano alto, animação, Armor, velocidade e stagger genérico não substituem.
- **Black Arcana:** `ARCANE_BACKLASH` permanece terminal e não crita/proca/concede Mastery/recursos nem abre/consome janelas MARTIAL.
- **Enshrouded:** Shroud/Exposure/Madness/Flame/Story não são guarda, CORE, stamina, dodge, heavy ou guard-break.
- **Volcanoes:** capacidades ambientais/geológicas permanecem em seus ramos próprios; A0012 não reaplica/deduz calor ambiental Volcanoes.
- **Mobstein 5.4.4:** allies/bodyguards ressuscitados são provider-owned e não herdam autoria MARTIAL do dono.
- **NeoVitae:** removido/ausente.

## Ciclos fechados anteriores

### A0001–A0010

- Chat 1: design fechado.
- Chat 2: PR #221 mergeada; merge `d7aa65bf37bbe284cac5d92818ef0a1a23ffd14b`; implementação confirmada.
- Retroauditoria: `AUDITORIA-RETROATIVA-PROVIDERS-A0001-A0010.md`.

### A0011–A0020

- Design/retroauditoria fechados.
- Arquivo: `AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md`.
- Chat 2: implementação/revalidação técnica documentada nos 10 dossiês e em `AUDITORIA-A0001-A0020-REVALIDACAO-IMPLEMENTACAO-CHAT2.md`; aguardando merge da PR #234 para confirmação definitiva.

### Retroauditoria combinada A0001–A0020

- PR #233 — `docs(perks): retroaudit provider integration A0001-A0020` — **MERGEADA**.
- Merge/main confirmado: `0087ef7e513664454b3d54cb70a9c3f24ec46e84`.
- Chat 2 PR #234 corrige/revalida os boundaries runtime exigidos pela retroauditoria.
- CI de código #2130: JUnit, NeoForge GameTests, build, JAR e dedicated-server smoke verdes; 10/10 workflows verdes antes da sincronização documental.
- P-A0017-01 continua não bloqueante e fail-closed correto.

## Chat 1 — lote exato A0021–A0030

**Estado:** `LOTE FECHADO NO DESIGN`.

- **INÍCIO:** A0021.
- **FIM:** A0030.
- **Quantidade:** 10 perks consecutivas.
- **Notion fetch fresco:** 10/10.
- **Notion alterado:** A0021, A0022, A0023, A0024, A0025, A0027, A0028, A0029, A0030.
- **Re-fetch pós-escrita:** 9/9 PASS em 2026-08-30.
- **A0026:** sem drift; nenhuma mutação cosmética.
- **Nove eixos:** PASS no design das 10 perks.
- **18 critérios técnicos:** PASS/N/A justificado no design; gaps runtime catalogados para Chat 2.
- **Arquivo canônico:** `AUDITORIA-RETROATIVA-PROVIDERS-A0021-A0030.md`.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0031+:** não iniciado.

### Pendências para Chat 2

1. `P-A0022-01` — ligar perda de 2 Fluxo a `ON_STUNNED` forte/hostil.
2. `P-A0022-02` — implementar a rota geométrica canônica de reposicionamento (≥1,5 blocos + ≥60°) sem depender de `DodgeEvent`, preservando exclusões de câmera/teleport/knockback.
3. `P-A0022-03` — aplicar idle decay de Fluxo após 3 s sem deslocamento relevante mesmo sem alvo hostil vivo; alvo não é condição do lifecycle.
4. `P-A0025-01` — remover/desativar classificação HAMMER por tag paralela.
5. `P-A0025-02` — substituir 3 XP/hit por `DiscoveryProgress` +10 por tipo hostil inédito.
6. `P-A0028-01` — integrar guard/posture pressure provider-native ou manter componente fail-closed.
7. `P-A0029-01` — integrar heavy receipt seguro; sem heurística.
8. `P-A0030-01` — integrar guard-break causal + heavy receipt; sem isso capstone permanece fail-closed.

**Próxima etapa do lote A0021–A0030:** pertence ao ciclo seguinte do Chat 2 e não é iniciada pela PR #234.

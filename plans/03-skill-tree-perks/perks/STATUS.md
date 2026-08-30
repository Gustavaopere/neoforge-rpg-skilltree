# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0020** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

A fonte canônica de design permanece o Notion. Este índice descreve o estado após as correções desta auditoria; `IMPLEMENTAÇÃO CONFIRMADA` só se torna definitivo quando a PR correspondente estiver mergeada na `main` com CI verde.

| Código | Perk | Design | Estado técnico auditado | Pendências bloqueantes |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO após reauditoria | Presente; classificação provider-native e fail-closed | nenhuma |
| A0002 | Treino com Espadas II | APROVADO | Presente | nenhuma |
| A0003 | Precisão com Espadas | APROVADO | Presente; crítico no pipeline canônico único | nenhuma |
| A0004 | Ritmo do Duelista | APROVADO | Presente; hit, dodge, miss, decay e stagger forte provider-native | nenhuma |
| A0005 | Abertura de Guarda | APROVADO após correção | Presente; defesa nativa ou fallback estrito de penetração por defesa física comprovável | nenhuma |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO | Presente; defesa técnica confirmada, janela, cooldown e dedup | nenhuma |
| A0007 | Treino com Machados I | APROVADO após reauditoria | Presente; classificação provider-native e fail-closed | nenhuma |
| A0008 | Treino com Machados II | APROVADO | Presente | nenhuma |
| A0009 | Precisão com Machados | APROVADO | Presente; crítico no pipeline canônico único | nenhuma |
| A0010 | Pressão do Carrasco | APROVADO após reauditoria | Presente no receipt server-authoritative do Epic Fight; demais rotas fail-closed | nenhuma |
| A0011 | Ruptura de Guarda | APROVADO após correção | Presente; condição heurística de “alvo pesado” removida do design | nenhuma |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO após correção + re-fetch | Implementado com transação PRE: CORE pago antes de exhaustion/bônus/pico; falha deixa o evento fail-closed | nenhuma, condicionado à CI/merge desta PR |
| A0013 | Treino com Lanças I | APROVADO após reauditoria | Presente; classificação provider-native e fail-closed | nenhuma |
| A0014 | Treino com Lanças II | APROVADO | Presente via `ModifyAttackSpeedEvent` | nenhuma |
| A0015 | Precisão com Lanças | APROVADO | Presente; crítico no pipeline canônico único | nenhuma |
| A0016 | Distância Ideal | APROVADO | Presente; alcance, hit, miss, expiração e stagger forte provider-native | nenhuma |
| A0017 | Interceptação | APROVADO | Presente em fallback canônico | nenhuma; redução de deslocamento permanece deliberadamente omitida sem receipt ofensivo provider-native |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO | Presente; crossing, consumo, janela e lockout por alvo | nenhuma |
| A0019 | Treino com Adagas I | APROVADO após reauditoria | Presente; classificação provider-native e fail-closed | nenhuma |
| A0020 | Treino com Adagas II | APROVADO | Presente via `ModifyAttackSpeedEvent` | nenhuma |

## Correções sistêmicas da reauditoria

- **Critérios versionados:** cópia integral dos critérios canônicos adicionada à pasta dos dossiês.
- **Provider-native first:** os fallbacks fictícios `rpgskilltree:swords`, `rpgskilltree:axes`, `rpgskilltree:spears` e `rpgskilltree:daggers` foram removidos do design canônico. Ausência de classificação segura agora é `FAIL-CLOSED`.
- **A0004/A0016:** `EpicFightEventHooks.Entity.ON_STUNNED` fornece o receipt server-side; apenas `LONG`, `KNOCKDOWN` e `NEUTRALIZE` são aceitos como stagger forte, com fonte hostil validada.
- **A0005:** guarda/postura observável continua sendo a rota principal; quando ela não é observável, somente defesa física server-side comprovável autoriza penetração-only.
- **A0010:** o fallback genérico sem receipt seguro foi removido; tentativa de ataque, animação ou classificação heurística não geram Fúria.
- **A0011:** removida do design a condição contraditória de “alvo classificado como pesado”.
- **A0012:** o custo +1,5 `CORE` é pré-condição no mesmo `DELIVER_DAMAGE_PRE`; exhaustion, bônus e gasto do pico só ocorrem depois do sucesso da escrita CORE. Thirst Was Reclaimed não participa deste contrato.
- **Mastery Epic Fight:** hits repetidos deixaram de conceder Mastery. Categorias de arma usam tipos hostis inéditos; `guard` também usa tipos hostis inéditos em contexto real de skill de guarda pagável, +10 por tipo, tornando gates 60/80 alcançáveis com 6/8 descobertas sem spam do mesmo alvo/tipo. O ledger continua sendo `DiscoveryProgress`.

## Evidência comum

- `NotionCombatPerkRules` — coeficientes, thresholds e durações.
- `A0001A0020CombatPolicy` — política provider-independent, consumo de recursos e deduplicação.
- `NotionCombatPerkState` — Ímpeto, Fúria, Controle de Distância, janelas, lockouts e Queda de Ritmo.
- `A0001A0020CriticalService` — resolução crítica única.
- `A0001A0020EpicFightHooks` — hits PRE/POST, transação corporal A0012, attack speed, dodge, miss, stagger, alcance e tick server-side.
- `ColdSweatFrenzyBridge` — integração fail-closed com Cold Sweat 2.4.2 `Temperature.Trait.CORE`.
- `EpicFightProgressionHooks` + `MasteryPolicies` — Mastery baseada em milestones persistentes e alcançáveis.
- `A0001A0020CombatPolicyTest` e `EpicFightDepthPolicyTest` — regressões dos contratos corrigidos.

A matriz detalhada dos nove eixos está em `AUDITORIA-A0001-A0020.md`.
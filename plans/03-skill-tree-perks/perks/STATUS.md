# Status dos Dossiês de Perks

Referência técnica auditada para o segundo lote: `main` em `7f90af76c2b69574378d7f3f1d292e862ccdd6f9`.

| Código | Perk | Design | Código em `main` | Pendências conhecidas |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO | Presente | fallback `rpgskilltree:swords` não localizado |
| A0002 | Treino com Espadas II | APROVADO | Presente | nenhuma específica identificada |
| A0003 | Precisão com Espadas | APROVADO | Presente | nenhuma específica identificada |
| A0004 | Ritmo do Duelista | APROVADO | Presente | perda por stagger pesado não conectada a adapter localizado |
| A0005 | Abertura de Guarda | APROVADO | Presente | fallback de penetração sem guarda/postura não está demonstrado pelo policy atual |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO | Presente | sem blocker; cobertura confirmada por esquiva, outros tipos de defesa não evidenciados |
| A0007 | Treino com Machados I | APROVADO | Presente | fallback `rpgskilltree:axes` não localizado |
| A0008 | Treino com Machados II | APROVADO | Presente | nenhuma específica identificada |
| A0009 | Precisão com Machados | APROVADO | Presente | nenhuma específica identificada |
| A0010 | Pressão do Carrasco | APROVADO | Presente | fallback genérico sem evento Epic Fight não localizado |
| A0011 | Ruptura de Guarda | APROVADO | Presente parcial | elegibilidade canônica de alvo classificado como pesado não representada |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO | Contrato/fail-closed | bridge causal de Frenesi, golpe pesado, Queda de Ritmo e adapter hídrico opcional ausentes |
| A0013 | Treino com Lanças I | APROVADO | Presente | fallback `rpgskilltree:spears` não localizado |
| A0014 | Treino com Lanças II | APROVADO | Presente | nenhuma específica identificada |
| A0015 | Precisão com Lanças | APROVADO | Presente | nenhuma específica identificada |
| A0016 | Distância Ideal | APROVADO | Presente parcial | perda por stagger pesado não conectada a adapter localizado |
| A0017 | Interceptação | APROVADO | Presente em fallback canônico | redução de deslocamento ofensivo omitida até existir receipt provider-native seguro |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO | Presente | nenhuma específica identificada |
| A0019 | Treino com Adagas I | APROVADO | Presente | fallback `rpgskilltree:daggers` não localizado |
| A0020 | Treino com Adagas II | APROVADO | Presente | nenhuma específica identificada |

## Evidência comum — A0001–A0010

- `NotionCombatPerkRules` contém os coeficientes de dano, ritmo, crítico, Ímpeto, Abertura e Fúria.
- `A0001A0020CombatPolicy` contém a política server-authoritative e deduplicação dos efeitos stateful.
- `A0001A0020EpicFightHooks` conecta `DELIVER_DAMAGE_PRE`, `DELIVER_DAMAGE_POST`, `MODIFY_ATTACK_SPEED`, `ON_DODGE`, `ATTACK_PHASE_END` e tick do Epic Fight 21.17.3.1.
- `A0001A0020CriticalService` implementa resolução crítica canônica.
- Existem testes `A0001A0020NotionContractTest`, `A0001A0020CombatPolicyTest` e `A0001A0020CriticalServiceTest`.

## Evidência comum — A0011–A0020

- O Catálogo Mestre foi re-fetched individualmente para A0011–A0020 antes da escrita dos dossiês.
- `NotionCombatPerkCatalog` materializa dependências/hook contracts de Ruptura, Frenesi, lanças e adagas.
- `CombatPerkTreeModel` reproduz as topologias de Machados A0007–A0012, Lanças A0013–A0018 e o início de Adagas A0019–A0020, incluindo gateways e mastery dos terminais.
- `NotionCombatPerkRules` contém coeficientes de A0011, fail-closed/duração de A0012, dano/ritmo/crítico de lanças/adagas, faixa ideal, janelas e lockout de Interceptação.
- `A0001A0020CombatPolicy` implementa A0011, estado de Controle de Distância, A0017/A0018 e mantém A0012 explicitamente fail-closed até bridges causais reais.
- `NotionCombatPerkState` mantém Fúria, Controle de Distância, janelas, lockouts, deduplicação e limpeza transitória.
- `A0001A0020EpicFightHooks` fornece classificação SWORD/AXE/SPEAR/DAGGER, alcance efetivo, hit PRE/POST, miss confirmado, attack speed e amostragem server-side de aproximação/faixa.
- `A0001A0020CombatPolicyTest` possui cenários de A0011, fail-closed de A0012 e janelas/lockout de A0017/A0018.

Este arquivo é índice. A justificativa completa e as evidências ficam no dossiê individual de cada perk.

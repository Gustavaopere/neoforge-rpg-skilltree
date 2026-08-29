# Status dos Dossiês de Perks

Referência técnica auditada: `main` em `54658e6f51d1862a267fdb26e4146466228b18cb`.

| Código | Perk | Design | Código em `main` | Pendências conhecidas neste lote |
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

## Evidência comum do bloco A0001–A0010

- `NotionCombatPerkRules` contém os coeficientes de dano, ritmo, crítico, Ímpeto, Abertura e Fúria.
- `A0001A0020CombatPolicy` contém a política server-authoritative e deduplicação dos efeitos stateful.
- `A0001A0020EpicFightHooks` conecta `DELIVER_DAMAGE_PRE`, `DELIVER_DAMAGE_POST`, `MODIFY_ATTACK_SPEED`, `ON_DODGE`, `ATTACK_PHASE_END` e tick do Epic Fight 21.17.3.1.
- `A0001A0020CriticalService` implementa resolução crítica canônica.
- Existem testes `A0001A0020NotionContractTest`, `A0001A0020CombatPolicyTest` e `A0001A0020CriticalServiceTest`.

Este arquivo é índice. A justificativa completa e as evidências ficam no dossiê individual de cada perk.

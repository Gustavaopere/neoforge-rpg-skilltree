# 08 — Quest & Progression Hooks

Estado: **FUNDAÇÃO IMPLEMENTADA — adapters específicos pendentes**.

## Objetivo
Preparar integração com quests, NPCs e progressão narrativa sem fazer FTB Quests ou qualquer mod específico virar dependência do RPG Core.

## Contrato implementado
- consulta pública combinada de level/XP/CPP/atributos/mastery/perks/classes via `RpgQuestProgressionApi.query`;
- condições declarativas reutilizáveis para level, mastery XP, classe desbloqueada, rank de perk e rank de atributo;
- recompensas canônicas replay-safe para `CHARACTER_XP`, `CORE_POINTS` e `MAIN_PERK_BUDGET` via `RpgQuestProgressionApi.applyReward`;
- IDs namespaced estáveis para condições e rewards;
- adapters opcionais ficam fora do core e não acessam attachments/codecs internos.

## Critérios de aceite
- [x] quest não grava dados internos diretamente;
- [x] repetir entrega/evento não duplica recompensa;
- [x] ausência do mod de quests mantém o core funcional;
- [x] API é documentada para addons em `docs/wiki/QUEST_PROGRESSION_API.md`.

## Pendências deliberadas
- adapter específico para FTB Quests ou outro engine de quests;
- integração com NPCs/cartas/mapas e conteúdo narrativo concreto;
- reward genérico de unlock de perk/classe somente depois que os estágios responsáveis por esses estados congelarem ownership e migração;
- eventuais condições adicionais devem reutilizar `QuestProgressionSnapshot`/serviço canônico, não ler attachments diretamente.

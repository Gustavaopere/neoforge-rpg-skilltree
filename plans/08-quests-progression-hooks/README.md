# 08 — Quest & Progression Hooks

Estado: **PLANEJADO**.

## Objetivo
Preparar integração com quests, NPCs e progressão narrativa sem fazer FTB Quests ou qualquer mod específico virar dependência do RPG Core.

## Contrato proposto
- consulta pública de level/mastery/perks/classes;
- eventos/recompensas canônicas para XP e unlocks;
- condições data-driven reutilizáveis;
- idempotência para recompensas;
- adapters opcionais ficam fora do core.

## Critérios de aceite
- [ ] quest não grava dados internos diretamente;
- [ ] repetir entrega/evento não duplica recompensa;
- [ ] ausência do mod de quests mantém o core funcional;
- [ ] API é documentada para addons.
# PLANO — 08 Quest & Progression Hooks

Estado: **PLANEJADO**.

## Objetivo

Permitir quests, NPCs e progressão narrativa consultarem/concederem progresso por APIs estáveis sem fazer FTB Quests ou qualquer outro mod virar dependência do RPG Core.

## Dependências

01 Core, 04 classes/masteries e 06 padrão de adapters.

## Etapas de implementação

### 1 — API pública de consulta
- [ ] consultar level, XP, mastery, perks, classes e especializações;
- [ ] respostas somente leitura e estáveis para addons.

### 2 — Commands/rewards canônicos
- [ ] conceder XP, mastery, pontos ou unlock permitido por serviços do core;
- [ ] nenhuma quest escreve storage interno diretamente.

### 3 — Condições data-driven
- [ ] condições de level/perk/class/mastery;
- [ ] codecs/serialização e mensagens de falha;
- [ ] combinação AND/OR somente se semanticamente necessária.

### 4 — Idempotência
- [ ] reward/event ID persistível;
- [ ] repetir diálogo, reload ou entrega não duplica recompensa;
- [ ] política explícita para quests repetíveis.

### 5 — Adapters opcionais
- [ ] FTB Quests fica em módulo/adapter opcional;
- [ ] NPC mods usam a mesma API pública;
- [ ] ausência dos mods mantém o core funcional.

### 6 — Ferramentas de authoring
- [ ] exemplos de condições/recompensas;
- [ ] logs de diagnóstico para quest authors;
- [ ] documentação em `docs/technical/`, não na wiki de jogador.

## Testes

- [ ] recompensa única e repetível;
- [ ] save/reload no meio da quest;
- [ ] mod de quests ausente;
- [ ] servidor reiniciado não perde idempotency ledger.

## Definição de concluído

API pública estável, recompensas idempotentes e pelo menos um adapter validado; então `PLANO-✅.md`.
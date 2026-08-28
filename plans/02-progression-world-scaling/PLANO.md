# PLANO — 02 Progression & World Scaling

Estado: **EM ANDAMENTO / infraestrutura presente**.

## Objetivo

Fazer o mundo reagir à progressão do jogador por nível de território, nível de entidade, relevant player level, raridade e arquétipos sem transformar todo mob em esponja de HP.

## Dependências

01 RPG Core para nível do jogador e serviços canônicos.

## Etapas de implementação

### 1 — Relevant player level
- [ ] definir busca espacial/party usada por entidade e área;
- [ ] regras para nenhum jogador relevante, múltiplos jogadores e grande disparidade de níveis;
- [ ] cache com invalidação para evitar scans caros por tick.

### 2 — Área/território
- [ ] persistir/derivar nível local de forma determinística;
- [ ] definir como exploração, distância, progressão ou conteúdo especial alteram o nível;
- [ ] garantir estabilidade após restart/reload.

### 3 — Nível da entidade
- [ ] calcular no ponto correto do lifecycle de spawn;
- [ ] impedir reaplicação de scaling ao recarregar entidade;
- [ ] fallback seguro para entidades externas/bosses.

### 4 — Raridade e arquétipos
- [ ] selecionar arquétipo/raridade uma única vez quando aplicável;
- [ ] aplicar modifiers idempotentes;
- [ ] registrar metadados suficientes para save/load.

### 5 — Fórmulas
- [ ] separar HP, dano, defesa, utilidade e recompensa;
- [ ] usar caps/curvas configuráveis;
- [ ] evitar crescimento que invalide build ou cause one-shot inevitável.

### 6 — Recompensas
- [ ] XP/loot acompanham risco sem criar farm exponencial;
- [ ] bosses e raridades especiais possuem multiplicadores explícitos.

## Testes e performance

- [ ] solo, party, jogadores de níveis muito diferentes;
- [ ] chunks unload/reload;
- [ ] mobs vanilla e externos;
- [ ] benchmark de spawn e cálculo de vizinhança;
- [ ] nenhum modifier duplicado.

## Definição de concluído

Fórmulas fechadas, multiplayer consistente, custo medido e matriz de save/spawn verde; então renomear para `PLANO-✅.md`.
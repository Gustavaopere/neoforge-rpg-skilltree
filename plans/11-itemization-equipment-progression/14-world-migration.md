# 11.14 — Migração de mundos e equipamentos existentes

## Objetivo

Introduzir itemização em saves existentes sem destruir equipamentos antigos, sem rerroll repetido e sem converter dados externos de forma ambígua.

## Passo a passo

### A — Versionamento

- [ ] identidade possui `schemaVersion` explícita;
- [ ] migrators encadeados e idempotentes;
- [ ] versão futura desconhecida falha fechada e preserva dados;
- [ ] migration nunca depende de texto localizado.

### B — Itens antigos sem identidade RPG

Aplicar migração lazy/event-driven em fronteiras seguras:

- inventário/equipamento do jogador ao carregar/usar;
- item ao ser equipado;
- container quando acessado/processado por boundary suportado;
- mob equipado quando entra no runtime relevante.

Evitar varrer todos os chunks/containers do mundo na inicialização.

### C — Contexto de migração

Item antigo pode não possuir origem ou nível histórico verificável. Definir policy conservadora:

- usar contexto atual somente quando isso for decisão explícita do design;
- quando não houver base confiável, usar migration baseline configurável/documentado;
- marcar `GenerationSource=MIGRATION`;
- não inventar boss/loot source histórico.

### D — Itens Apotheosis existentes

Auditar os dados reais e converter somente quando houver mapping seguro:

- preservar gems/sockets/encantamentos;
- mapear rarity externa para Rank RPG apenas se a policy for explicitamente definida;
- adaptar affixes conhecidos para famílias quando semanticamente seguro;
- affix desconhecido permanece externo/preservado, não vira modifier aleatório;
- não apagar dados para “limpar” o item.

### E — Upgrades e transforms

Após migração, smithing/reparo/upgrades seguem a regra normal de preservação. Testar recipes que substituem ItemStack/Item base e cópia de components.

### F — Rollback e diagnóstico

- [ ] registrar contagem de itens migrados e recusados;
- [ ] motivo estruturado para falha;
- [ ] nenhuma tentativa infinita a cada tick para item permanentemente inválido;
- [ ] backup/release notes antes de migration destrutiva futura;
- [ ] ferramentas admin de auditoria read-only antes de qualquer repair command.

## Testes previstos

- save fixture sem Stage 11 -> itemizado uma vez;
- reload subsequente não rerrola;
- item Apotheosis conhecido preserva dados;
- dado externo desconhecido não é apagado;
- schema N -> N+1 idempotente;
- versão futura desconhecida fail-closed;
- mundo grande não dispara scan global.

## Acceptance

Saves existentes entram no Stage 11 progressivamente, com versionamento e diagnóstico, sem perda silenciosa de equipamento ou rerroll a cada carregamento.

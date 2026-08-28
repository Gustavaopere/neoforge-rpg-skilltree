# 10.04 — Descoberta, progresso e recompensas

## Objetivo

Implementar um sistema de descoberta server-authoritative que transforme o Compêndio em progressão de exploração, sem permitir farm duplicado, spoof do cliente ou perda de progresso quando o catálogo mudar.

## Estados de descoberta

Cada entrada deve suportar, no mínimo:

- `UNKNOWN` — entrada ainda oculta ou apenas silhueta/nome parcial;
- `SEEN` — jogador observou/encontrou o conteúdo;
- `STUDIED` — critérios adicionais foram cumpridos;
- `MASTERED` — conjunto opcional de objetivos completos para aquela entrada.

Nem todo tipo de entrada precisa usar todos os estados. A política é data-driven.

## Gatilhos previstos

### Entidades

- proximidade/observação validada pelo servidor;
- mirar/inspecionar com item de pesquisa/luneta;
- interação válida;
- domesticação;
- reprodução;
- morte/derrota;
- fotografia via adapter quando Exposure estiver presente;
- descoberta de variante individual.

### Flora, árvores e cultivos

- aproximação/observação;
- coleta;
- plantio;
- crescimento/colheita;
- descoberta em habitat natural.

### Biomas e dimensões

- entrada real do jogador no biome/dimension;
- permanência mínima opcional para evitar trigger de borda/teleport transitório.

### Estruturas

- detecção server-side de estrutura válida;
- entrada no bounding box/peça relevante;
- conclusão/derrota de boss somente quando houver adapter confiável.

## Plano

### A — Estado persistido por jogador

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryState.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRecord.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryProgress.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRuntime.java
```

Cada registro deve guardar apenas dados necessários, por exemplo:

- `entryId`;
- estado alcançado;
- primeiro timestamp lógico/tempo de mundo, quando útil;
- primeira dimensão/posição aproximada quando configurado;
- variantes encontradas;
- objetivos concluídos;
- flags de recompensa já concedida.

Privacidade/performance: não registrar trilha histórica ilimitada de coordenadas.

### B — Avaliação idempotente

- [ ] eventos duplicados no mesmo tick não concedem progresso/recompensa duas vezes;
- [ ] cliente nunca envia “descobri X” como fato confiável;
- [ ] packets do cliente podem solicitar ação de inspeção, mas o servidor valida alvo/distância/item/estado;
- [ ] recompensas usam chaves idempotentes compatíveis com o Stage 08.

### C — Recompensas

Tipos planejados:

- XP do RPG;
- XP vanilla opcional;
- mastery XP quando semanticamente apropriado;
- comando data-driven restrito/validado;
- advancement/quest hook;
- item/recompensa específica apenas via definição explícita.

A descoberta básica **não deve inflar progressão** em packs grandes. Curvas/caps e recompensas por categoria precisam de orçamento.

### D — Progresso por categoria

Exibir e persistir derivadamente:

- descobertas/total em fauna;
- flora;
- árvores;
- cultivos;
- biomas;
- estruturas;
- dimensões;
- por mod/namespace;
- por dimensão/ecossistema.

O denominador deve usar o catálogo carregado e distinguir entradas ignoradas/indisponíveis.

### E — Compartilhamento e party

Decisão inicial: descoberta é individual por padrão.

Se houver compartilhamento futuro:

- [ ] deve ser opt-in/data-driven;
- [ ] precisa de regra explícita de party/range;
- [ ] não pode copiar automaticamente notas pessoais;
- [ ] deve respeitar anti-farm e idempotência.

### F — Conteúdo removido

- [ ] progresso de ID ausente não é apagado automaticamente;
- [ ] save mantém tombstone/record legado;
- [ ] se o mod voltar, a descoberta reaparece;
- [ ] migrações de ID devem ser explícitas.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRuntimeTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryIdempotencyTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRewardTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoverySaveRoundTripTest.java
```

Casos obrigatórios:

- [ ] observar entidade altera estado uma única vez;
- [ ] matar entidade já descoberta não duplica recompensa one-shot;
- [ ] variante nova pode avançar objetivos sem redescobrir a espécie de forma inválida;
- [ ] relog/dimension change preserva progresso;
- [ ] conteúdo removido temporariamente preserva record;
- [ ] packet forjado é rejeitado;
- [ ] multiplayer separa progresso de dois jogadores.

## Acceptance

O subplano fecha quando descoberta, progressão e recompensas forem server-authoritative, persistentes, idempotentes e testadas independentemente da UI final.

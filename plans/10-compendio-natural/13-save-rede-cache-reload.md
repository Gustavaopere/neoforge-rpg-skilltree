# 10.13 — Save, rede, cache e reload

## Objetivo

Fechar o ciclo operacional do Compêndio: persistência versionada, sincronização mínima, cache bounded e reload atômico.

O catálogo pode crescer para milhares de entradas; portanto não é aceitável sincronizar blobs completos a cada abertura de tela ou reprocessar worldgen/loot em tick.

## Plano

### A — Persistência versionada

Criar posteriormente, respeitando o padrão de save já adotado no projeto:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/CompendiumSaveCodec.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/CompendiumSaveVersion.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/CompendiumMigration.java
```

Persistir apenas dados do jogador/servidor que não podem ser reconstruídos do catálogo:

- progresso de descoberta;
- variantes descobertas;
- objetivos one-shot;
- rewards já concedidos;
- favoritos se a decisão for server-side;
- notas, conforme política final;
- tombstones/legacy IDs quando necessários.

Não persistir snapshots inteiros de registry, loot ou corpus editorial.

### B — Política de notas

Decisão preferida para multiplayer: notas pessoais pertencem ao jogador e devem sobreviver a relog.

Comparar antes de implementar:

1. notas server-side no save do jogador — melhor continuidade entre clientes, exige limites/rede;
2. notas client-side por mundo/servidor — mais privadas/local, mas não acompanham o jogador entre dispositivos.

Gate deste subplano: escolher uma opção e documentar. Em qualquer escolha:

- limite por nota e limite total;
- UTF-8 validado;
- sem rich text executável;
- migração/versionamento;
- IDs ausentes preservados.

### C — Snapshot de catálogo

Criar:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCatalogManager.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumReloadListener.java
```

Reload:

1. parseia dados para staging;
2. resolve registries/providers;
3. valida IDs/relações/schema;
4. constrói índices/cache;
5. se tudo passar, publica snapshot imutável novo;
6. se falhar, mantém snapshot anterior e gera diagnóstico.

Nunca publicar catálogo parcialmente atualizado.

### D — Rede

Criar posteriormente:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/network/CompendiumProtocol.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/network/CompendiumPackets.java
src/main/java/dev/gustavopere/rpgskilltree/compendium/network/CompendiumSyncService.java
```

Classes de payload previstas:

- handshake/schema version;
- discovery progress snapshot/delta;
- catalog metadata hash/version;
- catalog entry page/detail on demand quando necessário;
- instance inspection request/result;
- admin action request/result separado;
- notes sync, se server-side.

Regras:

- [ ] protocolo versionado;
- [ ] payloads limitados;
- [ ] strings/coleções com máximos explícitos;
- [ ] IDs desconhecidos rejeitados ou tratados como legado;
- [ ] compressão/chunking só se medição justificar;
- [ ] client nunca recebe dado administrativo sem autorização;
- [ ] catálogo estático não é reenviado a cada screen open.

### E — Estratégia de sincronização

Preferência:

- conteúdo estático incluído no mod/resource/datapack local: cliente resolve localmente após hash/version;
- conteúdo server-defined: sync de snapshot compactado ou páginas on-demand;
- progresso: snapshot no login + deltas;
- inspeção de entidade: on-demand com fatos whitelisted;
- reload server-side: invalida hash e sincroniza versão nova.

Se cliente e servidor tiverem catálogos incompatíveis, apresentar erro/diagnóstico explícito em vez de mapear IDs por posição.

### F — Cache

Caches separados:

- registry/static facts;
- loot summaries;
- search index client;
- relationships/indexes;
- renderer preview client;
- instance inspection com TTL curto, se necessário.

Proibições:

- cache global contendo referência forte a entidades/worlds descarregados;
- cache sem invalidação em datapack reload;
- cache de preview 3D ilimitado;
- scan completo por tick/frame.

### G — World/relog lifecycle

- [ ] login: sync versão + progresso;
- [ ] respawn: estado preservado;
- [ ] dimension change: nenhum reset;
- [ ] relog: round-trip idêntico;
- [ ] server restart: progresso preservado;
- [ ] mod removido: ID legado preservado;
- [ ] mod reinstalado: record reatacha ao mesmo ID;
- [ ] rename de ID: somente via migração explícita.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumMigrationTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/network/CompendiumProtocolTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/network/CompendiumPacketBoundsTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumReloadAtomicityTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumCacheInvalidationTest.java
```

Casos obrigatórios:

- [ ] save v1 -> versão atual;
- [ ] ID desconhecido preservado;
- [ ] reload inválido mantém snapshot anterior;
- [ ] reload válido muda hash/version;
- [ ] packet oversized rejeitado;
- [ ] ordem de catálogo diferente não quebra ID mapping;
- [ ] login/relog/dimension change;
- [ ] remoção/reinstalação de mod opcional;
- [ ] cache não retém referência a world/entity após lifecycle.

## Acceptance

O subplano fecha quando saves do Compêndio forem versionados/migráveis, rede tiver protocolo bounded e o catálogo suportar reload/cache sem estado parcial ou trabalho repetitivo por tick.

# 10.06 — Autoridade do servidor e sincronização de rede

## Objetivo

Garantir que o cliente receba somente o estado necessário para renderizar o Compêndio e nunca seja autoridade sobre descoberta, catálogo ativo ou recompensas.

## Arquivos previstos

Criar/alterar principalmente:

- `src/main/java/dev/gustavopere/rpgskilltree/runtime/network/EncyclopediaCatalogSyncPayload.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/network/EncyclopediaDiscoverySyncPayload.java` somente se o sync de progressão existente não for suficiente;
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/network/RequestEncyclopediaOpenPayload.java` somente se houver necessidade real de handshake;
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientEncyclopediaState.java`
- testes de codec e limites em `src/test/java/.../runtime/network/` ou pacote equivalente existente.

## Separação de responsabilidades

### Servidor

Autoridade sobre:

- catálogo data-driven efetivamente carregado no servidor;
- quais entries/providers são válidos;
- descoberta/estudo;
- rewards;
- mappings de target -> entry;
- versionamento do snapshot.

### Cliente

Responsável por:

- cache imutável do snapshot recebido;
- pesquisa/filtros locais;
- rendering;
- seleção da entrada;
- notificações já confirmadas pelo servidor.

O cliente não envia `entryId` para “confirmar descoberta”. Eventos de gameplay no servidor determinam o desbloqueio.

## Catálogo servidor x recursos do cliente

O servidor pode usar datapacks diferentes do JAR cliente. Portanto a UI não pode presumir que todo `entryId` built-in existe no catálogo do servidor.

Definir snapshot de catálogo com dados necessários para apresentação/validação, mantendo conteúdo textual eficiente. Opções aceitáveis:

1. sincronizar metadata/IDs e usar translation keys de recursos do cliente para corpus built-in;
2. para entradas server-defined, sincronizar texto literal PT-BR com limites explícitos;
3. exigir resource pack pareado para conteúdo visual/textual customizado e detectar ausência.

A implementação deve escolher um contrato e testá-lo. Não misturar silenciosamente “server data” e “client data” por coincidência.

## Reuso do `ProgressionSyncPayload`

Como `ProgressionState` já inclui `discoveries`, a primeira escolha deve ser reutilizar o snapshot de progressão para estado de unlock. Criar payload de discovery separado apenas se houver prova de que o payload atual fica excessivo ou o domínio exigir delta/versionamento independente.

## Sync events

Sincronizar catálogo/estado nos pontos necessários:

- login;
- respawn/reclone quando o estado canônico mudar;
- reload de datapack bem-sucedido;
- mudança de dimensão apenas se o Stage 07 consolidar sync nesse evento;
- nova descoberta/estudo, preferencialmente por state sync já existente;
- reconexão após mudança de modpack/server.

Não transmitir catálogo inteiro a cada descoberta.

## Versionamento e limites

Todo payload novo precisa de:

- `ResourceLocation` de tipo estável;
- `StreamCodec` bounded;
- limites de entries, tags, crosslinks e tamanho de texto;
- versão/schema se o conteúdo serializado puder evoluir;
- rejeição segura de payload inválido/oversized;
- nenhum decode que aloque lista arbitrária definida pela rede.

O `ProgressionSyncPayload` já limita snapshot a 1 MiB; o Stage 10 deve medir impacto real das novas discovery keys nesse teto.

## Deltas

O MVP pode sincronizar snapshot completo de discovery via progressão se o tamanho continuar baixo. Só introduzir delta protocol se profiling mostrar necessidade.

Catálogo grande deve ser sincronizado somente em login/reload, não em loop. Se o corpus completo ultrapassar budget razoável, separar metadata sincronizada de prosa/resources locais.

## Testes

- codec round-trip de snapshot vazio e populado;
- oversized rejeitado;
- lista count além do limite rejeitada;
- cliente não consegue conceder unlock;
- reload bem-sucedido substitui catálogo client-side atomicamente;
- reload falho mantém catálogo anterior;
- nova descoberta atualiza UI sem resync do catálogo inteiro;
- server sem provider opcional não envia entrada daquele provider;
- progress snapshot com milhares de chaves continua dentro do budget ou aciona redesign documentado.

## Acceptance

- [ ] Catálogo e unlock state têm autoridade clara do servidor.
- [ ] Nenhum payload client -> server concede descoberta/reward por confiança.
- [ ] Payloads são bounded e versionáveis.
- [ ] Login/reload/discovery usam granularidade adequada.
- [ ] O cliente não depende de catálogo local divergente do servidor.
- [ ] Testes de codec, limite e sync passam.

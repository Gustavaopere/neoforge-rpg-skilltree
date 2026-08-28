# 10.05 — Persistência, compatibilidade de save e migrações

## Objetivo

Garantir que conhecimento enciclopédico sobreviva a relog, morte, mudança de dimensão, restart, atualização de datapack e renome de entradas sem corromper a progressão canônica.

## Estado existente

`ProgressionState` já contém `DiscoveryProgress discoveries`, e `DiscoveryProgress` é um conjunto imutável de chaves. Isso é suficiente para estados booleanos de descoberta/estudo, desde que as chaves sejam estáveis e o codec canônico continue preservando-as.

## Arquivos previstos

Alterar/criar somente se necessário:

- `src/main/java/dev/gustavopere/rpgskilltree/core/DiscoveryProgress.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/ProgressionStateCodec.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaDiscoveryKeyPolicy.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaDiscoveryMigration.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/CanonicalPlayerAttachmentRuntime.java`
- testes de codec/migration já existentes + testes específicos do Compêndio.

## Regra principal

Não criar `EncyclopediaSavedData` separado para progresso do jogador enquanto `ProgressionState.discoveries()` atender ao requisito. O catálogo de entradas é conteúdo global; o que é pessoal é apenas o conjunto de descobertas/estudos.

## Namespace das chaves

Toda chave nova deve passar por uma policy central. Exemplo lógico:

```text
encyclopedia:rpgskilltree:fauna/alexsmobs/foo:discovered
encyclopedia:rpgskilltree:fauna/alexsmobs/foo:studied
```

O formato exato deve evitar parsing ambíguo de `ResourceLocation`. Preferir codec/record estruturado internamente e string serializada por uma única função.

## Reconciliação com descobertas antigas

Já existem chaves como:

- `biome:<namespace:path>`
- `dimension:<namespace:path>`

Ao instalar o Stage 10 em save existente:

- mapear descobertas antigas de bioma/dimensão para entries correspondentes sem conceder XP de novo;
- preservar chaves desconhecidas para compatibilidade, salvo migração explicitamente segura;
- não apagar descobertas de outros subsistemas.

## Aliases e renomes

O catálogo deve poder declarar aliases de IDs antigos para novos IDs em arquivo de migration/versioning ou tabela central.

Regras:

- rename com alias migra `discovered` e `studied`;
- merge de duas entries em uma aplica união idempotente;
- split exige decisão explícita e nunca desbloqueia ambas silenciosamente sem design;
- remoção sem substituto mantém chave legacy preservável, mas ela deixa de aparecer no catálogo ativo;
- migrations são versionadas e testadas.

## Ausência temporária de provider

Se o jogador descobriu uma entrada de mod opcional e depois o mod é removido:

- não apagar o progresso persistido;
- ocultar a entrada ativa porque o provider/target não existe;
- se o mod voltar com o mesmo ID compatível, a descoberta reaparece;
- se IDs mudarem, aplicar migration declarada.

## Necessidade de estado mais rico

Só ampliar `DiscoveryProgress` se uma feature aprovada exigir informação impossível em set de chaves, por exemplo timestamp, contador ou nota pessoal persistente. Nesse caso:

1. desenhar versão nova do codec;
2. migrar formatos antigos;
3. manter decode backwards-compatible das versões suportadas;
4. atualizar hardening do Stage 09.

Não ampliar o estado “para o futuro” sem consumidor real.

## Testes

- round-trip de save com 0, 1 e muitas descobertas enciclopédicas;
- decode de save anterior ao Stage 10;
- biome/dimension legacy -> encyclopedia sem XP duplicado;
- alias rename preserva discovered/studied;
- provider ausente não apaga chave;
- provider retornando restaura visibilidade;
- entrada removida não corrompe decode;
- chaves de outros sistemas permanecem intactas.

## Acceptance

- [ ] O progresso do Compêndio usa a persistência canônica existente ou há migração formal justificando mudança.
- [ ] Saves anteriores ao Stage 10 continuam válidos.
- [ ] Biomas/dimensões já descobertos são reaproveitados.
- [ ] Remoção temporária de mod não destrói progresso.
- [ ] Renomes têm alias/migration explícita.
- [ ] Testes de round-trip e backward compatibility passam.

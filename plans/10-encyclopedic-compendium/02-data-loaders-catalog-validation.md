# 10.02 — Dados, loaders, catálogo e validação

## Objetivo

Materializar as entradas do Compêndio como conteúdo data-driven recarregável, seguindo o padrão já usado em `runtime/data`, com publicação atômica e validação forte antes de qualquer snapshot virar visível para runtime/UI.

## Arquivos previstos

Criar/alterar principalmente:

- `src/main/java/dev/gustavopere/rpgskilltree/runtime/data/EncyclopediaEntryCatalog.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/data/EncyclopediaEntryReloader.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/data/EncyclopediaEntryParser.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/data/EncyclopediaCatalogSnapshot.java`
- `src/main/resources/data/rpgskilltree/encyclopedia/*.json`
- `src/test/java/dev/gustavopere/rpgskilltree/runtime/data/EncyclopediaEntryParserTest.java`
- `src/test/java/dev/gustavopere/rpgskilltree/runtime/data/EncyclopediaCatalogValidationTest.java`

## Formato de dados

Adotar uma pasta única de domínio:

```text
src/main/resources/data/rpgskilltree/encyclopedia/
```

O caminho físico pode ser subdividido por categoria/provedor para organização, mas a identidade continua sendo o `entryId` declarado/resolvido, não o caminho relativo do arquivo.

Cada JSON deve declarar pelo menos:

- categoria/subcategoria;
- alvo registry quando existir;
- `provider_mod_id` quando modded;
- chaves de texto PT-BR;
- icon/render hint;
- tags e crosslinks;
- regras de descoberta;
- política de visibilidade.

## Reload atômico

O reloader deve:

1. parsear todos os arquivos em estrutura temporária;
2. validar schema e referências internas;
3. verificar targets contra registries disponíveis quando o target exigir isso;
4. filtrar somente entradas explicitamente opcionais cujo provider esteja ausente;
5. rejeitar ambiguidades/duplicatas reais;
6. construir índices derivados;
7. publicar um único `EncyclopediaCatalogSnapshot` imutável.

Falha em uma entrada obrigatória não pode publicar metade do catálogo novo. Manter o snapshot anterior válido e emitir diagnóstico identificando arquivo + campo + ID.

## Índices obrigatórios

O snapshot deve preparar sem recomputação por frame:

- `entryId -> definition`;
- `target type + target id -> entryId`;
- categoria -> entradas ordenadas;
- tag -> entradas;
- provider mod id -> entradas;
- relação reversa de crosslinks quando útil.

Pesquisa textual do cliente entra no subplano 07; o servidor não deve indexar texto traduzido sem necessidade.

## Validações

Rejeitar:

- `entryId` duplicado;
- target duplicado sem política explícita;
- crosslink inexistente para entrada obrigatória;
- self-link;
- tag vazia;
- `provider_mod_id` inválido;
- provider presente mas target registry inexistente;
- regra de descoberta incompatível com tipo de target;
- translation key obrigatória ausente no corpus built-in;
- categoria/tipo incoerentes quando a regra é determinística.

Entradas de mod opcional ausente devem ser omitidas de forma previsível, não gerar stacktrace por `ClassNotFoundException` nem tocar classes do mod externo.

## Diagnóstico de authoring

Criar mensagens estruturadas suficientes para apontar:

- arquivo de origem;
- `entryId`;
- campo inválido;
- target esperado/resolvido;
- provider ausente versus target quebrado;
- crosslink quebrado.

Nenhum erro de conteúdo deve ser reduzido a “failed to load encyclopedia”.

## Testes

- catálogo vazio válido;
- dois arquivos válidos publicados juntos;
- duplicate ID rejeitado;
- target inexistente de provider presente rejeitado;
- provider opcional ausente filtrado;
- reload inválido preserva snapshot anterior;
- crosslinks forward/reverse resolvidos;
- ordem determinística entre runs;
- nenhuma coleção mutável escapa do snapshot.

## Acceptance

- [ ] O Compêndio possui loader/catálogo data-driven próprio.
- [ ] Reload é atômico e mantém último snapshot válido em falha.
- [ ] Targets e crosslinks são validados.
- [ ] Mods opcionais ausentes são tratados sem dependência dura.
- [ ] Erros de authoring são acionáveis.
- [ ] Testes de parser, validação e publicação atômica passam.

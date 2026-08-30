# Compêndio Natural 10.10 — Runtime Editorial Overlay Design

## Status

Design aprovado em chat em 2026-08-30. Esta especificação formaliza a integração do corpus editorial pt-BR ao runtime sem absorver responsabilidades reservadas ao Stage 10.13.

## Contexto

O Stage 10.10 já possui um contrato editorial offline versionado e validado por CI. O corpus usa IDs canônicos `KIND:namespace:path`, idioma `pt_br`, fontes explícitas, disponibilidade `RUNTIME`/`OPTIONAL`/`LEGACY`, revisão `DRAFT`/`REVIEWED` e cobertura determinística.

O runtime atual do Compêndio é server-authoritative para os catálogos técnicos. `CompendiumEntry` contém identidade, categorias, `CompendiumSection`/`CompendiumFact`, relações, políticas de descoberta/visibilidade e proveniência. Providers enriquecem fatos técnicos por `ProviderMerger`. `CompendiumPageModelFactory` filtra fatos e relações antes da renderização.

Prosa editorial não é um fato técnico e não deve participar do sistema de prioridade/conflito de providers. O Stage 10.13 continuará responsável por reload atômico de datapack, protocolo de rede, cache, hash/versionamento e persistência.

## Objetivos

1. Carregar o corpus editorial pt-BR em Java a partir de resources/datapacks.
2. Manter conteúdo editorial separado do catálogo técnico e dos `CompendiumFact`.
3. Vincular conteúdo editorial exclusivamente por `CompendiumEntryId` canônico.
4. Publicar um snapshot editorial imutável e server-authoritative.
5. Compor páginas do Compêndio com conteúdo editorial sem alterar semântica de providers técnicos.
6. Falhar fechado para corpus inválido, IDs incompatíveis, duplicatas e referências não autorizadas.
7. Preparar uma API reutilizável pelo Stage 10.13 para staging/reload atômico posterior.

## Não objetivos

Esta fatia NÃO implementa:

- protocolo/sync definitivo cliente-servidor;
- `/reload` ou listener de reload de datapack;
- hash/versionamento de catálogo para rede;
- cache novo;
- persistência de notas/favoritos/descoberta;
- authoring em massa do corpus real;
- validação dinâmica de stats mutáveis por providers;
- substituição de `CompendiumFact` ou `ProviderMerger`.

## Estrutura de resources

O loader Java procurará pacotes em:

```text
data/rpgskilltree/compendium/editorial/pt_br/<namespace>/*.json
```

O formato lógico permanece compatível com o contrato offline do Stage 10.10:

```json
{
  "schema": 1,
  "language": "pt_br",
  "namespace": "minecraft",
  "kind": "ENTITY",
  "entries": [
    {
      "entry_id": "ENTITY:minecraft:zombie",
      "title": "Zumbi",
      "summary": {
        "text": "...",
        "sources": [
          {"type": "RUNTIME", "ref": "minecraft:entity_type/minecraft:zombie"}
        ]
      },
      "sections": {
        "behavior": {
          "text": "...",
          "sources": [
            {"type": "RUNTIME", "ref": "minecraft:entity_type/minecraft:zombie"}
          ]
        }
      },
      "references": [],
      "review_status": "REVIEWED",
      "availability": "RUNTIME"
    }
  ]
}
```

O decoder Java deve preservar a mesma interpretação sem criar um segundo schema incompatível.

## Modelo de domínio

Criar uma camada editorial independente em `compendium/editorial`.

### `CompendiumEditorialContent`

Representa o conteúdo editorial de uma entrada:

- `CompendiumEntryId entryId`;
- `String title`;
- `CompendiumEditorialBlock summary`;
- coleção ordenada de `CompendiumEditorialSection`;
- lista de referências para outras entradas;
- `EditorialReviewStatus`;
- `EditorialAvailability`;
- motivo de disponibilidade quando aplicável.

### `CompendiumEditorialSection`

Representa uma seção textual nomeada por ID estável, sem convertê-la em `CompendiumFact<String>`.

### `CompendiumEditorialBlock`

Representa texto editorial + proveniência explícita. Fontes são dados do corpus; não substituem `CompendiumProvenance` técnico da entrada.

### `CompendiumEditorialSource`

Valor imutável com `type` e `ref`, validado contra vazio/placeholders conforme o contrato offline.

### `CompendiumEditorialSnapshot`

Snapshot imutável indexado por `CompendiumEntryId`, com lookup O(1). Deve ser determinístico e rejeitar duplicatas.

## Decoder e validação runtime

Criar `CompendiumEditorialResourceLoader` com duas responsabilidades separáveis:

1. descobrir/parsear todos os JSONs do caminho editorial no `ResourceManager`;
2. produzir um candidato `CompendiumEditorialSnapshot` validado contra o catálogo técnico atual.

Validações obrigatórias:

- `schema == 1`;
- `language == pt_br`;
- diretório físico `<namespace>` igual ao namespace declarado;
- `kind` do pacote igual ao kind do `entry_id`;
- namespace do `entry_id` igual ao namespace do pacote;
- IDs canônicos válidos;
- IDs duplicados entre arquivos rejeitados;
- `title`, resumo, seções e fontes sem texto vazio/placeholder;
- `availability` obrigatória;
- `RUNTIME` exige que o ID exista no catálogo técnico atual;
- `OPTIONAL`/`LEGACY` exige ID ausente do catálogo atual e justificativa não vazia;
- referências internas precisam resolver para uma entrada autorizada no catálogo atual ou para conteúdo editorial `OPTIONAL`/`LEGACY` válido;
- `REVIEWED` e `DRAFT` são preservados; política de release permanece responsabilidade do gate offline/CI nesta fatia.

Nenhuma entrada inválida deve ser parcialmente publicada.

## Publicação server-authoritative

Criar `RuntimeCompendiumEditorialCatalog` com snapshot atual e operação de publicação por candidato.

Semântica:

1. o catálogo técnico é construído normalmente pelos collectors/providers existentes;
2. o loader editorial constrói um candidato usando o `ResourceManager` e o snapshot técnico disponível;
3. se o candidato for totalmente válido, o snapshot editorial é trocado atomicamente;
4. se o candidato falhar, o snapshot editorial anterior permanece intacto;
5. em primeira inicialização sem snapshot válido anterior, permanece um snapshot editorial vazio;
6. falha editorial NÃO corrompe nem impede a publicação do catálogo técnico válido;
7. diagnóstico explícito é emitido em `RuntimeDiagnostics.Category.COMPENDIUM`.

Essa fronteira permite que o Stage 10.13 reaproveite exatamente o mesmo loader/candidato dentro de um reload staging mais amplo.

## Integração com páginas

A composição deve ocorrer depois que o `CompendiumEntry` técnico já passou pelos providers e antes da exposição à UI.

`CompendiumPageModel` será estendido para carregar um campo editorial separado, por exemplo `CompendiumEditorialContent editorialContent` ou `Optional<...>` normalizado pelo construtor.

`CompendiumPageModelFactory` receberá o overlay editorial correspondente ao `entry.id()` e aplicará as políticas já existentes:

- entrada escondida continua escondida;
- se detalhes técnicos não podem ser mostrados por descoberta/visibilidade, o texto editorial detalhado também não deve vazar;
- conteúdo editorial não sobrescreve `CompendiumSection` técnico;
- título editorial pode ser usado como título enciclopédico da página sem alterar a identidade/translation key do catálogo;
- ausência de editorial é válida e mantém fallback atual;
- referências editoriais só viram links clicáveis quando o target existir no snapshot de cliente autorizado.

A UI deve consumir o modelo composto, não acessar diretamente resources ou `ResourceManager`.

## Relações e referências editoriais

Referências editoriais são navegação de leitura, não relações técnicas/provider-native.

Portanto:

- não serão inseridas automaticamente em `CompendiumEntry.relations()`;
- não participam de `ProviderMerger`;
- serão projetadas separadamente para a página;
- alvo inexistente/oculto não é exposto ao cliente;
- o Stage 10.13 decidirá a forma definitiva de transporte/autorização dessa projeção.

## Separação editorial × técnico

Invariante central:

- `CompendiumFact` = dado técnico/estruturado, potencialmente provider-native, com confiança/visibilidade;
- `CompendiumEditorialContent` = prosa enciclopédica curada, com fontes editoriais e revisão;
- nenhum texto editorial usa prioridade de provider para vencer fatos técnicos;
- nenhum provider técnico altera silenciosamente prosa editorial;
- proveniência editorial e proveniência técnica permanecem auditáveis separadamente.

## Lifecycle nesta fatia

Nesta etapa, o overlay será carregado no lifecycle de startup já usado pelos catálogos do Compêndio, após registries estarem disponíveis.

Não registrar listener de datapack reload ainda. O loader será uma unidade reutilizável e sem estado global próprio; `RuntimeCompendiumEditorialCatalog` mantém apenas o snapshot publicado.

## Tratamento de erros

Erros de parse/schema/identidade/referência devem:

- impedir a publicação do candidato inteiro;
- preservar o último snapshot editorial válido;
- emitir diagnóstico com caminho do resource e causa;
- nunca inventar fallback factual;
- nunca transformar erro editorial em `CompendiumFact` confirmado;
- nunca derrubar um catálogo técnico já válido apenas porque a prosa editorial falhou.

## Testes TDD obrigatórios

### Modelo/decoder

- pacote válido é decodificado;
- `schema` incorreto falha;
- idioma diferente de `pt_br` falha;
- namespace físico/declarado divergente falha;
- kind/entry ID divergente falha;
- ID duplicado entre arquivos falha;
- placeholder/vazio falha;
- fonte inválida falha;
- `availability` ausente falha.

### Validação contra catálogo

- `RUNTIME` existente é aceito;
- `RUNTIME` ausente falha;
- `OPTIONAL`/`LEGACY` ausente com motivo é aceito;
- `OPTIONAL`/`LEGACY` que mascara ID presente falha;
- referência válida resolve;
- referência inválida falha.

### Snapshot/publicação

- snapshot é imutável e lookup é por ID;
- publicação válida substitui snapshot;
- candidato inválido preserva snapshot anterior;
- primeira falha mantém snapshot vazio;
- catálogo técnico permanece utilizável após falha editorial.

### Composição de página

- página com overlay recebe título/resumo/seções editoriais;
- página sem overlay mantém fallback atual;
- fatos técnicos permanecem idênticos após composição;
- conteúdo editorial não entra em `ProviderMerger`;
- entrada não descoberta/oculta não vaza texto editorial;
- referências editoriais não autorizadas não são expostas.

## CI

Os novos testes Java entram no aggregate `RPG Skill Tree CI`. Se houver valor de isolamento suficiente, o workflow `Compendium Editorial CI` também pode executar testes/runtime contract adicionais, sem duplicar lógica de validação.

Antes do merge, exigir:

- testes RED capturados antes da produção;
- GREEN dos testes novos;
- JUnit completo;
- NeoForge GameTests;
- todos os validadores do Compêndio;
- NeoForge build;
- verificação do JAR;
- dedicated-server smoke;
- review threads resolvidas;
- merge com `expected_head_sha`.

## Critérios de aceitação desta fatia

A fatia fecha quando:

1. existe modelo editorial Java separado de `CompendiumFact`;
2. resources pt-BR são decodificados e validados em runtime;
3. existe snapshot editorial imutável/indexado por ID;
4. publicação inválida preserva snapshot anterior e catálogo técnico;
5. página composta recebe editorial sem alterar fatos técnicos;
6. políticas de descoberta/visibilidade não vazam editorial;
7. testes obrigatórios passam no CI completo;
8. o design continua sem implementar responsabilidades do Stage 10.13.

O Stage 10.10 continuará aberto após esta fatia porque ainda faltará produzir/revisar o corpus real pt-BR em escala, validar dados técnicos mutáveis por fontes apropriadas e concluir QA linguística/cobertura editorial do modpack.

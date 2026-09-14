# Compêndio Natural — inventário reproduzível

Este documento descreve o contrato operacional do Stage 10.02. O inventário não mantém manualmente uma lista eterna de mods: **o runtime carregado é a autoridade**. A modlist textual é um snapshot auxiliar para versão, drift e auditoria de dependências embarcadas.

## 1. Snapshot da modlist

A ferramenta aceita o formato usado por `modlist agora atual.txt`, incluindo linhas internas sob `META-INF/jarjar/` e `META-INF/jars/`.

```bash
python3 scripts/compendium/inventory_modlist.py \
  "/caminho/para/modlist agora atual.txt" \
  --json generated/compendium/modpack-inventory.json \
  --markdown generated/compendium/modpack-inventory.md
```

O parser:

- exige e valida `Mods count: N`;
- separa mods top-level de dependências JAR-in-JAR;
- associa dependência embarcada ao mod pai quando a tabela fornece a relação por agrupamento;
- preserva filename, mod id, nome, versão runtime e hashes disponíveis;
- grava SHA-256 dos bytes exatos do snapshot;
- falha se a contagem declarada divergir da contagem top-level parseada.

`published_version` permanece nulo quando a fonte não distingue de forma confiável a versão publicada da versão runtime. O inventário não infere esse dado a partir de filename quando isso puder produzir uma versão falsa.

## 2. Coleta dos registries no pack realmente carregado

O mod possui um coletor de desenvolvimento opt-in. Inicie a instância/servidor NeoForge com a variável de ambiente:

```text
RPGSKILLTREE_COMPENDIUM_INVENTORY=1
```

Quando o servidor chega a `ServerStartedEvent`, o mod grava:

```text
generated/compendium/runtime-registry-inventory.json
```

relativo ao diretório de execução da instância. A coleta inclui:

- `ENTITY_TYPE`;
- blocos classificados como flora, árvore ou cultivo;
- `BIOME`;
- `STRUCTURE`;
- `LEVEL_STEM`/dimensões.

Cada registro contém `kind`, `resource_location`, `namespace`, `translation_key`, `mod_display_name`, `registry_source` e `present_at_runtime`. A saída também registra mods carregados, versões runtime e um fingerprint SHA-256 determinístico do conjunto de mods + IDs.

A ausência de um mod opcional não é erro: se o namespace não está carregado, ele simplesmente não aparece na coleta. Nenhuma classe de mod opcional é referenciada pelo scanner genérico.

## 3. Geração integral em um comando

Depois que a instância real produziu o snapshot runtime, a saída completa do Stage 10.02 pode ser regenerada com um único comando:

```bash
python3 scripts/compendium/generate_inventory.py \
  "/caminho/para/modlist agora atual.txt" \
  "/caminho/da/instancia/generated/compendium/runtime-registry-inventory.json" \
  --output-dir generated/compendium
```

O comando gera:

```text
generated/compendium/modpack-inventory.json
generated/compendium/modpack-inventory.md
generated/compendium/coverage-report.json
generated/compendium/coverage-report.md
```

Ele cruza a lista top-level com os mods realmente carregados. O runtime continua soberano: discrepâncias aparecem em `modlist_comparison`, não são mascaradas.

## 4. Cobertura editorial

O relatório de cobertura também pode ser executado isoladamente:

```bash
python3 scripts/compendium/inventory_runtime_report.py \
  generated/compendium/runtime-registry-inventory.json \
  --modlist generated/compendium/modpack-inventory.json \
  --json generated/compendium/coverage-report.json \
  --markdown generated/compendium/coverage-report.md
```

Toda entrada recebe exatamente um estado:

- `AUTO` — cobertura genérica por registry/runtime;
- `CURATED` — cobertura automática enriquecida por conteúdo próprio;
- `ADAPTER` — requer provider específico;
- `IGNORED` — exclusão enciclopédica deliberada, obrigatoriamente com motivo;
- `ERROR` — entrada deveria ser representável, mas está inválida/incompleta.

Entradas válidas sem override recebem `AUTO`. Entrada inválida nunca desaparece silenciosamente: permanece no relatório como `ERROR` e o comando termina com código `2`.

### Overrides

Arquivo opcional:

```json
{
  "schema": 1,
  "overrides": {
    "STRUCTURE|minecolonies:work_camp": {
      "state": "IGNORED",
      "reason": "estrutura operacional da colônia, não descoberta enciclopédica"
    },
    "ENTITY|example:scripted_creature": {
      "state": "ADAPTER",
      "reason": "ecologia depende de API específica do provider"
    }
  }
}
```

Override que referencia ID ausente do runtime falha fechado. `IGNORED` e `ERROR` sem motivo também falham.

## 5. Drift e legado

Para comparar duas coletas:

```bash
python3 scripts/compendium/inventory_runtime_report.py \
  generated/compendium/runtime-registry-inventory.json \
  --previous /caminho/para/runtime-registry-inventory-anterior.json
```

O relatório registra mods adicionados/removidos e IDs adicionados/removidos. IDs removidos também aparecem em `orphaned_registry_entries`.

Isso é deliberado: Stage 10.02 **detecta e preserva a informação de ausência**. A política definitiva de migração do progresso salvo pertence aos subplanos de save/hardening; um mod temporariamente ausente não autoriza apagar silenciosamente descobertas do jogador.

## 6. Relatório por namespace e listas detalhadas

`coverage-report.md` inclui a matriz:

```text
namespace | mod | entities | flora | trees | crops | biomes | structures | dimensions | AUTO | CURATED | ADAPTER | IGNORED | ERROR
```

Abaixo da matriz, o relatório gera listas detalhadas agrupadas primeiro por tipo (`ENTITY`, `FLORA`, `TREE`, `CROP`, `BIOME`, `STRUCTURE`, `DIMENSION`) e depois por namespace, preservando `resource_location`, `translation_key` e estado editorial de cada entrada. O JSON mantém a mesma coleção integral em forma estruturada.

## 7. CI

O workflow principal executa:

- contratos Java do catálogo;
- testes do parser da modlist e do pipeline integral;
- testes do relatório de cobertura/drift;
- build NeoForge;
- dedicated-server smoke com `RPGSKILLTREE_COMPENDIUM_INVENTORY=1` e validação do JSON produzido pelo jogo.

O smoke test confirma que o coletor funciona no runtime NeoForge 1.21.1 sem depender de rede ou de mods opcionais externos.

### Evidência da pilha de compatibilidade Volcanoes

O workflow historicamente chamado `Volcanoes Full Pack Compatibility Acceptance` **não carrega a modlist canônica completa do usuário**. O instalador `.github/scripts/volcanoes/install_full_pack_acceptance.sh` monta deliberadamente uma pilha limitada de providers necessária para a aceitação cruzada de Volcanoes (worldgen, Create/Sable, Destroy, Cold Sweat, RNS/KubeJS e MineColonies, com suas dependências verificadas).

Consequentemente, o inventário produzido nesse workflow tem autoridade estritamente limitada:

- uma entrada presente comprova que aquele registry ID/provider existiu no runtime de aceitação executado;
- a ausência de uma entrada **não** prova ausência na modlist física completa;
- a coleção não deve ser chamada de censo integral do modpack;
- ela não pode, sozinha, fechar o gate de binding geográfico de `LOC-0001`;
- lore, geografia narrativa e decisão de binding continuam fora da autoridade desse artifact.

O smoke dessa pilha habilita `RPGSKILLTREE_COMPENDIUM_INVENTORY=1` e grava, dentro do artifact de nome legado `volcanoes-full-pack-compatibility-<sha>`, uma cópia auditável do inventário e da cobertura do runtime realmente carregado.

Os arquivos de consumo direto ficam em `build/full-pack-acceptance/` dentro do artifact:

```text
runtime-registry-inventory.json
compendium-coverage.json
compendium-coverage.md
compendium-inventory-scope.json
```

`compendium-inventory-scope.json` torna a limitação machine-readable com `scope_id=volcanoes_compatibility_acceptance_stack` e `complete_modpack_inventory=false`, além de registrar contagem de mods carregados, contagem de registry entries e o fingerprint do runtime.

O workflow também preserva cópias por rodada (`*-round-1.*` e `*-round-2.*`) e exige que as duas inicializações produzam o mesmo `runtime_fingerprint_sha256`, a mesma contagem de entradas e o mesmo conjunto de `(kind, resource_location, namespace, present_at_runtime)`.

Para uma auditoria que dependa de **toda** a modlist física — como a shortlist definitiva de bioma/dimensão para `LOC-0001` — é necessário executar o mesmo coletor numa instância que realmente carregue o snapshot canônico completo e reconciliar o resultado com a modlist correspondente. Até essa evidência existir, candidatos observados nesta pilha permanecem evidência mecânica parcial, nunca prova de completude.

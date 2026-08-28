# 10.14 — Testes, performance e compatibilidade

## Objetivo

Validar o Compêndio em escala de modpack real. O sistema só é útil se continuar responsivo com centenas de mods, milhares de IDs, reloads e dedicated server.

Este subplano amplia os gates do Stage 09; não cria uma segunda política de hardening.

## Matriz funcional mínima

### Catálogo

- vanilla-only/development baseline;
- conteúdo modded genérico sem adapter;
- mod com adapter;
- mod removido depois de save existente;
- datapack adicionando/alterando entrada;
- datapack inválido;
- resource pack alterando texto/alias;
- 1.000+ entradas;
- IDs com mesmo path em namespaces diferentes.

### Descoberta

- singleplayer;
- multiplayer com dois jogadores;
- relog;
- respawn;
- dimension change;
- server restart;
- evento duplicado;
- variant discovery;
- reward idempotency.

### UI

- pesquisa;
- filtros combinados;
- scroll grande;
- página com poucas seções;
- página rica;
- preview 3D;
- renderer modded com falha;
- UI scale/resoluções diversas;
- pt-BR completo.

### Optional mods

Presença/ausência individual dos adapters implementados, pelo menos:

- TFC;
- Dynamic Trees;
- FTB Quests;
- KubeJS;
- Exposure;
- demais adapters nominais que surgirem no 10.02.

## Estratégia de testes

### A — Unit tests

Cobrir modelos puros, merge, schemas, pesquisa, IDs, migração, loot summary, classificadores e permission checks.

Diretório:

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/
```

### B — Integration/GameTests quando aplicável

Usar ambiente NeoForge para:

- registries reais;
- discovery por entidade/biome/structure;
- save/reload;
- packet lifecycle;
- optional integration loading;
- server authority.

Não forçar tudo para GameTest quando teste puro for suficiente.

### C — Dedicated-server smoke

Integrar com o workflow existente `.github/workflows/alpha2-build.yml` e a infraestrutura já usada pelo projeto.

Verificar especialmente:

- nenhuma classe client-only carregada no servidor;
- catálogo constrói sem renderer;
- pt-BR/resource data não exige client;
- adapters ausentes não causam `NoClassDefFoundError`;
- reload completa;
- servidor inicia/desliga sem leak óbvio.

### D — Teste de modpack completo

Adicionar perfil/manual gate com o conjunto real de mods quando a automação de todos os JARs não puder rodar em CI por licenças/tamanho.

Registrar:

- hash da modlist;
- total de mods top-level;
- total por tipo de entrada;
- `AUTO/CURATED/ADAPTER/IGNORED/ERROR`;
- erros de provider;
- tempo de construção do catálogo;
- memória aproximada do snapshot;
- tempo de abertura/pesquisa da UI.

## Budgets de performance

Valores finais devem ser medidos antes de congelar. O plano estabelece os budgets como **requisitos mensuráveis**, não números inventados.

Métricas obrigatórias:

### Startup/reload

- tempo total para inventory + catalog build;
- tempo por provider;
- tempo de loot/worldgen enrichment;
- quantidade de entries/facts/relations;
- alloc/memória do snapshot.

### Runtime server

- nenhuma varredura global por tick;
- discovery events O(1) ou lookup indexado no caminho comum;
- inspeção de entidade apenas on-demand;
- limite/rate limit para requests de inspeção/admin.

### Client/UI

- tempo para criar search index;
- tempo de query para corpus grande;
- frame time durante scroll;
- quantidade de previews 3D vivos/cacheados;
- memória do índice.

Depois das medições baseline, registrar budgets aprovados no Stage 09/performance e fazer CI falhar para regressões grosseiras quando o ambiente permitir estabilidade suficiente.

## Instrumentação prevista

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/catalog/CompendiumMetrics.java
scripts/compendium/report_performance.py
```

Métricas/diagnósticos devem estar disponíveis em log debug ou comando de operador, sem spam no modo normal.

## Compatibilidade de reload

Testar:

1. server abre com pack A;
2. jogador descobre conteúdo;
3. datapack muda catálogo;
4. reload;
5. snapshot anterior não aparece parcialmente;
6. progresso continua ligado aos IDs válidos;
7. cliente recebe versão/delta apropriado.

## Compatibilidade de save

Fixtures versionadas:

```text
src/test/resources/compendium/saves/v1/
src/test/resources/compendium/saves/current/
```

- [ ] salvar fixtures mínimas, não mundos gigantes;
- [ ] testar migração forward;
- [ ] não exigir downgrade support;
- [ ] documentar perda impossível/decisão de tombstone.

## Conteúdo e localização

CI deve executar:

- schema validation;
- coverage report;
- pt-BR completeness;
- broken internal references;
- provenance validation;
- duplicate canonical IDs;
- `ERROR` coverage count;
- placeholders (`TODO`, `TBD`) em corpus final.

## Comandos/gates previstos

Ajustar aos tasks reais do repositório quando implementado. O mínimo esperado inclui o equivalente a:

```text
./gradlew test
./gradlew build
```

mais validators/generation e dedicated-server smoke já existentes no workflow do projeto.

## Acceptance

O subplano fecha quando a suíte cobre catálogo, descoberta, UI models, rede/save e optional mods; o modpack completo possui relatório de cobertura/performance; e o dedicated-server smoke prova que o Compêndio não introduziu dependências client-only ou hard dependencies acidentais.

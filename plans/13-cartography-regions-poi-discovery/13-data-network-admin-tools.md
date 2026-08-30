# 13.13 — Dados, rede, sincronização e ferramentas administrativas

## Objetivo

Tornar a cartografia data-driven, segura na rede e diagnosticável/recuperável sem editar NBT/SavedData manualmente.

## Dados reloadable

Planejar registries/configs para:

- `RegionType` e regras de classificação;
- `PoiCategory` e regras de classificação;
- tabelas de nomeação;
- discovery rules;
- ícones/estilos semânticos abstratos;
- quest tags;
- overrides por registry ID/mod;
- políticas de compartilhamento de intel.

Validadores devem rejeitar IDs duplicados, referências quebradas, ciclos/precedências impossíveis e ausência de lang key obrigatória.

## Reload transacional e revisão de dataset

Todo reload possui um `cartographyDatasetRevision` monotônico.

Fluxo obrigatório:

1. parse/validar o novo dataset em staging;
2. se inválido, rejeitar integralmente e manter dataset/revision anterior;
3. se válido, publicar atomicamente o novo dataset e incrementar a revision;
4. invalidar caches derivados afetados por classificação, estilo, discovery ou sharing policy;
5. reconciliar regiões/POIs conhecidos em jobs bounded quando regras semânticas mudarem;
6. recalcular a **projeção autorizada** de cada owner/body online;
7. emitir delta mínimo quando seguro ou `projection reset + snapshot` quando permissões/classificações mudarem de forma não incremental;
8. reconciliar os renderers ativos, removendo markers/overlays que deixaram de ser autorizados e atualizando os alterados.

Uma mudança de presentation metadata não precisa reclassificar geometria, mas ainda deve atualizar a projection revision/renderer. Mudanças de sharing/discovery policy são tratadas como security-sensitive e nunca podem deixar markers previamente autorizados visíveis até reconnect.

## Rede

Pacotes devem carregar apenas projeções autorizadas e deltas necessários. Separar, conceitualmente:

- snapshot inicial de intel visível;
- add/update/remove de região visível;
- add/update/remove de POI visível;
- atualização de área de busca/quest;
- invalidation total ao trocar de corpo quando necessário;
- `datasetRevision` e `projectionRevision` para detectar cliente stale.

Nunca serializar a base física completa de POIs para o cliente.

Usar versionamento de protocolo compatível com a infraestrutura de rede canônica do projeto. Pacote de revision antiga deve ser rejeitado/ignorado de forma determinística; cliente que perde sequência recebe resync autorizado, não acesso à base física.

## Admin/debug

Criar comandos equivalentes, com permissões apropriadas:

- inspecionar região atual;
- inspecionar POIs próximos sem expor isso a jogador comum;
- revelar/ocultar intel para owner/body;
- renomear região;
- reclassificar/reconciliar área bounded;
- validar aliases;
- imprimir métricas/index size;
- imprimir dataset/projection revision;
- exportar diagnóstico sem dados desnecessários;
- reparar marker projection sem alterar descoberta.

Comandos destrutivos de rebuild/reclassificação devem exigir confirmação/flag explícita e possuir backup/snapshot ou dry-run quando aplicável.

## Observabilidade

Logs estruturados para:

- classifier fallback;
- POI provider inválido;
- region merge/split migration;
- JourneyMap adapter incompatível;
- packet rejeitado;
- intel inconsistente;
- reload rejeitado/publicado e respectivas revisions;
- projection resync;
- recovery aplicado.

Evitar log spam por chunk normal.

## PT-BR

Mensagens player-facing/admin amigáveis devem ter lang keys PT-BR. IDs técnicos permanecem disponíveis em saída avançada.

## Acceptance

- pacote não contém POIs ocultos;
- delta/reconnect produzem a mesma projeção;
- comandos respeitam permissão/body scope;
- reload inválido falha fechado preservando último dataset válido;
- reload válido incrementa revision e reconcilia jogadores online sem exigir reconnect;
- mudança de sharing/discovery remove imediatamente da projection todo marker que deixou de ser autorizado;
- renderer termina consistente com a projeção server-authoritative após reload;
- pacote stale não reintroduz marker removido;
- ferramentas bounded não force-loadam dimensão inteira.
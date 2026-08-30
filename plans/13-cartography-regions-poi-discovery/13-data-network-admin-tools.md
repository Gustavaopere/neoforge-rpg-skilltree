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

## Rede

Pacotes devem carregar apenas projeções autorizadas e deltas necessários. Separar, conceitualmente:

- snapshot inicial de intel visível;
- add/update/remove de região visível;
- add/update/remove de POI visível;
- atualização de área de busca/quest;
- invalidation total ao trocar de corpo quando necessário.

Nunca serializar a base física completa de POIs para o cliente.

Usar versionamento de protocolo compatível com a infraestrutura de rede canônica do projeto.

## Admin/debug

Criar comandos equivalentes, com permissões apropriadas:

- inspecionar região atual;
- inspecionar POIs próximos sem expor isso a jogador comum;
- revelar/ocultar intel para owner/body;
- renomear região;
- reclassificar/reconciliar área bounded;
- validar aliases;
- imprimir métricas/index size;
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
- recovery aplicado.

Evitar log spam por chunk normal.

## PT-BR

Mensagens player-facing/admin amigáveis devem ter lang keys PT-BR. IDs técnicos permanecem disponíveis em saída avançada.

## Acceptance

- pacote não contém POIs ocultos;
- delta/reconnect produzem a mesma projeção;
- comandos respeitam permissão/body scope;
- reload inválido falha fechado preservando último dataset válido;
- ferramentas bounded não force-loadam dimensão inteira.
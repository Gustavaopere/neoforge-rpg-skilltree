# 10 — Compêndio Enciclopédico

Transformar a descoberta de mundo já existente no RPG Skill Tree em um compêndio in-game completo, pesquisável e persistente para fauna, flora, biomas, estruturas, chefes e outros elementos relevantes do pack.

O nome de produto adotado no planejamento é **Compêndio Enciclopédico**: ele abrange bestiário, guia de campo e dicionário técnico sem limitar o sistema a criaturas.

## Resultado esperado

O jogador abre uma interface própria e consulta conhecimento que foi descoberto no mundo. Cada entrada pode conter texto descritivo, classificação, origem, localização/ecologia, comportamento, estatísticas, variantes, drops/recursos, relações com outras entradas e condições de descoberta. Conteúdo bloqueado não deve vazar informação que o jogador ainda não conquistou.

Todo texto player-facing entregue pelo mod deve existir em **português do Brasil (`pt_br`)**. IDs, namespaces e nomes internos permanecem técnicos e só aparecem onde forem realmente úteis, como metadado ou diagnóstico.

## Relação com o runtime existente

O estágio deve reutilizar, e não duplicar, contratos já presentes no projeto:

- `DiscoveryProgress` e `ProgressionState.discoveries()` para conhecimento persistido por IDs estáveis;
- `PlayerProgressionRuntime.creditDiscovery(...)` para descoberta server-authoritative e idempotente;
- `ExplorationProgressionEvents` para bioma/dimensão já descobertos;
- o padrão `runtime/data/*Catalog` + `*Reloader` para conteúdo data-driven;
- `ProgressionSyncPayload`/`ModNetworking` como referência de sync server -> owner;
- `ClientKeyMappings` e `RpgSkillTreeScreen` como referências de boundary cliente, sem transformar o compêndio em uma aba improvisada da árvore.

Se algum requisito do compêndio ultrapassar o que `DiscoveryProgress(Set<String>)` consegue representar, o estado só pode ser ampliado por migração explícita. Não criar um segundo save paralelo sem justificar no subplano de persistência.

## Escopo editorial

O corpus inicial deve partir da modlist atual e dos registries realmente carregados. O planejamento já identifica famílias de conteúdo presentes no pack, como Alex's Mobs Continued, Alex's Caves Neo, Better End, Better Nether, YUNG's Better Structures, L_Ender's Cataclysm, worldgen/biomas e ecossistemas de árvores. Isso é apenas a semente de cobertura: a implementação deve gerar uma matriz canônica a partir da modlist vigente e confirmar cada alvo por registry/documentação antes de escrever fatos sobre ele.

Não copiar código, textos ou assets de outros mods/guias. Referências externas podem orientar UX e pesquisa, mas a implementação, os textos e os recursos do Compêndio são próprios.

## Taxonomia mínima

1. **Fauna** — animais e criaturas não hostis/neutras.
2. **Hostis e chefes** — monstros, elites, bosses e ameaças especiais.
3. **Flora** — árvores, plantas, flores, fungos, cultivos e flora aquática.
4. **Biomas e ambientes** — biomas, dimensões, cavernas e ambientes especiais.
5. **Estruturas** — estruturas vanilla e modded com descoberta espacial verificável.
6. **Recursos especiais** — blocos/itens naturais ou de exploração somente quando tiverem valor enciclopédico real.

As categorias são apresentação; o ID persistido da entrada não pode depender do nome traduzido da categoria.

## Ordem causal

1. `01-architecture-taxonomy-schema.md`
2. `02-data-loaders-catalog-validation.md`
3. `03-modlist-corpus-authoring.md`
4. `04-discovery-progression-rewards.md`
5. `05-persistence-migrations.md`
6. `06-server-network-sync.md`
7. `07-client-ui-search-crosslinks.md`
8. `08-ptbr-editorial-localization.md`
9. `09-optional-mods-registry-adapters.md`
10. `10-tests-performance-release-gate.md`

## Dependências

- Stage 01: fonte canônica de progressão/persistência precisa estar reconciliada antes de qualquer migração final.
- Stage 02: descoberta/world scaling fornece eventos e dados úteis de entidades/biomas.
- Stage 07: contratos finais de data reload, networking e UI devem ser respeitados.
- Stage 09: migrations, performance e release gates do compêndio entram na matriz de hardening geral.

## Regra de execução

O Stage 10 não está implementado por este planejamento. Nenhum arquivo recebe `✅-` até que o Acceptance do próprio subplano esteja implementado, testado, integrado e auditado na `main`.

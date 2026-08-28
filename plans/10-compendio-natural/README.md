# 10 — Compêndio Natural

O estágio 10 adiciona ao projeto um **Dicionário Enciclopédico integrado ao jogo**, com foco em fauna, flora, árvores, cultivos, biomas, estruturas, dimensões e relações ecológicas do modpack.

O nome de produto adotado no planejamento é **Compêndio Natural**. Internamente, o domínio usa `compendium`.

A proposta consolida, em uma única experiência, os melhores conceitos observados em mods de referência como Biology Dictionary, Field Guide e Wildex, sem transformar nenhum deles em dependência dura e sem copiar código ou assets sem auditoria explícita de licença/proveniência.

## Objetivos

- entregar uma única enciclopédia para o jogador, em vez de três interfaces concorrentes;
- cobrir automaticamente conteúdo vanilla e modded registrado em runtime;
- complementar a descoberta automática com conteúdo editorial curado em **português do Brasil (`pt-BR`)**;
- mostrar dados técnicos verificáveis de entidades, flora e worldgen;
- permitir descoberta, progresso, notas pessoais e integração com o RPG/quests;
- manter conteúdo desconhecido visível por fallback, sem inventar lore ou mecânicas;
- continuar funcional quando mods opcionais forem adicionados ou removidos;
- separar a enciclopédia survival, somente leitura, de ferramentas administrativas que possam alterar entidades;
- produzir relatório de cobertura para que nenhum mob, árvore, planta, bioma, estrutura ou dimensão relevante do modpack desapareça silenciosamente.

## Fonte de verdade do modpack

O inventário editorial **não será uma lista hardcoded eterna**.

No início da implementação, o Stage 10 deve gerar um snapshot a partir da modlist canônica mais recente e dos registries efetivamente presentes em NeoForge 1.21.1. O snapshot conhecido no momento do planejamento é `modlist agora atual.txt` (2026-08-26), com 553 entradas top-level incluindo o loader; JARs internos em `META-INF/jarjar`/`META-INF/jars` não contam como mods independentes.

A presença real em runtime sempre vence um snapshot antigo. Se a modlist mudar antes da implementação, o inventário deve ser regenerado.

## Tipos de entrada

O modelo deve suportar, no mínimo:

1. `ENTITY` — animais, monstros, NPCs, bosses e outras entidades vivas;
2. `FLORA` — flores, fungos, plantas, vegetação aquática e flora especial;
3. `TREE` — espécie arbórea, inclusive mapeamento para Dynamic Trees quando existir;
4. `CROP` — cultivos e plantas agrícolas;
5. `BIOME` — biomas overworld, cavernas e dimensões;
6. `STRUCTURE` — estruturas registradas/worldgen relevante;
7. `DIMENSION` — dimensões e seus ecossistemas;
8. `BLOCK_FEATURE` — blocos naturais ou features especiais quando tiverem valor enciclopédico.

## Princípios arquiteturais

- **Registry-first:** descobrir conteúdo por `ResourceLocation` e registries, não por listas de classes hardcoded.
- **Server-authoritative:** progresso de descoberta, recompensas e dados persistidos pertencem ao servidor.
- **Client presentation:** pesquisa, filtros, modelo 3D e navegação são apresentação; o cliente não autoriza descoberta nem recompensa.
- **Fail-soft:** ausência de mod opcional nunca impede startup.
- **Data-driven:** descrições, categorias, aliases, relações e overrides devem aceitar datapacks/resource packs.
- **Proveniência explícita:** dado técnico, texto editorial e inferência devem ter origem distinguível.
- **Sem invenção:** quando um fato não puder ser comprovado, a UI mostra ausência de dado ou descrição genérica, não um texto fabricado.
- **pt-BR first:** todo texto visível ao jogador fornecido pelo projeto deve existir em português brasileiro. IDs técnicos permanecem intactos.
- **Performance bounded:** nenhum scan completo de registries, loot tables ou worldgen deve ocorrer a cada frame/tick.

## Namespace e caminhos previstos

Código novo deverá ficar, preferencialmente, em:

```text
src/main/java/dev/gustavopere/rpgskilltree/compendium/
  api/
  catalog/
  discovery/
  data/
  provider/
  integration/
  network/
  client/
  admin/
```

Recursos previstos:

```text
src/main/resources/data/rpgskilltree/compendium/
src/main/resources/assets/rpgskilltree/lang/pt_br.json
src/main/resources/assets/rpgskilltree/compendium/
generated/compendium/
```

Testes previstos:

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/
```

## Ordem causal

1. `01-proveniencia-licencas.md`
2. `02-inventario-modpack.md`
3. `03-modelo-dados-identidade.md`
4. `04-descoberta-progresso.md`
5. `05-fauna-entidades.md`
6. `06-flora-arvores-cultivos.md`
7. `07-loot-dieta-reproducao-ecologia.md`
8. `08-biomas-estruturas-dimensoes.md`
9. `09-ui-modelo3d-notas.md`
10. `10-ptbr-corpus-editorial.md`
11. `11-integracoes-extensibilidade.md`
12. `12-ferramentas-operador.md`
13. `13-save-rede-cache-reload.md`
14. `14-testes-performance-compatibilidade.md`
15. `15-gate-conteudo-release.md`

Os arquivos anteriores são contratos causais. Um subplano posterior não deve inventar uma segunda representação dos mesmos dados.

## Relação com os estágios existentes

O Stage 10 **consome**, mas não substitui:

- Stage 01 para estado/progressão canônica;
- Stage 06 para adapters opcionais;
- Stage 07 para padrões de data/network/UI;
- Stage 08 para hooks de quests/recompensas;
- Stage 09 para hardening, performance e release gates.

A enciclopédia deve depender de APIs estáveis desses domínios, não acessar internals arbitrariamente.

## Definition of Done do Stage 10

O Stage 10 só pode ser considerado concluído quando:

- [ ] todos os 15 subplanos estiverem marcados individualmente como concluídos;
- [ ] o catálogo runtime cobrir todos os tipos de entrada suportados presentes no pack;
- [ ] o relatório de cobertura não tiver entradas silenciosamente perdidas;
- [ ] conteúdo pt-BR estiver validado para todas as strings próprias e para o corpus editorial entregue;
- [ ] entidades suportadas mostrarem dados técnicos com origem verificável;
- [ ] flora/árvores/cultivos e worldgen tiverem páginas navegáveis;
- [ ] descoberta e recompensas forem server-authoritative e idempotentes;
- [ ] saves forem versionados e migráveis;
- [ ] optional-mod matrix passar em ausência/presença;
- [ ] client, singleplayer e dedicated server passarem os gates aplicáveis;
- [ ] performance do catálogo/UI estiver dentro dos budgets definidos;
- [ ] nenhuma licença/proveniência proibida ou não auditada tiver sido incorporada;
- [ ] a experiência survival não expuser mutações administrativas sem permissão.

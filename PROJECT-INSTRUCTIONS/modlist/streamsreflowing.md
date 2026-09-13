# StreamsReflowing

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8148a8cfcbc0730d0474
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `StreamsReflowing-1.21.1-neoforge-2.13.1.jar`, mod id `streamsreflowing`, runtime `2.13.1`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, StreamsReflowing 2.13.1 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** StreamsReflowing
- **Arquivo JAR:** `StreamsReflowing-1.21.1-neoforge-2.13.1.jar`
- **Versão 1.21.1:** 2.13.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen
- **Função:** Sistema de hidrologia/worldgen com streams que seguem relevo, lakes/ponds em diferentes elevações, corrente direcional opcional em rivers e organic water flow configurável.
- **Dependências:** Standalone segundo documentação oficial; sem hard dependency central. Runtime exato 2.13.1 NeoForge 1.21.1 confirmado publicamente. Integra por comportamento com terrain/worldgen providers, Create water wheels e outros sistemas de água.
- **Sobreposição:** Cruza com mods atuais de terrain/river/worldgen; incompatibilidades específicas devem ser avaliadas contra os providers realmente instalados, não contra arquitetura TFC removida.
- **Compatibilidade/Riscos:** Riscos: custo de chunkgen/presets altos, carving em terrain/structures, seams old/new chunks, river tags modded, competição com outros waterways e carga de waterfall particles. 2.13.1 corrige Create water wheels após restart e falsos waterfall spray; ambos são regression gates.
- **Observações:** mod id `streamsreflowing`; runtime 2.13.1. A configuração física e preset do pack não foram lidos; compatibilidade upstream com worldgen é intenção/teste publicado, não garantia universal. Decisão Sem decisão preservada.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial StreamsReflowing 2.13.1 + documentação oficial de hydrology/presets. Dossiê de 08/09 preservado; config/preset local não foi lido.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/streams-reflowing ; https://www.curseforge.com/minecraft/mc-mods/streams-reflowing/files/all?version=1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — StreamsReflowing 2.13.1 permanece o runtime físico; hydrology/worldgen, presets, chunk lifecycle e fixes Create water-wheel/waterfall spray continuam válidos.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `StreamsReflowing-1.21.1-neoforge-2.13.1.jar`, mod id `streamsreflowing`, versão `2.13.1`. Streams Reflowing é um sistema de **hidrologia/worldgen** que gera streams e lakes dependentes do terreno e adiciona corrente direcional a corpos d'água configurados.

## 1. Identidade, versão e papel
- **Mod:** Streams Reflowing.
- **JAR físico:** `StreamsReflowing-1.21.1-neoforge-2.13.1.jar`.
- **Mod id:** `streamsreflowing`.
- **Versão:** `2.13.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Release:** build oficial exata NeoForge 1.21.1 publicada em 27/08/2026.
- **Decisão:** Sem decisão; preservada.

## 2. Authority e ownership
Streams Reflowing é authority de:
- geração/carving dos seus streams e basins/lakes;
- cálculo de corrente dos streams;
- comportamento opcional de fluxo direcional de rivers conectados;
- seus presets e parâmetros de hydrology.

O mod **não substitui** o terrain provider ou biome provider. Terralith/outros worldgen mods continuam definindo relevo/biomas; Streams Reflowing lê esse terreno e injeta hidrologia sobre ele.

## 3. Terrain-following streams
A documentação oficial afirma que streams:
- seguem contornos e elevação do terreno;
- fluem para baixo;
- formam caminhos que procuram drainage coerente;
- podem terminar/atravessar lakes e seguir para rivers/oceans;
- têm geração configurável e podem ser desativados independentemente de outras features.

Isso torna o resultado altamente dependente da seed e do terrain final dos outros providers.

## 4. Lakes e ponds em diferentes elevações
O mod gera basins/lakes/ponds em cotas variadas. A documentação descreve ajuste por aridez:
- biomas úmidos tendem a manter lakes mais cheios;
- biomas secos podem ter nível menor;
- parte dos basins pode permanecer seca em regiões áridas.

Não inferir distribuição exata sem config/seed do pack.

## 5. Directional rivers
Feature opcional: rivers vanilla ou modded conectados podem receber **corrente direcional em direção ao oceano**. Boats, itens e debris podem seguir a corrente.

A compatibilidade depende de tags/conectividade do river e da configuração; não tratar todo bloco de água modded como river suportado sem teste.

## 6. Organic water flow
Streams Reflowing pode permitir que água continue carregando direção/momentum sobre source blocks, evitando que toda corrente morra ao tocar água fonte. Essa feature é configurável separadamente.

Ela é relevante para waterfalls/cascades e para máquinas que reagem a corrente, como Create water wheels.

## 7. Quality presets e custo de worldgen
O projeto publica presets de qualidade/terrain accuracy de **Potato a Max**. Eles controlam:
- precisão com que streams seguem drenagem natural;
- extensão analisada do watershed;
- custo de geração.

O upstream recomenda presets menores em packs pesados/hardware limitado e sugere preset maior durante pregen com Chunky, pois o custo principal ocorre na geração e o resultado permanece no mundo.

A configuração real do pack não foi lida.

## 8. Compatibilidade de worldgen
O autor projeta o mod para coexistir com terrain/worldgen mods sem disputar biomes diretamente. Isso é uma intenção/resultado upstream, **não garantia universal** para qualquer stack.

Incompatibilidades públicas conhecidas incluem Terrain Diffusion e Tellus. Também há orientação específica para William Wythers, que não deve ser extrapolada para providers não testados.

Para este pack, o teste prioritário é com os providers efetivamente instalados e seed nova.

## 9. Release 2.13.1
Mudanças/fixes publicados na série 2.12.9–2.13.1 incluem:
- river não deve dead-end em pond incapaz de receber seu outflow; stream só escolhe lake plausível;
- correção para **Create water wheels** que paravam após server restart e exigiam break/replace;
- correção de spray gerado em colunas de água deslocada sem waterfall real;
- limite/config de partículas de stream passa a governar waterfalls também.

Esses itens são regression gates diretos para a versão instalada.

## 10. Client / server
- Worldgen, corrente e state hidrológico são server/world authoritative.
- Cliente renderiza água/particles e observa movimento.
- Dedicated server precisa produzir o mesmo stream/current independentemente de qual jogador primeiro observa o chunk.
- Features meramente visuais não devem alterar a topologia do stream.

## 11. Lifecycle de mundo/chunks
Validar:
- geração de chunk novo;
- pregeneration;
- chunk load/unload;
- server restart;
- stream atravessando múltiplos chunks;
- corrente calculada ao primeiro acesso a chunk;
- upgrade do mod em mundo já existente;
- fronteira entre chunk antigo e novo;
- remoção do mod em cópia de teste para entender dependência de world data.

Worldgen existente não deve ser confundido com conteúdo regenerável automaticamente após update.

## 12. Integrações concretas no pack
- **Terralith/outros terrain providers atuais:** Streams lê o relevo e precisa preservar cliffs/shoreline/carving de terceiros.
- **Create 6.0.10:** water wheels e máquinas current-driven são regression gate explícito da 2.13.1.
- **Flowing Fluids:** ambos alteram percepção/comportamento de água, mas em camadas diferentes; testar corrente/worldgen versus fluid simulation para evitar efeitos inesperados.
- **Subtle Effects/Particle Rain:** waterfalls/spray/ambiente podem somar partículas e custo de render.
- **Chunk pregeneration/performance mods:** presets altos aumentam custo de geração; medir em seed representativa.

## 13. Riscos técnicos
1. **Chunkgen cost:** preset alto em stack pesado pode aumentar hitch/generation time.
2. **Terrain carving:** stream pode cortar estruturas/terrain de forma visualmente ruim apesar da intenção de compatibilidade.
3. **Old/new chunk seams:** update ou mudança de config pode produzir descontinuidade hidrológica.
4. **Current restart:** 2.13.1 corrige water wheels pós-restart; manter teste para regressão.
5. **River tagging:** modded rivers fora das tags esperadas podem não receber direção correta.
6. **Particle load:** waterfalls/spray somados a outros VFX mods podem elevar custo client-side.
7. **Hydrology competition:** outros river/waterway datapacks podem gerar cursos conflitantes.
8. **Flow semantics:** Create/FlowingFluids podem interpretar corrente/água de modo diferente; separar visual, worldgen e fluid state ao diagnosticar.

## 14. Matriz de testes
- [ ] Dedicated server gera seed nova com 2.13.1.
- [ ] Streams seguem relevo sem subir/terminar de modo implausível.
- [ ] Lakes em biomas úmidos/secos apresentam drainage coerente.
- [ ] River conectado flui na direção correta quando feature está habilitada.
- [ ] Boats/items respondem à corrente esperada.
- [ ] Water source blocks preservam corrente quando organic flow está habilitado.
- [ ] Create water wheel continua girando após server restart sem break/replace.
- [ ] Waterfall real produz spray; água deslocada sem queda não produz falso waterfall spray.
- [ ] Stream atravessa borda de chunk e sobrevive unload/reload.
- [ ] Pregeneration com preset escolhido sem stalls/crash.
- [ ] Fronteira old/new chunks não produz cortes críticos.
- [ ] Terralith/worldgen structures permanecem aceitáveis.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 15. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão.
- CurseForge oficial: build exata `StreamsReflowing-1.21.1-neoforge-2.13.1.jar` e escopo Client & Server.
- Documentação oficial: terrain-following streams, lakes/ponds, directional rivers, organic flow, presets e compatibilidade.
- Changelog oficial 2.13.1: pond/outflow, Create water wheel restart, waterfall spray e particle-setting fixes.

## 16. Revalidação física — 11/09/2026
A modlist atual continua contendo `StreamsReflowing-1.21.1-neoforge-2.13.1.jar`, mod id `streamsreflowing`, versão `2.13.1`. Não houve drift físico desde o dossiê de 08/09.

Os fixes de Create water wheels após restart e false waterfall spray permanecem regression gates diretos. A configuração/preset do pack continua não lida e nenhum teste de seed/chunkgen foi executado nesta auditoria.

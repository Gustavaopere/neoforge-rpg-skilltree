# StreamsReflowing

> **Reauditoria física — 17/09/2026.** Versão catalogada atual: `2.13.5`. O conteúdo abaixo foi reconstruído a partir da página Notion reconciliada e da autoridade física atual; a URL da própria página Notion foi deliberadamente omitida.

## Propriedades do registro

- **Mod:** StreamsReflowing
- **Arquivo JAR:** StreamsReflowing-1.21.1-neoforge-2.13.5.jar
- **Versão 1.21.1:** 2.13.5
- **Categoria:** Worldgen
- **Função:** Sistema de hidrologia/worldgen com streams que seguem relevo, lakes/ponds em diferentes elevações, corrente direcional opcional em rivers e organic water flow configurável.
- **Dependências:** Standalone segundo documentação oficial; sem hard dependency central. Runtime exato 2.13.1 NeoForge 1.21.1 confirmado publicamente. Integra por comportamento com terrain/worldgen providers, Create water wheels e outros sistemas de água.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Chunkgen/presets, carving em terrain/structures, seams old/new chunks, river tags modded, competição hidrológica e carga de partículas. Os gates antigos de Create water wheel/restart e waterfall spray permanecem; 2.13.2–2.13.5 adicionam performance de watershed, structure blocking, Caves Reflowing e fixes de stalls/current.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/streams-reflowing ; https://www.curseforge.com/minecraft/mc-mods/streams-reflowing/files/all?version=1.21.1
- **Procedência:** modlist física de 16/09/2026 + CurseForge/Modrinth oficiais e changelog 2.13.2–2.13.5. Nenhum teste de seed/chunkgen/corrente/restart foi executado.
- **Observações:** mod id `streamsreflowing`; runtime 2.13.5. Config/preset físico do pack não foi lido. Em 16/09/2026 há 2.13.7 upstream para outras linhas 1.21.x; não é promovida aqui como versão instalada nem como compat 1.21.1 validada sem arquivo específico correspondente.
- **Atualização/Status:** READITADO EM 17/09/2026 — runtime físico atualizado de 2.13.1 para 2.13.5; dossiê reconciliado aos deltas oficiais 2.13.2–2.13.5.
- **Decisão:** Sem decisão
- **Histórico da decisão:** 
- **Sobreposição:** Cruza com mods atuais de terrain/river/worldgen; incompatibilidades específicas devem ser avaliadas contra os providers realmente instalados, não contra arquitetura TFC removida.
- **Data da última decisão:** 2026-08-27

> 🏞️ **ESCOPO CANÔNICO.** Runtime físico: `StreamsReflowing-1.21.1-neoforge-2.13.5.jar`, mod id `streamsreflowing`, versão `2.13.5`. Streams Reflowing é um sistema de **hidrologia/worldgen** que gera streams e lakes dependentes do terreno e adiciona corrente direcional a corpos d'água configurados.
## 1. Identidade, versão e papel
- **Mod:** Streams Reflowing.
- **JAR físico:** `StreamsReflowing-1.21.1-neoforge-2.13.5.jar`.
- **Mod id:** `streamsreflowing`.
- **Versão:** `2.13.5`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Release atual:** build oficial NeoForge 1.21.1 `2.13.5`, publicada em 11/09/2026. A seção 2.13.1 permanece como histórico de fixes anteriores.
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
- [ ] Dedicated server gera seed nova com 2.13.5.
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
- Modlist física de 16/09/2026: `StreamsReflowing-1.21.1-neoforge-2.13.5.jar`; CurseForge/Modrinth oficiais: série 2.13.2–2.13.5 e escopo Client & Server.
- Documentação oficial: terrain-following streams, lakes/ponds, directional rivers, organic flow, presets e compatibilidade.
- Changelog oficial 2.13.1: pond/outflow, Create water wheel restart, waterfall spray e particle-setting fixes.
## 16. Revalidação física — 11/09/2026
Naquela revalidação, a modlist ainda continha `StreamsReflowing-1.21.1-neoforge-2.13.1.jar`, mod id `streamsreflowing`, versão `2.13.1`. Esse estado permanece preservado como histórico da auditoria.
Os fixes de Create water wheels após restart e false waterfall spray permanecem regression gates diretos. A configuração/preset do pack continua não lida e nenhum teste de seed/chunkgen foi executado nesta auditoria.
## 17. Revalidação física e upstream — 13/09/2026
Naquela data, o runtime físico ainda era `StreamsReflowing-1.21.1-neoforge-2.13.1.jar`, versão `2.13.1`. Os fixes para Create water wheels após restart e falso waterfall spray permanecem regression gates históricos. A configuração/preset local não foi lida e nenhum teste de seed, chunkgen, corrente ou restart foi executado naquela revalidação. A decisão **Sem decisão** foi preservada.
## 18. Atualização instalada — 2.13.2 a 2.13.5
O changelog oficial da linha instalada registra:
- preparação de stream regions aproximadamente duas vezes mais rápida / destaque de \~50% faster watershed generation;
- chunks próximos de água esperando menos pela geração;
- retirada temporária de fallen logs/toppled trees/driftwood da 2.13.0;
- estruturas deixando de gerar em streams/lakes e locate/trackers evitando alvos que não serão gerados;
- correções de boulders/terrain cortados em padrões de chunk e de freezing em patchwork;
- streams alcançando água oceânica/river/swamp real em vez de terminar em shallow water/sea-level falso;
- suporte ao addon **Caves Reflowing** para outlets subterrâneos quando instalado;
- mobs/items/boats em Forge/NeoForge seguindo a velocidade real da corrente;
- água acima/abaixo do stream deixando de herdar corrente indevida;
- integração melhor com **Effective** e **Particular** para waterfalls reais;
- diagnóstico de stall quando preparação de stream region trava;
- 2.13.5 remove regressão de 2.13.4 no dry-outlet fix, restaura o ganho de preparação de watershed e evita preparar regiões vizinhas só para checar structures;
- regions aguardadas pelo jogador deixam de degradar drasticamente mais tarde na sessão;
- stall diagnostic deixa de ser escrito quando o jogador está parado em land já carregada;
- mundos criados em 2.12.9+ preservam streams existentes após update em vez de re-prepará-los.
Esses deltas são agora regression gates do runtime físico 2.13.5. A configuração/preset local continua não lida; compat publicada não substitui teste do stack.
### Regression gates da 2.13.5
- [ ] Dedicated server gera seed nova com 2.13.5.
- [ ] Estruturas não aparecem dentro de streams/lakes e locate/trackers não apontam para estruturas bloqueadas.
- [ ] Mobs/items/boats seguem a corrente em NeoForge na velocidade esperada.
- [ ] Mundo criado em 2.12.9+ preserva streams existentes após update.
- [ ] Create water wheels continuam funcionando após server restart.

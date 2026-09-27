# Iris

## Propriedades do registro

- **Mod:** Iris
- **Arquivo JAR:** `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar`
- **Versão 1.21.1:** `1.8.14-beta.1+mc1.21.1`
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/irisshaders ; https://github.com/IrisShaders/Iris
- **Função:** Pipeline client-side de shaders para Minecraft moderno, compatível com shader packs do ecossistema OptiFine/ShadersMod e integrado ao Sodium; controla rendering/shadow passes, não gameplay.
- **Dependências:** Cliente NeoForge 1.21.1. Runtime local possui Sodium 0.8.13; a build 1.8.14-beta.1 foi publicada para a linha Sodium 0.8. O JAR embarca via JarJar quatro módulos Forgified Fabric API (`fabric_api_base`, `fabric_block_view_api_v2`, `fabric_renderer_api_v1`, `fabric_rendering_data_attachment_v1`), além de `glsl-transformer 3.0.0-pre3` e `jcpp 1.4.14`. Distant Horizons/Euphoria são integrações, não hard dependencies aqui afirmadas.
- **Compatibilidade/Riscos:** Build beta e fortemente acoplada ao renderer/Sodium. Regression gates upstream na exata beta incluem BlockEntities ausentes de shadow pass e clouds ausentes em certas combinações. Testar Sodium 0.8.13, Distant Horizons, Euphoria, GPU/driver, resource reload e dimension changes.
- **Sobreposição:** Sobreposição somente na camada de renderização: Iris compõe-se com Sodium, Distant Horizons, Euphoria Patcher e outros renderer mods, mas não substitui suas authorities. Pode disputar shadow/depth/fog/model passes; não deve ser tratado como provider de gameplay, iluminação lógica ou world state.
- **Observações:** mod id `iris`; runtime `1.8.14-beta.1+mc1.21.1`. Build beta e client-side. O JAR traz módulos Forgified Fabric API + GLSL Transformer/JCPP internamente; não recebem entradas top-level. A lista oficial filtrada para 1.21.1 não mostra build NeoForge posterior à 1.8.14-beta.1.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial Iris 1.8.14 Beta 1 for NeoForge 1.21.1 file 8242804 + source oficial IrisShaders/Iris usado estruturalmente + inventário físico do JAR para dependências JarJar embarcadas.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Iris 1.8.14-beta.1+mc1.21.1/JAR físico reconfirmado como build NeoForge 1.21.1 mais recente localizada. Correção material: módulos Forgified Fabric API, `glsl-transformer 3.0.0-pre3` e `jcpp 1.4.14` embarcados foram documentados como runtime interno, não top-level.

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #338: JAR `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar`, mod id `iris`, runtime `1.8.14-beta.1+mc1.21.1`, SHA-1 `1bd129cf7bce65d6b1c0543045f0620c98a0f885`. O host embarca via `META-INF/jarjar` quatro módulos Forgified Fabric API (`fabric_api_base`, `fabric_block_view_api_v2`, `fabric_renderer_api_v1`, `fabric_rendering_data_attachment_v1`), `glsl-transformer-3.0.0-pre3.jar` e `jcpp-1.4.14.jar`; todos permanecem dependências internas, não itens top-level.

> **ESCOPO CANÔNICO.** Runtime físico: `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar`, mod id `iris`, versão `1.8.14-beta.1+mc1.21.1`. Iris é **infraestrutura client-side de shaders/renderização**; não é authority de gameplay, mundo ou regras de servidor.

## 1. Identidade, versão e papel
- **Mod:** Iris.
- **JAR físico:** `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar`.
- **Mod id:** `iris`.
- **Versão instalada:** `1.8.14-beta.1+mc1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta. A build física foi publicada para NeoForge 1.21.1 e atualiza a integração para Sodium 0.8.
- **Papel:** carregar e executar shader packs modernos/compatíveis com o ecossistema ShadersMod/OptiFine, fornecendo pipeline de renderização, buffers, shadow passes e opções de shader no cliente.
- **Source/version pin:** release exata 1.8.14-beta.1 confirmada; source público oficial do projeto consultado, sem pin de commit do binário físico nesta ficha.

## 2. Authority e ownership
Iris controla a **camada de shader pipeline do cliente**: seleção/carregamento de shader pack, compilação/programas, passes e recursos de render associados. Ele não deve ser tratado como authority sobre iluminação lógica, clima, entidades, partículas, distância de simulação ou física; os mods que produzem esses estados continuam sendo providers, e Iris apenas decide como o cliente os renderiza.
Sodium permanece authority de suas próprias otimizações de renderer. Distant Horizons permanece authority dos LODs. Euphoria Patcher modifica/expande shader packs compatíveis. A relação correta é composição de renderers, não substituição de ownership.

## 3. Conteúdo/superfícies funcionais
Iris não é um content mod tradicional. A superfície publicada inclui:
- carregamento e seleção de shader packs;
- compatibilidade com shader packs do ecossistema OptiFine/ShadersMod;
- compilação e execução de shader programs;
- passes de sombra e buffers de cor/profundidade;
- opções/configurações expostas por shader packs;
- integração com Sodium para performance/rendering;
- compatibilidade de pipeline com mods de renderização quando suportada.
Não foram inventados blocos, itens, registries ou classes internas sem inspeção da source da versão exata.

## 4. Sistemas internos relevantes
### Pipeline de shaders
Ao ativar um shader pack, Iris substitui/estende etapas do pipeline gráfico vanilla para produzir os buffers e passes requeridos pelo pack. Isto inclui shadow rendering e recursos consumidos por shaders. Falhas podem aparecer como objetos ausentes de sombras, clouds ausentes, corrupção de framebuffers ou incompatibilidade com efeitos de pós-processamento.
### Compatibilidade e correção visual
O projeto declara como metas: performance, correção, compatibilidade com mods, compatibilidade retroativa com shader packs existentes e melhores ferramentas para autores de shaders. Essas metas não significam compatibilidade universal com qualquer combinação de renderer/GPU/shader.

## 5. Configuração e dados
- Configurações de Iris e opções do shader pack são **client-side**.
- Shader packs/resource packs são inputs externos do pipeline; não constituem state de servidor.
- Não foi lido o arquivo de configuração local do usuário nem identificado qual shader pack está atualmente ativo. Portanto nenhuma opção concreta do cliente é afirmada como habilitada.
- Alterações de shader/resource pack devem ser testadas após resource reload e troca de dimensão.

### Dependências embarcadas da build NeoForge
O JAR físico 1.8.14-beta.1 contém em `META-INF/jarjar` quatro módulos Forgified Fabric API — `fabric_api_base 0.4.42+d1308ded19`, `fabric_block_view_api_v2 1.0.10+9afaaf8c19`, `fabric_renderer_api_v1 3.4.0+acb05a3919` e `fabric_rendering_data_attachment_v1 0.3.48+73761d2e19` — além de `glsl-transformer-3.0.0-pre3.jar` e `jcpp-1.4.14.jar`. São **dependências internas do host**, não seis mods top-level adicionais. Updates de Iris podem trocar esse conjunto sem alteração visível na pasta `mods`, então mixin/shader-preprocessor regressions devem considerar o runtime embarcado da versão exata.

## 6. Client / server
- **Client-only funcionalmente.** O servidor dedicado não depende de Iris para simular o mundo.
- O estado visual é local a cada cliente; dois jogadores podem usar shaders/configurações diferentes sem alterar a autoridade do servidor.
- Crash/render corruption de Iris deve ser diagnosticado no cliente e no stack gráfico antes de ser atribuído a gameplay/server logic.

## 7. Lifecycle de renderização
Validar:
- client boot e inicialização do renderer;
- ativar/desativar shader pack durante a sessão;
- resource reload;
- resize de janela e fullscreen;
- troca de dimensão;
- reconnect/relog;
- alteração de render scale/shader options;
- criação/destruição de BlockEntities e entidades que participam de shadow passes;
- geração/atualização de LODs do Distant Horizons.

## 8. Integrações concretas no pack
- **Sodium 0.8.13:** integração estrutural mais importante; a release 1.8.14-beta.1 foi publicada especificamente na linha Sodium 0.8. Future upgrade para Sodium 0.9 deve ser tratado como version gate, não atualização independente.
- **Distant Horizons 3.2.0-b:** composição de terreno próximo + LOD distante; testar depth/shadow/fog e transição visual.
- **Euphoria Patcher 1.10.0-r5.9:** altera shader feature set; qualquer bug visual precisa ser isolado entre Iris base, shader pack e patch.
- **Particle Effects / Particle Rain / Particular / PartiCull:** todos afetam conteúdo/quantidade/visual de partículas. Iris renderiza o resultado; não é authority de spawn/efeito dessas partículas.
- **Entity Model/Texture Features e outros renderer mods:** coexistência exige regressão visual; não assumir integração especial sem confirmação.

## 9. Riscos técnicos
1. **Build beta:** regressões de rendering são risco maior que em release estável.
2. **Sodium version drift:** Iris e Sodium são fortemente acoplados; não atualizar um isoladamente sem compatibilidade publicada.
3. **BlockEntity shadows:** há issue upstream na exata 1.8.14-beta.1 relatando BlockEntities omitidas de shadow pass em certas condições. É regression gate, não bug local confirmado.
4. **Clouds/shader state:** há report upstream na mesma beta de clouds vanilla ausentes em combinação com shaders/Sodium. Testar o shader real do pack.
5. **Distant Horizons:** render scale, depth, fog e LOD podem interagir de forma específica por shader/GPU.
6. **GPU/driver variability:** crashes de compilação ou framebuffer podem depender de vendor/driver; preservar logs e shader pack ao diagnosticar.
7. **Resource reload:** stale shader resources após troca de pack/config podem aparentar corrupção até reload/restart.

## 10. Multiplayer
Não há state de gameplay por jogador. O ponto multiplayer é garantir que:
- cliente com Iris conecta a servidor sem Iris;
- rendering local não altera colisão, hitbox ou estado do mundo;
- partículas/telegraphs server-driven continuam perceptíveis mesmo sob shader pesado;
- mudança de dimensão/relog reconstrói corretamente os recursos gráficos.

## 11. Matriz de testes
- [ ] Cliente inicia com Iris 1.8.14-beta.1 + Sodium 0.8.13.
- [ ] Dedicated server inicia sem depender de Iris.
- [ ] Shaders OFF → ON → OFF sem leak/crash.
- [ ] Troca entre dois shader packs e resource reload.
- [ ] Fullscreen/windowed e resize repetido.
- [ ] Render scale padrão e valores alternativos usados no pack.
- [ ] Overworld/Nether/End e dimensões modded.
- [ ] Distant Horizons: terreno próximo↔LOD sem depth/fog artifacts críticos.
- [ ] BlockEntities comuns/Create sob shadow pass.
- [ ] Clouds vanilla conforme configuração do shader.
- [ ] Partículas intensas com Particle Effects/PartiCull e shader ativo.
- [ ] Relog após resource reload sem renderer stale.
Nenhum item foi marcado como aprovado nesta auditoria.

## 12. Evidências
- Modlist física canônica reconferida em 12/09/2026: JAR/mod id/versão e dependências JarJar embarcadas da build NeoForge.
- CurseForge oficial: arquivo `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar`, NeoForge 1.21.1, Beta, atualização para Sodium 0.8.
- Repositório oficial IrisShaders/Iris: objetivos e arquitetura de shader compatibility/performance.
- Issues upstream específicas da 1.8.14-beta.1 usadas somente como regression gates para shadows/clouds/rendering.

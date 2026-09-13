# ServerCore

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db81aaa4bee31d28a41f1f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `servercore-neoforge-1.5.19+1.21.1.jar`, mod id `servercore`, runtime `1.5.19+1.21.1`, mixin `servercore.common.mixins.json`; Sable 2.0.5 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, ServerCore 1.5.19+1.21.1 e Sable 2.0.5 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** ServerCore
- **Arquivo JAR:** `servercore-neoforge-1.5.19+1.21.1.jar`
- **Versão 1.21.1:** 1.5.19+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Performance, QoL
- **Função:** Otimização server-side/singleplayer com patches contra lag spikes e recursos configuráveis para entity activation range, performance dinâmica, mobcaps/spawning, villager ticking, breeding caps e chunk tick distance.
- **Dependências:** NeoForge 1.21.1. Sem hard dependency externa publicada para 1.5.19. Integra por comportamento com Sable 2.0.5, MineColonies, Create/Aeronautics e demais sistemas de entidades/chunks.
- **Sobreposição:** Complementar a spark: ServerCore modifica/otimiza processamento; spark mede e atribui custo. Não duplica render/memory optimizers.
- **Compatibilidade/Riscos:** Config agressiva pode alterar gameplay. Riscos: activation-range throttling de AI modded, dynamic distances/caps, mobcap tuning, villager/breeding behavior, chunk ticking e Sable sublevel raycast. 1.5.19 corrige mobcaps pós-/sc reload.
- **Observações:** Runtime físico revalidado em 11/09/2026. A maioria das features behavior-changing permanece configurável/desabilitada por default upstream; config local não foi lida, portanto nenhum cap/range/distância foi presumido ativo.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth oficial ServerCore NeoForge 1.5.19+1.21.1 + README/CHANGELOG oficial 1.5.17–1.5.19.
- **Fonte:** https://modrinth.com/mod/servercore/version/6N9hXiRa ; https://github.com/Wesley1808/ServerCore ; https://github.com/Wesley1808/ServerCore/blob/main/CHANGELOG.md
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — ServerCore 1.5.19+1.21.1 preservado e rechecado: optimizations, activation/dynamic settings, mobcaps, Sable raycast lineage, config, commands e testes.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `servercore-neoforge-1.5.19+1.21.1.jar`, mod id `servercore`, versão `1.5.19+1.21.1`. ServerCore é **otimização e controle adaptativo do servidor**, com patches de baixo impacto por padrão e recursos mais invasivos configuráveis separadamente.

## 1. Identidade, versão e papel
- **Mod:** ServerCore.
- **JAR físico:** `servercore-neoforge-1.5.19+1.21.1.jar`.
- **Mod id:** `servercore`.
- **Versão instalada:** `1.5.19+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** server-side e singleplayer.
- **Papel:** reduzir lag spikes/custo de tick e fornecer mecanismos configuráveis para reduzir carga de entidades, chunks e spawning.

## 2. Princípio de operação
A documentação oficial separa dois grupos:
1. **patches/optimizações** que buscam reduzir lag sem mudança perceptível de gameplay;
2. **features configuráveis** que podem reduzir fortemente o custo do servidor, mas alterar comportamento dependendo dos valores usados.

O próprio projeto declara que, **por padrão**, recursos que alteram comportamento vanilla ficam desabilitados. Portanto instalar ServerCore não autoriza presumir mobcaps, view distance ou activation range customizados sem ler a config real.

## 3. Superfícies funcionais confirmadas
### Optimizations
O upstream cita otimizações para:
- chunk ticking;
- mob spawning;
- item frames;
- player logins;
- redução geral de random lag spikes.

### Entity Activation Range
Permite reduzir a frequência/custo de processamento de entidades distantes/inativas. É baseado em ideias de Spigot/Paper, com configuração adicional.

### Dynamic performance checks
Pode ajustar automaticamente conforme tick time/config:
- chunk tick distance;
- view distance;
- simulation distance;
- mobcaps.

### Villager lobotomization
Reduz custo de villagers presos em espaços 1×1, especialmente trading halls.

### Configurable mob spawning
Permite modificar spawnrate e mobcaps por spawn group e opcionalmente aplicar caps a zombie reinforcements, spawners e random ticks de Nether portals.

### Breeding caps
Limita quantidade de mobs da mesma espécie que podem ser reproduzidos dentro de um raio configurado.

### Chunk ticking distance
Reduz a distância em que chunks continuam realizando mob spawning/random ticks, conforme configuração.

## 4. Release 1.5.19 e regressões recentes
O changelog oficial 1.5.19 registra:
- adição de **breeding cap config para Happy Ghasts**;
- correção de mobcaps não atualizando após `/sc reload`.

Mudanças imediatamente anteriores relevantes ao pack:
- 1.5.18 reduziu restrições de config para merge radius de XP/items, removeu um patch de prevent-sync-load em raycasting por ser intrusivo e adicionou despawn-distance config para mobcaps;
- 1.5.17 corrigiu **incompatibilidade com Sable em ray casting dentro de sublevels** e inicialização de valores dinâmicos default.

Como o pack possui Sable 2.0.5, a correção 1.5.17 é um regression gate concreto.

## 5. Configuração
Arquivos publicados:
- `config/servercore/config.yml`;
- `config/servercore/optimizations.yml`.

A configuração efetiva do usuário **não foi lida nesta auditoria**. Portanto não são afirmados:
- activation ranges atuais;
- mobcaps atuais;
- chunk tick distance atual;
- view/simulation distance dinâmicos;
- breeding caps atuais.

## 6. Comandos e observabilidade
Comandos documentados:
- `/servercore reload` — recarrega config;
- `/servercore settings` — modifica dynamic settings in-game;
- `/servercore status` — mostra dynamic settings atuais;
- `/mobcaps` — mostra mobcaps por jogador/spawn group;
- `/statistics entities` e `/statistics block-entities` — estatísticas de performance.

Placeholders publicados expõem view distance, simulation distance, chunk tick distance, mobcap percentage, chunk count, entity count e block-entity count, inclusive variantes nearby/loaded em alguns casos.

## 7. Authority e ownership
ServerCore pode alterar **cadência e limites de processamento**, mas não deve se tornar authority do conteúdo dos mods.
- mob AI/abilities permanecem sob seus providers;
- Sable continua authority dos sublevels;
- MineColonies continua authority dos colonists;
- Create continua authority das máquinas/contraptions;
- ServerCore decide quando/quanto algumas categorias são processadas segundo config.

Isso exige cuidado para não interpretar uma entidade "inativa" por activation range como bug do mod provider.

## 8. Lifecycle
Validar:
- dedicated server boot;
- `/servercore reload` e atualização imediata de mobcaps;
- mudanças via `/servercore settings`;
- save/restart preservando config;
- chunk load/unload e player movement alterando activation/tick distance;
- world/dimension com muitos mobs;
- villagers em trading hall;
- breeding em farms;
- Sable sublevels e raycasting;
- spawn/despawn durante mudanças dinâmicas de TPS.

## 9. Integrações concretas no pack
- **Sable 2.0.5:** 1.5.17 corrigiu raycasting em sublevels; teste obrigatório.
- **MineColonies:** grande número de NPCs pode ser afetado por activation range; confirmar que colonist AI crítica não fica indevidamente throttled.
- **Create/Aeronautics:** contraptions/sublevels podem expor edge cases de entity ticking/raycast/chunk distance.
- **Spark 1.10.124:** complementar, não redundante. ServerCore otimiza/modifica processamento; spark mede e atribui custo.
- **FerriteCore/ImmediatelyFast/EntityCulling:** atuam em memória/rendering/outros subsistemas; não substituem ServerCore.

## 10. Riscos técnicos
1. **Config agressiva:** ganho de TPS pode vir com mudança perceptível de gameplay.
2. **Activation range:** AI, timers ou machines entity-based podem pausar longe do jogador.
3. **Dynamic settings:** oscilação de TPS pode alterar distâncias/caps durante gameplay.
4. **Mobcap tuning:** pode reduzir ou inflar spawns modded se grupos/tags forem tratados de forma ampla.
5. **Villager optimization:** mods que estendem villager behavior precisam de regressão.
6. **Sable raycast:** regressão de sublevels permanece teste obrigatório apesar da correção upstream.
7. **Reload correctness:** 1.5.19 corrige mobcaps pós-reload; manter teste para evitar retorno do bug.

## 11. Multiplayer e server authority
Todas as mudanças relevantes são server-authoritative. Clientes não devem decidir activation range, caps ou distances. Em multiplayer, validar múltiplos jogadores em regiões diferentes para garantir que per-player mobcaps e dynamic distances se comportam consistentemente.

## 12. Matriz de testes
- [ ] Dedicated server boot com 1.5.19+1.21.1.
- [ ] `/servercore status`, `/servercore settings`, `/mobcaps`, `/statistics entities` e `/statistics block-entities` executam sem erro.
- [ ] `/servercore reload` atualiza mobcaps corretamente.
- [ ] Config default mantém comportamento vanilla esperado.
- [ ] Entity Activation Range com mobs vanilla e modded.
- [ ] MineColonies colonists longe/perto de players.
- [ ] Trading hall/villager optimization sem trade/AI corruption.
- [ ] Breeding caps, incluindo Happy Ghast.
- [ ] Sable sublevel raycast e interação de entidades.
- [ ] Create/Aeronautics contraptions atravessando chunks.
- [ ] Stress test comparativo medido com spark antes/depois de configs.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 13. Evidências
- Modlist física canônica de 08/09/2026: JAR/mod id/versão.
- Modrinth oficial: build NeoForge 1.5.19+1.21.1.
- GitHub oficial ServerCore README: features, defaults, comandos, placeholders e arquivos de config.
- CHANGELOG oficial: 1.5.17–1.5.19, incluindo Sable raycast e mobcap reload.

## 14. Revalidação física — 11/09/2026
A modlist física atual continua contendo exatamente `servercore-neoforge-1.5.19+1.21.1.jar`, mod id `servercore`, runtime `1.5.19+1.21.1`; Sable permanece 2.0.5. Modrinth oficial reconfirma a build NeoForge 1.5.19+1.21.1 como Release server-side/singleplayer, e o changelog oficial mantém como delta 1.5.19 o fix de mobcaps após `/sc reload`. O fix Sable de raycast pertence à 1.5.17 e continua regression gate herdado.

A configuração local `config/servercore/*.yml` não foi aberta nesta auditoria; nenhuma feature behavior-changing foi marcada como ativa apenas pela presença do mod.

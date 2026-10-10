# Particle Rain

## Propriedades do registro

- **Mod:** Particle Rain
- **Arquivo JAR:** particlerain-4.0.0-beta.11+1.21.1-neoforge.jar
- **Versão 1.21.1:** 4.0.0-beta.11
- **Categoria:** Visual, QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://modrinth.com/mod/particle-rain/version/v4-beta.11%2B1.21.1-neoforge
- **Função:** Overhaul visual de clima/atmosfera por partículas: chuva/neve, wind, haze/mist, dust/sandstorm e efeitos configuráveis por contexto, sem substituir a lógica climática de outros providers.
- **Dependências:** NeoForge 1.21.1. A camada funcional é visual/client-side; nenhuma hard dependency externa adicional foi estabelecida para a build física beta 11.
- **Compatibilidade/Riscos:** Beta. Riscos: overdraw/performance, obstruction config, biome-border culling, shader fog/blending, PartiCull e composição com outros VFX. Não confundir weather particles com autoridade de seasons/temperature.
- **Sobreposição:** Complementa Particle Effects/Particular e é cullável por PartiCull; ownership principal é clima/atmosfera visual, não MobEffects nem gameplay climático.
- **Observações:** Runtime 4.0.0-beta.11, publicação oficial NeoForge 1.21.1 de 24/08/2026. Beta 11 corrige biome-border particle culling, ajusta wind e adiciona null check/config fixes, entre outros deltas.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + Modrinth oficial beta 11 + source/changelog oficial PigCart/particle-rain.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — Particle Rain 4.0.0-beta.11/JAR físico reconfirmado; beta boundary, biome-border culling, wind, shaders e lifecycle preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #435: JAR `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`, mod id `particlerain`, runtime `4.0.0-beta.11`, SHA-1 `1eb1ee4b949ad8ef25b01fcd5fb0ad5cc59ec92f`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`, mod id `particlerain`, versão `4.0.0-beta.11`, NeoForge 1.21.1. Particle Rain substitui a apresentação vanilla de chuva/neve por partículas e acrescenta haze, wind, sandstorm e efeitos configuráveis. A build instalada é **Beta**, mas é uma publicação oficial 1.21.1; a camada funcional é visual/client-side, não um simulador climático server-authoritative.
</callout>
## 1. Identidade e papel
- **Mod:** Particle Rain.
- **JAR físico:** `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`.
- **Mod id:** `particlerain`.
- **Runtime:** `4.0.0-beta.11`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** PigCart.
- **Licença:** MIT.
- **Canal:** Beta.
- **Papel:** overhaul visual de clima/atmosfera via partículas configuráveis.
- **Decisão:** Sem decisão.
## 2. Boundary: clima visual, não clima lógico
Particle Rain lê o contexto climático/bioma e o representa visualmente. Não deve receber ownership de:
- season progression;
- temperatura corporal;
- precipitação server-side;
- wetness/damage;
- regras de agricultura/clima de outros mods.
Ecliptic Seasons, Cold Sweat e outros providers continuam responsáveis por suas próprias regras; Particle Rain desenha feedback atmosférico.
## 3. Rain e snow particles
O projeto substitui a renderização vanilla por partículas com movimento mais natural. A documentação destaca rain inclinada pelo vento e comportamento visual sensível ao movimento/tormenta.
Em velocidade alta, a apresentação deve continuar coerente sem parecer presa ao mundo/câmera de forma incorreta.
## 4. Haze, mist, dust e sandstorm
A linha v4 inclui efeitos atmosféricos adicionais como haze/mist, dust e sandstorm. Esses efeitos são configuráveis e podem depender de biome, blocos, clima e regras próprias do editor.
Não inferir gameplay a partir deles: haze/dust são feedback visual até que outro provider explicitamente use o mesmo evento para gameplay.
## 5. Custom particles e editor in-game
A v4 foi reescrita para permitir customização de partículas e efeitos via config in-game. A documentação da linha inclui:
- criar/customizar partículas;
- whitelist/blacklist por bioma e bloco;
- tint customizado ou baseado em fog/water/map color;
- escolha de textures/render style/rotation;
- regras de spawn e obstrução.
Isso transforma config em conteúdo relevante do pack; backup e teste após updates são necessários.
## 6. Obstrução por blocos — beta 10+
A beta 10 adicionou opção para weather **ignorar blocos específicos** e atravessá-los em vez de ser bloqueado; barriers, fences e signs são ignorados por default publicado.
Risco: uma tag/lista ampla demais pode fazer precipitação atravessar cobertura onde visualmente não deveria. Validar roofs, glass, slabs e blocos modded do pack.
## 7. Delta exato da beta 11
O changelog da `v4-beta.11+1.21.1-neoforge` registra, entre outros:
- null check antes de acessar config, visando possível problema de loading em plataformas Forge-family;
- rolling/falling block particles;
- aumento do limite de texto na config;
- fixes de shrubs e densidade exibida na GUI;
- fix de culling em fronteira de biomas com climas similares;
- ajuste do algoritmo de wind;
- wind aplicado cumulativamente em vez de sobrescrever a velocidade da partícula;
- dust haze reativado por default.
Mudanças exclusivas de targets 26.x não são promovidas a feature específica do runtime 1.21.1 quando não se aplicam.
## 8. Biome-border culling
A beta 11 corrige partículas desaparecendo entre biomas com climas similares. Em packs de worldgen extensos como BWG + Terralith/Tectonic, fronteiras de bioma são frequentes e esse fix é particularmente relevante.
Testar caminhada/voo ao cruzar borders durante chuva/neve/haze para evitar popping ou áreas visualmente secas indevidas.
## 9. Wind e velocidade
O algoritmo de wind foi ajustado e passa a aplicar velocidade de forma acumulativa. Isso pode mudar trajetória/overdraw e aparência em storms.
A ficha não inventa fórmula/força default. Benchmark visual deve observar estabilidade, não só número bruto de partículas.
## 10. Shaders e pipeline visual
Iris/shaders podem alterar fog, alpha, blending e custo de partículas. A lineage v4 já teve fixes específicos de fog/shader, o que reforça a necessidade de regressão com o shader stack real.
PartiCull pode reduzir partículas dinamicamente; ausência visual sob FPS baixo não prova que Particle Rain deixou de funcionar.
## 11. Composição com outros particle mods
O pack também contém:
- Particle Effects;
- Particular Reforged;
- PartiCull;
- shaders/Iris.
Cada um possui ownership diferente. Particle Rain domina weather/atmosphere presentation; Particular acrescenta ambient interactions; Particle Effects representa MobEffects; PartiCull otimiza/culla.
Risco principal é densidade/overdraw e legibilidade, não duplicação funcional automática.
## 12. Client/server
Modrinth classifica o projeto geral como client-side; a página da beta 11 pode exibir supported environments Client and Server por metadata da build. Não há gameplay server-authoritative documentado nesta ficha.
Prática operacional:
- validar dedicated server sem depender de visual state;
- tratar config/weather particles como presentation local;
- não usar presença de particle como trigger de quest/perk.
## 13. Riscos
1. **Beta maturity:** v4 ainda é Beta.
2. **Particle density/overdraw:** clima pesado + outros VFX pode pressionar FPS.
3. **Block obstruction rules:** precipitação pode atravessar cobertura por config incorreta.
4. **Biome border culling:** regression gate da beta 11.
5. **Shader fog/blending:** pipeline pode mudar aparência.
6. **PartiCull interaction:** feedback pode ser reduzido sob stress.
7. **Config migration:** editor/dados mudam ao longo da v4.
8. **Weather-provider confusion:** visual não deve ser usado como fonte de verdade climática.
## 14. Matriz de testes
- [ ] Cliente NeoForge 1.21.1 inicia com beta 11 e abre `/particlerain`/config.
- [ ] Rain e snow respondem a clima sem partículas órfãs após mudança.
- [ ] Wind em calm/storm não produz velocidade explosiva ou jitter.
- [ ] Cruzar biome borders com climas similares não culla particles indevidamente.
- [ ] Haze/mist/dust/sandstorm representativos funcionam conforme config real.
- [ ] Roofs, glass, slabs, fences, signs e blocos modded respeitam obstruction rules.
- [ ] Shader ligado/desligado mantém fog/alpha legíveis.
- [ ] PartiCull sob FPS baixo reduz carga sem deixar weather permanentemente ausente após recovery.
- [ ] Particle Effects + Particular + Particle Rain simultâneos permanecem dentro do orçamento visual/performance.
- [ ] Dimension change/relog/resource reload limpam state e recarregam configs corretamente.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 15. Evidências e limites
- Modlist física: JAR, mod id/runtime e `particlerain.mixins.json`.
- Modrinth oficial: `v4-beta.11+1.21.1-neoforge`, Beta, Minecraft 1.21.1/NeoForge e changelog da build.
- Source oficial `PigCart/particle-rain`: versão `4.0.0-beta.11`, MIT e changelog v4.
- Documentação do projeto: weather particles, haze/wind/sandstorms e editor/config in-game.
- **Limite:** defaults efetivos da instância, contagens de partículas e regras customizadas locais não foram lidos; não foram inventados.

## 16. Histórico upstream v4.0.0 → v4.0.2 — 10/10/2026

**Artefato físico:** `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar` / `4.0.0-beta.11`. O CurseForge publicou builds **NeoForge Minecraft 1.21.1** da série Release **v4.0.0 (07/10) → v4.0.1 (07/10) → v4.0.2 (10/10)**. v4.0.2 é a última compatível localizada e encerra a fase beta do build físico.

### v4.0.0 — mudanças materiais do changelog
- Retira o marcador **beta** porque as partículas, exceto streaks, usam o **sistema configurável**.
- Corrige contagem de Particle Group quando spawn é cancelado; wind aplicado indevidamente a partículas com wind strength zero; storm particles que apareciam no tempo errado; rain color/water tint do dripstone e config `always`.
- Corrige **rain em chunks ainda não carregados**, haze em spyglass/low FOV, surface effects gerados dentro de blocos em coordenadas negativas, colisões que ignoravam velocidade e mudanças de textura configurada provocando reload precoce.
- Adiciona **fade types**, rotação horizontal e **heavy rain/snow durante thunderstorms**; refatora mist/ripple para partículas horizontais configuráveis, variação de spawn para efeitos compostos e texturas de chuva/neve com maior densidade.
- **WindLink compatibility:** partículas vanilla/outros mods podem responder ao vento e WindLink pode solicitar mais chuva/neve; só há integração ativa se WindLink estiver presente.
- Alguns fixes no mesmo changelog são expressamente de **Minecraft 26.x** e não devem ser atribuidos como problema existente em 1.21.1.

### v4.0.1 — sem delta NeoForge 1.21.1 confirmado
- A nota oficial descreve **crash de accessor mapping na inicialização de Fabric pré-26.1**. Não atribuir o bug a NeoForge: embora exista build publicada em 1.21.1 NeoForge, o changelog não indica correção própria para esse loader.

### v4.0.2 — fixes em 1.21.1 e outras versões
- Corrige **heightmap collision consultada no bloco errado**, velocity particles invisíveis quando não há vento e level-height hardcoded que fazia chuva aparecer sob estruturas altas em alguns servidores.
- As demais correções do changelog são explicitamente limitadas a **26.3** (crash em improved transparency) e **Minecraft 1.20.x** (flicker ao coletar itens); não atribuir essas falhas ao alvo NeoForge 1.21.1.

### QA e limite de autoridade
No pack grande com PartiCull, Iris/shader, AmbientSounds, Subtle Effects e Particle Effects/Particular, validar custo FPS, overdraw, wind e WeatherX, haze/fog, resource packs que adicionem partículas, config migration beta→Release, storm transitions, high structures + world max height, server heightmaps alteradas, chunk load, biome borders, shaders/PBR, F3+T e mudança de dimensão. Particle Rain continua **cliente/visual**, não redefine estação, temperatura ou precipitation logic do servidor.

**Fonte primária:** https://github.com/PigCart/particle-rain/blob/main/CHANGELOG.md ; **builds**: https://www.curseforge.com/minecraft/mc-mods/particle-rain/files/all?version=1.21.1

**Estado:** v4.0.2 upstream, 4.0.0-beta.11 físico; sem troca de JAR ou teste executado.

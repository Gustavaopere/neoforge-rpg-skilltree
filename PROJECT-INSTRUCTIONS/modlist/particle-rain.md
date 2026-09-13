# Particle Rain

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db81c1828ae212a45dfd34
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`, mod id `particlerain`, runtime `4.0.0-beta.11`, mixin `particlerain.mixins.json`; Iris `1.8.14-beta.1`, PartiCull `2.0` e Particular Reforged `1.5.7` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Particle Rain beta 11 e o stack visual citado nos regression gates estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Particle Rain
- **Arquivo JAR:** `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 4.0.0-beta.11
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Overhaul visual de clima/atmosfera por partículas: chuva/neve, wind, haze/mist, dust/sandstorm e efeitos configuráveis por contexto, sem substituir a lógica climática de outros providers.
- **Dependências:** NeoForge 1.21.1. A camada funcional é visual/client-side; nenhuma hard dependency externa adicional foi estabelecida para a build física beta 11.
- **Sobreposição:** Complementa Particle Effects/Particular e é cullável por PartiCull; ownership principal é clima/atmosfera visual, não MobEffects nem gameplay climático.
- **Compatibilidade/Riscos:** Beta. Riscos: overdraw/performance, obstruction config, biome-border culling, shader fog/blending, PartiCull e composição com outros VFX. Não confundir weather particles com autoridade de seasons/temperature.
- **Observações:** Runtime 4.0.0-beta.11, publicação oficial NeoForge 1.21.1 de 24/08/2026. Beta 11 corrige biome-border particle culling, ajusta wind e adiciona null check/config fixes, entre outros deltas.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + Modrinth oficial beta 11 + source/changelog oficial PigCart/particle-rain.
- **Fonte:** https://modrinth.com/mod/particle-rain/version/v4-beta.11%2B1.21.1-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Particle Rain 4.0.0-beta.11 reconstruído: weather particles, config v4, obstruction, biome borders, wind, shader/culling boundaries, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `particlerain-4.0.0-beta.11+1.21.1-neoforge.jar`, mod id `particlerain`, versão `4.0.0-beta.11`, NeoForge 1.21.1. Particle Rain substitui a apresentação vanilla de chuva/neve por partículas e acrescenta haze, wind, sandstorm e efeitos configuráveis. A build instalada é **Beta**, mas é uma publicação oficial 1.21.1; a camada funcional é visual/client-side, não um simulador climático server-authoritative.

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

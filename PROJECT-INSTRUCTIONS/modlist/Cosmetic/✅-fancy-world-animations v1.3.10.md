# Fancy World Animations — 1.3.10

## Propriedades vigentes — 27/11/2026

- **Mod:** Fancy World Animations
- **Arquivo JAR instalado (declarado):** `fwa-1.21.1-neoforge-1.3.10.jar`
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.3.10` — Release.
- **Categoria CurseForge / pasta:** Cosmetic
- **Ordem física histórica:** #295 (preservada).
- **Estado da pesquisa:** atualização documental baseada nas publicações e histórico auditado; metadata interna e runtime não inspecionados.
- **Estado no pack:** instalação declarada pelo usuário em 27/11/2026; não constitui leitura do JAR físico nem aprovação de QA.
- **Fonte:** [arquivo/catálogo oficial CurseForge](https://www.curseforge.com/minecraft/mc-mods/fwa/files/9777751).
- **Dependências / compatibilidade / riscos:** NeoForge; build 21.1.222, não mínimo efetivo. Validar animações de blocos, EMF e pistões.
- **Atualização/Status:** `1.2.31` → `1.3.10`; dependências pendentes serão reconciliadas separadamente.
- **Procedência:** declaração do usuário + publicação oficial para o alvo; sem inferência de hash ou metadata binária.

## Auditoria incremental das versões

Cadeia auditada até 1.3.8 (20/11): desempenho, erros EMF e correções diversas, preservada. 1.3.9 localizada para Forge 1.20.1, não alvo. 1.3.10 (23/11): corrige animação indevida ao colocar portas/alçapões/portões já abertos e mover alçapões abertos com pistão. [Source pinado](https://github.com/maDU59/FancyWorldAnimations/tree/833fcce3ddca2c8fd57c3a60ecb842bbeaaf55f9) declara 1.3.10; 1.3.12 de outra plataforma não elegível. Filename CF usa hífen em vez do + do rótulo.

## Histórico documental preservado

> O conteúdo a seguir antecede 27/11/2026. Expressões como “atual”, “físico”, “pendente”, versões de dependências, hashes e decisões de promoção dizem respeito à data do respectivo snapshot. Para versão instalada declarada prevalecem as propriedades vigentes acima. A análise técnica histórica continua útil quando não contradita pelo delta auditado; não constitui nova inspeção binária.

## Propriedades do registro

- **Mod:** Fancy World Animations
- **Arquivo JAR:** `fwa+1.21.1-neoforge-1.2.31.jar`
- **Versão 1.21.1:** `1.2.31`
- **Categoria:** Visual, QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/maDU59/FancyWorldAnimations/tree/neoforge-1.21.1
- **Função:** Animações client-side para blocos interagíveis do mundo, como portas, trapdoors, levers, buttons, jukeboxes e lanternas; interpola transições sem alterar o block state lógico.
- **Dependências:** Client-side NeoForge 1.21.1. Source 1.2.31 usa NeoForge baseline 21.1.191. Não é dependency server-side de gameplay.
- **Compatibilidade/Riscos:** Ghost/stale visual após unload, culling/bounding-box cache, divergência visual sob updates rápidos e conflitos com resource/model loaders. 1.2.31 melhora frustum culling e adiciona compatibilidade de occlusion com EntityCulling.
- **Sobreposição:** Pode cruzar visualmente com resource packs, Fusion e outros model/animation layers, mas não substitui lógica de blocos. Evitar duas camadas transformando a mesma geometry sem compatibilidade explícita.
- **Observações:** Client-only: o servidor/provider original permanece authority de block state, redstone e colisão. A animação deve sempre convergir para o state sincronizado, inclusive após culling, reload e reconnect.
- **Procedência:** modlist(1).txt física atual de 22/09/2026 — 587 entradas top-level incluindo o modloader — confirma `fwa+1.21.1-neoforge-1.2.31.jar`, mod id `fwa`, runtime `1.2.31` e SHA-1 `a896f8442b9ffa62345b1fcfb868dd86a894d3e1`.
- **Atualização/Status:** AUDITORIA DOCUMENTAL INCREMENTAL EM 17/11/2026 — histórico source 1.2.32–1.3.7-patch.2 revisto; releases NeoForge 1.21.1 pós-1.2.31 conciliadas até 1.3.7-patch.2. Instalação física 1.2.31 preservada; nenhum teste em runtime/upgrade executado.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #295: JAR `fwa+1.21.1-neoforge-1.2.31.jar`, mod id `fwa`, runtime `1.2.31`, SHA-1 `a896f8442b9ffa62345b1fcfb868dd86a894d3e1`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `fwa+1.21.1-neoforge-1.2.31.jar`, mod id `fwa`, NeoForge 1.21.1. O snapshot histórico do source correspondente ao JAR instalado declarava `1.2.31`; a branch oficial consultada em 17/11/2026 declara `1.3.7-patch.2`. Fancy World Animations é **client-side**: anima a apresentação de blocos interagíveis sem substituir o block state autoritativo do mundo.
</callout>

## 1. Identidade e versão

- **Mod:** Fancy World Animations.
- **JAR físico:** `fwa+1.21.1-neoforge-1.2.31.jar`.
- **Mod id:** `fwa`.
- **Versão:** `1.2.31`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Source histórico:** 1.2.31, correspondente ao JAR instalado. **Source atual de referência:** branch `neoforge-1.21.1`, 1.3.7-patch.2; não representa a instalação.

## 2. Papel no modpack

O mod adiciona transições/animações visuais a objetos do mundo que normalmente mudariam instantaneamente entre estados. A documentação oficial cita portas, trapdoors, levers, buttons, jukeboxes e lanternas oscilantes, entre outros alvos suportados. O objetivo é fluidez visual e imersão; a lógica de abertura, redstone, colisão e interação continua pertencendo ao bloco/provider original.

## 3. Modelo de autoridade

- **Servidor/Minecraft/mod do bloco:** block state, redstone, colisão, inventory e efeito da interação.
- **FWA:** interpolação/animação visual client-side derivada desse state.

A animação não pode atrasar ou adiantar logicamente uma porta, botão ou lever; o renderer deve convergir para o state recebido do servidor.

## 4. Culling e versão 1.2.31

A release 1.2.31 introduziu/corrigiu pontos diretamente relevantes:
- compatibilidade de occlusion com **EntityCulling**;
- frustum culling melhorado usando cached bounding boxes.

Como EntityCulling está no tipo de stack visual/performance deste pack, validar objetos animados entrando/saindo do frustum e sendo ocultados/reexibidos sem ghost state.

## 5. Render lifecycle

Blocos animados podem precisar de state temporário no cliente entre dois estados discretos. Esse state deve ser descartável e reconstruível. Resource reload, chunk unload, dimension change e reconnect não podem deixar transforms persistentes depois que o world state já mudou.

## 6. Client-only boundary

A distribuição é client-side. O servidor não precisa usar FWA para determinar qualquer ação. Em multiplayer, clientes com e sem o mod podem ver transições diferentes, mas precisam concordar sobre o estado lógico do bloco.

## 7. Compatibilidade com resource/model stack

Resource packs, model loaders e mods que alteram block models podem afetar pivot, geometry e bounding boxes usados pelas animações. Fusion e outras bibliotecas visuais do pack podem coexistir, mas não há integração automática presumida. Cada combinação que altere o mesmo modelo precisa de teste visual.

## 8. Multiplayer

O caso crítico é mudança rápida de state por redstone ou dois jogadores interagindo enquanto a animação anterior ainda está em andamento. O client deve cancelar/reorientar a interpolação para o novo state autoritativo, não acumular uma segunda transição independente.

## 9. Riscos técnicos

- ghost visual após chunk unload/reload;
- culling esconder objeto animado incorretamente;
- cached bounding box ficar stale;
- animação divergir do block state real em updates rápidos;
- model/resource pack alterar pivot ou geometry esperada;
- duas camadas de animação transformarem o mesmo bloco;
- regressão de FPS em áreas com muitos blocos animados;
- classloading client indevido por integração comum/server.

## 10. Matriz de testes obrigatória

- [ ] Client boot com FWA 1.2.31.
- [ ] Dedicated server sem depender do mod para gameplay.
- [ ] Porta/trapdoor/button/lever em interação manual e redstone rápida.
- [ ] Jukebox e lanternas/alvos suportados no resource stack real.
- [ ] EntityCulling ativo: entrar/sair de occlusion sem ghost.
- [ ] Frustum edge: bloco animando enquanto entra/sai da câmera.
- [ ] Chunk unload/reload e dimension change durante animação.
- [ ] Reconnect com bloco no estado final correto.
- [ ] Dois jogadores alterando o mesmo bloco em sequência rápida.
- [ ] Resource reload e coexistência com model loaders/resource packs reais.

## 11. Evidências e limites

**Source da instalação (histórico):** release 1.2.31 e pin documental original. **Branch upstream atual (17/11/2026):** `maDU59/FancyWorldAnimations:neoforge-1.21.1` declara 1.3.7-patch.2; esse valor não substitui o JAR físico 1.2.31.
**Release oficial:** CurseForge file 8221257, NeoForge 1.21.1, com fixes de EntityCulling e frustum culling.
**Limite:** não foi feita uma enumeração exaustiva de todos os blocos suportados pelo registry interno; apenas categorias/alvos sustentados pela documentação oficial são afirmados.
**Nenhum teste de runtime foi executado nesta catalogação.**

## 12. Auditoria incremental — upstream 1.2.32 → 1.3.7-patch.2 (17/11/2026)

**Baseline físico imutável:** `fwa+1.21.1-neoforge-1.2.31.jar`, mod id `fwa`, runtime `1.2.31`, SHA-1 `a896f8442b9ffa62345b1fcfb868dd86a894d3e1`, posição física **#295** do último `modlist(1).txt` (587 top-level incluindo NeoForge). O nome deste dossier identifica a **instalação**, e não a última versão disponível.

**Autoridades verificadas:** [CurseForge Fancy World Animations, projeto 1452982](https://www.curseforge.com/minecraft/mc-mods/fwa) e [repositório do autor, branch neoforge-1.21.1](https://github.com/maDU59/FancyWorldAnimations/tree/neoforge-1.21.1). O projeto é **Cosmetic** no CurseForge (única categoria temática); a pasta atual `Cosmetic/` permanece correta. O código upstream atual usa `minecraft_version=1.21.1`, `mod_version=1.3.7-patch.2` e baseline `neo_version=21.1.222` ([gradle.properties](https://github.com/maDU59/FancyWorldAnimations/blob/5b5c3aa676ca6f092d934c833d403d04f4d7e303/gradle.properties)). A autoridade física conhecida do pack fixa NeoForge `21.1.250`, superior ao mínimo declarado **no source**; isso não prova a compatibilidade final de todos os mixins/renderers.

### 12.1. Inventário das publicações especificamente NeoForge / Minecraft 1.21.1

| Versão | Data | Evidência | Impacto documentado |
|---|---|---|---|
| **1.2.31 — instalada** | 09/06/2026 | [CurseForge 8221257](https://www.curseforge.com/minecraft/mc-mods/fwa/files/8221257) | Baseline já documentado: integração EntityCulling/occlusion e otimização de frustum/bounding boxes. |
| **1.3.4 — release identificada** | 02/11/2026 | [CurseForge 9405513](https://www.curseforge.com/minecraft/mc-mods/fwa/files/9405513) | Menor carga de CPU com muitas animações simultâneas. A build carrega a evolução do source anterior; testes de cada feature no binário não foram feitos. |
| **1.3.7 — release identificada** | 13/11/2026 | [Catálogo oficial de arquivos](https://www.curseforge.com/minecraft/mc-mods/fwa/files/all), [source exato 1.3.7](https://github.com/maDU59/FancyWorldAnimations/blob/e5379c5d6dba1b5bac3155909a14388baafcedb2/CHANGELOG.md) | Otimizações para grandes mundos, correção de compatibilidade com Entity Culling atual, melhora de blocos modded e suporte a lanternas Hardcore Torches. |
| **1.3.7-patch.2 — última compatível localizada** | 17/11/2026 | [CurseForge 9663634](https://www.curseforge.com/minecraft/mc-mods/fwa/files/9663634), [source](https://github.com/maDU59/FancyWorldAnimations/blob/5b5c3aa676ca6f092d934c833d403d04f4d7e303/CHANGELOG.md) | Inclui Patch 1 (corrige crash com lecterns de Scholar) e Patch 2 (corrige render type incorreto em animações modded). **Publicação comprovada para NeoForge 1.21.1.** |

**Separação de distribuição:** versões `1.2.32`–`1.2.40`, `1.3.0`–`1.3.3` e `1.3.5`–`1.3.6` tiveram mudanças públicas no source/projetos para **outras versões do Minecraft e/ou outros loaders**; não foram localizadas como JARs individuais NeoForge **1.21.1** na consulta das publicações oficiais. A correção `1.3.7-patch.1` aparece no changelog cumulativo da 1.3.7-patch.2 e foi distribuída para outros alvos, mas **não há confirmação de JAR 1.21.1/NeoForge separado**. Evitar inventar um JAR por número de versão. Para entender a evolução que culmina na 1.3.4/1.3.7-patch.2, todos os degraus de source são revisados abaixo, sem declarar portabilidade/runtime onde não houve inspeção de binário.

### 12.2. Evolução intermediária do source (publicações de outras plataformas; não instaladas)

| Versão/etapa | Mudança comprovada e relevância para o dossier | Fonte do autor |
|---|---|---|
| **1.2.32** | Coloca **Domum Ornamentum** na blacklist embutida porque suas animações não funcionavam corretamente; fornece API para terceiros sobrescrever a blacklist quando existir camada de compatibilidade. **Relevante**, pois Domum Ornamentum está presente no snapshot do pack. | [CHANGELOG 1.2.32](https://github.com/maDU59/FancyWorldAnimations/blob/464f59ef0f7e4d4e06d275bbc20338b5879c929d/CHANGELOG.md) |
| **1.2.33** | Port para Minecraft 26.2; sem alteração funcional demonstrada para NeoForge 1.21.1. **Não aplicável isoladamente.** | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/603fec89862eb9b29b9f22a923718d7bd7454958/CHANGELOG.md) |
| **1.2.34** | Corrige blocos animados renderizados em tamanho exagerado. **Correção visual relevante** quando a implementação estiver efetivamente presente no target. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/84a82ebc3800e6d3e0028908948d9abe97601e8d/CHANGELOG.md) |
| **1.2.35** | Desativa automaticamente animações de chests/shulker boxes quando **FA: Objects** está presente, e de bells com **BBE/OBE**. **Compatibilidade condicional**; presença desses consumers não comprovada no inventário. | [CHANGELOG na revisão desta etapa](https://github.com/maDU59/FancyWorldAnimations/blob/7802059ea57545826b41a39d08b71e30ef421845/CHANGELOG.md) |
| **1.2.36** | Melhora visual e compatibilidade da animação de bells e corrige bugs não individualizados. Não inventar a natureza dos fixes genéricos. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/0ae4d303bc1823ff86e6234a2640d0471c24a63d/CHANGELOG.md) |
| **1.2.37** | Configuração para desativar animações acionadas por redstone; compatibilidade com **Malisis Doors Reborn**. Relevante para sequência rápida de block states e opções do pack. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/662f1858fd244d97954fb707e063a93f0663c553/CHANGELOG.md) |
| **1.2.38** | Efeitos de clima no swing de lanterns/chains; forma de outros mods fornecerem oscilação (ex.: vento); corrige crash com **Json Things**. **Feature/API e compatibilidade relevantes**, mas não presumir que todos esses módulos estão ativos em NeoForge 1.21.1. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/128f59303cefe29d3851c2823a89b29e66eff0e3/CHANGELOG.md) |
| **1.2.38-patch.1** | Corrige dependência dos efeitos de clima em relação ao framerate. | [Changelog cumulativo do ciclo 1.2.38](https://github.com/maDU59/FancyWorldAnimations/blob/128f59303cefe29d3851c2823a89b29e66eff0e3/CHANGELOG.md) |
| **1.2.38-patch.2** | Corrige dependência dos efeitos de clima em relação ao número de passes de render por frame. | [Changelog cumulativo do ciclo 1.2.38](https://github.com/maDU59/FancyWorldAnimations/blob/128f59303cefe29d3851c2823a89b29e66eff0e3/CHANGELOG.md) |
| **1.2.39** | Melhora visuals com shaders; mudança de pipeline visual, sem claim de FPS numérico. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/00df2c28034ad23df0e78cc778138dc7768caecb/CHANGELOG.md) |
| **1.2.39-patch.1** | Corrige crash com alguns shaders; os shaders específicos não são identificados no changelog. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/59857fb37cbc6896e3676c9812e64fe421733080/CHANGELOG.md) |
| **1.2.40** | Corrige crash ao segurar bells. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/dbe4e42ba53b7d79824b968e72fb53fef06a70bd/CHANGELOG.md) |
| **1.3.0** | Melhora cancelamento/reversão da animação em transições rápidas (ex.: fechar porta enquanto abre); chest DEFAULT passa a se assemelhar ao vanilla; suporte a **VulkanMod**, controles de velocidade/intensidade de swing e fix de crash ao pressionar button. **Mecânicas de render/config relevantes.** | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/a68e8ebad8c0baa00260b70b9a665386acbca3c7/CHANGELOG.md) |
| **1.3.1** | Otimiza chains e lanterns; permite blacklist oriunda de **resource packs**, além do arquivo local; adiciona es_ar. **Importante** para precedência de configurações e packs visuais. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/18b648ade60283f6813cfbfdb7768596d9feda25/CHANGELOG.md) |
| **1.3.2** | Integra **MoreCulling** sem desativar por completo Block State Culling. **Compat condicional** (MoreCulling não confirmado como top-level no snapshot). | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/525db5b46bdc73c7148e286b3e70b5c3003fdee0/CHANGELOG.md) |
| **1.3.3** | Adiciona oscilação de **hanging signs** na linha publicamente distribuída para outras plataformas. Há código upstream relacionado antes do merge NeoForge da 1.3.4; não é prova de artefato próprio NeoForge 1.21.1. | [CurseForge 9364115 — Fabric/Quilt 26.1.x](https://www.curseforge.com/minecraft/mc-mods/fwa/files/9364115), [source](https://github.com/maDU59/FancyWorldAnimations/commit/5c397e7c61cabd729c4ebb6d42c79321d776fd9c) |
| **1.3.4** | Reduz carga de CPU em cenas com muitas animações simultâneas. **Release efetivamente publicada para NeoForge 1.21.1** em 02/11. | [CurseForge 9405513](https://www.curseforge.com/minecraft/mc-mods/fwa/files/9405513) |
| **1.3.5** | Compatibilidade melhorada com modded blocks e suporte a lanternas do **Hardcore Torches**. Fonte declara essa etapa, mas não comprova publicação individual 1.21.1/NeoForge. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/03a53afcd8071558107874c0327f3654fa16f114/CHANGELOG.md) |
| **1.3.6** | Melhora compatibilidade de lecterns com outros mods (commit menciona **Cinematic Interactions**), identificação do disco em jukebox multiplayer e compatibilidade **Scholar**. Não assumir que Scholar ou Cinematic Interactions estão instalados. | [CHANGELOG](https://github.com/maDU59/FancyWorldAnimations/blob/970e13f0d1901e5afb1085ee49a70e308655a97d/CHANGELOG.md) |
| **1.3.7** | Performance em mundos com muitos blocos animados; Entity Culling atualizado; estabilidade de render de blocos modded e suporte Hardcore Torches. A árvore source revela mapa de animações em buckets e redução de alocações; sem benchmark no pack. **Publicação NeoForge 1.21.1 identificada.** | [CHANGELOG 1.3.7](https://github.com/maDU59/FancyWorldAnimations/blob/e5379c5d6dba1b5bac3155909a14388baafcedb2/CHANGELOG.md), [commit de buckets](https://github.com/maDU59/FancyWorldAnimations/commit/d542f56431d4fa727f85b0d06a27eca8cafb90f7) |
| **1.3.7-patch.1** | Corrige crash envolvendo lecterns do **Scholar**; nota oficialmente incluída no changelog cumulativo. Sem prova de JAR independente para o alvo NeoForge 1.21.1. | [CHANGELOG cumulativo](https://github.com/maDU59/FancyWorldAnimations/blob/5b5c3aa676ca6f092d934c833d403d04f4d7e303/CHANGELOG.md) |
| **1.3.7-patch.2** | Corrige modded animations desenhadas com **RenderType incorreto**; a correção consta em release oficial NeoForge 1.21.1. | [CurseForge 9663634](https://www.curseforge.com/minecraft/mc-mods/fwa/files/9663634), [commit](https://github.com/maDU59/FancyWorldAnimations/commit/5b5c3aa676ca6f092d934c833d403d04f4d7e303) |

**Limite de prova:** a tabela intermediária descreve a evolução oficial no source e/ou publicações para outros targets; ela **não** transforma esses números em versões instaláveis separadamente no Minecraft 1.21.1/NeoForge. Funcionalidades herdadas do código-fonte precisam ser verificadas no artefato candidato para garantir que branch merges, flags ou mixins não as desativaram no port.

## 13. Consequências para o modpack e gates antes do upgrade

- **Domum Ornamentum (presente):** verificar exclusão de animações inadequadas via blacklist e a API de override; não presumir que todas as animações em blocos Domum passaram a funcionar.
- **Entity Culling (presente na autoridade física como 1.10.5):** a última release FWA declara correção com a linha atual do Entity Culling, mas não prova que a combinação física antiga **1.10.5** ou um upgrade simultâneo para **1.12.1** funciona sem regression. Testar occlusion, frustum, bounding boxes, reload e chunk boundaries.
- **Fusion 1.3.15a e Polytone 4.4.0 (presentes):** testar RenderType/translucência, texturas/modelos modificados, shader on/off e prioridade de resource packs; a correção 1.3.7-patch.2 deve ser inspecionada junto a esses mods, não presumida universal.
- **Redstone e controles:** verificar se reversões rápidas de portas/fence gates, settings para redstone e parâmetros de swing refletem a versão instalada depois de homologação. FWA permanece um **renderer client-side**; nenhuma mudança de block state, colisão ou regras do servidor deve ser atribuída a ele.
- **Blacklists:** além de `.minecraft/config/fwa.json`, o README upstream documenta `fwa-blacklist.json` (opcional), e a etapa 1.3.1 acrescenta leitura de blacklists de resource packs. Validar precedência/merge e entradas por ID reais antes de criar bloqueios por mod; não alterar configs nesta auditoria.
- **Consumers não comprovados como top-level no snapshot:** Hardcore Torches, Scholar, Cinematic Interactions, FA: Objects, MoreCulling, VulkanMod e BBE/OBE devem ser tratados como **gates condicionais**, não integrações já testadas.
- **Loader:** código upstream atual declara baseline NeoForge `21.1.222`; o último snapshot do projeto registra `21.1.250`, portanto o **número do loader conhecido atende ao mínimo declarado pelo source**, mas compatibilidade binária do JAR final e outros requisitos devem ser comprovados.
- **QA ainda não executada:** lançamento de cliente; servidor dedicado sem dependência indevida; testes com mods de render/culling instalados; itemframes, bells, cauldrons, lecterns, chests, signs, jukeboxes, lanternas; reload, reconnect, chunk unload, updates rápidos e smoke visual com shaders. Não houve substituição de JAR, medição de CPU/FPS ou teste de save.

**Conclusão documental de 17/11/2026:** pesquisa de versões intermediárias concluída e diferenças upstream relevantes registradas até **1.3.7-patch.2**. O único artefato comprovado como instalado continua sendo **`fwa+1.21.1-neoforge-1.2.31.jar` / 1.2.31**; não trocar o nome do dossier para a última release disponível. O dossier permanece no caminho oficial `Cosmetic/`, sem cópia ou mudança de categoria. **Instalação e homologação física continuam pendentes e separadas da auditoria documental.**

## Auditoria incremental — Fancy World Animations 1.3.8 (22/11/2026; upstream)

A baseline física segue `fwa+1.21.1-neoforge-1.2.31.jar` (#295), runtime `1.2.31`. O histórico intermediário até **1.3.7-patch.2** foi auditado anteriormente.

A linha **1.3.8 para NeoForge/Minecraft 1.21.1** está identificada no [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fwa/files/all) e na [branch oficial neoforge-1.21.1](https://github.com/maDU59/FancyWorldAnimations/tree/neoforge-1.21.1), cujo [gradle.properties](https://github.com/maDU59/FancyWorldAnimations/blob/neoforge-1.21.1/gradle.properties) declara `minecraft_version=1.21.1` e `mod_version=1.3.8`. O [changelog da mesma branch](https://github.com/maDU59/FancyWorldAnimations/blob/neoforge-1.21.1/CHANGELOG.md) anuncia **melhorias de performance**, correção de erro/aviso do **EMF** na criação de modelos (#108) e correções diversas (#109). Não detalha as demais mudanças nem informa benchmarks verificáveis; não inventar comportamento ou FPS. `1.3.7-patch.3` da linha Forge 1.20.1 não é um upgrade intermediário comprovado neste alvo.

**QA pendente:** teste em cliente com EntityCulling, EMF, Fusion/Polytone, shaders, modelos de blocos, recarga de recursos e cenas com muitas animações. Não foram instalados ou analisados binários 1.3.8. Versão 1.2.31 e pasta `Cosmetic` permanecem.

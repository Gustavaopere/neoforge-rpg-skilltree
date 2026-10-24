# BetterEnd: New Dawn

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#71**: `BetterEnd-21.0.34.jar`, mod id `betterend`, runtime `21.0.34`, SHA-1 `149b73179ea63bf777315a7c850b2eba65c554bd`.

> **Reauditoria física — 19/09/2026.** JAR top-level reconfirmado na modlist física atual: `BetterEnd-21.0.34.jar`, versão `21.0.34`. Paridade Notion → GitHub revalidada; URL da própria página Notion removida.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na origem:** Integrado ao Github
- **Autoridade física usada:** `modlist(1).txt` — 586 mods top-level
- **Auditoria de migração Notion → GitHub:** 2026-09-19

## Propriedades do banco

- **Mod:** BetterEnd: New Dawn
- **Arquivo JAR:** BetterEnd-21.0.34.jar
- **Versão 1.21.1:** 21.0.34
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Worldgen, Exploração, Mobs
- **Função:** Grande expansão do End no stack BetterX/New Dawn: biomas, flora, materiais, mobs, estruturas, embarcações, worldgen e progressão/equipamentos próprios.
- **Dependências:** BCLib: New Dawn + WorldWeaver: New Dawn + WunderLib: New Dawn. No pack: BCLib 21.0.26, WorldWeaver 21.0.25 e WunderLib 21.0.10 estão fisicamente presentes.
- **Sobreposição:** Provider amplo do End. Pode coexistir com estruturas/expansões adicionais, mas conflitos devem ser avaliados por biome/structure/feature concreta; não é substituível por uma library de worldgen.
- **Compatibilidade/Riscos:** Worldgen amplo do End. Riscos: conflito de biome/feature/structure placement, registry/holder stale, transição de versões em mundos existentes, render/particle client-side e sobreposição de End com YUNG's Better End Island/outros mods. 21.0.34 corrige crash de partículas server-side; a upstream 21.0.35 corrige cave-biome generation e adiciona configs ausentes para desativar dois biomas novos.
- **Observações:** Runtime físico 21.0.34 NeoForge 1.21.1. Upstream publicou 21.0.35 em 16/09/2026 com fix de geração de cave biomes e opções de configuração ausentes para desativar dois biomas novos; não está instalada.
- **Procedência:** modlist física atual + metadata runtime + Modrinth/CurseForge oficiais BetterEnd: New Dawn 21.0.34 instalada e 21.0.35 posterior + stack New Dawn já auditado. Revalidado em 01/10/2026; JAR/runtime permanecem `BetterEnd-21.0.34.jar` / `21.0.34`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/betterend-new-dawn
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — BetterEnd físico permanece 21.0.34. A release 21.0.35 foi comparada e os deltas de cave-biome generation/config foram incorporados abaixo.
- **Histórico da decisão:** Em 22/08/2026, BetterEnd foi desativado temporariamente apenas para isolamento junto de WorldWeaver e BetterNether. Após identificar e remover FirmaTerrain 1.0.0, o stack BetterX/New Dawn foi reativado e funcionou. Decisão final desta investigação: MANTER BetterEnd; a desativação anterior foi apenas diagnóstica.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `BetterEnd-21.0.34.jar`, mod id `betterend`, runtime `21.0.34`, NeoForge 1.21.1. A release 21.0.34 exige BCLib, WorldWeaver e WunderLib; os três componentes New Dawn estão presentes no pack. A decisão curatorial histórica **MANTER** permanece válida.

## 1. Papel e autoridade
BetterEnd: New Dawn é uma expansão ampla da dimensão **The End**. É authority do conteúdo BetterEnd que registra — biomas, flora, materiais, estruturas, mobs, blocos, itens e regras próprias de geração — enquanto Minecraft/NeoForge e o stack New Dawn fornecem registry/worldgen lifecycle.

BCLib/WorldWeaver/WunderLib são infraestrutura; não devem ser tratados como substitutos do conteúdo BetterEnd. Outros mods de End podem coexistir, mas não devem reaplicar biome/feature placement do BetterEnd.

## 2. Stack New Dawn confirmado no pack
- BetterEnd 21.0.34.
- BCLib 21.0.26.
- WorldWeaver 21.0.25.
- WunderLib 21.0.10.

A release oficial 21.0.34 declara BCLib, WorldWeaver e WunderLib como required content. Remover qualquer componente exige verificar a cadeia inteira, não apenas o JAR BetterEnd.

## 3. Biomas e worldgen
O projeto mantém a proposta BetterEnd de substituir a monotonia do End por biomas e ecossistemas distintos. A linha New Dawn atual confirma conteúdo moderno em 1.21.1, incluindo:
- **Flower Islets**;
- **Waterfall Ponds**;
- ilhas/pond structures com contornos, quedas d'água e vegetação específica;
- rework de **End lakes** com geração segura por chunk e shores materiais por bioma;
- Dragon-Helix trees em ilhas de lago;
- Amaranita patches em ilhas floridas.

Worldgen já materializado em chunks antigos não deve ser reinterpretado como se tivesse sido gerado pela configuração atual. Testes devem sempre distinguir chunks novos de chunks existentes.

## 4. Estruturas
A linha atual inclui **Ruined End Bridges**, estruturas que atravessam ilhas próximas. Qualquer integração com YUNG's Better End Island ou outros providers de estrutura deve ocorrer por placement/spacing real, não por exclusão temática genérica.

Structure starts, loot e proteção pertencem ao provider que registra a estrutura. Não duplicar loot via evento global só porque a estrutura é detectada.

## 5. Flora e ecossistemas
New Dawn confirma, entre as adições recentes:
- Dragon-Helix trees;
- Amaranita patches;
- vegetação dependente de água nas novas ilhas/lakes;
- vines e vegetação submersa/canopies nas regras de lago;
- suporte de tags de solo/sobrevivência para plantas BetterEnd.

A ficha não converte isso em uma contagem binária completa de todas as plantas históricas do BetterEnd; o mod é grande e a catalogação é por subsistemas e conteúdo relevante da build atual.

## 6. Madeira, blocos e transporte
Conteúdo atual confirmado inclui:
- boats BetterEnd;
- chest boats BetterEnd;
- **End Lotus Raft**;
- Chiseled Bookshelves para wood sets do End;
- famílias de blocos e materiais associadas aos biomas BetterEnd.

Boat/raft movement usa regras de entidade/veículo do provider; não criar uma segunda lógica de transporte em compat externa.

## 7. Equipamentos e enchantments
A linha New Dawn inclui **Resonance I** e **Resonance II** para Hammers, permitindo mineração em áreas 3×3×3 e 5×5×5 respectivamente conforme documentação do projeto.

Riscos:
- break events dispararem uma vez por bloco e não duas vezes por operação;
- proteção/claims receberem cada tentativa de quebra;
- durability/loot/fortune/silk touch seguirem o pipeline do provider;
- não tratar o volume visual de blocos quebrados como um único block-break vanilla se o provider executa múltiplas quebras reais.

## 8. Mobs e entidades
BetterEnd é classificado pelo projeto também como mod de mobs, além de worldgen. Entidades históricas e conteúdo biológico fazem parte do ecossistema da dimensão. Para integrações próprias, ownership de spawn, AI, loot e biome eligibility deve permanecer no registry/datapack BetterEnd.

Não foi usada nesta etapa uma enumeração binária integral de todas as entidades da 21.0.34; portanto nenhuma contagem artificial é publicada.

## 9. Dados, tags e registries
Worldgen moderno depende de registries/data. Regras obrigatórias:
- resolver `Holder`/registry entries no lifecycle correto;
- não manter referência stale atravessando datapack reload;
- tags de solo, blocos, itens e biome eligibility devem ser lidas do estado final carregado;
- recipes/loot tables devem ser alterados por datapack/KubeJS apenas quando houver objetivo explícito, sem duplicar data do provider.

## 10. Client/server
- biome placement, feature/structure generation, loot e entity spawn são server/common;
- fog, particles, models, shaders internos e efeitos visuais são client-side;
- a release 21.0.34 corrige especificamente um **server-side particle crash**, logo side separation é um ponto de regressão real;
- cliente não deve determinar se um biome/estrutura existe.

## 11. Compatibilidade no pack
### BCLib / WorldWeaver / WunderLib
São dependências estruturais do mesmo ecossistema e devem ser versionadas/testadas em conjunto.

### YUNG's Better End Island
Pode alterar a experiência inicial/ilha do End, enquanto BetterEnd cobre ecossistema e geração muito mais ampla. A sobreposição precisa ser avaliada em estruturas/placement concretos.

### Outros frameworks de worldgen
Biolith, TerraBlender, Lithostitched e outras APIs não são substitutos automáticos. Se um mod consumidor usa framework próprio, cada authority deve manter seus registries e placement rules.

## 12. Lifecycle de mundo
Validar:
1. criação de mundo novo;
2. entrada no End pela primeira vez;
3. geração de chunks novos depois de update/config change;
4. server restart;
5. datapack reload onde permitido;
6. teleport/dimension travel;
7. chunk unload/reload perto de estruturas/lakes;
8. resource reload para assets client-side.

Não usar reload para tentar regenerar chunks existentes.

## 13. Riscos técnicos
1. Registry/holder stale após reload.
2. Estruturas/biomas de dois providers disputando o mesmo espaço.
3. Chunk borders entre geração antiga e nova.
4. Server/client classloading de particles/render.
5. Hammer area mining causando double-break ou bypass de proteção.
6. Loot injection duplicado por compat externa.
7. Dependência New Dawn incompatível após atualização isolada.

## 14. Matriz de testes
1. Dedicated server boot com BCLib 21.0.26, WorldWeaver 21.0.25 e WunderLib 21.0.10.
2. Criar End novo e localizar biomas/estruturas BetterEnd.
3. Gerar novos chunks depois de restart sem duplicação de features.
4. Flower Islets/Waterfall Ponds e lakes sem erro de placement.
5. Ruined End Bridges em chunk borders.
6. Boats/chest boats/End Lotus Raft em multiplayer.
7. Resonance I/II com proteção, Fortune/Silk Touch e durability.
8. Resource reload e partículas sem classe server-only/client-only indevida.
9. Interação com YUNG's Better End Island sem pressupor conflito onde não há evidência.

## 15. Evidência
- modlist física atual: BetterEnd 21.0.34 + stack New Dawn presente;
- release oficial BetterEnd: New Dawn 21.0.34 para NeoForge 1.21.1;
- documentação New Dawn sobre Flower Islets, Waterfall Ponds, Ruined End Bridges, Dragon-Helix, Amaranita, End lakes, boats/raft, bookshelves e Resonance;
- changelog 21.0.34: correção de server-side particle crash;
- histórico curatorial preservado no Notion: desativação de 22/08 foi diagnóstica; decisão final MANTER.

> 🌌 Authority canônica: BetterEnd = conteúdo e geração do End; BCLib/WorldWeaver/WunderLib = infraestrutura New Dawn. A ficha não transforma libraries em providers de conteúdo nem mascara a diferença entre chunks antigos e novos.


## 16. Atualização upstream 21.0.35 — não instalada

A autoridade física continua em **BetterEnd: New Dawn 21.0.34**. A release **21.0.35** para NeoForge 1.21.1 foi publicada em 16/09/2026.

### Deltas oficiais
- adiciona **opções de configuração que estavam faltando para desabilitar dois dos novos biomas**;
- corrige **geração de cave biomes**.

### Impacto para o pack
Os dois pontos afetam diretamente worldgen do End. Config de biome deve controlar apenas geração futura conforme a arquitetura do provider; não deve ser interpretada como remoção retroativa de chunks existentes. O fix de cave biomes precisa ser validado em chunks novos, inclusive depois de restart e em coexistência com os demais providers do End.

### Gate de promoção 21.0.34 → 21.0.35
- [ ] Dedicated server boot com BCLib/WorldWeaver/WunderLib físicos atuais.
- [ ] Mundo novo entra no End e gera biomas/estruturas sem registry/codec errors.
- [ ] Cave biomes aparecem em geração nova sem ausência sistemática ou placement incorreto.
- [ ] As duas novas opções de config existem, persistem e realmente desabilitam os biomas correspondentes em chunks novos.
- [ ] Chunks antigos continuam carregando quando as opções são alteradas.
- [ ] Fronteira old/new chunks não apresenta corrupção ou transições críticas.
- [ ] Flower Islets, Waterfall Ponds, Ruined End Bridges e lakes continuam gerando conforme esperado.
- [ ] YUNG's Better End Island e outros providers do End continuam coexistindo sem double placement evidente.
- [ ] Particle crash corrigido em 21.0.34 não regride.

Fonte upstream: CurseForge BetterEnd: New Dawn 21.0.35, file ID 8896285, NeoForge 1.21.1. Nenhum teste acima foi executado nesta atualização documental.

## 17. Upstream BetterEnd 21.0.36 — 10/10/2026

**Autoridade física:** `BetterEnd-21.0.34.jar` / runtime 21.0.34. A seção anterior já analisa **21.0.35**, publicada em 16/09, com adição de opções de configuração para desligar dois biomas novos e fix de cave-biome generation. A publicação posterior **BetterEnd-21.0.36** para **NeoForge Minecraft 1.21.1** foi confirmada no CurseForge em **07/10/2026**, sem release 1.21.1 adicional identificada depois dela.

**Limite de changelog:** não foi possível isolar com segurança um delta técnico específico da **21.0.36** a partir da ficha de arquivo consultada. **Não atribuir novos mobs, biomas, fixes ou alterações de worldgen a esta build sem a release note exata ou diff source correspondente.** Mudanças publicadas nas séries BetterEnd 1.21.11, 26.1/26.2/26.3 são independentes e não são evidência de comportamento em 1.21.1.

### Gates
Validação deve ser feita em cópia do save do End, com chunks antigos/novos: geração de cave biomes, toggles novos da 21.0.35, estruturas/biomes/pools, worldgen sampling, startup dedicado, registry/holder resolution, BCLib: New Dawn 21.0.26 + WorldWeaver 21.0.25 + WunderLib 21.0.10 e seus ranges efetivos, compatibilidade com YUNG's Better End Island, Dynamic Trees BetterEnd 2.2.0, shaders, resource reload e travessia de antigas fronteiras de chunks. Não afirmar compatibilidade de 21.0.36 com libs instaladas apenas pela mesma Minecraft version.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/betterend-neoforge/files/all ; https://github.com/Reijin2312/BetterEnd-New-Dawn .

**Estado:** latest upstream 21.0.36 confirmado; changelog granular não verificado; JAR físico 21.0.34 preservado.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** BetterEnd: New Dawn
- **Último candidato 1.21.1 / NeoForge localizado:** **21.0.38 (NeoForge 1.21.1, 19/10)**
- **Estado:** **CANDIDATO COORDENADO NEW DAWN**
- **Gate de promoção:** Atualizar com BCLib/WorldWeaver/WunderLib e stack BetterNether; backup, End já explorado, biome/structure registries, chunks novos e restart.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/betterend-new-dawn

## Auditoria cumulativa NeoForge 1.21.1 — 23/10/2026 (BetterEnd: New Dawn 21.0.34 → 21.0.38)

**Identidade:** CurseForge projeto 1422294, BetterEnd: New Dawn, continuação mantida por Raijin2312; não confundir com o projeto original BetterEnd nem com a versão Fabric, que usa outra sequência numérica. **Base física histórica do modpack:** BetterEnd-21.0.34.jar, runtime 21.0.34, posição #071. **Releases NeoForge 1.21.1 publicadas após essa base:** **21.0.35 (16/09) → 21.0.36 (07/10) → 21.0.37 (14/10) → 21.0.38 (19/10)**, todas Release para a combinação exata Minecraft 1.21.1 / NeoForge. A 21.0.38 é a mais nova dessa linha identificada em 23/10. Outras releases de Minecraft 1.21.11 ou 26.x não são substitutos.

### 21.0.35 — 16/09 — configuração de biomas e worldgen
Confirmado e já analisado na seção 16: foram acrescentadas **opções de configuração para desativar dois biomas novos** e corrigida a **geração de cave biomes**. Efeitos são relevantes em chunks ainda não gerados; não alegar alteração retroativa dos chunks explorados.

### 21.0.36 — 07/10 — conteúdo e correções publicados no changelog exato
**Correção da seção 17 anterior:** é incorreta a observação de que o delta da 21.0.36 não pôde ser isolado. A página do arquivo **BetterEnd-21.0.36.jar, CurseForge File ID 9089547**, explicita:
- **Datapacks/loot:** adição de loot tables vanilla editáveis para **todos os mobs BetterEnd**, inclusive mobs que só concediam experiência; suporte a mudanças por datapack e /reload. Manter equilíbrio de drops de End Slime, End Fish, Cubozoa e Silk Moth durante a migração.
- **Novas receitas:** quatro blocos de neve comuns passam a produzir um Dense Snow; um Dense Snow pode virar dezesseis snowballs. Construção de Snow Golem com Dense Snow.
- **Sustentabilidade:** Amaranita renovável a partir de um Amaranita Hymenophore, que pode ser transformado em quatro Small Amaranita Mushrooms.
- **Armadura/ferramentas:** correção da elegibilidade de Aeternium, Crystalite, Terminite e Thallasium para enchantments de armadura; hammers aceitam Efficiency, Fortune, Silk Touch, Unbreaking e Mending. Amaranita stems reconhecidos corretamente como mineráveis com machado.
- **Itens e blocos:** Umbrella Cluster Juice agora tem animação/comportamento de beber; suporte a compostagem para folhas, cogumelos, caps/fur, Jellyshroom e Needlegrass; Flammalix passa a usar textura própria de item; nome de End Stone Flower Pot corrigido; random ticking de Brimstone restaurado para crescimento de Sulphur Crystals; corrigida textura de End Crystals em pedestais.
- **Gameplay/estabilidade:** corrigida **duplicação de itens em Infusion Pedestals**, crash do End Fish ao morrer ou sofrer dano não causado por jogador, spawn de mobs vulneráveis ao congelamento sobre Ancient Emerald Ice; quebrar Respawn Obelisk remove o vínculo de respawn dos jogadores associados.
- **Animações/render:** evita duplicação ou posicionamento incorreto das asas Lytra com mods de animação como Fresh Animations; renderizador de elytra própria separada da camada vanilla para melhorar compatibilidade.
- **Localização:** tradução ucraniana uk_ua completa.

**Fonte exata:** https://www.curseforge.com/minecraft/mc-mods/betterend-neoforge/files/9089547 . Esses fatos foram declarados pelo autor para a build NeoForge 1.21.1, não inferidos de outra versão.

### 21.0.37 — 14/10 — backports e estabilidade do mundo
A identificação da release está no CurseForge. O **código-fonte oficial do mantenedor** traz o bump para **21.0.37** e commits dessa janela, entre eles **67841db62151957d5d611383ffc986f5dea956f0** (backport de fixes de estabilidade/gameplay, 13/10) e **b148dad11fef6bf14980f672da534e9d8ea3c905** (consolidação de backports e sincronização de portais, 14/10). O diff da janela registra:
- **Recipes/tags:** receitas adicionais de smelting para alimentos do End (incluindo cogumelos, berries e fish), tags comuns de barras e correntes de Terminite e Thallasium.
- **Estruturas e rituais:** tratamento mais seguro de centros nulos de Eternal Ritual, posicionamento de NBTPieces e mudanças na sincronização de portais; verificar recarregamento de chunks com estruturas e portais.
- **Worldgen e blocos:** salvaguardas em features grandes e limites de geração, estabilização de scatter e geysers, estado inicial de waterlogging de Hydrothermal Vent e correções para grow ticks; melhorias em paths que voltam a blocos de origem.
- **Diversos:** posição de som em Respawn Obelisk, guardas para índices inválidos de cor de End Portals e normalização de strings dependentes de locale.

**Limite da atribuição:** os commits citados são mudanças da janela source entre as linhas 21.0.36 e 21.0.37, e a 21.0.37 está oficialmente publicada; ainda **não foi feito bytecode diff ou reprodução do JAR 21.0.37**, portanto não há certificado de cada caminho do código dentro do binário distribuído.

**Fontes:** https://github.com/Reijin2312/BetterEnd-New-Dawn/commit/67841db62151957d5d611383ffc986f5dea956f0 ; https://github.com/Reijin2312/BetterEnd-New-Dawn/commit/b148dad11fef6bf14980f672da534e9d8ea3c905 .

### 21.0.38 — 19/10 — backports de features, estruturas e geração concorrente
O commit oficial **2cb33c0f97dcff12dfab92050e6efac6f083cbf4**, intitulado *Backport latest upstream fixes*, **altera gradle.properties de mod_version=21.0.37 para mod_version=21.0.38** e contém mudanças específicas:
- Reorganização de **surface rules de Flower Islets e Waterfall Ponds**: verificar colocação e estabilidade de superfície após geração.
- Registro de **Aurant Polypore e Purple Polypore** na tag vanilla de mushrooms, com possível efeito em compatibilidades baseadas em tags.
- Ordem estável dos 16 **DyeColor** vanilla em ColoredMaterial, reduzindo dependência da ordem de enumeração.
- **Clonagem de arrays compartilhados de direções antes de embaralhar** para várias árvores, bushes, geysers e Sulphuric Cave, evitando que um gerador altere o array compartilhado usado por outros; mudança material de segurança de geração potencialmente concorrente.
- Ajuste do **bounding box vertical de NBTPiece** para utilizar blockBox.maxY, mais alterações de estruturas/portais Eternal.
- Atualização e adição de traduções/locales. Não tratar essas traduções como evidência de novos biomas ou mobs.

**Fonte de código com pin de versão explícito:** https://github.com/Reijin2312/BetterEnd-New-Dawn/commit/2cb33c0f97dcff12dfab92050e6efac6f083cbf4 .

### Matriz de testes coordenados (não executada)
- [ ] Backup completo do mundo e de configs; copiar save do End com chunks já explorados para comparar geração antiga e nova após atualização.
- [ ] Confirmar ranges e versões coerentes de **BCLib, WorldWeaver, WunderLib, BetterNether, Lithostitched**, e dependências do stack New Dawn; uma versão nova do BetterEnd isolada não comprova ABI/registry matching.
- [ ] Em mundo novo e fronteiras old/new chunks, verificar Flower Islets, Waterfall Ponds, cave biomes, estruturas (incluindo portal Eternal), surface rules e crashes em worldgen concorrente.
- [ ] Conferir loot de todos os mobs por loot tables/datapacks e /reload, sem desbalancear quests/drops; reprodução do bug de duplicação de itens em Infusion Pedestals.
- [ ] Testar Dense Snow, Snow Golem, Amaranita renewability, recipes JEI, hammers enchantments (inclusive Resonance), Aeternium/Crystalite/Terminite/Thallasium armor e comportamento de Respawn Obelisk.
- [ ] Validar o End Fish ao sofrer dano ambiental, renderização Lytra com Epic Fight/Fresh Animations, compostagem, render de itens e reload de recursos.
- [ ] Testar survival/multiplayer, server restart, retornos por portais, entidades e estruturas em chunks carregados/descarregados, rastrear logs de registry e chunkgen.

**Conclusão estrita:** o **intervalo 21.0.35–21.0.38 foi percorrido versão por versão**, com **changelog direto das builds 21.0.35/21.0.36 e deltas por source commit identificados para 21.0.37/21.0.38**. Fica **pendente conciliar os commits da janela source com os artefatos exatos 21.0.37 e 21.0.38** (e as versões das bibliotecas New Dawn no modpack) para afirmar auditoria por binário totalmente certificada. **Não retirar o item 69 da fila sob o critério máximo "tudo detalhado" enquanto esse vínculo não for comprovado.** Nenhum JAR ou configuração foi alterado.

## Inventário cruzado do stack New Dawn — 24/10/2026

**Âmbito da comprovação:** a [página oficial de dependências do BetterEnd: New Dawn](https://www.curseforge.com/minecraft/mc-mods/betterend-neoforge/relations/dependencies) identifica explicitamente **três projetos obrigatórios**: **BCLib: New Dawn**, **WorldWeaver: New Dawn** e **WunderLib: New Dawn**, todos da continuação mantida por **Raijin2312**. Essa identidade é relevante: **BCLib**, **WorldWeaver** e **WunderLib** originais não são automaticamente equivalentes às distribuições **New Dawn**. Os números abaixo foram extraídos do **índice mestre físico histórico `modlist.md` (snapshot de 587 entradas)**, e não de uma nova leitura dos JARs no dispositivo:

| Entrada física histórica | Artefato top-level registrado | Versão do snapshot | Papel no upgrade |
|---|---|---|---|
| **#066 — BCLib: New Dawn** | `bclib-21.0.26.jar` | `21.0.26` | **Obrigatório** no projeto BetterEnd; verificar que a versão é compatível com a release `21.0.38`. |
| **#572 — WorldWeaver: New Dawn** | `worldweaver-21.0.25.jar` | `21.0.25` | **Obrigatório**; validar registries, biomas, worldgen, serialização e dependências transitivas. |
| **#573 — WunderLib: New Dawn** | `wunderlib-21.0.10.jar` | `21.0.10` | **Obrigatório**; validar API/UI de configuração e quaisquer ranges declarados em metadata. |
| **#074 — BetterNether: New Dawn** | `BetterNether-21.0.26.jar` | `21.0.26` | **Coexistente**, não listado como dependência direta obrigatória do BetterEnd; mesmo stack de worldgen, sujeito à integração. |
| **#375 — Lithostitched** | `lithostitched-1.8.0+beta6-neoforge-21.1.jar` | `1.8.0+beta6` | **Coexistente**, não listado como dependência obrigatória direta do BetterEnd na página consultada; requer smoke de estabilidade de worldgen. |

**Resultado objetivo:** os **três nomes de dependências obrigatórias do projeto** têm artefatos top-level documentados no snapshot do perfil. Isso resolve a pergunta de **presença histórica nominal**, mas **não** confirma que `21.0.26 / 21.0.25 / 21.0.10` satisfazem todas as versões mínimas/ranges e APIs exigidas pelo **JAR `BetterEnd-21.0.38.jar`**. Para certificar compatibilidade, obter metadata do JAR candidato e dos três New Dawn e comparar os requisitos de `META-INF/neoforge.mods.toml`/metadata correspondente; executar boot em servidor dedicado, geração de End novo e transição em cópia de mundo. A [listagem oficial da linha 1.21.1](https://www.curseforge.com/minecraft/mc-mods/betterend-neoforge/files/all) confirma o candidato **`21.0.38`, NeoForge, 19/10/2026**, sem demonstrar paridade de cada commit source ao binário distribuído.

**Decisão:** documentação de dependências **mais precisa**, mas comparações de metadata/JAR e QA dos conteúdos `21.0.37`/`21.0.38` seguem **pendentes**. A versão física **`BetterEnd-21.0.34.jar`** e o basename do dossier permanecem intactos. **Não retirar o item #69 da fila.**

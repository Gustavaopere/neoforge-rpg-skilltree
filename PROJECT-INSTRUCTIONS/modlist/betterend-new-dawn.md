# BetterEnd: New Dawn — 21.0.34

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

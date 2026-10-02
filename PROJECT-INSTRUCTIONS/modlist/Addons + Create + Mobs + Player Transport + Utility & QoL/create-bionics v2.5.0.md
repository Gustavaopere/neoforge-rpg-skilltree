# Create: Bionics — 2.5.0

> **Adição pós-snapshot físico — 02/10/2026.** O usuário confirmou que adicionou **Create: Bionics** ao perfil. O snapshot físico disponível `modlist(1).txt` é anterior e não contém esse JAR. O upstream atual e o source `main` convergem em **2.5.0**; a distribuição observada é `createbionics-2.5.0.jar`, mod id `createbionics`. Esta ficha não inventa SHA-1 nem ordem física antes de novo snapshot.

> **Status de certificação:** **SEM `✅-`**. É documentação nova pós-migração; não existe ficha canônica correspondente no Notion e a presença física detalhada ainda precisa ser re-fetched.

## Propriedades do catálogo

- **Mod:** Create: Bionics
- **Arquivo JAR alvo:** `createbionics-2.5.0.jar`
- **Versão alvo/runtime upstream:** `2.5.0`
- **Mod id:** `createbionics`
- **Minecraft / loader:** 1.21.1 / NeoForge
- **Ambiente:** client + server
- **Licença:** MIT
- **Categoria:** Addons; Create; Mobs; Player Transport; Utility & QoL
- **Estado no pack:** Adicionado pelo usuário em 02/10/2026; confirmação física detalhada pendente
- **Estado da pesquisa:** Verificado no Modrinth + source; artefato local pós-snapshot pendente
- **Decisão:** Manter/adicionar
- **Função:** cinco animais robóticos craftáveis para exploração e coleta de recursos: Anole, Matchbox, Seeker, Oxhauler e Replete.
- **Dependência principal:** Create. O source 2.5.0 compila contra Create 6.0.9; o pack documenta Create 6.0.10, portanto o alvo está na mesma linha 6.0.x, mas o runtime real deve ser testado.
- **Fonte principal:** https://modrinth.com/mod/create-bionics
- **Versões:** https://modrinth.com/mod/create-bionics/versions
- **Código-fonte:** https://github.com/Dshbwlto/Create-Bionics

# Dossiê operacional — padrão Alex's Mobs

## 1. Escopo e authority

Create: Bionics não é apenas um pack cosmético de mobs. Os cinco robôs publicados são **companions utilitários**, com sistemas de combustível, comando, montagem/customização e funções distintas de exploração, iluminação, prospecção, storage/farming e fluid logistics.

Create continua authority de:
- materiais/itens Create;
- wrench;
- mechanical plough/harvester;
- fluid capability/ecossistema de fluidos;
- integração visual/tooltip Create.

Create: Bionics é authority das entidades robóticas e de suas regras próprias.

## 2. Roster público — cinco robôs

O texto oficial público enumera **cinco** robôs jogáveis:
1. **Anole**
2. **Matchbox**
3. **Seeker**
4. **Oxhauler**
5. **Replete**

Esse é o roster operacional desta ficha.

O source atual registra EntityTypes adicionais como Organ, Shark, Stalker, Stalker Captain e Golem, mas o próprio registro comenta que parte desse conteúdo é para **future updates**. Portanto esses EntityTypes **não são promovidos a conteúdo de gameplay publicado** sem release/documentação correspondente.

## 3. Sistema compartilhado de robôs

A base `AbstractRobot` mantém dados sincronizados/persistentes para:
- variant;
- fuel time;
- assembly;
- command;
- estado/tempo de pose.

O código base é um `TamableAnimal` e implementa informações de goggles/hover do ecossistema Create.

O command state percorre três estados lógicos; entidades concretas usam isso para comportamento de follow/wander/sit conforme seus próprios goals.

Não há breeding: a base retorna sem offspring e não trata os robôs como fauna reprodutiva.

## 4. Combustível

O projeto público informa que os robôs exigem reabastecimento ocasional com:
- coal;
- charcoal;
- blaze cakes.

O source confirma lógica de fuel state e uso de itens Create em robôs concretos. Creative Blaze Cake pode ser tratado de modo especial por implementações internas.

**Boundary:** combustível de Bionics é recurso operacional do robô, não stress unit nem energia elétrica de Create.

## 5. Customização visual

A descrição pública declara skins:
- andesite;
- brass;
- copper.

Elas são aplicadas por interação com o ingot correspondente e removidas com wrench.

O Anole possui ainda:
- skin de netherite;
- variantes oxidized;
- aplicação/remoção específica, incluindo wet sponge em variantes.

Customização visual não altera ownership de função do robô salvo onde source/release documentar explicitamente.

## 6. Anole

O Anole é um pequeno robô-lagarto:
- craftável cedo;
- pode acompanhar o jogador como entidade;
- possui forma de item;
- quando segurado pode ficar no ombro;
- sua presença assusta spiders/insects segundo a descrição pública.

Além de andesite/brass/copper, o Anole possui netherite e estados oxidized.

### Riscos/integrações
- comportamento de fear pode conflitar com mods que alterem AI de spiders/insetos;
- conversão entidade↔item precisa preservar variant/customization;
- shoulder/item rendering deve ser testado com first-person/player-model mods.

## 7. Matchbox

O Matchbox:
- armazena até **4 stacks de torches**;
- coloca tochas automaticamente em áreas escuras;
- pode operar acompanhando o jogador ou vagando;
- possui modo de colocação para sunlight/cave;
- pode ter a colocação desativada por shift-click.

### Boundary
Ele automatiza iluminação, não worldgen e não mob-spawn rule. Outros mods ainda controlam light engine/spawn rules.

## 8. Seeker

O Seeker funciona como prospector:
- recebe ore/metal/mineral;
- procura bloco de minério correspondente na área;
- quando encontra, cava por alguns segundos e retorna com o ore block;
- se falhar, o item fornecido pode ser consumido;
- diamond/netherite pickaxe acelera a escavação;
- algumas interações de skin exigem shift para não disputar com a mecânica de busca.

### Boundary
O Seeker é uma mecânica própria de descoberta/coleta. Não presumir compatibilidade universal com todo ore tag/modded ore sem teste do mapping real.

## 9. Oxhauler

O Oxhauler é um robô grande, bovino, voltado a logística/farming:
- capacidade publicada equivalente a **6 chests** de armazenamento;
- inclui crafting grid completo **3×3**;
- aceita Mechanical Plough;
- aceita Mechanical Harvester;
- pode arar terreno;
- pode colher crops.

O source atual implementa entidade multipart e peças próprias para plough/harvester.

### Operação
Ao testar:
- inventário não pode duplicar/perder itens ao desmontar;
- plough/harvester deve respeitar protection/claims;
- colher deve interoperar com crops modded somente quando a lógica/tag suportar.

## 10. Replete

O Replete é o transportador de fluidos:
- tank publicado de **160 buckets**;
- aceita fluids de outros mods via sistema compatível;
- pode transportar longas distâncias sem train/pipe;
- pode coletar fluidos do ambiente quando correspondem ao conteúdo do tank.

O source 2.5.0 usa NeoForge `Capabilities.FluidHandler` e `FluidTank`, além de entidade multipart.

O código limita coleta ambiental ao mesmo tipo de fluido já presente e trabalha em unidades de 1000 mB para fontes compatíveis.

### Boundary
- não assumir suporte a fluid sem capability/registry adequado;
- não tratar capacidade de 160 buckets como storage genérico de itens;
- remoção de fluid source do mundo precisa ser QA-tested contra fluid mods.

## 11. Montagem, desmontagem e salvage

O source possui estado de `assembly` e itens/partes de construção. Robôs multipartes podem ser montados por etapas.

Ao morrer/desmontar, implementações podem devolver partes ou salvage; o comportamento depende do assembly e de cada entidade.

Isso torna relevante testar:
- construção parcial;
- morte parcial;
- wrench dismantle;
- inventário/tank não vazio;
- persistência após save/reload.

## 12. Persistência e multiplayer

A base salva em NBT:
- command;
- assembly;
- variant;
- fuel;
- pose state.

Como `TamableAnimal`, ownership também faz parte do state vanilla/NeoForge da entidade.

Em multiplayer:
- interações e inventários/tanks devem ser server-authoritative;
- cliente não deve inventar variant/fuel;
- teleport/follow deve preservar ownership;
- multipart hitboxes devem sincronizar corretamente.

## 13. Conteúdo source não publicado

O source atual contém classes/registries para:
- Organ;
- Shark;
- Stalker;
- Stalker Captain;
- Golem.

O registro marca explicitamente parte deles como conteúdo de futuras updates. Há também assets e itens internos ainda não equivalentes ao roster público.

Regra desta ficha:
**registrado no source não é automaticamente conteúdo publicado/obtível em survival**.

Outro chat não deve criar quests/perks para esses nomes sem confirmação por release/runtime.

## 14. Compatibilidade Create

O source `gradle.properties` de 2.5.0 declara:
- Minecraft 1.21.1;
- NeoForge;
- `mod_id=createbionics`;
- `mod_version=2.5.0`;
- Create `6.0.9-215` como baseline de desenvolvimento;
- Flywheel/Ponder/Registrate como dependências de desenvolvimento do ecossistema Create.

O pack usa Create 6.0.10 na catalogação atual. Isso é próximo, mas **não substitui teste real**.

## 15. Create Aeronautics

A galeria oficial do projeto possui material explicitamente rotulado **Create: Aeronautics compatibility**.

A página textual principal não define um contrato técnico completo dessa integração. Portanto:
- registrar que a compatibilidade é anunciada;
- não inferir que todos os cinco robôs funcionam em qualquer sublevel/ship sem teste;
- testar pathfinding, collision, teleport, storage/fluid e persistência dentro de contraptions.

## 16. Histórico de releases conhecido

A distribuição pública possui uma linha 2.x extensa. Changelogs anteriores documentam, entre outros:
- melhorias de Anole em seat/minecart e fear de spiders/insects;
- expansão de farming/storage do Oxhauler;
- fluid capabilities/coleta do Replete;
- recipes survival e modelos 3D;
- correções de dedicated-server soundscape;
- preservação de customization do Anole ao desmontar;
- melhoria de pathfinding do Replete;
- multipart collision;
- melhorias de plough/harvester;
- correções de animação/model do Replete;
- fix de dupe com wet sponge/customized Anole;
- rebalance de recipes.

A lista pública atual mostra **2.5.0** como versão mais recente. O changelog textual específico de 2.5.0 não ficou acessível pelo endpoint de versões consultado; por isso esta ficha descreve o baseline atual por página oficial + source 2.5.0, sem inventar um “What's changed” da release.

## 17. Overlaps no pack

Possíveis superfícies:
1. **storage mobs/companions** — outros pets/mounts com inventário;
2. **farming Create** — deployers, harvesters, contraptions e outros addons;
3. **ore finding** — sistemas de prospecção/ore scanners;
4. **fluid transport** — pipes, tanks, trains, mobile fluid systems;
5. **lighting automation** — mods que colocam luz automaticamente;
6. **AI mods** — goals/pathfinding de TamableAnimal;
7. **Aeronautics/Sable** — entidades dentro de ships/sublevels.

Não classificar como redundância simples: Bionics concentra essas funções em companions móveis.

## 18. Riscos operacionais

1. Entity↔item conversion perder NBT/customization.
2. Oxhauler duplicar/perder conteúdo em dismantle/death.
3. Replete remover source block incorretamente em fluid modded.
4. Seeker consumir material sem achar ore por tag/mapping inesperado.
5. Automatic torch placement ignorar claims/protection.
6. Multipart hitboxes divergirem em combat/teleport.
7. AI/pathfinding modded quebrar follow/sit/wander.
8. Robots em Aeronautics/Sable perderem world/sublevel reference.
9. Fuel state divergir client/server.
10. Conteúdo futuro registrado internamente ser confundido com conteúdo survival atual.

## 19. Matriz de validação para esta instância

- [ ] Confirmar JAR físico `createbionics-2.5.0.jar`, runtime, mod id e SHA-1.
- [ ] Startup NeoForge 1.21.1 + Create 6.0.10.
- [ ] Craft/assemble os cinco robôs publicados.
- [ ] Refuel com coal, charcoal e Blaze Cake.
- [ ] Salvar/reabrir mundo preservando fuel/command/variant/assembly.
- [ ] Testar follow/wander/sit e ownership multiplayer.
- [ ] Anole: entity/item/shoulder e spider/insect fear.
- [ ] Anole: skins e desmontagem preservando customization.
- [ ] Matchbox: 4 stacks, cave/sunlight/off e proteção de área.
- [ ] Seeker: vanilla ore + modded ore + falha de busca + pickaxe speed.
- [ ] Oxhauler: inventário cheio, crafting 3×3, plough e harvester.
- [ ] Oxhauler: dismantle/death com inventário para detectar loss/dupe.
- [ ] Replete: 160 buckets, fill/drain por fluid capability.
- [ ] Replete: coleta de source matching e fluid modded.
- [ ] Resource/save reload com multipart entities.
- [ ] Dedicated-server smoke.
- [ ] Aeronautics/Sable: embarque, movimento, unload/reload e retorno ao world.
- [ ] Confirmar que Organ/Shark/Stalker/Golem não viraram conteúdo survival inesperado.

Nenhum teste foi marcado como executado nesta auditoria documental.

## 20. Regras para outros chats

- O roster publicado atual é **cinco** robôs: Anole, Matchbox, Seeker, Oxhauler, Replete.
- Não contar EntityTypes futuros do source como conteúdo survival.
- Não tratar fuel como Create stress/FE.
- Não assumir ore compatibility universal para Seeker.
- Não assumir fluid compatibility universal para Replete sem capability.
- Não criar perks baseadas em simples presença de entidade; exigir action/outcome real.
- Para quests, usar tarefas concretas: assemble, fuel, carry, light, prospect, farm, transport fluid.
- Para updates, JAR físico continua authority da versão instalada.

## 21. Evidências e grau de confiança

**Alta confiança — versão/source:** o source atual declara `mod_id=createbionics` e `mod_version=2.5.0`; a listagem pública atual também coloca 2.5.0 no topo.

**Alta confiança — roster e funções:** descrição oficial Modrinth documenta cinco robôs e suas funções centrais.

**Alta confiança — detalhes técnicos:** source atual confirma registry, TamableAnimal, persistência de fuel/command/assembly/variant, multipartes e fluid capability.

**Média/pendente — instalação local:** o usuário confirmou a adição, mas o snapshot físico do projeto antecede a instalação. Hash, ordinal e metadata carregada ainda precisam de re-fetch.

> **Boundary canônico:** Create: Bionics fornece companions robóticos utilitários; Create fornece o ecossistema mecânico. Conteúdo futuro presente no source não deve ser confundido com conteúdo publicado.

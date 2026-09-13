# Mobstein : Revive animals and necromancy!

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3cc69db9f0db819fb8cae2a8f949c352
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — Mobstein `5.4.4`, GeckoLib `4.9.2`, Sable `2.0.5` e Sable mob ragdoll corpses `1.1.5` confirmados fisicamente; JEAsync não aparece top-level
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Mobstein : Revive animals and necromancy!
- **Arquivo JAR:** `mobstein-5.4.4-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 5.4.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Mobs, Magia, RPG, Exploração
- **Função:** Necromancia/ressurreição com Clinical Stretch e seringas; corpos, cabeças e órgãos; Organ Extractor; Surgery Stretch e Subject Assembly; mobs ressuscitados domesticáveis/bodyguards; Igor e failed experiments; estruturas próprias e boss Witherstein.
- **Dependências:** GeckoLib — Required Dependency na release 5.4.4. JEAsync — Optional Dependency. JEI é recomendado pela página do autor para recipes/guia, mas não é hard dependency comprovada.
- **Sobreposição:** Sobreposição temática/técnica com sistemas de necromancia, pets e corpos. Interseção mais concreta neste lote: #408 Sable mob ragdoll corpses e o stack Sable; 5.4.4 declara compatibilidade com Sable, mas o mecanismo interno não foi presumido sem source.
- **Compatibilidade/Riscos:** 5.4.4 declara compatibilidade com Sable Mod, relevante ao stack físico Sable/#408. GeckoLib é required; JEAsync é optional. Riscos principais: death/resurrection overlap, loot/body parts, pets/bodyguards, worldgen/Witherstein e ausência de source exato para auditar internals/persistência/networking.
- **Observações:** JAR físico `mobstein-5.4.4-neoforge-1.21.1.jar`, mod id `mobstein`, runtime 5.4.4. CurseForge file ID 8040734, Release de 04/05/2026. O JAR possui metadata de projeto MCreator, sem que isso seja usado para inferir implementação. GeckoLib required; JEAsync optional; página recomenda JEI. 5.4.4: 'Mobstein now is compatible with Sable Mod'.
- **Procedência:** modlist.txt física atual + CurseForge oficial do projeto e file ID 8040734. Nenhum source público correspondente ao binário 5.4.4 foi estabelecido; dossiê limitado deliberadamente às evidências oficiais/publicadas e metadata física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mobstein-upgrade-useless-animals | https://www.curseforge.com/minecraft/mc-mods/mobstein-upgrade-useless-animals/files/8040734
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — dossiê reconstruído para a release exata 5.4.4 (CurseForge file ID 8040734). Changelog exato confirma compatibilidade com Sable Mod. Source correspondente ao binário 5.4.4 não foi estabelecido; internals não foram inventados.
- **Histórico da decisão:** 2026-09-10 — ficha reconstruída no padrão Alex's Mobs para 5.4.4. Release, dependências e catálogo oficial de gameplay auditados. Ausência de source exato foi registrada como limite documental. Mantido; compatibilidade Sable é oficial na própria release.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO DESTA FICHA.** A autoridade de presença e versão é a modlist física: `mobstein-5.4.4-neoforge-1.21.1.jar`, mod id `mobstein`, runtime `5.4.4`. A release oficial exata é o CurseForge file ID `8040734`, publicada em 04/05/2026 para NeoForge 1.21.1. O changelog dessa release registra explicitamente compatibilidade com **Sable Mod**. Não foi estabelecido um source público correspondente ao binário 5.4.4; portanto, esta ficha documenta gameplay, dependências, world content e contratos publicados pelo autor, mas **não inventa classes, eventos, registries, networking ou formato de persistência internos**.

## 1. Identidade, versão e authority
- **Mod:** Mobstein : Revive animals and necromancy!
- **JAR físico:** `mobstein-5.4.4-neoforge-1.21.1.jar`.
- **Mod id:** `mobstein`.
- **Runtime:** `5.4.4`.
- **Loader/jogo:** NeoForge 1.21.1.
- **CurseForge:** project ID `1193873`, file ID `8040734`, Release, 04/05/2026.
- **Autor:** Pecsitonic.
- **Ambiente oficial:** Client & Server.
- **Licença:** All Rights Reserved.
- **Metadata física:** o JAR é marcado como projeto gerado com MCreator; isso é informação de procedência/empacotamento e não é usado para inferir implementação interna.
- **Papel no pack:** necromancia e ressurreição de criaturas, criação de corpos/experimentos, pets e guarda-costas, progressão por estruturas, máquinas temáticas e boss próprio.
- **Decisão:** manter.

## 2. Dependências e componentes auxiliares
A relação oficial da release confirma:
- **GeckoLib — Required Dependency.**
- **JEAsync — Optional Dependency.**

A página do projeto também recomenda **JEI** para consultar recipes/guia, mas recomendação de uso não equivale a hard dependency. Assim:
- GeckoLib deve ser tratado como requisito de carregamento.
- JEAsync é opcional segundo a relação oficial.
- JEI é ferramenta recomendada pelo autor, não dependency obrigatória comprovada desta release.

## 3. Changelog exato da release 5.4.4
O changelog do file ID `8040734` declara:
- **“Mobstein now is compatible with Sable Mod”.**

Isso é particularmente relevante neste pack porque o stack Sable está fisicamente presente, inclusive `Sable`, `Sable Ragdolls`, `Ragdoll Reactions`, `Sable Ragdolls Patch` e #408 `Sable mob ragdoll corpses`.

A release não publica nesse changelog uma lista de alterações internas da integração. Portanto, a existência da compatibilidade é confirmada; seu mecanismo interno não é inferido sem source exato.

## 4. Conceito central: ressurreição de criaturas
Mobstein transforma a fantasia de necromancia em sistemas jogáveis de recuperação/criação de mobs. O conteúdo oficial descreve:
- ressurreição de criaturas vanilla em variantes ressuscitadas;
- uso de corpos, cabeças, órgãos e seringas;
- máquinas/estações para extração e montagem;
- pets e bodyguards com habilidades próprias;
- experimentos e failed experiments;
- estruturas temáticas e progressão até Witherstein.

O mod não deve ser reduzido a “novos mobs”: ele conecta coleta de corpos, crafting/estações, domesticação, criação de sujeitos e combate/progressão.

## 5. Clinical Stretch — ressurreição básica
A documentação oficial apresenta a **Clinical Stretch** como estação central de ressurreição:
- posiciona-se um **full body** na maca;
- o procedimento comum é feito à noite;
- após aproximadamente **15 segundos**, um raio conclui a ressurreição;
- existe uma **Lightningbolt Syringe** para realizar a ressurreição em condições alternativas, incluindo durante o dia.

A ficha usa ~15 s porque esse é o comportamento publicado pelo autor. Timing interno em ticks, condições exatas de céu/bioma e implementação de lightning não são inventados sem source/runtime.

## 6. Ressuscitados documentados
### 6.1 Axolotl Resucited
- pode sentar;
- pode ser transportado em bucket;
- quando domesticado, fornece efeitos benéficos publicados como regeneração/night vision ao jogador em seu contexto de uso;
- **Tropical Fish** é o item de tame documentado.

### 6.2 Dolphin Resucited
- montável;
- concede/associa utilidade de respiração aquática e deslocamento rápido na água;
- **uncooked fish** é usado para tame segundo a documentação.

### 6.3 Bat Resucited
- possui ataque aéreo;
- pode sentar;
- tame com **Pumpkin Pie**.

### 6.4 Putrid Horse
- cavalo ressuscitado com múltiplos estágios visuais de decomposição; a documentação descreve cerca de cinco aparências/estágios de rot;
- possui partículas e interface própria;
- foco em mobilidade: alto salto e velocidade elevada;
- não usa horse armor conforme a descrição publicada;
- tame com **Rotten Flesh**.

### 6.5 Ocelot Resucited
- collar tingível;
- pode sentar;
- velocidade elevada;
- tame com **Raw Salmon**.

### 6.6 Villager Resucited
- funciona como parte da cadeia de órgãos;
- a documentação registra chance de obter um de três órgãos em kills realizadas por ele/no sistema associado;
- tame com **Rotten Flesh**.

### 6.7 Silver Fish Resucited
- fornece efeito de mineração rápida nas proximidades em cavernas;
- pode sentar;
- tame com **Stone**.

### 6.8 Warden Resucited
- bodyguard extremamente forte;
- não possui o sonic boom do Warden vanilla ressuscitado, segundo o guia;
- não segue o dono da mesma maneira que pets comuns;
- não é ressuscitável via Reviver Syringe conforme a documentação;
- tame com **Echo Shard**.

### 6.9 Frankenstein Resucited
- golem/bodyguard central do tema do mod;
- aceita cosmetic suit;
- possui easter egg com pumpkin;
- drops publicados incluem **Screws** e **Infested Iron Ingots**;
- nas proximidades concede o efeito **Blacksmithstrength**, que o texto do projeto descreve em termos de ganho de velocidade apesar da iconografia/nome;
- não é ressuscitável via Reviver Syringe;
- tame com **Iron Ingot**.

### 6.10 Rabbit Resucited
- aparência esquelética;
- fornece Jump Boost nas proximidades;
- pode sentar;
- tame com **Carrot**.

## 7. Partes de mobs e full bodies
A documentação oficial descreve uma cadeia de coleta de partes corporais usada para necromancia/experimentos. Entre os exemplos publicados:
- Piglin chop / cooked variant;
- Polar Bear Leather;
- Panda Leather;
- Villager Nose obtido com shears;
- Spider/Cave Spider Legs obtidas com shears;
- Dolphin Tail;
- Horse Leather;
- Axolotl Gill;
- Bat Wings e Fangs;
- cooked leather usado na produção/composição de bodies.

Esses itens participam do domínio de loot/interação com mobs. Qualquer datapack/mod que altere drops, shearing ou corpos das mesmas entidades pode compartilhar superfície funcional; isso é risco de composição, não conflito confirmado.

## 8. Organ Extractor e órgãos
O **Organ Extractor** é uma estação documentada para trabalhar com full bodies:
- usa **Tweezers** no processo;
- extrai aleatoriamente um entre **três órgãos** do full body segundo o guia;
- o full body possui suporte/placement próprio no fluxo;
- shears são usadas para remover o corpo do suporte em parte desse processo.

A ficha não inventa chances numéricas, recipes ou storage schema sem fonte específica. Para balanceamento/automação, usar recipe/runtime real de 5.4.4.

## 9. Surgery Stretch — criação de experimentos
A **Surgery Stretch** é uma estação separada da ressurreição simples. A documentação apresenta quatro perks/parâmetros de criação:
- **Attack**.
- **Health**.
- **Speed**.
- **Template**.

Ranges publicados pelo autor:
- Health: **20–40**.
- Attack: **0.8–2**.
- Speed: **0.3–0.5**.

`Template` participa da capacidade de gerar outras combinações/sujeitos. Esses valores são tratados como gameplay publicado, não como nomes de atributos/API integrável.

### Tipos de experimento publicados
O guia lista famílias como:
- Turtle.
- Panda.
- Polar Bear.
- Spider.
- Enderman.
- Snow Golem.

Cada família possui tame/efeitos/habilidades próprios na documentação. A Surgery Stretch não aceita player heads e trabalha com partes corporais dentro de seu próprio modelo de montagem; não confundir com Subject Assembly Machine.

## 10. Subject Assembly Machine e mannequins
A **Subject Assembly Machine** cobre um caso diferente:
- aceita **qualquer head** no fluxo documentado;
- o **Head Creator** pode gerar player heads;
- cabeça + torso + pants são combinados para criar um mannequin/test subject visual;
- esses subjects/mannequins não recebem as habilidades dos experimentos da Surgery Stretch.

Isso permite reproduzir aparência/identidade visual sem transformar automaticamente o resultado em combat pet.

## 11. Dr Mobstenio
O mod possui o NPC/criatura especial **Dr Mobstenio**:
- associado ao **Frankenstein Castle**;
- pode ser revivido usando **Mobstenio Blood Syringe**;
- tame usa **Wither spores** obtidos na progressão de Witherstein;
- após domesticado, a documentação descreve comportamento de wandering em vez de follow convencional.

O guia também associa a ele recompensa/disc de música em conteúdo específico.

## 12. Igor e failed experiments
**Igor** aparece por spawner/estrutura e participa de uma mecânica de experimentos falhos:
- não é domesticável segundo a documentação;
- possui **Igor table**;
- **Suspicious Syringes** são usadas para gerar resultados aleatórios;
- o primeiro attempt é descrito como falho e a documentação estabelece limite de arremessos/tentativas nessa sequência;
- existem exceções/regras especiais envolvendo experimentos 062 e 097.

### Failed experiments documentados
O catálogo oficial inclui combinações numeradas, entre elas:
- **097** — Endermite + Frog.
- **062** — Cod + Cacao.
- **067** — Chicken + Enderman.
- **077** — Allay + Parrot.
- **091** — Fox + Dragon.
- **012** — Llama + Mooshroom.
- **007** — Villager + Bee.

Essas entidades possuem combinações próprias de tame/habilidades descritas no guia. Para uma integração que dependa de stats/AI exatos, validar a entidade runtime em vez de derivar comportamento pelo nome da combinação.

## 13. Seringas
A documentação enumera várias seringas com papéis distintos:
- **Empty Syringe** — base da cadeia.
- **Reviver Syringe** — ressurreição direta de vários mobs; há exceções publicadas como Warden Resucited e Frankenstein Resucited. Também participa da invocação de Witherstein via skeleton apropriado na estrutura.
- **Mobstenio Blood Syringe** — usada no fluxo de Dr Mobstenio.
- **Lightningbolt Syringe** — alternativa para acionar lightning/resurrection na Clinical Stretch, inclusive durante o dia.
- **Suspicious Syringe** — vinculada a Igor/failed experiments.

Não presumir que todas as seringas usam o mesmo event/hook ou aceitam toda entidade; seguir os casos publicados e testar os limites em runtime.

## 14. Estruturas e exploração
O projeto publica pelo menos três estruturas temáticas relevantes:
- **Frankenstein Castle** — associado a Dr Mobstenio e à fantasia central do mod.
- **Witherstein Ruins** — em Jungle, ligadas à invocação/progressão de Witherstein.
- **Old Ruins** — também documentadas em Jungle.

Como essas estruturas fazem parte de worldgen/exploração, a existência no catálogo não garante frequência, distância ou biome placement em um modpack pesado com vários worldgen providers. Esses parâmetros devem ser verificados na geração real da seed utilizada.

## 15. Witherstein — boss
**Witherstein** é o boss próprio do mod:
- sua progressão passa por um skeleton/altar nas ruínas e uso da **Reviver Syringe** conforme o guia;
- possui **três estágios** documentados;
- nos dois estágios finais, invoca/spawna **Wither Skeletons**.

O boss participa da progressão que fornece materiais como Wither spores usados em outro conteúdo do mod. Vida, damage, cooldowns e AI interna não são transcritos sem source/runtime confirmado.

## 16. Cosméticos, equipamentos e itens auxiliares
O projeto inclui conteúdo complementar além de necromancia:
- **Frankenweenie** como easter egg de wolf por rename.
- organ jars.
- music discs; o guia associa **Spooky Scary Skeletons** a Dr Mobstenio ressuscitado e **Luigi's Mansion** a Witherstein.
- Witch Hat.
- Villager Nose.
- goggles/scientist coat.
- paintings.
- cosmetics ligados a Frankenstein/experimentos.

Esses itens ajudam a fechar a identidade visual do mod, mas não são a autoridade para stats ou integrações externas.

## 17. Relação com Sable e #408
A release **5.4.4** foi publicada especificamente com a nota de que Mobstein agora é compatível com **Sable Mod**. No mesmo pack está #408 `Sable mob ragdoll corpses` 1.1.5, que intercepta fatal damage de mobs e mantém corpses físicos.

Pontos que devem ser validados conjuntamente:
- morte de mobs Mobstein gera ou não corpse de forma esperada;
- morte de mobs ressuscitados/pets/bodyguards não bloqueia seu próprio ciclo de drops/progressão;
- Reviver Syringe não tenta atuar sobre uma entidade que ainda esteja em estado de corpse Sable de forma inválida;
- Warden/Frankenstein exceptions continuam respeitadas;
- bosses/Igor/Dr Mobstenio não ficam presos em death state;
- bodies usados como itens/props do Mobstein não devem ser confundidos automaticamente com a entidade corpse do addon Sable.

A compatibilidade oficial reduz o risco, mas não substitui teste do stack exato instalado.

## 18. Ownership com outros sistemas de magia/necromancia
Mobstein é authority apenas para seu próprio fluxo de corpos, seringas, ressurreições, experimentos, estruturas e mobs. Outros providers de magia/necromancia no pack continuam donos de suas próprias regras.

Não criar automaticamente bridges de mana, corrupção, perks ou spell schools só porque o tema é necromancia. Qualquer integração com sistemas próprios do pack deve usar hook/ID real e preservar ownership dos providers.

## 19. Client/server, dados e persistência — limite documental
A página oficial classifica o mod como **Client & Server**, e GeckoLib é required. O conteúdo inclui entidades, worldgen, bosses, tame, máquinas e objetos colocáveis, portanto seu funcionamento normal é de mod presente nos dois lados.

Porém, sem source 5.4.4 correspondente:
- não foram estabelecidos packet channels;
- não foram estabelecidos event hooks;
- não foram estabelecidos registry class names;
- não foi estabelecido esquema de config.

Essas lacunas são mantidas deliberadamente. Para automação ou desenvolvimento de bridge, o próximo authority deve ser inspeção do JAR/runtime correspondente, não generalização a partir de MCreator ou de outra versão.

## 20. Compatibilidade e sobreposição
### GeckoLib
Hard dependency e provável suporte de animação segundo o ecossistema do projeto. A página não transforma GeckoLib em provider de gameplay; apenas requisito do mod.

### JEAsync / JEI
JEAsync é opcional na relação da release. JEI é recomendado pelo autor para receitas/guia. Ausência deles não deve ser descrita como quebra do core sem evidência adicional.

### Sistemas de corpos
Mobstein usa conceitos de body/full body/head/organ como itens/objetos de gameplay; Sable Corpse usa a própria entidade morta como ragdoll. Os domínios são semanticamente próximos, mas não equivalentes. A release 5.4.4 afirma compatibilidade com Sable.

### Worldgen
Frankenstein Castle, Witherstein Ruins e Old Ruins adicionam conteúdo ao mundo. Em pack com múltiplos geradores/estruturas, verificar localização/raridade e eventuais collisions de placement.

### Pets/bodyguards
Ressuscitados domesticáveis podem coexistir com outros sistemas de pets/summons. Ownership, follow/sit, target selection e friendly fire devem ser testados, não inferidos.

## 21. Riscos técnicos
- **Source exato ausente:** não há base para inventar API interna, events ou persistence hooks de 5.4.4.
- **Death/resurrection overlap:** #408 Sable Corpse e outros death handlers podem compartilhar a mesma fase de morte de entidades.
- **Worldgen:** três famílias de estrutura aumentam a superfície de geração/descoberta.
- **Boss/progression:** Witherstein possui múltiplos stages e summons; mods de scaling/AI/damage podem alterar dificuldade.
- **Tame/bodyguards:** follow/target/owner logic pode interagir com outros mods de combat AI.
- **Loot/body parts:** mudanças em loot/shearing de vanilla mobs podem afetar a cadeia de partes corporais.
- **Machines/recipes:** automação e recipe rewrites podem alterar Organ Extractor, Stretchs, syringes e assembly.
- **Sable compatibility:** existe declaração oficial em 5.4.4, mas o mecanismo não está source-auditável nesta versão.
- **MCreator metadata:** a origem não é, por si, defeito; o risco documental é apenas não poder inferir internals sem source exato.

## 22. Matriz de validação obrigatória
> Estes são testes requeridos, não resultados já aprovados.

### Boot/dependências
- Boot de servidor dedicado com GeckoLib presente.
- Cliente entrar com o mesmo modset.
- Testar presença/ausência de JEAsync/JEI sem confundir optional/recommended com hard dependency.

### Clinical Stretch
- montar fluxo de full body à noite;
- medir o ciclo publicado de ~15 s;
- confirmar lightning e mob resultante;
- repetir durante o dia usando Lightningbolt Syringe;
- save/restart com procedimento preparado antes de concluir.

### Ressuscitados
Para Axolotl, Dolphin, Bat, Putrid Horse, Ocelot, Villager, Silver Fish, Warden, Frankenstein e Rabbit:
- tame pelo item documentado;
- sit/follow/ride quando aplicável;
- habilidade/efeito proximal documentado;
- death + loot;
- confirmar exceções de Reviver Syringe quando publicadas.

### Sable / death lifecycle
- morte de Mobstein mob com Sable/Ragdoll stack ativo;
- corpse não bloqueia drop/progressão;
- Reviver Syringe não atua indevidamente sobre corpse transitório;
- Warden/Frankenstein/Igor/Dr Mobstenio preservam exceções e death lifecycle esperado.

### Máquinas/experimentos
- Organ Extractor com full body + Tweezers;
- Surgery Stretch com ranges publicados de Health/Attack/Speed;
- Subject Assembly Machine com head/torso/pants;
- Igor table + Suspicious Syringes e failed experiments documentados.

### Estruturas/boss
- localizar Frankenstein Castle, Witherstein Ruins e Old Ruins em geração real;
- invocar Witherstein pelo fluxo documentado;
- validar os três estágios e summons sem duplicação de boss/reward.

### Multiplayer/persistência/performance
- dois clientes observando/tamando/operando máquinas.
- restart com pets ressuscitados existentes.
- chunk unload/reload de estruturas, machines e mobs.
- combate com múltiplos bodyguards + corpses Sable.

## 23. Evidências e limites
- **Authority física:** `mobstein-5.4.4-neoforge-1.21.1.jar`, mod id `mobstein`, runtime 5.4.4.
- **CurseForge oficial:** project ID 1193873, file ID 8040734, NeoForge 1.21.1 Release, 04/05/2026.
- **Changelog exato:** compatibilidade com Sable Mod.
- **Relations oficiais:** GeckoLib required; JEAsync optional.
- **Página oficial do projeto:** authority para Clinical Stretch, resurrected mobs, body parts, Organ Extractor, Surgery Stretch, Subject Assembly, Igor/failed experiments, syringes, estruturas, Witherstein e cosméticos aqui documentados.

A ausência de source público exato da 5.4.4 fica registrada como limite de verificabilidade. Nenhum hook, class, registry, packet ou formato de persistência foi preenchido por suposição.

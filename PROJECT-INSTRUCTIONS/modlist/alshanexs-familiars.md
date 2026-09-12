# Alshanex's Familiars

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8161bff9d05c4dc3b7d0
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Alshanex's Familiars
- **Arquivo JAR:** `alshanex_familiars-1.21.1_v4.0.3.jar`
- **Versão 1.21.1:** 1.21.1_v4.0.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, Mobs
- **Função:** Addon de companions mágicos para Iron's Spells com 13 familiars próprios, tame/seleção/summon/unsummon, habilidades passivas e ativas, estruturas, Sound school/spells, trinkets, Curios, consumíveis de progressão, Pandora's Box, Familiar Storage/Bed, rituais data-driven, Shrinkinator e integração com FamiliarsLib.
- **Dependências:** Iron's Spells 'n Spellbooks é base funcional obrigatória do spellcasting; FamiliarsLib fornece a infraestrutura técnica de familiars/storage/beds e está instalada no pack (`familiarslib-1.21.1-1.7.1.jar`, metadata runtime `1.21.1-1.7`).
- **Sobreposição:** Sobreposição temática com summons/companions de outros addons mágicos, porém identidade própria: familiars permanentes com progressão individual, habilidades utilitárias e infraestrutura FamiliarsLib. Sound School cruza a fantasia de Bard/Melodic, mas não deve ser tratada como a mesma escola sem conferir IDs.
- **Compatibilidade/Riscos:** Companions persistentes, múltiplos summons e resurrect-at-mana-cost podem cruzar balanceamento com outras invocações do Iron's/RPG Skill Tree. A Sound school deve ser distinguida da escola Melodic de Tunes 'n Tomes antes de criar perks por ID. Curios adicionados competem por slots com muitos acessórios do pack. Há inconsistência upstream na grafia da tag de tame (`familiar_taming` vs `familiar_tamming` em documentação); verificar namespace/tag real do JAR 4.0.3 antes de datapack. Testar owner death/dimension, storage, multi-familiar, mana drain e dedicated server.
- **Observações:** A documentação pública alterna a grafia da tag usada para itens de tame. Não automatizar datapacks até conferir o tag path dentro do runtime. Tratar FamiliarsLib como provider técnico e esta entrada como conteúdo jogável.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/wiki/documentação oficiais Alshanex's Familiars 4.0.3/FamiliarsLib e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `alshanex_familiars-1.21.1_v4.0.3.jar` / `1.21.1_v4.0.3`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/alshanexs-familiars
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #23: `alshanex_familiars-1.21.1_v4.0.3.jar` / `1.21.1_v4.0.3` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `alshanex_familiars-1.21.1_v4.0.3.jar`. Este addon é conteúdo jogável do ecossistema Iron's Spells; **FamiliarsLib** é a infraestrutura técnica compartilhada. A ficha abaixo documenta familiars, habilidades, estruturas, itens, rituais, datapackability, progressão e riscos do pack.

## 1. Arquitetura do sistema de familiars
Alshanex's Familiars transforma criaturas mágicas em companions permanentes, não em summons descartáveis. O jogador encontra/obtém familiars por exploração, tame, transformação ou ritual, mantém uma coleção, pode summon/unsummon por menu e combina o familiar ativo com equipamentos/itens próprios.

O addon depende do spellcasting de **Iron's Spells 'n Spellbooks**: escolas, spell power, mana e casting continuam pertencendo ao mod-base. **FamiliarsLib** fornece classes/base behavior, beds, storage e APIs para addons registrarem novos familiars.

### Regras operacionais gerais
- O item padrão de domesticação é **Arcane Essence** para familiars que usam tame convencional.
- Um stick permite ordenar hold/stay e impedir ataques em contextos suportados.
- Há interface de seleção para summon/unsummon.
- Depois do avanço **Learn Necromancy**, familiars podem evitar a morte consumindo mana do dono; se não houver mana suficiente, morrem normalmente.
- Vários sistemas foram construídos para até **10 familiars** simultâneos/armazenados, portanto a carga de entidades e o consumo de mana precisam ser tratados como um sistema, não como pet único.

## 2. Catálogo dos 13 familiars
### 1. Lightning Mage
**Tema:** lightning/magia elétrica. **Habitat:** regiões montanhosas.

Usa ataques de lightning e pode atingir múltiplos inimigos. **Charge Owner** concede efeitos ofensivos/mobilidade ao dono, incluindo Speed e Strength. **Charge Creeper** interage com Creepers, energizando-os. É o familiar de afinidade elétrica e recebe benefícios de trinkets próprios de eletricidade.

### 2. Archmage
**Tema:** caster multi-school/endgame. **Origem:** settlement/laboratory no End.

Conjura diferentes escolas. **Transmutation** permite usar pedestal/scrolls para transformar scroll em outro da mesma raridade, com pequena chance de upgrade conforme regra do addon. Afugenta Endermen e possui resposta defensiva a projéteis de Shulker. Representa familiar de versatilidade e manipulação mágica, não uma escola nova independente.

### 3. Necromancer
**Tema:** blood/undead. **Habitat:** graveyards no Soul Sand Valley.

Usa magia sombria/necromântica. **Undead Chaos** e **Witherify** são habilidades centrais documentadas. Também é ponto de origem para transformação no **Cleric**: o processo de Cleanse aplicado perto de um Necromancer untamed produz o familiar sagrado correspondente.

### 4. Summoner
**Tema:** eldritch/shadow summons. **Origem:** Summoner's Laboratory integrado a Ancient City.

Invoca entidades de shadow/eldritch. **True Vision** fornece Night Vision e imunidades relacionadas a Blindness/Darkness ao dono. **Dark Ritual** interfere com o ambiente de Sculk: pode desabilitar shriekers/proteger o dono da detecção pelo Warden conforme a habilidade. A build 4.0.3 nerfou shadow summons para que um golpe suficientemente forte possa matá-los, corrigindo resistência excessiva.

### 5. Hunter
**Tema:** ranged/arrows/utility. **Habitat:** camps em florestas.

Ataca à distância. **Watcher** protege automaticamente o dono contra inimigos que o marcam como alvo. **Smelling Loot** permite apresentar um item ao Hunter para que ele marque/indique entidades próximas capazes de dropar aquele recurso. Isso cria utilidade direta para caça e obtenção de loot, não apenas DPS.

### 6. Druid
**Tema:** natureza/agro. **Habitat:** florestas.

**Overgrowth** age como bonemeal/crescimento sobre crops/vegetação conforme a lógica do addon. **Nature's Blessing** auxilia reprodução de animais. Pode ser transformado em **Plague Doctor** por meio de Poison Vial, portanto Druid também é nó de progressão para outra forma de familiar.

### 7. Illusionist
**Tema:** evocation/controle/engano.

Pode aparecer disfarçado de outros familiars do Overworld. **Magic Trick** esconde/protege o dono quando sua vida está baixa. **Decoy** cria isca/taunt explosivo. **Pickpocket** consegue obter ouro/esmeralda quando a condição de não ser observado é atendida. O **Mirror of Truth** serve para revelar a forma verdadeira, importante na caça a esse familiar.

### 8. Plague Doctor
**Tema:** poison/debuff. **Aquisição:** transformação de Druid com Poison Vial.

**Poison Blood** aumenta eficiência/dano contra alvos envenenados e participa da interação com poison/cure. O familiar representa uma especialização do Druid em venenos e medicina sombria, em vez de spawn independente comum.

### 9. Scorcher
**Tema:** fire. **Habitat:** Nether quente.

**Molten Heart** pune/queima agressores. **Heatproof** concede imunidade/resistência relevante a fogo para familiar e dono. É o familiar elemental de fire/Nether.

### 10. Cleric
**Tema:** holy/support. **Aquisição:** Cleanse próximo a Necromancer untamed.

**Salvation** é direcionada a undead e pode curar Zombie Villagers, dando utilidade de mundo além do combate. Funciona como contraponto sagrado do Necromancer.

### 11. Bard
**Tema:** Sound school, suporte + offense. **Aquisição:** uso/cast de spell de Sound com Harp próximo a familiar untamed conforme a progressão documentada.

**Musical Nature** sustenta sua identidade musical. O Bard está conectado à escola **Sound**, ao Harp, Symphonic Grimoire e a oito spells específicos descritos abaixo. Não confundir automaticamente com a escola **Melodic** de Tunes 'n Tomes: confirmar IDs/tags antes de perks ou atributos compartilhados.

### 12. Frostling
**Tema:** ice/cold. **Habitat:** biomas gelados.

**Frozen Feet** congela água sob familiar/dono e facilita travessia. **Ice Heart** fornece imunidade/benefícios ligados a freezing/powder snow. É o familiar elemental de frio e interage diretamente com navegação ambiental.

### 13. Dragoon
**Tema:** Ender/melee/proteção. **Habitat:** outer End.

**Dragon Breath** permite encher bottles/obter recurso de Dragon Breath conforme a habilidade. **Dragon Heart** fornece regeneration. **Protector** pode trocar de posição/intervir quando um familiar aliado recebe melee attack e então focar o agressor. É uma unidade de proteção de grupo, especialmente relevante quando múltiplos familiars estão ativos.

## 3. Estruturas e progressão de exploração
### Origin Island
Ilha perdida/oceânica que funciona como hub de origem/progressão. Contém **Lost Souls**, que podem ser engarrafadas para rituais. O caminho de localização documentado envolve Sniffer encontrando **Crystal Berries**; alimentar um familiar tamed produz **Memory Fragments**, que funcionam de forma semelhante a Eyes of Ender para orientar a busca.

### Hunter's Campsite
Ponto de exploração em florestas associado ao Hunter. O **Furled Map** participa da navegação/progressão relacionada à Origin Island.

### Graveyard
Estrutura no **Soul Sand Valley**, associada ao Necromancer e à progressão necromântica.

### Summoner's Lab
Laboratório integrado ao contexto de **Ancient City**, usado para encontrar/progredir o Summoner. Mudanças de versões 3.x corrigiram colocação/interação dessa estrutura, logo packs que alteram Ancient Cities precisam validar geração real.

### Archmage settlement/lab
Estrutura do **End**, associada ao Archmage e ao estágio tardio de exploração.

## 4. Familiar Trinkets
O addon possui trinkets direcionados a famílias/familiars específicos e usados para aumentar spell power/eficiência correspondente. A documentação lista:
- **Magic Leaf**
- **Chain of Hunter**
- **Insignia Lich**
- **Forgotten Crown**
- **Shiny Crown**
- **Golden Bell**
- **Conjurer's Candlestick**
- **Blessed Calyx**
- **Emblem Electricity**
- **Contaminated Cheese**
- **Lost Melody**
- **Frostbloom**
- **Dragon Tear**

Para quests/economia, cada trinket deve ser ligado ao familiar/estrutura/origem real descrita pelo addon; não tratar todos como loot genérico de dungeon.

## 5. Curios e acessórios de suporte
### Holy Water Grail
Curio de suporte; documentado com bônus de **mana regeneration** e capacidade de curar familiars consumindo mana.

### Invisibility Robes
Aumenta max mana e concede invisibility/proteção quando familiar entra em baixa vida conforme a regra do item.

### Statue of Willpower
Fornece spell resistance e ativa proteção tipo oakskin quando a vida do familiar cai abaixo de determinado limiar.

### Heart of Ender
Reduz cast time e fornece janela de evasão/escape ligada a baixa vida do familiar.

Esses itens aumentam pressão sobre slots Curios num pack que já possui muitos acessórios mágicos/RPG. Auditar slot IDs e possibilidade de stacking é obrigatório.

## 6. Consumíveis de progressão de familiar
- **Life Fruits:** tiers de aumento/progressão de health.
- **Armor Plates:** tiers de armor.
- **Magic Essence:** tiers ligados a spell power/spell resistance.
- **Tiny Shield:** adiciona chance de block/defesa.
- **Strange Mushroom:** sistema de enraged/stacking com risco/trigger próprio.
- **Magic Eye:** acompanha/procura informações de progressão do familiar.
- **Tiny Totem:** mecanismo emergencial de sobrevivência/revival quando não há mana suficiente para o mecanismo padrão.

FamiliarsLib expõe componente data-driven `familiarslib:familiar_consumable` com tipos documentados como `armor`, `health`, `spell_power`, `spell_resist`, `enraged` e `blocking`, permitindo expansão por dados/addons.

## 7. Pandora's Box e gestão de múltiplos familiars
**Pandora's Box** permite summon/unsummon de até **10 familiars**. Essa escala muda o custo do sistema: AI, targeting, spell entities, mana-based survival e sincronização passam a ser multiplicados.

Em servidor/dedicated server, validar:
- limite real;
- ownership;
- retorno após relog/dimension change;
- comportamento ao morrer;
- descarregamento de chunk;
- consumo simultâneo de mana quando vários familiars recebem dano fatal.

## 8. Familiar Tome of Alignment
O **Familiar Tome of Alignment** distribui atributos mágicos do dono entre os familiars atualmente summoned. A documentação exemplifica divisão proporcional: com 10 familiars, cada um recebe aproximadamente 10% da parcela distribuída; com 2, 50% cada. Isso cria interação direta com builds de spell power/atributos e deve ser considerado no RPG Skill Tree para evitar multiplicação não intencional de bônus.

## 9. Staff, spellbook e Sound school
### Harp
Staff/instrumento ligado à escola Sound e ao Bard.

### Symphonic Grimoire
Spellbook dedicado ao conteúdo Sound/Bard.

### Oito spells Sound documentados
1. **Clef** — projétil/note ofensivo.
2. **Chord Blast** — melody damage; alvo que ouviu a execução sofre restrição/penalidade de evasão conforme a mecânica.
3. **Crescendo** — dano com stun/controle.
4. **Grand Finale** — armadilha/efeito culminante com explosão musical.
5. **Celestial Chant** — cura/proteção de aliados.
6. **Rhapsody** — invoca/usa birds/efeitos musicais para buff de aliados.
7. **Sonata** — música rápida com função ofensiva.
8. **Serenade** — música lenta que induz sleep/controle em inimigos.

Ao criar perks, usar os **registry IDs reais** da Sound school/spells depois de inspecionar runtime. Nome de exibição não é contrato técnico.

## 10. Familiar Bed
Bloco de descanso/healing de familiars. É parte da infraestrutura FamiliarsLib e deve ser testado com ownership e healing real, principalmente após unload/reload de chunk.

## 11. Familiar Storage
Armazena até **10 familiars**. Enquanto armazenados, ficam protegidos/invulneráveis na maior parte do comportamento e podem operar em modos próprios.

### Store mode
Mantém familiars armazenados e permite transferência/gestão.

### Wander mode
Permite que familiars circulem em distância configurável e, quando habilitado, utilizem abilities. Isso pode criar farms/automação indireta dependendo do familiar.

Regras documentadas incluem owner-only para quebra/gestão, restrições para quebrar storage quando ocupado e limite espacial de storage, incluindo regra de distância de aproximadamente 16 blocos entre unidades na documentação atual. Validar valores exatos no config/runtime antes de construir automação.

## 12. Ritual system
Rituais são **data-driven** e exibidos no JEI. A documentação aponta recipes sob `data/alshanex_familiars/recipe/ritual_recipes` na estrutura de dados da linha atual.

Um ritual pode descrever:
- item central;
- quatro inputs/ingredientes;
- quantidades;
- se cada input é consumido;
- resultado;
- partícula/efeito opcional.

Isso é superfície canônica para modpack customization. Alterar ritual via datapack é preferível a mixin quando o contrato de recipe satisfaz o requisito.

## 13. Shrinkinator
Bloco/máquina de processamento que reduz alimentos/recursos para versões usadas pelos familiars e produz itens de cura/progressão como armor/defense consumables. Possui suporte a **hopper automation**. A documentação indica recipes em `data/alshanex_familiars/recipe/shrinking`.

Qualquer linha de automação Create/hopper deve validar sides/slots e não presumir que todo input pode ser extraído automaticamente.

## 14. Shards, spirits e itens de exploração
A Origin Island/ritual loop envolve **Bottle of Spirit**, shards e itens de memória. A documentação registra um shard por muitos dos familiars naturalmente encontrados no Overworld, com exceções como Illusionist. Outros itens importantes:
- **Crystal Berry**
- **Memory Fragment**
- **Bottle of Spirit**
- **Mirror of Truth**
- **Venomous Spider Fang**
- **Mysterious Orb**

O **Mysterious Orb** está ligado à obtenção do Harp/Lost Melody e exige marcos de tame para os familiars elementais conforme a progressão da documentação.

## 15. Datapackability e tags
### Taming item tag
Há uma **inconsistência de documentação upstream**: fontes antigas mencionam `familiar_taming`, enquanto a wiki atual exibe `familiar_tamming` em determinado trecho. Não criar datapack baseado apenas na grafia textual. **Abrir o JAR 4.0.3 e verificar o tag path real** antes de uso.

### Spell tags
O roster de spells de familiars pode ser alterado por tags sob o namespace de dados de `alshanex_familiars`/`irons_spellbooks/spells`, conforme a documentação atual. Isso permite adicionar/remover spells aceitos por um familiar sem hardcode quando o addon expõe a tag correspondente.

### FamiliarsLib API
A biblioteca fornece bases para registrar familiar custom, familiar bed/storage e outros contratos. Para projeto próprio, usar API/library antes de copiar classes internas.

## 16. Versão 4.0.3 — mudanças confirmadas
A release NeoForge 1.21.1 v4.0.3 registra:
- correção de crash com versões recentes de NeoForge;
- nerf de **shadow summons**: um hit com dano suficiente agora consegue matá-los;
- correção de crash do spell **Fire Fist**.

Essas correções são relevantes porque o pack usa NeoForge atual e porque Summoner/shadow summons fazem parte do roster acima.

## 17. Sobreposição e riscos no pack
### Outros summons/companions
Iron's e seus addons possuem summons, servants e spellcasters; Goety e outros sistemas também podem controlar minions. A sobreposição é temática e operacional: quantidade de entidades, targeting e buff sharing. Não declarar incompatibilidade sem reprodução.

### RPG Skill Tree
Perks de companion devem usar eventos/atributos do provider e evitar aplicar o mesmo bônus uma vez por familiar quando a intenção era aplicar por jogador. Tome of Alignment e multi-summon são superfícies de multiplicação.

### Sound vs Melodic
O pack possui conteúdo musical de outros addons. **Sound** e **Melodic** não devem ser fundidas por nome. Conferir school IDs, tags, attributes e spell power modifiers antes de criar uma árvore de Bard compartilhada.

### Curios
Vários acessórios adicionam max mana, cast time, spell resistance e passivos de baixa vida. A interação com outros Curios/affixes pode produzir stacking maior que o esperado.

## 18. Matriz de validação
1. Tame de cada familiar que usa Arcane Essence/rota convencional.
2. Aquisições especiais: Plague Doctor, Cleric, Bard, Illusionist reveal.
3. Testar as abilities dos **13 familiars** uma por uma.
4. Verificar summon/unsummon, hold/stay e owner targeting.
5. Testar Learn Necromancy + lethal hit com mana suficiente/insuficiente.
6. Pandora's Box com 1, 2 e 10 familiars.
7. Tome of Alignment com 2 e 10 familiars; medir spell power recebido e procurar multiplicação.
8. Origin Island, Hunter Camp, Graveyard, Ancient City Lab e End Archmage structure em mundo novo.
9. Lost Souls → Bottle/ritual → shards/memory loop.
10. Familiar Bed heal/persistence.
11. Familiar Storage Store/Wander, range, owner-only, chunk unload.
12. Shrinkinator manual + hopper automation.
13. JEI ritual recipes e pelo menos um ritual custom em datapack.
14. Verificar a **grafia real do tag de taming** dentro do JAR.
15. Testar os 8 Sound spells e seus registry IDs.
16. Testar Holy Water Grail, Robes, Statue, Heart of Ender e slot Curios.
17. Dedicated server: ownership, death, dimension transfer, relog e disconnect.
18. Performance: 10 familiars castando/targeting simultaneamente.
19. Interação com Epic Fight/animations quando owner/familiar combate.
20. Interação com perks do projeto próprio usando fail-closed se hooks/provider não forem comprovados.

## 19. Fontes e confiança
**Authority física:** modlist de 07/09/2026.

**Fonte upstream:** [CurseForge — Alshanex's Familiars](https://www.curseforge.com/minecraft/mc-mods/alshanexs-familiars), release v4.0.3 e wiki/documentação oficial/comunitária mantida pelo ecossistema do addon.

**Fonte interna do projeto:** guia completo de mods de magia, que classifica Alshanex's Familiars como camada jogável de companions e FamiliarsLib como infraestrutura.

**Confiança:** alta para roster, sistema geral, release 4.0.3 e funcionalidades documentadas. Para IDs, tag path de taming e qualquer hook usado em implementação, exigir verificação direta do JAR/source antes de codificar.

# Fantasy Armor — 1.2.4-1.21.1

> **Runtime físico confirmado:** `fantasy_armor-neoforge-1.2.4-1.21.1.jar` · mod id `fantasy_armor` · versão `1.2.4-1.21.1` · NeoForge 1.21.1. Source 1.21.1 auditado no repositório oficial.

## 1. Papel no modpack
Fantasy Armor é um content mod de equipamento RPG/visual. Ele registra armaduras jogáveis com modelos 3D próprios, atributos configuráveis e efeitos de set/peça definidos pelo próprio mod. Seu valor no pack é fornecer equipamento medieval/fantasia distinto, não apenas uma camada de textura.

## 2. Authority / ownership
- **Fantasy Armor:** itens de armadura que registra, atributos/effects configurados e assets 3D correspondentes.
- **Minecraft/NeoForge:** slots de equipamento, armor lifecycle e damage pipeline base.
- **Epic Fight/Cosmetic Armor/FirstPerson:** podem alterar apresentação ou leitura do equipamento, mas não devem reescrever o state do item sem bridge explícita.

## 3. Catálogo de conjuntos source-confirmed
O enum `FAArmorSet` da árvore NeoForge 1.21.1 lista 29 conjuntos: Eclipse Soldier, Dragonslayer, Hero, Golden Horns, Thief, Wandering Wizard, Chess Board Knight, Dark Lord, Sunset Wings, Fog Guard, Dark Cover, Spark of Dawn, Golden Execution, Forgotten Trace, Redeemer, Twinned, Gilded Hunt, Lady Maria, Crucible Knight, Evening Ghost, Ronin, Malenia, Old Knight, Silver Knight, Dead Gladiator, Flesh of the Feaster, Wind Worshipper, Grave Sentinel e Ornstein.

O registro percorre os sets e peças de armadura; não atribuir stats numéricos universais sem ler o config efetivo.

## 4. Itens e registro
`FAArmorItems` mantém o mapa set → peça e registra os ArmorItems. `FAItems` registra itens adicionais e inclui pelo menos `moon_crystal` na linha 1.21.1. A creative tab é própria do mod.

A enumeração acima é source-confirmed para a árvore NeoForge 1.21.1; o JAR físico continua autoridade final caso um update altere o registry.

## 5. Modelos e assets
Cada `FAArmorSet` deriva paths `geo/<set>_armor.geo.json`, `textures/armor/<set>_armor.png` e overlay correspondente. Isso confirma um pipeline de geometria/texture específico por set, razão pela qual mods que trocam player/armor renderer entram no regression matrix.

## 6. Configuração de atributos e efeitos
A inicialização NeoForge registra dois configs comuns: `fantasy_armor-armor_attributes.toml` e `fantasy_armor-armor_effects.toml`. Logo, armor values/effects devem ser tratados como **configuráveis**, não codificados na documentação do pack sem ler os arquivos reais da instância.

## 7. Equip lifecycle
Equip/unequip, troca rápida de peça, death/respawn, durability/break e reload precisam preservar exatamente o atributo/effect correspondente ao item equipado. Um efeito de set não pode permanecer após remover a peça nem ser aplicado duas vezes por outro sistema RPG.

## 8. Integrações concretas no pack
- **Epic Fight 21.17.3.1:** battle-mode/armature pode alterar apresentação e atributos de combate percebidos; validar clipping e armor semantics sem double modifiers.
- **Cosmetic Armor Reworked:** separa visual de armadura funcional; validar qual slot determina aparência e qual item continua authority dos stats.
- **FirstPerson 2.7.2:** corpo em primeira pessoa aumenta a chance de clipping de modelos 3D.
- Player model/animation stack e resource packs também precisam de smoke-test visual.

Nenhuma dessas relações implica dependência hard.

## 9. Client / Server
**Servidor:** item equipado, durability, atributos, efeitos e qualquer consequência gameplay.

**Cliente:** geometria, textura, animation/render do armor.

O renderer nunca deve decidir stats ou effect application.

## 10. Lifecycle
Validar login/relog, equip/unequip de cada slot, quebra da peça, death/respawn, dimension change, inventory move, config reload/restart quando aplicável, resource reload e update de renderer stack.

## 11. Multiplayer
Outro cliente deve enxergar o set correto sem influenciar os stats do wearer. Equip state e effects precisam ser sincronizados pelo servidor; cosmetic layer não pode causar duplicação de atributos.

## 12. Riscos
1. clipping com Epic Fight/FirstPerson/player-model mods;
2. Cosmetic Armor exibir peça diferente da funcional;
3. config de atributos gerar stacking inesperado com Apothic/Additional Attributes;
4. effect permanecer stale após unequip/death;
5. resource pack substituir asset incompatível;
6. model path/overlay faltar em um set;
7. durability break não remover modifier;
8. set update alterar registry IDs;
9. client/server divergirem sobre equipment state;
10. tratar os 29 sets como mero cosmético e ignorar seus contracts gameplay.

## 13. Matriz de testes
1. Dedicated server boot.
2. Equip completo de amostras de sets com stats conferidos no servidor.
3. Unequip peça por peça e verificar modifiers/effects.
4. Death/respawn e relog.
5. Durability/break.
6. Epic Fight battle mode.
7. Cosmetic Armor funcional vs visual.
8. FirstPerson/third-person.
9. Dois jogadores observando armor swap.
10. Resource reload e shader/resource-pack stack.
11. Alteração controlada dos dois configs e restart.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física canônica: JAR/mod id/version/hash;
- CurseForge oficial: release NeoForge 1.21.1 e natureza de armor content mod;
- source oficial `kend1e/FANTASY-ARMOR`, árvore `appNeoForge/1.21.1`: `FAArmorSet`, `FAArmorItems`, `FAItems`, creative tab e configs de attributes/effects.

> **Boundary canônico:** Fantasy Armor é authority dos **itens, stats/effects configurados e assets dos próprios sets**; outros mods podem renderizar ou consumir esse state, mas não devem criar uma segunda fonte de verdade.
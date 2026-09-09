# Ace's Spell Utils

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816b88f0e0b53eaf6262
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ace's Spell Utils
- **Arquivo JAR:** `aces_spell_utils-1.2.7.2-1.21.1.jar`
- **Versão 1.21.1:** 1.2.7.2-1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Magia
- **Função:** API/addon utilitário para Iron's Spells com conteúdo técnico e registros próprios. Fornece classes-base para spellcasting mobs/bosses, combo goals, domain spell entities; bases de curios/staffs/maces/swords/magic guns/loot bags/spellbooks; 13 atributos mágicos/RPG; escolas Occult/Hydro/Technomancy; casting animations, summon base, boss/phase music, post-processing, emissive GeckoLib armor, impact/roar/chromatic-aberration/ribbon/trail/screen-shake handlers e interfaces de keep-inventory/phase. 1.2.7.2 corrige Evasive e expande VFX/phase APIs.
- **Dependências:** REQUIRED upstream: Iron's Spells 'n Spellbooks. Runtime do pack: `irons_spellbooks-1.21.1-3.16.3.jar`. GeckoLib aparece como tecnologia de um renderer emissivo fornecido pela API, mas não foi promovido a hard dependency porque CurseForge relations lista apenas Iron's como required. Addons consumidores podem exigir outras deps.
- **Sobreposição:** Infraestrutura e conteúdo utilitário para addons de Iron's. Sobreposição de resultado com outros providers de atributos/escolas, mas não equivalência automática. Occult/Hydro/Technomancy podem ter escolas tematicamente parecidas de outros addons; só há colisão se registry IDs/semântica realmente coincidem. VFX utilities competem apenas na camada visual.
- **Compatibilidade/Riscos:** Não é library invisível: registra attributes/schools que podem participar diretamente do gameplay. Comparar IDs/pipelines com Additional Attributes, Apothic Attributes e Pufferfish's Attributes antes de perks. VFX handlers/post-processing/screenshake/ribbons/trails cruzam shaders/AAA/Photon/Lodestone; phase/music interfaces cruzam boss systems. 1.2.7.2 depreca os dois boss music managers antigos e corrige Evasive que antes não fazia nada. Exigir dedicated-server tests e fail-closed para consumer mismatch.
- **Observações:** Não inferir fórmulas exatas dos 13 atributos só pelo nome. Para perk/gear, inspecionar registry ID, min/max/default e ponto de aplicação no runtime/source. Boss Music Managers antigos estão deprecated em 1.2.7.2; novas integrações devem preferir as interfaces/manager rework aplicáveis ao consumer.
- **Procedência:** Modlist física 2026-09-07 + CurseForge oficial Ace's Spell Utils, feature list, relations e release 1.2.7.2 + guia mágico do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/aces-spell-utils
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê operacional completo de API/classes-base, 13 attributes, schools, VFX, phase/music, consumers e authority Iron's confirmado no QC global #3.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `aces_spell_utils-1.2.7.2-1.21.1.jar`. Ace's Spell Utils é simultaneamente **API para addons de Iron's** e provider de atributos/escolas/utilidades que existem de fato quando o JAR está carregado. Não resumir como “library que não faz nada sozinha”.

## 1. Objetivo arquitetural
O mod reduz duplicação entre addons de **Iron's Spells 'n Spellbooks**. Em vez de cada addon reimplementar spellcasting AI, boss phases, ability weapons, renderers ou VFX, Ace's expõe bases reutilizáveis.

Isso cria dois tipos de superfície:
1. **API/classes-base** — só produzem gameplay quando um consumer as usa;
2. **registries/utilidades próprias** — attributes, schools e handlers disponíveis diretamente no ambiente.

Auditorias devem separar esses dois grupos.

## 2. Dependência obrigatória
CurseForge relations declara **Iron's Spells 'n Spellbooks** como `Required Dependency`.

Runtime atual do pack:
`irons_spellbooks-1.21.1-3.16.3.jar`

Iron's continua authority de:
- mana;
- spell registry;
- spell schools base;
- spell power;
- scroll/spellbook casting;
- cast lifecycle.

Ace's estende esse ecossistema; não cria um segundo spell engine independente.

## 3. Classes-base de entidades
### Unique Spellcasting Mob
Base para mob com **custom model** e animações próprias para diferentes tipos de cast:
- long cast;
- instant cast;
- continuous cast.

Isso evita que addons precisem tratar todas as animações como mob humanoide vanilla.

### Unique Melee Spellcasting Mob
Base para entidade que combina melee e spellcasting.

### Generic Boss Entity
Estrutura genérica para bosses customizados, centralizando comportamento comum de boss.

### Generic Unique Boss Entity
Versão voltada a spellcasters/bosses com custom models.

### Wizard Spell Combo Goal
AI goal para sequências/combinações de spells, permitindo que mobs executem combos em vez de selecionar cast isolado sem contexto.

### Custom Domain Spell Entity
Base para entidades/efeitos associados a spells de domínio/área customizados.

## 4. Phase interfaces — 1.2.7.2
A 1.2.7.2 adicionou:
- `IPhaseEntity`;
- `IPhaseMusicEntity`.

A lógica de phase e phase-based music que antes estava presa a boss classes foi movida para interfaces. Consequência: **entidades que não são bosses** também podem possuir phases e música por phase se o consumer implementar o contrato.

Para projeto próprio, isso é preferível a detectar “é subclass de boss?” quando a feature desejada é especificamente phases.

## 5. `IKeepInventoryEntity` — 1.2.7.2
A release adiciona interface `IKeepInventoryEntity`.

O nome indica contrato para entidades que precisam preservar inventário/estado de inventory em determinado lifecycle, mas a documentação pública não detalha todas as condições/fórmulas. Não usar o nome da interface para inventar política global de keepInventory; inspecionar consumer/source antes de integrar.

## 6. Bases de itens — curios e staffs
### Imbueable Curio
Base para Curio que pode receber/armazenar spell/imbuement conforme consumer.

### Preset Imbue Curio
Versão com imbuement pré-definido.

### Imbueable Staff
Staff preparado para imbuement.

### Preset Imbue Staff
Staff com seleção/configuração predefinida pelo addon consumer.

### Sheath Curio
Curio de bainha com **custom player model rendering**. Isso toca render do corpo do jogador e precisa ser testado com Epic Fight/CPM/ragdoll/armor layers.

## 7. Custom item rarities
A API oferece rarities customizáveis para consumers.

Isso é diferente de rarity/affix do Apotheosis. Se um addon Ace's usa uma rarity visual própria, não presumir que ela participa automaticamente de loot rarity Apothic ou de Create Attribute Filters.

## 8. Mace ecosystem
A feature list publica:
- Extended Mace Item;
- Imbueable Mace Item;
- Preset Imbue Mace Item;
- Mace Staff Item.

Na **1.2.7.2** foram adicionadas variantes **Active & Passive ability Imbueable / PresetImbue mace items**.

Isso transforma mace em superfície de ability item e spell/imbue, não somente melee weapon.

## 9. Sword bases
### Active + Passive Ability Sword
Pode combinar habilidade acionável com efeito passivo.

### Active + Passive Ability Magic Sword
Versão integrada à fantasia/sistemas mágicos dos consumers.

Ao auditar arma de addon que herda essas classes, descobrir:
- active trigger;
- passive trigger;
- cooldown;
- mana/cost;
- server authority;
- stacking com atributos/perks.

A classe-base sozinha não define os valores do consumer.

## 10. Magic Gun Item
Base para arma mágica ranged. Não confundir com um arsenal próprio do Ace's: o conteúdo final pertence ao addon que instancia a classe.

Interações críticas no pack:
- projectile attributes;
- Epic Fight/ranged animations;
- Apothic loot category;
- magic projectile crit attributes do próprio Ace's.

## 11. Custom Loot Bag
Base de loot bag que usa **loot tables especificadas**.

Para economy/quests, a authority do conteúdo deve ser a loot table do consumer. Não hardcodar drops observados uma vez.

## 12. Flat Cooldown Passive Ability Item
Base para item com passiva controlada por cooldown fixo conforme consumer.

## 13. Passive Ability Spellbook
Base para spellbook que acrescenta passive ability além do comportamento padrão de spellbook.

## 14. Catálogo dos 13 atributos publicados
O upstream lista:
1. **Mana Steal**
2. **Mana Rend**
3. **Goliath Slayer**
4. **Hunger Steal**
5. **Spell Resistance Penetration**
6. **Evasive**
7. **Magic Damage Crit Chance**
8. **Magic Damage Crit Damage**
9. **Magic Projectile Crit Chance**
10. **Magic Projectile Crit Damage**
11. **Magic Projectile Bonus Damage**
12. **Life Recovery**
13. **Vigor Reap**

### Regra de documentação
O publisher fornece os nomes, mas a página principal **não publica fórmula completa, base/default, min/max e operation semantics de todos eles**. Portanto esta ficha não inventa percentuais.

Antes de perk/equipment usar um atributo:
- confirmar registry ID;
- base/default;
- min/max;
- se é addition/multiplier;
- trigger/pipeline onde é consumido;
- interaction com source/target.

## 15. Evasive — correção específica 1.2.7.2
O changelog diz explicitamente: **Fixed Evasive attribute not doing anything at all**.

Isso significa que builds anteriores podem ter apresentado atributo registrado mas sem efeito funcional. A build 1.2.7.2 deve ser usada como baseline ao testar Evasive.

## 16. Overlap dos atributos no pack
O pack possui:
- Additional Attributes 1.2.2;
- Apothic Attributes 2.10.1;
- Pufferfish's Attributes 0.8.3;
- RPG Skill Tree próprio;
- Iron's Spells attributes;
- equipamentos/affixes.

Exemplos de **resultado** potencialmente sobreposto:
- crit chance/damage;
- penetration;
- sustain/life recovery;
- steal mechanics;
- projectile damage.

Isso não prova duplicate registry IDs. O risco é **double-dipping em grandeza final**.

## 17. Custom schools
Ace's disponibiliza três schools utilitárias:
- **Occult**
- **Hydro**
- **Technomancy**

A existência da school não significa que Ace's fornece sozinho um catálogo completo de spells para cada uma. Addons consumers podem usá-las.

### Regra de integração
Outro addon pode ter school visualmente chamada Hydro/Technomancy/Occult. Só tratar como mesma school se **registry ID** e contract forem o mesmo.

## 18. Casting animations
A API oferece várias custom casting animations para consumers. Isso permite que spellcasters/players usem animações além do conjunto padrão do Iron's.

No pack, animação cruza:
- Epic Fight;
- playerAnimator;
- EFIS compatibility;
- custom entity models;
- GeckoLib consumers.

Testar casting em first/third person e sob Battle Mode quando o consumer é jogador.

## 19. Abstract Summon Spell
Base abstrata para addons implementarem summon spells.

Importante: isso **não define automaticamente uma política global de summons**. Cada consumer pode decidir:
- ownership;
- limite;
- duração;
- despawn;
- death behavior;
- dimension transfer.

Não usar `Abstract Summon Spell` como prova de que todos os summons Ace's são equivalentes.

## 20. Boss Music Manager / Boss Music Instance
A feature list original oferece managers de boss music dinâmica.

Na **1.2.7.2**, ambos os boss music managers anteriores foram **deprecated** em preparação para rework.

### Regra
Novos consumers não devem ser desenhados dependendo de API deprecated se já existir/release seguinte apontar contrato substituto. Consumers antigos precisam continuar sendo testados até migração.

## 21. Phase music
Com `IPhaseMusicEntity`, música pode seguir phase state, inclusive em non-boss entity.

Isso exige sync consistente de phase server→client; áudio é client-side, mas a mudança de phase que afeta combate deve permanecer server-authoritative.

## 22. Spell/Mob utility classes
O projeto inclui utility methods para necessidades recorrentes de spells e mobs. A documentação principal não enumera método por método; portanto outro chat que precise de chamada programática deve abrir o source/JAR da versão 1.2.7.2 antes de escrever código.

## 23. Post-processing shader handler
Ace's fornece handler para **custom post-processing shaders**.

Isso pode ser usado por consumers para effects de tela. Interações:
- shader pack;
- outros post-processing mods;
- AAA/Photon/Lodestone effects;
- camera/screen effects do Epic Fight.

Problema visual deve ser reproduzido com handler consumidor identificado.

## 24. Emissive GeckoLib armor renderer
A API oferece renderer emissivo para armor GeckoLib.

Isso não prova que GeckoLib é hard dependency publicada de Ace's — CurseForge relations lista somente Iron's como required — mas consumers que usam esse renderer naturalmente precisam que o stack necessário esteja disponível. O pack possui GeckoLib 4.9.2.

## 25. VFX Update 1.2.7.2
A release instalada adiciona um grande conjunto de handlers:

### Impact Frame Handler
Permite frames de impacto customizáveis.

### Roar Handler
Distorção visual para roar effects.

### Chromatic Aberration Handler
Efeito de aberração cromática pós-processado.

### Ribbon Handler
Trails em ribbon.

### Trail Handler
Infraestrutura geral de trails.

### Trail Particle
Partícula própria usada nos efeitos de trail.

### Custom Shake Manager
Gerenciamento de **screenshake**.

Essas superfícies são client/render-facing, mas normalmente são disparadas por eventos de gameplay server-synced do consumer.

## 26. Ability item text colors
1.2.7.2 também ajusta cores de texto dos ability items. É apresentação/UI; verificar resource packs/font/high-contrast se tooltips ficarem pouco legíveis.

## 27. Consumers e ownership
Como API, Ace's é útil somente em contexto de consumers. Para qualquer mod do pack que dependa dele, registrar separadamente:
- qual class/interface é usada;
- qual conteúdo o consumer registra;
- quais configs/values pertencem ao consumer;
- qual failure ocorre se a API divergir.

Isso evita atribuir spell/item/mob do addon ao Ace's core.

## 28. Failure modes
### API mismatch
`NoSuchMethodError`, missing class/interface ou mixin failure após update independente.

### Duplicate registry
Dois consumers podem registrar IDs iguais; isso não é resolvido pela API.

### Attribute registered but pipeline not consuming
Caso histórico do Evasive demonstra que “aparece no registry” não garante efeito funcional.

### Renderer/post-processing conflict
Visual corruption, pass order, screenshots/menus afetados.

### Phase sync
Cliente exibindo phase/music diferente do estado server-side.

## 29. Matriz de validação — API/content
1. Startup com Iron's 3.16.3.
2. Registry de 13 attributes sem duplicates.
3. Registry das schools Occult/Hydro/Technomancy.
4. Evasive funcionando na build 1.2.7.2.
5. Mana Steal/Rend em consumer real.
6. Magic damage crit chance/damage.
7. Projectile crit chance/damage/bonus.
8. Spell Resistance Penetration.
9. Life Recovery/Vigor Reap/Hunger Steal.
10. Goliath Slayer.

## 30. Matriz de validação — entities/items
1. Unique spellcasting mob: long/instant/continuous casts.
2. Melee spellcaster.
3. Boss entity + phases.
4. Non-boss `IPhaseEntity`.
5. Phase music.
6. Wizard Spell Combo goal.
7. Domain spell entity.
8. Imbueable/Preset Curio.
9. Imbueable/Preset Staff.
10. Sheath Curio player render.
11. Mace variants incluindo active/passive.
12. Ability Sword/Magic Sword.
13. Magic Gun.
14. Loot Bag loot table.
15. Passive spellbook/cooldown item.
16. Abstract Summon consumer.

## 31. Matriz de validação — VFX
1. Impact frame.
2. Roar distortion.
3. Chromatic aberration.
4. Ribbon trail.
5. Trail handler/particle.
6. Screenshake.
7. Post-processing com shader OFF/ON.
8. Emissive GeckoLib armor.
9. Multiplayer: outro cliente recebe VFX trigger sem controlar gameplay.

## 32. Dedicated server
Executar spellcasting mobs, phases e item abilities em dedicated server. Qualquer handler puramente visual precisa ficar protegido de client class loading no servidor.

## 33. Regras para outros chats
- Ace's não é “só library”.
- Nem todo recurso listado é conteúdo final; muitos são **bases para consumers**.
- Não inferir matemática dos attributes pelo nome.
- Evasive deve ser testado especificamente porque foi corrigido em 1.2.7.2.
- Boss music managers antigos estão deprecated.
- Para código, verificar assinatura no JAR/source 1.2.7.2 antes de chamar API.
- Para school/attribute overlap, comparar IDs reais e pipeline, não tradução/nome.

## 34. Fontes e confiança
**Authority física:** modlist 07/09/2026.

**Upstream:** [Ace's Spell Utils — CurseForge](https://www.curseforge.com/minecraft/mc-mods/aces-spell-utils), relations e [release 1.2.7.2](https://www.curseforge.com/minecraft/mc-mods/aces-spell-utils/files/8789930).

**Fonte interna:** guia completo de magia.

**Confiança:** alta para a lista de classes/features/attributes/schools e changelog 1.2.7.2. Fórmulas, registry IDs completos e assinaturas programáticas devem ser inspecionados no runtime/source antes de implementação.

# Epic Fight — 21.17.3.1

> **Runtime físico confirmado:** `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar` · mod id `epicfight` · versão `21.17.3.1` · NeoForge 1.21.1. A versão física prevalece sobre índices públicos atrasados; contratos abaixo vêm do source/docs oficiais da branch 1.21.1 e não de changelog inventado para 21.17.3.1.

## 1. Papel no modpack
Epic Fight é o **provider principal de combate action-RPG** desta instalação. Ele substitui/estende o combate vanilla quando o jogador entra em battle mode e fornece animation/combat state, weapon capabilities, stamina, stun, skills, special attacks e patches de entidades.

## 2. Authority / ownership
- **Epic Fight:** battle mode, player/entity patches, combat animations, weapon capability, stamina/stun/skill state e special-attack framework.
- **Minecraft/weapon provider:** ItemStack, durability, base enchantments e state original quando não substituído pelo capability.
- **Addons Epic Fight:** conteúdo/skills/patches que registram, sem criar uma segunda cópia do player patch.
- **Outros combat frameworks:** só podem coexistir com ownership explícito do evento de ataque/damage/movement.

O erro mais perigoso é executar dano/skill duas vezes: uma pelo Epic Fight e outra por integração externa observando a animação.

## 3. Battle mode
O README 1.21.1 documenta um modo Epic Fight/battle mode que pode ser ativado/desativado em runtime. Quando desativado, player model, animations e combat mechanics do Epic Fight deixam de ser a camada ativa.

Integrações precisam consultar o state correto; não assumir que Epic Fight controla todo clique de ataque permanentemente.

## 4. Inputs e ataques
O sistema documenta:
- basic attack;
- dash attack durante sprint;
- dodge mediante skill;
- special/weapon innate attack;
- toggle de Epic Fight mode.

A branch 1.21.1 também possui abstraction própria de input para compatibilidade com keyboard/controller. Mods próprios devem preferir input actions/API pública a hooks diretos em KeyMapping quando o objetivo for integrar-se ao Epic Fight.

## 5. Weapon capabilities
Epic Fight associa **item/weapon capabilities** a armas para definir weapon type, moveset, animation, attributes e innate/special behavior.

A documentação oferece Item Capability/Weapon Type guides e tooling/data para adaptar armas modded. Isso é a superfície correta para fazer uma arma externa participar do combat framework; não substituir o ItemStack por outro item paralelo.

## 6. Attributes de combate
A documentação pública lista atributos do framework como:
- **Armor Negation**;
- **Impact**;
- **Hit N enemies per swing**;
- **Weight**;
- **Stun Armor**.

Esses valores afetam a matemática/controle de combate do Epic Fight. Sistemas RPG externos devem ler/aplicar modifiers com cuidado e evitar uma segunda interpretação independente dos mesmos atributos.

## 7. Special attacks / weapon innate skills
A documentação descreve special attacks associados às armas e gauge/progressão por combate. Weapon type/offhand combination também pode mudar attack style e special behavior.

A liquidação de damage, gauge e cooldown deve ocorrer uma vez no provider. Tooltip/animação não é autoridade de execução.

## 8. Stun
Ataques animados podem aplicar **stun** e impedir controle por um intervalo. Weight/Stun Armor participam da resistência/timing documentado.

Stun é state de gameplay e deve convergir no servidor. Não reproduzir o mesmo immobilize em outro mod apenas porque a animação Epic Fight indica hit.

## 9. Stamina e skills
A documentação organiza skills em categorias como:
- Dodge;
- Guard;
- Passive.

Dodge/Guard consomem stamina; passive skills reagem a condições. Skill state é persistente e altamente integrável por addons.

Qualquer novo skill slot deve ter nome globalmente único; a própria API 1.21.1 alerta que colisões de enum/slot podem causar crash no bootstrap.

## 10. Skill books e skill-tree ecosystem
O core tradicional possui skill books como mecanismo de aprendizado. O projeto também documenta **Epic Fight: Skill Tree** como addon oficial capaz de substituir esse modelo por skill tree.

Nesta ficha, o core continua authority do skill framework; qualquer addon de progressão apenas decide como unlock/equip é apresentado/obtido.

## 11. Entity patches
Epic Fight transforma entidades suportadas em **patched entities** para aplicar armature, animation/combat state e AI/renderer correspondentes.

A API 1.21.1 documenta registro de:
1. entity patch;
2. armature type;
3. patched renderer no client.

Para casos simples também existem guides de custom entity datapack. Não duplicar patch da mesma entity type em dois addons sem precedence.

## 12. Animations e armatures
Animations são registradas pelo animation manager/registry e associadas a armatures. A branch 1.21.1 usa registration/lifecycle mais moderno e deferred do NeoForge em várias superfícies.

Assets de animação podem vir de Blender/export pipeline e resource packs. A armature é um contrato estrutural: bone/part mismatch pode produzir render quebrado sem necessariamente afetar o server combat state.

## 13. Patched renderers
Renderer patch é client-side mesmo quando entity patch/combat state é comum/server-aware. Custom armors podem aparecer invisíveis/deformados porque Epic Fight troca a player mesh/model pipeline; o upstream fornece guide de armor resource-pack compatibility.

Isso explica por que há vários armor/EMF/player-model bridges no pack.

## 14. Data-driven weapons/entities
O wiki oficial cobre:
- custom weapon/armor datapacks;
- weapon type/item capability configuration;
- custom entity datapacks;
- custom trails/resource packs.

Customização deve usar os schemas/documentação da branch 1.21.1. `/reload`/restart e migration precisam ser testados; presença no resource pack não prova que capability/entity patch carregou.

## 15. API / events
A branch 1.21.1 expõe packages de NeoForge events e um event system próprio para várias operações do framework. A documentação de developer integration cobre custom animations, entity patching, skill slots e utilities.

Addons devem declarar Epic Fight como dependência ou verificar presença antes de tocar classes. Optional integration sem guard pode causar classloading crash.

## 16. Input abstraction / controllers
A documentação atual introduz input actions e `InputManager` para abstrair keyboard/controller. Há distinção entre ações discretas e contínuas, e uma interface para integração de controller mods.

O próprio docs alerta que controller compatibility pode exigir tratamento adicional de camera/lock-on. Não hookar raw vanilla input se o objetivo é acompanhar semantics Epic Fight.

## 17. Combat mode + first person
O README 1.21.1 lista **First-person Model** como compatível. O pack possui `FirstPerson 2.7.2`, além de EFIS/EMF Compat e outras layers visuais.

Battle mode altera player model/pose; testar hands, held items, camera, lock-on e transitions. Uma limitação visual não deve ser confundida com ataque server-side inválido.

## 18. Stack Epic Fight local
A modlist física confirma, entre outros:
- Battle Arts API `21.17.7`;
- Epic-API `21.3.1`;
- Better Lock On `2.0.8-neoforge`;
- Epic Fight x Curios Compat release `2.2` / metadata `1.4`;
- EFIS `3.1.0` para Iron's Spells;
- Epic Fight Compat `1.1.0`;
- ParCool `4.0.0.3` + bridge Epic ParCool;
- Epic Colonies `21.0.8` (próxima ficha física, não auditada neste lote).

Cada addon deve consumir o core; nenhum deve manter seu próprio player patch paralelo.

## 19. Compatibilidade oficial
O README atual da branch 1.21.1 lista como plenamente suportados, entre outros:
- ParCool;
- Sodium;
- Iris;
- 3D Skin Layers;
- Shoulder Surfing Reloaded;
- playerAnimator;
- GeckoLib;
- AzureLib;
- First-person Model;
- KubeJS.

Também lista OptiFine e Controllable como incompatíveis. Compatibilidade upstream não elimina a necessidade de regressão na combinação específica do pack.

## 20. Werewolves / compat mixin
`Werewolves 2.0.3.3` está fisicamente presente. O JAR Epic Fight contém `epicfight-compat.werewolves.mixins.json`, mostrando uma superfície de compat código-side, mas a documentação/registro anterior do pack ainda aponta problema de render da forma de lobisomem.

Presença de mixin **não prova compatibilidade total**. Tratar transformação Werewolf em battle mode como regression gate explícito.

## 21. Client / Server
**Server/common authority:** damage settlement, stamina, skill equip/use, patched entity combat state, target/hit validation, item capability gameplay data.

**Client-facing:** mesh, animation interpolation, patched renderer, trails, camera/HUD/input presentation.

Packets precisam transportar intenção/state sem executar a mesma skill nos dois lados. Dedicated server não deve carregar patched renderer classes.

## 22. Lifecycle
Validar:
- login/logout;
- battle mode toggle;
- equip/unequip/troca de arma;
- dodge/guard/passive skill;
- special attack start/complete/cancel;
- death/respawn;
- dimension change;
- entity spawn/despawn/patch attach;
- datapack/resource reload;
- chunk unload/reload;
- server restart;
- controller/input-mode change;
- addon load/unload/update em instância de teste.

## 23. Multiplayer / idempotência
Damage, stun, stamina e skill effects precisam liquidar **exatamente uma vez**. Outros clients recebem animation/state e não devem reaplicar hit.

Target lock-on é apresentação/controle local; o servidor ainda valida alcance/target/damage. Dois players atacando o mesmo mob precisam produzir eventos independentes, sem static/global state incorreto.

## 24. Riscos
1. double-damage por integration hook;
2. double-stun/knockback;
3. stamina debit duplicado;
4. skill-slot name collision;
5. entity patch registrado duas vezes;
6. armature/renderer mismatch;
7. weapon capability datapack stale após update;
8. special attack animation terminar sem settlement ou vice-versa;
9. held/offhand restrictions divergirem de outro equipment mod;
10. armor/player model invisível/deformado;
11. input conflict com controller/first-person/lock-on;
12. addon compilado para outra linha Epic Fight;
13. optional integration classload sem guard;
14. Werewolves transform/render regression;
15. KubeJS/datapack aplicar segunda camada de damage/attributes;
16. battle mode toggle deixar pose/state residual.

## 25. Matriz de testes
1. Dedicated server boot com Epic Fight 21.17.3.1 e stack atual.
2. Battle mode on/off repetido, inclusive após relog.
3. Basic/dash/special attacks com arma vanilla adaptada.
4. Weapon capability de arma modded do pack.
5. Weight/Stun Armor/Impact/Armor Negation em cenário controlado.
6. Dodge e Guard com stamina baixa/alta.
7. Skill equip/unequip e persistence após restart.
8. Entity patch vanilla + entity modded patchado por addon/datapack.
9. Dois jogadores atacando o mesmo alvo; exatamente um settlement por hit.
10. Death/respawn durante skill/special attack.
11. Dimension change em battle mode.
12. FirstPerson + Better Lock On + EMF/ETF stack.
13. EFIS casting e cancelamento com Iron's Spells.
14. Curios equipment render durante attacks/dodge.
15. ParCool movement → battle transition.
16. Werewolves transform + battle mode.
17. Datapack reload de weapon/entity patch em mundo de teste.
18. Update smoke-test de qualquer addon Epic Fight antes de promover.

**Esta catalogação não afirma que esses testes foram executados.**

## 26. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/version/hash e stack de addons/compatibilities;
- GitHub oficial Epic Fight branch `1.21.1` / README: battle mode, inputs, attributes, special attacks, stun, stamina/skills e compatibility matrix;
- Epic Fight Wiki/docs: weapon/item capabilities, entity datapacks, armor/resource compatibility, custom animations, entity patches, skill slots e NeoForge events;
- API Input docs 1.21.1: input actions/controller abstraction;
- mixin configs do JAR físico: compat surfaces para SkinLayers3D, Werewolves, GeckoLib, Vampirism e outras.

> **Boundary canônico:** Epic Fight é a authority do **combat framework que adiciona**. Addons devem registrar/extender capabilities, patches, skills e animations sem criar outra fonte de verdade para damage, stamina ou player patch.
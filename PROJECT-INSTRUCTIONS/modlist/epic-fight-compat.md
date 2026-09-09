# Epic Fight Compat — 1.1.0

> **Runtime físico confirmado:** `epicfightcompat-1.1.0-mc1.21.1-neoforge.jar` · mod id `epicfightcompat` · versão `1.1.0` · NeoForge 1.21.1 · Client & Server.

## 1. Papel no modpack
Epic Fight Compat é um **compat pack modular e data-driven** que aplica patches de entidade e weapon capabilities do Epic Fight a conteúdo de outros mods quando existe analogia funcional segura.

## 2. Princípio de compatibilidade upstream
O source 1.21.1 explicita uma política conservadora: entidade semelhante a um mob vanilla já suportado pode reutilizar seu preset; criatura/boss exótico com animações próprias deve permanecer intacto. Para armas, só se atribui preset quando o archetype do Epic Fight é natural; staves/wands/exóticos podem permanecer no comportamento original/fallback.

## 3. Entity patches
Patches data-driven usam recursos `data/<target_modid>/epicfight_mobpatch/<entity>.json`. Um preset referencia um EntityType já suportado pelo Epic Fight. O compat também adiciona atributos Epic Fight às entidades externas via `EntityAttributeModificationEvent` quando necessário.

## 4. Weapon capabilities
Armas são mapeadas por `data/<modid>/capabilities/weapons/<item>.json` ou regexes de `item_keyword`. Presets oficiais incluem categorias como sword, axe, greatsword, longsword, dagger, spear, tachi, bow e crossbow. JSON explícito tem precedência sobre regex/fallback.

## 5. Activation modular
`CompatRegistry` só ativa um módulo quando `ModList` detecta o mod alvo. Falha de um módulo é capturada/logada para não impedir os demais. Os targets são optional; **Epic Fight é a dependência required**.

## 6. Módulos presentes no source 1.21.1
O registry atual lista: Variants & Ventures, Guard Villagers, Eternal Nether, Illager Invasion, It Takes a Pillage, Iron's Spellbooks, Simply Swords, Simply More, Mowzie's Mobs, Darker Depths, Quark, Block Factory's Bosses, Supplementaries, ReArm, Farmer's Delight, Hearth & Harvest e Mutant Monsters.

## 7. Subconjunto fisicamente ativo neste pack
A modlist confirma pelo menos estes targets presentes:
- Iron's Spells 'n Spellbooks `1.21.1-3.16.3`;
- Simply Swords `1.70.2-1.21.1`;
- Simply More `1.3.0_alpha`;
- Mowzie's Mobs `1.8.2`;
- Quark `4.1-483`;
- Supplementaries `1.21.1-3.9.8`;
- Farmer's Delight `1.3.4`.

Os demais módulos do registry não são tratados como ativos sem presença física correspondente.

## 8. Relação com bridges especializadas
EFIS, Epic Colonies, Curios Compat, Epic ParCool e outros patches dedicados podem tocar subsets mais específicos. O compat geral não deve ganhar precedence automaticamente quando uma bridge especializada preserva renderer/behavior que um preset vanilla destruiria.

## 9. Client / Server
**Server/common:** entity patch state, attributes, weapon capability e combat settlement.

**Client:** armature/renderer/animation derivados do patch.

Data packs precisam existir de forma coerente no servidor/pack; renderer nunca decide damage.

## 10. Lifecycle
Validar startup com targets ausentes/presentes, datapack reload, weapon capability reload, entity spawn, chunk reload, equip/troca de arma, battle mode toggle, death/respawn e updates de qualquer target.

## 11. Multiplayer / idempotência
O mesmo item/entity não pode receber dois mappings incompatíveis por compat packs diferentes. Damage/stun deve liquidar uma vez. Clients observadores apenas renderizam o patch sincronizado.

## 12. Riscos
1. target mod atualizar IDs/classes;
2. preset funcionalmente inadequado;
3. patch genérico sobrescrever animação especial de boss;
4. atributos Epic Fight faltarem numa entidade externa;
5. JSON de capability apontar item inexistente;
6. regex mapear arma errada;
7. bridge especializada disputar a mesma entidade/item;
8. logs de resources de targets ausentes;
9. datapack reload deixar capability stale;
10. versão 1.1.0 ficar atrás de mudanças do Epic Fight 21.17.3.1.

## 13. Matriz de testes
1. Boot dedicado com todos os targets físicos atuais.
2. Entidade de cada módulo ativo representativo.
3. Arma Simply Swords/Simply More de archetypes diferentes.
4. Iron's Spellbooks: arma compatível vs casting item que deve permanecer próprio.
5. Mowzie boss/exótico confirmando que não recebe patch genérico indevido.
6. Quark/Supplementaries/Farmer's Delight mappings relevantes.
7. `/reload` e reteste de capabilities.
8. Coexistência com EFIS e Epic Colonies.
9. Dois jogadores atacando a mesma entidade.
10. Smoke-test após update Epic Fight/target mod.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física canônica: JAR/version/hash e targets instalados;
- source oficial `payangar-dev/epicfight-mod-compat` branch 1.21.1: `CompatRegistry`, arquitetura modular e paths data-driven;
- documentação interna do projeto: política de presets, entity attributes, weapon capability precedence e failure isolation.

> **Boundary canônico:** Epic Fight Compat adapta **entidades/armas externas ao contrato Epic Fight**; ele não substitui os providers dos mods alvo nem deve forçar compat onde a analogia é ruim.
# Interações com outros mods

Esta página descreve somente interações sustentadas pelo estado atual do projeto. “Conteúdo definido” não é o mesmo que “adapter completo em runtime”.

## Epic Fight — integração confirmada

Perks Martial/Agility podem conceder stamina, regeneração de stamina e impact. O combate integrado evita tratar o mesmo golpe como Epic Fight e fallback vanilla ao mesmo tempo.

## Iron's Spells 'n Spellbooks — integração confirmada

- mastery `irons:casting` participa da classe Mage;
- perks usam max mana, mana regen, spell power, cooldown reduction, cast time reduction e poderes de escolas;
- existem especializações Blood, Eldritch, Ender, Evocation, Fire, Holy, Ice, Lightning e Nature;
- Warlock, Druid e Technomancer possuem efeitos que usam atributos do Iron's.

Um bônus em Fire Spell Power, por exemplo, é uma interação por atributo/escola. A wiki não afirma que cada spell de fogo tenha lógica nominal própria.

## Ars Nouveau — integração confirmada

- mastery `ars:casting` participa da classe Sorcerer;
- existem caminhos de Amplification, AoE, Control, Duration, Projectile e Summoning;
- há adapter runtime dedicado para ações do Ars.

## Malum — integração confirmada

Occult possui efeitos em Spirit Spoils, Arcane Resonance, Soul Ward Capacity e Geas Limit. Spirit harvesting/reaping confirmados também participam da progressão de mastery.

## Goety — integração confirmada

Eventos confirmados do Goety alimentam progressão associada a Soul Energy/ações ocultas. Isso não significa que qualquer interação trivial com um bloco ou item do mod conceda mastery.

## Eidolon: Repraised — integração confirmada

A conclusão válida de receitas do Crucible participa de mastery/discovery. Tentativas incompletas não devem ser interpretadas como conclusão.

## Identity2 / morphs — integração presente

Druid e Metamorph possuem permissões de forma ligadas à progressão. Druid representa formas naturais; Metamorph representa formas humanoides, monstruosas e aberrantes. Entidades técnicas/blacklisted permanecem fora dessas permissões.

## Apothic Attributes — compatibilidade por atributos

Várias perks usam atributos Apothic como dodge chance, armor pierce, mining speed, healing received, crit chance, life steal e overheal. Isso comprova compatibilidade de atributo, não uma integração completa com todo o ecossistema de bosses do Apothic.

## Create — conteúdo definido, integração de máquinas não confirmada

Existem especializações Create Kinetics, Automation, Artillery e Aeronautics, além de perks Tecnomago com nomes ligados a Create. Os efeitos numéricos auditados de `create_resonance` e `create_overdrive` alteram spell power/cooldown do Iron's. Não há base para afirmar que máquinas Create recebem esses bônus diretamente.

## Applied Energistics 2 — conteúdo definido

Existe AE2 Networks e gateway de Rede Arcana no Tecnomago. Um adapter completo de rede/crafting não está confirmado por esta revisão.

## Oritech — conteúdo definido

Existem Oritech Mining e Oritech Power, além do Portal de Potência na árvore de Tecnomago. O conteúdo é documentado como especialização existente; efeitos de eventos/máquinas só devem ser considerados quando confirmados no runtime.
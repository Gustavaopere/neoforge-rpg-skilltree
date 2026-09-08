# Ars Zero

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db816fb077c0e1d6c7bb62  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Ars Zero
- **Arquivo JAR:** `ars_zero-1.21.1-2.0.2.jar`
- **Versão 1.21.1:** `2.0.2`
- **Categoria:** Magia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ars-zero
- **Função:** Expande Ars Nouveau com Spell Staff/Psion's Circlet de cast contínuo multifásico, glyphs de contexto/geometria/voxel, staffs especializados e progressão Necropolis/Lich.
- **Dependências:** Ars Nouveau; Sable Companion 1.4.2 e Sauce 0.0.18.57 aparecem embarcados no JAR e não são top-level.
- **Compatibilidade/Riscos:** JAR físico é 2.0.2, mas source público disponível declara 2.0.0-beta3. Contratos arquiteturais e delta 2.0.2 estão confirmados; contagem binária exata adicional exige source/tag/JAR correspondente. Alto risco de double-cast/double-charge em begin/tick/end.
- **Sobreposição:** Complementa o ecossistema Ars; comparar conteúdo concreto com outros pacotes de glyphs sem assumir equivalência.
- **Observações:** Release 2.0.2 promove Staffs of Demonbane, Geometrize, Convergence, Lakes e Switcheroo para produção e drops do Necropolis Lich. Source anterior confirma 21 spell parts e arquitetura multifásica.
- **Procedência:** Modlist física atual + CurseForge release/changelog 2.0.2 + source zeroregard/Ars-Zero branch 1.21.1 usado somente onde suportado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou o runtime 2.0.2 e preservou a divergência entre a release física e o source público anterior beta3. O mod permanece tecnicamente catalogado sem transformar presença física em decisão de manter/remover.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — dossiê completo com separação explícita entre release-confirmed 2.0.2 e source-confirmed beta3; fail-closed mantido.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ⚠️ Versão física autoritativa: `ars_zero-1.21.1-2.0.2.jar`, mod id `ars_zero`, versão `2.0.2`. O branch público `1.21.1` atualmente acessível ainda declara `2.0.0-beta3`; por isso esta ficha separa explicitamente o que é confirmado pela release 2.0.2 do que é confirmado pelo source público anterior. Não há extrapolação silenciosa.

## 1. Papel e autoridade
Ars Zero é uma expansão de Ars Nouveau focada em **casting contínuo/multifásico**, staffs especializados, glyphs de contexto/forma/voxel e conteúdo de Necropolis/Lich. Ars Nouveau continua autoridade da mana do jogador, spell recipe/caster e Source. Ars Zero adiciona dispositivos e fases de cast; não deve existir uma terceira pool de mana nem cobrança duplicada por fase.

## 2. Spell Staff — contrato central
A release 2.x documenta um **Spell Staff** que executa três receitas independentes:
- **begin**: ao iniciar o canal;
- **tick**: durante canalização contínua;
- **end**: ao encerrar.

Cada fase suporta até **10 glyph slots**. Há tiers de staff Creative, Archmage, Mage e Novice. O dispositivo precisa preservar o mesmo caster/ownership durante toda a sequência e impedir que tick/reconnect/reload transforme uma única canalização em múltiplos casts concorrentes.

## 3. Psion's Circlet
Curio de cabeça Tier 3 que usa a mesma ideia begin/tick/end, com até 10 glyphs por fase, acionado por keybind de channel. O estado de canalização deve ser servidor-autoritativo; keybind é apenas input do cliente.

## 4. Spell parts confirmados pelo source público — 21
O source público anterior à 2.0.2 registra:
1. `TemporalContextForm`
2. `NearForm`
3. `ConjureVoxelEffect`
4. `SelectEffect`
5. `AnchorEffect`
6. `SustainEffect`
7. `PushEffect`
8. `EffectBeam`
9. `EffectConvergence`
10. `EffectGeometrize`
11. `DiscardEffect`
12. `AugmentHollow`
13. `AugmentSphere`
14. `AugmentCube`
15. `AugmentFlatten`
16. `EffectConjureBlight`
17. `AugmentAOETwo`
18. `AugmentAOEThree`
19. `AugmentAmplifyTwo`
20. `AugmentAmplifyThree`
21. `ZeroGravityEffect`

Glyphs de lifespan como Beam, Convergence, Geometrize e Conjure Voxel interagem com Anchor/Sustain para persistência/contexto. AOE II/III e Amplify II/III são extensões dos augments base e precisam respeitar o compatibility graph do Ars em vez de serem aplicados universalmente.

## 5. Conteúdo promovido especificamente na release 2.0.2
O changelog oficial da **2.0.2** confirma a promoção de cinco staffs antes development-only para produção:
- Staff of Demonbane
- Staff of Geometrize
- Staff of Convergence
- Staff of Lakes
- Staff of Switcheroo

A release também confirma:
- preset spells;
- visual tiers/cores;
- filial affinities;
- modelos e tooltips em inglês;
- Worn Notebook;
- inclusão dos cinco staffs na seleção de equipamento do Lich, filial crafting, protection upgrades, creative tab e spellcaster registration;
- os cinco staffs são **drops exclusivos do Necropolis Lich**, sem crafting próprio;
- staff equipado pelo Lich participa do drop garantido de equipamento, sujeito às regras normais de mob loot.

## 6. Staffs confirmados no source anterior
Além dos quatro tiers genéricos de Spell Staff, o source anterior confirma pelo menos:
- Staff of Telekinesis;
- Staff of Aetherwalk;
- e os cinco staffs acima já existiam no código, embora ainda marcados dev-only naquela revisão.

A promoção em 2.0.2 é portanto confirmada por release notes; não se usa o flag antigo como prova do estado final.

## 7. Itens, filials e progressão confirmados no source anterior
Superfícies conhecidas:
- dull circlet;
- archwood rod;
- bone chest;
- multiphase spell parchment;
- multiphase orb;
- blighted soil / frozen blight / blight archwood content;
- staff display;
- ossuary beacon;
- multiphase spell turret;
- filial items de Fire, Water, Air, Earth, Necromancy, Abjuration, Conjuration e Manipulation;
- Demonbane filament/filial e creative filial;
- Tattered/Rotted Arcanist gear e outros componentes de progressão de Necropolis conhecidos no source.

Como o source público não corresponde ao binário 2.0.2, esta seção é **inventário arquitetural confirmado**, não declaração de contagem binária exata da release física.

## 8. Voxels e geometria
O source confirma oito block-items/variantes de voxel spawner:
- arcane;
- fire;
- water;
- wind;
- stone;
- ice;
- lightning;
- blight.

Geometrize, Cube, Sphere, Flatten e Hollow formam um subsistema de construção/forma. Operações de alteração de mundo precisam ser servidor-autoritativas, respeitar proteção/eventos de bloco e evitar reexecução por tick do staff.

## 9. Entidades/Necropolis confirmadas no source anterior
Spawn eggs conhecidos:
- Acolyte;
- Necromancer;
- Lich;
- Bone Golem.

O Lich é particularmente relevante na 2.0.2 porque passa a ser provider dos cinco staffs promovidos. Loot/drops devem permanecer autoridade do mob/loot table do Ars Zero; não duplicar via reward global.

## 10. Mana, Source e causalidade
- Staff/Circlet do jogador: custo deve seguir a mana/caster do Ars e ocorrer conforme a implementação do dispositivo, sem cobrança duplicada por listeners externos.
- Turrets: versões anteriores documentam casos como Convergence drenando **Source Jar** em vez de mana do jogador. Source continua recurso Ars.
- Multiphase: begin/tick/end pertencem a **uma sessão de canalização**. Persistência/reconnect precisa encerrar ou retomar de forma definida, nunca clonar sessão.
- Projetis/entidades criados devem manter caster/origin para damage attribution, friendly-fire e progressão.

## 11. Dependências embarcadas
O JAR físico contém componentes embarcados como **Sable Companion 1.4.2** e **Sauce 0.0.18.57**. Eles não são entradas top-level separadas na modlist e não devem ser contados como mods independentes.

## 12. Riscos e testes obrigatórios
1. **Source drift:** branch público beta3 ≠ JAR 2.0.2; qualquer detalhe não confirmado pela release/source deve permanecer pendente até inspeção binária ou source/tag correspondente.
2. Staff continuous cast: begin/tick/end, cancelamento, release da tecla, logout, death, dimension change e server stop.
3. Mana/Source: exatamente uma cobrança por operação prevista; nenhum double-charge por tick externo.
4. Lich drops: exatamente um staff conforme regras da release; nada de reward duplicado.
5. Voxel/world edit: proteção, chunk border, unload e rollback de sessão.
6. Turrets: caster/source context, save/reload e deduplicação.
7. Dedicated server: Curios/client render/keybind isolados de código comum.

## 13. Evidência
- Modlist física atual: JAR 2.0.2.
- CurseForge release 2.0.2 e changelog oficial.
- Source público `zeroregard/Ars-Zero`, branch `1.21.1` (arquitetura e registries conhecidos, porém versão declarada 2.0.0-beta3).
- `ModGlyphs`, `ModStaffItems` e `ModItems` auditados.

> 🔒 Fail-closed: esta ficha é completa quanto às evidências disponíveis, mas não rotula o inventário do branch beta3 como inventário binário exato da 2.0.2. Uma futura inspeção do JAR/source tag pode ampliar a contagem sem invalidar os contratos aqui registrados.

# Vampiric Ageing

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db81f7bbb2f833be1b6e72
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `vampiricageing-1.21-1.4.21.jar`, mod id `vampiricageing`, runtime `1.21-1.4.21`; Vampirism `1.10.13` e Werewolves `2.0.3.3` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026” e contém revalidação física de 11/09. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Vampiric Ageing 1.4.21, Vampirism 1.10.13 e Werewolves 2.0.3.3 estão confirmados. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Vampiric Ageing
- **Arquivo JAR:** `vampiricageing-1.21-1.4.21.jar`
- **Versão 1.21.1:** 1.21-1.4.21
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Categoria:** Magia, RPG
- **Função:** Addon altamente configurável de progressão tardia para Vampirism. Introduz Age Ranks para Vampires e Hunters e, quando Werewolves está instalado, também para lobisomens. Os ranks podem evoluir por métodos como tempo, drenagem de sangue, infecção ou caça e ampliam vida, dano, mobilidade, regeneração e ações especiais conforme a facção, podendo também intensificar vulnerabilidades. Vampires chegam a rank 5 e podem acessar mecânicas como Celerity, waterwalking e blood-drain em ataques; Hunters recebem bônus contra facções inimigas e a linha opcional de Tainted Blood; Werewolves ganham melhoria de bite, duração de forma, summons e alimentação.
- **Dependências:** Vampirism 1.10.13; Werewolves 2.0.3.3 opcional upstream e presente; NeoForge 1.21.1 / Java 21.
- **Sobreposição:** Camada endgame sobre Vampirism e, quando presente, Werewolves. Reutiliza ACTION/SKILL/OIL registries e altera BloodDrink/Bite/Form/Howl/faction/death/movement por events/mixins; não deve ser duplicada por Black Arcana.
- **Compatibilidade/Riscos:** Source pin exato 16049e9aeadc47b2307995901c373521cef5fd76. Runtime QA pendente com Vampirism 1.10.13 + Werewolves 2.0.3.3, especialmente Celerity modifier, cooldown units, Wise Eye duration, Limited Bat Mode dimension gate, Hunter Tainted lifecycle e dedicated-server paths.
- **Observações:** Catálogo source-level preservado: 3 Age Types, 8 methods, 9 actions, 10 skills, Tainted acquisition e mixin surface. `AgeingManager`/HunterState são authorities; não reconstruir Age/Tainted em state paralelo.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source pin `TheDrOfDoctoring/Vampiric-Ageing@16049e9aeadc47b2307995901c373521cef5fd76` + CurseForge oficial 1.4.21. Changelog oficial atual registra métodos alternativos de ageing para Werewolf, fix de hunter trade multiplier e remoção do limite de hunt worth em config; runtime QA permanece pendente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vampiric-ageing-a-vampirism-addon
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Vampiric Ageing 1.4.21 permanece exatamente instalado; catálogo source-pinned preservado. Release 1.21.1 continua 1.4.21; runtime QA continua pendente.
- **Histórico da decisão:** 07/09/2026: catálogo granular source-level 1.4.21 concluído na PR #74. Confirmados 3 Age Types, 8 methods, 9 actions, 10 skills, 4 items, tainted_blood_effect, seniority_oil, aquisição survival Tainted e mixin surface. Runtime QA permanece pendente; preservar autoridade provider-native.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico confirmado em 08/09/2026: `vampiricageing-1.21-1.4.21.jar`, mod id `vampiricageing`, versão `1.21-1.4.21`. A página abaixo já possui auditoria **source-pinned** da build exata; este fechamento preserva integralmente esse catálogo. `AgeingManager`/states provider-native continuam autoridades únicas de Age/Tainted. **Runtime QA permanece pendente.**

## Identidade e contexto atual
- **Release:** 1.4.21 para NeoForge 1.21.1, canal Release.
- **Provider base instalado:** Vampirism 1.10.13.
- **Integração opcional instalada:** Werewolves 2.0.3.3.
- **Source pin auditado:** `TheDrOfDoctoring/Vampiric-Ageing@16049e9aeadc47b2307995901c373521cef5fd76`.
- **Estado de engenharia:** catálogo granular source-level concluído; dedicated-server/runtime QA ainda não executado.

## Boundary operacional
Age Rank, Age Method, Hunter Tainted, actions/skills e seus modifiers pertencem ao addon. RPG Skill Tree/Black Arcana não devem reconstruir rank, cumulative Tainted, Blood Tap, Werewolf Bite/Howl/Form ou cooldowns em state paralelo. Integração sem hook comprovado deve permanecer fail-closed.

## Auditoria source-level — 1.21-1.4.21
Source exato: `TheDrOfDoctoring/Vampiric-Ageing@16049e9aeadc47b2307995901c373521cef5fd76`. O `gradle.properties` desse commit declara `mod_version=1.21-1.4.21`, correspondente ao JAR instalado.

### Superfície provider-native
- **Age Types:** `VAMPIRE`, `HUNTER`, `WEREWOLF` — Werewolf é condicional ao mod Werewolves, que está instalado no pack.
- **Age Methods registrados no pack:** `DRAINING`, `BITING`, `TIME`, `V_HUNTING`, `HUNTING`, `DEVOUR`, `W_HUNTING`, `W_MIXED`.
- **Actions:** 9/9 com Werewolves instalado.
- **Skills:** 10/10 com Werewolves instalado.
- **Estado:** `AgeingManager` attachment persistente/sincronizado com tipo, método, Age Rank, rank progress e `TypeState` específico.

### Progressão default
- Vampire `DRAINING`: 150 / 300 / 600 / 900 / 1250 sangue drenado.
- Vampire `BITING`: 30 / 45 / 70 / 100 / 200 entidades infectadas.
- Vampire `TIME`: 72.000 / 144.000 / 288.000 / 576.000 / 1.152.000 ticks.
- Vampire `V_HUNTING`: 20 / 40 / 80 / 160 / 250 pontos, targets provider-tagged.
- Hunter `HUNTING`: 20 / 40 / 80 / 160 / 250 pontos, targets provider-tagged.
- Werewolf `DEVOUR` default: 30 / 60 / 100 / 250 / 500 pontos e exige kill por Werewolves BITE para pontuar.
- Werewolf `W_HUNTING` e `W_MIXED`: 20 / 40 / 80 / 160 / 250 pontos.

### Actions no pack
- Vampire: `drain_blood_action`, `celerity_action`, `water_walking_action`, `step_assist_action`.
- Hunter: `hunter_teleport_action`, `limited_hunter_batmode_action`, `step_assist_hunter_action`, `hunter_wise_eye_action`.
- Werewolf: `improved_senses_action`.

### Gates default relevantes
- Vampire: Celerity Age 1; Step Assist Age 2; Blood Tap Age 3; Water Walking Age 4.
- Hunter: Tainted Blood base Age 2; Step Assist Age 4; Wise Eye Age 5; Teleport cumulative Tainted Age 8; Limited Bat Mode cumulative Tainted Age 10; underwater breathing cumulative 11.
- Werewolf: Improved Senses Age 5 e, por default, também requer a skill `SENSE` do Werewolves.

### Autoridade Hunter Tainted
`CapabilityHelper.getCumulativeTaintedAge` é a derivação canônica. Sem bônus Tainted ativo nem transformação permanente, retorna 0; transformação permanente usa bônus fixo +6 sobre o base Age. Não reconstruir isso em tags/scoreboards próprios.

### Achados estáticos para QA
- Wise Eye possui `wiseEyeDuration=120`, mas `HunterEyeAction.getDuration()` usa `stepAssistDuration`.
- Step Assist Vampire/Hunter retornam cooldown config diretamente, sem conversão `*20` observada em outras Actions.
- Wise Eye / Improved Senses também retornam cooldown direto enquanto as durações são convertidas para ticks.
- Celerity usa `1.025` diretamente como valor de `ADD_MULTIPLIED_TOTAL`; velocidade efetiva precisa de medição runtime.
- Limited Bat Mode tem divergência entre o gate inicial de dimensão e o `onUpdate` (`OR` efetivo no gate vs `AND` no branch auditado).
- `WerewolfAgeingType.minFactionRank()` usa `CommonConfig.levelToBeginAgeMechanic`, não o config específico de Werewolf; defaults coincidem em 14.
- Blood Tap liquida sangue no hook provider-native de dano/bite; integração externa não pode duplicar o payout.

### Estado de validação
**Source-level audit concluída. Runtime QA ainda pendente** para a combinação instalada `Vampirism 1.10.13` + `Vampiric Ageing 1.4.21` + `Werewolves 2.0.3.3`, especialmente dedicated server, movement/flight, timings e lifecycle de attachment/faction/death.

### Aquisição survival Tainted — fechamento
- O item canônico é `vampiricageing:tainted_blood_bottle`; o `minecraft:damage` do stack carrega o bônus Tainted, não deve ser interpretado apenas como desgaste visual.
- O source contém cinco receitas `tainted_blood_bottle_1`…`tainted_blood_bottle_5` na Alchemical Table do Vampirism, todas gateadas por `vampiricageing:tainted_blood_skill`.
- Rank 1: Vampire Blood Bottle + `vampirism:pure_blood_0` → bottle com `minecraft:damage=1`.
- Rank 5: Vampire Blood Bottle + `vampirism:pure_blood_4` → bottle com `minecraft:damage=5`.
- `tainted_concentrate`: bottle rank 5 + `vampirism:pure_blood_4` na Alchemical Table, mantendo o mesmo skill gate.
- `tainted_elixir`: receita shaped com oito `vampiricageing:tainted_concentrate` ao redor de um `vampirism:mother_core`; uso válido exige Hunter Age 5 e grava a transformação no `HunterState`.
- MobEffect exato auditado: `vampiricageing:tainted_blood_effect`. O efeito visual/temporal não substitui o state persistido.

### Contrato de integração consolidado
- `AgeingManager` e `HunterState` permanecem autoridades únicas para Age/Tainted.
- Blood Tap liquida pelo pipeline real do Vampirism e pode produzir o `BloodDrinkEvent` que alimenta o método `DRAINING`; tratar o fluxo como uma única cadeia causal.
- Werewolf Bite, Howl, Form e Sense continuam provider-native do Werewolves; Ageing só os estende.
- Teleport/Bat Mode/Wise Eye/Improved Senses permanecem fail-closed para integrações de runtime até dedicated-server QA.
- **Estado final desta auditoria:** `SOURCE-PINNED / GRANULAR SOURCE CATALOG COMPLETE / RUNTIME QA PENDING`.

## Revalidação física — 11/09/2026
A modlist física continua contendo `vampiricageing-1.21-1.4.21.jar`, mod id `vampiricageing`, versão `1.21-1.4.21`. A file list oficial mantém 1.4.21 como release 1.21.1 pertinente.

O catálogo source-pinned existente foi preservado. O changelog oficial atual da 1.4.21 registra dois métodos alternativos de ageing para Werewolves, correção do hunter trade multiplier e remoção do limite de hunt worth no config. Isto não substitui os achados estáticos já documentados. Nenhum dedicated-server, Celerity, Wise Eye, cooldown, Bat Mode, Hunter Tainted ou lifecycle de attachment/faction/death foi testado nesta recatalogação.

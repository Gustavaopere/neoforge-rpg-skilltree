# Ars Technica

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81519809da73701c20b0
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Technica
- **Arquivo JAR:** `ars_technica-1.21.1-2.7.6.jar`
- **Versão 1.21.1:** 2.7.6
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, Tecnologia, Compat
- **Função:** Integra Ars Nouveau e Create com technomancy: 11 spell parts, Source Motor, relay/turret, armaduras/perk de pressão e bridges de automação.
- **Dependências:** Ars Nouveau 5.13.1 + Create 6.0.10 físicos; Curios 9.5.1 em superfícies de equipamento. Sauce 0.0.16.46 é embedded/jarjar, não top-level.
- **Sobreposição:** Ars Creo é bridge de integração; Ars Technica acrescenta glyphs, equipamento e Source Motor próprios.
- **Compatibilidade/Riscos:** Source Ars e rede cinética Create devem manter authorities separadas. Build 2.7.6 foi feito contra Ars 5.11.0.1267/Create 6.0.8; pack usa Ars 5.13.1/Create 6.0.10. Exige QA de motor, pressure/backtank, turret e Schematicannon sem double-processing.
- **Observações:** Inventário source-confirmed: 11 spell parts; 3 blocks/3 BEs; 8 misc entities; 12 peças de armor; 1 PressurePerk. Sauce embarcado não é mod top-level.
- **Procedência:** modlist.txt física atual de 11/09/2026 com 595 top-levels + source oficial zeroregard/Ars-Technica branch 1.21.X, versão 2.7.6 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `ars_technica-1.21.1-2.7.6.jar` / `2.7.6`; compatibilidade com Ars Nouveau 5.13.1/Create 6.0.10 continua sujeita ao QA já documentado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ars-technica
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #50: `ars_technica-1.21.1-2.7.6.jar` / `2.7.6` conferidos contra a modlist atual; drift de build-target Ars 5.11/Create 6.0.8 → runtime Ars 5.13.1/Create 6.0.10 preservado, com runtime QA obrigatório.
- **Histórico da decisão:** Permaneceu no pack após a revisão dos addons Ars. Confirmado carregado em 22/08/2026 na versão 2.7.6. Deve continuar sendo cruzado com Ars Creo e demais integrações Create apenas para detectar sobreposição real, não por semelhança temática.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `ars_technica-1.21.1-2.7.6.jar` / mod id `ars_technica` / versão `2.7.6`. Source público da branch `1.21.X` também declara `2.7.6`. O addon foi compilado contra Ars Nouveau 5.11.0.1267 e Create 6.0.8; o pack usa Ars Nouveau 5.13.1 e Create 6.0.10, portanto manter QA de integração.

## 1. Papel e autoridade
Ars Technica é uma ponte Ars Nouveau ↔ Create com conteúdo próprio de technomancy. Ars Nouveau continua autoridade de **Source, mana, spell casting e perks**; Create continua autoridade de **stress, speed, kinetic network e contraptions**. Ars Technica converte/expõe esses sistemas sem criar uma terceira economia paralela.

## 2. Spell parts registrados — 11
- `EffectCarve`
- `EffectPack`
- `EffectPolish`
- `EffectObliterate`
- `EffectPress`
- `AugmentSuperheat`
- `EffectFuse`
- `EffectWhirl`
- `EffectInsert`
- `EffectTelefeast`
- `EffectApply`

Esses parts devem ser tratados como extensão do pipeline de spells do Ars. Mana, caster, alvo e causalidade pertencem ao cast original; integrações externas não devem reaplicar custo ou duplicar efeito.

## 3. Blocos e block entities
### Source Motor
`source_motor` / `source_motor_block_entity` é um `GeneratingKineticBlockEntity` do Create. O código limita velocidade absoluta a 256, aceita velocidade positiva/negativa, verifica redstone e consome Source periodicamente. O custo deriva de velocidade, multiplicador Source↔speed e capacidade de stress; o motor para quando não há Source suficiente ou quando recebe sinal de redstone. Persiste estado de fuel, sinal e razão de stress.

**Contrato:** Source é retirado pela infraestrutura Ars; geração cinética entra na rede Create. Não contabilizar Source ou stress novamente em bridges próprias.

### Precise Relay
`precise_relay` / `precise_relay_tile` adiciona uma superfície especializada de relay à infraestrutura Ars. Qualquer extensão deve preservar ownership do SourceManager e invalidação/reload de block entity.

### Transmutation Turret
`transmutation_turret` / `transmutation_turret_block_entity` integra automação de transmutação/casting. O turret deve manter caster/contexto e evitar replay de operação após chunk unload/reload.

## 4. Itens e equipamentos confirmados
### Armaduras — 12 peças / 3 conjuntos
**Technomancer:** helmet, chestplate, leggings, boots.
**Artificer / Light Technomancer:** cap, tunic, pants, shoes.
**Machinaguard / Heavy Technomancer:** helmet, chestplate, leggings, boots.

As 12 peças são registradas como Ars armor perk providers e implementam integração com mana/perks. Elementos visuais também participam do ecossistema Create goggles quando aplicável.

### Utilidades e componentes
- `calibrated_precision_mechanism`
- `transmutation_focus`
- `spy_monocle`
- `giant_experience_gem`
- `gargantuan_experience_gem`
- `pocket_factory`
- `mark_of_technomancy`
- `blank_disc`
- block items de Source Motor/Precise Relay/Transmutation Turret
- migração do antigo runic spanner para a semântica de wrench do Create; não assumir um segundo wrench canônico.

## 5. Perk e pressão
Há **1 perk Ars confirmado: `PressurePerk`**. O sistema representa ar de ultra-alta pressão, com capacidade e refill escaláveis por nível. Mixins de integração com backtank do Create permitem que esse estado participe da semântica de ar/pressão.

Risco principal: dois providers tentando consumir/recarregar a mesma reserva. A autoridade deve permanecer no estado/perk do Ars Technica e na integração comprovada com o backtank, não em bônus genérico externo.

## 6. Entidades registradas
### Entidades misc — 8
- `arcane_polish_entity`
- `arcane_hammer_entity`
- `arcane_press_entity`
- `arcane_compact_entity`
- `arcane_pack_entity`
- `arcane_fusion_entity`
- `arcane_whirl_entity`
- `item_projectile_entity`

### Block entities — 3
- `source_motor_block_entity`
- `precise_relay_tile`
- `transmutation_turret_block_entity`

## 7. Integrações relevantes no pack
- Ars Nouveau 5.13.1: provider de magia/Source/perks.
- Create 6.0.10: provider cinético.
- Curios 9.5.1: superfície de equipamentos/curios quando usada pelo addon.
- Sauce 0.0.16.46 aparece embarcado no JAR e **não conta como mod top-level**.
- O código contém integração de Schematicannon: equipamento/tag de technomancy pode alterar velocidade do cannon conforme configuração. Create continua autoridade do cannon.

## 8. Client/server, lifecycle e multiplayer
- Consumo de Source, mudança de speed/stress e execução de turret são decisões de servidor.
- Render/goggles/monocle são apresentação client-side; não podem ser autoridade de estado.
- Chunk unload/reload deve preservar BE state sem gerar Source, stress ou operações duplicadas.
- Mudança de dimensão/logout/death não pode deixar perk/Curios/backtank com estado órfão.
- Em multiplayer, duas fontes de automação no mesmo alvo precisam ser idempotentes quanto a itens/Source.

## 9. Riscos e regressões obrigatórias
1. **Version drift:** source build Ars 5.11/Create 6.0.8 vs pack Ars 5.13.1/Create 6.0.10.
2. **Source Motor:** testar falta de Source, redstone, velocidades ±256, save/reload e reconnect da rede cinética.
3. **Pressure/backtank:** testar equip/unequip, death, dimension change e refill sem duplicação.
4. **Turret/transmutation:** testar exatamente-uma execução por trigger e por item.
5. **Schematicannon:** garantir que aceleração não duplica tick/consumo e respeita config.
6. **Dedicated server:** nenhuma classe exclusivamente client deve vazar para inicialização comum.

## 10. Evidência
- Modlist física atual de 08/09/2026: **595 entradas top-level**, incluindo `ars_technica-1.21.1-2.7.6.jar`; JAR/version/mod id reconciliados.
- Source oficial `zeroregard/Ars-Technica`, branch `1.21.X`, `gradle.properties` 2.7.6.
- `GlyphRegistry`, `BlockRegistry`, `ItemRegistry`, `EntityRegistry`, `ArsNouveauRegistry` e `SourceMotorBlockEntity` auditados.

> ⚠️ Status técnico: conteúdo e contratos principais catalogados de forma exaustiva para a superfície registrada confirmada. A compatibilidade binária com as versões mais novas de Ars/Create do pack deve continuar sendo validada em runtime; não foi inferida apenas pelo range de dependência.

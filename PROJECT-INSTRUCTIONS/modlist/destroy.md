# Destroy — 0.4.3

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db814e9d4cd93cbd201599  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Destroy
- **Arquivo JAR:** `destroy-1.21.1-0.4.3.jar`
- **Versão 1.21.1:** `0.4.3`
- **Categoria:** Tecnologia; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/NHblock714/Destroy/tree/1.21.1-neo
- **Função:** Grande addon de Create focado em química: elementos/moléculas/reactions, mixtures, Vat, processamento industrial, pollution, hazards, explosives, containers químicos, oil e compatibilidades.
- **Dependências:** Source 0.4.3: Minecraft 1.21.1, NeoForge >=21.1.200, Create 6.0.10, petrolpark library 1.5.0 no `gradle.properties` atual, Registrate MC1.21-1.3.0+67 e Flywheel 1.0.5; Ponder/Catnip via Create. JEI/Curios/CBC/Create Connected/Farmer's Delight são superfícies opcionais conforme source.
- **Compatibilidade/Riscos:** State químico complexo: risco de dupe/perda de moles/volume, stale chemistry sync, duplicate reactions após reload, client classloading em dedicated server, floating-point temperature drift, BE/contraption lifecycle, CBC payload loss e pollution double-processing.
- **Sobreposição:** Cruza Create, CBC, Curios, Connected, Farmer's Delight e sistemas ambientais apenas via integrações explícitas. Destroy permanece authority de chemistry/mixtures/pollution; Create permanece authority de kinetics/contraptions.
- **Observações:** Metadata antiga 0.4.1 foi corrigida para 0.4.3. Chemistry data-driven usa elements→molecules→reactions com sync S2C/rejoin. Há divergência documental: README ainda cita petrolpark lib 1.4.31+, enquanto `gradle.properties` do source 0.4.3 fixa 1.5.0; build file é referência mais específica.
- **Procedência:** modlist(4).txt — fonte física canônica atual, 595 mods top-level; JAR `destroy-1.21.1-0.4.3.jar`, mod id `destroy`, runtime `0.4.3`, hash físico `060b4503283c768ab0433dcf6147b7ae4d8ee4a1`. Source pin: `NHblock714/Destroy`, branch `1.21.1-neo`, com `gradle.properties` declarando `mod_version=0.4.3`; README/PORT_SUMMARY do mesmo branch usados para subsistemas, compat e migrations. Upstream Petrolpark é origem histórica 1.20.1, não o binário atual.
- **Histórico da decisão:** Ficha reconstruída em 08/09/2026 e metadata reconciliada de 0.4.1 para o runtime físico 0.4.3, com source branch da mesma versão.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — runtime/source 0.4.3 pinados; chemistry/mixtures/Vat/pollution/processing/compat, data sync, lifecycle, risks e matriz de testes catalogados.
- **Data da última decisão:** 2026-09-08

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `destroy-1.21.1-0.4.3.jar` · mod id `destroy` · versão `0.4.3` · NeoForge 1.21.1 · Java 21. O source oficial do port `NHblock714/Destroy`, branch `1.21.1-neo`, declara exatamente `mod_version = 0.4.3`.

## 1. Identidade e proveniência
- **Mod:** Destroy — port comunitário autorizado de Petrolpark/Destroy para NeoForge 1.21.1.
- **JAR:** `destroy-1.21.1-0.4.3.jar`.
- **Mod id:** `destroy`.
- **Versão:** `0.4.3`.
- **Source pin:** `NHblock714/Destroy`, branch `1.21.1-neo`; `gradle.properties` corresponde ao runtime 0.4.3.
- **Descrição do source:** “Chemistry and Carnage”.

## 2. Papel no modpack
Destroy é um grande addon de **Create** centrado em química, mistura de fluidos, reações, processamento industrial, poluição, explosivos e equipamentos/containers químicos. Não é apenas um pacote de recipes: possui state químico persistente, block entities, networking e compatibilidades próprias.

## 3. Authority / ownership
- **Destroy** é authority de seus elementos, moléculas, reações, Mixture fluids, Vat, chemistry hazards, pollution types, equipamentos e explosivos próprios.
- **Create** permanece authority de kinetics, stress, basins, pipes/contraptions e framework-base que Destroy estende.
- **Create Big Cannons**, **Create: Connected**, **Curios** e **Farmer's Delight** permanecem authority de seus próprios contratos; Destroy só adapta conteúdo nas bridges específicas.

Mods próprios não devem recalcular reações, temperatura, concentração, pollution ou moles em handlers paralelos.

## 4. Dependências exatas do branch 0.4.3
`gradle.properties` do branch pinado declara:
- Minecraft `1.21.1`;
- NeoForge `21.1.200+`;
- **Create `6.0.10`**;
- **petrolpark library `1.5.0`** no build atual;
- Ponder/Catnip via Create;
- Registrate `MC1.21-1.3.0+67`;
- Flywheel `1.0.5`.

O README ainda menciona petrolpark library `1.4.31+` / `1.4.32+` como requirement histórico do port; para o source 0.4.3, **`gradle.properties` é a referência de build mais específica** e a divergência fica registrada.

## 5. Subsistemas portados/confirmados
O source/port summary organiza o runtime em grandes áreas:
- **chemistry** — moléculas, mixtures, reactions, periodic table, functional groups e generic reactions;
- **Vat multiblock** — controller/sides, UV, colourimeter, monitor e pollutometer;
- **processamento:** ageing barrel, centrifuge, cooler, distillation tower, dynamo/arc furnace, extrusion, glassblowing, mechanical sieve, tree tap e trypolithography;
- **oil:** pumpjack, seismograph, surveys e oil deposits;
- **produtos/effects:** alcohol, hangover/inebriation, fireworks, fireproofing;
- **chemical storage:** test tube, beaker, flask, jar, measuring cylinder e mixture-aware tanks;
- **hazards:** lacrimator/chemical poison, Crying effect/particles e dano químico;
- **pollution:** attachment types, smog/rendering, outdoor temperature e biome interaction;
- **explosives:** SmartExplosion, bombs e mixed explosives;
- **player/inventory:** extended inventory e bladder/urination mechanic;
- **redstone/programming:** Redstone Programmer e circuit-pattern pipeline.

## 6. Registries técnicos
O port summary confirma registries top-level para blocks, items, fluids, entity types, block entity types, mob effects, potions, armor materials, damage types, **attachment types**, **data components**, packets, pollution types, tags, recipe types, creative tabs, cauldron interactions, compostables, villagers, village additions e trades.

A migração para NeoForge 1.21 usa attachments/data components em superfícies que no 1.20.1 dependiam de capabilities/NBT legado.

## 7. Química data-driven — 0.4.3
A linha atual introduz chemistry registrável por datapack:
- `data/<ns>/destroy/elements/<id>.json`;
- `data/<ns>/destroy/molecules/<id>.json`;
- `data/<ns>/destroy/reactions/<id>.json`.

A ordem de load é **elements → molecules → reactions**. Há sync S2C no reload e resync per-player via datapack sync. Assets de átomos 3D podem ser fornecidos em resource pack.

Isso torna `/reload`, login e mismatch client/server superfícies críticas: um cliente não deve operar com catálogo químico stale.

## 8. Mixtures, temperatura e conservation
Destroy trabalha com fluid stacks que carregam composição/temperatura. O port 0.4.3 inclui correções para:
- merge de mixtures em containers de item;
- quantização de temperatura de distillation em 0.1 K para permitir stacking cross-mod;
- conservação de volume/moles em gas extraction;
- condensate sub-mB;
- persistência de aquecimento do Vat;
- sim-then-execute ao transferir de flask/beaker/test tube, evitando perda de fluido.

**Regra de integração:** conservação de massa/moles/volume e temperature state devem ser respeitados; não converter Mixture para “fluido simples” sem bridge explícita.

## 9. Vat e chemistry processing
O Vat é um stateful multiblock central. A linha atual restaura inventário de 9 slots e corrige lifecycle/reload/temperature issues. O state pode envolver composição, reação, catalisadores, UV, heat exchange e outputs.

Riscos: double-reaction, output duplicado, stale tank state, block entity unload e reprocessamento após reload.

## 10. Pollution e ambiente
Pollution usa attachments próprios e pode influenciar smog/temperature/biome behavior. O port também possui Pollutometer e Display Link integration.

Outros mods ambientais do pack não devem ser tratados como mesma authority. Qualquer bridge deve ler/adaptar pollution em vez de reproduzir seu cálculo.

## 11. Compatibilidades confirmadas
O source 0.4.3 documenta bridges opcionais para:
- **JEI** — opcional, incluindo molecule/reaction reverse lookup;
- **EMI** — plugin nativo na linha atual;
- **Curios** — bindings de gas mask/lab coat;
- **Create Big Cannons** — mixed explosive munitions/propellant behavior;
- **Create: Connected** — FluidVessel mixture-aware integration;
- **Farmer's Delight** — tag compatibility;
- **Sable** — Tree Tap recebeu correções específicas de compatibilidade.

Integração concreta não significa que esses mods sejam hard dependencies.

## 12. Client / Server e networking
Gameplay químico, reactions, inventories, pollution e explosion state precisam ser server-authoritative. O port possui packets próprios e sync do catálogo chemistry.

A migração 1.21 corrigiu vários dedicated-server crashes provocados por referências `net.minecraft.client.*` carregadas no lado errado. Classes de render/JEI/particles são especialmente sensíveis a dist-cleaning.

## 13. Lifecycle
Validar obrigatoriamente:
- dedicated server boot;
- first join + chemistry sync;
- `/reload` com/sem Vat carregado;
- datapack adding/removing element/molecule/reaction;
- player reconnect;
- dimension transition;
- chunk unload/reload com Vat, distillation, tanks e pollution state;
- server restart;
- contraption capture de block entities compatíveis;
- config reload;
- recipe-browser reload.

## 14. Multiplayer / idempotência
Dois jogadores usando o mesmo sistema químico não podem executar a mesma reaction twice, duplicar fluid, inventário ou explosion payload. Packets client-bound não podem ser reinterpretados como authority para mutation server-side.

## 15. Riscos críticos
1. perda/dupe de moles ou volume em transfers;
2. duplicate reaction/recipe registration após reload;
3. chemistry catalog stale no cliente;
4. client classloading em dedicated server;
5. mixture stack incompatibility por floating-point temperature drift;
6. CBC payload/fuze/data-component loss;
7. Sable/Create contraption lifecycle quebrando BE state;
8. pollution double-processing com outro sistema ambiental;
9. optional compat classloading sem mod presente;
10. version drift entre Create, petrolpark lib e branch do port.

## 16. Matriz de testes
1. Dedicated server boot com stack atual.
2. Client join → catálogo elements/molecules/reactions sincronizado.
3. `/reload` sem Vat e com Vat ativo.
4. Datapack custom element → molecule → reaction e validação de load order.
5. Transferir mixtures entre Vat, pipes e containers sem perda/dupe.
6. Aquecer/resfriar, reiniciar e confirmar temperature persistence.
7. Distillation outputs empilhando corretamente.
8. Chunk unload/reload de Vat e pollution state.
9. Multiplayer concorrente no mesmo processing chain.
10. Create: Connected FluidVessel com mixtures.
11. CBC mixed explosive hand-load/fire/save-reload.
12. Curios gas-mask/lab-coat equip/unequip.
13. Tree Tap com Sable stop/resume.
14. JEI/EMI reload sem duplicate recipe entries.

**Matriz futura; nenhum teste acima foi declarado como executado nesta catalogação.**

## 17. Evidências
- modlist física canônica de 08/09/2026: runtime `0.4.3`;
- source oficial do port `NHblock714/Destroy`, branch `1.21.1-neo`, `gradle.properties` declarando `mod_version=0.4.3`;
- README/PORT_SUMMARY do mesmo branch para dependencies, subsistemas, migrations, fixes e compatibilidade;
- upstream Petrolpark como origem funcional histórica, sem confundir 1.20.1 com o binário NeoForge atual.

> **Boundary canônico:** Destroy é authority de chemistry/mixture/reaction/pollution state. Create fornece a infraestrutura cinética; bridges devem adaptar providers, nunca duplicar seus pipelines.

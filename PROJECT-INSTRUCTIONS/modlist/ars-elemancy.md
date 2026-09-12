# Ars Elemancy

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8132bccacdbc9f1c5030
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Elemancy
- **Arquivo JAR:** `ars_elemancy-1.21.1-1.18.3.jar`
- **Versão 1.21.1:** 1.18.3
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG
- **Função:** Expansão de Ars Elemental com 6 escolas híbridas (Tempest/Cinder/Silt/Mire/Vapor/Lava) + Elemancer, 7 foci, 7 essences, 7 bangles, 21 armor sets e recipe `armor_upgrade`.
- **Dependências:** Ars Nouveau 5.13.1 + Ars Elemental 0.7.10.1. Sauce permanece jarjar/embedded e não é top-level.
- **Sobreposição:** Sobrepõe conceitualmente especializações elementais do Ars Elemental, mas por design as combina. Não duplicar bônus das escolas-base e híbridas em bridges próprias.
- **Compatibilidade/Riscos:** Risco central é double-dip de school power/perks em spells híbridos, especialmente quando bônus das duas escolas-base e da escola combinada coexistem. Também validar armor perk holders, Curios e tags elementais de dano após updates de Ars Elemental.
- **Observações:** mod id `ars_elemancy`. Registry 1.18.3 confirma 6 SpellSchools híbridas; conteúdo principal: 7 foci + 7 essences + 7 bangles + 21 armor sets; `armor_upgrade` tem RecipeType/Serializer próprios.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source oficial Lyrellion/Ars-Elemancy 1.18.3 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `ars_elemancy-1.21.1-1.18.3.jar` / `1.18.3`; sem divergência física.
- **Fonte:** https://github.com/Lyrellion/Ars-Elemancy
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #43: `ars_elemancy-1.21.1-1.18.3.jar` / `1.18.3` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:** Manter. Ficha reconstruída em 07/09/2026 contra registry 1.18.3 e modlist física; addon é progressão híbrida real sobre Ars Elemental, não um sistema mágico paralelo.
- **Data da última decisão:** 2026-09-07

> 🌩️ **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico: `ars_elemancy-1.21.1-1.18.3.jar`, mod id `ars_elemancy`, NeoForge 1.21.1. A build 1.18.3 define **6 escolas híbridas**, mais a progressão Elemancer, com **7 foci, 7 essences, 7 bangles e 21 armor sets** (sete identidades × light/medium/heavy), além de recipe próprio de upgrade de armadura e tags elementais de dano. Ars Elemental e Ars Nouveau continuam sendo os providers-base.

## 1. Identidade e versão
- **Mod:** Ars Elemancy.
- **JAR:** `ars_elemancy-1.21.1-1.18.3.jar`.
- **Runtime:** `1.18.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Papel:** especializações híbridas de elementos sobre Ars Elemental/Ars Nouveau.
- **Decisão:** **Manter**.

## 2. Escolas híbridas — 6
O registry define escolas próprias como composição de duas escolas elementais do Ars:
1. **Tempest** — Air + Water.
2. **Cinder** — Air + Fire.
3. **Silt** — Air + Earth.
4. **Mire** — Earth + Water.
5. **Vapor** — Fire + Water.
6. **Lava** — Fire + Earth.

A linha **Elemancer** usa a escola `ELEMENTAL` ampla, funcionando como progressão acima das seis combinações.

## 3. Foci — 7
- `tempest_focus`
- `silt_focus`
- `mire_focus`
- `lava_focus`
- `cinder_focus`
- `vapor_focus`
- `elemancer_focus`

São Curios/itens de especialização de uma escola. Não criar um segundo cálculo de spell power por fora do provider.

## 4. Essences — 7
- `tempest_essence`
- `silt_essence`
- `mire_essence`
- `lava_essence`
- `cinder_essence`
- `vapor_essence`
- `elemancer_essence`

Essences são recursos de progressão/crafting próprios do addon e não equivalem a Source.

## 5. Bangles — 7
- `tempest_bangle`
- `mire_bangle`
- `vapor_bangle`
- `silt_bangle`
- `lava_bangle`
- `cinder_bangle`
- `elemancer_bangle`

São acessórios vinculados às mesmas escolas. Curios/equipment modifiers precisam ser lidos do item real, não reproduzidos por nome.

## 6. Armor — 21 sets
Para cada identidade — Tempest, Silt, Mire, Lava, Vapor, Cinder e Elemancer — o source constrói três classes de armadura:
- **Light**
- **Medium**
- **Heavy**

Total: **21 conjuntos**, cada um com as peças normais de armor da implementação. O addon usa `ArmorPerkHolder` do Ars, portanto slots/perks nativos de armadura permanecem parte do contrato.

## 7. Recipe de upgrade
O registry adiciona `armor_upgrade` como **RecipeType + RecipeSerializer** baseado em `ElementalArmorRecipe`. Isso é a via canônica para upgrades correspondentes; não inferir upgrade apenas pela troca visual de item.

## 8. Damage tags elementais
A build registra quatro tags de DamageType no namespace do addon:
- `fire_damage`
- `water_damage`
- `earth_damage`
- `air_damage`

São superfícies de classificação. Outros sistemas de dano/perks devem consultar a classificação real e evitar aplicar bônus elementais novamente ao mesmo hit.

## 9. Relação com Ars Elemental
Ars Elemancy **depende diretamente de Ars Elemental** e combina suas afinidades. Ele não redefine as quatro escolas elementais nem cria um casting engine próprio. Boundary:
- Ars Nouveau = spell grammar/Source/cast.
- Ars Elemental = base elemental/foci/perks e integrações elementais.
- Ars Elemancy = pares de escolas + gear/Curios/progressão híbrida.

## 10. Dependência Sauce embutida
O JAR físico carrega Sauce via jarjar. Isso não é uma entrada top-level adicional na modlist. Não duplicar a biblioteca no catálogo apenas porque aparece dentro de `META-INF`.

## 11. Riscos
1. double-dip de spell power quando um spell pertence a duas escolas-base;
2. perks que somam bônus de Air+Water e Tempest novamente;
3. armor upgrade duplicado por recipe bridge externa;
4. Curios/armor modifiers reaplicados em handlers paralelos;
5. mudança de versão de Ars Elemental alterando escola/perk contracts;
6. dano híbrido mal classificado nas quatro tags.

## 12. Matriz de validação
- boot client + dedicated server;
- equipar os 7 foci e 7 bangles;
- validar 6 escolas híbridas + Elemancer;
- testar os 21 armor sets e seus perk slots;
- recipe `armor_upgrade` e persistência de perks;
- spells que pertencem a cada combinação sem double-dip;
- damage tags Fire/Water/Earth/Air;
- Curios/equipment save-load, death/respawn e reconnect;
- update de Ars Elemental sem registry/mapping drift.

## 13. Fontes
- Modlist física 07/09/2026.
- Source oficial `Lyrellion/Ars-Elemancy`, build `1.18.3`.
- Guia consolidado de Magia do projeto.

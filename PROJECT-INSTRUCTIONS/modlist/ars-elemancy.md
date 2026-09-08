# Ars Elemancy

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8132bccacdbc9f1c5030  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Ars Elemancy
- **Arquivo JAR:** `ars_elemancy-1.21.1-1.18.3.jar`
- **Versão 1.21.1:** `1.18.3`
- **Categoria:** Magia; RPG
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/Lyrellion/Ars-Elemancy
- **Função:** Expansão de Ars Elemental com 6 escolas híbridas (Tempest/Cinder/Silt/Mire/Vapor/Lava) + Elemancer, 7 foci, 7 essences, 7 bangles, 21 armor sets e recipe `armor_upgrade`.
- **Dependências:** Ars Nouveau + Ars Elemental. Sauce é biblioteca embutida/jarjar e não deve ser catalogada como mod top-level separado.
- **Compatibilidade/Riscos:** Risco central é double-dip de school power/perks em spells híbridos, especialmente quando bônus das duas escolas-base e da escola combinada coexistem. Também validar armor perk holders, Curios e tags elementais de dano após updates de Ars Elemental.
- **Sobreposição:** Sobrepõe conceitualmente especializações elementais do Ars Elemental, mas por design as combina. Não duplicar bônus das escolas-base e híbridas em bridges próprias.
- **Observações:** mod id ars_elemancy. Registry 1.18.3 confirma 6 SpellSchools híbridas; conteúdo principal: 7 foci + 7 essences + 7 bangles + 21 armor sets; `armor_upgrade` tem RecipeType/Serializer próprios.
- **Procedência:** Runtime/JAR: modlist física 07/09/2026. Registries: source oficial Ars-Elemancy build 1.18.3. Contexto: guia consolidado de Magia.
- **Histórico da decisão:** Manter. Ficha reconstruída em 07/09/2026 contra registry 1.18.3 e modlist física; addon é progressão híbrida real sobre Ars Elemental, não um sistema mágico paralelo.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026. Runtime confirmado: ars_elemancy-1.21.1-1.18.3.jar. Dossiê exaustivo com escolas híbridas, 7 foci/essences/bangles, 21 armor sets, armor_upgrade, damage tags, authority e testes.
- **Data da última decisão:** 2026-09-07.

## Dossiê operacional — padrão Alex's Mobs

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

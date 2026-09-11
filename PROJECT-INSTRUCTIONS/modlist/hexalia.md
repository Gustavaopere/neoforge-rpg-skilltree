# Hexalia

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db8119a1c2d69284d5abd2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Hexalia
- **Arquivo JAR:** `hexalia-neoforge-1.3.6.jar`
- **Versão 1.21.1:** 1.3.6 (metadata: 1.3.5)
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Categoria:** Magia, RPG, Worldgen, Mobs
- **Função:** Magia natural/bruxaria com herbal alchemy, rituals, brewing, mutations/transmutations, enchanted flora, idols, mobs, worldgen e data components próprios.
- **Dependências:** NeoForge 1.21.1; source 1.3.6 usa Architectury API 13.0.8, GeckoLib 4.7.6 e Patchouli 92. Pack contém Architectury 13.0.11, GeckoLib 4.9.2 e Patchouli 93.
- **Sobreposição:** Sobreposição temática com Ars/Goety/Malum em rituais/alquimia/nature magic, mas recipes, data components, entities e settlement são provider-specific; não unificar por semelhança temática.
- **Compatibilidade/Riscos:** Drift real: filename/release/source 1.3.6, metadata interna 1.3.5. Outros riscos: ABI Architectury/GeckoLib, recipe serializer drift, ritual/mutation double settlement, data-component/set-bonus duplication, projectile ownership e worldgen collision.
- **Observações:** Source 1.3.6 confirma recipe types celestial_infusion/natures_ritual/small_cauldron/mortar_and_pestle/mutation, 10 entity types e componentes persistentes/sincronizados para tether, moth, magic resist e armor-set state.
- **Procedência:** modlist.txt física atual de 09/09/2026 + CurseForge oficial Hexalia 1.3.6 NeoForge 1.21.1 + source oficial AstralyaStudios/Hexalia branch hexalia-1.21.1 exatamente em mod_version 1.3.6; metadata física 1.3.5 preservada como divergência.
- **Fonte:** https://github.com/AstralyaStudios/Hexalia/tree/hexalia-1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Hexalia 1.3.6 source-pinned com metadata runtime 1.3.5 divergente; recipes, entities, components, rituais/transmutação, worldgen, side/lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-28

# Dossiê operacional — padrão Alex's Mobs

> **VERSÃO COM DRIFT DE METADATA.** O arquivo físico é `hexalia-neoforge-1.3.6.jar` e a release/source oficial 1.21.1 declara **Hexalia 1.3.6**; porém a metadata interna exibida pela modlist reporta `1.3.5`. A divergência é real e fica preservada. Não renomear nem tratar silenciosamente o runtime como 1.3.6 sem registrar o metadata stale.

## 1. Identidade e papel
Hexalia é um mod de magia natural/bruxaria centrado em **rituais, herbal alchemy, flora viva, brewing, transmutação e exploração**. O source oficial `AstralyaStudios/Hexalia:hexalia-1.21.1` declara `mod_version=1.3.6`, Minecraft 1.21.1 e suporte Fabric/NeoForge, correspondendo ao filename/release instalado.

## 2. Authority
Hexalia é authority de seus próprios herbs/crops, recipes de processamento, rituals, brews, enchanted plants, idols, mutations/transmutations, mobs e state de itens/componentes. Outros sistemas mágicos do pack podem observar ou integrar esses estados, mas não devem duplicar ritual settlement, mutation outcome ou data components.

## 3. Dependências físicas e build line
O source 1.3.6 foi desenvolvido contra NeoForge 21.1.215, Architectury API 13.0.8, GeckoLib 4.7.6 e Patchouli 92. O pack contém NeoForge 21.1.248, Architectury 13.0.11, GeckoLib 4.9.2 e Patchouli 93. Isso é compatibilidade por faixa/linha, não prova de ausência de regressão; as versões físicas atuais são a matriz de teste real.

## 4. Registries confirmados no source 1.3.6
A linha exata registra blocks, items, entities, effects, sounds, particles, menus, block entities, recipe serializers/types, data components, worldgen features/tree decorators e creative tabs. O mod também usa mixin NeoForge próprio. Essas superfícies devem ser consideradas em startup, registry sync e reload.

## 5. Recipe systems
O source registra cinco recipe types principais: `celestial_infusion`, `natures_ritual`, `small_cauldron`, `mortar_and_pestle` e `mutation`; há ainda serializer legado `ritual_table` apontando para o ritual moderno. Scripts/datapacks não devem presumir que todos são crafting-table recipes. Alterar serializers/JSON requer `/reload` e validação de codecs.

## 6. Rituais e celestial infusion
`natures_ritual` e `celestial_infusion` são contracts data-driven distintos. Admission, consumo de ingredientes e resultado precisam ser liquidados uma vez. Chunk unload, restart ou dois jogadores acionando a mesma estrutura não podem duplicar output nem consumir inputs parcialmente.

## 7. Herbal alchemy / mortar / cauldron
O README oficial descreve coleta de ervas/crops, refino por ferramentas simples e mistura de essências com salt/elemental forces, além de brews com efeitos em camadas. `mortar_and_pestle` e `small_cauldron` confirmam pipelines próprios de processamento. Recipes e efeitos devem permanecer provider-owned e não ser convertidos em recipes genéricas sem preservar semântica.

## 8. Mutation e transmutação
O projeto destaca **Mutavis** e **Morphora** como ferramentas/conceitos de transmutação do mundo. O registry `mutation` confirma recipe pipeline específico. Integrações externas devem observar resultado final e requisitos reais, evitando executar uma segunda transmutação ao escutar interação + recipe completion.

## 9. Entidades registradas
O source 1.3.6 registra exatamente dez entity types: `silk_moth`, `cacofey`, `mod_boat`, `mod_chest_boat`, `rabbage`, `purifying_sac`, `foul_sac`, `frost_sac`, `searing_sac` e `thorn_arrow`. Os cinco `*_sac`/rabbage/thorn arrow são projectiles/utility entities; Silk Moth e Cacofey são creatures. Não extrapolar spawn rules sem data/source específico correspondente.

## 10. Data components persistentes/sincronizados
A build registra componentes `spiritroot_tether`, `moth`, `magic_resist_pct`, `armor_set_id`, `armor_group_set_id` e `full_set_bonus_pct`. Todos são construídos com persistência e sincronização de rede no source. Isso torna item serialization, clone/copy, inventory transfer e network sync boundaries críticos.

## 11. Armor/magic-resist state
`magic_resist_pct`, IDs de set/grupo e `full_set_bonus_pct` indicam state versionado em item components. Equip/unequip/relog não deve duplicar bônus de set nem perder resistência. Mods próprios devem ler o valor authoritative do item/provider em vez de manter shadow state paralelo.

## 12. Flora, idols e worldgen
O projeto descreve enchanted plants que alteram o ambiente, idols capazes de influenciar weather/cleansing, além de flora e estruturas próprias. Features e tree decorators registrados tornam worldgen uma superfície efetiva. Mudanças em worldgen precisam ser testadas em mundo novo e em chunks ainda não gerados; não presumir retrofit de chunks existentes.

## 13. Client / server
A distribuição é Client & Server. Servidor deve ser authority de recipes, ritual/mutation settlement, projectile damage/effects, mob state, item components e world changes. Cliente cuida de render/models/particles/sounds/UI. Data components sincronizados devem convergir ao mesmo valor após reconnect.

## 14. Lifecycle e multiplayer
Validar boot/registry sync, recipe reload, resource reload, chunk unload/reload, entity despawn, item transfer, death/respawn, reconnect, dimension change e server restart. Rituais/infusions com múltiplos participantes precisam de owner/contexto determinístico. Projectile entities devem atribuir caster/owner corretamente.

## 15. Sobreposição com outros sistemas mágicos
Hexalia se sobrepõe tematicamente a Ars Nouveau, Goety, Malum e outros providers em alquimia, flora e rituais, mas recipes, components, resources e settlement continuam específicos. Sem bridge explícita, “ritual”, “mana”, “spirit” ou “transmutation” de outro mod não são equivalentes aos de Hexalia.

## 16. Riscos técnicos
- metadata interna 1.3.5 causar diagnóstico/version-gate incorreto apesar do release 1.3.6;
- ABI drift com Architectury/GeckoLib/Patchouli;
- serializer legado `ritual_table` ou recipe JSON stale;
- ritual/mutation double settlement;
- data component perdido/duplicado em item copy/relog;
- set bonus/resistance aplicado mais de uma vez;
- projectile ownership errado em multiplayer;
- worldgen feature collision com o stack extenso do pack;
- client/server registry mismatch;
- source/release 1.3.6 ser confundido com metadata runtime 1.3.5.

## 17. Matriz de testes obrigatória
- [ ] Dedicated server boot com filename 1.3.6 / metadata 1.3.5 sem dependency warning inesperado.
- [ ] Registry sync das entities/recipes/components atuais.
- [ ] `natures_ritual` success/failure/retry/chunk unload sem dupe/loss.
- [ ] `celestial_infusion`, cauldron, mortar e mutation recipes sobrevivem a `/reload`.
- [ ] Spiritroot tether e moth components persistem após inventory move/relog/restart.
- [ ] Armor set/magic resist components não duplicam modifiers.
- [ ] Silk Moth e Cacofey spawn/despawn/save-load sem state stale.
- [ ] Projectiles atribuem owner/damage corretamente com dois jogadores.
- [ ] Mundo novo gera features/structures esperadas sem colisão crítica.
- [ ] GeckoLib/resource reload não deixa render cache stale.

## 18. Evidências e limites
- **Modlist/JAR metadata:** filename 1.3.6, mod id `hexalia`, metadata reportada `1.3.5`.
- **CurseForge:** release NeoForge 1.3.6 para 1.21.1 em 16/08/2026.
- **Source oficial:** branch `hexalia-1.21.1`, `mod_version=1.3.6`; recipe types, entities e data components acima confirmados diretamente.
- **Limite:** a causa do metadata stale não foi determinada nesta auditoria; defaults/configs e spawn tables não lidos não são inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

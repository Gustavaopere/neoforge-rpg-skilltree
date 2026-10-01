# Hexalia

## Propriedades do registro

- **Mod:** Hexalia
- **Arquivo JAR:** `hexalia-neoforge-1.3.7.jar`
- **Versão 1.21.1:** `1.3.7`
- **Categoria:** Magia, RPG, Worldgen, Mobs
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/AstralyaStudios/Hexalia/tree/hexalia-1.21.1
- **Função:** Magia natural/bruxaria com herbal alchemy, rituals, brewing, mutations/transmutations, enchanted flora, idols, mobs, worldgen e data components próprios.
- **Dependências:** NeoForge 1.21.1. Pack físico atual usa NeoForge 21.1.250, Architectury 13.0.11, GeckoLib 4.9.2 e Patchouli 93. As versões de desenvolvimento citadas nas seções 1.3.6 abaixo são snapshot histórico, não authority atual.
- **Compatibilidade/Riscos:** Runtime atual 1.3.7. Riscos: ABI Architectury/GeckoLib/Patchouli, recipe serializer drift, ritual/mutation double settlement, data-component/set-bonus duplication, projectile ownership e worldgen collision. O antigo drift filename 1.3.6 × metadata 1.3.5 pertence ao snapshot histórico e não deve ser aplicado ao JAR físico atual 1.3.7.
- **Sobreposição:** Sobreposição temática com Ars/Goety/Malum em rituais/alquimia/nature magic, mas recipes, data components, entities e settlement são provider-specific; não unificar por semelhança temática.
- **Observações:** Runtime físico 1.3.7. A release 1.3.7 overhaul Nature's Ritual com braziers livres/ofertas variáveis, adiciona soul-sacrifice rituals, Herb Jar, Heartseed, Gravebloom Brew, Cinderhew, novos relics/accessories, novos enchanted-plant behaviors/herb propagation e artwork atualizado.
- **Procedência:** modlist física atual confirma `hexalia-neoforge-1.3.7.jar` / runtime `1.3.7`; CurseForge file ID 8875587 confirma a release NeoForge 1.21.1. O snapshot 1.3.6/metadata 1.3.5 permanece preservado abaixo como histórico de migração, não como state atual.
- **Atualização/Status:** RECONCILIAÇÃO FÍSICA + UPSTREAM REVALIDADA EM 01/10/2026 — Hexalia físico e upstream 1.21.1 estão ambos em **1.3.7**. O header foi reconciliado para a autoridade atual; o antigo drift 1.3.6/1.3.5 permanece explicitamente histórico.
- **Data da última decisão:** 2026-08-28

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #314: JAR `hexalia-neoforge-1.3.7.jar`, mod id `hexalia`, runtime `1.3.7`, SHA-1 `ca90edf1664cf6d44fe7e5318c71069050499c7e`.

> **Reconciliação de autoridade física — 27/09/2026.** O texto migrado do Notion abaixo preserva o snapshot histórico de 12/09/2026, no qual `hexalia-neoforge-1.3.6.jar`, metadata `1.3.5` e NeoForge `21.1.248` eram registrados. Isso **não representa a instalação física atual**: a modlist atual confirma `hexalia-neoforge-1.3.7.jar`, mod id `hexalia`, runtime `1.3.7`, e o modloader NeoForge `21.1.250`. O conteúdo histórico é mantido integralmente para paridade; para versão instalada e loader atuais, prevalece esta reconciliação junto ao bloco de autoridade física atual.

<callout icon="⚠️" color="yellow_bg">
	**DRIFT HISTÓRICO — SNAPSHOT 12/09/2026.** Naquele snapshot, o arquivo era `hexalia-neoforge-1.3.6.jar` enquanto a metadata interna reportava `1.3.5`. Essa divergência histórica é preservada para paridade, mas **não representa o JAR atual**, que a modlist confirma como `hexalia-neoforge-1.3.7.jar` / runtime `1.3.7`.
</callout>
## 1. Identidade e papel
Hexalia é um mod de magia natural/bruxaria centrado em **rituais, herbal alchemy, flora viva, brewing, transmutação e exploração**. A análise de source 1.3.6 abaixo pertence ao snapshot histórico que originou o dossiê; a autoridade física atual é a release **1.3.7**.
## 2. Authority
Hexalia é authority de seus próprios herbs/crops, recipes de processamento, rituals, brews, enchanted plants, idols, mutations/transmutations, mobs e state de itens/componentes. Outros sistemas mágicos do pack podem observar ou integrar esses estados, mas não devem duplicar ritual settlement, mutation outcome ou data components.
## 3. Dependências físicas e build line
O source 1.3.6 histórico foi desenvolvido contra NeoForge 21.1.215, Architectury API 13.0.8, GeckoLib 4.7.6 e Patchouli 92. O pack físico atual usa **NeoForge 21.1.250**, Architectury 13.0.11, GeckoLib 4.9.2 e Patchouli 93. As diferenças são regression gates; a baseline instalada atual é Hexalia 1.3.7.
## 4. Registries confirmados no source 1.3.6
A linha exata registra blocks, items, entities, effects, sounds, particles, menus, block entities, recipe serializers/types, data components, worldgen features/tree decorators e creative tabs. O mod também usa mixin NeoForge próprio. Essas superfícies devem ser consideradas em startup, registry sync e reload.
## 5. Recipe systems
O source registra cinco recipe types principais: `celestial_infusion`, `natures_ritual`, `small_cauldron`, `mortar_and_pestle` e `mutation`; há ainda serializer legado `ritual_table` apontando para o ritual moderno. Scripts/datapacks não devem presumir que todos são crafting-table recipes. Alterar serializers/JSON requer `/reload` e validação de codecs.
A release oficial **1.3.6** também adicionou uma configuração do **Nature's Ritual** para exigir de **0 a 32 crops próximos**, com **8 como padrão**. Esse valor deve ser tratado como configuração efetiva do provider e validado no runtime/config da instância antes de qualquer integração que assuma custo ou condição fixa.
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
- [ ] Dedicated server boot com **Hexalia 1.3.7**; manter o antigo caso filename 1.3.6/metadata 1.3.5 apenas como regressão histórica.
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
- **Modlist/JAR atual:** `hexalia-neoforge-1.3.7.jar`, mod id `hexalia`, runtime `1.3.7`; o filename 1.3.6 / metadata 1.3.5 é evidência histórica do snapshot anterior.
- **CurseForge:** release NeoForge **1.3.7** para 1.21.1 (file ID 8875587); a 1.3.6 permanece referência histórica.
- **Source oficial:** branch `hexalia-1.21.1`, `mod_version=1.3.6`; recipe types, entities e data components acima confirmados diretamente.
- **Limite:** a causa do metadata stale não foi determinada nesta auditoria; defaults/configs e spawn tables não lidos não são inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.


## 19. Reconciliação atual e release 1.3.7
A modlist física atual e a distribuição oficial convergem em **Hexalia 1.3.7**. Portanto não há update upstream pendente neste item.

Changelog 1.3.7:
- overhaul do **Nature's Ritual**, com colocação livre de braziers e quantidades variáveis de offering;
- novos **soul-sacrifice rituals** para summon de Silk Moths e Cacofey;
- adiciona **Herb Jar, Heartseed, Gravebloom Brew e Cinderhew**;
- expande o accessory system com novos magical relics;
- adiciona novos enchanted-plant behaviors e melhora herb propagation;
- atualiza artwork do conteúdo 1.3.7 e sprites de brews, salves e spawn eggs.

Boundary: os registries/source descritos em detalhe nas seções antigas foram pinados na 1.3.6 e não são automaticamente declarados idênticos na 1.3.7 sem nova leitura integral do source. A versão instalada, porém, é inequivocamente 1.3.7.

Fonte upstream: CurseForge file ID 8875587, `hexalia-neoforge-1.3.7.jar`.
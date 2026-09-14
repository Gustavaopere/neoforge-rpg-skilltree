# Artifacts

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8121b079dbc4804d6da0
- **Baseline física pré-update:** `artifacts-neoforge-13.2.3.jar`
- **Artefato alvo selecionado no CurseForge:** `artifacts-neoforge-13.2.5.jar` — file `8791899`, Release, NeoForge 1.21.1, publicado em 02/09/2026
- **Source target já comparado:** branch oficial `1.21.1`, commit `7cf7dc42e322e13f096eea16cee17a4b400b75f7` (`Update 13.2.5`)
- **Data da atualização documental GitHub:** 2026-09-14

> **BOUNDARY FÍSICO.** A modlist física fornecida ainda contém 13.2.3. O GitHub registra 13.2.5 como alvo. O inventário de itens não mudou no diff 13.2.3→13.2.5 (`ModItems.java` permaneceu intacto); os arquivos alterados auditados foram `MimicChestMaterials.java`, `ArtifactHooks.java` e `gradle.properties`. A 13.2.5 publica explicitamente **“Fixed a crash with Quark”**, relevante porque Quark está no pack.

## Propriedades equivalentes do catálogo

- **Mod:** Artifacts
- **Arquivo JAR alvo:** `artifacts-neoforge-13.2.5.jar`
- **Versão 1.21.1 alvo:** 13.2.5
- **Baseline física auditada:** 13.2.3
- **Estado da pesquisa:** Verificado documentalmente/source-level; validação física/runtime da 13.2.5 pendente
- **Decisão:** Sem decisão
- **Categoria:** RPG, QoL
- **Função:** Adiciona 45 artefatos equipáveis Curios e 4 itens utilitários, com atributos, toggles, movement, retaliation, lifesteal, auto-smelt, death protection e loot próprio.
- **Dependências:** Curios 9.5.1 no pack baseline; ExpandAbility 12.0.0 embedded no JAR 13.2.3. Source baseline compilado com Curios 9.3.1/NeoForge 21.1.211; reextrair metadata/JarJar do alvo 13.2.5 após instalação.
- **Sobreposição:** Pode compartilhar efeitos com acessórios de outros mods, mas possui seu próprio conjunto de itens e rotas de aquisição.
- **Compatibilidade/Riscos:** Alta sobreposição de atributos/movimento/combate com RPG, Epic Fight e outros providers. Quark 4.1-483 está presente; 13.2.5 inclui o fix de crash com Quark que faltava na baseline 13.2.3. Manter regressão Mimic/Quark, Curios, loot dedup, cooldown/death lifecycle e dedicated server.
- **Observações:** Inventário source-confirmed: 45 wearables + 4 não-wearables = 49 item entries e `ModItems.java` não mudou entre 13.2.3 e 13.2.5. A atualização altera apenas a linha Mimic/hooks/version no diff auditado.
- **Procedência:** modlist.txt física baseline + source oficial `ochotonida/artifacts` branch 1.21.1 commits 13.2.3/13.2.5 + CurseForge 13.2.5 file 8791899 + comparação upstream 13.2.3→13.2.5.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/artifacts
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/09/2026 — GitHub promovido para alvo 13.2.5. Inventário de 49 itens permanece source-confirmed; patch boundary Quark/Mimic deixa de ser “fix ausente” e passa a ser regression gate incluído na versão-alvo.
- **Histórico da decisão:** Sem decisão formal. A baseline 13.2.3 foi source-pinned em 09/09/2026; em 14/09 o target 13.2.5 foi selecionado porque a correção Quark é material para a composição física do pack.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Baseline física/source: `artifacts-neoforge-13.2.3.jar`, commit `eb331973311068a6b5a6392a0c734f7f2a705e2a`. Alvo: `artifacts-neoforge-13.2.5.jar`, commit source `7cf7dc42e322e13f096eea16cee17a4b400b75f7`. O diff source confirma que o inventário de itens não mudou; a promoção é concentrada em patches Mimic/Quark/hooks.

## 1. Papel e autoridade
Artifacts adiciona artefatos equipáveis e utilidades com efeitos próprios via atributos, data components, eventos e Curios. **Artifacts é a autoridade dos efeitos de seus itens**; Curios é autoridade de slots/equip state; Minecraft/NeoForge são autoridade dos atributos/damage/effects subjacentes. Sistemas RPG próprios não devem recalcular nem reaplicar os efeitos dos artefatos apenas por detectarem o item equipado.

## 2. Inventário de itens — 49 entradas confirmadas

### Não-equipáveis — 4
- `mimic_spawn_egg`
- `umbrella`
- `everlasting_beef`
- `eternal_steak`

Everlasting Beef e Eternal Steak são alimentos reutilizáveis com cooldown configurável. Umbrella tem comportamento próprio de item. Mimic Spawn Egg materializa a entidade Mimic do mod.

### Head — 8
1. `plastic_drinking_hat` — bônus de eating/drinking speed.
2. `novelty_drinking_hat` — eating/drinking speed + ability lore.
3. `snorkel` — Water Breathing conforme condição/config.
4. `night_vision_goggles` — Night Vision, força visual reduzida configurável e toggle.
5. `villager_hat` — atributo de villager reputation.
6. `superstitious_hat` — bônus de Looting.
7. `cowboy_hat` — mount speed.
8. `anglers_hat` — bônus de Luck of the Sea e Lure.

### Necklace — 9
1. `lucky_scarf` — Fortune.
2. `scarf_of_invisibility` — invisibilidade + toggle e regras de ocultação.
3. `cross_necklace` — invincibility ticks adicionais + post-damage cooldown.
4. `panic_necklace` — Speed após receber dano + cooldown.
5. `shock_pendant` — retaliatory lightning; pode cancelar dano de lightning conforme config.
6. `flame_pendant` — retaliatory fire e possível Fire Resistance.
7. `thorn_pendant` — thorns retaliation com chance/cooldown/faixa de dano.
8. `charm_of_sinking` — sinking, oxygen bonus, regra de fall damage subaquático e toggle.
9. `charm_of_shrinking` — modifica atributo `SCALE` e possui toggle.

### Belt — 8
1. `cloud_in_a_bottle` — double jump, velocidades/config e SAFE_FALL_DISTANCE.
2. `obsidian_skull` — Fire Resistance após dano de fogo + cooldown.
3. `antidote_vessel` — cura efeitos dentro do limite de duração configurado.
4. `universal_attractor` — efeito Magnetism + toggle.
5. `crystal_heart` — MAX_HEALTH.
6. `helium_flamingo` — “swim in air” com flight/recharge/cooldown.
7. `chorus_totem` — death-protection teleport com chance, health restored, cooldown e consumo configuráveis.
8. `warp_drive` — Ender Pearl com custo de fome/cooldown e possível imunidade ao dano da pearl.

### Hands — 10
1. `digging_claws` — BLOCK_BREAK_SPEED + tool tier upgrade.
2. `feral_claws` — ATTACK_SPEED.
3. `power_glove` — ATTACK_DAMAGE.
4. `fire_gauntlet` — duração de burning em ataques.
5. `pocket_piston` — ATTACK_KNOCKBACK.
6. `vampiric_glove` — damage absorption/lifesteal configurável.
7. `golden_hook` — bônus de XP de entidade.
8. `onion_ring` — alimento/equipável; Haste após comer, escalado pela comida.
9. `pickaxe_heater` — auto-smelt.
10. `withered_bracelet` — chance de aplicar Wither em ataque, com nível/duração/cooldown.

### Feet — 9
1. `aqua_dashers` — colisão com fluido quando sprintando.
2. `bunny_hoppers` — jump strength, fall modifiers, safe fall distance e hurt sound de coelho.
3. `kitty_slippers` — repele creepers/phantoms e altera hurt sound.
4. `running_shoes` — sprint speed + sprint step height.
5. `snowshoes` — caminhar em powder snow + movement speed on snow.
6. `steadfast_spikes` — knockback resistance + slip resistance.
7. `flippers` — swim speed.
8. `rooted_boots` — replenishes hunger sobre grass + plant growth após comer.
9. `strider_shoes` — colisão com lava quando sneak + proteção de hot floor conforme config.

### Curio genérico — 1
- `whoopee_cushion` — atributo de flatulence/chance + som próprio.

## 3. Patch boundary 13.2.3 → 13.2.5
O commit baseline é `eb331973311068a6b5a6392a0c734f7f2a705e2a`; o target é `7cf7dc42e322e13f096eea16cee17a4b400b75f7` (`Update 13.2.5`). A comparação upstream mostra somente:
- `MimicChestMaterials.java`;
- `ArtifactHooks.java`;
- `gradle.properties`.

`ModItems.java` **não muda**, logo as 49 entradas permanecem válidas para 13.2.5.

A baseline 13.2.3 já inclui fixes de textura de Mimic com Lootr e funcionamento em trinket slot. A linha posterior 13.2.4/13.2.5 incorpora patches adicionais no boundary Mimic/Quark/hooks. O changelog público da **13.2.5** registra literalmente **“Fixed a crash with Quark”**.

Como Quark 4.1-483 está fisicamente presente, esse fix é material e a 13.2.5 é preferível à baseline para o pack atual.

## 4. Arquitetura de efeitos
O source baseline/linha constrói grande parte do comportamento por **data components** e atributos, incluindo componentes para:
- double jump;
- post-damage effects/cooldowns;
- retaliation (thorns/fire/lightning);
- damage immunity;
- cure effects;
- swim in air;
- death protection teleport;
- Ender Pearl cost/immunity;
- tool tier upgrade;
- damage absorption;
- post-eating effects;
- auto-smelt;
- attack effects;
- fluid collision;
- repellents;
- hunger/plant growth;
- toggles.

Integração correta deve observar o efeito final/provider, não duplicar lógica item-a-item em outro mod.

## 5. Atributos próprios/superfícies de atributo observadas
O inventário usa atributos vanilla e custom como drinking speed, eating speed, villager reputation, mount speed, invincibility ticks, entity experience, attack burning duration, magnetism/effects, sprinting speed/step height, movement speed on snow, slip resistance, swim speed e flatulence.

**AttributeFix está presente no pack**, mas apenas amplia ranges; ele não substitui nem calcula os modifiers de Artifacts.

## 6. Loot e aquisição
O source NeoForge possui geração de loot/loot modifiers própria para inserir artefatos no ecossistema de loot. Itens podem ser individualmente controlados por config (`generateAsLoot` é uma superfície confirmada no datagen/config para itens como Cloud in a Bottle). Portanto:
- não adicionar segundo loot injection genérico sem dedup;
- configs do Artifacts permanecem autoridade sobre habilitação/loot de cada artefato;
- Lootr/containers modded precisam ser validados por compatibilidade, não presumidos.

## 7. Curios e slots
O source mantém tags/equip semantics para head/necklace/belt/hands/feet/curio. Curios no pack baseline está em 9.5.1+1.21.1. O item equipado deve ter exatamente uma instância ativa por slot/provider; sistemas externos não devem conceder o modifier novamente a cada tick.

Reextrair a dependency metadata do JAR 13.2.5 após instalação antes de declarar compile/runtime targets internos exatos.

## 8. Morte, cooldown e persistência
Artefatos com estado/cooldown/toggle/death-protection exigem atenção especial:
- Chorus Totem: death protection não pode disparar duas vezes no mesmo evento de morte.
- Cloud in a Bottle: double jump deve resetar apenas em lifecycle válido de grounded/air state.
- Helium Flamingo: flight/recharge/cooldown precisa persistir/sincronizar coerentemente.
- Cross/Panic/Obsidian/retaliation pendants: cooldown deve ser server-authoritative.
- Toggle items: client envia intenção; servidor valida equip state e estado final.

## 9. Client/server e multiplayer
- Damage, healing, attributes, item durability, food cooldown, death protection, teleport e loot são servidor.
- Renderers/model layers/pose de wearables são cliente.
- Multiplayer precisa manter efeitos por `LivingEntity`/player correto; nada de cache global de artefato equipado.
- Fake players devem seguir apenas hooks oficialmente suportados para mineração/auto-smelt; não conceder player-only Curios behavior por inferência.

## 10. Interações de alto risco no pack

### Quark / Mimic
Quark está presente e é precisamente a integração associada ao crash corrigido em 13.2.5. Testar Mimic/containers/entidades em cenários Quark relevantes. A ausência de crash precisa ser comprovada em runtime; o changelog não substitui teste do stack completo.

### RPG/perks/Apothic Attributes
Muitos artefatos modificam os mesmos atributos usados por perks. A composição deve ocorrer via attribute system; não transformar o valor do item em bônus paralelo.

### Epic Fight/combate
Attack speed, attack damage, knockback, lifesteal, retaliation, invulnerability ticks e attack effects podem interagir com pipelines de combate. Preservar causalidade do hit e exatamente-uma execução do Artifacts.

### Movimento
Double jump, air swimming, scale, fluid collision, jump strength, sprint speed e step height podem conflitar com ParCool/outros movement providers. O item não deve ganhar uma segunda implementação genérica só para “compatibilidade”.

### Loot
Múltiplos mods injetam loot. Testar geração sem duplicação em estruturas/container providers presentes no pack.

## 11. Testes/regressões obrigatórios
1. Nova modlist física confirma `artifacts-neoforge-13.2.5.jar`.
2. Dedicated server/client boot com Quark 4.1-483 e Curios do pack sem crash.
3. Reproduzir paths Mimic/Quark historicamente problemáticos e confirmar ausência do crash corrigido na 13.2.5.
4. Equip/unequip de cada família de slot e ausência de modifier fantasma.
5. Death/logout/respawn/dimension change com Chorus Totem e toggles.
6. Cloud in a Bottle: single double-jump por ciclo; fall damage correto.
7. Retaliation: um proc por hit elegível, cooldown respeitado.
8. Vampiric Glove: heal uma vez por dano elegível e cap por hit.
9. Pickaxe Heater: drop original não pode coexistir com resultado smeltado duplicado.
10. Fluid walkers: atualização de estado sem desync cliente/servidor.
11. Loot configs: disabled item não deve aparecer por modifier próprio.
12. Curios: equip sync e slot tags sem ghost modifier.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidência
- Modlist física baseline: Artifacts 13.2.3, Curios 9.5.1 e Quark 4.1-483.
- Source oficial `ochotonida/artifacts`, branch `1.21.1`: commit baseline `eb331973311068a6b5a6392a0c734f7f2a705e2a` e target `7cf7dc42e322e13f096eea16cee17a4b400b75f7`.
- `ModItems.java` auditado: 49 entradas; diff 13.2.3→13.2.5 confirma que esse arquivo não mudou.
- CurseForge oficial: `artifacts-neoforge-13.2.5.jar`, file 8791899, Release NeoForge 1.21.1, 02/09/2026; changelog “Fixed a crash with Quark”.
- Datagen NeoForge de loot/tags e componentes/rendering já consultados na baseline.

> **Contagem source-confirmed:** 45 wearables (8 head + 9 necklace + 8 belt + 10 hands + 9 feet + 1 curio) + 4 itens não-wearable = 49 item entries. **Instalação física 13.2.5 ainda precisa ser confirmada pela próxima modlist/JAR.**
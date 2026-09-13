# Artifacts

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8121b079dbc4804d6da0
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Artifacts
- **Arquivo JAR:** `artifacts-neoforge-13.2.3.jar`
- **Versão 1.21.1:** 13.2.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, QoL
- **Função:** Adiciona 45 artefatos equipáveis Curios e 4 itens utilitários, com atributos, toggles, movement, retaliation, lifesteal, auto-smelt, death protection e loot próprio.
- **Dependências:** Curios 9.5.1 no pack; ExpandAbility 12.0.0 embedded no JAR. Source 13.2.3 foi compilado com Curios 9.3.1 e NeoForge 21.1.211; runtime atual deve ser validado contra Curios 9.5.1/pack completo.
- **Sobreposição:** Pode compartilhar efeitos com acessórios de outros mods, mas possui seu próprio conjunto de itens e rotas de aquisição.
- **Compatibilidade/Riscos:** Alta sobreposição de atributos/movimento/combate com RPG, Epic Fight e outros providers. Quark 4.1-483 está presente e o runtime 13.2.3 NÃO contém os fixes Quark/crash posteriores de 13.2.4/13.2.5; validar Mimic/Quark, Curios 9.5.1, loot dedup, cooldown/death lifecycle e dedicated server.
- **Observações:** Inventário source-confirmed no commit 13.2.3: 45 wearables + 4 não-wearables = 49 item entries. `ModItems.java` não muda entre 13.2.3 e 13.2.5; fixes posteriores foram explicitamente excluídos do runtime atual.
- **Procedência:** modlist.txt física atual de 08/09/2026 + source oficial `ochotonida/artifacts` branch 1.21.1 commit `eb331973311068a6b5a6392a0c734f7f2a705e2a` (`mod_version=13.2.3`) + CurseForge oficial 13.2.3 + comparação upstream 13.2.3→13.2.5.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/artifacts
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — runtime Artifacts 13.2.3 fixado ao commit upstream exato `eb331973311068a6b5a6392a0c734f7f2a705e2a`; inventário de 49 itens validado por diff até 13.2.5 e patch boundary 13.2.4/13.2.5 preservado no QC global #55.
- **Histórico da decisão:** Sem decisão formal. A ficha foi inicialmente construída contra a linha source 13.2.5; em 09/09/2026 esse desvio foi corrigido contra o runtime físico real 13.2.3 e o commit upstream exato `eb331973311068a6b5a6392a0c734f7f2a705e2a`. O inventário de 49 entries permanece válido porque `ModItems.java` não mudou entre 13.2.3 e 13.2.5. A presença física não foi convertida automaticamente em decisão.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física/source agora fixada exatamente: `artifacts-neoforge-13.2.3.jar`, mod id `artifacts`, versão `13.2.3`, branch oficial `1.21.1`, commit upstream `eb331973311068a6b5a6392a0c734f7f2a705e2a` (`Update 13.2.3`). Esse checkpoint declara NeoForge 21.1.211, Curios 9.3.1 e ExpandAbility 12.0.0; no pack Curios é 9.5.1 e ExpandAbility 12.0.0 está embarcado no JAR.

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

## 2.1 Patch boundary 13.2.3 → 13.2.5
O commit exato da release instalada é `eb331973311068a6b5a6392a0c734f7f2a705e2a`. A comparação upstream até `7cf7dc42e322e13f096eea16cee17a4b400b75f7` (`Update 13.2.5`) mostra somente três arquivos alterados: `MimicChestMaterials.java`, `ArtifactHooks.java` e `gradle.properties`; `ModItems.java` não muda. Assim, o inventário de **49 item entries** permanece válido para 13.2.3 com evidência source-level exata. A 13.2.3 inclui os fixes de textura de Mimic com Lootr e de funcionamento em trinket slot. As correções posteriores de 13.2.4/13.2.5 — incluindo fixes adicionais de Mimic/Quark e crash com entidades modded/Quark — **não pertencem ao runtime instalado**. Como Quark 4.1-483 está fisicamente presente, esse boundary é risco real de regressão e deve ser testado em runtime.

## 3. Arquitetura de efeitos
O source exato 13.2.3 constrói grande parte do comportamento por **data components** e atributos, incluindo componentes para:
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

Isso significa que integração correta deve observar o efeito final/provider, não duplicar lógica item-a-item em outro mod.

## 4. Atributos próprios/superfícies de atributo observadas
O inventário usa atributos vanilla e custom como drinking speed, eating speed, villager reputation, mount speed, invincibility ticks, entity experience, attack burning duration, magnetism/effects, sprinting speed/step height, movement speed on snow, slip resistance, swim speed e flatulence.

**AttributeFix está presente no pack**, mas apenas amplia ranges; ele não substitui nem calcula os modifiers de Artifacts.

## 5. Loot e aquisição
O source NeoForge possui geração de loot/loot modifiers própria para inserir artefatos no ecossistema de loot. Itens podem ser individualmente controlados por config (`generateAsLoot` é uma superfície confirmada no datagen/config para itens como Cloud in a Bottle). Portanto:
- não adicionar segundo loot injection genérico sem dedup;
- configs do Artifacts devem permanecer autoridade sobre habilitação/loot de cada artefato;
- Lootr/containers modded precisam ser validados por compatibilidade, não presumidos.

## 6. Curios e slots
O source mantém tags/equip semantics para grupos como head/necklace/belt/hands/feet/curio. Curios no pack está em 9.5.1+1.21.1, acima do compile target 9.3.1. O item equipado deve ter exatamente uma instância ativa por slot/provider; sistemas externos não devem conceder o modifier novamente a cada tick.

## 7. Morte, cooldown e persistência
Artefatos com estado/cooldown/toggle/death-protection exigem atenção especial:
- Chorus Totem: death protection não pode disparar duas vezes no mesmo evento de morte.
- Cloud in a Bottle: double jump deve resetar apenas em lifecycle válido de grounded/air state.
- Helium Flamingo: flight/recharge/cooldown precisa persistir/sincronizar coerentemente.
- Cross/Panic/Obsidian/retaliation pendants: cooldown deve ser server-authoritative.
- Toggle items: client envia intenção; servidor valida equip state e estado final.

## 8. Client/server e multiplayer
- Damage, healing, attributes, item durability, food cooldown, death protection, teleport e loot são servidor.
- Renderers/model layers/pose de wearables são cliente.
- Multiplayer precisa manter efeitos por `LivingEntity`/player correto; nada de cache global de artefato equipado.
- Fake players devem seguir apenas hooks oficialmente suportados para mineração/auto-smelt; não conceder player-only Curios behavior por inferência.

## 9. Interações de alto risco no pack

### RPG/perks/Apothic Attributes
Muitos artefatos modificam os mesmos atributos usados por perks. A composição deve ocorrer via attribute system; não transformar o valor do item em bônus paralelo.

### Epic Fight/combate
Attack speed, attack damage, knockback, lifesteal, retaliation, invulnerability ticks e attack effects podem interagir com pipelines de combate. Preservar causalidade do hit e exatamente-uma execução do Artifacts.

### Movimento
Double jump, air swimming, scale, fluid collision, jump strength, sprint speed e step height podem conflitar com ParCool/outros movement providers. O item não deve ganhar uma segunda implementação genérica só para “compatibilidade”.

### Loot
Múltiplos mods injetam loot. Testar geração sem duplicação em estruturas/container providers presentes no pack.

## 10. Testes/regressões obrigatórios
1. Equip/unequip de cada família de slot e ausência de modifier fantasma.
2. Death/logout/respawn/dimension change com Chorus Totem e toggles.
3. Cloud in a Bottle: single double-jump por ciclo; fall damage correto.
4. Retaliation: um proc por hit elegível, cooldown respeitado.
5. Vampiric Glove: heal uma vez por dano elegível e cap por hit.
6. Pickaxe Heater: drop original não pode coexistir com resultado smeltado duplicado.
7. Fluid walkers: atualização de estado sem desync cliente/servidor.
8. Loot configs: disabled item não deve aparecer por modifier próprio.
9. Dedicated server smoke: render/model classes isoladas.
10. Curios 9.5.1: equip sync e slot tags.

## 11. Evidência
- Modlist física atual: Artifacts 13.2.3, Curios 9.5.1 e Quark 4.1-483.
- Source oficial `ochotonida/artifacts`, branch `1.21.1`, commit exato `eb331973311068a6b5a6392a0c734f7f2a705e2a`; `gradle.properties` declara `mod_version=13.2.3`.
- `ModItems.java` no commit 13.2.3 auditado para as 49 entradas; comparação 13.2.3→13.2.5 confirma que esse arquivo não mudou.
- CurseForge oficial 13.2.3: fixes de Lootr Mimic e trinket slot; releases 13.2.4/13.2.5 são posteriores e não foram promovidas como runtime.
- Datagen NeoForge de loot/tags e componentes/rendering consultados.

> 📚 Contagem source-confirmed: 45 wearables (8 head + 9 necklace + 8 belt + 10 hands + 9 feet + 1 curio) + 4 itens não-wearable = 49 item entries. Nenhum item foi resumido como “etc.”.

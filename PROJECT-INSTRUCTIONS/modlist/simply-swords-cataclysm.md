# Simply Swords: Cataclysm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3cc69db9f0db819d98f7e5f7de9b7c3b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `simplycataclysm-1.0.2+1.21.1+neoforge.jar`, mod id `simplycataclysm`, runtime `1.0.2+1.21.1+neoforge`; Simply Swords 1.70.2, L_Ender's Cataclysm 3.33 e KubeJS 2101.7.2-build.374 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Simply Swords: Cataclysm 1.0.2, Simply Swords 1.70.2, L_Ender's Cataclysm 3.33 e KubeJS estão presentes; Spartan Weaponry: Cataclysm não aparece top-level. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Simply Swords: Cataclysm
- **Arquivo JAR:** `simplycataclysm-1.0.2+1.21.1+neoforge.jar`
- **Versão 1.21.1:** 1.0.2+1.21.1+neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, RPG
- **Função:** Addon Simply Swords ↔ Cataclysm que cria armas de Ignitium, Cursium, Witherite, Ancient Metal e Black Steel com abilities, crafting/smithing e server config próprios.
- **Dependências:** Linha 1.0.x requer Simply Swords >=1.56.0 e L_Ender's Cataclysm >=2.00. Pack satisfaz com Simply Swords 1.70.2-1.21.1 e Cataclysm 3.33. Spartan Weaponry: Cataclysm não foi encontrado top-level.
- **Sobreposição:** Complementa Simply Swords/Cataclysm. Pode cruzar tematicamente outros addons de armas Cataclysm; avaliar por IDs/effects/recipes, não por tema isolado.
- **Compatibilidade/Riscos:** Addon stateful de combate. Riscos: provider API drift, proc/amplifier duplication, Mecha Pulse cooldown bypass, lifesteal double-processing, durability regression, recipe drift, config mismatch e cross-addon status stacking.
- **Observações:** Delta exato 1.0.2 NeoForge: Ignitium, Cursium e Witherite ficaram corretamente unbreakable. Defaults de abilities documentados; config local/KubeJS não foram lidos.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Simply Swords: Cataclysm 1.0.2 e descrição/changelogs 1.0.x.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/simplycataclysm
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Simply Swords: Cataclysm 1.0.2 reconstruído: material weapon families, Blazing Brand, Accursed Rage, Mecha Smite/Pulse, crafting/config, persistence, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `simplycataclysm-1.0.2+1.21.1+neoforge.jar`, mod id `simplycataclysm`, versão `1.0.2+1.21.1+neoforge`. O addon cruza **Simply Swords** com materiais/efeitos de **L_Ender's Cataclysm**, adicionando famílias de armas Ignitium, Cursium, Witherite, Ancient Metal e Black Steel. A decisão vigente **Manter** é preservada.

## 1. Identidade e requisitos
- **Mod:** Simply Swords: Cataclysm.
- **JAR:** `simplycataclysm-1.0.2+1.21.1+neoforge.jar`.
- **Mod id:** `simplycataclysm`.
- **Versão:** `1.0.2+1.21.1+neoforge`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Linha 1.0.x requer:** Simply Swords >=1.56.0 e Cataclysm >=2.00.

O pack satisfaz com Simply Swords **1.70.2-1.21.1** e L_Ender's Cataclysm **3.33**.

## 2. Authority e ownership
- **Simply Swords:** weapon archetypes/base combat integration.
- **Cataclysm:** materiais, effects/identidade de bosses e recursos temáticos.
- **Simply Cataclysm:** itens cross-mod, recipes/upgrades, abilities e config próprios.

O addon não substitui os providers; ele cria conteúdo que depende de ambos.

## 3. Materiais cobertos
O projeto adiciona variantes Simply Swords para:
- Ignitium;
- Cursium;
- Witherite;
- Ancient Metal;
- Black Steel.

Ancient Metal e Black Steel não recebem abilities especiais adicionais segundo a documentação atual; o foco mecânico está em Ignitium, Cursium e Witherite.

## 4. Ignitium — Blazing Brand
Em hit, há chance publicada de 75% de aplicar Blazing Brand por 6 s. Hits consecutivos podem elevar amplifier até 4 (V).

O efeito reduz Armor e Armor Toughness em 20% multiplicado pelo amplifier. O atacante com arma Ignitium também recebe lifesteal inspirado no Ignis; retorno de vida depende de attack speed, amplifier e `lifestealMultiplier` configurável.

Servidor deve calcular aplicação/amplifier/lifesteal uma única vez por hit lógico.

## 5. Cursium — Accursed Rage
Em hit, chance publicada de 50% de aplicar **Accursed Rage** ao usuário por 5.5 s. Hits consecutivos aumentam amplifier até 4 (V).

Enquanto o effect está ativo e o player usa arma Cursium, dano adicional padrão é 1.0 por amplifier; a descrição exemplifica até +5.0 com amplifier 5. O sistema imita o rage meter de Maledictus.

Config pode alterar parâmetros; os números acima são defaults publicados, não config local confirmada.

## 6. Witherite — Mecha Smite
Em hit, Mecha Smite tem chance publicada de 100% de:
- aplicar Wither II por 5 s;
- incendiar o alvo por 5 s;
- conceder Regeneration II por 5 s ao usuário se estiver abaixo de 50% da vida máxima.

Validar stacking com outros Wither/fire effects e heal triggers do pack.

## 7. Witherite — Mecha Pulse
Em hit, chance publicada de 75% de aplicar Pulse Charge por 7 s. Hits consecutivos elevam amplifier até 9 (X).

Ao atingir máximo, o projeto libera shockwave, stuna o alvo por 10 s, adiciona 4.0 de dano, remove Pulse Charge e aplica Pulse Cooldown por 40 s, período em que nova carga não deve acumular.

Esse é um state machine: charge → threshold → pulse → cooldown; packets/hits duplicados não podem disparar duas pulses no mesmo threshold.

## 8. Durabilidade especial e delta 1.0.2
Ignitium, Cursium e Witherite são documentados como fireproof e unbreakable. O changelog **exato da 1.0.2 NeoForge** é um fix para que essas três famílias fiquem **properly unbreakable**.

Portanto dano de item/durability é regression gate direto da build física. Não confundir com config geral: o autor informa que durability/enchantability estão entre os aspectos não configuráveis pelo server config.

## 9. Crafting e smithing
- Ancient Metal, Black Steel e Witherite: crafting normal segundo recipes do addon;
- Ignitium e Cursium: upgrade de armas Netherite com respectivos ingots + upgrade templates em smithing table.

A linha 1.0.1 corrigiu twinblade recipes e recipes de Ignitium/Cursium; esses casos permanecem regression lineage na 1.0.2.

## 10. Server config
O addon possui server config extensa e declara quase todos os parâmetros de abilities configuráveis. Durability e enchantability são exceções citadas pelo autor.

Para alterações fora da config, o upstream sugere datapack/KubeJS para recipes/visibilidade em recipe viewers e scripts de startup para tier stats. O pack contém KubeJS, mas esta ficha não presume customização local sem ler scripts/config.

## 11. Visual e modelo
O addon também contém pequenos tweaks de textura para weapons Netherite e fix de orientation do held model de Greathammer.

Esses são client-facing e não devem afetar reach/damage/hitbox funcional; qualquer diferença de combate deve ser triada na lógica do weapon/provider, não no modelo.

## 12. Compatibilidade opcional com Spartan Weaponry: Cataclysm
O projeto informa que, quando seu addon irmão Spartan Weaponry: Cataclysm está instalado, Simply Cataclysm reutiliza status effects dele para evitar **double-stacking**.

Nenhum JAR Spartan/Spartan Weaponry foi encontrado top-level na modlist atual, portanto essa integração é capacidade upstream, **não path ativo confirmado** neste snapshot.

## 13. Client / Server
Servidor deve ser authority para:
- proc chance;
- effects/amplifier;
- lifesteal/heal;
- extra damage/stun;
- cooldown;
- crafting/smithing;
- item durability state.

Cliente apresenta particles/sounds/model/tooltips. Partícula duplicada não pode equivaler a proc funcional duplicado.

## 14. Persistence e multiplayer
Effects e cooldowns precisam sobreviver/transicionar corretamente entre hits, death e relog conforme semântica do Minecraft/provider. Dois players atacando o mesmo target não podem compartilhar counters/amplifiers do atacante errado.

Recipe/config state é server-wide; cliente com config divergente não deve decidir dano/procs.

## 15. Integrações concretas no pack
- **Simply Swords 1.70.2:** provider de weapon families.
- **L_Ender's Cataclysm 3.33:** provider de materiais/effects/temática.
- **KubeJS 7.2:** disponível para customizações, mas nenhum script foi atribuído sem auditoria própria.
- Outros addons Cataclysm/Simply Swords coexistem; sobreposição de conteúdo deve ser avaliada por item IDs/abilities/recipes, não por tema.

## 16. Riscos técnicos
1. **Provider API drift:** Simply Swords/Cataclysm mudam item/effect APIs.
2. **Proc duplication:** mesmo hit dispara ability duas vezes.
3. **Amplifier off-by-one:** tooltip/config e effect level divergem.
4. **Pulse state duplication:** max charge gera múltiplas shockwaves.
5. **Cooldown bypass:** hit continua acumulando Pulse Charge em cooldown.
6. **Lifesteal over-heal:** cálculo executa duas vezes por event hook.
7. **Durability regression:** 1.0.2 unbreakable fix deixa de funcionar.
8. **Recipe drift:** smithing/twinblade recipes quebram após provider update.
9. **Config/client mismatch:** tooltip visual não representa valores server-side.
10. **Cross-addon stacking:** outro Cataclysm weapon addon aplica effect equivalente em paralelo.

## 17. Matriz de testes
- [ ] Dedicated server inicia com Simply Cataclysm 1.0.2 + Simply Swords 1.70.2 + Cataclysm 3.33.
- [ ] Ignitium Blazing Brand aplica chance/duração/amplifier conforme server config.
- [ ] Armor/Toughness reduction e lifesteal liquidam uma vez por hit.
- [ ] Cursium Accursed Rage escala dano pelo amplifier sem state de outro player.
- [ ] Witherite Mecha Smite aplica Wither/fire e Regeneration condicional corretamente.
- [ ] Mecha Pulse atinge threshold uma vez, stuna/danifica e entra em cooldown.
- [ ] Pulse Cooldown impede nova charge até terminar.
- [ ] Ignitium/Cursium/Witherite permanecem unbreakable — regression 1.0.2.
- [ ] Ancient Metal/Black Steel não recebem ability extra indevida.
- [ ] Smithing Ignitium/Cursium usa template + ingot e preserva recipe correto.
- [ ] Twinblade/weapon recipes permanecem válidos.
- [ ] Multiplayer separa effects/amplifiers por atacante.
- [ ] Config reload/restart mantém servidor como authority.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- Modlist física atual: Simply Cataclysm 1.0.2, Simply Swords 1.70.2 e Cataclysm 3.33.
- CurseForge oficial do projeto: materiais, abilities, crafting, config e requirements 1.0.x.
- Changelog oficial 1.0.2: fix NeoForge para Ignitium/Cursium/Witherite corretamente unbreakable.
- Changelog 1.0.1 usado somente como lineage de recipes/default Accursed Rage.
- **Limite:** config local e scripts KubeJS não foram lidos; defaults publicados não são tratados como valores locais se o servidor os tiver alterado.

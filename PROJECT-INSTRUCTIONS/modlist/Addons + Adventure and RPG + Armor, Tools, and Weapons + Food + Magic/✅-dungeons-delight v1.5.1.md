# Dungeon's Delight

## Propriedades do registro

- **Mod:** Dungeon's Delight
- **Arquivo JAR:** neoforge-dungeonsdelight-1.21.1-1.5.1.jar
- **Versão 1.21.1:** 1.5.1
- **Categoria:** Comida, RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dungeons-delight
- **Função:** Addon de Farmer's Delight que transforma recursos de monstros/dungeons em alimentos, efeitos, weapons/tools e cooking próprio, conectando combate/exploração à culinária.
- **Dependências:** Farmer's Delight 1.3.4 físico; RunicLib 5.0.7 físico; NeoForge 21.1.250 físico. A linha recente requer Farmer's Delight 1.3+ / 1.3.4 para a manutenção atual, RunicLib 5.0.0+ e NeoForge 21.1.219+.
- **Compatibilidade/Riscos:** Farmer's Delight addon com RunicLib required. Riscos: combat scaling, Putrid Scent/effect overlap, XP drain do Dungeon Stove, recipe overlap, dispenser automation e ID migration Tokayaki→Takoyaki.
- **Sobreposição:** Compartilha domínio culinário com outros addons Farmer's Delight, mas possui loop próprio de monster-food/combat; comparar efeitos/recipes concretos antes de qualquer decisão de redundância.
- **Observações:** Distribuição física/release oficial 1.5.1 (`neoforge-dungeonsdelight-1.21.1-1.5.1.jar`), mas o metadata interno reporta 1.5.0. A divergência foi reconciliada pela hierarquia canônica: arquivo/release física prevalece para a versão catalogada.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + release oficial Dungeon's Delight 1.5.1 para NeoForge 1.21.1 + metadata interno 1.5.0 registrado como divergência conhecida.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — distribuição física 1.5.1 reconfirmada; metadata interno 1.5.0 preservado como divergência conhecida. Farmer's Delight 1.3.4, RunicLib 5.0.7 e NeoForge 21.1.250 permanecem compatíveis com os mínimos documentados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-08-27

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #416: JAR `neoforge-dungeonsdelight-1.21.1-1.5.1.jar`, mod id `dungeonsdelight`, distribuição/version pin `1.5.1`, metadata interna `1.5.0`, SHA-1 `1a7fbb14c9844a2124169f0b2a3eb6fbb2b1848e`.

<callout icon="🔎" color="orange_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `neoforge-dungeonsdelight-1.21.1-1.5.1.jar`, mod id `dungeonsdelight`, versão de distribuição `1.5.1` (o metadata interno do mod ainda declara `1.5.0`; a divergência é documentada e a autoridade física/release prevalece). A release atual exige Farmer's Delight 1.3+, NeoForge 21.1.219+ e, pelo relacionamento oficial do projeto/linha 1.4.4+, **RunicLib 5.0.0+**. O pack usa Farmer's Delight 1.3.4, NeoForge 21.1.248 e RunicLib 5.0.7, satisfazendo os mínimos conhecidos.
</callout>
## 1. Identidade e papel
- **Mod:** Dungeon's Delight.
- **JAR físico:** `neoforge-dungeonsdelight-1.21.1-1.5.1.jar`.
- **Mod id:** `dungeonsdelight`.
- **Runtime catalogado:** `1.5.1` pela distribuição física/release oficial; metadata interno reporta `1.5.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor/ecossistema:** Yirmiri / AZURUNE.
- **Ambiente:** Client & Server.
- **Papel:** addon de Farmer's Delight que conecta combate/dungeons a culinária, transformando recursos de monstros em alimentos, equipamentos e efeitos próprios.
## 2. Dependências e contratos mínimos
A linha atual mantém os requisitos conhecidos da 1.5.0 e a 1.5.1 adiciona compatibilidade com Farmer's Delight 1.3.4. Requisitos confirmados:
- **Farmer's Delight 1.3+**;
- **NeoForge 21.1.219+**.
A relação oficial do projeto lista **RunicLib** como Required Dependency; a linha 1.4.4 já estabelece **RunicLib 5.0.0+** como mínimo compatível.
Runtime do pack:
- Farmer's Delight 1.3.4;
- NeoForge 21.1.250;
- RunicLib 5.0.7.
Todos os mínimos conhecidos são atendidos.
## 3. Papel de Farmer's Delight
Farmer's Delight continua authority da infraestrutura culinária base. Dungeon's Delight acrescenta:
- ingredientes monstruosos;
- recipes próprios;
- efeitos/foods;
- ferramentas/equipamentos temáticos;
- estações/integrações próprias.
Sobreposição com outros addons de comida não significa redundância: o eixo de design deste mod é converter exploração e combate em progressão culinária.
## 4. Linha 1.5.0 e manutenção 1.5.1
A **1.5.0 — Lull Garden Update** continua sendo a base funcional das mudanças de armas, foods, experiência, cooking, dispenser behavior e compatibilidade de tooltips/enchantments descritas abaixo.
A distribuição física atual é **1.5.1**, publicada como manutenção da linha 1.5.x. O changelog 1.5.1 confirma:
- compatibilidade com **Farmer's Delight 1.3.4**;
- correção de **Stained Scrap** não sendo dropado por Spawners e Rotten Spawners.
A ficha mantém os deltas funcionais 1.5.0 como comportamento herdado e trata os dois itens acima como regressões específicas da 1.5.1.
## 5. Ricochet Cleaver
A 1.5.0 altera o multiplicador de dano do **Ricochet Cleaver** para **1.33× por bounce consecutivo**.
Isso cria uma curva de dano acumulativa ligada a trajetória/ricochete. Em pack com atributos e combate modificados, o teste precisa separar:
- dano base da arma;
- bônus por ricochete;
- modificadores globais de atributos;
- efeitos de encantamentos/perks.
## 6. Gunk Arrows e Putrid Scent
A release registra:
- **Gunk Arrows** aplicando **Putrid Scent por 15 segundos**;
- trail roxo para o projétil;
- suporte a dispensers.
Isso atravessa combate, efeitos, rendering e automação redstone. O servidor deve ser authority do hit/effect; o trail é apresentação client-side.
## 7. Foods e efeitos
Mudanças 1.5.0 confirmadas:
- **Chloropasta** concede **Nourishment**, não Comfort;
- **Bubblegunk** passa a ter use time de **12 ticks**;
- **Raw Spider Meat** é o nome atual do item anteriormente denominado Spider Meat;
- Raw Spider Meat pode virar **Smoked Spider Meat** por furnace/campfire;
- **Ghast Calamari → Fried Calamari** via smelting/campfire;
- **Sniffer Egg → Soft Serve Sniffer Egg** via campfire.
Essas cadeias devem ser avaliadas junto aos demais addons Farmer's Delight para evitar receitas duplicadas/alternativas que comprimam progressão.
## 8. Rotbulb e Rich Soil
A 1.5.0 estabelece influência de **Rich Soil** sobre o crescimento de **Rotbulb**.
Isso cria uma integração agrícola direta com a infraestrutura de Farmer's Delight. Não assumir taxas/condições específicas além do que a versão publicada comprova; validar growth behavior no runtime.
## 9. Dungeon Stoves e experiência
A release adiciona comportamento em que **Dungeon Stoves drenam experiência em uma área de raio 13 sobre o bloco**.
Essa é uma superfície sensível de multiplayer e economia:
- definir qual jogador é afetado deve ser verificado em runtime;
- drenagem precisa ser consistente em dedicated server;
- múltiplos stoves próximos não devem gerar cobrança inesperada sem entendimento do stacking;
- quests/perks que usam XP precisam considerar esse sink.
## 10. Monster Cooking e recipe viewing
A categoria JEI do **Monster Pot** passa a se chamar **Monster Cooking**. Isso confirma uma camada culinária própria do addon sobre a infraestrutura Farmer's Delight.
Recipe viewers são apresentação; recipes server-side/data continuam authority. Alterações via KubeJS/datapack devem usar IDs reais da 1.5.0.
## 11. Rot and Steel
A 1.5.0 registra várias mudanças para **Rot and Steel**:
- tag `#minecraft:durability_enchantable`;
- capacidade de acender stoves, TNT e creepers;
- suporte a dispenser.
Isso cria interseções com encantamentos, redstone/dispensers e interação com entidades explosivas. Testar em multiplayer, inclusive proteção/claims se houver.
## 12. IDs e migração: Takoyaki
A release renomeia/re-IDa **Tokayaki → Takoyaki** e declara transferência segura de ID.
Esse tipo de mudança é crítico para mundos existentes:
- inventários/containers antigos não devem perder o item;
- recipes/tags/datapacks externos podem continuar referenciando o ID antigo;
- scripts do pack precisam usar o ID atual após confirmar a migração.
## 13. RunicLib como dependency
Dungeon's Delight é o consumer causal que transforma #417 RunicLib em dependency operacional do pack.
Não atribuir gameplay do Dungeon's Delight à RunicLib: a library fornece APIs/utilidades compartilhadas; itens, foods, weapons, effects e stations pertencem a Dungeon's Delight.
## 14. Client/server, dados e lifecycle
Server-authoritative:
- dano/projéteis;
- effects;
- XP drain;
- recipes/cooking;
- dispenser interactions;
- crop/growth state.
Client-facing:
- models/animations;
- Gunk Arrow trail;
- JEI presentation/tooltips.
Lifecycle obrigatório inclui save/restart, chunk unload, item-ID migration e `/reload` de recipes/tags.
## 15. Riscos
1. **Dependency drift:** RunicLib \<5.0.0 é incompatível com a linha recente; pack usa 5.0.7.
2. **Combat scaling:** Ricochet Cleaver pode somar com atributos/perks de forma agressiva.
3. **Effect overlap:** Putrid Scent e outros status podem interagir com mods de resistência/imunidade.
4. **XP economy:** Dungeon Stove cria sink de experiência em área.
5. **Recipe overlap:** muitos addons Farmer's Delight compartilham ingredientes/cooking methods.
6. **ID migration:** scripts/datapacks podem ainda usar Tokayaki antigo.
7. **Dispenser automation:** Rot and Steel/Gunk Arrows precisam comportamento exactly-once.
8. **World/content churn:** addon é ativo e mudanças de balance/content exigem regressão por release.
## 16. Matriz de testes
- [ ] Dedicated server inicia com Dungeon's Delight 1.5.1 + Farmer's Delight 1.3.4 + RunicLib 5.0.7.
- [ ] Ricochet Cleaver aplica progressão de dano por bounce sem double-application de atributos.
- [ ] Gunk Arrow aplica Putrid Scent por 15 s e dispenser dispara corretamente.
- [ ] Chloropasta concede Nourishment; Bubblegunk usa tempo esperado.
- [ ] Smelting/campfire recipes citadas produzem exatamente o output correto.
- [ ] Rich Soil influencia Rotbulb sem growth loop/dupe.
- [ ] Dungeon Stove drena XP no alcance real e multiplayer se mantém consistente.
- [ ] Rot and Steel acende stove/TNT/creeper e funciona via dispenser conforme esperado.
- [ ] Mundo salvo com item antigo/migração Takoyaki não perde stack.
- [ ] `/reload` mantém recipes/tags/JEI consistentes.
- [ ] Compat de tooltips/enchantment descriptions não produz crash quando providers estão presentes/ausentes.
- [ ] Stained Scrap volta a dropar de Spawners/Rotten Spawners conforme o fix 1.5.1.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 17. Evidências e limites
- Modlist física de 17/09/2026: `neoforge-dungeonsdelight-1.21.1-1.5.1.jar`, mod id `dungeonsdelight`, metadata interno `1.5.0`.
- Release oficial 1.5.1: arquivo físico correspondente, compatibilidade com Farmer's Delight 1.3.4 e fix de Stained Scrap em Spawners/Rotten Spawners.
- Relations oficiais: RunicLib Required; linha 1.4.4 estabelece RunicLib 5.0.0+.
- Pack físico: Farmer's Delight 1.3.4, NeoForge 21.1.250 e RunicLib 5.0.7.
- Changelog 1.5.0: Ricochet Cleaver, Gunk Arrow, foods, Rotbulb, Dungeon Stove, Rot and Steel, Monster Cooking e ID migration.
- **Divergência resolvida:** o arquivo/release é 1.5.1, mas o metadata interno permanece 1.5.0. Pela hierarquia do projeto, JAR físico e release oficial prevalecem para a versão catalogada.
- **Limite:** conteúdo histórico não explicitamente sustentado para a linha atual não foi promovido a claim operacional.

# Create Jetpack

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b5b5b2c217c94e07cf
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Jetpack
- **Arquivo JAR:** `create_jetpack-forge-5.2.1.jar`
- **Versão 1.21.1:** 5.2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Exploração, QoL
- **Função:** Jetpack Create alimentado por pressurized air, com flight/hover, Jetpack e Netherite Jetpack, charging cinético, movimento aquático, configs server-side e integração Curios/Cold Sweat.
- **Dependências:** Create 6.0.10 físico. Flight Lib 4.0.36 é jar-in-jar embarcado no host. Create: Curios Jetpack 1.2.0 está instalado como bridge externa; Cold Sweat 2.4.2 e Create: Cold Sweat 1.1.2 são superfícies de integração presentes.
- **Sobreposição:** Create: Curios Jetpack & Backtank 1.2.0 é bridge de equipagem instalada e não substitui o owner de flight. Há overlap de source discovery/equip com Curios, mas Create Jetpack permanece owner do voo e consumo de ar.
- **Compatibilidade/Riscos:** Riscos: double-consumption de pressurized air, perda/dupe de components em upgrades, Curios/chest source concorrente, flight/config desync, regressão de client-only class no dedicated server, underwater/Elytra stacking, Cold Sweat double-count e Flight Lib jar-in-jar drift.
- **Observações:** JAR físico `create_jetpack-forge-5.2.1.jar`, mod id `create_jetpack`, runtime 5.2.1. Apesar de `forge` no filename, a file page oficial identifica Loader NeoForge 1.21.1, Release, 31/07/2026. 5.2.1 corrige client-only classes carregadas por config screen no servidor. Flight Lib 4.0.36 é filho jar-in-jar.
- **Procedência:** modlist.txt física atual de 09/09/2026 + metadata runtime/jar-in-jar + CurseForge oficial Create Jetpack 5.2.1 + repositório oficial PssbleTrngle/CreateJetpack, branch main/neoforge/1.21.x.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-jetpack
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê completo 5.2.1; pressurized-air flight/hover, server config/sync, Jetpack/Netherite Jetpack, Flight Lib jar-in-jar, Curios/Cold Sweat e regression 5.2.1 catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🚀 **Identidade física confirmada:** `create_jetpack-forge-5.2.1.jar`, mod id `create_jetpack`, runtime `5.2.1`. Apesar de `forge` no filename, a publicação oficial deste mesmo arquivo é **NeoForge 1.21.1 Release 5.2.1**, de 31/07/2026, Client & Server.

## 1. Papel e authority
Create Jetpack transforma o conceito de Backtank do Create em equipamento de voo alimentado por **pressurized air**. **Create 6.0.10** continua owner da lógica fundamental de Backtank/air e enchimento cinético; Create Jetpack owns o item de jetpack, regras de voo, hover, integração de controls, configs e recipes de upgrade.

## 2. Conteúdo principal
O source oficial da linha NeoForge 1.21.x confirma dois tiers de equipamento/bloco: **Jetpack** e **Netherite Jetpack**. Ambos são placeable como os backtanks e integram o modelo de armazenamento de ar do Create. O Jetpack normal deriva do upgrade do Copper Backtank usando brass; a linha Netherite fornece o tier superior.

## 3. Pressurized air
`JetpackItem` estende `BacktankItem` e usa `BacktankUtil`. O source valida disponibilidade de ar via `BacktankUtil.getAllWithAir`, lê o saldo e consome a superfície do backtank periodicamente durante flight actions.
Consequência arquitetural: o addon não deve possuir um segundo combustível paralelo. O mesmo ecossistema de pressurized air pode continuar alimentando outros equipamentos Create, como Extendo-Grip, conforme a documentação oficial.

## 4. Charging cinético
Assim como o Backtank, o jetpack é recarregado ao ser **colocado como bloco e receber rotational force**. Create permanece authority da cinética e do enchimento; o jetpack preserva componentes/state quando alterna entre item equipado e bloco colocado.
Break/place/recharge precisa conservar exatamente o saldo de ar e dados do item.

## 5. Flight e hover
O projeto fornece flight normal e **hover mode**. O source usa controles toggle para active/hover e configura server-side para horizontal speed, vertical speed, acceleration e hover descend speed.
Movimento efetivo precisa ser validado/servido de forma autoritativa; partículas/HUD não podem conceder velocidade independentemente do servidor.

## 6. Consumo de ar configurável
O `ServerConfig` atual expõe `air.seconds_per_tank` e `air.seconds_per_tank_hover`. Defaults do source são 450 s para uso normal e 900 s para hover/down. Esses são defaults de source, não garantia da config runtime do pack.
O consumo depende da action; qualquer integração que também debite Backtank air precisa evitar double-consumption.

## 7. Velocidade e limites
O source expõe configurações server-side para:
- `speed.horizontal`;
- `speed.vertical`;
- `speed.acceleration`;
- `speed.hover_descend`;
- `speed.swim_modifier`;
- `features.elytra_boost`;
- `heightAboveGroundLimit`.

Esses parâmetros tornam o mod diretamente relevante a balanceamento de mobilidade. A config runtime do usuário não foi lida; valores efetivos permanecem a confirmar.

## 8. Underwater movement
A documentação oficial confirma boost durante sprint-swimming e que hover evita afundamento. O source possui `swimModifier` server config. Isso interage com outros mods de movimentação/água do pack; não adicionar segundo multiplicador sem teste concreto.

## 9. Elytra behavior
Quando existe uma solução que permite Elytra junto da chestplate, o jetpack pode fornecer **firework-like boost** enquanto o jogador pressiona `UP`. O source expõe `elytraBoost`.
Nenhum mod top-level com nome Elytra Slot foi encontrado na busca física usada para esta ficha; portanto essa integração não é assumida como runtime ativa apenas por estar suportada upstream.

## 10. Curios
O próprio source reconhece `CuriosSource` como fonte válida de equipamento e o README documenta suporte por tag/datapack. No pack está instalado **Create: Curios Jetpack & Backtank 1.2.0**, que está fora do escopo desta edição porque seu estado no Notion é `Integrado ao Github`.
Essa bridge torna Curios uma integração concreta do runtime. O main mod continua owner do voo; o addon Curios apenas torna o jetpack encontrável/equipável na superfície de slots.

## 11. Flight Lib jar-in-jar
A modlist física lista dentro do JAR do Create Jetpack o filho **`/META-INF/jarjar/flightlib-1.21.1-neoforge-4.0.36.jar`**, mod id `flightlib`, runtime 4.0.36. Ele é dependência empacotada do host, não entrada top-level.
O source do Jetpack usa a API Flight Lib (`IJetpack`, flight actions e equipment sources). Qualquer falha em controls/flight sourcing pode vir desse boundary, mas o catálogo deve manter Flight Lib subordinado ao JAR host.

## 12. Enchantments
A documentação oficial confirma suporte a **Capacity**. O source também possui configuração de allow/deny de enchantments por lista e modo blacklist. `JetpackItem.supportsEnchantment` aplica esse filtro server config.
Não assumir que qualquer enchantment de chest armor seja válido; usar o provider/config real.

## 13. Cold Sweat
O source NeoForge 1.21.x inclui data específica de **Cold Sweat**, inclusive tags de insulator/drains-backtank e definição para Netherite Jetpack. O pack físico contém Cold Sweat 2.4.2 e Create: Cold Sweat 1.1.2.
Isso é integração concreta. A semântica térmica final continua pertencendo ao Cold Sweat; Jetpack apenas fornece seus data hooks. Testar consumo de ar/insulation sem double-count térmico.

## 14. Recipes e preservação de componentes
O source registra recipes para Jetpack, Netherite Jetpack e upgrades, incluindo uma classe `CopyComponentsMechanicalCraftingRecipe`. Isso sinaliza explicitamente que upgrade precisa preservar data components relevantes do equipamento de origem.
Air/enchantments/custom data não podem ser descartados ou duplicados durante crafting/upgrade. Validar a recipe real da build antes de automatização massiva.

## 15. Client/server e networking
A build 5.2.1 corrige especificamente **client-only classes being loaded for config screen on server (#221)**. Portanto dedicated-server cold boot é regression gate obrigatório.
O source separa entrypoint cliente e comum e possui sync de server config. HUD/particles/controls são cliente; parâmetros de voo/consumo precisam convergir com a config autoritativa do servidor.

## 16. Lifecycle e multiplayer
Testar equip/unequip, Curios↔chest, takeoff/landing, hover toggle, underwater, death/respawn, dimension change, relog e restart. O item deve manter um único saldo de ar e um único flight state por jogador.
Dois jogadores não podem compartilhar indevidamente air source, toggle state ou config local. Config server-side deve sincronizar para todos os clientes.

## 17. Riscos
1. Double-consumption de pressurized air por múltiplos hooks/integrations.
2. Upgrade perde ou duplica air/data components/enchantments.
3. Curios e chest source apontam para estados concorrentes.
4. Flight/hover diverge entre client prediction e servidor.
5. Config server não sincroniza e clientes usam velocidades distintas.
6. Client-only config screen volta a carregar no dedicated server — regressão 5.2.1.
7. Underwater modifier empilha com outro mod de movimento.
8. Elytra boost ativa em condição incompatível.
9. Cold Sweat integration drena/aplica efeitos duas vezes.
10. Flight Lib jar-in-jar drift quebra controls/source discovery.
11. Height limit ou speeds configurados tornam mobilidade incompatível com progressão do pack.

## 18. Matriz de testes
- [ ] Dedicated server inicia com Create Jetpack 5.2.1 sem client-class crash.
- [ ] Jetpack e Netherite Jetpack carregam por rotational force e preservam saldo após break/place.
- [ ] Voo normal consome pressurized air exatamente uma vez.
- [ ] Hover alterna corretamente e respeita consumo/config.
- [ ] Extendo-Grip/outro consumidor Create continua encontrando air source sem double debit.
- [ ] Upgrade Copper Backtank→Jetpack preserva componentes esperados.
- [ ] Upgrade para Netherite preserva state esperado.
- [ ] Curios bridge equipada funciona sem item ghost/duplo source.
- [ ] Underwater sprint e hover respeitam modifier sem stacking anormal.
- [ ] Config server sincroniza velocidades/consumo para dois clientes.
- [ ] Death/relog/dimension/restart não duplicam item, air ou flight toggle.
- [ ] Cold Sweat integration não double-counta insulation/drain.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências e limites
A modlist física confirma `create_jetpack-forge-5.2.1.jar`, runtime 5.2.1 e Flight Lib 4.0.36 jar-in-jar. CurseForge oficial confirma que o arquivo é NeoForge 1.21.1 Release, Client & Server, e o fix 5.2.1 de classes client-only no servidor. O repositório oficial `PssbleTrngle/CreateJetpack`, branch `main/neoforge/1.21.x`, confirma Jetpack/Netherite Jetpack, BacktankUtil, Flight Lib, CuriosSource, configs, Cold Sweat data e component-preserving recipe. O branch é atual da linha 1.21.x, não uma tag imutável da release; comportamento final permanece sujeito ao runtime físico/config do pack.

> 🔒 Boundary canônico: **Create owns pressurized air e cinética; Create Jetpack owns voo/hover e seus upgrades; Flight Lib é dependência interna; Curios/Cold Sweat são providers externos integrados**. Air state único e config server-authoritative são os gates centrais.
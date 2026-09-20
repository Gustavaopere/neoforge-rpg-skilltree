# Create: Jetpack Curios

## Propriedades do registro

- **Mod:** Create: Jetpack Curios
- **Arquivo JAR:** create_jetpack_curios-1.2.0-neoforge-1.21.1.jar
- **Versão 1.21.1:** 1.2.0
- **Categoria:** Compat, QoL, Tecnologia
- **Função:** Bridge de equipagem que permite usar Create Jetpack e backtanks compatíveis no slot Curios de costas, mantendo sua função enquanto libera o chest slot para armor.
- **Dependências:** Create + Curios API na variante NeoForge. Create Jetpack é opcional em Minecraft 1.20+ para a bridge de backtanks do Create; quando presente, seus jetpacks também são suportados.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Bridge stateful de slot/equipamento. Riscos: item simultaneamente em chest e Curios, modifiers/proteção duplicados, consumo de ar/combustível contado duas vezes, render duplicado e incompatibilidade de slot topology. Equipado via Curios, o item continua funcional mas não concede protection bonus do chest slot.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-curios-jetpack
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_jetpack_curios` 1.2.0 + CurseForge oficial revalidado em 20/09/2026; 1.2.0 permanece a release mais recente para NeoForge 1.21.1.
- **Observações:** mod id `create_jetpack_curios`; runtime 1.2.0; Client & Server. Suporta Create Jetpack e backtanks no back slot Curios. Quando equipados por Curios, continuam funcionais mas não fornecem bônus de proteção do chest slot.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — lote físico #145: create_jetpack_curios-1.2.0-neoforge-1.21.1.jar / 1.2.0 confirmados; overlap funcional direto com Curios Backtank 1.0.1 nos backtanks base permanece documentado.
- **Decisão:** Manter
- **Histórico da decisão:** Decisão formal `Manter` registrada em 22/08/2026. A justificativa histórica dizia que Jetpack Curios e Curios Backtank não eram redundantes. Na revalidação de 12/09/2026, o upstream da própria build 1.2.0 confirmou que Jetpack Curios também cobre os backtanks base do Create; portanto existe overlap funcional direto com Create: Curios Backtank 1.0.1. A decisão `Manter` foi preservada, mas a redundância deve ser reavaliada separadamente e não tratada como inexistente.
- **Sobreposição:** Overlap funcional direto com Create: Curios Backtank 1.0.1 nos backtanks base do Create: ambos habilitam uso via Curios. Create SA Curios Jetpacks cobre outro provider. Tratar o primeiro par como possível redundância/duplo hook, não como incompatibilidade provada; Curios continua authority do slot e Create/Create Jetpack da função do equipamento.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs
> 🎒 Versão física confirmada: `create_jetpack_curios-1.2.0-neoforge-1.21.1.jar`, mod id `create_jetpack_curios`, runtime `1.2.0`. Decisão formal do pack: **Manter** desde 22/08/2026.

## 1. Papel e authority
Create: Jetpack Curios é uma **bridge de equipagem**. Ela não implementa um segundo jetpack, backtank ou inventário de acessórios: Curios controla o slot; Create/Create Jetpack controlam a função do item; a bridge faz o equipamento funcionar quando colocado no back slot.
## 2. Back slot Curios
Na variante NeoForge, o item compatível pode ser equipado no slot Curios de costas. O mesmo stack não deve existir simultaneamente no chest slot e no Curios slot por sincronização indevida.
Integrações externas devem consultar o equipamento efetivo em vez de assumir que jetpack/backtank só funciona quando ocupa o chest slot vanilla.
## 3. Jetpack + chestplate
O objetivo central da bridge é permitir **jetpack e chestplate simultaneamente**. Isso separa mobilidade do slot de armor, mas não significa somar proteção do jetpack como se ele ainda estivesse equipado no peito.
## 4. Protection bonus
A documentação oficial é explícita: jetpack/backtank em Curios continua funcional, porém **não concede protection bonus** associado à equipagem como chest armor.
Armor calculations não devem contar uma cópia lógica do item no chest slot apenas para preservar seus efeitos funcionais.
## 5. Create backtanks
Em Minecraft 1.20+, Create Jetpack é opcional para o uso básico da bridge: backtanks do **Create base** também recebem compatibilidade Curios. Isso explica por que a dependência funcional não pode ser modelada como “Create Jetpack sempre obrigatório”.
## 6. Create Jetpack
Quando Create Jetpack está presente, seus jetpacks podem usar a mesma bridge. Thrust/hover/resource logic continuam pertencendo ao Create Jetpack; esta página não inventa consumo, velocidade, capacidade ou keybinds do mod-base.
## 7. Recursos e consumo
Backtank air ou outro recurso consumido pelo equipamento deve ser debitado exatamente uma vez pelo provider original. A bridge não deve criar um segundo contador por estar observando um slot alternativo.
Death/reconnect/equip transitions precisam preservar o state real do stack sem reset ou duplicação.
## 8. Render
O item pode precisar ser renderizado na posição correta quando equipado no back slot. Render é client presentation; não pode criar um segundo equipamento funcional ou modifier server-side.
Coexistência com Cosmetic Armor, player-model mods e first-person render deve ser validada por double-render/clipping, não por presumir conflito apenas pela presença.
## 9. Coexistência com outras bridges
O catálogo possui outras bridges relacionadas a backtanks/jetpacks. O upstream atual da própria build 1.2.0 confirma que **Create: Jetpack Curios cobre tanto jetpacks do Create Jetpack quanto backtanks base do Create**. Portanto:
- Create: Jetpack Curios: jetpacks do Create Jetpack + backtanks Create;
- Create: Curios Backtank 1.0.1: também cobre backtanks base do Create via Curios, criando **overlap funcional direto** com esta bridge;
- Create SA Curios Jetpacks: atende outro provider de jetpack.
O overlap Jetpack Curios↔Curios Backtank é uma possível redundância/duplo hook e deve ser testado por duplicate lookup, consumo e render; não é incompatibilidade provada nem autorização automática para remoção.
## 10. Client/server e sync
A distribuição é Client & Server. Equip state e recursos que afetam gameplay devem convergir no servidor; GUI/render do slot é client-facing.
Um cliente não pode fabricar funcionalidade de jetpack apenas movendo visualmente um item para um slot que o servidor não reconheceu.
## 11. Lifecycle
Validar equip/unequip, swap chest↔Curios, death/respawn, dimension change, disconnect/reconnect, server restart e alterações da topologia Curios.
Cached lookup do item equipado deve ser invalidado em toda transition relevante.
## 12. Riscos
1. Stack duplicado entre chest e Curios.
2. Armor/protection contado duas vezes.
3. Air/fuel consumido duas vezes.
4. Jetpack parar de funcionar após reconnect.
5. Render duplicado/clipping.
6. Outra bridge agir sobre o mesmo item.
7. Curios slot ausente/renomeado deixar referência stale.
## 13. Matriz de testes
1. Dedicated server + cliente com Create/Curios/bridge.
2. Backtank Create no back slot: função normal e sem protection bonus extra.
3. Create Jetpack no back slot, se provider instalado.
4. Chestplate funcional simultaneamente.
5. Equip/unequip e swap rápido sem dupe.
6. Consumo de recurso exactly once.
7. Death/respawn e dimension change.
8. Disconnect/reconnect/restart.
9. Coexistência com outras bridges apenas nos itens que cada uma suporta.
10. Cosmetic/player-render stack sem double render.
## 14. Evidência
- modlist física 08/09/2026: 1.2.0;
- CurseForge oficial: NeoForge 1.21.1, Client & Server;
- documentação oficial: jetpack e backtanks no Curios/Trinkets back slot; função preservada; sem protection bonus; Create Jetpack opcional em 1.20+ para backtanks;
- histórico do Notion: decisão formal `Manter` em 22/08/2026.
> 🔒 Boundary canônico: **Curios decide onde o item está equipado; o provider original decide o que ele faz; a bridge só conecta os dois**.

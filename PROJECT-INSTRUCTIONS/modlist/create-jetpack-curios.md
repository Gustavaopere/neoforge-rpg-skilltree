# Create: Jetpack Curios

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81348b93d8f19ac5b8da  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: Jetpack Curios
- **Arquivo JAR:** `create_jetpack_curios-1.2.0-neoforge-1.21.1.jar`
- **Versão 1.21.1:** `1.2.0`
- **Categoria:** Compat; QoL; Tecnologia
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-curios-jetpack
- **Função:** Bridge de equipagem que permite usar Create Jetpack e backtanks compatíveis no slot Curios de costas, mantendo sua função enquanto libera o chest slot para armor.
- **Dependências:** Create + Curios API na variante NeoForge. Create Jetpack é opcional em Minecraft 1.20+ para a bridge de backtanks do Create; quando presente, seus jetpacks também são suportados.
- **Compatibilidade/Riscos:** Bridge stateful de slot/equipamento. Riscos: item simultaneamente em chest e Curios, modifiers/proteção duplicados, consumo de ar/combustível contado duas vezes, render duplicado e incompatibilidade de slot topology. Equipado via Curios, o item continua funcional mas não concede protection bonus do chest slot.
- **Sobreposição:** Não duplica Create: Curios Backtank nem Create SA Curios Jetpacks: cada bridge atende itens/providers diferentes. Curios continua authority do slot; Create/Create Jetpack continuam authority da função do equipamento.
- **Observações:** mod id `create_jetpack_curios`; runtime 1.2.0; Client & Server. Suporta Create Jetpack e backtanks no back slot Curios. Quando equipados por Curios, continuam funcionais mas não fornecem bônus de proteção do chest slot.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_jetpack_curios` 1.2.0 + CurseForge oficial da release NeoForge 1.21.1 e documentação do projeto; decisão histórica `Manter` preservada.
- **Histórico da decisão:** Decisão formal `Manter` registrada em 22/08/2026. A bridge foi mantida porque permite usar Create Jetpack/backtanks no slot Curios e não é redundante com Create: Curios Backtank ou Create SA Curios Jetpacks, que atendem conjuntos distintos. Em 08/09/2026, a decisão e a data foram preservadas e a ficha reconstruída ao padrão técnico.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — Curios-slot authority, jetpack/backtank behavior, armor-protection boundary, resource consumption, sync/render lifecycle e coexistência com outras bridges catalogados.
- **Data da última decisão:** 2026-08-22.

## Dossiê operacional — padrão Alex's Mobs

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
O catálogo possui outras bridges relacionadas a backtanks/jetpacks. A regra é por **provider/item coberto**:
- Create: Jetpack Curios: jetpacks do Create Jetpack + backtanks Create;
- Create: Curios Backtank: bridge própria para seu escopo documentado;
- Create SA Curios Jetpacks: bridge para outro provider de jetpack.

Não remover uma apenas pelo nome semelhante.

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

# Cosmetic Armor Reworked

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8184a4c4df968d6c88aa
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Cosmetic Armor Reworked
- **Arquivo JAR:** `cosmeticarmorreworked-1.21.1-v1-neoforge.jar`
- **Versão 1.21.1:** 1.21.1-v1-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Separa aparência e função da armadura: o conjunto normal mantém proteção/atributos enquanto slots cosméticos independentes determinam o que é renderizado, com toggles de visibilidade e utilidades próprias.
- **Dependências:** Client & Server em multiplayer. Nenhuma hard dependency adicional relevante foi confirmada para o artefato top-level atual. Não confundir seus slots cosméticos com Curios ou com o slot funcional vanilla.
- **Sobreposição:** Pode cruzar outros armor-render/cosmetic systems, mas não é Curios e não substitui o equipamento funcional. Qualquer integração deve preservar stats do armor real e tratar cosmetic slots como apresentação.
- **Compatibilidade/Riscos:** Riscos em sync de cosmetic slots, render layers, armor models de outros mods, death/drop/keep config, skin toggles e mods que também substituem armor rendering. Cosmetic slots não devem conceder stats. Client pode ocultar render, mas não alterar proteção server-side.
- **Observações:** mod id `cosmeticarmorreworked`; runtime preservado integralmente: `1.21.1-v1-neoforge`. Multiplayer requer client e server. Cosmetic armor é display-only; itens cosméticos normalmente dropam na morte, com config para manter. Inclui toggles de skin/armor, `/clearcosarmor [player]`, `/coshat` e API para integrações.
- **Procedência:** modlist.txt física atual de 08/09/2026 (595 top-levels) + runtime `1.21.1-v1-neoforge` + CurseForge oficial Cosmetic Armor Reworked para NeoForge 1.21.1 + documentação oficial do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cosmetic-armor-reworked
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Cosmetic Armor Reworked `1.21.1-v1-neoforge`, dual-slot authority, render/stat separation, death/drop, visibility, API e lifecycle confirmados no QC global #111. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Cosmetic Armor Reworked `1.21.1-v1-neoforge` foi reconfirmado fisicamente e reconstruído ao padrão técnico. A presença do QoL visual não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🛡️ Versão física confirmada: `cosmeticarmorreworked-1.21.1-v1-neoforge.jar`, mod id `cosmeticarmorreworked`, runtime `1.21.1-v1-neoforge`. Em multiplayer, o mod requer presença em **cliente e servidor** para manter slots/state cosméticos coerentes.

## 1. Papel e authority
Cosmetic Armor Reworked separa **armadura funcional** de **armadura exibida**. O equipamento normal continua fornecendo armor/toughness/enchants/atributos e demais efeitos; os slots cosméticos definem apenas a aparência renderizada.
Regra crítica: item colocado no slot cosmético não deve conceder uma segunda cópia de seus stats ou efeitos.

## 2. Dual armor sets
O jogador mantém dois conjuntos conceitualmente distintos:
- armor real/funcional;
- cosmetic armor/display-only.
A interface pode permitir copiar/organizar aparência, mas o settlement de proteção e atributos continua no conjunto funcional.

## 3. Cosmetic slots
Slots cosméticos armazenam itens reais para renderização. Inventário/state desses slots precisa ser persistido e sincronizado; não é apenas uma skin temporária do cliente.
Outros mods não devem tratar o conteúdo cosmético como item equipado para triggers de combat/attributes sem integração explícita.

## 4. Render de armaduras modded
Itens de armor de outros mods podem possuir models/render layers próprios. Cosmetic Armor precisa renderizar a aparência escolhida sem acionar novamente gameplay hooks do equipamento visual.
Mods que substituem player/armor renderer são a principal superfície de conflito visual.

## 5. Visibility e skin toggles
A documentação oficial oferece toggles para mostrar/ocultar armor cosmético, armor normal e partes de skin suportadas. Esses toggles são apresentação.
Ocultar uma peça funcional não remove proteção; mostrar uma cosmética não concede stats.

## 6. PVP/client disable
O projeto permite desativar a exibição cosmética no cliente, inclusive para contextos como PVP, de modo que o usuário possa ver a armadura funcional real.
Essa preferência não pode alterar inventário/armor state do outro jogador no servidor.

## 7. Death/drop behavior
Por padrão, itens nos cosmetic slots participam do fluxo de morte/drop. A documentação registra opção de config para **manter cosmetic armor após a morte**.
Esse comportamento precisa settlement uma vez: não pode dropar a peça e também preservá-la no slot após respawn.

## 8. `/clearcosarmor`
O comando `/clearcosarmor [player]` fornece rota administrativa para limpar cosmetic slots. A operação deve agir sobre o inventário cosmético real e respeitar permissões/target corretos.
Não usar como fluxo automático sem decidir o destino dos itens, pois limpeza pode ser destrutiva dependendo da implementação/config.

## 9. `/coshat`
O projeto também expõe `/coshat`, permitindo usar item como aparência de cabeça conforme o contract do mod. Isso permanece display/cosmetic state; não deve importar atributos/efeitos funcionais do item para o head slot real.

## 10. API
Existe API para outros mods integrarem com o sistema cosmético. Integrações devem usar essa superfície em vez de duplicar storage/sync dos slots por NBT paralelo.
Nomes concretos de classes/métodos só devem ser usados após pin do source/JAR da build atual.

## 11. Relação com Curios e outros slot systems
Curios adiciona/equipa accessories funcionais em slots próprios; Cosmetic Armor Reworked controla slots de aparência de armor. São authorities diferentes.
Não migrar item Curios para cosmetic armor nem contar cosmetic slot como Curios equip event sem bridge explícita.

## 12. Client/server e multiplayer
- cosmetic inventory/state e death/drop: servidor/common;
- armor stats/effects: provider funcional/servidor;
- render, visibility e skin toggles: cliente;
- sync de cosmetic slots: cliente ↔ servidor.
Clientes precisam concordar sobre item cosmético armazenado mesmo quando cada cliente decide ocultar sua renderização localmente.

## 13. Lifecycle
Validar equip/unequip cosmético, troca de armor funcional, death/respawn, keep/drop config, dimension change, disconnect/reconnect, server restart, model/resource reload e player clone.
Nenhuma peça deve duplicar, desaparecer ou reaparecer em dois inventories após transições.

## 14. Riscos
1. Cosmetic slot conceder stats indevidos.
2. Armor funcional oculto visualmente ser confundido com unequip.
3. Death dropar e preservar o mesmo item.
4. Sync client/server perder cosmetic inventory.
5. Armor renderer de outro mod conflitar/double-render.
6. Skin toggle ocultar camada errada.
7. `/clearcosarmor` causar perda inesperada de item.
8. Curios/outro slot system ser tratado como mesma authority.
9. Client PVP toggle alterar lógica server-side por engano.

## 15. Matriz de testes
1. Dedicated server boot + cliente com o mod.
2. Armor funcional A + cosmetic armor B: stats permanecem de A.
3. Trocar/remove cosmetic item sem alterar armor stats.
4. Armor modded com model custom em cosmetic slot.
5. Visibility/skin toggles localmente.
6. Segundo cliente observar cosmetic state sincronizado.
7. Death com keep cosmetic off: drop exatamente uma vez.
8. Death com keep cosmetic on: preservar sem drop/dupe.
9. Dimension change/reconnect/restart com slots ocupados.
10. `/clearcosarmor` e `/coshat` em ambiente de teste.
11. Curios e outros render/armor mods coexistindo.

## 16. Evidência
- modlist física atual: `cosmeticarmorreworked-1.21.1-v1-neoforge.jar`, runtime igual ao filename version string;
- CurseForge oficial: dois conjuntos de armor, cosmetic display-only, Client & Server para multiplayer;
- documentação oficial: death/drop config, visibility/skin toggles, client disable, `/clearcosarmor`, `/coshat` e API;
- changelog da build: atualização para Minecraft 1.21.1 NeoForge.

> 🎭 Boundary canônico: **armor funcional fornece stats; cosmetic armor fornece aparência**. Nunca liquidar atributos/efeitos duas vezes por causa do item exibido.

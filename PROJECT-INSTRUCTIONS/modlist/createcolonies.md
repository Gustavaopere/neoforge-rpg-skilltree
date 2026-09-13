# CreateColonies

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d9b7c1edca17c22d84
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** CreateColonies
- **Arquivo JAR:** `createcolonies-2.0.6.jar`
- **Versão 1.21.1:** 2.0.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia, Automação
- **Função:** Bridge Create↔Structurize/MineColonies que corrige requisitos/placement de componentes Create dentro de blueprints e adiciona interoperabilidade de clipboard e conversão Create schematic↔Structurize blueprint.
- **Dependências:** Create + Structurize/MineColonies. Build 2.0.6 é Client & Server para NeoForge 1.21.1. O mod não adiciona citizens que usam Create nem transporte de citizens por trains.
- **Sobreposição:** Comparar com outros bridges Create↔MineColonies somente por feature concreta. CreateColonies owns repairs de blueprint/requirements e suas ferramentas de conversão; não é um sistema de logística completa ou citizen automation.
- **Compatibilidade/Riscos:** Atua na interpretação/construção de blueprints: material requirements, rotação/mirror, rail geometry, belts, bogeys, deployers e stations. Bridges similares podem disputar o mesmo placement. O upstream declara train signals ainda problemáticos e explicitamente não oferece citizen train travel/Create-machine worker.
- **Observações:** JAR físico `createcolonies-2.0.6.jar`; runtime 2.0.6. Publicação oficial usa filename `CreateColonies-1.21.1-2.0.6.jar`; a diferença de nome do arquivo empacotado é preservada pela autoridade física. Features oficiais: repairs Rails/Belts/Bogeys/Deployers/Train Stations, clipboard↔Builder's Hut e Schematic Workbench bidirecional.
- **Procedência:** Modlist física canônica de 08/09/2026, 600 top-levels + runtime CreateColonies 2.0.6 + CurseForge oficial do projeto/build 2.0.6 e documentação funcional atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/createcolonies
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — Create↔Structurize/MineColonies blueprint authority, rail/belt/bogey/deployer/station placement repairs, clipboard interoperability, schematic workbench conversion e lifecycle 2.0.6 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, CreateColonies 2.0.6 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. A utilidade do bridge não foi convertida automaticamente em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🏗️ Versão física confirmada: `createcolonies-2.0.6.jar`, runtime `2.0.6`, NeoForge 1.21.1. CreateColonies é um **bridge de blueprint/construção** entre Create e Structurize/MineColonies.

## 1. Papel e authority
CreateColonies adapta como blocos Create são representados, requeridos e colocados por blueprints Structurize/MineColonies. Create continua authority do bloco/contraption final; Structurize/MineColonies continua authority do blueprint/build process; o bridge traduz requisitos e placement.

## 2. Rails
O projeto corrige colocação de rails curvos/inclinados e os materiais exigidos para eles. Rotation/mirroring de blueprint precisa resultar na geometria Create correta sem cobrar item impossível ou gerar rail inválido.

## 3. Belts
Corrige crash e requisitos de shafts/belt na construção por blueprint. O builder não deve consumir um assembly impossível nem perder connections ao concluir o placement.

## 4. Bogeys
A bridge corrige o requisito indevido de Train Casing ao construir bogeys. O blueprint deve pedir materiais realmente obtíveis e criar o block state esperado pelo Create.

## 5. Deployers
Deployers em blueprint precisam considerar o item segurado. O bridge corrige esse requirement; item held é parte do state funcional e não pode ser omitido ou duplicado na conversão.

## 6. Train Stations
CreateColonies corrige coupling com track quando schematic/blueprint é rotacionado ou espelhado. Regression gate: station construída deve reconhecer a track correta sem exigir reposicionamento manual.

## 7. Clipboard ↔ Builder's Hut
Sneak+interact com Builder's Hut ou mine usando clipboard do Create pode preencher a clipboard com os itens ainda necessários para a build atual. Essa lista é leitura do request/build state de MineColonies; clipboard não cria o pedido nem fornece os materiais.

## 8. Schematic Workbench
O **Schematic Workbench** converte Create schematic em Structurize blueprint e também no sentido inverso. Conversão deve preservar geometria/state suportado sem criar conteúdo que nenhum dos formatos consegue representar.

## 9. Blueprint→schematic
A documentação descreve uso do preview aberto pelo Build Tool para selecionar o blueprint e um empty schematic para a saída. O preview é seleção/contexto; a conversão final precisa usar os dados reais do blueprint.

## 10. Schematic→blueprint
Schematic items entram no workbench e nome/path de destino é indicado com Scan Tool; se não houver nome, ele pode ser inferido do schematic. Nome/path são metadata de destino, não authority de conteúdo.

## 11. Limites explícitos
O upstream declara que o mod **não** faz citizens usarem trains, não adiciona citizen que crafta via máquinas Create e não resolve train signals que não se colocam corretamente. Esses itens não devem ser prometidos pelo catálogo como feature atual.

## 12. Train signals
A documentação registra que signals podem aparecer inválidos mesmo quando rail foi colocado antes e não renderizar o indicador esperado. Isso permanece risco conhecido; não inventar fix em 2.0.6 sem changelog específico.

## 13. Bridges concorrentes
Outro addon Create↔MineColonies pode tocar requirements/placement do mesmo block. Duas bridges não devem ambas reescrever o mesmo blueprint state sem ordem/policy clara, sob risco de material mismatch ou state corruption.

## 14. Client/server e multiplayer
Build requirements, conversion e placement que alteram mundo são server/common. Preview, clipboard display e GUI são client-facing. Em colônia multiplayer, permissions/ownership de MineColonies continuam sendo respeitados pelo provider.

## 15. Lifecycle
Validar blueprint load, rotation/mirror, builder restart, chunk unload, server restart, schematic conversion, colony build cancellation/restart e update isolado de Create/Structurize/MineColonies.

## 16. Riscos
1. Material requirement incorreto consumir item impossível/extra.
2. Rotation/mirror quebrar rail/station state.
3. Belt/bogey/deployer perder state.
4. Conversão schematic↔blueprint perder NBT suportado.
5. Clipboard ficar stale em relação à build.
6. Duas bridges reescreverem o mesmo block.
7. Train signal permanecer inválido.
8. Feature futura ser confundida com feature implementada.

## 17. Matriz de testes
1. Dedicated server boot com Create + Structurize/MineColonies.
2. Blueprint com rail reto/curvo/inclinado.
3. Belt com shafts e material requirements.
4. Bogey sem Train Casing indevido.
5. Deployer com held item.
6. Train Station em blueprint rotacionado e espelhado.
7. Clipboard em Builder's Hut com lista de materiais pendentes.
8. Schematic→blueprint e blueprint→schematic round-trip de construção simples.
9. Reabrir mundo e continuar build sem state loss.
10. Confirmar que citizens/trains/Create crafting não são oferecidos como features.

## 18. Evidência
- modlist física 08/09/2026: runtime 2.0.6;
- CurseForge oficial: bridge Create↔Structurize/MineColonies, Client & Server;
- documentação oficial: Rails, Belts, Bogeys, Deployers, Train Stations, clipboard e Schematic Workbench;
- upstream lista explicitamente citizens-on-trains, Create-machine citizens e train-signal placement como não implementados/problema conhecido.

> 🔒 Boundary canônico: **CreateColonies traduz requirements/placement e formatos; Create e MineColonies/Structurize continuam donos do state final e do processo de construção**.
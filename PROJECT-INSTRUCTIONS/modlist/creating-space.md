# Creating Space

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c469db9f0db813ca2fcede6633e2bcf
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(4).txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Creating Space
- **Arquivo JAR:** `creatingspace-1.21.1-1.7.20.jar`
- **Versão 1.21.1:** `1.7.20`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — rocket-contraption/planet travel authority, Create 6 dependency, assembly/travel lifecycle, Interactive compatibility caveat e decisão `Manter` preservada para 1.7.20.
- **Categoria:** Exploração; Tecnologia
- **Compatibilidade/Riscos:** Contraptions móveis, world/dimension transitions e persistência de veículos são superfícies críticas. A documentação oficial é inconsistente sobre Create: Interactive: CurseForge descreve compatibilidade parcial desde 1.7.8 com bugs/workarounds, enquanto Modrinth ainda alerta incompatibilidade; tratar como compat experimental e testar.
- **Decisão:** Manter
- **Dependências:** Create 6.x obrigatório desde a linha 1.7.10. Build 1.7.20 é Client & Server, NeoForge 1.21.1. Integrações/compat datapacks externos não são tratados como dependências-base sem metadata específica.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-creating-space
- **Função:** Addon espacial de Create em que foguetes são construídos como contraptions e usados para viajar a outros planetas, com proposta de projeto inspirada em engenharia/ciência em vez de veículos prontos.
- **Histórico da decisão:** 2026-08-22 — decisão vigente `Manter`: Creating Space foi recolocado/mantido junto de Northstar Redux para substituir Stellaris e evitar uma progressão espacial excessivamente tecnológica. Em 08/09/2026, a decisão e a coexistência deliberada foram preservadas na modlist física de 595.
- **Observações:** JAR `creatingspace-1.21.1-1.7.20.jar`; runtime 1.7.20; Release NeoForge 1.21.1 de 30/06/2026. Publicação 1.21.1 mais recente permanece 1.7.20 mesmo existindo 1.7.21 apenas para 1.20.1. Não foi localizado changelog granular da 1.7.20; detalhes version-specific adicionais ficam fail-closed.
- **Procedência:** Modlist física canônica de 08/09/2026, 595 top-levels + runtime Creating Space 1.7.20 + CurseForge/Modrinth oficiais do projeto e build 1.7.20. Decisão formal histórica de 22/08/2026 preservada.
- **Sobreposição:** Northstar Redux também cobre exploração espacial, mas a coexistência foi escolhida deliberadamente. Não classificar como duplicata automática; comparar planetas, vehicle construction, progression e recursos concretos.
- **Data da última decisão:** 2026-08-22

## Dossiê operacional — padrão Alex's Mobs

> 🚀 Versão física confirmada: `creatingspace-1.21.1-1.7.20.jar`, runtime `1.7.20`, NeoForge 1.21.1. Decisão formal do pack: **Manter** desde 22/08/2026.

## 1. Papel e authority
Creating Space adiciona exploração espacial baseada em foguetes construídos como **contraptions Create**. Create continua authority das primitives de contraption; Creating Space controla assembly espacial, travel/planet context e conteúdo próprio associado ao voo.

## 2. Filosofia de construção
O projeto enfatiza projetar o foguete em vez de receber uma nave pronta. O layout físico deve ser validado pelo runtime antes do lançamento; esta ficha não inventa requisitos de tamanho, thrust, fuel ou peças sem documentação/JAR 1.7.20.

## 3. Create 6+
Desde a linha 1.7.10, a documentação oficial exige **Create 6.+**. Isso torna a versão do Create uma boundary dura: update isolado de Create precisa de smoke-test de assembly, launch e return.

## 4. Contraption state
Ao virar foguete, blocos deixam de ser apenas world blocks e passam a compor uma contraption. Inventories, block entities e attachments suportados precisam manter state único entre assembly, flight e disassembly.

## 5. Viagem a planetas
A feature central é viajar a outros planetas. Dimension/world transition precisa manter player, veículo e inventories coerentes e não duplicar a nave no mundo de origem/destino.

## 6. Progressão espacial
A decisão do pack preserva Creating Space junto de Northstar Redux para uma progressão menos baseada em máquinas espaciais prontas. Essa é decisão curatorial; não implica equivalência técnica entre os dois mods.

## 7. Create: Interactive
As páginas oficiais divergem: CurseForge afirma compatibilidade **parcial** com Create: Interactive em versões recentes, com bugs/workarounds; Modrinth ainda exibe aviso de incompatibilidade. Para a build 1.7.20, tratar Interactive como integração experimental e nunca como suporte garantido.

## 8. Ad Astra datapacks
O projeto divulga datapacks de compatibilidade com Ad Astra como recurso externo da equipe. Eles não são parte automaticamente ativa do JAR 1.7.20 e não devem ser catalogados como runtime sem presença física separada.

## 9. Client/server
Assembly, vehicle state, travel, world transition e inventories são server/common. Models, cockpit/visuals e efeitos são client-facing. O cliente não pode autorizar launch/destination por state local não validado.

## 10. Multiplayer
Com múltiplos players na mesma nave, ownership/passenger state e transição dimensional precisam ocorrer uma vez. Reconnect durante viagem não pode criar cópia da contraption ou deixar player preso em dimensão incorreta.

## 11. Chunk lifecycle
Foguete no limite de chunks ou durante unload/reload exige persistência cuidadosa. Chunk ticketing real pertence à implementação; não inferir que o mod mantém todos os chunks sempre carregados.

## 12. Version 1.7.20
A build 1.7.20 é a release NeoForge 1.21.1 vigente publicada em 30/06/2026. A 1.7.21 posterior é para Minecraft 1.20.1, portanto não substitui a build instalada. Não foi localizado changelog granular específico da 1.7.20.

## 13. Northstar Redux
A coexistência foi decidida pelo usuário. Integrations/recipes/planets que se sobrepõem precisam ser avaliados feature-by-feature; não remover um provider simplesmente por ambos serem `space mods`.

## 14. Lifecycle
Validar build→assembly→launch→travel→arrival→disassembly, death/reconnect, server restart com nave persistida, dimension unload e update de Create.

## 15. Riscos
1. Contraption duplicar em transição dimensional.
2. Inventory/block entity state se perder.
3. Passenger state divergir em multiplayer.
4. Create update quebrar assembly.
5. Interactive hook causar conflito/desync.
6. Conteúdo de datapack externo ser assumido presente.
7. Coexistência com outro space mod duplicar recipes/progression.
8. Changelog inexistente ser preenchido por inferência.

## 16. Matriz de testes
1. Dedicated server boot com Create 6.x.
2. Montar foguete mínimo válido conforme guia/runtime.
3. Assembly/disassembly preservando blocks/inventories.
4. Launch e chegada a destino sem dupe.
5. Return trip para mundo/origem.
6. Multiplayer com passageiro adicional.
7. Disconnect/reconnect antes/durante/depois da viagem.
8. Server restart com foguete montado.
9. Teste A/B com Create: Interactive se estiver instalado.
10. Coexistência com Northstar Redux sem recipe/dimension collision evidente.

## 17. Evidência
- modlist física 08/09/2026: Creating Space 1.7.20;
- CurseForge/Modrinth oficiais: foguetes como contraptions, planet travel, Client & Server;
- requisito Create 6.+ desde 1.7.10;
- documentação oficial conflitante sobre Interactive tratada como risco, não como fato resolvido;
- decisão histórica `Manter` de 22/08/2026 preservada.

> 🔒 Boundary canônico: **Creating Space decide a camada espacial; Create decide a infraestrutura de contraption**. A transição mundo↔foguete↔planeta precisa preservar uma única cópia de cada state.

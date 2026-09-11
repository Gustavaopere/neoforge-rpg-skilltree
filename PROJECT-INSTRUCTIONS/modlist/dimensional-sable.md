# Dimensional Sable

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81fbaef0fdadc7d0388e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Dimensional Sable
- **Arquivo JAR:** `dimensional_sable-1.0.5.jar`
- **Versão 1.21.1:** 1.0.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia, Exploração
- **Função:** API/modpack tool server-side para transferir objetos/sublevels físicos do Sable entre dimensões, incluindo connected sublevels e suporte opcional a Create Aeronautics.
- **Dependências:** Required oficial: Sable 2.0.3+; pack atual usa Sable 2.0.5. Create Aeronautics é integração opcional e está presente no pack em 1.3.2.
- **Sobreposição:** Não substitui Sable nem Create Aeronautics. Sable é authority da física/sublevel; Dimensional Sable é authority da operação de transferência cross-dimension.
- **Compatibilidade/Riscos:** Cross-dimension state transfer: risco de dupe origem/destino, BE/inventory loss, connected graph parcial, chunk unload, stale Sable IDs e contraption incompatível. Moving Mechanical Piston é limitação conhecida; não assumir suporte universal a Create contraptions.
- **Observações:** 1.0.5 atualizou para Sable 2.0.3; pack usa 2.0.5. `/sable dimension_set` é comando documentado. Teleport de sublevels conectados é suportado por relações descritas no projeto; Moving Mechanical Piston permanece caso problemático.
- **Procedência:** Runtime/JAR/mod id/ordem: modlist física canônica de 08/09/2026 (595 top-levels). Release/environment/deps: Modrinth oficial 1.0.5. Operação/command/connected-sublevel limits: README/source oficial do projeto.
- **Fonte:** https://modrinth.com/mod/dimensional-sable/version/1.0.5
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — cross-dimension sublevel authority, Sable/Aeronautics integration, server lifecycle, connected state, known contraption limit, risks e testes catalogados.
- **Histórico da decisão:** Ficha reconstruída em 08/09/2026 contra runtime 1.0.5 e baseline atual Sable 2.0.5.
- **Data da última decisão:** 2026-09-08

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `dimensional_sable-1.0.5.jar` · mod id `dimensional_sable` · versão `1.0.5` · NeoForge 1.21.1.

## 1. Identidade
- **Mod:** Dimensional Sable.
- **JAR:** `dimensional_sable-1.0.5.jar`.
- **Mod id:** `dimensional_sable`.
- **Versão:** `1.0.5`.
- **Minecraft/loader:** 1.21.1 / NeoForge.
- **Environment oficial:** Server-side + Singleplayer.
- **Licença:** MIT.
- **Release 1.0.5:** atualiza compatibilidade para Sable 2.0.3; o pack atualmente possui Sable 2.0.5.

## 2. Papel no modpack
É um mod/API de gerenciamento para **teleportar objetos/sublevels físicos do Sable entre dimensões**. Também possui suporte opcional a **Create Aeronautics**.
Não cria uma engine física própria. **Sable continua authority do objeto físico/sublevel**; Dimensional Sable controla a operação cross-dimension e o vínculo necessário para mover esse state.

## 3. Authority / ownership
- **Sable:** body/sublevel physics, conexão física e state estrutural.
- **Dimensional Sable:** pedido e execução de dimension transfer suportado.
- **Create/Create Aeronautics:** contraptions/airship components e seus próprios block entities.
Uma integração própria deve chamar/adaptar o transfer contract em vez de clonar blocos manualmente entre levels.

## 4. Comando e operação confirmados
O README oficial documenta o comando:
- `/sable dimension_set ...`
O addon pode teleportar:
- um sublevel isolado;
- sublevels conectados entre si por relações suportadas, incluindo **ropes, springs e docking connectors/default connected** conforme documentação do projeto.
Isso é state transfer estrutural, não simples `/tp` de entidade vanilla.

## 5. Conectividade
O critério de “connected sublevels” importa porque um objeto físico composto pode possuir várias partes/sublevels. Transferir apenas uma parte pode quebrar ropes/springs/docking state.
Qualquer ferramenta automática deve identificar o connected component antes da mudança de dimensão e executar uma única transação lógica de transferência.

## 6. Limite conhecido — Create contraptions
O projeto registra como limitação atual que **Moving Mechanical Piston** (incluindo sticky) está quebrado/problemático nesse fluxo. A documentação também aponta como objetivo futuro teleportar Create contraptions de forma mais própria, em vez de depender de desmontagem/reconstrução.
Portanto esta ficha **não afirma preservação universal de contraptions Create** em dimension transfer.

## 7. Dependências concretas
- **Sable 2.0.3+** é required segundo a release 1.0.5.
- O pack usa **Sable 2.0.5**, atendendo a baseline.
- **Create Aeronautics** é dependência/integração opcional oficialmente declarada; o pack possui Create Aeronautics 1.3.2.

## 8. Server authority
O Modrinth marca o projeto server-side. Transferir estruturas entre dimensions altera world/chunk/block-entity state e deve ser feito pelo servidor.
O cliente pode receber updates/render do resultado, mas não deve criar a nova estrutura localmente.

## 9. Lifecycle e state transfer
Superfícies a preservar ou validar:
- source level e destination level;
- chunk tickets/load state;
- block entities e inventários;
- entities/passengers ligados ao objeto quando suportados;
- conexões entre sublevels;
- Sable physics identifiers;
- unload do sublevel de origem;
- load/registration no destino;
- world save/restart após transferência.
A documentação pública não confirma garantia universal para cada tipo de block entity de mods terceiros; tratar cada provider stateful fail-closed.

## 10. Multiplayer
A transferência precisa ser idempotente: um comando/evento não pode deixar uma cópia na origem e outra no destino. Dois jogadores tentando transferir o mesmo connected component simultaneamente devem convergir para uma única operation authority.
Client interpolation/render não deve ser confundido com confirmação de commit server-side.

## 11. Integrações no pack
- **Sable 2.0.5:** hard dependency/provider físico.
- **Create Aeronautics 1.3.2:** integração opcional explicitamente suportada.
- **Create:** block entities/contraptions podem existir dentro do objeto, mas suporte deve ser validado caso a caso; Moving Mechanical Piston é limitação conhecida.
- Outros mods com inventories/machines stateful precisam de QA antes de serem declarados “teleportable”.

## 12. Riscos
1. dupe estrutural origem+destino;
2. perda de bloco/BE/inventory durante transfer;
3. connected component parcialmente movido;
4. stale physics ID ou connection graph;
5. chunk unload no meio da transação;
6. passageiros/entities órfãos;
7. Create contraption desmontada ou inconsistente;
8. Moving Mechanical Piston quebrado;
9. retry/reconnect repetindo transferência;
10. version drift com Sable.

## 13. Matriz de testes
1. Dedicated server boot com Sable 2.0.5.
2. Transferir um sublevel simples entre duas dimensões.
3. Confirmar ausência de cópia na origem.
4. Restart e conferir state no destino.
5. Transferir connected sublevels via rope.
6. Repetir com spring/docking connection suportada.
7. Inventory/block entity simples dentro do sublevel.
8. Dois jogadores tentando mover o mesmo objeto simultaneamente.
9. Transfer durante chunk boundary/load-unload.
10. Create Aeronautics object/airship compatível.
11. Moving Mechanical Piston como teste negativo conhecido.
12. Death/logout/relogin de jogador observando/operando o transfer.
**Matriz de validação futura; nenhum resultado foi assumido.**

## 14. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/versão e Sable atual;
- Modrinth oficial `Dimensional Sable 1.0.5`: MC1.21.1, NeoForge, server-side, Sable 2.0.3+ e Create Aeronautics opcional;
- README/source oficial para `/sable dimension_set`, connected sublevels e limitações de contraption/Moving Mechanical Piston.

> **Boundary canônico:** Dimensional Sable transfere state físico entre dimensions; Sable continua dono da física. Não tratar teleport como copy/paste de blocos.
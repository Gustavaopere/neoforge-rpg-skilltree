# Create Tweaked Controllers

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db810da50ccae3e2da1ddb
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Tweaked Controllers
- **Arquivo JAR:** `create_tweaked_controllers-1.21.1-1.2.7.jar`
- **Versão 1.21.1:** 1.21.1-1.2.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL
- **Função:** Expande controle de contraptions Create por Tweaked Lectern Controller e input de teclado, mouse, gamepad/joystick, com session ownership server-side e modos de eixo/precisão.
- **Dependências:** Create 6.0.0+; pack físico usa Create 6.0.10. Source matching inclui integração opcional CC:Tweaked, mas CC:Tweaked não aparece como JAR top-level atual, portanto não é integração ativa confirmada.
- **Sobreposição:** Complementa Create control/contraptions. Overlap é de input/control ownership com outros sistemas veiculares/controladores, não duplicidade global de conteúdo; CC:Tweaked upstream está inativo no pack atual pela ausência física.
- **Compatibilidade/Riscos:** Riscos: axis/update storm em rede Create, input residual após hotplug, UUID/user state fantasma após logout/unload, dois usuários concorrentes, mapping/deadzone divergente e conflito com outros controllers. 1.2.7 corrige log spam de debug residual.
- **Observações:** JAR/mod id/runtime exatos. Branch oficial `dev-1.21` declara mod 1.21.1-1.2.7 e MC 1.21.1. Source confirma TweakedLecternControllerBlockEntity, ControllerData, User UUID, UseFullPrecision e surface ComputerCraft opcional.
- **Procedência:** modlist.txt física atual de 09/09/2026 — 594 JARs top-level + release oficial 1.2.7 + repositório oficial getItemFromBlock/Create-Tweaked-Controllers branch pin-matching `dev-1.21`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-tweaked-controllers
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.2.7 com lectern/user persistence, GLFW input matrix, full precision, client/server authority, optional CC surface, lifecycle e regression gates catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🎮 **Identidade física e source matching confirmados:** `create_tweaked_controllers-1.21.1-1.2.7.jar`, mod id `create_tweaked_controllers`, runtime `1.21.1-1.2.7`. O branch oficial `dev-1.21` declara exatamente MC 1.21.1/mod 1.21.1-1.2.7 e Create range 6.0.0+.

## 1. Papel e authority
Create Tweaked Controllers expande o controle de contraptions Create por um controller avançado. O addon owns captura/mapeamento de input, estado do controller/lectern e tradução desse input para outputs de controle. Create continua owner das contraptions, redstone links e mecanismos que recebem os sinais.

## 2. Tweaked Lectern Controller
O source matching confirma um **Tweaked Lectern Controller** com block entity própria. Ela armazena dados do controller, usuário atual, modo de precisão e estado de eixos/botões.
O lectern admite um usuário por vez: o UUID do usuário é persistido/sincronizado, e o servidor valida se o jogador continua presente, em alcance e em estado válido de uso.

## 3. Persistência e user ownership
A block entity serializa `ControllerData`, `UseFullPrecision` e, quando presente, o UUID `User`. O servidor limpa o usuário quando a entidade não existe mais ou sai das condições de uso.
Isso é um boundary multiplayer importante: input remoto não deve continuar ativo depois de logout, morte, unload ou abandono do lectern.

## 4. Inputs suportados
O README oficial confirma suporte via GLFW a:
- teclado;
- botões do mouse;
- movimento do cursor;
- gamepads conhecidos, incluindo Xbox/PlayStation;
- gamepads alternativos;
- joysticks/HOTAS/racing wheels quando detectados pelo GLFW, via modo avançado.
WiiMotes não são suportados segundo a documentação atual.

## 5. Gamepad versus joystick genérico
GLFW pode detectar um dispositivo apenas como joystick, com eixos/botões em ordem dependente do driver, ou como gamepad com mapping conhecido. A documentação informa que gamepads mapeados expõem uma estrutura padronizada de seis eixos e quinze botões.
Portanto profiles/mappings precisam ser tratados como configuração de input, não como propriedade universal do hardware.

## 6. Precisão de eixos
O source matching confirma dois modos de leitura: estado compactado/quantizado e **full precision** com seis valores float. O servidor/block entity mantém o modo e os valores usados para output.
Mudança de precisão não pode deixar eixo antigo preso nem aplicar dois caminhos de output simultaneamente.

## 7. Limitação de updates do Create
O upstream alerta que uma rede Create atualizada vezes demais pode quebrar, citando como exemplo eixo ligado a Adjustable Chain Gearshift. O comportamento problemático correspondente foi desabilitado desde 1.2.3.
Essa limitação continua sendo um regression gate para inputs analógicos de alta frequência: movimento de eixo não pode gerar update storm na rede Create.

## 8. Client/server boundary
Captura de teclado/mouse/gamepad é necessariamente client-facing. Ownership do lectern, validade do usuário e efeitos sobre o mundo precisam convergir no servidor.
O cliente não deve poder enviar state de controller para um lectern não possuído, fora de alcance ou inexistente.

## 9. ComputerCraft surface
O source matching contém integração própria com **CC:Tweaked**, incluindo comportamento de computador e `TweakedLecternPeripheral`. O pack físico atual, porém, não contém CC:Tweaked como JAR top-level identificado.
Portanto essa superfície é **upstream disponível, mas não integração ativa confirmada no pack**; não se usa para justificar comportamento runtime atual sem a dependência presente.

## 10. Config client-side
O branch matching contém `ModClientConfig`, coerente com a necessidade de configuração de input. Configurações de mapping/deadzone/presentation são client-side; elas não podem alterar unilateralmente permissões/ownership server-side.

## 11. Compatibilidade física/veicular
A página oficial declara compatibilidade da versão atual com Clockwork/Valkyrien Skies e Create Simulated. No pack, o stack Sable/Aeronautics e mods de controle físico aumentam a importância de validar ownership de input.
A presença de outros controllers não implica conflito automático; o risco é dois providers escreverem no mesmo mecanismo/contraption no mesmo tick.

## 12. Hotplug e reconnect
Gamepads/joysticks podem ser conectados/desconectados durante a sessão. Perda do dispositivo deve zerar/neutralizar outputs, não manter throttle/axis antigo.
Relog do jogador, troca de dimensão e unload do lectern também devem invalidar sessão de controle.

## 13. Multiplayer
Com dois jogadores tentando usar o mesmo lectern, apenas o usuário aceito pelo server state deve controlar os outputs. O segundo cliente não deve sobrescrever UUID/input nem receber autoridade por render/local UI.

## 14. Delta 1.2.7
A release 1.2.7 é uma Release NeoForge 1.21.1 de 20/04/2026. O changelog da build corrige **log spam causado por debug code remanescente**. Ausência de spam é regression gate, mas não prova que outros fluxos de input foram testados nesta auditoria.

## 15. Lifecycle
Testar place/break do lectern, inserir/trocar controller, start/stop use, player out-of-range, disconnect, death, dimension change, chunk unload/reload e restart.
PersistentData `IsUsingLecternController` e UUID do block entity precisam convergir; nenhum deles isoladamente deve manter controle fantasma.

## 16. Riscos
1. Axis de alta frequência gera update storm em rede Create.
2. Input permanece ativo após gamepad disconnect/hotplug.
3. UUID/user state fica preso após logout/death/unload.
4. Dois jogadores controlam o mesmo lectern simultaneamente.
5. Cliente envia input para target fora de alcance/inexistente.
6. Full precision e modo quantizado deixam valores divergentes/stale.
7. Mapping de joystick genérico produz eixo/botão errado.
8. Outro controller/vehicle mod escreve no mesmo mecanismo no mesmo tick.
9. Integração CC:Tweaked é presumida ativa sem o mod físico presente.
10. Log spam corrigido na 1.2.7 reaparece por caminho residual.

## 17. Matriz de testes
- [ ] Dedicated server inicia com Tweaked Controllers 1.2.7 + Create 6.0.10.
- [ ] Teclado controla o target sem input residual ao soltar tecla.
- [ ] Mouse buttons/cursor respeitam mapping configurado.
- [ ] Gamepad conhecido mapeia eixos/botões corretamente.
- [ ] Joystick/HOTAS em modo avançado neutraliza state ao desconectar.
- [ ] Full precision troca sem manter valores antigos.
- [ ] Adjustable Chain Gearshift não recebe update storm por axis contínuo.
- [ ] Segundo jogador não toma controle de lectern já em uso.
- [ ] Sair de alcance/logout/death libera `User` e outputs.
- [ ] Chunk unload/reload/restart não mantém controle fantasma.
- [ ] Ausência de log spam residual da regressão corrigida na 1.2.7.
- [ ] Integrações veiculares presentes no pack não duplicam input no mesmo target.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
A modlist física confirma JAR/mod id/runtime. A publicação oficial confirma Release 1.2.7, Create 6.0.0+, Client & Server e o fix de log spam. O branch oficial `dev-1.21` é pin-matching e confirma block entity, persistência de user/controller/full precision e surface ComputerCraft. O README confirma métodos de input e a limitação de update de rede Create. Features de mods opcionais não presentes permanecem inativas/não presumidas.

> 🔒 **Boundary canônico:** o cliente captura o dispositivo; Tweaked Controllers valida e traduz a sessão; Create owns o mecanismo/contraption resultante. Nenhum input local é authority suficiente para alterar o mundo sem state server-side válido.
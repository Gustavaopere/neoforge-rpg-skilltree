# Create: Radiologistics

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#201**: JAR `CreateRadiologistics-1.1.1.jar`, mod id `radiologistics`, runtime `1.1.1`, SHA-1 `7f26aad650b69559d9eb8a1449e4a49c94d86880`.

## Propriedades do registro

- **Mod:** Create: Radiologistics
- **Arquivo JAR:** `CreateRadiologistics-1.1.1.jar`
- **Versão 1.21.1:** `1.1.1`
- **Categoria:** Tecnologia, Automação
- **Função:** Camada Create de comunicação sem fio e computação lógica visual baseada em nós para controle remoto de fábricas, ferrovias, defesa e flying machines.
- **Dependências:** Create obrigatório; pack físico usa NeoForge 21.1.248 + Create 6.0.10. Create Big Cannons 5.11.7, Create: Radars 0.4.9.4-1.21.1 e Create Aeronautics 1.3.2 permanecem integrações opcionais concretamente presentes.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** 1.1.1 é Beta-only para NeoForge 1.21.1. Superfícies críticas: persistência de variáveis, rádio até 3000 blocos, jammer de 150 blocos, Redstone Links, áudio remoto/TTS, sensores e projeção de coordenadas em sublevels Aeronautics. 1.1.1 corrige Radars, Ponders, MP3 player e 3D gizmos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-radiologistics
- **Procedência:** modlist.txt física atual de 11/09/2026 + runtime `radiologistics` 1.1.1 + CurseForge oficial revalidado em 12/09/2026; 1.1.1 de 02/07/2026 continua a build Beta NeoForge 1.21.1 mais recente.
- **Observações:** runtime 1.1.1; build Beta NeoForge 1.21.1. Features oficiais incluem Main Computer/node editor, Radio Transmitter/Antennas, Redstone Link Module, Memory Module, Gyroscope, Jammer, Audio Module, Pilot Helmet e Transparent Screen. 1.1.1 adiciona Smart Optical Sensor, Servo Motor, novos nodes e coding help.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — lote físico #202: CreateRadiologistics-1.1.1.jar / runtime 1.1.1 reconfirmados como latest Beta NeoForge 1.21.1; Main Computer/node programs, rádio, memória, sensores/jammer/audio e integrações Radars/CBC/Aeronautics permanecem atuais.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Radiologistics 1.1.1 foi reconfirmado como `Instalado`; a maturidade Beta é risco técnico documentado e não foi convertida em decisão curatorial.
- **Sobreposição:** Pode compartilhar comunicação/controle com Redstone Links, Radars e outros logic systems, mas owns sua rede, node programs, memória e módulos. Bridges consomem dados de outros providers sem substituí-los.

# Dossiê operacional — padrão Alex's Mobs
> 📻 Versão física confirmada: `CreateRadiologistics-1.1.1.jar`, runtime `1.1.1`, NeoForge 1.21.1. Esta build é **Beta** e fornece rede sem fio + programação visual por nodes para controlar sistemas Create e integrações opcionais.
## 1. Papel e authority
Radiologistics é uma camada de computação/telecontrole. Create continua authority das máquinas, Redstone Links e contraptions; Radars continua authority de contatos; CBC continua authority da munição/projectile; Aeronautics continua authority dos moving sublevels. Radiologistics conecta, calcula e envia comandos.
## 2. Main Computer
O Main Computer é a CPU da rede e abre editor visual node-based. Math, lógica, variáveis e texto compõem algoritmos. O resultado que muda gameplay precisa ser validado pelo servidor; editor cliente não é execução autoritativa por si só.
## 3. Radio Transmitter e Antennas
Transmissores e antenas enviam dados wireless. Cada segmento vertical aumenta alcance, com hard cap oficial de **3000 blocos**. Extensão por script não deve criar um segundo cálculo de range ignorando a topologia real.
## 4. Redstone Link Module
O módulo integra Create Redstone Links ao algoritmo, enviando/recebendo sinais em frequências de itens. O sinal final deve ser liquidado uma vez; loops entre programa e Redstone Link precisam de proteção contra feedback infinito.
## 5. Memory Module
Memory Module persiste variáveis de database entre resets da rede. Restart, chunk unload e reconnect não podem apagar state persistente nem duplicá-lo em duas memórias lógicas.
## 6. Gyroscope Sensor
O Gyroscope mede pitch, yaw e coordenadas absolutas. Em contraptions móveis, o espaço de coordenadas precisa ser transformado corretamente antes de alimentar navigation/weapon logic.
## 7. Jammer Module
Jammer bloqueia canais wireless e Create Redstone Links próximos dentro do raio documentado de **150 blocos**. Jammer deve interferir no canal/range previsto, não remover state permanente de devices bloqueados.
## 8. Audio Module
Audio Module suporta streams remotos WAV/MP3/YouTube e TTS local, com panning espacial 3D. Essa superfície é client/audio-heavy; falha de stream não deve travar o servidor nem alterar a lógica do programa.
## 9. Pilot Helmet e Transparent Screen
Pilot Helmet exibe elementos/camera no jogador e envia pitch/yaw ao computador. Transparent Screen fornece superfície semelhante em bloco. Input/view state cliente deve ser tratado como sinal, não como autoridade para mutações server-side sem validação.
## 10. Smart Optical Sensor e Servo Motor — 1.1.1
A 1.1.1 adiciona **Smart Optical Sensor** e **Servo Motor**, além de novos nodes e coding help. Como a publicação não expõe todos os parâmetros/IDs em detalhe, stats/interfaces específicas ficam fail-closed até JAR/source pin.
## 11. Create: Radars
Integração oficial permite extrair e rastrear coordenadas de radar pelo Network Controller dentro dos algoritmos. O contato continua pertencendo ao Radars; Radiologistics não deve inventar uma segunda posição independente para o mesmo alvo.
## 12. Create Big Cannons
A integração adiciona **Wired Inertia Fuze**: quando shell block contendo o fuze recebe redstone, pode ser ativado e lançado como projectile em vez de detonar no local. CBC mantém authority da shell/projectile/damage; Radiologistics fornece o controle remoto.
## 13. Create Aeronautics
Há projeção de coordenadas para physics grids/moving sublevels. References devem ser resolvidas novamente em assembly/disassembly e nunca misturar parent-world e sublevel coordinates como se fossem o mesmo espaço.
## 14. Correções 1.1.1
O changelog exato registra fixes para **Radars, Ponders, MP3 player e 3D gizmos**. Esses quatro caminhos são regression gates diretos. O changelog não detalha classes/causas; a ficha não inventa implementação interna.
## 15. Client/server e multiplayer
Program state, network membership/links, persistent memory e comandos que alteram world state precisam ser server-authoritative. GUI/node editor, overlays, gizmos e áudio são client-facing. Pacotes/retries precisam ser idempotentes.
## 16. Lifecycle
Validar computer load/save, variable persistence, antenna changes, jammer enter/leave range, program reload, contraption assembly/disassembly, player reconnect, server restart e optional-mod presence/absence.
## 17. Riscos
1. Programa entrar em feedback loop.
2. Variável persistente perder/duplicar state.
3. Coordenada parent↔sublevel ser transformada duas vezes.
4. Jammer bloquear além do raio/config esperado.
5. Áudio remoto causar stall/crash.
6. Radars contact ser duplicado.
7. CBC fuze produzir double-launch/double-damage.
8. Beta 1.1.1 regressar node serialization.
9. Optional integration classload sem provider.
## 18. Matriz de testes
1. Dedicated server boot.
2. Programa simples math/logic/variables/text.
3. Antenna range e hard cap de 3000 blocos.
4. Redstone Link send/receive sem feedback infinito.
5. Memory Module após reset/restart.
6. Gyroscope em bloco parado e Aeronautics contraption.
7. Jammer dentro/fora de 150 blocos.
8. Audio Module com falha de URL sem afetar servidor.
9. Radars integration e Smart Optical Sensor.
10. Servo Motor e novos nodes smoke-test.
11. Wired Inertia Fuze com CBC exatamente uma vez.
12. Reconnect/resource reload/Ponder/3D gizmos.
## 19. Evidência
- modlist física 08/09/2026: Radiologistics 1.1.1;
- CurseForge oficial: Main Computer, antennas, Redstone Link Module, Memory, Gyroscope, Jammer, Audio, helmet/screen e integrações CBC/Radars/Aeronautics;
- release 1.1.1: Beta, fixes Radars/Ponder/MP3/3D gizmos; Smart Optical Sensor, Servo Motor, novos nodes e coding help.
> 🔒 Boundary canônico: **Radiologistics calcula e transmite; cada provider integrado continua decidindo a semântica do dado/ação final**. Persistência e comandos precisam convergir uma única vez no servidor.

## Auditoria parcial — 20/11/2026 — novas publicações

A autoridade física #201 permanece `CreateRadiologistics-1.1.1.jar`. O catálogo CurseForge recuperado nesta rodada é antigo (quatro betas até 02/07), embora o [perfil oficial do autor](https://www.curseforge.com/members/maqwr_/projects) registre atualização do projeto em 20/10/2026, com categoria Create. Portanto não usar a listagem antiga para declarar 1.1.1 latest.

A [publicação oficial Modrinth 1.1.3, ID itGbK3IZ](https://modrinth.com/mod/create-radiologistics/version/1.1.3), Beta NeoForge 1.21.1, confirma correção de interação indevida com cliente em servidor, restauração de funcionalidade anterior do servo motor e aumento da distância máxima do Smart Optical Sensor para **500 blocos**. O changelog não detalha o mecanismo do fix de servidor nem o contrato dos nodes do servo; testar dedicated boot, controle de servo e limites de sensor (não confundir com alcance de antenas).

Há indícios de 1.1.2 e 1.1.4 em listagens secundárias/modpacks, inclusive [changelog de Create_Craft&Quiet](https://www.curseforge.com/minecraft/modpacks/create-craft-quiet/files/9625612/additional-files), mas a consulta às páginas individuais oficiais dessas versões não retornou conteúdo e a listagem Modrinth não expôs a tabela. Não assumir datas, compatibilidade ou alterações dessas builds. **Pendente:** recuperar catálogo integral, confirmar as distribuições intermediárias/mais recentes e seus changelogs. Não promover o JAR nem liberar a auditoria documental ainda.

## Auditoria incremental parcial — 21/11/2026 — confirmação da release 1.1.4

**Autoridade física preservada:** posição #201, `CreateRadiologistics-1.1.1.jar`, NeoForge 1.21.1. O [CurseForge oficial, projeto 1556290, arquivo 9265504](https://www.curseforge.com/minecraft/mc-mods/create-radiologistics/files/9265504) confirma a publicação **1.1.4 Beta em 20/10/2026**, para **NeoForge 1.21.1**, com categoria temática **Create**. Essa publicação substitui a hipótese não confirmada de 1.1.4 registrada na auditoria parcial de 20/11. A release 1.1.3 já está documentada na seção anterior pelo changelog primário do Modrinth.

**Changelog oficial específico da 1.1.4:** correções de (i) dependências declaradas pelo mod; (ii) tela transparente; (iii) giroscópio, que o próprio autor qualifica como **“maybe”**, portanto **não verificado como definitivamente corrigido**; (iv) custom node; e (v) informações dos canhões. O arquivo não detalha causas, valores, APIs nem a implementação. Verificar inicialização no servidor e cliente, interface transparente, precisão do giroscópio em contraptions, serialização/execução de nodes customizados e dados de canhões com Create Big Cannons. Não atribuir a uma build anterior.

**Bloqueio remanescente:** a cadeia completa entre 1.1.1 e 1.1.4 exige confirmação individual da distribuição 1.1.2 e de seu changelog; não é possível declarar auditoria integral só com 1.1.3/1.1.4. Nenhum JAR, metadata ou teste in-game foi modificado. **PENDENTE — não liberar para remoção do Bloco de Notas.**

**Fonte primária:** [CurseForge 1.1.4](https://www.curseforge.com/minecraft/mc-mods/create-radiologistics/files/9265504), seção *What's new*.

## Conciliação incremental — 21/11/2026 — distribuição 1.1.2 confirmada

A [lista integral de arquivos do projeto oficial no CurseForge (projeto 1556290)](https://www.curseforge.com/minecraft/mc-mods/create-radiologistics/files/all), consultada com resultados atualizados, **enumera sete publicações**; as quatro da sequência pertinente são:

| Versão NeoForge 1.21.1 | Data informada no catálogo | Situação da nota técnica |
|---|---|---|
| **1.1.1** (baseline instalado) | 02/07/2026 | Recursos/fixes já registrados neste dossier |
| **1.1.2**, `CreateRadiologistics-1.1.2.jar` | **20/09/2026** | **Arquivo distribuído confirmado oficialmente; changelog específico não recuperado** |
| **1.1.3**, `CreateRadiologistics-1.1.3.jar` | **28/09/2026** | Notas primárias do autor no [Modrinth 1.1.3](https://modrinth.com/mod/create-radiologistics/version/1.1.3): correção de interação client/server, restabelecimento do servo motor e sensor óptico até 500 blocos |
| **1.1.4**, `CreateRadiologistics-1.1.4.jar` | **20/10/2026** | Notas primárias do [CurseForge 1.1.4 (ID 9265504)](https://www.curseforge.com/minecraft/mc-mods/create-radiologistics/files/9265504): ajustes de dependências, transparent screen, gyro (*maybe*), custom node e cannon info |

**Correção das auditorias anteriores:** a existência, filename, data e compatibilidade **NeoForge 1.21.1** da **1.1.2** deixam de ser hipótese; a tabela atual de distribuições não mostra outra release entre 1.1.1 e 1.1.4. Entretanto **continua impossível atestar o conteúdo técnico introduzido na 1.1.2**, pois o catálogo de arquivos fornece metadata, não uma nota por versão. Nenhuma mudança da 1.1.3 ou 1.1.4 foi retroatribuída à 1.1.2. Esta lacuna demanda changelog próprio, evidência de source pinado ou comparação dos JARs oficiais antes de certificação da auditoria integral.

**Status: PARCIAL, fail-closed.** Versão instalada permanece **1.1.1**, posição **#201**, sem modificação de JAR, nome certificado, categoria **Create**, arquivos de configuração ou QA. **Não liberar o nº 124 do Bloco de Notas.**

## Fechamento documental do intervalo 1.1.1 → 1.1.4 — 21/11/2026

**Cadeia de publicações oficialmente enumeradas:** a [lista CurseForge do projeto 1556290](https://www.curseforge.com/minecraft/mc-mods/create-radiologistics/files/all) confirma que os únicos arquivos NeoForge 1.21.1 posteriores ao baseline 1.1.1 e até a publicação mais recente de 20/10/2026 são **1.1.2 (20/09, CurseForge file ID 8930397) → 1.1.3 (28/09, file ID 8998071) → 1.1.4 (20/10, file ID 9265504)**. O [Modrinth oficial **1.1.2**](https://modrinth.com/mod/create-radiologistics/version/1.1.2), da mesma autoria, disponibiliza o JAR NeoForge e **o changelog específico que faltava**. A janela inteira passa a ter notas primárias por versão. Os conteúdos abaixo são **upstream e não instalados**.

### 1.1.2 — novas funcionalidades e correções da publicação

- **Editor e execução por nodes:** adiciona **Helmet Audioset** para saída de áudio individual ao usuário do Pilot Helmet; os **2D Gizmos** ganham parâmetros `rotation`, `x_offset` e `y_offset` para instrumentos customizados. Corrige persistência do grafo de nodes, conexões e contornos visuais que desapareciam ao fechar o editor sobre contraptions/aircraft; também corrige entrada de links e o atalho **Shift+A**, que inseria um `A` indevido na busca rápida. Inclui dicas para entradas/saídas de nodes.
- **Câmeras, monitoramento e telas:** permite conectar **múltiplas câmeras Vista** a um mesmo Main Computer por **Wire**; corrige a camada de gizmos da saída de câmera. O sistema de **Display Links** passa a admitir múltiplos links ligados ao computador, com correção do respectivo node.
- **Telemetria e orientação:** padroniza sinais e convenções de **Pitch** (`+` para cima, `-` para baixo), **Yaw** e **Roll** nos nodes PosToRot, RotToPos, HelmetRotation, Gyroscope e Camera. Corrige bugs de **Gyroscope**, Redstone Links/IDs, integração **Radars** e node Ponders. É uma alteração de contrato semântico relevante: programas que dependam de rotações antigas devem ser testados antes de importar grafos/saves.
- **Áudio:** correções do Audio Module/Audio Player passam a exigir **URL direta de áudio MP3 ou OGG** conforme o changelog, com informações de tempo atual e final. Links de páginas compartilhadas do Discord/Google Drive/GitHub não devem ser presumidos streams válidos sem serem **diretos para o arquivo**; a release também menciona fixes server-side e Sable. Testar timeout/falha de URL sem travar o dedicated server.
- **Servo, Sable e integração:** o autor classifica o **Servo Motor como WIP** nesta versão, cita direct rotation, adiciona **Servo Bearing** e corrige bugs dessa peça. Reduz *logspam* Sable e otimiza salvamento de sublevels. Não presumir que a configuração ou o protocolo da versão 1.1.1 permaneçam plenamente compatíveis.

**Fonte primária integral:** [Modrinth — Radiologistics 1.1.2, versão NeoForge 1.21.1](https://modrinth.com/mod/create-radiologistics/version/1.1.2), artefato distribuído `CreateRadiologistics-1.1.2.jar`.

### 1.1.3 — 28/09/2026

O [changelog primário da 1.1.3](https://modrinth.com/mod/create-radiologistics/version/1.1.3), já parcialmente registrado acima, especifica: correção do mod que exigia cliente no servidor; restauração da **funcionalidade anterior do servo motor**, após a condição WIP descrita na 1.1.2; distância máxima do **Smart Optical Sensor aumentada para 500 blocos**. Este limite é do sensor, **não** do rádio ou das antenas. Não extrapolar a correção para outras versões nem interpretar a menção a cliente/servidor como prova de sincronização testada no pack.

### 1.1.4 — 20/10/2026

O [changelog específico do arquivo NeoForge 1.21.1, CurseForge 9265504](https://www.curseforge.com/minecraft/mc-mods/create-radiologistics/files/9265504) informa fixes de **dependências declaradas**, **Transparent Screen**, **custom node** e **cannon info**. A correção do **Gyroscope** está literalmente qualificada como **“maybe”** pelo autor e **não** deve ser tratada como correção inequívoca. Essa publicação não descreve novos nodes/itens além da cadeia já documentada.

### Gate de integração e decisão

As interfaces mais sensíveis ao upgrade são **serialização de programas**, convenções de orientação e coordenadas em Aeronautics/Sable, múltiplas câmeras Vista/Display Links, áudio remoto privado e fluxo de Servo Motor/Servo Bearing, integração Radars e Create Big Cannons, módulos client/server, configuração e declaração de dependências. Validar em **cópia** do perfil Minecraft **1.21.1 / NeoForge 21.1.248**, com Create **6.0.10** e os providers opcionais realmente presentes. Os avisos WIP e *maybe* são limites explícitos da fonte.

**AUDITORIA DOCUMENTAL DE CHANGELOGS POR RELEASE: CONCLUÍDA até a candidata 1.1.4.** A pendência anterior sobre a 1.1.2 está **superada pela publicação primária do autor**, com metadata de distribuição NeoForge confirmada pelo CurseForge. **A instalação física/QA não foi homologada**, pois nenhum JAR novo, metadata `neoforge.mods.toml`, dependência resolvida, configuração, save nem runtime foi testado. **Versão instalada continua 1.1.1**, nome do dossier e pasta **Create** preservados. Após verificação de persistência GitHub, **mod nº 124 pode ser apagado do Bloco de Notas de auditoria documental**, não de uma eventual lista separada de upgrades físicos.


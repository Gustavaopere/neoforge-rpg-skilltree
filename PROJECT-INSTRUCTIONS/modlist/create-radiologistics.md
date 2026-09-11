# Create: Radiologistics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ceb085e4ad973d88b1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Radiologistics
- **Arquivo JAR:** `CreateRadiologistics-1.1.1.jar`
- **Versão 1.21.1:** 1.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Camada Create de comunicação sem fio e computação lógica visual baseada em nós para controle remoto de fábricas, ferrovias, defesa e flying machines.
- **Dependências:** Create obrigatório. Create Big Cannons, Create: Radars e Create Aeronautics são integrações opcionais oficiais; o pack deve ativá-las apenas quando os providers correspondentes estiverem efetivamente presentes.
- **Sobreposição:** Pode compartilhar comunicação/controle com Redstone Links, Radars e outros logic systems, mas owns sua rede, node programs, memória e módulos. Bridges consomem dados de outros providers sem substituí-los.
- **Compatibilidade/Riscos:** 1.1.1 é Beta-only para NeoForge 1.21.1. Superfícies críticas: persistência de variáveis, rádio até 3000 blocos, jammer de 150 blocos, Redstone Links, áudio remoto/TTS, sensores e projeção de coordenadas em sublevels Aeronautics. 1.1.1 corrige Radars, Ponders, MP3 player e 3D gizmos.
- **Observações:** runtime 1.1.1; build Beta NeoForge 1.21.1. Features oficiais incluem Main Computer/node editor, Radio Transmitter/Antennas, Redstone Link Module, Memory Module, Gyroscope, Jammer, Audio Module, Pilot Helmet e Transparent Screen. 1.1.1 adiciona Smart Optical Sensor, Servo Motor, novos nodes e coding help.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime radiologistics 1.1.1 + CurseForge oficial da build Beta 1.1.1 e documentação do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-radiologistics
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — wireless/node-computing authority, persistent memory, sensors/jammer/audio, Create integrations, moving-sublevel projection, lifecycle e regressões Beta 1.1.1 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Radiologistics 1.1.1 foi reconfirmado como `Instalado`; a maturidade Beta é risco técnico documentado e não foi convertida em decisão curatorial.
- **Data da última decisão:**

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

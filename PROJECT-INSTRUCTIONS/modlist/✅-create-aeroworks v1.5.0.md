# Create: Aeroworks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 16/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Aeroworks
- **Arquivo JAR:** `aeroworks-1.5.0.jar`
- **Versão 1.21.1:** 1.5.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL
- **Função:** Avionics/controle para physics ships: gyroscope, joystick, cockpit modular, control stands e servos configuráveis que transformam input em estabilização, sinais e saída rotacional.
- **Dependências:** Documentação atual: NeoForge 21.1.225+, Create 6.0.0+, Create Aeronautics 1.0.3+; relações CurseForge também registram Create + Sable. Pack: NeoForge 21.1.248, Create 6.0.10, Aeronautics 1.3.2 e Sable 2.0.5.
- **Sobreposição:** Compartilha domínio com outros addons de pilotagem, mas fornece controles modulares, gyroscope e servos próprios. Não substitui solver físico, lift ou propulsão do Aeronautics/Sable.
- **Compatibilidade/Riscos:** Testar concorrência com outros controladores de orientação/força e sincronização de inputs. A 1.5.0 adiciona Drive-By-Sable compat; validar multiplayer, bindings e servos após schematic/mirror/wrench.
- **Observações:** 1.5.0 adiciona Control Stand/Copycat Control Stand, variantes copper/steering wheel, terceiro pedal socket e Drive-By-Sable. Validar input cliente→servidor, gyroscopes concorrentes, servos e persistência após schematic/mirror/wrench.
- **Procedência:** modlist.txt física do projeto consultada em 14/09/2026 + CurseForge oficial Create: Aeroworks 1.5.0. Artefato `aeroworks-1.5.0.jar`, runtime `1.5.0`, SHA-1 `a14ff1c30f3c824b36ee6e2e711a47fd537a5c5f`; mixins `aeroworks-simulated`, `drivebysable`, `drivebywire` e núcleo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeroworks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 14/09/2026 — ownership de avionics, quatro mixin configs físicos, input/sync, lifecycle e fingerprint documentados; 1.5.0/Drive-By-Sable mantidos como gates de multiplayer.
- **Histórico da decisão:**
- **Data da última decisão:**

## Escopo e papel
Camada de **controle e estabilização de airships/physics ships** do ecossistema Create Aeronautics. Adiciona gyroscope, joystick, controles modulares, servos e interfaces para transformar input do jogador em sinais/rotação usados por um veículo construído com blocos.

## Runtime e autoridade
- JAR físico: `aeroworks-1.5.0.jar`.
- Mod ID: `aeroworks`.
- Runtime: `1.5.0`.
- Release oficial: `1.5.0+mc1.21.1`, NeoForge 1.21.1.

## Dependências
A documentação atual informa **NeoForge 21.1.225+**, **Create 6.0.0+** e **Create Aeronautics 1.0.3+**. A relação do CurseForge também registra Create + Sable como required. O pack satisfaz os pisos com NeoForge `21.1.248`, Create `6.0.10`, Aeronautics `1.3.2` e Sable `2.0.5`.

## Conteúdo funcional
O Gyroscope estabiliza a orientação do ship e escala sua força com RPM. O sistema de cockpit permite módulos como lever, joystick, buttons, steering wheel, yoke e throttle quadrant, com configuração por canal e input de teclado/mouse/controller. Servos fornecem saída rotacional configurável. A 1.5.0 acrescenta Control Stand, Copycat Control Stand, variantes copper, steering wheels coloridos, terceiro socket de pedal e compatibilidade Drive-By-Sable.

## Compatibilidade, sobreposição e riscos
Aeroworks atua no mesmo domínio de outros addons de pilotagem, mas não é automaticamente redundante: é um fornecedor de controles, estabilização e servos. O risco está em múltiplos sistemas escreverem simultaneamente nos mesmos eixos de orientação, forças ou canais de redstone/controle. A compatibilidade Drive-By-Sable da 1.5.0 deve ser exercitada porque o pack usa um stack Sable amplo.

## Limites
Não fornece o solver físico, lift ou propulsão principal; esses papéis pertencem a Sable/Aeronautics e addons de propulsão. O Gyroscope estabiliza, não cria sustentação infinita por si só.


## Ownership técnico e superfícies de integração
- **Ownership primário:** input, controle de atitude e conversão de comandos do jogador em sinais/saída rotacional para physics ships; não possui solver físico, lift ou propulsão principal.
- **Mod ID:** `aeroworks`.
- **Mixin configs físicos:** `aeroworks-simulated.mixins.json`, `aeroworks-drivebysable.mixins.json`, `aeroworks-drivebywire.mixins.json` e `aeroworks.mixins.json`.
- Os nomes dos configs confirmam superfícies dedicadas a integrações Simulated, Drive-by-Sable e Drive-by-Wire, além do núcleo; **os classes/métodos exatos não foram inferidos sem leitura de código do artefato**.
## Configuração e dados
A configuração funcional é majoritariamente in-world: canais/bindings dos módulos de cockpit, servos e Control Stands. Não foi confirmada neste lote uma lista completa de arquivos TOML/JSON próprios. Configuração de blocos deve sobreviver a wrench rotation, mirror, schematic placement e save/reload.
## Client/server, input e sincronização
Teclado/mouse/controller nascem no cliente, mas efeitos sobre orientação, redstone/controle e rotação do ship precisam convergir no servidor. A versão 1.5.0 adiciona compatibilidade Drive-By-Sable, o que torna multiplayer e sincronização de input uma superfície de regressão prioritária. Dedicated-server testing deve confirmar que um cliente não produz estado exclusivo/local de controle.
## Lifecycle operacional
Validar **colocação/configuração → bind de input → montagem do ship → alteração de RPM/power → perda/restauração de energia → mirror/schematic/wrench → desmontagem → save/reload**. Gyroscopes múltiplos e múltiplos controladores escrevendo no mesmo eixo/canal devem ser testados explicitamente.
## Fingerprint físico
- JAR: `aeroworks-1.5.0.jar`
- Runtime: `1.5.0`
- SHA-1: `a14ff1c30f3c824b36ee6e2e711a47fd537a5c5f`
- Mixin configs: quatro, listados acima.

## Testes recomendados
1. Gyroscope com RPM baixo/alto e ships de massas diferentes.
2. Múltiplos gyroscopes e perda/restauração de potência.
3. Todos os módulos de cockpit, inclusive bindings simultâneos de teclado/mouse/controller.
4. Servos após wrench rotation, mirror e schematic placement.
5. Control Stand/Copycat Control Stand e variantes 1.5.0.
6. Drive-By-Sable e dedicated-server multiplayer para sincronização de input/HUD.

## Evidências
- [CurseForge oficial — Create: Aeroworks](https://www.curseforge.com/minecraft/mc-mods/create-aeroworks)
- Modlist física atual e guia consolidado de Tecnologia.
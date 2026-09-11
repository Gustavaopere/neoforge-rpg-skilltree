# Create: Aeroworks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db811b81a1d5fcc7663d35
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Aeroworks
- **Arquivo JAR:** `aeroworks-1.5.0.jar`
- **Versão 1.21.1:** 1.5.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL
- **Função:** Avionics/controle para physics ships: gyroscope, joystick, cockpit modular, control stands e servos configuráveis que transformam input em estabilização, sinais e saída rotacional.
- **Dependências:** Documentação atual: NeoForge 21.1.225+, Create 6.0.0+, Create Aeronautics 1.0.3+; relações CurseForge também registram Create + Sable. Pack: NeoForge 21.1.248, Create 6.0.10, Aeronautics 1.3.2 e Sable 2.0.5.
- **Sobreposição:** Compartilha domínio com outros addons de pilotagem, mas fornece controles modulares, gyroscope e servos próprios. Não substitui solver físico, lift ou propulsão do Aeronautics/Sable.
- **Compatibilidade/Riscos:** Testar concorrência com outros controladores de orientação/força e sincronização de inputs. A 1.5.0 adiciona Drive-By-Sable compat; validar multiplayer, bindings e servos após schematic/mirror/wrench.
- **Observações:** 1.5.0 adiciona Control Stand, Copycat Control Stand, módulos copper, steering wheels coloridos, terceiro pedal socket e Drive-By-Sable compatibility.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Create: Aeroworks 1.5.0 + dossiê técnico existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeroworks
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — gyroscope, cockpit modules, servos, Control Stand, Drive-By-Sable e input/multiplayer QA confirmados no QC global #16.
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
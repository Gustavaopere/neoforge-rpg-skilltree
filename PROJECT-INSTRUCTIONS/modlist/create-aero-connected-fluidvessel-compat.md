# Create Aero + Connected FluidVessel Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812ea7a7f89e8f419905
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aero + Connected FluidVessel Compat
- **Arquivo JAR:** `aeroconnectedfluidvessel-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Bridge específica Create: Connected ↔ Create Aeronautics/Sable: faz FluidVessels funcionarem como boilers com Steam Vents em physics ships, sincroniza redstone entre vents do mesmo vessel e corrige separação indevida dos shafts em physics entities.
- **Dependências:** Obrigatórias upstream: Create, Create Aeronautics e Create: Connected. Pack atual: Create 6.0.10, Create Aeronautics 1.3.2 (bundle) e Create: Connected 1.3.3-mc1.21.1 presentes; Sable é a infraestrutura física do stack.
- **Sobreposição:** Não duplica Create: Connected ou Aeronautics: cobre a lacuna de interoperabilidade entre ambos. Só seria redundante se o mesmo fix fosse incorporado upstream.
- **Compatibilidade/Riscos:** Compat de block entities/physics assembly; risco principal é regressão após updates de Create/Aeronautics/Connected. Testar boiler, redstone, montagem/desmontagem, save/reload e servidor. Nenhum conflito comprovado no snapshot atual.
- **Observações:** Addon de compatibilidade estreita; não cria sistema novo de fluidos/energia. Autoridade física atual: modlist de 595 top-levels.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial + dossiê técnico existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-and-connected-fluid-vessel
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — bridge FluidVessel↔Aeronautics, boiler/redstone/shaft assembly e lifecycle confirmados no QC global #12.
- **Histórico da decisão:**
- **Data da última decisão:**

## Escopo e papel
Bridge específica entre **Create: Connected** e o stack físico **Create Aeronautics/Sable**. O problema upstream é concreto: os Steam Vents do Aeronautics não reconhecem, por padrão, o `FluidVessel` do Create: Connected como boiler válido. A bridge faz esse vessel participar do circuito de vapor em physics ships.

## Runtime e autoridade
- JAR físico: `aeroconnectedfluidvessel-1.0.0.jar`.
- Mod ID: `aeroconnectedfluidvessel`.
- Runtime: `1.0.0`.
- Autoridade de presença/versão: modlist física atual de 595 entradas top-level.
- Release oficial consultada: NeoForge 1.21.1, 1.0.0.

## Dependências
- **Obrigatórias upstream:** Create, Create Aeronautics e Create: Connected.
- No pack atual, `Create 6.0.10`, `Create Aeronautics 1.3.2` (bundle) e `Create: Connected 1.3.3-mc1.21.1` estão presentes.
- Sable entra pela infraestrutura física usada pelo Aeronautics; não deve ser contado como uma segunda função do addon.

## Integrações no pack
A bridge permite que FluidVessels sejam usados como boilers em airships, sincroniza o acionamento redstone dos Steam Vents ligados ao mesmo vessel e corrige o caso em que shafts dos vents podiam se separar em physics entities independentes. Isso toca diretamente vapor, cinética, redstone e montagem/desmontagem de ships.

## Compatibilidade, sobreposição e riscos
A função é estreita e não é duplicada pelo Create: Connected nem pelo Aeronautics isoladamente. Só se torna redundante se o mesmo fix for incorporado upstream. O maior risco é regressão após atualização de Create/Aeronautics/Connected por depender de comportamento interno de block entities e montagem física.

## Limites
Não adiciona um novo sistema de fluidos, boiler ou energia. Não substitui Fluid Tanks do Create e não altera a física geral do Sable fora das interações corrigidas.

## Testes recomendados
1. Montar FluidVessel + Steam Vents em terreno estático e confirmar steam/boiler.
2. Repetir em physics ship montado e em movimento.
3. Acionar vários vents do mesmo vessel com uma única entrada redstone e validar sincronização.
4. Montar/desmontar o ship repetidamente e verificar que shafts não viram physics entities independentes.
5. Validar persistência após save/reload e dedicated server.

## Evidências
- [CurseForge oficial — Create Aeronautics and Connected Fluid Vessel Compatability](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-and-connected-fluid-vessel)
- Modlist física atual e guia consolidado de Tecnologia do projeto.

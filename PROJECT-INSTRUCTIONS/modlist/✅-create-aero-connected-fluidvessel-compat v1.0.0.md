# Create Aero + Connected FluidVessel Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 16/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aero + Connected FluidVessel Compat
- **Arquivo JAR:** `aeroconnectedfluidvessel-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Bridge específica Create: Connected ↔ Create Aeronautics/Sable: faz FluidVessels funcionarem como boilers com Steam Vents em physics ships, sincroniza redstone entre vents do mesmo vessel e corrige separação indevida dos shafts em physics entities.
- **Dependências:** Obrigatórias upstream: Create, Create Aeronautics e Create: Connected. Pack atual: Create 6.0.10, Create Aeronautics 1.3.2 (bundle) e Create: Connected 1.3.3-mc1.21.1 presentes; Sable é a infraestrutura física do stack.
- **Sobreposição:** Não duplica Create: Connected ou Aeronautics: cobre a lacuna de interoperabilidade entre ambos. Só seria redundante se o mesmo fix fosse incorporado upstream.
- **Compatibilidade/Riscos:** Compat de block entities/physics assembly; risco principal é regressão após updates de Create/Aeronautics/Connected. Testar boiler, redstone, montagem/desmontagem, save/reload e servidor. Nenhum conflito comprovado no snapshot atual.
- **Observações:** Bridge estreita Connected↔Aeronautics/Sable. Mixin configs físicos: `aeroconnectedfluidvessel.aeronauticsconnected.mixins.json` e `aeroconnectedfluidvessel.mixins.json`. Nenhuma config própria de usuário foi confirmada neste lote; manter fail-closed.
- **Procedência:** modlist.txt física do projeto consultada em 14/09/2026 + CurseForge oficial Create Aero + Connected FluidVessel Compat 1.0.0 + dossiê técnico revalidado. Autoridade física: `aeroconnectedfluidvessel-1.0.0.jar` / `1.0.0`, SHA-1 `ce5be5d77836c966cc99d690b108409537ca1171`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-and-connected-fluid-vessel
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 14/09/2026 — dossiê operacional aprofundado com ownership, mixins, lifecycle, multiplayer, fingerprint físico e matriz de validação; JAR/runtime preservados.
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


## Ownership técnico e superfícies alteradas
- **Ownership primário:** interoperabilidade `Create: Connected` ↔ `Create Aeronautics/Sable` para `FluidVessel` e `Steam Vent`; não possui o sistema-base de fluidos, vapor ou física.
- **Mod ID físico:** `aeroconnectedfluidvessel`.
- **Mixin configs expostos no inventário físico:** `aeroconnectedfluidvessel.aeronauticsconnected.mixins.json` e `aeroconnectedfluidvessel.mixins.json`.
- A presença desses dois configs confirma uma superfície de patch dedicada à integração Aeronautics/Connected, mas **as classes e métodos-alvo individuais não foram inferidos apenas pelo nome dos arquivos**.
## Configuração e dados
Não foi confirmada, nas fontes auditadas deste lote, uma superfície de configuração própria destinada ao usuário. O comportamento documentado é de bridge técnica. Qualquer chave/config adicional encontrada em runtime deve ser tratada como pendência de inventário, não presumida.
## Client/server, lifecycle e multiplayer
O estado funcional relevante — boiler, redstone do vessel, shafts e montagem física — precisa permanecer autoritativo no servidor. Não foi identificado um subsistema client-only autônomo nas fontes consultadas. O ciclo crítico é **carregar mundo → montar ship → operar boiler/vents → desmontar → salvar/recarregar → reconectar em servidor dedicado**; regressões podem aparecer apenas após recriação das block entities/physics entities.
## Fingerprint físico do snapshot
- JAR: `aeroconnectedfluidvessel-1.0.0.jar`
- Runtime: `1.0.0`
- SHA-1: `ce5be5d77836c966cc99d690b108409537ca1171`
- O fingerprint vem da `modlist.txt` física do projeto e é a autoridade deste snapshot.

## Testes recomendados
1. Montar FluidVessel + Steam Vents em terreno estático e confirmar steam/boiler.
2. Repetir em physics ship montado e em movimento.
3. Acionar vários vents do mesmo vessel com uma única entrada redstone e validar sincronização.
4. Montar/desmontar o ship repetidamente e verificar que shafts não viram physics entities independentes.
5. Validar persistência após save/reload e dedicated server.

## Evidências
- [CurseForge oficial — Create Aeronautics and Connected Fluid Vessel Compatability](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-and-connected-fluid-vessel)
- Modlist física atual e guia consolidado de Tecnologia do projeto.

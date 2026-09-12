# Alcubierre

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81fdb670d60b75dd865f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Alcubierre
- **Arquivo JAR:** `alcubierre-1.2.6.jar`
- **Versão 1.21.1:** `1.2.6`
- **Estado no pack:** Integrado ao Github
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #18: `alcubierre-1.2.6.jar` / `1.2.6` conferidos contra a modlist atual; JarJar `sable-companion-common-1.21.1-1.6.0.jar` continua interno, não top-level; corpo técnico, decisão e estado preservados.
- **Categoria:** Tecnologia
- **Compatibilidade/Riscos:** Warp interdimensional toca serialização/persistência de block entities, passageiros, constraints, redstone e cinética. Dimensional Sable precisa permanecer compatível com Sable. Antigravidade cruza estabilização/propulsão, mas com semântica distinta.
- **Decisão:** Sem decisão
- **Dependências:** Create + Sable; desde a versão 1.2.6 Dimensional Sable é obrigatório. Pack atual: Create 6.0.10, Sable 2.0.5 e Dimensional Sable 1.0.5 presentes. `sable-companion-common-1.21.1-1.6.0.jar` é jarjar interno do JAR.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-alcubierre
- **Função:** Tecnologia de physics ships: Antigravity Drive cancela gravidade enquanto alimentado pela rede cinética; Alcubierre Controller warpa o ship inteiro para coordenadas/dimensão configuradas.
- **Histórico da decisão:**
- **Observações:** Componente jarjar Sable Companion não conta como mod top-level. Validar transferências com block entities, inventários, seats, fluids e constraints antes de depender do warp em gameplay crítico.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Create Aeronautics: Alcubierre 1.2.6 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `alcubierre-1.2.6.jar` / `1.2.6`; JarJar interno não contado como mod físico independente; sem divergência top-level.
- **Sobreposição:** Não duplica Aeroworks: Aeroworks estabiliza/controla; Alcubierre cancela gravidade e teleporta o ship. Também não substitui Northstar/AeroStar ou portais gerais.
- **Data da última decisão:**

## Escopo e papel
Addon de tecnologia avançada para **Create/Sable** com duas mecânicas centrais: **Antigravity Drive**, que cancela a gravidade do physics ship enquanto recebe potência cinética, e **Alcubierre Controller**, que transfere o ship inteiro para coordenadas e dimensão configuradas.

## Runtime e autoridade
- JAR físico: `alcubierre-1.2.6.jar`.
- Mod ID: `alcubierre`.
- Runtime: `1.2.6`.
- O JAR embarca `sable-companion-common-1.21.1-1.6.0.jar` via jarjar; esse componente é interno e não é outra entrada top-level.

## Dependências
- O projeto declara integração com Create e Sable.
- **A partir de 1.2.6, Dimensional Sable é obrigatório.**
- Pack atual: Create `6.0.10`, Sable `2.0.5` e Dimensional Sable `1.0.5` presentes.

## Mecânicas
O Antigravity Drive conecta-se à kinetic network; enquanto energizado, neutraliza a gravidade aplicada à contraption, permitindo hover sem depender de lift aerodinâmico. O Alcubierre Controller recebe dimensão/coordenadas e pode ser ativado pelo painel ou redstone para warpar a estrutura completa. O Glimmering Bottle usado no crafting do drive é obtido enchendo uma garrafa abaixo de Y=-40 no End, conforme documentação upstream.

## Compatibilidade, sobreposição e riscos
O domínio cruza outros sistemas de voo/estabilização, mas a semântica é distinta: Aeroworks estabiliza orientação; wings/propulsion produzem forças convencionais; Alcubierre cancela gravidade e faz warp dimensional. A maior superfície de risco é transferência de stateful block entities, passageiros, constraints e cinética entre dimensões. Dimensional Sable precisa permanecer compatível com a versão do Sable instalada.

## Limites
Não fornece exploração espacial por planetas/progressão própria como Northstar/AeroStar. O warp é uma capacidade de ship; não substitui portais gerais do mundo. Antigravidade não equivale a controle completo de atitude ou propulsão horizontal.

## Testes recomendados
1. Antigravity Drive em ships leves/pesados e com diferentes RPM/power states.
2. Perda súbita de potência e restauração de gravidade.
3. Warp intra-dimensão e interdimensional com ship mínimo.
4. Warp com inventários, fluids, block entities, redstone, seats e passageiros.
5. Repetir com constraints/docking e desmontagem após warp.
6. Save/reload e dedicated-server smoke após transferências sucessivas.

## Evidências
- [CurseForge oficial — Create Aeronautics: Alcubierre](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-alcubierre)
- Modlist física atual e guia consolidado de Tecnologia.

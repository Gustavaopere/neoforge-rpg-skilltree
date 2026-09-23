# Create Aeronautics: FTB Chunks

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#129**: JAR `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar`, mod id `create_aeronautics_ftb_chunks`, runtime `1.2.0`, SHA-1 `d505c3675adb535a23b2abc794e034bc809098d4`.

## Propriedades do registro

- **Mod:** Create Aeronautics: FTB Chunks
- **Arquivo JAR:** create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar
- **Versão 1.21.1:** 1.2.0
- **Categoria:** Compat, Tecnologia
- **Função:** Integra subníveis/veículos físicos do Create Aeronautics com claims, permissões e force-loading do FTB Chunks/FTB Teams.
- **Dependências:** Obrigatórias e fisicamente presentes: Create Aeronautics 1.3.2 + FTB Chunks 2101.1.22 + FTB Teams 2101.1.11.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: claim órfão/remapeado incorretamente em ship movement, force-load/physics performance, quota, ACL stale após team changes, bypass por wrench/glue/kinetic breaker e regressão de shutdown/save em sublevels. `allow_plot_chunk_force_load` e `allow_physics_force_load` vêm OFF por padrão upstream; não assumir o estado runtime sem ler a config física. A 1.2.0 atualiza para latest Sable e corrige Save & Quit stuck, tornando shutdown/restart um regression gate explícito.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-ftb-chunks
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_aeronautics_ftb_chunks` 1.2.0 + publicação oficial 1.2.0 de 15/09/2026 já auditada. Revalidação física/documental em 20/09/2026.
- **Observações:** JAR físico `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar`, mod id `create_aeronautics_ftb_chunks`, runtime 1.2.0. Release oficial NeoForge 1.21.1 de 15/09/2026: update to latest Sable e fix do problema de Save & Quit ficar travado. Os settings `allow_plot_chunk_force_load` e `allow_physics_force_load` permanecem relevantes; a config física do pack é authority do estado efetivo.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — registro histórico do lote físico #128 na numeração então registrada; posição física atual #129: create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar / 1.2.0 confirmados; update para Sable atual e correção de save & quit preso permanecem registrados.
- **Decisão:** Sem decisão
- **Histórico da decisão:** 2026-09-18 — runtime físico atualizado para 1.2.0; nenhuma decisão curatorial nova.
- **Sobreposição:** Complementa Aeronautics e FTB Chunks; não substitui nenhum deles.

> 🗺️ **ESCOPO CANÔNICO.** Runtime físico: `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar`, mod id `create_aeronautics_ftb_chunks`, versão `1.2.0`. A bridge aplica claims/permissões/force-loading do FTB Chunks a ships/sublevels de Create Aeronautics. A 1.2.0 atualiza para latest Sable e corrige o problema de Save & Quit ficar travado.

## 1. Contraption Claim Block
O bloco central é o **Contraption Claim Block** colocado no ship. Sua tela permite:
- claim/unclaim dos chunks do ship para o FTB team;
- force loading;
- opcionalmente manter physics simulation ativa sem jogadores próximos;
- alternar acesso entre Private, Allies e Public;
- gerenciar allies quando a team é party.
Há limite de **um claim block por ship**. Quebrá-lo libera os claims associados.
## 2. Authority de claims
FTB Chunks continua authority de claim/force-load e FTB Teams continua authority de team/allies. Aeronautics/Sable continuam authority da geometria do ship/sublevel. A bridge traduz a área móvel do ship para a política de claims; não deve manter uma segunda ACL paralela.
## 3. Proteção de interação
Quando o ship está claimed, o projeto bloqueia ações de outros jogadores e também integra ferramentas Create como wrench, glue e kinetic block breakers à proteção. Portanto proteção precisa ser testada em **ações indiretas**, não só break vanilla.
## 4. Force loading e física
O projeto expõe dois settings server-side sensíveis a performance: `allow_plot_chunk_force_load` e `allow_physics_force_load`. A documentação oficial atual confirma que são controles separados para manter anchor chunks carregados e manter physics ticking sem players, e que **ambos vêm desativados por padrão** devido ao impacto de performance.
A política efetiva do pack continua sendo definida pela config física: os defaults upstream não provam o estado runtime do servidor. Mesmo quando force loading é habilitado, quotas/políticas do FTB Chunks continuam relevantes. A 1.1.1 havia atualizado a integração para a linha recente do Sable e adicionado a chunk-loading API usada pela bridge. A release física 1.2.0 atualiza novamente para latest Sable e corrige o problema de Save & Quit ficar travado, tornando shutdown/save/restart um regression gate explícito.
## 5. Runtime físico
- Create Aeronautics 1.3.2.
- FTB Chunks 2101.1.22.
- FTB Teams 2101.1.11.
A build 1.2.0 é Release NeoForge 1.21.1 de 15/09/2026. O stack está presente e deve ser testado como conjunto; não há motivo para manter esta bridge se qualquer provider obrigatório for removido.
## 6. Ship movement e remapeamento
Claims precisam acompanhar o ship/sublevel sem deixar proteção órfã em posições antigas nem permitir gaps durante movement/load. Restart, disassembly/reassembly e mudança de dimensão/sublevel são superfícies de risco mesmo que o projeto abstraia parte delas.
## 7. Multiplayer e ownership
Mudança de Private/Allies/Public deve ser server-authoritative. Transferência de team/party, saída de membro e quebra do claim block precisam atualizar acesso sem manter cache stale em clientes.
## 8. Riscos
1. Claim fica órfão após ship removal/disassembly.
2. Duas áreas recebem proteção para o mesmo ship.
3. Force loading excede quota/custo esperado.
4. Physics force-load mantém contraption pesada tickando offline.
5. Wrench/glue/kinetic breaker contorna ACL.
6. Allies/team state fica stale após mudança de party.
7. API drift FTB Chunks/Teams, Aeronautics ou Sable altera contract.
8. Save & Quit volta a travar com ship/sublevel ativo ou force-loaded.
## 9. Boundary para automação/quests
Claim, acesso e force-loading são infraestrutura, não milestones por si. Não conceder progresso por simples abertura da GUI ou por tick de chunk loaded. Se houver quest de segurança, usar mudança autoritativa e deduplicável do claim state.
## 10. Matriz de testes
- [ ] Dedicated server inicia com bridge 1.2.0 + FTB Chunks 2101.1.22 + Teams 2101.1.11 + Aeronautics 1.3.2.
- [ ] Claim/unclaim acompanha o mesmo ship sem sobras.
- [ ] Private/Allies/Public respeitam team atual após reconnect.
- [ ] Wrench, glue e kinetic breaker são bloqueados para não autorizados.
- [ ] Quebrar Claim Block libera todos os claims do ship.
- [ ] Force loading respeita quota/policy.
- [ ] Physics offline só permanece ativa quando configuração autoriza.
- [ ] Restart/disassembly não deixa ACL stale.
- [ ] Save & Quit encerra normalmente com ship/sublevel ativo, cobrindo a correção específica da 1.2.0.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 11. Evidências e limite
CurseForge oficial confirma o Contraption Claim Block, claim/force-load/access/allies, bloqueio de ferramentas Create, one-block-per-ship e requisitos Aeronautics + FTB Chunks + FTB Teams. A release 1.2.0 de 15/09/2026 registra update to latest Sable e fix para Save & Quit stuck. Os valores efetivos da config do pack não foram lidos nesta ficha.

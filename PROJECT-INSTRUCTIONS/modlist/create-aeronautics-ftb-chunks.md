# Create Aeronautics: FTB Chunks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e3a7bee57e01e609e7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics: FTB Chunks
- **Arquivo JAR:** `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.1.1.jar`
- **Versão 1.21.1:** 1.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Integra subníveis/veículos físicos do Create Aeronautics com claims, permissões e force-loading do FTB Chunks/FTB Teams.
- **Dependências:** Obrigatórias e fisicamente presentes: Create Aeronautics 1.3.2 + FTB Chunks 2101.1.22 + FTB Teams 2101.1.11.
- **Sobreposição:** Complementa Aeronautics e FTB Chunks; não substitui nenhum deles.
- **Compatibilidade/Riscos:** Riscos: claim órfão/remapeado incorretamente em ship movement, force-load/physics performance, quota, ACL stale após team changes e bypass por wrench/glue/kinetic breaker. Config real deve ser lida antes de assumir force-load/physics ativos.
- **Observações:** JAR físico `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.1.1.jar`, mod id `create_aeronautics_ftb_chunks`, runtime 1.1.1. Release oficial NeoForge 1.21.1 de 12/07/2026.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Create Aeronautics: FTB Chunks 1.1.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-ftb-chunks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; Contraption Claim Block, claims/access, force-loading, physics ticking, team ownership e protection bypass tests catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🗺️ **ESCOPO CANÔNICO.** Runtime físico: `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.1.1.jar`, mod id `create_aeronautics_ftb_chunks`, versão `1.1.1`. A bridge aplica claims/permissões/force-loading do FTB Chunks a ships/sublevels de Create Aeronautics.

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
O projeto expõe dois settings server-side sensíveis a performance: `allow_plot_chunk_force_load` e `allow_physics_force_load`. A documentação atual indica que são controles separados para manter anchor chunks carregados e manter physics ticking sem players.
A política real do pack deve ser lida da config antes de assumir ambos ativos. Mesmo quando force loading é permitido, quotas do FTB Chunks continuam relevantes.

## 5. Runtime físico
- Create Aeronautics 1.3.2.
- FTB Chunks 2101.1.22.
- FTB Teams 2101.1.11.

A build 1.1.1 é Release NeoForge 1.21.1. O stack está presente e deve ser testado como conjunto; não há motivo para manter esta bridge se qualquer provider obrigatório for removido.

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
7. API drift FTB Chunks/Teams ou Aeronautics altera contract.

## 9. Boundary para automação/quests
Claim, acesso e force-loading são infraestrutura, não milestones por si. Não conceder progresso por simples abertura da GUI ou por tick de chunk loaded. Se houver quest de segurança, usar mudança autoritativa e deduplicável do claim state.

## 10. Matriz de testes
- [ ] Dedicated server inicia com bridge 1.1.1 + FTB Chunks 2101.1.22 + Teams 2101.1.11 + Aeronautics 1.3.2.
- [ ] Claim/unclaim acompanha o mesmo ship sem sobras.
- [ ] Private/Allies/Public respeitam team atual após reconnect.
- [ ] Wrench, glue e kinetic breaker são bloqueados para não autorizados.
- [ ] Quebrar Claim Block libera todos os claims do ship.
- [ ] Force loading respeita quota/policy.
- [ ] Physics offline só permanece ativa quando configuração autoriza.
- [ ] Restart/disassembly não deixa ACL stale.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limite
CurseForge oficial confirma o Contraption Claim Block, claim/force-load/access/allies, bloqueio de ferramentas Create, one-block-per-ship e requisitos Aeronautics + FTB Chunks + FTB Teams. Os valores efetivos da config do pack não foram lidos nesta ficha.

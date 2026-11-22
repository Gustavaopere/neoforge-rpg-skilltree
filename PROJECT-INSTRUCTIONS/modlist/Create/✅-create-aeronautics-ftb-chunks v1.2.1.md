# Create Aeronautics: FTB Chunks — 1.2.1 (instalação declarada em 22/11/2026)

> **Versão vigente declarada pelo usuário:** 1.2.0 → **1.2.1**, arquivo oficial `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.1.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-ftb-chunks/files/9663793)). O novo JAR local e seu metadata/SHA não foram inspecionados nesta sessão.

> **Deltas da cadeia de publicações:** Corrige crash/travamento no shutdown após retirada de force-load de navio e terreno que descarregava após 15 s; compatibilidade Sable 2.0.5+ declarada. O ambiente de desenvolvimento cita Aeronautics 1.3.2/Chunks 2101.1.24/NeoForge 21.1.266, não um mínimo de manifesto confirmado.

> **Dependências pendentes:** Aeronautics, Sable, FTB Chunks e FTB Teams; versões do stack ainda em sincronização documental por lote. Não impede esta atualização.

> **Compatibilidade e QA:** Claims e permissões Private/Allies/Public, quota de force-load, navios em movimento, chunks, restart e shutdown ainda sem QA.

> **Leitura do histórico:** referências antigas a versão 'física atual', 'instalada' ou 'não instalada' abaixo descrevem o snapshot e a auditoria anterior, não o estado documental declarado agora. A posição física 129 e o JAR antigo `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar` são mantidos como rastreabilidade.


> **Snapshot físico histórico — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#129**: JAR `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar`, mod id `create_aeronautics_ftb_chunks`, runtime `1.2.0`, SHA-1 `d505c3675adb535a23b2abc794e034bc809098d4`.

## Propriedades do registro

- **Mod:** Create Aeronautics: FTB Chunks
- **Arquivo JAR instalado (declarado pelo usuário):** `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.1.jar` — publicado no [CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-ftb-chunks/files/9663793); arquivo local não relido.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.2.1` (antes `1.2.0`; metadata da nova build não extraído).
- **Categoria:** Compat, Tecnologia
- **Função:** Integra subníveis/veículos físicos do Create Aeronautics com claims, permissões e force-loading do FTB Chunks/FTB Teams.
- **Dependências:** Aeronautics, Sable, FTB Chunks e FTB Teams; versões do stack ainda em sincronização documental por lote. Não impede esta atualização.
- **Estado no pack:** `1.2.1` instalado segundo declaração do usuário; documentação sincronizada, sem inspeção local.
- **Estado da pesquisa:** versão-alvo e artefato oficial confirmados; publicações intermediárias documentadas nas seções históricas; binário e QA pendentes.
- **Compatibilidade/Riscos:** Claims e permissões Private/Allies/Public, quota de force-load, navios em movimento, chunks, restart e shutdown ainda sem QA.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-ftb-chunks
- **Procedência:** posição histórica #129, declaração de atualização em 22/11/2026 e [publicação oficial](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-ftb-chunks/files/9663793); nenhum arquivo local novo foi lido.
- **Observações vigentes:** arquivo publicado `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.1.jar` declarado instalado, sem SHA local; snapshot 1.2.0 apenas histórico.
- **Atualização/Status:** 22/11/2026 — `1.2.0` → `1.2.1`; Corrige crash/travamento no shutdown após retirada de force-load de navio e terreno que descarregava após 15 s; compatibilidade Sable 2.0.5+ declarada. O ambiente de desenvolvimento cita Aeronautics 1.3.2/Chunks 2101.1.24/NeoForge 21.1.266, não um mínimo de manifesto confirmado.
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

## Auditoria incremental upstream — Create Aeronautics: FTB Chunks 1.2.1 (22/11/2026; não instalada)

Físico #129 `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.0.jar` / 1.2.0. A [release oficial **1.2.1**, CurseForge 9663793](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-ftb-chunks/files/9663793) foi publicada em **17/11/2026** para **NeoForge Minecraft 1.21.1**, filename `create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.2.1.jar`. Changelog por arquivo:
- Corrige **crash de servidor e possível travamento no encerramento após remover force-load de uma ship**.
- Compatibilidade declarada com **Sable 2.0.5 ou superior** (antes apenas 2.0.5).
- Corrige o terreno abaixo de ship force-loaded, que antes podia **descarregar após 15 s**.
- Atualiza o ambiente do autor para **Create Aeronautics 1.3.2, FTB Chunks 2101.1.24 e NeoForge 21.1.266**. Essas versões citadas no build **não comprovam intervalos de dependência do artefato**; o perfil histórico registra NeoForge **21.1.250**, portanto o loader deve ser conciliado por metadata e testes, sem presumir compatibilidade nem bloquear como requisito obrigatório sem `versionRange` comprovado.

**QA:** force-load/unload/reload/restart, terreno e chunks sob ships, claims ACL, allies/groups/team, persistência de sublevels e constraints, snapshots de Sable e FTB Chunks realmente instalados, desligamento dedicado com/sem barcos em movimento e FTB permission checks. Não promover o upgrade sem backup/teste e inspeção de JAR/metadados; **sem alteração física**. Arquivo e pasta `Create` preservados.

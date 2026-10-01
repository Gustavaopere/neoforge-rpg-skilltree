# CBCAT Fix

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#90**: JAR `cbcatfix-1.21.1-neoforge-1.0.1.jar`, mod id `cbcatfix`, runtime `1.0.0`, SHA-1 `d204cbac7d11942a4e7d025e5c4e743ac329b6cd`. Filename físico `1.0.1`; metadata/runtime permanece **`1.0.0`**, divergência preservada sem normalização artificial.

## Propriedades do registro

- **Mod:** CBCAT Fix
- **Arquivo JAR:** `cbcatfix-1.21.1-neoforge-1.0.1.jar`
- **Versão 1.21.1:** `1.0.0`
- **Categoria:** Compat; Tecnologia; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cbc-advanced-technologies-aeronautics-fix
- **Função:** Patch/addon para CBC: Advanced Technologies que corrige crashes e cluster munitions, integra loading de rockets por Mechanical Arm, corrige Rocket Pod e adiciona big rockets/rails e shells Flak/Heavy HE/HESH/HEAT.
- **Dependências:** Create Big Cannons + Create Big Cannons: Advanced Technologies, conforme finalidade oficial do patch. A necessidade deve ser reavaliada quando upstream CBC/CBC:AT mudar.
- **Compatibilidade/Riscos:** Patch/addon sensível a version drift de CBC/CBC:AT. Riscos de fix duplicado após upstream, rocket dupe em Mechanical Arm/rail, cluster munition double-processing e recipe conflicts. Filename 1.0.1 diverge da metadata runtime 1.0.0. Upstream 1.1.x altera profundamente rockets, ballistics, Sable physicalization, configs e conteúdo legado; a atualização também introduz integração opcional com Create: Radars/RWR e requer atenção a world migration por remoção do HESH próprio.
- **Sobreposição:** Corrige/estende CBC:AT; não é substituto do addon base. Deve ser removido apenas após confirmar que upstream incorporou os mesmos fixes/conteúdo sem conflito.
- **Observações:** Arquivo publicado/build 1.0.1; mod id `cbcatfix`; runtime físico 1.0.0. Changelog 1.0.1: crash fix, cluster munitions fix, Mechanical Arm rocket loading, Rocket Pod craft fix, big rocket/rails e Flak/Heavy HE/HESH/HEAT. A linha upstream para 1.21.1 avançou depois por 1.1.0, 1.1.1 e 1.1.2; essas releases estão documentadas separadamente abaixo e não são atribuídas ao runtime instalado.
- **Procedência:** modlist(1).txt física atual de 20/09/2026 + metadata runtime/log atual + CurseForge oficial CBCAT Fix build 1.0.1 e changelog da release, além das fontes já auditadas. Reconciliação final: filename físico `cbcatfix-1.21.1-neoforge-1.0.1.jar` e runtime metadata `1.0.0` permanecem deliberadamente distintos; nenhuma divergência foi mascarada.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a build física `cbcatfix-1.21.1-neoforge-1.0.1.jar` foi auditada; a metadata runtime continua 1.0.0. A presença do patch não foi convertida em decisão automática de manter/remover.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — artefato físico continua `cbcatfix-1.21.1-neoforge-1.0.1.jar` / metadata `1.0.0`. O upstream NeoForge 1.21.1 avançou por **1.1.0 → 1.1.1 → 1.1.2**; os três deltas foram revisados integralmente e registrados abaixo. A 1.1.0 é uma atualização de rockets/ballistics/Sable com migração de HESH, a 1.1.1 adiciona integração Radars/RWR/jamming e a 1.1.2 corrige crash no lançamento do Minecraft.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs
> ✅ Artefato físico confirmado: `cbcatfix-1.21.1-neoforge-1.0.1.jar`, mod id `cbcatfix`, metadata runtime **1.0.0**. O filename publicado é 1.0.1, mas o runtime observado continua 1.0.0; a divergência é preservada em vez de ser corrigida por inferência.
## 1. Papel e authority
CBCAT Fix é um patch/addon para **Create Big Cannons: Advanced Technologies (CBC:AT)** e seu stack Create Big Cannons. A função original declarada é corrigir crashes com versões recentes de CBC:AT, mas a build 1.0.1 também contém correções funcionais e conteúdo adicional.
Create Big Cannons continua authority de cannon/munition fundamentals; CBC:AT continua authority de suas armas/tecnologias; CBCAT Fix corrige/adiciona apenas a superfície publicada nesta build.
## 2. Release 1.0.1 — escopo confirmado
O changelog oficial da 1.0.1 registra:
- crash fix;
- fix de **cluster munitions**;
- rockets carregáveis em rail por **Mechanical Arm**;
- fix da recipe do **Rocket Pod**;
- **big rocket** e **big rocket rails**;
- shells **Flak**, **Heavy HE**, **HESH** e **HEAT**.
Essa lista é release-specific e pode ser tratada como conteúdo confirmado da build publicada.
## 3. Crash patch
O projeto existe para resolver incompatibilidades/crashes da versão moderna de CBC:AT. Sem source/tag suficientemente auditado, não se inventa qual classe/mixin é alterado.
Operacionalmente, qualquer atualização de CBC/CBC:AT pode invalidar o patch. Se o upstream incorporar a correção, manter o fix pode passar de necessário a conflitante; isso deve ser comprovado por runtime/source, não por nome do mod.
## 4. Cluster munitions
A 1.0.1 declara correção de cluster munitions. A authority de explosão, submunições, dano e block interaction deve permanecer no provider CBC/CBC:AT conforme a implementação final.
Integrações de combate não devem contar o impacto principal e cada submunição como casts/ações independentes sem preservar causalidade.
## 5. Rockets e Mechanical Arm
A build permite carregar rockets em rail usando **Mechanical Arm**. Isso cria uma bridge concreta com automação Create:
- Create controla o arm/transfer;
- CBC:AT/CBCAT Fix controla eligibility do rocket/rail;
- a operação precisa inserir exatamente uma munição aceita.
Retry ou braço concorrente não pode duplicar rocket nem deixar rail e inventário ambos contendo o mesmo stack.
## 6. Rocket Pod recipe
O changelog corrige a craft do Rocket Pod. Recipes pertencem ao datapack/provider e podem mudar entre versões; KubeJS/compat externa não deve reintroduzir a recipe antiga sob o mesmo ID ou produzir duas rotas conflitantes sem intenção explícita.
## 7. Big Rocket e rails
A 1.0.1 adiciona **big rocket** e **big rocket rails**. O catálogo não inventa damage, velocidade, propellant, calibres ou recipes ausentes da documentação pública.
Esses registries precisam ser enumerados diretamente no JAR/source caso alguma integração própria exija IDs concretos.
## 8. Shells adicionais
Conteúdo confirmado por nome: Flak, Heavy HE, HESH e HEAT shells. A ficha não converte o significado balístico dos nomes em números de penetração/explosão sem dados versionados.
Qualquer bridge RPG/armor deve observar o damage/event final do provider e não aplicar uma segunda fórmula por reconhecer “HEAT” ou “HESH” no nome.
## 9. Client/server e multiplayer
Ammo loading, recipes, projectile spawn, explosion/damage e rail state são server-authoritative. Models, particles e HUD são client-side.
Em multiplayer, arms/jogadores concorrentes acessando o mesmo rail devem serializar inventory transfer; projéteis precisam manter shooter/owner/cannon attribution para proteção, kill credit e perks.
## 10. Lifecycle
Validar startup com versões físicas de CBC/CBC:AT, resource/datapack reload, rail load/unload, chunk unload, server restart, rocket insertion por Mechanical Arm e projectile persistence. O patch não deve depender de ordem de carregamento acidental.
## 11. Riscos
1. Version drift com CBC/CBC:AT tornar patch incompatível.
2. Fix duplicado depois de upstream incorporar a correção.
3. Rocket dupe em Mechanical Arm/rail.
4. Cluster munition settlement duplicado.
5. Recipe antiga + nova coexistirem por scripts externos.
6. Projectile ownership perdido após chunk crossing.
7. Filename 1.0.1 ser confundido com metadata runtime 1.0.0.
## 12. Matriz de testes
1. Dedicated server boot com CBC + CBC:AT + CBCAT Fix.
2. Reproduzir o crash que o patch pretende eliminar em ambiente de teste.
3. Cluster munitions: spawn, submunitions e damage exatamente uma vez.
4. Mechanical Arm carregando rockets em rail.
5. Duas fontes de automação competindo pelo mesmo rail sem dupe.
6. Rocket Pod recipe via recipe viewer/crafting.
7. Big rocket + big rocket rails: load/fire/reload/save.
8. Flak/Heavy HE/HESH/HEAT shells: load/fire/projectile attribution.
9. Datapack/resource reload.
10. Atualizar CBC/CBC:AT em ambiente separado e verificar se o patch ainda é necessário.
## 13. Evidência
- modlist/log físico: `cbcatfix-1.21.1-neoforge-1.0.1.jar`, runtime `cbcatfix` 1.0.0;
- CurseForge oficial do arquivo 1.0.1;
- changelog oficial: crash fix, cluster munitions, Mechanical Arm rocket loading, Rocket Pod recipe, big rocket/rails e quatro shells;
- página CBC:AT do catálogo usada apenas para ownership/contexto, sem fundir os dois top-levels.
> 🛠️ Boundary canônico: CBCAT Fix é um patch/addon **separado** de CBC:AT. Conteúdo e fixes devem ser processados uma vez; não duplicar registries/recipes por tratar o patch como um segundo CBC:AT.

## 14. Atualizações upstream 1.1.0 → 1.1.2 — não instaladas
A autoridade física do pack continua em **build 1.0.1**, com metadata runtime **1.0.0**. Para NeoForge 1.21.1, o upstream publicou depois **1.1.0**, **1.1.1** e **1.1.2**. Nenhuma delas está instalada neste snapshot.

### 1.1.0 — Sable Rocket
A 1.1.0 é uma mudança funcional grande, não apenas um crash fix incremental.

**Rockets e balanceamento**
- passa a oferecer **33 variantes de rocket em 11 tipos**, cada tipo com variantes lightweight, double-fuel e double-payload;
- revisa características de rockets small/medium/large para separar melhor seus papéis;
- variantes lightweight recebem **20% mais powered target speed**, em troca de menor durability e fuel capacity;
- ajusta fire rate de small launchers e efeitos de algumas munições;
- preserva trade-offs entre mobilidade, duração de powered flight e força do payload.

**Flight e guidance**
- drag e gravity do airframe passam a depender do **tamanho do rocket**, não do warhead;
- após engine burnout, rockets continuam em voo balístico usando o comportamento de gravity/drag do CBC;
- guided rockets precisam limpar o launcher antes de iniciar steering;
- corrige interação que podia sobrescrever a orientação de guided rockets com o alinhamento padrão de projectile;
- refina colisões com obstáculos leves, dano do airframe e engine shutdown após impactos fortes.

**Launchers e Sable**
- corrige uma rota de **duplicação de munição durante physicalization/dephysicalization do Sable**;
- melhora transferência de inventory entre source/destination blocks do launcher;
- remove recoil de cannon dos rocket launchers sem alterar recoil normal dos cannons;
- preserva carrier velocity herdada quando o lançamento ocorre de estruturas Sable em movimento.

**Interface/config**
- agrupa variantes de rocket no Creative menu;
- amplia tooltips via SHIFT;
- move gameplay settings para o sistema de **server config do NeoForge**;
- atualiza localização EN/UK.

**Migração de mundo — HESH**
- o HESH próprio do CBCAT Fix é **removido**;
- itens/blocos/entities legados `cbcatfix:hesh_shell` são mapeados para **Heavy HE**;
- HESH de outros mods não é afetado;
- `config/cbcatfix-server.toml` preserva valores salvos, portanto configs antigas podem sobrescrever os novos defaults de balanceamento.

**Pisos/dependências declarados pela release:** Minecraft 1.21.1, NeoForge 21.1.228+, Create + Create Big Cannons + CBC: Advanced Technologies; integrações opcionais incluem Sable, Create: Radars e Jade. O snapshot físico contém Create: Radars 0.4.9.4, Sable 2.0.5 e Jade 15.10.6, portanto esses caminhos opcionais são relevantes para teste.

### 1.1.1 — Radars / RWR / jamming
A release 1.1.1 adiciona uma segunda camada material:
- **chaff** e **directional jamming** via Radars API;
- melhora tratamento de contatos de radar perdidos/falsos, preservando last-known target position e fire-and-forget guidance;
- adiciona emissões de **RWR** para active radar seekers, incluindo cleanup quando guidance termina;
- restaura o guided fuze original do Radars no Creative tab e adiciona recipe;
- valida coexistência com Create: Kaboom no ambiente de desenvolvimento;
- mantém as integrações opcionais.

O changelog registra uma limitação conhecida da versão **Radars 5 EA** usada no upstream: startup pode falhar quando Sable não está presente. O pack físico usa Create: Radars **0.4.9.4**, não a linha 5 EA; portanto essa limitação é registrada como upstream lineage, não como falha local confirmada.

Há uma inconsistência de nomenclatura na publicação: a página CurseForge identifica a release como **1.1.1**, enquanto o campo de filename exposto nessa página aparece como `cbcatfix-1.1.0.jar`. O catálogo preserva ambos sem tentar normalizar por inferência.

### 1.1.2 — hotfix de inicialização
A 1.1.2 é o latest NeoForge 1.21.1 localizado, publicada em 24/09/2026. Seu changelog é específico: **corrige crash ao iniciar o Minecraft**. O file page expõe `cbcatfix-1.1.2.jar`, enquanto o título do artefato é `cbcatfix-1.21.1-neoforge-1.1.2.jar`.

### Impacto e gate de promoção
Uma atualização 1.0.1 → 1.1.2 não deve ser tratada como patch trivial. O gate mínimo inclui:
1. backup de mundo antes da migração por remoção/mapeamento do HESH próprio;
2. inspeção de `cbcatfix-server.toml` para defaults antigos preservados;
3. rockets small/medium/large e variantes lightweight/double-fuel/double-payload;
4. engine burnout → ballistic flight, launcher-clear-before-guidance e hard-impact shutdown;
5. Sable physicalize/dephysicalize sem dupe, inherited carrier velocity e inventory transfer;
6. Mechanical Arm/rails antigos continuando exactly-once;
7. Create: Radars 0.4.9.4: chaff, jamming, lost/false contacts, guided fuze e RWR lifecycle;
8. dedicated server + client boot em 1.1.2, incluindo o hotfix de Minecraft launch;
9. save/restart/chunk unload de rockets/rails e ausência de stale seeker/RWR emission;
10. revisar scripts/recipes/integrations que ainda referenciem `cbcatfix:hesh_shell`.

Fontes upstream revisadas: CurseForge file 1.0.1 (baseline instalada), Modrinth/project description da 1.1.0, CurseForge file 1.1.1 e CurseForge file 1.1.2.

## Preservação de propriedade histórica do Notion
> **Atualização/Status — valor histórico da origem:** PADRÃO ALEX'S MOBS REVALIDADO EM 20/09/2026 — reconciliação final física #89: artefato `cbcatfix-1.21.1-neoforge-1.0.1.jar` reconfirmado; metadata runtime `1.0.0` preservada sem normalização fictícia. Crash/cluster fixes, rocket automation, Rocket Pod, big rockets/rails e shell scope permanecem documentados.

O registro acima é mantido literalmente para paridade de migração. O cabeçalho atual do arquivo continua sendo a authority temporal mais recente para posição física e estado vigente.

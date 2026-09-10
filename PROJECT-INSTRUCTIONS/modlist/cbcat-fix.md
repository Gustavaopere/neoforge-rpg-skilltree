# CBCAT Fix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8131aeefc8b1f2956f5c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** CBCAT Fix
- **Arquivo JAR:** `cbcatfix-1.21.1-neoforge-1.0.1.jar`
- **Versão 1.21.1:** `1.0.0`
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — artefato `cbcatfix-1.21.1-neoforge-1.0.1.jar` e metadata runtime 1.0.0 reconfirmados; crash/cluster fixes, rocket automation, Rocket Pod, big rockets/rails e shell scope preservados. Runtime QA não executado.
- **Categoria:** Compat; Tecnologia; Automação
- **Compatibilidade/Riscos:** Patch/addon sensível a version drift de CBC/CBC:AT. Riscos de fix duplicado após upstream, rocket dupe em Mechanical Arm/rail, cluster munition double-processing e recipe conflicts. Filename 1.0.1 diverge da metadata runtime 1.0.0.
- **Decisão:** Sem decisão
- **Dependências:** Create Big Cannons + Create Big Cannons: Advanced Technologies, conforme finalidade oficial do patch. A necessidade deve ser reavaliada quando upstream CBC/CBC:AT mudar.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cbc-advanced-technologies-aeronautics-fix
- **Função:** Patch/addon para CBC: Advanced Technologies que corrige crashes e cluster munitions, integra loading de rockets por Mechanical Arm, corrige Rocket Pod e adiciona big rockets/rails e shells Flak/Heavy HE/HESH/HEAT.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a build física `cbcatfix-1.21.1-neoforge-1.0.1.jar` foi auditada; a metadata runtime continua 1.0.0. A presença do patch não foi convertida em decisão automática de manter/remover.
- **Observações:** Arquivo publicado/build 1.0.1; mod id `cbcatfix`; runtime físico 1.0.0. Changelog 1.0.1: crash fix, cluster munitions fix, Mechanical Arm rocket loading, Rocket Pod craft fix, big rocket/rails e Flak/Heavy HE/HESH/HEAT.
- **Procedência:** modlist.txt física atual de 08/09/2026 + metadata runtime/log atual + CurseForge oficial CBCAT Fix build 1.0.1 e changelog da release.
- **Sobreposição:** Corrige/estende CBC:AT; não é substituto do addon base. Deve ser removido apenas após confirmar que upstream incorporou os mesmos fixes/conteúdo sem conflito.
- **Data da última decisão:** não definida

## Dossiê operacional — padrão Alex's Mobs

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

# Easy NPC: Bundle

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#240**: JAR `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc_bundle`, runtime `7.12.1`, SHA-1 `2aaf47e6d2f586b8e7bafaf12b7c1f18eedd4d65`.

## Propriedades do registro

- **Mod:** Easy NPC: Bundle
- **Arquivo JAR:** `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar`
- **Versão 1.21.1:** `7.12.1`
- **Categoria:** RPG, QoL
- **Função:** Meta-package/bundle da família Easy NPC que declara/organiza a instalação alinhada do Core e Config UI; não implementa um segundo sistema de NPC nem substitui os módulos funcionais.
- **Dependências:** Easy NPC Core + Easy NPC Config UI; NeoForge 1.21.1. No pack atual, Core, Bundle e Config UI existem como três JARs top-level separados e alinhados pelo filename/release 7.12.1.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Risco principal é misturar versões diferentes da família ou interpretar Bundle como provider de gameplay. A linha 7.13.0 altera o protocolo de rede e exige client/server alinhados; 7.14.0 corrige replicação tardia no Core/UI. A forma de empacotamento exata requer inspeção do JAR: o build NeoForge 7.15.0 indica dependências publicadas separadamente, mas a descrição de metadados do Bundle ainda menciona Jar-in-Jar. Mod IDs duplicados e classloading continuam riscos que devem ser testados, não resultados já demonstrados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/all
- **Procedência:** modlist física atual de 21/09/2026 — 587 mods incluindo o modloader — confirma `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar`, mod id `easy_npc_bundle` e runtime `7.12.1`, junto de Core/Config UI na mesma linha. CurseForge 1.21.1 foi revalidado em 03/10/2026 até Bundle 7.14.0.
- **Observações:** Bundle 7.12.1 está fisicamente alinhado ao Core/Config UI 7.12.1. Upstream publicou Bundle 7.13.0 e 7.14.0; o meta-package continua com semântica de distribuição/dependência, enquanto gameplay/persistência pertencem ao Core.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — runtime físico permanece Bundle 7.12.1. As releases próprias posteriores 1.21.1 são 7.13.0 e 7.14.0; qualquer promoção deve manter Core/Bundle/Config UI coordenados.
- **Decisão:** Dependência
- **Sobreposição:** Não é duplicata funcional do Easy NPC Core. A sobreposição de nome é de distribuição: Bundle agrega dependências, Core fornece NPCs, Config UI fornece a interface.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `easy_npc_bundle-neoforge-1.21.1-7.12.1.jar` · mod id `easy_npc_bundle` · versão `7.12.1` · NeoForge 1.21.1.
## 1. Papel no modpack
Easy NPC: Bundle é um **meta-package de distribuição** da família Easy NPC. Sua função é facilitar instalação e alinhamento de dependências, não criar NPCs, diálogos, trades ou state próprio equivalente ao Core.
## 2. Arquitetura moderna do bundle
No **source NeoForge 7.15.0**, o Bundle declara Core/Config UI como dependências obrigatórias e o build as anuncia à distribuição, mas a descrição do manifesto ainda menciona Jar-in-Jar. **Sem inspeção do JAR distribuído, não é possível atestar ausência ou presença de módulos embarcados.** No último snapshot físico, Core, Bundle e Config UI figuram como três arquivos top-level.
O procedimento seguro é inspecionar metadata e JarJar efetivo em cada JAR e verificar se o loader registra exatamente uma instância de cada mod ID, prevenindo duplicidade, classloading ambíguo e dependências ausentes.
## 3. Dependências funcionais
O Bundle organiza:
- **Easy NPC Core** — provider real de NPC state/gameplay;
- **Easy NPC Config UI** — ferramentas gráficas e networking de configuração.
No pack atual os três aparecem como JARs top-level separados, alinhados na linha 7.12.1 por filename/build/publicação; a metadata runtime do Core continua não declarada.
## 4. Authority / ownership
- **Bundle:** relação de dependência/distribuição.
- **Core:** NPCs, persistência, dialogs, actions, trades e behavior.
- **Config UI:** screens/config flow/networking correspondente.
Nenhuma integração deve consultar o Bundle para obter NPC state quando o contrato pertence ao Core.
## 5. Client / Server
O Bundle pode estar presente nos dois lados por causa de dependency resolution do modpack, mas não deve ser tratado como gameplay authority. Client/server contracts reais vêm dos módulos que ele exige.
## 6. Lifecycle
O ponto crítico é o **bootstrap/dependency graph**:
- todas as dependências exigidas devem estar presentes;
- versões da família devem permanecer compatíveis;
- não carregar duas cópias do mesmo módulo;
- atualização do Bundle deve ser acompanhada de verificação do Core/Config UI efetivamente resolvidos.
## 7. Atualização e version alignment
Misturar Bundle com módulos de outra linha pode criar API/GUI/network drift mesmo que o launcher permita boot. No pack atual, Bundle, Config UI e filename/build do Core estão alinhados em **7.12.1**.
A coluna física de versão do Core continua vazia; o alinhamento da família não autoriza preencher metadata runtime ausente por inferência.
A linha instalada 7.12.1 deve ser tratada coordenadamente: atualizar apenas o Bundle ou apenas um módulo reabre risco de API/GUI/network drift.
## 8. Riscos
1. interpretar Bundle como segundo sistema de NPC;
2. duplicar conteúdo do Core na documentação/integração;
3. versões da família desalinhadas;
4. dependência ausente;
5. duas cópias do mesmo módulo;
6. voltar a usar jar-in-jar e criar mod ID duplicado;
7. classloading/handshake divergente entre cliente e servidor;
8. remover Core/Config UI porque o Bundle parece 'completo'.
## 9. Matriz de testes
1. Dedicated server boot com Core + Bundle + Config UI 7.12.1.
2. Client connect com o mesmo conjunto.
3. Confirmar presença única de cada mod id.
4. Abrir Config UI e editar NPC do Core.
5. Reiniciar servidor e confirmar persistência.
6. Atualizar a família em cópia de teste e verificar dependency resolution.
7. Remover uma dependência em instância descartável e confirmar falha clara, não comportamento parcial silencioso.
**Esta catalogação não afirma que esses testes foram executados.**
## 10. Evidências
- modlist física atual de 21/09/2026: Bundle `7.12.1` + Core build `7.12.1` + Config UI `7.12.1` como JARs top-level;
- evidências oficiais Easy NPC Bundle: build NeoForge 1.21.1 indica distribuição por dependências, mas a descrição no manifesto ainda refere Jar-in-Jar; composição física do artefato permanece pendente de inspeção.
> **Boundary canônico:** Bundle possui **dependency/distribution semantics**. NPC gameplay pertence ao Core; edição pertence ao Config UI.

## 11. Atualizações upstream 7.13.0 → 7.14.0 — não instaladas

A autoridade física continua em **Easy NPC: Bundle 7.12.1**. Na listagem 1.21.1 do projeto Bundle, as releases posteriores são **7.13.0** e **7.14.0**.

### 7.13.0
O Bundle continua sendo um meta-package, portanto as mudanças funcionais pertencem principalmente aos módulos que resolve. O delta mais crítico da família é a **mudança de network protocol**, com exigência explícita de cliente e servidor na mesma versão. Também entram correções de dialog buttons, owner/move item, skin error handling, preset search e novos tokens `@initiator`, `@npc` e `@score()`.

### 7.14.0
A família corrige:
- custom poses ausentes para jogadores que começam a observar o NPC mais tarde;
- attribute/trading settings ausentes para late viewers;
- max uses/XP/trading type não refletidos imediatamente nas telas;
- `/easy_npc sound set` não sincronizando sem reload;
- adiciona GameTests de pose/settings após respawn e para jogadores novos.

### Regra de promoção
O Bundle **não deve ser atualizado isoladamente**. Promover somente quando Core e Config UI correspondentes forem atualizados para a mesma linha e o handshake client/server for validado.

Gate:
- [ ] Core + Bundle + Config UI alinhados em 7.14.0.
- [ ] Client/server handshake sem mismatch de protocol.
- [ ] Presença única de cada mod id; nenhuma regressão jar-in-jar.
- [ ] Config UI abre e altera state do Core após upgrade.
- [ ] Late viewers e players respawnados recebem pose/settings/trading/sound state corretos.

Fonte upstream: CurseForge Easy NPC Bundle 1.21.1, sequência 7.12.1 → 7.13.0 → 7.14.0. Nenhum teste acima foi executado nesta atualização documental.

## Reauditoria de publicação e compatibilidade — 13/10/2026

**Projeto:** Easy NPC (bundle). **Última release pública identificada para Minecraft 1.21.1 / NeoForge:** `7.15.0` (`easy_npc_bundle-neoforge-1.21.1-7.15.0.jar`). **Referência oficial:** https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/all?page=1&pageSize=20&version=1.21.1.

A família Easy NPC publicou a linha 7.13.0→7.14.0→7.15.0, com bundle, Core e Config UI para NeoForge 1.21.1. O bundle é o elemento de distribuição/agregação e não fornece um segundo sistema de NPCs; inspecionar sua composição JarJar real antes de assumir ausência de módulos embarcados ou adicionar/remover quaisquer JARs. O changelog oficial de 7.15.0 foi revisado e suas mudanças verificadas estão descritas no complemento crítico abaixo; não extrapolar além dessas notas. Atualização coordenada deve verificar scripts/diálogos existentes, profissões/permissões e persistência de NPCs e seu estado, dependências de UI, rastreamento multiplayer e dedicated server. Evitar desalinhar versões da tríade.

**Autoridade física:** permanece o JAR/versão registrados nas seções históricas deste dossiê e na `modlist(1).txt`; NÃO foi disponibilizada nova lista física nem realizada instalação/teste in-game. **Recomendação:** atualização física pendente de reconciliação de dependências, SHA e validações; não interpretar o número publicado como versão instalada. **Filename `.md`:** ✅ herdado e versão do JAR físico preservados conforme protocolo canônico.

## Complemento crítico — changelog Easy NPC 7.15.0 e bloqueio da linha 1.21.1

A release 7.15.0 do **Core** (e do bundle vinculado) declara fixes que alteram comportamento persistente, não só interface:

- Preset import via `/execute` ou command blocks fica silencioso; limites de NPC também se aplicam ao spawner default.
- Sons de resource packs podem ser escolhidos na configuração; NPCs deixam de nascer canhotos aleatoriamente.
- Botões de diálogo com mesmo label em diálogos distintos deixam de compartilhar indevidamente o lock.
- Corrige NPCs deletados/duplicados que voltavam, vazamentos de memória, problemas de proprietário/GUI e falta do mob patch Epic Fight para NPC Wither Skeleton.
- **Correção histórica:** as opções de home e a preservação de IDs/posição dos presets são da **7.12.0**, já presentes antes do JAR 7.12.1, e não novidades de 7.15.0.
- Muda política de backup: dados de mundos e NPCs mortos expiram em **30 dias** conforme `storage.cfg`; revisar política local de retenção/backup antes do upgrade.
- **Novo requisito declarado para 7.15.0:** mesma versão do mod em cliente/servidor **e Easy Model Entities 2.5.0+**. O catálogo oficial consultado mostra Easy Model Entities 2.5.0 **para Minecraft 26.1.2**, mas a última publicação NeoForge **1.21.1** confirmada é **2.4.0**. **BLOQUEADO** migrar a tríade Easy NPC 7.15.0 no Minecraft 1.21.1 até sair build da dependência no alvo ou existir confirmação oficial inequívoca de compatibilidade alternativa. Não usar o JAR 26.1.2 em 1.21.1.

**Fontes e segregação por loader:** [changelog oficial da série 7.15.0](https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/9151672) (**esta página específica corresponde ao JAR Fabric 1.21.1; NÃO é o artefato NeoForge**); [bundle Easy NPC: releases filtradas por Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/all?version=1.21.1) (confirmar `easy_npc_bundle-neoforge-1.21.1-7.15.0.jar`); [Core: releases filtradas por Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all?version=1.21.1) (confirmar `easy_npc-neoforge-1.21.1-7.15.0.jar`); [Easy Model Entities: arquivos por loader/MC](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all) (última build NeoForge 1.21.1 identificada: 2.4.0). **Matriz obrigatória:** backup de NPCs, teste de import preservando UUID/owner/home, prevenção de respawn duplicado, scripts/commands, Resource-pack sounds, lock local de diálogo, retenção de backup, Epic Fight NPC poses, client+dedicated server na mesma versão.

## Revisão de conteúdo oficial 7.15.0 e cronologia — 13/10/2026

**Ownership:** o JAR `easy_npc_bundle-neoforge-1.21.1-7.15.0.jar` declara a distribuição modular; não implementa um segundo sistema de NPCs. O changelog do **Core 7.15.0** acrescenta as ações `Jump`, `Assist Owner`, `Follow Nearest Player`; inventário de NPC com coleta de itens e drops na morte; `Sit on Shoulder`, boss bar, animação `Hold`, preset Bank Teller, comando `/easy_npc debug core loglevel` e métodos de API de addons. Essas funções pertencem ao **Core**, não ao Bundle. Corrige ainda persistência/duplicação de NPCs, memory leaks, lock de botões por diálogo, limitações do spawner padrão, áudios em resource packs e patch Epic Fight do Wither Skeleton NPC.

**Linha temporal corrigida:** `Set Home Here`, `Reset Home`, IDs/posição preservados em export/import são de **7.12.0** (já anteriores ao JAR físico 7.12.1). Versão 7.13.0 introduziu novas macros de diálogo e protocolo de rede; 7.14.0 melhora sincronização de poses, trading e som a novos observadores. Não atribuir esses itens a 7.15.0.

**Gate crítico:** Core, Bundle e Config UI na mesma versão no client/server. O changelog do Core 7.15.0 declara **Easy Model Entities 2.5.0+**, embora a última build EME **NeoForge Minecraft 1.21.1** verificada seja **2.4.0**. A EME 2.5.0 publicada para **Minecraft 26.1.2** não pode substituir a de 1.21.1. Confirmar manifesto/optional fallback e obter publicação compatível antes de promover. Testar resolução de dependências, ausência de JARs embutidos duplicados, networking, editor, lifecycle, NPC inventory/pickup/drops sem dupe, boss bar, follower AI e persistência. Nenhum desses testes foi executado.

**Fontes oficiais:** [Easy NPC CHANGELOG.md — branch 1.21.1](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/CHANGELOG.md) (seções 7.12.0 a 7.15.0); [Core 7.15.0 NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/all?version=1.21.1); [listagem Easy Model Entities](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all); [EME 2.5.0 NeoForge — Minecraft 26.1.2](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/9152252).

**Autoridade física:** sem dump novo de `mods/`, o JAR e a versão do último snapshot permanecem. Prefixo ✅ e versão histórica no filename são preservados.

## Auditoria declarativa NeoForge 7.15.0 — 13/10/2026

### Manifesto, distribuição e risco de duplicidade

O manifesto `bundle/NeoForge/src/main/resources/META-INF/neoforge.mods.toml` declara `easy_npc` e `easy_npc_config_ui` com `mandatory = true` e `versionRange = "[${file.jarVersion},${file.nextMajorVersion})"` (substituição feita no build). No `bundle/NeoForge/build.gradle`, a linha de desenvolvimento usa **`runtimeOnly`** para ambos os módulos e a publicação CurseForge usa **`mainFile.addRequirement`** para Core e Config UI; a publicação Modrinth declara ambos como **`required.project`**. Esse arquivo build **não contém etapa visível de montagem JarJar**.

**DIVERGÊNCIA DOCUMENTAL EM UPSTREAM:** apesar desse build, o campo `description` do manifesto ainda afirma que o bundle *includes both mods using Jar-in-Jar technology*. O texto descritivo e as regras efetivas do build **não bastam para afirmar a composição do JAR distribuído**. Portanto a documentação antiga de que o bundle é apenas metapacote e nunca contém JARs embarcados fica como hipótese histórica, **pendente de inspeção do ZIP/JAR exato**. Até lá, não remover módulos top-level nem instalar cópias extras de Core/UI.

A dependência `easy_model_entities` fica **`optional` com faixa `[2.5.0,3.0.0)` no Core/UI**, não no manifesto Bundle. O fato de EME 2.4.0 estar no pack cria o risco de incompatibilidade de versão com a família 7.15.0. **Gate físico:** listar `META-INF/jarjar/` e `META-INF/jars/` dos JARs novos e antigos, extrair `neoforge.mods.toml` real, conferir mod IDs/versões e garantir somente uma instância carregada de cada mod ID.

**Código-fonte lido (branch oficial `1.21.1`, 13/10/2026):** [manifesto NeoForge Core](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/core/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [manifesto NeoForge Config UI](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/config-ui/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [manifesto NeoForge Bundle](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/bundle/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [build.gradle do Bundle NeoForge](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/bundle/NeoForge/build.gradle), [gradle.properties do Core](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/core/gradle.properties), [documentação oficial NeoForge 1.21.1 — dependências opcionais](https://docs.neoforged.net/docs/1.21.1/gettingstarted/modfiles/), [CurseForge EME 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-model-entities/files/all?version=1.21.1), [Modrinth EME 2.4.0 NeoForge 1.21.1](https://modrinth.com/mod/easy-model-entities/version/KIk2E6sP). **Limite:** estes arquivos são source da branch 1.21.1; o manifesto do **binário publicado 7.15.0** ainda não foi extraído e comparado byte a byte, nem houve boot do perfil.


## Avaliação de fallback 7.14.0 — Bundle (13/10/2026)

O source [`2c7fb934c6626224220227e6db5150edd891cdd3`](https://github.com/MarkusBordihn/BOs-Easy-NPC/commit/2c7fb934c6626224220227e6db5150edd891cdd3) identifica a família Easy NPC **7.14.0 / Minecraft 1.21.1**, com Core e Config UI permitindo EME opcional `[2.4.0,3.0.0)` ([Core](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/2c7fb934c6626224220227e6db5150edd891cdd3/core/NeoForge/src/main/resources/META-INF/neoforge.mods.toml), [UI](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/2c7fb934c6626224220227e6db5150edd891cdd3/config-ui/NeoForge/src/main/resources/META-INF/neoforge.mods.toml)). O [manifesto Bundle 7.14.0](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/2c7fb934c6626224220227e6db5150edd891cdd3/bundle/NeoForge/src/main/resources/META-INF/neoforge.mods.toml) mantém requisitos para Core + Config UI e descrição de Jar-in-Jar. **A composição efetiva do JAR publicado não foi inspecionada**; não confundir descrição com prova de empacotamento.

**Decisão:** 7.14.0 é **alternativa documental condicional** à 7.15.0 atualmente bloqueada por EME 2.4.0; não está aprovada para instalação. Inspecionar JAR Bundle + Core + Config UI publicados, identificar dependências embarcadas, controlar duplicidade de mod ID, validar faixas e então realizar os gates de multiplayer, NPC persistente, UI e restart. O [source EME 2.5.0 para 1.21.1](https://github.com/MarkusBordihn/BOs-Easy-Model-Entities/blob/7305984c57845b97d0190bfc6d4b3e0363ca5aaf/gradle.properties) **existe**, porém publicação binária NeoForge 1.21.1 não foi comprovada. Preservar trio físico `7.12.1`, sem renomeações nem remoções.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Easy NPC
- **Último candidato 1.21.1 / NeoForge localizado:** **7.16.0 (Release, NeoForge 1.21.1, 17/10)**
- **Estado:** **CANDIDATO COORDENADO**
- **Gate de promoção:** Promover somente junto de Core 7.16.0 + Config UI 7.16.0 + Easy Model Entities compatível. EME 2.6.0 para 1.21.1 agora existe, removendo o bloqueio de ausência de publicação 2.5+, mas não substitui inspeção de manifests/JarJar nem QA de NPC persistence/network/render.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/all


## Revisão incremental por release — 22/10/2026 (Bundle 7.12.1 físico → 7.17.0 candidato)

**Nova publicação oficial:** `easy_npc_bundle-neoforge-1.21.1-7.17.0.jar`, 21/10/2026. O Bundle é um **módulo de distribuição/dependências**, portanto o changelog funcional abaixo pertence ao **Core**; não atribuir IA, inventário, storage ou rendering próprios ao Bundle.

- **7.12.2 (changelog do Core; publicação do Bundle nessa versão não confirmada):** hitboxes de NPCs escalonados, validação de payloads e mudança de protocolo.
- **7.13.0:** macros de diálogo e novo network protocol exigindo versões iguais no cliente/servidor; correções de owner/skins/presets.
- **7.14.0:** late-viewer sync de pose, atributos, trading e som; revalidação da Config UI.
- **7.15.0:** novas AI actions, inventário, shoulder/boss bar e correções de duplicação/persistência do Core; requisito opcional de EME 2.5+ se presente e alterações na retenção de backups.
- **7.16.0:** comando de revive/shoulder, prioridade de objetivos de addons, correções de attack speed/shoulder e rate limit de export; EME opcional `[2.6.0,3.0.0)` comprovada **no source da família 7.16**.
- **7.17.0:** correções de persistência e gravação de arquivos NPC, saves/export no shutdown, movimento entre dimensões, render/sync de shoulder; Light Level padrão 7→0, novo comando `/easy_npc attribute` e diagnóstico de armazenamento. **O novo padrão de iluminação pode mudar NPCs preexistentes.**

**Gate específico do Bundle:** validar se o artefato publicado **7.17.0** contém dependências embutidas em `META-INF/jarjar/` ou `META-INF/jars/`, cotejar mod IDs Core/UI com os JARs top-level, conferir a versão EME realmente resolvida e fazer staging dos três módulos juntos. A divergência histórica entre descrição JarJar e build com dependências externas não está resolvida por consultar a página de download. Não renomear o dossier `✅-... v7.12.1.md` sem recaptura física.

**Status:** `7.12.1` instalada historicamente; `7.17.0` **candidato coordenado, não instalado, QA pendente**. Os changelogs 7.12.2→7.17.0 foram rastreados como **mudanças da família**, sem presumir a publicação individual de todo número como artefato Bundle.

**Fontes:** [CurseForge Bundle, lista de versões 1.21.1](https://www.curseforge.com/minecraft/mc-mods/easy-npc/files/all?version=1.21.1); [Core 7.17.0, changelog comum no arquivo 9282416 (Fabric; não é binário NeoForge)](https://www.curseforge.com/minecraft/mc-mods/easy-npc-core/files/9282416/additional-files); [CHANGELOG oficial 1.21.1](https://github.com/MarkusBordihn/BOs-Easy-NPC/blob/1.21.1/CHANGELOG.md).

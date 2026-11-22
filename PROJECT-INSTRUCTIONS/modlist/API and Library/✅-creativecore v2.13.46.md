# CreativeCore

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#207**: JAR `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar`, mod id `creativecore`, runtime `2.13.46`, SHA-1 `180613074e3f8df9b1e052a3abe19a1c4ac38b48`.

## Propriedades do registro

- **Mod:** CreativeCore
- **Arquivo JAR:** `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar`
- **Versão 1.21.1:** `2.13.46`
- **Categoria:** Biblioteca
- **Função:** Core/API da CreativeMD com infraestrutura compartilhada usada por mods consumidores, incluindo GUI/config, networking e rendering/model utilities.
- **Dependências:** Biblioteca Client & Server. Necessidade determinada pelos consumers CreativeMD instalados; não remover nem atualizar isoladamente sem mapear dependentes e validar ABI/comportamento. Pack físico usa NeoForge 21.1.250.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos de ABI/version drift em GUI/config/network/render APIs e model loading. O runtime atual 2.13.46 incorpora o rework de VecOrigin/partialTick da 2.13.45 e os fixes de item rendering/rotation/offset da 2.13.46; consumers CreativeMD e integrações com rendering físico devem ser regression-tested após a atualização.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/creativecore
- **Procedência:** modlist física atual de 20/09/2026 + runtime `creativecore` 2.13.46 + CurseForge oficial: 2.13.45 Release NeoForge 1.21.1 de 10/09/2026 e 2.13.46 Release NeoForge 1.21.1 de 11/09/2026. A modlist permanece authority da versão instalada.
- **Observações:** JAR físico `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar`, mod id `creativecore`, runtime 2.13.46. A sequência instalada agora incorpora 2.13.45 (ChunkLayerMap index method; VecOrigin/partialTick client) e 2.13.46 (fixes de empty box list em item rendering, animation rotations como radians e item-box offset held/in-ground).
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 2.13.46. CurseForge avançou a linha NeoForge 1.21.1 por 2.13.47 até 2.13.48; o fix de 2.13.47 para origins como Sable é diretamente relevante ao pack.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. CreativeCore 2.13.44 havia sido reconfirmado em 08/09/2026; a modlist física atual de 20/09/2026 confirma a atualização instalada para 2.13.46. A presença continua técnica/dependencial e não foi convertida em decisão curatorial.
- **Sobreposição:** Core específica da CreativeMD; coexistência com outras GUI/config/network/render libraries não implica redundância binária. Consumers compilam contra contratos próprios.
- **Data da última decisão:** 2026-09-20

# Dossiê operacional — padrão Alex's Mobs
> 🧱 Versão física confirmada: `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar`, mod id `creativecore`, runtime `2.13.46`, NeoForge 1.21.1. CreativeCore é uma **core library da CreativeMD**.
## 1. Papel e authority
CreativeCore fornece infraestrutura reutilizada por mods consumidores. O consumer continua authority de sua feature final — blocos, estruturas, GUI, rendering ou gameplay — mesmo quando utiliza componentes da library.
## 2. GUI e configuração
A library oferece infraestrutura comum para GUIs/configs no ecossistema CreativeMD. A tela pode editar/apresentar dados, mas validação, persistência e semântica de cada opção pertencem ao consumer.
Não transformar uma widget/client screen em authority de server state.
## 3. Networking
CreativeCore fornece utilities de comunicação usadas por consumers. O payload e a ação final pertencem ao consumer; packets devem validar side, contexto e permissões antes de alterar gameplay state.
Retry/reconnect não pode provocar double settlement de uma ação por handler duplicado.
## 4. Rendering e models
O changelog 2.13.44 registra **rework de models de block e item**, com impacto explícito em LittleTiles e LittleFrames. Isso torna model loading/baking uma superfície crítica da build.
Resultado visual continua client-side; block/entity gameplay state não deve depender de um model renderizado com sucesso.
## 5. Filters
A mesma release corrige **multiple filters not getting loaded properly**. Filters usados por consumers precisam ser revalidados após config/resource/data lifecycle para não manter seleção incompleta ou stale.
A ficha não inventa tipos/classes de filter sem source pin mais granular.
## 6. Consumer impact
O upstream cita LittleTiles e LittleFrames como afetados pelo rework de modelos. Isso comprova consumer impact, mas não autoriza assumir que todo mod CreativeMD usa a mesma superfície.
Atualizações devem ser testadas no conjunto físico real de consumers do pack.
## 7. Client/server
CreativeCore é Client & Server. Rendering/GUI é client-facing; common configs/networking e state de consumers podem operar em ambos os lados.
Dedicated server não deve carregar renderer ou screen apenas para inicializar utilities comuns.
## 8. Resource/config lifecycle
Validar resource reload, config reload quando suportado, model bake, world join, disconnect/reconnect e restart. Caches de model/filter/UI não devem sobreviver quando a fonte foi alterada.
## 9. Versão atual e deltas 2.13.45–2.13.46
A versão fisicamente instalada é **2.13.46**, correspondente à latest Release NeoForge 1.21.1 publicada em 11/09/2026.
A **2.13.45** adiciona um método de index ao `ChunkLayerMap` e reworka `VecOrigin` para melhor suporte a `partialTick` no client, além de cleanup interno. A **2.13.46** corrige **item rendering com lista de boxes vazia**, interpretação de **rotation values de animation como radians** e **offset de item-box rendering quando o item está segurado/no chão**.
Esses deltas agora fazem parte do runtime instalado. Regression tests devem cobrir principalmente renderização de item, transforms/animations dependentes de `VecOrigin`, câmeras/render físico com partial ticks e consumers que dependam dessas utilities.
Sintomas possíveis de incompatibilidade com consumers continuam incluindo:
- linkage error em consumer;
- GUI/config que deixa de abrir;
- packet/codec incompatível;
- model quebrado/missing;
- filters ausentes;
- client classloading no servidor;
- regressões de item/animation rendering ou transforms client-side.
Diagnóstico precisa registrar versão CreativeCore + consumer + fase do lifecycle e considerar **runtime instalado 2.13.46** como authority física.
## 10. Fail-closed de API
A documentação pública confirma as superfícies gerais da core library e o changelog da 2.13.44, mas não fornece um inventário estático de toda ABI. Para integração própria, pin de source/JAR deve preceder uso de classes/métodos concretos.
## 11. Riscos
1. Remover com consumer ativo.
2. Atualizar library isoladamente e quebrar consumer.
3. Model rework regredir LittleTiles/LittleFrames ou outro consumer.
4. Filters permanecerem ausentes/stale.
5. Packet handler duplicar settlement.
6. UI/config client divergir do state real.
7. Renderer class vazar para dedicated server.
## 12. Matriz de testes
1. Dedicated server boot com consumers atuais.
2. Client join sem linkage/classloading errors.
3. GUIs/configs de consumers selecionados.
4. Network actions exatamente uma vez.
5. Models de block/item dos consumers após resource reload.
6. LittleTiles/LittleFrames, se ativos no perfil, como regression gates herdados do rework 2.13.44.
7. Item rendering com box list vazia e item-box offsets held/in-ground — regressions corrigidas em 2.13.46.
8. Animations/transforms que usam `VecOrigin`, incluindo partialTick client e rotation values — deltas 2.13.45/2.13.46.
9. Filters de consumers carregados após restart/reload.
10. Disconnect/reconnect sem handler/cache duplication.
## 13. Evidência
- modlist física atual de 20/09/2026: `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar` / runtime 2.13.46;
- CurseForge oficial: 2.13.45 Release NeoForge 1.21.1 de 10/09/2026 e 2.13.46 Release NeoForge 1.21.1 de 11/09/2026;
- 2.13.45: `ChunkLayerMap` index method e rework de `VecOrigin` para partialTick client;
- 2.13.46: fixes de empty-box item rendering, animation rotation values e held/in-ground item-box offset;
- changelog 2.13.44 preservado como lineage: rework de block/item models e fix de multiple filters;
- LittleTiles/LittleFrames citados explicitamente pelo upstream como afetados pela linha anterior.
> 🔧 Boundary canônico: CreativeCore fornece **infraestrutura comum**; cada consumer permanece authority de seu conteúdo e gameplay.

## 21. Histórico upstream 2.13.46 → 2.13.48 — não instalado
A authority física continua em **CreativeCore 2.13.46**. Foram revisadas as releases públicas posteriores para NeoForge 1.21.1 até a mais recente localizada no CurseForge em 01/10/2026.

### 2.13.47 — 22/09/2026
- adiciona sistema para garantir que **child origins sejam atualizadas quando a parent origin muda**;
- corrige `center` com origins como **Sable**, que podia gerar **bounding boxes muito grandes**.

Esse segundo fix é material para este modpack: Sable 2.0.5 está fisicamente instalado e CreativeCore já oferece suporte a origins/voxel shapes transformadas. Portanto, a atualização deve ser testada em ships/sublevels, não tratada como simples library bump.

### 2.13.48 — 22/09/2026
- adiciona `onItemToos` ao CreativeLoader, conforme grafia do changelog upstream.

O source da branch 1.21 contém commits posteriores 2.13.49/2.13.50, mas eles **não foram tratados aqui como releases CurseForge 1.21.1** porque a comparação pedida usa a publicação atual do CurseForge como teto de promoção.

Gate de regressão: Sable parent/child origins, bounding boxes em moving structures, LittleTiles/LittleFrames/AmbientSounds consumers, item rendering, rotations/animations, save/reload e client/dedicated-server boot.

Fontes upstream: CurseForge CreativeCore 2.13.47/2.13.48 para NeoForge 1.21.1; `CreativeMD/CreativeCore` branch `1.21`, `changelog.txt`.

> **Atualização/Status — valor histórico preservado do Notion:** REATUALIZADO EM 20/09/2026 — runtime físico CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar / 2.13.46 confirmado. A build instalada é a latest Release NeoForge 1.21.1 de 11/09/2026. O delta 2.13.45 adiciona index method ao ChunkLayerMap e reworka VecOrigin para melhor suporte a partialTick no client; 2.13.46 corrige empty-box item rendering, rotation values de animation tratados como radians e item-box offset held/in-ground.

## Atualização upstream e gate de manutenção — 11/10/2026

**Estado upstream:** **`2.13.51`**, release NeoForge MC 1.21.1 de 09/10/2026 (`CreativeCore_NEOFORGE_v2.13.51_mc1.21.1.jar`, file ID **9108041**); física histórica continua **2.13.46**. A cadeia 2.13.47/48 documentada na seção 21 continua válida; houve também 2.13.49/50 publicados para 1.21.1. **Changelog oficial 2.13.51:** correção de suporte a **rotation collision em Sable origins**. Isso complementa o fix anterior de bounding boxes exagerados em origins parent/child. **Gate de migração:** LittleTiles/LittleFrames e demais consumers, colisões/voxel shapes em Sable ships e sublevels, origins e bounds rotativas, cliente/dedicated server, render, configuração, troca de dimensão e mundo salvo. Fonte não prova que a versão foi instalada. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/creativecore/files/9108041

> **Autoridade e limites:** revisão documental baseada no último inventário físico histórico, sem novo dump de JARs/SHA e sem testes in-game nesta execução. Não alterar versão instalada, filename ou modlist sem evidência física atual.

## Auditoria incremental CreativeCore 2.13.46 → 2.13.54 (17/11/2026)

**Autoridade física preservada:** posição #207, `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar`, mod id `creativecore`, runtime **2.13.46**, SHA-1 `180613074e3f8df9b1e052a3abe19a1c4ac38b48`. Projeto oficial [CreativeCore, CurseForge 257814](https://www.curseforge.com/minecraft/mc-mods/creativecore), categoria **API and Library**; pasta existente correta. Último release verificado para **NeoForge Minecraft 1.21.1: 2.13.54 (10/11/2026)**, **não instalado**. Não misturar a linha 2.14.x para Minecraft 26.x com a do pack.

**Fonte primária comum às etapas:** [changelog versionado do desenvolvedor, branch `1.21`](https://github.com/CreativeMD/CreativeCore/blob/1.21/changelog.txt), [versão do source na mesma branch](https://github.com/CreativeMD/CreativeCore/blob/1.21/gradle.properties) e [releases oficiais filtradas para 1.21.1](https://www.curseforge.com/minecraft/mc-mods/creativecore/files/all?version=1.21.1).

| Etapa após a instalada | Delta nominal do autor, e relevância | Situação |
|---|---|---|
| **2.13.47** | Propaga mudanças de parent origin a child origins; corrige `center` em origins transformadas do **Sable**, evitando bounding boxes enormes. Relevante para ships e colisão. | Changelog/source; já registrada na seção 21 |
| **2.13.48** | Adiciona callback/helper `onItemToos` ao CreativeLoader (grafia original do autor). Potencial nova API para consumers, sem atribuir assinatura sem inspect. | Changelog/source; já registrada na seção 21 |
| **2.13.49** | Corrige **conversão incorreta de rotação vanilla**. Relevante para transform de modelos e orientação. | Changelog/source |
| **2.13.50** | Aperfeiçoa gerenciamento das mudanças em `IVecOrigin`. Relevante para invalidação de estado de origins, mas sem detalhe técnico individual na nota. | Changelog/source |
| **2.13.51** | Corrige suporte a **rotation collision em origins do Sable**, com impacto em colisões de estruturas móveis. | [CurseForge 9108041](https://www.curseforge.com/minecraft/mc-mods/creativecore/files/9108041); já registrada na seção precedente |
| **2.13.52** | Corrige pose de origin que não era atualizada durante colisão. Regredir movimentação, rotação, chunk update e player interaction em Sable. | [CurseForge — releases](https://www.curseforge.com/minecraft/mc-mods/creativecore/files/all?version=1.21.1), changelog oficial |
| **2.13.53** | Novos helpers de `BoxCorner` e `ABB`; corrige **potencial exploit de item** pela seleção/mudança da mão em GUI. Impacto de integridade de inventário e API. | [CurseForge 9474088](https://www.curseforge.com/minecraft/mc-mods/creativecore/files/9474088) |
| **2.13.54** | Corrige **modificação indevida de client config fields por outros jogadores**, **abertura de inventários de bloco a longa distância**, **abertura client-side indevida de GUIs de blocos/itens**; valida que a GUI aberta foi iniciada legitimamente. Melhora menor em **named type registry**. **Correções de validação de autoridade e acesso potencialmente críticas** para qualquer consumer. | [Changelog oficial](https://github.com/CreativeMD/CreativeCore/blob/1.21/changelog.txt), [release NeoForge 1.21.1 de 10/11/2026](https://www.curseforge.com/minecraft/mc-mods/creativecore/files/all?version=1.21.1) |

**Escopo das etapas:** 2.13.47–2.13.54 são entradas individualizadas no source da linha Minecraft 1.21. O catálogo CurseForge também confirma o artefato NeoForge 1.21.1 **2.13.54**; não assumir um JAR NeoForge individual para toda transição interna só pelo número de source, nem inferir que todo consumer executa o código alterado. As três correções de autorização de GUI **2.13.53–2.13.54** requerem atenção prioritária em servidor com usuários não confiáveis. Nenhuma exploração foi reproduzida no modpack; a nota descreve superfícies corrigidas pelo autor, não vulnerabilidade demonstrada localmente.

**Gates de promoção:** consumers CreativeMD (LittleTiles, LittleFrames, AmbientSounds apenas se presentes), GUI/config e networking em cliente/dedicado, permissões e abertura remota de container, hand swap e conservação de itens, persistência de inventories, origins/voxel shapes transformadas e colisões de Sable 2.0.5, resource reload, desempenho e regressões no multiplayer. Backup e validação de manifests/compatibilidade são obrigatórios antes de substituir o JAR. **Nenhum JAR, config, workflow ou save foi alterado nesta auditoria.** 

**Resultado documental:** mudanças upstream relevantes até **2.13.54** incorporadas, baseline físico **2.13.46** preservado; basename `✅-creativecore v2.13.46.md` e pasta **API and Library** inalterados.

## Auditoria incremental upstream — CreativeCore 2.13.55 (22/11/2026; não instalada)

**Baseline:** #207 `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar`, `2.13.46`. O dossier já auditou as etapas de source **2.13.47–2.13.54**, incluindo as mudanças de validação/permissão de GUIs da 2.13.53 e 2.13.54.

A [release oficial NeoForge Minecraft 1.21.1 **2.13.55**, CurseForge 9720383](https://www.curseforge.com/minecraft/mc-mods/creativecore/files/9720383), de **20/11/2026**, sucede a 2.13.54. O [`changelog.txt` da branch oficial `1.21`](https://github.com/CreativeMD/CreativeCore/blob/1.21/changelog.txt) registra especificamente:
- **Adicionado `BoxEdge`** à infraestrutura geométrica.
- **Reorganizados métodos de renderização em `RenderBox`**.

**Impacto:** mudança de APIs de geometria/render que pode alterar o contrato de consumers em compilação/ABI ou visualização; o changelog não declara remoção de métodos nem garante compatibilidade binária. Regressar LittleTiles/LittleFrames e outros consumers CreativeMD comprovadamente presentes, modelos inclinados, camadas de render, origens Sable, caixas voxel, GUI, multiplayer e dedicada; confirmar a metadata e eventuais chamadas afetadas nos JARs. **Sem instalação ou teste, mantendo nome `v2.13.46` e pasta `API and Library`.**

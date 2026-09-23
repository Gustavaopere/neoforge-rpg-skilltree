# CreativeCore

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
- **Atualização/Status:** REATUALIZADO EM 20/09/2026 — runtime físico CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar / 2.13.46 confirmado. A build instalada é a latest Release NeoForge 1.21.1 de 11/09/2026. O delta 2.13.45 adiciona index method ao ChunkLayerMap e reworka VecOrigin para melhor suporte a partialTick no client; 2.13.46 corrige empty-box item rendering, rotation values de animation tratados como radians e item-box offset held/in-ground.
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

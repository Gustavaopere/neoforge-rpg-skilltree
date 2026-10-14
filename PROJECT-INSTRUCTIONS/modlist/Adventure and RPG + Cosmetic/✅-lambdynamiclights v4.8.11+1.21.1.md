# LambDynamicLights

## Propriedades do registro

- **Mod:** LambDynamicLights
- **Arquivo JAR:** lambdynamiclights-4.8.11+1.21.1.jar
- **Versão 1.21.1:** 4.8.11+1.21.1
- **Categoria:** Visual, QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lambdynamiclights/files/all
- **Função:** Engine client-side de iluminação dinâmica renderizada para itens/entidades; altera lightmap/render visual, não o light level server-side do mundo.
- **Dependências:** NeoForge client 1.21.1. Bundle físico embarca runtime/API e Yumi/SpruceUI/Pride Lib. Sodium é integração/recomendação de performance, não hard dependency. Sable Dynamic Lights 2.0.1 depende de LambDynamicLights e o estende para Create/Sable.
- **Compatibilidade/Riscos:** Stack físico: Sodium 0.8.13 + Iris 1.8.14-beta.1 + Sable Dynamic Lights 2.0.1. Sable é bridge dependente do engine Lamb, não uma authority concorrente. Riscos: rebuild cost, stale/duplicate source, shader/render drift, competing dynamic-light engine e EOL da linha 1.21.
- **Sobreposição:** LambDynamicLights é o engine escolhido. Sable Dynamic Lights 2.0.1 o estende para contraptions/sublevels e depende dele; não é substituto. Evitar engines concorrentes como Sodium Dynamic Lights/RyoamicLights, listados upstream como incompatíveis.
- **Observações:** JAR top-level `lambdynamiclights-4.8.11+1.21.1.jar`, mod id `lambdynlights`; runtime NeoForge interno `lambdynlights_runtime` 4.8.11 com mixins próprios. 4.8.11 corrige duplicate keybind que podia corromper `options.txt`.
- **Procedência:** modlist.txt física atual + Modrinth/CurseForge oficiais LambDynamicLights 4.8.11 + source oficial README/HOW_DOES_IT_WORK/CHANGELOG + Sable Dynamic Lights oficial para a relação de bridge.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — LambDynamicLights 4.8.11/JAR físico reconfirmado; 4.8.11 permanece a release 1.21.1 mais recente localizada. Engine visual, EOL, bridges, lifecycle, riscos e testes preservados.
- **Histórico da decisão:** Mantido como parte do stack escolhido de iluminação dinâmica com Sodium e Sable Dynamic Lights. As implementações Sodium/Embeddium Dynamic Lights e bridges Create concorrentes foram removidas em 15/08/2026.
- **Data da última decisão:** 2026-08-26

> **Última autoridade física certificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #369: JAR `lambdynamiclights-4.8.11+1.21.1.jar`, mod id `lambdynlights`, runtime `4.8.11+1.21.1`, SHA-1 `7b5afc75a094b4e8a7118f9856eec959b10f2422`.

> **ESCOPO CANÔNICO.** O snapshot físico certificado de 25/09/2026 registra `lambdynamiclights-4.8.11+1.21.1.jar` (mod ID top-level `lambdynlights`). É um renderer **client-side** de luz dinâmica, não um modificador do nível de luz lógico do servidor. O JAR no disco atual não foi inspecionado nesta auditoria.
## 1. Identidade e packaging físico
A modlist confirma o wrapper top-level e, dentro dele, o runtime NeoForge `lambdynamiclights-4.8.11+1.21.1-neoforge.jar` com mod id `lambdynlights_runtime` e mixins `lambdynlights.mixins.json` + `lambdynlights.lightsource.mixins.json`.
O bundle também contém componentes internos, portanto **não top-level**: LambDynamicLights API 4.8.11, Yumi Minecraft Libraries: Foundation 1.0.0-beta.1, Yumi Commons Core/Collections/Event 1.0.0, SpruceUI 6.2.5 e Pride Lib 1.2.4, com variantes de mapping/packaging do bundle. Não criar páginas independentes apenas por aparecerem sob `META-INF/jars/`.
## 2. Papel no modpack
LambDynamicLights faz entidades e itens luminosos iluminarem visualmente o ambiente enquanto se movem. O upstream documenta, por padrão, itens segurados que emitem luz como bloco, entidades em chamas e fontes naturais como blaze, fireball, glow squid, magma cube, spectral arrow e allay; resource packs podem customizar luminância de itens e ampliar suporte a itens modded.
Algumas fontes podem deixar de emitir ou ter comportamento reduzido na água conforme o tipo/configuração. Isso é comportamento visual da implementação, não uma alteração de block light persistente.
## 3. Authority / ownership
LambDynamicLights é authority do **cálculo/renderização de dynamic light** e da API `lambdynlights_api`. Minecraft/servidor continuam authority de skylight/block light reais, mob spawning, crop growth e qualquer mecânica que consulta iluminação lógica do mundo.
Consequência operacional: uma tocha na mão pode clarear a tela sem tornar o bloco logicamente iluminado para regras server-side. Quests, AI ou mods próprios não devem consultar o efeito visual como source of truth de iluminação.
## 4. Como a luz é calculada
O documento técnico oficial descreve o método por **lightmap coordinates**, não a estratégia de sobrescrever getters de light level do chunk. A implementação injeta no cálculo de coordenadas de lightmap usado pelo renderer, calcula a contribuição de fontes dinâmicas por distância/luminância e mantém a maior contribuição quando ela supera a luz vanilla.
O range técnico documentado é 7,75 blocos para limitar a área de atualização. A contribuição cai com a distância e é convertida para a escala de lightmap preservando precisão. `EntityRenderDispatcher#getLight` recebe tratamento equivalente para evitar entidades escuras dentro de uma área visualmente iluminada.
## 5. Chunk rebuild e performance
Dynamic light exige rebuild das seções/chunks visuais afetados. O próprio upstream classifica isso como a parte crítica de performance. Há modos `FASTEST`, `FAST` e `FANCY` que controlam frequência/qualidade de update.
A implementação acompanha a posição das fontes e marca chunks próximos para rebuild. Quando Sodium está presente, o hook de terrain setup é adaptado ao pipeline Sodium. Atualizações 4.8.x adicionaram culling/adaptive ticking e correções no scheduler para reduzir rebuilds e crashes.
## 6. Sodium, Iris e stack físico
O snapshot físico de 25/09/2026 registra **Sodium 0.8.13** e **Iris 1.8.14-beta.1**; a instalação atual não foi reaberta nesta etapa. A linha LambDynamicLights 4.8.9 atualizou explicitamente a compatibilidade da tela de configuração para Sodium 0.8.x; 4.8.10 adicionou o ícone do mod nessa tela. Sodium é recomendado/compatível como otimização, não deve ser confundido com o projeto separado **Sodium Dynamic Lights**, listado pelo upstream como implementação incompatível concorrente.
Iris/shaders entram na superfície de renderização e devem ser validados visualmente, especialmente com shader packs; a presença de Iris não prova conflito por si.
## 7. Relação concreta com Sable Dynamic Lights
O snapshot físico catalogado contém `sable-dynamic-lights-1.21.1-2.0.1.jar`. O projeto oficial Sable Dynamic Lights declara **Create + LambDynamicLights** como dependências e Sable como opcional; ele é um **bridge/extensão alimentado pelo engine LambDynamicLights**, adicionando iluminação a contraptions/trains e Sable sub-levels.
Logo, no stack atual não existem duas authorities independentes por definição: LambDynamicLights permanece o engine, enquanto Sable Dynamic Lights estende as fontes/superfícies para estruturas móveis. Ainda assim, a composição deve ser testada para evitar fonte duplicada, rebuild redundante ou desync entre world/sublevel/contraption.
## 8. Client / server
A publicação oficial de LambDynamicLights 4.8.11 é **client-side**. O servidor não precisa usar o efeito para decidir iluminação lógica. Em multiplayer, clientes podem ter configuração/modo visual diferentes sem que isso altere o state canônico do servidor.
Sable Dynamic Lights, por outro lado, é publicado como Client & Server/bridge e pode precisar de informação de contraption/sublevel; isso não transforma LambDynamicLights em authority de gameplay server-side.
## 9. Configuração e resource packs
A tela de configuração permite habilitar/desabilitar categorias/fonte e ajustar qualidade/performance. A luminância de itens pode ser customizada por resource pack usando o sistema de dynamic light sources do mod. Mudanças de resource/config devem reconstruir somente o state visual necessário e não criar state persistente de mundo.
## 10. Lifecycle
Pontos críticos:
- client startup e aplicação dos mixins/render hooks;
- world join/disconnect e troca de dimensão limpando fontes antigas;
- entity spawn/despawn/death removendo fontes;
- item equip/unequip/drop atualizando luminância uma vez;
- chunk unload/reload invalidando rebuild tracking;
- resource reload reconstruindo definições de luminância;
- fechamento do cliente persistindo config/keybinds sem corromper `options.txt`.
A versão 4.8.11 corrige especificamente um caso de **duplicate keybind** que podia corromper `options.txt`.
## 11. Linha 1.21 em EOL
O changelog 4.8.9, 4.8.10 e 4.8.11 marca a linha Minecraft 1.21 como **End-of-Life**. A 4.8.11, publicada em 31/08/2026, permanece a release mais recente **para Minecraft 1.21.1** localizada na revisão externa de 14/10/2026 e contém o fix de keybind duplicado + atualização de Yumi Foundation.
Isso não torna a versão insegura ou quebrada, mas aumenta risco de manutenção futura: regressões específicas do pack podem não receber backport nessa linha.
## 12. Riscos técnicos
1. **False authority:** tratar iluminação visual como block-light server-side.
2. **Chunk rebuild cost:** muitas fontes móveis causarem CPU/GPU rebuild excessivo.
3. **Duplicate source/rebuild:** bridge de contraptions registrar a mesma fonte mais de uma vez.
4. **Stale source:** entidade/sublevel descarregado continuar iluminando visualmente.
5. **Shader/render incompatibility:** Iris/shader pack alterar lightmap ou pipeline esperado.
6. **Competing implementation:** instalar Sodium Dynamic Lights/RyoamicLights ou outro engine concorrente junto ao LambDynamicLights.
7. **Mixin/render drift:** updates de Sodium/Iris/Minecraft mudarem hooks de renderer.
8. **EOL:** ausência de manutenção futura específica para MC 1.21.
## 13. Boundary para projetos próprios
Não usar dynamic light renderizado para liberar perk, impedir spawn, determinar stealth, calcular temperatura, validar ritual ou qualquer mecânica autoritativa. Se um projeto próprio precisar de “luz real”, deve consultar o light engine/state server-side apropriado. Para integração visual, preferir a API do LambDynamicLights/bridge correspondente em vez de criar um segundo dynamic-light engine.
## 14. Matriz de testes
- [ ] Client cold boot com LambDynamicLights 4.8.11 + Sodium 0.8.13 + Iris 1.8.14-beta.1.
- [ ] Dedicated server aceita clientes com/sem o efeito sem depender do light visual.
- [ ] Tocha/item luminoso na mão ilumina visualmente, mas não altera regra server-side de block light.
- [ ] Equip/unequip/drop atualiza a fonte exatamente uma vez.
- [ ] Entidade em chamas/spawn/despawn não deixa luz fantasma.
- [ ] Troca de dimensão e relog limpam fontes/rebuild tracking.
- [ ] Sable Dynamic Lights 2.0.1 ilumina Create contraptions/sublevels sem duplicação ou flicker.
- [ ] World ↔ sublevel ↔ contraption se iluminam conforme o bridge sem rebuild runaway.
- [ ] Shader pack ativo via Iris não quebra lightmap, transparência ou emissives.
- [ ] `FASTEST`, `FAST` e `FANCY` apresentam comportamento/performance coerente.
- [ ] Resource reload reaplica definições de luminância sem stale cache.
- [ ] Fechar/reabrir o jogo preserva keybind/config sem corromper `options.txt`.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 15. Evidências e limites
- modlist física: wrapper 4.8.11, runtime NeoForge, API e bibliotecas embarcadas; Sodium 0.8.13, Iris 1.8.14-beta.1 e Sable Dynamic Lights 2.0.1 presentes;
- Modrinth/CurseForge oficiais: 4.8.11 para 1.21/1.21.1, ambiente client-side e changelog/EOL;
- source oficial: README e `HOW_DOES_IT_WORK.md` para lightmap, entity lighting, chunk rebuild e integração Sodium;
- Sable Dynamic Lights oficial: bridge Create/Sable dependente de LambDynamicLights.
Nenhum FPS ganho/perda foi inventado. O efeito com shaders específicos e a ausência de double-source no stack atual ainda dependem de teste runtime.

## 16. Revalidação da release 4.8.11, API v4 e stacks de luz — 14/10/2026

### 16.1. Autoridade da versão instalada e publicação oficial

- **Snapshot histórico #369 de 25/09/2026:** `lambdynamiclights-4.8.11+1.21.1.jar`; mod ID top-level `lambdynlights`; versão `4.8.11+1.21.1`; SHA-1 `7b5afc75a094b4e8a7118f9856eec959b10f2422`. O JAR atual em disco e os JARs embarcados **não foram reabertos** neste ciclo. A última autoridade de packaging continua sendo a metadata documentada no snapshot anterior.
- **Distribuição oficial CurseForge:** [File ID `8780227`](https://www.curseforge.com/minecraft/mc-mods/lambdynamiclights/files/8780227), **31/08/2026**, release para Minecraft **1.21 e 1.21.1**, **Fabric/Quilt/NeoForge**; campo **File name** = `lambdynamiclights-4.8.11+1.21.1.jar`, igual ao snapshot. O [filtro oficial de arquivos Minecraft 1.21.1](https://www.curseforge.com/minecraft/mc-mods/lambdynamiclights/files/all?page=1&pageSize=20&version=1.21.1) não apresenta release posterior à 4.8.11.
- **Distribuição oficial Modrinth:** [versão `4.8.11+1.21.1`](https://modrinth.com/mod/lambdynamiclights/version/ksaGCvSu) publicada com **ambiente Client-side**. O [changelog do autor](https://modrinth.com/mod/lambdynamiclights/changelog) registra para 4.8.11 **correção de keybind duplicado que corrompia `options.txt` (#311, #386)** e marca a linha Minecraft 1.21 em **End-of-Life**. Não confundir versões 4.12.x/4.13.x para Minecraft 26.x com atualizações compatíveis com 1.21.1.
- **Source oficial da versão pinado:** [tag `v4.8.11+1.21.1` / commit `c2e10b81c3233dc30c01cb1a62154529b5dbca2d`](https://github.com/LambdAurora/LambDynamicLights/tree/c2e10b81c3233dc30c01cb1a62154529b5dbca2d). [`gradle.properties`](https://github.com/LambdAurora/LambDynamicLights/blob/c2e10b81c3233dc30c01cb1a62154529b5dbca2d/gradle.properties) declara `base_version=4.8.11`, `java_version=21`, `neoforge_mc_constraints=[1.21,1.21.1]` e IDs CurseForge/Modrinth. Fonte e release são consistentes nominalmente; **o hash do download oficial não foi comparado ao JAR histórico**.
- **Licença:** o [README no commit da tag](https://github.com/LambdAurora/LambDynamicLights/blob/c2e10b81c3233dc30c01cb1a62154529b5dbca2d/README.md) e a publicação oficial identificam **Lambda License**, adotada na linha 3.0.0. Não presumir permissão de redistribuição ou de empacotamento de derivados sem consultar o texto da licença e o acordo da distribuição.
- **Decisão:** **Manter** a release 4.8.11 catalogada, compatível nominalmente com Minecraft 1.21.1 NeoForge; sem indicação de versão superior 1.21.1 e sem teste de necessidade de troca.

### 16.2. Data-driven API — namespaces, paths, precedência e consequências

A [documentação oficial da API v4](https://lambdaurora.dev/projects/lambdynamiclights/docs/v4/) estabelece dois caminhos de resource pack, **client-side**:

- `assets/<namespace>/dynamiclights/item/<file>.json`: [especificação de item lighting](https://lambdaurora.dev/projects/lambdynamiclights/docs/v4/item.html). Itens luminosos por estado de bloco podem herdar luminância; JSON de item permite especificar condições e intensidade, inclusive para itens modded. A documentação da API usa `lambdynlights` para mod completo e `lambdynlights_api` para a API.
- `assets/<namespace>/dynamiclights/entity/<file>.json`: [especificação de entity lighting](https://lambdaurora.dev/projects/lambdynamiclights/docs/v4/entity.html). Permite `match` de tipo de entidade/ID/tag, localização, effects, flags, equipamento e relações com veículos/passageiros; luminância pode ser fixa ou derivada de provider (`lambdynlights:value`, `lambdynlights:item`, `lambdynlights:water_sensitive`, `lambdynlights:wet_sensitive`, entre outros definidos pela API).
- **Semântica version-specific da release 4.8.11:** no [source oficial `ItemLightSources.getLuminance`](https://github.com/LambdAurora/LambDynamicLights/blob/c2e10b81c3233dc30c01cb1a62154529b5dbca2d/src/main/java/dev/lambdaurora/lambdynlights/resource/item/ItemLightSources.java), o manager percorre cada definição cujo predicado reconhece o item e usa `Math.max` sobre sua luminância (respeitando `waterSensitive`); o [manager de entidades `EntityLightSources.getLuminance`](https://github.com/LambdAurora/LambDynamicLights/blob/c2e10b81c3233dc30c01cb1a62154529b5dbca2d/src/main/java/dev/lambdaurora/lambdynlights/resource/entity/EntityLightSources.java) também usa `Math.max` sobre fontes de entidade correspondentes, com saída antecipada ao atingir 15. Dentro de [listas de providers de uma mesma entidade](https://github.com/LambdAurora/LambDynamicLights/blob/c2e10b81c3233dc30c01cb1a62154529b5dbca2d/api/src/main/java/dev/lambdaurora/lambdynlights/api/entity/luminance/EntityLuminance.java), `EntityLuminance.getLuminance` seleciona igualmente o máximo. **Isto não é soma de intensidades.** Os codecs [`ItemLightSource`](https://github.com/LambdAurora/LambDynamicLights/blob/c2e10b81c3233dc30c01cb1a62154529b5dbca2d/api/src/main/java/dev/lambdaurora/lambdynlights/api/item/ItemLightSource.java) e [`EntityLightSource`](https://github.com/LambdAurora/LambDynamicLights/blob/c2e10b81c3233dc30c01cb1a62154529b5dbca2d/api/src/main/java/dev/lambdaurora/lambdynlights/api/entity/EntityLightSource.java) **não declaram campo `priority` de definição nessa tag**. **Separadamente**, um resource pack de maior prioridade pode substituir um JSON de mesmo identificador/path na resolução vanilla de recursos. Não importar para 4.8.11 contracts de releases posteriores (por exemplo `priority`, `lambdynlights:max` ou `lambdynlights:conditional`) sem confirmar sua existência no bytecode da linha 4.8.11. O intervalo de luminância é **0–15**.
- **Provider Java:** a [documentação de interfaces](https://lambdaurora.dev/projects/lambdynamiclights/docs/v4/java.html) permite novos providers de luminância e registro direto via API; a API usa entrypoints, inclusive na plataforma NeoForge. Para mods próprios e bridges, essa superfície é mais segura do que criar uma segunda engine de luz ou modificar o light engine lógico de Minecraft.

**Fronteira para catálogo de resource packs:** arquivos JSON de dynamic lighting **só devem ser contabilizados como presentes no modpack quando o resource pack real contiver esses paths**. O fato de o mod aceitar `assets/*/dynamiclights` não justifica criar novas entradas no inventário de resource packs sem artefato verificável.

### 16.3. Pipeline de renderização, custo e coexistência do pack

No [`HOW_DOES_IT_WORK.md` da tag](https://github.com/LambdAurora/LambDynamicLights/blob/c2e10b81c3233dc30c01cb1a62154529b5dbca2d/HOW_DOES_IT_WORK.md), o provider explica a arquitetura **lightmap coordinates**: usa o maior valor entre contribuição dinâmica e luz vanilla ao calcular a iluminação de renderização, em vez de escrever blocos de luz reais no chunk. O raio citado para cálculo é **7,75 blocos**; contribuição diminui com a distância. A atualização de seções visuais implica **chunk/section rebuild**, principal custo informado pelo upstream. Os modos `FASTEST`, `FAST` e `FANCY` controlam frequência de atualização. Hook de renderer tem rota própria quando Sodium está presente. Isso não constitui mudança de block light server-side.

**Relações do snapshot do pack:**

| Item | Snapshot / papel |
| --- | --- |
| **Sodium #512** | `sodium-neoforge-0.8.13+mc1.21.1.jar`. Render optimization, não segunda dynamic-light engine. A release Lamb 4.8.9 registrou suporte de configuração a Sodium 0.8.x. |
| **Iris #338** | `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar`. Shader pipeline client-side; shader packs e opções não foram examinados nesta revisão. |
| **Create #116** | `create-1.21.1-6.0.10.jar`. Provider de contraptions/trens; não substitui o engine visual de Lamb. |
| **Sable Dynamic Lights #485** | `sable-dynamic-lights-1.21.1-2.0.1.jar`. A [descrição oficial da bridge](https://modrinth.com/mod/sable-dynamic-lights) confirma **Create + LambDynamicLights** obrigatórios, **Sable opcional**, suporte a contraptions, trens e sublevels; desde 2.0.0 declara iluminação cruzada world/sublevel/contraption. A bridge usa a engine Lamb para luz visual, mas seu ambiente é **Client & Server**, o que não equivale a exigir Lamb no servidor. |

**Atenção:** a [lista de incompatibilidades Modrinth](https://modrinth.com/mod/lambdynamiclights) relaciona implementações de luz dinâmica concorrentes. Não instalar `Sodium Dynamic Lights`/`RyoamicLights` só porque o **Sodium renderer** consta no pack: são componentes distintos. Qualquer composição com alternativas como Contraption Lights deve ser avaliada separadamente para evitar registros duplicados, rebuilds em excesso e luz fantasma.

### 16.4. QA ainda não executado

- [ ] Reabrir o JAR físico atual e confirmar SHA-1, `lambdynlights`, dependências e bundles internos (`META-INF/jars`), diferenciando libs embarcadas de JARs top-level.
- [ ] Confirmar o `File ID 8780227`/Modrinth `ksaGCvSu` por hash comparável ao snapshot antes de declarar equivalência byte-a-byte com o JAR histórico.
- [ ] Boot de client com Java 21 / NeoForge, Sodium 0.8.13, Iris 1.8.14-beta.1 e Sable Dynamic Lights 2.0.1; verificar shaders usados no perfil real.
- [ ] Testar item segurado, drop, entidade em chamas, dimensão, spawn/despawn, disconnect/reconnect e resource-pack reload; nenhuma fonte luminosa deve persistir após remoção.
- [ ] Em **perfil de teste**, validar duas definições de arquivos diferentes que satisfaçam o mesmo `match` com luminâncias distintas: os managers do source **4.8.11** agregam pelo máximo após os filtros de água. Testar **separadamente** dois resource packs sobrescrevendo **o mesmo resource path**, quando a prioridade vanilla de pack determina qual JSON é carregado. Não esperar um campo de `priority` inexistente nessa tag.
- [ ] Perfil de performance: contabilizar frame time e contagem de rebuilds com centenas de fontes e com contraptions móveis; comparar modos `FASTEST`/`FAST`/`FANCY`, com e sem shader Iris.
- [ ] Sable bridge: luz de mundo/sublevel/contraption em todas as direções, sem duplicação de fontes ou runaway rebuild, verificando atualizações ao desmontar, descarregar e mudar dimensão.
- [ ] Verificar fechamento/reabertura do cliente e integridade de `options.txt`: correção do upstream existe, mas não houve teste local do fix nem de eventuais conflitos de keybind.
- [ ] Confirmar que farms, spawn de mobs, lógica de temperatura, stealth e perks não consomem luminância visual como fonte de verdade do servidor.

**Limites:** esta é uma atualização do **dossiê do mod #369**, não uma instalação, edição de resource pack ou teste gráfico. As versões físicas citadas são de snapshot histórico, sem novo dump de outubro. Sem modificação de `modlist.md`, saves, scripts, JARs ou mecânicas. Nenhum desempenho em FPS ou compatibilidade in-game foi alegado sem medição.

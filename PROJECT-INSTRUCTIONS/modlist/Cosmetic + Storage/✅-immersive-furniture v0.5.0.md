# Immersive Furniture — 0.5.0

## Propriedades vigentes — 27/11/2026

- **Mod:** Immersive Furniture
- **Arquivo JAR instalado (declarado):** `immersive_furniture-neoforge-0.5.0-1.21.1.jar`
- **Versão instalada declarada:** `0.5.0` — Release, Minecraft 1.21.1 / NeoForge.
- **Ordem física histórica:** #328 (preservada).
- **Categoria CurseForge / pasta:** Cosmetic + Storage.
- **Função:** Furniture customizada com editor, biblioteca remota, texturas, sons, partículas, sitting/sleeping e estados configuráveis.
- **Atualização/Status:** `0.3.3+1.21.1` → `0.5.0`.
- **Estado no pack:** instalação declarada pelo usuário; JAR local/metadata não relidos.
- **Estado da pesquisa:** auditoria incremental das publicações identificadas; limites de changelog, metadata e procedência indicados abaixo; sem homologação de runtime.
- **Dependências / compatibilidade / riscos:** NeoForge 1.21.1, Client & Server; versão interna de sable-companion não verificada na nova build. Preservar JarJar apenas como dependência embutida. Testar mallet, solidez/collision/pathfinding e mobiliário em Sable, culling, assets lazy, editor/library, Dynamic Trees, persistência de texturas e saves. Não afirmar compatibilidade testada.
- **Fonte:** [publicação ou catálogo de referência](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture/files/9777712).
- **Procedência:** solicitação expressa de atualização, fontes oficiais e, onde explicitado, corroboradores secundários; não atribuir hash/metadata da versão nova sem leitura do binário.

## Auditoria incremental das publicações

- 0.3.4 — 23/10, [arquivo 9299760](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture/files/9299760): recuperação de cache, retry de downloads, concorrência, crash Dynamic Trees, camas em sublevels Sable, opções e limites de atlas, preservação de baked textures em autosave.
- 0.4.0 — 05/11, [arquivo 9456983](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture/files/9456983): busca de furniture similar, ordenação hot/popular, nome e tags para publicação, likes/data, colar links; grade e preview maior, flip XYZ; correção de flicker, botões, crashes com blocos modded e possível freeze perto de chunks descarregados.
- 0.4.1 — 05/11, [arquivo 9457770](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture/files/9457770): correções de crash genéricas, sem causa discriminada.
- 0.5.0 — 23/11, Release: mallet desloca móveis rapidamente um pixel, corrige culling, autor menciona possível correção de crash sem causa, carregamento preguiçoso de assets para reduzir tempo de boot, móveis passam a ser considerados sólidos. Não inferir geometria de colisão específica, receitas do mallet ou ganho quantificado. Publicação chama versão 0.5.0 e filename usa -1.21.1 em vez de +1.21.1 da baseline; metadata interna nova não lida.

## Histórico documental preservado

> Os dados abaixo descrevem o snapshot ou a auditoria na sua data original. Menções a versão física, atual, candidata, bloqueada, não instalada, hashes e requisitos anteriores são históricas; as propriedades vigentes acima prevalecem. A declaração atual não constitui teste de execução.

## Propriedades do registro

- **Mod:** Immersive Furniture
- **Arquivo JAR:** `immersive_furniture-neoforge-0.3.3+1.21.1.jar`
- **Versão 1.21.1:** `0.3.3+1.21.1`
- **Categoria:** QoL, Visual
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/immersive-furniture
- **Função:** Sistema de furniture customizada com editor in-game e biblioteca online; suporta partículas, sons, sitting/sleeping, material-based texturing, estados alternáveis e lighting.
- **Dependências:** NeoForge 1.21.1. Client & Server. O JAR físico embarca `sable-companion-common-1.21.1-1.5.0.jar` (`sablecompanion` 1.5.0) em `META-INF/jarjar`; é dependência interna do host. Sable é integração relevante no pack e teve crash fix na linha 0.3.2; não foi tratado como hard dependency universal além do componente embarcado.
- **Compatibilidade/Riscos:** Riscos: furniture entity/state desync, pose/collision, conteúdo remoto não reprodutível, custo em builds densas e transforms em Sable. 0.3.2 corrige crashes Sable; 0.3.3 corrige particle emitter rotation.
- **Sobreposição:** Sobreposição temática com furniture/decor mods, mas editor/importação de objetos customizados e library remota são superfícies próprias.
- **Observações:** JAR físico `immersive_furniture-neoforge-0.3.3+1.21.1.jar`, mod id `immersive_furniture`, runtime 0.3.3+1.21.1. Nested `sablecompanion` 1.5.0 é JarJar interno e não recebe entrada top-level. Release 0.3.3 permanece a build NeoForge 1.21.1 mais recente localizada.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial Immersive Furniture 0.3.3 NeoForge 1.21.1 + changelog 0.3.1–0.3.3 + inventário físico do JAR confirmando `sable-companion-common-1.21.1-1.5.0.jar` embarcado em `META-INF/jarjar`.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Immersive Furniture 0.3.3+1.21.1/JAR físico reconfirmado como release NeoForge 1.21.1 mais recente localizada. Correção material: `Sable Companion 1.5.0` está embarcado via JarJar no host e foi documentado como dependência interna, não top-level.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #328: JAR `immersive_furniture-neoforge-0.3.3+1.21.1.jar`, mod id `immersive_furniture`, runtime `0.3.3+1.21.1`, SHA-1 `cd595ea729f0b55a09568c2ebcdb1ad2d6c16c53`. `sable-companion-common-1.21.1-1.5.0.jar` permanece JarJar interno do host e não ocupa posição top-level.

<callout icon="🪑" color="green_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `immersive_furniture-neoforge-0.3.3+1.21.1.jar`, mod id `immersive_furniture`, versão `0.3.3+1.21.1`. É um sistema Client & Server para criar, importar e usar furniture customizada, não apenas um pack estático de modelos.
</callout>
## 1. Editor e biblioteca
O projeto fornece editor in-game e biblioteca online comunitária. A documentação pública registra mais de 3500 peças disponíveis, mas esse número é catálogo remoto e muda sem troca do JAR; não deve ser tratado como registry count fixo da build.
O Artisan's Workstation é o ponto de entrada documentado para criação/uso do sistema.
## 2. Superfícies funcionais
Furniture pode usar partículas, sons, sitting, sleeping, texturização automática baseada em material, lighting e estados alternáveis como open/closed. Estados podem responder a right-click e, na linha histórica documentada, a redstone.
## 3. State e authority
O servidor deve ser authority de placement, interação funcional, sitting/sleeping e qualquer storage/state persistente. Editor/model preview e biblioteca remota são apresentação/authoring; não devem conceder gameplay state sem validação.
## 4. Furniture entities e performance
A linha 0.3.x recebeu melhorias de performance para furniture items, editor e furniture entities. Em builds com muitos objetos customizados, testar custo de render/tick, colisão e chunk load em áreas densas.
## 5. Sable
A 0.3.2 corrigiu crashes ligados ao suporte Sable; a 0.3.3 corrige particle emitter ignorando rotação do bloco. Como o pack contém Sable, ambos são regression gates reais: móveis em sublevels/ships precisam preservar transformação, interação e partículas.
O JAR físico `immersive_furniture-neoforge-0.3.3+1.21.1.jar` embarca `META-INF/jarjar/sable-companion-common-1.21.1-1.5.0.jar`, mod `sablecompanion` 1.5.0. Pelo protocolo do catálogo, essa biblioteca/bridge é **dependência JarJar interna do host**, não um mod top-level separado. Atualizar Immersive Furniture pode alterar esse runtime interno mesmo sem surgir novo JAR na pasta `mods`.
## 6. Conteúdo remoto
Baixar furniture da biblioteca introduz conteúdo que não está representado apenas pelo JAR. Para reprodução do modpack, objetos necessários a quests/builds devem ser versionados/exportados de forma controlada; não depender de um item remoto que possa mudar ou desaparecer.
## 7. Riscos
1. Modelo/state custom não sincroniza entre clientes.
2. Furniture entity duplica/desaparece em chunk unload.
3. Sitting/sleeping conflita com pose/collision mods.
4. Sable transform quebra posição/rotação/interação.
5. Biblioteca remota muda conteúdo sem alteração de versão.
6. Partículas/som/lighting causam custo excessivo em builds densas.
7. Tratar `Sable Companion 1.5.0` embarcado como mod top-level ou ignorar seu version drift ao atualizar o host.
## 8. Boundary para quests/perks
Colocar ou usar um móvel só deve contar quando o server state correspondente for confirmado. Preview/editor/library download não são milestones de gameplay.
## 9. Matriz de testes
- [ ] Dedicated server inicia com 0.3.3.
- [ ] Furniture criada/importada persiste após restart.
- [ ] Toggle open/closed e redstone convergem em multiplayer.
- [ ] Sitting/sleeping não deixa pose presa.
- [ ] Particle emitter respeita rotação.
- [ ] Furniture em sublevel Sable mantém transform após unload/reload.
- [ ] Área com muitos móveis mantém custo aceitável.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 10. Evidências e limite
CurseForge/Modrinth oficiais confirmam Release 0.3.3, editor, online library, partículas/sons/sitting/sleeping, material texturing, toggle states/lighting e changelog 0.3.1–0.3.3. A modlist/JAR inventory física confirma ainda `sable-companion-common-1.21.1-1.5.0.jar` embarcado em `META-INF/jarjar`. Schemas internos de furniture e storage não foram pinados ao binário nesta etapa.

## Auditoria incremental — 18/11/2026 — 0.3.4 a 0.4.1

Baseline físico histórico #328: `immersive_furniture-neoforge-0.3.3+1.21.1.jar`. O [catálogo oficial 1.21.1](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture/files/all?page=1&pageSize=20&version=1.21.1) identifica exatamente três releases NeoForge após essa build. Categorias oficiais verificadas: Cosmetic + Storage; caminho preservado.

- **[0.3.4 — 23/10/2026](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture/files/9299760/additional-files):** correções de cache corrompido, concorrência de acesso aleatório, repetição de downloads falhos, crash com Dynamic Trees e camas de móveis em sublevels Sable; permite configurar dimensões dos atlas de entidades e temporários, respeita limites da GPU/configuração e preserva texturas baked em autosaves. O changelog desta versão está também reproduzido sob seu cabeçalho na publicação 0.4.1.
- **[0.4.0 — 05/11/2026](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture/files/9456983):** biblioteca com busca por móveis semelhantes, ordenação por tendência/popularidade, nome e tags customizadas obrigatórios para publicação de conteúdo, exibição de curtidas/data e abertura de links colados diretamente na biblioteca. Editor/preview recebe grade de referência, maior área de visualização e inversão XYZ. Correções de flicker, botões sobrepostos em janela pequena, crash com blocos modded e possível congelamento do servidor junto a chunks descarregados.
- **[0.4.1 — 05/11/2026](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture/files/9457770):** somente correções de crash descritas genericamente pelo autor, sem nova mecânica ou configuração identificada; não inferir causa. A publicação traz também o histórico das duas versões acima.

**QA de promoção:** criação/importação e publicação de móveis, tags e navegação da biblioteca, rotação/inversão de modelos, limites de atlas e recuperação de cache, Dynamic Trees, camas em sublevels Sable, texturas persistentes em autosave/restart, fronteira de chunks e servidor dedicado. Conferir versão de componentes jarjar da nova build sem criar entradas top-level. Nenhum JAR ou configuração alterado, nem teste de runtime realizado. Auditoria documental da sequência publicada concluída.

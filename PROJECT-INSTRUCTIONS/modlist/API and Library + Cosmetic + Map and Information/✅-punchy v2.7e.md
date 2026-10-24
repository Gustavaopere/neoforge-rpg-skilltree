# Punchy

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#465**: JAR `punchy-2.7e-neoforge-1.21.1.jar`, mod id `punchy`, runtime `2.7e`, SHA-1 `5949f795c64d460c6c1209a3e9b78b808d258fb3`. `Punchy Epic Fight Compat 1.0.0` permanece presente.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Punchy
- **Arquivo JAR:** `punchy-2.7e-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 2.7e
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL, RPG
- **Função:** Melhora animações e sensação em primeira pessoa, com movimento/física da mão e feedback visual para ações e combate.
- **Dependências:** NeoForge 1.21.1. Punchy Epic Fight Compat 1.0.0 está presente para composição com Epic Fight; não é dependência-base do Punchy.
- **Sobreposição:** Foco em primeira pessoa; não equivale a NotEnoughAnimations/third-person nem ao sistema de combate Epic Fight.
- **Compatibilidade/Riscos:** Riscos: render competition com First Person Model/Epic Fight, resource-reload state, animation audio stale, itemgrip alignment, profile/Mixpack drift e performance após reload. Bridge dedicada desativa Punchy em battle mode.
- **Observações:** Release 2.7e NeoForge 1.21.1 de 03/09/2026. Delta: refresh pós-resource-manager, cleanup de animation sounds e fix de rotação/alinhamento multi-eixo de itemgrip.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Punchy 2.7e + bridge Punchy Epic Fight Compat física/oficial.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/punchy
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Punchy 2.7e reconstruído: first-person hands/items, Hand Editor/Mixpacks, resource reload, audio cleanup, itemgrip rotation, Epic Fight bridge, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `punchy-2.7e-neoforge-1.21.1.jar`, mod id `punchy`, versão `2.7e`, NeoForge 1.21.1. Punchy altera a apresentação/sensação de ações em primeira pessoa com animações e física visual das mãos/itens. O pack possui bridge dedicado para Epic Fight; essa bridge evita concorrência em battle mode, mas não transforma Punchy em sistema de combate.

## 1. Identidade e papel
- **Mod:** Punchy.
- **JAR:** `punchy-2.7e-neoforge-1.21.1.jar`.
- **Mod id:** `punchy`.
- **Runtime:** `2.7e`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server conforme publicação.
- **Licença:** All Rights Reserved.
- **Papel:** first-person animation/feel, in-hand physics e tuning visual de ações.
- **Decisão:** Sem decisão.

## 2. Presentation layer
Punchy deve ser tratado primariamente como camada de apresentação. O gameplay de atacar, minerar, usar item ou mover-se continua pertencendo ao sistema que dispara a ação.

Uma animação atrasada/ausente não prova que o evento funcional não ocorreu; inversamente, uma animação local não deve criar ação server-side.

## 3. Mãos, itens e física visual
O projeto ajusta movimento das mãos e itens segurados para reduzir rigidez e aumentar feedback. Isso toca transformações de primeira pessoa, poses, equip/unequip e uso de items.

Testar main hand/offhand, left-handed mode, itens grandes/custom renderers e transições rápidas de slot.

## 4. Hand Editor e poses
A linha do projeto inclui ferramentas/tuning como Hand Editor/Hand Pose para ajustar apresentação. Esses valores são conteúdo/configuração do usuário/pack, não mecânica universal.

Persistência dessas preferências deve sobreviver a restart e resource reload sem acumular transforms.

## 5. Mixpacks e profiles
Punchy suporta profiles/Mixpacks que compõem animações/ajustes. Como múltiplos recursos podem agir sobre a mesma mão/item, a ordem e o profile ativo precisam ser auditados quando houver desalinhamento.

Não atribuir a Punchy conteúdo de um Mixpack específico sem identificá-lo fisicamente.

## 6. Release 2.7e
A build instalada 2.7e é Release NeoForge 1.21.1 de 03/09/2026.

O changelog registra:
- refresh final depois que o resource manager termina, corrigindo tuning salvo do Hand Editor, profiles/Mixpacks e animações que assentavam incorretamente após resource-pack reload;
- correção de sons de animação continuando após animação mudar/parar/ser desabilitada/reload;
- correção de ordem de rotação multi-eixo de `itemgrip`/Blockbench e alinhamento de held items.

## 7. Resource reload
A 2.7e ataca diretamente bugs de reload. Regression gate obrigatório:
- habilitar/desabilitar resource pack;
- recarregar assets;
- verificar pose salva;
- verificar profile/Mixpack;
- confirmar que sons antigos param;
- confirmar que item grip não deriva para posição errada.

O autor menciona problemas de performance ligados a reload que nem sempre foram reproduzidos; não declarar universalmente resolvido sem smoke local.

## 8. Áudio ligado à animação
Sons de animação precisam iniciar/parar junto da animação correspondente. Em stack com Presence Footsteps/AmbientSounds, separar:
- Punchy → som ligado à animação específica;
- Presence Footsteps → passos/surface context;
- ambience → soundscape.

Double sound deve ser isolado pelo evento antes de remover mod.

## 9. Epic Fight battle mode
Epic Fight possui animation/combat authority própria. O pack inclui `punchy_epicfight_neoforge.jar`, bridge criada para **desabilitar o rendering do Punchy durante battle mode**.

Isso reduz competição visual. Testar entrada/saída de battle mode para garantir reativação correta do Punchy no modo normal.

## 10. First Person Model e outros renderers
O pack contém outras camadas de first-person/player rendering. Superfícies críticas:
- corpo/mão duplicados;
- held item desalinhado;
- clipping com armor/weapon;
- camera transform acumulado;
- shaders e particles no espaço errado.

Conflito deve ser reproduzido por ação e mod stack concretos.

## 11. Lineage relevante
Releases anteriores trataram compatibilidade/performance com 3D Skin Layers, shaders, Fresh Moves, particles/camera e Better Combat. Esses itens são regression lineage, não claims de delta exclusivo da 2.7e.

## 12. Client/server e multiplayer
Mesmo com forte foco visual, ações do jogo continuam server-authoritative. Em multiplayer, Punchy pode exibir feedback diferente por config local sem alterar dano/cooldown real.

Dedicated server precisa iniciar com a composição prevista pela publicação sem client classloading indevido.

## 13. Riscos
1. **Render competition:** First Person Model/Epic Fight/other layers.
2. **Resource reload state:** área corrigida em 2.7e.
3. **Audio stale:** som continua após animation stop.
4. **Item grip rotation:** desalinhamento em multi-axis transforms.
5. **Profile/Mixpack drift:** recursos feitos para versão anterior.
6. **Performance after reload:** comportamento reportado historicamente.
7. **Battle-mode transition:** bridge não reativa/desativa no momento correto.

## 14. Matriz de testes
- [ ] Cliente/servidor iniciam com Punchy 2.7e.
- [ ] Main/offhand e left-handed mode renderizam corretamente.
- [ ] Mining/attack/use/equip não deixam mão presa.
- [ ] Resource reload preserva Hand Editor/Profile sem transform stale.
- [ ] Som de animação encerra quando animação para/reload ocorre.
- [ ] Held item multi-axis mantém orientação correta.
- [ ] Epic Fight battle mode desativa Punchy via bridge e restaura no retorno.
- [ ] First Person Model não duplica mão/corpo de forma grave.
- [ ] Shaders/resource packs não causam degradação evidente após múltiplos reloads.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: JAR, mod id/runtime e três mixin configs.
- Publicação oficial: Punchy 2.7e Release NeoForge 1.21.1, 03/09/2026.
- Changelog 2.7e: final resource refresh, animation sound cleanup e itemgrip rotation/alignment fixes.
- Pack físico: Punchy Epic Fight Compat 1.0.0 instalado.
- **Limite:** profiles/Mixpacks/config local não foram inventariados; compatibilidade visual é validada por smoke, não pela mera presença dos mods.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Punchy! - First person animations
- **Último candidato 1.21.1 / NeoForge localizado:** **2.7e (NeoForge 1.21.1)**
- **Estado:** **REVALIDADO — sem bump localizado**
- **Gate de promoção:** Manter baseline; testar primeira pessoa com Epic Fight, animações, câmera/FOV e mods de render se houver troca futura.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/punchy

## Auditoria acumulada — lote 11–20 (22/10/2026)

**Versão instalada:** Punchy 2.7e, NeoForge 1.21.1. **Mais recente no alvo:** 2.7e, mesma versão. A listagem oficial separa 1.21.1 de 1.21.11 e Minecraft 26.x: builds de outras versões com o mesmo número 2.7e não são upgrades aplicáveis.

**Intervalo instalado → upstream:** vazio. Portanto não há release intermediária nova a descrever. O dossier já registra o changelog do 2.7e (resource refresh, término de sons de animação e item grip alignment); esses fixes já estão incorporados à linha física e não constituem uma atualização futura.

**Conferências para mudanças futuras:** Punchy Epic Fight Compat, battle-mode switch, First Person Model, Hand Editor, perfis/Mixpacks, animations on reload, left-handed e shaders. Nenhum teste de runtime foi executado agora.

**Fonte primária da distribuição:** https://www.curseforge.com/minecraft/mc-mods/punchy/files/all (2.7e NeoForge 1.21.1, 03/09/2026).

## Pendência de categorias CurseForge — 22/10/2026

Categorias temáticas oficiais atuais: API and Library + Cosmetic + Map and Information. Caminho atual: Cosmetic + Map and Information. Há discrepância e a movimentação necessita revisão dos links internos e índice. Não considerar este dossier inteiramente reorganizado ou liberado para retirada da lista até reconciliação verificada. O JAR físico e basename permanecem inalterados.

Fonte: https://www.curseforge.com/minecraft/mc-mods/punchy

## Encerramento da pendência estrutural — 23/10/2026

> **A antiga seção “Pendência de categorias CurseForge — 22/10/2026” foi superada:** o dossier foi movido, preservando integralmente o basename e a versão `✅-punchy v2.7e.md`, para `API and Library + Cosmetic + Map and Information/`. As categorias **API and Library, Cosmetic e Map and Information** foram verificadas no projeto oficial CurseForge **1374153**. Link do índice `modlist.md` corrigido, posição física **#465** e JAR `punchy-2.7e-neoforge-1.21.1.jar` mantidos. No catálogo para NeoForge **Minecraft 1.21.1**, **2.7e (03/09/2026)** permanece o arquivo mais recente identificado; não existe intervalo de releases 1.21.1 posterior ao instalado a revisar.

Fontes: https://www.curseforge.com/minecraft/mc-mods/punchy ; https://www.curseforge.com/minecraft/mc-mods/punchy/files/all?version=1.21.1 . Movimentação sem troca de JAR, sem alteração de funcionamento e sem reauditoria Notion→GitHub. As ressalvas gerais de QA de mods visuais continuam, sem impedir o encerramento documental da conferência de releases.

## Revisão de catálogo integral corrigida — 23/10/2026 (2.7e → 2.8g)

**Correção obrigatória à nota anterior “Encerramento da pendência estrutural”:** o CurseForge **publicou releases posteriores a 2.7e para exatamente Minecraft 1.21.1 e NeoForge**. A afirmação anterior de que 2.7e era a última versão é falsa; esta seção prevalece. O arquivo físico documentado continua `punchy-2.7e-neoforge-1.21.1.jar`. A movimentação já concluída para **API and Library + Cosmetic + Map and Information** permanece válida e não autoriza renomear o dossier.

**Releases posteriores publicadas na linha NeoForge 1.21.1:** `2.8a` (15/09), `2.8b` (25/09), `2.8c` (29/09), `2.8f` (14/10), `2.8g` (18/10). A lista filtrada não mostra artefatos específicos 2.8d ou 2.8e para esta combinação de jogo/loader; não inventar esses deltas. Fonte oficial: https://www.curseforge.com/minecraft/mc-mods/punchy/files/all?gameVersionTypeId=6&page=1&pageSize=20&showAlphaFiles=hide

### 2.8a — 15/09
A publicação é descrita pelo autor como `Punchy v2.8a HOTFIX`, sem notas técnicas pormenorizadas recuperadas. **O delta exclusivo desta build permanece desconhecido**, não devendo ser substituído por mudanças da 2.8b. Fonte de identificação: https://modrinth.com/mod/punchy-fpa/version/QShDZDjS (mesma série de projeto, outra plataforma; confirmar no arquivo NeoForge exato).

### 2.8b — 25/09
- **Novo:** seletores de compatibilidade `related:<name>` para aplicar configurações a itens com nomes correlacionados; tipos experimentais de animação de locomoção por item (idle, deslocamentos direcionais, corrida, salto, queda e aterrissagem).
- **Correções:** animação de mineração não deve reiniciar quando enchantments/components mudam (inclusive Penchant); estados obsoletos de offhand e de transições de equip; `disable_mining` não deve repetir combo durante clique sustentado; resolução de conflitos `enchantment_variants` e `particle_emitters` ao mesclar packs; correção de sleeves de armaduras aparadas que apareciam pretas.
- **Performance:** otimizações gerais; autor ressalva QA limitado da combinação com outros mods.
Fonte: https://modrinth.com/mod/punchy-fpa/version/Y4cSOAFP (artefato NeoForge 1.21.1 do mesmo projeto).

### 2.8c — 29/09
- Opções por item para desativar a animação de queda da mace e a animação geral de queda do braço.
- Novos tipos de compatibilidade para animação de tridente (`use_trident`, `use_trident_throw`, `use_trident_throw_loyalty`) e `movement_influence`.
- Correção de combo que parava após o primeiro ataque para `disable_mining_animation`; tremulação/sumiço da mão secundária após transições de ações com duas mãos.
- As notas sobre shader em Minecraft 26.x são **restritas às versões 26.x** e não constituem novidade comprovada no runtime 1.21.1.
Fonte: https://modrinth.com/mod/punchy-fpa/version/inqESDtx (NeoForge 1.21.1).

### 2.8f — 14/10
- **Spatial GUI:** habilitar compatibilidade configura opções de inventário/containers/mira uma única vez, preservando mudanças posteriores do usuário; evita animação de clique duplicada com Punchy e mantém offsets personalizados de braços F8.
- **Blend/animator:** transições suaves de bow/crossbow com cancelamento/retrigger, comer/beber em fases, movimento de braços ao nadar e handoff da mão secundária; preservação de timing de áudio e de offsets ao transitar entre timelines antigas e V2.
Fonte de changelog da série 2.8f: https://modrinth.com/mod/punchy-fpa/version/kburaezy (arquivo Fabric 1.21.1; **não foi possível piná-lo ao texto do arquivo NeoForge**, mas o build 2.8f NeoForge 1.21.1 está confirmado no CurseForge). Confirmar equivalência de implementação no artefato específico antes de certificar todos os deltas como presentes.

### 2.8g — 18/10
- Corrigido flash do offhand na animação inspect e sons de inspeção ausentes; uso de projéteis quando o último stack é consumido ou um equip de duas mãos é interrompido.
- Configurações de movimento de animações customizadas aplicam-se durante ações e equip, e retornam suavemente a idle; influência de movimento com duas mãos sem contaminar ação de um só braço; evita aplicar idle customizado duas vezes.
- Preserva deslocamentos e rotações globais dos braços em ataque/equip; corrige modelos customizados que atravessavam uma pose neutra indevida.
- Corrige posicionamento de linha na vara com Enchanted Fishing Line; exibe avisos de conflito com Hold My Items/Locomotion, ou ausência de animações/arquivos de compatibilidade ativos.
Fonte da release série 2.8g: https://www.curseforge.com/minecraft/mc-mods/punchy/files/9239914 (Fabric 26.2); a versão **NeoForge 1.21.1 2.8g** é confirmada separadamente pelo catálogo oficial, sem presumir diferenças entre loaders.

### Decisão e testes
**Candidato upstream NeoForge 1.21.1: 2.8g.** A sequência de cinco versões publicadas foi identificada e o conteúdo relevante conhecido catalogado. Permanecem **lacunas de changelog individual exato 2.8a e comprovação de texto para NeoForge nas 2.8f/2.8g**, logo a auditoria integral por build **não está certificada**. Validações necessárias no modpack: Epic Fight bridge e battle mode, First Person Model, shaders e arm model, enchants/components dinâmicos com Penchant, tridentes, dual-hand equips, Spatial GUI se instalado, animações personalizadas e resource reload. Nenhum JAR foi trocado; nenhuma QA in-game foi executada. **Não apagar item 11 da lista de pendências ainda.**

## Retificação do percurso de publicações: 2.8d e 2.8e no Modrinth — 23/10/2026

**Inventário físico preservado:** `punchy-2.7e-neoforge-1.21.1.jar`, mod id `punchy`, runtime 2.7e, posição #465. **Projeto CurseForge oficial** `punchy` (ID 1374153); categorias oficiais previamente verificadas **API and Library + Cosmetic + Map and Information**. **Outra fonte oficial do mesmo mod:** projeto Modrinth `punchy-fpa` (ID `8aoMKplv`, marca/autoria Punchy), cujos artefatos devem ser considerados **separadamente do filtro de arquivos CurseForge**, sem presumir publicação cruzada de cada build.

### Correção de lacuna histórica
A seção anterior afirmou que versões **2.8d e 2.8e não aparecem no catálogo filtrado CurseForge**. Isso **não autoriza concluir inexistência das releases para NeoForge 1.21.1**. Há **artefatos exatos publicados pelo projeto oficial no Modrinth**:

- **2.8d, NeoForge 1.21.1**, versão Modrinth `NFoiGe81`: correção de **lanternas e outros itens com física de pêndulo** que saltavam ao equipar ou finalizar ataque/mineração; sincronização de pêndulo para itens e partes do modelo; sway de caminhada menos oscilante e mais ponderado. Também corrige **atraso no carregamento da besta** ao recarregar imediatamente após equipar e **offhand vazio retornando brevemente a idle** durante charge/troca e animações hand-out/hand-in puladas no disparo. Changelog do próprio artefato: https://modrinth.com/mod/punchy-fpa/version/NFoiGe81 .
- **2.8e, NeoForge 1.21.1**, versão Modrinth `luK5HVTT`: nova integração opcional **Spatial GUI** (toggle **ligado por padrão**) para inventário, crafting tables, fornalhas e outras telas, mostrando transições hand-out/hand-in e item no cursor na mão do personagem. O changelog global também menciona atualização de metadata do **Minecraft 26.4 snapshot** (outra versão, não alteração da 1.21.1) e integração experimental com Mielon's The Sift/Tiny Takeovers para carregar Blubs domesticados; não presumir que este mod opcional esteja instalado. Fonte do arquivo exato: https://modrinth.com/mod/punchy-fpa/version/luK5HVTT .

**Nova matriz de versões identificadas entre a física e 2.8g:** `2.8a → 2.8b → 2.8c → 2.8d → 2.8e → 2.8f → 2.8g`. Para 2.8d/2.8e a evidência de NeoForge 1.21.1 é **Modrinth oficial**, e não se reivindica que o CurseForge distribua exatamente as mesmas duas builds. A conclusão anterior de 'cinco releases na linha CurseForge' pode continuar válida **somente com escopo restrito ao catálogo dessa plataforma**.

### Repercussões técnicas e QA futuro
- [ ] Caso Spatial GUI esteja presente no pack, verificar transições entre inventário, crafting e containers, item arrastado no cursor, abrir/fechar menus repetidamente, offsets F8 e sobreposição com Epic Fight/First Person Model.
- [ ] Verificar crossbow quick-reload após equip, disparos com item na mão secundária, pêndulo de lanternas em corrida/ataque/mineração e modelos com braços customizados.
- [ ] Manter a comparação lado a lado de Punchy Epic Fight Compat, shaderpack e resource packs com animações de primeira pessoa; nenhum desses testes foi executado aqui.

**Status fail-closed:** a série de candidatos NeoForge 1.21.1 está agora melhor catalogada, mas **o changelog exclusivo da 2.8a continua publicado apenas como “HOTFIX”** e as correspondências exatas das notas **2.8f/2.8g** com seus artefatos NeoForge ainda precisam ser reconciliadas. Portanto **não liberar o item #11** da fila. Não instalar, mover ou renomear `✅-punchy v2.7e.md`.


## Complemento de intervalo — release 2.8 NeoForge 1.21.1 identificada em 24/10/2026

**Correção material da matriz anterior:** a sequência documentada até aqui começava em `2.8a`, mas há uma **release `2.8` (sem sufixo), explicitamente publicada pelo autor para Minecraft 1.21.1 / NeoForge**. Trata-se de uma publicação distinta do `2.8a HOTFIX`, não de um apelido da mesma versão. **Fonte primária do artefato exato:** [Punchy 2.8 (NeoForge) — 1.21.1, Modrinth `TCa8rsY7`](https://modrinth.com/mod/punchy-fpa/version/TCa8rsY7). O arquivo físico permanece `punchy-2.7e-neoforge-1.21.1.jar` / runtime `2.7e`, posição física **#465**; nenhum JAR foi instalado ou substituído.

### Delta próprio da 2.8, conforme changelog do artefato NeoForge 1.21.1

| Superfície | Mudanças explicitadas pelo autor | Implicações específicas para validação |
|---|---|---|
| **Interface de estilos** | Menus de estilo mais compactos; controles e scrollbars realinhados. | Reabrir e salvar Hand Editor/Hand Config, conferir resolução, escala da UI e persistência de perfil. |
| **Braços com armadura** | Correção das mangas de chestplate em primeira pessoa para acompanhar o movimento dos braços animados. | Conferir armaduras vanilla/modded e combinações com renderers de jogador e Epic Fight, sem atribuir compatibilidade in-game não testada. |
| **ItemPhysic Full e drop Q** | Ao lançar um item carregado com Q, a animação de mão não deve reiniciar repetidamente; só é reproduzida quando o item sai efetivamente do slot. | Reproduzir lançamento Q normal/carregado, stacks e animações interrompidas caso ItemPhysic Full esteja presente. |
| **Alimentos e stacks** | Correção da animação de soltar stacks de comida com Q. | Avaliar comportamento ao soltar parte ou totalidade de um stack durante ações de uso. |
| **Hand Config: profiles** | Correção de carregamento, salvamento, exclusão e reset de perfis, evitando reloads de recursos desnecessários. | Conferir backup dos perfis, alterações de configuração em runtime e ausência de recarga excessiva. |
| **Troca de resource packs** | Corrige alimentos e outros itens segurados que ficavam visualmente invertidos após mudar de pack, sem exigir reiniciar o jogo. | Testar alternância de resource packs com itens equipados, incluindo packs de animação visual. |

**Delimitação estrita de plataforma:** o changelog desse mesmo arquivo também anuncia suporte de lançamento ao **Minecraft 26.3**, correção de pêndulo com **Improved Transparency em Minecraft 26.2+** e correção de carregamento sem fio **Powah em Forge 1.20.1**. Esses trechos se referem explicitamente a **outros alvos**; não devem ser promovidos a mudanças de runtime NeoForge 1.21.1. Não há evidência aqui de que **ItemPhysic Full** ou os demais providers opcionais citados estejam instalados na modlist física.

### Sequência corrigida e lacunas restantes

**Ordem de releases nomeadas encontradas depois da 2.7e:** `2.8 → 2.8a → 2.8b → 2.8c → 2.8d → 2.8e → 2.8f → 2.8g`. A versão `2.8` tem changelog **direto do arquivo NeoForge 1.21.1**; `2.8a` permanece identificada apenas como **HOTFIX**, sem delta exclusivo técnico publicado na fonte até aqui recuperada. As 2.8d/2.8e já têm fontes diretas NeoForge no Modrinth, conforme seções anteriores. Para 2.8f/2.8g, o **CurseForge** confirma os arquivos concretos NeoForge 1.21.1 `punchy-2.8f-neoforge-1.21.1.jar` (ID **9172981**) e `punchy-2.8g-neoforge-1.21.1.jar` (ID **9239897**), mas o texto granular recuperado até aqui ainda é proveniente de outra combinação de loader/game version. A existência do binário correto **não equivale a atestar cada implementação contida nele**.

**Categorias revalidadas diretamente em 24/10 no [projeto CurseForge 1374153](https://www.curseforge.com/minecraft/mc-mods/punchy):** `API and Library`, `Cosmetic`, `Map and Information`. A pasta atual `API and Library + Cosmetic + Map and Information` permanece correta; não mover nem renomear o dossier.

**Decisão:** a lacuna nova da `2.8` foi preenchida com fonte primária, mas a versão `2.8a` e a atribuição exata por JAR de `2.8f`/`2.8g` **continuam bloqueadas**. Nenhuma verificação binária, QA client-side ou smoke test foi executado nesta revisão. **Item 11 deve permanecer no Bloco de Notas.**

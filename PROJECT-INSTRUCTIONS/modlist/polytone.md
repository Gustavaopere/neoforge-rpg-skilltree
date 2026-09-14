# Polytone

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819c8e42fe88295e416a
- **Baseline física pré-update:** `polytone-1.21-4.2.0-neoforge.jar`
- **Artefato alvo selecionado no CurseForge:** `polytone-1.21-4.4.0-neoforge.jar` — Release, NeoForge 1.21.1, publicado em 11/09/2026
- **Embedded baseline do JAR 4.2.0:** CodecUI 1.4.3, exp4j 0.4.8 e MVEL2 2.5.4.Final; versões embarcadas do alvo 4.4.0 ainda precisam ser extraídas do JAR físico
- **Data da atualização documental GitHub:** 2026-09-14

> **BOUNDARY FÍSICO.** A modlist física fornecida ainda confirma Polytone 4.2.0. O GitHub registra 4.4.0 como alvo de atualização. A listagem oficial confirma o novo arquivo, mas não foi localizado changelog público release-specific suficientemente detalhado para atribuir mudanças internas à 4.3.0/4.4.0. O conteúdo 4.2.0 abaixo permanece baseline comprovada e os deltas posteriores ficam fail-closed.

## Propriedades equivalentes do catálogo

- **Mod:** Polytone
- **Arquivo JAR alvo:** `polytone-1.21-4.4.0-neoforge.jar`
- **Versão 1.21.1 alvo:** 1.21-4.4.0
- **Baseline física auditada:** 1.21-4.2.0
- **Estado da pesquisa:** Verificado documentalmente; validação física/binária da 4.4.0 pendente
- **Decisão:** Sem decisão
- **Categoria:** Visual, Biblioteca
- **Função:** Framework resource-pack driven para personalizar aspectos visuais e ambientais como colormaps, propriedades de blocos/itens, partículas, sons, GUI e biomas.
- **Dependências:** NeoForge 1.21.1. Utilidade depende de resource packs/consumers. O JAR físico 4.2.0 embute CodecUI 1.4.3, exp4j 0.4.8 e MVEL2 2.5.4.Final; não promover essas libraries a top-level nem assumir que as versões internas permanecem idênticas em 4.4.0 sem inspeção.
- **Sobreposição:** Framework amplo de extensions de resource packs; não é equivalente a Fusion/Continuity nem shader pack. Redundância só pode ser avaliada por feature/resource pack concreto.
- **Compatibilidade/Riscos:** Resource-pack driven. Riscos: schema/pack drift entre 4.2.0→4.4.0, water-fog + shaders, GPU particle force spawn + culling/overdraw, tooltip/GUI composition, biome cache e embedded-library ambiguity.
- **Observações:** Baseline 4.2.0 publicou GPU particle force spawn e novas propriedades em `colors.json` para water fog. Upstream publicou 4.3.0 e 4.4.0 em 11/09/2026; nenhum changelog detalhado dessas duas releases foi localizado nesta auditoria.
- **Procedência:** modlist.txt física baseline + CurseForge oficial Polytone files para NeoForge 1.21.1 + documentação 4.2.0 já auditada + listagem oficial 4.4.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/polytone
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/09/2026 — GitHub promovido para alvo 4.4.0; delta 4.3.0/4.4.0 mantido fail-closed por ausência de changelog release-specific suficiente. Baseline funcional 4.2.0 preservada para regressão.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico baseline: `polytone-1.21-4.2.0-neoforge.jar`, mod id `polytone`, versão `1.21-4.2.0`. Alvo documental: `polytone-1.21-4.4.0-neoforge.jar`, Release NeoForge 1.21.1. O comportamento abaixo é comprovado para a baseline/linha; diferenças 4.3.0/4.4.0 que não possuem evidência específica não são inventadas.

## 1. Identidade e papel
- **Mod:** Polytone.
- **JAR físico baseline:** `polytone-1.21-4.2.0-neoforge.jar`.
- **JAR alvo:** `polytone-1.21-4.4.0-neoforge.jar`.
- **Mod id baseline:** `polytone`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** MehVahdJukaar.
- **Licença:** GPLv3.
- **Ambiente publicado:** Client & Server.
- **Papel:** ampliar o que resource packs podem customizar além do vanilla/OptiFine-like formats.
- **Decisão:** Sem decisão.

## 2. Resource-pack authority
Polytone fornece engine/formatos para resource packs. O resultado visual efetivo depende dos packs carregados.

Separar:
- Polytone → parser/runtime e extensões;
- resource pack → dados/assets/regras visuais;
- mod provider → gameplay real do bloco/item/bioma.

Uma mudança de cor/som/tooltip não altera mecanicamente o objeto salvo pelo jogo, salvo integração explicitamente documentada.

## 3. Superfícies publicadas
O projeto oficial lista suporte a customizações como:
- block sounds;
- colors/colormaps/lightmaps;
- post shaders;
- custom item models;
- biome variant textures;
- GUI modifiers e tooltips;
- custom particles;
- creative tabs;
- compatibilidade com formatos OptiFine.

Nem toda superfície está necessariamente usada pela stack atual de resource packs.

## 4. Colors e colormaps
Custom colors/colormaps podem afetar blocos, água, foliage e outras apresentações dependentes de contexto. Em worldgen extenso, testar transições entre biomas para evitar seams ou colormap incorreto.

A baseline 4.2.0 adiciona **novas propriedades em `colors.json` para water fog**, tornando fog de água um regression gate específico. Após atualizar para 4.4.0, esses packs precisam ser revalidados em vez de presumir schema idêntico.

## 5. Custom sounds
Resource packs podem ajustar sons de blocos. Isso pode compor com Presence Footsteps e sound-type providers.

Ownership importante:
- Polytone/resource pack altera mapeamento/apresentação sonora;
- Presence Footsteps decide footsteps segundo surface context;
- gameplay como stealth/AI não deve ser inferido do som sem provider explícito.

## 6. Partículas e GPU particle force spawn
A baseline 4.2.0 publica **GPU particle force spawn**. Essa superfície interage com a pilha de partículas do pack:
- Particle Effects;
- Particle Rain;
- Particular;
- PartiCull;
- shaders/Iris.

Testar se force-spawn contorna culling/limits de forma que aumente excessivamente overdraw; não assumir bypass sem reproduzir o caso real. Repetir a regressão em 4.4.0 porque o salto inclui duas releases.

## 7. GUI, tooltips e item models
Polytone pode personalizar GUI/tooltips e modelos de item via resource data. Isso cruza Obscure Tooltips e outros renderers, mas não é conflito automático.

Testar item com custom renderer/model, tooltip longo e GUI scale extrema. Se conteúdo textual estiver errado, identificar o pack/provider antes de atribuir ao engine.

## 8. Biome variant textures
Biome-dependent assets podem variar conforme contexto. Em BWG/Terralith e fronteiras densas de bioma, validar:
- troca correta ao cruzar border;
- cache invalidation;
- resource reload;
- dimension change;
- shader state.

Texture variant não altera registry/biome logic.

## 9. Embedded libraries
A hierarquia física do JAR 4.2.0 registra sob o host:
- `codecui-neoforge-1.21.1-1.4.3.jar` — mod id `codecui`, versão `1.21.1-1.4.3`;
- `exp4j-0.4.8.jar`;
- `mvel2-2.5.4.Final.jar`.

Regra canônica:
- não criar ordinais/páginas top-level;
- não atualizar isoladamente;
- documentar apenas como dependencies embutidas de Polytone;
- reextrair a hierarquia da 4.4.0 após instalação antes de afirmar versões embarcadas.

## 10. Codec/expression boundary
A presença de exp4j/MVEL2 indica infraestrutura para expressões/avaliação usada pelo host. Esta ficha não afirma quais fórmulas concretas a build executa sem source/config específico.

Resource packs malformados precisam falhar de modo controlado; expressão inválida não deve corromper world state.

## 11. Client/server e resource lifecycle
Embora muitas features sejam visuais/client-facing, o projeto publica ambiente Client & Server. Validar:
- dedicated server sem classloading de renderer indevido;
- clientes com resource packs diferentes não divergindo em gameplay;
- reload de resources;
- join/reconnect;
- mudança de dimensão;
- pack enable/disable.

## 12. Sobreposição com outros frameworks
Polytone não equivale a Fusion/Continuity nem a um shader pack. Ele cobre um conjunto amplo de extensions resource-pack driven.

Sobreposição deve ser avaliada por feature específica: connected textures, colors, particles, models, sounds etc. Uma outra library visual não é substituta automática.

## 13. Delta de versão 4.2.0 → 4.4.0
A listagem oficial para Minecraft 1.21.1/NeoForge mostra:
- 4.2.0 — Release, 05/09/2026;
- 4.3.0 — Release, 11/09/2026;
- 4.4.0 — Release, 11/09/2026.

A 4.4.0 é, portanto, a release NeoForge 1.21.1 mais recente localizada. Nesta auditoria não foi localizado changelog público detalhado que permita mapear 4.3.0/4.4.0 a features/fixes específicos. O único procedimento correto é **version bump + regressão das superfícies existentes + inspeção do novo JAR/resource formats**.

## 14. Riscos
1. **Resource-pack/schema drift** entre 4.2.0 e 4.4.0.
2. **Water fog:** propriedades da 4.2.0 podem compor mal com shaders ou mudar de schema.
3. **GPU particles:** force spawn pode elevar custo visual em stack denso.
4. **Tooltip/GUI composition:** conflito de presentation layer.
5. **Biome cache:** variant/colormap pode ficar stale entre borders/reload.
6. **Embedded library ambiguity:** versões do CodecUI/exp4j/MVEL2 precisam ser reextraídas do alvo.
7. **Expression errors:** data malformada pode quebrar um recurso específico.
8. **No proven consumer:** remover/classificar como Dependência exige mapear resource packs efetivamente usados.
9. **Opaque delta:** não há changelog release-specific suficiente para antecipar regressões 4.3/4.4 por feature.

## 15. Matriz de testes
- [ ] Nova modlist física confirma `polytone-1.21-4.4.0-neoforge.jar`.
- [ ] Extrair metadata/JarJar da 4.4.0 e reconciliar embedded libraries.
- [ ] Cliente e dedicated server iniciam com Polytone 4.4.0.
- [ ] Resource pack Polytone representativo carrega sem parse error.
- [ ] Water fog customizado funciona com shader ligado/desligado.
- [ ] Biome colors/variants mudam corretamente em borders BWG/Terralith.
- [ ] Custom particle representativa não causa spam sob PartiCull/GPU force spawn.
- [ ] Custom block sound compõe com Presence Footsteps sem double sound evidente.
- [ ] GUI/tooltip/model customizado permanece estável em várias GUI scales.
- [ ] Resource reload aplica/remove dados sem stale cache.
- [ ] Dimension/reconnect preserva somente resources atuais.
- [ ] Nenhuma library embutida vira entrada top-level duplicada.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física baseline: `polytone-1.21-4.2.0-neoforge.jar`, mixins e três libraries embutidas da baseline.
- CurseForge oficial: project 958094; `polytone-1.21-4.4.0-neoforge` é Release para 1.21.1/NeoForge publicada em 11/09/2026.
- Changelog 4.2.0: GPU particle force spawn e novas propriedades `colors.json` para water fog.
- Descrição oficial: sounds, colors/lightmaps/colormaps, post shaders, item models, biome textures, GUI/tooltips, particles, creative tabs e OptiFine format support.
- **Limite:** não foi localizado changelog detalhado da 4.3.0/4.4.0 nesta auditoria; internals, schema changes e embedded versions do alvo não foram inferidos. Instalação física de 4.4.0 continua pendente de nova modlist/JAR.

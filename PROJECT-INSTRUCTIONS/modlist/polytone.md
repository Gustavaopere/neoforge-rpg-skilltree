# Polytone

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819c8e42fe88295e416a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `polytone-1.21-4.2.0-neoforge.jar`, mod id `polytone`, runtime `1.21-4.2.0`, mixins `polytone.mixins.json` e `polytone-common.mixins.json`; CodecUI 1.4.3, exp4j 0.4.8 e MVEL2 2.5.4.Final embutidos
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Polytone 4.2.0 está presente; CodecUI/exp4j/MVEL2 aparecem apenas sob o host e não são tratados como top-level. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Polytone
- **Arquivo JAR:** `polytone-1.21-4.2.0-neoforge.jar`
- **Versão 1.21.1:** 1.21-4.2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Biblioteca
- **Função:** Framework resource-pack driven para personalizar aspectos visuais e ambientais como colormaps, propriedades de blocos/itens, partículas, sons e biomas.
- **Dependências:** NeoForge 1.21.1. Utilidade depende de resource packs/consumers. Embedded no JAR: CodecUI 1.4.3, exp4j 0.4.8 e MVEL2 2.5.4.Final; não catalogar como top-level.
- **Sobreposição:** Framework amplo de extensions de resource packs; não é equivalente a Fusion/Continuity nem shader pack. Redundância só pode ser avaliada por feature/resource pack concreto.
- **Compatibilidade/Riscos:** Resource-pack driven. Riscos: pack/schema drift, water-fog + shaders, GPU particle force spawn + culling/overdraw, tooltip/GUI composition, biome cache e embedded-library ambiguity.
- **Observações:** Runtime 1.21-4.2.0, Release NeoForge 1.21.1 de 05/09/2026. Delta publicado: GPU particle force spawn e novas propriedades em colors.json para water fog.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Polytone 4.2.0 + hierarquia física das libraries embutidas.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/polytone
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Polytone 4.2.0 reconstruído: resource-pack framework, colors/water fog, GPU particles, sounds/models/GUI, embedded CodecUI/exp4j/MVEL2, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `polytone-1.21-4.2.0-neoforge.jar`, mod id `polytone`, versão `1.21-4.2.0`, NeoForge 1.21.1. Polytone é um framework orientado a resource packs para customizar cores, sons, particles, GUI, item models, biome textures e outras superfícies visuais/data-driven. O JAR embute CodecUI 1.4.3, exp4j 0.4.8 e MVEL2 2.5.4.Final; nenhum deles recebe entrada top-level.

## 1. Identidade e papel
- **Mod:** Polytone.
- **JAR físico:** `polytone-1.21-4.2.0-neoforge.jar`.
- **Mod id:** `polytone`.
- **Runtime:** `1.21-4.2.0`.
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

A 4.2.0 adiciona **novas propriedades em `colors.json` para water fog**, tornando fog de água um regression gate específico.

## 5. Custom sounds
Resource packs podem ajustar sons de blocos. Isso pode compor com Presence Footsteps e sound-type providers.

Ownership importante:
- Polytone/resource pack altera mapeamento/apresentação sonora;
- Presence Footsteps decide footsteps segundo surface context;
- gameplay como stealth/AI não deve ser inferido do som sem provider explícito.

## 6. Partículas e GPU particle force spawn
A 4.2.0 publica **GPU particle force spawn**. Essa superfície interage com a pilha de partículas do pack:
- Particle Effects;
- Particle Rain;
- Particular;
- PartiCull;
- shaders/Iris.

Testar se force-spawn contorna culling/limits de forma que aumente excessivamente overdraw; não assumir bypass sem reproduzir o caso real.

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
A hierarquia física registra sob o host:
- `codecui-neoforge-1.21.1-1.4.3.jar` — mod id `codecui`, versão `1.21.1-1.4.3`;
- `exp4j-0.4.8.jar`;
- `mvel2-2.5.4.Final.jar`.

Regra canônica:
- não criar ordinais/páginas top-level;
- não atualizar isoladamente;
- documentar apenas como dependencies embutidas de Polytone.

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

## 13. Riscos
1. **Resource-pack drift:** formato/asset pode ficar incompatível após update.
2. **Water fog:** novas propriedades da 4.2.0 podem compor mal com shaders.
3. **GPU particles:** force spawn pode elevar custo visual em stack denso.
4. **Tooltip/GUI composition:** conflito de presentation layer.
5. **Biome cache:** variant/colormap pode ficar stale entre borders/reload.
6. **Embedded library ambiguity:** CodecUI/exp4j/MVEL2 não são top-level.
7. **Expression errors:** data malformada pode quebrar um recurso específico.
8. **No proven consumer:** remover ou classificar como Dependência exige mapear resource packs efetivamente usados.

## 14. Matriz de testes
- [ ] Cliente e dedicated server iniciam com Polytone 4.2.0.
- [ ] Resource pack Polytone representativo carrega sem parse error.
- [ ] Water fog customizado funciona com shader ligado/desligado.
- [ ] Biome colors/variants mudam corretamente em borders BWG/Terralith.
- [ ] Custom particle representativa não causa spam sob PartiCull/GPU force spawn.
- [ ] Custom block sound compõe com Presence Footsteps sem double sound evidente.
- [ ] GUI/tooltip/model customizado permanece estável em várias GUI scales.
- [ ] Resource reload aplica/remova dados sem stale cache.
- [ ] Dimension/reconnect preserva somente resources atuais.
- [ ] Embedded CodecUI/exp4j/MVEL2 resolvem sem entradas top-level duplicadas.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: JAR, mod id/runtime, `polytone.mixins.json`, `polytone-common.mixins.json` e três libraries embutidas.
- CurseForge oficial: project 958094, Release `polytone-1.21-4.2.0-neoforge` para NeoForge 1.21.1 de 05/09/2026.
- Changelog 4.2.0: GPU particle force spawn e novas propriedades `colors.json` para water fog.
- Descrição oficial: sounds, colors/lightmaps/colormaps, post shaders, item models, biome textures, GUI/tooltips, particles, creative tabs e OptiFine format support.
- **Limite:** resource packs ativos/consumers concretos não foram enumerados neste passe; decisão permanece Sem decisão.

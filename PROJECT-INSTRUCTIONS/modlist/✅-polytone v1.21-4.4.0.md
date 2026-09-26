# Polytone

## Propriedades do registro

- **Mod:** Polytone
- **Arquivo JAR:** polytone-1.21-4.4.0-neoforge.jar
- **Versão 1.21.1:** 1.21-4.4.0
- **Categoria:** Visual, Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/polytone
- **Função:** Framework resource-pack driven para personalizar aspectos visuais e ambientais como colormaps, propriedades de blocos/itens, partículas, sons e biomas.
- **Dependências:** NeoForge 1.21.1. Utilidade depende de resource packs/consumers. Embedded no JAR físico atual: CodecUI 1.21.1-1.4.3, exp4j 0.4.8 e Nexp 1.2.0; não catalogar como top-level.
- **Compatibilidade/Riscos:** Resource-pack driven. Riscos: pack/schema drift, water-fog + shaders, GPU particle force spawn + culling/overdraw, tooltip/GUI composition, biome cache e embedded-library ambiguity.
- **Sobreposição:** Framework amplo de extensions de resource packs; não é equivalente a Fusion/Continuity nem shader pack. Redundância só pode ser avaliada por feature/resource pack concreto.
- **Observações:** Runtime físico 1.21-4.4.0, Release NeoForge 1.21.1 de 11/09/2026. A documentação de 4.2.0 permanece como lineage para GPU particle force spawn e water fog; o JAR atual embute Nexp 1.2.0 no lugar da referência antiga a MVEL. Releases 4.5.x posteriores existem upstream e não estão instaladas.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + CurseForge oficial Polytone 4.4.0 + hierarquia física das libraries embutidas.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — Polytone físico corrigido de 4.2.0 para 4.4.0; JAR/runtime e dependências embutidas reconciliados. Releases 4.5.x posteriores existem upstream e não estão instaladas.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #449: JAR `polytone-1.21-4.4.0-neoforge.jar`, mod id `polytone`, runtime `1.21-4.4.0`, SHA-1 `dc5e9c7ae01c4d1748c4b3ba223e8082144e0b29`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `polytone-1.21-4.4.0-neoforge.jar`, mod id `polytone`, versão `1.21-4.4.0`, NeoForge 1.21.1. Polytone é um framework orientado a resource packs para customizar cores, sons, particles, GUI, item models, biome textures e outras superfícies visuais/data-driven. O JAR físico atual embute CodecUI 1.21.1-1.4.3, exp4j 0.4.8 e Nexp 1.2.0; nenhum deles recebe entrada top-level.
</callout>
## 1. Identidade e papel
- **Mod:** Polytone.
- **JAR físico:** `polytone-1.21-4.4.0-neoforge.jar`.
- **Mod id:** `polytone`.
- **Runtime:** `1.21-4.4.0`.
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
A lineage 4.2.0 adicionou **novas propriedades em ****`colors.json`**** para water fog**; essa capacidade permanece um regression gate relevante na 4.4.0 instalada.
## 5. Custom sounds
Resource packs podem ajustar sons de blocos. Isso pode compor com Presence Footsteps e sound-type providers.
Ownership importante:
- Polytone/resource pack altera mapeamento/apresentação sonora;
- Presence Footsteps decide footsteps segundo surface context;
- gameplay como stealth/AI não deve ser inferido do som sem provider explícito.
## 6. Partículas e GPU particle force spawn
A lineage 4.2.0 introduziu **GPU particle force spawn**. Essa superfície permanece relevante para a 4.4.0 instalada e interage com a pilha de partículas do pack:
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
- `nexp-1.2.0.jar`.
Regra canônica:
- não criar ordinais/páginas top-level;
- não atualizar isoladamente;
- documentar apenas como dependencies embutidas de Polytone.
## 10. Codec/expression boundary
A presença de exp4j/Nexp indica infraestrutura para expressões/avaliação usada pelo host. Esta ficha não afirma quais fórmulas concretas a build executa sem source/config específico.
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
2. **Water fog:** propriedades introduzidas na lineage 4.2.0 podem compor mal com shaders na 4.4.0 instalada.
3. **GPU particles:** force spawn pode elevar custo visual em stack denso.
4. **Tooltip/GUI composition:** conflito de presentation layer.
5. **Biome cache:** variant/colormap pode ficar stale entre borders/reload.
6. **Embedded library ambiguity:** CodecUI/exp4j/Nexp não são top-level.
7. **Expression errors:** data malformada pode quebrar um recurso específico.
8. **No proven consumer:** remover ou classificar como Dependência exige mapear resource packs efetivamente usados.
## 14. Matriz de testes
- [ ] Cliente e dedicated server iniciam com Polytone 4.4.0.
- [ ] Resource pack Polytone representativo carrega sem parse error.
- [ ] Water fog customizado funciona com shader ligado/desligado.
- [ ] Biome colors/variants mudam corretamente em borders BWG/Terralith.
- [ ] Custom particle representativa não causa spam sob PartiCull/GPU force spawn.
- [ ] Custom block sound compõe com Presence Footsteps sem double sound evidente.
- [ ] GUI/tooltip/model customizado permanece estável em várias GUI scales.
- [ ] Resource reload aplica/remova dados sem stale cache.
- [ ] Dimension/reconnect preserva somente resources atuais.
- [ ] Embedded CodecUI/exp4j/Nexp resolvem sem entradas top-level duplicadas.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 15. Evidências e limites
- Modlist física: JAR, mod id/runtime, `polytone.mixins.json`, `polytone-common.mixins.json` e três libraries embutidas.
- CurseForge oficial: project 958094, Release `polytone-1.21-4.4.0-neoforge` para NeoForge 1.21.1 de 11/09/2026. Releases 4.5.x posteriores existem upstream, mas não estão instaladas nesta modlist.
- Lineage 4.2.0: GPU particle force spawn e novas propriedades `colors.json` para water fog; esses pontos permanecem como regression gates na 4.4.0 instalada.
- Hierarquia física 4.4.0: CodecUI 1.21.1-1.4.3, exp4j 0.4.8 e Nexp 1.2.0 estão embutidos sob o host.
- Descrição oficial: sounds, colors/lightmaps/colormaps, post shaders, item models, biome textures, GUI/tooltips, particles, creative tabs e OptiFine format support.
- **Limite:** resource packs ativos/consumers concretos não foram enumerados neste passe; decisão permanece Sem decisão.

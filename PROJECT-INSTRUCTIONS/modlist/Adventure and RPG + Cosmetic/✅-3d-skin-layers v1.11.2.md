# 3D Skin Layers

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#506**: JAR `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar`, mod id `skinlayers3d`, runtime `1.11.2`, SHA-1 `d688d428ba72a5bd57b44d8954793c59d8576838`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** 3D Skin Layers
- **Arquivo JAR:** `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar`
- **Versão 1.21.1:** 1.11.2
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Renderer client-side que transforma a segunda camada da skin em geometria 3D, com transparência, player-head support e fallback automático para layer 2D além de 12 blocos.
- **Dependências:** Client-side; sem gameplay dependency. Integração upstream suportada com Player Animator. Stack físico relevante: Epic Fight 21.17.3.1, FirstPerson 2.7.2, NotEnoughAnimations 1.12.4, Iris 1.8.14-beta.1, Sodium 0.8.13 e ImmediatelyFast 1.6.13.
- **Sobreposição:** Cruza somente na renderização do player com FirstPerson, Epic Fight, NotEnoughAnimations, shaders e armor renderers; não altera skin/account nem gameplay. Coexistência deve ser validada por composição visual.
- **Compatibilidade/Riscos:** Client render layer. Riscos: model-transform mismatch, shader side-shadow artifacts, armor clipping, first-person/animation renderer competition, LOD pop e stale skin/model caches. Epic Fight físico inclui mixin compat `skinlayers3d`; Iris/FirstPerson/NotEnoughAnimations também exigem regressão visual.
- **Observações:** 1.11.2 é Release NeoForge 1.21.1 de 18/06/2026. Delta exato dessa release é pequeno (debug/localizações/ports); funcionalidades e compat render vêm da linha atual. Epic Fight possui mixin físico explícito para Skin Layers 3D no pack.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Skin Layers 3D 1.11.2 e documentação/known issues da linha atual.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/skin-layers-3d/files/8274824
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — 3D Skin Layers 1.11.2/JAR físico reconfirmado; 1.11.2 permanece a release NeoForge 1.21.1 mais recente localizada. TRansition 1.0.21 e TRender 1.0.15 embedded documentados; render/animation stack preservado.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar`, mod id `skinlayers3d`, versão `1.11.2`. É um mod **puramente client-side**: troca a segunda camada plana da skin do jogador por geometria 3D e volta automaticamente ao render 2D à distância.

## 1. Identidade e papel
- **Mod:** 3D Skin Layers / Skin Layers 3D.
- **JAR:** `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar`.
- **Mod id:** `skinlayers3d`.
- **Versão:** `1.11.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client.
- **Mixin:** `skinlayers3d.mixins.json`.
- **Componentes internos físicos (****`META-INF/jars`****):** `TRansition 1.0.21` (`transition`) e `TRender 1.0.15` (`trender`). Ambos pertencem ao host 3D Skin Layers e não recebem ordinal/página top-level.
- **Papel:** renderizar outer skin layer em 3D sem alterar gameplay ou servidor.
## 2. Segunda camada 3D
O mod converte hats/jackets/sleeves/pants e demais pixels da segunda camada da skin em pequenas superfícies/volumes 3D. A skin base continua pertencendo ao renderer/player profile vanilla; o mod apenas substitui a apresentação da camada externa.
## 3. Transparência
Pixels transparentes são suportados, permitindo detalhes como óculos/visores sem preencher artificialmente áreas vazias. Alpha/transparency é, portanto, parte do pipeline de mesh e precisa continuar coerente sob resource/skin reload.
## 4. Player head items
A documentação atual também aplica o efeito a player heads. Render de cabeça como item/bloco pode passar por paths diferentes do player entity, então é regression gate separado.
## 5. LOD por distância
Para preservar FPS, o projeto retorna automaticamente ao estilo vanilla 2D quando o player está a mais de **12 blocos**. Isso reduz custo em multiplayer/cenas densas.
A transição precisa evitar flicker/pop excessivo e usar a posição de câmera correta em third-person/free-camera contexts.
## 6. Client-only boundary
O servidor não precisa do mod. Em multiplayer, a geometria 3D é decisão local do observador; não altera hitbox, reach, armor, pose autoritativa nem skin data do outro player.
## 7. Compatibilidade upstream
O projeto declara suporte para Player Animator, o que cobre uma categoria relevante de mods de animação. Também declara suporte para Essential/Shoulder Surfing e suporte parcial a CustomSkinLoader mediante helper específico.
Essas declarações upstream não substituem teste do stack físico do pack.
## 8. Epic Fight no pack
O JAR físico do **Epic Fight 21.17.3.1** contém `epicfight-compat.skinlayers3d.mixins.json`, evidência concreta de uma integration surface entre Epic Fight e Skin Layers 3D.
Testar poses de combate, roll/dodge, attacks, death e transições de mode para garantir que a outer layer acompanhe bones/limbs sem descolar.
## 9. FirstPerson e NotEnoughAnimations
O pack contém FirstPerson `2.7.2` e NotEnoughAnimations `1.12.4`. Ambos alteram caminhos/poses de player render. Não há prova de conflito automático, mas são regression surfaces concretas para braços/corpo em primeira e terceira pessoa.
## 10. Iris/Sodium/shaders
O pack contém Iris e Sodium. A linha do 3D Skin Layers possui histórico de workarounds específicos para shader/Sodium e a documentação atual reconhece que shaders podem produzir sombras estranhas nas faces laterais das layers.
Uma opção de compatibilidade Iris foi adicionada em versões anteriores da linha; deve ser usada somente se necessária, porque pode sacrificar conexões anguladas nos cantos.
## 11. Armor clipping
Como a layer externa é ligeiramente maior que o player model, armaduras/modelos modded podem intersectar ou cobrir partes do volume 3D. Isso é limitação visual, não erro de stats/equipment.
Avaliar especialmente armaduras volumosas, Cosmetic Armor e equipamentos 3D do pack.
## 12. Limitações visuais conhecidas
O upstream registra:
- skins podem não alinhar perfeitamente devido ao outer layer maior;
- padrões zig-zag nos cantos podem se unir de maneira inesperada;
- shaders podem sombrear lateral faces de forma estranha;
- modelos/armaduras custom podem clipar.
Essas limitações devem ser distinguidas de regressões introduzidas pela 1.11.2.
## 13. Delta exato 1.11.2
A release 1.11.2 adiciona suporte/ports para versões Minecraft mais novas, debugging relacionado ao issue #280 e atualizações de localizações `pl_pl`/`zh-cn`.
O known issue publicado para NeoForge 26.2 não se aplica ao runtime 1.21.1 instalado. Não atribuir novas features de render exclusivas à 1.11.2 sem changelog.
## 14. Skin/model lifecycle
Validar:
- login/reconnect;
- skin download/update;
- resource reload;
- player entra/sai de render distance;
- troca 2D↔3D no limiar de 12 blocos;
- head item render;
- armor equip/unequip;
- pose/animation change;
- shader enable/disable/reload.
Mesh/cache antigo não deve persistir depois de skin/model change.
## 15. Performance
O fallback 2D é o mecanismo principal publicado de contenção de custo. Em cenas com muitos jogadores, medir frame time perto e longe do limiar, especialmente com shaders e animações.
O mod não promete zero custo; a função é trocar detalhe por distância de forma controlada.
## 16. Riscos técnicos
1. **Bone/pose mismatch:** mesh 3D não segue animação.
2. **Shader artifact:** side faces escurecem ou z-fighting.
3. **Armor clipping:** layer atravessa modelo/equipment.
4. **Renderer competition:** FirstPerson/Epic Fight/NEA alteram o mesmo player render path.
5. **LOD pop:** troca 3D→2D é visualmente brusca.
6. **Cache stale:** skin antiga continua extrudida após update.
7. **Head render mismatch:** player head não usa textura/layer correta.
8. **Performance regression:** crowd + shader aumenta custo acima do esperado.
## 17. Matriz de testes
- [ ] Cliente inicia com 3D Skin Layers 1.11.2.
- [ ] Outer layer aparece 3D em distância curta.
- [ ] Transparência em óculos/visores permanece correta.
- [ ] Player head item usa layer 3D corretamente.
- [ ] A \>12 blocos ocorre fallback 2D; ao voltar, mesh 3D retorna sem flicker persistente.
- [ ] Epic Fight attacks/dodge/death mantêm limbs/layers alinhados.
- [ ] FirstPerson não cria braços/corpo duplicados ou layers deslocadas.
- [ ] NotEnoughAnimations poses continuam alinhadas.
- [ ] Iris shader on/off não produz corrupção persistente do mesh.
- [ ] Sodium/ImmediatelyFast coexistem sem missing layer.
- [ ] Armor/Cosmetic Armor não apresenta clipping inaceitável nos sets prioritários.
- [ ] Multiplayer com vários players mantém FPS/LOD esperado.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 18. Evidências e limites
- Modlist física atual: 3D Skin Layers 1.11.2 e render stack relevante.
- CurseForge oficial: client-only, 3D second layer, transparency, player heads e fallback 2D \>12 blocos.
- CurseForge 1.11.2: delta exato e known issue de 26.2 não aplicável.
- Epic Fight JAR físico: mixin de compat `skinlayers3d` confirma integration surface.
- **Limite:** nenhuma inspeção visual/runtime foi executada nesta auditoria; qualidade de composição depende do shader, skin e modelos efetivamente usados.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Skin Layers 3D
- **Último candidato 1.21.1 / NeoForge localizado:** **1.10.1 (último arquivo NeoForge explicitamente comprovado para Minecraft 1.21.1 na listagem oficial consultada)**
- **Estado:** **FAIL-CLOSED — DIVERGÊNCIA COM A AUTORIDADE FÍSICA**
- **Gate de promoção:** O dossier físico registra 1.11.2, mas a publicação 1.11.x visível é para versões Minecraft posteriores. Não rebaixar nem reescrever o instalado. Inspecionar o JAR físico/manifest/filename e origem antes de concluir se 1.11.2 é backport, mislabeled ou outro artefato.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/skin-layers-3d/files/8274824

## Reconciliação definitiva NeoForge 1.21.1 — 23/10/2026 (1.11.2 → 1.11.4)

**Autoridade física:** `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar`, versão runtime `1.11.2`, #506 no último snapshot; sem troca de binário. A antiga seção **“Atualização de catálogo — 22/10/2026”**, que dizia que o último arquivo confirmado 1.21.1 seria 1.10.1 e questionava a existência da versão instalada, foi baseada em uma consulta incompleta e **está expressamente revogada**. A listagem oficial filtrada por Minecraft **1.21.1**, ID CurseForge **521480**, confirma arquivos **NeoForge** 1.11.2 (18/06/2026), **1.11.3 (17/09/2026)** e **1.11.4 (18/10/2026)**. Versões 26.2/26.3 para outros Minecraft não foram atribuídas ao cliente 1.21.1.

**Projeto e pasta:** projeto original **Skin Layers 3D**, autor **tr7zw**; categorias temáticas oficiais **Adventure and RPG + Cosmetic**, já correspondentes à pasta usada. Nenhum fork confundido com o original. Fonte: https://www.curseforge.com/minecraft/mc-mods/skin-layers-3d/files/all?page=1&version=1.21.1 .

### 1.11.3 — 17/09/2026 (Release NeoForge 1.21.1)
A release GitHub **tag `1.11.3`** lista:
- Inclusão dos alvos Minecraft **26.3 Fabric/NeoForge** e **26.2 NeoForge**, sem alegar que isso muda gameplay em 1.21.1.
- **Tradução russa** revisada; efeito de localização para clientes que utilizam esse locale.
- O changelog **não declara novas mecânicas de extrusão, LOD ou shader** para 1.21.1; os commits não devem ser reinterpretados como novas funcionalidades.

Fontes: https://github.com/tr7zw/3d-Skin-Layers/releases/tag/1.11.3 e https://github.com/tr7zw/3d-Skin-Layers/compare/1.11.2...1.11.3 .

### 1.11.4 — 18/10/2026 (Release NeoForge 1.21.1)
A release GitHub **tag `1.11.4`** informa:
- **Correção de compatibilidade com resource packs EMF**, com ênfase declarada **em Forge, particularmente Minecraft 1.20.1** (issues #303 e #290). É uma mudança da base compartilhada; **não afirmar que o bug existia no NeoForge 1.21.1** sem reprodução.
- **Rastreamento e descarte de texturas carregadas por recursos** para evitar vazamento de memória/VRAM e estado residual após reload (relacionado à issue #23). Esta é a mudança com efeito potencial mais relevante no pack que utiliza recursos visuais extensos.
- Novas/revistas traduções **ms_my** (malaio) e **it_it** (italiano).
- Atualizações de bibliotecas que corrigem vazamentos em conjunto com **Fabric API** e um crash raro **em Forge** ao carregar vários mods do mesmo autor; não atribuir essas duas correções específicas à distribuição NeoForge.

Fontes: https://github.com/tr7zw/3d-Skin-Layers/releases/tag/1.11.4 e https://github.com/tr7zw/3d-Skin-Layers/compare/1.11.3...1.11.4 . Catálogo exato de JARs NeoForge 1.21.1: https://www.curseforge.com/minecraft/mc-mods/skin-layers-3d/files/all?page=1&version=1.21.1 .

### Relevância, regressões e conclusão
O conjunto completo de atualizações posteriores à versão física **1.11.2 → 1.11.3 → 1.11.4** foi verificado por publicações oficiais e releases pinadas por tag. Validar numa eventual atualização: **EMF/Fresh Animations, Epic Fight, FirstPerson, NotEnoughAnimations, Iris/Sodium, ImmediatelyFast**, ciclo de resource reload, troca de skin, memória/VRAM sob muitas entradas e saídas de mundo, cabeças de jogador, LOD 2D/3D e servidor dedicado sem carregar classes cliente. O contrato é de **renderização**, não de stats/inventário.

**Estado documental:** sequência completa e diferenças conhecidas do autor incorporadas; a versão física continua 1.11.2. **Revisão upstream/documental concluída**; não afirmar QA in-game nem instalação 1.11.4.

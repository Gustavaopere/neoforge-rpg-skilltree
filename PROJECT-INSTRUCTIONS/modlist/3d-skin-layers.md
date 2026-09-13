# 3D Skin Layers

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a1953cdc95631d11bd
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR e render stack confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** 3D Skin Layers
- **Arquivo JAR:** `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar`
- **Versão 1.21.1:** 1.11.2
- **Estado no pack:** Instalado — Dossiê completo
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
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — 3D Skin Layers 1.11.2 reconstruído: second-layer voxelization, 12-block 3D→2D fallback, transparency/player-heads, render/animation stack, exact 1.11.2 delta, shaders/Iris lineage, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:**

> **Divergência documental registrada:** a procedência do Notion menciona “modlist.txt física atual de 11/09/2026”. A modlist física mais recente efetivamente acessível nesta execução é o snapshot de 08/09/2026 com 595 entradas; ele confirma o JAR `skinlayers3d-neoforge-1.11.2-mc1.21.1.jar` e o render stack citado. O texto-fonte foi preservado sem ser promovido a autoridade física inexistente.

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
- [ ] A >12 blocos ocorre fallback 2D; ao voltar, mesh 3D retorna sem flicker persistente.
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
- CurseForge oficial: client-only, 3D second layer, transparency, player heads e fallback 2D >12 blocos.
- CurseForge 1.11.2: delta exato e known issue de 26.2 não aplicável.
- Epic Fight JAR físico: mixin de compat `skinlayers3d` confirma integration surface.
- **Limite:** nenhuma inspeção visual/runtime foi executada nesta auditoria; qualidade de composição depende do shader, skin e modelos efetivamente usados.

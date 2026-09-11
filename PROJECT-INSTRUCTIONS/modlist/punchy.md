# Punchy

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8150a6d1dc0993d068f6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `punchy-2.7e-neoforge-1.21.1.jar`, mod id `punchy`, runtime `2.7e`, mixins `punchy.mixins.json`, `punchy.mixins.modefite.json` e `punchy.compat.mixins.json`; `punchy_epicfight_neoforge.jar` 1.0.0 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Punchy 2.7e e Punchy Epic Fight Compat 1.0.0 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Punchy
- **Arquivo JAR:** `punchy-2.7e-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 2.7e
- **Estado no pack:** Instalado — Dossiê completo
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

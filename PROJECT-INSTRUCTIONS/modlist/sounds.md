# Sounds

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812ba776cb75e55b1cf7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sounds-2.4.22+lts+1.21.1-neoforge.jar`, mod id `sounds`, runtime `2.4.22+lts`; Sound Physics Aeronautics 1.4.0.1 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sounds 2.4.22+lts e Sound Physics Aeronautics 1.4.0.1 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sounds
- **Arquivo JAR:** `sounds-2.4.22+lts+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 2.4.22+lts
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Overhaul client-side de conteúdo sonoro com mais de 170 efeitos para UI, chat, ações e blocos, altamente configurável e extensível por resource packs/data definitions para itens, screens, blocks e custom sound events.
- **Dependências:** Client-only NeoForge 1.21.1. Stack físico associado contém MRU 1.0.33+1.21.1 e YetAnotherConfigLib 3.8.2+1.21.1, usados no ecossistema/configuração do projeto; nenhuma dependência server-side é necessária para a função publicada.
- **Sobreposição:** Complementa Sound Physics Aeronautics: Sounds define/adiciona cues e eventos; Sound Physics Aeronautics processa ambiente, oclusão e propagação. Sobreposição real deve ser avaliada apenas com outros mods/resource packs que disparem/substituam os mesmos eventos sonoros.
- **Compatibilidade/Riscos:** Client-only sound-content layer. Riscos: duplicate cues com outros sound-event mods/resource packs, tag/resource reload stale, UI/action trigger duplication, config/resource override drift e volume/pitch excessivos. Não substitui Sound Physics Aeronautics, que controla propagação/oclusão. 2.4.22 corrige tags sendo carregadas cedo demais.
- **Observações:** JAR físico `sounds-2.4.22+lts+1.21.1-neoforge.jar`, mod id `sounds`, runtime 2.4.22+lts, Release. Changelog exato da build: correção de tags carregadas cedo demais. Não requer instalação no servidor segundo o projeto oficial.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sounds 2.4.22+lts File ID 7325254 + repositório/documentação oficial IMB11 Sounds + stack físico MRU/YACL.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sound/files/7325254
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sounds 2.4.22+lts reconstruído: 170+ SFX, UI/chat/actions/block audio, config, resource-pack dynamic definitions, tags/custom events, client-only lifecycle, exact 2.4.22 tag-load fix, Sound Physics boundary, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sounds-2.4.22+lts+1.21.1-neoforge.jar`, mod id `sounds`, versão `2.4.22+lts`, NeoForge 1.21.1. Sounds é um **overhaul client-side de conteúdo sonoro**; adiciona e substitui cues/eventos, mas não é o provider de propagação/oclusão acústica.

## 1. Identidade, versão e ambiente
- **Mod:** Sounds.
- **JAR:** `sounds-2.4.22+lts+1.21.1-neoforge.jar`.
- **Mod id:** `sounds`.
- **Versão:** `2.4.22+lts`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release/LTS.
- **Ambiente:** **Client-only** segundo o projeto oficial.
- **Decisão:** Sem decisão; preservada.

## 2. Papel funcional
O projeto adiciona uma grande biblioteca de novos efeitos sonoros e melhora cues existentes que considera insuficientes. O README oficial anuncia **mais de 170 efeitos**, abrangendo UI, chat, ações in-game e blocos.

A função é enriquecer feedback sonoro, não alterar gameplay state ou física do mundo.

## 3. Authority e ownership
- **Sounds:** escolha/disparo/configuração dos cues que implementa e suas definições dinâmicas.
- **Minecraft/mod provider do evento:** ação lógica que ocorreu.
- **Sound Physics Aeronautics:** processamento acústico posterior, como oclusão, ambiente e propagação.
- **Resource pack:** pode substituir assets/definitions conforme prioridade.

Um som tocado não deve ser tratado como prova de que a ação server-side ocorreu se o evento foi apenas client UI feedback.

## 4. UI sounds
Sounds adiciona feedback para diversas interações de interface. Isso inclui categorias de menu/inventory/chat e outras actions client-side do jogo.

Custom screens de mods são regression surface porque hooks genéricos podem tocar som em contextos inesperados ou duplicar feedback que a própria screen já fornece.

## 5. Chat e mensagens
O catálogo oficial inclui efeitos relacionados a chat/messages. Esses cues são apresentação local.

Mute/filter/chat mods podem alterar quando uma mensagem é exibida; Sounds não deve reconstruir state de chat nem tornar um evento oculto visível apenas para tocar áudio.

## 6. In-game actions
O projeto adiciona cues para ações do jogador e eventos de interação. Trigger deve ocorrer uma vez por ação lógica local observada.

Client prediction + confirmação do servidor podem ser uma superfície de double cue em ações que passam por ambos os paths; isso precisa ser validado por evento real.

## 7. Block sounds
Sounds suporta customização dinâmica de sons ligados a blocos. A documentação de resource packs expõe definitions específicas de block sounds.

Definições podem controlar eventos como:
- break;
- fall;
- hit;
- place;
- step.

Isso altera o asset/cue apresentado, não hardness, drop, collision ou block state.

## 8. Dynamic item sounds
Resource packs podem definir comportamento de itens sob paths do projeto como `assets/<namespace>/sounds/items/*.json`.

As definitions podem usar item keys/tags e escolher sound events, além de pitch/volume opcionais conforme schema documentado.

Tags permitem cobrir famílias de items sem hardcode item por item.

## 9. Dynamic screen sounds
O projeto documenta definitions para **HandledScreens** em paths como `assets/<namespace>/sounds/screens/*.json`.

Essas regras permitem estender feedback a GUIs modded sem alterar código do provider. Screen/resource ID incorreto deve falhar sem comprometer a UI inteira.

## 10. Custom block definitions
Definitions de block sound podem ser adicionadas por resource pack e associadas a blocks/keys/tags. O schema permite habilitar/desabilitar regras e configurar pitch/volume.

Um resource pack de maior prioridade pode substituir ou desativar uma definition. Portanto resource-pack order faz parte do lifecycle real.

## 11. Custom sound events
Sounds suporta **custom sound events** usados nas definitions e na configuração. O evento precisa existir/resolver no resource state atual.

Missing event/resource deve degradar como falha de apresentação, não cancelar a ação lógica que originou o som.

## 12. Configuração
O repositório oficial documenta configuração em `config/sounds/` e uma configuration screen.

O pack contém fisicamente:
- MRU `1.0.33+1.21.1-neoforge`;
- YetAnotherConfigLib `3.8.2+1.21.1-neoforge`.

Eles fazem parte da infraestrutura/configuração disponível no runtime, mas esta ficha não inventa versões mínimas/relações obrigatórias não explicitamente verificadas para a build.

## 13. Pitch e volume
Definitions/config podem ajustar **pitch** e **volume**. Esses valores são presentation settings e não alteram alcance físico do evento por si só.

Volume exagerado somado a long-range/physics mods deve ser diagnosticado separando cue amplitude de propagation range.

## 14. Tags e data/resource lifecycle
A build física 2.4.22 corrige especificamente **tags sendo carregadas cedo demais**.

Isso indica que tags fazem parte do resolution lifecycle de dynamic definitions. Regression gate: resource/tag reload precisa reconstruir mappings sem usar tags incompletas ou state da sessão anterior.

## 15. Delta exato 2.4.22+lts
O changelog publicado para a release física contém um fix direto:
- **Fixed tags being loaded too early.**

Não atribuir outras features como novidade exclusiva da 2.4.22; o catálogo amplo vem da arquitetura atual do projeto.

## 16. Client-only boundary
O projeto declara que o mod é **fully client-side** e não precisa ser instalado no servidor.

Consequências:
- servidor não deve depender de Sounds para gameplay;
- dois clientes podem usar configs/resource packs diferentes;
- diferenças de áudio entre jogadores podem ser legítimas;
- remover Sounds do cliente não deve remover blocks/items/server data.

## 17. Sound Physics Aeronautics
No pack, Sound Physics Aeronautics 1.4.0.1 é o provider de propagation/acoustic processing. Sounds e SPA atuam em camadas diferentes:
1. Sounds escolhe/adiciona o cue/evento;
2. SPA pode processar como esse som é ouvido no ambiente.

Isso é complementar. Som duplicado deve ser investigado no event trigger/resource definitions, não tratado automaticamente como conflito com physics acoustics.

## 18. Resource packs e addons do pack
O catálogo contém customizações relacionadas a Sounds, como conteúdo específico para outros mods. Essas extensões devem permanecer data/resource driven e subordinadas à prioridade de packs.

A presença de uma definition para um mod não altera o ownership funcional desse mod; apenas adiciona feedback sonoro.

## 19. Performance
Mais eventos e assets podem aumentar trabalho de áudio/client, especialmente em cenas com muitas ações repetitivas. Entretanto a presença do mod não prova gargalo.

Se houver stutter/audio saturation, medir event rate, source count e interação com sound physics antes de remover conteúdo aleatoriamente.

## 20. Lifecycle
Validar:
- client boot;
- world join/disconnect;
- resource-pack enable/disable;
- resource reload;
- tag reload;
- config edit/restart;
- language/UI changes;
- custom screens;
- item/block registry de mods carregados;
- troca entre worlds/servers.

Caches de definitions não devem sobreviver indevidamente a resource/tag changes.

## 21. Riscos técnicos
1. **Duplicate cue:** dois hooks/resource definitions disparam o mesmo feedback.
2. **Tag-load regression:** mapping usa tags incompletas — fix direto 2.4.22.
3. **Stale resource mapping:** pack removido continua afetando sons.
4. **Screen over-trigger:** UI modded gera sound em interação não pretendida.
5. **Missing custom event:** definition referencia sound inexistente.
6. **Pack priority conflict:** duas definitions substituem a mesma key/tag.
7. **Volume/pitch extremes:** config torna cue intrusivo ou mascarado.
8. **Acoustic misdiagnosis:** problema de propagation é atribuído ao mod de cues ou vice-versa.

## 22. Matriz de testes
- [ ] Cliente inicia com Sounds 2.4.22+lts.
- [ ] Dedicated server funciona sem Sounds instalado server-side.
- [ ] UI/chat/action/block cues principais tocam uma única vez.
- [ ] Custom block sound por key funciona.
- [ ] Custom block sound por tag resolve após reload — regression 2.4.22.
- [ ] Dynamic item definition aplica somente aos itens esperados.
- [ ] Dynamic screen definition não quebra GUI modded.
- [ ] Resource pack superior consegue substituir/desativar definition.
- [ ] Custom sound event missing degrada sem crash/gameplay failure.
- [ ] Config pitch/volume persiste após restart.
- [ ] Resource/tag reload invalida cache antigo.
- [ ] Sounds + Sound Physics Aeronautics produzem um cue com propagation correta, sem duplicação de provider.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 23. Evidências e limites
- Modlist física de 11/09/2026: Sounds 2.4.22+lts, MRU 1.0.33, YACL 3.8.2 e Sound Physics Aeronautics 1.4.0.1.
- CurseForge oficial File ID 7325254: release NeoForge 1.21.1 e fix exato de tag loading.
- Repositório oficial IMB11-Mods/Sounds: 170+ SFX, client-only, `config/sounds/`, resource-pack extensibility.
- Documentação oficial: dynamic item/screen/block sounds, tags, custom events, pitch/volume.
- **Limite:** config local e resource-pack definitions efetivamente ativos não foram inventariados nesta auditoria; runtime/audio tests continuam pendentes.

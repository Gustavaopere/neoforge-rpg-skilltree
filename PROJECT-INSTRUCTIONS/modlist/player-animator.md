# Player Animator

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d5b2cfd33f09d59452
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `player-animation-lib-forge-2.0.4+1.21.1.jar`, mod id `playeranimator`, runtime `2.0.4+1.21.1`, mixin `playerAnimator-common.mixins.json`; consumer Create: Gunsmithing 1.4.9 confirmado
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Player Animator 2.0.4 e Create: Gunsmithing 1.4.9 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Player Animator
- **Arquivo JAR:** `player-animation-lib-forge-2.0.4+1.21.1.jar`
- **Versão 1.21.1:** 2.0.4+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, Visual
- **Função:** API/biblioteca de animações do jogador usada por mods para reproduzir animações customizadas e controlar poses/partes do corpo.
- **Dependências:** NeoForge 1.21.1. Consumer causal confirmado: Create: Gunsmithing 1.4.9 exige playerAnimator. Cópias JarJar internas em outros hosts não substituem o JAR top-level.
- **Sobreposição:** Player Animation Library é sucessor funcional e lê formato legado, mas não substitui automaticamente dependências ao mod id/API `playeranimator`.
- **Compatibilidade/Riscos:** Build Beta. Riscos: first-person/item animation regressions, Epic Fight/armature arbitration, pose cleanup, PAL confusion e JarJar ambiguity. PAL é sucessor funcional, mas outro mod id/API.
- **Observações:** Runtime 2.0.4+1.21.1, file ID 7389814, Beta de 28/12/2025. Changelog exato: fixes de first person e item animations.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Player Animator 2.0.4 + relação oficial já auditada de Create: Gunsmithing 1.4.9.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/playeranimator
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Player Animator 2.0.4 reconstruído e reclassificado como Dependência: Create: Gunsmithing consumer, Beta boundary, first-person/item fixes, PAL/JarJar distinction, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-08-26 — `Tirar` antigo corrigido posteriormente porque cópia JarJar não substitui o top-level. 2026-09-10 — reclassificado de Sem decisão para Dependência após confirmação de Create: Gunsmithing 1.4.9 como consumer que exige playerAnimator.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `player-animation-lib-forge-2.0.4+1.21.1.jar`, mod id `playeranimator`, versão `2.0.4+1.21.1`, NeoForge 1.21.1. Esta é a API Player Animator/KosmX clássica, em build **Beta** para 1.21.1. Embora Player Animation Library seja seu sucessor funcional, o pack possui consumer atual que exige `playerAnimator`: **Create: Gunsmithing 1.4.9**. A decisão é, portanto, **Dependência**.

## 1. Identidade e papel
- **Mod:** Player Animator.
- **JAR físico:** `player-animation-lib-forge-2.0.4+1.21.1.jar`.
- **Mod id:** `playeranimator`.
- **Runtime:** `2.0.4+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor/projeto:** KosmX / PlayerAnimator.
- **Licença:** MIT.
- **Canal da build:** Beta.
- **Papel:** API/biblioteca para mods aplicarem animações keyframed ao player e controlarem poses/partes do modelo.
- **Decisão:** Dependência.

## 2. Consumer causal: Create: Gunsmithing
O pack físico contém `create-gunsmithing-1.21.1-1.4.9.jar`.

A relação oficial desse consumer exige:
- NTGL;
- Create;
- **playerAnimator**.

Isso resolve a classificação: o JAR top-level não é redundante enquanto Create: Gunsmithing 1.4.9 estiver presente e declarar/usar essa API.

Remover Player Animator isoladamente pode impedir load ou quebrar animações/código do consumer.

## 3. Biblioteca, não pacote de animações
O projeto oficial descreve Player Animator como **library**. Ele permite que consumers reproduzam/controlam animações; sozinho não deve ser interpretado como provider de um conjunto específico de movimentos de gameplay.

Ownership:
- Player Animator → framework/camadas/transforms;
- consumer → assets de animação, gatilhos e gameplay que pedem a pose;
- renderer/model mods → apresentação final adicional.

## 4. Keyframes e pose do player
O framework permite animação por keyframes e transformação de partes do modelo do jogador. Isso cria interseções com:
- primeira/terceira pessoa;
- itens nas mãos;
- arm pose;
- camera/player model mods;
- Epic Fight e outras armatures;
- mods que tentam tocar as mesmas partes do corpo simultaneamente.

A prioridade/camada concreta deve ser documentada pelo consumer; não presumir arbitration universal.

## 5. Build 2.0.4 para 1.21.1
A build exata `2.0.4+1.21.1` é Beta oficial NeoForge, file ID `7389814`, publicada em 28/12/2025.

Changelog específico:
- correções relacionadas a **first person**;
- correções de **item animations**.

Esses dois pontos são regression gates diretos para um pack com First Person Model, armas e diversas camadas de animação.

## 6. Player Animation Library não substitui o mod id
Player Animation Library (PAL) é sucessor funcional e consegue trabalhar com o formato legado, mas é outro mod/API (`player_animation_library`).

Compatibilidade de formato não satisfaz automaticamente:
- manifest que exige `playeranimator`;
- imports/classes da API antiga;
- serviços/entrypoints associados ao mod antigo.

Logo manter PAL instalado não elimina a necessidade do Player Animator enquanto consumers explícitos permanecerem.

## 7. JarJar versus top-level
O catálogo já identificou cópias internas de Player Animator em outros hosts, como Climbable Ropes.

Uma cópia sob `META-INF/jarjar`:
- pertence ao host;
- não substitui automaticamente o JAR top-level;
- pode existir para satisfazer apenas o próprio host;
- não autoriza apagar a entrada canônica top-level que outros consumers exigem.

## 8. First-person e item animations
Como 2.0.4 corrige justamente first-person e item animation issues, testar:
- arma/item em mão durante animação customizada;
- mão principal/offhand;
- First Person Model;
- FOV/camera changes;
- equip/unequip durante animation;
- animação terminando/interrompendo sem pose presa.

## 9. Relação com Epic Fight
Epic Fight possui seu próprio sistema de armature/combat animation. Player Animator não deve ser tratado como extensão nativa do Epic Fight.

Quando um consumer usa Player Animator durante battle mode, validar:
- prioridade da pose;
- recovery/cancel;
- attacks não ficando invisíveis/desalinhados;
- mão/item coerente;
- retorno à armature Epic Fight após término.

Conflito deve ser provado por animação concreta.

## 10. Client/server e sync
Animação é majoritariamente presentation-facing, mas o evento que a dispara pode ser server-authoritative. O cliente não deve poder obter gameplay adicional apenas reproduzindo uma animation local.

Multiplayer precisa verificar que observadores veem a animação esperada quando o consumer sincroniza esse state.

## 11. Lifecycle
Testar:
- iniciar/interromper animação;
- trocar item;
- entrar em GUI;
- death/respawn;
- dimension change;
- reconnect;
- first↔third person;
- entrar/sair do battle mode;
- unload da entidade observada.

Transforms temporários precisam voltar ao estado base.

## 12. Riscos
1. **Hard consumer dependency:** Create: Gunsmithing exige Player Animator.
2. **Beta build:** 2.0.4 1.21.1 é Beta.
3. **PAL confusion:** sucessor funcional não substitui mod id/API automaticamente.
4. **First-person regression:** área explicitamente corrigida nesta build.
5. **Item animation regression:** armas/itens podem desalinhá-la.
6. **Epic Fight arbitration:** múltiplos animation systems podem competir.
7. **JarJar ambiguity:** cópia embedded confundida com substituto top-level.
8. **Pose cleanup:** interrupção/lifecycle pode deixar transform stale.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Player Animator 2.0.4 + Create: Gunsmithing 1.4.9.
- [ ] Consumer registra/carrega sem missing `playeranimator` dependency/class.
- [ ] Animação representativa do consumer aparece em third person.
- [ ] First Person Model não produz mão/corpo duplicado ou clipping grave.
- [ ] Item/offhand permanece alinhado durante animação.
- [ ] Epic Fight battle mode inicia/interrompe animação sem pose presa.
- [ ] Death/relog/dimension change limpa transforms temporários.
- [ ] Outro cliente observa a animação sincronizada quando o consumer prevê isso.
- [ ] PAL coexistindo não cria duplicate animation application.
- [ ] Cópias JarJar internas resolvem sem substituir/duplicar indevidamente o top-level.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `player-animation-lib-forge-2.0.4+1.21.1.jar`, mod id/runtime e `playerAnimator-common.mixins.json`.
- CurseForge oficial: project 658587, file ID 7389814, Beta NeoForge 1.21/1.21.1 de 28/12/2025; changelog = fixes de first person e item animations.
- Create: Gunsmithing 1.4.9 físico e relação oficial previamente auditada exigindo `playerAnimator`, sustentando `Dependência`.
- Player Animation Library presente separadamente e documentada como sucessor, não mod-id replacement automático.
- **Limite:** lista total de consumers que importam a API antiga não foi enumerada; um consumer causal confirmado já é suficiente para impedir remoção.

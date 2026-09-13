# Sable x CPM

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db818dabaff2e09e6637f5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sable-x-cpm-0.3.2+1.21.1.jar`, mod id `sablexcpm`, runtime `0.3.2+1.21.1`, mixin `sablexcpm.mixins.json`; Customizable Player Models 0.6.27a, Kotlin for Forge 5.12.0 e Sable Ragdolls 0.7.5 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sable x CPM 0.3.2+1.21.1 e as três dependências publicadas estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable x CPM
- **Arquivo JAR:** `sable-x-cpm-0.3.2+1.21.1.jar`
- **Versão 1.21.1:** 0.3.2+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual
- **Função:** Compatibilidade visual entre Customizable Player Models e Sable Ragdolls: vincula o modelo CPM do jogador ao PlayerModel de cada peça física antes do render.
- **Dependências:** Required publicado e presente: Customizable Player Models 0.6.27a, Kotlin for Forge 5.12.0 e Sable Ragdolls 0.7.5. Sable 2.0.5 é provider físico indireto do ragdoll.
- **Sobreposição:** Não substitui CPM nem Sable Ragdolls; somente corrige a boundary de render entre o modelo customizado e as peças físicas.
- **Compatibilidade/Riscos:** Funcionalmente client-side. Riscos: wrong-owner CPM binding, plugin API drift, renderer/mixin conflicts, stale model cache, fallback failure, cape/elytra vanilla mismatch e seams em partes CPM muito afastadas do bone pai.
- **Observações:** 0.3.2 é a primeira release pública. Corrige texturas CPM nas peças e inclui fallback seguro para vanilla skin. Suporte upstream a Ragdoll Corpse existe, mas o provider top-level `ragdoll_corpse` não aparece na modlist física atual.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + CurseForge oficial Sable x CPM 0.3.2 e relações/dependências oficiais.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-x-cpm
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Sable x CPM 0.3.2 reconstruído: CPM plugin binding, texture/fallback path, required stack, client-side boundary, known limits, multiplayer/cache risks e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-28

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sable-x-cpm-0.3.2+1.21.1.jar`, mod id `sablexcpm`, versão `0.3.2+1.21.1`. O addon resolve a boundary de renderização entre **Customizable Player Models (CPM)** e os ragdolls do Sable, vinculando o modelo CPM do jogador ao `PlayerModel` usado por cada peça física antes da renderização. A publicação funcional declara o mod **client-side only**, embora a ficha do arquivo no CurseForge possa exibir ambiente genérico Client & Server.

## 1. Identidade e papel
- **Mod:** Sable x CPM.
- **JAR:** `sable-x-cpm-0.3.2+1.21.1.jar`.
- **Mod id:** `sablexcpm`.
- **Versão instalada:** `0.3.2+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Mixin config físico:** `sablexcpm.mixins.json`.
- **Papel:** compatibilidade visual CPM ↔ Sable Player Ragdoll/Corpse.

## 2. Dependências publicadas e físicas
A publicação oficial lista como required:
- **Customizable Player Models 0.6.20+**;
- **Kotlin for Forge 5+**;
- **Sable: Ragdolls 0.7+**.

O pack atual contém:
- CPM **0.6.27a**;
- Kotlin for Forge **5.12.0**;
- Sable Ragdolls **0.7.5**.

Portanto as três dependências funcionais declaradas estão satisfeitas fisicamente.

## 3. Problema que a bridge resolve
Sem o addon, cada peça do ragdoll é renderizada usando um `PlayerModel` standalone que o CPM não intercepta automaticamente. O resultado é o ragdoll mostrar a skin/modelo vanilla em vez do outfit/modelo customizado do jogador.

Sable x CPM injeta a ligação CPM imediatamente antes de cada peça renderizar, de modo que o corpo físico preserve a aparência customizada.

## 4. Autoridade e ownership
- **CPM:** modelo customizado, texturas, outfit e plugin API.
- **Sable Ragdolls:** criação/state das peças físicas, pose e lifecycle do ragdoll.
- **Sable:** physics/sublevel transform das peças.
- **Sable x CPM:** bridge de binding/render entre o jogador/modelo CPM e o PlayerModel usado pelo ragdoll.

O addon não deve manter um segundo estado funcional de jogador, inventário ou física.

## 5. Binding via CPM plugin API
A documentação oficial afirma que o addon usa a **plugin API do CPM** para associar o modelo do jogador morto ao PlayerModel usado por cada peça física.

Esse binding ocorre no caminho de render. Portanto qualquer falha deve degradar visualmente, não corromper state físico ou impedir o servidor de continuar simulando o ragdoll.

## 6. Fallback seguro
A release 0.3.2 adiciona/declara **fallback para skin vanilla** quando o binding CPM falha, em vez de crashar ou deixar o renderer preso.

Regression gate: provocar um cenário de modelo indisponível/inválido em ambiente controlado deve resultar em fallback visual e não em crash loop.

## 7. Texturas CPM
A release também registra correção/uso das **texturas CPM corretas nas peças do ragdoll**, em vez da skin vanilla.

Testar players com:
- modelo padrão CPM;
- textura customizada;
- partes adicionadas/removidas;
- dois modelos distintos simultaneamente.

Cada ragdoll precisa continuar associado ao perfil/modelo do owner correto.

## 8. Corpse e dolls
A publicação informa que o addon também cobre **Sable: Ragdoll Corpse**, que reutiliza o mesmo renderer, e a release menciona ragdolls, corpses e dolls.

No snapshot físico atual não há JAR top-level com mod id `ragdoll_corpse`; portanto essa parte da compatibilidade deve ser tratada como **capacidade upstream**, não como integração top-level confirmada neste momento. O escopo Player Ragdoll 0.7.5 está confirmado.

## 9. Limites conhecidos
O projeto documenta dois limites:
- capes e elytra do ragdoll continuam pelo caminho vanilla, fora do player model CPM;
- partes CPM muito afastadas do bone pai podem parecer separadas nas juntas, porque cada peça do ragdoll é simulada separadamente.

Esses limites são esperados e devem ser distinguidos de regressões como textura errada ou model binding perdido.

## 10. Kotlin e mixins
O projeto informa que as sources principais são Kotlin, enquanto as duas mixin classes permanecem Java porque o processor de mixins mescla bytecode no alvo e metadata Kotlin não é segura nesse contexto.

No runtime, isso torna Kotlin for Forge parte concreta da cadeia de carregamento do addon.

## 11. Client / Server
A descrição funcional oficial classifica o addon como **client-side only**. Sua responsabilidade é render/binding de modelo; não deve alterar física, inventário, ragdoll state ou gameplay do servidor.

Se o arquivo estiver instalado também no servidor, isso não transforma o binding visual em lógica autoritativa. Dedicated server deve, no mínimo, não carregar classes client-only por caminho incorreto.

## 12. Lifecycle
Validar:
- player entra com CPM model já carregado;
- ragdoll inicia e termina;
- death/respawn;
- model reload/change;
- relog/reconnect;
- outro player entra em render distance depois do ragdoll existir;
- skin/profile cache refresh;
- resource reload;
- fallback quando CPM binding falha.

O addon não deve deixar binding do jogador A aplicado às peças do jogador B.

## 13. Integrações concretas no pack
- **CPM 0.6.27a:** provider visual.
- **Sable Ragdolls 0.7.5:** provider do player ragdoll.
- **Sable 2.0.5:** physics/sublevel provider indireto.
- **Sable Ragdolls Patch 1.9:** patch adicional do mesmo domínio de render/interação; precisa coexistir sem ordem/mixin conflict.
- **Fresh Animations/player resource packs:** podem alterar modelo/render, mas esta ficha não atribui compatibilidade específica além do que o patch separado cobre.

## 14. Multiplayer
Dois jogadores com CPM models diferentes precisam produzir ragdolls visualmente distintos para todos os observadores. O renderer não pode reutilizar o último modelo bound globalmente.

Player desconectando enquanto o ragdoll está visível precisa liberar/reassociar caches sem contaminar outro UUID/perfil.

## 15. Riscos técnicos
1. **Wrong-owner binding:** modelo do jogador A aplicado ao ragdoll B.
2. **CPM API drift:** plugin binding muda em atualização.
3. **Renderer/mixin conflict:** Ragdolls Patch ou outro player-render mod intercepta o mesmo caminho.
4. **Fallback failure:** erro CPM derruba renderer em vez de cair para vanilla.
5. **Detached parts:** limite conhecido com partes muito afastadas do bone pai.
6. **Cape/elytra mismatch:** seguem caminho vanilla fora do CPM binding.
7. **Cache stale:** modelo antigo continua após mudança/relog.
8. **Client-class leakage:** dedicated server tenta carregar código estritamente visual.

## 16. Matriz de testes
- [ ] Cliente inicia com CPM 0.6.27a + KFF 5.12.0 + Sable Ragdolls 0.7.5 + Sable x CPM 0.3.2.
- [ ] Dedicated server inicia sem classloading client-only indevido.
- [ ] Rag doll de jogador com CPM outfit usa modelo/textura CPM corretos.
- [ ] Player vanilla/CPM binding ausente cai para vanilla sem crash.
- [ ] Dois players com CPM distintos não trocam modelos entre si.
- [ ] Death/respawn/relog não deixa modelo stale.
- [ ] Mudança/reload do modelo CPM atualiza o ragdoll subsequente corretamente.
- [ ] Partes CPM afastadas exibem apenas o limite conhecido, sem corrupção de pose global.
- [ ] Cape/elytra seguem o comportamento vanilla documentado.
- [ ] Sable Ragdolls Patch 1.9 coexiste sem mixin/render conflict.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- Modlist física canônica de 10/09/2026: Sable x CPM 0.3.2, CPM 0.6.27a, KFF 5.12.0 e Sable Ragdolls 0.7.5.
- CurseForge oficial 0.3.2: first public release, CPM textures, fallback vanilla, sources Kotlin/mixins Java.
- Descrição oficial: mecanismo de binding via CPM plugin API e limites de cape/elytra/part seams.
- Relações oficiais: CPM, Kotlin for Forge e Sable Ragdolls como required dependencies.
- **Limite:** suporte a Ragdoll Corpse é upstream, mas o provider top-level `ragdoll_corpse` não está confirmado na modlist física atual.

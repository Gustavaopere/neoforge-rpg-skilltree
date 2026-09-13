# Player Animation Library

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81129352d646100d567c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar`, mod id `player_animation_library`, runtime `1.1.6+mc.1.21.1`, mixin `player_animation_library.mixins.json`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Player Animation Library 1.1.6 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Player Animation Library
- **Arquivo JAR:** `PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar`
- **Versão 1.21.1:** 1.1.6+mc.1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Visual
- **Função:** Biblioteca/API de animação do jogador separada do ecossistema Player Animator/KosmX. Fornece infraestrutura para mods aplicarem poses e animações customizadas ao modelo do jogador, combinarem camadas de animação e preservarem interoperabilidade entre consumidores que dependem especificamente desta API.
- **Dependências:** NeoForge 1.21.1. Nenhum consumer top-level explícito desta API foi comprovado no cruzamento atual; dependency scan de manifests é necessário antes de remoção.
- **Sobreposição:** Sucessor funcional do PlayerAnimator e compatível com formato legado, mas mod id/API são distintos. Player Animator segue necessário por consumer confirmado; PAL permanece Sem decisão até consumer próprio ser mapeado.
- **Compatibilidade/Riscos:** Release NeoForge 1.21.1. Riscos: consumer não mapeado, false redundancy com Player Animator, animation priority, MoLang/speed regression, Bezier behavior e coexistência com Epic Fight/render stack.
- **Observações:** Runtime 1.1.6+mc.1.21.1. Delta exato: backport de Bezier da linha 26.1+ e fix de crash em algumas MoLang queries combinadas com speed modifier.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + Modrinth/CurseForge oficiais Player Animation Library 1.1.6 + cruzamento atual do catálogo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/player-animation-library
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Player Animation Library 1.1.6 reconstruída: successor boundary, priorities/layers, legacy format, Bezier backport, MoLang-speed fix, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar`, mod id `player_animation_library`, versão `1.1.6+mc.1.21.1`, NeoForge 1.21.1. Player Animation Library (PAL) é o sucessor moderno do ecossistema PlayerAnimator, com foco em animações de player que coexistam entre consumers. Nenhum consumer top-level explícito desta API foi comprovado no cruzamento atual, então a decisão permanece **Sem decisão**.

## 1. Identidade e papel
- **Mod:** Player Animation Library.
- **JAR físico:** `PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar`.
- **Mod id:** `player_animation_library`.
- **Runtime:** `1.1.6+mc.1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** MIT.
- **Papel:** API de animação de player com suporte a múltiplos consumers, prioridades/camadas e compatibilidade com o formato antigo do PlayerAnimator.
- **Decisão:** Sem decisão.

## 2. Sucessor do PlayerAnimator
A documentação oficial define PAL como sucessor do PlayerAnimator/KosmX. Isso significa evolução funcional da ideia/API de animação, mas não substituição automática do JAR antigo na modlist.

O pack possui ambos porque:
- `playeranimator` ainda tem consumer causal confirmado;
- `player_animation_library` é outro mod id/API;
- compatibilidade de formato não converte manifests antigos automaticamente.

## 3. Prioridade e coexistência
O objetivo publicado da library é permitir que mods animem o jogador **sem entrar em conflito** com outros mods. Para isso, a arquitetura inclui composição/prioridade de animações.

Isso reduz conflitos, mas não garante que qualquer combinação de armature/render seja perfeita. Consumers ainda precisam definir prioridades e cancelar layers corretamente.

## 4. Formato legado
PAL oferece suporte ao formato de animação antigo do PlayerAnimator. Essa compatibilidade é útil para migrar assets, mas há três camadas separadas:
1. formato do asset;
2. API/mod id que o consumer chama;
3. runtime que resolve a animação.

Compatibilidade no item 1 não prova compatibilidade automática nos itens 2 e 3.

## 5. Release 1.1.6
A build `1.1.6+1.21.1-NeoForge` é **Release** oficial para Minecraft 1.21.1.

Changelog exato:
- backport da funcionalidade **Bezier** da linha 26.1+;
- fix de crash quando certas **MoLang queries** eram combinadas com **speed modifier**.

Esses pontos são regression gates concretos da build instalada.

## 6. Bezier
O backport Bezier amplia a capacidade de interpolação/curvas em animações suportadas. Isso pode afetar suavidade e timing visual.

Não atribuir uma curva específica a um consumer sem abrir o asset dele. Testar apenas animações que efetivamente usam essa funcionalidade quando forem identificadas.

## 7. MoLang e speed modifier
A 1.1.6 corrige crash envolvendo combinação de algumas MoLang queries com speed modifier.

Regression gate:
- animação parametrizada por velocidade;
- mudanças rápidas de movement speed;
- query inválida/valor extremo;
- interrupção/restart da layer.

O fix é da library; a expressão MoLang concreta pertence ao consumer/asset.

## 8. Consumer mapping atual
O cruzamento das propriedades de dependência/compatibilidade no catálogo atual não revelou outro top-level declarando Player Animation Library explicitamente.

Isso não prova ausência de consumer — manifests podem ainda não estar refletidos no texto do catálogo.

Portanto:
- não classificar como `Dependência` sem causa confirmada;
- não classificar como `Tirar` por parecer redundante ao Player Animator;
- manter `Sem decisão` até dependency scan de manifests/source.

## 9. Relação com Epic Fight e render stack
PAL toca poses/transforms de player; Epic Fight, CPM, First Person Model e outros sistemas também podem tocar apresentação.

Testar:
- layers simultâneas;
- prioridade/cancelamento;
- first/third person;
- item held transforms;
- retorno à pose base;
- battle mode.

Conflito deve ser documentado por consumer e animação concretos.

## 10. Client/server e sync
A library é Client & Server. Animação continua sendo representação; gameplay deve permanecer authority do consumer/server.

Se um consumer sincroniza animation state, validar que:
- observadores recebem estado correto;
- reconexão não reaplica layer antiga;
- client sem evento lógico não obtém efeito de gameplay apenas por reproduzir asset.

## 11. Lifecycle
Superfícies críticas:
- iniciar/parar animação;
- mudança de velocidade;
- equip/unequip;
- death/respawn;
- dimension change;
- reconnect;
- unload da entidade;
- resource reload quando assets forem data/resource driven.

Layers temporárias precisam ser removidas sem pose stale.

## 12. Riscos
1. **Consumer não mapeado:** necessidade ainda pode existir.
2. **False redundancy:** Player Animator antigo não torna PAL dispensável automaticamente.
3. **Animation priority:** consumers podem escolher prioridades ruins.
4. **MoLang/speed:** regressão explicitamente corrigida em 1.1.6.
5. **Bezier asset behavior:** curva/timing pode mudar entre versões.
6. **Epic Fight/render coexistence:** múltiplas transforms podem competir.
7. **Lifecycle cleanup:** layer fica ativa após consumer terminar.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com PAL 1.1.6.
- [ ] Dependency scan identifica consumers reais de `player_animation_library`.
- [ ] Animação simples de consumer inicia/encerra corretamente.
- [ ] Duas layers com prioridades diferentes resolvem de forma previsível.
- [ ] Asset legado compatível carrega sem exigir PlayerAnimator por formato apenas.
- [ ] MoLang + speed modifier representativo não reproduz crash corrigido.
- [ ] Bezier animation, se houver consumer, interpola sem jitter evidente.
- [ ] Epic Fight/CPM/First Person stack não deixa pose stale.
- [ ] Death/relog/dimension change limpam animation state.
- [ ] Coexistência com Player Animator não aplica a mesma animação duas vezes.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `PlayerAnimationLibNeoforge-1.1.6+mc.1.21.1.jar`, mod id/runtime e `player_animation_library.mixins.json`.
- Modrinth oficial: Release 1.1.6 NeoForge 1.21.1, Client & Server.
- Changelog 1.1.6: Bezier backport e fix MoLang query + speed modifier.
- Documentação oficial: sucessor do PlayerAnimator, compatibilidade com formato antigo e arquitetura para reduzir conflitos entre animações.
- Cruzamento do catálogo: nenhum consumer explícito confirmado neste passe.
- **Limite:** sem dependency scan físico de todos os manifests, não declarar PAL órfã nem indispensável.

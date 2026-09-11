# SmartBrainLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db810aa4f0c2794a1efe07
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `SmartBrainLib-neoforge-1.21.1-1.16.11.jar`, mod id `smartbrainlib`, runtime `1.16.11`; nenhum consumer causal inequívoco foi provado pela metadata física usada neste lote
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, SmartBrainLib 1.16.11 está presente, mas a metadata física aqui disponível não prova um consumer causal. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** SmartBrainLib
- **Arquivo JAR:** `SmartBrainLib-neoforge-1.21.1-1.16.11.jar`
- **Versão 1.21.1:** 1.16.11
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca de IA que simplifica o Brain system do Minecraft com sensors, memories, behaviours e grupos de activities/core-idle-fight tasks para mobs consumidores.
- **Dependências:** NeoForge 1.21.1. Necessidade depende de mods consumidores. Nenhum consumer físico foi confirmado causalmente nesta auditoria; por regra permanece `Sem decisão`, apesar de a remoção exigir dependency scan/log test antes.
- **Sobreposição:** Não substitui mods de mobs nem otimizações genéricas de AI; fornece framework/API para consumers. Benefício/necessidade só existe quando algum mod utiliza a biblioteca.
- **Compatibilidade/Riscos:** Library de AI client&server sem gameplay próprio. Principal risco é remover/update sem mapear consumer causal, além de API drift, memory/task duplication, sensor scan cost e entity/provider incompatibilities. Nenhum consumer atual foi comprovado causalmente no Notion/modlist metadata deste lote.
- **Observações:** 1.16.11 é Release NeoForge 1.21.1 (File ID 7055149). O changelog específico aponta para commits da branch 1.21, sem resumo de delta. A futura 2.0 renomeia BrainActivityGroup→ActivityBuilder, evidência de que essa API 2.0 não deve ser aplicada retroativamente à build instalada.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial SmartBrainLib 1.16.11 + descrição oficial e exemplos públicos da branch 1.21; changelog 2.0 usado somente como boundary de API futura.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/smartbrainlib/files/7055149
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — SmartBrainLib 1.16.11 reconstruído: Brain-based AI library, sensors/memories/behaviours/activity groups, server AI lifecycle, API lineage, consumer uncertainty, risks and tests.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `SmartBrainLib-neoforge-1.21.1-1.16.11.jar`, mod id `smartbrainlib`, versão `1.16.11`, NeoForge 1.21.1. É uma **library de IA**: não adiciona gameplay por si só e só é necessária quando um mod consumidor usa sua API.

## 1. Identidade e papel
- **Mod:** SmartBrainLib.
- **JAR:** `SmartBrainLib-neoforge-1.21.1-1.16.11.jar`.
- **Mod id:** `smartbrainlib`.
- **Versão:** `1.16.11`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server segundo a distribuição atual.
- **Papel:** abstrair/simplificar o Brain AI system do Minecraft para mobs consumidores.

## 2. Biblioteca, não conteúdo
A página oficial afirma explicitamente que SmartBrainLib **não faz nada sozinho** para o jogador. Ele existe para developers implementarem AI complexa de forma mais simples e reutilizável.

Consequência curatorial: a presença não prova necessidade; a ausência também não pode ser decidida sem mapear consumers.

## 3. Por que existe
O projeto caracteriza o Brain vanilla como poderoso, porém complexo e pouco ergonômico. SmartBrainLib fornece uma camada de abstração para usar memories, sensors, activities e behaviours sem repetir grande volume de boilerplate.

O objetivo é arquitetura de AI, não substituir pathfinding/tick de todos os mobs automaticamente.

## 4. Brain state
Mobs consumidores usam o `Brain`/memory system como state compartilhado da IA. Sensors observam o mundo e escrevem informações; behaviours consultam memories/conditions e executam ações.

State de Brain continua pertencendo à entity consumidora e deve ser reconstruído/persistido conforme o contrato do Minecraft/provider.

## 5. Sensors
A API 1.21 da linha 1.16 usa `ExtendedSensor` e sensors custom/vanilla para coletar contexto como nearby players/entities, hurt state, target reachability e outras observações.

Sensor scan rate pode ser configurável por implementação consumidora. Scan excessivo em muitos mobs pode elevar custo server tick.

## 6. Memories
Memories conectam sensors e behaviours: attack target, walk target, hurt-by state, cooldowns ou memory types custom podem controlar decisão sem dependência direta entre cada task.

Expiração/forgettable memory deve acontecer uma única vez e no tick autoritativo; memory stale pode prender o mob em activity incorreta.

## 7. Behaviours
SmartBrainLib fornece behaviours reutilizáveis para look, movement, target selection, attacks, idle e utility logic, além de permitir behaviours customizados.

O consumer decide quais behaviours usar, suas conditions/cooldowns e prioridade; SmartBrainLib não define uma AI universal para todo mob.

## 8. Activity groups na 1.16.x
A API da branch 1.21 usada pela geração 1.16.x organiza behaviours em `BrainActivityGroup`, incluindo patterns como:
- core tasks;
- idle tasks;
- fight tasks.

Exemplos públicos usam groups para LookAtTarget/WalkToTarget em core, targeting/random movement em idle e attack/target-maintenance em fight.

## 9. Core / idle / fight boundary
**Core tasks** tendem a manter comportamento transversal como olhar/mover/flutuar. **Idle tasks** selecionam targets ou comportamento não-combat. **Fight tasks** gerenciam perseguição, invalidation de target e ataques.

Isso é um padrão da API, não obrigação para cada consumer. Um mod pode estruturar seu mob de forma diferente.

## 10. Behaviour composition
A library inclui patterns de composição como escolher o primeiro behaviour aplicável ou selecionar aleatoriamente entre behaviours. Isso permite state machines complexas sem hardcode monolítico.

Priority/selection incorreta em consumer pode produzir starvation: um behaviour sempre aplicável impede outro de iniciar.

## 11. Cooldowns e timed memories
Consumers podem usar memories temporárias e cooldowns para impedir attack/action por vários ticks. Esses timers precisam ser server-authoritative e independentes de FPS/client prediction.

Reload/relog não deve transformar cooldown curto em permanente nem reaplicar ação já liquidada.

## 12. Pathfinding/targeting
Behaviours podem definir walk targets, flee targets, attack target, random position ou reação a target unreachable. SmartBrainLib orquestra state/behaviour; o path navigator da entity/Minecraft continua responsável por executar path válido.

Path failure deve resultar em fallback/invalid target conforme consumer, não loop infinito de recomputação.

## 13. Public 1.21 example architecture
Exemplo público do ecossistema do autor em branch 1.21 mostra `BrainActivityGroup`, `ExtendedSensor`, `HurtBySensor`, target sensors, `LookAtTarget`, `WalkOrRunToWalkTarget`, `SetWalkTargetToAttackTarget`, `InvalidateAttackTarget`, `AnimatableMeleeAttack` e custom behaviours.

Esse exemplo demonstra a **forma da API 1.16.x**; o mod de exemplo não está sendo declarado como consumer instalado neste pack.

## 14. Client / server boundary
A decisão de AI funcional — target, navigation, attacks, cooldowns, memories — pertence ao servidor. Cliente pode receber entity data/animation result, mas não deve escolher target real ou executar dano localmente.

Um consumer que usa a library apenas no server ainda pode empacotar o JAR em ambos os lados por distribuição/dependency rules.

## 15. Performance model
O custo depende principalmente dos **consumers**:
- quantidade de entities;
- frequência de sensors;
- raio/complexidade de entity queries;
- path recomputation;
- número de behaviours/conditions.

A library isolada não implica ganho nem perda mensurável. Profiling deve apontar consumer/entity class, não culpar o JAR apenas pelo nome.

## 16. Consumer causal — estado atual da auditoria
Busca no catálogo por `SmartBrainLib` encontrou apenas a própria página; a modlist metadata não expôs, neste lote, um consumer causal inequívoco.

Por regra canônica, a decisão permanece **Sem decisão** em vez de ser promovida automaticamente a `Dependência` apenas porque o projeto é uma library.

Isso **não** autoriza remover o JAR: antes de qualquer corte, executar scan de metadata/dependency graph e boot test controlado.

## 17. Version/API boundary
A linha futura 2.0 renomeia `BrainActivityGroup` para `ActivityBuilder` e reorganiza muitas APIs. Esse changelog serve como evidência de uma boundary de incompatibilidade.

A build instalada é 1.16.11: documentação/código da 2.0 não deve ser usado para implementar ou diagnosticar consumers 1.16.x como se os nomes fossem idênticos.

## 18. Delta exato 1.16.11
A página do arquivo 1.16.11 não publica um resumo textual das mudanças; aponta para os commits da branch 1.21.

Portanto o dossiê não inventa um changelog específico. O que é afirmado sobre architecture vem da documentação/projeto da mesma geração; mudanças exclusivas da build 1.16.11 ficam limitadas à identidade Release/versionada.

## 19. Persistence e entity lifecycle
Validar consumers em:
- entity spawn/despawn;
- chunk unload/reload;
- save/restart;
- dimension transfer quando permitido;
- death/remove;
- target death/disconnect;
- memory timeout;
- activity switch.

Memory/behaviour callbacks não podem continuar rodando para entity removida ou target stale.

## 20. Interações com otimização de servidor
Mods como ServerCore podem reduzir/tunar entity ticking. Se uma AI SmartBrainLib parece “parada”, separar:
1. consumer não recebeu sensor/memory;
2. behaviour condition bloqueou;
3. navigation falhou;
4. entity foi throttled/activation-ranged por otimização externa.

Authority de AI não deve ser diagnosticada apenas pela animação visual.

## 21. Riscos técnicos
1. **Unknown consumer:** remoção quebra mod não mapeado.
2. **API drift:** consumer compilado para geração diferente.
3. **Sensor cost:** scan amplo/frequente degrada TPS.
4. **Memory stale:** target/cooldown nunca é esquecido.
5. **Behaviour starvation:** priority/condition bloqueia tasks.
6. **Duplicate action:** activity switch executa attack/event duas vezes.
7. **Removed entity callback:** task continua após unload/death.
8. **Path loop:** unreachable target causa recomputação excessiva.
9. **Optimizer interaction:** entity activation altera frequência esperada de AI.

## 22. Matriz de testes
- [ ] Dedicated server inicia com SmartBrainLib 1.16.11.
- [ ] Dependency graph/log identifica consumers físicos antes de qualquer remoção experimental.
- [ ] Consumer conhecido, se identificado, spawna sem ClassNotFound/NoSuchMethodError.
- [ ] Sensors atualizam target/memory conforme esperado.
- [ ] Idle→fight→idle muda activity sem task duplicada.
- [ ] Target morto/removido é invalidado.
- [ ] Cooldown/memory temporária expira corretamente.
- [ ] Chunk unload/reload não deixa behaviour callback stale.
- [ ] Save/restart preserva somente state que o consumer pretende persistir.
- [ ] Stress de muitos mobs consumidores é medido com profiler.
- [ ] ServerCore/activation range não é confundido com bug de AI.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 23. Evidências e limites
- Modlist física atual: SmartBrainLib NeoForge 1.21.1-1.16.11.
- CurseForge oficial: library do Brain system, sem gameplay próprio; Release exata e branch de changelog.
- Código público 1.21 do ecossistema do autor: uso concreto de `BrainActivityGroup`, sensors/memories/behaviours na geração 1.16.x.
- Changelog 2.0: renome `BrainActivityGroup`→`ActivityBuilder`, usado somente para delimitar geração de API.
- **Limite:** nenhum consumer causal foi comprovado neste lote. `Sem decisão` é deliberado; necessidade precisa ser resolvida por dependency scan/boot evidence, não por suposição.

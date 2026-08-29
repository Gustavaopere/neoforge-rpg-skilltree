# World Scaling Plan — Formulas, Rewards and Performance

**Goal:** fechar curvas de dificuldade/recompensa com custo aceitável.

- [ ] Separar escalas de HP, dano, defesa, utilidade e recompensa.
- [ ] Configurar caps/curvas para evitar one-shot inevitável e HP sponge.
- [ ] Fazer XP/loot acompanhar risco sem criar farm exponencial.
- [ ] Definir multiplicadores de bosses/raridades especiais.
- [ ] Testar solo, party e grande diferença de níveis.
- [ ] Benchmarkar spawn, consulta espacial e recomputação.

## Infraestrutura concluída nesta etapa

O lifecycle necessário para aplicar Effective Stats de forma segura após reload foi separado da inicialização/rolagem da entidade:

- `EntityScalingStateApplier` define uma fronteira server-authoritative para aplicar um `EntityScalingState` já resolvido, sem rerrolar level/rarity/affixes/behaviors.
- `EntityScalingStateApplierCatalog` permite instalar explicitamente o applier de produção, mantendo balanceamento/policy fora do adapter de evento.
- `EntityScalingEvents.onEntityJoinLevel` agora dá precedência ao estado persistido, chama o initializer apenas quando não existe estado e, em seguida, entrega exatamente o estado persistido/resolvido ao applier instalado.
- `EntityScalingReapplicationGameTests.persistedStateIsReappliedWithoutReroll` prova que reload/re-add reaplica o estado sem chamar o initializer.
- `EntityScalingReapplicationGameTests.newStateInitializesThenAppliesExactlyOnce` prova que uma entidade nova inicializa uma vez e aplica exatamente o mesmo estado salvo.
- O RED original foi o CI #1483 (`33266461721`) no head `728c1bba1422e5ad973f169dbb7700b6ed530db4`, falhando apenas pela ausência de `EntityScalingStateApplierCatalog`.
- O GREEN de implementação foi o CI #1493 (`33266626470`) no head `8710682f71a44480b03ba9171da8bf70755cc074`, com Core, JUnit, NeoForge GameTests, build, JAR e dedicated-server smoke aprovados.

## Pendências causais

Este slice **não** fecha nenhum dos seis itens de balanceamento acima por conta própria. Ainda faltam, em slices seguintes:

1. um applier de atributos com IDs estáveis e aplicação realmente idempotente sobre `AttributeInstance`, preservando modifiers de outros sistemas/mods;
2. políticas/configuração data-driven que mantenham HP, dano, defesa, utilidade e recompensa como eixos independentes;
3. runtime de recompensa (XP/loot) com anti-farm/caps apropriados;
4. multiplicadores/caps de bosses e raridades especiais;
5. matriz solo/party/delta de níveis e benchmarks de hot path.

Nenhum coeficiente final de HP/dano/defesa/recompensa é congelado aqui; os valores permanecem configuração/playtest data, conforme o contrato do RPG Core.

**Acceptance:** curvas permanecem jogáveis no intervalo alvo e nenhum hot path depende de scan global/per-tick desnecessário.
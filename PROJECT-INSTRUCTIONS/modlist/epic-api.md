# Epic-API — 21.3.1

> **Runtime físico e source pin confirmados:** `epic_api-21.3.1.jar` · mod id `epic_api` · versão `21.3.1` · NeoForge 1.21.1. O branch oficial `1.21.1` declara `mod_version=21.3.1`, NeoForge baseline `21.1.219` e Epic Fight como dependência required na linha `21.17.x`; o pack usa NeoForge 21.1.248 + Epic Fight 21.17.3.1.

## 1. Papel no modpack
Epic-API é uma biblioteca separada do core Epic Fight criada para simplificar registro/integração de addons e fornecer helpers reutilizáveis. Ela também adiciona sistemas próprios, principalmente **HeavyAttack** e **CounterAttack**, em vez de ser apenas um conjunto passivo de interfaces.

## 2. Authority / ownership
- **Epic Fight:** player/entity patch, combat pipeline, animations, skills/capabilities-base.
- **Epic-API:** helpers/registrars/event hooks próprios e skills/features que registra.
- **Addon consumidor:** conteúdo que registra através da API.

Não confundir `epic_api` com o package/API interna `yesman.epicfight.api.*` do próprio Epic Fight; são projetos diferentes.

## 3. Source pin 21.3.1
`gradle.properties` do branch 1.21.1 declara explicitamente `mod_id=epic_api` e `mod_version=21.3.1`. O source está, portanto, pinado à mesma versão do JAR físico.

O build atual referencia Epic Fight 21.17.3 e o metadata declara Epic Fight required na linha 21.17.2; o runtime local 21.17.3.1 está imediatamente nessa linha, mas ainda exige regressão porque APIs internas podem mudar em patch releases.

## 4. HeavyAttack
O source contém `skills/common/HeavyAttack.java`, uma Skill própria. O changelog histórico registra correção em que HeavyAttack multiplicava **damage** quando deveria afetar **impact** em determinado caso, provando que damage/impact ownership e scaling são superfícies sensíveis.

HeavyAttack deve executar uma vez no combat pipeline e não ser replicado por addon/KubeJS observando o input ou a animação.

## 5. CounterAttack
A linha 21.2.4 adicionou `CounterAttack` como outra skill comum e EventHooks dedicados para HeavyAttack/CounterAttack. O source 21.3.1 continua contendo ambas.

Counter logic precisa amarrar janela/trigger ao event state do Epic Fight; não assumir que qualquer hit recebido deve gerar counter sem a condição definida pela skill.

## 6. Default skill integration
`PlayerPatchMixin` no source injeta HeavyAttack e CounterAttack no capability de skills quando o player patch entra no mundo. Isso torna join/rejoin/respawn uma superfície crítica para evitar registro duplicado ou skill container stale.

## 7. Weapon datapack integration
`WeaponTypeReloadListenerMixin` injeta dados para tornar HeavyAttack/CounterAttack integráveis ao reload de weapon type/capability. Portanto datapack reload não é apenas apresentação: pode alterar quais combos/capabilities expõem essas skills.

Configuração externa deve usar a camada data-driven esperada, não reflection sobre internals.

## 8. EventHooks
O projeto fornece `IEventHook` e `EpicAPIEventHooks`, incluindo eventos como `HeavyAttackEvent`, `CounterAttackEvent` e hooks de registro/entity patch.

`IEventHook` é documentado no source como um mecanismo leve para execução dinâmica de eventos. Consumers devem respeitar cancelamento/prioridade/contexto definidos pela API e evitar registrar o mesmo listener múltiplas vezes em lifecycle repetido.

## 9. Builders de Minecraft
O changelog/source registra helpers como:
- `CommandsBuilder`;
- `GameRulesBuilder`;
- `ItemsBuilder`;
- `KeyMappingsBuilder`.

Esses builders reduzem boilerplate de registry. Eles não criam ownership de gameplay por si só; o addon que os usa continua responsável pelo conteúdo registrado.

## 10. Builders / registrars Epic Fight
A API possui infraestrutura como:
- `ArmatureRegistrar`;
- `EntityPatchRegistrar`;
- `DeferredCapabilityBuilder`;
- registry/event hooks para capabilities/entity patches.

O changelog 21.2.x contém fixes para armature/entity-patch registration e capability hooks, demonstrando que ordem/deferred registration é um risco real em updates.

## 11. Compatibility loading
O changelog adicionou `@Compatibility` para carregar classes condicionalmente quando um mod id está presente, além de abstractions de classes carregáveis.

Uma correção posterior tratou crash em dedicated server quando classe client-sided era carregada indevidamente. Portanto optional integration deve permanecer guardada por mod presence **e side**.

## 12. Input / keymapping
O source contém `EpicAPIIntputAction.HEAVY_ATTACK` e mixin no `ControlEngine` do Epic Fight. O changelog registra fix de crash de `EpicAPIKeyMappings` com Epic Fight 21.16.3.

Input integration é version-sensitive; addons devem consumir a API da build atual e não hardcodar internals do ControlEngine.

## 13. SimpleAttackAnimation e animation helpers
O changelog registra `SimpleAttackAnimation` e fix de basis speed em constructor, indicando que a biblioteca oferece helpers de animation além de registries.

Animation timing precisa permanecer coerente com hit phase do Epic Fight; alterar speed visual sem revisar damage window pode criar desync percebido.

## 14. Client / Server
- skills, capability registration, event settlement e entity patch registration: common/server-sensitive;
- keymapping/control engine e rendering/animation helpers: possuem superfícies client-side;
- conditional loader precisa impedir classloading client-only em dedicated server;
- damage/impact/counter settlement deve ser server-authoritative.

## 15. Lifecycle
Validar:
- mod bootstrap/deferred registration;
- player join/rejoin;
- death/respawn;
- HeavyAttack/CounterAttack availability;
- weapon datapack reload;
- entity patch registration;
- armature/renderer registration por consumer;
- keymapping/input reload;
- dedicated server boot;
- update Epic Fight/Epic-API.

## 16. Multiplayer / idempotência
HeavyAttack, CounterAttack e consumers de EventHooks precisam produzir exatamente um resultado por evento. Player join não pode adicionar a mesma skill duas vezes. Datapack reload não pode duplicar capability registration.

Clientes podem iniciar input, mas damage/impact/counter result deve convergir no servidor.

## 17. Version drift local
Source 21.3.1 compila contra **Epic Fight 21.17.3**; pack usa **21.17.3.1**. A diferença é pequena, mas a biblioteca usa mixins e APIs internas sensíveis.

Não há evidência de incompatibilidade comprovada; classificar como regression gate em qualquer update do Epic Fight.

## 18. Riscos
1. HeavyAttack aplicar damage/impact incorreto;
2. CounterAttack disparar duas vezes;
3. default skills duplicarem em join/rejoin;
4. weapon reload duplicar capability/data;
5. EventHook listener registrado mais de uma vez;
6. entity patch/armature registrar em fase errada;
7. client class carregar no dedicated server;
8. keymapping/ControlEngine mixin quebrar após update;
9. animation speed divergir da hit window;
10. addon consumidor usar API removida/deprecated;
11. Epic Fight 21.17.3.1 alterar internals usados pela build 21.3.1;
12. confundir Epic-API com API interna do core e registrar a mesma feature em ambas.

## 19. Matriz de testes
1. Dedicated server boot com Epic Fight 21.17.3.1 + Epic-API 21.3.1.
2. Login/relogin e confirmar HeavyAttack/CounterAttack únicos.
3. Death/respawn preservando skill capability coerente.
4. HeavyAttack contra alvo com Impact mensurável; conferir ausência de double damage.
5. CounterAttack sob vários timings de hit.
6. Weapon type/datapack reload e nova tentativa de HeavyAttack.
7. Consumer registrando entity patch/armature via helpers.
8. Client sem optional compat provider e servidor dedicado.
9. Keymapping/controller path em battle mode.
10. Dois jogadores usando HeavyAttack simultaneamente.
11. Smoke-test após qualquer atualização do Epic Fight.
12. Verificar addons consumidores antes de remover/atualizar a biblioteca.

**Esta catalogação não afirma que esses testes foram executados.**

## 20. Evidências
- modlist física canônica: JAR/mod id/version/hash e Epic Fight 21.17.3.1;
- GitHub `M6FGR/Epic-API` branch 1.21.1: `gradle.properties` version 21.3.1 e build dependency Epic Fight 21.17.3;
- source auditado: `HeavyAttack`, `CounterAttack`, `PlayerPatchMixin`, `WeaponTypeReloadListenerMixin`, `IEventHook`, `EpicAPIEventHooks`, registrars/builders e ControlEngine mixin;
- changelog oficial 21.2.x: fixes/additions em damage-impact, capability hooks, armature/entity patch registrars, client-side classloading, key mappings, builders e counter/heavy events.

> **Boundary canônico:** Epic-API fornece **helpers e features adicionais sobre Epic Fight**. O core Epic Fight continua sendo a authority do combat framework e do player/entity patch principal.
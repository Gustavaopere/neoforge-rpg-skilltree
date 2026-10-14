# Epic-API

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#261**: JAR `epic_api-21.3.1.jar`, mod id `epic_api`, runtime `21.3.1`, SHA-1 `cfb57d825beb565445d185be17f6202c5793ec40`.

## Propriedades do registro

- **Mod:** Epic-API
- **Arquivo JAR:** `epic_api-21.3.1.jar`
- **Versão 1.21.1:** `21.3.1`
- **Categoria:** Biblioteca, RPG
- **Função:** Biblioteca/addon API para Epic Fight que simplifica registries e integrações e adiciona infraestrutura própria como HeavyAttack, CounterAttack, builders/registrars e EventHooks reutilizáveis por addons.
- **Dependências:** Source 21.3.1 declara Epic Fight como required (`versionRange = 21.17.2`) e compila contra Epic Fight 21.17.3. Runtime físico usa Epic Fight 21.17.3.1. NeoForge source baseline 21.1.219; pack físico usa NeoForge 21.1.250.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Acoplamento forte aos internals/API do Epic Fight. Riscos: registrars/event hooks quebrarem após update, HeavyAttack/CounterAttack double execution, capability registration duplicada, client-only classloading em dedicated server, input/keymapping conflict e datapack capability drift. Changelog 21.2.x registra fixes exatamente nessas superfícies; 21.3.1 precisa ser revalidado contra Epic Fight 21.17.3.1.
- **Fonte:** https://github.com/M6FGR/Epic-API
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `epic_api-21.3.1.jar`, mod id `epic_api`, runtime 21.3.1 e SHA-1 `cfb57d825beb565445d185be17f6202c5793ec40`. CurseForge oficial revalidado em 21/09/2026 mantém 21.3.1 como latest release NeoForge 1.21.1; source branch 1.21.1 permanece alinhado à mesma versão.
- **Observações:** Source branch 1.21.1 confirma `mod_version=21.3.1`, NeoForge 21.1.219 e Epic Fight dependency 21.17.x. O projeto não é a mesma coisa que a API interna do core Epic Fight: é uma biblioteca separada que oferece helpers e também adiciona HeavyAttack/CounterAttack. Changelog público disponível detalha 21.2.x; não foi inventado changelog 21.3.1 ausente.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #260: `epic_api-21.3.1.jar` / runtime `21.3.1` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader. CurseForge oficial mantém 21.3.1 como latest release NeoForge 1.21.1; source/runtime continuam alinhados.
- **Decisão:** Sem decisão
- **Sobreposição:** Complementa a API do próprio Epic Fight com helpers/registrars/event hooks e skills próprias. Não deve ser tratada como substituta do core, Battle Arts API ou outras libs; consumers precisam usar o contrato específico esperado.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico e source pin confirmados:** `epic_api-21.3.1.jar` · mod id `epic_api` · versão `21.3.1` · NeoForge 1.21.1. O branch oficial `1.21.1` declara `mod_version=21.3.1`, NeoForge baseline `21.1.219` e Epic Fight como dependência required na linha `21.17.x`; o pack usa NeoForge 21.1.250 + Epic Fight 21.17.3.1.
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


## 21. Auditoria source 21.3.1 — HeavyAttack, animação compartilhada e consumo de stamina (13/10/2026)

**Escopo e versionamento:** último inventário histórico consultado: **Epic-API #261 `epic_api-21.3.1.jar`**, mod ID `epic_api`, versão runtime `21.3.1`; **Epic Fight #260 `21.17.3.1`**, **NeoForge #001 `21.1.250`**, Minecraft 1.21.1, Java 21. [CurseForge oficial](https://www.curseforge.com/minecraft/mc-mods/epic-api/files/all) continua publicando `epic_api-21.3.1.jar` como a **release mais recente identificada para NeoForge/Minecraft 1.21.1** (14/07/2026); builds numericamente diferentes para Forge 1.20.1 não a substituem. A inspeção abaixo é **estática**, baseada no [source oficial da branch 1.21.1, commit `29b5c563d6152ba1bd64bed9415a3bc7ad4a7a16`](https://github.com/M6FGR/Epic-API/tree/29b5c563d6152ba1bd64bed9415a3bc7ad4a7a16), cujo `gradle.properties` fixa `mod_version=21.3.1`. Não foi provada identidade byte-a-byte com o JAR físico histórico.

### 21.1. Registry e contratos úteis para perks

[`EpicAPISkills.java`](https://github.com/M6FGR/Epic-API/blob/29b5c563d6152ba1bd64bed9415a3bc7ad4a7a16/src/main/java/M6FGR/epic_api/gameassets/EpicAPISkills.java) utiliza `DeferredRegister.create(EpicFightRegistries.Keys.SKILL, "epic_api")` e registra **`epic_api:heavy_attack`** e **`epic_api:counter_attack`**. A classe [`EpicAPI.java`](https://github.com/M6FGR/Epic-API/blob/29b5c563d6152ba1bd64bed9415a3bc7ad4a7a16/src/main/java/M6FGR/epic_api/main/EpicAPI.java) carrega `EpicAPISkills` via `ILoadableClass.loadClass`. Portanto a biblioteca **declara e registra skills próprias**, mas a presença no registry **não demonstra** que um jogador tenha acesso automático a elas, nem que algum addon ativo já forneça movimentos/animações. A [descrição do autor](https://www.curseforge.com/minecraft/mc-mods/epic-api) a caracteriza como biblioteca para consumidores; não prometer uma habilidade jogável sem verificação de datapack, weapon capability e equip do player.

A [implementação de `HeavyAttack.java`](https://github.com/M6FGR/Epic-API/blob/29b5c563d6152ba1bd64bed9415a3bc7ad4a7a16/src/main/java/M6FGR/epic_api/skills/common/HeavyAttack.java) lê de parâmetros NBT/data-driven:
- `heavy_attack_consumption`, `heavy_dash_consumption`, `heavy_airslash_consumption` (consumos de stamina por modo);
- `attack_recovery` (fallback numérico **16** se ausente);
- `damage_multiplier` (fallback **0.5f**), `impact_multiplier` (**1.0f**) e `armor_negation` (**15.0f**).

Esses são **nomes de chaves encontrados no source 21.3.1**, não nomes inventados de atributos NeoForge e **não equivalem a uma API pública para reescrever diretamente o estado do mod por perk**. Validar schema/registro do consumer antes de editar datapack. O builder da skill usa `Resource.STAMINA`, categoria `HEAVY_ATTACK` e ativação `ONE_SHOT`; `CounterAttack` também usa `Resource.STAMINA`, tem parâmetro `stamina_consumption`, categoria `COUNTER_ATTACK`, e seu `isExecutableState` testa disponibilidade de stamina e `EpicAPIAnimationStates.CAN_COUNTER`. Nenhum comportamento novo foi instalado pelo catálogo.

### 21.2. Escalonamento mutável em `AttackAnimation.Phase`

No source `HeavyAttack.executeOnServer`, uma animação é obtida do `AnimationAccessor`; antes de `playAnimationSynchronized`, `applyWeaponScaling(attackAnim)` percorre **`attackAnim.phases`** e chama `phase.addProperty` para `DAMAGE_MODIFIER`, `IMPACT_MODIFIER` e `ARMOR_NEGATION_MODIFIER`. A [implementação atual da branch Epic Fight 1.21.1, commit `a78aa24b72e90a9d09f5fd61925e4369d117abaf`, `AttackAnimation.Phase.addProperty`](https://github.com/Antikythera-Studios/epicfight/blob/a78aa24b72e90a9d09f5fd61925e4369d117abaf/src/main/java/yesman/epicfight/api/animation/types/AttackAnimation.java) utiliza `this.properties.put(propertyType, value)`: **sobrescreve a chave do mapa em vez de empilhar modificadores numa lista**. Ainda assim, a propriedade é escrita **no objeto de fase da animação**, não em uma cópia explícita por-hit exibida nesse caminho.

**Hipótese operacional, NÃO bug reproduzido:** se o mesmo `AttackAnimation`/fase for compartilhado entre armas, jogadores ou usos, parâmetros de um HeavyAttack podem persistir ou ser sobrescritos por outro uso até uma nova escrita/reset. A versão branch do core Epic Fight pode não ser byte-identical à versão física `21.17.3.1`; não inferir efeito global real sem inspeção de JAR/mutabilidade/lifecycle e reprodução. Para criar perks de escalonamento, preferir interface oficial do provider e estado server-authoritative por hit/skill em vez de mutar fases globais indiscriminadamente.

### 21.3. Ordem de recursos, cancelamentos e estados sem animação

Em `HeavyAttack.executeOnServer`, o source constrói `SkillConsumeEvent` e faz `EpicFightEventHooks.Player.CONSUME_SKILL.postWithListener(...)` antes de obter e validar a lista de animações. Quando o hook não cancela, executa `consumer.consume(..., event.getAmount())`. Só depois verifica `heavyAttackEvent.post()`, a existência de `combo` e `checkConsumption(...)` — método que chama `playerPatch.consumeForSkill(this, resource, consumption)`.

**Pontos de teste, não defeitos declarados:** verificar no JAR/runtime se `getDefaultConsumptionAmount` é zero ou não para essa skill; se o consumo ocorre uma ou duas vezes em um HeavyAttack válido; se stamina é cobrada em caminhos sem `combo`/animação; se cancelamento do evento impede efetivamente gasto; se multipliers alterados por datapack não interferem no cálculo normal de dano/impact. Não concluir “double stamina charge” a partir da presença de dois métodos sem conhecer o default, a semântica dos hooks e o estado do player.

Em [`CounterAttack.executeOnServer`](https://github.com/M6FGR/Epic-API/blob/29b5c563d6152ba1bd64bed9415a3bc7ad4a7a16/src/main/java/M6FGR/epic_api/skills/common/CounterAttack.java), o source cria `SkillConsumeEvent`, verifica `isCanceled()` e consome `counterConsumption`, mas **nesse caminho não aparece chamada explícita a `EpicFightEventHooks.Player.CONSUME_SKILL.post...`**; há `CounterAttackEvent.post()` separado. Confirmar com hooks e mods consumidores se a ausência de post do evento de consumo resulta em política de cancelamento diferente da HeavyAttack; não afirmar bypass efetivo sem teste. O CounterAttack escolhe motion via `DeferredCapabilityBuilder.getParryingCounterAttacks` ou `getNormalCounterAttack`, não gera automaticamente todos os counters para todas as armas.

### 21.4. QA de integração — PENDENTE

1. Confirmar hashes, metadata, classes e bytecode físicos para Epic-API **21.3.1** e Epic Fight **21.17.3.1**; confrontar a versão real de `Phase.addProperty` e `SkillConsumeEvent`.
2. Com datapack/weapon capability de teste, habilitar `heavy_attack` e `counter_attack` por um consumer com IDs válidos e testar armas com e sem motion disponível; medir stamina **antes/depois** por ataque comum/dash/aéreo e por cancelamento.
3. Repetir em dois jogadores com consumos/multipliers diferentes e na mesma animação, depois voltar a ataque normal; medir dano, impact e armor negation para detectar vazamento ou restauração de propriedades de fase.
4. Variar `damage_multiplier`, `impact_multiplier`, `armor_negation` e `attack_recovery` sem presumir que configurações persistidas se aplicam automaticamente; testar datapack reload, death/relog, dimension change, troca de arma e rollback.
5. Validar SideBoundary (skill/dano/stamina no servidor; VFX/inputs no cliente), coexistência de addons Epic Fight e nenhuma liquidação duplicada de damage/impact. Repetir em dedicated server NeoForge **21.1.250**.

**Conclusão da auditoria documental — sem decisão curatorial nova:** a versão física **Epic-API 21.3.1** continua sendo a versão registrada no inventário histórico; a pesquisa **não encontrou motivo técnico comprovado para promover ou substituir o JAR**. O campo estruturado **`Decisão: Sem decisão` permanece vigente** e uma decisão futura de manter/remover/atualizar exige avaliação formal de dependentes e QA. O source sustenta contratos de registry, parâmetros e caminhos de execução, mas não certifica comportamento real dos JARs do pack. Nenhum upgrade, bug confirmado, JAR/config/mundo alterado ou teste in-game é reivindicado.

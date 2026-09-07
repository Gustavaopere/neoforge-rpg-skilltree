# Mastery Plan — Practice-Based Progression

**Goal:** medir domínio por ações reais, não por posse de item ou eventos auxiliares.

- [x] Manter catálogo canônico de mastery IDs.
- [ ] Definir evento semântico confirmado para cada fonte.
- [ ] Ignorar ações canceladas/falhas.
- [ ] Impedir dupla concessão por eventos que representam a mesma ação.
- [ ] Definir curvas, caps e thresholds.
- [ ] Persistir e sincronizar progresso necessário à UI.

## Progresso arquitetural confirmado — Mastery → investimento de classe emergente

- [x] A contribuição de Mastery para `InvestmentState` não é inferida do nome/namespace/provider da lane. A única fonte é metadata explícita em `data/*/mastery_investments/*.json`.
- [x] Cada entrada declara `lane`, `minimum_experience`, `domain_weights` e `tags`; ausência de entrada significa zero contribuição de classe para aquela lane/threshold.
- [x] `MasteryInvestmentMetadataParser` aceita somente lanes canônicas de `MasteryLaneCatalog`, inteiros positivos exatos e falha fechado para duplicata `(lane, threshold)`, colisão de domínio após normalização, valor fracionário/overflow e contribuição no-op.
- [x] `MasteryInvestmentMetadataCatalog` publica snapshot imutável e `MasteryInvestmentMetadataReloader` o substitui somente depois de parse/validação completos no datapack reload.
- [x] A API pública `ClassResolutionRuntime.resolveCanonical(ProgressionState)` consome exclusivamente o catálogo publicado; não existe boundary público de produção para injetar pesos de Mastery ad hoc.
- [x] Thresholds distintos explicitamente declarados para a mesma lane podem coexistir e os thresholds já alcançados acumulam contribuições deterministicamente.
- [x] Catálogo vazio não cria fallback: mesmo uma lane canônica com XP elevado contribui zero se nenhuma metadata de investimento tiver sido publicada.
- [x] O ciclo TDD de hardening foi observado no `RPG Skill Tree CI` `33996656425`: quatro falhas exatas para no-op, domínio normalizado duplicado, fracionários e API ad hoc. O candidato corrigido passou o `RPG Skill Tree CI` `33996882580` completo, incluindo JUnit 5, NeoForge JUnit adapters, GameTests, build/JAR e dedicated-server smoke.
- [ ] Os **valores concretos** de curvas, caps, thresholds e pesos continuam pendentes neste Stage 04.03; nenhum default de balanceamento foi criado pelo fechamento arquitetural do Stage 04.01.

## Progresso runtime confirmado — BOW

- [x] `epicfight:bow` é o ledger canônico do gate BOW; `combat:bow` não permanece como ledger paralelo na arquitetura.
- [x] Tiro físico exige `ArrowLooseEvent` real, não cancelado, seguido de projétil `BowItem` correlacionado e `LivingDamageEvent.Post` com dano positivo contra alvo hostil.
- [x] Cada tipo hostil inédito concede o milestone uma única vez por `DiscoveryProgress`: `mastery:epicfight:weapon/bow/hostile_type/<entity_type>`.
- [x] O milestone BOW usa a política canônica de arma (`+10 epicfight:bow`, além do ledger geral de arma já definido pela política existente).
- [x] Creative, spectator e `FakePlayer` são inelegíveis; projéteis sintéticos/spell/derived sem release física correlacionada falham fechado.
- [x] A identidade de descoberta coincide com a usada pelo adapter Epic Fight, impedindo dupla concessão quando os dois providers observam o mesmo resultado semântico.
- [x] Contrato coberto por teste core; build NeoForge, GameTests e dedicated-server smoke permanecem gates de CI.

## Progresso runtime confirmado — CROSSBOW

- [x] `epicfight:crossbow` é o ledger canônico do gate CROSSBOW; `combat:crossbow` não permanece como ledger paralelo na arquitetura.
- [x] Disparo físico exige `ArrowLooseEvent` real, não cancelado, seguido de projétil associado a `CrossbowItem` da mesma categoria e `LivingDamageEvent.Post` com dano positivo contra alvo hostil.
- [x] BOW e CROSSBOW compartilham um único produtor físico server-authoritative, evitando correlatores de Mastery concorrentes.
- [x] Cada tipo hostil inédito concede o milestone uma única vez por `DiscoveryProgress`: `mastery:epicfight:weapon/crossbow/hostile_type/<entity_type>`.
- [x] O milestone CROSSBOW usa a política canônica de arma (`+10 epicfight:crossbow`, além de `+5 epicfight:weapon`).
- [x] Multishot pode correlacionar vários projéteis à mesma release física, mas o mesmo tipo hostil continua limitado a uma descoberta persistente.
- [x] Creative, spectator e `FakePlayer` são inelegíveis; projéteis sintéticos, spell-derived ou owner-only sem release física correlacionada falham fechado.
- [x] A identidade de descoberta coincide com a usada pelo adapter Epic Fight, impedindo dupla concessão quando os dois providers observam o mesmo resultado semântico.
- [x] Contrato coberto por teste core; build NeoForge, GameTests e dedicated-server smoke permanecem gates de CI.

## Progresso runtime confirmado — FIST

- [x] `combat:fist` permanece o ledger canônico do gate FIST; categorias provider-native `fist` e `knuckle` não criam gates paralelos `epicfight:fist`/`epicfight:knuckle`.
- [x] `rpgskilltree:combat_fist` está publicado no architecture catalog como especialização provider `epicfight`, domínio `MARTIAL`, nível mínimo 8 e gate `combat:fist` 60, alinhado ao `CombatPerkTreeModel` e ao contrato aprovado A0055–A0060.
- [x] O produtor usa `EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST`: somente dano positivo confirmado contra alvo hostil pode gerar milestone.
- [x] `fist` e `knuckle` convergem para a mesma categoria semântica `fist` e para a mesma identidade persistente: `mastery:combat:fist/hostile_type/<entity_type>`.
- [x] Cada tipo hostil inédito concede `+10 combat:fist` e `+5 epicfight:weapon`; seis tipos distintos alcançam o gate 60 e oito alcançam o gate 80 sem permitir spam de hits no mesmo tipo.
- [x] A concessão reutiliza `WeaponMasteryMilestoneRuntime.awardIfNew`, mantendo a mesma fronteira persistente de `DiscoveryProgress` usada nas lanes físicas já fechadas.
- [x] Creative, spectator e `FakePlayer` continuam inelegíveis pelo adapter Epic Fight; categorias fora de `fist|knuckle` e dano não positivo falham fechado.
- [x] Contrato coberto por teste core; build NeoForge, GameTests e dedicated-server smoke permanecem gates de CI.

## Progresso runtime confirmado — EPIC FIGHT

- [x] Provider alvo confirmado na modlist física e no Catálogo Mestre: Epic Fight `21.17.3.1` (`epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`). O adapter continua protegido pelo `EpicFightVersionContract` exato para essa versão.
- [x] `EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST` permanece uma fronteira provider-native pós-resultado para Mastery de arma: exige `ServerPlayer` elegível, alvo hostil, dano modificado positivo e capability real de arma antes de produzir milestone deduplicado por categoria + tipo hostil.
- [x] `EpicFightEventHooks.Entity.ON_DODGE` permanece autoridade pós-resultado para esquiva efetivamente sucedida e usa discovery persistente `mastery:epicfight:dodge_success/first`, impedindo farm de repetição.
- [x] `EpicFightEventHooks.Player.CONSUME_SKILL` **não é autoridade de Mastery**. Na revisão upstream auditada, o provider publica `SkillConsumeEvent`, depois verifica `consumeEvent.isCanceled()` e somente então executa o consumer do recurso; portanto outro listener ainda pode cancelar a ação depois do callback do RPG Skill Tree.
- [x] O handler `onSkillConsume` permanece somente como boundary provider-native para ajustar o custo real de stamina através de `EpicFightStaminaPolicy` e `event.setAmount(...)`. Ele não cria `CombatAction`, não persiste discovery e não chama o pipeline de Mastery.
- [x] `MasteryPolicies.forEpicFight` rejeita explicitamente ações cuja origem seja `epicfight:skill_consume`, fornecendo defesa em profundidade caso um caller futuro tente reutilizar o pré-consumo como autoridade.
- [x] Guard, mover, weapon innate e Mastery genérica de skill/stamina que dependiam apenas de `CONSUME_SKILL` ficam **FAIL-CLOSED** até existir um boundary provider-native pós-execução comprovável; não há fallback em bônus genérico, tick, comparação de stamina posterior ou inferência temporal ambígua.
- [x] Os fluxos confirmados convergem no pipeline canônico `MasteryPolicies.forEpicFight` → `PlayerProgressionRuntime.awardMasteryAndDiscoveries`; o pré-consumo não grava sequer discovery vazio, evitando que uma tentativa cancelada queime ou conceda milestone.
- [x] Creative, spectator e `FakePlayer` permanecem inelegíveis nos handlers provider-specific já existentes.
- [x] O RED TDD foi observado no `RPG Skill Tree CI` `34151024231`, job `101833157762`: 1214 testes foram executados e somente `EpicFightSkillConsumeMasteryAuthorityJUnitTest.preConsumeSkillEventCannotAwardMastery` falhou antes do fix. A primeira rodada candidata depois do runtime fix expôs ainda `EpicFightDepthPolicyTest` com a expectativa histórica de `+10 guard` no mesmo pré-consumo; esse contrato obsoleto foi atualizado para exigir fail-closed.
- [x] Nenhuma curva, cap, threshold, peso de investimento ou valor global de balanceamento foi criado ou alterado neste fechamento de fonte.

## Progresso runtime confirmado — IRON'S SPELLS

- [x] Provider alvo confirmado no build: Iron's Spells 'n Spellbooks `1.21.1-3.16.3`.
- [x] A fonte semântica é `SpellOnCastEvent`; upstream dispara esse evento somente depois de `SpellPreCastEvent` permitir o cast, portanto tentativa cancelada não chega ao produtor de Mastery.
- [x] Apenas origens `SPELLBOOK` e `SCROLL` contam como prática; `COMMAND` e demais origens falham fechado.
- [x] Creative, spectator e `FakePlayer` são inelegíveis para Mastery, mesmo quando o provider emite um cast válido.
- [x] Há um único produtor Iron's no runtime. A concessão segue `SpellOnCastEvent` → `MasteryPolicies.forIron` → `PlayerProgressionRuntime.awardMastery`, sem ledger paralelo nem segundo evento que represente o mesmo cast.
- [x] O cast continua concedendo os ledgers canônicos existentes (`magic:casting`, `irons:casting` e `irons:<discipline>`) sem alterar curvas, thresholds ou intensidade já aprovados.
- [x] O contrato de elegibilidade é isolado de tipos do provider em `IronMasterySourcePolicy`, permitindo teste JUnit sem promover Iron's de integração opcional para dependência runtime obrigatória.

## Progresso runtime confirmado — ARS NOUVEAU

- [x] Provider alvo confirmado na modlist/build: Ars Nouveau `1.21.1-5.13.1`; a revisão upstream auditada é `d16c939835ec9eae27d2eece42d19c572b46389c` (`mod_version=5.13.1`).
- [x] `SpellCastEvent` é somente a fronteira de acesso e de armação causal. Nessa revisão upstream ele é postado antes de `castType.onCast*` produzir o `CastResolveType`, portanto não é autoridade suficiente para conceder Mastery.
- [x] A concessão foi movida para `SpellResolveEvent.Post`, emitido após a resolução não cancelada dos efeitos; tentativa cancelada/falha que não chega a essa fronteira não concede Mastery.
- [x] O `SpellAction` semântico é anexado ao `SpellContext` por `ArsMasteryCausalAward`; a reivindicação one-shot provider-free em `ArsMasteryClaim` garante que uma mesma ação causal só possa conceder Mastery uma vez.
- [x] Clones de `SpellContext` compartilham o valor do attachment na revisão 5.13.1; contextos filhos que não carreguem o attachment diretamente procuram a mesma causalidade pela cadeia `previousContext`.
- [x] Se a causalidade não estiver disponível, o runtime falha fechado. O claim no attachment é transitório e não reconstrói uma ação após serialização sem evidência causal verificável.
- [x] O fluxo canônico é `SpellCastEvent` → armar causalidade → `SpellResolveEvent.Post` → `claimResolved()` → `MasteryPolicies.forArs` → `PlayerProgressionRuntime.awardMastery`; não há segundo ledger/produtor concorrente.
- [x] TDD observado: o RED `34003592647` / job `101406688873` falhou porque a fronteira causal ainda não existia; `34003942932` / job `101407651096` expôs que o JUnit comum não deve promover Ars de `compileOnly` a provider obrigatório; a separação provider-free final passou o `RPG Skill Tree CI` `34004201977` / job `101408341407` completo, incluindo JUnit 5, NeoForge adapters, GameTests, build/JAR e dedicated-server smoke.
- [x] Nenhuma curva, cap, threshold, peso de investimento ou valor de balanceamento de Mastery foi criado ou alterado neste fechamento de fonte.

## Progresso runtime confirmado — GOETY

- [x] Provider alvo confirmado na modlist, no catálogo do projeto e no build: Goety `3.1.4` (`goety-3.1.4.jar`). `GoetyVersionContract` aceita exatamente `3.1.4`; provider ausente, versão diferente ou falha de registro não ativa silenciosamente o adapter provider-specific.
- [x] O bootstrap é opcional e fail-closed: `ABSENT_PROVIDER`, `UNSUPPORTED_VERSION` e `FAILED_CLOSED` impedem registro fora do contrato auditado; somente `ACTIVE` registra `GoetyProgressionEvents`.
- [x] Casts Goety não concedem Mastery no callback inicial. `CastMagicEvent`, `CastingMagicEvent`, `TouchMagicEvent` e `BlockMagicEvent` apenas armam um `PendingCast`; a confirmação exige `ChangeSoulEnergyEvent.Loss` causal do mesmo player dentro da janela de 1 tick.
- [x] O custo efetivamente perdido de Soul Energy, após `GoetySoulPolicy`, alimenta o `SpellAction` confirmado e então `MasteryPolicies.forGoety`; se a perda causal não ocorrer, o pending expira/é descartado e não há concessão.
- [x] Kills de servants exigem alvo `MobCategory.MONSTER`, atacante `IOwned` e owner real `ServerPlayer`; o tipo real da entidade hostil e as tags de classe do servant alimentam `MasteryPolicies.forGoetyServant` sem inferência por posse de item.
- [x] Comandos de servant são intents apenas no clique. O runtime registra os servants realmente commandable/owned/alive/em alcance e só concede no `PlayerTickEvent.Post` quando o estado real de `IServant` confirma o comando; pending não confirmado expira em 2 ticks e é consumido one-shot quando confirmado.
- [x] Logout limpa os mapas transitórios de cast/comando. `FakePlayer`, creative e spectator são inelegíveis pelo boundary comum `eligible(ServerPlayer)`.
- [x] O ciclo TDD do gate de integração foi observado no PR #454: o RED no run `34061893323` / job `101563764311` falhou em `compileTestJava` com 14 símbolos ausentes exatamente para `GoetyIntegrationState`, `GoetyIntegrationBootstrap` e `GoetyVersionContract`, antes da implementação desses contratos.
- [x] Nenhuma curva, cap, threshold, peso de investimento ou valor global de balanceamento foi criado ou alterado neste fechamento de fonte.

## Progresso runtime confirmado — MALUM

- [x] Provider alvo confirmado na modlist e no Catálogo Mestre do projeto: Malum `1.8.2` (`malum-1.21.1-1.8.2.jar`). `MalumVersionContract` aceita exatamente `1.8.2`; ausência, versão divergente ou falha de registro não ativa silenciosamente o adapter provider-specific.
- [x] O bootstrap é opcional e fail-closed: `ABSENT_PROVIDER`, `UNSUPPORTED_VERSION` e `FAILED_CLOSED` impedem registro fora do contrato auditado; somente `ACTIVE` registra `MalumProgressionEvents`.
- [x] O produtor semântico existente foi preservado, sem pipeline paralelo: reaping usa `ModifySpiritSpoilsEvent` e coleta usa `CollectSpiritEvent`, ambos eventos públicos do sistema de spirits já consumido pelo adapter.
- [x] Reaping só concede após evidência positiva e identificada dos `ItemStack`s de spirits do alvo. Resultado ausente/vazio, signature inesperada, `ReflectiveOperationException`, `RuntimeException` ou `LinkageError` produz evidência zero e falha fechado; o fallback anterior de magnitude `1` foi removido.
- [x] O valor causal de reaping continua sendo derivado da quantidade real dos stacks confirmados e das IDs reais dos itens de spirit; nenhuma inferência por posse, receita ou evento auxiliar foi introduzida.
- [x] Coleta continua ancorada no `CollectSpiritEvent` provider-native e representa uma coleta emitida pelo próprio sistema de spirits, sem criar um segundo produtor concorrente.
- [x] `FakePlayer`, creative e spectator são inelegíveis nos dois handlers; apenas `ServerPlayer` pode chegar ao pipeline canônico `MasteryPolicies.forMalum` → `PlayerProgressionRuntime.awardMastery`.
- [x] O ciclo TDD do gate começou no commit `674bc5d0927285d0493d1e6d844500832dcfed53`; SonarQube Cloud `34064478820`, job `101570718580`, observou o RED com `:compileTestJava FAILED` e 14 erros `cannot find symbol` para `MalumIntegrationState`, `MalumIntegrationBootstrap` e `MalumVersionContract` antes da implementação.
- [x] Nenhuma curva, cap, threshold, peso de investimento ou valor global de balanceamento foi criado ou alterado neste fechamento de fonte.

## Progresso runtime confirmado — EIDOLON: REPRAISED

- [x] Provider alvo confirmado na modlist física, no Catálogo Mestre e no build: Eidolon: Repraised `0.5.0.2` (`eidolon_repraised-1.21.1-0.5.0.2.jar`), com mod id runtime `eidolon_repraised`. O ID legado `eidolon` foi removido do registry opcional, metadata NeoForge e matrizes de smoke/bootstrap.
- [x] `EidolonVersionContract` aceita exatamente `0.5.0.2`. `EidolonIntegrationBootstrap` distingue `ABSENT_PROVIDER`, `UNSUPPORTED_VERSION`, `ACTIVE` e `FAILED_CLOSED`; versão divergente ou falha de registro não ativa silenciosamente os listeners provider-specific.
- [x] O produtor de ritual existente foi preservado: interação com Flint & Steel é somente intent; o award exige o `BrazierTileEntity` provider-native provar ritual selecionado, transição de burning e `ritualDone=true` antes da conclusão. Ritual abortado/extinto sem confirmação não concede Mastery.
- [x] O produtor de alquimia existente também foi preservado: interação/toss apenas associa contribuinte a um `CrucibleTileEntity`; a confirmação exige spawn de item compatível com o resultado exato de uma `CrucibleRecipe` ativa, identidade estável de recipe e pending causal válido do mesmo player/dimensão/janela.
- [x] Os dois fluxos convergem exclusivamente em `MasteryPolicies.forEidolonRitual`/`MasteryPolicies.forEidolonAlchemy` → `PlayerProgressionRuntime.awardMasteryAndDiscoveries`; não foi criado ledger, recurso ou segundo pipeline paralelo.
- [x] `FakePlayer`, creative e spectator permanecem inelegíveis; logout remove pendências transitórias, e ausência de confirmação provider-native falha fechado sem award substituto.
- [x] O RED TDD foi observado no `RPG Skill Tree CI` `34148023477`, job `101824145746`: `:compileTestJava FAILED` com 14 erros `cannot find symbol` para `EidolonIntegrationState`, `EidolonIntegrationBootstrap` e `EidolonVersionContract` antes da implementação. A primeira rodada GREEN-candidate ainda expôs IDs legados adicionais nos contratos Foundation, que foram corrigidos antes do fechamento final.
- [x] Nenhuma curva, cap, threshold, peso de investimento ou valor global de balanceamento foi criado ou alterado neste fechamento de fonte.

## Progresso runtime confirmado — CREATE

- [x] Provider alvo confirmado na modlist/registro de projeto: Create `6.0.10`. `CreateVersionContract` aceita exatamente `6.0.10`; provider ausente, versão diferente ou falha de registro não ativa silenciosamente o adapter.
- [x] O bootstrap é opcional e fail-closed: `ABSENT_PROVIDER`, `UNSUPPORTED_VERSION` e `FAILED_CLOSED` impedem o listener provider-specific de permanecer ativo fora do contrato auditado.
- [x] A fonte semântica usada pelo base Create é `AdvancementEarnEvent`, restrita a uma allowlist finita de **51 advancements** auditados da superfície 6.0.10. Namespace/path desconhecido, posse/receita/background, throughput-farm, acidente/gimmick de combate e demais eventos fora da allowlist não concedem Mastery.
- [x] O base Create possui somente `kinetics`, `logistics` e `automation`; `artillery`, `aeronautics` e `power` ficam reservadas aos providers/addons que realmente possuem essas mecânicas, evitando authority falsa e dupla propriedade de lane.
- [x] Cada advancement permitido gera identidade persistente `mastery:create:engineering/advancement/<path>` e awards replay-safe. A política canônica concede `+3` em `CREATE_ENGINEERING` e `+3` na lane específica do milestone.
- [x] `CreateMasteryMilestoneRuntime` consulta `DiscoveryProgress` antes da mutação e persiste awards + discovery pela mesma fronteira `PlayerProgressionRuntime.awardMasteryAndDiscoveries`; replay do mesmo milestone retorna sem nova concessão.
- [x] Creative, spectator e `FakePlayer` são inelegíveis no listener provider-specific; player não servidor também é ignorado.
- [x] A cobertura da fronteira persistente roda no `testJunit` carregado pelo NeoForge, porque mocks de `ServerPlayer` dependem do bootstrap Minecraft. O `Volcanoes Third-Party Provenance Audit` `34055889760` executou `./gradlew --no-daemon build` com plain JUnit + `testJunit` e fechou GREEN no candidato funcional.
- [x] Nenhuma curva, cap, threshold, peso de investimento ou valor global de balanceamento foi criado ou alterado neste fechamento de fonte.

As demais caixas gerais acima permanecem abertas porque o fechamento é por fonte completa de Mastery, não por uma única categoria de arma, provider ou pela fronteira de investimento de classe.

**Acceptance:** repetir uma ação válida aumenta mastery exatamente uma vez e tentativas inválidas não aumentam. A autoridade estrutural para projetar Mastery em classe emergente está fechada; curvas/caps/thresholds concretos e cobertura integral das fontes continuam abertos neste subplano.
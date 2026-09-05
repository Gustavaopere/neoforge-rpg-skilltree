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

## Progresso runtime confirmado — IRON'S SPELLS

- [x] Provider alvo confirmado no build: Iron's Spells 'n Spellbooks `1.21.1-3.16.3`.
- [x] A fonte semântica é `SpellOnCastEvent`; upstream dispara esse evento somente depois de `SpellPreCastEvent` permitir o cast, portanto tentativa cancelada não chega ao produtor de Mastery.
- [x] Apenas origens `SPELLBOOK` e `SCROLL` contam como prática; `COMMAND` e demais origens falham fechado.
- [x] Creative, spectator e `FakePlayer` são inelegíveis para Mastery, mesmo quando o provider emite um cast válido.
- [x] Há um único produtor Iron's no runtime. A concessão segue `SpellOnCastEvent` → `MasteryPolicies.forIron` → `PlayerProgressionRuntime.awardMastery`, sem ledger paralelo nem segundo evento que represente o mesmo cast.
- [x] O cast continua concedendo os ledgers canônicos existentes (`magic:casting`, `irons:casting` e `irons:<discipline>`) sem alterar curvas, thresholds ou intensidade já aprovados.
- [x] O contrato de elegibilidade é isolado de tipos do provider em `IronMasterySourcePolicy`, permitindo teste JUnit sem promover Iron's de integração opcional para dependência runtime obrigatória.

As demais caixas gerais acima permanecem abertas porque o fechamento é por fonte completa de Mastery, não por uma única categoria de arma, provider ou pela fronteira de investimento de classe.

**Acceptance:** repetir uma ação válida aumenta mastery exatamente uma vez e tentativas inválidas não aumentam. A autoridade estrutural para projetar Mastery em classe emergente está fechada; curvas/caps/thresholds concretos e cobertura integral das fontes continuam abertos neste subplano.

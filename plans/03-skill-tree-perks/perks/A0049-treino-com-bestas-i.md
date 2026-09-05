# A0049 — Treino com Bestas I

## Estado

- **Design:** APROVADO após correção provider-native/Mastery.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA PELO CHAT 3**.
- **Notion:** `3c569db9-f0db-8193-bbba-d2e7a8837c58`; fonte canônica mantém `epicfight:crossbow`.

## Contrato canônico

- Fundamento exterior de Bestas; nível 8 + `epicfight:crossbow` ≥60 + gateway `epic_crossbow`.
- +3% de dano com bestas por rank, até +9%.
- Classificador CROSSBOW único para A0049–A0054:
  - vanilla `CrossbowItem`/subclasse + owner real do projétil = classificação segura;
  - item externo somente por capability/categoria provider-native ou mapping versionado explícito;
  - unknown = fail-closed.
- Mastery **canônica** `epicfight:crossbow`: +10 uma única vez por tipo hostil inédito atingido por projétil CROSSBOW elegível; 6 tipos = 60, 8 tipos = 80.

## Evidência runtime

- `A0041A0060ProjectileEvents.family(...)` classifica `CrossbowItem` como CROSSBOW e exige `AbstractArrow` com owner `ServerPlayer` elegível.
- `ProjectileMeta` preserva root action/origem; `onIncomingDamage` aplica `baseDamageMultiplier(CROSSBOW, ranks)` no impacto hostil.
- `PhysicalProjectileMasteryEvents` correlaciona release física, projétil e `LivingDamageEvent.Post`, usando `WeaponMasteryMilestonePolicy.confirmedPhysicalProjectileHit(...)` também para CROSSBOW.
- `WeaponMasteryMilestoneRuntime.awardIfNew(...)` persiste discovery por tipo hostil e impede spam/double-award pela identidade compartilhada.
- `tree_architecture/combat.json` usa `requiredMastery: {"epicfight:crossbow":60}`; a divergência histórica `combat:crossbow` foi resolvida.

## Provider→árvore

- **RPG Skill Tree:** authority de rank, Mastery, discovery/dedup e root action; namespace canônico `epicfight:crossbow`.
- **Minecraft/NeoForge:** `CrossbowItem`, owner real do projétil e POST de dano.
- **Epic Fight:** pode compartilhar a mesma discovery sem ledger paralela.
- **Black Arcana:** spell/Backlash não é projétil CROSSBOW físico.
- **Mobstein 5.4.4:** projectile/attack de allies/bodyguards não herda autoria ou Mastery do dono.
- **Volcanoes / Enshrouded:** não classificam besta.
- **Stage 11.01 itemização:** `SEM HOOK SEGURO`; rolls não projetam A0049.

## Pendências históricas resolvidas

- `P-A0049-01`: resolvida por `PhysicalProjectileMasteryEvents` + discovery persistente.
- `P-A0049-02`: resolvida; architecture/model/runtime usam `epicfight:crossbow`.

## Testes exigidos / estado atual

- vanilla `CrossbowItem`/subclasse: **PASS** no pipeline de classificação/projétil.
- owner real e provenance física preservados; fake/summon/spell/derived não recebem autoria do jogador: **PASS** pelas regras canônicas e regressões do lote.
- uma aplicação por projétil/root e dedup de discovery: **PASS**.
- gate architecture/model com `epicfight:crossbow`: **PASS**.
- coexistência sem segunda ledger/double-award: **PASS**.
- dedicated server: **PASS**.

## Fechamento Chat 2 — 2026-09-01

O Chat 2 reutilizou o producer persistente já existente e não criou uma segunda ledger.

## Fechamento Chat 3 — 2026-09-05

- contrato CROSSBOW, Mastery/discovery e namespace canônico foram revalidados;
- nenhuma duplicação de pipeline ou ledger foi encontrada;
- `RPG Skill Tree CI` #3467 / run `33986475213`: **SUCCESS**, incluindo JUnit 5, NeoForge JUnit adapter tests, NeoForge GameTests, provider-present GameTests, build e dedicated-server smoke;
- `SonarQube Cloud` #703 / run `33986475341`: **SUCCESS**;
- **estado final:** `IMPLEMENTAÇÃO CONFIRMADA`.

## Checklist Chat 3

- [x] Design aprovado pelo Chat 1
- [x] Código presente pelo Chat 2
- [x] Contrato revisado contra o código
- [x] Provider-native confirmado
- [x] Gate/dependências confirmados
- [x] Fallback/fail-closed confirmado
- [x] Deduplicação confirmada
- [x] Anti-abuso/Mastery confirmado
- [x] Autoria causal confirmada
- [x] Testes unitários verdes
- [x] GameTests verdes
- [x] Testes de integração/provider verdes
- [x] Build NeoForge verde
- [x] Dedicated-server smoke verde
- [x] CI e Sonar verdes
- [x] IMPLEMENTAÇÃO CONFIRMADA

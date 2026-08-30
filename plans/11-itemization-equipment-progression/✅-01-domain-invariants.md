# 11.01 — Domínio, invariantes e autoridade

## Objetivo

Congelar o contrato conceitual antes de escrever runtime: o que é equipamento itemizável, quais dados são permanentes, quem pode mutá-los e como o Stage 11 se relaciona com sistemas existentes.

## Passo a passo

### A — Definir vocabulário

- [x] `ItemizationIdentity`: identidade persistente da instância, composta por `instanceId`, `deterministicSeed` e `schemaVersion`.
- [x] `ItemRank`: Comum, Incomum, Raro, Épico, Lendário, Mítico, Único, materializados tecnicamente como `COMMON`..`UNIQUE`.
- [x] `ItemPower`: escala numérica não negativa e independente de rank/quantidade de modificadores.
- [x] `ModifierFamily`: `PREFIX`, `SUFFIX`, `INFIX`.
- [x] `RolledModifier`: `ResourceLocation` da definição + parâmetros/rolls numéricos persistidos e imutáveis.
- [x] `GenerationSource`: craft, smithing, loot, mob equipment/drop, reward, trade, machine, migration, admin/fallback.

### B — Congelar regras permanentes

- [x] cada família contém 1..5 modificadores;
- [x] rank não define quantidade;
- [x] primeira geração é definitiva;
- [x] reload só pode alterar definições para gerações futuras: o estado canônico expõe rolls persistidos imutáveis e não possui operação de re-roll/regeração;
- [x] reparo, mudança de dimensão, drop/pickup e containers preservam identidade por contrato; lifecycle adapters posteriores não podem substituir `ItemizationIdentity` para o mesmo equipamento;
- [x] smithing/upgrade compatível preserva identidade via `ItemizationIdentityPolicy.preserveForEvolution(...)`;
- [x] cópias reais de stack possuem política explícita via `forkForTrueCopy(...)`, que exige novo `instanceId`, novo `deterministicSeed` e preserva a versão de schema.

### C — Separar estado permanente de projeções

```text
ItemizationState persistido
-> resolução de definições (subplanos posteriores)
-> ItemizationSnapshot efetivo/read-only
-> projeções de atributos/efeitos/UI (subplanos posteriores)
```

O Stage 11.01 persiste/congela a decisão canônica e não cria cache ou modifier aplicado como segunda autoridade. `ItemizationQueryService` produz snapshot imutável sem mutation implícita.

### D — Autoridade e boundaries

- [x] geração/mutação é contrato server-authoritative, concentrado em `ItemizationMutationAuthority`; não existe entrada survival de re-roll;
- [x] a fronteira de query é `ItemizationQueryService`/`ItemizationSnapshot`, separada da mutation e suficiente para consumers futuros de tooltip/UI;
- [x] o domínio não importa classes de mods opcionais nem packages internos de compatibilidade;
- [x] adapters externos permanecem fora do domínio e devem entrar pelas fronteiras dos Stages 00/06 e pelos subplanos específicos do Stage 11;
- [x] APIs públicas distinguem explicitamente query de mutation.

### E — Política de reroll

- [x] não existe API survival de reroll;
- [x] `ItemizationMutationAuthority.initialize(...)` rejeita fail-closed qualquer segunda inicialização de item já itemizado;
- [x] nenhuma exceção administrativa de survival foi criada; eventual comando de debug futuro terá de ser explícito, permissionado e diagnosticável fora desta API canônica.

### F — PT-BR como requisito de domínio

- [x] IDs técnicos permanecem `ResourceLocation` estáveis;
- [x] o domínio não persiste texto de exibição; texto próprio futuro deverá usar chaves de localização;
- [x] `pt_br` permanece obrigatório para toda chave player-facing introduzida pelos subplanos posteriores do Stage 11;
- [x] strings traduzidas não fazem parte de `ItemizationIdentity`, `RolledModifier` ou `ItemizationState`.

## Implementação fechada

Contrato canônico em `src/main/java/dev/gustavopere/rpgskilltree/itemization/domain/`:

- `ItemizationIdentity`, `ItemRank`, `ItemPower`;
- `ModifierFamily`, `GenerationSource`, `RolledModifier`;
- `ItemizationState` e `ItemizationSnapshot` com defensive/deep immutable copies;
- `ItemizationQueryService` separado de `ItemizationMutationAuthority`;
- `ItemizationIdentityPolicy` para evolução do mesmo equipamento versus cópia real;
- validação central `ItemizationModifiers` para exatamente as três famílias e 1..5 rolls por família.

## Testes e evidência

- `ItemizationDomainContractTest`: vocabulário canônico, schema/seed, `ItemPower`, 1..5 por família, independência rank/contagem, rejeição de segunda geração, evolução/cópia de identidade, rejeição de UUID ou seed reutilizados em cópia real, snapshot read-only, `ResourceLocation`, ausência de texto traduzido persistido e defensive copies.
- `ItemizationOptionalImportBoundaryTest`: varre o package de domínio e recusa imports fora de JDK/Minecraft/NeoForge, impedindo inclusive dependência indireta em `runtime.compat`/providers opcionais.
- TDD RED inicial: RPG Skill Tree CI `33308736024` falhou em `:compileTestJava` exclusivamente pelos tipos 11.01 ainda inexistentes.
- Primeiro review do PR #232: a allowlist original permitia qualquer package interno; corrigido em `1fc372df5eda3e2beaa4292224cf59a6cf967d90` com barreira estrita.
- GREEN intermediário: RPG Skill Tree CI `33309096174` / run #2100 — JUnit 5, NeoForge GameTests, validators, NeoForge build, JAR e dedicated-server smoke GREEN; workflows Foundation/Compendium associados também GREEN.
- Segundo review do PR #232: `forkForTrueCopy(...)` ainda aceitava seed igual ao original. Foi criado primeiro o teste de regressão em `79fdca4bddadea6b11a97a379faf59ce50853252`; o RPG Skill Tree CI `33319128527` / run #2107 confirmou RED exatamente em `compatibleEvolutionPreservesIdentityAndTrueCopiesForkIt()` (`109 tests completed, 1 failed`).
- Correção: `973c2bb0f9f3afad7429cf835c2aa7fa6652bcd0` passou a exigir simultaneamente `instanceId` e `deterministicSeed` distintos para cópia real.
- GREEN funcional final antes do fechamento documental: RPG Skill Tree CI `33319227439` / run #2109 — Core, JUnit 5, NeoForge GameTests, Compendium, validators, drift, NeoForge build, verificação do JAR e dedicated-server smoke GREEN; todos os oito workflows Foundation/Compendium associados ao mesmo head também GREEN.

## Acceptance

**SATISFIED.** Existe um único contrato documentado e testado para identidade, autoridade, imutabilidade dos rolls, famílias, política de cópia/evolução e localização. Os subplanos seguintes devem reutilizar estes tipos e não criar representações concorrentes.
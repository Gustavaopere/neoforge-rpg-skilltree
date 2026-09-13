# AUDITORIA CHAT 3 — A0081–A0090 — VALIDAÇÃO E FECHAMENTO

Data: 2026-09-07  
PR: #372  
Branch: `feat/chat2-a0081-a0090-implementation`  
Escopo: **exatamente 10 perks consecutivas, A0081–A0090**.  
Minecraft: NeoForge 1.21.1  
Java: 21

## 1. Estado do ciclo

- Chat 1: **DESIGN APROVADO / LOTE FECHADO**.
- Chat 2: **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO**.
- Chat 3: implementação revisada, pendências técnicas solucionáveis corrigidas, cobertura adicionada/completada e baseline funcional integralmente verde.
- A0091+ não foi iniciado.
- Nenhum redesign foi realizado.

## 2. Preflight obrigatório

A modlist física mais recente anexada em 2026-09-07 contém **612 entradas top-level**. Dependências `jarjar` não foram contadas como mods top-level.

A Auditoria Mestre da Modlist no Notion foi consultada novamente no fechamento: **613 registros totais, 612 `Instalado`, 613 `Verificado`**. O manifesto operacional do mesmo snapshot registra **605/605 `modVersion` exatos** e **7/7 JARs sem runtime version preservados sem inferência**.

Este Chat 3 não refez a auditoria integral dos quatro guias, conforme protocolo. Foram preservados os contracts já fechados pelo Chat 1 e consultadas apenas as evidências pertinentes às pendências técnicas.

## 3. Reconciliação da branch/PR

A PR #372 nasceu historicamente empilhada sobre A0071–A0080. Após o fechamento/merge da #355, o Chat 3 preservou o head histórico em `backup/chat3-v12-a0081-a0090-stacked` e reconstruiu a branch A0081–A0090 sobre a `main`, mantendo apenas o delta deste lote.

A PR atual é não-draft e mergeable. Nenhuma perk A0071–A0080 foi reimplementada nesta branch.

## 4. Correções técnicas realizadas pelo Chat 3

### 4.1 Iron's reflection health

O review apontou que uma falha de invocação refletida poderia deixar A0083 comprável enquanto todas as roots degradavam silenciosamente para zero healing. Isso foi corrigido sem alterar o design:

- `IronsSustainVersionContract` ganhou latch process-wide `runtimeInvocationHealthy`;
- falha em `lifestealPercent(...)` marca o runtime contract como incompatível;
- `runtimeContractPresent()` passa a retornar `false` nas verificações subsequentes;
- availability de A0083 volta a fail-closed/unavailable.

Regressão: `IronsSustainVersionContractJUnitTest.reflectiveInvocationFailureLatchesTheRuntimeContractUnavailable()`.

### 4.2 Test lane / Sonar S2187

As suites A0081–A0090 que dependem do bootstrap Minecraft/NeoForge foram movidas para a lane `testJunit`, em vez do JUnit puro. O harness executável de validação foi renomeado para `A0081A0090Chat3ValidationMain`, removendo o falso reconhecimento Sonar de classe de teste sem `@Test` e eliminando S2187 sem suppress/exclusion.

A correção não altera gameplay.

### 4.3 Crossbow review

O review P1 sobre crossbow foi rechecado contra o hook real NeoForge 1.21.1. `CrossbowItem.performShooting` passa por `EventHooks.onArrowLoose(...)`, portanto o `ArrowLooseEvent` usado pelo runtime é um receipt real também para crossbow. Não foi criado segundo hook concorrente.

A thread foi respondida e resolvida.

## 5. Resultado por perk

| Código | Estado Chat 3 | Decisão final do lote |
|---|---|---|
| A0081 | **NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA** | herda A0075 unavailable; purchase/rank efetivo fail-closed; recovery/lifecycle testados |
| A0082 | **IMPLEMENTAÇÃO CONFIRMADA NOS BINDINGS FÍSICOS CAUSAIS** | Epic Fight/vanilla/projectile usam root causal; Ignitium permanece source-specific fail-closed |
| A0083 | **IMPLEMENTAÇÃO CONFIRMADA PARA IRON'S 1.21.1-3.16.3** | exact version + API/runtime shape; drift/falha/native lifesteal ambíguo falham fechado; Ars permanece sem adapter |
| A0084 | **NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA** | indisponível sem mapa canônico/versionado school/damage type→elemento |
| A0085 | **NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA** | indisponível sem owner + applicationId + pulseId provider-native |
| A0086 | **NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA** | availability transitiva de A0085; universal não sintetiza classificadores |
| A0087 | **NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA** | indisponível sem A0075/A0081 + BodyProvider/healing-received geral; nenhum benefício parcial |
| A0088 | **IMPLEMENTAÇÃO CONFIRMADA** | `MAX_HEALTH` relativo + preservação de proporção de vida + modifier idempotente |
| A0089 | **IMPLEMENTAÇÃO CONFIRMADA** | `ARMOR` relativo; zero base permanece zero; sem STUN_ARMOR/segunda resistência |
| A0090 | **IMPLEMENTAÇÃO CONFIRMADA** | `ARMOR_TOUGHNESS` relativo; A0089≥2 preservada; zero base permanece zero |

## 6. Evidência funcional específica

### Sustain / recovery

`A0081A0100CombatPolicyTest` e `A0081A0090Chat3ValidationMain` cobrem:

- maior coeficiente elegível em vez de soma integral;
- claim-once/dedup por root;
- cap compartilhado 3% max health/20 ticks;
- clipping de overkill e missing health;
- native correlation ambígua fail-closed;
- isolamento por ator;
- Recovery: cap 8% max health, 3 s de atraso, snapshot congelado, quatro parcelas, desconto da cura realmente aplicada, interrupção por dano hostil e expiry 10 s.

### Runtime / availability / lifecycle

`A0081A0090RuntimeCoverageJUnitTest` e `A0081A0090EventCoverageJUnitTest` cobrem:

- A0081/A0084/A0085/A0086/A0087 unavailable;
- ranks persistidos indisponíveis mascarados para zero efetivo;
- A0083 provider absent/version mismatch/API mismatch → unavailable;
- provider receipt one-shot/actor scoped;
- physical sustain uma vez por root;
- source nativa ambígua sem Skill Tree healing;
- bow/crossbow projectile correlation e correlação expirada fail-closed;
- dano zero sem pagamento;
- death/logout/dimension/respawn/server stop limpam estado bounded;
- direct magic Iron's chega ao POST uma vez.

### Atributos A0088–A0090

O GameTest canônico `AttributeModifierGameTests` usa `ServerPlayer` real e exercita `MULTIPLY_TOTAL` em apply → repeated apply → remove → reapply. `verify-attribute-runtime.py` exige esse GameTest e a reconciliação de players online após reload válido.

`A0081A0100CombatPolicyTest` cobre os multiplicadores +2%/rank e a preservação de health ratio de A0088.

## 7. Baseline funcional validado

HEAD funcional pré-documentação:

`4502d1d253863f6f25e13e6a7284a0d53a3b0fd5`

### RPG Skill Tree CI

Run `34147843664`: **SUCCESS**.

Passaram, entre outros:

- Core tests;
- JUnit 5;
- NeoForge JUnit adapter tests;
- NeoForge GameTests;
- Battle Mage provider-present GameTests;
- data/client/node/passive/runtime validations;
- attribute runtime validation;
- unified node effect runtime validation;
- canonical provider binding runtime validation;
- generated-data sanity;
- NeoForge build;
- built JAR verification;
- dedicated-server smoke.

### SonarQube

Run `34147843581`: **SUCCESS**.

Passaram as coletas provider-free, Battle Mage, Ars Nouveau, Create e MineColonies, seguidas de `Build and analyze with SonarQube`. O finding S2187 foi removido pela correção estrutural do harness, sem diminuir Quality Gate nem excluir runtime novo.

### CodeQL

Run `34147843620`: **SUCCESS** (`Compile project for CodeQL` + `Analyze`).

## 8. Review threads

Duas threads existiam na #372:

1. Crossbow/ArrowLooseEvent — revalidada contra o hook real NeoForge e resolvida sem mudança de runtime.
2. Iron's reflection health — defeito válido corrigido com latch fail-closed + regressão JUnit; thread respondida e resolvida.

Estado: **2/2 resolvidas**.

## 9. Pendências não bloqueantes preservadas

- A0082: `EXACT_INTERCEPTED` de Ignitium continua ausente; essa fonte permanece Skill Tree=0.
- A0083: Ars Nouveau 5.13.1 permanece sem adapter causal aprovado.
- A0084: ativação futura exige mapa canônico/versionado elemento↔provider; mudança desse design volta ao Chat 1.
- A0085: ativação futura exige provider com owner/application/pulse; divergência semântica do primeiro provider volta ao Chat 1.
- A0086: permanece bloqueada transitivamente por A0085.
- A0087: permanece bloqueada por contracts corporais/healing-received; se o +8% geral não puder ser implementado sem alterar escopo, retorna ao Chat 1.

Essas pendências não tornam o estado fail-closed atual inseguro e não autorizam heurísticas.

## 10. Retorno ao Chat 1

Nenhum bloqueio atual exige redesign para mergear o comportamento existente. Os retornos listados acima são apenas condicionais à futura ativação de nodes hoje deliberadamente unavailable.

## 11. Gate documental final

Os 10 dossiês e `STATUS.md` são atualizados neste fechamento com a evidência acima. Como essa atualização cria um novo HEAD, o Chat 3 deve exigir **CI/Sonar/CodeQL verdes frescos no HEAD documental** antes do merge.

Somente depois disso:

1. merge da PR #372;
2. fetch fresco da `main`;
3. confirmação do SHA final da `main` e presença do merge;
4. confirmação da saúde pós-merge quando workflow aplicável existir;
5. parar sem iniciar A0091+.
# Auditoria Chat 3 — A0051–A0060 — Pendências, testes, validação e merge

## Escopo

- **INÍCIO:** A0051
- **FIM:** A0060
- **PR:** #387
- **Branch:** `feat/chat2-a0051-a0060-stacked-handoff`
- **Minecraft:** 1.21.1
- **NeoForge:** linha 21.1.x; modlist fresca do fechamento registra `neoforge-21.1.248`.
- **Java:** 21
- **Provider MARTIAL principal:** Epic Fight `21.17.3.1`.

O Chat 3 revisou a implementação entregue pelo Chat 2 contra os dossiês aprovados, resolveu a pendência técnica A0052 Multishot sem alterar design, executou a bateria automatizada aplicável e preservou fail-closed onde a versão atual do provider não expõe receipt semântico suficiente.

## Modlist fresca e boundaries

A modlist anexada no fechamento possui **607 mods**, substituindo o snapshot de 573 usado pelos guias consolidados como autoridade de presença/versão para esta execução. Para este lote:

- Epic Fight permanece `21.17.3.1` — sem delta funcional relevante para os contratos A0051–A0060.
- Apothic Attributes permanece `2.10.1` — resolver crítico/penetration continuam sob pipelines canônicos já auditados.
- Mobstein permanece `5.4.4` — companion/bodyguard não recebe autoria de ataque físico do dono por inferência.
- Punchy passou de `2.7d` no snapshot/Notion para `2.7e` na modlist fresca. É visual/compat, não provider de heavy/finalizer, guard-break, Stamina, FIST classification ou autoria; o drift não altera nem bloqueia este lote.

Nenhuma dessas diferenças autoriza bridge nova ou substituição de provider.

## Resultado por perk

| Perk | Resultado Chat 3 | Pendência restante |
|---|---|---|
| A0051 — Precisão com Bestas | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma bloqueante |
| A0052 — Cadência de Recarga | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL enquanto A0050 estiver indisponível** | herda A0050 sem binding semântico de reload/preparation speed |
| A0053 — Virote Perfurante | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL** | herda A0050→A0052; Impact component-wise só com receipt seguro |
| A0054 — Mecanismo Ajustado | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL** | herda cadeia CROSSBOW; reload-speed extra omitido sem hook seguro |
| A0055 — Treino com Armas de Punho I | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma bloqueante |
| A0056 — Treino com Armas de Punho II | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma bloqueante |
| A0057 — Precisão com Armas de Punho | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma bloqueante |
| A0058 — Sequência Limpa | **IMPLEMENTAÇÃO CONFIRMADA NOS COMPONENTES COM RECEIPT REAL / FALLBACK CANÔNICO** | heavy-impact recebido e body modulation permanecem provider/config-bound |
| A0059 — Quebra de Ritmo | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL** | heavy/finalizer + guard-break receipts provider-native ausentes |
| A0060 — Combinação Final | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL** | heavy/finalizer + ledger causal de Stamina ausentes |

A expressão `FAIL-CLOSED ATUAL CONFIRMADO` significa que a implementação segura/inativa prevista pelo contrato foi validada. Ela **não** significa que o efeito dependente de provider está disponível ou jogável.

## Pendência P-A0052-04 — Multishot

### RED reproduzível

A implementação anterior tratava cada `AbstractArrow` de um Multishot isoladamente. O primeiro sibling que atingisse bloco podia executar `onCrossbowFailure(...)` e reduzir Cadência antes de outro sibling do mesmo `rootActionId` confirmar hit.

### Correção

Foi introduzido arbiter transiente bounded por `actor + rootActionId` em `A0041A0060CombatState`:

- `registerCrossbowProjectile(...)` — registra siblings correlacionados reais;
- `recordCrossbowProjectileFailure(...)` — acumula miss sem settlement prematuro;
- `recordCrossbowProjectileSuccess(...)` — success terminal para failure arbitration;
- `sealCrossbowRoot(...)` — fecha a janela de registro;
- all-fail liquida no máximo uma perda de Cadência;
- qualquer sibling success vence failures anteriores;
- callbacks duplicados/replay não liquidam o root novamente;
- TTL, reconciliation e lifecycle removem outcomes órfãos.

`A0041A0060ProjectileEvents` foi ligado ao arbiter no runtime real: register no spawn correlacionado, success no dano POST confirmado, failure em impacto não-entidade e seal no encerramento da janela causal.

### Teste de regressão

`A0051A0060Chat2ContractJUnitTest.multishotRootUsesSuccessWinsAndAtMostOneFailure()` cobre:

1. miss de sibling antes de hit de outro sibling → **success wins**, sem perda;
2. todos os siblings falham → exatamente uma perda;
3. seal antes dos outcomes finais → último miss pode liquidar uma única falha;
4. callbacks duplicados → nenhuma segunda liquidação.

**P-A0052-04: RESOLVIDA.**

## Validação automatizada funcional

HEAD funcional validado:

`6826c50896c4ad586b8942031465b6a0a3ce44af`

Workflow:

`RPG Skill Tree CI` — run `34006356029`

Resultado: **SUCCESS**.

Passaram no mesmo HEAD:

- Gradle wrapper contract;
- Core tests;
- geração/wiki + drift checks;
- main-tree content coverage audit;
- JUnit 5;
- NeoForge JUnit adapter tests;
- NeoForge GameTests;
- Battle Mage provider-present GameTests;
- Compendium provenance/inventory/model/modlist/runtime/editorial tests;
- data validation;
- client tree validation;
- node/passive/runtime/attribute/provider validations;
- generated data drift sanity;
- NeoForge build;
- built JAR verification;
- NeoForge dedicated-server smoke.

A documentação de fechamento posterior precisa receber CI novamente antes do merge; este run prova o estado funcional/código, não substitui a verificação do HEAD final da PR.

## Fallbacks e fail-closed finais

### A0052–A0054 — CROSSBOW

A0050 continua `UNAVAILABLE_NODE` porque Epic Fight `21.17.3.1` não fornece binding semântico seguro para o efeito de reload/preparation speed aprovado. A0052–A0054 herdam essa indisponibilidade; não existe ghost rank nem bypass.

A0053/A0054 mantêm reservation→commit e provenance reais, mas não são promovidas a jogáveis enquanto a cadeia não existir. A parcela de reload-speed da A0054 permanece omitida em vez de usar projectile speed/timer heurístico.

### A0058 — heavy-impact/body modulation

Sequence gain, timeout, miss/switch, dedup e lifecycle estão implementados. Reset por heavy-impact recebido só poderá entrar com receipt provider-native inequívoco; exhaustion/fome só com configuração real. Nenhum proxy de Stamina foi criado.

### A0059 — heavy/finalizer/guard-break

Sem receipt inequívoco de heavy/finalizer, a policy não é chamada e não consome Sequência. Guard-break/movement penalty também permanecem inativos sem receipt causal real. Dano alto, animação, timing e Punchy são explicitamente proibidos como classificadores.

### A0060 — heavy/finalizer/Stamina

Sem heavy/finalizer receipt, o capstone não ativa nem consome Sequência/cooldown. O refund de Stamina fica `0.0` sem ledger causal pós-consumo das cinco ações; barra/config/hunger/timing não são estimadores válidos.

## Deduplicação, causalidade e anti-abuso

- Critical resolver: uma resolução/root; A0051/A0057 não criam segunda rolagem.
- CROSSBOW launch provenance: owner/metadata isolados não provam ação; launch receipt correlacionado é obrigatório onde o contrato exige.
- Multishot: siblings compartilham root outcome agregado; `success-wins`; all-fail no máximo uma perda.
- Reservation→commit: A0053/A0054 só debitam Cadência após projectile/root materializado.
- FIST Mastery: `combat:fist` é ledger única; discovery finita, sem `epicfight:fist` paralela.
- Sequence: `claimOnce` por root, cap/janela bounded, lifecycle em rank loss/respec/reload/logout.
- Companion/proc/Backlash/hazard não herdam autoria física do jogador por tema ou ownership indireto.

## Pendências técnicas não bloqueantes registradas

- `P-A0058-01`: heavy-impact recebido provider-native.
- `P-A0058-02`: modulação corporal opcional somente com config/provider real.
- `P-A0059-01`: heavy/finalizer receipt inequívoco.
- `P-A0059-02`: guard-break causal + penalty de movimento.
- `P-A0060-01`: heavy/finalizer receipt inequívoco.
- `P-A0060-02`: ledger causal pós-consumo de Stamina para as cinco ações.
- A0052–A0054: blockers herdados de A0050/availability e componentes explicitamente omitidos.

Essas pendências não comprometem o contrato atualmente executável porque todas as parcelas dependentes permanecem fail-closed. Nenhuma exige redesign neste ciclo; se um provider futuro expuser novos receipts, a habilitação deverá respeitar o contrato existente ou retornar ao Chat 1 caso altere semântica essencial.

## Retorno ao Chat 1

**Nenhum retorno obrigatório ao Chat 1 neste lote.** A correção Multishot não alterou identidade, efeito, provider, gate, dependência, topologia ou authority. As capacidades sem receipt já possuíam fallback/fail-closed aprovado.

## Estado antes do merge

- Código funcional: validado em `6826c508...` / CI `34006356029` GREEN.
- Dossiês A0051–A0060: reconciliados pelo Chat 3.
- P-A0052-04: resolvida.
- Pendências provider-bound: registradas e não ocultadas.
- Próximo gate: CI GREEN do HEAD documental final, revisão dos checks/reviews e merge da PR #387.

O Chat 3 deve parar após merge e confirmação da `main`; **não iniciar A0061+ neste ciclo**.
# Auditoria Chat 3 — A0051–A0060 — Pendências, testes, validação e merge

## Escopo

- **INÍCIO:** A0051
- **FIM:** A0060
- **PR:** #387
- **Branch:** `feat/chat2-a0051-a0060-stacked-handoff`
- **Minecraft:** 1.21.1
- **NeoForge:** `21.1.248`
- **Java:** 21
- **Provider MARTIAL principal:** Epic Fight `21.17.3.1`

O Chat 3 revisou a implementação entregue pelo Chat 2 contra os dossiês aprovados, corrigiu a pendência Multishot A0052 sem redesign, validou causalidade/deduplicação/lifecycle/fail-closed e preservou inativas todas as parcelas para as quais a versão atual do provider não expõe receipt semântico suficiente.

## Modlist fresca e boundaries

A modlist anexada no fechamento possui **607 mods**. Para este lote, a reconciliação fresca confirma:

- Epic Fight `21.17.3.1`;
- Apothic Attributes `2.10.1`;
- Mobstein `5.4.4`;
- Punchy `2.7e`.

O Notion foi rechecado no fechamento e está alinhado para esses providers relevantes. Punchy `2.7e` permanece camada visual/compat e não recebe authority de FIST, heavy/finalizer, guard-break ou Stamina. Nenhuma diferença de modlist autoriza bridge heurística ou substituição de provider.

## Resultado por perk

| Perk | Resultado Chat 3 | Pendência restante |
|---|---|---|
| A0051 — Precisão com Bestas | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma bloqueante |
| A0052 — Cadência de Recarga | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL enquanto A0050 estiver indisponível** | herda A0050 sem binding semântico de reload/preparation speed |
| A0053 — Virote Perfurante | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL** | herda A0050→A0052; Impact component-wise somente com receipt seguro |
| A0054 — Mecanismo Ajustado | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL** | herda cadeia CROSSBOW; reload-speed extra omitido sem hook seguro |
| A0055 — Treino com Armas de Punho I | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma bloqueante |
| A0056 — Treino com Armas de Punho II | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma bloqueante |
| A0057 — Precisão com Armas de Punho | **IMPLEMENTAÇÃO CONFIRMADA** | nenhuma bloqueante |
| A0058 — Sequência Limpa | **IMPLEMENTAÇÃO CONFIRMADA NOS COMPONENTES COM RECEIPT REAL / FALLBACK CANÔNICO** | heavy-impact/body modulation permanecem provider/config-bound |
| A0059 — Quebra de Ritmo | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL** | heavy/finalizer + guard-break receipts provider-native ausentes |
| A0060 — Combinação Final | **FAIL-CLOSED ATUAL CONFIRMADO; NÃO JOGÁVEL** | heavy/finalizer + ledger causal de Stamina ausentes |

`FAIL-CLOSED ATUAL CONFIRMADO` significa que a implementação segura/inativa prevista pelo contrato foi validada; não significa que o efeito dependente do provider esteja disponível.

## P-A0052-04 — Multishot — RESOLVIDA

### Defeito reproduzido

A implementação anterior tratava cada `AbstractArrow` de um Multishot isoladamente. Um sibling que atingisse bloco podia reduzir Cadência antes de outro sibling do mesmo `rootActionId` confirmar hit.

### Correção

Foi introduzido arbiter transiente bounded por `actor + rootActionId` em `A0041A0060CombatState`:

- `registerCrossbowProjectile(...)` registra siblings correlacionados;
- `recordCrossbowProjectileFailure(...)` acumula miss sem settlement prematuro;
- `recordCrossbowProjectileSuccess(...)` fecha o root por sucesso;
- `sealCrossbowRoot(...)` encerra a janela de registro;
- qualquer sibling success vence failures anteriores;
- all-fail liquida no máximo uma perda de Cadência;
- callback duplicado/replay não liquida novamente;
- TTL, reconciliation e lifecycle removem outcomes órfãos.

`A0041A0060ProjectileEvents` liga o arbiter ao runtime real: register no spawn correlacionado, success no dano POST confirmado, failure no impacto não-entidade e seal no encerramento da janela causal.

## Cobertura Chat 3 adicionada

A rodada final de cobertura não reduziu Quality Gate nem adicionou exclusions de produção. Foram adicionados testes comportamentais para:

- crossbow hit receipt vinculado à identidade estável da arma;
- Multishot success-wins/all-fail/seal/duplicate/TTL;
- reservation→commit/rollback/expiry de A0053 e A0054;
- rank reconciliation e lifecycle cleanup;
- provenance de `PendingLaunch` e `ProjectileMeta`;
- classificação BOW/CROSSBOW com itens vanilla já registrados;
- identidade estável de besta no runtime;
- `onEntityJoin` correlacionado versus projétil derivado/não confirmado;
- `onArrowLoose` sem commit prematuro;
- fixtures que dependem de registries executadas no lane NeoForge-loaded, preservando o JUnit plain independente.

A primeira tentativa de bridge coverage falhou por fixture de teste que instanciava novos `BowItem/CrossbowItem` com registries já congelados. O defeito foi isolado como problema de harness, não de runtime; a suíte foi substituída por `Items.BOW`, `Items.CROSSBOW` e demais objetos vanilla já registrados.

## Evidência funcional final antes do commit documental

HEAD validado:

`e0fcc0a178b2560ff3476bb9b7cda1690c5436e8`

### RPG Skill Tree CI

- workflow: `RPG Skill Tree CI`
- run: `34009906068` / #3720
- resultado: **SUCCESS**

No mesmo HEAD passaram:

- Gradle wrapper contract;
- Core tests;
- geração/wiki + drift checks;
- main-tree content coverage audit;
- JUnit 5;
- NeoForge JUnit adapter tests;
- NeoForge GameTests;
- Battle Mage provider-present GameTests;
- Compendium provenance/inventory/model/modlist/runtime/editorial tests;
- data/client/node/passive/runtime/attribute/provider validations;
- generated-data drift sanity;
- NeoForge build;
- built JAR verification;
- NeoForge dedicated-server smoke.

### SonarQube Cloud

- run: `34009906109` / #956
- resultado do workflow: **SUCCESS**
- Quality Gate: **PASSED**
- Coverage on New Code: **81.7%** — gate requerido ≥80%
- Duplication on New Code: **0.0%**
- Security Hotspots: **0**
- Sonar reporta **1 New issue** não bloqueante para o Quality Gate.

O comentário GitHub do Sonar não expõe o detalhe dessa issue. Inspeção do painel externo fica registrada como pendência separada; este chat não usa TinyFish/navegador externo para obtê-la. O gate oficial da PR está aprovado.

## Fallbacks e fail-closed finais

### A0052–A0054 — CROSSBOW

A0050 continua `UNAVAILABLE_NODE` porque Epic Fight `21.17.3.1` não fornece binding semântico seguro para reload/preparation speed. A0052–A0054 herdam a indisponibilidade; não existe ghost rank, gasto sem efeito ou bypass de predecessor.

A0053/A0054 mantêm reservation→commit e provenance reais. A parcela de reload-speed de A0054 permanece omitida em vez de usar projectile speed/timer heurístico.

### A0058 — heavy-impact/body modulation

Sequence gain, timeout, miss/switch, dedup e lifecycle estão implementados. Reset por heavy-impact recebido só poderá entrar com receipt provider-native inequívoco; body modulation somente com config/provider real. Nenhum proxy de Stamina foi criado.

### A0059 — heavy/finalizer/guard-break

Sem receipt inequívoco de heavy/finalizer, a policy não é chamada e não consome Sequência. Guard-break/movement penalty permanecem inativos sem receipt causal real. Dano alto, animação, timing e Punchy são proibidos como classificadores.

### A0060 — heavy/finalizer/Stamina

Sem heavy/finalizer receipt, o capstone não ativa nem consome Sequência/cooldown. O refund de Stamina permanece `0.0` sem ledger causal pós-consumo das cinco ações; barra/config/hunger/timing não são estimadores válidos.

## Deduplicação, causalidade e anti-abuso

- Critical resolver: uma resolução por root; A0051/A0057 não criam segunda rolagem.
- CROSSBOW launch provenance: owner/metadata isolados não provam ação; launch receipt correlacionado é obrigatório.
- Multishot: siblings compartilham root outcome; success-wins; all-fail no máximo uma perda.
- Reservation→commit: A0053/A0054 só debitam após projectile/root materializado.
- FIST Mastery: `combat:fist` é ledger única; discovery finita, sem `epicfight:fist` paralela.
- Sequence: `claimOnce` por root, cap/janela bounded, lifecycle em rank loss/respec/reload/logout.
- Companion/proc/Backlash/hazard não herdam autoria física do jogador por tema ou ownership indireto.

## Pendências técnicas não bloqueantes

- `P-A0058-01`: heavy-impact recebido provider-native.
- `P-A0058-02`: modulação corporal opcional somente com config/provider real.
- `P-A0059-01`: heavy/finalizer receipt inequívoco.
- `P-A0059-02`: guard-break causal + penalty de movimento.
- `P-A0060-01`: heavy/finalizer receipt inequívoco.
- `P-A0060-02`: ledger causal pós-consumo de Stamina para as cinco ações.
- A0052–A0054: blockers herdados de A0050/availability e componentes explicitamente omitidos.
- `P-SONAR-PR387-01`: Sonar aponta 1 New issue não bloqueante; detalhe exige inspeção do painel externo em outro chat autorizado a usar navegador.

Essas pendências não comprometem o contrato executável atual porque todas as parcelas sem receipt permanecem fail-closed. Nenhuma exige redesign neste ciclo.

## Retorno ao Chat 1

**Nenhum retorno obrigatório ao Chat 1 neste lote.** A correção Multishot e os testes de cobertura não alteraram identidade, efeito, provider, gate, dependência, topologia, authority ou semântica aprovada.

## Estado pré-merge

- Código e testes: validados no HEAD `e0fcc0a178b2560ff3476bb9b7cda1690c5436e8`.
- RPG Skill Tree CI #3720 / `34009906068`: **SUCCESS**.
- SonarQube #956 / `34009906109`: **SUCCESS**, Quality Gate **PASSED**, 81.7% New Code coverage.
- Dossiês A0051–A0060: reconciliados pelo Chat 3.
- P-A0052-04: resolvida.
- Pendências provider-bound: registradas e não ocultadas.
- Este commit documental final cria novo HEAD; **merge continua proibido até o novo HEAD receber CI/Sonar verde e a PR ser rechecada quanto a reviews/base/mergeabilidade**.

Após o merge e confirmação da `main`, o Chat 3 deve parar. **A0061+ não deve ser iniciado neste ciclo.**
# A0059 — Quebra de Ritmo

## Estado

- **Design:** APROVADO; fail-closed já estava correto no Notion.
- **Notion:** `3c569db9-f0db-8105-9107-d95706ba3486`.
- **Runtime:** **NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA PELO CHAT 3**. A policy matemática/consumo existe, mas o adapter permanece deliberadamente inerte porque Epic Fight 21.17.3.1 não fornece receipt inequívoco de heavy/finalizer nem guard-break causal suficiente para esta ativação.

## Contrato canônico

- A0058 ≥1 + A0056 ≥2 + gateway `combat_fist`.
- Com ≥3 Sequência, heavy/finalizer inequivocamente reconhecido pode consumir 3 cargas.
- Rank 1/2: +25%/+40% pressão de guarda e +10%/+15% Impact naquele golpe.
- Penalidade −8% movimento por 2 s somente após quebra de guarda/postura realmente confirmada.
- Sem heavy/finalizer seguro, não ativa nem consome.
- Se heavy existir mas guard/posture não, pode manter apenas Impact quando semanticamente disponível.
- Se A0059/A0058/A0056/gateway for invalidado por rank loss, respec ou rules reload, qualquer reserva/estado próprio de ativação deve ser descartado; Sequência é reconciliada pelo owner A0058.

## Evidência runtime

`A0041A0060CombatPolicy.beforeFistHeavy(...)` implementa matemática/consumo, mas o adapter Epic Fight não chama a policy sem receipt server-authoritative inequívoco de heavy/finalizer para a ação concreta. Também não há caminho seguro de aplicação da redução de movimento após guard break.

O Chat 3 confirmou que esse comportamento deve permanecer fail-closed: não é permitido inferir heavy/finalizer por dano, animação, timing, Punchy ou outro sinal visual. O owner A0058 reconcilia Sequência em rank loss/respec/rules reload; como A0059 não arma reserva no estado atual, não existe estado próprio órfão a carregar.

A modlist fresca do fechamento mantém Epic Fight em `21.17.3.1`. Punchy subiu para `2.7e`, mas continua boundary visual/compat e não constitui receipt ou authority de heavy/finalizer/guard-break.

## Pendências técnicas

- **P-A0059-01 — PENDÊNCIA PROVIDER NÃO BLOQUEANTE PARA O MERGE:** integrar heavy/finalizer receipt provider-native seguro quando existir; não usar dano, animação, Punchy ou timing heurístico.
- **P-A0059-02 — PENDÊNCIA PROVIDER NÃO BLOQUEANTE PARA O MERGE:** quando houver ativação, integrar guard-break receipt real e −8% movimento por 2 s; sem guard break, omitir essa parcela.
- **P-A0059-03 — N/A NO ESTADO ATUAL:** nenhum estado/reserva A0059 é criado enquanto o adapter está fail-closed; Sequência já possui lifecycle sob A0058.
- Herdadas de A0055/A0058: gateway/Mastery FIST resolvidos; heavy-impact recebido continua fallback provider-bound de A0058.

## Implementação e validação

- [x] Design aprovado pelo Chat 1.
- [x] Código presente pelo Chat 2.
- [x] Contrato revisado contra o código pelo Chat 3.
- [x] Policy de consumo/efeito existente e preservada.
- [x] Provider-native first preservado: ausência de receipt não é substituída por heurística.
- [x] Gate/dependências preservados.
- [x] Fail-closed do adapter confirmado sem consumo de Sequência quando o receipt não existe.
- [x] Deduplicação/lifecycle do recurso-base permanecem sob A0058.
- [x] **VALIDAÇÃO CHAT 3:** JUnit/core e regressões do lote verdes.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge JUnit adapter e GameTests verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **IMPLEMENTAÇÃO CONFIRMADA DO COMPORTAMENTO FAIL-CLOSED ATUAL**.
- [ ] **IMPLEMENTAÇÃO CONFIRMADA COMO JOGÁVEL:** depende de receipt provider-native futuro para heavy/finalizer e, para a parcela correspondente, guard-break.

## Boundaries

`ARCANE_BACKLASH`, hazard, companion-owned attack e estados Shroud/Arcane não qualificam heavy/finalizer, guard break ou Impact FIST. Punchy é apresentação/compatibilidade e não pode ser usado como provider causal.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design e runtime fail-closed** | A0058 ≥1 + A0056 ≥2 + `combat_fist`; sem heavy/finalizer receipt a ativação não consome Sequência. |
| 2. Integração global | **PASS** | Heavy/guard-break/Impact permanecem provider-native; não usa dano alto, Backlash, Shroud, hazard ou companion como heurística. |
| 3. Qualidade e identidade | **PASS** | Notable condicionado a Sequência + golpe especial. |
| 4. Ramificação, distância e topologia | **PASS** | Convergência A0058 + A0056 no ramo FIST. |
| 5. Especializações | **PASS** | MARTIAL/ARMAS_DE_PUNHO. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Gate/Hook/Fallback/Regra expressam fail-closed correto. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight permanece authority; Punchy/Mobstein/own-projects não foram promovidos artificialmente a receipts de combate. |

## Notion

O contrato de fail-closed já estava persistido e não exigiu redesign ou mutação funcional pelo Chat 3.

## Fechamento Chat 3 — PR #387

O comportamento aplicável foi validado no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`, cuja bateria passou JUnit 5, NeoForge JUnit adapter, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke. A0059 permanece **NÃO CONFIRMADA COMO JOGÁVEL**, mas seu **FAIL-CLOSED ATUAL ESTÁ IMPLEMENTADO E CONFIRMADO**; as pendências de provider não autorizam heurística e não bloqueiam o merge desta implementação segura.
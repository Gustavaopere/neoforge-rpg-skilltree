# A0094 — Recuperação de Guarda

## Estado

- **Chat 1:** DESIGN APROVADO com **UNAVAILABLE_NODE / FAIL-CLOSED estrutural e transitivo**.
- **Notion:** `3c569db9-f0db-81f2-810a-fdfe37dbcdf8`; fetch fresco no ciclo.
- **Domínio:** VITALITY ↔ MARTIAL; Camada 2; Ramo Guarda e Estamina.
- **Ranks:** 4; custo 1 PP/rank.
- **Dependência:** A0093 Guarda Econômica ≥2.

## Contrato canônico

- Quando houver contrato real, +3% por rank na **velocidade de recuperação do estado nativo causado por uma quebra de guarda real**, até +12%.
- Exige simultaneamente: receipt causal de `GUARD_BREAK` + extension point seguro da recuperação/duração daquele estado.
- A perk não apaga a penalidade de ter a guarda quebrada e não converte o efeito em stamina refund.

## Provider / authority

- Epic Fight 21.17.3.1 é owner de guard-break e recuperação.
- A0094 não pode inferir guard-break por animação, knockback, dano, som, falta de stamina ou resultado genérico BLOCKED.
- RPG Skill Tree só modifica recovery quando ambos os lados do contrato estiverem disponíveis.
- Stage 04.02 continua owner de custo/provenance de confluência; A0094 não cobra/reembolsa bridge de classe.

## Availability transitiva

- A0093 está indisponível no snapshot auditado; portanto A0094 também deve ser não comprável mesmo antes de avaliar o segundo binding.
- Além disso, falta o contrato próprio de recovery pós-guard-break.
- `requirementsSatisfied=false` deve ser imposto server-side enquanto qualquer predecessor/binding obrigatório estiver indisponível.

## Fallback / fail-closed

- Sem fallback mecânico.
- Proibido reduzir duração de animação genericamente, criar cooldown sintético, restaurar stamina, usar knockback/stagger como proxy ou aceitar guard-break sem receipt causal.

## Evidência e pendências para Chat 2

- `A0081A0100CombatPolicy.guardRecoveryMultiplier` é apenas fórmula pura condicionada por booleano hipotético.
- `A0081A0100CombatEvents` mantém A0094 fail-closed, mas não implementa availability de compra.
- **P-A0094-01:** availability transitiva A0093→A0094 no purchase/gate.
- **P-A0094-02:** manter o node indisponível também por falta de receipt GUARD_BREAK + recovery extension point; não inventar adapter.

## Dedup / lifecycle

- Uma quebra causal pode iniciar no máximo uma recovery modificada.
- State/receipt futuro precisa ser bounded e limpo em logout/death/respawn/dimension/rank loss/respec/rules reload.
- Nenhum producer de Mastery/recurso.

## Testes obrigatórios Chat 3

1. A0093 indisponível torna A0094 não comprável;
2. A0093 hipoteticamente disponível, mas sem recovery hook: A0094 continua indisponível;
3. nenhum PP/rank em tentativas rejeitadas;
4. fórmula 103/106/109/112% somente com contrato causal verdadeiro;
5. animação/knockback/stamina baixa/resultado BLOCKED não ativam;
6. future provider fixture: GUARD_BREAK + recovery correlacionados aplicam uma vez;
7. lifecycle/rollback/reload limpa receipts transitórios;
8. bridge class provenance Stage04.02 permanece intocada.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0093≥2 + provider binding; transitivamente unavailable hoje. |
| Integração global | PASS | Epic Fight permanece authority. |
| Qualidade/identidade | PASS | Recovery de guard-break real. |
| Topologia | PASS | Camada 2 atrás da bridge de guarda. |
| Especializações | PASS | Bridge semântico, sem custo paralelo. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Contrato completo. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS/FAIL-CLOSED | Sem contrato suportado; indisponibilidade aprovada. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [ ] P-A0094-01 availability transitiva implementada
- [ ] P-A0094-02 provider hook seguro implementado ou fail-closed preservado
- [ ] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: testes provider/purchase/lifecycle
- [ ] VALIDAÇÃO CHAT 3: GameTests/build/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA

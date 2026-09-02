# A0093 — Guarda Econômica

## Estado

- **Chat 1:** DESIGN APROVADO com **UNAVAILABLE_NODE / FAIL-CLOSED estrutural**.
- **Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3 / EM FAIL-CLOSED**.
- **Implementação:** availability server-authoritative impede rank efetivo/uso enquanto faltar binding causal suportado; nenhum efeito substituto foi criado.
- **Notion:** `3c569db9-f0db-8157-9ee9-d1516f495b51`; fetch fresco no ciclo.
- **Domínio:** VITALITY ↔ MARTIAL; Camada 1; Função Ponte.
- **Ranks:** 5; custo 1 PP/rank.

## Contrato canônico

- Gateway VITALITY + acesso semântico real ao corredor de guarda MARTIAL.
- Quando houver provider suportado: −2% por rank do **custo real de stamina da mesma ação de guarda**, máximo −10%.
- A modificação ocorre antes do débito nativo; nunca via reembolso pós-consumo.
- A ação nunca se torna gratuita.

## Provider / authority

- **Epic Fight 21.17.3.1** é owner da guarda e stamina.
- O código auditado contém cálculo interno de custo, mas não foi comprovado um extension point público/server-authoritative estável para interceptar o débito causal da guarda na versão instalada.
- RPG Skill Tree só pode ser consumer/modifier quando existir adapter suportado; não cria stamina paralela.
- Stage 04.02 do RPG é owner de provenance/custo de confluências pagas. O fato de A0093 ser Bridge não autoriza cobrar/reembolsar bridge de classe por este node.

## Gate de indisponibilidade

- Enquanto o binding obrigatório estiver ausente, o cálculo server-authoritative de disponibilidade trata A0093 como `UNAVAILABLE_NODE`.
- Rank efetivo não é exposto ao runtime e a perk não produz efeito.
- O caminho de aquisição deve rejeitar a compra sem gasto; a prova comportamental completa fica para o Chat 3.
- UI deve refletir indisponibilidade/razão de provider quando a superfície de apresentação permitir.

## Fallback / fail-closed

- **Sem fallback mecânico.**
- Proibido: estimar custo por impacto/animação; reembolsar stamina depois; usar hunger/exhaustion; reduzir custo de dodge/skill não correlacionada; criar stamina própria.

## Evidência após Chat 2

- `CombatPerkAvailabilityRuntime`/pipeline de ranks efetivos foi estendido para manter A0093 indisponível.
- `A0081A0100CombatPolicy.guardCostMultiplier` permanece fórmula pura condicionada a contrato causal; não é usado como autorização para um binding inexistente.
- O runtime não introduz mixin interno, refund, stamina paralela nem heurística para simular guarda.
- A pendência de provider permanece deliberadamente aberta para futuro contrato versionado; qualquer mudança semântica volta ao Chat 1.
- O Chat 2 **não executou** testes de purchase/provider, GameTests, build, dedicated-server smoke ou CI.

## Dedup / authority

- Uma guarda causal = no máximo uma modificação do custo.
- Se múltiplos adapters observarem a mesma ação, precisam compartilhar identidade causal e convergir antes do débito.
- Nenhum Mastery/recurso é produzido por A0093.

## Testes obrigatórios para Chat 3

1. Epic Fight ausente/incompatível: node não comprável, nenhum PP gasto;
2. Epic Fight 21.17.3.1 sem binding suportado: mesmo resultado indisponível;
3. fórmula pura preserva 98/96/94/92/90% apenas quando `causalGuardCostContract=true`;
4. nenhum refund pós-consumo ocorre;
5. dodge/skill/stamina não correlacionada não recebe desconto;
6. purchase replay/idempotência não gasta PP em node indisponível;
7. bridge PP não interfere na provenance de confluência Stage 04.02;
8. provider futuro simulado: uma ação/um desconto, nunca gratuito.

## Nove eixos

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Gate de provider + bridge semântica; indisponível hoje. |
| Integração global | PASS | Epic Fight mantém stamina. |
| Qualidade/identidade | PASS | Eficiência defensiva real, não bônus genérico. |
| Topologia | PASS | Bridge VITALITY↔MARTIAL. |
| Especializações | PASS | Bridge PP não duplica thresholds/custos. |
| PT-BR | PASS | Consistente. |
| Notion | PASS | Contrato explícito. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS/FAIL-CLOSED | Sem hook público seguro; indisponibilidade é o comportamento aprovado. |

## Checklist

- [x] Design aprovado pelo Chat 1
- [x] Fail-closed de design definido
- [x] Unavailable-node integrado ao runtime/purchase gate do Chat 2
- [x] Código presente / Chat 2 concluído
- [ ] VALIDAÇÃO CHAT 3: provider-absent/incompatible/purchase tests
- [ ] VALIDAÇÃO CHAT 3: build/GameTest/dedicated server/CI
- [ ] VALIDAÇÃO CHAT 3: IMPLEMENTAÇÃO CONFIRMADA

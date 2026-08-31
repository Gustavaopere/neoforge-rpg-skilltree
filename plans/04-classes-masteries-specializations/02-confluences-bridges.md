# Classes Plan — Confluences and Bridges

**Goal:** formalizar combinações adjacentes e distantes como caminhos de progressão verificáveis.

- [x] Validar confluências naturais sem custo de bridge extra quando definido.
- [x] Validar confluências distantes e custo de bridge de 10 pontos onde os dados exigem.
- [x] Fazer UI mostrar pontos faltantes e requisitos de ambos os domínios.
- [x] Recalcular disponibilidade após respec.
- [x] Testar todas as classes híbridas data-driven.

## Contrato fechado

- Confluências naturais continuam automáticas quando a definição atual é adjacente e possui custo de bridge `0`.
- Confluências distantes usam o custo declarado pelos dados; no catálogo atual, todas as classes híbridas pagas exportadas exigem **10 pontos**.
- Nenhuma classe híbrida pode desbloquear com apenas parte dos `required_completed_domains`; os testes percorrem as definições atuais carregadas por datapack em vez de manter uma lista Java paralela.
- A UI mantém confluências pagas ainda incompletas visíveis e distingue explicitamente domínio pendente, pontos de bridge pendentes e estado pronto para liberação.
- O pagamento de bridge possui proveniência persistida junto ao estado de classes. Uma ativação cobra a bridge uma única vez; chamadas repetidas enquanto a classe permanece ativa não cobram novamente.
- Se respec ou outra reconciliação derivada quebrar um domínio/requisito exigido, a classe paga é revogada pelo mesmo loop autoritativo de `PlayerProgressionRuntime`. Somente uma bridge cuja proveniência de pagamento esteja registrada é reembolsada, e a marca é removida com a classe.
- O round-trip do codec de compatibilidade preserva a proveniência da bridge por uma nova cauda opcional do payload v4 e continua aceitando snapshots legados v1–v4 que não possuem esse campo.

**Acceptance:** nenhuma classe híbrida desbloqueia com apenas metade do caminho e o custo de bridge é aplicado uma única vez por ativação válida.

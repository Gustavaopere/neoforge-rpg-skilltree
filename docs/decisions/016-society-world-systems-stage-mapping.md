# ADR 016 — Mapeamento dos Stages 14–20 no MASTER PLAN

Status: Accepted
Date: 2026-08-30

## Context

`docs/MASTER_PLAN.md` continua sendo o plano canônico de execução arquitetural e organiza estabilização do projeto nas Phases 0–9. Os Stages 14–20 foram criados depois como planos funcionais de sociedade e sistemas de mundo. Sem um mapeamento explícito, a sequência interna `14 → 15 → ... → 20` poderia ser interpretada como autorização para ignorar blockers das Phases 0–7.

Este ADR resolve essa ambiguidade. Ele não altera os objetivos das Phases 0–9 e não torna nenhum Stage novo “pronto para implementação” apenas porque o plano existe.

## Decision

### Regra de precedência

`docs/MASTER_PLAN.md` permanece autoridade sobre **quando** expansão arquitetural/conteúdo pode começar. Os Stages em `plans/` descrevem **o que** deve ser construído e sua ordem causal interna.

A sequência dos novos planos é subordinada a:

```text
MASTER PLAN Phases 0–4: fundação obrigatória
        ↓
Phase 5–6 quando o Stage consumir progressão/UI correspondentes
        ↓
Phase 7 para qualquer integração provider-facing relevante
        ↓
Phase 8: implementação/expansão de conteúdo e sistemas novos
        ↓
Phase 9: release readiness
```

Planejamento e documentação podem existir antes desses gates; implementação de gameplay não os contorna.

### Stage 02.06 — eligibility de scaling

- É uma correção/hardening do runtime de world scaling existente, não um novo lote de conteúdo.
- Pode ser implementado quando a infraestrutura das Phases 0–4 necessária ao change estiver verde.
- O adapter MineColonies deve seguir os gates da Phase 7 para provider isolation/version/runtime evidence.
- Deve fechar antes de qualquer Stage 18/20 que dependa de classificação correta de civis/guards/raiders.

### Stage 14 — building/blueprint pipeline

- O núcleo puro de `VoxelModel`, transforms, BOM e exporters independentes pode ser implementado depois dos gates fundamentais 0–4, pois é infraestrutura de tooling/conteúdo.
- Adapters Create, Structurize e MineColonies obedecem Phase 7.
- O pipeline deve estar operacional antes dos prédios físicos do Stage 18 e da Central Térmica do Stage 19.

### Stage 15 — administrative districts

- Requer as garantias de persistência/reload/network das Phases 0–4.
- Reutiliza Stage 13 para apresentação/bridge, sem alterar autoridade de regiões naturais.
- É pré-requisito de políticas distritais, economia territorial e heat allocation dos Stages 16, 17 e 19.
- A implementação live é tratada como expansão da Phase 8.

### Stage 16 — colony economy & society

- Requer Phases 0–4 e Stage 15 para contexto territorial completo.
- Bridges MineColonies pertencem ao hardening provider da Phase 7.
- O sistema live faz parte da expansão Phase 8.
- Deve preceder Stage 17 em tudo que depende de fatos econômicos reais, e Stage 18 em comércio/salários.

### Stage 17 — government, laws & institutions

- Requer Stage 16 para impostos, riqueza, propriedade, salários e tesouro.
- Requer Stage 15 para overrides territoriais.
- Qualificações que dependam de masteries/classes seguem Phase 5; UI política segue princípios da Phase 6.
- Integrações mágicas/tecnológicas provider-facing seguem Phase 7.
- Gameplay live entra na Phase 8.

### Stage 18 — MineColonies civic/commercial buildings

- Requer Stage 14, Stage 16 e Stage 17.
- É explicitamente provider-facing e só deve implementar a bridge MineColonies após os gates da Phase 7.
- Constitui expansão de conteúdo da Phase 8.

### Stage 19 — extreme winter & district heating

- Requer Stage 14 para Central Térmica, Stage 15 para distritos, Stage 16 para combustível/orçamento, Stage 17 para leis de emergência e Stage 18 para prédios/cidadãos quando aplicável.
- Adapters MineColonies, Cold Sweat, Create e clima seguem Phase 7.
- Gameplay live entra na Phase 8.

### Stage 20 — realms, diplomacy, war & rebellion

- Requer Stage 02.06 para semântica correta de combatentes/civis.
- Requer Stage 15–17 para território, economia e governo.
- Materialização MineColonies/guards/raiders segue Phase 7 e Stage 18.
- Simulação abstrata não autoriza bypass dos gates de persistência/performance das Phases 0–4.
- Gameplay live entra na Phase 8.

### Gate de release

Nenhum Stage 14–20 é considerado “release ready” fora da Phase 9. Cada Stage pode ter seu próprio acceptance, mas isso não substitui license/provider/save/performance/release gates globais.

## Ordem causal interna aprovada

Depois dos gates do MASTER PLAN aplicáveis:

```text
02.06
→ 14
→ 15
→ 16
→ 17
→ 18
→ 19
→ 20
```

Isso é uma ordem de dependência entre os novos sistemas, não uma nova ordem superior às Phases 0–9.

## Alternatives considered

### Tratar Stages 14–20 como novas Phases 10–16

Rejeitado. Misturaria dois níveis de planejamento e permitiria interpretar a numeração de conteúdo como sucessora automática da release Phase 9.

### Reescrever `docs/MASTER_PLAN.md` inteiro

Rejeitado nesta rodada. Os objetivos atuais das Phases 0–9 continuam válidos; o problema era apenas a relação entre o master plan e os novos stages.

### Permitir que cada Stage decida sozinho quando começar

Rejeitado. Criaria exatamente a competição de autoridade que este ADR elimina.

## Consequences

- `docs/MASTER_PLAN.md` continua canônico para sequencing arquitetural.
- Stages 14–20 podem ser planejados agora, mas sua implementação obedece aos gates acima.
- Adapters de MineColonies/Create/Cold Sweat e outros providers não entram antes do padrão Phase 7 correspondente.
- Phase 8 passa a ser o umbrella de implementação live desses sistemas novos depois que seus pré-requisitos estiverem satisfeitos.
- `plans/SOCIETY-WORLD-SYSTEMS-COVERAGE.md` permanece a matriz de cobertura da discussão, não um master plan concorrente.

## Migration/compatibility impact

Nenhuma alteração de save/runtime nesta ADR. Cada Stage mantém seu próprio schema/migration plan. O único efeito é governança de execução.

## Tests/verification required

- PRs futuros dos Stages 14–20 devem citar este ADR e os gates do MASTER PLAN aplicáveis.
- Review deve bloquear implementação provider-facing que pule Phase 7.
- Review deve bloquear expansão live antes das fundações 0–4 necessárias estarem satisfeitas.
- Release review deve exigir Phase 9 independentemente do acceptance local do Stage.
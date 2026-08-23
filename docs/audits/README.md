# Histórico de Auditorias

Este diretório preserva auditorias técnicas do projeto como **snapshots históricos**. Uma auditoria não se torna automaticamente a especificação vigente do projeto.

## Política

- Cada auditoria deve registrar data, branch e commit auditado.
- Achados devem permanecer vinculados ao snapshot em que foram observados.
- Recomendações ainda não consolidadas não devem ser tratadas como regras definitivas apenas por estarem neste diretório.
- Quando houver uma nova auditoria, os resultados devem ser comparados com as auditorias anteriores.
- Pontos de concordância ganham confiança; divergências devem ser verificadas contra o código atual e fontes específicas de NeoForge 1.21.1.
- O plano canônico futuro deve ser mantido fora do histórico de auditorias, em documentação própria, após consolidação.

## Auditorias

### 2026-08-23 — Baseline sem Minecraft Skills

Diretório: [`2026-08-23-baseline-no-minecraft-skills/`](./2026-08-23-baseline-no-minecraft-skills/)

Snapshot auditado:

- branch: `main`
- commit: `31377faa79685565b683923e9d8e2e62db073c92`
- Minecraft: `1.21.1`
- NeoForge: `21.1.248`
- Java: `21`

Esta auditoria utilizou GitHub, DeepWiki, documentação oficial NeoForge/Mojang, inspeção local e metodologias Superpowers. As Minecraft Skills especializadas não estavam disponíveis na sessão em que ela foi produzida.

Ela foi preservada como **Auditoria A / baseline independente** para comparação posterior com uma auditoria executada com as Minecraft Skills realmente carregadas.

Arquivos:

1. [`01-scope-architecture-blockers.md`](./2026-08-23-baseline-no-minecraft-skills/01-scope-architecture-blockers.md) — escopo, inventário, arquitetura, estado da implementação e bloqueadores concretos.
2. [`02-technical-audit-and-recommended-architecture.md`](./2026-08-23-baseline-no-minecraft-skills/02-technical-audit-and-recommended-architecture.md) — auditoria técnica detalhada por área e arquitetura recomendada.
3. [`03-master-plan.md`](./2026-08-23-baseline-no-minecraft-skills/03-master-plan.md) — plano mestre em fases 0–9.
4. [`04-rules-checklist-decisions-handoff.md`](./2026-08-23-baseline-no-minecraft-skills/04-rules-checklist-decisions-handoff.md) — regras permanentes propostas, checklist, decisões pendentes, handoff e ferramentas utilizadas.

## Próxima etapa prevista

Quando a auditoria com Minecraft Skills estiver pronta:

1. arquivá-la em um novo diretório, sem sobrescrever esta baseline;
2. comparar achados, prioridades, APIs e arquitetura;
3. verificar conflitos contra o estado atual do repositório e fontes NeoForge 1.21.1;
4. produzir uma auditoria consolidada;
5. somente então promover decisões confirmadas para documentação canônica (`AGENTS.md`, plano mestre, arquitetura, testes e ADRs).

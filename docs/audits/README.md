# Histórico de Auditorias

Este diretório preserva auditorias técnicas do projeto como **snapshots históricos**. Uma auditoria não se torna automaticamente a especificação vigente do projeto.

## Política

- Cada auditoria registra data, branch e commit auditado.
- Achados permanecem vinculados ao snapshot em que foram observados.
- Recomendações históricas não são regras vigentes por si só.
- Auditorias posteriores devem ser comparadas com as anteriores.
- Divergências são resolvidas contra o código atual e fontes específicas de Minecraft/NeoForge 1.21.1.
- Após consolidação, a documentação operacional fica fora do histórico de auditorias.

---

## Auditoria A — Baseline sem Minecraft Skills

Diretório: [`2026-08-23-baseline-no-minecraft-skills/`](./2026-08-23-baseline-no-minecraft-skills/)

Snapshot:

- branch: `main`
- commit: `31377faa79685565b683923e9d8e2e62db073c92`
- Minecraft: `1.21.1`
- NeoForge: `21.1.248`
- Java: `21`

Produzida com GitHub, DeepWiki, documentação oficial NeoForge/Mojang, inspeção local e metodologias Superpowers, mas sem as Minecraft Skills especializadas carregadas na sessão.

Arquivos:

1. [`01-scope-architecture-blockers.md`](./2026-08-23-baseline-no-minecraft-skills/01-scope-architecture-blockers.md)
2. [`02-technical-audit-and-recommended-architecture.md`](./2026-08-23-baseline-no-minecraft-skills/02-technical-audit-and-recommended-architecture.md)
3. [`03-master-plan.md`](./2026-08-23-baseline-no-minecraft-skills/03-master-plan.md)
4. [`04-rules-checklist-decisions-handoff.md`](./2026-08-23-baseline-no-minecraft-skills/04-rules-checklist-decisions-handoff.md)

---

## Auditoria B — Com Minecraft Skills

Diretório: [`2026-08-23-with-minecraft-skills/`](./2026-08-23-with-minecraft-skills/)

Snapshot:

- branch: `main`
- commit: `87a8ef224af52e1a613bce892a5f3e6732691466`
- Minecraft: `1.21.1`
- NeoForge: `21.1.248`
- Java: `21`

Produzida com uso efetivo de:

- `minecraft-modding`;
- `minecraft-mod-dev`;
- `minecraft-testing`;
- `minecraft-ci-release`;
- Superpowers;
- GitHub;
- DeepWiki;
- documentação/fontes NeoForge 1.21.1.

O snapshot B está 66 commits à frente do snapshot A e inclui o merge da fundação do sistema. Por isso, alguns problemas da Auditoria A são históricos ou foram parcialmente corrigidos antes da B.

Arquivos:

- [`README.md`](./2026-08-23-with-minecraft-skills/README.md)
- [`FULL_AUDIT.md`](./2026-08-23-with-minecraft-skills/FULL_AUDIT.md) — texto integral da Auditoria B.

---

## Auditoria Consolidada — A × B + verificação do `main`

Diretório: [`2026-08-23-consolidated/`](./2026-08-23-consolidated/)

Arquivo:

- [`README.md`](./2026-08-23-consolidated/README.md)

A consolidação:

- compara A e B;
- considera os 66 commits de diferença entre os snapshots;
- verifica diretamente no `main` os achados críticos;
- marca itens como confirmados, parcialmente corrigidos, obsoletos ou ainda pendentes;
- define a ordem consolidada de execução.

Exemplos de classificação feita na consolidação:

- **confirmado:** tag de bosses ainda no caminho plural incorreto;
- **confirmado:** 34 referências vanilla de atributos com IDs incompatíveis com o alvo 1.21.1;
- **confirmado:** reconcile de nó removido continua estruturalmente problemático;
- **confirmado:** regras cliente/servidor podem divergir;
- **confirmado:** XP/mastery frequente ainda provoca refresh total de atributos + sync do estado;
- **parcialmente corrigido:** preservação das três especializações migradas;
- **obsoleto como blocker atual:** antigo PR de fundação vermelho, posteriormente corrigido e mesclado.

---

# Documentação canônica atual

Depois da consolidação, estes são os documentos operacionais que futuros chats/agentes devem seguir:

- [`/AGENTS.md`](../../AGENTS.md) — contrato permanente para agentes, invariantes, versão alvo, workflow e blockers verificados.
- [`/docs/MASTER_PLAN.md`](../MASTER_PLAN.md) — plano mestre consolidado e ordenado por dependências.
- [`/docs/TESTING.md`](../TESTING.md) — estratégia de testes e gates de merge.
- [`/docs/decisions/README.md`](../decisions/README.md) — decisões arquiteturais ainda abertas e modelo de ADR.

A documentação histórica em `docs/audits/` deve ser mantida para rastreabilidade, mas não substitui esses documentos canônicos.

## Próxima etapa de desenvolvimento

A execução deve começar pela **Fase 0** de `docs/MASTER_PLAN.md`: baseline reproduzível/testável e correções P0 específicas de Minecraft/NeoForge 1.21.1, antes de expansão de conteúdo.
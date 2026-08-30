# A0044 — Treino com Arcos II

## Estado

- **Design:** APROVADO após correção de availability/fail-closed no review da PR #243.
- **Implementação:** NÃO CONFORME com o fail-closed canônico; o efeito está sem consumer seguro, mas o nó continua comprável no `CombatPerkTreeModel`.
- **Notion:** `3c569db9-f0db-81d4-9c22-c7fc0ebcd482`; corrigido e re-fetch PASS em 2026-08-30.

## Contrato canônico

- A0043 ≥2 + gateway `epic_bow` + binding server-authoritative válido de draw/preparation speed.
- +2% de ritmo efetivo de preparo/disparo com arcos por rank, até +6%, somente quando provider expuser parâmetro server-authoritative com essa semântica.
- Projectile speed, movimento, stamina, dano, tooltip ou manipulação de animação não são substitutos.
- **Sem binding válido, A0044 é explicitamente INDISPONÍVEL/NÃO COMPRÁVEL:** nenhum ponto pode ser gasto e nenhum rank pode ser adquirido como no-op.
- Enquanto A0044 estiver indisponível, dependências que exigem seus ranks não podem ser satisfeitas; A0047 permanece bloqueada. Não criar bypass runtime ad hoc.

## Evidência runtime

- Catálogo/ruleset/topologia contêm A0044.
- Busca no runtime A0041–A0060 não encontra consumer de A0044 que modifique draw/preparation time.
- `A0041A0060ProjectileEvents` manipula propriedades de disparo/projétil, mas não apresenta API semântica de velocidade de preparo do arco.
- `CombatPerkTreeModel` ainda publica A0044 como `Node` normal com custo/ranks/dependências; `SkillTreeDataLoader.closedCombatRules()` projeta esse nó para `NodePurchaseDefinition`/`NodeAccessRequirement` sem availability gate de provider.
- `ProgressionService.purchaseNode(...)` só conhece `requirementsSatisfied`; portanto, no estado atual, ausência do provider não produz automaticamente o estado explícito de indisponibilidade exigido pelos invariantes 16 e 24 de `AGENTS.md`.

## Provider→árvore

- Nenhum dos providers retroauditados fornece draw speed seguro para este contrato.
- Stage 11.01 de itemização não possui projeção de efeito que autorize esta cadência.
- Volcanoes/Enshrouded/Black Arcana/Mobstein não são providers de preparação de arco.

## Pendência Chat 2

### P-A0044-01 — availability gate server-authoritative

Adicionar um estado explícito de disponibilidade de nó/binding que seja avaliado no catálogo server-authoritative antes da compra. Enquanto nenhum provider de draw/preparation speed compatível estiver validado, A0044 deve aparecer/ser tratada como indisponível e `purchaseNode` não pode gastar pontos nem criar rank. A indisponibilidade deve propagar naturalmente para A0047 por sua dependência estrutural de A0044 ≥2.

Não corrigir com efeito alternativo, custo zero, rank fantasma, projectile speed, mixin frágil ou bypass de dependência.

## Testes exigidos

- provider ausente → A0044 não comprável e nenhum ponto gasto;
- provider incompatível → indisponível com diagnóstico;
- provider presente → rank 1/2/3 comprável e efeito real +2/+4/+6%;
- A0047 bloqueada enquanto A0044 indisponível;
- nenhum dano/stamina/movement/projectile-speed fallback;
- dedicated server e projeção cliente coerente.

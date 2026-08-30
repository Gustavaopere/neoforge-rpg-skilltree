# A0050 — Treino com Bestas II

## Estado

- **Design:** APROVADO após correção de availability/fail-closed no review da PR #243.
- **Implementação:** NÃO CONFORME com o fail-closed canônico; o efeito está sem consumer seguro, mas o nó continua comprável no `CombatPerkTreeModel`.
- **Notion:** `3c569db9-f0db-812f-9e64-ca806740e883`; corrigido e re-fetch PASS em 2026-08-30.

## Contrato canônico

- A0049 ≥2 + gateway `epic_crossbow` + binding server-authoritative válido de reload/preparation speed.
- +2% de ritmo efetivo de recarga/preparo com bestas por rank, até +6%, somente quando provider expuser parâmetro server-authoritative com essa semântica.
- Projectile speed, mobilidade, stamina, dano, tooltip ou manipulação de timers por heurística não são substitutos.
- **Sem binding válido, A0050 é explicitamente INDISPONÍVEL/NÃO COMPRÁVEL:** nenhum ponto pode ser gasto e nenhum rank pode ser adquirido como no-op.
- Dependências posteriores que exijam A0050 não podem ser satisfeitas enquanto o nó estiver indisponível. O lote A0051+ deve tratar seus próprios gates quando for auditado; Chat 2 não cria bypass ad hoc.

## Evidência runtime

- Catálogo/ruleset/topologia contêm A0050.
- `A0041A0060ProjectileEvents.tickCrossbow(...)` observa estado carregado/uso para perks posteriores, mas não implementa modifier semântico de reload/preparation speed de A0050.
- Nenhum provider retroauditado fornece API segura de reload speed para esta perk.
- `CombatPerkTreeModel` publica A0050 como `Node` normal e `SkillTreeDataLoader.closedCombatRules()` o converte em regra comprável sem availability gate de provider.
- `ProgressionService.purchaseNode(...)` não recebe disponibilidade de binding como conceito próprio; assim, o estado atual viola os invariantes de `AGENTS.md` que proíbem compra silenciosa de nó inútil quando provider obrigatório está ausente.

## Provider→árvore

- RPG Skill Tree permanece consumer apenas quando houver hook real; Stage 11 itemização não fornece reload projection.
- Volcanoes, Enshrouded, Black Arcana e Mobstein não são providers de recarga da besta.
- WoM/itens externos só participam se explicitamente classificados CROSSBOW; classificação não cria reload-speed API.

## Pendência Chat 2

### P-A0050-01 — availability gate server-authoritative

Adicionar estado explícito de disponibilidade/binding para A0050. Sem provider compatível de reload/preparation speed, o nó deve permanecer indisponível/não comprável, sem gasto de pontos e sem rank fantasma. Quando o provider existir, validar versão/semântica antes de liberar a compra.

Não corrigir com projectile speed, dano, Stamina, custo zero, manipulação heurística de timers ou bypass de dependências posteriores.

## Testes exigidos

- provider ausente → A0050 não comprável e nenhum ponto gasto;
- provider incompatível → indisponível com diagnóstico;
- provider presente → rank 1/2/3 comprável e redução real de tempo de recarga/preparo +2/+4/+6%;
- dependências posteriores permanecem insatisfeitas enquanto A0050 estiver indisponível;
- nenhum efeito em projectile speed/dano/Stamina;
- mainhand/offhand, multiplayer e dedicated server.

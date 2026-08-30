# Chat 1 — Checklist de listagem de projetos próprios em perks

Use este checklist depois de auditar o efeito da perk e antes de fechar `Provider/Mods`.

Para cada um dos quatro projetos próprios potencialmente relacionado, responder:

1. **Relação:** provider principal, consumer secundário, bridge, gate, recurso, read-only, hazard, equipamento, Mastery/progressão, não aplicável ou fail-closed?
2. **Estado:** o contrato necessário está canônico em `main`, parcial, preparatório, planejado ou bloqueado?
3. **Authority:** qual projeto é dono da decisão/estado?
4. **Boundary:** qual API, serviço, hook, query, event ou provider concreto será usado?
5. **Versão/evidência:** qual versão/SHA/plano fechado comprova esse boundary?
6. **Causalidade:** qual ação discreta do jogador justifica proc/Mastery/reward?
7. **Deduplicação:** qual identidade impede processar a mesma ação mais de uma vez?
8. **Fallback:** a identidade da perk continua preservada sem o provider opcional?
9. **Fail-closed:** o que fica inativo quando o hook não é seguro ou não existe?
10. **Proibição:** qual estado/pipeline do provider a perk explicitamente não deve duplicar ou escrever diretamente?

## Como preencher `Provider/Mods`

Não escrever apenas `RPG Skill Tree`, `Volcanoes`, `Enshrouded` ou `Black Arcana` quando o uso real puder ser especificado.

Preferir formas semanticamente precisas, por exemplo:

- `RPG Skill Tree — NodeEffectRuntime / Core Progression`;
- `Volcanoes — GeologicalDepositSource (read-only)`;
- `Enshrouded — ShroudQuery + FlamePassageQuery`;
- `Black Arcana — Arcane Danger / Arcane Backlash provenance`.

Quando a relação for futura, registrar explicitamente `PLANEJADO — sem hook implementável no snapshot`; não mascarar como provider atual.

A resposta deste checklist deve ser incorporada ao dossiê individual da perk quando qualquer projeto próprio for pertinente.

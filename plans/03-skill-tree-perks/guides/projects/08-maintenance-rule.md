# Regra de manutenção dos projetos próprios

Os quatro dossiês são snapshots auditáveis, não substitutos para o estado vivo dos repositórios.

## Gate obrigatório antes de fechar cada lote

O Chat 1 deve fazer fetch fresco de `main` e `plans/STATUS.md` de:

- RPG Skill Tree;
- Volcanoes;
- Enshrouded;
- Black Arcana.

Depois, deve comparar os SHAs atuais com o baseline registrado em [`06-snapshot-reconciliation.md`](06-snapshot-reconciliation.md) e [`12-capability-delta-coverage.md`](12-capability-delta-coverage.md).

Se o SHA não mudou ou o avanço não toca nenhuma superfície jogável/progressiva pertinente, registrar `SEM DELTA RELEVANTE`.

Ao detectar avanço posterior ao SHA registrado:

1. consultar `plans/STATUS.md` fresco do projeto;
2. identificar quais planos/subsistemas mudaram;
3. consultar código/testes/CI apenas na superfície alterada quando necessário;
4. atualizar o estado do subcomponente afetado sem promover o Stage inteiro indevidamente;
5. atualizar a matriz cruzada se uma bridge/authority mudou;
6. atualizar o apêndice temático pertinente somente se a mudança afetar Gameplay, Magia ou Tecnologia;
7. preservar separação entre runtime canônico, implementação parcial, trabalho preparatório e planejamento;
8. **extrair toda capacidade jogável nova ou semanticamente alterada**, incluindo recurso, resistência, estado, hazard, ação, equipamento, query, serviço, progressão, diagnóstico, milestone ou boundary público;
9. lançar cada capacidade em [`12-capability-delta-coverage.md`](12-capability-delta-coverage.md), mesmo que nenhuma perk existente já a mencione;
10. classificar sua cobertura antes de o lote ser declarado fechado.

## Regra contra a lacuna circular

É proibido concluir que uma capacidade “não é pertinente” somente porque nenhuma perk atual a referencia. O próprio objetivo da auditoria provider → árvore é descobrir **perks/bridges/especializações que ainda não existem**.

Exemplos permanentes:

- Volcanoes: O₂/pressão parcial, respiração, hipóxia, filtros/proteção, gases, pressão, geologia e novas integrações;
- Black Arcana: Arcane Resistance, Corruption Resistance, Strain, Danger/Backlash e futuras superfícies públicas;
- Enshrouded: Exposure, Flame, Sanctuary/Ward, Corrupted Ecology, Story/Lich e futuras integrações;
- RPG Skill Tree: qualquer nova superfície pública de progressão, atributos, subtrees, itemização, corpos, compêndio ou cartografia conforme chegue à `main`.

A lista é exemplificativa, não exaustiva.

## Novos mods externos adicionados à modlist

A mesma lógica vale para mods externos adicionados depois do snapshot dos três guias. Eles devem ser incorporados aos guias pertinentes e entrar na cobertura do Chat 1 antes do próximo fechamento de lote.

Exemplo já registrado em 2026-08-30: **Mobstein 5.4.4** foi adicionado à modlist após `modlist 28.08.26.txt` e incorporado aos guias de Gameplay e Magia.

O objetivo é permitir atualização incremental precisa sem obrigar o Chat 1 a reauditar historicamente todos os quatro projetos ou toda a modlist a cada lote, mas sem permitir que capacidades/mods novos passem despercebidos.

# Regra de manutenção dos projetos próprios

Os quatro dossiês são snapshots auditáveis, não substitutos para o estado vivo dos repositórios.

Ao detectar avanço posterior ao SHA registrado:

1. consultar `plans/STATUS.md` fresco do projeto;
2. identificar quais planos/subsistemas mudaram;
3. consultar código/testes/CI apenas na superfície alterada quando necessário;
4. atualizar o estado do subcomponente afetado sem promover o Stage inteiro indevidamente;
5. atualizar a matriz cruzada se uma bridge/authority mudou;
6. atualizar o apêndice temático pertinente somente se a mudança afetar Gameplay, Magia ou Tecnologia;
7. preservar separação entre runtime canônico, implementação parcial, trabalho preparatório e planejamento.

O objetivo é permitir atualização incremental precisa sem obrigar o Chat 1 a reauditar historicamente todos os quatro projetos a cada lote.

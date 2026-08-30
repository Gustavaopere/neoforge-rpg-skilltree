# Chat 1 — Regra obrigatória para projetos próprios

Este arquivo complementa o protocolo permanente do Chat 1 para garantir que os quatro projetos próprios do modpack participem da auditoria de perks e que novas capacidades desses projetos não fiquem invisíveis por ainda não possuírem uma perk no catálogo.

Antes de fechar qualquer lote, o Chat 1 deve ler integralmente:

- `../guides/projects/README.md`;
- `../guides/projects/01-rpg-skill-tree.md`;
- `../guides/projects/02-volcanoes.md`;
- `../guides/projects/03-enshrouded.md`;
- `../guides/projects/04-black-arcana.md`;
- `../guides/projects/05-cross-project-integration-matrix.md`;
- `../guides/projects/06-snapshot-reconciliation.md`;
- `../guides/projects/07-chat1-provider-listing-checklist.md`;
- `../guides/projects/08-maintenance-rule.md`;
- `../guides/projects/12-capability-delta-coverage.md`.

## Gate obrigatório de delta — antes da primeira perk do lote

O Chat 1 deve fazer fetch fresco de `main` e `plans/STATUS.md` de:

1. RPG Skill Tree;
2. Volcanoes;
3. Enshrouded;
4. Black Arcana.

Comparar os SHAs atuais contra o baseline reconciliado. Para cada projeto:

- registrar `SEM DELTA RELEVANTE` quando nada pertinente mudou; ou
- identificar os planos/subsistemas alterados;
- consultar código/testes/CI apenas na superfície necessária;
- extrair **toda capacidade jogável nova ou semanticamente alterada**, mesmo que nenhuma perk atual já a cite;
- lançar a capacidade na matriz de cobertura;
- classificá-la antes de fechar o lote.

As decisões possíveis de cobertura são:

- `COBERTA POR PERK EXISTENTE`;
- `PERK PRÓPRIA`;
- `ESPECIALIZAÇÃO`;
- `BRIDGE`;
- `COBERTO POR SISTEMA UNIVERSAL`;
- `PROGRESSÃO NATIVA AUTORITATIVA`;
- `SEM HOOK SEGURO`;
- `NÃO DEVE SER INTEGRADO`.

A auditoria deve provar **os dois sentidos**:

- `perk → provider`: a perk existente usa authority e boundary corretos;
- `provider → árvore`: nenhuma capacidade nova/alterada pertinente ficou sem avaliação só porque ainda não existe uma perk apontando para ela.

Exemplos permanentes de capacidades que devem acionar essa pergunta quando presentes/alteradas incluem O₂/respiração/pressão/proteção do Volcanoes, Arcane Resistance/Corruption Resistance/Strain do Black Arcana, Exposure/Flame/Sanctuary/Story do Enshrouded e qualquer nova superfície pública do RPG Skill Tree. São exemplos, não lista exaustiva.

Detectar uma capacidade não obriga criar perk. `Provider-native first`, hook real, authority, deduplicação, fallback e fail-closed permanecem obrigatórios.

A descoberta de uma lacuna **não altera a regra de lotes exatos de 10**. Se o node necessário estiver fora do lote atual, registrar a necessidade e o posicionamento proposto para ciclo posterior; não iniciar uma décima primeira perk.

## Classificação de cada perk

Para cada perk, o Chat 1 deve classificar a relação com cada projeto próprio pertinente como uma destas categorias:

- provider principal;
- provider/consumer secundário;
- bridge;
- gate/requisito;
- recurso;
- Mastery/progressão;
- hazard/ambiente;
- equipamento/itemização;
- read-only/query;
- não aplicável;
- bloqueado/fail-closed.

O Chat 1 não pode tratar `PLANEJADO`, `PREPARATÓRIO / NÃO CANÔNICO` ou `BLOQUEADO / FAIL-CLOSED` como runtime disponível. Um subcomponente comprovado em `main` não promove automaticamente o Stage inteiro a concluído.

README raiz dos quatro projetos não é evidência suficiente. A autoridade operacional é o estado real de `plans/STATUS.md`, planos individuais e, quando necessário, código/testes/CI na `main` do projeto correspondente.

Integração temática não cria bridge. Em especial:

- Black Arcana Corruption não é Enshrouded Shroud/Exposure;
- Black Arcana Arcane Resistance não é generic magic resistance nem Enshrouded MagicResistanceService;
- Volcanoes Atmosphere/pressão/temperatura não viram Shroud ou Arcane Resistance por inferência;
- o RPG Skill Tree não deve escrever diretamente estado autoritativo de Flame/Shroud, Volcanoes ou Black Arcana sem provider/API explícito.

Se uma perk toca dois ou mais projetos próprios, o dossiê deve declarar um pipeline principal, a direção da bridge, authority de cada domínio, identidade de deduplicação, fallback e fail-closed.

## Delta de mods externos

Quando a modlist receber um mod depois do snapshot dos três guias, ele precisa ser cadastrado e incorporado aos guias pertinentes antes do próximo fechamento de lote. Exemplo já reconciliado: **Mobstein 5.4.4**, adicionado em 2026-08-30 como provider de ressurreição corporal, experimentos, aliados ressuscitados, estruturas e bosses; sua necromancia não cria bridge automática com Goety, Black Arcana, Malum, Eidolon ou Enshrouded.

Esta regra é cumulativa com `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` e não reduz nenhuma exigência existente.

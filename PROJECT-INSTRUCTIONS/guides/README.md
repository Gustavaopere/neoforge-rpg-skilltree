# Guias Detalhados do Modpack

Esta pasta versiona as fontes editoriais detalhadas usadas para manter os quatro guias consolidados de `PROJECT-INSTRUCTIONS/` e para auditar providers, integrações, authority, deltas e provenance.

Para execução operacional de perks, o ponto de entrada canônico são os arquivos consolidados na pasta pai:

- `../GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `../GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `../GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `../GUIA-COMPLETO-PROJETOS-PROPRIOS.md`.

A árvore abaixo continua sendo a fonte editorial detalhada. Ela não cria uma segunda autoridade operacional concorrente.

## Autoridade por mod para criação futura de perks

A auditoria de cobertura de **29/09/2026** mudou a regra operacional para leitura por provider externo:

1. **Presença, posição, JAR e versão atuais:** `../modlist/modlist.md`.
2. **Mecânicas, authority, lifecycle, riscos, hooks e limites de um mod individual:** dossier atual correspondente em `../modlist/<categorias>/✅-*.md`.
3. **Contexto transversal, relações entre providers e histórico editorial:** estes guias.
4. **RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana:** `projects/` continua obrigatório; esses contratos não devem ser copiados artificialmente para dossiers de mods externos.

Os arquivos `CURRENT-MODLIST*.md` desta árvore são **snapshots editoriais históricos**. Eles não substituem a modlist física atual nem autorizam usar um mod removido, uma versão antiga ou um hook não confirmado. Se um guia contiver uma mecânica perk-relevante que não esteja no dossier individual atual, o lote de perks deve **parar e reconciliar a lacuna primeiro**.

Relatório da reconciliação: [GUIDE-TO-MOD-DOSSIER-COVERAGE-AUDIT-2026-09-29.md](GUIDE-TO-MOD-DOSSIER-COVERAGE-AUDIT-2026-09-29.md).

## Guias temáticos

1. [Gameplay e Sistemas](gameplay/README.md) — [inventário atual da modlist](gameplay/CURRENT-MODLIST.md)
2. [Mods de Magia](magic/README.md) — [inventário atual da modlist](magic/CURRENT-MODLIST.md)
3. [Mods de Tecnologia](technology/README.md) — [inventário atual da modlist](technology/CURRENT-MODLIST.md)

## Projetos próprios — leitura obrigatória para o Chat 1

4. [Projetos Próprios do Modpack](projects/README.md)

A coleção `projects/` permanece separada dos três guias temáticos. Ela documenta integralmente, com estado técnico explícito:

- RPG Skill Tree;
- Volcanoes;
- Enshrouded;
- Black Arcana;
- matriz de integração cruzada entre eles;
- reconciliação de snapshots;
- matriz provider → árvore para detectar capacidades novas/alteradas ainda sem perk.

Ela é a fonte transversal para decidir se um projeto próprio deve aparecer em `Provider/Mods`, qual boundary/hook é legítimo, qual projeto conserva a authority, se a integração está `IMPLEMENTADO E CANÔNICO`, `IMPLEMENTADO PARCIALMENTE`, `PREPARATÓRIO / NÃO CANÔNICO`, `PLANEJADO`, `BLOQUEADO / FAIL-CLOSED` ou `NÃO APLICÁVEL`, e se uma capacidade nova exige perk, especialização, bridge, cobertura universal, progressão nativa, fail-closed ou nenhuma integração.

## Autoridade de modlist

O snapshot de **2026-09-07** abaixo é histórico. A autoridade física atual da auditoria de 29/09/2026 é `../modlist/modlist.md`, com **587 entradas top-level incluindo o modloader**. Dependências `jarjar` embutidas não contam como mods top-level.

A modlist física mais recente continua sendo a authority de presença, filename JAR e versão runtime. Cada guia possui um `CURRENT-MODLIST.md`; esses inventários e seus deltas registram a reconciliação editorial do snapshot, mas não substituem um snapshot físico posterior.

No fechamento de 2026-09-07:

- 612 entradas top-level;
- 612 registros `Instalado` no Notion;
- 613/613 registros `Verificado`;
- 605/605 JARs que declaram `modVersion` reconciliados com versão runtime exata;
- 7/7 JARs sem `modVersion` preservados sem inferência.

Quando um capítulo histórico ainda citar versão anterior, `CURRENT-MODLIST.md` + delta mais recente prevalecem para identidade instalada. Update físico não autoriza inferir mudança de API/hook.

## Regra estrutural

Os guias **não são divididos por tamanho bruto**. Cada arquivo contém uma seção completa. Isso evita títulos soltos, parágrafos começando em um arquivo e terminando em outro e Markdown quebrado por limites artificiais de captura.

A navegação de cada guia está no `README.md` de sua própria pasta. A coleção `projects/` é deliberadamente separada para impedir quatro cópias divergentes dos mesmos contratos nos três guias temáticos; cada guia possui apenas um apêndice final com o recorte pertinente.

## Regras de precisão

- O estado de um projeto próprio deve ser derivado principalmente de `plans/STATUS.md`, planos individuais fechados/abertos e código/CI em `main` quando necessário; README raiz não basta.
- Trabalho apenas em branch/PR não conta como canônico em `main`.
- Um subcomponente provado em `main` não promove automaticamente seu Stage inteiro a concluído.
- Similaridade temática não cria bridge. Shroud, Corruption, Atmosphere, pressão, temperatura e Arcane Resistance permanecem authorities distintas salvo contrato explícito.
- O Chat 1 deve ler `projects/README.md`, os quatro dossiês, a matriz cruzada, a reconciliação e `projects/12-capability-delta-coverage.md` antes de fechar qualquer lote.
- O Chat 1 deve fazer fetch fresco das fontes operacionais dos quatro sistemas de primeira classe por lote para detectar capacidades novas ou alteradas mesmo que nenhuma perk já as mencione.
- Mods adicionados ou atualizados na modlist devem ser reconciliados nos `CURRENT-MODLIST.md` antes do próximo fechamento de lote.
- Biblioteca, bridge, UI, visual ou compatibilidade não vira provider mecânico automaticamente.

## Integridade da organização

- Gameplay: capítulos temáticos + `CURRENT-MODLIST.md`.
- Magia: capítulos temáticos + `CURRENT-MODLIST.md`.
- Tecnologia: capítulos temáticos + `CURRENT-MODLIST.md`.
- Projetos próprios: 4 dossiês completos + matriz cruzada + reconciliação + governança/checklists + matriz de delta de capacidades.
- Delta externo de modlist: `../modlist/MODLIST-DELTA-2026-09-07.md`.
- Nenhum arquivo depende de continuar um parágrafo ou seção em outro arquivo.

O caminho editorial ativo é `PROJECT-INSTRUCTIONS/guides/`. Não recriar `plans/03-skill-tree-perks/guides/`.
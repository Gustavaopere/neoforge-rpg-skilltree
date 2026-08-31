# Guias Canônicos do Modpack

Esta pasta versiona os três guias temáticos canônicos usados na auditoria e no design das perks e a coleção transversal dos **quatro projetos próprios** do modpack.

Para **Gameplay e Sistemas**, **Mods de Magia** e **Mods de Tecnologia**, o **GitHub é a fonte editorial e técnica canônica**. Não deve existir uma segunda cópia editorial concorrente desses três guias no Notion.

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
- **matriz provider → árvore para detectar capacidades novas/alteradas ainda sem perk**.

Ela é a fonte transversal para decidir se um projeto próprio deve aparecer em `Provider/Mods`, qual boundary/hook é legítimo, qual projeto conserva a autoridade, se a integração está `IMPLEMENTADO E CANÔNICO`, `IMPLEMENTADO PARCIALMENTE`, `PREPARATÓRIO / NÃO CANÔNICO`, `PLANEJADO`, `BLOQUEADO / FAIL-CLOSED` ou `NÃO APLICÁVEL`, e se uma capacidade nova exige perk, especialização, bridge, cobertura universal, progressão nativa, fail-closed ou nenhuma integração.

## Autoridade de modlist

A referência atual dos três guias temáticos é a `modlist.txt` reconciliada em **2026-08-30**:

- **573 entradas top-level**, incluindo NeoForge;
- **572 arquivos `.jar`**;
- a união dos inventários atuais dos três guias cobre **572/572 JARs**;
- NeoForge `21.1.248` é registrado separadamente como modloader;
- sobreposição de JAR entre guias é intencional quando um mod pertence a mais de um domínio.

Cada guia possui um `CURRENT-MODLIST.md`. Esse arquivo é a autoridade imediata de **presença, filename JAR e versão**. Se um capítulo histórico ainda contiver uma versão anterior, o inventário atual prevalece até a atualização editorial daquele parágrafo.

## Regra estrutural

Os guias **não são divididos por tamanho bruto**. Cada arquivo contém uma seção completa. Isso evita títulos soltos, parágrafos começando em um arquivo e terminando em outro e Markdown quebrado por limites artificiais de captura.

A navegação canônica de cada guia está no `README.md` de sua própria pasta. A coleção `projects/` é deliberadamente separada para impedir quatro cópias divergentes dos mesmos contratos nos três guias temáticos; cada guia possui apenas um apêndice final com o recorte pertinente.

## Regras de precisão

- O estado de um projeto próprio deve ser derivado principalmente de `plans/STATUS.md`, planos individuais fechados/abertos e código/CI em `main` quando necessário; README raiz não basta.
- Trabalho apenas em branch/PR não conta como canônico em `main`.
- Um subcomponente provado em `main` não promove automaticamente seu Stage inteiro a concluído.
- Similaridade temática não cria bridge. Shroud, Corruption, Atmosphere, pressão, temperatura e Arcane Resistance permanecem autoridades distintas salvo contrato explícito.
- O Chat 1 deve ler `projects/README.md`, os quatro dossiês, a matriz cruzada, a reconciliação e `projects/12-capability-delta-coverage.md` antes de fechar qualquer lote.
- O Chat 1 deve fazer fetch fresco dos quatro projetos próprios por lote para detectar **capacidades novas ou alteradas mesmo que nenhuma perk já as mencione**.
- Mods adicionados ou atualizados na modlist devem ser reconciliados nos `CURRENT-MODLIST.md` antes do próximo fechamento de lote.

## Integridade da organização

- Gameplay: capítulos temáticos + `CURRENT-MODLIST.md`.
- Magia: capítulos temáticos + `CURRENT-MODLIST.md`.
- Tecnologia: capítulos temáticos + `CURRENT-MODLIST.md`.
- Projetos próprios: 4 dossiês completos + matriz cruzada + reconciliação + governança/checklists + matriz de delta de capacidades.
- Nenhum arquivo depende de continuar um parágrafo ou seção em outro arquivo.

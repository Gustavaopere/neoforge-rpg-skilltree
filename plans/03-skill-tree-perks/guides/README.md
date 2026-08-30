# Snapshots Canônicos dos Guias do Modpack

Esta pasta versiona os três guias temáticos canônicos usados na auditoria e no design das perks e a coleção transversal dos **quatro projetos próprios** do modpack. O Notion continua sendo a fonte editorial de verdade; os arquivos daqui são snapshots auditáveis para leitura técnica e revisão no GitHub.

## Guias temáticos

1. [Gameplay e Sistemas](gameplay/README.md)
2. [Mods de Magia](magic/README.md)
3. [Mods de Tecnologia](technology/README.md)

## Projetos próprios — leitura obrigatória para o Chat 1

4. [Projetos Próprios do Modpack](projects/README.md)

A coleção `projects/` documenta integralmente, com estado técnico explícito:

- RPG Skill Tree;
- Volcanoes;
- Enshrouded;
- Black Arcana;
- matriz de integração cruzada entre eles.

Ela é a fonte transversal para decidir se um projeto próprio deve aparecer em `Provider/Mods`, qual boundary/hook é legítimo, qual projeto conserva a autoridade e se a integração está `IMPLEMENTADO E CANÔNICO`, `IMPLEMENTADO PARCIALMENTE`, `PREPARATÓRIO / NÃO CANÔNICO`, `PLANEJADO`, `BLOQUEADO / FAIL-CLOSED` ou `NÃO APLICÁVEL`.

## Regra estrutural

Os snapshots **não são divididos por tamanho bruto**. Cada arquivo contém uma seção completa. Isso evita títulos soltos, parágrafos começando em um arquivo e terminando em outro e Markdown quebrado por limites artificiais de captura.

A navegação canônica de cada guia está no `README.md` de sua própria pasta. A coleção `projects/` é deliberadamente separada para impedir quatro cópias divergentes dos mesmos contratos nos três guias temáticos; cada guia possui apenas um apêndice final com o recorte pertinente.

## Autoridade e referência

- Gameplay e Sistemas: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6
- Mods de Magia: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03
- Mods de Tecnologia: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff
- Projetos Próprios do Modpack: https://app.notion.com/p/3cc69db9f0db81b09939eaca7c446fa2
- Modlist reconciliada nos três guias temáticos: `modlist 28.08.26.txt`.

## Regras de precisão

- O estado de um projeto próprio deve ser derivado principalmente de `plans/STATUS.md`, planos individuais fechados/abertos e código/CI em `main` quando necessário; README raiz não basta.
- Trabalho apenas em branch/PR não conta como canônico em `main`.
- Um subcomponente provado em `main` não promove automaticamente seu Stage inteiro a concluído.
- Similaridade temática não cria bridge. Shroud, Corruption, Atmosphere, pressão, temperatura e Arcane Resistance permanecem autoridades distintas salvo contrato explícito.
- O Chat 1 deve ler `projects/README.md`, os quatro dossiês e a matriz cruzada antes de fechar qualquer perk potencialmente relacionada a esses projetos.

## Integridade da organização

- Gameplay: 14 arquivos de conteúdo, incluindo o apêndice final de projetos próprios.
- Magia: 18 arquivos de conteúdo, incluindo o apêndice final de projetos próprios.
- Tecnologia: 20 arquivos de conteúdo, incluindo o apêndice final de projetos próprios.
- Projetos próprios: 4 dossiês completos + 1 matriz cruzada + README de governança.
- Cada guia temático foi originalmente reconstruído integralmente e repartido por headings de nível 1.
- Nenhum arquivo depende de continuar um parágrafo ou seção em outro arquivo.

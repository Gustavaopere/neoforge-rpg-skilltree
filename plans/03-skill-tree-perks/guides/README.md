# Snapshots Canônicos dos 3 Guias do Modpack

Esta pasta versiona os três guias canônicos usados na auditoria e no design das perks. O Notion continua sendo a fonte de verdade; os arquivos daqui são snapshots auditáveis para leitura técnica e revisão no GitHub.

## Guias

1. [Gameplay e Sistemas](gameplay/README.md)
2. [Mods de Magia](magic/README.md)
3. [Mods de Tecnologia](technology/README.md)

## Regra estrutural

Os snapshots **não são mais divididos por tamanho bruto**. Cada arquivo contém uma seção completa do guia. Isso evita títulos soltos, parágrafos começando em um arquivo e terminando em outro e Markdown quebrado por limites artificiais de captura.

A navegação canônica de cada guia está no `README.md` de sua própria pasta. Não é necessário concatenar arquivos para entender uma seção.

## Autoridade e referência

- Gameplay e Sistemas: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6
- Mods de Magia: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03
- Mods de Tecnologia: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff
- Modlist reconciliada no snapshot: `modlist 28.08.26.txt`.

## Integridade desta reorganização

- Gameplay: 13 arquivos de conteúdo.
- Magia: 17 arquivos de conteúdo.
- Tecnologia: 19 arquivos de conteúdo.
- Cada guia foi primeiro reconstruído integralmente e só então repartido por headings de nível 1.
- A transformação abortou se detectasse perda ou reordenação do conteúdo ao repartir os capítulos.
- Headings que estavam grudados em parágrafos foram separados em linhas próprias.
- Callouts foram convertidos para Markdown; tabelas estruturais foram convertidas para tabelas Markdown; navegação `mention-page` foi substituída por links explícitos.
- Nenhum arquivo novo depende de continuar um parágrafo ou seção em outro arquivo.

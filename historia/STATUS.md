# STATUS — História

## Estado da estrutura

- Estrutura editorial: **CRIADA**
- Política sem spoilers: **ATIVA**
- IDs estáveis: **DEFINIDOS**
- Templates reutilizáveis: **MIGRADOS PARA MINECRAFT MOD FACTORY**
- Profile consumidor da campanha: **DEFINIDO**
- Macro-história inicial: **REGISTRADA**
- Faixa inicial de NPCs `NPC-0001`–`NPC-0006`: **VERSIONADA COM ESTADOS EDITORIAIS EXPLÍCITOS**
- NPCs já espelhados do Grimoire em dossiês completos: **Iren, Liora, Oren e Elian**
- NPC source-blocked preservado sem preenchimento artificial: **Elias (`NPC-0002`)**
- Conflito de identidade preservado em fail-closed: **Severin ↔ Aren**
- Primeira oportunidade/quest de referência: **REGISTRADA**
- Pipeline genérico de autoria: **MIGRADO PARA MINECRAFT MOD FACTORY / CONSUMIDO POR CI**
- Contratos editoriais específicos da campanha: **MANTIDOS NO RPG**
- Referências Grimoire relacionadas ainda sem ID editorial próprio: **RASTREADAS NO BACKLOG / NÃO MATERIALIZADAS SEM SOURCE**
- Diretor narrativo IA in-game: **OPCIONAL / PLANEJADO / NÃO É AUTORIDADE**

## Regra de atualização

Ao adicionar conteúdo:

1. criar/usar ID estável;
2. declarar estado editorial (`RASCUNHO`, `CANÔNICO`, `EXPERIMENTAL`, `OBSOLETO`);
3. registrar relações por IDs, não apenas por nomes;
4. preservar o modo sem spoilers no chat;
5. nunca transformar plano técnico em fato narrativo sem decisão editorial;
6. nunca inventar mecânica de provider para justificar história;
7. usar `historia/narrative-authoring-profile.json` ao consumir validators/inventory da Factory;
8. não duplicar localmente tooling, skill ou scaffolds genéricos da Factory;
9. quando um dossiê trouxer UUID de entidade relacionada do Grimoire sem ID editorial no GitHub, registrar a referência no backlog antes de criar novo `NPC/FAC/SET/LOC`;
10. atualizar este arquivo apenas com progresso estrutural, sem revelar spoilers.

## Próximos blocos editoriais

- reconciliar as entidades relacionadas já referenciadas por UUID nos dossiês de Oren/Elian antes de criar duplicatas (`Maura`, `Ofícios do Pátio`, `Pátio das Oficinas`, `Coordenação de Sobrevivência do Entreposto`, `Acampamento do Entreposto`, `O Entreposto`);
- resolver a decisão editorial Severin↔Aren;
- recuperar a authority de `NPC-0002` Elias;
- continuar expansão de NPCs nomeados somente a partir de source recuperado;
- criar/expandir facções, instituições, assentamentos e locais quando suas entities-fonte estiverem reconciliadas;
- registrar mistérios, evidências e rumores;
- criar arcos por era;
- criar quests âncora e oportunidades emergentes;
- criar matriz de finais/epílogos;
- conectar cada conteúdo aos providers reais do modpack quando pertinente.

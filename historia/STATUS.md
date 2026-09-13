# STATUS — História

## Estado da estrutura

- Estrutura editorial: **CRIADA**
- Política sem spoilers: **ATIVA**
- IDs estáveis: **DEFINIDOS**
- Templates: **CRIADOS**
- Macro-história inicial: **REGISTRADA**
- Primeiro NPC sistêmico: **REGISTRADO**
- Primeira oportunidade/quest de referência: **REGISTRADA**
- Pipeline de autoria assistida: **REGISTRADO**
- Stack de autoria com política de custo zero: **REGISTRADO**
- Área e template de diálogos versionados: **CRIADOS**
- Validador local de IDs/referências: **CRIADO / TESTADO (10 testes) / SAÍDA SPOILER-SAFE POR PADRÃO**
- Lint estrutural de diálogos: **CRIADO / TESTADO (9 testes) / SAÍDA SPOILER-SAFE POR PADRÃO**
- Inventário local de registros/IDs/referências: **CRIADO / TESTADO (8 testes) / USO EDITORIAL DELIBERADAMENTE REVELADOR**
- Suíte conjunta do tooling editorial: **TESTADA (27 testes)**
- Contrato editorial de relações/memória/continuidade: **PROPOSTO NO PR DO STACK DE AUTORIA**
- Template auxiliar de relação multidimensional: **CRIADO**
- Pipeline visual de NPCs alinhado à Minecraft Mod Factory: **REGISTRADO**
- Política de reconciliação Grimoire ↔ GitHub: **REGISTRADA**
- Contrato de binding geográfico com Compêndio/runtime: **REGISTRADO**
- Piloto de voz/diálogo/asset brief de NPC-0001: **EM PR SEPARADO / RASCUNHO**
- Piloto integrado de NPC/quest/evidências/assentamento/local/asset brief: **EM PR SEPARADO / RASCUNHO / BLOQUEADO PARA RECONCILIAÇÃO COM GRIMOIRE ANTES DE PROMOÇÃO CANÔNICA**
- NPC-0002 (Elias): **CONSOLIDAÇÃO BLOQUEADA POR AUSÊNCIA DE EVIDÊNCIA CANÔNICA SUFICIENTE**
- Macro-história alinhada ao contexto canônico e Era da Fragmentação: **EM PR DRAFT SEPARADO / PENDENTE DE RECONCILIAÇÃO COM GRIMOIRE**
- Macroprogressão não linear de 14 eras: **EM PR DRAFT SEPARADO / EMPILHADA SOBRE MACRO-HISTÓRIA**
- Contrato editorial de epílogos combinatórios: **EM PR SEPARADO / SEM FINAIS CONCRETOS**
- Diretor narrativo IA in-game: **OPCIONAL / PLANEJADO / NÃO É AUTORIDADE**

## Regra de atualização

Ao adicionar conteúdo:

1. criar/usar ID estável;
2. declarar estado editorial (`RASCUNHO`, `CANÔNICO`, `EXPERIMENTAL`, `OBSOLETO` ou estado equivalente aprovado);
3. registrar relações por IDs, não apenas por nomes, preservando direção `origem -> alvo`;
4. não colapsar affection/trust/respect/fear/dependency/ideological alignment em uma única reputação;
5. preservar o modo sem spoilers no chat e nas saídas padrão das ferramentas;
6. usar `--reveal` apenas para depuração editorial quando a ferramenta oferecer esse modo;
7. tratar `story_inventory.py` como ferramenta editorial reveladora, nunca como saída player-facing;
8. nunca transformar plano técnico em fato narrativo sem decisão editorial;
9. nunca inventar mecânica de provider para justificar história;
10. nunca preencher lacunas de NPC/lore por suposição quando a authority necessária estiver ausente;
11. consultar Grimoire antes de duplicar lore central quando a conexão estiver disponível;
12. usar Compêndio/runtime antes de fixar worldgen/bioma/estrutura/dimensão;
13. executar os validadores estruturais aplicáveis antes de consolidar grandes lotes;
14. atualizar este arquivo apenas com progresso estrutural, sem revelar spoilers.

## Próximos blocos editoriais

- reconciliar os pilotos atuais com Grimoire quando a conexão estiver disponível;
- produzir/validar concepts e skins seguindo a pipeline visual da Factory;
- aplicar o contrato multidimensional de relações aos NPCs somente quando eventos/evidências justificarem estado inicial ou mudança;
- recuperar ou redesenhar deliberadamente NPCs nomeados sem material suficiente;
- expandir NPCs com evidência canônica já disponível;
- criar facções, assentamentos e locais somente quando houver necessidade narrativa/authority suficiente;
- registrar mistérios, evidências e rumores sem single point of failure;
- criar arcos por era e quests âncora conforme o canon amadurecer;
- produzir fragmentos `END-####` somente quando o estado canônico necessário estiver estabilizado;
- conectar cada conteúdo aos providers reais do modpack quando pertinente;
- automatizar novas verificações apenas quando forem específicas deste projeto e permanecerem sem dependência paga.

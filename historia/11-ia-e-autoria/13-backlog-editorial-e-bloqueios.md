# Backlog editorial e bloqueios de cânone

## Estado
ATIVO COMO FILA OPERACIONAL / NÃO É CÂNONE NOVO.

## Objetivo
Registrar lacunas reais da campanha sem tratá-las como autorização para invenção. Este backlog organiza o que pode avançar com as fontes atuais, o que exige decisão editorial e o que depende de prova mecânica do runtime/Compêndio.

Este arquivo não substitui `historia/STATUS.md`, os dossiês de entidade, a Campaign Bible do Grimoire/TTRPG.bot nem os contratos técnicos.

## Estados operacionais
- `PRONTO`: pode avançar com as fontes atuais sem criar fato novo não suportado.
- `AUDITORIA MECÂNICA`: pode levantar evidência técnica/candidatos, sem fixar lore por conta própria.
- `BLOQUEADO — GRIMOIRE`: falta authority de lore estruturada ainda não recuperada/reconciliada.
- `BLOQUEADO — RUNTIME`: falta prova de provider, registry ID, worldgen ou hook mecânico.
- `PENDENTE DE DECISÃO EDITORIAL`: as fontes podem existir, mas ainda falta decisão de autoria.
- `CONFLITO DE IDENTIDADE`: fontes atuais descrevem entidades/papéis sobrepostos e qualquer merge/alias/retcon automático é proibido.
- `CONCLUÍDO`: requisito resolvido na fonte autoritativa e refletido nos dossiês apropriados.

## Regras de execução
1. ausência de informação nunca autoriza preencher uma lacuna;
2. lore estruturada central deve ser reconciliada com o Grimoire quando o domínio estiver registrado ali;
3. mecânica/provider/worldgen exige fonte técnica atual;
4. fato social/político e capability mecânica são domínios diferentes;
5. candidatos podem ser registrados sem virarem cânone;
6. um item só sai de `BLOQUEADO` quando a fonte necessária foi realmente consultada;
7. conflitos entre GitHub e Grimoire são fail-closed até decisão editorial explícita;
8. asset visual rascunho não fixa sozinho aparência canônica nem identidade narrativa;
9. readiness visual é avaliada por NPC: ausência de conflito de identidade não basta; o dossiê/lore precisa estar reconciliado e o asset brief deve derivar apenas de fontes aprovadas;
10. UUID relacionado recuperado de um dossiê não autoriza criar automaticamente um novo ID editorial: primeiro consultar a entidade-fonte no Grimoire e auditar duplicatas em `main`/PRs.

## Fila atual
| Área | Alvo | Estado | Authority/evidência exigida | Permitido agora | Proibido enquanto pendente |
| --- | --- | --- | --- | --- | --- |
| NPC | `NPC-0001` Severin ↔ Aren | `CONFLITO DE IDENTIDADE` | dossiês GitHub + entidade Aren no Grimoire + decisão editorial explícita | preservar ambos, comparar proveniência, manter assets como look-dev | assumir mesma pessoa ou pessoas distintas; copiar provider, aparência, segredos, motivações ou relações entre eles |
| NPC | `NPC-0002` Elias | `BLOQUEADO — GRIMOIRE` | Campaign Bible/registro anterior recuperado | preservar ID/nome e localizar proveniência | inventar aparência, função, personalidade, relações, arco ou produzir asset visual baseado em lacunas |
| NPC | `NPC-0003` Iren Valmor | `PRONTO` | dossiê versionado + draft reconciliado no Grimoire | continuar autoria derivada compatível; futura skin técnica e novo portrait verificado | duplicar identidade, conceder knowledge sem proveniência, promover look-dev a final sem QA |
| NPC | `NPC-0004` Liora | `PRONTO` para lore já espelhada / `AUDITORIA MECÂNICA` para detalhes provider-native | dossiê + entidade Grimoire + runtime Ars Nouveau atual | usar identidade, voz, limites de knowledge e princípio de pluralidade já registrados; auditar spells/glyphs/rituais separadamente | inventar spell, glyph, ritual, local obrigatório, facção ou converter Source em recurso universal |
| NPC | `NPC-0005` Oren | `PRONTO` | dossiê + entidade Oren + `FAC-0003` + `SET-0003` + `LOC-0002` | usar memória/proveniência/revisão de hipóteses e vínculos agora reconciliados | inventar autoria de adulteração, causa histórica, knowledge ou eventos não presentes nas fontes |
| NPC | `NPC-0006` Elian | `PRONTO` | dossiê + entidade Elian + `NPC-0007` + `FAC-0002` + `SET-0002` | usar levantamento/documentação, distinção observação↔hipótese e vínculos reconciliados | inventar estrutura perdida, responsável, data, consequência ou relação além da fonte |
| NPC | `NPC-0007` Maura | `PRONTO` | entidade Maura + dossiê versionado | usar triagem/recuperação de materiais, limites e vínculo com `FAC-0002`/`SET-0002` | inventar evento do passado, estrutura específica, provider mecânico ou relação não registrada |
| Facção | `FAC-0001` Corte de Pedra Clara | `PRONTO` para fatos já reconciliados / `PENDENTE DE DECISÃO EDITORIAL` para liderança nominal | dossiê + draft reconciliado no Grimoire | usar funções institucionais já registradas | inventar líder, conspiração global, jurisdição universal ou agenda secreta |
| Facção | `FAC-0002` Ofícios do Pátio | `PRONTO` | entidade-fonte Grimoire + dossiê versionado | usar objetivos, crenças, recursos e limites de authority registrados | inferir propriedade de ruínas, provider técnico, identidade dos dois membros ainda não reconciliados |
| Facção | `FAC-0003` Coordenação de Sobrevivência do Entreposto | `PRONTO` | entidade-fonte Grimoire + dossiê versionado | usar coordenação emergencial, recursos, `NPC-0005`, `SET-0003` e `LOC-0002` | transformá-la em governo pleno, fixar Conselho futuro ou inventar os quatro membros ainda não reconciliados |
| Assentamento | `SET-0001` Pedra Clara | `PRONTO` para lore já reconciliada / `AUDITORIA MECÂNICA` para binding físico | dossiê + draft Grimoire + runtime/worldgen | usar fatos sociais existentes; auditar representação física separadamente | inferir bioma, coordenada, população ou capability mecânica não comprovada |
| Assentamento | `SET-0002` Pátio das Oficinas | `PRONTO` para lore / `AUDITORIA MECÂNICA` para binding físico | entidade Grimoire + runtime/worldgen quando necessário | usar identidade pequena/técnica e relações reconciliadas | tratar `location_type=city` como escala urbana automática; inventar provider, coordenadas ou direito prévio sobre `LOC-0002` |
| Assentamento | `SET-0003` Acampamento do Entreposto | `PRONTO` para lore / `AUDITORIA MECÂNICA` para binding físico | entidade Grimoire + runtime/worldgen quando necessário | usar núcleo inicial, população 5, `FAC-0003` e proximidade de `LOC-0002` | converter `district` em cidade madura; nomear habitantes por inferência; fixar provider/worldgen |
| Local | `LOC-0001` Região dos Ecos | `AUDITORIA MECÂNICA` | Compêndio/runtime + provider de worldgen + reconciliação geográfica com Grimoire | levantar candidatos com registry IDs reais | escolher bioma/dimensão/estrutura por estética, nome ou afinidade temática |
| Local | `LOC-0002` O Entreposto | `PRONTO` para lore / `AUDITORIA MECÂNICA` para representação física | entidade Grimoire + runtime/worldgen/estrutura quando houver binding | usar história multicamada, reutilização, deterioração e tensão preservação↔reuso | canonizar registry structure, provider, Projeto Continuidade, tecnologia apocalíptica ou nome antigo completo sem fonte |
| Quest | `QST-0001` Ecos Sombrios | `PRONTO` como quest-test/lifecycle | dossiês de quest/evidência/knowledge | validar discovery, lifecycle e consequências | transformar evidência isolada em culpa/localização automática |
| Eventos | família `EVT-####` | `PENDENTE DE DECISÃO EDITORIAL` | evento concreto, causa/ator/tempo e consequência | criar quando existir fato persistente real | preencher diretório por completude artificial |
| Finais/epílogos | família `END-####` | `PENDENTE DE DECISÃO EDITORIAL` | estados persistentes/deriváveis do Narrative Core | definir fragmentos condicionais quando houver base | escrever rota final fixa ou moral score universal |
| Assets | portraits/skins NPC | `PRONTO` somente para NPC com lore/identidade reconciliados e asset brief source-grounded | dossiê reconciliado + asset brief + aprovação visual + pipeline técnica | produzir portrait HD e skin 64×64 separados apenas para NPCs que atendam ao gate individual | iniciar asset para NPC source-blocked; usar portrait como UV de skin; marcar `FINAL` sem QA; inventar símbolos/provider |

## Referências Grimoire resolvidas em 2026-09-14

As seis referências abaixo deixaram de ser bloqueios depois da consulta individual das entidades-fonte, auditoria de duplicatas/numeração e materialização em branch. O UUID continua registrado para cross-reference.

| Referência | UUID Grimoire | ID editorial atribuído | Proveniência inicial | Estado operacional |
| --- | --- | --- | --- | --- |
| Maura | `76350f82-9af5-4cef-9c34-f66cf69cd2f7` | `NPC-0007` | `NPC-0006` Elian | `CONCLUÍDO` |
| Ofícios do Pátio | `c8408ee4-435c-4c85-9d61-5d4472197a88` | `FAC-0002` | `NPC-0006` Elian | `CONCLUÍDO` |
| Pátio das Oficinas | `b015847c-7c60-4472-8d5b-31f44dcbc53e` | `SET-0002` | `NPC-0006` Elian | `CONCLUÍDO` |
| Coordenação de Sobrevivência do Entreposto | `5988b526-9cd1-4b0c-b8b2-306f71043973` | `FAC-0003` | `NPC-0005` Oren | `CONCLUÍDO` |
| Acampamento do Entreposto | `e98d8010-3824-46f2-8851-cb20fddd3128` | `SET-0003` | `NPC-0005` Oren | `CONCLUÍDO` |
| O Entreposto | `d540695a-a27a-42f8-83e4-089822918836` | `LOC-0002` | `NPC-0005` Oren | `CONCLUÍDO` |

A classificação não foi derivada cegamente da categoria genérica `locations`: as descrições-fonte caracterizam Pátio das Oficinas e Acampamento do Entreposto como núcleos habitados, portanto `SET-####`; O Entreposto é sítio/ruína persistente, portanto `LOC-####`.

## Referências relacionadas ainda sem ID editorial próprio

Esses UUIDs surgiram durante a reconciliação. Permanecem fail-closed até sua própria entidade-fonte ser consultada e duplicatas/tipagem serem auditadas. A única exceção é Pátio da Passagem, cuja entidade já foi localizada no Grimoire, mas ainda não passou neste ciclo pelo gate completo de tipagem/ID/editorialização.

| Referência/contexto | UUID Grimoire | Proveniência | Estado operacional |
| --- | --- | --- | --- |
| Pátio da Passagem | `14e39b79-a0de-4bf0-9bbe-9c11ae5b95a9` | conexão de `SET-0002` | `PENDENTE DE DECISÃO EDITORIAL` |
| parent location de `SET-0003`/`LOC-0002` | `e05ef102-46c3-4f31-8043-ee2eda6d99af` | entidades Acampamento/Entreposto | `BLOQUEADO — GRIMOIRE` |
| membro de `FAC-0002` | `6ffab968-5ff5-4563-9aa9-3552b5e97310` | `key_member_ids` | `BLOQUEADO — GRIMOIRE` |
| membro de `FAC-0002` | `74a40012-22e6-44aa-a479-75345011519d` | `key_member_ids` | `BLOQUEADO — GRIMOIRE` |
| membro de `FAC-0003` | `29552385-a06a-4258-bcb7-a27c2ce8e6bb` | `key_member_ids` | `BLOQUEADO — GRIMOIRE` |
| membro de `FAC-0003` | `32a3237d-e699-4384-bf14-a12f8d620965` | `key_member_ids` | `BLOQUEADO — GRIMOIRE` |
| membro de `FAC-0003` | `55f7bdb0-a0a3-46c2-8d10-a69b76cbe6fd` | `key_member_ids` | `BLOQUEADO — GRIMOIRE` |
| membro de `FAC-0003` | `8ed41a1d-1692-4df2-9366-7a2153545e74` | `key_member_ids` | `BLOQUEADO — GRIMOIRE` |

`324ff53b-c599-4892-b263-0f86a8e8f8b9`, também presente em `FAC-0003.key_member_ids`, já é `NPC-0005` Oren e por isso não aparece como pendência.

## Reconciliações concluídas nesta etapa
- `NPC-0003` Iren Valmor foi materializado no Grimoire como draft ligado a `FAC-0001` e `SET-0001`;
- `FAC-0001` Corte de Pedra Clara foi materializada no Grimoire como draft;
- `SET-0001` Pedra Clara foi materializada no Grimoire como draft;
- `NPC-0004` Liora foi materializada no GitHub a partir da entidade já ativa do Grimoire, preservando Ars Nouveau como provider específico e mantendo discovery/lifecycle não fixados;
- `NPC-0005` Oren e `NPC-0006` Elian foram materializados no GitHub a partir das entidades já ativas do Grimoire;
- as seis referências relacionadas antes bloqueadas foram consultadas individualmente e materializadas como `NPC-0007`, `FAC-0002`, `FAC-0003`, `SET-0002`, `SET-0003` e `LOC-0002`;
- os vínculos de Oren/Elian foram atualizados para os IDs editoriais reconciliados sem expandir semântica de relações;
- o conflito Severin↔Aren foi registrado em fail-closed no GitHub, mas ainda não resolvido editorialmente;
- o antigo portrait candidate de Iren da PR #548 foi rejeitado e a PR foi fechada sem merge por corrupção/incompletude do arquivo e direção visual não aprovada.

## Ordem recomendada
1. resolver a identidade editorial Severin↔Aren antes de qualquer asset final do necromante;
2. recuperar/reconciliar `NPC-0002` Elias antes de qualquer autoria visual dele;
3. consultar os UUIDs relacionados ainda sem entidade-fonte materializada, priorizando os que condicionarem cenas/diálogos próximos; não reservar IDs antecipadamente;
4. decidir a tipagem/ID de Pátio da Passagem somente depois de auditar sua entidade completa e duplicatas;
5. manter Iren/Liora/Oren/Elian/Maura, as três facções e os três assentamentos/locais reconciliados sem promover hipóteses a fatos;
6. executar auditoria runtime do binding de `LOC-0001`, `LOC-0002`, `SET-0002` e `SET-0003` apenas quando representação física exigir;
7. só depois expandir eventos, diálogos e consequências que dependam dos UUIDs ainda bloqueados.

## Trabalho seguro que pode continuar
- QA editorial e de referências;
- portraits/look-dev apenas de NPCs cuja lore/identidade já esteja reconciliada e cujo asset brief seja source-grounded, sempre em novos arquivos completos/verificados;
- auditoria de provider/runtime sem promover candidatos a cânone;
- validação de knowledge, lifecycle, relações e causalidade;
- identificação de drift, duplicatas e referências órfãs;
- inventário de UUIDs Grimoire já citados no GitHub, desde que inventário não seja tratado como autorização para materialização.

## Critério de saída do backlog
Um item deixa esta fila apenas quando a authority necessária foi realmente consultada, a decisão foi registrada no dossiê correto, referências relacionadas foram revalidadas e nenhuma capability mecânica foi inferida sem prova.

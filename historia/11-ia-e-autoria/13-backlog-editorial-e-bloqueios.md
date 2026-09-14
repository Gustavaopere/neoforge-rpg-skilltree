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
| NPC | `NPC-0005` Oren | `PRONTO` para lore já espelhada / `BLOQUEADO — GRIMOIRE` para entidades relacionadas ainda não materializadas | dossiê + entidade Oren + sources das relações UUID | usar memória/proveniência/revisão de hipóteses já registradas e manter relações por UUID | criar facts sobre Coordenação/Entreposto/Acampamento, autoria de adulteração ou causa histórica não recuperada |
| NPC | `NPC-0006` Elian | `PRONTO` para lore já espelhada / `BLOQUEADO — GRIMOIRE` para entidades relacionadas ainda não materializadas | dossiê + entidade Elian + sources das relações UUID | usar levantamento/documentação e distinção observação↔hipótese já registradas e manter relações por UUID | inventar facts sobre Maura/Ofícios/Pátio, estrutura perdida, responsável, data ou consequência não recuperada |
| Facção | `FAC-0001` Corte de Pedra Clara | `PRONTO` para fatos já reconciliados / `PENDENTE DE DECISÃO EDITORIAL` para liderança nominal | dossiê + draft reconciliado no Grimoire | usar funções institucionais já registradas | inventar líder, conspiração global, jurisdição universal ou agenda secreta |
| Assentamento | `SET-0001` Pedra Clara | `PRONTO` para lore já reconciliada / `AUDITORIA MECÂNICA` para binding físico | dossiê + draft Grimoire + runtime/worldgen | usar fatos sociais existentes; auditar representação física separadamente | inferir bioma, coordenada, população ou capability mecânica não comprovada |
| Local | `LOC-0001` Região dos Ecos | `AUDITORIA MECÂNICA` | Compêndio/runtime + provider de worldgen + reconciliação geográfica com Grimoire | levantar candidatos com registry IDs reais | escolher bioma/dimensão/estrutura por estética, nome ou afinidade temática |
| Quest | `QST-0001` Ecos Sombrios | `PRONTO` como quest-test/lifecycle | dossiês de quest/evidência/knowledge | validar discovery, lifecycle e consequências | transformar evidência isolada em culpa/localização automática |
| Eventos | família `EVT-####` | `PENDENTE DE DECISÃO EDITORIAL` | evento concreto, causa/ator/tempo e consequência | criar quando existir fato persistente real | preencher diretório por completude artificial |
| Finais/epílogos | família `END-####` | `PENDENTE DE DECISÃO EDITORIAL` | estados persistentes/deriváveis do Narrative Core | definir fragmentos condicionais quando houver base | escrever rota final fixa ou moral score universal |
| Assets | portraits/skins NPC | `PRONTO` somente para NPC com lore/identidade reconciliados e asset brief source-grounded | dossiê reconciliado + asset brief + aprovação visual + pipeline técnica | produzir portrait HD e skin 64×64 separados apenas para NPCs que atendam ao gate individual | iniciar asset para NPC source-blocked; usar portrait como UV de skin; marcar `FINAL` sem QA; inventar símbolos/provider |

## Referências Grimoire ainda sem ID editorial próprio

Estas referências já aparecem em dossiês aceitos em `main`, mas **não receberam ID editorial neste passo** porque a entidade-fonte completa não foi recuperada/reconciliada. O UUID serve apenas para impedir duplicação futura.

| Referência | Tipo registrado na fonte do vínculo | UUID Grimoire | Proveniência GitHub | Estado operacional |
| --- | --- | --- | --- | --- |
| Maura | relação NPC | `76350f82-9af5-4cef-9c34-f66cf69cd2f7` | `NPC-0006` Elian | `BLOQUEADO — GRIMOIRE` |
| Ofícios do Pátio | facção | `c8408ee4-435c-4c85-9d61-5d4472197a88` | `NPC-0006` Elian | `BLOQUEADO — GRIMOIRE` |
| Pátio das Oficinas | local | `b015847c-7c60-4472-8d5b-31f44dcbc53e` | `NPC-0006` Elian | `BLOQUEADO — GRIMOIRE` |
| Coordenação de Sobrevivência do Entreposto | facção | `5988b526-9cd1-4b0c-b8b2-306f71043973` | `NPC-0005` Oren | `BLOQUEADO — GRIMOIRE` |
| Acampamento do Entreposto | local | `e98d8010-3824-46f2-8851-cb20fddd3128` | `NPC-0005` Oren | `BLOQUEADO — GRIMOIRE` |
| O Entreposto | local | `d540695a-a27a-42f8-83e4-089822918836` | `NPC-0005` Oren | `BLOQUEADO — GRIMOIRE` |

Até que cada source seja consultado, não decidir se um `local` deve virar `LOC-####` ou `SET-####`, não atribuir facção a `FAC-####` e não reservar novo `NPC-####` para Maura apenas pela existência do vínculo.

## Reconciliações concluídas nesta etapa
- `NPC-0003` Iren Valmor foi materializado no Grimoire como draft ligado a `FAC-0001` e `SET-0001`;
- `FAC-0001` Corte de Pedra Clara foi materializada no Grimoire como draft;
- `SET-0001` Pedra Clara foi materializada no Grimoire como draft;
- `NPC-0004` Liora foi materializada no GitHub a partir da entidade já ativa do Grimoire, preservando Ars Nouveau como provider específico e mantendo discovery/lifecycle não fixados;
- `NPC-0005` Oren e `NPC-0006` Elian foram materializados no GitHub a partir das entidades já ativas do Grimoire, com relações externas preservadas por UUID sem criação de entidades novas;
- o conflito Severin↔Aren foi registrado em fail-closed no GitHub, mas ainda não resolvido editorialmente;
- o antigo portrait candidate de Iren da PR #548 foi rejeitado e a PR foi fechada sem merge por corrupção/incompletude do arquivo e direção visual não aprovada.

## Ordem recomendada
1. resolver a identidade editorial Severin↔Aren antes de qualquer asset final do necromante;
2. recuperar/reconciliar `NPC-0002` Elias antes de qualquer autoria visual dele;
3. recuperar as seis entidades Grimoire relacionadas já referenciadas por Oren/Elian e só então decidir IDs editoriais, evitando duplicatas;
4. manter Iren/Liora/Oren/Elian e Corte/Pedra Clara sincronizados sem promover drafts/espelhos a fatos novos;
5. executar auditoria runtime do binding de `LOC-0001` e dos detalhes provider-native de Liora quando necessário;
6. só depois expandir eventos, diálogos e consequências que dependam desses fatos.

## Trabalho seguro que pode continuar
- QA editorial e de referências;
- portraits/look-dev apenas de NPCs cuja lore/identidade já esteja reconciliada e cujo asset brief seja source-grounded, sempre em novos arquivos completos/verificados;
- auditoria de provider/runtime sem promover candidatos a cânone;
- validação de knowledge, lifecycle, relações e causalidade;
- identificação de drift, duplicatas e referências órfãs;
- inventário de UUIDs Grimoire já citados no GitHub, desde que inventário não seja tratado como autorização para materialização.

## Critério de saída do backlog
Um item deixa esta fila apenas quando a authority necessária foi realmente consultada, a decisão foi registrada no dossiê correto, referências relacionadas foram revalidadas e nenhuma capability mecânica foi inferida sem prova.

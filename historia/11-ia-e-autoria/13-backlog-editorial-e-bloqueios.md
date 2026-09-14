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
8. asset visual rascunho não fixa sozinho aparência canônica nem identidade narrativa.

## Fila atual
| Área | Alvo | Estado | Authority/evidência exigida | Permitido agora | Proibido enquanto pendente |
| --- | --- | --- | --- | --- | --- |
| NPC | `NPC-0001` Severin ↔ Aren | `CONFLITO DE IDENTIDADE` | dossiês GitHub + entidade Aren no Grimoire + decisão editorial explícita | preservar ambos, comparar proveniência, manter assets como look-dev | assumir mesma pessoa ou pessoas distintas; copiar provider, aparência, segredos, motivações ou relações entre eles |
| NPC | `NPC-0002` Elias | `BLOQUEADO — GRIMOIRE` | Campaign Bible/registro anterior recuperado | preservar ID/nome e localizar proveniência | inventar aparência, função, personalidade, relações ou arco |
| NPC | `NPC-0003` Iren Valmor | `PRONTO` | dossiê versionado + draft reconciliado no Grimoire | continuar autoria derivada compatível; futura skin técnica e novo portrait verificado | duplicar identidade, conceder knowledge sem proveniência, promover look-dev a final sem QA |
| Facção | `FAC-0001` Corte de Pedra Clara | `PRONTO` para fatos já reconciliados / `PENDENTE DE DECISÃO EDITORIAL` para liderança nominal | dossiê + draft reconciliado no Grimoire | usar funções institucionais já registradas | inventar líder, conspiração global, jurisdição universal ou agenda secreta |
| Assentamento | `SET-0001` Pedra Clara | `PRONTO` para lore já reconciliada / `AUDITORIA MECÂNICA` para binding físico | dossiê + draft Grimoire + runtime/worldgen | usar fatos sociais existentes; auditar representação física separadamente | inferir bioma, coordenada, população ou capability mecânica não comprovada |
| Local | `LOC-0001` Região dos Ecos | `AUDITORIA MECÂNICA` | Compêndio/runtime + provider de worldgen + reconciliação geográfica com Grimoire | levantar candidatos com registry IDs reais | escolher bioma/dimensão/estrutura por estética, nome ou afinidade temática |
| Quest | `QST-0001` Ecos Sombrios | `PRONTO` como quest-test/lifecycle | dossiês de quest/evidência/knowledge | validar discovery, lifecycle e consequências | transformar evidência isolada em culpa/localização automática |
| Eventos | família `EVT-####` | `PENDENTE DE DECISÃO EDITORIAL` | evento concreto, causa/ator/tempo e consequência | criar quando existir fato persistente real | preencher diretório por completude artificial |
| Finais/epílogos | família `END-####` | `PENDENTE DE DECISÃO EDITORIAL` | estados persistentes/deriváveis do Narrative Core | definir fragmentos condicionais quando houver base | escrever rota final fixa ou moral score universal |
| Assets | portraits/skins NPC | `PRONTO` por NPC sem conflito de identidade | asset brief + aprovação visual + pipeline técnica | portrait HD e skin 64×64 separados | usar portrait como UV de skin; marcar `FINAL` sem QA; inventar símbolos/provider |

## Reconciliações concluídas nesta etapa
- `NPC-0003` Iren Valmor foi materializado no Grimoire como draft ligado a `FAC-0001` e `SET-0001`;
- `FAC-0001` Corte de Pedra Clara foi materializada no Grimoire como draft;
- `SET-0001` Pedra Clara foi materializada no Grimoire como draft;
- o conflito Severin↔Aren foi registrado em fail-closed no GitHub, mas ainda não resolvido editorialmente;
- o antigo portrait candidate de Iren da PR #548 foi rejeitado e a PR foi fechada sem merge por corrupção/incompletude do arquivo e direção visual não aprovada.

## Ordem recomendada
1. resolver a identidade editorial Severin↔Aren antes de qualquer asset final do necromante;
2. recuperar/reconciliar `NPC-0002` Elias;
3. manter Iren/Corte/Pedra Clara sincronizados sem promover drafts a fatos novos;
4. executar auditoria runtime do binding de `LOC-0001`;
5. só depois expandir eventos, diálogos e consequências que dependam desses fatos.

## Trabalho seguro que pode continuar
- QA editorial e de referências;
- portraits/look-dev de NPCs sem conflito de identidade, sempre em novos arquivos completos/verificados;
- auditoria de provider/runtime sem promover candidatos a cânone;
- validação de knowledge, lifecycle, relações e causalidade;
- identificação de drift, duplicatas e referências órfãs.

## Critério de saída do backlog
Um item deixa esta fila apenas quando a authority necessária foi realmente consultada, a decisão foi registrada no dossiê correto, referências relacionadas foram revalidadas e nenhuma capability mecânica foi inferida sem prova.

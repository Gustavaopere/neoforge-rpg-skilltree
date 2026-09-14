# Backlog editorial e bloqueios de cânone

## Estado
ATIVO COMO FILA OPERACIONAL / NÃO É CÂNONE NOVO.

## Objetivo
Registrar lacunas reais da campanha sem tratá-las como autorização para invenção. Este documento organiza o que pode avançar com as fontes atualmente disponíveis, o que exige reconciliação com o Grimoire/TTRPG.bot e o que depende de evidência mecânica do runtime/Compêndio.

Este backlog não substitui `historia/STATUS.md`, a Campaign Bible, os dossiês de entidade nem os contracts técnicos. Ele existe para impedir que campos vazios sejam completados por inferência apenas porque existe uma sequência de produção a manter.

## Estados operacionais

- `PRONTO`: pode avançar com fontes atuais sem criar fato novo não suportado.
- `AUDITORIA MECÂNICA`: pode levantar evidência técnica/candidatos, mas não fixar fato narrativo por conta própria.
- `BLOQUEADO — GRIMOIRE`: requer Campaign Bible/Foundations ou fonte anterior recuperada.
- `BLOQUEADO — RUNTIME`: requer prova de provider, registry ID, worldgen ou hook mecânico.
- `PENDENTE DE DECISÃO EDITORIAL`: fontes podem estar disponíveis, mas ainda não existe decisão de autoria aceita.
- `CONCLUÍDO`: requisito foi resolvido em fonte autoritativa e refletido nos dossiês apropriados.

## Regras de execução

1. ausência de informação nunca autoriza preencher uma lacuna;
2. lore estruturada central deve ser reconciliada com o Grimoire quando o domínio estiver registrado ali;
3. mecânica/provider/worldgen exige fonte técnica atual e não pode ser deduzida de tema, nome ou estética;
4. candidatos podem ser registrados sem virarem cânone;
5. um item só sai de `BLOQUEADO` quando a fonte que faltava foi realmente consultada;
6. este arquivo não deve receber segredos, culpados, soluções ou finais como resumo público; detalhes internos pertencem aos dossiês apropriados e continuam sujeitos ao modo sem spoilers.

## Fila atual

| Área | Alvo | Estado | Authority/evidência exigida | Permitido agora | Proibido enquanto bloqueado |
| --- | --- | --- | --- | --- | --- |
| NPC | `NPC-0002` Elias | `BLOQUEADO — GRIMOIRE` | Campaign Bible/Foundations ou fonte anterior recuperada | preservar ID/nome; localizar proveniência; registrar divergências | inventar aparência, função, personalidade, motivações, segredos, relações ou papel narrativo |
| NPC | `NPC-0001` Severin | `PRONTO` | dossiê canônico + ficha de autoria + estado narrativo real | escrever material derivado compatível; calibrar voz; preparar assets sem promover rascunho visual | conceder knowledge sem proveniência; transformar estética em alinhamento moral; fixar plot por fala de calibração |
| NPC | `NPC-0003` Iren Valmor | `PRONTO` | dossiê canônico + ficha de autoria + estado narrativo real | usar ficha/asset brief/`DLG-0002` como referência de consistência | duplicar perfil ou criar segunda versão concorrente sem reconciliação |
| Facção/instituição | `FAC-0001` Corte de Pedra Clara | `BLOQUEADO — GRIMOIRE` para liderança/agenda não registrada | Campaign Bible + dossiê versionado | usar somente funções já explicitadas; levantar necessidades mecânicas separadamente | inventar líder, agenda, jurisdição total, leis ou posição política ausente |
| Assentamento | `SET-0001` Pedra Clara | `AUDITORIA MECÂNICA` + `BLOQUEADO — GRIMOIRE` para fatos sociais não registrados | Compêndio/runtime/worldgen + Campaign Bible | levantar candidatos físicos; verificar MineColonies/worldgen; manter sociedade e representação mecânica separadas | declarar estrutura, política, leis, opinião pública ou sistema social que provider não possua |
| Local | `LOC-0001` Região dos Ecos | `AUDITORIA MECÂNICA` | catálogo geográfico do Compêndio/runtime + provider de worldgen | levantar registry IDs candidatos e fatos verificáveis; manter binding `NÃO FIXADO` | escolher bioma/dimensão/estrutura por estética, tradução ou nome |
| Eventos | família `EVT-####` | `PENDENTE DE DECISÃO EDITORIAL` | fato/evento aceito, consequência observável ou source material recuperado | preparar Event Ledger somente quando houver evento concreto a registrar | criar evento apenas para preencher `08-eventos/` ou justificar retrospectivamente uma região/quest |
| Evidências | família `EVD-####` | `PRONTO` dentro dos registros existentes | dossiês versionados + knowledge/proveniência | expandir apenas quando a nova evidência tem fonte, descoberta e consequência definidas | usar evidência como atalho para knowledge universal ou causa automática |
| Diálogo | `DLG-0001` e `DLG-0002` | `PRONTO` como calibração | fichas de autoria + knowledge disponível | testar voz e consistência sem mutação de estado | tratar calibração como encontro ocorrido/canônico |
| Diálogo | novos `DLG-####` | `PENDENTE DE DECISÃO EDITORIAL` | NPC suportado + contexto/precondições/knowledge | criar quando houver cena ou necessidade editorial concreta | produzir diálogo em massa para NPC sem dossiê ou knowledge comprovado |
| Finais/epílogos | família `END-####` | `PENDENTE DE DECISÃO EDITORIAL` | fatos persistentes/deriváveis do Narrative Core + contrato de composição | definir fragmentos somente quando condições reais existirem | escrever rota final fixa, moral score universal ou consequência sem estado que a sustente |
| Integração de lore | Grimoire ↔ GitHub | `BLOQUEADO — GRIMOIRE` nesta sessão quando a integração não estiver exposta | acesso real ao Grimoire/TTRPG.bot | continuar trabalho que não dependa de lacuna central; registrar necessidade de reconciliação | afirmar que Grimoire foi consultado; substituir Campaign Bible por inferência |

## Ordem recomendada quando o Grimoire estiver acessível

1. recuperar e reconciliar `NPC-0002` Elias;
2. confirmar quais campos ainda abertos de `FAC-0001` e `SET-0001` já existem na Campaign Bible;
3. reconciliar fatos geográficos já registrados antes de qualquer binding de `LOC-0001`;
4. revisar se eventos/causalidade atualmente implícitos merecem registros `EVT-####` reais;
5. somente então expandir diálogos, relações e consequências que dependam desses fatos.

## Trabalho que pode continuar sem Grimoire

- QA editorial e de referências sobre conteúdo já versionado;
- auditoria de registry IDs/providers/worldgen sem promover candidatos a cânone;
- validação de contratos de knowledge, lifecycle, relações e causalidade;
- preparação de briefs/assets explicitamente marcados como rascunho;
- revisão de compatibilidade entre narrativa e mecânicas realmente presentes;
- identificação de drift, duplicatas, referências órfãs e documentação histórica obsoleta.

## Critério de saída do backlog

Um item deixa esta fila apenas quando:

1. a fonte de autoridade necessária foi consultada;
2. a decisão foi registrada no dossiê correto;
3. referências relacionadas foram revalidadas;
4. nenhuma capability mecânica foi inferida sem prova;
5. o estado editorial (`CANÔNICO`, `RASCUNHO`, `PROPOSTA`, `BLOQUEADO`, etc.) ficou explícito.

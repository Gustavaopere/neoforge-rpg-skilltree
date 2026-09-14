# DLG-0003 — Iren Valmor — Possível oferta de investigação

## Estado editorial
RASCUNHO

## Escopo
CANDIDATO PLAYER-FACING / requer validação contra runtime antes de implementação. O arquivo descreve intents e não muta estado diretamente.

## Participantes
- `NPC-0003` Iren Valmor
- jogador

## Contexto
Diálogo que pode apresentar `QST-0001` quando Iren possui knowledge suficiente para justificar uma investigação e decide envolver o jogador. Não presume que Iren conhece `NPC-0001`.

## Precondições editoriais
- `QST-0001` availability = `ELIGIBLE`;
- resolution ainda permite investigação relevante;
- `NPC-0003` está disponível e possui evidence/knowledge suficiente por canal rastreável;
- offer ainda é semanticamente válida para o estado atual;
- alternate-entry/pre-resolution foi reconciliado antes de entrar no nó padrão.

## Knowledge exigido de NPC-0003
Iren deve possuir ao menos uma combinação suficiente de relatos/registro/evidência para justificar investigação. O authoring/runtime precisa registrar quais peças ele efetivamente conhece; o diálogo não pode acessar `EVD-0001..0003` por simples existência global.

## Relações/estado relevantes
A relação pode alterar tom, quantidade de contexto e disposição de Iren em compartilhar detalhes, mas não muda a verdade dos fatos.

## Entrada padrão — pedido sem acusação
Iren: "Tenho ocorrências demais na mesma região para continuar chamando tudo de coincidência e evidência de menos para chamar de qualquer outra coisa. Preciso de observação de campo, não de uma caça a culpados."

Iren: "Se você aceitar, procure o que ainda pode ser verificado. Não vá tentando provar a minha hipótese. Eu ainda não tenho uma que mereça esse nome."

## Ramos

### Ramo A — aceitar
- jogador: "Eu investigo."
- Iren: "Então comece pela região, não pelas histórias. Registre o que encontrar antes de interpretar. Se houver pessoas envolvidas, não assuma hostilidade só porque preferem não ser encontradas."
- intent: solicitar transição de engagement para `ACCEPTED` apenas após validação do Narrative Core;
- discovery: no mínimo `DISCOVERED`, limitado ao conteúdo legitimamente compartilhado nesta conversa;
- próximo: journal/objetivo player-safe compatível com o knowledge atual.

### Ramo B — recusar
- jogador: "Não vou me envolver."
- Iren: "Certo. Prefiro uma recusa clara a alguém aceitando por educação e desaparecendo depois. A investigação não depende da sua concordância para continuar existindo."
- intent: solicitar `DECLINED`;
- consequência: nenhuma punição automática; progressão autônoma segue apenas se causas já estiverem declaradas.

### Ramo C — pedir evidências
- jogador: "O que você tem de concreto?"
- regra de composição: **não existe uma resposta fixa que enumere todas as evidências**. A fala deve ser composta somente pelas classes/peças que `NPC-0003` realmente conhece naquele estado, preservando a proveniência e a incerteza de cada uma.
- fragmento se Iren conhece ao menos um relato de `EVD-0001` ou equivalente: "Tenho um relato que merece ser verificado. Ainda é informação de uma fonte, não confirmação do que ocorreu."
- variante somente se Iren conhece múltiplos relatos realmente incompatíveis entre si: "Tenho relatos que não concordam nos detalhes. Isso é motivo para comparar fontes, não para escolher a versão mais dramática."
- fragmento se Iren conhece `EVD-0002` ou análise cartográfica equivalente: "Há uma concentração geográfica que pode ser padrão real ou viés de coleta. Ainda preciso separar as duas coisas."
- fragmento se Iren conhece `EVD-0003` ou informação de campo equivalente: "Há informação de campo que justifica olhar mais de perto, mas a proveniência e a interpretação ainda precisam de cuidado."
- composição: quando mais de um fragmento for legítimo, Iren pode condensá-los em uma resposta curta; desconhecer uma peça nunca autoriza mencioná-la, negar sua existência ou antecipar sua descoberta.
- intent: pode elevar discovery do jogador conforme informação efetivamente transmitida; não altera engagement sozinho.

### Ramo D — exigir um suspeito
- jogador: "Quem você acha que está fazendo isso?"
- Iren: "Se eu tivesse uma resposta defensável, não a apresentaria como palpite. Um nome precoce contamina testemunhas, busca e interpretação. Por enquanto, procure fatos que sobreviveriam mesmo se estivermos errados sobre a pessoa."
- intent: nenhum.

### Ramo E — jogador já encontrou EVD-0003
Precondição: o jogador possui knowledge legítimo de `EVD-0003` ou fato equivalente.

- jogador: "Já estive naquela região. Encontrei um lugar que parece ter sido usado em segredo."
- Iren: "Usado por quem?"
- jogador: "Não sei."
- Iren: "Ótimo. Mantenha esse 'não sei'. Agora me diga o que faz você chamar o uso de oculto. E encontrou sinais de repetição ou só de uma passagem? São afirmações diferentes."
- regra: reconciliar alternate entry; não pedir ao jogador para repetir descoberta já realizada;
- intent: compartilhar knowledge com `NPC-0003` apenas através do pipeline autorizado e registrar proveniência como relato do jogador, não como observação direta de Iren.

### Ramo F — jogador já possui resolução relevante
Precondição: world state indica `PRE_RESOLVED`, `RESOLVED_BY_OTHERS`, `OBSOLETE` ou `TRANSFORMED` incompatível com a oferta padrão.

O nó padrão **não deve executar**. Usar diálogo reconciliado/contextual específico do estado real. Nunca oferecer a versão antiga da investigação como se nada tivesse acontecido.

### Ramo G — aceitar mas questionar autoridade
- jogador: "Por que eu deveria fazer trabalho da corte?"
- Iren: "Não deveria, por obrigação. Estou pedindo porque você pode circular sem transformar uma investigação em espetáculo institucional. Se isso não é motivo suficiente, recuse."
- intent: mantém `OFFERED` até escolha explícita;
- nota: preservar autonomia do jogador; não tratar pergunta como aceite ou recusa.

## Saídas do diálogo
- accepted: compromisso registrado somente após validação;
- declined: recusa distinta de nunca oferecido;
- undecided: conversa pode terminar mantendo `OFFERED`;
- alternate entry: reconciliar com histórico antes de qualquer objetivo novo;
- invalidated/resolved: diálogo padrão não aparece.

## Intents/consequências solicitadas
Este arquivo descreve intents. Texto nunca muta estado diretamente.

Intents possíveis:
- engagement accept/decline;
- discovery/knowledge grant limitado ao que foi dito/mostrado;
- knowledge transfer player -> `NPC-0003` com provenance;
- journal presentation após confirmação do Narrative Core.

## Arcos/quests relacionados
- `QST-0001`.

## NPCs/facções/locais/evidências/eventos relacionados
- `NPC-0003`;
- `EVD-0001`;
- `EVD-0002`;
- `EVD-0003`;
- `NPC-0001` apenas quando o estado legítimo de knowledge permitir; o diálogo padrão não revela esse vínculo.

## Invariantes
- Iren não apresenta suspeito sem evidência;
- aceitar não congela o mundo;
- recusar não cria punição automática;
- pedir contexto não conta como aceitar;
- descobrir algo antes da oferta é reconhecido;
- conhecimento transmitido ao NPC preserva origem no jogador;
- uma resposta sobre evidências só pode mencionar peças/classes realmente conhecidas por Iren;
- divergência entre relatos só pode ser mencionada quando Iren conhece múltiplos relatos incompatíveis;
- diálogo padrão não executa se a oportunidade já mudou de estado.

## Notas de voz
Iren evita linguagem mística genérica. Quando não sabe, diz explicitamente. A precisão não deve virar exposição longa: a maior parte de suas falas player-facing deve caber em poucos períodos.

## QA
- [x] nenhum participante sabe informação sem proveniência;
- [x] offer depende de estado real;
- [x] aceitar/recusar permanecem distintos de discovery;
- [x] consequências são intents validados, não mutações por texto;
- [x] alternate entry possui ramo próprio;
- [x] resposta de evidências é condicionada ao knowledge real de `NPC-0003`;
- [x] conflito entre relatos não é inferido de um único relato conhecido;
- [x] não há culpado/solução expostos;
- [x] perda de evidência individual não bloqueia toda a quest;
- [x] não há provider inventado.

## Spoilers internos
Nenhuma solução ou identidade é definida por este diálogo.

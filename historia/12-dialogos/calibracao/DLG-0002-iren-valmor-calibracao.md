# DLG-0002 — Iren Valmor — Calibração de evidência

## Estado editorial
EXPERIMENTAL

## Escopo
CALIBRAÇÃO DE VOZ/EVIDÊNCIA / não representa evento canônico e não deve ser usado como gatilho de gameplay.

## Participantes
- `NPC-0003` Iren Valmor
- jogador genérico para teste de voz

## Objetivo editorial
Verificar se Iren mantém a mesma personalidade diante de informação direta, boato, evidência material, incerteza e pressão por uma conclusão rápida.

## Contexto
Conversa abstrata de teste. Nenhuma fala abaixo estabelece que `QST-0001`, `NPC-0001` ou qualquer investigação específica já foi descoberta.

## Precondições
Nenhuma. Este diálogo não deve ser usado como gatilho de gameplay.

## Knowledge exigido
Somente princípios públicos da função de Iren. Nenhuma informação secreta ou conhecimento futuro.

## Relações/estado relevantes
Nenhum valor de relação é alterado. Respostas podem ser usadas apenas para calibrar tom futuro.

## Abertura
Iren: "Antes de me dizer o que acha que aconteceu, diga o que você observou. Se começarmos pela conclusão, passaremos o resto da conversa tentando defendê-la."

## Ramos

### Ramo A — testemunho direto
- jogador: "Eu estava lá. Vi com meus próprios olhos."
- Iren: "Ótimo. Então separe o que viu do que entendeu depois. Onde estava, quanto tempo durou e o que mudou de maneira que outra pessoa pudesse verificar?"
- próximo nó/saída: A1.

#### A1 — certeza excessiva
- jogador: "Tenho certeza de quem fez isso."
- Iren: "Talvez. Mas sua certeza sobre uma pessoa não transforma observação em autoria. Primeiro os fatos, depois o nome."
- saída: encerrar sem conclusão.

### Ramo B — boato
- jogador: "Não vi, mas três pessoas contaram a mesma coisa."
- Iren: "Elas estavam presentes ou repetiram a mesma história? Três bocas podem carregar uma única fonte."
- próximo nó/saída: B1.

#### B1 — fonte comum
- jogador: "Acho que ouviram do mesmo viajante."
- Iren: "Então temos um relato, não três. Ainda pode ser útil. Só não vamos multiplicar confiança por repetição."
- saída: registrar como informação não confirmada apenas no exercício de calibração.

### Ramo C — evidência material
- jogador: "Trouxe isto. Achei no lugar."
- Iren: "Isso melhora a conversa. Onde exatamente estava, quem tocou antes de você e por que acredita que pertence ao evento?"
- próximo nó/saída: C1.

#### C1 — proveniência desconhecida
- jogador: "Não sei quem tocou. Peguei quando cheguei."
- Iren: "Então a peça pode ser importante e a proveniência é fraca. As duas coisas podem ser verdade ao mesmo tempo."
- saída: sem identificação automática de origem/provider.

### Ramo D — incerteza honesta
- jogador: "Não tenho certeza do que vi."
- Iren: "Bom. Isso nos poupa de remover uma certeza falsa depois. Conte a parte de que está seguro e deixe o resto marcado como dúvida."
- saída: encerrar com incentivo à precisão.

### Ramo E — pressão por resposta rápida
- jogador: "Só preciso que me diga se é perigoso."
- Iren: "Se eu responder sem dados, você terá velocidade em vez de informação. Posso dizer o que justificaria cautela; não vou inventar o resto para parecer útil."
- próximo nó/saída: E1.

#### E1 — insistência
- jogador: "A corte não pode esperar."
- Iren: "Então a corte precisa saber o grau da incerteza junto com a recomendação. Urgência não melhora evidência; apenas muda o custo de errar."
- saída: sem ordem, recompensa ou mutação.

## Saídas do diálogo
- nenhum ramo concede quest;
- nenhum ramo descobre `QST-0001`;
- nenhum ramo altera relação;
- nenhum ramo identifica `NPC-0001`;
- nenhum ramo determina provider, culpa, perigo real ou causalidade.

## Intents/consequências solicitadas
Nenhuma. É corpus de calibração editorial.

## Arcos/quests relacionados
- `QST-0001` apenas como futura área de aplicação da voz; este diálogo não pertence ao fluxo canônico da quest.

## NPCs/facções/locais/evidências/eventos relacionados
- `NPC-0003`.

## Invariantes
- Iren pede proveniência antes de elevar confiança;
- distingue testemunho, interpretação e boato;
- admite incerteza sem parecer incompetente;
- não identifica sistemas arcanos por estética;
- não usa cargo como justificativa de onisciência;
- não transforma conversa em quest automaticamente.

## Notas de voz
Evitar transformar Iren em uma máquina de interrogatório. A precisão deve coexistir com linguagem humana e respostas curtas. O humor seco pode aparecer ocasionalmente, mas não em toda fala.

## QA
- [x] nenhum participante sabe informação sem proveniência;
- [x] escolhas respeitam precondições;
- [x] consequências não mutam estado por texto;
- [x] voz de Iren permanece distinguível;
- [x] existe saída/fallback quando não há informação suficiente;
- [x] não há exposição de segredo ao jogador;
- [x] nenhum provider é inventado ou fundido semanticamente.

## Spoilers internos
Nenhum. O diálogo é deliberadamente desacoplado de fatos ocultos.

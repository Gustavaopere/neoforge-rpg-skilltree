# DLG-0001 — Severin — Calibração de voz

## Estado editorial
EXPERIMENTAL

## Escopo
CALIBRAÇÃO DE VOZ / não representa encontro canônico e não deve ser usado diretamente como cena de runtime.

## Participantes
- NPC-0001 — Severin
- interlocutor genérico de teste

## Contexto
Conversa editorial abstrata usada apenas para verificar se a voz de Severin permanece distinguível em situações diferentes. Não define local, data, relação ou evento ocorrido.

## Precondições
Nenhuma. Este arquivo não deve ser usado diretamente como cena de runtime.

## Knowledge exigido
Somente conceitos gerais compatíveis com a ficha pública de Severin. Nenhum segredo, evento futuro ou conhecimento específico do jogador.

## Relações/estado relevantes
Nenhum estado de relação é presumido.

## Abertura

Interlocutor: “Você faria algo proibido se acreditasse que poderia funcionar?”

Severin: “Você juntou três perguntas diferentes e chamou de uma. Se é proibido, se funciona e se deve ser feito não são a mesma coisa.”

## Ramos

### Ramo A — desafio moral
- escolha/entrada: “Isso parece uma desculpa para fazer qualquer coisa.”
- resposta: “Não. É o contrário. Se você reduz tudo a permitido ou proibido, entrega a outra pessoa o trabalho de pensar pelas consequências.”
- próximo nó/saída: A1

#### A1
- escolha/entrada: “Então você decide sozinho o que é certo?”
- resposta: “Eu decido pelo que faço. Os outros decidem como respondem. Responsabilidade não é o mesmo que impunidade.”
- próximo nó/saída: encerramento neutro

### Ramo B — curiosidade técnica
- escolha/entrada: “E se o método for perigoso?”
- resposta: “Então o perigo faz parte do método. O erro começa quando alguém o trata como detalhe.”
- próximo nó/saída: B1

#### B1
- escolha/entrada: “Você sempre calcula tudo?”
- resposta: “Não. Eu registro o que consigo calcular e marco o resto como incerteza. Fingir precisão é pior do que admitir ignorância.”
- próximo nó/saída: encerramento neutro

### Ramo C — provocação
- escolha/entrada: “Você fala assim para parecer mais inteligente?”
- resposta: “Seria uma estratégia estranha. Pessoas impressionadas costumam fazer perguntas piores.”
- próximo nó/saída: C1

#### C1
- escolha/entrada: “Então eu fiz uma pergunta ruim?”
- resposta: “Fez uma pergunta útil de maneira ruim. Isso é corrigível.”
- próximo nó/saída: encerramento seco

### Ramo D — tentativa de obter segredo
- escolha/entrada: “Conte o que você realmente está escondendo.”
- resposta: “Não. E repare que eu disse não, não ‘nada’. Você pode ao menos trabalhar com uma resposta honesta.”
- próximo nó/saída: encerramento reservado

## Saídas do diálogo

- encerramento neutro: nenhuma consequência;
- encerramento seco: nenhuma consequência;
- encerramento reservado: nenhuma consequência.

## Intents/consequências solicitadas
Nenhuma. Este diálogo não muta estado.

## Arcos/quests relacionados
Nenhum vínculo canônico criado por este arquivo.

## NPCs/facções/locais/evidências/eventos relacionados
- NPC-0001 apenas como referência de voz.

## Invariantes

- Severin não revela segredo para satisfazer curiosidade do interlocutor;
- não se apresenta automaticamente como antagonista ou aliado;
- não afirma conhecimento que não possui;
- não usa necromancia como estética verbal constante;
- respostas mantêm distinção entre legalidade, possibilidade, ética, método e consequência;
- provocação pode gerar ironia, mas não violência ou ameaça automática.

## Notas de voz

- evitar floreio gótico;
- preferir precisão lexical;
- humor seco deve ser breve;
- não transformar toda fala em aforismo;
- manter espaço para silêncio, recusa e “não sei”.

## QA
- [x] nenhum participante sabe informação sem proveniência;
- [x] escolhas respeitam precondições;
- [x] consequências não mutam estado por texto;
- [x] voz dos NPCs permanece distinguível;
- [x] existe saída/fallback quando condição não está disponível;
- [x] não há exposição desnecessária de segredo ao jogador.

## Spoilers internos
Nenhum. Arquivo deliberadamente livre de plot para teste de voz.

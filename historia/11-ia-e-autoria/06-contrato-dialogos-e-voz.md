# Contrato de Diálogos e Voz de NPC

## Objetivo
Garantir que cada NPC tenha voz própria e que diálogos respeitem conhecimento, estado do mundo e autoridade do Narrative Core.

## IDs
Diálogo versionado usa `DLG-####`. IDs não são reciclados depois de entrarem em `main`.

## Voz do NPC
Cada NPC narrativamente relevante deve poder declarar:

- nível de formalidade;
- comprimento típico das frases;
- ritmo e pausas;
- vocabulário recorrente;
- humor e ironia;
- grau de franqueza ou evasão;
- assuntos que evita;
- formas de tratamento;
- emoções que tenta esconder;
- padrões de fala que não combinam com ele;
- exemplos curtos de calibração.

Exemplos de calibração não são eventos canônicos por si mesmos.

## Conhecimento
Toda fala que revela fato importante deve ser compatível com o conhecimento disponível ao NPC.

Um diálogo não pode inventar que o personagem conhece segredo, testemunha, evidência, local ou evento apenas porque isso produz uma cena melhor.

## Estrutura editorial mínima

Cada diálogo deve declarar:

- ID;
- estado editorial;
- participantes;
- contexto;
- precondições;
- conhecimento necessário por participante;
- abertura;
- escolhas possíveis;
- respostas;
- saídas/finais do diálogo;
- intents ou consequências solicitadas ao Narrative Core;
- referências a NPCs, quests, arcos, locais, evidências e eventos relacionados.

## Consequências
Texto de diálogo não altera o save diretamente. Ele pode solicitar uma intent/consequência que deverá ser validada e executada pelo domínio autorizado.

## Branching

Markdown continua como formato editorial padrão enquanto for suficiente. Ink/Inky só será adotado após PoC quando a quantidade de ramificações tornar o formato atual difícil de revisar.

## QA de voz

Para NPCs principais, manter um pequeno corpus com falas em situações diferentes: neutra, sob pressão, irritada, vulnerável, enganando, negociando e falando com alguém de status diferente quando aplicável.

A revisão deve procurar:

- vozes excessivamente parecidas;
- exposição artificial;
- informação impossível;
- mudança súbita de vocabulário;
- fala que existe apenas para explicar mecânica ao jogador;
- respostas que ignoram relação, medo, confiança ou contexto.

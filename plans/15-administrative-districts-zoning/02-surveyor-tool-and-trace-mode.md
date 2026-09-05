# 15.02 — Ferramenta de topografia e modo de traçado

## Fluxo

1. jogador ativa modo de distrito;
2. clique válido adiciona vértice;
3. cliente mostra preview local dos segmentos;
4. servidor mantém sessão limitada e valida cada intenção;
5. fechar no primeiro ponto/ação explícita propõe o polígono;
6. servidor executa validação completa;
7. confirmação cria/revisa `DistrictRecord`.

## Autoridade e segurança

Cliente envia somente intenções/posições observadas. Servidor verifica:

- permissão administrativa;
- distância razoável do jogador ao ponto conforme modo escolhido;
- world/dimension correto;
- número máximo de vértices;
- claim/colony constraints quando configurados;
- geometria e overlap.

## Edição

Modo separado permite mover/adicionar/remover vértice. A alteração só publica uma nova revision após validar o polígono completo; falha mantém a versão anterior.

## UX pt-BR

Mensagens devem explicar: ponto adicionado, segmento inválido, cruzamento, área fora da colônia, conflito com distrito, fechar/cancelar e revision criada.

## Testes

- spam C2S rate-limited;
- sessão expira em logout/dimension change;
- cancelamento não grava;
- edição inválida preserva original;
- jogador sem permissão não cria distrito;
- polygon concave via ferramenta.

## Acceptance

O jogador consegue desenhar fronteiras orgânicas sem editar JSON e sem conceder autoridade geométrica ao cliente.
# Composição de Epílogos

## Estado editorial
RASCUNHO

## Escopo
Contrato editorial de composição, sem finais concretos. O merge deste documento não promove automaticamente nenhum `END-####` futuro a cânone.

## Autoridade técnica
Este documento traduz para autoria o contrato de `plans/08-quests-progression-hooks/21-campaign-eras-epilogues.md`.

O Narrative & Society Core continua autoridade sobre estado persistente, facts, relações, facções, assentamentos, políticas, histórico de quests, ordering e resolução de condições. `historia/` define o conteúdo editorial que poderá ser selecionado a partir desse estado.

## Regra principal
Não existe uma única escolha final A/B que determine sozinha o estado do mundo.

Um epílogo pode ser composto por múltiplos `END-####` compatíveis, selecionados a partir de estados persistentes construídos durante a campanha.

`END-####` pode representar:

- um fragmento de epílogo sobre uma dimensão do mundo;
- uma resolução de personagem/facção/assentamento;
- uma consequência de longo prazo;
- uma combinação especial que substitui fragments incompatíveis;
- um epílogo completo apenas quando o conteúdo realmente exigir uma peça indivisível.

Não criar uma segunda família de IDs apenas para fragments.

## Eixos iniciais
O Stage 08 define como eixos iniciais:

- civilization;
- technology;
- magic policy;
- necromancy policy;
- vampirism balance;
- ecology;
- Shroud state;
- Black Arcana policy;
- space expansion.

A pasta editorial já prevê ainda dimensões persistentes como governo, situação de facções, relações pessoais, crises não resolvidas e fatos nunca descobertos.

Esses eixos são fontes de condição, não sliders obrigatórios nem enumerações fechadas. Novos eixos precisam corresponder a estado narrativo realmente persistido/derivável.

## Estado observado, não moralidade hardcoded
Fragments devem consultar fatos e estados, não uma classificação global de “bom/mau final”.

Exemplos de fontes válidas quando o runtime correspondente existir:

- facts globais ou históricos;
- estado de settlement/institution/faction;
- políticas/leis;
- relações persistentes;
- Event Ledger;
- resolução de quests/arcos;
- knowledge/discovery do jogador quando a diferença de saber ou não saber for relevante ao texto;
- Identity Continuity Record;
- estados/provider facts comprovados;
- crises ativas ou resolvidas.

Não criar score moral universal apenas para escolher epílogo.

## Fragmentos condicionais
Cada `END-####` componível deve declarar:

- estado editorial;
- domínio/eixo principal;
- condições necessárias;
- condições de exclusão;
- prioridade/ordering quando necessário;
- fragments incompatíveis ou substituídos;
- fatos/eventos/entidades referenciados;
- variante player-facing PT-BR;
- o que o fragmento NÃO assume;
- spoilers internos quando aplicável.

## Conflitos
Dois fragments não podem aparecer juntos quando afirmam estados mutuamente exclusivos.

Conflitos devem ser resolvidos de maneira explícita e determinística por:

1. condições mais específicas;
2. exclusões declaradas;
3. prioridade/ordering definido;
4. fragmento de combinação/substituição quando a interação merece texto próprio.

Não depender da ordem acidental de arquivos.

## Ordering
A apresentação final precisa ser determinística.

O ordering editorial deve poder agrupar fragments por função, por exemplo:

1. estado macro do mundo/civilização;
2. assentamentos e instituições centrais;
3. tecnologia/magia/políticas sistêmicas;
4. facções e conflitos persistentes;
5. NPCs/relacionamentos;
6. exploração/territórios;
7. crises abertas e consequências futuras;
8. coda da campanha quando existir.

Essa ordem é orientação de apresentação, não ordem cronológica obrigatória nem contrato de runtime rígido.

## História específica do jogador
O texto pode usar detalhes nominais do histórico real do jogador quando eles estiverem disponíveis de forma legítima: nomes de atores, eventos presenciados, decisões, relações ou lugares relevantes.

Não inventar participação do jogador em fatos que ele não viveu/conheceu apenas para tornar a narração mais dramática.

## Knowledge e fatos nunca descobertos
Um fato verdadeiro pode afetar o mundo sem ter sido descoberto pelo jogador.

O epílogo deve distinguir:

- consequência objetiva do estado do mundo;
- conhecimento que o personagem jogador realmente possui;
- informação que o texto pode ou não revelar ao jogador naquele momento.

Não transformar o epílogo automaticamente em exposição onisciente de todos os segredos da campanha.

## NPCs, morte e retorno
Fragments de NPC devem consultar o estado real e, quando aplicável, continuidade de identidade.

Não usar apenas `alive/dead` quando memória, personalidade, relações, knowledge, identidade legal/social ou retorno por provider alterarem o resultado.

## Providers
Um fragmento pode refletir consequência de um provider somente quando o estado correspondente existir no runtime/bridge real.

Não usar epílogo para canonizar capability inexistente, fundir recursos distintos ou explicar todos os hazards por uma mesma origem.

## Segunda Concordância
A Segunda Concordância é uma possibilidade macro, não um epílogo obrigatório nem uma recompensa universal.

Quando existir, sua forma deve emergir da combinação dos estados construídos na campanha. Ela não precisa reproduzir a Primeira Concordância e não é automaticamente “o final bom”.

## Pós-campanha
Epílogo não precisa congelar o save.

O Stage 08 prevê conteúdo pós-campanha e crises futuras. Fragments podem encerrar uma trajetória e ainda indicar estados persistentes que continuam válidos para novas quests/eventos.

## QA
Antes de aceitar um `END-####`:

- [ ] condições dependem de estado real/derivável;
- [ ] não existe score moral universal implícito;
- [ ] conflitos com outros fragments estão declarados;
- [ ] ordering não depende de ordem de arquivos;
- [ ] texto não inventa knowledge do jogador;
- [ ] morte/retorno respeita continuidade de identidade;
- [ ] provider capability foi verificada quando necessária;
- [ ] fragmento não exige que uma única rota da campanha tenha ocorrido;
- [ ] texto player-facing está em PT-BR;
- [ ] spoilers permanecem no nível deliberadamente escolhido para o epílogo.

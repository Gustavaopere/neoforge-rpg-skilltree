# SET-0003 — Acampamento do Entreposto

## Estado editorial
RASCUNHO ESTRUTURADO / representação versionada de entidade ativa no Grimoire.

A entidade-fonte está na categoria genérica `locations` e usa `location_type=district`, mas sua descrição canônica a define como o primeiro núcleo habitado da campanha: um acampamento de sobrevivência que antecede qualquer cidade formal. Por isso recebe `SET-####`, conforme a taxonomia editorial de assentamentos, sem transformar o tipo genérico do schema em uma afirmação de urbanização madura.

## Referência cruzada
- stable editorial ID: `SET-0003`;
- Grimoire entity UUID: `e98d8010-3824-46f2-8851-cb20fddd3128`;
- estado da fonte: `active`;
- `location_type` do schema Grimoire: `district`;
- população inicial registrada: `5`.

## Identidade pública
Primeiro núcleo habitado canônico da campanha. Começa como acampamento de sobrevivência instalado junto a `LOC-0002` O Entreposto, antes de existir cidade formal.

“Acampamento do Entreposto” é um nome funcional. A fonte permite que o nome mude quando a comunidade adquirir identidade própria; uma mudança diegética de nome não exige reciclar o ID estável enquanto a continuidade do assentamento permanecer reconhecível.

## Estado social inicial
Núcleo misto formado por sobreviventes de origens diferentes. Sua identidade coletiva ainda está nascendo.

Decisões sobre sobrevivência, acolhimento, preservação e risco podem criar precedentes. Precedente não equivale automaticamente a lei, governo maduro ou consenso social.

## Estrutura física registrada
- abrigos improvisados;
- estoque comum inicial;
- área de trabalho e reparo;
- proximidade imediata de `LOC-0002` O Entreposto.

O assentamento **não começa como cidade** e **não começa como colônia politicamente madura**.

Forma física final, blocos, layout, biome, coordenadas e provider de construção permanecem **NÃO FIXADOS**.

## Governo / instituição
- `FAC-0003` Coordenação de Sobrevivência do Entreposto surge aqui de forma informal.

A Coordenação distribui responsabilidades emergenciais; sua existência não deve ser reescrita como governo pleno, conselho formal ou autoridade jurídica consolidada.

## População
A fonte registra população inicial `5`, sem autorizar este arquivo a atribuir nomes, cargos, relações ou destino aos cinco habitantes.

Os cinco UUIDs de membros-chave registrados em `FAC-0003` permanecem referências do Grimoire até reconciliação individual.

## Relações espaciais registradas
- conectado a `LOC-0002` O Entreposto — UUID Grimoire `d540695a-a27a-42f8-83e4-089822918836`;
- parent location UUID `e05ef102-46c3-4f31-8043-ee2eda6d99af` permanece **SEM ID EDITORIAL / NÃO RECONCILIADO** neste passo.

A referência ao parent UUID é preservada como dado-fonte; este dossiê não inventa nome, escala, território, distância ou autoridade para essa entidade ainda não recuperada.

## Evolução possível
A fonte registra que, após a primeira crise coletiva, a Coordenação de Sobrevivência **pode** evoluir para um Conselho Funcional se a composição da comunidade e os sobreviventes presentes justificarem.

Isso é possibilidade condicional. Não é evento futuro garantido, capítulo obrigatório nem proteção de personagens necessários à transição.

## Provider/runtime
Nenhum provider físico é definido por este dossiê.

MineColonies pode futuramente representar população, construções, profissões ou infraestrutura se o runtime real e o design adotado justificarem. Isso não transformaria MineColonies em authority de identidade coletiva, precedentes, governo, knowledge ou história interna.

Nenhum sistema de estoque, política, opinião pública ou governança é presumido sem hook real.

## Discovery
- não fixada;
- a existência canônica do acampamento não significa que todo ator saiba onde ele está;
- descoberta, chegada do protagonista e sequência de encontro precisam ser sustentadas por estado narrativo próprio;
- proximidade com `LOC-0002` não concede compreensão histórica automática da ruína.

## Invariantes
- começa como acampamento de sobrevivência, não como cidade formal;
- população inicial registrada é 5, sem identities inferidas neste arquivo;
- decisões emergenciais podem criar precedentes, mas não leis retroativas automáticas;
- `FAC-0003` é coordenação informal, não governo pleno;
- worldgen, coordenadas, blocks e provider físico permanecem abertos;
- o parent UUID não recebe identidade inventada;
- evolução institucional futura depende do estado real da campanha.

## Spoilers internos já registrados no Grimoire
A Coordenação de Sobrevivência surge informalmente neste núcleo. A possibilidade de transformação posterior em Conselho Funcional depende da primeira crise coletiva, da composição efetiva e de quem continuar presente.

Nenhum resultado dessa crise é canonizado por este dossiê.

# SET-0002 — Pátio das Oficinas

## Estado editorial
RASCUNHO ESTRUTURADO / representação versionada de entidade ativa no Grimoire.

A entidade-fonte está na categoria genérica `locations`, porém sua própria descrição a define como **pequeno assentamento**. Por isso recebe `SET-####`, seguindo a taxonomia editorial de `historia/06-assentamentos/README.md`, sem transformar o campo técnico `location_type=city` em afirmação de escala urbana maior do que a fonte permite.

## Referência cruzada
- stable editorial ID: `SET-0002`;
- Grimoire entity UUID: `b015847c-7c60-4472-8d5b-31f44dcbc53e`;
- estado da fonte: `active`;
- `location_type` do schema Grimoire: `city`.

## Identidade pública
Núcleo técnico canônico registrado como A3 em R3. Pequeno assentamento de manutenção, reocupação e reaproveitamento que vive de conservar estruturas úteis, adaptar materiais e recuperar componentes sem possuir tecnologia industrial avançada.

Os marcadores `A3` e `R3` são preservados como terminologia da fonte. Este arquivo não inventa seu significado geográfico, administrativo ou cronológico.

## Cultura e práticas
Comunidade de ofícios técnicos baseada em:
- aprendizagem prática;
- registro de intervenções;
- debate entre conservação, uso e recuperação de materiais;
- distinção entre competência técnica e autoridade sobre estruturas de outras comunidades.

Sua relação com ruínas é prática e disputada: preservar, estabilizar e desmontar são decisões diferentes.

## Estrutura física registrada
- oficinas modestas;
- áreas de triagem de materiais recuperados;
- espaços de levantamento e reparo;
- pequenos estoques especializados.

Forma física final, população de fundo, blocos, coordenadas, bioma e posição exata do marcador T2 dependem de worldgen/runtime e permanecem **NÃO FIXADOS**.

## Governo / instituições
A fonte não define governo municipal formal. A instituição explicitamente relacionada é:
- `FAC-0002` Ofícios do Pátio — organização local de coordenação técnica, não autoridade regional automática.

Nenhuma equivalência entre guilda técnica e governo pleno é inferida.

## NPCs relacionados
- `NPC-0006` Elian — levantamento/documentação;
- `NPC-0007` Maura — recuperação de materiais.

Outros membros de `FAC-0002` permanecem por UUID até reconciliação individual.

## Relações espaciais registradas
- conexão com Pátio da Passagem — UUID Grimoire `14e39b79-a0de-4bf0-9bbe-9c11ae5b95a9`.

Pátio da Passagem não recebe ID editorial neste arquivo. A existência do vínculo não define distância, estrada, direção, tempo de viagem ou hierarquia territorial.

## Relação com O Entreposto
A fonte registra explicitamente que `SET-0002` **não possui direito prévio sobre `LOC-0002` O Entreposto**.

O conhecimento do Entreposto permanece condicionado a uma cadeia causal plausível de informação — incluindo, conforme a fonte, Vale, Pátio da Passagem, Tomé, viajantes ou observação futura. Nenhum desses canais é promovido aqui a evento ocorrido ou knowledge automático.

## Provider/runtime
Nenhum provider de cidade, colônia, indústria, crafting ou engenharia é fixado por este dossiê.

Se MineColonies, Create ou outro provider vier a representar partes do assentamento, a integração deverá respeitar capabilities reais e manter separadas a authority mecânica do provider e a authority narrativa deste dossiê.

## Discovery
- não fixada;
- vínculo de NPC/facção não concede descoberta automática ao jogador;
- a existência canônica do assentamento não exige que o protagonista conheça sua localização no início.

## Invariantes
- é assentamento pequeno apesar do `location_type=city` do schema genérico;
- não possui tecnologia industrial avançada por declaração da fonte;
- competência técnica não gera propriedade automática sobre ruínas;
- não possui direito prévio sobre `LOC-0002`;
- worldgen, coordenadas, blocos, população de fundo e provider físico permanecem abertos;
- preservação, estabilização e desmontagem são decisões distintas;
- conhecimento de locais externos exige proveniência causal.

## Spoilers internos
A fonte explicita que Pátio das Oficinas não é a comunidade de origem de Tomé, embora compartilhe práticas e contatos profissionais com R3. Nenhum dossiê de Tomé é criado neste passo e nenhuma identidade adicional é inferida desse nome.

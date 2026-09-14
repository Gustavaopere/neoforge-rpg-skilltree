# LOC-0001 — Região dos Ecos

## Estado editorial
RASCUNHO ESTRUTURADO / LOCAL DE REFERÊNCIA PARA `QST-0001`.

## Tipo
Região narrativa de investigação; não representa um único bloco, estrutura ou chunk.

## Identidade pública
"Região dos Ecos" é um nome editorial de trabalho para o conjunto territorial onde se concentram parte dos relatos ligados a `QST-0001`. O nome não precisa existir como topônimo conhecido pelos habitantes e pode ser substituído por nome diegético posterior sem reciclar o ID.

## Função narrativa
- permitir que ocorrências independentes sejam comparadas espacialmente sem transformar a quest em waypoint único;
- suportar descoberta casual ou investigação deliberada;
- admitir vestígios que podem mudar, desaparecer ou ser encontrados antes da oferta formal;
- permitir múltiplas rotas de acesso e observação quando o worldgen real comportar isso;
- permanecer separada do núcleo urbano de `SET-0001` sem exigir distância fixa.

## Descoberta
- por `EVD-0001` quando relatos trazem localização suficiente;
- por `EVD-0002` quando a agregação institucional revela concentração relevante;
- por exploração independente;
- por `EVD-0003` encontrado antes de qualquer investigação formal;
- por comunicação legítima de outro ator que conheça a região.

Conhecer a região não equivale a conhecer `QST-0001`, `NPC-0001` ou a causa das ocorrências.

## Estado físico/worldgen
NÃO FIXADO. Este arquivo não define clima, vegetação, altitude, geologia, estruturas, mobs, recursos ou composição de blocos.

## Binding geográfico
- estado: `NÃO FIXADO`;
- dimensão: pendente de validação pelo Compêndio/runtime;
- bioma: pendente de validação pelo Compêndio/runtime;
- estrutura: nenhuma obrigatória neste estado;
- fonte do binding: `docs/compendium` + catálogo runtime quando snapshot relevante estiver disponível;
- auditoria auxiliar: `LOC-0001-binding-audit.md`;
- fatos verificados: existem providers/biomas catalogados no pack, mas nenhum foi selecionado para este local;
- propriedades ainda desconhecidas: posição, bioma, clima, relevo, composição, estruturas e integração final com worldgen.

## Requisitos físicos mínimos
Requisitos narrativos independentes de provider:
- área suficientemente ampla para que relatos/vestígios não apontem automaticamente a um único ponto;
- acesso possível sem teleporte narrativo obrigatório;
- pelo menos uma rota plausível de exploração não dependente de `NPC-0003`;
- world state capaz de mudar antes/depois da visita sem exigir reset da região;
- `EVD-0003` deve poder ser representado sem exigir estrutura registrável específica.

Esses requisitos devem ser revisados quando o binding real for escolhido; não autorizam inferir como o terreno será gerado.

## Habitantes/facções
Nenhuma população permanente é definida por este arquivo. A região pode receber viajantes, trabalhadores, moradores dispersos, patrulhas ou outros atores somente quando conteúdo específico os declarar.

`FAC-0001` pode receber relatos da região sem exercer vigilância total, posse integral ou jurisdição automática sobre ela.

## Eventos relacionados
Nenhum `EVT-####` específico é criado apenas para justificar a região. Eventos futuros devem registrar causa/ator/tempo conforme o Event Ledger.

## Evidências/segredos
- `EVD-0001` — relatos podem referenciar diferentes pontos dentro da região;
- `EVD-0002` — consolida ocorrências de forma aproximada;
- `EVD-0003` — vestígio material que pode existir em um ponto da região.

Nenhuma evidência transforma o local inteiro em propriedade, esconderijo ou território de `NPC-0001` por inferência.

## Transformações possíveis
A região pode:
- perder ou ganhar relevância investigativa;
- ter vestígios removidos/alterados por causas concretas;
- continuar existindo depois de `QST-0001`;
- adquirir novos locais filhos `LOC-####` quando houver pontos específicos com identidade persistente.

Transformação narrativa não exige trocar o ID geográfico se a identidade da região continuar reconhecível.

## Provider/worldgen
- provider físico: PENDENTE;
- authority mecânica: ficará com o provider real selecionado;
- adapters: nenhum assumido.

## Relações
- assentamentos: `SET-0001` como origem institucional possível, não necessariamente território;
- quests: `QST-0001`;
- NPCs: `NPC-0003` pode investigar/receber informações; `NPC-0001` não é automaticamente associado ao local;
- facções: `FAC-0001`;
- evidências: `EVD-0001`, `EVD-0002`, `EVD-0003`.

## Invariantes
- não há biome binding até fonte verificável;
- não há coordenada fixa neste estágio;
- local descoberto antes da quest continua válido;
- a região não fornece knowledge de culpado/causa por si só;
- ausência de estrutura específica não invalida a quest;
- worldgen não deve ser regenerado/resetado para restaurar uma versão esperada da investigação.

## Spoilers internos
Nenhuma identidade, causa ou resolução é definida por este arquivo.

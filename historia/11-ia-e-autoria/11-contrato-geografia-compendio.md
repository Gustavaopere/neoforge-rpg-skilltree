# Contrato de Geografia Narrativa ↔ Compêndio

## Estado
ATIVO COMO CONTRATO EDITORIAL / sem dependência nova de runtime.

## Objetivo
Permitir que `LOC-####`, `SET-####`, quests e arcos sejam escritos antes da escolha final de bioma/estrutura/dimensão sem inventar propriedades de worldgen nem divergir do Compêndio server-authoritative.

## Fontes de autoridade
1. fatos narrativos aceitos em `historia/`;
2. catálogo geográfico do Compêndio para identidades reais `BIOME`, `STRUCTURE` e `DIMENSION` presentes no runtime;
3. provider real de worldgen para mecânicas específicas;
4. nomes/traduções de bioma nunca são usados sozinhos para inferir temperatura, altitude, recursos, mobs, estruturas, frequência ou composição.

## Regra de binding tardio
Um local narrativo pode existir com geografia parcialmente aberta.

Antes do binding:
- declarar função narrativa, acessibilidade, relações, discovery e requisitos físicos mínimos;
- usar `provider/worldgen: PENDENTE` ou `binding: NÃO FIXADO`;
- não afirmar coordenadas, bioma, dimensão ou estrutura não verificadas.

Depois do binding:
- registrar o registry ID exato (`BIOME:namespace:id`, `STRUCTURE:namespace:id`, `DIMENSION:namespace:id`) quando aplicável;
- citar a fonte do Compêndio/runtime usada para escolher o ID;
- declarar quais propriedades vieram de fonte verificável e quais continuam decisões narrativas;
- revalidar quests, assets e ambientação que dependiam da localização.

## Critérios para escolher um bioma/local físico
O candidato precisa satisfazer simultaneamente:
1. ID existente no catálogo/runtime relevante;
2. compatibilidade com requisitos narrativos já escritos;
3. ausência de conflito com estrutura/dimensão/provider escolhido;
4. acesso/raridade adequados ao gameplay quando esses fatos forem verificáveis;
5. nenhuma propriedade deduzida apenas do nome traduzido.

## Candidatos
É permitido manter uma lista editorial de candidatos, mas ela não vira cânone.

Formato recomendado:
- registry ID;
- razão narrativa de interesse;
- fatos verificados;
- fatos ainda desconhecidos;
- risco/conflito;
- estado: `CANDIDATO`, `DESCARTADO` ou `VINCULADO`.

## Assentamentos
`SET-####` pode ser definido politicamente/socialmente antes da geografia final. MineColonies ou outro provider físico não ganha autoridade sobre governo, leis, knowledge ou história apenas por hospedar a representação do assentamento.

## Quests e locais móveis
Uma quest não deve codificar bioma específico quando sua semântica só exige uma região remota, rota comercial, floresta, ruína ou outro requisito abstrato. Fixar registry ID só quando a mecânica/ambientação realmente depender dele.

## Drift
Se modpack/worldgen mudar e o ID vinculado desaparecer:
- não apagar silenciosamente o fato narrativo;
- marcar binding como indisponível/orphaned;
- escolher migração explícita;
- preservar histórico/IDs editoriais do local;
- validar saves e consequências antes de trocar o binding.

## Aplicação atual
`SET-0001` Pedra Clara e a região de `QST-0001` permanecem sem binding de bioma. O corpus atual confirma múltiplos providers/IDs de worldgen, mas o nome dos biomas não fornece sozinho evidência suficiente para fixar clima, geologia ou adequação urbana.

## Regra
Não escolher worldgen por estética quando o Compêndio/provider ainda não prova os fatos necessários.

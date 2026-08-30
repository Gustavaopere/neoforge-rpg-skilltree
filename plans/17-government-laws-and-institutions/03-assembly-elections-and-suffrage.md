# 17.03 — Assembleia, eleições e sufrágio

## Eleitorado

`SuffragePolicy` calcula elegibilidade a partir de fatos, podendo combinar:

- cidadania/residência;
- idade/estado compatível quando provider possuir dado real;
- patrimônio/renda;
- propriedade;
- classe social derivada;
- cargo;
- vínculo religioso;
- qualificação tecnológica/arcana;
- exclusions legais explícitas.

Nunca inferir critérios inexistentes no provider.

## Voto censitário

É implementado por threshold real de patrimônio/renda/propriedade fornecido pelo Stage 16. Alterar riqueza pode alterar elegibilidade na próxima election snapshot; não há tag permanente “rico pode votar”.

## ElectionSnapshot

No início de uma eleição, congelar revision de eleitorado/candidatos para evitar mudança durante contagem. Registrar turnout, opções, resultado e provenance.

## Assembleia

Pode ter representantes eleitos, nomeados por cargo ou por estates conforme GovernmentForm. A assembleia produz intents de law/decree; servidor valida autoridade.

## NPC politics

Singleplayer-first: votos NPC usam preferências/fatores transparentes e determinísticos/seeded; não manipular resultado escondido para favorecer jogador.

## Testes

- universal/censitary;
- wealth threshold;
- citizen move/death;
- frozen electorate;
- tie rule data-driven;
- invalid vote replay;
- appointed seat vs elected seat.

## Acceptance

Sufrágio é consequência de policy e fatos econômicos/institucionais, com eleição reproduzível.
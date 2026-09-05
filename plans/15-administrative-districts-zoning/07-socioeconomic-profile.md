# 15.07 — Perfil socioeconômico distrital

## Objetivo

Produzir analytics derivados para governo/economia sem persistir uma classe social inventada por território.

## Métricas deriváveis

- população/residentes;
- empregos e desemprego;
- renda média/mediana quando ledger permitir;
- patrimônio agregado;
- aluguel/propriedade;
- atividade comercial;
- arrecadação/gasto público;
- dívida/inadimplência;
- acesso a calor/serviços;
- desigualdade por medidas configuráveis;
- incidentes de descontentamento.

## Fonte

Dados vêm dos Stages 16–19 e MineColonies através de snapshots/read APIs. Stage 15 não escreve wallets, classes ou leis.

## Agregação

Atualizar incrementalmente/event-driven e com jobs bounded. Métricas históricas podem usar séries temporais compactadas; não registrar cada tick/cidadão indefinidamente.

## Privacidade de estado

Singleplayer-first; ainda assim, cliente recebe somente dados necessários à UI e não grandes dumps internos a cada mudança.

## Testes

- distrito vazio;
- mudança de residência/emprego;
- transação atualiza agregado sem double count;
- save/reload de séries compactadas;
- métricas ausentes exibem “sem dados”, não zero falso.

## Acceptance

Mapa/governo consegue comparar distritos usando métricas derivadas consistentes e bounded.
# 16.02 — Salários, trabalho e emprego

## Objetivo

Ligar atividade produtiva a renda real sem pagar por tick nem permitir farm passivo infinito.

## EmploymentContract

Persistir referência entre trabalhador e empregador:

- citizen/economic actor;
- job/profession ID;
- building/employer ID;
- wage policy;
- jornada/periodicidade econômica;
- status ativo/suspenso;
- arrears acumulados;
- regime/law revision de contratação.

MineColonies continua autoridade de job assignment; Stage 16 observa o vínculo e cria/encerra contrato econômico idempotentemente.

## Pagamento

Salário é liquidado por períodos econômicos discretos ou por evento de folha, nunca por cada AI tick. O empregador pode ser tesouro, instituição pública ou empresa privada conforme Stage 17.

Se faltar dinheiro:

1. não criar moeda;
2. registrar `WageArrear`;
3. pagar parcial somente se policy permitir;
4. atualizar satisfação/descontentamento através de uma saída semântica;
5. permitir quitação futura rastreável.

## Produtividade

Produtividade influencia output/eficiência somente através de hooks legítimos do provider. Não duplicar item já produzido pelo MineColonies para “simular economia”. Quando não houver hook seguro, registrar apenas resultado econômico sem alterar inventory provider.

## Desemprego

Cidadão economicamente ativo sem contract/job entra em estado derivado de desemprego. Isso alimenta perfil distrital e welfare, não muda a profissão MineColonies por si só.

## Testes

- salário público/privado;
- mudança de job;
- citizen unload;
- empregador sem fundos;
- arrears + quitação;
- reload sem pagamento duplicado;
- regime muda employer ownership sem apagar dívida histórica.

## Acceptance

Trabalho gera renda em eventos bounded e toda folha possui payer real.
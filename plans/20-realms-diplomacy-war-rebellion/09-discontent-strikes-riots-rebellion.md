# 20.09 — Descontentamento, greves, distúrbios e rebelião

## Inputs

`DiscontentService` consome fatores comprovados:

- wage arrears/unemployment;
- poverty/debt/inequality;
- tax burden;
- housing/service shortage;
- cold/deaths/heat inequity;
- coercive labor status;
- war casualties/occupation;
- representation/suffrage;
- doctrine/government mismatch quando definido;
- successful welfare/prosperity como fatores redutores.

## Escada aprovada

```text
STABLE
DISCONTENT
PROTEST
STRIKE
SABOTAGE
RIOT/MUTINY
INSURRECTION
```

Transitions exigem thresholds + duração/histerese/cooldown. RNG pode modular eventos, nunca ser a única causa.

## Protesto

Pode reduzir legitimacy, gerar demands e eventos sem violência.

## Greve

Workers participantes suspendem/reduzem produção por hook MineColonies seguro; salary/work contracts registram estado. Não deletar job.

## Sabotagem/distúrbio

Só afeta máquinas/buildings por mecanismos explicitamente seguros e bounded; não grief random blocks.

## Insurreição

Pode criar rebel faction/realm, contest territory ou iniciar civil war record. Civis que participam como combatentes são então explicitamente reclassificados para Stage 02.06; população civil comum não recebe scaling ofensivo.

## Demand system

Groups podem exigir wage payment, tax change, representation, abolition/reform, heat allocation etc. Cumprir demanda altera fatores, não zera unrest por comando mágico.

## Testes

- arrears→protest;
- welfare reduces factors;
- strike lifecycle;
- no instant rebellion from one bad tick;
- save/reload thresholds;
- rebellion creates valid political actor;
- provider-safe production hook.

## Acceptance

Rebelião é consequência legível de sociedade persistente e pode ser evitada/resolvida por decisões reais.
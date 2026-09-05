# 18.03 — Prédios cívicos e de governo

## Capacidades

Suportar materialização de:

- treasury/finance office;
- tax office;
- assembly hall;
- court/justice building;
- palace/court seat ou government seat;
- administrative archive/office.

Não é necessário substituir o Town Hall MineColonies. Um prédio RPG pode complementar a colônia e registrar `institutionSeat` do Stage 17.

## Institution seats

Cada building capability declara quais offices/institutions pode sediar. Exemplo: assembly hall hospeda seats da assembleia; court hospeda magistrate/court service; treasury office hospeda treasurer/finance service.

## Consequência

Lei pode exigir prédio/nível mínimo para determinado serviço. Se o prédio for destruído, authority histórica permanece, mas serviço operacional pode suspender até novo seat ser designado.

## Tesouro

Building físico não contém o saldo autoritativo; chests podem representar procurement/material storage, enquanto `TreasuryAccount` é Stage 16.

## Testes

- seat assignment;
- destroyed building suspends capability;
- law requiring institution;
- upgrade capacity/seats;
- government transition reassigns office sem reset building.

## Acceptance

Instituições políticas têm presença física sem transformar chest/worker em autoridade do estado jurídico.
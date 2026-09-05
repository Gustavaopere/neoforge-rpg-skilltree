# 18 — Prédios Cívicos, Comerciais e Profissões MineColonies

## Objetivo

Materializar economia, governo, religião, saúde, pesquisa, tecnologia, magia e aquecimento em **prédios e empregos reais** integrados ao MineColonies, sem transferir para MineColonies a autoridade dos sistemas dos Stages 16–20.

## Separação de autoridade

- MineColonies: cidadão, worker assignment, Builder, requests/logistics e lifecycle de building quando API suportar;
- Stage 14: geometria/blueprints/style packs;
- Stage 16: dinheiro, shop transactions, salários, propriedade e orçamento;
- Stage 17: cargo político, lei, autoridade e regime;
- Stage 19: heat network;
- Stage 20: realm/diplomacy/war.

Um prédio pode existir fisicamente sem que seu subsistema esteja disponível; nesse caso a função correspondente fica diagnosticada/inativa, nunca simulada por efeito mágico silencioso.

## Princípios

1. Integração usa APIs/registries da versão MineColonies 1.21.1 efetivamente suportada; internals frágeis só entram com contrato/teste explícito.
2. Ausência de MineColonies não impede core-only startup.
3. Jobs próprios usam identidade persistente do cidadão, não entity UUID transitório.
4. Courier/Warehouse permanecem logística; compra e pagamento pertencem ao Stage 16.
5. Buildings têm `buildingId` nosso e bridge para MineColonies, permitindo save/recovery mesmo quando provider estiver ausente.
6. Níveis 1–5 usam Stage 14 quando a função exigir evolução física.
7. Todo texto próprio em pt-BR.
8. Nenhum prédio copia schematic/asset externo sem Stage 09.09.

## Famílias

- comércio/finanças;
- governo/justiça;
- religião/saúde;
- pesquisa/tecnologia/magia;
- habitação e variantes socioeconômicas;
- infraestrutura térmica.

## Ordem

1. `01-custom-building-framework.md`
2. `02-commercial-buildings.md`
3. `03-civic-government-buildings.md`
4. `04-religious-health-buildings.md`
5. `05-technology-and-arcane-buildings.md`
6. `06-housing-social-class-variants.md`
7. `07-workers-jobs-schedules.md`
8. `08-requests-inventory-and-economy-bridges.md`
9. `09-upgrades-stylepacks-tests.md`

## Definition of Done

Uma colônia consegue construir ao menos um prédio de cada família suportada, atribuir worker quando aplicável, operar requests/logistics, pagar salários/transacionar pelo Stage 16 e atualizar níveis sem perder inventories, anchors ou vínculos funcionais.
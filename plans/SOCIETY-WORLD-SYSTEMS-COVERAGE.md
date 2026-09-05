# Matriz de cobertura — discussão de sociedade e sistemas de mundo

Data de consolidação: **2026-08-30**.

Este documento existe para provar onde cada assunto discutido nesta conversa foi materializado e evitar que um tema seja esquecido ou planejado duas vezes.

## Já estava no GitHub — não duplicado

### Compêndio Natural / conteúdo modded automático

Já formalizado no Stage 10: conteúdo suportado entra por registry/provider genérico; adapters enriquecem casos especiais. Este lote não cria uma segunda especificação.

### Itemização e classificação universal de equipamentos

Já formalizado no Stage 11, especialmente `11.02 — Classificação universal de equipamentos`: overrides → adapters → tags/registries/capabilities/components → atributos/slot → fallback `GENERIC_EQUIPMENT`. Não foi duplicado.

## Adicionado como complemento de estágio existente

| Assunto discutido | Plano |
|---|---|
| vacas/galinhas/passivos sem level scaling | `02-progression-world-scaling/06-entity-scaling-eligibility-minecolonies.md` |
| hostis/bosses/guards/raiders com scaling completo | Stage 02.06 |
| civis MineColonies com proteção apenas defensiva | Stage 02.06 |
| cleanup de modifiers legacy em passivos | Stage 02.06 |
| auditoria de War ’n Taxes / War ’n Nobility / Warring Nations / inverno / territory tools / boiler references | `09-hardening-release/09-society-worldsystems-reference-audit.md` |

## Stage 14 — construções/schematics

Cobertos: VoxelModel único, paletas modded/Create, gerador paramétrico, preview fiel, `.schem`, exporter Structurize/MineColonies, style packs, upgrades 1–5, Create funcional, BOM, validação e CI. A exigência central “preview aprovado = mesmos blocos do schematic” está em 14.01/14.04/14.05.

## Stage 15 — distritos

Cobertos: polígonos côncavos, ferramenta de traçado, markers/beams/wireframe, point-in-polygon, índice espacial, `districtAt(BlockPos)`, zoning, policies locais, perfil socioeconômico, JourneyMap/Stage 13 e migration/performance.

Região natural do Stage 13 e distrito administrativo do Stage 15 são entidades diferentes.

## Stage 16 — economia/sociedade

Cobertos: moeda real, treasury, citizen wallets, salários, employment, wage arrears, preços, impostos, subsídios, decretos, compra real, logística, shops, goods modded automáticos, propriedade, aluguel, patrimônio, classe social, construção/manutenção, orçamento, research funding, empresas/mecenas, pobreza, dívida, welfare e desigualdade.

Regra explicitada: Courier/Warehouse transportam mercadoria; transporte interno não é compra.

## Stage 17 — governo/leis/regimes

Cobertos: separação GovernmentForm/EconomicRegime/LawSet, precedência lei geral → district policy → decreto, assembleia, eleições, sufrágio, voto censitário por riqueza real, capitalismo, economia comunal/comunista, teocracia, tecnocracia, magocracia, feudalismo, servidão, escravidão fictícia como status coercivo, corte, cargos, conselho, oposição, legitimidade e transição de regime.

## Stage 18 — MineColonies civil/comercial

Cobertos: framework de custom buildings, comércio/finanças, governo/justiça, religião/saúde, tecnologia/pesquisa/magia, habitação socioeconômica, jobs/workers/schedules, requests/inventory/economy e upgrades/style packs.

## Stage 19 — inverno e aquecimento

Cobertos: inverno extremo sazonal, state machine de crise, building thermal model, heat network por distrito, combustíveis modded automáticos, caldeira/Generator Core, Central Térmica níveis 1–5, substations/endpoints/prioridades, saúde/produtividade/morte por exposição, emergency laws/rationing e Create funcional.

Regras explicitadas: Cold Sweat continua autoridade da temperatura corporal do jogador; não há perma-inverno; Create não gera calor infinito só por RPM.

## Stage 20 — reinos e política externa/interna

Cobertos: Realm acima de Colony, títulos/condados/ducados/reinos, diplomacia, tratados, guerra/raids/sieges/occupation, vassalagem/tributo/conquista, reinos NPC singleplayer, settlements offscreen agregados, espionagem/intel parcial e progressão descontentamento → protesto → greve → sabotagem → motim/distúrbio → insurreição.

## Regras transversais aprovadas

- alvo primário: singleplayer;
- não projetar governança em função de PvP;
- servidor é autoridade;
- pt-BR first;
- integrations opcionais fail-soft/classloading-safe;
- estado persistido versionado e migrations explícitas;
- unknown IDs não são apagados silenciosamente;
- hot paths bounded/event-driven;
- nenhuma lista manual eterna quando registry/tag/adapter pode classificar;
- providers continuam autoridade de seus próprios recursos/AI/temperatura;
- construções e assets externos obedecem proveniência/licença;
- implementação de cada Stage deve terminar com testes, PR, CI verde, merge na `main` e confirmação pós-merge conforme o workflow do projeto.

## Ordem de dependência recomendada

```text
02.06 (scaling eligibility)
14 (blueprint pipeline)
15 (districts)
16 (economy)
17 (government)
18 (MineColonies buildings/jobs)
19 (winter/heating)
20 (realms/war/rebellion)
```

Há dependências cruzadas, mas cada Stage deve preservar boundaries e ser testável isoladamente/fail-soft.
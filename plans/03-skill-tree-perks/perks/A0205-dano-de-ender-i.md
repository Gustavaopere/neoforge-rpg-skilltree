# A0205 — Dano de Ender I

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-81cf-84ea-cc3a6d50413a; dependências adiantadas, availability, classifier e lifecycle corrigidos; re-fetch PASS.
- **Runtime observado:** não há direct_ender_outcome/classificador ENDER ligado ao pipeline do RPG. A0205 é **UNAVAILABLE_NODE/não comprável**.
- **Dependências adiantadas:** A0144 e A0148–A0155 estão antes do lote e não são presumidas concluídas.

## Contrato canônico

- Até 4 ranks: +3% / +6% / +9% / +12%.
- Atua somente sobre componente mágico ENDER direto, positivo e atribuído ao jogador.
- Pipeline: Potência Mágica universal → camada específica ENDER A0205 uma vez → especializações posteriores.
- Preserva action_id/outcome_id e não reaplica em DoT ou derived component já escalado.
- Parcel térmico eventualmente declarado pelo provider é separado; A0205 não o cria nem amplia.

## Gate e availability

A compra exige Gateway ARCANE, A0144 ≥2, pelo menos uma técnica com rank entre A0148–A0155 e um adapter versionado que publique direct_ender_outcome. Sem dependency closure ou classifier, o node fica indisponível.

Teleporte, mudança de dimensão, entidade/item do End, Void, cold, partículas, nome, gear e namespace não classificam ENDER.

## Providers

- Iron's 3.16.3: framework e SchoolType/action identity.
- Fire's Ender Expansion 2.4.1: provider candidato principal, ainda sem adapter comprovado no RPG.
- Somake Spells 1.0.8: apenas conteúdo explicitamente mapeado.
- Ars e outros: somente por adapter de ação concreta.
- Black Arcana/Enshrouded/Volcanoes: nenhuma capability atual classifica ENDER.
- Tecnologia, summons e fake players: N/A/excluídos.

## Mastery futura

A0205 não exige Ender Mastery, mas estabelece o primeiro outcome ENDER que A0206+ consomem. O adapter deve publicar também school/action ID suficiente para uma futura ender_mastery_lane_id exata; não criar ledger genérica.

## Pendências para Chat 2

- **P-A0205-01 BLOQUEANTE:** availability e dependency closure A0144/A0148–A0155.
- **P-A0205-02 BLOQUEANTE:** adapter direct_ender_outcome para versão exata do provider.
- **P-A0205-03:** binding da camada ENDER no magic outcome pipeline, uma vez por componente.
- **P-A0205-04:** school/action identity para mastery posterior, sem alias temático.
- **P-A0205-05:** testes direct/DoT/derived/summon/End/Void/cold/dedup.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | cadeia anterior é blocker, não bypass. |
| Integração global | PASS | camada ENDER depois de Potência universal. |
| Qualidade/identidade | PASS | dano dimensional direto, não todo dano do End. |
| Topologia | PASS | fundamento ARCANE/ENDER. |
| Especializações | PASS | pode compor Gate A; não satisfaz Gate B/C. |
| PT-BR | PASS | Dano de Ender permanece classificação explícita. |
| Notion | PASS após correção | dependency/availability regravados. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | Fire's/Somake somente por action id real. |

Os 18 critérios passam **no design** com classifier obrigatório e no-op purchase proibido.

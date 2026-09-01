# A0209 — Imbuimento de Ender

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-816c-8969-c9a4b1d635a1; dependencies, lanes, action producer, availability, Hook e lifecycle corrigidos; re-fetch PASS.
- **Runtime observado:** não há producer causal ENDER nem hook de componente derivado para este node. A0209 é **UNAVAILABLE_NODE/não comprável** e herda A0205.

## Contrato canônico

- Até 2 ranks.
- Ação ou deslocamento ENDER direto confirmado abre janela única por 120 ticks.
- Cada direct_melee_outcome elegível adiciona 5% / 10% da base canônica pré-mitigação, pré-crítico e pré-componentes.
- Um único derived_component:ENDER pertence ao mesmo action_id/outcome_id pai.
- Recast ENDER válido renova duração; não empilha magnitude.

## Lanes canônicas

O gate exige mastery ≥20 na lane realmente usada:

- epicfight:sword;
- epicfight:axe;
- epicfight:spear somente em contato;
- epicfight:dagger;
- epicfight:heavy;
- combat:mace;
- combat:scythe;
- combat:fist somente quando sua cadeia estiver válida.

IDs epic_sword/epic_axe etc. são gateways, não ledgers. Ranged, projétil, lança arremessada, mão vazia não classificada, sweep derivado, DoT, summon, fake player e automação são excluídos.

## Producer e componente derivado

Somente commit de ação/cast/deslocamento ENDER comprovado arma a janela; tentativa, cancelamento, teleporte genérico ou diferença de posição não. O component herda a única decisão crítica do pai e não cria DamageSource, segunda crítica, teleporte, Ruptura, Mastery, sustain ou proc.

## Providers

- Fire's 2.4.1/Somake 1.0.8 + Iron's 3.16.3: action/SchoolType id exato e adapter ENDER.
- Epic Fight 21.17.3.1: direct melee outcome e categorias reais.
- Weapons of Miracles: somente arma concreta mapeada.
- NeoForge teleport event isolado: insuficiente.
- Outros mods/hazards: N/A.

## Lifecycle

Limpar janela, reservations e dedup em morte, logout, dimensão, rank/dependency loss, respec e rules reload. Se uma ação ENDER causa deslocamento entre dimensões, ordering transacional deve preservar somente o estado recém-commitado correspondente.

## Pendências para Chat 2

- **P-A0209-01 BLOQUEANTE:** availability transitiva de A0205.
- **P-A0209-02 BLOQUEANTE:** producer causal de ação/deslocamento ENDER.
- **P-A0209-03:** direct melee outcome e append do component no pai.
- **P-A0209-04:** lane normalization e blockers combat:fist.
- **P-A0209-05:** lifecycle, dedup e testes de cancelamento/ranged/derived/teleport.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0205, producer ENDER e lane melee 20. |
| Integração global | PASS | component no mesmo outcome, sem segunda resolução. |
| Qualidade/identidade | PASS | imbuimento dimensional híbrido. |
| Topologia | PASS | ponte ARCANE↔MARTIAL/AGILITY. |
| Especializações | PASS | PP bridge sem dupla contagem. |
| PT-BR | PASS | Imbuimento de Ender e janela explícitos. |
| Notion | PASS após correção | lanes e availability regravadas. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | action ID real; teleport genérico excluído. |

Os 18 critérios passam **no design**; derived component permanece parte do outcome pai.

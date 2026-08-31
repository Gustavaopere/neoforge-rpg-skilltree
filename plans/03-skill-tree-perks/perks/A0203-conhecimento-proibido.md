# A0203 — Conhecimento Proibido

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-81aa-bcdc-c423d00abb2c; OCCULT, Mastery, Gate, Fallback, Hook e lifecycle corrigidos; re-fetch PASS.
- **Runtime observado:** não há registry de três categorias ELDRITCH, HealingResolver ou lane addon ELDRITCH válida. A0203 é **UNAVAILABLE_NODE/não comprável**.
- **Dependências adiantadas:** A0198 e A0199 permanecem fechadas; A0201/A0202 também estão indisponíveis neste baseline.

## Contrato canônico

- 1 rank.
- Três eldritch_category_id distintas em uma janela móvel de 200 ticks ativam RPG_FORBIDDEN_KNOWLEDGE por 160 ticks.
- Uma action_id/outcome_id credita no máximo uma categoria.
- Durante o estado: dano ELDRITCH direto ×1,12; duração-base de estado ELDRITCH allowlisted ×1,15; cura efetiva recebida ×0,85.
- Ao ativar, limpar o conjunto; o estado não se autoalimenta.
- O parcel de duração é opcional. Dano e penalidade de cura são inseparáveis.

## Gate semântico

Exige A0198 ≥3, uma eldritch_mastery_lane_id exata ≥30, pelo menos 3 Passive Points válidos em PP_REGION:OCCULT e uma rota profunda entre A0199=1, A0201=1 ou A0202≥2.

OCCULT ≥3 significa PP semânticos válidos da região OCCULT. Não significa ranks arbitrários, mana, proximidade topológica ou qualquer recurso de mod. “Eldritch Mastery” não soma várias escolas.

## Categorias e providers

As três categorias vêm de registry/adapters versionados. Não inferir “direct damage”, ruína, loucura, fratura, curse ou dreamless por nome, partícula, VFX, namespace ou origem do mod.

- Iron's/Discerning/Deeper Darker: somente categorias e SchoolType IDs comprovados.
- Black Arcana: Backlash é hazard terminal; Arcane/Corruption/Strain não viram categorias ELDRITCH.
- Enshrouded: Madness/Shroud não viram Eldritch.
- Goety/Malum/Eidolon: somente futuros adapters explícitos.
- Tecnologia/summons/fake players: excluídos.

## State e lifecycle

Ledger de categorias por jogador, bounded, com timestamps e dedup por action/outcome. Limpar ledger e estado em morte, logout, troca de dimensão, rank/dependency loss, respec e rules reload. A duração ×1,15 atua na base da criação/renovação, nunca no tempo restante por polling.

## Pendências para Chat 2

- **P-A0203-01 BLOQUEANTE:** availability transitiva A0198/A0199/A0201/A0202.
- **P-A0203-02 BLOQUEANTE:** lane ELDRITCH exata e suporte de addon no catálogo.
- **P-A0203-03 BLOQUEANTE:** registry com no mínimo três categorias reais.
- **P-A0203-04 BLOQUEANTE:** HealingResolver para penalidade inseparável.
- **P-A0203-05:** ledger bounded, dedup, state-duration adapter e lifecycle.
- **P-A0203-06:** testes de repetição, uma ação/uma categoria, anti-recursão e reload.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | todos os gates adiantados permanecem fechados. |
| Integração global | PASS | ledger, damage layer, states e healing em boundaries canônicos. |
| Qualidade/identidade | PASS | domínio por variedade causal, não spam. |
| Topologia | PASS | keystone ARCANE↔OCCULT, não terminal. |
| Especializações | PASS | PP_REGION e school lane exatos. |
| PT-BR | PASS | Conhecimento Proibido preserva identidade e tradeoff. |
| Notion | PASS após correção | OCCULT e availability regravados. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | três categorias reais; nenhum tema presumido. |

Os 18 critérios passam **no design** com registry e tradeoff all-or-nothing.

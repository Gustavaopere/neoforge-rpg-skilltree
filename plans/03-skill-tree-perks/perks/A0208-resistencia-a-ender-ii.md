# A0208 — Resistência a Ender II

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-8157-bdf6-d1730b8dd6a7; receipt causal, transitive availability, transação e lifecycle corrigidos; re-fetch PASS.
- **Runtime observado:** faltam tanto o receipt de deslocamento próprio quanto o classifier hostil ENDER. A0208 é **UNAVAILABLE_NODE/não comprável**.

## Contrato canônico

- 1 rank; exige A0207 ≥3.
- Deslocamento dimensional próprio confirmado arma RPG_ENDER_VEIL por 100 ticks.
- O próximo hostile_direct_damage_outcome com componente ENDER positivo recebe ×0,75 somente nesse componente e consome o Véu.
- Novo deslocamento próprio válido renova a expiração, sem empilhar magnitude.

## Causalidade e availability

O node exige dois producers independentes:

1. receipt server-side que prove a ação do próprio jogador causadora do deslocamento;
2. classifier hostile_direct_ender_component.

EntityTeleportEvent isolado, posição, dimensão atual, portal, comando, respawn e teleporte forçado não provam 1. Sem qualquer producer ou sem A0207 disponível, compra é proibida.

## Transação e lifecycle

Véu nasce após commit do deslocamento. No outcome consumidor, reservar mitigação+consumo e commitar somente se o evento não for cancelado e permanecer elegível; rollback preserva o Véu. Deduplicar por outcome_id.

Limpar Véu e reservas em morte, logout, dimensão, rank/dependency loss, respec e rules reload. A troca de dimensão causada pela própria ação já é representada pelo receipt; o cleanup não pode apagar o Véu recém-armado no mesmo transaction boundary.

## Providers

- RPG Skill Tree: displacement receipt service, state e resolver.
- Fire's/Somake: ação de origem e/ou hostile ENDER component somente por adapter exato.
- NeoForge: eventos auxiliares, não prova causal isolada.
- AGILITY: semântica topológica, não owner automático de teleporte.

## Pendências para Chat 2

- **P-A0208-01 BLOQUEANTE:** availability transitiva A0207→A0208.
- **P-A0208-02 BLOQUEANTE:** causal self-displacement receipt.
- **P-A0208-03 BLOQUEANTE:** hostile ENDER classifier.
- **P-A0208-04:** reservation→commit/rollback e ordering com dimension transition.
- **P-A0208-05:** testes forced/command/respawn, cancelamento, renewal e dedup.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0207 + dois producers reais. |
| Integração global | PASS | Véu é mitigador transitório distinto. |
| Qualidade/identidade | PASS | defesa premiando mobilidade dimensional própria. |
| Topologia | PASS | notable VITALITY↔AGILITY. |
| Especializações | PASS | PP bridge sem dupla contagem. |
| PT-BR | PASS | Véu e consumo definidos claramente. |
| Notion | PASS após correção | transaction/lifecycle regravados. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | evento genérico explicitamente insuficiente. |

Os 18 critérios passam **no design** com causalidade de deslocamento obrigatória.

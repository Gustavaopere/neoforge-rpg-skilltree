# A0075 — Ritmo Sustentado

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-8114-a9eb-cb1a5d82f617`; Gate/Hook/Fallback/Provider/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** core matemático existe, mas o node deve permanecer **indisponível/não comprável**.

## Contrato canônico

- A0061 Força Aplicada ≥3 + A0064 Ritmo de Combate ≥2 + MARTIAL.
- 1 rank, custo 2.
- Três action-families marciais distintas em até 8 s ativam janela de 6 s.
- Durante a janela: +10% recuperação/regeneração efetiva de Stamina, +15% contribuição térmica metabólica por atividade elegível e +10% exhaustion.
- Cooldown de 12 s após o fim.
- O contrato é **all-or-nothing**: nenhum benefício existe sem os três bindings obrigatórios.

## Provider / authority / boundary

- Epic Fight 21.17.3.1: `STAMINA_REGEN` existe como grandeza provider-native.
- Minecraft/NeoForge: exhaustion.
- Cold Sweat 2.4.2: só pode participar se houver boundary causal seguro para a parcela específica de atividade/calor metabólico.
- Thirst Was Reclaimed permanece eixo independente; sede nunca é inferida de exhaustion.

## Evidência runtime

A policy já exige flags `staminaRegenAvailable`, `thermalActivityAvailable`, `exhaustionAvailable`. O próprio adapter Epic Fight documenta que A0075 não é registrado porque a parcela térmica Cold Sweat obrigatória não está provada. Matemática pura não torna o node utilizável.

## Fallback e fail-closed

Enquanto faltar qualquer binding obrigatório, A0075 fica indisponível/não comprável; não permitir silent no-op purchase nem benefício parcial. Não substituir por desconto de custo, movement/attack speed, temperatura aproximada, dano ou sede.

## Pendências para Chat 2

- **P-A0075-01 BLOQUEANTE:** unavailable-node invariant no purchase/gate.
- **P-A0075-02:** provar/implementar boundary Cold Sweat 2.4.2 para contribuição térmica causal de atividade; sem isso não ativar.
- **P-A0075-03:** integrar `STAMINA_REGEN` + exhaustion + thermal como transação all-or-nothing e testar action-family/root dedup.
- **P-A0075-04:** cleanup de qualifiers/janela/cooldown em lifecycle/rank loss/respec/rules reload.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | três bindings operacionais obrigatórios. |
| Integração global | PASS | Stamina/temperatura/exhaustion/sede permanecem eixos distintos. |
| Qualidade/identidade | PASS | rotação ofensiva com custo metabólico real. |
| Topologia | PASS | Camada 3, `MARTIAL/SUSTAIN`. |
| Especializações | PASS | região de sustentação explícita. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Epic Fight/Cold Sweat/vanilla delimitados; ausência gera indisponibilidade. |

Os 18 critérios passam **no design** porque a indisponibilidade é parte explícita do contrato.
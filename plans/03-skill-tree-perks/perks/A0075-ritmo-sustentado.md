# A0075 — Ritmo Sustentado

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-8114-a9eb-cb1a5d82f617`; Gate/Hook/Fallback/Provider/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

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

A policy modela o contrato, mas o adapter não registra qualificadores enquanto os três bindings obrigatórios não estiverem provados. A integração Volcanoes → Cold Sweat atualmente existente projeta calor ambiental `WORLD` de fontes vulcânicas e **não** é receipt metabólico da atividade do jogador; não resolve a pendência A0075.

## Implementação Chat 2 — 2026-09-01

- `CombatPerkAvailabilityRuntime` marca A0075 indisponível;
- purchase server-authoritative recusa o node e `effectiveRanks` mascara qualquer rank persistido para zero;
- o bridge Epic Fight continua deliberadamente sem `recordMartialAction(...)`, impedindo ativação parcial;
- nenhum desconto antigo de custo, attack/movement speed, temperatura aproximada, dano ou sede foi usado como substituto;
- mudança de rank efetivo/availability limpa qualificadores/estado transitório;
- a matemática pura permanece disponível somente como contrato/core, sem ser promovida a gameplay.

## Pendências para Chat 3

- validar compra recusada e ausência total de benefício parcial;
- validar que Volcanoes/Cold Sweat WORLD heat não habilita A0075;
- validar que rank persistido preexistente é efetivamente mascarado enquanto indisponível;
- quando/SE existir futuramente boundary metabólico causal seguro, validar os três bindings como transação all-or-nothing e action-family/root dedup; se isso exigir mudança de contrato/provider, devolver ao Chat 1.

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

Chat 2 conclui corretamente A0075 em fail-closed; não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.

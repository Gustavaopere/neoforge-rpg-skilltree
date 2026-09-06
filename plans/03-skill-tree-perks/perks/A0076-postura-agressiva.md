# A0076 — Postura Agressiva

## Estado

- **Design:** APROVADO após fechamento do boundary de ativação em 2026-08-31.
- **Notion:** `3c569db9-f0db-81a3-b3b3-f3901dbb0937`; Gate/Hook/Fallback/Provider/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** state/cooldown puro já existe, porém não há caller/input/payload; até esse binding ser implementado, node indisponível/não comprável.

## Contrato canônico

- MARTIAL + A0061 Força Aplicada ≥3 + A0064 Ritmo de Combate ≥1.
- 1 rank, custo 1.
- Enquanto `AGGRESSIVE`: +8% dano físico elegível e −5% resistência física.
- Ocupa exclusivamente `MARTIAL_STANCE`; cooldown de troca 1,5 s.

## Boundary de ativação fechado

RPG Skill Tree é owner da stance e do comando. Implementar controle remapeável `Alternar Postura Marcial` que envia somente intenção por payload serverbound. O servidor valida ranks, disponibilidade e cooldown, então efetiva a transição atômica:

- se só A0076 estiver disponível: `NONE ↔ AGGRESSIVE`;
- quando A0076 e A0077 estiverem legitimamente disponíveis: `NONE → AGGRESSIVE → CAUTIOUS → NONE`.

Cliente nunca é authority. A stance nativa de Epic Fight não substitui o slot RPG e só pode coexistir por adapter explícito sem duplicação.

## Evidência runtime

`A0061A0080CombatState.switchStance(...)` já possui slot e cooldown. `ClientKeyMappings` e `ModNetworking` demonstram infraestrutura de keybind/payload no mod, mas não existe binding específico da postura.

## Fallback e lifecycle

Sem comando/payload server-authoritative, A0076 fica indisponível/não comprável. Limpar stance em morte, respawn, logout/login inconsistente, dimensão, rank loss/respec/rules reload e perda do binding. Não usar potion, item, animação ou flag client-side como substituto.

## Pendências para Chat 2

- **P-A0076-01 BLOQUEANTE:** implementar control/payload serverbound e availability gate antes de permitir compra.
- **P-A0076-02:** aplicar/remover efeitos atômicos e validar cooldown/exclusividade/lifecycle.
- **P-A0076-03:** testes multiplayer/client-server para spoofed payload, rank ausente, spam e troca de stance.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | ranks + binding de ativação. |
| Integração global | PASS | resistência física não é Armor/magic/Shroud. |
| Qualidade/identidade | PASS | stance de risco ofensivo. |
| Topologia | PASS | Camada 3, `MARTIAL/POSTURE`. |
| Especializações | PASS | região de posturas explícita. |
| PT-BR | PASS | controle/nome player-facing em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | RPG authority; Epic Fight apenas coexistência explícita. |

Os 18 critérios passam **no design**; implementação depende do binding de ativação especificado.
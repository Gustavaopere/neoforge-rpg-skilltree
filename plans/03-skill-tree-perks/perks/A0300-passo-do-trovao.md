# A0300 — Passo do Trovão

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por dependency closure.
- **Authority de unlock:** `TreeUnlockResolver` + `TreeUnlockDefinition` + `TreeUnlockCatalog`; não criar `SPECIALIST_GATE_RESOLVER_V1` paralelo.
- **Fonte canônica:** Notion `A0300` — https://app.notion.com/3c569db9f0db814bab25d5600acae780
- **Persistência Notion:** re-fetch 2026-09-02 confirmado.

## Contrato aprovado

Enquanto `RPG_STORM_BODY` de A0299 estiver ativo, concede +8% movement. Mantém `storm_discharge_snapshot` da ação LIGHTNING direta própria mais recente elegível; a ativação ofensiva de A0299 pode semear o snapshot e ações LIGHTNING diretas posteriores o substituem. Com snapshot positivo, movimento voluntário elegível pode emitir no máximo uma descarga derivada a cada 15 ticks contra o inimigo válido mais próximo em 3 blocos, com LOS.

- alvo comum: `0.20 × snapshot`;
- BOSS/PvP: `0.10 × snapshot`;
- alvo aplica sua própria mitigação LIGHTNING;
- CHARGED só é adicionado após commit positivo da descarga e pelo ledger canônico de A0284;
- a descarga é DERIVED: não cria segunda rolagem de crítico, proc ou Mastery.

## Gate e closure

Compra exige `SPECIALIST_UNLOCK:LIGHTNING`, A0299, A0297, A0294 ou A0295, ≥18 PP internos válidos e Lightning Mastery ≥180. No snapshot atual, Gate C A0176 permanece `UNAVAILABLE_NODE`; A0299 também depende desse unlock. Portanto A0300 é inalcançável e a compra deve falhar **antes** do gasto.

Allocation legado indisponível vale 0 PP para gates/thresholds e permanece reembolsável/migrável.

## Providers e boundaries

- RPG Skill Tree: owner do node, unlock, PP/Mastery, estado e composição.
- `AttributeNodeEffectRuntime`: primitive existente de modifier transitório idempotente por ID estável; pode sustentar o +8% quando A0299 estiver operacional.
- Epic Fight/ParCool/Epic ParCool: somente via adapter versionado que produza receipt de dodge/locomoção; animação não prova ação.
- Sable/Aeronautics: apenas transform/contexto de sublevel; não classificam movimento voluntário.
- Iron's/Ars: podem originar LIGHTNING somente por classifier/action identity explícitos.

Ainda faltam contratos seguros para locomoção voluntária/dodge, target query, derived outcome, boss/PvP e CHARGED correlation. Nenhum deles pode ser inferido por distância percorrida, velocidade, VFX, FE ou tempestade.

## Causalidade, deduplicação e anti-abuso

Uma emissão por `root_action + movement_receipt + interval_window`; push, knockback, veículo, contraption e deslocamento imposto não ativam. A descarga herda a ação causal e não pode recircular para gerar nova descarga. Cooldown de 15t conta apenas emissão commitada conforme contrato do compositor.

## Fallback

Enquanto A0176/A0299 estiverem fechados: node inteiro indisponível. Quando a closure abrir, a parcela +8% pode existir se A0299 estiver ativo; se os receipts da descarga continuarem ausentes, omitir somente descarga/CHARGED, sem substituir por teleporte ou dano genérico.

## Testes obrigatórios para Chat 3

1. compra fail-before-spend com A0176/Specialist Lightning indisponível;
2. allocation legado indisponível = 0 PP em gates;
3. modifier +8% idempotente e cleanup em expiração, respec, logout e dimensão;
4. push/knockback/veículo/contraption não disparam descarga;
5. máximo 1 descarga/15t e 1 alvo com LOS;
6. 0.20 normal e 0.10 BOSS/PvP;
7. derived outcome não gera crítico/proc/Mastery em cascata;
8. CHARGED somente após commit positivo e sem duplicação;
9. provider absent/version mismatch falha fechado;
10. multiplayer e dedicated server.

## Handoff Chat 2

Não redesenhar. Implementar apenas o fail-closed/availability que já caiba na infraestrutura canônica. Não fabricar os receipts ausentes e não tornar A0300 comprável como no-op. Qualquer necessidade de alterar identidade, coeficientes, authority ou dependências retorna ao Chat 1.
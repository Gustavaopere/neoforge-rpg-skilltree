# A0313 — Aura de Pólen

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0313` — https://app.notion.com/3c569db9f0db81bdb54ad84556a4ebd2
- **Persistência:** fetch 2026-09-05.

## Contrato aprovado

Um heal **direto, próprio e classificado `NATURE_HEALING`** em outro ally player ou owned `NATURAL_COMPANION`, com cura efetiva ≥ `max(2 HP, 5% da vida máxima do alvo)`, pode ativar a aura quando fora do cooldown.

- rank 1: 100t, raio 4;
- rank 2: 140t, raio 5,5;
- pulse a cada 20t;
- cada aliado elegível no mesmo espaço autoritativo/sublevel recebe no máximo um heal derived por pulse;
- heal por pulse = 0,50% / 0,625% da **vida máxima atual do owner snapshotada para o pulse**;
- cooldown: 400t.

O heal da aura entra no `HEALING_OUTCOME` como derived NATURE. Modificadores target-side aplicam uma vez; o outcome derived não satisfaz novamente triggers que exigem heal direto.

## Gate e closure

Compra exige Specialist Natureza/A0183, A0304 ≥2 e A0306 ≥1. A closure mantém o node indisponível e compra fail-before-spend.

## Providers e authority

- RPG Skill Tree: trigger, aura instance, pulse identity, spatial selection e derived-heal classification.
- Healing provider: continua authority do heal originador e de sua quantidade efetiva. `LivingHealEvent` fornece amount, mas source/category precisa de receipt canônico.
- Companion providers só entram quando `NATURAL_COMPANION` + owner único forem comprovados.
- Sable/Aeronautics: apenas transformação/espaço. Parent-level proximity não substitui same-space/sublevel authority.

## Deduplicação

Identidade de entrega: `aura_instance + pulse_index + target`. Um alvo recebe no máximo uma entrega por pulse. O heal derived não gera nova aura, não concede Mastery como ação direta e não é reclassificado por outro adapter.

## Fallback

Sem `NATURE_HEALING` receipt, companion owner ou spatial context seguro, omitir o caso. Não aproximar por qualquer heal, tame status ou distância no parent level.

## Testes obrigatórios para Chat 3

1. fail-before-spend pelas dependencies;
2. trigger só por direct own NATURE_HEALING efetivo ≥ limiar;
3. outro player/owned natural companion válidos; self/unowned/ambíguo inválidos;
4. duração/raio 100t-4 e 140t-5,5;
5. pulso 20t e máximo um heal por target/pulse;
6. coeficiente 0,50%/0,625% do snapshot de max health do owner;
7. same-space/sublevel correto; parent-level false positive proibido;
8. derived heal não retriggera direct-heal procs/Mastery;
9. cooldown 400t e lifecycle cleanup;
10. provider absent/version mismatch, multiplayer e dedicated server.

## Handoff Chat 2

Não transformar qualquer cura em Nature e não fabricar ownership de companion.
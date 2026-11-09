# A0317 — Bosque Ambulante

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0317` — https://app.notion.com/3c569db9f0db81eaa66bf1da7d19cf46
- **Persistência:** fetch 2026-09-05.

## Contrato aprovado

Preparação exige `NATURAL_TERRITORY_V1=true` de forma contínua por 160t **e** pelo menos 3 ações NATURE diretas, distintas e elegíveis dentro da mesma continuidade. Sair do território, perder authority ou invalidar a classificação reseta a preparação.

Ao ativar:

- Nature Mastery 120–149: aura 200t, raio 5;
- Nature Mastery ≥150: aura 240t, raio 6;
- cooldown 900t;
- aliados elegíveis no mesmo espaço autoritativo recebem mitigação distinta ×0,92;
- a cada 40t recebem heal derived de 0,75% da vida máxima atual do owner snapshotada para o pulse;
- hostis elegíveis podem receber `GROVE_ROOT_ASSIST` a cada 40t, no máximo 1 crédito por pulse e 2 por `aura + target`; assist nunca é ação direta e sozinho não satisfaz A0314.

## Gate e closure

Compra exige Specialist Natureza/A0183, A0313 ≥1, A0314 ≥1, (A0315 **ou** A0316), Nature Mastery ≥120 e ≥18 PP válidos internos ao Specialist Natureza. Toda a cadeia relevante permanece indisponível; purchase fail-before-spend.

## Authority e boundaries

- RPG Skill Tree: preparation ledger, aura, mitigation, heal, assist e cooldown.
- `NATURAL_TERRITORY_V1` precisa de authority explícita/data-driven. Biome visual, bloco sob os pés, chuva, floresta no parent level ou proximity a planta não provam território.
- Sable/Aeronautics fornecem transformação espacial, não semântica Nature; same-space deve ser resolvido antes da seleção.
- A0314 é único owner do threshold/DR de root; A0317 somente produz assist bounded.

## Deduplicação e anti-abuso

Ações de preparação são distinct root actions. Pulse identity é `aura_instance + pulse + target`. Mitigação aplica uma vez por outcome. Heal derived não gera nova ação direta/Mastery. Root assist tem caps rígidos e não pode ser duplicado por múltiplos adapters.

## Fallback

Sem territory classifier/spatial context seguros, preparação não avança. Não aproximar por biome/foliage, nem transformar ausência em aura parcial gratuita.

## Testes obrigatórios para Chat 3

1. fail-before-spend e gate ≥18 PP internos/Nature Mastery120;
2. 160t contínuos + 3 direct distinct NATURE actions;
3. saída/loss de authority reseta preparação;
4. duração/raio 200t-5 e 240t-6 por mastery;
5. mitigação ×0,92 uma vez por outcome;
6. heal 0,75% a cada40t por target e same-space correto;
7. `GROVE_ROOT_ASSIST` max1/pulse e max2/aura+target;
8. assist sozinho nunca satisfaz A0314;
9. territory/spatial classifier ausente falha fechado; sem heurística por biome;
10. cooldown900t, lifecycle, multiplayer e dedicated server.

## Handoff Chat 2

Não implementar territory por heurística nem um segundo root resolver.
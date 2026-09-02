# A0108 — Pele de Pedra

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO.  
**Runtime atual:** `UNAVAILABLE_NODE` porque A0100 Anti-Crítico está indisponível.  
**Notion:** https://app.notion.com/p/3c569db9f0db81be9186f9195a54b0d6

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY.
- Ramo: Fortaleza Pesada.
- Camada: 5; função: Keystone.
- Ranks: 1; custo 2 PP.
- Pré-requisitos: A0092 Resistência Física ≥3 + A0100 Anti-Crítico ≥2 + A0090 Têmpera ≥2 + Gateway VITALITY.

## Contrato congelado

Quando algum dia estiver adquirível/ativo, A0108 concede simultaneamente:

- **+15% de redução própria de dano físico elegível**, uma única contribuição no `DamageMitigationResolver`;
- **−8% de velocidade de movimento**, por modifier server-side real de `minecraft:generic.movement_speed`.

Benefício e penalidade são **inseparáveis** e devem ser aplicados/removidos/reconciliados como uma única transação lógica. Não pode existir estado parcial.

O +15% é teto próprio da A0108; não existe cap defensivo global oculto. A perk não altera Armor/Toughness, não substitui A0092 e não reduz dano verdadeiro/não mitigável nem custos de Stamina.

## Availability transitiva

A0100 está formalmente `UNAVAILABLE_NODE` por falta de decomposição incoming critical segura. Portanto A0108 é atualmente **indisponível/não comprável e sem gasto de PP**, independentemente de os hooks genéricos de mitigação/movement speed existirem.

A availability do predecessor não pode ser bypassada por implementar apenas o efeito de A0108.

## Provider, hook e authority

- NeoForge `21.1.248` + RPG Skill Tree.
- Redução física: classifier físico canônico + `DamageMitigationResolver` em `LivingDamageEvent.Pre`.
- Penalidade: `Attributes.MOVEMENT_SPEED`/`minecraft:generic.movement_speed`, ID de modifier estável.
- Epic Fight só contribui pela classificação causal das fontes físicas.
- Protection Pixel `2.2.1` é equipamento próprio; não fornece weight/encumbrance e não substitui o tradeoff.

## Causalidade, deduplicação e lifecycle

- uma contribuição A0108 por root;
- um único modifier de movement speed por ator;
- apply/remove deve ser idempotente em login/reload/rank reconciliation;
- rank loss/respec/rules reload removem benefício e penalidade juntos;
- death/logout/dimension não podem produzir duplicate modifier ou benefício sem penalidade.

## Projetos próprios / cobertura provider → árvore

- RPG Skill Tree: ProgressionService governa A0100 e a availability transitiva; attribute runtime governa penalty.
- Volcanoes/Enshrouded/Black Arcana: não fornecem Anti-Crítico, weight state ou substituto para A0100; `NÃO DEVE SER INTEGRADO`.
- Black Arcana Arcane Resistance não é resistência física.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS, blocker transitivo explícito.
2. Integração global — PASS em design; generic hooks existem.
3. Qualidade/identidade — PASS, keystone de defesa física com tradeoff real.
4. Topologia — PASS, Fortaleza Pesada.
5. Especializações — PASS/N/A.
6. PT-BR — PASS.
7. Notion — PASS, fetch fresco.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS.

Authority, dedup, atomicidade, lifecycle, fallback e fail-closed transitivo estão congelados.

## Pendências para Chat 2

- `P-A0108-01`: implementar availability transitiva de A0100; purchase deve falhar antes do gasto enquanto o predecessor estiver indisponível.
- `P-A0108-02`: se houver valor em preparar o consumer latente, benefício + penalidade devem ser atômicos/idempotentes e nunca tornar o node comprável antes de A0100.
- `P-A0108-03`: testes de modifier uniqueness, zero partial state, one-reducer/root e reconciliação rank/respec/reload.

## Testes exigidos ao Chat 3

Estado atual: A0100 unavailable → A0108 unavailable sem gasto. Se consumer latente existir: +15% físico uma vez/root, −8% movement real, benefício/penalidade atômicos, reload/respec/death/logout/dimension sem duplicação, unknown/bypass exclusions, multiplayer, GameTests, build, JAR e dedicated-server smoke.

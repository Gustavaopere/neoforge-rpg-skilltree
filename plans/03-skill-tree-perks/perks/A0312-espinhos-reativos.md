# A0312 — Espinhos Reativos

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0312` — https://app.notion.com/3c569db9f0db81b3a271fac75c9cf51a
- **Persistência:** fetch 2026-09-05.

## Contrato aprovado

Depois de o defensor sobreviver a um **ataque corpo a corpo direto e hostil elegível**, A0312 adiciona ao compositor reativo de A0309 uma contribuição de:

- rank 1: 4% do dano efetivamente recebido;
- rank 2: 6% do dano efetivamente recebido.

A família A0309+A0312 respeita teto de **10% da vida máxima atual do defensor** antes de coeficientes especiais. Contra BOSS ou PvP, a parcela reativa é multiplicada por ×0,50. Existe no máximo um derived reactive damage por ataque original.

Após o commit do ataque elegível, há uma única rolagem server-side de 20% / 35% para uma tentativa provider-native de POISON por 60t. A claim inicia cooldown de 40t para `defender + attacker` somente no ponto previsto pelo contrato; o proc de Poison não cria novo reactive outcome.

## Gate e closure

Compra exige Specialist Natureza/A0183 e (A0309 ≥2 **ou** A0303 ≥2). Essas rotas permanecem transitivamente indisponíveis; compra falha antes do gasto.

## Authority e boundaries

- RPG Skill Tree: compositor reativo, cap, RNG/claim e dedup.
- Damage pipeline canônico: precisa provar direct hostile melee, dano real >0 e sobrevivência. `LivingDamageEvent.Post` é primitive útil, mas não classifica sozinho melee/hostilidade/root action.
- Provider POISON: authority do efeito real; A0312 só solicita uma aplicação elegível.
- Boss/PvP exige classifier explícito; não inferir boss por HP/nome.

## Anti-loop

Projectile, DoT, ambiente, self, ally damage, thorns/retaliation, derived outcome, dano zero e hit fatal são inelegíveis. Reactive damage herda root/parent identity e não pode ativar A0309/A0312 novamente, crítico, proc ou Mastery como nova ação direta.

## Fallback

Sem direct-melee receipt, boss/PvP classifier ou derived-outcome seam seguros, a parcela correspondente permanece inativa/fail-closed. Nunca usar `hurt()` paralelo sem provenance/dedup.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. somente direct hostile melee + sobrevivência ativa;
3. 4%/6% do actual damage, uma vez;
4. cap familiar de 10% max-health e ×0,50 BOSS/PvP;
5. exclusões projectile/DoT/environment/self/ally/derived/zero/fatal;
6. RNG exatamente uma vez por ataque elegível: 20/35%;
7. Poison 60t via provider e cooldown 40t sem duplicação;
8. reactive outcome não recircula para A0309/A0312/crítico/proc/Mastery;
9. classifier/provider ausente falha fechado;
10. multiplayer/dedicated server.

## Handoff Chat 2

Reusar o compositor de A0309 quando existir; não criar segundo pipeline de retaliation.
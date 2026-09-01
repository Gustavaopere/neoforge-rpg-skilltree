# A0129 — Economia Metabólica: Usar Arco/Besta

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED.  
**Runtime atual:** `UNAVAILABLE_NODE`; `METABOLIC_RANGED` corporal causal está ausente.  
**Notion:** https://app.notion.com/p/3c569db9f0db81f6a724db6dd548b55d

## Identidade e posição

- SURVIVAL / Principal — SURVIVAL; Esforço Profissional — Combate à Distância; função Ponte.
- 4 ranks; 1 PP/rank.
- Gate: Gateway SURVIVAL + acesso AGILITY/RANGED + disparo legítimo + custo corporal METABOLIC positivo atribuível ao disparo.

## Contrato congelado

Se futuramente existir custo corporal real causado pelo uso legítimo de arco/besta, A0129 reduz esse custo em **3% por rank**, até **12%**, sob teto METABOLIC compartilhado de **30% por evento**.

No runtime auditado, Minecraft/Epic Fight conseguem classificar o disparo, mas isso **não prova** um débito FoodData/hunger/exhaustion causado pelo shot. Logo não há efeito habilitável hoje.

## Proibições de substituição

É proibido fabricar exhaustion para justificar a perk ou trocar sua identidade por redução de:

- Stamina;
- munição;
- draw/reload time;
- Focus/Cadence;
- mana;
- durabilidade;
- qualquer resource provider-native não corporal.

## Pipeline futuro

`ranged launch/root action_id -> classifier BOW/CROSSBOW -> provider corporal emite quote/receipt METABOLIC_RANGED positivo -> aggregate reducers -> cap 30% -> commit uma vez`.

Launch receipt prova a ação ranged, não o custo corporal. Sem os dois elementos, fail-closed.

## Availability

Enquanto `METABOLIC_RANGED` e `BodyCostResolver` seguro estiverem ausentes, compra nova desabilitada; gasto zero; allocation legado vale 0 PP para gates e permanece reembolsável/migrável.

## Dedup / anti-abuso

Multishot/irmãos de projectile compartilham a root do disparo para este custo corporal; um launch causal -> no máximo um settlement. Projectile derivado/reemitido sem launch provenance não herda custo.

## Projetos próprios

RPG Skill Tree pode hospedar resolver/classifier futuro. Volcanoes, Enshrouded e Black Arcana não fornecem METABOLIC_RANGED. Recursos próprios desses sistemas não são substitutos.

## Pendências Chat 2

- `P-A0129-01` — materializar `UNAVAILABLE_NODE` enquanto `METABOLIC_RANGED` estiver ausente.
- `P-A0129-02` — somente habilitar se provider corporal real expuser custo causal do shot e boundary modificável; caso contrário manter fail-closed.
- `P-A0129-03` — launch provenance BOW/CROSSBOW + root/Multishot dedup sem confundir action receipt com cost receipt.
- `P-A0129-04` — cap 30%, lifecycle/rules reload/provider removal.

## Testes Chat 3

Purchase fail-before-spend; PP 0; bow/crossbow launch sem custo permanece sem benefício; nenhum exhaustion artificial; Stamina/ammo/draw/Focus/Cadence/mana intactos; Multishot root dedup; projectile derivado; provider future present/absent; cap 30%; multiplayer/lifecycle; GameTests/build/JAR/dedicated smoke.

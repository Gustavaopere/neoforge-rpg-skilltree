# A0158 — Resistência a Fogo I

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO BOUNDARY NEOFORGE.**

Chat 1 não implementa runtime. O contrato pode ser implementado pelo Chat 2 sem redesign porque NeoForge 1.21.1 expõe um pre-damage boundary mutável completo.

## Contrato

- Ponte VITALITY ↔ ARCANE; camada 4; Ramo; 4 ranks; 1 PP/rank.
- Pré-requisito: A0156 ≥1 **OU** Gateway VITALITY.
- +4% FIRE resistance/rank, máximo +16%.
- A contribuição existe somente no bucket canônico `RPG_FIRE_RESISTANCE`.
- Bridge PP segue contagem única; não pode satisfazer simultaneamente duas exigências de gasto como se fossem dois PP.

## Authority e hook

NeoForge `LivingDamageEvent.Pre` fornece `DamageSource`, `getNewDamage()` e `setNewDamage(...)` antes da perda de vida. Chat 2 deve criar/usar um único `DamageMitigationResolver` e classificar FIRE por tag/identidade canônica do `DamageSource`.

A contribuição do bucket é resolvida uma vez. Reduções externas/provider-native compõem fora dele de acordo com seus pipelines próprios.

## Pipeline

`incoming damage → canonical FIRE classification → somar A0158/A0159 no RPG_FIRE_RESISTANCE → clamp seguro [0,1] → aplicar bucket uma vez no LivingDamageEvent.Pre → demais etapas nativas`.

Não existe cap adicional de design além dos valores das perks; clamp [0,1] é apenas segurança matemática.

## Exclusões

- Cold Sweat body temperature/thermal;
- Volcanoes heat, biome ou lava por inferência;
- magic resistance geral, Armor ou Toughness;
- segundo reducer FIRE para cada perk;
- inferência por partícula/nome/provider instalado.

## Handoff Chat 2

Implementar A0158 e a infraestrutura compartilhada do bucket. Preservar idempotência e não duplicar o reducer com A0159.

## Testes Chat 3

1. ranks 0–4: 0/4/8/12/16%;
2. FIRE canônico positivo e não-FIRE negativo;
3. bucket aplicado exatamente uma vez;
4. composição multiplicativa com reduções externas sem duplicação;
5. Cold Sweat/Volcanoes thermal não entram;
6. bridge PP sem double count;
7. rank loss/respec/rules reload e dedicated server.
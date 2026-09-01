# A0143 — Nutrição Persistente

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE TRANSITIVO.**

No snapshot atual, A0142 Digestão Frugal é `UNAVAILABLE_NODE`; portanto A0143 também não pode ser comprada. Mesmo após A0142 vir a ser capability-eligible, cada canal de recuperação exige um hook server-authoritative próprio.

## Contrato

- Domínio SURVIVAL; ramo Nutrição — Dieta Persistente; camada 5; Keystone.
- 1 rank; 2 PP.
- Pré-requisitos: A0142 + Gateway SURVIVAL + Nutritional Balance 7.0.3 ativo com lista de nutrientes não vazia.
- Seja `N = getPlayerNutrients().size()` e `O = número de nutrientes individuais em ON_TARGET`.
- `MALNOURISHED` ou `ENGORGED`: ×1,00.
- `SAFE` e `2 × O < N`: ×1,05.
- `SAFE` e `2 × O ≥ N`: ×1,10.
- `ON_TARGET` global: ×1,15.
- O multiplicador atua somente sobre recuperação natural já positiva e causalmente identificada.

## Authority e providers

- Nutritional Balance 7.0.3 é owner do estado nutricional configurável.
- Epic Fight 21.17.3.1 continua owner de Stamina; A0143 não cria scheduler próprio nem altera active gains/refunds.
- Minecraft/NeoForge continua owner de cura; cura natural precisa ser distinguida de spell/poção/lifesteal/comida antes de aplicar o bônus.
- Farmer's Delight/Create e outros mods alimentares apenas fornecem alimentos; não substituem o provider nutricional.

## Availability

Compra exige A0142 capability-eligible. Como A0142 está fail-closed no snapshot atual, A0143 também é `UNAVAILABLE_NODE` e legacy allocation vale 0 PP.

Quando A0142 for habilitada, consulta nutricional segura é obrigatória. Canais STAMINA e HEALTH podem ser habilitados separadamente apenas quando cada um tiver um seam causal comprovado; ausência de um canal não autoriza inventar outro.

## Boundary nutricional

`Nutritional Balance getStatus() + getPlayerNutrients() → resolver tier dinâmico → recovery event causal elegível → multiplicar uma vez`.

Não hardcodear cinco nutrientes, nomes, thresholds ou semântica TFC.

## Hooks futuros

### STAMINA

`FUTURE_PROVIDER_CONTRACT`: modulação da **regeneração natural positiva** do Epic Fight 21.17.3.1 antes do settlement. Nenhum seam versionado suficiente foi provado neste ciclo.

### HEALTH

`FUTURE_PROVIDER_CONTRACT`: cura natural vanilla causalmente identificável antes do settlement. `LivingHealEvent` genérico, isoladamente, não prova origem natural; spell heal, poções, lifesteal, comida e cura externa não entram.

## Exclusões

- não criar ticks de Stamina ou HP;
- não transformar hunger/saturation/hydration/nutrient decay em cura;
- não aplicar em active Stamina gains/refunds;
- não aplicar em spell/poção/lifesteal/comida;
- não inferir dieta por lista fixa de nutrientes.

## Handoff Chat 2

- manter indisponível enquanto A0142 não for capability-eligible;
- implementar somente canais cuja origem natural esteja comprovada;
- se nenhum canal de recuperação natural for comprovado, não vender o node como funcional;
- divergência semântica de provider volta ao Chat 1.

## Testes Chat 3

1. availability transitiva A0142→A0143 e purchase fail-before-spend;
2. legacy allocation = 0 PP enquanto predecessor indisponível;
3. N dinâmico, inclusive N diferente de 5;
4. MALNOURISHED/ENGORGED nunca geram bônus;
5. tiers ×1,05/×1,10/×1,15 corretos;
6. STAMINA somente natural positiva, se provider existir;
7. HEALTH somente natural causal, se hook existir;
8. exclusão de spell/poção/lifesteal/comida/refunds;
9. um evento causal recebe no máximo um multiplicador;
10. provider removal/reload/respec/logout/restart/multiplayer.
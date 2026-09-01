# A0179 — Resistência a Natureza I

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO BOUNDARY NEOFORGE.**

Chat 1 não implementa runtime. O contrato está fechado para o Chat 2 porque `LivingDamageEvent.Pre` permite mitigação server-side e Iron's Spells 'n Spellbooks 3.16.3 fornece identidade NATURE explícita para `irons_spellbooks:nature_magic`. Não existe tag vanilla genérica NATURE aprovada; outras fontes ficam fail-closed até adapter exato.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81689d81fb4891784975`.

## Contrato

- VITALITY ↔ SURVIVAL ↔ ARCANE; camada 4; Ponte; 4 ranks; 1 PP/rank.
- Pré-requisito: A0177 ≥1 **OU** Gateway VITALITY desbloqueado.
- Como A0177 está `UNAVAILABLE_NODE`, o caminho adquirível atual é Gateway VITALITY.
- +4% de Resistência a Natureza por rank; máximo +16%.
- Bucket único: `RPG_NATURE_RESISTANCE`.
- 16% é o teto próprio de A0179, não cap defensivo global.

## Boundary e authority

Usar o mesmo `ElementalDamageMitigationResolver` da família defensiva elemental.

Classificação NATURE segura no snapshot atual:

1. Iron's 3.16.3 `irons_spellbooks:nature_magic` / tag provider `NATURE_MAGIC` por adapter exato;
2. outros providers somente por adapter versionado explícito que prove o DamageSource NATURE correspondente.

Não existe tag vanilla NATURE genérica aprovada. Portanto poison, thorn, dano físico de planta/fauna e ambiente **não** são fallback.

A classificação defensiva NATURE não prova magia DIRECT e não libera A0177/A0178.

## Pipeline canônico

`LivingDamageEvent.Pre -> classificar source NATURE uma vez -> calcular RPG_NATURE_RESISTANCE de A0179/A0180 -> clamp matemático seguro [0,1] -> setNewDamage(current * (1 - bucket)) uma vez`.

Adapters classificam; não aplicam redução por conta própria.

## Deduplicação

- um único `ElementalDamageMitigationResolver`;
- A0179 e A0180 somam no mesmo bucket;
- um root/evento recebe a família `RPG_NATURE_RESISTANCE` no máximo uma vez;
- não duplicar mitigação em listener Iron's + listener NeoForge.

## Fallback e exclusões

Fonte desconhecida/não versionada é inelegível.

Proibido inferir NATURE por:

- poison/veneno genérico;
- planta, folha, espinho ou vine;
- fauna;
- fome/clima/bioma;
- cor/partícula;
- namespace/nome textual;
- magia genérica.

A topologia SURVIVAL pode conectar a ponte, mas não redefine o elemento nem a classificação de dano.

## Bridge PP

`PP_REGION: VITALITY_NATURE_BRIDGE/RESISTANCE`.

Contagem de bridge é unitária: o mesmo PP não pode satisfazer simultaneamente duas regiões como dois pontos distintos. Specialist só pode whitelistar por regra semântica explícita.

## Handoff Chat 2

Implementar A0179 no mesmo `ElementalDamageMitigationResolver`; adicionar classifier exato de Iron's `nature_magic`; manter todas as demais fontes fail-closed até adapter comprovado. Não adicionar poison/ambiente como fallback.

## Testes obrigatórios para Chat 3

1. ranks 0–4 = 0/4/8/12/16%;
2. Iron's `nature_magic` positivo com provider/adaptor exato;
3. source não-NATURE negativo;
4. poison/thorn/planta/fauna/ambiente negativos;
5. provider desconhecido/version mismatch fail-closed;
6. bucket aplicado exatamente uma vez;
7. composição A0179+A0180 sem double reducer;
8. bridge PP sem double-count;
9. A0177 unavailable não bloqueia rota via Gateway VITALITY;
10. respec/reload/dedicated-server safety.

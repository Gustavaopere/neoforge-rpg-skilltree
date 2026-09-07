# A0186 — Resistência a Sagrado I

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO BOUNDARY NEOFORGE.**

Chat 1 não implementa runtime. A perk possui classifier HOLY defensivo concreto no Iron's 3.16.3 e NeoForge 1.21.1 fornece `LivingDamageEvent.Pre` mutável. Como A0184 está unavailable, o caminho adquirível atual é Gateway VITALITY.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db8152b038c64ffbcb913d`.

## Contrato

- VITALITY ↔ HEALING ↔ ARCANE; camada 4; Ponte; 4 ranks; 1 PP/rank.
- Pré-requisito: A0184 ≥1 **OU** Gateway VITALITY.
- +4% de Resistência a Sagrado por rank: 4/8/12/16%.
- Um único modifier/bucket: `RPG_HOLY_RESISTANCE`.
- 16% é o teto próprio de A0186, não cap defensivo global.

## Classifier HOLY aprovado

Iron's Spells 'n Spellbooks 3.16.3 registra:

- school `irons_spellbooks:holy`;
- `ISSDamageTypes.HOLY_MAGIC`;
- tag `irons_spellbooks:holy_magic` / `HOLY_MAGIC`.

Não existe tag vanilla HOLY genérica aprovada para este contrato. Outros providers só entram por adapter exato/versionado que prove o DamageSource HOLY correspondente.

A classificação defensiva **não prova** `DIRECT_MAGIC_OUTCOME_V1` e não habilita A0184/A0185 ofensivas.

## Boundary e pipeline canônico

NeoForge 1.21.1 `LivingDamageEvent.Pre` é o único boundary de mutação desta família.

Chat 2 deve introduzir ou reutilizar **um único** `ElementalDamageMitigationResolver` compartilhado com FIRE/ICE/LIGHTNING/NATURE:

`LivingDamageEvent.Pre -> classificar elemento uma vez -> calcular bucket HOLY A0186/A0187 -> clamp matemático [0,1] -> setNewDamage(current * (1 - bucket)) uma vez -> demais mitigadores de identidade distinta`.

O clamp é proteção matemática, não novo cap de design.

## Deduplicação

- um único listener/resolver;
- A0186 e A0187 somam no mesmo `RPG_HOLY_RESISTANCE`;
- adapters classificam e nunca aplicam redução paralela;
- uma instância/root de dano é processada uma vez pela família HOLY;
- não reduzir em evento Iron's e novamente no NeoForge.

## Fallback

Fonte sem classifier HOLY exato fica sem benefício. Proibido inferir HOLY por:

- `Tags.DamageTypes.IS_MAGIC` genérico;
- luz/partícula;
- cura/absorção/bênção;
- entidade undead;
- religião/reputação/ritual;
- namespace ou nome textual.

Ausência do Iron's não desabilita o node estrutural, mas deixa fontes não adaptadas inelegíveis.

## Bridge PP

`PP_REGION: VITALITY_HEALING_HOLY_BRIDGE/RESISTANCE`.

O mesmo PP não satisfaz simultaneamente thresholds puros VITALITY, HEALING e ARCANE. Specialist pode whitelistar a ponte em no máximo um threshold semântico explícito.

## Handoff Chat 2

Implementar A0186 no `ElementalDamageMitigationResolver` compartilhado, sem novo reducer. O classifier mínimo é a tag/identidade HOLY exata do Iron's 3.16.3. Providers adicionais permanecem fail-closed até adapter comprovado.

## Testes obrigatórios para Chat 3

1. ranks 0–4 = 0/4/8/12/16%;
2. Iron's `HOLY_MAGIC` positivo;
3. magic genérico não-HOLY negativo;
4. cura/absorção/luz/undead negativos como classifier;
5. provider ausente/version mismatch sem crash e sem benefício indevido;
6. bucket aplicado exatamente uma vez;
7. composição multiplicativa com mitigadores distintos sem double-processing;
8. Gateway VITALITY permite aquisição mesmo com A0184 unavailable;
9. bridge PP sem double-count;
10. respec/reload/dedicated-server safety.
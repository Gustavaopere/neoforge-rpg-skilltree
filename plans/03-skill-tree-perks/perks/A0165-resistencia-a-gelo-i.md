# A0165 — Resistência a Gelo I

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO BOUNDARY NEOFORGE.**

Chat 1 não implementa runtime. O contrato está suficientemente fechado para o Chat 2 porque NeoForge 1.21.1 fornece um pre-damage boundary mutável e os classifiers iniciais seguros podem ser definidos sem inventar mecânica de provider.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81da9c6ce0e845350cd5`.

## Contrato

- VITALITY ↔ ARCANE; camada 4; Ponte; 4 ranks; 1 PP/rank.
- Pré-requisito: A0163 ≥1 **OU** Gateway VITALITY.
- Como A0163 está `UNAVAILABLE_NODE`, o caminho adquirível atual é Gateway VITALITY.
- +4% de Resistência a Gelo por rank; máximo +16%.
- Uma única identidade de modifier/bucket: `RPG_ICE_RESISTANCE`.
- Não existe cap defensivo global adicional implícito; 16% é o teto próprio de A0165.

## Boundary e authority

NeoForge 1.21.1 `LivingDamageEvent.Pre` fornece o dano corrente mutável via `getNewDamage()`/`setNewDamage(...)` antes da perda final de vida. Chat 2 deve introduzir/usar **um único** `ElementalDamageMitigationResolver` para a família elemental defensiva.

Classificação ICE inicial segura:

1. vanilla: `DamageTypeTags.IS_FREEZING` — em 1.21.1 contém `minecraft:freeze`;
2. Iron's Spells 'n Spellbooks 3.16.3: adapter exato para `irons_spellbooks:ice_magic`;
3. demais providers: somente adapters versionados que provem o DamageSource ICE correspondente.

Adapters classificam; não aplicam mitigação paralela.

Cold Sweat 2.4.2 conserva autoridade térmica. BODY temperature, freezing comfort, CHILL e outras condições sem dano não passam por A0165.

## Pipeline canônico

`LivingDamageEvent.Pre -> classificar DamageSource uma vez -> se ICE, calcular RPG_ICE_RESISTANCE de A0165/A0166 -> clamp matemático seguro [0,1] -> event.setNewDamage(getNewDamage() * (1 - bucket)) uma vez -> demais pipelines nativos/externos`.

O clamp [0,1] é segurança matemática, não um novo cap de design.

## Deduplicação

- um listener/resolver canônico para o bucket;
- A0165 e A0166 somam contribuição no mesmo bucket;
- adapter de Iron's/Ars/etc. só retorna classificação/evidência, nunca chama `setNewDamage` por conta própria;
- não aplicar uma redução em evento provider e outra em NeoForge para o mesmo hit;
- a própria instância de evento/root damage deve ser processada uma vez pela família `RPG_ICE_RESISTANCE`.

## Fallback

Fonte não reconhecida inequivocamente como ICE fica sem benefício. A perk continua funcional nos classifiers seguros presentes.

Proibido inferir ICE por:

- Slowness;
- CHILL;
- partículas/visual;
- bioma/temperatura;
- nome textual do DamageSource;
- namespace genérico ou simples presença do mod.

Não converter em resistência mágica, térmica, Armor ou Toughness.

## Bridge PP

`PP_REGION: VITALITY_ICE_BRIDGE/RESISTANCE`.

O mesmo PP não satisfaz simultaneamente thresholds puros VITALITY e ARCANE como se fossem dois pontos. Specialist pode whitelistar a bridge em no máximo um threshold semântico explícito.

## Handoff Chat 2

Implementar A0165 e a infraestrutura compartilhada `ElementalDamageMitigationResolver` sem criar segundo reducer. Começar pelos classifiers vanilla `IS_FREEZING` e Iron's `ice_magic`; providers sem adapter completo permanecem fail-closed.

## Testes obrigatórios para Chat 3

1. ranks 0–4 = 0/4/8/12/16%;
2. `minecraft:freeze`/`IS_FREEZING` positivo;
3. Iron's `ice_magic` positivo quando o provider/adaptor está presente;
4. dano não-ICE negativo;
5. provider desconhecido/mismatch negativo;
6. bucket aplicado exatamente uma vez;
7. composição com mitigadores de identidade distinta sem double-processing;
8. Cold Sweat temperature/CHILL/Slowness não entram;
9. bridge PP sem double-count;
10. respec/rank loss/reload e dedicated-server safety.
# A0165 — Resistência a Gelo I

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO BOUNDARY NEOFORGE.**

Chat 1 não implementa runtime. O contrato está suficientemente fechado para o Chat 2 porque NeoForge 1.21.1 fornece um pre-damage boundary mutável e a classificação ICE base pode usar uma tag semântica canônica sem inventar mecânica de provider.

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

Classificação ICE segura:

1. qualquer `DamageSource` que satisfaça `DamageTypeTags.IS_FREEZING` é ICE para esta resistência;
2. isso cobre `minecraft:freeze` e também `ars_nouveau:cold_snap` em Ars Nouveau 5.13.1, pois o próprio provider registra `COLD_SNAP` em `IS_FREEZING`;
3. Iron's Spells 'n Spellbooks 3.16.3: adapter exato para `irons_spellbooks:ice_magic`, porque esse DamageType não foi provado em `IS_FREEZING`;
4. outros providers: somente a tag semântica canônica ou adapters versionados que provem o DamageSource ICE correspondente.

`IS_FREEZING` aqui é classificação de **dano ICE defensivo**. Ela não prova magia direta e não autoriza A0163/A0164.

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

Fonte não reconhecida por `IS_FREEZING` nem por adapter ICE versionado explícito fica sem benefício. Ausência do adapter do Iron's desativa somente `ice_magic`; não desabilita vanilla nem Ars Cold Snap já cobertos pela tag.

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

Implementar A0165 e a infraestrutura compartilhada `ElementalDamageMitigationResolver` sem criar segundo reducer. O classifier base é `source.is(DamageTypeTags.IS_FREEZING)`; adicionar adapter exato para Iron's `ice_magic`. Providers sem tag/adaptor completo permanecem fail-closed.

## Testes obrigatórios para Chat 3

1. ranks 0–4 = 0/4/8/12/16%;
2. `minecraft:freeze`/`IS_FREEZING` positivo;
3. Ars Nouveau 5.13.1 `ars_nouveau:cold_snap` positivo pela mesma tag, sem adapter redundante;
4. Iron's `ice_magic` positivo quando provider/adapter está presente;
5. dano não-ICE e provider desconhecido/mismatch negativos;
6. bucket aplicado exatamente uma vez;
7. composição com mitigadores de identidade distinta sem double-processing;
8. Cold Sweat temperature/CHILL/Slowness não entram;
9. bridge PP sem double-count;
10. respec/rank loss/reload e dedicated-server safety.

# A0172 — Resistência a Raio I

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO BOUNDARY NEOFORGE.**

Chat 1 não implementa runtime. O contrato está suficientemente fechado para o Chat 2 porque NeoForge 1.21.1 fornece `LivingDamageEvent.Pre`, Minecraft possui `DamageTypeTags.IS_LIGHTNING` e Iron's Spells 'n Spellbooks 3.16.3 possui identidade `irons_spellbooks:lightning_magic` explícita.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db814e87d8ea494cca6de0`.

## Contrato

- VITALITY ↔ ARCANE; camada 4; Ponte; 4 ranks; 1 PP/rank.
- Pré-requisito: A0170 ≥1 **OU** Gateway VITALITY desbloqueado.
- Como A0170 está `UNAVAILABLE_NODE`, o caminho adquirível atual é Gateway VITALITY.
- +4% de Resistência a Raio por rank; máximo +16%.
- Bucket único: `RPG_LIGHTNING_RESISTANCE`.
- 16% é o teto próprio de A0172, não um cap defensivo global.

## Boundary e authority

Chat 2 deve introduzir/estender **um único** `ElementalDamageMitigationResolver` em `LivingDamageEvent.Pre`.

Classificação LIGHTNING segura:

1. `DamageSource` que satisfaz `DamageTypeTags.IS_LIGHTNING` é LIGHTNING para esta resistência;
2. Iron's 3.16.3: adapter exato para `irons_spellbooks:lightning_magic` / tag provider `LIGHTNING_MAGIC`;
3. outros providers: somente adapters versionados que provem a identidade LIGHTNING correspondente.

A classificação defensiva não prova magia DIRECT e não libera A0170/A0171/A0174.

Create, Oritech, FE e outros sistemas tecnológicos permanecem fora, salvo se um DamageSource for explicitamente classificado LIGHTNING pelo contrato defensivo; eletricidade temática por si só nunca basta.

## Pipeline canônico

`LivingDamageEvent.Pre -> classificar source uma vez -> calcular RPG_LIGHTNING_RESISTANCE de A0172/A0173 -> clamp matemático seguro [0,1] -> setNewDamage(current * (1 - bucket)) uma vez`.

Adapters classificam; não aplicam mitigação paralela.

## Deduplicação

- um resolver para a família elemental defensiva;
- A0172 e A0173 somam no mesmo bucket;
- um root/evento processado no máximo uma vez por `RPG_LIGHTNING_RESISTANCE`;
- não instalar listener provider e listener NeoForge que reduzam o mesmo dano duas vezes.

## Fallback

Fonte desconhecida/não versionada é inelegível e permanece fail-closed. Ausência de adapter de um provider opcional não desabilita os classifiers já comprovados.

Proibido inferir LIGHTNING por:

- FE/energia armazenada;
- Create/Oritech/máquina;
- partícula/som/cor;
- alvo molhado;
- nome textual do source;
- simples presença do mod.

## Bridge PP

`PP_REGION: VITALITY_LIGHTNING_BRIDGE/RESISTANCE`.

O mesmo PP não pode valer simultaneamente como dois PP distintos em regiões semânticas diferentes. Specialist pode considerar bridge apenas por regra explícita de contagem única.

## Handoff Chat 2

Implementar A0172 no `ElementalDamageMitigationResolver`; usar `IS_LIGHTNING` e adapter exato para Iron's `lightning_magic`. Não criar reducer exclusivo da perk nem converter tecnologia em magia LIGHTNING.

## Testes obrigatórios para Chat 3

1. ranks 0–4 = 0/4/8/12/16%;
2. `IS_LIGHTNING` positivo;
3. Iron's `lightning_magic` positivo quando adapter/provider exato estiver presente;
4. source não-LIGHTNING negativo;
5. FE/Create/Oritech sem classifier negativo;
6. provider desconhecido/version mismatch fail-closed;
7. bucket aplicado exatamente uma vez;
8. composição A0172+A0173 sem double reducer;
9. bridge PP sem double-count;
10. respec/reload/dedicated-server safety.

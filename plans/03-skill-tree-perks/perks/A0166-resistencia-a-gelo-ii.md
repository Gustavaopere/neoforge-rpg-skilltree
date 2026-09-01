# A0166 — Resistência a Gelo II

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO MESMO BOUNDARY DE A0165.**

A0166 não cria um segundo sistema defensivo. Ela é uma contribuição condicional adicional ao mesmo `RPG_ICE_RESISTANCE` calculado por A0165 no único `ElementalDamageMitigationResolver`.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81f39addd4bfdc37ad45`.

## Contrato

- VITALITY ↔ ARCANE; camada 5; Ramo; 3 ranks; 1 PP/rank.
- Dependência: A0165 rank ≥2.
- Se a vida **imediatamente anterior ao evento** estiver estritamente abaixo de 50% da vida máxima, acrescentar +4% `RPG_ICE_RESISTANCE` por rank.
- Rank 1/2/3: +4%/+8%/+12% adicionais sob o gate.
- Vida exatamente em 50% não ativa A0166.
- A0165=4 + A0166=3 sob <50% HP produz contribuição local máxima de 28% desta família; isso não é cap defensivo global.

## Authority e boundary

Usar exatamente o mesmo `LivingDamageEvent.Pre` e o mesmo classifier/resolver de A0165:

- vanilla `DamageTypeTags.IS_FREEZING`;
- Iron's 3.16.3 `irons_spellbooks:ice_magic` via adapter exato;
- demais providers apenas com adapters versionados.

A leitura `health/maxHealth` é server-authoritative e acontece antes de A0166 modificar o dano. Não usar vida pós-impacto ou previsão de vida restante.

## Pipeline

`LivingDamageEvent.Pre -> classificar ICE uma vez -> ler health/maxHealth pré-impacto -> somar A0165 + (A0166 se health/maxHealth < 0.5) no mesmo RPG_ICE_RESISTANCE -> clamp seguro -> aplicar bucket uma vez`.

A0166 não registra listener separado, não cria outro bucket e não chama mitigação pela segunda vez.

## Gates negativos

Não ativar A0166 por:

- vida exatamente 50%;
- vida pós-dano;
- temperatura corporal;
- CHILL/Slowness;
- armor/toughness;
- cura projetada ou overheal;
- simples presença de provider ICE.

## Fallback

Se a fonte não for classificada com segurança como ICE, A0166 não contribui. A0165 continua funcionando onde seu classifier for válido. Ausência/version mismatch de adapter externo desativa apenas aquele adapter.

## Deduplicação e bridge PP

- um evento/root recebe o bucket `RPG_ICE_RESISTANCE` no máximo uma vez;
- A0165/A0166 são termos do mesmo bucket;
- adapters classificam, não mitigam;
- `PP_REGION: VITALITY_ICE_BRIDGE/RESISTANCE` e regra de contagem única da bridge permanecem iguais a A0165.

## Handoff Chat 2

Implementar A0166 como termo condicional dentro do mesmo resolver criado/expandido para A0165. Não criar segundo listener nem stateful cooldown.

## Testes obrigatórios para Chat 3

1. A0165 rank ≥2 como prerequisite;
2. A0166 ranks 0–3 = +0/4/8/12% sob <50%;
3. 49,99% HP ativa, 50,00% e 50,01% não ativam;
4. vida lida antes do dano atual;
5. A0165=4 + A0166=3 = 28% local sob gate;
6. A0165 mantém sua contribuição fora do gate de A0166;
7. ICE classifiers iguais aos de A0165;
8. uma aplicação do bucket por evento;
9. Cold Sweat/CHILL/Slowness negativos;
10. respec/reload/dedicated server e multiplayer.
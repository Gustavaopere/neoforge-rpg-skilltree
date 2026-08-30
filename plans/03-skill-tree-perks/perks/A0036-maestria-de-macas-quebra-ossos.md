# A0036 — Maestria de Maças — Quebra-Ossos

## Estado

- **Design:** APROVADO após correção canônica.
- **Implementação:** NÃO CONFIRMADA; `P-A0036-01`, `P-A0036-02` e dependência de `P-A0031-02`.
- **Notion:** `3c569db9-f0db-810d-8095-d44ead0e1310`.

## Contrato canônico

- Capstone; A0034 + A0035 + `combat:mace` ≥80 (= 8 tipos hostis distintos no modelo anti-farm).
- Em alvo sob Armadura Fendida, golpe pesado MACE provider-confirmed pode aplicar Descompasso por 3 s.
- Descompasso: −8% dano físico causado + −10% velocidade de movimento; boss recebe metade.
- Cooldown por alvo 12/11/10 s em mastery 80/90/100.
- Sem heavy receipt ou sem ponto seguro para ambos debuffs, fail-closed; não substituir por stun/dano/penetração.

## Evidência runtime

- `A0021A0040CombatPolicy` possui regra completa e resultado `applyBonebreaker`.
- `A0021A0040EpicFightHooks.onDamagePre` envia `heavy=false` para os HitFacts.
- `onDamagePost` aplica Armor Sunder, mas não existe caller runtime de `applyBonebreaker`; busca no repositório encontra o campo somente no policy/testes.
- Mastery `combat:mace` ainda recebe 3 XP por hit via `A0021A0040MasteryPolicy`.

## Pendências Chat 2

- **P-A0036-01:** integrar heavy receipt MACE provider-native/versionado; nenhuma heurística.
- **P-A0036-02:** aplicar realmente os dois debuffs temporários de Descompasso server-side, com boss half, cooldown e cleanup; não reduzir resistência mágica.
- Depende de **P-A0031-02** para mastery anti-farm.
- A classificação Witherstein/boss depende de `P-A0035-01`.

## Provider→árvore

Backlash, Shroud/Exposure, hazards Volcanoes e companions Mobstein não fornecem heavy receipt nem autoria MARTIAL. TERMINAL_EXTERIOR: MARTIAL/MAÇAS permanece preservado.

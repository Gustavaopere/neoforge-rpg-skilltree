# A0036 — Maestria de Maças — Quebra-Ossos

## Estado

- **Design:** APROVADO após correção canônica.
- **Implementação:** NÃO CONFIRMADA; `P-A0036-01`, `P-A0036-02`, `P-A0036-03` e dependência de `P-A0031-02`.
- **Notion:** `3c569db9-f0db-810d-8095-d44ead0e1310`.

## Contrato canônico

- Capstone; A0034 + A0035 + `combat:mace` ≥80 (= 8 tipos hostis distintos no modelo anti-farm).
- Em alvo **já sob Armadura Fendida antes do golpe atual**, golpe pesado MACE provider-confirmed pode aplicar Descompasso por 3 s.
- Descompasso: −8% dano físico causado + −10% velocidade de movimento; boss recebe metade.
- Cooldown por alvo 12/11/10 s em mastery 80/90/100.
- Sem heavy receipt ou sem ponto seguro para ambos debuffs, fail-closed; não substituir por stun/dano/penetração.

## Evidência runtime

- `A0021A0040CombatPolicy` possui regra e resultado `applyBonebreaker`.
- `A0021A0040EpicFightHooks.onDamagePre` envia `heavy=false` para os HitFacts.
- `onDamagePost` aplica Armor Sunder, mas não existe caller runtime de `applyBonebreaker`; busca no repositório encontra o campo somente no policy/testes.
- Há ainda um problema de sequencing no policy atual: no mesmo `beforeHit`, A0035 pode consumir Trauma e chamar `markSundered`, e em seguida A0036 consulta `isSundered`. Quando um heavy receipt for adicionado, isso permitiria ao mesmo golpe criar Armadura Fendida e imediatamente satisfazer a condição de Quebra-Ossos, embora o contrato exija alvo previamente sob Armadura Fendida.
- Mastery `combat:mace` ainda recebe 3 XP por hit via `A0021A0040MasteryPolicy`.

## Pendências Chat 2

- **P-A0036-01:** integrar heavy receipt MACE provider-native/versionado; nenhuma heurística.
- **P-A0036-02:** aplicar realmente os dois debuffs temporários de Descompasso server-side, com boss half, cooldown e cleanup; não reduzir resistência mágica.
- **P-A0036-03:** preservar a ordem causal A0035→A0036. A elegibilidade de Quebra-Ossos deve usar um snapshot/receipt de `Sundered` existente antes do root action atual; o mesmo golpe não pode criar Armadura Fendida e ativar Descompasso. A correção deve permanecer compatível com `P-A0035-02`, que move o commit de A0035 para pós-hit confirmado.
- Depende de **P-A0031-02** para mastery anti-farm.
- A classificação Witherstein/boss depende de `P-A0035-01`.

## Provider→árvore

Backlash, Shroud/Exposure, hazards Volcanoes e companions Mobstein não fornecem heavy receipt nem autoria MARTIAL. TERMINAL_EXTERIOR: MARTIAL/MAÇAS permanece preservado.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura MACE:** Pernach/arma Simply More só entra quando a família `MACE` é comprovada pelo Epic Fight Compat ou mapping versionado; o nome do tipo não é suficiente.
- **Preexistência RPG:** debuff/armor reduction provider-native não satisfaz a exigência de Armadura Fendida RPG já existente antes do root atual.
- **Heavy permanece ausente:** Implicit, Unique ability, debuff, gem power, stun, dano alto ou animação Simply/Simply More não é heavy receipt e não fecha `P-A0036-01`.
- **Sequencing:** a regra A0035→A0036 continua estrita; o mesmo golpe não pode criar `Sundered` e ativar Descompasso, independentemente de efeitos provider-owned no root.
- **Alpha:** Unique/Implicit Simply More não comprovado permanece fail-closed; nenhuma inferência pelo namespace.
- **Notion:** quatro propriedades corrigidas e re-fetch PASS.

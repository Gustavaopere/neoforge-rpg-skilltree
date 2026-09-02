# A0036 — Maestria de Maças — Quebra-Ossos

## Estado

- **Design:** APROVADO após correção canônica.
- **Implementação:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-810d-8095-d44ead0e1310`.

## Contrato canônico

- Capstone; A0034 + A0035 + `combat:mace` ≥80 (= 8 tipos hostis distintos no modelo anti-farm).
- Em alvo **já sob Armadura Fendida antes do golpe atual**, golpe pesado MACE provider-confirmed pode aplicar Descompasso por 3 s.
- Descompasso: −8% dano físico causado + −10% velocidade de movimento; boss recebe metade.
- Cooldown por alvo 12/11/10 s em mastery 80/90/100.
- Sem heavy receipt ou sem ponto seguro para ambos debuffs, fail-closed; não substituir por stun/dano/penetração.

## Evidência runtime após Chat 2

- `A0021A0040CombatPolicy` tira snapshot de `isSundered(...)` antes de preparar A0035 no root atual; o mesmo golpe não pode criar Armadura Fendida e satisfazer A0036.
- A0036 usa `prepareBonebreaker(...)`/`commitPreparedBonebreaker(...)`; cooldown só começa após POST confirmado.
- O bridge possui consumidor de Descompasso para os dois componentes aprovados: modifier transitório de movimento e multiplicador somente sobre dano classificado pelo `rpgskilltree:physical` DamageType tag.
- O bridge real continua enviando `heavyConfirmed=false`, pois Epic Fight 21.17.3.1 não fornece receipt inequívoco de heavy attack nos hooks auditados.
- `shouldChargeWeapon`, animação, dano alto, arma lenta, Impact ou charge-time estimado continuam proibidos como substitutos.
- `combat:mace` usa discovery finita +10/tipo; 8 tipos atingem 80.

## Pendências Chat 2

- **P-A0036-01 — FAIL-CLOSED PRESERVADO:** heavy receipt provider-native/versionado continua ausente; A0036 não ativa no gameplay atual.
- **P-A0036-02 — IMPLEMENTAÇÃO LATENTE PRESENTE:** ambos os debuffs de Descompasso, boss half, duração, cooldown e cleanup possuem consumer server-side, mas só podem operar quando um futuro receipt seguro tornar `heavyConfirmed=true`.
- **P-A0036-03 — RESOLVIDA NO CÓDIGO:** elegibilidade usa snapshot de `Sundered` preexistente antes do root; A0035 commitada no mesmo golpe não habilita A0036.
- Dependência de **P-A0031-02 — RESOLVIDA NO CÓDIGO** pela mastery anti-farm.
- Classificação Witherstein/boss continua dependente de prova versionada conforme A0035.

## Provider→árvore

Backlash, Shroud/Exposure, hazards Volcanoes e companions Mobstein não fornecem heavy receipt nem autoria MARTIAL. TERMINAL_EXTERIOR: MARTIAL/MAÇAS permanece preservado.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura MACE:** Pernach/arma Simply More só entra quando a família `MACE` é comprovada pelo Epic Fight Compat ou mapping versionado; o nome do tipo não é suficiente.
- **Preexistência RPG:** debuff/armor reduction provider-native não satisfaz Armadura Fendida RPG preexistente.
- **Heavy permanece ausente:** Implicit, Unique ability, debuff, gem power, stun, dano alto ou animação Simply/Simply More não é heavy receipt.
- **Sequencing:** o mesmo golpe não cria `Sundered` e ativa Descompasso.
- **Alpha:** Unique/Implicit Simply More não comprovado permanece fail-closed.

## Handoff Chat 3

Validar que A0036 permanece inativa com `heavyConfirmed=false`; testar diretamente o consumer latente com receipt controlado, preexistência de Sunder, boss half, dano físico-only, movement, 3 s, cooldown 12/11/10, rollback e cleanup. O Chat 2 não executou a bateria final e não declara a perk utilizável enquanto o heavy receipt estiver ausente.

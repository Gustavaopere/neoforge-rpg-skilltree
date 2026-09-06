# A0036 — Maestria de Maças — Quebra-Ossos

## Estado

- **Design:** APROVADO após correção canônica.
- **Implementação:** **NÃO CONFIRMADA / FAIL-CLOSED CORRETO**; consumer latente validado pelo Chat 3 na PR #359.
- **Notion:** `3c569db9-f0db-810d-8095-d44ead0e1310`.

## Contrato canônico

- Capstone; A0034 + A0035 + `combat:mace` ≥80 (= 8 tipos hostis distintos no modelo anti-farm).
- Em alvo já sob Armadura Fendida antes do golpe atual, golpe pesado MACE provider-confirmed pode aplicar Descompasso por 3 s.
- Descompasso: −8% dano físico causado + −10% velocidade de movimento; boss recebe metade.
- Cooldown por alvo 12/11/10 s em mastery 80/90/100.
- Sem heavy receipt ou sem ponto seguro para ambos debuffs, fail-closed; não substituir por stun/dano/penetração.

## Evidência runtime

- `A0021A0040CombatPolicy` tira snapshot de `isSundered(...)` antes de preparar A0035; o mesmo golpe não pode criar Armadura Fendida e satisfazer A0036.
- A0036 usa `prepareBonebreaker(...)`/`commitPreparedBonebreaker(...)`; cooldown só começa após POST confirmado.
- O consumer de Descompasso aplica modifier transitório de movimento e multiplicador somente sobre dano do tag `rpgskilltree:physical`.
- O bridge real continua enviando `heavyConfirmed=false`, pois Epic Fight 21.17.3.1 não fornece receipt inequívoco de heavy attack nos hooks auditados.
- `shouldChargeWeapon`, animação, dano alto, arma lenta, Impact ou charge-time estimado permanecem proibidos como substitutos.
- `combat:mace` usa discovery finita +10/tipo; 8 tipos atingem 80.

## Pendências

- **P-A0036-01 — ABERTA / FAIL-CLOSED CORRETO:** heavy receipt provider-native/versionado continua ausente; A0036 não ativa no gameplay atual.
- **P-A0036-02 — CONSUMER LATENTE VALIDADO:** ambos os debuffs, boss-half, duração, cooldown e cleanup estão presentes/testados, sem autorizar ativação sem receipt.
- **P-A0036-03 — RESOLVIDA:** elegibilidade usa Sunder preexistente antes do root; A0035 commitada no mesmo golpe não habilita A0036.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- Pernach/arma Simply More só entra quando a família `MACE` é comprovada pelo Epic Fight Compat ou mapping versionado.
- Debuff/armor reduction provider-native não satisfaz Armadura Fendida RPG preexistente.
- Implicit, Unique ability, debuff, gem power, stun, dano alto ou animação Simply/Simply More não é heavy receipt.
- O mesmo golpe não cria `Sundered` e ativa Descompasso.
- Unique/Implicit Simply More não comprovado permanece fail-closed.

## Fechamento Chat 3

Testes confirmam que o consumer latente é causal, físico-only, bounded e rollback-safe quando recebe um receipt controlado. A perk permanece deliberadamente não operacional no adapter real porque `heavyConfirmed=false`. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`.

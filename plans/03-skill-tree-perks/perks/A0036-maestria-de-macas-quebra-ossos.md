# A0036 — Maestria de Maças — Quebra-Ossos

## Estado

- **Design:** APROVADO após correção canônica.
- **Implementação:** NÃO CONFIRMADA / FAIL-CLOSED CORRETO. `P-A0036-02` e `P-A0036-03` foram resolvidas na PR #252; Mastery anti-farm também foi resolvida. `P-A0036-01` permanece bloqueante porque não existe heavy receipt inequívoco comprovado na API pública auditada do Epic Fight 1.21.1.
- **Notion:** `3c569db9-f0db-810d-8095-d44ead0e1310`.

## Contrato canônico

- Capstone; A0034 + A0035 + `combat:mace` ≥80 (= 8 tipos hostis distintos no modelo anti-farm).
- Em alvo **já sob Armadura Fendida antes do golpe atual**, golpe pesado MACE provider-confirmed pode aplicar Descompasso por 3 s.
- Descompasso: −8% dano físico causado + −10% velocidade de movimento; boss recebe metade.
- Cooldown por alvo 12/11/10 s em mastery 80/90/100.
- Sem heavy receipt ou sem ponto seguro para ambos debuffs, fail-closed; não substituir por stun/dano/penetração.

## Evidência runtime

- `A0021A0040CombatPolicy` preserva um snapshot `preexistingSunder` antes de preparar A0035; o mesmo root não consegue criar Armadura Fendida e satisfazer A0036.
- A0036 usa `prepareBonebreaker(...)` no PRE e `commitPreparedBonebreaker(...)` somente no POST confirmado; cancelamento/dano zero descarta a reserva e não inicia cooldown.
- `A0021A0040EpicFightHooks` possui runtime de Descompasso: movimento via modifier transitório `Attributes.MOVEMENT_SPEED`; dano causado reduzido somente quando o `DamageType` está no tag canônico `rpgskilltree:physical`; boss-half é preservado pelos escalares da policy.
- Cleanup de Descompasso ocorre por expiry, death, logout/dimension/respawn e server stop.
- `combat:mace` usa discovery finita +10 por tipo hostil distinto; 80 = 8 tipos.
- A ativação runtime continua fail-closed porque `onDamagePre` não fabrica `heavy=true` sem receipt comprovado.

## Auditoria do heavy receipt — Chat 3

- `DealDamageEvent` do Epic Fight 1.21.1 expõe alvo, dano original/modificado e `EpicFightDamageSource`, mas não uma flag pública inequívoca de heavy attack.
- `EpicFightDamageSource.shouldChargeWeapon()` foi rejeitado como substituto: no `PlayerPatch` auditado, `setChargeWeapon(...)` deriva de `isComboAttackAnimation()`/variável de combo, portanto representa charge/combo e não prova semanticamente o “golpe pesado” exigido pelo dossiê.
- Busca textual por `heavy_attack` na árvore pública auditada não revelou receipt canônico utilizável.
- Consequência: nenhuma heurística por animação, dano alto, lentidão da arma, combo, stun ou impacto foi introduzida.

## Pendências Chat 2 / resolução Chat 3

- **P-A0036-01 — ABERTA / BLOQUEANTE:** heavy receipt MACE provider-native/versionado ausente; A0036 permanece inativa/fail-closed no runtime real.
- **P-A0036-02 — RESOLVIDA E TESTADA:** Descompasso possui os dois debuffs temporários, boss-half, expiry/cooldown e cleanup; dano não-físico fica fora do multiplicador.
- **P-A0036-03 — RESOLVIDA E TESTADA:** Sundered deve preexistir ao root atual; commit A0035 ocorre no POST e não satisfaz A0036 do mesmo golpe.
- Dependência **P-A0031-02 — RESOLVIDA:** Mastery anti-farm por discovery finita.
- A classificação Witherstein/boss específica permanece condicionada à evidência de `P-A0035-01`.

## Provider→árvore

Backlash, Shroud/Exposure, hazards Volcanoes e companions Mobstein não fornecem heavy receipt nem autoria MARTIAL. TERMINAL_EXTERIOR: MARTIAL/MAÇAS permanece preservado.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura MACE:** Pernach/arma Simply More só entra quando a família `MACE` é comprovada pelo Epic Fight Compat ou mapping versionado; o nome do tipo não é suficiente.
- **Preexistência RPG:** debuff/armor reduction provider-native não satisfaz a exigência de Armadura Fendida RPG já existente antes do root atual.
- **Heavy permanece ausente:** Implicit, Unique ability, debuff, gem power, stun, dano alto ou animação Simply/Simply More não é heavy receipt e não fecha `P-A0036-01`.
- **Sequencing:** a regra A0035→A0036 continua estrita; o mesmo golpe não pode criar `Sundered` e ativar Descompasso.
- **Alpha:** Unique/Implicit Simply More não comprovado permanece fail-closed; nenhuma inferência pelo namespace.
- **Notion:** quatro propriedades corrigidas e re-fetch PASS.

## Validação Chat 3 — PR #252

- `A0031A0040Chat3RegressionJUnitTest`: root cancelado não inicia cooldown e libera a reserva A0036.
- `A0031A0040ImplementationContractJUnitTest`: mesmo root que cria Sunder não ativa A0036; root subsequente pre-sundered prepara/commita cooldown corretamente.
- `RPG Skill Tree CI` #2806: JUnit 5, NeoForge GameTests, build e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #41: **GREEN**.
- Resultado: infraestrutura A0036 está validada, mas a perk permanece **NÃO CONFIRMADA / FAIL-CLOSED CORRETO** por `P-A0036-01`. Não declarar implementação operacional enquanto o provider não expuser receipt heavy inequívoco.

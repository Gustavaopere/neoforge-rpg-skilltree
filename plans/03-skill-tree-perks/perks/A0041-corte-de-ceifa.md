# A0041 — Corte de Ceifa

## Estado

- **Design:** APROVADO após correção transacional/provider-native.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA PELO CHAT 3**.
- **Notion:** `3c569db9-f0db-8108-86a7-dc58d3a93f63`.

## Contrato canônico

- A0038 ≥2 + A0039 ≥1 + gateway `combat_scythe`.
- Alvo deve possuir Marca da Ceifa Madura do mesmo jogador e estar em ≤50% da vida.
- Hit direto SCYTHE confirmado consome uma única Marca Madura e aplica +12%/+20% de dano físico e +15%/+25% de impacto quando esse componente existir.
- O classificador SCYTHE é único para A0037–A0042: provider-native ou mapping versionado explícito; unknown = fail-closed.
- O PRE pode **reservar** a operação para cálculo de modifier, mas o consumo irreversível da Marca ocorre somente no commit pós-hit com dano efetivo >0.

## Evidência runtime

- `A0041A0060CombatPolicy.scytheCut(...)` agora apenas valida e reserva a Marca Madura; não remove mais estado no PRE.
- `A0041A0060CombatState` mantém reserva exclusiva `actor + target + rootActionId`, impedindo dois roots concorrentes de reivindicarem a mesma Marca.
- `A0041ScytheCommitHooks` registra um consumer dedicado no `EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST`.
- dano modificado `> 0` consome a Marca reservada; dano zero descarta a reserva sem consumo.
- o commit não exige `target.isAlive()`, portanto hit letal confirmado continua sendo outcome válido.
- reserva possui retenção curta e limpeza por lifecycle/estado do ator.

## Provider→árvore

- **RPG Skill Tree:** authority da Marca, root action, reserva/commit e deduplicação.
- **Epic Fight 21.17.3.1:** PRE fornece modifier e POST fornece confirmação causal do dano.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não consome Marca nem ativa A0041.
- **Mobstein 5.4.4:** companion/bodyguard damage é Mobstein-owned; ataque direto do jogador contra entidade Mobstein pode qualificar normalmente.
- **Volcanoes / Enshrouded:** hazards/Shroud/Exposure não são hit SCYTHE.
- **Stage 11.01 itemização:** `SEM HOOK SEGURO`; rolls não projetam efeito MARTIAL nesta perk.

## Pendência Chat 3

- validar em integração real que PRE→POST mantém exatamente um modifier e um consume por root;
- validar zero/cancelado, hit letal, callback duplicado e dois roots concorrentes para o mesmo alvo;
- validar cleanup de reserva em lifecycle/multiplayer.

## Testes exigidos

- Marca madura vs imatura;
- target >50% vs ≤50%;
- hit confirmado vs zero/cancelado;
- reserva duplicada / uma Marca por root;
- impacto presente/ausente;
- companion/Backlash inelegíveis;
- lifecycle do target herdado de A0040.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura SCYTHE:** Scythe Simply só participa quando Epic Fight Compat resolve `SCYTHE` server-side.
- **Execute no mesmo root:** o execute Implicit pode coexistir com A0041 somente sob a causalidade do mesmo root direto; A0041 processa esse root uma vez e o RPG não rerrola/reaplica o execute.
- **Sem segundo consumo:** evento execute/ability/derived separado não constitui segundo Corte de Ceifa e não pode consumir outra Marca Madura.
- **Transação preservada:** reservation PRE e commit POST permanecem independentes do pipeline Simply.
- **Simply More/Cataclysm:** efeitos não comprovados/traits permanecem provider-owned e fail-closed para autoria A0041.
- **Notion:** quatro propriedades corrigidas; re-fetch PASS.

## Fechamento Chat 2 — 2026-09-01

`P-A0041-01` foi resolvida em código. O Chat 2 não executou a bateria final de GameTests/build/dedicated-server e não declara `IMPLEMENTAÇÃO CONFIRMADA`.

## Fechamento Chat 3 — 2026-09-02

- reservation→POST commit, zero/cancelado, causalidade e deduplicação foram revalidados contra o contrato aprovado.
- CI `RPG Skill Tree CI` #3378 (`33665545963`) passou JUnit 5, NeoForge JUnit, GameTests, build, JAR e dedicated-server smoke.
- **Estado final do lote:** `IMPLEMENTAÇÃO CONFIRMADA`.
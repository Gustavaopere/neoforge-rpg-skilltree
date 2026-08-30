# A0041 — Corte de Ceifa

## Estado

- **Design:** APROVADO após correção transacional/provider-native.
- **Implementação:** PARCIAL; `P-A0041-01` aberta e depende da classificação SCYTHE canônica do ramo.
- **Notion:** `3c569db9-f0db-8108-86a7-dc58d3a93f63`.

## Contrato canônico

- A0038 ≥2 + A0039 ≥1 + gateway `combat_scythe`.
- Alvo deve possuir Marca da Ceifa Madura do mesmo jogador e estar em ≤50% da vida.
- Hit direto SCYTHE confirmado consome uma única Marca Madura e aplica +12%/+20% de dano físico e +15%/+25% de impacto quando esse componente existir.
- O classificador SCYTHE é único para A0037–A0042: provider-native ou mapping versionado explícito; unknown = fail-closed.
- O PRE pode **reservar** a operação para cálculo de modifier, mas o consumo irreversível da Marca ocorre somente no commit pós-hit com dano efetivo >0.

## Evidência runtime

- `A0041A0060CombatPolicy.scytheCut(...)` valida Marca Madura, deduplica `A0041:consume`, mas hoje chama `legacy.consumeMatureReap(...)` ainda no PRE.
- `A0041A0060EpicFightHooks.onDamagePre(...)` calcula e anexa dano/impacto antes de existir confirmação de dano efetivo.
- `onDamagePost(...)` descarta hit com `modifiedDamage <= 0`, porém nesse momento a Marca já pode ter sido consumida.

## Provider→árvore

- **RPG Skill Tree:** authority da Marca, root action e deduplicação.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não consome Marca nem ativa A0041.
- **Mobstein 5.4.4:** companion/bodyguard damage é Mobstein-owned; ataque direto do jogador contra entidade Mobstein pode qualificar normalmente.
- **Volcanoes / Enshrouded:** hazards/Shroud/Exposure não são hit SCYTHE.
- **Stage 11.01 itemização:** `SEM HOOK SEGURO`; rolls não projetam efeito MARTIAL nesta perk.

## Pendência Chat 2

### P-A0041-01 — commit pós-hit da Marca

Implementar transação reservation→commit por `rootActionId`: reservar no PRE, aplicar modifiers uma vez, commit da Marca somente após hit confirmado e dano >0; cancelamento/dano zero libera reserva sem consumo. Testar callback duplicado e multiplayer.

## Testes exigidos

- Marca madura vs imatura;
- target >50% vs ≤50%;
- hit confirmado vs zero/cancelado;
- reserva duplicada / uma Marca por root;
- impacto presente/ausente;
- companion/Backlash inelegíveis;
- lifecycle do target herdado de A0040.

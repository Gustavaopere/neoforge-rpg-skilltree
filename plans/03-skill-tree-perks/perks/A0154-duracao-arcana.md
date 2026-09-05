# A0154 — Duração Arcana

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

Não foi provado hook público genérico que permita alterar com segurança a duração-base de todo efeito mágico temporário em Iron's/Ars.

## Contrato

- ARCANE; camada 3; Ramo; 4 ranks; 1 PP/rank.
- Pré-requisitos: A0147 ≥2 + Gateway ARCANE.
- +5% de duração/rank, máximo +20%.
- Somente efeitos mágicos temporários com `base_duration` real e mutável.
- Aplicação ocorre na criação ou renovação legítima, nunca sobre tempo restante já ampliado.

## Availability

Exige `SPELL_DURATION_ADAPTER_V1` para ao menos um provider+effect. Adapter é whitelist/versionado e precisa atuar no boundary de criação/renewal real. Sem canal completo: `UNAVAILABLE_NODE`.

## Pipeline futuro

`provider base_duration → A0154 multiplier uma vez → provider clamp/rules → effect instance`.

Refresh/reapply deve recomputar da base autoritativa, não da duração atual.

## Exclusões

- efeitos permanentes/infinite;
- fixed-duration que o provider não permite estender;
- cooldown, cast time, channel lifetime;
- alterar remaining time após criação;
- scheduler que prolonga artificialmente o efeito;
- crescimento cumulativo a cada refresh.

## Handoff Chat 2

Não implementar globalmente por `MobEffectInstance#getDuration`. Somente adapters aprovados podem declarar a base e a operação de renovação.

## Testes Chat 3

1. unavailable purchase atual;
2. ranks 0–4: 0/5/10/15/20%;
3. create e renewal usam base, sem crescimento cumulativo;
4. permanent/fixed/cooldown/cast/channel negativos;
5. provider mismatch fail-closed;
6. respec/reload e reapply idempotente.
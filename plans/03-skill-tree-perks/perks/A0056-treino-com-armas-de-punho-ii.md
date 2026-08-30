# A0056 — Treino com Armas de Punho II

## Estado

- **Design:** APROVADO; sem mutação no Notion nesta reauditoria.
- **Notion:** `3c569db9-f0db-81c2-a2ce-fe2a2fa8714c`.
- **Runtime:** caminho de attack speed presente; aquisição depende do fechamento de A0055.

## Contrato canônico

- A0055 ≥2 + gateway `combat_fist`.
- +2% de ritmo efetivo FIST por rank, máximo +6%.
- Usar apenas `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`ModifyAttackSpeedEvent` quando o moveset FIST/knuckle realmente consumir o valor server-authoritative.
- Sem contrato de cadência seguro, omitir o componente; não converter para Stamina, movimento, dano ou alteração direta de animação.

## Evidência runtime

`A0041A0060EpicFightHooks.onAttackSpeed(...)` aplica o bônus somente quando a capability Epic Fight é FIST/knuckle. O efeito está alinhado ao contrato. A árvore, porém, não pode ser considerada plenamente adquirível enquanto `combat:fist`/`combat_fist` de A0055 estiverem desalinhados no producer/architecture.

## Pendências para Chat 2

- Herdadas de A0055: `P-A0055-01` e `P-A0055-02`.
- Revalidar `ModifyAttackSpeedEvent` no moveset FIST real e dedicated-server/provider-present.

## Provider→árvore

Volcanoes, Enshrouded, Black Arcana, Mobstein e Punchy não fornecem cadência FIST.

## Notion

Fetch fresco sem drift; nenhuma mutação cosmética.

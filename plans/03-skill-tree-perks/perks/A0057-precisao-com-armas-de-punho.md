# A0057 — Precisão com Armas de Punho

## Estado

- **Design:** APROVADO após correção de classificação/provenance.
- **Notion:** `3c569db9-f0db-81e5-a161-e615da182f4e`.
- **Runtime:** resolver crítico FIST presente; aquisição depende do fechamento de A0055.

## Contrato canônico

- A0055 ≥1 + gateway `combat_fist`.
- +3% chance crítica FIST por rank, máximo +9%.
- Classificação FIST/knuckle provider-native/versionada e ataque direto do jogador.
- Uma única resolução crítica/root action; Apothic, se usado, integra o mesmo resolver.
- Sem categoria segura, fail-closed; `rpgskilltree:fist_weapons` não é fallback canônico.

## Evidência runtime

`A0041A0060EpicFightHooks.onCriticalHit(...)` e `rootAction(...)` usam categoria `fist`/`knuckle`, correlacionam crítico provider-native e chamam o serviço crítico canônico. O pipeline é tecnicamente coerente; a disponibilidade da linha depende da Mastery/gateway A0055.

## Pendências para Chat 2

- Herdadas de A0055: reconciliar `combat:fist` e `combat_fist`.
- Reforçar regressão de uma única rolagem e direct-player provenance.

## Boundaries

`ARCANE_BACKLASH`, dano de companion Mobstein, proc/follow-up derivado e callback visual Punchy são inelegíveis. Ataque FIST direto do jogador contra entidades dos próprios projetos continua universalmente elegível.

## Notion

Hook/Fallback/Regra corrigidos; re-fetch PASS.

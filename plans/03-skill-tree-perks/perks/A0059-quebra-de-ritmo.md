# A0059 — Quebra de Ritmo

## Estado

- **Design:** APROVADO; fail-closed já estava correto no Notion.
- **Notion:** `3c569db9-f0db-8105-9107-d95706ba3486`.
- **Runtime:** NÃO CONFIRMADO; ativação permanece fail-closed por falta de heavy/finalizer receipt.

## Contrato canônico

- A0058 ≥1 + A0056 ≥2 + gateway `combat_fist`.
- Com ≥3 Sequência, heavy/finalizer inequivocamente reconhecido pode consumir 3 cargas.
- Rank 1/2: +25%/+40% pressão de guarda e +10%/+15% Impact naquele golpe.
- Penalidade −8% movimento por 2 s somente após quebra de guarda/postura realmente confirmada.
- Sem heavy/finalizer seguro, não ativa nem consome.
- Se heavy existir mas guard/posture não, pode manter apenas Impact quando semanticamente disponível.

## Evidência runtime

`A0041A0060CombatPolicy.beforeFistHeavy(...)` implementa a matemática/consumo, mas `A0041A0060EpicFightHooks` declara explicitamente A0059/A0060 fail-closed e não chama a policy porque não há receipt server-authoritative inequívoco de heavy/finalizer para a ação concreta. Também não há caminho de aplicação da redução de movimento após guard break.

## Pendências para Chat 2

- **P-A0059-01:** integrar heavy/finalizer receipt provider-native seguro; não usar dano, animação, Punchy ou timing heurístico.
- **P-A0059-02:** quando houver ativação, integrar guard-break receipt real e aplicar −8% movimento por 2 s com lifecycle seguro; sem guard break, omitir essa parcela.
- Herdadas de A0055/A0058: gateway/Mastery FIST e reset por heavy impact.

## Boundaries

`ARCANE_BACKLASH`, hazard, companion-owned attack e estados Shroud/Arcane não qualificam heavy/finalizer, guard break ou Impact FIST.

## Notion

Fetch fresco sem drift; nenhuma mutação cosmética.

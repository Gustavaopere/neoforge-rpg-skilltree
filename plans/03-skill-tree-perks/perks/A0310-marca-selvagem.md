# A0310 — Marca Selvagem

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por dependency closure do Specialist Natureza.
- **Fonte canônica:** Notion `A0310` — https://app.notion.com/3c569db9f0db810ab1b1fd116ca0d6e5
- **Persistência:** fetch do registro em 2026-09-05; nenhum ajuste adicional necessário neste ciclo.

## Contrato aprovado

Enquanto o jogador estiver em uma forma natural válida e explicitamente classificada, A0310 concede **+4% de dano por rank** aos próprios outcomes ofensivos elegíveis: +4% / +8% / +12% nos ranks 1–3.

A parcela de traits é independente: traits da forma só recebem **+3% de eficiência por rank** quando um adapter versionado declarar a trait como `OFFENSIVE_SCALABLE` e expuser um parâmetro numérico seguro. Sem esse contrato, a parcela de trait é omitida; o bônus de dano não é substituído por outro efeito.

## Gate e dependency closure

Compra exige o unlock Specialist Natureza, A0183 e a rota interna prevista no catálogo: A0305 ≥1 **ou** A0306 ≥2, além de uma forma natural desbloqueada e ativa. A0183 continua `UNAVAILABLE_NODE` transitivamente por A0182; portanto A0310 não é comprável no snapshot atual e deve falhar **antes do gasto**.

Allocation legado indisponível vale 0 PP em gates/thresholds e permanece reembolsável/migrável.

## Providers, authority e boundaries

- **RPG Skill Tree:** owner do node, rank, gates, composição do dano e lifecycle do estado derivado.
- **Identity2 2.2.2 / Woodwalkers 5.8.13 e outros morph providers:** continuam authority da forma e de suas capacidades nativas. Um adapter pode publicar identidade da forma; isso **não** implica classificação `NATURAL`.
- `NATURAL_FORM_STATE_V1` precisa de mapping semântico data-driven/versionado. Tipo de entidade, namespace, aparência, VFX, tamanho ou stats-base não classificam a forma.
- Trait scaling só usa contrato explícito `OFFENSIVE_SCALABLE`; não refletir/instrumentar campos internos do provider.

## Causalidade, deduplicação e anti-abuso

Uma ação ofensiva recebe no máximo uma contribuição A0310. A contribuição acompanha a identidade canônica do outcome/root action; derived outcomes não reentram para nova contribuição. Mudança de forma, loss de unlock, respec, logout, reload ou troca de dimensão deve reconciliar/limpar o estado sem stacking.

## Fallback / fail-closed

- Sem Specialist Natureza/A0183: node inteiro indisponível.
- Com Specialist aberto no futuro, mas sem mapping seguro `NATURAL`: contribuição = 0.
- Sem adapter numérico de trait: omitir apenas a parcela da trait.
- Nunca inferir NATURAL por heurística nem converter ausência de adapter em bônus genérico.

## Testes obrigatórios para Chat 3

1. compra fail-before-spend enquanto A0183/Specialist Natureza estiver indisponível;
2. legacy unavailable = 0 PP e continua reembolsável/migrável;
3. forma explicitamente NATURAL aplica +4/+8/+12% exatamente uma vez por outcome elegível;
4. forma sem mapping/ambígua aplica 0;
5. trait só escala quando `OFFENSIVE_SCALABLE` expõe parâmetro numérico seguro;
6. ausência/version mismatch do morph adapter falha fechado;
7. derived outcome não recebe segunda contribuição;
8. troca de forma/respec/logout/reload/dimensão limpa/reconcilia estado idempotentemente;
9. nenhuma alteração de base stats/progressão nativa do morph provider;
10. multiplayer e dedicated server.

## Handoff Chat 2

Não redesenhar. Enquanto a closure A0183 persistir, implementar apenas availability/fail-closed que caiba na infraestrutura canônica. Não fabricar `NATURAL` por entity type/namespace e não tornar o node comprável como no-op.
# A0053 — Virote Perfurante

## Estado

- **Design:** APROVADO após correção de availability/provenance e reservation→commit.
- **Notion:** `3c569db9-f0db-811a-9656-f34ddd39f999`.
- **Runtime:** caminho de penetration presente; nó estruturalmente indisponível enquanto A0052 não puder ser adquirido e o consumo atual ainda ocorre cedo demais para lançamentos cancelados.

## Contrato canônico

- A0052 ≥1 + gateway `epic_crossbow`; availability de A0052 é obrigatória.
- Com 2 Cadências, disparo CROSSBOW totalmente carregado pode consumir 2 para +10%/+15% penetration e +15%/+25% impact.
- O custo segue **reservation→commit**: a tentativa pode reservar 2 Cadências, mas o commit só ocorre quando a criação do projectile/root é confirmada. Cancelamento tardio de `ArrowLooseEvent`, ausência de projectile spawn ou falha equivalente libera a reserva sem consumo.
- Componentes são independentes; aplicar apenas os semanticamente seguros.
- Primeiro impacto elegível do mesmo projectile/root recebe o efeito uma vez.
- Ricochetes, perfurações posteriores, derivados, dano periódico, Backlash ou projectile de companion não reaplicam.

## Evidência runtime

`tryPiercingBolt(...)` exige duas cargas e ao menos penetration/impact disponível, porém é chamado dentro de `onArrowLoose(...)` e já consome as Cadências antes de a criação do projétil ser confirmada. Como outro listener pode cancelar o `ArrowLooseEvent` depois do handler `HIGHEST`, é possível perder as duas Cadências sem qualquer projectile/root materializado. O bridge precisa reservar no lançamento e commit/rollback em função da criação efetiva do projétil.

O caminho de penetration em primeiro impacto existe; impact permanece fail-closed quando não há provider semântico seguro.

## Pendências para Chat 2

- **P-A0053-01:** propagar availability A0050→A0052→A0053 no catálogo/purchase path; não permitir rank no-op/bypass.
- **P-A0053-02:** transformar o consumo de 2 Cadências em reservation→commit ligado à criação confirmada do projectile/root; cancelamento tardio/ausência de spawn deve rollback integralmente.
- Revalidar first-impact/dedup no GameTest real, incluindo multi-pierce/ricochet/derivado e cancelamento de `ArrowLooseEvent` por listener posterior.
- Herdar os blockers de aquisição CROSSBOW de A0049/A0050/A0052; não considerar a perk alcançável até a cadeia inteira ser válida.

## Provider→árvore

Nenhum dos projetos próprios ou Mobstein fornece penetration/impact CROSSBOW alternativo. Stage 11 itemization continua authority separada e `SEM HOOK SEGURO` para projetar seus rolls nesta perk.

## Notion

Dependências, Gate, Hook, Fallback e Regra corrigidos no fechamento inicial. Após review da PR #249, `Hook`, `Fallback` e `Regra` passaram a exigir reservation→commit/rollback por criação real do projectile/root; re-fetch pós-review PASS em 2026-08-30.

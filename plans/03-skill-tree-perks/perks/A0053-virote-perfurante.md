# A0053 — Virote Perfurante

## Estado

- **Design:** APROVADO após correção de availability/provenance.
- **Notion:** `3c569db9-f0db-811a-9656-f34ddd39f999`.
- **Runtime:** caminho de penetration presente; nó estruturalmente indisponível enquanto A0052 não puder ser adquirido.

## Contrato canônico

- A0052 ≥1 + gateway `epic_crossbow`; availability de A0052 é obrigatória.
- Com 2 Cadências, disparo CROSSBOW totalmente carregado pode consumir 2 para +10%/+15% penetration e +15%/+25% impact.
- Componentes são independentes; aplicar apenas os semanticamente seguros.
- Primeiro impacto elegível do mesmo projectile/root recebe o efeito uma vez.
- Ricochetes, perfurações posteriores, derivados, dano periódico, Backlash ou projectile de companion não reaplicam.

## Evidência runtime

`tryPiercingBolt(...)` já exige duas cargas e ao menos penetration/impact disponível. O bridge atual cria o special shot no lançamento com penetration disponível e impact fail-closed; o impacto especial é reclamado uma vez pelo metadata do projétil.

## Pendências para Chat 2

- **P-A0053-01:** propagar availability A0050→A0052→A0053 no catálogo/purchase path; não permitir rank no-op/bypass.
- Revalidar first-impact/dedup no GameTest real, incluindo multi-pierce/ricochet/derivado.

## Provider→árvore

Nenhum dos projetos próprios ou Mobstein fornece penetration/impact CROSSBOW alternativo. Stage 11 itemization continua authority separada e `SEM HOOK SEGURO` para projetar seus rolls nesta perk.

## Notion

Dependências, Gate, Hook, Fallback e Regra corrigidos; re-fetch PASS.

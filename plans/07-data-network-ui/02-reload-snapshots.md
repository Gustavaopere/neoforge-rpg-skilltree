# Data/UI Plan — Reload and Snapshots

**Goal:** publicar dados recarregados de forma atômica.

- [ ] Construir snapshot novo fora da referência ativa.
- [ ] Validar tudo antes do swap.
- [ ] Manter snapshot antigo se o reload falhar conforme política escolhida.
- [ ] Recomputar efeitos/classes derivados que dependam dos dados novos.
- [ ] Evitar corrida entre reload e requests de compra.

**Acceptance:** nunca existe janela em que metade dos registries usa dados novos e metade usa dados antigos.
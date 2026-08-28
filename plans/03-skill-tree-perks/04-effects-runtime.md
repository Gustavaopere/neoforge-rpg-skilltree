# Skill Tree Plan — Effect Resolution

**Goal:** resolver efeitos inline, packs externos e comportamentos de forma determinística.

- [ ] Definir precedência/composição entre `bonuses`, `node_effects` e handlers comportamentais.
- [ ] Gerar IDs estáveis de modifiers por nó/rank/origem.
- [ ] Aplicar efeitos exatamente uma vez após login/reload/compra.
- [ ] Remover efeito quando requisito deixa de valer.
- [ ] Proteger efeitos de mods opcionais quando o provider estiver ausente.
- [ ] Testar operações flat, percent-base e multiply-total.

**Acceptance:** a mesma build sempre resolve o mesmo conjunto final de efeitos, sem acumulação.
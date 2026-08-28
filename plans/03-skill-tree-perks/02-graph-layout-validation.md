# Skill Tree Plan — Graph and Layout Validation

**Goal:** garantir que a malha 512/512 seja estruturalmente alcançável e coerente.

- [ ] Detectar ciclos inválidos e requisitos inalcançáveis.
- [ ] Validar roots, regiões, final triads, bridges e keystones.
- [ ] Confirmar orçamento `target_node_count = 512` e `actual_node_count = 512` enquanto esse contrato for canônico.
- [ ] Validar posições/links consumidos pela UI.
- [ ] Impedir node orphan sem intenção explícita.

**Acceptance:** o validator prova que o grafo carregado é navegável e que layout/dados não divergem.
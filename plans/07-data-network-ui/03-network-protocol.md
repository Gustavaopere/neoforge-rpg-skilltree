# Data/UI Plan — Network Protocol

**Goal:** manter packets pequenos, versionáveis e resistentes a input inválido.

- [ ] Definir packets client→server somente como intenção.
- [ ] Aplicar bounds em strings, listas, IDs, índices e quantidades.
- [ ] Validar jogador/contexto no servidor antes de qualquer mutação.
- [ ] Versionar payloads que possam mudar de schema.
- [ ] Evitar sync completo quando um delta/snapshot pequeno resolver.

**Acceptance:** packet malformado ou replay não altera progressão e não causa consumo descontrolado.
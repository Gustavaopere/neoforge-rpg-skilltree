# Foundation Plan — Client/Server Boundaries

**Goal:** impedir classloading client-only no dedicated server e manter a camada comum limpa.

- [ ] Isolar telas, keybinds, renderers e registro client.
- [ ] Revisar inicializadores estáticos comuns por referências a classes client-only.
- [ ] Garantir que packets comuns não importem UI/rendering.
- [ ] Cobrir login, datapack reload e bootstrap do servidor sem inicialização de cliente.
- [ ] Adicionar teste/smoke que falhe em referência client-only acidental.

**Acceptance:** dedicated server inicia sem carregar classes de cliente e o cliente mantém registro visual separado.
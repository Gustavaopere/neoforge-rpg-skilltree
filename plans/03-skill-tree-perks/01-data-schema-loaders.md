# Skill Tree Plan — Data Schema and Loaders

**Goal:** validar completamente os arquivos de árvore antes de publicar um snapshot utilizável.

- [ ] Validar ID, tree, custo, max rank, posição, requisitos e payload de efeito.
- [ ] Rejeitar IDs duplicados e referências inexistentes.
- [ ] Validar ranges e tipos de operações de atributo.
- [ ] Separar parse, validação e publicação do snapshot.
- [ ] Erros devem apontar arquivo, ID e campo problemático.

**Acceptance:** datapack inválido falha de forma explícita sem publicar estado parcial.
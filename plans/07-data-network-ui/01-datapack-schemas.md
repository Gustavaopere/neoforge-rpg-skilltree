# Data/UI Plan — Datapack Schemas

**Goal:** tornar todo conteúdo data-driven validável antes de entrar no jogo.

- [ ] Validar classes, skills, node effects, specializations e progression.
- [ ] Verificar referências cruzadas somente após todos os arquivos serem parseados.
- [ ] Incluir path, ID e campo em erros.
- [ ] Definir comportamento para campos desconhecidos e versões futuras.
- [ ] Manter validators executáveis no CI.

**Acceptance:** nenhum datapack inválido produz estado parcialmente utilizável ou erro opaco.
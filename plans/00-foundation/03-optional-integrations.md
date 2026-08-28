# Foundation Plan — Optional Integration Safety

**Goal:** permitir instalar o RPG Skill Tree sem transformar mods compatíveis em dependências duras acidentais.

- [ ] Centralizar detecção de mods/capabilities.
- [ ] Evitar tipos externos em assinaturas carregadas quando o mod estiver ausente.
- [ ] Isolar adapters por integração.
- [ ] Definir fallback neutro para ausência de cada provider.
- [ ] Validar que ausência de um mod não remove funcionalidade do core sem necessidade.

**Acceptance:** combinações suportadas iniciam sem `ClassNotFoundException`/`NoClassDefFoundError` e o core funciona sem cada integração opcional.
# Quest Hooks Plan — Public Query API

**Goal:** expor progresso do jogador para quests/addons sem permitir acesso direto ao storage interno.

- [ ] Consultar level, XP, pontos, perks, classes, masteries e especializações.
- [ ] Retornar snapshots/read-only views.
- [ ] Definir semântica para ID inexistente/provider ausente.
- [ ] Evitar expor detalhes de implementação do persistence layer.
- [ ] Versionar contrato público quando necessário.

**Acceptance:** addons conseguem verificar requisitos sem depender de classes internas mutáveis.
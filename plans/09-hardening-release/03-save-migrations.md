# Hardening Plan — Save and Upgrade Migrations

**Goal:** permitir atualizar o mod sem perder progressão silenciosamente.

- [ ] Versionar dados persistidos de jogador/mundo.
- [ ] Criar política de migração para IDs removidos/renomeados.
- [ ] Testar mundo novo e mundo de versão anterior.
- [ ] Validar chunk/entity data de scaling após upgrade.
- [ ] Falhar de forma explícita/recuperável quando migração não for possível.
- [ ] Documentar breaking changes no changelog.

**Acceptance:** save suportado abre na versão nova preservando progresso ou recebe erro explícito antes de corrupção.
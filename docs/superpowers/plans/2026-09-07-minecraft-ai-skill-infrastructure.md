# Minecraft AI Skill Infrastructure Implementation Plan

**Goal:** tornar as 20 skills fornecidas legíveis e seguras para uso consistente pelos chats do RPG Skill Tree.

**Spec:** `docs/superpowers/specs/2026-09-07-minecraft-ai-skill-infrastructure-design.md`

## Constraints

- Minecraft 1.21.1; NeoForge 21.1.x; Java 21.
- Não promover exemplo de versão/loader diferente sem prova.
- Registrar proveniência dos ZIPs.
- Uma etapa manual do usuário por vez.
- Nenhuma alteração de runtime Java nesta fase.

## Tasks

- [x] Auditar os 20 ZIPs e calcular SHA-256.
- [x] Classificar preferred / overlay-required / incomplete / disabled-by-default.
- [x] Criar router, authority e protocolo manual.
- [x] Criar 20 entrypoints auditados em `library/`.
- [x] Criar validador determinístico.
- [ ] Ligar a infraestrutura a `PROJECT-INSTRUCTIONS/README.md` e `AGENTS.md`.
- [ ] Revisar diff contra a `main` mais recente.
- [ ] Abrir PR, rodar checks aplicáveis, sincronizar de novo com `main`, revalidar e mergear.

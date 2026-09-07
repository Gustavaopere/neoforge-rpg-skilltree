---
name: minecraft-neoforge-engineering
source: user-supplied minecraft-neoforge-engineering.zip
project_status: preferred
---
# Minecraft NeoForge Engineering — projeto

Use para design, implementação, continuação, debug, review, testes, integrações, hardening e release de mods Minecraft **1.21.1 / NeoForge 21.1.x / Java 21**.

## Regras obrigatórias

- Precisão e evidência acima de velocidade.
- Confirme APIs version-sensitive antes de usar.
- Provider-native first; bridge comprovada depois; integração própria apenas quando necessária.
- Cliente nunca é autoridade de gameplay.
- Classe client-only não pode vazar para dedicated server.
- Integração opcional deve sobreviver à ausência/incompatibilidade do provider.
- Para bug: reproduzir/characterizar → regressão quando prática → corrigir → validar.
- Compile-only não prova gameplay.
- GitHub: sincronize com `origin/main` antes de editar, antes do handoff e imediatamente antes do merge; revalide após a última sincronização.

Leia `../../VERSION-AUTHORITY.md` e o `AGENTS.md` do repositório antes de decisões arquiteturais.

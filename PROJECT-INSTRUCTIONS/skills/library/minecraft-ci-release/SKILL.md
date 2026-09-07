---
name: minecraft-ci-release
source: user-supplied minecraft-ci-release.zip
project_status: overlay-required
---
# Minecraft CI / Release — overlay 1.21.1

Use para CI, artifacts, versionamento e release governance. O ZIP original contém exemplos para outros loaders/versões; adapte apenas depois de ler os workflows e Gradle reais do repositório.

CI deve validar o que o projeto realmente exige, sem desligar checks para obter verde. Antes de merge, sincronize com `main` e reexecute validações aplicáveis no HEAD reconciliado. Tokens/secrets nunca entram no repositório.

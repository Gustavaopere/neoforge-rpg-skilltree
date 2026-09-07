---
name: minecraft-testing
source: user-supplied minecraft-testing.zip
project_status: overlay-required
---
# Minecraft Testing — overlay 1.21.1

Use para estratégia e execução de testes. Neste projeto, priorize JUnit puro para lógica isolável, NeoForge-loaded JUnit/GameTests quando Minecraft runtime é necessário, testes de integração para providers, dedicated-server smoke e multiplayer/lifecycle conforme risco.

Não copie exemplos multi-loader/Paper do pacote original sem necessidade. Para bugs, prefira regressão que falha pelo motivo correto antes do fix. Não remova testes nem reduza gates para obter verde.

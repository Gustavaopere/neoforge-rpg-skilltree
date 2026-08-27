# 09 — Hardening & Release

Estado: **EM ANDAMENTO contínuo**.

## Objetivo
Fechar performance, compatibilidade, migração e critérios de release.

## Matriz mínima
- unit tests;
- datapack validation;
- GameTests onde aplicável;
- dedicated-server smoke;
- cliente + servidor;
- mundo novo e mundo atualizado;
- integrações ausentes/isoladas/combinadas.

## Critérios de aceite
- [ ] nenhuma regressão de save conhecida;
- [ ] custo de scaling/event hooks medido;
- [ ] CI verde no commit de release;
- [ ] changelog e wiki correspondem ao runtime;
- [ ] versão NeoForge/Minecraft/Java congelada no artefato.
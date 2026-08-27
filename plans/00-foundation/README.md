# 00 — Foundation

Estado: **EM ANDAMENTO / base existente**.

## Objetivo
Manter um núcleo NeoForge 1.21.1/Java 21 estável, testável e seguro para dedicated server.

## Escopo
- bootstrap/mod lifecycle;
- registries e configuração;
- separação client/server;
- infraestrutura de testes;
- logging e diagnóstico;
- contratos opcionais de integração.

## Critérios de aceite
- [ ] `test` verde.
- [ ] build verde.
- [ ] dedicated-server smoke sem carga acidental de classes client-only.
- [ ] ausência de mods opcionais não derruba o mod.
- [ ] documentação de versão/dependências acompanha o build real.
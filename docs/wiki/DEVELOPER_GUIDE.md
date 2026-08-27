# Guia de desenvolvimento

## Ambiente

- Minecraft 1.21.1
- NeoForge
- Java 21

## Antes de alterar gameplay

1. Leia `plans/README.md`, `plans/STATUS.md`, `plans/DECISIONS.md` e o estágio relevante.
2. Localize o serviço canônico em `src/main/java/dev/gustavopere/rpgskilltree`.
3. Verifique se o comportamento já é data-driven antes de hardcodar.
4. Se for integração, prove qual evento representa conclusão real da ação.

## Ao adicionar uma perk

- crie ID estável;
- use efeito conhecido/validado;
- declare requisitos sem ciclos;
- mantenha respec reversível;
- teste save/reload;
- atualize ou regenere o catálogo.

## Ao adicionar integração

- gate por presença do mod;
- isole imports/classes externas;
- converta o evento externo para uma operação canônica do RPG;
- evite double-counting;
- teste com e sem o mod;
- documente apenas o comportamento comprovado.

## Validação

```bash
./gradlew test
./gradlew runGameTestServer
./gradlew build
```

A release só deve avançar com CI verde e smoke de dedicated server quando disponível.
# 10.04 — Descoberta, estudo, progressão e recompensas

## Objetivo

Fazer o Compêndio crescer por ações reais no mundo, usando autoridade do servidor e idempotência já adotadas pelo RPG Skill Tree. A descoberta deve ser satisfatória, mas não obrigar o jogador a matar tudo que existe.

## Arquivos previstos

Criar/alterar principalmente:

- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaDiscoveryKeyPolicy.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/EncyclopediaDiscoveryService.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/events/EncyclopediaDiscoveryEvents.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java`
- `src/test/java/dev/gustavopere/rpgskilltree/core/EncyclopediaDiscoveryServiceTest.java`
- testes runtime específicos para bridges de evento quando aplicável.

## Reutilização obrigatória

`DiscoveryProgress` já persiste `Set<String>` de chaves descobertas e `PlayerProgressionRuntime.creditDiscovery(...)` já garante crédito idempotente no caminho canônico. O Compêndio deve usar esse contrato enquanto ele for suficiente.

Formato sugerido, estável e versionável:

```text
encyclopedia:<entryId>:discovered
encyclopedia:<entryId>:studied
```

A função que constrói essas chaves deve ser única; não concatenar strings em dezenas de event handlers.

## Métodos de descoberta

Cada entrada declara um ou mais métodos válidos. O runtime só registra os métodos implementados genericamente ou por adapter seguro.

### Observação de criatura

- jogador suficientemente próximo;
- target vivo e rastreável;
- line-of-sight quando tecnicamente viável;
- sampling com intervalo/budget, nunca scan integral de todas as entidades a cada tick.

### Luneta

Observação com spyglass pode conceder `STUDIED` ou acelerar a descoberta. O servidor deve confirmar target/raycast; o cliente não informa livremente qual entity “viu”.

### Interação

Taming, feeding, milking, shearing, mounting ou interação genérica só contam quando o evento semântico correspondente realmente ocorreu. Não conceder estudo apenas por clicar e falhar.

### Combate

- primeiro kill pode descobrir hostis;
- boss defeat deve reutilizar o pipeline de boss/progression quando possível;
- criaturas pacíficas não podem exigir kill como única rota.

### Flora e recursos

Descoberta pode vir de:

- observar/inspecionar bloco;
- colher/quebrar com ação válida;
- obter item representativo pela primeira vez quando a relação block -> entry estiver definida.

Evitar recompensar automação/máquina como se fosse observação pessoal sem design explícito.

### Biomas e dimensões

Reutilizar `ExplorationProgressionEvents`, que já credita `biome:<id>` e `dimension:<id>`. Criar mapping para entradas do Compêndio sem conceder XP duas vezes pelo mesmo fato semântico.

### Estruturas

Preferir detecção server-side por structure manager/holder e localização do jogador dentro da estrutura. Não fazer busca radial cara por tick. Sampling e cache por chunk/jogador devem respeitar budget.

## Dois níveis de conhecimento

`DISCOVERED` libera resumo e identificação. `STUDIED` pode liberar dados avançados quando a entrada declarar essa camada.

Exemplos de estudo:

- observar com luneta por janela mínima;
- interagir com sucesso;
- derrotar/ser derrotado por ameaça especial, conforme design;
- visitar estrutura de forma confirmada;
- coletar amostra/loot representativo.

Não exigir `STUDIED` para todas as entradas. O schema define quais seções são gated.

## Recompensas

O Compêndio pode conceder XP de personagem/progressão somente quando:

- a entrada define explicitamente reward;
- é a primeira transição de estado correspondente;
- a mesma descoberta não já concedeu reward equivalente por `ExplorationProgressionEvents` ou outro pipeline.

Criar política central de reward, com caps/valores data-driven ou concentrados em `GameplayXpPolicy`; não espalhar números nos event handlers.

## Notificação

Ao descobrir/estudar:

- sincronizar estado;
- emitir notificação client-side localizada e não invasiva;
- permitir clicar/abrir diretamente a entrada quando a UX final suportar isso;
- não enviar spam para eventos repetidos.

## Testes

- primeira descoberta retorna `firstDiscovery=true`; repetição não altera XP;
- `studied` não pode existir sem `discovered` após reconcile;
- kill de criatura pacífica não é requisito único quando outra regra foi declarada;
- target inválido/não catalogado não cria chave órfã;
- biome/dimension bridge não duplica reward já emitido pelo sistema existente;
- sampling de entidades/estruturas respeita intervalo/budget determinístico em testes puros;
- payload do cliente não consegue forjar descoberta arbitrária.

## Acceptance

- [ ] Métodos de descoberta são server-authoritative e configurados por entrada.
- [ ] `DiscoveryProgress` é reutilizado sem segundo silo de progresso.
- [ ] `DISCOVERED`/`STUDIED` têm transições idempotentes.
- [ ] Biomas/dimensões existentes são reconciliados sem reward duplicado.
- [ ] Estruturas e observação não criam scan caro por tick.
- [ ] Testes cobrem repetição, forgery e regras por categoria.

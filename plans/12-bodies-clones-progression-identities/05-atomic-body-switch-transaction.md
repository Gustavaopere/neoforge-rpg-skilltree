# 12.05 — Transação atômica de troca de corpo

## Objetivo

Implementar a troca como uma transação server-authoritative com rollback. Este é o ponto de maior risco do Stage 12: uma falha no meio não pode misturar dois personagens nem duplicar itens.

## Pipeline obrigatório

```text
request target body
↓
validate owner + target + anchor
↓
acquire owner switch lock
↓
create transaction journal
↓
freeze mutable interaction state
↓
capture source body
↓
validate source snapshot
↓
persist source + lastKnownGood
↓
unproject source runtime
↓
load + validate target snapshot
↓
apply target vanilla/provider/RPG state
↓
rebuild derived runtime
↓
set activeBodyId
↓
sync client
↓
refresh local scaling context
↓
commit journal
↓
release lock
```

## Validações antes de tocar no player

- corpo alvo pertence ao owner;
- corpo alvo está `READY` ou `STORED`;
- corpo alvo não é o corpo já ativo;
- anchor/máquina/ritual ainda é válido;
- custo foi reservado quando aplicável;
- nenhum outro switch está em andamento;
- corpo fonte está consistente;
- providers obrigatórios estão disponíveis.

## Lock

Um `BodySwitchLock` por owner impede:

- dois cliques simultâneos;
- request de network duplicada;
- respawn concorrendo com troca manual;
- logout/login no meio de uma segunda operação;
- container/máquina iniciar outra mutação do mesmo perfil.

## Ordem de captura/aplicação

A ordem deve ser explícita para evitar que atributos de um corpo contaminem o outro:

1. inventory/equipment capture;
2. external providers capture;
3. RPG source capture;
4. remover runtime derivado;
5. aplicar vanilla target;
6. aplicar provider target;
7. aplicar RPG target;
8. recomputar runtime derivado;
9. network/UI.

A ordem definitiva deve ser validada por testes de integração; providers podem declarar dependências.

## Rollback

Qualquer exceção antes de `COMMITTED`:

- não consumir permanentemente custo ainda reservado;
- restaurar snapshot fonte `lastKnownGood`;
- restaurar `activeBodyId` fonte;
- reconstruir runtime fonte;
- registrar diagnóstico com transaction ID;
- mostrar mensagem PT-BR sem stacktrace ao jogador.

Se rollback automático também falhar, bloquear novas trocas e marcar `RECOVERY_REQUIRED`.

## Duplicação

Nunca usar semântica "copy source inventory into target" durante switch. O inventário ativo é parte do source snapshot; o target recebe somente seu próprio inventário salvo.

## Logout/crash

O journal deve permitir determinar no próximo login se a transação estava:

- antes do source persistido;
- source persistido, target não aplicado;
- target aplicado, commit não escrito.

A recovery policy deve preferir consistência a conveniência.

## Critérios de aceite

- switch normal é atômico;
- pacote duplicado não duplica corpo ou item;
- crash simulado em cada fase recupera estado determinístico;
- falha de provider executa rollback;
- source e target nunca ficam ambos `ACTIVE`;
- custo só é consumido após commit conforme política;
- dedicated server não depende de tela cliente para completar a transação.
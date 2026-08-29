# 12.01 — Domínio, invariantes e ownership

## Objetivo

Definir formalmente o que é conta, corpo, identidade ativa e jornada de progressão antes de alterar qualquer storage do jogador.

## Modelo de ownership

- `ownerUuid`: UUID real da conta Minecraft. Nunca muda ao trocar de corpo.
- `bodyId`: UUID estável de uma instância corporal.
- `activeBodyId`: corpo atualmente controlado pelo owner.
- `BodyRegistry`: catálogo server-side dos corpos pertencentes ao owner.
- `BodyProfile`: snapshot versionado do estado local daquele corpo.

Nunca substituir o UUID real do `ServerPlayer` pelo `bodyId`. Integrações externas normalmente identificam o jogador pelo UUID da conta; falsificar esse UUID quebraria permissões, claims, teams, advancements e inúmeros mods.

## Estados permitidos de um corpo

```text
CONSTRUCTING
READY
ACTIVE
STORED
DESTROYED
RECOVERY_REQUIRED
```

Regras:

1. no máximo um `ACTIVE` por owner;
2. `CONSTRUCTING` não pode ser ativado;
3. `DESTROYED` é tombstone persistente até política de limpeza/migração;
4. `RECOVERY_REQUIRED` bloqueia ativação automática e exige validação/rollback;
5. transições inválidas devem falhar antes de tocar no player ativo.

## Corpo original

No primeiro uso do sistema, o estado atual do jogador vira o **Corpo Original**:

- recebe `bodyId`;
- preserva toda progressão existente;
- é marcado inicialmente `ACTIVE`;
- não há reset do save atual;
- nenhum sistema deve exigir que o jogador recrie sua progressão pré-existente.

## Corpo novo

Por padrão, um corpo recém-construído:

- pertence ao mesmo `ownerUuid`;
- começa no nível RPG inicial definido pelo Stage 01/02;
- possui XP RPG inicial;
- tem 0 pontos gastos;
- não herda perks/classes/masteries/especializações corporais;
- recebe atributos base normais de um personagem novo;
- não herda inventário corporal;
- mantém somente estados classificados explicitamente como globais da conta.

## Corpo não é personagem multiplayer

O sistema não deve criar fake players, contas offline paralelas ou UUIDs de login artificiais. Corpos são perfis controlados pelo mesmo `ServerPlayer`.

## Invariantes de segurança

- toda mutação deve acontecer no thread do servidor;
- body ownership deve ser validado em toda request de troca;
- clientes nunca podem escolher arbitrary NBT/bodyId não pertencente ao player;
- pacotes devem carregar IDs, não snapshots autoritativos enviados pelo cliente;
- nenhum corpo pode ficar ativo em duas localizações simultâneas;
- criação de corpo não duplica conteúdo do corpo fonte salvo decisão explícita de design;
- switching não deve disparar recompensas de primeiro-login/first-level de forma duplicada.

## Contratos públicos previstos

Criar interfaces estáveis, sem expor implementação de NBT:

```text
BodyService
BodyRegistry
BodyProfile
BodyId
BodyState
ActiveBodyResolver
BodyLifecycleEvent
```

Operações mínimas:

```text
getActiveBody(player)
listBodies(player)
createBody(owner, creationContext)
storeActiveBody(player, anchor)
switchBody(player, targetBodyId, anchor)
destroyBody(owner, bodyId, reason)
validateBody(owner, bodyId)
```

## Critérios de aceite

- owner UUID permanece constante durante qualquer troca;
- dois corpos do mesmo jogador possuem `bodyId` distintos;
- apenas um corpo pode estar `ACTIVE`;
- corpo novo é realmente uma jornada nova;
- corpo original pode ser registrado sem perder estado existente;
- state machine rejeita transições impossíveis;
- API não depende de classes client-only.
# 12.02 — BodyProfile, persistência e versionamento

## Objetivo

Criar o snapshot autoritativo e versionado de cada corpo sem serializar o `ServerPlayer` inteiro de forma cega.

## Estrutura conceitual

```text
BodyProfile
├── schemaVersion
├── bodyId
├── ownerUuid
├── displayName
├── state
├── createdAt
├── lastActivatedAt
├── creationKind
├── locationSnapshot
├── vanillaState
├── rpgState
└── providerStates{}
```

## RPG state mínimo

Persistir por corpo:

- nível RPG;
- XP RPG e progresso para próximo nível;
- pontos disponíveis/gastos;
- alocações da árvore e ranks de perks;
- atributos adquiridos e fontes corporais;
- classes detectadas/desbloqueadas quando forem estado persistente;
- masteries e XP de mastery;
- especializações;
- cooldowns/recursos cuja semântica exija persistência;
- flags de migração e versão.

Não persistir caches derivados. Eles devem ser reconstruídos ao ativar o corpo.

## Vanilla state corporal

Por padrão incluir:

- inventário principal;
- armor/offhand;
- vida atual;
- fome, saturação e exaustão;
- vanilla XP/level;
- posição/dimensão de armazenamento quando aplicável;
- efeitos persistentes somente conforme política definida em 12.03.

## Storage

Preferir storage server-side explícito e versionado (`SavedData`/attachment apropriado para NeoForge 1.21.1), com índice por owner e `bodyId`.

Não depender de editar diretamente o arquivo `playerdata/<uuid>.dat` offline como mecanismo normal.

## Inspiração NeoSync

`NeoSync ShellState` já demonstra persistência de UUID, owner, inventário, vida, fome, vanilla XP, dimensão e posição. O RPG pode reutilizar/adaptar esse desenho, mas deve acrescentar `BodyProfile` e providers corporais em vez de tratar o shell apenas como cópia física.

O conceito `ShellStateComponent` é especialmente útil: cada integração registra um provider capaz de capturar/aplicar seu estado sem o core conhecer NBT privado de todos os mods.

## Atomicidade e backup

Cada corpo deve ter:

- snapshot corrente;
- revision monotônica;
- checksum/validação estrutural quando viável;
- `lastKnownGood` para recovery de troca interrompida;
- journal mínimo da transação em andamento.

Salvar corpo fonte antes de aplicar corpo destino.

## Versionamento

Toda estrutura recebe `schemaVersion`.

Migrações devem ser sequenciais e idempotentes:

```text
v1 -> v2 -> v3
```

Nunca interpretar silenciosamente campos desconhecidos como defaults se isso puder apagar progressão.

## Recovery

Ao carregar mundo:

- detectar `ACTIVE` duplicado;
- detectar transaction journal incompleto;
- validar owner/body links;
- restaurar último snapshot consistente quando possível;
- marcar `RECOVERY_REQUIRED` quando não for seguro decidir automaticamente;
- nunca apagar corpo corrompido para "resolver" startup.

## Critérios de aceite

- save/reload preserva todos os corpos;
- corpo armazenado não depende do player estar online;
- snapshots são versionados;
- dados desconhecidos de providers não são descartados silenciosamente;
- falha de provider não corrompe o core;
- lastKnownGood permite recuperação de transação interrompida.
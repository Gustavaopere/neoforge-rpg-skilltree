# 11.03 — Identidade, persistência e versionamento do item

## Objetivo

Criar a representação persistente única de cada equipamento itemizado e provar que ela sobrevive ao lifecycle completo sem reroll.

## Passo a passo

### A — Estado canônico

Definir componente/estado equivalente compatível com NeoForge 1.21.1 contendo, no mínimo:

```text
schemaVersion
instanceId
deterministicSeed
rank
itemPower
prefixes[]
suffixes[]
infixes[]
generationSource
generationContextVersion
```

Não persistir texto localizado nem cache de atributos derivados.

### B — Codec e sync

- [ ] codec/stream codec versionados conforme o boundary escolhido;
- [ ] round-trip sem perda;
- [ ] cópia de `ItemStack` preserva dados quando a operação representa o mesmo item;
- [ ] networking transmite apenas o necessário ao cliente;
- [ ] cliente não possui mutation authority.

### C — Idempotência

Criar operação conceitual `ensureItemized(stack, context)`:

- se não elegível: não altera;
- se já itemizado: retorna a identidade existente;
- se elegível e não itemizado: gera uma vez e persiste;
- se dados inválidos/versionamento desconhecido: fail-closed + diagnóstico, sem reroll silencioso.

### D — Duplicação e stack semantics

Equipamentos normalmente têm stack size 1, mas o contrato deve definir cópia/clonagem administrativa, crafting containers e mods que duplicam components. `instanceId` não pode ser tratado como garantia de exclusividade mundial sem política explícita para cópias reais.

### E — Lifecycle obrigatório

Provar preservação em:

- save/load;
- logout/login;
- mudança de dimensão;
- drop/pickup;
- container/hopper quando aplicável;
- mob pickup/equip/drop;
- morte do jogador;
- reparo;
- reload de datapack.

## Testes previstos

JUnit para codec/versionamento e GameTests para lifecycle real. Comparar identidade inteira, não apenas rank.

## Acceptance

O subplano fecha quando um item recebe identidade uma única vez, o snapshot persistido é a fonte de verdade e nenhuma transição suportada consegue rerrolar ou perder seus dados.

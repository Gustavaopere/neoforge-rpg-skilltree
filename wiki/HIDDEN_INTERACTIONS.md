# Interações ocultas e comportamentos não óbvios

> **Snapshot:** `main`, 28/08/2026. Só entram comportamentos sustentados pelo código atual.

## Goety: sua build altera a economia de Soul Energy

`GoetySoulPolicy` modifica ganho e custo de Soul Energy.

### Ganho

| Condição | Bônus |
| --- | ---: |
| `rpgskilltree:occult_000` | +5% |
| `rpgskilltree:occult_001` | +5% |
| `warlock` desbloqueado | +10% |
| `necromancer` desbloqueado | +5% |

O bônus total é limitado a **25%** e usa `floor`, sem reduzir o valor original.

### Custo de feitiços

| Condição | Desconto |
| --- | ---: |
| `occult_000` | 5% |
| `occult_001` | 5% |
| `warlock` | 10% |
| `necromancer` + spell tag `summoning` | 10% |

O desconto total é limitado a **30%**. Custo positivo nunca cai abaixo de 1 e é arredondado para cima.

**Detalhe:** Necromancer só recebe seu desconto extra quando o feitiço Goety é reconhecido como `summoning`; Warlock recebe seu desconto de classe em qualquer spell Goety reconhecida.

Fonte: `core/GoetySoulPolicy.java`.

## Goety: tentar castar não basta para mastery

Eventos de cast apenas criam um candidato temporário. A mastery só é persistida quando `ChangeSoulEnergyEvent.Loss` confirma gasto real em até **1 tick**.

Um cast confirmado concede atualmente:

- `occult:practice`: 2;
- `goety:casting`: `2 + intensidade`;
- `goety:soul_spending`: `1 + intensidade`;
- `goety:<disciplina>` para tags reconhecidas.

A intensidade vai de 1 a 4 conforme o custo confirmado.

Fonte: `runtime/compat/goety/GoetyProgressionEvents.java` e `core/MasteryPolicies.java`.

## Goety: comandos de servos só contam após mudança real

`CommandFocus` e `OrderFocus` criam intenção; `PlayerTickEvent.Post` confirma o estado efetivo do servo em até **2 ticks**.

Regras verificadas:

- servo precisa estar vivo e ser comandável;
- o verdadeiro dono precisa ser o jogador;
- distância máxima ao criar a intenção: **64 blocos**;
- repetir exatamente o comando já ativo é filtrado;
- comando em entidade e bloco são diferenciados;
- `OrderFocus` pode confirmar vários servos.

Awards:

- `goety:commanding`: `2 + min(3, servos confirmados)`;
- `summoning:practice`: `1 + min(2, servos confirmados)`.

Logo, ordens de grupo não escalam XP indefinidamente.

## Goety: servant kills distinguem Warlock e Necromancer

Quando uma entidade `IOwned` mata um `MONSTER`, o dono pode receber mastery. A intensidade usa a vida máxima da vítima, limitada de 1 a 4.

Awards básicos:

- `goety:servants`: `2 + intensidade`;
- `summoning:practice`: 2.

Bônus:

- Necromancer: `goety:necromancy = 3 + intensidade`;
- Warlock: `goety:pact_servants = 2 + intensidade`.

## Eidolon: primeira conclusão vale mais

Ritual confirmado (`confirmed_ritual`):

| Lane | Primeira | Repetição |
| --- | ---: | ---: |
| `eidolon:ritual` | 8 | 3 |
| `occult:practice` | 4 | 2 |
| `summoning:practice`, se summoning | 4 | 2 |
| `healing:practice`, se holy | 3 | 1 |

Alquimia confirmada:

- `eidolon:alchemy`: 8 na primeira conclusão, 3 depois;
- `occult:practice`: 3 na primeira, 1 depois.

Fonte: `MasteryPolicies.forEidolonRitual/forEidolonAlchemy`.

## Iron's e Ars: custo aumenta mastery, com teto

Ambos derivam intensidade de 1 a 5 em degraus de recurso. Custos extremos não aumentam indefinidamente a mastery.

Iron's alimenta `magic:casting`, `irons:casting` e a disciplina. Ars alimenta `magic:casting`, `ars:casting` e lanes como projectile, amplification, aoe, duration, summoning e control quando as tags correspondem.

## Epic Fight: dodge também desenvolve Agility

`dodge_success` concede simultaneamente:

- `epicfight:practice`: 2;
- `epicfight:dodge`: 6;
- `agility:practice`: 3.

## Malum: um reaping pode desenvolver várias lanes

Uma prática pode alimentar `malum:spirit_arcana`; `reaping` adiciona `occult:practice` e `malum:reaping`; `collection` adiciona `malum:collection`; tags `spirit:<afinidade>` adicionam `malum:spirit/<afinidade>`.

## Anti-recursão de mastery

As policies verificadas retornam nenhum award quando `ActionOrigin.procDepth() > 0`. Isso reduz cadeias recursivas em que um proc derivado seria contado novamente como prática primária.

A regra aparece nas policies de Iron's, Ars, Goety, servants, commands, Malum, Eidolon, Create e Epic Fight.

## Nomes tecnológicos não provam bônus de máquina

Caminhos de Tecnomago podem mencionar Create, AE2 e Oritech. A identidade existe, mas um nome não significa automaticamente RPM, stress, FE, crafting speed ou throughput adicional. Só se documenta bônus de máquina quando houver hook/attribute/policy concreto.

## Fontes principais

- `src/main/java/dev/gustavopere/rpgskilltree/core/GoetySoulPolicy.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/MasteryPolicies.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/goety/GoetyProgressionEvents.java`

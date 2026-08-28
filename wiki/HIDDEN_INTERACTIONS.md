# Interações ocultas e comportamentos não óbvios

> **Snapshot documentado:** `main` em 28/08/2026. Aqui entram apenas interações sustentadas pelo código atual. “Oculto” significa pouco evidente para o jogador, não conteúdo secreto inventado.

Esta página é o lugar para combinações que podem passar despercebidas em tooltips, nomes de perks ou descrições de classe.

## Goety — Soul Energy é alterada pela sua build

A integração com Goety não serve apenas para gerar mastery. Ela altera a própria economia de Soul Energy.

### Ganho de Soul Energy

`GoetySoulPolicy.adjustedGain` soma os seguintes bônus:

| Condição | Bônus ao ganho |
| --- | ---: |
| `rpgskilltree:occult_000` aprendido | +5% |
| `rpgskilltree:occult_001` aprendido | +5% |
| classe `warlock` desbloqueada | +10% |
| classe `necromancer` desbloqueada | +5% |

O bônus acumulado é limitado a **25%**. O resultado usa `floor`, mas nunca reduz o ganho original.

Exemplo: alguém com os dois nós Occult e Warlock atinge +20% de ganho; adicionar Necromancer leva o total ao teto de +25%.

### Custo de feitiços Goety

A mesma policy reduz o custo confirmado de Soul Energy:

| Condição | Desconto |
| --- | ---: |
| `occult_000` | 5% |
| `occult_001` | 5% |
| `warlock` | 10% |
| `necromancer` + feitiço com tag `summoning` | 10% |

O desconto total é limitado a **30%**. O custo é arredondado para cima e nunca cai abaixo de **1** quando o custo original é positivo.

Isso cria uma sinergia específica: **Necromancer só recebe seu desconto extra em feitiços Goety classificados como summoning**, enquanto Warlock recebe o desconto de classe em qualquer feitiço Goety reconhecido.

**Fonte:** `core/GoetySoulPolicy.java` e `runtime/compat/goety/GoetyProgressionEvents.java`.

## Goety — tentar lançar um feitiço não basta para ganhar mastery

Os callbacks de cast (`CastMagicEvent`, `CastingMagicEvent`, `TouchMagicEvent` e `BlockMagicEvent`) apenas criam um candidato temporário. A mastery só é concedida quando Goety confirma um gasto real por `ChangeSoulEnergyEvent.Loss`.

O candidato precisa ser confirmado em até **1 tick**. Se não houver confirmação, não há award.

Consequências:

- clicar/acionar sem produzir gasto não deve gerar mastery;
- o sistema reduz farming por tentativas inválidas;
- a intensidade da mastery usa o custo de Soul Energy já ajustado pela build.

Para um cast Goety confirmado, a policy atual concede:

- `occult:practice`: 2 XP;
- `goety:casting`: `2 + intensidade`;
- `goety:soul_spending`: `1 + intensidade`;
- uma lane específica `goety:<disciplina>` quando a tag reconhecida estiver entre necromancy, nether, ill, frost, geomancy, wind, storm, abyss, wild, void ou summoning.

A intensidade é de 1 a 4, derivada do custo confirmado em degraus de aproximadamente 50 Soul Energy.

**Fonte:** `GoetyProgressionEvents.onSoulSpent` e `MasteryPolicies.forGoety`.

## Goety — ordens de servos só contam depois de uma mudança real

Usar `CommandFocus` ou `OrderFocus` gera apenas uma intenção inicial. O RPG verifica depois, em `PlayerTickEvent.Post`, se o estado de comando do servo realmente mudou.

A confirmação pode ocorrer em até **2 ticks**. Sem mudança confirmada, não há mastery.

Outras regras pouco visíveis:

- só servos vivos e comandáveis contam;
- o verdadeiro dono precisa ser o jogador;
- o servo precisa estar a no máximo **64 blocos** do jogador quando a intenção é criada;
- repetir exatamente o mesmo comando já ativo é filtrado;
- comandos em entidade e em bloco são diferenciados;
- `OrderFocus` pode confirmar múltiplos servos.

Awards após confirmação:

- `goety:commanding`: `2 + min(3, servos confirmados)`;
- `summoning:practice`: `1 + min(2, servos confirmados)`.

Portanto, comandar 10 servos de uma vez não escala infinitamente a recompensa: a amplitude útil é limitada.

**Fonte:** `GoetyProgressionEvents` e `MasteryPolicies.forGoetyCommand`.

## Goety — kills de servos reconhecem Warlock e Necromancer de forma diferente

Quando uma entidade controlada via `IOwned` mata um mob da categoria `MONSTER`, o dono pode receber mastery.

O evento usa a vida máxima da vítima como magnitude. A intensidade é `ceil(maxHealth / 10)`, limitada entre 1 e 4.

Awards básicos:

- `goety:servants`: `2 + intensidade`;
- `summoning:practice`: 2.

Bônus condicionais:

- Necromancer: `goety:necromancy` = `3 + intensidade`;
- Warlock: `goety:pact_servants` = `2 + intensidade`.

Uma mesma kill pode, portanto, desenvolver lanes diferentes conforme as classes já desbloqueadas pelo dono.

**Fonte:** `GoetyProgressionEvents.onServantHostileKill` e `MasteryPolicies.forGoetyServant`.

## Eidolon — primeira conclusão vale mais que repetição

As policies de Eidolon distinguem primeira conclusão de repetição.

### Ritual confirmado

Somente uma ação com tag `confirmed_ritual` recebe award:

| Lane | Primeira vez | Repetição |
| --- | ---: | ---: |
| `eidolon:ritual` | 8 | 3 |
| `occult:practice` | 4 | 2 |
| `summoning:practice`, se ritual de summoning | 4 | 2 |
| `healing:practice`, se ritual holy | 3 | 1 |

### Alquimia confirmada

O resultado precisa estar confirmado:

- `eidolon:alchemy`: 8 na primeira conclusão, 3 depois;
- `occult:practice`: 3 na primeira conclusão, 1 depois.

Isso torna descoberta e execução correta mais valiosas do que repetir mecanicamente a mesma receita.

**Fonte:** `MasteryPolicies.forEidolonRitual` e `MasteryPolicies.forEidolonAlchemy`.

## Iron's e Ars — custo alto aumenta mastery, mas só até um teto

Ambos calculam uma intensidade de **1 a 5** a partir do custo de recurso, em degraus de 50. Custos muito altos não continuam multiplicando mastery além desse teto.

Iron's concede sempre `magic:casting`, `irons:casting` e `irons:<disciplina>` para uma ação válida. Ars concede `magic:casting`, `ars:casting` e pode adicionar lanes de projectile, amplification, aoe, duration, summoning ou control conforme as tags do cast.

**Fonte:** `MasteryPolicies.forIron` e `MasteryPolicies.forArs`.

## Epic Fight — um dodge bem-sucedido alimenta também Agility

Uma ação marcada como `dodge_success` concede simultaneamente:

- `epicfight:practice`: 2;
- `epicfight:dodge`: 6;
- `agility:practice`: 3.

Assim, esquiva confirmada não é apenas progresso “do Epic Fight”: ela também desenvolve a prática geral de Agility.

**Fonte:** `MasteryPolicies.forEpicFight`.

## Malum — reaping pode gerar várias lanes no mesmo evento

Uma prática espiritual válida sempre pode alimentar `malum:spirit_arcana`. Se for `reaping`, também alimenta `occult:practice` e `malum:reaping`. Se houver `collection`, adiciona `malum:collection`.

Tags `spirit:<afinidade>` geram ainda `malum:spirit/<afinidade>`.

Um único evento de colheita pode, portanto, desenvolver a prática geral de Malum, Occult, reaping e uma afinidade espiritual específica ao mesmo tempo.

**Fonte:** `MasteryPolicies.forMalum`.

## Nomes tecnológicos não equivalem a bônus de máquina

Alguns caminhos, especialmente de Tecnomago, mencionam Create, AE2 e Oritech. Isso participa da identidade e arquitetura da árvore, mas não significa automaticamente que toda máquina desses mods seja acelerada ou fortalecida.

O exemplo já visível na árvore é importante: perks com fantasia de “Ressonância Cinética”/“Sobrecarga Cinética” podem atualmente materializar efeitos em atributos mágicos, e não em RPM, stress, FE ou crafting speed do Create.

Regra de leitura da wiki: **nome e fantasia descrevem identidade; somente hook/attribute/policy comprovam o efeito mecânico.**

## Regra anti-proc recursivo nas masteries

As policies de mastery verificadas retornam vazio quando `ActionOrigin.procDepth() > 0`. Isso impede que efeitos gerados por procs derivados sejam automaticamente recontados como prática primária e iniciem cadeias de XP recursivas.

Essa proteção aparece nas policies de Iron's, Ars, Goety, Goety Servants, Goety Command, Malum, Eidolon, Create e Epic Fight.

**Fonte:** `core/MasteryPolicies.java`.

## Fontes principais

- `src/main/java/dev/gustavopere/rpgskilltree/core/GoetySoulPolicy.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/MasteryPolicies.java`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/goety/GoetyProgressionEvents.java`

Esta página deve crescer somente quando uma nova interação puder ser ligada a uma implementação concreta.
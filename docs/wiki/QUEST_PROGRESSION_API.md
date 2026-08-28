# Quest Progression API

Estado: **IMPLEMENTED FOUNDATION**.

A integração pública para quests, NPCs e narrativa é propositalmente independente de FTB Quests ou de qualquer outro engine de quests. Adapters opcionais devem depender desta superfície pública, e não de attachments, codecs, ledgers ou catálogos internos do RPG.

## Entrada pública

A façade server-side é:

```java
import dev.gustavopere.rpgskilltree.api.RpgQuestProgressionApi;
```

Ela expõe três operações:

```java
QuestProgressionSnapshot snapshot = RpgQuestProgressionApi.query(player);
QuestConditionEvaluation result = RpgQuestProgressionApi.evaluate(player, condition);
QuestProgressionSnapshot after = RpgQuestProgressionApi.applyReward(player, reward);
```

Todas recebem `ServerPlayer`. O servidor continua autoritativo.

## Snapshot de progressão

`QuestProgressionSnapshot` combina uma visão imutável das fontes canônicas existentes sem tornar nenhuma delas gravável pelo addon:

- Character Level e RPG XP;
- Core Progression Points e alocações;
- Perk Budget;
- seis atributos fundamentais e ranks `long` uncapped;
- mastery XP por domínio;
- classes desbloqueadas;
- ranks de perks existentes.

A consulta não materializa Core state apenas por ser observada. Para jogador legado ou novo, a projeção de Core continua seguindo o contrato read-only de `CorePlayerProgressionRuntime.queryProgression`.

## Condições reutilizáveis

`QuestProgressionCondition` possui ID namespaced estável e pode representar atualmente:

- `minimumLevel`;
- `minimumMasteryXp`;
- `classUnlocked`;
- `perkRankAtLeast`;
- `attributeRankAtLeast`.

Exemplo:

```java
QuestProgressionCondition condition = QuestProgressionCondition.minimumMasteryXp(
    "myaddon:quest/fire_training",
    "fire",
    100L
);
QuestConditionEvaluation evaluation = RpgQuestProgressionApi.evaluate(player, condition);
if (evaluation.matched()) {
    // O adapter pode avançar seu próprio estado de quest.
}
```

`QuestConditionEvaluation` retorna o ID da condição, fato avaliado, valor observado, mínimo exigido e resultado booleano. Isso permite logging/debug sem consultar internals do RPG.

## Recompensas

Recompensas devem usar `ProgressionReward` e a façade pública:

```java
ProgressionReward reward = ProgressionReward.characterXp(
    "myaddon:quest/chapter_1",
    250L,
    "myaddon:main_story"
);
RpgQuestProgressionApi.applyReward(player, reward);
```

Tipos canônicos implementados neste checkpoint:

- `CHARACTER_XP`;
- `CORE_POINTS`;
- `MAIN_PERK_BUDGET`.

O `rewardId` é persistido no sistema de claims. Repetir exatamente a mesma recompensa é no-op mesmo após save/reload. Reutilizar o mesmo ID com payload incompatível é rejeitado pelo Core.

Unlock genérico de perk/classe não faz parte desta superfície ainda. Esses estados ainda têm ownership e migração próprios em outros estágios; um adapter não deve contornar isso escrevendo `ProgressionState` diretamente.

## Regras para adapters

1. Nunca use `ModAttachments` diretamente.
2. Nunca escreva level, XP, CPP, rank, Perk Budget ou claims manualmente.
3. Nunca envie rules snapshot ou preço de atributo a partir do cliente.
4. Use IDs namespaced e estáveis para condições e rewards.
5. Trate `query` como observação; use `applyReward` apenas para recompensas server-side confiáveis.
6. Um adapter específico para FTB Quests deve ficar opcional e fora do RPG Core.

## Compatibilidade sem mod de quests

O Core não importa classes de FTB Quests e não registra dependência obrigatória de engine de quests. A API existe dentro do próprio mod e permanece inerte quando nenhum adapter a consome. Portanto remover ou não instalar um mod de quests não remove o runtime de RPG.

## Contratos de teste

A fundação é protegida por:

- `QuestProgressionHooksFoundationTest` — snapshot imutável, condições, IDs e suporte a níveis/ranks acima de `Integer.MAX_VALUE`;
- `verify-quest-runtime.py` — reward/query internos continuam server-authoritative/read-only;
- `verify-quest-public-api.py` — façade pública existe, delega aos serviços canônicos e não cria dependência FTB/codec/attachment Core público;
- pipeline NeoForge completo, incluindo dedicated-server smoke.

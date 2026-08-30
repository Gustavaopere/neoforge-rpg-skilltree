# 02.06 — Elegibilidade de scaling, fauna passiva e MineColonies

## Objetivo

Separar **classificação de entidade** de **política de scaling**. O fato de uma entidade ser `LivingEntity` não autoriza aumentar vida/dano. A decisão deve refletir seu papel real no combate e preservar o equilíbrio de MineColonies.

## Política canônica

Toda entidade elegível ao runtime recebe exatamente uma decisão:

```text
COMBATANT_FULL
NONCOMBATANT_DEFENSIVE
UNSCALED
```

### `COMBATANT_FULL`

Aplica nível, raridade/arquétipo e Effective Stats ofensivos/defensivos segundo Stage 02.

Inclui por padrão:

- hostis vanilla/modded;
- bosses;
- neutrals capazes de combate quando a classificação provar esse papel;
- guards/mercenaries/soldados;
- MineColonies guards;
- MineColonies raiders/invasores;
- summons/pets explicitamente combatentes quando tag/adapter confirmar.

### `NONCOMBATANT_DEFENSIVE`

Recebe somente proteção necessária para coexistir com um mundo escalado. Não recebe multiplicador de dano, affix ofensivo nem reward de mob escalado.

Inclui:

- cidadãos/colonists MineColonies sem função de combate;
- NPCs civis importantes explicitamente marcados por adapter/tag;
- outros não combatentes protegidos por configuração data-driven.

A camada defensiva pode atuar em `MAX_HEALTH`, `ARMOR`, `ARMOR_TOUGHNESS` e resistência a knockback quando suportados. Qualquer outra estatística exige regra explícita.

### `UNSCALED`

Mantém atributos vanilla/provider e não inicializa scaling RPG.

Inclui por padrão:

- vacas, porcos, galinhas, ovelhas, peixes e fauna passiva comum;
- villagers vanilla, salvo override explícito;
- entidades ambientais/decorativas;
- não combatentes sem razão de proteção sistêmica.

Resultado esperado: personagem de nível muito alto continua podendo eliminar fauna passiva com o poder correspondente à própria build; não existe equalização artificial do animal.

## Pipeline de decisão

Precedência obrigatória:

1. override data-driven por `entity_type`/tag;
2. adapter nominal do provider, quando necessário;
3. sinais estáveis vanilla/NeoForge e classificação de arquétipo existente;
4. fallback conservador e diagnosticável.

Tags planejadas:

```text
rpgskilltree:scaling/full_combatants
rpgskilltree:scaling/defensive_noncombatants
rpgskilltree:scaling/excluded
```

`excluded` vence as demais; conflito entre `full_combatants` e `defensive_noncombatants` é erro de validação de datapack.

## MineColonies

O adapter deve usar APIs/classes/IDs **da versão efetivamente suportada em 1.21.1**, verificados no momento da implementação. Não copiar internals nem depender de nomes históricos sem teste.

Semântica requerida:

- raider/invasor → `COMBATANT_FULL`;
- guard/combat worker → `COMBATANT_FULL`;
- worker/citizen civil → `NONCOMBATANT_DEFENSIVE`;
- entidade MineColonies desconhecida → decisão segura via adapter/tag, com diagnóstico; nunca inferir dano ofensivo apenas por namespace.

MineColonies continua autoridade de profissão, colônia, cidadania e comportamento; RPG Skill Tree decide apenas a política de scaling.

## Persistência e migração

Adicionar `scalingPolicyVersion` ao estado ou mecanismo equivalente de migração. Entidades de saves antigos que receberam modifiers RPG mas agora são `UNSCALED` devem ter todos os modifiers canônicos de scaling removidos determinística e idempotentemente.

Para `NONCOMBATANT_DEFENSIVE`, a migração remove componentes ofensivos e reaplica apenas o snapshot defensivo permitido.

Reload não rerrola nível/raridade de combatentes já persistidos. A mudança de política reconcilia apenas os modifiers incompatíveis.

## Performance

- nenhuma varredura global de entidades a cada tick;
- decisão em join/load, mudança relevante de provider/role ou reload de regras;
- cache por `EntityType` somente quando a resposta não depender de estado individual;
- adapter MineColonies consulta estado individual apenas nos pontos de lifecycle necessários.

## Testes obrigatórios

- vaca e galinha: `UNSCALED`, zero modifiers RPG;
- zombie: `COMBATANT_FULL`;
- neutral combatente: full quando evidência aplicável;
- villager vanilla: unscaled por padrão;
- MineColonies worker: defensive-only;
- MineColonies guard: full;
- MineColonies raider: full;
- ausência de MineColonies: core inicia sem classloading opcional;
- legacy passive já escalado: modifiers são removidos;
- legacy civilian com dano escalado: dano é removido e defesa preservada;
- save/load/reload repetido: sem stacking;
- datapack conflictante: falha de validação e last-known-good permanece.

## Acceptance

O subplano fecha somente quando fauna passiva não acompanha nível do jogador, combatentes continuam usando o Stage 02 completo, cidadãos MineColonies sobrevivem por scaling defensivo sem se tornarem combatentes e saves antigos são reconciliados sem modifier órfão.
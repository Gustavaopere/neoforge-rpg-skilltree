# 12.03 — Política de escopo dos estados

## Objetivo

Definir, antes de integrar mods, quais dados pertencem ao corpo e quais pertencem à conta/mundo. Copiar tudo produz duplicação e bugs; compartilhar tudo impede o efeito de "novo jogo".

## Três classificações

### `BODY_LOCAL`

O estado troca junto com o corpo.

Baseline planejado:

- nível e XP RPG;
- pontos e árvore de perks;
- atributos adquiridos pela progressão;
- classe, mastery e especialização;
- recursos corporais persistentes;
- inventário/armor/offhand;
- Curios e equipamentos corporais;
- vanilla XP/level;
- vida/fome;
- progressões externas que representem capacidades inerentes ao personagem, quando houver provider seguro.

### `ACCOUNT_GLOBAL`

O estado é compartilhado entre todos os corpos:

- owner UUID;
- configurações/client preferences;
- lista de corpos e permissões;
- construções e estado físico global do mundo;
- dados administrativos;
- Compêndio/descobertas globais por padrão, salvo decisão futura contrária;
- shared stash explícito, caso seja implementado.

### `RECONCILED`

O sistema externo não pode ser simplesmente salvo/restaurado. Exige regra própria.

Candidatos:

- advancements;
- quests com consequências globais no mundo;
- reputação/factions que alterem NPCs globais;
- claims/teams;
- estados de dimensões/portais;
- dados que outro mod indexe apenas por player UUID e não ofereça API de snapshot.

## Registry de políticas

Criar um registry/data-driven de providers com metadados:

```text
providerId
scope
capture()
validate()
apply()
clearForFreshBody()
merge/reconcile()
requiredMod
failurePolicy
```

## Fresh body

Ao criar um corpo novo, `BODY_LOCAL` deve usar `empty/default`, não cópia do corpo fonte, salvo explicitamente configurado.

Isso é o que garante:

```text
Corpo A: nível 300, Piromante
Corpo B novo: nível 1, nenhuma perk, nenhuma mastery
```

## Mods sem integração

Não copiar NBT arbitrário por heurística. Para um mod desconhecido:

1. manter estado como `ACCOUNT_GLOBAL` por segurança se ele já está ligado ao UUID da conta;
2. registrar diagnóstico de provider ausente quando esse estado deveria idealmente ser corporal;
3. nunca apagar dados externos ao trocar de corpo;
4. adicionar adapter explícito depois.

## Questões especiais

### Vampirism

A forma/nível de vampiro é candidata forte a `BODY_LOCAL`, pois é característica biológica do corpo. Só implementar após confirmar API/storage seguro do mod.

### Ars / Iron's

Mana derivada de atributos deve ser reconstruída; progressões persistentes específicas devem usar provider quando existirem.

### Epic Fight

Skills/passivos associados ao personagem são candidatos a `BODY_LOCAL`, mas não copiar internals sem contrato validado.

### Quests

Separar quest de personagem de quest de mundo. Uma quest que abre uma estrutura global não pode ser "desfeita" ao trocar de corpo.

## Auditoria obrigatória

Antes de marcar este subplano como concluído, produzir tabela real dos mods integrados:

| Provider | Escopo | Capture | Apply | Fresh | Recovery | Testado |
| --- | --- | --- | --- | --- | --- | --- |

## Critérios de aceite

- nenhuma integração existe sem classificação de escopo;
- fresh body não herda progressão corporal;
- state global não é sobrescrito por snapshot corporal;
- providers desconhecidos falham de forma conservadora;
- política é documentável e auditável.
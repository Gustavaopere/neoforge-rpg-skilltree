# A0035 — Armadura Fendida

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** PRESENTE para Armor física; `P-A0035-01` aberta para classificação boss específica do Mobstein.
- **Notion:** `3c569db9-f0db-8136-89ac-e4bbcf6ab6f1`.

## Contrato canônico

- A0032 ≥2 + A0033 ≥1.
- Com 3 Trauma, próximo hit direto MACE consome 3 e aplica −8%/−12% de `Attributes.ARMOR` por 4/6 s.
- Boss recebe metade; classificação deve ser server-side comprovada.
- Nunca substituir por Armor Negation do atacante, dano adicional ou redução mágica/arcana.

## Evidência runtime

- `A0021A0040CombatPolicy` consome Trauma, marca Sundered e calcula 8/12%; boss recebe escala 0,5.
- `A0021A0040EpicFightHooks.applyArmorSunder` aplica `ADD_MULTIPLIED_TOTAL` transitório em `Attributes.ARMOR`, mantém expiry e remove no lifecycle.
- Adapter usa `Tags.EntityTypes.BOSSES` para a escala de boss.

## Provider→árvore

- Black Arcana/Enshrouded resistências mágicas e Shroud não são Armor física.
- Volcanoes hazards não são Armor.
- Mobstein documenta Witherstein como boss, mas as fontes auditadas não comprovam seu registry id nem membership em `Tags.EntityTypes.BOSSES`.

## Pendências Chat 2

- **P-A0035-01:** verificar Mobstein 5.4.4 e provar se Witherstein está no boss tag/canonical classification. Se não estiver, criar somente mapping versionado pelo registry id comprovado. Não inferir por nome/aparência. Até lá, a atenuação específica de boss Mobstein permanece `SEM HOOK SEGURO`/não confirmada.
- Preservar o modifier físico existente e seu cleanup; não alterar resistências mágicas.

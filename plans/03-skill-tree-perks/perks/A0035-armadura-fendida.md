# A0035 — Armadura Fendida

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA no contrato genérico pelo Chat 3 na PR #359**; Witherstein específico permanece fail-closed até prova versionada.
- **Notion:** `3c569db9-f0db-8136-89ac-e4bbcf6ab6f1`.

## Contrato canônico

- A0032 ≥2 + A0033 ≥1.
- Com 3 Trauma, próximo hit direto MACE confirmado consome 3 e aplica −8%/−12% de `Attributes.ARMOR` por 4/6 s.
- Boss recebe metade; classificação deve ser server-side comprovada.
- Nunca substituir por Armor Negation do atacante, dano adicional ou redução mágica/arcana.

## Evidência runtime

- PRE usa `availableTrauma(...)` + `prepareSunder(...)`; Trauma permanece intacto durante a fase reversível.
- `afterConfirmedHit(...)` faz `commitPreparedSunder(...)` somente quando o mesmo root é direto, hostil e produziu dano real; cancelamento/dano zero libera a preparação.
- O commit consome exatamente 3 Trauma e só então marca `Sundered`.
- `A0021A0040EpicFightHooks` aplica o modifier transitório em `Attributes.ARMOR` somente quando o commit foi confirmado.
- Boss genérico usa `Tags.EntityTypes.BOSSES`; não existe mapping inventado para Witherstein.

## Pendências

- **P-A0035-02 — RESOLVIDA:** reservation→POST commit, rollback e consumo único testados.
- **P-A0035-01 — FAIL-CLOSED NÃO BLOQUEANTE:** atenuação específica de Witherstein exige registry/tag versionado real; a rota boss canônica permanece funcional para entidades no tag.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- Pernach/arma Simply More só participa quando Epic Fight Compat ou mapping versionado resolve a arma como `MACE`.
- Armor reduction/ignore/sunder provider-native não conta como Trauma ou `Sundered` RPG.
- O commit ocorre somente após o mesmo root MACE produzir hit confirmado.
- Efeito específico não comprovado do `simplymore-forge-1.3.0_alpha.jar` permanece fail-closed.
- Simply Tooltips não é provider mecânico.

## Fechamento Chat 3

PRE→POST, rollback cancel/zero, concorrência de roots, consumo único, modifier/expiry e boss-half genérico foram validados. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`.

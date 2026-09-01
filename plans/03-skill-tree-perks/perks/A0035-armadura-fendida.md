# A0035 — Armadura Fendida

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**; Witherstein permanece sem classificação boss específica até prova versionada.
- **Notion:** `3c569db9-f0db-8136-89ac-e4bbcf6ab6f1`.

## Contrato canônico

- A0032 ≥2 + A0033 ≥1.
- Com 3 Trauma, próximo hit direto MACE confirmado consome 3 e aplica −8%/−12% de `Attributes.ARMOR` por 4/6 s.
- Boss recebe metade; classificação deve ser server-side comprovada.
- Nunca substituir por Armor Negation do atacante, dano adicional ou redução mágica/arcana.

## Evidência runtime após Chat 2

- PRE não consome mais Trauma nem marca `Sundered`: `availableTrauma(...)` + `prepareSunder(...)` reservam o consumo por `rootActionId`.
- `afterConfirmedHit(...)` faz `commitPreparedSunder(...)` somente quando o mesmo root é direto, hostil e produziu dano real; cancelamento/dano zero descarta a preparação sem perder Trauma.
- O commit consome exatamente 3 Trauma e só então marca `Sundered`.
- `A0021A0040EpicFightHooks` aplica o modifier transitório `ADD_MULTIPLIED_TOTAL` em `Attributes.ARMOR` somente quando o commit foi confirmado.
- Boss genérico continua usando `Tags.EntityTypes.BOSSES`; não foi inventado mapping para Witherstein.

## Provider→árvore

- Black Arcana/Enshrouded resistências mágicas e Shroud não são Armor física.
- Volcanoes hazards não são Armor.
- Mobstein documenta Witherstein como boss, mas as fontes auditadas não comprovam seu registry id nem membership em `Tags.EntityTypes.BOSSES`.

## Pendências Chat 2

- **P-A0035-02 — RESOLVIDA NO CÓDIGO:** transação movida para reservation→POST commit; cancelamento/dano zero preserva Trauma e não cria `Sundered` fantasma.
- **P-A0035-01 — FAIL-CLOSED PRESERVADO:** atenuação específica de Witherstein só poderá ser confirmada com registry/tag versionado real. A ausência desse mapping não desativa a rota boss canônica já comprovada para entidades no tag.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura MACE:** Pernach/arma Simply More só participa quando Epic Fight Compat ou mapping explicitamente versionado resolve a arma como `MACE`; aparência, nome e namespace não bastam.
- **Debuffs separados:** armor reduction/ignore/sunder provider-native permanece provider-owned e não conta como Trauma ou `Sundered` RPG.
- **Transação:** o commit da Armadura Fendida ocorre somente após o mesmo root MACE produzir hit confirmado.
- **Alpha:** efeito específico não comprovado do `simplymore-forge-1.3.0_alpha.jar` permanece fail-closed.
- **Simply Tooltips:** não é provider mecânico.

## Handoff Chat 3

Validar PRE→POST, rollback em cancel/zero, concorrência de roots, consumo único de 3 Trauma, modifier/expiry/cleanup, boss half e ausência de classificação heurística do Witherstein. O Chat 2 não executou a bateria final.

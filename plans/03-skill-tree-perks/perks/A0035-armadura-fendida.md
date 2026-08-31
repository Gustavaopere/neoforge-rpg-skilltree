# A0035 — Armadura Fendida

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA para o contrato canônico genérico pela PR #252; `P-A0035-02` resolvida. `P-A0035-01` permanece como extensão provider-specific Mobstein sem hook seguro comprovado.
- **Notion:** `3c569db9-f0db-8136-89ac-e4bbcf6ab6f1`.

## Contrato canônico

- A0032 ≥2 + A0033 ≥1.
- Com 3 Trauma, próximo hit direto MACE confirmado consome 3 e aplica −8%/−12% de `Attributes.ARMOR` por 4/6 s.
- Boss recebe metade; classificação deve ser server-side comprovada.
- Nunca substituir por Armor Negation do atacante, dano adicional ou redução mágica/arcana.

## Evidência runtime

- `A0021A0040CombatPolicy.beforeHit` agora apenas prepara/reserva A0035 por `rootActionId`; não consome Trauma nem marca `Sundered` no PRE.
- `A0021A0040CombatState.commitPreparedSunder(...)` consome exatamente 3 Trauma e marca `Sundered` somente após o mesmo root produzir hit direto/hostil com dano real.
- Em cancelamento/dano zero, `afterConfirmedHit(...)` chama `discardPreparedMaceActions(...)`; Trauma e estado permanecem intactos e a reserva é liberada imediatamente, sem aguardar TTL.
- `A0021A0040EpicFightHooks.applyArmorSunder` aplica `ADD_MULTIPLIED_TOTAL` transitório em `Attributes.ARMOR`, mantém expiry e remove no lifecycle; o modifier só é aplicado quando o commit POST retorna `armorSunderCommitted=true`.
- Boss genérico usa `Tags.EntityTypes.BOSSES` e recebe metade da fração; testes do lote confirmam 12% → 6% no rank 2.

## Provider→árvore

- Black Arcana/Enshrouded resistências mágicas e Shroud não são Armor física.
- Volcanoes hazards não são Armor.
- Mobstein documenta Witherstein como boss, mas a auditoria Chat 3 não obteve registry id nem prova de membership em `Tags.EntityTypes.BOSSES`; nenhuma classificação específica foi inventada.

## Pendências Chat 2 / resolução Chat 3

- **P-A0035-01 — ABERTA, NÃO BLOQUEANTE PARA O CONTRATO GENÉRICO:** Witherstein permanece `SEM HOOK SEGURO` para atenuação específica até existir registry id/tag comprovado e versionado. O boss-half genérico funciona para qualquer entidade realmente presente em `Tags.EntityTypes.BOSSES`.
- **P-A0035-02 — RESOLVIDA:** A0035 usa reservation→POST commit; cancelamento/dano zero preserva Trauma/Sundered e libera reserva.
- Modifier físico e cleanup foram preservados; nenhuma resistência mágica foi alterada.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura MACE:** Pernach/arma Simply More só participa quando Epic Fight Compat ou mapping explicitamente versionado resolve a arma como `MACE`; aparência, nome e namespace não bastam.
- **Debuffs separados:** qualquer armor reduction/ignore/sunder ou outro Implicit/Unique do provider permanece provider-owned e não conta como Trauma, `Sundered` RPG ou receipt de A0035.
- **Transação:** Armadura Fendida é commitada somente após o mesmo root MACE produzir hit confirmado.
- **Alpha:** efeito específico não comprovado do `simplymore-forge-1.3.0_alpha.jar` permanece fail-closed.
- **Simply Tooltips:** não é provider mecânico.
- **Notion:** `Provider/Mods`, `Hook`, `Fallback` e `Regra` atualizados; re-fetch PASS.

## Validação Chat 3 — PR #252

- `A0031A0040Chat3RegressionJUnitTest`: rollback de A0035 preserva 3 Trauma, não marca Sundered e permite nova preparação do mesmo root após término inválido.
- `A0031A0040ImplementationContractJUnitTest`: PRE preserva estado, POST confirmado consome Trauma/marca Sundered, boss-half validado.
- `RPG Skill Tree CI` #2806: Core, JUnit 5, NeoForge GameTests, runtime/data validations, build, JAR e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #41: **GREEN**.
- Resultado: contrato genérico A0035 validado; apta ao merge da PR #252. A extensão Witherstein continua fail-closed documentada.

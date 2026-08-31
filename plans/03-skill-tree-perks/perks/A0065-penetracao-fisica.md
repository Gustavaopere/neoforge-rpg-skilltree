# A0065 — Penetração Física

## Estado de design

**APROVADA COM BOUNDARY.** Especialização anti-armadura do corredor de Força Aplicada.

## Contrato final

- **Ranks:** 4; **custo:** 1/rank.
- **Dependência:** A0061 ≥ 2 ranks + gateway MARTIAL.
- **Efeito:** +2 pontos percentuais de penetração física elegível por rank, máximo 8%, no mesmo root direto.
- Melee Epic Fight usa `armor_negation`; projectile físico usa redução da parcela de Armor no `DamageContainer`. A perk aplica **somente sua própria parcela**.

## Authority / composição

Penetração provider-native já presente no ataque continua pertencendo ao provider. A0065 não lê o valor do Implicit para reaplicá-lo e não converte armor sunder/debuff persistente em penetração adicional RPG.

## Simply Swords

Armor-ignore de Rapier/Spear e armor sunder de Hammer/Greathammer continuam Simply-owned. Eles podem coexistir com A0065 no mesmo ataque conforme pipelines distintos, mas não são rerrolados, copiados, somados como se fossem ranks nem transformados em novos roots.

## Fail-closed

Sem surface segura para reduzir Armor/armor negation naquela ação, omitir a parcela; não substituir por dano bruto, true damage ou redução persistente. Herda `P-A0061-01` para classificação melee.

## Testes obrigatórios

- 2/4/6/8% apenas no mesmo root;
- melee e projectile usam suas surfaces canônicas sem dupla redução;
- Simply armor-ignore/sunder não é reexecutado pelo RPG;
- magia, DoT, backlash, companion e hazard = neutro.

## Nove eixos

1. Gates: PASS — A0061≥2.
2. Integração: PASS — Armor pipeline existente.
3. Qualidade: PASS — decisão anti-armadura, não dano genérico.
4. Topologia: PASS — ramifica de Força Aplicada.
5. Especialização: PASS — MARTIAL anti-armadura.
6. PT-BR: PASS.
7. Registro: GitHub.
8. NeoVitae: PASS.
9. Providers: PASS com ownership Simply explícito.
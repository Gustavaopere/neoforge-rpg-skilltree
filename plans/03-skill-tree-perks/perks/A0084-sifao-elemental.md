# A0084 — Sifão Elemental

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-81a6-9479-ed17ddf2d786`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** coeficiente existe no core, mas não há producer elemental direto no bridge atual; node **indisponível/não comprável** até existir binding seguro.

## Contrato canônico

- Gateway ARCANE + afinidade/gateway/ramo elemental válido da Árvore Exterior + adapter elemental disponível.
- 3 ranks: 0,5% / 1,0% / 1,5% do dano elemental direto pós-mitigação.
- A0083, A0084 e lifesteal nativo convergem na mesma root; o `SustainResolver` escolhe o maior coeficiente elegível, sem soma integral.
- Cap compartilhado: 3% da vida máxima por janela móvel de 20 ticks.

## Classificação elemental

O receipt mínimo deve provar `ownerPlayer`, root/event identity, `DIRECT_ELEMENTAL` e o elemento canônico. Namespace, cor/VFX, nome do spell ou tipo visual não bastam.

- Iron's 3.16.3: `SpellDamageSource.spell().getSchoolType().getDamageType()` fornece uma superfície provider-native para mapear escolas/damage types aprovados; o mapa elemento↔school deve ser explícito/versionado.
- Ars Nouveau 5.13.1: integrar somente pela classificação de spell/contexto realmente exposta pela versão instalada.
- Ars Elemental 0.7.10.1: owner de extensões elementais; integrar por API/registro próprio quando o elemento puder ser provado, nunca por heurística de glyph/VFX.
- Addons de Iron's/Ars só herdam A0084 se preservarem a identidade causal e a classificação elemental do provider pai.

## Exclusões

Dano periódico usa A0085, não A0084. Summons, ambiente, Black Arcana Backlash, BLOOD_MAGIC_COST, Shroud/Exposure, Volcanoes heat/lava/gas e máquinas tecnológicas ficam fora. Um spell que aplica DoT pode ter o hit direto elegível em A0084 e os ticks posteriores apenas em A0085, com identidades distintas.

## Evidência runtime

`A0081A0100CombatPolicy` modela A0084, mas `A0081A0100CombatEvents` nunca classifica `elemental=true`; portanto hoje nenhum root alcança o coeficiente. A fórmula pura não satisfaz availability.

## Pendências para Chat 2

- **P-A0084-01 BLOQUEANTE:** unavailable-node invariant enquanto nenhum adapter elemental server-authoritative existir.
- **P-A0084-02:** mapa versionado Iron's school/damage type → elemento, com causing player e root real.
- **P-A0084-03:** adapter Ars/Ars Elemental somente contra API/artefato instalado, com dedup A0083↔A0084 na mesma root.
- **P-A0084-04:** separar hit direto de DoT derivado; o tick posterior nunca reutiliza a root como nova cura A0084.
- **P-A0084-05:** testes multi-elemento, classificação ambígua, addon provider-present/absent, cap e multiplayer.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | ARCANE + afinidade + adapter elemental. |
| Integração global | PASS | A0083/A0084 dedup no mesmo SustainResolver. |
| Qualidade/identidade | PASS | sustain elemental sem confundir DoT/ambiente. |
| Topologia | PASS | ponte ARCANE/ELEMENTAL_SUSTAIN. |
| Especializações | PASS | afinidade exterior não vira Specialist por si só. |
| PT-BR | PASS | nomenclatura consistente. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | Iron's/Ars/Ars Elemental por classificação real. |

Os 18 critérios passam **no design** com unavailable-node explícito.
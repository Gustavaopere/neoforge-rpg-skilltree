# A0102 — Proteção Arcana

## Estado

- **Design:** APROVADO em 2026-08-31.
- **Notion:** `3c569db9-f0db-8106-b28d-d2036ec65d51`; corrigido e verificado pós-escrita.
- **Runtime:** consumer genérico ainda não materializado; node fica indisponível até implementação.

## Contrato canônico

- Gateway VITALITY + A0088 Constituição ≥2 + acesso real ao corredor ARCANE.
- 4 ranks, +2% de redução mágica genérica/rank, teto +8%.
- Classificação primária: `neoforge:is_magic` no DamageType.
- Uma contribuição genérica A0102 por root.
- Resistência de escola/elemento realmente distinta permanece provider-owned e pode compor; alias da mesma resistência genérica deve deduplicar.
- `ARCANE_BACKLASH`, `BLOOD_MAGIC_COST` e custos/hazards excluídos não são elegíveis.

## Boundary de implementação

Usar `LivingDamageEvent.Pre` + `DamageMitigationResolver` RPG-owned. O NeoForge 1.21.1 fornece `Tags.DamageTypes.IS_MAGIC`; adapters versionados só são necessários quando o provider não publica a classificação correta.

## Cobertura de providers

- NeoForge 21.1.248: tag mágica canônica e pipeline.
- Iron's Spellbooks 3.16.3: schools/resistances continuam nativas; nenhuma duplicação.
- Ars Nouveau 5.13.1 no guia/modlist: classificação apenas por fonte/tag/adapter causal.
- Black Arcana: Arcane/Corruption Resistance e forecast permanecem authority própria/read-only; não alimentam A0102 genericamente.
- Fixture RPG atual ainda referencia Ars 5.13.0 e precisa ser reconciliada pelo Chat 2.

## Pendências para Chat 2

- `P-A0102-01`: implementar consumer genérico no resolver e availability.
- `P-A0102-02`: reconciliar fixture Ars 5.13.0→5.13.1 e testar Iron's/Ars/provider absent, school resistance e exclusões Black Arcana.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | bridge VITALITY↔ARCANE real. |
| Integração global | PASS | genérico ≠ school/arcane resistance. |
| Qualidade/identidade | PASS | proteção mágica transversal. |
| Topologia | PASS | ponte camada 2. |
| Especializações | PASS | bridge PP anti-double-count. |
| PT-BR | PASS | sem ambiguidade de stack. |
| Notion | PASS | correção persistida. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | versões/authority explicitadas. |

Os 18 critérios passam no design; fontes não classificadas ficam fail-closed.
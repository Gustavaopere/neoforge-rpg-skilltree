# A0084 — Sifão Elemental

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-81a6-9479-ed17ddf2d786`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- Node permanece **indisponível/não comprável** porque nenhum mapa elemental aprovado/versionado foi materializado pelo Chat 1.

## Contrato canônico

- Gateway ARCANE + afinidade/gateway/ramo elemental válido da Árvore Exterior + adapter elemental disponível.
- 3 ranks: 0,5% / 1,0% / 1,5% do dano elemental direto pós-mitigação.
- A0083, A0084 e lifesteal nativo convergem na mesma root; o `SustainResolver` escolhe o maior coeficiente elegível, sem soma integral.
- Cap compartilhado: 3% da vida máxima por janela móvel de 20 ticks.

## Classificação elemental

O receipt mínimo deve provar `ownerPlayer`, root/event identity, `DIRECT_ELEMENTAL` e o elemento canônico. Namespace, cor/VFX, nome do spell ou tipo visual não bastam.

- Iron's 1.21.1-3.16.3 expõe `SpellDamageSource.spell().getSchoolType().getDamageType()`, mas o dossiê exige mapa elemento↔school **explícito/versionado**; o Chat 2 não inventou esse mapa.
- Ars Nouveau 5.13.1 e Ars Elemental 0.7.10.1 permanecem sem adapter até haver API/contexto exato aprovado.
- Addons só podem herdar A0084 se preservarem identidade causal e classificação elemental do provider pai.

## Implementação Chat 2 — 2026-09-01

- `CombatPerkAvailabilityRuntime` marca A0084 unavailable e `effectiveRanks` mascara qualquer rank persistido;
- purchase server-authoritative recusa A0084 antes de custo/replay mutation;
- `A0081A0100CombatPolicy` mantém a fórmula latente, mas nenhum caller envia `elemental=true`;
- o novo adapter A0083 do Iron's **não** promove school/damage type a elemento sem o mapa exigido;
- nenhum namespace, VFX, glyph ou damage type isolado foi usado como heurística;
- DoT derivado permanece reservado a A0085, sem reutilizar hit direto como pulso elemental.

## Checklist Chat 2

- [x] Availability fail-closed implementada
- [x] Rank efetivo mascarado quando indisponível
- [x] Purchase sem gasto/rank fantasma
- [x] Fórmula latente preservada sem producer falso
- [x] A0083 não promove automaticamente A0084
- [x] Código presente em fail-closed
- [ ] **PENDÊNCIA / RETORNO AO CHAT 1 SE FOR DESEJADO ATIVAR:** definir mapa canônico/versionado school/damage type → elemento
- [ ] **VALIDAÇÃO CHAT 3:** purchase unavailable/rank persistido=efeito zero
- [ ] **VALIDAÇÃO CHAT 3:** Iron's direct magic não ativa A0084 sem mapa
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/testes de integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

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

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
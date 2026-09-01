# A0083 — Vampirismo Mágico

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-8156-85ca-dc922918e483`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE para Iron's 1.21.1-3.16.3 / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- A0083 é comprável somente quando o exact version contract do Iron's está operacional; drift/ausência volta a **UNAVAILABLE**.

## Contrato canônico

- Gateway ARCANE + pelo menos um ramo de dano mágico direto + provider adapter disponível.
- 3 ranks: 0,6% / 1,2% / 1,8% do dano mágico direto pós-mitigação.
- Uma identidade causal resolve no máximo uma vez no `SustainResolver`; cap global 3% max health/20 ticks.
- Dano periódico, summon, ambiente, custo de vida/recurso e efeito derivado não entram.

## Binding provider-native implementado

O upstream da release auditada declara `mod_version=1.21.1-3.16.3`. Nesse mesmo snapshot, `io.redspace.ironsspellbooks.damage.SpellDamageSource` expõe `spell()`, `getLifestealPercent()` e a semântica `isDirect()`/`indirect()`.

`IronsSustainVersionContract` faz exact-match em `1.21.1-3.16.3` e verifica por reflexão a presença da classe e dos dois métodos exigidos sem link obrigatório quando Iron's está ausente. `CombatPerkAvailabilityRuntime` só torna A0083 disponível quando presença + versão + runtime contract passam.

`IronsSustainEvents` aceita somente `SpellDamageSource` com causing entity `ServerPlayer`, player elegível, alvo hostil e `source.isDirect()==true`. A root é mantida por identidade do `DamageSource`; o dano final vem de `LivingDamageEvent.Post` e converge no mesmo `SustainResolver`.

Quando `getLifestealPercent()>0`, ou se a leitura desse campo falhar, a native correlation é `AMBIGUOUS`: a parcela Skill Tree é zero. Nenhum heal nativo é estimado ou reexecutado.

## Providers ainda fail-closed

- Ars Nouveau 5.13.1 continua sem adapter neste lote porque o dossiê exige classe/contexto causal exato da versão instalada; namespace/damage type isolado não é prova suficiente.
- Addons de Iron's/Ars só herdam o caminho se emitirem exatamente o provider source causal aprovado; nenhuma lista por namespace foi inventada.
- Summons/familiars, Black Arcana Backlash, BLOOD_MAGIC_COST, Enshrouded/Volcanoes hazards, máquinas/turrets/fake players permanecem inelegíveis.

## Checklist Chat 2

- [x] Availability por provider/version contract implementada
- [x] Iron's exact version `1.21.1-3.16.3` fixada
- [x] Runtime API shape `SpellDamageSource` verificada por reflexão
- [x] Causing player + direct source exigidos
- [x] Dano final confirmado em POST
- [x] `SustainResolver` único preservado
- [x] Native lifesteal ambíguo fail-closed
- [x] Ars/derived providers permanecem sem heurística
- [x] Código presente
- [ ] **PENDÊNCIA NÃO BLOQUEANTE:** Ars Nouveau 5.13.1 sem adapter causal aprovado
- [ ] **VALIDAÇÃO CHAT 3:** provider absent/version mismatch/API mismatch → unavailable
- [ ] **VALIDAÇÃO CHAT 3:** direct vs indirect Iron's
- [ ] **VALIDAÇÃO CHAT 3:** native lifesteal >0 → Skill Tree=0
- [ ] **VALIDAÇÃO CHAT 3:** cap/dedup/multiplayer
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/testes de integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Pendências para Chat 3

- validar o exact version gate com o metadata runtime real do JAR instalado;
- validar pelo menos uma spell `SpellDamageSource` direta elegível e uma `.indirect()` inelegível;
- validar lifesteal nativo, source reflection failure e absence/provider drift em fail-closed;
- confirmar que uma mesma `DamageSource` multi-target não paga sustain mais de uma vez por root.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | ARCANE + ramo direto + adapter causal. |
| Integração global | PASS | converge no SustainResolver. |
| Qualidade/identidade | PASS | sustain de magia direta, não de hazards/summons. |
| Topologia | PASS | ARCANE/SUSTAIN, Camada 4. |
| Especializações | PASS | PP por mapeamento semântico. |
| PT-BR | PASS | terminologia canônica. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | Iron's/Ars somente por receipt real; addons por herança comprovada. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
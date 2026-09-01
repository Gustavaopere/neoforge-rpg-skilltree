# Auditoria Chat 1 — A0141–A0150

**Intervalo:** A0141–A0150, exatamente 10 perks consecutivas.  
**Data:** 2026-09-01.  
**Base de abertura:** `main@452e8b23e374179c1f616f9beedce6e3dea66ef5`.  
**Freshness final:** `main@69e2a04c8de840b332541967a06a1c7e2d3082f3`; o delta é exclusivamente Compendium/TFC fauna editorial da PR #357, sem capability nova para este lote.  
**Responsabilidade:** auditoria, design, integração e documentação. Nenhum runtime é implementado pelo Chat 1 e nenhum merge pertence a este chat.

## Fontes obrigatórias

Foram usadas como referência operacional:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`;
- Notion canônico A0141–A0150;
- `STATUS.md`, dossiês predecessores e código/API exato dos providers quando o contrato exigiu prova.

## Resultado executivo

| Código | Perk | Resultado Chat 1 | Estado runtime esperado hoje |
|---|---|---|---|
| A0141 | Adaptação Boreal | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: ledger/mapper COLD causal ausentes |
| A0142 | Digestão Frugal | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: tradeoff all-or-nothing sem seam de nutrient decay |
| A0143 | Nutrição Persistente | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` transitivo por A0142; recovery seams também não provados |
| A0144 | Poder Mágico | DESIGN APROVADO | Iron's `SPELL_POWER`; Ars `SpellDamageEvent.Pre` para dano |
| A0145 | Eficiência Arcana | DESIGN APROVADO PARCIAL POR PROVIDER | Ars implementável; Iron's fail-closed por incoerência admission→debit |
| A0146 | Reserva Arcana | DESIGN APROVADO | Iron's `MAX_MANA`; Ars `MaxManaCalcEvent` |
| A0147 | Fluxo Arcano | DESIGN APROVADO | Iron's `MANA_REGEN`; Ars `ManaRegenCalcEvent` |
| A0148 | Conjuração Rápida | DESIGN APROVADO | Iron's `CAST_TIME_REDUCTION`; outros canais fail-closed |
| A0149 | Recuperação de Feitiço | DESIGN APROVADO | Iron's cast completion + cooldown Pre/Post comprovados |
| A0150 | Estabilidade de Conjuração | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: interruption conversion boundary ausente |

`UNAVAILABLE_NODE` significa compra falha antes do gasto e allocation legado vale 0 PP para gates, permanecendo reembolsável/migrável. Fallback por provider não autoriza bônus genérico.

## Notion

- Fetch fresco: **10/10**.
- As dez páginas foram auditadas individualmente.
- Hardening/materialização de provider aplicada a A0141–A0150 conforme necessário.
- Re-fetch final pós-escrita: **10/10 PASS**.
- A fonte canônica preserva ranks, custos, topologia e identidades; apenas hooks/gates/fallbacks foram endurecidos quando a API real exigiu.

## Providers e snapshots exatos

### Nutritional Balance 7.0.3

Upstream auditado: `dannydjdk/Nutritional-Balance@fce213e966b395b16ae30a801a19a37f6a73da50`.

`INutritionalBalancePlayer` expõe consulta dinâmica de nutrientes e status, mas `DefaultNutritionalBalancePlayer.processSaturationChange(...)` calcula e aplica o decremento internamente, sem evento/reducer público com contexto do jogador. Portanto A0142 não pode modular causalmente o benefício do tradeoff sem capability futura; A0142 inteira fica indisponível. A0143 herda esse gate.

### Iron's Spells 'n Spellbooks 3.16.3

Upstream auditado: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

Bindings comprovados:

- `AttributeRegistry.SPELL_POWER` → A0144;
- `AttributeRegistry.MAX_MANA` → A0146;
- `AttributeRegistry.MANA_REGEN` → A0147;
- `AttributeRegistry.CAST_TIME_REDUCTION` → A0148;
- `SpellOnCastEvent` + `SpellCooldownAddedEvent.Pre/Post` → A0149.

Bindings não suficientes:

- A0145: `SpellOnCastEvent#setManaCost` só altera o debit **depois** de `canBeCastedBy()` já ter calculado `hasEnoughMana` com `getManaCost(spellLevel)` original. Desconto somente no debit é implementação semanticamente incompleta; canal Iron's fica fail-closed.
- A0150: `SpellPreCastEvent` não representa interrupção ativa e não expõe motivo/tempo restante; nenhum boundary público equivalente foi provado imediatamente antes do cancelamento real. A perk inteira permanece indisponível.

### Ars Nouveau 5.13.1

Upstream auditado: `baileyholl/Ars-Nouveau@112920ff774831f204031da75b4c4e73d3765157`.

Bindings comprovados:

- `SpellDamageEvent.Pre` → A0144, somente dano de spell;
- `SpellCostCalcEvent.Post` → A0145;
- `MaxManaCalcEvent` + `IManaCap` → A0146;
- `ManaRegenCalcEvent` → A0147.

Nenhum hook genérico de cast-time/sequence/interruption foi aprovado para A0148–A0150; esses canais permanecem fail-closed até contrato específico.

## Decisões críticas de design

### A0141 — cold acclimation

A0141 não pode ser comprada enquanto `AcclimationLedger(ENVIRONMENTAL_COLD)` e um mapper COLD causal/quantificado estiverem ausentes. `ADVERSE_COLD` e `ENVIRONMENTAL_COLD` são estados distintos. Bioma, BODY/WORLD, freezing point e ICE damage não substituem receipt.

### A0142/A0143 — nutrição

A0142 é all-or-nothing: custo de saturação e benefício sobre decay nutricional devem existir juntos. Não é permitido compensar nutrientes depois do fato ou inferir decay por polling.

A0143 usa `N = getPlayerNutrients().size()` e status dinâmicos, sem assumir cinco nutrientes. Mesmo depois de A0142 ser habilitada, STAMINA e HEALTH só recebem bônus quando um seam causal de recuperação **natural** estiver provado; não criar pulses.

### A0144 — potência provider-local

Não existe segundo atributo universal `MAGIC_POWER`. Iron's preserva `SPELL_POWER`; Ars preserva `SpellDamageEvent.Pre`. Cada outcome recebe A0144 no máximo uma vez. Ars healing/utility permanece fora até seam próprio.

### A0145 — coerência gate→debit

A existência de setter de mana cost não basta. A redução precisa afetar o pipeline que decide se o cast cabe **e** a cobrança. Ars satisfaz; Iron's 3.16.3 não.

### A0146 — sem mana gratuita

Aumentar máximo nunca aumenta current. Rank-down/respec só clampa para baixo se current exceder o novo máximo. Iron's e Ars mantêm pools separados.

### A0147 — regen não cria fonte

A perk multiplica somente taxa nativa positiva. Taxa 0 continua 0. Sem scheduler próprio e sem `addMana` direto como substituto.

### A0148 — semântica nativa de cast time

Iron's diferencia LONG/non-continuous de CONTINUOUS em `getEffectiveCastTime()`. A0148 altera `CAST_TIME_REDUCTION`; não substitui a lógica pelo modelo genérico `tempo/(1+bônus)`.

### A0149 — reservation → commit

A janela nasce na conclusão real da primeira magia não-INSTANT. A segunda magia arma candidate. A redução é aplicada em `SpellCooldownAddedEvent.Pre`, mas janela/candidate e cooldown interno só são consumidos/iniciados após `SpellCooldownAddedEvent.Post` confirmar o commit.

### A0150 — interruption fail-closed

Sem boundary server-side antes do cancelamento, não existe implementação segura. Reiniciar cast, interceptar depois do fato ou refundar mana não preserva identidade. `ResourceDebitReceipt(MANA)` deve ser da mesma action e refletir mana realmente paga.

## Capability delta dos quatro projetos próprios

Arquivo canônico: `guides/projects/17-capability-delta-a0141-a0150.md`.

### RPG Skill Tree

Baseline anterior `f055a65e…`; head auditado `452e8b23…`; freshness final da main `69e2a04…`. O delta pertinente inclui política de origem de Mastery do Iron's, que é progressão/Mastery e não cria authority sobre MANA, spell power, cast-time, cooldown ou interrupção. O commit final da main é apenas Compendium/TFC fauna editorial.

### Volcanoes

`eaddc3232dfc600780769f4a5e7e45ff1e50181c`, sem delta. Não é provider de mana/nutrição/cast deste lote.

### Enshrouded

`e00e6037d7265eb6ab6b3b877428ddfbc4eaec81`. O delta fecha Stage 08.01 com bridge para Ars Zero na manifestação do Lich. Isso é **BRIDGE** de narrativa/manifestação; não cria pool, spell power, cast-time ou cooldown do jogador e não exige nova perk dentro das dez auditadas.

### Black Arcana

`e573a0edfcb69d09e423b60ad75ab71b9d8e70c5`. O delta é QA/fixture/artifact/presentation de Stage 05/05A. Arcane/Corruption Resistance, Strain e Backlash permanecem authorities próprias; nenhuma delas é MANA. Nenhuma capability nova precisa ser incorporada às perks do lote.

## Provider → árvore

| Capability/provider | Cobertura | Disposição |
|---|---|---|
| Nutritional Balance query de nutrients/status | A0142/A0143 | query coberta; mutation de decay sem seam ⇒ fail-closed |
| Iron's `SPELL_POWER` | A0144 | COBERTA POR PERK EXISTENTE |
| Ars `SpellDamageEvent.Pre` | A0144 | COBERTA POR PERK EXISTENTE, dano apenas |
| Ars `SpellCostCalcEvent.Post` | A0145 | COBERTA POR PERK EXISTENTE |
| Iron's cost setter pós-admission | A0145 | SEM HOOK COMPLETO; NÃO INTEGRAR PARCIALMENTE |
| Iron's/Ars max mana | A0146 | COBERTA POR PERK EXISTENTE |
| Iron's/Ars mana regen | A0147 | COBERTA POR PERK EXISTENTE |
| Iron's cast-time attribute | A0148 | COBERTA POR PERK EXISTENTE |
| Iron's cooldown Pre/Post | A0149 | COBERTA POR PERK EXISTENTE |
| Iron's interruption internal path | A0150 | SEM HOOK SEGURO; FAIL-CLOSED |
| Enshrouded ↔ Ars Zero Lich manifestation | fora do efeito das dez perks | BRIDGE, progressão/story nativa |
| Black Arcana QA/presentation | nenhuma | NÃO DEVE SER INTEGRADO |
| RPG Iron Mastery source policy | nenhuma nova | PROGRESSÃO NATIVA AUTORITATIVA |

Nenhuma capability detectada ficou sem disposição.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | availability transitiva e provider-gated; A0141/A0142/A0143/A0150 indisponíveis |
| Integração global | PASS | resources/authority separados; sem pools universais inventados |
| Qualidade/identidade | PASS | mana, Source, Soul Energy, nutrition, cast-time e cooldown distintos |
| Topologia | PASS | SURVIVAL e ARCANE preservados conforme Notion |
| Especializações | PASS/N/A | PP inválido/fail-closed não conta; fundamentals não bypassam Gate B/C |
| PT-BR | PASS | nomes/efeitos preservados |
| Notion | PASS | 10/10 fetch + 10/10 re-fetch final |
| NeoVitae | PASS/N/A | ausente/removido |
| Cobertura providers | PASS | providers diretos + quatro projetos próprios classificados |

## Checklist técnico — 18 critérios

| # | Critério | Resultado |
|---:|---|---|
| 1 | efeito real | PASS — somente hooks/providers comprovados |
| 2 | provider-native first | PASS |
| 3 | sem mecânica inventada | PASS |
| 4 | fail-closed | PASS — gaps explícitos |
| 5 | fallback mantém identidade | PASS |
| 6 | Mastery por feitos | N/A neste lote; delta de policy não vira perk |
| 7 | anti-farm | PASS/N/A — windows bounded e sem proc fantasma |
| 8 | atribuição causal | PASS — caster/action/cooldown commit/receipt quando aplicável |
| 9 | sem pipelines duplicados | PASS |
| 10 | custos reais | PASS — MANA tipada e paid receipt em A0150 |
| 11 | sem geração gratuita | PASS — A0146/A0147 endurecidas |
| 12 | read-only correto | PASS — projetos próprios/gates sem authority indevida |
| 13 | versionamento | PASS — snapshots exatos registrados |
| 14 | coerência estrutural | PASS — ranks/custos/prereqs preservados |
| 15 | dependências semânticas | PASS — A0142→A0143 e A0144/A0145→A0146 etc. |
| 16 | sem sobreposição indevida | PASS — resources/channels separados |
| 17 | implementável posteriormente | PASS — owner/hook/order/fallback/testes definidos |
| 18 | verificação pós-escrita | PASS — Notion 10/10 |

## Handoff Chat 2

1. A0141: manter `UNAVAILABLE_NODE` até ledger + mapper COLD real.
2. A0142: manter `UNAVAILABLE_NODE` até os dois lados do tradeoff serem implementáveis causalmente.
3. A0143: manter indisponível enquanto A0142 não for capability-eligible; depois, habilitar apenas recovery channels comprovados.
4. A0144: Iron's via `SPELL_POWER`; Ars via `SpellDamageEvent.Pre`; sem atributo paralelo.
5. A0145: implementar Ars; **não** implementar Iron's apenas com `setManaCost`.
6. A0146: max mana sem top-up; pools separados.
7. A0147: multiplicar regen nativa; nunca criar pulse/scheduler.
8. A0148: usar `CAST_TIME_REDUCTION` do Iron's e preservar semântica provider-native.
9. A0149: reservation→Pre→Post; consumir janela e iniciar lockout somente no Post.
10. A0150: manter `UNAVAILABLE_NODE` até interruption boundary + receipt/debit atômico + gates seguros.
11. Divergência de API que altere identidade, authority, gate ou semântica volta ao Chat 1; não redesenhar silenciosamente.

## Testes transversais Chat 3

- purchase fail-before-spend e legacy PP 0 para os quatro nodes indisponíveis;
- provider present/absent/removal/version drift;
- rank/respec/rules reload/login/dimensão/restart;
- max mana sem top-up e clamp descendente;
- regen zero-base;
- Ars mana cost gate→debit e Iron's negative regression;
- Iron's cast-time LONG/CONTINUOUS/INSTANT;
- A0149 janela/candidate/Pre/Post/rollback/dedup/persistence;
- resource typing MANA ≠ Source/Soul Energy/HP;
- A0144 dedup de outcome e exclusão de Ars healing/utility sem hook;
- A0142/A0143 lista dinâmica de nutrientes e ausência de polling/refund;
- A0150 interruption fail-closed, quantization, same-action receipt e no restart/refund approximation;
- multiplayer e dedicated-server smoke;
- JUnit, NeoForge GameTests, validators, build e JAR quando houver implementação.

## Estado final Chat 1

**DESIGN APROVADO / LOTE A0141–A0150 FECHADO PELO CHAT 1 NO DESIGN / NOTION 10/10 PERSISTIDO / DOSSIÊS 10/10 / RUNTIME NÃO ALTERADO / AGUARDANDO CHAT 2.**

Chat 1 não faz merge. A0151+ não pertence a este ciclo.
# Auditoria Chat 1 — A0151–A0160

**Intervalo:** A0151–A0160, exatamente 10 perks consecutivas.  
**Data:** 2026-09-01.  
**Base/freshness de abertura:** `main@0be05cb9cee8c34ff5ceb9091d2b5cb5d4c55328`.  
**Responsabilidade:** auditoria, design, integração e documentação. Chat 1 não implementa runtime, não declara implementação confirmada e não faz merge.

## Fontes obrigatórias

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`;
- Notion canônico A0151–A0160;
- `STATUS.md`, dossiês predecessores e código/API exato dos providers.

## Resultado executivo

| Código | Perk | Resultado Chat 1 | Estado esperado hoje |
|---|---|---|---|
| A0151 | Crítico Mágico | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: `DIRECT_MAGIC_OUTCOME_V1` ausente |
| A0152 | Potência Crítica Mágica | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` transitivo + action state/correlation ausentes |
| A0153 | Alcance Arcano | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: semantic range adapter ausente |
| A0154 | Duração Arcana | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: base-duration adapter ausente |
| A0155 | Área Arcana | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: radius/targets/MANA receipt incompletos |
| A0156 | Dano de Fogo I | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: DIRECT_MAGIC + FIRE classifier canônicos ausentes |
| A0157 | Dano de Fogo II | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: owned removable fire state ausente |
| A0158 | Resistência a Fogo I | DESIGN APROVADO | implementável no `LivingDamageEvent.Pre` do NeoForge |
| A0159 | Resistência a Fogo II | DESIGN APROVADO | implementável no mesmo bucket/boundary de A0158 |
| A0160 | Imbuimento de Fogo | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: same-outcome derived component ausente |

`UNAVAILABLE_NODE`: compra falha antes do gasto; allocation legado vale 0 PP para gates e permanece reembolsável/migrável.

## Notion

- fetch fresco: **10/10**;
- páginas A0151–A0160 atualizadas com gates/hooks/regras endurecidos;
- ranks, custos, topologia e identidade preservados;
- reconsulta SQL pós-escrita: **10/10 PASS**;
- A0160 foi relida após correção de formatação da fórmula `× (0,04 × rank)`.

## Providers e snapshots

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

- `SpellDamageEvent` é mutável antes de `hurt`;
- `SpellDamageSource` expõe spell/source;
- `SchoolRegistry.FIRE_RESOURCE` é `irons_spellbooks:fire`, ligado a `FIRE_MAGIC`;
- post-hit ignition termina em `igniteForTicks`, sem ownership da duração por fonte;
- nenhum hook genérico de semantic range/base duration/radius foi provado.

Consequências: future adapter para A0151/A0156 é plausível, mas não substitui `DIRECT_MAGIC_OUTCOME_V1`; A0157 continua fail-closed porque não pode retirar 40 ticks de um contador de fogo misto.

### Ars Nouveau 5.13.1

Snapshot `baileyholl/Ars-Nouveau@112920ff774831f204031da75b4c4e73d3765157`.

- `SpellCastEvent` expõe `Spell` + `SpellContext`;
- attachments de `SpellContext` permitem future correlation por action;
- `SpellDamageEvent.Pre` expõe caster/context e dano mutável;
- spell parts possuem registry identity para future `spell_key`;
- FIRE identities específicas existem, mas effects como Flare podem gerar cinders derivados;
- AoE não é radius universal: Flare usa AoE para quantidade de cinders.

### NeoForge 1.21.1

`LivingDamageEvent.Pre` expõe `DamageSource`, `getNewDamage()` e `setNewDamage(...)` antes da perda de vida. É boundary público suficiente para A0158/A0159 e para um único `DamageMitigationResolver`/bucket `RPG_FIRE_RESISTANCE`.

## Decisões críticas

### A0151/A0152 — crítica mágica

O `CriticalService` existente deve continuar único. Não se cria segunda RNG para spells. A0151 exige producer canônico DIRECT_MAGIC; A0152 exige também action correlation e estado crítico por ação. Ars fornece peças de contexto para adapter futuro; Iron's fica fail-closed quando a correlação de uma ação com todos os outcomes for ambígua.

### A0153/A0154 — geometria/tempo

Range e duration são semânticas provider-owned. Velocidade/lifetime não substituem range; remaining time não substitui base duration. Sem adapter explícito provider+spell/effect, os nodes são indisponíveis.

### A0155 — transação AoE

Radius ×1,12 só existe junto de distinct-target receipt e cobrança extra MANA após custo normal: `quantize_up(10% × mana_normal_final, provider_min_unit)`. Sem MANA suficiente, spell segue normal e a janela não é consumida. Nenhum lado do tradeoff pode ser implementado isoladamente.

### A0156/A0157 — FIRE direto e combustão

FIRE identity e directness são provas separadas. Namespace, cor, particle ou `isOnFire()` não classificam direct spell damage. A0157 exige ownership da combustão preexistente; o contador global de fogo não satisfaz isso.

### A0158/A0159 — mitigação única

As duas perks contribuem para um único `RPG_FIRE_RESISTANCE`; o resolver aplica o bucket uma vez. A0159 usa snapshot PRE-impacto estritamente `<50%`; exatamente 50% não ativa. Máximo local conjunto = 28%. Thermal/Volcanoes não entram por inferência.

### A0160 — componente derivado no mesmo outcome

A parcela FIRE deve ser componente do melee parent, baseada no weapon damage pré-target/pré-critical e herdando o crítico. Segundo DamageSource/event/roll é proibido. Sem `DERIVED_DAMAGE_COMPONENT_V1`, node indisponível.

## Capability delta dos quatro projetos próprios

Arquivo: `guides/projects/18-capability-delta-a0151-a0160.md`.

- RPG `0be05cb9…`: hardening Sonar/NBT do Volcanoes, sem semântica nova de perk.
- Volcanoes `eaddc323…`: sem delta; heat/geologia permanecem fora do FIRE damage por inferência.
- Enshrouded `bf97ea0e…`: Stage 08.02 classifica magia Iron's/Ars no reducer defensivo próprio; BRIDGE/authority nativa, não outcome FIRE/crítico do Skill Tree.
- Black Arcana `e573a0ed…`: sem capability nova pertinente ao lote.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | availability transitiva e fail-before-spend definidos |
| Integração global | PASS | CriticalService e mitigation bucket únicos; projects separados |
| Qualidade/identidade | PASS | DIRECT/FIRE/derived/thermal/range/radius/duration distintos |
| Topologia | PASS | ARCANE/VITALITY/MARTIAL bridges preservadas |
| Especializações | PASS | Mastery/family gates preservados; FIST não inferido |
| PT-BR | PASS | nomes/efeitos preservados |
| Notion | PASS | 10/10 fetch + 10/10 pós-escrita |
| NeoVitae | PASS/N/A | ausente/removido |
| Cobertura providers | PASS | Iron's, Ars, NeoForge e quatro projetos classificados |

## Checklist técnico — 18 critérios

| # | Critério | Resultado |
|---:|---|---|
| 1 | efeito real | PASS — hooks/contracts reais ou unavailable |
| 2 | provider-native first | PASS |
| 3 | sem mecânica inventada | PASS |
| 4 | fail-closed | PASS |
| 5 | fallback mantém identidade | PASS |
| 6 | Mastery por feitos | PASS/N/A — apenas gates; nenhum farm novo |
| 7 | anti-farm | PASS — windows/CD/action dedup bounded |
| 8 | atribuição causal | PASS — action/outcome/ownership exigidos |
| 9 | sem pipelines duplicados | PASS — CriticalService e mitigation únicos |
| 10 | custos reais | PASS — extra de A0155 é somente MANA tipada |
| 11 | sem geração gratuita | PASS |
| 12 | read-only correto | PASS — projects/providers sem authority indevida |
| 13 | versionamento | PASS — snapshots exatos |
| 14 | coerência estrutural | PASS — ranks/custos/prereqs preservados |
| 15 | dependências semânticas | PASS — transitividade explícita |
| 16 | sem sobreposição indevida | PASS — thermal/FIRE/magic/derived separados |
| 17 | implementável posteriormente | PASS — owner/order/fallback/testes definidos |
| 18 | verificação pós-escrita | PASS — Notion 10/10 |

## Handoff Chat 2

1. A0151–A0157 e A0160: preservar `UNAVAILABLE_NODE` enquanto os contracts nomeados estiverem ausentes.
2. Não criar producer DIRECT_MAGIC local por perk; deve ser infraestrutura canônica compartilhada.
3. A0152: action correlation + spell_key estáveis; no self-rearm.
4. A0153/A0154: adapters provider-specific; sem substitutions genéricas.
5. A0155: all-or-nothing radius + targets + MANA receipt; extra não recebe A0145.
6. A0157: nunca consumir contador global de fogo sem ownership.
7. A0158/A0159: implementar um único `DamageMitigationResolver`/`RPG_FIRE_RESISTANCE` no NeoForge Pre; sem segundo reducer.
8. A0160: nenhum segundo DamageSource; derived FIRE precisa compor no mesmo outcome.
9. Divergência que altere identidade, provider, gate, topologia, authority ou semântica volta ao Chat 1.

## Testes transversais Chat 3

- unavailable purchase fail-before-spend/legacy PP 0 para A0151–A0157/A0160;
- provider present/absent/version mismatch;
- root/action/outcome dedup e rollback;
- DIRECT vs derived/DoT/summon/fake-player negatives;
- window/CD/lifecycle de A0152/A0155/A0157/A0160;
- FIRE classification sem thermal/Volcanoes;
- A0158/A0159 ranks, threshold 50%, bucket único, respec/reload;
- multiplayer e dedicated-server smoke.

## Estado final do Chat 1

**DESIGN APROVADO / LOTE A0151–A0160 FECHADO PELO CHAT 1.**

Isso não é `CÓDIGO PRESENTE`, não é `IMPLEMENTAÇÃO CONFIRMADA` e não autoriza merge pelo Chat 1.
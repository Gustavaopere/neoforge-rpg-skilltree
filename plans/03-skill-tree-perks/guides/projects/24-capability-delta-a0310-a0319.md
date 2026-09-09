# Capability Delta — Chat 1 — A0310–A0319

Data da revalidação: 2026-09-05.

Escopo: exatamente A0310–A0319. Este arquivo satisfaz o gate bidirecional `perk → provider` e `provider → árvore`. Novo código em projeto próprio não vira perk/bridge automaticamente; cada capability recebe disposição explícita.

## 1. Checkpoints

Checkpoint anterior usado pelo lote A0300–A0309:

- RPG Skill Tree: `5213d068a91c95f45b9e119dec0be0636abc426d`;
- Volcanoes standalone: `298352973e941c2034c97465929dc67f6a0400e2`;
- Enshrouded: `03db94044b903628e51808de18a93134be9ad300`;
- Black Arcana: `6b77b5c0ec4f0ff4a8688bb105cef055860c061c`.

Fresh heads auditados para este lote:

- RPG Skill Tree: `8e33da13a9fe0347987d43addf888885d05be24b`;
- Volcanoes standalone: `298352973e941c2034c97465929dc67f6a0400e2` — tombstone, sem delta;
- Enshrouded: `67f4ab9095e69a922f265ffc477381f84c30ec69`;
- Black Arcana: `8c7ea474e17b4a0c80c6377482f08c8ebce1c58b`.

## 2. RPG Skill Tree — delta

### Capacidades/runtime de perks A0031–A0040

As mudanças desse intervalo implementam/corrigem contratos já pertencentes a perks existentes. Disposição: **COBERTA POR PERK EXISTENTE**. Não criam novas capabilities Nature/Agility para A0310–A0319.

### Compêndio Natural

Os lotes recentes de catálogo/biomas são conhecimento e apresentação do sistema canônico do Compêndio. Disposição para este delta: **COBERTO POR SISTEMA UNIVERSAL**. Ler/mostrar entrada/bioma não prova `NATURAL_TERRITORY_V1`, action receipt, morph category ou Mastery causal.

### Consolidação Volcanoes

A authority viva do Volcanoes foi consolidada no repositório RPG Skill Tree; o standalone permanece tombstone. Isso é **relocação de authority**, não criação automática de nova perk. Atmosphere/O2/respiração/pressão/geologia/volcanismo continuam pertencendo aos seus pipelines nativos e não equivalem a Nature territory, POISON ou stamina.

Disposição: **PROGRESSÃO/PIPELINE NATIVO AUTORITATIVO + BRIDGES JÁ EXISTENTES**. Cobertura provider→árvore permanece aquela documentada para Volcanoes; nenhum delta detectado exige 11ª perk neste ciclo.

### Efeito no lote

- A0310–A0317 continuam fechadas por A0183 + blockers locais.
- A0318 usa `AttributeNodeEffectRuntime`/MOVEMENT_SPEED já existente.
- A0319 continua sem receipt/precommit sprint-cost seguro.

## 3. Volcanoes standalone

`298352973...` → mesmo SHA. **SEM DELTA RELEVANTE** no standalone. O dossiê vivo no Notion registra que o standalone foi aposentado e que runtime/testes/evolução pertencem agora ao RPG Skill Tree.

Provider→árvore permanece explícito: O2/respiração/gases/pressão/protection/geologia não são reinterpretados como Nature, Poison ou AGILITY. Disposição: **NÃO DEVE SER INTEGRADO** a A0310–A0319 salvo boundary futuro específico, hoje inexistente.

## 4. Enshrouded — delta

`03db9404...` → `67f4ab90...`.

O avanço pertinente fecha Stage 08.05 Goety/Malum/Eidolon flavor como **NO-OP intencional** após value review:

- nenhum adapter Goety/Malum/Eidolon;
- nenhuma dependency/recipe/loot/runtime hook/progression bridge/conversion;
- authorities nativas desses providers permanecem separadas;
- removing providers não altera o loop Enshrouded.

Disposição provider→árvore: **NÃO DEVE SER INTEGRADO**. Não existe nova capability jogável para representar em perk e a decisão explícita é preservar ausência de coupling.

Novo baseline pode avançar a `67f4ab9095e69a922f265ffc477381f84c30ec69` sem gap.

## 5. Black Arcana — delta material

`6b77b5c0...` → `8c7ea474...` adiciona capabilities canônicas de Stage 07.

### 5.1 Blood & Curses

Capabilities implementadas:

- Blood Price — substituição parcial bounded de custo provider-owned por vida real, transacional;
- Equilibrium Rite — transferência bounded de vida;
- Sanguine Harvest — drain bounded liquidado pelo dano efetivamente entregue;
- Law of Recurrence — adaptação bounded por famílias semânticas de dano;
- Sympathetic Wound — mirrored damage com marker/recursion guard/provenance.

Authority: **Black Arcana continua owner dessas mecânicas**; Iron's mantém mana authority e Arcane Danger/backlash mantém seu pipeline.

Disposição:

- `PROGRESSÃO NATIVA AUTORITATIVA` para as cinco capabilities;
- o RPG já possui corredor BLOOD A0191–A0197, mas seus contratos exigem provider/action identity explícitos. A similaridade temática **não** cria integração;
- futura integração Black Arcana → família BLOOD: **BRIDGE candidata / SEM HOOK SEGURO no RPG até existir adapter semântico versionado** que preserve root action/outcome/cost provenance;
- nenhuma nova perk é criada fora do lote atual.

### 5.2 Souls & Death

Capabilities implementadas:

- `SoulAnchorLedger`/SavedData/runtime bounded e exactly-once;
- consumo atômico de anchor para prevenção de morte;
- Spirit Sight bounded/privacy-aware;
- Malum trace provider para identidades suportadas.

Disposição:

- Mortal Ledger/Soul Anchor: **PROGRESSÃO NATIVA AUTORITATIVA** — Black Arcana mantém safety/persistence. Não duplicar em RPG Skill Tree e não conflar com Mobstein/Goety/Eidolon;
- Spirit Sight: **PROGRESSÃO NATIVA AUTORITATIVA**; o bridge Malum pertence ao Black Arcana e não precisa de perk nominal no RPG;
- death → Malum spirit producer: **SEM HOOK SEGURO**. O próprio contrato documenta ausência de callback Malum 1.8.2 que prove quanto spirit provider-owned uma morte gerou. Fica fail-closed, sem synthetic spirit;
- Eidolon player-specific Soul Anchor unlock: **SEM HOOK SEGURO**. Callback de custom ritual não expõe caster/player identity; não inferir owner nem liberar anchor gratuitamente.

### 5.3 Relação com A0310–A0319

Nenhuma capability Black Arcana Stage 07 prova `NATURAL_FORM_STATE_V1`, Nature action, `NATURAL_TERRITORY_V1`, direct Nature healing ou sprint-cost receipt. Logo o delta não abre os nove nodes fail-closed do lote e não altera A0318.

Novo baseline pode avançar somente com as disposições acima registradas; todas as capabilities detectadas receberam decisão.

## 6. Mobstein externo

Mobstein 5.4.4 continua provider próprio de ressurreição corporal/experimentos/allies/estruturas/boss. Suas perks internas Attack/Health/Speed/Template não são nodes RPG. Nada neste lote autoriza bridge automática com Soul Anchor/Black Arcana, Goety ou Nature.

Disposição: **PROGRESSÃO NATIVA AUTORITATIVA / NÃO DEVE SER INTEGRADO** neste lote sem hook explícito.

## 7. Perk → provider

| Perk | Provider/authority principal | Boundary exigido | Estado |
|---|---|---|---|
| A0310 | RPG + morph provider | natural-form identity/category + offensive outcome | unavailable |
| A0311 | RPG + POISON provider | direct NATURE action ledger + application commit | unavailable |
| A0312 | RPG damage/POISON pipelines | direct hostile melee + derived outcome + classifier | unavailable |
| A0313 | RPG healing + companion/spatial providers | direct NATURE_HEALING + owner + same-space | unavailable |
| A0314 | RPG + control provider | NATURE credits + root commit + DR + boss/elite | unavailable |
| A0315 | RPG + morph provider | explicit primary natural category | unavailable |
| A0316 | RPG POISON/healing | shared reducers + actual prevented accounting | unavailable |
| A0317 | RPG + territory/spatial | `NATURAL_TERRITORY_V1` + pulses/assist | unavailable |
| A0318 | RPG attribute runtime + vanilla | `MOVEMENT_SPEED` stable modifier | implementável |
| A0319 | RPG + stamina/metabolic providers | causal sprint cost precommit | unavailable |

## 8. Provider → árvore — cobertura final

- RPG new A0031–A0040 capability → **COBERTA POR PERK EXISTENTE**.
- RPG Compêndio catalog capability → **COBERTO POR SISTEMA UNIVERSAL**.
- Volcanoes consolidated environmental capability → **PROGRESSÃO/PIPELINE NATIVO AUTORITATIVO**, sem nova perk neste delta.
- Enshrouded necromancy flavor review → **NÃO DEVE SER INTEGRADO** (NO-OP deliberado).
- Black Arcana Blood/Curses → **PROGRESSÃO NATIVA AUTORITATIVA**; future BLOOD bridge somente com adapter seguro.
- Black Arcana Soul Anchor/Spirit Sight → **PROGRESSÃO NATIVA AUTORITATIVA**.
- Malum death→spirit producer via Black Arcana → **SEM HOOK SEGURO**, fail-closed.
- Eidolon caster-specific anchor unlock → **SEM HOOK SEGURO**, fail-closed.
- Mobstein 5.4.4 → **PROGRESSÃO NATIVA AUTORITATIVA / NÃO DEVE SER INTEGRADO** ao lote.

## 9. Checkpoint

Todas as capabilities novas/alteradas detectadas receberam disposição e não há linha órfã na matriz. Para o próximo lote, usar como baseline reconciliado:

- RPG Skill Tree `8e33da13a9fe0347987d43addf888885d05be24b`;
- Volcanoes standalone `298352973e941c2034c97465929dc67f6a0400e2` (provenance/tombstone; runtime vivo no RPG);
- Enshrouded `67f4ab9095e69a922f265ffc477381f84c30ec69`;
- Black Arcana `8c7ea474e17b4a0c80c6377482f08c8ebce1c58b`.

Nenhuma disposição exige ampliar A0310–A0319 além de dez perks.
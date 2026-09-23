# Capability Delta — Chat 1 — A0300–A0309

Data da revalidação: 2026-09-02.

Escopo: exatamente A0300–A0309. Este arquivo satisfaz o gate bidirecional `perk → provider` e `provider → árvore` do protocolo Chat 1. Presença de mod, nome temático, VFX ou primitive genérica não é prova de capability.

## 1. Snapshots dos projetos próprios

| Projeto | Head auditado | Delta relevante | Disposição para A0300–A0309 |
|---|---|---|---|
| RPG Skill Tree | `5213d068a91c95f45b9e119dec0be0636abc426d` | Volcanoes consolidado; TreeUnlock canônico; attribute runtime idempotente; boss/reward primitives existentes | OWNER/CONSUMER canônico. Não contém os receipts/classifiers específicos faltantes deste lote. |
| Volcanoes standalone | `298352973e941c2034c97465929dc67f6a0400e2` | repo aposentado/tombstone após consolidação | PROVENANCE ONLY. Authority ambiental continua semanticamente Volcanoes no subsistema nativo, mas não fornece POISON/NATURE companion/territory semantics nem LIGHTNING locomotion. |
| Enshrouded | `03db94044b903628e51808de18a93134be9ad300` | Stage 08.04 JourneyMap/discovery owner-aware concluído | NÃO DEVE SER INTEGRADO ao lote. `ProgressionOwner` é authority de discovery Shroud, não ownership genérico de companions; JourneyMap é apresentação. |
| Black Arcana | `6b77b5c0ec4f0ff4a8688bb105cef055860c061c` | Stage 06 Rituals canônico | N/A para efeitos do lote. Ritual lifecycle/ledger não publica poison, nature-heal, natural-hostile, companion, ground/territory ou lightning movement receipts. |

## 2. Delta RPG Skill Tree relevante

### TreeUnlock substitui o resolver Specialist legado

`TreeUnlockDefinition`, `TreeUnlockResolver` e `TreeUnlockCatalog` existem na `main` e avaliam unlocks de forma data-driven. A Stage 04.01 já fornece projeção canônica de investimento. Consequência: A0300–A0309 **não** devem criar nem depender de um `SPECIALIST_GATE_RESOLVER_V1` paralelo.

- A0300 depende de `SPECIALIST_UNLOCK:LIGHTNING`; Gate C A0176 continua `UNAVAILABLE_NODE`.
- A0301–A0309 dependem de `SPECIALIST_UNLOCK:NATURE`; Gate C A0183 continua `UNAVAILABLE_NODE`.
- A0300 também depende de A0299, que depende do mesmo unlock LIGHTNING.
- A0302 depende de A0301; A0306/A0307/A0308 dependem de A0304; A0309 depende de A0307.

Resultado: **10/10 indisponíveis no snapshot atual por dependency closure**.

### Attribute modifier primitive

`AttributeNodeEffectRuntime` já usa modifier transitório por ID estável e reconciliação. Isso é primitive suficiente para a parcela de atributo de A0300 e A0307 quando seus contextos ficarem disponíveis. Não fornece por si só `RPG_STORM_BODY`, movement receipt, support surface ou `NATURAL_GROUND`.

### Boss primitives

Há infraestrutura de boss/reward (`BossIdentity` e policies associadas), porém isso não prova um classifier universal `isBoss(target)` em qualquer combat outcome. Pode alimentar adapters explícitos; não deve ser promovida automaticamente a classifier runtime de A0300/A0301/A0305/A0309.

### Tags NATURE ausentes

Busca na árvore atual não encontrou as tags planejadas:

- `rpgskilltree:natural_hostiles`;
- `rpgskilltree:natural_companion`;
- `rpgskilltree:natural_ground`;
- `rpgskilltree:natural_biomes`.

Logo A0305–A0308 não podem usar heurística por mob/block/biome/namespace para substituir os classifiers.

## 3. Perk → provider

| Perk | Providers/primitives pertinentes | Capability ausente/closure | Decisão |
|---|---|---|---|
| A0300 Passo do Trovão | RPG TreeUnlock/attribute runtime; Epic Fight/ParCool por adapter; Sable/Aeronautics context; Iron's/Ars LIGHTNING | Gate C A0176/A0299; voluntary locomotion/dodge receipt; target query; derived outcome; boss/PvP; CHARGED correlation | `UNAVAILABLE_NODE` |
| A0301 Toxicidade | NeoForge `MobEffectEvent.Added` source primitive; vanilla/Iron's/Ars/Toxony adapters | Gate C A0183; poison application identity, owner, pulse attribution, boss classifier | `UNAVAILABLE_NODE` |
| A0302 Virulência | provider-native poison application/renewal | A0183→A0301; precommit candidate-duration modifier | `UNAVAILABLE_NODE` |
| A0303 Inoculação | NeoForge `LivingDamageEvent.Pre` | A0183; canonical POISON component classifier/shared mitigation lane | `UNAVAILABLE_NODE` |
| A0304 Crescimento | NeoForge `LivingHealEvent` amount boundary; magic/heal providers by adapter | A0183; `HEALING_OUTCOME_V1` source/category | `UNAVAILABLE_NODE` |
| A0305 Predador Natural | data-driven entity classifier; boss primitive as adapter input | A0183; `NATURAL_HOSTILE` tag/classifier and boss/PvP mapping | `UNAVAILABLE_NODE` |
| A0306 Simbiose | Animal Husbandry/Wellness, vanilla tame, Ars familiars, Iron's summons by explicit adapters | A0183→A0304; natural companion classifier + unique owner receipt | `UNAVAILABLE_NODE` |
| A0307 Raiz Profunda | stable attribute modifier; Sable/Aeronautics coordinate context | A0183→A0304; support-context resolver + `NATURAL_GROUND` classifier | `UNAVAILABLE_NODE` |
| A0308 Seiva Arcana | provider-native MANA regen; Sable/Aeronautics context | A0183→A0304; `NATURAL_TERRITORY` + native MANA regen modifier | `UNAVAILABLE_NODE` |
| A0309 Espinhos | NeoForge `LivingDamageEvent.Post` + DamageSource primitives | A0183→A0307; hostile direct-melee receipt, derived outcome, boss/PvP | `UNAVAILABLE_NODE` |

## 4. Provider → árvore

### NeoForge / Minecraft

- `MobEffectEvent.Added`: evidence de lifecycle/source; não fornece application ledger nem mutable precommit duration.
- `LivingDamageEvent.Pre`: adequado para mutação de mitigação depois de classificação; não classifica POISON sozinho.
- `LivingDamageEvent.Post`: adequado para observar actual damage recebido; não prova sozinho hostilidade/direct-melee/root action.
- `LivingHealEvent`: amount boundary; não distingue NATURE/REGEN de food/lifesteal/potion.

Disposição: primitives úteis, sempre atrás de classifier/receipt canônico.

### Epic Fight / ParCool / Epic ParCool

Pertinentes a A0300 somente se adapter versionado expuser identidade server-authoritative de dodge/locomoção voluntária. Animação, velocidade ou displacement observados não bastam.

### Iron's / Ars / Toxony / Hexalia

Podem originar LIGHTNING, POISON ou heals apenas quando IDs/actions/schools concretos forem allowlisted e o adapter produzir receipt suficiente. Tema Nature, veneno visual ou escola mágica genérica não é classifier automático.

### Animal Husbandry / Animal Wellness / familiars/summons

Pertinentes a A0306 apenas via `NATURAL_COMPANION` + owner único. Tame status, equipe, proximidade, summon visual e owner inferido são insuficientes.

### Sable / Aeronautics

Transformam contexto/coordenadas de sublevel. Não decidem:

- movimento voluntário;
- support surface natural;
- biome/território natural;
- ownership de companion.

Em A0307/A0308 é proibido consultar parent Level como aproximação do contexto do sublevel.

### Tecnologia

Create, Oritech, FE e máquinas não são providers positivos do lote. Movimento por contraption é explicitamente excluído de A0300; energia tecnológica não é LIGHTNING magic nem MANA.

## 5. Projetos próprios — disposição detalhada

### Volcanoes

A consolidação para o RPG não muda ownership semântico de geologia/atmosfera/pressão. Nenhuma dessas grandezas equivale a `NATURAL_GROUND`, `NATURAL_TERRITORY`, POISON ou NATURE healing. Não integrar por proximidade temática.

### Enshrouded

Stage 08.04 introduziu discovery server-authoritative e projeção opcional para JourneyMap. Isso é útil como padrão arquitetural de authority/snapshot, mas **não** é capability reutilizável de gameplay neste lote. `ProgressionOwner` é owner de discovery Shroud, não owner universal de entidade.

### Black Arcana

Stage 06 possui activation IDs, transactional reserve/commit/refund e completion ledger. Esses conceitos podem inspirar formato de dedup, mas não devem ser importados como receipts do lote sem bridge explícita. Nenhum ritual gera os classifiers requeridos.

## 6. Fail-closed e futuro desbloqueio

Quando A0176/A0183 forem reabertas, Chat 2 deve reavaliar **cada blocker local**. A abertura do Specialist não materializa automaticamente:

- poison application/pulse ownership;
- poison duration precommit;
- POISON damage classification;
- healing category/source;
- natural hostile/companion/ground/territory classifiers;
- companion owner;
- MANA regen modifier;
- voluntary movement/dodge receipt;
- derived combat outcome;
- universal boss/PvP classification.

Até isso ocorrer, node indisponível deve falhar antes do gasto; allocation legado indisponível vale 0 PP em gates/thresholds e permanece reembolsável/migrável.

## 7. Conclusão

Nenhum delta dos quatro projetos próprios abre uma perk A0300–A0309 no snapshot 2026-09-02. O design está fechado, mas a disponibilidade operacional é 10/10 `UNAVAILABLE_NODE`. Nenhuma capability planejada foi promovida a runtime apenas por nome ou similaridade semântica.
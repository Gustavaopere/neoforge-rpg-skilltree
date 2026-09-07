# AUDITORIA — A0081–A0090

Data do design Chat 1: 2026-08-31  
Implementação Chat 2: 2026-09-01  
Escopo: **exatamente 10 perks consecutivas, A0081–A0090**.

## 1. Estado operacional do lote

- **Chat 1:** DESIGN APROVADO / LOTE FECHADO.
- **Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- A0081, A0084, A0085, A0086 e A0087 permanecem corretamente **CÓDIGO PRESENTE EM FAIL-CLOSED / UNAVAILABLE_NODE**.
- A0083 possui código funcional somente para o adapter auditado Iron's `1.21.1-3.16.3`; ausência, drift de versão/API e native lifesteal ambíguo falham fechado.
- Chat 2 **não executou** unit tests, GameTests, build NeoForge, dedicated-server smoke ou CI final e **não declara `IMPLEMENTAÇÃO CONFIRMADA`**.

## 2. Fontes obrigatórias do design

O Chat 1 aplicou integralmente os critérios de `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, o protocolo Chat 1, os guias de Gameplay/Sistemas, Magia, Tecnologia e Projetos Próprios.

Regra de cobertura aplicada nos dois sentidos:

1. `perk → provider`;
2. `provider → árvore`.

Provider sem relação causal foi excluído; provider pertinente sem hook seguro permaneceu fail-closed.

## 3. Gate de delta dos projetos próprios — evidência herdada do Chat 1

Baselines do lote anterior:

- RPG Skill Tree: `877120acf4f20a693e971282e8fca35bef72c6e7`.
- Volcanoes: `bbb273d61984e2c9bb84e8f8a56668ae7e315532`.
- Enshrouded: `391ea82203d30cb392a3397f92e2a3cbe7fb6128`.
- Black Arcana: `526d8196087c863e9df64051d5d39d88c3050856`.

Freshness registrada pelo Chat 1:

- RPG Skill Tree: `d20e7d666b627615f4af26dffb7c794b9a0b0fbd` — delta narrativa/história, sem capability nova pertinente;
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c` — hardening/release, sem mecânica nova do lote;
- Enshrouded: `391ea82203d30cb392a3397f92e2a3cbe7fb6128` — sem delta;
- Black Arcana: `710077da89da5eb4418d3ac676e148849727ff07` — hardening Backlash/snapshot; não cria sustain ofensivo.

O Chat 2 não refez a auditoria integral dos guias, conforme protocolo.

## 4. Resultado perk por perk após Chat 2

| Código | Perk | Estado Chat 2 | Decisão de runtime |
|---|---|---|---|
| A0081 | Recuperação de Combate | **CÓDIGO PRESENTE EM FAIL-CLOSED** | availability A0075→A0081; rank efetivo zero enquanto A0075 unavailable; recovery service e provenance hardened |
| A0082 | Vampirismo de Arma | **CÓDIGO PRESENTE** | physical sustain causal; Epic Fight root provider-native; vanilla/player_attack e launch receipts; Ignitium fail-closed por fonte |
| A0083 | Vampirismo Mágico | **CÓDIGO PRESENTE** | Iron's exact `1.21.1-3.16.3` + `SpellDamageSource` direto; native lifesteal ambíguo paga Skill Tree=0 |
| A0084 | Sifão Elemental | **CÓDIGO PRESENTE EM FAIL-CLOSED** | unavailable até mapa school/damage type→elemento aprovado/versionado |
| A0085 | Sifão de Dano Periódico | **CÓDIGO PRESENTE EM FAIL-CLOSED** | unavailable até owner+applicationId+pulseId provider-native |
| A0086 | Vampirismo Universal | **CÓDIGO PRESENTE EM FAIL-CLOSED** | unavailable transitivamente por A0085; 1% universal não classifica origem desconhecida |
| A0087 | Sede de Sangue | **CÓDIGO PRESENTE EM FAIL-CLOSED** | unavailable por A0075/A0081 + BodyProvider/healing-received geral ausentes; nenhum benefício parcial |
| A0088 | Constituição | **CÓDIGO PRESENTE** | binding `MAX_HEALTH` +2%/rank; preservação de razão de vida |
| A0089 | Couro Endurecido | **CÓDIGO PRESENTE** | binding `ARMOR` +2% relativo/rank; zero continua zero |
| A0090 | Têmpera | **CÓDIGO PRESENTE** | binding `ARMOR_TOUGHNESS` +2% relativo/rank; A0089≥2 preservado |

## 5. Notion — estado herdado do fechamento Chat 1

Fetch fresco do Chat 1: **10/10**.

Páginas mutadas no design: **7/10** — A0081, A0082, A0083, A0084, A0085, A0086, A0087. Re-fetch pós-escrita: **7/7 PASS**.

Sem mutação funcional: A0088, A0089, A0090.

O Chat 2 não alterou design/Notion.

## 6. Pipeline de sustain canônico após implementação

`SustainResolver` permanece o único bucket para vampirismo/sifão A0082–A0087:

- claim por root/pulse;
- maior coeficiente elegível;
- cap móvel 3% da vida máxima / 20 ticks;
- clipping por vida pré-impacto real e missing health;
- native correlation ambígua falha fechado;
- sem carry-over.

`A0081A0090SustainRuntime` centraliza chamadas físicas e direct-magic sem criar segundo bucket.

A0081 continua separada em `CombatRecoveryService`: snapshot diferido e até quatro parcelas; sua cura não entra no `SustainResolver`.

## 7. Availability / purchase / rank efetivo

`CombatPerkAvailabilityRuntime` foi estendido:

- A0081 — unavailable por A0075;
- A0083 — available somente quando Iron's está carregado exatamente em `1.21.1-3.16.3` e o runtime contém `SpellDamageSource.spell()` + `getLifestealPercent()`;
- A0084 — unavailable;
- A0085 — unavailable;
- A0086 — unavailable por A0085;
- A0087 — unavailable.

`NodePurchaseRequestProcessor` e `PlayerProgressionRuntime` herdados da PR #355 rejeitam unavailable nodes antes de custo/replay mutation. `A0081A0100RuntimeState.ranks(...)` agora usa `effectiveRanks` e limpa estado transitório quando a snapshot efetiva muda.

## 8. A0081/A0082 — provenance físico e deduplicação

### Epic Fight 21.17.3.1

`A0061A0080EpicFightHooks` agora publica `PhysicalHitReceipt` no PRE com:

- player/actor;
- `rootActionId` provider-native;
- vida pré-impacto;
- `ItemStack` usado.

Nenhuma cura é paga no PRE. O receipt é consumido apenas no `LivingDamageEvent.Post`, que fornece dano pós-mitigação final. POST zero/inválido descarta o handoff.

### Vanilla

Fallback melee só aceita `DamageSource` vanilla exato com `minecraft:player_attack`, direct entity e causing entity iguais ao jogador e arma não vazia. Source custom/provider não vira melee por main-hand heuristic.

### Projectiles

Bow/crossbow usam launch receipt server-side com janela bounded de 250 ms. Arrows siblings daquela janela compartilham a root, impedindo multiplicação de sustain por Multishot. Projectile sem launch receipt falha fechado.

## 9. A0082 — Simply Swords: Cataclysm / Ignitium

Upstream SimplyCataclysm confirma que Blazing Brand cura via `attacker.heal(...)` dentro do callback de hit. O provider também expõe o tag `simplycataclysm:ignitium_gear` com as famílias Ignitium.

Sem receipt final correlacionável da cura nativa, `A0081A0090SustainRuntime` marca roots cujo weapon stack pertence a esse tag como `NativeCorrelation.AMBIGUOUS`. Resultado: Skill Tree healing = 0 para a fonte, sem duplicar o provider.

Demais armas físicas comprovadas continuam elegíveis.

## 10. A0083 — Iron's direct magic

O upstream auditado declara `mod_version=1.21.1-3.16.3`. `SpellDamageSource` expõe `spell()`, `getLifestealPercent()` e semântica `isDirect()/indirect()`.

Implementados:

- `IronsSustainVersionContract` exact-match de versão;
- reflection gate de classe/métodos, evitando link obrigatório quando o mod está ausente;
- `IronsSustainEvents` com causing `ServerPlayer`, target hostil e source `isDirect()==true`;
- root por identidade do `DamageSource`;
- pagamento apenas no `LivingDamageEvent.Post`;
- `getLifestealPercent()>0` ou falha de leitura → `AMBIGUOUS` → Skill Tree=0.

Ars Nouveau permanece sem adapter; isso é fail-closed por provider e não invalida A0083 quando o Iron's exact contract está operacional.

## 11. A0084/A0085/A0086/A0087 — fail-closed preservado

### A0084

A fórmula existe, mas o Chat 1 não definiu o mapa canônico/versionado school/damage type→elemento. O Chat 2 não inventou esse design. Node unavailable.

### A0085

Nenhum provider auditado oferece owner persistente + `applicationId` + `pulseId` conforme contrato. Nenhum ledger heurístico foi criado. Node unavailable.

### A0086

A0085 unavailable mantém a keystone unavailable. O core `max(especializados, universal)` foi preservado; o 1% universal nunca classifica source desconhecida.

### A0087

`BloodThirstService` continua com `BodyProvider=null`; A0075/A0081 seguem indisponíveis; não há binding geral de +8% healing received. O node permanece completamente unavailable e nenhum 3% mínimo/+8%/tradeoff parcial é ativado.

## 12. A0088–A0090 — atributos vanilla

Bindings já existentes foram preservados:

- A0088 → `minecraft:generic.max_health`, `MULTIPLY_TOTAL`, +0,02/rank, com health-ratio preservation;
- A0089 → `minecraft:generic.armor`, `MULTIPLY_TOTAL`, +0,02/rank;
- A0090 → `minecraft:generic.armor_toughness`, `MULTIPLY_TOTAL`, +0,02/rank e dependência A0089≥2 no graph.

Nenhum segundo owner/NBT/STUN_ARMOR/Resistência Física foi criado.

## 13. Cobertura negativa preservada

- Black Arcana `ARCANE_BACKLASH`/`BLOOD_MAGIC_COST` não geram sustain.
- Enshrouded Shroud/Exposure/Madness não viram ofensiva do jogador.
- Volcanoes hazards/geologia não geram sustain.
- Mobstein/companions/summons não transferem autoria.
- Create/TFMG/machines/turrets/automation/fake-player/contraptions não herdam sustain do owner/construtor.

## 14. Anomalia de branch/PR do ciclo

O protocolo normal exige continuidade da PR do Chat 1, porém a PR Chat 1 **#310** já estava mergeada/fechada quando este Chat 2 iniciou. Além disso, A0081 depende da availability A0075 implementada na ainda aberta PR **#355**.

Para não duplicar a infraestrutura A0071–A0080 nem criar uma implementação incompatível, o Chat 2 criou:

- branch: `feat/chat2-a0081-a0090-implementation`;
- ponto de partida: `20499ec0df16b89454cac6a6c1e2b042e725b3c1`, HEAD da #355 no início deste ciclo;
- PR do lote deve permanecer **empilhada sobre `feat/chat2-a0071-a0080-implementation`** até #355 ser mergeada;
- depois, o Chat 3 deve retarget/reconciliar a PR A0081–A0090 com a `main` fresca, preservando somente o delta deste lote.

Isso é dependência operacional entre lotes, não autorização para o Chat 2 mergear #355.

## 15. Pendências obrigatórias para Chat 3

1. validar unavailable purchase/effective-rank zero de A0081/A0084/A0085/A0086/A0087;
2. validar A0083 provider absent, exact version, version mismatch e API mismatch;
3. testar direct vs indirect `SpellDamageSource` e native lifesteal >0 → Skill Tree=0;
4. testar vanilla melee vs source custom/ability e Epic Fight root provider-native;
5. testar bow/crossbow Multishot, damage zero/cancel e dedup cross-bridge;
6. testar Ignitium tag/native heal sem double-heal;
7. testar `SustainResolver`: max coefficient, claim-once, cap, overkill, missing health e multiplayer;
8. testar `CombatRecoveryService`: cap, snapshot, quatro parcelas, hostile interrupt, expiry e lifecycle;
9. testar A0088–A0090 composition/idempotência/rank loss/respec/relog/respawn;
10. executar unit tests, GameTests, integração, build NeoForge, dedicated-server smoke e CI aplicáveis;
11. retarget/reconciliar a PR empilhada após merge da #355;
12. só então declarar `IMPLEMENTAÇÃO CONFIRMADA`, obter CI GREEN e fazer merge.

## 16. Pontos que podem exigir retorno ao Chat 1 no futuro

- A0084: ativação exige mapa elemental canônico/versionado; Chat 2 não o define.
- A0085: primeiro provider application+pulse pode exigir decisão de design se a API real divergir do receipt especificado.
- A0087: se +8% healing received geral não puder ser implementado sem alterar escopo/semântica, devolver ao Chat 1; não estreitar para SustainResolver.

Nenhum desses pontos bloqueia o estado atual **fail-closed/unavailable** do lote.

## 17. Fechamento Chat 2

**A0081–A0090: CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3.**

Chat 2 para neste lote. A0091+ não deve ser iniciado neste ciclo.
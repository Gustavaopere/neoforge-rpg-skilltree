# Black Arcana — Dossiê Canônico para Perks

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db81dc8848c3ab317c2c70

**Snapshot auditado:** `Gustavaopere/Black-Arcana@07263ae9bad12eba6ed500992991faa36ad598b2`

O `plans/STATUS.md` auditado é anterior a commits recentes do Stage 05A. Por isso este documento separa o **status formal do estágio** da evidência de componentes que já chegaram à `main`. Um componente presente não autoriza promover o Stage 05A inteiro a “fechado”.

## 1. Identidade e autoridade

Black Arcana é a plataforma própria de magia sombria/perigosa do pack. O projeto possui:

- casting server-authoritative;
- recursos/custos;
- targeting/effect runtime;
- cooldown/persistência;
- conteúdo data-driven;
- bridges para ecossistemas mágicos;
- segurança de efeitos no mundo;
- Arcane Danger: Danger Profiles, Arcane Resistance, Corruption Resistance, Arcane Strain e Arcane Backlash;
- planejamento downstream para rituals, spell domains e progression/balance.

Perks não devem criar segundo cast pipeline, segundo ledger de dano ou conversões implícitas entre hazards de outros mods e Arcane Danger.

## 2. Foundation e Reference Catalog — IMPLEMENTADO E CANÔNICO

Stages 00 e 01 estão fechados. Eles estabelecem base técnica, catálogo de referência, identidade própria e decisões de balance/risco necessárias antes de importar/reinterpretar mecânicas inspiradas em outros sistemas.

## 3. Arcana Core — IMPLEMENTADO E CANÔNICO

Stage 02 fecha:

1. cast request/execution;
2. resource cost provider;
3. targeting/effect runtime;
4. cooldowns/persistence;
5. networking/data-driven content.

Todo cast deve terminar no pipeline canônico do Stage 02. Perks podem fornecer gates/modifiers apenas por extension points reais; elas não criam segunda reserva/commit de custo ou execução paralela.

Client input/presentation nunca se torna authority de gameplay.

## 4. Integration Layer — IMPLEMENTADO E CANÔNICO

Stage 03 fecha bridges com:

- Iron's Spellbooks;
- Ars Nouveau;
- Eidolon;
- Malum;
- RPG Skill Tree;
- fallbacks de dependência opcional.

### Regra para perks

O adapter traduz contexto ao Black Arcana. Ele não deve criar segunda progressão, segundo mana pool ou segundo cast engine para o provider externo.

A existência dessa integração geral com RPG não prova automaticamente que toda API futura de Arcane Danger ↔ RPG já está finalizada; cada subcontrato deve ser auditado pelo seu estágio.

## 5. World Safety — IMPLEMENTADO E CANÔNICO

Stage 04 fecha:

- world effect policy;
- temporary blocks/rollback;
- area budgets/chunk safety;
- PvP/boss protection.

Perks, rituais e spell domains não podem contornar essas políticas para produzir uma versão “mais forte” do mesmo world effect.

## 6. Casting & UX — IMPLEMENTADO PARCIALMENTE

O código de Stage 05 foi mergeado e os gates automatizados ficaram verdes, mas a matriz manual real de cliente/visual/input não estava encerrada no `STATUS.md` auditado.

Áreas do estágio:

- input/loadouts;
- radial wheel;
- contextual HUD;
- accessibility/client config.

Mesmo quando presentes em código, são sobretudo input/apresentação e não devem virar authority de damage/cost/hazard.

## 7. Arcane Danger — IMPLEMENTADO PARCIALMENTE / ESTÁGIO ATIVO

### 7.1 Danger Model — PRESENTE

`ArcaneDangerTier` distingue ao menos:

- `NORMAL`;
- `UNSTABLE`;
- `DANGEROUS`;
- `FORBIDDEN`;
- `CATASTROPHIC`.

`ArcanaCastId` continua sendo a identidade raiz do cast. Subordinate damage recebe IDs próprios. Hazard sessions são bounded e snapshots são imutáveis após ativação.

### 7.2 Arcane Resistance — PRESENTE

É um canal específico para suportar energia arcana perigosa. Não é automaticamente:

- vanilla armor;
- armor toughness;
- generic magic resistance;
- Enshrouded Shroud/Exposure;
- Volcanoes heat/respiration/toxicity/pressure.

Curva canônica inicial documentada:

`residual(R) = K / (K + clamp(R, 0, R_MAX))`

Defaults auditados:

- `K = 40`;
- `R_MAX = 240`;
- `R = 0` produz exatamente `1.0` de residual antes de floors/multipliers do profile.

Contribuições chegam por providers registrados, bounded e read-only. Provider que falha é isolado e contribui zero. O snapshot é capturado no hazard activation boundary e não muda com gear swap posterior.

### 7.3 Corruption Resistance — PRESENTE

Canal separado de Arcane Resistance.

A Corruption do Black Arcana é persistente por jogador e responde à alteração de longo prazo causada por magia proibida. Ela **não é** o Shroud/Exposure do Enshrouded.

Alta Arcane Resistance com baixa Corruption Resistance é uma build válida e vice-versa.

### 7.4 Arcane Strain — PRESENTE

Carga de curto/médio prazo criada por channeling repetido de magia perigosa.

- possui estado bounded;
- recuperação é lazy/event-driven quando possível;
- relog/restart não deve limpá-la de graça;
- não é uma segunda mana pool;
- só influencia preflight/backlash/corruption quando o danger profile declara essa interação.

### 7.5 Arcane Backlash — COMPONENTE IMPLEMENTADO E VERIFICADO

O Backlash usa causalidade própria e dano realmente confirmado:

- a base é health damage elegível confirmado pós-mitigação;
- não usa nominal spell damage, valor pre-armor ou client value;
- ledger bounded por root cast deduplica `ArcanaDamageInstanceId`;
- delayed damage mantém ownership do root cast quando permitido;
- canonical linear DANGEROUS/FORBIDDEN com zero Arcane Resistance produz backlash 1:1 sobre dano elegível confirmado;
- `ARCANE_BACKLASH` é damage family terminal.

Backlash **não** pode:

- recursar;
- contar como dano ofensivo normal;
- critar;
- lifestealar;
- conceder offensive Mastery;
- alimentar proc chains ofensivas;
- gerar sustain credit.

### 7.6 Equipment/Containment — IMPLEMENTADO PARCIALMENTE

O plano 05A.06 ainda não estava formalmente fechado, porém `main@07263ae...` contém a implementação mergeada de **equipment set bonuses** (#20):

- contrato bounded de bônus de set;
- registry;
- datapack definitions/reload;
- resolver cumulativo por thresholds;
- publicação por runtime;
- inclusão no standard equipment snapshot/provider;
- contributions diagnósticas de Arcane/Corruption Resistance;
- hot reload e cleanup.

Isso prova a infraestrutura de set bonus. Não prova automaticamente que toda emergency protection/transação, todos os itens de conteúdo ou todo o tuning de Equipment/Containment estejam fechados.

### 7.7 Curios, Profiles, Public API, RPG Hazard Integration, HUD e Hardening — NÃO DECLARAR FECHADOS SEM NOVA EVIDÊNCIA

No status formal auditado, 05A.07–05A.12 permanecem abertos. Commits posteriores podem fechar subpartes específicas; o Chat 1 deve registrar exatamente a subparte comprovada, nunca promover o conjunto inteiro sem evidência.

## 8. RPG Skill Tree integration do Arcane Danger — PLANEJADO NO 05A.10

O contrato proposto exige:

- RPG adapter registra contributions via interfaces públicas Black Arcana;
- Black Arcana não lê attachments internos do RPG;
- contributions são snapshotadas no hazard activation boundary;
- Mastery/attribute gates permanecem separados da resistência;
- satisfazer requisito de cast não implica immunity;
- integração ausente/incompatível contribui zero e não desativa Black Arcana;
- Backlash nunca gera Mastery.

### Regra operacional

Enquanto 05A.10 não estiver realmente fechado em `main`, uma perk que dependa dessa bridge pode ter o design documentado, mas o componente de implementação fica **pending/fail-closed**.

## 9. Rituals — PREPARATÓRIO / NÃO CANÔNICO

Stage 06 possui trabalho avançado preservado em branches/protótipos para:

- ritual contracts;
- Eidolon ritual bridge;
- Malum spirit components;
- grand rituals.

Esse trabalho antecede o freeze final do Stage 05A e precisa ressincronizar. Não usar como hook da `main`.

## 10. Spell Domains — PREPARATÓRIO / PLANEJADO

Domínios planejados incluem:

- blood/curses;
- souls/death;
- projection/arsenal;
- space/displacement;
- black flame;
- forbidden domains;
- familiars/divination.

São identidade futura de conteúdo, não uma lista de spells/hooks canônicos já disponíveis.

## 11. Progression & Balance — PREPARATÓRIO / PLANEJADO

Stage 08 prevê:

- knowledge progression;
- RPG mastery gates;
- balance budget;
- diminishing returns/caps;
- server config presets.

Não usar números/tuning desse estágio como contrato atual de perk.

## 12. Hardening & Release — PLANEJADO

Stage 09 não é provider de perks.

## 13. Relação com Enshrouded e Volcanoes

Separações obrigatórias:

- Black Arcana Corruption ≠ Enshrouded Shroud/Exposure;
- Arcane Resistance ≠ Enshrouded `MagicResistanceService` de mobs corrompidos;
- Volcanoes heat/respiration/toxicity/pressure não contribuem automaticamente para Arcane Resistance;
- Flame Passage/Flame Ward não fornecem Arcane Resistance automaticamente.

Qualquer relação futura exige provider/bridge explícito, directional e bounded.

## 14. Regras obrigatórias para perks

1. Provider-native first: Black Arcana conserva a authority do seu casting e Arcane Danger.
2. Não transformar armor/generic magic resistance/Shroud/pressão/temperatura em Arcane Resistance por inferência.
3. Resistências são snapshotadas no boundary do cast; alteração posterior de gear não retroage.
4. Backlash jamais concede Mastery, lifesteal ou proc ofensivo.
5. Não criar segundo `ArcanaCastId`, ledger ou reflection pipeline.
6. Corruption/Strain não podem ser abusadas por evento contínuo arbitrário se o contrato exige milestones/casts específicos.
7. Componente ainda planejado fica fail-closed/pending no Chat 2.

## 15. Fontes principais

- `plans/STATUS.md`
- `plans/00-foundation/`
- `plans/01-reference-catalog/`
- `plans/02-arcana-core/`
- `plans/03-integration-layer/`
- `plans/04-world-safety/`
- `plans/05-casting-ux/`
- `plans/05a-arcane-danger/`
- `plans/06-rituals/`
- `plans/07-spell-domains/`
- `plans/08-progression-balance/`
- `plans/09-hardening-release/`
- commit `07263ae9bad12eba6ed500992991faa36ad598b2` para equipment set bonuses.

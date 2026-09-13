# Enshrouded — Dossiê Canônico para Perks

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db813a91bec39bc08f2e53

**Snapshot auditado:** `Gustavaopere/Enshrouded@de145be720f7f500f55e060982693312ed7f7bc3`

Foundation, Shroud Field, Terrain Corruption, Exposure e Corrupted Ecology estão fechados. Flame Progression está 3/4 no snapshot. Lich/Story, Client Experience, Integrations e Hardening não são runtime canônico completo.

## 1. Identidade e autoridade

Enshrouded é o provider do **Shroud**: campo espacial persistente, corrupção/materialização de terreno, exposição e consequências do jogador, ecologia de criaturas corrompidas e progressão da Flame.

O Shroud é semanticamente diferente da `Corruption` do Black Arcana e não deve ser convertido automaticamente em recurso/resistência arcana.

## 2. Foundation — IMPLEMENTADO E CANÔNICO

A Foundation congela boundaries consumíveis pelos estágios posteriores, incluindo:

- `ShroudQuery` / severity / sample;
- `MutationAuthority`;
- `ProgressionOwner` e resolver;
- `FlamePassageQuery`;
- `FlameWardQuery`;
- classificação de dano mágico;
- boundary para provider de Lich.

### Regra para perks

Consumir boundaries públicos, não SavedData/classes internas. Quando unknown/failure em gate de passagem/proteção poderia abrir acesso indevido, o comportamento deve ser fail-closed.

## 3. Shroud Field — IMPLEMENTADO E CANÔNICO

O Stage 01 fecha o campo do Shroud:

- estado autoritativo dimension-local persistente;
- core lifecycle explícito/idempotente;
- expansão frontier-based e bounded;
- ausência de world/chunk scan e chunk forcing desnecessário;
- query indexada/determinística;
- client sync compacto/read-only;
- core seeding/discovery determinístico e sparse.

Sanctuary, quando o provider final existir, deve funcionar como overlay de proteção sobre o campo latente; proteção não significa apagar automaticamente a existência lógica do Shroud.

### Perks legítimas

- ler zona/severity via query real;
- reagir a entrada/saída como evento causal quando necessário;
- descoberta de core com identidade deduplicável;
- benefícios de exploração que não mutam o campo por fora da autoridade.

Não conceder Mastery por simplesmente permanecer na névoa.

## 4. Terrain Corruption — IMPLEMENTADO E CANÔNICO

O Stage 02 centraliza mutações de mundo:

- `DefaultMutationAuthority` é a authority de mutação/proteção;
- SAFE/AGGRESSIVE terrain tags;
- tri-state protection;
- safeguards para block entities;
- materialização data-driven, reversível e bounded;
- revalidação de `ShroudQuery` antes da mutação;
- corruption growths determinísticos/limitados;
- regression/purification determinística.

Stage 02 é o owner da transição lógica `DESTROYED → PURIFIED`; limpar blocos visuais não pode ressuscitar estado lógico ou criar segundo lifecycle.

### Regra para perks

Perk não pode mutar terreno Shroud por fora de `MutationAuthority` nem contornar claims/wards/protected-area decisions.

## 5. Exposure — IMPLEMENTADO E CANÔNICO

O Stage 03 possui estado de exposição versionado e server-authoritative:

- CLEAR recupera reserva;
- SHROUD drena reserva;
- delta de lag é bounded;
- reconnect/save-load não limpa reserva insegura;
- `DeadlyExposurePolicy` é seam estável;
- Madness pertence ao domínio canônico;
- Red Sludge faz parte do Stage 03 fechado.

### Deadly / Red Shroud

`FlameGatedDeadlyExposurePolicy` usa progressão da Flame como gate real:

- requirement padrão de passage level 2;
- fallback Foundation level 1;
- jogador subnivelado entra em emergency window bounded e continua rapid drain;
- `ProgressionOwnerResolver` + `FlamePassageQuery` são as dependências do gate;
- owner/query incerto falha fechado;
- edge-dancing não cria segundo timer/reset gratuito.

### Perks legítimas

Eficiência, recuperação, reserva ou mitigação só podem ser desenhadas se respeitarem a authority do Exposure Service. Uma perk genérica não pode bypassar o gate de Passage Level.

## 6. Corrupted Ecology — IMPLEMENTADO E CANÔNICO

O Stage 04 fecha:

- entity corruption;
- hostility/buffs;
- magic resistance;
- ecology visuals.

`MagicResistanceService` é o **único reducer** de resistência mágica do ecossistema. Adapters de outros mods mágicos, quando existirem, devem fornecer evidência/classificação, nunca aplicar a redução outra vez.

### Perks legítimas

Identificação/combate contra corrompidos e interação com resistência somente através de hooks canônicos. Evitar segundo reducer e double-dipping com o pipeline mágico de origem.

## 7. Flame Progression — IMPLEMENTADO PARCIALMENTE

### Flame State — IMPLEMENTADO E CANÔNICO

`FlameProgressionSavedData` persiste por owner:

- Flame Level;
- Passage Level;
- ritual IDs concluídos;
- schema/version.

Novo owner começa em Flame Level 1 / Passage Level 1. `FlamePassageService` implementa o boundary Foundation. Ritual IDs são estáveis; repetição é idempotente/rejeitada.

### Flame Altar — IMPLEMENTADO E CANÔNICO

`05.02` está fechado em `main`. O altar participa da progressão pela authority do Stage 05; não é legítimo detectar “qualquer bloco semelhante” por heurística e conceder avanço.

### Sanctuary — PLANEJADO / ABERTO

`05.03` permanece aberto no snapshot. `FlameWardQuery` existe como boundary Foundation, mas o provider final de Sanctuary não pode ser tratado como implementado antes do fechamento dessa task.

### Level 1 Ritual — IMPLEMENTADO E CANÔNICO

`05.04` está fechado. Rituais concluídos usam identidade persistente; perks/rewards não podem ser reaplicados por reconnect ou reexecução do mesmo ritual.

## 8. Lich & Story — PLANEJADO

O Stage 06 está aberto. A Foundation reserva boundary para provider de Lich, mas a presença desse seam não prova runtime de Lich.

Perks futuras podem ser planejadas, porém a implementação deve ficar pending/fail-closed até existir provider real em `main`.

## 9. Client Experience — PLANEJADO

Stage 07 contém planos para HUD, fog rendering, audio/particles e accessibility.

Esses recursos são apresentação. Mesmo após implementação, client visual/input não deve se tornar autoridade de gameplay.

## 10. Integrations — PLANEJADO COMO STAGE 08

O Stage 08 inteiro está aberto no snapshot.

### Ars Nouveau / Iron's

O plano prevê adapters de **classificação de dano**. `MagicResistanceService` continua sendo o único reducer. O plano cita Ars Nouveau 5.13.0 e Iron's Spells 3.16.3 no momento em que foi escrito; implementação futura deve reconciliar a versão real corrente da modlist.

### Goety / Malum / Eidolon

Planejados apenas como flavor/recipe/loot opcional quando houver valor concreto. A progressão necromântica desses providers não deve substituir ou sequestrar Flame Progression.

### Demais integrações planejadas

- Ars Zero;
- combat/claims/teams;
- JourneyMap;
- necromancy flavor.

Nenhuma deve ser listada como bridge operacional apenas porque há plano.

## 11. Hardening — PLANEJADO

Stage 09 não é provider de perk.

## 12. Relação com Black Arcana

Separações obrigatórias:

- Enshrouded Shroud/Exposure ≠ Black Arcana Corruption;
- Enshrouded mob Magic Resistance ≠ Black Arcana Arcane Resistance;
- Flame Passage / Flame Ward ≠ Arcane Resistance;
- Madness ≠ Arcane Strain.

Arcane Resistance e Corruption Resistance do Black Arcana não recebem Shroud automaticamente. Qualquer relação futura precisa de bridge/provider explícito.

## 13. Anti-abuso e deduplicação

1. Sem Mastery por tick de exposição.
2. Sem Mastery apenas por ficar dentro do Shroud.
3. Edge-dancing não pode farmar progresso.
4. Core discovery/ritual progress usam identidade persistente/one-time semantics quando aplicável.
5. Remover visual não apaga estado lógico do Shroud.
6. Não bypassar `MutationAuthority`, `FlamePassageQuery` ou protected-area/ward boundaries.
7. Failure/unknown de passage/protection falha fechado.

## 14. Fontes principais

- `plans/STATUS.md`
- `plans/00-foundation/`
- `plans/01-shroud-field/`
- `plans/02-terrain-corruption/`
- `plans/03-exposure/`
- `plans/04-corrupted-ecology/`
- `plans/05-flame-progression/`
- `plans/06-lich-story/`
- `plans/07-client-experience/`
- `plans/08-integrations/`
- `plans/09-hardening/`

# Auditoria Chat 1 — A0171–A0180

**Intervalo:** A0171–A0180, exatamente 10 perks consecutivas.  
**Auditoria inicial:** 2026-09-01.  
**Revalidação final:** 2026-09-02.  
**Correção pós-review P1:** 2026-09-02.  
**Branch/PR:** `docs/chat1-a0171-a0180-audit` / #375.  
**Responsabilidade:** auditoria, design, integração e documentação. Chat 1 não implementa runtime, não executa a bateria final da implementação e não faz merge na `main`.

## 1. Fontes obrigatórias e estado de entrada

Foram aplicados integralmente os anexos permanentes do projeto:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`.

A modlist atual foi conferida na File Library e no catálogo Notion antes da revalidação. A referência instalada permanece **573 entradas top-level incluindo NeoForge**, com NeoForge 1.21.1/21.1.248 e os providers versionados usados pelos dossiês.

As dez páginas A0171–A0180 do Catálogo Mestre foram buscadas frescas após as correções do V8. Após o review P1 da PR #375, A0179 e A0180 receberam uma correção adicional de `Dependências Obrigatórias`, `Gate`, `Hook`, `Fallback` e `Regra` para impedir compra no-op sem classifier NATURE ativo; ambas foram re-fetched e a persistência foi confirmada em 2026-09-02.

## 2. Resultado executivo final

| Código | Perk | Estado Chat 1 | Motivo principal |
|---|---|---|---|
| A0171 | Dano de Raio II | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam `DIRECT_MAGIC_OUTCOME_V1` + `LIGHTNING_CONSUMABLE_STATE_V1`; A0170 também está unavailable |
| A0172 | Resistência a Raio I | **DESIGN APROVADO / IMPLEMENTÁVEL** | NeoForge `LivingDamageEvent.Pre` + `IS_LIGHTNING`; adapter Iron's é extensão, não única fonte |
| A0173 | Resistência a Raio II | **DESIGN APROVADO / IMPLEMENTÁVEL** | mesmo resolver/bucket de A0172 + vida PRE-impacto estritamente `<50%` |
| A0174 | Imbuimento de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct magic outcome + `DERIVED_DAMAGE_COMPONENT_V1`; fallback somente de movimento é proibido |
| A0175 | Afinidade de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `MAGIC_THERMAL_PARCEL_V1`; Cold Sweat permanece authority térmica |
| A0176 | Maestria de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` transitivo | unlock/investment genérico existe, mas dependency A0175 está unavailable |
| A0177 | Dano de Natureza I | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `DIRECT_MAGIC_OUTCOME_V1` NATURE |
| A0178 | Dano de Natureza II | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct outcome + `NATURE_CONTROL_RECEIPT_V1` |
| A0179 | Resistência a Natureza I | **DESIGN APROVADO / IMPLEMENTÁVEL NO PACK ATUAL** | NeoForge Pre + classifier NATURE ativo; Iron's `nature_magic` satisfaz no pack atual; sem qualquer classifier ativo → `UNAVAILABLE_NODE` |
| A0180 | Resistência a Natureza II | **DESIGN APROVADO / IMPLEMENTÁVEL NO PACK ATUAL** | mesmo resolver/bucket/availability gate de A0179 + vida PRE-impacto estritamente `<50%` |

**Resultado no pack auditado:** 10/10 design fechado; 6/10 estruturalmente fail-closed `UNAVAILABLE_NODE`; 4/10 implementáveis pelo Chat 2 sem redesign. A0179/A0180 são condicionalmente disponíveis em runtime: se todos os classifiers NATURE allowlisted/version-compatible estiverem ausentes ou rejeitados, ambas fecham como `UNAVAILABLE_NODE` fail-before-spend.

## 3. Evidência técnica e authority dos providers

### 3.1 NeoForge / Minecraft

`LivingDamageEvent.Pre` é o boundary mutável server-side aprovado para a família defensiva. O design exige **um único** `ElementalDamageMitigationResolver`, com classificação antes da mitigação e uma única alteração do dano corrente.

Buckets separados:

- `RPG_LIGHTNING_RESISTANCE` — A0172 + A0173;
- `RPG_NATURE_RESISTANCE` — A0179 + A0180.

`DamageTypeTags.IS_LIGHTNING` é classifier válido para LIGHTNING defensivo e permanece disponível sem Iron's. Isso evita que A0172/A0173 virem nodes no-op quando a integração Iron's não estiver carregada.

Não existe tag vanilla NATURE genérica aprovada neste lote. Por isso a família A0179/A0180 exige um gate explícito derivado dos classifiers NATURE realmente ativos.

### 3.2 Iron's Spells 'n Spellbooks 3.16.3

Identidades provider-native comprovadas e preservadas:

- `irons_spellbooks:lightning_magic` / `LIGHTNING_MAGIC`;
- `irons_spellbooks:nature_magic` / `NATURE_MAGIC`.

Essas identidades permitem adapters defensivos exatos. Elas **não** provam automaticamente autoria, DIRECT vs derived ou root action de uma conjuração.

Exclusões:

- `CHARGED` é self-buff do caster e não satisfaz o estado consumível de alvo de A0171;
- RootSpell/RootEntity não viram `NATURE_CONTROL_RECEIPT_V1` automaticamente;
- nome, namespace, VFX ou tema não substituem classifier/receipt.

Iron's é integração opcional do RPG Skill Tree. Portanto `nature_magic` pode ser o classifier NATURE ativo do pack atual, mas a availability de A0179/A0180 não pode ser hardcoded em “Iron's sempre presente”.

### 3.3 Availability canônica da família NATURE defensiva

O mesmo registry/adapter set que o `ElementalDamageMitigationResolver` usa para classificar NATURE deve expor semanticamente:

`hasActiveNatureClassifier() == true|false`

Esse predicate não cria segunda authority. Ele é apenas uma projeção da fonte de verdade já usada pela classificação.

Regras:

- pelo menos um classifier NATURE allowlisted/version-compatible ativo → família disponível, sujeita aos demais gates;
- zero classifiers NATURE ativos → A0179/A0180 = `UNAVAILABLE_NODE`;
- compra falha antes do gasto;
- allocation legado indisponível conta 0 PP para gates/thresholds e permanece reembolsável/migrável;
- se Iron's estiver ausente, mas outro classifier NATURE explicitamente allowlisted estiver ativo, a família continua disponível;
- não manter flag manual, cache independente ou registry paralelo que possa divergir da classificação real.

### 3.4 Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1

Podem ser providers futuros de outcomes/states/classifiers elementais, mas somente por adapters versionados que provem causalidade, autoria, state identity e deduplicação. A presença de spell events/contextos não autoriza um producer DIRECT local por perk nem transforma automaticamente Ars em classifier NATURE defensivo.

### 3.5 Cold Sweat 2.4.2

Cold Sweat conserva authority única da temperatura corporal. A0175 exige uma parcela térmica causal de magia (`MAGIC_THERMAL_PARCEL_V1`) antes de qualquer transformação; evento global old/new de temperatura não prova qual ação causou a parcela.

### 3.6 Tecnologia

Create, Oritech e FE permanecem tecnologia. Eletricidade tecnológica não é magia LIGHTNING por tema e não pode abrir A0171/A0174/A0175 nem gerar Mastery elemental.

### 3.7 RPG Skill Tree

A infraestrutura de investment/unlock de A0176 continua canônica. Não criar `SpecialistGateResolver` paralelo. Gate C é a posse de A0176; Gates A/B continuam no pipeline de unlock/investment existente.

O `ElementalDamageMitigationResolver` é um contrato compartilhado **a implementar/estender pelo Chat 2**; ele não é tratado como runtime já existente apenas porque o nome consta do design.

## 4. Revalidação dos quatro projetos/sistemas próprios

O gate completo está em `guides/projects/20-capability-delta-a0171-a0180.md`.

Freshness final:

- RPG Skill Tree + runtime Volcanoes consolidado: `main@5213d068a91c95f45b9e119dec0be0636abc426d`;
- Volcanoes standalone tombstone/proveniência: `298352973e941c2034c97465929dc67f6a0400e2`, **não** fonte operacional futura;
- Enshrouded: `main@5fb7e1da39288cae82beaccf2a869e6ebbb099a3`;
- Black Arcana: `main@6b77b5c0ec4f0ff4a8688bb105cef055860c061c`.

### 4.1 Volcanoes pós-consolidação

Volcanoes agora é subsystem nativo do RPG Skill Tree. Geologia, atmosfera, respiração, pressão, proteção e integrações continuam semanticamente Volcanoes-owned. A consolidação não cria classifier LIGHTNING/NATURE e não converte ambiente em magia.

### 4.2 Enshrouded Stage 08.03

O delta atual fecha ownership/FTB Teams, claims/FTB Chunks/MineColonies e composição de protected-area authority. Epic Fight permanece presence/compat-only. Classificação: **NÃO DEVE SER INTEGRADO** às perks LIGHTNING/NATURE.

### 4.3 Black Arcana Stage 06 Rituals

Nova capability real detectada: lifecycle ritual canônico com `RitualActivationId`, session persistence, transactional components e exactly-once `RitualCompletionLedger`, além de bridges Eidolon/Malum.

Disposição provider→árvore:

- lifecycle/transaction ritual: **PROGRESSÃO NATIVA AUTORITATIVA** do Black Arcana;
- catálogo possui família Ritual Mastery fora deste lote (ex.: A0405/A0588): **COBERTA POR PERK EXISTENTE / BRIDGE** em nível conceitual;
- não há receipt/API RPG-facing estável comprovado para award hoje: **SEM HOOK SEGURO**;
- futura bridge deve deduplicar pela activation/completion identity Black Arcana e não creditar de novo o ritual via Eidolon/Malum subjacente;
- ação fica registrada para ciclo posterior que contenha/revise as perks ritualísticas; a regra de lote impede editar uma 11ª perk agora.

Stage 06 não publica nenhum dos contracts LIGHTNING/NATURE bloqueantes.

## 5. Capabilities transversais — estado final

A busca fresca na `main` atual manteve ausentes:

1. `DIRECT_MAGIC_OUTCOME_V1`;
2. `LIGHTNING_CONSUMABLE_STATE_V1`;
3. `DERIVED_DAMAGE_COMPONENT_V1`;
4. `MAGIC_THERMAL_PARCEL_V1`;
5. `NATURE_CONTROL_RECEIPT_V1`.

Consequência: A0171/A0174/A0175/A0177/A0178 continuam indisponíveis diretamente e A0176 continua indisponível por dependency closure. Nenhum fallback genérico é permitido.

Para as quatro resistências o boundary de dano é suficiente. A0172/A0173 possuem classifier LIGHTNING vanilla; A0179/A0180 exigem, adicionalmente, `hasActiveNatureClassifier()` verdadeiro no registry real.

## 6. Nove eixos obrigatórios de aprovação

### 6.1 Dependências, bloqueios e gates — PASS

- dependency closures explícitas;
- `UNAVAILABLE_NODE` falha antes do gasto;
- allocation legado indisponível vale 0 PP para gates/thresholds e permanece reembolsável/migrável;
- A0172 mantém rota legítima via Gateway VITALITY apesar do ofensivo unavailable;
- A0179 mantém rota topológica via Gateway VITALITY **somente quando** `hasActiveNatureClassifier()==true`;
- A0180 herda disponibilidade de A0179 e não cria segundo gate/provider registry;
- A0176 reutiliza o unlock canônico e não inventa segunda authority.

### 6.2 Integração global — PASS

- Cold Sweat mantém temperatura;
- Volcanoes mantém ambiente/geologia;
- tecnologia não vira LIGHTNING mágico;
- NATURE não é poison/fauna/planta/ambiente por inferência;
- Black Arcana rituals não viram outcome elemental;
- mod opcional ausente não deixa node NATURE defensivo comprável como no-op.

### 6.3 Qualidade e identidade — PASS

- A0171 é combo por state consumível, não +dano genérico;
- A0174 é bridge spellblade same-outcome + movimento inseparável;
- A0175 é afinidade térmica causal, distinta de resistência;
- A0176 é terminal/Gate C, sem pacote artificial;
- A0178 é combo por state/control receipt e spell diferente;
- resistências usam buckets estáveis e previsíveis;
- availability da família NATURE é derivada dos classifiers reais, sem mecanismo concorrente.

### 6.4 Ramificação, distância e topologia — PASS

LIGHTNING conclui ofensiva/defesa/bridge/afinidade/terminal; NATURE inicia potência/controle/defesa. Bridge PP tem contagem unitária; nenhuma rota de gateway substitui dependência semântica ofensiva ou o gate de disponibilidade do provider/classifier.

### 6.5 Especializações — PASS

A0176 satisfaz somente Gate C. Gate B usa investimento/região semântica canônica, não geometria client-side. Respec futuro deve reconciliar unlock pelo pipeline existente. Allocation NATURE defensivo indisponível vale 0 PP para esses cálculos até a família voltar a ficar disponível ou ser reembolsada/migrada.

### 6.6 PT-BR — PASS

Conteúdo player-facing permanece PT-BR; IDs, hooks, classes e contratos técnicos permanecem em inglês quando apropriado.

### 6.7 Preenchimento Notion — PASS

As dez páginas estão completas e persistidas. A0179/A0180 foram atualizadas novamente após o review P1 e re-fetched com o availability gate, fail-before-spend e tratamento de allocation legado persistidos.

### 6.8 NeoVitae — PASS

Nenhuma das dez perks depende de NeoVitae ou usa recurso/hook legado NeoVitae.

### 6.9 Cobertura modlist / provider→árvore — PASS

Iron's, Ars, Ars Elemental, Cold Sweat, NeoForge/Minecraft, tecnologia pertinente e os quatro projetos/sistemas próprios foram reavaliados. A capability ritual nova do Black Arcana recebeu disposição explícita, sem expandir o lote. A opcionalidade de Iron's foi incorporada ao gate NATURE defensivo após review.

## 7. Deduplicação, causalidade e anti-abuso

- A0172/A0173: um bucket `RPG_LIGHTNING_RESISTANCE`, uma passagem pelo resolver;
- A0179/A0180: um bucket `RPG_NATURE_RESISTANCE`, uma passagem;
- adapters classificam; não reduzem dano em listener paralelo;
- availability A0179/A0180 usa o mesmo registry dos classifiers; nenhuma segunda lista/flag;
- A0171 futuro: PRE-state + consumo/bônus atômico, um commit por outcome;
- A0174 futuro: componente derivado no mesmo outcome pai, sem segundo `hurt`, DamageSource, crítico, proc ou Mastery;
- A0175 futuro: uma transformação por thermal parcel/action, sem segunda temperatura;
- A0178 futuro: uma janela por state/alvo, sem farm por tick/reaplicação;
- fake player, summon, minion, automação e throughput não herdam autoria por inferência;
- nenhuma perk concede Mastery por tick, cooldown, duração, temperatura, FE ou equipamento.

## 8. Fallback e fail-closed

Fallback preserva a identidade:

- classifier opcional ausente → aquela fonte contribui zero;
- **se a família NATURE perder todos os classifiers compatíveis, A0179/A0180 inteiras ficam `UNAVAILABLE_NODE`**, em vez de permanecerem compráveis como no-op;
- classifier desconhecido/version mismatch individual → fonte inelegível;
- contract causal ausente → node unavailable, não bônus genérico;
- A0174 não pode ativar só o movimento;
- A0175 não pode reduzir temperatura global sem parcela causal;
- A0178 não pode usar Slowness/root visual como substituto universal;
- poison/planta/fauna/ambiente não podem ser promovidos a NATURE para manter availability artificialmente.

## 9. Testes especificados para o Chat 3

Os dossiês individuais são a fonte detalhada. O fechamento futuro deve cobrir, conforme aplicável:

1. purchase fail-before-spend e legacy unavailable = 0 PP;
2. ranks e caps próprios das quatro resistências;
3. classifiers LIGHTNING/NATURE positivos e negativos;
4. A0172/A0173 continuam classificando `IS_LIGHTNING` sem Iron's;
5. A0179/A0180 com Iron's 3.16.3 ativo e `nature_magic` válido;
6. **Iron's ausente + nenhum outro classifier NATURE ativo → A0179/A0180 `UNAVAILABLE_NODE`, compra falha antes do gasto**;
7. **adapter Iron's rejeitado por version mismatch + nenhum outro classifier NATURE ativo → mesmo fail-before-spend**;
8. segundo classifier NATURE allowlisted válido mantém a família disponível sem Iron's;
9. reload/transição available→unavailable preserva allocation legado, mas passa a contar 0 PP para gates/thresholds e permite respec;
10. reload/transição unavailable→available reabre a família sem duplicar allocation/efeito;
11. nenhuma conversão FE/Create/Oritech em magia LIGHTNING;
12. nenhum poison/thorn/planta/fauna/ambiente como NATURE genérico;
13. dedup dos buckets e ausência de reducer paralelo;
14. health PRE-impacto e boundary estrito `<50%` para A0173/A0180;
15. A0171 PRE-state, consumo atômico e CD 80t quando capability existir;
16. A0174 same-outcome/no-second-DamageSource e movimento all-or-nothing quando capability existir;
17. A0175 thermal parcel causal sem segunda temperature authority;
18. A0178 spell diferente, janela 120t, extensão ≤20t e CD 140t;
19. A0176 Gates A/B/C + respec/reconcile quando dependency closure abrir;
20. lifecycle/logout/reload/dimensão/multiplayer;
21. build NeoForge, GameTests/integrações, dedicated-server smoke e CI — responsabilidade do Chat 3.

## 10. STATUS.md e concorrência documental

As PRs predecessoras #361 (A0141–A0150), #366 (A0151–A0160) e #368 (A0161–A0170) permanecem abertas. Regravar integralmente `perks/STATUS.md` a partir da `main` atual pode apagar/reordenar estados ainda não integrados.

Por isso `audits/STATUS-A0171-A0180.md` continua sendo o tracker complementar autoritativo deste lote, explicitamente indexado. A futura reconciliação do `STATUS.md` raiz deve preservar A0141–A0180 em ordem e não pode transformar esse mecanismo de concorrência em perda de histórico.

## 11. Review P1 da PR #375 — resolução

O review automatizado apontou que, sem Iron's ou com adapter rejeitado, a rota Gateway VITALITY ainda permitiria comprar A0179 mesmo sem qualquer fonte NATURE classificada; A0180 herdaria o no-op.

**Finding aceito.** A correção preserva identidade e authority e não requer redesign de efeito/topologia:

- availability derivada do mesmo classifier registry;
- `hasActiveNatureClassifier()` obrigatório para aquisição/ativação A0179/A0180;
- zero classifiers → `UNAVAILABLE_NODE` fail-before-spend;
- allocation legado indisponível = 0 PP para gates/thresholds, reembolsável/migrável;
- ausência de Iron's não fecha a família se outro adapter NATURE válido estiver ativo;
- Notion A0179/A0180 e dossiês GitHub atualizados e persistência confirmada.

## 12. Handoff Chat 2

Chat 2 deve continuar **a mesma branch/PR #375**.

Implementar sem redesign:

- A0172/A0173 — bucket `RPG_LIGHTNING_RESISTANCE` no resolver elemental compartilhado;
- A0179/A0180 — bucket `RPG_NATURE_RESISTANCE` no mesmo resolver;
- o mesmo registry/adapter set do resolver deve derivar `hasActiveNatureClassifier()`;
- gates de compra/ativação A0179/A0180 devem usar esse predicate e falhar antes do gasto quando falso;
- preservar allocations legadas indisponíveis sem contá-las em gates/thresholds.

Preservar fail-closed estrutural:

- A0171, A0174, A0175, A0176, A0177 e A0178 enquanto blockers/dependencies continuarem ausentes.

Proibições:

- não criar direct-magic producer, state receipt, thermal parcel ou derived-damage pipeline local por perk;
- não criar Specialist resolver paralelo;
- não criar segundo registry/flag de availability NATURE;
- não converter tecnologia/ambiente/projeto próprio em elemento por tema;
- divergência que mude identidade, efeito, provider, gate, dependência, topologia, authority ou semântica essencial volta ao Chat 1.

## 13. Fechamento Chat 1

**A0171–A0180: DESIGN APROVADO / LOTE FECHADO PELO CHAT 1 — revalidado e corrigido após review P1 em 2026-09-02.**

- 10/10 dossiês completos;
- 10/10 páginas Notion persistidas e coerentes;
- A0179/A0180 re-fetched após a correção P1;
- 6/10 estruturalmente `UNAVAILABLE_NODE` fail-closed;
- 4/10 implementáveis no pack atual sem redesign;
- A0179/A0180 fecham dinamicamente como `UNAVAILABLE_NODE` se não houver classifier NATURE ativo;
- delta provider→árvore atualizado para a topologia Volcanoes consolidada, Enshrouded Stage 08.03 e Black Arcana Stage 06;
- testes futuros especificados, incluindo ausência/version mismatch de provider NATURE;
- nenhum runtime de perk implementado por este chat;
- nenhuma bateria final executada;
- nenhuma `IMPLEMENTAÇÃO CONFIRMADA` declarada;
- nenhuma merge na `main` realizada.

**Não iniciar A0181+ neste ciclo.**

# Auditoria Chat 1 — A0171–A0180

**Intervalo:** A0171–A0180, exatamente 10 perks consecutivas.  
**Auditoria inicial:** 2026-09-01.  
**Revalidação final:** 2026-09-02.  
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

As dez páginas A0171–A0180 do Catálogo Mestre foram buscadas frescas após as correções do V8. `Provider/Mods`, `Efeito`, `Gate`, `Hook`, `Fallback` e `Regra` persistem coerentes com os dossiês. Como o delta de 2026-09-02 não altera nenhum contrato dessas dez perks, não foi feita uma segunda escrita artificial no Notion apenas para mudar timestamps.

## 2. Resultado executivo final

| Código | Perk | Estado Chat 1 | Motivo principal |
|---|---|---|---|
| A0171 | Dano de Raio II | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam `DIRECT_MAGIC_OUTCOME_V1` + `LIGHTNING_CONSUMABLE_STATE_V1`; A0170 também está unavailable |
| A0172 | Resistência a Raio I | **DESIGN APROVADO / IMPLEMENTÁVEL** | NeoForge `LivingDamageEvent.Pre`, `IS_LIGHTNING` e adapter exato Iron's `lightning_magic` |
| A0173 | Resistência a Raio II | **DESIGN APROVADO / IMPLEMENTÁVEL** | mesmo resolver/bucket de A0172 + vida PRE-impacto estritamente `<50%` |
| A0174 | Imbuimento de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct magic outcome + `DERIVED_DAMAGE_COMPONENT_V1`; fallback somente de movimento é proibido |
| A0175 | Afinidade de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `MAGIC_THERMAL_PARCEL_V1`; Cold Sweat permanece authority térmica |
| A0176 | Maestria de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` transitivo | unlock/investment genérico existe, mas dependency A0175 está unavailable |
| A0177 | Dano de Natureza I | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `DIRECT_MAGIC_OUTCOME_V1` NATURE |
| A0178 | Dano de Natureza II | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct outcome + `NATURE_CONTROL_RECEIPT_V1` |
| A0179 | Resistência a Natureza I | **DESIGN APROVADO / IMPLEMENTÁVEL** | NeoForge Pre + adapter exato Iron's `nature_magic`; sem tag vanilla NATURE genérica |
| A0180 | Resistência a Natureza II | **DESIGN APROVADO / IMPLEMENTÁVEL** | mesmo resolver/bucket de A0179 + vida PRE-impacto estritamente `<50%` |

**Resultado:** 10/10 design fechado; 6/10 aprovadas em fail-closed `UNAVAILABLE_NODE`; 4/10 implementáveis pelo Chat 2 sem redesign.

## 3. Evidência técnica e authority dos providers

### 3.1 NeoForge / Minecraft

`LivingDamageEvent.Pre` é o boundary mutável server-side aprovado para a família defensiva. O design exige **um único** `ElementalDamageMitigationResolver`, com classificação antes da mitigação e uma única alteração do dano corrente.

Buckets separados:

- `RPG_LIGHTNING_RESISTANCE` — A0172 + A0173;
- `RPG_NATURE_RESISTANCE` — A0179 + A0180.

`DamageTypeTags.IS_LIGHTNING` é classifier válido para LIGHTNING defensivo. Não existe tag vanilla NATURE genérica aprovada neste lote.

### 3.2 Iron's Spells 'n Spellbooks 3.16.3

Identidades provider-native comprovadas e preservadas:

- `irons_spellbooks:lightning_magic` / `LIGHTNING_MAGIC`;
- `irons_spellbooks:nature_magic` / `NATURE_MAGIC`.

Essas identidades permitem adapters defensivos exatos. Elas **não** provam automaticamente autoria, DIRECT vs derived ou root action de uma conjuração.

Exclusões:

- `CHARGED` é self-buff do caster e não satisfaz o estado consumível de alvo de A0171;
- RootSpell/RootEntity não viram `NATURE_CONTROL_RECEIPT_V1` automaticamente;
- nome, namespace, VFX ou tema não substituem classifier/receipt.

### 3.3 Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1

Podem ser providers futuros de outcomes/states elementais, mas somente por adapters versionados que provem causalidade, autoria, state identity e deduplicação. A presença de spell events/contextos não autoriza um producer DIRECT local por perk.

### 3.4 Cold Sweat 2.4.2

Cold Sweat conserva authority única da temperatura corporal. A0175 exige uma parcela térmica causal de magia (`MAGIC_THERMAL_PARCEL_V1`) antes de qualquer transformação; evento global old/new de temperatura não prova qual ação causou a parcela.

### 3.5 Tecnologia

Create, Oritech e FE permanecem tecnologia. Eletricidade tecnológica não é magia LIGHTNING por tema e não pode abrir A0171/A0174/A0175 nem gerar Mastery elemental.

### 3.6 RPG Skill Tree

A infraestrutura de investment/unlock de A0176 continua canônica. Não criar `SpecialistGateResolver` paralelo. Gate C é a posse de A0176; Gates A/B continuam no pipeline de unlock/investment existente.

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

O `ElementalDamageMitigationResolver` é um contrato compartilhado **a implementar/estender pelo Chat 2** para as quatro resistências; não é tratado como capability já existente apenas por estar descrito no design.

## 6. Nove eixos obrigatórios de aprovação

### 6.1 Dependências, bloqueios e gates — PASS

- dependency closures explícitas;
- `UNAVAILABLE_NODE` falha antes do gasto;
- allocation legado indisponível vale 0 PP para gates/thresholds e permanece reembolsável/migrável;
- A0172 e A0179 mantêm rota legítima via Gateway VITALITY apesar dos ofensivos unavailable;
- A0176 reutiliza o unlock canônico e não inventa segunda authority.

### 6.2 Integração global — PASS

- Cold Sweat mantém temperatura;
- Volcanoes mantém ambiente/geologia;
- tecnologia não vira LIGHTNING mágico;
- NATURE não é poison/fauna/planta/ambiente por inferência;
- Black Arcana rituals não viram outcome elemental.

### 6.3 Qualidade e identidade — PASS

- A0171 é combo por state consumível, não +dano genérico;
- A0174 é bridge spellblade same-outcome + movimento inseparável;
- A0175 é afinidade térmica causal, distinta de resistência;
- A0176 é terminal/Gate C, sem pacote artificial;
- A0178 é combo por state/control receipt e spell diferente;
- resistências usam buckets estáveis e previsíveis.

### 6.4 Ramificação, distância e topologia — PASS

LIGHTNING conclui ofensiva/defesa/bridge/afinidade/terminal; NATURE inicia potência/controle/defesa. Bridge PP tem contagem unitária; nenhuma rota de gateway substitui dependência semântica ofensiva.

### 6.5 Especializações — PASS

A0176 satisfaz somente Gate C. Gate B usa investimento/região semântica canônica, não geometria client-side. Respec futuro deve reconciliar unlock pelo pipeline existente.

### 6.6 PT-BR — PASS

Conteúdo player-facing permanece PT-BR; IDs, hooks, classes e contratos técnicos permanecem em inglês quando apropriado.

### 6.7 Preenchimento Notion — PASS

As dez páginas estão completas e persistidas. A revalidação atual não encontrou divergência que justificasse nova alteração editorial.

### 6.8 NeoVitae — PASS

Nenhuma das dez perks depende de NeoVitae ou usa recurso/hook legado NeoVitae.

### 6.9 Cobertura modlist / provider→árvore — PASS

Iron's, Ars, Ars Elemental, Cold Sweat, NeoForge/Minecraft, tecnologia pertinente e os quatro projetos/sistemas próprios foram reavaliados. A capability ritual nova do Black Arcana recebeu disposição explícita, sem expandir o lote.

## 7. Deduplicação, causalidade e anti-abuso

- A0172/A0173: um bucket `RPG_LIGHTNING_RESISTANCE`, uma passagem pelo resolver;
- A0179/A0180: um bucket `RPG_NATURE_RESISTANCE`, uma passagem;
- adapters classificam; não reduzem dano em listener paralelo;
- A0171 futuro: PRE-state + consumo/bônus atômico, um commit por outcome;
- A0174 futuro: componente derivado no mesmo outcome pai, sem segundo `hurt`, DamageSource, crítico, proc ou Mastery;
- A0175 futuro: uma transformação por thermal parcel/action, sem segunda temperatura;
- A0178 futuro: uma janela por state/alvo, sem farm por tick/reaplicação;
- fake player, summon, minion, automação e throughput não herdam autoria por inferência;
- nenhuma perk concede Mastery por tick, cooldown, duração, temperatura, FE ou equipamento.

## 8. Fallback e fail-closed

Fallback preserva a identidade:

- adapter opcional ausente → aquela fonte contribui zero;
- classifier desconhecido/version mismatch → inelegível;
- contract causal ausente → node unavailable, não bônus genérico;
- A0174 não pode ativar só o movimento;
- A0175 não pode reduzir temperatura global sem parcela causal;
- A0178 não pode usar Slowness/root visual como substituto universal.

## 9. Testes especificados para o Chat 3

Os dossiês individuais são a fonte detalhada. O fechamento futuro deve cobrir, conforme aplicável:

1. purchase fail-before-spend e legacy unavailable = 0 PP;
2. ranks e caps próprios das quatro resistências;
3. classifiers LIGHTNING/NATURE positivos e negativos;
4. nenhuma conversão FE/Create/Oritech em magia LIGHTNING;
5. nenhum poison/thorn/planta/fauna/ambiente como NATURE genérico;
6. dedup dos buckets e ausência de reducer paralelo;
7. health PRE-impacto e boundary estrito `<50%` para A0173/A0180;
8. A0171 PRE-state, consumo atômico e CD 80t quando capability existir;
9. A0174 same-outcome/no-second-DamageSource e movimento all-or-nothing quando capability existir;
10. A0175 thermal parcel causal sem segunda temperature authority;
11. A0178 spell diferente, janela 120t, extensão ≤20t e CD 140t;
12. A0176 Gates A/B/C + respec/reconcile quando dependency closure abrir;
13. lifecycle/logout/reload/dimensão/multiplayer;
14. build NeoForge, GameTests/integrações, dedicated-server smoke e CI — responsabilidade do Chat 3.

## 10. STATUS.md e concorrência documental

As PRs predecessoras #361 (A0141–A0150), #366 (A0151–A0160) e #368 (A0161–A0170) permanecem abertas. Regravar integralmente `perks/STATUS.md` a partir da `main` atual pode apagar/reordenar estados ainda não integrados.

Por isso `audits/STATUS-A0171-A0180.md` continua sendo o tracker complementar autoritativo deste lote, explicitamente indexado. A futura reconciliação do `STATUS.md` raiz deve preservar A0141–A0180 em ordem e não pode transformar esse mecanismo de concorrência em perda de histórico.

## 11. Handoff Chat 2

Chat 2 deve continuar **a mesma branch/PR #375**.

Implementar sem redesign:

- A0172/A0173 — bucket `RPG_LIGHTNING_RESISTANCE` no resolver elemental compartilhado;
- A0179/A0180 — bucket `RPG_NATURE_RESISTANCE` no mesmo resolver.

Preservar fail-closed:

- A0171, A0174, A0175, A0176, A0177 e A0178 enquanto blockers/dependencies continuarem ausentes.

Proibições:

- não criar direct-magic producer, state receipt, thermal parcel ou derived-damage pipeline local por perk;
- não criar Specialist resolver paralelo;
- não converter tecnologia/ambiente/projeto próprio em elemento por tema;
- divergência que mude identidade, efeito, provider, gate, dependência, topologia, authority ou semântica essencial volta ao Chat 1.

## 12. Fechamento Chat 1

**A0171–A0180: DESIGN APROVADO / LOTE FECHADO PELO CHAT 1 — revalidado em 2026-09-02.**

- 10/10 dossiês completos;
- 10/10 páginas Notion persistidas e coerentes;
- 6/10 `UNAVAILABLE_NODE` fail-closed;
- 4/10 implementáveis sem redesign;
- delta provider→árvore atualizado para a topologia Volcanoes consolidada, Enshrouded Stage 08.03 e Black Arcana Stage 06;
- testes futuros especificados;
- nenhum runtime de perk implementado por este chat;
- nenhuma bateria final executada;
- nenhuma `IMPLEMENTAÇÃO CONFIRMADA` declarada;
- nenhuma merge na `main` realizada.

**Não iniciar A0181+ neste ciclo.**

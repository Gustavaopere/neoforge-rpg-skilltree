# Auditoria Chat 1 — A0171–A0180

**Intervalo:** A0171–A0180, exatamente 10 perks consecutivas.  
**Data:** 2026-09-01.  
**Base/freshness de abertura:** `main@c6677431a5c7cb2050ffc445834286a6001026fe`.  
**Branch:** `docs/chat1-a0171-a0180-audit`.  
**Responsabilidade:** auditoria/design/integração/documentação. Nenhum runtime de perk foi implementado, nenhuma bateria final de testes foi executada e nenhum merge pertence ao Chat 1.

## 1. Fontes obrigatórias

Foram usados como base operacional os anexos permanentes do projeto:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- protocolo `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`.

A modlist atual foi conferida na biblioteca/Notion antes da auditoria. O Catálogo Mestre do Notion foi buscado fresco para A0171–A0180; Provider/Mods, Gate, Hook, Fallback e Regra foram corrigidos onde necessário e as dez páginas foram reconsultadas após as escritas, confirmando persistência.

## 2. Resultado executivo

| Código | Perk | Estado Chat 1 | Motivo principal |
|---|---|---|---|
| A0171 | Dano de Raio II | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct magic outcome + estado elétrico consumível real; A0170 unavailable |
| A0172 | Resistência a Raio I | **DESIGN APROVADO / IMPLEMENTÁVEL** | NeoForge Pre + `IS_LIGHTNING` + Iron's `lightning_magic` exato |
| A0173 | Resistência a Raio II | **DESIGN APROVADO / IMPLEMENTÁVEL** | mesmo resolver/bucket + health PRE-impacto `<50%` |
| A0174 | Imbuimento de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct outcome + same-outcome derived component; A0170 unavailable |
| A0175 | Afinidade de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta parcela térmica LIGHTNING causal; dependency closure unavailable |
| A0176 | Maestria de Raio | DESIGN APROVADO / `UNAVAILABLE_NODE` transitivo | infra genérica de unlock existe; A0175 continua indisponível |
| A0177 | Dano de Natureza I | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `DIRECT_MAGIC_OUTCOME_V1` NATURE |
| A0178 | Dano de Natureza II | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct outcome + `NATURE_CONTROL_RECEIPT_V1` |
| A0179 | Resistência a Natureza I | **DESIGN APROVADO / IMPLEMENTÁVEL** | NeoForge Pre + classifier Iron's `nature_magic` exato |
| A0180 | Resistência a Natureza II | **DESIGN APROVADO / IMPLEMENTÁVEL** | mesmo resolver/bucket + health PRE-impacto `<50%` |

**Conclusão:** 10/10 têm design fechado. Seis permanecem aprovadas em fail-closed/`UNAVAILABLE_NODE`; quatro — A0172, A0173, A0179 e A0180 — possuem boundary suficiente para implementação pelo Chat 2 sem redesign.

## 3. Evidência técnica dos providers

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot/provider exato auditado no ciclo: 3.16.3.

Identidades relevantes comprovadas:

- LIGHTNING school/damage identity, incluindo `irons_spellbooks:lightning_magic` e tag provider `LIGHTNING_MAGIC`;
- NATURE school/damage identity, incluindo `irons_spellbooks:nature_magic` e tag provider `NATURE_MAGIC`.

Essas identidades são evidence válida para classifiers defensivos/adapters versionados. Elas **não** provam automaticamente autoria DIRECT do jogador.

`CHARGED` foi explicitamente excluído de A0171: é self-buff do caster, não um estado consumível pré-existente do alvo.

`RootSpell`/`RootEntity` não foram promovidos automaticamente a `NATURE_CONTROL_RECEIPT_V1`; A0178 exige adapter explícito que prove estado, instância, duração/cap e modificação segura.

### Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1

Ars possui eventos/contextos úteis de spell, mas o RPG Skill Tree ainda não possui um producer unificado que concentre autoria, action/outcome identity, DIRECT vs derived e deduplicação para A0171/A0174/A0177/A0178.

Ars Elemental permanece versionado pela build instalada 0.7.10.1; código de versões posteriores não é prova automática.

Nenhum estado elétrico/NATURE foi inventado a partir de nome, efeito visual ou Slowness.

### NeoForge/Minecraft 1.21.1

`LivingDamageEvent.Pre` fornece boundary mutável server-side para o dano corrente.

`DamageTypeTags.IS_LIGHTNING` existe e pode classificar dano LIGHTNING vanilla para A0172/A0173. Iron's `lightning_magic` entra por adapter exato. Não existe uma tag vanilla genérica NATURE aprovada para A0179/A0180; por isso o classifier NATURE atual é o adapter explícito do Iron's e futuros adapters versionados.

A família defensiva deve usar um único `ElementalDamageMitigationResolver`, com buckets separados por identidade:

- `RPG_LIGHTNING_RESISTANCE`;
- `RPG_NATURE_RESISTANCE`.

Adapters classificam; não reduzem o dano por conta própria.

### Cold Sweat 2.4.2

Cold Sweat conserva authority única da temperatura corporal.

A0175 exige `MAGIC_THERMAL_PARCEL_V1` antes da mutação canônica, porque o evento global old/new de temperatura não fornece origem/action suficiente para repartir a parcela causal de uma spell LIGHTNING. LIGHTNING sem thermal parcel explícito permanece termicamente neutro.

### RPG Skill Tree current main

`TreeUnlockResolver` e `TreeUnlockDefinition` já existem e avaliam domain scores, tags e Mastery. O Stage 04.01 — canonical investment projection, PR #365 — foi mergeado e fornece projeção canônica de investimento a partir de ProgressionState/metadata explícita, fail-closed quando metadata/revisions divergem.

Portanto A0176 **não** precisa de um resolver Specialist paralelo. Ela permanece indisponível por dependency closure em A0175.

### Tecnologia

Create/Oritech/FE continuam tecnologia. Eletricidade tecnológica não se converte em magia LIGHTNING por semântica temática e não alimenta A0171/A0174/A0175/A0176.

## 4. Gate de delta dos quatro projetos próprios

O arquivo `guides/projects/20-capability-delta-a0171-a0180.md` registra o gate completo.

Heads usados:

- RPG Skill Tree: `c6677431a5c7cb2050ffc445834286a6001026fe`;
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`;
- Enshrouded: `5671114c361be8cbb6fd2dadafdaa05f27d1fe2c`;
- Black Arcana: `d8fb667cc5954d5811dacbbef4da1053fa296581`.

O delta relevante do RPG foi integrado apenas onde comprovado: Stage 04.01 reforça a infraestrutura de A0176. A consolidação Volcanoes #369 não cria capability LIGHTNING/NATURE. Os demais projetos não introduzem capability nova pertinente a este lote.

Nenhuma capability ficou sem disposição e nenhum projeto foi convertido em elemento por tema.

## 5. Nove eixos obrigatórios de aprovação

### 5.1 Dependências, bloqueios e gates — PASS

- dependency closures foram explicitadas;
- `UNAVAILABLE_NODE` falha antes do gasto e vale 0 PP enquanto indisponível;
- A0172 e A0179 preservam rota legítima via Gateway VITALITY mesmo com A0170/A0177 unavailable;
- A0176 distingue infraestrutura genérica existente de dependency closure;
- Mastery textual não é tratada como producer implícito.

### 5.2 Integração global corpo/sobrevivência/magia/tecnologia — PASS

- Cold Sweat continua único owner térmico;
- LIGHTNING mágico e eletricidade tecnológica permanecem separados;
- NATURE mágico não equivale a poison/fauna/planta/ambiente;
- SURVIVAL fornece topologia, não redefine classifier elemental.

### 5.3 Qualidade e identidade — PASS

- A0171 é combo de estado elétrico consumível real, não +dano genérico;
- A0174 é spellblade LIGHTNING same-outcome + movimento transitório, all-or-nothing;
- A0175 é afinidade térmica causal, não resistência;
- A0176 é terminal/Gate C sem pacote artificial de poder;
- A0178 exige combo spell diferente + estado NATURE provider-native;
- resistências usam buckets únicos e previsíveis.

### 5.4 Topologia/distância — PASS

- A0171–A0176 concluem o corredor LIGHTNING em ofensiva, defesa, bridge, afinidade e terminal;
- A0177–A0180 iniciam NATURE com potência, controle e ponte defensiva;
- bridges preservam política de contagem única de PP;
- acesso por Gateway não substitui dependências semânticas ofensivas.

### 5.5 Especializações — PASS

- A0176 satisfaz somente Gate C;
- Gates A/B/C continuam simultâneos;
- Gate B usa região semântica, não geometria/UI;
- `TreeUnlockResolver`/Stage 04.01 são reutilizados, sem second authority;
- respec futuro deve preservar invariantes de especialização.

### 5.6 PT-BR — PASS

Nomes e efeitos de gameplay permanecem em PT-BR; IDs/contracts técnicos permanecem em inglês.

### 5.7 Preenchimento Notion — PASS

As dez páginas foram auditadas/corrigidas e re-fetched após a escrita. Persistência confirmada.

### 5.8 NeoVitae — PASS

Nenhuma perk usa NeoVitae, recurso NeoVitae ou fallback NeoVitae.

### 5.9 Cobertura modlist/provider→árvore — PASS

Iron's, Ars, Ars Elemental, Cold Sweat, NeoForge/Minecraft, tecnologia pertinente e os quatro projetos próprios foram considerados. Integrações sem hook seguro ficaram fail-closed.

## 6. Contratos transversais

### `DIRECT_MAGIC_OUTCOME_V1`

Continua ausente. Necessário para A0171/A0174/A0177/A0178 e dependências ofensivas transitivas. Não criar producer local por perk.

### `LIGHTNING_CONSUMABLE_STATE_V1`

Continua ausente. A0171 exige estado real pré-existente e consumível do alvo. Iron's `CHARGED` não satisfaz o contrato.

### `DERIVED_DAMAGE_COMPONENT_V1`

Continua ausente. A0174 deve anexar componente LIGHTNING ao mesmo melee outcome, sem segundo DamageSource/crítico/proc/Mastery.

### `MAGIC_THERMAL_PARCEL_V1`

Continua ausente. A0175 só transforma thermal parcel explícita antes do Cold Sweat.

### `NATURE_CONTROL_RECEIPT_V1`

Continua ausente. A0178 não cria root/snare universal e não usa Slowness como substituto.

### `ElementalDamageMitigationResolver`

É infraestrutura compartilhada implementável para A0172/A0173/A0179/A0180, compatível com a direção já aprovada para FIRE/ICE em lotes predecessores. Um resolver, buckets separados, uma mutação por root/evento.

### Unlock de A0176

`TreeUnlockResolver`/`TreeUnlockDefinition` e Stage 04.01 estão presentes; não são blocker ausente. A0176 continua unavailable enquanto A0175 estiver unavailable.

## 7. Mastery e anti-abuso

- Lightning/Nature Mastery só contam por progressão canônica causal;
- não conceder Mastery por tick, duração de estado, cooldown, FE, temperatura, poison, vegetação ou equipamento;
- componentes derivados de A0174 não geram Mastery;
- reaplicações/ticks de controle A0178 não geram Mastery;
- fake player/summon/minion/automação não recebem autoria por inferência.

## 8. Deduplicação e causalidade

- A0172/A0173: um bucket `RPG_LIGHTNING_RESISTANCE`, uma passagem;
- A0179/A0180: um bucket `RPG_NATURE_RESISTANCE`, uma passagem;
- A0171 futuro: consumo+bônus atômicos, um commit por outcome;
- A0174 futuro: um componente derivado no outcome pai;
- A0175 futuro: uma transformação por thermal parcel/action;
- A0178 futuro: uma janela por state/alvo e um commit por segundo outcome elegível;
- A0176 não gera side effect de combate.

## 9. Testes especificados para o Chat 3

Cada dossiê contém matriz específica. O fechamento futuro deve cobrir, conforme aplicável:

1. purchase fail-before-spend e legacy unavailable = 0 PP;
2. classifiers LIGHTNING/NATURE positivos e negativos;
3. ausência de conversão FE/Create/Oritech em magia LIGHTNING;
4. ausência de poison/fauna/planta/ambiente como NATURE;
5. dedup dos buckets defensivos;
6. health PRE-impacto e boundary estrito `<50%` de A0173/A0180;
7. A0171 PRE-state, atomic consumption e CD 80t quando capability existir;
8. A0174 same-outcome/no-second-DamageSource e movimento all-or-nothing quando capability existir;
9. A0175 thermal parcel causal sem segunda temperature authority;
10. A0178 spell diferente, janela 120t, extensão ≤20t e CD 140t;
11. A0176 Gate A/B/C + respec seguro quando dependency closure abrir;
12. lifecycle/logout/reload/dimensão/multiplayer;
13. build NeoForge, GameTests/integrações, dedicated-server smoke e CI — responsabilidade do Chat 3.

## 10. STATUS.md e concorrência documental

As PRs #361 (A0141–A0150), #366 (A0151–A0160) e #368 (A0161–A0170) permanecem abertas e não mergeadas. Regravar a tabela histórica inteira de `perks/STATUS.md` a partir da `main` atual criaria risco de apagar/reordenar estados ainda não integrados.

Por isso o estado operacional completo deste lote está em `audits/STATUS-A0171-A0180.md`, neste audit e nos dez dossiês. A reconciliação futura do `STATUS.md` raiz deve preservar A0141–A0180 em ordem quando a cadeia predecessor for integrada/rebaseada.

## 11. Fechamento Chat 1

**A0171–A0180: DESIGN APROVADO / LOTE FECHADO PELO CHAT 1.**

- 10/10 dossiês materializados;
- 10/10 páginas Notion auditadas/corrigidas e revalidadas;
- 6/10 aprovadas em fail-closed `UNAVAILABLE_NODE`;
- 4/10 implementáveis: A0172, A0173, A0179 e A0180;
- delta dos quatro projetos próprios fechado sem capability órfã;
- provider→árvore e perk→provider fechados;
- testes do Chat 3 especificados;
- nenhum runtime implementado;
- nenhuma bateria final de implementação executada;
- nenhum merge realizado.

**Handoff:** Chat 2 deve continuar a mesma branch/PR deste lote, implementar exatamente os quatro contratos disponíveis e preservar fail-closed nas seis restantes. Divergência que altere identidade, efeito, provider, gate, dependência, topologia, authority ou semântica essencial volta ao Chat 1.

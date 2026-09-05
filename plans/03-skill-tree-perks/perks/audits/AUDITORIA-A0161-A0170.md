# Auditoria Chat 1 — A0161–A0170

**Intervalo:** A0161–A0170, exatamente 10 perks consecutivas.  
**Data:** 2026-09-01.  
**Base/freshness de abertura:** `main@0be05cb9cee8c34ff5ceb9091d2b5cb5d4c55328`.  
**Branch:** `docs/chat1-a0161-a0170-audit`.  
**Responsabilidade:** design/auditoria/documentação. Nenhum runtime de perk foi implementado, nenhuma bateria final de testes foi executada e nenhum merge pertence ao Chat 1.

## 1. Fontes obrigatórias

Antes do fechamento foram usados integralmente os critérios e os quatro guias consolidados anexados ao projeto:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`.

O catálogo mestre do Notion foi buscado fresco para as dez perks. Campos de provider, Gate, Hook, Fallback e Regra foram corrigidos onde o catálogo tratava nomes de contracts futuros como se fossem runtime já existente. Todas as dez páginas alteradas foram buscadas novamente após a escrita e a persistência foi confirmada.

## 2. Resultado executivo

| Código | Perk | Estado Chat 1 | Motivo principal |
|---|---|---|---|
| A0161 | Afinidade de Fogo | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta parcela térmica causal `MAGIC_THERMAL_PARCEL_V1`; A0156 também unavailable |
| A0162 | Maestria de Fogo | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `SPECIALIST_GATE_V1`; dependency closure unavailable |
| A0163 | Dano de Gelo I | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `DIRECT_MAGIC_OUTCOME_V1` |
| A0164 | Dano de Gelo II | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct outcome + `ICE_CONTROL_RECEIPT_V1` |
| A0165 | Resistência a Gelo I | **DESIGN APROVADO / IMPLEMENTÁVEL** | NeoForge pre-damage + classifiers ICE seguros |
| A0166 | Resistência a Gelo II | **DESIGN APROVADO / IMPLEMENTÁVEL** | mesmo resolver/bucket de A0165 + health pré-impacto |
| A0167 | Imbuimento de Gelo | DESIGN APROVADO / `UNAVAILABLE_NODE` | faltam direct outcome + `DERIVED_DAMAGE_COMPONENT_V1` |
| A0168 | Afinidade de Gelo | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta parcela térmica causal; A0163 unavailable |
| A0169 | Maestria de Gelo | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `SPECIALIST_GATE_V1`; dependency closure unavailable |
| A0170 | Dano de Raio I | DESIGN APROVADO / `UNAVAILABLE_NODE` | falta `DIRECT_MAGIC_OUTCOME_V1` LIGHTNING |

**Conclusão:** 10/10 possuem design fechado. Oito são aprovadas em fail-closed/`UNAVAILABLE_NODE`; duas, A0165 e A0166, têm boundary suficiente para implementação pelo Chat 2 sem redesign.

## 3. Evidência técnica dos providers

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot upstream auditado: `e4056af90302d37eb1739f5ff05020b020e6e252`.

A versão possui identidades nativas explícitas:

- `SchoolRegistry.FIRE_RESOURCE = irons_spellbooks:fire`;
- `SchoolRegistry.ICE_RESOURCE = irons_spellbooks:ice`;
- `SchoolRegistry.LIGHTNING_RESOURCE = irons_spellbooks:lightning`;
- DamageTypes correspondentes `fire_magic`, `ice_magic`, `lightning_magic`.

Isso é evidência válida para classifiers versionados. Não prova autoria DIRECT automaticamente. O `ConeOfColdProjectile` auditado aplica dano através do DamageSource do spell; esse código não comprova um sistema genérico `CHILL`/buildup compartilhado, logo A0164/A0167 não podem fabricar um estado universal de Calafrio.

### Ars Nouveau 5.13.1

Snapshot upstream auditado: `112920ff774831f204031da75b4c4e73d3765157`.

`SpellDamageEvent.Pre/Post` expõe caster, target, DamageSource, damage e `SpellContext`. Isso é um seam provider-local útil, mas o RPG Skill Tree ainda precisa correlacionar ações, classificar DIRECT vs derived e deduplicar tudo em um producer canônico. Nenhuma perk deste lote deve instalar uma segunda pipeline ad hoc em cima do evento.

### Ars Elemental 0.7.10.1

A build instalada é 0.7.10.1. O branch 1.21 upstream já anuncia 0.7.10.2; portanto código exclusivo do head posterior não foi usado como prova automática da build instalada. Integrações permanecem versionadas/fail-closed quando o contrato exato não foi provado.

### Cold Sweat 2.4.2

Cold Sweat é autoridade única da temperatura corporal. O `TemperatureChangedEvent` público auditado transporta entidade, `Trait`, temperatura anterior e temperatura nova; não contém identidade da ação/origem causal. Assim, A0161/A0168 não podem calcular “a parcela desta magia” observando a mudança global de BODY depois do fato.

O design exige `MAGIC_THERMAL_PARCEL_V1` antes da mutação canônica, sem criar segunda temperatura e sem escrever diretamente BODY/CORE/RATE.

### NeoForge/Minecraft 1.21.1

`LivingDamageEvent.Pre` fornece boundary server-side mutável para o dano corrente. `DamageTypeTags.IS_FREEZING` existe em 1.21.1 e cobre `minecraft:freeze`.

Isso fecha a parte defensiva ICE:

- um único `ElementalDamageMitigationResolver`;
- classifier vanilla `IS_FREEZING`;
- Iron's `ice_magic` por adapter exato;
- A0165/A0166 somando no mesmo `RPG_ICE_RESISTANCE`;
- uma única aplicação do bucket por evento/root.

### Tecnologia

Create/Oritech/FE continuam tecnologia. Presença de energia, choque ou eletricidade não prova `DIRECT_MAGIC_OUTCOME_V1` LIGHTNING. A0170 não pode converter automaticamente dano/energia tecnológica em magia de Raio.

## 4. Gate de delta dos projetos próprios

O arquivo `guides/projects/19-capability-delta-a0161-a0170.md` registra o gate completo.

Heads frescos usados:

- RPG Skill Tree: `0be05cb9cee8c34ff5ceb9091d2b5cb5d4c55328` — sem delta relevante;
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c` — sem delta relevante;
- Enshrouded: `5671114c361be8cbb6fd2dadafdaa05f27d1fe2c` — mudança documental de proveniência do RED/Stage 08.02, `NÃO DEVE SER INTEGRADO` como capability;
- Black Arcana: `d8fb667cc5954d5811dacbbef4da1053fa296581` — mudança documental de sequencing/final validation, `NÃO DEVE SER INTEGRADO` como capability.

Nenhuma capacidade nova ficou sem decisão. Nenhum projeto próprio foi convertido em FIRE/ICE/LIGHTNING por afinidade temática.

## 5. Nove eixos obrigatórios de aprovação

### 5.1 Dependências, bloqueios e gates — PASS

- Dependency closures foram explicitadas.
- `UNAVAILABLE_NODE` falha antes do gasto e vale 0 PP em gates enquanto indisponível.
- A0165 preserva rota legítima por Gateway VITALITY mesmo com A0163 unavailable.
- Terminais A0162/A0169 não fingem resolver Specialist sem `SPECIALIST_GATE_V1`.
- Mastery textual não é tratada como producer já implementado.

### 5.2 Integração global corpo/sobrevivência/magia/tecnologia — PASS

- Cold Sweat continua único owner térmico.
- Dano ICE/FIRE, resistência, afinidade térmica e CHILL foram separados.
- Create/Oritech/FE não viram LIGHTNING mágico.
- Volcanoes, Enshrouded e Black Arcana mantêm authorities próprias.

### 5.3 Qualidade e identidade — PASS

- A0161/A0168 são keystones de afinidade térmica causal, não resistência genérica.
- A0164 é rotação spell→spell condicionada a controle provider-native.
- A0167 é bridge spellblade same-outcome, não apenas `+dano` arbitrário.
- A0165/A0166 formam família defensiva clara e deduplicada.
- Terminais A0162/A0169 são gates estruturais sem pacote de poder artificial.

### 5.4 Topologia/distância — PASS

- FIRE conclui o corredor exterior em A0161/A0162.
- ICE abre em A0163, possui ramo ofensivo/controle, ponte defensiva, ponte martial, afinidade e terminal.
- A0170 inicia LIGHTNING sem antecipar perks posteriores.
- Bridge PP possui política de contagem única; nenhum border hopping gratuito.

### 5.5 Especializações — PASS

- A0162/A0169 são terminais exteriores e somente Gate C futuro.
- Specialist exige Gates A/B/C, com Gate B de 100 PP válidos na região semântica correspondente.
- Respec seguro foi especificado para impedir quebra do gate enquanto houver perk interna possuída.
- Providers mágicos não viram classes automaticamente.

### 5.6 PT-BR — PASS

Nomes/efeitos permanecem em PT-BR. IDs/classes/contracts técnicos (`DIRECT_MAGIC_OUTCOME_V1`, `RPG_ICE_RESISTANCE`, etc.) permanecem em inglês por serem implementação.

### 5.7 Preenchimento Notion — PASS

As dez páginas tiveram os campos pertinentes corrigidos. Persistência confirmada por re-fetch individual após a atualização.

### 5.8 NeoVitae — PASS

Nenhuma das dez perks usa NeoVitae, recursos NeoVitae ou fallback NeoVitae.

### 5.9 Cobertura de modlist/provider→árvore — PASS

- Iron's, Ars, Ars Elemental, Cold Sweat, NeoForge/Minecraft e stack tecnológico pertinente foram considerados.
- Projetos próprios tiveram delta fresco.
- Integrações sem hook seguro ficaram fail-closed.
- Não foi inventada bridge temática.

## 6. Contratos transversais que o Chat 2 deve respeitar

### `DIRECT_MAGIC_OUTCOME_V1`

Continua ausente. A0163/A0164/A0167/A0170 e dependências transitivas não podem criar producers locais concorrentes. Quando existir, deve concentrar action/outcome identity, ownership, DIRECT vs derived e classificação elementar versionada.

### `MAGIC_THERMAL_PARCEL_V1`

Continua ausente. A0161/A0168 exigem delta térmico causal antes da aplicação pelo Cold Sweat. Evento global old/new não substitui o receipt.

### `ICE_CONTROL_RECEIPT_V1`

Continua ausente. Não criar CHILL universal. Providers só entram quando expuserem state/buildup real e mutável de forma segura.

### `DERIVED_DAMAGE_COMPONENT_V1`

Continua ausente. A0167 não pode simular same-outcome damage com segundo `hurt`/DamageSource. O componente futuro deve herdar autoria/crítico do pai e não reentrar em pipelines de proc/Mastery.

### `SPECIALIST_GATE_V1`

Continua ausente. A0162/A0169 permanecem terminais não adquiríveis enquanto a dependency closure também estiver indisponível. Não usar a geometria da UI como gate.

### `ElementalDamageMitigationResolver`

É infraestrutura implementável a partir deste lote para A0165/A0166. Deve existir uma vez e ser extensível a elementos posteriores sem múltiplos listeners/reducers por perk. Adapters classificam; o resolver muta dano uma vez.

## 7. Mastery e anti-abuso

- Fire/Ice Mastery mencionadas nos gates só contam quando houver progressão canônica com autoria causal.
- Nenhuma perk deste lote concede Mastery por tick, tempo conectado, temperatura, efeito persistente, equipamento, throughput ou exposição contínua.
- A0167 não concede Mastery pelo componente derivado.
- A0164 não concede Mastery por reaplicações/ticks de controle.
- Automação, fake player, summon/minion não recebem autoria por inferência.

## 8. Deduplicação e causalidade

- A0165/A0166: um bucket `RPG_ICE_RESISTANCE`, uma passagem por evento.
- A0163/A0170 futuros: uma aplicação por `outcome_id` direto.
- A0164 futuro: uma janela/consumo por action/outcome; cooldown inicia somente no commit elegível.
- A0167 futuro: um componente derivado no outcome pai; sem segundo DamageSource/crítico/proc.
- A0161/A0168 futuros: uma transformação por `action_id`/parcela térmica.
- Terminais não geram side effects de combate.

## 9. Testes especificados para o Chat 3

Cada dossiê contém sua matriz específica. O fechamento futuro deve cobrir, conforme aplicável:

1. availability e purchase fail-before-spend;
2. legacy unavailable rank = 0 PP e reembolso/migração;
3. provider present/absent/version mismatch;
4. classifiers ICE positivos/negativos;
5. dedup/idempotência do bucket de mitigação;
6. boundary de <50% HP de A0166;
7. ausência de inferência térmica global em afinidades;
8. ausência de CHILL universal/fabricado;
9. same-outcome/no-second-DamageSource quando A0167 se tornar disponível;
10. Specialist Gates A/B/C + safe respec quando o resolver existir;
11. lifecycle/logout/reload/dimensão/multiplayer;
12. build NeoForge, GameTests/integrações aplicáveis, dedicated-server smoke e CI — responsabilidade do Chat 3, não executados aqui.

## 10. STATUS.md e concorrência documental

As PRs documentais predecessoras #361 (A0141–A0150) e #366 (A0151–A0160) permanecem abertas e independentes sobre `main`. Reescrever a tabela histórica inteira de `perks/STATUS.md` nesta branch criaria alto risco de conflito e de apagar estados ainda não integrados.

Por isso, o estado operacional completo deste lote é registrado canonicamente neste audit + nos dez dossiês e será também registrado em um tracker de status do lote na mesma pasta. A reconciliação da tabela raiz `STATUS.md` deve acontecer quando a cadeia predecessor for integrada/rebaseada, preservando os três lotes. Esta exceção é deliberada e não significa que o estado do lote esteja indefinido.

## 11. Fechamento Chat 1

**A0161–A0170: DESIGN APROVADO / LOTE FECHADO PELO CHAT 1.**

- 10/10 dossiês materializados.
- 10/10 páginas Notion corrigidas/revalidadas.
- 8/10 aprovadas em fail-closed `UNAVAILABLE_NODE`.
- 2/10, A0165 e A0166, implementáveis no boundary NeoForge.
- Delta dos quatro projetos próprios fechado sem lacunas.
- Nenhum runtime de perk implementado.
- Nenhuma bateria final de testes executada.
- Nenhum merge realizado pelo Chat 1.

O Chat 2 deve continuar **esta mesma branch/PR**, implementar somente o que o contrato permite e preservar fail-closed nas capabilities ausentes.
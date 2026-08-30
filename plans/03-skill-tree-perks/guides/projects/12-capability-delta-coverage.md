# Matriz de Cobertura e Delta de Capacidades — Projetos Próprios

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db81ee99a7e465e8333251

Os projetos próprios estão em desenvolvimento contínuo. O Chat 1 não pode limitar a auditoria às capacidades já citadas pelas perks existentes ou pelo snapshot anterior. Todo avanço de `main` deve ser examinado por **delta de capacidades**.

## 1. Objetivo

Impedir que uma mecânica nova ou alterada de RPG Skill Tree, Volcanoes, Enshrouded ou Black Arcana fique fora da árvore apenas porque nenhuma perk antiga já a menciona.

## 2. Gate obrigatório por lote

Antes de fechar cada lote exato de 10 perks, o Chat 1 deve:

1. fazer fetch fresco de `main` e `plans/STATUS.md` dos quatro projetos próprios;
2. comparar o SHA atual com o último SHA reconciliado registrado nos dossiês/reconciliação;
3. registrar `SEM DELTA` quando não houver avanço relevante;
4. quando houver avanço, identificar os planos/subsistemas alterados e inspecionar apenas essa superfície, usando código/testes/CI quando necessário;
5. extrair toda capacidade jogável nova ou semanticamente alterada: recurso, resistência, estado, hazard, ação, equipamento, query, serviço, progressão, diagnóstico, milestone ou boundary público;
6. lançar cada capacidade na matriz de cobertura, **mesmo que nenhuma perk atual a cite**;
7. não declarar a cobertura global do lote fechada enquanto toda capacidade pertinente não tiver classificação explícita;
8. **não avançar o baseline do projeto enquanto qualquer capacidade detectada naquele delta estiver sem decisão, ação e fail-closed quando aplicável**.

Isso cria dois sentidos obrigatórios de auditoria:

- **perk → provider:** a perk usa authority/boundary correto;
- **provider → árvore:** a árvore não deixou capacidades novas/alteradas pertinentes sem avaliação.

## 3. Classificação obrigatória de cobertura

Cada capacidade detectada deve receber exatamente uma decisão principal:

- **COBERTA POR PERK EXISTENTE** — indicar código(s) e por que a cobertura é suficiente;
- **PERK PRÓPRIA** — precisa de node dedicado;
- **ESPECIALIZAÇÃO** — pertence a uma progressão temática própria;
- **BRIDGE** — deve integrar uma perk/sistema existente por boundary explícito;
- **COBERTO POR SISTEMA UNIVERSAL** — não precisa de perk nominal do provider;
- **PROGRESSÃO NATIVA AUTORITATIVA** — o provider já possui progressão própria e a árvore não deve duplicá-la;
- **SEM HOOK SEGURO** — design pode ser registrado, mas implementação fica pending/fail-closed;
- **NÃO DEVE SER INTEGRADO** — a relação seria artificial, redundante ou quebraria authority.

Cobertura `PARCIAL` ou `AUSENTE` deve gerar lacuna explícita antes do lote ser declarado fechado.

## 4. Matriz mínima por capacidade

| Projeto | Capacidade | Estado real | SHA/evidência | Cobertura atual | Decisão | Perk(s)/ação | Hook/boundary | Fail-closed |
|---|---|---|---|---|---|---|---|---|
| Exemplo | Nova resistência/recurso | CANÔNICO/PARCIAL/PLANEJADO | SHA + plano/código | Completa/Parcial/Ausente | categoria da seção 3 | código da perk ou backlog de design | API/query/provider real | comportamento sem hook |

## 5. Capacidades que devem ser rastreadas explicitamente

Estas listas são **gatilhos de cobertura**, não ordens para criar uma perk para cada item.

### Volcanoes

- composição atmosférica, fração e pressão parcial de O₂;
- respiração, oferta/consumo de ar e tolerância a hipóxia somente por hook real;
- canais específicos de gases, fumaça/particulados e proteção/filtragem;
- pressão atmosférica e hidrostática;
- equipamentos/volumes de proteção;
- geologia, depósitos, prospecção, tectônica, terremotos, vulcanismo e geotermia;
- novas integrações ambientais/tecnológicas que cheguem à `main`.

A existência de O₂ no provider **não** autoriza inventar `+X% oxigênio`. O design pode terminar em tolerância à hipóxia, eficiência de proteção/respiração, diagnóstico ou outra interação apenas quando houver extension point real.

### Black Arcana

- Arcane Resistance;
- Corruption Resistance;
- Arcane Strain;
- Arcane Backlash e provenance;
- Danger tiers/profiles;
- recursos/custos de casting, equipment contributions e containment;
- novos domínios, rituais ou public hazard boundaries quando se tornarem canônicos.

Arcane Resistance continua um canal próprio. Generic magic resistance, Shroud, pressão, temperatura ou gases não a preenchem sem provider explícito.

### Enshrouded

- Shroud severity/query e descobertas de core;
- Exposure: reserva, drain, recovery, Madness, Deadly/Red Shroud;
- Flame Level, Passage, Sanctuary/Ward e rituais;
- Corrupted Ecology e `MagicResistanceService`;
- Lich/Story e demais progressões somente conforme cada subcomponente chegar à `main`.

### RPG Skill Tree

- novos atributos/recursos/boundaries públicos;
- novas subtrees/classes/masteries/especializações canônicas;
- world scaling, itemização, corpos/identidades, compêndio/cartografia e outras áreas somente conforme seu estado real.

## 6. Regra de lote exato

A descoberta de uma lacuna **não autoriza transformar o lote em 11 perks**. Se a solução exigir uma perk nova fora do lote atual:

1. registrar a capacidade e a cobertura `AUSENTE/PARCIAL`;
2. registrar a classificação e o local temático proposto;
3. não iniciar automaticamente outro lote;
4. preservar o ciclo de lotes exatos de 10 definido pelo protocolo permanente.

## 7. Provider-native first continua obrigatório

Detectar uma capacidade não significa automaticamente criar um node. Antes de qualquer design:

- preservar a progressão nativa quando ela for authority adequada;
- confirmar API/query/hook real;
- declarar authority e direção da bridge;
- definir causalidade e deduplicação;
- definir fallback e fail-closed;
- proibir escrita direta em estado/pipeline autoritativo do provider.

## 8. Disposição obrigatória do delta detectado em 2026-08-30

Os SHAs abaixo **só podem virar baseline porque cada capacidade nova/alterada relevante do delta recebeu uma disposição explícita antes do checkpoint**.

| Projeto | Capacidade detectada no delta | Estado real | SHA/evidência | Cobertura atual | Decisão principal | Ação para a árvore | Hook/boundary | Fail-closed |
|---|---|---|---|---|---|---|---|---|
| Volcanoes | produtor físico hidrotermal bounded/determinístico de Cu/Fe/Au | IMPLEMENTADO E CANÔNICO | `602e0188...`; PRs #79/#80; `plans/STATUS.md` | não é uma capacidade de perk a ser duplicada | **NÃO DEVE SER INTEGRADO** como mutação/produção da perk | perks de geologia podem continuar consumindo boundaries read-only de descoberta; nenhuma perk deve invocar/reimplementar o produtor físico ou escrever ownership de worldgen | `GeologicalDepositSource`/estado geológico read-only para consumers; produtor físico permanece Volcanoes-owned | sem boundary público seguro, contribuição/mutação da perk é zero; não produzir minério nem alterar worldgen |
| Volcanoes | handoff seletivo de ownership Cu/Fe/Au para RNS + lifecycle bridge | IMPLEMENTADO PARCIALMENTE / aberto | `plans/06-integrations/05-rns.md`; `plans/STATUS.md` em `602e0188...` | ausente porque o contrato ainda não fechou | **SEM HOOK SEGURO** | registrar como integração futura; não desenhar perk supondo que Volcanoes já substitui authority do RNS | RNS permanece authority de prospecção e native metal worldgen até fechamento explícito | bridge permanece desabilitada/fail-closed; tin/nickel/zinc/silver continuam RNS-owned |
| Enshrouded | Sanctuary / Flame Ward do Stage 05 | IMPLEMENTADO E CANÔNICO | `77552a3d...`; `✅-03-sanctuary.md`; Stage 05 fechado | coberto pela progressão nativa do Enshrouded, não por node RPG próprio obrigatório | **PROGRESSÃO NATIVA AUTORITATIVA** | não duplicar Flame Level, Sanctuary ou Ward como estado RPG; perks futuras podem consultar/gatear ou contribuir somente por boundary explícito | `FlameWardQuery`, `FlamePassageQuery` e runtime bindings canônicos do Enshrouded | ausência/incompatibilidade de adapter = nenhuma escrita/contribuição; nunca materializar Sanctuary paralelo |
| Enshrouded | Story State 06.01: ownership, encounter UUID, lifecycle e reward issuance idempotente | IMPLEMENTADO E CANÔNICO para 06.01; Stage 06 segue parcial | `77552a3d...`; PR #41; `plans/06-lich-story/01-story-state.md` | coberto pelo estado narrativo nativo; não existe obrigação de perk espelho | **PROGRESSÃO NATIVA AUTORITATIVA** | RPG pode futuramente consumir milestones discretos apenas por query/event/adapter público comprovado; não copiar narrativa, encounter state ou reward ledger | `ProgressionOwner` e Story State permanecem Enshrouded-owned; cross-mod access deve ser provado separadamente | sem boundary cross-mod comprovado, perk/bridge fica inativa; nunca inferir derrota/reward por entidade física ou tick |

**Resultado do delta:** nenhuma dessas quatro linhas exige inserir uma 11ª perk no lote ativo. Os dois itens provider-owned permanecem fora de mutação direta da árvore; o handoff RNS continua fail-closed; Sanctuary e Story State permanecem progressões/estados nativos autoritativos até existir uma necessidade de bridge/perk comprovada por design e boundary real.

## 9. Baseline reconciliado após as disposições acima — 2026-08-30

| Projeto | Baseline para o próximo delta | Observação |
|---|---|---|
| RPG Skill Tree | `f448aa0b4f9df400011873e9ad26771209876ad4` | inclui a documentação canônica de projetos próprios da PR #227 |
| Volcanoes | `602e0188c123ac8531d3413a5630daa22e3d761f` | baseline avançado **depois** das disposições explícitas da seção 8; PRs #79/#80 mudaram a frente RNS/hidrotermal |
| Enshrouded | `77552a3d7f089a47908c109f5f8c19aff8a0f97d` | baseline avançado **depois** das disposições explícitas da seção 8; Sanctuary fechou e 06.01 Story State chegou à `main` |
| Black Arcana | `07263ae9bad12eba6ed500992991faa36ad598b2` | sem avanço desde o snapshot auditado |

Os baselines são checkpoints de comparação, não congelamento de verdade. Em todo novo lote, `main` e `plans/STATUS.md` frescos prevalecem.

## 10. Delta já reconciliado neste ciclo

### Volcanoes

Desde `1d0da7ae...` até `602e0188...`:

- identidade hidrotermal exata e migração de world-upgrade foram canonicalizadas;
- um produtor físico bounded/determinístico de Cu/Fe/Au foi implementado e testado;
- portanto não é mais correto afirmar que Volcanoes ainda não demonstrou placement físico desses três metais;
- **RNS continua authority de prospecção e native metal worldgen até o handoff seletivo de ownership**; a lifecycle bridge continua fail-closed e `05-rns.md` permanece aberto.

### Enshrouded

Desde `de145be7...` até `77552a3d...`:

- Stage 05 Flame Progression fechou integralmente, incluindo Sanctuary/Flame Ward;
- 06.01 Story State chegou à `main` com estado narrativo server-global versionado, `ProgressionOwner`, encounter UUID estável, transições one-way e defeat/reward issuance idempotentes;
- isso não promove Stage 06 inteiro: boss provider, manifestação e demais tasks continuam dependendo de fechamento próprio.

Esses dois casos são a prova operacional de por que o delta deve ser executado em todo lote.

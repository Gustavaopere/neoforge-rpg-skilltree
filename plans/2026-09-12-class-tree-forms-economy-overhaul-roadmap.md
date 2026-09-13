# Roadmap Mestre — Classes, Árvore 2/3, Form Engine, UI e Economy V2

> **Data:** 2026-09-12  
> **Status:** arquitetura aprovada; execução futura decomposta em subprojetos.  
> **Baseline reconciliado na abertura:** `main@594cbeea70cb3e16d698933f35391e05eddd7c60`.  
> **Modlist física:** 2026-09-08, 595 entradas, NeoForge 21.1.248.  
> **PR de arquitetura:** #428 / `plan/stage-07-symbolic-skill-tree-ui`.

## 1. Objetivo

Reorganizar a progressão do RPG Skill Tree para suportar uma experiência ARPG ampla e integrada sem descartar a infraestrutura server-authoritative já concluída.

O resultado de produto é:

```text
ÁRVORE 1
atributos permanentes/base
      |
      v
ÁRVORE 2
uma malha conectada
23 regiões de classe
origem + multiclass + shared nodes
      |
      v
ÁRVORE 3
especializações class-centric
      |
      +---- Form Engine: Druid/Metamorph
      |
      +---- provider disciplines/adapters

ECONOMY V2
subprojeto transversal independente
ledger único + moeda/contas/comércio/MineColonies
```

---

## 2. Documentos normativos

- **Classes/Tree 2/Tree 3:** `plans/04-classes-masteries-specializations/07-class-oriented-progression-overhaul.md`;
- **Druid/Metamorph/Form Engine:** `plans/06-integrations/06-identity-morphs.md`;
- **Economy V2:** `plans/06-integrations/12-economy-v2.md`;
- **UI/authoring:** `plans/07-data-network-ui/05-skill-tree-ui.md`.

Se houver divergência, presença/versão física e runtime/código real continuam seguindo a hierarquia de autoridade do projeto.

---

## 3. Decisões fechadas

1. O jogador começa escolhendo **uma Classe de Origem**.
2. A Árvore 2 possui **23 galhos/regiões de classe**, mas é um único grafo; não há 23 árvores isoladas.
3. Perks podem ser compartilhadas ou aproveitadas por classes vizinhas quando sua semântica permitir.
4. Comprar perks de outra região **não** concede classe automaticamente.
5. Multiclass é obtido por **Class Gateway** + requisitos reais.
6. Não existe hard cap de classes.
7. A limitação vem de custo progressivo, distância, atributos-base, Mastery, providers e gateways.
8. A Classe de Origem pode receber desconto econômico nos nodes com afinidade explícita da origem.
9. Requisitos de atributo usam somente investimento/base permanente da Árvore 1.
10. Tree 3 deixa de ser essencialmente provider-centric e passa a aprofundar identidade de classe.
11. Druid e Metamorph continuam classes próprias.
12. Druid possui assimilação primal de traits; Metamorph possui catálogo mais amplo de corpos, mas não assimila traits no corpo humano.
13. Identity2/Woodwalkers não voltam como dependency por inércia histórica.
14. AE2/Oritech removidos não continuam como requirements de Technomancer/especializações.
15. Economy V1 MineColonies é preservada e expandida por um Economy Core único.
16. Lightman's Currency/Numismatics são referências/reuse candidates mediante licença/provenance, não authorities paralelas.
17. Mine and Slash é referência clean-room para árvore grande/authoring, não fonte de código copiável.

---

## 4. Workstream A — Reconciliação física e auditoria das 23 classes

### Entrada

- modlist física mais recente;
- Auditoria Mestre no Notion;
- `main` atual;
- class definitions;
- specialization definitions;
- subtrees existentes;
- quatro guias do projeto quando aplicáveis.

### Trabalho

Para cada classe, responder e registrar:

- mantém nome/identidade?
- pode ser Origem?
- qual é sua habilidade signature?
- qual fantasy mecânica não pode ser roubada pelas classes vizinhas?
- quais perks são genéricas/compartilháveis?
- quais providers realmente existem?
- quais recursos/provider authorities usa?
- quais classes devem ficar próximas?
- onde fica o Class Gateway?
- quais atributos-base são coerentes como gates?
- quais Masteries medem prática real?
- quais especializações Tree 3 são candidatas?
- quais efeitos antigos ficaram órfãos após remoção de mods?

### Gate

Nenhum layout final de 23 regiões é congelado antes de todas as classes terem disposition explícita.

### Deltas conhecidos

- Druid: redesign radical para Form Engine;
- Metamorph: redesign radical para Form Engine;
- Technomancer: remover AE2/Oritech e reauditar stack Create/tecnologia atual;
- specializations `ae2_networks`, `oritech_mining`, `oritech_power`: provider removido;
- demais classes Seed Alpha 2: revisão temática/mecânica antes de assumir que o domínio antigo basta.

---

## 5. Workstream B — Schema de Origem, afinidade, custos e atributos

### Objetivo

Estender os schemas atuais sem transferir authority ao cliente.

### Capacidades necessárias

- Origin Class persistida de forma estável;
- class affinity explícita por node;
- Class Gateway explícito;
- requisito de atributo-base;
- custo efetivo server-derived;
- desconto de origem;
- pressão de investimento global;
- registro do custo realmente pago por rank para refund;
- migration/versioning.

### Invariantes

- ID/posição não inferem classe;
- cliente não declara custo;
- buff/equipamento não satisfaz requisito de atributo-base;
- respec da Árvore 1 reconcilia dependências;
- replay não duplica compra;
- custo/refund usam provenance exata.

### Gate

Schema e testes puros antes de converter a árvore de conteúdo.

---

## 6. Workstream C — Balance simulator

### Objetivo

Determinar números por simulação em vez de palpite.

### Casos

- 1 classe pura nível 100/200/300;
- origem + perks vizinhas;
- duas classes formais;
- três classes formais;
- Mage/Sorcerer/Warlock crossover;
- Priest/Cleric/Paladin crossover;
- build INT alta para perks de mana;
- build espalhada em múltiplos atributos;
- respec e reconstrução.

### Alvo

No nível 300, duas classes formais devem representar investimento profundo; três devem ser possíveis em princípio, porém excepcionalmente caras. Não criar hard cap para forçar esse resultado.

### Saída

- curva de custo;
- desconto de origem;
- thresholds de gateways;
- budgets de pontos;
- relatório de builds de referência.

---

## 7. Workstream D — Nova topologia da Árvore 2

### Objetivo

Materializar uma única malha de 23 regiões depois que classes e balance estiverem auditados.

### Regras

- 23 anchors/identidades reconhecíveis;
- nenhum componente isolado;
- shared nodes são um único ID;
- corredores refletem afinidade real;
- região não cria lock artificial;
- Class Gateway é explícito;
- 512 é baseline histórico, não teto;
- aliases preservam saves/IDs quando houver migração.

### Conteúdo

Perks continuam sendo trabalhadas no protocolo formal de **lotes exatos de 10**. O redesign macro não autoriza gerar/fechar centenas de perks de uma vez.

---

## 8. Workstream E — Migração do class resolver

### Preservar

- `ProgressionState` como authority;
- resolução determinística;
- metadata explícita;
- múltiplas identidades;
- reconcile em purchase/respec/Mastery/reload;
- fail-closed;
- idempotência.

### Alterar

- origem passa a ser input canônico;
- identidade secundária vem do Class Gateway, não de completar domínio por inferência temática;
- fixed bridge rules antigas são migradas para requisitos/custos data-driven;
- paid provenance continua auditable.

### Testes

- origem única;
- class gateway;
- perks vizinhas sem concessão;
- múltiplas classes;
- respec;
- reload;
- migration legacy.

---

## 9. Workstream F — Tree 3 class-centric

### Passo 1

Auditar as 25 definitions atuais.

Classificar cada uma como:

- specialization final;
- shared discipline;
- provider module/gate;
- legacy/retired.

### Passo 2

Desenhar especializações reais por classe. Não exigir número idêntico para todas; identidade e profundidade vêm antes da simetria.

### Passo 3

Definir topologia real das subtrees. UI não cria fake nodes.

### Passo 4

Migrar grants/gateways antigos e remover provider assumptions obsoletos.

---

## 10. Workstream G — Form Engine

Executar `plans/06-integrations/06-identity-morphs.md` como subprojeto próprio.

Dependências:

- identidade Druid/Metamorph auditada;
- schema/gateway novo suficiente para gates;
- causalidade de kill estável;
- classificação de entities/provider adapters.

Ordem:

1. FormDefinition/classification;
2. runtime corporal mínimo;
3. locomotion/combat;
4. unlock por kill;
5. Druid Primal Traits;
6. external adapters;
7. custom forms.

Não bloquear o redesign inteiro da Árvore 2 esperando todos os adapters de criaturas; unsupported forms ficam fail-closed.

---

## 11. Workstream H — Mine and Slash spike + D001

### Spike Mine and Slash

Estudar de forma clean-room:

- authoring grid;
- parser/graph;
- UI buttons;
- pan/zoom;
- Ascendancy;
- purchase/refund;
- network authority;
- persistence;
- performance/culling.

Saída: ADR de authoring/layout.

### D001

Construir vertical slice nosso com duas regiões, shared node, Class Gateway, specialization gateway, custo dinâmico e atributo-base.

Comparar engine externa candidata versus custom UI e registrar ADR.

Só depois escalar 07.05.

---

## 12. Workstream I — Economy V2

Pode avançar em paralelo aos detalhes de conteúdo da árvore, desde que não compartilhe branch de implementação sem necessidade.

Ordem:

1. revalidar MineColonies 1.1.1381;
2. audit técnico/licença de Lightman's Currency e Numismatics;
3. provenance/ADR reuse vs clean-room;
4. generalizar ledger V1 para accounts;
5. cash/wallet/bank;
6. vendors/buy-sell;
7. payroll/taxes;
8. market/auction;
9. inter-colony trade.

O ledger continua único.

---

## 13. Workstream J — UI integral

Depois de D001:

- renderer de 23 regiões;
- shared nodes;
- class-origin navigation;
- class gateway states;
- dynamic cost/requisite tooltips;
- Tree 3 glyph layouts;
- search/filter/breadcrumbs;
- accessibility;
- profiling 512/750/1000+ nodes;
- spatial optimizations somente após profiling.

---

## 14. Migração de saves

Nenhuma etapa pode assumir mundo novo.

Obrigatório:

- schema versioning;
- aliases de IDs;
- converter/remover old class bridge provenance sem duplicar pontos;
- refund exato de node sem equivalente;
- specializations com provider removido ficam legacy/reconciled, não causam crash;
- Identity2 form permissions antigas não viram unlock first-party automaticamente sem regra explícita;
- economy V1 supply preservada exatamente na V2.

Criar fixtures de saves históricos representativos.

---

## 15. CI e validação

Cada subprojeto define TDD/validators próprios, mas o fechamento global exige:

- unit tests puros;
- NeoForge GameTests quando o runtime exigir;
- provider-present lanes para versões exatas relevantes;
- provider-absent/fail-closed;
- dedicated server smoke;
- save/reload/migration;
- multiplayer;
- anti-replay/idempotency;
- build/JAR validators;
- UI profiling;
- full-pack acceptance quando aplicável.

Teste executado antes da última sincronização com `main` não vale como evidência final de merge.

---

## 16. Estratégia de Git

PR #428 é **arquitetura/documentação** e não implementa runtime.

Implementação futura deve ser dividida em PRs/subprojetos coerentes, sempre:

1. fetch `origin/main`;
2. registrar SHA;
3. verificar trabalho equivalente;
4. reconciliar branch;
5. implementar/testar;
6. fetch/reconciliar novamente antes de handoff/merge;
7. revalidar após a última sincronização.

Perks continuam no protocolo Chat 1 → Chat 2 → Chat 3 em lotes exatos de 10 e não são mergeadas por Chat 1/2.

---

## 17. Sequência recomendada

```text
A. provider/class audit
        |
        +--> B. schema/cost/attribute foundations
        |        |
        |        +--> C. balance simulator
        |                 |
        |                 +--> D. Tree 2 topology/content batches
        |                          |
        |                          +--> E. class resolver migration
        |                          +--> F. Tree 3 redesign
        |                                   |
        |                                   +--> G. Form Engine
        |
        +--> H. Mine&Slash spike -> D001 -> J. UI integral

I. Economy V2  ---------------------------------> paralelo, mesma governança
```

---

## 18. Critério de conclusão do programa

O overhaul global está concluído somente quando:

- as 23 classes foram reauditadas contra a modlist corrente;
- cada classe tem identidade, origem/gateway e adjacency documentadas;
- a Árvore 2 é uma malha real de 23 regiões;
- custo progressivo e atributos-base estão server-authoritative;
- multiclass não tem hard cap e o balance nível 300 atende o alvo;
- Tree 3 é class-centric e providers removidos foram migrados;
- Druid/Metamorph rodam no Form Engine first-party;
- D001 foi resolvida e a UI escala com profiling;
- Economy V2 possui uma única monetary authority e MineColonies atual validado;
- saves existentes migram sem perda/dupe silencioso;
- CI final ocorre depois da última reconciliação com `main`.

Este roadmap não declara a implementação pronta. Ele fixa a ordem e as invariantes para que os próximos ciclos não reconstruam decisões contraditórias.
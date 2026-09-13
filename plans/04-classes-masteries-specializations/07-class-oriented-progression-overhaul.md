# 04.07 — Redesign canônico: Árvore 2 orientada às 23 classes, multiclass e Árvore 3

> **Status:** PLANO ARQUITETURAL APROVADO PELO USUÁRIO / IMPLEMENTAÇÃO NÃO INICIADA.  
> **Baseline Git reconciliado:** `main@594cbeea70cb3e16d698933f35391e05eddd7c60`.  
> **Snapshot físico de autoridade:** modlist de 2026-09-08, **595 entradas**, Minecraft 1.21.1, **NeoForge 21.1.248**, Java 21.  
> **Importante:** este documento redesenha a arquitetura global de classes e especializações; ele **não** marca perks individuais como `DESIGN APROVADO` no protocolo de lotes de 10.

## 1. Decisão executiva

A progressão do jogador passa a ter três camadas complementares:

1. **Árvore 1 — Atributos:** investimento permanente/base do personagem;
2. **Árvore 2 — Progressão principal:** **uma única malha conectada**, misturada, com **23 galhos/regiões de classe** e travessias entre eles;
3. **Árvore 3 — Especializações:** aprofundamentos reais de identidade de classe, não uma lista de providers externos.

A Árvore 2 **não** será dividida visualmente por categorias como `MARTIAL`, `ARCANE`, `HEALING` etc. Esses domínios podem continuar existindo como metadata interna para balance, resolução, busca e validação, mas a topologia player-facing deve falar a linguagem das **classes**.

Uma perk pertence ao grafo, não a uma “caixa fechada” de classe. A localização representa afinidade e custo de acesso. Uma perk de fogo próxima de Sorcerer pode ser útil ao Mage; uma perk de espinhos próxima de um ramo marcial pode servir a uma build de Blood/Bone; uma perk de mana pode ser atravessada por qualquer build capaz de chegar até ela e cumprir seus requisitos.

### 1.1 Regra central

```text
região de classe != permissão exclusiva
```

Só existe exclusividade quando a semântica da mecânica exige uma identidade formal, por exemplo:

- `Wild Shape` e assimilação primal do Druid;
- pacto formal do Warlock;
- gateway de uma especialização que exige uma classe;
- habilidade signature explicitamente vinculada à classe.

Bonificações genéricas, atributos, dano por tag/escola/tipo, defesa, recursos, QoL e efeitos compartilháveis devem funcionar por **semântica real**, não por uma barreira artificial de classe.

---

## 2. Escolha inicial de classe

### 2.1 Modelo adotado

O jogador escolhe **uma Classe de Origem** no onboarding da progressão RPG.

A origem fornece três vantagens estruturais:

1. define o **ponto inicial** do jogador na malha da Árvore 2;
2. pode conceder uma **habilidade signature inicial** quando essa habilidade tiver contrato próprio seguro e auditado;
3. aplica uma **afinidade econômica**: perks cuja metadata inclua a classe de origem custam menos do que custariam para um personagem que chegou à mesma região vindo de fora.

A classe inicial **não** bloqueia armas, equipamentos, spells, perks ou regiões de outras classes por princípio. Restrições continuam sendo declaradas apenas quando o provider ou a mecânica realmente exigirem.

### 2.2 Por que esta solução

Ela preserva ao mesmo tempo:

- identidade desde o começo;
- uma fantasia inicial forte, como Druid já receber acesso básico a Wild Shape;
- liberdade de build ao estilo de uma árvore ARPG compartilhada;
- multiclass orgânico;
- valor real para a escolha inicial sem criar 23 árvores isoladas.

Não será usado o modelo “personagem sem classe até a build emergir” como experiência principal. A resolução emergente existente continua útil como mecanismo de reconhecimento e validação, mas deixa de ser a única forma de obter uma identidade.

---

## 3. Multiclasse sem limite rígido

### 3.1 Regra adotada

**Não existe um número máximo hardcoded de classes.**

O limite é econômico e estrutural. Uma terceira classe é permitida se o jogador realmente conseguir pagar o caminho, cumprir requisitos de atributos, Mastery, provider e gateway.

A meta de balance é que, com a curva real de pontos e autoleveling, um personagem em torno do **nível 300** normalmente não consiga completar mais do que aproximadamente **duas identidades de classe relevantes**, sem transformar isso em uma proibição artificial.

Essa meta é um **alvo de simulação**, não uma constante de runtime.

### 3.2 Pegar perk de outra região não concede classe

O jogador pode atravessar uma região vizinha e comprar perks úteis sem automaticamente receber aquela classe.

Exemplo:

```text
Warlock -> nodes compartilhados de magia -> Sorcerer/elemental -> perk FIRE
```

Isso não transforma o Warlock em Sorcerer.

Uma nova classe formal é conquistada por um **Class Gateway** daquele galho. O gateway exige investimento significativo e requisitos próprios. Somente depois dele a segunda identidade passa a existir para efeitos que realmente dependem de classe e para acesso às especializações correspondentes.

### 3.3 Soft limits obrigatórios

O sistema combina cinco pressões:

1. **distância/topologia:** chegar a outro galho consome pontos em caminhos reais;
2. **custo progressivo global:** quanto maior o investimento consolidado na Árvore 2, maior o custo marginal de novos ranks/nodes segundo uma curva monotônica e data-driven;
3. **desconto de origem:** a Classe de Origem reduz custo apenas em nodes com afinidade explícita daquela classe;
4. **requisitos de atributo:** nodes avançados podem exigir investimento permanente na Árvore 1;
5. **gates reais:** Mastery, classe, provider, item/estado canônico ou pré-requisitos de node quando a mecânica exigir.

O autoleveling do modpack continua cuidando da escala do mundo; ele não substitui o orçamento de build nem autoriza custo infinito.

---

## 4. Custo progressivo e prevenção de exploits

### 4.1 Authority

O cliente nunca informa custo. A compra continua server-authoritative e preserva o contrato existente: o cliente envia intenção, e o servidor resolve custo, requisitos, rank, conectividade e disponibilidade.

### 4.2 Modelo de custo

O custo efetivo deve ser função de dados canônicos, aproximadamente:

```text
custo_efetivo = custo_base_do_rank
               + pressão_por_investimento_global
               + modificadores_de_gateway/topologia
               - desconto_de_afinidade_da_origem
```

Este documento **não fixa percentuais ou thresholds numéricos**. Eles devem sair de simulação sobre a curva de pontos real até nível 300.

### 4.3 Investimento usado na pressão global

A pressão deve derivar de investimento permanente e autoritativo na Árvore 2, e não de:

- buffs temporários;
- equipamento;
- posição visual;
- nome/namespace do node;
- quantidade bruta de classes apenas.

A curva não deve permitir que a ordem de compra seja explorada para pagar menos pelo mesmo estado final de forma não intencional.

### 4.4 Refund correto

Custo dinâmico torna obrigatório persistir/provar **quanto realmente foi pago** por compra/rank quando houver refund. Respec não pode recalcular o preço atual e devolver valor diferente do debitado.

A proveniência/idempotência já existente para bridge e compras deve ser reaproveitada, não substituída por uma segunda contabilidade.

---

## 5. Requisitos de atributo da Árvore 1

Nodes da Árvore 2 podem exigir atributos-base permanentes, por exemplo:

```text
+50 Mana
requisito: Intelligence >= 40
```

### 5.1 Fonte do atributo

O requisito usa exclusivamente o **investimento/base canônico da Árvore 1**. Não contam:

- armadura;
- Curios;
- poções;
- buffs;
- efeitos temporários;
- bônus concedidos pela própria Árvore 2.

Isso impede equipar um item, comprar uma perk e retirar o item para contornar o gate.

### 5.2 Respec da Árvore 1

Se um respec permanente de atributo quebrar requisito de nodes já comprados, o servidor executa reconciliação determinística e segura. Nodes que deixam de satisfazer o contrato e dependentes que se tornam inalcançáveis são desativados/removidos e reembolsados segundo a proveniência de custo real.

Nunca se usa queda temporária de atributo como causa para destruir build.

### 5.3 Composição de requisitos

Um node poderá combinar, quando aplicável:

- requisito de atributo-base;
- rank/node anterior;
- nível;
- Mastery;
- classe formal;
- provider/capability disponível;
- escolha mutuamente exclusiva;
- recurso/estado canônico do provider.

Todos os campos precisam de schema explícito. Nenhum requisito é inferido pelo ID ou pela posição visual.

---

## 6. Os 23 galhos da Árvore 2

O catálogo runtime atual materializa 23 identidades, e o redesign parte exatamente delas como **inventário a auditar**, não como promessa de que a semântica atual de todas permanecerá intacta:

1. Arcanist
2. Beastmaster
3. Cleric
4. Druid
5. Duelist
6. Engineer
7. Geomancer
8. Guardian
9. Mage
10. Metamorph
11. Miner
12. Necromancer
13. Occultist
14. Paladin
15. Priest
16. Rogue
17. Sorcerer
18. Spellblade
19. Summoner
20. Survivor
21. Technomancer
22. Warlock
23. Warrior

### 6.1 A Árvore 2 tem 23 regiões, não 23 ilhas

Cada classe recebe um galho/região reconhecível, porém galhos vizinhos compartilham corredores, nodes ou confluências.

Clusters iniciais para o solver/editorial, sujeitos à auditoria de conteúdo:

- **Arcano:** Arcanist, Mage, Sorcerer, Warlock;
- **Sagrado:** Priest, Cleric, Paladin;
- **Marcial:** Warrior, Guardian, Duelist, Rogue;
- **Natureza/companions:** Druid, Beastmaster, Summoner, Survivor;
- **Oculto/morte:** Occultist, Necromancer, Warlock, Metamorph;
- **Tecnologia/ofício:** Engineer, Technomancer, Miner;
- **Confluências:** Spellblade entre magia e Martial; Geomancer entre magia e Mining/natureza; Technomancer entre magia e Engineering; Paladin entre Holy/defesa/martial; Necromancer entre Occult/Summoning.

Os clusters **não são categorias exclusivas** e podem sobrepor-se.

### 6.2 Node compartilhado

Quando uma mesma perk serve verdadeiramente a duas ou mais classes, preferir:

```text
1 gameplay node ID = 1 instância canônica no grafo
```

Não clonar o mesmo efeito em `mage_fire_power`, `sorcerer_fire_power` e `warlock_fire_power` apenas para manter fronteiras artificiais.

Uma mesma instância pode declarar múltiplas afinidades para layout, desconto de origem, busca e documentação.

### 6.3 Corredores e fronteiras

Fronteiras devem ser porosas. A topologia precisa permitir exemplos como:

- Blood/Bone build acessando Thorns/retaliation de uma vizinhança melee;
- Warlock de fogo avançando em direção a Sorcerer;
- Mage usando nodes elementais próximos do Sorcerer;
- Priest/Cleric/Paladin compartilhando Holy/healing/defesa onde a semântica for comum;
- Spellblade aproveitando nodes reais de Mage e Warrior sem duplicá-los.

---

## 7. Auditoria obrigatória das 23 classes antes de congelar conteúdo

O catálogo atual foi criado sobre o antigo modelo de domínios e contém vários “Seed Alpha 2”. Portanto **cada classe deve ser reavaliada contra a modlist física de 595 entradas antes de sua árvore final ser desenhada**.

### 7.1 Drift já comprovado

| Classe | Problema atual | Ação obrigatória |
|---|---|---|
| Druid | documentação/runtime histórico dependia de Identity2/Ars Morph | redesenhar para Form Engine first-party |
| Metamorph | documentação/runtime histórico dependia de Identity2/Ars Morph | redesenhar para Form Engine first-party |
| Technomancer | subtree atual contém AE2 Networks e Oritech Power | remover dependências ausentes e reconstruir sobre providers atuais, com Create como eixo forte |
| Engineer | seed antigo `ENGINEERING` é genérico | reauditar ecossistema tecnológico físico atual, especialmente Create e addons |
| Miner | seed antigo `MINING` é genérico | reauditar TFC/mineração/providers atuais |
| demais classes | definição antiga pode ser só confluência de domínios | revalidar fantasia, signature, providers, gates e adjacências |

AE2, Oritech, Identity/Identity2 e Woodwalkers **não estão na modlist física atual** e não podem permanecer como requisito ativo.

### 7.2 O que deve ser decidido por classe

Para cada uma das 23:

- se é selecionável como Origem;
- habilidade signature inicial, se houver;
- fantasia e papel mecânico;
- providers realmente presentes;
- recursos e authority;
- galhos vizinhos;
- nodes compartilháveis;
- Class Gateway para multiclass;
- requisitos de atributo/Mastery;
- especializações candidatas;
- bridge/fallback/fail-closed;
- anti-abuso e deduplicação;
- testes necessários.

Nenhuma classe perde/ganha provider por analogia temática.

---

## 8. Migração do runtime de classes existente

O sistema atual de `ProgressionState + ClassRuleCatalog`, resolução determinística, metadata explícita e múltiplas identidades **deve ser preservado como authority**.

O que muda é a regra de design alimentada nele.

### 8.1 Preservar

- resolução determinística;
- estado server-authoritative;
- metadata explícita, sem heurísticas;
- múltiplas identidades;
- reconciliação em purchase/respec/Mastery/reload;
- idempotência;
- proveniência de pagamentos;
- fail-closed para metadata/provider ausente.

### 8.2 Substituir

O contrato antigo de “classe emerge porque completou domínios/final triads + bridge fixa de 10” deixa de ser o modelo final de produto.

A implementação futura migra para:

- `Origin Class` escolhida;
- investimento real numa malha orientada a classe;
- `Class Gateway` explícito para identidade secundária;
- requisitos data-driven de atributo/Mastery/provider;
- custo progressivo e proveniência do custo real.

Dados antigos devem continuar legíveis durante a migração e receber aliases/conversão quando necessário. Não quebrar saves por uma renumeração editorial.

---

## 9. IDs e códigos editoriais

IDs runtime/persistidos continuam estáveis e namespaced quando o contrato atual exigir. Códigos legíveis para design/UI podem ser adicionados como aliases/editorial metadata.

Convenção-alvo:

- Árvore 1: `0001`, `0002`... como código visível/editorial;
- Mage: `MAG0001`, `MAG0002`...;
- Druid: `DRU0001`...;
- Metamorph: `MET0001`...;
- Warrior: `WAR0001`...;
- especialização: prefixo da classe + especialização, sem substituir o ID runtime existente sem migração formal.

O código editorial nunca é usado implicitamente como authority de classe/provider.

---

## 10. Árvore 3 — especializações

### 10.1 Estado atual

A `main` materializa **25 specialization definitions**:

- 9 Iron's Spells 'n Spellbooks;
- 6 Ars Nouveau;
- 3 Epic Fight;
- 4 Create;
- 2 Oritech;
- 1 AE2.

Como AE2 e Oritech foram removidos da modlist atual, **3 das 25 definições estão obsoletas como integrações ativas**: `ae2_networks`, `oritech_mining` e `oritech_power`.

Elas não devem ser silenciosamente redirecionadas para Tom's Storage, Create ou outro provider. A semântica deve ser reavaliada e, se não houver equivalência real, a definição é aposentada/migrada explicitamente.

### 10.2 Mudança de taxonomia

A Tree 3 final será **class-centric**, não provider-centric.

Exemplo conceitual:

```text
Druid
  ├─ Wild Shape combat specialist
  ├─ Herança Primal / assimilação
  └─ natureza/companions/support, se aprovado

Mage
  ├─ especializações que aprofundam identidade Mage
  └─ disciplinas de Iron's podem alimentar os nodes sem virar automaticamente “classe”
```

Provider continua importante como authority mecânica, mas uma especialização de jogador não deve existir apenas porque há um mod externo com uma API.

### 10.3 Reuso de disciplinas atuais

As 22 definições cujo provider ainda está presente não são descartadas automaticamente. Elas entram numa auditoria com quatro possíveis destinos:

- especialização de classe verdadeira;
- disciplina/linha compartilhada acessada por múltiplas classes;
- módulo/provider gate usado dentro de uma especialização maior;
- aposentada/renomeada por não representar mais a fantasia desejada.

### 10.4 Compartilhamento

Uma disciplina como FIRE/HOLY/SUMMONING pode ser consumida por mais de uma classe sem duplicar o mesmo runtime state.

`1 specialization/discipline ID = 1 estado canônico`.

---

## 11. Druid e Metamorph

As duas permanecem **classes próprias**.

- **Druid:** formas naturais/bestiais + natureza + companions/summons + capacidade exclusiva de assimilar traços de formas para o corpo humano;
- **Metamorph:** transformação corporal como identidade central e catálogo mais amplo; pode usar formas naturais e também humanoides, monstros, undead/aberrant quando classificados, porém **não recebe a assimilação primal humana do Druid**.

O contrato detalhado vive em `plans/06-integrations/06-identity-morphs.md`, que passa a ser o plano do Form Engine first-party.

---

## 12. Referência técnica de árvores ARPG

Antes de implementar o novo authoring/layout da Árvore 2, executar um spike comparativo sobre sistemas de árvore grandes.

### 12.1 Mine and Slash

A leitura já confirmou no projeto público que `TalentGrid` transforma uma grade autoral em nodes/conectores e calcula conexões por busca de caminho. Isso é uma referência de engenharia válida para:

```text
authoring compacto -> parser -> grafo canônico -> validators -> renderer
```

O estudo deve continuar cobrindo:

- `TalentTree`;
- `TalentGrid`/`GridPoint`;
- `SkillTreeScreen`/`TalentsScreen`;
- `PerkButton`;
- alocação/refund;
- persistência;
- packets/authority;
- Ascendancy;
- culling, hit testing e performance em árvores grandes.

O código Mine and Slash é **referência de comportamento/arquitetura**, não fonte a ser copiada: a distribuição pública analisada não oferece licença permissiva suficiente para reutilização literal.

### 12.2 Objetivo

A pesquisa deve produzir uma ADR curta sobre nosso pipeline de authoring, separada da decisão D001 de engine visual.

---

## 13. Balance e simulação antes de congelar custos

Criar uma ferramenta/simulador determinístico de builds antes de fixar a curva de custo.

Cenários mínimos:

- origem pura até nível 100/200/300;
- origem + invasão moderada de região vizinha;
- duas classes formais;
- tentativa de três classes;
- build com atributos espalhados;
- build que concentra Intelligence para alcançar node avançado de mana;
- respec completo e reconstrução em outra direção.

Métricas:

- pontos totais ganhos;
- custo total pago;
- nodes/ranks comprados;
- classes formais alcançadas;
- investimento na Árvore 1;
- distância média até segundo gateway;
- impacto do desconto de origem;
- diferença entre builds especializadas e generalistas.

**Acceptance de balance:** não existe hard cap; ainda assim a curva deve tornar duas classes uma conquista significativa e três classes excepcionalmente caras no nível 300, sem inviabilizar experimentação em regiões vizinhas.

---

## 14. UI e topologia

A UI correspondente está em `plans/07-data-network-ui/05-skill-tree-ui.md`.

Regras de gameplay que a UI não pode violar:

- 23 galhos/regiões dentro da própria Árvore 2;
- fronteiras sobrepostas/porosas;
- uma única instância para node compartilhado;
- classe de origem destaca o ponto inicial, mas não esconde o restante;
- custo/requisitos vêm do servidor;
- Tree 3 só renderiza nodes reais;
- a contagem histórica de 512 nodes é baseline legado, **não teto de produto**.

---

## 15. Ordem de execução

1. **Reconciliar modlist/providers:** remover assumptions AE2/Oritech/Identity2 e identificar qualquer outro drift das 23 classes/25 definições.
2. **Auditar as 23 classes:** identidade, origem, signature, adjacency, gateway, providers e especializações candidatas.
3. **Auditar Tree 3:** reclassificar as 25 definições atuais e desenhar catálogo class-centric.
4. **Definir schema novo:** afinidades de classe, Origin Class, requisitos de atributo-base, Class Gateway e custo/proveniência.
5. **Simular balance:** determinar curva de custo e desconto de origem por evidência, não por palpite.
6. **Migrar resolução de classe:** manter authority determinística e trocar critérios antigos pelos novos.
7. **Construir conteúdo em lotes controlados:** perks continuam obedecendo o protocolo formal de 10 por ciclo.
8. **Form Engine:** implementar em subprojeto próprio após spec/auditoria técnica.
9. **UI/authoring:** executar spike Mine and Slash + vertical slice D001 antes da UI integral.
10. **Migração/compat:** aliases, saves existentes, refunds e atualização de docs/tests.

Nenhum passo posterior autoriza pular a reauditoria de provider da versão física atual.

---

## 16. Testes/aceitação futura

A implementação só pode ser considerada fechada quando cobrir, no mínimo:

- escolha de origem idempotente e server-authoritative;
- impossível forjar desconto pelo cliente;
- perks de região vizinha funcionam sem conceder classe indevida;
- Class Gateway concede apenas com todos os requisitos reais;
- multiclass sem hard cap;
- custo monotônico/proveniência/refund exato;
- requisito de atributo usa base permanente, não buff/equipamento;
- respec da Árvore 1 invalida/refunda dependências de forma determinística;
- provider ausente falha fechado sem apagar progresso independente;
- shared nodes não double-count;
- saves antigos migram sem perda silenciosa;
- simulações de nível 300 atendem o alvo econômico;
- 23 regiões permanecem conectadas e sem componentes órfãos;
- dedicated server não carrega classes client-only;
- reload de datapack é atômico.

---

## 17. Não objetivos deste plano

Este documento não:

- implementa runtime;
- escolhe custos numéricos finais;
- redesenha silenciosamente cada uma das 23 classes sem auditoria;
- declara as 25 especializações atuais como finais;
- reintroduz AE2, Oritech, Identity2 ou Woodwalkers;
- cria equivalência falsa entre providers removidos e mods atuais;
- inicia automaticamente um lote formal de perks.

**Resultado esperado:** a classe inicial dá identidade e eficiência, mas a build continua livre; a Árvore 2 é uma única malha com 23 regiões integradas, multiclass é conquistado pelo próprio grafo e limitado por economia/requisitos em vez de um teto arbitrário, e a Árvore 3 passa a aprofundar classes reais em vez de espelhar a lista de mods instalados.
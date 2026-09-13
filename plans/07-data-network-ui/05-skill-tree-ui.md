# 07.05 — Skill Tree UI — Uma malha, 23 regiões de classe e especializações

> **Status:** PLANO ARQUITETURAL / IMPLEMENTAÇÃO NÃO INICIADA.  
> **Baseline reconciliado:** `main@594cbeea70cb3e16d698933f35391e05eddd7c60`.  
> **Authority física:** modlist 2026-09-08, 595 entradas, NeoForge 21.1.248.  
> **Contrato de gameplay:** `plans/04-classes-masteries-specializations/07-class-oriented-progression-overhaul.md`.

## 1. Mudança em relação ao plano anterior

O plano antigo representava a Árvore 2 como 11 domínios radiais e colocava as classes como constelações externas.

Isso **não é mais o produto desejado**.

A direção canônica passa a ser:

```text
Árvore 1 — atributos/base
          ↓
Árvore 2 — UMA malha conectada
          ├─ 23 regiões/galhos de classe
          ├─ nodes compartilhados
          ├─ corredores entre classes
          ├─ Class Gateways
          └─ sobreposições/confluências
          ↓
Árvore 3 — especializações reais das classes
```

Os antigos 11 domínios podem continuar como metadata invisível de balance/consulta, mas não organizam a experiência visual principal.

---

## 2. Objetivo visual

O jogador deve conseguir olhar a árvore em três escalas:

### Zoom distante

Ler territórios e relações:

- “Mage, Sorcerer e Warlock estão próximos”;
- “Priest, Cleric e Paladin compartilham uma vizinhança”;
- “Spellblade fica entre magia e combate”;
- “Technomancer conecta magia e engenharia”.

### Zoom médio

Ler:

- nomes de classes;
- Class Gateways;
- Notables/Keystones/Capstones;
- especializações;
- caminhos para regiões vizinhas;
- estado de bloqueio/requisitos.

### Zoom próximo

Ler e interagir com:

- node;
- rank;
- custo atual server-projected;
- requisitos de atributo;
- Mastery/provider/class gates;
- efeito exato;
- links dependentes;
- refund/respec.

---

## 3. Árvore 1

A Árvore 1 continua sendo a camada de atributos/base.

Ela deve ser visualmente acessível a partir do mesmo workspace, mas não precisa ocupar a mesma topologia de edges da Árvore 2 se isso prejudicar legibilidade.

Requisitos da UI:

- mostrar atributos permanentes/base;
- distinguir base de bônus temporário/equipamento;
- quando uma perk da Árvore 2 exigir `Intelligence >= 40`, o tooltip deve apontar explicitamente que é **atributo-base da Árvore 1**;
- preview de respec deve listar quais nodes da Árvore 2 se tornariam inválidos.

---

## 4. Árvore 2 — uma única malha

### 4.1 23 regiões reconhecíveis

As 23 classes atuais recebem regiões de afinidade:

Arcanist, Beastmaster, Cleric, Druid, Duelist, Engineer, Geomancer, Guardian, Mage, Metamorph, Miner, Necromancer, Occultist, Paladin, Priest, Rogue, Sorcerer, Spellblade, Summoner, Survivor, Technomancer, Warlock e Warrior.

A UI não trata região como subtree isolada.

### 4.2 Regiões porosas

Uma região pode:

- dividir nodes com outra;
- ter corredor curto para uma classe semanticamente próxima;
- ter corridor longo/caro para uma classe distante;
- sobrepor uma área de mecânica comum;
- apontar para um Class Gateway sem duplicar os nodes do percurso.

### 4.3 Exemplos de vizinhança

O layout deve tornar intuitivas relações como:

```text
                Mage
              /      \
        Arcanist    Sorcerer
              \      /
               Warlock
                  \
                Occultist --- Necromancer

Priest ---- Cleric ---- Paladin ---- Guardian ---- Warrior

Druid ---- Beastmaster ---- Summoner
  |                              |
Survivor                    Necromancer

Mage ---- Spellblade ---- Warrior
  |
Technomancer ---- Engineer ---- Miner
```

Isso é **direção editorial**, não um conjunto final de edges. O grafo real deve ser validado por gameplay e não pelo desenho ASCII.

### 4.4 Node compartilhado

Um node canônico aparece uma vez.

Se várias classes têm afinidade com ele, a UI pode representar essa afinidade por:

- borda multissimbólica;
- tooltip “afinidades”;
- corredores convergindo;
- background/region blend.

Nunca clonar o node só para preencher duas regiões.

---

## 5. Classe de Origem na UI

No onboarding:

1. usuário escolhe a Classe de Origem;
2. servidor confirma a escolha;
3. viewport abre no anchor daquela classe;
4. a região recebe destaque inicial;
5. nodes com desconto real mostram preço original/efetivo e motivo;
6. o restante da árvore permanece visível/explorável.

A UI nunca aplica desconto localmente.

### 5.1 Signature

Se a classe possui habilidade signature inicial, a tela apresenta como parte da origem somente depois do servidor confirmar grant/availability.

Druid, por exemplo, poderá mostrar Wild Shape básico quando o Form Engine real existir. Antes disso, mostrar estado planejado seria fake gameplay e é proibido.

---

## 6. Multiclasse na UI

A diferença entre “usar perks de outra região” e “possuir outra classe” precisa ser óbvia.

### 6.1 Estado de região

Possíveis estados:

- `ORIGIN` — classe inicial;
- `VISITED` — comprou node naquela região, sem identidade formal;
- `GATEWAY_REACHABLE`;
- `CLASS_UNLOCKED` — identidade formal ativa;
- `BLOCKED_PROVIDER`;
- `BLOCKED_REQUIREMENTS`.

### 6.2 Class Gateway

Tooltip do gateway lista exatamente:

- investimento/path faltante;
- atributo-base faltante;
- Mastery faltante;
- provider/capability ausente;
- custo atual;
- consequências de obter a identidade;
- especializações que passam a ser elegíveis, sem afirmar disponibilidade se outro gate ainda faltar.

Não existe botão “multiclass grátis”.

---

## 7. Gramática visual de nodes

| Tipo | Representação | Significado |
|---|---|---|
| path/minor | círculo pequeno | progressão incremental |
| ranked passive | círculo com rings | ranks reais |
| notable | medalhão médio | mudança relevante de build |
| keystone | forma/runa distinta | regra forte/trade-off |
| class gateway | portal/hexágono | concede identidade formal |
| specialization gateway | emblema/portal temático | abre Tree 3 real |
| capstone | emblema grande | fechamento real de ramo |
| shared node | uma instância com múltiplas afinidades | node comum |

Cor nunca é a única codificação.

---

## 8. Custos dinâmicos e requisitos

O servidor projeta no snapshot de UI:

- custo base;
- custo efetivo;
- desconto de origem, se aplicável;
- pressão de investimento global;
- requisitos;
- motivo de bloqueio;
- refund disponível conforme proveniência.

O cliente pode calcular preview visual apenas a partir do snapshot; a confirmação sempre volta ao servidor.

### 8.1 Tooltip de atributo

Exemplo:

```text
Arcane Reservoir III
+50 Mana

Custo atual: 4 pontos
Afinidade de origem: Mage (-1)
Requisitos:
✓ Mage path reachable
✗ Intelligence base 40 (atual 36)
```

Valores acima são meramente ilustrativos de formato; não fixam balance.

---

## 9. Árvore 3

### 9.1 Especialização é gameplay real

Uma especialização só mostra nodes compráveis quando existe topologia server-authoritative real.

Se existe apenas definition/gateway:

- mostrar emblema;
- nome;
- requisitos;
- status;
- provider/capability;
- sem fake nodes.

### 9.2 Visual simbólico

Quando uma subtree real existir, o layout pode fazer seus próprios nodes formarem um glifo/símbolo:

- chama;
- asa;
- escudo;
- pentagrama;
- engrenagem;
- garra;
- árvore/folha;
- outros temas aprovados.

A forma nunca altera edges/requisitos para “ficar bonita”. O fitting posiciona a topologia real.

### 9.3 Taxonomia nova

A UI passa a apresentar especializações sob as classes correspondentes, enquanto disciplinas compartilhadas/provider modules podem aparecer como bridges/satélites comuns.

Não assumir que as 25 definitions atuais são 25 especializações finais.

---

## 10. Druid e Metamorph

A UI de classes precisa refletir a diferença de fantasia:

### Druid

- Wild Shape;
- biblioteca de formas naturais;
- traits extraíveis;
- loadout de Primal Traits;
- especializações como Wild Shape e Herança Primal quando materializadas.

### Metamorph

- catálogo amplo de formas;
- natural + humanoid/monster/undead/aberrant conforme permission catalog;
- transformação rápida/profunda;
- sem interface de Primal Trait Imprinting.

Formas e traits vêm do snapshot do Form Engine; a UI não classifica entidades por conta própria.

---

## 11. Authoring de uma árvore muito grande

A contagem histórica de 512 nodes é **baseline legado, não teto**. O authoring precisa continuar manejável quando a árvore crescer.

### 11.1 Pipeline-alvo

```text
fonte de authoring compacta
      ↓
parser/layout compiler
      ↓
grafo canônico server-side
      ↓
validators
      ↓
snapshot revisionado
      ↓
renderer client-side
```

### 11.2 Pesquisa Mine and Slash

O source estudado de Mine and Slash mostra uma abordagem útil: `TalentGrid` parseia uma grade autoral, identifica talents/conectores e deriva conexões por busca de caminho.

Antes de escolher nosso formato definitivo, o spike deve inspecionar:

- `TalentTree`;
- `TalentGrid` e `GridPoint`;
- `SkillTreeScreen`/`TalentsScreen`;
- `PerkButton`;
- Ascendancy;
- pan/zoom;
- hit testing;
- persistência;
- packets;
- purchase/refund;
- custo de construção do grafo;
- culling/performance.

Mine and Slash é **referência clean-room**. Não copiar source All Rights Reserved/sem licença permissiva.

### 11.3 Nosso authoring não pode ser só coordenada manual por node

Precisamos permitir:

- anchors de classe;
- corridors;
- clusters;
- shared nodes;
- constraints de distância;
- shape templates para Tree 3;
- overrides manuais quando necessário;
- deterministic layout output.

O output final deve continuar validável como grafo comum.

---

## 12. D001 — engine visual continua gate

A decisão entre engine externa compatível e UI custom continua aberta.

Antes da implementação integral, construir um vertical slice com:

- duas regiões de classe vizinhas;
- um shared node;
- um Class Gateway;
- uma specialization gateway real;
- purchase;
- respec;
- custo dinâmico;
- requisito de atributo;
- pan/zoom;
- server sync;
- reload revision.

Comparar o candidato externo versus custom UI em:

- capacidade de representar 23 regiões sobrepostas;
- nodes compartilhados;
- start positions por origem;
- custo/requisitos dinâmicos;
- Tree 3 simbólica;
- performance;
- networking;
- maintainability;
- licença;
- acessibilidade.

Registrar ADR e só então escalar para a árvore completa.

---

## 13. Layout solver

Requisitos:

- determinístico para a mesma revisão;
- sem random visual entre logins;
- anchors de classe estáveis;
- evitar overlap ilegível;
- preservar grafo real;
- nodes compartilhados não duplicados;
- edges não atravessam nodes sem necessidade;
- labels adaptam ao zoom;
- mudança editorial de layout não altera gameplay ID.

### 13.1 Persistência de layout

Layout é dado de apresentação revisionável, não estado de progressão do jogador.

Mover um node na tela não exige migration de save se o ID/topologia não mudaram.

---

## 14. Performance

Antes de introduzir otimizações complexas, medir.

Métricas:

- tempo para abrir tela;
- frame time com árvore inteira;
- quantidade de nodes/edges visíveis;
- hit testing por frame;
- tooltip latency;
- zoom/pan allocation pressure;
- snapshot size;
- reload/layout compile time.

Otimizações candidatas após profiling:

- viewport culling;
- spatial index;
- cached label/icon geometry;
- LOD por zoom;
- batched edges;
- precomputed hit regions.

Não implementar cache avançado por especulação.

---

## 15. Navegação

Obrigatório:

- pan livre;
- zoom suave e bounded;
- “voltar à minha origem”;
- “centralizar node selecionado”;
- busca por nome/efeito/tag/classe;
- filtros de comprável/bloqueado/comprado;
- breadcrumbs para specialization;
- highlight de caminho mínimo conhecido até um target, sem fingir que é economicamente ótimo;
- minimap opcional se profiling/UX justificar.

---

## 16. Acessibilidade

- PT-BR canônico do projeto;
- cor + forma + texto para status;
- navegação por teclado/controller quando aplicável;
- reduced motion;
- UI scale;
- contraste;
- tooltip não depende de hover milimétrico;
- preço/requisito textual legível;
- screen-reader/text export considerado para informações críticas.

---

## 17. Server authority e network

O snapshot precisa carregar apenas o necessário para representar a revisão vigente.

Cliente não pode:

- declarar classe;
- declarar preço;
- inventar affinity discount;
- declarar atributo-base;
- alterar rank;
- conceder specialization;
- classificar form;
- decidir provider availability.

Request de compra/respec referencia IDs/intenção; resposta do servidor confirma resultado e nova revisão/estado.

---

## 18. Drift de providers

A UI não deve codificar AE2/Oritech/Identity2 porque eram verdade no passado.

A modlist atual removeu esses providers. Portanto:

- specialization antiga sem provider aparece como legacy/migration state quando necessário, não como caminho novo ativo;
- Technomancer não mostra gateways AE2/Oritech na árvore nova;
- Druid/Metamorph não mostram Identity2 como authority;
- Tom's Storage não substitui AE2 por nome/semelhança sem uma definição nova auditada.

---

## 19. Testes de UI/layout

### Estruturais

- 23 class anchors únicos;
- grafo inteiro conectado conforme regras;
- shared node tem uma instância;
- zero edge referencia node ausente;
- zero specialization fake node;
- deterministic layout hash para mesma revisão;
- aliases não criam instância duplicada.

### Funcionais

- escolher origem centraliza região correta;
- visitar outra região não concede classe;
- gateway mostra blockers reais;
- custo server-projected atualiza após compras;
- base attribute gate atualiza após Tree 1;
- respec preview mostra impacto;
- provider ausente mostra fail-closed;
- reload de datapack troca revisão atomicamente.

### Performance

- árvore 512, 750, 1000+ nodes synthetic fixtures;
- zoom extremo;
- muitos edges no viewport;
- busca/filtro;
- reload enquanto múltiplos clientes estão conectados;
- UI scale/resoluções diferentes.

### Dedicated server

Nenhuma classe renderer/layout/client-only deve carregar no dedicated server.

---

## 20. Acceptance

07.05 só fecha quando:

- a Árvore 2 é apresentada como uma única malha de 23 regiões de classe, não 11 domínios + classes externas;
- perks podem atravessar fronteiras sem conceder classe indevidamente;
- shared nodes não são duplicados;
- Origin Class, Class Gateway, custo dinâmico e atributo-base são legíveis;
- Tree 3 usa apenas topologia real;
- D001 foi resolvida por vertical slice/ADR;
- performance foi medida em escala maior que o baseline histórico;
- cliente continua incapaz de conceder gameplay localmente.

O resultado visual deve lembrar um **mapa de build interconectado**, não uma tela com 23 menus independentes.
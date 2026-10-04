# Plano de execução — reconstrução completa das perks

## Objetivo

Reconstruir o sistema de perks a partir do modpack realmente instalado, sem herdar especializações, providers ou pressupostos do catálogo legado.

A regra central é: **primeiro entender o que o pack oferece; depois desenhar builds; somente então aprofundar APIs/hooks e implementar.**

O antigo fluxo de três chats — auditar perks existentes, implementar e corrigir pendências — é considerado legado para esta reconstrução. Ele pode inspirar ferramentas técnicas, mas não determina a ordem de design.

---

## Fase 0 — congelamento e limpeza do legado editorial

### Trabalho
- não expandir o catálogo de perks enquanto a modlist não estiver reconciliada;
- remover especializações herdadas automaticamente do runtime antigo;
- preservar somente infraestrutura técnica já implementada que possa ser reaproveitada;
- marcar exemplos existentes, como Chain Lightning, como pilotos conceituais e não como auditorias técnicas concluídas.

### Gate de saída
Nenhuma especialização antiga é tratada como aprovada e nenhuma perk nova depende de um provider cuja presença atual não esteja confirmada.

---

## Fase 1 — fechar a modlist física atual

### Objetivo
Criar uma única autoridade de presença, versão e identidade dos mods instalados.

### Trabalho
1. localizar ou reconstruir a lista física de JARs que representa o pack atual;
2. reconciliar cada JAR com seu dossiê em `PROJECT-INSTRUCTIONS/modlist`;
3. corrigir dossiês de mods removidos, substituídos ou atualizados;
4. eliminar contradições entre os guias Gameplay, Magic e Technology;
5. garantir que todos apontem para a mesma authority física;
6. separar claramente:
   - provider de gameplay;
   - addon;
   - bridge/compat;
   - biblioteca/API;
   - UI/QoL;
   - cosmético;
   - worldgen/conteúdo sem superfície legítima de perk;
7. registrar remoções explicitamente para que mods antigos não reapareçam em planos futuros.

### Gate de saída
Existe uma fonte única e auditável que responde: “este mod/JAR está ou não está no pack agora?”.

Nenhum número de arquivos `.md` deve ser confundido automaticamente com quantidade de JARs instalados.

---

## Fase 2 — matriz de capacidades do pack

### Objetivo
Descobrir **o que o jogador pode fazer**, sem ainda criar perks individuais.

### Fontes
- todos os providers reais da modlist;
- Minecraft vanilla;
- projetos próprios do pack;
- addons e bridges somente quando adicionarem capacidade jogável real.

### Para cada provider/capacidade registrar
- capacidade/fantasia;
- tipo: spell, weapon skill, active skill, summon, stance, movement, survival mechanic, crafting/process, logistics, vehicle, ritual, pet etc.;
- domínio(s) candidatos;
- se a progressão é nativa e deve ser respeitada;
- se pode receber Standard Perks;
- se possui Abilities candidatas a Transmutation;
- se alimenta alguma fantasia de especialização;
- dependências e boundaries conhecidos em alto nível.

### Importante
Nesta fase não é necessário descobrir classe Java, evento, registry ou hook exato. Basta entender funcionalmente a capacidade.

### Gate de saída
Todo provider relevante possui pelo menos uma decisão: integrar, cobrir por sistema universal, preservar progressão nativa, ou não integrar.

---

## Fase 3 — matriz das 11 árvores

As árvores canônicas são:

`MARTIAL`, `AGILITY`, `VITALITY`, `HEALING`, `ARCANE`, `ENGINEERING`, `MINING`, `SURVIVAL`, `SUMMONING`, `OCCULT` e `LOGISTICS`.

### Trabalho
Distribuir as capacidades da Fase 2 nas árvores.

Um mod pode alimentar várias árvores. Uma árvore pode receber capacidades de muitos mods. **Mod não é árvore.**

Cada capacidade terá um owner principal para contagem, mesmo quando existir integração cruzada.

### Gate de saída
As 11 árvores possuem identidade mecânica clara e cobertura suficiente para começar o design sem inventar filler.

---

## Fase 4 — arquitetura das especializações

### Conceito
Especialização é uma **ramificação temática da árvore principal**, não uma pasta por mod.

Exemplo conceitual:

```
ARCANE
├── perks normais
├── especialização: Piromante
├── especialização: Criomante
└── outras especializações aprovadas
```

Piromante pode consumir capacidades de Iron's, Ars, vanilla e outros providers compatíveis. O provider não dá nome nem ownership automático à especialização.

### Paridade obrigatória
Depois da matriz de capacidades serão congelados:

- `K_specializations_per_tree` — mesma quantidade de especializações em cada árvore;
- `M_perks_per_specialization` — mesma quantidade de perks em cada especialização.

Assim:

`N_specialization_per_tree = K_specializations_per_tree * M_perks_per_specialization`.

Se alguma árvore não suportar `K` ou `M` sem filler, reduzimos o valor **globalmente**. Não abrimos exceção para ARCANE ou qualquer outro domínio.

### Gate de saída
Todas as 11 árvores possuem a mesma estrutura quantitativa de especializações e cada especialização representa uma fantasia/build distinta.

---

## Fase 5 — catálogo conceitual completo

### 5A — Standard Perks
Criar primeiro intenção de gameplay, owner, sinergias e providers candidatos.

### 5B — Transmutation Perks

Cada Ability candidata recebe **uma única raiz `Txxxx` em todo o catálogo**. Essa raiz é o único ponto dedicado a customizar especificamente aquela Ability.

A raiz abre três grupos independentes de escolha:

1. **Esquerda** — mínimo 2 e máximo 9 opções; exatamente 1 opção ativa.
2. **Direita** — mínimo 2 e máximo 9 opções; exatamente 1 opção ativa.
3. **Metamorfose** — mínimo 3 e máximo 9 opções; exatamente 1 opção ativa.

Uma configuração completa da Ability pode ter simultaneamente **1 esquerda + 1 direita + 1 metamorfose**. O que é proibido é manter duas opções do mesmo grupo ativas ao mesmo tempo.

Exemplo:

```
T5000 — Chain Lightning
├── ESQUERDA — escolher 1
│   ├── T5000.11
│   └── T5000.12
├── DIREITA — escolher 1
│   ├── T5000.21
│   └── T5000.22
└── METAMORFOSE — escolher 1
    ├── T5000.31
    ├── T5000.32
    └── T5000.33
```

Exemplo de configuração válida:

`T5000.11 + T5000.22 + T5000.33`.

A Chain Lightning não deve receber outra raiz mais tarde na árvore. Qualquer nova opção específica dela deve continuar dentro dos ranges `T5000.1x`, `T5000.2x` ou `T5000.3x`.

`T5001` representa **outra Ability**, nunca uma segunda ocorrência de Chain Lightning.

### Numeração dos grupos

- `.11`–`.19` = esquerda;
- `.21`–`.29` = direita;
- `.31`–`.39` = metamorfose.

O segundo dígito é o índice da opção dentro do grupo, de 1 a 9.

### Papel dos grupos

Os grupos não precisam representar sempre exatamente os mesmos atributos em todas as Abilities. Eles devem representar dois eixos menores distintos e um eixo de transformação profunda.

Exemplos de eixos menores: custo, crítico, dano, alcance, área, duração, cadência, geração de recurso, número de alvos ou consistência.

Metamorfoses podem alterar elemento, geometria, trajetória, forma de entrega, resource model, targeting, comportamento espacial ou função da Ability.

### Paridade

A paridade de Transmutation compara **raízes/Abilities**, não o número de opções internas. Uma Ability pode ter 2/2/3 e outra 4/3/6, desde que nenhuma ultrapasse 9 opções por grupo.

### 5C — Specialization Perks
Criar as perks de cada especialização depois que suas fantasias e quotas estiverem fechadas.

### Paridade global
Congelar somente quando as 11 árvores estiverem representadas:

- `N_standard_per_tree`;
- `N_transmutation_per_tree`;
- `K_specializations_per_tree`;
- `M_perks_per_specialization`.

### Gate de saída
O catálogo conceitual inteiro pode ser revisado como sistema de builds antes de qualquer implementação específica de provider.

---

## Fase 6 — auditoria técnica por provider

Somente agora cada perk aprovada é aprofundada tecnicamente.

### Para cada integração descobrir
- versão instalada;
- registry/ID real;
- API, evento ou boundary utilizável;
- authority server/client;
- persistência;
- escolha ativa e troca entre opções do mesmo grupo;
- cooldown/recurso;
- dano/heal/ownership;
- compatibilidade com addons;
- efeitos visuais;
- fallback e comportamento fail-closed;
- riscos de dupla aplicação.

### Regra
A perk conceitual não deve ser redesenhada apenas para aproveitar um hook conveniente. Primeiro tentamos implementar a fantasia aprovada; se não existir boundary seguro, registramos limitação, alternativa coerente ou fail-closed.

### Gate de saída
Cada perk possui contrato implementável ou decisão explícita de bloqueio.

---

## Fase 7 — binding e implementação

- congelar IDs editoriais;
- definir `ResourceLocation` runtime estável;
- migrar schema/loaders onde necessário;
- implementar adapters como `AbilityKnowledgeProvider`;
- implementar efeitos sem duplicar authority dos providers;
- implementar exclusividade intragrupo e coexistência entre os três grupos;
- adicionar testes de escolha/troca, save/reload, multiplayer e ausência de mod opcional;
- reaproveitar a infraestrutura de `✅-01` a `✅-05` somente onde os contratos continuarem válidos.

---

## Fase 8 — topologia, UX e documentação final

Somente depois do catálogo e runtime estarem estáveis:

- desenhar posição visual das árvores;
- posicionar especializações como ramificações;
- definir conexões, gateways e leitura visual;
- gerar wiki;
- adicionar validators de quota/IDs/links;
- criar drift gate entre dados, runtime e documentação.

---

## Ordem obrigatória resumida

```
MODLIST
  ↓
CAPACIDADES
  ↓
11 ÁRVORES
  ↓
ESPECIALIZAÇÕES
  ↓
PERKS CONCEITUAIS
  ↓
AUDITORIA TÉCNICA DOS MODS
  ↓
IMPLEMENTAÇÃO
  ↓
TOPOLOGIA / WIKI
```

Pular uma fase só é permitido quando a etapa posterior não depende da informação ausente.

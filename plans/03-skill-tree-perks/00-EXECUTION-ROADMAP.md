# Plano de execução — reconstrução completa das perks

## Objetivo

Reconstruir o sistema de perks a partir do modpack realmente instalado, sem herdar especializações, providers ou pressupostos do catálogo legado.

A regra central é: **primeiro entender o que o pack oferece; depois desenhar builds; somente então aprofundar APIs/hooks e implementar.**

Diablo IV e outros ARPGs podem ser estudados para identificar bons padrões de escolha, transformação e identidade de classe. Nenhum efeito, número, nome ou estrutura externa é obrigatório para este projeto.

---

## Fase 0 — congelamento e limpeza do legado editorial

### Trabalho
- não expandir o catálogo enquanto a modlist não estiver reconciliada;
- remover especializações herdadas automaticamente do runtime antigo;
- preservar somente infraestrutura técnica reaproveitável;
- tratar exemplos existentes, como Chain Lightning, como pilotos conceituais.

### Gate de saída
Nenhuma especialização antiga é tratada como aprovada e nenhuma perk nova depende de provider cuja presença atual não esteja confirmada.

---

## Fase 1 — fechar a modlist física atual

### Objetivo
Criar uma única autoridade de presença, versão e identidade dos mods instalados.

### Trabalho
1. localizar ou reconstruir a lista física de JARs atual;
2. reconciliar cada JAR com seu dossiê em `PROJECT-INSTRUCTIONS/modlist`;
3. corrigir dossiês de mods removidos, substituídos ou atualizados;
4. eliminar contradições entre Gameplay, Magic e Technology;
5. garantir que todos apontem para a mesma authority física;
6. classificar cada entrada como provider de gameplay, addon, bridge/compat, biblioteca/API, UI/QoL, cosmético, worldgen ou outra função;
7. registrar remoções explicitamente para impedir ressurgimento de mods antigos em planos futuros.

### Gate de saída
Existe uma fonte única e auditável que responde se um mod/JAR está ou não no pack atual.

---

## Fase 2 — matriz de capacidades do pack

### Objetivo
Descobrir **o que o jogador pode fazer**, sem ainda criar perks individuais.

### Fontes
- providers reais da modlist;
- Minecraft vanilla;
- projetos próprios do pack;
- addons/bridges quando adicionarem capacidade jogável real.

### Para cada capacidade registrar
- fantasia/função;
- tipo: spell, weapon skill, active skill, summon, stance, movement, survival mechanic, crafting/process, logistics, vehicle, ritual, pet etc.;
- domínio(s) candidatos;
- progressão nativa que deve ser respeitada;
- potencial para Standard Perks;
- Abilities candidatas a Transmutation;
- fantasias de especialização que pode alimentar;
- boundaries conhecidos em alto nível.

### Gate de saída
Todo provider relevante possui decisão de integração, cobertura universal, preservação da progressão nativa ou não integração.

---

## Fase 3 — matriz das 11 árvores

As árvores canônicas são:

`MARTIAL`, `AGILITY`, `VITALITY`, `HEALING`, `ARCANE`, `ENGINEERING`, `MINING`, `SURVIVAL`, `SUMMONING`, `OCCULT` e `LOGISTICS`.

### Trabalho
Distribuir as capacidades da Fase 2 nas árvores.

Um mod pode alimentar várias árvores. Uma árvore pode receber capacidades de muitos mods. **Mod não é árvore.**

Cada capacidade terá um owner principal para contagem, mesmo quando existir integração cruzada.

### Gate de saída
As 11 árvores possuem identidade mecânica clara e cobertura suficiente para começar o design sem filler.

---

## Fase 4 — arquitetura das especializações

### Conceito

Especialização é uma **ramificação temática da árvore principal**, não uma pasta por mod.

Exemplo conceitual:

```
ARCANE
├── perks normais
├── Piromante
├── Criomante
└── outras especializações aprovadas
```

Uma especialização pode consumir capacidades de Iron's, Ars, vanilla e outros providers compatíveis. O provider não dá nome nem ownership automático à especialização.

### Mecânica assinatura

Cada especialização deve justificar sua existência com uma **Signature Mechanic / Mecânica Assinatura**: uma camada de decisão que muda como aquela build é montada ou jogada.

Padrões válidos incluem, sem limitar:
- escolher afinidades/espíritos/patronos que alteram tags e bônus;
- configurar funções de summons e trocar parte do exército por benefícios próprios;
- caçar/capturar/selar entidades para obter poderes ou opções persistentes;
- juramentos, posturas, contratos, escolas, doutrinas, companion roles ou outros sistemas equivalentes.

A mecânica assinatura **não deve ser uma cópia literal de Diablo**. Ela deve nascer das capacidades reais do modpack e da fantasia da especialização.

Se uma especialização não tiver identidade suficiente para sustentar uma mecânica reconhecível, ela deve ser fundida, redesenhada ou descartada.

### Paridade

A obrigação de equilíbrio é o mesmo **orçamento total de perks de especialização por árvore**.

Como hipótese preferencial, também buscamos o mesmo número de especializações por árvore e o mesmo número de perks por especialização, mas esses dois valores só serão congelados depois da matriz de capacidades. Não criaremos filler para preservar uma simetria que prejudique o design.

Cada especialização aprovada deve ter uma mecânica assinatura de peso comparável às demais, embora a forma dessa mecânica possa ser completamente diferente.

### Gate de saída
Todas as árvores possuem especializações distintas, suportadas pela modlist, com mecânicas assinatura comparáveis e sem depender de nomes de mods.

---

## Fase 5 — catálogo conceitual completo

### 5A — Standard Perks
Criar intenção de gameplay, owner, sinergias e providers candidatos.

### 5B — Transmutation Perks

Cada Ability candidata recebe **uma única raiz `Txxxx` em todo o catálogo**.

A raiz abre três grupos independentes:

1. **LEFT** — mínimo 2, máximo 9; exatamente 1 opção ativa.
2. **RIGHT** — mínimo 2, máximo 9; exatamente 1 opção ativa.
3. **METAMORPHOSIS** — mínimo 3, máximo 9; exatamente 1 opção ativa.

Configuração completa:

`1 LEFT + 1 RIGHT + 1 METAMORPHOSIS`.

### Numeração

- `.11`–`.19` = LEFT;
- `.21`–`.29` = RIGHT;
- `.31`–`.39` = METAMORPHOSIS.

### Papel dos grupos

LEFT e RIGHT representam dois eixos menores distintos, escolhidos de acordo com a Ability: custo, crítico, dano, alcance, área, duração, cadência, geração de recurso, número de alvos, consistência etc.

METAMORPHOSIS muda identidade ou funcionamento: afinidade/escola, geometria, trajetória, targeting, forma de entrega, resource model, comportamento espacial, papel ofensivo/defensivo ou outra transformação estrutural.

### Conversão de afinidade/escola

Quando uma metamorfose permitir conversão, o conceito preferido é **afinidade/escola selecionável**, não uma conversão fixa para “gelo” ou “fogo”.

Exemplo conceitual:

```
T5000.33 — Transmutação de Afinidade
Ritual -> selecionar uma afinidade suportada
Exemplos candidatos: Fire, Ice, Lightning, Holy, Blood, Ender...
```

A lista real só será fechada após a modlist e a auditoria dos providers. “Elemento” e “escola” não são tratados como sinônimos técnicos; o catálogo usa `Affinity` como abstração comum.

A conversão, quando suportada, deve tentar trocar o conjunto semântico completo: damage type/school/tag, scaling, statuses, resistências, VFX e sinergias. Se um provider não permitir conversão coerente, a afinidade correspondente fica indisponível em vez de receber uma troca puramente cosmética.

### Referências externas

Diablo pode sugerir perguntas úteis — “e se esta skill orbitasse o jogador?”, “e se mudasse de escola?”, “e se sacrificasse summons por benefício próprio?” — mas a resposta deve ser construída para o nosso pack.

Não copiar automaticamente:
- números;
- quantidade de opções;
- elementos disponíveis;
- custos;
- cooldowns;
- nomes;
- restrições de classe;
- mecânicas sem equivalente útil no modpack.

### Paridade

A paridade de Transmutation compara **raízes/Abilities**, não o número de opções internas.

### 5C — Specialization Perks

Criar perks e a mecânica assinatura de cada especialização depois que suas fantasias e quotas estiverem fechadas. As perks devem alimentar a mecânica assinatura, e não existir como uma lista paralela de bônus desconectados.

### Gate de saída
O catálogo conceitual inteiro pode ser revisado como sistema de builds antes da implementação específica de providers.

---

## Fase 6 — auditoria técnica por provider

Somente agora cada conceito aprovado é aprofundado tecnicamente.

### Para cada integração descobrir
- versão instalada;
- registry/ID real;
- API/event/boundary;
- authority server/client;
- persistência;
- escolha/troca entre opções;
- cooldown/recurso;
- damage/heal/ownership;
- tags/schools/affinities;
- compatibilidade com addons;
- VFX;
- fallback/fail-closed;
- risco de dupla aplicação.

### Regra
Primeiro tentamos implementar a fantasia aprovada. Se não existir boundary seguro, registramos limitação, alternativa coerente ou fail-closed; não redesenhamos toda a perk só para aproveitar um hook conveniente.

---

## Fase 7 — binding e implementação

- congelar IDs editoriais;
- definir `ResourceLocation` runtime estável;
- migrar schema/loaders onde necessário;
- implementar adapters, inclusive conhecimento de Ability e afinidades quando necessário;
- implementar exclusividade intragrupo e coexistência entre LEFT/RIGHT/METAMORPHOSIS;
- implementar mecânicas assinatura sem duplicar authority dos providers;
- testar escolha/troca, persistência, save/reload, multiplayer e ausência de mod opcional;
- reaproveitar `✅-01` a `✅-05` onde continuarem válidos.

---

## Fase 8 — topologia, UX e documentação final

Somente depois do catálogo e runtime estarem estáveis:

- desenhar posição visual das árvores;
- posicionar especializações como ramificações;
- desenhar interfaces das mecânicas assinatura;
- definir conexões/gateways;
- gerar wiki;
- adicionar validators de quota/IDs/links;
- criar drift gate.

---

## Ordem obrigatória resumida

```
MODLIST
  ↓
CAPACIDADES
  ↓
11 ÁRVORES
  ↓
ESPECIALIZAÇÕES + MECÂNICAS ASSINATURA
  ↓
PERKS CONCEITUAIS
  ↓
AUDITORIA TÉCNICA DOS MODS
  ↓
IMPLEMENTAÇÃO
  ↓
TOPOLOGIA / UX / WIKI
```

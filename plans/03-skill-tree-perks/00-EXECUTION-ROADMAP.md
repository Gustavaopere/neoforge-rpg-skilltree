# Plano de execução — reconstrução completa das perks

## Objetivo

Reconstruir o sistema de perks a partir do modpack realmente instalado, sem herdar especializações, providers ou pressupostos do catálogo legado.

A regra central é: **primeiro entender o que o pack oferece; depois desenhar builds; somente então aprofundar APIs/hooks e implementar.**

Diablo IV e outros ARPGs podem ser estudados para identificar bons padrões de escolha, transformação e identidade de classe. Nenhum efeito, número, nome ou estrutura externa é obrigatório para este projeto.

A política de fontes e seleção de Abilities está em [`SOURCES-AND-SELECTION-POLICY.md`](./SOURCES-AND-SELECTION-POLICY.md).

---

## Fase 0 — congelamento e limpeza do legado editorial

- não expandir o catálogo enquanto a modlist não estiver reconciliada;
- remover especializações herdadas automaticamente do runtime antigo;
- preservar somente infraestrutura técnica reaproveitável;
- tratar exemplos existentes como pilotos conceituais.

---

## Fase 1 — fechar a modlist física atual

### Objetivo
Criar uma única autoridade de presença, versão e identidade dos mods instalados.

### Trabalho
1. reconciliar `PROJECT-INSTRUCTIONS/modlist` com o pack atual;
2. corrigir removidos/substituídos/atualizados;
3. eliminar contradições entre os guias;
4. classificar provider/addon/bridge/library/UI/cosmético/worldgen;
5. cruzar `PROJECT-INSTRUCTIONS/datapacks` porque datapacks podem alterar comportamento carregado;
6. registrar remoções explicitamente.

Resource packs e shaders são inventariados como contexto de apresentação, não como prova de capacidade de gameplay.

### Gate
Existe uma fonte única e auditável de presença/versionamento.

---

## Fase 2 — matriz de capacidades do pack

### Fontes obrigatórias

- modlist reconciliada;
- datapacks;
- Minecraft vanilla;
- projetos próprios;
- `Gustavaopere/Black-Arcana/wiki/modpack-catalog/providers` para capacidades mágicas já catalogadas;
- addons/bridges que adicionem capacidade jogável real;
- resource packs e shaders apenas quando apresentação/VFX forem pertinentes.

### Objetivo
Descobrir o que o jogador pode fazer, sem ainda criar perks individuais.

Para cada capacidade registrar fantasia, tipo, domínio candidato, progressão nativa, potencial de Standard Perks, candidatura a Transmutation, relação com especializações e boundaries conhecidos.

### Decisão de seleção

O primeiro sistema de Transmutation usa **seleção fixa e curada**.

Durante esta fase serão escolhidas as Abilities que realmente merecem raízes `Txxxx`. Não é requisito dar Transmutation a toda skill existente nem aceitar escolha arbitrária do jogador.

A seleção deve cobrir todas as árvores e incluir capacidades não mágicas quando aplicável: weapon skills, attacks, stances, movement, summons, defensive skills, survival/mining/engineering/logistics actions e vanilla.

A lista concreta e os critérios finais de ranking serão traçados durante a execução, não antecipados neste plano.

### Gate
Existe um inventário suficiente para comparar candidatas sem favorecer magia apenas porque há mais providers mágicos.

---

## Fase 3 — matriz das 11 árvores

`MARTIAL`, `AGILITY`, `VITALITY`, `HEALING`, `ARCANE`, `ENGINEERING`, `MINING`, `SURVIVAL`, `SUMMONING`, `OCCULT` e `LOGISTICS`.

Um mod pode alimentar várias árvores; mod não é árvore. Cada capacidade recebe owner principal para contagem.

### Gate
As 11 árvores possuem identidade e cobertura suficientes sem filler.

---

## Fase 4 — especializações e mecânicas assinatura

Especialização é ramificação temática, não pasta por mod.

Cada especialização deve possuir uma **Mecânica Assinatura** que altere decisões de build/gameplay.

Padrões de inspiração incluem afinidades/espíritos/patronos, funções e sacrifícios de summons, caça/captura/selamento, juramentos, posturas, contratos e sistemas equivalentes.

A mecânica não é cópia literal de Diablo; nasce das capacidades reais do pack.

O mesmo total de perks de especialização por árvore é obrigatório. Quantidade exata de especializações e perks por especialização só é congelada após a matriz de capacidades.

---

## Fase 5 — catálogo conceitual

### Standard Perks
Definir intenção, owner, sinergias e providers candidatos.

### Transmutation Perks

Cada Ability **selecionada** recebe uma única raiz `Txxxx`.

- LEFT `.11`–`.19`: mínimo 2, máximo 9, escolher 1.
- RIGHT `.21`–`.29`: mínimo 2, máximo 9, escolher 1.
- METAMORPHOSIS `.31`–`.39`: mínimo 3, máximo 9, escolher 1.

Configuração: `1 LEFT + 1 RIGHT + 1 METAMORPHOSIS`.

Metamorphosis pode alterar affinity/school, geometria, trajetória, targeting, forma de entrega, resource model ou função.

Quando houver conversão de afinidade, usar `Affinity` como abstração e só oferecer escolas/elementos que possam ser convertidos semanticamente, não apenas visualmente.

### Specialization Perks
As perks devem alimentar a Mecânica Assinatura da especialização.

---

## Fase 6 — auditoria técnica por provider

Para cada conceito aprovado: versão, registry/ID, API/event/boundary, authority server/client, persistência, escolhas, recursos, dano/heal/ownership, tags/schools/affinities, compatibilidade, VFX e fail-closed.

Primeiro tentamos implementar a fantasia aprovada. Ausência de hook seguro vira limitação explícita, não bônus genérico inventado.

---

## Fase 7 — binding e implementação

- congelar IDs;
- definir runtime IDs;
- migrar schema/loaders;
- implementar adapters;
- implementar exclusividade intragrupo;
- implementar mecânicas assinatura;
- testar persistência/multiplayer/ausência de mod opcional;
- reaproveitar `✅-01` a `✅-05` somente onde ainda forem válidos.

---

## Fase 8 — topologia, UX e wiki

Só depois do catálogo/runtime:
- layout visual;
- ramificações;
- UI das mecânicas assinatura;
- gateways;
- wiki;
- validators e drift gate.

## Ordem

```
MODLIST + DATAPACKS + CATÁLOGOS
  ↓
CAPACIDADES
  ↓
SELEÇÃO CURADA DE ABILITIES
  ↓
11 ÁRVORES
  ↓
ESPECIALIZAÇÕES + MECÂNICAS ASSINATURA
  ↓
PERKS CONCEITUAIS
  ↓
AUDITORIA TÉCNICA
  ↓
IMPLEMENTAÇÃO
  ↓
TOPOLOGIA / UX / WIKI
```

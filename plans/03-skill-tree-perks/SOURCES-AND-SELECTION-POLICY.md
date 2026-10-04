# Fontes e política de seleção das Transmutation Abilities

## 1. Decisão de arquitetura

O primeiro catálogo do RPG Skill Tree usará **seleção fixa e curada de Abilities elegíveis a Transmutation**.

Não serão objetivos iniciais:

- oferecer Transmutation para toda Ability existente no modpack;
- permitir que o jogador escolha arbitrariamente qualquer Ability e exigir que o sistema gere três grupos automaticamente;
- duplicar a mesma Ability em mais de uma raiz de Transmutation.

A lista concreta de Abilities só será escolhida durante a execução da matriz de capacidades. Este documento fixa apenas a estratégia.

## 2. Por que seleção curada

Uma seleção fixa permite:

- manter a mesma quantidade de raízes `Txxxx` nas 11 árvores;
- garantir que cada Ability selecionada sustente LEFT, RIGHT e METAMORPHOSIS com opções relevantes;
- evitar opções artificiais em capacidades que não têm espaço mecânico suficiente;
- auditar providers e hooks caso a caso;
- balancear magia e não magia pelo mesmo orçamento estrutural;
- produzir VFX, UI, persistência e testes com qualidade controlável.

Se futuramente existir uma abstração técnica realmente genérica e segura, elegibilidade dinâmica pode ser reavaliada como expansão separada. Ela não é requisito do primeiro sistema.

## 3. Cobertura universal de arquétipos

Transmutation não é um sistema de magia.

A matriz deve procurar Abilities candidatas em todas as árvores, incluindo quando aplicável:

- spells e rituais;
- weapon skills e técnicas de combate;
- ataques especiais;
- stances;
- movement/mobility;
- summons e comandos de companions;
- defensive skills;
- survival actions;
- mining/extraction actions;
- engineering/technology actions;
- logistics/automation actions;
- vanilla abilities/actions quando forem transformáveis de forma legítima.

A igualdade entre árvores é medida pelo mesmo `N_transmutation_per_tree`.

## 4. Fontes obrigatórias na execução

### 4.1 Presença física e versão

Fonte primária:

`Gustavaopere/neoforge-rpg-skilltree/PROJECT-INSTRUCTIONS/modlist`

A modlist decide se o mod/provider está atualmente no pack e qual versão/documentação de presença deve ser considerada. Um catálogo externo não pode ressuscitar provider removido.

### 4.2 Datapacks

Fonte:

`PROJECT-INSTRUCTIONS/datapacks`

Datapacks devem ser cruzados porque podem alterar recipes, tags, world/content integration, compatibilidade e outros comportamentos carregados pelo pack.

Eles não são automaticamente providers de Transmutation; primeiro é necessário identificar se alteram ou adicionam capacidade jogável pertinente.

### 4.3 Catálogo mágico do Black Arcana

Fonte:

`Gustavaopere/Black-Arcana/wiki/modpack-catalog/providers`

É a fonte canônica já existente para inventário de capacidades mágicas catalogadas por provider.

Ela é usada para acelerar a Fase 2, especialmente spells, glyphs, rituals, summons e systems mágicos.

O status `✅/⚠️/⛔` desse catálogo descreve a catalogação/evidência daquele provider e não substitui a confirmação de presença física da modlist.

### 4.4 Resource packs

Fonte:

`PROJECT-INSTRUCTIONS/resource-packs`

Resource packs podem informar apresentação, identidade visual, modelos, animações e compatibilidade estética.

Eles **não** provam capacidade de gameplay, API ou hook.

São relevantes principalmente quando uma Transmutation exige nova apresentação coerente com os assets realmente usados pelo pack.

### 4.5 Shaders

Fonte:

`PROJECT-INSTRUCTIONS/shaders`

Shaders são contexto de apresentação e compatibilidade visual. Não são authority de gameplay.

VFX de perks devem considerar o stack visual real, mas shader presence nunca cria uma perk nem prova um hook.

### 4.6 Vanilla e projetos próprios

Minecraft vanilla e os projetos próprios do modpack também entram na matriz de capacidades, mesmo quando não aparecem como provider externo na modlist.

## 5. Hierarquia de autoridade

Quando as fontes divergirem:

1. presença física/versionamento atual do pack;
2. comportamento/configuração efetivamente carregada, incluindo datapacks;
3. documentação/catálogo funcional do provider;
4. source/API/runtime auditado;
5. resource packs/shaders para apresentação;
6. referências externas de design.

Diablo IV e outros jogos ficam fora da authority do pack: são apenas inspiração.

## 6. Seleção concreta — deliberadamente adiada

Durante a execução, as Abilities candidatas serão comparadas antes de receber IDs `Txxxx`.

A seleção deve considerar, entre outros fatores que serão definidos naquela fase:

- espaço real para decisões LEFT/RIGHT/METAMORPHOSIS;
- identidade de build;
- ausência de redundância;
- relevância para o domínio;
- viabilidade técnica;
- qualidade da transformação possível;
- cobertura justa entre estilos mágicos e não mágicos.

Nenhuma lista concreta de “melhores skills” é congelada neste documento.

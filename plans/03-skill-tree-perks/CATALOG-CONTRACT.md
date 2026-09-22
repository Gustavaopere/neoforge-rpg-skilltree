# Contrato do novo catálogo de perks

## 1. Unidades de progressão

O catálogo possui quatro famílias distintas:

1. **Internal Attributes** — seis atributos fundamentais já canônicos no runtime.
2. **Standard Perks** — passivos e regras de build que não dependem de modificar uma habilidade concreta.
3. **Transmutation Perks** — nós-base que exigem uma Ability conhecida/desbloqueada e abrem modificadores exclusivos.
4. **Specialization Perks** — perks que pertencem a uma especialização formal e respeitam seus gateways.

## 2. Paridade obrigatória entre as 11 árvores

As 11 árvores canônicas são:

`MARTIAL`, `AGILITY`, `VITALITY`, `HEALING`, `ARCANE`, `ENGINEERING`, `MINING`, `SURVIVAL`, `SUMMONING`, `OCCULT` e `LOGISTICS`.

Para cada família contável, todas as árvores devem possuir exatamente a mesma quantidade:

- `STANDARD_PERKS_PER_TREE = N_standard`
- `TRANSMUTATIONS_PER_TREE = N_transmutation`
- `SPECIALIZATION_PERKS_PER_TREE = N_specialization`

Os valores finais de `N_*` serão congelados somente depois de existir um piloto de conteúdo suficiente para as 11 árvores. É proibido completar quota com perks artificiais apenas para atingir um número.

A árvore interna é comum a todas e contém exatamente os seis atributos canônicos atuais.

## 3. O que conta como uma Transmutation Perk

Somente o nó-base `Txxxx` conta para `N_transmutation`.

Os filhos `Txxxx.n` são modificadores internos daquela transmutação e **não** entram na contagem de paridade.

Cada transmutação deve possuir no mínimo:

- 2 opções no ramo esquerdo;
- 2 opções no ramo direito;
- 3 opções de metamorfose.

As escolhas dentro de cada ramo são mutuamente exclusivas, salvo exceção explicitamente documentada.

## 4. Ability, não Spell

A camada de transmutação trabalha com o conceito genérico `Ability`, que pode representar spell, weapon skill, active skill, summon, stance, movement ou outra capacidade integrável.

Uma transmutação só fica disponível quando a Ability exigida estiver efetivamente conhecida/desbloqueada. Estar temporariamente equipada não é suficiente.

A origem desse conhecimento deve passar por um adapter, por exemplo `AbilityKnowledgeProvider`. Para Iron's, Spell Codex é um candidato natural para a fonte persistente de spells descobertos; Spell Actionbar é interface/casting, não deve ser tratada automaticamente como autoridade de conhecimento.

## 5. Ownership

Toda perk possui exatamente uma árvore proprietária para fins de contagem e numeração, mesmo quando seus requisitos ou efeitos atravessam múltiplos domínios.

Especializações podem ser híbridas; isso não duplica a mesma perk em duas quotas.

## 6. Valores e balanceamento

Arquivos podem registrar uma referência de design externa, mas valores finais de Minecraft devem ser validados contra o provider real, a versão instalada e o pipeline do RPG Skill Tree. Referência de Diablo IV não é valor autoritativo de balanceamento do modpack.

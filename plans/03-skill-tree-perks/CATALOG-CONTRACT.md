# Contrato do novo catálogo de perks

## 1. Famílias

1. **Internal Attributes** — seis atributos fundamentais comuns.
2. **Standard Perks** — passivos e regras de build que não modificam uma Ability concreta.
3. **Transmutation Perks** — uma raiz `Txxxx` por Ability, com três grupos `Txxxx.xx`.
4. **Specialization Perks** — perks de uma ramificação temática, ligadas a uma mecânica assinatura.

## 2. As 11 árvores

`MARTIAL`, `AGILITY`, `VITALITY`, `HEALING`, `ARCANE`, `ENGINEERING`, `MINING`, `SURVIVAL`, `SUMMONING`, `OCCULT` e `LOGISTICS`.

## 3. Paridade

Obrigatório:
- mesmo `N_standard_per_tree`;
- mesmo `N_transmutation_per_tree`;
- mesmo `N_specialization_per_tree`.

Preferencial, a congelar somente após a matriz de capacidades:
- mesmo `K_specializations_per_tree`;
- mesmo `M_perks_per_specialization`.

A prioridade é equilíbrio sem filler. Se a simetria de K/M produzir especializações artificiais, preservamos primeiro o mesmo orçamento total de perks por árvore e redesenhamos a distribuição.

## 4. Especializações

Especialização é uma ramificação temática, não um mod, escola ou provider.

Cada especialização aprovada deve possuir uma **Mecânica Assinatura** que crie decisões próprias e seja alimentada por suas perks.

Exemplos de famílias de mecânica:
- afinidade/patrono/espírito;
- funções e sacrifícios de summons;
- caça/captura/selamento;
- posturas/juramentos/contratos;
- doutrinas de crafting/engenharia;
- outros sistemas equivalentes sustentados pelo pack.

A forma pode variar, mas o orçamento de poder e profundidade deve ser comparável.

## 5. Transmutation Perks

Somente a raiz `Txxxx` conta para `N_transmutation_per_tree`.

### Unicidade
Cada Ability possui uma única raiz no catálogo.

### Grupos
- LEFT: `.11`–`.19`, mínimo 2, máximo 9, escolher 1.
- RIGHT: `.21`–`.29`, mínimo 2, máximo 9, escolher 1.
- METAMORPHOSIS: `.31`–`.39`, mínimo 3, máximo 9, escolher 1.

Os três grupos coexistem: `1 LEFT + 1 RIGHT + 1 METAMORPHOSIS`.

A quantidade de opções internas pode variar por Ability; a paridade mede raízes.

### Affinity/School Transformation

Conversões profundas devem usar uma abstração de **Affinity**, porque Fire/Ice/Lightning podem se comportar como elementos enquanto Holy/Blood/Ender podem ser escolas ou afinidades do provider.

Uma metamorfose de afinidade pode oferecer várias escolhas internas, mas apenas uma afinidade fica configurada por vez.

A lista permitida é provider-aware e será definida após a modlist. Uma affinity só entra se a conversão puder preservar coerentemente damage semantics, scaling, status, resistências, tags e apresentação necessária.

Troca apenas de cor/VFX não satisfaz uma Transmutação de Afinidade.

## 6. Ability, não Spell

`Ability` pode representar spell, weapon skill, active skill, summon, stance, movement, ação vanilla ou outra capacidade integrável.

Vanilla faz parte da matriz.

Uma raiz só fica disponível quando a Ability exigida estiver conhecida/desbloqueada segundo a authority pertinente.

## 7. Referências externas

Diablo e outros jogos são fontes de padrões e perguntas de design, não specifications.

Podemos adaptar uma ideia, generalizá-la, combinar padrões ou rejeitá-la completamente se não servir ao modpack.

## 8. Ownership

Toda perk possui exatamente uma árvore proprietária para contagem. Um provider pode alimentar várias árvores.

## 9. Balanceamento

Valores externos não são autoridade. Números finais são decididos contra Minecraft, providers instalados, sinergias do pack e testes.

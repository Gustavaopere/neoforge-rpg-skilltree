# Estado atual do RPG Skill Tree

> **Snapshot documentado:** `main` em 28/08/2026. Esta página descreve somente comportamento e conteúdo presentes no repositório nesse ponto. Planos e ideias futuras não são apresentados como funcionalidades disponíveis.

O RPG Skill Tree já possui uma base funcional ampla de progressão, árvore, classes, masteries, escalonamento, integrações e sincronização cliente/servidor. Ao mesmo tempo, nem todo nó ou especialização visualmente existente corresponde a um bônus numérico ou integração profunda.

## O que está implementado

### Progressão do jogador

- nível de personagem independente da experiência vanilla;
- XP de progressão e pontos passivos;
- compra e estado de nós da árvore;
- masteries por práticas reconhecidas;
- estados de classes e especializações;
- registro de progressão/recompensas relevantes;
- persistência por Data Attachments;
- sincronização do estado autoritativo do servidor para o cliente.

O servidor é a autoridade para progressão, requisitos, custos, efeitos e mutações persistentes. A UI não decide se um desbloqueio é válido.

### Árvore e dados

O datapack atual contém as famílias de dados usadas por:

- arquétipos;
- recompensas de chefes;
- escolhas e regras de classe;
- classes;
- efeitos e regras de nós;
- progressão;
- skill trees;
- skills;
- especializações;
- morph categories;
- tags;
- arquitetura semântica da árvore;
- compendium.

A Árvore Principal possui **512 nós materializados**. Isso não significa 512 bônus numéricos diferentes: alguns nós são caminhos, gateways, requisitos ou pontos de estrutura.

### Classes e identidades emergentes

O mod distingue investimento, mastery e identidade de classe. Em termos práticos, a classe não é apenas um botão de seleção: regras de progressão podem reconhecer padrões de prática e investimento para desbloquear identidades e subárvores.

A wiki detalhada continua em [Classes](CLASSES.md), [Masteries](MASTERIES.md) e [Especializações](SPECIALIZATIONS.md).

### Efeitos de perks

Efeitos declarados por nós podem modificar atributos de Minecraft e de mods integrados. A composição de modificadores segue uma ordem determinística no núcleo de efeitos, evitando depender da ordem acidental de carregamento.

Consulte [Perks e habilidades](PERKS.md) e [Estatísticas dos efeitos](EFFECT_CATALOG.md) para os efeitos atualmente catalogados.

### Recompensas de chefes

Há infraestrutura e dados para recompensas de progressão por chefes, com resolução do identificador da recompensa e persistência do estado de progressão. A recompensa precisa corresponder às regras/dados carregados; a wiki não presume que toda entidade chamada de boss por outro mod seja automaticamente reconhecida.

### Escalonamento de entidades

O runtime possui estado persistente/serializável de escalonamento de entidades e inicializadores próprios. O comportamento detalhado está em [Escalonamento do mundo](WORLD_SCALING.md).

### UI e rede

O projeto possui tela da árvore e payloads de sincronização. O estado que o cliente apresenta deriva do servidor; datapacks e progressão autoritativa não são decididos pelo cliente.

## Integrações atualmente presentes

Há código de integração/awards para múltiplos ecossistemas, incluindo:

- Iron's Spells 'n Spellbooks;
- Ars Nouveau;
- Epic Fight;
- Goety;
- Malum;
- Eidolon: Repraised;
- Identity2 / morphs.

Também existem definições e caminhos temáticos ligados a Create e outros mods tecnológicos. A existência de um caminho ou nome de especialização **não prova por si só** que máquinas desse mod recebam um bônus runtime. Só consideramos integração funcional quando há hook, atributo, policy ou adapter correspondente.

Veja [Interações com outros mods](MOD_INTERACTIONS.md) e [Interações ocultas e não óbvias](HIDDEN_INTERACTIONS.md).

## Compendium: infraestrutura vs. conteúdo

O código possui uma infraestrutura de compendium com categorias, descoberta, entradas, relações, proveniência e verificações de cobertura. Porém, no snapshot atual, `data/rpgskilltree/compendium/entries/` contém somente `pig.json`.

Portanto:

- a infraestrutura de compendium existe;
- ela **não** deve ser descrita como uma enciclopédia in-game já preenchida com todo o RPG;
- a pasta `wiki/` continua sendo a fonte humana mais abrangente de documentação de gameplay.

## O que não deve ser inferido

- Um nó existir não significa que ele tenha efeito numérico próprio.
- Uma especialização existir não significa que o mod externo correspondente tenha integração runtime completa.
- Um nome como “Create”, “AE2” ou “Oritech” não significa, sozinho, bônus universal a máquinas.
- Um texto de fantasia/lore não substitui uma implementação em código ou dados.

## Fontes no repositório

Principais pontos de verdade usados para esta página:

- `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/`
- `src/main/resources/data/rpgskilltree/`
- `src/main/resources/data/rpgskilltree/compendium/`
- `AGENTS.md`

Para problemas conhecidos de implementação e diferenças entre estado desejado e estado verificado, consulte [Limitações conhecidas](KNOWN_LIMITATIONS.md).
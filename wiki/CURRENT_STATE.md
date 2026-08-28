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

O datapack atual contém famílias para arquétipos, recompensas de chefes, escolhas/regras de classe, classes, efeitos/regras de nós, progressão, skill trees, skills, especializações, morph categories, tags, arquitetura semântica e compendium.

A Árvore Principal possui **512 nós materializados**. Isso não significa 512 bônus numéricos diferentes: alguns nós são caminhos, gateways, requisitos ou pontos de estrutura.

### Classes e identidades emergentes

O mod distingue investimento, mastery e identidade de classe. Regras de progressão podem reconhecer padrões de prática e investimento para desbloquear identidades e subárvores. Veja [Classes](CLASSES.md), [Masteries](MASTERIES.md) e [Especializações](SPECIALIZATIONS.md).

### Efeitos de perks

Efeitos declarados por nós podem modificar atributos de Minecraft e de mods integrados. A composição usa ordem determinística no núcleo de efeitos. Consulte [Perks e habilidades](PERKS.md) e [Estatísticas dos efeitos](EFFECT_CATALOG.md).

### Recompensas de chefes e escalonamento

Há infraestrutura/data-driven rules para recompensas de chefes e runtime persistente/serializável de escalonamento de entidades. A presença de uma entidade chamada de boss por outro mod não garante reconhecimento sem correspondência nas regras carregadas.

### UI e rede

A árvore e os payloads de sincronização apresentam o estado do servidor. Datapacks e progressão autoritativa não são decididos pelo cliente.

## Integrações atualmente presentes

Há código de integração/awards para Iron's Spells 'n Spellbooks, Ars Nouveau, Epic Fight, Goety, Malum, Eidolon: Repraised e Identity2/morphs. Existem também definições e caminhos temáticos tecnológicos; nome de especialização não prova integração runtime profunda.

Veja [Interações com outros mods](MOD_INTERACTIONS.md) e [Interações ocultas e não óbvias](HIDDEN_INTERACTIONS.md).

## Compendium: infraestrutura vs. conteúdo

O código possui infraestrutura de compendium com categorias, descoberta, entradas, relações, proveniência e cobertura. No snapshot atual, porém, `data/rpgskilltree/compendium/entries/` contém somente `pig.json`.

Logo, a infraestrutura existe, mas não deve ser descrita como enciclopédia in-game já preenchida com todo o RPG. A pasta `wiki/` continua sendo a documentação humana mais abrangente.

## O que não deve ser inferido

- um nó existir não significa bônus numérico próprio;
- uma especialização existir não significa integração runtime completa;
- nomes como Create, AE2 ou Oritech não significam bônus universal de máquina;
- lore/fantasia não substitui implementação em código ou dados.

## Fontes no repositório

- `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`
- `src/main/java/dev/gustavopere/rpgskilltree/core/`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/`
- `src/main/resources/data/rpgskilltree/`
- `src/main/resources/data/rpgskilltree/compendium/`
- `AGENTS.md`

Veja também [Limitações conhecidas](KNOWN_LIMITATIONS.md).
# RPG Skill Tree — Wiki

Esta pasta é a enciclopédia de **gameplay** do RPG Skill Tree. Ela descreve o que o jogador encontra no jogo: progressão, árvore, classes, especializações, perks, estatísticas, sinergias, interações com outros mods, comportamentos menos óbvios e curiosidades.

Aqui não ficam planos de desenvolvimento. Planos pertencem a `plans/`; documentação técnica de implementação pode existir em `docs/`. Quando um detalhe interno influencia diretamente o gameplay, a wiki pode explicá-lo em linguagem de jogador e apontar a fonte correspondente.

> **Regra editorial:** a wiki documenta o `main` atual. Uma ideia no roadmap, nome de perk ou texto de fantasia não é tratada como mecânica até existir implementação correspondente em código/dados.

## Comece aqui

- [Estado atual do mod](CURRENT_STATE.md)
- [Como começar](GETTING_STARTED.md)
- [Progressão](PROGRESSION.md)
- [Árvore de Habilidades](SKILL_TREE.md)
- [Classes](CLASSES.md)
- [Masteries](MASTERIES.md)
- [Especializações](SPECIALIZATIONS.md)
- [Perks e habilidades](PERKS.md)
- [Catálogo dos 512 nós](PERK_CATALOG.md)
- [Estatísticas dos efeitos](EFFECT_CATALOG.md)
- [Combate e magia](COMBAT_AND_MAGIC.md)
- [Escalonamento do mundo](WORLD_SCALING.md)
- [Interações com outros mods](MOD_INTERACTIONS.md)
- [Interações ocultas e não óbvias](HIDDEN_INTERACTIONS.md)
- [Compatibilidade](COMPATIBILITY.md)
- [Comportamento técnico relevante ao gameplay](INTERNALS.md)
- [Limitações conhecidas](KNOWN_LIMITATIONS.md)
- [Trivia](TRIVIA.md)
- [Glossário](GLOSSARY.md)

## Como interpretar as páginas

A wiki usa quatro categorias implícitas:

- **implementado e ativo:** há código/dados atuais sustentando o comportamento;
- **presente como estrutura:** existe catálogo, gateway, contrato ou UI, mas não necessariamente um bônus final;
- **comportamento não óbvio:** efeito real que pode não estar explicado integralmente no texto do jogo;
- **não implementado/limitado:** planos ou definições que não devem ser confundidos com funcionalidade disponível.

## Sobre os 512 nós

A Árvore Principal possui **512 nós materializados**, mas esse número não equivale a 512 bônus numéricos únicos. Alguns nós são caminhos, requisitos ou gateways; outros podem depender de um atributo/provider opcional para materializar efeito.

Quando uma página apresenta uma estatística como efeito atual, ela deve corresponder a uma definição e binding atualmente sustentados. Quando descreve apenas função estrutural, identidade de classe ou fantasia, isso é indicado pelo contexto.

## Sobre conteúdo em desenvolvimento

O mod continua crescendo. A wiki deve acompanhar o código, não antecipá-lo. Ao encontrar divergência entre wiki e implementação, a implementação do `main` e os dados carregados pelo servidor têm prioridade; a página deve então ser corrigida.
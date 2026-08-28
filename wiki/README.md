# RPG Skill Tree — Wiki

Esta pasta é a enciclopédia de **gameplay** do RPG Skill Tree. Ela descreve o que o jogador encontra no jogo: progressão, árvore, classes, especializações, perks, estatísticas, sinergias, integrações, comportamentos menos óbvios e curiosidades.

Planos de desenvolvimento pertencem a `plans/`; documentação puramente de implementação pertence a `docs/`. Quando um detalhe interno altera o gameplay, ele pode ser explicado aqui.

> **Regra editorial:** a wiki documenta o `main` atual. Roadmap, nome de perk e fantasia não viram mecânica até existir implementação correspondente.

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

- **implementado e ativo:** código/dados atuais sustentam o comportamento;
- **estrutura:** existe catálogo, gateway, contrato ou UI, mas não necessariamente bônus final;
- **não óbvio:** efeito real pouco evidente ao jogador;
- **limitado/não implementado:** não deve ser confundido com feature disponível.

A Árvore Principal possui **512 nós materializados**, mas isso não equivale a 512 bônus numéricos únicos. Alguns são caminhos/requisitos/gateways e alguns efeitos dependem de atributos/providers opcionais.

Se wiki e implementação divergirem, o `main` e os dados carregados pelo servidor prevalecem.
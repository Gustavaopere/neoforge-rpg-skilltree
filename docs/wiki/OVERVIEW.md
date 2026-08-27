# Visão geral

RPG Skill Tree é uma camada de progressão RPG server-authoritative para Minecraft NeoForge 1.21.1. O objetivo é transformar atributos, uso de armas/magia, exploração e integrações com mods em uma progressão coerente, sem exigir classes rígidas.

## O que o mod faz

- mantém XP/nível e estado RPG do jogador;
- fornece atributos e modificadores consumidos por perks;
- carrega árvores/skills por dados;
- calcula classes emergentes, masteries e especializações;
- fornece gateways para conteúdo avançado;
- escala ameaça por contexto de mundo/jogador, incluindo nível de entidade e raridade/arquetipagem;
- integra eventos reais de mods externos a mastery/gating;
- sincroniza o estado necessário para UI mantendo o servidor como autoridade.

## Filosofia

O personagem é definido por investimento e prática. Um jogador pode convergir para Mage, Warrior, Ranger, Tank, Artificer, Summoner ou híbridos conforme os contratos de classe e especialização evoluem, sem transformar a seleção inicial em lock absoluto.

## A árvore de 512 nós

A árvore principal atual possui **512 nós materializados**, não apenas um alvo histórico. O layout gerado declara `target_node_count = 512` e `actual_node_count = 512`.

A composição é: 28 nós Core + 420 nós nas 11 regiões principais + 48 bridges híbridas + 16 keystones externos = 512.

Importante: **nó materializado não é sinônimo de efeito mecânico distinto finalizado**. Vários JSONs de skill têm `bonuses: []`; a mecânica efetiva também pode vir de `node_effects/*.json` e de handlers runtime. A wiki separa, por isso, inventário de nós e catálogo de efeitos.
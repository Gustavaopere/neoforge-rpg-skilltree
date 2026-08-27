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

O personagem é definido por investimento e prática. Um jogador pode convergir para Mage, Warrior, Ranger, Tank, Artificer, Summoner ou híbridos como Spellblade/Battlemage/Arcane Archer/Technomancer conforme os contratos de classe e especialização evoluem, sem transformar a seleção inicial em lock absoluto.

## 512 vs 474

Documentos históricos descrevem um blueprint de 512 nós. A auditoria desta revisão encontra 474 arquivos JSON materializados em `src/main/resources/data/rpgskilltree/skills/main/`. Portanto, `512` é tratado como blueprint/meta; `474` é o inventário atual comprovado.
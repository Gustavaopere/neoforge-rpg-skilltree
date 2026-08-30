# 20.06 — Simulação de reinos NPC para singleplayer

## Objetivo

Criar oponentes/aliados políticos persistentes sem depender de outros jogadores.

## NPCRealmController

Cada realm NPC possui strategy profile data-driven, governance/economic state, resources, military, goals, known intelligence e decision schedule.

## Decisão

Avaliar em turnos/períodos world-time bounded:

1. atualizar snapshot econômico/militar;
2. processar treaties/obligations;
3. avaliar ameaças/oportunidades;
4. escolher intents limitados;
5. executar via serviços canônicos;
6. persistir decision provenance/seed.

Não permitir que AI escreva diretamente em wallets/territory ignorando rules.

## Personalidade

Profiles podem alterar pesos (expansionist, mercantile, isolationist etc.), mas não concedem cheats de recursos. Todos os realms obedecem accounting/war contracts.

## Fog of war

NPC não deve saber automaticamente tudo sobre jogador; usa intel state/espionage e dados publicamente observáveis.

## Testes

- deterministic reload;
- resource constraints;
- treaty compliance/breach;
- no omniscience;
- bounded decision frequency;
- multiple realms without tick explosion.

## Acceptance

Singleplayer tem política externa ativa sem simular players falsos.
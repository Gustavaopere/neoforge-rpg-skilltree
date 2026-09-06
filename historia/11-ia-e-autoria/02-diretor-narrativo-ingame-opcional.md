# Diretor Narrativo com IA In-Game — Opcional

## Estado
PLANEJADO / OPCIONAL / NÃO É AUTORIDADE.

## Objetivo
Permitir diálogos e microconteúdo emergente mais vivos sem entregar a um LLM autoridade sobre o save.

## Arquitetura

`Narrative & Society Core → contexto permitido → AI Narrative Director → proposta/intenção → validação do Narrative Core → ação/evento autorizado ou rejeitado`

## O que a IA pode propor
- diálogo contextual;
- rumor;
- reação emocional;
- pequena tarefa;
- intenção de deslocamento/interação;
- pedido de ajuda;
- microconflito;
- interpretação de fatos que o NPC realmente conhece.

## O que a IA não pode fazer sozinha
- declarar morte/ressurreição;
- criar ou remover item/recurso;
- mudar governo/lei/facção;
- conceder progressão/recompensa;
- inventar que um NPC sabe um segredo;
- criar capacidades de provider inexistentes;
- sobrescrever estado canônico;
- decidir que uma ação ocorreu sem execução/validação real.

## Providers candidatos
PlayerEngine/Player2, Entity Dialogue, Mamizou ou outros podem ser pesquisados/adaptados futuramente, mas precisam de auditoria específica da versão NeoForge 1.21.1 e compatibilidade com o pack antes de entrar no runtime.

## Regra
Mesmo que todos os providers de IA in-game sejam removidos, a campanha principal continua jogável e coerente.
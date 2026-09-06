# Pipeline de Autoria Assistida — Fora do Jogo

## Objetivo
Usar IA e ferramentas disponíveis ao agente para produzir grande volume de conteúdo consistente sem obrigar o jogador/usuário a ler o material e estragar a descoberta.

## Autor primário
O agente ChatGPT que trabalha no projeto pode criar, revisar e versionar diretamente os dossiês narrativos.

## Ferramentas auxiliares possíveis

- GitHub: fonte versionada, diff, PR, histórico e revisão.
- Web/GitHub/CurseForge/Modrinth: pesquisa de providers e mecânicas reais quando a história depender delas.
- Skills de game design/brainstorming: apoio a loops, agência, conflitos e testes de experiência; não são authority do cânone.
- Outliners/agentes genéricos instalados podem auxiliar organização ou segunda leitura quando agregarem valor, mas não substituem a fonte canônica.
- Não há, no conjunto consultado, um plugin especializado de worldbuilding/ficção que deva virar dependência obrigatória do processo.

## Processo sem spoilers

1. Ler o estado atual de `historia/` e os contratos pertinentes do Stage 08.
2. Definir escopo editorial do ciclo sem expor conteúdo oculto no chat.
3. Produzir/alterar arquivos narrativos internamente.
4. Fazer revisão de consistência:
   - causalidade;
   - cronologia;
   - knowledge/proveniência;
   - agendas de NPCs;
   - rotas SIM/NÃO/ANTES/DEPOIS;
   - oportunidade nunca descoberta;
   - morte/retorno;
   - providers reais;
   - anti-soft-lock;
   - consequências atrasadas.
5. Fazer revisão de surpresa: remover soluções óbvias, exposição excessiva e quests que dizem ao jogador tudo que está acontecendo.
6. Fazer revisão de agência: garantir múltiplas soluções e possibilidade legítima de não participar.
7. Versionar em branch/PR.
8. No chat, informar somente metadados e estado editorial por padrão.

## Regra de qualidade
IA pode sugerir conteúdo, mas não deve despejar geração bruta no cânone. Cada entrada precisa passar por coerência sistêmica e relação com o mundo existente.

## Regra anti-spoiler
O usuário não precisa aprovar o plot detalhado. Quando necessário, pedir aprovação apenas de tom, limites, intensidade, temas, escopo ou mudanças de regra estrutural.
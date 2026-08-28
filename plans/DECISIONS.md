# Decisões arquiteturais

## D001 — Runtime é autoridade sobre documentação histórica
Aceita. Specs antigas continuam úteis, mas não provam implementação.

## D002 — Progressão é server-authoritative
Aceita. XP, level, mastery, unlocks, gating e efeitos de gameplay não dependem de confiança no cliente.

## D003 — Conteúdo de árvore é data-driven
Aceita. IDs, requisitos e efeitos permanecem carregáveis/validáveis por dados quando o sistema oferece esse contrato.

## D004 — 512 é o orçamento materializado da Árvore Principal, não sinônimo de 512 efeitos distintos
Aceita. Alguns nós são estruturais ou gateways; efeitos também vivem em packs e handlers.

## D005 — Wiki de jogador vive em `wiki/`, na raiz
Aceita. `wiki/` não contém arquitetura, tarefas de programação ou guia de desenvolvedor. Ela documenta gameplay, estatísticas, requisitos, interações e trivia. Conteúdo técnico pertence a `plans/` ou `docs/technical/`.

## D006 — Integração genérica continua genérica
Aceita. Um modificador de dano/poder mágico só é atribuído nominalmente a um spell externo quando existe evidência específica dessa relação.

## D007 — Mods opcionais não viram dependência dura por acidente
Aceita. Adapters devem ser isolados e protegidos por presença/contratos apropriados.

## D008 — Create/AE2/Oritech não são anunciados como runtime completo sem adapter comprovado
Aceita. Especializações, gateways ou nomes de perks podem existir sem interceptação completa de máquinas/redes.

## D009 — IDs persistidos são API de save
Aceita. Renomear/remover skill, classe, mastery ou especialização exige compatibilidade ou migração explícita.

## D010 — Um evento de gameplay concede progressão uma vez
Aceita. Bridges devem preferir confirmação semântica real e idempotência.

## D011 — Estado de plano é expresso no nome do arquivo
Aceita. Cada estágio usa `PLANO.md` enquanto aberto e só é renomeado para `PLANO-✅.md` após implementação, validação e integração canônica completas.

## D012 — O plano de estágio é executável
Aceita. Cada `PLANO.md` registra objetivo, dependências, ordem de implementação, migração/dados, testes, critérios de aceite e definição explícita de pronto.
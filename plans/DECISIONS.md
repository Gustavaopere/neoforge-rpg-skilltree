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
Aceita. `wiki/` não contém arquitetura, tarefas de programação ou guia de desenvolvedor.

## D006 — Integração genérica continua genérica
Aceita. Um modificador só é atribuído nominalmente a conteúdo externo quando existe evidência específica dessa relação.

## D007 — Mods opcionais não viram dependência dura por acidente
Aceita. Adapters devem ser isolados e protegidos por presença/contratos apropriados.

## D008 — Create/AE2/Oritech não são anunciados como runtime completo sem adapter comprovado
Aceita. Especializações, gateways ou nomes de perks podem existir sem interceptação completa de máquinas/redes.

## D009 — IDs persistidos são API de save
Aceita. Renomear/remover skill, classe, mastery ou especialização exige compatibilidade ou migração explícita.

## D010 — Um evento de gameplay concede progressão uma vez
Aceita. Bridges devem preferir confirmação semântica real e idempotência.

## D011 — Planos seguem o padrão Volcanoes
Aceita. Cada estágio tem `README.md` + múltiplos arquivos numerados. Trabalho concluído é marcado renomeando `01-x.md` para `✅-01-x.md`.

## D012 — O check pertence ao subplano
Aceita. Não é necessário esperar o estágio inteiro terminar para marcar um arquivo; porém o arquivo só recebe check depois de implementação, validação e integração completas do seu próprio escopo.

## D013 — Cada subplano é executável isoladamente
Aceita. Todo arquivo numerado deve conter objetivo, passos de implementação, testes/validação e critério de aceite, respeitando dependências causais anteriores.
# Decisões arquiteturais

## D001 — Runtime é autoridade sobre documentação histórica
Aceita. Specs antigas continuam úteis, mas não provam implementação.

## D002 — Progressão é server-authoritative
Aceita. XP, level, mastery, unlocks, gating e efeitos de gameplay não dependem de confiança no cliente.

## D003 — Conteúdo de árvore é data-driven
Aceita. IDs, requisitos e efeitos devem permanecer carregáveis/validáveis por dados quando o sistema já oferece esse contrato.

## D004 — 512 não significa 512 perks implementadas
Aceita. `512` representa o blueprint/capacidade histórica; a revisão de 2026-08-27 encontra 474 arquivos de nós materializados em `skills/main`.

## D005 — Wiki não duplica silenciosamente payload de 474 JSON
Aceita. `PERK_CATALOG.md` indexa todo o inventário materializado e aponta os arquivos JSON como autoridade de efeitos. A evolução desejada é geração automática de páginas/tabelas detalhadas.

## D006 — Integração genérica continua genérica
Aceita. Ex.: um modificador de dano mágico só pode ser atribuído nominalmente a um feitiço externo se houver handler/tag/contrato que prove essa relação.

## D007 — Mods opcionais não viram dependência dura por acidente
Aceita. Adapters devem ser isolados e protegidos por detecção de mod/contratos apropriados.

## D008 — Create/AE2 não serão anunciados como runtime completo sem adapter comprovado
Aceita. Definições de progressão/especialização podem existir e serão rotuladas `SPEC/DATA` enquanto isso.

## D009 — IDs persistidos são API de save
Aceita. Renomear/remover IDs de skill, classe, mastery ou especialização exige compatibilidade ou migração explícita.

## D010 — Um evento de gameplay concede progressão uma vez
Aceita. Bridges de mods devem preferir confirmação semântica real do evento e idempotência, evitando XP/mastery duplicados por eventos auxiliares.
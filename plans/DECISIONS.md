# Decisões arquiteturais

## D001 — Runtime é autoridade sobre documentação histórica
Aceita. Specs antigas continuam úteis, mas não provam implementação.

## D002 — Progressão é server-authoritative
Aceita. XP, level, mastery, unlocks, gating e efeitos de gameplay não dependem de confiança no cliente.

## D003 — Conteúdo de árvore é data-driven
Aceita. IDs, requisitos e efeitos devem permanecer carregáveis/validáveis por dados quando o sistema já oferece esse contrato.

## D004 — 512 é o orçamento materializado da árvore principal, não sinônimo de 512 efeitos mecânicos distintos
Aceita. A revisão atual comprova 512 nós JSON e layout 512/512. Alguns nós ainda são estruturais/esqueleto e não possuem bônus inline próprio.

## D005 — Wiki separa inventário de nós de catálogo de efeitos
Aceita. `PERK_CATALOG.md` indexa todos os 512 IDs materializados; `EFFECT_CATALOG.md` documenta as entradas de atributo atualmente declaradas. Os JSONs continuam sendo a autoridade.

## D006 — Integração genérica continua genérica
Aceita. Ex.: um modificador de dano mágico só pode ser atribuído nominalmente a um feitiço externo se houver handler/tag/contrato que prove essa relação.

## D007 — Mods opcionais não viram dependência dura por acidente
Aceita. Adapters devem ser isolados e protegidos por detecção de mod/contratos apropriados.

## D008 — Create/AE2 não serão anunciados como runtime completo sem adapter comprovado
Aceita. Nomes como `technomancer/create_resonance` podem ter efeitos reais sobre atributos do RPG/Iron's sem, por si só, provar que eventos de máquinas Create são interceptados.

## D009 — IDs persistidos são API de save
Aceita. Renomear/remover IDs de skill, classe, mastery ou especialização exige compatibilidade ou migração explícita.

## D010 — Um evento de gameplay concede progressão uma vez
Aceita. Bridges de mods devem preferir confirmação semântica real do evento e idempotência, evitando XP/mastery duplicados por eventos auxiliares.
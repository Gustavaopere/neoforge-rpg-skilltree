# Contrato do catálogo I/P/S/T

## Paridade e ownership

11 árvores: MARTIAL, AGILITY, VITALITY, HEALING, ARCANE, ENGINEERING, MINING, SURVIVAL, SUMMONING, OCCULT, LOGISTICS.

Cada owner terá mesmo total de **P**, mesmo total de **S** e mesmo total de **vagas T**. Perfis de Abilities não contam como vagas, e seu total não precisa ser igual por domínio. O conjunto I é compartilhado e fixo em seis.

Cada perk P/S e vaga T possui um owner; uma especialização pode ser HYBRID com requisitos de outras árvores sem contar duas vezes. Sem filler.

## Responsabilidades

- **I**: identidade dos seis atributos e suas policies do Core; não cria barra de stats paralela.
- **P**: perks normais que alteram sistemas/ações/recursos de modo geral, sem criar uma árvore específica para um único spell.
- **S**: especializações com Mecânica Assinatura e perks generalistas por **categoria validada** de ação/Ability; não se limitar a uma única spell.
- **T**: vagas de progressão com escolha futura de Ability conhecida, e perfis **únicos para cada Ability elegível**.

## Escolha T

Perfis suportados têm LEFT (mínimo 2, máximo 9), RIGHT (mínimo 2, máximo 9), META (mínimo 3, máximo 9); **1 de cada grupo** ativo. Uma metamorfose pode converter afinidade/escola, alterando damage/scaling/status/tags/VFX quando tecnicamente possível. A lista de afinidades é provider-aware, não pré-aprovada.

Um slot T não concede a Ability; depende de conhecimento/desbloqueio segundo a fonte real. O mapeamento slot ↔ Ability e o tier/raridade/level do provider serão auditados depois.

## Evidência e custos

Perks P/S e profiles T citam nome, mod id, JAR, **versão real instalada** e funções/hook da release. As competências gerais devem usar tags normalizadas **somente a partir de facts verificadas**; não presumir que mods compartilham a mesma taxonomia.

Cada perk contém custos de pontos/ranks/tier e requisitos mínimos de atributos (não consumíveis), além de dependências, sinergias, providers, fallback e antiabuso. Recurso mana/stamina/cooldown respeita pipeline nativo e não pode ser debitado duas vezes.

Leia [`PROVIDER-EVIDENCE-AND-COSTS.md`](./PROVIDER-EVIDENCE-AND-COSTS.md), [`ABILITY-SLOTS-AND-TAGS.md`](./ABILITY-SLOTS-AND-TAGS.md) e [`CODEX-FOUR-STAGES.md`](./CODEX-FOUR-STAGES.md).

## Gates

Nenhum slot vazio publicado como perk funcional. Nenhuma P/S com hook não confirmado é tratada como TECH-AUDITED. Nenhuma Ability com tipo de interação/estado insuficiente recebe metamorfose falsa. Layout vem por último. Diablo IV é inspiração, não autoridade técnica.

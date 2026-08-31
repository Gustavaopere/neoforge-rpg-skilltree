# 08.27 — História Editorial Source

## Goal
Separar formalmente contratos de runtime do Stage 08 e conteúdo editorial da campanha.

## Source boundary

- `plans/08-quests-progression-hooks/` define **como** Narrative & Society Core funciona.
- `historia/` define **o que** existe, pode ser descoberto e pode acontecer na campanha.

Nenhum arquivo de `historia/` prova que uma API/provider está implementado. Nenhum plano técnico cria automaticamente um fato narrativo.

## Spoiler policy
A autoria pode ser realizada sem apresentar ao usuário detalhes ocultos. O contrato editorial vive em `historia/00-canone/politica-sem-spoilers.md`.

## AI-assisted authoring
Ferramentas externas e IA podem criar/revisar rascunhos, porém apenas conteúdo revisado e mergeado na fonte versionada é aceito como cânone editorial.

## Optional in-game AI
LLM/NPC agents podem futuramente propor diálogo, intenção e microconteúdo, mas são consumers/proposers. O Narrative Core permanece authority de fatos, knowledge, relações, leis, recompensas, morte/retorno e consequências.

## Fail-closed
Ausência de qualquer integração de IA não pode bloquear a campanha principal nem alterar fatos canônicos.
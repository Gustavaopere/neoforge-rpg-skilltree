# 11 — IA e Autoria

Esta seção mantém somente decisões específicas desta campanha sobre IA e ferramentas externas.

A capability reutilizável de autoria narrativa — skill, templates, validators, inventory e processo genérico de criação/revisão — pertence a `Gustavaopere/minecraft-mod-factory/narrative/` e deve ser consumida através de `historia/narrative-authoring-profile.json`.

Da mesma forma, direção de arte reutilizável, contratos genéricos de asset, validators, pipeline de Blockbench/modelagem e QA visual pertencem a `Gustavaopere/minecraft-mod-factory/art/`. O RPG mantém somente decisões visuais específicas da campanha, briefs por entidade, manifests/configuração consumidora e os assets realmente pertencentes a esta campanha.

Permanecem neste repositório:

1. **IA dentro do jogo para NPCs/diálogo/conteúdo emergente** — opcional e sempre subordinada ao Narrative Core;
2. **contratos de authority/ferramentas externas desta campanha** — incluindo limites de cânone, runtime e integração;
3. `11-contrato-geografia-compendio.md` — binding tardio e prova de worldgen/Compêndio;
4. `12-grimoire-github-sync.md` — reconciliação rastreável entre Campaign Bible e fonte editorial versionada;
5. `13-backlog-editorial-e-bloqueios.md` — fila fail-closed das lacunas reais da campanha, com authority exigida, trabalho permitido e proibições enquanto a fonte necessária não estiver disponível;
6. `14-auditoria-reconciliacao-grimoire-2026-09-15.md` — snapshot operacional da auditoria de pendências Grimoire↔GitHub que originou reconciliações e bloqueios posteriormente formalizados;
7. `15-migracao-npc-0001-severin-aren-2026-09-15.md` — decisão editorial que mantém `NPC-0001` como ID estável de Aren e aposenta Severin como entidade ativa, sem transferir automaticamente voz, aparência, relações ou cenas;
8. `16-factory-narrative-toolkit-integration.md` — contrato de consumo do Narrative Authoring Toolkit revision 1, distinção entre profile canônico e probe de compatibilidade e evidência da integração CI;
9. `legado-dialogos/` — material de diálogo aposentado preservado somente para provenance histórica. Esse diretório fica deliberadamente fora de `historia/12-dialogos`, o corpus ativo validado pelo consumer de diálogos.

Não adicionar skills, validators, templates ou pipelines genéricos a `historia/`; quando a necessidade for reutilizável, implementá-la no domínio correspondente da Minecraft Mod Factory e manter aqui apenas a configuração/uso específico desta campanha.

A campanha não deve depender de IA in-game para ser boa. Quests âncora, cânone, segredos estruturais, conflitos e regras sociais precisam funcionar sem LLM em runtime.

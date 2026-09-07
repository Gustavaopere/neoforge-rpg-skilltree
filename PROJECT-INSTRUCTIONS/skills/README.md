# Skills e instruções especializadas

Esta pasta é o ponto de entrada canônico para skills/instruções de Minecraft usadas pelos chats que trabalham neste repositório.

## Leia primeiro

1. `ROUTER.md` — escolhe a skill adequada.
2. `VERSION-AUTHORITY.md` — fixa a autoridade de Minecraft 1.21.1 / NeoForge 21.1.x / Java 21.
3. `USER-GUIDED-WORKFLOW.md` — obriga uma etapa manual verificável por vez quando o usuário precisa agir.
4. `SOURCE-AUDIT.md` — explica quais skills foram recebidas, quais são seguras, quais exigem overlay e quais ficam fora do escopo padrão.
5. `SOURCE-MANIFEST.md` — registra SHA-256 dos 20 ZIPs recebidos em 2026-09-07.

## Estrutura

- `library/<skill>/SKILL.md` contém a adaptação canônica e auditada do entrypoint de cada skill fornecida pelo usuário.
- skills específicas deste projeto, como modelagem, Blockbench/GeckoLib, VFX, spells, áudio e visual QA, vivem em diretórios irmãos de `library/`.

Os ZIPs originais foram auditados, mas seus exemplos multi-versão e arquivos auxiliares não são promovidos automaticamente a autoridade. Isso evita que exemplos de 1.21.4+, 1.21.8+, 1.21.11, Fabric, Forge legado, Paper ou multi-loader contaminem o runtime NeoForge 1.21.1.

Ter um `SKILL.md` neste repositório não o transforma em skill nativa do ChatGPT; ele é uma instrução versionada que um chat deve ler e seguir quando trabalhar neste repositório.

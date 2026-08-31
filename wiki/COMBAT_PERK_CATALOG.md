# Catálogo das perks de combate A0001–A0100

Este índice cobre a árvore semântica `rpgskilltree:runtime/combat_perks`. Ele permanece separado de `PERK_CATALOG.md`, que representa a malha histórica de 512 nós.

Nomes vêm do snapshot canônico versionado; requisitos, ranks e custos vêm do modelo de aquisição autoritativo. Descrições só aparecem quando existe texto player-facing auditado; ausências permanecem `—`. O gerador não converte policies Java em prosa nem inventa efeitos.

Catálogos gerados por lote:

- [A0001–A0010](combat-perks/A0001-A0010.md)
- [A0011–A0020](combat-perks/A0011-A0020.md)
- [A0021–A0030](combat-perks/A0021-A0030.md)
- [A0031–A0040](combat-perks/A0031-A0040.md)
- [A0041–A0050](combat-perks/A0041-A0050.md)
- [A0051–A0060](combat-perks/A0051-A0060.md)
- [A0061–A0070](combat-perks/A0061-A0070.md)
- [A0071–A0080](combat-perks/A0071-A0080.md)
- [A0081–A0090](combat-perks/A0081-A0090.md)
- [A0091–A0100](combat-perks/A0091-A0100.md)

## Regeneração

Execute `python3 scripts/generate-wiki-catalog.py`. O CI executa o mesmo pipeline em modo `--check` e falha se qualquer lote gerado estiver ausente ou desatualizado.

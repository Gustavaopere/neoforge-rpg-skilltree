# Orientações operacionais do projeto

Esta pasta é o ponto de entrada canônico para documentação que orienta o trabalho de perks e integrações.

## 1. Instruções para IA — `ia/`

Arquivos que devem ser **seguidos como procedimento operacional**, não apenas lidos como contexto:

- [Critérios obrigatórios para aprovação de perks](ia/CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md)
- [Chat 1 — auditoria, design e integração](ia/CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md)
- [Chat 2 — implementação](ia/CHAT-2-IMPLEMENTACAO-PERKS-ANEXOS-PROJETO.md)
- [Chat 3 — pendências, testes, validação e merge](ia/CHAT-3-PENDENCIAS-TESTES-VALIDACAO-MERGE-PERKS-ANEXOS-PROJETO.md)

## 2. Referências e explicações — `referencias/`

Material de consulta, consolidação e evidência. Esses arquivos informam o trabalho, mas não substituem as instruções de `ia/`:

- [Guia completo dos projetos próprios](referencias/GUIA-COMPLETO-PROJETOS-PROPRIOS.md)
- [Auditoria de cobertura dos antigos guides contra os dossiers individuais](referencias/GUIDE-TO-MOD-DOSSIER-COVERAGE-AUDIT-2026-09-29.md)

## 3. Projetos próprios — `projetos/`

Contratos transversais de RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana.

- [Índice operacional](projetos/INDEX.md)
- [Governança e estados](projetos/README.md)
- [Fontes de auditoria](projetos/README-SOURCES.md)
- `projetos/referencias/`: dossiês, matrizes, snapshots e capability deltas.
- `projetos/regras-ia/`: checklist, manutenção, política de fontes, authority e regra de não inferir hooks.

## Authority por tipo de informação

- **Provider externo / mod da modlist:** `../modlist/modlist.md` para presença, posição, JAR e versão; dossier individual em `../modlist/<categorias>/✅-*.md` para mecânicas, hooks, ownership/authority, lifecycle, multiplayer, persistência, riscos e limites.
- **Projeto próprio:** `projetos/` e o consolidado em `referencias/GUIA-COMPLETO-PROJETOS-PROPRIOS.md`.
- **Procedimento dos Chats 1/2/3:** `ia/`.

Os antigos guias agregados de Gameplay, Magia e Tecnologia foram retirados da árvore ativa em 01/10/2026 após reconciliação com os dossiers individuais. O histórico Git preserva esse material, mas ele não é authority operacional.

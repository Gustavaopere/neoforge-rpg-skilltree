# Guias e contratos transversais

Esta pasta mantém apenas documentação que **não pertence corretamente a um único mod externo** e a evidência histórica da auditoria que permitiu retirar os antigos guias temáticos.

## Autoridade para criação de perks

Para providers externos:

1. **Presença, posição, JAR e versão instalada:** `../modlist/modlist.md`.
2. **Mecânicas, ownership/authority, lifecycle, multiplayer, persistência, riscos, hooks e limites conhecidos:** dossier individual atual em `../modlist/<categorias>/✅-*.md`.
3. **RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana:** `projects/` e `../GUIA-COMPLETO-PROJETOS-PROPRIOS.md`.
4. **Evidência da reconciliação dos antigos guias temáticos:** `GUIDE-TO-MOD-DOSSIER-COVERAGE-AUDIT-2026-09-29.md`.

A auditoria de 29/09/2026 comparou os antigos guias de Gameplay, Magia e Tecnologia com os dossiers individuais para uso futuro em perks. As lacunas perk-relevantes confirmadas foram migradas para os dossiers correspondentes. Por isso, as árvores `gameplay/`, `magic/` e `technology/` e seus três consolidados foram retirados da documentação ativa.

O histórico Git continua preservando esses arquivos para provenance. Eles não devem ser recriados como segunda authority.

## Projetos próprios — leitura obrigatória quando aplicável

[Projetos Próprios do Modpack](projects/README.md)

A coleção `projects/` permanece porque documenta contratos transversais que não pertencem a um único dossier de mod externo:

- RPG Skill Tree;
- Volcanoes;
- Enshrouded;
- Black Arcana;
- matriz de integração cruzada;
- authority e fail-closed;
- capability delta provider → árvore;
- governança e regras de não inferência de hooks.

Para uma perk híbrida, ler o dossier individual do provider externo **e** o contrato do projeto próprio pertinente.

## Regra fail-closed

Se uma perk exigir uma mecânica, hook, API ou comportamento de provider externo que não esteja sustentado pelo dossier individual atual:

1. não recuperar silenciosamente a informação de um guia histórico;
2. não inferir pelo nome ou função aparente do mod;
3. reabrir a auditoria do provider e atualizar o dossier com evidência adequada;
4. manter a perk bloqueada até a reconciliação.

A posição física **#272 — Factory Construction Registry Probe 0.1.0** permanece o bloqueio estrutural conhecido sem dossier certificado, conforme o relatório de cobertura.

## Manutenção

- Provider externo: atualizar o dossier individual em `modlist/`.
- Presença/JAR/versão: atualizar `modlist/modlist.md` conforme o protocolo canônico.
- Projetos próprios/contratos transversais: atualizar `projects/` e reconciliar `../GUIA-COMPLETO-PROJETOS-PROPRIOS.md` quando necessário.
- Não recriar a antiga árvore `plans/03-skill-tree-perks/guides/`.

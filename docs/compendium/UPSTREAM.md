# Compêndio Natural — Upstreams congelados

Este manifesto registra **referências externas de comportamento/UX** usadas no Stage 10. Ele não concede permissão adicional e não transforma nenhum projeto listado em dependência do RPG Skill Tree.

Autoridade para cada registro: o repositório upstream congelado no SHA indicado e, quando aplicável, a página oficial do projeto. O snapshot abaixo foi auditado em **2026-08-28** para Minecraft **1.21.1**.

<!-- compendium-upstream:v1 -->
```json
[
  {
    "id": "biology_dictionary",
    "name": "Biology Dictionary",
    "project_url": "https://www.curseforge.com/minecraft/mc-mods/biology-dictionary",
    "source_repository": "https://github.com/xienaoban/minecraft-biology-dictionary",
    "source_ref": "main-architectury-1.21.1",
    "source_sha": "5b70858371960d95a4ffba1ef4c1320aa94452e8",
    "observed_version": "1.2.1",
    "minecraft_version": "1.21.1",
    "loader": "NeoForge (Architectury multi-loader source)",
    "code_license": "LGPL-3.0-or-later",
    "code_license_evidence": "https://github.com/xienaoban/minecraft-biology-dictionary/blob/5b70858371960d95a4ffba1ef4c1320aa94452e8/LICENSE",
    "asset_license": "NOT_SEPARATELY_STATED",
    "asset_license_evidence": "https://github.com/xienaoban/minecraft-biology-dictionary/blob/5b70858371960d95a4ffba1ef4c1320aa94452e8/README.md",
    "code_reuse_policy": "BEHAVIOR_REFERENCE",
    "asset_reuse_policy": "NO_REUSE",
    "observed_at": "2026-08-28",
    "notes": "O LICENSE cobre o programa sob LGPL-3.0-or-later. A auditoria não encontrou uma concessao separada e inequivoca para assets; portanto assets nao sao reutilizados."
  },
  {
    "id": "field_guide",
    "name": "Field Guide",
    "project_url": "https://www.curseforge.com/minecraft/mc-mods/field-guide",
    "source_repository": "https://github.com/evanbones/Field-Guide",
    "source_ref": "1.21.1",
    "source_sha": "a206cf81a4465e453b0663b0173066f30dcdc348",
    "observed_version": "1.15.2",
    "minecraft_version": "1.21.1",
    "loader": "NeoForge / Fabric / Forge multi-loader source",
    "code_license": "MIT",
    "code_license_evidence": "https://github.com/evanbones/Field-Guide/blob/a206cf81a4465e453b0663b0173066f30dcdc348/LICENSE",
    "asset_license": "ALL-RIGHTS-RESERVED-UNLESS-SPECIFIED",
    "asset_license_evidence": "https://github.com/evanbones/Field-Guide/blob/a206cf81a4465e453b0663b0173066f30dcdc348/README.md",
    "code_reuse_policy": "BEHAVIOR_REFERENCE",
    "asset_reuse_policy": "NO_REUSE",
    "observed_at": "2026-08-28",
    "notes": "O codigo e MIT. O README declara explicitamente os assets como All Rights Reserved pelos respectivos criadores, salvo indicacao em contrario; nenhum asset e reutilizado."
  },
  {
    "id": "wildex",
    "name": "Wildex Bestiary",
    "project_url": "https://www.curseforge.com/minecraft/mc-mods/wildex-bestiary",
    "source_repository": "https://github.com/ColdFang/wildex",
    "source_ref": "3.0.0 source snapshot for 1.21.1",
    "source_sha": "b67267f6e664af58fe4ff430ba83c78a379029a5",
    "observed_version": "3.0.0",
    "minecraft_version": "1.21.1",
    "loader": "NeoForge 21.1.219+",
    "code_license": "CC-BY-NC-4.0",
    "code_license_evidence": "https://github.com/ColdFang/wildex/blob/b67267f6e664af58fe4ff430ba83c78a379029a5/LICENSE.txt",
    "asset_license": "CC-BY-NC-4.0-WORK-WIDE",
    "asset_license_evidence": "https://github.com/ColdFang/wildex/blob/b67267f6e664af58fe4ff430ba83c78a379029a5/LICENSE.txt",
    "code_reuse_policy": "BEHAVIOR_REFERENCE",
    "asset_reuse_policy": "NO_REUSE",
    "observed_at": "2026-08-28",
    "notes": "O gradle.properties congelado identifica Minecraft 1.21.1, NeoForge 21.1.219 e mod_version 3.0.0. O LICENSE.txt aplica CC BY-NC 4.0 ao trabalho; por prudencia e para evitar restricao NonCommercial no projeto, nao reutilizamos codigo nem assets."
  }
]
```

## Resumo jurídico-operacional

| Upstream | Código | Assets | Política do Compêndio |
| --- | --- | --- | --- |
| Biology Dictionary | LGPL-3.0-or-later | licença separada não confirmada | referência comportamental; sem cópia |
| Field Guide | MIT | All Rights Reserved salvo exceção explícita | referência comportamental; sem cópia |
| Wildex Bestiary | CC BY-NC 4.0 | tratado como parte do trabalho CC BY-NC 4.0 | referência comportamental; sem cópia |

## Regra de atualização

Uma referência só pode ser atualizada substituindo **SHA + versão + evidência de licença** no mesmo commit. Links de branch sem SHA não constituem evidência congelada.

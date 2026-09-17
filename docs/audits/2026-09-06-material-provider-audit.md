# Material Provider Audit — Canonical Baseline 2026-09-06

## Scope

This file freezes the exact top-level JAR baseline that the Universal Material Chemistry & Visualization work must audit. It is not a claim that all providers are already resolved. Unresolved rows deliberately use `BLOCKED_AUDIT` so later implementation cannot silently treat them as non-material mods.

- Canonical source: `modlist.txt`
- SHA-256: `5803863cc589ab0bfa2d83888eb538410bd807e00c70d5a71d54a4881465b7da`
- Top-level JAR rows: **607**
- Notion master database: `https://app.notion.com/p/34e80068b1ae402d8e32db7456044bab`
- Minecraft: **1.21.1**
- Loader: **NeoForge**
- Java: **21**

## Baseline dispositions

- `MATERIAL_PROVIDER`: 1
- `MATERIAL_CONSUMER_ONLY`: 0
- `NO_ELIGIBLE_MATERIALS`: 0
- `BLOCKED_AUDIT`: 606

`BLOCKED_AUDIT` means **not yet provider-audited**. It is fail-closed, not a negative classification. No row may move out of it without evidence from the installed version, its verified Notion record, provider source/data, or another approved primary source.

## Runtime-metadata gaps preserved

The canonical modlist contains eight top-level rows with at least one empty runtime metadata field. They are not normalized by guesswork. Where Notion has verified an exact value, that value is recorded; where it explicitly confirms the field is not declared, the manifest uses `__not_declared__`. Publication/filename versions remain distinct from runtime versions.

| JAR | Resolution | Notion evidence |
| --- | --- | --- |
| `connector-2.0.0-beta.17+1.21.1-full.jar` | modId=`connector`, version=`__not_declared__` | https://app.notion.com/p/3c369db9f0db8103a9acee25c6616051 |
| `create_enchantment_industry_plus-1.1.1-1.21.1.jar` | modId=`create_enchantment_industry_plus`, version=`1.1.1` | https://app.notion.com/p/3c369db9f0db8150bf7ae0b9d912c41a |
| `create_sophback_compat-1.0.jar` | modId=`create_sophback_compat`, version=`__not_declared__` | https://app.notion.com/p/3c369db9f0db8112ab47f26caaa78805 |
| `createmechanicalcompanion-1.9-neoforge-1.21.1.jar` | modId=`createmechanicalcompanion`, version=`__not_declared__` | https://app.notion.com/p/3c369db9f0db81d8bc42fa26362db2f1 |
| `dtquark-2.6.1.jar` | modId=`dtquark`, version=`__not_declared__` | https://app.notion.com/p/3c369db9f0db812c9334cfde63093afa |
| `easy_model_entities-neoforge-1.21.1-2.3.0.jar` | modId=`easy_model_entities`, version=`2.3.0` | https://app.notion.com/p/3d369db9f0db814d915ef3b0d105e25e |
| `easy_npc-neoforge-1.21.1-7.11.0.jar` | modId=`easy_npc`, version=`7.11.0` | https://app.notion.com/p/3d369db9f0db812f8576f5016343b18c |
| `kotlinforforge-5.12.0-all.jar` | modId=`__not_declared__`, version=`__not_declared__` | https://app.notion.com/p/3c369db9f0db8116bbbcf716e1f870e4 |

## Authority lock

`Destroy 0.4.1` is the only row promoted in this baseline to `MATERIAL_PROVIDER` because its chemistry/material-provider role and authority are already verified by the canonical Notion record and the approved design. This does **not** imply that other material providers are absent; they remain blocked until their individual audit is completed.

## Next audit pass

Provider resolution proceeds by evidence-bearing cohorts: chemistry/petrochemistry, metallurgy/mining/geology, technology/process materials, magic/fictional materials, food/agriculture consumable substances, then the residual modlist. Each promotion must update both this ledger and `modlist-audit.json` in the same commit.

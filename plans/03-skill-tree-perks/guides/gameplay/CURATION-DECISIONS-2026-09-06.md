# Decisões de curadoria da modlist — 2026-09-06

> **Escopo:** fechamento da auditoria da `modlist.txt` atual para NeoForge 1.21.1. Este arquivo registra **decisão de curadoria** (`Manter`, `Tirar`, `Opcional`) e riscos conhecidos. Ele não substitui [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md) como authority de presença/JAR/versão física.
>
> Um mod marcado `Tirar` ou `Opcional` pode continuar fisicamente instalado até que uma nova modlist prove a alteração. Não marcar `Removido` no Notion antes dessa evidência.

## Bloqueios e remoções decididas

| Mod | JAR atual | Decisão | Motivo |
|---|---|---|---|
| Alex's Caves 2.0.2 | `alexscaves-2.0.2.jar` | **Tirar** | Conflita por `modid alexscaves` com a implementação canônica escolhida. |
| Alex's Caves Continued 1.0.9 | `alexscaves-1.0.9-neoforge+1.21.1.jar` | **Manter** | Implementação canônica escolhida para `alexscaves`. |
| Alex's Mobs 1.22.9 | `alexsmobs-1.22.9.jar` | **Tirar** | Conflita por `modid alexsmobs` com a implementação canônica escolhida. |
| Alex's Mobs Continued 2.1.9 | `alexsmobs-2.1.9-neoforge+1.21.1.jar` | **Manter** | Implementação canônica escolhida para `alexsmobs`; a linha 2.1.9 inclui correção de coexistência com Citadel real. |
| Create: Bits 'n' Bobs 2.3.0 | `bits_n_bobs-2.3.0.jar` | **Tirar** | Create: Sulfuric Resonance 0.4.1 documenta conflito de assets dos Thermochemical Cogwheels e recomenda desabilitar Bits 'n' Bobs. |

## Opcionais / validação A-B antes de remover

| Mod | JAR atual | Decisão | Regra de uso |
|---|---|---|---|
| Dynamic Trees: Universal Compat | `NeoForge-dynamictreescompat-2.3.jar` | **Opcional** | Há risco de sobreposição com treepacks dedicados já instalados. Validar worldgen em mundo descartável e precedência/skip logic antes de torná-lo canônico. |
| Ice And Fire: Dread Land 0.1.2 | `iceandfire_dreadland-0.1.2.jar` | **Opcional** | Upstream classifica o projeto como Early Alpha/WIP. Dimensão, bosses e progressão ainda possuem conteúdo incompleto; usar preferencialmente em ambiente de teste. |
| Sinytra Connector beta.17 | `connector-2.0.0-beta.17+1.21.1-full.jar` | **Opcional** | Artefato oficial e válido, mas a auditoria não identificou consumidor Fabric-only inequívoco. Antes de retirar, fazer boot A/B e conferir logs/cache de mods transformados. |
| Connector Extras 1.12.1 | `ConnectorExtras-1.12.1+1.21.1.jar` | **Opcional** | Só agrega bridges quando Sinytra Connector possui consumidor real. Se Connector for dispensável no teste A/B, retirar Extras junto. |

**Importante:** Forgified Fabric API não acompanha automaticamente a remoção de Connector/Extras. Há consumidores NeoForge nativos que podem exigir FFAPI; portanto sua necessidade deve ser avaliada separadamente.

## Builds prerelease mantidas conscientemente

Os seguintes registros foram encerrados como **Manter / Verificado** mesmo usando Beta/Alpha/Pre-Release, porque a identidade e o risco estão conhecidos e existe razão técnica para preservar a linha instalada ou testar antes de downgrade:

- `cataclysm_spellbooks-1.1.13-1.21.jar` — Cataclysm: Spellbooks 1.1.13 Beta; 1.1.11 permanece fallback Release.
- `createaddition-1.7.0.jar` — Create Crafts & Additions 1.7.0 Beta; possui mudanças relevantes ao stack Sable/Connector.
- `tracks_plus-1.0.6b6.jar` — Create: Tracks+ 1.0.6beta6; 1.0.5 é fallback Release.
- `create_fantasizing-1.21.1-1.2.0-b3.jar` — beta3 contém correções posteriores de duplicação de fluido/NPE.
- `railways-0.3.0-beta.2+neoforge-mc1.21.1.jar` — Steam 'n' Rails beta2; linha contém fixes relevantes ao Create moderno.
- `create_wizardry-1.21.1-0.5.1-pre1.jar` — Create: Wizardry pre-release; 0.5.0 é fallback conservador.
- `reliquified_ars_nouveau-1.21.1-0.8.1.jar` — Beta com correção de compatibilidade com Reliquified Artifacts.
- `reliquified_artifacts-1.21.1-1.0.8.jar` — Beta oficial da linha 1.21.1.
- `sablebeyond-neoforge-1.21.1-v0.5.0.jar` — Alpha com funcionalidades específicas de sublevels/fluidos/Sable.
- `sable-dynamic-lights-1.21.1-2.0.1.jar` — Beta voltada à iluminação entre contraptions/sublevels.
- `true_impact-0.5.7-delta.jar` — experimental de **alto risco**, mantido conscientemente; não tratar como estabilidade comprovada.
- `sky_aesthetics-neoforge-2.0.13-beta.jar` — Beta com fixes posteriores; 1.7.1 permanece fallback Release.
- `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar` — Alpha oficial da linha 1.21.1; mantido com validação JEI/worldgen.

## Procedência fechada nesta auditoria

### Simply More — Alpha 5

O JAR físico permanece `simplymore-forge-1.3.0_alpha.jar`, runtime `1.3.0_alpha`, mas o SHA-1 local `51636477cd5c378f42d9700e1fe35cd952c8f4f1` identifica o **CurseForge File ID 8736778 — Simply More 1.3.0 ALPHA 5**. A Alpha 5 inclui correções para crash ao entrar em dedicated server, Soulfracture e Blade of the Grotesque.

Decisão: **Manter / Verificado**, preservando fail-closed para perks que dependam de efeitos de Uniques ainda não provados na linha Alpha.

### Immersive Portal - Iron's Spells 'n Spellbooks Addon — 1.0.1

A release pública exata foi confirmada: `immersive_portal_irons_spells_n_spellbooks_addon-1.0.1.jar`, **CurseForge File ID 8770439**, Release NeoForge 1.21.1 publicada em 30/08/2026.

Decisão: **Manter / Verificado**. O projeto é recente; validar Portal Spell, tamanho configurável, travessia/renderização e dedicated server.

## Bridges MineColonies

As versões declaradas nos arquivos de build são **baselines mínimas**, não pins exatos:

- Compatibility addon 3.56 exige MineColonies `>= 1.1.1368` e Tweaks `>= 3.33`;
- Tweaks 3.33 exige MineColonies `>= 1.1.1368`;
- Let's Do addon 2.1 exige MineColonies `>= 1.1.1120`;
- Epic Colonies 21.0.8 declara Epic Fight e MineColonies com `versionRange = [0,)` no metadata NeoForge.

O MineColonies `1.1.1376` instalado satisfaz os mínimos declarados. Isso remove o bloqueio de loader por versão, mas não substitui smoke/integration tests de comportamento.

Decisão para os quatro: **Manter / Verificado**.

## Atualizações recomendadas sem remoção automática

- **Photon:** manter o provider, mas atualizar/testar a linha estável publicada após a Beta 2.2.5 instalada (`2.2.6.a` Release) antes do fechamento físico do pack.
- **JEI 19.53.0.425:** manter; é Beta recente e central para muitos plugins. `19.51.0.418` permanece fallback Release se aparecer regressão.
- **Werewolves 2.0.3.3:** manter. Epic Fight possui módulo específico de compatibilidade, mas o upstream de Werewolves ainda documenta risco visual; validar forma transformada/animações em battle mode.

## Estado da auditoria

Os **27 registros que estavam enumerados como `Estado da pesquisa = Rever`** foram tratados individualmente e receberam decisão final no Notion. A persistência dos seis últimos casos foi confirmada por re-leitura direta das páginas.

A contagem agregada final do banco não é usada como prova neste arquivo; a evidência operacional é a atualização individual dos registros. Se uma futura varredura encontrar novo `Rever`, ele deve ser tratado como novo delta e não como reabertura silenciosa deste lote.

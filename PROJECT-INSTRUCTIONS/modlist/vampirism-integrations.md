# Vampirism Integrations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db811dbec2e5f7472b65ab
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `vampirism_integrations-1.21.1-1.10.2.jar`, mod id `vampirism_integrations`, runtime `1.10.2`; Cold Sweat 2.4.2, Jade 15.10.6 e MineColonies 1.1.1381 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Vampirism Integrations 1.10.2 e os targets citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Vampirism Integrations
- **Arquivo JAR:** `vampirism_integrations-1.21.1-1.10.2.jar`
- **Versão 1.21.1:** 1.10.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, Compat
- **Função:** Camada oficial de compatibilidade cross-mod do Vampirism. Move integrações opcionais para fora do core e adapta mecânicas do Vampirism a outros mods quando eles estão presentes. Entre os projetos declarados pelo addon estão Cold Sweat, MineColonies, Tough As Nails, Jade, Immersive Engineering, CraftTweaker, Guard Villagers, MCA Reborn, Biomes O' Plenty e outros; as integrações só têm efeito quando o respectivo mod-alvo está instalado.
- **Dependências:** Vampirism 1.10.13. No snapshot físico atual de 595 top-levels: Cold Sweat 2.4.2 = target/version eligible e default-enabled em source, runtime activation não confirmada; Jade 15.10.6 = target presente/plugin discoverable, runtime registration não confirmada; MineColonies 1.1.1381 presente, mas sem bridge Java/loader provada no source pin.
- **Sobreposição:** Bridge de compatibilidade. Não cria nova facção, recurso, temperatura, sangue ou progressão: Cold Sweat/Vampirism/Jade permanecem autoridades de seus próprios domínios.
- **Compatibilidade/Riscos:** Source pin bff02b9686408691aea2c0c910ccb712edb18bd5. `mod instalado` != `compat ativa`: validar `/vampirism-integrations loaded`, config real e setup sem exceção. Cold Sweat/Jade permanecem runtime-unconfirmed; MineColonies fail-closed. Targets ausentes permanecem inactive.
- **Observações:** Catálogo source-level preservado. Cold Sweat 2.4.2 e Jade 15.10.6 continuam presentes; BOP/WAILA/EvilCraft/CraftTweaker/TAN/MCA/CTOV/Guard Villagers e data maps condicionais permanecem inativos quando seus targets não estão presentes. MineColonies aparece no pack mas runtime path segue não provado.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source pin `TeamLapen/VampirismIntegrations@bff02b9686408691aea2c0c910ccb712edb18bd5` + CurseForge oficial 1.10.2. Cold Sweat 2.4.2, Jade 15.10.6 e MineColonies 1.1.1381 continuam fisicamente presentes; activation/setup real das bridges não foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vampirism-integrations
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Vampirism Integrations 1.10.2 permanece exatamente instalado; catálogo source-pinned e distinção `target presente` ≠ `bridge ativa` preservados. Runtime activation QA continua pendente.
- **Histórico da decisão:** 07/09/2026: auditoria source-level 1.10.2 reconciliada após review da PR #75. Correção: Cold Sweat não pode ser chamado ACTIVE sem config/setup runtime; estado canônico = eligible/default-enabled. Jade = target-present/plugin-discoverable, runtime unconfirmed. Provenance exata LGPLv3 registrada; MineColonies bridge continua não encontrada; runtime QA pendente.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `vampirism_integrations-1.21.1-1.10.2.jar`, mod id `vampirism_integrations`, versão `1.10.2`. A auditoria abaixo é **source-pinned** (`TeamLapen/VampirismIntegrations@bff02b9686408691aea2c0c910ccb712edb18bd5`) e deve ser preservada. No snapshot físico atual de **595 top-levels**, Cold Sweat 2.4.2 e Jade 15.10.6 continuam presentes; MineColonies 1.1.1381 também está presente, mas o source pin não demonstrou runtime bridge para ele. Runtime activation continua pendente.

## Auditoria source-level — 1.10.2
Source exato: `TeamLapen/VampirismIntegrations@bff02b9686408691aea2c0c910ccb712edb18bd5`.

### Resultado no pack atual
A build 1.10.2 é uma camada de compatibilidade e não um provider autônomo de magia/progressão. No snapshot físico atual de 595 top-levels, as integrações com alvo presente e caminho source-level elegível/discoverable são:
- **Cold Sweat 2.4.2 — ELIGIBLE / DEFAULT-ENABLED / RUNTIME UNCONFIRMED.** O target está presente, satisfaz o range `[2.2.3,)` e a config gerada é habilitada por padrão. Se o compat for realmente preparado, Vampires recebem modifiers transitórios nos atributos `cold_sweat:freezing_point` e `cold_sweat:burning_point`. Defaults source-level: `enableTemperatureVampires=true`, resistência fria configurada em 30 °C antes da conversão de unidades do provider e fator de burning point 0.7. O modifier usa id `vampirism:vampire_modifier` e o caminho source-level o reconcilia em login, respawn e mudança de faction level. A config real do pack e setup sem exceção ainda precisam de QA.
- **Jade 15.10.6 — TARGET PRESENT / PLUGIN DISCOVERABLE / RUNTIME UNCONFIRMED.** A integração não depende do central `ModCompatLoader`: `JadePlugin` usa `@WailaPlugin`, portanto possui caminho de discovery pelo Jade quando o target está presente. O source declara providers para entity blood, player/entity faction, Garlic Diffuser, Totem, Pedestal, Alchemy/Potion/Weapon/Research Tables, Altar of Inspiration/Infusion e Altar Pillar. Registro/rendering efetivos no pack continuam runtime QA.

### Compats registradas, mas inativas no snapshot atual
O loader 1.10.2 registra BOP, WAILA, EvilCraft, CraftTweaker, Tough As Nails, MCA, CTOV e Guard Villagers, além do dummy compat do próprio Vampirism. Como os respectivos targets não estão presentes no snapshot atual, esses módulos não devem ser promovidos a gameplay atual.

O loader também valida version range/config e descarrega uma compat se ela lançar exceção durante lifecycle. `mod instalado` não equivale automaticamente a `bridge executando com sucesso`.

### Data maps condicionais
O JAR estende data maps do próprio Vampirism quando providers externos existem:
- MCA villagers: blood 15 + converter `vampirism_integrations:mca`;
- capybara externa: blood 10 + converter/overlay;
- `biomesoplenty:blood`: conversion rate 0.4;
- `evilcraft:blood`: 0.8;
- `bloodmagic:life_essence_fluid`: 0.8;
- `tconstruct:blood`: 0.8;
- `evilcraft:blood_orb_filled`: blood value 800.

Essas entradas são condition-gated e os targets não estão ativos na modlist atual, portanto permanecem **supported-but-inactive**.

### Boundary MineColonies
MineColonies está instalado no pack e aparece em Gradle/deploy metadata do projeto, mas no source exato 1.10.2 não foi localizada classe Java de compat MineColonies nem registro correspondente no `ModCompatLoader`. Logo, a existência do mod e da dependência de build **não prova** biting/conversion/faction/blood integration em runtime. Manter `FAIL-CLOSED / RUNTIME PATH UNPROVEN`.

### Contrato de integração
- Cold Sweat continua autoridade para temperatura corporal, thresholds e consequências; não duplicar os modifiers vampíricos em Black Arcana. Até `/vampirism-integrations loaded` provar preparação no pack exato, consumers que dependam especificamente dessa bridge devem falhar fechado.
- Jade é UI/query; renderizar um componente não é evento causal de gameplay.
- Conversões condicionais permanecem sob os data maps do Vampirism; não criar rates concorrentes.
- Ausência de crash não comprova sucesso do bridge Cold Sweat porque o handler captura falhas e reduz logging após o primeiro erro.
- Mudanças futuras da modlist podem ativar compats/data maps sem alterar o JAR; cada target novo exige reauditoria.

### Runtime QA pendente
1. `/vampirism-integrations loaded` deve confirmar o conjunto preparado pelo central loader.
2. Login/respawn/faction transition deve aplicar exatamente um modifier Cold Sweat por atributo.
3. Curar/deixar Vampire deve remover os modifiers.
4. Medir thresholds térmicos finais no Cold Sweat 2.4.2.
5. Validar todos os providers Jade contra Vampirism 1.10.13.
6. Verificar que data maps condicionais ausentes não geram erros de data loading.
7. Se gameplay depender de MineColonies, exigir evidência de JAR/runtime antes de promover qualquer capacidade.

**Estado:** `SOURCE-PINNED / CURRENT-PACK ELIGIBLE+DISCOVERABLE BRIDGES CATALOGED / RUNTIME ACTIVATION QA PENDING`.

## Revalidação física — 11/09/2026
O runtime físico continua exatamente `vampirism_integrations-1.21.1-1.10.2.jar`, mod id `vampirism_integrations`, versão `1.10.2`; a file list oficial mantém esta como a release 1.21.1 pertinente.

Cold Sweat `2.4.2`, Jade `15.10.6` e MineColonies `1.1.1381` continuam presentes na modlist. A distinção do source pin foi preservada: target presente/eligible/discoverable **não equivale a bridge comprovadamente ativa**. Nenhum `/vampirism-integrations loaded`, setup Cold Sweat, provider Jade ou caminho MineColonies foi validado em runtime nesta recatalogação.

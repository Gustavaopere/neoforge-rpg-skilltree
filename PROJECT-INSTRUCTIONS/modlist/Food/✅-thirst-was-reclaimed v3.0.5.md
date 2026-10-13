# Thirst Was Reclaimed

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#543**: JAR `ThirstWasReclaimed-1.21.1-3.0.5.jar`, mod id `thirst`, runtime `1.21.1-3.0.5`, SHA-1 `53c51af93d5c6bffc1bea867cdcf7e831d019508`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Thirst Was Reclaimed
- **Arquivo JAR:** `ThirstWasReclaimed-1.21.1-3.0.5.jar`
- **Versão 1.21.1:** 1.21.1-3.0.5
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** 
- **Categoria:** Comida, RPG
- **Função:** Provider principal de sede/hidratação do pack: mantém thirst do jogador, purity da água/containers, item settings e sincronização server→client; 3.0.4 expõe thirst via persistent data e refaz Jade.
- **Dependências:** NeoForge 1.21.1. Thirst Was Fixed 2.1.6 e Sophisticated Thirst Upgrade 0.1.8 permanecem extensões separadas. Cold Sweat 2.4.3.1 e Create 6.0.10 são integration/regression surfaces quando aplicáveis.
- **Sobreposição:** Não é um segundo sistema de fome ou nutrição; adiciona a necessidade separada de hidratação.
- **Compatibilidade/Riscos:** Purity migration/propagation, config hash/sync stale, addon double ownership e HUD overlap. Linha 3.0.x trata purity ausente como max, permite disable global e sincroniza settings; testar transfers Bottle/Bucket/Pipes/Create e addons.
- **Observações:** Runtime físico `ThirstWasReclaimed-1.21.1-3.0.5.jar`, mod id `thirst`, metadata `1.21.1-3.0.5`; versão semântica 3.0.5. A 3.0.5 corrige incompatibilidade com CreateCyberGoggle, adiciona cooldown para beber água diretamente, corrige perda de purity em container do Supplementaries e erro de parsing de loot table.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Thirst Was Reclaimed 3.0.5 (file ID 8852248, 10/09/2026) + dossiê anterior 3.0.4 preservado. Nenhum teste runtime foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed/files/8391910 ; https://github.com/mlus-asuka/Thirst-Was-Reclaimed
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece ThirstWasReclaimed-1.21.1-3.0.5.jar / metadata 1.21.1-3.0.5. CurseForge atualmente lista 3.0.6 para NeoForge 1.21.1; bucket stacking, default purity e dripstone mudaram nessa release e ainda não estão ativos no pack.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ThirstWasReclaimed-1.21.1-3.0.5.jar`, mod id `thirst`, versão `1.21.1-3.0.5`. Thirst Was Reclaimed é o **provider principal de sede/hidratação** do pack: mantém thirst do jogador, regras de consumo e sistema de purity da água.
## 1. Identidade, versão e papel
- **Mod:** Thirst Was Reclaimed.
- **JAR:** `ThirstWasReclaimed-1.21.1-3.0.5.jar`.
- **Mod id:** `thirst`.
- **Versão:** `3.0.5` para MC 1.21.1.
- **Loader:** NeoForge.
- **Ambiente:** Client & Server.
- **Papel:** necessidade separada de hidratação, projetada para compatibilidade com modpacks.
## 2. Authority e ownership
Thirst Was Reclaimed é authority de:
- valor de thirst do jogador;
- efeitos/regras base de hidratação;
- purity de água/containers quando habilitada;
- item settings e regras sincronizadas do sistema.
Thirst Was Fixed e Sophisticated Thirst Upgrade são extensões; não devem manter uma segunda barra/state concorrente.
## 3. Thirst state do jogador
O mod adiciona um recurso separado da fome. O servidor deve ser a fonte de verdade do nível de thirst; HUD e integrations apenas representam/consomem esse state.
A 3.0.4 passou a expor o thirst level também via `player.getPersistentData()`, criando uma superfície oficial para addons/scripts lerem o recurso sem duplicá-lo.
## 4. Purity — semântica da linha 3.0.x
O refactor 3.0.0 alterou profundamente purity:
- removeu default purity configurável anterior;
- items/fluids sem tag de purity passam a ser tratados como **max purity** por padrão;
- valores inválidos são sanitizados;
- `enablePurity` permite desativar completamente o sistema;
- com purity desativada, tags/effects de purity não devem ser aplicados.
Addons como Thirst Was Fixed precisam obedecer esta semântica.
## 5. Propagação de purity em containers e fluids
A linha 3.0.0 corrigiu propagation para:
- bottles;
- buckets;
- pipes;
- campfire recipes;
- Create integration;
- Tough As Nails compatibility;
- mountain/running water cases.
Isso demonstra que purity é data que acompanha água através de múltiplos caminhos de transferência, não apenas atributo visual do item.
## 6. Item settings e keyword rules
O provider possui server item settings e keyword rules para definir quanto itens/fluidos hidratam e como são reconhecidos.
A linha 3.0.x sincroniza essas regras com clients. A configuração física do pack não foi lida; não registrar valores de hydration específicos sem o arquivo runtime.
## 7. Config synchronization
3.0.0 introduziu/expandiu sync de:
- server item settings;
- keyword rules;
- purity enablement;
- custom purity containers.
O protocolo usa hash SHA-256 para que cliente só solicite config completa quando a versão do servidor mudou.
Isso exige limpeza/reload correto ao trocar de servidor para evitar regras stale.
## 8. HUD e AppleSkin
A linha 3.0.0 inclui correções de coordenadas para thirst HUD/tooltip em integração AppleSkin, evitando overlap visual.
O pack possui múltiplos HUD/QoL mods; qualquer ausência de sede visual deve ser separada entre state do servidor e renderer do cliente.
## 9. Jade — release 3.0.4
A mudança principal 3.0.4 refatora integração Jade para exibir:
- fluid storage;
- purity.
O pack usa Jade no ecossistema; tanks/containers suportados devem apresentar valor coerente com o state real, sem converter tooltip em authority.
## 10. Create e fluid ecosystem
O source 1.21.1 inclui Create como integration/runtime de desenvolvimento e a linha 3.0.0 menciona fix explícito de purity propagation com Create.
No pack com Create 6.0.10 e muitos fluid addons, validar bucket/bottle/pipe/tank transitions e evitar perda de purity durante transferências.
## 11. Cold Sweat e outros survival mods
O source 1.21.1 inclui Cold Sweat entre integrações de desenvolvimento. O pack físico atual possui Cold Sweat 2.4.3.1.
Isto não significa que Thirst e temperature sejam o mesmo recurso: cada sistema mantém authority própria. Testar apenas a integração publicada/runtime, sem fundir balanceamento de thirst e body temperature na documentação.
## 12. Addons concretos no pack
- **Thirst Was Fixed 2.1.6:** corrige cauldrons e integra Ars/Ultimine/ParCool/Amendments.
- **Sophisticated Thirst Upgrade 0.1.8:** extensão para storage/backpacks sofisticados, catalogada separadamente.
- **Cold Sweat 2.4.3.1:** sistema térmico separado.
- **Create 6.0.10:** fluid transfer integration relevante.
- **Jade:** inspection de storage/purity na 3.0.4.
## 13. Client / server e multiplayer
- Servidor: thirst, purity e regras configuradas.
- Cliente: HUD/tooltips e caches sincronizados.
- Cliente não pode escolher hydration/purity local para superar regra do servidor.
- Troca de servidor deve invalidar hash/config anterior quando necessário.
## 14. Lifecycle
Validar:
- criação/login do player;
- beber item/fluido e morte/respawn;
- relog/server restart;
- bucket↔bottle↔tank↔pipe;
- Create transfer;
- purity ON/OFF em cópia de teste;
- troca entre servidores com configs distintas;
- Jade inspection após mudança de purity;
- addons lendo persistent thirst data.
## 15. Riscos técnicos
1. **Purity migration:** worlds/items antigos podem ter tags da semântica anterior.
2. **Transfer propagation:** qualquer ponte de fluid pode perder/sanitizar purity incorretamente.
3. **Config desync:** hydration/purity rules stale no cliente.
4. **Addon double ownership:** extensions não podem criar state paralelo.
5. **HUD overlap:** AppleSkin/outros HUDs podem esconder ou deslocar indicador.
6. **PersistentData consumers:** addon pode interpretar escala/ausência de dado incorretamente; validar contrato antes de scripting.
7. **Purity disabled:** addons precisam respeitar modo global sem reintroduzir tags/effects.
## 16. Matriz de testes
- [ ] Dedicated server boot com TWR 3.0.5.
- [ ] Thirst reduz/aumenta e persiste conforme gameplay esperado.
- [ ] Morte/respawn/relog não duplica ou zera state indevidamente.
- [ ] Purity ausente é interpretada segundo semântica 3.0.x.
- [ ] Purity inválida é sanitizada sem crash.
- [ ] `enablePurity` OFF remove efeitos/tags esperados em ambiente de teste.
- [ ] Bottle/bucket/pipe/Create transfer preserva purity.
- [ ] Cliente recebe item settings/keyword/purity config do servidor.
- [ ] Troca de servidor não mantém config/hash stale.
- [ ] Jade mostra fluid storage/purity coerentes.
- [ ] Thirst Was Fixed e Sophisticated Thirst Upgrade usam o mesmo state base.
- [ ] Persistent player data expõe thirst para integração sem criar segunda authority.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 17. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e addons/integrations presentes.
- CurseForge oficial 3.0.4: build NeoForge 1.21.1, Jade refactor e persistent thirst data.
- Changelog oficial 3.0.0: purity refactor, transfer fixes, config sync/hash e AppleSkin HUD fixes.
- Repositório oficial branch 1.21.1: modpack-oriented thirst provider e integration surface; source usado para arquitetura, não para inventar valores de config locais.
## 18. Revalidação física — 11/09/2026
Àquela data, o runtime físico confirmado era `ThirstWasReclaimed-1.21.1-3.0.4.jar`, mod id `thirst`, versão `3.0.4`. Esse registro permanece como histórico da auditoria anterior.
Thirst Was Fixed `2.1.6` e Sophisticated Thirst Upgrade `0.1.8` permaneciam extensões separadas. Nenhum teste de purity migration, Bottle/Bucket/Pipe/Create transfer, Jade, config hash/sync ou persistent-data consumer foi executado naquela recatalogação.
## 19. Revalidação física e upstream — 13/09/2026
Naquela revalidação, o runtime físico ainda era `ThirstWasReclaimed-1.21.1-3.0.4.jar`, versão `3.0.4`. O mod permanecia authority única de thirst/purity, com Thirst Was Fixed `2.1.6` e Sophisticated Thirst Upgrade `0.1.8` como extensões. Nenhum teste de purity migration, Bottle/Bucket/Pipe/Create transfer, Jade, config hash/sync ou persistent-data consumer foi executado naquela revalidação.
## 20. Atualização instalada — 3.0.5
A release oficial `1.21.1-3.0.5`, file ID 8852248, publicada em 10/09/2026 para NeoForge 1.21.1, registra quatro mudanças concretas:
1. correção de incompatibilidade com **CreateCyberGoggle**;
2. cooldown para beber água diretamente com a mão;
3. correção de perda de **purity** em container vindo de **Supplementaries**;
4. correção de erro de parsing ao carregar loot table.
Esses quatro pontos passam a ser regression gates da build física atual. O histórico da 3.0.4 permanece preservado porque a integração Jade e a exposição via `player.getPersistentData()` pertencem ao contexto documentado daquela linha.
Nenhum teste de drinking, purity, loot, Supplementaries, CreateCyberGoggle ou Cold Sweat foi executado nesta atualização documental.
### Regression gates da 3.0.5
- [ ] Direct drinking respeita o cooldown e não duplica consumo em spam/retry.
- [ ] Container do Supplementaries preserva purity ao transferir/armazenar água.
- [ ] Loot tables carregam sem erro de parsing.
- [ ] CreateCyberGoggle coexiste sem incompatibilidade reproduzível.
- [ ] Dedicated server inicia com Thirst 3.0.5 e Cold Sweat 2.4.3.1.
## 21. Atualização upstream 3.0.6 — não instalada
A versão física continua **3.0.5**. O índice atual do CurseForge confirma ThirstWasReclaimed-1.21.1-3.0.6.jar como release **NeoForge 1.21.1**, publicada em 16/09/2026.

O changelog 3.0.6 registra três mudanças:

- correção de **buckets que não podiam ser empilhados**;
- **reversão da mudança de default purity** introduzida anteriormente;
- **dripstone deixa de fornecer água com purity**.

Esses deltas são relevantes para o contrato de purity e containers descrito nas seções anteriores. Em especial, uma promoção para 3.0.6 precisa regressar bucket stacking, geração/coleta de água por dripstone, propagação de purity e addons que assumam o default de purity.

Há uma inconsistência de metadata na página individual do file ID 8893710, que exibe 1.20.1 em um campo apesar do filename 1.21.1. O **índice atual de arquivos do projeto**, porém, lista explicitamente ThirstWasReclaimed-1.21.1-3.0.6.jar sob **1.21.1 / NeoForge**. Portanto a existência da build 3.0.6 para 1.21.1 está confirmada; ela apenas **não é a versão fisicamente instalada**.

**Fontes upstream:** CurseForge Thirst Was Reclaimed files index e file ID 8893710.


## 22. Auditoria source exata 3.0.5 e delta Sophisticated 3.0.6 — 13/10/2026

**Autoridade física histórica imutável:** `ThirstWasReclaimed-1.21.1-3.0.5.jar`, mod ID `thirst`, runtime `1.21.1-3.0.5`, SHA-1 `53c51af93d5c6bffc1bea867cdcf7e831d019508`, posição #543 do snapshot `modlist(1).txt`; **nenhum novo JAR ou hash foi inspecionado**. A [publicação oficial 3.0.6, CurseForge 8893710](https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed/files/8893710), é candidata NeoForge 1.21.1; a [listagem oficial](https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed/files/all) confirma o target, mas uma página individual mostra campo de versão de jogo contraditório. O binário 3.0.6 não foi instalado.

**Correção de lacuna anterior: as classes de compatibilidade com a API antiga JÁ EXISTIAM no SOURCE 3.0.5.** O [commit oficial `bf07c26b7e2298368ef3dd0547a0d74876f8b6f5`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5) apresenta `gradle.properties` com `mod_version=1.21.1-3.0.5` e `mod_id=thirst`; inclui [`dev.ghen.thirst.api.ThirstHelper`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5/src/main/java/dev/ghen/thirst/api/ThirstHelper.java) herdando da API `cn.mlus.thirst.api.ThirstHelper` e [`dev.ghen.thirst.foundation.common.capability.ModAttachment`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5/src/main/java/dev/ghen/thirst/foundation/common/capability/ModAttachment.java) herdando do attachment atualizado. O histórico upstream mostra inclusão dessas bridges em **08/06/2026**, antes do commit de bump 3.0.5 de **10/09/2026**. **São provas de presença no source desse commit, não de execução, igualdade com JAR distribuído ou compatibilidade funcional com Sophisticated Thirst Upgrade 0.1.8.** Não foi encontrado `dev.ghen.thirst.foundation.common.capability.ModCapabilities` nesse source, o que impede presumir compatibilidade geral de todos os addons compilados contra APIs mais antigas.

**Delta source 3.0.5 → 3.0.6 com impacto material no pack:** [compare oficial](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/compare/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5...3a4326893b726010bbd7580c557de3d8447fc8a9) adiciona `SophisticatedPurity.java`, `MixinSwapEmptyFluidContainerHandler.java`, `MixinTankUpgradeWrapper.java`, entradas em `thirst.mixins.json` e testes específicos. Esse código tenta preservar/transferir **pureza de água em tanques Sophisticated**, incluindo reconhecimento de garrafas d'água com componentes de pureza, mistura com pureza mais baixa, drain, fill/simulate e serialização. **Não confundir esse delta source com um changelog específico da publicação**, que também informa bucket stacking, mudança de purity padrão e dripstone. A branch 3.0.6 insere mixins `@Pseudo` com **`require=1`** e alvos internos `net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeWrapper` e classe interna `SwapEmptyFluidContainerHandler`. Se assinaturas ou métodos mudarem no candidato Sophisticated Core `1.5.11`, há risco de falha de aplicação ou regressão; **não afirmar crash sem um teste**.

**Limite declarado dos testes upstream:** [`tests/sophisticated/README.md`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/tests/sophisticated/README.md) registra teste de pureza em **NeoForge 21.1.215 / Core 1.3.89.1239 / Backpacks 3.25.14.1410**, com relato de barreira para verificar Core `1.5.1.2341`. Esses testes upstream **não certificam o pack histórico Core 1.5.1 nem o candidato Core 1.5.11 / Backpacks 3.26.12**. O relatório orienta verificar `All 2 required tests passed` no log, não apenas exit-code do Gradle. Nenhum desses testes foi executado localmente nesta auditoria.

**Gate de promoção coordenada:** inspecionar manifests publicados de Reclaimed 3.0.5/3.0.6, Thirst Upgrade 0.1.8, Thirst Was Fixed 2.1.6, Sophisticated Core e Backpacks; validar Mixin targeting/require num *staging* com backup; testar fluid tanks e purity 0–3 por potions/buckets/fluids, garrafa cheia/vazia, mistura e simulação, save/chunk reload/contraption. Repetir consumo pelo upgrade automático com provider Reclaimed, filters, cooldown e remainder e comparar contabilidade exatamente uma vez. **Decisão: 3.0.5 física mantida; 3.0.6 apenas candidata PENDENTE por riscos ABI/Mixins e tests.**


## 23. Consumidor alternativo com integração direta ao Reclaimed — SUA 0.9.2 (não instalado)

A [release oficial de Soph Upgrade Addons `SophUpgradeAddons-1.21.1-0.9.2.jar`](https://www.curseforge.com/minecraft/mc-mods/soph-upgrade-addons/files/9071404) foi publicada em 05/10/2026 para **NeoForge Minecraft 1.21.1**; a [versão Modrinth 0.9.1](https://modrinth.com/mod/soph-upgrade-addons/version/dTowYtkJ) já lista **Thirst Was Reclaimed** como dependência opcional explícita, mas 0.9.2 é a publicação posterior que corrige recipes. **Não instalada.**

**Verificação cruzada exata com provider source 3.0.5:** [SUA branch `1.21.1`, commit `eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c`](https://github.com/MorenRx/Soph-Upgrade-Addons/tree/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c), `mod_version=0.9.2`: `SUACompat.THIRST` testa mod ID `thirst`; `DrinkCompatProvider` prefere `MlusThirstCompat` quando encontra `cn.mlus.thirst.api.ThirstHelper`. Esse adaptador importa `cn.mlus.thirst.api.ThirstHelper`, `IThirst`, `ModAttachment.PLAYER_THIRST` e chama `itemRestoresThirst`, `isDrink`, `getPurity`, `getThirst`, `IThirst.getThirst`. **Todas essas referências nominais foram encontradas no source oficial Reclaimed 3.0.5 `bf07c26b7e2298368ef3dd0547a0d74876f8b6f5`**; `PlayerThirst` implementa `IThirst`. Essa verificação **não recompilou contra o JAR físico nem executou hidratação**, logo não é certificação de ABI/rede ou transações de itens.

**Bloqueador anterior a testes do pack:** o [Gradle 0.9.2](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/gradle.properties) define `neo_version=21.1.252` e o [template `neoforge.mods.toml`](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/templates/META-INF/neoforge.mods.toml) exige `[21.1.252,)`. O modloader do último inventário é NeoForge **21.1.250**, e a **publicação binária 0.9.2 não teve o manifesto extraído nesta sessão**. O source posterior 0.9.2.1 reduz `neo_version_range` em `gradle.properties`, mas o template usa `neo_version` ainda `21.1.252`: não deduzir que esse ajuste resolveu o requisito.

**Ownership e decisão:** o Reclaimed segue provider único de sede e pureza; a alternativa SUA poderia atuar como **consumer de auto-drink**, mas compete funcionalmente com `sophisticatedthirst` 0.1.8 do pack e acrescenta outros upgrades (networks, ender, void, salvaging). **Não instalar, não habilitar simultaneamente nem remover o addon físico** sem validar loader/manifesto, compatibilidade com Core/Backpacks/Thirst Was Fixed e migração de mochilas existentes. Verificações pendentes: bebida/purity thresholds, remainder do item, contagem exata, interações de Reclaimed 3.0.6 com TankUpgradeWrapper, multiuser 3 blocos, configs e persistência.


## 24. Revisão dos targets de Core 1.5.11 no source — 13/10/2026

**Novo resultado estático, sem instalação:** o [source Sophisticated Core 1.21.x, commit `400660f2d4e6165076977b09e66cae26a7f2dfd3`](https://github.com/P3pp3rF1y/SophisticatedCore/tree/400660f2d4e6165076977b09e66cae26a7f2dfd3) tem `gradle.properties mod_version=1.5.11` e seu [`TankUpgradeWrapper.java`](https://github.com/P3pp3rF1y/SophisticatedCore/blob/400660f2d4e6165076977b09e66cae26a7f2dfd3/src/main/java/net/p3pp3rf1y/sophisticatedcore/upgrades/tank/TankUpgradeWrapper.java) contém todos os elementos **nominativamente exigidos** pelos dois mixins de integração [Reclaimed source 3.0.6](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/3a4326893b726010bbd7580c557de3d8447fc8a9): `getCustomFluidHandler`, `CUSTOM_FLUIDHANDLER_FACTORIES`, `fill(FluidStack, FluidAction, boolean)` com callsite `FluidStack.setAmount`, classe aninhada `SwapEmptyFluidContainerHandler`, construtor, `fill`, `drain(FluidStack,FluidAction)` e callsite `ItemStack.isSameItemSameComponents`. Isso reduz a hipótese de **alvo ausente na fonte revisada**, mas **não prova aplicação Mixin byte-a-byte nem compatibilidade da release publicada**; há `require=1`, signatures e remapping/transformers para confirmar no lançamento de teste.

**Ponto adicional de compatibilidade global:** a [classe `MixinFluidStack.java`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/src/main/java/cn/mlus/thirst/foundation/mixin/MixinFluidStack.java) ignora **somente `ThirstComponent.PURITY`** nas comparações de água ao interceptar `FluidStack.isSameFluidSameComponents`, com `ThreadLocal` para evitar recursão; isso permite passar pela comparação inicial em `TankUpgradeWrapper.fill` quando a pureza difere. Reclaimed [`MixinTankUpgradeWrapper`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/src/main/java/cn/mlus/thirst/foundation/mixin/sophisticated/MixinTankUpgradeWrapper.java) define escolha de pureza mínima (mais suja) no `EXECUTE`. **Auditar efeitos em pipes/fluids de terceiros e cenários SIMULATE/EXEcUTE**; a comparação global não significa mistura segura sem controlar quanto da água realmente foi transferido.

O source dos **2 GameTests upstream** cobre bottle filling/drain e tank mixing/serialização com Core `1.3.89`/Backpacks `3.25.14`, **não** Core candidato `1.5.11`. No pack real permanece `ThirstWasReclaimed-1.21.1-3.0.5.jar`, e os mixins adicionais correspondem à versão **3.0.6 candidata, não instalada**. Os perfis de teste cliente/servidor, bytecode JAR, hooks de consumo automático `sophisticatedthirst` 0.1.8, Thirst Was Fixed 2.1.6, Create pipes e integridade de mundo continuam **NÃO VERIFICADOS**. **Decisão: upgrade físico bloqueado; registrar source-target compat estática, sem alegar QA.**

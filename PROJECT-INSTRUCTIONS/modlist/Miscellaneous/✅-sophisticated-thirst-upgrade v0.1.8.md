# Sophisticated Thirst Upgrade

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#514**: JAR `sophisticated-thirst-upgrade.jar`, mod id `sophisticatedthirst`, runtime `0.1.8`, SHA-1 `788e5f75df0def9824b1a939e92aecffd0227d66`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Sophisticated Thirst Upgrade
- **Arquivo JAR:** `sophisticated-thirst-upgrade.jar`
- **Versão 1.21.1:** 0.1.8
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Comida, QoL
- **Função:** Adiciona upgrades ao Sophisticated Backpacks para consumir automaticamente itens de hidratação armazenados na mochila; a versão Advanced adiciona filtragem semelhante ao Feeding Upgrade.
- **Dependências:** Sophisticated Backpacks 3.26.3 está presente. O projeto upstream é publicado para Thirst Was Taken; o stack físico atual do pack usa Thirst Was Reclaimed 1.21.1-3.0.5. A ficha mantém teste runtime obrigatório em vez de presumir equivalência perfeita entre Taken e Reclaimed.
- **Sobreposição:** Não fornece barra/sistema de sede; apenas automatiza consumo a partir da mochila. Thirst Was Reclaimed continua provider da necessidade de hidratação.
- **Compatibilidade/Riscos:** Bridge de automação de hidratação. Riscos: consumir item errado/duas vezes, filtros divergentes, provider de sede incompatível, backpack sync, cooldown/threshold stale e death/relog state. Upstream publica contra Thirst Was Taken; o pack usa Thirst Was Reclaimed 3.0.5, portanto compatibilidade funcional atual deve ser validada em runtime.
- **Observações:** Runtime físico 0.1.8 no JAR `sophisticated-thirst-upgrade.jar`. A publicação oficial 1.21.1 confirma Thirst Upgrade + Advanced Thirst Upgrade; o provider atual do pack é Thirst Was Reclaimed, não o nome original Thirst Was Taken usado na página upstream.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + CurseForge oficial Sophisticated Thirst Upgrade 0.1.8 + provider físico Sophisticated Backpacks 3.26.3 e Thirst Was Reclaimed 1.21.1-3.0.5.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/thirst-upgrade-for-sophisticated-backpacks-thirst
- **Atualização/Status:** RECONCILIADO EM 27/09/2026 — addon 0.1.8 mantido; providers físicos atuais reconciliados para Sophisticated Backpacks 3.26.3 e Thirst Was Reclaimed 1.21.1-3.0.5.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticated-thirst-upgrade.jar`, mod id `sophisticatedthirst`, versão `0.1.8`, NeoForge 1.21.1. É uma bridge de automação: usa itens guardados em **Sophisticated Backpacks** para restaurar sede; não cria um sistema de sede próprio.

## 1. Identidade e papel
- **Mod:** Sophisticated Thirst Upgrade.
- **JAR:** `sophisticated-thirst-upgrade.jar`.
- **Mod id:** `sophisticatedthirst`.
- **Versão:** `0.1.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Papel:** automatizar consumo de itens hidratantes diretamente da mochila Sophisticated.
## 2. Providers e ownership
- **Sophisticated Backpacks:** inventory, upgrade slots/settings e armazenamento do item consumível.
- **Provider de sede:** valor de thirst, critérios de restauração e efeitos do consumível.
- **Sophisticated Thirst Upgrade:** detecta a necessidade e aciona consumo a partir da mochila conforme regras/config do upgrade.
A bridge não deve manter uma segunda barra de sede ou duplicar efeitos do provider.
## 3. Thirst Upgrade
O projeto publica um **Thirst Upgrade** equivalente conceitualmente ao Feeding Upgrade: quando as condições são satisfeitas, ele busca na mochila item capaz de restaurar sede e o utiliza automaticamente.
A seleção deve consumir exatamente um item por ação aceita e aplicar uma única restauração/effect chain.
## 4. Advanced Thirst Upgrade
Também existe um **Advanced Thirst Upgrade**, com filtragem semelhante ao sistema avançado de alimentação.
Filtros precisam ser server-authoritative e persistir com o upgrade/backpack. Um filtro client-only não pode permitir consumo funcional de item bloqueado pelo servidor.
## 5. Boundary com Sophisticated Backpacks
O pack contém Sophisticated Backpacks `3.26.3`. O upgrade depende do lifecycle de inventário/upgrades do backpack:
- equipar/retirar mochila;
- colocar como bloco quando suportado;
- abrir/fechar inventory;
- mover consumíveis entre slots;
- trocar/remover upgrade.
Remover o upgrade deve encerrar automação imediatamente, sem task/cache residual.
## 6. Naming mismatch do provider de sede
A publicação upstream descreve integração com **Thirst Was Taken**. O pack atual usa **Thirst Was Reclaimed 1.21.1-3.0.5**.
O catálogo anterior trata este addon como funcional no stack de sede atual, mas a identidade nominal diferente é material: não se deve afirmar compatibilidade perfeita apenas pelo conceito. Boot e consumo real precisam ser testados.
## 7. Authority da hidratação
O provider de sede continua responsável por:
- quantidade de thirst restaurada;
- efeitos secundários;
- regras de item válido;
- limites/cooldowns próprios.
O upgrade só deve acionar o mesmo consumo lógico que seria aceito pelo provider, sem hardcode paralelo de valores.
## 8. Item selection
Com múltiplos itens hidratantes na mochila, a escolha deve seguir o algoritmo/filter do upgrade e produzir resultado determinístico o suficiente para não alternar indefinidamente ou consumir item bloqueado.
O dossiê não inventa prioridade específica não publicada; esse comportamento deve ser observado em runtime.
## 9. Consumíveis com container remainder
Itens que devolvem garrafa/bowl/container após uso são regression surface. O output/remainder precisa retornar ao backpack/inventory/world exatamente uma vez conforme o contrato do item/provider.
Não pode haver perda ou duplicação por automação.
## 10. Efeitos e itens modded
Itens de outros mods podem restaurar sede e aplicar potion/effects adicionais. A bridge precisa delegar ao use action real do item/provider, não apenas reduzir stack e somar um valor fixo.
Testar itens com NBT/data components e remainders.
## 11. Threshold e frequência
A automação deve evitar consumo excessivo quando o jogador já está suficientemente hidratado ou quando a condição do upgrade não está satisfeita.
Tick checks muito frequentes em muitos backpacks podem gerar custo; medir apenas se profiler apontar o addon/upgrade path.
## 12. Client / server
Servidor deve decidir:
- necessidade de hidratação;
- item selecionado;
- stack decrement;
- restauração/effects;
- filtro funcional.
Cliente pode exibir GUI/settings. Preview visual divergente não pode executar consumo localmente.
## 13. Multiplayer
Cada jogador deve consultar apenas seu próprio backpack/upgrade state. Dois players com backpacks idênticos não podem compartilhar cooldown, filtro ou consumível por key global incorreta.
Inventory sync deve reconciliar imediatamente depois do auto-consumo.
## 14. Lifecycle e persistence
Validar:
- equip backpack;
- adicionar/remover upgrade;
- alterar filtro;
- mover itens hidratantes;
- atingir threshold de sede;
- death/relog;
- dimension change;
- backpack placement/pickup quando aplicável;
- save/restart.
Config/filtro do upgrade deve persistir; uma pending consumption não deve repetir após reconnect.
## 15. Integrações concretas no pack
- **Sophisticated Backpacks 3.26.3:** provider de storage/upgrades.
- **Thirst Was Reclaimed 3.0.5:** provider de sede atual do pack; compatibilidade requer teste por causa da nomenclatura/upstream lineage.
- **Thirst Was Fixed 2.1.6:** patch do stack de sede presente; qualquer mudança no consumo precisa ser triada entre provider/fix/upgrade.
## 16. Riscos técnicos
1. **Provider mismatch:** addon procura API/ID de Thirst Was Taken não exposta pelo Reclaimed.
2. **Double consumption:** tick/event duplica uso.
3. **Wrong item:** filtro/prioridade escolhe consumível indesejado.
4. **Remainder loss/dupe:** garrafa/container é liquidado incorretamente.
5. **Backpack sync:** cliente mostra stack antigo após consumo.
6. **Filter persistence:** Advanced Upgrade perde regras após restart.
7. **Threshold stale:** continua consumindo após thirst já restaurada.
8. **Item-use bypass:** automação ignora side effects/cooldowns do item.
## 17. Matriz de testes
- [ ] Dedicated server inicia com addon 0.1.8 + Sophisticated Backpacks 3.26.3 + Thirst Was Reclaimed 3.0.5.
- [ ] Thirst Upgrade reconhece consumível do provider atual.
- [ ] Consome exatamente um item por trigger.
- [ ] Restaura a quantidade/effects definidos pelo provider/item.
- [ ] Advanced Upgrade respeita whitelist/blacklist/filter configurado.
- [ ] Dois consumíveis diferentes seguem seleção coerente.
- [ ] Item com bottle/container remainder não duplica nem perde output.
- [ ] Remover upgrade encerra auto-consumo imediatamente.
- [ ] Backpack inventory sincroniza em multiplayer após consumo.
- [ ] Death/relog não repete uma pending action.
- [ ] Save/restart preserva filter/settings.
- [ ] Thirst Was Fixed coexistente não duplica consumo/effects.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 18. Evidências e limites
- Modlist física atual de 27/09/2026: addon 0.1.8, Sophisticated Backpacks 3.26.3, Thirst Was Reclaimed 3.0.5 e Thirst Was Fixed 2.1.6.
- CurseForge oficial: Thirst Upgrade automático e Advanced Thirst Upgrade com filtering para a linha Thirst Was Taken.
- Catálogo anterior: addon mantido no stack de sede atual.
- **Limite:** compatibilidade binária/funcional com Thirst Was Reclaimed não foi provada por runtime nesta auditoria; por isso permanece teste explícito e não uma equivalência assumida.

## Reauditoria da bridge de hidratação e risco de manutenção — 13/10/2026

**Autoridade física histórica:** `sophisticated-thirst-upgrade.jar`, mod ID `sophisticatedthirst`, runtime `0.1.8`, posição #514 e SHA-1 registrados na modlist anterior; nenhum novo dump foi obtido. O [único arquivo CurseForge 1.21.1 NeoForge, ID 8102344](https://www.curseforge.com/minecraft/mc-mods/thirst-upgrade-for-sophisticated-backpacks-thirst/files/8102344), foi publicado em **17/05/2026**, com mesmo filename `sophisticated-thirst-upgrade.jar`. O autor declara expressamente na nota dessa release que **não pretende atualizá-la para outras versões**. Não extrapolar que haverá suporte a mudança de ABI de Sophisticated Backpacks.

**Provider documentado vs instalado:** a página do addon diz **Thirst Was Taken**, mas o último inventário do pack registra **Thirst Was Reclaimed 3.0.5** e Thirst Was Fixed 2.1.6. A [branch oficial 1.21.1 do Reclaimed, commit `3a4326893b726010bbd7580c557de3d8447fc8a9`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/3a4326893b726010bbd7580c557de3d8447fc8a9) define `mod_id=thirst` em [`gradle.properties`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/gradle.properties) e `Thirst.ID = "thirst"` em [`Thirst.java`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/src/main/java/cn/mlus/thirst/Thirst.java). **Ter um mod ID genérico conhecido não demonstra, por si só, identidade de classes/APIs, contratos de item-use ou compatibilidade do addon**; seria preciso ler a metadata/class references do seu JAR e testar consumo de verdade. O source Reclaimed pinado representa trabalho 3.0.6, não prova qual código está no físico 3.0.5. A [publicação 3.0.6 para MC 1.21.1](https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed) é um candidato externo distinto e **não foi instalada nesta ação**.

**Decisão de risco:** o candidato Backpacks `3.26.12` e Core `1.5.11` podem mudar handlers/upgrade APIs dos quais a bridge depende. **Risco de compatibilidade API classificado PENDENTE (não há prova de quebra real nem de funcionamento)**. Não remover o addon por precaução sem verificar necessidade/uso em mundos existentes. A ausência de manutenção futura declarada pelo autor agrava risco de atualizar providers sem teste.

**Matriz mínima de testes em perfil descartável e cópia de mundo (não executada):** (1) dedicated-server boot + logs e HUD com Reclaimed + Fixed; (2) Thirst Upgrade e Advanced Upgrade em backpack com sede abaixo do limiar; (3) exatamente um item debitado, um efeito de hidratação e um container remainder; (4) modo advanced whitelist/blacklist e persistência de filtros após save/restart; (5) equipa/retira backpack, troca dimensão, death/relog e dois clientes sem consumir por jogador errado; (6) compara cenário físico antigo e candidato Backpacks/Core coordenados; (7) inspeciona JAR para `META-INF/neoforge.mods.toml`, referências de API, mod IDs e event hooks. Sem validação desse gate, **não considerar Thirst Upgrade compatível com o stack novo**.


## 19. Evidência adicional de proveniência e API antiga — 13/10/2026

**Cruzamento de hash independente, de origem secundária:** o [manifesto público do modpack Create - Marooned](https://modpacks.ch/modpacks/1560021/create-marooned) associa exatamente `sophisticated-thirst-upgrade.jar`, SHA-1 `788e5f75df0def9824b1a939e92aecffd0227d66`, ao **CurseForge File ID `8102344`**. Esse SHA coincide com o inventário físico histórico #514 deste pack, e o arquivo corresponde ao [projeto oficial do autor](https://www.curseforge.com/minecraft/mc-mods/thirst-upgrade-for-sophisticated-backpacks-thirst/files/8102344) para NeoForge 1.21.1. **O site terceiro é uma evidência de proveniência secundária, não hash oficial autenticado:** não houve download do JAR, cálculo local do SHA-1/SHA-256, comparação binária ou leitura do manifesto efetivo.

**API antiga no source EXATO do provider histórico:** o [source oficial Reclaimed `1.21.1-3.0.5`, commit `bf07c26b7e2298368ef3dd0547a0d74876f8b6f5`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5) conserva shims `dev.ghen.thirst.api.ThirstHelper` e `dev.ghen.thirst.foundation.common.capability.ModAttachment`, ambos delegando/estendendo classes `cn.mlus.thirst`. Essas classes não foram adicionadas apenas em 3.0.6: já estavam presentes **antes da 3.0.5**, segundo histórico oficial. Isso corrige a incerteza da reauditoria anterior sobre quais shims pertencem ao source correspondente à versão física, mas **não prova** que `sophisticatedthirst` use somente essas APIs nem que seja funcional com o Reclaimed instalado. Não há `dev.ghen.thirst.foundation.common.capability.ModCapabilities` encontrado no source 3.0.5; addons que dependam dele não podem ser liberados por analogia.

**Nova interação 3.0.6/Sophisticated:** Reclaimed 3.0.6 adiciona mixins em `Sophisticated Core TankUpgradeWrapper` e lógica explícita de pureza em garrafas/tanques. Isso **não é uma correção declarada do Thirst Upgrade 0.1.8**, mas amplia o conjunto de interações a testar com upgrades automáticos, água em tank/backpack e Core candidato 1.5.11. O upstream testou esse recurso com Core 1.3.89/Backpacks 3.25.14, **não com o Core/Backpacks candidatos deste pack**.

**Alternativa avaliada, não aprovada:** [waterupgrade 1.0.0 — NeoForge 1.21.1, File ID 8837011](https://www.curseforge.com/minecraft/mc-mods/waterupgrade/files/8837011) anuncia upgrades de beber e integração com **Tough As Nails, Legendary Survival Overhaul, Homeostatic ou Thirst Mod**, mas não menciona explicitamente **Thirst Was Reclaimed**. O [repositório oficial público](https://github.com/xlxyvergil/Water-Upgrade/tree/ca8b1ec88de58445e4a6200dac2dd343ce6d4823) inspecionado nesta rodada contém **source Forge 1.20.1**, não o target binário NeoForge 1.21.1: `ThirstModHandler.java` importa `dev.ghen.thirst.api.ThirstHelper` e `ModCapabilities`; `ThirstHandlerRegistry.java` escolhe `modId="thirst"`. A presença de ID `thirst` no Reclaimed não prova que o port 1.21.1 desse outro addon tenha API compatível. **Estado da alternativa: NÃO APROVADA; não adicionar/remover mods ou dossiês físicos.**

**QA em aberto:** extrair bytecode e metadata reais do addon `sophisticated-thirst-upgrade.jar` e confrontar as classes referenciadas com o Reclaimed 3.0.5; testar item-use/filtragem/remainder/client-server/Thirst Was Fixed, semântica de pureza e alterações de ABI do Core. O presente lote avançou apenas na **proveniência secundária e contrato source**, e não constitui validação de runtime.


## 20. Alternativa auditada em source: Soph Upgrade Addons 0.9.2 (NÃO INSTALADA) — 13/10/2026

**Decisão operacional: BLOQUEADA PARA O NEOFORGE HISTÓRICO / NÃO SUBSTITUIR** `sophisticated-thirst-upgrade.jar` / runtime `0.1.8` / SHA-1 `788e5f75df0def9824b1a939e92aecffd0227d66`. Não foi possível obter/examinar um JAR instalado ou publicar testes de Minecraft nesta sessão. O addon atual permanece na posição física #514 e as 587 linhas do inventário histórico ficam intactas.

**Candidato externo 1.21.1:** [Soph Upgrade Addons (MorenRx), CurseForge projeto 1570994](https://www.curseforge.com/minecraft/mc-mods/soph-upgrade-addons), [file `9071404` — `SophUpgradeAddons-1.21.1-0.9.2.jar`](https://www.curseforge.com/minecraft/mc-mods/soph-upgrade-addons/files/9071404), publicado em **05/10/2026** para **NeoForge 1.21.1**; changelog publicado: correção de recipes. A [listagem oficial filtrada](https://www.curseforge.com/minecraft/mc-mods/soph-upgrade-addons/files/all?page=1&pageSize=20&showAlphaFiles=hide&version=1.21.1) contém as duas releases NeoForge 1.21.1 `0.9.1` (03/10) e `0.9.2` (05/10). Não confundir o candidato com [Water Upgrade de xlxyvergil](https://www.curseforge.com/minecraft/mc-mods/waterupgrade/files/8837011), cujo source disponível anteriormente era Forge 1.20.1; são **projetos diferentes**.

**Source específico 1.21.1 próximo da release:** [MorenRx/Soph-Upgrade-Addons branch `1.21.1`, commit `eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c` de 05/10/2026](https://github.com/MorenRx/Soph-Upgrade-Addons/tree/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c), `gradle.properties` declara `mod_version=0.9.2`, `minecraft_version=1.21.1`, `neo_version=21.1.252`, `neo_version_range=[21.1.252,)`; o [template efetivo `neoforge.mods.toml`](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/templates/META-INF/neoforge.mods.toml) usa **`versionRange="[${neo_version},)"`** no bloco obrigatório `modId="neoforge"`. O último modloader documentado no pack é **NeoForge 21.1.250**, menor que **21.1.252**: **incompatibilidade declarativa com o estado histórico**, condicionada à confirmação do manifesto do JAR distribuído. O template também exige `sophisticatedbackpacks [3.21,)` e `sophisticatedcore [1.2,)` (ranges amplos, **não prova de ABI real**). A release `0.9.1` usa igualmente NeoForge `21.1.252` no source. Não propor downgrade ou instalação forçada ignorando FML.

**Tentativa posterior de relaxamento de versão não certificada:** o [commit `b1d2bb48a30d488b0f01fa99e8c80e60e00680a2` de 13/10/2026](https://github.com/MorenRx/Soph-Upgrade-Addons/tree/b1d2bb48a30d488b0f01fa99e8c80e60e00680a2) sobe o **source** para `mod_version=0.9.2.1` e altera a propriedade `neo_version_range` para `[21.1.197,)`, **mas mantém `neo_version=21.1.252` e o template usando `neo_version` no requisito obrigatório**; sem prova de novo artefato publicado e verificação de metadata, isso **não libera** o pack 21.1.250. É possível corrigir o template em upstream, porém não alterar código de terceiros ou classificar o release como compatível sem build/QA.

**Suporte de hidratação confirmado por source (não por runtime):** [`SUACompat.java`](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/java/top/morenrx/sua/init/SUACompat.java) detecta `modId="thirst"`; [`DrinkCompatProvider.java`](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/java/top/morenrx/sua/upgrades/compat/drink/DrinkCompatProvider.java) usa `Class.forName("cn.mlus.thirst.api.ThirstHelper")` para escolher `MlusThirstCompat` ao encontrar Reclaimed, ou `GhenThirstCompat` para API Taken. [`MlusThirstCompat.java`](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/java/top/morenrx/sua/upgrades/compat/drink/MlusThirstCompat.java) referencia `cn.mlus.thirst.api.ThirstHelper`, `IThirst` e `ModAttachment.PLAYER_THIRST`. As chamadas estáticas `itemRestoresThirst`, `isDrink`, `getPurity`, `getThirst`, `IThirst.getThirst` e o attachment aparecem no [source Reclaimed **3.0.5** `bf07c26b7e2298368ef3dd0547a0d74876f8b6f5`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5). **Compatibilidade estática nominal, não linkage ou gameplay comprovados.**

**Comportamento candidato e colisão funcional:** [`DrinkUpgradeWrapper.java`](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/java/top/morenrx/sua/upgrades/drink/DrinkUpgradeWrapper.java) tem upgrades Drink/Advanced, filtro de bebidas, limiar de sede, minimum purity, `drinkForHurt`, cooldowns constantes `100`/ `10` ticks e busca jogadores até **3 blocos** quando backpack é bloco. [`SUAConfig.java`](https://github.com/MorenRx/Soph-Upgrade-Addons/blob/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c/src/main/java/top/morenrx/sua/init/SUAConfig.java) declara filtros padrão 9/16 slots, `enable=true` para os upgrades. O addon inclui **Ender, Super Void, network magnet/pickup/deposit/restock, potion charm e salvaging**, muito além de hidratação; avaliar ownership e redundâncias com funcionalidades Sophisticated e outros addons. A lógica não declara `UpgradeConflictDefinition` para o Drink, de modo que **não há demonstração de prevenção nativa de conflito com o Thirst Upgrade já instalado**. Não manter os dois ativos simultaneamente sem teste anti-double-drink.

**Matriz exigida antes de substituição (PENDENTE):** (1) obter `neoforge.mods.toml` dos binários exatos `9071404` e do addon instalado; (2) confirmar NeoForge/ABI e refazer avaliação de loader sem atualizar arbitrariamente todo o pack; (3) mapear `sophisticatedthirst` vs `soph_upgrade_addons` mod IDs/upgrades/recipes/data components, NBT e risco para mochilas antigas; (4) duas fontes de hidratação isoladas e teste de coexistência/remoção na cópia do mundo; (5) debit/restore/remainder exatamente uma vez, pureza e `Thirst Was Fixed`; (6) backpack equipado/colocado, radius 3, dois usuários, death/relogin, save/restart, Create contraptions e linked groups; (7) revisar todas as funcionalidades adicionais do SUA e a eventual desativação específica por config; (8) rollback e inventário de JARs físico regenerado apenas após alteração efetiva. **Não foi instalado nem substituído nenhum item.**


## 21. Revisão do requisito de NeoForge para SUA 0.9.2 — 13/10/2026

**Bloqueio contra o perfil histórico permanece:** o Sophisticated Thirst Upgrade 0.1.8 (JAR sophisticated-thirst-upgrade.jar, posição #514) continua registrado no snapshot com NeoForge 21.1.250 (#001). O addon externo [Soph Upgrade Addons 0.9.2 / CurseForge File ID 9071404](https://www.curseforge.com/minecraft/mc-mods/soph-upgrade-addons/files/9071404) exige [NeoForge 21.1.252 ou superior no source 0.9.2](https://github.com/MorenRx/Soph-Upgrade-Addons/tree/eb7d4f35066b6f48d9926f4168f09dd4ecdb0e8c). Nenhum JAR físico foi atualizado.

**Disponibilidade upstream esclarecida:** o [Maven oficial do NeoForged](https://maven.neoforged.net/releases/net/neoforged/neoforge/) contém 21.1.252 e **21.1.253** para Minecraft 1.21.1. Portanto 21.1.253 **pode atender numericamente ao piso do SUA**, diferentemente do 21.1.250 histórico. A [mudança oficial de 21.1.253](https://maven.neoforged.net/releases/net/neoforged/neoforge/21.1.253/neoforge-21.1.253-changelog.txt) atualiza FancyModLoader para 4.0.45 e vem após a mudança de slot-copy da 21.1.252; ambos têm blast radius além do addon de sede. O source posterior SUA 0.9.2.1 ainda não prova um manifesto de release com piso relaxado; não contar essa alteração como alternativa pronta no perfil 250.

**Pré-condições de substituição cumulativas:** novo inventário físico; teste NeoForge 21.1.253 isolado, inclusive Connector/Extras/FFAPI e dependências de hundreds de mods; leitura do manifesto real do JAR 9071404 e metadata do addon físico 0.1.8; teste da integração MlusThirstCompat com Reclaimed 3.0.5/3.0.6 e Thirst Was Fixed 2.1.6; filtros/purity/cooldown, remainders e anti-double-consume; análise do overlap de upgrades e migração de IDs/recipes/data components em cópia de mundo, incluindo backup/rollback. **Sem essas provas, SUA continua CANDIDATO BLOQUEADO, e Thirst Upgrade 0.1.8 NÃO É REMOVIDO.**

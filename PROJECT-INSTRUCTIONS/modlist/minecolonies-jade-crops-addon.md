# MineColonies: Jade crops addon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db819d837dcceff4d0a134
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `mcjadecrops 1.1.1300`, Jade `15.10.6+neoforge` e MineColonies `1.1.1381-1.21.1-snapshot` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 10/09/2026”; a autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** MineColonies: Jade crops addon
- **Arquivo JAR:** `mcjadecrops-1.1.1300.jar`
- **Versão 1.21.1:** 1.1.1300
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Compat
- **Tipo de conteúdo:** Addon
- **Função:** Addon de tooltip que detecta blocos do namespace minecolonies com IntegerProperty `age`, calcula age/maxAge e exibe no Jade percentual de crescimento ou estado Mature. Não modifica crescimento, colheita, loot nem IA.
- **Dependências:** Funcionalmente: MineColonies + Jade. Source-line: NeoForge >=21.1.227 e Minecraft [1.21.1,1.22) required; Jade >=15.10.5 optional/CLIENT no loader. MineColonies não é hard dependency declarada no neoforge.mods.toml, embora seus blocos sejam o alvo funcional.
- **Sobreposição:** Sobrepõe apenas apresentação de estágio no Jade; MineColonies mantém authority do crop e Jade da infraestrutura de tooltip. Pode duplicar informação de outro Jade plugin, mas não é um segundo sistema de agricultura.
- **Compatibilidade/Riscos:** Client HUD bridge MineColonies→Jade. Riscos: mudança/ausência da IntegerProperty `age`, false positive em bloco minecolonies com `age`, tooltip duplicado, estado visual client stale e semântica de dependência opcional. Runtime MineColonies 1.1.1381 é mais novo que o baseline nominal do addon; smoke test obrigatório.
- **Observações:** Runtime físico 1.1.1300. Provider Jade UID `mcjadecrops:crop_stage`; config `enabled=true` e `debugLogging=false`. Warnings de blocos sem `age` são deduplicados por ResourceLocation em cache concorrente. Feature concreta é client-side e derivada do BlockState.
- **Procedência:** modlist.txt física atual de 10/09/2026 + source oficial alexkond12/MineColonies-Jade-crops correspondente exatamente a 1.1.1300 + arquivos de plugin/provider/resolver/config auditados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-jade-crops-addon
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 1.1.1300 exato; Jade client provider, resolução namespace+age, cálculo de maturidade, config, loader semantics, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:** 2026-09-07 — novo mod incorporado à auditoria; sem decisão curatorial ainda.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `mcjadecrops-1.1.1300.jar`, mod id `mcjadecrops`, versão `1.1.1300`, em NeoForge 21.1.248 / Minecraft 1.21.1. O source público `alexkond12/MineColonies-Jade-crops` declara exatamente `mod_version=1.1.1300`, Minecraft 1.21.1 e NeoForge mínimo 21.1.227; por isso a implementação abaixo é tratada como correspondência direta da build instalada.

## 1. Identidade e papel
MineColonies: Jade crops é um addon leve de **HUD/tooltip**. Ele não altera crescimento, colheita, drops ou IA do Farmer: observa o `BlockState` que o jogador está mirando e adiciona ao Jade uma linha com o estágio de crescimento de crops do namespace `minecolonies`.

O objetivo funcional é mostrar **percentual de crescimento** ou **Mature** sem abrir GUI ou consultar estado colonial.

## 2. Authority / ownership
- **MineColonies** continua authority do bloco, crop, propriedade `age`, crescimento e colheita.
- **Jade** continua authority da infraestrutura e renderização do tooltip.
- **mcjadecrops** apenas resolve `age/maxAge`, calcula uma apresentação e a entrega ao Jade.

Nenhum sistema do pack deve interpretar o percentual exibido como nova fonte de verdade de gameplay; é derivado do `BlockState` já sincronizado ao cliente.

## 3. Registro e client integration
A classe principal `MineColoniesJadeCrops` registra um `ModConfigSpec` do tipo **COMMON**. A integração Jade é feita por um `@WailaPlugin`.

No `registerClient`, `JadePlugin` registra `JadeCropComponentProvider.INSTANCE` para `Block.class`. O `register` comum é vazio. Assim, a feature concreta do addon está no caminho client-side de tooltip, embora o mod container/config exista no carregamento comum.

O UID do provider é `mcjadecrops:crop_stage`.

## 4. Resolução de crops
`CropStageResolver.resolve(BlockState)` aplica uma cadeia estrita:
1. o bloco precisa pertencer ao namespace `minecolonies` no `BuiltInRegistries.BLOCK`;
2. o estado precisa expor uma `IntegerProperty` cujo nome seja exatamente `age`;
3. o valor atual é lido dessa property;
4. `maxAge` é o maior valor permitido pela própria `IntegerProperty`;
5. sem `age`, retorna vazio e não adiciona tooltip.

Portanto o addon não mantém lista hardcoded de IDs de crops. Ele faz detecção estrutural por namespace + propriedade `age`.

## 5. Cálculo e apresentação
`CropProgress` considera maduro quando `age >= maxAge`.

Quando ainda não maduro, o percentual é calculado por `round(age * 100 / maxAge)` e limitado a `0..100`; se `maxAge <= 0`, retorna 0.

No tooltip:
- label traduzível `tooltip.mcjadecrops.crop.growth`, em cinza;
- crop maduro usa texto traduzível de maturidade em verde;
- demais estágios mostram `<percent>%` em branco.

Esse cálculo é puramente apresentacional: não muda ticks nem chance de crescimento.

## 6. Configuração
A build 1.1.1300 expõe dois booleanos:
- `enabled=true` — habilita/desabilita a integração de tooltip;
- `debugLogging=false` — permite logar blocos MineColonies não suportados.

O projeto documenta o arquivo como `config/mcjadecrops-common.toml`.

Para evitar spam, IDs de blocos sem property `age` são guardados em um `ConcurrentHashMap.newKeySet()` e cada ResourceLocation é avisada apenas uma vez enquanto a instância permanece ativa.

## 7. Dependências e semântica de loader
O `neoforge.mods.toml` da source-line declara:
- NeoForge `>=21.1.227` — required/BOTH;
- Minecraft `[1.21.1,1.22)` — required/BOTH;
- Jade `>=15.10.5` — **optional**, AFTER, CLIENT.

Curiosamente, MineColonies não é declarado como hard dependency no metadata do loader. Funcionalmente o addon só produz tooltip para blocos `minecolonies`, portanto MineColonies e Jade são providers necessários para a feature fazer sentido, mesmo que o loader não force os dois da mesma forma.

O pack físico contém MineColonies 1.1.1381 e Jade; a versão do addon continua 1.1.1300. A lógica por namespace/property reduz acoplamento a IDs concretos, mas não elimina risco de mudança de schema.

## 8. Client/server, rede e persistência
Não foi identificado packet próprio, capability, attachment, SavedData, banco de crops ou escrita de NBT. O provider lê o `BlockState` disponível no cliente via Jade e acrescenta texto ao tooltip.

Não há mutação server-authoritative conhecida nessa build. Persistência relevante limita-se à configuração do mod; o cache de warnings é transitório de processo.

## 9. Integrações concretas no pack
- **MineColonies 1.1.1381:** provider dos crops; está mais novo que o número embutido no nome/release do addon, exigindo smoke test de properties.
- **Jade:** surface de UI direta.
- Outros addons de MineColonies não são automaticamente integrados: apenas blocos no namespace `minecolonies` com `IntegerProperty age` entram na resolução.

## 10. Riscos
1. **Schema drift:** crop novo/alterado sem property inteira `age` deixa de exibir progresso.
2. **False positive estrutural:** qualquer bloco `minecolonies` com uma property `age` compatível pode receber a linha, mesmo que semanticamente não seja crop, se existir tal caso.
3. **Tooltip duplication:** outro plugin Jade pode exibir estágio do mesmo bloco.
4. **Stale client state:** apresentação depende do `BlockState` conhecido no cliente; atraso de sincronização visual não muda a authority server-side.
5. **Optional dependency semantics:** o metadata não força MineColonies e marca Jade como optional; ausência dos providers precisa falhar de forma limpa.
6. **Debug spam control:** o cache evita repetição por ID, mas debug em modpack grande ainda deve ser usado apenas para diagnóstico.

## 11. Matriz de testes
- [ ] Dedicated server + cliente iniciam com NeoForge 21.1.248, MineColonies 1.1.1381 e Jade atuais.
- [ ] Crop MineColonies jovem mostra percentual coerente com `age/maxAge`.
- [ ] Estágio máximo mostra **Mature**.
- [ ] Bloco MineColonies sem `age` não recebe linha falsa.
- [ ] Bloco de outro namespace com `age` não é capturado.
- [ ] `enabled=false` remove a linha sem afetar o crop.
- [ ] `debugLogging=true` registra um bloco incompatível uma única vez por ID.
- [ ] Relog/restart não altera crescimento nem gera estado persistente indevido.
- [ ] Tooltip não duplica informação de outro provider Jade ativo.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências consultadas
- Modlist física: `mcjadecrops-1.1.1300.jar`, runtime `1.1.1300`.
- Source oficial: `alexkond12/MineColonies-Jade-crops`, `gradle.properties` exatamente 1.1.1300.
- Arquivos auditados: `neoforge.mods.toml`, `MineColoniesJadeCrops.java`, `JadePlugin.java`, `JadeCropComponentProvider.java`, `CropStageResolver.java`, `MineColoniesCropRegistry.java`, `CropProgress.java`, `McJadeCropsConfig.java`.
- Limite: esta ficha descreve a source-line pública atual correspondente à versão física; o JAR não foi decompilado byte-a-byte nesta etapa.

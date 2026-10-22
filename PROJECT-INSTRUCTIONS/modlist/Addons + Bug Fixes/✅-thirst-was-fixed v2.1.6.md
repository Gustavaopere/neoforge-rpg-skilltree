# Thirst Was Fixed

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#542**: JAR `thirstwasfixed-2.1.6.jar`, mod id `thirstwasfixed`, runtime `2.1.6`, SHA-1 `94f5ae571e4ab828c8aca8f7f9b26e98c59292d3`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Thirst Was Fixed
- **Arquivo JAR:** `thirstwasfixed-2.1.6.jar`
- **Versão 1.21.1:** 2.1.6
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** 
- **Categoria:** Compat, Comida, QoL
- **Função:** Addon de Thirst Was Reclaimed que corrige purity/cauldron edge cases e adiciona integrações configuráveis de hidratação com Ars Nouveau/Elemental, FTB Ultimine, ParCool e Amendments.
- **Dependências:** Thirst Was Reclaimed é dependência funcional; pack instala 3.0.5. Integrações fisicamente relevantes: Ars Nouveau 5.13.1, Ars Elemental 0.7.10.1, FTB Ultimine 2101.1.15, ParCool 4.0.0.3 e Amendments 2.1.10.
- **Sobreposição:** Não cria uma segunda barra de sede; estende e corrige o provider Thirst Was Reclaimed.
- **Compatibilidade/Riscos:** Purity semantic drift com TWR 3.0.x; fluid dupe/loss em Flasks/Urn/cauldrons; gates Ultimine e stamina ParCool devem ser server-authoritative. 2.1.6 corrige crash com Millénaire olive oil, mostrando superfície ativa com fluids externos.
- **Observações:** mod id `thirstwasfixed`; runtime 2.1.6. Não cria segunda barra de sede. Config física não foi lida; speed/stamina/purity/Ultimine options não são presumidas ativas.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + Thirst Was Fixed 2.1.6 + provider físico Thirst Was Reclaimed 1.21.1-3.0.5 + integrações físicas Ars Nouveau/Ars Elemental/FTB Ultimine/ParCool/Amendments. O histórico 3.0.4 de 11/13 de setembro foi preservado; config e runtime não foram testados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed ; https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed/files/8794908
- **Atualização/Status:** RECONCILIADO EM 27/09/2026 — Thirst Was Fixed 2.1.6 permanece instalado; o provider funcional atual é Thirst Was Reclaimed 1.21.1-3.0.5. Cauldrons/purity, Ars/Ultimine/ParCool/Amendments, lifecycle, riscos e testes permanecem documentados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `thirstwasfixed-2.1.6.jar`, mod id `thirstwasfixed`, versão `2.1.6`. É um **addon de correções e integrações para Thirst Was Reclaimed**, não um segundo provider de barra de sede.
## 1. Identidade e dependência
- **Mod:** Thirst Was Fixed.
- **JAR:** `thirstwasfixed-2.1.6.jar`.
- **Mod id:** `thirstwasfixed`.
- **Versão:** `2.1.6`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Dependência funcional desde a linha 2.x:** Thirst Was Reclaimed; pack instala `ThirstWasReclaimed-1.21.1-3.0.5.jar`.
## 2. Authority e ownership
- **Thirst Was Reclaimed:** authority da barra de sede, purity e regras base de hidratação.
- **Thirst Was Fixed:** corrige edge cases e adiciona bridges/configs para mods externos.
O addon não deve manter state paralelo de thirst nem ser tratado como substituto do provider.
## 3. Correção de cauldrons/pureza
Features publicadas:
- opção para corrigir cauldrons existentes sem purity definida;
- opção para beber diretamente de cauldrons com a mão vazia;
- configuração de purity para rainwater e água de Pointed Dripstone.
Essas funções precisam respeitar a semântica de purity vigente em Thirst Was Reclaimed 3.0.5, cuja linha 3.0.x passou por grande refactor.
## 4. Full-thirst speed bonus
O addon oferece opção de adicionar **speed bonus** quando a barra de sede está cheia.
Esse bônus é uma extensão opcional, não comportamento obrigatório do provider. A config física não foi lida; não afirmar que esteja habilitado.
## 5. Ars Nouveau Potion Flasks
Integração oficial com Potion Flasks:
- podem restaurar thirst;
- opcionalmente podem ser preenchidos em water source blocks e cauldrons como Glass Bottles;
- capacidade pode ser aumentada por config.
O pack possui Ars Nouveau `5.13.1`, portanto esta integração é fisicamente relevante.
## 6. Ars Elemental — Urn of Endless Waters
Com Ars Elemental, a Urn pode:
- definir purity configurada ao encher cauldrons;
- opcionalmente encher Buckets e Bottles com purity configurada;
- opcionalmente encher Potion Flasks.
O pack possui Ars Elemental `0.7.10.1`, portanto testar conservação de fluido e purity é obrigatório.
## 7. FTB Ultimine
Integração permite exigir uma quantidade configurada de thirst para usar Ultimine.
O pack possui FTB Ultimine `2101.1.15`. O gate precisa ser server-authoritative: cliente não deve conseguir iniciar operação se o servidor considerar thirst insuficiente.
## 8. ParCool
O addon pode aplicar **bônus ou penalidade de regeneração de stamina** dependendo do nível de thirst.
O pack possui ParCool `4.0.0.3`. Isso cria coupling entre dois recursos de survival/movement; testar thresholds e evitar feedback loop que torne sprint/parkour impossível ou gratuito.
## 9. Amendments — boiling cauldrons
Integração oficial: **boiling cauldrons purify water** quando Amendments está presente.
O pack possui Amendments `2.1.10`. É necessário validar que a purity resultante é gravada/propagada corretamente para bottles/buckets/fluid handlers.
## 10. Release 2.1.6
Changelog exato 2.1.6: corrige crash com **Millénaire olive oil**.
Mesmo que esse item/mod não seja central no pack atual, o fix demonstra que handlers de fluid/item externos são superfície de compatibilidade ativa. Não extrapolar suporte universal a qualquer edible fluid.
## 11. Configuração
A página oficial expõe várias opções independentes para cauldrons, purity, flask capacity, speed, Ultimine e ParCool.
A configuração física não foi lida; todos esses recursos devem ser descritos como **configuráveis**, não automaticamente ativos.
## 12. Client / server e lifecycle
- Servidor: thirst gate, purity, cauldron state e bonuses que afetam gameplay.
- Cliente: HUD/feedback proveniente do provider e integrações visuais.
Validar:
- login/relog;
- chuva/dripstone enchendo containers;
- cauldron chunk unload/reload;
- Ars Flask/Urn após restart;
- Ultimine e ParCool com thirst perto do threshold;
- server config reload/restart conforme suporte.
## 13. Integrações concretas no pack
Confirmadas fisicamente:
- Thirst Was Reclaimed `3.0.5`;
- Ars Nouveau `5.13.1`;
- Ars Elemental `0.7.10.1`;
- FTB Ultimine `2101.1.15`;
- ParCool `4.0.0.3`;
- Amendments `2.1.10`.
Cold Sweat existe no pack, mas Thirst Was Fixed não deve ser catalogado como bridge de temperatura sem fonte explícita.
## 14. Riscos técnicos
1. **Purity semantic drift:** TWR 3.0.x refatorou purity; addon deve concordar com provider.
2. **Fluid duplication/loss:** Urn/Flask/cauldron bridges precisam conservar volume e purity.
3. **Double buffs:** speed/stamina integrations podem somar com outros atributos do pack.
4. **Gate desync:** Ultimine deve usar thirst do servidor.
5. **Cauldron legacy state:** correção de cauldrons antigos não pode sobrescrever purity válida.
6. **External fluids:** 2.1.6 corrige crash específico com olive oil; outros fluid items modded são regression surface.
## 15. Matriz de testes
- [ ] Dedicated server boot com TWR 3.0.5 + TWF 2.1.6.
- [ ] Cauldron sem purity recebe correção conforme opção sem alterar cauldron válido.
- [ ] Beber de cauldron reduz volume/aplica thirst uma vez.
- [ ] Rainwater e dripstone recebem purity configurada.
- [ ] Potion Flask restaura thirst e preserva capacidade/purity.
- [ ] Urn enche cauldron/bucket/bottle/flask sem dupe/loss.
- [ ] FTB Ultimine é bloqueado/liberado no threshold configurado.
- [ ] ParCool stamina regen muda apenas conforme config/thirst esperado.
- [ ] Amendments boiling cauldron purifica e propaga purity aos containers.
- [ ] Relog/restart preserva state dos containers.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 16. Evidências
- Modlist física canônica 08/09/2026: JAR e integrações presentes.
- CurseForge oficial ThirstWasFixed: escopo de fixes/integrations, features detalhadas e release exata 2.1.6.
- Changelog 2.1.6: fix de crash com Millénaire olive oil.
## 17. Revalidação física — 11/09/2026
A modlist física atual mantém exatamente `thirstwasfixed-2.1.6.jar`, mod id `thirstwasfixed`, versão `2.1.6`, com Thirst Was Reclaimed `3.0.4` como provider funcional. A release oficial 2.1.6 continua sendo a build NeoForge 1.21.1 relevante.
As integrações documentadas com Ars Nouveau, Ars Elemental, FTB Ultimine, ParCool e Amendments permanecem superfícies reais do stack, mas nenhuma configuração local, purity bridge, stamina gate ou transferência de fluido foi executada nesta recatalogação. A matriz de testes permanece integralmente desmarcada.
## 18. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `thirstwasfixed-2.1.6.jar`, versão `2.1.6`, e a release NeoForge 1.21.1 localizada continua nessa versão. Thirst Was Reclaimed `3.0.4` permanece o provider funcional. As integrações com Ars Nouveau/Ars Elemental, FTB Ultimine, ParCool e Amendments continuam documentadas sem presumir que suas opções estejam habilitadas na config local. Nenhum teste de purity, cauldron, Flask/Urn, stamina ou gate de Ultimine foi executado nesta revalidação.
## 19. Reconciliação física — 27/09/2026
A autoridade física atual mantém `thirstwasfixed-2.1.6.jar`, mod id `thirstwasfixed`, runtime `2.1.6`, enquanto o provider funcional instalado passou a `ThirstWasReclaimed-1.21.1-3.0.5.jar`, mod id `thirst`, runtime `1.21.1-3.0.5`. As referências operacionais atuais desta ficha foram atualizadas para 3.0.5; as revalidações históricas de 11/13 de setembro permanecem preservadas com 3.0.4. Nenhum teste de purity, cauldron, Flask/Urn, stamina ou gate de Ultimine foi executado nesta reconciliação.

## Release e compatibilidade — reauditoria 13/10/2026

**Atualização upstream NeoForge 1.21.1:** 2.1.6. **Fonte oficial:** https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed/files/all.

CurseForge oficial ainda indica **2.1.6** (02/09/2026) como a última release NeoForge Minecraft 1.21.1, coincidente com o JAR histórico `thirstwasfixed-2.1.6.jar`. Não confundir com versões do provider **Thirst Was Reclaimed**. Como addon fix/integration, manter ownership da sede no provider original; testar consumables, bebidas, thirst sync client/server, integration com food/temperature mods, save/restart, potion/fill via Create se houver, e recipe/item handlers exactly-once. Nenhuma novidade publicada confirmada nesta revisão.

**Autoridade física:** estado histórico de `modlist(1).txt` mantido; JAR, metadata, SHA e testes locais não foram re-observados. Filename do `.md` com ✅ e versão física herdada preservado. A revisão documental não significa instalação ou aprovação de promoção.


## 20. Pré-compatibilidade com Reclaimed 3.0.6 e mixins Sophisticated — 13/10/2026

**Físico preservado:** `thirstwasfixed-2.1.6.jar` / runtime `2.1.6`, posição #542 e SHA-1 histórico `94f5ae571e4ab828c8aca8f7f9b26e98c59292d3`; provider histórico `ThirstWasReclaimed-1.21.1-3.0.5.jar`. Não foi disponibilizado JAR físico novo, nem há prova de atualização/teste.

**Evidência de source do provider:** [Reclaimed 3.0.5 no commit `bf07c26b7e2298368ef3dd0547a0d74876f8b6f5`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/bf07c26b7e2298368ef3dd0547a0d74876f8b6f5) já contém pontes de compatibilidade para `dev.ghen.thirst.api.ThirstHelper` e `dev.ghen.thirst.foundation.common.capability.ModAttachment`; a presença no source não garante chamadas corretas do Thirst Was Fixed sem inspeção de seu bytecode. [Reclaimed 3.0.6 source](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/3a4326893b726010bbd7580c557de3d8447fc8a9) acrescenta mixins de pureza em `Sophisticated Core` (`TankUpgradeWrapper`, `SwapEmptyFluidContainerHandler`), tratamento de água de garrafas/tanques e testes de integração; o [README dos testes upstream](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/tests/sophisticated/README.md) limita o ambiente observado a Core 1.3.89 e Backpacks 3.25.14, sem certificar Core 1.5.11/Backpacks 3.26.12.

**Ownership e riscos de regressão:** Reclaimed controla hidratação e pureza; Thirst Was Fixed aplica patches/integrações próprias, Sophisticated Core/Backpacks são providers de inventário/tank, Sophisticated Thirst Upgrade aciona consumo automático. Nenhuma extensão deve criar segunda autoridade de água, cobrar consumível duas vezes, alterar purity durante `SIMULATE` ou corrigir o mesmo comportamento em dois mixins incompatíveis. **Não atribuir conflito ou compatibilidade como fato observado.**

**Gates pendentes para qualquer promoção:** verificar `thirstwasfixed` 2.1.6 contra mixins/metadata reais do Reclaimed 3.0.5/3.0.6, NeoForge e Core, com boot de dedicated server; purity 0–3 em bucket/bottle/tank; Create tank/fluid pipe; items remainders; cliente-servidor; save/restart, chunk unload, dois jogadores e ausência de duplicação de hydration/Source. Nenhum teste desse gate foi executado neste lote; decisão física **MANTER HISTÓRICO / COMPATIBILIDADE CANDIDATA PENDENTE**.


## 21. Auditoria do source 2.2.0 — Moonlight, ParCool e NeoForge (13/10/2026)

**Instalado na última autoridade física, não reinspecionado nesta sessão:** Thirst Was Fixed 2.1.6 (thirstwasfixed-2.1.6.jar, #542, SHA-1 94f5ae571e4ab828c8aca8f7f9b26e98c59292d3), Thirst Was Reclaimed 3.0.5 (#543), ParCool 4.0.0.3 e NeoForge 21.1.250 (#001). A [última release NeoForge 1.21.1 identificada na página CurseForge consultada](https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed/files/8794908) é 2.1.6, File ID 8794908, de 02/09/2026. Não foi baixado ou validado um JAR 2.2.0.

### 21.1. Dois snapshots upstream distintos

- [Source do commit aec5c8a65596ba8162858b22189057edbaa847d4 (02/09/2026)](https://github.com/Partonetrain/thirstwasfixed/tree/aec5c8a65596ba8162858b22189057edbaa847d4): gradle.properties declara mod_version=2.1.6, neo_version=21.1.247, neo_version_range=[21.1.247,), thirst_version_range=[1.21.1-3.0.4,). Corresponde temporalmente ao código 2.1.6, mas não se comprovou equivalência exata com o bytecode publicado.
- [Source do commit 47fb0e7063f9e906aa05da492a938ad45f39cb8c (13/10/2026)](https://github.com/Partonetrain/thirstwasfixed/tree/47fb0e7063f9e906aa05da492a938ad45f39cb8c): mod_version=2.2.0 apenas no source inspecionado; neo_version=21.1.256 para desenvolvimento, mantendo neo_version_range=[21.1.247,). O [template neoforge.mods.toml](https://github.com/Partonetrain/thirstwasfixed/blob/47fb0e7063f9e906aa05da492a938ad45f39cb8c/src/main/templates/META-INF/neoforge.mods.toml) usa neo_version_range efetivamente como dependência. NeoForge 21.1.250 satisfaz esse limite **apenas numericamente**: uma eventual compilação contra 21.1.256 pode introduzir dependência binária que o range sozinho não denuncia. Não confundir com o template defeituoso do projeto separado Soph Upgrade Addons 0.9.2.1.

### 21.2. Alteração de API do ParCool

O [commit intermediário 3c2fd04b61f6563513e36111aa8bd5f8b73ebe8c](https://github.com/Partonetrain/thirstwasfixed/commit/3c2fd04b61f6563513e36111aa8bd5f8b73ebe8c) troca em ParCoolHelper o owner de STAMINA_RECOVERY de com.alrex.parcool.api.Attributes para com.alrex.parcool.api.ParCoolAttributes. Também muda a dependência de desenvolvimento ParCool do CurseForge File ID 6797814 para 9106851. O [commit 47fb0e7](https://github.com/Partonetrain/thirstwasfixed/commit/47fb0e7063f9e906aa05da492a938ad45f39cb8c) altera o NeoForge de desenvolvimento para 21.1.256 e a resolução do Millénaire para Curse Maven File ID 8913592. A reorganização de seções de Config.java não prova alteração funcional de configuração. **Não inferir** que ParCool físico 4.0.0.3 tenha a nova classe, nem que 2.2.0 seja compatível com ele, antes de inspecionar classes/metadata dos JARs e executar boot.

### 21.3. Mixin de água em Moonlight do source 2.1.6

A classe [SoftFluidTankMixin](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/mixin/moonlight/SoftFluidTankMixin.java) foi lida no source 2.1.6 e é textual/funcionalmente igual na branch 2.2.0 auditada. Usa ModifyReturnValue em SoftFluidTank.isFluidCompatible e Inject cancelável no início de SoftFluidTank.addFluidOntoExisting. Quando a configuração SOFT_FLUID_WATER_MERGE, WaterPurity.isEnabled e a identidade de água de ambos os soft fluids permitem, ignora a diferença de componentes na aceitação; se ambas as purezas estão presentes e diferem, grava a pureza mínima e aumenta o volume, cancelando o caminho original. A [Config.java 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/Config.java) define Soft Fluid Water Merge=true **como default de código**; a configuração real do pack não foi lida. Casos de pureza ausente, data components adicionais, volume e persistência permanecem pendentes de teste.

### 21.4. Relação com os mixins do Reclaimed 3.0.6

O MixinFluidStack global do Reclaimed 3.0.6 descrito pelas PRs #897–#898 atua em NeoForge FluidStack, enquanto o Mixin do Thirst Was Fixed age em Moonlight SoftFluidTank/SoftFluidStack. **Não são o mesmo alvo de Mixin**, de modo que não há prova de colisão direta no mesmo método. Há risco semântico de água migrar entre Moonlight/Supplementaries, Create e tanques Sophisticated com critérios diferentes de purity; isso deve ser testado, e não declarado como dupe real.

O [thirstwasfixed.mixins.json do source 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/resources/thirstwasfixed.mixins.json) possui required=true, defaultRequire=1 e lista Moonlight, PlayerThirst, Ars, FTB, Supplementaries e outros mixins. O [MixinPlugin](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/MixinPlugin.java) condiciona bridges opcionais à identificação de cada mod. Presença na lista não certifica aplicação válida na build física.

### 21.5. Gate de promoção (NÃO EXECUTADO)

1. Inspecionar JAR físico 2.1.6 e eventual JAR publicado 2.2.0: manifesto, Mixin config, class signatures e SHA; revalidar inventário.
2. Verificar a presença de ParCoolAttributes e o contrato STAMINA_RECOVERY no ParCool físico 4.0.0.3; não atualizar Thirst Was Fixed isoladamente.
3. Conferir os alvos binários SoftFluidTank.isFluidCompatible/addFluidOntoExisting no Moonlight físico; ler configs efetivas.
4. Comparar água em Moonlight/Supplementaries, Create e Sophisticated com purity nula, igual e diferente, conservando volume e componentes; SIMULATE não pode alterar estado.
5. Dedicated-server e client boot, dois clientes, reload, mundo salvo, chunk unload/reload, persistência e ausência de double processing.
6. Executar baseline Reclaimed 3.0.5 versus candidato 3.0.6 em cópia de perfil e com rollback; NeoForge 21.1.259 seria estágio **separado**, não pré-requisito presumido para o source 2.2.0.

**Decisão:** MANTER 2.1.6 no estado físico histórico e no filename do dossiê; source 2.2.0 em OBSERVAÇÃO e não instalado. Gate binário/runtime PENDENTE. Nenhuma atualização física ou teste de Minecraft foi realizada nesta auditoria documental.


### 21.6. Resolução das duas dependências de desenvolvimento ParCool

A checagem da publicação CurseForge dos **file IDs já presentes no diff upstream** resolve a nomenclatura exata: [ID 6797814 = ParCool 1.21.1-3.4.1.1-NF](https://www.curseforge.com/minecraft/mc-mods/parcool/files/6797814), dependência de desenvolvimento declarada no source ThirstWasFixed 2.1.6; [ID 9106851 = ParCool 1.21.1-4.0.1.2](https://www.curseforge.com/minecraft/mc-mods/parcool/files/9106851), dependência de desenvolvimento declarada no source ThirstWasFixed 2.2.0. **O pack, entretanto, mantém ParCool-1.21.1-4.0.0.3.jar, runtime 4.0.0.3, #433**, versão intermediária entre as duas integrações de desenvolvimento. Isso demonstra **drift de contrato de API a investigar**, não prova de crash: checar se a classe nova ParCoolAttributes, o atributo STAMINA_RECOVERY e a semântica de modifiers estão presentes no JAR 4.0.0.3. Uma promoção 4.0.0.3 → 4.0.1.2 seria outra atualização física, com dependências e regressões próprias, e **não está autorizada como consequência automática**. Nenhum dos três JARs foi inspecionado nesta sessão.


## 22. Reconciliação do owner de atributo com ParCool 4.0.0.3 — source 1.21.1 (13/10/2026)

**Evidência nova que refina a seção 21.6:** o [commit oficial ParCool `5116ae71574428ba63e162f7e39a0f2c5e1d641c`](https://github.com/alRex-U/ParCool/tree/5116ae71574428ba63e162f7e39a0f2c5e1d641c) foi localizado na branch **`1.21.1-v4`**, com `gradle.properties` declarando `mod_version=4.0.0.3`, `minecraft_version=1.21.1`, `neoforge_version=21.1.248` e range `[21.1.248,)`. A [classe `api/ParCoolAttributes.java` desse commit](https://github.com/alRex-U/ParCool/blob/5116ae71574428ba63e162f7e39a0f2c5e1d641c/src/main/java/com/alrex/parcool/api/ParCoolAttributes.java) declara `STAMINA_RECOVERY` como `DeferredHolder<Attribute, Attribute>`, com ID de registro `parcool:parcool.stamina_recovery` e chave de descrição `attribute.name.parcool.stamina_recovery`. A árvore source completa (881 entradas, não truncada) **não contém** `api/Attributes.java`. É evidência de **source correspondente à linha 1.21.1**, não leitura de bytecode. O commit `8f480f8` com a mesma versão numérica declara **Minecraft 1.19.2 / Forge** e não serve como prova para este runtime NeoForge.

**Desalinhamento preexistente:** o [`ParCoolHelper` no source ThirstWasFixed 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/ParCoolHelper.java), associado ao JAR físico #542, referencia `com.alrex.parcool.api.Attributes.STAMINA_RECOVERY` (classe ausente no source ParCool 4.0.0.3), o que cria **risco estático concreto de linkage** se o caminho for acionado. Não diagnosticar erro em produção sem logs/bytecode. O [`Config.java` 2.1.6](https://github.com/Partonetrain/thirstwasfixed/blob/aec5c8a65596ba8162858b22189057edbaa847d4/src/main/java/info/partonetrain/thirstwasfixed/Config.java) configura por default `Stamina Bonus Requirement=0` e `Stamina Penalty Requirement=0`: os dois ramos de acesso ao campo são condicionalmente ignorados no source quando os requirements são zero. Configuração do pack **não lida**.

**Correção nominal somente no source candidato 2.2.0:** o [commit de alteração `3c2fd04`](https://github.com/Partonetrain/thirstwasfixed/commit/3c2fd04b61f6563513e36111aa8bd5f8b73ebe8c) usa `ParCoolAttributes.STAMINA_RECOVERY`, símbolo encontrado no source do ParCool 4.0.0.3. **Isto resolve a dúvida de existência nominal em source** registrada anteriormente; não prova build, compatibilidade de bytecode, Mixin, nem sucesso com NeoForge 21.1.250. O arquivo de distribuição 2.2.0 não foi verificado nem instalado.

**QA prioritário:** extrair os JARs atuais, comparar descriptors de owner/campo e aliases, verificar `ParCoolAttributes`/`Attributes` efetivos, ler os limiares reais, testar cliente e dedicated server em cópia com valores `0` e não nulos e logs de linkage, modifiers idempotentes, networking e relog/restart. Se houver incompatibilidade confirmada, decidir mitigação por configuração versus atualização coordenada em staging (sem promover item por conveniência). **Físico inalterado:** TWF 2.1.6, ParCool 4.0.0.3 e Reclaimed 3.0.5 continuam no snapshot.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** ThirstWasFixed
- **Último candidato 1.21.1 / NeoForge localizado:** **2.2.1 (Release, 15/10)**
- **Estado:** **CANDIDATO COORDENADO**
- **Gate de promoção:** Revalidar com ParCool 4.0.1.6, Thirst Was Reclaimed/Sophisticated integrations, stamina attribute owner/API, purity/fluid handlers, configs e dedicated server.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed ; https://www.curseforge.com/minecraft/mc-mods/thirstwasfixed/files/8794908


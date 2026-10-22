# Sophisticated Core

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#518**: JAR `sophisticatedcore-1.21.1-1.5.1.2341.jar`, mod id `sophisticatedcore`, runtime `1.5.1`, SHA-1 `a256b4d3c1218754a3172253e6bda644551be6a5`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Sophisticated Core
- **Arquivo JAR:** `sophisticatedcore-1.21.1-1.5.1.2341.jar`
- **Versão 1.21.1:** 1.5.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Biblioteca
- **Função:** Shared core/library for the Sophisticated ecosystem, centralizing reusable inventory/storage, upgrade, filtering/settings, serialization/sync and common infrastructure consumed by Backpacks/Storage/addons.
- **Dependências:** Library itself is the required base for Sophisticated Backpacks 3.26.3, Sophisticated Storage 1.5.91 and their integrations currently installed. Other addons also consume Sophisticated APIs.
- **Sobreposição:** Infrastructure library; no standalone storage system to compare. Removing it while consumers remain would break the Sophisticated stack.
- **Compatibilidade/Riscos:** Shared library structurally required by the installed Sophisticated stack. Risks: ABI/API drift, shared serialization/config regression, upgrade registry mismatch, packet/component sync errors and consumer version skew. Direct consumers physically present include Backpacks 3.26.3, Storage 1.5.91 and both Create integrations.
- **Observações:** JAR físico `sophisticatedcore-1.21.1-1.5.1.2341.jar`, runtime 1.5.1. It has no independent gameplay proposition; value/necessity derives from consumers. Previous 1.4.x/1.5.1.2333 references are historical.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + CurseForge official Sophisticated Core 1.5.1.2341 + direct installed consumers in current modlist.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/8839323
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — runtime físico permanece 1.5.1 / build 2341. CurseForge publicou Sophisticated Core 1.5.2.2343 para NeoForge 1.21.1 em 27/09/2026; o delta principal confirmado no source é shared linked storage support.
- **Histórico da decisão:** Mantido as core estrutural do ecossistema Sophisticated. Revalidated on 11/09/2026 against physical runtime 1.5.1.2341 and direct installed consumers.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sophisticatedcore-1.21.1-1.5.1.2341.jar`, mod id `sophisticatedcore`, versão `1.5.1`, NeoForge 1.21.1. É a **library compartilhada** do ecossistema Sophisticated e possui consumers diretos instalados; a decisão vigente **Manter** é preservada.

## 1. Identidade e papel
- **Mod:** Sophisticated Core.
- **JAR:** `sophisticatedcore-1.21.1-1.5.1.2341.jar`.
- **Mod id:** `sophisticatedcore`.
- **Versão:** `1.5.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server como library compartilhada.
- **Papel:** infraestrutura comum para os mods Sophisticated.
## 2. Consumers físicos comprovados
No snapshot atual, consumers inequívocos incluem:
- Sophisticated Backpacks `3.26.3`;
- Sophisticated Storage `1.5.91`;
- Sophisticated Backpacks Create Integration `0.2.0`;
- Sophisticated Storage Create Integration `0.1.21`.
Outros addons do pack também usam APIs/conteúdo do ecossistema.
## 3. Necessidade estrutural
Sophisticated Core não tem proposta de gameplay independente. Sua presença é justificada pela árvore de dependências dos consumers.
Removê-lo enquanto Backpacks/Storage permanecem não é uma otimização válida; tende a resultar em dependency failure/class loading errors.
## 4. Shared upgrade infrastructure
A família Sophisticated compartilha conceitos de upgrades, slots, filters e settings. Core centraliza abstrações reutilizadas para que Backpacks e Storage não mantenham implementações incompatíveis do mesmo sistema.
O consumer continua owner do container concreto; Core fornece a infraestrutura.
## 5. Inventory/storage contracts
Core participa dos contratos comuns de inventory/storage utilizados pelos consumers. Mutation real deve continuar server-authoritative e passar pelo container/provider apropriado.
A library não deve criar um segundo inventário independente apenas por expor helpers/capabilities.
## 6. Filters e settings
Settings de upgrades/filtros precisam serializar, sincronizar e persistir de forma consistente entre consumers.
Uma alteração de Core pode afetar vários mods simultaneamente; por isso bugs de filter/settings em Backpacks e Storage podem ter causa compartilhada.
## 7. Serialization
State comum de upgrades/settings/components depende de schemas/codecs/serialization da linha. Update com mudança incompatível pode produzir:
- defaults inesperados;
- state perdido;
- data antiga não reconhecida;
- modifiers/upgrades reaplicados.
Backup e teste de mundo são obrigatórios antes de update major do stack.
## 8. Network/sync boundary
GUI e state de container precisam ser sincronizados entre servidor/cliente. Core fornece infraestrutura comum para essa comunicação onde usada pelos consumers.
Cliente não deve ser authority de stack count, upgrade install ou filter funcional.
## 9. ABI/API boundary
Como library, o maior risco é **version skew**: consumer compilado contra API diferente da disponível em runtime.
Sintomas típicos incluem `NoSuchMethodError`, `NoClassDefFoundError`, registry failures ou comportamento silenciosamente quebrado após update parcial.
## 10. Update policy do stack
Atualizar Core isoladamente sem checar Backpacks/Storage/integrations é alto risco. O caminho seguro é tratar versões do ecossistema como conjunto compatível e validar requirements publicados.
A modlist física, não uma versão upstream isolada, continua authority do runtime atual.
## 11. Client / server
Mesmo sem gameplay próprio, Core pode conter infraestrutura usada nos dois lados. Dedicated server precisa carregar a versão compatível quando consumers server-side dependem dela.
Client-only removal não é seguro se o modpack distribui consumers que referenciam classes comuns.
## 12. Lifecycle compartilhado
Regression surfaces:
- game bootstrap/registries;
- login/network handshake;
- container open/close;
- install/remove upgrade;
- data/config reload quando aplicável;
- save/restart;
- chunk load de storage colocado;
- assembly/disassembly via integrations Create.
## 13. Relação com Backpacks
Backpacks usa Core para infraestrutura comum, mas mantém ownership de backpack inventory, tiers e lifecycle portátil/placed.
Bug reproduzível apenas em backpack pode estar no consumer; bug idêntico em Backpack + Storage sugere investigar Core/shared layer.
## 14. Relação com Storage
Sophisticated Storage reutiliza o mesmo ecossistema de upgrades/settings em containers estacionários.
Core não decide topology/controller/storage block state específico; esses pertencem a Storage.
## 15. Create integrations
As duas bridges Create dependem de Core além de seus providers específicos. Movement/contraption state pode exercitar serialization/capability paths compartilhados.
Update parcial de Core pode quebrar integrations mesmo quando Backpacks/Storage básicos ainda parecem funcionar.
## 16. Addons externos
Ars Sophisticated Compatibility e outros addons presentes podem consumir APIs do ecossistema. Isso amplia o blast radius de mudanças de Core.
Compatibilidade precisa ser avaliada por requirements/boot/runtime, não por nome semelhante.
## 17. Performance
A library em si não deve ser avaliada por “conteúdo visível”. Custo deriva dos consumers e dos paths compartilhados executados.
Profiling deve apontar método/consumer/path concreto antes de culpar/remover Core.
## 18. Riscos técnicos
1. **ABI drift:** consumer espera método/classe diferente.
2. **Version skew:** apenas um membro do stack é atualizado.
3. **Serialization regression:** settings/upgrades perdem state.
4. **Packet mismatch:** GUI/state diverge client↔server.
5. **Registry mismatch:** upgrade/type não registra corretamente.
6. **Shared bug blast radius:** Backpacks e Storage quebram simultaneamente.
7. **Addon incompatibility:** third-party integration usa API antiga.
8. **Partial update false-positive:** boot funciona, feature específica quebra depois.
## 19. Matriz de testes
- [ ] Dedicated server inicia com Core 1.5.1 e todos consumers físicos.
- [ ] Backpacks abre/edita upgrades/settings sem erro.
- [ ] Storage abre/edita upgrades/settings sem erro.
- [ ] Create integrations carregam e executam assembly básico.
- [ ] Addon externo Sophisticated relevante carrega sem linkage error.
- [ ] Save/restart preserva filters/settings dos dois consumers principais.
- [ ] Multiplayer container sync não gera ghost stacks.
- [ ] Upgrade install/remove sincroniza cliente e servidor.
- [ ] Dependency/version scan confirma requirements do stack antes de qualquer update.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 20. Evidências e limites
- Modlist física atual: Core 1.5.1.2341 e consumers diretos instalados.
- CurseForge oficial: Sophisticated Core é a library dos mods Sophisticated e a build física é Release 1.21.1.
- Catálogo atual: Backpacks, Storage e bridges Create dependem explicitamente do Core.
- **Limite:** classes/APIs internas específicas não foram inventadas sem source exato; o dossiê documenta o contrato compartilhado observado/publicado e mantém runtime tests pendentes.

## 21. Atualização upstream 1.5.2.2343 — não instalada
A versão física continua **1.5.1.2341**. O CurseForge lista **sophisticatedcore-1.21.1-1.5.2.2343.jar** como release NeoForge 1.21.1 de 27/09/2026.

Entre a build física 2341 e a release 2343, o source oficial registra como mudança funcional principal **shared linked storage support**. Esse contrato compartilhado é relevante porque Backpacks, Storage e as integrações Create consomem infraestrutura do Core; a bridge Sophisticated Backpacks Create Integration 0.2.1.171 publicada no mesmo ciclo declara atualização de sua integração de linked storage para o Core mais recente.

Não se atribui esse comportamento ao runtime 1.5.1 instalado. Uma promoção deve ser feita de forma coordenada com os consumers Sophisticated para evitar version skew de API/serialization/link state.

Gate de regressão: linked storage entre Backpacks/Storage, Create integrations, save/restart, chunk unload/reload, multiplayer sync, filtros/upgrades sobre storage compartilhado e abertura de inventories montados.

Fontes upstream: https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/all?page=1&pageSize=20&version=1.21.1 ; https://github.com/P3pp3rF1y/SophisticatedCore/commit/f5ec6fb7636a867f87e37cc388077be20221abfe

> **Atualização/Status — valor histórico preservado do Notion:** RECONCILIADO EM 27/09/2026 — Sophisticated Core 1.5.1.2341 permanece físico; consumer Sophisticated Backpacks reconciliado para 3.26.3.


## 22. Atualizações upstream 1.5.4.2356 → 1.5.5.2363 — não instaladas
A cadeia posterior ao runtime físico `1.5.1.2341` foi revisada até a release mais recente de 03/10/2026. Para NeoForge 1.21.1, as releases relevantes são **1.5.2.2343 → 1.5.4.2356 → 1.5.5.2363**; não há release pública 1.5.3 nessa sequência do CurseForge.

### 1.5.2.2343 — shared linked storage
Já documentada acima: introduz infraestrutura compartilhada de **linked storage**, ampliando o contrato de persistência, sincronização e integração consumido por Backpacks/Storage e bridges.

### 1.5.4.2356 — migração e memory settings
O changelog/source upstream corrige problemas com impacto direto em integridade de dados:
- **Backpacks e Storages perdendo item data ao migrar mundos**;
- **crash nas memory settings ao desmarcar slots**.

O source adiciona normalização de ItemStacks legados via DataFixer antes de reconstruir inventories, inclusive preservando contagens estendidas e custom data. Isso torna save migration um gate obrigatório para qualquer promoção do Core.

### 1.5.5.2363 — links de storage
O source oficial da 1.5.5 corrige **storage links perdendo conexões quando um barrel era quebrado**. É uma correção de consistência da malha de linked storage/controller, não apenas cosmética.

### Política para este pack
O runtime físico documentado continua **1.5.1.2341**. A 1.5.5.2363 é a mais recente disponível, mas Sophisticated Core é uma library compartilhada e não deve ser atualizada isoladamente sem validar version skew com os consumers físicos. O snapshot atual contém, entre outros, Sophisticated Backpacks 3.26.3.2158 e Sophisticated Storage 1.5.91.2127, além das integrações Create.

**Recomendação:** tratar a promoção como atualização coordenada do ecossistema Sophisticated. O fato de existirem releases mais novas dos consumers não autoriza alterá-los nesta tarefa, porque eles não fazem parte do lote solicitado.

### Gate de regressão adicional
- cópia/migração de mundo antigo com inventories preenchidos;
- nested backpack/custom data e stacks com contagem não trivial;
- memory settings: select/unselect individual e all;
- linked barrels: break/place/relink, chunk unload/reload e restart;
- multiplayer container sync e absence de ghost/lost stacks;
- Backpacks + Storage + ambas as Create integrations no mesmo runtime.

Fontes upstream: CurseForge Sophisticated Core 1.5.4.2356 e 1.5.5.2363; commits oficiais `0c393553fb16a8195bb2f8a7a4b656176680734b` e `1a284cdd4445897264ff0131344ded6f1ddcacc7` em `P3pp3rF1y/SophisticatedCore`.

## 23. Atualizações upstream 1.5.6.2374 → 1.5.7.2381 — 10/10/2026

**Autoridade física:** `sophisticatedcore-1.21.1-1.5.1.2341.jar` / 1.5.1. As seções 21–22 deste dossiê já preservam a cadeia **1.5.2.2343 → 1.5.4.2356 → 1.5.5.2363**: shared linked storage, prevenção da perda de item data em migração de mundo, crash de memory settings ao desmarcar slots e perda de storage links após quebra de barrel. **Não existe release pública 1.5.3 para NeoForge 1.21.1 no índice verificado.**

### 1.5.6.2374 (05/10/2026)
O arquivo `sophisticatedcore-1.21.1-1.5.6.2374.jar` foi publicado para NeoForge 1.21.1, mas **o changelog técnico específico não foi isolado com segurança nesta auditoria**. Não atribuir à 1.5.6 features/fixes vistos em versões vizinhas ou de outro Minecraft.

### 1.5.7.2381 (06/10/2026)
O arquivo `sophisticatedcore-1.21.1-1.5.7.2381.jar` é a release pública mais recente NeoForge 1.21.1 identificada em 10/10. Changelog publicado:
- corrige **dimensões do ícone do Ender Linker** na interface;
- atualiza **traduções espanholas**.

O primeiro fix é visual, mas o comportamento de linked storage continua sendo o risco fundamental herdado da série 1.5.2–1.5.5. Não deduzir correção de corrupção de dados na 1.5.7 quando o upstream só documenta correção do ícone.

### Contrato de atualização e gate
Sophisticated Core deve ser promovido **coordenadamente** com Sophisticated Backpacks (físico 3.26.3, upstream 3.26.9), Sophisticated Storage (físico 1.5.91, upstream 1.6.2), bridges Create (Storage Create físico 0.1.21; Backpacks Create físico 0.2.0) e Sophisticated JEI Index. Em cópia do mundo verificar migração e fidelidade de data components, linked barrels/backpacks, break/relink, controller, memory slot selection, Ender Linker icon scaling em GUI grande/pequena, multiplayer concurrent sync, reload, chunks e save/restart. Compatibilidade não pode ser inferida de arquivo CurseForge recente nem de CI documental.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/all?version=1.21.1 ; https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/9083874

**Estado:** físico permanece 1.5.1.2341, disponível 1.5.7.2381; **promover somente após confirmação de manifests e teste de integração**.

## Reauditoria de publicação e compatibilidade — 13/10/2026

**Projeto:** Sophisticated Core. **Última release pública identificada para Minecraft 1.21.1 / NeoForge:** `1.5.11.2394` (`sophisticatedcore-1.21.1-1.5.11.2394.jar`). **Referência oficial:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/all.

Release NeoForge 1.21.1 de 12/10/2026, sucedendo 1.5.7.2381 e versões anteriores. Biblioteca compartilhada: migrar somente após alinhar Sophisticated Backpacks 3.26.12, Sophisticated Storage 1.6.4 e duas bridges Create (Backpacks 0.2.3; Storage 0.1.23). Changelog granular desta build não foi estabelecido; não inferir semântica de upgrades/handlers. Validar serialização de NBT/data components, capabilities, networking menus, automação, filtros, limite de stack, movimentação com contraptions, load/unload e mundos persistentes; rollback com backup.

**Autoridade física:** permanece o JAR/versão registrados nas seções históricas deste dossiê e na `modlist(1).txt`; NÃO foi disponibilizada nova lista física nem realizada instalação/teste in-game. **Recomendação:** atualização física pendente de reconciliação de dependências, SHA e validações; não interpretar o número publicado como versão instalada. **Filename `.md`:** ✅ herdado e versão do JAR físico preservados conforme protocolo canônico.

## Contrato upstream coordenado — 13/10/2026

**Autoridade física preservada:** `sophisticatedcore-1.21.1-1.5.1.2341.jar`, runtime `1.5.1` e SHA-1 históricos do snapshot; não houve dump novo ou alteração de JAR. **Candidato upstream, não instalado:** `sophisticatedcore-1.21.1-1.5.11.2394.jar`, NeoForge 1.21.1, publicado em 12/10/2026. [CurseForge oficial](https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/all?version=1.21.1).

O [source 1.21.x pinado em `400660f2d4e6165076977b09e66cae26a7f2dfd3`](https://github.com/P3pp3rF1y/SophisticatedCore/tree/400660f2d4e6165076977b09e66cae26a7f2dfd3) declara `mod_version=1.5.11`, Minecraft `[1.21.1,1.21.2)` e NeoForge `[21.1.229,)` em `gradle.properties`. O [template NeoForge](https://github.com/P3pp3rF1y/SophisticatedCore/blob/400660f2d4e6165076977b09e66cae26a7f2dfd3/src/main/templates/META-INF/neoforge.mods.toml) inclui JEI opcional/client-only e mixin Create condicionado ao mod Create. O pack histórico registra NeoForge 21.1.250 e Create 6.0.10, que atendem os respectivos pisos numéricos.

**Dependentes candidatos:** Backpacks 3.26.12.2208 e Storage 1.6.5.2176 declaram Core `[1.5.9,)` obrigatório nos templates, após expansão do Gradle; a versão 1.5.11 está acima desse piso. Ambas as bridges Create exigem suas bases específicas, sem Core como dependência direta no template, mas usam suas APIs transitivamente. O changelog exclusivo Core 1.5.11 não foi isolado, portanto não são atribuídos novos métodos não documentados.

**QA pendente:** verificar o manifesto expandido do JAR publicado, ID `sophisticatedcore`, hash, Java/NeoForge, contratos com Sophisticated JEI Index 1.2.3, Thirst Upgrade, Ars Compat e create_sophback_compat; migrar cópia de mundo com item data/NBT/data components, storage links, memory settings, linked upgrades, menus multiplayer e save/restart. Rollback requer JARs e mundo coerentes. **Estado: PENDENTE de validação binária/runtime; nenhuma instalação/teste executado.**


## Compatibilidade estática dos targets de mixin Reclaimed 3.0.6 — 13/10/2026

**Status: targets presentes no SOURCE candidato; aplicação binária/runtime NÃO VERIFICADA.** A instalação histórica permanece `sophisticatedcore-1.21.1-1.5.1.2341.jar` / runtime `1.5.1`, SHA-1 e versão física previamente certificados no inventário, sem novo acesso ao JAR. A revisão abaixo usa apenas a [branch oficial do Core `1.21.x`, commit `400660f2d4e6165076977b09e66cae26a7f2dfd3` de 13/10/2026](https://github.com/P3pp3rF1y/SophisticatedCore/tree/400660f2d4e6165076977b09e66cae26a7f2dfd3), cujo `gradle.properties` indica `mod_version=1.5.11`; **não equivale a extrair o JAR publicado `1.5.11.2394`**. O source de [Thirst Was Reclaimed `3.0.6` commit `3a4326893b726010bbd7580c557de3d8447fc8a9`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/tree/3a4326893b726010bbd7580c557de3d8447fc8a9) é o consumidor dos alvos; o pack mantém **Reclaimed `3.0.5`**, que ainda não contém esses mixins específicos de tanques.

**Matriz de correspondência entre source e mixin:**

| Mixin upstream em Reclaimed 3.0.6 | Elemento-alvo observado no source Core 1.5.11 | Evidência e limite |
|---|---|---|
| `MixinTankUpgradeWrapper`: `@Inject(method="getCustomFluidHandler", at=@At("RETURN"), require=1)` | `TankUpgradeWrapper.getCustomFluidHandler(ItemStack)` privado estático e map `CUSTOM_FLUIDHANDLER_FACTORIES` | **Método e campo existem**; ainda sem bytecode local, remapping aplicado ou prova de injeção |
| `MixinTankUpgradeWrapper`: `@Redirect(method="fill", target=FluidStack.setAmount(int), require=1)` | `TankUpgradeWrapper.fill(FluidStack, FluidAction, boolean)` chama `contents.setAmount(contents.getAmount() + toFill)` apenas para tanque não vazio e `EXECUTE` | **Ponto de chamada presente**; validação de descritores e contagem da injeção depende do JAR real |
| `MixinSwapEmptyFluidContainerHandler`: `@Inject(method="<init>", at=@At("RETURN"), require=1)`; `@Inject(method="fill", at=@At("RETURN"), require=1)` | Classe interna `TankUpgradeWrapper$SwapEmptyFluidContainerHandler`, construtor e `fill(FluidStack, FluidAction)` estão presentes | **Elementos presentes**; não confundir nome de classe interna com uma API pública estável |
| `MixinSwapEmptyFluidContainerHandler`: `@Redirect(method="*", target=ItemStack.isSameItemSameComponents, require=1, allow=1)` | Há chamada à comparação em `SwapEmptyFluidContainerHandler.drain(FluidStack, FluidAction)` | **Callsite presente**; o wildcard e o método compilado precisam de validação Mixin real |
| `MixinSwapEmptyFluidContainerHandler`: `@ModifyVariable(method="drain(FluidStack,FluidAction)", at=@At("HEAD"), argsOnly=true, require=1)` | `SwapEmptyFluidContainerHandler.drain(FluidStack, FluidAction)` presente | **Assinatura Java compatível no source**; index de variável, descriptor gerado e conflito com outros transformers não testados |

**Interação que explica mistura de purezas:** o Core `fill(...)` rejeita fluidos com componentes diferentes usando `FluidStack.isSameFluidSameComponents`; o Reclaimed [`MixinFluidStack`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/src/main/java/cn/mlus/thirst/foundation/mixin/MixinFluidStack.java) intercepta esse comparador globalmente para **água**, removendo de cópias somente o componente `ThirstComponent.PURITY` ao comparar. O mixin tem trava `ThreadLocal` para não recursar. A lógica de Reclaimed [`MixinTankUpgradeWrapper`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/src/main/java/cn/mlus/thirst/foundation/mixin/sophisticated/MixinTankUpgradeWrapper.java) tenta então conservar a pureza **mínima/mais suja** durante o `EXECUTE`. **Implicação de QA:** conferir em **todo provider de fluidos do pack**, não só Sophisticated, que ignorar diferenças de pureza na comparação não permita bypass de qualidade da água, vazamento de componentes ou mutações em `SIMULATE`.

**Limite dos testes upstream:** [`tests/sophisticated/README.md`](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/tests/sophisticated/README.md) registra **2 GameTests** usando NeoForge `21.1.215`, Core `1.3.89.1239`, Backpacks `3.25.14.1410`; o autor aponta bloqueio para validar Core histórico `1.5.1.2341`. **Não extrapolar** passes para Core `1.5.11`, Backpacks `3.26.12` ou Reclaimed `3.0.6` no modpack real. [Source dos GameTests](https://github.com/mlus-asuka/Thirst-Was-Reclaimed/blob/3a4326893b726010bbd7580c557de3d8447fc8a9/tests/sophisticated/java/cn/mlus/thirst/compat/sophisticated/SophisticatedPurityGameTests.java) verifica pureza 0–3 em bottle, tank, simulação, inserção inválida, serialização e mixing.

**Gate de promoção NÃO EXECUTADO:** obter hashes/manifestos e bytecode dos JARs publicados Core `1.5.11` e Reclaimed `3.0.6`; executar bootstrap cliente e servidor com Mixin diagnostics e conferir cada `require=1` efetivamente aplicado; testar tanks e água de purity 0–3 nos dois sentidos, `SIMULATE` sem mutações, remainders de garrafas/buckets, serialização/recarga, pipes Create, outros fluid handlers, contraptions e multiplayer. Registrar logs reais; reverter juntos Core/Reclaimed/Backpacks e backup do mundo ao detectar quebra. **Classificação: correspondência estrutural source POSITIVA; compatibilidade efetiva PENDENTE.**

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Sophisticated Core
- **Último candidato 1.21.1 / NeoForge localizado:** **1.5.17.2426 (Release, 21/10)**
- **Estado:** **CANDIDATO COORDENADO**
- **Gate de promoção:** Não atualizar isoladamente. Validar junto de Backpacks, Storage, bridges Create e consumers; exigir anti-dupe, linked inventories, reopen/save/restart, chunk unload e dedicated server.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/8839323


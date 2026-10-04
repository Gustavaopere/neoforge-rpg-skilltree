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

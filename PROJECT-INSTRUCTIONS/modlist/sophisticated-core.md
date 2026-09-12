# Sophisticated Core

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817ca9d3d515e3a45838
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sophisticatedcore-1.21.1-1.5.1.2341.jar`, mod id `sophisticatedcore`, runtime `1.5.1`, mixins `sophisticatedcore.mixins.json` e `sophisticatedcore.create.mixins.json`; Backpacks 3.26.2, Storage 1.5.91 e as duas integrações Create presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sophisticated Core 1.5.1 e seus consumers diretos citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sophisticated Core
- **Arquivo JAR:** `sophisticatedcore-1.21.1-1.5.1.2341.jar`
- **Versão 1.21.1:** 1.5.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Biblioteca
- **Função:** Shared core/library for the Sophisticated ecosystem, centralizing reusable inventory/storage, upgrade, filtering/settings, serialization/sync and common infrastructure consumed by Backpacks/Storage/addons.
- **Dependências:** Library itself is the required base for Sophisticated Backpacks 3.26.2, Sophisticated Storage 1.5.91 and their integrations currently installed. Other addons also consume Sophisticated APIs.
- **Sobreposição:** Infrastructure library; no standalone storage system to compare. Removing it while consumers remain would break the Sophisticated stack.
- **Compatibilidade/Riscos:** Shared library structurally required by the installed Sophisticated stack. Risks: ABI/API drift, shared serialization/config regression, upgrade registry mismatch, packet/component sync errors and consumer version skew. Direct consumers physically present include Backpacks 3.26.2, Storage 1.5.91 and both Create integrations.
- **Observações:** JAR físico `sophisticatedcore-1.21.1-1.5.1.2341.jar`, runtime 1.5.1. It has no independent gameplay proposition; value/necessity derives from consumers. Previous 1.4.x/1.5.1.2333 references are historical.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge official Sophisticated Core 1.5.1.2341 + direct installed consumers in current modlist.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sophisticated-core/files/8839323
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sophisticated Core 1.5.1 reconstruído: shared upgrade/storage infrastructure, direct physical consumers, ABI/version boundary, client/server sync, lifecycle, risks and tests.
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
- Sophisticated Backpacks `3.26.2`;
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

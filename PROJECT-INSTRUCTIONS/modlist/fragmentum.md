# Fragmentum — 2.4.4

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81bd9a88fe3745dd7808  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Fragmentum
- **Arquivo JAR:** `fragmentum-neoforge-1.21.1-2.4.4.jar`
- **Versão 1.21.1:** `2.4.4`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/ObscuriaLithium/fragmentum/tree/1.21.1
- **Função:** Framework/core cross-platform da Obscuria Collection: abstrações de plataforma e deferred registries, config, networking/payloads, built-in packs e client registries/tooltips; não adiciona gameplay por si só.
- **Dependências:** Runtime NeoForge 1.21.1 / Java 21. O JAR físico incorpora YACL 3.6.6+1.21.1 NeoForge, LuaJ core/JSE e dependências transitivas internas em META-INF/jarjar; não são entradas top-level.
- **Compatibilidade/Riscos:** Riscos principais: version drift entre consumer/framework, double registration, payload mismatch, stale state após reload, mixin collision e client classloading em dedicated server; a linha 1.21.1 já teve correção upstream específica para mixin client carregado no servidor.
- **Sobreposição:** Sobreposição apenas de infraestrutura com outras bibliotecas/abstrações. Não é redundância temática; remover ou substituir exige mapear consumidores reais. Evitar registro/config/networking duplicado por APIs Fragmentum e NeoForge nativo.
- **Observações:** Runtime físico e source coincidem em Fragmentum 2.4.4 para Minecraft 1.21.1. Framework declarado como exclusivo da arquitetura Obscuria Collection, não general-purpose. YACL/LuaJ estão embarcados e não devem virar páginas top-level.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial ObscuriaLithium/fragmentum branch 1.21.1 exatamente em 2.4.4 + CurseForge oficial file 8707553.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Fragmentum 2.4.4 source-pinned; registry/config/networking/packs, side boundaries, JarJar, lifecycle, riscos e matriz de testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `fragmentum-neoforge-1.21.1-2.4.4.jar`, mod id `fragmentum`. O branch oficial `ObscuriaLithium/fragmentum:1.21.1` declara exatamente `version=2.4.4`, `minecraft_version=1.21.1` e Java 21, portanto é um source pin compatível com a versão instalada. Fragmentum é framework da Obscuria Collection e **não adiciona conteúdo de gameplay por si só**.

## 1. Identidade, versão e authority
- **Mod:** Fragmentum.
- **JAR físico:** `fragmentum-neoforge-1.21.1-2.4.4.jar`.
- **Mod id:** `fragmentum`.
- **Versão instalada:** `2.4.4`.
- **Minecraft / loader / Java:** 1.21.1 / NeoForge / Java 21.
- **Source pin:** branch `1.21.1`, `gradle.properties` exatamente em 2.4.4.
- **Papel:** core/framework compartilhado pelos mods cross-platform da Obscuria Collection.
- **Authority:** Fragmentum é autoridade apenas sobre seus contratos de framework/abstração. Conteúdo, registries e gameplay continuam pertencendo ao mod consumidor.

## 2. Papel no modpack
A descrição oficial é explícita: Fragmentum fornece ferramentas compartilhadas e arquitetura multi-loader para manter os mods consumidores mais simples e sem código específico de loader espalhado. Não deve ser removido enquanto existir consumidor que o exija. Também não deve ser tratado como “mod de conteúdo invisível”: sua importância está em APIs, registro, config, networking, packs e abstrações de plataforma.

## 3. Superfícies confirmadas no source 2.4.4
O source pinado contém, entre outras, as seguintes superfícies concretas:
- `Fragmentum`, `FragmentumFactory`, `FragmentumProxy`, `ModCompat` e abstração `Platform`;
- client: `ClientGroupTooltip`, `ClientRegistrar`, `FragmentumClient`, `FragmentumClientRegistry`, `TooltipComponentRegistry`;
- config: `ConfigBuilder` e `ConfigValue`;
- networking: `FragmentumNetworking` e `PayloadRegistrar`;
- built-in packs: `BuiltInPackBuilder`, `BuiltInPackRegistry`, `BuiltInRepositorySource` e `FragmentumLayer`;
- registry abstractions: `BootstrapContext`, `Deferred`, `DeferredAttribute`, `DeferredBlock`, `DeferredBlockEntity`, `DeferredEntity`, `DeferredItem` e outras classes do pacote `content.registry`.

Essas classes confirmam a função de framework; não são prova de itens/blocos próprios registrados pelo host.

## 4. Registro e ownership
A família `Deferred*` encapsula objetos registráveis para consumidores. Em desenvolvimento próprio, não criar registro paralelo “porque Fragmentum está presente”: o mod consumidor deve continuar dono do seu namespace e lifecycle. Fragmentum fornece a infraestrutura de deferred/bootstrap; duplicar registro por NeoForge direto e pela abstração pode produzir ids duplicados ou inicialização fora de ordem.

## 5. Configuração
- O source 2.4.4 possui abstrações `ConfigBuilder`/`ConfigValue`.
- `gradle.properties` referencia `config_version=21.1.4` e `yacl_version=3.6.6` no projeto upstream.
- O JAR físico NeoForge incorpora `yet-another-config-lib-3.6.6+1.21.1-neoforge.jar` sob `META-INF/jarjar/`.
- Configs específicas pertencem aos consumidores; não presumir que Fragmentum tenha gameplay toggles próprios além de sua infraestrutura sem inspeção pontual.

## 6. Built-in packs e resource lifecycle
`BuiltInPackBuilder`, `BuiltInPackRegistry` e `BuiltInRepositorySource` confirmam suporte a packs embutidos. `FragmentumLayer` é parte dessa superfície. Mods consumidores podem registrar recursos/datapacks via framework; consequentemente, resource/datapack reload é lifecycle relevante. Registros derivados de pack não devem se acumular a cada reload.

## 7. Networking
`FragmentumNetworking` e `PayloadRegistrar` confirmam uma camada comum para payloads. Fragmentum fornece transporte/registro, mas a validação semântica do pacote pertence ao consumidor. Qualquer ação de gameplay recebida do cliente deve continuar server-authoritative no mod que implementa a regra.

## 8. Client / server
- CurseForge marca Fragmentum 2.4.4 como **Client & Server**.
- O source separa pacote client e classes comuns, evidenciando side boundaries.
- Tooltips/client registries são client-facing; registry/config/networking/packs possuem superfície comum.
- Histórico upstream: uma release 2.1.1 corrigiu tentativa de carregar mixin client-side em dedicated server. Embora 2.4.4 seja posterior, isso demonstra que classloading de side é um risco real da linha 1.21.1 e deve permanecer na matriz de testes.

## 9. Dependências embarcadas no JAR físico
A modlist física mostra como conteúdo embarcado do host, **não top-level**:
- `luaj-core-3.0.8-figura.jar`;
- `yet-another-config-lib-3.6.6+1.21.1-neoforge.jar` (`yet_another_config_lib_v3`), com mixins YACL;
- dependências internas da YACL visíveis no nesting: `common-io 3.12.0`, `imageio-metadata 3.12.0`, `gson 0.2.1`, `imageio-webp 3.12.0`, `common-image 3.12.0`, `imageio-core 3.12.0`, `common-lang 3.12.0`, `json 0.2.1`;
- `luaj-jse-3.0.8-figura.jar`.

Esses jars não recebem páginas próprias apenas por estarem em `META-INF/jarjar/`.

## 10. Lifecycle e multiplayer
Para consumidores do framework, validar: mod construction/registry bootstrap, common/client setup, login/relogin quando houver payloads, disconnect, dimension change quando state de consumidor for sincronizado, resource/datapack reload, config reload quando suportado e dedicated server boot. Em multiplayer, erros de codec/payload, ordem de registro e versão de consumidor podem aparecer como falha do framework mesmo quando a causa está no consumidor; diagnosticar ownership antes de patchar Fragmentum.

## 11. Integrações com a modlist
Fragmentum deve ser relacionado apenas a mods Obscuria/consumidores realmente presentes e confirmados por metadata ou source. A simples presença de outros frameworks, YACL ou Lua não cria integração temática. YACL/LuaJ aparecem embarcados e pertencem ao runtime do host.

## 12. Riscos técnicos
- **Remoção com dependentes presentes:** falha de load por missing dependency/API.
- **Version drift consumidor/framework:** `NoSuchMethodError`, `NoClassDefFoundError` ou falha de linkage.
- **Double registration:** consumer usando abstração Fragmentum e registro loader-native para o mesmo objeto.
- **Client classloading em dedicated server:** risco historicamente material na linha 1.21.1.
- **Payload mismatch:** codecs/registrars divergentes entre cliente e servidor.
- **Reload/stale registry derivado:** built-in pack ou cache de recurso acumulado após reload.
- **Mixin collision:** o JAR físico possui `fragmentum.mixins.json` e `fragmentum.neoforge.mixins.json`.
- **JarJar confusion:** YACL/LuaJ embarcados não devem ser tratados como mods independentes da modlist.

## 13. Matriz de testes obrigatória
- [ ] Dedicated server boot com Fragmentum 2.4.4 e seus consumidores atuais.
- [ ] Client boot e conexão ao dedicated server.
- [ ] Ausência de client-class load no servidor.
- [ ] Registry bootstrap de cada consumidor sem duplicate id/double registration.
- [ ] Resource reload e datapack reload com built-in packs dos consumidores.
- [ ] Login/relogin e reconexão para consumidores que usam payloads Fragmentum.
- [ ] Mismatch controlado de versão em ambiente de teste para identificar erro de linkage de forma clara.
- [ ] Config UI/client config de consumidor baseado em YACL sem tocar classes client no server.
- [ ] Smoke test de cada mod consumidor após atualização isolada do Fragmentum.

## 14. Evidências e limites
**Canônico:** modlist física atual e metadata nela extraída: JAR, mod id, versão, mixins e JarJar.

**Source primário:** `ObscuriaLithium/fragmentum`, branch `1.21.1`, exatamente `version=2.4.4`.

**Distribuição oficial:** CurseForge Fragmentum NeoForge, file `8707553`, release 2.4.4 de 22/08/2026 para 1.21.1.

**Limite:** o projeto declara que a biblioteca é destinada à Obscuria Collection e não é general-purpose. Não listar APIs como “usadas” por um mod específico sem confirmar o consumidor.

**Nenhum teste de runtime foi executado nesta catalogação.**
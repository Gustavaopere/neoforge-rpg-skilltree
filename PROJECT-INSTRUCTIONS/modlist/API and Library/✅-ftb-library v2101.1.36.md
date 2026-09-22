# FTB Library

## Propriedades do registro

- **Mod:** FTB Library
- **Arquivo JAR:** `ftb-library-neoforge-2101.1.36.jar`
- **Versão 1.21.1:** `2101.1.36`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/FTBTeam/FTB-Library/tree/1.21.1/main
- **Função:** Core/biblioteca do ecossistema FTB: UI/widgets/panels/sidebar, configuração e SNBT, networking/sync, NBT editor, registry/resource selectors, text/icons/math e APIs compartilhadas por FTB Chunks, Quests, Teams, Ultimine e XMod Compat.
- **Dependências:** Ecossistema FTB/Architectury conforme consumidor. Pack instala FTB Chunks 2101.1.22, FTB Quests 2101.1.36, FTB Teams 2101.1.11, FTB Ultimine 2101.1.15 e FTB XMod Compat 21.1.11. FTB Chunks 2101.1.21+ requer Library 2101.1.34+; 2101.1.36 atende.
- **Compatibilidade/Riscos:** Biblioteca central: risco de ABI/version skew, packet/codec mismatch, client classloading, stale KnownServerRegistries, permissões de NBT/config, shared-UI mixin collision e conflitos de key modifiers. 2101.1.35 corrigiu o bug NeoForge de Tab/Shift-Tab; 2101.1.36 adiciona `CursorType.MOVE` e corrige numeric value handling no NBT editor.
- **Sobreposição:** Infraestrutura compartilhada, não substituto de FTB Quests/Teams/Chunks/Ultimine. Pode sobrepor visualmente tooltips/sidebar/UI de outros mods; item_modname é false por default justamente porque outros mods costumam adicionar esse tooltip.
- **Observações:** Runtime físico confirmado: 2101.1.36. A release instalada adiciona `CursorType.MOVE` e corrige tratamento de valores numéricos no NBT editor; o source detalhado anteriormente foi auditado na 2101.1.35, portanto o delta 2101.1.36 é atribuído ao changelog oficial sem projetar source não revalidado.
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `ftb-library-neoforge-2101.1.36.jar`, mod id `ftblibrary`, runtime `2101.1.36` e SHA-1 `07b5bf1c6ac5160a6cfe1b39ed8c1465dc151b00`. CurseForge oficial confirma File ID 8858846, release NeoForge 1.21.1 publicada em 11/09/2026.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #289: runtime atualizado de 2101.1.35 para 2101.1.36; delta oficial (`CursorType.MOVE` + correção de valores numéricos no NBT editor) incorporado.
- **Data da última decisão:** 2026-08-26

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `ftb-library-neoforge-2101.1.36.jar`, mod id `ftblibrary`, versão instalada `2101.1.36`, Minecraft 1.21.1 / NeoForge. O source detalhado foi auditado enquanto a linha 1.21.1 correspondia à 2101.1.35; o delta 2101.1.36 é atribuído ao changelog oficial, sem projetar source não revalidado. FTB Library é infraestrutura compartilhada: **não é autoridade sobre quests, teams, claims ou ultimine**; esses domínios pertencem aos mods consumidores.
</callout>

## 1. Identidade, versão e authority

- **Mod:** FTB Library.
- **JAR físico:** `ftb-library-neoforge-2101.1.36.jar`.
- **Mod id:** `ftblibrary`.
- **Versão instalada:** `2101.1.36`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Source audit:** a árvore 1.21.1 foi auditada detalhadamente em 2101.1.35; para 2101.1.36, os dois deltas atribuídos nesta ficha vêm do changelog oficial da release.
- **Papel:** biblioteca/core do ecossistema FTB, fornecendo UI/widgets, configuração/SNBT, networking, registry utilities, sidebar, seleção de recursos, NBT editing, ícones/text, math/utilities e abstrações consumidas por outros mods FTB.
- **Authority:** FTB Library é dona dos seus contratos de biblioteca, protocolos e componentes de UI. FTB Quests continua autoridade sobre quests; FTB Teams sobre equipes; FTB Chunks sobre claims/map/force-load; FTB Ultimine sobre vein mining.

## 2. Papel no modpack

FTB Library sustenta vários mods FTB instalados e também concentra infraestrutura que anteriormente vivia em consumidores específicos. A linha 2101.1.x, por exemplo, moveu o carregamento de faces de entidades de FTB Chunks para a Library para reutilização por FTB Quests. Remover ou trocar a versão sem checar a matriz FTB pode produzir falhas de linkage, telas quebradas, packets incompatíveis ou comportamento client/server divergente.

## 3. Superfícies técnicas confirmadas no source auditado em 2101.1.35

A árvore source pinada contém, entre outras famílias:
- entrypoints/core: `FTBLibrary`, `FTBLibraryClient`, `FTBLibraryCommands`, `FTBLibraryCommon`;
- API pública client: `FTBLibraryClientApi`;
- API de cores: `RegisterCustomColorEvent`;
- API/sidebar: `SidebarButton`, `SidebarButtonCreatedEvent`, `ButtonOverlayRender`;
- configuração: `ConfigGroup`, `ConfigValue`, `BooleanConfig`, `ColorConfig`, `DoubleConfig`, `EnumConfig`, `FluidConfig`, `IntConfig`, `ItemStackConfig`, `ListConfig`, `LongConfig`, `NBTConfig`, `NameMap`, `ResourceConfigValue` e outras especializações;
- SNBT/config manager e serialization infrastructure;
- networking em `dev.ftb.mods.ftblibrary.net`;
- NBT editor e `NBTEditResponseHandlers`;
- seleção/representação de itens, fluids, entities, resources e imagens;
- GUI/panels/widgets/context menus/sidebar;
- math/utilities e classes de texto/cor;
- abstrações recentes `DocsMod` / `DocsModRegistry` para integração com mods de documentação;
- abstração de currency adicionada na linha 2101.1.x para provider externo, sem criar moeda própria.

A biblioteca registra alguns itens/creative tab internos via `ModItems.init()`, mas isso não a transforma em content mod de progressão; o papel dominante é infra/API.

## 4. Lifecycle real do entrypoint

`FTBLibrary` confirma o seguinte fluxo:
1. construtor obtém `ConfigManager`, executa `init()` e registra o client config;
2. startup/server configs de teste são registrados **somente em development mode**;
3. registra comandos por `CommandRegistrationEvent`;
4. chama `FTBLibraryNet.register()`;
5. registra listeners para `SERVER_STARTED`, `SERVER_STOPPED` e `PLAYER_JOIN`;
6. inicializa `ModItems`;
7. executa `FTBLibraryClient::init` apenas em `Env.CLIENT` via `EnvExecutor`;
8. registra `ftb:rainbow` por `RegisterCustomColorEvent`;
9. no lifecycle `SETUP`, coleta custom colors e as injeta em `ExtendableTextColor`.

Esse desenho torna ordem de init, side isolation e cleanup de server-global state contratos relevantes.

## 5. Server start / stop / player join

No `SERVER_STARTED`, a Library cria `KnownServerRegistries.server` a partir do servidor e registra handlers builtin do editor NBT. No `SERVER_STOPPED`, a referência é explicitamente zerada. Em `PLAYER_JOIN`, se `KnownServerRegistries.server` estiver disponível, o servidor envia `SyncKnownServerRegistriesPacket` ao jogador. O código trata a possibilidade de a referência estar `null`, citando caso upstream conhecido.
Consequências operacionais:
- não manter referência server-global depois de shutdown;
- integração que depende de registries conhecidos deve tolerar janela `null` durante lifecycle;
- reconnect/login precisa ressicronizar state por jogador;
- não assumir que cache client anterior continua válido após trocar de servidor.

## 6. Networking confirmado

`FTBLibraryNet.register()` registra explicitamente:

### S2C

- `EditConfigPacket`;
- `EditConfigChoicePacket`;
- `EditNBTPacket`;
- `SyncKnownServerRegistriesPacket`;
- `SyncConfigFromServerPacket`;
- `SidebarButtonVisibilityPacket`;
- `SyncGameStagesMessage`.

### C2S

- `EditNBTResponsePacket`;
- `SyncConfigToServerPacket`.

A Library fornece transporte/protocolo, mas autorização deve continuar server-authoritative. Em especial, edição de config/NBT recebida do cliente precisa passar pelos handlers e permissões previstos; não construir bridge que aplique payload client diretamente a state servidor.

## 7. Configuração

### Client config — `ftblibrary-client`

`FTBLibraryClientConfig` confirma:
- grupo `tooltips`:
	- `item_modname = false`;
	- `fluid_modname = true`;
	- `image_modname = true`;
	- `entity_modname = true`;
- `colorselector.recents` começa como array vazio;
- `sidebar.enabled = true`;
- `sidebar.position = TOP_LEFT`;
- mapa de botões da sidebar armazenado em `sidebar.buttons`;
- posições válidas: `TOP_LEFT`, `TOP_RIGHT`, `BOTTOM_LEFT`, `BOTTOM_RIGHT`.

O comentário upstream deixa `item_modname` false porque vários mods comuns já adicionam o nome do mod ao tooltip, evitando duplicação visual por default.

### Server/startup configs

As classes `FTBLibraryServerConfig` e `FTBLibraryStartupConfig` existem, mas estão explicitamente rotuladas **Testing only!** e o entrypoint só as registra em development mode. Elas contêm valores de teste `section1/section2` e não devem ser documentadas como gameplay config de produção do pack.

## 8. Sistema de configuração e SNBT

A família `ConfigValue`/`ConfigGroup` e especializações fornece modelo reutilizável para configs e telas. A 2101.1.34 adicionou variante de `ConfigValue#setCanEdit` com `BooleanSupplier`, permitindo editabilidade dinâmica da tela. A 2101.1.30 adicionou visualização read-only em `EditConfigScreen`, importante para permitir que jogadores não-admin vejam server config sem editar.
Para consumidores, respeitar distinção entre valor local, valor server-synced e permissão de edição. Não duplicar serialização manual se a Library já é o contrato do mod consumidor.

## 9. NBT editor

A Library possui infraestrutura de edição NBT e handlers de resposta. Mudanças upstream relevantes:
- **2101.1.27:** corrigiu `/ftblibrary nbtedit` enviando dados ao servidor duas vezes ao aplicar mudanças;
- **2101.1.25:** adicionou registro extensível `NBTEditResponseHandlers` e tornou `FTBLibraryCommands.InfoBuilder` público, permitindo que outros mods registrem handlers próprios;
- **2101.1.32:** corrigiu problemas de permissão em NBT editing.
- **2101.1.36:** corrige parte do tratamento de valores numéricos no NBT editor.

Esses históricos tornam double-send, autorização, numeric value handling e dispatch ao handler correto riscos concretos a validar.

## 10. UI, panels, sidebar e scrolling

FTB Library concentra componentes GUI reutilizados pelo ecossistema FTB. A linha atual inclui:
- sidebar configurável e packets de visibilidade;
- context menus e select screens;
- editor de config/NBT;
- seleção de resources/items/fluids/entities/images;
- multiline/text widgets;
- entity face loading;
- icon system e custom text colors.

Mudanças relevantes:
- 2101.1.34 reimplementou panel scrolling para multi-axis vanilla, inclusive X/Y simultâneo em trackpad;
- 2101.1.33 corrigiu pose-stack leak no sidebar renderer;
- 2101.1.30 corrigiu modal panels que não recebiam `tick()` via `BaseScreen`;
- 2101.1.29 corrigiu reflow de `TextField` considerando scaling;
- 2101.1.28 limpou layout de selector screens e migrou enum config de uma tela deprecated/buggy.

A 2101.1.36 adiciona ainda `CursorType.MOVE`, ampliando a superfície de cursor/UI compartilhada. Mods próprios não devem mixinar arbitrariamente esses mesmos componentes sem necessidade, pois a superfície é compartilhada por vários consumidores.

## 11. Keymaps — correção específica 2101.1.35

A release **2101.1.35** introduziu a correção, herdada pela versão instalada **2101.1.36**, para a checagem NeoForge de key modifiers em keybindings que não possuem modificador. Antes da correção, um binding sem modifier podia ser considerado ativo mesmo com uma modifier key pressionada, causando problema concreto em FTB Quests com **Tab / Shift-Tab**. A 2101.1.34 havia backportado a abstração de keymapping e suporte a modifiers.
No pack, qualquer conflito de tecla envolvendo FTB Quests, menus ou mods de input deve ser diagnosticado considerando essa camada antes de criar patch próprio.

## 12. Delta instalado 2101.1.36

A release **2101.1.36**, publicada para NeoForge 1.21.1 em 11/09/2026 e agora instalada no pack, adiciona `CursorType.MOVE` e corrige tratamento de valores numéricos no NBT editor. Esses dois pontos entram como comportamento atribuído ao runtime atual e regression gates de UI/NBT editing.

## 13. Registry sync, stages e extensões

A Library mantém `KnownServerRegistries` e sincroniza-os no login. A 2101.1.22 adicionou client sync para o provider builtin de gamestages (`EntityTagStageProvider`) e variante de `NetworkHelper#composite` com 9 argumentos para stream codecs. A mesma release adicionou `ftb:rainbow` e `RegisterCustomColorEvent`, confirmando extensibilidade explícita.
A existência de uma abstração não significa que o pack esteja usando determinado provider; registrar apenas integrações efetivamente configuradas.

## 14. Client / server

- **Client:** grande parte de GUI, panels, sidebar render, keymaps, icon/entity-face rendering, seleção visual e client config.
- **Server:** KnownServerRegistries source, handlers NBT/config, permissões, comandos e origem authoritative de state sincronizado.
- **Comum:** codecs, config model, math/text utilities, API contracts e networking registration.

O entrypoint usa `EnvExecutor` para isolar `FTBLibraryClient::init`, portanto consumers próprios devem manter o mesmo princípio e evitar referências eager a classes client no dedicated server.

## 15. Multiplayer

A Library sincroniza state de registries/config/stages por packets. Riscos multiplayer incluem:
- cliente com versão ABI diferente;
- packet/codec mismatch;
- edição de config/NBT concorrente ou sem permissão;
- double-submit de UI;
- cache de server registry herdado de conexão anterior;
- sidebar/config state local confundido com autoridade de servidor.

Em servidores com múltiplos jogadores, testar simultaneamente abertura/aplicação de telas e reconexão, não só singleplayer integrado.

## 16. Integrações concretas na modlist física

Os seguintes consumidores FTB estão fisicamente presentes no pack e devem ser tratados como matriz principal:
- **FTB Chunks 2101.1.22**;
- **FTB Quests 2101.1.36**;
- **FTB Teams 2101.1.11**;
- **FTB Ultimine 2101.1.15**;
- **FTB XMod Compat 21.1.11**.

Relações de versão confirmadas no changelog:
- FTB Library 2101.1.21 moveu entity face loading para Library e declara que essa release **não funciona** com FTB Quests anterior a 2101.1.16 ou FTB Chunks anterior a 2101.1.11;
- FTB Chunks 2101.1.21+ requer Library 2101.1.34+ por causa do novo panel scrolling; o pack usa Library 2101.1.36, portanto satisfaz essa relação documental.

Não inferir dependência direta de qualquer outro mod apenas por usar GUI semelhante.

## 17. Riscos técnicos

- **ABI/version skew:** biblioteca central pode quebrar vários consumidores de uma vez por método/classe ausente.
- **Packet/codec mismatch:** cliente e servidor divergentes podem falhar em sync/config/NBT/stages.
- **Double-send de edição:** bug historicamente real no NBT editor, corrigido em 2101.1.27.
- **Permissão de NBT/config:** bug real corrigido em 2101.1.32; não contornar autorização em integrations próprias.
- **Key modifier conflict:** bug real corrigido em 2101.1.35.
- **NBT numeric value handling:** corrigido em 2101.1.36; manter teste de valores inteiros/decimais e limites no editor.
- **Cursor/UI state:** `CursorType.MOVE` entra na 2101.1.36 e deve ser smoke-tested por consumidores de UI compartilhada.
- **Pose stack / GUI state leak:** sidebar renderer já teve leak corrigido em 2101.1.33.
- **Client classloading:** ampla superfície client exige side isolation.
- **Stale KnownServerRegistries:** referência global precisa ser criada/zerada no lifecycle e sincronizada a cada join.
- **Shared UI mixin collision:** patches de terceiros em `BaseScreen`, panels, selectors ou keymaps podem afetar diversos FTB mods simultaneamente.
- **Config ownership incorreto:** client config não deve autorizar mutação server-side.

## 18. Matriz de testes obrigatória

- [ ] Dedicated server boot com FTB Library 2101.1.36 + todos os consumidores FTB atuais.
- [ ] Server stop/restart; `KnownServerRegistries.server` não retém state stale.
- [ ] Login/relogin e troca entre servidores; registry sync refeito corretamente.
- [ ] Cliente/servidor com versões iguais; nenhum packet/codec mismatch.
- [ ] FTB Quests Tab e Shift-Tab após correção 2101.1.35, inclusive com outras modifier keys.
- [ ] Sidebar nos quatro cantos, enable/disable e visibilidade sincronizada.
- [ ] Trackpad/mouse scrolling em painéis X/Y e telas FTB Chunks/Quests.
- [ ] GUI scaling diferente: TextField/reflow/context menus sem clipping.
- [ ] Abrir e fechar repetidamente sidebar/modals; sem pose-stack leak ou widget sem tick.
- [ ] `ftblibrary-client` defaults e persistência de alterações.
- [ ] Jogador não-admin vê config read-only quando consumidor expõe essa capacidade, sem conseguir editar.
- [ ] NBT edit: uma aplicação gera exatamente um C2S response e exatamente uma mutação autorizada.
- [ ] NBT editor 2101.1.36: valores numéricos inteiros/decimais/limites são tratados sem regressão.
- [ ] UI compartilhada: `CursorType.MOVE` aparece/é consumido sem cursor stale ou conflito de estado.
- [ ] NBT edit simultâneo por dois jogadores sobre objetos diferentes e, em teste controlado, mesmo objeto.
- [ ] Consumer handlers via `NBTEditResponseHandlers` roteados ao alvo correto.
- [ ] Game-stage/config/registry sync após relog.
- [ ] Smoke test individual de FTB Chunks, Quests, Teams, Ultimine e XMod Compat após qualquer update de Library.

## 19. Evidências e limites

**Modlist física atual:** confirma JAR, mod id, versão e consumidores FTB instalados.
**Source primário auditado:** `FTBTeam/FTB-Library`, branch `1.21.1/main`, auditado detalhadamente na build 2101.1.35; não é usado para inventar mudanças além do delta oficial 2101.1.36.
**Classes auditadas:** `FTBLibrary`, `FTBLibraryNet`, `FTBLibraryClientConfig`, `FTBLibraryServerConfig`, `FTBLibraryStartupConfig` e árvore de source/API/config.
**Changelog oficial:** releases 2101.1.35, 2101.1.36 e 2101.1.x relevantes para keymaps, GUI, NBT editing, registry sync e compatibilidade com Chunks/Quests.
**Limite:** server/startup configs encontradas no source são marcadas `Testing only!` e só registradas em development mode; não foram promovidas a config operacional do pack.
**Nenhum teste de runtime foi executado nesta catalogação.** A matriz acima é plano de validação.

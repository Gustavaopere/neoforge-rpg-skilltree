# Iceberg

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8178bf48fbc6ffaf3320
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Iceberg
- **Arquivo JAR:** `Iceberg-1.21.1-neoforge-1.3.2.jar`
- **Versão 1.21.1:** 1.3.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca multiplataforma de events/helpers/utilities, config e client tooltip infrastructure para mods consumidores.
- **Dependências:** NeoForge 1.21.1; source 1.3.2 usa adapter NeoForge e common module. Dependência real deve ser avaliada pelos consumers físicos antes de qualquer remoção.
- **Sobreposição:** Library/API; não é substituto direto de mods de tooltip/UI. Remoção só após enumerar consumers e validar boot/linkage.
- **Compatibilidade/Riscos:** Riscos: ABI/linkage drift, platform adapter incorreto, client tooltip classloading em dedicated server, event duplication, config-spec incompatibility e login/network state incorreto em consumers.
- **Observações:** Source confirma arquitetura common + NeoForge/Forge/Fabric, IcebergClient para tooltip components e framework IcebergConfig. A lista completa de hard consumers não foi inferida sem metadata de cada JAR.
- **Procedência:** modlist.txt física atual de 09/09/2026 + CurseForge oficial Iceberg 1.3.2 NeoForge 1.21.1 + source oficial AHilyard/Iceberg branch 1.21.1-multi exatamente em 1.3.2.
- **Fonte:** https://github.com/AHilyard/Iceberg/tree/1.21.1-multi
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Iceberg 1.3.2 source-pinned; common/platform adapters, tooltip/config/client-server boundaries, lifecycle, consumers, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Iceberg-1.21.1-neoforge-1.3.2.jar`, mod id `iceberg`, versão `1.3.2`. O source oficial `AHilyard/Iceberg:1.21.1-multi` declara exatamente `modVersion=1.3.2`, Minecraft 1.21.1 e suporte NeoForge; esta ficha é source-pinned.

## 1. Papel e authority
Iceberg é biblioteca multiplataforma de eventos, helpers e utilities para mods consumidores. Não é owner de gameplay próprio dos consumers. Seu contrato é fornecer abstrações comuns e adapters por loader; tooltip, UI, config ou comportamento final continua pertencendo ao mod que usa a API.

## 2. Build e loader
A branch exata 1.21.1-multi declara Forge/Fabric/NeoForge e `neoforgeVersion=21.1.119` como build dependency. O pack roda NeoForge 21.1.248. A compatibilidade prática depende do range do artefato e consumers; atualização do loader deve ser validada por linkage/boot, não inferida só pelo número de build.

## 3. Arquitetura common + platform
O source separa módulo `common` e adapters `neoforge`, `forge` e `fabric`. `Iceberg.java` define o core comum; `IcebergNeoForge` integra os event buses do NeoForge. Isso reduz duplicação entre loaders, mas torna platform bridge e common API duas superfícies de ABI distintas.

## 4. Client utilities
`IcebergClient` trabalha com ItemStack e TooltipComponent, confirmando infraestrutura client de tooltips/render presentation. Consumers podem depender de eventos/helpers para montar componentes customizados. Uma falha nessa camada deve afetar apresentação, não transferir authority de item stats para o cliente.

## 5. Config framework
O source contém `IcebergConfig` e abstrações de config spec. Consumers podem registrar configs sobre essa infraestrutura. Defaults/chaves pertencem a cada consumer; Iceberg não deve ser tratado como arquivo de configuração único do pack. Reload de config precisa respeitar side e lifecycle suportados.

## 6. Eventos e lifecycle
A implementação de plataforma registra event handlers no startup e inclui hooks de client/server. O lifecycle crítico é mod construction, registration, client initialization, player login e config loading/reloading. Atualização da biblioteca pode quebrar consumers em boot mesmo sem alterar world data.

## 7. Client / server
Iceberg possui código explicitamente client e server/platform. Consumers devem isolar classloading: tooltip/render classes não podem ser alcançadas no dedicated server. Hooks de login/server não devem depender de `Minecraft` client. Dedicated-server boot é regression gate obrigatório.

## 8. Networking/login boundaries
O source possui tratamento de eventos de conexão/login nas plataformas. Qualquer state enviado/sincronizado por consumer precisa validar versão e player correto. Biblioteca de transporte/evento não confere authority ao cliente para alterar state de gameplay.

## 9. Consumers no pack
O pack contém vários mods do ecossistema de UI/tooltip do autor/adjacentes, incluindo **Advancement Plaques 1.6.8**. Porém esta ficha não declara uma lista exaustiva de dependentes sem ler metadata individual de cada JAR. Antes de remover Iceberg, enumerar consumers pelo loader/log é obrigatório.

## 10. Riscos técnicos
- remover Iceberg mantendo consumer obrigatório;
- `NoClassDefFoundError`/`NoSuchMethodError` por ABI drift;
- platform adapter carregar classes do loader errado;
- client tooltip helper ser carregado no servidor;
- event handler duplicado após update/reload;
- config spec de consumer ficar incompatível;
- login/network hook operar state de player errado;
- atribuir bug do consumer à library sem stack trace causal.

## 11. Matriz de testes obrigatória
- [ ] Dedicated server boot com Iceberg 1.3.2 e consumers atuais.
- [ ] Client boot/join sem linkage/classloading errors.
- [ ] Tooltips/components de consumers renderizam após F3+T/resource reload.
- [ ] Configs de consumers carregam e persistem sem parse errors.
- [ ] Login/reconnect não duplica eventos ou state derivado.
- [ ] Remoção experimental em cópia identifica todos os hard consumers antes de qualquer decisão.
- [ ] Atualização futura do Iceberg é testada contra todos os consumers, não isoladamente.

## 12. Evidências e limites
- **Modlist física:** `Iceberg-1.21.1-neoforge-1.3.2.jar`.
- **CurseForge:** release NeoForge 1.3.2 para Minecraft 1.21.1.
- **Source oficial:** branch `1.21.1-multi`, versão exata, common/platform architecture, client helpers e config framework.
- **Limite:** consumers obrigatórios completos não foram inferidos por nome; devem ser enumerados de metadata/log antes de remoção.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

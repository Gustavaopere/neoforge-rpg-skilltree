# Fzzy Config

> **Reauditoria física — 18/09/2026.** Versão catalogada atual: `0.7.7+1.21+neoforge`. O conteúdo abaixo preserva a página Notion reconciliada; a URL da própria página Notion foi deliberadamente omitida.

## Propriedades do registro

- **Mod:** Fzzy Config
- **Arquivo JAR:** fzzy_config-0.7.7+1.21+neoforge.jar
- **Versão 1.21.1:** 0.7.7+1.21+neoforge
- **Categoria:** Biblioteca
- **Função:** Framework de configuração tipada: registro/serialização, validation, GUI/widgets, scopes e sincronização server↔client para mods consumidores.
- **Dependências:** Framework Client & Server. Os JarJars Jankson/TOML-KT foram comprovados no host 0.7.6; o interior do novo 0.7.7 não foi re-inspecionado nesta passagem.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** 0.7.7 remove APIs deprecated/ValidatedEnumMap e altera Registrar generics. Riscos: consumers compilados contra APIs removidas, client sobrescrevendo server authority, validators/sync, schema mismatch, stale UI e packet registration em NeoForge.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/fzzy-config
- **Procedência:** modlist física atual + CurseForge oficial Fzzy Config 0.7.7+1.21+neoforge, file ID 8883390, 15/09/2026. JarJars internos do novo host não foram re-inspecionados.
- **Observações:** Runtime físico `fzzy_config-0.7.7+1.21+neoforge.jar`. 0.7.7 inclui breaking removals, `PlatformApi.platform()`, Registrar retornando `RegistryEntry<F>` e múltiplos fixes de validation/NeoForge/config GUI.
- **Atualização/Status:** READITADO EM 18/09/2026 — runtime físico atualizado de 0.7.6 para 0.7.7+1.21+neoforge; breaking changes, Registrar generics e fixes NeoForge 0.7.7 incorporados.
- **Decisão:** Sem decisão
- **Histórico da decisão:** 
- **Sobreposição:** Não é authority do significado das configs dos consumidores. Fzzy Config fornece schema/validation/UI/sync; cada consumer continua dono da semântica de suas opções. Evitar serialização ou packets paralelos para a mesma config.
- **Data da última decisão:** 2026-08-26

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `fzzy_config-0.7.7+1.21+neoforge.jar`, mod id `fzzy_config`, upstream core `0.7.7`. O pin `621f8ffe...` permanece como evidência histórica da auditoria 0.7.6, mas deixou de ser authority da versão instalada. Fzzy Config é framework de **configuração, validação, GUI e sync**, não content mod.
## 1. Identidade e versão
- **Mod:** Fzzy Config.
- **JAR físico:** `fzzy_config-0.7.7+1.21+neoforge.jar`.
- **Mod id:** `fzzy_config`.
- **Versão instalada:** `0.7.7+1.21+neoforge` / upstream core `0.7.7`.
- **Minecraft:** 1.21 e 1.21.1 na linha pinada.
- **Source pin exato:** commit `621f8ffe6aa1aa3d48a485156a73b4b592593d66` da branch NeoForge 1.21.
## 2. Papel no modpack
Fzzy Config fornece uma camada reutilizável para mods declararem configs tipadas, validarem valores, gerarem telas de edição, sincronizarem dados entre servidor e cliente e definirem escopo/autoridade de cada opção. Consumers devem usar essa infraestrutura como contrato de config, sem duplicar arquivos, packets ou validação paralela.
## 3. Registro e API de config
A documentação do commit pinado descreve registro de configurações por API própria. O framework lida com descoberta/serialização de campos e construção de GUI. A existência de uma config class em um consumer não significa que todo valor possa ser editado em qualquer side: escopo e permissão continuam parte do contrato.
## 4. Validação
Validation é uma função central do projeto. Valores recebidos de arquivo, GUI ou rede devem passar pelos validators definidos pelo consumer/framework antes de serem aplicados. Integrações próprias não devem escrever valores diretamente nos campos/arquivos para contornar range, enum, predicate ou constraints de coleção.
## 5. GUI e widgets
Fzzy Config possui UI built-in para edição e também API de GUI para custom screens/widgets. O client pode representar opções e staging de mudanças, mas não pode conceder autoridade sobre configs server-owned. Fechar/cancelar/aplicar a tela deve produzir no máximo uma mutação efetiva por ação.
## 6. Server sync e scope
A documentação oficial confirma sincronização server→client e sistema de scopes. O ponto operacional é separar:
- config local/client, que pode variar por usuário;
- config authoritative/server, que deve ser originada/validada pelo servidor;
- valores que precisam ser sincronizados para apresentação ou comportamento client.
Não tratar o arquivo local do cliente como fonte de verdade para regra do servidor.
## 7. Dependências embarcadas
A modlist física mostra dois jars internos do host:
- `jankson-1.2.3.jar`;
- `tomlkt-jvm-0.3.7.jar`.
Eles pertencem ao runtime do Fzzy Config e **não são entradas top-level** da modlist. Não criar páginas independentes apenas porque aparecem em `META-INF/jars`/nesting.
## 8. Loader/version pin
O commit exato declara `mcVersions=1.21,1.21.1`, `neoforgeVersion=21.0.116-beta`, além de referências Fabric/Forge usadas pelo projeto multiplataforma. Esses valores são baselines de build do source, não exigência automática de que o pack esteja exatamente nessas versões. A autoridade de runtime continua sendo o JAR físico.
## 9. Client / server
- **Client:** telas, widgets, interação local e apresentação de config.
- **Server/common:** parsing/validation de valores server-owned, sync e regras de scope.
Consumers devem manter side isolation e evitar carregar GUI classes em dedicated server.
## 10. Lifecycle
Validar inicialização de configs, leitura de arquivo, primeira criação/defaults, edição/aplicação, reconnect, mudança de servidor, config reload quando suportado e update de consumer. Caches/GUI staged state não devem sobreviver indevidamente a uma nova sessão/servidor.
## 11. Multiplayer
O servidor precisa prevalecer em opções sincronizadas/server-scoped. Dois clientes editando superfícies autorizadas não podem produzir race que deixe valor parcialmente aplicado. Version mismatch de consumer/config schema também pode gerar decode/sync failure e deve falhar de modo claro.
## 12. Integrações concretas e ownership
Fzzy Config só deve ser relacionado a consumers cuja metadata/source confirme dependência. Sua presença não torna todas as configs do pack parte dele. Cada mod consumidor continua authority do significado de suas opções; Fzzy Config fornece serialização, validation, UI e transport/scope.
## 13. Riscos técnicos
- source HEAD futuro ser confundido com a 0.7.6 instalada;
- client config sobrescrever server authority;
- bypass de validators por mutação direta;
- double-submit de GUI/sync;
- schema/version mismatch entre cliente e servidor;
- stale UI/cache após reconnect;
- classloading de GUI no dedicated server;
- nested dependencies catalogadas erroneamente como mods top-level;
- consumers dependerem de API adicionada somente em 0.7.7+.
## 14. Matriz de testes obrigatória
- [ ] Dedicated server boot com Fzzy Config 0.7.7 e consumers atuais.
- [ ] Client conecta e recebe configs server-scoped corretamente.
- [ ] Client-only config pode variar sem alterar regra server-side.
- [ ] Validators rejeitam valores inválidos de GUI/arquivo/sync.
- [ ] Apply/cancel da GUI gera a mutação esperada exatamente uma vez.
- [ ] Reconnect para outro servidor não mantém state sincronizado antigo.
- [ ] Update/reload de consumer não deixa schema/cache stale.
- [ ] Consumers atuais não chamam APIs removidas em 0.7.7 nem dependem de `ValidatedEnumMap`.
- [ ] Jankson/TOML-KT internos carregam apenas como dependências do host.
## 15. Evidências e limites
**Source histórico da 0.7.6:** `fzzyhmstrs/fconfig`, commit `621f8ffe...`. A versão física atual é 0.7.7 e usa a publicação oficial 0.7.7 como authority de release.
**Documentação oficial do mesmo commit:** config registration, GUI, server sync, scopes e validation.
**Modlist física:** confirma o JAR NeoForge e dependências internas Jankson/TOML-KT.
**Limite:** não foram atribuídos consumers específicos sem confirmação individual de dependência.
**Nenhum teste de runtime foi executado nesta catalogação.**
## 16. Atualização instalada — 0.7.7
A release 0.7.7 para NeoForge 1.21/1.21.1 introduz mudanças que podem afetar consumers:
- remove `ValidatedEnumMap` e métodos/propriedades previamente deprecated para remoção em 0.7.0;
- adiciona `PlatformApi.platform()` com enum de plataforma e helper `forgelike()`;
- melhora generics do Registrar para retornar `RegistryEntry<F>` do tipo específico registrado;
- corrige possível crash em `ValidatedIdentifier.ofRegistryKey`;
- corrige popups vazios em Neo, tradução de `ValidatedSet`, decoder exception em configs muito grandes, `registerAndLoadNoGuiConfig`, datagen translations, alguns registrations de packet em Neo e enums não-CAPS em `ValidatedEnum`.
Regression gates:
- [ ] consumidores compilados contra APIs removidas falham de forma identificável, sem silent corruption;
- [ ] configs grandes em Neo decodificam sem exception;
- [ ] configs `NoGui` permanecem ocultas;
- [ ] packet registration e server↔client sync permanecem estáveis.

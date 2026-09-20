# MezzConfig

## Propriedades do registro

- **Mod:** MezzConfig
- **Arquivo JAR:** mezz_config-1.21.1-neoforge-0.5.9.jar
- **Versão 1.21.1:** 0.5.9
- **Tipo de conteúdo:** Mod
- **Categoria:** Biblioteca
- **Função:** Biblioteca tipada de configuração para mods: preferências client-side, configurações client-side por mundo, validação/defaults/recovery, listeners/migrações e configurações server-authoritative sincronizadas aos clientes.
- **Dependências:** Minecraft 1.21.1, NeoForge e Java 21 para o runtime top-level atual. Recursos de rede da library são opcionais; a documentação oficial permite cliente com MezzConfig conectando a servidor sem MezzConfig.
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Version drift entre o top-level 0.5.9 e a cópia JarJar 0.5.6 dentro do JEI; upstream 0.5.11 ainda não instalado; watcher/reload duplicado, state stale em world/reconnect, authority client/server e dependência indevida de packages internos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mezzconfig
- **Procedência:** modlist física atual (MezzConfig top-level 0.5.9; JEI 19.56.0.440 com MezzConfig 0.5.6 via JarJar) + CurseForge oficial 0.5.9/0.5.11 + source oficial mezz/MezzConfig.
- **Observações:** O catálogo usa o top-level 0.5.9. A cópia 0.5.6 em META-INF/jarjar do JEI não é entrada top-level separada. Upstream 0.5.11 para NeoForge 1.21.1 existe desde 19/09/2026, mas não substitui o version pin físico até a modlist ser atualizada.
- **Atualização/Status:** CATALOGADO EM 20/09/2026 — runtime físico 0.5.9 confirmado. O changelog 0.5.9 corrige watcher events disparados pelos próprios saves. Upstream 0.5.11 é update candidate, não runtime instalado.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Entrada criada em 20/09/2026 porque MezzConfig passou a existir como mod top-level físico. Nenhuma decisão curatorial adicional foi inferida apenas da presença da biblioteca.
- **Sobreposição:** Não substitui a authority dos consumers. MezzConfig fornece plumbing de configuração/validação/sync; cada mod consumidor continua definindo significado, permissões e efeitos de seus valores.
- **Data da última decisão:** 2026-09-20

> 🧩 **RUNTIME FÍSICO CONFIRMADO:** `mezz_config-1.21.1-neoforge-0.5.9.jar`, mod id `mezz_config`, versão `0.5.9`, como mod top-level na modlist atual. O JEI `19.56.0.440` também carrega `/META-INF/jarjar/mezz_config-1.21.1-neoforge-0.5.6.jar`; essa cópia embarcada não é uma segunda entrada top-level e não altera a versão catalogada. O upstream já publicou `0.5.11` para NeoForge 1.21.1 em 19/09/2026, mas essa build **não está presente** na modlist física atual.

## 1. Papel e authority
MezzConfig é uma **biblioteca tipada de configuração para mods Minecraft**. Ela fornece uma API comum para preferências client-side, configurações client-side por mundo e configurações server-authoritative sincronizadas aos clientes conectados.

A library é authority da infraestrutura de configuração — serialização, validação, defaults, recuperação de arquivo, listeners, migrações e sincronização oferecida pela API. O **mod consumidor continua authority do significado e das regras de gameplay** de cada opção. Um valor existir em MezzConfig não transfere para a biblioteca ownership sobre a mecânica configurada.

## 2. Identidade física e version pin
Runtime top-level atual:
- JAR: `mezz_config-1.21.1-neoforge-0.5.9.jar`;
- mod id: `mezz_config`;
- runtime name: `MezzConfig`;
- versão: `0.5.9`;
- Minecraft: `1.21.1`;
- loader: NeoForge;
- Java para a linha 1.21.1: 21.

A fonte oficial declara suporte da linha 1.21.1 a Fabric e NeoForge com Java 21.

## 3. JarJar dentro do JEI
A mesma modlist contém, dentro de `jei-1.21.1-neoforge-19.56.0.440.jar`, a dependência embarcada:

`/META-INF/jarjar/mezz_config-1.21.1-neoforge-0.5.6.jar`

Pelo protocolo do catálogo, dependências em `META-INF/jarjar/` não viram automaticamente mods top-level. Portanto:
- a entrada física catalogada permanece `0.5.9`;
- `0.5.6` deve ser tratada como cópia embarcada do host JEI;
- não criar um segundo dossiê para a cópia JarJar;
- em diagnóstico de classloading/version selection, verificar qual artefato efetivamente vence no runtime, sem inferir conflito apenas pela coexistência.

## 4. Modelo de configuração
A documentação oficial descreve:
- valores tipados com validação;
- defaults;
- recuperação automática de arquivo;
- uma API pública compartilhada entre loaders suportados;
- configurações pertencentes ao servidor com sincronização **one-way** para clientes;
- updates atômicos;
- change listeners;
- requisitos de restart;
- ferramentas de migração;
- ordenação persistente definida pelo usuário para valores descobertos em runtime.

A API pública fica sob `net.mezzdev.config.api`; outros packages são tratados pelo projeto como internos. Compat externa deve evitar depender de internals.

## 5. Client/server e rede
Os recursos de rede do MezzConfig são opcionais. A documentação oficial afirma que um cliente com MezzConfig pode conectar a servidor vanilla ou a servidor sem MezzConfig.

Quando um valor é server-owned:
1. o servidor/provider define o valor autoritativo;
2. a library realiza a sincronização prevista pelo contrato;
3. o cliente consome o snapshot sincronizado;
4. UI/client cache não deve ser tratado como fonte de verdade para gameplay server-side.

Não criar sync paralelo para o mesmo valor sem necessidade, pois isso aumenta risco de state divergente e dupla aplicação.

## 6. Lifecycle
Superfícies que precisam permanecer corretas:
- criação inicial do arquivo;
- leitura de arquivo existente;
- save de alteração;
- file watcher/reload;
- world load/unload;
- troca de mundo;
- login/reconnect;
- conexão a servidor sem MezzConfig;
- sincronização de server-owned settings;
- aplicação de migration;
- opção marcada como restart-required;
- falha de parse/valor inválido com retorno seguro a defaults/recovery.

Listeners devem observar transições reais, sem gerar loops causados pelo próprio processo de persistência.

## 7. Alteração instalada — 0.5.9
O changelog oficial da build física `0.5.9` registra uma correção específica:

- **ignorar watcher events gerados por saves da própria configuração**.

Isso é operacionalmente relevante porque reduz o risco de um save disparar um ciclo de watcher/reload como se fosse uma edição externa.

Regression gates para 0.5.9:
- [ ] salvar uma configuração não gera reload/listener duplicado;
- [ ] editar externamente o arquivo continua sendo detectado quando o contrato prevê watcher;
- [ ] reload preserva validação e defaults;
- [ ] listener do consumer executa uma única vez por mudança efetiva.

## 8. Drift upstream — 0.5.11
Em 19/09/2026 o upstream publicou `0.5.11` para NeoForge 1.21.1. A modlist física usada por este catálogo ainda contém `0.5.9`.

Portanto `0.5.11` é **update candidate**, não runtime confirmado. Não atribuir ao pack atual mudanças de 0.5.10/0.5.11 até o JAR físico ser atualizado e a modlist confirmar a nova build.

## 9. Integração com consumers
MezzConfig é infraestrutura. Consumers podem:
- registrar schemas/valores;
- reagir a mudanças;
- usar migrações;
- consumir configuração por mundo;
- usar configuração autoritativa do servidor.

Cada consumer continua responsável por validar seus próprios invariants. MezzConfig validar tipo/range não substitui checks de permissão, ownership, existência de registry object ou regras específicas de gameplay.

## 10. Multiplayer e concorrência
Em multiplayer, separar claramente:
- preferência puramente local;
- configuração local por mundo;
- configuração server-owned.

Riscos:
1. cliente agir sobre valor local quando o contrato exige valor do servidor;
2. listener executar antes de o contexto/world estar pronto;
3. reconnect reutilizar snapshot stale;
4. dois caminhos de sync competirem pelo mesmo state;
5. update não atômico ser observado parcialmente pelo consumer.

A própria library oferece updates atômicos; integrations devem preservar esse contrato em vez de decompor uma mudança lógica em mutações independentes sem necessidade.

## 11. Migrações e compatibilidade de API
Config persistida sobrevive a atualizações de mods, por isso migrations são parte do contrato operacional.

Ao atualizar MezzConfig ou um consumer:
- validar schema antigo → novo;
- preservar valores reconhecidos;
- aplicar default apenas quando necessário;
- não reinterpretar silenciosamente valores com semântica diferente;
- testar downgrade somente quando explicitamente suportado;
- evitar classes fora de `net.mezzdev.config.api`, pois são internas e podem mudar sem o mesmo compromisso de compatibilidade.

## 12. Riscos
1. **Version drift:** top-level 0.5.9 coexistir com cópia JarJar 0.5.6 exige atenção em diagnóstico de classloading.
2. **Upstream drift:** 0.5.11 já existe, mas ainda não é runtime do pack.
3. **Watcher loop/duplicação:** regression gate específico da correção 0.5.9.
4. **State stale:** troca de mundo/reconnect pode expor snapshot antigo se consumer mantiver cache próprio incorretamente.
5. **Authority inversion:** client config substituir indevidamente server-owned setting.
6. **API internals:** consumer depender de package interno.
7. **Migration loss:** atualização de schema perder ou reinterpretar valor persistido.
8. **Restart contract:** consumer assumir hot reload para opção que exige reinício.

## 13. Matriz de testes
- [ ] Dedicated server boot com o runtime top-level 0.5.9.
- [ ] Client boot e carregamento de configurações válidas.
- [ ] Arquivo ausente → criação/defaults.
- [ ] Valor inválido → validation/recovery conforme contrato.
- [ ] Save próprio → nenhum watcher loop ou listener duplicado.
- [ ] Edição externa → mudança observada uma vez quando suportada.
- [ ] Login, logout e reconnect.
- [ ] Troca de mundo e configuração per-world.
- [ ] Cliente com MezzConfig conectando a servidor sem MezzConfig.
- [ ] Server-owned setting sincronizando em uma direção para clientes.
- [ ] Restart-required setting respeitando lifecycle.
- [ ] Migração de schema preservando dados válidos.
- [ ] Consumer usando apenas API pública relevante.
- [ ] Atualização futura para 0.5.11: repetir boot, reload, sync e migration antes de alterar o version pin do catálogo.

Nenhum teste de gameplay/runtime foi marcado como executado por esta auditoria documental.

## 14. Evidências e limite
Evidências usadas:
- modlist física atual: top-level `mezz_config-1.21.1-neoforge-0.5.9.jar`;
- modlist física atual: JEI 19.56.0.440 contendo MezzConfig 0.5.6 via JarJar;
- CurseForge oficial: build `0.5.9` para NeoForge 1.21.1 e changelog do watcher;
- CurseForge oficial: `0.5.11` para NeoForge 1.21.1 publicada em 19/09/2026;
- source oficial `mezz/MezzConfig`: modelo de configuração, API pública, sync e loaders/Java suportados.

Fontes externas:
- https://www.curseforge.com/minecraft/mc-mods/mezzconfig
- https://github.com/mezz/MezzConfig

> 🔒 Boundary canônico: **MezzConfig = infraestrutura de configuração/validação/sync; o mod consumidor = significado da opção e autoridade da mecânica configurada**.

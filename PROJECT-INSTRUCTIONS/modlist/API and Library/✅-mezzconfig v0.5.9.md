# MezzConfig

## Propriedades do registro

- **Mod:** MezzConfig
- **Arquivo JAR:** mezz_config-1.21.1-neoforge-0.5.9.jar
- **Versão 1.21.1:** 0.5.9
- **Categoria:** Biblioteca
- **Tipo de conteúdo:** Mod
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mezzconfig
- **Função:** Biblioteca tipada de configuração para mods: preferências client-side, configurações client-side por mundo, validação/defaults/recovery, listeners/migrações e configurações server-authoritative sincronizadas aos clientes.
- **Dependências:** Minecraft 1.21.1, NeoForge e Java 21 para o runtime top-level atual. Recursos de rede da library são opcionais; a documentação oficial permite cliente com MezzConfig conectando a servidor sem MezzConfig.
- **Compatibilidade/Riscos:** Version drift entre o top-level 0.5.9 e a cópia JarJar 0.5.6 dentro do JEI; upstream 0.6.7 ainda não instalado; watcher/reload duplicado, state stale em world/reconnect, authority client/server, migrations com valores rejeitados, JarJar resolution e dependência indevida de packages internos.
- **Sobreposição:** Não substitui a authority dos consumers. MezzConfig fornece plumbing de configuração/validação/sync; cada mod consumidor continua definindo significado, permissões e efeitos de seus valores.
- **Observações:** O catálogo usa o top-level 0.5.9. A cópia 0.5.6 em META-INF/jarjar do JEI não é entrada top-level separada. O upstream avançou até 0.6.7 para NeoForge 1.21.1 em 03/10/2026, mas nenhuma dessas builds substitui o version pin físico até a modlist/JAR confirmar a atualização.
- **Procedência:** modlist física atual (MezzConfig top-level 0.5.9; JEI 19.56.0.440 com MezzConfig 0.5.6 via JarJar) + CurseForge oficial 0.5.9→0.6.7 + source oficial mezz/MezzConfig e histórico multiversion.
- **Atualização/Status:** AUDITORIA INCREMENTAL DOCUMENTAL 17/11/2026 — o baseline físico top-level continua 0.5.9, com JEI 19.56.0.440 embarcando MezzConfig 0.5.6 via JarJar. Histórico prévio 0.5.10→0.6.7 preservado e versões posteriores 0.6.8, 0.6.9, 0.6.10, 0.6.11, 0.7.0 e 0.7.1 verificadas no changelog oficial cumulativo e documentadas na seção 15. 0.7.1 Beta para NeoForge 1.21.1 é somente alvo upstream; promoção física/compatibilidade JEI não homologadas.
- **Histórico da decisão:** Entrada criada em 20/09/2026 porque MezzConfig passou a existir como mod top-level físico. Nenhuma decisão curatorial adicional foi inferida apenas da presença da biblioteca.
- **Data da última decisão:** 2026-09-20

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #394: JAR `mezz_config-1.21.1-neoforge-0.5.9.jar`, mod id `mezz_config`, runtime `0.5.9`, SHA-1 `49225b1f619f82d3530d00b8120ac7db166e9697`.

<callout icon="🧩" color="blue_bg">
	**RUNTIME FÍSICO CONFIRMADO:** `mezz_config-1.21.1-neoforge-0.5.9.jar`, mod id `mezz_config`, versão `0.5.9`, como mod top-level na modlist atual. O JEI `19.56.0.440` também carrega `/META-INF/jarjar/mezz_config-1.21.1-neoforge-0.5.6.jar`; essa cópia embarcada não é uma segunda entrada top-level e não altera a versão catalogada. O upstream já publicou `0.6.7` para NeoForge 1.21.1 em 03/10/2026, mas essa build **não está presente** na modlist física atual.
</callout>
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
## 8. Atualizações upstream — 0.5.10 → 0.6.7 — não instaladas
A modlist física usada por este catálogo continua em **0.5.9**. O CurseForge publicou para **NeoForge 1.21.1** uma sequência posterior que chegou a **0.6.7 em 03/10/2026**. Foi revisado o histórico intermediário exposto pelos changelogs oficiais entre 0.5.9 e 0.6.7; alguns headings de versão aparecem de forma cumulativa no changelog de um artefato posterior e não são tratados aqui como prova de que existiu um arquivo 1.21.1 separado para cada heading. Somente deltas com impacto técnico para consumers/configuração são promovidos a requisitos operacionais abaixo.

| Release | Delta relevante para o catálogo |
|---|---|
| **0.5.10** | Otimiza leituras de configuração: cacheia snapshots ordenados e evita snapshots quando o conteúdo lido não mudou; também inicia o suporte multiversion. |
| **0.5.11** | Adia validação da API publicada e corrige parsing do manifest do loader Jenkins; impacto principal é build/distribuição, sem mudança de authority do runtime documentada aqui. |
| **0.5.12** | Restaura suporte Forge até 1.21.1 e adiciona metadata/icon do NeoForge; demais deltas publicados são predominantemente CI/documentação/localização. |
| **0.6.0** | **Muda a API de migrations para permitir valores rejeitados**, relevante para consumers que migram schemas e dependem da semântica de validação/recovery. |
| **0.6.1** | Corrige tracking de commits usado por notificações de release; mudança de infraestrutura de publicação. |
| **0.6.2** | Ignora baselines inválidos de release notification; mudança de infraestrutura de publicação. |
| **0.6.3** | Serializa validação de releases multiversion; mudança de infraestrutura de build/release. |
| **0.6.4** | **Mantém client settings já carregados quando o refresh de startup falha** e **evita gravar o arquivo quando o conteúdo serializado é idêntico**. São deltas relevantes para recovery, estabilidade e redução de writes/watch events. |
| **0.6.5** | Separa outputs de compiler/resources no Forge; mudança de build/packaging. |
| **0.6.6** | **Corrige a resolução JarJar do MezzConfig standalone** (issue #2), diretamente relevante para ambientes onde a biblioteca aparece top-level e/ou embarcada por consumers. |
| **0.6.7** | **Separa schema definitions dos runtime schemas** e corrige o issue #4 para **evitar path checks em leituras de configuração cujo conteúdo não mudou**. O delta é relevante para a arquitetura de schema/migration/server runtime e reduz trabalho desnecessário no hot path de leitura. |

Consequências para este pack:
- `0.6.7` é **update candidate**, não runtime confirmado;
- não atribuir ao runtime 0.5.9 a nova semântica de migration/recovery/JarJar;
- numa promoção, validar migration de valores aceitos/rejeitados, falha de startup refresh, ausência de writes idênticos desnecessários e seleção/resolução da library quando coexistem o top-level e cópias JarJar;
- a cópia `0.5.6` embarcada no JEI atual continua sendo evidência física separada do top-level 0.5.9; sua coexistência deve ser reavaliada após qualquer atualização do JEI ou do MezzConfig.
### Nota de source pós-release — 03/10/2026

O source oficial já recebeu um commit de **bump para 0.6.8** depois da publicação de 0.6.7. Nesta auditoria, porém, **0.6.8 ainda não aparece como artefato CurseForge 1.21.1**; portanto não é tratado como release distribuída nem como update candidate instalado. O teto público usado aqui permanece 0.6.7.

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
2. **Upstream drift:** 0.6.7 já existe, mas ainda não é runtime do pack; 0.6.x inclui mudanças em migrations, recovery e JarJar resolution que exigem regressão antes de promoção.
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
- [ ] Atualização futura para 0.6.7: repetir boot, reload, sync e migration; cobrir migration com valores rejeitados, falha de startup refresh, writes idênticos e JarJar resolution antes de alterar o version pin do catálogo.
Nenhum teste de gameplay/runtime foi marcado como executado por esta auditoria documental.
## 14. Evidências e limite
Evidências usadas:
- modlist física atual: top-level `mezz_config-1.21.1-neoforge-0.5.9.jar`;
- modlist física atual: JEI 19.56.0.440 contendo MezzConfig 0.5.6 via JarJar;
- CurseForge oficial: build `0.5.9` para NeoForge 1.21.1 e changelog do watcher;
- CurseForge oficial: releases NeoForge 1.21.1 posteriores a 0.5.9 até `0.6.7`, publicada em 03/10/2026;
- source oficial `mezz/MezzConfig`: histórico multiversion 0.5.10→0.6.7, incluindo migration API, recovery/write behavior e JarJar resolution;
- source oficial `mezz/MezzConfig`: modelo de configuração, API pública, sync e loaders/Java suportados.
Fontes externas:
- [https://www.curseforge.com/minecraft/mc-mods/mezzconfig](https://www.curseforge.com/minecraft/mc-mods/mezzconfig)
- [https://github.com/mezz/MezzConfig](https://github.com/mezz/MezzConfig)
<callout icon="🔒" color="gray_bg">
	Boundary canônico: **MezzConfig = infraestrutura de configuração/validação/sync; o mod consumidor = significado da opção e autoridade da mecânica configurada**.
</callout>

## 15. Auditoria incremental 0.6.8 → 0.7.1 — não instaladas (17/11/2026)

**Autoridade física preservada:** `mezz_config-1.21.1-neoforge-0.5.9.jar` (top-level 0.5.9). A seção 8 já documenta individualmente 0.5.10, 0.5.11, 0.5.12, 0.6.0, 0.6.1, 0.6.2, 0.6.3, 0.6.4, 0.6.5, 0.6.6 e 0.6.7. O cabeçalho e a nota de 03/10 dessa seção descrevem **aquele snapshot de pesquisa**, não o estado do upstream hoje. A publicação oficial [0.7.1 Beta para NeoForge 1.21.1, CurseForge file 9527937](https://www.curseforge.com/minecraft/mc-mods/mezzconfig/files/9527937) é datada de **09/11/2026**, usa `mezz_config-1.21.1-neoforge-0.7.1-standalone.jar` e publica um changelog cumulativo com os seis deltas subsequentes:

| Versão intermediária | Mudança publicada pelo autor | Relevância para o dossier |
|---|---|---|
| **0.6.8** | Separa estados de gravação **staged/committed**; acrescenta retry em falhas de file sharing no Windows; corrige testes de paths e replacement e quoting de argumentos Windows CI. | **Relevante** para atomicidade/recovery e configuração no Windows; alterações de testes/CI não justificam novas mecânicas. |
| **0.6.9** | Usa **AtomicFile** para operações de configuração; dispara listeners **fora dos locks de schema**; inclui tradução chinesa simplificada; bump de versão. | **Relevante** para atomicidade, potencial contenção/deadlock e ordering de callbacks. Tradução/bump não mudam authority da configuração. |
| **0.6.10** | Exposição de **immutable sorting snapshots**. | **Relevante para consumers** que leem ordenação dinâmica: snapshot imutável; não inferir mutabilidade ou callbacks além do contrato publicado. |
| **0.6.11** | Exposição das **localization keys** de descrição de configurações. | **Relevante** para GUI, tradução e consumers que exibem descrições; não implica atualização automática de resource packs. |
| **0.7.0** | Exposição de tipos de valor estáveis para **config serializers**. | **Relevante para API pública e serializers de consumers**; exige validação de assinaturas reais ao migrar código. |
| **0.7.1** | Resolve **API baselines a partir de artefatos já publicados** e realiza bump para 0.7.1. | Predominantemente **build/publicação**; não há novo comportamento de gameplay comprovado nessa etapa isolada. |

**Aplicação ao pack:** MezzConfig é biblioteca, não provider da semântica dos parâmetros. Após promover o top-level, testar listener disparado exatamente uma vez e fora de lock, rollback/atomic save (inclusive arquivo aberto no Windows), snapshots de sorting, traduções, serializers e migration com valores rejeitados. Reconciliar o top-level com o `MezzConfig 0.5.6` em `META-INF/jarjar/` do JEI físico; a distribuição e o changelog **não comprovam** que o conjunto JEI/MezzConfig e demais consumers atuais é ABI-compatível. Não tratar dependências embarcadas como mods top-level adicionais.

**Evidências primárias:** [CurseForge: 0.7.1 NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/mezzconfig/files/9527937), seção *What's new* com headings separados para cada release, e [source do autor mezz/MezzConfig](https://github.com/mezz/MezzConfig), que define a API pública sob `net.mezzdev.config.api`. Não foi extraído o JAR distribuído nem executado ambiente cliente/dedicated server. A enumeração dos headings não prova isoladamente que cada versão teve um binário NeoForge 1.21.1 publicado; comprova a sequência técnica do upstream.

**Resultado documental:** histórico completo de mudanças relevantes **0.5.9 → 0.7.1** registrado, sem substituir a versão instalada ou afirmar homologação física.

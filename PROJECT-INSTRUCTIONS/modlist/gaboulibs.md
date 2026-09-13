# gaboulibs — 1.9

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c869db9f0db814cabb8eae645d1c5fa  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** gaboulibs
- **Arquivo JAR:** `gaboulibs-neoforge-1.9.jar`
- **Versão 1.21.1:** `1.9`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/xGabou/Gabou-s-Libs/tree/1.21.1
- **Função:** Biblioteca compartilhada com networking, tick/events, data attachments, movement abilities, prediction/interpolation, collision e fluid/snow utilities para mods consumidores do autor.
- **Dependências:** NeoForge 1.21.1; source 1.9 declara NeoForge 21.1.211. Consumers específicos devem ser confirmados individualmente; a library não ativa automaticamente todas as abilities presentes no source.
- **Compatibilidade/Riscos:** Stale attachments, packet duplication/reorder, prediction divergence, high-speed collision, races com collision threads, tick/config mismatch e competição com outros movement/collision mods. A documentação antiga de auth 1.8.7 é obsoleta: 1.9 removeu auth.
- **Sobreposição:** Infraestrutura pode cruzar com outros frameworks de movimento/collision/networking, mas cada consumer mantém authority de suas regras. Evitar duas camadas aplicando a mesma ability, displacement ou collision settlement no mesmo tick.
- **Observações:** Corrigida divergência antiga: runtime é 1.9, não 1.8.7. O source exato contém abilities Double Jump, Wall Jump, Dash, Wall Slide, Glide, Water Jump, Ground Pound, Crawl e Roll; presença de classes não implica ativação. Auth foi removido na 1.9.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial xGabou/Gabou-s-Libs branch 1.21.1 exatamente em 1.9 + distribuição/changelog oficial da release 1.9.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Gabou's Libs 1.9 source-pinned; lifecycle, networking, attachments, movement abilities, prediction/collision, config, remoção do auth legado, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `gaboulibs-neoforge-1.9.jar`, mod id `gaboulibs`. O branch oficial `xGabou/Gabou-s-Libs:1.21.1` declara exatamente `mod_version=1.9`, Minecraft 1.21.1 e NeoForge 21.1.211. A metadata antiga desta ficha citava 1.8.7/Auth; isso está obsoleto: a release 1.9 removeu o sistema de auth e o source exato não contém esse caminho.

## 1. Identidade e versão
- **Mod:** Gabou's Libs.
- **JAR físico:** `gaboulibs-neoforge-1.9.jar`.
- **Mod id:** `gaboulibs`.
- **Versão instalada:** `1.9`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Source pin:** branch `1.21.1`, exatamente 1.9.

## 2. Papel no modpack
Gabou's Libs é biblioteca compartilhada pelos mods do autor. A versão 1.9 não é apenas um holder de helpers triviais: o source contém infraestrutura comum para networking, tick, data attachments, movement abilities, prediction/interpolation, collision, fluid/snow handling e abstrações de plataforma. Consumers permanecem authority das regras de gameplay que escolhem habilitar; a library fornece implementação/utilidades compartilhadas.

## 3. Entrypoint e lifecycle
`Gaboulibs` inicializa common/config/loader, registra tick events, data attachments e packets S2C/C2S, e separa client initialization conforme ambiente. O source também conecta eventos de player join, leave e death, server command registration e ticks.
Isso torna login/logout/death/reconnect contratos reais: state de movimento/prediction de um consumidor não pode vazar para uma nova sessão/player.

## 4. Networking
O source 1.9 possui `PacketHandlerCommon` e payloads S2C/C2S. A biblioteca fornece plumbing de rede, mas o servidor deve continuar authority sobre ações que mudam gameplay. Pacotes de movimento/ability precisam ser validados, rate-limited conforme desenho do consumer e processados exatamente uma vez.

## 5. Data attachments e player/entity state
A árvore source inclui custom data attachments e classes de entity/player data. Esses dados podem sustentar abilities, timers ou state sincronizado de consumers. Integrações externas não devem manter uma segunda cópia concorrente do mesmo state nem persistir referência a entity antiga após death/dimension change.

## 6. Movement abilities presentes na library
O source contém implementações/infraestrutura para:
- Double Jump;
- Wall Jump;
- Dash;
- Wall Slide;
- Glide;
- Water Jump;
- Ground Pound;
- Crawl;
- Roll.
A presença dessas classes na library **não prova que todas estão ativas no pack**. Ativação, cooldown, custo e progressão pertencem ao consumer/config que as usa.

## 7. Collision, fluid e prediction
A linha 1.9 inclui componentes de collision, high-speed collision, fluid/snow handling, client interpolation/extrapolation e local prediction. São superfícies sensíveis a multiplayer: visual prediction pode antecipar movimento, mas o state final deve convergir ao servidor/consumer authority.

## 8. Configuração — `config/gaboulibs.properties`
Campos confirmados no source 1.9 incluem:
- `SERVER_TICK_RATE`;
- `SERVER_COLLISION_THREADS`;
- `HIGH_SPEED_COLLISION`;
- `MAX_DISPLACEMENT_PER_STEP`;
- `MODE`;
- `CLIENT_TICK_RATE`;
- `EXTRAPOLATION`;
- `SIGNAL_SPEED`;
- `INTERPOLATION_SPEED`;
- `LOCAL_PREDICTION`;
- `CLIENT_COLLISION_THREADS`;
- `PRINT_MODPACK_HASH`;
- `NETWORK_HANDLER`.
Não registrar defaults não auditados nesta etapa. Esses valores têm potencial de alterar performance e sincronização; precisam ser lidos da instância antes de tuning.

## 9. Auth removido em 1.9
A release 1.9 registra remoção do auth e a árvore source exata não contém classes/paths `Auth`. Portanto qualquer documentação anterior sobre `AuthPacket`, handshake de autenticação próprio ou risco específico de auth da 1.8.7 deve ser removida da análise atual. Segurança de networking ainda importa, mas por validação dos payloads presentes, não por um sistema já removido.

## 10. Client / server
- **Servidor/common:** tick, collision authority conforme consumer, data attachments, events e validação de packets.
- **Cliente:** interpolation/extrapolation, prediction, rendering/model helpers e input presentation.
- **Shared:** abstrações de abilities/network/state.
Client prediction nunca deve liquidar dano, cooldown ou deslocamento final sem validação adequada do consumer/server.

## 11. Lifecycle e multiplayer
Validar player join/leave, death/respawn, dimension transfer, reconnect, server restart, entity replacement, high-latency/jitter, correction após prediction e shutdown de worker threads. Configuração de múltiplas collision threads exige atenção a race/data ownership.

## 12. Riscos técnicos
- stale player/entity attachment após death/reconnect;
- packet duplicado/reordenado;
- prediction client divergindo do servidor;
- high-speed collision tunneling ou dupla resolução;
- race com múltiplas collision threads;
- tick rate/config incompatível entre sides;
- consumer ativar ability duas vezes por listener duplicado;
- classloading client no dedicated server;
- metadata/documentação antiga de 1.8.7 ser aplicada à 1.9;
- outra biblioteca/mod de movimento disputar transform/collision no mesmo tick.

## 13. Matriz de testes obrigatória
- [ ] Dedicated server boot com Gabou's Libs 1.9 e consumers atuais.
- [ ] Login/logout/reconnect limpa state temporário.
- [ ] Death/respawn não herda attachment/ability state indevido.
- [ ] Dimension change preserva apenas state intencional.
- [ ] S2C/C2S: cada ação processada exatamente uma vez.
- [ ] Latência artificial: prediction/interpolation converge ao server state.
- [ ] High-speed collision sem tunneling/dupla resolução.
- [ ] Collision threads >1 em teste controlado sem race.
- [ ] Consumers de Double Jump/Dash/Roll/etc. ativam somente abilities configuradas.
- [ ] Confirmar ausência de qualquer fluxo Auth legado da 1.8.7.
- [ ] Coexistência com outros mods de movimento/collision do pack.

## 14. Evidências e limites
**Source primário pinado:** `xGabou/Gabou-s-Libs`, branch `1.21.1`, exatamente 1.9.
**Entrypoint/config/tree auditados:** lifecycle, packets, attachments, abilities, collision/prediction e `config/gaboulibs.properties`.
**Distribuição oficial:** Gabou's Libs 1.9, Client & Server; changelog registra remoção de auth.
**Limite:** a existência de ability/helper no framework não prova uso por um consumer específico; consumers devem ser confirmados separadamente.
**Nenhum teste de runtime foi executado nesta catalogação.**

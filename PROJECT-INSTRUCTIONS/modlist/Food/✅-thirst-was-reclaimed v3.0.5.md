# Thirst Was Reclaimed

> **Reauditoria física — 17/09/2026.** Versão catalogada atual: `3.0.5`. O conteúdo abaixo foi reconstruído a partir da página Notion reconciliada e da autoridade física atual; a URL da própria página Notion foi deliberadamente omitida.

## Propriedades do registro

- **Mod:** Thirst Was Reclaimed
- **Arquivo JAR:** ThirstWasReclaimed-1.21.1-3.0.5.jar
- **Versão 1.21.1:** 1.21.1-3.0.5
- **Categoria:** Comida; RPG
- **Função:** Provider principal de sede/hidratação do pack: mantém thirst do jogador, purity da água/containers, item settings e sincronização server→client; 3.0.4 expõe thirst via persistent data e refaz Jade.
- **Dependências:** NeoForge 1.21.1. Thirst Was Fixed 2.1.6 e Sophisticated Thirst Upgrade 0.1.8 permanecem extensões separadas. Cold Sweat 2.4.3.1 e Create 6.0.10 são integration/regression surfaces quando aplicáveis.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Purity migration/propagation, config hash/sync stale, addon double ownership e HUD overlap. Linha 3.0.x trata purity ausente como max, permite disable global e sincroniza settings; testar transfers Bottle/Bucket/Pipes/Create e addons.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed/files/8391910 ; https://github.com/mlus-asuka/Thirst-Was-Reclaimed
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Thirst Was Reclaimed 3.0.5 (file ID 8852248, 10/09/2026) + dossiê anterior 3.0.4 preservado. Nenhum teste runtime foi executado.
- **Observações:** Runtime físico `ThirstWasReclaimed-1.21.1-3.0.5.jar`, mod id `thirst`, metadata `1.21.1-3.0.5`; versão semântica 3.0.5. A 3.0.5 corrige incompatibilidade com CreateCyberGoggle, adiciona cooldown para beber água diretamente, corrige perda de purity em container do Supplementaries e erro de parsing de loot table.
- **Atualização/Status:** READITADO EM 17/09/2026 — runtime físico atualizado para ThirstWasReclaimed-1.21.1-3.0.5.jar / metadata 1.21.1-3.0.5. Release 3.0.5 de 10/09/2026 adiciona cooldown ao direct drinking e corrige CreateCyberGoggle, perda de purity em containers do Supplementaries e erro de parsing de loot table. Histórico 3.0.4 preservado.
- **Histórico da decisão:** 
- **Sobreposição:** Não é um segundo sistema de fome ou nutrição; adiciona a necessidade separada de hidratação.
- **Data da última decisão:** 2026-08-27

> 🥤 **ESCOPO CANÔNICO.** Runtime físico: `ThirstWasReclaimed-1.21.1-3.0.5.jar`, mod id `thirst`, versão `1.21.1-3.0.5`. Thirst Was Reclaimed é o **provider principal de sede/hidratação** do pack: mantém thirst do jogador, regras de consumo e sistema de purity da água.
## 1. Identidade, versão e papel
- **Mod:** Thirst Was Reclaimed.
- **JAR:** `ThirstWasReclaimed-1.21.1-3.0.5.jar`.
- **Mod id:** `thirst`.
- **Versão:** `3.0.5` para MC 1.21.1.
- **Loader:** NeoForge.
- **Ambiente:** Client & Server.
- **Papel:** necessidade separada de hidratação, projetada para compatibilidade com modpacks.
## 2. Authority e ownership
Thirst Was Reclaimed é authority de:
- valor de thirst do jogador;
- efeitos/regras base de hidratação;
- purity de água/containers quando habilitada;
- item settings e regras sincronizadas do sistema.
Thirst Was Fixed e Sophisticated Thirst Upgrade são extensões; não devem manter uma segunda barra/state concorrente.
## 3. Thirst state do jogador
O mod adiciona um recurso separado da fome. O servidor deve ser a fonte de verdade do nível de thirst; HUD e integrations apenas representam/consomem esse state.
A 3.0.4 passou a expor o thirst level também via `player.getPersistentData()`, criando uma superfície oficial para addons/scripts lerem o recurso sem duplicá-lo.
## 4. Purity — semântica da linha 3.0.x
O refactor 3.0.0 alterou profundamente purity:
- removeu default purity configurável anterior;
- items/fluids sem tag de purity passam a ser tratados como **max purity** por padrão;
- valores inválidos são sanitizados;
- `enablePurity` permite desativar completamente o sistema;
- com purity desativada, tags/effects de purity não devem ser aplicados.
Addons como Thirst Was Fixed precisam obedecer esta semântica.
## 5. Propagação de purity em containers e fluids
A linha 3.0.0 corrigiu propagation para:
- bottles;
- buckets;
- pipes;
- campfire recipes;
- Create integration;
- Tough As Nails compatibility;
- mountain/running water cases.
Isso demonstra que purity é data que acompanha água através de múltiplos caminhos de transferência, não apenas atributo visual do item.
## 6. Item settings e keyword rules
O provider possui server item settings e keyword rules para definir quanto itens/fluidos hidratam e como são reconhecidos.
A linha 3.0.x sincroniza essas regras com clients. A configuração física do pack não foi lida; não registrar valores de hydration específicos sem o arquivo runtime.
## 7. Config synchronization
3.0.0 introduziu/expandiu sync de:
- server item settings;
- keyword rules;
- purity enablement;
- custom purity containers.
O protocolo usa hash SHA-256 para que cliente só solicite config completa quando a versão do servidor mudou.
Isso exige limpeza/reload correto ao trocar de servidor para evitar regras stale.
## 8. HUD e AppleSkin
A linha 3.0.0 inclui correções de coordenadas para thirst HUD/tooltip em integração AppleSkin, evitando overlap visual.
O pack possui múltiplos HUD/QoL mods; qualquer ausência de sede visual deve ser separada entre state do servidor e renderer do cliente.
## 9. Jade — release 3.0.4
A mudança principal 3.0.4 refatora integração Jade para exibir:
- fluid storage;
- purity.
O pack usa Jade no ecossistema; tanks/containers suportados devem apresentar valor coerente com o state real, sem converter tooltip em authority.
## 10. Create e fluid ecosystem
O source 1.21.1 inclui Create como integration/runtime de desenvolvimento e a linha 3.0.0 menciona fix explícito de purity propagation com Create.
No pack com Create 6.0.10 e muitos fluid addons, validar bucket/bottle/pipe/tank transitions e evitar perda de purity durante transferências.
## 11. Cold Sweat e outros survival mods
O source 1.21.1 inclui Cold Sweat entre integrações de desenvolvimento. O pack físico atual possui Cold Sweat 2.4.3.1.
Isto não significa que Thirst e temperature sejam o mesmo recurso: cada sistema mantém authority própria. Testar apenas a integração publicada/runtime, sem fundir balanceamento de thirst e body temperature na documentação.
## 12. Addons concretos no pack
- **Thirst Was Fixed 2.1.6:** corrige cauldrons e integra Ars/Ultimine/ParCool/Amendments.
- **Sophisticated Thirst Upgrade 0.1.8:** extensão para storage/backpacks sofisticados, catalogada separadamente.
- **Cold Sweat 2.4.3.1:** sistema térmico separado.
- **Create 6.0.10:** fluid transfer integration relevante.
- **Jade:** inspection de storage/purity na 3.0.4.
## 13. Client / server e multiplayer
- Servidor: thirst, purity e regras configuradas.
- Cliente: HUD/tooltips e caches sincronizados.
- Cliente não pode escolher hydration/purity local para superar regra do servidor.
- Troca de servidor deve invalidar hash/config anterior quando necessário.
## 14. Lifecycle
Validar:
- criação/login do player;
- beber item/fluido e morte/respawn;
- relog/server restart;
- bucket↔bottle↔tank↔pipe;
- Create transfer;
- purity ON/OFF em cópia de teste;
- troca entre servidores com configs distintas;
- Jade inspection após mudança de purity;
- addons lendo persistent thirst data.
## 15. Riscos técnicos
1. **Purity migration:** worlds/items antigos podem ter tags da semântica anterior.
2. **Transfer propagation:** qualquer ponte de fluid pode perder/sanitizar purity incorretamente.
3. **Config desync:** hydration/purity rules stale no cliente.
4. **Addon double ownership:** extensions não podem criar state paralelo.
5. **HUD overlap:** AppleSkin/outros HUDs podem esconder ou deslocar indicador.
6. **PersistentData consumers:** addon pode interpretar escala/ausência de dado incorretamente; validar contrato antes de scripting.
7. **Purity disabled:** addons precisam respeitar modo global sem reintroduzir tags/effects.
## 16. Matriz de testes
- [ ] Dedicated server boot com TWR 3.0.5.
- [ ] Thirst reduz/aumenta e persiste conforme gameplay esperado.
- [ ] Morte/respawn/relog não duplica ou zera state indevidamente.
- [ ] Purity ausente é interpretada segundo semântica 3.0.x.
- [ ] Purity inválida é sanitizada sem crash.
- [ ] `enablePurity` OFF remove efeitos/tags esperados em ambiente de teste.
- [ ] Bottle/bucket/pipe/Create transfer preserva purity.
- [ ] Cliente recebe item settings/keyword/purity config do servidor.
- [ ] Troca de servidor não mantém config/hash stale.
- [ ] Jade mostra fluid storage/purity coerentes.
- [ ] Thirst Was Fixed e Sophisticated Thirst Upgrade usam o mesmo state base.
- [ ] Persistent player data expõe thirst para integração sem criar segunda authority.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 17. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e addons/integrations presentes.
- CurseForge oficial 3.0.4: build NeoForge 1.21.1, Jade refactor e persistent thirst data.
- Changelog oficial 3.0.0: purity refactor, transfer fixes, config sync/hash e AppleSkin HUD fixes.
- Repositório oficial branch 1.21.1: modpack-oriented thirst provider e integration surface; source usado para arquitetura, não para inventar valores de config locais.
## 18. Revalidação física — 11/09/2026
Àquela data, o runtime físico confirmado era `ThirstWasReclaimed-1.21.1-3.0.4.jar`, mod id `thirst`, versão `3.0.4`. Esse registro permanece como histórico da auditoria anterior.
Thirst Was Fixed `2.1.6` e Sophisticated Thirst Upgrade `0.1.8` permaneciam extensões separadas. Nenhum teste de purity migration, Bottle/Bucket/Pipe/Create transfer, Jade, config hash/sync ou persistent-data consumer foi executado naquela recatalogação.
## 19. Revalidação física e upstream — 13/09/2026
Naquela revalidação, o runtime físico ainda era `ThirstWasReclaimed-1.21.1-3.0.4.jar`, versão `3.0.4`. O mod permanecia authority única de thirst/purity, com Thirst Was Fixed `2.1.6` e Sophisticated Thirst Upgrade `0.1.8` como extensões. Nenhum teste de purity migration, Bottle/Bucket/Pipe/Create transfer, Jade, config hash/sync ou persistent-data consumer foi executado naquela revalidação.
## 20. Atualização instalada — 3.0.5
A release oficial `1.21.1-3.0.5`, file ID 8852248, publicada em 10/09/2026 para NeoForge 1.21.1, registra quatro mudanças concretas:
1. correção de incompatibilidade com **CreateCyberGoggle**;
2. cooldown para beber água diretamente com a mão;
3. correção de perda de **purity** em container vindo de **Supplementaries**;
4. correção de erro de parsing ao carregar loot table.
Esses quatro pontos passam a ser regression gates da build física atual. O histórico da 3.0.4 permanece preservado porque a integração Jade e a exposição via `player.getPersistentData()` pertencem ao contexto documentado daquela linha.
Nenhum teste de drinking, purity, loot, Supplementaries, CreateCyberGoggle ou Cold Sweat foi executado nesta atualização documental.
### Regression gates da 3.0.5
- [ ] Direct drinking respeita o cooldown e não duplica consumo em spam/retry.
- [ ] Container do Supplementaries preserva purity ao transferir/armazenar água.
- [ ] Loot tables carregam sem erro de parsing.
- [ ] CreateCyberGoggle coexiste sem incompatibilidade reproduzível.
- [ ] Dedicated server inicia com Thirst 3.0.5 e Cold Sweat 2.4.3.1.
## 21. Upstream posterior observado
Em 16/09/2026 foi observada uma publicação `3.0.6` cujo filename sugere Minecraft 1.21.1, porém a listagem recuperada do CurseForge apresenta metadata de game version inconsistente. Pela regra fail-closed e pela autoridade física do pack, essa publicação **não** substitui o runtime instalado `3.0.5` nesta auditoria. Uma atualização futura exige confirmar o artefato físico realmente instalado e a compatibilidade correspondente.

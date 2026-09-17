# Knight Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na origem:** Integrado ao Github
- **Autoridade física atual:** `modlist(1).txt` anexada em 16/09/2026 — Knight Lib `2.0.1` e Companions! `1.3.4` confirmados fisicamente
- **Data da reauditoria:** 2026-09-16

## Propriedades do banco

- **Mod:** Knight Lib
- **Arquivo JAR:** `knightlib-neoforge-1.21.1-2.0.1.jar`
- **Versão 1.21.1:** 2.0.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca multiloader de utilidades compartilhadas para networking, configs, eventos, AI, bossbars, camera shake, shaders, spawns, loot, persistent sounds, entity data e registration; bridge concreta para Companions! no pack.
- **Dependências:** NeoForge 1.21.1. Consumer físico confirmado: `companions-neoforge-1.21.1-1.3.4.jar`. O changelog oficial de Companions! 1.3.4 eleva explicitamente a versão mínima requerida de Knight Lib para **2.0.1**; GeckoLib permanece dependência do consumer, não da própria Knight Lib.
- **Sobreposição:** Não é substituível por biblioteca genérica sem adaptar consumers. Companions! 1.3.4 é dependente concreto. Similaridade funcional de networking/config/event APIs com outras libs não implica ABI compatível.
- **Compatibilidade/Riscos:** Library de ampla superfície. Riscos: networking/config/event ABI drift, hot-reload inconsistente, bossbar links stale, entity persistent-data/schema drift, AI/OBB/render paths em side errado e consumer incompatível. O antigo version gate 1.6.1 → 2.0.1 está fisicamente atravessado e, no pack atual, Companions! 1.3.4 requer exatamente Knight Lib ≥2.0.1. A release 2.0.1 corrige particle keyframe worldspace transform que usava incorretamente a inverse view rotation matrix.
- **Observações:** O dossiê de origem foi source-pinned em Knight Lib 1.6.1 e documentava networking, auto config, common event bus, Automaton AI, OBB hitboxes experimentais, camera shake, boss bars, music provider, post shaders, biome spawns, loot helpers, persistent sounds, entity data e registrar. Em 12/09/2026 o Notion registrou 1.6.2 e 2.0.1 apenas como updates externos. A modlist física atual confirma `2.0.1`; Companions! também avançou para 1.3.4 e seu changelog oficial torna 2.0.1 requisito mínimo.
- **Procedência:** modlist física atual anexada em 16/09/2026 + CurseForge oficial Knight Lib 2.0.1 NeoForge 1.21.1, file ID 8859434, publicado em 11/09/2026 + source oficial Xylonity/Knight-Lib + changelog oficial Xylonity/Companions 1.3.4 confirmando Knight Lib 2.0.1 como mínimo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/knight-lib/files/8859434 | https://github.com/Xylonity/Knight-Lib/tree/1.21.1 | https://github.com/Xylonity/Companions/blob/508013d35529e8a67953cce0a7c64efa6ab78c2c/CHANGELOG.md
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 16/09/2026 — Knight Lib físico 2.0.1 confirmado; o version gate major registrado no Notion foi encerrado pela instalação física e pela atualização concomitante de Companions! para 1.3.4, que exige Knight Lib 2.0.1. Superfícies de networking/config/events/AI/bossbar/render/data/registrar, lifecycle, riscos e testes permanecem catalogadas.
- **Histórico da decisão:** 2026-08-26 — registro inicial com Knight Lib 1.x e consumer Companions!. 2026-09-10 — Knight Lib 1.6.1 documentada com correção de bossbar links em world reload. 2026-09-12 — Notion registrou 1.6.2 e 2.0.1 como updates externos e bloqueou promoção automática por salto major. 2026-09-16 — modlist física confirma Knight Lib 2.0.1 e Companions! 1.3.4; o changelog do consumer confirma 2.0.1 como versão mínima requerida.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> 🛡️ **ESCOPO CANÔNICO.** Runtime físico atual: `knightlib-neoforge-1.21.1-2.0.1.jar`, mod id `knightlib`, versão `2.0.1`. O pack contém **Companions! 1.3.4**, cujo changelog oficial declara: a versão mínima requerida de Knight Lib foi elevada para **2.0.1**. Isso substitui o antigo estado do Notion em que 1.6.1 era física e 2.0.1 aparecia apenas como version gate externo.

## 1. Papel e authority
Knight Lib é uma biblioteca multiloader que centraliza sistemas comuns reutilizados por mods Xylonity/consumers. Ela é authority da implementação compartilhada de suas APIs; o gameplay específico continua pertencendo ao consumer.

## 2. Consumer físico — Companions!
O pack contém `companions-neoforge-1.21.1-1.3.4.jar`. O histórico do dossiê registrava Companions! 1.3.2 como required consumer de Knight Lib e GeckoLib. A cadeia atual ficou mais forte: o changelog oficial de **Companions! 1.3.4** declara que a versão mínima requerida de Knight Lib foi elevada para **2.0.1**. Logo Knight Lib 2.0.1 é operacionalmente necessária enquanto esse consumer estiver instalado.

## 3. Networking
O README/source auditado documenta canais S2C/C2S por mod com API de envio/recebimento. Packets devem validar side, target e state server-authoritative; um packet repetido não pode liquidar ação de gameplay duas vezes.

## 4. Auto Config
A library oferece TOML config, GUI gerada, hot-reload e compatibilidade com frontends documentados. O consumer continua owner do significado das opções. Hot-reload só deve ser usado em valores cujo lifecycle o consumer realmente suporta.

## 5. Event Bus comum
Knight Lib abstrai eventos entre loaders para código comum. Isso reduz duplicação, mas cria risco de listener duplicado ou ordem diferente após port/update. Eventos que concedem dano/recompensa precisam de idempotência no consumer.

## 6. Automaton AI
O projeto expõe finite-state-machine para AI complexa. Estado da entidade precisa persistir/limpar conforme o consumer; client animation não pode assumir authority sobre transition AI server-side.

## 7. OBB hitboxes experimentais
Há suporte experimental a oriented bounding boxes vinculadas a bones GeckoLib. Como é superfície experimental, colisão/damage/hit detection exige regression test, especialmente com animation speed, scaling e multiplayer latency.

## 8. Camera, bossbars e música
A library inclui camera shake, custom boss bars e music provider. Camera/music são apresentação client-side; bossbar membership/link state depende do server/entidade. A 1.6.1 corrigiu especificamente **bossbar links que não eram limpos em world reload**; esse regression gate histórico deve continuar coberto após o upgrade 2.x.

## 9. Post shaders e rendering
Utilities de post-processing shader podem operar sobre múltiplos targets. Não devem vazar render state nem ser carregadas em dedicated-server path indevido. Compatibilidade com Iris/render stack precisa ser testada pelo consumer que ativa o efeito.

## 10. Spawns, loot e persistent sounds
Biome spawn API e loot helpers atuam sobre registries/data/events de gameplay; persistent sounds vinculam áudio a entities/items/blocks. Spawn/loot settlement permanece server-authoritative; som não é evidência de evento concluído.

## 11. Entity Data e Registrar
A library fornece acesso a persistent entity data e helpers de registration. IDs/schema precisam permanecer estáveis entre updates; remover/renomear field do consumer pode deixar save data stale. Registrar não autoriza duplicate namespace IDs.

## 12. Client / server
A release 2.0.1 é publicada para NeoForge 1.21.1 e classificada como Client & Server. Networking, configs server-relevant, AI, spawns, loot e entity data têm paths server-authoritative. Camera, shaders e parte da UI são client-side. Common code deve separar classes de ambiente corretamente.

## 13. Lifecycle
Validar construction/registration, config load/hot-reload, world join/reload, entity spawn/despawn, bossbar link/unlink, chunk unload, reconnect e server restart. O fix 1.6.1 mantém world reload como regression gate histórico.

### Version gate 1.x → 2.x — resolvido fisicamente
O Notion de 12/09/2026 registrava **1.6.2** e **2.0.1** como versões upstream posteriores à 1.6.1 física e, corretamente, não presumiu compatibilidade binária no salto major. A situação atual é diferente:
- Knight Lib física = **2.0.1**;
- Companions! físico = **1.3.4**;
- Companions! 1.3.3 introduziu compatibilidade com Knight Lib 2.0.0;
- Companions! 1.3.4 elevou a versão mínima de Knight Lib para **2.0.1**.

Portanto, o antigo version gate não é mais um “update disponível”: o par de versões compatíveis está instalado. Isso não equivale a afirmar que todos os testes de runtime abaixo foram executados.

## 14. Changelog exato 2.0.1
A release oficial Knight Lib 2.0.1 para NeoForge 1.21.1 registra uma correção específica: **particle keyframe worldspace transform** não deve usar incorretamente a **inverse view rotation matrix**. Não são atribuídas à 2.0.1 outras mudanças sem evidência explícita.

## 15. Riscos técnicos
- consumer ABI drift no salto 1.x → 2.x, mitigado para o consumer principal pela exigência explícita de Companions! 1.3.4;
- duplicate event/network registration;
- packet aplicado duas vezes;
- hot-reload em opção não hot-safe;
- bossbar link stale após reload;
- OBB hitbox divergir da animação;
- entity data/schema migration quebrar save;
- render/client class em dedicated server;
- particle keyframe worldspace transform regredir;
- remover library mantendo Companions! instalado.

## 16. Matriz de testes obrigatória
- [ ] Dedicated server + cliente iniciam com Knight Lib 2.0.1 e Companions! 1.3.4.
- [ ] Networking S2C/C2S do consumer não duplica settlement.
- [ ] Config parse/GUI funciona e hot-reload respeita limites do consumer.
- [ ] Event listeners não duplicam após reload/reconnect.
- [ ] AI entities mantêm state coerente após chunk unload.
- [ ] OBB hitbox, quando usada, acompanha bones sem hits fantasmas críticos.
- [ ] Bossbars limpam links em world reload — regressão histórica da 1.6.1.
- [ ] Camera/music/shader effects não alteram gameplay authority.
- [ ] Persistent entity data sobrevive restart sem duplicação.
- [ ] Dedicated server não carrega render-only classes.
- [ ] Particle keyframes em worldspace usam transformação visual correta — regressão 2.0.1.

## 17. Evidências e limites
- **Modlist física atual:** Knight Lib 2.0.1 e Companions! 1.3.4.
- **CurseForge oficial:** Knight Lib 2.0.1 NeoForge 1.21.1, file ID 8859434, Release de 11/09/2026; changelog do particle worldspace transform.
- **Source oficial:** Xylonity/Knight-Lib; snapshot indexado declara `version=2.0.1`.
- **Changelog oficial do consumer:** Companions! 1.3.3 adiciona compatibilidade com Knight Lib 2.0.0; 1.3.4 eleva a versão mínima para 2.0.1.
- **Histórico preservado do Notion:** subsistemas auditados em 1.6.1, fix de bossbar links em world reload e version gate 1.6.1 → 2.0.1 registrado em 12/09/2026.
- **Limite:** uso efetivo de cada subsystem por cada consumer não foi inferido sem source específico do consumer; nenhum dos testes de runtime acima foi executado nesta catalogação.

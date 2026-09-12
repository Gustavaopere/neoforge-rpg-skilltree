# Knight Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db810da2b3f510f94c2635
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — Knight Lib `1.6.1` e Companions! `1.3.2` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Knight Lib
- **Arquivo JAR:** `knightlib-neoforge-1.21.1-1.6.1.jar`
- **Versão 1.21.1:** 1.6.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca multiloader de utilidades compartilhadas para networking, configs, eventos, AI, bossbars, camera shake, shaders, spawns, loot, persistent sounds, entity data e registration; bridge concreta para Companions! no pack.
- **Dependências:** Source exato 1.21.1 declara Knight Lib 1.6.1, Java 21, Minecraft 1.21.1 e desenvolvimento NeoForge 21.1.150. A própria library não lista hard dependency adicional no CurseForge. Consumer físico confirmado: Companions! 1.3.2 requer Knight Lib e GeckoLib.
- **Sobreposição:** Não é substituível por biblioteca genérica sem adaptar consumers. Companions! 1.3.2 é dependente concreto. Similaridade funcional de networking/config/event APIs com outras libs não implica ABI compatível.
- **Compatibilidade/Riscos:** Library de ampla superfície. Riscos: networking/config/event ABI drift, hot-reload inconsistente, bossbar links stale, entity persistent-data/schema drift, AI/OBB/render paths em side errado e consumer incompatível. 1.6.1 corrige links de bossbar não limpos em world reload.
- **Observações:** Source-pinned 1.6.1. Subsistemas publicados incluem networking, auto config, common event bus, Automaton AI, OBB hitboxes experimentais, camera shake, boss bars, music provider, post shaders, biome spawns, loot helpers, persistent sounds, entity data e registrar. 1.6.1 limpa bossbar links em world reload.
- **Procedência:** modlist.txt física atual + CurseForge oficial Knight Lib 1.6.1 NeoForge 1.21.1 file 8400701 + source oficial Xylonity/Knight-Lib branch 1.21.1 com version=1.6.1 + README de subsistemas + dependência oficial de Companions! 1.3.2.
- **Fonte:** https://github.com/Xylonity/Knight-Lib/tree/1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Knight Lib 1.6.1 source-pinned; networking/config/events/AI/bossbar/render/data/registrar frameworks, Companions 1.3.2 consumer, world-reload fix, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `knightlib-neoforge-1.21.1-1.6.1.jar`, mod id `knightlib`, versão `1.6.1`. O source oficial branch `1.21.1` declara exatamente `version=1.6.1`, Java 21 e Minecraft 1.21.1. **Companions! 1.3.2**, presente no pack, declara Knight Lib como required dependency.

## 1. Papel e authority
Knight Lib é uma biblioteca multiloader que centraliza sistemas comuns reutilizados por mods Xylonity/consumers. Ela é authority da implementação compartilhada de suas APIs; o gameplay específico continua pertencendo ao consumer.

## 2. Consumer físico — Companions!
O pack contém `companions-neoforge-1.21.1-1.3.2.jar`. O projeto Companions! lista **Knight Lib** e GeckoLib como required dependencies. Isso torna a presença de Knight Lib operacionalmente necessária enquanto esse consumer estiver instalado.

## 3. Networking
O README documenta canais S2C/C2S por mod com API de envio/recebimento. Packets devem validar side, target e state server-authoritative; um packet repetido não pode liquidar ação de gameplay duas vezes.

## 4. Auto Config
A library oferece TOML config, GUI gerada, hot-reload e compatibilidade com frontends documentados. O consumer continua owner do significado das opções. Hot-reload só deve ser usado em valores cujo lifecycle o consumer realmente suporta.

## 5. Event Bus comum
Knight Lib abstrai eventos entre loaders para código comum. Isso reduz duplicação, mas cria risco de listener duplicado ou ordem diferente após port/update. Eventos que concedem dano/recompensa precisam de idempotência no consumer.

## 6. Automaton AI
O projeto expõe finite-state-machine para AI complexa. Estado da entidade precisa persistir/limpar conforme o consumer; client animation não pode assumir authority sobre transition AI server-side.

## 7. OBB hitboxes experimentais
Há suporte experimental a oriented bounding boxes vinculadas a bones GeckoLib. Como é superfície experimental, colisão/damage/hit detection exige regression test, especialmente com animation speed, scaling e multiplayer latency.

## 8. Camera, bossbars e música
A library inclui camera shake, custom boss bars e music provider. Camera/music são apresentação client-side; bossbar membership/link state depende do server/entidade. A 1.6.1 corrige especificamente **bossbar links que não eram limpos em world reload**.

## 9. Post shaders e rendering
Utilities de post-processing shader podem operar sobre múltiplos targets. Não devem vazar render state nem ser carregadas em dedicated-server path indevido. Compatibilidade com Iris/render stack precisa ser testada pelo consumer que ativa o efeito.

## 10. Spawns, loot e persistent sounds
Biome spawn API e loot helpers atuam sobre registries/data/events de gameplay; persistent sounds vinculam áudio a entities/items/blocks. Spawn/loot settlement permanece server-authoritative; som não é evidência de evento concluído.

## 11. Entity Data e Registrar
A library fornece acesso a persistent entity data e helpers de registration. IDs/schema precisam permanecer estáveis entre updates; remover/renomear field do consumer pode deixar save data stale. Registrar não autoriza duplicate namespace IDs.

## 12. Client / server
A release é Client & Server. Networking, configs server-relevant, AI, spawns, loot e entity data têm paths server-authoritative. Camera, shaders e parte da UI são client-side. Common code deve separar classes de ambiente corretamente.

## 13. Lifecycle
Validar construction/registration, config load/hot-reload, world join/reload, entity spawn/despawn, bossbar link/unlink, chunk unload, reconnect e server restart. O fix 1.6.1 torna world reload um regression gate obrigatório.

## 14. Riscos técnicos
- consumer ABI drift;
- duplicate event/network registration;
- packet aplicado duas vezes;
- hot-reload em opção não hot-safe;
- bossbar link stale após reload;
- OBB hitbox divergir da animação;
- entity data/schema migration quebrar save;
- render/client class em dedicated server;
- remover library mantendo Companions! instalado.

## 15. Matriz de testes obrigatória
- [ ] Dedicated server + cliente iniciam com Knight Lib 1.6.1 e Companions! 1.3.2.
- [ ] Networking S2C/C2S do consumer não duplica settlement.
- [ ] Config parse/GUI funciona e hot-reload respeita limites do consumer.
- [ ] Event listeners não duplicam após reload/reconnect.
- [ ] AI entities mantêm state coerente após chunk unload.
- [ ] OBB hitbox, quando usada, acompanha bones sem hits fantasmas críticos.
- [ ] Bossbars limpam links em world reload — regressão 1.6.1.
- [ ] Camera/music/shader effects não alteram gameplay authority.
- [ ] Persistent entity data sobrevive restart sem duplicação.
- [ ] Dedicated server não carrega render-only classes.

## 16. Evidências e limites
- **Modlist física:** Knight Lib 1.6.1 e Companions! 1.3.2.
- **Source exato:** branch `1.21.1`, `version=1.6.1`, Java 21/Minecraft 1.21.1.
- **README oficial:** subsistemas de networking/config/event/AI/OBB/camera/bossbar/music/shader/spawn/loot/sound/entity data/registrar.
- **CurseForge 1.6.1:** fix de bossbar links em world reload.
- **Limite:** uso efetivo de cada subsystem por cada consumer não foi inferido sem source específico do consumer.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

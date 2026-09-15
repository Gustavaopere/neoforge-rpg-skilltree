# Knight Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `knightlib-neoforge-1.21.1-1.6.1.jar`
- **Versão 1.21.1:** 1.6.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca multiloader de utilidades compartilhadas para networking, configs, eventos, AI, bossbars, camera shake, shaders, spawns, loot, persistent sounds, entity data e registration; bridge concreta para Companions! no pack.
- **Dependências:** Source exato 1.21.1 declara Knight Lib 1.6.1, Java 21, Minecraft 1.21.1 e desenvolvimento NeoForge 21.1.150. A própria library não lista hard dependency adicional no CurseForge. Consumer físico confirmado: Companions! 1.3.2 requer Knight Lib e GeckoLib.
- **Sobreposição:** Não é substituível por biblioteca genérica sem adaptar consumers. Companions! 1.3.2 é dependente concreto. Similaridade funcional de networking/config/event APIs com outras libs não implica ABI compatível.
- **Compatibilidade/Riscos:** Library de ampla superfície. Riscos: networking/config/event ABI drift, hot-reload inconsistente, bossbar links stale, entity persistent-data/schema drift, AI/OBB/render paths em side errado e consumer incompatível. Novo risco operacional: upstream 2.0.1 é major superior à 1.6.1 física; não atualizar sem validar Companions! e demais consumers.
- **Observações:** Runtime físico permanece 1.6.1. Upstream publicou 1.6.2 NeoForge 1.21.1 em 10/09/2026 e 2.0.1 em 11/09/2026. A 2.0.1 corrige particle keyframe worldspace transform; o salto 1.x→2.x é tratado como version gate, não atualização automática.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial Knight Lib: 1.6.1 físico, 1.6.2 de 10/09/2026 e 2.0.1 NeoForge 1.21.1 file 8859434 de 11/09/2026 + source/docs 1.6.1 já auditados. Consumer Companions! permanece motivo para fail-closed antes de qualquer troca major.
- **Fonte:** https://github.com/Xylonity/Knight-Lib/tree/1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Knight Lib 1.6.1/JAR físico reconfirmado, porém upstream 1.21.1 avançou para 1.6.2 em 10/09 e 2.0.1 em 11/09/2026. Gap de versão major registrado; atualização não aplicada porque Companions! é consumer físico e compatibilidade 2.x precisa ser validada.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🛡️ **ESCOPO CANÔNICO.** Runtime físico: `knightlib-neoforge-1.21.1-1.6.1.jar`, mod id `knightlib`, versão `1.6.1`. O source oficial branch `1.21.1` declara exatamente `version=1.6.1`, Java 21 e Minecraft 1.21.1. **Companions! 1.3.2**, presente no pack, declara Knight Lib como required dependency.

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

### Version gate atual
Após o JAR físico 1.6.1, o upstream publicou **1.6.2 NeoForge 1.21.1 em 10/09/2026** e **2.0.1 NeoForge 1.21.1 em 11/09/2026**. A 2.0.1 publica correção de particle keyframe worldspace transform. Como existe salto de major `1.x → 2.x` e **Companions! 1.3.2** é consumer físico, esta catalogação registra atualização disponível mas não presume compatibilidade binária: a troca deve ser bloqueada até teste de bootstrap, networking/config, AI/bossbar/render e consumer real.

## 14. Riscos técnicos
- consumer ABI drift, especialmente no salto físico 1.6.1 → upstream 2.0.1;
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
- [ ] Knight Lib 2.0.1 só substitui a 1.6.1 após smoke test explícito de Companions! e demais consumers.

## 16. Evidências e limites
- **Modlist física:** Knight Lib 1.6.1 e Companions! 1.3.2.
- **Source exato:** branch `1.21.1`, `version=1.6.1`, Java 21/Minecraft 1.21.1.
- **README oficial:** subsistemas de networking/config/event/AI/OBB/camera/bossbar/music/shader/spawn/loot/sound/entity data/registrar.
- **CurseForge 1.6.1:** fix de bossbar links em world reload.
- **Limite:** uso efetivo de cada subsystem por cada consumer não foi inferido sem source específico do consumer.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

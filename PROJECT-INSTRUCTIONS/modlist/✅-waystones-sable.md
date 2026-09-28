# Waystones: Sable

> **Autoridade física atual — 27/09/2026.** Ordem física **#565**: JAR `waystonessable-1.0.7.jar`, mod id `waystonessable`, runtime `1.0.7`, SHA-1 `cebbc2cf9a7a8b2255caff04f2109d9611b28091`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Waystones: Sable
- **Arquivo JAR:** `waystonessable-1.0.7.jar`
- **Versão 1.21.1:** 1.0.7
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL, Tecnologia
- **Função:** Bridge Waystones↔Sable para SubLevels móveis: corrige teleportation, destination validation, distance calculation, client sync e transformação espacial sem substituir a rede Waystones nem a física Sable.
- **Dependências:** Waystones 21.1.45 + Sable 2.0.5 no snapshot físico atual; bridge 1.0.7 Client & Server.
- **Sobreposição:** Não substitui Waystones nem Sable. É compatibilidade espacial específica; não deve coexistir com uma segunda bridge que faça a mesma tradução sem deduplicação.
- **Compatibilidade/Riscos:** Riscos: coordinate drift, destination stale após assembly/unload, double-conversion de distância, client/server desync e bypass indevido de custos/visibility/permissions. Provider-specific fallback deve ser fail-closed.
- **Observações:** Mod id `waystonessable`, runtime 1.0.7. Waystones é authority de destino/teleporte; Sable é authority de SubLevel/transforms; a bridge apenas traduz entre ambos. Changelog exato 1.0.7: `fix Twinbound issue`; não extrapolar a natureza interna desse fix sem source/JAR pin.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + CurseForge/Modrinth oficiais Waystones:Sable 1.0.7 + Waystones 21.1.45 e Sable 2.0.5 físicos. Changelog exato da bridge 1.0.7 preservado como `fix Twinbound issue`; snapshots 08/09–13/09 com Waystones 21.1.44 permanecem históricos. Nenhum runtime spatial/teleport test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/waystones-sable
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Waystones:Sable 1.0.7 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; teleportation, validation, distance calculation, client sync e spatial transforms preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🗿 **ESCOPO CANÔNICO.** Runtime físico: `waystonessable-1.0.7.jar`, mod id `waystonessable`, versão `1.0.7`. Esta é a bridge específica **Waystones ↔ Sable** para SubLevels móveis. Waystones continua authority da rede/destinos de teleporte; Sable continua authority das transforms/sublevels; esta integração apenas traduz validação, distância, sincronização e coordenadas entre os dois domínios.
## 1. Identidade e stack atual
- **Waystones:Sable:** 1.0.7, Release NeoForge 1.21.1.
- **Waystones:** 21.1.45 no pack.
- **Sable:** 2.0.5 no pack.
- **Ambiente:** Client & Server.
A release 1.0.7 é a build pública atual para 1.21.1 e foi publicada em 12/08/2026.
## 2. Função confirmada
O projeto oficial declara quatro superfícies centrais para waystones colocadas dentro ou apontando para SubLevels Sable:
- correção de **teleportation**;
- **validation** de destino;
- **distance calculation**;
- **client synchronization**.
O objetivo é preservar a semântica normal do Waystones quando origem/destino não pertencem ao grid estático do level principal.
## 3. Authority e ownership
- **Waystones:** destino, activation, visibility, permissions, custos/cooldowns e autorização final do teleporte.
- **Sable:** identidade do SubLevel e transformações mundo ↔ espaço móvel.
- **Waystones:Sable:** tradução espacial e compatibilidade entre os dois.
Não manter lista paralela de destinations, não recalcular transform manualmente por heurística e não teleportar diretamente para coordenada stale quando o provider rejeita o destino.
## 4. Validação e distância
Distância de teleporte envolvendo SubLevel não deve ser calculada usando apenas coordenadas locais do sublevel. A bridge existe exatamente para evitar essa interpretação incorreta.
Qualquer perk ou regra externa de custo por distância deve consumir o resultado provider-native final; não duplicar distância por converter coordenadas novamente.
## 5. Sincronização client/server
Destino e resultado do teleporte são gameplay state server-authoritative. O cliente pode apresentar GUI/marker e receber coordenadas transformadas, mas não deve autorizar o teleporte.
Em multiplayer, dois clientes precisam observar a mesma posição final e a mesma identidade de destination, mesmo quando o sublevel se move entre abertura da UI e confirmação do teleporte.
## 6. Lifecycle crítico
Validar:
- waystone colocada em SubLevel;
- activation/rename/visibility;
- assemble/disassemble do espaço físico;
- movimento e rotação do SubLevel;
- chunk/sublevel unload/reload;
- server restart;
- teleport main world → SubLevel;
- SubLevel → main world;
- SubLevel → SubLevel;
- destino removido ou movido durante a janela de teleport.
## 7. Boundaries para quests/perks
- Discovery/activation só pode gerar milestone quando vier do state real do Waystones.
- Movimento do SubLevel não cria uma “nova waystone”.
- Assemble/disassemble/reload não pode gerar Mastery novamente.
- Se a bridge estiver ausente ou falhar, integração provider-specific deve ficar **fail-closed**; não usar coordenada local como fallback inventado.
## 8. Riscos
1. **Coordinate drift:** destino aponta para posição antiga após movimento/rotation.
2. **Distance double-conversion:** custo calculado duas vezes por bridge externa.
3. **Client desync:** UI mostra destino válido enquanto servidor já invalidou transform.
4. **Lifecycle stale state:** assembly/unload/restart deixa reference antiga.
5. **Teleport bypass:** integração própria ignora custos, visibility ou permission do Waystones.
6. **Version coupling:** bridge depende simultaneamente das semânticas de Waystones e Sable.
## 9. Matriz de testes
- [ ] Dedicated server inicia com Waystones 21.1.45 + Sable 2.0.5 + bridge 1.0.7.
- [ ] Waystone em SubLevel é ativada e listada uma única vez.
- [ ] Distância main↔SubLevel corresponde à posição física real.
- [ ] Teleport main→SubLevel pousa na posição correta após movimento do veículo.
- [ ] SubLevel→main e SubLevel→SubLevel preservam validação/custo.
- [ ] Assemble/move/rotate/disassemble não deixa destination stale.
- [ ] Chunk/sublevel unload e restart preservam a identidade correta.
- [ ] Dois jogadores usando o mesmo destino não produzem posições divergentes.
- [ ] Destino quebrado durante a janela de teleporte falha sem teleport parcial.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 10. Evidências
- **Modlist física 08/09/2026:** `waystonessable-1.0.7.jar`, `waystones-neoforge-1.21.1-21.1.44.jar`, `sable-neoforge-1.21.1-2.0.5.jar`.
- CurseForge oficial Waystones:Sable: Release 1.0.7, NeoForge 1.21.1, Client & Server; fixes de teleportation, validation, distance calculation e client sync para SubLevels.
- Guia de Tecnologia do projeto: transformação de coordenadas e boundary Waystones↔Sable.
## 11. Limitação
A implementação interna/signatures da bridge 1.0.7 não foi decompilada nesta etapa. Qualquer integração programática que precise interceptar a tradução espacial deve inspecionar o source/JAR antes de depender de classes internas.
## 12. Revalidação física — 11/09/2026
A modlist física mantém exatamente `waystonessable-1.0.7.jar`, mod id `waystonessable`, versão `1.0.7`, com Waystones `21.1.44` e Sable `2.0.5`. A file list oficial continua apontando 1.0.7 como latest release NeoForge 1.21.1, publicada em 12/08/2026.
A divisão de authority permanece inalterada: Waystones decide destination/teleport; Sable decide SubLevel/transforms; a bridge traduz validation, distance e sync. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum main↔SubLevel/SubLevel↔SubLevel teleport, assembly, movement, unload/reload ou multiplayer test foi executado nesta recatalogação.
## 13. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `waystonessable-1.0.7.jar`, versão `1.0.7`, com Waystones `21.1.44` e Sable `2.0.5`. A 1.0.7 continua a latest release NeoForge 1.21.1 localizada; seu changelog exato registra apenas `fix Twinbound issue`, sem detalhe suficiente para inferir a implementação afetada. O boundary permanece Waystones = destination/teleport, Sable = SubLevel/transforms, bridge = tradução espacial/validation/distance/sync. Nenhum main↔SubLevel, SubLevel↔SubLevel, assembly, movement, unload/reload ou multiplayer test foi executado nesta revalidação.
## 14. Reconciliação física — 27/09/2026
A bridge permanece `waystonessable-1.0.7.jar`, mod id `waystonessable`, runtime `1.0.7`, com Sable `2.0.5`. O provider Waystones instalado avançou para `21.1.45`; a bridge continua sendo apenas a tradução espacial/validation/distance/sync entre Waystones e Sable. As referências de 08/09–13/09 a Waystones `21.1.44` foram preservadas como snapshots históricos. Nenhum main↔SubLevel, SubLevel↔SubLevel, assembly, movement, unload/reload ou multiplayer test foi executado nesta reconciliação.

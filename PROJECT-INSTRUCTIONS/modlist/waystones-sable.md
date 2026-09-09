# Waystones: Sable

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81619583c83829ce7e8a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Waystones: Sable
- **Arquivo JAR:** `waystonessable-1.0.7.jar`
- **Versão 1.21.1:** `1.0.7`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; spatial transform, distance, validation, sync e lifecycle catalogados.
- **Categoria:** Compat; QoL; Tecnologia
- **Compatibilidade/Riscos:** Riscos: coordinate drift, destination stale após assembly/unload, double-conversion de distância, client/server desync e bypass indevido de custos/visibility/permissions. Provider-specific fallback deve ser fail-closed.
- **Decisão:** Sem decisão
- **Dependências:** Waystones 21.1.44 + Sable 2.0.5 no snapshot físico atual; bridge 1.0.7 Client & Server.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/waystones-sable
- **Função:** Bridge Waystones↔Sable para SubLevels móveis: corrige teleportation, destination validation, distance calculation, client sync e transformação espacial sem substituir a rede Waystones nem a física Sable.
- **Histórico da decisão:** vazio
- **Observações:** Mod id `waystonessable`, runtime 1.0.7. Waystones é authority de destino/teleporte; Sable é authority de SubLevel/transforms; a bridge apenas traduz entre ambos.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Waystones:Sable 1.0.7 + Guia Tecnologia atual.
- **Sobreposição:** Não substitui Waystones nem Sable. É compatibilidade espacial específica; não deve coexistir com uma segunda bridge que faça a mesma tradução sem deduplicação.
- **Data da última decisão:** 2026-09-09

> 🗿 **ESCOPO CANÔNICO.** Runtime físico: `waystonessable-1.0.7.jar`, mod id `waystonessable`, versão `1.0.7`. Esta é a bridge específica **Waystones ↔ Sable** para SubLevels móveis. Waystones continua authority da rede/destinos de teleporte; Sable continua authority das transforms/sublevels; esta integração apenas traduz validação, distância, sincronização e coordenadas entre os dois domínios.

## 1. Identidade e stack atual
- **Waystones:Sable:** 1.0.7, Release NeoForge 1.21.1.
- **Waystones:** 21.1.44 no pack.
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
- [ ] Dedicated server inicia com Waystones 21.1.44 + Sable 2.0.5 + bridge 1.0.7.
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

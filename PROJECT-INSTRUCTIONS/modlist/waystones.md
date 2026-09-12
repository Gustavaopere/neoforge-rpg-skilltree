# Waystones

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a9aefbcfc95945ab22
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Waystones
- **Arquivo JAR:** `waystones-neoforge-1.21.1-21.1.44.jar`
- **Versão 1.21.1:** `21.1.44`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; network authority, visibility, worldgen, Sable/JourneyMap e lifecycle catalogados.
- **Categoria:** QoL; Exploração
- **Compatibilidade/Riscos:** Validar config real de costs/cooldowns/visibility/worldgen, teleport permissions, break/Silk Touch index, multiplayer e coordinate transforms Sable. 21.1.44 corrige visibility/index e feature-cycle worldgen. Marker JourneyMap não é teleport authority.
- **Decisão:** Sem decisão
- **Dependências:** Balm 21.0.65 no pack. Bridges atuais: Waystones:Sable 1.0.7; JourneyMap Integration 1.9 com JourneyMap 6.0.7.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/waystones
- **Função:** Rede persistente de destinos de teleporte descobertos/construídos, com activation, visibility, costs/cooldowns/restrições configuráveis e worldgen opcional.
- **Histórico da decisão:** vazio
- **Observações:** Mod id `waystones`, runtime 21.1.44. Waystones é authority da rede/destinos; Waystones:Sable traduz sublevels e JMI apenas apresenta markers. Referências 21.1.41/21.1.42 são históricas.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Waystones 21.1.44 + Guias Gameplay/Tecnologia atuais.
- **Sobreposição:** Compartilha finalidade com outros teleports/portais, mas sua rede persistente é própria. JourneyMap/JMI e Waystones:Sable são bridges/UI, não sistemas concorrentes.
- **Data da última decisão:** 2026-09-09

> 🗿 **ESCOPO CANÔNICO.** Runtime físico: `waystones-neoforge-1.21.1-21.1.44.jar`, mod id `waystones`, versão `21.1.44`. Waystones é a **authority da rede persistente de destinos de teleporte**; JourneyMap Integration e Waystones:Sable apenas expõem/adaptam essa rede em outros espaços/UI.

## 1. Identidade e versão
- **Mod:** Waystones.
- **Build:** 21.1.44 para NeoForge 1.21.1.
- **Dependência:** Balm; pack atual possui Balm 21.0.65.
- **Estado:** Release física atual, substituindo referências históricas 21.1.41/21.1.42.

A linha 21.1.44 inclui correções de visibility/index de waystones e um fix de worldgen registration relacionado a feature-cycle, além de ajustes de animação na release atual.

## 2. Authority e state
Waystones controla:
- registro/identidade de waystones;
- ativação/descoberta conforme regras do servidor;
- lista de destinos acessíveis;
- teleport request/validation;
- custos/cooldowns/restrições configuráveis;
- geração natural quando habilitada.

Mods externos não devem manter uma segunda lista autoritativa de destinos nem teleportar ignorando a validação do provider.

## 3. Ativação e visibilidade
Uma waystone ativada pode entrar na rede/lista do jogador/equipe/global conforme configuração e tipo de visibilidade.

A 21.1.44 corrige comportamentos de visibilidade/index em cenários envolvendo default global visibility e waystones obtidas com Silk Touch.

Para integrações próprias:
- não inferir “descoberta” apenas porque um bloco foi renderizado no mapa;
- consultar state real do provider quando uma quest exigir activation;
- activation deve ser deduplicável e server-authoritative.

## 4. Teleporte e configuração
Custos, cooldowns, requisitos, distância e restrições dimensionais são configuráveis. O dossiê não fixa defaults numéricos porque a config efetiva do pack não foi lida nesta etapa.

Uma perk/quest não deve bypassar:
- custo configurado;
- cooldown;
- dimension restriction;
- permission/ownership/visibility;
- destination validity.

Se o provider negar teleport, integração externa deve falhar fechada.

## 5. Worldgen
Waystones pode gerar estruturas/blocos naturalmente conforme configuração. A 21.1.44 inclui correção para registro repetido de worldgen features que podia gerar feature-cycle crash.

Worldgen é server-authoritative. Update do mod não deve retroativamente duplicar waystones em chunks já gerados.

## 6. Waystones:Sable 1.0.7
A modlist física contém `waystonessable-1.0.7.jar`.

Essa bridge adapta Waystones a **SubLevels móveis Sable/Aeronautics**, incluindo validação de destino, cálculo de distância, client/server sync e transformação de coordenadas quando origem/destino está dentro de contraption/sublevel.

Boundary:
- Waystones mantém authority do destino/teleport;
- Sable mantém authority das transforms/sublevel;
- Waystones:Sable traduz coordenadas/validation entre os dois.

Não duplicar teleport por código próprio quando a bridge já processa o caso.

## 7. JourneyMap Integration
O pack possui **JourneyMap 6.0.7** e **JourneyMap Integration 1.9**. JMI pode expor destinos/markers Waystones na UI do mapa.

Marker não é teleport authority. Apagar/mostrar marker não deve criar/remover waystone nem alterar activation state por inferência.

## 8. Multiplayer e permissions
Waystones podem ter visibilidade pessoal/equipe/global conforme config e modo de ativação.

Testar:
- dois jogadores com listas diferentes;
- teams/permissions quando configurados;
- shared/global destination;
- rename/remove/break;
- teleport simultâneo;
- player reconnect;
- waystone em chunk descarregado.

## 9. Lifecycle
- placement/activation;
- rename/visibility change;
- break/silk touch conforme comportamento atual;
- save/restart;
- relog;
- dimension change;
- destination chunk unload;
- worldgen em chunks novos;
- Sable assembly/disassembly;
- movement de sublevel com waystone;
- JourneyMap marker refresh.

## 10. Boundary para Mastery/quests
Uma ativação/discovery pode ser milestone somente quando o provider expõe evento/state causal confiável.

Não conceder Mastery por:
- teleport por tick;
- permanecer perto da waystone;
- reabrir GUI;
- recarregar marker JourneyMap;
- assemble/disassemble repetido de sublevel;
- reativar o mesmo destino por reload.

Se não houver hook deduplicável para descoberta, integração fica **fail-closed**.

## 11. Riscos
1. **Teleport bypass:** integração externa ignorando custos/restrições.
2. **Duplicate destination:** state duplicado após break/place/reload.
3. **Visibility leak:** waystone privada aparecendo como global por bridge/UI.
4. **Worldgen duplication/cycle:** regressão de feature registration.
5. **Sable coordinate drift:** destino movido em sublevel apontando para coordenada antiga.
6. **Cross-dimension validation:** destino inválido ou unload durante teleport.
7. **Marker vs authority:** JourneyMap exibindo state stale sem alterar provider real.

## 12. Matriz de testes
- [ ] Dedicated server inicia com Waystones 21.1.44 + Balm 21.0.65.
- [ ] Colocar/ativar waystone cria um único destino.
- [ ] Relog/restart preserva activation/lista.
- [ ] Visibility pessoal/equipe/global respeita config.
- [ ] Silk Touch/break não deixa índice stale.
- [ ] Teleport cobra custo/aplica cooldown conforme config real.
- [ ] Restrição dimensional/permission bloqueia sem mutation parcial.
- [ ] Worldgen de chunks novos não gera feature-cycle crash.
- [ ] Waystones:Sable mantém destination correto após assemble/move/disassemble.
- [ ] Teleport de/para sublevel usa transform correto e não duplica player/state.
- [ ] JourneyMap/JMI mostra marker sem alterar activation/ownership.
- [ ] Dois jogadores teleportando simultaneamente não corrompem state.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências
- **Modlist física 08/09/2026:** Waystones 21.1.44, Balm 21.0.65, Waystones:Sable 1.0.7, JourneyMap 6.0.7 e JMI 1.9.
- CurseForge oficial Waystones: rede persistente de teleporte, config, release 21.1.44 e changelog atual.
- Guia Gameplay/Tecnologia: Waystones network, JourneyMap integration e Waystones:Sable.

## 14. Limitação
Config efetiva de costs/cooldowns/visibility/worldgen não foi lida e runtime QA não foi executado. Integrações de quest/perk devem consultar hooks/state reais da 21.1.44 antes de assumir discovery ou teleport success.

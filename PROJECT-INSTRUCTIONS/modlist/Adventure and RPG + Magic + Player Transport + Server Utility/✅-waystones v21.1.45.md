# Waystones

> **Autoridade física atual — 27/09/2026.** Ordem física **#564**: JAR `waystones-neoforge-1.21.1-21.1.45.jar`, mod id `waystones`, runtime `21.1.45`, SHA-1 `6ec1a176a102212db4f6391886db52ed103c830f`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Waystones
- **Arquivo JAR:** `waystones-neoforge-1.21.1-21.1.45.jar`
- **Versão 1.21.1:** 21.1.45
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Exploração
- **Função:** Rede persistente de destinos de teleporte descobertos/construídos, com activation, visibility, costs/cooldowns/restrições configuráveis e worldgen opcional.
- **Dependências:** Balm 21.0.65 no pack. Bridges atuais: Waystones:Sable 1.0.7; JourneyMap Integration 1.9 com JourneyMap 6.0.8.
- **Sobreposição:** Compartilha finalidade com outros teleports/portais, mas sua rede persistente é própria. JourneyMap/JMI e Waystones:Sable são bridges/UI, não sistemas concorrentes.
- **Compatibilidade/Riscos:** Rede persistente de teleporte com riscos de destination state, visibility, costs/cooldowns, permission gates, worldgen, cross-dimension e bridges Sable/JourneyMap. Upstream 21.1.46 adiciona comandos administrativos de cooldown e corrige três regressões de warp scroll, portanto atualizar exige testar custo/requisito/activation state de scrolls e comandos.
- **Observações:** Runtime físico permanece Waystones 21.1.45. A release **21.1.46** para NeoForge 1.21.1 foi publicada em 25/09/2026 e não está instalada.
- **Procedência:** modlist física atual + CurseForge oficial Waystones 21.1.45/21.1.46 + histórico 21.1.44 já preservado. A autoridade física continua `waystones-neoforge-1.21.1-21.1.45.jar`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/waystones
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Waystones físico permanece 21.1.45. A release **21.1.46** foi revisada integralmente e registrada abaixo.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🗿 **ESCOPO CANÔNICO.** Runtime físico: `waystones-neoforge-1.21.1-21.1.45.jar`, mod id `waystones`, versão `21.1.45`. Waystones é a **authority da rede persistente de destinos de teleporte**; JourneyMap Integration e Waystones:Sable apenas expõem/adaptam essa rede em outros espaços/UI.
## 1. Identidade e versão
- **Mod:** Waystones.
- **Build:** 21.1.45 para NeoForge 1.21.1.
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
O pack possui **JourneyMap 6.0.8** e **JourneyMap Integration 1.9**. JMI pode expor destinos/markers Waystones na UI do mapa.
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
- [ ] Dedicated server inicia com Waystones 21.1.45 + Balm 21.0.65.
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
- CurseForge oficial Waystones: rede persistente de teleporte, config e release instalada 21.1.45; changelog 21.1.45 registra correção de crash ao ativar waystone em multiplayer.
- Guia Gameplay/Tecnologia: Waystones network, JourneyMap integration e Waystones:Sable.
## 14. Limitação
Config efetiva de costs/cooldowns/visibility/worldgen não foi lida e runtime QA não foi executado. Integrações de quest/perk devem consultar hooks/state reais da 21.1.45 antes de assumir discovery ou teleport success.
## 15. Revalidação física — 11/09/2026
A modlist física mantém exatamente `waystones-neoforge-1.21.1-21.1.44.jar`, mod id `waystones`, versão `21.1.44`. A file list oficial confirma essa mesma build como latest release NeoForge 1.21.1 em 08/09/2026.
Os fixes exatos de 21.1.44 — activation visibility com default global, índices global/team após Silk Touch, waystones unseen/unnamed, feature-cycle de worldgen e animações choppy — permanecem relevantes. Balm `21.0.65`, Waystones:Sable `1.0.7`, JourneyMap `6.0.7` e JMI `1.9` continuam presentes. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum config/teleport/worldgen/Sable/multiplayer test foi executado nesta recatalogação.
## 16. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `waystones-neoforge-1.21.1-21.1.44.jar`, versão `21.1.44`, e esta continua a latest release NeoForge 1.21.1 localizada. Permanecem confirmados os fixes da 21.1.44 para visibility/activation com default global, índices global/team após Silk Touch, waystones unseen/unnamed, registro repetido de worldgen features causando feature-cycle e animações choppy. Balm `21.0.65`, Waystones:Sable `1.0.7`, JourneyMap `6.0.7` e JMI `1.9` continuam presentes. Nenhum config, teleport, worldgen, Sable ou multiplayer test foi executado nesta revalidação.
## 17. Reconciliação física e upstream — 27/09/2026
A autoridade física atual contém `waystones-neoforge-1.21.1-21.1.45.jar`, mod id `waystones`, runtime `21.1.45`, com Balm `21.0.65`, Waystones:Sable `1.0.7`, JourneyMap `6.0.8` e JourneyMap Integration `1.9`. A release oficial 21.1.45, publicada em 13/09/2026, corrige crash ao ativar waystone em multiplayer. A 21.1.46 foi publicada posteriormente e existe upstream, mas não está instalada; por hierarquia de autoridade, o dossiê operacional permanece pinado ao runtime físico 21.1.45. As seções de 08/09–13/09 sobre Waystones 21.1.44 e JourneyMap 6.0.7 permanecem como histórico datado. Nenhum config, teleport, worldgen, Sable ou multiplayer test foi executado nesta reconciliação.


## 18. Atualização upstream 21.1.46 — não instalada
A autoridade física continua em **Waystones 21.1.45**. A próxima release 1.21.1 é **21.1.46**, publicada em 25/09/2026.

Changelog oficial:
- adiciona `/waystones cooldown <targets> add <identifier> <seconds>`;
- adiciona `/waystones cooldown <targets> set <identifier> <seconds>`;
- corrige bound scrolls aparecendo como **invalid** para jogadores que ainda não ativaram o destino;
- adiciona a mensagem de erro ausente quando um **warp requirement** impede teleport por scroll;
- corrige scrolls cobrando **XP quando não deveriam**.

Impacto: os comandos novos permitem mutação administrativa explícita de cooldown; integrações externas devem observar o state final do provider, não manter um segundo cooldown paralelo. Os fixes de scroll tocam activation/requirements/cost settlement e precisam permanecer exactly-once no servidor.

Gate de promoção 21.1.45→21.1.46: cooldown add/set em um e vários jogadores; identifiers simultâneos; relog/restart; bound scroll para destino ativado e não ativado; warp requirement permitido/bloqueado com mensagem; XP cost zero/não-zero; cross-dimension; Waystones:Sable; JourneyMap marker sem alterar authority.

Fonte upstream: CurseForge file ID 8969751, `waystones-neoforge-1.21.1-21.1.46.jar`.
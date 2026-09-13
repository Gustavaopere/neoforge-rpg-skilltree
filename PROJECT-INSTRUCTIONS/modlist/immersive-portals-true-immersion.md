# Immersive Portals: True Immersion

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c1818ce631cf60c3a2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Immersive Portals: True Immersion
- **Arquivo JAR:** `immersive_portals_true_immersion-2.0.4.jar`
- **Versão 1.21.1:** 2.0.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Opcional
- **Categoria:** Compat, Visual, QoL
- **Função:** Addon de Immersive Portals que amplia a continuidade de interação através de portais, permitindo mais interações entre os dois lados sem substituir o core de portal.
- **Dependências:** Requer funcionalmente uma implementação compatível de Immersive Portals. No pack, o provider é `immersive_portals_core` 6.0.7 fornecido por Immersive Aeronautics 1.1.4. CurseForge não declara related-project dependency formal para a release 2.0.4.
- **Sobreposição:** Complementa o core com interação cross-portal. Não substitui Immersive Aeronautics/Immersive Portals nem o addon de Portal Spell do Iron's. Escopos podem se encontrar em raycast/interactions e exigem teste conjunto.
- **Compatibilidade/Riscos:** Addon de portal interaction com source limitado. Riscos: mixin/API drift, client/server disagreement sobre alvo através do portal, reach/raycast validation, duplicate interaction, stale portal transform e compatibilidade não comprovada com o rewrite Immersive Aeronautics 1.1.4/core 6.0.7.
- **Observações:** Runtime físico `immersive_portals_full_immersion` 2.0.4. O upstream só confirma genericamente mais interações através de portais; tipos específicos de interação não são inventados. A compatibilidade com o rewrite Aeronautics deve ser validada operacionalmente.
- **Procedência:** modlist.txt física atual + CurseForge oficial True Immersion 2.0.4 file 8600736, Release NeoForge 1.21.1 de 08/08/2026 + descrição oficial de more interactions through portals; source exato e changelog 2.0.4 não localizados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/immersive-portals-true-immersion/files/8600736
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — True Immersion 2.0.4 release-pinned; through-portal interaction scope, rewrite-core 6.0.7 boundary, client/server authority, lifecycle/multiplayer, compat risks e testes catalogados; source/changelog exatos indisponíveis.
- **Histórico da decisão:** Tratado como addon opcional de imersão, não como substituto do fork/rewrite de Immersive Portals. A presença e release 2.0.4 estão verificadas; a compatibilidade específica com Immersive Aeronautics permanece como teste operacional separado.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `immersive_portals_true_immersion-2.0.4.jar`, mod id `immersive_portals_full_immersion`, versão `2.0.4`. O CurseForge oficial confirma a release NeoForge 1.21.1 file 8600736 de 08/08/2026. O escopo público confirmado é ampliar **interações através de portais**; source exato e changelog da 2.0.4 não foram localizados, portanto detalhes granulares permanecem fail-closed.

## 1. Papel e authority
True Immersion é addon funcional de Immersive Portals. O core continua authority da geometria/linkage do portal, destination e entity transfer; o addon amplia a camada de **interaction through portal**. Ele não deve manter segundo portal state nem substituir a implementação-base.

## 2. Provider real no pack
O pack não usa um Immersive Portals upstream top-level separado. O provider é `immersive_portals_core` 6.0.7 embutido funcionalmente no rewrite **Immersive Aeronautics 1.1.4**. True Immersion precisa ser validado contra essa implementação específica, não apenas contra o mesmo mod id/version nominal.

## 3. Escopo público limitado
A descrição oficial afirma apenas que o mod melhora os aspectos imersivos ao permitir **mais interações através de portais**. Sem source/changelog exato, esta ficha não inventa se isso cobre block use, entity attack, item use, inventory interaction, redstone ou qualquer outro caso particular. Cada comportamento observado deve ser tratado como runtime evidence.

## 4. Interaction routing
Qualquer interação through-portal precisa resolver origem, portal transform, destination world/sublevel e alvo real. O cliente pode calcular ray/hover para apresentação, mas o servidor deve validar se a ação é permitida, qual entity/block foi atingido e se alcance/contexto são válidos.

## 5. Reach e raycast
Portais alteram a geometria do raycast: distância até o portal e distância transformada no destino não devem permitir reach bypass. Mods de câmera/alcance/combat podem alterar assumptions. Sem código exato, não assumir algoritmo; o teste deve verificar que interação remota não excede regras authoritative do servidor.

## 6. Exactly-once interaction
Uma ação não pode ser processada uma vez no lado de origem e novamente no destino. Packet replay, client prediction ou dois hooks de compatibilidade não devem duplicar block use, damage, item consumption ou outro efeito. O addon deve ser tratado como roteador/bridge, não como segunda authority do gameplay do alvo.

## 7. Portal transform e state
Se o portal muda, fecha, descarrega ou perde destination entre input e processamento, a interação deve falhar ou ser revalidada conforme o core. Referência stale não pode direcionar uma ação para coordenada/world antigo depois de chunk/sublevel unload.

## 8. Client / server
A distribuição é Client & Server. Cliente apresenta seleção/raycast/feedback visual através do portal; servidor precisa validar o alvo e aplicar qualquer consequência de gameplay. Divergência de cálculo entre lados deve resolver a favor do server state, não do client preview.

## 9. Multiplayer
Dois jogadores podem interagir pelo mesmo portal simultaneamente. Target resolution e context devem permanecer por-player; não compartilhar ray/state temporário. Ações concorrentes sobre container/entity/bloco precisam seguir o locking/authority do provider alvo e ocorrer exatamente uma vez.

## 10. Relação com Portal Spell addon
O pack também contém `Immersive Portal - Iron's Spells 'n Spellbooks Addon 1.0.1`, que cria portais a partir do Portal Spell. True Immersion não substitui essa bridge: ele atua depois que um portal existe. Um portal criado pelo spell deve ser testado como entrada para as interações do True Immersion sem criar segundo teleport/interaction path.

## 11. Relação com Sable/Create Aeronautics
Como o core é o rewrite orientado a Sable/Create Aeronautics, portais podem coexistir com sublevels/ships e transforms não vanilla. Interagir através de portal enquanto origem/destino está em estrutura móvel é um edge case de alto risco: o alvo precisa ser resolvido no frame correto e não em coordenadas world stale.

## 12. Lifecycle
Validar client/server boot, world join, portal create/remove, chunk unload, sublevel load/unload, reconnect, dimension change, resource reload e server restart. Estado transitório de interaction/raycast não deve sobreviver a mudança de world ou portal invalidation.

## 13. Maturidade e provenance
A release 2.0.4 é oficial e marcada Release, mas não possui changelog disponível na página do arquivo e não foi localizado source público exato nesta auditoria. `Release` descreve o canal de publicação; não substitui regression testing contra o rewrite do core usado pelo pack.

## 14. Riscos técnicos
- addon esperar internals do Immersive Portals original que divergem no rewrite;
- reach/raycast bypass através do portal;
- interação processada duas vezes;
- client/server escolherem alvos diferentes;
- portal transform stale durante ação;
- ship/sublevel movement alterar target frame;
- mixin target/API drift em update do core;
- conflito com combat/camera/reach mods;
- assumir tipos de interação não publicados pelo upstream.

## 15. Matriz de testes obrigatória
- [ ] Client + dedicated server boot com True Immersion 2.0.4 e core 6.0.7 do Immersive Aeronautics.
- [ ] Interações realmente suportadas pelo runtime atravessam portal e atingem somente o alvo correto.
- [ ] Alvos fora do alcance válido não podem ser acionados através do portal.
- [ ] Uma única ação não produz double-use/double-damage/double-consumption.
- [ ] Fechar/remover portal antes do processamento invalida o target corretamente.
- [ ] Chunk/sublevel unload não deixa reference/raycast stale.
- [ ] Dois jogadores usando o mesmo portal mantêm context independente.
- [ ] Portal criado pelo addon do Iron's funciona com as interações suportadas sem dupe.
- [ ] Portal em/para Sable craft móvel resolve o alvo no frame correto.
- [ ] Reconnect/restart limpa qualquer state transitório.

## 16. Evidências e limites
- **Modlist física:** filename, mod id, runtime 2.0.4 e mixin config `immersive_portals_full_immersion.mixins.json`.
- **CurseForge oficial:** file 8600736, Release NeoForge 1.21.1, Client & Server e descrição geral de mais interações through-portals.
- **Provider físico:** Immersive Aeronautics 1.1.4 expondo `immersive_portals_core` 6.0.7.
- **Limite:** source/changelog exatos não localizados; tipos concretos de interação, packet classes, reach formula e mixin targets não são inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

# Eidolon: Repraised

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819783f8f48dbc9c9a96
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Eidolon: Repraised
- **Arquivo JAR:** `eidolon_repraised-1.21.1-0.5.0.2.jar`
- **Versão 1.21.1:** 0.5.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Sistema mágico ocultista/RPG com progressão documentada pelo Ars Ecclesia, rituais, theurgy de divindades, alchemy/artifice, soul manipulation, equipamentos e curios.
- **Dependências:** Curios é a única dependência obrigatória declarada pelo projeto; o pack instala Curios API 9.5.1+1.21.1. Runtime físico Eidolon Repraised 0.5.0.2. Sable/Aeronautics estão presentes e a release 0.5.0.2 contém correção explícita para partículas em Sable sublevels.
- **Sobreposição:** Sobreposição temática com Ars Nouveau, Iron's Spells, Goety e outros magic mods, mas Eidolon possui progressão, rituals, theurgy, alchemy/artifice e soul mechanics próprios; não é substituto direto dos demais.
- **Compatibilidade/Riscos:** Grande provider mágico com estado/progressão próprios. Riscos: rituals/altar/crucible/soul-enchanter data drift, automação de Crucible em estado intermediário, mana/soul-heart sync, enchant offers, incense/effect cleanup, book barter e sobreposição de progressão/equipamentos com outros magic/RPG providers. Histórico 1.21 contém várias correções nessas superfícies.
- **Observações:** 0.5.0.2 é release NeoForge 1.21.1 de 09/05/2026. Changelog específico: partículas não deslocarem em Sable sublevel/Aeronautics e nova tentativa de corrigir book barter intermitente. O projeto 1.21 também tornou Crucible/Worktable recipes e rituals mais data-driven; altar bonuses são Data Maps.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `eidolon_repraised-1.21.1-0.5.0.2.jar`, mod id `eidolon_repraised`, versão 0.5.0.2 e Curios 9.5.1. CurseForge oficial confirma release 0.5.0.2 e documentação do sistema/linha 1.21.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/eidolon-repraised
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Eidolon Repraised 0.5.0.2; Ars Ecclesia, rituals, theurgy, crucible/worktable, datapackability, soul enchantment, curios, Sable fix, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `eidolon_repraised-1.21.1-0.5.0.2.jar` · mod id `eidolon_repraised` · versão `0.5.0.2` · NeoForge 1.21.1 · Client & Server.

## 1. Papel no modpack
Eidolon: Repraised é um grande provider de **magia ocultista e progressão RPG**, combinando alchemy, theurgy, necromancy/soul manipulation, rituals, itens mágicos, ferramentas, armaduras e curios. A progressão é documentada in-game pelo **Ars Ecclesia**.

## 2. Dependência e authority
O projeto declara **Curios** como única dependência obrigatória; o pack possui Curios API 9.5.1+1.21.1.
- **Eidolon:** progressão, rituals, altars, chants, soul systems, recipes/machines e seus itens/effects.
- **Curios:** slots/equip contract.
- **Sable/Aeronautics:** sublevel/physics quando o jogador está nesses espaços; Eidolon apenas precisa renderizar/sincronizar corretamente dentro deles.

Outros magic mods não devem reaplicar mana, ritual output ou soul effects do Eidolon.

## 3. Ars Ecclesia
O Ars Ecclesia é o livro-guia do mod e contém documentação de features e recipes. A própria página oficial alerta que ele **não se adapta automaticamente a custom recipes ou remoções de recipes**, então modpack authors não devem tratar cada página do livro como prova de que um recipe customizado continua válido após alterações externas.

## 4. Rituals
Rituals usam soul energy/shards e um brazier para executar operações poderosas. A documentação oficial descreve usos como:
- crafting/conversão;
- summoning de mobs;
- wards/efeitos de área;
- execução de outras ações definidas pelo ritual.

Na linha 1.21, rituals tornaram-se **datapackable**, podendo ser modificados ou adicionados para crafting, summoning ou execução de comandos. Isso torna IDs/inputs/outputs uma superfície de dados, não algo a hardcodar fora do provider.

## 5. Theurgy
O sistema de theurgy usa altar, oração, sacrifícios e reputação com divindades/lados dark/light. Progressão pode revelar conhecimento e conceder abilities.

Altar bonuses na linha 1.21 foram movidos para **Data Maps**, facilitando override/extensão por datapacks. Integração própria deve usar essa camada de dados em vez de duplicar cálculos de altar.

## 6. Artifice e Crucible
O Crucible é a estação de alchemy do mod. Na linha 1.21:
- recipes de Crucible/Worktable são datapackable;
- Crucible pode interagir com hoppers e automação similar;
- itens lançados dentro podem ser coletados imediatamente;
- o inventory interno é válido para o step atual;
- redstone tick pode stir o Crucible;
- Observer pode emitir tick quando o step muda;
- blocos quentes podem ser definidos via tag `eidolon:crucible_hot_blocks`.

A própria documentação histórica alerta que recovery de inventário durante steps é uma superfície delicada. Automação precisa ser testada contra dupe/loss e transição de steps.

## 7. Worktable / Scriptorium
O sistema de artifice inclui worktable para criação/tuning de itens mágicos. A linha 1.21 também possui Scriptorium/UI e recebeu correções de drops/UI em builds anteriores da mesma linha.

Não inventar recipes/slots específicos sem consultar os dados da build atual; a ficha trata o subsystem e seus riscos operacionais.

## 8. Soul Enchanter
O mod possui Soul Enchanter como superfície própria de encantamento. A linha 1.21 recebeu correções históricas para render do black book e para offers muito baixos.

Regression gate: offers, black book rendering, save/reload e interação com outros sistemas de enchanting. O Soul Enchanter não deve ser fundido logicamente com enchanting vanilla/Apothic sem uma compat explícita.

## 9. Chants
Chants fazem parte da magia/theurgy do mod. A linha atual suporta config de custo/cooldown e recipes/conversions associados; builds 1.21 corrigiram conversion chants e adicionaram bulk conversion para dark/light touch chants.

Chant execution precisa ser server-authoritative e idempotente; um client animation/sound não é prova de que a conversão foi commitada.

## 10. Incenses e effects
O mod possui incenses e status/effects próprios. A linha 1.21 recebeu correções de cleanup de effect icon após Purity Incense e de comportamento oposto entre undead/non-undead para determinados incenses.

Esses fixes históricos viram regression gates, não alegação de bug atual.

## 11. Equipamentos e Curios
O projeto oferece armas, ferramentas, armor e curios mágicos. Curios controla slots/equip lifecycle; Eidolon controla o efeito/modifier do item.

Equip/unequip, death/respawn e dimension change precisam limpar/reaplicar modifiers exatamente uma vez.

## 12. Progressão e sync
O histórico 1.21 contém correções para progression points, mana e soul hearts sync. Isso demonstra que progressão/resource synchronization é uma superfície real e sensível.

Servidor deve ser authority de unlocks, reputação, mana/soul state e progressão; cliente apenas apresenta o estado sincronizado.

## 13. Sable / Aeronautics
A release física **0.5.0.2** corrige partículas deslocadas quando o jogador está em **Sable sublevel (Aeronautics)**. Como Sable/Aeronautics estão presentes no pack, esta não é compat teórica: é uma regressão local importante.

Testar rituals, particles e item/effect visuals dentro e fora de sublevels, incluindo transição capture/release.

## 14. Book barter
O changelog 0.5.0.2 registra outra tentativa de corrigir **book barter às vezes não funcionar**. Isso deve permanecer como regression gate: executar o fluxo várias vezes, relogar/reiniciar e validar settlement exactly-once.

## 15. Datapack surface
A linha 1.21 move várias superfícies para dados:
- Crucible recipes;
- Worktable recipes;
- rituals;
- altar bonuses via Data Maps;
- recipe/conversion-related content.

Qualquer modpack customization deve preservar namespaces/conditions e ser testada com `/reload`; boot sem crash não prova que cada recipe/ritual resolveu.

## 16. Client / Server
- progressão, altar/reputation, rituals, recipes, mana/soul state, effects: server-authoritative;
- particles, models, book UI, tooltips: client-facing;
- networking precisa convergir após reconnect;
- dedicated server não deve carregar render-only classes para processar ritual/recipe state.

## 17. Lifecycle
Validar:
- first unlock/progression;
- prayer/sacrifice/reputation;
- ritual start/complete/fail;
- Crucible step transitions e automation;
- Worktable/Scriptorium interactions;
- Soul Enchanter;
- chants;
- equip/unequip curios;
- death/respawn;
- dimension/sublevel travel;
- chunk unload/reload;
- save/restart;
- datapack reload;
- reconnect multiplayer.

## 18. Multiplayer / idempotência
Ritual output, barter, Crucible recipes, chants e item conversions precisam liquidar exatamente uma vez no servidor. Dois jogadores usando a mesma estação não podem duplicar output ou consumir inputs duas vezes.

Progression/reputation devem permanecer por jogador e não vazar por static/global state indevido.

## 19. Sobreposição com o stack mágico
O pack também possui Ars Nouveau, Iron's Spells, Goety e muitos addons. A sobreposição é temática e de progressão/equipamento, não equivalência técnica automática.

Ao balancear, decidir precedence por recurso: um perk externo pode ler um Eidolon effect, mas não deve recalcular ritual result, altar bonus ou soul enchantment em paralelo.

## 20. Riscos
1. ritual datapack ID/input drift;
2. ritual output duplicado;
3. Crucible automation dupe/loss;
4. step transition/redstone inconsistente;
5. altar bonus Data Map sobrescrito por datapack concorrente;
6. progression/mana/soul sync stale;
7. Soul Enchanter offers incorretos;
8. incense/effect cleanup incorreto;
9. Curios modifier residual;
10. Sable particle offset regressar;
11. book barter falhar ou duplicar settlement;
12. custom recipes divergirem do Ars Ecclesia;
13. magic-stack balance overlap;
14. reload manter data cache antigo.

## 21. Matriz de testes
1. Dedicated server boot com Curios atual.
2. Criar Ars Ecclesia e validar navegação básica.
3. Progredir altar/theurgy e verificar unlock/reputation após relog.
4. Executar rituals de categorias diferentes e reiniciar o mundo.
5. Crucible manual + hopper/redstone/observer sem dupe/loss.
6. Worktable/Scriptorium save/reload.
7. Soul Enchanter: offers e aplicação.
8. Chants simples e bulk conversion.
9. Incenses em undead/non-undead e cleanup.
10. Curios equip/unequip/death/relogin.
11. Sable/Aeronautics sublevel: particles e ritual visuals.
12. Book barter repetido sob latency/reconnect.
13. `/reload` com recipe/ritual datapack de teste.
14. Dois jogadores compartilhando altar/Crucible quando permitido.

**Esta catalogação não afirma que esses testes foram executados.**

## 22. Evidências
- modlist física canônica: JAR/mod id/version + Curios/Sable/Aeronautics presentes;
- CurseForge oficial 0.5.0.2: release NeoForge 1.21.1, Sable particle fix e book barter fix;
- descrição oficial: Ars Ecclesia, Rituals, Theurgy e Artifice;
- changelogs da linha 1.21: datapackable Crucible/Worktable/Rituals, altar Data Maps, automation, progression/sync/Soul Enchanter regressions.

> **Boundary canônico:** Eidolon é authority de sua **progressão ocultista, rituals, theurgy, soul systems e artifice**. Outros magic frameworks coexistem, mas não devem duplicar essas operações.

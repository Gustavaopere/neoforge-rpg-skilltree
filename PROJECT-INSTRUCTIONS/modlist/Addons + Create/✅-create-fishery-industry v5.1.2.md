# Create: Fishery Industry

## Propriedades do registro

- **Mod:** Create: Fishery Industry
- **Arquivo JAR:** `createfisheryindustry-5.1.2.jar`
- **Versão 1.21.1:** `5.1.2`
- **Categoria:** Comida, Automação, Exploração
- **Função:** Adiciona pesca automatizada, mergulho, captura de criaturas e processamento/logística de produtos aquáticos no estilo Create.
- **Dependências:** Create obrigatório; pack físico usa NeoForge 21.1.250 + Create 6.0.10. A 5.1.2 adiciona compatibilidade opcional com Sable; o pack contém Sable 2.0.5, tornando essa integração runtime relevante. Pneumatic Harpoon Gun continua consumindo recurso de backtank/Create.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: entity/loot double-capture; biome loot mismatch; Mesh Trap insert+drop dupe; Smart Nozzle filter/pickup; Peeler output duplication; Harpoon air accounting/force; Sable sublevel pull/anchor coordinate mapping e dimension changes; diving movement stacking; Mechanical Arm/manual extraction race; chunk/contraption replay.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fishery-industry
- **Procedência:** modlist física atual de 20/09/2026 + runtime `createfisheryindustry` 5.1.2 + CurseForge oficial File ID 8863886, Release NeoForge 1.21.1 publicada em 12/09/2026 + changelog oficial 5.1.2. Stack físico relevante: NeoForge 21.1.250, Create 6.0.10 e Sable 2.0.5.
- **Observações:** JAR/mod id/runtime 5.1.2 confirmados. Changelog oficial 5.1.2: compat opcional Sable para pneumatic harpoons; pull de Sable sublevels com força configurável; melhorias de harpoon anchor mapping/dimension changes; dependências NeoForge/Create/Flywheel/Vanillin/Ponder atualizadas; JUnit tests para física Sable. O pack contém Sable 2.0.5.
- **Atualização/Status:** REATUALIZADO EM 20/09/2026 — runtime físico 5.1.2 confirmado e correspondente à latest Release NeoForge 1.21.1 de 12/09/2026. O delta 5.1.2 adiciona compatibilidade opcional com Sable para pneumatic harpoons, suporte a puxar sublevels Sable com força configurável, melhora anchor coordinate mapping/dimension changes, atualiza dependências e adiciona testes JUnit da física Sable.
- **Decisão:** Sem decisão
- **Sobreposição:** Compartilha pesca/comida/fauna aquática com outros mods, mas a superfície própria inclui traps em contraptions, capture/nozzles, harpoon pneumático e processamento. Overlap deve ser comparado por loot/recipe/drop.
- **Data da última decisão:** 2026-09-20

# Dossiê operacional — padrão Alex's Mobs
> 🎣 **Identidade física confirmada:** `createfisheryindustry-5.1.2.jar`, mod id `createfisheryindustry`, runtime `5.1.2`. A release exata é NeoForge 1.21.1, Client & Server, publicada oficialmente em 12/09/2026. O delta 5.1.2 adiciona compatibilidade opcional com Sable para pneumatic harpoons; o pack contém Sable `2.0.5`, portanto essa superfície é runtime-relevante.
## 1. Papel e authority
Create: Fishery Industry adiciona pesca automatizada, captura de criaturas, coleta de itens, processamento de pescado, equipamentos de mergulho e logística em estilo Create. O addon owns traps, loot-generation logic, seus equipamentos e processing; Minecraft/provider de fishing loot continua authority das loot tables consultadas.
## 2. Frame Trap em contraptions
O **Frame Trap** é desenhado para operar em contraptions. Durante movimento, pode capturar peixes e outras criaturas configuráveis que encontra, coletar itens próximos e gerar fishing loot. Isso cria três superfícies distintas que não devem liquidar o mesmo alvo/recurso duas vezes.
## 3. Fishing loot por bioma
A documentação afirma que o Frame Trap gera loot de pesca variando por bioma e imitando recompensas da pesca tradicional. O bioma/loot table efetivamente resolvidos no servidor são authority; JEI/tooltips não substituem o roll real.
## 4. Creature capture
Criaturas capturáveis são configuráveis. Uma entity precisa transicionar de mundo→captura/drop exatamente uma vez. Morte, despawn, teleport ou outro trap concorrente não podem produzir duplicação da mesma entity/drop.
## 5. Trap Nozzle
O **Trap Nozzle** cria air currents para capturar criaturas aéreas, com phantoms citados como exemplo. Alcance e elegibilidade reais devem vir da implementação/config; não assumir que todo mob voador é capturável.
## 6. Smart Mesh
O **Smart Mesh** auxilia logística da cadeia. Sua função deve ser documentada pelo comportamento runtime; filtros/roteamento não podem inventar ou consumir itens fora do inventory state real.
## 7. Mechanical Peeler
O **Mechanical Peeler** separa fish skin de fish steaks. Input deve ser consumido uma vez e outputs produzidos conforme recipe válida. Processamento simultâneo por outra máquina/recipe não pode reaplicar o mesmo stack.
## 8. Diving Leggings
As **Diving Leggings** complementam o equipamento de mergulho Create e concedem super jump debaixo d'água. Movimento precisa ser validado no servidor e coexistir com swimming, effects, Epic Fight/parkour e outros modificadores sem acumular impulso indefinidamente.
## 9. Harpoon e Harpoon Pouch
O addon publica **Harpoon** e **Harpoon Pouch**. Harpoons funcionam de forma semelhante a tridents com menor dano segundo a descrição; a pouch permite lançamento continuado. Ammo/item ownership e retorno/consumo devem seguir o provider sem dupe em troca de dimensão ou pickup concorrente.
## 10. Pneumatic Harpoon Gun
O **Pneumatic Harpoon Gun** é criado por Mechanical Crafting e dispara usando gás comprimido do backtank. Pode puxar criaturas/items em direção ao jogador ou puxar o jogador em direção a blocos. Cada disparo precisa debitar recurso do backtank e spawnar/aplicar uma única ação.
Na **5.1.2**, o changelog oficial adiciona compatibilidade opcional com **Sable** para pneumatic harpoons. O pack contém Sable `2.0.5`, então essa integração é concreta: harpoons passam a poder interagir com sublevels Sable, incluindo suporte a puxá-los com força configurável. A mesma release melhora o mapeamento de coordenadas do anchor e o tratamento de dimension changes. Essas transformações precisam manter a física/transform authority do Sable sem duplicar force application ou resolver coordenadas no espaço errado.
## 11. Backtank integration
O pack possui múltiplas extensões de backtank/jetpack. A Harpoon Gun depende da semântica de ar do Create/backtank compatível, não de qualquer tanque visualmente semelhante. Equip/Curios bridges precisam ser validados para evitar consumo de fonte errada ou ar infinito.
## 12. Mesh Trap terrestre
O **Mesh Trap** captura criaturas pequenas que entram nele e envia seus drops diretamente a containers adjacentes. Capture→drop→insertion é uma transação: se o container rejeita, remainder/drop deve seguir uma única política sem duplicar loot.
## 13. Smart Nozzle
O **Smart Nozzle** coleta itens remotamente por air current e oferece filter. Diferentemente de Trap/standard nozzles, a documentação diz que só afeta items, não jogadores/criaturas. Entity-item pickup deve ser server-authoritative e respeitar filters atuais.
## 14. Mechanical Arm e extração manual
A maioria dos blocos do mod suporta extração manual por right-click e interação direta com Mechanical Arms. Dois consumers concorrentes não podem retirar o mesmo stack. Capability/handler real do bloco é authority de extract simulation/commit.
## 15. Configuração
Tipos de criatura capturáveis e outras políticas são configuráveis. Alterar config pode mudar elegibilidade de entity/loot; cache ou contraption já montada não deve continuar usando lista antiga indefinidamente.
## 16. Client/server
Capture, loot roll, item insertion, processing, harpoon physics/damage e air consumption são server-authoritative. Models, particles e animation são client-facing. Cliente não pode forçar captura ou loot apenas por colisão visual.
## 17. Chunk/contraption lifecycle
Frame Trap em contraption pode atravessar chunk boundaries rapidamente. Entidade alvo e trap precisam estar no mesmo state authoritative no tick de captura. Unload/reload/restart não pode repetir captura já liquidada.
## 18. Sobreposição
O pack possui outros conteúdos aquáticos, pesca e comida. Fishery Industry se distingue por cadeia integrada de traps móveis, captura de entidades, nozzle, harpoon e processamento. Overlap deve ser comparado por loot/recipe/drop, não pelo tema “pesca”.
## 19. Riscos
1. Frame Trap captura a mesma entity duas vezes.
2. Fishing loot é gerado duas vezes por movement tick/chunk boundary.
3. Loot de bioma incorreto ou bypass de condições de fishing table.
4. Mesh Trap dropa e insere simultaneamente.
5. Mechanical Peeler duplica outputs.
6. Smart Nozzle coleta item ignorando filter ou duas vezes.
7. Harpoon Gun não debita backtank corretamente.
8. Harpoon force duplica/teleporta player/entity ou aplica força duas vezes em Sable sublevels.
9. Anchor mapping usa espaço/dimensão incorretos após mudança de dimensão/sublevel.
10. Diving Leggings acumulam movimento com outro mod.
11. Manual extract + Mechanical Arm extraem o mesmo stack.
12. Config muda mas contraption mantém eligibility stale.
## 20. Matriz de testes
- [ ] Dedicated server inicia com Fishery Industry 5.1.2 + Create 6.0.10 + Sable 2.0.5.
- [ ] Frame Trap captura uma entity uma única vez em contraption.
- [ ] Fishing loot varia conforme bioma/loot table real.
- [ ] Item collection não duplica entities/items.
- [ ] Trap Nozzle captura apenas alvos configurados.
- [ ] Mechanical Peeler conserva input/output.
- [ ] Diving Leggings funcionam underwater sem impulso residual.
- [ ] Harpoon/Pouch mantêm ammo state após pickup/relog.
- [ ] Pneumatic Harpoon Gun consome ar exatamente uma vez por disparo.
- [ ] Pneumatic Harpoon + Sable puxa sublevel uma única vez com força configurada e sem force duplication.
- [ ] Harpoon anchor conserva coordinate-space correto em Sable sublevel e após dimension change.
- [ ] Mesh Trap lida com container cheio sem dupe/loss.
- [ ] Smart Nozzle respeita filters e só afeta items.
- [ ] Mechanical Arm + right-click concorrentes mantêm inventory conservation.
- [ ] Chunk unload/restart não repete captura/loot.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 21. Evidências e limites
A modlist física confirma JAR/mod id/runtime 5.1.2. A publicação oficial 5.1.2 (File ID `8863886`, 12/09/2026) confirma a release NeoForge 1.21.1 e registra: compat opcional Sable para pneumatic harpoons; pull de Sable sublevels com força configurável; melhorias de harpoon anchor coordinate mapping/dimension changes; atualização de NeoForge, Create, Flywheel, Vanillin e Ponder; e testes JUnit para Sable harpoon physics. As features gerais de Frame Trap, biome fishing loot, nozzles, Mechanical Peeler, diving/harpoon stack e Mechanical Arm interaction permanecem parte do dossiê. Internals não publicados continuam fail-closed.
> 🔒 **Boundary canônico:** Fishery Industry pode automatizar captura e fishing loot, mas cada entity, item, loot roll e recurso pneumático deve ser liquidado exatamente uma vez no servidor.

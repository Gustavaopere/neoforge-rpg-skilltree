# Create: Fishery Industry

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8188ab05cb35dffffbd2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Fishery Industry
- **Arquivo JAR:** `createfisheryindustry-5.1.1.jar`
- **Versão 1.21.1:** 5.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Automação, Exploração
- **Função:** Adiciona pesca automatizada, mergulho, captura de criaturas e processamento/logística de produtos aquáticos no estilo Create.
- **Dependências:** Create obrigatório; build 5.1.1 é NeoForge 1.21.1 Client & Server. Pneumatic Harpoon Gun usa recurso de backtank/Create; pack possui múltiplas bridges de backtank que exigem regression test.
- **Sobreposição:** Compartilha pesca/comida/fauna aquática com outros mods, mas a superfície própria inclui traps em contraptions, capture/nozzles, harpoon pneumático e processamento. Overlap deve ser comparado por loot/recipe/drop.
- **Compatibilidade/Riscos:** Riscos: entity/loot double-capture; biome loot mismatch; Mesh Trap insert+drop dupe; Smart Nozzle filter/pickup; Peeler output duplication; Harpoon air accounting/force; diving movement stacking; Mechanical Arm/manual extraction race; chunk/contraption replay.
- **Observações:** JAR/mod id/runtime 5.1.1 confirmados. Página oficial documenta Frame Trap, fishing loot por bioma, Trap Nozzle, Smart Mesh, Mechanical Peeler, Diving Leggings, Harpoon/Pouch, Pneumatic Harpoon Gun, Mesh Trap e Smart Nozzle. Source head consultado ainda declara 5.0.0.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + publicação oficial Fishery Industry 5.1.1 + repositório adonis-baffin/CreateFisheryIndustry usado apenas como arquitetura por estar em versão 5.0.0 no gradle consultado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-fishery-industry
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 5.1.1 com Frame/Mesh Traps, biome fishing loot, Trap/Smart Nozzles, Mechanical Peeler, diving/harpoon stack, backtank resource e contraption lifecycle catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🎣 **Identidade física confirmada:** `createfisheryindustry-5.1.1.jar`, mod id `createfisheryindustry`, runtime `5.1.1`. A release exata é NeoForge 1.21.1, Client & Server. O repositório público consultado ainda declara 5.0.0 em `gradle.properties`, portanto sua implementação não é tratada como equivalência binária para 5.1.1.

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
8. Harpoon force duplica/teleporta player/entity.
9. Diving Leggings acumulam movimento com outro mod.
10. Manual extract + Mechanical Arm extraem o mesmo stack.
11. Config muda mas contraption mantém eligibility stale.

## 20. Matriz de testes
- [ ] Dedicated server inicia com Fishery Industry 5.1.1 + Create 6.0.10.
- [ ] Frame Trap captura uma entity uma única vez em contraption.
- [ ] Fishing loot varia conforme bioma/loot table real.
- [ ] Item collection não duplica entities/items.
- [ ] Trap Nozzle captura apenas alvos configurados.
- [ ] Mechanical Peeler conserva input/output.
- [ ] Diving Leggings funcionam underwater sem impulso residual.
- [ ] Harpoon/Pouch mantêm ammo state após pickup/relog.
- [ ] Pneumatic Harpoon Gun consome ar exatamente uma vez por disparo.
- [ ] Mesh Trap lida com container cheio sem dupe/loss.
- [ ] Smart Nozzle respeita filters e só afeta items.
- [ ] Mechanical Arm + right-click concorrentes mantêm inventory conservation.
- [ ] Chunk unload/restart não repete captura/loot.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 21. Evidências e limites
A modlist física confirma JAR/mod id/runtime 5.1.1. A página oficial 5.1.1 documenta Frame Trap, biome fishing loot, Trap Nozzle, Smart Mesh, Mechanical Peeler, Diving Leggings, Harpoons, Pneumatic Harpoon Gun, Mesh Trap, Smart Nozzle e Mechanical Arm interaction. O source público consultado está em 5.0.0, portanto internals específicos de 5.1.1 ficam fail-closed.

> 🔒 **Boundary canônico:** Fishery Industry pode automatizar captura e fishing loot, mas cada entity, item, loot roll e recurso pneumático deve ser liquidado exatamente uma vez no servidor.

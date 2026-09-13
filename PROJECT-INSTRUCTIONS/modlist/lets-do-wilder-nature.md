# [Let's Do] Wilder Nature

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db812a8b58f8a487718666
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** [Let's Do] Wilder Nature
- **Arquivo JAR:** `letsdo-wildernature-neoforge-1.1.5.jar`
- **Versão 1.21.1:** 1.1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs, Worldgen, Exploração, Comida
- **Função:** Reintroduz fauna do WilderNature com modelos retrabalhados e conteúdo associado, ampliando animais, exploração e elementos naturais do mundo.
- **Dependências:** Required upstream e fisicamente presentes: Architectury 13.0.11 + Curios 9.5.1+1.21.1. Farm & Charm 1.1.23 possui compat explícita na 1.1.5, mas não é tratado como hard dependency universal.
- **Sobreposição:** Sobreposição de categoria com outros mods de fauna, mas possui conjunto de animais e conteúdo próprio; não é duplicata integral.
- **Compatibilidade/Riscos:** Grande overhaul de fauna/AI. Riscos: spawn density, pathfinding, mobGriefing, mob item-storage dupe/loss, trust persistence, Farm & Charm interactions e sobreposição ecológica. CurseForge marca NeoForge 1.1.5 Release; Modrinth marca Beta devido ao volume de mudanças.
- **Observações:** JAR físico `letsdo-wildernature-neoforge-1.1.5.jar`, mod id `wildernature`, runtime 1.1.5. Documentação principal ainda diz Bounty Board temporariamente disabled em 1.21.1, enquanto changelog 1.1.5 descreve o sistema; runtime test obrigatório antes de tratá-lo como ativo.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais [Let's Do] WilderNature 1.1.5.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-wildernature
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; AI/trust/herds, caches/storage, termites/world interaction, hunting, Farm & Charm compat, bounty ambiguity e tests catalogados para 1.1.5.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🦒 **ESCOPO CANÔNICO.** Runtime físico: `letsdo-wildernature-neoforge-1.1.5.jar`, mod id `wildernature`, versão `1.1.5`. Wilder Nature é um provider amplo de fauna, comportamento animal, hunting, estruturas/recursos naturais e sistemas de exploração; não é apenas um pacote visual de mobs.

## 1. Authority e dependências
CurseForge publica Architectury API e Curios API como required dependencies. O pack contém Architectury 13.0.11 e Curios 9.5.1+1.21.1.

Farm & Charm 1.1.23 também está instalado e possui compat explícita no conteúdo 1.1.5, mas não é tratado aqui como hard dependency universal do Wilder Nature.

## 2. Canal da build 1.1.5
A mesma build NeoForge 1.1.5 aparece como **Release no CurseForge** e **Beta no Modrinth**; o changelog explica que o canal foi alterado devido ao grande volume de mudanças comportamentais. A identidade da build é confirmada, mas a maturidade deve ser tratada com cautela e runtime QA.

## 3. Escopo ecológico 1.1.5
A atualização reestrutura rotinas diárias, sleep cycles, herd behavior, threat/trust systems e interação com o ambiente. Exemplos documentados:
- Hippos acumulam threat, avisam antes de atacar, defendem jovens, atacam boats e alternam água/terra;
- Giraffes vivem em herds, fogem quando ameaçadas e usam kicks defensivos;
- Scorpions se enterram, emboscam com poison e possuem trust/taming progressivo;
- Swift Foxes roubam itens de mãos/chão, escondem, lembram e podem devolver/presentear após trust;
- Cassowaries ganharam escalation territorial com alert/threat/attack;
- Bison/Deer/MiniSheep usam herd/panic/sleep logic mais rica.

Isso torna AI state uma superfície central da integração com outros mods de fauna/combat.

## 4. Storage e comportamento de mobs
A 1.1.5 adiciona vários fluxos de item storage por mobs:
- Raccoons saqueiam containers e guardam itens em caches;
- Squirrels coletam nuts, usam hollow caches e podem entregar gifts quando trusted;
- Dogs carregam itens, usam burrow storage e fazem fetch/delivery.

Esses inventários pertencem ao mob/provider; automações externas devem preservar stack/state completo e evitar duplicação em unload/death/tame transitions.

## 5. Termites, mounds e rotten logs
Termites e Termite Mounds foram adicionados à Savanna. Colônias coletam recursos de logs, usam chambers e podem produzir Woodmeal. Logs próximos podem se tornar Rotten Logs, ser infestados/limpos e virar farmland após preenchimento com Dirt.

Boars, crops e Mushroom Colonies também interagem com essa cadeia; `mobGriefing` é respeitado por várias ações documentadas, portanto gamerule real é authority de world modification.

## 6. Hunting, trophies e food
O projeto adiciona meats provenientes da fauna, hunting rewards, Bison Horn, fish oil e trophies/decor. Trophies podem ter efeitos úteis, portanto não devem ser tratados como decoração pura. Loot/economy precisa ser testado em conjunto com Alex's Mobs, outros mob packs e culinária do pack.

## 7. Bounty Board — conflito documental
O changelog 1.1.5 descreve um sistema de bounties com refresh a cada 3 dias, tipos Hunt/Gather/Observe/Explore, Guild Commissions, reward preview e exploração de biomas. Entretanto a página principal atual ainda diz que o **Bounty Board está temporariamente disabled em 1.21.1**.

Consequência canônica: o sistema existe no código/documentação da linha, mas **não será considerado ativo no pack sem runtime/config test**.

## 8. Farm & Charm compat
A 1.1.5 documenta compat: Boars podem converter Dirt/Coarse Dirt em Fertilized Soil e Raccoons podem roubar ovos de ChickenCoops/ChickenNests. Como Farm & Charm 1.1.23 está presente, essas interações são regression gates concretos.

## 9. Rendering/equipment e Curios
A build corrige wolf fur cloak animation/crouch alignment e rendering em Armor Stands. Curios é required dependency; qualquer equipamento/trinket relacionado deve manter equip/render state consistente com o stack de animação do pack.

## 10. Multiplayer, tame e persistence
Trust, tame, herd leader, caches, stolen-item memory, burrows, sleep e bounty progress são estados persistentes/sensíveis a reconnect. O servidor deve ser authority; partículas/body language/animations são feedback, não prova suficiente de trust/tame completion.

## 11. Riscos no modpack
1. Densidade de fauna somada a Alex's Mobs/outros providers.
2. AI goals/pathfinding competem com Enhanced AI/performance mods.
3. `mobGriefing` e caches alteram blocos/economia.
4. Item theft/storage gera loss/dupe em unload/death.
5. Trust/tame state fica stale após relog.
6. Bounty system assumido ativo apesar do conflito documental.
7. Farm & Charm interaction duplica crops/eggs/soil transformations.
8. Beta/Release channel discrepancy mascara regressões comportamentais.

## 12. Boundary para quests/perks
Kills, taming, observation e exploration podem ser semanticamente úteis, mas só usar eventos/IDs server-side deduplicáveis. Não conceder Mastery por animation, proximity ou um mob entrar em sleep/threat state. Bounties só podem ser integradas depois de confirmar que estão realmente habilitadas no runtime 1.21.1.

## 13. Matriz de testes
- [ ] Dedicated server inicia com Wilder Nature 1.1.5 + Architectury 13.0.11 + Curios 9.5.1.
- [ ] Hippo/Cassowary threat escalation não ataca Creative indevidamente.
- [ ] Swift Fox theft/hide/return não perde nem duplica item em unload.
- [ ] Raccoon/Squirrel/Dog caches persistem após restart.
- [ ] `mobGriefing=false` bloqueia alterações correspondentes.
- [ ] Farm & Charm boar/raccoon compat funciona uma única vez por ação.
- [ ] Herd/sleep/trust states sobrevivem relog sem reset incorreto.
- [ ] Bounty Board é verificado como ativo ou disabled no runtime real.
- [ ] Spawn density permanece aceitável com os demais mob packs.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limite
CurseForge/Modrinth oficiais confirmam 1.1.5, dependências, Client & Server e o extenso changelog comportamental. A divergência Bounty Board/changelog foi mantida fail-closed: esta ficha não resolve o conflito por suposição. Contagem total de mobs/registries também não foi inventada sem pin completo do catálogo da build.

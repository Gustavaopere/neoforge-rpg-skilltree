# Corail Tombstone

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812ebd0cc164b9328b40
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Corail Tombstone
- **Arquivo JAR:** `tombstone-neoforge-1.21.1-9.5.5.jar`
- **Versão 1.21.1:** 9.5.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, RPG
- **Função:** Sistema de death recovery com grave protegido, decorative graves/souls, Knowledge of Death/perks, magic/prayers, Forgotten Knowledge/lore e utilidades de sobrevivência/teleporte.
- **Dependências:** NeoForge 1.21.1/Java 21. Integrações opcionais conforme stack. Create Aeronautics 1.3.2 está presente e a 9.5.5 adiciona compat específica para respawn em veículo; Curios 9.5.1 também está presente.
- **Sobreposição:** Sobreposição parcial com outros grave/death mods; nenhuma duplicação lógica foi identificada neste lote.
- **Compatibilidade/Riscos:** Double death/grave interception, inventory-extension recovery, grave placement/ownership e progression stacking. 9.5.5: Aeronautics vehicle respawn é regression gate. 9.5.4 remove ritual flute screen e reescreve lore/objectives, podendo invalidar quests antigas.
- **Observações:** mod id `tombstone`; runtime físico 9.5.5. Decisão Sem decisão preservada. A file list oficial passou a oferecer 9.5.6 para NeoForge 1.21.1 em 09/09/2026; registrar como atualização disponível sem substituir a versão canônica até o JAR físico mudar. Config de grave/access/perks não foi lida.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Corail Tombstone 9.5.5 File ID 8661357 + file list oficial 1.21.1 confirmando 9.5.6 em 09/09/2026 + stack físico Create Aeronautics 1.3.2/Curios 9.5.1. Dossiê de 09/09 preservado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/corail-tombstone ; https://www.curseforge.com/minecraft/mc-mods/corail-tombstone/files/8661357
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Corail Tombstone 9.5.5 permanece o runtime físico. Grave recovery, Souls/Knowledge/magic/lore, Aeronautics, lifecycle, riscos e testes preservados. ATUALIZAÇÃO DISPONÍVEL: 9.5.6 NeoForge 1.21.1 publicada em 09/09/2026.
- **Histórico da decisão:**
- **Data da última decisão:**

> **Divergência documental registrada:** a procedência do Notion menciona “modlist física atual de 11/09/2026”. A modlist física mais recente efetivamente acessível nesta execução é o snapshot de 08/09/2026 com 595 entradas; é ele que confirma `tombstone-neoforge-1.21.1-9.5.5.jar`, Create Aeronautics 1.3.2 e Curios 9.5.1+1.21.1. O texto-fonte foi preservado sem ser promovido a autoridade física inexistente.

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `tombstone-neoforge-1.21.1-9.5.5.jar`, mod id `tombstone`, versão `9.5.5`. Corail Tombstone é mais que um grave mod: protege inventário na morte e adiciona **decorative graves/souls, Knowledge of Death, perks, magic items, prayers, forgotten knowledge/lore e utilidades de sobrevivência/exploração**.

## 1. Identidade, versão e papel
- **Mod:** Corail Tombstone.
- **JAR:** `tombstone-neoforge-1.21.1-9.5.5.jar`.
- **Mod id:** `tombstone`.
- **Versão:** `9.5.5`.
- **Minecraft/loader:** 1.21.1 NeoForge 21+, Java 21.
- **Ambiente:** Client & Server.
- **Canal:** 9.5.5 é release recomendada publicada para 1.21.1 em 16/08/2026.
- **Decisão:** Sem decisão; preservada.

## 2. Authority e ownership
Corail Tombstone é authority de:
- criação e recuperação do grave após morte;
- seus decorative graves e haunting souls;
- Knowledge of Death e perks;
- magic items/prayers/ritual/lore próprios;
- comandos e teleport/death utilities próprios.
Curios, Create Aeronautics e outros mods continuam authority de seus slots/veículos. Integrações devem devolver/reconstruir state sem Tombstone assumir o sistema inteiro.

## 3. Grave de morte e proteção do inventário
O propósito central publicado é manter os itens do inventário seguros em um **grave** quando o jogador morre.
Testes críticos:
- inventário principal/hotbar/offhand/slots integrados conforme suporte;
- criação do grave em terreno normal e situações extremas;
- chave/fluxo de recuperação;
- proteção contra outro jogador conforme config;
- recuperação única sem dupe/loss;
- morte em dimensão/veículo/sublevel.
A configuração física do servidor não foi lida; regras de acesso/placement não são presumidas.

## 4. Decorative Graves
Decorative graves são blocos construíveis separados do grave de morte. A documentação oficial confirma:
- variantes visuais sem diferença essencial de gameplay entre dark/white/modelos;
- partículas/fog noturnas;
- podem ser minerados com ferramentas apropriadas;
- grave plates podem ser gravadas/renomeadas via anvil;
- podem ser **haunted by a soul** conforme chance configurada.
Não confundir decorative grave com container temporário de morte: eles participam do sistema mágico/souls.

## 5. Souls e enchanting mágico
Souls em decorative graves permanecem até serem usadas e alimentam superfícies mágicas como:
- enchanting de magic scroll/tablet e itens suportados;
- upgrade da Grave's Key;
- progressão de Knowledge of Death quando usadas/libertadas por determinados meios.
Soul é recurso do Tombstone; efeitos/encantamentos concretos devem ser validados no Compendium/runtime da 9.5.5.

## 6. Knowledge of Death
O projeto possui sistema de progressão próprio. Pontos podem ser obtidos por ações publicadas como:
- usar grave soul para enchant;
- libertar soul de receptacle;
- rezar perto de decorative grave com **Ankh of Pray**, sujeito a cooldown;
- completar Tombstone advancements.
Os pontos desbloqueiam **perks** numa GUI própria. Perks são bônus relacionados às features Tombstone e podem depender de config.
Isso é um progression system independente de outras skill trees do pack; evitar double-gating ou stacking não planejado.

## 7. Ankh of Pray e prayers
O Ankh of Pray participa do ganho de Knowledge e de prayers/buffs próprios. A documentação pública histórica informa cooldown de seis horas in-game para o fluxo padrão de prayer.
Como configs e perks podem alterar superfícies atuais, o valor runtime deve ser testado antes de usá-lo como regra absoluta do pack.

## 8. Forgotten Knowledge e lore
Versões recentes incluem scrolls legíveis com enigmas/objetivos que desbloqueiam **Forgotten Knowledge**. A documentação cita exemplos como Elyra's Diary e Rite of Silent Bound, e a linha 9.5.x continua expandindo/revisando lore.
A 9.5.3 adicionou **The Nights of Nour**; a 9.5.4 reescreveu grande parte das lore stories, podendo alterar objetivos. Portanto quests externas não devem hardcode passos antigos sem revalidação.

## 9. Ritual Flute — mudança 9.5.4
Na 9.5.4:
- ritual flute melodies passam a tocar automaticamente no bloco correto após serem aprendidas;
- a antiga ritual flute screen foi removida.
Qualquer guia/quest do pack que ainda peça abrir essa tela está desatualizado para o runtime 9.5.5.

## 10. GUI, Compendium e preferências
O mod oferece interface in-game para:
- preferences do jogador;
- Compendium de informações;
- seleção/consulta de perks de Knowledge of Death.
Client UI deve refletir state authoritative do servidor; perks não podem ser concedidos apenas por manipulação visual local.

## 11. Comandos e teleport/death utilities
A descrição oficial confirma comandos relacionados a morte e **dimensional teleportation**.
Sem command registry pin exato desta build, a ficha não lista sintaxe individual. Operacionalmente, comandos administrativos/teleport precisam respeitar permissions e não duplicar grave recovery.

## 12. Release 9.5.5 — Create Aeronautics
Mudança exata 9.5.5:
- **compatibility Create Aeronautics — respawn on vehicle**, issue 351.
O pack instala Create Aeronautics `1.3.2`. Isso é integração concreta e obrigatória para regression testing de morte/respawn em veículos/sublevels.
A correção não implica que todo mod de veículo/physics esteja automaticamente coberto.

## 13. Client / server e multiplayer
- Servidor: morte, grave placement, inventory capture/recovery, Knowledge/perks e magic state.
- Cliente: GUI, particles, feedback visual e leitura de lore.
- Multiplayer exige ownership seguro do grave e recuperação atômica.
- Dois eventos de death/respawn próximos não podem criar dois graves para o mesmo inventário.

## 14. Lifecycle
Validar:
- morte normal;
- death durante dimension change;
- death em água/lava/void/altura extrema;
- death em Create Aeronautics vehicle;
- disconnect durante death screen;
- server restart antes da recuperação;
- grave chunk unload/reload;
- recuperação com inventário parcialmente ocupado;
- uso de soul/perk e relog;
- atualização de Forgotten Knowledge/lore state.

## 15. Integrações concretas no pack
- **Create Aeronautics 1.3.2:** compat exata de respawn em veículo introduzida/registrada na 9.5.5.
- **Curios 9.5.1:** pack possui slots adicionais; recuperação/equip order deve ser testada conforme integrações Tombstone atuais.
- **Epic Fight/ParCool/Sable:** movement/death contexts podem ocorrer em estados especiais; não assumir compat sem runtime test.
- **Outros death/revive/grave systems:** qualquer mod que também capture inventário ou intercepte death event é alto risco de dupe/loss; mapear antes de manter dois providers.
- **Lore/quest stack:** passos de ritual precisam refletir mudanças 9.5.4/9.5.5.

## 16. Riscos técnicos
1. **Double grave/death interception:** dois providers de morte podem capturar o mesmo inventário.
2. **Vehicle/sublevel respawn:** 9.5.5 corrige Aeronautics, mas continua regression gate.
3. **Inventory extensions:** slots externos podem ser perdidos, duplicados ou reequipados em ordem errada.
4. **Grave placement:** void/world border/structure/claims podem impedir local válido.
5. **Permission/ownership:** outro jogador não deve recuperar indevidamente itens protegidos.
6. **Knowledge stacking:** perks podem somar com outras skill trees e alterar balanceamento.
7. **Lore drift:** 9.5.4 reescreveu objetivos; quests externas podem ficar obsoletas.
8. **Teleport commands:** interação com world borders/dimensions precisa de validação administrativa.

## 17. Matriz de testes
- [ ] Dedicated server boot com Tombstone 9.5.5.
- [ ] Morte normal cria um único grave com inventário completo esperado.
- [ ] Recuperação devolve itens uma única vez e respeita ownership.
- [ ] Restart antes da recuperação preserva grave/inventário.
- [ ] Grave chunk unload/reload não perde state.
- [ ] Death/respawn em Create Aeronautics 1.3.2 vehicle funciona sem spawn inválido/dupe.
- [ ] Curios/equipment slots recuperam conforme integração atual.
- [ ] Decorative grave pode receber soul e consumi-la uma vez.
- [ ] Knowledge of Death ganha pontos/perks e persiste após relog/restart.
- [ ] Ritual Flute usa fluxo 9.5.4 sem tela antiga.
- [ ] Forgotten Knowledge/Nights of Nour objectives funcionam conforme lore atual.
- [ ] Death em dimensão/void/ambiente extremo cria recovery path válido.
- [ ] Nenhum outro death/grave mod captura o mesmo inventário simultaneamente.
Nenhum teste foi marcado como aprovado nesta auditoria.

## 18. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão, Aeronautics e Curios presentes.
- CurseForge oficial Corail Tombstone: grave protection, decorative graves/souls, Knowledge of Death, magic, Compendium e teleport/death utilities.
- Release oficial 9.5.5: compat Create Aeronautics para respawn em vehicle; 9.5.4 Ritual Flute/lore rewrite; 9.5.3 Nights of Nour.

## 19. Revalidação física — 11/09/2026
A modlist física mantém `tombstone-neoforge-1.21.1-9.5.5.jar`, mod id `tombstone`, versão `9.5.5`; portanto **9.5.5 continua sendo a autoridade do runtime**. A integração 9.5.5 com Create Aeronautics para respawn em veículo continua sendo regression gate do pack.
**Atualização upstream disponível:** a file list oficial passou a listar **Corail Tombstone 9.5.6 para NeoForge 1.21.1**, publicada em **09/09/2026**. Esta auditoria não atribui mudanças internas à 9.5.6 sem changelog específico confirmado e não altera a versão instalada. Nenhum teste de morte, grave recovery, veículo, Curios, Knowledge ou lore foi executado.

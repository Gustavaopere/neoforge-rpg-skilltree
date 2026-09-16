# Corail Tombstone

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `tombstone-neoforge-1.21.1-9.5.6.jar`
- **Versão 1.21.1:** 9.5.6
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, RPG
- **Função:** Death recovery com grave protegido, decorative graves/souls, Knowledge of Death/perks, magic/prayers, Forgotten Knowledge/lore e utilidades de sobrevivência/teleporte.
- **Dependências:** NeoForge 1.21.1/Java 21. Integrações opcionais conforme stack. A compat de respawn em veículo introduzida na linha 9.5.5 para Create Aeronautics permanece regression gate; Curios permanece superfície de inventory extension quando presente.
- **Sobreposição:** Sobreposição parcial com outros grave/death mods; dois interceptors de inventário no mesmo death event são risco alto de dupe/loss.
- **Compatibilidade/Riscos:** Double death/grave interception, inventory-extension recovery, grave placement/ownership, progression stacking, Aeronautics vehicle respawn, lore/ritual drift e XP restoration em níveis altos.
- **Observações:** mod id `tombstone`; runtime físico 9.5.6. A release 9.5.6 para NeoForge 1.21.1, publicada em 09/09/2026, corrige restauração de XP na morte com level count alto causada por integer overflow. Config física de grave/access/perks não foi lida.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Corail Tombstone 9.5.6 + dossiê Notion anterior com 9.5.3–9.5.5. Nenhum teste de death/recovery foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/corail-tombstone
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — runtime físico atualizado de 9.5.5 para 9.5.6; o fix de XP restoration que antes era update candidate agora pertence à build instalada. Certificação pendente de QC/re-fetch final.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🪦 **ESCOPO CANÔNICO.** Runtime físico: `tombstone-neoforge-1.21.1-9.5.6.jar`, mod id `tombstone`, versão `9.5.6`. Corail Tombstone protege inventário na morte e também possui decorative graves/souls, Knowledge of Death, perks, magic/prayers, Forgotten Knowledge/lore e utilities próprias.

## 1. Authority e ownership
Tombstone é authority de criação/recuperação do grave, decorative graves/haunting souls, Knowledge of Death/perks, magic/lore e comandos/teleport-death utilities próprios. Curios, Create Aeronautics e outros mods continuam authority de slots/veículos; integrations devem reconstruir state sem Tombstone assumir os sistemas externos.

## 2. Grave de morte
O grave deve capturar e devolver inventário uma única vez. Gates preservados do Notion:
- main inventory/hotbar/offhand e slots integrados conforme suporte;
- terreno normal e situações extremas;
- key/recovery flow;
- ownership/access conforme config;
- ausência de dupe/loss;
- death em dimension/vehicle/sublevel.

A config física do servidor não foi lida, portanto regras exatas de access/placement não são presumidas.

## 3. Decorative Graves e Souls
Decorative graves são blocos construíveis distintos do grave de morte, com variantes visuais, fog/particles noturnas, mineração apropriada e grave plates graváveis/renomeáveis. Podem ser haunted por soul conforme chance configurada. Souls permanecem recurso Tombstone para enchanting de itens mágicos/scrolls/tablets, upgrade de Grave's Key e superfícies de Knowledge of Death; decorative grave não deve ser confundido com death container.

## 4. Knowledge of Death, perks e prayers
Knowledge é progressão própria. Pontos podem vir de ações publicadas como usar grave soul em enchanting, libertar soul, prayer perto de decorative grave com **Ankh of Pray** e advancements Tombstone. Perks vivem em GUI própria e podem depender de config. O valor histórico de cooldown de prayer (seis horas in-game no fluxo padrão documentado) não deve virar regra absoluta sem validar config/runtime atual.

## 5. Forgotten Knowledge e lore
Scrolls/enigmas/objectives desbloqueiam Forgotten Knowledge. O dossiê registra Elyra's Diary, Rite of Silent Bound e a adição de **The Nights of Nour** na 9.5.3. A 9.5.4 reescreveu grande parte do lore/objectives; quests externas não devem hardcode passos antigos sem revalidação.

## 6. Ritual Flute — mudança 9.5.4
Desde 9.5.4, melodies aprendidas tocam automaticamente no bloco correto e a antiga ritual-flute screen foi removida. Guia/quest que exija abrir essa tela está desatualizado para 9.5.6.

## 7. GUI, Compendium, commands e teleport
O mod oferece preferences, Compendium e perks UI. Client UI apenas apresenta state; perks permanecem server-authoritative. Descrição oficial também confirma comandos de morte/dimensional teleportation. Sem command registry pinado nesta build, sintaxe não é inventada; permissions e interação com grave recovery precisam ser validadas.

## 8. Histórico 9.5.5 — Create Aeronautics
A 9.5.5 adicionou compat específica **Create Aeronautics — respawn on vehicle** (issue 351). Esse fix continua parte do histórico funcional que deve regredir na 9.5.6. Não implica compat automática com todo vehicle/physics mod.

## 9. Atualização instalada — 9.5.6
A build física 9.5.6 corrige **XP restoration on death com level count alto por integer overflow**. Esse ponto agora é regression gate direto do runtime instalado: morte/recuperação com XP elevado deve restaurar valor correto sem overflow, perda indevida ou duplicação.

## 10. Client/server e lifecycle
Servidor: death, grave placement, inventory capture/recovery, Knowledge/perks, magic state e XP restoration. Cliente: GUI, particles, feedback/lore. Multiplayer exige ownership e recuperação atômica. Validar death normal, dimension change, água/lava/void, vehicle/sublevel, disconnect na death screen, restart antes de recovery, chunk unload/reload, inventário parcialmente ocupado, soul/perk/relog e lore state.

## 11. Integrações concretas
- **Create Aeronautics:** vehicle respawn continua gate herdado da 9.5.5 quando provider correspondente está presente.
- **Curios:** slots adicionais devem recuperar/equipar conforme integration real.
- **Epic Fight/ParCool/Sable:** death/movement special states são superfícies de teste, não compat automática.
- **Outros death/revive/grave systems:** risco alto de double interception.
- **Quest/lore:** ritual/objectives precisam refletir 9.5.4+.

## 12. Riscos
1. double grave/death interception;
2. vehicle/sublevel respawn;
3. slot externo perdido/duplicado/reequipado errado;
4. grave placement inválido em void/border/claims;
5. ownership/access indevido;
6. Knowledge/perk stacking com outras skill trees;
7. lore drift;
8. teleport command + dimension/border interactions;
9. XP overflow/regression em níveis altos — alvo do fix 9.5.6.

## 13. Matriz de testes
- [ ] Dedicated server boot com Tombstone 9.5.6.
- [ ] Morte normal cria um único grave com inventário esperado.
- [ ] Recovery devolve itens uma vez e respeita ownership.
- [ ] Restart/chunk unload preservam grave/state.
- [ ] Death/respawn em vehicle/sublevel compatível não duplica nem cria spawn inválido.
- [ ] Slots Curios/inventory extensions recuperam corretamente quando integration existe.
- [ ] Decorative grave/soul consome recurso uma única vez.
- [ ] Knowledge/perks persistem após relog/restart.
- [ ] Ritual Flute usa fluxo sem screen antiga.
- [ ] Forgotten Knowledge/Nights of Nour seguem lore atual.
- [ ] Death em dimension/void/ambiente extremo tem recovery path válido.
- [ ] Nenhum outro grave mod captura o mesmo inventário.
- [ ] XP alto é restaurado corretamente sem integer overflow/dupe.

**Nenhum teste foi executado nesta reauditoria documental.**

## 14. Evidências e limites
- modlist física de 16/09/2026: `tombstone-neoforge-1.21.1-9.5.6.jar` / 9.5.6;
- CurseForge oficial: grave protection, decorative graves/souls, Knowledge, magic, Compendium/utilities e fix 9.5.6;
- histórico 9.5.3–9.5.5 do Notion preservado, inclusive Ritual Flute/lore rewrite e Aeronautics vehicle respawn;
- configs de access/perks e runtime tests permanecem não verificados.

## 15. Reauditoria física — 16/09/2026
9.5.6 foi promovida de update candidate para runtime físico. Decisão **Sem decisão** preservada. Nenhum teste de death, grave recovery, XP restoration, vehicle respawn, Curios, Knowledge ou lore foi executado.
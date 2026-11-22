# Minecolonies: Epicfied (Epic Colonies) — 21.1.41 (atualização declarada em 22/11/2026)

> **Versão vigente declarada em 22/11/2026:** `21.1.41`; JAR `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.1.41.jar`; anterior `21.0.8`. Fonte [CurseForge](https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9708810). O pedido do usuário atualiza o inventário documental, mesmo que o índice histórico de outros mods ainda não tenha sido sincronizado.

> **Auditoria e limites:** Versões 21.0.9, 21.0.91, 21.0.912, 21.1.0–21.1.4 já auditadas (animator, archer, Raider, Guard, Druid/Mage, facial expressions, visual e configs). **21.1.41 (20/11/2026):** changelog oficial declara tentativa de corrigir crash de cidadão ao atualizar o item segurado (mensagem do autor contém ‘Hopefully’; não declarar fix comprovado no runtime). O sufixo EFM-21.16.4 é parte do arquivo real e não deve ser modificado para corresponder ao Epic Fight documental. Os trechos antigos “não instalada” são históricos e anteriores à declaração atual; sem verificação de JAR local ou smoke tests.


## Propriedades do registro

- **Mod:** Minecolonies: Epicfied (Epic Colonies)
- **Arquivo JAR instalado — declaração do usuário:** `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.1.41.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9708810)).
- **Versão instalada declarada para Minecraft 1.21.1 / NeoForge:** `21.1.41` (anterior `21.0.8`).
- **Categoria:** Compat, RPG, Mobs
- **Função:** Integra MineColonies ao Epic Fight preservando modelos/acessórios de cidadãos e raiders em skinned meshes/animations do Epic Fight, incluindo expressão facial e combat presentation especializada.
- **Dependências:** Epic Fight 21.17.3.1 + MineColonies 1.1.1387-1.21.1-snapshot estão fisicamente presentes. O filename oficial registra compile target EFM-21.16.4; inspeção anterior do metadata upstream registrou ranges de loader amplos.
- **Estado no pack:** `21.1.41` declarado instalado pelo usuário; inventário documental no GitHub sincronizado, sem leitura do JAR do computador.
- **Estado da pesquisa:** releases e source verificados conforme histórico; sem afirmação de homologação de dependências nem execução do jogo.
- **Compatibilidade/Riscos:** Build 21.0.8 foi produzida contra Epic Fight 21.16.4, enquanto o pack usa 21.17.3.1; não há loader block previamente identificado, mas renderer/armature/entity-patch drift continua regression gate. Riscos: patch duplicado com compat genérico, clipping de acessórios/jobs, AI MineColonies vs combat patch e double damage/stun.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/epic-colonies
- **Procedência:** posição preservada da modlist física histórica; atualização de versão declarada em 22/11/2026; JAR conforme [publicação oficial](https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9708810). Sem hash local novo.
- **Observações vigentes:** `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.1.41.jar`, `21.1.41`. JAR anterior, hash e versões citados nas seções de origem ficam como histórico; não são prova contrária à atualização declarada.
- **Atualização/Status:** 22/11/2026 — `21.0.8` → `21.1.41` por declaração do usuário. Versões 21.0.9, 21.0.91, 21.0.912, 21.1.0–21.1.4 já auditadas (animator, archer, Raider, Guard, Druid/Mage, facial expressions, visual e configs). **21.1.41 (20/11/2026):** changelog oficial declara tentativa de corrigir crash de cidadão ao atualizar o item segurado (mensagem do autor contém ‘Hopefully’; não declarar fix comprovado no runtime). O sufixo EFM-21.16.4 é parte do arquivo real e não deve ser modificado para corresponder ao Epic Fight documental.
- **Decisão:** Manter
- **Histórico da decisão:** 2026-09-06 — Manter após inspeção do source 1.21.1: release 21.0.8 é a linha NeoForge 1.21.1 disponível e o sufixo EFM-21.16.4 não era loader constraint. 2026-09-09 — dossier refeito contra a modlist física de 595 mods; risco 21.16.4→21.17.3.1 mantido como regression gate comportamental.
- **Sobreposição:** Especializado em MineColonies↔Epic Fight; pode cruzar com Epic Fight Compat geral, mas não é substituto equivalente. Precedence deve impedir dois patches/renderers para a mesma entidade.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #262: JAR `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar`, mod id `epic_colonies`, runtime `21.0.8`, SHA-1 `82a9727afd01fac255ac399ec97b4504440e9a70`.

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar` · mod id `epic_colonies` · versão `21.0.8` · NeoForge 1.21.1. O pack usa **Epic Fight 21.17.3.1** e **MineColonies 1.1.1387-1.21.1-snapshot**.
## 1. Papel no modpack
Epic Colonies é a integração especializada **MineColonies ↔ Epic Fight**. Seu escopo é fazer cidadãos/raiders do MineColonies participarem da apresentação e do combat framework do Epic Fight sem abandonar a identidade visual dos modelos/jobs do MineColonies.
## 2. Authority / ownership
- **MineColonies:** cidadão/raider, job, AI colonial, inventory, tasks, stats e lifecycle da colônia.
- **Epic Fight:** armature, animation/combat patch e mechanics de combate aplicadas quando a integração as usa.
- **Epic Colonies:** bridge/model assets e adaptação entre os dois providers.
A bridge não deve criar um segundo cidadão, job state ou inventário paralelo.
## 3. Cobertura visual publicada
A página oficial descreve mais de **80 tipos de modelo** refeitos/adaptados para o rig/skinned mesh do Epic Fight, com objetivo de preservar os modelos e acessórios de job do MineColonies. Essa cobertura é um diferencial frente a patches genéricos que apenas aplicam um humanoide padrão.
## 4. Expressões faciais
O projeto documenta olhos 3D que se movem/piscam e boca com animação limitada; as expressões são apresentação client-side derivada do state do cidadão. Elas não alteram happiness, job ou AI do MineColonies.
## 5. Combate dos cidadãos
Quando um cidadão/raider é compatibilizado, Epic Fight fornece a camada de combate/animação correspondente. Damage, death e ownership do cidadão continuam server-authoritative; uma animação não pode liquidar dano duas vezes.
## 6. Relação com Epic Fight Compat geral
`Epic Fight Compat 1.1.0` também está instalado, mas possui escopo genérico para vários mods. Epic Colonies é especializado em MineColonies e preserva modelos/acessórios específicos do ecossistema colonial. Não assumir equivalência nem remover um pelo outro sem mapear patches efetivamente ativos.
## 7. Version drift EFM-21.16.4 → runtime 21.17.3.1
O filename oficial registra alvo de compilação **EFM-21.16.4**. O pack usa Epic Fight **21.17.3.1**. Inspeção anterior da branch 1.21.1 registrou `epicfight` e `minecolonies` como dependências required com range amplo `[0,)`; portanto não há bloqueio de loader por essa diferença.
Isso não elimina risco binário/comportamental: armature, renderer ou patch internals podem mudar em patch releases.
## 8. Client / Server
**Servidor/common:** cidadão, combat state válido, damage/death e AI MineColonies.
**Cliente:** skinned mesh, pose, facial expression, attachment de acessórios e rendering.
Dedicated server não deve carregar renderer/model classes como authority de gameplay.
## 9. Lifecycle
Validar: citizen spawn/load, contratação/job change, guard combat enter/exit, raider spawn, death/respawn replacement, chunk unload/reload, colony restart, dimension/teleport quando aplicável, resource reload e update de MineColonies/Epic Fight.
## 10. Multiplayer
Dois clientes devem observar o mesmo cidadão/raider com combat state coerente. Um observador não pode causar um segundo attack settlement. Tracking range, reconnect e troca de job precisam preservar modelo/acessórios corretos.
## 11. Riscos
1. armature drift entre Epic Fight 21.16.4 e 21.17.3.1;
2. citizen model/job accessory clipping;
3. cidadão manter renderer antigo após job change;
4. patch duplicado por compat genérico;
5. double damage/stun;
6. AI colonial competir com combat goal/patch;
7. facial state stale após reload;
8. raider/citizen específico sem modelo adaptado;
9. snapshot do MineColonies alterar entity/model contract;
10. server/client divergirem sobre death/combat state.
## 12. Matriz de testes
1. Dedicated server com MineColonies 1.1.1387 + Epic Fight 21.17.3.1 + Epic Colonies 21.0.8.
2. Cidadãos de múltiplos jobs, incluindo acessórios distintos.
3. Guard/citizen entrando e saindo de combate.
4. Raider combat e death.
5. Job change sem relog e após relog.
6. Chunk unload/reload da colônia.
7. Dois jogadores observando o mesmo combate.
8. Epic Fight Compat geral carregado simultaneamente.
9. Resource reload/model reload.
10. Smoke-test após qualquer update do MineColonies ou Epic Fight.
**Esta catalogação não afirma que esses testes foram executados.**
## 13. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/versão/hash + Epic Fight/MineColonies atuais;
- página oficial Epic Colonies: dependências, modelos de cidadãos preservados, 80+ model types e facial expressions;
- inspeção anterior do source 1.21.1: ranges de dependência amplos, distinguindo compile target de loader constraint.
> **Boundary canônico:** MineColonies continua authority do **cidadão e da colônia**; Epic Fight do **combat/animation framework**; Epic Colonies apenas integra essas duas superfícies.

## 14. Atualização upstream 21.0.9 — não instalada
A autoridade física permanece em **Epic Colonies 21.0.8**. A release **21.0.9** para NeoForge 1.21.1 foi publicada em 29/09/2026; não há release intermediária posterior a 21.0.8 antes dela.

### Correções
- corrige a **animação de mineração repetindo** mesmo depois de o cidadão deixar de minerar;
- corrige a **animação de sentar repetindo** mesmo depois de o cidadão deixar de estar sentado;
- corrige textura quebrada de **Aristocrat**;
- corrige textura quebrada de **Settler**;
- corrige o display name do mod em `mods.toml`.

### Adições
- adiciona compatibilidade de movesets para **EpicFight-Nightfall + EpicFight-Extra**, exigindo **os dois** instalados para ativar os novos movesets;
- adiciona meshes de Settler que estavam faltando.

### Remoção
- remove logs de debug desnecessários.

Impacto para o pack: as correções de mining/sitting atingem exatamente state visual derivado de AI/job state de MineColonies e precisam permanecer apresentação-only; elas não podem alterar o state canônico do cidadão. A nova compat Nightfall+Extra é condicional e não deve ser tratada como ativa se ambos os providers não estiverem presentes.

Gate de promoção 21.0.8→21.0.9: mining start/stop, sit/stand, job change, Aristocrat/Settler textures e meshes, guard/raider combat, resource reload, coexistência com Epic Fight 21.17.3.1 e verificação condicional de Nightfall+Extra somente quando os dois estiverem instalados.

Fonte upstream: CurseForge file ID 9005453, `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.9.jar`.

## 15. Revalidação upstream 21.0.9 — 10/10/2026

**Físico:** EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar / 21.0.8. **Mais recente publicada** para NeoForge 1.21.1 identificada: 21.0.9 (29/09/2026), já auditada em detalhe na seção 14, sem nova release intermediária.

São preservados os fixes de animações mining/sitting, texturas Aristocrat/Settler, meshes Settler e compat condicional de movesets EpicFight-Nightfall + EpicFight-Extra, apenas quando ambos estiverem presentes. O compile target EFM 21.16.4 não deve ser reescrito como Epic Fight 21.17.3.1, versão física conhecida. MineColonies físico continua snapshot 1.1.1387, com 1.1.1403 somente upstream.

**Regressões para futura promoção:** stop/start mining/sitting por job, combat animation e accessories, skins/mesh, resource reload, Guard/Raider AI, dois jogadores e clash de entity patches com addons Epic Fight. Esta verificação não prova execução em runtime.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/epic-colonies/files/all

**Estado:** versão upstream já registrada, sem update físico.

## Release e compatibilidade — reauditoria 13/10/2026

**Atualização upstream NeoForge 1.21.1:** 21.0.9. **Fonte oficial:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9005453.

**Novas adições upstream em 21.0.9 (29/09/2026):** compat com **EpicFight-Nightfall + EpicFight-Extra** em conjunto para novos movesets; meshes faltantes de Settlers. **Correções declaradas pelo autor com ressalva de tentativa:** repetição de animações de mineração e sentar, textures Aristocrat/Settler, nome do mod em `mods.toml`; remoção de debug logs desnecessários. **Gate:** confirmar se ambos addons Nightfall/Extra existem no pack antes de ativar movesets, MineColonies citizens AI/work/job animations, pose overrides com EMF/ETF, skins/textures, pathfinding e dedicated server; não presumir correções definitivas quando changelog diz 'hopefully'.

**Instalação física:** não revalidada desde `modlist(1).txt`; versão instalada histórica e filename versionado com ✅ preservados. Sem substituição de JAR, execução in-game ou confirmação de compatibilidade plena nesta atualização documental. Não promover a versão upstream à coluna física do índice.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Minecolonies: Epiched (Epic Colonies)
- **Último candidato 1.21.1 / NeoForge localizado:** **21.1.2 (Release NeoForge 1.21.1, 17/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** 21.1.2 adiciona suporte a MineColonies Mages, opções de moveset/facial features e corrige Druids. Validar MineColonies atual, Epic Fight 21.17.3.1, citizen models/AI/combat, multiplayer e restart.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/epic-colonies

## Auditoria detalhada 21.0.8 → 21.1.2 — lote 31–40 (22/10/2026)

**Físico:** Minecolonies: Epicfied (Epic Colonies) 21.0.8 NeoForge 1.21.1. **Último upstream:** 21.1.2 Release NeoForge 1.21.1 (17/10). Este é o port **TheDevKenji**, CurseForge 1610923, não o mod Epic Fight ou o MineColonies provider. A cadeia é 21.0.9 → 21.0.91 → 21.1.0 → 21.1.1 → 21.1.2, com fontes oficiais individuais.

| Release | Mudança anunciada e escopo |
|---|---|
| 21.0.9 (29/09, file 9005453) | Tenta corrigir loop de animação de mineração e de sentar (autor escreve *hopefully*), texturas Aristocrat/Settler, nome do mod em mods.toml; acrescenta meshes de Settler e compat Nightfall + Extra **somente se os dois addons EpicFight estiverem instalados**; remove logs debug. |
| **21.0.91** (02/10, file 9035733) | Refix da animação de mineração após o job terminar; fixes de arqueiros e raider archers que não disparavam flechas. |
| **21.1.0** (06/10, file 9075508) | Fix de raider archers; mesh Epic Fight do Amazon Spearman; **dois recursos opt-in com default FALSE:** arqueiro troca para dagger no corpo a corpo se houver no inventário; cidadão usa tools tornadas unbreakable por outros mods. Não supor essas features habilitadas. |
| **21.1.1** (12/10, file 9140888) | Corrige visual de armaduras de mods como Fantasy Armor, ajusta pesos dos combat movesets dos cidadãos, animação use/place para Builder ao construir; mais correção da animação de mineração (autor ressalva *I hope*). |
| **21.1.2** (17/10, file 9234338) | Suporte a **MineColonies Mages**, configuração dos pesos de movesets de Guard, compat com novas versões de MineColonies; opções individuais para desativar *blinking*, expressão facial e eyes-follow; fix de Druids. |

**Riscos concretos:** MineColonies 1.1.1387-snapshot do inventário versus upgrades documentais 1.1.1403/1405, Epic Fight instalado runtime 21.17.3.1 versus **EFM-21.16.4 gravado no filename upstream**: não inferir equivalência da versão compilada com provider presente. Testar guard/raider ranged, melee dagger, mages/druids, animations idle/sitting/mining/use, config false defaults, sync multiplayer, renderer EMF/ETF, claims, equipamento unbreakable e startup dedicado. O modpack físico não foi alterado.

**Fontes:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9005453 ; https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9035733 ; https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9075508 ; https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9140888 ; https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9234338 .

## Fechamento incremental — 18/11/2026 — 21.0.8 a 21.1.4

**Baseline físico preservado:** posição #262, `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar`, mod id `epic_colonies`, runtime `21.0.8`. Projeto correto **Minecolonies: Epicfied (Epic Colonies)**, TheDevKenji, CurseForge 1610923. Última release NeoForge 1.21.1 no catálogo oficial: 21.1.4, 16/11/2026. Categorias oficiais atuais: Addons + Adventure and RPG + Armor, Tools, and Weapons + Cosmetic, iguais ao diretório existente.

A listagem atual comprova a sequência NeoForge 1.21.1 **21.0.9 → 21.0.91 → 21.0.912 → 21.1.0 → 21.1.1 → 21.1.2 → 21.1.3 → 21.1.4**. A build 20.1.21 de 18/10 pertence ao Forge 1.20.1, não ao alvo. A revisão anterior já documentou os deltas de 21.0.9, 21.0.91, 21.1.0, 21.1.1 e 21.1.2; a versão hotfix 21.0.912 antes omitida é agora reconciliada.

| Release | Data | Delta relevante e gate |
|---|---|---|
| [21.0.912](https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9053219) | 04/10/2026 | Nova tentativa/correção anunciada de arqueiros coloniais e raiders arqueiros não dispararem. Relevante ao funcionamento do combate ranged; não declarar que o problema deixou de existir em todas as versões, pois há regressão posteriormente registrada. |
| [21.1.3](https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9379537) | 31/10/2026 | Opção client-side para desligar faces 3D dos cidadãos (EpicColonies-Client/Cosmetic/Disable Citizen 3D Faces), nova correção para disparos de Raider Rangers, movesets de machado e atribuição de tipos Epic Fight ao chefe/spear (IDs publicados: `minecolonies:cheifsword` como `epicfight:sword`; `minecolonies:spear` como `epicfight:spear`). Regressar rendering e combate, sem corrigir a grafia do ID por inferência. |
| [21.1.4](https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9641684) | 16/11/2026 | Correção anunciada para armor e attack damage de cidadãos que não funcionavam; o autor ressalva incerteza. Alta relevância para stats e settlement de combate, sem assumir implementação bem-sucedida nem valores específicos. |

**QA separado:** testar armadura e dano de guardas com equipamento/movesets distintos, raiders/arqueiros, faces 3D on/off, weapons do MineColonies, interações com addons Nightfall/Extra quando presentes, MineColonies 1.1.1387-snapshot e Epic Fight 21.17.3.1 do snapshot frente ao compile target EFM-21.16.4, dedicated server, multiplayer e save/restart. Nenhuma nova versão instalada, binário inspecionado ou teste executado.

**Conclusão documental:** cadeia de oito releases auditada (cinco no histórico anterior, três complementadas acima); arquivo, versão e caminho físicos preservados. Fonte do inventário de publicações: https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/all . Promoção física condicionada a QA e revisão de manifestos.

## Sincronização documental declarada — 22/11/2026

**Antes:** `21.0.8`. **Agora, conforme usuário:** `21.1.41`, distribuição `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.1.41.jar` ([fonte oficial](https://www.curseforge.com/minecraft/mc-mods/minecolonies-epicfied-epic-colonies/files/9708810)).

**Mudanças e requisitos:** Versões 21.0.9, 21.0.91, 21.0.912, 21.1.0–21.1.4 já auditadas (animator, archer, Raider, Guard, Druid/Mage, facial expressions, visual e configs). **21.1.41 (20/11/2026):** changelog oficial declara tentativa de corrigir crash de cidadão ao atualizar o item segurado (mensagem do autor contém ‘Hopefully’; não declarar fix comprovado no runtime). O sufixo EFM-21.16.4 é parte do arquivo real e não deve ser modificado para corresponder ao Epic Fight documental.

Não alterar dependências cujos próprios dossiers ainda aguardam lote posterior; registrar diferenças como pendência de sincronização, sem afirmar que a combinação foi executada ou testada.

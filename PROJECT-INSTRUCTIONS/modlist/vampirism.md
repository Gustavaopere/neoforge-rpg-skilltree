# Vampirism

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db810c96a9e779d8132289
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Vampirism-1.21-1.10.13.jar`, mod id `vampirism`, runtime `1.10.13`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Vampirism 1.10.13 e o stack de addons citado estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Vampirism
- **Arquivo JAR:** `Vampirism-1.21-1.10.13.jar`
- **Versão 1.21.1:** 1.10.13
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG, Mobs
- **Função:** Provider sobrenatural base de Vampire/Hunter: transformation/factions, níveis 1–14, Lord 1–5, blood economy, skills/actions, tasks, refinements, minions, NPCs e villages/world content.
- **Dependências:** NeoForge 1.21.1 e dependências oficiais. Stack atual inclui Werewolves 2.0.3.3, Vampiric Ageing 1.4.21, Vampirism Integrations 1.10.2 e Vampire Spells Addon 0.0.9.
- **Sobreposição:** Compartilha fantasia RPG/sobrenatural, mas é authority canônica de Vampire/Hunter e blood. Addons instalados estendem essa progressão; não constituem substitutos do core.
- **Compatibilidade/Riscos:** Authority extensa e persistente. Não duplicar blood bar, faction state, skill points, cooldowns, Lord XP, minion ownership ou village state. Riscos principais: double progression, damage/heal stacking, addon version drift e modifiers/state órfãos em lifecycle.
- **Observações:** Source audit registrado: 14 Vampire actions + 3 Hunter + 2 Lord; 31 Vampire skills + 32 Hunter + 3 Lord; 46 task keys; 10 Entity Actions; 7 Minion Tasks; 47 refinements; 4 attributes. 1 blood unit = 100 mB quando o provider converte para `vampirism:blood`. Runtime QA ainda pendente.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source pin `TeamLapen/Vampirism@e1ed095713cef5e9eb151d0ee58908fa830d6bb7` + CurseForge oficial 1.10.13. Changelog exato 1.10.13 revalidado: ActionWheel/MinionTask config save, village totems com Lithostitched, teleportation e informação para neutral players virarem hunters. Runtime QA/interops continuam pendentes.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vampirism-become-a-vampire
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Vampirism 1.10.13 permanece exatamente instalado e continua a release 1.21.1 mais nova auditada; source pin, authorities, lifecycle, addons e testes preservados.
- **Histórico da decisão:** 2026-09-07 — versão reconciliada 1.10.13 e auditoria source-level do provider base concluída na PR #74. Mantido fail-closed para runtime não testado e para semântica de Bloodlines/Vampire Spells não auditada.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Vampirism-1.21-1.10.13.jar`, mod id `vampirism`, versão `1.10.13`. Vampirism é a **authority sobrenatural base** de Vampire/Hunter, blood, níveis, skills, actions, tasks, Lord progression, minions e villages. Addons instalados devem estender essa authority, não recriar facções ou barras paralelas.

## 1. Identidade, release e source auditado
- **Mod:** Vampirism.
- **Versão instalada:** 1.10.13, NeoForge 1.21.1.
- **Canal:** Release.
- **Source pin usado na auditoria granular:** `TeamLapen/Vampirism@e1ed095713cef5e9eb151d0ee58908fa830d6bb7`.
- **Estado:** catálogo source-level do provider base concluído; runtime QA/interops continuam separados.

A modlist física atual confirma 1.10.13 e substitui referências históricas a 1.10.12.

## 2. Facções e progressão
Vampirism fornece duas progressões-base completas:
- **Vampire**;
- **Vampire Hunter**.

O sistema controla transformação/faction state, níveis normais `1–14` e progressão Lord `1–5` onde aplicável. Skills e actions pertencem ao provider; qualquer árvore externa deve consultar/gatear essa progressão, não conceder ranks internamente.

A transformação vampírica pode surgir por vias nativas como bite/blood e altera profundamente a fisiologia do jogador.

## 3. Blood como recurso canônico
Para vampiros, blood é recurso corporal/progressivo do próprio Vampirism e substitui parte da economia vanilla de alimentação.

A auditoria source-level registra a convenção de conversão: **1 blood unit = 100 mB** quando o provider converte para o fluido `vampirism:blood`.

Boundary obrigatório:
- não criar segunda barra de sangue;
- não armazenar “blood” em scoreboard/attachment paralelo para perks;
- mutations devem passar pelo contrato/provider real;
- fluid conversion e player blood state não são automaticamente a mesma superfície de API.

## 4. Feeding, victims e causalidade
Vampires podem alimentar-se de entidades reconhecidas pelo sistema. A vítima, blood disponível, consequências de feeding e conversões pertencem ao provider.

Para Mastery/progressão externa:
- somente ação server-authoritative e causalmente atribuível pode pontuar;
- não pontuar por manter uma vítima próxima, blood bar alta ou drain por tick sem evento discreto deduplicável;
- um mesmo feed/drain não pode ser contado novamente por Vampiric Ageing e RPG Skill Tree sem contrato explícito de autoria independente.

## 5. Skills e Actions — inventário source-level
O catálogo pinado registrou, na base 1.10.13:
- **14 Vampire actions + 3 Hunter + 2 Lord**;
- **31 Vampire skills + 32 Hunter + 3 Lord** no inventário auditado;
- **46 task keys**;
- **10 Entity Actions**;
- **7 Minion Tasks**;
- **47 refinements**;
- **4 attributes**.

Esses números documentam a superfície encontrada no source pin. Não transformá-los em API estável; ids/classes individuais devem ser consultados no source quando uma integração específica precisar deles.

## 6. Actions, cooldowns e server authority
Actions são provider-native. Cooldowns, custos, activation gates, target validation e side effects precisam ser resolvidos no servidor.

RPG Skill Tree não deve:
- disparar a action por imitação de efeito;
- manter cooldown paralelo;
- conceder Mastery por cooldown ticking;
- reaplicar modifiers que a skill/action já fornece.

Se a integração exigir saber se uma action executou com sucesso, usar callback/evento/estado provider comprovado; heurística por animação/partícula é insuficiente.

## 7. Hunter progression
Hunters possuem progressão, equipment, tasks e skills próprios contra vampiros. Essa rota é facção nativa e não uma classe cosmética.

Addons como Vampiric Ageing podem estender hunters depois da progressão-base. Gates externos precisam respeitar a ordem real: faction/rank/skill provider antes de perk dependente.

## 8. Lord progression
A progressão Lord possui state e ações próprias auditadas no provider. Lord XP/rank não devem ser derivados de nível comum ou reconstruídos por uma árvore externa.

Qualquer perk que dependa de Lord deve consultar explicitamente o provider ou ficar fail-closed.

## 9. Tasks, refinements e minions
O source pin registra uma superfície extensa de **tasks**, **refinements**, **Entity Actions** e **Minion Tasks**.

Isso cria riscos de dupla progressão/automação:
- completar task nativa não deve conceder duas vezes a mesma recompensa externa por reload/reopen;
- minion trabalhando autonomamente não gera Mastery por tick para o owner;
- refinement precisa permanecer sob a recipe/process authority do Vampirism;
- ownership de minion deve vir do provider, não de proximity/UUID guess.

## 10. World content e villages
Vampirism adiciona conteúdo de mundo como Vampire Forest, estruturas, NPCs/barons e villages/faction interactions. Vilas podem participar do conflito entre vampiros e hunters.

Worldgen/settlement state deve ser tratado server-side. Para discovery/quests externas:
- usar structure/POI/faction identity real quando disponível;
- reentrar/recarregar a mesma village não concede progresso repetido;
- não inferir controle de faction por blocos visuais ou NPC isolado.

## 11. Sunlight, holy e vulnerabilidades
Vampirism é authority das vulnerabilidades e resistências do vampiro, incluindo sunlight e interações holy reconhecidas.

Outros addons podem traduzir essas regras para seus sistemas; exemplo atual: **Vampire Spells Addon 0.0.9** adapta Holy/Blood do Iron's.

Não duplicar dano solar/holy em mod próprio se o provider ou bridge já processou a consequência.

## 12. Stack sobrenatural atual
A modlist atual inclui, entre outros:
- **Werewolves 2.0.3.3** — terceira facção no ecossistema;
- **Vampiric Ageing 1.4.21** — Age Ranks/endgame;
- **Vampirism Integrations 1.10.2** — bridges externas;
- **Vampire Spells Addon 0.0.9** — Iron's Blood/Holy ↔ Vampirism;
- **Bloodlines 3.0.9** — especializações de facção, conforme catálogo do pack.

Cada addon mantém seu próprio ownership. O core Vampirism continua authority de faction/blood/base progression.

## 13. Client / server e multiplayer
State de faction, levels, blood, skills, actions, villages e mobs é server-authoritative. Cliente renderiza GUI, overlays, models e feedback.

Validar:
- transformação e faction sync;
- blood mutation simultânea;
- skill/action activation em multiplayer;
- reconnect;
- respawn;
- dimension change;
- tracking de minions/mobs;
- village state com múltiplos jogadores.

## 14. Lifecycle crítico
- início/remoção de faction;
- evolução de nível;
- aquisição/reset de skill;
- action activation/cooldown;
- death/respawn;
- relog/server restart;
- dimension change;
- conversion de entity/villager;
- spawn/despawn de minion;
- village ownership/state;
- addon attach/detach em updates.

State externo não deve sobreviver de forma órfã quando o provider remove/transforma a condição correspondente.

## 15. Riscos técnicos
1. **Duplicate authority:** segunda blood bar, segunda faction tree ou segundo cooldown.
2. **Double progression:** mesma ação alimentando provider e Mastery sem deduplicação.
3. **Addon drift:** cada addon suporta faixas próprias de Vampirism.
4. **Persistent state:** faction/skill/minion/village precisam sobreviver lifecycle sem modifiers órfãos.
5. **Damage/heal stacking:** Holy, silver, feeding e combat bridges podem aplicar consequências duas vezes.
6. **Client inference:** VFX/GUI não prova sucesso de mutation no servidor.

## 16. Matriz de testes
- [ ] Dedicated server inicia com Vampirism 1.10.13 + addons instalados.
- [ ] Transformação Vampire/Hunter sincroniza corretamente.
- [ ] Blood gain/spend persiste em relog/restart e não duplica.
- [ ] Level/rank/skill gates impedem ações indevidas.
- [ ] Actions aplicam custo/cooldown exactly-once.
- [ ] Death/respawn preserva/remove state conforme provider.
- [ ] Minions mantêm ownership sem progresso AFK indevido.
- [ ] Village/faction state persiste após chunk unload/reload.
- [ ] Werewolves/Vampiric Ageing não duplicam faction/blood state.
- [ ] Vampire Spells Addon liquida Blood/Holy exactly-once.
- [ ] Vampirism Integrations carrega somente compats elegíveis/configuradas.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências
- **Modlist física 08/09/2026:** `Vampirism-1.21-1.10.13.jar`, mod id `vampirism`, runtime 1.10.13, mixin `vampirism.mixins.json`.
- **Source pin:** `TeamLapen/Vampirism@e1ed095713cef5e9eb151d0ee58908fa830d6bb7` — inventário granular registrado na auditoria anterior.
- Catálogo Gameplay/Magia do projeto: facções Vampire/Hunter, blood economy, skills/actions, world content e relação com addons.

## 18. Limitação
A auditoria source-level base não equivale a runtime QA. Bloodlines, Vampire Spells, Vampiric Ageing e Integrations possuem contratos próprios e devem ser validados separadamente no conjunto exato instalado.

## 19. Revalidação física — 11/09/2026
O runtime físico continua exatamente `Vampirism-1.21-1.10.13.jar`, mod id `vampirism`, versão `1.10.13`. A file list oficial mantém 1.10.13 como a release 1.21.1 mais nova auditada, publicada em 05/09/2026.

O changelog exato desta build registra: correção do salvamento das configs de ActionWheel e MinionTask; correção de totems excessivos em villages quando Lithostitched está instalado; melhoria da teleportation para teleport arrow/action; e informação a neutral players sobre como se tornar hunter. O source-pinned catalog e as authorities de faction/blood/progression permanecem preservados. Nenhum runtime QA do core ou dos addons foi executado nesta recatalogação.

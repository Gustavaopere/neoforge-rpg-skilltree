# Goety — 3.1.4

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81178eeefa49db41145c  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Goety
- **Arquivo JAR:** `goety-3.1.4.jar`
- **Versão 1.21.1:** `3.1.4`
- **Categoria:** Magia; RPG; Exploração; Mobs
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/goety/files/8689429
- **Função:** Provider mágico de Soul Energy, Focus casting por Wands/Staffs, servants/minions, rituals, Research, brewing/witchcraft, artifices, mobs/structures e progressão associada.
- **Dependências:** Goety base 3.1.4. Integrações físicas relevantes: Patchouli 1.21.1-93-NEOFORGE, Curios 9.5.1+1.21.1 e JEI; addons separados Goety Iron 3.1 e Goety Cataclysm 1.21.1-1.8.2.
- **Compatibilidade/Riscos:** Release 3.1.4 exata; source oficial público acessível permanece 1.20 e não é authority da build 1.21.1. Riscos: Soul Energy/cast duplication, servant lifecycle/ownership, ritual partial settlement, Research mirror drift, addon ABI drift e regressão Prisoner item pickup.
- **Sobreposição:** Cobertura forte em necromancia, summons, souls, rituals e várias escolas elementais/void. Recursos permanecem provider-specific: Goety Soul Energy não é Malum spirits, Eidolon souls, Vampirism blood nem recurso Black Arcana.
- **Observações:** Inventário público normalizado: 110 Focuses base/10 famílias, 12 Wands/Staffs, 13 ritual types e 10 Research lines. Esses números servem a coverage/deduplicação, não são claim de registry JAR 110/110. 3.1.4 corrige principalmente crash de servidor quando Prisoner pega item.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release oficial Goety 3.1.4 + documentação oficial pública + inventário Phase 2 previamente reconciliado; source 1.20/forks/decompilação não promovidos a authority 1.21.1.
- **Histórico da decisão:** 07/09/2026: auditoria Phase 2 avançada. Release pública exata 3.1.4 fixada; inventário oficial público normalizado em 110 Focuses base/10 categorias, 12 Wands/Staffs, 13 ritual types e 10 Research lines. Corrigido baseline 109→110 pela inclusão de Order Focus. Exact source/JAR registry/API e runtime QA permanecem pendentes/fail-closed.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Goety 3.1.4 release-pinned; inventário público 110 Focuses/12 Wands-Staffs/13 rituals/10 Research, Soul Energy, casting, servants, lifecycle, integrations, riscos e testes catalogados; internals 3.1.4 mantidos fail-closed.
- **Data da última decisão:** 2026-09-07

## Dossiê operacional — padrão Alex's Mobs

> 🔬 **PADRÃO ALEX'S MOBS — ESCOPO CANÔNICO.** Runtime físico: `goety-3.1.4.jar`, mod id `goety`, NeoForge 1.21.1. A release pública **3.1.4** é pinada exatamente. O source oficial público `Polarice3/Goety-2` acessível está centrado na linha 1.20 e não expõe branch 1.21.1 auditável; por isso registry IDs, classes, métodos e internals específicos da 3.1.4 permanecem fail-closed. O inventário público oficial previamente reconciliado é preservado para coverage/deduplicação, não como claim de enumeração do JAR.

## 1. Identidade e autoridade
Goety é um provider mágico principal do pack. Sua authority inclui **Soul Energy**, casting por **Focus + Wand/Staff**, servants/minions, rituals, Research/unlocks, witchcraft/brewing, artifices alimentados por Soul Energy e progressão de Lichdom quando aplicável ao conteúdo da versão instalada. Integrações externas devem consumir esses estados; não devem criar ledger paralelo de Soul Energy, casts, servants ou settlement ritual.

## 2. Release física 3.1.4
- **JAR:** `goety-3.1.4.jar`.
- **Versão:** `3.1.4`.
- **Minecraft/loader:** 1.21.1 / NeoForge.
- **Distribuição oficial:** release de 20/08/2026.
- **Changelog exato 3.1.4:** corrige principalmente crash de servidor quando um **Prisoner** pega um item, além de outras correções menores.
Esse crash é regression gate obrigatório para servidores com mobs/servants e item pickup.

## 3. Escopo funcional oficial
A documentação oficial descreve Goety como mod de magia com mobs, estruturas, poder arcano, summons/minions, perigos, lore e tesouros. As features publicamente confirmadas incluem:
- Spells por Wands/Staffs equipados com Focus;
- Servants invocados para defender/atacar;
- Brewing de poções próprias;
- Artifices alimentados por Soul Energy;
- estruturas/mobs/exploração associados à progressão.
JEI e Patchouli são recomendados pelo projeto; com Patchouli há o Black Book como superfície de documentação in-game.

## 4. Inventário público normalizado
A auditoria Phase 2 já reconciliou o inventário público oficial em:
- **110 Focuses base** / 10 famílias: Magic 25, Necromancy 11, Geomancy 8, Frost 9, Wild 11, Wind 8, Storm 8, Abyss 8, Nether 10 e Void 12;
- **12 Wands/Staffs**;
- **13 tipos de ritual**;
- **10 linhas de Research**.
O baseline anterior 109 foi corrigido para 110 pela inclusão de **Order Focus**. Estes números são inventário documental para coverage; não significam `JAR 110/110` nem autorizam inferir IDs/mechanics.

## 5. Soul Energy
Soul Energy é recurso provider-native de Goety. A documentação oficial confirma que é usada para alimentar magia/artifices e que a linha histórica de progressão a obtém por morte de mobs. Para a build 3.1.4, custos, storage caps, ganho exato por entidade e métodos internos não são promovidos sem source/JAR exato.
Regra de integração: Black Arcana, RPG Skill Tree ou quests podem **observar/gatear** Soul Energy por contrato confirmado, mas não devem duplicar saldo nem conceder/consumir por uma segunda authority.

## 6. Focus casting
Wands/Staffs recebem Focus para definir spells; a documentação pública mostra troca de Focus durante uso. Cast admission, custo, cooldown, alvo e settlement pertencem ao Goety. Uma bridge não deve disparar dano/spawn duas vezes ao observar simultaneamente input, animation e efeito final.

## 7. Servants e minions
Servants são uma das features centrais. O owner, lifecycle, AI, summon/despawn, persistência e qualquer cap pertencem ao provider Goety ou ao addon que adiciona um servant específico. Addons como **Goety Iron** e **Goety Cataclysm** ampliam esse domínio, mas são catalogados separadamente e não entram no inventário base de 110 Focuses.

## 8. Rituals
A auditoria pública contabiliza 13 tipos de ritual. Ritual admission e settlement devem ser tratados como transação: validar requisitos antes de consumir inputs; impedir dupla conclusão em lag/reload; persistir ou abortar de forma coerente em chunk unload/server restart. Sem source 3.1.4 exato, estruturas de dados e handlers concretos permanecem não afirmados.

## 9. Research e progressão
As 10 linhas de Research constituem progressão provider-native. Quests/RPG Skill Tree podem usar Research como gate/condição, mas não devem manter uma cópia concorrente da progressão interna. Renomes/IDs de research não devem ser hardcoded sem confirmar o JAR ou API suportada.

## 10. Brewing e witchcraft
Brewing é feature oficial e coexistirá com outros sistemas alquímicos do pack. Sobreposição temática não significa identidade semântica. Ingredients, recipes, potion effects e Taglock/witchcraft devem ser comparados por efeito/ID real antes de qualquer deduplicação com Black Arcana, Eidolon ou outro provider.

## 11. Artifices e mundo
Artifices usam Soul Energy segundo a documentação oficial. Como Goety também inclui mobs e estruturas, lifecycle de chunk/worldgen faz parte do risco operacional. Estruturas já geradas não devem ser presumidas retroativamente alteradas por mudança de datapack/config.

## 12. Client / server
A distribuição 3.1.4 é **Client & Server**.
- **Servidor:** authority de Soul Energy, casting que muda gameplay, servants, rituals, research/progress, mobs e world state.
- **Cliente:** UI, models, particles, sounds, book/JEI presentation e input.
Nenhum efeito de gameplay deve depender apenas de animação/GUI local.

## 13. Persistência e lifecycle
Validar login/relog, death/respawn, dimension transfer, chunk unload/reload, server save/restart, datapack reload e atualização do mod. Soul Energy, research, servant ownership e ritual state precisam convergir ao mesmo state authoritative depois desses eventos. Não é afirmado qual mecanismo de persistência interno a 3.1.4 usa sem source exato.

## 14. Multiplayer e exactly-once
Cenários críticos:
- dois jogadores interagindo com o mesmo ritual/estrutura;
- servant owner desconectando ou mudando de dimensão;
- cast sob latência/retry;
- item pickup por Prisoner, especificamente pela correção 3.1.4;
- reward/quest integration observando o mesmo evento Goety.
Side effects devem ocorrer exatamente uma vez no owner/contexto correto.

## 15. Integrações concretas do pack
- **Goety Iron 3.1:** bridge Goety ↔ Iron's Spells; ficha separada.
- **Goety Cataclysm 1.21.1-1.8.2:** bridge Goety ↔ L_Ender's Cataclysm; ficha separada.
- **Patchouli 1.21.1-93-NEOFORGE:** presente fisicamente; o projeto documenta Black Book quando Patchouli está instalado.
- **Curios API 9.5.1+1.21.1:** presente fisicamente e creditado como integração pelo projeto.
- **JEI 19.53.0.426:** presente no pack e recomendado pela documentação oficial.
Integrações adicionais só devem ser declaradas após prova específica.

## 16. Boundaries com outros sistemas mágicos
Goety Soul Energy permanece distinta de **Malum spirits**, **Eidolon souls/Soul Shards**, **Vampirism blood** e recursos próprios de **Black Arcana**. Cobertura nominal de fire/frost/storm/wind/geomancy/void/necromancy não unifica resources, schools ou settlement. **Order Focus** é colisão nominal real, mas não prova equivalência ao conceito de imposed-law/seal do Black Arcana.

## 17. Configuração e data-driven surfaces
O projeto se descreve como altamente configurável, mas esta ficha não inventa nomes/defaults de configs 3.1.4 sem artefato/source exato. Recipes, tags, loot, structures e demais data-driven surfaces precisam ser auditados do JAR/config real antes de uso por KubeJS/quests/mods próprios.

## 18. Riscos técnicos
- source 1.20 ser confundido com 1.21.1/3.1.4;
- Soul Energy duplicada por bridge externa;
- cast liquidado mais de uma vez por input/event/animation;
- servant owner/cap/persistence divergente em reconnect/dimension change;
- ritual consumir inputs e falhar antes do settlement;
- Research/progress mirror ficar stale;
- worldgen/structure data drift em mundo existente;
- addon Goety Iron/Cataclysm assumir versão/API diferente;
- client/server mismatch;
- crash/regressão de item pickup por Prisoner;
- sobreposição temática ser tratada como equivalência de recursos.

## 19. Matriz de testes obrigatória
- [ ] Dedicated server boot com Goety 3.1.4 e addons físicos atuais.
- [ ] Prisoner pega item sem crash — regressão específica 3.1.4.
- [ ] Soul Energy ganha/consome/persiste sem duplicação em relog/restart.
- [ ] Focus swap + cast sob latência executa efeito exatamente uma vez.
- [ ] Servant summon/owner/death/despawn/reconnect/dimension transfer.
- [ ] Dois jogadores e múltiplos servants sem ownership leak.
- [ ] Ritual success/failure/chunk unload/restart sem dupe/loss de inputs.
- [ ] Research unlock persiste e integra com quests sem mirror divergente.
- [ ] Brewing/artifices permanecem server-authoritative.
- [ ] Patchouli Black Book e JEI exibem conteúdo sem data errors.
- [ ] Goety Iron e Goety Cataclysm carregam sem linkage/registry mismatch.
- [ ] Mundo existente e mundo novo após datapack/config change controlado.
- [ ] Logs sem missing registry, codec, mixin ou linkage errors.

## 20. Evidências e limites
- **Modlist física:** `goety-3.1.4.jar` e versões concretas de addons/integrações presentes.
- **Release oficial 3.1.4:** NeoForge 1.21.1; fix principal do crash de Prisoner ao pegar item.
- **Documentação oficial:** spells Focus/Wand/Staff, servants, brewing, artifices/Soul Energy, mobs/structures e integrações Patchouli/Curios.
- **Auditoria Phase 2 preservada:** inventário público 110 Focuses, 12 Wands/Staffs, 13 rituals, 10 Research lines.
- **Limite crítico:** não há source oficial público 1.21.1/3.1.4 auditável no repositório exposto; internals, registry IDs, custos e APIs exatas permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

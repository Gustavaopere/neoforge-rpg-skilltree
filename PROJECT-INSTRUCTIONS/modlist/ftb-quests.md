# FTB Quests — 2101.1.34

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db81c88a4acb0b80b9c9b1  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** FTB Quests
- **Arquivo JAR:** `ftb-quests-neoforge-2101.1.34.jar`
- **Versão 1.21.1:** `2101.1.34`
- **Categoria:** RPG; QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ftb-quests-forge
- **Função:** Framework de quests do ecossistema FTB para capítulos, quest groups, tasks, rewards, progressão individual/equipe e quest books data-driven.
- **Dependências:** FTB Library 2101.1.35 e FTB Teams 2101.1.11 estão fisicamente presentes; FTB Quests 2101.1.34 é Client & Server. Outros addons/scripts podem ampliar task/reward types.
- **Compatibilidade/Riscos:** Provider canônico de quests do pack. Riscos: reward duplicada, shared/team progression divergente, quest-data migration, scripts/tasks custom quebrados, cache/UI stale e tasks de localização/estrutura creditadas sem deduplicação. A 2101.1.34 melhora tracker e corrige cache de ícones.
- **Sobreposição:** É o provider de questing planejado/canônico do pack; pode coexistir com advancements e outros sistemas de progressão, mas ownership e reward settlement devem ser definidos para evitar crédito duplicado.
- **Observações:** JAR físico `ftb-quests-neoforge-2101.1.34.jar`, mod id `ftbquests`, runtime 2101.1.34. Em 09/09/2026 já existe Release 2101.1.35, mas a autoridade de versão do pack continua sendo o JAR físico 1.34 até atualização explícita.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial FTB Quests 2101.1.34 + documentação oficial FTB Quests sobre chapters/tasks/rewards.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — metadata Verificada com corpo vazio corrigida; quest model, tasks/rewards, team/shared state, persistence, scripting e exactly-once boundaries catalogados para 2101.1.34.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 📜 **ESCOPO CANÔNICO.** Runtime físico: `ftb-quests-neoforge-2101.1.34.jar`, mod id `ftbquests`, versão `2101.1.34`. FTB Quests é o **framework de quests/progressão data-driven** do ecossistema FTB e o provider planejado para o quest book do pack.

## 1. Modelo de dados
Quest books são organizados em chapters/chapter groups e quests. Uma quest pode conter múltiplas tasks e rewards; completion deve ser derivada das tasks e do state persistente do provider, não reconstruída por UI externa.

## 2. Tipos de task documentados
A documentação oficial inclui superfícies como Item, Custom, XP Levels, Visit Dimension, Stat, Kill Entity, Location, Checkmark, Advancement, Observation, Visit Biome, Find Structure, Stage, Fluid e Forge Energy. Image é elemento decorativo, não task causal.
Integrações próprias devem usar o tipo de task que corresponde ao evento real, evitando polling por tick quando existe milestone provider-native.

## 3. Rewards e exactly-once
Reward settlement é uma operação sensível: conclusão e claim precisam impedir duplicação em relog, reconnect, team changes e race multiplayer. Uma integração externa não deve conceder a mesma recompensa novamente só porque observou a quest como complete.

## 4. Team/shared progression
FTB Teams 2101.1.11 está fisicamente presente. Quests podem operar com contexto compartilhado conforme configuração/design. State individual e team state não devem ser mesclados por inferência; qualquer bridge precisa consultar o owner/context efetivo da quest.

## 5. Persistência e edição
Quest data e player/team progress precisam sobreviver save/restart. Alterar quest book em mundo existente exige validar IDs e migração: renomear/remover/recriar uma quest pode mudar como progresso antigo é resolvido.

## 6. Build física 2101.1.34
A release 2101.1.34 corrige cache que podia impedir atualização de task/quest icons após edição e melhora performance do pinned quest tracker, especialmente em books grandes. O pack usa exatamente essa build.
Em 09/09/2026 foi publicada a 2101.1.35; isso é apenas sinal de manutenção disponível. O JAR físico 2101.1.34 continua authority até mudança explícita da modlist.

## 7. Client/server boundary
Servidor é authority de progressão, completion e rewards. Cliente renderiza quest book/tracker e envia interações permitidas. Não aceitar claim/progress mutation só porque um widget/ícone local indica conclusão.

## 8. Boundary com mods próprios
RPG Skill Tree, Black Arcana e outros sistemas podem produzir eventos que alimentam quests, mas não devem duplicar o ledger de quest progress. Preferir event/state causal do provider e deduplicação por quest/task ID.

## 9. Riscos
1. **Reward duplication:** claim processado mais de uma vez.
2. **Team divergence:** membro entra/sai da equipe e progresso fica inconsistente.
3. **ID migration:** edição do quest book invalida progresso salvo.
4. **Polling abuse:** location/biome/structure task contabilizada repetidamente.
5. **Script/addon drift:** custom task ou reward quebra após update.
6. **UI stale cache:** tracker mostra state antigo sem alterar o server state real.

## 10. Matriz de testes
- [ ] Dedicated server inicia com FTB Quests 2101.1.34 + Library/Teams atuais.
- [ ] Quest book abre e chapters/tasks/rewards carregam sem data errors.
- [ ] Completion de task ocorre exatamente uma vez.
- [ ] Reward claim não duplica em relog/reconnect/concurrency.
- [ ] Team shared progress segue a configuração efetiva.
- [ ] Visit Biome/Find Structure/Location usam identidade real e deduplicada.
- [ ] Restart preserva progresso e claims.
- [ ] Edição controlada de quest não perde progresso sem migração planejada.
- [ ] Pinned tracker permanece responsivo em book grande.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limitação
- Modlist física: FTB Quests 2101.1.34, FTB Library 2101.1.35 e FTB Teams 2101.1.11.
- CurseForge oficial: changelog 2101.1.34 e Release 2101.1.35 mais recente em 09/09/2026.
- O quest book/scripts reais do pack não foram lidos nesta ficha; portanto não é afirmado quais task types estão atualmente configurados.

# FTB Quests — 2101.1.36

> **Reauditoria física e de migração — 16/09/2026.** A autoridade física atual é `ftb-quests-neoforge-2101.1.36.jar`, mod id `ftbquests`, runtime `2101.1.36`, SHA-1 `b2ede29b98a3022c22065fbe9b1761e385a28683`, em NeoForge 1.21.1. O dossiê abaixo preserva integralmente o conteúdo técnico migrado do Notion e incorpora os deltas 2101.1.35/2101.1.36 agora efetivamente instalados. Nenhum teste de runtime foi promovido como executado.

## Propriedades do registro

- **Mod:** FTB Quests
- **Arquivo JAR:** `ftb-quests-neoforge-2101.1.36.jar`
- **Versão 1.21.1 / runtime:** `2101.1.36`
- **Minecraft / loader:** 1.21.1 / NeoForge
- **Categoria:** RPG; QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ftb-quests-forge
- **Função:** Framework de quests do ecossistema FTB para capítulos, quest groups, tasks, rewards, progressão individual/equipe e quest books data-driven.
- **Dependências:** FTB Library `2101.1.36` e FTB Teams `2101.1.11` estão fisicamente presentes. FTB Quests é Client & Server. Outros addons/scripts podem ampliar task/reward types.
- **Compatibilidade/Riscos:** Provider canônico de quests do pack. Riscos: reward duplicada, shared/team progression divergente, quest-data migration, scripts/tasks custom quebrados, cache/UI stale, tasks de localização/estrutura creditadas sem deduplicação e uso incorreto dos comandos administrativos/hotkeys de edição. A linha instalada herda o fix de permissão de 2101.1.35 e acrescenta as hotkeys/seleção/fixes de UI de 2101.1.36.
- **Sobreposição:** É o provider de questing planejado/canônico do pack; pode coexistir com advancements e outros sistemas de progressão, mas ownership e reward settlement devem ser definidos para evitar crédito duplicado.
- **Observações:** A build física é 2101.1.36. A 2101.1.35 corrigiu a verificação de permissões do jogador em comandos `/ftbquests ...` disparados por outros mods. A 2101.1.36 acrescenta hotkeys rebindáveis de edição e corrige NPE/scrolling da tela de quests.
- **Procedência:** modlist física atual de 16/09/2026 confirma JAR, mod id, runtime e SHA-1. O conteúdo funcional base permanece o dossiê migrado do Notion; os deltas 2101.1.35/2101.1.36 foram revalidados na publicação oficial.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — migração preservada e estado físico atualizado de 2101.1.34 para 2101.1.36.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 📜 **ESCOPO CANÔNICO.** Runtime físico: `ftb-quests-neoforge-2101.1.36.jar`, mod id `ftbquests`, versão `2101.1.36`. FTB Quests é o **framework de quests/progressão data-driven** do ecossistema FTB e o provider planejado para o quest book do pack.

## 1. Modelo de dados
Quest books são organizados em chapters/chapter groups e quests. Uma quest pode conter múltiplas tasks e rewards; completion deve ser derivada das tasks e do state persistente do provider, não reconstruída por UI externa.

## 2. Tipos de task documentados
A documentação oficial inclui superfícies como Item, Custom, XP Levels, Visit Dimension, Stat, Kill Entity, Location, Checkmark, Advancement, Observation, Visit Biome, Find Structure, Stage, Fluid e Forge Energy. Image é elemento decorativo, não task causal.

Integrações próprias devem usar o tipo de task que corresponde ao evento real, evitando polling por tick quando existe milestone provider-native.

## 3. Rewards e exactly-once
Reward settlement é uma operação sensível: conclusão e claim precisam impedir duplicação em relog, reconnect, team changes e race multiplayer. Uma integração externa não deve conceder a mesma recompensa novamente só porque observou a quest como complete.

## 4. Team/shared progression
FTB Teams `2101.1.11` está fisicamente presente. Quests podem operar com contexto compartilhado conforme configuração/design. State individual e team state não devem ser mesclados por inferência; qualquer bridge precisa consultar o owner/context efetivo da quest.

## 5. Persistência e edição
Quest data e player/team progress precisam sobreviver save/restart. Alterar quest book em mundo existente exige validar IDs e migração: renomear/remover/recriar uma quest pode mudar como progresso antigo é resolvido.

## 6. Linha instalada 2101.1.34 → 2101.1.36
A 2101.1.34 corrigiu cache que podia impedir atualização de task/quest icons após edição e melhorou performance do pinned quest tracker, especialmente em books grandes.

A 2101.1.35, agora herdada pela build instalada, corrigiu um bug na verificação de permissões do jogador que podia causar problemas quando outros mods executavam comandos `/ftbquests ...`.

A 2101.1.36, atualmente instalada, acrescenta:
- hotkey rebindável para **force-complete** da quest sob o cursor (`Alt+C` por padrão);
- hotkey rebindável para **force-reset** (`Alt+R`);
- hotkey para **salvar o quest book no servidor** (`Ctrl+S`);
- hotkey para **baixar o quest book para o cliente** (`Shift+Ctrl+S`);
- seleção por caixa com `Alt+LMB` e toggle de seleção com `Ctrl+Alt+LMB`;
- correção de NPE ao colar uma imagem quando já havia imagem(ns) selecionada(s);
- correção do scrolling por setas na tela de quests.

As hotkeys são ferramentas de edição/administração; não alteram por si mesmas a autoridade server-side de progressão/rewards.

## 7. Client/server boundary
Servidor é authority de progressão, completion e rewards. Cliente renderiza quest book/tracker e envia interações permitidas. Não aceitar claim/progress mutation só porque um widget/ícone local indica conclusão.

## 8. Boundary com mods próprios
RPG Skill Tree, Black Arcana e outros sistemas podem produzir eventos que alimentam quests, mas não devem duplicar o ledger de quest progress. Preferir event/state causal do provider e deduplicação por quest/task ID.

## 9. Riscos
1. **Reward duplication:** claim processado mais de uma vez.
2. **Team divergence:** membro entra/sai da equipe e progresso fica inconsistente.
3. **ID migration:** edição do quest book invalida progresso salvo.
4. **Polling abuse:** Location/Visit Biome/Find Structure contabilizada repetidamente.
5. **Script/addon drift:** custom task ou reward quebra após update.
6. **UI stale cache:** tracker mostra state antigo sem alterar o server state real.
7. **Admin hotkeys:** force-complete/reset/save/download devem respeitar contexto/permissões e não ser tratados como gameplay normal.

## 10. Matriz de testes
- [ ] Dedicated server inicia com FTB Quests 2101.1.36 + Library 2101.1.36 + Teams 2101.1.11.
- [ ] Quest book abre e chapters/tasks/rewards carregam sem data errors.
- [ ] Completion de task ocorre exatamente uma vez.
- [ ] Reward claim não duplica em relog/reconnect/concurrency.
- [ ] Team shared progress segue a configuração efetiva.
- [ ] Visit Biome/Find Structure/Location usam identidade real e deduplicada.
- [ ] Restart preserva progresso e claims.
- [ ] Edição controlada de quest não perde progresso sem migração planejada.
- [ ] Pinned tracker permanece responsivo em book grande.
- [ ] Comandos `/ftbquests ...` disparados por outro mod respeitam o fix de permissão herdado de 2101.1.35.
- [ ] Hotkeys 2101.1.36 de complete/reset/save/download funcionam apenas no contexto de edição esperado.
- [ ] Seleção Alt+LMB/Ctrl+Alt+LMB e scrolling por setas não corrompem seleção/state.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limitação
- Modlist física atual: FTB Quests 2101.1.36, FTB Library 2101.1.36 e FTB Teams 2101.1.11.
- Publicação oficial: changelog herdado da 2101.1.34, fix de permissão da 2101.1.35 e hotkeys/seleção/fixes de UI da 2101.1.36.
- O quest book/scripts reais do pack não foram lidos nesta ficha; portanto não é afirmado quais task types estão atualmente configurados.
- A matriz de testes é plano de validação; nenhum resultado de runtime foi inventado.
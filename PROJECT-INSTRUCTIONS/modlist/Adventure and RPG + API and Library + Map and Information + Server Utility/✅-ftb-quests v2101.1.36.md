# FTB Quests

## Propriedades do registro

- **Mod:** FTB Quests
- **Arquivo JAR:** `ftb-quests-neoforge-2101.1.36.jar`
- **Versão 1.21.1:** `2101.1.36`
- **Categoria:** RPG, QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ftb-quests-forge
- **Função:** Framework de quests do ecossistema FTB para capítulos, quest groups, tasks, rewards, progressão individual/equipe e quest books data-driven.
- **Dependências:** FTB Library 2101.1.36 e FTB Teams 2101.1.11 estão fisicamente presentes. FTB Quests 2101.1.36 é Client & Server; outros addons/scripts podem ampliar task/reward types.
- **Compatibilidade/Riscos:** Provider canônico de quests do pack. Riscos: reward duplicada, shared/team progression divergente, quest-data migration, scripts/tasks custom quebrados, cache/UI stale, location/structure tasks sem deduplicação e comandos/hotkeys administrativos usados fora do contexto de edição.
- **Sobreposição:** É o provider de questing planejado/canônico do pack; pode coexistir com advancements e outros sistemas de progressão, mas ownership e reward settlement devem ser definidos para evitar crédito duplicado.
- **Observações:** Runtime físico 2101.1.36. A 2101.1.35 corrige verificação de permissões em comandos `/ftbquests ...` disparados por outros mods. A 2101.1.36 adiciona hotkeys edit-mode, selection box via Alt+LMB e corrige NPE ao colar imagem e scrolling por arrow keys.
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `ftb-quests-neoforge-2101.1.36.jar`, mod id `ftbquests`, runtime `2101.1.36` e SHA-1 `b2ede29b98a3022c22065fbe9b1761e385a28683`. A versão física não mudou nesta rodada; as evidências técnicas já registradas permanecem preservadas.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #290: FTB Quests 2101.1.36 reconfirmado; nenhuma mudança de versão física nesta rodada.
- **Data da última decisão:** 2026-09-06

<callout icon="📜" color="yellow_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `ftb-quests-neoforge-2101.1.36.jar`, mod id `ftbquests`, versão `2101.1.36`. FTB Quests é o **framework de quests/progressão data-driven** do ecossistema FTB e o provider planejado para o quest book do pack.
</callout>

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

## 6. Linha instalada 2101.1.34 → 2101.1.36

A release 2101.1.34 corrigiu cache que podia impedir atualização de task/quest icons após edição e melhorou performance do pinned quest tracker, especialmente em books grandes. Essa era a build física na auditoria anterior e permanece documentada como histórico.
Em 09/09/2026 foi publicada a **2101.1.35**, que corrigiu um bug na verificação de permissões do jogador quando outros mods executavam comandos `/ftbquests ...`. Esse fix agora é herdado pela build física atual 2101.1.36.

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

- [ ] Dedicated server inicia com FTB Quests 2101.1.36 + FTB Library 2101.1.36 + FTB Teams 2101.1.11.
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

- Modlist física atual: FTB Quests 2101.1.36, FTB Library 2101.1.36 e FTB Teams 2101.1.11.
- CurseForge oficial: changelog 2101.1.34 e release 2101.1.35 de 09/09/2026, cujo fix é a verificação de permissões em comandos `/ftbquests ...` executados por outros mods.
- O quest book/scripts reais do pack não foram lidos nesta ficha; portanto não é afirmado quais task types estão atualmente configurados.

## 12. Atualização instalada — 2101.1.35 e 2101.1.36

A build física atual herda o fix 2101.1.35 de verificação de permissões em comandos `/ftbquests ...` executados por outros mods.
A 2101.1.36, publicada em 15/09/2026, acrescenta:
- hotkeys rebindáveis de edit mode: force-complete quest (Alt-C), force-reset (Alt-R), force-save server data (Ctrl+S) e download quest book data (Shift+Ctrl+S);
- Alt + botão esquerdo para arrastar selection box, além do middle button; Ctrl+Alt+LMB alterna seleção;
- fix de NPE client-side ao colar imagem com imagens já selecionadas;
- fix de arrow keys para scrolling da quest screen.

Regression gates adicionais:
- [ ] comandos externos respeitam permission checks;
- [ ] hotkeys administrativas funcionam somente no contexto autorizado de edição;
- [ ] seleção por mouse não duplica/omite quests;
- [ ] colar imagens não causa NPE;
- [ ] scrolling por setas funciona sem alterar state do servidor.

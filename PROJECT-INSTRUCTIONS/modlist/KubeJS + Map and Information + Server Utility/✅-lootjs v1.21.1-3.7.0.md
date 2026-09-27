# LootJS

## Propriedades do registro

- **Mod:** LootJS
- **Arquivo JAR:** lootjs-neoforge-1.21.1-3.7.0.jar
- **Versão 1.21.1:** 1.21.1-3.7.0
- **Categoria:** Automação, Compat, QoL
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lootjs
- **Função:** Addon KubeJS server-side para modificar loot tables e loot gerado por scripts, com filters, conditions, actions/functions, substituições e composite loot entries.
- **Dependências:** KubeJS NeoForge 2101.7.2-build.377 está fisicamente presente. Loot providers/loot tables alvo pertencem aos respectivos mods e datapacks; LootJS apenas os modifica por script.
- **Compatibilidade/Riscos:** Camada server-side de scripting sobre loot tables. Riscos: dupla injeção com outros loot modifiers/datapacks, replaceLoot preservando componentes/quantidade de forma inesperada, referências recursivas, ordem de scripts e reload stale. 3.7.0 adiciona composite entries em replaceLoot.
- **Sobreposição:** Pode modificar as mesmas loot tables que datapacks, Global Loot Modifiers e outros addons; isso é composição configurável. O resultado efetivo depende da ordem e dos scripts do pack.
- **Observações:** JAR físico `lootjs-neoforge-1.21.1-3.7.0.jar`, mod id `lootjs`, runtime metadata `1.21.1-3.7.0`; release pública 3.7.0 de 29/04/2026.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge oficial LootJS confirmando `LootJS-NeoForge-1.21.1-3.7.0` como release 1.21.1 atual + documentação/source 1.21.1 já auditados; KubeJS físico atual 2101.7.2-build.377.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — LootJS 1.21.1-3.7.0/JAR físico reconfirmado; KubeJS físico reconciliado para 2101.7.2-build.377. Loot-table API, composite entries em `replaceLoot`, reload lifecycle, provider boundary e riscos de double injection preservados.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #386: JAR `lootjs-neoforge-1.21.1-3.7.0.jar`, mod id `lootjs`, runtime `1.21.1-3.7.0`, SHA-1 `54ffddd1763ae4af84e9bbcb22e4081a1e7f3556`.

<callout icon="🎁" color="yellow_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `lootjs-neoforge-1.21.1-3.7.0.jar`, mod id `lootjs`, runtime metadata `1.21.1-3.7.0`. LootJS é uma **camada de scripting server-side para loot** sobre KubeJS; não possui economia própria sem scripts que modifiquem loot tables/eventos.
</callout>
## 1. Loot-table authority
Loot tables continuam pertencendo ao Minecraft/mod/datapack que as registra. LootJS oferece APIs para ler/modificar sua composição e o loot produzido. Uma integração deve distinguir claramente provider original, modificação por script e resultado final.
## 2. APIs e entries
A linha 1.21.1 expõe manipulação direta de loot tables e estruturas de entries. O modelo suporta item/empty/tag/reference e composições como alternatives, sequences e groups. A 3.7.0 adiciona explicitamente suporte a composite entries em `replaceLoot`.
## 3. Conditions, actions e functions
Modifiers seguem pipeline semelhante ao de loot tables: conditions controlam quando uma regra se aplica; actions/functions transformam/adicionam/removem loot. Ordem importa. Duas regras válidas podem se somar e causar inflação se não houver exclusão/deduplicação.
## 4. Replacement semantics
`replaceLoot` substitui loot correspondente e versões anteriores da linha adicionaram opções de preservação de count/component types. A 3.7.0 amplia essa ação para entries compostas. Não presumir que substituir um item seja equivalente a editar a loot table source: o comportamento depende do script e do momento do pipeline.
## 5. Reload lifecycle
Scripts/loot data podem ser recarregados. Validar que reload remove/recria handlers corretamente, sem acumular modifiers ou manter cache de table antiga. Erro de script deve falhar de modo observável e não deixar parte do loot pipeline em estado híbrido.
## 6. Client/server boundary
Loot generation é server-authoritative. Cliente, JEI ou AdvancedLootInfo podem representar informação, mas não são source of truth do drop. Scripts que concedem/removem loot devem executar no contexto server apropriado.
## 7. Composição com o pack
O pack possui muitos mods que adicionam loot e também outros mecanismos de integração. LootJS pode tocar a mesma table que datapacks/Global Loot Modifiers. Isso não é conflito automático, mas exige inventário dos scripts para saber se a combinação soma, substitui ou condiciona entries.
## 8. Riscos
1. **Double injection:** duas regras adicionam o mesmo loot.
2. **Replacement loss:** componentes/count/NBT são descartados indevidamente.
3. **Recursive reference:** loot table reference forma ciclo ou expansão inesperada.
4. **Order dependency:** resultado muda conforme ordem de scripts/modifiers.
5. **Reload duplication:** listeners/modifiers acumulam após reload.
6. **Economy inflation:** rare drops deixam de ser raros por regras globais amplas.
## 9. Boundary para quests/perks
Drop gerado não deve ser usado como prova automática de kill/completion; a causa pode ser modifier, chest, fishing ou outra source. Se uma quest exige kill causal, usar o evento de kill/provider, não a mera obtenção do item modificado por LootJS.
## 10. Matriz de testes
- [ ] Dedicated server inicia com LootJS 3.7.0 + KubeJS atual.
- [ ] Scripts de loot compilam/carregam sem errors.
- [ ] Add/remove/replace de item ocorre exatamente conforme regra.
- [ ] Composite alternatives/sequences/groups em `replaceLoot` funcionam sem crash.
- [ ] `/reload` não duplica modifiers.
- [ ] Loot modded preserva components/count quando o script assim determina.
- [ ] Referências entre loot tables não criam recursion.
- [ ] Multiplayer/chest generation não duplica settlement.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 11. Evidências e limitação
- Modlist física: `lootjs-neoforge-1.21.1-3.7.0.jar`; KubeJS 2101.7.2-build.377 também presente.
- CurseForge/GitHub oficiais: Release 3.7.0 e composite loot entries em `replaceLoot`; changelog 1.21.1 registra evolução de pools/filters/replacement.
- Os scripts reais do pack não foram auditados nesta ficha. Nenhuma modificação concreta de loot é atribuída ao pack sem localizar o script correspondente.

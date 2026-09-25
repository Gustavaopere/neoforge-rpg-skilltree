# Just Enough Resources (JER)

## Propriedades do registro

- **Mod:** Just Enough Resources (JER)
- **Arquivo JAR:** JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar
- **Versão 1.21.1:** 1.6.0.17
- **Categoria:** QoL, Worldgen
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer
- **Função:** Addon de JEI que apresenta informação de recursos do mundo, drops de mobs, loot e villagers para consulta; não altera o worldgen, loot tables ou trades authoritative.
- **Dependências:** JEI e NeoForge 1.21.1. Pack físico usa JEI 19.56.0.440. Build física JER 1.6.0.17 é a linha oficial NeoForge 1.21.1 instalada.
- **Compatibilidade/Riscos:** 1.6.0.17 é Alpha oficial. Riscos: dados de ore/worldgen/loot stale com datapacks/TFC/worldgen customizado, JEI API drift, informações divergentes de loot dinâmico e sobreposição parcial com Advanced Loot Info. O runtime/provider sempre prevalece sobre a UI.
- **Sobreposição:** Sobreposição parcial com Advanced Loot Info no domínio de loot. JER também cobre recursos/worldgen e villagers, portanto não é duplicata completa. Não substitui JEI nem os providers reais de loot/worldgen.
- **Observações:** Build 1.6.0.17 permanece Alpha; o changelog exato adiciona tradução portuguesa (#518). A antiga 1.6.0.12 Beta é uma build anterior, não um upgrade. Nenhuma troca física foi feita.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + CurseForge/Modrinth oficiais JER 1.6.0.17 para Minecraft 1.21.1 + changelog exato commit c8341e5 + JEI físico 19.56.0.440. Decisão Manter previamente registrada foi preservada.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — JER 1.6.0.17/JAR físico reconfirmado; JEI físico reconciliado para 19.56.0.440. 1.6.0.17 permanece a build NeoForge 1.21.1 mais recente localizada; decisão Manter preservada.
- **Histórico da decisão:** 2026-09-06 — decisão fechada em Manter. A classificação Alpha é preservada como risco de maturidade, mas não há base para downgrade automático: 1.6.0.17 é a linha oficial 1.21.1 posterior e de adoção ampla.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #356: JAR `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`, mod id `jeresources`, runtime `1.6.0.17`, SHA-1 `e739d69b2de31ca8c0cb4691ab08e823f153bc44`.

<callout icon="⛏️" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`, mod id `jeresources`, versão `1.6.0.17`. A build oficial é **Alpha** para NeoForge 1.21.1. **Decisão ****`Manter`**** preservada.**
</callout>
## 1. Papel e authority
Just Enough Resources adiciona ao JEI informação sobre recursos, mobs, worldgen, loot e villagers. É uma camada de consulta; loot tables, ore placement, spawn e trades continuam pertencendo ao jogo/mod/datapack que os define.
## 2. Canal Alpha
O rótulo Alpha da 1.6.0.17 é mantido como risco de maturidade. A existência de 1.6.0.12 Beta não torna essa build anterior automaticamente superior; não houve downgrade físico nesta auditoria.
## 3. JEI
O pack usa JEI 19.56.0.440. JER depende do viewer para categories/entries. Mudanças na API JEI podem quebrar apresentação mesmo sem alterar loot/worldgen reais.
## 4. Mob drops
JER pode apresentar drops associados a mobs. Loot condicional por difficulty, looting, killer, biome, equipment ou outro mod pode não caber em um resumo estático. O servidor/loot table final é a authority.
## 5. Worldgen e recursos
Informação de geração de minérios/recursos é especialmente sensível a datapacks e worldgen customizado. O pack possui TFC e vários mods de geração; qualquer faixa/distribuição exibida deve ser validada contra o data state real antes de virar regra de quest/progressão.
## 6. Dungeon loot
Entries de loot são documentação probabilística. Uma chest/structure específica pode ter pool alterado por integrações, datapacks ou loot modifiers. Não garantir obtenção de item só porque JER o associa a uma fonte.
## 7. Villagers
O addon também pode apresentar informação ligada a villagers. Profession/trade assignment e preços continuam server-authoritative e podem ser modificados por outros mods.
## 8. Sobreposição com Advanced Loot Info
Há sobreposição parcial no domínio de loot. Isso não torna os mods idênticos: JER também cobre worldgen/resources/villagers. A decisão de manter ambos deve considerar qualidade da UI, cobertura e custo, não apenas nomes de categorias semelhantes.
## 9. Release 1.6.0.17
O changelog exato publicado para essa build adiciona **tradução portuguesa** (#518, commit `c8341e5`). Não atribuir fixes de worldgen/loot à 1.6.0.17 sem evidência adicional.
## 10. Client / server
A build é classificada client-side. JER lê/apresenta dados para o usuário; não deve alterar geração, drops ou trade settlement. Informação client-side divergente resolve a favor do servidor/data authoritative.
## 11. Lifecycle
Validar client boot, JEI plugin registration, world join, data/resource reload, language change e modpack update. Índices devem reconstruir sem duplicar entries ou reter recursos removidos.
## 12. Riscos técnicos
- ore/worldgen info stale sob datapacks/TFC;
- loot display não refletir loot modifiers condicionais;
- mob drop chance interpretada como garantia;
- villager/trade info stale;
- JEI API drift;
- Alpha regression;
- duplicate information com outro plugin;
- cache não invalidado após data change.
## 13. Matriz de testes obrigatória
- [ ] Cliente inicia com JER 1.6.0.17 + JEI 19.56.0.440.
- [ ] Mob drop category abre para amostra vanilla/modded.
- [ ] Worldgen/resource info não causa crash com stack TFC/worldgen.
- [ ] Loot entry é comparada a uma fonte authoritative real.
- [ ] Villager info abre sem registry/reference error.
- [ ] Tradução portuguesa carrega sem missing keys críticas.
- [ ] Data/resource reload não duplica entries.
- [ ] Advanced Loot Info coexistente não produz conflito funcional crítico.
- [ ] JER não altera drops/worldgen/trades.
- [ ] Upgrade futuro de JEI/JER é validado em conjunto.
## 14. Evidências e limites
- **Modlist física:** JAR/mod id/version e JEI atual.
- **CurseForge/Modrinth oficiais:** build 1.6.0.17, canal Alpha e escopo mobs/worldgen/villagers.
- **Changelog:** tradução portuguesa #518 em 1.6.0.17.
- **Limite:** nenhum arquivo de worldgen/loot efetivo do pack foi inferido a partir da UI; dados exibidos precisam de confirmação para decisões de progressão.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

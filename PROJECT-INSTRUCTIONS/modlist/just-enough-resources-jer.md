# Just Enough Resources (JER)

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815b85b3c4fbd3714758
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JER `1.6.0.17` e JEI `19.53.0.426` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Just Enough Resources (JER)
- **Arquivo JAR:** `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`
- **Versão 1.21.1:** 1.6.0.17
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** QoL, Worldgen
- **Função:** Addon de JEI que apresenta informação de recursos do mundo, drops de mobs, loot e villagers para consulta; não altera o worldgen, loot tables ou trades authoritative.
- **Dependências:** JEI e NeoForge 1.21.1. Pack físico usa JEI 19.53.0.426. Build física JER 1.6.0.17 é a linha oficial NeoForge 1.21.1 instalada.
- **Sobreposição:** Sobreposição parcial com Advanced Loot Info no domínio de loot. JER também cobre recursos/worldgen e villagers, portanto não é duplicata completa. Não substitui JEI nem os providers reais de loot/worldgen.
- **Compatibilidade/Riscos:** 1.6.0.17 é Alpha oficial. Riscos: dados de ore/worldgen/loot stale com datapacks/TFC/worldgen customizado, JEI API drift, informações divergentes de loot dinâmico e sobreposição parcial com Advanced Loot Info. O runtime/provider sempre prevalece sobre a UI.
- **Observações:** Build 1.6.0.17 permanece Alpha; o changelog exato adiciona tradução portuguesa (#518). A antiga 1.6.0.12 Beta é uma build anterior, não um upgrade. Nenhuma troca física foi feita.
- **Procedência:** modlist.txt física atual + CurseForge/Modrinth oficiais JER 1.6.0.17 para Minecraft 1.21.1 + changelog exato commit c8341e5 + JEI físico 19.53.0.426. Decisão Manter previamente registrada foi preservada.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/just-enough-resources-jer
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — JER 1.6.0.17 Alpha oficial; mob/worldgen/loot/villager display authority, JEI integration, custom-worldgen risks, Advanced Loot Info overlap, lifecycle e testes catalogados. Decisão Manter preservada.
- **Histórico da decisão:** 2026-09-06 — decisão fechada em Manter. A classificação Alpha é preservada como risco de maturidade, mas não há base para downgrade automático: 1.6.0.17 é a linha oficial 1.21.1 posterior e de adoção ampla.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `JustEnoughResources-NeoForge-1.21.1-1.6.0.17.jar`, mod id `jeresources`, versão `1.6.0.17`. A build oficial é **Alpha** para NeoForge 1.21.1. **Decisão `Manter` preservada.**

## 1. Papel e authority
Just Enough Resources adiciona ao JEI informação sobre recursos, mobs, worldgen, loot e villagers. É uma camada de consulta; loot tables, ore placement, spawn e trades continuam pertencendo ao jogo/mod/datapack que os define.

## 2. Canal Alpha
O rótulo Alpha da 1.6.0.17 é mantido como risco de maturidade. A existência de 1.6.0.12 Beta não torna essa build anterior automaticamente superior; não houve downgrade físico nesta auditoria.

## 3. JEI
O pack usa JEI 19.53.0.426. JER depende do viewer para categories/entries. Mudanças na API JEI podem quebrar apresentação mesmo sem alterar loot/worldgen reais.

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
- [ ] Cliente inicia com JER 1.6.0.17 + JEI 19.53.0.426.
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

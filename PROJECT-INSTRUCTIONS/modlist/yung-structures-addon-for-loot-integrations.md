# YUNG Structures Addon for Loot Integrations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816abc23f1edbce8d1aa
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** YUNG Structures Addon for Loot Integrations
- **Arquivo JAR:** `lootintegrations_yungs-1.6.jar`
- **Versão 1.21.1:** 1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Worldgen, Exploração
- **Função:** Adapta Loot Integrations às estruturas da série YUNG's Better, enriquecendo suas tabelas de loot.
- **Dependências:** Loot Integrations 4.7 + mods YUNG alvo presentes; YUNG's API 5.1.8 é dependência da família YUNG. Cupboard 4.1 é dependência do framework Loot Integrations.
- **Sobreposição:** Bridge YUNG↔Loot Integrations. Não substitui mods YUNG, YUNG's API ou Lootr; pode sobrepor LootJS/datapacks/outros modifiers nas mesmas loot tables.
- **Compatibilidade/Riscos:** Riscos: version drift/publication mismatch do JAR 1.6 em ambiente 1.21.1, IDs de loot YUNG alterados, double injection com LootJS/datapacks, inflação econômica, reload stale/duplicado e multiplicação com Lootr.
- **Observações:** Filename/publicação: 1.6; metadata runtime canônica no JAR físico: 1. A listagem atual do CurseForge associa 1.6 a versões posteriores e mostra 1.3 como arquivo explicitamente 1.21.1; presença física de 1.6 confirmada, compatibilidade operacional ainda requer teste.
- **Procedência:** modlist.txt física atual + página/listagem oficial do CurseForge para YUNG Structures Addon + stack YUNG físico + comportamento do Loot Integrations 4.7 auditado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yung-structures-addon-for-loot-integrations
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — YUNG addon 1.6/publicação reconciliado com runtime metadata 1; targets físicos, ownership, server-side, Lootr/LootJS, riscos e testes catalogados. LIMITAÇÃO: CurseForge atual não marca o arquivo 1.6 especificamente para 1.21.1; 1.3 é o arquivo explicitamente listado para 1.21.1.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🏛️ **ESCOPO CANÔNICO.** Runtime físico: `lootintegrations_yungs-1.6.jar`, mod id `lootintegrations_yungs`, metadata runtime `1`; o filename/publicação é `1.6`. É um **addon server-side/data-driven de Loot Integrations** para estruturas YUNG. **Divergência documentada:** a listagem atual do CurseForge associa o arquivo 1.6 a versões posteriores, enquanto `1.3` é o arquivo explicitamente listado para Minecraft 1.21.1; a presença física de 1.6 no pack é confirmada, mas compatibilidade operacional 1.21.1 dessa publicação não é inferida como testada.

## 1. Identidade e versionamento
- **JAR físico:** `lootintegrations_yungs-1.6.jar`.
- **Mod id:** `lootintegrations_yungs`.
- **Runtime metadata canônica:** `1`.
- **Filename/publicação:** `1.6`.
- **Ambiente do pack:** Minecraft 1.21.1 / NeoForge.
A coluna de versão permanece `1`, conforme a metadata física. A publicação `1.6` é preservada separadamente e sua marcação pública de game version é tratada como divergência, não como prova de incompatibilidade nem de compatibilidade.

## 2. Papel no modpack
O addon adiciona regras de integração para melhorar/diversificar loot em estruturas da série **YUNG's Better** por meio do Loot Integrations. A descrição oficial informa que o loot ganha mais variação e é escolhido a partir de tabelas vanilla comparáveis, que também podem conter loot modded.

## 3. Targets documentados pelo projeto
A página oficial enumera suporte para:
- Better Desert Temples;
- Better Jungle Temples;
- Better Dungeons;
- Better Nether Fortresses;
- Better Ocean Monuments;
- Better Strongholds;
- Better Witch Huts;
- Extras.
Essa é a superfície declarada do addon como projeto; ela não implica que todos esses mods estejam instalados neste pack.

## 4. Interseção com a modlist física
No runtime atual foram confirmados: **YUNG's Better Desert Temples 4.1.5**, **Better Dungeons 5.1.4**, **Better Jungle Temples 3.1.2**, **Better Nether Fortresses 3.1.5**, **Better Ocean Monuments 4.1.2** e **Better Witch Huts 4.1.1**, além de **YUNG's API 5.1.8**. Better Strongholds e Extras não foram encontrados como mods top-level na modlist física consultada; portanto não são tratados como integrações ativas deste pack nesta auditoria.

## 5. Autoridade e ownership
**Loot Integrations 4.7** é authority do mecanismo de composição/injeção. Cada mod YUNG continua authority de sua estrutura, placement, templates e tabelas-base. O addon é apenas a bridge de dados; não registra uma terceira cópia das estruturas, não controla worldgen e não substitui YUNG's API.

## 6. Server-side, dados e lifecycle
O projeto oficial declara **server-side only**. O comportamento relevante ocorre no servidor quando loot tables/recursos são carregados e quando containers resolvem loot. Mudanças de datapack ou de versão precisam ser regressadas em `/reload` e restart; o cliente não é authority da recompensa final.

## 7. Interação com Lootr e outros modifiers
Lootr pode individualizar por jogador os containers das estruturas YUNG já enriquecidos. LootJS/datapacks/outros modifiers podem transformar as mesmas tabelas. O risco principal é composição repetida, ordem inesperada e multiplicação econômica em multiplayer, não uma disputa de ownership sobre a estrutura.

## 8. Divergência de publicação 1.6
Na consulta atual ao CurseForge, `lootintegrations_yungs-1.6.jar` aparece associado a `26.1.2+8`, enquanto `lootintegrations_yungs-1.3.jar` aparece explicitamente como `1.21.1+4`. Como a modlist física contém 1.6, o artefato instalado permanece a autoridade de presença. Porém esta ficha **não afirma** que a publicação 1.6 foi oficialmente marcada para 1.21.1 nem que seu boot foi testado aqui. Esse ponto é um gate obrigatório de runtime.

## 9. Riscos técnicos e de balanceamento
1. **Version drift / publication mismatch** entre o JAR 1.6 instalado e a marcação pública atual de game version.
2. **Missing/renamed loot-table IDs** após updates dos mods YUNG.
3. **Double injection** com LootJS/datapacks.
4. **Economy inflation** pela soma de várias estruturas YUNG enriquecidas.
5. **Reload stale/duplicado** durante alterações de datapack.
6. **Lootr multiplication** por recompensas individualizadas em multiplayer.
7. Tratar Better Strongholds/Extras como ativos apesar de não aparecerem na modlist física atual.

## 10. Matriz de testes
- [ ] Dedicated server 1.21.1 inicia com `lootintegrations_yungs` 1.6 + Loot Integrations 4.7.
- [ ] Não há erro de versão/loader ou datapack relacionado ao publication mismatch.
- [ ] Desert Temple, Jungle Temple, Dungeon, Nether Fortress, Ocean Monument e Witch Hut YUNG presentes resolvem loot válido.
- [ ] Ausência de Better Strongholds/Extras não causa erro.
- [ ] Cada integração é aplicada uma única vez.
- [ ] `/reload` e restart não duplicam nem deixam regras antigas.
- [ ] LootJS/datapacks não repetem o mesmo enrichment.
- [ ] Lootr preserva um fill coerente por jogador.
- [ ] A densidade agregada de recompensas continua compatível com a progressão do pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limites
Evidências utilizadas: modlist física atual; metadata extraída no inventário físico; página oficial do YUNG Structures Addon for Loot Integrations; listagem oficial de arquivos do CurseForge; stack YUNG fisicamente presente; Loot Integrations 4.7 e Cupboard 4.1 no pack. A divergência de game-version da publicação 1.6 permanece explicitamente aberta até teste de runtime ou evidência oficial específica para 1.21.1.

# Integrated Cataclysm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814e94a4e5c7af79dd50
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Integrated Cataclysm
- **Arquivo JAR:** `integrated_cataclysm-1.0.6+1.21.1-neoforge.jar`
- **Versão 1.21.1:** 1.0.6+1.21.1-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Worldgen, Exploração
- **Função:** Overhaul das estruturas de L_Ender's Cataclysm no estilo Integrated, adicionando dungeons mais detalhadas com decoração de Quark/Supplementaries e mecanismos, puzzles e traps de Create.
- **Dependências:** Obrigatórias oficiais e presentes: Integrated API 1.8.0, L_Ender's Cataclysm 3.33, Create 6.0.10, Supplementaries 3.9.8 e Quark 4.1-483.
- **Sobreposição:** Não substitui Cataclysm como provider de bosses/mobs/items. Substitui/expande o contexto estrutural de suas áreas e pode sobrepor função geral de outros structure mods; deduplicação deve ser por structure_set/placement/loot, não pelo nome do boss.
- **Compatibilidade/Riscos:** Worldgen bridge de alta densidade. Riscos: structure-set collisions, loot/spawner inflation, recipe overlap, referências a providers ausentes, Create mechanism state em estruturas, hybrid chunks após update e Cataclysm boss/progression duplicados por integrações externas.
- **Observações:** Release 1.0.6 é o port oficial para Minecraft/NeoForge 1.21.1. O projeto também altera algumas recipes para integrar os mods envolvidos; recipe/data overrides devem ser tratados como data-driven e version-sensitive.
- **Procedência:** modlist.txt física atual + CurseForge oficial Integrated Cataclysm 1.0.6 file 7808552 + descrição oficial de structures/dependencies/configuration; source público exato não foi promovido a authority nesta auditoria.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-cataclysm/files/7808552
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Integrated Cataclysm 1.0.6 release-pinned; overhaul de estruturas Cataclysm, Create puzzles/traps, recipes/datapacks, structure-set control, provider ownership, worldgen/lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `integrated_cataclysm-1.0.6+1.21.1-neoforge.jar`, mod id `integrated_cataclysm`, versão `1.0.6+1.21.1-neoforge`. O CurseForge oficial confirma a release 1.0.6 para NeoForge 1.21.1; esta ficha é release-pinned.

## 1. Papel e authority
Integrated Cataclysm é o módulo da série Integrated que reformula as estruturas de **L_Ender's Cataclysm** em dungeons mais detalhadas e integradas a outros mods. Cataclysm continua authority de bosses, mobs, AI, attacks e itens; Integrated Cataclysm controla seus templates, placement, decoração, puzzles/traps e alterações de data/recipes que publica.

## 2. Dependências obrigatórias
A release exige Integrated API, L_Ender's Cataclysm, Create, Supplementaries e Quark. No pack físico estão Integrated API 1.8.0, Cataclysm 3.33, Create 6.0.10, Supplementaries 3.9.8 e Quark 4.1-483. Atualizar qualquer provider pode quebrar block IDs, tags, recipes ou mecanismos referenciados pelas estruturas.

## 3. Overhaul estrutural
O projeto declara que **overhauls Cataclysm structures with heavily detailed dungeons**. Isso muda a experiência de exploração e o contexto dos encounters sem transferir ownership do boss para a bridge. Quests que detectam boss kill devem observar o evento real do Cataclysm, não a mera entrada na estrutura Integrated.

## 4. Create: portas, puzzles e traps
As estruturas podem empregar mecanismos do Create em portas, puzzles e armadilhas. Block entities/contraptions continuam sendo state do Create. Templates não devem duplicar sua lógica; após geração, assembly, redstone/kinetics e chunk reload precisam manter o comportamento do provider.

## 5. Quark e Supplementaries
Esses mods fornecem blocos/decor e possíveis block entities usados pelo template. A bridge não passa a ser owner desses registries. Remover ou trocar versões depois que chunks foram gerados cria risco de missing blocks, mudança de propriedades e conteúdo persistido incompatível.

## 6. Recipes integradas
O projeto informa alterações de algumas recipes para integrar os mods usados. Recipes são data contracts: qualquer override externo/KubeJS deve comparar o recipe ID final carregado e não criar uma segunda rota acidentalmente mais barata ou duplicar outputs.

## 7. Configuração por datapack
A documentação do ecossistema Integrated permite alterar recipes e controles de worldgen por data. Structure sets usam spacing/separation; separation não pode exceder spacing. O projeto também documenta mecanismo de avoid-list por tag de `structure_set`. Mudanças precisam ser testadas em seed fixa e chunks novos.

## 8. Worldgen e densidade
O pack contém vários structure providers. O risco relevante é coexistência espacial: spacing, biome targeting, terrain fit, loot e spawner density. Dois mods de estruturas não são automaticamente redundantes, mas podem produzir progressão excessivamente concentrada se gerarem próximos ou oferecerem rewards equivalentes.

## 9. Loot, spawners e progressão Cataclysm
Qualquer boss/loot original permanece sob regras do Cataclysm. Integrated Cataclysm pode mudar containers e cenário, porém sistemas externos não devem conceder loot de boss de novo por detectar template, advancement ou chest. Settlement precisa ser exatamente uma vez.

## 10. Release 1.0.6
O changelog da build instalada identifica **1.0.6 como o port para 1.21**. Não foi publicado nesse changelog um inventário de alterações internas adicional; portanto não são atribuídos fixes ou estruturas novas específicas além do escopo documentado do port.

## 11. Client / server
A distribuição é Client & Server. O servidor é authority de worldgen, loot, mob spawn, puzzle state e gameplay dos blocos. O cliente renderiza assets e interfaces dos providers. Registry/data mismatch entre cliente e servidor pode impedir conexão ou produzir conteúdo incorreto.

## 12. Lifecycle e persistência
Validar world creation, first-chunk generation, structure placement, mechanism activation, boss encounter, chunk unload/reload, save/restart e `/reload`. Atualizações não reconstruem estruturas já persistidas; regiões antigas e novas podem divergir.

## 13. Riscos técnicos
- missing registry/provider em template;
- colisões de structure sets/densidade excessiva;
- puzzles Create quebrados após unload;
- recipe override duplicado ou progressão barateada;
- loot/spawner inflation;
- boss reward duplicado por listener externo;
- update de Cataclysm/Create/Quark/Supplementaries quebrar templates;
- chunks híbridos após update;
- tratar a bridge como owner dos bosses Cataclysm.

## 14. Matriz de testes obrigatória
- [ ] Dedicated server inicia com os cinco requisitos físicos.
- [ ] Datapacks/recipes carregam sem missing registry ou duplicate IDs.
- [ ] Amostra de estruturas gera corretamente em chunks novos.
- [ ] Create doors/puzzles/traps funcionam após unload/reload.
- [ ] Blocos Quark/Supplementaries mantêm state e aparência.
- [ ] Boss Cataclysm aparece/progride sem duplicação de rewards.
- [ ] Loot/chests não geram rota de progressão quebrada.
- [ ] Structure spacing/separation é validado contra outros worldgen mods.
- [ ] `/reload` não invalida data carregada.
- [ ] Restart preserva estrutura e encounter state.

## 15. Evidências e limites
- **Modlist física:** filename/mod id/version e providers atuais.
- **CurseForge oficial:** release 1.0.6, port 1.21, escopo estrutural e dependencies.
- **Documentação oficial:** uso de Create/Quark/Supplementaries, recipe integration e controles de worldgen/data.
- **Limite:** source code exato da 1.0.6 não foi pinado; classes, IDs internos e inventário completo de templates permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

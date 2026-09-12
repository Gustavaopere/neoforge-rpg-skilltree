# Explorer's Compass — 1.21.1-3.4.0-neoforge

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81188f5cda4bbb7a1790  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Explorer's Compass
- **Arquivo JAR:** `ExplorersCompass-1.21.1-3.4.0-neoforge.jar`
- **Versão 1.21.1:** `1.21.1-3.4.0-neoforge`
- **Categoria:** Exploração; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/explorers-compass
- **Função:** Locator de estruturas vanilla/modded registradas, com GUI de seleção, target/compass/HUD, next-instance search e custos opcionais de durability/XP configuráveis na linha 3.4.0.
- **Dependências:** NeoForge 1.21.1. Consome o registry/placement de estruturas dos mods/datapacks carregados; não gera estruturas nem substitui seus providers.
- **Compatibilidade/Riscos:** Com muitos structure mods pode reduzir discovery cost e aumentar custo de busca. 3.4.0 corrige hidden tag `c:hidden_from_locator_selection` e casos de estruturas a oeste. Riscos: search radius alto, target stale, dimension mismatch, structure custom incompatível, GUI extensa e quebra de progressão imersiva.
- **Sobreposição:** Nature's Compass localiza biomes; Explorer's Compass localiza structures. Mapas/quests/locators podem compartilhar objetivo de navegação, mas não são equivalentes tecnicamente.
- **Observações:** Preservada a divergência legítima entre release pública curta `3.4.0` e metadata runtime `1.21.1-3.4.0-neoforge`. Changelog 3.4.0 adiciona/backporta next-instance search, durability/repair e XP cost configuráveis, além de fixes de hidden tag e busca a oeste.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `ExplorersCompass-1.21.1-3.4.0-neoforge.jar`, mod id `explorerscompass`, metadata version `1.21.1-3.4.0-neoforge` e SHA-1 9f62af344988a6e2d855b113e61cf0e1611c7e0f.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Explorer's Compass 3.4.0/runtime 1.21.1-3.4.0-neoforge; structure-search authority, config/costs, 3.4.0 fixes, lifecycle, multiplayer, risks and tests cataloged.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `ExplorersCompass-1.21.1-3.4.0-neoforge.jar` · mod id `explorerscompass` · metadata version `1.21.1-3.4.0-neoforge` · release pública `3.4.0` · NeoForge 1.21.1.

## 1. Papel no modpack
Explorer's Compass é um locator de **estruturas registradas**, vanilla e modded. O jogador abre uma GUI, escolhe estrutura/grupo e o item procura uma ocorrência dentro dos limites/config definidos.

## 2. Authority / ownership
- **Worldgen/structure provider:** registro, placement e existência real da estrutura.
- **Explorer's Compass:** consulta/search, target selecionado, compass/HUD presentation e custos configurados.

O mod não gera estruturas, não altera seed e não deve ser usado como prova de que uma estrutura existe fora dos limites de busca.

## 3. GUI e seleção
Right-click abre a interface de seleção. O projeto suporta estruturas registradas de mods, então a lista depende do registry/datapacks efetivamente carregados. Estruturas ocultadas por tags/config não devem aparecer.

## 4. Estado do compass
O compass mantém informação da busca/target e aponta para a estrutura localizada. Shift-right-click reseta o state segundo a documentação pública. Quando não possui target válido, o comportamento publicado inclui apontar para world spawn.

## 5. Release 3.4.0 — next instance
A changelog de 3.4.0 backportou a capacidade de buscar a **próxima instância** de uma estrutura já localizada. Isso permite iterar ocorrências sem tratar a primeira coordenada como único exemplar da structure type.

## 6. Durability e repair configuráveis
3.4.0 adiciona/backporta opção de config para durability do compass e recipe de repair do item quebrado. Se desabilitado/configurado diferentemente, não assumir custo fixo.

## 7. XP cost configurável
3.4.0 também traz opção para consumir **XP levels durante buscas**. Custo de exploração é portanto uma decisão de configuração do pack, não valor universal do mod.

## 8. Search correctness / hidden tag
A release corrige `c:hidden_from_locator_selection` para ocultar estruturas da seleção e também um bug em que certas ocorrências a oeste do ponto inicial podiam ser ignoradas.

Essas correções são relevantes em pack grande com muitos structure mods/datapacks.

## 9. Config / alcance
O projeto expõe configurações como search radius e blacklist/selection controls. Valores concretos do pack devem ser lidos do config real; esta ficha não inventa radius, cooldown ou XP defaults.

## 10. Relação com Nature's Compass
Nature's Compass localiza **biomes**; Explorer's Compass localiza **structures**. São objetivos similares de navegação, mas registries e semantics diferentes.

## 11. Imersão/progressão
Em um pack orientado a exploração/quests, localizar estruturas diretamente pode reduzir discovery cost. Isso é decisão de design, não incompatibilidade técnica. XP/durability/blacklist podem ser usados para modular o poder do locator.

## 12. Client / Server
A GUI/HUD/needle são client-facing, mas search result e world structure data precisam corresponder ao servidor. Em dedicated server, o cliente não deve inventar coordenadas a partir de seed local.

## 13. Lifecycle
Validar craft/obtenção, GUI open/close, search success/failure, next-instance search, reset, durability break/repair, XP insufficient, dimension change, structure datapack reload, server restart e worldgen mod update.

## 14. Multiplayer
Cada player pode ter target próprio. Buscar a mesma structure type não deve compartilhar state global incorretamente. Coordenada localizada precisa corresponder à mesma world/dimension authority do servidor.

## 15. Riscos
1. estrutura modded aparecer mas ser impossível de localizar por placement custom;
2. blacklist/tag hidden não respeitada;
3. search radius muito alto causar custo server-side;
4. XP/durability config desbalancear exploração;
5. target stale após structure datapack change;
6. compass manter coordenada de outra dimension;
7. next-instance repetir a mesma estrutura;
8. GUI enorme com muitos structure mods;
9. locator quebrar progressão/quest discovery;
10. confundir ausência no search com inexistência global.

## 16. Matriz de testes
1. Structure vanilla comum.
2. Structure vanilla rara.
3. Structure modded de pelo menos três providers do pack.
4. Estrutura marcada `c:hidden_from_locator_selection`.
5. Busca com target a oeste do ponto inicial.
6. Next-instance search.
7. XP cost on/off e XP insuficiente.
8. Durability/repair on/off.
9. Dimension change/reset.
10. Dois jogadores com targets diferentes.
11. `/reload` de datapacks e nova busca.

**Esta catalogação não afirma que esses testes foram executados.**

## 17. Evidências
- modlist física canônica: JAR/mod id/metadata version/hash;
- projeto oficial Explorer's Compass: locate registered structures, GUI, reset, HUD e configs;
- changelog 3.4.0: durability/repair, next instance, XP cost, hidden tag fix, west-side search fix e GUI improvements.

> **Boundary canônico:** Explorer's Compass é authority da **busca/localização**, nunca do worldgen. A estrutura continua pertencendo ao mod/datapack que a registra.
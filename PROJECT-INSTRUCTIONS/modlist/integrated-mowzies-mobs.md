# Integrated Mowzie's Mobs

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db811f8be7f9f35273f678
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Integrated Mowzie's Mobs
- **Arquivo JAR:** `IMM v1.3.0-1.21.1.jar`
- **Versão 1.21.1:** 1.3.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Worldgen, Compat, Mobs
- **Função:** Overhaul server-side/data-driven das estruturas de Mowzie's Mobs, com melhor integração ao terreno e ao worldgen modded através do ecossistema Integrated API.
- **Dependências:** Provider-alvo: Mowzie's Mobs 1.8.2. Required upstream: Integrated API 1.8.0, Quark 4.1-483, Supplementaries 3.9.8 e Amendments 2.1.10. Create 6.0.10 é integração opcional na linha atual.
- **Sobreposição:** Não substitui Mowzie's Mobs: reestrutura as estruturas/worldgen do provider. Pode coexistir com outros structure packs; colisão real deve ser avaliada por placement, spacing, biome tags, loot e terrain fit.
- **Compatibilidade/Riscos:** Riscos: terrain/structure_set drift, Integrated API shared bug em Umvuthana Grove, cave-biome interactions, optional Create references, hybrid chunks, loot/encounter balance e remoção de providers após geração. 1.3.0 corrige Wroughtnaut Chamber com cave-biome mods.
- **Observações:** Runtime físico `IMM v1.3.0-1.21.1.jar`, mod id `integrated_mowzies_mobs`. 1.3.0 corrige Wroughtnaut Chamber com cave-biome mods como Terralith; upstream ainda atribui a geração estranha de Umvuthana Grove a Integrated API.
- **Procedência:** modlist.txt física atual + CurseForge oficial Integrated Mowzie's Mobs 1.3.0 file 8833807, Release NeoForge 1.21.1 de 07/09/2026 + documentação oficial de worldgen/dependencies/config + changelogs 1.1.0–1.3.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-mowzies-mobs/files/8833807
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Integrated Mowzie's Mobs 1.3.0 release-pinned; data-driven structure overhaul, terrain/spacing controls, hard/optional dependencies, Wroughtnaut cave-biome fix, Integrated API boundary, lifecycle/worldgen risks e testes catalogados.
- **Histórico da decisão:** 2026-09-07 — novo mod incorporado. 2026-09-07 — projeto e dependências confirmados na página oficial fornecida pelo usuário; decisão MANTER. Não normalizar o JAR local 1.1.0 para a publicação pública 1.0.0 enquanto não houver evidência do arquivo exato.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `IMM v1.3.0-1.21.1.jar`, mod id `integrated_mowzies_mobs`, versão `1.3.0`. O CurseForge oficial confirma a release NeoForge 1.21.1 file 8833807 de 07/09/2026. O projeto é server-side e data-driven, voltado a reestruturar **todas as estruturas de Mowzie's Mobs** com melhor integração ao terreno.

## 1. Papel e authority
Integrated Mowzie's Mobs (IMM) é um overhaul de worldgen/structures para Mowzie's Mobs. Mowzie's continua authority de bosses, mobs, AI, abilities e loot-base; IMM controla seus templates, placement, terrain adaptation e integrações estruturais. Integrated API fornece infraestrutura compartilhada do ecossistema e precisa permanecer separada como provider técnico.

## 2. Dependências e snapshot físico
O alvo funcional Mowzie's Mobs 1.8.2 está presente. A documentação atual lista como required **Integrated API, Quark, Supplementaries e Amendments**; no pack estão Integrated API 1.8.0, Quark 4.1-483, Supplementaries 3.9.8 e Amendments 2.1.10. **Create é opcional** na linha atual e está presente em 6.0.10.

## 3. Data-driven worldgen
O projeto declara uso do formato vanilla de datapack para o worldgen. Portanto estruturas, placement e parâmetros podem ser alterados por data sem reescrever código. Isso torna `/reload`, codecs/JSON, biome targeting e structure sets superfícies operacionais importantes.

## 4. Controle de terrain fit
A documentação fornece como exemplo `mowziesmobs:worldgen/structure/umvuthana_grove.json` e o parâmetro `allowed_terrain_height_range`. Reduzir esse range força terreno mais plano/adequado, mas pode tornar a estrutura mais rara; o próprio projeto indica compensar, quando necessário, via spacing/separation no `structure_set` correspondente. Não alterar ambos sem seed/regression comparison.

## 5. Umvuthana Grove — boundary com Integrated API
Na linha 1.2.0 e novamente no changelog 1.3.0, o upstream registra geração estranha de **Umvuthana Grove** como problema atribuído ao **Integrated API**, com correção sendo trabalhada por Halosesparta. Portanto um Grove deformado na matriz atual não deve ser automaticamente imputado ao template IMM nem “corrigido” localmente sem identificar a camada causal.

## 6. Wroughtnaut Chamber — fix 1.3.0
A release física 1.3.0 corrige explicitamente a geração da **Wroughtnaut Chamber com cave-biome mods**, citando Terralith como exemplo. O pack contém Terralith 2.6.2; essa combinação é um regression gate concreto. O fix deve ser validado em chunks novos, não em estruturas já persistidas.

## 7. Histórico relevante da linha
A 1.1.0 tornou Create opcional e corrigiu chests vazios no Monastery, além de conteúdo ausente após Frostmaw Arena. A 1.2.0 corrigiu compatibilidade opcional de Create no Monastery e documentou o problema de Umvuthana Grove/Integrated API. Esses pontos explicam contracts que continuam relevantes na 1.3.0, sem afirmar que o changelog atual reimplementa todos eles.

## 8. Structures e provider ownership
IMM pode usar blocos de Quark, Supplementaries, Amendments e opcionalmente Create, mas esses mods continuam owners de seus registries. Remover uma dependency depois que uma estrutura com esses blocos foi gerada pode deixar conteúdo ausente no save. Templates não devem duplicar behavior de block entities dos providers.

## 9. Loot e encounters
Bosses/encounters de Mowzie's permanecem sob authority do mod-base. IMM pode alterar o contexto estrutural, containers e placement, mas quests ou sistemas externos não devem conceder boss rewards novamente apenas por detectar a estrutura. Loot/advancement settlement precisa ser deduplicado.

## 10. Client / server
O projeto é marcado como **Server** porque sua função principal é data/worldgen. Isso não implica que clientes possam remover os mods que registram blocos/mobs usados nas estruturas; cada dependency mantém seus próprios requisitos de instalação. O servidor é authority do placement, loot, spawns e world state.

## 11. Lifecycle e persistência
Validar datapack load, criação de mundo, geração em chunks novos, chunk unload/reload, server restart, `/reload`, atualização de IMM/Integrated API e mudança na lista de dependencies opcionais. Estruturas já geradas permanecem no save; updates podem produzir regiões híbridas antigas/novas.

## 12. Multiplayer e performance
Múltiplos jogadores explorando chunks novos podem disparar geração simultânea de estruturas grandes e bosses. Avaliar spikes de chunk generation, pathfinding/spawner activation e disputa de loot. Aumentar frequência para compensar terrain constraints pode multiplicar custo e rewards.

## 13. Relação com outros structure packs
O pack contém IDAS, Integrated Dungeons Arise e vários outros providers de estruturas. O risco relevante é colisão de placement, terrain fit, biome tags, densidade, spawners e loot inflation. Não deduplicar simplesmente porque dois projetos usam o prefixo “Integrated”.

## 14. Riscos técnicos
- bug compartilhado do Integrated API ser confundido com defeito do IMM;
- Wroughtnaut Chamber ainda interagir mal com outro cave-biome provider;
- `allowed_terrain_height_range` muito baixo tornar structure praticamente ausente;
- spacing/separation excessivamente reduzido causar densidade e performance ruins;
- referências opcionais a Create não degradarem corretamente;
- chunks antigos/novos divergirem após update;
- remover dependency depois de structures geradas;
- loot/boss reward duplicado por integração externa;
- `/reload` aceitar data nova sem reconstruir world state persistido.

## 15. Matriz de testes obrigatória
- [ ] Dedicated server boot com IMM 1.3.0 + Mowzie's 1.8.2 + required dependencies.
- [ ] Datapack/worldgen carrega sem missing registry/codec error.
- [ ] Wroughtnaut Chamber gera corretamente com Terralith em chunks novos.
- [ ] Umvuthana Grove é avaliado separando template IMM de bug conhecido no Integrated API.
- [ ] Monastery gera e mantém loot esperado com Create presente.
- [ ] Remoção experimental de Create confirma degradação opcional sem hard-fail.
- [ ] `allowed_terrain_height_range` e structure_set spacing/separation são testados por seed antes de qualquer ajuste.
- [ ] Frostmaw Arena/estruturas principais mantêm rota de progressão utilizável.
- [ ] `/reload` conclui sem data errors.
- [ ] Restart mantém structures/boss state sem duplicação.
- [ ] Exploração multiplayer de chunks novos não gera regressão severa de tick.

## 16. Evidências e limites
- **Modlist física:** `IMM v1.3.0-1.21.1.jar`, Mowzie's 1.8.2 e dependencies atuais.
- **CurseForge oficial:** file 8833807, Release 1.3.0, environment Server, descrição data-driven e dependency model.
- **Changelog 1.3.0:** Wroughtnaut Chamber + cave-biome fix e boundary do Umvuthana Grove com Integrated API.
- **Limite:** source code exato da build não foi promovido a authority nesta execução; IDs internos além dos documentados permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

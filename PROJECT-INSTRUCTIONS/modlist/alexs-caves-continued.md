# Alex's Caves Continued

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db8147b66dc784fc4bb955
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Alex's Caves Continued
- **Arquivo JAR:** `alexscaves-1.0.9-neoforge+1.21.1.jar`
- **Versão 1.21.1:** 1.0.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Worldgen, Mobs, Exploração
- **Função:** Continuação/port do Alex's Caves para NeoForge moderno, preservando os seis cave biomes, mobs, blocos, itens, receitas, progressão de descoberta e Cave Compendium do conteúdo original.
- **Dependências:** CodxLib compatível com a versão/loader; pack atual possui CodxLib 1.6.0. O Continued não exige Citadel externo para funcionar; Citadel 2.7.1 top-level permanece no pack por outros consumidores.
- **Sobreposição:** É a implementação canônica `alexscaves` do pack. O antigo Alex's Caves 2.0.2 foi removido; não há segunda implementação concorrente no snapshot atual.
- **Compatibilidade/Riscos:** Duplicidade `alexscaves` antiga está resolvida: só o Continued 1.0.9 permanece. Riscos atuais são worldgen/spawn/chunk borders e integrações version-specific. AeronauticsCompat cita outro port de Alex's Caves; não assumir cobertura Sable sobre o Continued sem validar mixin targets.
- **Observações:** Decisão Manter preservada. Presença/JAR/runtime têm autoridade na modlist física atual de 595 top-levels. Não inferir compatibilidade de patches feitos para ports diferentes apenas pelo mod ID.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Alex's Caves Continued 1.0.9 + dossiê técnico existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/alexs-caves-continued
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — six cave biomes, CodxLib 1.6.0, Continued authority, worldgen/chunk-border risks e Sable fail-closed rule confirmados no QC global #19.
- **Histórico da decisão:** 2026-09-06 — escolhida como implementação canônica após conferir a modlist atual e a release 1.0.9 para NeoForge 1.21.1. O projeto Continued é mantido ativamente e declara preservar o conteúdo original sem redesign.
- **Data da última decisão:** 2026-09-06

## Escopo e papel
**Alex's Caves Continued** é a continuação/port do Alex's Caves para versões modernas, preservando deliberadamente o conteúdo do projeto original em vez de redesenhá-lo. O conjunto inclui os seis cave biomes — Magnetic Caves, Primordial Caves, Toxic Caves, Abyssal Chasm, Forlorn Hollows e Candy Cavity — além de mobs, blocos, itens, receitas, progressão e Cave Compendium.

## Runtime e autoridade
- JAR físico: `alexscaves-1.0.9-neoforge+1.21.1.jar`.
- Mod ID: `alexscaves`.
- Runtime: `1.0.9`.
- É a única implementação `alexscaves` presente no snapshot físico atual; o antigo `alexscaves-2.0.2.jar` foi removido.
- Presença/JAR/runtime vêm da modlist física de 595 top-level; builds upstream posteriores para outras versões não substituem essa autoridade.

## Dependências
O projeto Continued usa **CodxLib** correspondente ao loader/versão. O pack atual possui `codxlib-1.6.0-neoforge+1.21.1.jar`. A documentação do Continued informa que Citadel é empacotado/dispensado como dependência externa para o port; o `citadel-2.7.1-1.21.1.jar` top-level do pack pode permanecer por outros consumidores e não deve ser interpretado como requisito externo necessário deste mod.

## Integrações no pack
O mod participa diretamente de worldgen subterrâneo, exploração, fauna, loot e progressão de descoberta. Estruturas/mods que consomem conteúdo Alex's podem reconhecer o mesmo mod ID `alexscaves`, mas compatibilidade efetiva deve ser verificada por versão e assets/registry.

## Compatibilidade, sobreposição e riscos
O bloqueio antigo por mod ID duplicado está resolvido fisicamente. Permanecem riscos normais de worldgen em packs densos: distribuição de cave biomes, bordas entre chunks antigos/novos, interações de spawn e estruturas subterrâneas. O `AeronauticsCompat` upstream cita um port de Alex's Caves de Raguto; o pack usa o Continued de CodxIO, portanto **não assumir** que aqueles mixins de compatibilidade Sable cobrem esta implementação sem validar targets/classes reais.

## Limites
O projeto Continued declara preservar o conteúdo original; ele não é um redesign de mecânicas nem um segundo sistema de cavernas separado. Não substitui geradores gerais de terreno ou mods de estruturas.

## Testes recomendados
1. Gerar mundo novo e validar ocorrência dos seis cave biomes.
2. Conferir Cave Compendium, tablets/descoberta e progressão de acesso.
3. Validar spawns, pathfinding, loot, recipes e block entities principais.
4. Abrir mundo existente e gerar chunks novos para inspecionar bordas/worldgen.
5. Dedicated-server smoke com exploração prolongada.
6. Se mobs/blocos forem usados em physics ships, testar explicitamente Sable/Aeronautics em vez de presumir cobertura do AeronauticsCompat.

## Evidências
- [CurseForge oficial — Alex's Caves Continued](https://www.curseforge.com/minecraft/mc-mods/alexs-caves-continued)
- Modlist física atual e guia consolidado de Gameplay/Sistemas.

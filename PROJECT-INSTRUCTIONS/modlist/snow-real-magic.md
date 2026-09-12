# Snow! Real Magic!

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db811eb31dd5b8f4807aad
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `SnowRealMagic-1.21.1-NeoForge-12.2.2.jar`, mod id `snowrealmagic`, runtime `12.2.2+neoforge`, mixin `snowrealmagic.mixins.json`; Kiwi 15.8.7, Cloth Config 15.0.140, Ecliptic Seasons 0.15.0-rc-3 e MultiMod Patch 0.32.1 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Snow! Real Magic! 12.2.2 e o stack Kiwi/Cloth Config/Ecliptic Seasons citado estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Snow! Real Magic!
- **Arquivo JAR:** `SnowRealMagic-1.21.1-NeoForge-12.2.2.jar`
- **Versão 1.21.1:** 12.2.2+neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Worldgen, Clima
- **Função:** Expande snow layers vanilla com blocos cobertos, snow gravity, acumulação/melting, colisão e interação configuráveis, blizzard gamerules, datapack tags/model variants e integração sazonal.
- **Dependências:** Required physical: Kiwi 15.8.7+neoforge. Cloth Config 15.0.140 também está presente como optional/config support. Ecliptic Seasons 0.15.0-rc-3 + MultiMod Patch estão presentes; `integration.accumulationWinterOnly` existe, mas config local não foi lida.
- **Sobreposição:** Interseção com Ecliptic Seasons é intencional: Seasons decide ciclo/clima; Snow Real Magic modifica representation/accumulation/melting. Config `accumulationWinterOnly` pode alinhar os sistemas. Não são substitutos integrais.
- **Compatibilidade/Riscos:** Stateful snow/world decoration system. Riscos: contained-block state loss, unlimited-layer regression, fence/wall connection, wrong plant snow variant, random-tick overlay loss, falling-snow water conversion, season double-control e performance de accumulation/melting. Clean removal exige restoreOriginalBlocks workflow.
- **Observações:** Runtime metadata local `12.2.2+neoforge`; arquivo `SnowRealMagic-1.21.1-NeoForge-12.2.2.jar`. Release NeoForge 1.21.1 de 26/06/2026. Delta 12.2.2 corrige unlimited max layers, snowy fence/wall connection, wrong plant snow variants, grass random-tick sem overlay e issue #461 novamente.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth/CurseForge oficiais Snow! Real Magic! 12.2.2 + documentação oficial de features/config/tags.
- **Fonte:** https://modrinth.com/mod/snow-real-magic/version/12.2.2%2Bneoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Snow! Real Magic! 12.2.2 reconstruído: snow-covered contained blocks, gravity/ice, accumulation/melting, collisions/snowballs, blizzard gamerules, tags/models, season integration, uninstall restore path, exact 12.2.2 fixes, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `SnowRealMagic-1.21.1-NeoForge-12.2.2.jar`, mod id `snowrealmagic`, runtime metadata `12.2.2+neoforge`, NeoForge 1.21.1. Snow! Real Magic! altera **state e representação de snow layers**; não é apenas textura. Cobertura, acumulação, queda, melting e contained blocks têm efeito server/world-state.

## 1. Identidade e papel
- **Mod:** Snow! Real Magic!.
- **JAR:** `SnowRealMagic-1.21.1-NeoForge-12.2.2.jar`.
- **Mod id:** `snowrealmagic`.
- **Runtime:** `12.2.2+neoforge`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Mixin:** `snowrealmagic.mixins.json`.
- **Required:** Kiwi; pack `15.8.7+neoforge`.

## 2. Snow-covered contained blocks
O mod permite combinar neve com diversos blocos que normalmente não podem ocupar o mesmo espaço, incluindo slabs, stairs, walls, fences, gates e vegetação. Em vez de simplesmente desenhar neve por cima, a linha atual mantém **contained/original block state** em variantes snow-covered.

Isso torna save/load, neighbor updates e remoção do mod boundaries críticas: perder o contained state pode transformar permanentemente o bloco original.

## 3. World generation de snow-covered blocks
A opção `snow-cover.replaceWorldgenFeature` pode substituir a feature vanilla de geração de neve para criar também variantes cobertas em worldgen.

Esse mecanismo atua na formação do mundo, não apenas em render. Mudanças de config afetam chunks novos e, dependendo do fluxo, chunks já carregados por accumulation.

## 4. Snow gravity
Com `snowGravity`, snow layers podem cair como falling blocks em condições suportadas. A linha foi refatorada para usar comportamento de falling block vanilla em versões anteriores.

Queda deve transferir layer/state exatamente uma vez e não duplicar neve entre posição de origem e destino.

## 5. Falling snow + water → ice
A opção `snowMakingIce` permite que neve em queda converta água em gelo. Isso cruza entity/block update e fluid state.

Regression gates: uma falling snow entity não deve criar volumes gigantes de água/gelo nem converter múltiplos blocks por callback duplicado; a linha 12.2.0 corrigiu um caso de “gigantic water by falling snow”.

## 6. Accumulation
O mod pode acumular camadas durante condições meteorológicas configuradas. Defaults publicados incluem:
- `accumulatesDuringSnowfall = false`;
- `accumulatesDuringSnowstorm = true`;
- `maxLayers = 6`;
- valor `9` para max layers significa **unlimited** segundo a documentação.

A config local não foi lida; esses valores são defaults upstream, não valores locais confirmados.

## 7. Unlimited layers — regression 12.2.2
O changelog exato 12.2.2 corrige **unlimited max snow layers not working properly**.

Como `maxLayers=9` possui semântica especial de unlimited, testar limite 8/9 e transição entre configs para evitar clamp, overflow visual ou state inválido.

## 8. Natural melting
`naturalMelting` permite que snow layers acima de determinado estado derretam com o tempo. Há também opções para impedir melting ou permitir melting em warm biomes.

Melting deve remover uma quantidade previsível de layer/state sem destruir o contained block abaixo.

## 9. Winter-only integration
A config `integration.accumulationWinterOnly` permite restringir accumulation ao inverno quando há mod de seasons compatível.

O pack contém **Ecliptic Seasons 0.15.0-rc-3** e seu MultiMod Patch. Portanto esta é uma integration surface concreta, mas a config local não foi aberta: não afirmar que winter-only está ativo.

## 10. Ecliptic Seasons boundary
Ecliptic Seasons continua authority do ciclo sazonal/clima sazonal. Snow! Real Magic! continua authority de seus snow-covered blocks, accumulation/melting e interação de snow layers.

Se os dois alterarem formação/melting de neve, o comportamento deve ser reconciliado por integração/config em vez de assumir que um substitui o outro.

## 11. Collision e bounding box
O mod pode usar bounding box mais fina para snow layers, aproximando collision do volume visual. Isso afeta movement/fall interactions no servidor.

A opção não deve mudar arbitrariamente collision do contained block nem permitir clipping através de fence/wall/stair coberta.

## 12. Fall-damage reduction
A linha suporta redução de fall damage conforme quantidade de snow layers. Cálculo precisa usar o state real do bloco de aterrissagem e ser server-authoritative.

Não confundir redução do snow layer com efeitos de outros mods de movement/parkour presentes no pack.

## 13. Snowball scraping
A documentação permite usar shovel ou sneak + empty hand, conforme config, para remover uma camada e obter snowball. A interação deve consumir exatamente uma layer por ação aceita.

Spam/multiplayer não pode entregar duas snowballs para o mesmo state transition.

## 14. Right-click visual toggle
Existe opção para empty-hand right-click alternar o overlay/fancy snow de certos blocos. Esse mecanismo pode alterar representação/state específico do bloco coberto.

Client prediction precisa reconciliar com servidor para evitar bloco visualmente toggled apenas para um jogador quando o state é world-authoritative.

## 15. Snowy fence/wall connections — 12.2.2
A release 12.2.2 corrige **snowy fence/wall connection**. Como esses blocks dependem de neighbor shape/update, testar conexão depois de:
- placement;
- snow cover/remove;
- neighbor break/place;
- chunk reload.

A conexão visual e collision precisam convergir com o state real.

## 16. Plant snow variants — 12.2.2
A build corrige alguns plants sendo convertidos para **wrong snow variant block**.

Regression gate: cada planta/tag coberta deve restaurar exatamente o provider/original state correto quando snow é removida/meltida, sem converter espécie/state.

## 17. Random ticking / grass overlay — 12.2.2
O changelog corrige random ticking criando **grass blocks sem snow overlay**.

Esse bug cruza random tick, contained state e visual state. Testar spread/grass changes sob snow para garantir que overlay acompanha o novo underlying state.

## 18. Plants e contained-state tags
A documentação expõe datapack tags para controlar quais blocks podem ser contidos/cobertos, plants, entity-inside callbacks, animate tick, Y offset, expanded model e blocks onde snow não deve acumular.

Isso permite compat data-driven com mods. Tag excessivamente ampla pode criar variante snow-covered de block que não suporta o lifecycle.

## 19. Model variants
O sistema suporta custom snowy model metadata e client `snowVariants`. Alguns foliage/contained blocks podem renderizar de forma distinta quando cobertos.

Render variant não deve alterar block ID/state funcional sem o correspondente server state.

## 20. Blizzard gamerules
A documentação publica gamerules:
- `snowrealmagic:blizzardStrength` — 1–8 max layers geradas;
- `snowrealmagic:blizzardFrequency` — frequência por 10000 por tick por chunk para falling snow.

Valores altos aumentam quantidade de updates/entities e precisam ser medidos em servidor grande.

## 21. Mob spawning em snow layers
Config inclui `mobSpawningMaxLayers`. Isso pode limitar spawning conforme profundidade de neve.

Alterar esse valor é gameplay, não cosmético; o servidor deve usar state real da snow layer, inclusive em contained blocks.

## 22. Lighting
A linha 12.x inclui fixes de block light para stairs/slabs e lighting geral. Snow-covered variants precisam preservar propagação/occlusion adequada do contained block.

Invisible/dark blocks devem ser triados entre model/culling/light state, não apenas resource pack.

## 23. Neighbor updates e drops
A 12.2.0 corrigiu resource drop quando contained state é destruído por neighbor update. Essa é uma regression lineage importante: atualização estrutural não pode apagar recurso do bloco original nem dropar duas vezes.

## 24. Clean uninstall / restoreOriginalBlocks
A config `snow-cover.restoreOriginalBlocks` existe especificamente para remoção do mod. Quando habilitada, snow-covered blocks podem ser restaurados para seus original blocks conforme chunks carregam.

**Não remover o JAR diretamente de mundo principal sem seguir esse workflow e backup.** Como o mod armazena variantes/state próprios, clean uninstall precisa liquidar esses states primeiro.

## 25. Client / server authority
Servidor decide:
- block state/contained state;
- accumulation/melting;
- falling snow;
- water→ice;
- collision/spawning;
- layer removal/drop.

Cliente renderiza models/particles/snow variants. Desync visual não deve mudar o state canônico do mundo.

## 26. Performance
Áreas de custo:
- random/scheduled ticks para accumulation/melting;
- falling snow entities;
- neighbor updates de fences/walls/plants;
- worldgen replacement;
- model variants/particles client-side.

O próprio projeto classifica algumas opções de accumulation/melting como impacto médio. Medir em biomas nevados durante snowstorm com profiler.

## 27. Integrações físicas do pack
- **Kiwi 15.8.7:** required library.
- **Cloth Config 15.0.140:** config support opcional presente.
- **Ecliptic Seasons 0.15.0-rc-3:** seasons integration surface.
- **Ecliptic Seasons MultiMod Patch 0.32.1:** stack de compat sazonal presente.
- Outros mods de clima/temperatura não devem receber ownership da snow layer apenas por afetarem weather/temperature.

## 28. Riscos técnicos
1. **Contained-state loss:** bloco original perdido ao cobrir/remover neve.
2. **Unlimited-layers regression:** max=9 não respeita semântica especial.
3. **Fence/wall topology:** connections/collision inconsistentes.
4. **Wrong plant restore:** planta vira variante/ID errado.
5. **Random-tick overlay loss:** grass/state muda sem snow representation.
6. **Falling-snow duplication:** origem/destino ambos preservam layer.
7. **Water/ice amplification:** update cria volume indevido.
8. **Season double-control:** Ecliptic + SRM acumulam/derretem por regras concorrentes.
9. **Performance:** snowstorm gera excesso de ticks/entities.
10. **Unsafe uninstall:** remover mod deixa blocks desconhecidos/perde original state.

## 29. Matriz de testes
- [ ] Dedicated server inicia com Snow Real Magic 12.2.2 + Kiwi 15.8.7.
- [ ] Snow cobre slab/stair/fence/wall/gate/plants mantendo original state.
- [ ] Remover/melt snow restaura exatamente o bloco/state original.
- [ ] Falling snow transfere uma layer sem dupe.
- [ ] Falling snow em água cria gelo conforme config sem water amplification.
- [ ] `maxLayers=9` funciona como unlimited — regression 12.2.2.
- [ ] Fence/wall snowy connections atualizam após neighbor changes — regression 12.2.2.
- [ ] Plants não convertem para snow variant incorreta — regression 12.2.2.
- [ ] Grass/random tick mantém snow overlay — regression 12.2.2.
- [ ] Natural melting não destrói contained block.
- [ ] Winter-only com Ecliptic Seasons funciona somente se config habilitada.
- [ ] Sneak/empty-hand ou shovel scraping remove uma layer e entrega um snowball exatamente uma vez.
- [ ] Blizzard gamerules alteram geração conforme valores configurados.
- [ ] Chunk unload/reload/save restart preservam contained state/layers.
- [ ] Workflow `restoreOriginalBlocks` é validado em cópia de mundo antes de eventual remoção.
- [ ] Stress de snowstorm é medido com profiler/TPS.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 30. Evidências e limites
- Modlist física atual: Snow Real Magic 12.2.2+neoforge, Kiwi, Cloth Config e Ecliptic Seasons stack.
- Modrinth NeoForge 12.2.2: exact version, environment e fix de unlimited max layers.
- Changelog 12.2.2 compartilhado pela linha 1.21.1: fence/wall, plant variant, random-tick overlay e issue #461 fixes.
- Documentação oficial: snow gravity, covered blocks, accumulation/melting, configs, gamerules, tags, model variants e restore workflow.
- **Limite:** config local não foi lida; defaults upstream e integration toggles não são tratados como configuração efetiva do pack.

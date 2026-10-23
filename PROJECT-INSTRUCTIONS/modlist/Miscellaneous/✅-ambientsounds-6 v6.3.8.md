# AmbientSounds 6

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#25**: `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar`, mod id `ambientsounds`, runtime `6.3.8`, SHA-1 `c79ab328b2c46068effebdcb3f26e98dc4404cb0`.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 22/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09


## Reconciliação física atual — 22/09/2026
O snapshot canônico do Notion, fechado em 11/09/2026, registra CreativeCore `2.13.44`. A autoridade física atual (`modlist(1).txt`, 22/09/2026) contém `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar` / `2.13.46`. As referências a `2.13.44` abaixo são preservadas como estado histórico da origem; para o runtime atual, a dependência efetiva é `2.13.46`.

## Propriedades do banco

- **Mod:** AmbientSounds 6
- **Arquivo JAR:** `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar`
- **Versão 1.21.1:** 6.3.8
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Client-side environmental soundscape engine. Detecta contexto do mundo e mistura ambience loops/sound pools por bioma, região e condições, com transições suaves, debug/configuração e suporte a packs de áudio. A build 6.3.8 adiciona a categoria de som `suspense`. Não altera spawn, clima, worldgen ou regras de gameplay.
- **Dependências:** CreativeCore é obrigatória; o pack instala `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar` (2.13.46).
- **Sobreposição:** Sobreposição parcial com outros mods de ambience/soundscape. Não substitui Presence Footsteps, Sound Physics ou sistema de música; atua em outra camada de áudio.
- **Compatibilidade/Riscos:** Risco principal é composição de áudio: outros ambience/sound mods podem tocar camadas simultâneas e elevar volume/ruído/custo. Presence Footsteps não é duplicata: trata passos/material sob o jogador, enquanto AmbientSounds trata paisagem sonora contextual. Validar cavernas, água, biomas, dimensões, weather/daytime e categoria suspense. Como é client-side, não deve ser usado como authority de eventos de servidor.
- **Observações:** Qualquer resource/sound pack que acrescente ambience precisa ser auditado junto porque pode mudar completamente a experiência sem alterar o JAR. O mod reage ao mundo; não é fonte de verdade para biome/weather/spawn.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial AmbientSounds 6.3.8 + CreativeCore 2.13.44 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar` / `6.3.8`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ambientsounds
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 03/10/2026 — runtime físico documentado permanece 6.3.8; CurseForge publicou 6.3.9 para NeoForge 1.21.1 em 02/10/2026. O delta de scanner/matching/performance está registrado abaixo.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar`, NeoForge 1.21.1, ambiente **client-only**. O mod cria paisagem sonora reativa; ele não deve ser usado como prova de que uma condição de gameplay aconteceu no servidor.

## 1. Função real
AmbientSounds 6 substitui o silêncio/repetição do áudio ambiental vanilla por um motor que escolhe e mistura **loops e sound pools contextuais**. O resultado varia com o local onde o cliente está: bioma, ambiente, caverna/superfície, água e outras condições reconhecidas pelo engine influenciam quais sons entram e saem.

O ponto importante para outros chats é: AmbientSounds **observa** o estado do mundo para decidir áudio. Ele não cria clima, não muda biome registry, não spawna fauna e não injeta worldgen para justificar seus sons.

## 2. Engine de ambience
O sistema trabalha com múltiplas camadas e transições para evitar cortes secos ao atravessar regiões. Em vez de um único arquivo reproduzido sem contexto, sets de sons podem ser ativados/desativados e misturados conforme condições.

Isso permite, por exemplo, diferenciação sonora entre florestas, cavernas, regiões aquáticas e ambientes dimensionais sem que cada biome mod precise registrar manualmente uma entidade sonora.

## 3. Categoria `suspense`
A mudança específica da build **6.3.8** instalada foi a adição da sound category **`suspense`**. Para configuração de volume, accessibility e mixagem, essa categoria deve ser tratada como parte da identidade da versão atual, não como recurso de versões posteriores.

## 4. Configuração e debug
O ecossistema CreativeMD fornece configuração client-side e ferramentas de diagnóstico para entender quais ambiences estão ativos. Em troubleshooting, o caminho correto é inspecionar/debugar o soundscape em vez de presumir que um som “vem do biome”.

Quando um pack customizado de AmbientSounds estiver ativo, a origem pode ser o pack de som e não o JAR. Portanto a auditoria final de áudio deve cruzar:
- JAR AmbientSounds;
- config client-side;
- resource/sound packs;
- outros mods que emitam ambience.

## 5. Dependência
**CreativeCore** é a dependência técnica obrigatória. O runtime físico atual contém `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar`, versão 2.13.46.

CreativeCore fornece GUI/config/network/render/utilities compartilhadas aos mods do autor. Não substituir CreativeCore por Architectury/Balm apenas porque todas são “libraries”; consumidores foram compilados contra APIs diferentes.

## 6. Superfícies que o mod toca
- sound engine do cliente;
- volume/categorias de áudio;
- detecção contextual de ambiente;
- resource assets de áudio;
- configuração client-side;
- transição/mixagem de loops.

Não toca como authority:
- spawn rules;
- mob AI;
- weather state server-side;
- loot;
- recipes;
- world generation.

## 7. Sobreposição no pack
### Presence Footsteps
Não é duplicata. Presence Footsteps produz sons relacionados a **passos/material/locomoção**; AmbientSounds produz **soundscape ambiental**. Eles podem coexistir e, juntos, aumentar densidade sonora.

### Outros ambience/music mods
Se outro mod também cria vento, cavern ambience, fauna ambiente ou loops por biome, pode haver dupla reprodução. Isso é sobreposição funcional, não incompatibilidade formal.

### Sound Physics/reverb
Um processador acústico pode alterar/reverberar os sons gerados por AmbientSounds. O risco é mix excessivo ou custo client-side, não conflito de registro de gameplay.

## 8. Riscos específicos
1. **Volume cumulativo:** múltiplos providers de ambience podem mascarar música, footsteps ou cues de combate.
2. **Performance/client:** muitos loops/condições/resource assets podem aumentar custo de áudio e memória.
3. **Falsa causalidade:** ouvir chuva/caverna/fauna não prova que um evento de servidor ocorreu.
4. **Resource pack override:** assets/configs podem mudar sem o JAR mudar.
5. **Dimension mods:** ambientes custom precisam ser testados; não presumir cobertura só porque o mod reconhece vanilla biomes.
6. **Accessibility:** a categoria suspense pode exigir ajuste separado para usuários sensíveis a tensão/ruídos súbitos.

## 9. Matriz de validação
1. Startup com CreativeCore 2.13.46.
2. Overworld: plains/forest/taiga/desert/swamp/mountain.
3. Cave rasa vs cave profunda.
4. Submerso e margem d'água.
5. Nether em múltiplos biomas.
6. End e dimensões modded relevantes.
7. Dia/noite.
8. Clear/rain/thunder, verificando que o áudio apenas reage.
9. Transição entre biomas sem corte ou loop duplicado persistente.
10. Categoria `suspense` no mixer/config.
11. Coexistência com Presence Footsteps.
12. Coexistência com outros ambience/music providers.
13. Resource packs ativos: verificar prioridade e assets substituídos.
14. Medir CPU/audio stutter numa área com muitos sons de mobs/máquinas.
15. Dedicated server: confirmar que ausência do mod no servidor não é tratada como ausência de gameplay, respeitando seu caráter client-only.

## 10. Regras para outros chats
- Não criar perk, quest ou trigger baseado em “som do AmbientSounds”.
- Não classificar o mod como weather/worldgen.
- Para conflitos de áudio, identificar **quem toca o som**, não remover mods por categoria genérica.
- Se um som estiver errado em um biome modded, inspecionar config/resource pack e detecção de ambiente antes de culpar worldgen.

## 11. Fontes e confiança
**Authority física:** modlist 07/09/2026.

**Upstream:** [CurseForge — AmbientSounds 6](https://www.curseforge.com/minecraft/mc-mods/ambientsounds), arquivo NeoForge `6.3.8` publicado para Minecraft 1.21.1; changelog da build: adição da categoria `suspense`.

**Projeto:** guia completo gameplay/sistemas, que o classifica corretamente como camada client-side de ambience e registra CreativeCore como dependência.

**Confiança:** alta para versão, ambiente client-only, dependência e escopo. Packs de som/configuração efetivos precisam ser auditados separadamente para determinar o soundscape final da instância.


## 12. Atualização upstream 6.3.9 — não instalada
A autoridade física permanece **AmbientSounds 6.3.8**. Não há release 1.21.1 intermediária posterior a 6.3.8 antes da **6.3.9**, publicada em 02/10/2026 como `AmbientSounds_NEOFORGE_v6.3.9_mc1.21.1.jar` (CurseForge file ID **9037168**).

O changelog oficial traz três mudanças relevantes para correção e custo do engine de detecção ambiental:
- fecha o **air pocket scanner** assim que um novo level é carregado, evitando scanner antigo sobrevivendo à troca de mundo/level;
- faz **cache dos matches de regex de bioma** e limita o terrain-height scan, reduzindo trabalho repetitivo;
- compila os **patterns de nomes de dimensões uma única vez**, em vez de recompilá-los a cada tick.

### Impacto no pack
São mudanças de lifecycle/performance client-side, não novas fontes de gameplay. A primeira é especialmente relevante para login/relogin e troca de dimensão; as outras duas reduzem custo recorrente da classificação de ambiente em um pack com muitos biomas/dimensões.

### Gate de promoção 6.3.8 → 6.3.9
1. startup com CreativeCore atual;
2. entrar/sair de mundo e trocar dimensões repetidamente, verificando ausência de ambience/scanner stale;
3. transições rápidas entre biomas e cavernas;
4. profiler/client frame-time em regiões com muitos biomas e sound layers;
5. coexistência com Presence Footsteps, Sound Physics e resource/sound packs;
6. confirmar preservação da categoria `suspense` introduzida na 6.3.8.

Fonte upstream: CurseForge file ID 9037168, `AmbientSounds_NEOFORGE_v6.3.9_mc1.21.1.jar`.

## 13. Atualização AmbientSounds 6.3.10 — 10/10/2026

**Autoridade física conhecida:** `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar` / 6.3.8. A seção 12 já registra a release **6.3.9 (02/10)** e seu delta completo: encerramento do air-pocket scanner em troca de level, cache de regex de biomas, terrain scan limitado e padrões de dimensão pré-compilados.

**Nova release:** **`AmbientSounds_NEOFORGE_v6.3.10_mc1.21.1.jar`**, publicada em **08/10/2026** para Minecraft 1.21.1 NeoForge. É a mais recente identificada nessa linha; artefatos de outros Minecrafts não podem ser usados como substitutos. **O changelog individual da build 6.3.10 ainda não pôde ser recuperado com granularidade técnica de uma fonte primária**. Não lhe atribuir mudança de sound engine, novos sons, performance ou bugfix específico sem a release note exata.

**Cadeia integral verificada:** 6.3.8 → 6.3.9 → 6.3.10. As correções da 6.3.9 continuam sendo o principal ganho técnico **confirmado** desde o runtime físico.

**Risco e QA:** compare CreativeCore metadata required pelo JAR, startup client, dimension swaps, caves, modded biomes, rain/ambience and sound category, large biome registry regex cost, profiler, instrumentação de soundscape com shaders/Particle Rain/Subtle Effects, mudança de packs, pausa/relogin e descarte de scanners. Não supor que 6.3.10 altera a lógica ambiental do servidor, pois o mod continua layer client-only.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/ambientsounds/files/all?version=1.21.1

**Estado:** release 6.3.10 upstream documentada, 6.3.8 físico preservado, changelog granular 6.3.10 pendente e nenhum teste executado.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum arquivo físico do pack, filename documental certificado, posição de inventário, configuração ou save foi alterado.

- **Projeto solicitado:** AmbientSounds 6
- **Último candidato compatível localizado:** **6.3.11 (NeoForge 1.21.1, 19/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Client audio: validar soundscape por biome/dimensão, resource reload, performance e coexistência com sound resource packs.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/ambientsounds

## Auditoria completa do intervalo NeoForge 1.21.1 — 23/10/2026 (6.3.8 → 6.3.11)

**Autoridade física preservada:** posição #025 na última modlist física, JAR `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar`, mod ID `ambientsounds`, runtime `6.3.8`. **Projeto oficial CurseForge 254284**, autor **CreativeMD**; única categoria temática **Miscellaneous**, coerente com a pasta atual. Ambiente **Client**; a CreativeCore instalada é `CreativeCore_NEOFORGE_v2.13.46_mc1.21.1.jar` no último inventário, obrigatória à linha usada. O mod é soundscape ambiental, não provider de weather, worldgen ou gameplay.

### Releases posteriores confirmadas, individualmente

| Versão | Publicação na plataforma correta | Changelog oficial e impacto |
|---|---|---|
| **6.3.9** | 02/10/2026 — `AmbientSounds_NEOFORGE_v6.3.9_mc1.21.1.jar`, CurseForge File ID **9037168** | Fecha o scanner de **air pockets** quando um novo level carrega, prevenindo scanner obsoleto entre mundos/dimensões; **faz cache de matches de regex de biomas** e limita o scan de altura de terreno; compila **patterns de nomes de dimensões** uma vez em vez de a cada tick. Fixes de lifecycle e custo client-side já descritos na seção 12. |
| **6.3.10** | 08/10/2026 — `AmbientSounds_NEOFORGE_v6.3.10_mc1.21.1.jar`, CurseForge File ID **9099001** | **Adiciona variantes de grama para biomas de Biomes We've Gone e Regions Unexplored**; **acrescenta suporte a outros tipos de água**; **remove sons de floresta do vampire forest**. Correção expressa à seção 13, em que o changelog desta build constava como indisponível. Não atribuir refmap fix de builds 26.x ao alvo sem prova. |
| **6.3.11** | 19/10/2026 — `AmbientSounds_NEOFORGE_v6.3.11_mc1.21.1.jar`, Release NeoForge Minecraft 1.21.1 | **Melhora distinção de grama e blocos de grama para biomas personalizados com materiais de solo diferentes** e **corrige ausência de grilos perto de rios**. As mesmas duas mudanças constam na release **6.3.11 NeoForge 1.21.1 publicada pelo autor no Modrinth oficial**; o CurseForge confirma o JAR homônimo e a mesma combinação de loader/jogo/data. |

**Fontes primárias dos artefatos:** https://www.curseforge.com/minecraft/mc-mods/ambientsounds/files/all?version=1.21.1 ; https://www.curseforge.com/minecraft/mc-mods/ambientsounds/files/9037168 ; https://www.curseforge.com/minecraft/mc-mods/ambientsounds/files/9099001 ; https://modrinth.com/mod/ambientsounds/version/6.3.11 . Para 6.3.11, a associação ao loader/jogo também foi confirmada no próprio catálogo CurseForge de 19/10. Não confundir com **`...mc26.3.jar`** ou builds Fabric de mesmo número.

### Mudanças materiais no contexto do modpack
- **Desempenho/lifecycle (6.3.9):** evitar scanning de level desativado e custo repetitivo de regex/altura quando há muitas dimensões/biomas; não inventar ganho quantitativo de FPS.
- **Fidelidade de ambiente (6.3.10):** categorização de solo em BYG/Regions Unexplored, águas não padrão e soundscape do bioma vampire forest; são decisões auditivas do cliente e não modificam os biomas.
- **Correção contextual (6.3.11):** distinguir materiais de grama e evitar que o som de grilos seja silenciado indevidamente próximo de rios.
- **Integrações locais:** CreativeCore é dependência obrigatória; Presence Footsteps, Sound Physics, recursos de áudio e outras fontes de ambience podem acumular camadas, sem que isso signifique duplicação de um provider de gameplay.

### Testes para eventual atualização física — não executados
- [ ] Checar metadata/manifest e versão CreativeCore exigida pelo JAR 6.3.11 com a versão física 2.13.46 antes de qualquer substituição.
- [ ] Entrar/sair de mundos e alternar dimensões/cavernas repetidamente; verificar que o air-pocket scanner e os loops antigos são interrompidos.
- [ ] Comparar som sobre grama/blocos de grama em Biomes We've Gone, Regions Unexplored e biomas com materiais modificados, com resource packs ativos.
- [ ] Testar rios e margens com grilos, vampire forest, áreas com água de mods, Nether/End, weather/dia-noite e mudanças de altitude.
- [ ] Avaliar sobreposição com Presence Footsteps, Sound Physics e sound/resource packs; testar reload, muting, volumes e transições sem loops presos.
- [ ] Medir profiler real do cliente em área com muitos biomas e dimensões; soundscape não deve ser tratado como estado do servidor.

**Conclusão documental:** sequência **6.3.8 → 6.3.9 → 6.3.10 → 6.3.11** confirmada com **changelog individual para cada nova Release** no alvo NeoForge 1.21.1, corrigindo a antiga lacuna de 6.3.10. **Item #81 pode sair da fila de pesquisa de releases**. Isso não aprova a instalação 6.3.11 nem afirma que houve QA client-side; filename, JAR instalado e versão física seguem 6.3.8.

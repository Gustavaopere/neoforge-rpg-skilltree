# AmbientSounds 6

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8129b8ded48793d4c919
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AmbientSounds 6
- **Arquivo JAR:** `AmbientSounds_NEOFORGE_v6.3.8_mc1.21.1.jar`
- **Versão 1.21.1:** 6.3.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Client-side environmental soundscape engine. Detecta contexto do mundo e mistura ambience loops/sound pools por bioma, região e condições, com transições suaves, debug/configuração e suporte a packs de áudio. A build 6.3.8 adiciona a categoria de som `suspense`. Não altera spawn, clima, worldgen ou regras de gameplay.
- **Dependências:** CreativeCore é obrigatória; o pack instala `CreativeCore_NEOFORGE_v2.13.44_mc1.21.1.jar` (2.13.44).
- **Sobreposição:** Sobreposição parcial com outros mods de ambience/soundscape. Não substitui Presence Footsteps, Sound Physics ou sistema de música; atua em outra camada de áudio.
- **Compatibilidade/Riscos:** Risco principal é composição de áudio: outros ambience/sound mods podem tocar camadas simultâneas e elevar volume/ruído/custo. Presence Footsteps não é duplicata: trata passos/material sob o jogador, enquanto AmbientSounds trata paisagem sonora contextual. Validar cavernas, água, biomas, dimensões, weather/daytime e categoria suspense. Como é client-side, não deve ser usado como authority de eventos de servidor.
- **Observações:** Qualquer resource/sound pack que acrescente ambience precisa ser auditado junto porque pode mudar completamente a experiência sem alterar o JAR. O mod reage ao mundo; não é fonte de verdade para biome/weather/spawn.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial AmbientSounds 6.3.8 + CreativeCore 2.13.44 + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ambientsounds
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — client-only ambience authority, CreativeCore 2.13.44, suspense category, resource-pack composition and audio QA confirmed in global QC #25.
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
**CreativeCore** é a dependência técnica obrigatória. O runtime físico atual contém `CreativeCore_NEOFORGE_v2.13.44_mc1.21.1.jar`, versão 2.13.44.

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
1. Startup com CreativeCore 2.13.44.
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

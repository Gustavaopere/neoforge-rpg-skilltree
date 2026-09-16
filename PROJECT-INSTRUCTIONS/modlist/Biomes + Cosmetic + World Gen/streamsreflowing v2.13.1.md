# StreamsReflowing

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `StreamsReflowing-1.21.1-neoforge-2.13.5.jar`
- **Versão 1.21.1:** 2.13.5
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen
- **Função:** Sistema de hidrologia/worldgen com streams que seguem relevo, lakes/ponds em diferentes elevações, corrente direcional opcional em rivers e organic water flow configurável.
- **Dependências:** Standalone segundo documentação oficial; sem hard dependency central. Integra por comportamento com terrain/worldgen providers, Create water wheels, estruturas e VFX de água.
- **Sobreposição:** Cruza com terrain/river/worldgen e fluid/particle mods; incompatibilidades específicas devem ser testadas contra providers realmente instalados.
- **Compatibilidade/Riscos:** Chunkgen/presets, carving em terrain/structures, seams old/new chunks, river tags modded, competição hidrológica e carga de partículas. Os gates antigos de Create water wheel/restart e waterfall spray permanecem; 2.13.2–2.13.5 adicionam performance de watershed, structure blocking, Caves Reflowing e fixes de stalls/current.
- **Observações:** mod id `streamsreflowing`; runtime 2.13.5. Config/preset físico do pack não foi lido. Em 16/09/2026 há 2.13.7 upstream para outras linhas 1.21.x; não é promovida aqui como versão instalada nem como compat 1.21.1 validada sem arquivo específico correspondente.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial/changelog 2.13.2–2.13.5. Nenhum teste de seed/chunkgen/corrente/restart foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/streams-reflowing ; https://www.curseforge.com/minecraft/mc-mods/streams-reflowing/files/all?version=1.21.1
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — runtime físico atualizado de 2.13.1 para 2.13.5; dossiê reconciliado aos deltas 2.13.2–2.13.5. Certificação pendente de QC/re-fetch final.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

> 🏞️ **ESCOPO CANÔNICO.** Runtime físico: `StreamsReflowing-1.21.1-neoforge-2.13.5.jar`, mod id `streamsreflowing`, versão `2.13.5`. Streams Reflowing gera hidrologia terrain-following e correntes configuráveis; não substitui terrain/biome provider.

## 1. Authority e geração
O mod é authority de seus streams, basins/lakes, cálculo de corrente, fluxo direcional opcional de rivers e presets de hydrology. Terralith/outros providers continuam definindo relevo/biomas. Streams seguem contornos/elevação, procuram drainage coerente e podem atravessar/terminar em lakes, rivers e oceans conforme algoritmo/config.

## 2. Lakes, directional rivers e organic flow
Lakes/ponds podem aparecer em diferentes cotas e variar com aridez; distribuição exata depende de config/seed. Rivers conectados podem receber corrente rumo ao oceano, movendo boats/items quando suportados. Organic flow pode preservar direção/momentum sobre source blocks e é relevante para waterfalls e máquinas como Create water wheels.

## 3. Presets e custo
Presets de **Potato a Max** controlam precisão de drenagem, área de watershed e custo de geração. Upstream recomenda presets menores em packs pesados e permite abordagem de preset maior durante pregen. A configuração local não foi lida.

## 4. Compatibilidade de worldgen
O projeto busca coexistência com terrain/worldgen sem tomar ownership de biomes, mas isso não é garantia universal. Incompatibilidades públicas históricas incluem Terrain Diffusion/Tellus e orientações específicas para William Wythers; não extrapolar. Testar seed nova com providers atuais.

## 5. Histórico 2.13.1 preservado
A 2.13.1 corrigiu rivers terminando em pond incapaz de receber outflow, Create water wheels que paravam após server restart e falsos waterfall spray/particle limits na série precedente. Esses gates permanecem úteis no runtime 2.13.5.

## 6. Atualização instalada — 2.13.2 a 2.13.5
O changelog oficial da linha instalada registra:
- preparação de stream regions aproximadamente duas vezes mais rápida / destaque de ~50% faster watershed generation;
- chunks próximos de água esperando menos pela geração;
- retirada temporária de fallen logs/toppled trees/driftwood da 2.13.0;
- estruturas deixando de gerar em streams/lakes e locate/trackers evitando alvos que não serão gerados;
- correções de boulders/terrain cortados em padrões de chunk e de freezing em patchwork;
- streams alcançando água oceânica/river/swamp real em vez de terminar em shallow water/sea-level falso;
- suporte ao addon **Caves Reflowing** para outlets subterrâneos quando instalado;
- mobs/items/boats em Forge/NeoForge seguindo a velocidade real da corrente;
- água acima/abaixo do stream deixando de herdar corrente indevida;
- integração melhor com **Effective** e **Particular** para waterfalls reais;
- diagnóstico de stall quando preparação de stream region trava;
- 2.13.5 remove regressão de 2.13.4 no dry-outlet fix, restaura o ganho de preparação de watershed e evita preparar regiões vizinhas só para checar structures;
- regions aguardadas pelo jogador deixam de degradar drasticamente mais tarde na sessão;
- stall diagnostic deixa de ser escrito quando o jogador está parado em land já carregada;
- mundos criados em 2.12.9+ preservam streams existentes após update em vez de re-prepará-los.

## 7. Client/server e lifecycle
Worldgen/corrente são authority do mundo/servidor; cliente renderiza água/particles. Validar geração nova, pregen, chunk load/unload, restart, streams multi-chunk, upgrade em mundo existente e fronteira old/new chunks. Conteúdo já gerado não deve ser tratado como automaticamente regenerável após update.

## 8. Integrações concretas
- **Terralith/outros terrain providers:** preservar cliffs/shorelines/structures.
- **Create 6.0.10:** water wheels continuam regression gate.
- **Flowing Fluids:** separar hydrology/current de fluid simulation.
- **Effective/Particular/Subtle Effects/Particle Rain:** VFX podem somar custo; 2.13.4 melhora integração específica com Effective/Particular.
- **Caves Reflowing:** suporte existe, mas só é path ativo se addon estiver instalado.

## 9. Riscos
1. custo de chunkgen/preset;
2. carving visualmente ruim apesar de compat pretendida;
3. seams old/new chunks;
4. regressão de current após restart;
5. tags de river modded incompletas;
6. particle load;
7. competição com outros waterways;
8. interpretação divergente de corrente por Create/Flowing Fluids;
9. stalls/regressões de preparation introduzidas por versões intermediárias;
10. structure blocking alterando layout esperado.

## 10. Matriz de testes
- [ ] Dedicated server gera seed nova com 2.13.5.
- [ ] Streams seguem relevo e alcançam drainage plausível.
- [ ] Lakes em biomas úmidos/secos são coerentes.
- [ ] Rivers direcionais/boats/items seguem corrente quando habilitado.
- [ ] Organic flow se comporta conforme config.
- [ ] Create water wheel continua após restart.
- [ ] Waterfalls reais/VFX não surgem em pool edges falsos.
- [ ] Streams cruzam chunks e sobrevivem unload/reload.
- [ ] Pregen mede custo do preset escolhido.
- [ ] Old/new chunks não criam cortes críticos.
- [ ] Structures/boulders não sofrem regressões conhecidas.
- [ ] Update de mundo 2.12.9+ preserva streams existentes.

**Nenhum teste foi executado nesta reauditoria documental.**

## 11. Evidências e limites
A modlist física confirma 2.13.5. CurseForge oficial confirma a série 2.13.2–2.13.5 e seus deltas de performance, structures, currents, Caves Reflowing e lifecycle de atualização. A configuração local continua não lida; compat publicada não substitui teste do stack.

## 12. Reauditoria física — 16/09/2026
O runtime físico mudou de `2.13.1` para `2.13.5`. Todo o escopo migrado do Notion foi mantido e os deltas documentados foram incorporados. Decisão **Sem decisão** preservada; nenhum teste runtime foi promovido.
# LambDynamicLights

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814a9815cb5cad57f6c5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — LambDynamicLights `4.8.11`, Sable Dynamic Lights `2.0.1`, Sodium `0.8.13` e Iris `1.8.14-beta.1` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** LambDynamicLights
- **Arquivo JAR:** `lambdynamiclights-4.8.11+1.21.1.jar`
- **Versão 1.21.1:** 4.8.11+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, QoL
- **Função:** Engine client-side de iluminação dinâmica renderizada para itens/entidades; altera lightmap/render visual, não o light level server-side do mundo.
- **Dependências:** NeoForge client 1.21.1. Bundle físico embarca runtime/API e Yumi/SpruceUI/Pride Lib. Sodium é integração/recomendação de performance, não hard dependency. Sable Dynamic Lights 2.0.1 depende de LambDynamicLights e o estende para Create/Sable.
- **Sobreposição:** LambDynamicLights é o engine escolhido. Sable Dynamic Lights 2.0.1 o estende para contraptions/sublevels e depende dele; não é substituto. Evitar engines concorrentes como Sodium Dynamic Lights/RyoamicLights, listados upstream como incompatíveis.
- **Compatibilidade/Riscos:** Stack físico: Sodium 0.8.13 + Iris 1.8.14-beta.1 + Sable Dynamic Lights 2.0.1. Sable é bridge dependente do engine Lamb, não uma authority concorrente. Riscos: rebuild cost, stale/duplicate source, shader/render drift, competing dynamic-light engine e EOL da linha 1.21.
- **Observações:** JAR top-level `lambdynamiclights-4.8.11+1.21.1.jar`, mod id `lambdynlights`; runtime NeoForge interno `lambdynlights_runtime` 4.8.11 com mixins próprios. 4.8.11 corrige duplicate keybind que podia corromper `options.txt`.
- **Procedência:** modlist.txt física atual + Modrinth/CurseForge oficiais LambDynamicLights 4.8.11 + source oficial README/HOW_DOES_IT_WORK/CHANGELOG + Sable Dynamic Lights oficial para a relação de bridge.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lambdynamiclights/files/all
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — runtime/packaging 4.8.11 auditados; lightmap authority, chunk rebuild, Sodium/Iris, Sable Dynamic Lights bridge, EOL, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:** Mantido como parte do stack escolhido de iluminação dinâmica com Sodium e Sable Dynamic Lights. As implementações Sodium/Embeddium Dynamic Lights e bridges Create concorrentes foram removidas em 15/08/2026.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `lambdynamiclights-4.8.11+1.21.1.jar`, mod id top-level `lambdynlights`, versão `4.8.11+1.21.1`. É uma camada **client-side de iluminação dinâmica renderizada**; não altera o light level canônico do mundo no servidor.

## 1. Identidade e packaging físico
A modlist confirma o wrapper top-level e, dentro dele, o runtime NeoForge `lambdynamiclights-4.8.11+1.21.1-neoforge.jar` com mod id `lambdynlights_runtime` e mixins `lambdynlights.mixins.json` + `lambdynlights.lightsource.mixins.json`.
O bundle também contém componentes internos, portanto **não top-level**: LambDynamicLights API 4.8.11, Yumi Minecraft Libraries: Foundation 1.0.0-beta.1, Yumi Commons Core/Collections/Event 1.0.0, SpruceUI 6.2.5 e Pride Lib 1.2.4, com variantes de mapping/packaging do bundle. Não criar páginas independentes apenas por aparecerem sob `META-INF/jars/`.

## 2. Papel no modpack
LambDynamicLights faz entidades e itens luminosos iluminarem visualmente o ambiente enquanto se movem. O upstream documenta, por padrão, itens segurados que emitem luz como bloco, entidades em chamas e fontes naturais como blaze, fireball, glow squid, magma cube, spectral arrow e allay; resource packs podem customizar luminância de itens e ampliar suporte a itens modded.
Algumas fontes podem deixar de emitir ou ter comportamento reduzido na água conforme o tipo/configuração. Isso é comportamento visual da implementação, não uma alteração de block light persistente.

## 3. Authority / ownership
LambDynamicLights é authority do **cálculo/renderização de dynamic light** e da API `lambdynlights_api`. Minecraft/servidor continuam authority de skylight/block light reais, mob spawning, crop growth e qualquer mecânica que consulta iluminação lógica do mundo.
Consequência operacional: uma tocha na mão pode clarear a tela sem tornar o bloco logicamente iluminado para regras server-side. Quests, AI ou mods próprios não devem consultar o efeito visual como source of truth de iluminação.

## 4. Como a luz é calculada
O documento técnico oficial descreve o método por **lightmap coordinates**, não a estratégia de sobrescrever getters de light level do chunk. A implementação injeta no cálculo de coordenadas de lightmap usado pelo renderer, calcula a contribuição de fontes dinâmicas por distância/luminância e mantém a maior contribuição quando ela supera a luz vanilla.
O range técnico documentado é 7,75 blocos para limitar a área de atualização. A contribuição cai com a distância e é convertida para a escala de lightmap preservando precisão. `EntityRenderDispatcher#getLight` recebe tratamento equivalente para evitar entidades escuras dentro de uma área visualmente iluminada.

## 5. Chunk rebuild e performance
Dynamic light exige rebuild das seções/chunks visuais afetados. O próprio upstream classifica isso como a parte crítica de performance. Há modos `FASTEST`, `FAST` e `FANCY` que controlam frequência/qualidade de update.
A implementação acompanha a posição das fontes e marca chunks próximos para rebuild. Quando Sodium está presente, o hook de terrain setup é adaptado ao pipeline Sodium. Atualizações 4.8.x adicionaram culling/adaptive ticking e correções no scheduler para reduzir rebuilds e crashes.

## 6. Sodium, Iris e stack físico
O pack contém **Sodium 0.8.13** e **Iris 1.8.14-beta.1**. A linha LambDynamicLights 4.8.9 atualizou explicitamente a compatibilidade da tela de configuração para Sodium 0.8.x; 4.8.10 adicionou o ícone do mod nessa tela. Sodium é recomendado/compatível como otimização, não deve ser confundido com o projeto separado **Sodium Dynamic Lights**, listado pelo upstream como implementação incompatível concorrente.
Iris/shaders entram na superfície de renderização e devem ser validados visualmente, especialmente com shader packs; a presença de Iris não prova conflito por si.

## 7. Relação concreta com Sable Dynamic Lights
A modlist física contém `sable-dynamic-lights-1.21.1-2.0.1.jar`. O projeto oficial Sable Dynamic Lights declara **Create + LambDynamicLights** como dependências e Sable como opcional; ele é um **bridge/extensão alimentado pelo engine LambDynamicLights**, adicionando iluminação a contraptions/trains e Sable sub-levels.
Logo, no stack atual não existem duas authorities independentes por definição: LambDynamicLights permanece o engine, enquanto Sable Dynamic Lights estende as fontes/superfícies para estruturas móveis. Ainda assim, a composição deve ser testada para evitar fonte duplicada, rebuild redundante ou desync entre world/sublevel/contraption.

## 8. Client / server
A publicação oficial de LambDynamicLights 4.8.11 é **client-side**. O servidor não precisa usar o efeito para decidir iluminação lógica. Em multiplayer, clientes podem ter configuração/modo visual diferentes sem que isso altere o state canônico do servidor.
Sable Dynamic Lights, por outro lado, é publicado como Client & Server/bridge e pode precisar de informação de contraption/sublevel; isso não transforma LambDynamicLights em authority de gameplay server-side.

## 9. Configuração e resource packs
A tela de configuração permite habilitar/desabilitar categorias/fonte e ajustar qualidade/performance. A luminância de itens pode ser customizada por resource pack usando o sistema de dynamic light sources do mod. Mudanças de resource/config devem reconstruir somente o state visual necessário e não criar state persistente de mundo.

## 10. Lifecycle
Pontos críticos:
- client startup e aplicação dos mixins/render hooks;
- world join/disconnect e troca de dimensão limpando fontes antigas;
- entity spawn/despawn/death removendo fontes;
- item equip/unequip/drop atualizando luminância uma vez;
- chunk unload/reload invalidando rebuild tracking;
- resource reload reconstruindo definições de luminância;
- fechamento do cliente persistindo config/keybinds sem corromper `options.txt`.
A versão 4.8.11 corrige especificamente um caso de **duplicate keybind** que podia corromper `options.txt`.

## 11. Linha 1.21 em EOL
O changelog 4.8.9, 4.8.10 e 4.8.11 marca a linha Minecraft 1.21 como **End-of-Life**. A 4.8.11, publicada em 31/08/2026, é a release atual localizada para 1.21.1 e contém o fix de keybind duplicado + atualização de Yumi Foundation.
Isso não torna a versão insegura ou quebrada, mas aumenta risco de manutenção futura: regressões específicas do pack podem não receber backport nessa linha.

## 12. Riscos técnicos
1. **False authority:** tratar iluminação visual como block-light server-side.
2. **Chunk rebuild cost:** muitas fontes móveis causarem CPU/GPU rebuild excessivo.
3. **Duplicate source/rebuild:** bridge de contraptions registrar a mesma fonte mais de uma vez.
4. **Stale source:** entidade/sublevel descarregado continuar iluminando visualmente.
5. **Shader/render incompatibility:** Iris/shader pack alterar lightmap ou pipeline esperado.
6. **Competing implementation:** instalar Sodium Dynamic Lights/RyoamicLights ou outro engine concorrente junto ao LambDynamicLights.
7. **Mixin/render drift:** updates de Sodium/Iris/Minecraft mudarem hooks de renderer.
8. **EOL:** ausência de manutenção futura específica para MC 1.21.

## 13. Boundary para projetos próprios
Não usar dynamic light renderizado para liberar perk, impedir spawn, determinar stealth, calcular temperatura, validar ritual ou qualquer mecânica autoritativa. Se um projeto próprio precisar de “luz real”, deve consultar o light engine/state server-side apropriado. Para integração visual, preferir a API do LambDynamicLights/bridge correspondente em vez de criar um segundo dynamic-light engine.

## 14. Matriz de testes
- [ ] Client cold boot com LambDynamicLights 4.8.11 + Sodium 0.8.13 + Iris 1.8.14-beta.1.
- [ ] Dedicated server aceita clientes com/sem o efeito sem depender do light visual.
- [ ] Tocha/item luminoso na mão ilumina visualmente, mas não altera regra server-side de block light.
- [ ] Equip/unequip/drop atualiza a fonte exatamente uma vez.
- [ ] Entidade em chamas/spawn/despawn não deixa luz fantasma.
- [ ] Troca de dimensão e relog limpam fontes/rebuild tracking.
- [ ] Sable Dynamic Lights 2.0.1 ilumina Create contraptions/sublevels sem duplicação ou flicker.
- [ ] World ↔ sublevel ↔ contraption se iluminam conforme o bridge sem rebuild runaway.
- [ ] Shader pack ativo via Iris não quebra lightmap, transparência ou emissives.
- [ ] `FASTEST`, `FAST` e `FANCY` apresentam comportamento/performance coerente.
- [ ] Resource reload reaplica definições de luminância sem stale cache.
- [ ] Fechar/reabrir o jogo preserva keybind/config sem corromper `options.txt`.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- modlist física: wrapper 4.8.11, runtime NeoForge, API e bibliotecas embarcadas; Sodium 0.8.13, Iris 1.8.14-beta.1 e Sable Dynamic Lights 2.0.1 presentes;
- Modrinth/CurseForge oficiais: 4.8.11 para 1.21/1.21.1, ambiente client-side e changelog/EOL;
- source oficial: README e `HOW_DOES_IT_WORK.md` para lightmap, entity lighting, chunk rebuild e integração Sodium;
- Sable Dynamic Lights oficial: bridge Create/Sable dependente de LambDynamicLights.
Nenhum FPS ganho/perda foi inventado. O efeito com shaders específicos e a ausência de double-source no stack atual ainda dependem de teste runtime.

# Create: Smart Bounds

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a38fa9dd2aaa88dfbc
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Create: Smart Bounds
- **Arquivo JAR:** `smart_bounds-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Performance
- **Função:** Otimização client-side que reduz render bounding boxes exageradas de block entities Create e evita recomputações inúteis de bounds, especialmente em belts e grandes fábricas/contraptions.
- **Dependências:** Create 6.0.10 é required e está presente. Client-side. Stack de render/performance físico relevante: Sodium 0.8.13, Iris 1.8.14-beta.1, ImmediatelyFast 1.6.13 e EntityCulling 1.10.5; coexistência é complementar até que profiling prove conflito/redundância.
- **Sobreposição:** Complementa culling/render optimizers gerais, mas atua especificamente nos bounds/caching das BEs Create. Não substitui EntityCulling, Sodium, ImmediatelyFast nem otimização de tick server-side.
- **Compatibilidade/Riscos:** Client-only optimization. Riscos: bounds pequenos demais causando pop/culling de parts/connections, cache stale após state change, Create API drift e interação visual com Sodium/Iris/ImmediatelyFast/EntityCulling. Não altera machine tick/gameplay.
- **Observações:** 1.0.0 é a única Release NeoForge 1.21.1 publicada. Suporte upstream atual: Mechanical Arms, Belts, Chain Conveyors, Factory Panels, PSI & Deployers, Rollers e Frog Ports. `/neoforge debug_blockentity_renderbounds true` permite inspecionar bounds.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/Modrinth oficiais Create: Smart Bounds 1.0.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-smart-bounds/files/6731245
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Create: Smart Bounds 1.0.0 reconstruído: render-bounds culling, bounds caching, supported Create BEs, NeoForge debug command, client lifecycle, render-stack overlap, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `smart_bounds-1.0.0.jar`, mod id `smart_bounds`, versão `1.0.0`, NeoForge 1.21.1. É uma otimização **client-only** focada em render bounding boxes e caching de block entities do Create; não muda ticking, recipes nem lógica funcional das máquinas.

## 1. Identidade e papel
- **Mod:** Create: Smart Bounds.
- **JAR:** `smart_bounds-1.0.0.jar`.
- **Mod id:** `smart_bounds`.
- **Versão:** `1.0.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release / first release.
- **Ambiente:** Client.
- **Mixin:** `smart_bounds.mixins.json`.
- **Dependência:** Create 6.0.10 presente.

## 2. O problema de render bounds
Minecraft evita renderizar objetos fora da visão usando bounding boxes. Block entities podem declarar bounds maiores que um bloco quando renderizam partes externas, como conexões.

O upstream explica que várias BEs do Create usam bounds excessivamente amplos. Resultado: máquinas podem continuar renderizando mesmo quando nenhum elemento relevante está realmente visível.

## 3. Estratégia do mod
Smart Bounds reduz essas caixas para regiões mais próximas do que cada BE realmente desenha. O objetivo é aumentar a efetividade do frustum/culling e reduzir trabalho inútil de render.

É uma otimização de **visibilidade**, não de simulação.

## 4. Bounds caching
O projeto também reestrutura caching de bounds. Bounds não precisam ser recriados a cada frame e, para certos blocos, nem a cada tick.

O exemplo upstream é **Belts**: anteriormente a bounding box podia ser recarregada todo game tick, 20 vezes por segundo. O mod passa a atualizar somente em eventos necessários, como break/replace conforme o comportamento documentado.

## 5. Mechanical Arms
Mechanical Arms são explicitamente suportados. Bounds precisam incluir corretamente a área visual ocupada pelo braço/alcance que o renderer realmente precisa, sem manter um volume absurdo permanente.

Durante rotação/movimento visual, reduzir a caixa demais pode causar partes desaparecendo perto das bordas da câmera.

## 6. Belts
Belts são um alvo central tanto para bounds quanto para caching. Em linhas longas/complexas, o ganho potencial vem da redução da área considerada visível e da eliminação de recomputações inúteis.

Testar belts curtas, longas, diagonais, verticais e conectadas a funnels/tunnels para garantir que conexões não sejam cortadas.

## 7. Chain Conveyors
Chain Conveyors precisam de bounds maiores que o bloco por causa das chains/connections. Esse é exatamente o tipo de BE em que bounds muito pequenas causariam visual clipping, enquanto bounds enormes anulam culling.

Regression gate: chain entre pontos distantes continua visível quando qualquer trecho relevante está em tela.

## 8. Factory Panels
Factory Panels são suportados pelo upstream. Painéis/conexões associados precisam continuar visíveis sob camera angles extremos sem manter uma área de render exagerada quando totalmente fora da tela.

## 9. PSI & Deployers
O projeto lista **PSI & Deployers** entre os targets. Deployers possuem parts/model animation além do bloco-base; bounds precisam acompanhar o renderer sem cortar hand/tool/extension.

## 10. Rollers e Frog Ports
Rollers e Frog Ports também são explicitamente suportados. Como elementos podem ter partes/animation fora do cube unitário, validar visibilidade durante movimento/uso e contraptions.

## 11. Debugging NeoForge
O upstream recomenda o comando:
`/neoforge debug_blockentity_renderbounds true`
para visualizar block-entity render bounds.

Esse comando é a ferramenta preferencial de QA: permite verificar se a box contém tudo que precisa ser renderizado e comparar áreas exageradas antes/depois.

## 12. Large factories/contraptions
O autor descreve o ganho como pequeno por instância, mas potencialmente relevante em **grandes contraptions e machine areas**. O efeito esperado cresce com a quantidade de BEs Create presentes/visíveis.

Não prometer FPS específico: medir frame time e rendered BEs no pack real.

## 13. Authority e client boundary
- **Create:** state da BE, machine logic e renderer funcional original.
- **Smart Bounds:** caixa de visibilidade/cache de render.
- **Servidor:** continua processando a máquina independentemente de ela estar visualmente culled.

Culling incorreto só deve afetar imagem, nunca output/tick/redstone/kinetic state.

## 14. Interação com render optimizers do pack
O pack contém Sodium `0.8.13`, Iris `1.8.14-beta.1`, ImmediatelyFast `1.6.13` e EntityCulling `1.10.5`.

Esses mods atuam em outros níveis do render pipeline. A coexistência é potencialmente complementar: Smart Bounds fornece uma caixa melhor; outros sistemas decidem como/quando renderizar. Redundância/conflito só deve ser declarada após profiling ou artefato concreto.

## 15. Iris/shaders
Shaders não mudam o state da bounding box, mas podem tornar clipping/pop mais visível por sombras/lighting e elevar custo das BEs ainda renderizadas.

Testar o mesmo factory view com shader on/off para separar erro de bounds de custo do shader.

## 16. Lifecycle de cache
Validar eventos que podem alterar geometria/bounds:
- place/break/replace;
- connection change;
- belt length/path change;
- Deployer state/tool;
- chunk load/unload;
- resource reload;
- contraption assembly/disassembly;
- reconnect/world reload.

Cache stale não pode conservar box de state anterior.

## 17. Riscos técnicos
1. **Under-bounding:** parte visual desaparece cedo demais.
2. **Connection clipping:** chain/belt connection fica fora da box.
3. **Cache stale:** alteração de state não invalida bounds.
4. **Create drift:** renderer/BE muda e patch 1.0.0 não cobre novo comportamento.
5. **Contraption edge case:** transformação móvel usa bounds não projetada corretamente.
6. **Optimizer interaction:** culling adicional expõe timing/render bug de outro mod.
7. **False performance claim:** pack não tem densidade de BEs suficiente para ganho mensurável.

## 18. Matriz de testes
- [ ] Cliente inicia com Create 6.0.10 + Smart Bounds 1.0.0.
- [ ] `/neoforge debug_blockentity_renderbounds true` mostra boxes coerentes.
- [ ] Mechanical Arm permanece completamente visível em todos os ângulos relevantes.
- [ ] Belts não perdem segmentos/conexões ao entrar/sair do frustum.
- [ ] Belt bounds não são recomputadas inutilmente em state estável conforme profiling/debug disponível.
- [ ] Chain Conveyor connection distante permanece visível quando necessário.
- [ ] Factory Panels não somem prematuramente.
- [ ] Deployer extension/tool renderiza dentro da box correta.
- [ ] Rollers/Frog Ports sem clipping.
- [ ] Assembly/disassembly de contraption invalida/cacheia bounds corretamente.
- [ ] Sodium/ImmediatelyFast/EntityCulling coexistem sem missing render.
- [ ] Iris shader on/off não muda funcionalidade das bounds.
- [ ] Frame-time comparison em factory densa mede benefício real.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências e limites
- Modlist física atual: Smart Bounds 1.0.0, Create 6.0.10 e render stack relevante.
- CurseForge/Modrinth oficiais: objetivo de reduzir Create BE render bounds, caching e lista de BEs suportadas.
- Release 1.0.0: primeira release NeoForge 1.21.1.
- **Limite:** não foi executado profiling/FPS benchmark; benefício no pack real permanece hipótese mensurável, não resultado aprovado.

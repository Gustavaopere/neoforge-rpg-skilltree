# Sodium

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81f0b9f0f82b1ae021ce
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sodium-neoforge-0.8.13+mc1.21.1.jar`, mod id `sodium`, runtime `0.8.13+mc1.21.1`, mixins `sodium-neoforge.mixins.json` e `sodium-common.mixins.json`; Iris 1.8.14-beta.1, Reese's Sodium Options 2.2.3, LambDynamicLights 4.8.11, Entity Texture Features 7.2.1, EntityCulling 1.10.5 e Create: Smart Bounds 1.0.0 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sodium 0.8.13 e o stack gráfico citado estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sodium
- **Arquivo JAR:** `sodium-neoforge-0.8.13+mc1.21.1.jar`
- **Versão 1.21.1:** 0.8.13+mc1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Performance
- **Função:** Substitui e otimiza partes centrais do renderer client-side do Minecraft, reduzindo custo de chunk/world rendering, melhorando frame rate e micro-stutter e expondo infraestrutura de opções gráficas consumida por addons.
- **Dependências:** NeoForge 1.21.1. Client-side. Integrações físicas relevantes: Iris 1.8.14-beta.1, Reese's Sodium Options 2.2.3, LambDynamicLights 4.8.11, Entity Texture Features, EntityCulling 1.10.5 e Create: Smart Bounds 1.0.0. Não foi identificada alternativa concorrente de renderer top-level.
- **Sobreposição:** É o núcleo de otimização do world renderer. Complementa culling, shader, dynamic-light e bounds optimizers; não deve ser tratado como duplicata de EntityCulling, ImmediatelyFast ou Smart Bounds sem profiling.
- **Compatibilidade/Riscos:** Core client-side de render. Riscos: renderer/API drift, shader integration, culling excessivo, option conflicts, buffer/native regressions e incompatibilidade com mods que assumem pipeline vanilla. 0.8.13 é Release e corrige buffer/native/config-API issues; stack físico relevante inclui Iris, Reese's Sodium Options, LambDynamicLights, ETF, EntityCulling e Smart Bounds.
- **Observações:** 0.8.13+mc1.21.1 é Release NeoForge 1.21.1. Delta exato inclui mensagens de crash/config mais claras, fix de buffer overflow nativo, correções de operações atômicas/native buffer, environment cleanup e prioridade override/overlay na Config API.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth/CurseForge oficiais Sodium 0.8.13 para NeoForge 1.21.1 + integrações físicas confirmadas no catálogo atual.
- **Fonte:** https://modrinth.com/mod/sodium/version/mc1.21.1-0.8.13-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sodium 0.8.13 reconstruído: renderer replacement, chunk/render pipeline boundary, Config API, integrações Iris/Reese's/LambDynamicLights/ETF/EntityCulling/Smart Bounds, delta exato 0.8.13, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sodium-neoforge-0.8.13+mc1.21.1.jar`, mod id `sodium`, versão `0.8.13+mc1.21.1`, NeoForge 1.21.1. Sodium é um **renderer replacement client-side** orientado a performance; não altera regras de gameplay do servidor.

## 1. Identidade e papel
- **Mod:** Sodium.
- **JAR:** `sodium-neoforge-0.8.13+mc1.21.1.jar`.
- **Mod id:** `sodium`.
- **Versão:** `0.8.13+mc1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client.
- **Papel:** substituir/otimizar partes centrais do pipeline de renderização do mundo e reduzir frame-time, stutter e trabalho gráfico desnecessário.

## 2. Authority e boundary
Sodium é authority da implementação otimizada de várias etapas do renderer que ele substitui. Minecraft/world continua authority de chunks, block states, entities e gameplay; Sodium apenas decide como esses dados são preparados e desenhados no cliente.

Um artefato visual não deve ser confundido com state incorreto do servidor. Para diagnosticar, comparar world state e render separadamente.

## 3. Chunk/world rendering
O objetivo principal publicado é melhorar a eficiência do world renderer. Em packs grandes, isso afeta especialmente cenas com muitos chunks, block models e geometry updates.

Chunk rebuild, camera movement, render-distance changes e dimension swaps são lifecycle crítico: cache/mesh antigo não pode sobreviver ao contexto errado.

## 4. Micro-stutter e frame pacing
Além de FPS médio, o projeto enfatiza redução de micro-stutter. Portanto benchmark útil deve observar **frame time**, não apenas FPS instantâneo.

Travadas ao entrar em chunks, alterar resource packs ou movimentar contraptions devem ser medidas antes de atribuir causa a Sodium.

## 5. Configuração gráfica
Sodium fornece sua própria infraestrutura de opções gráficas e uma Config API usada por addons. A 0.8.13 inclui ajuste de prioridade **override/overlay** nessa API.

Addons como Reese's Sodium Options podem reorganizar/expor opções, mas não se tornam authority do renderer em si.

## 6. Delta exato da 0.8.13
A release física registra correções pontuais, entre elas:
- mensagem de crash/config mais clara quando NeoForge apresenta erro grave ou configuração de mod ausente;
- fix de buffer overflow em caminho nativo de `getModuleFileName`;
- correção de operações atômicas relacionadas à contabilidade de native buffers;
- cleanup de environment variable via mecanismo apropriado;
- prioridade correta de override/overlay na Config API.

Esses são deltas específicos da 0.8.13; não atribuir à build mudanças maiores sem changelog correspondente.

## 7. Native memory e buffers
Como a release toca native buffers, crashes nativos, contadores inconsistentes ou crescimento anormal de memória gráfica são regression gates relevantes.

Isso não significa que Sodium seja o único allocator do processo. Diagnóstico deve separar heap Java, native memory, driver e GPU memory.

## 8. Iris
O pack contém **Iris 1.8.14-beta.1**. Iris integra shaders ao pipeline Sodium; os dois formam uma boundary estrutural de alto impacto.

Testar shader off/on, reload, troca de shader, dimension change e resource reload. Um bug que só existe com shader ligado deve ser triado como integração, não automaticamente como defeito isolado do Sodium.

## 9. Reese's Sodium Options
**Reese's Sodium Options 2.2.3** está instalado e modifica organização/UX das opções Sodium. Ele deve refletir valores válidos do renderer e não criar duas fontes de verdade para a mesma setting.

Config save/restart precisa preservar exatamente o valor selecionado.

## 10. Dynamic lights e entity textures
O pack contém LambDynamicLights e Entity Texture Features. Ambos consomem/renderizam dados em etapas sensíveis do pipeline gráfico.

Sodium deve coexistir sem missing geometry, lighting stale, flicker ou entity texture corruption. Esses mods continuam owners de seus recursos específicos.

## 11. EntityCulling e Smart Bounds
**EntityCulling** decide culling adicional de entidades/block entities; **Create: Smart Bounds** melhora bounds de BEs Create. Sodium continua renderer base.

As camadas podem ser complementares, mas culling excessivo pode fazer objetos desaparecerem cedo. Qualquer remoção por suposta redundância exige reprodução/profiling.

## 12. ImmediatelyFast e outros optimizers
Optimizers client-side podem reduzir custos diferentes do mesmo frame. Coexistência não implica duplicação integral.

Medir CPU render thread, frame time, chunk rebuilds e GPU bottleneck antes de concluir que um mod é desnecessário.

## 13. Contraptions/Create/Sable
O pack usa Create/Sable/Aeronautics. Estruturas móveis, block entities transformadas e sublevels são regression surfaces porque o renderer recebe geometry/contexto não puramente vanilla.

Testar assembly/disassembly, movimento, chunk crossing, camera clipping e shader on/off sem assumir compatibilidade perfeita apenas pelo boot.

## 14. Resource reload e lifecycle
Validar:
- startup e world join;
- resource-pack reload;
- language/UI change quando afeta screens de options;
- shader reload;
- dimension change;
- render-distance change;
- fullscreen/window resize;
- disconnect/reconnect;
- chunk unload/rebuild.

Caches gráficos não podem vazar entre worlds/dimensões.

## 15. Multiplayer
Sodium não muda authority multiplayer. Cliente pode deixar de renderizar algo por erro local sem que a entity/block deixe de existir para servidor/outros jogadores.

Ao investigar desync aparente, comparar outro cliente e dados server-side antes de alterar gameplay mods.

## 16. Riscos técnicos
1. **Renderer API drift:** addon espera versão Sodium diferente.
2. **Shader integration:** Iris/shader altera path e expõe regressão.
3. **Over-culling:** EntityCulling/bounds fazem objeto sumir.
4. **Native buffer regression:** leak/crash/contabilidade incorreta.
5. **Chunk cache stale:** geometry antiga persiste após update/reload.
6. **Config conflict:** addon de options sobrescreve valor inesperadamente.
7. **Contraption render:** moving structures usam transform/bounds incompatíveis.
8. **Driver/GPU edge case:** problema nativo parece bug de gameplay.

## 17. Matriz de testes
- [ ] Cliente inicia com Sodium 0.8.13 sem erro.
- [ ] World vanilla/modded carrega chunks sem missing geometry.
- [ ] Frame-time é comparado com cenário reprodutível, não apenas FPS médio.
- [ ] Resource reload reconstrói geometry corretamente.
- [ ] Iris shader off/on e troca de shader sem corrupção persistente.
- [ ] Reese's Sodium Options salva/restaura settings corretas.
- [ ] LambDynamicLights acompanha objetos em movimento sem stale light.
- [ ] ETF/entities mantêm texturas corretas.
- [ ] EntityCulling não remove entidades/BEs visíveis indevidamente.
- [ ] Smart Bounds + Create não causa clipping prematuro.
- [ ] Contraptions/Sable sublevels renderizam durante movimento e chunk crossing.
- [ ] Dimension/reconnect não conserva caches da world anterior.
- [ ] Native memory/frame stability é observada em sessão longa.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- Modlist física atual: Sodium 0.8.13 e stack gráfico relacionado.
- Modrinth/CurseForge oficiais: Release NeoForge 1.21.1, finalidade de performance e delta exato 0.8.13.
- Catálogo atual: integrações físicas com Iris, Reese's Sodium Options, LambDynamicLights, ETF, EntityCulling e Smart Bounds.
- **Limite:** nenhum benchmark ou teste visual foi executado nesta auditoria; ganho real e compatibilidade fina dependem do hardware, shader/config e cena do pack.

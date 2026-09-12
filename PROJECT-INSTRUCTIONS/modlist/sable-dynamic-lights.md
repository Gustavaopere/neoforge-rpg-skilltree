# Sable Dynamic Lights

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815e8b7cf6d985021d57
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sable-dynamic-lights-1.21.1-2.0.1.jar`, mod id `sabledynlights`, runtime `2.0.1`, mixin `sabledynlights.mixins.json`; Create 6.0.10, LambDynamicLights 4.8.11+1.21.1 e Sable 2.0.5 presentes; Sable Companion common 1.6.0 embarcado
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sable Dynamic Lights 2.0.1, Create 6.0.10, LambDynamicLights 4.8.11+1.21.1 e Sable 2.0.5 estão presentes; o JAR do addon contém Sable Companion common 1.6.0 embarcado. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable Dynamic Lights
- **Arquivo JAR:** `sable-dynamic-lights-1.21.1-2.0.1.jar`
- **Versão 1.21.1:** 2.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat
- **Função:** Bridge de iluminação dinâmica para fontes em Create trains/contraptions e Sable sublevels, usando LambDynamicLights e projetando luz entre espaços móveis.
- **Dependências:** Create 6.0.10 + LambDynamicLights 4.8.11+1.21.1 presentes; Sable 2.0.5 também presente e ativa o escopo de sublevels. JAR inclui Sable Companion common 1.6.0 embarcado.
- **Sobreposição:** Não duplica LambDynamicLights: LambDynamicLights é o engine-base; este addon resolve iluminação em transforms móveis Create/Sable. Evitar bridges concorrentes para o mesmo escopo.
- **Compatibilidade/Riscos:** Beta intencional da linha 2.x. Riscos: ghost/stale lights, transform mismatch, double-lighting com bridges concorrentes, performance sob muitas fontes móveis, provider drift e embedded Companion drift.
- **Observações:** 2.0.1 é Beta; 1.0.0 é Release. A linha 2.x é mantida porque desde 2.0.0 world, sublevels e contraptions iluminam uns aos outros; 2.0.1 registra melhoria significativa de performance.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + CurseForge oficial Sable Dynamic Lights 2.0.1 + documentação oficial Sable Companion.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-dynamic-lights
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Sable Dynamic Lights 2.0.1 reconstruído: Create/Sable/LambDynamicLights ownership, world↔sublevel↔contraption lighting, Companion embedded, lifecycle, performance, riscos e testes.
- **Histórico da decisão:** 2026-09-06 — decisão Manter confirmada; pesquisa fechada. O uso da linha 2.0.1 é justificado pelas integrações adicionais com Sable/Create, não apenas por ser mais nova.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sable-dynamic-lights-1.21.1-2.0.1.jar`, mod id `sabledynlights`, versão `2.0.1`. O mod é um fork de Create: Dynamic Lights que faz fontes luminosas em **Create contraptions/trains** e **Sable sub-levels** iluminarem o ambiente dinamicamente usando LambDynamicLights. A build física 2.0.1 é Beta e contém `Sable Companion 1.6.0` embarcado via Jar-in-Jar.

## 1. Identidade e papel
- **Mod:** Sable Dynamic Lights.
- **JAR:** `sable-dynamic-lights-1.21.1-2.0.1.jar`.
- **Mod id:** `sabledynlights`.
- **Versão instalada:** `2.0.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Ambiente publicado:** Client & Server.
- **Mixin config físico:** `sabledynlights.mixins.json`.
- **Embedded:** `sable-companion-common-1.21.1-1.6.0.jar`.

## 2. Stack físico atual
O pack contém:
- **Create 6.0.10**;
- **LambDynamicLights 4.8.11+1.21.1**;
- **Sable 2.0.5**;
- **Sable Dynamic Lights 2.0.1**.

A publicação do addon declara Create + LambDynamicLights como dependências e Sable como integração opcional. Neste pack, os três providers estão presentes, então todo o escopo Create + Sable está ativo para teste.

## 3. Autoridade e ownership
- **LambDynamicLights:** engine/base de dynamic-light computation/rendering.
- **Create:** transforms e lifecycle de trains/contraptions.
- **Sable:** transforms/sub-levels móveis e seu lifecycle físico.
- **Sable Dynamic Lights:** bridge que projeta fontes de luz através desses espaços móveis e coordena a iluminação dinâmica.

O addon não deve ser tratado como fonte de iluminação estática do mundo nem como physics provider.

## 4. Contraptions e trains
A documentação oficial cita glowing blocks em Create trains e contraptions. Torches, glowstone, lanterns e outras fontes reconhecidas pelo engine podem iluminar o ambiente enquanto a estrutura se move.

Regression gate: a luz deve acompanhar o transform atual da contraption sem ficar presa na coordenada antiga ou criar fonte fantasma depois de disassembly/unload.

## 5. Sable sub-levels
Sable move chunks/blocos em um espaço de coordenadas próprio. A linha 2.x do Dynamic Lights foi criada para iluminar corretamente nesse cenário, incluindo interação entre world, sublevels e contraptions.

Desde 2.0.0, o projeto afirma que **world, sublevels e contraptions iluminam uns aos outros em todas as combinações suportadas**. Isso é a justificativa funcional para manter a linha 2.x no pack Sable.

## 6. Sable Companion embarcado
O JAR físico contém `Sable Companion 1.6.0` como Jar-in-Jar. Companion é a camada leve oficial para compatibilidade com posições/transforms de sublevels, usando implementação segura quando Sable está ausente e implementação ativa quando Sable está presente.

Essa cópia embarcada pertence ao addon e não deve gerar uma nova entrada top-level no catálogo.

## 7. Client / Server
A consequência visível é client-facing, mas a publicação classifica o mod como Client & Server. A posição real da contraption/sublevel continua derivada dos providers autoritativos; o cliente só deve calcular/renderizar a luz para o transform recebido.

Nenhuma luz dinâmica deve criar alteração funcional de block light/server spawning por mera renderização local, salvo se o provider explicitamente fizer isso — não presumido aqui.

## 8. Lifecycle
Validar:
- contraption assembly/disassembly;
- train entrando/saindo de render distance;
- sublevel load/unload;
- sublevel assembly/destruction;
- chunk load/unload;
- dimension change;
- resource reload;
- client reconnect;
- server restart com estruturas persistentes.

Luz dinâmica não deve permanecer em world coords antigas após qualquer transição.

## 9. Interações world ↔ sublevel ↔ contraption
Os cenários centrais da linha 2.x são:
- fonte no world iluminando objeto móvel;
- fonte no sublevel iluminando world;
- fonte no Create contraption iluminando sublevel;
- fonte no sublevel iluminando contraption;
- dois objetos móveis próximos com iluminação recíproca.

Esses casos devem ser testados com movimento e rotação, não apenas parados.

## 10. Performance
A 2.0.1 registra explicitamente **melhoria significativa de performance**. Isso é delta confirmado da versão instalada, mas não autoriza assumir ganho quantitativo sem benchmark local.

Medir FPS/frame time com:
- muitas fontes móveis;
- trains em movimento;
- sublevels grandes;
- shaders/resource packs do pack;
- múltiplas contraptions simultâneas.

## 11. Relação com outras soluções de dynamic lights
O pack possui LambDynamicLights como engine-base. Sable Dynamic Lights não duplica o engine: ele estende o cálculo/render para transforms Create/Sable.

Outra bridge que tente resolver as mesmas fontes móveis pode gerar double-light, trabalho duplicado ou caches concorrentes. Manter apenas integrações com escopo claro.

## 12. Maturidade da 2.0.1
A 1.0.0 é publicada como Release, enquanto 2.0.0/2.0.1 são Beta. A decisão **Manter** já existente é sustentada pelo escopo funcional: a linha 2.x amplia a iluminação recíproca entre world, Sable e Create, que é precisamente o cenário da modlist atual.

O fallback 1.0.0 só deve ser considerado mediante regressão real e teste de perda funcional, não por rótulo de canal isoladamente.

## 13. Riscos técnicos
1. **Stale light source:** luz fica na posição anterior após movimento/unload.
2. **Transform mismatch:** projeção sublevel↔world usa pose errada.
3. **Double lighting:** duas bridges registram a mesma fonte móvel.
4. **Performance regression:** muitas fontes/transforms elevam frame time.
5. **Provider drift:** Create/Sable/LambDynamicLights mudam API/caches.
6. **Reload residue:** resource reload preserva fonte antiga.
7. **Client divergence:** clientes veem luz em posições diferentes para a mesma estrutura.
8. **Embedded Companion drift:** addon inclui Companion 1.6.0 enquanto Sable evolui.

## 14. Matriz de testes
- [ ] Cliente e dedicated server iniciam com Dynamic Lights 2.0.1 + Create 6.0.10 + LambDynamicLights 4.8.11 + Sable 2.0.5.
- [ ] Tocha/luz em contraption acompanha movimento sem ghost light.
- [ ] Luz em train acompanha curvas/movimento corretamente.
- [ ] Fonte em Sable sublevel ilumina o world no transform atual.
- [ ] Fonte do world ilumina bloco/entidade dentro de sublevel.
- [ ] Contraption e sublevel se iluminam mutuamente quando próximos.
- [ ] Rotação rápida não deixa light cache na pose antiga.
- [ ] Unload/disassembly remove a fonte dinâmica antiga.
- [ ] Resource reload não duplica fontes/listeners.
- [ ] Benchmark comparativo confirma comportamento aceitável da 2.0.1 com múltiplas fontes móveis.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física canônica de 10/09/2026: JAR/mod id/runtime, providers e Companion 1.6.0 embedded.
- CurseForge oficial: fork de Create: Dynamic Lights, integração Create/Sable via LambDynamicLights e linha 2.x de iluminação recíproca.
- Changelog 2.0.1: melhoria significativa de performance.
- Sable Companion oficial: mecanismo de compatibilidade/projeção de posições em sublevels.
- **Limite:** algoritmo interno de light propagation/cache e métricas de performance não foram inferidos além das fontes publicadas.

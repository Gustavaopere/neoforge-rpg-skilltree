# spark

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db819bbebec9f7a2f840b7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `spark-1.10.124-neoforge.jar`, mod id `spark`, runtime `1.10.124` confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026” e contém revalidação física de 11/09. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**, no qual spark 1.10.124 está confirmado. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** spark
- **Arquivo JAR:** `spark-1.10.124-neoforge.jar`
- **Versão 1.21.1:** 1.10.124
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Função:** Profiler/diagnóstico para clientes e servidores: CPU sampling/call graph, heap summary/dump, GC monitoring e health metrics como TPS, tick durations, CPU, memória e disco.
- **Dependências:** NeoForge 1.21.1. A build oficial 1.10.124 suporta Client & Server e é backport release para 1.21.1. Não depende de ServerCore; ambos são complementares.
- **Sobreposição:** Não é redundante com mods de performance como ServerCore ou FerriteCore: spark mede CPU, memória, GC e health metrics, enquanto esses mods alteram/otimizam processamento. Pode observar qualquer subsystem do pack, mas não substitui o provider nem executa a otimização diagnosticada.
- **Compatibilidade/Riscos:** Ferramenta de observabilidade, não otimizador. Riscos de interpretação: sampling parcial, janela curta, thread errada, observer effect e correlação sem causalidade. Heap dumps podem ser grandes; profiles compartilhados devem ser tratados como artefatos diagnósticos do ambiente.
- **Observações:** mod id `spark`; runtime 1.10.124. CPU profiler usa sampling; engines oficiais incluem Native/Async via async-profiler em plataformas suportadas e Java via ThreadMXBean. Nenhum profile real deste pack foi coletado nesta auditoria.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth/repositório/site oficiais spark 1.10.124. Dossiê técnico de 08/09 preservado e revalidado; nenhum profile real foi executado.
- **Fonte:** https://modrinth.com/mod/spark/version/1.10.124-neoforge-1.21.1 ; https://github.com/lucko/spark ; https://spark.lucko.me/
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — spark 1.10.124 permanece exatamente o runtime físico; dossiê de CPU/memory/GC/health profiling preservado e revalidado contra a release oficial NeoForge 1.21.1.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `spark-1.10.124-neoforge.jar`, mod id `spark`, versão `1.10.124`. spark é **profiler/diagnóstico**, não otimizador: mede CPU, memória, GC e saúde do servidor/cliente para localizar gargalos.

## 1. Identidade, versão e papel
- **Mod:** spark.
- **JAR físico:** `spark-1.10.124-neoforge.jar`.
- **Mod id:** `spark`.
- **Versão instalada:** `1.10.124`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Papel:** instrumentação de performance e geração de perfis/relatórios para diagnóstico técnico.

## 2. Status da release
A página oficial da versão confirma `1.10.124-neoforge-1.21.1` para Minecraft 1.21.1. O upstream descreve esta build como **backport release**, trazendo correções da linha mais nova para a popular versão 1.21.1.

Isso não transforma a build em fork: continua sendo release oficial spark para NeoForge 1.21.1.

## 3. Authority e ownership
spark não deve ser tratado como causa ou corretor automático de performance. Seu papel é **observabilidade**:
- mede onde CPU time está sendo gasto;
- inspeciona uso de memória;
- monitora garbage collection;
- reporta métricas de saúde do processo/server.

Qualquer conclusão sobre outro mod precisa vir do profile/heap/metric real, não de presença de spark na modlist.

## 4. CPU Profiler
O profiler oficial é baseado em amostragem estatística e constrói um call graph para análise posterior.

Características publicadas:
- baixo overhead relativo e adequado para uso em produção quando necessário;
- possibilidade de amostrar threads específicas/intervalos configuráveis;
- opção de focar períodos laggy;
- viewer em árvore e flame graph;
- suporte a deobfuscation mappings no viewer.

### Engines
O projeto documenta dois engines:
- **Native/Async:** usa `async-profiler`, disponível em Linux/macOS conforme suporte da plataforma;
- **Java:** usa `ThreadMXBean` e é a alternativa JVM/portável.

O engine efetivamente usado no ambiente do usuário não foi medido nesta auditoria.

## 5. Memory Inspection
spark oferece três famílias principais:
- **Heap Summary:** visão resumida do heap por classes/instance counts;
- **Heap Dump:** snapshot HPROF completo, opcionalmente comprimido, para análise externa;
- **GC Monitoring:** acompanha frequência/duração de garbage collections e volume de memória liberada.

Heap Summary é ferramenta de triagem, não substituto de análise forense completa de heap quando o problema exige retenção/references.

## 6. Server Health Reporting
Métricas publicadas incluem:
- TPS;
- tick durations, incluindo mínimo/máximo/média;
- CPU usage do processo e sistema;
- memory usage;
- disk usage.

O upstream também consegue monitorar ticks individuais e reportar eventos acima de um threshold, permitindo correlacionar spikes com acontecimentos do jogo.

## 7. Viewer e artefatos
O ecossistema spark inclui viewer web para:
- profiles de CPU em árvore/flame graph;
- heap summaries em histogramas;
- busca/bookmarks e interpretação dos call frames.

Profiles podem ser enviados ao serviço online oficial ou exportados em formatos próprios (`.sparkprofile` / `.sparkheap`, conforme viewer atual). A decisão de compartilhar um profile deve considerar que dados técnicos do runtime ficam acessíveis no relatório gerado.

## 8. Client / server
- **Servidor:** principal uso para TPS, tick duration, CPU, heap, GC e gargalos de mods/world simulation.
- **Cliente:** pode perfilar baixo FPS/custo de render/tick do cliente.
- O mesmo mod pode existir nos dois lados, mas o profile deve ser interpretado segundo a thread/processo em que foi coletado.
- Não comparar diretamente um profile client-side de FPS com um profile server-side de TPS como se medissem o mesmo problema.

## 9. Lifecycle de diagnóstico
Fluxo recomendado para o pack:
1. reproduzir o problema com configuração estável;
2. coletar baseline curto sem mudança simultânea de mods/config;
3. capturar profile durante o evento de lag;
4. inspecionar call tree/flame graph;
5. se houver suspeita de memória, usar heap summary/GC e só então heap dump quando necessário;
6. alterar uma variável por vez;
7. repetir a coleta para comparar.

Essa disciplina evita atribuir causalidade a um mod apenas porque ele aparece na stack.

## 10. Integrações concretas no pack
- **ServerCore 1.5.19:** relação complementar. ServerCore muda/otimiza processamento; spark mede o efeito e ajuda a verificar regressões.
- **Sable/Create Aeronautics/Coasters:** physics/sublevels podem gerar hotspots; spark pode localizar custo real em tick/threads.
- **MineColonies:** grandes populações de NPCs são candidatas a profiling de AI/tick.
- **Iris/PartiCull/particle mods:** em client profiling, ajudam a separar custo de render/shaders/particles do server tick.
- **FerriteCore:** otimização de memória; heap summary pode ajudar a verificar tendência, mas uma redução global de heap não prova diretamente eficácia de um único mod sem comparação controlada.

## 11. Riscos e limitações de interpretação
1. **Sampling ≠ tracing completo:** frames curtos/raros podem ser subamostrados.
2. **Observer effect:** apesar de baixo overhead, profiling não é custo zero.
3. **Wrong thread:** analisar thread errada pode levar a conclusão inválida.
4. **Short capture:** janela curta demais pode perder picos esporádicos.
5. **Heap summary simplifica:** instance count/size não mostra todo o grafo de retenção.
6. **Online viewer/privacy técnica:** profiles compartilhados devem ser tratados como artefatos diagnósticos potencialmente sensíveis ao ambiente.
7. **Correlation ≠ causation:** método de um mod aparecer no topo não significa necessariamente bug daquele mod; pode ser caller/provider legítimo ou efeito de configuração.

## 12. Multiplayer e produção
Em servidor real, spark deve ser usado com escopo e duração adequados. Perfis longos/heap dumps grandes podem gerar I/O/memória adicional. Para incidentes, registrar horário, número de jogadores, dimensão/local, ação que disparou o spike e configuração de ServerCore para tornar a comparação reproduzível.

## 13. Matriz de testes
- [ ] Dedicated server boot com spark 1.10.124.
- [ ] Cliente inicia com a mesma build sem conflito.
- [ ] Profile CPU curto gera resultado/viewer válido.
- [ ] Captura durante spike de TPS diferencia main server thread de outras threads.
- [ ] Heap Summary retorna classes/instances coerentes.
- [ ] GC monitoring registra eventos durante carga controlada.
- [ ] Health report mostra TPS/tick durations/CPU/memória/disk.
- [ ] Profile antes/depois de mudança ServerCore com mesma carga.
- [ ] Stress Sable/Coasters/Create e identificação do hotspot sem crash.
- [ ] Client profile com Iris/particles para separar render cost de tick cost.
Nenhum teste foi marcado como aprovado nesta auditoria.

## 14. Evidências
- Modlist física canônica de 08/09/2026: JAR/mod id/versão.
- Modrinth oficial: release 1.10.124 NeoForge 1.21.1, Client & Server e backport de bug fixes.
- Repositório oficial lucko/spark: CPU profiler, memory inspection, GC monitoring, health reporting, profiler engines e viewer model.
- Site oficial spark: viewer e artefatos de profile/heap.

## 15. Revalidação física — 11/09/2026
A modlist física atual continua contendo exatamente `spark-1.10.124-neoforge.jar`, mod id `spark`, runtime `1.10.124`. A release oficial continua sendo a build NeoForge 1.21.1 usada pelo pack; não houve drift físico desde o dossiê de 08/09.

O conteúdo técnico acima permanece coerente: spark continua sendo ferramenta de observabilidade/profiling, não otimizador. **Nenhum profile, heap dump ou benchmark foi executado nesta recatalogação**, portanto a matriz de testes permanece integralmente desmarcada.

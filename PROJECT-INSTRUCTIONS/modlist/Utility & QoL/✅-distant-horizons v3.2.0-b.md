# Distant Horizons

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#222**: JAR `DistantHorizons-3.2.0-b-1.21.1-fabric-neoforge.jar`, mod id `distanthorizons`, runtime `3.2.0-b`, SHA-1 `df25e8cfe06917963778723a1d9a60610dd21bec`.

## Propriedades do registro

- **Mod:** Distant Horizons
- **Arquivo JAR:** `DistantHorizons-3.2.0-b-1.21.1-fabric-neoforge.jar`
- **Versão 1.21.1:** `3.2.0-b`
- **Categoria:** Performance, Visual
- **Função:** Sistema de Level of Detail (LOD) que gera, persiste, sincroniza quando aplicável e renderiza terreno simplificado além da distância vanilla, sem substituir o chunk real.
- **Dependências:** Minecraft 1.21.1; artefato multi-loader executado sob NeoForge 21.1.250 no pack. Bibliotecas internas em META-INF/jars permanecem dependências embarcadas, não mods top-level. Compatibilidade com shaders/renderers continua profile-dependent.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Runtime físico 3.2.0-b; upstream 1.21.1 avançou por 3.3.0→3.3.1→3.3.2→3.3.3. Riscos: config reset, database LOD stale/corrompido, CPU/RAM/I/O, render/shader/fog, API/protocol drift, Chunky coexistence e multiplayer. 3.3.3 eleva config version 4→5 e limpa config; 3.3.2 marca SSRD 1.8.6 e anteriores incompatíveis por reverse-Z.
- **Fonte:** https://gitlab.com/distant-horizons-team/distant-horizons
- **Procedência:** modlist física atual confirma `DistantHorizons-3.2.0-b-1.21.1-fabric-neoforge.jar` / 3.2.0-b. CurseForge oficial 1.21.1 foi revalidado em 01/10/2026 e mostra a sequência posterior completa 3.3.0, 3.3.1, 3.3.2 e 3.3.3, latest de 29/09/2026.
- **Observações:** Distant Horizons 3.2.0-b permanece instalado. A cadeia 3.3.x traz mudanças substanciais de API/render/worldgen/database/config; 3.3.3 é a latest pública 1.21.1. Nenhuma dessas mudanças é tratada como ativa no runtime físico.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece 3.2.0-b. Todas as releases 1.21.1 posteriores até 3.3.3 foram percorridas em ordem e os deltas relevantes foram incorporados abaixo.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão curatorial formal. Em 08/09/2026, a ficha foi reconstruída contra o runtime físico 3.2.0-b e a linha oficial 3.2, preservando a distinção entre LOD e chunks reais.
- **Sobreposição:** Sobrepõe superfícies de render distante, fog e geração de representação visual; não substitui worldgen real nem Chunky. Coexistência com shaders e outros render mods exige QA, não é conflito automático.

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `DistantHorizons-3.2.0-b-1.21.1-fabric-neoforge.jar` · mod id `distanthorizons` · versão `3.2.0-b` · Minecraft 1.21.1. O artefato é multi-loader; o pack o executa sob NeoForge.
## 1. Papel no modpack
Distant Horizons (DH) implementa um sistema de **Level of Detail (LOD)** para representar terreno muito além da distância de renderização vanilla. Ele mantém uma representação simplificada própria e pode gerar/carregar dados para alimentar essa camada distante.
DH não substitui chunks vanilla em distância de interação. Blocos, entidades, colisão, redstone, loot e gameplay continuam pertencendo ao mundo/chunk real do Minecraft e dos mods de worldgen.
## 2. Authority / ownership
- **Minecraft + stack de worldgen:** autoridade do chunk real e de seu conteúdo em resolução completa.
- **Distant Horizons:** autoridade da base de dados LOD, geração/atualização dessa representação, render distante e protocolo DH quando o modo servidor é usado.
- **Shaders/render mods:** continuam donos de seus próprios pipelines; compatibilidade visual precisa ser validada, não presumida.
Integrações não devem tratar LOD como bloco real nem escrever gameplay state a partir de uma amostra LOD.
## 3. Release 3.2.0-b
A build física é `3.2.0-b`, publicada como **Beta** para 1.21.1. A linha 3.2 introduz uma configuração/UI de configuração refeita; configs antigas são resetadas durante a migração e uma cópia `.bak` é criada. A `3.2.0-b` também corrige um conflito de recipe. Em 18/09/2026, o projeto publicou `3.3.1` para Minecraft 1.21.1; essa release é mais nova, mas **não está instalada** na modlist física atual.
Isso torna upgrade/migração de config um regression gate concreto: preservar backups e não copiar cegamente chaves antigas para o novo schema.
## 4. LOD e banco de dados
DH persiste dados LOD separados do save vanilla. A linha atual passou a organizar saves **por dimensão**, e versões recentes incluem migrações do banco de dados e compactação de dados.
Riscos operacionais:
- base LOD stale após mudança relevante de worldgen;
- migração interrompida;
- crescimento de disco;
- inconsistência entre dados antigos e chunks regenerados;
- remoção manual do banco causando apenas reconstrução visual, nunca alteração do chunk real.
Backups de mundo devem considerar tanto o save vanilla quanto os dados persistentes de DH quando se pretende preservar a cache/estado LOD.
## 5. Geração de LOD / world generation
A linha 3.1+ recebeu uma reescrita relevante de world generation para LOD e ampliou limites internos, incluindo distância vertical máxima de geração LOD de 256 para 4096 e maior concorrência de objetos. Isso pode aumentar throughput, mas também CPU, RAM, I/O e pressão sobre geração de chunks dependendo da configuração.
Em um modpack de worldgen pesado, pregen e DH não devem ser usados simultaneamente em escala alta sem medição. Chunky, Terralith, BetterEnd/BetterNether e outros providers continuam decidindo o conteúdo real; DH observa/gera representação derivada.
## 6. Client / Server
O projeto suporta uso client-side e também um modo server-side em versões recentes. A linha 3.1.3 adicionou suporte de servidor para NeoForge 1.21.1, conversão entre modos optional/required e o comando `/dh config`.
A infraestrutura multiplayer nova é descrita upstream como **experimental/incompleta (alpha)** dentro da linha 3.2. Portanto:
- não tratar a presença do mod no servidor como garantia de estabilidade de todas as combinações client/server;
- validar versões e modo exigido por cliente;
- o servidor continua authority de mundo; o cliente só deve renderizar dados permitidos/sincronizados.
## 7. Networking
A linha atual inclui upload compactado de LOD e mudanças substanciais no protocolo/multiplayer. O contrato de rede deve ser tratado como version-sensitive.
Riscos: cliente incompatível, handshake incompleto, payloads grandes, reconnect com cache stale e discrepância entre LOD local e dados recebidos do servidor.
## 8. Renderização
DH desenha terreno simplificado além da distância vanilla. Possíveis pontos de conflito incluem:
- shader pipeline;
- fog/atmosfera;
- translucência;
- clipping/culling;
- depth buffer;
- céu e dimensões com renderers especiais;
- mudanças de renderer entre mundos/dimensões.
Artefato visual distante não é evidência de worldgen incorreto até o chunk real ser carregado e comparado.
## 9. Configuração
A configuração afeta qualidade, distância, geração, concorrência e comportamento client/server. A `3.2.0` substituiu a UI/config antiga e cria backup na migração.
Valores exatos do pack não são congelados nesta ficha porque a config física não foi auditada neste ciclo. Qualquer tuning deve ser versionado e benchmarkado com a mesma modlist e hardware.
## 10. API pública
O upstream registra uma refatoração da **EAPI** pública na linha atual. Código próprio que dependa de símbolos da API deve piná-los à versão instalada; não reutilizar exemplos antigos sem conferir assinatura/namespace da 3.2.x.
## 11. Lifecycle
Validar:
- criação de mundo e primeira geração de LOD;
- load/unload de dimensão;
- teleporte e retorno;
- save/restart;
- reconnect multiplayer;
- alteração de distância/config;
- migração de config e database;
- atualização de worldgen em mundo existente;
- exclusão/reconstrução controlada da cache LOD.
## 12. Integrações concretas no pack
DH coexistirá com uma stack extensa de worldgen e render. A regra de integração é separar **world state real** de **representação LOD**. Compatibilidade específica com cada shader/mod visual só deve ser afirmada após reprodução no perfil atual.
Os muitos JARs encontrados dentro de `META-INF/jars/` do artefato — incluindo componentes Fabric API usados pela distribuição multi-loader — são **dependências embarcadas**, não entradas top-level adicionais da modlist.
## 13. Riscos
1. migração/config reset inesperado;
2. database LOD stale ou corrompido;
3. CPU/RAM/I/O excessivos durante geração;
4. competição com pregen/worldgen pesado;
5. render/shader incompatibility;
6. multiplayer alpha / protocol drift;
7. reconnect com cache divergente;
8. confundir erro visual LOD com chunk real;
9. dimension renderer especial produzir clipping/fog incorreto;
10. atualizar DH isoladamente sem validar migrações.
## 14. Matriz de testes
1. Client boot e mundo singleplayer existente.
2. Gerar área nova e comparar LOD distante com chunk real ao aproximar.
3. Reiniciar cliente/mundo e verificar persistência.
4. Viajar Overworld ↔ Nether ↔ End/modded dimensions e retornar.
5. Dedicated server com modo DH configurado; dois clientes.
6. Reconnect e troca de dimensão sem LOD stale.
7. Migração de config 3.1.x/antiga em cópia de teste, verificando `.bak`.
8. Banco LOD existente → atualização → restart.
9. Shader principal do pack + fog/sky/translucência.
10. Benchmark CPU/RAM/disco durante geração e pregen controlada.
11. Recipe registry sem o conflito corrigido pela 3.2.0-b.
**Esta catalogação não afirma que esses testes foram executados.**
## 15. Evidências
- modlist física canônica de 08/09/2026: JAR, mod id, versão e dependências embarcadas;
- release instalada 3.2.0/3.2.0-b para MC 1.21.1 e publicação oficial 3.3.1 de 18/09/2026, ainda não instalada;
- changelogs oficiais da linha 3.1–3.2: config/UI nova, servidor NeoForge, worldgen rewrite, networking, EAPI, per-dimension storage e migrations;
- source oficial do projeto no GitLab.
> **Boundary canônico:** DH é authority da representação LOD e de sua persistência/protocolo. O chunk real e todo gameplay continuam sob Minecraft e os providers de worldgen.

## 16. Atualizações upstream 3.3.0 → 3.3.3 — não instaladas

A autoridade física continua em **Distant Horizons 3.2.0-b**. Para Minecraft 1.21.1, a sequência pública posterior é **3.3.0 (17/09) → 3.3.1 (18/09) → 3.3.2 (21/09) → 3.3.3 (29/09/2026)**.

### 3.3.0 — release estrutural
Deltas relevantes publicados para a linha:
- API pública sobe de **7.0.1 para 7.1.0**;
- adiciona world generator de superfície de alta velocidade nas versões compatíveis;
- adiciona/expande anti-aliasing temporal e mipmaps para LODs;
- melhorias de integração/render com Iris e acesso a texturas/shader data;
- priorização/render task loading e outras otimizações de velocidade;
- melhorias de database e integração com pregeneration/Chunky/C2ME;
- migração de database e shutdown ficam mais paralelos/rápidos;
- trabalho para reduzir uso de disco e várias correções de lifecycle/render/worldgen.

### 3.3.1 — NeoForge config UI
- corrige opções de configuração NeoForge que não usavam os nomes/localizações de idioma adequados.

### 3.3.2 — API/reverse-Z
- API sobe de **7.1.0 para 7.2.0**;
- o upstream marca **SSRD 1.8.6 e anteriores como incompatíveis**: o reverse-Z de DH pode causar renderização incorreta até SSRD atualizar;
- ajustes adicionais de health-check/thread handling do servidor na linha comum.

### 3.3.3 — config reset, Chunky e estabilidade
Deltas comuns aplicáveis à linha 1.21.1:
- DH passa a desabilitar seu worldgen **somente quando o worldgen do Chunky também está ativo**;
- config version sobe **4 → 5**, o que **limpa a configuração** para corrigir valores necessários ausentes, inclusive relacionados a grass textures;
- ignora alguns DB shutdown errors no update propagator e reduz spam de erros de deserialize;
- corrige progresso de worldgen no chat fora de sync;
- corrige referências de servidor singleplayer que não fechavam;
- corrige raro crash OpenGL em hardware sem GL 4.3/vertex attribute binding;
- corrige raro crash do NeoForge lightmap wrapper.

Fixes explicitamente restritos pelo upstream a Minecraft 26.x/Iris 26.x não são promovidos como claims da build 1.21.1.

### Impacto para o pack
Este salto atravessa API, render pipeline, database, worldgen e configuração. A limpeza de config na 3.3.3 é uma migração explícita; presets/opções do pack precisam ser capturados antes da troca. A incompatibilidade SSRD antiga também cruza diretamente o stack gráfico recomendado por outros mods.

### Gate de promoção 3.2.0-b → 3.3.3
- [ ] Backup do banco de LOD e cópia da configuração 3.2.0-b antes de iniciar 3.3.3.
- [ ] Confirmar versão de SSRD; não promover com SSRD ≤1.8.6.
- [ ] Após o reset config v4→v5, reconstituir e revisar opções do pack em vez de reutilizar arquivo stale.
- [ ] LOD database existente migra/abre sem holes ou corrupção; testar também DB novo.
- [ ] Chunky pregen e DH worldgen não desabilitam ambos indevidamente.
- [ ] Shader/Iris stack renderiza LODs, fog/lightmap e near/far transition corretamente.
- [ ] Server restart/shutdown fecha DB e references sem hang.
- [ ] API consumers próprios/externos são revalidados contra API 7.2.0.
- [ ] CPU/RAM/I/O e stutter são comparados com 3.2.0-b em exploração longa.
- [ ] Multiplayer/server networking continua compatível com o modo realmente usado no pack.

Fontes upstream: CurseForge Distant Horizons 1.21.1 — releases 3.3.0, 3.3.1, 3.3.2 e 3.3.3. Nenhum teste acima foi executado nesta atualização documental.

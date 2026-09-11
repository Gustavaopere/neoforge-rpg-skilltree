# Distant Horizons

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817ca2f2d4e18e72ec9c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Distant Horizons
- **Arquivo JAR:** `DistantHorizons-3.2.0-b-1.21.1-fabric-neoforge.jar`
- **Versão 1.21.1:** 3.2.0-b
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Performance, Visual
- **Função:** Sistema de Level of Detail (LOD) que gera, persiste, sincroniza quando aplicável e renderiza terreno simplificado além da distância vanilla, sem substituir o chunk real.
- **Dependências:** Minecraft 1.21.1; artefato multi-loader executado sob NeoForge. A distribuição inclui diversas bibliotecas dentro de META-INF/jars; elas são dependências embarcadas, não mods top-level. Compatibilidade com shaders/renderers deve ser validada por perfil.
- **Sobreposição:** Sobrepõe superfícies de render distante, fog e geração de representação visual; não substitui worldgen real nem Chunky. Coexistência com shaders e outros render mods exige QA, não é conflito automático.
- **Compatibilidade/Riscos:** Riscos em migração/config reset 3.2, database LOD stale/corrompido, pressão de CPU/RAM/I/O, competição com pregen/worldgen pesado, render/shader/fog, protocol drift e multiplayer experimental/incompleto da linha 3.2.
- **Observações:** Distant Horizons 3.2.0-b — infraestrutura de LOD/renderização distante com cache próprio; risco principal concentrado em compatibilidade gráfica, shaders, render pipeline e invalidadores de cache/worldgen. Revalidar após mudanças de shaders, mods de render, drivers, Java/NeoForge ou geração de mundo. Bibliotecas internas/JarJar não são mods top-level da modlist.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `DistantHorizons-3.2.0-b-1.21.1-fabric-neoforge.jar` / runtime 3.2.0-b e os JARs embarcados. Comportamento/migrations: release/changelogs oficiais 3.1–3.2 e source oficial GitLab.
- **Fonte:** https://gitlab.com/distant-horizons-team/distant-horizons
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — LOD/database, worldgen, config migration, server/network alpha, render authority, lifecycle, riscos e matriz de testes catalogados.
- **Histórico da decisão:** Sem decisão curatorial formal. Em 08/09/2026, a ficha foi reconstruída contra o runtime físico 3.2.0-b e a linha oficial 3.2, preservando a distinção entre LOD e chunks reais.

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
A build física é `3.2.0-b`, publicada como **Beta** para 1.21.1. A linha 3.2 introduz uma configuração/UI de configuração refeita; configs antigas são resetadas durante a migração e uma cópia `.bak` é criada. A `3.2.0-b` também corrige um conflito de recipe.

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
- release oficial 3.2.0/3.2.0-b para MC 1.21.1;
- changelogs oficiais da linha 3.1–3.2: config/UI nova, servidor NeoForge, worldgen rewrite, networking, EAPI, per-dimension storage e migrations;
- source oficial do projeto no GitLab.

> **Boundary canônico:** DH é authority da representação LOD e de sua persistência/protocolo. O chunk real e todo gameplay continuam sob Minecraft e os providers de worldgen.

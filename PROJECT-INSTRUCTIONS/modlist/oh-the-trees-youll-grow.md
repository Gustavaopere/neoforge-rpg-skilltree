# Oh The Trees You'll Grow

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e3b6e4f38ccae5ac96
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Oh-The-Trees-Youll-Grow-neoforge-1.21.1-5.3.2.jar`, mod id `ohthetreesyoullgrow`, runtime `5.3.2`; BWG `2.6.0` confirmado fisicamente como consumer
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Trees 5.3.2 e BWG 2.6.0 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Oh The Trees You'll Grow
- **Arquivo JAR:** `Oh-The-Trees-Youll-Grow-neoforge-1.21.1-5.3.2.jar`
- **Versão 1.21.1:** 5.3.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, Worldgen
- **Função:** Library/backend de geração de árvores por structure templates/NBT e blockstate providers, requerida pelo BWG para gerar árvores e variantes.
- **Dependências:** NeoForge 1.21.1. Consumer confirmado: Oh The Biomes We've Gone 2.6.0.
- **Sobreposição:** Não substitui Dynamic Trees; fornece backend/template exigido por BWG. Dynamic Trees - BWG é bridge separado.
- **Compatibilidade/Riscos:** Hard dependency de BWG 2.6.0. Riscos: template/NBT drift, log-filter semantics, structure/bedrock collision, random rotation footprint, provider IDs e interação com Dynamic Trees BWG.
- **Observações:** Runtime 5.3.2, file ID 8096180, Release 16/05/2026. 5.3.2 adiciona log filter behavior PIERCE/PASSTHROUGH/BLOCK. Não confundir com Dynamic Trees.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial 5.3.2 + lineage pública 5.2–5.3 + dependency oficial de BWG.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/oh-the-trees-youll-grow
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Oh The Trees You'll Grow 5.3.2 reconstruído: BWG dependency, Tree From Structure/NBT v2, blockstate providers, log filters PIERCE/PASSTHROUGH/BLOCK, rotation, collision, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-08-26 — classificado como Dependência após confirmação na relação oficial do BWG de que Oh The Trees You'll Grow é Required Dependency.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Oh-The-Trees-Youll-Grow-neoforge-1.21.1-5.3.2.jar`, mod id `ohthetreesyoullgrow`, versão `5.3.2`. É o backend/library de geração de árvores exigido por Oh The Biomes We've Gone 2.6.0. A release 5.3.2 adiciona comportamento configurável de filtros de logs com os modos `PIERCE`, `PASSTHROUGH` e `BLOCK`; não é equivalente a Dynamic Trees.

## 1. Identidade e papel
- **Mod:** Oh The Trees You'll Grow.
- **JAR físico:** `Oh-The-Trees-Youll-Grow-neoforge-1.21.1-5.3.2.jar`.
- **Mod id:** `ohthetreesyoullgrow`.
- **Runtime:** `5.3.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** Corgi_Taco / Potion Studios ecosystem.
- **Ambiente:** Client & Server no projeto.
- **Papel:** library/backend de árvores baseado em structure templates e providers, consumido por mods como BWG.
- **Decisão:** Dependência.

## 2. Consumer confirmado: BWG
Oh The Biomes We've Gone 2.6.0 lista Oh The Trees You'll Grow como **Required Dependency**. Consequência:
- remover Trees isoladamente quebra a relação suportada do BWG;
- atualizar Trees exige regressão do worldgen BWG;
- árvores geradas por BWG podem depender da semântica de templates/providers desta library.

BWG continua authority dos biomas/conteúdo que solicita; Trees é infrastructure do processo de geração.

## 3. Tree from Structure
A linha 5.x oferece sistema de árvores definido a partir de **structure templates/NBT**. Isso permite árvores grandes/complexas sem depender apenas de feature code rígido.

Superfícies de risco:
- template faltante ou incompatível;
- bloco/estado ausente após mudança de mod;
- placement intersectando estruturas ou terreno;
- rotação alterando footprint;
- providers de leaves/logs produzindo composição inválida.

## 4. Linha 5.2 — Tree From Structure NBT v2
A lineage 5.2 introduziu uma segunda versão do formato de Tree From Structure NBT e ampliou suporte a **blockstate providers**, múltiplos leaf targets/providers e configuração mais flexível.

Esse histórico importa porque datapacks/templates criados para formatos antigos podem exigir migração. A ficha não afirma compatibilidade automática entre schemas sem testar os dados reais carregados.

## 5. Filtros de logs — 5.3.2
O delta exato 5.3.2 adiciona capacidade de definir **log filter behavior**. Os comportamentos publicados são:
- `PIERCE` — destrói blocos que correspondem ao filtro;
- `PASSTHROUGH` — a árvore gera, mas pula os blocos filtrados;
- `BLOCK` — a árvore não gera se encontrar um bloco do filtro.

Esses modos mudam diretamente o modo como uma árvore interage com o ambiente existente.

## 6. Segurança contra estruturas/bedrock
A lineage 5.2.1 registra correção para trees atravessando paredes, estruturas e bedrock. Isso transforma colisão com estruturas/blocos protegidos em regression gate real.

Com 5.3.2, o modo de filtro escolhido deve reforçar — não sabotar — essa segurança. Um datapack mal configurado com `PIERCE` pode deliberadamente permitir comportamento destrutivo sobre classes de blocos selecionadas.

## 7. Random rotation
A linha 5.3.0 adicionou toggle para rotação aleatória de árvores. Isso aumenta variação visual, mas também altera footprint de templates assimétricos.

Testes com seed fixa devem observar:
- consistência de colocação;
- colisões com estruturas/terreno;
- folhas/logs cortados;
- diferença entre gerações antes/depois de update/config.

## 8. Blockstate providers e leaves
Providers permitem escolher block states de logs/leaves/targets de forma data-driven. Isso facilita integração com espécies de mods consumidores, mas aumenta risco de tags/IDs ausentes após remoções ou updates.

Não atribuir espécies específicas à library: elas pertencem ao consumer que fornece templates/dados.

## 9. Relação com Dynamic Trees
Oh The Trees You'll Grow **não é Dynamic Trees**. Dynamic Trees altera o modelo de crescimento/árvores vivas; Trees You'll Grow fornece infraestrutura/template usada pelo BWG.

O pack possui Dynamic Trees - BWG como addon separado. Esse bridge pode mudar representação/geração de árvores BWG, mas não deve ser interpretado como substituto automático da hard dependency do BWG sem prova do manifest/runtime.

## 10. Client/server e lifecycle
Worldgen e placement são server-authoritative. O cliente apenas recebe/renderiza os blocos resultantes.

Lifecycle crítico:
- world creation;
- chunk generation;
- sapling/growth path quando o consumer usa a library para crescimento;
- `/reload` de datapacks/templates;
- restart;
- update de BWG/Trees em mundo existente.

## 11. Riscos
1. **Hard dependency:** remover a library quebra BWG.
2. **Template/schema drift:** NBT/data antigos podem deixar de corresponder ao formato esperado.
3. **Filter semantics:** `PIERCE` pode destruir blocos filtrados por design.
4. **Structure collision:** regressão histórica de trees atravessando estruturas/bedrock.
5. **Rotation footprint:** árvores assimétricas podem colidir de forma diferente.
6. **Provider IDs:** blockstate/leaf providers podem apontar a conteúdo removido.
7. **Worldgen churn:** mudanças afetam chunks novos e podem criar fronteiras em mundos existentes.
8. **Dynamic Trees interaction:** bridge deve ser testado separadamente.

## 12. Matriz de testes
- [ ] Dedicated server inicia com Trees 5.3.2 + BWG 2.6.0.
- [ ] Mundo novo gera árvores BWG sem missing template/provider.
- [ ] `BLOCK` impede geração quando encontra bloco filtrado.
- [ ] `PASSTHROUGH` preserva bloco filtrado e gera o restante sem corrupção.
- [ ] `PIERCE` remove apenas alvos configurados e não atravessa proteção indevida.
- [ ] Árvores não atravessam bedrock/estruturas nos cenários de segurança esperados.
- [ ] Random rotation não cria clipping grave em templates representativos.
- [ ] `/reload` de datapack reconstrói definitions sem duplicate/stale state.
- [ ] Dynamic Trees - BWG coexistindo não produz árvore dupla/ausente no mesmo caso.
- [ ] Existing world abre e chunks novos geram sem crash.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física: JAR, mod id/runtime e `ohthetreesyoullgrow.mixins.json`; hash físico `1867792e4e3d9fc6bb7acabb31cbec7a3d49ecad`.
- CurseForge oficial: project 962544, file ID 8096180, Release NeoForge 1.21.1 de 16/05/2026.
- Changelog 5.3.2: log filter behavior `PIERCE`/`PASSTHROUGH`/`BLOCK`.
- Lineage 5.2–5.3: NBT v2, blockstate providers, múltiplos leaf providers, fix de wall/structure/bedrock e random rotation.
- BWG 2.6.0: Required Dependency confirmada.
- **Limite:** templates/IDs específicos do BWG e internals do placement não foram inventados sem dados/JAR específicos.

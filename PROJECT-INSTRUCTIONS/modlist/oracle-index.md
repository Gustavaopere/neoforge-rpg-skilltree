# Oracle Index

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3cc69db9f0db81999c2ad539adb4b8d1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `oracle_index-neoforge-1.3.1.jar`, mod id `oracle_index`, runtime `1.3.1`, mixin `oracle_index.mixins.json`; CommonMark/jsoup/LangChain4j/ONNX/OpenNLP/tokenizers e demais libraries confirmadas como JARs internos em `META-INF/jars/`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Oracle Index 1.3.1 e sua pilha embedded estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Oracle Index
- **Arquivo JAR:** `oracle_index-neoforge-1.3.1.jar`
- **Versão 1.21.1:** 1.3.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Opcional
- **Categoria:** QoL, Biblioteca
- **Função:** Viewer client-side de documentação/wiki in-game compatível com a estrutura de conteúdo do ModdedMC.wiki, com parsing, assets e infraestrutura local de busca/indexação.
- **Dependências:** NeoForge 1.21.1. O JAR embute CommonMark, jsoup, LangChain4j, ONNX Runtime, OpenNLP, tokenizers/embeddings e outras libraries; não catalogar como top-level.
- **Sobreposição:** Sobrepõe UX de consulta com livros/viewers específicos, mas não substitui APIs/documentação exigidas por consumers. Permanece Opcional.
- **Compatibilidade/Riscos:** Viewer client-side com grande footprint embedded. Riscos: CPU/RAM de indexação, cache de imagens, parser/content drift e access-widener packaging. Bibliotecas NLP/embedding internas não provam uso de rede externa.
- **Observações:** Runtime 1.3.1. Release 1.3.1 otimiza imagens e corrige inclusão do NeoForge access widener. JAR físico grande por stack de dependencies embarcadas.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth oficiais da release 1.3.1 + metadata física dos JarJars internos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/oracle-index
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Oracle Index 1.3.1 reconstruído: viewer ModdedMC.wiki-compatible, parsing/assets, grande stack embedded, search/index boundary, image optimization, access-widener fix, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `oracle_index-neoforge-1.3.1.jar`, mod id `oracle_index`, versão `1.3.1`, NeoForge 1.21.1. Oracle Index é um viewer client-side de documentação compatível com a estrutura de conteúdo do ModdedMC.wiki. O JAR físico é grande porque embute uma pilha extensa de parsing/search/ML libraries; esses artefatos pertencem ao host e não são mods top-level.

## 1. Identidade e papel
- **Mod:** Oracle Index.
- **JAR físico:** `oracle_index-neoforge-1.3.1.jar`.
- **Mod id:** `oracle_index`.
- **Runtime:** `1.3.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** rearth.
- **CurseForge project ID:** 1206582.
- **Papel:** visualizador in-game de documentação/wiki.
- **Decisão:** Opcional.

## 2. Modelo de documentação
O projeto foi desenhado para consumir conteúdo com a **mesma estrutura/formatação usada pelo ModdedMC.wiki**, permitindo que autores mantenham uma fonte documental reutilizável para web e in-game.

Isso faz do Oracle Index uma camada de apresentação/descoberta de conhecimento, não authority do gameplay documentado.
Se uma página diz que um item faz algo, o provider do item continua sendo a fonte de verdade funcional.

## 3. Client-side boundary
O projeto é apresentado como viewer client-side. Sua função principal é:
- descobrir conteúdo documental;
- navegar páginas;
- renderizar Markdown/conteúdo associado;
- buscar/organizar informação local carregada pelo viewer.

Não há base nesta auditoria para atribuir mutação de gameplay, world state ou autoridade server-side ao Oracle Index.

## 4. Release 1.3.1
A release exata 1.3.1 para NeoForge 1.21.1 registra:
- **otimização de imagens**;
- correção da inclusão do **NeoForge access widener**.

A otimização de imagens é relevante ao footprint/resource loading; o fix de access widener é regression gate de carregamento da build NeoForge.

## 5. Pilha embedded do JAR
A modlist física mostra sob `META-INF/jars` uma pilha interna extensa, incluindo:
- `api-0.31.1.jar`;
- `commonmark-0.27.1.jar`;
- `commonmark-ext-yaml-front-matter-0.27.1.jar`;
- `exp4j-0.4.8.jar`;
- `jsoup-1.17.2.jar`;
- `langchain4j-1.0.0.jar` e componentes associados;
- embeddings MiniLM;
- `onnxruntime-1.20.0.jar`;
- `opennlp-tools-2.5.4.jar`;
- `tokenizers-0.31.1.jar`.

Esses artefatos explicam parte do tamanho elevado do JAR, mas **não** autorizam afirmar que o mod envia dados a serviços externos ou usa rede/IA remota. A presença de libraries não é evidência de comportamento de rede.

## 6. Parsing e rendering
CommonMark/YAML front matter/jsoup são coerentes com ingestão e apresentação de conteúdo documental estruturado. O viewer precisa lidar com:
- headings/listas/links;
- imagens;
- metadata de página;
- conteúdo ausente ou malformado;
- mudanças de estrutura entre versões da documentação.

A ficha não inventa extensões Markdown específicas além das declaradas/suportadas pelo projeto.

## 7. Search/indexing local
A presença de bibliotecas de tokenização, embeddings, ONNX e NLP indica uma superfície de indexação/busca interna no artefato. Sem documentação/source pinado que detalhe o pipeline da 1.3.1, esta ficha limita a claim a **infraestrutura embarcada disponível ao host**.

Não afirmar upload de documentos, chamada a LLM externa ou telemetria sem evidência concreta.

## 8. Assets e footprint
O JAR é muito maior que um viewer simples por causa de assets e dependências embarcadas. A 1.3.1 otimiza imagens, então resource loading e memória são superfícies práticas de teste:
- tempo de abertura do viewer;
- cache de imagens;
- resource reload;
- memória após navegar por muitas páginas;
- fechamento/reabertura sem stale state.

## 9. Relação com outros sistemas de guia
O pack possui outras formas de documentação, como livros próprios de mods e frameworks específicos. Oracle Index não os substitui automaticamente:
- Modonomicon/Patchouli-like systems podem ser dependencies de conteúdo específico;
- Oracle Index é viewer genérico de documentação compatível com sua estrutura;
- remover um viewer opcional não remove os systems providers, mas reduz uma superfície de consulta.

Por isso `Opcional` permanece adequado.

## 10. Segurança e dados
Nada na evidência atual autoriza tratar Oracle Index como sistema online obrigatório. Ao auditar segurança, separar:
- bibliotecas locais embarcadas;
- links externos clicáveis presentes na documentação;
- qualquer futura feature explicitamente documentada como rede.

Até prova em contrário, não inferir exfiltração ou processamento remoto apenas por existir LangChain4j/ONNX.

## 11. Client lifecycle
Testes relevantes:
- cold launch;
- abrir/fechar viewer repetidamente;
- pesquisa após carregar muitas páginas;
- resource reload;
- mudança de idioma/GUI scale;
- conteúdo com imagem ausente;
- documentação desatualizada ou link inválido.

## 12. Riscos
1. **Large embedded footprint:** memória/startup/resource cost maior que um viewer mínimo.
2. **Index/search cost:** indexação de muita documentação pode impactar CPU/RAM.
3. **Asset cache:** imagens podem permanecer stale ou pressionar memória.
4. **Access widener packaging:** 1.3.1 corrige inclusão NeoForge; manter como regression gate.
5. **Content drift:** wiki in-game pode ficar desatualizada em relação ao mod provider.
6. **Parser edge cases:** Markdown/front matter malformado pode quebrar uma página.
7. **Embedded dependency ambiguity:** não catalogar libraries internas como mods top-level.
8. **Overlapping guide UX:** vários viewers podem aumentar redundância de interface, embora não sejam drop-in replacements.

## 13. Matriz de testes
- [ ] Cliente NeoForge 1.21.1 inicia com Oracle Index 1.3.1 sem access-widener error.
- [ ] Viewer abre e fecha sem crash.
- [ ] Página simples e página extensa renderizam headings, listas, links e imagens corretamente.
- [ ] Imagem ausente falha de forma controlada.
- [ ] Search/index funciona em corpus representativo sem travamento perceptível.
- [ ] Navegar por muitas páginas não produz crescimento de memória claramente não recuperável.
- [ ] Resource reload não deixa imagens/documentos stale.
- [ ] GUI scale/resolução diferentes preservam layout.
- [ ] Links/documentação não são tratados como autoridade de gameplay quando divergentes do provider.
- [ ] Embedded jars resolvem sem duplicate-mod entries.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `oracle_index-neoforge-1.3.1.jar`, mod id/runtime, `oracle_index.mixins.json` e pilha de JarJars internos listada acima.
- CurseForge oficial: project 1206582, release 1.3.1 NeoForge 1.21.1 de 11/07/2026.
- Modrinth/release notes: otimização de imagens e fix da inclusão do access widener NeoForge.
- Descrição oficial: viewer de documentação compatível com o formato/diretórios do ModdedMC.wiki.
- **Limite:** a função exata de cada biblioteca NLP/embedding no pipeline da 1.3.1 e qualquer comportamento de rede não foram inferidos sem source/documentação explícita.

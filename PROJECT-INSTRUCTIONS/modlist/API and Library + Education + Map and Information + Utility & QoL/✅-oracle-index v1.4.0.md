# Oracle Index

## Propriedades do registro

- **Mod:** Oracle Index
- **Arquivo JAR:** oracle_index-neoforge-1.4.0.jar
- **Versão 1.21.1:** 1.4.0
- **Categoria:** QoL, Biblioteca
- **Decisão:** Opcional
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/oracle-index
- **Função:** Viewer client-side de documentação/wiki in-game compatível com a estrutura de conteúdo do [ModdedMC.wiki](http://ModdedMC.wiki), com parsing, assets e infraestrutura local de busca/indexação.
- **Dependências:** NeoForge 1.21.1. O JAR embute CommonMark, jsoup, LangChain4j, ONNX Runtime, OpenNLP, tokenizers/embeddings e outras libraries; não catalogar como top-level.
- **Compatibilidade/Riscos:** Viewer client-side com grande footprint embedded. Riscos: CPU/RAM de indexação, cache de imagens, parser/content drift, rendering de estruturas ricas (tables/code tabs/callouts), layout invalidation e access-widener packaging histórico. Bibliotecas NLP/embedding internas não provam uso de rede externa.
- **Sobreposição:** Sobrepõe UX de consulta com livros/viewers específicos, mas não substitui APIs/documentação exigidas por consumers. Permanece Opcional.
- **Observações:** Runtime 1.4.0. A release 1.4.0 amplia significativamente o renderer documental (tables, block quotes, GitHub-style alerts, hover text, image attributes/captions, enhanced code blocks, code tabs, audio/video links e callout variants), melhora translatability e corrige image alignment, collapsible-callout invalidation e code-tab sizing/styling. O fix de NeoForge access widener da 1.3.1 permanece como regression gate histórico.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + CurseForge oficial da release NeoForge 1.4.0 para Minecraft 1.21.1 + metadata física dos JARs internos.
- **Atualização/Status:** REVALIDADO EM 25/09/2026 — Oracle Index 1.4.0/JAR físico reconfirmado; renderer documental e footprint embedded da release 1.4.0 preservados no dossiê.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #428: JAR `oracle_index-neoforge-1.4.0.jar`, mod id `oracle_index`, runtime `1.4.0`, SHA-1 `90415e944016b45bd3791bfa3705e9e8345e1dde`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `oracle_index-neoforge-1.4.0.jar`, mod id `oracle_index`, versão `1.4.0`, NeoForge 1.21.1. Oracle Index é um viewer client-side de documentação compatível com a estrutura de conteúdo do [ModdedMC.wiki](http://ModdedMC.wiki). O JAR físico é grande porque embute uma pilha extensa de parsing/search/ML libraries; esses artefatos pertencem ao host e não são mods top-level.
</callout>
## 1. Identidade e papel
- **Mod:** Oracle Index.
- **JAR físico:** `oracle_index-neoforge-1.4.0.jar`.
- **Mod id:** `oracle_index`.
- **Runtime:** `1.4.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** rearth.
- **CurseForge project ID:** 1206582.
- **Papel:** visualizador in-game de documentação/wiki.
- **Decisão:** Opcional.
## 2. Modelo de documentação
O projeto foi desenhado para consumir conteúdo com a **mesma estrutura/formatação usada pelo **[**ModdedMC.wiki**](http://ModdedMC.wiki), permitindo que autores mantenham uma fonte documental reutilizável para web e in-game.
Isso faz do Oracle Index uma camada de apresentação/descoberta de conhecimento, não authority do gameplay documentado.
Se uma página diz que um item faz algo, o provider do item continua sendo a fonte de verdade funcional.
## 3. Client-side boundary
O projeto é apresentado como viewer client-side. Sua função principal é:
- descobrir conteúdo documental;
- navegar páginas;
- renderizar Markdown/conteúdo associado;
- buscar/organizar informação local carregada pelo viewer.
Não há base nesta auditoria para atribuir mutação de gameplay, world state ou autoridade server-side ao Oracle Index.
## 4. Release 1.4.0
A release 1.4.0 para NeoForge 1.21.1 amplia materialmente o renderer documental:
- tabelas e block quotes;
- GitHub-style alerts e variantes adicionais de callout;
- hover text;
- image attributes e captions;
- enhanced code blocks e code tabs;
- links de áudio e vídeo;
- maior translatability em controls, callouts, errors, content properties, search e page titles por locale;
- correções de image alignment, collapsible-callout layout invalidation, code-tab sizing/styling e outros problemas menores de rendering.
A release 1.3.1 anterior já havia otimizado imagens e corrigido a inclusão do NeoForge access widener; esses comportamentos permanecem regression gates históricos.
## 5. Pilha embedded do JAR
A modlist física mostra sob `META-INF/jars` uma pilha interna extensa, incluindo:
- `api-0.31.1.jar`;
- `commonmark-0.27.1.jar`;
- `commonmark-ext-gfm-tables-0.27.1.jar`;
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
CommonMark/YAML front matter/jsoup são coerentes com ingestão e apresentação de conteúdo documental estruturado. Na 1.4.0 o viewer documenta suporte adicional a:
- headings, listas, links, tabelas e block quotes;
- GitHub-style alerts e variantes adicionais de callout;
- hover text;
- imagens com attributes e captions;
- enhanced code blocks e code tabs;
- links de áudio e vídeo;
- metadata de página;
- conteúdo ausente ou malformado;
- mudanças de estrutura entre versões da documentação.
Essas superfícies são presentation/parsing; não transferem authority de gameplay para o viewer.
## 7. Search/indexing local
A presença de bibliotecas de tokenização, embeddings, ONNX e NLP indica uma superfície de indexação/busca interna no artefato. Sem documentação/source pinado que detalhe o pipeline da 1.4.0, esta ficha limita a claim a **infraestrutura embarcada disponível ao host**.
Não afirmar upload de documentos, chamada a LLM externa ou telemetria sem evidência concreta.
## 8. Assets e footprint
O JAR é muito maior que um viewer simples por causa de assets e dependências embarcadas. A otimização de imagens introduzida na 1.3.1 permanece relevante, e a 1.4.0 adiciona estruturas visuais mais ricas; portanto resource loading, layout e memória são superfícies práticas de teste:
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
- [ ] Cliente NeoForge 1.21.1 inicia com Oracle Index 1.4.0 sem access-widener error.
- [ ] Viewer abre e fecha sem crash.
- [ ] Página simples e página extensa renderizam headings, listas, links, imagens, tabelas e block quotes corretamente.
- [ ] GitHub-style alerts/callouts, hover text, image captions/attributes, enhanced code blocks e code tabs mantêm layout correto.
- [ ] Links de áudio/vídeo falham de forma controlada quando indisponíveis e não quebram a página.
- [ ] Imagem ausente falha de forma controlada.
- [ ] Search/index funciona em corpus representativo sem travamento perceptível.
- [ ] Navegar por muitas páginas não produz crescimento de memória claramente não recuperável.
- [ ] Resource reload não deixa imagens/documentos stale.
- [ ] GUI scale/resolução diferentes preservam layout.
- [ ] Links/documentação não são tratados como autoridade de gameplay quando divergentes do provider.
- [ ] Embedded jars resolvem sem duplicate-mod entries.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 14. Evidências e limites
- Modlist física de 16/09/2026: `oracle_index-neoforge-1.4.0.jar`, mod id/runtime 1.4.0, `oracle_index.mixins.json` e pilha de JarJars internos listada acima.
- CurseForge oficial: project 1206582, release 1.4.0 NeoForge 1.21.1 de 14/09/2026.
- Changelog oficial 1.4.0: tables, block quotes, GitHub-style alerts, hover text, image attributes/captions, enhanced code blocks, code tabs, audio/video links, callout variants, translatability e correções de layout/render; a 1.3.1 anterior documenta otimização de imagens e fix da inclusão do access widener NeoForge.
- Descrição oficial: viewer de documentação compatível com o formato/diretórios do [ModdedMC.wiki](http://ModdedMC.wiki).
- **Limite:** a função exata de cada biblioteca NLP/embedding no pipeline da 1.4.0 e qualquer comportamento de rede não foram inferidos sem source/documentação explícita.

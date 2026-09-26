# Modonomicon

## Propriedades do registro

- **Mod:** Modonomicon
- **Arquivo JAR:** modonomicon-1.21.1-neoforge-1.120.4.jar
- **Versão 1.21.1:** 1.120.4
- **Categoria:** Biblioteca, QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/modonomicon | https://github.com/klikli-dev/modonomicon
- **Função:** Framework data-driven para livros/guias in-game com categories, entries, pages, condições/progressão e preview de multiblocos.
- **Dependências:** NeoForge 1.21.1. Necessidade depende dos consumers que registram livros. Nenhum consumer inequívoco foi comprovado na modlist física nesta passagem; Occultism não está presente. CommonMark 0.29.0 e extensões são JarJars internos do host.
- **Compatibilidade/Riscos:** Framework data-driven. Riscos: schema/ID drift, unlock/first-read state, reload, multiblock rendering e dependency inference. Branch de release 1.120.4 possui [gradle.properties](http://gradle.properties) ainda em 1.110.0; versão exata vem do JAR/publicação, não desse campo isolado.
- **Sobreposição:** Coexiste com Patchouli/GuideME; não são substituições drop-in porque consumers dependem de APIs/formats específicos.
- **Observações:** Runtime 1.120.4. Branch `release/v1.21.1-1.120.4`; commit auditado `9ec9fe54...` corrige batching do multiblock preview. O [gradle.properties](http://gradle.properties) do ref acessível ainda diz 1.110.0, divergência explicitamente preservada.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + publicação oficial 1.120.4 + source/docs oficial klikli-dev/modonomicon; source metadata divergente tratado fail-closed.
- **Atualização/Status:** REVALIDADO EM 13/09/2026 — release 1.21.1 e dossiê preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #404: JAR `modonomicon-1.21.1-neoforge-1.120.4.jar`, mod id `modonomicon`, runtime `1.120.4`, SHA-1 `1377fec77ba0cb3e4adda0da9ce2f14847e21e40`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `modonomicon-1.21.1-neoforge-1.120.4.jar`, mod id `modonomicon`, versão `1.120.4`. O upstream possui branch de release `release/v1.21.1-1.120.4`; o workflow dessa branch aponta ao commit `9ec9fe54eb422ff519a9a4af961faf8efc11f1a3`, que corrige batching do preview de multiblock em 1.21.1. O `gradle.properties` acessível nesse ref ainda mostra `mod_version=1.110.0`; portanto ele **não** é usado como prova isolada de versionamento. A versão exata é sustentada pelo JAR físico/publicação, preservando a divergência de source metadata.
</callout>
## 1. Identidade e papel
- **Mod:** Modonomicon.
- **JAR físico:** `modonomicon-1.21.1-neoforge-1.120.4.jar`.
- **Mod id:** `modonomicon`.
- **Runtime:** `1.120.4`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Upstream:** klikli-dev/modonomicon.
- **Papel:** framework data-driven para livros/guias in-game, documentação de progressão, páginas interativas e visualização de multiblocos.
Modonomicon não é um “livro de conteúdo” por si só. Ele fornece a infraestrutura para outros mods/datapacks definirem livros e sua progressão.
## 2. Modelo de dados: Book → Category → Entry → Page
A documentação 1.21.1 organiza livros em uma hierarquia:
- **Book** — container principal;
- **Categories** — agrupam entradas;
- **Entries** — unidades de conteúdo/progressão;
- **Pages** — conteúdo efetivamente apresentado.
As categorias podem assumir apresentação de nós/progressão ou índice. Entradas podem ter relações parentais e condições de desbloqueio, permitindo que o guia represente uma árvore de progresso em vez de apenas texto estático.
## 3. Datapack e namespace
Os livros são data-driven e vivem sob o namespace do provider, na estrutura conceitual:
`data/<mod_id>/modonomicon/books/<book_id>/`.
Consequências para pack development:
- IDs de book/category/entry/page devem ser tratados como contrato de dados;
- renomear/remover IDs pode quebrar links ou progressão;
- datapack reload pode alterar a disponibilidade/estrutura do livro;
- integrações próprias devem usar os formatos reais da versão instalada.
## 4. Entradas, desbloqueios e progressão
Entradas podem representar relações de progressão e conter condições/requisitos. A documentação também suporta comportamento como esconder conteúdo bloqueado e executar recompensas/comandos na primeira leitura, conforme definido pelo livro/provider.
**Ownership:** o mod que fornece o livro continua authority do significado da progressão. Modonomicon fornece engine, render e infraestrutura. Não usar “entrada desbloqueada” como prova de uma conquista externa sem confirmar como aquele livro define a condição.
## 5. Páginas e conteúdo
O framework suporta páginas de documentação com diferentes tipos de conteúdo, incluindo texto, itens, imagens e recipes, entre outros tipos expostos pela versão/API.
Isso o torna uma superfície de integração para mods que precisam explicar sistemas complexos sem hardcodar uma GUI monolítica.
## 6. Multiblock preview
Modonomicon possui renderer de preview de multiblocos. O commit `9ec9fe54...` na branch de release 1.120.4 corrige batching incorreto em 1.21.1: antes de inicializar/reutilizar buffers de preview, o renderer passa a encerrar o batch principal para evitar estado inconsistente.
Esse commit fornece evidência direta de que multiblock rendering é uma superfície sensível da release e deve ser testada com o stack gráfico atual.
## 7. Mixins e bibliotecas embarcadas
A modlist física registra `modonomicon.mixins.json`.
O JAR também embute sob `META-INF/jarjar`:
- `commonmark-0.29.0.jar`;
- `commonmark-ext-gfm-strikethrough-0.29.0.jar`;
- `commonmark-ext-ins-0.29.0.jar`.
Essas bibliotecas pertencem ao host Modonomicon. Pelo protocolo da modlist, não devem gerar entradas top-level independentes.
## 8. Client/server
Há componentes em ambos os lados do sistema:
- dados/progressão/condições precisam respeitar authority de servidor quando afetam estado real;
- rendering de livro, páginas e multiblock preview é client-facing;
- datapack/resource loading atravessa a fronteira entre conteúdo de dados e apresentação.
Não presumir que toda página é apenas client-side nem que abrir uma página autoriza mutação server-side.
## 9. Consumidores no pack
Nesta passagem, a modlist física foi procurada por `occultism` e **não contém Occultism**. Também não foi estabelecido outro consumidor inequívoco de Modonomicon apenas pela presença dos JARs atuais.
Portanto a decisão permanece **Sem decisão**: a presença do framework é confirmada, mas a necessidade efetiva deve ser decidida após mapear consumidores por manifests/source, não por memória de quais mods costumam usá-lo em outros packs.
## 10. Relação com Patchouli/GuideME
Patchouli e outros frameworks podem coexistir. Eles não são substituições drop-in:
- cada consumer compila/declara contra uma API/formato específico;
- livros Modonomicon não viram livros Patchouli automaticamente;
- remover um framework exige provar que nenhum consumer instalado depende dele.
## 11. Riscos
1. **Data schema drift:** mudanças de JSON/codec entre versões podem quebrar livros.
2. **ID/link drift:** rename de entry/category/page pode quebrar links/progressão.
3. **Unlock state:** condições e primeira leitura precisam ser validadas em multiplayer/restart.
4. **Datapack reload:** livros devem recarregar sem duplicação, stale cache ou perda de estado.
5. **Multiblock rendering:** 1.120.4 inclui fix específico de batching; stack gráfico deve ser regressado.
6. **Embedded CommonMark:** não catalogar/remover como top-level.
7. **Source metadata mismatch:** branch/ref de release contém `gradle.properties` com número antigo; não usar esse arquivo isoladamente para corrigir o runtime 1.120.4.
8. **Dependency inference:** não declarar Modonomicon dispensável até os consumidores serem rastreados por metadata/source.
## 12. Matriz de testes
- [ ] Cliente/servidor iniciam com Modonomicon 1.120.4.
- [ ] Um livro real fornecido por consumer, se existir, abre sem missing type/codec.
- [ ] Categories/entries/pages navegam e links resolvem IDs corretos.
- [ ] Condição de entrada bloqueada/desbloqueada sincroniza entre server e client.
- [ ] Primeiro acesso/recompensa, quando usado, não executa duas vezes em reconnect.
- [ ] `/reload`/datapack reload reconstrói livro sem duplicate/stale entries.
- [ ] Resource reload reconstrói imagens/fonts/resources sem crash.
- [ ] Preview de multiblock abre repetidamente sem buffer/render corruption.
- [ ] Dois clientes visualizando o mesmo conteúdo não alteram estado um do outro indevidamente.
- [ ] Reinício completo conserva progressão do consumer conforme contrato real.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 13. Evidências e limites
- Modlist física: JAR, mod id, runtime, mixin e três JarJars CommonMark.
- Source oficial: `klikli-dev/modonomicon`.
- Branch de release: `release/v1.21.1-1.120.4`.
- Commit auditado: `9ec9fe54eb422ff519a9a4af961faf8efc11f1a3`, fix de `MultiblockPreviewRenderer` em 29/07/2026.
- Documentação oficial 1.21.1: estrutura de livros data-driven e progress visualization.
- **Limite:** `gradle.properties` no ref acessível reporta versão antiga 1.110.0; não há afirmação de equivalência byte-a-byte baseada nesse campo. A authority de versão é o JAR físico/publicação 1.120.4.

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
- **Compatibilidade/Riscos:** Framework data-driven. Riscos: schema/ID drift, unlock/first-read/save state, reload, clientbound registry access e multiblock rendering. Runtime 1.120.4; upstream 1.120.5 corrige offset de smithing recipe page, 1.120.6 corrige registry access em clientbound packets no Forge e 1.120.7 corrige save-state errors para books unknown/unbuilt.
- **Sobreposição:** Coexiste com Patchouli/GuideME; não são substituições drop-in porque consumers dependem de APIs/formats específicos.
- **Observações:** Runtime físico permanece 1.120.4. Releases posteriores oficiais para 1.21.1: 1.120.5 (22/09), 1.120.6 (24/09) e 1.120.7 (25/09/2026). Os três deltas são fixes específicos e foram incorporados abaixo.
- **Procedência:** modlist física atual + publicação/source oficial `klikli-dev/modonomicon`; releases GitHub `release/v1.21.1-1.120.5`, `1.120.6` e `1.120.7` foram revalidadas em 01/10/2026. Runtime físico permanece 1.120.4.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Modonomicon físico permanece 1.120.4. A cadeia 1.120.5→1.120.6→1.120.7 foi comparada em ordem e os fixes foram incorporados abaixo.
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

## 14. Atualizações upstream 1.120.5 → 1.120.7 — não instaladas

A autoridade física continua em **Modonomicon 1.120.4**.

### 1.120.5 — smithing recipe page
- corrige recipe pages de **smithing** para usarem o crafting Y offset correto (`418e637`, PR #390).

### 1.120.6 — clientbound registry access
- no Forge, clientbound packets passam a usar **server registry access** (`65f8247`).

Este delta toca diretamente a fronteira client/server e registry serialization; é relevante para books/pages que enviem dados referenciando registries modded.

### 1.120.7 — save state
- corrige **save state errors para books unknown/unbuilt** (`5cbce6b`, PR #368).

### Gate de promoção 1.120.4 → 1.120.7
- [ ] Smithing recipe pages alinham/renderizam corretamente em múltiplas resoluções/GUI scale.
- [ ] Dedicated server envia book/page data sem registry access/codec errors.
- [ ] Cliente entrando após o servidor já estar ativo recebe registries/book state correto.
- [ ] Unknown/unbuilt books não geram save-state exceptions.
- [ ] Unlock/first-read/progress state persiste após relog e restart.
- [ ] Datapack `/reload` não duplica entries nem deixa IDs/book references stale.
- [ ] Multiblock preview mantém o batching fix já documentado na 1.120.4.

Fontes upstream: releases oficiais `klikli-dev/modonomicon` 1.120.5, 1.120.6 e 1.120.7. Nenhum teste acima foi executado nesta atualização documental.

## Auditoria parcial — 20/11/2026 — publicações posteriores à 1.120.7

A autoridade física #404 continua `modonomicon-1.21.1-neoforge-1.120.4.jar`. As versões 1.120.5/.6/.7 permanecem documentadas na seção 14. As seguintes publicações do projeto CurseForge 538392 foram confirmadas individualmente para NeoForge 1.21.1; categorias oficiais Adventure and RPG + API and Library + Map and Information mantidas.

| Publicação | Delta documentado |
|---|---|
| [1.122.0 — 29/10/2026](https://www.curseforge.com/minecraft/mc-mods/modonomicon/files/9364289) | Introduz condições de carregamento para a linha 1.21.1 (commit `89e0eb7`, PR #417). Relevante para disponibilidade condicional de conteúdo data-driven; não inventar schema, escopo ou sintaxe sem documentação exata. |
| [1.123.0 — 31/10/2026](https://www.curseforge.com/minecraft/mc-mods/modonomicon/files/9384356) | Atualiza versões dos loaders (commit `7f9881b`); demais notas referem-se a cache de datagen, arquivos gerados e CI. Confirmar mínimos reais de runtime antes de promover. |
| [1.123.1 — 16/11/2026](https://www.curseforge.com/minecraft/mc-mods/modonomicon/files/9646793) | Melhora o tratamento de erros na construção de livros (commit `1a662c4`); não atribuir comportamento específico de recovery não publicado. |

**Pendência documental:** a listagem filtrada CurseForge e a API Modrinth não retornaram o inventário integral; não foi possível excluir publicações intermediárias entre 1.120.7 e 1.122.0. As consultas aos nomes de tags hipotéticos também retornaram 404, o que não prova inexistência de publicações. Manter item pendente. Regressões: load conditions, books unknown/unbuilt, logs de falha no build, reload, estado de progressão e consumidores reais; confirmar loader/dependências. Nenhum teste ou promoção física.

## Fechamento documental — 21/11/2026 — versões 1.120.8 e 1.121.0 conciliadas

**Instalação física mantida:** `modonomicon-1.21.1-neoforge-1.120.4.jar`, posição #404 no snapshot; versão `1.120.4` permanece no nome do dossier e no índice. A revisão das [GitHub Releases oficiais da linha 1.21.1](https://github.com/klikli-dev/modonomicon/releases) identificou as publicações sequenciais **1.120.5 → 1.120.6 → 1.120.7 → 1.120.8 → 1.121.0 → 1.122.0 → 1.123.0 → 1.123.1** posteriores à versão física. As .5/.6/.7 já estão na seção 14; 1.122.0, 1.123.0 e 1.123.1 já estão individualizadas na auditoria parcial de 20/11. A lacuna intermediária identificada naquele relatório corresponde às duas releases a seguir:

### [1.120.8 — 27/10/2026, release GitHub da linha 1.21.1](https://github.com/klikli-dev/modonomicon/releases/tag/release/v1.21.1-1.120.8)

A correção oficial [`d8602c7`](https://github.com/klikli-dev/modonomicon/commit/d8602c755c93ddc342c369cc7cc8137987aadb96) impede que o **preview de multiblocos altere a seed do random do nível cliente** (*do not reseed client level random in multiblock preview*). É relevante para determinismo de apresentações e para evitar efeitos colaterais do preview em outras operações client-side. Não interpretar como alteração de seed de geração do mundo no servidor sem evidência adicional. Testar abrir/fechar diversos previews de multibloco, renderização e outros consumidores de random do cliente.

### [1.121.0 — 28/10/2026, release GitHub da linha 1.21.1](https://github.com/klikli-dev/modonomicon/releases/tag/release/v1.21.1-1.121.0)

Changelog oficial registra:
- **Navegação:** as páginas do livro passam a poder ser viradas com as teclas de seta ([`6c61254`](https://github.com/klikli-dev/modonomicon/commit/6c612544d96eb1fffe91b5da47c713a52c1c54ad)).
- **Interface:** corrige entradas fantasma nos backgrounds de categorias do modo *index* ([`e253be7`](https://github.com/klikli-dev/modonomicon/commit/e253be7085be8adc4183b333ad4df5132864eba2)).
- **Bookmarks:** corrige o botão de favorito que funcionava apenas uma vez ([`4013470`](https://github.com/klikli-dev/modonomicon/commit/4013470dd1b7c6b0d4321e40390211e18f665092)). Testar múltiplas marcações e remoções sucessivas, persistência e navegação entre entradas.

**Conciliação da cadeia até 1.123.1:** as GitHub Releases oficiais distinguem cada versão da linha Minecraft 1.21.1 da sequência acima, sem evidência de outra GitHub Release 1.21.1 intermediária no intervalo auditado. O [artefato CurseForge 1.123.1 para NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/modonomicon/files/9646793) confirma o alvo publicado em 16/11/2026; diferenças entre outros loaders/versões 26.x **não** foram transferidas para este dossier. As mudanças adicionais 1.122.0–1.123.1 permanecem descritas na seção anterior.

**Resultado:** auditoria **documental das publicações upstream identificadas até 1.123.1 concluída**. Não implica equivalência binária inspecionada de cada publicação nem homologação do upgrade. Seguem pendentes os testes de consumidores, datapacks, sincronização, bookmarks, preview e mundo salvo. **Nenhuma instalação ou teste de jogo foi realizado**, sem alterar JAR nem certificar versão-alvo como instalada.

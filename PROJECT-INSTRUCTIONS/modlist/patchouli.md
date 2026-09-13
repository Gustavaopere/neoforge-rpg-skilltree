# Patchouli

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81df9253d059128095af
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Patchouli-1.21.1-93-NEOFORGE.jar`, mod id `patchouli`, runtime `1.21.1-93-NEOFORGE`, mixin `patchouli_xplat.mixins.json`; consumers físicos Goety `3.1.4` e Apotheosis `8.8.0` confirmados
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Patchouli 93, Goety 3.1.4 e Apotheosis 8.8.0 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Patchouli
- **Arquivo JAR:** `Patchouli-1.21.1-93-NEOFORGE.jar`
- **Versão 1.21.1:** 1.21.1-93-NEOFORGE
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, QoL
- **Função:** Framework data-driven para livros/guias in-game usado por mods para documentação e progressão.
- **Dependências:** NeoForge 1.21.1. Consumers físicos confirmados: Goety 3.1.4 e Apotheosis 8.8.0, ambos ligados oficialmente a Patchouli; em Apotheosis o Chronicle of Shadows depende da integração documental.
- **Sobreposição:** Coexiste com Modonomicon/Oracle Index, mas consumers compilam/estruturam conteúdo contra frameworks específicos; não há redundância técnica automática.
- **Compatibilidade/Riscos:** Dependency de documentação/progressão. Riscos: consumer book schema drift (`use_resource_pack=false` legacy), broken IDs/recipes, data-component item icons, load-order/custom book item e reload consistency. Não confundir book quebrado com library inválida.
- **Observações:** Runtime 1.21.1-93-NEOFORGE, file ID 7730942, Release 08/03/2026. Release 93 corrige superfície de ItemStack icons com datapack registries/data components. Modonomicon/Oracle não são substitutos drop-in.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Patchouli 93 + relações oficiais atuais de Goety 3.1.4 e Apotheosis 8.8.0 + evidência de runtime previamente catalogada para books legacy.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/patchouli
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Patchouli 93 reconstruído e reclassificado como Dependência: books data-driven, consumers Goety/Apotheosis, release 93, legacy-book boundary, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-09-10 — reclassificado de Sem decisão para Dependência após confirmação física e oficial de consumers atuais: Goety 3.1.4 e Apotheosis 8.8.0 utilizam Patchouli; remover a library isoladamente quebraria ou degradaria conteúdo documental dos consumers.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Patchouli-1.21.1-93-NEOFORGE.jar`, mod id `patchouli`, versão `1.21.1-93-NEOFORGE`, NeoForge 1.21.1. Patchouli é um framework data-driven de livros/guias in-game. Nesta modlist ele não é apenas “biblioteca disponível”: **Goety 3.1.4 e Apotheosis 8.8.0 são consumers físicos atuais ligados oficialmente a Patchouli**, portanto a decisão correta passa a ser **Dependência**.

## 1. Identidade e papel
- **Mod:** Patchouli.
- **JAR físico:** `Patchouli-1.21.1-93-NEOFORGE.jar`.
- **Mod id:** `patchouli`.
- **Runtime:** `1.21.1-93-NEOFORGE`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** Vazkii / Patchouli.
- **Papel:** framework data-driven para autores criarem livros de documentação/progressão in-game sem implementar toda a UI em código.
- **Decisão:** Dependência.

## 2. Consumers confirmados no pack
A modlist física contém:
- `Apotheosis-1.21.1-8.8.0.jar`;
- `goety-3.1.4.jar`.

As relações oficiais atuais de ambos os projetos listam **Patchouli**. Em Apotheosis, a própria documentação explica que Patchouli é necessário para o Chronicle of Shadows/experiência documental; em Goety ele aparece como Required Dependency na relação pública.

Consequência operacional: remover Patchouli isoladamente quebra ou degrada conteúdo/documentação de consumers presentes. Não é uma library órfã.

## 3. Modelo data-driven de books
Patchouli permite definir books e conteúdo por dados/resources. A estrutura conceitual usa books compostos por categorias, entries e pages, com diferentes tipos de página e conteúdo fornecido pelo mod consumidor.

Ownership:
- Patchouli fornece engine/UI/rendering/data contracts;
- o consumer é authority do texto, recipes, progressão e conteúdo descrito no livro;
- um erro factual no livro pertence ao conteúdo do consumer, não ao framework por si só.

## 4. Client/server e sincronização
A apresentação do livro é fortemente client-facing, mas desbloqueios/progressão/regras ligadas ao gameplay do consumer podem depender de estado do servidor.

Não usar abertura de página como prova de state funcional. Recipes, advancements, research e unlocks reais permanecem sob authority do provider que os integra.

## 5. Data/resource lifecycle
Como framework data-driven, Patchouli é sensível a:
- resource/data reload;
- IDs de books/categories/entries/pages;
- templates/macros e assets referenciados pelos consumers;
- recipes/items desaparecidos após update;
- mudança de schema entre versões.

`/reload` deve reconstruir conteúdo sem duplicar books, perder páginas ou deixar links quebrados.

## 6. Release 93
A release `1.21.1-93-NEOFORGE` é a build física atual para NeoForge 1.21.1.

A lineage recente registra correção ligada a **ItemStack icons usando datapack registries em data components**. Isso é um regression gate para páginas que renderizam itens/componentes vindos de registries data-driven.

A release anterior 92 também tratou problemas de build/classloading, inclusive caso em que Patchouli podia carregar antes de mod que fornecia custom book item; essas notas históricas reforçam a importância da ordem/lifecycle, mas não são rotuladas como delta exclusivo da 93.

## 7. Consumers e books quebrados — boundary de responsabilidade
A evidência de runtime já registrada no catálogo apontou books de alguns consumers usando `use_resource_pack=false`, formato removido após 1.20, e sendo ignorados.

Esse tipo de erro deve ser atribuído ao **conteúdo do book consumidor desatualizado**, não à validade do Patchouli 93. O framework pode estar carregando corretamente enquanto um book específico não satisfaz o schema atual.

Ao corrigir:
- atualizar o book/datapack do consumer;
- não fazer downgrade automático de Patchouli;
- validar o consumer em separado.

## 8. Apotheosis
Apotheosis 8.8.0 está presente. Sua documentação informa que Patchouli sustenta o **Chronicle of Shadows**; a relação CurseForge atual marca Patchouli como Required Dependency.

Isso é suficiente para classificar Patchouli como dependency operacional do pack mesmo que outras partes de Apotheosis possam tecnicamente carregar sem o Chronicle.

## 9. Goety
Goety 3.1.4 está presente e sua relação oficial atual lista Patchouli como Required Dependency, junto de Curios.

Qualquer livro/ritual/documentação Goety que use Patchouli continua pertencendo ao Goety no conteúdo; Patchouli fornece a engine documental.

## 10. Coexistência com Modonomicon e Oracle Index
O pack também possui outros frameworks/viewers. Isso não torna Patchouli redundante:
- Modonomicon books dependem da API de Modonomicon;
- Oracle Index é viewer genérico de documentação compatível com seu próprio formato;
- Patchouli consumers esperam seus contracts/IDs/page types.

Frameworks de documentação não são drop-in replacements.

## 11. Performance e assets
Books podem conter muitas pages, recipes, item renderers, images e links. Em grandes modpacks, testar:
- abertura inicial de books pesados;
- navegação rápida;
- search/index quando usado;
- render de item icons complexos;
- resource reload;
- memória após abrir/fechar books repetidamente.

## 12. Riscos
1. **Consumer schema drift:** book antigo pode usar propriedades removidas.
2. **Broken references:** IDs/recipes/items removidos deixam páginas inválidas.
3. **Data-component item icons:** regression gate da release 93.
4. **Load order/custom book item:** histórico recente de classloading/lifecycle.
5. **Misattribution:** book quebrado não significa Patchouli quebrado.
6. **Framework substitution:** remover por existir Modonomicon/Oracle quebra consumers específicos.
7. **Reload consistency:** content packs precisam reconstruir sem stale state.
8. **Removal impact:** Goety/Apotheosis atuais dependem funcionalmente do framework.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Patchouli 93 + consumers atuais.
- [ ] Chronicle of Shadows/Apotheosis abre e navega sem missing page/item icon.
- [ ] Conteúdo Patchouli do Goety abre sem missing registry/type.
- [ ] Item icons com data components/datapack registries renderizam corretamente.
- [ ] `/reload` reconstrói books sem duplicação ou páginas stale.
- [ ] Links internos/category navigation permanecem válidos.
- [ ] Book com recipe/item removido falha de forma controlada e identificável ao consumer.
- [ ] Books legacy com `use_resource_pack=false` são tratados como problema de conteúdo, sem downgrade automático do framework.
- [ ] Restart/reconnect preserva qualquer unlock/progress state pertencente aos consumers.
- [ ] Modonomicon/Oracle coexistem sem conflito de registry/UI global.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `Patchouli-1.21.1-93-NEOFORGE.jar`, mod id/runtime e `patchouli_xplat.mixins.json`.
- CurseForge oficial: file ID 7730942, Release NeoForge 1.21.1 de 08/03/2026.
- Relações atuais: Goety 3.1.4 e Apotheosis 8.8.0 presentes no pack e ligados oficialmente a Patchouli.
- Documentação Patchouli: engine data-driven de books/guias; release 93 com fix de ItemStack icons/data components.
- Evidência de catálogo/runtime anterior: consumers com formato legacy de book são problema do conteúdo consumidor.
- **Limite:** esta auditoria não reexecutou logs locais nem enumerou todos os books/entries do pack; cada consumer precisa de smoke próprio.

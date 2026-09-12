# Guide-API-VP

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db81a1a943f698008da198  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Guide-API-VP
- **Arquivo JAR:** `Guide-API-VP-1.21.1-2.3.0.jar`
- **Versão 1.21.1:** `2.3.0`
- **Categoria:** Biblioteca; QoL
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/guide-api-village-and-pillage
- **Função:** API/fork moderno da Guide-API para mods criarem documentação e livros in-game majoritariamente em código, com registro, paginação/localização, page types e helpers reutilizáveis.
- **Dependências:** NeoForge 1.21.1. Vampirism é consumer oficial listado pelo projeto e está fisicamente presente no pack. Para consumers, Guide-API pode ser integração opcional em runtime; a decisão `Dependência` do catálogo é preservada sem convertê-la em hard dependency universal.
- **Compatibilidade/Riscos:** Library de documentação, não hard dependency universal. O próprio upstream declara que consumers podem funcionar sem Guide-API, apenas sem o livro. Riscos: consumer API drift, registro de livro/página em fase errada, conteúdo/config desatualizado e remoção indevida se o pack depende operacionalmente do guia.
- **Sobreposição:** Pode coexistir com Patchouli/Oracle Index e outros sistemas de documentação; APIs e consumers são distintos. Não é substituto universal nem provider de gameplay.
- **Observações:** JAR físico `Guide-API-VP-1.21.1-2.3.0.jar`, mod id `guideapi_vp`, runtime 2.3.0. Upstream lista Vampirism como consumer e declara ausência da library como tolerável quando a integração é opcional: o livro some, o restante do mod continua.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Guide-API-VP 2.3.0 + descrição oficial do modelo de livros e optional dependency.
- **Histórico da decisão:**
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — metadata Verificada com corpo vazio corrigida; book API, optional-dependency semantics, page types, localization/config binding e lifecycle catalogados para 2.3.0.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 📖 **ESCOPO CANÔNICO.** Runtime físico: `Guide-API-VP-1.21.1-2.3.0.jar`, mod id `guideapi_vp`, versão `2.3.0`. Guide-API-VP fornece infraestrutura para **documentação/livros in-game**. Não adiciona progressão ou conteúdo de gameplay próprio.

## 1. Arquitetura do guia
O projeto é um fork moderno da Guide-API e permite que mods construam seus guide books principalmente em código. A própria API cuida de registration e de várias superfícies de apresentação do livro.
Isso contrasta com sistemas predominantemente JSON-based; não torna uma abordagem automaticamente substituível pela outra.

## 2. Conteúdo ligado ao consumer
Books podem incluir ou alterar conteúdo conforme configuração do mod consumidor, referenciar keybindings e constantes internas e usar helpers para gerar partes do guia. Portanto o texto exibido pode refletir state/config do consumer e não deve ser duplicado manualmente sem necessidade.

## 3. Paginação e localização
A API documenta wrapping/pagination automáticos para ajudar localized strings a caberem nas páginas. Integrações próprias devem evitar hardcode de paginação por idioma quando a API já controla essa superfície.

## 4. Page types e extensão
Há page types prontos para texto, conteúdo focado em item/bloco, recipes e imagens, além da possibilidade de custom page/recipe types. A existência de uma página custom não transfere gameplay authority para Guide-API; o consumer continua owner do sistema descrito.

## 5. Optional-dependency semantics
O upstream declara explicitamente que **não há hard dependency universal para consumers**: se Guide-API não estiver presente, o livro pode não existir, enquanto o restante do mod continua funcionando quando a integração foi desenhada como opcional.
Vampirism é listado oficialmente como mod consumidor e está fisicamente presente neste pack. A decisão `Dependência` do catálogo é preservada como escolha operacional/documental, não como alegação de que todo consumer falha no boot sem a library.

## 6. Authority e side
Guide-API controla book/page infrastructure; Minecraft/mod consumer controla gameplay state. Abrir página, navegar ou clicar link não deve alterar progressão server-side salvo ação explícita do consumer.

## 7. Lifecycle
Validar book registration, resource/language reload, alteração de config que mude conteúdo, mudança de keybind, client reconnect e update do consumer/library. Não manter page cache com dados antigos após reload quando o consumer espera refresh.

## 8. Riscos
1. Consumer compilado contra API diferente.
2. Livro registrado duas vezes ou ausente após reload.
3. Conteúdo exibido divergir da config/runtime real.
4. Custom page referencing classes client-only em path comum.
5. Remover Guide-API e perder documentação necessária para UX, mesmo se o consumer ainda carregar.

## 9. Boundary para quests/perks
Ler/abrir guide book não deve conceder Mastery automaticamente. Se uma quest exigir leitura específica, usar milestone explícito/deduplicado e não polling contínuo da UI.

## 10. Matriz de testes
- [ ] Cliente inicia com Guide-API-VP 2.3.0 e consumers atuais.
- [ ] Livro de consumer registrado abre sem missing page/type.
- [ ] Mudança de idioma não causa overflow/erro de paginação.
- [ ] Conteúdo condicionado por config acompanha valor real.
- [ ] Custom recipe/page links resolvem corretamente.
- [ ] Dedicated server não carrega classes gráficas por integração própria.
- [ ] Remoção em ambiente de teste confirma quais consumers perdem apenas documentação versus hard-fail.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limitação
- Modlist física: `Guide-API-VP-1.21.1-2.3.0.jar`; Vampirism 1.10.13 também presente.
- CurseForge oficial: Release 2.3.0 NeoForge 1.21.1, modelo code-based, ready-made page types, custom pages e optional-dependency semantics.
- Não foi realizado dependency graph completo de todos os consumers do pack; não generalizar Vampirism para mods não confirmados.

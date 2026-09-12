# Architectury API

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81af83dfd39b42f36694
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Architectury API
- **Arquivo JAR:** `architectury-13.0.11-neoforge.jar`
- **Versão 1.21.1:** 13.0.11
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** API intermediária multiplataforma usada por mods para abstrair eventos, networking, registries, loader/platform calls e implementações específicas via `@ExpectPlatform`. Não adiciona sistema jogável próprio.
- **Dependências:** É uma biblioteca consumida por diversos mods multiplataforma. A necessidade da instalação top-level é determinada pelo grafo real de dependentes; cópias jarjar/embedded não contam como mods top-level separados.
- **Sobreposição:** Não é redundante com NeoForge, Cloth Config, Curios ou APIs de providers. Architectury abstrai diferenças de plataforma para consumidores; outras bibliotecas têm contratos distintos.
- **Compatibilidade/Riscos:** Principal risco é version mismatch entre Architectury e consumidores, causando linkage/registry/network/mixin errors. Não remover por ausência de conteúdo visível e não atualizar isoladamente para linha de outro Minecraft. Stack traces podem apontar Architectury mesmo quando o erro está no consumer.
- **Observações:** mod id: `architectury`; runtime 13.0.11. Build oficial 13.0.11+neoforge suporta Minecraft 1.21–1.21.1. Mais de 90 event hooks, networking/registry/loader abstractions e `@ExpectPlatform` são superfícies de desenvolvimento, não gameplay.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth/source oficiais Architectury API 13.0.11 + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/architectury-api
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Architectury 13.0.11, platform abstraction, event/network/registry contracts, top-level vs jarjar and dependency-graph risks confirmed in global QC #39. Estado anterior `Integrado ao Github` preservado como histórico documental.
- **Histórico da decisão:** Dependência. Em 07/09/2026 a ficha foi refeita para registrar seu papel exclusivamente infraestrutural, a distinção top-level versus jarjar e a regra de avaliar necessidade pelo grafo de dependentes.
- **Data da última decisão:** 2026-09-07

> 🧩 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta ficha documenta `architectury-13.0.11-neoforge.jar`, mod id `architectury`, em NeoForge 1.21.1. Por ser biblioteca, a exaustividade é de **API e contratos reais** — mais de 90 event hooks upstream, networking, registries, loader/platform queries, `@ExpectPlatform`, separação client/server, top-level versus jarjar, grafo de dependentes e falhas de versionamento. Architectury não possui mobs, itens, blocos ou progressão próprios a inventariar e não é provider de perk.

## 1. Identidade e versão
- **Mod:** Architectury API.
- **Runtime instalado:** `13.0.11`.
- **JAR físico:** `architectury-13.0.11-neoforge.jar`.
- **Compatibilidade oficial da build:** Minecraft 1.21–1.21.1 / NeoForge.
- **Ambiente:** cliente + servidor.
- **Licença:** LGPL-3.0.
- **Papel:** abstrair diferenças de APIs/loaders para mods multiplataforma.
- **Decisão:** **Dependência**.

## 2. O que Architectury API realmente é
Architectury API fornece uma camada comum para autores que querem compartilhar código entre plataformas. O projeto oficial a descreve como uma API que abstrai chamadas equivalentes de loaders diferentes, permitindo que o mod consumidor escreva grande parte da lógica em código comum e delegue diferenças de plataforma para implementações específicas.

Isso explica por que o jogador normalmente não “vê conteúdo Architectury”: sua função é possibilitar que outros mods iniciem, registrem conteúdo, escutem eventos e troquem packets de maneira portátil.

## 3. Superfícies principais
O README oficial informa que Architectury contém **mais de 90 event hooks** e abstrações para várias áreas.

### Eventos
Hooks unificados para lifecycle, jogador, entidades, blocos, mundo e eventos client-side. A implementação da build específica converte esses hooks para os mecanismos nativos do loader.

### Networking
Abstração para registro e tratamento de packets C2S/S2C, evitando que cada mod consumidor replique toda a diferença entre plataformas.

### Registries
Abstrações para registrar itens, blocos e outros conteúdos em registries de forma compartilhada entre loaders.

### Loader/platform queries
Funções para consultar loader, environment, presença de mods e caminhos/configurações específicas de plataforma.

### `@ExpectPlatform`
Mecanismo de delegação no qual um método estático declarado no módulo comum é resolvido para implementação específica do loader. É ferramenta de desenvolvimento; não é evento de gameplay.

## 4. Authority no pack
Architectury **não possui authority de gameplay** sobre os sistemas dos mods que o utilizam. Se Identity2, AnimalHusbandry ou outro consumer usa Architectury para registro/networking, a semântica da mecânica continua pertencendo ao consumer.

Portanto:
- não criar perk “de Architectury”;
- não usar um evento Architectury genérico como justificativa para substituir o hook nativo de um provider quando existe evento mais específico;
- não tratar `architectury` como sistema equivalente a Curios, AE2, Iron's, Create etc.

## 5. Top-level JAR versus bibliotecas embutidas
A modlist física contém **um JAR top-level** `architectury-13.0.11-neoforge.jar`, que deve ser catalogado normalmente.

Alguns mods também podem carregar cópias `jarjar`/embedded de bibliotecas. Essas cópias internas **não contam como mods top-level adicionais** na catalogação. A presença de uma Architectury embarcada em outro JAR também não autoriza remover automaticamente a instalação top-level: a necessidade é determinada pelas dependências reais de todos os consumers.

## 6. Necessidade e remoção
A decisão correta para Architectury é **Dependência**, não “manter por conteúdo”. Sua presença deve ser avaliada pelo grafo de mods que a declaram/consomem.

Remover uma biblioteca estrutural porque “não adiciona nada ao jogo” pode impedir o carregamento dos consumers ou causar falhas em registros, eventos e networking antes mesmo da criação do mundo.

## 7. Compatibilidade de versão
A build `13.0.11+neoforge` foi publicada oficialmente para Minecraft 1.21/1.21.1. Isso confirma a compatibilidade de plataforma da biblioteca instalada, mas **não prova sozinho que qualquer versão arbitrária de um consumer é compatível**.

Regra de atualização:
1. conferir a faixa exigida pelos dependentes;
2. atualizar Architectury junto com o conjunto quando necessário;
3. realizar boot e smoke test client/server;
4. investigar erros de API/mixin/registration pelo consumer que falhou, não pela ausência de “conteúdo visível”.

## 8. Riscos relevantes
1. **Version mismatch:** consumers compilados para outra major/minor podem falhar em linkage/method resolution.
2. **Embedded + top-level:** não contar duas vezes e não remover a top-level sem mapear dependentes.
3. **Client/server separation:** consumer pode usar uma API client-only incorretamente; o crash aparece no stack Architectury, mas a origem pode ser o mod consumidor.
4. **Registry/network hooks:** falhas surgem cedo no startup e devem ser diagnosticadas pelo stack trace completo.
5. **Atualização isolada:** “latest” de Architectury para outra versão de Minecraft não é apropriada ao pack 1.21.1; a authority é a build compatível com o jogo e os dependentes atuais.

## 9. Sobreposição
Não é redundante com NeoForge API, Cloth Config, Curios ou outras bibliotecas. Architectury abstrai plataformas; cada uma dessas bibliotecas possui outro contrato. Também não é substituto automático de APIs específicas do provider.

## 10. Matriz mínima de teste
Para uma biblioteca desse tipo, os testes são de **integração dos dependentes**:
- client boot até menu;
- criação/entrada em mundo;
- dedicated-server boot;
- conexão cliente↔servidor;
- executar uma amostra dos mods consumidores instalados;
- verificar ausência de `NoSuchMethodError`, `ClassNotFoundException`, registry errors, packet mismatches ou mixin failures ligados à versão;
- repetir após qualquer update de Architectury ou consumer importante.

## 11. Fontes
- [CurseForge — Architectury API 13.0.11 NeoForge](https://www.curseforge.com/minecraft/mc-mods/architectury-api/files/8492726)
- [Source oficial — Architectury API](https://github.com/architectury/architectury-api)
- Modlist física do projeto — authority da instalação top-level.

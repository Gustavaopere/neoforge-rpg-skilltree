# ModernFix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b085d5ed1035b98e9d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — ModernFix `5.27.24+mc1.21.1`, `modernfix-modernfix.mixins.json`, Create Teleporters Remastered `2.0.2`, FastSuite `6.0.7` e FerriteCore `7.0.3` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”; a autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** ModernFix
- **Arquivo JAR:** `modernfix-neoforge-5.27.24+mc1.21.1.jar`
- **Versão 1.21.1:** 5.27.24+mc1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Performance
- **Função:** Conjunto configurável de correções e otimizações para Minecraft modded, abrangendo launch/world load, memória, resources/models/caches e outros caminhos internos.
- **Dependências:** NeoForge/Minecraft 1.21.1 conforme a release instalada. Não tratar FerriteCore/FastSuite como dependências; são coinstalados com funções parcialmente complementares.
- **Sobreposição:** Objetivos parcialmente se cruzam com FerriteCore/FastSuite/outros performance mods, mas não há redundância integral comprovada. Remoção só com profiling ou conflito reproduzível.
- **Compatibilidade/Riscos:** Stack de patches configuráveis. Riscos: mixin collision, config drift, caches/resources/models e regressões de world load. Há histórico na linha 1.21.1 envolvendo `mixin.perf.dynamic_resources` + Create Teleporters; o pack possui Create Teleporters Remastered, mas nenhum conflito atual 5.27.24 foi confirmado.
- **Observações:** JAR físico `modernfix-neoforge-5.27.24+mc1.21.1.jar`, file ID 8774737, Release de 31/08/2026. Upstream usa desenvolvimento contínuo; não foi pinado tag/commit byte-equivalente a 5.27.24 neste lote.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 5.27.24 + source/wiki oficial embeddedt/ModernFix; source HEAD usado apenas para arquitetura, não como pin binário.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/modernfix/files/8774737 | https://github.com/embeddedt/ModernFix
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — dossiê reconstruído para ModernFix 5.27.24+mc1.21.1; patches/mixins configuráveis, client/server, resource/model pipeline, performance, compat histórica com Create Teleporters, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `modernfix-neoforge-5.27.24+mc1.21.1.jar`, mod id `modernfix`, versão `5.27.24+mc1.21.1`. A release oficial exata é o CurseForge file ID `8774737`, publicada em 31/08/2026 para NeoForge 1.21.1. O projeto upstream usa desenvolvimento contínuo e nem toda build publicada corresponde a uma GitHub Release/tag; por isso o JAR físico + arquivo CurseForge são authority de versão, enquanto a branch `1.21.1` do source é usada para arquitetura e superfícies de configuração.

## 1. Identidade e papel
- **Mod:** ModernFix.
- **JAR físico:** `modernfix-neoforge-5.27.24+mc1.21.1.jar`.
- **Mod id:** `modernfix`.
- **Runtime:** `5.27.24+mc1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor/projeto:** embeddedt / ModernFix.
- **CurseForge file ID:** 8774737.
- **Papel:** pacote amplo de correções e otimizações para Minecraft modded, com foco em launch/load time, uso de memória, carregamento de recursos/modelos e outras superfícies de performance/bugfix.

ModernFix não é um único “FPS mod”. Ele aplica um conjunto de patches/mixins independentes, muitos configuráveis, que atuam em subsistemas diferentes do jogo e do ambiente modded.

## 2. Arquitetura por patches/mixins
A modlist física registra `modernfix-modernfix.mixins.json`. O projeto organiza grande parte do comportamento em patches/mixins que podem ser habilitados/desabilitados por configuração.

A consequência operacional é importante: dizer apenas “ModernFix está instalado” não informa quais otimizações estão efetivamente ativas. Diagnóstico de conflito deve identificar o patch/mixin específico antes de recomendar remoção do mod inteiro.

## 3. Configuração
A linha 1.21.1 usa configuração de mixins, historicamente exposta por `config/modernfix-mixins.properties`. Opções individuais controlam patches específicos.

Ao auditar bugs:
- preservar o arquivo real do pack;
- comparar overrides com defaults da versão instalada;
- desabilitar apenas o patch suspeito quando houver evidência;
- não copiar uma configuração de outra versão do Minecraft como se fosse equivalente.

## 4. Performance e memória
O README upstream define ModernFix como mod voltado a melhorar:
- tempo de inicialização;
- tempo de carregamento de mundos;
- uso de memória;
- performance/robustez de vários caminhos internos.

O benefício exato depende do conjunto de mods, hardware, datapacks, quantidade de modelos/recursos e opções habilitadas. Esta ficha não atribui percentuais de ganho sem benchmark da instância.

## 5. Resource/model pipeline
Uma parte relevante da superfície histórica do ModernFix envolve recursos dinâmicos, model baking, caches e otimizações de carregamento. Isso é especialmente sensível neste pack porque há grande quantidade de mods Create, assets, resource packs e extensões de rendering.

Não se deve presumir que qualquer model/render bug é causado pelo ModernFix; porém, quando um problema só aparece com carregamento/reload de recursos, a configuração de patches de recursos/modelos é uma área prioritária de triagem.

## 6. Compatibilidade histórica relevante: Create Teleporters
Existe histórico público de conflito na linha 1.21.1 envolvendo `mixin.perf.dynamic_resources=true` e Create: Teleporters em ModernFix 5.26.1. O workaround relatado era desabilitar aquela opção específica. O issue foi encerrado e **não prova incompatibilidade da build 5.27.24 instalada**.

Este pack contém fisicamente `createteleporters-remastered-2.0.2b-neoforge-1.21.1.jar`. Portanto, o caso histórico justifica um teste dirigido após updates de ModernFix/Create Teleporters, mas não é motivo suficiente para remover nenhum dos dois sem reprodução atual.

## 7. Relação com outros mods de otimização
O pack também usa outros componentes de performance, incluindo FerriteCore e FastSuite. Sobreposição de objetivo não significa redundância integral:
- FerriteCore tem foco forte em estruturas de memória;
- FastSuite atua em caminhos de recipes/caches próprios;
- ModernFix agrega patches de múltiplas categorias.

Remoção por “faz a mesma coisa” só deve ocorrer após profiling, documentação de patches ou conflito reproduzível.

## 8. Client/server
ModernFix contém patches aplicáveis a mais de um side. Portanto:
- testar cliente e dedicated server;
- diferenciar crash client/render/resource de problema server/world/data;
- não assumir que uma opção client-only precisa existir no servidor nem o inverso sem conferir o arquivo/metadata da versão instalada.

## 9. Lifecycle e atualização
Por alterar caminhos internos do Minecraft e de integração modded, ModernFix é sensível a:
- update de NeoForge;
- update de mods que transformam as mesmas classes;
- mudanças de loaders de resources/models;
- datapack/resource reload;
- geração/entrada em mundo;
- shutdown/restart.

Uma atualização que inicia normalmente ainda precisa de regressão em world load e reload; boot verde sozinho não cobre o escopo.

## 10. Changelog 5.27.24
A publicação exata 5.27.24 no CurseForge orienta consultar o GitHub/wiki para mudanças maiores e não fornece uma lista completa de patches no texto curto do arquivo. Esta ficha, portanto, não inventa uma lista de “novidades 5.27.24” a partir de commits de versões anteriores.

## 11. Riscos técnicos
1. **Mixin collision:** patches podem tocar classes também alteradas por outros performance/render/content mods.
2. **Config drift:** copiar `modernfix-mixins.properties` de outra versão pode habilitar/desabilitar opções inexistentes ou com comportamento alterado.
3. **Dynamic resources:** superfície histórica de conflito com Create Teleporters; precisa de reteste na build atual.
4. **Model/resource caching:** bugs podem aparecer apenas após `/reload`, troca de resource pack ou client reconnect.
5. **World loading:** otimização/caching pode mascarar problema até restart completo.
6. **Attribution error:** não culpar ModernFix apenas porque aparece no stacktrace de mixin; verificar transformação real e reprodução A/B.
7. **Continuous upstream:** source HEAD não deve ser tratado como byte-equivalente ao file ID 8774737 sem pin confirmado.

## 12. Matriz de testes
- [ ] Dedicated server inicia com ModernFix 5.27.24 e NeoForge atual do pack.
- [ ] Cliente inicia e entra no mundo sem mixin/config errors.
- [ ] Criar e carregar mundo mede tempo/memória de forma comparável quando necessário.
- [ ] Restart completo do servidor não produz registry/cache corruption.
- [ ] `/reload` conclui sem model/resource exceptions.
- [ ] Troca/reload de resource packs no cliente não produz modelos ausentes/stale.
- [ ] Create Teleporters Remastered carrega, renderiza e executa sua função com `dynamic_resources` conforme config atual.
- [ ] FastSuite/FerriteCore coexistem sem regressão reproduzível.
- [ ] Testar conexão/reconexão de dois clientes em mundo já carregado.
- [ ] Qualquer suspeita de conflito é reproduzida com alteração de **uma opção por vez**, não removendo vários performance mods simultaneamente.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física: JAR, mod id, runtime e `modernfix-modernfix.mixins.json`.
- CurseForge oficial: file ID 8774737, Release NeoForge 1.21.1, 31/08/2026.
- Source oficial: `embeddedt/ModernFix`, branch 1.21.1 e documentação/wiki do projeto.
- Evidência histórica de compat: issue público sobre `mixin.perf.dynamic_resources` + Create Teleporters em versão anterior da mesma linha.
- **Limite:** não foi localizado um tag/commit público inequivocamente pinado como 5.27.24; source HEAD não é apresentado como binário exato.

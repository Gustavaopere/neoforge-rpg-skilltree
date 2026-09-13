# Glodium — 1.21-2.2-neoforge

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81129c5bfde08fe2fdda  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Glodium
- **Arquivo JAR:** `Glodium-1.21-2.2-neoforge.jar`
- **Versão 1.21.1:** `1.21-2.2-neoforge`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/glodium/files/5821676
- **Função:** Biblioteca de infraestrutura para renderização, registries, networking, recipes, reflection e utilidades compartilhadas por mods consumidores.
- **Dependências:** NeoForge 1.21.1. A necessidade concreta depende dos consumers. Source público da linha NeoForge 1.21 encontrado em versão 1.2, portanto não é tratado como pin exato do runtime 2.2.
- **Compatibilidade/Riscos:** ABI/source drift, reflection/mappings, classloading client no servidor, registry duplicado e helpers de rede/recipe usados incorretamente por consumers. Release 2.2 corrige crash com JDK23+.
- **Sobreposição:** Não é otimização visual substituível por ImmediatelyFast nem mod de conteúdo. É biblioteca/API; remover ou trocar exige primeiro mapear e testar consumers reais.
- **Observações:** Runtime físico `Glodium-1.21-2.2-neoforge.jar`. O branch `neoforge-1.21` acessível declara 1.2; arquitetura foi usada apenas estruturalmente. Java do pack é 21, não JDK23.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release oficial Glodium 1.21-2.2 NeoForge + source oficial GlodBlock/Glodium branch neoforge-1.21 usado com source drift explícito.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Glodium 1.21-2.2-neoforge release-pinned; render/registry/network/recipe/reflection architecture, side/lifecycle, source drift, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `Glodium-1.21-2.2-neoforge.jar`, mod id `glodium`, versão `1.21-2.2-neoforge`. A release oficial 2.2 para NeoForge 1.21.1 é a authority da versão. O branch público `GlodBlock/Glodium:neoforge-1.21` ainda declara `1.21-1.2-neoforge`; por isso ele é usado somente para arquitetura/nomenclatura da linha, não como pin exato do binário 2.2.

## 1. Identidade e papel
Glodium é uma biblioteca de código. A descrição oficial resume seu escopo como infraestrutura para **render, registry e network** e afirma que não faz nada útil quando instalada isoladamente.

## 2. Authority
Glodium não é owner de gameplay. Consumers usam suas abstrações; cada consumer continua autoridade de itens, blocos, receitas, estado de rede e renderização que implementa. Remover a biblioteca sem mapear consumidores pode impedir carregamento ou causar linkage errors.

## 3. Arquitetura pública da linha 1.21
O source oficial da branch `neoforge-1.21` expõe pacotes `client`, `network`, `recipe`, `reflect`, `registry` e `util`, além do entrypoint `Glodium`. Essa árvore confirma a natureza transversal indicada pela descrição do projeto.

## 4. Registry helpers
O pacote `registry` mostra que a biblioteca fornece abstrações de registro para consumers. Integrações próprias não devem duplicar registries ou presumir que um helper Glodium se torna owner do objeto registrado; registry IDs continuam definidos pelo mod consumidor.

## 5. Networking
O pacote `network` demonstra infraestrutura compartilhada de rede. Payloads concretos, autoridade e validação continuam pertencendo aos consumers. Cliente não deve conseguir transformar um helper de transporte em autorização implícita para mudar state server-side.

## 6. Recipe e utilidades
A existência de pacote `recipe` indica suporte reutilizável para lógica/serialização de recipes na linha pública. Sem source pinado da 2.2, esta ficha não promove classes, codecs ou formatos específicos desse pacote como contrato exato da build instalada.

## 7. Client/render
O pacote `client` confirma helpers client-side. Consumers devem preservar side isolation: classes de render exclusivamente client não podem ser carregadas por caminhos common/dedicated server.

## 8. Reflection e compatibilidade binária
O pacote `reflect` torna reflection uma superfície estrutural da linha pública. Reflection é sensível a renames/mappings/version drift; consumers devem falhar de forma diagnóstica quando um alvo muda, em vez de produzir state parcial silencioso.

## 9. Release 2.2
A release física `1.21-2.2-neoforge` foi publicada para Minecraft 1.21.1. O changelog oficial registra uma correção de crash com **JDK 23+**. O pack usa Java 21, então esse fix não implica necessidade de JDK 23, mas mostra que bootstrap/runtime Java é uma superfície relevante.

## 10. Source drift
A branch pública NeoForge 1.21 atualmente acessível declara `1.21-1.2-neoforge`, enquanto o JAR físico é 2.2. Logo:
- nomes de pacotes/arquitetura podem informar o dossiê;
- assinaturas, métodos, classes e comportamento específicos da 2.2 permanecem fail-closed sem pin correspondente;
- qualquer integração própria deve compilar/testar contra o artefato realmente instalado.

## 11. Client / server e lifecycle
A biblioteca contém áreas client e common/network. Validar cold boot, dedicated server, login, reconnect, resource reload quando consumer usa render helpers, datapack reload quando usa recipe helpers e shutdown/restart. O failure mode esperado de incompatibilidade deve apontar para o consumer/API, não ser mascarado como problema de world state.

## 12. Riscos técnicos
- remover Glodium enquanto consumer ainda depende dela;
- consumer compilado contra API diferente da 2.2;
- source 1.2 ser confundido com binário 2.2;
- classloading de helper client no servidor;
- payload consumer sem validação server-side;
- reflection quebrar após mappings/API drift;
- registry helper usado com ID duplicado;
- recipe/data serializer incompatível após reload/update;
- atribuir bug de consumer à biblioteca sem stack trace causal.

## 13. Matriz de testes obrigatória
- [ ] Dedicated server inicia com Glodium 2.2 e todos os consumers atuais.
- [ ] Cliente conecta sem `NoClassDefFoundError`, `NoSuchMethodError` ou linkage error.
- [ ] Consumer que usa render helpers funciona após F3+T/resource reload.
- [ ] Consumer que usa network helpers sincroniza state sem client authority indevida.
- [ ] Consumer que usa registry helpers registra IDs uma única vez.
- [ ] Datapack/recipe reload de consumers não gera serializer errors.
- [ ] Java 21 boot permanece estável; não extrapolar o fix JDK23+ como requisito.
- [ ] Atualização futura de Glodium é testada contra todos os consumidores antes de troca.

## 14. Evidências e limites
- **Modlist física:** confirma `Glodium-1.21-2.2-neoforge.jar`.
- **Release oficial:** confirma 2.2 / NeoForge 1.21.1 e fix de crash JDK23+.
- **Source oficial da linha:** branch `neoforge-1.21`, com `client`, `network`, `recipe`, `reflect`, `registry` e `util`.
- **Limite:** a branch pública encontrada declara 1.2, não 2.2; nenhum método/classe específico da build 2.2 foi inventado.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

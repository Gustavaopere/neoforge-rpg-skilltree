# Resourceful Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81238b99ee1a5cee5d51
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `resourcefullib-neoforge-1.21-3.0.12.jar`, mod id `resourcefullib`, runtime `3.0.12`; Jade 15.10.3 contém Resourceful Lib common 3.0.0 embutida
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Resourceful Lib 3.0.12 está presente top-level e Jade hospeda uma cópia common 3.0.0 embutida. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Resourceful Lib
- **Arquivo JAR:** `resourcefullib-neoforge-1.21-3.0.12.jar`
- **Versão 1.21.1:** 3.0.12
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca geral compartilhada da Team Resourceful para infraestrutura reutilizável de mods consumidores; não adiciona gameplay autônomo.
- **Dependências:** NeoForge 1.21/1.21.1. Consumidores top-level não foram causalmente enumerados nesta etapa. Jade 15.10.3 hospeda separadamente uma cópia embedded Resourceful Lib common 3.0.0.
- **Sobreposição:** Não é duplicata de Resourceful Config. A cópia 3.0.0 embedded em Jade é dependência embarcada do host, não substituto automaticamente equivalente do top-level 3.0.12.
- **Compatibilidade/Riscos:** Riscos: consumer graph desconhecido, binary API drift, classloading/dist leakage, stale global state/cache e lineage top-level 3.0.12 vs embedded 3.0.0 no Jade. A cópia embedded não prova conflito nem redundância segura.
- **Observações:** Jar-in-Jar confirmado: Jade 15.10.3 contém `/META-INF/jars/resourcefullib-common-1.21-3.0.0.jar`. Essa cópia pertence ao host e não recebe entrada top-level própria. Não remover 3.0.12 sem dependency mapping/teste.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + inventário JarJar/Jars do snapshot atual + publicação oficial Resourceful Lib 3.0.12 + repositório oficial usado apenas como contexto arquitetural.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/resourceful-lib
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Resourceful Lib 3.0.12 reconstruído: shared-library role, top-level vs Jade embedded 3.0.0, JARJAR ownership, lifecycle/classloading, risks and tests.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime top-level: `resourcefullib-neoforge-1.21-3.0.12.jar`, mod id `resourcefullib`, versão `3.0.12`, NeoForge 1.21/1.21.1. Resourceful Lib é uma **biblioteca geral da Team Resourceful**. A modlist também revela uma cópia **embarcada Resourceful Lib common 3.0.0 dentro de Jade 15.10.3**; essa cópia Jar-in-Jar pertence ao hospedeiro e não constitui entrada top-level separada.

## 1. Identidade e papel
- **Mod:** Resourceful Lib.
- **JAR top-level:** `resourcefullib-neoforge-1.21-3.0.12.jar`.
- **Mod id:** `resourcefullib`.
- **Versão instalada:** `3.0.12`.
- **Loader/jogo:** NeoForge; release compatível com 1.21/1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Tipo:** biblioteca/API compartilhada.

## 2. Papel no modpack
Fornece infraestrutura reutilizável para mods consumidores da Team Resourceful e outros projetos que dependem de sua API. Não deve ser avaliada por conteúdo visível próprio: sua importância é contratual e de runtime.

Uma biblioteca top-level pode ser load-bearing mesmo sem item, bloco ou UI própria.

## 3. Autoridade / ownership
- **Resourceful Lib:** contratos/utilidades compartilhadas expostas aos consumidores.
- **Mod consumidor:** autoridade do gameplay, conteúdo, config e dados que utiliza essa infraestrutura.
- **Jade:** hospeda sua própria cópia embarcada 3.0.0; essa cópia faz parte do empacotamento do Jade e não deve ser catalogada como mod independente.

A biblioteca não deve receber ownership de mecânicas apenas porque um consumidor chama sua API.

## 4. Superfície técnica — fail-closed
A documentação oficial classifica Resourceful Lib como shared library. O repositório público confirma arquitetura multiplataforma do projeto, porém o branch atual não foi assumido como source pin exato do binário 3.0.12.

Por isso esta ficha **não inventaria uma lista de classes, métodos, packets, codecs ou registries exatos da 3.0.12** sem tag/commit correspondente.

O que está confirmado é seu papel de biblioteca geral e a presença física das versões top-level/embarcada.

## 5. Top-level 3.0.12 versus embedded 3.0.0
A modlist física contém:
- top-level `resourcefullib-neoforge-1.21-3.0.12.jar`;
- dentro de `Jade-1.21.1-NeoForge-15.10.3.jar`, `/META-INF/jars/resourcefullib-common-1.21-3.0.0.jar`.

Isso **não prova redundância segura** do top-level 3.0.12. Jar-in-Jar pode existir para satisfazer o host, enquanto outros consumidores dependem da versão top-level/NeoForge.

Também não prova conflito automático: classloading/metadata podem tratar o componente embarcado de forma controlada. A conclusão correta é registrar o version lineage e testar.

## 6. JARJAR e regra de catalogação
A cópia 3.0.0 embarcada em Jade deve permanecer documentada como dependência do hospedeiro, não como nova linha do catálogo.

Se outra cópia embarcada for encontrada em futuro snapshot, registrar o host, versão e motivo aparente sem criar página top-level automaticamente.

## 7. Client / Server
A release top-level é Client & Server. Como biblioteca, isso significa que consumidores podem usá-la em ambos os lados; não significa que toda API seja segura em qualquer dist ou que gameplay possa ser client-authoritative.

Qualquer consumer que usa recursos server-side deve continuar validado em dedicated server para detectar classloading indevido.

## 8. Lifecycle
Para uma shared library, os gates relevantes são:
- mod bootstrap/initialization;
- client startup;
- dedicated server startup;
- criação/encerramento de world/server context quando consumidores inicializam recursos;
- login/reconnect quando consumidor utiliza estado compartilhado;
- resource/data reload quando consumidor aciona APIs correspondentes;
- server restart;
- atualização simultânea de library + consumidores.

Caches ou storage globais não devem reter world/player antigo após descarte, caso o consumidor utilize tais superfícies.

## 9. Multiplayer
A biblioteca não deve introduzir state global compartilhado incorretamente entre players/servers por uso inadequado de consumidores. Quando um consumer serializa/sincroniza dados através da library, ownership e authority continuam pertencendo ao consumer/provider funcional.

Sem source pin, nenhuma política específica de networking foi atribuída à 3.0.12.

## 10. Consumidores e dependência real
Nesta etapa não foi fechado um inventário causal de todos os mods que exigem o top-level 3.0.12. Portanto:
- **Decisão permanece Sem decisão**;
- não remover por existir cópia embarcada no Jade;
- não promover automaticamente cada mod da Team Resourceful a consumidor sem metadata/source correspondente.

A remoção só é segura após dependency mapping ou teste controlado.

## 11. Relação com Resourceful Config
**Resourceful Lib** e **Resourceful Config** são projetos distintos:
- Resourceful Lib = infraestrutura compartilhada geral;
- Resourceful Config = API focada em configuração.

A presença de ambos é coerente e não implica duplicação funcional.

## 12. Version drift e compatibilidade
A maior superfície de risco é contrato binário entre consumidores e library. Mudanças de versão podem produzir:
- missing class/method;
- comportamento alterado em utility/data layer;
- incompatibilidade de serialização;
- classloading diferente entre client/server;
- divergência entre top-level e embedded lineage.

Nenhum desses problemas foi afirmado como presente sem teste.

## 13. Riscos técnicos
1. **Unknown consumer graph:** remoção prematura pode impedir boot.
2. **Binary API drift:** consumidor compilado para outra versão.
3. **Embedded/top-level lineage:** 3.0.0 no Jade versus 3.0.12 top-level.
4. **Classloading ambiguity:** bibliotecas duplicadas precisam ser testadas, não presumidas conflitantes.
5. **Stale global state/cache:** risco genérico de shared infrastructure em reload/restart.
6. **Dist leakage:** consumer chama classe client-only no dedicated server.
7. **Migration/serialization drift:** formatos usados por consumidores mudam entre versões.

## 14. Matriz de testes
- [ ] Cliente inicia com Resourceful Lib 3.0.12.
- [ ] Dedicated server inicia sem missing class/method ou dist classloading error.
- [ ] Jade 15.10.3 inicia com sua cópia embedded 3.0.0 sem colisão aparente com top-level 3.0.12.
- [ ] Consumidores conhecidos inicializam e executam funções básicas.
- [ ] Login/reconnect não produz stale state em consumidor dependente.
- [ ] Server restart não deixa resource/cache antigo reutilizado indevidamente.
- [ ] Resource/data reload de consumidor não duplica registration/listener.
- [ ] Atualização futura da library é testada contra todos os consumidores antes de merge no pack.
- [ ] Remoção experimental do top-level só ocorre depois de dependency mapping e deve validar boot + funções dos consumidores.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: top-level Resourceful Lib 3.0.12 e embedded Resourceful Lib common 3.0.0 dentro de Jade 15.10.3.
- Publicação oficial: release NeoForge 1.21/1.21.1, Client & Server, shared-library role.
- Repositório oficial: contexto arquitetural do projeto, sem tratá-lo como source pin exato da 3.0.12.
- **Limite:** consumer graph e superfície pública exata da 3.0.12 não foram exaustivamente pinados; classes/métodos não confirmados foram deliberadamente omitidos.

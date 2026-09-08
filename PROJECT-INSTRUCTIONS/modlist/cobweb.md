# Cobweb

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db813ba717c952b8dd5bc5  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Cobweb
- **Arquivo JAR:** `cobweb-neoforge-1.21-1.4.0.jar`
- **Versão 1.21.1:** `1.4.0`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cobweb
- **Função:** Crystal Nest API/library com abstrações compartilhadas para registro e entries de registries, utilidades comuns e infraestrutura dinâmica usada por mods consumidores do ecossistema Crystal Nest.
- **Dependências:** Biblioteca de infraestrutura. A release 1.4.0 para Minecraft 1.21/1.21.1 não publica hard dependency adicional na página do arquivo; necessidade no pack é determinada pelos consumers Crystal Nest e outros que compilam contra Cobweb.
- **Compatibilidade/Riscos:** Riscos de version drift em CobwebEntry/registry helpers e APIs dinâmicas. O changelog da linha 1.21 registra correções de Holder matching e tags, e 1.4.0 adiciona DynamicBlockEntityType. Features marcadas no changelog como 1.21.3+ não são retroativamente atribuídas ao JAR 1.21/1.21.1 sem inspeção.
- **Sobreposição:** Biblioteca técnica do ecossistema Crystal Nest. Coexistência com outras APIs não implica redundância; consumers dependem de contratos próprios.
- **Observações:** mod id `cobweb`; runtime 1.4.0. Changelog da linha comprova unified registering/CobwebEntry desde 1.21, fixes de tags/Holder e `DynamicBlockEntityType` em 1.4.0; APIs explicitamente restritas a 1.21.3+ permanecem fora do escopo confirmado deste JAR.
- **Procedência:** Modlist física mais recente de 07/09/2026 + CurseForge oficial Cobweb 1.4.0 para NeoForge 1.21/1.21.1 + changelog/source oficial Crystal-Nest/cobweb branch 1.21.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Cobweb 1.4.0 foi reconfirmado no JAR físico e reconstruído como Crystal Nest API. A instalação atual não foi convertida em decisão de manter/remover.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — registry/CobwebEntry contract, DynamicBlockEntityType, version/branch boundaries, side/lifecycle e consumer risks catalogados fail-closed.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `cobweb-neoforge-1.21-1.4.0.jar`, mod id `cobweb`, runtime `1.4.0`, NeoForge 1.21/1.21.1. Cobweb é a **Crystal Nest API**, não um provider de gameplay independente.

## 1. Papel e authority

Cobweb concentra infraestrutura compartilhada usada por mods Crystal Nest. O consumer continua authority da feature final — fogo, crop, portal, equipamento ou outro gameplay — enquanto Cobweb fornece contracts reutilizáveis.

Não atribuir a Cobweb conteúdo dos consumers apenas porque eles a declaram como required dependency.

## 2. Unified registering e CobwebEntry

O changelog da linha 1.21 registra a evolução de uma API unificada de registration e, desde v1.1.0, métodos de registry retornando **`CobwebEntry`** em vez de simples `Supplier`.

Essa abstraction é consumer-facing e version-sensitive. Integrações próprias devem usar o source/API da branch correspondente, não reflection baseada em versões antigas.

## 3. Holder matching

O changelog posterior da linha 1.21 documenta fix para `CobwebEntry`s verificarem corretamente se um `Holder` corresponde. Isso demonstra que matching de registry/holder é uma superfície real da library.

Para o JAR 1.21/1.21.1, tratar matching como contract sensível e não duplicar equivalência de registry por nome humano.

## 4. Tags e datapack support

A linha 1.21 também possui correções históricas relacionadas a item tags/internal datapack API. Isso reforça que tag resolution é superfície técnica da library.

Features do changelog explicitamente marcadas como **1.21.3+** ou versões posteriores não são atribuídas automaticamente a este JAR 1.21/1.21.1 sem inspeção direta.

## 5. DynamicBlockEntityType — 1.4.0

O changelog de Cobweb v1.4.0 registra adição de **`DynamicBlockEntityType`** e abandono de suporte a Minecraft abaixo de 1.21 na linha moderna.

O catálogo registra a classe/conceito porque aparece no changelog oficial da versão, mas não inventa seus métodos, lifecycle interno ou consumers específicos sem source pin mais granular.

## 6. Branch/version boundary

O projeto reutiliza versões semânticas entre vários patch levels de Minecraft. Por isso, um changelog de `1.4.0` pode conter notas específicas para versões posteriores de 1.21.x.

Regra fail-closed desta ficha: só promover ao runtime 1.21/1.21.1 aquilo que não está explicitamente limitado a 1.21.3+ ou outra versão posterior, salvo inspeção direta do branch/JAR correspondente.

## 7. Consumer-driven necessity

Mods Crystal Nest como Prometheus/Soul Fire'd/Harvest with ease podem declarar Cobweb required em suas linhas correspondentes. Isso não significa que todos estejam presentes ou que todos usem as mesmas APIs.

A necessidade de manter o JAR deve ser resolvida pelo dependency graph físico, não por uma lista genérica de projetos do autor.

## 8. Client/server

Cobweb é Client & Server. Registry/data contracts usados por gameplay são common/server; qualquer helper visual do consumer permanece client-side.

A library não deve forçar classloading de client-only code no dedicated server quando o consumer usa apenas API common.

## 9. Lifecycle

Validar mod bootstrap, registry creation, tag/datapack reload, world join, server restart e consumer reload paths. `CobwebEntry`/Holder references não podem permanecer stale depois de registry/tag reload quando a API espera resolução atualizada.

## 10. Version drift

Sintomas possíveis:

- linkage errors em consumer;
- registry entry não resolvida;
- Holder/tag mismatch;
- dynamic block entity registration incompatível;
- behavior diferente entre patch levels de Minecraft com a mesma versão Cobweb.

Diagnóstico deve sempre registrar **consumer + Minecraft patch + Cobweb version**.

## 11. Riscos

1. Remover Cobweb com consumer ativo.
2. Importar API de 1.21.3+ para o JAR 1.21/1.21.1.
3. CobwebEntry/Holder mismatch quebrar registry logic.
4. Tag reload deixar cache stale.
5. Dynamic block entity contract divergir entre versions.
6. Library similar ser tratada como substituta automática.
7. Client-only helper vazar para dedicated server.

## 12. Matriz de testes

1. Dedicated server boot com consumers instalados.
2. Client join/reconnect.
3. Registry bootstrap sem missing entries.
4. Datapack/tag reload dos consumers.
5. Consumer que usa CobwebEntry/Holder matching.
6. Dynamic block entity consumer, se houver no pack, após save/reload.
7. Restart repetido sem duplicate registration.
8. Atualização futura: validar source branch exata antes de migrar API.

## 13. Evidência

- modlist física: Cobweb 1.4.0 NeoForge para 1.21/1.21.1;
- CurseForge oficial: Crystal Nest API, Client & Server;
- changelog oficial: CobwebEntry/unified registry history, tag/Holder fixes e DynamicBlockEntityType em 1.4.0;
- notas explicitamente restritas a versões posteriores são mantidas fora do runtime atual por política fail-closed.

> 🕸️ Boundary canônico: Cobweb fornece **contracts de infraestrutura**; features jogáveis e sua authority permanecem nos mods consumidores.

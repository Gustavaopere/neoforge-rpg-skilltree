# Create: Design n' Decor — 2.2b

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81f3958cc5eb500319ba  
> Estado no momento da exportação: `Integrado ao Github`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods top-level  
> Exportado/reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** Create: Design n' Decor
- **Arquivo JAR:** `Design-n-Decor-1.21.1-2.2b.jar`
- **Versão 1.21.1:** `2.2b`
- **Categoria:** Visual; Tecnologia; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-design-n-decor/files/8156977
- **Função:** Addon de Create focado em decoração/QoL, com amplo registry de blocos, crushing-wheel/container variants, block entities e integração de mounted storage/stress com o framework de Create.
- **Dependências:** Source 2.2b: Minecraft 1.21.1, NeoForge >=21.1.200, Create 6.0.10-280, Flywheel 1.0.6, Ponder 1.0.82, Registrate MC1.21-1.3.0+67. Curios/Dye Depot aparecem em superfícies de desenvolvimento/integração e não são tratados aqui como hard dependencies sem metadata runtime.
- **Compatibilidade/Riscos:** Server crash é regression gate da build 2.2b. Riscos: stress double-processing, inventory dupe/loss em mounted storage/contraptions, config reload stale, BE lifecycle, version drift com Create e sobreposição visual com outros decor addons.
- **Sobreposição:** Sobreposição estética com Create Deco, Bells & Whistles, Copycats+ e outros addons não implica substituição. Create permanece authority de kinetics/stress/contraptions; o addon só registra seus blocos e adapters.
- **Observações:** DnDecorItems confirma `belt_connector` / Mechanical Belt (Full). DnDecorConfigs registra CLIENT/COMMON/SERVER e providers de BlockStressValues. Registry de blocos é extenso; IDs devem ser obtidos de DnDecorBlocks em vez de inferidos manualmente.
- **Procedência:** Runtime: modlist física canônica 08/09/2026 (595 top-levels). Release: CurseForge File ID 8156977. Source pin: branch oficial 1.21.1, cujo `gradle.properties` declara mod_version 2.2b; registries DnDecorBlocks/DnDecorItems/DnDecorBETypes/DnDecorMountedStorageTypes e configs auditados.
- **Histórico da decisão:** Ficha reconstruída em 08/09/2026 contra runtime 2.2b e source branch 1.21.1 compatível com a mesma versão.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — source 2.2b pinado, registries, mounted storage, stress/config lifecycle, server crash regression, risks e matriz de testes catalogados.
- **Data da última decisão:** 2026-09-08

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `Design-n-Decor-1.21.1-2.2b.jar` · mod id `dndecor` · versão `2.2b` · NeoForge 1.21.1.

## 1. Identidade e source pin
- **Mod:** Create: Design n' Decor.
- **JAR:** `Design-n-Decor-1.21.1-2.2b.jar`.
- **Mod id:** `dndecor`.
- **Versão:** `2.2b`.
- **Autores declarados no branch:** LopyLuna, DrMangoTea.
- **Source auditado:** branch oficial `1.21.1` de `DrMango14/Create-Design-n-Decor`; `gradle.properties` do branch também declara `mod_version=2.2b`, portanto o pin é compatível com o runtime instalado.

## 2. Papel no modpack
Addon de **Create** orientado a decoração/QoL. O projeto oficial descreve variantes de blocos existentes, novos crushing wheels, containers e blocos adicionais alinhados à estética/sistemas de Create.

Ele expande a superfície visual e alguns blocos funcionais, mas **Create continua authority do kinetic network, stress, speed e contraption framework**.

## 3. Dependências e baseline técnico
O source `2.2b` declara:
- Minecraft `1.21.1`;
- NeoForge mínimo `21.1.200`;
- Create `6.0.10-280`;
- Flywheel `1.0.6`;
- Ponder `1.0.82`;
- Registrate `MC1.21-1.3.0+67`.

Também há dependências de desenvolvimento/integração no source para Curios e Dye Depot; presença no buildscript não deve ser convertida automaticamente em hard dependency runtime sem metadata correspondente.

## 4. Registries confirmados
O branch exato contém registries dedicados para:
- `DnDecorBlocks` — catálogo principal de blocos, grande e data-generating;
- `DnDecorItems` — itens não-bloco;
- `DnDecorBETypes` — block entities;
- `DnDecorMountedStorageTypes` e `DnDecorInventoryIdentifiers` — integração de storage com o ecossistema Create;
- creative tabs, configs, shapes e metal types.

### Item não-bloco confirmado
`DnDecorItems` registra:
- **`belt_connector`**, exibido como **Mechanical Belt (Full)**; a recipe converte quatro `AllItems.BELT_CONNECTOR` em quatro unidades.

### Escopo de blocos
O registry de blocos é muito amplo (arquivo `DnDecorBlocks.java` >100 KB). A documentação oficial confirma famílias como **crushing wheels, containers e variantes decorativas**. Esta ficha não inventa uma lista manual de centenas de IDs; para integração por ID, `DnDecorBlocks` no branch `1.21.1` é a authority de enumeração.

## 5. Storage e contraptions
A presença explícita de mounted-storage registration mostra que certos containers do addon entram no modelo de armazenamento móvel de Create. Qualquer mod próprio que leia inventário em contraptions deve consultar o storage abstraction de Create/addon, não assumir que todo container é um `Container` vanilla estático.

Riscos centrais: montagem/desmontagem, save/load de conteúdo, duplicação em contraption assembly e storage state stale.

## 6. Kinetics / stress
`DnDecorConfigs` integra **`BlockStressValues`** de Create e registra providers de **impact** e **capacity** a partir de config server-side (`DStress`).

Portanto, valores cinéticos do addon pertencem ao pipeline de stress de Create. Não recalcular stress em handlers externos.

## 7. Configuração
O source registra três camadas de configuração:
- **CLIENT** (`DClient`);
- **COMMON** (`DCommon`);
- **SERVER** (`DServer`).

O registry escuta `ModConfigEvent.Loading` e `ModConfigEvent.Reloading`, propagando `onLoad()`/`onReload()` aos objetos de config. Valores de stress são fornecidos pela config server.

## 8. Client / Server
- Config e state cinético que afetam gameplay precisam obedecer server/common authority.
- Models, render e presentation decorativa são client-side.
- O changelog da build física `2.2b` registra **“Server Crash Fix”**, logo dedicated-server boot é regression gate desta versão.

## 9. Lifecycle
Validar:
- config load/reload;
- block entity load/unload;
- chunk unload/reload;
- contraption assemble/disassemble;
- world restart;
- mounted storage persistindo conteúdo;
- mudanças de stress config sem cache stale;
- resource/model reload no cliente.

## 10. Multiplayer
Containers/blocos funcionais devem permanecer server-authoritative. Interações simultâneas com o mesmo container não podem duplicar item/state. Visualização não deve alterar inventory state localmente.

## 11. Integrações concretas no pack
- **Create 6.0.10:** dependência estrutural e authority cinética.
- **Flywheel/Ponder/Registrate:** infraestrutura alinhada ao baseline do Create branch.
- **Create: Copycats+, Create Deco, Bells & Whistles e outros decor addons:** sobreposição estética possível, sem equivalência funcional automática.
- **Dye Depot:** source possui superfície de integração de cores; não assumir que todos os blocos exigem o mod em runtime.

## 12. Riscos
1. server crash/classloading — build 2.2b contém fix específico;
2. stress duplicado por provider externo;
3. inventory dupe/loss em mounted storage;
4. block entity state perdido em contraption/chunk lifecycle;
5. config reload sem invalidar valor derivado;
6. model/render overlap com outros decor mods;
7. version drift com Create/Flywheel/Ponder;
8. confundir dependência de desenvolvimento opcional com hard dependency runtime.

## 13. Matriz de testes
1. Dedicated server boot com `2.2b`.
2. Client join e render dos blocos.
3. Colocar/quebrar containers e verificar drops.
4. Inserir itens e reiniciar o mundo.
5. Assemble/disassemble de contraption contendo storage do addon.
6. Verificar exatamente uma cópia do inventário após movimento.
7. Kinetic stress/capacity antes e depois de config reload.
8. Testar crushing-wheel variants em rede Create sem stress double-count.
9. Multiplayer simultâneo em container.
10. Resource reload e reconexão.

**Matriz documental; testes não foram executados nesta etapa.**

## 14. Evidências
- modlist física canônica de 08/09/2026;
- CurseForge oficial, File ID **8156977**, release `2.2b` e changelog “Server Crash Fix”;
- source oficial branch `1.21.1`, `gradle.properties` `mod_version=2.2b`;
- `DnDecorItems`, `DnDecorConfigs` e estrutura de registries do branch exato.

> **Boundary canônico:** Design n' Decor possui seus blocos/BE/storage adapters, enquanto **Create continua dono do kinetic/contraption contract**.

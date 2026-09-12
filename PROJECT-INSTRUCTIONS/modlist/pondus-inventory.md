# Pondus Inventory

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db810797defd55fb0766bb
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `pondus_inventory_fi-0.15-Beta.jar`, mod id `pondus_inventory_fi`, runtime `0.15-Beta`; Sable Companion 1.5.0 embutido no host; Sable 2.0.5 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Pondus Inventory 0.15-Beta e Sable 2.0.5 estão presentes; Sable Companion 1.5.0 permanece embutido no JAR do host. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Pondus Inventory
- **Arquivo JAR:** `pondus_inventory_fi-0.15-Beta.jar`
- **Versão 1.21.1:** 0.15-Beta
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Compat
- **Função:** Addon de física para Sable que faz itens, blocos e fluidos armazenados em inventários de ships/sublevels contribuírem para a massa física total da estrutura.
- **Dependências:** Sable 2.0.5. Fluid support depende de containers que exponham NeoForge IFluidHandler. Sable Companion 1.5.0 está embedded no JAR e permanece sob o host.
- **Sobreposição:** Complementa Sable adicionando massa de cargo/inventários; não cria sistema de armazenamento nem physics engine independente.
- **Compatibilidade/Riscos:** Publicação é Release apesar do runtime/filename `0.15-Beta`. Riscos: cobertura/config de massas, capability de fluids, double counting, cache stale, performance de inventories grandes e necessidade de ajustes manuais para outros mods.
- **Observações:** CurseForge project 1528664: main file 0.15-Beta é Type Release para NeoForge 1.21.1 em 04/07/2026. O mod soma massa de blocks/items/fluids e conteúdo de containers à física Sable.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Pondus Inventory + documentação publicada de massa/config/capabilities + hierarquia física do Sable Companion embedded.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/pondus-inventory-sable
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Pondus Inventory 0.15-Beta reconstruído: Release-channel mismatch, mass model de blocks/items/fluids/containers, IFluidHandler, config/cache/sync, Sable Companion embedded, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `pondus_inventory_fi-0.15-Beta.jar`, mod id `pondus_inventory_fi`, versão `0.15-Beta`, NeoForge 1.21.1. Apesar do sufixo `Beta` no filename/runtime, a publicação atual é classificada **Release** no CurseForge. O mod é addon de Sable que inclui massa de blocos, itens e fluidos — inclusive conteúdo de containers — nos cálculos físicos da ship. O JAR embute Sable Companion 1.5.0 sob o host.

## 1. Identidade e papel
- **Mod:** Pondus Inventory [Sable].
- **JAR físico:** `pondus_inventory_fi-0.15-Beta.jar`.
- **Mod id:** `pondus_inventory_fi`.
- **Runtime:** `0.15-Beta`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** CatCosYT / Fractal Interactive ecosystem.
- **Licença:** MIT.
- **Ambiente:** Client & Server.
- **Papel:** integrar massa de inventários/containers/blocos/fluidos ao cálculo físico de ships Sable.
- **Decisão:** Sem decisão.

## 2. Canal: filename Beta versus publicação Release
A authority física mantém literalmente `0.15-Beta`; isso não deve ser “corrigido”. Paralelamente, o CurseForge publica esse mesmo artefato como **Release** em 04/07/2026.

Regra de catálogo:
- `Versão 1.21.1` conserva `0.15-Beta`;
- status registra o canal Release da publicação;
- risco de maturidade é avaliado pelo comportamento/documentação, não só pelo nome do arquivo.

## 3. Dependência de Sable
A página oficial declara **Sable** como requisito funcional (“You need Sable. That's it.”). O pack possui Sable 2.0.5.

Ownership:
- Sable → física, ships/SubLevels e mass solver;
- Pondus → cálculo/configuração de massa do conteúdo e sua contribuição ao sistema;
- container/provider → inventory/fluid capability real.

Sem Sable, o addon perde seu alvo funcional.

## 4. Massa de blocos, itens e fluidos
A documentação oficial descreve um sistema detalhado para definir massa de:
- blocos;
- itens;
- fluidos.

O valor pode ser configurado por namespace/entry e escalado por modificadores globais. Isso permite ajustar mods diferentes sem exigir que todos publiquem o mesmo modelo de massa.

## 5. Conteúdo dos containers
Pondus olha dentro de containers como chests, barrels, fluid tanks e outros inventories e soma o conteúdo àquilo que sustenta a massa.

Resultado pretendido: uma ship fica mais pesada quando recebe carga real.

Regression gates:
- insert/extract atualiza massa;
- mover stack entre slots não duplica contribuição;
- destruir container não subtrai duas vezes;
- unload/reload não perde cache.

## 6. Fluid support e IFluidHandler
A documentação afirma que suporte a fluidos depende de blocos/mods que implementem a capability NeoForge **`IFluidHandler`**.

Isso é especialmente importante em um pack Create-heavy com vários tanks/fluids.

Testar por implementação concreta; não assumir que todo tanque visual expõe a capability no mesmo lado/contexto.

## 7. Configuração por namespace
Configs ficam sob `config/pondus_inventory_fi/<mod_namespace>/` segundo a documentação publicada. O sistema inclui arquivos/regras como:
- modificadores globais;
- massas de blocos;
- massas de itens;
- massas de fluidos;
- multiplicador de inventário/container.

A auditoria não leu os arquivos locais, portanto não declara massas atuais do pack.

## 8. Modificadores globais
A lineage publica modificadores separados para block, item e fluid mass. Isso permite recalibrar a física global sem editar cada entry.

Risco: multiplicador extremo muda radicalmente balanceamento de ships e propulsão. Toda alteração deve ser testada com uma ship de massa conhecida e carga incremental reproduzível.

## 9. Runtime reload e cache
A documentação de integração menciona reload de config, limpeza de `InventoryMassCache` e `/pondus sync` após mudanças runtime.

Isso mostra que cache/sincronização fazem parte do lifecycle operacional.

Testar:
- alteração de config;
- invalidate/sync;
- reconnect de cliente;
- ship já montada;
- mass reading antes/depois.

Não editar config live sem procedimento de invalidation.

## 10. Linha histórica relevante
A lineage da série registra mudanças diretamente relacionadas à modelagem de massa:
- suporte a liquids em FluidTank;
- config de massa de líquido por bucket;
- correção de hopper mass registration;
- fix de item removido duas vezes ao destruir bloco;
- modificadores globais separados para items/blocks/fluids;
- comando para inventory weight modifier.

Essas notas são regression gates herdados, não claims de novos deltas exclusivos da 0.15 quando não publicados assim.

## 11. Embedded Sable Companion
O JAR físico embute `sable-companion-common-1.21.1-1.5.0.jar`, mod id `sablecompanion`, versão `1.5.0`.

Ele pertence ao host Pondus nesta hierarquia:
- não recebe ordinal/página top-level;
- não deve ser atualizado isoladamente;
- resolução/versioning devem ser testados junto do addon.

## 12. Integração com o pack
Superfícies importantes:
- Create inventories/tanks;
- carga de materiais pesados/leve;
- fluidos de addons tecnológicos;
- containers em SubLevels;
- assembly/disassembly;
- propulsion/engine sizing.

Pondus não cria armazenamento; apenas torna a carga relevante à física.

## 13. Client/server e autoridade
Massa funcional precisa ser server-authoritative. Cliente pode mostrar feedback, mas não pode escolher uma massa diferente da usada pela física.

Multiplayer deve verificar:
- insert por um player observado por outro;
- sync da massa;
- reconnect;
- chunk/ship unload;
- config mismatch.

## 14. Riscos
1. **Config coverage:** mods sem entry adequada podem receber massa errada/default.
2. **Capability coverage:** tank sem IFluidHandler esperado pode não contribuir corretamente.
3. **Double counting:** block base + inventory/content calculados duas vezes.
4. **Cache stale:** cargo muda e massa não atualiza.
5. **Extreme multipliers:** física/propulsion fica desbalanceada.
6. **Container destruction:** regressão histórica de remoção dupla.
7. **Performance:** inventories grandes/complexos podem tornar mass recalculation caro.
8. **Embedded dependency:** Sable Companion interno não deve virar top-level.
9. **Active development:** autor avisa que integrações com outros mods podem exigir ajustes manuais.

## 15. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Pondus 0.15-Beta + Sable 2.0.5.
- [ ] Ship vazia tem baseline de massa reproduzível.
- [ ] Inserir stack pesado aumenta massa uma vez; retirar restaura baseline.
- [ ] Chest/barrel cheio soma conteúdo corretamente.
- [ ] Fluid tank com IFluidHandler muda massa proporcionalmente ao volume/config.
- [ ] Hopper/container destroy não duplica/subtrai duas vezes.
- [ ] `/pondus sync` após config reload converge cliente/servidor.
- [ ] Chunk/ship unload/reload preserva massa sem cache stale.
- [ ] Create/modded containers representativos são auditados individualmente.
- [ ] Embedded Sable Companion resolve sem entrada top-level duplicada.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física: `pondus_inventory_fi-0.15-Beta.jar`, mod id/runtime e Sable Companion 1.5.0 embedded.
- CurseForge oficial: project 1528664, main file 0.15-Beta, **Type Release**, NeoForge 1.21.1, 04/07/2026, Client & Server, MIT.
- Descrição oficial: massa de blocks/items/fluids, containers, fluid tanks, config por namespace, global modifiers e Sable requirement.
- Lineage publicada: FluidTank/liquid mass, hopper fix, double-removal fix e modifiers separados.
- **Limite:** configs físicos de massa e cobertura de cada mod/container da instância não foram lidos; números efetivos não foram inventados.

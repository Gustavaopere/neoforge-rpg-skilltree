# Productive Metalworks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812eae78de7aeb315383
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `productivemetalworks-1.21.1-1.15.1.jar`, mod id `productivemetalworks`, runtime `1.21.1-1.15.1`; Productive Metalworks KubeJS Addon 1.0.0 presente; Productive Lib 0.2.0, Flywheel 1.0.4-30 e Ponder 1.0.81 embutidos
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Productive Metalworks 1.15.1 e seu addon KubeJS 1.0.0 estão presentes; as bibliotecas listadas permanecem embutidas sob o host. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Productive Metalworks
- **Arquivo JAR:** `productivemetalworks-1.21.1-1.15.1.jar`
- **Versão 1.21.1:** 1.21.1-1.15.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Metalurgia, Tecnologia, Automação
- **Função:** Sistema de fundição/casting com foundry multiblock, metais líquidos e processamento de metais em escala industrial.
- **Dependências:** NeoForge 1.21.1. Consumer operacional confirmado e mantido: Productive Metalworks KubeJS Addon 1.0.0. JAR embute Productive Lib 0.2.0, Flywheel 1.0.4-30 e Ponder 1.0.81 sob o host.
- **Sobreposição:** Sobreposição funcional parcial com Create Metallurgy/TFMG e outras rotas; não há equivalência integral. Classificação Dependência deriva do addon mantido.
- **Compatibilidade/Riscos:** Foundry/casting com sobreposição parcial a outras metalurgias. Riscos: recipe/API drift, dupes, capability invalidation, chunk/multiblock lifecycle, FE/fuel balance, block-render regression e scripts KubeJS. Remover provider quebra addon mantido.
- **Observações:** Runtime 1.21.1-1.15.1, Release 02/08/2026. Delta exato: fix de crash ao renderizar na foundry/casting table blocos que não podem ser renderizados sem level.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Productive Metalworks 1.15.1 + addon KubeJS físico/oficial + hierarquia embedded.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/productive-metalworks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Productive Metalworks 1.15.1 reconstruído e reclassificado como Dependência: foundry/melting/casting/alloying, KubeJS consumer mantido, embedded libs, lifecycle, dupes, riscos e testes.
- **Histórico da decisão:** 2026-08-26 — antigo motivo de revisão invalidado; permaneceu instalado/sem decisão. 2026-09-10 — reclassificado de Sem decisão para Dependência porque Productive Metalworks KubeJS Addon 1.0.0 está instalado e com decisão Manter, exigindo este provider.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `productivemetalworks-1.21.1-1.15.1.jar`, mod id `productivemetalworks`, versão `1.21.1-1.15.1`, NeoForge 1.21.1. Productive Metalworks fornece foundry multiblock, metais líquidos, melting, casting e alloying. O pack mantém `productive-metalworks-kubejs-addon-1.0.0.jar` com decisão **Manter**; portanto o provider passa de `Sem decisão` para **Dependência**.

## 1. Identidade e papel
- **Mod:** Productive Metalworks.
- **JAR:** `productivemetalworks-1.21.1-1.15.1.jar`.
- **Mod id:** `productivemetalworks`.
- **Runtime:** `1.21.1-1.15.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** All Rights Reserved.
- **Papel:** foundry/smeltery multiblock para fundir, armazenar e moldar metais líquidos.
- **Decisão:** Dependência.

## 2. Causalidade da dependência
O addon `productive-metalworks-kubejs-addon-1.0.0.jar` está instalado e sua decisão é **Manter**. Esse addon existe especificamente para registrar/remover recipes e fuels do Productive Metalworks.

Consequência: remover Productive Metalworks enquanto o addon permanece quebraria o provider esperado e tornaria a decisão Manter incoerente. A dependência é operacional, não mera semelhança temática.

## 3. Foundry multiblock
O projeto adiciona uma foundry configurável construída com componentes como bricks, tanks, drains e windows. A estrutura pode variar em tamanho/layout e possui opções visuais de cores.

Testar formação, invalidação ao remover bloco, reconstrução e persistência após chunk unload/reload.

## 4. Melting e metais líquidos
Inputs metálicos podem ser derretidos em fluidos/molten metals. A foundry pode processar múltiplos materiais e o projeto se posiciona como processamento eficiente/industrial.

Ownership de recipe e quantidade permanece no provider/datapack. Não assumir equivalência 1:1 com TFC/Create Metallurgy sem recipe audit.

## 5. Casting
Molten metal pode ser convertido em ingots, blocks e outros itens por casting tables/basins/casts conforme recipes. A lineage inclui correções para capability invalidation, fluid containers e extração de casts durante operação.

Esses pontos são regression gates para automação e inventários externos.

## 6. Alloying
A linha suporta ligas a partir de múltiplos molten fluids. Releases anteriores corrigiram velocidade de alloying e duplicações específicas. O teste precisa verificar proporção, consumo e output, especialmente quando KubeJS adiciona/edita recipes.

## 7. Tanks, transferência e upgrades
A lineage 1.13.x registra mudanças de tank column fill, transfer rate, tank tick config e speed upgrades afetando alloying. Isso demonstra que throughput/config fazem parte do sistema operacional.

Não inventar valores locais: configs da instância não foram lidos.

## 8. Energia FE e aquecimento
A linha adicionou powered heating coils e foundry capacitors para FE. Esse caminho pode cruzar Create/energia do pack, mas não transforma Productive Metalworks em sistema elétrico geral.

Testar energia recebida, aquecimento, falta de energia e transição entre fuels/energia quando aplicável.

## 9. Create e JEI
A lineage registra suporte a Create e visualização de entity melting no JEI. Além disso, o JAR físico embute Flywheel 1.0.4-30 e Ponder 1.0.81+mc1.21.1.

Create compat não significa que Productive Metalworks substitua Create Metallurgy; há apenas integração/sobreposição parcial de rotas.

## 10. Productive Lib embedded
O JAR embute `productivelib-1.21.1-0.2.0.jar`; não há Productive Lib top-level na modlist atual. Esse artefato permanece sob o host e não recebe ordinal/página própria.

Também estão embedded Flywheel e Ponder. Atualização isolada desses jars internos não deve ser feita sem suporte do host.

## 11. Release 1.15.1
A release física 1.15.1 é a atual para Minecraft 1.21.1 e foi publicada em 02/08/2026.

Delta exato: **corrige crash ao renderizar, na foundry ou casting table, blocos que não conseguem ser renderizados sem um level**.

Regression gate: recipes/casts que referenciem block rendering não devem reproduzir esse crash.

## 12. Histórico de dupes e lifecycle
A lineage inclui correções para:
- item removido duas vezes ao destruir bloco;
- `foundryCoolingModifier` dupe;
- Menril dupe;
- slots após chunk reload;
- client lag com itens não derretíveis;
- dedicated server crash.

Essas notas justificam testes específicos de save/reload, break/rebuild e automação, sem afirmar que os bugs continuam presentes.

## 13. Sobreposição metalúrgica
O pack possui Create Metallurgy, TFMG e outras rotas. Productive Metalworks oferece foundry/casting próprio e não contém o sistema completo de ferramentas/armas de Tinkers Construct.

Curadoria deve comparar recipes, ligas, fuels, automação e estética feature-by-feature. A atual classificação Dependência vem do addon mantido, não de uma conclusão de que o sistema é insubstituível em abstrato.

## 14. Client/server e multiplayer
Recipes, inventories, molten fluids, energy e multiblock state precisam ser server-authoritative. Clientes devem receber render/JEI sem controlar resultado funcional.

Testar dois players interagindo, chunk boundaries, reconnect e restart.

## 15. Riscos
1. Remoção quebraria o addon KubeJS mantido.
2. Recipe overlap com Create Metallurgy/TFMG.
3. Dupes/regressões de casting/alloying/inventory.
4. Capability invalidation em tables/basins.
5. Chunk reload/multiblock state.
6. Render de blocks sem level — fix específico 1.15.1.
7. FE/fuel balance e throughput.
8. Embedded libraries confundidas com top-level.
9. KubeJS scripts divergindo da API após update.

## 16. Matriz de testes
- [ ] Dedicated server e cliente iniciam com PMW 1.15.1 + addon KubeJS 1.0.0.
- [ ] Foundry forma, invalida e reforma corretamente.
- [ ] Melting consome input uma vez e gera volume esperado.
- [ ] Item/block casting consome fluido/cast corretamente.
- [ ] Alloying respeita proporções e speed upgrades.
- [ ] Chunk unload/reload preserva slots, tanks e multiblock state.
- [ ] Break/rebuild não reproduz double-removal/dupe históricos.
- [ ] Powered heating/FE, se usado, mantém balanço correto.
- [ ] JEI e recipes KubeJS convergem ao state server-side.
- [ ] Block rendering em foundry/casting table não reproduz crash corrigido da 1.15.1.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- Modlist física: PMW 1.15.1; embedded Flywheel, Ponder e Productive Lib; addon KubeJS 1.0.0 presente.
- CurseForge oficial: project 1184570, Release NeoForge 1.21.1 1.15.1 de 02/08/2026, Client & Server.
- Changelog 1.15.1 e lineage anterior para multiblock/casting/alloying/capability/dupe/lifecycle.
- **Limite:** configs e recipes locais efetivos não foram enumerados; sobreposição com outras metalurgias não foi convertida em incompatibilidade sem evidência.

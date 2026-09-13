# Create: The Factory Must Grow

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db81579dcccfc1a893330f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Create: The Factory Must Grow
- **Arquivo JAR:** `tfmg-1.21.1-1.2.4b-community.jar`
- **Versão 1.21.1:** 1.2.4b-community
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** TFMG Community Edition: grande expansão dieselpunk/heavy engineering para Create com petróleo e derivados, distillation, coke/blast furnaces, metalurgia, eletricidade própria, engines, electrolysis e equipamentos industriais.
- **Dependências:** Create é base funcional; runtime físico Create 6.0.10. Sable 2.0.5 está presente e é integração de alto risco porque 1.2.4b corrige um dupe explicitamente relacionado ao Sable. NeoForge 1.21.1.
- **Sobreposição:** Compartilha petróleo, combustíveis e geração/uso de energia com outros addons Create, porém implementa sua própria cadeia de heavy engineering e não é apenas um recipe pack.
- **Compatibilidade/Riscos:** Community fork com features experimentais. 1.2.4b corrige electric network updates, Blast Furnace, Distillation heat/quantidades e dupe Sable. Known issues: Engines sem Ponders; Vats/Tanks diferentes conectam; Cable Connector quebrado não desconecta totalmente outra ponta.
- **Observações:** mod id `tfmg`; runtime 1.2.4b-community. A antiga observação de que a build comunitária não estava em releases públicas foi supersedida: há release pública exata da Community Edition em 24/08/2026.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Create: TFMG Community Edition 1.2.4b File ID 8723583 + Create 6.0.10 e Sable 2.0.5 físicos. Dossiê de 09/09 preservado; nenhum processo industrial foi testado em runtime.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/tfmg-community-edition/files/8723583 ; https://github.com/DrMango14/Create-The_Factory_Must_Grow
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — TFMG Community Edition 1.2.4b permanece exatamente instalada e segue latest release pública 1.21.1; oil/distillation, metalurgia, eletricidade/engines, Sable dupe fix, known issues, lifecycle, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `tfmg-1.21.1-1.2.4b-community.jar`, mod id `tfmg`, versão `1.2.4b-community`. Esta build é a **Create: TFMG Community Edition**, fork público que continua o sistema de heavy engineering/oil do TFMG e prioriza correções de bugs. O binário instalado corresponde a uma release pública oficial da Community Edition de 24/08/2026.

## 1. Identidade, versão e provenance
- **Mod:** Create: The Factory Must Grow.
- **Distribuição instalada:** TFMG Community Edition.
- **JAR:** `tfmg-1.21.1-1.2.4b-community.jar`.
- **Mod id:** `tfmg`.
- **Versão:** `1.2.4b-community`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Stack físico:** Create `6.0.10`; Sable `2.0.5` presente.

## 2. Relação com upstream e authority
A Community Edition é fork do Create: TFMG original por DrMangoTea e declara objetivo de corrigir bugs e eventualmente convergir mudanças upstream.
Para esta ficha:
- **versão/changelog exatos:** Community Edition 1.2.4b;
- **arquitetura/content families:** source 1.21.1 do TFMG original, quando compatível conceitualmente;
- qualquer detalhe não confirmado no fork/build exata é tratado como arquitetura upstream, não prova automática do binário.
Create continua authority de rotação/stress/contraptions base; TFMG é authority de sua heavy engineering, petróleo, eletricidade, metais e máquinas.

## 3. Escopo industrial confirmado
O upstream 1.21.1 documenta como famílias principais:
- Large Distilleries;
- realistic electricity;
- Steel Mills;
- Concrete;
- Electrolyzers;
- Steel, Aluminum, Cast Iron, Lead e Sulfur;
- petróleo e derivados;
- Quad Potato Cannon, flamethrowers e equipamentos associados.
O objetivo declarado é expandir Create do steam/clockpunk para uma camada dieselpunk/industrial pesada.

## 4. Petróleo e cadeia de fluidos
O registry 1.21.1 confirma fluidos/gases industriais como:
- Crude Oil e Heavy Oil;
- Gasoline, Diesel, Naphtha e Kerosene;
- Creosote;
- Hydrogen, Furnace Gas, Ethylene, Propylene, CO2, Air e Hot Air, entre outros.
Esses recursos alimentam recipes e máquinas específicas. A presença no registry não implica que toda rota esteja obrigatoriamente habilitada/atingível na configuração atual; JEI/recipes runtime são autoridade operacional.

## 5. Distillation
TFMG possui recipe type de **Distillation** e sistema de Distillation Tower/Controller.
Na exata 1.2.4b Community:
- corrigido controller não detectando heat corretamente;
- quantidade drenada passa a corresponder ao input do recipe;
- quantidade preenchida corresponde ao result;
- foi adicionado delay entre processos conforme heat level do tank.
Esses quatro pontos exigem teste quantitativo de conservação de fluidos.

## 6. Coke Oven, Blast Furnace e metalurgia
O source confirma Coke Oven e Blast Furnace/industrial metallurgy. Config upstream inclui tamanho máximo de Coke Oven, altura máxima de Blast Furnace e parâmetros de fuel/speed.
A 1.2.4b corrige **Blast Furnace não conseguindo finalizar recipes**. Portanto recipe completion, consumo de combustível e output correto são regression gates da build física.

## 7. Eletricidade própria
TFMG implementa rede elétrica própria além da rotação Create. Source/config confirma componentes como generators, cable connectors, electric motor, accumulator e parâmetros de voltage/current/resistance.
Na 1.2.4b, `IElectric` passou a atualizar quando o power state muda, corrigindo especialmente **Creative Generators que não atualizavam a network**.
Electricidade TFMG não deve ser confundida automaticamente com FE/RF; bridges/converters precisam ser catalogadas/testadas separadamente.

## 8. Motores e conversão para rotação
O sistema de engines expõe informações como RPM, torque, efficiency, fuel consumption, injection rate, length e engine type. O TFMG usa sua cadeia de combustíveis para produzir trabalho dentro do ecossistema Create.
A 1.2.4b muda assembly por clique direito: engines não devem mais montar completamente ao clicar com qualquer item fora de Creative.

## 9. Electrolysis e processos especiais
O upstream registra recipe/process types como:
- Coking;
- Distillation;
- Air Blasting / Industrial Blasting;
- Polarizing;
- Winding;
- Electrolysis.
Esses processos formam uma cadeia industrial própria e precisam ser verificados no JEI/Ponder da Community Edition, pois recipes e UX podem divergir do upstream original.

## 10. Armas, hazards e materiais industriais
O source confirma conteúdo perigoso/industrial como flamethrowers, napalm/thermite/grenades, pipe bombs, acid/hellfire contexts e materiais pesados.
Isso aumenta a superfície de integração com damage systems, claims e outros combat mods. A ficha não enumera dano/recipes específicos sem runtime/source pin exato do fork.

## 11. Configuração
A arquitetura upstream possui config extensa de machines, incluindo exemplos como:
- electric motor internal resistance;
- Forge Energy conversion voltage;
- electrolysis minimum current;
- engine max length;
- scanner depth;
- generator power/min speed;
- Blast Furnace dimensions/consumption;
- Coke Oven max size.
A configuração física do pack não foi lida. Não assumir defaults upstream como valores atuais do usuário.

## 12. Release 1.2.4b Community — fixes exatos
Bug fixes publicados:
- `IElectric` atualiza com mudança de power state;
- Blast Furnace finaliza recipes;
- Distillation Controller detecta heat;
- nomes das texturas Layered Galena corrigidos;
- **duplication bug relacionado ao Sable corrigido**;
- engines não montam integralmente por right-click comum fora de Creative.
Changes:
- distillation conserva input/output conforme recipe e ganhou delay por heat;
- Ponder adicionado para Winding Machine.

## 13. Known issues oficiais da 1.2.4b
A release lista explicitamente:
- Engines ainda **não têm Ponders**;
- Vats e Tanks de tipos diferentes podem conectar entre si;
- quebrar um Cable Connector não desconecta completamente a outra ponta.
Esses itens são limitações conhecidas da build, não hipóteses locais.
O release também avisa que nem todos os bugs estão corrigidos e que algumas adições novas são experimentais/sujeitas a mudança.

## 14. Client / server e lifecycle
Validar:
- dedicated server boot;
- network elétrica após place/break/restart;
- assemble/disassemble de engines;
- distillation e blast furnace atravessando save/restart;
- tanks/vats cheios durante unload;
- recipes parciais após chunk unload;
- contraptions Create/Sable contendo componentes suportados;
- resource reload/model state para Layered Galena.

## 15. Integrações concretas no pack
- **Create 6.0.10:** base obrigatória funcional do addon.
- **Sable 2.0.5:** presente; 1.2.4b corrige um dupe explicitamente ligado ao Sable, portanto regressão obrigatória.
- **Create: New Age / Crafts & Additions / Power Grid / Flux Networks:** coexistem no domínio elétrico/energético, mas não compartilham automaticamente voltage/network semantics; bridges precisam ser explícitas.
- **Create Metallurgy/Metalwork:** sobreposição parcial de metalurgia; comparar recipes/material ownership para evitar loops/duplicates.
- **JEI/Ponder:** essenciais para validar recipe graph da Community Edition; known gaps de Ponder permanecem.

## 16. Riscos técnicos
1. **Community-fork drift:** upstream original e fork podem divergir em recipes/registries.
2. **Experimental features:** release avisa que parte das adições pode mudar.
3. **Sable dupe:** corrigido em 1.2.4b, mas é regression gate crítico.
4. **Electrical stale network:** Cable Connector conhecido pode deixar endpoint conectado.
5. **Cross-energy ambiguity:** FE/rotational/electricity TFMG não devem ser tratados como sistema único sem bridge.
6. **Fluid conservation:** distillation recebeu correções quantitativas; testar input/output exatamente.
7. **Vat/tank cross-type connection:** known issue pode permitir layouts inválidos.
8. **Heavy machine chunk lifecycle:** multiblocks e partial recipes precisam sobreviver a unload/restart.

## 17. Matriz de testes
- [ ] Dedicated server boot com TFMG CE 1.2.4b + Create 6.0.10 + Sable 2.0.5.
- [ ] Distillation Controller reconhece heat e conserva quantidades do recipe.
- [ ] Blast Furnace finaliza recipe e entrega output único.
- [ ] Creative Generator atualiza rede ao mudar power state.
- [ ] Cable Connector break evidencia/valida known issue sem corrupção adicional.
- [ ] Vat/Tank de tipos diferentes é tratado conforme known issue.
- [ ] Engine assembly por right-click só ocorre no contexto esperado.
- [ ] Engine save/restart mantém fuel/state sem energia infinita/duplicação.
- [ ] Sable interaction não reproduz dupe corrigido.
- [ ] Winding Machine Ponder abre; Engines continuam sem Ponder conforme known issue.
- [ ] JEI recipes de coking/distillation/blasting/polarizing/winding/electrolysis são executáveis.
- [ ] Bridges energéticas instaladas não criam loop de energia.
Nenhum teste foi marcado como aprovado nesta auditoria.

## 18. Evidências
- Modlist física canônica 08/09/2026: JAR, Create 6.0.10 e Sable 2.0.5.
- CurseForge oficial TFMG Community Edition: fork público e release exata 1.2.4b de 24/08/2026.
- Changelog Community 1.2.4b: fixes, distillation changes, Ponder e known issues.
- Source oficial TFMG branch 1.21.1: famílias de features, fluids/gases, recipes, blocks e machine config; usado como arquitetura upstream, não como commit pin do fork.

## 19. Revalidação física — 11/09/2026
A modlist atual mantém exatamente `tfmg-1.21.1-1.2.4b-community.jar`, mod id `tfmg`, runtime `1.2.4b-community`, com Create `6.0.10` e Sable `2.0.5`. A página oficial da Community Edition continua apontando **1.2.4b** como latest release NeoForge 1.21.1.
Os fixes de distillation, Blast Furnace, electric network update e dupe relacionado ao Sable continuam regression gates da build. Os known issues oficiais permanecem documentados; nenhum processo, rede elétrica ou multiblock foi executado nesta recatalogação.

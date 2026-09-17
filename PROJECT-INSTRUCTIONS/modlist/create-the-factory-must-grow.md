# Create: The Factory Must Grow

## Propriedades do registro

- **Mod:** Create: The Factory Must Grow
- **Arquivo JAR:** tfmg-1.21.1-1.3.1-community.jar
- **Versão 1.21.1:** 1.3.1-community
- **Categoria:** Tecnologia; Automação
- **Função:** TFMG Community Edition: grande expansão dieselpunk/heavy engineering para Create com petróleo e derivados, distillation, coke/blast furnaces, metalurgia, eletricidade própria, engines, electrolysis e equipamentos industriais.
- **Dependências:** Create 6.0.10 é a base funcional; Sable 2.0.5 está presente. NeoForge 1.21.1. Create Liquid Fuels: Reburned é integração opcional: 1.3.1 corrige data generation quando ele está ausente.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Community fork com features experimentais. Gates atuais incluem Coke Oven/Blast Furnace, distillation/fluid conservation, electric network, Sable, Oil Reserves/worldgen 1.3.0, Vats/Tanks cross-type e Cable Connector disconnect não confirmados como resolvidos. 1.3.1 corrige Coke Oven multiblocks e data generation sem CLF Reburned.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/tfmg-community-edition/files/8723583 ; https://github.com/DrMango14/Create-The_Factory_Must_Grow
- **Procedência:** modlist física de 17/09/2026 + releases/changelogs oficiais TFMG Community Edition 1.2.4b, 1.3.0 e 1.3.1 + Create 6.0.10 e Sable 2.0.5 físicos.
- **Observações:** mod id `tfmg`; runtime físico 1.3.1-community. A 1.3.0 adicionou Ponders para engines, supersedindo o antigo known issue de ausência de Ponders. 1.3.1 traz manutenção de Coke Oven, light bulbs, Multimeter/Cable Connectors e data generation.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 17/09/2026 — TFMG Community Edition atualizado para 1.3.1-community; deltas 1.3.0/1.3.1 incorporados; Ponders de engines marcados como resolvidos e demais known issues mantidos fail-closed quando não houve fix explícito.
- **Decisão:** Sem decisão
- **Histórico da decisão:** 
- **Sobreposição:** Compartilha petróleo, combustíveis e geração/uso de energia com outros addons Create, porém implementa sua própria cadeia de heavy engineering e não é apenas um recipe pack.

> **ESCOPO CANÔNICO.** Runtime físico: `tfmg-1.21.1-1.3.1-community.jar`, mod id `tfmg`, versão `1.3.1-community`. Esta build é a **Create: TFMG Community Edition**, fork público que continua o sistema de heavy engineering/oil do TFMG e prioriza correções de bugs. O binário instalado corresponde a uma release pública oficial da Community Edition de 24/08/2026.
## 1. Identidade, versão e provenance
- **Mod:** Create: The Factory Must Grow.
- **Distribuição instalada:** TFMG Community Edition.
- **JAR:** `tfmg-1.21.1-1.3.1-community.jar`.
- **Mod id:** `tfmg`.
- **Versão:** `1.3.1-community`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Stack físico:** Create `6.0.10`; Sable `2.0.5` presente.
## 2. Relação com upstream e authority
A Community Edition é fork do Create: TFMG original por DrMangoTea e declara objetivo de corrigir bugs e eventualmente convergir mudanças upstream.
Para esta ficha:
- **versão/changelog atuais:** Community Edition 1.3.1, preservando 1.2.4b/1.3.0 como histórico de regressão;
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
## 12. Evolução 1.2.4b → 1.3.0 → 1.3.1 Community
A 1.2.4b estabeleceu regression gates preservados nesta ficha: atualização de `IElectric`, Blast Furnace finalizando recipes, correções quantitativas de Distillation, layered galena textures, dupe ligado ao Sable, engine assembly por right-click e Ponder da Winding Machine.
A **1.3.0** ampliou e corrigiu superfícies importantes:
- Concrete Hoses renderizando corretamente;
- Blast Furnaces sem crash por index out of bounds;
- pumps/TFMG fluid handlers voltando a interagir;
- Blast Stoves permitindo extração de fluidos;
- Winding Machines aceitando input manual;
- Coke Oven progress oculto quando idle;
- Exhaust/Smokestacks aceitando a tag `tfmg:exhaustable`;
- **Ponders para engines** regular, radial e large, resolvendo o antigo gap;
- mudanças de Vats, pressure/barometer e render de items/fluids;
- revisão de Oil Reserves: drain, chunk attachment, criação no worldgen, altura mínima e conversão de bedrock;
- ajustes de Polarizer, Blast Furnace hatches/inventory/save e compat de Multimeter com Goggles;
- suporte a Create Liquid Fuels Reburned;
- revisão de striated ores/worldgen com toggles/config;
- expansão de API/operation registry para Vats e handlers.
A **1.3.1-community**, instalada atualmente, publica:
- Coke Oven multiblocks voltando a funcionar;
- light bulbs com controle mais fino de intensidade por redstone;
- Cable Connectors mostrando input mode atual no tooltip do Multimeter;
- data generation sem falha quando Create Liquid Fuels: Reburned está ausente;
- argumento opcional `showIfEmpty` em `TFMGUtils.createFluidTooltip`;
- translation keys adicionais/corrigidas.
## 13. Known issues históricos e estado atual
Na 1.2.4b eram listados explicitamente:
- Engines sem Ponders;
- Vats e Tanks de tipos diferentes conectando entre si;
- quebrar um Cable Connector não desconectando completamente a outra ponta.
O primeiro item foi **resolvido pela 1.3.0**, que adicionou Ponders para engines regular/radial/large. As notas 1.3.0/1.3.1 consultadas não afirmam correção dos outros dois; portanto Vats/Tanks cross-type e disconnect incompleto de Cable Connector permanecem como regression gates **não confirmados como resolvidos**.
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
9. **Oil reserve/worldgen drift:** mudanças 1.3.0 em chunk attachment/drain/bedrock podem produzir comportamento híbrido em mundos existentes.
10. **Optional CLF integration:** data generation deve continuar funcionando com Create Liquid Fuels: Reburned ausente.
## 17. Matriz de testes
- [ ] Dedicated server boot com TFMG CE 1.3.1-community + Create 6.0.10 + Sable 2.0.5.
- [ ] Distillation Controller reconhece heat e conserva quantidades do recipe.
- [ ] Blast Furnace finaliza recipe e entrega output único.
- [ ] Creative Generator atualiza rede ao mudar power state.
- [ ] Cable Connector break evidencia/valida known issue sem corrupção adicional.
- [ ] Vat/Tank de tipos diferentes é tratado conforme known issue.
- [ ] Engine assembly por right-click só ocorre no contexto esperado.
- [ ] Engine save/restart mantém fuel/state sem energia infinita/duplicação.
- [ ] Sable interaction não reproduz dupe corrigido.
- [ ] Winding Machine Ponder abre e Ponders de engines regular/radial/large existem conforme 1.3.0.
- [ ] JEI recipes de coking/distillation/blasting/polarizing/winding/electrolysis são executáveis.
- [ ] Bridges energéticas instaladas não criam loop de energia.
- [ ] Coke Oven multiblock funciona após o fix 1.3.1.
- [ ] Light bulbs respondem ao controle fino de redstone.
- [ ] Multimeter mostra input mode de Cable Connector.
- [ ] Data generation não falha com Create Liquid Fuels: Reburned ausente.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 18. Evidências
- Modlist física de 17/09/2026: `tfmg-1.21.1-1.3.1-community.jar`, Create 6.0.10 e Sable 2.0.5.
- Releases/changelogs oficiais Community Edition: 1.2.4b, 1.3.0 e 1.3.1-community.
- 1.3.0: correções/expansões de Blast Furnace, fluid handlers, Winding Machine, Oil Reserves, Polarizer, Vats, worldgen, API e Ponders de engines.
- 1.3.1: Coke Oven multiblocks, light bulbs/redstone, Multimeter/Cable Connector, data generation sem CLF Reburned e `showIfEmpty` em fluid tooltip.
- Source oficial TFMG 1.21.1 permanece contexto arquitetural; o fork/build física é a autoridade dos deltas version-specific.
## 19. Revalidação física — 17/09/2026
A modlist atual confirma **TFMG Community Edition 1.3.1-community** para NeoForge 1.21.1, com Create 6.0.10 e Sable 2.0.5. A antiga build 1.2.4b é somente histórico de regressão.
O inventário físico continua distinguindo dependências top-level de bibliotecas embarcadas. Os processos industriais, redes elétricas e multiblocks não foram executados nesta auditoria documental; por isso os regression gates acima permanecem pendentes de runtime QA.

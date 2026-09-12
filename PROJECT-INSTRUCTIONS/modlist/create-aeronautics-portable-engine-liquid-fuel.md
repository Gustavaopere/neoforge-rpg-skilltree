# Create Aeronautics: Portable Engine Liquid Fuel

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819aad04e16a4846c698
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aeronautics: Portable Engine Liquid Fuel
- **Arquivo JAR:** `portable_engine_liquid_fuel-2.0.0-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 2.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia, Automação
- **Função:** Permite alimentar portable engines do ecossistema Aeronautics com combustíveis líquidos, conectando propulsionamento portátil a sistemas de fluidos/fuel.
- **Dependências:** Alvo funcional: Portable Engine do Create: Aeronautics. O pack físico contém Create Aeronautics 1.3.2, Sable 2.0.5 e Create. CurseForge expõe 2 relações Required Dependency, mas os nomes não foram retornados pela superfície auditada; não foram inventados.
- **Sobreposição:** Expande apenas o combustível do Portable Engine para fluidos; não substitui Create Aeronautics, Sable, sistemas de fluidos ou geradores completos.
- **Compatibilidade/Riscos:** Riscos: fuel-tag overlap, burn_time desbalanceado, datapack override/replace, fluid transport/pipe edge cases, normal vs superheated map confusion e divergência entre vários mods que adicionam combustíveis. Não atribuir compat nativa com Diesel Generators/TFMG/Destroy sem datapack/relation explícita.
- **Observações:** Runtime 2.0.0, Release NeoForge 1.21.1 de 27/04/2026, project 1521213, Client & Server, MIT. Página oficial documenta custom fuels via NeoForge data maps e consumo de fluidos como lava por pipe systems.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial do projeto/release 2.0.0 + documentação oficial de datapack/data maps para fuels.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-portable-engine-liquid-fuel
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Create Aeronautics: Portable Engine Liquid Fuel 2.0.0 reconstruído: liquid-fuel pipeline, data maps normal/superheated, burn_time, tags/conditions, pack fuel intersections, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `portable_engine_liquid_fuel-2.0.0-neoforge-1.21.1.jar`, mod id `portable_engine_liquid_fuel`, versão `2.0.0`, NeoForge 1.21.1. O addon adiciona suporte a **combustíveis líquidos** para o Portable Engine do Create: Aeronautics, inclusive alimentação por sistemas de pipes. A definição de fuels é data-driven por NeoForge data maps, com mapas separados para fuel normal e superheated fuel.

## 1. Identidade e papel
- **Mod:** Create Aeronautics: Portable Engine Liquid Fuel.
- **JAR físico:** `portable_engine_liquid_fuel-2.0.0-neoforge-1.21.1.jar`.
- **Mod id:** `portable_engine_liquid_fuel`.
- **Runtime:** `2.0.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** Xenra0.
- **CurseForge project ID:** 1521213.
- **Ambiente:** Client & Server.
- **Licença:** MIT.
- **Papel:** permitir que o Portable Engine consuma fluidos como combustível em vez de depender somente de combustíveis sólidos.
- **Decisão:** Sem decisão.

## 2. Boundary de ownership
O addon não cria uma nova engine nem um novo physics system.
Ownership:
- Create Aeronautics / Portable Engine → engine, propulsion e comportamento-base;
- Sable → física/SubLevels do stack atual;
- Create/NeoForge fluid handlers e pipe providers → transporte real do fluido;
- Portable Engine Liquid Fuel → reconhecimento, burn-time e integração do combustível líquido com a engine.
Se a ship/engine falha mecanicamente, não atribuir o problema a este addon sem isolar a camada de fuel.

## 3. Alimentação por fluidos
A descrição oficial afirma que o Portable Engine pode consumir fluidos, citando **lava através de pipe systems** como exemplo.
Superfícies de teste:
- tank → pipe → engine;
- interrupção de fluxo;
- troca de fuel durante operação;
- tank vazio;
- chunk/ship transition;
- assembly/disassembly.
A ficha não presume qual pipe mod é obrigatório: qualquer compatibilidade depende do handler/fluid transfer exposto na combinação real.

## 4. Data map de fuel normal
Custom fuels podem ser adicionados em:
`data/portable_engine_liquid_fuel/data_maps/fluid/engine_fuel.json`
Cada entrada associa um fluid ID ou fluid tag a um objeto com `burn_time`.
Exemplo conceitual publicado:
- `modid:fluid_name` → burn time específico;
- `#fluidtag:name` → regra por tag.
Isso torna o balanceamento de fuel **data-driven**, não hardcoded apenas aos exemplos do mod.

## 5. Data map de superheated fuel
Há um segundo mapa separado:
`data/portable_engine_liquid_fuel/data_maps/fluid/super_engine_fuel.json`
Ele é destinado à categoria publicada como **Superheated fuel**.
A existência de dois mapas exige cautela: um mesmo fluido não deve ser colocado em categorias conflitantes sem intenção explícita. A documentação auditada não publica nesta página a fórmula interna que diferencia os dois regimes; nenhuma semântica adicional foi inventada.

## 6. `burn_time`
O campo publicado é `burn_time`.
Ele representa a duração/valor de queima usado pelo addon para o fuel registrado.
Riscos de balanceamento:
- valor muito alto torna combustível virtualmente gratuito;
- valor muito baixo cria consumo excessivo e pressão logística;
- tags amplas podem fazer muitos fluidos herdarem o mesmo valor;
- duas definições concorrentes podem produzir resultado diferente conforme merge/override do data map.
Valores devem ser calibrados no pack contra autonomia real da engine.

## 7. Tags de fluidos
A documentação permite registrar **fluid tags**, não apenas IDs individuais.
Isso é especialmente útil em modpack com múltiplos providers de diesel, biodiesel, oil-derived fuels ou equivalentes, mas a existência de uma tag compartilhada não deve ser presumida.
Antes de unificar fuels de Create Diesel Generators, TFMG, Destroy ou outros mods:
- verificar tags físicas;
- escolher burn-time explícito;
- evitar incluir fluidos não combustíveis por tag ampla;
- testar source/flow do fluido real.

## 8. Condições NeoForge por mod carregado
A página oficial ensina usar `neoforge:conditions` com `type: neoforge:mod_loaded` e um `modid`.
Isso permite que uma definição de fuel seja carregada apenas quando o mod fornecedor estiver instalado.
É a abordagem correta para compat packs opcionais: não registrar um fluid ID inexistente quando o provider foi removido.

## 9. Merge seguro com `replace: false`
A documentação recomenda `replace: false` para evitar sobrescrever entradas existentes no data map.
Em um pack com datapacks/KubeJS/data generators, essa flag é um regression gate importante.
Auditar:
- ordem de packs;
- duplicação de key;
- map replacement acidental;
- reload após alteração;
- compat packs que usam o mesmo namespace/tag.

## 10. Cruzamento com o stack de combustíveis
A modlist contém vários sistemas que adicionam ou consomem combustíveis/fluidos. Isso cria **potencial de integração**, não compatibilidade automática.
Não afirmar que Diesel Generators, TFMG, Destroy ou outro mod é suportado nativamente só porque seus fluidos existem. Para cada fuel desejado é necessário confirmar:
- fluid ID/tag;
- registro no data map;
- `burn_time` escolhido;
- transferência por pipe/tank;
- comportamento da engine em servidor.

## 11. Dependências e stack físico
A página CurseForge expõe **2 relações Required Dependency**, mas a superfície auditada não retornou os nomes dessas duas relações. Para não inventar metadata, esta ficha registra apenas o que é comprovável:
- o alvo funcional é o Portable Engine do Create: Aeronautics;
- o pack físico contém Create Aeronautics 1.3.2, Sable 2.0.5 e Create;
- o addon roda Client & Server.
A dependency graph exata por manifest/relation deve ser extraída do JAR ou da relação oficial quando os nomes estiverem acessíveis.

## 12. Linha de releases
Publicações 1.21.1 identificadas:
- 1.0.0 — primeira release;
- 1.0.1 — removeu libraries não utilizadas;
- 2.0.0 — release atual instalada, publicada em 27/04/2026.
Não foi localizado changelog textual adicional da 2.0.0 além da documentação atual do projeto; por isso nenhuma feature específica extra foi atribuída ao delta 2.0.0 sem prova.

## 13. Client/server e lifecycle
Como a engine e o consumo de combustível afetam gameplay, o estado relevante deve convergir ao servidor.
Validar:
- servidor dedicado;
- consumo sincronizado;
- disconnect/reconnect;
- datapack reload;
- engine montada em contraption;
- fluid handler desaparecendo durante operação;
- alteração do data map entre restarts.
Cliente não deve manter fuel/burn state funcional divergente do servidor.

## 14. Riscos
1. **Fuel-tag overlap:** tag ampla registra líquidos indevidos.
2. **Burn-time imbalance:** autonomia fora da curva do pack.
3. **Normal vs superheated ambiguity:** cadastro na categoria errada.
4. **Datapack override:** `replace`/ordem elimina entradas de outro pack.
5. **Missing-mod references:** IDs inválidos sem `mod_loaded` condition.
6. **Pipe/handler mismatch:** fluido existe, mas não chega à engine pelo sistema usado.
7. **Reload consistency:** mudança de data map não reflete corretamente até reload/restart.
8. **Fuel-provider drift:** updates alteram IDs/tags de Diesel/TFMG/Destroy etc.
9. **Authority confusion:** problema de propulsion/physics não deve ser atribuído automaticamente ao addon de fuel.

## 15. Matriz de testes
- [ ] Dedicated server e cliente iniciam com runtime 2.0.0.
- [ ] Portable Engine funciona com comportamento-base antes de adicionar fuel customizado.
- [ ] Lava/um fuel publicado é transferido por pipe e consumido corretamente.
- [ ] `engine_fuel.json` custom registra um fluid ID com `burn_time` previsível.
- [ ] Entrada por fluid tag aplica-se somente aos fluidos desejados.
- [ ] `super_engine_fuel.json` é validado separadamente do fuel normal.
- [ ] `neoforge:mod_loaded` impede carregar entry quando provider está ausente.
- [ ] `replace:false` preserva entradas de outro datapack.
- [ ] `/reload` ou restart atualiza maps sem state stale/duplicado.
- [ ] Create Diesel Generators/TFMG/Destroy fuels só funcionam após registro explícito comprovado.
- [ ] Engine em Sable/Aeronautics contraption mantém consumo correto após assembly/disassembly e reconnect.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física: `portable_engine_liquid_fuel-2.0.0-neoforge-1.21.1.jar`, mod id/runtime.
- CurseForge oficial: project 1521213, Release NeoForge 1.21.1 2.0.0 de 27/04/2026, Client & Server, MIT.
- Documentação oficial: liquid fuel no Portable Engine, exemplo com lava via pipes, data maps `engine_fuel` e `super_engine_fuel`, `burn_time`, fluid IDs/tags, `neoforge:mod_loaded` e `replace:false`.
- Lineage oficial: 1.0.0 primeira release; 1.0.1 remove libraries não utilizadas.
- **Limite:** os nomes das duas relações Required Dependency não foram expostos pela superfície CurseForge auditada; não foram inventados. Também não foi presumida compat nativa com fuels de outros mods sem data map explícito.
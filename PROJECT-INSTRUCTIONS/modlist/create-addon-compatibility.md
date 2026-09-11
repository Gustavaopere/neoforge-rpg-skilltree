# Create: Addon Compatibility

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81bb9bfbfa69d9e7e91b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Addon Compatibility
- **Arquivo JAR:** `createaddoncompatibility-neoforge-1.21.1-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Camada domain-specific de unificação entre addons Create sobre Almost Unified, cobrindo copycats/gearboxes e famílias de oil/fuels/materiais com prioridades configuráveis e datapacks próprios.
- **Dependências:** Create + Almost Unified obrigatórios. Pack físico: Create 6.0.10 + AlmostUnified 1.21.1-1.4.2. Vários providers suportados estão instalados; alguns, como PneumaticCraft/Immersive Engineering/Ad Astra, não aparecem top-level atualmente.
- **Sobreposição:** Complementa Almost Unified; não o substitui. Integrações ativas incluem Copycats+/Connected/DnD, Connected/Create Utilities e múltiplos providers de Crude Oil/Fuels. Regras que dependem de PneumaticCraft/IE/Ad Astra ficam apenas como upstream, não overlap ativo.
- **Compatibilidade/Riscos:** Riscos: modPriorities trocar item canônico, variantes ocultas em viewer serem confundidas com removidas, stacks legados órfãos, oil/fuel semantic mismatch, TFMG não aceitar fuel externo apesar da unificação, datapack/reload stale e loops de conversão entre equivalentes.
- **Observações:** JAR/mod id/runtime 1.0.0 confirmados. Branch oficial `1.21.1` declara versão 1.0.0 e dependências Create/Almost Unified. README publica prioridades default e superfícies exatas de blocks/items/fluids unificados.
- **Procedência:** modlist.txt física atual de 09/09/2026 — 594 JARs top-level + release oficial 1.0.0 + repositório oficial Amronos/createaddoncompatibility branch pin-matching `1.21.1` + verificação dos providers presentes/ausentes no pack.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/createaddoncompatibility
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.0.0 com Almost Unified boundary, modPriorities, active provider matrix, copycat/gearbox/oil/fuel unification, datapacks/reload e migration risks catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🔗 **Identidade física e source matching confirmados:** `createaddoncompatibility-neoforge-1.21.1-1.0.0.jar`, mod id `createaddoncompatibility`, runtime `1.0.0`. O branch oficial `1.21.1` declara exatamente MC 1.21.1/mod 1.0.0 e requer Create + Almost Unified.

## 1. Papel e authority
Create: Addon Compatibility é uma camada de compatibilidade transversal entre addons Create. Ele usa **Almost Unified** como engine de unificação e fornece regras específicas para materiais, blocks/items, fluids e recipes de mods Create relacionados.
Almost Unified continua owner do mecanismo genérico de unificação; este addon owns o conjunto específico de compat rules/datapacks que acrescenta.

## 2. Dependências concretas
O projeto requer **Create** e **Almost Unified**. O pack físico atual contém Create 6.0.10 e AlmostUnified `1.21.1-1.4.2`, portanto a função central está ativa em princípio.
O source 1.0.0 foi desenvolvido contra Almost Unified 1.3.0/Create 6.0.9, então o pack usa revisões posteriores; compatibilidade deve ser validada por recipes/tags resultantes, não presumida apenas pelo range.

## 3. Priority model
O README oficial define prioridade por `config/almostunified/unify.json`, campo `modPriorities`. A ordem default publicada é, do maior para o menor: `pneumaticcraft`, `copycats`, `create_connected`, `create_dd`, `tfmg`.
Essa prioridade determina qual variante se torna canônica em superfícies de unificação; mudar a ordem pode alterar outputs e itens mostrados por viewers.

## 4. Visibility em JEI/REI/EMI
Itens de mods de menor prioridade podem ser ocultados em JEI/REI/EMI. **Oculto no viewer não significa removido do registry ou inválido em inventários existentes.**
Automação/scripts não devem usar ausência visual como prova de inexistência.

## 5. Copycat blocks — integração ativa
O projeto unifica **All Copycat Blocks** entre Copycats+, Create: Connected e Create Dreams & Desires.
O pack físico contém os três providers: Copycats+ 3.0.9, Create: Connected 1.3.3 e Dreams n' Desires 2.3a-BETA. Portanto esta é uma compatibilidade **ativa e diretamente relevante**.

## 6. Six Way Gearbox — integração ativa
O README unifica **Six Way Gearbox** entre Create: Connected e Create Utilities. O pack contém Create: Connected 1.3.3 e Create Utilities J 0.3.4+1.21.1.
Recipes/processes precisam aceitar a variante canônica sem deixar outputs duplicados ou impossível de converter.

## 7. Coal Coke Dust — cobertura parcial
A regra oficial cobre Immersive Engineering + TFMG. O pack contém TFMG 1.2.4b-community, mas não foi encontrado Immersive Engineering como JAR top-level atual.
Logo não se marca essa pair-unification como integração multi-provider ativa neste estado físico.

## 8. Plastic Sheet — cobertura parcial
A regra oficial cobre PneumaticCraft + TFMG. PneumaticCraft não aparece como JAR top-level atual; TFMG está presente.
Portanto esta superfície existe upstream, mas não constitui hoje uma duplicidade concreta entre os dois providers citados.

## 9. Crude Oil — integração ativa
A regra de **Crude Oil** abrange múltiplos mods. No pack atual estão presentes pelo menos Create Diesel Generators 1.3.15, Destroy 0.4.3 e TFMG 1.2.4b-community.
Ad Astra e PneumaticCraft não aparecem top-level. A unificação de oil continua relevante porque há mais de um provider ativo entre os suportados.

## 10. Fuels — integração fortemente ativa
O README lista fuels de Ad Astra, CC&A, Create Diesel Generators, Steam 'n' Rails, Destroy, Create Garnished, PneumaticCraft e TFMG.
No pack atual estão presentes **Create Crafts & Additions 1.7.0**, Create Diesel Generators 1.3.15, Steam 'n' Rails 0.3.0-beta.2, Destroy 0.4.3, Create Garnished 2.1.9.2 e TFMG 1.2.4b-community. Portanto esta é uma das superfícies de maior impacto econômico/semântico do addon.

## 11. Limitação específica do TFMG
A documentação oficial registra que, na linha suportada, apenas fuels próprios do TFMG funcionam em seus generators porque TFMG não possui suporte genérico a custom fuel types.
Unificação de item/fluid ou recipe não deve ser confundida com garantia de que todo consumer TFMG aceite todos os fuels equivalentes.

## 12. Kerosene/plastic/lubricant
As regras oficiais para kerosene, molten/liquid plastic e lubricant/lubrication oil conectam PneumaticCraft e TFMG. Como PneumaticCraft está ausente da modlist top-level atual, essas pairs não são marcadas como overlap concreto de dois providers neste pack.

## 13. Datapacks embarcados
O projeto inclui datapacks para alterar aspectos de compatibilidade, habilitáveis na criação do mundo ou por `/datapack enable` segundo o README.
A lista de datapacks habilitados é world state; mudar esse estado pode modificar recipes/compatibilidade sem trocar o JAR.

## 14. Almost Unified boundary
Almost Unified executa canonicalização/unificação genérica; Create: Addon Compatibility fornece conhecimento específico de equivalências entre addons Create.
Remover este addon não equivale a remover Almost Unified: parte da unificação genérica pode continuar, mas regras domain-specific aqui publicadas deixam de ser garantidas.

## 15. Existing stacks e migration
Quando prioridade muda, itens já existentes de um provider de menor prioridade não necessariamente desaparecem. Inventários, machines e recipes precisam continuar capazes de lidar com stacks legados ou convertê-los conforme policy da engine.
Nunca apagar stacks legados apenas porque ficaram ocultos no viewer.

## 16. Recipes e canonical outputs
Unificação pode alterar qual variante é usada como output/ingredient. Cada recipe deve produzir um output canônico consistente sem multiplicar matéria ao alternar entre equivalentes.
Loops de conversão A→B→A com ganho de quantidade são um risco explícito em packs grandes.

## 17. Reload lifecycle
Mudanças em `unify.json`, datapacks e recipes exigem `/reload` ou restart conforme a subsystem. Caches de recipe/tag/viewer precisam convergir para a mesma prioridade.
Máquinas em andamento não podem completar usando canonical item antigo e reiniciar usando outro de forma a duplicar output.

## 18. Client/server e multiplayer
Canonical recipe/tag state é server-authoritative. JEI/REI/EMI apenas reflete/oculta variantes no cliente.
Clientes com cache/viewer diferente não podem alterar o item realmente aceito/gerado pelo servidor.

## 19. Delta 1.0.0
A release 1.0.0 é o port NeoForge 1.21.1 publicado em 09/03/2026 e registra “a bunch of bug fixes”. Como o changelog não detalha cada bug, não se inventam causas específicas além das regras publicadas no source/README.

## 20. Riscos
1. `modPriorities` muda e troca o item canônico de recipes existentes.
2. Item oculto no JEI é tratado erroneamente como removido.
3. Copycat blocks de três providers produzem outputs paralelos.
4. Six Way Gearbox não converte/aceita variante de outro provider.
5. Crude Oil equivalente possui semântica/capability diferente entre mods.
6. Fuel unificado é aceito em recipe mas rejeitado por consumer específico.
7. TFMG consumer é presumido compatível com fuel externo apesar da limitação upstream.
8. Existing stacks ficam órfãos após troca de prioridade.
9. `/reload` deixa cache de tags/recipes/viewer stale.
10. Datapack embarcado muda comportamento sem ser contabilizado na auditoria.
11. Loop de conversão entre variantes gera matéria líquida.
12. Atualização de Almost Unified 1.4.2 diverge da versão 1.3.0 usada no source 1.0.0.

## 21. Matriz de testes
- [ ] Dedicated server inicia com addon 1.0.0 + Almost Unified 1.4.2 + Create 6.0.10.
- [ ] Copycats+/Connected/DnD convergem conforme prioridade sem recipe duplicada.
- [ ] Six Way Gearbox funciona entre Connected e Create Utilities sem item órfão.
- [ ] Crude Oil de Diesel Generators/Destroy/TFMG participa apenas das processes realmente compatíveis.
- [ ] Fuels de CC&A/CDG/Railways/Destroy/Garnished/TFMG não criam loop ou consumo incoerente.
- [ ] Limitação de generator TFMG é preservada; fuel externo não é presumido válido.
- [ ] Existing stack de provider menor continua recuperável após restart.
- [ ] JEI/REI/EMI ocultam variantes conforme policy sem afetar registry/inventory.
- [ ] Alteração controlada de `modPriorities` em mundo de teste produz canonicalização determinística.
- [ ] `/reload` atualiza tags/recipes/viewer sem cache stale.
- [ ] Datapacks embarcados enable/disable não duplicam recipes.
- [ ] Conversões equivalentes não geram quantidade líquida.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
A modlist física confirma JAR/runtime 1.0.0 e os providers ativos citados. A release oficial confirma NeoForge 1.21.1, Client & Server e o port 1.0.0. O branch oficial `1.21.1` declara exatamente mod 1.0.0 e dependências Create/Almost Unified; o README lista explicitamente cada família de blocks/items/fluids unificada, prioridades default e datapacks. Compatibilidades cujo segundo provider não está instalado foram mantidas como upstream, não como overlap ativo.

> 🔒 **Boundary canônico:** Almost Unified owns o mecanismo de unificação; Create: Addon Compatibility owns as regras específicas entre addons. O item/fluido canônico é determinado pela configuração e pelo state server-side carregado, nunca apenas pelo que o recipe viewer mostra.
# Create: Mechanical Spawner

> **Autoridade física atual — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#147**: JAR `create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar`, mod id `create_mechanical_spawner`, runtime `1.3.1-6.0.10`, SHA-1 `d9a5a2ee44238e4a90f70057ba9ec8414594fe24`. O filename/publicação física é `1.3.2-6.0.10`; a metadata/runtime top-level do JAR permanece `1.3.1-6.0.10`, divergência stale preservada para troubleshooting.

## Propriedades do registro

- **Mod:** Create: Mechanical Spawner
- **Arquivo JAR:** create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar
- **Versão 1.21.1:** 1.3.2-6.0.10
- **Categoria:** Automação, Tecnologia, Mobs
- **Função:** Adiciona um Mechanical Spawner movido por rotação Create, com recipes de spawn fluid, geração aleatória dependente de bioma ou entidade específica, Loot Collector e integrações de automação/configuração.
- **Dependências:** Create 6.0.10 + Mechanicals Lib 1.1.6 conforme a entrada física/catalogada. Projeto oferece JEI/Jade/KubeJS como integrações; a build pública 1.3.2-6.0.10 é Beta NeoForge 1.21.1.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Economia de mob farms sensível. Riscos em spawn conditions/range, biome-random generation, fluid consumption, Loot Collector/custom loot, Wither recipes, KubeJS recipes e troubleshooting de versão porque a metadata interna reporta 1.3.1-6.0.10 embora o artefato físico/publicado seja 1.3.2-6.0.10.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-mechanical-spawner/files/all
- **Procedência:** modlist.txt física atual de 16/09/2026 + JAR `create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar` + metadata runtime `create_mechanical_spawner` 1.3.1-6.0.10 + CurseForge oficial revalidado em 20/09/2026; publicação 1.3.2-6.0.10 é a build Beta mais recente para NeoForge 1.21.1.
- **Observações:** mod id `create_mechanical_spawner`; JAR/publicação `1.3.2-6.0.10`; runtime metadata literal `1.3.1-6.0.10`. A versão catalogada segue o artefato físico/publicação oficial; a string interna divergente é preservada como metadata stale. Projeto documenta stress/min speed/fluid capacity/range/time multiplier configuráveis, spawn fluid mixer recipes, Loot Collector, JEI/Jade/KubeJS e Ponder.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — lote físico #146: artefato `create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar` e publicação oficial 1.3.2-6.0.10 confirmados; metadata interna ainda reporta 1.3.1-6.0.10 e é tratada como string stale do JAR, preservada para troubleshooting.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão curatorial formal. Em 20/09/2026, a identidade instalada foi reconciliada: o JAR físico e a publicação oficial são 1.3.2-6.0.10; a metadata runtime 1.3.1-6.0.10 foi confirmada como string stale interna e permanece registrada apenas para troubleshooting.
- **Sobreposição:** É uma rota cinética/data-driven de geração de mobs e não uma duplicata lógica de Apothic Spawners. Conflitos devem ser avaliados por spawn economy, loot e ownership de farms, não por ambos lidarem com spawners.

# Dossiê operacional — padrão Alex's Mobs
> 🧟 Identidade física: `create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar`. A publicação upstream é `1.3.2-6.0.10`, enquanto a metadata runtime da instância declara **`1.3.1-6.0.10`**. A versão catalogada segue o artefato físico/publicação; a string runtime divergente permanece registrada como metadata stale para troubleshooting.

## 1. Papel e authority
Create: Mechanical Spawner adiciona um spawner automatizado movido por **rotação Create**. Create decide a rede cinética/stress; o addon decide seus spawn recipes, fluid inputs, processing cadence, range e Loot Collector.
Ele não deve modificar silenciosamente vanilla/Apothic spawner state como se fosse o mesmo sistema.
## 2. Mechanical Spawner
O bloco processa um recipe de spawn quando recebe as condições cinéticas e de fluido configuradas. A entidade deve nascer uma única vez por conclusão válida; redstone/retry/chunk reload não pode repetir o mesmo settlement.
## 3. Random spawn fluid
O projeto documenta **random spawn fluid** com geração aleatória dependente do bioma. Nesse modo, ausência de `output` específico não significa entidade arbitrária global: a seleção deve respeitar o pool/regra de bioma implementado pelo addon.
## 4. Specific mob recipes
Recipes podem especificar entidade de output concreta. O formato público documenta `input` obrigatório, `output` opcional, `processingTime` opcional e `customLoot` opcional.
IDs e recipe values da build física devem vir dos datapacks/JAR; esta ficha não congela uma lista inventada de mobs.
## 5. Rotação, stress e velocidade mínima
O addon expõe configuração para **stress** e **minimum speed**. A rede Create é authority do RPM/stress real; scripts não devem manter uma segunda condição de velocidade paralela.
Overstress ou velocidade abaixo do mínimo precisa impedir/atrasar processamento conforme config atual.
## 6. Fluid capacity e mixer recipes
A capacidade de fluido é configurável e o projeto fornece **spawn fluid mixer recipes**. Consumo de fluido e output do mixer precisam ser atômicos para evitar criação de entidade sem débito ou débito duplicado por outro handler.
## 7. Range
`Spawn point max range` é configurável. Spawn placement deve validar posição/chunk/espaço e respeitar qualquer condição aplicável antes de materializar a entidade.
Não usar client particle/preview como prova de que o spawn server-side ocorreu.
## 8. Processing time multiplier
O addon permite multiplicar/dividir o tempo de processamento globalmente sem editar cada recipe. Isso é uma policy de cadence do provider; KubeJS/config externa deve evitar aplicar um segundo multiplicador ao mesmo valor final.
## 9. Loot Collector
O **Loot Collector** pode recolher loot produzido pelo sistema. A documentação permite configurar containers aceitos, inclusive Create Item Vault, ou desabilitar o bloco; o spawner também pode ser configurado para funcionar somente com collectors.
Mob death e item insertion precisam manter ownership único para não duplicar drops entre world entities e inventory.
## 10. Custom loot
Spawn recipes podem definir `customLoot` quando usados com Loot Collector. Isso transforma o addon em authority daquele resultado customizado; loot hooks externos não devem somar automaticamente o drop vanilla como uma segunda recompensa se o recipe substitui o caminho.
## 11. Wither e blocos resistentes
O projeto documenta **Wither recipe** e glass/casing imunes a Wither/explosions. Boss spawning é superfície de alto risco: validar containment, one-shot processing, drops e server performance sem presumir que regras vanilla de spawner se aplicam integralmente.
## 12. JEI, Jade, KubeJS e Ponder
- JEI: recipe presentation;
- Jade: inspection/tooltip;
- KubeJS: extensão data/script de recipes;
- Ponder: documentação visual.
Nenhuma dessas camadas deve substituir recipe/spawn state server-authoritative.
## 13. Client/server e lifecycle
Spawn, fluid, inventory, loot e recipe completion são common/server. UI/Ponder/overlay são client-facing.
Validar chunk unload/reload, server restart, datapack/KubeJS reload quando suportado, capability invalidation e alteração de configs com mundo já existente.
## 14. Divergência de versão
A build física exige registrar separadamente:
- filename/publicação: `1.3.2-6.0.10`;
- runtime metadata: `1.3.1-6.0.10`.
O campo `Versão 1.21.1` preserva a identidade do artefato físico/publicação (`1.3.2-6.0.10`). A metadata runtime `1.3.1-6.0.10` permanece documentada separadamente como string stale do JAR para troubleshooting.
## 15. Riscos
1. Entidade nascer duas vezes por recipe completion.
2. Fluid debit duplicado/ausente.
3. Biome-random pool incorreto.
4. Spawn position inválida ou em chunk não pronto.
5. Loot Collector duplicar drops.
6. Custom loot somar indevidamente ao loot padrão.
7. KubeJS criar recipe conflitante/duplicado.
8. Boss/Wither farm exceder balance/performance esperado.
9. Version string divergente mascarar troubleshooting.
## 16. Matriz de testes
1. Dedicated server boot com Create 6.0.10 + Mechanicals Lib.
2. RPM abaixo/acima do mínimo e overstress.
3. Recipe de mob específico.
4. Random spawn por pelo menos dois biomas.
5. Fluid capacity/consumption e mixer recipes.
6. Range boundary e posições inválidas.
7. Processing time multiplier.
8. Loot Collector com container permitido, não permitido e desabilitado.
9. Custom loot exatamente uma vez.
10. Wither recipe em mundo de teste isolado.
11. KubeJS/JEI/Jade/Ponder smoke-test.
12. Restart/chunk reload sem duplicate spawn.
## 17. Evidência
- modlist física atual de 16/09/2026: JAR `1.3.2-6.0.10`, runtime metadata `1.3.1-6.0.10`;
- CurseForge: build 1.3.2-6.0.10 Beta para NeoForge 1.21.1/Create 6.0.10;
- documentação Modrinth: stress/min speed/fluid capacity/range/time multiplier, random/specific spawn recipes, Loot Collector, Wither, JEI/Jade/KubeJS/Ponder.
> 🔒 Boundary canônico: **Create fornece a energia cinética; Mechanical Spawner decide o recipe de spawn e seu loot/fluido**. Um processamento válido produz uma única consequência server-side.

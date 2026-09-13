# Create: Mechanical Spawner

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81739650d944208d0200
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Mechanical Spawner
- **Arquivo JAR:** `create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar`
- **Versão 1.21.1:** 1.3.1-6.0.10
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Automação, Tecnologia, Mobs
- **Função:** Adiciona um Mechanical Spawner movido por rotação Create, com recipes de spawn fluid, geração aleatória dependente de bioma ou entidade específica, Loot Collector e integrações de automação/configuração.
- **Dependências:** Create 6.0.10 + Mechanicals Lib 1.1.6 conforme a entrada física/catalogada. Projeto oferece JEI/Jade/KubeJS como integrações; a build pública 1.3.2-6.0.10 é Beta NeoForge 1.21.1.
- **Sobreposição:** É uma rota cinética/data-driven de geração de mobs e não uma duplicata lógica de Apothic Spawners. Conflitos devem ser avaliados por spawn economy, loot e ownership de farms, não por ambos lidarem com spawners.
- **Compatibilidade/Riscos:** Economia de mob farms sensível. Riscos em spawn conditions/range, biome-random generation, fluid consumption, Loot Collector/custom loot, Wither recipes, KubeJS recipes e divergência de identidade: filename/publicação 1.3.2-6.0.10 enquanto metadata runtime declara 1.3.1-6.0.10.
- **Observações:** mod id `create_mechanical_spawner`; JAR/publicação `1.3.2-6.0.10`; runtime físico literal `1.3.1-6.0.10`. Divergência preservada fail-closed. Projeto documenta stress/min speed/fluid capacity/range/time multiplier configuráveis, spawn fluid mixer recipes, Loot Collector, JEI/Jade/KubeJS e Ponder.
- **Procedência:** Modlist física canônica de 08/09/2026 + metadata runtime `create_mechanical_spawner` 1.3.1-6.0.10 + CurseForge/Modrinth oficiais que identificam o artefato `1.3.2-6.0.10` Beta para NeoForge 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-mechanical-spawner/files/all
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — spawn/fluid/rotation authority, biome-vs-specific recipes, Loot Collector/custom loot, config/data/KubeJS, lifecycle e divergência 1.3.2↔1.3.1 runtime catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Mechanical Spawner foi reconfirmado como `Instalado`; a divergência entre artefato/publicação 1.3.2-6.0.10 e metadata runtime 1.3.1-6.0.10 foi preservada em vez de normalizada. Presença não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🧟 Identidade física: `create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar`. A publicação upstream é `1.3.2-6.0.10`, enquanto a metadata runtime da instância declara **`1.3.1-6.0.10`**. As duas evidências permanecem separadas.

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
O campo `Versão 1.21.1` preserva o runtime observado; a ficha mantém o artefato/publicação para rastreabilidade. Não “corrigir” um com base no outro.

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
- modlist física 08/09/2026: JAR `1.3.2-6.0.10`, runtime `1.3.1-6.0.10`;
- CurseForge: build 1.3.2-6.0.10 Beta para NeoForge 1.21.1/Create 6.0.10;
- documentação Modrinth: stress/min speed/fluid capacity/range/time multiplier, random/specific spawn recipes, Loot Collector, Wither, JEI/Jade/KubeJS/Ponder.

> 🔒 Boundary canônico: **Create fornece a energia cinética; Mechanical Spawner decide o recipe de spawn e seu loot/fluido**. Um processamento válido produz uma única consequência server-side.
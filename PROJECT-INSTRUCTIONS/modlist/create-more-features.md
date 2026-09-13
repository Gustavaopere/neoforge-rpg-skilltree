# Create: More Features

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db814ca8d8de5b178d3288  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: More Features
- **Arquivo JAR:** `create_mf-0.1.3-neoforge-1.21.1.jar`
- **Versão 1.21.1:** `0.1.3`
- **Categoria:** Tecnologia; Automação; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-more-features
- **Função:** Addon amplo de Create que adiciona profissões de villagers, mecanismos, itens/conteúdo legado ou removido do Create, auto farms, decoração e dispositivos próprios.
- **Dependências:** Create. Build 0.1.3 é Release NeoForge 1.21.1 e Client & Server; nenhuma dependência adicional obrigatória foi inferida sem metadata versionada mais granular.
- **Compatibilidade/Riscos:** Escopo amplo e heterogêneo. Pode cruzar villager trades/professions, farms, mechanisms, decor e itens reintroduzidos por outros addons. A página pública da 0.1.3 não fornece changelog granular; detalhes de registries/stats/recipes não pinados ficam fail-closed.
- **Sobreposição:** Pode compartilhar features com vários addons Create, mas a amplitude do projeto não autoriza declarar redundância global. Comparar profissão, mecanismo, farm, item ou bloco concreto antes de qualquer decisão.
- **Observações:** mod id `create_mf`; runtime 0.1.3. Release 0.1.3 publicada em 26/06/2026. O upstream descreve cinco superfícies confirmadas: new villager professions, new mechanisms, removed items from Create, new auto farms, decor/devices. Sem changelog granular da 0.1.3 na fonte pública consultada.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_mf` 0.1.3 + CurseForge oficial da release 0.1.3 NeoForge 1.21.1 e descrição oficial do projeto.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: More Features 0.1.3 foi reconfirmado como `Instalado` e reconstruído proporcionalmente ao escopo que o upstream sustenta. Presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — escopo de professions/mechanisms/farms/decor, Create authority, data/recipe/trade boundaries, client/server/lifecycle e limite fail-closed da 0.1.3 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 🧰 Versão física confirmada: `create_mf-0.1.3-neoforge-1.21.1.jar`, mod id `create_mf`, runtime `0.1.3`, NeoForge 1.21.1. O upstream descreve um addon **amplo**; onde a 0.1.3 não publica detalhe granular, esta ficha permanece fail-closed.

## 1. Papel e authority
Create: More Features reúne extensões variadas em cima do Create. Create mantém authority de kinetics, stress, processing e contraptions; este addon controla apenas seus próprios professions, mechanisms, farms, blocks/items e recipes.

## 2. Villager professions
O upstream confirma **novas profissões para villagers**. Trade tables, workstations e progression devem vir dos data/registries reais da build; esta ficha não inventa nomes, níveis ou trades não pinados.

Villager restock/trade state continua server-authoritative.

## 3. New mechanisms
O projeto declara novos mecanismos. Cada mechanism deve consumir a rede Create pelas APIs/state suportados, sem manter cálculo paralelo de RPM/stress.

Sem inventário versionado de blocks/machines da 0.1.3 na fonte pública consultada, qualquer automação própria precisa inspecionar JEI/JAR/source correspondente antes de integrar.

## 4. Removed items from Create
A descrição oficial menciona conteúdo que reintroduz **itens removidos do Create**. Isso cria risco de colisão temática/ID com outros addons de nostalgia/backports.

Não assumir que um item reintroduzido tem comportamento idêntico ao de uma versão histórica do Create; a implementação atual do addon é a authority.

## 5. Auto farms
O upstream confirma **new auto farms**. Harvest/input/output precisam settlement server-side uma vez por operação. Sobreposição com Integrated Farming, Central Kitchen ou outros addons deve ser demonstrada por máquina/recipe concreto.

## 6. Decor e devices
Decoração e devices adicionais ampliam o catálogo Create. Aparência/render é client-facing; block state, inventory e interaction relevantes continuam common/server.

Resource packs podem alterar presentation sem mudar a semântica do block registrado.

## 7. Recipes e progression
Um addon amplo pode introduzir recipes para múltiplas categorias. Recipe Manager/datapack é authority; JEI apenas apresenta. KubeJS overrides devem usar IDs reais e evitar manter recipe original e override em paralelo.

## 8. Villager/trade interoperability
Outros mods também alteram villagers e trades. Conflito real ocorre quando duas modificações disputam profession/workstation/trade pool de modo incompatível; coexistência por si só não prova redundância.

## 9. Create integration
Mechanisms/farms próprios devem seguir network state do Create. Atualização de Create pode afetar recipes, kinetic block interfaces ou rendering; portanto version drift deve ser testado no conjunto real.

## 10. Client/server
Trades, inventories, farms, recipes e block state são server/common. Models, particles, screens e tooltips são client-facing. Dedicated server não deve carregar classes gráficas para registrar conteúdo comum.

## 11. Lifecycle
Validar server boot, registry/data load, villager profession assignment/restock, recipe reload, resource reload, chunk unload/reload, restart e contraption interaction quando um block for móvel.

## 12. Limite de evidência 0.1.3
A release 0.1.3 está confirmada como Release NeoForge 1.21.1, porém a página pública consultada não expõe changelog granular nem catálogo completo de registries. Portanto:
- não congelar nomes/stats/recipes não observados;
- não transportar automaticamente documentação de versões antigas/alpha;
- inspecionar JAR/source exato antes de código ou balanceamento item-a-item.

## 13. Riscos
1. Trade/profession collision.
2. Farm duplicar output por dois handlers.
3. Item “removed from Create” colidir semanticamente com outro backport.
4. Recipe alternate quebrar progression.
5. Mechanism usar API Create incompatível após update.
6. Client-only renderer carregar no servidor.
7. Documentação de versão antiga ser tratada como 0.1.3.

## 14. Matriz de testes
1. Dedicated server boot.
2. Enumerar professions/workstations/trades reais via gameplay/JAR.
3. Smoke-test de cada mechanism registrado.
4. Auto farms: input→output exactly once.
5. Recipes via Recipe Manager/JEI.
6. Blocks/items reintroduzidos: comportamento atual, não histórico presumido.
7. Resource/datapack reload.
8. Restart/chunk reload.
9. Coexistência com addons Create que tocam a mesma feature concreta.

## 15. Evidência
- modlist física 08/09/2026: 0.1.3;
- CurseForge oficial: Release NeoForge 1.21.1, Client & Server;
- descrição oficial: villager professions, mechanisms, removed Create items, auto farms, decor e devices;
- ausência de changelog granular da 0.1.3 tratada explicitamente fail-closed.

> 🔒 Boundary canônico: **o escopo amplo é confirmado; detalhes não publicados da build 0.1.3 não são inventados**. Para integração item-a-item, pin de JAR/source é obrigatório.

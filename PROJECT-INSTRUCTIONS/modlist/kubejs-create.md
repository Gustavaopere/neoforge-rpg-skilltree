# KubeJS Create — 2101.3.1-build.18

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db8129acc7d29b6a0d0480  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** KubeJS Create
- **Arquivo JAR:** `kubejs-create-neoforge-2101.3.1-build.18.jar`
- **Versão 1.21.1:** `2101.3.1-build.18`
- **Categoria:** Compat; Automação; Tecnologia
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-create/files/all
- **Função:** Integra KubeJS ao Create, expondo builders de recipes/processos Create como compacting, crushing, deploying, filling, mechanical crafting e sequenced assembly para scripts.
- **Dependências:** Required content oficial: KubeJS e Create. Runtime físico: KubeJS 2101.7.2-build.374 + Create 6.0.10. Client & Server.
- **Compatibilidade/Riscos:** Linha NeoForge 1.21.1 é Beta-only; build.18 é a mais recente localizada e não existe Release estável equivalente. Riscos: recipe duplication/reload, transitional items, Deployer held-item semantics e API drift Create/KubeJS.
- **Sobreposição:** Complementa Create/KubeJS; não adiciona segunda lógica de máquinas. Recipes/scripts podem sobrepor recipes de outros addons e precisam de IDs/ordem coordenados.
- **Observações:** JAR físico `kubejs-create-neoforge-2101.3.1-build.18.jar`, mod id `kubejs_create`, runtime 2101.3.1-build.18. `Verificado` não elimina o risco inerente ao canal Beta.
- **Procedência:** modlist.txt física atual de 08/09/2026 + Modrinth oficial KubeJS Create build.18 + KubeJS Wiki Create 1.21.1.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; Create recipe builders, sequenced assembly, lifecycle/reload, Beta-only boundary e riscos catalogados para build.18.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> ⚙️ **ESCOPO CANÔNICO.** Runtime físico: `kubejs-create-neoforge-2101.3.1-build.18.jar`, mod id `kubejs_create`, versão `2101.3.1-build.18`. É a integração KubeJS ↔ Create para NeoForge 1.21.1. A linha pública 2101.3.1 é **Beta-only**; isso é maturidade do artefato, não motivo automático para downgrade.

## 1. Recipe integration
A documentação 1.21.1 expõe recipe builders para processos Create, incluindo compacting, crushing, cutting, deploying, emptying, filling, haunting, mechanical crafting e sequenced assembly. Scripts ficam tipicamente em `server_scripts` via `ServerEvents.recipes`.

## 2. Create process semantics
Cada recipe customizada continua executada pelas máquinas/provider Create. KubeJS Create descreve o recipe e parâmetros; não se torna owner de inventories, RPM, heat ou machine state.
Exemplos críticos:
- Deploying usa exatamente dois inputs e pode manter o held item quando configurado;
- Compacting pode exigir heated/superheated;
- Sequenced Assembly usa transitional item e loops;
- Mechanical Crafting pode chegar a grids maiores que vanilla.

## 3. Recipe generation e IDs
A documentação registra mecanismos para evitar auto-generated counterparts e mostra que recipe IDs/ordem importam. Scripts devem usar IDs estáveis para permitir substituição/remoção determinística e evitar duplicação após reload.

## 4. Lifecycle
Recipes em `server_scripts` são reloadable. O addon precisa sobreviver a cold boot e `/reload` sem acumular recipes, manter transitional item stale ou quebrar JEI/recipe viewer. Registration de conteúdo custom que sirva de ingrediente pertence ao lifecycle KubeJS adequado, não ao recipe callback por acaso.

## 5. Runtime do pack
Create físico é 6.0.10 e KubeJS é 2101.7.2-build.374. A build KubeJS Create 2101.3.1-build.18 é a publicação NeoForge 1.21–1.21.1 mais recente localizada e declara KubeJS/Create como required content.

## 6. Beta-only boundary
Todas as builds públicas 2101.3.1 para NeoForge 1.21.1 são Beta. Não há Release estável equivalente para o mesmo alvo. Portanto `Verificado` significa versão/escopo confirmados, **não** estabilidade garantida.

## 7. Riscos
1. Recipe duplicada após reload.
2. Transitional item perdido/duplicado em sequenced assembly.
3. Held item consumido incorretamente em Deployer.
4. Heat requirement divergente do recipe esperado.
5. Create 6 API drift quebrando builders.
6. Script incompatível com mudanças de KubeJS 7.x.

## 8. Boundary para quests/perks
Recipe registrada não é milestone. Crédito deve vir do resultado/processo causal server-side, quando necessário, e não do JEI ou da existência da recipe no registry.

## 9. Matriz de testes
- [ ] Dedicated server inicia com build.18 + Create 6.0.10 + KubeJS 7.2.
- [ ] `/reload` não duplica recipes.
- [ ] Deploying consome/preserva held item conforme script.
- [ ] Heated/superheated compacting respeita heat real.
- [ ] Sequenced Assembly conserva transitional item e loops.
- [ ] Mechanical crafting custom resolve output uma única vez.
- [ ] Recipe viewer acompanha alterações após reload.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limite
Modrinth confirma build.18 Beta, NeoForge 1.21–1.21.1, Client & Server e required content KubeJS/Create. KubeJS Wiki documenta os builders 1.21.1. Scripts reais do pack não foram auditados; nenhuma recipe custom é inferida sem localizar os arquivos.

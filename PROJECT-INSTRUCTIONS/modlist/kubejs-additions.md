# KubeJS Additions — 1.21.1-6.0.0

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db8120ae65df58c703f4fa  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** KubeJS Additions
- **Arquivo JAR:** `kubejsadditions-neoforge-1.21.1-6.0.0.jar`
- **Versão 1.21.1:** `1.21.1-6.0.0`
- **Categoria:** QoL; Automação; Compat
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-additions
- **Função:** Extensão do KubeJS que adiciona APIs/eventos/helpers extras para packdev, com destaque para categorias/recipe types JEI customizados e integração programável com Jade.
- **Dependências:** KubeJS NeoForge 2101.7.2-build.374 está fisicamente presente. JEI/Jade são superfícies de integração documentadas; a presença real de cada consumer deve ser confirmada antes de assumir UI ativa.
- **Compatibilidade/Riscos:** Addon de scripting acoplado a KubeJS. Riscos: scripts incompatíveis após API drift, registration em fase errada, client-only JEI/Jade code em server path, reload parcial e handlers duplicados. Não atribuir gameplay ao addon sem script efetivo que o implemente.
- **Sobreposição:** Complementa KubeJS; pode cruzar com outros addons de scripting/JEI/Jade, mas APIs distintas podem coexistir. O comportamento efetivo depende dos scripts do pack.
- **Observações:** JAR físico `kubejsadditions-neoforge-1.21.1-6.0.0.jar`, mod id `kubejsadditions`, runtime 1.21.1-6.0.0. A superfície histórica de eventos Fabric/Architectury não foi presumida integralmente sem pin de source da build.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial KubeJS Additions 1.21.1-6.0.0 + documentação pública do projeto.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — metadata Verificada com corpo vazio corrigida; KubeJS scripting extensions, custom JEI/Jade surfaces, side/lifecycle e riscos catalogados para 1.21.1-6.0.0.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🧩 **ESCOPO CANÔNICO.** Runtime físico: `kubejsadditions-neoforge-1.21.1-6.0.0.jar`, mod id `kubejsadditions`, versão `1.21.1-6.0.0`. KubeJS Additions amplia a **API de scripting** do KubeJS; sozinho não define a progressão/gameplay do pack sem scripts que consumam essas APIs.

## 1. Superfície confirmada
A publicação oficial 1.21.1 destaca suporte a **custom JEI categories/recipe types** e **Jade integration**. O projeto também se define como conjunto de events, wrappers e funcionalidades que não estão no KubeJS base.

## 2. Authority
KubeJS Additions é authority apenas das APIs/wrappers que registra. Recipes, tooltips, overlays e regras criadas por scripts pertencem ao script/config do pack e aos providers que eles modificam.
Não registrar no catálogo uma regra custom como feature nativa do addon sem localizar o script correspondente.

## 3. JEI integration
Categorias e recipe types customizados podem representar processos do pack. A representação no JEI não cria a recipe: o backend de recipe/processing continua sendo o registry/script/provider real.
Client-side registration deve permanecer em phase/context correto e não ser referenciado em dedicated-server paths.

## 4. Jade integration
A integração permite customizar informação exibida por Jade. Tooltip/overlay é representação; state de bloco/entity deve ser obtido do servidor/provider. Evitar usar texto renderizado como source of truth para quest/progressão.

## 5. Scripting lifecycle
KubeJS diferencia fases como startup/server/client e reloadable data. Uma API registrada ou chamada na fase errada pode causar missing registry, stale script state ou duplicate handler após reload. Scripts que dependem de KubeJS Additions devem ser testados em cold boot e `/reload`/resource reload conforme aplicável.

## 6. Client/server boundary
Custom JEI/Jade surfaces são predominantemente client-facing. Mutação de recipes, loot ou gameplay deve permanecer server-authoritative por meio do KubeJS/provider apropriado. Não enviar mutation arbitrária a partir de callbacks visuais.

## 7. Riscos
1. **API drift:** scripts quebram após update do addon/KubeJS.
2. **Wrong phase:** registration executada em startup/client/server incorreto.
3. **Duplicate handlers:** reload acumula listeners.
4. **Client leakage:** JEI/Jade class carregada em dedicated server.
5. **False authority:** GUI/tooltip tratado como state causal.
6. **Script dependency:** remover o addon sem procurar usos nos scripts gera falhas silenciosas ou startup errors.

## 8. Boundary para quests/perks
KubeJS Additions não concede Mastery por si. Se scripts criarem milestones, a causalidade e deduplicação pertencem ao script/provider. Uma categoria JEI, tooltip Jade ou callback visual nunca é completion event por si só.

## 9. Matriz de testes
- [ ] Dedicated server inicia com KubeJS Additions 6.0.0 + KubeJS físico atual.
- [ ] Startup scripts não geram missing method/class.
- [ ] Custom JEI categories registram sem duplicação após resource reload.
- [ ] Jade integrations exibem state sem alterar authority server-side.
- [ ] Script errors apontam claramente consumer/API incompatível.
- [ ] `/reload` não duplica handlers nem mantém state stale.
- [ ] Remoção/update só ocorre após busca por usos nos scripts do pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limitação
- Modlist física: KubeJS Additions 1.21.1-6.0.0 e KubeJS 2101.7.2-build.374 presentes.
- CurseForge oficial: Release NeoForge 1.21.1-6.0.0, custom JEI categories/recipe types e Jade integration.
- Os scripts efetivos do modpack não foram auditados nesta ficha; portanto nenhum uso concreto da API é inferido sem evidência de script.

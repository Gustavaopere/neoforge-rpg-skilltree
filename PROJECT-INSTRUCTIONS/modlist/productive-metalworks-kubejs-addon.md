# Productive Metalworks KubeJS Addon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db81e39dd0c39d6370f78d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `productive-metalworks-kubejs-addon-1.0.0.jar`, mod id `pmw_kubejs_addon`, runtime `1.0.0`; Productive Metalworks 1.15.1 e KubeJS presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, o addon 1.0.0, Productive Metalworks 1.15.1 e KubeJS estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Productive Metalworks KubeJS Addon
- **Arquivo JAR:** `productive-metalworks-kubejs-addon-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Automação, Metalurgia
- **Função:** Addon KubeJS para Productive Metalworks com helpers para melting, casting, alloying, remoção de receitas, duração customizada e combustíveis líquidos.
- **Dependências:** Productive Metalworks 1.15.1; KubeJS; NeoForge 1.21.1. Ambos os providers estão fisicamente presentes.
- **Sobreposição:** Interface de scripting do Productive Metalworks; não é um segundo sistema metalúrgico. Pode ser usada para harmonizar/remover rotas duplicadas.
- **Compatibilidade/Riscos:** Riscos: provider/KubeJS API drift, recipe IDs duplicados, remoção ampla por type, fluid tags ambíguas, fuels desbalanceados e reload inconsistente. Scripts locais não foram presumidos ativos sem leitura.
- **Observações:** Release 1.0.0 NeoForge 1.21.1 de 27/06/2026, Client & Server. Helpers publicados cobrem melting, item/block casting, alloying, recipe removal, duration e custom liquid fuels.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial KubeJS Metalworks 1.0.0 + documentação oficial de helpers.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-metalworks-kubejs-addon-for-productive/files/all
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Productive Metalworks KubeJS Addon 1.0.0 reconstruído: melting/casting/alloying helpers, removals, custom durations/fuels, KubeJS lifecycle, riscos e testes; Manter preservado.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `productive-metalworks-kubejs-addon-1.0.0.jar`, mod id `pmw_kubejs_addon`, versão `1.0.0`, NeoForge 1.21.1. O addon expõe helpers KubeJS para recipes e combustíveis do Productive Metalworks. Productive Metalworks 1.15.1 e KubeJS estão presentes; a decisão curatorial **Manter** é preservada.

## 1. Identidade e papel
- **Mod:** Productive Metalworks KubeJS Addon / KubeJS Metalworks.
- **JAR:** `productive-metalworks-kubejs-addon-1.0.0.jar`.
- **Mod id:** `pmw_kubejs_addon`.
- **Runtime:** `1.0.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** All Rights Reserved.
- **Papel:** facilitar scripting de Productive Metalworks via KubeJS.
- **Decisão:** Manter.

## 2. Dependências e ownership
O addon exige contexto real de **Productive Metalworks + KubeJS**. Ele não implementa foundry próprio.

Ownership:
- Productive Metalworks → recipe types, foundry, casting/alloying e fuels;
- KubeJS → runtime/eventos de script;
- addon → helpers que traduzem scripts para contracts do provider.

## 3. Helpers de melting
A documentação publica `PMW.melt(...)` para criar melting recipes, inclusive overload com duração customizada. Validar input, fluid output, quantidade e duração; valores definidos no script são parte da configuração do modpack, não defaults do Productive Metalworks.

## 4. Casting
São publicados helpers como `PMW.itemCast(...)` e `PMW.blockCast(...)`. Eles definem resultado, cast/forma e quantidade de fluido.

Testar conflito de recipe ID, fluid tags e comportamento após `/reload`.

## 5. Alloying
`PMW.alloy(...)` permite declarar ligas a partir de múltiplos fluidos/quantidades, incluindo tags como `#c:molten_copper`.

Riscos: proporção errada, tag ampla, rota duplicada com Create Metallurgy/TFMG e diferença entre recipe exibida e recipe server-side.

## 6. Remoção de recipes
O addon publica helpers de remoção por ID e por tipo. Essa superfície é destrutiva para o recipe graph do pack: scripts devem usar IDs/types específicos e ser auditados após update do provider.

## 7. Custom liquid fuels
`PMW.fuel(...)` adiciona combustível líquido com parâmetros de temperatura, velocidade/eficiência e consumo conforme API publicada.

Não assumir que qualquer fuel de outro mod esteja integrado sem script/config concreto.

## 8. KubeJS lifecycle
Mudanças de script podem ocorrer em startup/reload conforme o evento usado. Testar:
- cold boot;
- `/reload` quando aplicável;
- erro de sintaxe;
- recipe duplicada;
- provider ausente;
- update do Productive Metalworks.

O addon não deve deixar recipes stale após reload.

## 9. Integração metalúrgica
O pack contém várias rotas de metalurgia. O addon é ferramenta de curadoria para harmonizar recipes, não outro sistema de fundição.

Pode ser usado para remover duplicatas, definir ligas e alinhar fuels, mas cada mudança deve ser registrada no script correspondente.

## 10. Riscos
1. **Provider/API drift:** update do Productive Metalworks muda recipe contract.
2. **KubeJS drift:** evento/helper deixa de ser chamado como esperado.
3. **Duplicate IDs/recipes:** scripts concorrentes registram mesma rota.
4. **Broad removal:** remover por type apaga recipes não pretendidas.
5. **Tag ambiguity:** tags de molten fluids incluem providers inesperados.
6. **Fuel imbalance:** parâmetros customizados trivializam foundry.
7. **Reload inconsistency:** recipe stale entre cliente/servidor.

## 11. Matriz de testes
- [ ] Cliente e dedicated server iniciam com addon + KubeJS + Productive Metalworks.
- [ ] `PMW.melt` registra recipe e quantidade corretas.
- [ ] Duração customizada é respeitada.
- [ ] Item/block cast funcionam com cast e fluid corretos.
- [ ] Alloy por tags consome proporções esperadas.
- [ ] Remoção por ID/type remove somente alvo pretendido.
- [ ] Custom liquid fuel usa parâmetros definidos sem dupe.
- [ ] `/reload`/restart não duplica recipes.
- [ ] Script inválido falha de forma localizável sem corromper world state.
- [ ] JEI/visualização, quando aplicável, converge ao recipe server-side.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física: addon 1.0.0, Productive Metalworks 1.15.1 e KubeJS presentes.
- CurseForge oficial: project 1589907, Release NeoForge 1.21.1 de 27/06/2026, Client & Server.
- Documentação oficial: helpers para melting, casting, alloying, recipe removal, durations e custom liquid fuels.
- **Limite:** scripts KubeJS locais efetivamente usados pelo pack não foram abertos neste lote; nenhuma recipe custom foi declarada ativa sem evidência.

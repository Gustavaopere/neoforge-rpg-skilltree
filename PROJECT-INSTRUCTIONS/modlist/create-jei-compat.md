# Create JEI Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816fb363c664f7f9d23e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create JEI Compat
- **Arquivo JAR:** `createjeicompat-1.0.3.jar`
- **Versão 1.21.1:** 1.0.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL
- **Função:** Melhora a visualização de Sequenced Assembly do Create no JEI para receitas longas, adicionando paginação/controles e mantendo ingredientes fora da página pesquisáveis; a versão atual também funciona com EMI via JEmi quando esse stack está presente.
- **Dependências:** Create + JEI obrigatórios. Pack físico usa Create 6.0.10 e JEI 19.53.0.426. EMI/JEmi são integrações opcionais upstream e não foram encontrados como JARs top-level.
- **Sobreposição:** Não substitui JEI nem a integração JEI nativa do Create; adiciona paginação/navegação para recipes extensas de Sequenced Assembly.
- **Compatibilidade/Riscos:** Riscos: drift de mixin/category com Create/JEI; ingredientes off-page deixarem de ser pesquisáveis; layout/focus/tooltip stale; input de teclado/wheel interferir em widgets; classloading opcional EMI/JEmi. Source 1.0.3 foi desenvolvido contra Create 6.0.4 e JEI 19.27, abaixo das revisões do pack.
- **Observações:** JAR/mod id/runtime 1.0.3 e source matching confirmados. Mod client-side: altera apresentação/navegação de Sequenced Assembly no JEI, não recipes ou execução server-side.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/changelog oficiais 1.0.3 + source oficial Starior/CreateJeiCompat matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/createjeicompat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.0.3 com paginação de Sequenced Assembly, navegação por teclado/botões, ingredientes off-page pesquisáveis, refresh de layout, config showPageArrows, WrapMethod e boundary client-only catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 📖 **Identidade física e source matching confirmados:** `createjeicompat-1.0.3.jar`, mod id `createjeicompat`, runtime `1.0.3`, NeoForge 1.21.1. É um addon **client-side** de apresentação para Sequenced Assembly; não altera recipes nem execução server-side.

## 1. Papel e authority
Create JEI Compat melhora a apresentação de recipes longas de **Sequenced Assembly** do Create no JEI, adicionando paginação e controles de navegação. Create/Recipe Manager continuam authority do recipe e de seus steps; JEI e este addon apenas apresentam os dados ao cliente.

## 2. Dependências concretas
O source 1.0.3 declara Minecraft 1.21.1, Create e JEI, com EMI opcional via JEmi. O pack físico usa Create 6.0.10 e JEI 19.53.0.426. EMI/JEmi não foram encontrados como JARs top-level, portanto o caminho JEI está ativo e a integração EMI permanece apenas upstream.

## 3. Paginação de Sequenced Assembly
Recipes com muitos steps podem ser divididas em páginas. Mudar de página deve alterar apenas o layout visual; a lista real de steps, order, loops e ingredients pertence ao recipe carregado pelo Create.

## 4. Controles por botões e teclado
A 1.0.3 adiciona page-turn buttons e navegação por Up/Down/Left/Right através do fluxo de input da recipe category. Input deve ficar limitado ao painel relevante e não capturar teclas quando outra tela/widget possui foco.

## 5. Config `showPageArrows`
A configuração client-side `showPageArrows` controla a exibição das setas; o changelog informa default desligado e aplicação sem restart. O indicador `1/N` permanece visível. Config visual não pode alterar recipes nem sincronização de servidor.

## 6. Ingredientes fora da página
A 1.0.3 mantém ingredientes de páginas não visíveis registrados de forma invisível para que a busca do JEI ainda encontre a recipe. Isso evita que paginação reduza discoverability, mas exige que slots invisíveis não sejam desenhados/interagidos como slots ativos.

## 7. Atualização in-place do layout
Após troca de página, o addon atualiza o layout JEI/EMI in-place. Cursor, tooltip, ingredient focus e bounds precisam ser reconstruídos sem manter referências da página anterior.

## 8. `WrapMethod` em vez de overwrite
A 1.0.3 substituiu overwrite de métodos da categoria Sequenced Assembly por MixinExtras `WrapMethod`, explicitamente para reduzir interferência com outros mods que também atingem `setRecipe`. Isso diminui colisão estrutural, mas não elimina risco quando Create/JEI mudam assinatura ou ordering.

## 9. Mouse wheel
Em EMI/JEmi, o wheel sobre a recipe pode trocar steps/páginas e bloquear o page-scroll do EMI. Como EMI/JEmi não estão ativos no pack atual, esse caminho deve permanecer inerte e não criar classloading obrigatório.

## 10. Correções de layout 1.0.3
O changelog também corrige altura de `emptyBackground` na category do Create e compatibilidade de scroll de steps no EMI com recipes JEI 19/`RecipeHolder`. São correções client-side de layout/interop, não mudanças no processamento.

## 11. Busca e focus
Buscar por um ingredient que aparece apenas em página posterior deve continuar encontrando a recipe. Ao abrir o resultado, a visualização não deve falsificar o step atual nem perder o focus passado pelo JEI.

## 12. Client-only boundary
O mod não precisa participar de dedicated-server gameplay. Qualquer referência a classes client/JEI deve permanecer confinada ao lado cliente. A ausência do addon no servidor não deve alterar a validade das recipes Create.

## 13. Version drift do pack
O source matching foi desenvolvido com Create 6.0.4 e JEI 19.27, enquanto o pack usa Create 6.0.10 e JEI 19.53. Isso exige smoke-test da recipe category/mixins mesmo com a versão do addon exatamente pinada em 1.0.3.

## 14. Sobreposição
Não substitui JEI nem a integração JEI nativa do Create. Atua somente sobre a experiência de visualização de Sequenced Assembly extensa. Outros addons que patcham a mesma category são overlap técnico, não duplicata funcional automática.

## 15. Riscos
1. Mixin deixa de aplicar após update Create/JEI.
2. Página visual diverge dos steps reais da recipe.
3. Ingrediente off-page deixa de ser pesquisável.
4. Slot invisível recebe hover/click indevido.
5. Troca de página mantém tooltip/focus stale.
6. Teclado ou wheel interfere com outros widgets.
7. Layout in-place mantém bounds da página anterior.
8. Caminho EMI/JEmi tenta classloadar provider ausente.
9. Outro mod targeta `setRecipe` e ordering muda apesar de `WrapMethod`.

## 16. Matriz de testes
- [ ] Cliente inicia com Create 6.0.10 + JEI 19.53.0.426 + JEI Compat 1.0.3.
- [ ] Recipe curta permanece sem regressão visual.
- [ ] Recipe longa pagina todos os steps na ordem correta.
- [ ] Botões e teclas mudam apenas a página esperada.
- [ ] `showPageArrows` aplica sem restart.
- [ ] `1/N` permanece coerente com número de páginas.
- [ ] Ingredient presente só em página posterior continua pesquisável.
- [ ] Focus/tooltip não fica stale ao trocar página.
- [ ] Ausência de EMI/JEmi não gera erro de classloading.
- [ ] Resource/recipe reload não deixa layout antigo em cache.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
A modlist física confirma JAR/mod id/runtime 1.0.3. O source oficial matching confirma versão, MC 1.21.1, Create/JEI e EMI opcional. O changelog exato confirma paginação, inputs, ingredientes invisíveis para busca, refresh in-place, config de setas, `WrapMethod` e fixes JEI/EMI. Nenhuma alteração de recipe/gameplay foi atribuída ao addon.

> 🔒 **Boundary canônico:** Create JEI Compat controla apenas apresentação e navegação client-side. Recipe, ingredients, step order e execução continuam sob Create/Recipe Manager.

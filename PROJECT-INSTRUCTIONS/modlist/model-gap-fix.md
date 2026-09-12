# Model Gap Fix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816a8546c00ca659001a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `modelfix-1.21-1.10.jar`, mod id `modelfix`, runtime literal `1.21-1.10` e `modelfix.mixins.json` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”; a autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Model Gap Fix
- **Arquivo JAR:** `modelfix-1.21-1.10.jar`
- **Versão 1.21.1:** 1.21-1.10
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Correção client-side para pequenas frestas/gaps em block/item models associadas ao bug MC-73186, ajustando o tratamento das texturas/UVs sem criar gameplay próprio.
- **Dependências:** Cliente NeoForge 1.21/1.21.1. Não adiciona hard dependency funcional externa conhecida além da plataforma suportada.
- **Sobreposição:** Não substitui EMF/Fusion/Continuity/resource packs; corrige uma superfície específica de model baking/UV. Sobreposição somente técnica se outro mod transformar as mesmas funções.
- **Compatibilidade/Riscos:** Correção client-side do pipeline de modelos/UVs. Riscos: mixin collision/model-bake overlap, edge cases de resource packs e diagnóstico confundido com outros sistemas visuais. Nenhuma incompatibilidade concreta confirmada no pack atual.
- **Observações:** Runtime literal `1.21-1.10`; não normalizar para `1.10`. File ID 5591286, Release NeoForge 1.21/1.21.1. A modlist física registra `modelfix.mixins.json`.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release + source oficial MehVahdJukaar/modelfix-multi.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/model-gap-fix/files/5591286 | https://github.com/MehVahdJukaar/modelfix-multi
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — dossiê reconstruído para `modelfix-1.21-1.10.jar`; correção de MC-73186/model gaps, mixin surface, client boundary, performance, riscos e testes documentados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `modelfix-1.21-1.10.jar`, mod id `modelfix`, versão literal `1.21-1.10`, em NeoForge 1.21.1. A release exata é o CurseForge file ID `5591286`, publicada em 03/08/2024 para 1.21/1.21.1. O source oficial `MehVahdJukaar/modelfix-multi` é usado para explicar o mecanismo; a identidade/versionamento do binário continua sendo determinada pelo JAR físico e pela release publicada.

## 1. Identidade e papel
- **Mod:** Model Gap Fix.
- **JAR físico:** `modelfix-1.21-1.10.jar`.
- **Mod id:** `modelfix`.
- **Runtime:** `1.21-1.10`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** MehVahdJukaar.
- **Projeto CurseForge:** 676136; file ID 5591286.
- **Licença:** GPLv3.
- **Side funcional:** cliente.
- **Papel:** corrigir artefatos visuais de pequenas frestas/linhas em modelos de itens e blocos causados pelo tratamento das UVs no pipeline de model baking/renderização.

## 2. Problema que o mod corrige
O projeto referencia o bug Mojang `MC-73186`. A correção existe porque faces de modelos podem apresentar pequenos gaps entre quads/partes, especialmente em modelos customizados e itens 2D, quando a textura é efetivamente ampliada/ajustada no processo de geração do modelo.

Model Gap Fix remove esse zoom/expansão para que a face utilize a textura completa de forma coerente. O resultado esperado é eliminar linhas transparentes ou frestas visíveis entre elementos que deveriam se tocar.

## 3. Escopo de modelos
Segundo a documentação oficial do projeto, a correção se aplica a:
- modelos de blocos;
- modelos de itens;
- potencialmente outros modelos que passem pelas mesmas funções corrigidas.

Isso não equivale a substituir Entity Model Features, Fusion, Continuity ou resource packs. Esses projetos atuam em sistemas diferentes. Model Gap Fix intervém em um defeito geométrico/UV específico.

## 4. Implementação e mixin surface
A modlist física registra `modelfix.mixins.json`, confirmando que a build usa mixin para alterar o pipeline relevante. Não há evidência de gameplay content, capability, SavedData, recipe, entity, block ou networking próprio nesta build.

Por ser uma correção de render/model baking, o risco técnico está concentrado no cliente e nas mesmas classes/funções que outros mods de rendering/model loading possam transformar.

## 5. Performance
O autor descreve impacto de performance como essencialmente desprezível. Em itens com texturas côncavas pode haver pequeno aumento no número de quads produzidos. Isso deve ser entendido como característica possível da estratégia de correção, não como garantia de custo zero em qualquer resource pack.

No pack grande, qualquer suspeita de custo deve ser medida com profiling/render diagnostics antes de remover o mod por categoria.

## 6. Client/server e persistência
A funcionalidade é client-side:
- não muda regras de servidor;
- não altera dados persistentes do mundo;
- não muda loot, recipes, física ou atributos;
- não cria protocolo multiplayer próprio conhecido.

Em servidor dedicado, o teste relevante é apenas garantir que a composição do pack não provoque classloading indevido caso o JAR seja distribuído junto. A correção em si é observável no cliente.

## 7. Compatibilidade no pack
Superfícies a observar:
- resource packs com modelos complexos;
- loaders/frameworks de modelos;
- mods que alteram model baking/render de itens e blocos;
- otimizações de rendering.

Nenhuma incompatibilidade concreta com o stack atual foi confirmada nesta auditoria. A coexistência deve ser julgada por artefato visual real, não apenas porque dois mods atuam no rendering.

## 8. Changelog exato 1.10 para 1.21
A release correspondente ao JAR instalado publica apenas: **correção de um problema no NeoForge**. A ficha não atribui outras mudanças de releases anteriores a esta versão específica.

## 9. Riscos técnicos
1. **Mixin collision:** outro mod pode transformar a mesma função de geração/model baking.
2. **Resource-pack edge cases:** modelo não convencional pode revelar quads extras, z-fighting ou diferenças de UV.
3. **Diagnóstico incorreto:** glitches de EMF/entity model, connected textures ou shader não devem ser automaticamente atribuídos ao Model Gap Fix.
4. **Versão literal:** não normalizar `1.21-1.10` para `1.10`; a string física é authority deste catálogo.
5. **Rendering stack:** mudanças grandes em Modern UI, ImmediatelyFast ou outros render mods justificam smoke test visual mesmo sem conflito conhecido.

## 10. Matriz de testes
- [ ] Cliente NeoForge 1.21.1 inicia com Model Gap Fix e stack visual atual.
- [ ] Item 2D com bordas transparentes não exibe frestas indevidas.
- [ ] Bloco com modelo multipart/custom não apresenta gaps que o mod pretende corrigir.
- [ ] Resource pack principal carrega e recarrega sem model-bake crash.
- [ ] Trocar resource pack não deixa modelos stale.
- [ ] Verificar inventário, mão, item frame e bloco colocado para diferenças de UV.
- [ ] Testar com shaders/render optimizations atualmente instalados.
- [ ] Dedicated server do pack inicia sem referência client-only indevida causada pela distribuição do conjunto.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limites
- Modlist física atual: `modelfix-1.21-1.10.jar`, `modelfix`, runtime `1.21-1.10`, mixin `modelfix.mixins.json`.
- CurseForge oficial: Model Gap Fix, file ID 5591286, Release NeoForge para 1.21/1.21.1, 03/08/2024.
- Source oficial: `MehVahdJukaar/modelfix-multi`.
- README oficial: correção de MC-73186, remoção do zoom de textura, suporte a block/item models e observação de custo mínimo.
- **Limite:** esta auditoria não estabelece igualdade byte-a-byte entre um commit Git específico e o JAR físico; internals não são extrapolados além do mecanismo documentado.

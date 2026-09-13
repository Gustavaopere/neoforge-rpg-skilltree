# Let's Do addon for MineColonies

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db8132968eeb20cfdb17a2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — addon `2.1`, MineColonies `1.1.1381`, Compatibility `3.56`, Tweaks `3.33`, Farm & Charm `1.1.23`, Bakery `2.1.6` e Brewery `2.1.9` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Let's Do addon for MineColonies
- **Arquivo JAR:** `MineColonies_LetsDo-1.21.1-2.1.jar`
- **Versão 1.21.1:** 2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, RPG, Comida
- **Tipo de conteúdo:** Addon
- **Função:** Bridge que permite workers MineColonies usar stations/crops/recipes modernos de Let's Do. Chef: Cooking Pot/Crafting Bowl/Stove/Mincer/Roaster; Farmer: Mincer→seeds e Tomato; Baker: Caking Station/Crafting Bowl/Stove; módulos adicionais cobrem compat providers como Brewery.
- **Dependências:** Required no projeto: MineColonies, Compatibility addon for MineColonies e Tweaks addon. Mods Let's Do/Farm & Charm e compats são opcionais conforme módulo. Target 2.1: MC 1.1.1120, Compatibility 3.0, Tweaks 3.1; runtime físico usa 1.1.1381/3.56/3.33.
- **Sobreposição:** Não substitui MineColonies, Compatibility ou Let's Do. Adapta recipes/jobs/stations ao request/workflow colonial. Evitar duplicar as mesmas bridges em KubeJS/datapacks.
- **Compatibilidade/Riscos:** Bridge não oficial MineColonies↔Let's Do. Runtime MineColonies 1.1.1381 satisfaz mínimo 1.1.1120, mas permanece risco comportamental por grande drift. Compatibility 3.56/Tweaks 3.33 também superam baselines da 2.1. Brewery é gate prioritário porque 2.1 corrige crash com versão recente.
- **Observações:** Release 2.1 NeoForge 1.21.1 de 11/05/2026. Addon explicitamente não oficial. 2.1 corrige crash com versão recente de Let's Do Brewery. Source público confirma arquitetura modular e teaching screens; tag exata 2.1 não foi pinada neste lote.
- **Procedência:** Notion registra “modlist.txt física atual de 10/09/2026”; a autoridade física realmente acessível nesta exportação permanece o snapshot de 08/09/2026 com 595 top-levels. Somam-se CurseForge oficial file 8073689 + relações oficiais + source público gisellevonbingen-Minecraft/MineColonies_LetsDo, com limite de source pin documentado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-letsdo
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — release 2.1 reconciliada; Farm & Charm/Bakery/Brewery modules, teaching/recipes, required addon stack, MineColonies minimum semantics, request lifecycle, riscos e testes catalogados.
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada após inspeção do source 1.21.1. `Target Versions` são baseline de compilação; o `neoforge.mods.toml` usa mínimo de MineColonies, não pin. Decisão: Manter 2.1 com risco comportamental documentado.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `MineColonies_LetsDo-1.21.1-2.1.jar`, mod id `minecolonies_letsdo`, release oficial 2.1 para NeoForge 1.21.1 de 11/05/2026. O projeto é explicitamente **não oficial** em relação à equipe MineColonies. A documentação da publicação e o source público `gisellevonbingen-Minecraft/MineColonies_LetsDo` são usados para descrever a bridge; detalhes não demonstrados pela build 2.1 não são projetados.

## 1. Papel e fronteira de autoridade
Este addon conecta **MineColonies + Compatibility addon for MineColonies** ao ecossistema moderno **[Let's Do] Farm & Charm** e compats relacionados. Ele não substitui nenhum desses providers.
- MineColonies continua authority de citizens, jobs, buildings, requests e colony state.
- Compatibility addon fornece abstrações/integrações reutilizadas por este projeto.
- Mods Let's Do continuam authority de seus blocos, crops e recipes.
- MineColonies_LetsDo adapta esses elementos para trabalhadores e teaching recipes da colônia.

## 2. Escopo oficial 2.1
A página oficial declara suporte principal a **Farm & Charm** e compat addons associados. A família antiga `Legacy: [Let's Do] ...` não é o alvo desta linha; a própria página informa que legacy integrations ficam no Compatibility addon.
A 2.1 foi publicada especificamente para corrigir **crash com versão recente de Let's Do Brewery**.

## 3. Farm & Charm
Features oficiais documentadas:
- **Chef** pode usar Cooking Pot, Crafting Bowl, Stove, Mincer e Roaster;
- **Farmer** pode usar Mincer para transformar itens em seeds;
- Farmer pode colher **Tomato crop**.
Essas capacidades são adaptação de trabalho/recipe de MineColonies para stations/crops Let's Do; o addon não redefine a recipe original do provider como authority global.

## 4. Bakery
A documentação oficial da linha 2.1 confirma que o **Baker** pode usar:
- Caking Station;
- Crafting Bowl;
- Stove.
O source público contém módulos e teaching screens específicos de bakery, mostrando que a integração não é apenas tags: há UI/recipe teaching e módulos que conectam as stations ao fluxo de trabalho colonial.

## 5. Arquitetura modular
O source público expõe `ModuleManager`, `LetsDoV2Module` e módulos especializados como `bakery`, `brewery` e `farm_and_charm`. Isso permite habilitar integrações por provider disponível e evita que uma única classe concentre toda a bridge.
A existência de um módulo não deve ser interpretada como provider instalado. A ativação efetiva depende dos mods presentes e das condições implementadas na build.

## 6. Dependências da publicação 2.1
O arquivo oficial 2.1 foi construído/testado com baselines:
- MineColonies `1.1.1120-1.21.1-snapshot`;
- Structurize `1.0.786-1.21.1-snapshot`;
- Multi-Piston `1.2.51-1.21.1-snapshot`;
- Domum Ornamentum `1.0.19-1.21.1-snapshot`;
- BlockUI `1.0.216-RELEASE`;
- Tweaks addon `1.21.1-3.1`;
- Compatibility addon `1.21.1-3.0`.
Esses números são **target versions de publicação**, não pins máximos.
O source/metadata previamente auditado declara MineColonies 1.1.1120 como mínimo (`versionRange` aberto para cima). O runtime físico atual usa MineColonies 1.1.1381-snapshot, portanto satisfaz o contrato mínimo do loader; isso não elimina regressão comportamental.

## 7. Relações de projeto
A página de relações oficial lista como **required dependencies**:
- MineColonies;
- Compatibility addon for MineColonies;
- Tweaks addon for MineColonies.
Mods Let's Do como Farm & Charm e compats Bakery/Brewery/Candlelight aparecem como opcionais conforme a integração desejada.
Isso é importante para a modlist: remover Compatibility/Tweaks pode quebrar o addon mesmo quando Farm & Charm permanece instalado.

## 8. Client/server e UI
O source possui classes `common` e `client`, teaching screens e mixin configs separados. Recipe execution/job logic deve permanecer server-authoritative; screens apenas permitem configuração/ensino e representação.
Riscos de sync incluem recipe ensinada no cliente sem correspondência server-side, station removida durante job e provider Let's Do atualizado com schema/recipe type novo.

## 9. Requests, recipes e lifecycle
A bridge participa de recipes/jobs que alimentam o request system do MineColonies. Por isso devem ser testados:
- teaching de recipe;
- criação de request de ingredient;
- execução na station Let's Do correta;
- consumo e output exatos;
- entrega/settlement uma única vez;
- cancelamento quando station/building/provider deixa de existir.
Como a 2.1 corrigiu crash de Brewery recente, Brewery é uma surface de regressão prioritária.

## 10. Riscos
1. **MineColonies API drift:** 1.1.1381 é muito mais nova que o baseline 1.1.1120.
2. **Compatibility/Tweaks ABI drift:** runtime atual também avançou em relação aos targets 3.0/3.1.
3. **Let's Do recipe drift:** mudança de station/recipe type pode impedir teaching/execution.
4. **Brewery regression:** a própria 2.1 existe para corrigir crash com versão recente.
5. **Double integration:** Compatibility addon pode cobrir legacy providers; não duplicar recipe bridge em scripts/KubeJS.
6. **Request duplication:** output de station e settlement de MineColonies precisam permanecer exactly-once.
7. **Client UI mismatch:** teaching screen não deve ser usada como authority do recipe executável.

## 11. Matriz de testes
- [ ] Dedicated server inicia com MineColonies 1.1.1381 + Compatibility 3.56 + Tweaks 3.33 + addon 2.1.
- [ ] Chef aprende e executa Cooking Pot, Crafting Bowl, Stove, Mincer e Roaster onde aplicável.
- [ ] Farmer transforma item em seed no Mincer sem duplicação.
- [ ] Farmer reconhece/colhe Tomato crop.
- [ ] Baker aprende e executa Caking Station/Crafting Bowl/Stove.
- [ ] Brewery atual não reproduz o crash corrigido pela 2.1.
- [ ] Ingredient requests resolvem e fazem settlement uma vez.
- [ ] `/reload`/restart não perde teaching nem cria recipe duplicada.
- [ ] Provider Let's Do ausente desabilita apenas seu módulo, sem crash global, quando declarado opcional.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências
- Modlist física: `MineColonies_LetsDo-1.21.1-2.1.jar`.
- CurseForge oficial, file 8073689, release 2.1 de 11/05/2026.
- Página oficial: escopo Farm & Charm/Bakery, aviso de addon não oficial e relações required/optional.
- Source público `gisellevonbingen-Minecraft/MineColonies_LetsDo`: arquitetura modular e teaching screens.
- **Limite:** não foi localizado tag/commit de release 2.1 inequivocamente pinado neste lote; internals source são usados apenas quando coerentes com a publicação e não como prova byte-a-byte do JAR.

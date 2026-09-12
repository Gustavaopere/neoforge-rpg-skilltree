# [Let's Do] Bakery

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db812ba1abce18781da6e5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** [Let's Do] Bakery
- **Arquivo JAR:** `letsdo-bakery-neoforge-2.1.6.jar`
- **Versão 1.21.1:** 2.1.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida
- **Função:** Expande culinária com pães, bolos, cupcakes, doces e blocos/estações temáticos de padaria, integrado à linha Farm & Charm.
- **Dependências:** Obrigatórias e fisicamente presentes: [Let's Do] Farm & Charm 1.1.23 + Architectury 13.0.11 + Cloth Config 15.0.140. A linha Bakery 2.1.3+ exige Farm & Charm 1.1.15+, requisito satisfeito.
- **Sobreposição:** Complementa o stack culinário; não é equivalente integral a Farmer's Delight ou Candlelight.
- **Compatibilidade/Riscos:** Riscos: recipe/remainder dupe ou container loss, stale data após reload, food-effect double stacking, placeable-food state/collision e API drift Farm & Charm. 2.1.6 corrige early recipe-remainder initialization, TrayBlock hitbox e BreadCrate particles.
- **Observações:** JAR físico `letsdo-bakery-neoforge-2.1.6.jar`, mod id `bakery`, runtime 2.1.6. Release NeoForge 1.21.1 de 15/02/2026; variante feita para compat melhorada com Farm & Charm.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais [Let's Do] Bakery - Farm&Charm Compat 2.1.6.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-bakery-farm-charm-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; stations/recipes, food effects, container remainders, placement/collision, reload e integrations catalogados para 2.1.6.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🥐 **ESCOPO CANÔNICO.** Runtime físico: `letsdo-bakery-neoforge-2.1.6.jar`, mod id `bakery`, versão `2.1.6`. É a variante Bakery integrada ao ecossistema **[Let's Do] Farm & Charm**, Client & Server, com cadeia culinária, estações, alimentos colocáveis, efeitos e decoração.

## 1. Papel e conteúdo
Bakery adiciona pães, cupcakes, cakes, tarts, pastries e outros alimentos com preparação em múltiplas etapas. O projeto documenta Glazing, Baking e Caking stations, além de cakestands, breadbaskets, café furniture e alimentos colocáveis como decoração.

Não é apenas um reskin de Farmer's Delight/Farm & Charm: possui recipes, blocos e efeitos próprios, embora compartilhe ingredientes/tags e lógica culinária do stack.

## 2. Dependências físicas
A publicação lista como dependências obrigatórias **Farm & Charm, Architectury API e Cloth Config**. O pack contém:
- Farm & Charm 1.1.23;
- Architectury 13.0.11;
- Cloth Config 15.0.140.

A linha 2.1.3 passou a exigir Farm & Charm 1.1.15+, portanto o runtime físico 1.1.23 satisfaz o baseline publicado.

## 3. Recipes e estações
Baker Station recipes tornaram-se data-driven na linha 2.1.3. Isso torna datapack/reload uma superfície operacional: recipe IDs, ingredients, remainders e outputs devem convergir server-side e no recipe viewer após alteração de dados.

## 4. Food effects
A linha atual documenta efeitos próprios como **Sugar Rush** e **Vitality**. Sugar Rush pode empilhar bônus de movimento e, em stacks altos, attack speed; Vitality reduz periodicamente exhaustion. Os valores efetivos devem vir da build/config, não ser reimplementados por perks externos.

Outras comidas oferecem refeições mais saturantes e sweets com buffs curtos/empilháveis. O server continua authority de consumo, hunger/exhaustion e effects.

## 5. Container/remainder integrity
A 2.1.5 corrigiu containers como bottles, jars, bowls e buckets que não eram devolvidos após cooking e adicionou crafting remainder a Jam/Chocolate Spread. Em automação, remainder handling é um regression gate de dupe/loss.

A 2.1.6 corrigiu **early initialization de recipe remainders** que fazia o jogo avançar/rush durante startup, além de TrayBlock hitbox rotation e BreadCrate break particles.

## 6. Placement, decoração e collision
Muitos foods são placeable. TrayBlock orientation/hitbox deve acompanhar facing; displays/cakestands precisam conservar item/state após unload/reload. Break particles são client visual, não state authority.

## 7. Integrações do pack
Farm & Charm é a integração principal. Farmer's Delight e outros mods culinários do pack podem compartilhar tags/ingredientes/estações sem serem equivalentes. Preferir tags comuns quando semântica for realmente intercambiável e evitar recipe duplication por múltiplos datapacks/KubeJS.

## 8. Riscos
1. Recipe/remainder dupe ou container loss.
2. `/reload` deixa recipes/data stale.
3. Food effect empilha duas vezes com integração externa.
4. Placeable food perde state/item count.
5. Tray/display collision não acompanha facing.
6. Farm & Charm/API drift quebra recipe/ingredient assumptions.
7. KubeJS/other culinary addon redefine o mesmo recipe ID.

## 9. Boundary para quests/perks
Comer/craftar só deve contar quando o server confirma consumo/output. Abrir station/JEI ou colocar display não prova que uma receita foi concluída. Effects Bakery não devem ser duplicados por perk sem intenção explícita.

## 10. Matriz de testes
- [ ] Dedicated server inicia com Bakery 2.1.6 + Farm & Charm 1.1.23.
- [ ] Baker/Glazing/Caking flow produz output esperado.
- [ ] Bottles/jars/bowls/buckets retornam exatamente uma vez.
- [ ] Jam/Chocolate Spread remainder não duplica.
- [ ] `/reload` mantém recipes idempotentes.
- [ ] Sugar Rush/Vitality aplicam/removem sem stacking fantasma após relog.
- [ ] TrayBlock hitbox acompanha rotação.
- [ ] Placeable foods persistem após chunk unload/restart.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limite
CurseForge/Modrinth oficiais confirmam 2.1.6, dependências, superfícies culinárias/decorativas e changelogs 2.1.3–2.1.6. Registry counts e recipes efetivamente alterados por scripts do pack não foram inferidos sem ler os dados runtime.

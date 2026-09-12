# [Let's Do] Brewery

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db819fab82c6cebd676317
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** [Let's Do] Brewery
- **Arquivo JAR:** `letsdo-brewery-neoforge-2.1.9.jar`
- **Versão 1.21.1:** 2.1.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida
- **Função:** Adiciona cadeia de bebidas fermentadas e temática de brewery/brewfest, com produção e itens próprios integrados à linha Farm & Charm.
- **Dependências:** Obrigatórias e fisicamente presentes: [Let's Do] Farm & Charm 1.1.23 + Architectury 13.0.11 + Cloth Config 15.0.140. Brewery 2.1.6+ exige Farm & Charm 1.1.15+, requisito satisfeito.
- **Sobreposição:** Sobreposição parcial com outras bebidas/alimentos; possui produção, itens e estética próprios.
- **Compatibilidade/Riscos:** Riscos: output multiplication, quality/minigame desync, effect duration/level drift, Breathalyzer client-setup race, tag/recipe overlap e camera intoxication conflicts. 2.1.9 corrige concurrent item-property registration no NeoForge.
- **Observações:** JAR físico `letsdo-brewery-neoforge-2.1.9.jar`, mod id `brewery`, runtime 2.1.9. Release NeoForge 1.21.1 de 28/03/2026; variante Farm & Charm Compat.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais [Let's Do] Brewery - Farm&Charm Compat 2.1.9.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-brewery-farm-charm-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; brewing/quality, effects/intoxication, tags, output integrity, Breathalyzer regression e lifecycle catalogados para 2.1.9.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🍺 **ESCOPO CANÔNICO.** Runtime físico: `letsdo-brewery-neoforge-2.1.9.jar`, mod id `brewery`, versão `2.1.9`. Brewery é a extensão de bebidas/fermentação do stack Farm & Charm, Client & Server, com drying, brewing, aging, Beer/Whiskey, efeitos, Brewfest e decoração temática.

## 1. Cadeia de produção
O projeto documenta preparação de ingredientes, drying, brewing e aging/refinement antes do consumo. Beer e Whiskey são produtos centrais; estações/kettles e o fluxo de brewing têm estado próprio e podem envolver eventos/minigame de qualidade.

Farm & Charm fornece a integração agrícola/culinária base, mas Brewery mantém recipes, efeitos e blocks próprios.

## 2. Dependências físicas
A publicação lista **Farm & Charm, Architectury API e Cloth Config** como required dependencies. O pack contém Farm & Charm 1.1.23, Architectury 13.0.11 e Cloth Config 15.0.140.

A linha 2.1.6 exige Farm & Charm 1.1.15+, portanto o runtime físico satisfaz esse requisito.

## 3. Brewing quality e minigame
A 2.1.8 reequilibra qualidade: nenhum evento resolvido produz quality 0; resolver 2–4 eventos produz quality 2; quality 3 exige resolver todos os eventos. Netherite Brewing Stations passaram a funcionar em comfort mode sem exigir minigame.

Quality é state do processo Brewery; quests/perks não devem recalcular essa pontuação de forma paralela.

## 4. Integridade de output
2.1.8 corrige bug em que o output do brewing aumentava a cada brew subsequente no mesmo kettle. Esse fix é um regression gate de economia: reutilizar a mesma estação não pode multiplicar output progressivamente.

Também corrigiu crash do Brew Oven com Decorative Blocks: Reborn.

## 5. Efeitos e intoxicação
A linha 2.1.x inclui efeitos de bebidas; 2.1.6 corrigiu níveis de Beer/Whiskey e formatação de duração. A linha 2.1.4 documenta intoxication progressiva com camera sway, movement penalty, nausea e blackout em níveis altos.

Gameplay effects devem ser server-authoritative; camera sway é apenas apresentação client-side.

## 6. Breathalyzer e client setup
A build exata 2.1.9 corrige crash NeoForge causado por **concurrent item property registration do Breathalyzer durante client setup**. Esse é o principal regression gate específico da 2.1.9: abertura/uso/render do item não pode reintroduzir race de setup.

## 7. Tags e compatibilidade agrícola
2.1.7 move drying recipes de Corn para tag `c:crops/corn`, melhorando compatibilidade entre providers. Isso é o padrão correto: usar tags quando os crops são semanticamente equivalentes, sem hardcodar um único item de Farm & Charm.

## 8. Brewfest e decoração
O mod inclui roupas temáticas como Lederhosen/Dirndl e blocos/food blocks de Oktoberfest/tavern. Essa superfície é conteúdo, não a authority da progressão de bebidas. Armor rendering teve fixes na linha 2.1.x e deve ser smoke-tested com o stack visual do pack.

## 9. Riscos
1. Brewing output volta a escalar a cada batch no mesmo kettle.
2. Quality/minigame diverge server/client.
3. Effect level/duration fica stale após relog.
4. Breathalyzer item-property race no client setup.
5. Corn/tag compat cria recipes duplicadas.
6. Bucket/container consumption incorreta.
7. Camera/intoxication conflita com outros camera/movement mods.
8. Farm & Charm/API drift quebra stations/ingredients.

## 10. Boundary para quests/perks
Uma bebida só deve contar quando o server confirma recipe completion/consumo. Participar do minigame ou abrir Brewing Station não basta. Quality pode ser lida do provider quando exposta de forma segura; não reconstruir resultado por heurística client-side.

## 11. Matriz de testes
- [ ] Dedicated server inicia com Brewery 2.1.9 + Farm & Charm 1.1.23.
- [ ] Mesmo kettle produz quantidade estável em batches consecutivos.
- [ ] Quality 0/2/3 respeita eventos/comfort mode conforme configuração.
- [ ] Beer/Whiskey aplicam levels/durations corretos.
- [ ] Intoxication server state persiste e camera effect limpa corretamente.
- [ ] Breathalyzer não causa race/crash no client setup.
- [ ] Corn de providers compatíveis resolve via tag sem duplicar recipe.
- [ ] Brew Oven não reproduz crash de compat corrigido.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limite
CurseForge/Modrinth oficiais confirmam 2.1.9, dependências e changelog 2.1.4–2.1.9. Config efetiva de comfort mode, recipes e balanceamento do pack não foi lida nesta ficha; não assumir defaults como política final do servidor.

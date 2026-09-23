# Create: Wizardry

> **Autoridade física atual — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#166**: JAR `create_wizardry-1.21.1-0.5.1-pre1.jar`, mod id `create_wizardry`, runtime `1.21.1-0.5.1-pre1`, SHA-1 `5aa96dd49136ea855ccdf441b606cc26fd88b927`.

## Propriedades do registro

- **Mod:** Create: Wizardry
- **Arquivo JAR:** create_wizardry-1.21.1-0.5.1-pre1.jar
- **Versão 1.21.1:** 1.21.1-0.5.1-pre1
- **Categoria:** Compat, Magia, Tecnologia, Automação
- **Função:** Bridge entre Create e Iron's Spells 'n Spellbooks que adiciona processamento/automação e conteúdo cruzado para materiais mágicos como Arcane Essence, Liquid Mana e inks.
- **Dependências:** Create + Iron's Spells 'n Spellbooks obrigatórios para a função central. A build física 0.5.1-pre1 é NeoForge 1.21.1, Client & Server.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** 0.5.1-pre1 é Pre-Release/Beta; 0.5.0 é a Release estável imediatamente anterior. Changelog confirmado da pre1: Mana Siphon em diferentes direções e drenagem de Mana de blocos/itens, convertendo-os para contrapartes mundane e retornando 50% da Mana armazenada. Riscos: dupe/loss nessa conversão, recipe overlap, Create/Iron's drift e regressões próprias da prerelease.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-wizardry
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_wizardry` 1.21.1-0.5.1-pre1 + CurseForge/Modrinth oficiais revalidados em 20/09/2026; 0.5.1-pre1 permanece o artefato físico Beta/Pre-Release, enquanto 0.5.0 permanece a Release estável anterior.
- **Observações:** JAR `create_wizardry-1.21.1-0.5.1-pre1.jar`; runtime 1.21.1-0.5.1-pre1. Pre-Release/Beta de 28/06/2026; 0.5.0 é Release estável de 23/06/2026. Delta oficial da pre1: Mana Siphon orientável e drenagem de Mana de blocos/itens para contraparte mundane com 50% da Mana armazenada.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — lote físico #165: create_wizardry-1.21.1-0.5.1-pre1.jar / runtime 1.21.1-0.5.1-pre1 confirmados; changelog da pre1 preservado: Mana Siphon orientável e drenagem de Mana de blocos/itens com 50% de retorno.
- **Decisão:** Manter
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada em `Manter`. A presença do bridge Create↔Iron's Spells foi aprovada; a build instalada 0.5.1-pre1 permanece registrada, com 0.5.0 como fallback estável recomendado se a prerelease apresentar regressão. Em 08/09/2026, decisão e data foram preservadas e a ficha reconstruída ao padrão técnico.
- **Sobreposição:** É bridge específico Create↔Iron's Spells, não substitui os mods-base. Pode cruzar outros addons que automatizam Arcane Essence/mana/ink; deduplicar somente recipes concretos, mantendo uma única authority por conversão.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> 🪄 Versão física confirmada: `create_wizardry-1.21.1-0.5.1-pre1.jar`, runtime `1.21.1-0.5.1-pre1`. É uma **Pre-Release/Beta** do bridge Create ↔ Iron's Spells 'n Spellbooks; decisão formal do pack: **Manter** desde 06/09/2026.

## 1. Papel e authority
Create: Wizardry conecta automação/processamento Create a materiais e conteúdo de Iron's Spells. **Create** continua authority de kinetics/processamento; **Iron's Spells** continua authority de spellcasting, mana e sua progressão mágica; Wizardry controla recipes/conteúdo de integração próprios.
## 2. Arcane Essence
Material público do projeto confirma recipes/processamento envolvendo **Arcane Essence**. Input, processamento e rendimento precisam vir do recipe manager da build; scripts externos não devem criar uma segunda conversão equivalente sem policy explícita.
## 3. Liquid Mana
O projeto confirma **Liquid Mana** como superfície de integração. Volume, conversion rate e container behavior não são congelados nesta ficha porque não foi localizado contrato granular da 0.5.1-pre1. O state server-side e os recipes reais prevalecem.
## 4. Inks
Material público confirma recipes para os cinco tiers de Ink: **Common, Uncommon, Rare, Epic e Legendary**. A cadeia Create pode automatizar parte da produção, mas rarity/progression dos inks continua vinculada ao ecossistema mágico que os consome.
## 5. Electromancer Robe
A galeria/documentação pública confirma **Electromancer Robe** como conteúdo do projeto. Stats, atributos e interação exata com spell power/mana não são inventados sem source/JAR pin da pre1; o runtime é authority.
## 6. Processing Create
Mixing, filling, pressing ou outras primitives só devem ser atribuídas quando o recipe real da build as registra. Wizardry não cria uma segunda máquina abstrata: ele usa o sistema Create para recipes próprios.
## 7. Spell/mana authority
Automatizar materiais mágicos não transfere ownership do spell system. Mana pool, cast cost, cooldown, spell school e resolução de cast continuam no provider mágico correspondente.
## 8. Recipe overlap
Outros addons podem adicionar rotas para Arcane Essence, ink ou materiais mágicos. Coexistência pode reduzir custos/progression. Comparar recipe IDs, inputs, heat, fluids e outputs antes de qualquer deduplicação.
## 9. Pre-Release 0.5.1-pre1
A build instalada é **0.5.1-pre1**, publicada como Beta/Pre-Release. A **0.5.0** é a Release estável anterior. O changelog oficial da pre1 confirma dois deltas concretos: o **Mana Siphon pode ser colocado em diferentes direções** e passa a **drenar Mana de blocos e itens, convertendo-os para suas contrapartes mundane e concedendo 50% da Mana armazenada**. Esses comportamentos são regression gates específicos da build física; parâmetros além dos publicados continuam fail-closed.
## 10. Fallback 0.5.0
A decisão histórica do pack preserva 0.5.0 como fallback estável caso a pre1 apresente regressão. Isso não significa downgrade automático: o runtime físico atual permanece 0.5.1-pre1 até a modlist mudar.
## 11. Client/server
Recipes, fluids, inventories, mana-related material settlement e equipment effects são common/server quando afetam gameplay. Models, particles, JEI/Ponder/screens são client-facing.
## 12. Lifecycle
Validar startup com Create + Iron's, recipe/datapack reload, fluid/container transitions, server restart, optional viewer reload e update isolado de qualquer mod-base.
## 13. Riscos
1. Recipe de Arcane Essence duplicado.
2. Liquid Mana convertido/creditado duas vezes.
3. Ink progression ficar excessivamente barata.
4. Equipamento mágico receber modifiers duplicados.
5. Create update quebrar processing integration.
6. Iron's update alterar item/fluid/tag esperado.
7. Pre-Release 0.5.1-pre1 possuir regressão nos novos caminhos de orientação/drenagem do Mana Siphon.
8. Documentação de versão antiga ser atribuída à pre1.
## 14. Matriz de testes
1. Dedicated server boot com Create + Iron's + Wizardry.
2. Recipe real de Arcane Essence.
3. Liquid Mana: produção, transporte/container e consumo conforme JEI/runtime.
4. Common/Uncommon/Rare/Epic/Legendary Ink recipes.
5. Electromancer Robe: equip/unequip e modifiers reais.
6. Recipe reload sem duplication.
7. Restart com fluid/inventory state preservado.
8. Comparação com outras bridges mágicas instaladas.
9. Mana Siphon em múltiplas orientações + drenagem de Mana de bloco/item, validando conversão mundane e retorno de 50% sem dupe/loss.
10. Smoke-test geral da 0.5.1-pre1; registrar 0.5.0 apenas como fallback, não como runtime.
## 15. Evidência
- modlist física 08/09/2026: `create_wizardry-1.21.1-0.5.1-pre1.jar`;
- CurseForge/Modrinth oficiais: bridge Create↔Iron's Spells, Client & Server; 0.5.1-pre1 Beta e 0.5.0 Release;
- material público do projeto: Arcane Essence, Liquid Mana, cinco tiers de Ink e Electromancer Robe;
- changelog oficial 0.5.1-pre1: Mana Siphon orientável; drenagem de Mana de blocos/itens, conversão para contraparte mundane e retorno de 50% da Mana armazenada.
> 🔒 Boundary canônico: **Wizardry automatiza/integra materiais; Iron's continua decidindo magia e Create continua decidindo processamento**. A prerelease física é registrada sem fabricar um changelog inexistente.

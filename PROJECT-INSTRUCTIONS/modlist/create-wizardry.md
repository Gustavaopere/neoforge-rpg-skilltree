# Create: Wizardry

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8111a25bc164c88eea3c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Wizardry
- **Arquivo JAR:** `create_wizardry-1.21.1-0.5.1-pre1.jar`
- **Versão 1.21.1:** 1.21.1-0.5.1-pre1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Magia, Tecnologia, Automação
- **Função:** Bridge entre Create e Iron's Spells 'n Spellbooks que adiciona processamento/automação e conteúdo cruzado para materiais mágicos como Arcane Essence, Liquid Mana e inks.
- **Dependências:** Create + Iron's Spells 'n Spellbooks obrigatórios para a função central. A build física 0.5.1-pre1 é NeoForge 1.21.1, Client & Server.
- **Sobreposição:** É bridge específico Create↔Iron's Spells, não substitui os mods-base. Pode cruzar outros addons que automatizam Arcane Essence/mana/ink; deduplicar somente recipes concretos, mantendo uma única authority por conversão.
- **Compatibilidade/Riscos:** 0.5.1-pre1 é Pre-Release/Beta; 0.5.0 é a Release estável imediatamente anterior. Não foi localizado changelog granular que demonstre delta funcional indispensável da pre1. Riscos: recipe duplication com outras bridges, mana/ink conversion, Create version drift e integração de equipamento mágico.
- **Observações:** JAR `create_wizardry-1.21.1-0.5.1-pre1.jar`; runtime 1.21.1-0.5.1-pre1. 0.5.1-pre1 é Beta/Pre-Release de 28/06/2026; 0.5.0 é Release estável de 23/06/2026. Conteúdo público confirmado inclui Arcane Essence, Liquid Mana, cinco tiers de Ink e Electromancer Robe; sem granular changelog da pre1, detalhes adicionais ficam fail-closed.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime 1.21.1-0.5.1-pre1 + CurseForge/Modrinth oficiais do projeto/build e evidência pública de recipes/conteúdo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-wizardry
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — Create↔Iron's bridge authority, mana/ink/essence processing, recipe/equipment boundaries, prerelease risk e fail-closed delta 0.5.1-pre1 catalogados.
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada em `Manter`. A presença do bridge Create↔Iron's Spells foi aprovada; a build instalada 0.5.1-pre1 permanece registrada, com 0.5.0 como fallback estável recomendado se a prerelease apresentar regressão. Em 08/09/2026, decisão e data foram preservadas e a ficha reconstruída ao padrão técnico.
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
A build instalada é **0.5.1-pre1**, publicada como Beta/Pre-Release. A **0.5.0** é a Release estável anterior. Não foi localizado changelog granular da pre1 que permita afirmar mudanças específicas; portanto a ficha não inventa deltas.

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
7. Pre-Release 0.5.1-pre1 possuir regressão não documentada.
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
9. Smoke-test da 0.5.1-pre1; registrar 0.5.0 apenas como fallback, não como runtime.

## 15. Evidência
- modlist física 08/09/2026: `create_wizardry-1.21.1-0.5.1-pre1.jar`;
- CurseForge/Modrinth oficiais: bridge Create↔Iron's Spells, Client & Server; 0.5.1-pre1 Beta e 0.5.0 Release;
- material público do projeto: Arcane Essence, Liquid Mana, cinco tiers de Ink e Electromancer Robe;
- ausência de changelog granular da pre1 tratada explicitamente fail-closed.

> 🔒 Boundary canônico: **Wizardry automatiza/integra materiais; Iron's continua decidindo magia e Create continua decidindo processamento**. A prerelease física é registrada sem fabricar um changelog inexistente.
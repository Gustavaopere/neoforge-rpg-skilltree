# Crystal Chronicles

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81e58effeaca501edc13
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Crystal Chronicles
- **Arquivo JAR:** `crystal_chronicles-0.1.3-alpha.jar`
- **Versão 1.21.1:** 0.1.3-alpha
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG, Exploração
- **Função:** Addon pós-game de Iron's Spells 'n Spellbooks com tiers superiores de armaduras/armas/staffs, conjunto Prismatic, Bismuth Formations no End, blocos temáticos e uma dimensão cavernosa alpha organizada por escolas de magia.
- **Dependências:** Required oficialmente: AzureLib, Iron's Spells 'n Spellbooks, Biolith e Fusion. JEI/REI e Better Combat são recomendados, não obrigatórios. Projeto declara incompatibilidade com OptiFine.
- **Sobreposição:** Expande diretamente Iron's Spells 'n Spellbooks e pode cruzar outros addons de equipamentos/dimensões mágicas, mas possui worldgen, progression e assets próprios. Comparar escolas/equipment/worldgen concretos antes de classificar redundância.
- **Compatibilidade/Riscos:** 0.1.3-alpha é explicitamente WIP/Alpha: equipment recipes ainda são placeholder e a dimensão está incompleta. Worldgen/portal/progression podem mudar entre builds. Connected textures dependem de Fusion; biome/worldgen depende de Biolith; OptiFine é declarado incompatível.
- **Observações:** JAR `crystal_chronicles-0.1.3-alpha.jar`; runtime 0.1.3-alpha; Client & Server. Features confirmadas: 3D armors/weapons por schools, Prismatic set, Bismuth Formations no End, Bismuth Portal Frame + Chisel + Prismatic Portal spell, cave dimension alpha e building blocks/connected textures. Recipes atuais são placeholder.
- **Procedência:** Modlist física canônica de 08/09/2026, 600 top-levels + runtime 0.1.3-alpha + CurseForge oficial da release/projeto e lista oficial de dependências/features.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/crystal-chronicles
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — ISS postgame/equipment authority, Bismuth End biome, portal/dimension WIP, placeholder recipes, connected-texture dependencies, client/server lifecycle e alpha risks catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Crystal Chronicles 0.1.3-alpha foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. A antiga data 26/08 ligada a `Sem decisão` foi removida porque não havia histórico de decisão formal.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 💎 Versão física confirmada: `crystal_chronicles-0.1.3-alpha.jar`, runtime `0.1.3-alpha`, NeoForge 1.21.1. O próprio projeto classifica a progressão/dimensão como **WIP/Alpha**.

## 1. Papel e authority
Crystal Chronicles estende o pós-game de Iron's Spells 'n Spellbooks. Iron's continua authority de schools, spellcasting, mana e base equipment semantics; Crystal Chronicles controla seus upgrades, items, worldgen, portal e dimensão própria.

## 2. Armaduras e armas 3D
O projeto adiciona tiers superiores das armaduras de várias Schools of Magic, além de novas weapons/staffs. Os modelos são 3D e o objetivo é aumentar stats como mana/spell power. Valores exatos precisam vir do runtime 0.1.3-alpha; não são congelados sem source/JAR pin.

## 3. Prismatic set
Há um conjunto **Prismatic** descrito como penúltimo tier. Sua posição na progressão é conteúdo próprio do addon; outros sistemas de affix/upgrades não devem duplicar modifiers sem policy explícita.

## 4. Placeholder recipes
A documentação declara que os equipamentos estão atualmente craftable por **placeholder recipes**. Isso é risco de balanceamento e migration: recipe atual não deve ser tratado como progressão final garantida.

## 5. Bismuth Formations
O addon adiciona o biome **Bismuth Formations** no End. Worldgen é provider próprio com suporte de Biolith; chunks já gerados não devem ser assumidos retroativos após alterações futuras.

## 6. Portal
O fluxo público usa Bismuth Portal Frame, montagem com **Bismuth Portal Chisel** e depois o **Prismatic Portal spell** para abrir o acesso. Portal assembly e spell trigger precisam convergir no servidor uma única vez.

## 7. Dimensão cavernosa Alpha
O destino é uma grande dimensão subterrânea dividida em biomes associados às Schools of Magic. O projeto a chama explicitamente de Alpha/WIP; ausência de biome/mob/estrutura planejada não é bug automaticamente.

## 8. Biomes por escola
A visão de longo prazo é pelo menos um biome ou estrutura por School. A documentação diferencia features atuais de planned features; esta ficha não promove conteúdo planejado a runtime 0.1.3-alpha.

## 9. Crystals e progression WIP
O projeto planeja crystals raros por school, usados para upgrades e protegidos por mecanismos/ameaças. Como parte dessa progressão ainda é WIP, stats, spawn rates e defesa não são registrados como contratos estáveis sem prova da build.

## 10. Thematic blocks
Cada biome adicionado inclui building blocks e variantes como bricks/chiseled/stairs/slabs. Alguns Bismuth blocks usam connected textures, justificando dependência de Fusion.

## 11. Dependências
A página oficial lista como Required: **AzureLib, Iron's Spells 'n Spellbooks, Biolith e Fusion**. JEI/REI e Better Combat são recomendações. Isso separa dependency hard de QoL/combat optional.

## 12. OptiFine
O projeto declara incompatibilidade com OptiFine e sugere Sodium + Iris como alternativa. Esse é um boundary de render explícito; não atribuir o problema a worldgen/gameplay sem evidência.

## 13. Client/server
Worldgen, portal, equipment state e progression são common/server. Models, connected textures e efeitos visuais são client-facing. Dedicated server não deve depender de renderer para registrar conteúdo common.

## 14. Lifecycle
Validar first worldgen, End exploration, portal assembly/open/close, dimension travel, restart dentro da dimensão, resource reload e update de dependências.

## 15. Riscos
1. Placeholder recipes quebrar balanceamento.
2. Alpha worldgen mudar entre updates e gerar seams.
3. Portal state duplicar ou ficar preso.
4. Equipment modifiers duplicarem com outro addon.
5. Fusion/Biolith version drift quebrar render/worldgen.
6. Feature planejada ser documentada como já implementada.
7. OptiFine causar falha visual/crash.
8. Update de Iron's mudar schools/items esperados.

## 16. Matriz de testes
1. Dedicated server boot com quatro dependências required.
2. Client join sem OptiFine.
3. Equip/unequip de um set school-specific e Prismatic.
4. Confirmar recipes atuais como placeholder no recipe manager.
5. Gerar Bismuth Formations em chunk novo.
6. Montar portal com frames/chisel e abrir com Prismatic Portal spell.
7. Entrar/sair da dimensão e reiniciar servidor dentro dela.
8. Connected textures de Bismuth após resource reload.
9. Upgrade de Iron's/Biolith/Fusion em cópia do mundo.

## 17. Evidência
- modlist física 08/09/2026: 0.1.3-alpha;
- CurseForge oficial: postgame ISS, 3D equipment, Prismatic set, Bismuth Formations, portal e cave dimension Alpha;
- página oficial: AzureLib + Iron's + Biolith + Fusion required; OptiFine incompatível;
- documentação separa claramente features atuais de planned/WIP progression.

> 🔒 Boundary canônico: **Crystal Chronicles expande o pós-game de Iron's; não redefine o core de spellcasting**. Tudo marcado WIP/Alpha deve permanecer tratado como contrato instável até evidência versionada futura.
# Monsters & Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db813ab20ff22d764cec7d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — Monsters & Spellbooks `0.0.16.3`, Ace's Spell Utils `1.2.7.2-1.21.1`, AzureLib `3.1.11`, Iron's `3.16.3` e AttributeFix `21.1.3` confirmados fisicamente; Better Combat não aparece top-level
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”; a autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Monsters & Spellbooks
- **Arquivo JAR:** `monstersspellbooks-0.0.16.3.jar`
- **Versão 1.21.1:** 0.0.16.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG, Mobs, Worldgen
- **Função:** Grande addon de Iron's Spells com 90+ spells, 2 novas schools, armor/weapons, ores, accessories/slot, mobs spellcasters e estruturas de Overworld; foco atual forte em Necro/curses/DoT.
- **Dependências:** Required oficiais: Ace's Spell Utils, AzureLib, Iron's Spells 'n Spellbooks. Optional: AttributeFix e Better Combat. Pack contém Ace's 1.2.7.2, AzureLib 3.1.11, Iron's 3.16.3 e AttributeFix 21.1.3; Better Combat não foi identificado.
- **Sobreposição:** Grande interseção temática com outros addons de Iron's, porém conteúdo próprio. Avaliar power scaling, schools/attributes, gear e worldgen antes de qualquer decisão de redundância.
- **Compatibilidade/Riscos:** Addon grande e em desenvolvimento ativo. Required: Ace's Spell Utils, AzureLib e Iron's Spells. AttributeFix optional está instalado; Better Combat optional não foi encontrado. Riscos: attribute/effect/tag regressions, jewelry/runes, worldgen density, mob spell AI, power creep e source público desatualizado.
- **Observações:** Runtime 0.0.16.3, file ID 8788560, Release 01/09/2026. Source público encontrado ainda declara 0.0.14 e foi tratado apenas como evidência divergente, não pin exato.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release/relations + source público RedReaper28 explicitamente divergente em versão.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/monsters-spellbooks-iss/files/8788560 | https://www.curseforge.com/minecraft/mc-mods/monsters-spellbooks-iss/relations/dependencies | https://github.com/RedReaper28/Monsters-Spellbooks-1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Monsters & Spellbooks 0.0.16.3 reconstruído: 90+ spells/2 schools, gear/mobs/ores/structures, deps oficiais, changelog exato, source 0.0.14 divergente, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `monstersspellbooks-0.0.16.3.jar`, mod id `monstersspellbooks`, versão `0.0.16.3`, NeoForge 1.21.1. A release exata é o CurseForge file ID `8788560`, publicada em 01/09/2026. O source público `RedReaper28/Monsters-Spellbooks-1.21.1` ainda declara `0.0.14`; por isso é usado apenas como evidência de arquitetura/linha de dependências quando coerente, **não** como source exato de 0.0.16.3.

## 1. Identidade, versão e papel
- **Mod:** Monsters & Spellbooks: Iron's Spells 'n Spellbooks Addon.
- **JAR físico:** `monstersspellbooks-0.0.16.3.jar`.
- **Mod id:** `monstersspellbooks`.
- **Runtime:** `0.0.16.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor público:** RedTablos / source credit RedReaper.
- **CurseForge project ID:** 1428928; file ID 8788560.
- **Licença:** MIT.
- **Papel:** expansão grande de Iron's Spells 'n Spellbooks com spells, escolas, equipamento, mobs, ores, accessories e estruturas.

## 2. Dependências oficiais da release
A página de relações oficial lista cinco relações para o projeto:
- **Ace's Spell Utils — Required Dependency.**
- **AzureLib — Required Dependency.**
- **Iron's Spells 'n Spellbooks — Required Dependency.**
- **AttributeFix — Optional Dependency.**
- **Better Combat — Optional Dependency.**

A modlist física contém:
- `aces_spell_utils-1.2.7.2-1.21.1.jar`;
- `azurelib-neo-1.21.1-3.1.11.jar`;
- `irons_spellbooks-1.21.1-3.16.3.jar`;
- `attributefix-neoforge-1.21.1-21.1.3.jar`.

Better Combat não foi encontrado como top-level na modlist atual durante esta auditoria. Portanto sua integração não é tratada como ativa.

## 3. Escopo publicado do conteúdo
A descrição oficial atual apresenta aproximadamente:
- **90+ spells**;
- **2 novas spell schools**;
- **12 armor sets**;
- **30 weapons**;
- **5 ores**;
- **10 accessories + um novo accessory slot**;
- **10 mobs com spells especiais**;
- **2 estruturas de Overworld**.

Esses números são catálogo publicado do projeto, não contagem independente de registry IDs feita nesta auditoria. Para KubeJS/perks/datapacks, usar IDs reais da build 0.0.16.3.

## 4. Escolas e direção de design
A descrição atual destaca a escola **Necro** como foco principal de desenvolvimento, com identidade orientada a:
- debuffs;
- dano ao longo do tempo;
- curses;
- efeitos/visuais próprios.

O mod também publica uma segunda escola nova e grande número de spells adicionais. Sem source exato de 0.0.16.3, esta ficha não inventa nome/registry ID da escola não confirmada por material atual específico.

**Ownership:** Iron's Spells continua authority do framework de spellcasting, mana/cooldowns, spell registry base e contratos de escola. Monsters & Spellbooks adiciona escolas/spells/conteúdo próprios sobre essa infraestrutura.

## 5. Gear tiers: Arch e Hybrid
O autor descreve duas categorias conceituais de equipamento:
- **Arch:** orientado a builds concentradas em uma spell school, com skills/benefícios específicos;
- **Hybrid:** orientado a combinações entre escolas compatíveis, trocando bônus máximos por maior versatilidade.

Isso é relevante ao balanceamento do pack porque vários outros addons de Iron's também adicionam gear, attributes e school power. Não somar modificadores sem validar caps/operações reais.

## 6. Weapons, armor, accessories e attributes
O conteúdo publicado inclui dezenas de armas/armaduras e pelo menos dez accessories, além de novo accessory slot. A release 0.0.16.3 corrigiu especificamente **attributes usando operação errada**, o que torna stacking/cálculo de atributos uma superfície crítica de regressão.

Também corrigiu inserção de **Necro Rune em jewelry**, confirmando integração entre runas/jewelry e o sistema de equipamento do addon.

Não atribuir slots/capabilities específicos a Curios sem manifest/source 0.0.16.3 que os confirme; o dado seguro é que existe accessory slot publicado pelo próprio addon.

## 7. Ores e worldgen
O projeto publica cinco ores e duas estruturas de Overworld. Isso faz o addon participar de worldgen/progressão física, não apenas do spell registry.

Riscos no pack:
- densidade de ores com muitos providers;
- tags de mineração/loot;
- estruturas competindo por espaço/biomes;
- progressão acelerada caso ore/loot seja acessível cedo demais;
- chunks antigos sem conteúdo novo quando versões acrescentam worldgen.

## 8. Mobs spellcasters
O catálogo oficial declara dez mobs com spells especiais, inspirados em diferentes jogos/fantasias. Eles devem ser tratados como entidades próprias do addon, com efeitos sobre:
- combate e dificuldade;
- efeitos/debuffs;
- drops;
- spawn/worldgen;
- animações AzureLib;
- interação com outros sistemas de AI/combat.

A ficha não cria lista nominal completa sem evidência 0.0.16.3 suficiente para cada entidade.

## 9. Changelog exato 0.0.16.3
A release instalada registra:
- remoção de restos de conteúdo **Aero** para evitar tags interferindo;
- aumento do nível de slowness do **Fall Curse Spell**;
- redução do nerf de **Wither Bombs**;
- novas alterações visuais de Necro;
- correção de Necro Rune em jewelry;
- correção de attributes usando operação errada;
- correção de efeitos que não eram removidos corretamente.

Esses itens demonstram superfícies reais de tags, attributes, effects e jewelry na build atual.

## 10. Source público divergente
O repositório público 1.21.1 encontrado ainda informa `mod_version=0.0.14`, enquanto o runtime físico é 0.0.16.3.

No estado 0.0.14, o build referencia Iron's Spells, AzureLib, Caelus, JEI e player animator, mas esses dados **não são automaticamente promovidos a dependências exatas de 0.0.16.3**. Para a release atual, a relação oficial CurseForge é authority das dependencies publicadas.

## 11. Client/server e animação
O projeto CurseForge classifica o ambiente como `Server`, mas o addon adiciona gear, mobs, spells, animações e assets e requer AzureLib. Essa classificação pública isolada não é suficiente para afirmar que o cliente pode rodar sem o JAR.

O protocolo seguro do pack é instalar o mesmo conjunto normal de conteúdo em cliente e servidor, salvo manifest da build provando outra coisa. Rendering/animações são client-facing; spawn, dano, spells e worldgen precisam permanecer server-authoritative.

## 12. Integrações no pack
O pack possui um ecossistema grande de Iron's, incluindo o provider base 3.16.3 e vários addons. Pontos de composição:
- spell school power/attributes;
- jewelry/runes/accessories;
- mobs que castam spells;
- structures/loot;
- recipes e ores;
- efeitos/debuffs;
- KubeJS/bridges do ecossistema Iron's.

Não classificar como redundante apenas por adicionar magia: a decisão curatorial precisa comparar spells/gear/progressão concretos.

## 13. Riscos técnicos e de balanceamento
1. **Source drift:** source público 0.0.14 não representa binário 0.0.16.3 exatamente.
2. **Active-development churn:** o próprio autor avisa que conteúdo pode ser refeito, removido ou adicionado.
3. **Attribute stacking:** release atual corrigiu operação errada; testar com AttributeFix e outros addons.
4. **Effect cleanup:** regressão já corrigida em 0.0.16.3; testar logout/death/dimension.
5. **Tags:** restos Aero foram removidos por interferirem; `/reload` e datapacks são superfície sensível.
6. **Jewelry/runes:** Necro Rune teve fix específico.
7. **Worldgen density:** cinco ores + duas estruturas entram em um pack já denso.
8. **Power creep:** 90+ spells e grandes tiers de gear podem comprimir progressão do RPG.
9. **Mob spell AI:** efeitos, immunities e interrupts podem interagir com Epic Fight/AI mods.
10. **Optional Better Combat:** não está fisicamente presente; não tratar compat como ativa.

## 14. Matriz de testes
- [ ] Cliente + dedicated server iniciam com Iron's 3.16.3, Ace's Spell Utils e AzureLib atuais.
- [ ] Registrar spells/schools sem duplicate registry ID.
- [ ] Testar amostra representativa de Necro: curse, DoT, projectile/AoE e cleanup de effects.
- [ ] Fall Curse aplica slowness esperado e remove corretamente.
- [ ] Wither Bombs funcionam com balance atual sem efeito residual.
- [ ] Necro Rune entra/sai de jewelry corretamente.
- [ ] Attributes de armor/weapon/accessory usam operações corretas em equip/unequip/relog.
- [ ] AttributeFix coexiste sem double-clamp/double-application.
- [ ] Mobs spellcasters executam casts e drops no servidor sem desync de animação.
- [ ] Cinco ores aparecem apenas em chunks/condições válidas e possuem tags de mineração/loot.
- [ ] Duas estruturas geram sem conflito evidente com worldgen final.
- [ ] `/reload` não reintroduz tags inválidas nem duplica registries/recipes.
- [ ] Death, dimension change e reconnect limpam effects temporários.
- [ ] Avaliar power curve com outros addons de Iron's antes de liberar loot cedo em quests.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: `monstersspellbooks-0.0.16.3.jar`, id/version exatos e `monstersspellbooks.mixins.json`.
- CurseForge oficial: project 1428928, file 8788560, Release 01/09/2026, catálogo atual e changelog 0.0.16.3.
- Relations oficiais: Ace's Spell Utils, AzureLib e Iron's Spells required; AttributeFix e Better Combat optional.
- Source público: `RedReaper28/Monsters-Spellbooks-1.21.1`, atualmente divergente em 0.0.14.
- **Limite:** internals/classes/registry IDs específicos de 0.0.16.3 não foram inventados a partir do source antigo.

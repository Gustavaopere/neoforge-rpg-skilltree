# Cataclysm: Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e3b174e6774f555569
- **Baseline física pré-update:** `cataclysm_spellbooks-1.1.13-1.21.jar`
- **Artefato alvo selecionado no CurseForge:** `cataclysm_spellbooks-1.1.14-1.21.jar` — file `8847070`, Beta, publicado em 09/09/2026
- **Data da atualização documental GitHub:** 2026-09-14

> **BOUNDARY FÍSICO.** A modlist física ainda confirma 1.1.13. Este arquivo documenta 1.1.14 como alvo de atualização, sem afirmar instalação física. O source público 1.21.1 continua atrás da linha binária usada no pack; registry IDs/contratos não demonstrados permanecem fail-closed.

## Propriedades equivalentes do catálogo

- **Mod:** Cataclysm: Spellbooks
- **Arquivo JAR alvo:** `cataclysm_spellbooks-1.1.14-1.21.jar`
- **Versão 1.21.1 alvo:** 1.1.14-1.21
- **Baseline física auditada:** 1.1.13-1.21
- **Estado da pesquisa:** Verificado documentalmente; validação física/runtime da 1.1.14 pendente
- **Decisão:** Manter
- **Categoria:** Magia, RPG, Compat
- **Função:** Addon de Iron's Spells 'n Spellbooks + L_Ender's Cataclysm com schools/spells/equipment próprios, incluindo Abyssal e Technomancy. A linha 1.1.13 já introduziu mais spells e um novo boss; a 1.1.14 adiciona duas mudanças de aquisição/processamento documentadas abaixo.
- **Dependências:** Required relations publicadas: AzureLib + Iron's Spells 'n Spellbooks + L_Ender's Cataclysm. Pack físico baseline: AzureLib 3.1.11, Iron's Spells 3.16.3 e Cataclysm 3.33. Ace's Spell Utils 1.2.7.2-1.21.1 também está instalado, mas não aparece entre as três required relations atuais; não remover sem dependency graph/JAR metadata dos outros consumers.
- **Sobreposição:** Integra Iron's e Cataclysm; não substitui nenhum dos dois. Outros spell addons podem coexistir, mas mana/cooldown/damage/summons e spell IDs devem permanecer provider-owned.
- **Compatibilidade/Riscos:** 1.1.14 continua Beta. Riscos: source público defasado, dependency drift, recipe/JEI/EMI parsing, griefing config, summon/projectile ownership, boss lifecycle e double-processing de mana/cooldown/damage. A nova brewing path de manuscript → timeless slurry também cruza recipe/brewing/data loading.
- **Observações:** Changelog 1.1.14: (1) added way to obtain strange disc; (2) manuscripts can be brewed into 500 mB of timeless slurry. Nenhum outro delta foi atribuído à release.
- **Procedência:** modlist.txt física baseline 1.1.13 + CurseForge oficial Cataclysm: Spellbooks 1.1.14 file 8847070 + descrição/relações atuais + source público 1.21.1 apenas como contexto arquitetural.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cataclysm-spellbooks
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/09/2026 — GitHub promovido para alvo 1.1.14-1.21. Delta público limitado a strange disc acquisition e manuscript → 500 mB timeless slurry; demais internals permanecem fail-closed até JAR/source 1.1.14.
- **Histórico da decisão:** Em 06/09/2026 foi registrada decisão formal de MANTER Cataclysm: Spellbooks. O runtime físico foi reconciliado para 1.1.13 em 09/09; em 14/09 o GitHub passou a registrar 1.1.14 como alvo de atualização, sem alterar a decisão Manter.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** O pack físico entregue usa 1.1.13; o alvo documental é `cataclysm_spellbooks-1.1.14-1.21.jar`. A 1.1.14 é **Beta**. O dossiê preserva a arquitetura e escopo 1.1.13 já auditados e incorpora somente o delta 1.1.14 explicitamente publicado.

## 1. Papel e authority
Cataclysm: Spellbooks integra **Iron's Spells 'n Spellbooks** e **L_Ender's Cataclysm**, adicionando spellcasting, escolas, equipamentos e conteúdo próprio inspirado no universo Cataclysm.
Iron's continua authority do framework de mana, cast, cooldown e spellbook; L_Ender's Cataclysm continua authority de seus bosses, mobs, estruturas e materiais originais; `cataclysm_spellbooks` é authority dos spells, escolas, equipamentos, summons e encounter próprio que registra.
Integrações do pack não devem reaplicar damage, mana, cooldown, summon ou loot por reconhecer apenas o tema Cataclysm.

## 2. Build baseline 1.1.13 e alvo 1.1.14
A modlist física confirma `cataclysm_spellbooks-1.1.13-1.21.jar`. O alvo selecionado é `cataclysm_spellbooks-1.1.14-1.21.jar`, file 8847070, NeoForge 1.21.1, Beta, publicado em 09/09/2026.

A 1.1.13 havia publicado:
- updated arts;
- ported more spells;
- fixed bugs;
- added a new boss.

A 1.1.14 publica apenas dois deltas concretos:
- **added way to obtain strange disc**;
- **manuscripts can now be brewed into 500 mB of timeless slurry**.

Não foram publicados, nesse changelog, outros IDs, fórmulas, chances, recipes completos ou mudanças de boss/spells. Esses detalhes não são inventados.

## 3. Escopo publicado — 65 spells
A descrição oficial da linha declara **65 new spells** e mantém as escolas **Abyssal** e **Technomancy**.
Isso é uma contagem release-facing, não uma enumeração source-pinned dos registries do JAR 1.1.14. Para integração por IDs, perks, quests ou compat programática, enumerar o JAR/runtime antes de hardcodar qualquer spell ID.

## 4. Abyssal school
A documentação oficial associa **Abyssal** ao conteúdo inspirado no Leviathan. O addon controla os spells dessa school; Iron's controla mana/cast/cooldown base.
Regras de integração:
- não duplicar secondary damage/effects por evento externo;
- não converter automaticamente Abyssal em outra school interna do pack;
- qualquer perk por school deve consultar a school/registry real da build instalada;
- summon, projectile e block interaction continuam server-authoritative.

## 5. Technomancy school
A documentação oficial associa **Technomancy** ao Harbinger. A school pode coexistir com outros sistemas tecnológicos/mágicos do pack, mas similaridade temática não cria equivalência de energy system.
Não mapear Technomancy para FE, Create stress ou outra resource pool sem bridge explicitamente projetada e validada.

## 6. Novo boss da linha 1.1.13+
A 1.1.13 confirmou **um novo boss**, mas o changelog consultado não publica nome nem contratos de spawn, arena, phases, loot ou summon. A 1.1.14 não anuncia alteração desse boss.
Por isso:
- não inventar identidade/ID;
- não criar quest/perk por nome até enumerar registry/JAR;
- boss state, attack settlement, death e loot permanecem authority do addon;
- dedicated-server e multiplayer QA são obrigatórios antes de integração própria.

## 7. Strange disc — novo acquisition path 1.1.14
A 1.1.14 passa a documentar **uma forma de obter o strange disc**. O changelog não publica método, estrutura, chance, mob, recipe ou ID além do nome release-facing.

Gate operacional:
- identificar no JAR/data 1.1.14 o provider real de aquisição;
- evitar adicionar recipe/loot paralelo antes dessa identificação;
- verificar exactly-once em loot/grant se a aquisição envolver evento/estrutura.

## 8. Manuscripts → timeless slurry
A 1.1.14 documenta que **manuscripts podem ser brewed em 500 mB de timeless slurry**.

Isto cria uma superfície explícita de processamento/fluido:
- recipe admission e consumo precisam ser server-authoritative;
- output deve ser exatamente 500 mB por operação válida conforme o provider;
- automação não pode duplicar manuscript consumption ou slurry output;
- JEI/EMI/recipe-viewer e reload de datapacks/recipes precisam refletir a recipe efetiva;
- qualquer bridge Create/fluid deve observar o output provider em vez de recriar a recipe.

O changelog não informa aparelho, tempo, ingredientes adicionais ou registry IDs. Esses detalhes permanecem fail-closed.

## 9. Dependências publicadas atualmente
A página de relações atual do projeto lista como **Required Dependency**:
- AzureLib;
- Iron's Spells 'n Spellbooks;
- L_Ender's Cataclysm.

No pack físico baseline:
- AzureLib `3.1.11`;
- Iron's Spells 'n Spellbooks `3.16.3`;
- L_Ender's Cataclysm `3.33`.

Ace's Spell Utils `1.2.7.2-1.21.1` também está fisicamente presente, mas não aparece entre as três required relations atuais. Isso não autoriza removê-lo: outros consumers podem depender dele.

## 10. Boundary de source
O repositório público `AceTheEldritchKing/Cataclysm_Spellbooks_1.21.1` consultado anteriormente ainda declarava `mod_version=1.1.11-1.21` na evidência auditada.
Consequência:
- o source público é útil para arquitetura/nomes históricos;
- ele **não é prova de paridade binária com 1.1.14**;
- não publicar registry counts/IDs da 1.1.14 a partir desse source sem diff/tag/commit correspondente;
- a release e o changelog 1.1.14 prevalecem para o delta documental.

## 11. Mana, cooldown e damage authority
Todo cast precisa seguir o pipeline do Iron's/addon:
1. validar caster/spell;
2. validar custo/cooldown;
3. executar cast/provider logic;
4. spawnar projectile/summon/effect conforme server state;
5. liquidar damage/status uma única vez.
Bridges de RPG/Epic Fight não devem reaplicar dano ou consumir mana novamente ao observar animation, projectile ou hit visual.

## 12. Summons, projectiles e ownership
Para qualquer spell atual:
- owner/caster precisa permanecer associado;
- death/logout/dimension change deve limpar ou transferir state apenas conforme provider;
- chunk unload não deve duplicar summon/projectile;
- particle/animation não é prova de hit;
- fake players/automation só devem ser tratados se o provider realmente suportar.
Sem enumeração 1.1.14, nenhum summon/spell adicional é inventado aqui.

## 13. Block interaction e griefing
A linha 1.21.1 já teve reports públicos de spells interagindo com blocos/config de griefing. A 1.1.14 não declara correção específica disso.
Portanto permanece gate de QA:
- testar spells que alterem blocos com config on/off;
- testar claims/protection;
- não presumir resolução de issue antiga sem evidência release-specific;
- não adicionar patch global antes de reproduzir na 1.1.14.

## 14. Recipe viewers e data loading
A linha possui histórico de problemas de recipe parsing/JEI/EMI. A nova recipe de manuscript → timeless slurry aumenta a relevância desse boundary.
Validar na build alvo:
- startup com recipe viewers atuais;
- recipe registry e visualização do brewing path;
- datapack reload;
- ausência de missing registry keys;
- output de 500 mB exatamente uma vez.

## 15. Client/server
Server/common authority:
- cast validity;
- mana/cooldown settlement;
- damage/status;
- projectile/summon entity state;
- boss state/death/loot;
- block interaction/unlock;
- brewing/recipe admission e fluid output.

Client presentation:
- models/textures;
- animations;
- particles;
- spellbook/HUD/recipe-viewer presentation.
AzureLib/render classes não devem virar requisito de logic path em dedicated server.

## 16. Lifecycle e multiplayer
Validar:
1. dedicated server boot com as três required dependencies atuais;
2. client join/reconnect;
3. spell registry/bootstrap;
4. resource reload;
5. datapack/recipe reload;
6. death/respawn;
7. dimension change;
8. summon/projectile chunk unload;
9. dois jogadores usando spells simultaneamente;
10. boss em multiplayer, incluindo death/rejoin e loot exactly-once;
11. brewing de manuscript concorrente/automatizado sem dupe/loss.

## 17. Riscos atuais
1. **Beta 1.1.14** explicitamente sujeita a regressões.
2. Source público conhecido estar atrás da linha binária e não provar registries 1.1.14.
3. Importar IDs/nomes antigos como se fossem 1.1.14.
4. Dependency drift com AzureLib 3.1.11, Iron's 3.16.3 e Cataclysm 3.33.
5. Double damage/mana/cooldown em bridges externas.
6. Summon/projectile owner perdido no lifecycle.
7. Boss ter state/loot duplicado em multiplayer/reload.
8. Block grief config permanecer problemática.
9. Recipe/JEI/EMI parsing regressions.
10. **Brewing duplicate settlement** no manuscript → timeless slurry.
11. Duplicar acquisition path do strange disc por script/loot externo.

## 18. Matriz de testes
1. Nova modlist física confirma `cataclysm_spellbooks-1.1.14-1.21.jar`.
2. Dedicated server boot com AzureLib 3.1.11 + Iron's 3.16.3 + Cataclysm 3.33.
3. Client boot com recipe viewers do pack e sem registry/JSON errors.
4. Enumerar registries/spells diretamente da **1.1.14** antes de integração por ID.
5. Confirmar as 65 spells release-facing contra registry runtime antes de transformar a contagem em contrato binário.
6. Abyssal/Technomancy: mana/cooldown/damage exactly-once.
7. Boss: spawn/summon, phase/state, death, loot, restart e multiplayer.
8. Identificar e testar o acquisition path real do strange disc sem duplicação.
9. Brew de manuscript produz exatamente **500 mB** de timeless slurry por operação válida, sem dupe/loss.
10. Recipe viewer e `/reload` refletem a recipe da 1.1.14.
11. Block interaction/grief config com claims/protection.
12. Death/reconnect/dimension change com spells/summons ativos.

## 19. Evidência e boundary
- modlist física baseline: `cataclysm_spellbooks-1.1.13-1.21.jar`;
- CurseForge oficial: `cataclysm_spellbooks-1.1.14-1.21.jar`, file 8847070, Beta, NeoForge 1.21.1, 09/09/2026;
- changelog 1.1.14: way to obtain strange disc; manuscripts brew into 500 mB timeless slurry;
- descrição oficial da linha: 65 spells, Abyssal e Technomancy;
- relações atuais do projeto: AzureLib, Iron's Spells 'n Spellbooks e L_Ender's Cataclysm como required dependencies;
- source público conhecido usado apenas como contexto arquitetural, não como prova binária da 1.1.14.

> **Fail-closed:** a versão-alvo 1.1.14 está documentada, mas sua instalação física e internals exatos ainda precisam ser confirmados pelo JAR/modlist pós-update. IDs, método exato de strange-disc acquisition e detalhes do brewing além dos 500 mB não foram inferidos.
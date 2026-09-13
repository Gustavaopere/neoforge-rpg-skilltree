# Cataclysm: Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e3b174e6774f555569
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Cataclysm: Spellbooks
- **Arquivo JAR:** `cataclysm_spellbooks-1.1.13-1.21.jar`
- **Versão 1.21.1:** 1.1.13-1.21
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG, Compat
- **Função:** Addon de Iron's Spells 'n Spellbooks + L_Ender's Cataclysm com schools/spells/equipment próprios, incluindo Abyssal e Technomancy; a build física 1.1.13 Beta também confirma mais spells portados e um novo boss, cujos IDs/contratos exatos exigem inspeção JAR/source 1.1.13 antes de integrações por ID.
- **Dependências:** Required relations atualmente publicadas: AzureLib + Iron's Spells 'n Spellbooks + L_Ender's Cataclysm. Pack físico atual: AzureLib 3.1.11, Iron's Spells 3.16.3 e Cataclysm 3.33. Ace's Spell Utils 1.2.7.2-1.21.1 também está instalado, mas não aparece entre as três required relations atuais; não remover sem dependency graph/JAR metadata dos outros consumers.
- **Sobreposição:** Integra Iron's e Cataclysm; não substitui nenhum dos dois. Outros spell addons podem coexistir, mas mana/cooldown/damage/summons e spell IDs devem permanecer provider-owned.
- **Compatibilidade/Riscos:** Build 1.1.13 é Beta. Riscos: source público 1.21.1 ainda em 1.1.11, dependency drift, recipe/JEI/EMI parsing, griefing config, summon/projectile ownership, novo boss/lifecycle e double-processing de mana/cooldown/damage. IDs e nome do novo boss permanecem fail-closed sem JAR/source 1.1.13 pinado.
- **Observações:** 1.1.13 Beta, publicada em 02/09/2026 (file 8792628). Changelog oficial: updated arts, ported more spells, fixed bugs e added a new boss; recomenda backup. Descrição atual do projeto declara 65 spells e schools Abyssal/Technomancy, tratadas como release-facing e não como enumeração binária source-pinned.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Cataclysm: Spellbooks 1.1.13 file 8792628 + descrição/relações atuais do projeto + source oficial público 1.21.1 usado somente como contexto porque ainda declara 1.1.11.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cataclysm-spellbooks
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — runtime físico 1.1.13-1.21 reconciliado; Beta/changelog, 65-spell release scope, Abyssal/Technomancy, required relations e boundary source 1.1.11→binary 1.1.13 documentados. Decisão Manter preservada. Runtime QA não executado.
- **Histórico da decisão:** Em 06/09/2026 foi registrada decisão formal de MANTER Cataclysm: Spellbooks. Uma auditoria intermediária havia ancorado o corpo na build 1.1.12 Alpha. A modlist física mais recente de 08/09/2026 confirma `cataclysm_spellbooks-1.1.13-1.21.jar`; em 09/09/2026 o dossiê foi reconciliado para 1.1.13 Beta, sem alterar a decisão Manter nem inventar detalhes não publicados.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> ⚠️ Autoridade física atual: `cataclysm_spellbooks-1.1.13-1.21.jar`, mod id `cataclysm_spellbooks`, runtime `1.1.13-1.21`, NeoForge 1.21.1. A build 1.1.13 foi publicada em 02/09/2026 como **Beta**. O changelog oficial confirma arts atualizadas, mais spells portados, bugs corrigidos e um novo boss. O autor ainda recomenda backup. **Dossiê documental completo não equivale a runtime QA aprovado.**

## 1. Papel e authority
Cataclysm: Spellbooks integra **Iron's Spells 'n Spellbooks** e **L_Ender's Cataclysm**, adicionando spellcasting, escolas, equipamentos e conteúdo próprio inspirado no universo Cataclysm.
Iron's continua authority do framework de mana, cast, cooldown e spellbook; L_Ender's Cataclysm continua authority de seus bosses, mobs, estruturas e materiais originais; `cataclysm_spellbooks` é authority dos spells, escolas, equipamentos, summons e encounter próprio que registra.
Integrações do pack não devem reaplicar damage, mana, cooldown, summon ou loot por reconhecer apenas o tema Cataclysm.

## 2. Build física 1.1.13
A modlist física atual confirma `cataclysm_spellbooks-1.1.13-1.21.jar`. A página oficial do arquivo confirma:
- Minecraft 1.21.1;
- NeoForge;
- tipo **Beta**;
- upload em 02/09/2026;
- file ID 8792628;
- ambiente do projeto Client & Server.
A antiga 1.1.12 Alpha não é mais o runtime do pack e não deve ser usada como authority da instância atual.

## 3. Delta oficial 1.1.13
O changelog público da 1.1.13 é curto e deve ser tratado literalmente:
- **updated arts**;
- **ported more spells**;
- **fixed bugs**;
- **added a new boss**.
A publicação também diz que a build é amplamente jogável, mas ainda pode ter bugs, e recomenda backup.
Não foram publicados, nesse changelog, IDs, contagem dos spells portados, nome do boss, fórmulas, loot, arena, summon condition ou lista de bugs corrigidos. Esses detalhes permanecem fail-closed até JAR/source 1.1.13 versionado ou runtime inspection.

## 4. Escopo atual publicado — 65 spells
A descrição oficial atual do projeto, atualizada no mesmo período da 1.1.13, declara **65 new spells** e mantém as escolas **Abyssal** e **Technomancy**.
Isso é uma contagem release-facing do estado atual do projeto, não uma enumeração source-pinned dos registries do JAR 1.1.13. Para integração por IDs, perks, quests ou compat programática, enumerar o JAR/runtime antes de hardcodar qualquer spell ID.

## 5. Abyssal school
A documentação oficial associa **Abyssal** ao conteúdo inspirado no Leviathan. O addon controla os spells dessa school; Iron's controla mana/cast/cooldown base.
Regras de integração:
- não duplicar secondary damage/effects por evento externo;
- não converter automaticamente Abyssal em outra school interna do pack;
- qualquer perk por school deve consultar a school/registry real da build instalada;
- summon, projectile e block interaction continuam server-authoritative.

## 6. Technomancy school
A documentação oficial associa **Technomancy** ao Harbinger. A school pode coexistir com outros sistemas tecnológicos/mágicos do pack, mas similaridade temática não cria equivalência de energy system.
Não mapear Technomancy para FE, Create stress ou outra resource pool sem bridge explicitamente projetada e validada.

## 7. Novo boss da 1.1.13
A 1.1.13 confirma **um novo boss**, mas o changelog consultado não publica o nome nem seus contratos de spawn, arena, phases, loot ou summon.
Por isso:
- não inventar identidade/ID;
- não criar quest/perk por nome até enumerar registry/JAR;
- boss state, attack settlement, death e loot devem permanecer authority do addon;
- dedicated-server e multiplayer QA são obrigatórios antes de integração própria.

## 8. Arts atualizadas
A 1.1.13 atualiza assets/art. Isso é superfície client-side de apresentação e pode afetar models, textures, animations ou spell visuals sem implicar mudança de gameplay.
Resource-pack compatibility deve ser validada na build atual; não usar asset antigo da 1.1.12 como prova de registry ou behavior.

## 9. Dependências publicadas atualmente
A página de relações atual do projeto lista como **Required Dependency**:
- AzureLib;
- Iron's Spells 'n Spellbooks;
- L_Ender's Cataclysm.
No pack físico atual:
- AzureLib `3.1.11`;
- Iron's Spells 'n Spellbooks `3.16.3`;
- L_Ender's Cataclysm `3.33`.
Ace's Spell Utils `1.2.7.2-1.21.1` também está fisicamente presente no pack, mas não aparece entre as três relações required atualmente publicadas pela página do projeto. Isso não autoriza removê-lo: outros consumers podem depender dele e qualquer remoção exige dependency graph/JAR metadata real.

## 10. Boundary de source — não confundir 1.1.11 com 1.1.13
O repositório público atual `AceTheEldritchKing/Cataclysm_Spellbooks_1.21.1` é oficialmente descrito como repository para 1.21.1, porém seu `gradle.properties` ainda declara `mod_version=1.1.11-1.21` e NeoForge 21.1.219.
Consequência:
- o source público é útil para arquitetura/nomes históricos;
- ele **não é prova de paridade binária com 1.1.13**;
- não publicar registry counts/IDs da 1.1.13 a partir desse source sem diff/tag/commit correspondente;
- a release física e o changelog 1.1.13 prevalecem para versão/estado da build instalada.

## 11. Mana, cooldown e damage authority
Todo cast precisa seguir o pipeline do Iron's/addon:
1. validar caster/spell;
2. validar custo/cooldown;
3. executar cast/provider logic;
4. spawnar projectile/summon/effect conforme server state;
5. liquidar damage/status uma única vez.
Bridges de RPG/Epic Fight não devem reaplicar dano ou consumir mana novamente ao observar animation, projectile ou hit visual.

## 12. Summons, projectiles e ownership
O projeto historicamente possui summons e projectiles, e a 1.1.13 porta mais spells. Para qualquer spell atual:
- owner/caster precisa permanecer associado;
- death/logout/dimension change deve limpar ou transferir state apenas conforme provider;
- chunk unload não deve duplicar summon/projectile;
- particle/animation não é prova de hit;
- fake players/automation só devem ser tratados se o provider realmente suportar.
Sem enumeração 1.1.13, nenhum summon/spell específico adicional é inventado aqui.

## 13. Block interaction e griefing
A linha 1.21.1 já teve reports públicos de spells interagindo com blocos/config de griefing. A 1.1.13 diz genericamente “fixed bugs”, mas não enumera esse caso.
Portanto permanece gate de QA:
- testar spells que alterem blocos com config on/off;
- testar claims/protection;
- não presumir que issue antiga foi corrigida sem evidência release-specific;
- não adicionar patch global antes de reproduzir na 1.1.13.

## 14. Recipe viewers e data loading
A linha anterior teve report público de problemas de recipe parsing/JEI/EMI em 1.1.11. A 1.1.13 possui mudanças e fixes não enumerados, então a presença de “fixed bugs” não prova resolução específica.
Validar na build física:
- startup com recipe viewers atuais;
- recipe registry;
- Arcane Anvil/integration surfaces quando aplicáveis;
- datapack reload;
- ausência de missing registry keys.
Falha deve ser atribuída ao stack/version real, não automaticamente ao addon.

## 15. Client/server
Server/common authority:
- cast validity;
- mana/cooldown settlement;
- damage/status;
- projectile/summon entity state;
- boss state/death/loot;
- block interaction/unlock.
Client presentation:
- models/textures;
- animations;
- particles;
- spellbook/HUD presentation.
AzureLib/render classes não devem virar requisito de logic path em dedicated server.

## 16. Lifecycle e multiplayer
Validar:
1. dedicated server boot com as três required dependencies atuais;
2. client join/reconnect;
3. spell registry/bootstrap;
4. resource reload das arts novas;
5. datapack reload;
6. death/respawn;
7. dimension change;
8. summon/projectile chunk unload;
9. dois jogadores usando spells simultaneamente;
10. novo boss em multiplayer, incluindo death/rejoin e loot exactly-once.

## 17. Riscos atuais
1. **Beta 1.1.13** ainda explicitamente sujeita a bugs.
2. Source público disponível estar em 1.1.11 e não provar registries 1.1.13.
3. Importar IDs/nomes da 1.1.12 ou source 1.1.11 como se fossem 1.1.13.
4. Dependency drift com AzureLib 3.1.11, Iron's 3.16.3 e Cataclysm 3.33.
5. Double damage/mana/cooldown em bridges externas.
6. Summon/projectile owner perdido no lifecycle.
7. Novo boss ter state/loot duplicado em multiplayer/reload.
8. Block grief config antiga permanecer problemática.
9. Recipe/JEI/EMI parsing regressions.
10. Resource pack/model incompatibility após updated arts.

## 18. Matriz de testes
1. Dedicated server boot com AzureLib 3.1.11 + Iron's 3.16.3 + Cataclysm 3.33.
2. Client boot com recipe viewers do pack e sem registry/JSON errors.
3. Enumerar registries/spells diretamente da **1.1.13** antes de qualquer integração por ID.
4. Confirmar as 65 spells release-facing contra registry runtime antes de transformar a contagem em contrato binário.
5. Abyssal/Technomancy: mana/cooldown/damage exactly-once.
6. Spells portados na 1.1.13: smoke de cast/projectile/summon conforme encontrados no JAR.
7. Novo boss: spawn/summon, phase/state, death, loot, restart e multiplayer.
8. Block interaction/grief config com claims/protection.
9. Resource reload das arts atualizadas.
10. Death/reconnect/dimension change com spells/summons ativos.
11. Datapack reload sem stale registry/recipe handlers.
12. Teste de regressão das issues antigas somente após reproduzir na 1.1.13.

## 19. Evidência e boundary
- modlist física atual de 08/09/2026: `cataclysm_spellbooks-1.1.13-1.21.jar`;
- CurseForge oficial file 8792628: 1.1.13 Beta, NeoForge 1.21.1, 02/09/2026;
- changelog 1.1.13: updated arts, ported more spells, fixed bugs, added a new boss, com aviso de backup;
- descrição oficial atual: 65 spells, Abyssal e Technomancy;
- relações atuais do projeto: AzureLib, Iron's Spells 'n Spellbooks e L_Ender's Cataclysm como required dependencies;
- source oficial 1.21.1 público consultado, porém ainda declarando 1.1.11; usado apenas como contexto arquitetural, não como prova binária da 1.1.13.

> 🔒 Fail-closed: **1.1.13 é a authority de versão física, mas não há source pin público 1.1.13 equivalente na evidência consultada.** IDs, nome do novo boss, lista exata dos spells portados e bugs específicos corrigidos só entram como contrato depois de inspeção do JAR/runtime ou source/tag correspondente.

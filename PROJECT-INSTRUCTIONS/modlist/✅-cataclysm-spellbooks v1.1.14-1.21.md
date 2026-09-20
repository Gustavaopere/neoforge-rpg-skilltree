# Cataclysm: Spellbooks

## Propriedades do registro

- **Mod:** Cataclysm: Spellbooks
- **Arquivo JAR:** `cataclysm_spellbooks-1.1.14-1.21.jar`
- **Versão 1.21.1:** `1.1.14-1.21`
- **Categoria:** Magia; RPG; Compat
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cataclysm-spellbooks
- **Função:** Addon de Iron's Spells 'n Spellbooks + L_Ender's Cataclysm com schools/spells/equipment próprios, incluindo Abyssal e Technomancy; a build física 1.1.14 Beta herda o grande delta da 1.1.13 e acrescenta obtenção do Strange Disc e brewing de manuscripts em 500 mB de Timeless Slurry. IDs/contratos não publicados continuam fail-closed.
- **Dependências:** Required relations atualmente publicadas: AzureLib + Iron's Spells 'n Spellbooks + L_Ender's Cataclysm. Pack físico atual: AzureLib 3.1.11, Iron's Spells 3.16.3 e Cataclysm 3.33. Ace's Spell Utils 1.2.7.2-1.21.1 também está instalado, mas não aparece entre as três required relations atuais; não remover sem dependency graph/JAR metadata dos outros consumers.
- **Compatibilidade/Riscos:** Build 1.1.14 é Beta. Riscos: source público 1.21.1 ainda em 1.1.11, dependency drift, recipe/JEI/EMI parsing, griefing config, summon/projectile ownership, boss/lifecycle, novos caminhos de obtenção/brewing e double-processing de mana/cooldown/damage. IDs não publicados permanecem fail-closed sem JAR/source 1.1.14 pinado.
- **Sobreposição:** Integra Iron's e Cataclysm; não substitui nenhum dos dois. Outros spell addons podem coexistir, mas mana/cooldown/damage/summons e spell IDs devem permanecer provider-owned.
- **Observações:** 1.1.14 Beta, publicada em 09/09/2026 (file 8847070). Changelog oficial: added way to obtain strange disc; manuscripts podem ser brewed em 500 mB de timeless slurry. A 1.1.13 Beta (02/09/2026, file 8792628) permanece o baseline do grande delta de arts/spells/bugs/new boss herdado pela build atual.
- **Procedência:** modlist(1).txt física atual de 20/09/2026 + CurseForge oficial Cataclysm: Spellbooks 1.1.14 file 8847070 + 1.1.13 file 8792628 como baseline do delta anterior + descrição/relações atuais + source oficial público 1.21.1 usado somente como contexto porque ainda declara 1.1.11. Reconciliação final: JAR/runtime atuais são `cataclysm_spellbooks-1.1.14-1.21.jar` / `1.1.14-1.21`; source 1.1.11 não foi promovido artificialmente à paridade 1.1.14 e a decisão `Manter` permanece.
- **Histórico da decisão:** Em 06/09/2026 foi registrada decisão formal de MANTER Cataclysm: Spellbooks. Uma auditoria intermediária havia ancorado o corpo na build 1.1.12 Alpha. A modlist física mais recente de 08/09/2026 confirma `cataclysm_spellbooks-1.1.13-1.21.jar`; em 09/09/2026 o dossiê foi reconciliado para 1.1.13 Beta, sem alterar a decisão Manter nem inventar detalhes não publicados. Em 20/09/2026 a modlist física atual confirmou a atualização para `cataclysm_spellbooks-1.1.14-1.21.jar`; o dossiê foi reconciliado para a 1.1.14 Beta sem alterar a decisão `Manter`.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 20/09/2026 — reconciliação final física #86: `cataclysm_spellbooks-1.1.14-1.21.jar` / `1.1.14-1.21` conferidos contra a modlist atual; delta 1.1.14 (Strange Disc + manuscripts→500 mB Timeless Slurry), baseline Beta 1.1.13, 65-spell release scope, Abyssal/Technomancy, required relations e boundary source público 1.1.11→binary 1.1.14 preservados. Decisão `Manter` mantida.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs
> ⚠️ Autoridade física atual: `cataclysm_spellbooks-1.1.14-1.21.jar`, mod id `cataclysm_spellbooks`, runtime `1.1.14-1.21`, NeoForge 1.21.1. A build 1.1.14 foi publicada em 09/09/2026 como **Beta**. Seu changelog adiciona uma forma de obter o Strange Disc e permite fermentar manuscripts em **500 mB de Timeless Slurry**. A 1.1.13 Beta permanece o baseline imediatamente anterior de arts atualizadas, mais spells portados, bugs corrigidos e um novo boss. O source público 1.21.1 continua declarando 1.1.11. **Dossiê documental completo não equivale a runtime QA aprovado.**
## 1. Papel e authority
Cataclysm: Spellbooks integra **Iron's Spells 'n Spellbooks** e **L_Ender's Cataclysm**, adicionando spellcasting, escolas, equipamentos e conteúdo próprio inspirado no universo Cataclysm.
Iron's continua authority do framework de mana, cast, cooldown e spellbook; L_Ender's Cataclysm continua authority de seus bosses, mobs, estruturas e materiais originais; `cataclysm_spellbooks` é authority dos spells, escolas, equipamentos, summons e encounter próprio que registra.
Integrações do pack não devem reaplicar damage, mana, cooldown, summon ou loot por reconhecer apenas o tema Cataclysm.
## 2. Build física 1.1.14
A modlist física atual confirma `cataclysm_spellbooks-1.1.14-1.21.jar`. A página oficial do arquivo confirma:
- Minecraft 1.21.1;
- NeoForge;
- tipo **Beta**;
- upload em 09/09/2026;
- file ID 8847070;
- ambiente do projeto Client & Server.
A antiga 1.1.13 Beta não é mais o runtime do pack. Ela permanece relevante apenas como baseline do grande delta imediatamente anterior.
## 3. Delta oficial 1.1.14
O changelog público da 1.1.14 é curto e deve ser tratado literalmente:
- **Added way to obtain strange disc**;
- manuscripts podem ser **brewed into 500 mB of timeless slurry**.
A release não publica, nesse changelog, novos IDs, fórmulas, estrutura de recipe/brewing, nome de registry do Strange Disc ou contratos adicionais de boss/spells.
O grande delta de arts atualizadas, mais spells portados, bugs corrigidos e novo boss pertence à 1.1.13 Beta e é herdado pela build atual sem ser reatribuído à 1.1.14.
Detalhes binários permanecem fail-closed até inspeção do JAR/runtime ou source/tag 1.1.14 correspondente.
## 4. Escopo atual publicado — 65 spells
A descrição oficial atual do projeto declara **65 new spells** e mantém as escolas **Abyssal** e **Technomancy**.
Isso é uma contagem release-facing do estado atual do projeto, não uma enumeração source-pinned dos registries do JAR 1.1.14. Para integração por IDs, perks, quests ou compat programática, enumerar o JAR/runtime antes de hardcodar qualquer spell ID.
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
## 7. Novo boss herdado da 1.1.13
A 1.1.13 confirmou **um novo boss**. A 1.1.14 não publica mudança adicional desse boss no changelog consultado. O changelog 1.1.13 não publicou o nome nem seus contratos de spawn, arena, phases, loot ou summon.
Por isso:
- não inventar identidade/ID;
- não criar quest/perk por nome até enumerar registry/JAR 1.1.14;
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
## 10. Boundary de source — não confundir 1.1.11 com 1.1.14
O repositório público atual `AceTheEldritchKing/Cataclysm_Spellbooks_1.21.1` é oficialmente descrito como repository para 1.21.1, porém seu `gradle.properties` ainda declara `mod_version=1.1.11-1.21` e NeoForge 21.1.219.
Consequência:
- o source público é útil para arquitetura/nomes históricos;
- ele **não é prova de paridade binária com 1.1.14**;
- não publicar registry counts/IDs da 1.1.14 a partir desse source sem diff/tag/commit correspondente;
- a release física e o changelog 1.1.14 prevalecem para versão/estado da build instalada;
- o changelog 1.1.13 continua válido apenas como baseline histórico do delta imediatamente anterior.
## 11. Mana, cooldown e damage authority
Todo cast precisa seguir o pipeline do Iron's/addon:
1. validar caster/spell;
2. validar custo/cooldown;
3. executar cast/provider logic;
4. spawnar projectile/summon/effect conforme server state;
5. liquidar damage/status uma única vez.
Bridges de RPG/Epic Fight não devem reaplicar dano ou consumir mana novamente ao observar animation, projectile ou hit visual.
## 12. Summons, projectiles e ownership
O projeto historicamente possui summons e projectiles, e a 1.1.13 portou mais spells. A 1.1.14 não publica uma nova lista de spells/summons; seu delta oficial é Strange Disc + brewing de manuscripts.
Para qualquer spell atual:
- owner/caster precisa permanecer associado;
- death/logout/dimension change deve limpar ou transferir state apenas conforme provider;
- chunk unload não deve duplicar summon/projectile;
- particle/animation não é prova de hit;
- fake players/automation só devem ser tratados se o provider realmente suportar.
Sem enumeração 1.1.14, nenhum summon/spell específico adicional é inventado aqui.
## 13. Block interaction e griefing
A linha 1.21.1 já teve reports públicos de spells interagindo com blocos/config de griefing. A 1.1.13 disse genericamente “fixed bugs”; a 1.1.14 não menciona esse caso.
Portanto permanece gate de QA:
- testar spells que alterem blocos com config on/off;
- testar claims/protection;
- não presumir que issue antiga foi corrigida sem evidência release-specific;
- não adicionar patch global antes de reproduzir na 1.1.14.
## 14. Recipe viewers e data loading
A linha anterior teve report público de problemas de recipe parsing/JEI/EMI em 1.1.11. A 1.1.13 trouxe “fixed bugs” sem enumeração; a 1.1.14 documenta Strange Disc e brewing de manuscripts, mas não declara resolução específica de recipe-viewer regressions.
Validar na build física:
- startup com recipe viewers atuais;
- recipe registry;
- Arcane Anvil/integration surfaces quando aplicáveis;
- datapack reload;
- obtenção do Strange Disc;
- brewing de manuscripts em 500 mB de Timeless Slurry;
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
1. **Beta 1.1.14** ainda deve ser tratada como build de pré-release.
2. Source público disponível estar em 1.1.11 e não provar registries 1.1.14.
3. Importar IDs/nomes da 1.1.13 ou source 1.1.11 como se fossem contratos completos da 1.1.14.
4. Dependency drift com AzureLib 3.1.11, Iron's 3.16.3 e Cataclysm 3.33.
5. Double damage/mana/cooldown em bridges externas.
6. Summon/projectile owner perdido no lifecycle.
7. Boss introduzido na 1.1.13 ter state/loot duplicado em multiplayer/reload.
8. Block grief config antiga permanecer problemática.
9. Recipe/JEI/EMI parsing regressions.
10. Resource pack/model incompatibility após updated arts da 1.1.13.
11. Novo caminho de obtenção do Strange Disc ou brewing de manuscripts conflitar com recipe/datapack integrations do pack.
## 18. Matriz de testes
1. Dedicated server boot com AzureLib 3.1.11 + Iron's 3.16.3 + Cataclysm 3.33.
2. Client boot com recipe viewers do pack e sem registry/JSON errors.
3. Enumerar registries/spells diretamente da **1.1.14** antes de qualquer integração por ID.
4. Confirmar as 65 spells release-facing contra registry runtime antes de transformar a contagem em contrato binário.
5. Abyssal/Technomancy: mana/cooldown/damage exactly-once.
6. Spells do baseline 1.1.13: smoke de cast/projectile/summon conforme encontrados no JAR 1.1.14.
7. Boss introduzido na 1.1.13: spawn/summon, phase/state, death, loot, restart e multiplayer.
8. Obter o Strange Disc pelo caminho adicionado na 1.1.14.
9. Brew de manuscripts: confirmar consumo/produção de **500 mB de Timeless Slurry** e exactly-once.
10. Block interaction/grief config com claims/protection.
11. Resource reload das arts atualizadas.
12. Death/reconnect/dimension change com spells/summons ativos.
13. Datapack reload sem stale registry/recipe handlers.
14. Teste de regressão das issues antigas somente após reproduzir na 1.1.14.
## 19. Evidência e boundary
- modlist física atual de 20/09/2026: `cataclysm_spellbooks-1.1.14-1.21.jar`;
- CurseForge oficial file 8847070: 1.1.14 Beta, NeoForge 1.21.1, 09/09/2026;
- changelog 1.1.14: nova forma de obter Strange Disc + manuscripts podem ser brewed em 500 mB de Timeless Slurry;
- CurseForge oficial file 8792628: 1.1.13 Beta, 02/09/2026, usado como baseline histórico do delta “updated arts, ported more spells, fixed bugs, added a new boss”;
- descrição oficial atual: 65 spells, Abyssal e Technomancy;
- relações atuais do projeto: AzureLib, Iron's Spells 'n Spellbooks e L_Ender's Cataclysm como required dependencies;
- source oficial 1.21.1 público consultado, porém ainda declarando 1.1.11; usado apenas como contexto arquitetural, não como prova binária da 1.1.14.
> 🔒 Fail-closed: **1.1.14 é a authority de versão física, mas não há source pin público 1.1.14 equivalente na evidência consultada.** IDs, estrutura exata do novo caminho do Strange Disc, registry/recipe do brewing de manuscripts e detalhes binários herdados do boss/spells só entram como contrato depois de inspeção do JAR/runtime ou source/tag correspondente.

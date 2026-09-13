# Discerning The Eldritch

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81099ffbe83296ba9a58
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Discerning The Eldritch
- **Arquivo JAR:** `discerning_the_eldritch-1.4.4-1.21.jar`
- **Versão 1.21.1:** 1.4.4-1.21
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Grande addon de Iron's Spells 'n Spellbooks que expande Eldritch e outras schools com spells, equipamentos, summons/entities, structure/world content e progression própria.
- **Dependências:** Runtime confirmado no pack: Iron's Spells 'n Spellbooks 1.21.1-3.16.3; Ace's Spell Utils 1.2.7.2-1.21.1; AzureLib 3.1.11. Release 1.4.4 corrige crash com ASU recente e problema de config.
- **Sobreposição:** Outros addons ISS podem compartilhar schools, summons, damage/healing concepts ou gear. ISS permanece authority do casting/mana/cooldown; DTE só é authority de seu conteúdo. Não somar pipelines equivalentes sem bridge explícita.
- **Compatibilidade/Riscos:** ASU API drift, config regression, double-damage/healing/lifesteal, recast duplicado, summon ownership, teleport desync, insanity gating e source/inventory drift. Marketing oficial diz 15 spells; auditoria interna anterior registra 22; runtime registry deve arbitrar.
- **Observações:** CurseForge atual declara 15 new spells, 1 new school, 1 structure, 9 mobs e 1 boss, mas nomeia 14 spells no texto. Auditoria interna registra 22 spell registrations (9 Eldritch, 1 Blood, 2 Evocation, 2 Fire, 1 Holy, 2 Ice, 5 Ritual). Divergência preservada fail-closed até inspeção do registry do JAR.
- **Procedência:** JAR/mod id/version/deps: modlist física canônica 08/09/2026, 595 top-levels. Release/behavior: CurseForge oficial File ID 8816497 e descrição atual. Contagem 22/22 por school vem de auditoria interna anterior. GitHub público foi consultado, mas o master acessível está desatualizado frente ao JAR 1.4.4 e não foi usado para inventar IDs ausentes.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/discerning-the-eldritch/files/8816497
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — runtime 1.4.4, spells/items/world-content, ISS/ASU/AzureLib authority, config/ASU regressions, lifecycle/MP, riscos e divergência 15↔22 catalogados.
- **Histórico da decisão:** Ficha reconstruída em 08/09/2026 contra release 1.4.4 e stack físico atual; divergência entre documentação oficial, auditoria interna e source público desatualizado registrada sem inferência.
- **Data da última decisão:** 2026-09-08

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `discerning_the_eldritch-1.4.4-1.21.jar` · mod id `discerning_the_eldritch` · versão `1.4.4-1.21` · NeoForge 1.21.1. A release exata é CurseForge File ID **8816497**, publicada em 05/09/2026.

## 1. Papel no modpack
**Discerning The Eldritch (DTE)** é um grande addon de **Iron's Spells 'n Spellbooks (ISS)** que expande principalmente a escola Eldritch e também adiciona spells de outras schools, equipamentos, mobs/entidades, world content e progression própria.

A descrição oficial atual declara **15 new spells, 1 new spell school, 1 structure, 9 mobs e 1 boss**. Entretanto, uma auditoria interna anterior baseada em source inventory registrou **22 spell registrations**. Como esses números divergem, esta ficha mantém ambas as evidências separadas em vez de escolher uma por inferência.

## 2. Authority / ownership
- **ISS** permanece authority do spell registry framework, casting pipeline, mana, cooldown, spell power e school semantics.
- **DTE** é authority dos spells, school adicional, entities, items, structure e configs que registra.
- **Ace's Spell Utils (ASU)** fornece utilitários compartilhados usados pelo addon; não deve ser confundido com provider de conteúdo DTE.
- **AzureLib** fornece infraestrutura de animação/model quando consumida pelo addon.

Mods próprios não devem duplicar mana/cooldown, recast, summon ownership ou damage/healing já processados por um spell DTE.

## 3. Dependências concretas no runtime atual
A modlist física contém:
- **Iron's Spells 'n Spellbooks `1.21.1-3.16.3`**;
- **Ace's Spell Utils `1.2.7.2-1.21.1`**;
- **AzureLib `3.1.11`**.

A release 1.4.4 corrige especificamente um **crash com a versão mais recente de ASU** e um **problema de config**. Esses dois pontos são regression gates desta build.

## 4. Spells publicamente documentados
A página oficial lista nominalmente os seguintes spells:
1. **Silence** [Eldritch] — impede o alvo de castar spells;
2. **Esoteric Edge** [Eldritch] — slash amplo, com bônus ao segurar arma;
3. **Boogie Woogie** [Evocation] — troca posição entre caster e alvo;
4. **Guardian's Gaze** [Evocation] — ray-like attack que aplica Mining Fatigue;
5. **Otherworldly Presence** [Eldritch] — teleport com estado que impede receber/causar dano e impede casting;
6. **Abracadabra** [Eldritch] — buff com damage cap e bloqueio de efeitos negativos, configurável;
7. **Conjure: Forsaken Aid** [Eldritch] — summon de entidades eldritch como Sightless Maw, Untold Behemoth e Eldritch Apostle;
8. **Conjure: Gaoler** [Eldritch] — invoca Gaoler, que pode atacar entidades próximas inclusive o caster;
9. **Esoteric Strike** [Eldritch] — golpe frontal escalado por attack damage;
10. **Mend Flesh** [Eldritch] — cura inicial e interação de lifesteal/healing com XP orbs, configurável;
11. **Rift Walker** [Eldritch] — avanço/teleport com rifts instáveis que explodem depois;
12. **Exorcism** [Holy] — limpa insanity stacks; só habilitado quando insanity está habilitada;
13. **Crystalline Carver** [Ice] — sequência de slashes com Chilled/Frostbite e golpe final escalado por Frostbite;
14. **Glacial Cleave** [Ice] — icy slash de baixo dano que aprisiona entidades em ice tomb.

### Divergência de contagem
A página oficial diz “15 new spells”, mas o texto público atualmente nomeia 14. **O 15º nome não é inventado aqui.**

A auditoria interna anterior registrou um inventário source-pinned de **22/22** distribuído como:
- 9 Eldritch;
- 1 Blood;
- 2 Evocation;
- 2 Fire;
- 1 Holy;
- 2 Ice;
- 5 Ritual.

Essa contagem é preservada como evidência interna, porém o repositório público atual acessível nesta rodada não reproduz esse inventário: o `master` exposto contém um `SpellRegistry` antigo com apenas Silence e Boogie Woogie registrados. Portanto, **não é válido usar o master público como representação do JAR 1.4.4**.

## 5. Itens publicamente documentados
A página oficial nomeia:
- **The God Spear** — holy spear com Divine Smite 6;
- **Greatsword of The Depths** — greatsword com Planar Sight 4;
- **Ice Spear** — inscribed with Glacial Cleave;
- **The Snowgrave** — halberd com Crystalline Carver e Ice Tomb;
- **Forsaken Flamberge** — upgrade eldritch de Decrepit Flamberge;
- **Ymir** — blade com Esoteric Edge 6 e Silence 6;
- **Staff of Vehemence** — staff com Esoteric Edge 6 e Abracadabra 6;
- **The Black Book** — spellbook eldritch, drop de The Ascended One;
- **Staff of Ascension** — staff, drop de The Ascended One;
- **The Apocrypha** — spellbook eldritch que aumenta eldritch power;
- **Corrupted Cloth**;
- **Echo Vibration Ring** — ring que concede recasts para Boogie Woogie;
- **Tempestuous Tome** [unobtainable atualmente];
- **Frozen Folios** [unobtainable atualmente];
- **Guardian's Guidebook** [unobtainable atualmente];
- **Diary of Decay** [unobtainable atualmente].

“Unobtainable” aqui significa que a própria documentação diz que aparecem no Creative, mas ainda não possuem rota survival.

## 6. Entities / world content
A descrição oficial confirma quantitativamente **9 mobs, 1 boss e 1 structure**, mas não fornece na mesma página uma lista textual completa dos nomes. A ficha não inventa os nomes ausentes.

Entidades explicitamente citadas no spell **Conjure: Forsaken Aid** incluem **Sightless Maw, Untold Behemoth e Eldritch Apostle**; **Gaoler** é citado em seu próprio summon. **The Ascended One** aparece como source de drops de Black Book e Staff of Ascension e é tratado como entidade/boss documentado pelo projeto.

## 7. Insanity e efeitos de controle
`Exorcism` depende de insanity estar habilitada. `Otherworldly Presence` cria uma janela de invulnerabilidade/neutralidade com restrição de casting. `Abracadabra` altera damage cap/negative effects e é configurável.

Esses sistemas mudam damage/effect/casting gates; integrações de RPG/Epic Fight não devem aplicar uma segunda camada equivalente sem identificar qual provider tem authority.

## 8. Summons
DTE possui summons que criam entidades combatentes. Ownership, targeting, lifetime e cleanup precisam ser server-authoritative. `Conjure: Gaoler` é especialmente importante porque a própria descrição diz que o summon pode atacar **inclusive o caster**; não assumir “friendly summon” como regra universal.

## 9. Damage, healing e recast
- `Mend Flesh` cruza healing/lifesteal/XP orb events.
- `Echo Vibration Ring` altera recast de Boogie Woogie.
- `Esoteric Strike` escala por attack damage.
- `Crystalline Carver` escala seu golpe final por Frostbite.

Riscos: double-heal, double-damage, recast duplicado, XP orb processado duas vezes e conflito de attribute providers.

## 10. Configuração
A release 1.4.4 registra **“Fixed config issue”**. A documentação pública confirma que pelo menos Abracadabra e Mend Flesh possuem comportamento configurável, além do gate de insanity/Exorcism.

Sem o config schema exato do JAR, esta ficha não inventa nomes de keys/TOMLs. Runtime QA deve inspecionar o config efetivamente gerado.

## 11. Client / Server
O projeto oficial é **Client & Server**. Spell resolution, summon/entity state, damage, healing, recast e worldgen são gameplay state e devem ser server-authoritative. AzureLib/model/animation/particles pertencem à presentation client-side.

A 1.4.4 corrigiu crash de integração com ASU, logo classloading/API drift entre addon e utility lib é risco real.

## 12. Lifecycle
Validar:
- login/relogin e spell availability;
- config load/reload/restart;
- death/respawn durante buffs/recast state;
- dimension change com summons/rifts;
- chunk unload/reload de entities/structure state;
- server restart;
- equip/unequip de ring/spellbooks/weapons;
- worldgen/structure generation em mundo novo e existente.

## 13. Multiplayer
State per-player de buffs/recasts/cooldowns não pode vazar. Summons precisam de ownership consistente. Teleport/swap de Boogie Woogie deve validar ambos os players/entities no servidor. Damage cap/invulnerability não pode ser decidido apenas no cliente.

## 14. Integrações com a modlist
- **ISS 3.16.3:** framework-base obrigatório.
- **Ace's Spell Utils 1.2.7.2:** util dependency; release 1.4.4 corrige compat com versão recente.
- **AzureLib 3.1.11:** dependency documentada pelo projeto para animação/model infrastructure.
- **Epic Fight + ISS compat stack:** pode cruzar attack animation/damage, mas compatibilidade de cada spell/weapon DTE precisa de QA específica.
- Outros addons ISS podem compartilhar schools/effects sem serem substitutos.

## 15. Riscos
1. ASU API drift/crash;
2. config regression;
3. double damage/healing/lifesteal;
4. recast duplicado;
5. summon ownership/cleanup;
6. teleport/swap desync;
7. insanity gate divergente entre client/server;
8. spell/source inventory drift — marketing 15 vs auditoria interna 22;
9. repositório público master desatualizado em relação ao JAR 1.4.4;
10. unobtainable items acidentalmente expostos em progression recipes externas.

## 16. Matriz de testes
1. Dedicated server boot com ISS/ASU/AzureLib atuais.
2. Client join e spell registry availability.
3. Executar os 14 spells publicamente nomeados, um por um.
4. Confirmar spell count/registry real do JAR e reconciliar 15 vs 22 sem inferência.
5. Boogie Woogie em mob e player.
6. Forsaken Aid/Gaoler em multiplayer, ownership e cleanup.
7. Mend Flesh com XP orbs → exatamente uma cura/lifesteal event.
8. Otherworldly Presence → damage dealt/received/casting gates.
9. Abracadabra com config on/off.
10. Exorcism com insanity enabled/disabled.
11. Equip/unequip Echo Vibration Ring e conferir recast.
12. Death/respawn/dimension change com buffs/summons.
13. Generate structure/mobs em world test.
14. Restart e config reload.

**A catalogação não afirma que os testes passaram.**

## 17. Evidências e limitações
- modlist física canônica de 08/09/2026: JAR/mod id/version e dependências presentes;
- CurseForge oficial do projeto e File ID **8816497** para release 1.4.4;
- changelog 1.4.4: fix de crash com ASU recente + config issue;
- descrição oficial para spells/items/world-content counts e comportamentos listados;
- auditoria interna anterior: 22/22 spell registrations por school;
- GitHub público `AceTheEldritchKing/Discerning_The_Eldritch` consultado, mas o `master` acessível está materialmente atrás do runtime 1.4.4; não foi usado para preencher IDs ausentes.

> **Boundary canônico:** DTE fornece conteúdo; ISS continua sendo o framework mágico. Em qualquer divergência de inventário, o JAR físico 1.4.4 e seu registry runtime prevalecem sobre marketing ou source público desatualizado.

# Deeper and Darker: Spellbooks

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db815d9885e302e488d2c2  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Deeper and Darker: Spellbooks
- **Arquivo JAR:** `darkermagic-1.3.3-1.21.1-ver.b.jar`
- **Versão 1.21.1:** `1.3.3-1.21.1`
- **Categoria:** Magia; RPG; Compat
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/deeper-and-darker-spellbooks/files/7897469
- **Função:** Addon de integração entre Deeper and Darker e Iron's Spells 'n Spellbooks: adiciona conteúdo mágico/gear temático e quatro summon spells baseados em criaturas do Deep Dark/Otherside.
- **Dependências:** Obrigatórias: Deeper and Darker + Iron's Spells 'n Spellbooks. Runtime atual usa Deeper and Darker 1.4.1. A build instalada é Version B; não confundir com a variante A.
- **Compatibilidade/Riscos:** Regressão crítica histórica: summon spells podiam crashar servidores; 1.3.3 declara correção. Riscos adicionais: summon duplicado por double-processing, ownership/sync em MP, version drift com ISS/Deeper and Darker e bônus Eldritch duplicado.
- **Sobreposição:** Sobreposição temática com outros addons ISS não implica duplicação. Authority do casting permanece ISS e authority dos mobs originais permanece Deeper and Darker; evitar segunda implementação de summon/cooldown/spell power.
- **Observações:** Version B instalada: Warden Mage/Battlemage Armor usa 10% Eldritch Spell Power; Version A usa 5%. Release 1.3.3 corrige crash dos summon spells após uso em servidores. Testes runtime não foram executados nesta catalogação.
- **Procedência:** Runtime/JAR/mod id/ordem: modlist física canônica de 08/09/2026 (595 top-levels). Release exata: CurseForge File ID 7897469. Conteúdo/inventário dos quatro summons: source público da linha 1.21 previamente auditado; o source localizado declara versão anterior à 1.3.3, divergência mantida explícita no corpo.
- **Histórico da decisão:** Ficha reconstruída em 08/09/2026 no padrão Alex's Mobs contra o runtime 1.3.3 Version B. Mantida a divergência entre binário físico e source público localizado, sem preencher lacunas por inferência.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — bridge D&D/ISS, quatro summons, Version B 10% Eldritch Spell Power, dedicated-server regression, authority/lifecycle/MP e divergência de source catalogados.
- **Data da última decisão:** 2026-09-08.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `darkermagic-1.3.3-1.21.1-ver.b.jar` · mod id `darkermagic` · versão `1.3.3-1.21.1` · NeoForge 1.21.1. Esta é a **Version B** do addon.

## 1. Identidade e versão
- **Mod:** Deeper and Darker: Spellbooks.
- **JAR instalado:** `darkermagic-1.3.3-1.21.1-ver.b.jar`.
- **Mod id:** `darkermagic`.
- **Versão:** `1.3.3-1.21.1`, variante **B**.
- **Loader/jogo:** NeoForge / Minecraft 1.21.1.
- **Publicação oficial:** CurseForge File ID 7897469, release de 09/04/2026.
- **Environment oficial:** Client & Server.

## 2. Papel no modpack
É uma bridge/content addon entre **Deeper and Darker** e **Iron's Spells 'n Spellbooks (ISS)**. Sua responsabilidade é transformar criaturas/tema do Deep Dark/Otherside em conteúdo de magia compatível com o framework de spells do ISS, além de acrescentar equipamento temático próprio.

Não substitui nenhum dos dois providers-base.

## 3. Authority / ownership
- **Deeper and Darker** permanece authority sobre os mobs e conteúdo original do Otherside/Deep Dark.
- **Iron's Spells 'n Spellbooks** permanece authority do casting pipeline, mana, spell schools, cooldowns, spell data e execução genérica de spells.
- **`darkermagic`** é authority apenas sobre os spells, equipamentos e bindings que ele próprio registra.

Uma integração externa não deve duplicar summon, custo, cooldown ou ownership do mob invocado em um segundo handler.

## 4. Conteúdo confirmado
A descrição oficial confirma:
- **Warden Battlemage Armor**;
- **um spellbook novo**;
- **um staff novo**;
- **quatro summon spells**.

O inventário de source já auditado para a linha 1.21 identifica quatro invocações:
1. **Summoned Warden**;
2. **Summoned Shattered**;
3. **Summoned Sculk Centipede**;
4. **Summoned Sculk Snapper**.

A variante instalada **Version B** define o bônus do conjunto Warden Mage/Battlemage em **10% Eldritch Spell Power**; a Version A usa 5%.

### Limite de evidência
O source público localizado para auditoria anterior declarava `1.3.0`, enquanto o binário instalado é `1.3.3`. Portanto, nomes acima são registrados como inventário confirmado da linha 1.21 já auditada, mas esta ficha **não afirma equivalência byte-a-byte do source 1.3.0 com o JAR 1.3.3**.

## 5. Sistema de summons
Os quatro spells usam o framework de magia de ISS para materializar entidades temáticas de Deeper and Darker. A responsabilidade funcional relevante é a transição:

`cast ISS → criação/ownership do summon → AI/targeting da entidade → remoção/expiração conforme implementação`

O addon não deve ser tratado como novo sistema geral de AI. Depois de criada, a entidade continua dependendo dos contratos da entidade/provider correspondente e do código de summon do addon.

## 6. Regressão crítica da versão 1.3.3
O changelog oficial da build física informa explicitamente que a `1.3.3` **corrige os summon spells em servidores**, pois anteriormente podiam provocar crash após o uso.

Isso torna dedicated server + cast das quatro invocações um regression gate obrigatório para este pack.

## 7. Equipamentos e balanceamento Version B
A diferença documentada entre A e B é de balanceamento, não de loader:
- **Version A:** 5% Eldritch Spell Power no Warden Mage Armor set;
- **Version B instalada:** **10% Eldritch Spell Power**.

Qualquer perk/mod próprio que também conceda Eldritch Spell Power deve empilhar/modificar o atributo pelo contrato do ISS em vez de reprocessar o bônus do armor set.

## 8. Configuração e dados
Não foi localizada documentação oficial suficiente para declarar configs próprias específicas da `1.3.3`. Não inventar TOMLs, datapack paths ou toggles.

Recipes, tags e spell metadata devem ser tratados como recursos do addon apenas quando identificados no JAR/source correspondente.

## 9. Client / Server
- **Gameplay e summons:** common/server-authoritative.
- **Casting visual, models, particles e presentation:** cliente, conforme ISS/addon.
- **Estado de entidade:** precisa nascer e permanecer coerente no servidor para multiplayer.

A correção oficial da `1.3.3` reforça que classloading/lifecycle de servidor é uma superfície real de risco.

## 10. Lifecycle
Validar:
- cast inicial e recast quando aplicável;
- despawn/remoção do summon;
- morte do caster e da criatura;
- logout/relogin do caster;
- dimension change;
- chunk unload/reload com summon presente;
- server restart;
- datapack/resource reload quando spells são recarregáveis pelo stack ISS.

## 11. Multiplayer
Summons devem possuir uma única authority de criação e ownership. Em multiplayer, dois jogadores usando os mesmos spells não podem compartilhar state indevidamente, duplicar entidades por packet replay ou produzir execução dupla client+server.

## 12. Integrações concretas no pack
- **Deeper and Darker 1.4.1:** provider dos mobs/tema usados pelo addon.
- **Iron's Spells 'n Spellbooks:** framework mágico obrigatório para os spells e spell-power semantics.
- **Epic Fight / compatibilidades ISS presentes no pack:** podem afetar animação/combate do jogador, mas compatibilidade específica com cada summon não foi confirmada por esta fonte e não deve ser presumida.

## 13. Riscos técnicos
1. regressão de crash pós-cast em dedicated server;
2. summon duplicado por execução client+server;
3. ownership perdido após reconnect/dimension transition;
4. AI/targeting alterado por outros mods de AI;
5. double application de Eldritch Spell Power;
6. version drift entre ISS/Deeper and Darker e o addon;
7. assumir conteúdo do source `1.3.0` como idêntico ao JAR `1.3.3`;
8. remover um provider-base mantendo `darkermagic` instalado.

## 14. Matriz de testes
1. Dedicated server boot com os três mods presentes.
2. Cast individual de **Summoned Warden**.
3. Cast individual de **Summoned Shattered**.
4. Cast individual de **Summoned Sculk Centipede**.
5. Cast individual de **Summoned Sculk Snapper**.
6. Confirmar exatamente uma entidade por cast e ausência de crash depois do uso.
7. Dois jogadores invocando simultaneamente.
8. Morte/relogin/dimension change do caster com summon ativo.
9. Chunk unload/reload e server restart.
10. Equipar/desquipar Warden Mage Armor e verificar o bônus Version B uma única vez.

**Esses testes são requisitos futuros; esta catalogação não afirma que foram executados.**

## 15. Evidências
- modlist física canônica de 08/09/2026: JAR/version/mod id;
- CurseForge oficial, File ID **7897469**, changelog `1.3.3` e distinção Version A/B;
- página oficial do projeto: addon Deeper and Darker + ISS, Client & Server;
- source público da linha 1.21 previamente auditado para o inventário dos quatro summons, com divergência de versão registrada nesta ficha.

> **Boundary canônico:** `darkermagic` fornece o conteúdo mágico de integração; ISS controla o casting e Deeper and Darker continua dono dos mobs/origem temática. A build `1.3.3 Version B` deve ser tratada como runtime authority.

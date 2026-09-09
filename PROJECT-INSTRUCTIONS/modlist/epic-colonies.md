# Epic Fight: Epicfied (Epic Colonies) — 21.0.8

> **Runtime físico confirmado:** `EpicColonies-NeoForge-1.21.1-EFM-21.16.4-21.0.8.jar` · mod id `epic_colonies` · versão `21.0.8` · NeoForge 1.21.1. O pack usa **Epic Fight 21.17.3.1** e **MineColonies 1.1.1381-1.21.1-snapshot**.

## 1. Papel no modpack
Epic Colonies é a integração especializada **MineColonies ↔ Epic Fight**. Seu escopo é fazer cidadãos/raiders do MineColonies participarem da apresentação e do combat framework do Epic Fight sem abandonar a identidade visual dos modelos/jobs do MineColonies.

## 2. Authority / ownership
- **MineColonies:** cidadão/raider, job, AI colonial, inventory, tasks, stats e lifecycle da colônia.
- **Epic Fight:** armature, animation/combat patch e mechanics de combate aplicadas quando a integração as usa.
- **Epic Colonies:** bridge/model assets e adaptação entre os dois providers.

A bridge não deve criar um segundo cidadão, job state ou inventário paralelo.

## 3. Cobertura visual publicada
A página oficial descreve mais de **80 tipos de modelo** refeitos/adaptados para o rig/skinned mesh do Epic Fight, com objetivo de preservar os modelos e acessórios de job do MineColonies. Essa cobertura é um diferencial frente a patches genéricos que apenas aplicam um humanoide padrão.

## 4. Expressões faciais
O projeto documenta olhos 3D que se movem/piscam e boca com animação limitada; as expressões são apresentação client-side derivada do state do cidadão. Elas não alteram happiness, job ou AI do MineColonies.

## 5. Combate dos cidadãos
Quando um cidadão/raider é compatibilizado, Epic Fight fornece a camada de combate/animação correspondente. Damage, death e ownership do cidadão continuam server-authoritative; uma animação não pode liquidar dano duas vezes.

## 6. Relação com Epic Fight Compat geral
`Epic Fight Compat 1.1.0` também está instalado, mas possui escopo genérico para vários mods. Epic Colonies é especializado em MineColonies e preserva modelos/acessórios específicos do ecossistema colonial. Não assumir equivalência nem remover um pelo outro sem mapear patches efetivamente ativos.

## 7. Version drift EFM-21.16.4 → runtime 21.17.3.1
O filename oficial registra alvo de compilação **EFM-21.16.4**. O pack usa Epic Fight **21.17.3.1**. Inspeção anterior da branch 1.21.1 registrou `epicfight` e `minecolonies` como dependências required com range amplo `[0,)`; portanto não há bloqueio de loader por essa diferença.

Isso não elimina risco binário/comportamental: armature, renderer ou patch internals podem mudar em patch releases.

## 8. Client / Server
**Servidor/common:** cidadão, combat state válido, damage/death e AI MineColonies.

**Cliente:** skinned mesh, pose, facial expression, attachment de acessórios e rendering.

Dedicated server não deve carregar renderer/model classes como authority de gameplay.

## 9. Lifecycle
Validar: citizen spawn/load, contratação/job change, guard combat enter/exit, raider spawn, death/respawn replacement, chunk unload/reload, colony restart, dimension/teleport quando aplicável, resource reload e update de MineColonies/Epic Fight.

## 10. Multiplayer
Dois clientes devem observar o mesmo cidadão/raider com combat state coerente. Um observador não pode causar um segundo attack settlement. Tracking range, reconnect e troca de job precisam preservar modelo/acessórios corretos.

## 11. Riscos
1. armature drift entre Epic Fight 21.16.4 e 21.17.3.1;
2. citizen model/job accessory clipping;
3. cidadão manter renderer antigo após job change;
4. patch duplicado por compat genérico;
5. double damage/stun;
6. AI colonial competir com combat goal/patch;
7. facial state stale após reload;
8. raider/citizen específico sem modelo adaptado;
9. snapshot do MineColonies alterar entity/model contract;
10. server/client divergirem sobre death/combat state.

## 12. Matriz de testes
1. Dedicated server com MineColonies 1.1.1381 + Epic Fight 21.17.3.1 + Epic Colonies 21.0.8.
2. Cidadãos de múltiplos jobs, incluindo acessórios distintos.
3. Guard/citizen entrando e saindo de combate.
4. Raider combat e death.
5. Job change sem relog e após relog.
6. Chunk unload/reload da colônia.
7. Dois jogadores observando o mesmo combate.
8. Epic Fight Compat geral carregado simultaneamente.
9. Resource reload/model reload.
10. Smoke-test após qualquer update do MineColonies ou Epic Fight.

**Esta catalogação não afirma que esses testes foram executados.**

## 13. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/versão/hash + Epic Fight/MineColonies atuais;
- página oficial Epic Colonies: dependências, modelos de cidadãos preservados, 80+ model types e facial expressions;
- inspeção anterior do source 1.21.1: ranges de dependência amplos, distinguindo compile target de loader constraint.

> **Boundary canônico:** MineColonies continua authority do **cidadão e da colônia**; Epic Fight do **combat/animation framework**; Epic Colonies apenas integra essas duas superfícies.
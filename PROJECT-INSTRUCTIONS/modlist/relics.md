# Relics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8168aa84e5817c5ca3ed
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `relics-1.21.1-0.12.8.jar`, mod id `relics`, runtime `0.12.8`, mixin `relics.mixins.json`; Curios 9.5.1, OctoLib 0.6.2, FTB Teams 2101.1.11, Sophisticated Backpacks 3.26.2 e addons Reliquified presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Relics 0.12.8 e as dependências/integradores citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Relics
- **Arquivo JAR:** `relics-1.21.1-0.12.8.jar`
- **Versão 1.21.1:** 0.12.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, Magia, Exploração
- **Função:** Framework/content mod de relics e acessórios únicos com habilidades, progressão/XP próprios e loot configurável; provider-base do ecossistema Reliquified instalado.
- **Dependências:** Required publicado: Curios API e ShatterLib | OctoLib. Pack satisfaz via Curios 9.5.1 + OctoLib 0.6.2. Sophisticated Backpacks 3.26.2 é integração optional presente; FTB Teams 2101.1.11 também está presente.
- **Sobreposição:** Compartilha slots/efeitos com Artifacts e outros acessórios, mas Relics mantém authority de sua progressão/abilities. Curios mantém authority dos slots; addons Reliquified estendem conteúdo sem substituir o framework.
- **Compatibilidade/Riscos:** Beta oficial. Riscos: API drift dos addons Reliquified, modifiers/abilities duplicados no lifecycle Curios, target filtering, XP/stat overflow, stale cache/memory retention, dedicated-server classloading e power/loot stacking. Issues/PR upstream citados não foram reproduzidos localmente.
- **Observações:** 0.12.8 adiciona Shield of Retaliation, targets configuráveis, FTB Teams e estatística de relic XP; também otimiza cache/corrige leaks e altera Ghostly Mantle, Midnight Mantle e Springy Boot. Artifacts físico atual é 13.2.3.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + release/dependências oficiais Relics 0.12.8 + source público da linha 1.21 usado apenas para arquitetura + issues/PR upstream marcados como riscos não reproduzidos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/relics-mod
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Relics 0.12.8 reconstruído: progression/XP, Curios ownership, target rules, loot/config, FTB Teams, cache lifecycle, addons Reliquified, riscos upstream e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `relics-1.21.1-0.12.8.jar`, mod id `relics`, versão `0.12.8`, NeoForge 1.21.1. Relics é um sistema de acessórios/relics com habilidades e progressão próprias, loot configurável e integração com Curios. A build 0.12.8 é Beta oficial; isso é maturidade publicada, não evidência automática de incompatibilidade.

## 1. Identidade e papel
- **Mod:** Relics.
- **JAR:** `relics-1.21.1-0.12.8.jar`.
- **Mod id:** `relics`.
- **Versão instalada:** `0.12.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal oficial:** Beta.
- **Ambiente publicado:** Client & Server.
- **Mixin config físico:** `relics.mixins.json`.

## 2. Papel no modpack
Relics adiciona acessórios únicos com habilidades e progressão individual, distribuídos por loot/geração configurável. No pack ele também funciona como **framework/provider** para a família de addons Reliquified instalada logo após esta entrada.

A página deve ser tratada como referência de ownership para XP de relic, levels/ranks, abilities e targeting/configuração do ecossistema Relics.

## 3. Dependências confirmadas
A publicação oficial declara:
- **Curios API** — required;
- **ShatterLib | OctoLib** — required como alternativa de biblioteca;
- **Sophisticated Backpacks** — optional.

No pack atual estão presentes Curios 9.5.1, OctoLib 0.6.2 e Sophisticated Backpacks 3.26.2. Portanto a rota de dependência via OctoLib está satisfeita fisicamente.

## 4. Autoridade / ownership
- **Relics:** relic definitions, progression/experience, ability state, target rules, loot/config do próprio ecossistema.
- **Curios:** slot/equip lifecycle e container/accessory plumbing.
- **FTB Teams:** quando integrado, fornece contexto de equipe; não se torna authority da relic.
- **Addons Reliquified:** possuem seus relics temáticos e bridges; não substituem o framework Relics.
- **Sophisticated Backpacks:** continua authority do próprio armazenamento/upgrades; qualquer interação de relic com backpack deve respeitar esse ownership.

## 5. Conteúdo e registries — escopo confirmado
A release 0.12.8 adiciona explicitamente a relic **Shield of Retaliation** e altera sistemas de targeting/configuration, estatísticas de XP e integrações.

O source público da linha 1.21 — não um pin comprovadamente idêntico ao binário 0.12.8 — mostra o projeto inicializando registries/subsistemas para itens, block entities/tiles, blocks, sounds, badges, entities, effects, commands, particles, loot codecs, creative tabs, data components e relic containers. Essa evidência é usada para mapear arquitetura da linha, **não** para afirmar que cada entrada do branch é exatamente igual ao JAR instalado.

## 6. Progressão e experiência
Relics possui progressão própria de relics. A 0.12.8 adicionou uma estatística de **relic experience gained** e otimizou o cache de relic data, corrigindo memory leaks relacionados.

XP/level/rank devem permanecer server-authoritative. Equip/unequip ou relog não podem reaplicar XP, duplicar modifiers ou resetar cooldown/estado de forma incorreta.

## 7. Targeting configurável
A 0.12.8 adicionou configuração dos **targets que uma relic pode afetar**. Isso cria uma fronteira de segurança importante com aliados, villagers, pets, membros de equipe e entidades neutras.

Uma issue upstream aberta para 0.12.8 relata que **Jellyfish Necklace / Electric Discharge** pode atingir villagers apesar de configurações de target. Isso é risco upstream reportado, não reprodução local.

## 8. Loot e descoberta
A documentação oficial posiciona relics como loot apropriado a exploração/dungeons e oferece configuração de generation/chances/characteristics. A existência da API de loot não implica que todos os relics tenham a mesma origem ou chance.

Addons Reliquified podem inserir relics em estruturas/biomas de seus providers, portanto a origem final de loot deve ser testada pelo conteúdo específico.

## 9. FTB Teams
A versão 0.12.8 adicionou integração com **FTB Teams**. O pack possui FTB Teams 2101.1.11. Essa integração é relevante para identificação de aliados/ownership/targets, mas não autoriza presumir a regra exata de cada relic sem config/runtime.

## 10. Cache e memória
O changelog 0.12.8 cita otimização do relic data cache e correção de memory leaks relacionados. Regression gates:
- equip/unequip repetido;
- login/logout;
- troca de dimensão;
- descarte de player/entity;
- restart de servidor;
- long session com várias relics.

Cache stale não pode reter player/level antigo nem recriar modifiers duplicados.

## 11. Client / Server
Relics é Client & Server. Gameplay, XP, targeting e ability state devem ser validados no servidor; renderer, tooltips, particles e outros feedbacks podem ser client-facing.

Existe um PR upstream posterior à 0.12.8 propondo mover um `appendHoverText` mixin para classe client-only para evitar crash de dedicated server. Isso é **sinal de risco upstream pós-release**, não prova de que o pack atual falha; dedicated server boot é teste obrigatório.

## 12. Lifecycle crítico
Validar:
- acquire/loot de relic;
- equip/unequip em Curios;
- login/relogin;
- death/respawn;
- dimension change;
- XP gain e level/rank progression;
- cooldown start/end;
- team membership change;
- world/server restart;
- config/datapack reload quando aplicável;
- remoção de relic enquanto ability está ativa.

## 13. Multiplayer e idempotência
Para cada evento funcional deve existir uma única aplicação do efeito/XP. Em multiplayer, owner/caster/target precisam ser identificados corretamente e sincronizados. Addons não devem recalcular progressão em paralelo com Relics.

Um jogador desconectando durante cooldown/ability não deve deixar state órfão que afete outro UUID.

## 14. Integrações concretas na modlist
- **Curios 9.5.1:** equip/slot provider obrigatório.
- **OctoLib 0.6.2:** rota de biblioteca requerida presente.
- **FTB Teams 2101.1.11:** integração adicionada na 0.12.8.
- **Sophisticated Backpacks 3.26.2:** integração opcional relevante no pack.
- **Reliquified Ars Nouveau 0.8.1.**
- **Reliquified Artifacts 1.0.8.**
- **Reliquified Iron's Spells 'n Spellbooks 0.2.7.**
- **Reliquified L_Ender's Cataclysm 0.1.1 + fix 1.0.2.**

Esses addons aumentam a superfície de regressão quando Relics atualiza.

## 15. Delta confirmado da 0.12.8
- nova relic **Shield of Retaliation**;
- targets configuráveis por relic;
- integração FTB Teams;
- estatística de relic XP ganho;
- cache de relic data otimizado e memory leaks corrigidos;
- atualização da fog da **Ghostly Mantle**;
- atualização do sky da **Midnight Mantle**;
- remoção de screen shake da **Springy Boot**.

Esses são regression gates diretamente vinculados à build instalada.

## 16. Riscos técnicos
1. **API drift:** quebra dos addons Reliquified após update.
2. **Duplicate modifiers/abilities:** Curios lifecycle processado mais de uma vez.
3. **XP overflow:** issue upstream relata Experience Disperser parando de evoluir quando estatística distribuída ultrapassa o limite de inteiro 32-bit.
4. **Target filtering:** issue upstream da Jellyfish Necklace/villagers.
5. **Cache stale/memory retention:** área explicitamente corrigida na 0.12.8.
6. **Dedicated-server classloading:** risco indicado por PR upstream posterior.
7. **Power stacking:** Relics + Artifacts + addons + outros acessórios do pack.
8. **Loot duplication:** múltiplos injectors adicionando a mesma recompensa.
9. **State migration:** XP/rank/cooldown ao trocar de versão/framework.

## 17. Matriz de testes
- [ ] Dedicated server inicia com Relics 0.12.8 + Curios + OctoLib.
- [ ] Relic equipada aplica modifier/ability uma única vez.
- [ ] Unequip remove state/modifier sem residue.
- [ ] XP e level/rank persistem após relog/restart.
- [ ] Death/respawn não duplica XP/modifiers.
- [ ] Dimension change mantém owner/state correto.
- [ ] Shield of Retaliation funciona sem double-trigger.
- [ ] Target rules respeitam aliados/villagers/pets conforme configuração usada.
- [ ] FTB Teams não causa friendly-fire indevido nem bloqueia target válido.
- [ ] Sessão longa não demonstra crescimento anômalo relacionado ao relic data cache.
- [ ] Addons Reliquified carregam e suas relics progridem sob o mesmo provider.
- [ ] Experience Disperser é stress-tested para overflow em ambiente controlado.
- [ ] Sophisticated Backpacks interaction não duplica pickup/XP.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- Modlist física: JAR, mod id, versão, mixin e versões das dependências/integradores presentes.
- Release oficial 0.12.8: canal, ambiente e changelog exato.
- Dependências oficiais: Curios API, ShatterLib/OctoLib, Sophisticated Backpacks optional.
- GitHub público da linha 1.21: arquitetura geral de registries/subsistemas, sem tratá-lo como source pin exato do JAR.
- Issues/PR upstream: riscos conhecidos, explicitamente marcados como **não reproduzidos localmente**.
- **Limite:** não foi enumerada cada relic do mod nem cada config entry; isso exigiria source/JAR audit pinning adicional e não é necessário para inventar conteúdo.

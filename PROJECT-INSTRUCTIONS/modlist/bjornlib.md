# BjornLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d98d6ec39cc94db94f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** BjornLib
- **Arquivo JAR:** `bjornlib-neoforge-1.0.88-1.21.1.jar`
- **Versão 1.21.1:** 1.0.88
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca compartilhada com Ability Registry/events, Particle Builder com glowing rendering/default particles, Lightning Builder e Leveling Builder para mobs, usada por mods do autor.
- **Dependências:** Biblioteca de infraestrutura; necessidade determinada pelos consumidores que a declaram. Não é substituível automaticamente por outra library.
- **Sobreposição:** Biblioteca técnica específica. APIs conceitualmente parecidas em outras libs não são substitutas binárias dos consumidores BjornLib.
- **Compatibilidade/Riscos:** APIs de ability/event/particle/lightning/leveling podem ser usadas por gameplay e render; riscos de event duplication, client/server authority incorreta, entity state órfão e consumer incompatível após update.
- **Observações:** Descrição oficial confirma Ability Registry + events, Particle Builder com glowing rendering/default particles, Lightning Builder e Leveling Builder para mobs. Nenhuma classe interna adicional foi inventada além das superfícies publicadas.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial BjornLib 1.0.88 NeoForge 1.21.1 + superfícies públicas e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `bjornlib-neoforge-1.0.88-1.21.1.jar` / `1.0.88`; detalhes internos não confirmados continuam não inferidos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/bjornlib
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #78: `bjornlib-neoforge-1.0.88-1.21.1.jar` / `1.0.88` conferidos contra a modlist atual; Ability Registry/events, Particle Builder, Lightning Builder e Leveling Builder preservados de forma fail-closed.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, BjornLib 1.0.88 foi reconfirmado como biblioteca estrutural. Em 09/09/2026, a versão física e as quatro superfícies públicas documentadas foram revalidadas sem inferir decisão curatorial.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `bjornlib-neoforge-1.0.88-1.21.1.jar`, mod id `bjornlib`, runtime `1.0.88`, NeoForge 1.21.1. O projeto oficial define BjornLib como biblioteca de código genérico para os mods do autor.

## 1. Papel e autoridade
BjornLib fornece componentes reutilizáveis para mods consumidores. Ela não adiciona uma progressão própria ao jogador apenas por estar instalada. As superfícies publicamente documentadas incluem Ability Registry/events, partículas, lightning e mob leveling.

O **consumer** continua authority do significado de cada ability, efeito, mob ou ação. BjornLib fornece infraestrutura.

## 2. Ability Registry
O projeto confirma um **Ability Registry** e eventos relacionados. Esse registry é uma superfície de extensão para consumidores registrarem/organizarem abilities sem manter implementações totalmente isoladas.

Regras operacionais:
- IDs de ability precisam ser estáveis;
- registration deve ocorrer uma vez;
- handlers de evento não podem conceder a mesma ability/efeito duas vezes;
- uma ability visual não deve ser confundida com permissão server-side para executar gameplay.

## 3. Ability events
A descrição oficial menciona eventos relacionados ao Ability Registry. Sem source versionado auditado nesta etapa, nomes exatos/classes/signatures não são publicados na ficha.

O contrato seguro é:
- event producer pertence à library/consumer;
- listener deve ser idempotente quando necessário;
- cancelar/observar um evento não deve reemitir outro settlement sem intenção explícita;
- multiplayer state deve permanecer associado à entity/player correto.

## 4. Particle Builder
BjornLib documenta **Particle Builder** com:
- glowing rendering em estilo mágico;
- partículas default fornecidas pela library.

Particles são principalmente apresentação client-side. Se um consumer usa um particle event como sincronização visual de um golpe/spell, o dano/efeito real precisa ter sido resolvido pelo servidor/provider antes ou independentemente do render.

## 5. Lightning Builder
A library expõe um **Lightning Builder**. A documentação pública não prova que todo lightning criado por essa superfície cause dano ou corresponda a entidade vanilla `LightningBolt`; portanto a ficha não faz essa inferência.

Uma integração deve distinguir:
- lightning visual;
- lightning entity/gameplay;
- dano/efeito produzido pelo consumer.

## 6. Leveling Builder para mobs
O projeto confirma um **Leveling Builder to level up mobs**. Isso é infraestrutura para consumidores construírem progressão/escala de mobs.

Em um pack com outros sistemas de mob scaling/RPG, o risco central é **double scaling**. Um mob cujo consumer usa BjornLib não deve receber automaticamente um segundo cálculo só porque o modpack também possui progressão própria.

## 7. O que não foi inferido
Sem source 1.0.88 auditado nesta etapa, a ficha não inventa:
- nomes de classes concretas;
- campos/configs internos;
- fórmula de leveling;
- número de abilities;
- packet IDs;
- partículas registradas individualmente;
- regras de persistência específicas.

Esses pontos permanecem provider-specific até inspeção versionada.

## 8. Client/server
- ability eligibility/state, mob level e efeitos de gameplay devem ser server-authoritative quando afetam o jogo;
- particle rendering é cliente;
- lightning pode ter componente visual e/ou gameplay dependendo do consumer;
- código comum precisa separar classes exclusivamente de render.

## 9. Lifecycle
Consumidores BjornLib devem ser testados em:
- entity spawn/despawn;
- chunk unload/reload;
- death/respawn;
- dimension change;
- server restart;
- ability registration/bootstrap;
- resource reload para assets/particles;
- mob leveling aplicado na criação vs reload de entidade.

Um mob não pode “subir de nível” novamente em cada load se o consumer pretendia aplicação única.

## 10. Multiplayer
State de ability/level deve pertencer à entidade correta. Eventos não podem usar cache global que faça abilities ou níveis atravessarem jogadores/mobs. Partículas podem ser broadcast visual, mas o settlement de gameplay precisa continuar centralizado.

## 11. Riscos
1. Ability registrada duas vezes.
2. Event listener duplicando proc/dano/efeito.
3. Particle ou lightning visual virando authority de gameplay.
4. Mob Leveling Builder combinando-se duas vezes com outro scaler.
5. State de entity persistindo após unload/death de forma indevida.
6. Consumer compilado contra API BjornLib diferente.
7. Classes client-only carregadas em dedicated server.

## 12. Matriz de testes
1. Dedicated server boot com consumidores BjornLib.
2. Registro de abilities sem duplicate id/event handler.
3. Ability em multiplayer: owner correto após reconnect/death.
4. Particle builder após resource reload.
5. Lightning visual/gameplay distinguido pelo consumer real.
6. Mob leveling: spawn inicial, save/reload e chunk unload sem reaplicação indevida.
7. Interação com outros sistemas de mob scaling do pack sem double scaling.

## 13. Evidência
- modlist física atual: BjornLib 1.0.88 NeoForge 1.21.1;
- CurseForge oficial do projeto e arquivo 1.0.88;
- descrição oficial: Ability Registry/events, Particle Builder com glowing rendering/default particles, Lightning Builder e Leveling Builder.

> 🧰 Exaustividade proporcional e fail-closed: as quatro superfícies públicas confirmadas estão catalogadas; detalhes internos não publicados para a 1.0.88 não foram inventados.

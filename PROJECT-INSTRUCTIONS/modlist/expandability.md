# ExpandAbility — 12.0.0

> **Runtime físico confirmado:** `expandability-12.0.0.jar` · mod id `expandability` · versão `12.0.0` · NeoForge 1.21.1. O source oficial está pinável na tag **v12.0.0**; essa versão foi a atualização para Minecraft 1.21.

## 1. Papel no modpack
ExpandAbility é uma biblioteca/API de baixo nível para ampliar controle sobre habilidades vanilla de movimento e interação com fluidos. Na tag 12.0.0, as superfícies públicas verificadas são especialmente **swimming** e **living-fluid collision**.

## 2. Authority / ownership
- **Minecraft:** movimento, fluid physics, collision e player state base.
- **ExpandAbility:** hooks/eventos que permitem a mods consumidores autorizar/negar/alterar comportamentos específicos.
- **Consumer:** regra gameplay que decide usar o hook.

A biblioteca não deve ser descrita como um mod de voo/step-height por si só sem consumer comprovado.

## 3. API exata v12.0.0
O source v12.0.0 expõe no dispatcher:
- `onPlayerSwim(Player)`;
- `onLivingFluidCollision(LivingEntity, FluidState)`.

No módulo NeoForge, essas superfícies correspondem a:
- `PlayerSwimEvent`;
- `LivingFluidCollisionEvent`.

Esses nomes são source-pinned à tag 12.0.0 e podem mudar em versões futuras.

## 4. PlayerSwimEvent
Permite que consumers interfiram na decisão de swimming do jogador. O evento deve ser tratado como parte do movement state, não como animação puramente visual.

Client prediction e server validation precisam convergir para evitar rubber-banding.

## 5. LivingFluidCollisionEvent
Permite modificar a colisão de uma `LivingEntity` com um `FluidState`, viabilizando mecânicas do tipo caminhar/colidir com fluidos quando um consumer assim determina.

Isso toca physics/collision e deve ser server-authoritative quando afeta posição ou movimento real.

## 6. Mixins v12.0.0
A árvore da tag mostra mixins em áreas de:
- fluid collision;
- swimming em Entity/LivingEntity/Player;
- bubble columns;
- fall location;
- paths client e server para player/swim sync.

Esses mixins existem para expor/implementar os hooks; não significam que a biblioteca habilite uma habilidade sozinha.

## 7. Embedded copies vs top-level
A modlist física também mostra cópias **JarJar** de ExpandAbility 12.0.0 dentro de outros JARs, incluindo Artifacts e outro consumer. Isso não transforma automaticamente o top-level em redundante: cada artefato pode resolver sua própria dependência embarcada, enquanto um consumer externo pode esperar o mod top-level.

Remoção exige dependency graph de todos os consumers.

## 8. Relação com Caelus/Pehkui
Caelus, Pehkui e outras APIs de movimento podem coexistir porque expõem contratos diferentes. Não substituir ExpandAbility por outra library apenas porque todas tocam movement/abilities.

A tag 12.0.0 não autoriza afirmar equivalência com flight API, scale API ou attribute API.

## 9. Client / Server
A biblioteca possui code paths client e server. Movement prediction ocorre no cliente, mas collision/swim state que muda posição precisa convergir no servidor.

Dedicated server é regression gate obrigatório para qualquer consumer que use os eventos.

## 10. Lifecycle
Validar player join, entrada/saída de fluid, start/stop swimming, bubble column, fall into fluid, dimension change, death/respawn, reconnect, fluid modded e update de consumers.

## 11. Multiplayer
Dois clients não podem discordar persistentemente sobre swimming/collision. Server correction excessiva indica consumer incompatível ou event result divergente.

## 12. Riscos
1. consumer depender de API diferente da v12.0.0;
2. mixin collision conflitar com outro fluid/physics mod;
3. client predizer swimming diferente do servidor;
4. fluid modded não seguir assumptions vanilla;
5. living-fluid collision permitir posição inválida;
6. bubble-column/fall behavior quebrar;
7. JarJar e top-level carregarem versões diferentes em atualização futura;
8. remover top-level por falsa redundância;
9. consumer registrar decisão conflitante com outro consumer;
10. atribuir gameplay próprio à biblioteca sem evidência.

## 13. Matriz de testes
1. Dedicated server boot com top-level 12.0.0.
2. Entrar/sair de água em swim normal.
3. Consumer conhecido que altera PlayerSwimEvent, se identificado.
4. LivingFluidCollisionEvent com entidade viva.
5. Bubble column.
6. Fall damage ao cair em/através de fluidos.
7. Fluid modded relevante do pack.
8. Dois jogadores observando movement.
9. Reconnect/dimension change durante estado alterado.
10. Auditoria de consumers antes de qualquer remoção do top-level.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física canônica: top-level 12.0.0 e cópias JarJar internas;
- GitHub oficial `florensie/ExpandAbility`, tag `v12.0.0`: changelog, `EventDispatcher`, `PlayerSwimEvent`, `LivingFluidCollisionEvent` e mixin tree.

> **Boundary canônico:** ExpandAbility fornece **hooks de movement/fluid ability**; a regra concreta pertence ao consumer e o servidor continua authority da posição física.
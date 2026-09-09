# EMF Compat: Create — 2.0.0

> **Runtime físico confirmado:** `emf_compat_create_1.21.1_2.0.0.jar` · mod id `emf_compat_create` · versão `2.0.0` · NeoForge 1.21.1 · **Client-only**.

## 1. Papel no modpack
EMF Compat: Create é uma bridge visual entre **Create** e **Entity Model Features**. Ela evita que a animation do resource pack EMF continue sobrescrevendo a pose que Create precisa mostrar durante ações específicas do jogador.

## 2. Authority / ownership
- **Create/Aeronautics/rope provider:** ação/movement state e pose funcional correspondente.
- **EMF:** player model/resource-pack animation fora dessa ação.
- **EMF Compat Core:** captura/restauração de pose.
- **EMF Compat: Create:** regra específica de quando pausar/preservar a animation Create.

Nenhuma física, movement ou contraption logic é decidida por este mod.

## 3. Skyhook
A documentação oficial destaca o **Create Skyhook** como principal superfície. Durante o ride, animações EMF do jogador são pausadas/preservadas para que a pose do Skyhook permaneça visível.

Movement e attachment continuam sob Create; a compat atua apenas no render do player.

## 4. Chains e ropes
O projeto declara suporte a chain/rope riding associado aos mechanics de Skyhook, inclusive rotas ligadas a **Create Aeronautics**. O pack instala Aeronautics 1.3.2 e **Climbable Ropes 2.1.3**, portanto essa superfície é ativa localmente.

## 5. Additional compatibility
A página oficial também cita:
- Create Aeronautics;
- Climbable Ropes;
- Create Grappling Hooks;
- Grabbing Physics Objects.

Isso descreve capacidade upstream. Neste pack, Aeronautics e Climbable Ropes foram confirmados fisicamente; os demais não são assumidos ativos sem presença física.

## 6. Resource-pack animations
O projeto foi testado upstream com **Fresh Animations: Player Extension** e **Detailed Animations**, e o autor espera compatibilidade com a maioria dos packs de player animation via EMF.

Isso não é garantia universal: cada pack pode alterar hierarquia/ossos/poses e deve ser testado no stack real.

## 7. Pose pause/restore
Ao iniciar a ação suportada, a compat precisa impedir que a idle/walk animation do EMF recupere os membros indevidamente. Ao terminar, deve devolver o controle ao EMF sem snap permanente, pose congelada ou frame residual.

## 8. First / third person
Embora a função principal seja player model rendering, primeira e terceira pessoa podem usar pipelines distintos. Uma correção visual em third person não prova ausência de clipping em first person com outros first-person mods do pack.

## 9. Client-only boundary
CurseForge classifica o projeto como **Client**. O servidor não usa esta bridge para validar riding, speed, attachment ou collision. Remover a compat de um cliente deve no máximo degradar a pose visual, não mudar a física Create do servidor.

## 10. Lifecycle
Validar:
- iniciar/encerrar Skyhook;
- trocar chain/rope segment;
- mount/dismount em Aeronautics;
- disconnect durante ride;
- death/respawn;
- first/third person toggle;
- resource reload;
- troca de player animation pack;
- update Create/Aeronautics/EMF/Core.

## 11. Multiplayer
Outros jogadores precisam ver a pose correta do rider a partir do movement state normal sincronizado. Cada cliente aplica a compat localmente; não deve haver packet gameplay específico apenas para pose correction.

## 12. Riscos
1. EMF animation continuar ativa durante Skyhook;
2. pose não ser restaurada ao desmontar;
3. body/arms snap entre frames;
4. first/third person divergirem;
5. Aeronautics rope state não ser detectado;
6. Climbable Ropes mudar hooks;
7. outro animation compat capturar a mesma pose;
8. resource reload deixar pose stale;
9. disconnect durante ride deixar animation congelada;
10. update Create/EMF alterar o render hook esperado.

## 13. Matriz de testes
1. Cliente com Create 6.0.10 + EMF 3.3.5 + Core 2.0.0.
2. Skyhook vanilla Create com player animation pack ativo.
3. Chain/rope ride do stack Aeronautics.
4. Climbable Ropes 2.1.3.
5. Mount/dismount repetido.
6. First/third person durante ride.
7. Outro jogador observando o rider.
8. Disconnect/reconnect durante ação.
9. Resource reload e troca de pack.
10. Smoke-test após update de Create/Aeronautics/EMF.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física: JAR/mod id/version/SHA-1, Create 6.0.10, Aeronautics 1.3.2, Climbable Ropes 2.1.3;
- CurseForge oficial: Client-only, release 2.0.0 NeoForge 1.21.1;
- descrição oficial: pausa EMF durante Skyhook, chain/rope/Aeronautics e compat adicionais.

> **Boundary canônico:** EMF Compat: Create controla apenas a **precedence visual da pose**. Create/Aeronautics continuam authority do movement e physics state.

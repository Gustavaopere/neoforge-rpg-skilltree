# Climbable Ropes for Create Aeronautics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c181ebfd06bfadff42
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Climbable Ropes for Create Aeronautics
- **Arquivo JAR:** `climbable_ropes-2.1.3.jar`
- **Versão 1.21.1:** 2.1.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Tecnologia
- **Função:** Addon de Create Aeronautics/Simulated que permite agarrar e escalar cordas verticais/plunger ropes com movimento, slide, jump-off e animação sincronizada de escalada.
- **Dependências:** Voltado a Create Aeronautics/Simulated. Player Animator 2.0.4+1.21.1 é confirmado pela documentação oficial da branch como library MIT embarcada via jar-in-jar; uma cópia top-level pode coexistir, com NeoForge resolvendo a versão aplicável.
- **Sobreposição:** Mecânica específica de escalada em ropes do stack Aeronautics. Pode cruzar Parkour/mobility/camera/player animation mods, mas não substitui genericamente sistemas de corda ou climb.
- **Compatibilidade/Riscos:** State de climbing/movement precisa permanecer server-coherent enquanto animação é client-side. 2.1.3 corrige drift em contraptions móveis, herança de velocity no dismount e override da hanging pose por FA-PE; 2.1.1 já corrigia respawn/dimension, disconnect stale pose e render cancellation. Riscos com mobility/input/camera/player-render mods e rope physics do Aeronautics.
- **Observações:** JAR físico atual `climbable_ropes-2.1.3.jar`, mod id `climbable_ropes`, runtime 2.1.3. A 2.1.3 corrige drift/velocity em Create contraptions móveis e conflito de hanging pose com FA-PE; Player Animator 2.0.4+1.21.1 permanece embarcado via jar-in-jar.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Climbable Ropes 2.1.3 + changelogs oficiais 2.1.1/2.1.3 + metadata física do Player Animator 2.0.4+1.21.1 embarcado via jar-in-jar.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — runtime físico 2.1.3, fixes de drift/velocity em contraptions móveis, herança de velocidade no dismount, conflito FA-PE de hanging pose e regressões lifecycle 2.1.1 confirmados no QC global #95. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026 uma leitura anterior reconciliou provisoriamente o dossiê à 2.1.1; a modlist física mais recente de 08/09/2026 confirma `climbable_ropes-2.1.3.jar`. A conclusão válida sobre Player Animator permanece: a cópia embarcada via jar-in-jar não torna automaticamente redundante eventual cópia top-level exigida por outros consumers.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> ✅ Autoridade física atual: `climbable_ropes-2.1.3.jar`, mod id `climbable_ropes`, runtime `2.1.3`, NeoForge 1.21.1. A 2.1.3 corrige drift/velocity em contraptions móveis e conflito de hanging pose com FA-PE. Player Animator 2.0.4+1.21.1 permanece embarcado via **jar-in-jar** e não vira top-level deste lote.

## 1. Papel e authority
Climbable Ropes adiciona interação de escalada às ropes verticais do ecossistema Create Aeronautics/Simulated. O provider da rope/physics continua sendo o stack Aeronautics; este addon controla o **grab/climb/slide/jump-off** do jogador e sua apresentação animada.
Não duplicar a movimentação aplicando um segundo climb velocity por mod próprio.

## 2. Entrada e controle
A documentação oficial descreve o fluxo de mãos vazias:
- right-click para agarrar a rope;
- W/S para subir/descer;
- Space para saltar/soltar;
- Sneak para largar;
- slide rápido é uma ação própria da linha atual.
Input do cliente é intenção; a posição/movimento final precisa permanecer coerente com a authority do jogador/servidor.

## 3. Verticalidade
O addon é voltado a ropes verticais ou suficientemente verticais. A config da animação usa critério de aproximadamente 45 graus para decidir quando aplicar a layer customizada.
Não extrapolar isso como fórmula de física universal da rope; é critério documentado da superfície de animação.

## 4. Plunger ropes
A descrição oficial inclui ropes do Plunger Launcher/stack Aeronautics. O addon deve reagir à rope real, não registrar uma segunda entidade/cabo equivalente.
Se a rope desmontar, mover ou desaparecer, climbing state deve encerrar sem deixar jogador preso a um attachment stale.

## 5. Player Animator jar-in-jar
A documentação da própria branch confirma **KosmX Player Animator** embarcado como jar-in-jar, versão 2.0.4+1.21.1 na linha auditada. Não é top-level separado por este host.
Se uma cópia top-level também existir, isso não autoriza remoção automática: NeoForge resolve a library carregada e outros consumers podem depender da cópia externa.

## 6. Animation layer
A animação é client-side, mas o climb state é sincronizado para outros clientes. A config documenta uma layer de prioridade 40 e animações distintas para subida, descida e slide.
Animation state é apresentação; não pode conceder climbing quando o jogador já não está preso à rope.

## 7. Histórico de regressões relevante — 2.1.1 → 2.1.3
A **2.1.1** corrigiu três regressões de lifecycle/render que continuam válidas como regression gates da linha atual:
1. animação de climb parando após respawn/dimension change;
2. pose stale vazando para o próximo mundo após disconnect durante climb;
3. glitch de render quando outro mod cancela player rendering durante climb.
A **2.1.3**, que é a build física atual, acrescenta dois fixes diretamente relevantes ao pack:
1. jogadores deixavam de acompanhar corretamente contraptions Create em movimento enquanto escalavam ropes ou usavam ziplines, podendo derivar da estrutura; a correção também cobre herança da velocidade da contraption ao desmontar;
2. a animação de voo do FA-PE podia sobrescrever a hanging pose em flat ropes, ziplines e chain conveyors do Create; a 2.1.3 corrige essa prioridade/interoperabilidade.
Portanto, QA atual precisa cobrir simultaneamente transitions de lifecycle herdadas da 2.1.1 e movimento/animação específicos da 2.1.3.

## 8. Respawn e dimension change
Climb state transitório precisa ser reconstruído/limpo em clone/respawn e teleport/dimension transition. O jogador não deve reaparecer marcado como climbing sem rope válida.
A correção 2.1.1 prova que essa superfície já teve regressão e merece teste explícito.

## 9. Disconnect/reconnect
Disconnect no meio da escalada deve limpar state local e remoto. Ao entrar em outro mundo/server, nenhuma pose ou attachment anterior pode sobreviver.
O fix de stale pose da 2.1.1 é especificamente relevante para packs com troca frequente de mundos/servidores.

## 10. Render interoperability
Outro mod pode cancelar/substituir player render. A 2.1.1 corrige glitch nesse cenário, portanto compat com First Person/animation/camera/render mods deve ser testada pelo resultado final, sem forçar a animação em callbacks cancelados.

## 11. Client/server e multiplayer
- rope attachment/movimento que afeta posição: state coerente com servidor;
- input: cliente → validação/settlement;
- animation layer: cliente;
- observadores recebem state suficiente para render coerente.
Dois clientes não podem divergir sobre o jogador ainda estar preso à rope depois de detach/death/teleport.

## 12. Lifecycle
Validar grab, climb, slide, jump-off, rope movement, rope removal, death/respawn, dimension change, disconnect/reconnect, resource reload e render cancellation.
Qualquer attachment temporário deve ser descartado quando a entidade/rope deixa de ser válida.

## 13. Riscos
1. Double movement com outro climbing provider.
2. Pose stale após transition.
3. Input client manter attachment inválido.
4. Player Animator version drift entre jar-in-jar e top-level.
5. Render mods cancelarem/substituírem layer.
6. Rope physics mover attachment enquanto player state usa posição antiga.
7. Drift entre movimento da contraption e attachment do jogador reaparecer em ships/elevators/ziplines.
8. FA-PE ou outro animation provider sobrescrever a hanging pose válida em flat ropes/chain conveyors.
9. Documentação ou integração futura voltar a tratar 2.1.1 como runtime físico apesar do JAR atual 2.1.3.

## 14. Matriz de testes
1. Dedicated server boot com stack Aeronautics.
2. Grab/release de rope vertical com mãos vazias.
3. W/S climb, slide, Sneak detach e Space jump-off.
4. Rope/plunger/zipline em contraption móvel: jogador acompanha a estrutura sem drift e herda a velocity correta ao desmontar — regressão 2.1.3.
5. Flat ropes, ziplines e Create chain conveyors com FA-PE: hanging pose não é sobrescrita pela flying animation — regressão 2.1.3.
6. Death/respawn durante climb — regressão 2.1.1.
7. Dimension travel durante/após climb — regressão 2.1.1.
8. Disconnect durante climb → outro mundo/server sem stale pose.
9. Player render cancelado/substituído por mod de animação/câmera.
10. Multiplayer: outro jogador observa animação/state correto.
11. Player Animator jarjar + eventual top-level coexistindo sem linkage/mixin failure.
12. Detach/dismount em moving contraption não aplica velocity duas vezes nem deixa attachment stale.

## 15. Evidência
- modlist física atual: `climbable_ropes-2.1.3.jar`, runtime 2.1.3;
- CurseForge oficial 2.1.3: NeoForge 1.21.1, Client & Server;
- descrição oficial: grab/climb/slide/jump interactions;
- metadata física: Player Animator 2.0.4+1.21.1 embarcado via jar-in-jar;
- changelog 2.1.1: fixes de respawn/dimension, disconnect stale pose e render cancellation;
- changelog 2.1.3: fixes de drift/velocity em contraptions móveis e override de hanging pose por FA-PE.

> 🪢 Boundary canônico: o addon controla **interação do jogador com ropes Aeronautics**; a rope e sua física continuam pertencendo ao provider hospedeiro.

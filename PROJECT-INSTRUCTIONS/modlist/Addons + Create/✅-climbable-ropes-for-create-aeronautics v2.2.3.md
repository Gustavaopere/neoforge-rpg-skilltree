# Climbable Ropes for Create Aeronautics — 2.2.3 (versão instalada declarada em 22/11/2026)

> **Estado vigente, sincronização documental em 22/11/2026:** versão 2.1.3 → **2.2.3**. JAR de distribuição identificado: `climbable_ropes-2.2.3.jar`. Instalação declarada pelo usuário; o JAR top-level local e seu metadata não foram relidos. [Publicação de referência](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes/files/9733043).
> **Delta auditado:** Releases 2.1.4, 2.2.0, 2.2.1 e 2.2.2 já auditadas: substituição de Player Animator por pose procedural e correções de escalada em cordas inclinadas. 2.2.3 atualiza Create Aeronautics para 1.3.3 e NeoForge de desenvolvimento para 21.1.247, e corrige cópias/cadáveres/estátuas de jogador imitando a pose de escalada.
> **Dependências/risco:** Create Aeronautics 1.3.3 consta como versão de build upstream em 2.2.3; não tratar como requisito mínimo do JAR sem manifest. Dependência aguardando sincronização documental em lote posterior. Player Animator deixou de ser requisito deste addon a partir de 2.2.0, mas pode atender outros mods. Verificar Sable/Aeronautics, multiplayer, rope grip, cadáveres/estátuas, poses e contraptions móveis; sem QA.
> **Limite das evidências:** documentos históricos abaixo descrevem o snapshot físico de setembro de 2026. Os estados antigos não substituem a versão declarada vigente; sem testes de runtime, multiplayer, saves ou compatibilidade. O detalhamento das versões intermediárias consta das seções históricas de auditoria abaixo.


> **Snapshot físico histórico — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#95**: JAR `climbable_ropes-2.1.3.jar`, mod id `climbable_ropes`, runtime `2.1.3`, SHA-1 `70ec457cb47e3772baf76e5e7c19872dbd4537f9`.

## Propriedades do registro

- **Mod:** Climbable Ropes for Create Aeronautics
- **Arquivo JAR instalado (declarado pelo usuário):** `climbable_ropes-2.2.3.jar` ([fonte](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes/files/9733043)).
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `2.2.3` (versão anterior `2.1.3`; novo metadata não extraído).
- **Categoria:** QoL; Tecnologia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** versão-alvo oficialmente identificada e cadeia intermediária documentada nas auditorias do dossier; ressalvas de changelog, artefato e QA descritas acima.
- **Estado no pack:** `2.2.3` declarado instalado pelo usuário em 22/11/2026; inventário GitHub sincronizado, sem inspeção local.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes
- **Função:** Addon de Create Aeronautics/Simulated que permite agarrar e escalar cordas verticais/plunger ropes com movimento, slide, jump-off e animação sincronizada de escalada.
- **Dependências:** Create Aeronautics 1.3.3 consta como versão de build upstream em 2.2.3; não tratar como requisito mínimo do JAR sem manifest. Dependência aguardando sincronização documental em lote posterior. Player Animator deixou de ser requisito deste addon a partir de 2.2.0, mas pode atender outros mods.
- **Compatibilidade/Riscos:** Verificar Sable/Aeronautics, multiplayer, rope grip, cadáveres/estátuas, poses e contraptions móveis; sem QA. Requisitos globais do pack pendentes de sincronização documental; sem QA.
- **Sobreposição:** Mecânica específica de escalada em ropes do stack Aeronautics. Pode cruzar Parkour/mobility/camera/player animation mods, mas não substitui genericamente sistemas de corda ou climb.
- **Observações vigentes:** JAR oficial `climbable_ropes-2.2.3.jar`; Verificar Sable/Aeronautics, multiplayer, rope grip, cadáveres/estátuas, poses e contraptions móveis; sem QA. Snapshot anterior é histórico.
- **Procedência:** posição #095 do snapshot físico histórico; declaração de instalação do usuário e publicação [upstream](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes/files/9733043). Nenhum hash/metadata novo local coletado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026 uma leitura anterior reconciliou provisoriamente o dossiê à 2.1.1; a modlist física mais recente de 08/09/2026 confirma `climbable_ropes-2.1.3.jar`. A conclusão válida sobre Player Animator permanece: a cópia embarcada via jar-in-jar não torna automaticamente redundante eventual cópia top-level exigida por outros consumers.
- **Atualização/Status:** 22/11/2026 — `2.1.3` → `2.2.3` por declaração do usuário. Releases 2.1.4, 2.2.0, 2.2.1 e 2.2.2 já auditadas: substituição de Player Animator por pose procedural e correções de escalada em cordas inclinadas. 2.2.3 atualiza Create Aeronautics para 1.3.3 e NeoForge de desenvolvimento para 21.1.247, e corrige cópias/cadáveres/estátuas de jogador imitando a pose de escalada.
- **Data da última decisão:** não definida

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

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Climbable Ropes for Create Aeronautics
- **Último candidato 1.21.1 / NeoForge localizado:** **2.1.3**
- **Estado:** **REVALIDADO — sem bump localizado**
- **Gate de promoção:** Manter baseline; testar rope climbing em moving sublevels, dismount/fall, multiplayer e chunk transitions se houver update futuro.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes

## Reauditoria de releases 2.1.3 → 2.2.0 — 23/10/2026

**Baseline instalada:** `climbable_ropes-2.1.3.jar` (conservar o nome físico registrado na tabela mestre, sem inferir mudança). O catálogo CurseForge do projeto **1528764** está temporalmente defasado e mostra 2.1.3 como principal; porém **o mesmo projeto oficial no Modrinth `jImqv1M5` e o source canônico do autor publicaram 2.1.4 (26/09/2026) e 2.2.0 (19/10/2026)** para **Minecraft 1.21.1 / NeoForge**. Último JAR publicado identificado: `climbable_ropes-2.2.0.jar`. **Não pressupor que a atualização já ocorreu no modpack.**

### 2.1.4 — 26/09/2026
- Mudanças de otimização gerais e atualização da configuração de desenvolvimento/compatibilidade para **Create Aeronautics 1.3.2** e **Sable 2.0.5**.
- Entre 2.1.3 e o release, o source refatorou a física de escalada de cordas normais e plunger ropes com helpers compartilhados (pacing, hover raycast, ride lifecycle e controllers). A refatoração não comprova nova mecânica visível, mas afeta a área a testar.
- Fonte primária (release Modrinth ID `fk5DMIYY`): https://modrinth.com/mod/create-aeronautics-climbable-rope/version/fk5DMIYY ; commits upstream: https://github.com/matejhozlar/Create-Aeronautics-Climbable-Ropes/commits/1.21.1 .

### 2.2.0 — 19/10/2026
- **Mudança principal:** substitui as animações de keyframes da escalada por um sistema de **IK (cinemática inversa) procedural**, ajustando poses à orientação real da corda; resulta de source commit `20468a967`.
- **Compatibilidade com cordas inclinadas e horizontais:** rig procedural permite rastejar/deslocar-se ao longo de cordas planas no lugar da pose pendurada padrão do Create (`cb5946b46`); acompanha direção local em segmentos curvados/sagging e não continua animação além das pontas (`54c5296df`).
- **Poses físicas:** trava pés na corda e permite balanço das pernas (`d5e42bbb1`); os pés apertam a corda e as pernas impulsionam o movimento (`3c73af2dc`).
- **Correções de render e transição:** cordas inclinadas não aparecem indevidamente em primeira pessoa (`e09f00270`); só aplica pose depois de determinar a direção da corda e mantém grip durante fade-out (`3baaf511b`); reinicializa supressão de pose em primeira pessoa a cada tick para evitar estado residual (`189a60709`).
- **Evidência inequívoca da publicação:** commit `49c63485c` `chore: release 2.2.0` e merge `586433256`, ambos 19/10/2026, na branch upstream oficial `1.21.1`. Source: https://github.com/matejhozlar/Create-Aeronautics-Climbable-Ropes/commits/1.21.1 ; catálogo oficial: https://modrinth.com/mod/create-aeronautics-climbable-rope/versions .

**Intervalo completo de versões lançadas após a instalada:** 2.1.4 → 2.2.0, sem release 2.1.5 publicada identificada. O README e código do projeto são a evidência dos detalhes de 2.2.0, não especulação a partir do nome do pacote.

**Impactos de QA se houver upgrade físico:** confirmar compatibilidade entre Sable e Create Aeronautics físicos, Player Animator, Epic Fight, EMF/Fresh Animations, cordas em contraptions movendo/rotacionando, plunger ropes, transições vertical/horizontal, multiplayer/pose observadores, câmera em primeira pessoa, detach/respawn/relog, chunk unload e performance de IK no cliente. **Auditoria documental concluída no nível source/release; nenhum JAR instalado ou runtime validado.** A classificação do dossier permanece **Addons + Create**, tal como as categorias temáticas publicadas no CurseForge.

## Auditoria incremental — 18/11/2026 — até 2.2.2

**Autoridade física histórica:** #095, `climbable_ropes-2.1.3.jar`, runtime 2.1.3. Categorias CurseForge revalidadas: Addons + Create. As versões 2.1.4 e 2.2.0 já foram auditadas acima. O [arquivo oficial 2.2.0](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes/files/9251386) também confirma uma mudança de dependência importante omitida na revisão anterior: **a partir de 2.2.0 o addon deixa de requerer Player Animator**, substituindo a animação pré-fabricada pela pose procedural de escalada. Isso não autoriza remover Player Animator top-level enquanto outros consumidores dependerem dele, nem altera a evidência de jar-in-jar da 2.1.3 instalada.

- **2.2.1 — 08/11/2026:** [CurseForge file 9511062](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes/files/9511062); [notas versionadas do autor](https://github.com/matejhozlar/Create-Aeronautics-Climbable-Ropes/commit/5bab04483e6ee87a187f8ffdf89ff968b86bec76) e [commit de release](https://github.com/matejhozlar/Create-Aeronautics-Climbable-Ropes/commit/87c8e710b8059cde4bfbd0235ec3ae27677ecc4f) confirmam atualização do Sable para 2.0.6 no ambiente de desenvolvimento/runtime. Não assumir exigência mínima do manifesto sem ler o JAR. A página individual do CurseForge não abriu integralmente; delta respaldado pelo changelog upstream fixado.
- **2.2.2 — 09/11/2026:** [CurseForge file 9529382](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes/files/9529382), NeoForge 1.21.1. Corrige deriva e inversão da pose sobre cordas quase horizontais ao girar a câmera, continuação indevida da descida depois de os pés atingirem a extremidade da corda e alcance da pegada em cordas inclinadas/jogadores escalados. [Release upstream](https://github.com/matejhozlar/Create-Aeronautics-Climbable-Ropes/commit/7e32599203d6b708d5b0fd9386cb118eceb0d216).

**Gates de promoção:** validar versões de Sable/Aeronautics efetivas, presença de Player Animator para outros consumidores, escalada com Pehkui, cordas planas, inclinadas e curvas, câmera girando, limite inferior, contraptions móveis, multiplayer e regressões de IK. Nenhum JAR instalado ou teste runtime executado. Dossiê e caminho preservam a versão histórica instalada; auditoria documental até 2.2.2 concluída.


## Atualização documental declarada — Climbable Ropes 2.2.3 (22/11/2026)

[CurseForge file 9733043](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-climbable-ropes/files/9733043), 21/11/2026, Release NeoForge 1.21.1, `climbable_ropes-2.2.3.jar`: atualização do ambiente de build para Create Aeronautics 1.3.3 e NeoForge 21.1.247; corrige entidades que imitam o jogador (cadáveres, estátuas e cópias desenhadas por outros mods) herdando erroneamente a pose de escalada. Não assumir que 1.3.3 seja requisito mínimo do manifest sem leitura do binário. As publicações intermediárias 2.1.4–2.2.2 permanecem auditadas nas seções anteriores. QA não executado.

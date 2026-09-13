# ParCool!

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8159a815c0e3c5450ed5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `ParCool-1.21.1-4.0.0.3.jar`, mod id `parcool`, runtime `4.0.0.3`, mixin `parcool.mixins.json`; Curios `9.5.1+1.21.1`, Epic Fight `21.17.3.1`, Sable `2.0.5` e Create Aeronautics `1.3.2` confirmados; Epic Parcool ausente na authority física
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, o stack acima está presente e Epic Parcool está ausente. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** ParCool!
- **Arquivo JAR:** `ParCool-1.21.1-4.0.0.3.jar`
- **Versão 1.21.1:** 4.0.0.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, RPG
- **Função:** Sistema de parkour/movimento avançado com ações como vault, wall movement, slides, agarrões e outras técnicas de mobilidade.
- **Dependências:** NeoForge 1.21.1; linha upstream 1.21.1-v4 usa Curios, presente fisicamente como curios-neoforge-9.5.1+1.21.1.jar. Epic Parcool NÃO está presente na modlist física atual.
- **Sobreposição:** ParCool é mobility/parkour; Epic Fight é combat system. Sem bridge física atual, qualquer integração de stamina/action precisa ser comprovada em runtime.
- **Compatibilidade/Riscos:** Riscos prioritários: issue upstream aberta com Sable/Create Aeronautics, coexistência direta com Epic Fight sem bridge dedicada, wall-run edge cases, input/pose e server movement authority. Não tratar reports upstream como bug local sem reprodução.
- **Observações:** Runtime 4.0.0.3. Pack físico contém Epic Fight 21.17.3.1, Sable 2.0.5 e Create Aeronautics 1.3.2, mas não contém Epic Parcool. Referência antiga à bridge foi removida.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + source/tracker oficial alRex-U/ParCool e documentação da linha 1.21.1-v4.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/parcool
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — ParCool 4.0.0.3 reconstruído: ações de parkour, input/movement authority, Epic Fight sem bridge, risco Sable/Aeronautics, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ParCool-1.21.1-4.0.0.3.jar`, mod id `parcool`, versão `4.0.0.3`, NeoForge 1.21.1. ParCool é um sistema de parkour/mobilidade avançada — não um sistema de combate. A modlist atual contém Curios, Epic Fight, Sable e Create Aeronautics, mas **não contém Epic Parcool**; referências antigas a essa bridge foram removidas desta ficha.

## 1. Identidade e papel
- **Mod:** ParCool!.
- **JAR físico:** `ParCool-1.21.1-4.0.0.3.jar`.
- **Mod id:** `parcool`.
- **Runtime:** `4.0.0.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** alRex-U / ParCool.
- **Licença:** LGPLv3.
- **Papel:** adicionar ações de parkour e traversal ao player, inspiradas em sistemas como Smart Moving.
- **Decisão:** Sem decisão.

## 2. Ações de mobilidade publicadas
A documentação upstream descreve um conjunto amplo de movimentos, incluindo exemplos como:
- grabbing/clinging em bordas;
- corrida mais rápida;
- roll;
- backflip;
- wall jump;
- cat leap;
- outras ações de parkour ligadas a contexto/input.

A ficha não inventa a lista integral nem keybinds/defaults sem config/runtime da 4.0.0.3.

## 3. Boundary: mobilidade, não combate
ParCool é authority do movimento/parkour que adiciona. Epic Fight continua authority do combat state, attacks, guard/dodge próprios e animação de combate.

Um salto, wall run ou roll de ParCool não deve ser usado como substituto automático de uma dodge/combat action do Epic Fight sem bridge explícita.

## 4. Dependências e modlist atual
A linha 1.21.1-v4 usa **Curios** no ecossistema upstream; o pack físico contém `curios-neoforge-9.5.1+1.21.1.jar`.

A modlist atual também contém:
- `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`;
- `sable-neoforge-1.21.1-2.0.5.jar`;
- `create-aeronautics-bundled-1.21.1-1.3.2.jar`.

**Epic Parcool não está presente na modlist física atual.** Portanto nenhuma integração Epic Fight↔ParCool é presumida por addon dedicado nesta instalação.

## 5. Input e action arbitration
Parkour adiciona novos gestos sobre movement input já usado por sprint, jump, sneak, combat e camera mods.

Testar:
- conflito de keybinds;
- prioridade quando duas ações são elegíveis;
- cancelamento ao entrar em GUI/mount;
- input durante battle mode;
- transição de primeira para terceira pessoa.

A ficha não atribui keybindings específicos sem ler a config local.

## 6. Wall movement
Wall movement/wall jump são superfícies centrais do mod e dependem de geometria, colisão e estado do player.

Há report upstream recente da linha 4.0.0.2 sobre **vertical Wall Run só ativando em paredes de até cerca de 5 blocos**. O pack está em 4.0.0.3, então isso é apenas regression pointer — não bug confirmado da build instalada.

Testar paredes baixas/altas, blocos completos, corners e superfícies modded.

## 7. Roll, backflip e cat leap
Essas ações alteram deslocamento e animation timing. Interseções relevantes:
- Epic Fight battle mode;
- player animation/render mods;
- fall damage/landing rules;
- stamina systems quando outro provider tenta cobrar recurso pela mesma ação.

Sem bridge física atual, não presumir que stamina do Epic Fight é consumida automaticamente pelo ParCool.

## 8. Sable / Create Aeronautics — risco upstream
O tracker upstream possui issue aberta de **incompatibilidade com Sable e Create Aeronautics**. Como ambos estão instalados no pack, isso é um regression gate prioritário.

A issue não prova que a combinação física atual reproduz o problema; registrar como risco significa testar:
- parkour dentro/sobre contraptions móveis;
- mudança de referencial/local coordinates;
- agarrar/wall actions em blocos de SubLevel;
- desmontar/embarcar sem velocity/position corrupta.

## 9. Epic Fight sem bridge dedicada
O pack contém Epic Fight, mas não Epic Parcool. Logo coexistência deve ser validada diretamente:
- battle mode + sprint/jump;
- roll/parkour durante attack recovery;
- armature/pose reset;
- knockback e airborne states;
- stamina não sendo cobrada/duplicada indevidamente.

Se surgir incompatibilidade, a solução não deve presumir uma bridge ausente.

## 10. Client/server e autoridade
Posição/movimento válido precisam convergir ao server state. O cliente pode antecipar animation/camera, mas não deve manter velocity/pose inválida após correção do servidor.

Dedicated server e multiplayer devem ser testados com:
- latência;
- repeated wall actions;
- dimension change;
- respawn;
- mounts/contraptions.

## 11. Persistência e configuração
Configurações de ações/keybinds podem ser client-facing, enquanto regras que afetam movimento precisam produzir resultado compatível com servidor.

Não atribuir comportamento ao default upstream sem conferir os arquivos físicos da instância. Após update, comparar configs para options renomeadas ou defaults alterados.

## 12. Riscos
1. **Sable/Aeronautics incompatibility:** issue upstream aberta e stack presente.
2. **Epic Fight arbitration:** sem bridge dedicada instalada.
3. **Wall-run edge cases:** geometria/altura podem expor regressões.
4. **Input conflicts:** várias actions usam sprint/jump/sneak/contexto.
5. **Animation pose:** roll/backflip/wall actions podem colidir com armatures/render mods.
6. **Movement authority:** lag pode causar rubber-banding se client/server divergem.
7. **Stamina ownership:** não cobrar Epic Fight stamina automaticamente sem integração comprovada.
8. **Config drift:** update pode alterar ação/defaults.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com ParCool 4.0.0.3 + Curios atual.
- [ ] Sprint/roll/backflip/wall jump/cat leap representativos funcionam sem input preso.
- [ ] Wall run é testado em paredes de alturas diferentes, inclusive acima de 5 blocos.
- [ ] Epic Fight battle mode coexistindo não causa pose/velocity travada.
- [ ] Stamina Epic Fight não é consumida/duplicada sem integração explícita.
- [ ] Sable SubLevel/contraption: wall action, jump, grab e landing preservam posição correta.
- [ ] Create Aeronautics ship em movimento não produz teleport/rubber-band grave.
- [ ] Mount/GUI/dimension/death cancelam ações de forma segura.
- [ ] Latência moderada não duplica movimento nem deixa state divergente.
- [ ] First/third person e player animation stack não ficam com pose stale.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `ParCool-1.21.1-4.0.0.3.jar`, mod id/runtime e `parcool.mixins.json`.
- Source oficial `alRex-U/ParCool`: parkour actions e linha 1.21.1-v4.
- Modlist atual: Curios, Epic Fight, Sable e Create Aeronautics presentes; **Epic Parcool ausente**.
- Tracker upstream: issue aberta Sable/Create Aeronautics e report próximo à build instalada sobre vertical wall run.
- **Limite:** reports de issue não foram promovidos a bugs locais sem reprodução; keybinds, fórmulas e lista integral de ações não foram inventados sem config/JAR auditados.

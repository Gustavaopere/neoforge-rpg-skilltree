# Lock-On Movement Fix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db8155a930f7b1637a8c53
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Lock-On Movement Fix
- **Arquivo JAR:** `lockonmovementfix-neoforge-1.0.2.jar`
- **Versão 1.21.1:** 1.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL
- **Função:** Correção client-facing para movimento/câmera/aim durante lock-on do Epic Fight, incluindo WASD/dodge relativos à câmera, target-facing, lunge e integrações opcionais.
- **Dependências:** Epic Fight é obrigatório. Runtime físico: Epic Fight 21.17.3.1; também presentes Better Lock On 2.0.8-neoforge, Bosses'Rise 2.1.2, FTB Teams 2101.1.11 e Iron's Spells 3.16.3, todos com superfícies de integração documentadas pelo mod. Controllable não foi localizado na modlist.
- **Sobreposição:** Complementa Epic Fight/Better Lock On; não substitui o combat engine nem decide dano/hit. Pode sobrepor outros camera/movement fixes e por isso exige coordenação do input stack.
- **Compatibilidade/Riscos:** Riscos: double-transform com outros camera fixes, target/team filtering divergente, aim/body desync, Epic Fight API drift, Better Lock On target-swap conflict e diferença entre lockado/deslockado. 1.0.2 corrige override de dodge para atuar somente em lock-on.
- **Observações:** Release 1.0.2 NeoForge 1.21.1 pinada. Movimento/aim visual não é authority de hit/dano. Config client documentada inclui lockOnRange 64 e filtros de player/team; defaults devem ser confirmados no arquivo efetivo do pack antes de tuning.
- **Procedência:** modlist.txt física atual + release GitHub oficial `neoforge-1.0.2`/asset exato + README oficial do projeto Seramicx/epic-fight-better-lockon-movement-camera-fix.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lock-on-movement-fix
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — release 1.0.2 pinada; camera-relative movement/dodge/aim, Better Lock On/Bosses'Rise/FTB Teams/Iron's interop, config, authority, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🎯 **ESCOPO CANÔNICO.** Runtime físico: `lockonmovementfix-neoforge-1.0.2.jar`, mod id `lockonmovementfix`, versão `1.0.2`. É uma correção de movimento/câmera/aim para o **lock-on do Epic Fight**, predominantemente client-facing; não é um segundo combat engine.

## 1. Identidade e source pin
A release oficial GitHub `neoforge-1.0.2`, target da linha NeoForge 1.21.1, publica exatamente o asset `lockonmovementfix-neoforge-1.0.2.jar`. O source/README oficial é a referência correspondente da build. Epic Fight para 1.21.1 é requisito funcional.

## 2. Papel no stack de combate
O mod redefine como input e câmera são interpretados enquanto existe um alvo travado. Epic Fight permanece authority de animações de combate, attacks, hit validation e demais mechanics. Better Lock On pode fornecer/alterar seleção de alvo; Lock-On Movement Fix adapta movimento/aim em torno desse state.

## 3. WASD relativo à câmera
Durante lock-on, o README documenta WASD relativo à câmera em primeira pessoa, terceira pessoa e SSR, removendo a tendência de puxar o jogador automaticamente em direção ao alvo. O corpo também gira em direção à câmera durante movimento. Isso é transformação de input/presentation, não prova de deslocamento aceito pelo servidor.

## 4. Dodge relativo à câmera
Dodges usam direção WASD relativa à câmera. A **1.0.2** corrige especificamente o override para atuar somente enquanto o jogador está lockado; quando não há lock-on, o caso é deixado para o comportamento normal/`ssr-camera-fixes` quando aplicável. Esse locked-vs-unlocked boundary é o principal regression gate da release instalada.

## 5. Attack lunge e facing
Ataques podem fazer lunge em direção ao alvo lockado, e o player pode virar para o alvo ao bloquear ou carregar spell. O target visual selecionado precisa convergir com o state usado pelo combat provider; hit/damage não deve ser calculado por câmera local ou por um frame visual.

## 6. Auto lock-on e target swap
O mod documenta auto lock-on ao iniciar swing, controlável por keybind, e troca de alvo por mouse flick; com Controllable, há também stick flick. O toggle de auto lock-on vem sem binding por padrão. Target swap deve escolher uma única entidade válida e não competir em loop com Better Lock On.

## 7. Better Lock On — integração concreta
O pack contém **Better Lock On 2.0.8-neoforge**. A integração é opcional segundo o README, mas no runtime deste pack a coexistência é concreta. Validar aquisição, retenção e troca de alvo, especialmente quando ambos os mods aplicam filtros/range ou target-cycling.

## 8. Bosses'Rise — integração concreta
O pack contém **Bosses'Rise 2.1.2**. O README documenta integração de direção de roll com Bosses' Rise. Roll/dodge deve usar uma única transformação direcional e não receber correção duplicada de câmera.

## 9. FTB Teams — filtro de aliados
O pack contém **FTB Teams 2101.1.11** e o mod documenta filtro de team allies. A config cliente expõe `filterTeamAllies`. Isso é conveniência de seleção: regras de friendly fire/dano reais continuam pertencendo ao servidor e aos providers relevantes. Um aliado filtrado da mira não deve ser considerado invulnerável por consequência.

## 10. Iron's Spells e aim de itens
O README inclui bows, crossbows, tridents, itens de uso e casts instantâneos de **Iron's Spells** no alinhamento com crosshair em terceira pessoa. O pack usa Iron's 3.16.3. Aim client-side deve orientar a solicitação, mas spell acceptance, mana, target/hit e damage continuam server-authoritative no Iron's.

## 11. Config client
A documentação lista `config/lockonmovementfix-client.toml` com defaults como `turnSpeed=0.45`, `idleTurnSpeed=0.7`, `autoFaceTarget=true`, `lockOnRange=64`, `filterPlayersFromAutoLockOn=true`, `flickSensitivity=8` e `filterTeamAllies=true`. Estes são defaults publicados; o arquivo efetivo do pack precisa ser lido antes de afirmar configuração ativa.

## 12. Client / server boundary
Câmera, leitura do mouse, keybinds e boa parte do movement transform são client-facing. O servidor/combat provider continua authority do movimento aceito, hit, damage, cooldown, spell cast e team rules. Divergência visual deve ser tratada como desync, nunca como state causal confiável.

## 13. Lifecycle e compatibilidade
Validar entrar/sair de lock-on, mudar perspectiva, morrer/relogar com target, trocar dimensão, target despawn/death, abrir GUI e reconfigurar keybind. Nenhum target reference ou camera transform deve permanecer preso depois de o lock-on terminar.

## 14. Riscos técnicos
1. **Double input transform** com outro camera/movement fix.
2. **Locked/unlocked leakage** do dodge override — fix alvo da 1.0.2.
3. **Target-selection conflict** com Better Lock On.
4. **Team-filter mismatch** entre seleção cliente e regras servidor.
5. **Aim/body desync** em terceira/primeira pessoa.
6. **Epic Fight API drift** após update do combat engine.
7. **Spell/projectile divergence** se crosshair visual não corresponder ao target server-side.
8. **Stale target/camera state** após death/despawn/relog.

## 15. Matriz de testes
- [ ] Lock-on em primeira pessoa mantém WASD relativo à câmera.
- [ ] Terceira pessoa/SSR mantém direção consistente.
- [ ] Dodge lockado usa direção corrigida; dodge deslockado não é sobrescrito indevidamente.
- [ ] Better Lock On troca/alterna targets sem ping-pong ou target fantasma.
- [ ] Bosses'Rise roll recebe uma única correção direcional.
- [ ] FTB Teams filtra aliados na seleção sem alterar indevidamente regra de dano.
- [ ] Bow/crossbow/trident alinham aim e resultado servidor.
- [ ] Cast instantâneo de Iron's 3.16.3 aponta para o resultado esperado e consome cast uma vez.
- [ ] Target morto/despawnado libera camera/facing imediatamente.
- [ ] Multiplayer mantém hit/damage coerentes com o servidor apesar da apresentação local.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
A ficha usa o JAR físico, a release GitHub oficial 1.0.2 e o README do projeto. Better Lock On, Bosses'Rise, FTB Teams, Epic Fight e Iron's foram cruzados com a modlist física. Controllable não foi localizado na lista física durante esta auditoria e, portanto, permanece apenas integração upstream opcional, não integração ativa afirmada.

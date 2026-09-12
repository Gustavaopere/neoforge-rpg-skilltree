# Sable Ragdolls Patch

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db815fa4abf8cd70b5224b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sable_player_ragdoll_patch-1.21.1-1.9.jar`, mod id `sable_player_ragdoll_patch`, runtime `1.9`, com cinco mixin configs; Sable Ragdolls 0.7.5, Sable 2.0.5, Punchy 2.7e, Curios 9.5.1 e `mob_ragdoll_corpse` 1.1.5 presentes; `ragdoll_corpse` 0.3.0 ausente como top-level
- **Data da exportação:** 2026-09-11

## Divergências documentais detectadas na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**.
- A publicação do Patch 1.9 exige `ragdoll_corpse 0.3.0`, mas esse provider não aparece como JAR/mod id top-level no snapshot físico acessível. O pack contém `mob_ragdoll_corpse-1.1.5.jar`, mod id `mob_ragdoll_corpse`, que permanece tratado como projeto distinto. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable Ragdolls Patch
- **Arquivo JAR:** `sable_player_ragdoll_patch-1.21.1-1.9.jar`
- **Versão 1.21.1:** 1.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL
- **Função:** Patch de correção/compatibilidade para Sable Ragdolls, cobrindo render/modelos, Curios, carrying, colisão, ações durante ragdoll e safety de comandos; não é um segundo sistema de ragdoll.
- **Dependências:** Publicado: Sable Player Ragdoll 0.7.5 + Sable: Ragdoll Corpse 0.3.0. Player Ragdoll 0.7.5 está presente; `ragdoll_corpse` 0.3.0 não aparece top-level na modlist atual. `mob_ragdoll_corpse` 1.1.5 está presente, mas não foi presumido equivalente. Punchy 2.7e e Curios 9.5.1 são integrações concretas.
- **Sobreposição:** Camada de patch apenas. Sable Ragdolls mantém sessions/physics state; Sable mantém sublevels; Curios/Punchy mantêm seus próprios domínios.
- **Compatibilidade/Riscos:** Riscos: published dependency mismatch de `ragdoll_corpse`, mixin/API drift, carrying interaction leakage, stale collision state, Punchy first-person regression, bypass de pearl/wind bomb, orphan state após `/sable remove @e` e conflitos de render com CPM/FA/Curios.
- **Observações:** Release 1.9 NeoForge 1.21.1. Delta exato: corrige Punchy first-person arms em ragdoll, bloqueia Ender Pearls/Wind Bombs nesse state e corrige `/sable remove @e` durante ragdoll. Cinco mixin configs físicos incluem base/punchy/leawind/corpse/ruok.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + JAR/mixin configs do Patch 1.9 + CurseForge oficial do projeto/file 1.9. Discrepância `ragdoll_corpse 0.3.0` registrada sem inferir equivalência com `mob_ragdoll_corpse`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-ragdoll-patch
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Sable Ragdolls Patch 1.9 reconstruído: requirements audit, render/Curios/carrying/collision fixes, Punchy/action/remove-command delta, lifecycle, discrepancy corpse, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-28

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sable_player_ragdoll_patch-1.21.1-1.9.jar`, mod id `sable_player_ragdoll_patch`, versão `1.9`, NeoForge 1.21.1. É uma camada de correção/compatibilidade para Sable Ragdolls, não um segundo sistema de ragdoll. A publicação oficial exige `sable-player-ragdoll 0.7.5`, presente no pack, e também `ragdoll_corpse 0.3.0`; este segundo provider **não aparece como JAR/mod id top-level na modlist física atual**. O pack possui `mob_ragdoll_corpse-1.1.5.jar` (`mob_ragdoll_corpse`), projeto distinto que não será tratado como equivalente sem evidência.

## 1. Identidade e papel
- **Mod:** Sable Ragdolls Patch.
- **JAR:** `sable_player_ragdoll_patch-1.21.1-1.9.jar`.
- **Mod id:** `sable_player_ragdoll_patch`.
- **Versão instalada:** `1.9`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente publicado:** Client & Server.
- **Papel:** correções de render, colisão, carrying/interações e estados inválidos ao redor de Sable Player Ragdoll / Ragdoll Corpse.

## 2. Requisitos publicados versus stack físico
A publicação oficial da 1.9 declara:
- Minecraft 1.21.1;
- NeoForge 21.1.x;
- **Sable Player Ragdoll 0.7.5**;
- **Sable: Ragdoll Corpse 0.3.0**.

O snapshot físico atual confirma Sable Player Ragdoll **0.7.5**, mas não contém um JAR top-level/mod id `ragdoll_corpse` 0.3.0.

Existe `mob_ragdoll_corpse-1.1.5.jar`, mod id `mob_ragdoll_corpse`, que é um projeto distinto. Sem metadata/source que declare equivalência, a auditoria mantém essa dependência publicada como **não reconciliada**. Isso não prova falha de runtime; boot/teste é obrigatório para determinar o efeito real.

## 3. Autoridade e ownership
- **Sable Ragdolls 0.7.5:** sessions, root/parts, physics-state e API principal do player ragdoll.
- **Sable 2.0.5:** sublevels, bodies e transforms físicos.
- **Sable: Ragdoll Corpse:** seria authority do conteúdo corpse específico quando presente.
- **Sable Ragdolls Patch:** somente corrige boundaries e estados incompatíveis; não deve criar segunda session, segundo body ou inventário paralelo.
- **Curios:** continua authority dos acessórios/slots.
- **Punchy:** continua authority de sua renderização/first-person arms.

## 4. Superfície de mixins física
O JAR físico declara cinco configs:
- `sable_player_ragdoll_patch.mixins.json`;
- `sable_player_ragdoll_patch.punchy.mixins.json`;
- `sable_player_ragdoll_patch.leawind.mixins.json`;
- `sable_player_ragdoll_patch.corpse.mixins.json`;
- `sable_player_ragdoll_patch.ruok.mixins.json`.

Isso confirma módulos de compat separados no binário. **Punchy** está fisicamente presente no pack. Os nomes `leawind` e `ruok` não foram mapeados a providers físicos nesta etapa e não terão alvos inventados por associação nominal.

## 5. FA+Player / modelo oversized
A descrição oficial registra fixes para braços/modelos oversized com os resource packs **FA+Player v1.1** e **FA+Player Expressions v1.2**.

Esses fixes pertencem à camada de render/modelo. A ficha não presume que esses dois packs específicos estejam ativos na instância apenas porque o patch possui suporte; o cenário deve ser testado quando o resource pack correspondente estiver habilitado.

## 6. Curios display
O patch corrige exibição de itens Curios em ragdolls. Ownership permanece no Curios/provider do item; o patch deve apenas renderizar/transportar a apresentação correta no state de ragdoll.

Regression gates: equip/unequip, death/ragdoll, relog e múltiplos slots sem duplication visual ou item funcional duplicado.

## 7. Carrying e inventário
A documentação oficial corrige duas classes de interação enquanto um ragdoll está sendo carregado:
- clicar com botão direito não deve abrir indevidamente o inventário do ragdoll;
- clicar com botão direito enquanto carrega não deve abrir chests/furnaces próximos por interceptação errada.

Ao mesmo tempo, a interação apropriada com o **torso** continua podendo acessar o inventário quando esse comportamento é intencional.

A diferença entre “carregar” e “interagir com torso” precisa permanecer determinística no servidor.

## 8. Colisão e `/nocollide`
O patch remove colisão de players com ragdolls e fornece `/nocollide` para alternar o comportamento documentado.

A mudança não deve alterar collision de outras entidades ou deixar hitboxes invisíveis após release/remove. Config/command state precisa convergir para todos os clientes.

## 9. Second skin layer, cape e swim pose
A publicação documenta correções para:
- preservação da **second skin layer** após morte;
- visibilidade de **cape**;
- prevenção de swim pose incorreta quando um player está abaixo da borda de um ragdoll.

Esses casos são majoritariamente visual/pose, mas devem ser testados contra state real de death/respawn e posição física do ragdoll.

## 10. Delta 1.9 — Punchy first-person arms
O changelog exato da 1.9 corrige braços de primeira pessoa do **Punchy** sendo exibidos enquanto o jogador está em ragdoll.

Punchy 2.7e está fisicamente presente no pack, então essa integração é concreta e regression gate direto.

## 11. Delta 1.9 — ações proibidas em ragdoll
A 1.9 passa a impedir uso de **Ender Pearls** e **Wind Bombs** durante ragdoll.

Esse bloqueio evita ações que podem mover/teleportar o player enquanto o state físico está delegado ao ragdoll. A validação deve ocorrer server-side; esconder animação/uso somente no cliente não é suficiente.

## 12. Delta 1.9 — `/sable remove @e`
A release corrige um caso em que executar `/sable remove @e` durante ragdoll podia fazer o jogador cair para fora do mundo e impedir reentrada adequada.

Esse é um lifecycle/safety gate explícito: remover entidades/sublevels via comando não pode deixar player preso a seat/root/body inexistente.

## 13. Client / Server
O patch toca tanto render quanto state/interação. Divisão esperada:
- **Servidor:** permissões de uso enquanto ragdolled, collision/state de carrying, remove command safety e lifecycle funcional.
- **Cliente:** modelo, arms, Curios display, skin/cape e pose.

O mesmo fix não deve ser aplicado duas vezes por prediction/client listener e server handler.

## 14. Lifecycle crítico
Validar:
- iniciar ragdoll;
- carregar/soltar ragdoll;
- abrir inventário via torso;
- tentativa de abrir container enquanto carrega;
- death/respawn;
- cape/skin refresh;
- Curios equip changes;
- Punchy first-person transition;
- uso de pearl/wind bomb;
- `/sable remove @e` durante state ativo;
- logout/reconnect;
- chunk/sublevel unload;
- removal do ragdoll por API/command.

## 15. Dependência `ragdoll_corpse` não reconciliada
A publicação oficial da 1.9 nomeia **Sable: Ragdoll Corpse 0.3.0** como requisito. Esse provider não está visível como top-level na autoridade física atual.

Possibilidades como dependência embarcada, metadata alternativa ou substituição por outro projeto **não serão presumidas**. A ação correta é:
- manter a discrepância explícita no catálogo;
- verificar boot/log/metadata do runtime quando houver teste executável;
- não renomear `mob_ragdoll_corpse 1.1.5` para satisfazer artificialmente o requisito.

## 16. Integrações concretas no pack
- **Sable Ragdolls 0.7.5:** requisito publicado presente.
- **Sable 2.0.5:** provider físico do ecossistema.
- **Punchy 2.7e:** integração 1.9 diretamente relevante.
- **Curios 9.5.1:** display de acessórios corrigido pelo patch.
- **Sable x CPM 0.3.2:** outra bridge de render do mesmo ragdoll; coexistência precisa ser testada.
- **Ragdoll Reactions 0.7.0:** addon que pode disparar ragdoll; patch não deve duplicar lifecycle.
- **mob_ragdoll_corpse 1.1.5:** projeto distinto presente; não foi tratado como substituto de `ragdoll_corpse 0.3.0`.

## 17. Riscos técnicos
1. **Published dependency mismatch:** `ragdoll_corpse 0.3.0` não aparece top-level.
2. **Mixin drift:** Player Ragdoll/Punchy/Curios/render APIs mudam.
3. **Carrying interaction leakage:** right-click ativa inventário/container errado.
4. **Collision state stale:** `/nocollide` ou ragdoll removal deixa hitbox inconsistente.
5. **Punchy first-person regression:** arms reaparecem durante ragdoll.
6. **Action bypass:** pearl/wind bomb executa no servidor apesar de UI bloqueada.
7. **Remove-command orphan:** `/sable remove @e` deixa seat/root/player state inválido.
8. **Render layering:** CPM/FA/resource packs/Curios disputam o mesmo renderer.
9. **Death/respawn stale visuals:** cape/second layer/pose permanecem incorretos.

## 18. Matriz de testes
- [ ] Dedicated server inicia com Patch 1.9 + Player Ragdoll 0.7.5 + Sable 2.0.5.
- [ ] Verificar em log/runtime se a ausência top-level de `ragdoll_corpse 0.3.0` bloqueia ou não o carregamento; não assumir resultado documentalmente.
- [ ] Se o provider corpse oficial for resolvido em runtime, executar também os testes corpse-specific do patch.
- [ ] Curios items permanecem visíveis/corretos sem duplicação funcional.
- [ ] Carregar ragdoll + right-click não abre inventário indevido.
- [ ] Carregar ragdoll próximo a chest/furnace não abre container indevidamente.
- [ ] Interação intencional com torso continua acessando inventário quando suportada.
- [ ] Players não colidem com ragdoll conforme comportamento/default e `/nocollide` alterna state corretamente.
- [ ] Second skin layer e cape persistem corretamente após death/ragdoll.
- [ ] Player abaixo da borda do ragdoll não entra em swim pose incorreta.
- [ ] Punchy 2.7e não mostra first-person arms durante ragdoll.
- [ ] Ender Pearl e Wind Bomb são bloqueados funcionalmente no servidor enquanto ragdolled.
- [ ] `/sable remove @e` durante ragdoll não lança o player ao void nem impede reentrada/reconexão.
- [ ] Sable x CPM 0.3.2 coexiste sem renderer/mixin conflict.
- [ ] FA+Player fixes são testados somente quando os packs correspondentes estiverem ativos.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências e limites
- Modlist física canônica de 10/09/2026: Patch 1.9, Player Ragdoll 0.7.5, Sable 2.0.5, Punchy 2.7e, Curios e integrations próximas.
- JAR físico: cinco mixin configs, incluindo módulo Punchy e módulo corpse.
- CurseForge oficial 1.9: Release NeoForge 1.21.1, requisitos publicados e changelog de Punchy/pearl/wind bomb/`/sable remove @e`.
- Descrição oficial do projeto: FA+Player fixes, Curios display, carrying, collisions, skin/cape e swim pose.
- **Limite crítico:** `ragdoll_corpse 0.3.0` não foi encontrado como top-level na modlist; `mob_ragdoll_corpse 1.1.5` não foi presumido equivalente. Compat corpse permanece pendente de verificação de runtime/metadata, sem impedir a catalogação factual do patch instalado.

# Ragdoll Reactions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db81d19fc5e0dddc9689e7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `ragdoll_reactions-1.21.1-0.7.0.jar`, mod id `ragdoll_reactions`, runtime `0.7.0`; stack Sable/Ragdolls presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Ragdoll Reactions 0.7.0 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Ragdoll Reactions
- **Arquivo JAR:** `ragdoll_reactions-1.21.1-0.7.0.jar`
- **Versão 1.21.1:** 0.7.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Compat
- **Função:** Faz o jogador entrar em estado de ragdoll em resposta a eventos físicos do mundo, como colisões, atropelamentos, mudanças bruscas de direção e explosões.
- **Dependências:** NeoForge 21.1.219+; Sable; Sable: Ragdolls / Sable Player Ragdoll. Pack usa NeoForge 21.1.248 e possui as bases exigidas.
- **Sobreposição:** Não implementa o ragdoll-base; apenas dispara reações sobre Sable Ragdolls. Deve permanecer separado de corpse/death/revive systems.
- **Compatibilidade/Riscos:** Trigger layer sobre Sable Ragdolls. Riscos: retrigger loop, sensitivity excessiva com ParCool/Epic Fight, relative velocity em SubLevels, launch impulse extremo, mob experimental e death/corpse desync.
- **Observações:** Release 0.7.0 NeoForge 1.21.1 de 19/06/2026. Changelog exato: suporte a mobs adicionado como experimental.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth oficiais Ragdoll Reactions 0.7.0 + stack Sable/Ragdolls já auditado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ragdoll-reactions
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Ragdoll Reactions 0.7.0 reconstruído: crash/run-over/sharp-turn/explosion triggers, server config, cooldown/launch clamp, mobs experimentais, Sable authority, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ragdoll_reactions-1.21.1-0.7.0.jar`, mod id `ragdoll_reactions`, versão `0.7.0`, NeoForge 1.21.1. Ragdoll Reactions é uma **camada de gatilhos físicos**: reage a colisões, atropelamentos, mudanças bruscas de direção, explosões e outros eventos para acionar o sistema de ragdoll existente. Não implementa o ragdoll-base; depende de Sable + Sable: Ragdolls.

## 1. Identidade e papel
- **Mod:** Ragdoll Reactions.
- **JAR:** `ragdoll_reactions-1.21.1-0.7.0.jar`.
- **Mod id:** `ragdoll_reactions`.
- **Runtime:** `0.7.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Licença:** All Rights Reserved.
- **Papel:** detectar eventos físicos e solicitar ragdoll ao sistema Sable correspondente.
- **Decisão:** Sem decisão.

## 2. Dependências e authority
A publicação exige:
- Minecraft 1.21.1;
- NeoForge **21.1.219+**;
- Sable;
- Sable: Ragdolls / Sable Player Ragdoll.

O pack usa NeoForge 21.1.248 e possui o stack Sable/Ragdolls, portanto o requisito de loader é satisfeito.

Ownership:
- Sable → física/SubLevels relevantes;
- Sable Ragdolls → implementação/state do ragdoll;
- Ragdoll Reactions → decisão de quando dispará-lo.

## 3. Crashes e colisões
Um trigger documentado é colisão/crash físico. Isso deve ser calculado com dados server-authoritative quando afeta state de ragdoll.

Testar colisões frontais/laterais, baixas e altas velocidades e transições entre world e contraption para evitar false positives.

## 4. Ser atropelado
O projeto inclui reação a ser atingido/atropelado. Num pack com vehicles/contraptions, distinguir colisão válida de simples overlap visual.

Risco: hitbox/velocity de Sable produzir múltiplos triggers no mesmo impacto.

## 5. Mudanças bruscas de direção
Sharp direction changes também podem disparar ragdoll conforme sensibilidade/config. Isso interage com ParCool, knockback, dash, Epic Fight movement e contraptions acelerando/rotacionando.

Não presumir que qualquer dash cause ragdoll; o threshold real vem da config/runtime.

## 6. Explosões
Explosões são trigger publicado. O evento deve acionar ragdoll de forma coerente com força/distância e não duplicar state quando outro mod também reage ao mesmo dano/explosion event.

## 7. Configuração server-side
A documentação publica opções como:
- master switch/ativação geral;
- sensibilidade;
- clamp/limite de launch speed;
- cooldown de retrigger;
- se players em creative são afetados.

Esses valores são balanceamento funcional. A auditoria não leu o arquivo local e não inventa defaults efetivos do pack.

## 8. Cooldown de retrigger
Cooldown é crítico para impedir loop de ragdoll: enquanto um player já está caindo/colidindo, múltiplos impactos/ticks não devem reiniciar o state indefinidamente.

Testar reimpacto durante cooldown e imediatamente após recovery.

## 9. Launch-speed clamp
O limite de velocidade de lançamento existe para evitar impulses absurdos. Em Sable, velocidades relativas podem ser grandes; validar world velocity versus ship/SubLevel transform para impedir lançamento explosivo incorreto.

## 10. Creative-mode boundary
A opção de afetar creative deve ser respeitada server-side. Admin/tester em creative não deve entrar em ragdoll se a config desabilitar isso.

## 11. Release 0.7.0
A build 0.7.0 é Release NeoForge 1.21.1 publicada em 19/06/2026.

O changelog exato registra: **“Mobs added (experimental)!”**.

Como o suporte a mobs é explicitamente experimental, tratá-lo como superfície de teste, não como garantia de cobertura universal de entidades modded.

## 12. Mobs experimentais
Testar apenas entidades representativas e observar:
- mob entra em ragdoll sem perder AI/state permanentemente;
- death/despawn não duplica corpo;
- mount/passenger relations não quebram;
- mob modded com animação própria não fica invisível/preso.

Não declarar suporte a todos os mobs do pack sem matriz real.

## 13. Relação com o restante do stack ragdoll
O pack também contém Sable Ragdolls, patches e corpse/ragdoll integrations. Ragdoll Reactions não deve duplicar corpse creation nem revival logic.

Em death lifecycle, separar:
- reação temporária à física;
- ragdoll de player vivo;
- corpse/death ragdoll;
- revive systems.

## 14. Epic Fight, ParCool e movement systems
Movimentos bruscos podem ser legítimos por combate/parkour. Testar:
- dodge/dash;
- knockback;
- wall movement;
- jump/fall;
- battle-mode attacks;
- entrar/sair de contraption.

Config de sensibilidade deve evitar transformar movimento normal em ragdoll constante.

## 15. Client/server e sync
Como o state muda movimento/render e pode interagir com physics, servidor deve ser authority. Clientes observadores precisam ver início/recovery coerentes.

Reconnect durante ragdoll não pode deixar pose/hitbox stale.

## 16. Lifecycle
Cobrir trigger → ragdoll → recovery, death durante ragdoll, dimension change, disconnect/reconnect, chunk/ship unload, mount/dismount e server restart.

## 17. Riscos
1. **Retrigger loop** por múltiplos impacts/ticks.
2. **Sensitivity excessiva** com parkour/combat movement.
3. **Relative velocity** incorreta em Sable/SubLevels.
4. **Launch impulse extremo** sem clamp efetivo.
5. **Mob experimental** causando AI/render state stale.
6. **Death/corpse duplication** com outros ragdoll mods.
7. **Client/server desync** de pose/hitbox.
8. **Creative-rule mismatch**.

## 18. Matriz de testes
- [ ] Dedicated server/cliente iniciam com 0.7.0 + Sable/Ragdolls.
- [ ] Crash físico acima do threshold aciona ragdoll uma vez.
- [ ] Baixa velocidade não dispara indevidamente.
- [ ] Atropelamento em contraption gera reação coerente.
- [ ] Sharp turn de ParCool/Epic Fight é testado contra sensibilidade real.
- [ ] Explosão aciona ragdoll sem duplicate trigger.
- [ ] Cooldown impede loop de retrigger.
- [ ] Launch-speed clamp limita impulse extremo.
- [ ] Creative respeita config.
- [ ] Mob experimental representativo entra/sai do ragdoll sem state stale.
- [ ] Death/reconnect/dimension change limpam state temporário.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências e limites
- Modlist física: JAR, mod id/runtime e hash exatos; Sable stack presente.
- Publicação oficial: Release 0.7.0 NeoForge 1.21.1, Client & Server, requer NeoForge 21.1.219+, Sable e Sable: Ragdolls.
- Descrição oficial: crashes, run-over, sharp direction changes, explosions e server config de sensitivity/launch/cooldown/creative.
- Changelog 0.7.0: mobs experimentais.
- **Limite:** valores da config local e comportamento de cada mob/vehicle do pack não foram medidos; decisão permanece Sem decisão.

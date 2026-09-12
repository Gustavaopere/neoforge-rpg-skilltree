# Clockwork

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db81cf8abeec349eef0170
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Clockwork
- **Arquivo JAR:** `clockwork-neoforge-1.21.1-1.1.4.jar`
- **Versão 1.21.1:** 1.1.4
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Exploração
- **Função:** Adiciona ferramentas, armas e utilidades mecânicas independentes: Clockwork Dragonfly mount, crossbows scoped/automatic, Potion Sprayer, seeking arrow, Clockwork Wings, Flamethrower e Clockwork Drill.
- **Dependências:** NeoForge 1.21.1. Nenhuma dependência obrigatória externa foi afirmada para a build física 1.1.4 com base na publicação auditada.
- **Sobreposição:** Sobreposição funcional parcial com mounts, ranged weapons, elytra variants e mining companions do pack; não é uma bridge do Create nem substitui Create Aeronautics.
- **Compatibilidade/Riscos:** Mod independente de equipamentos/mount, não o antigo projeto Create: Clockwork. Riscos: mount flight/collision, automatic crossbow fire rate, seeking arrow targeting, potion duration economy, flamethrower grief/fire e drill inventory/automation. 1.1.4 corrige arm pose e amplia Dragonfly spawn em outposts.
- **Observações:** JAR físico `clockwork-neoforge-1.21.1-1.1.4.jar`, mod id `clockwork`, runtime 1.1.4. Não confundir com o antigo mod de física/Create de nome semelhante.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Clockwork 1.1.4 e changelog exato já auditado. Reconciliação final: JAR/runtime permanecem exatamente `clockwork-neoforge-1.21.1-1.1.4.jar` / `1.1.4`; decisão `Manter` permanece inalterada e o mod continua distinguido do antigo projeto Create: Clockwork.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/clockwork
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #96: `clockwork-neoforge-1.21.1-1.1.4.jar` / `1.1.4` conferidos contra a modlist atual; Dragonfly, crossbows, Potion Sprayer, Wings, Flamethrower, Drill, configs/lifecycle e decisão `Manter` preservados.
- **Histórico da decisão:** 2026-09-06 — decisão formal registrada: Manter Clockwork. Em 09/09/2026, a build física 1.1.4 foi revalidada no QC global #96; a decisão foi preservada e runtime QA permanece pendente.
- **Data da última decisão:** 2026-09-06

> ⚙️ **ESCOPO CANÔNICO.** Runtime físico: `clockwork-neoforge-1.21.1-1.1.4.jar`, mod id `clockwork`, versão `1.1.4`. Este Clockwork é um mod independente de ferramentas/armas/mounts mecânicos. **Não é o antigo projeto de física Create: Clockwork.**

## 1. Clockwork Dragonfly
Broken Clockwork Dragonfly pode ser encontrado em pillager outposts, reparado com Clockwork Gears e posteriormente domado/montado. Funciona como mount de locomoção terrestre e aérea.
A 1.1.4 passa a gerar Dragonflies em allay cages de outposts e aumenta o spawn weight documentado de 30 para 50.

## 2. Crossbows
Há duas variantes principais:
- **Scoped Crossbow:** projéteis mais rápidos/longos e scope tipo spyglass;
- **Automatic Crossbow:** barrel com reload automático aproximadamente duas vezes mais rápido.
A 1.1.4 adiciona som de spyglass ao zoom e corrige pose dos braços ao mirar.

## 3. Potion Sprayer e Clockwork Arrow
Potion Sprayer usa potions como munição e aplica droplets consumindo apenas fração da duração da poção. Clockwork Arrow ajusta trajetória em direção ao mob mais próximo da mira.
São superfícies de combate server-authoritative: custo, target e efeito não devem ser inferidos apenas por partículas/animação.

## 4. Wings e Flamethrower
Clockwork Wings são variante de elytra com menor gliding e flap boost. Flamethrower consome Blaze Powder e pode incendiar entidades/blocos; 1.1.4 corrige ignição lateral de blocos.
Grief/fire-spread e interação com proteção de área precisam ser testados no servidor real.

## 5. Clockwork Drill
Drill é companion de mineração toggleable com inventário próprio, reparável com gears. A 1.1.4 adiciona duration bar e permite alterar a configuração de blocos até quebrar sem exigir restart.
Pontos críticos: ownership do inventário, drops exactly-once, unload do companion e proteção de blocos/claims.

## 6. Authority e integração
Clockwork é authority de suas próprias entities/items/configs. Epic Fight ou outros combat frameworks podem alterar animação/uso de armas sem assumir ownership dos efeitos. Sistemas de voo/mount externos continuam providers separados.

## 7. Lifecycle e multiplayer
Validar tame/owner, mount/dismount, death/despawn, dimension change, chunk unload, restart, drill inventory, crossbow reload e dois jogadores interagindo com o mesmo companion/target.

## 8. Riscos
1. Dragonfly duplicar/desaparecer em unload ou portal.
2. Automatic Crossbow gerar cadência acima do balanceamento esperado.
3. Seeking Arrow selecionar alvo incorreto/PvP aliado.
4. Potion Sprayer multiplicar duração/efeito por droplets.
5. Flamethrower contornar proteção/grief rules.
6. Drill perder/duplicar drops ou inventário.

## 9. Boundary para quests/perks
Créditos devem usar ações causais server-side: tame confirmado, kill credit real, mineração concluída pelo provider etc. Não premiar animação, projectile spawn isolado ou tick de mount.

## 10. Matriz de testes
- [ ] Dedicated server inicia com Clockwork 1.1.4.
- [ ] Dragonfly gera/repara/doma e persiste após unload/restart.
- [ ] Voo/montaria não duplica player/entity state.
- [ ] Scoped/Automatic Crossbows gastam munição exatamente uma vez.
- [ ] Potion Sprayer conserva consumo/efeito esperado.
- [ ] Flamethrower respeita regras server-side de fogo/proteção.
- [ ] Drill conserva drops/inventory após unload/restart.
- [ ] Epic Fight/animation stack não deixa pose presa após uso.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limitação
- Modlist física: `clockwork-neoforge-1.21.1-1.1.4.jar`.
- CurseForge oficial: conteúdo do mod e changelog 1.1.4 acima.
- IDs internos, atributos exatos e config keys não foram extraídos do JAR nesta etapa; integrações programáticas devem consultar resources/API reais.

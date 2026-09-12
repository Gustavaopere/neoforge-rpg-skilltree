# Soul Fire'd

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8186bb34fb35ce91913e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `soul-fire-d-neoforge-1.21-6.1.0.jar`, mod id `soul_fire_d`, runtime `6.1.0`; Cobweb 1.4.0 e Prometheus 1.2.5 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Soul Fire'd 6.1.0, Cobweb 1.4.0 e Prometheus 1.2.5 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Soul Fire'd
- **Arquivo JAR:** `soul-fire-d-neoforge-1.21-6.1.0.jar`
- **Versão 1.21.1:** 6.1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG
- **Função:** Expands soul-fire into a distinct gameplay fire type with blue burning overlay, 2 damage/second, soul-fire projectile/entity interactions, Soul Fire Aspect/Soul Flame enchantments, datapack configuration and Prometheus-backed custom-fire API.
- **Dependências:** Required and physically present: Cobweb 1.4.0 and Prometheus 1.2.5. NeoForge 1.21/1.21.1. Optional integrations are only active when their providers are present.
- **Sobreposição:** Touches fire/soul-fire mechanics only. Potential overlap with other custom-fire/burning mods must be evaluated by fire-type IDs, damage hooks and enchantment behavior, not theme.
- **Compatibilidade/Riscos:** Gameplay fire-state overhaul. Risks: double damage/tick, wrong fire-type persistence, enchantment stacking, projectile ignition duplication, datapack config drift, custom-fire API conflicts and mob/fire-immunity interactions. Since 6.x, Prometheus owns shared custom-fire API infrastructure.
- **Observações:** JAR físico `soul-fire-d-neoforge-1.21-6.1.0.jar`, runtime 6.1.0. Old metadata saying no central dependency was corrected: official current line requires Cobweb + Prometheus, both present. Since v6.0.0 API moved to Prometheus.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge official Soul Fire'd 6.1.0/current project page + physical Cobweb/Prometheus pages in current catalog.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/soul-fire-d/files/7260057
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Soul Fire'd 6.1.0 rebuilt: soul-fire burn state/overlay, 2 dmg/s, arrow/zombie interactions, Soul Fire Aspect/Soul Flame, datapack config, Prometheus fire API, dependencies, lifecycle, risks and tests.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `soul-fire-d-neoforge-1.21-6.1.0.jar`, mod id `soul_fire_d`, versão `6.1.0`, NeoForge 1.21.1. Soul Fire'd transforma **soul fire em um fire type funcional distinto**, com dano, overlay, enchantments, projectile interactions e configuração própria.

## 1. Identidade e dependências
- **Mod:** Soul Fire'd.
- **JAR:** `soul-fire-d-neoforge-1.21-6.1.0.jar`.
- **Mod id:** `soul_fire_d`.
- **Versão:** `6.1.0`.
- **Loader/jogo:** NeoForge 1.21/1.21.1.
- **Ambiente:** Client & Server.
- **Required:** Cobweb `1.4.0` e Prometheus `1.2.5`, ambos presentes.

## 2. Fire-type ownership
Minecraft continua owner do entity/world state geral. Soul Fire'd define comportamento específico para soul fire e usa infraestrutura Prometheus na linha 6.x para custom fire types.

Outro mod não deve tratar soul fire como vanilla fire comum se o hook/API do custom fire type estiver disponível.

## 3. Soul-fire burn state
Entidades que entram em soul fire passam a exibir visual de **chamas azuis** e permanecem em um estado de burning distinto conforme as regras do mod.

O servidor deve ser authority do fire type e duração; cliente apenas representa overlay/particles.

## 4. Dano publicado
A documentação oficial define **2 de dano por segundo** para entidades queimando em soul fire.

Esse dano precisa ser liquidado na cadência prevista exatamente uma vez. Não pode somar simultaneamente o tick de fire vanilla e outro tick duplicado do mesmo soul-fire state sem regra explícita.

## 5. Overlay visual
Soul Fire'd adiciona overlay de soul fire para o player/entidades afetadas. A aparência azul deve acompanhar o state real e desaparecer quando o burning termina.

Desync visual não pode prolongar dano server-side nem indicar fire type errado.

## 6. Zombies e burning semantics
O projeto torna as regras de soul fire mais consistentes, incluindo zombies queimando sob soul fire conforme comportamento publicado.

Mob immunity, undead rules e custom AI de terceiros precisam ser validados por provider; o addon não deve ignorar imunidade explícita de outro mod sem regra própria.

## 7. Arrows through soul fire
Arrows/projéteis que atravessam soul fire podem **incendiar alvos com soul fire**.

O projectile deve carregar/receber o fire type correto e aplicar o estado uma única vez no hit. Collision + projectile-hit event não podem duplicar duração/dano.

## 8. Soul Fire Aspect
O enchantment **Soul Fire Aspect** é análogo tematicamente a Fire Aspect, mas aplica soul fire e dano/interação correspondentes.

Attack event deve escolher o fire type correto sem empilhar Fire Aspect vanilla + Soul Fire Aspect de forma não prevista.

## 9. Soul Flame
**Soul Flame** desempenha papel equivalente à família Flame para projectiles, aplicando soul fire em hits conforme regra do mod.

Bow/projectile mods podem substituir projectile classes; compatibilidade precisa ser testada no entity/projectile real, não apenas na enchant table.

## 10. Configuração por datapack
Na linha 1.21, funcionalidades são individualmente configuráveis e a configuração migrou para **datapacks**.

Data reload pode mudar quais regras/enchantments/interactions estão habilitados. State já ativo deve convergir sem conservar regra removida indefinidamente.

## 11. Prometheus API — linha 6.x
Desde **v6.0.0**, a API de custom fire types foi movida para **Prometheus**. Soul Fire'd consome essa infraestrutura em vez de possuir toda a API isoladamente.

Isso corrige a metadata antiga da ficha: Prometheus é dependência central real da linha instalada.

## 12. Cobweb dependency
**Cobweb** também é required dependency oficial e está presente no pack.

Como library/helper, sua remoção não deve ser considerada separadamente enquanto Soul Fire'd e outros consumers que a requerem permanecerem instalados.

## 13. Custom-fire extensibility
A API Prometheus/Soul Fire'd permite integrações/developers definirem custom fire behavior. Isso amplia o risco de collisions entre fire-type IDs/tags/datapacks.

Um custom fire de outro mod não deve ser convertido para soul fire apenas porque compartilha cor/modelo semelhante.

## 14. Integrações upstream
O projeto publica integrações com mods como Tetra/Tetracelium, Decorative Blocks, Alex's Mobs Interaction, Amendments e Infernal Expansion em linhas suportadas.

Somente providers fisicamente presentes devem ser considerados paths ativos; esta ficha não marca integration runtime sem confirmação da modlist.

## 15. Entity immunities e modded mobs
Mobs/bosses podem possuir fire immunity, custom damage handlers ou phases. Soul-fire application precisa respeitar os hooks/semântica suportados pelo provider.

Cataclysm, Ice and Fire, Goety e outros content mods no pack são regression surfaces de dano/fire, não integrações automáticas atribuídas ao addon.

## 16. Client / server
Servidor decide:
- fire type ativo;
- duração;
- damage ticks;
- projectile/enchantment application;
- datapack rules.

Cliente renderiza overlay/particles/tooltips. Um client sem visual correto não pode alterar dano real.

## 17. Persistence e lifecycle
Validar:
- entity entra/sai de soul fire;
- fire extinguish por água/rain/effect conforme regras aplicáveis;
- projectile ignition;
- death/remove;
- dimension transfer;
- chunk unload/reload;
- save/restart com entity burning;
- datapack reload.

State órfão não deve reaparecer após reload sem causa.

## 18. Multiplayer
Todos os clientes devem observar o fire type que o servidor atribuiu. Damage ticks precisam ser únicos mesmo com múltiplos observadores/packets.

Enchant application pertence ao atacante/projectile correto; dois players não podem compartilhar duração/source por state global.

## 19. Riscos técnicos
1. **Double damage tick:** soul + vanilla fire liquidam o mesmo segundo duas vezes.
2. **Wrong fire type:** entity fica em fire vanilla em vez de soul fire ou vice-versa.
3. **Projectile double application:** pass-through + hit hooks duplicam state.
4. **Enchant stacking:** Soul Fire Aspect/Soul Flame acumulam indevidamente com vanilla equivalents.
5. **Datapack drift:** config removida continua cacheada.
6. **Prometheus API drift:** custom-fire contract muda.
7. **Mob immunity conflict:** provider modded e soul-fire hook divergem.
8. **Persistence stale:** entity salva com fire state inválido.
9. **Visual desync:** overlay azul persiste sem state funcional.

## 20. Matriz de testes
- [ ] Dedicated server inicia com Soul Fire'd 6.1.0 + Cobweb 1.4.0 + Prometheus 1.2.5.
- [ ] Entity em soul fire recebe overlay/state azul correto.
- [ ] Dano é 2 por segundo conforme regra publicada, sem double tick.
- [ ] Extinguish encerra state/dano corretamente.
- [ ] Arrow atravessando soul fire aplica soul fire ao target uma vez.
- [ ] Soul Fire Aspect aplica fire type correto.
- [ ] Soul Flame aplica soul fire em projectile hit.
- [ ] Combinação com Fire Aspect/Flame não cria stacking indevido.
- [ ] Datapack disable/reload altera feature conforme esperado sem cache stale.
- [ ] Mob fire-immune/modded não recebe state inválido.
- [ ] Save/restart/chunk reload preserva ou encerra burning segundo lifecycle correto.
- [ ] Multiplayer mostra overlay consistente e dano server-authoritative.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 21. Evidências e limites
- Modlist física atual: Soul Fire'd 6.1.0, Cobweb 1.4.0 e Prometheus 1.2.5.
- CurseForge oficial: soul-fire overlay, 2 damage/s, zombie/projectile behavior, Soul Fire Aspect/Soul Flame, datapack configuration e required deps.
- Página oficial: desde v6.0.0 a custom-fire API migrou para Prometheus.
- **Limite:** datapacks locais e cada integração opcional não foram inventariados neste lote; nenhum runtime test foi executado.

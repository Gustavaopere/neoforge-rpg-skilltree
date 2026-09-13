# YDM's Weapon Master

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c6992ad536381f8b59
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `weaponmaster_ydm-1.21.1-neoforge-4.2.7.jar`, mod id `weaponmaster_ydm`, runtime `4.2.7`; Epic Fight `21.17.3.1` também confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026” e contém revalidação física de 11/09. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, YDM 4.2.7 e Epic Fight 21.17.3.1 estão presentes. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** YDM's Weapon Master
- **Arquivo JAR:** `weaponmaster_ydm-1.21.1-neoforge-4.2.7.jar`
- **Versão 1.21.1:** 4.2.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, RPG, QoL
- **Função:** Renderiza itens do hotbar/equipamentos no corpo do jogador, com posições configuráveis e tratamento de shield/banner, sem alterar inventário ou comportamento da arma.
- **Dependências:** NeoForge 1.21.1; integração visual com o player renderer. Epic Fight 21.17.3.1 permanece authority do combate.
- **Sobreposição:** Sobreposição somente de apresentação com outros renderers/cosméticos; não é sistema de combate, inventário ou equipamento paralelo.
- **Compatibilidade/Riscos:** Riscos de layer conflict, clipping com Epic Fight/Fresh Moves/First-person Model, stale visual state e classloading em dedicated server. A 4.2.7 inclui fix explícito de server crash. Não usar posição visual como gameplay state.
- **Observações:** Mod id `weaponmaster_ydm`, runtime 4.2.7. É apresentação cosmética de hotbar; item real/inventário/weapon category continuam authorities dos providers originais.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial YDM's Weapon Master 4.2.7 NeoForge 1.21.1, cujo changelog exato registra `server crash fix` + Epic Fight 21.17.3.1 físico. Nenhum render/dedicated-server runtime test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ydms-weapon-master
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — YDM's Weapon Master 4.2.7 permanece exatamente instalado e continua a release NeoForge 1.21.1 pertinente; hotbar/body renderer, inventory boundary, Epic Fight coexistence e server-crash fix preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `weaponmaster_ydm-1.21.1-neoforge-4.2.7.jar`, mod id `weaponmaster_ydm`, versão `4.2.7`. YDM's Weapon Master é uma camada **visual de equipamentos visíveis no corpo do jogador**. Não altera a authority de inventário, dano, weapon category ou moveset.

## 1. Função confirmada
O projeto oficial define o comportamento central: itens do hotbar podem ser exibidos no corpo do jogador, com posições dedicadas/configuráveis e tratamento próprio para itens como shield/banner.

A release exata 4.2.7 para NeoForge 1.21.1 existe oficialmente; o changelog desse arquivo registra **server crash fix**.

## 2. Authority e ownership
O item real continua no inventário/hotbar. YDM mantém somente a representação visual associada ao slot/item.

Consequências:
- não mover/copiar ItemStacks para “slots visuais” próprios;
- não usar o renderer para decidir arma equipada;
- não converter item mostrado nas costas/cintura em equipamento funcional;
- Epic Fight continua authority de weapon category, animations e combat behavior.

## 3. Renderização e composição visual
O mod precisa coexistir com o pipeline de player rendering do pack. Interações prioritárias:
- Epic Fight;
- First-person Model;
- Fresh Moves/Fresh Animations e extensões de player;
- Vanity/cosméticos;
- custom armor layers e held-item renderers.

O fato de o item aparecer em posição incorreta é falha de render, não mudança de inventário.

## 4. Main/offhand e hotbar state
A representação precisa acompanhar trocas de slot, main hand/offhand, item removal e sync de inventário.

O visual não deve permanecer stale após:
- mudar o slot selecionado;
- dropar/mover o item;
- death/respawn;
- reconnect;
- alteração de dimensão.

## 5. Client/server boundary
Modrinth classifica a build 4.2.7 como compatível Client-side e Client & Server; a release inclui correção de crash de servidor.

A renderização é client-facing. O servidor não deve receber mutation de gameplay baseada na posição visual do item.

Dedicated server deve carregar a build sem classes client-only vazando para common path.

## 6. Boundary para RPG/combate
- Nunca detectar “arma nas costas” para conceder perk/skill.
- Consultar o item realmente equipado/selecionado e a capability/category do provider de combate.
- Não gerar Mastery por item permanecer visível.
- Se outro renderer esconder/substituir o modelo, isso não afeta stats ou requisitos.

## 7. Riscos
1. **Layer conflict:** dois renderers desenham o mesmo item/slot.
2. **Animation mismatch:** item flutua/clippa durante poses Epic Fight/Fresh Moves.
3. **First-person leakage:** renderer de third-person aparece onde não deveria.
4. **Stale visual state:** troca de slot/inventory não atualiza modelo.
5. **Dedicated server:** regressão de classloading; 4.2.7 inclui fix explícito de server crash.
6. **Cosmetic ambiguity:** aparência visual confundida com item mecanicamente equipado.

## 8. Matriz de testes
- [ ] Dedicated server inicia com 4.2.7 sem client-only crash.
- [ ] Todos os hotbar slots relevantes aparecem/atualizam corretamente.
- [ ] Main/offhand e shield/banner usam posição esperada.
- [ ] Drop/move/swap remove o visual antigo imediatamente.
- [ ] Epic Fight idle/attack/dodge/guard não produz clipping grave ou item duplicado.
- [ ] First-person Model não duplica render de equipamento.
- [ ] Fresh Moves/Fresh Animations mantêm transforms coerentes.
- [ ] Relog/death/dimension change não deixa render stale.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências
- **Modlist física 08/09/2026:** `weaponmaster_ydm-1.21.1-neoforge-4.2.7.jar`, runtime 4.2.7.
- CurseForge/Modrinth oficiais: hotbar items visíveis no personagem, versão 4.2.7 para NeoForge 1.21.1, changelog `server crash fix`.

## 10. Limitação
A configuração visual efetiva e compatibilidade com cada renderer do pack não foram testadas nesta etapa. Nenhuma incompatibilidade concreta é afirmada sem render-test.

## 11. Revalidação física — 11/09/2026
A modlist física continua contendo exatamente `weaponmaster_ydm-1.21.1-neoforge-4.2.7.jar`, mod id `weaponmaster_ydm`, versão `4.2.7`. A release oficial 4.2.7 para NeoForge 1.21.1 permanece aplicável; o changelog exato continua registrando **server crash fix**.

O mod permanece estritamente uma camada de apresentação do equipamento/hotbar; inventário, arma real e combat behavior continuam pertencendo aos providers originais, com Epic Fight `21.17.3.1` como authority de combate. A decisão **Sem decisão** e o estado **Instalado — Dossiê completo** foram preservados. Nenhum dedicated-server, Epic Fight, First-person Model, Fresh Moves ou stale-render test foi executado nesta recatalogação.

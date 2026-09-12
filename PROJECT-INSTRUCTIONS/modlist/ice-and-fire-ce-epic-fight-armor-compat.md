# Ice and Fire CE x Epic Fight Armor Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81998b04d13e191ed240
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Ice and Fire CE x Epic Fight Armor Compat
- **Arquivo JAR:** `iceandfire-ce-epicfight-armor-compat-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, RPG, Visual
- **Função:** Bridge client-side que adapta armaduras do Ice And Fire CE à aparência/poses/animações do Epic Fight.
- **Dependências:** Ice And Fire CE 2.1.2 + Epic Fight 21.17.3.1 no pack atual. Release 1.0.0 NeoForge 1.21.1, Environment Client.
- **Sobreposição:** Não substitui Epic Fight nem Ice and Fire CE; cobre somente adaptação visual de armaduras. Weapons, dragon combat, AI e movesets permanecem fora do escopo salvo outra bridge comprovada.
- **Compatibilidade/Riscos:** Escopo estreito de armaduras. Riscos: mixin target drift, double transform/layer, clipping em poses, update unilateral de Epic Fight/IAF CE e falsa suposição de full combat compatibility. IAF CE não declara Epic Fight como suporte nativo geral.
- **Observações:** JAR físico declara `iceandfire_epicfight_armor_compat.mixins.json`. A release oficial é a primeira versão e o projeto é Client-only; não atribuir dano/stats/combat authority à bridge.
- **Procedência:** modlist.txt física atual de 09/09/2026 + CurseForge oficial file 8552314 / descrição do autor; source público exato não localizado, internals mantidos fail-closed.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-x-epic-fight/files/8552314
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — IAF CE x Epic Fight Armor Compat 1.0.0 release-pinned; client-only armor bridge, mixin/side boundaries, lifecycle visual, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `iceandfire-ce-epicfight-armor-compat-1.0.0.jar`, mod id `iceandfire_epicfight_armor_compat`, versão `1.0.0`. A release oficial NeoForge 1.21.1 file `8552314` confirma exatamente o filename e descreve a build como primeira versão do compat. O projeto é **Client** e seu escopo declarado é armadura/aparência/animação.

## 1. Papel e authority
Este addon corrige/adapta **as armaduras do Ice And Fire Community Edition** para a pose/animação/aparência do Epic Fight. Ele não é combat overhaul, não adiciona movesets de armas nem altera a AI dos dragões. Ice and Fire CE continua owner das armaduras/stats; Epic Fight continua owner do animation/combat framework; esta bridge é owner apenas da adaptação visual entre os dois.

## 2. Dependências físicas
O pack contém `iceandfire-2.1.2.jar` e `epic-fight-21.17.3.1-mc1.21.1-neoforge.jar`. A descrição do addon afirma ter sido testada contra a build beta mais recente disponível na época; isso não é garantia automática para toda versão futura. A combinação física atual precisa de smoke test visual.

## 3. Mixin e superfície de intervenção
O JAR físico declara `iceandfire_epicfight_armor_compat.mixins.json`, coerente com um patch de render/model/armor compatibility. Sem source público exato, targets/classes/methods do mixin não são inventados. O arquivo de 6 KB e a descrição oficial reforçam escopo estreito.

## 4. Client-only
A distribuição oficial marca Environment **Client**. Não deve ser necessário atribuir authority server-side a esse addon. Dano, armor attributes, durability e combat settlement continuam nos providers-base. Se o JAR for removido apenas do servidor, validar handshake/mod-list real; se removido do cliente, o risco esperado é render/animation incompatível, não perda deliberada de gameplay state.

## 5. Boundary com o suporte oficial IAF CE
Ice And Fire CE 2.1.2 declara que **Epic Fight não possui suporte nativo geral**. Este addon cobre armaduras/aparência. Portanto não usar sua presença como evidência de full compatibility para weapons, dragon riding/combat, AI, hitboxes ou movesets.

## 6. Lifecycle visual
Validar equip/unequip, troca de armor set, primeira/terceira pessoa, animation state do Epic Fight, death/respawn, dimension change, reconnect e resource reload. Model transforms não devem ficar presos após remover/trocar armadura e não podem acumular duas transforms por frame.

## 7. Riscos técnicos
- update do Epic Fight mudar armor animation/model hooks;
- update do IAF CE mudar armor renderer/layers;
- mixin target drift causar crash ou compat simplesmente não aplicar;
- double transform com outra bridge de armor;
- clipping/deformação em poses Epic Fight;
- interpretar compat de armadura como suporte de combate completo;
- client mod-list divergence entre jogadores produzir apresentação diferente.

## 8. Matriz de testes obrigatória
- [ ] Cliente inicia com IAF CE 2.1.2 + Epic Fight 21.17.3.1 + compat 1.0.0.
- [ ] Dedicated server inicia com os providers-base sem depender de lógica da bridge client.
- [ ] Equipar cada família principal de armor IAF não deforma body/limbs em idle/run/attack.
- [ ] Troca rápida equip/unequip não deixa transform stale.
- [ ] Terceira pessoa/outro jogador observa armor alinhada durante animações Epic Fight.
- [ ] Death/respawn/dimension change limpa state visual.
- [ ] F3+T/resource reload não duplica layers.
- [ ] Weapons/dragon combat não são considerados compatíveis sem teste/bridge separados.

## 9. Evidências e limites
- **Modlist física:** JAR, mod id, versão, mixin config e versões físicas de IAF CE/Epic Fight.
- **CurseForge:** release NeoForge 1.21.1 file 8552314, Environment Client, first version upload, escopo explícito de armor animation/appearance.
- **Limite:** source público da build 1.0.0 não foi localizado; targets de mixin e lista exata de armors cobertas permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

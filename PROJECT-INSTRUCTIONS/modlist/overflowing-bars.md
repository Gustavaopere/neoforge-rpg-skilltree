# Overflowing Bars

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81439e21efaaa76f2b90
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `OverflowingBars-v21.1.1-1.21.1-NeoForge.jar`, mod id `overflowingbars`, runtime `21.1.1`, mixins `overflowingbars.common.mixins.json` + `overflowingbars.neoforge.mixins.json` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Overflowing Bars 21.1.1 e os providers/overlays citados nos regression gates estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Overflowing Bars
- **Arquivo JAR:** `OverflowingBars-v21.1.1-1.21.1-NeoForge.jar`
- **Versão 1.21.1:** 21.1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL, RPG
- **Função:** HUD client-side para representar health, armor e armor toughness acima dos limites visuais vanilla sem alterar os atributos reais.
- **Dependências:** Cliente NeoForge 1.21.1; nenhuma hard dependency externa adicional confirmada para a release física.
- **Sobreposição:** Sobrepõe apenas presentation layer de HUD; não substitui AttributeFix/MaxHealthFix nem qualquer provider de atributos.
- **Compatibilidade/Riscos:** Client-only. Riscos: sobreposição/z-order com outros HUDs, GUI scale, valores dinâmicos stale e composição com high-health/armor systems. 21.1.1 corrige rendering quando HUD é ocultado por F1.
- **Observações:** Runtime 21.1.1, file ID 5770623, Release 30/09/2024. Changelog exato: não renderizar elementos quando HUD está escondido via F1.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 21.1.1 e descrição funcional.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/overflowing-bars
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Overflowing Bars 21.1.1 reconstruído: health/armor/toughness overflow HUD, client-only authority, F1 fix, high-attribute composition, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `OverflowingBars-v21.1.1-1.21.1-NeoForge.jar`, mod id `overflowingbars`, versão `21.1.1`, NeoForge 1.21.1. É um mod **client-side de HUD**: representa health, armor e armor toughness acima dos limites visuais vanilla, sem alterar os atributos reais. A 21.1.1 corrige especificamente rendering quando o HUD é ocultado por F1.

## 1. Identidade e papel
- **Mod:** Overflowing Bars.
- **JAR físico:** `OverflowingBars-v21.1.1-1.21.1-NeoForge.jar`.
- **Mod id:** `overflowingbars`.
- **Runtime:** `21.1.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** Fuzs.
- **CurseForge project ID:** 852662.
- **Ambiente:** Client.
- **Licença:** MPL 2.0.
- **Papel:** expandir a representação visual de health/armor/toughness altos de forma compacta.

## 2. Authority: HUD, não atributos
Overflowing Bars não aumenta vida, armadura ou toughness. Ele apenas renderiza valores já existentes de forma mais legível quando excedem a capacidade visual do HUD vanilla.

Ownership:
- atributos reais pertencem ao player/provider/mod que os modifica;
- server continua authority dos valores funcionais;
- Overflowing Bars controla somente apresentação client-side.

## 3. Health acima do vanilla
Em builds RPG, valores altos de vida podem ultrapassar a quantidade de corações que cabe confortavelmente no HUD vanilla. O mod reorganiza/representa esse excesso sem exigir que o jogador interprete múltiplas cores confusas como sistema de estado separado.

O valor mostrado precisa acompanhar exatamente o health/max-health recebido do jogo; qualquer discrepância deve ser investigada no provider de atributos antes de culpar o renderer.

## 4. Armor e toughness
O mesmo princípio se aplica a armor e armor toughness acima da representação normal. Isso é especialmente relevante em packs com gear, atributos e addons mágicos que podem elevar esses valores.

A visualização não altera redução de dano nem fórmula de armor. Ela deve apenas refletir o estado já calculado.

## 5. Release 21.1.1
O changelog exato da 21.1.1 registra: **não renderizar elementos quando o HUD está escondido com F1**.

Esse é o regression gate específico da versão instalada. A 21.1.0 foi o port para 1.21.1; portanto não atribuir a 21.1.1 outras mudanças não publicadas.

## 6. Compatibilidade com HUDs do pack
O risco principal é composição visual com outros mods que desenham barras/overlays, não conflito de gameplay.

Testar:
- Dynamic RPG Resource Bars e outros HUDs presentes;
- boss bars;
- efeitos/status icons;
- Modern UI/font scaling;
- diferentes GUI scales/resoluções;
- shaders quando alteram o pipeline de HUD.

Conflito deve ser descrito por posição/z-order/clipping específico, não como incompatibilidade geral.

## 7. Valores extremos e AttributeFix
O pack possui sistemas capazes de elevar atributos e também mods de correção/caps. Overflowing Bars deve receber/renderizar o resultado final sem se tornar parte do cálculo.

Testes devem incluir:
- health acima de 20;
- absorção quando aplicável;
- armor/toughness elevados;
- mudança dinâmica de max health por equip/unequip;
- valores reduzidos novamente após remover gear/effect.

## 8. Client-only e multiplayer
Como mod client-side, diferentes jogadores podem usar ou não Overflowing Bars sem alterar o state autoritativo do servidor.

A ausência do mod deve afetar apenas apresentação. O servidor não deve depender dele para validar dano, health ou armor.

## 9. Lifecycle visual
Eventos sensíveis:
- entrada no mundo;
- troca de dimensão;
- death/respawn;
- equip/unequip rápido;
- mudança de GUI scale;
- F1 hide/show;
- resource reload.

O HUD deve reconstruir o valor atual sem barras fantasmas ou estado stale.

## 10. Riscos
1. **HUD overlap:** barras podem colidir com overlays de outros mods.
2. **Z-order/clipping:** elementos podem ficar atrás/por cima de componentes indevidos.
3. **F1 regression:** 21.1.1 corrige rendering com HUD oculto; retestar.
4. **Dynamic attribute changes:** equip/unequip pode deixar valor visual stale.
5. **Absorption/high-health composition:** múltiplos estados precisam ser representados sem confusão.
6. **GUI scale:** layouts extremos podem comprimir ou cortar barras.
7. **Misattribution:** renderer não é causa de cálculo de armor/health incorreto sem evidência.

## 11. Matriz de testes
- [ ] Cliente inicia com Overflowing Bars 21.1.1.
- [ ] Health vanilla normal permanece legível.
- [ ] Max health alto é representado sem overflow/clipping.
- [ ] Damage/heal atualizam o HUD imediatamente.
- [ ] Equip/unequip de gear que altera max health não deixa barra stale.
- [ ] Armor e toughness altos são representados corretamente.
- [ ] F1 oculta todos os elementos do mod e ao retornar restaura corretamente.
- [ ] Death/respawn e dimension change limpam/reconstroem o estado visual.
- [ ] GUI scale mínima/máxima usada no pack não colide com outros HUDs.
- [ ] Outros resource/boss bars permanecem legíveis em composição.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixins `overflowingbars.common.mixins.json`/`overflowingbars.neoforge.mixins.json`.
- CurseForge oficial: project 852662, file ID 5770623, Release NeoForge 1.21.1 de 30/09/2024, Environment Client.
- Descrição oficial: representação compacta de health, armor e toughness acima dos limites vanilla.
- Changelog 21.1.1: não renderizar quando HUD está escondido por F1.
- **Limite:** fórmulas de layout, thresholds e internals de rendering não foram inventados sem source/JAR específico.

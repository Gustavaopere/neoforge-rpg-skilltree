# Spell Actionbar

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81a8b575e7477bce6344
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `spell_actionbar-1.1.4.jar`, mod id `spell_actionbar`, runtime `1.1.4`; Iron's Spells 3.16.3, Iron's Lib 2.1.0, Curios 9.5.1 e Spell Codex/Specs 1.6.5 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Spell Actionbar 1.1.4 e seu stack Iron's/Curios/Codex citado estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Spell Actionbar
- **Arquivo JAR:** `spell_actionbar-1.1.4.jar`
- **Versão 1.21.1:** 1.1.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Magia, QoL
- **Função:** Action bar dedicada para Iron's Spells com equip screen, slots de scroll/spellbook, quick-cast, ícones/cooldowns/mana/keybind HUD e APIs de integração; Spell Codex 1.6.5 é consumidor confirmado.
- **Dependências:** Iron's Spells 'n Spellbooks + Iron's Lib; Curios via Iron's. Runtime físico: Iron's 3.16.3, Iron's Lib 2.1.0, Curios 9.5.1, Spell Actionbar 1.1.4. Consumidor confirmado: Spell Codex 1.6.5.
- **Sobreposição:** Pode substituir visualmente a spell bar nativa do Iron's; essa sobreposição é configurável e intencional.
- **Compatibilidade/Riscos:** Riscos: slot shrink com retorno/drop de scroll, HUD stacking, gate client/server mismatch, keybind conflicts, double cast em integrações de input/animação e Curios capacity stale. Scrolls não são consumidos ao castar pela barra.
- **Observações:** mod id `spell_actionbar`; runtime 1.1.4. Decisão Dependência preservada devido ao Spell Codex escolhido. Sem spellbook Curios são 3 slots; book equipado define capacidade. A action bar controla UI/loadout, não spell registry nem mana real.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Spell Actionbar 1.1.4 + stack físico Iron's Spells 3.16.3, Iron's Lib 2.1.0, Curios 9.5.1 e Spell Codex 1.6.5. Dossiê de 08/09 preservado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/spellactionbar
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Spell Actionbar 1.1.4 permanece o runtime físico e a dependência funcional do Spell Codex; slot model, HUD, quick-cast, Curios lifecycle e APIs continuam coerentes com a release oficial.
- **Histórico da decisão:** 2026-08-27 — classificado como Dependência após confirmação de Spell Codex 1.6.5 instalado/Manter e integrado à Spell Actionbar.
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `spell_actionbar-1.1.4.jar`, mod id `spell_actionbar`, versão `1.1.4`. Spell Actionbar é a camada de **slots, equip UI, HUD e quick-cast** usada por Iron's Spells e consumida diretamente pelo Spell Codex 1.6.5.

## 1. Identidade, versão e decisão
- **Mod:** Spell Actionbar.
- **JAR físico:** `spell_actionbar-1.1.4.jar`.
- **Mod id:** `spell_actionbar`.
- **Versão:** `1.1.4`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Decisão vigente:** **Dependência**, preservada porque Spell Codex 1.6.5 usa esta action bar como superfície funcional.

## 2. Stack físico confirmado
- Iron's Spells 'n Spellbooks `1.21.1-3.16.3`;
- Iron's Lib `1.21.1-2.1.0`;
- Curios `9.5.1+1.21.1`;
- Spell Actionbar `1.1.4`;
- Spell Codex/Specs `1.6.5`.

O upstream exige Iron's + Iron's Lib e Curios via Iron's. O pack satisfaz o stack físico.

## 3. Authority e ownership
- **Iron's:** authority de spell definitions, mana, cooldowns e efeitos.
- **Spell Actionbar:** authority dos slots equipáveis, HUD, key labels e integração de quick-cast.
- **Curios:** authority do spellbook equipado.
- **Spell Codex:** pode impor gates e escolher conteúdo elegível, mas usa Actionbar como surface de equip/cast.

A action bar não cria spells nem substitui o state de mana/cooldown do Iron's.

## 4. Slot model
A documentação oficial define:
- **3 slots** quando nenhum spellbook Curios está equipado;
- spellbook equipado define o tamanho da barra conforme sua **max spell capacity**;
- spells já packed no book ocupam e bloqueiam os primeiros slots;
- slots restantes recebem scrolls do jogador;
- se o book diminuir ou bloquear um slot ocupado por scroll, o scroll retorna ao inventário e é dropado se o inventário estiver cheio.

Este lifecycle de shrink/return é crítico para evitar item loss/duplication.

## 5. Equip screen e casting
- A equip screen abre pelo keybind **Spell Actionbar**; a documentação oficial informa Backtick como tecla padrão.
- Iron's scrolls podem ser colocados nos slots livres.
- Cast usa os quick-cast keybinds do Iron's.
- Scrolls **não são consumidos** ao castar pela barra.
- O HUD mostra o keybind correspondente em cada slot.

Quando Spell Codex está ativo, a elegibilidade de casting pode ser adicionalmente filtrada pelos gates do Codex.

## 6. HUD
Superfícies publicadas:
- ícones de spells;
- cooldown overlays;
- affordability tinting;
- labels compactos de keybind;
- mana bar estilizada para o conjunto;
- ocultação da barra nativa de spells e do mana overlay do Iron's para evitar stacking visual.

O HUD é representação; mana/cooldown reais continuam sob authority do Iron's/servidor.

## 7. API para pack makers e addons
A página oficial publica três superfícies de integração:
- `SpellActionbarMenuOpeners` — permite que outro mod abra uma equip UI customizada;
- `ActionbarCastGate` — helper de gating de cast, explicitamente usado por addons como Specs/Spell Codex;
- APIs de **HUD offset preview/persist** para editores de HUD companheiros.

Essas APIs explicam a integração arquitetural com Spell Codex; não são meras coincidências de UI.

## 8. Client / server
- Cliente: equip screen, HUD, key labels, input e preview.
- Servidor: cast válido, item/spell state e consequências de gameplay precisam continuar authoritative.
- Equip changes não podem existir apenas no cliente; reconectar deve reconstruir slots/state corretamente.

## 9. Multiplayer
Validar especialmente:
- jogador remoto com spellbook de capacidade diferente;
- mudança de spellbook durante sessão;
- cast simultâneo/quick-cast sem double execution;
- cooldown/mana HUD refletindo state servidor;
- scroll retornado ao inventário/drop quando slot desaparece;
- Spell Codex gating idêntico entre cliente e servidor.

## 10. Lifecycle
- boot e conexão;
- abrir/fechar equip screen;
- adicionar/remover scroll;
- equip/unequip/trocar spellbook Curios;
- shrink de capacidade;
- relog/restart;
- morte/respawn;
- mudança de keybind;
- HUD edit/offset persist;
- adicionar/remover Spell Codex em ambiente de teste para separar responsibilities.

## 11. Integrações concretas no pack
- **Spell Codex 1.6.5:** consumidor confirmado; usa actionbar, gates e HUD editing.
- **Iron's 3.16.3:** provider das spells e quick-cast infrastructure.
- **Curios 9.5.1:** spellbook equip state.
- **Epic Fight & Iron's animation compat:** casting visual/animation precisa ser regression-tested para casts disparados pela action bar.
- Outros HUD mods podem disputar região acima da hotbar; conflito deve ser tratado como layout, não gameplay.

## 12. Riscos técnicos
1. **Slot shrink:** item duplication/loss quando capacidade diminui.
2. **HUD stacking:** outro mod pode reexibir barra/mana nativos ou ocupar a mesma área.
3. **Gate mismatch:** cliente mostrar spell equipável que servidor/Codex rejeita.
4. **Keybind conflicts:** Backtick e quick-cast keys podem colidir com outros controles do pack.
5. **Cast duplication:** integração com animações/input não deve enviar dois casts.
6. **Curios state:** equip/unequip rápido ou relog pode deixar capacidade stale.
7. **Scroll semantics:** scroll não é consumido na barra; qualquer addon que suponha consumo precisa de teste.

## 13. Matriz de testes
- [ ] Dedicated server boot com Iron's/Curios/Actionbar.
- [ ] Sem spellbook: exatamente 3 slots.
- [ ] Spellbook Curios altera capacidade conforme max spell capacity.
- [ ] Packed book spells ficam locked nos slots iniciais.
- [ ] Scroll livre pode ser inserido/removido sem consumo indevido.
- [ ] Shrink devolve scroll ao inventário; inventário cheio provoca drop único.
- [ ] Cooldown/mana/affordability HUD refletem state real.
- [ ] Iron's native spell/mana overlays não ficam duplicados.
- [ ] `ActionbarCastGate` do Spell Codex bloqueia/permite conforme regra.
- [ ] Relog/restart preserva/reconstrói loadout corretamente.
- [ ] Epic Fight casting animation ocorre uma vez por cast.
- [ ] Dois jogadores com books/loadouts diferentes não desyncam.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 14. Evidências
- Modlist física canônica 08/09/2026: JAR e stack Iron's/Curios/Codex.
- CurseForge oficial SpellActionbar 1.1.4: slots, spellbook behavior, HUD, casting, requirements e APIs de integração.

## 15. Revalidação física — 11/09/2026
A modlist atual mantém exatamente `spell_actionbar-1.1.4.jar`, com Iron's Spells `3.16.3`, Iron's Lib `2.1.0`, Curios `9.5.1` e Spell Codex `1.6.5`. A relação causal que sustenta a decisão **Dependência** permanece válida.

A release 1.1.4 continua sendo o escopo físico; os controles de slots/HUD/casting descritos acima permanecem aplicáveis. Nenhum teste de keybind, slot shrink ou double-cast foi executado nesta recatalogação.

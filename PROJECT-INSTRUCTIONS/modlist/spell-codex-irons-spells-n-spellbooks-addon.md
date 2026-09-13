# Spell Codex: Iron's Spells 'n Spellbooks Addon

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81cfb0e1ccdf3ae404c5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `specs_irons_spellbooks-1.6.5.jar`, mod id `specs_irons_spellbooks`, runtime name `Specs: Iron's Spells 'n Spellbooks Addon`, runtime `1.6.5`; Iron's Spells 3.16.3, Iron's Lib 2.1.0 e Spell Actionbar 1.1.4 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Specs/Spell Codex 1.6.5 e o stack Iron's/Actionbar citado estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Spell Codex: Iron's Spells 'n Spellbooks Addon
- **Arquivo JAR:** `specs_irons_spellbooks-1.6.5.jar`
- **Versão 1.21.1:** 1.6.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG, QoL
- **Função:** Sistema de descoberta/progressão para Iron's Spells: Codex por escolas, unlock sequencial de tiers com XP+ink, conversão de scroll economy e loadout/casting integrado à Spell Actionbar; em 1.6.5 adiciona exceção configurável para spells imbued em armas.
- **Dependências:** Obrigatórias: Iron's Spells 'n Spellbooks + Iron's Lib + Spell Actionbar 1.1.4+. Runtime físico: Iron's 3.16.3, Iron's Lib 2.1.0, Actionbar 1.1.4. Upstream declara teste com Iron's 3.16.2, portanto 3.16.3 é regression gate.
- **Sobreposição:** Toca em progressão de magia e UI, mas não é um pacote de spells; pode coincidir com Dynamic Skill Trees/Pufferfish em objetivos de progressão.
- **Compatibilidade/Riscos:** Riscos: double-gating com outras skill trees; school/spell addons sem custos/blacklists adequados; transformação de recipes/scrolls; slot shrink; config sync; patch drift Iron's 3.16.3. Em 1.6.5 `allowImbuedWeaponCasting` default true bypassa discovery/tier, mas respeita spell blacklist.
- **Observações:** mod id `specs_irons_spellbooks`; runtime name `Specs: Iron's Spells 'n Spellbooks Addon`; editorialmente Spell Codex. Decisão Manter preservada. A descrição geral de casting actionbar-only é supersedida pelo changelog 1.6.5 especificamente para imbued weapons quando habilitado.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Spell Codex/Specs 1.6.5 + stack físico Iron's Spells 3.16.3, Iron's Lib 2.1.0 e Spell Actionbar 1.1.4. Dossiê de 08/09 preservado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon ; https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/8710459
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Spell Codex/Specs 1.6.5 permanece exatamente instalado; progressão/discovery, Actionbar dependency, scroll economy e exceção imbued 1.6.5 revalidados contra o stack Iron's atual.
- **Histórico da decisão:** Confirmado instalado como Spell Codex 1.6.4. Foi identificado corretamente como sistema de descoberta/progressão/UX, e não como pacote de spells. Na comparação com Unraveling, foi recomendado escolher um dos sistemas de gating/progressão em vez de manter ambos. Em 22/08/2026 o usuário escolheu Spell Codex e removeu Unraveling. Spell Codex passa, portanto, a ser o sistema escolhido nessa comparação. Dynamic Skill Tree continua separado e em teste.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `specs_irons_spellbooks-1.6.5.jar`, mod id `specs_irons_spellbooks`, runtime name `Specs: Iron's Spells 'n Spellbooks Addon`, versão `1.6.5`. A página editorial é **Spell Codex**. Este mod é a camada escolhida de **descoberta, progressão, unlock e loadout/casting** sobre Iron's Spells; não é um pacote de spells independente.

## 1. Identidade, versão e decisão
- **Mod/página:** Spell Codex: Iron's Spells 'n Spellbooks Addon.
- **Runtime name:** Specs: Iron's Spells 'n Spellbooks Addon.
- **JAR físico:** `specs_irons_spellbooks-1.6.5.jar`.
- **Mod id:** `specs_irons_spellbooks`.
- **Versão:** `1.6.5`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Decisão vigente:** **Manter**, escolhida em 22/08/2026 em vez de Unraveling para este papel de gating/progressão.

## 2. Stack físico confirmado
O runtime atual contém:
- Iron's Spells 'n Spellbooks `1.21.1-3.16.3`;
- Iron's Lib `1.21.1-2.1.0`;
- Spell Actionbar `1.1.4`;
- Spell Codex/Specs `1.6.5`.

O upstream do Codex declara Spell Actionbar 1.1.4+ e informa teste com Iron's `1.21.1-3.16.2`. O pack está um patch acima em Iron's (`3.16.3`), portanto essa combinação precisa de regressão local, embora a identidade e os requisitos estejam satisfeitos.

## 3. Authority e ownership
- **Iron's Spells:** authority das escolas, spells, mana, cooldowns, spell data e efeitos de gameplay.
- **Spell Codex:** authority da descoberta, tiers desbloqueados, custos de unlock, respec, conversão de scrolls/pages e gate de casting do jogador.
- **Spell Actionbar:** authority dos slots/equip UI e HUD/actionbar consumidos pelo Codex.
- **Curios:** equip state do spellbook usado para ampliar slots.

O Codex não deve duplicar atributos/mana/spell power de Iron's nem substituir o registry das spells.

## 4. Loop de progressão publicado
O fluxo oficial é:
1. descobrir spells no Codex;
2. abrir o Codex pela integração com Spell Actionbar;
3. selecionar ink correspondente e desbloquear tiers em ordem;
4. escolher uma spell já descoberta/desbloqueada e colocá-la na action bar;
5. castar segundo os gates vigentes.

A UI possui guia integrado, tabs por escola, grid de spells, barras de progresso por escola/geral, slots de ink e editor de HUD.

## 5. Descoberta de spells
Mecanismos publicados:
- **Empty Codex Page:** papel + ink de Iron's no Arcane Anvil, attuned à raridade;
- **Scroll Forge imprint:** blank page + school focus, com custo de XP; inks melhores permitem raridades maiores;
- **Codex Pages:** aprendidas com clique direito; podem vir de loot e conversão de drops de scroll;
- **scrolls remanescentes de Iron's:** podem ser absorvidos no Codex;
- **spellbooks preenchidos:** sneak + clique direito aprende spells inscritas e esvazia o livro; o livro vazio pode então servir como spellbook equipado para slots.

## 6. Tiers, custos e respec
- Tiers são desbloqueados em sequência.
- O unlock custa **XP + uma ink da raridade correspondente**.
- School focus correspondente concede **25% de desconto de XP**, sem remover o custo de ink.
- Respec devolve uma fração configurável do XP gasto.
- Custos por escola/spell podem ser controlados por JSON sincronizado.

Nenhum valor concreto de custo do pack foi lido; não presumir defaults locais.

## 7. Spell Actionbar e slot model
- Sem spellbook equipado: Spell Actionbar fornece 3 slots.
- Um spellbook Curios vazio amplia o número de slots conforme sua capacidade.
- O Codex escolhe/equipa spells nesses slots e inclui edição de posição do grupo actionbar + mana e cast/charge bar.
- O HUD permanece oculto até haver spell equipada.

A action bar é dependência funcional do Codex, não apenas mod visual opcional.

## 8. Mudanças de scroll/recipe economy
O Codex altera a cadeia vanilla do Iron's:
- remove crafting de scrolls de Iron's;
- mantém crafting de spellbooks;
- Scroll Forge passa a imprimir Empty Codex Pages;
- scroll loot/drop é convertido em Codex pages;
- pages podem ser recicladas no Alchemist Cauldron para ink.

A 1.6.5 corrige crash de servidor ao percorrer recipes cujo resultado de outro mod é nulo durante essa remoção/transformação de crafts — upstream cita Stellaris como exemplo.

## 9. Casting e exceção 1.6.5
A documentação geral do projeto afirma que o casting normal do jogador é actionbar-only e bloqueia wheel/books/inventory scrolls. **Entretanto, o changelog específico da 1.6.5 supersede essa descrição para armas imbued:**
- `allowImbuedWeaponCasting` foi adicionado a `codex_settings.json`, default `true`;
- keybind dedicado **Cast Imbued Spell**, default `R`;
- slot HUD próprio à esquerda da action bar;
- imbued casts **não exigem discovery nem tier unlock**;
- `spell_blacklist` continua sendo respeitada.

Portanto, para o runtime físico 1.6.5, armas imbued constituem uma exceção configurável ao gate normal do Codex.

## 10. Config e dados sincronizados
Arquivos oficiais sob `config/specs_irons_spellbooks/`:
- `spell_blacklist.json`;
- `school_blacklist.json`;
- `school_unlock_costs.json`;
- `spell_unlock_costs.json`;
- `codex_settings.json`;
- `known_spells.json` como referência.

Config client HUD: `specs_irons_spellbooks-client.toml`, em composição com config client da Spell Actionbar.

A configuração física do usuário não foi lida nesta auditoria; os defaults publicados não são tratados como config confirmada do pack.

## 11. Admin e operações
Comando publicado: `/specs_codex`, com superfícies administrativas para escolas, discoveries, give pages e list access. O estado de progressão é gameplay state e deve permanecer server-authoritative em multiplayer.

## 12. Client / server e multiplayer
- O cliente apresenta Codex, HUD, editor e seleção.
- O servidor deve validar discovery, unlock, custos, spell blacklist e casting permitido.
- Dois clientes não podem divergir sobre tier/discovery real apenas por HUD stale.
- JSONs sincronizados devem produzir a mesma regra para todos os jogadores conectados.

## 13. Lifecycle
Validar:
- primeiro login e criação do estado de Codex;
- aprender page/scroll/spellbook;
- unlock de tier consumindo XP/ink uma única vez;
- respec e persistência;
- equip/unequip de spellbook Curios alterando slots sem perder spells;
- relog, morte/respawn e server restart;
- reload/config change;
- atualização da lista de schools/spells com addons;
- imbued weapon cast em 1.6.5.

## 14. Integrações concretas no pack
- **Iron's 3.16.3:** provider principal; upstream testou 3.16.2, então patch drift é regression gate.
- **Spell Actionbar 1.1.4:** dependência direta e UI/casting surface.
- **Ars/Iron's compatibility addons e school addons:** novas schools/spells podem entrar no Codex conforme compatibilidade e blacklist; não assumir cobertura universal sem teste.
- **Dynamic Skill Trees/Pufferfish:** podem impor progressão adicional em camada distinta; evitar double-gating não intencional.
- **Stellaris:** 1.6.5 inclui correção explícita para null recipe result durante strip de scroll crafts, relevante porque o pack possui Stellaris-related content.

## 15. Riscos técnicos
1. **Double progression:** outro skill tree pode exigir gate adicional sobre uma spell já desbloqueada no Codex.
2. **School addon discovery:** spell nova pode aparecer sem custo/blacklist adequado se JSONs não forem atualizados.
3. **Recipe transformation:** mods que criam recipes não convencionais precisam de regressão após 1.6.5.
4. **Slot shrink:** reduzir capacidade do spellbook não pode perder scroll/loadout state.
5. **Imbued bypass:** default true ignora discovery/tier; isso pode contrariar a intenção de progressão do pack se não for deliberadamente aceito.
6. **Config sync:** cliente com JSON stale não pode superar regras do servidor.
7. **Version drift:** Iron's 3.16.3 está um patch acima do teste publicado 3.16.2.

## 16. Matriz de testes
- [ ] Dedicated server boot com Iron's 3.16.3 + Actionbar 1.1.4 + Codex 1.6.5.
- [ ] Empty Codex Page e Scroll Forge imprint.
- [ ] Learn via page, scroll e filled spellbook.
- [ ] Tier unlock cobra XP + ink exatamente uma vez.
- [ ] Focus aplica desconto de XP sem remover ink.
- [ ] Respec devolve apenas fração configurada.
- [ ] Blacklist de school/spell é server-authoritative.
- [ ] Curios spellbook aumenta/diminui slots sem perda indevida.
- [ ] Scroll crafts removidos; spellbook crafts permanecem.
- [ ] Recipe de outro mod com result nulo não derruba servidor.
- [ ] Imbued weapon casting default/config ON/OFF e blacklist.
- [ ] Relog/restart/morte preservam discovery/tier conforme esperado.
- [ ] Addon school/spell aparece e respeita custos/blacklists.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 17. Evidências
- Modlist física canônica 08/09/2026: JAR, mod id, runtime name e stack Iron's/Actionbar.
- CurseForge oficial Spell Codex: loop, discovery, unlock, config JSON, admin command, requisitos e alterações de scroll economy.
- Changelog oficial 1.6.5: imbued weapon casting e fix de recipe null.

## 18. Revalidação física — 11/09/2026
O snapshot físico continua exatamente em `specs_irons_spellbooks-1.6.5.jar`, mod id `specs_irons_spellbooks`, runtime 1.6.5, com Iron's Spells `3.16.3`, Iron's Lib `2.1.0` e Spell Actionbar `1.1.4` presentes.

A exceção de casting de armas imbued e o fix de recipe result nulo continuam sendo os deltas exatos atribuídos à 1.6.5. A decisão **Manter** e o papel do Codex como sistema escolhido de descoberta/progressão permanecem coerentes. Nenhum teste runtime foi executado nesta revalidação.

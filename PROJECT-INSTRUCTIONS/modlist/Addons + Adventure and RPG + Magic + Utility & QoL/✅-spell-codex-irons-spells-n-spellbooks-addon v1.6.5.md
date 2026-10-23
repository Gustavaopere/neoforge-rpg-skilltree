# Spell Codex: Iron's Spells 'n Spellbooks Addon

> **Autoridade física atual — revalidada em 02/10/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#525**: JAR `specs_irons_spellbooks-1.6.5.jar`, mod id `specs_irons_spellbooks`, runtime `1.6.5`, SHA-1 `05349ae05cf7119bf46f45621ee578b3651563e6`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Spell Codex: Iron's Spells 'n Spellbooks Addon
- **Arquivo JAR:** `specs_irons_spellbooks-1.6.5.jar`
- **Versão 1.21.1:** 1.6.5
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, RPG, QoL
- **Função:** Sistema de descoberta/progressão para Iron's Spells: Codex por escolas, unlock sequencial de tiers com XP+ink, conversão de scroll economy e loadout/casting integrado à Spell Actionbar; em 1.6.5 adiciona exceção configurável para spells imbued em armas.
- **Dependências:** Obrigatórias: Iron's Spells 'n Spellbooks + Iron's Lib + Spell Actionbar 1.1.4+. Runtime físico: Iron's 3.16.3, Iron's Lib 2.1.0, Actionbar 1.1.4. Upstream declara teste com Iron's 3.16.2, portanto 3.16.3 é regression gate.
- **Sobreposição:** Toca em progressão de magia e UI, mas não é um pacote de spells; pode coincidir com Dynamic Skill Trees/Pufferfish em objetivos de progressão.
- **Compatibilidade/Riscos:** Riscos: double-gating com outras skill trees; school/spell addons sem custos/blacklists adequados; transformação de recipes/scrolls; slot shrink; config sync; patch drift Iron's 3.16.3. Em 1.6.5 `allowImbuedWeaponCasting` default true bypassa discovery/tier, mas respeita spell blacklist.
- **Observações:** mod id `specs_irons_spellbooks`; runtime name `Specs: Iron's Spells 'n Spellbooks Addon`; editorialmente Spell Codex. Decisão Manter preservada. A descrição geral de casting actionbar-only é supersedida pelo changelog 1.6.5 especificamente para imbued weapons quando habilitado.
- **Procedência:** modlist física atual revalidada em 02/10/2026 confirma Spell Codex/Specs 1.6.5, Iron's Spells 3.16.3, Iron's Lib 2.1.0 e Spell Actionbar 1.1.4; CurseForge oficial revalidado em 02/10/2026 continua apontando 1.6.5 como a release NeoForge 1.21.1 mais recente. Dossiê histórico preservado; nenhum teste runtime executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon ; https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/8710459
- **Atualização/Status:** UPSTREAM E AUTORIDADE FÍSICA REVALIDADOS EM 02/10/2026 — Spell Codex/Specs 1.6.5 continua instalado e permanece a release NeoForge 1.21.1 mais recente; não há atualização posterior a percorrer. Imbued-casting exception, recipe-null fix, gates e testes permanecem válidos.
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
## 19. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `specs_irons_spellbooks-1.6.5.jar`, versão `1.6.5`, com Iron's Spells `3.16.3`, Iron's Lib `2.1.0` e Spell Actionbar `1.1.4`. A 1.6.5 continua sendo a release 1.21.1 localizada mais recente. A exceção configurável de casting por armas imbued e o fix de recipe result nulo continuam deltas materiais; não há version gate novo. A decisão **Manter** permanece inalterada. Nenhum teste de discovery, unlock, scroll economy, imbued casting ou integração com Iron's 3.16.3 foi executado nesta revalidação.

## 20. Revalidação física e upstream — 02/10/2026

A autoridade física atual continua exatamente em `specs_irons_spellbooks-1.6.5.jar`, mod id `specs_irons_spellbooks`, versão `1.6.5`, na ordem física #525. O mesmo snapshot mantém Iron's Spells `1.21.1-3.16.3`, Iron's Lib `1.21.1-2.1.0` e Spell Actionbar `1.1.4`.

O CurseForge oficial continua apontando **1.6.5** (file ID `8710459`) como a release NeoForge 1.21.1 mais recente, publicada em 22/08/2026. Logo, não existe release posterior à versão do GitHub/física que precise ser incorporada neste ciclo. Permanecem materiais os deltas já documentados da própria 1.6.5: `allowImbuedWeaponCasting`, keybind/HUD de spell imbued, bypass de discovery/tier para casts imbued com blacklist preservada e o fix de startup quando outro mod fornece recipe com result nulo.

`Apprentice's Codex 0.9.7.1` também aparece na modlist física atual, porém é um mod distinto (`apprenticecodex`) e não altera a identidade nem a versão física de Spell Codex/Specs. Nenhum teste runtime adicional foi executado nesta revalidação documental.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum arquivo físico do pack, filename documental certificado, posição de inventário, configuração ou save foi alterado.

- **Projeto solicitado:** Spell Codex: Iron's Spells 'n Spellbooks Addon
- **Último candidato compatível localizado:** **1.6.13 (NeoForge 1.21.1, 19/10)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Validar spellbook/codex UI, spell registry, recipes/loot, Iron's Spells API, networking e persistence.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon ; https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/8710459

## Auditoria oficial release-by-release NeoForge 1.21.1 — 23/10/2026 (1.6.5 → 1.6.13)

**Autoridade física histórica preservada:** ordem #525, JAR `specs_irons_spellbooks-1.6.5.jar`, mod id `specs_irons_spellbooks`, runtime `1.6.5`. O nome de runtime é *Specs: Iron's Spells 'n Spellbooks Addon*, enquanto o projeto CurseForge exato é **Spell Codex: Iron's Spells 'n Spellbooks Addon**, autor **SemSemSem**, **ID 1623374**. Categorias temáticas oficiais **Addons + Adventure and RPG + Magic + Utility & QoL**, correspondentes ao diretório. Ambiente **Client & Server**. A última Release NeoForge 1.21.1 verificada em 23/10 é **`specs_irons_spellbooks-1.6.13-neoforge-1.21.1.jar`**, 19/10, CurseForge **File ID 9253448**.

### Sequência completa dos arquivos publicados após a build física

| Versão | Data / File ID NeoForge 1.21.1 | Changelog oficial atribuível à build correta | Requisito Actionbar publicado |
|---|---|---|---|
| **1.6.6** | **17/09**, **8906895**, `specs_irons_spellbooks-1.6.6.jar` | **Corrige targeting de bloco em spells do Apprentice's Codex** quando lançados por actionbar ou tecla de spell imbued (antes podia solicitar “Target a block” indevidamente); `known_spells.json` passa a **agrupar spells pela escola real**, e não tudo em Evocation; os JSON de config passam a conter apenas dados, movendo guias/exemplos para `config/specs_irons_spellbooks/CONFIG_GUIDE.md`. | **1.1.5+** |
| **1.6.7** | **18/09**, **8910755**, `specs_irons_spellbooks-1.6.7.jar` | **Descobrir uma spell** por Codex page, scroll ou extração de spellbook também **desbloqueia tier I** da spell; corrige estado descoberto sem primeiro tier acessível. | **1.1.5+** |
| **1.6.8** | **22/09**, **8946417**, `specs_irons_spellbooks-1.6.8-neoforge-1.21.1.jar` | Introduz tab **All spells** para spells descobertas agrupadas por escola, memória da última aba aberta e rolagem suave em abas e grade; corrige **camada de desenho de cooldowns** de actionbar/imbued slot para não cobrir hotkey, além de setas de abas de escolas que ficavam presas à aba selecionada. | **1.1.6+** |
| **1.6.9** | **26/09**, **8976546**, `specs_irons_spellbooks-1.6.9-neoforge-1.21.1.jar` | Adiciona **`defaultActionbarSlots`** ao `codex_settings.json`, padrão **3** e faixa **1–15**, sincronizada com clientes, definindo os slots mostrados quando **não há spellbook equipado**. | **1.1.7+** |
| **1.6.10** | **29/09**, **9013888**, `specs_irons_spellbooks-1.6.10-neoforge-1.21.1.jar` | Acrescenta **comandos administrativos para elevar ao máximo os tiers de spells já descobertas**, no conjunto do jogador ou filtrado por escola: `/specs_codex spell max @s` e `/specs_codex spell max @s irons_spellbooks:fire`. | A página da build não declara mínimo novo; manter o piso **1.1.7+** já estabelecido para a 1.6.9, sem presumir retrocompatibilidade. |
| **1.6.13** | **19/10**, **9253448**, `specs_irons_spellbooks-1.6.13-neoforge-1.21.1.jar` | Corrige **Codex saindo da tela com GUI scale alto/janela pequena** (escala ajustada para manter livro, inspector e ink row visíveis); corrige **crash de dedicated server** por classe client-only de player em networking comum; impede colocar **Codex spell cards em slots extras de outros mods, como TrashSlot**, evitando **duplicação** — cards só entram na actionbar. | **1.1.7+** |

**Lacunas numéricas:** o catálogo oficial filtrado **não publica builds `1.6.11` nem `1.6.12` para NeoForge 1.21.1** entre 1.6.10 e 1.6.13; elas **não devem ser inventadas nem contabilizadas como releases auditadas**. Também não tomar arquivos com mesmo número para **Forge 1.20.1** como prova dos fixes no NeoForge: as notas acima foram verificadas nas páginas dos JARs **da plataforma correta**.

**Fontes primárias por arquivo:**
- 1.6.6 — https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/8906895
- 1.6.7 — https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/8910755
- 1.6.8 — https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/8946417
- 1.6.9 — https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/8976546
- 1.6.10 — https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/9013888
- 1.6.13 — https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/9253448 ; lista completa e categorias https://www.curseforge.com/minecraft/mc-mods/spell-codex-irons-spells-n-spellbooks-addon/files/all

### Bloqueio obrigatório: Spell Actionbar instalado não atende as novas releases

**O snapshot físico atual contém `Spell Actionbar 1.1.4`, enquanto a versão 1.6.6 já declara `Spell Actionbar 1.1.5+`, a 1.6.8 requer `1.1.6+` e as versões 1.6.9/1.6.13 pedem explicitamente `1.1.7+`.** Logo, apesar de a série documental estar completa, **nenhum destes candidatos pode ser recomendado para instalação isolada**. Deve ser auditada e coordenada a atualização de Spell Actionbar e verificada a compatibilidade real com Iron's Spells 3.16.3, Iron's Lib 2.1.0, playerAnimator/Curios, skills/quests do pack e Apprentice's Codex 0.9.7.1. A exigência publicada é um mínimo de dependency, não comprovação de QA.

**Mudanças de gameplay que demandam decisão:** 1.6.7 concede tier I no momento da descoberta; isso interfere na economia de XP+ink e em qualquer skill tree que também faça gating. Comandos 1.6.10 alteram progressão server-side; verificar permissões e flags para não criar unlock sem custo indevido. `defaultActionbarSlots` altera apresentação e sync da configuração por jogador, mas não cria novas spells nem autoridade cliente para casting.

### Plano de validação — nenhum teste executado
- [ ] Backup de mundo, configurações JSON e estado por jogador antes de migrar; catalogar páginas/spellbooks, tiers, inks e cargas em actionbar.
- [ ] Atualizar Spell Actionbar para versão compatível **≥1.1.7** em uma cópia de instância, avaliando o changelog e a metadata da sua própria build; nunca alterar automaticamente o JAR físico.
- [ ] Abrir Codex em GUI scale alta e janela pequena; testar tabs, All spells, scrollbars, school navigation, inspector e ink row.
- [ ] Iniciar **dedicated server** de verdade e dois clientes, testar rede/sincronização e ausência de classes cliente no common networking.
- [ ] Tentar mover Codex cards para slots extra de TrashSlot/outros mods e checar conservação/ausência de dupe, dentro e fora da actionbar.
- [ ] Testar Apprentice's Codex block-targeting spells, casting imbued e actionbar; garantir tier I após discovery por page, scroll e spellbook, incluindo casos de XP/ink insuficientes.
- [ ] Validar `defaultActionbarSlots` 1/3/15 com e sem spellbook equipado, sincronização server→client, slot shrink e inventários cheios.
- [ ] Testar comandos `/specs_codex spell max` com e sem escola e permissions, relog, morte, restart e spells provenientes de outros addons.

**Conclusão documental:** as **seis releases posteriores à física 1.6.5** foram discriminadas por **changelog do artefato NeoForge 1.21.1 exato**, e a ausência de 1.6.11/1.6.12 no catálogo foi registrada sem inferência. **#82 pode sair da fila de pesquisa de versões/changelogs**. Isso **não autoriza update**: o **Spell Actionbar 1.1.4 físico bloqueia a promoção** da série 1.6.6–1.6.13, e os gates de progressão/duplicação/servidor ainda requerem QA. Preservar dossier, versão e JAR.

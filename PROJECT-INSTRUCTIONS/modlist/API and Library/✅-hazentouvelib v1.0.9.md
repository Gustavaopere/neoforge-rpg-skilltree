# HazentouveLib

## Propriedades do registro

- **Mod:** HazentouveLib
- **Arquivo JAR:** `hazentouvelib-1.0.9.jar`
- **Versão 1.21.1:** `1.0.9`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/Hazentouvel/HazentouveLib
- **Função:** Biblioteca/API do ecossistema Hazen com schools, attributes, entity/item bases, effects, particles, data attachments e helpers reutilizáveis para addons de Iron's Spells.
- **Dependências:** NeoForge 1.21.1; Hazen N Stuff 1.4.0.14 é consumer físico confirmado. Source 1.0.9 declara NeoForge \[21.1.0,) e foi desenvolvido contra 21.1.224; pack usa 21.1.250.
- **Compatibilidade/Riscos:** Riscos: ABI drift com consumers/Iron's, duplicate school/attribute registration, attachments stale, Hexed/damage duplicado, modifiers Dual/Tri/Pure duplicados, client keybind sem validação e classloading side incorreto.
- **Sobreposição:** Biblioteca específica do ecossistema Hazen; não substitui Iron's Spells, SpellLib ou outras APIs sem alterar os consumers. Schools/resources continuam provider-specific.
- **Observações:** API pública documenta Radiance/Shadow/Cosmic, bases de Enderman spellcaster, Hexed, tiers Dormant/Pure/Ascended, Dual/Tri, maces, rarities, 5 keybinds e AbstractTaggedSpell. Não tratar essas bases como conteúdo final independente.
- **Procedência:** modlist(1).txt física atual de 22/09/2026 — 587 entradas top-level incluindo o modloader — confirma `hazentouvelib-1.0.9.jar`, mod id `hazentouvelib`, runtime `1.0.9` e SHA-1 `b5d68711babb604e368b918683eb89a4d1492077`. CurseForge/source oficiais revalidados em 22/09/2026 mantêm 1.0.9 como build NeoForge 1.21.1 atual; a linha 26.1.2 é separada e não aplicável ao pack.
- **Atualização/Status:** Histórico upstream NeoForge 1.21.1 1.0.10→1.0.14 revisto em 17/11/2026 (Steel/IGnJ, GeckolibBlockEntity, Ender Fire/WingCurioItem, remoções source 1.0.13, modelo de asa 1.0.14). Runtime físico 1.0.9 preservado; versões publicadas versus commit source diferenciadas; QA pendente.
- **Data da última decisão:** 2026-08-26


> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #313: JAR `hazentouvelib-1.0.9.jar`, mod id `hazentouvelib`, runtime `1.0.9`, SHA-1 `b5d68711babb604e368b918683eb89a4d1492077`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `hazentouvelib-1.0.9.jar`, mod id `hazentouvelib`, versão `1.0.9`, Minecraft 1.21.1 / NeoForge. O source oficial `Hazentouvel/HazentouveLib:main` declara exatamente `mod_version=1.0.9`, portanto a ficha é source-pinned à build instalada.
</callout>

## 1. Papel e authority

HazentouveLib é a biblioteca compartilhada dos mods Hazen. O projeto a descreve como library para criação de addons e afirma que não adiciona gameplay independente; ao mesmo tempo, publica registries/classes-base reutilizáveis. A authority final de spells/equipamentos concretos permanece no consumer; a library é authority apenas dos tipos, registries e helpers que expõe.

## 2. Dependência física e versão

Hazen N Stuff 1.4.0.14 está presente e é consumer confirmado. O source 1.0.9 declara Minecraft 1.21.1, NeoForge range `[21.1.0,)`, desenvolvimento contra 21.1.224 e dependências de desenvolvimento como GeckoLib, JEI, Player Animator, Curios, AttributeLib e AzureLib. O pack usa NeoForge 21.1.250, dentro do range.

## 3. Schools compartilhadas

A documentação oficial enumera três schools providas pela library: **Radiance, Shadow e Cosmic**. Elas formam infraestrutura de school/attribute/spell para consumers Hazen; não devem ser duplicadas por addons próprios com IDs paralelos sem necessidade. School power/resistance e spell registration devem passar pelo contrato real do ecossistema Iron's/HazentouveLib.

## 4. Entities-base

A library publica abstrações `AbstractSpellCastingEnderman`, `AbstractSpellCastingEnderManAngerEvent` e `AbstractNeutralSpellCastingEnderman`. São tipos-base para consumers, não entidades de gameplay que devem necessariamente spawnar por si. O owner de uma entidade concreta continua o mod que a registra.

## 5. Effect `Hexed`

A documentação declara o effect **Hexed**, associado a dano ao lançar spells. Como efeito de combate/casting, a decisão de aplicar/remover e a liquidação de dano precisam ser server-authoritative. Consumers não devem reaplicar o dano ao observar simultaneamente cast event e tick do effect.

## 6. Item/API families

A library documenta infraestrutura de armor em três tiers — **Dormant, Pure e Ascended** — além de variantes **Dual** e **Tri** com múltiplos school spell powers. Também expõe bases de Magic Maces (`HLMaceCastingItem`, `HLMaceStaffItem`, `HLImbuableMaceStaffItem`) e spawn eggs do ecossistema. Esses nomes são API/source surfaces, não inventário de itens finais do Hazen N Stuff.

## 7. Rarities e technical surface

São publicadas rarities específicas de schools e outras rarities auxiliares, incluindo Fireblossom, Hydro e Deus. A documentação também declara **5 keybinds para 5 abilities** e `AbstractTaggedSpell`. Keybind é input client; a habilidade final deve validar contexto/authority no servidor.

## 8. Registries confirmados no source

O source 1.0.9 expõe registries para sounds, mob effects, items, blocks, schools, particles, creative tabs e attributes, além de NeoForge **data attachments**. Essa superfície torna registry sync, attachment persistence e ABI de consumers boundaries reais.

## 9. Data attachments e persistência

A existência de `HLDataAttachments` usando `AttachmentType` confirma state anexado a objetos NeoForge. Sem enumerar serializers/defaults não publicados, qualquer consumer que dependa desse state deve validar login/relog, clone/death quando aplicável, dimension transfer e server restart para evitar attachment stale ou perdido.

## 10. Client / server

A distribuição oficial marca Environment `Server`, mas a library contém registries e superfícies usadas por itens/particles/keybinds dos consumers. A etiqueta não substitui a análise de classloading. Paths common/server não devem carregar classes gráficas; input/particles permanecem client-facing, enquanto attributes/effects/attachments e gameplay são server-authoritative.

## 11. Lifecycle

Registrar a library antes de consumers que precisam de schools/items/effects; validar mod construction, registry events, attribute creation, attachment registration, login/registry sync, reload dos consumers e shutdown/restart. Atualização da library é mudança de ABI e deve ser testada contra todos os consumers instalados.

## 12. Multiplayer

Attributes/effects/attachments não podem usar state global compartilhado entre jogadores. Cinco abilities/keybinds devem ser validadas por player/contexto; efeitos e modifiers precisam ser exatamente-once em equip/relog. Consumer entity ownership deve permanecer no consumer, não na library.

## 13. Riscos técnicos

- remover a library com Hazen N Stuff ainda instalado;
- ABI drift de school/item/entity base classes;
- duplicate school/attribute registration;
- attachment sem persistência/sync esperado;
- `Hexed` causar dano duplicado por múltiplos hooks;
- modifier Dual/Tri/Pure aplicado duas vezes;
- keybind client provocar ação sem validação server-side;
- classloading client-only em dedicated server;
- update de Iron's alterar contracts consumidos pela library.

## 14. Matriz de testes obrigatória

- [ ] Dedicated server boot com HazentouveLib 1.0.9 + Hazen N Stuff 1.4.0.14 + Iron's 3.16.3.
- [ ] Radiance/Shadow/Cosmic registram uma única vez e sincronizam ao cliente.
- [ ] Consumer spell usando `AbstractTaggedSpell` mantém school/cast correto.
- [ ] `Hexed` aplica dano apenas no evento/contexto esperado.
- [ ] Dormant/Pure/Ascended e Dual/Tri modifiers não duplicam após relog.
- [ ] Data attachments persistem/sincronizam conforme o consumer espera.
- [ ] Cinco ability inputs não permitem client authority indevida.
- [ ] Entity bases de consumers não perdem owner/anger state em reconnect.
- [ ] Atualização da library é bloqueada até regression test de todos os consumers.

## 15. Evidências e limites

- **Modlist física:** `hazentouvelib-1.0.9.jar` e consumer Hazen N Stuff instalado.
- **Source oficial:** `mod_version=1.0.9`, registries e data attachments da build atual.
- **Documentação oficial:** 3 schools, entity bases, Hexed, armor tiers, mace bases, rarities, 5 keybinds e AbstractTaggedSpell.
- **Limite:** não confundir infraestrutura registrada pela library com conteúdo final de cada consumer; serializers/defaults não auditados permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

## Releases 1.0.10 → 1.0.14 — auditoria incremental (17/11/2026, não instaladas)

**Runtime comprovado somente no último snapshot físico:** `hazentouvelib-1.0.9.jar`, #313, 1.0.9. **Projeto correto:** HazentouveLib (Hazen/HazentouvelPrime), CurseForge project **1528722**, category temática **API and Library**; não confundir com Hazen 'n Stuff consumidor. O projeto já apresenta `hazentouvelib-1.0.14.jar` como **Release NeoForge 1.21.1 de 14/11/2026** em seu [painel oficial](https://www.curseforge.com/minecraft/mc-mods/hazentouvelib/files/8977077); identificador do arquivo-alvo observado no link oficial **9617408**. O código `Hazentouvel/HazentouveLib:main` também marca `mod_version=1.0.14`, mas o código-fonte não comprova sozinho hashes ou conteúdo binário do artefato.

| Versão | Evidência e impacto |
|---|---|
| **1.0.10** — 25/09/2026, [CurseForge 8975951](https://www.curseforge.com/minecraft/mc-mods/hazentouvelib/files/8975951) | Reajusta **Soul Flame Explosion** para ser coerente com o Soul Fire da library; **backport Steel**, move compatibilidade de Steel com Iron's Gems 'n Jewelry de **Hazen 'n Stuff** para a própria library. O changelog tem uma linha truncada ("Touched on an issue with..."), que não autoriza inventar outro fix. [Commit source 736cf415](https://github.com/Hazentouvel/HazentouveLib/commit/736cf41531d33dcac667a4ee3462bf097c4e63a3) adiciona steel ingot/nugget, crude metal, blocos e tags de mining. Relevante a registries, crafts e consumers; conteúdo ainda não ativo no runtime 1.0.9. |
| **1.0.11** — 26/09/2026, [CurseForge 8977077](https://www.curseforge.com/minecraft/mc-mods/hazentouvelib/files/8977077) | **Receitas de Steel** e base **GeckolibBlockEntity**, úteis para addons com renderer/block entity; risco de ABI e collision de registries. |
| **1.0.12** — 08/11/2026, [CurseForge 9506484](https://www.curseforge.com/minecraft/mc-mods/hazentouvelib/files/9506484) | Remove **Soul Fire custom** em favor do Soul Fire fornecido pelo **Iron's Lib**, acrescenta **Ender Fire** e corrige **WingCurioItem**. O [commit da linha 1.0.12](https://github.com/Hazentouvel/HazentouveLib/commit/ccb56edb094d4de3b5f9fc0a80ce2b7531fee164) mostra modificação de attachment `soul_fire`→`ender_fire` e atualização de dependências locais Iron's/IGnJ. Potencial quebra de dados/registries/visual/fire effects; não tratar como mera troca cosmética. |
| **1.0.13** — source 14/11/2026, [commit 202212b76](https://github.com/Hazentouvel/HazentouveLib/commit/202212b76161d724b325e49f91c634c4e9a6909a) | **Estado de publicação separado:** commit muda `mod_version=1.0.12` para `1.0.13` e remove da library os registros de **SoulFlameBolt entity, renderer e spell** (além de adicionar tag base `ender_fire_base_block`). A existência de um JAR CurseForge 1.0.13 separado **não foi comprovada**; documentar este passo como **mudança verificável no source** que antecede a 1.0.14. Remoções podem quebrar addons que referenciem o registro/ABI anterior. |
| **1.0.14** — 14/11/2026, [projeto CurseForge com Release 1.0.14](https://www.curseforge.com/minecraft/mc-mods/hazentouvelib/files/9617408), [commit source bcc95a8](https://github.com/Hazentouvel/HazentouveLib/commit/bcc95a8469557907b5c59713ba6cc49fcb5a9ee6) | Incrementa `mod_version` para 1.0.14 e corrige transform fixo de `wing_base.json` (rotation Y 180 e translation ajustada), mudando representação de asas/Curios no slot. O commit é evidência do source, **não inspeção byte a byte do JAR**. |

**Dependências e gates:** o último dump registra Hazen 'n Stuff **1.4.0.14**, Iron's Spellbooks **3.16.3**, Iron's Lib **2.1.0**. A linha de source 1.0.12 atualiza as referências build/dev de Iron's Lib (não confundir automaticamente com hard minimum do artefato). Antes de qualquer upgrade, verificar metadata/dependency bounds do 1.0.14, migrar ou preservar attachments/registries de fire/bolt, testar steel recipes/IGnJ compat, blocos e world saves, wings/Curios, Hazen N Stuff e outros consumers, client + dedicated server. O bytecode de 1.0.14 e sua compatibilidade com as versões físicas **não foram testados**. Auditar 1.0.13 como transição source verificável, sem inventar publicação externa.

**Conclusão:** releases publicadas identificadas 1.0.10/1.0.11/1.0.12/1.0.14 e revisão do source 1.0.13; documentação de todas as mudanças intermediárias verificáveis concluída **até 1.0.14**. O JAR físico **continua 1.0.9**, sem renomear ou duplicar o dossier.

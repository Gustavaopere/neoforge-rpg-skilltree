# HazentouveLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81dd8bf5c2d4af6f51aa
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** HazentouveLib
- **Arquivo JAR:** `hazentouvelib-1.0.9.jar`
- **Versão 1.21.1:** 1.0.9
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca/API do ecossistema Hazen com schools, attributes, entity/item bases, effects, particles, data attachments e helpers reutilizáveis para addons de Iron's Spells.
- **Dependências:** NeoForge 1.21.1; Hazen N Stuff 1.4.0.14 é consumer físico confirmado. Source 1.0.9 declara NeoForge `[21.1.0,)` e foi desenvolvido contra 21.1.224; pack usa 21.1.248.
- **Sobreposição:** Biblioteca específica do ecossistema Hazen; não substitui Iron's Spells, SpellLib ou outras APIs sem alterar os consumers. Schools/resources continuam provider-specific.
- **Compatibilidade/Riscos:** Riscos: ABI drift com consumers/Iron's, duplicate school/attribute registration, attachments stale, Hexed/damage duplicado, modifiers Dual/Tri/Pure duplicados, client keybind sem validação e classloading side incorreto.
- **Observações:** API pública documenta Radiance/Shadow/Cosmic, bases de Enderman spellcaster, Hexed, tiers Dormant/Pure/Ascended, Dual/Tri, maces, rarities, 5 keybinds e AbstractTaggedSpell. Não tratar essas bases como conteúdo final independente.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial Hazentouvel/HazentouveLib exatamente em 1.0.9 + CurseForge oficial para superfícies API publicadas.
- **Fonte:** https://github.com/Hazentouvel/HazentouveLib
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — HazentouveLib 1.0.9 source-pinned; schools, entity/item bases, effects, registries, data attachments, keybind/side, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `hazentouvelib-1.0.9.jar`, mod id `hazentouvelib`, versão `1.0.9`, Minecraft 1.21.1 / NeoForge. O source oficial `Hazentouvel/HazentouveLib:main` declara exatamente `mod_version=1.0.9`, portanto a ficha é source-pinned à build instalada.

## 1. Papel e authority
HazentouveLib é a biblioteca compartilhada dos mods Hazen. O projeto a descreve como library para criação de addons e afirma que não adiciona gameplay independente; ao mesmo tempo, publica registries/classes-base reutilizáveis. A authority final de spells/equipamentos concretos permanece no consumer; a library é authority apenas dos tipos, registries e helpers que expõe.

## 2. Dependência física e versão
Hazen N Stuff 1.4.0.14 está presente e é consumer confirmado. O source 1.0.9 declara Minecraft 1.21.1, NeoForge range `[21.1.0,)`, desenvolvimento contra 21.1.224 e dependências de desenvolvimento como GeckoLib, JEI, Player Animator, Curios, AttributeLib e AzureLib. O pack usa NeoForge 21.1.248, dentro do range.

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

# Ars Hex

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db819484eff8bd220a057e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Ars Hex
- **Arquivo JAR:** `ars_hex-1.21.1-5.0.4b.jar`
- **Versão 1.21.1:** 5.0.4b
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Magia, Compat
- **Função:** Bridge/unificação entre Ars Nouveau e outros ecossistemas mágicos. Na build 5.0.4b integra condicionalmente Malum, Iron's Spells e Hexerei; no pack atual Malum e Iron's estão ativos, Hexerei não está presente como JAR top-level.
- **Dependências:** Ars Nouveau 5.13.1. Providers opcionais físicos ativos: Malum 1.8.2 e Iron's Spells 3.16.3. Hexerei está ausente da modlist top-level atual; seu módulo permanece dormente. Sauce é jarjar.
- **Sobreposição:** Sobrepõe objetivos de integração a outras bridges mágicas, mas não substitui nenhum provider. Deve usar contracts nativos de Ars, Malum e Iron's sem reaplicar dano, souls ou perks.
- **Compatibilidade/Riscos:** Riscos principais: double damage conversion Ars↔Iron's, double soul/spirit rewards com Malum, classloading de provider opcional e contaminação documental por 5.0.5. Hexerei não está ativo no runtime físico atual. Moon Dial é 5.0.5 e foi excluído.
- **Observações:** mod id `ars_hex`. Na integração Malum a build registra EffectSoulShatter + SoulWardPerk, MagicProficencyPerk e SpiritSpoilsPerk. Iron's registra damage tweaks/post-init/docs/particles; perk placeholder permanece comentado. Hexerei support existe upstream, mas provider está ausente na modlist física.
- **Procedência:** modlist.txt física atual de 11/09/2026 + source Ars-Unity commit `a12bf191458f48ae0375d1e1073f24455f654416` (5.0.4b) e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `ars_hex-1.21.1-5.0.4b.jar` / `5.0.4b`; sem divergência física.
- **Fonte:** https://github.com/Alexthw46/Ars-Unity
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #45: `ars_hex-1.21.1-5.0.4b.jar` / `5.0.4b` conferidos contra a modlist atual; source pin e exclusão de conteúdo 5.0.5 preservados.
- **Histórico da decisão:** Manter. Ficha reconstruída em 07/09/2026 no commit exato 5.0.4b; suporte Hexerei foi separado de integração ativa e Moon Dial 5.0.5 foi explicitamente excluído.
- **Data da última decisão:** 2026-09-07

> 🔮 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Runtime físico: `ars_hex-1.21.1-5.0.4b.jar`, mod id `ars_hex`, NeoForge 1.21.1. A auditoria está fixada no commit upstream **`a12bf191458f48ae0375d1e1073f24455f654416`**, cujo `gradle.properties` declara `5.0.4b`. O addon é uma camada de **unificação/compatibilidade entre Ars Nouveau e outros ecossistemas mágicos**. No pack atual, Malum e Iron's estão ativos; Hexerei não está presente como JAR top-level. Conteúdo introduzido em 5.0.5, especialmente **Moon Dial**, foi explicitamente excluído.

## 1. Identidade e version pin
- **Mod:** Ars Hex / Ars Hex Unity.
- **JAR:** `ars_hex-1.21.1-5.0.4b.jar`.
- **Runtime:** `5.0.4b`.
- **Mod id:** `ars_hex`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Commit exato usado:** `a12bf191458f48ae0375d1e1073f24455f654416`.
- **Decisão:** **Manter**.

## 2. Papel arquitetural
Ars Hex não deve ser tratado como uma escola mágica independente. Seu papel é conectar Ars Nouveau a providers mágicos externos, acrescentando conteúdo/behavior apenas quando o mod correspondente está carregado.

Na inicialização da build 5.0.4b, o código possui módulos condicionais para:
- **Malum**;
- **Hexerei**;
- **Iron's Spells 'n Spellbooks**.

Cada módulo é carregado por `ModList.isLoaded`; provider ausente deve resultar em integração ausente, não fallback genérico.

## 3. Integração Malum — ativa no pack
A modlist física contém Malum `1.8.2.0`, portanto esta integração é efetivamente relevante.

### Glyph Ars registrado
- `EffectSoulShatter` — registrado apenas quando `malum` está carregado.

### Perks Ars registrados
1. `SoulWardPerk`
2. `MagicProficencyPerk`
3. `SpiritSpoilsPerk`

### Conteúdo/bridge adicional
O source 5.0.4b inclui `EnchanterScythe` e `MalumCompat`, conectando equipamento/semântica Malum ao lado Ars.

**Boundary:** espíritos, soul data e recursos Malum continuam sob authority Malum; Ars Hex fornece a ponte. Não criar um segundo inventário de souls nem duplicar drops/spirit conversion.

## 4. Integração Iron's Spells — ativa no pack
A modlist física contém Iron's `3.16.3`. Na inicialização 5.0.4b, `ISSCompat`:
- é inicializado somente quando `irons_spellbooks` está carregado;
- registra listeners de **damage tweaks** para Ars e para a integração EISS;
- possui `postInit` próprio;
- injeta documentação compatível;
- registra particles client-side quando necessário.

Na build auditada, os placeholders de perk Iron's no `ArsNouveauRegistry` permanecem comentados, portanto **não documentar perk Iron's próprio como registrado**.

### Risco central
Damage conversion/tweaks da bridge já são provider-native. Uma perk ou integração local que converta spell damage novamente pode causar double-dip, resistência errada ou duas contabilizações do mesmo hit.

## 5. Integração Hexerei — upstream existente, runtime inativo
O source possui `HexereiCompat` com init/post-init, render layers, client extensions, renderers, particles e documentação condicionais.

**Estado atual do pack:** não foi localizado JAR top-level Hexerei na modlist física desta auditoria. Portanto:
- suporte upstream = **confirmado**;
- integração Hexerei ativa no runtime atual = **não confirmada / considerada ausente**;
- não usar classes Hexerei em código carregado incondicionalmente.

## 6. Registry base do addon
O mod registra DeferredRegisters próprios para:
- items;
- blocks;
- sound events;
- entity types;
- particle types.

Contudo, a build 5.0.4b é fortemente provider-conditional: o número de entradas efetivas depende dos compat modules carregados. Esta ficha não soma classes de provider ausente como conteúdo ativo do pack.

## 7. Conteúdo e classes presentes na árvore 5.0.4b
A árvore version-pinned contém, entre os módulos de compatibilidade, superfícies como:
- `EffectSoulShatter`;
- `EnchanterScythe`;
- perks Malum listados acima;
- adapters de Malum, Hexerei e Iron's;
- infraestrutura/config própria do Ars Hex.

O princípio operacional é mais importante que uma contagem artificial: itens/entities/particles de compat só existem quando o módulo correspondente os registra.

## 8. Patch boundary obrigatório — Moon Dial NÃO está instalado
O upstream avançou para `5.0.5`. A mudança que introduz **Moon Dial** está no commit posterior `5f5b9243aa3893f9e208747f89df4d79d6c05e3a`.

Como o JAR físico é `5.0.4b`:
- Moon Dial **não pertence ao catálogo do runtime atual**;
- qualquer documentação que o liste deve ser entendida como versão futura/posterior;
- só adicionar à ficha quando a modlist física mudar.

## 9. Authority e ownership
- **Ars Nouveau:** Source, glyph grammar, casting e Ars perk registry.
- **Malum:** spirits/soul systems e conteúdo próprio.
- **Iron's:** mana/spell schools/cast/damage de Iron's.
- **Hexerei:** conteúdo/rituais próprios quando instalado.
- **Ars Hex:** bridge entre esses providers.

Nunca substituir a integração real por um bônus genérico apenas para “funcionar”.

## 10. Lifecycle e classloading
Como integrações são opcionais:
- classes de providers ausentes não podem ser acessadas em paths comuns;
- dedicated server não deve carregar render-only classes;
- docs/renderers/particles de Hexerei são registrados somente sob gate de presença;
- Malum/Iron's hooks precisam ser revalidados após update dos providers.

## 11. Riscos
1. double damage conversion entre Ars/Iron's;
2. double soul/spirit rewards com Malum;
3. classloading failure de provider opcional;
4. docs de versão 5.0.5 contaminando a build 5.0.4b;
5. pipeline de partículas/render client-only em dedicated server;
6. perks/gear externos copiados em vez de usar o provider nativo.

## 12. Matriz de validação
- client + dedicated-server boot com Malum e Iron's presentes;
- inicialização sem Hexerei;
- `EffectSoulShatter` e três perks Malum;
- EnchanterScythe e soul/spirit handling sem duplicação;
- damage bridge Ars↔Iron's com um único resultado por hit;
- unload/reload/death/respawn de estados compatíveis;
- ausência de classloading Hexerei no runtime atual;
- confirmar que Moon Dial não aparece no 5.0.4b;
- repetir após update de Malum, Iron's ou do próprio Ars Hex.

## 13. Fontes
- Modlist física do projeto, 07/09/2026.
- Source oficial `Alexthw46/Ars-Unity`, commit exato `a12bf191458f48ae0375d1e1073f24455f654416` (5.0.4b).
- Histórico upstream que mostra Moon Dial entrando somente em 5.0.5.
- Guia consolidado de Magia do projeto.

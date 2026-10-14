# kubejsarsnouveau

## Propriedades do registro

- **Mod:** kubejsarsnouveau
- **Arquivo JAR:** kubejsarsnouveau-1.3.2.jar
- **Versão 1.21.1:** 1.3.2
- **Categoria:** Compat, Automação, Magia
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-ars-nouveau
- **Função:** Integra KubeJS ao Ars Nouveau para criar/alterar recipes como Enchanting Apparatus, enchantment, Crush, Glyph, Caster Tome e Imbuement.
- **Dependências:** KubeJS 2101.7.2-build.377 + Ars Nouveau 5.13.1 estão fisicamente presentes. NeoForge 1.21.1; release é Client & Server.
- **Compatibilidade/Riscos:** Riscos: recipe duplication/stale reload, Source cost imbalance, invalid glyph references, loot inflation e API drift Ars/KubeJS. Scripts reais não foram auditados nesta ficha.
- **Sobreposição:** Complementa Ars Nouveau/KubeJS. Pode substituir/compor recipes Ars via IDs; não substitui spell engine, Source ou registries do provider.
- **Observações:** JAR físico `kubejsarsnouveau-1.3.2.jar`, mod id `kubejsarsnouveau`, runtime 1.3.2. Conteúdo material depende de scripts; addon sozinho não cria nova magia.
- **Procedência:** modlist(1).txt física anexada e reconferida em 25/09/2026 + KubeJS Wiki Ars Nouveau + CurseForge oficial KubeJS Ars Nouveau 1.3.2.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — KubeJS Ars Nouveau 1.3.2/JAR físico reconfirmado; matriz física reconciliada com KubeJS 2101.7.2-build.377 e Ars Nouveau 5.13.1. Recipe APIs, Source authority, reload e riscos preservados.
- **Data da última decisão:** 2026-09-06

> **Última autoridade física confirmada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #366: JAR `kubejsarsnouveau-1.3.2.jar`, mod id `kubejsarsnouveau`, runtime `1.3.2`, SHA-1 `f39f4f409e628731be551fd961fac2964768d358`.

> **ESCOPO CANÔNICO.** O snapshot físico de 25/09/2026 registra `kubejsarsnouveau-1.3.2.jar` (mod ID `kubejsarsnouveau`, versão 1.3.2). O addon integra schemas de receitas Ars Nouveau com KubeJS; não cria outro motor de magia. O JAR do perfil atual não foi reaberto nesta auditoria.
## 1. Recipe types documentados
A documentação KubeJS 1.21.1 confirma builders para:
- Enchanting Apparatus;
- enchantment recipes;
- Crush;
- Glyph recipe replacement;
- Caster Tome;
- Imbuement.
Esses recipes são definidos em `server_scripts` por `ServerEvents.recipes`.
## 2. Source cost e Ars authority
Recipes como Apparatus/Imbuement podem declarar Source cost. Ars Nouveau 5.13.1 continua authority de Source, machines, glyph validity e spell/tome behavior. O script descreve a recipe; não deve manter Source paralelo.
## 3. Glyph boundary
A própria documentação alerta que glyph recipe só funciona para glyph válido existente no tome e é adequada principalmente para substituir receita de glyph. Não tratar o addon como registry genérico de glyphs novos sem API/evidência adicional.
## 4. Caster Tomes
O builder de Caster Tome permite registrar tome/recipe com spell parts e metadata de apresentação. Spell parts continuam IDs do provider Ars. Um tome custom não deve ser catalogado como conteúdo ativo do pack sem script real que o registre.
## 5. Reload lifecycle
Recipes ficam no lifecycle de server data e precisam ser idempotentes em `/reload`. IDs estáveis são necessários para substituir recipes antigas sem duplicação. Alterar recipe em mundo existente deve manter compatibilidade com items/components existentes.
## 6. Runtime físico
Ars Nouveau 5.13.1 e KubeJS build.377 estão presentes. A release KubeJS Ars Nouveau 1.3.2 é NeoForge 1.21.1 Client & Server. Nenhuma incompatibilidade específica com o stack físico foi comprovada, mas API drift deve ser testado após updates.
## 7. Riscos
1. Recipe duplicate/stale após reload.
2. Source cost incorreto altera economia mágica.
3. Glyph recipe aponta para glyph inválido.
4. Apparatus ingredient set/keep-NBT diverge do esperado.
5. Crush loot gera duplicação excessiva.
6. API drift Ars/KubeJS quebra builders.
## 8. Boundary para projetos próprios
Black Arcana/RPG Skill Tree não devem inferir spell mastery a partir da existência de recipe/tome. Progressão deve usar evento/state causal do provider real. Recipe customization permanece layer de dados.
## 9. Matriz de testes
- [ ] Dedicated server inicia com 1.3.2 + Ars 5.13.1 + KubeJS build.377.
- [ ] `/reload` duas vezes não duplica recipes.
- [ ] Enchanting Apparatus conserva ingredients/reagent e Source cost.
- [ ] Imbuement aplica custo correto.
- [ ] Glyph replacement só afeta ID pretendido.
- [ ] Caster Tome resolve spell parts válidas.
- [ ] Crush respeita loot/chance sem dupe.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 10. Evidências e limite
KubeJS Wiki e CurseForge oficiais documentam os builders acima e Release 1.3.2. Revalidação externa em 13/09/2026 mantém `1.3.2` como a release NeoForge 1.21.1 mais recente localizada. Scripts reais não foram inspecionados; nenhuma recipe Ars custom é considerada ativa sem evidência no diretório KubeJS do pack.

## 11. Reauditoria de source, schemas e limites para perks — 14/10/2026

### 11.1. Autoridades, versões e discrepância source ↔ distribuição

- **Snapshot físico histórico #366 (25/09/2026):** `kubejsarsnouveau-1.3.2.jar`, mod ID `kubejsarsnouveau`, 1.3.2, SHA-1 `f39f4f409e628731be551fd961fac2964768d358`. **Não foi inspecionado o JAR atualmente em disco** nesta etapa.
- **Distribuição oficial verificada:** [CurseForge file 7181937](https://www.curseforge.com/minecraft/mc-mods/kubejs-ars-nouveau/files/7181937) publica `kubejsarsnouveau-1.3.2.jar` para **Minecraft 1.21.1 NeoForge**, em **03/11/2025**, a release mais recente para essa combinação identificada na [lista de arquivos oficial](https://www.curseforge.com/minecraft/mc-mods/kubejs-ars-nouveau/files/all). Changelog: atualização para KubeJS recente via [PR #9 do autor](https://github.com/BobVarioa/kjsarsnouveau/pull/9), mergeada em 03/11/2025. Release publicada ≠ prova de arquivo local atual.
- **Source pinado e efetivamente lido:** [`BobVarioa/kjsarsnouveau`, commit `18278a05d27def7200158a6d08516d5f22318e44`](https://github.com/BobVarioa/kjsarsnouveau/tree/18278a05d27def7200158a6d08516d5f22318e44). A [configuração Gradle desse commit](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/gradle.properties) ainda declara **`mod_version=1.3.1`**, enquanto o CurseForge distribui **1.3.2**. **Não equiparar source público e bytecode instalado nem alterar para 1.3.1 a versão física histórica.** Os schemas documentados abaixo são contratos **do source auditado**, e a aplicação binária exata na release 1.3.2 continua pendente.
- **Ambiente de compilação do source:** Minecraft 1.21.1, NeoForge `21.1.209`, KubeJS `2101.7.2-build.295`, Rhino `2101.2.7-build.81` e Architectury `13.0.8`. O [template de metadata](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/templates/META-INF/neoforge.mods.toml) declara dependências **required / BOTH** para KubeJS (range iniciado no build de compilação) e Ars Nouveau `[5.9.4,)`; são requisitos desse source, não uma confirmação da metadata binária do JAR 1.3.2. Licença pública MIT.
- **Runtime registrado no último snapshot do pack:** KubeJS #363 `2101.7.2-build.377`, Ars Nouveau #047 `5.13.1`, NeoForge #001 `21.1.250`. São versões posteriores às referências de compilação indicadas; **drift não é teste de incompatibilidade e nem certificação de compatibilidade**.

### 11.2. Schemas encontrados no source público

A classe [`KubeJSArsNouveauPlugin.java`](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/java/com/bobvarioa/kubejsarsnouveau/KubeJSArsNouveauPlugin.java) registra seis tipos de receita sob namespace `ars_nouveau`, além dos componentes `crush_item`, `color` e `sound`. A [KubeJS Wiki Ars Nouveau](https://kubejs.com/wiki/addons/ars-nouveau) descreve exemplos em `ServerEvents.recipes`, pasta `server_scripts/`. Os exemplos não demonstram receitas ativas neste modpack.

| Tipo de recipe | Campos do schema inspecionado e nuances |
| --- | --- |
| `enchanting_apparatus` | `pedestalItems` lista opcional, padrão vazio; `reagent` ingrediente ou lista; `result` item; `sourceCost` inteiro (alias `source`, padrão zero); `keepNbtOfReagent` booleano (alias `keepNbt`, padrão false). |
| `enchantment` | `pedestalItems`, `enchantment` (string), `level` (int), `sourceCost` (alias `source`); não foi identificado o mesmo padrão opcional de `sourceCost` que existe no Apparatus. |
| `crush` | `input`, `output` lista de `CrushItem` e `skip_block_place` (booleano, padrão true). |
| `imbuement` | `input`, `output`, `source` inteiro, `pedestalItems` opcional, padrão vazio. Atenção à distinção **`source` versus `sourceCost`**. |
| `glyph` | `inputs`, `output`, `exp` inteiro (aliases `experience`/`xp`, padrão zero); substituir receitas exige glyph válido registrado pelo próprio Ars no tome, não cria glyph novo. |
| `caster_tome` | `tome_type` (alias `tomeType`, padrão `ars_nouveau:caster_tome`), `name`, `spell` lista de IDs/strings, `flavour_text` (aliases de descrição), `color` e `sound` opcional. IDs de spell parts continuam definidos pelo Ars. |

Classes concretas verificadas no commit acima: [Apparatus](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/java/com/bobvarioa/kubejsarsnouveau/recipes/EnchantingApparatusRecipeJS.java), [Enchantment](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/java/com/bobvarioa/kubejsarsnouveau/recipes/EnchantmentRecipeJS.java), [Crush](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/java/com/bobvarioa/kubejsarsnouveau/recipes/CrushRecipeJS.java), [Imbuement](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/java/com/bobvarioa/kubejsarsnouveau/recipes/ImbuementRecipeJS.java), [Glyph](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/java/com/bobvarioa/kubejsarsnouveau/recipes/GlyphRecipeJS.java) e [Caster Tome](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/java/com/bobvarioa/kubejsarsnouveau/recipes/CasterTomeRecipeJS.java).

### 11.3. Riscos observáveis no source, sem falhas runtime afirmadas

1. No [`ArsComponents.CrushItem`](https://github.com/BobVarioa/kjsarsnouveau/blob/18278a05d27def7200158a6d08516d5f22318e44/src/main/java/com/bobvarioa/kubejsarsnouveau/components/ArsComponents.java), cada saída usa `stack`, `chance` e `maxRange`. O método `CrushItemComponent.replace` recebendo um `ItemStack` simples cria `chance=1.0` e `maxRange=1`. Substituições automáticas sem preservar parâmetros podem desequilibrar drops.
2. A chave `output` de `CrushRecipeJS.CRUSH_OUTPUTS` está registrada no source com **`ComponentRole.INPUT`**, apesar do nome `output`. Um filtro KubeJS que selecione componentes pela role `OUTPUT` pode não tratar esse campo como se espera. **Testar antes de afirmar comportamento ou bug.**
3. O source registra `Color` como record de `id,r,g,b` e `Sound` com campos opcionais `sound,volume,pitch`. A Wiki exemplifica `caster_tome` com cor **numérica**; o caminho de coerção dessa entrada na release binária 1.3.2 não foi certificado. Testar serialização/codec e reload em vez de assumir que as duas representações são intercambiáveis.
4. Roles de recipe como `OUTPUT` aplicadas internamente a `sourceCost` não significam geração de Source; a execução, consumo, máquinas, glyphs e tomes pertencem ao **Ars Nouveau**. KubeJS gerencia o script e o addon adapta schemas: três domínios com ownership diferente.
5. **Fronteira de perks e RPG Skill Tree:** esta API registra receitas; ela **não fornece** por si só gatilhos causais de cast, dano, cooldown, mana/Source consumido, duração de efeitos ou mastery. Qualquer perk nesses domínios precisa de contrato do provider real ou implementação própria verificada. Não usar uma categoria JEI ou existência de tome como prova de progresso.

### 11.4. Regressões exigidas — PENDENTES, nenhuma executada

- [ ] Reabrir **JAR físico atual**, ler `META-INF/neoforge.mods.toml`, mod ID, dependências, hashes e serializadores; reconciliar bytecode 1.3.2 com o source 1.3.1 (não presumir equivalência).
- [ ] Boot client + dedicated server com Ars Nouveau 5.13.1, KubeJS build.377, NeoForge do perfil e Java 21; verificar classloading.
- [ ] Localizar scripts do pack consumindo os seis schemas e IDs reais de receitas; sem consumidores demonstrados, **uso efetivo não verificado**.
- [ ] Dois `/reload` sucessivos e restart: IDs/quantidade de recipes estáveis, ausência de conflitos com datapacks/addons.
- [ ] Apparatus: pedestais, reagent, `sourceCost`, `keepNbtOfReagent` e data components; Imbuement/Enchantment: custo e nível corretos.
- [ ] Crush: `chance`, `maxRange`, `skip_block_place`, substituição de output com role `INPUT` e ausência de dupe.
- [ ] Glyph: recipe válido com glyph existente, sem ID inexistente; Caster Tome: spell parts, cor numérica versus record `Color`, som e persistência após reload.
- [ ] Testar crafting/execução no próprio Ars, não apenas visualização JEI. Conferir consequências em Source, loot, multiplayer, saves e custo de tick quando muitos recipes forem adicionados.

**Decisão documental:** manter `1.3.2` conforme snapshot. **Nenhum JAR, script, datapack, resource pack, shader, mundo, modlist ou sistema de gameplay foi alterado.** Os testes listados continuam pendentes; nenhuma compatibilidade de runtime foi inventada.

# Epic Fight x Curios Compat

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#259**: JAR `Epic Fight x Curios Compat 2.2.jar`, mod id `epicfight_curios_compat`, runtime `1.4`, SHA-1 `3b13ee4590d3765249419c6f320f461cbf1c8f6d`.

## Propriedades do registro

- **Mod:** Epic Fight x Curios Compat
- **Arquivo JAR:** `Epic Fight x Curios Compat 2.2.jar`
- **Versão 1.21.1:** `1.4`
- **Categoria:** Compat, RPG, Visual
- **Função:** Bridge de renderização/equipamento entre Epic Fight e Curios que corrige/anexa visualmente itens em slots Curios ao modelo/renderer Epic Fight e mantém compatibilidade com acessórios/back-slot de mods suportados.
- **Dependências:** Epic Fight 21.17.3.1 + Curios API 9.5.1+1.21.1 estão fisicamente presentes. Providers locais relevantes à matriz publicada incluem Supplementaries 3.9.9, Sophisticated Backpacks 3.26.3 e Iron's Spells 3.16.3.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Divergência legítima: filename/publicação 2.2, metadata runtime 1.4. A matriz pública 1.21.1 localizada cita Epic Fight 21.16.1, enquanto o pack usa 21.17.3.1; isso é version-drift gate. Riscos de accessory transform duplicado, slot não reconhecido, book/backpack/quiver clipping, stale Curios state e conflito com outros armor/player render bridges.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `Epic Fight x Curios Compat 2.2.jar`, mod id `epicfight_curios_compat`, metadata runtime 1.4 e SHA-1 `3b13ee4590d3765249419c6f320f461cbf1c8f6d`. CurseForge oficial revalidado em 21/09/2026 mantém 2.2 como latest release NeoForge 1.21.1 e Environment Client & Server.
- **Observações:** SHA-1 físico `3b13ee4590d3765249419c6f320f461cbf1c8f6d` identifica o artefato publicado como `Epic Fight x Curios Compat 2.2.jar`; metadata interna continua `1.4` e não deve ser normalizada para 2.2. CurseForge oficial revalidado em 21/09/2026 classifica o projeto como **Client & Server**.
- **Atualização/Status:** REVALIDADO DOCUMENTALMENTE EM 13/10/2026 — último inventário físico histórico de **587 entradas** coloca o item na posição **#259** (`Epic Fight x Curios Compat 2.2.jar`; versão distribuída `2.2`; metadata interna `1.4`). A menção a **#258** pertence ao lote de reauditoria de 21/09/2026. CurseForge confirma a release **2.2** NeoForge/MC1.21.1; **a branch de source público rotulada 1.21.1 não corresponde à build 2.2**. Nenhum novo teste binário ou update físico foi realizado.
- **Decisão:** Sem decisão
- **Sobreposição:** Não substitui Curios ou Epic Fight. Atua na camada de attachment/render de equipamentos Curios no modelo Epic Fight; outras armor/player render bridges podem tocar os mesmos transforms e exigem precedence.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs
> **Autoridade física:** `Epic Fight x Curios Compat 2.2.jar` · mod id `epicfight_curios_compat` · **metadata runtime `1.4`**. O filename/publicação é `2.2`; essa divergência é legítima e deve permanecer explícita. SHA-1: `3b13ee4590d3765249419c6f320f461cbf1c8f6d`.
## 1. Papel no modpack
Epic Fight x Curios Compat é uma bridge de **equip/render**: itens equipados em slots Curios precisam acompanhar o modelo/armature/pose do Epic Fight em vez de permanecer presos ao renderer vanilla ou desaparecer/clipping durante animações.
## 2. Authority / ownership
- **Curios:** slot topology, equip/unequip e ItemStack real.
- **Epic Fight:** player armature/animation/combat renderer.
- **Compat:** tradução/attachment visual entre slot Curios e renderer Epic Fight.
O compat não deve copiar inventário Curios nem criar um segundo equipamento lógico.
## 3. Divergência de versão
A modlist física lê metadata `1.4`, enquanto o arquivo é publicado como `Epic Fight x Curios Compat 2.2.jar`. O SHA-1 físico corresponde ao CurseForge file 7865987.
Regra canônica: **não normalizar 1.4 para 2.2 nem o contrário**. Um identifica metadata interna; o outro, release/filename.
## 4. Stack base local
O pack usa:
- Epic Fight `21.17.3.1`;
- Curios API `9.5.1+1.21.1`.
A bridge precisa acompanhar alterações de armature/render do Epic Fight e slot/render contracts do Curios.
## 5. Matriz pública de slots/providers
A descrição pública localizada para a linha 1.21.1 lista suporte a providers como Supplementaries, Sophisticated Backpacks, Iron's Spells e outros backpack/back-slot mods.
No pack estão confirmados:
- Supplementaries `3.9.9`;
- Sophisticated Backpacks `3.26.3`;
- Iron's Spells `3.16.3`.
Não tratar os demais providers da lista como ativos sem presença física.
## 6. Supplementaries
A compat publicada declara suporte completo à linha Supplementaries. O caso clássico é item/quiver/back attachment que precisa acompanhar a transformação corporal do Epic Fight.
O ItemStack e o slot continuam pertencendo ao Curios/Supplementaries; a bridge apenas corrige apresentação.
## 7. Sophisticated Backpacks
Backpacks grandes são uma superfície de clipping/attachment relevante porque ocupam o torso/costas enquanto Epic Fight modifica pose e armature.
Regression gate: idle, sprint, attack, dodge, guard, death e troca de backpack sem transform residual.
## 8. Iron's Spells
A matriz publicada marca suporte específico a spellbooks/itens de back slot da família Iron's Spells. O pack também possui EFIS e EMF Compat: Iron's Spells, então a mesma região visual pode ser tocada por várias bridges.
Isso exige precedence de transform, não duplicação de equip state.
## 9. Drift Epic Fight 21.17.3.1
A matriz pública localizada para 1.21.1 cita **Epic Fight 21.16.1**. O runtime do pack é 21.17.3.1, portanto existe version drift real.
Não há evidência de incompatibilidade comprovada, mas toda mudança do renderer/armature é regression gate obrigatório.
## 10. Side boundary
O CurseForge oficial revalidado em 21/09/2026 classifica o projeto como **Client & Server**. A bridge toca apresentação/render, mas não deve ser tratada como client-only por inferência.
Gameplay/equipment state continua server-authoritative via Curios/provider; renderer não deve liquidar mutation de equip.
## 11. Lifecycle
Validar:
- equip/unequip;
- troca de slot/item;
- battle mode on/off;
- attack/dodge/guard;
- death/respawn;
- dimension change;
- reconnect;
- resource/model reload;
- update Epic Fight;
- update Curios/provider do item.
## 12. Multiplayer
Outro jogador deve enxergar o accessory/backpack na posição correta a partir do state Curios sincronizado. O compat não pode exigir que o observador recrie inventário ou enviar mutation de equip baseada em render.
## 13. Riscos
1. item renderizado duas vezes;
2. item desaparecer em battle mode;
3. attachment point incorreto;
4. torso/backpack clipping;
5. spellbook/quiver transform competir com outra bridge;
6. Curios slot desconhecido;
7. equip state stale após relog;
8. Epic Fight armature/API drift 21.16.1→21.17.3.1;
9. metadata 1.4 ser confundida com release 2.2;
10. assumir client-only sem validar distribuição real do pack.
## 14. Matriz de testes
1. Epic Fight 21.17.3.1 + Curios 9.5.1 boot/connect.
2. Equip/unequip item Curios simples.
3. Supplementaries back/quiver item suportado.
4. Sophisticated Backpack em idle/sprint/attack/dodge.
5. Iron's Spellbook nas poses de cast.
6. EFIS + EMF Compat: Iron's Spells carregados simultaneamente.
7. Battle mode toggle repetido.
8. Death/respawn e reconnect com item equipado.
9. Outro jogador observando os acessórios.
10. Update smoke-test do Epic Fight antes de promover nova build.
**Esta catalogação não afirma que esses testes foram executados.**
## 15. Evidências
- modlist física canônica: filename 2.2, mod id, metadata 1.4, hash e versões Epic Fight/Curios/providers;
- SHA-1 físico → CurseForge file 7865987;
- descrição/matriz pública do projeto: correção de render Curios e providers/slots suportados em 1.21.1.
> **Boundary canônico:** Curios continua authority do **equipamento**; Epic Fight do **modelo/combat animation**; esta bridge apenas resolve o attachment/render entre ambos.


## 16. Revalidação de distribuição e proveniência do source — 13/10/2026

**Autoridade instalada conhecida:** última modlist histórica de **587 posições incluindo NeoForge** registra **#259 `Epic Fight x Curios Compat 2.2.jar`**, identificador `epicfight_curios_compat`, **release/filename `2.2`** e **metadata runtime `1.4`**, SHA-1 previamente catalogado `3b13ee4590d3765249419c6f320f461cbf1c8f6d`. Referência do mesmo inventário: **Epic Fight #260 21.17.3.1**, **Curios #211 9.5.1+1.21.1**, **NeoForge #001 21.1.250**. Sem acesso direto aos JARs nesta auditoria; hash/metadata são registros históricos, não novos cálculos. O filename deste dossiê segue a versão **interna 1.4** por padrão herdado, sem renomeação infundada.

**Fonte oficial de distribuição:** [CurseForge, File ID **7865987**](https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat/files/7865987), **release 2.2 em 03/04/2026**, arquivo exato `Epic Fight x Curios Compat 2.2.jar` publicado para **Minecraft 1.21.1 / NeoForge**, ambiente categorizado **Client & Server**. A [listagem oficial de arquivos](https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat/files/all) não apresenta release posterior da mesma combinação Minecraft/loader na consulta realizada; a publicação 2.2 é, portanto, **a versão externa mais recente compatível identificada**, mas a metadata runtime histórica continua sendo **1.4**, não 2.2.

### 16.1. O que a release 2.2 afirma ter corrigido

O changelog de **7865987** declara dois fixes específicos:
1. **Curios Slots Editor:** corrigida a digitação de letras e números na interface.
2. **Supplementaries quiver:** corrigido um caso de **renderização dupla da aljava**.

O pack tem **Supplementaries #536 3.9.9**, portanto o segundo caso é um regression gate diretamente relevante. O primeiro é um gate de **input/editor client-side**, não justificativa para atribuir à bridge controle do inventário ou da topologia de slots. A existência do fix na release não certifica a ausência de regressão com Epic Fight 21.17.3.1, outras bridges de visual ou as configurações atuais do pack.

### 16.2. Branch oficial chamada 1.21.1 não é o source comprovado do JAR 2.2

O [link **Source** da própria página oficial CurseForge](https://github.com/Unitei-Dev/OneWorldStudio_EpicFight_CuriosCompat) aponta para o repositório do autor, mas sua branch [**`1.21.1`, HEAD `2ec76ce27f9df75f5699d037fb5d5eb6e72114ef`**](https://github.com/Unitei-Dev/OneWorldStudio_EpicFight_CuriosCompat/tree/2ec76ce27f9df75f5699d037fb5d5eb6e72114ef) contém, textualmente:
- [`gradle.properties`](https://github.com/Unitei-Dev/OneWorldStudio_EpicFight_CuriosCompat/blob/2ec76ce27f9df75f5699d037fb5d5eb6e72114ef/gradle.properties): **`minecraft_version=1.20.1`**, **`forge_version=47.4.10`**, **`mod_version=1.3`**;
- [`META-INF/mods.toml`](https://github.com/Unitei-Dev/OneWorldStudio_EpicFight_CuriosCompat/blob/2ec76ce27f9df75f5699d037fb5d5eb6e72114ef/src/main/resources/META-INF/mods.toml): `loaderVersion="[47,)"`, dependência **`forge`** `[47.4.0,)`, Curios `[5.14.1,)` e Epic Fight `[20.13.6,)`, módulo exibido como Forge **1.3**;
- [`README.txt`](https://github.com/Unitei-Dev/OneWorldStudio_EpicFight_CuriosCompat/blob/2ec76ce27f9df75f5699d037fb5d5eb6e72114ef/README.txt) usa exemplos de JARs **Forge 1.20.1** e recomenda desativar CuriosCompat interno do Epic Fight.

**Conclusão de proveniência, não de incompatibilidade:** essa branch, apesar do nome, **não documenta o build NeoForge/1.21.1/2.2**. Não usar assinaturas, hooks, classes de rendering ou instruções do README Forge como prova de comportamento ou de ação necessária para o JAR físico do pack. A instrução de desativar compat interno do Epic Fight **não está validada para 21.17.3.1** e não autoriza remover classes, mixins ou outro mod. Não foi encontrada, nesta inspeção, uma tag/release source pública que conecte **inequivocamente** o artefato CurseForge 7865987 ao código correspondente; classificar a comparação bytecode como **pendente**.

### 16.3. Integrações realmente presentes e testes pendentes

A descrição oficial da linha 1.21.1 lista **Supplementaries, Sophisticated Backpacks e Iron's Spells** dentre os providers suportados (além de outros). O inventário confirma esses três: **#536 3.9.9**, **#516 3.26.3** e **#342 3.16.3**. Coexistem **EFIS #245 3.1.0** e **EMF Compat: Iron's Spells #249 2.0.0**; sua mera presença não prova conflito, mas exige testes de precedence para spellbook/torso/back/cape e player renderer. O quadro público foi originalmente referenciado a **Epic Fight 21.16.1**, diferente da versão histórica **21.17.3.1**: drift de API/render a auditar no JAR, sem inventar incompatibilidade.

**Matriz futura, não executada:** obter cópia autorizada do JAR 2.2, inspecionar `META-INF/neoforge.mods.toml`, mixin configs, classes, dependências e SHA-1; testar boot client + dedicated server NeoForge 21.1.250; validar edição de slots com teclas alfanuméricas; equipar/remover aljava Supplementaries, alternar render de cosmetic/equipment e battle mode, observar se há **um** quiver; testar backpack Sophisticated e spellbook Iron's em ataque/dodge/guard/cast, dois jogadores, troca de dimensão, morte/respawn, relog, reload de resources e coexistência EFIS/EMF. Não atribuir ao cliente autoridade de mutation do inventário. Sem o binário e testes, o *nível de certeza* limita-se às declarações de publicação e ao último inventário registrado.

**Estado:** `Sem decisão` curatorial continua vigente; release publicada externa **2.2** e metadata física histórica **1.4** permanecem distintas. Nenhuma atualização/remoção/patch físico foi efetuado, nem compatibilidade de runtime ou teste in-game foi certificado.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Epic Fight: Curios Compat 3.0
- **Último candidato 1.21.1 / NeoForge localizado:** **linha/projeto 3.0 atualizado em 18/10; suporte 1.21.1/NeoForge confirmado, mas o número exato do arquivo 1.21.1 não foi exposto pela fonte oficial consultada**
- **Estado:** **FAIL-CLOSED — IDENTIDADE/ARTEFATO EXATO PENDENTE**
- **Gate de promoção:** Não inferir versão pelo nome do projeto. Exigir página/arquivo oficial 1.21.1, filename, manifest, dependências Epic Fight/Curios, e teste de render de slots/backpacks/relics antes de editar versão ou recomendar promoção.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat

## Evidência oficial NeoForge 1.21.1 e nova release 3.1 — 23/10/2026

**Preservação da identidade instalada:** o snapshot físico #259 aponta o JAR top-level `Epic Fight x Curios Compat 2.2.jar` com metadata `epicfight_curios_compat` runtime **1.4**. O nome distribuído 2.2 e a metadata 1.4 são diferentes: não renomear dossier ou versão instalada. A página CurseForge **projeto ID 1389133, autor Unitei** intitulada **Epic Fight: Curios Compat 3.0** é inequívoca, mas seu título não corresponde à versão atual da distribuição.

### Publicações e limites do intervalo
- **2.2** (baseline instalado, 03/04/2026): compatibilidade de slots/render Curios com Epic Fight já descrita nas seções anteriores. O projeto oficial do JAR físico permanece o mesmo.
- **3.0** (publicação indicada em 18/10/2026): o catálogo recente mostrava **3.0 para Forge 1.20.1**, não foi recuperado o file ID de um JAR **NeoForge 1.21.1** 3.0 nem changelog específico de arquivo nessa combinação. **Não transferir o conteúdo do arquivo Forge ao NeoForge sem evidência.** A versão 3.0 pode ter sido pulada no canal 1.21.1; o número no nome do projeto não comprova existência de artefato NeoForge.
- **3.1** (22/10/2026, Release **NeoForge 1.21.1**, File ID **9288645**): nome exato do artefato publicado `EpicFight x Curios Compat 3.1.jar`. O changelog da página oficial registra **suporte completo novo** para **Dungeons And Combat** e **Samurai Dynasty**; corrige **Apocalypse Now – Post Apocalyptic Arsenal** quando o personagem está sentado e o item equipado no slot Curios aparece deslocado fora do modelo. Evidência primária: https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat/files/9288645 .

### Impacto de compatibilidade real
- **Render/sentado:** validar alinhamento e offsets de itens Curios enquanto o jogador está sentado, escalado, em posição especial e em battle mode. Não confundir deslocamento do renderer com perda de `ItemStack` física.
- **Novos providers:** testar Dungeons And Combat e Samurai Dynasty **somente se presentes** na modlist atual; o suporte publicado não demonstra que estejam instalados.
- **Mods concorrentes já relacionados no dossier:** Supplementaries quiver, Sophisticated Backpacks, Iron's Spells e outras integrações de accessory/back slots; teste de no-double-render e troca de equipamento, em terceira pessoa, first person, multiplayer, troca de dimensão, respawn e client resource reload.
- **Compatibilidade de API:** 3.1 pode depender de contratos Epic Fight/Curios diferentes dos do JAR 2.2; consultar `neoforge.mods.toml` da distribuição exata e as versões instaladas antes de promover.

**Status de pesquisa:** identidade CurseForge e release 3.1 NeoForge 1.21.1 **confirmadas**, novo conteúdo funcional **registrado**; **não é possível declarar o histórico 2.2→3.1 integral** sem eliminar a ambiguidade da intermediária 3.0 e examinar sua publicação exata NeoForge (se houver). Cumprir fail-closed; **não retirar o item #57 do controle por enquanto**, não instalar nem mudar filename/metadados físicos.

## Reconciliação de todos os JARs NeoForge 1.21.1 — 24/10/2026 (2.2 → 4.0)

**RETIFICAÇÃO da conclusão anterior:** a versão intermediária **3.0 para NeoForge 1.21.1 está inequivocamente publicada** pelo **mesmo projeto CurseForge 1389133**, autor **Unitei**, em **18/10/2026**, File ID **9248354**. Não confundir com o arquivo **Forge 1.20.1 3.0**, presente na mesma data, nem atribuir ao alvo notas de outro loader. A listagem oficial apresenta somente as versões posteriores **3.0 (18/10), 3.1 (22/10) e 4.0 (23/10)** para NeoForge 1.21.1 após o JAR físico 2.2.

**Autoridade física imutável:** posição histórica **#259**, JAR top-level **`Epic Fight x Curios Compat 2.2.jar`** (CurseForge File ID **7865987**), mod ID `epicfight_curios_compat`, **metadata interna runtime 1.4**. O nome externo **2.2** não é a mesma coisa que o valor do metadata **1.4**; nenhuma versão do dossier/JAR foi renomeada. Categorias oficiais CurseForge **Addons + Bug Fixes + Cosmetic**, iguais à pasta atual. A última release externa é **4.0**, mas a última instalação continua **2.2/metadata 1.4**.

### Matriz release-by-release do canal correto

| Release e arquivo NeoForge 1.21.1 | Fonte oficial do artefato | Delta publicado |
|---|---|---|
| **2.2** (baseline de 03/04) `Epic Fight x Curios Compat 2.2.jar` | **7865987** | Corrige digitação no editor Curios e duplicação visual da aljava de Supplementaries. Já registrado no dossier; não confundir com mudança posterior. |
| **3.0**, 18/10/2026 `EpicFight x Curios Compat 3.0.jar` | **9248354** | **Novas integrações com suporte marcado `Full`:** Artifacts, Relics e seus addons, Olympus, Spyglass Improvements, L Ender's Cataclysm, Reliable Backpacks, Create Backtank, Tool Belt, Elytra Slot, Apocalypse Now – Post Apocalyptic Arsenal, Modular BackPacks, Reliquified Iron's Spells 'n Spellbooks, Reliquified Artifacts, Reliquified Twilight Forest, Reliquified L_Ender’s Cataclysm e Reliquified Ars Nouveau. **Correções** de item em Back Weapon Slot e Create que não aparecia no lugar correto, render duplo da aljava Supplementaries, render duplo/posição da L2 Backpack. A descrição contém algumas famílias relacionadas; uma integração declarada **não prova presença física** do mod correspondente no pack. |
| **3.1**, 22/10/2026 `EpicFight x Curios Compat 3.1.jar` | **9288645** | **Adiciona suporte completo a Dungeons And Combat e Samurai Dynasty** e corrige item Curios de Apocalypse Now – Post Apocalyptic Arsenal saindo do personagem enquanto sentado. Não aplicar esta correção a outros providers sem evidência. |
| **4.0**, 23/10/2026 `EpicFight x Curios Compat [NeoForge] 4.0.jar` | **9296370** | **Novo Curios Compat Editor**, anunciado com vídeo de demonstração pelo autor; integrações `Full` com Dungeons And Combat, Samurai Dynasty, **Ydm's Weapon Master, Eidolon: Repraised, Beans Backpacks (Legacy), BountifulBaubles: Reforked, Nameless Trinkets, Beans Backpacks e Back Slots**. Reitera fix do item Curios deslocado em postura sentada com Apocalypse Now; notas 4.0 repetem as duas integrações já citadas em 3.1. A listagem 4.0 repete BountifulBaubles, mas isso não equivale a dois addons distintos. |

**Prova individual do 3.0 exato:** https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat/files/9248354 ; **3.1:** https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat/files/9288645 ; **4.0:** https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat/files/9296370 ; **catálogo NeoForge e comparação entre loaders:** https://www.curseforge.com/minecraft/mc-mods/epicfightcurioscompat/files/all . Cada página confirma Minecraft **1.21.1**, **NeoForge**, nome do JAR, data e File ID; não usar o projeto/branch Forge 1.20.1 como source autoritativo do binário novo.

### Mudanças e bloqueios funcionais no modpack

- **Incerteza preservada da versão *interna*:** o valor metadata `1.4` é comprovado para o JAR **2.2 instalado**, porém não foi lido o `META-INF/neoforge.mods.toml` dos novos JARs 3.0/3.1/4.0. Logo **não afirmar** que metadata 4.0 será literalmente `4.0`, nem que toda a configuração antiga migra sem conversão.
- **Possível conflito com outros renderers:** suporte de Cataclysm, Relics/Artifacts e backpack/Curios deve ser testado com Epic Fight **21.17.3.1**, Curios **9.5.1**, Supplementaries **3.9.9**, Sophisticated Backpacks **3.26.3**, Iron's Spells **3.16.3**, EFIS e EMF Compat: Iron's Spells, conforme inventário histórico do pack. **Não inferir que a revisão substitua os outros addons.**
- **Nova superfície de configuração 4.0:** editor Curios Compat tem comportamento anunciado, mas as notas não demonstram permissões detalhadas para servidores, formato de armazenamento, sincronização de editor ou contratos de classes; esses pontos são QA e inspecção de manifesto, não conclusões documentais.
- **Correção do modo sentado:** testar sentado em entidades, montaria/veículos, display em battle mode, back slot e transições de animação sem itens duplicados ou flutuantes; diferenciar render quebrado de perda de equipamento.
- **Limites de providers:** Dungeons And Combat, Samurai Dynasty, Ydm’s Weapon Master, Eidolon, Beans, BountifulBaubles, Nameless Trinkets, Olympus etc. são **suportes declarados**, e não entradas físicas confirmadas. Não alterar a tabela de mods top-level.

**Testes futuros, NÃO executados:** backup de configs e hashes/JARs; metadata/depends do 3.0/3.1/4.0; smoke-test cliente e dedicated server; editor 4.0 no single-player/servidor com usuário autorizado e não autorizado; troca/edição de slots com várias teclas; quiver de Supplementaries só renderizado uma vez; Artifacts/Relics/Curios/backpacks e Cataclysm em idle/attack/guard/dodge/sit/ride/respawn; multiplayer com dois jogadores, reload de recursos e shader; habilitar somente providers efetivamente presentes.

**Conclusão documental:** o último bloqueio original (existência e changelog de **3.0 NeoForge 1.21.1**) foi resolvido com **File ID e release note do JAR exato**; as publicações **3.1** e a recém-publicada **4.0** também têm changelogs individualizados para o loader correto. **#57 pode ser retirado da fila de pesquisa documental de releases**. **Instalação/promoção para 4.0, compatibilidade efetiva Epic Fight/Curios e QA continuam PENDENTES**. O arquivo `✅-epic-fight-curios-compat v1.4.md` permanece no lugar original, sem alteração de basename ou versão física.


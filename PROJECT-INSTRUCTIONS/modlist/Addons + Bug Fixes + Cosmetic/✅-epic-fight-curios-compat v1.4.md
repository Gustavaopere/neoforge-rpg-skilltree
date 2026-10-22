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


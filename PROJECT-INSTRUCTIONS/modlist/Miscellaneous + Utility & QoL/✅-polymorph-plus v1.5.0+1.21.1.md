# Polymorph+ — 1.5.0+1.21.1 (atualização declarada em 22/11/2026)

> **Versão vigente declarada em 22/11/2026:** `1.5.0+1.21.1`; JAR `polymorph_plus-neoforge-1.5.0+1.21.1.jar`; anterior `1.3.1+1.21.1`. Fonte [CurseForge](https://www.curseforge.com/minecraft/mc-mods/polymorph-plus/files/all). O pedido do usuário atualiza o inventário documental, mesmo que o índice histórico de outros mods ainda não tenha sido sincronizado.

> **Auditoria e limites:** Cadeia publicada 1.3.2, 1.4.0, 1.4.1, 1.4.2 e 1.5.0, com fontes versionadas no dossier. Mudanças em seleção de receitas, favoritos/prioridades, posicionamento do picker, integrações Sophisticated, JEI recipe transfer, Brewing/recipe cache e config da posição/escala do botão. A 1.5.0 NeoForge para MC 1.21.1 consta na listagem CurseForge de 09/11; não confundir com artefatos para Minecraft 26.x. Os trechos antigos “não instalada” são históricos e anteriores à declaração atual; sem verificação de JAR local ou smoke tests.


## Propriedades do registro

- **Mod:** Polymorph+
- **Arquivo JAR instalado — declaração do usuário:** `polymorph_plus-neoforge-1.5.0+1.21.1.jar` ([CurseForge](https://www.curseforge.com/minecraft/mc-mods/polymorph-plus/files/all)).
- **Versão instalada declarada para Minecraft 1.21.1 / NeoForge:** `1.5.0+1.21.1` (anterior `1.3.1+1.21.1`).
- **Categoria:** QoL, Compat
- **Decisão:** Dependência
- **Estado da pesquisa:** releases e source verificados conforme histórico; sem afirmação de homologação de dependências nem execução do jogo.
- **Estado no pack:** `1.5.0+1.21.1` declarado instalado pelo usuário; inventário documental no GitHub sincronizado, sem leitura do JAR do computador.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/polymorph-plus
- **Função:** Expande o sistema de resolução de conflitos entre receitas, oferecendo seleção quando múltiplas receitas usam a mesma combinação de ingredientes.
- **Dependências:** NeoForge 1.21.1. Consumer operacional confirmado no pack: Ars Polymorphia 1.0.3 exige Polymorph; Polymorph+ declara compatibilidade com addons feitos para o Polymorph original. Ars Nouveau também está presente.
- **Compatibilidade/Riscos:** Fork compatível com addons do Polymorph original e explicitamente incompatível com instalar o original junto. Riscos: addon/API drift, recipe reload, server/client selection mismatch e persistência do Crafter.
- **Sobreposição:** Substitui funcionalmente o Polymorph original nesta instalação e não deve coexistir com ele. Ars Polymorphia é integração consumidora, não duplicata.
- **Observações vigentes:** `polymorph_plus-neoforge-1.5.0+1.21.1.jar`, `1.5.0+1.21.1`. JAR anterior, hash e versões citados nas seções de origem ficam como histórico; não são prova contrária à atualização declarada.
- **Procedência:** posição preservada da modlist física histórica; atualização de versão declarada em 22/11/2026; JAR conforme [publicação oficial](https://www.curseforge.com/minecraft/mc-mods/polymorph-plus/files/all). Sem hash local novo.
- **Atualização/Status:** 22/11/2026 — `1.3.1+1.21.1` → `1.5.0+1.21.1` por declaração do usuário. Cadeia publicada 1.3.2, 1.4.0, 1.4.1, 1.4.2 e 1.5.0, com fontes versionadas no dossier. Mudanças em seleção de receitas, favoritos/prioridades, posicionamento do picker, integrações Sophisticated, JEI recipe transfer, Brewing/recipe cache e config da posição/escala do botão. A 1.5.0 NeoForge para MC 1.21.1 consta na listagem CurseForge de 09/11; não confundir com artefatos para Minecraft 26.x.
- **Histórico da decisão:** 2026-09-10 — reclassificado de Sem decisão para Dependência: Ars Polymorphia 1.0.3 está fisicamente instalado e exige Polymorph; Polymorph+ é o único provider instalado e declara compatibilidade com addons do original.
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #448: JAR `polymorph_plus-neoforge-1.3.1+1.21.1.jar`, mod id `polymorph_plus`, runtime `1.3.1+1.21.1`, SHA-1 `ec4793d7272d9b6c568119ce4f1b51e0a419b6af`.

<callout icon="🔎" color="green_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `polymorph_plus-neoforge-1.3.1+1.21.1.jar`, mod id `polymorph_plus`, versão `1.3.1+1.21.1`, NeoForge 1.21.1. Polymorph+ é fork/continuação do Polymorph original, com seleção de resultado quando receitas entram em conflito. O pack também contém **Ars Polymorphia 1.0.3**, que exige Polymorph; como Polymorph+ declara compatibilidade com addons feitos para o original e o original está ausente, esta instalação depende operacionalmente do fork e a decisão passa a **Dependência**.
</callout>
## 1. Identidade e papel
- **Mod:** Polymorph+.
- **JAR físico:** `polymorph_plus-neoforge-1.3.1+1.21.1.jar`.
- **Mod id:** `polymorph_plus`.
- **Runtime:** `1.3.1+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** LGPLv3.
- **Papel:** resolver recipe conflicts permitindo escolher o output desejado quando os mesmos ingredientes satisfazem múltiplas receitas.
- **Decisão:** Dependência.
## 2. Fork do Polymorph original
O projeto declara ser um fork que reproduz as funcionalidades do Polymorph original e acrescenta melhorias. Também declara duas boundaries explícitas:
- compatível com addons feitos para o Polymorph original;
- **incompatível com instalar Polymorph original simultaneamente**.
A modlist atual contém apenas Polymorph+ como implementação top-level desse sistema, portanto não há duplicata original instalada.
## 3. Consumer causal: Ars Polymorphia
O pack físico contém `ars_polymorphia-1.0.3.jar`.
Ars Polymorphia publica como required content:
- Ars Nouveau;
- Polymorph.
Sua função é levar o seletor de recipes ao **Storage Lectern** do Ars Nouveau. Polymorph+ declara compatibilidade com addons feitos para Polymorph original.
Assim, a instalação atual usa Polymorph+ como provider compatível para esse addon; remover o fork quebraria/degradaria a integração presente.
## 4. Recipe conflict selection
Quando um conjunto de ingredientes corresponde a mais de uma receita, o sistema oferece uma UI de seleção para escolher o resultado.
A recipe continua pertencendo ao provider original. Polymorph+ não reescreve a lógica funcional do output; resolve a ambiguidade de escolha.
Testar crafting vanilla, crafting table modded, recipes com tags e recipes dinâmicas.
## 5. Persistência da seleção
O fork publica persistência de seleção para o Crafter block em 1.21+, permitindo redstone automation manter o resultado escolhido.
Isso é uma superfície server-authoritative importante:
- seleção deve persistir com o bloco;
- save/restart não pode resetar indevidamente;
- mudança de ingredientes deve invalidar seleção quando necessário;
- redstone repetido não deve alternar output sozinho.
## 6. UI ampliada do fork
A linha do fork adiciona:
- seletor scrollável quando há mais de sete receitas;
- navegação por setas/mouse wheel;
- painel pinável durante a sessão;
- tutorial de primeira execução.
Essas features são presentation/QoL; a receita escolhida ainda precisa convergir ao servidor.
## 7. Linha 1.3.1
A build física 1.3.1 é Release NeoForge 1.21.1 publicada em 27/08/2026.
A família 1.3.x adiciona melhorias como favoritos/atalhos e refinamentos de layout/preferência. Como a página exata 1.21.1 não publicou aqui um changelog detalhado por alvo além da release family, esta ficha evita atribuir comportamento específico não verificado ao binário quando não necessário.
## 8. Ars Polymorphia / Storage Lectern
Ars Polymorphia adiciona a seleção de conflitos ao terminal do Storage Lectern. A documentação publica que:
- um botão aparece quando há múltiplos resultados;
- a lista permite selecionar o resultado;
- a seleção é lembrada enquanto os ingredientes não mudam;
- 1.0.3 corrige recipes com resultado vazio.
O teste deve garantir que Polymorph+ realmente satisfaz essa compatibilidade publicada na instalação atual.
## 9. Network e server authority
A UI de seleção existe no cliente, mas o output craftado deve ser validado no servidor. Riscos:
- cliente escolher recipe que servidor não reconhece;
- seleção stale após recipe reload;
- container fechado antes de sync;
- spam de seleção sob latência;
- discrepância entre recipe list de cliente/servidor.
## 10. `/reload` e recipe lifecycle
Datapacks/KubeJS/mod updates podem alterar recipes. Após `/reload`:
- selector precisa refletir conjunto atual;
- favorites/prefs inválidos devem falhar de modo controlado;
- seleção persistida para recipe removida não pode gerar output fantasma;
- Ars Storage Lectern deve reconstruir options corretamente.
## 11. Compatibilidade com recipe mods
Em modpack grande, conflitos podem envolver Create, Farmer's Delight, Ars, crafting vanilla e inúmeros addons.
Polymorph+ deve resolver ambiguidade sem assumir ownership dos recipes. Se um recipe está incorreto, investigar o provider; se dois recipes válidos conflitam e selector falha, Polymorph+/integration é a superfície correta.
## 12. Riscos
1. **Fork compatibility:** addon Polymorph precisa funcionar realmente contra Polymorph+.
2. **Original conflict:** instalar Polymorph original junto é explicitamente incompatível.
3. **Recipe reload:** seleção/favorites podem apontar para recipe removida.
4. **Server/client mismatch:** resultado selecionado precisa ser autorizado pelo servidor.
5. **Automation persistence:** Crafter não pode alternar resultado após restart.
6. **Large selector:** muitas receitas podem estressar UI/layout.
7. **Addon drift:** Ars Polymorphia pode esperar API behavior de outra versão.
8. **Recipe provider attribution:** Polymorph+ não deve ser culpado por recipe mal definida.
## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Polymorph+ 1.3.1 + Ars Polymorphia 1.0.3.
- [ ] Duas recipes com mesmos ingredientes exibem selector e craftam a escolhida.
- [ ] Mais de sete conflitos usam scroll sem cortar opções.
- [ ] Crafter persiste seleção após save/restart e redstone automation.
- [ ] Mudança dos ingredientes invalida seleção antiga corretamente.
- [ ] Storage Lectern do Ars mostra selector via Ars Polymorphia.
- [ ] Recipe com output vazio é ignorada pela integração 1.0.3.
- [ ] `/reload` atualiza options sem stale recipes.
- [ ] Latência não permite craft de recipe não autorizada pelo servidor.
- [ ] Polymorph original continua ausente; nenhuma dupla implementação é carregada.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 14. Evidências e limites
- Modlist física: Polymorph+ 1.3.1, mixins de core/compatibility e Ars Polymorphia 1.0.3 presente; Polymorph original ausente.
- CurseForge oficial Polymorph+: Release NeoForge 1.21.1 1.3.1 de 27/08/2026; fork declara compatibilidade com addons do original e conflito com o original instalado junto.
- Ars Polymorphia 1.0.3: required content = Ars Nouveau + Polymorph; função = recipe conflict selection no Storage Lectern; 1.0.3 ignora recipes com output vazio.
- **Limite:** a compatibilidade publicada do fork precisa ser confirmada por smoke real da dupla Ars Polymorphia↔Polymorph+; a classificação Dependência registra a relação operacional pretendida, não inventa equivalência de mod id interna.

## Release e compatibilidade — reauditoria 13/10/2026

**Atualização upstream NeoForge 1.21.1:** 1.3.2+1.21.1. **Fonte oficial:** https://www.curseforge.com/minecraft/mc-mods/polymorph-plus/files/all.

Release NeoForge 1.21.1 em 12/10/2026: `polymorph_plus-neoforge-1.3.2+1.21.1.jar`, comparada ao último físico 1.3.1. Não confundir com 1.3.6 de Minecraft 26.x. Sem changelog técnico de 1.3.2, não declarar conserto de UI nem novos métodos de seleção. O mod resolve conflitos de receitas, com `recipe choice` para o player; garantir que JEI/EMI, crafting tables modded, receitas KubeJS/Datapacks, item components e multiplayer server-authoritative ainda selecionam a saída correta, sem duplicação por reabrir GUI ou trocar recipe.

**Instalação física:** não revalidada desde `modlist(1).txt`; versão instalada histórica e filename versionado com ✅ preservados. Sem substituição de JAR, execução in-game ou confirmação de compatibilidade plena nesta atualização documental. Não promover a versão upstream à coluna física do índice.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Polymorph+
- **Último candidato 1.21.1 / NeoForge localizado:** **1.4.1 (Release NeoForge 1.21.1)**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** A linha nova inclui compat/fix para JEI 19.52+ e seleção '+'. Coordenar com JEI candidato; testar recipe conflicts, GUI, networking e reload.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/polymorph-plus

## Auditoria cumulativa Polymorph+ 1.3.1 → 1.4.1 — lote 31–40 (22/10/2026)

**Instalado:** Polymorph+ fork de Aminoquiz, `1.3.1+1.21.1` NeoForge Minecraft 1.21.1. **Nova Release comprovada:** `1.4.1+1.21.1-neoforge`, Modrinth version ID `8N82mraG`; não confundir com projeto Polymorph original nem com Polymorph+ 1.4.1 de Minecraft 26.x.

| Publicação | Delta que pode ser atribuído com segurança |
|---|---|
| **1.3.2** | JAR NeoForge 1.21.1 publicado (12/10, revisão anterior do dossier). O catálogo histórico não dispõe de changelog detalhado individual pinado; não se deve inventar novos recursos. |
| **1.4.0** | Há changelog público da linha 26.x com Traveler's Backpack picker, prioridade e favoritos nas crafting upgrades do Sophisticated e ajustes de tutorial/GUI. **Esse arquivo 26.x não prova que tudo foi entregue num artefato 1.21.1**; o arquivo intermediário específico 1.21.1 e sua atribuição permanecem não verificados. |
| **1.4.1 (NeoForge 1.21.1)** | **Corrige crash de inicialização com JEI 19.52+**; integração do botão `+` do JEI passa a selecionar diretamente a receita clicada. Fonte Modrinth 1.4.1+1.21.1-neoforge, não notas de outra versão do Minecraft. |

**Gate:** JEI físico 19.56.0.440 já na faixa mencionada mas isso não prova que o crash se reproduziu; validar a versão exata junto a Ars Polymorphia, crafting grids vanilla e modded, KubeJS/datapacks, favoritos e prioridade entre players, recipes/remainders sem perda/duplicação, multiplayer autoritativo e /reload. **Revisão cumulativa agora resolvida por commits versionados específicos da branch 1.21.1**, documentados no adendo de reconciliação abaixo. O status de cada binário instalado versus não instalado é preservado. A versão física não foi mudada.

Fonte principal: https://modrinth.com/mod/polymorph_plus/version/1.4.1%2B1.21.1-neoforge ; https://www.curseforge.com/minecraft/mc-mods/polymorph-plus/files/all .

## Fechamento da lacuna 1.3.2/1.4.0 — fonte versionada 1.21.1 (22/10/2026)

**Código upstream público pinado na branch oficial `1.21.1`** — não usar o changelog de Minecraft 26.x como substituto. A cronologia do código-fonte não equivale automaticamente à data de upload de cada JAR, e não comprova hashes publicados.

| Revisão de source | Conteúdo verificado nos commits do projeto correto |
|---|---|
| **1.3.2**, commit `75d77ebd228cbb3594f4dc9a88ad213025e84003` (28/08) | O selector de resultado rolável volta a ficar **acima da GUI** quando possível. 1.3.0 havia ancorado o widget ao botão para acomodar terminais AE2 altos, mas o posicionava 12 pixels para dentro das GUIs vanilla; agora anchor acima-da-GUI é preferido quando cabe, e o do botão serve de fallback. |
| **1.4.0**, commit `65c9a02a6abdc94443f7e3a5005af4747c6a4cdc` (14/10) | Suporte a crafting terminal de **Tom's Simple Storage**, **FastFurnace**, **FastSuite**, Simple Storage Network crafting table/remote, **Cyclic workbench** e prioridade/favoritos nas crafting upgrades de **Sophisticated Backpacks e Storage**, inclusive escolha pelas setas lembrada. **QuickBench support é Fabric-only**. Fixes: tutorial por cima das listas JEI/REI, carried stack/tooltips visíveis sobre picker, FastWorkbench sem escolha obsoleta após mudar grid, seleção no FastFurnace, botão no Traveler's Backpack. Preferência de modpack ignorada no Fabric é fix **Fabric-only**. O commit efetivamente altera módulos `neoforge/` além de common; não é código exclusivo 26.x. |
| **1.4.1**, commit `597f6c4015c93bf6f9e486fb0beff264b3ba56dc` (17/10) | Integra JEI por **recipe transfer listener API**, removendo mixins antigos de transfer e handshake próprio; corrige fluxo do botão `+`. Changelog público do JAR NeoForge 1.21.1 cita fix do crash com JEI 19.52+. |

**Ressalva de publicação:** versões/fixes 1.3.2, 1.4.0 e 1.4.1 estão agora **auditadas no source de versão correta**; 1.4.0 foi identificado como milestone de código, mas o registro de download de um JAR 1.4.0 NeoForge 1.21.1 **não foi individualizado**. O commit 1.4.2 (18/10) existe no source, mas **não é incorporado à lista de releases sem binário confirmado**. Esta limitação afeta a validação do upgrade físico, não a cobertura documental dos deltas públicos. **Auditoria documental da cadeia concluída por release/source; QA de build, ABI e runtime pendente**.

**Fontes source pinadas:** https://github.com/Aminoquiz/polymorph_plus/commit/75d77ebd228cbb3594f4dc9a88ad213025e84003 ; https://github.com/Aminoquiz/polymorph_plus/commit/65c9a02a6abdc94443f7e3a5005af4747c6a4cdc ; https://github.com/Aminoquiz/polymorph_plus/commit/597f6c4015c93bf6f9e486fb0beff264b3ba56dc ; artefato https://modrinth.com/mod/polymorph_plus/version/1.4.1%2B1.21.1-neoforge .

## Auditoria incremental de 18/11/2026 — source 1.4.2, 1.4.3 e 1.5.0

**Autoridade física:** #448, `polymorph_plus-neoforge-1.3.1+1.21.1.jar`, mod ID `polymorph_plus`, runtime 1.3.1+1.21.1. Projeto correto Aminoquiz / CurseForge 1586874; categorias **Miscellaneous + Utility & QoL**. A publicação 1.4.0+1.21.1 NeoForge anteriormente não individualizada agora está demonstrada na [ficha Modrinth CgbHLpne](https://modrinth.com/mod/polymorph_plus/version/CgbHLpne); seus deltas previamente registrados no source estão confirmados. A 1.4.1 permanece comprovada por [Modrinth 8N82mraG](https://modrinth.com/mod/polymorph_plus/version/1.4.1%2B1.21.1-neoforge).

| Etapa versionada na branch oficial 1.21.1 | Alterações verificadas no source |
|---|---|
| **1.4.2**, 18/10/2026, [5982856](https://github.com/Aminoquiz/polymorph_plus/commit/5982856670752ed8388208d8e66dcf0a5beaebc7) | Seletor de brewing, configuração do botão, mudanças de recipe transfer JEI/REI e integração de Crafting Slots, Crafting Station, Better Furnaces Reforged e Iron Furnaces; alterações de cache, dados de recipes persistentes e networking. O commit altera explicitamente os módulos common e NeoForge e define a versão exata. |
| **1.4.3**, 08/11/2026, [7f16766](https://github.com/Aminoquiz/polymorph_plus/commit/7f16766c97195bd4462477a0000a6e09e95caa8e) | Correção anunciada de entrada com Carpet, ajustes de integrações Better Furnaces e InvCrafting, mixin plugin e serviços de plataforma. O commit define 1.4.3+1.21.1; não há confirmação de um JAR público independente dessa etapa. |
| **1.5.0**, 09/11/2026, [be85539](https://github.com/Aminoquiz/polymorph_plus/commit/be85539c823f9756dccf7481b57a712ee9886159) | Seletor in-world para campfires por slot e Overgeared (commit [53cb9cf](https://github.com/Aminoquiz/polymorph_plus/commit/53cb9cf3732483b27328425f48ab183ee04ad207)); ajuste do alias de dependência Polymorph ([a26eb03](https://github.com/Aminoquiz/polymorph_plus/commit/a26eb0391a68f78cb1e759285aa7305767102c94)), integração de casting Hephaestus ([b0a4020](https://github.com/Aminoquiz/polymorph_plus/commit/b0a4020139872f228170518e828e4d70fa2848a1)) e versionamento dos pacotes da seleção in-world ([9df2e53](https://github.com/Aminoquiz/polymorph_plus/commit/9df2e538055dca59b10893e790303988135f8218)). Os commits são do alvo 1.21.1, não da linha 26.x. Não presumir que cada integração está ativa no pack. |

**Limite documental e de distribuição:** os commits 1.4.2–1.5.0 e mudanças correspondentes estão comprovados na branch oficial. Índices secundários apontam os arquivos CurseForge 9242741 (1.4.2) e 9531210 (1.5.0), mas as fichas oficiais exatas não puderam ser recuperadas nesta consulta. A enumeração completa das publicações NeoForge 1.21.1 após 1.4.1 não está fechada; não declarar versão mais recente publicada como confirmada só pelo source. **PENDENTE** confirmar inventário público e arquivos/changelogs correspondentes. Instalação histórica e caminho inalterados. QA: brewing, campfire por slot, receitas removidas/reload, recipe transfer, alias para Ars Polymorphia, JEI, networking client/server e conservação de itens/fluids. Nenhum teste foi realizado.

## Fechamento do catálogo publicado 1.3.2–1.5.0 — 18/11/2026

**Autoridade física:** #448 `polymorph_plus-neoforge-1.3.1+1.21.1.jar`; o registro Modrinth XKcrDMnD confirma SHA-1 histórico `ec4793d7272d9b6c568119ce4f1b51e0a419b6af`. A [API oficial do projeto 3uT4J18I, filtrada para NeoForge 1.21.1](https://api.modrinth.com/v2/project/3uT4J18I/version?loaders=%5B%22neoforge%22%5D&game_versions=%5B%221.21.1%22%5D) devolve oito registros, cinco posteriores à instalada. [CurseForge projeto 1586874](https://www.curseforge.com/minecraft/mc-mods/polymorph-plus/license) mantém Miscellaneous + Utility & QoL. As fichas CurseForge individuais mais novas não abriram nesta sessão; inventário e changelogs foram obtidos do mesmo autor pelo catálogo Modrinth, não de indexador secundário.

| Versão NeoForge 1.21.1 | Data UTC | Mudanças confirmadas na publicação oficial |
|---|---|---|
| [1.3.2](https://modrinth.com/mod/polymorph_plus/version/2exvsyUu) | 12/10/2026 | Posicionamento do seletor acima da janela; corrige `/polymorph conflicts` travado em dedicado com alguns mods e perda da preferência de modpack ao criar mundo singleplayer. |
| [1.4.0](https://modrinth.com/mod/polymorph_plus/version/CgbHLpne) | 13/10/2026 | Tom's Simple Storage, FastFurnace/FastSuite, Simple Storage Network, Cyclic, favoritos e prioridade nas crafting upgrades Sophisticated; correções de picker, overlays e seleção em FastWorkbench/FastFurnace/Traveler's Backpack. QuickBench e fix da preferência Fabric são exclusivos desse loader. |
| [1.4.1](https://modrinth.com/mod/polymorph_plus/version/8N82mraG) | 17/10/2026 | Corrige crash com JEI 19.52+ e botão + seleciona a receita clicada. |
| [1.4.2](https://modrinth.com/mod/polymorph_plus/version/35rd3PLa) | 18/10/2026 | Seletor no brewing stand; `buttonOffsetX`, `buttonOffsetY`, `buttonScale` (0,5–4); shapeless conflicts independentes da ordem dos ingredientes; fix de resultado vazio em Oritech e de tipo de receita de fan blasting/smoking do Create; suporta Iron Furnaces, Better Furnaces Reforged, Crafting Station e Crafting Slots. |
| [1.5.0](https://modrinth.com/mod/polymorph_plus/version/63hk99pO) | 09/11/2026 | Escolha de resultado por slot de campfire via botão lateral, escolha antes de casting em tables/basins de Hephaestus, corrige sobreposição do botão no Smithing Anvil de Overgeared. Autor exige atualização cliente e servidor. |

A 1.4.3 existe como commit versionado (08/11), já examinado acima, mas não como artefato separado no catálogo Modrinth desse alvo; não equipará-la a release publicada. Confirmada a distribuição final `polymorph_plus-neoforge-1.5.0+1.21.1.jar`, SHA-1 `57bba84d3ba77356e36a7c4fdf1ea325252663aa`, não instalada. Manter Ars Polymorphia e Polymorph+ como relação operacional registrada, testar alias de dependência e receitas/itens/fluidos em todos os containers presentes, JEI, Create fan, KubeJS, datapacks, config/UI, reload, seleção autoritativa e sincronização. **Auditoria documental da cadeia publicada e source intermediário concluída; QA e instalação pendentes.** Nome, versão histórica e pasta preservados.

## Sincronização documental declarada — 22/11/2026

**Antes:** `1.3.1+1.21.1`. **Agora, conforme usuário:** `1.5.0+1.21.1`, distribuição `polymorph_plus-neoforge-1.5.0+1.21.1.jar` ([fonte oficial](https://www.curseforge.com/minecraft/mc-mods/polymorph-plus/files/all)).

**Mudanças e requisitos:** Cadeia publicada 1.3.2, 1.4.0, 1.4.1, 1.4.2 e 1.5.0, com fontes versionadas no dossier. Mudanças em seleção de receitas, favoritos/prioridades, posicionamento do picker, integrações Sophisticated, JEI recipe transfer, Brewing/recipe cache e config da posição/escala do botão. A 1.5.0 NeoForge para MC 1.21.1 consta na listagem CurseForge de 09/11; não confundir com artefatos para Minecraft 26.x.

Não alterar dependências cujos próprios dossiers ainda aguardam lote posterior; registrar diferenças como pendência de sincronização, sem afirmar que a combinação foi executada ou testada.

# The Brazilian Localization Project [PT-BR]

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `The Brazilian Project [1.21.1-1.3.0].zip`
- **Versão 1.21.1:** 1.3.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra captura física da pasta Resource Packs do perfil em 08/09/2026 como evidência de instalação de `The Brazilian Project [1.21.1-1.3.0].zip`.
- A modlist física JAR-centric de 08/09/2026 não constitui prova independente da presença do `.zip`.
- O pack controla somente recursos de localização/strings; cada mod permanece authority integral de registries, recipes, stats, AI, networking e demais mecânicas.

## Propriedades do banco

- **Mod:** The Brazilian Localization Project [PT-BR]
- **Arquivo JAR:** `The Brazilian Project [1.21.1-1.3.0].zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 1.3.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Resource pack de localização PT-BR para Minecraft modded que preenche traduções ausentes e revisa inconsistências/terminologia de traduções existentes.
- **Dependências:** Minecraft 1.21.1 e os mods cujas localization keys o pack traduz. Não cria dependência funcional; ausência de tradução deve cair em chave/idioma fallback sem alterar gameplay.
- **Sobreposição:** Pode substituir `pt_br.json`/localization resources fornecidos pelos próprios mods ou por outros translation packs. A prioridade de resource pack determina qual string vence por chave.
- **Compatibilidade/Riscos:** Pack de tradução amplo, mas não equivale a 100% de cobertura de todos os mods instalados. Pode sobrescrever traduções PT-BR nativas por prioridade e ficar desatualizado quando mods adicionam/renomeiam localization keys.
- **Observações:** Arquivo instalado `The Brazilian Project [1.21.1-1.3.0].zip`, versão 1.3.0. A lista oficial 1.21.1 cobre muitos mods presentes no pack, mas não autoriza afirmar cobertura total da modlist.
- **Procedência:** CurseForge oficial The Brazilian Localization Project 1.3.0 para 1.21.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física atual.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/brazilian-project
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Brazilian Localization Project 1.3.0, PT-BR modded, coverage boundary, language-resource priority, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado:** `The Brazilian Project [1.21.1-1.3.0].zip`, versão `1.3.0`, publicado para Minecraft 1.21.1.

## 1. Papel e authority
The Brazilian Localization Project fornece localização **Português do Brasil** para mods, preenchendo traduções ausentes e revisando inconsistências, terminologia e naturalidade onde o projeto possui cobertura. Os mods continuam authorities de conteúdo e gameplay.

## 2. Cobertura confirmada
A lista oficial 1.21.1 é ampla e inclui vários mods presentes neste pack, como Alex's Mobs, Apotheosis/Apothic modules, Ars Nouveau e addons, Artifacts, Balm, Better Combat, BlockUI, Bookshelf, Bosses' Rise, Cosmetic Armor Reworked e Curios, entre muitos outros. **Lista ampla não significa 100% da modlist.**

## 3. Modelo de localização
O pack fornece/substitui localization resources, tipicamente chaves `pt_br`. Quando uma mesma chave existe no mod e neste pack, a prioridade efetiva dos resources determina qual texto é exibido.

## 4. Boundary funcional
Tradução ausente, incorreta ou desatualizada deve resultar em texto fallback/chave sem tradução; não deve mudar registry IDs, recipes, stats, AI, networking ou lógica de qualquer mod.

## 5. Atualização e drift
Mods atualizados podem adicionar/renomear localization keys antes que o projeto 1.3.0 as cubra. O catálogo não presume que uma entrada oficial de suporte esteja completa para toda versão posterior do mod.

## 6. Sobreposição e riscos
1. Tradução do mod ser mais nova que a versão do pack.
2. Outro pack PT-BR sobrescrever a mesma chave.
3. Terminologia ficar inconsistente entre mods integrados.
4. Chave nova aparecer em inglês ou como literal.
5. Tradução alterar sentido técnico de configuração/tooltips sem alterar o valor real.

## 7. Matriz de testes
- [ ] Configurar idioma para Português do Brasil.
- [ ] Amostrar menus, items, tooltips e mensagens de vários mods suportados.
- [ ] Conferir termos técnicos recorrentes entre Ars, Apotheosis e RPG/combat mods.
- [ ] Identificar chaves faltantes/inglesas após updates recentes.
- [ ] Comparar prioridade contra outros translation packs, se presentes.
- [ ] Resource reload/relog sem erro de language JSON.
- [ ] Confirmar que pack on/off não altera qualquer dado de gameplay.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma a versão 1.3.0 para 1.21.1, o objetivo de completar PT-BR modded e a extensa lista de mods cobertos. O catálogo não extrapola essa lista para cobertura total do perfil.

> Boundary canônico: **o projeto controla somente strings/localização; cada mod continua controlando integralmente seu conteúdo e comportamento**.

## 9. Atualização upstream 1.3.0 → 1.4.0 — 10/10/2026

**Autoridade física histórica:** `The Brazilian Project [1.21.1-1.3.0].zip` conforme captura de Resource Packs do perfil em 08/09/2026. Essa versão não foi substituída nem certificada mediante novo ZIP nesta auditoria.

**Publicação oficial:** CurseForge `The Brazilian Project [1.21.1-1.4.0].zip`, **07/10/2026**, file ID **9085941**, compatível com Minecraft 1.21.1. Não foi localizada publicação `1.3.1` para **Minecraft 1.21.1** na fonte consultada; essa numeração existe em **Minecraft 26.1.2** e não deve ser inserida artificialmente na cadeia desta linha. A sequência comprovada para 1.21.1 desde o ZIP físico é **1.3.0 → 1.4.0**.

### Changelog publicado pelo autor da 1.4.0
- **Suporte a 231 mods para Minecraft 1.21.1**, conforme declaração literal do autor. Não inferir que os 231 sejam necessariamente novos em relação à 1.3.0, porque a lista de cobertura anterior não foi comparada item a item.
- **Mods atualizados** (traduções revisadas) e correções de **typos, bugs e ajustes diversos**.
- A relação nominal de novos projetos anunciados inclui entradas diretamente relevantes ao pack e/ou sua documentação, como **Aeronautics Curios Compat**, **Ars Nouveau Flavors Delight**, **Create Aeronautics Gadgets & Gizmos**, **Create Nuclear**, **Just Enough Items**, **Sophisticated Backpacks**, **Sophisticated Core**, **Sable** e outros. **MineColonies não foi localizado na lista nominal da release** e não deve ser tratado como cobertura confirmada. Não garantir que todas as traduções estejam completas nem que os mods instalados tenham a mesma versão da linha-alvo da tradução.

### Integração com o pack
A alteração é de **arquivos de idioma, tooltips e textos de UI** e não modifica AI, damage, recipes, registries, configs efetivos ou persistência server-side. A última modlist física contém diversas atualizações documentais upstream não instaladas; os textos de 1.4.0 podem antecipar ou não cobrir translation keys específicas de novas versões. Em ordem de prioridade, testar pack 1.4.0 acima/abaixo de outros packs pt_BR, abrir as interfaces de Create, Ars, MineColonies, JEI, Sophisticated e quests; conferir placeholders `%s`, format arguments, JSON válido, keys cruas em inglês, texto truncado, tooltips, HUD, tela de configs e recuperação após F3+T. **Não confundir prioridade visual com autoridade de dados de gameplay.**

**Fonte oficial:** https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9085941

**Estado:** última publicação 1.4.0 registrada; **ZIP físico histórico 1.3.0 preservado**, testes pendentes.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum arquivo físico do pack, filename documental certificado, posição de inventário, configuração ou save foi alterado.

- **Projeto solicitado:** The Brazilian Localization Project [PT-BR]
- **Último candidato compatível localizado:** **1.5.0 para Minecraft 1.21.1 (Release, 20/10)**
- **Estado:** **CANDIDATO DE RESOURCE PACK**
- **Gate de promoção:** Validar idioma PT-BR, chaves sobrescritas, traduções de mods presentes, fallback en_us, ordem do pack e ausência de strings quebradas.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/texture-packs/brazilian-project


## Auditoria incremental 1.3.0 → 1.4.0 → 1.5.0 — 22/10/2026

**Autoridade física:** `The Brazilian Project [1.21.1-1.3.0].zip` permanece o arquivo certificado, sem nova cópia ou SHA. A cadeia de arquivos da **linha 1.21.1**, conferida no [CurseForge](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/all?version=1.21.1), é **1.3.0 (07/05/2026) → 1.4.0 (07/10/2026) → 1.5.0 (20/10/2026)**. A 1.3.1 da família 26.1.2 não é intermediária 1.21.1.

| Versão publicada | Conteúdo documental comprovado |
|---|---|
| `1.4.0` | O autor declara suporte a **231 mods** na linha 1.21.1; tradução/revisão de mods, correções de terminologia e typos. O inventário nominal e riscos próprios dessa release já estão detalhados na seção anterior. O número não significa incremento líquido sobre 1.3.0. |
| `1.5.0` | O [changelog do arquivo `9263820`](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9263820) declara **383 mods e 101 guidebooks** com suporte em MC 1.21.1, suporte orientado a modpacks (BMC5, All The Mons, ATM10 e ATM10 Aeronautics, FTB Skies 2 Aero, Infinity Legacy II), revisões de traduções, correções de typos e bugs. O autor não publica diff chave-a-chave `1.4.0→1.5.0`: números são **cobertura total declarada**, não exatamente "152 mods novos" ou garantia de completude. |

**Interseções explícitas com esta modlist na lista nominal de novos suportes de `1.5.0`:** **MineColonies, AdvancedCoreInfo, AdvancedLootInfo, AzureLib, Cyclops Core, Collective, Entity Model Features, Entity Texture Features, Euphoria Patcher, 3d-Skin-Layers, oωo**, entre outros. Essas entradas representam **nomes anunciados pelo autor**; conferir a identificação de cada projeto e os namespaces reais `assets/*/lang/pt_br.json` antes de concluir que a tradução está completa ou coincide com a versão instalada. A observação histórica de que *MineColonies não consta na lista nominal de 1.4.0* continua válida **para 1.4.0**, mas não deve ser estendida a 1.5.0.

**Gates prioritários:** comparar ZIPs e JSONs `1.3.0→1.4.0→1.5.0` em staging; validar chaves com os JARs instalados e `en_us.json`; `%s` e índices posicionais, escapes UTF-8, JSON, livro/gui de Create Aeronautics, MineColonies, JEI, Sophisticated e APIs; verificar precedência de outros packs de tradução após F3+T. A tradução pode alterar strings sem alterar o comportamento do jogo. **1.5.0 segue candidata documental, não instalada nem aprovada**.

**Fontes:** [arquivo oficial 1.5.0 — CurseForge ID 9263820](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9263820); [arquivo 1.4.0 — ID 9085941](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9085941); [catálogo de releases 1.21.1](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/all?version=1.21.1).

## Complemento 1.5.2 para Minecraft 1.21.1 — 24/10/2026

**Correção de latest:** a conclusão anterior terminava em `1.5.0` (20/10), mas o **mesmo projeto CurseForge ID 1465500** publicou em **23/10/2026** o **arquivo exato `The Brazilian Project [1.21.1-1.5.2].zip`**, **Release**, ID **9300904**, com suporte declarado a **Minecraft 1.21.1/1.21** e aproximadamente **8,1 MB**. O [arquivo oficial](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9300904) substitui `1.5.0` (ID 9263820, cerca de 7,8 MB) como candidato upstream. **Nenhum ZIP foi instalado, renomeado ou comparado em binário**; a autoridade física histórica continua `The Brazilian Project [1.21.1-1.3.0].zip`, versão `1.3.0`.

**Sequência aplicável ao MC 1.21.1, sem inventar releases ausentes:** `1.3.0 → 1.4.0 → 1.5.0 → 1.5.2`. **Não foi identificado artefato separado `1.5.1` para 1.21.1** no [catálogo oficial consultado](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/all?version=1.21.1); não deduzir que tal ZIP existiu apenas pelo salto numérico. A série `1.3.1` publicada para Minecraft 26.1.2 tampouco pertence a esse percurso.

### Evidência direta da 1.5.2 e limite do delta exclusivo

A **página do arquivo exato 9300904** reproduz a declaração do autor de **383 mods, 101 guidebooks**, as seis famílias de modpacks suportados (BMC5, All The Mons, ATM10, ATM10 Aeronautics, FTB Skies 2 Aero, Infinity Legacy II), atualização de traduções, ajustes gerais e a **longa relação nominal de projetos**, já encontrada nas notas da `1.5.0`. **Esses totais e nomes são descrições cumulativas de cobertura, não prova de novidades exclusivas da `1.5.2`**. A diferença de tamanho aproximado entre os arquivos `1.5.0` e `1.5.2` **não permite atribuir novos idiomas, mods, chaves JSON ou correções específicas sem um diff dos ZIPs exatos**. Até a fonte consultada, **o delta `1.5.0 → 1.5.2` permanece não discriminado**.

**Risco documental concreto:** atualizar diretamente um pack de traduções de `1.3.0` para `1.5.2` pode alterar/substituir arquivos `assets/<namespace>/lang/pt_br.json`, placeholders/argumentos, textos de menus e guidebooks. São superfícies a comparar, **não bugs observados**. Nenhuma mudança de receitas, registries ou semântica server-side é inferida de uma atualização apenas de recursos de idioma. Antes de homologar a versão candidata: arquivar hashes e `pack.mcmeta` de `1.3.0`, `1.4.0`, `1.5.0` e `1.5.2`; inventariar caminhos ZIP e fazer diff chave-a-chave, inclusive `%s`, índices posicionais e formatação de componentes; cruzar namespaces/nomes dos mods presentes; testar prioridade em `options.txt`, menus de MineColonies/JEI/Create/Sophisticated, guidebooks, `F3+T`, relog e fallback `en_us`. Só depois pode haver decisão de promoção do ZIP.

**Estado fail-closed:** o arquivo publicado `1.5.2` está identificado e sua release note está registrada, mas **não há diff específico `1.5.0→1.5.2` nem QA**, portanto **Resource Pack #1 permanece na lista**. Não mudar o basename certificado nem a coluna de versão física no índice.

## Verificação do source oficial da 1.5.2 — 24/10/2026

**Identidade inequívoca:** o projeto CurseForge **1465500** lista a [release 1.21.1-1.5.2, File ID 9300904](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9300904), publicada em 23/10/2026. A página principal do mesmo projeto fornece como URL de código-fonte **[L2-01/Brazilian-Project](https://github.com/L2-01/Brazilian-Project)**. Isso permite testar se a nota de release cumulativa está acompanhada de uma snapshot rastreável.

**Re-fetch direto da branch `main` do upstream (24/10/2026):** HEAD `8f265cbefafae8e6534f0d0999f86b1444423ecc`; os diretórios top-level disponíveis estão identificados como `The Brazilian Project [1.19.2-1.5.0]`, `The Brazilian Project [1.20.1-1.5.0]`, `The Brazilian Project [1.21.1-1.5.0]` e `The Brazilian Project [26.1.2-1.5.0]`. **Não existe uma árvore top-level `1.21.1-1.5.2` nessa branch no momento da consulta.** Isso não invalida a publicação CurseForge, nem prova que a fonte seja privada/inexistente ou que 1.5.2 seja igual a 1.5.0: comprova somente que **essa branch atual não expõe uma revisão explícita 1.5.2 apta a diff direto por pastas**.

**Critério de liberação do Resource Pack #1:** recuperar os **ZIPs exatos 1.5.0 e 1.5.2** (e o histórico 1.3.0/1.4.0 para a auditoria cumulativa), comparar árvore `assets/`, chaves PT-BR, guidebooks, metadata e SHA, ou obter tag/commit upstream que identifique inequivocamente ambos os builds. Enquanto isso, as contagens `383 mods / 101 guidebooks` compartilhadas pelas notas de 1.5.0 e 1.5.2 são **claims cumulativos do autor**, não mudanças `1.5.0→1.5.2`. Ainda **não apagar o Resource Pack #1**. Sem instalação, troca de ZIP, reclassificação ou mudança de versão física.

## Retificação da disponibilidade CurseForge da 1.5.0 — 24/10/2026

**Motivo da retificação:** o histórico deste dossier registra `1.5.0` como um ZIP oficial identificável pelo **File ID CurseForge 9263820** e o inclui na sequência de distribuição `1.3.0 → 1.4.0 → 1.5.0 → 1.5.2`. **Essa afirmação anterior não pode mais ser verificada no catálogo público atual:** em nova consulta direta à [listagem CurseForge filtrada para MC 1.21.1](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/all?page=1&pageSize=30&version=1.21.1), o projeto **1465500** apresenta **seis** arquivos no total para essa versão de Minecraft, a saber `1.0.0`, `1.1.0`, `1.2.0`, `1.3.0`, `1.4.0` e **`1.5.2`**, **sem `1.5.0` e sem `1.5.1`**. A tentativa de reabrir [9263820](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9263820) retorna **HTTP/página 404**. Isso **não prova que o ZIP 1.5.0 nunca existiu ou jamais foi publicado**; ele pode ter sido deslistado, removido ou estar inacessível. O registro histórico não pode ser empregado como prova atual de sua publicação/distribuição nem de seu tamanho ~7,8 MB.

**Distinção entre fonte e release distribuída:** o [GitHub-fonte oficial `L2-01/Brazilian-Project`](https://github.com/L2-01/Brazilian-Project) foi novamente consultado: branch `main`, commit **`8f265cbefafae8e6534f0d0999f86b1444423ecc`**, com diretório top-level **`The Brazilian Project [1.21.1-1.5.0]`**. **A existência de pasta de fonte etiquetada 1.5.0 não estabelece identidade, hash ou disponibilidade de um ZIP 1.5.0 no CurseForge**, tampouco prova que ela representa precisamente o conteúdo usado no arquivo 1.5.2. Continua sem uma árvore `1.21.1-1.5.2` nessa branch.

### Matriz de evidências atual, sem extrapolação

| Versão de MC 1.21.1 | Autoridade exata reconsultada | Estado |
|---|---|---|
| **1.3.0 INSTALADA** | [CurseForge 8051220](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/8051220) / `The Brazilian Project [1.21.1-1.3.0].zip` | ZIP físico do snapshot, **não atualizado**. |
| **1.4.0** | [CurseForge 9085941](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9085941) / `The Brazilian Project [1.21.1-1.4.0].zip` | Release de 07/10/2026, **3,5 MB**; notas da página declaram suporte a **231 mods**, atualização geral e correção de textos; há lista nominal de mods. |
| **1.5.0** | `main` do GitHub oficial contém diretório de source com esse nome; **a URL CurseForge 9263820 está em 404** | **Source tree confirmado**, artefato ZIP específico **NÃO acessível / NÃO revalidado** no catálogo. Não atribuir a essa distribuição atual a cobertura de 383 mods e 101 guidebooks sem outra prova preservada. |
| **1.5.2** | [CurseForge 9300904](https://www.curseforge.com/minecraft/texture-packs/brazilian-project/files/9300904) / `The Brazilian Project [1.21.1-1.5.2].zip` | Release **23/10/2026**, **8,1 MB**, suporte a **383 mods e 101 guidebooks** declarados **na própria página da 1.5.2**. |
| **1.5.1** | Ausente dos seis arquivos oficiais listados para MC 1.21.1 | Não inventar artefato intermediário ou delta próprio. |

**Sequência de distribuições que pode ser corroborada diretamente no catálogo atual a partir da física:** `1.3.0 → 1.4.0 → 1.5.2`. A referência `1.5.0` passa a ser **marco documental/de fonte cuja distribuição não é verificável**, não um ZIP obrigatório hipotético. O salto de **231 mods declarados na ficha 1.4.0** para **383 mods e 101 guidebooks declarados na ficha 1.5.2** é **diferença entre totais anunciados pelo autor**, e **não** prova a adição exata de 152 traduções de mods nem das chaves específicas. Não comparar automaticamente contagens de releases que podem ter critérios de catalogação diferentes.

**Plano tecnicamente realizável com artefatos publicados hoje:** obter os ZIPs oficiais `1.3.0` (8051220), `1.4.0` (9085941) e `1.5.2` (9300904), conferir SHA/estrutura interna, `pack.mcmeta`, guidebooks, namespaces `assets/*/lang/pt_br.json`, divergências de chaves e placeholders, redundância/prioridade com demais Resource Packs e QA em staging. Opcionalmente comparar a árvore-fonte pública `1.5.0`, **indicando-a claramente como source não vinculado ao ZIP distribuído**. Não foi possível recuperar os arquivos ZIP para comparar bytes nesta rodada. **O Resource Pack #1 continua na lista, sem promoção da versão física.** As afirmações históricas acima substituem apenas aquilo que puder ser sustentado por uma fonte atual e exata; o restante das notas antigas permanece como registro da auditoria anterior.

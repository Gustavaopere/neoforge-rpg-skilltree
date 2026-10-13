# Sophisticated Backpacks: Ars Compat — Ars Nouveau Compatibility

> **CORREÇÃO DE IDENTIDADE DOCUMENTAL — 13/10/2026.** O inventário histórico registra na posição física **#054** o JAR `arssophisticatedcompat-0.3.0.jar`, mod ID atribuído no snapshot `arssophisticatedcompat`, versão runtime `0.3.0` e SHA-1 histórico `7cc6c1e1d92d109230f68b6a63dc4bfb13c245a6`. Esse filename corresponde **exatamente** ao arquivo oficial de **Sophisticated Backpacks: Ars Compat** para Minecraft 1.21.1 / NeoForge, [CurseForge 1653477, File ID 8653384](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated/files/8653384). O dossiê anterior confundia o projeto com **Sophisticated Storage: Ars Compat**, que tem o JAR distinto `arssophisticatedstoragecompat-0.3.0.jar`, [CurseForge 1653878, File ID 8655579](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-ars-compat-sophisticated/files/8655579). **O arquivo do pack não foi disponibilizado nesta rodada para comparação SHA ou extração de manifesto. A identidade é fortemente apoiada no filename oficial, não uma igualdade binária já verificada.**

- **Identidade oficial mais compatível com a evidência física:** Sophisticated Backpacks: Ars Compat | Sophisticated Backpacks Ars-Nouveau Compatibility.
- **Nome histórico no catálogo:** Ars Sophisticated Compatibility / erroneamente Sophisticated Storage: Ars Compat.
- **Autor e publicação:** LuminaCherry, 15/08/2026, tipo Release, CurseForge project `1653477`, file `8653384`.
- **Minecraft/loader do arquivo publicado:** Minecraft `1.21.1`, NeoForge, cliente e servidor.
- **Arquivo top-level histórico:** `arssophisticatedcompat-0.3.0.jar` — não modificado.
- **Versão histórica:** `0.3.0` (runtime no snapshot). **Disponível para o mesmo target:** `0.3.0`.
- **Mod ID do inventário:** `arssophisticatedcompat`; confirmar novamente no JAR real.
- **Categorias CurseForge:** `Magic + Storage + Utility & QoL`; a pasta existente permanece válida.
- **Estado do pack:** última evidência de presença é `modlist(1).txt` de setembro de 2026; nenhuma nova lista física foi fornecida.
- **Decisão:** **MANTER no catálogo e no inventário histórico**, corrigindo a identidade; promoção/compatibilidade com atualização Sophisticated **PENDENTE**.
- **Fonte primária funcional:** [descrição do projeto Backpacks](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated) e [release 0.3.0 NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated/files/8653384).
- **Source versionado:** não localizado/inspecionado para este binário; não inventar assinaturas, mixins, ticks ou nomes internos de classes.

## 1. Função e arquitetura

A extensão fornece upgrades de **Ars Nouveau para Sophisticated Backpacks**, manipulando Source, conversão de itens, manutenção de efeitos de poção e reparo de equipamentos enquanto associados a uma mochila. É uma bridge de funcionalidade, não cria um segundo inventário independente nem substitui o sistema de magia Ars.

**Ownership:** Sophisticated Backpacks controla inventários móveis/colocados, upgrades, slots, filtros, persistência e sincronização. Ars Nouveau é autoridade da economia de Source e dos efeitos/reparos aceitos. Este addon conecta essas duas APIs no contexto de uma **mochila Sophisticated**, não de barrel/chest/shulker do Sophisticated Storage.

## 2. Superfícies e conteúdo oficialmente publicados

### 2.1 Source Storage Upgrade
A documentação do autor prevê guardar Source dentro de backpack e escalar capacidade com **Stack Upgrades**. Valores concretos, fórmula, número de slots, transferências e restrições não foram isolados no JAR; não publicar valores especulativos.

### 2.2 Sourcelink Upgrades
A [descrição oficial](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated) enumera **Agronomic, Alchemical, Mycelial, Volcanic e Vitalic**. A [release 0.3.0](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated/files/8653384) informa conversão de itens válidos da mochila em Source por Backpack Source Link. As condições específicas dos cinco tipos, quantidades e receitas internas não foram auditadas; qualquer lógica de conversão deve se apoiar no provider Ars.

### 2.3 Potion Jar Upgrade
A documentação prevê armazenar duração de poção e manter efeitos ativos com auto-refresh. Testar duração, remoção do upgrade, troca de mochila, dimension change, morte, logout e restart sem efeito fantasma; o comportamento exato de servidor não foi inspecionado em bytecode.

### 2.4 Enchanter's Upgrade
A [página oficial do projeto](https://www.curseforge.com/minecraft/mc-mods/sophisticated-backpacks-ars-compat-sophisticated) anuncia reparo de equipamento encantado consumindo Source guardado. A release file 8653384 não lista esse upgrade explicitamente no texto resumido, portanto sua existência é **evidência da descrição do projeto**, sem atribuir classes/receitas específicas ao binário antes de inspeção.

### 2.5 Templates e recipes
A release confirma modelos Ars-themed de upgrades para backpack e recipes. Não inventar IDs, custo/outputs ou compatibilidade com alterações KubeJS/datapacks sem extrair o conteúdo da publicação e avaliar o RecipeManager carregado.

## 3. Dependências e faixa de versões

**Obrigatórias conforme descrição da publicação:** Ars Nouveau, Sophisticated Backpacks e Sophisticated Core. O autor não publica ranges granulares do NeoForge no changelog acessado; **não atribuir mínimos numéricos a este addon sem ler o manifesto do JAR**.

**Providers do último snapshot do pack:** Ars Nouveau `5.13.1`, Sophisticated Backpacks `3.26.3` e Sophisticated Core `1.5.1`. A migração documental avaliada na PR #893 mira Core `1.5.11.2394` + Backpacks `3.26.12.2208` + Storage `1.6.5.2176` e bridges Create. Essa combinação **não foi testada com Ars Compat 0.3.0**. O lançamento para o mesmo Minecraft não comprova ABI com versões posteriores.

**Não confundir com outro produto:** [Sophisticated Storage: Ars Compat, File ID 8655579](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-ars-compat-sophisticated/files/8655579), depende de **Sophisticated Storage** e implementa upgrades de recipientes estacionários; seu filename oficial não foi encontrado como top-level no snapshot conhecido. Não registrá-lo como instalado nem transferir suas features para o presente dossiê.

## 4. Integrações do modpack

- **Sophisticated JEI Index (#515):** consulta contents de backpacks e recipe transfer. Se o Sourcelink consumir itens simultaneamente, verificar contabilidade e invalidar caches corretamente.
- **Sophisticated Backpacks Create Integration (#517):** contraptions e grupos vinculados podem mover/expor backpacks; garantir um dono efetivo dos ticks de Source/upgrades durante montagem, movimento e desmontagem.
- **Sophisticated Storage e sua bridge Create (#519–520):** providers relacionados, mas **não destinatários diretos** dos upgrades deste addon. A partilha de linked storage entre Backpacks e Storage pode expor inventários relacionados, exigindo evitar conversão/tick em duplicidade.
- **Ars Nouveau e addons Ars:** economia e efeitos seguem contratos Ars. A coexistência não autoriza aplicação duplicada de Source ou reparos.
- **KubeJS/datapacks:** confirmar receitas de upgrades e conflitos de recipe IDs/outputs na instância real; nenhuma mudança arbitrária foi feita.
- **Dedicated server/multiplayer:** dois usuários consultando o mesmo backpack/linked group devem ver estado server-authoritative; nenhuma transferência pode ocorrer mais de uma vez.

## 5. Persistência, sincronização e performance

Cenários de risco a examinar: carga de chunks, backpack equipado vs colocado, mudança de dono/localização, Inception/nesting se suportado pelo conjunto instalado, movement via contraption, ações concorrentes e restart. O provider dos itens e o de Source devem preservar estado de maneira transacional; uma cópia client-side não pode recriar item consumido nem saldo de Source.

As opções de configuração e cooldowns específicos da versão 0.3.0 **não foram inspecionados**. Não deduzir custo de tick, taxa de geração ou mecanismos internos apenas pelas descrições funcionais.

## 6. Matriz de validação para atualização coordenada (tudo PENDENTE)

- [ ] Obter o JAR top-level real `arssophisticatedcompat-0.3.0.jar`, comparar SHA-1 físico `7cc6c1e1d92d109230f68b6a63dc4bfb13c245a6` com SHA do arquivo oficial **8653384** (se acessível), verificar SHA-256.
- [ ] Extrair `META-INF/neoforge.mods.toml` do JAR real e oficial; confirmar `modId`, versão, loader, dependências e eventuais JarJar, sem inferir por filename.
- [ ] Verificar se **`arssophisticatedstoragecompat-0.3.0.jar`** está ausente/presente em um **novo dump físico**; não presumir que o segundo mod foi instalado.
- [ ] Testar boot cliente + dedicated server NeoForge `1.21.1`, Java 21 e logs de classloading com o trio Ars/Core/Backpacks.
- [ ] Testar cada Sourcelink publicado com insumos válidos, débito único dos itens e crédito único de Source, inclusive quando inventário é visualizado via JEI.
- [ ] Testar Source Storage + Stack Upgrades antes/depois da alteração de nível; nenhuma perda/duplicação de Source.
- [ ] Testar Potion Jar após remoção do upgrade, backpack pickup/place, restart, death/respawn e troca de dimensão.
- [ ] Testar Enchanter's Upgrade somente se confirmado na versão binária: Source debitado uma vez por reparo efetivo.
- [ ] Testar Backpack em Create contraption, linked group Backpack↔Storage, unload/reload, assemble/disassemble e dois clientes concorrentes.
- [ ] Repetir o conjunto em **cópia do mundo** com Core 1.5.11, Backpacks 3.26.12, bridges Create 0.2.3/0.1.23 e Storage 1.6.5; rollback e contagem de inventário antes/depois.
- [ ] Confirmar recipes/datapacks/KubeJS/JEI de upgrades sem alterar progressão nem sobrescrever recipe silenciosamente.

**Nenhum desses testes de JAR/runtime ou inspeção binária foi executado neste ciclo.** A inferência de identificação baseia-se em correspondência exata de filename publicado, target e versão, mais descrição oficial; **a identidade byte-a-byte permanece NÃO VERIFICADA**.

## 7. Histórico da correção e rastreabilidade

- **Antes de 13/10/2026:** o catálogo classificava erroneamente o arquivo físico `arssophisticatedcompat-0.3.0.jar` como Sophisticated Storage: Ars Compat, atribuindo-lhe blocos estacionários, Storage Source Link e dependencies de Storage. Essa associação histórica **foi refutada pela comparação dos dois projetos oficiais**. A informação errada não deve continuar servindo de base para migração/QA.
- **13/10/2026 — revisão da PR #894:** havia sido sinalizada apenas a divergência entre o filename do snapshot e o artefato de Storage, sem procurar o produto de Backpacks. Esta reauditoria resolve a *atribuição por filename* com o File ID `8653384`, mas conserva como pendência a prova binária.
- **O que não mudou:** arquivo físico, nome físico do JAR, runtime e SHA-1 históricos, posição #054, número de mods, instalações e mundos. A pasta/file slug histórico `✅-ars-sophisticated-compatibility v0.3.0.md` foi conservado nesta correção para preservar os links existentes; o **título e identidade funcional** agora correspondem ao projeto de Backpacks.
- **Produto separado:** Sophisticated Storage: Ars Compat mantém [File ID `8655579`](https://www.curseforge.com/minecraft/mc-mods/sophisticated-storage-ars-compat-sophisticated/files/8655579), **não incluído como top-level confirmado** no snapshot. A inclusão futura depende de pedido/justificativa, avaliação de redundância/estabilidade e inventário físico novo.

**Estado do lote:** identidade documental **CORRIGIDA COM BASE EM EVIDÊNCIA OFICIAL**; disponibilidade e metadata binária **NÃO VERIFICADAS**; promoção do stack Sophisticated **PENDENTE**.

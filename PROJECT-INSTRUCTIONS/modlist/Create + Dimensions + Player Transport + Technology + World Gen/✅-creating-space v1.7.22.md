# Creating Space

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#206**: JAR `creatingspace-1.21.1-1.7.22.jar`, mod id `creatingspace`, runtime `1.7.22`, SHA-1 `6eca99ee07780ef8918ad6969809f15ed0b1371c`.

## Propriedades do registro

- **Mod:** Creating Space
- **Arquivo JAR:** `creatingspace-1.21.1-1.7.22.jar`
- **Versão 1.21.1:** `1.7.22`
- **Categoria:** Exploração, Tecnologia
- **Função:** Addon espacial de Create em que foguetes são construídos como contraptions e usados para viajar a outros planetas, com proposta de projeto inspirada em engenharia/ciência em vez de veículos prontos.
- **Dependências:** Create 6.x obrigatório; pack físico usa NeoForge 21.1.248 + Create 6.0.10. Build 1.7.22 é Client & Server para NeoForge 1.21.1. Integrações/datapacks externos permanecem condicionais à presença física específica.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Contraptions móveis, world/dimension transitions e persistência de veículos são superfícies críticas. A documentação oficial é inconsistente sobre Create: Interactive: CurseForge descreve compatibilidade parcial desde 1.7.8 com bugs/workarounds, enquanto Modrinth ainda alerta incompatibilidade; tratar como compat experimental e testar.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-creating-space
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `creatingspace` 1.7.22 + CurseForge oficial da release 1.7.22 (14/09/2026) e changelog correspondente. Decisão formal de 22/08/2026 preservada.
- **Observações:** JAR `creatingspace-1.21.1-1.7.22.jar`; runtime 1.7.22; Release NeoForge 1.21.1 de 14/09/2026. A 1.7.22 corrige loot tables, collision do rocket e recipe ausente de moon stone variants, e altera engineer table material logic, schedule destination selection, nickel ore texture e ore spawn rates.
- **Atualização/Status:** REVALIDADO EM 17/09/2026 — runtime físico atualizado para creatingspace-1.21.1-1.7.22.jar / 1.7.22. Release NeoForge 1.21.1 de 14/09/2026 confirmada; changelog oficial registra fixes de loot tables, collision do rocket e recipe ausente de moon stone variants, além de ajustes de engineer table, destination selector, nickel texture e ore spawn rates. Decisão `Manter` preservada.
- **Decisão:** Manter
- **Histórico da decisão:** 2026-08-22 — decisão vigente `Manter`: Creating Space foi recolocado/mantido junto de Northstar Redux para substituir Stellaris e evitar uma progressão espacial excessivamente tecnológica. Em 08/09/2026, a decisão e a coexistência deliberada foram preservadas na modlist física de 595.
- **Sobreposição:** Northstar Redux também cobre exploração espacial, mas a coexistência foi escolhida deliberadamente. Não classificar como duplicata automática; comparar planetas, vehicle construction, progression e recursos concretos.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs
> 🚀 Versão física confirmada: `creatingspace-1.21.1-1.7.22.jar`, runtime `1.7.22`, NeoForge 1.21.1. Decisão formal do pack: **Manter** desde 22/08/2026.
## 1. Papel e authority
Creating Space adiciona exploração espacial baseada em foguetes construídos como **contraptions Create**. Create continua authority das primitives de contraption; Creating Space controla assembly espacial, travel/planet context e conteúdo próprio associado ao voo.
## 2. Filosofia de construção
O projeto enfatiza projetar o foguete em vez de receber uma nave pronta. O layout físico deve ser validado pelo runtime antes do lançamento; esta ficha não inventa requisitos de tamanho, thrust, fuel ou peças sem documentação/JAR 1.7.22.
## 3. Create 6+
Desde a linha 1.7.10, a documentação oficial exige **Create 6.+**. Isso torna a versão do Create uma boundary dura: update isolado de Create precisa de smoke-test de assembly, launch e return.
## 4. Contraption state
Ao virar foguete, blocos deixam de ser apenas world blocks e passam a compor uma contraption. Inventories, block entities e attachments suportados precisam manter state único entre assembly, flight e disassembly.
## 5. Viagem a planetas
A feature central é viajar a outros planetas. Dimension/world transition precisa manter player, veículo e inventories coerentes e não duplicar a nave no mundo de origem/destino.
## 6. Progressão espacial
A decisão do pack preserva Creating Space junto de Northstar Redux para uma progressão menos baseada em máquinas espaciais prontas. Essa é decisão curatorial; não implica equivalência técnica entre os dois mods.
## 7. Create: Interactive
As páginas oficiais divergem: CurseForge afirma compatibilidade **parcial** com Create: Interactive em versões recentes, com bugs/workarounds; Modrinth ainda exibe aviso de incompatibilidade. Para a build 1.7.22, tratar Interactive como integração experimental e nunca como suporte garantido.
## 8. Ad Astra datapacks
O projeto divulga datapacks de compatibilidade com Ad Astra como recurso externo da equipe. Eles não são parte automaticamente ativa do JAR 1.7.22 e não devem ser catalogados como runtime sem presença física separada.
## 9. Client/server
Assembly, vehicle state, travel, world transition e inventories são server/common. Models, cockpit/visuals e efeitos são client-facing. O cliente não pode autorizar launch/destination por state local não validado.
## 10. Multiplayer
Com múltiplos players na mesma nave, ownership/passenger state e transição dimensional precisam ocorrer uma vez. Reconnect durante viagem não pode criar cópia da contraption ou deixar player preso em dimensão incorreta.
## 11. Chunk lifecycle
Foguete no limite de chunks ou durante unload/reload exige persistência cuidadosa. Chunk ticketing real pertence à implementação; não inferir que o mod mantém todos os chunks sempre carregados.
## 12. Version 1.7.22
A build 1.7.22 é a release NeoForge 1.21.1 vigente publicada em 14/09/2026. O changelog oficial registra três correções materiais: loot tables, collision do rocket e recipe ausente para moon stone variants. Também registra alterações em engineer table material logic, schedule destination selection widget, nickel ore texture e ore spawn rates. Essas mudanças tornam loot, collision, recipes, destination selection e worldgen de minério regression gates explícitos desta atualização.
## 13. Northstar Redux
A coexistência foi decidida pelo usuário. Integrations/recipes/planets que se sobrepõem precisam ser avaliados feature-by-feature; não remover um provider simplesmente por ambos serem `space mods`.
## 14. Lifecycle
Validar build→assembly→launch→travel→arrival→disassembly, death/reconnect, server restart com nave persistida, dimension unload e update de Create.
## 15. Riscos
1. Contraption duplicar em transição dimensional.
2. Inventory/block entity state se perder.
3. Passenger state divergir em multiplayer.
4. Create update quebrar assembly.
5. Interactive hook causar conflito/desync.
6. Conteúdo de datapack externo ser assumido presente.
7. Coexistência com outro space mod duplicar recipes/progression.
8. Comportamento além do changelog 1.7.22 ser preenchido por inferência.
## 16. Matriz de testes
1. Dedicated server boot com Create 6.x.
2. Montar foguete mínimo válido conforme guia/runtime.
3. Assembly/disassembly preservando blocks/inventories.
4. Launch e chegada a destino sem dupe.
5. Return trip para mundo/origem.
6. Multiplayer com passageiro adicional.
7. Disconnect/reconnect antes/durante/depois da viagem.
8. Server restart com foguete montado.
9. Teste A/B com Create: Interactive se estiver instalado.
10. Coexistência com Northstar Redux sem recipe/dimension collision evidente.
## 17. Evidência
- modlist física 16/09/2026: Creating Space 1.7.22;
- CurseForge oficial: release 1.7.22 NeoForge 1.21.1 de 14/09/2026, com fixes de loot/collision/recipe e ajustes de engineer table, destination selector, nickel texture e ore spawn;
- CurseForge/Modrinth oficiais: foguetes como contraptions, planet travel, Client & Server;
- requisito Create 6.+ desde 1.7.10;
- documentação oficial conflitante sobre Interactive tratada como risco, não como fato resolvido;
- decisão histórica `Manter` de 22/08/2026 preservada.
> 🔒 Boundary canônico: **Creating Space decide a camada espacial; Create decide a infraestrutura de contraption**. A transição mundo↔foguete↔planeta precisa preservar uma única cópia de cada state.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** Create: Creating Space
- **Último candidato 1.21.1 / NeoForge localizado:** **1.7.22**
- **Estado:** **REVALIDADO — sem bump localizado**
- **Gate de promoção:** Manter baseline; qualquer futura troca exige testes de dimensão/teleporte, Create, worldgen, vehicles, save/restart e multiplayer.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/create-creating-space

## Retificação de versão upstream — 23/10/2026 (Creating Space 1.7.22 → 1.7.23)

**Estado físico preservado:** `creatingspace-1.21.1-1.7.22.jar`, mod ID `creatingspace`, runtime `1.7.22`, posição física #206. **Identidade do projeto:** *Create : Creating Space*, CurseForge **858897**, autor RealAntEngineer. Categorias atuais **Create + Dimensions + Player Transport + Technology + World Gen** estão de acordo com a pasta. **Retificação expressa da nota de 22/10:** a informação de que 1.7.22 ainda seria a última versão ficou obsoleta: em **21/10/2026** foi publicada a **Release 1.7.23 NeoForge 1.21.1**, arquivo exato **`creatingspace-1.21.1-1.7.23.jar`**, **CurseForge File ID 9282749**. A lista oficial não mostra outra release 1.21.1 intermediária entre 1.7.22 e 1.7.23.

### Changelog público específico
A página oficial da release 1.7.23 contém somente **“bug fixes”**, sem discriminar subsistemas. Esse texto, isoladamente, **não prova** quais correções estão no JAR distribuído, nem justifica atribuir mudanças de outras linhas/versões.

### Investigação do source oficial `RealAntEngineer/creating_space` — branch `1.21.1-release`
O próprio CurseForge vincula esse repositório. O histórico público contém **três PRs de correção fundidos em 19/10/2026**, anteriores à publicação 1.7.23, com evidência técnica por commits e arquivos modificados. O commit **`ef417124e4760f9f08482ce7e6d4eee4e79af180`**, de **21/10**, acrescentou automação de publicação e ajustes em dados/recipes/rocket engineer table. Esses commits são **evidência de source na janela de atualização**, mas não existe nesta auditoria um release tag/manifesto ou diff binário que prove correspondência 1:1 com a distribuição 9282749; a lista abaixo não é apresentada como changelog binário certificado.

**PR #130 (`0f94fa5ce787507b3a96a53b595e580ec0fa8276`, 19/10) — crashes, oxigênio e worldgen:**
- Corrige **disconnects `set_entity_data`** causados pelo envio/compartilhamento de `RoomShape` mutável em packets de entity data; `RoomShapeSerializer` e cópia de shape são superfícies relevantes;
- Corrige **dano por superaquecimento dentro de salas seladas com oxigênio**, onde o estado de `inO2()` podia ser consumido por mais de uma checagem;
- Corrige **data race** em cache de altura do **CraterCarver** durante geração de chunks por múltiplas threads. Fonte: https://github.com/RealAntEngineer/creating_space/commit/0f94fa5ce787507b3a96a53b595e580ec0fa8276 .

**PR #131 (`0cad4fed0a2edc0eaeee8810fcdc00b987316111`, 19/10) — desempenho de salas:**
- Reduz custo de **room rebuilds** e atualizações de blocos, incluindo debounce de reconstruções e trabalho evitado em callbacks de blocos sem necessidade;
- Examina ocupantes em intervalos menores de frequência, evitando queries de todas as entidades em todas as caixas a cada tick;
- Ajusta igualdade de `RoomShape` para evitar retransmissões desnecessárias quando a geometria não mudou. Fonte: https://github.com/RealAntEngineer/creating_space/commit/0cad4fed0a2edc0eaeee8810fcdc00b987316111 .

**PR #132 (`211ffbbb9b806eae1282b68128bdb6491ac2282d`, 19/10) — simulação de ambientes fechados:**
- Corrige **drift de volume de sala** ao remover uma box e recalcular sua geometria;
- Aplica corretamente o **limite de blocos processados por tick** a cada box, preservando continuidade de buscas que atingem o limite;
- Evita **forçar carregamento de chunks** durante scan de salas ao tratar chunks descarregados como sólidos; a busca não deve produzir chunkloads involuntários.
- Reorganiza busca de ocupantes e sincronização de geometria. Fonte: https://github.com/RealAntEngineer/creating_space/commit/211ffbbb9b806eae1282b68128bdb6491ac2282d .

**Commit 21/10 — publicação e data/loot:** o commit `ef417124e4760f9f08482ce7e6d4eee4e79af180` toca workflows de publicação, loot de rocket controls e regolith, tags de mineração, `RocketEngineerTableBlockEntity`, traduções e documentation. A alteração de arquivos no source **não significa automaticamente** mudança visível comprovada no JAR 1.7.23; comparar diff source/binário antes de atribuir comportamento específico. Fonte: https://github.com/RealAntEngineer/creating_space/commit/ef417124e4760f9f08482ce7e6d4eee4e79af180 .

### Riscos e testes de promoção — nenhum executado
- [ ] Obter JAR 1.7.23 e comparar hashes/manifests com a release, ou release tag do upstream, para fechar a correspondência dos commits de 19–21/10 com o binário.
- [ ] Testar salas seladas e atmosfera/oxigênio, incluindo temperaturas extremas, superaquecimento, pressão e diferença entre cliente/servidor.
- [ ] Testar retransmissões `set_entity_data` com vários jogadores, join/rejoin, shape mutável e redes instáveis sem desconexões.
- [ ] Testar dimensão lunar/marciana, muitos chunks gerados em paralelo, crateras e `CraterCarver` sem data race/seed inconsistente.
- [ ] Testar novos/reprocessados rooms em limites de chunks, chunks descarregados, construção/destruição de paredes, grande volume, block budget/tick e estabilidade de TPS.
- [ ] Testar criação/lançamento de foguetes e transições entre dimensões com Create 6.0.10 e Northstar Redux; salvar, reiniciar, descarregar chunks, reconectar e desassemblar sem dupe/perda de inventários.
- [ ] Testar loot/recipe de Engineer Table e regolith em data packs e JEI se os deltas do source se confirmarem na distribuição.

**Status:** **nova release 1.7.23 confirmada**; mudanças no source da janela anterior detalhadas, mas **delta exato do binário continua parcialmente indeterminado** porque o changelog oficial só diz “bug fixes” e não houve diff entre JARs. Pela exigência estrita de release-by-release comprovada no artefato, **manter o item 75 pendente** da fila até realizar essa conciliação. Não modificar JAR instalado, filename, versão física ou configurações.

**Fontes primárias:** https://www.curseforge.com/minecraft/mc-mods/create-creating-space/files/9282749 ; https://github.com/RealAntEngineer/creating_space/tree/1.21.1-release ; PRs #130, #131, #132 e commits pinados acima.

## Encerramento da reconciliação source → Release 1.7.23 — 24/10/2026

> **Supera a pendência documental da seção “Retificação de versão upstream — 23/10/2026”.** Foi localizada **a Release pública do próprio autor**, com **tag Git, commit, JAR de distribuição e notas de PRs** para **o Minecraft/loader exatos**. Isso comprova a procedência documental do source da build; não é uma nova medição de equivalência binária do arquivo CurseForge nem um teste de gameplay.

**Autoridade física preservada:** posição **#206**, JAR `creatingspace-1.21.1-1.7.22.jar`, mod ID `creatingspace`, runtime **1.7.22**. Projeto **Create: Creating Space**, CurseForge **858897**, autor **RealAntEngineer**, categorias oficiais **Create + Dimensions + Player Transport + Technology + World Gen**, idênticas à pasta. Único artefato posterior NeoForge 1.21.1 confirmado neste intervalo: **1.7.23**, CurseForge **File ID 9282749**, nome `creatingspace-1.21.1-1.7.23.jar`, upload **21/10/2026**; notas curtas no CurseForge: *bug fixes*.

### Identificador verificável da distribuição do autor

- **Release GitHub oficial**: https://github.com/RealAntEngineer/creating_space/releases/tag/v1.21.1-1.7.23, publicada **21/10/2026 23:16:09 UTC**. Asset **`creatingspace-1.21.1-1.7.23.jar`**, tamanho **2.506.597 bytes**, digest **SHA-256 do asset GitHub** `ba40b9bc2e74d595ed07788e7f9d3ca34822eccc8fe37d0315e849547facf882`.
- **Tag versionada `v1.21.1-1.7.23`**: https://github.com/RealAntEngineer/creating_space/tree/ef417124e4760f9f08482ce7e6d4eee4e79af180 . A referência Git do tag aponta para **`ef417124e4760f9f08482ce7e6d4eee4e79af180`**, commit **21/10/2026 23:11:51 UTC**. Seu `gradle.properties` declara explicitamente **`minecraft_version=1.21.1`, `mod_version=1.7.23`, `neo_version=21.1.219`**.
- **Build** `.github/workflows/release.yml`: o workflow lê as versões de `gradle.properties`, executa `./gradlew build`, cria/move tag `v<mc>-<mod>` na revisão atual e anexa **`build/libs/*.jar`** à GitHub Release.
- **Distribuição** `.github/workflows/publish.yml`: baixa o **JAR da Release versionada**, remove fontes/javadoc/dev/slim, exige um único binário e envia **esse arquivo** ao Modrinth e ao CurseForge, com versão Minecraft/NeoForge obtida do `gradle.properties`. Fontes: https://github.com/RealAntEngineer/creating_space/blob/ef417124e4760f9f08482ce7e6d4eee4e79af180/.github/workflows/release.yml e https://github.com/RealAntEngineer/creating_space/blob/ef417124e4760f9f08482ce7e6d4eee4e79af180/.github/workflows/publish.yml .
- **Limite de certeza:** o workflow documenta o procedimento de publicação, mas a execução particular do upload CurseForge **não foi rastreada por run/log**, tampouco foi obtido o hash do binário CurseForge para comparar byte a byte com o SHA-256 acima. Não afirmar identidade binária já verificada. A coincidência do projeto, loader/jogo, versão, filename, data e a cadeia versionada do upstream dá rastreabilidade documental adequada aos deltas abaixo.

### Deltas atribuíveis ao source tagueado da 1.7.23

**Somente os três PRs da janela 19/10**, posteriores à física 1.7.22, são tratados como novidades exclusivas dessa passagem. A GitHub Release enumera também PRs #103–129 acumuladas da portabilidade 1.21.1; **não reatribuí-las automaticamente** à 1.7.23 porque suas datas e presença anterior podem preceder a baseline instalada.

| PR incorporado na tag 1.7.23 | Correções demonstráveis no source oficial da build |
|---|---|
| **#130** — merge `0f94fa5ce787507b3a96a53b595e580ec0fa8276` | Corrige **desconexões `set_entity_data`** causadas pelo envio de `RoomShape` mutável, **dano de superaquecimento em salas seladas com oxigênio** e **data race** do cache de altura de `CraterCarver` em geração concorrente. |
| **#131** — merge `0cad4fed0a2edc0eaeee8810fcdc00b987316111` | Otimiza **reconstruções de salas e callbacks de atualização de blocos**; faz debounce de rebuild, compara `RoomShape` por valor para evitar sincronizações redundantes e reduz custo de varreduras de entidades. |
| **#132** — merge `211ffbbb9b806eae1282b68128bdb6491ac2282d` | Corrige **desvio no cálculo de volume de salas**, respeita o **limite de trabalho por tick e continua a busca após caixas limitadas**, não força carregamento de chunks não carregados e faz scan de ocupantes em janela de **5 ticks** por consulta tipada única. |

**Fonte que vincula essas PRs à Release exata (não só à janela temporal):** descrição do autor em https://github.com/RealAntEngineer/creating_space/releases/tag/v1.21.1-1.7.23 . A publicação CurseForge https://www.curseforge.com/minecraft/mc-mods/create-creating-space/files/9282749, de **21/10**, resume esse lançamento apenas como **“bug fixes”**. O commit `ef417124...` também ajusta data/loot, tags, tela da Engineer Table e documentação; como o histórico gerado na Release agrega PRs antigas, avaliar essas alterações adicionais somente no diff entre tags/source antes de classificá-las como exclusivas em gameplay.

### Condições de promoção física — permanecem abertas

- [ ] Baixar arquivos autorizados **GitHub Release** e **CurseForge 9282749** e confrontar SHA-256 para comprovação binária independente, caso a atualização seja realizada.
- [ ] Checar metadata e dependências reais (Create 6.x, NeoForge, Create: Interactive, demais mods de dimensões) antes da substituição do JAR.
- [ ] Dedicated server e dois clientes: loops de sealed-room/oxygen/superaquecimento, atualização concorrente de blocos, volume/geometria de salas, `/reload`, chunks não carregados e restrição de budget por tick.
- [ ] Worldgen de crateras em múltiplas threads, launch/landing do foguete, rota de ida e volta entre planetas/dimensões, persistence/relog, save/restart, passageiros, inventories e risco de desync/dupe.

**Conclusão:** há **um único release NeoForge 1.21.1 entre 1.7.22 e 1.7.23**, com **GitHub Release tagueada e changelog de três PRs corretivas** ligados diretamente ao source da 1.7.23 e às ferramentas de publicação do autor. **A pendência de pesquisa de changelogs/release do item da lista renumerada #17 (antigo #75) está documentalmente concluída; pode sair do Bloco de Notas.** A certificação não vale para **hash entre plataformas, migração do JAR nem QA gameplay**. O arquivo `.md`, JAR e runtime instalados permanecem com **1.7.22**.

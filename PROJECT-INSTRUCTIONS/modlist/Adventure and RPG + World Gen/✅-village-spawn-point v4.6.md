# Village Spawn Point

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#561**: JAR `villagespawnpoint-1.21.1-4.6.jar`, mod id `villagespawnpoint`, runtime `4.6`, SHA-1 `a4139b691cae6311c2888c08948b0866845d1297`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Village Spawn Point
- **Arquivo JAR:** `villagespawnpoint-1.21.1-4.6.jar`
- **Versão 1.21.1:** 4.6
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** 
- **Categoria:** QoL, Worldgen, Exploração
- **Função:** Utilidade server-side que escolhe o world spawn inicial de mundos novos no centro de uma village encontrada; não gera villages nem altera seu conteúdo.
- **Dependências:** NeoForge 1.21.1 e dependências declaradas pelo projeto. O resultado depende do worldgen/villages realmente reconhecidos no runtime.
- **Sobreposição:** Não substitui Integrated Villages, Dynamic Village, Stoneholm ou outros settlements; apenas consome uma village reconhecida como destino de spawn.
- **Compatibilidade/Riscos:** Validar custo de busca inicial, posição segura, seed determinism e quais villages modded são realmente reconhecidas. Não assumir que toda estrutura visualmente semelhante a village é candidata. Existing worlds não devem ser reposicionados por inferência.
- **Observações:** Mod id `villagespawnpoint`, runtime 4.6, server-side. Atua apenas na seleção do spawn inicial; providers de settlements continuam authorities do conteúdo da village.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge oficial Village Spawn Point 4.6 revalidado em 13/09/2026. A linha 4.6 permanece a build aplicável a Minecraft 1.21.1; nenhum world-creation/runtime test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/village-spawn-point
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — Village Spawn Point 4.6 permanece exatamente instalado e continua a release aplicável a Minecraft 1.21.1; releases 4.7 pertencem a linhas posteriores do jogo e não constituem update automático deste runtime.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `villagespawnpoint-1.21.1-4.6.jar`, mod id `villagespawnpoint`, versão `4.6`. Village Spawn Point é utilidade **server-side** de seleção do spawn inicial: procura uma village e move o world spawn para o centro dela. Não gera villages e não é authority de settlement/worldgen.
## 1. Função
O mod altera a escolha do ponto inicial de mundos novos para usar uma village válida em vez do algoritmo vanilla padrão de spawn.
Seu efeito é de bootstrap do mundo: depois que o spawn foi escolhido, conteúdo, NPCs, professions, loot e layout continuam pertencendo ao provider da village encontrada.
## 2. Authority e boundaries
- Village Spawn Point: authority somente da escolha/reposicionamento inicial do world spawn.
- Worldgen providers: authority das villages/structures existentes.
- Villagers/factions/jobs: authority dos respectivos mods.
Não criar quest/perk assumindo que a village escolhida pertence a um mod específico sem identificar a estrutura/provider real.
## 3. Stack de villages do pack
O pack possui vários sistemas que alteram settlements e estruturas. Portanto o resultado precisa ser validado contra o worldgen real.
A presença de Integrated Villages, Dynamic Village, Stoneholm ou outros mods de settlements **não prova** que todos sejam candidatos aceitos pelo algoritmo do Village Spawn Point. O critério real precisa ser observado no runtime/source da build 4.6.
## 4. Server-side e criação de mundo
O projeto é server-side. A decisão de spawn deve ocorrer na geração/configuração do mundo e ser persistida como world spawn normal.
Client mods não devem recalcular essa posição.
## 5. Seed determinism e worldgen
Para seed fixa + mesma modlist/config, o resultado deve ser reproduzível dentro das regras do worldgen.
Alterar a modlist/worldgen depois de criar o mundo não deve ser interpretado como obrigação de reposicionar automaticamente o spawn antigo.
## 6. Riscos
1. **Worldgen-heavy search:** localizar village durante criação pode aumentar custo inicial conforme raio/algoritmo.
2. **Invalid placement:** centro geométrico/structure center pode cair em posição ruim dependendo do terrain/structure.
3. **Modded village assumptions:** estruturas visualmente parecidas com villages podem não ser reconhecidas pelo critério usado.
4. **Spawn safety:** água, altura, caverna ou bloco perigoso precisam de smoke test.
5. **Existing world:** não assumir reprocessamento automático em save já criado.
## 7. Boundary para quests/progressão
Chegar ao spawn inicial não é milestone repetível. Não conceder Mastery por login/respawn/reload no mesmo ponto.
Se uma quest usar “village inicial”, persistir identidade/estado próprio da quest e não reexecutar a seleção do mod como detector causal.
## 8. Matriz de testes
- [ ] Criar mundo novo em dedicated server com seed fixa.
- [ ] World spawn termina em village válida e segura.
- [ ] Reabrir o mundo preserva o mesmo spawn sem nova busca indevida.
- [ ] `/kill`/respawn usa regras normais de spawn/bed após criação.
- [ ] Testar seeds com terrain/worldgen complexo.
- [ ] Observar se villages modded do stack são ou não candidatas reais.
- [ ] Confirmar ausência de crash/timeout em geração inicial.
- [ ] Existing world não é reposicionado inesperadamente após update.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências
- **Modlist física 08/09/2026:** `villagespawnpoint-1.21.1-4.6.jar`, mod id `villagespawnpoint`, runtime 4.6.
- CurseForge oficial Serilum: server-side, move o spawn inicial de novos mundos para o centro de uma village.
- Guia Gameplay: não gera villages nem substitui os providers de settlement.
## 10. Limitação
O algoritmo exato de lookup/radius/structure recognition da build 4.6 não foi decompilado nesta etapa. Compatibilidade com cada village modded deve ser medida, não inferida.
## 11. Revalidação física — 11/09/2026
O runtime físico continua exatamente `villagespawnpoint-1.21.1-4.6.jar`, mod id `villagespawnpoint`, versão `4.6`, em ambiente server-side. A file list oficial mantém 4.6 como a linha aplicável a Minecraft 1.21.1.
Versões 4.7 existem para linhas posteriores do Minecraft e **não são tratadas como atualização aplicável ao runtime 1.21.1**. Nenhuma criação de mundo, busca de village, seed determinism, spawn safety ou compatibilidade com settlements modded foi testada nesta recatalogação.
## 12. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `villagespawnpoint-1.21.1-4.6.jar`, versão `4.6`, em ambiente server-side. A file list oficial continua tratando `4.6` como a release aplicável ao Minecraft 1.21.1; versões `4.7` localizadas pertencem a linhas posteriores do jogo e não são update automático deste runtime. O boundary permanece o mesmo: Village Spawn Point escolhe o world spawn inicial, mas não gera villages nem assume ownership dos settlements encontrados. Nenhuma criação de mundo, busca de village, seed determinism, spawn safety ou compatibilidade com settlements modded foi testada nesta revalidação.

## Auditoria parcial — 20/11/2026 — novas versões 4.8 a 6.1

O projeto oficial CurseForge 347381 (Serilum) mantém categorias Adventure and RPG + World Gen. O snapshot físico #561 continua `villagespawnpoint-1.21.1-4.6.jar`. A conclusão histórica de que 4.6 era a última publicação aplicável não deve ser usada como estado upstream atual.

Publicações NeoForge 1.21.1 localizadas:
- [4.8, file ID 9032289](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9032289): identificada na busca do catálogo, nota técnica individual não acessível.
- [5.0 — 01/11/2026](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9394057): evita travamento na criação quando a busca não encontra village e corrige posicionamento fora/abaixo do centro. Suporta spawn de jogadores novos em villages subterrâneas; opções `blacklistedVillageStructures`, `maximumVillageSearchSeconds` e `spawnNewPlayersInsideUndergroundVillages`. `locateVillageTag` torna-se seletor de estruturas com múltiplos destinos; ativação/desativação passa a valer no próximo mundo novo sem reinício. A correção de compatibilidade Biome Spawn Point identificada especificamente como Fabric não é atribuída ao alvo NeoForge.
- [6.0 — 07/11/2026](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9490279): inclui opção Spawn in Village na tela de criação do mundo e corrige interferência de Biome Spawn Point desativado na escolha da village. Correção de baú bônus declarada apenas para Fabric não é atribuída a NeoForge.
- [6.1, file ID 9518255](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9518255), também [Modrinth VXApkWmo](https://modrinth.com/mod/village-spawn-point/version/VXApkWmo): publicação localizada nos índices; nota individual não recuperada.

**Gates:** testar seeds sem village acessível, limite de tempo, vilas subterrâneas, centro/segurança, blacklist por estrutura, migração de `locateVillageTag`, novos mundos com opção ligada/desligada e coexistência com Biome Spawn Point (se presente). Validar dedicated server separado da tela client-side. Não alterar spawn de mundos existentes por inferência. **Pendente:** notas exatas 4.8 e 6.1 e catálogo integral (incluindo possíveis publicações intermediárias). Sem instalação ou testes.

## Fechamento incremental — 21/11/2026 — Village Spawn Point 4.6 → 6.1 (NeoForge 1.21.1)

**Base física:** posição **#561**, `villagespawnpoint-1.21.1-4.6.jar`, runtime **4.6**. Projeto oficial [Village Spawn Point, CurseForge 347381, Serilum](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point); categorias **Adventure and RPG + World Gen**, coincidentes com a pasta atual. **Último candidato**: **[6.1 para Minecraft 1.21.1, 08/11/2026, file ID 9518255](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9518255)**, Release com suporte declarado a NeoForge, Forge, Fabric e Quilt; distinguir builds de mesmo número para **Minecraft 26.x**.

O intervalo de arquivos oficiais desse alvo verificado é **4.6 → 4.8 → 5.0 → 6.0 → 6.1**. Não foram encontrados artefatos **4.7, 4.9, 5.1–5.9** para **Minecraft 1.21.1** na listagem oficial consultada; suas numerações em outras versões do Minecraft não justificam fabricar changelogs da 1.21.1.

| Publicação oficial NeoForge 1.21.1 | Alteração por versão e importância para o dossier |
|---|---|
| **[4.8 — 01/10/2026, File ID 9032289](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9032289)** | Altera o **package Java para `com.serilum.*`** e informações de `mods.toml`/`fabric.mod.json`; **sem mudanças funcionais anunciadas**. Impacto: possíveis integrações que dependam indevidamente de nome de classe/pacote; o identificador do JAR não prova migração automática de configurações. Não atribuir novo comportamento de spawn a esta publicação. |
| **[5.0 — 01/11/2026, File ID 9394057](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9394057)** | Corrige **travamento da criação do mundo quando nenhuma village é encontrada**, além de spawn indevido **ao lado/embaixo** em vez do centro. Acrescenta suporte a **novos jogadores surgirem em villages subterrâneas** e as opções **`blacklistedVillageStructures`**, **`maximumVillageSearchSeconds`**, **`spawnNewPlayersInsideUndergroundVillages`**. **`locateVillageTag` passa a seletor de estruturas que aceita múltiplas villages**; configurações antigas exigem reconciliação antes da substituição. Ativar/desativar o mod passa a valer no **próximo mundo novo** sem reiniciar. O fix de Biome Spawn Point assinalado **(Fabric)** não é reivindicado para NeoForge. |
| **[6.0 — 07/11/2026, File ID 9490279](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9490279)** | Novo controle **`Spawn in Village` na tela Create World** para habilitar/desabilitar spawn em village por mundo. Corrige interferência de **Biome Spawn Point desativado** que ainda impedia o spawn em villages. Fix de **baú bônus extra** é explicitamente **Fabric-only**, não fato sobre NeoForge. |
| **[6.1 — 08/11/2026, File ID 9518255](https://www.curseforge.com/minecraft/mc-mods/village-spawn-point/files/9518255)** | Corrige **`Spawn in Village` ser ignorado pelo Biome Spawn Point**, de modo que a seleção tenha efeito na integração. Não atribuir outras mecânicas à 6.1 sem fonte. A presença real de Biome Spawn Point e a compatibilidade das versões físicas devem ser verificadas separadamente. |

**Relevância operacional:** o efeito sobre mundos existentes não deve ser inferido a partir da experiência ao criar mundo novo. Para upgrade coordenado, validar seeds com e sem village próxima, timeout e fallback, seleção de várias structures, blacklist das villages dos outros mods de worldgen, spawn subterrâneo seguro, centro efetivo da estrutura, mudança de opção `Spawn in Village`, coexistência com Biome Spawn Point e seleção de spawn no servidor. Salvar `serverconfig`/configs antes de migrar o formato de `locateVillageTag`, reiniciar e entrar com jogadores novos. A própria UI de criação é client-facing, enquanto o spawn efetivo precisa continuar server-authoritative. Nenhum teste in-game/servidor foi executado.

**Conclusão:** **quatro publicações posteriores aplicáveis ao alvo NeoForge 1.21.1**, com **changelogs individuais recuperados e cotejados**; auditoria documental **CONCLUÍDA até 6.1**, sem promoção física. Basename `✅-village-spawn-point v4.6.md`, pasta e JAR físico preservados.


# WunderLib NeoForge

> **Autoridade física atual — 28/09/2026.** Ordem física **#573**: JAR `wunderlib-21.0.10.jar`, mod id `wunderlib`, runtime `21.0.10`, SHA-1 `330ab9d6d8d92a9337e97ef22e64f83fe2d4bf2a`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** WunderLib NeoForge
- **Arquivo JAR:** `wunderlib-21.0.10.jar`
- **Versão 1.21.1:** 21.0.10
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Biblioteca BetterX/New Dawn com automated config screens, grid-based UI, simplified networking e utilities compartilhadas; gameplay/conteúdo pertencem aos consumers.
- **Dependências:** Consumidores atuais registrados no pack: BetterEnd 21.0.34 e BetterNether 21.0.26; stack compartilhado com BCLib/WorldWeaver.
- **Sobreposição:** Não é redundante com BCLib, WorldWeaver, Cloth Config ou YACL; APIs/consumers distintos podem coexistir no mesmo stack.
- **Compatibilidade/Riscos:** Riscos de consumer/API drift, client/server leakage e remoção indevida por assumir que outra config library substitui WunderLib. Não remover sem dependency graph real.
- **Observações:** Mod id `wunderlib`, runtime 21.0.10, Client & Server. Decisão `Dependência` preservada enquanto BetterX consumers atuais exigirem a API.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + fontes oficiais WunderLib 21.0.10 + BetterEnd 21.0.34 e BetterNether 21.0.26 presentes. Nenhum teste runtime foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/wunderlib-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — WunderLib 21.0.10 permanece instalado e atual para NeoForge 1.21.1; decisão Dependência preservada.
- **Histórico da decisão:** 2026-08-27 — classificado como Dependência após confirmação de consumidores atuais BetterEnd/BetterNether New Dawn.
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> 🧰 **ESCOPO CANÔNICO.** Runtime físico: `wunderlib-21.0.10.jar`, mod id `wunderlib`, versão `21.0.10`. WunderLib: New Dawn é biblioteca compartilhada do ecossistema BetterX, com utilidades de **configuração/UI/networking**. Não adiciona progressão ou gameplay independente e não é intercambiável automaticamente com BCLib, WorldWeaver, Cloth Config ou YACL.
## 1. Função confirmada
A documentação oficial descreve WunderLib como continuação/port mantido que fornece:
- telas de configuração automatizadas;
- layouts de UI baseados em grid;
- gerenciamento simplificado de networking;
- outras utilities usadas pelos mods BetterX.
A build 21.0.10 é Release NeoForge 1.21.1.
## 2. Consumers atuais no pack
Os guias/Notion do projeto registram BetterEnd: New Dawn e BetterNether: New Dawn como consumers no stack compartilhado BetterX.
A modlist física atual confirma:
- BetterEnd 21.0.34;
- BetterNether 21.0.26;
- WunderLib 21.0.10.
Por isso a decisão `Dependência` permanece válida enquanto esses consumers exigirem a library.
## 3. Authority e ownership
WunderLib é authority apenas das APIs/utilities que expõe. Conteúdo visível, biomas, blocos, features e progressão pertencem aos consumers.
Não criar perks, Mastery ou quests “de WunderLib”. Não usar presença da library como proxy de que um consumer específico está ativo.
## 4. UI/configuration
Telas/configs construídas sobre WunderLib continuam representações de configurações dos consumers. Alterar uma opção precisa persistir no mecanismo do consumer/library correspondente; a UI por si não é gameplay state.
Não confundir WunderLib com YACL/Cloth Config: APIs distintas podem coexistir e consumers compilam contra contratos específicos.
## 5. Networking
A biblioteca também fornece primitives de networking simplificado. Isso aumenta o risco de classloading/protocol drift em upgrades de consumers.
Mods próprios não devem interceptar packets internos da library sem contrato público. Integrações devem preferir API/evento do consumer.
## 6. Client/server e lifecycle
A página oficial classifica WunderLib como Client & Server.
Validar:
- dedicated server boot;
- client boot;
- config screen open/save;
- reconnect;
- resource/data reload quando consumer usar essas surfaces;
- update de consumer/library em conjunto.
## 7. Riscos
1. **Consumer coupling:** update/removal isolado quebra BetterX consumer.
2. **API drift:** UI/network utilities mudam entre versões.
3. **Library substitution:** remover porque outra config lib existe quebra linkage.
4. **Client/server leakage:** UI code carregado indevidamente em dedicated server.
5. **False dependency inference:** página de relações pública não lista necessariamente todos os consumers efetivos do pack; a authority de presença é a modlist/metadata local.
## 8. Matriz de testes
- [ ] Dedicated server inicia com WunderLib 21.0.10 + BetterEnd/BetterNether atuais.
- [ ] Cliente abre/configura consumers sem UI crash.
- [ ] Alterações de config persistem após restart quando aplicável.
- [ ] Networking de consumers não gera protocol mismatch em multiplayer.
- [ ] BetterEnd/BetterNether carregam sem missing class/method.
- [ ] Remoção não é tentada sem dependency graph real.
- [ ] Update futuro é testado em conjunto com consumers, BCLib e WorldWeaver.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências
- **Modlist física 08/09/2026:** `wunderlib-21.0.10.jar`, BetterEnd 21.0.34, BetterNether 21.0.26.
- CurseForge oficial WunderLib: automated config screens, grid UI, simplified networking, Client & Server, Release 21.0.10 para NeoForge 1.21.1.
- Guia Gameplay/Notion: BetterEnd/BetterNether como consumers atuais e papel complementar com BCLib/WorldWeaver.
## 10. Limitação
Não foi feito dependency graph bytecode-level de todos os consumers nesta etapa. A classificação `Dependência` permanece baseada no stack auditado; remoção exige confirmação do vínculo real antes de qualquer mudança física.
## 11. Revalidação física — 11/09/2026
A modlist física mantém `wunderlib-21.0.10.jar`, mod id `wunderlib`, versão `21.0.10`. A release oficial 21.0.10 permanece aplicável ao NeoForge 1.21.1; o arquivo publicado exige NeoForge \>=176, requisito atendido pelo pack em 21.1.248.
BetterEnd `21.0.34` e BetterNether `21.0.26` continuam presentes como consumers registrados no stack BetterX. A decisão **Dependência** e o estado **Integrado ao Github** foram preservados. Nenhum config-screen, networking, dedicated-server ou linkage test foi executado nesta recatalogação.
## 12. Revalidação — 13/09/2026
WunderLib 21.0.10 permanece atual para NeoForge 1.21.1. BetterEnd 21.0.34 e BetterNether 21.0.26 continuam consumers atuais. Decisão Dependência preservada. Nenhum teste runtime foi executado.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** WunderLib: New Dawn
- **Último candidato 1.21.1 / NeoForge localizado:** **21.0.11 (NeoForge 1.21.1, 14/10)**
- **Estado:** **CANDIDATO COORDENADO NEW DAWN**
- **Gate de promoção:** Validar API/consumers do stack BetterNether/BetterEnd/BCLib/WorldWeaver e boot/restart antes de promoção.
- **Fonte primária do dossier reconsultada:** https://www.curseforge.com/minecraft/mc-mods/wunderlib-neoforge

## Auditoria da release 21.0.10 → 21.0.11 — 23/10/2026

**Autoridade física preservada:** posição real histórica **#573**, JAR `wunderlib-21.0.10.jar`, mod id `wunderlib`, runtime `21.0.10`. **Projeto CurseForge inequívoco:** *WunderLib: New Dawn*, autor Raijin2312, ID **1422273**, categorias temáticas **API and Library + Utility & QoL**, exatamente a pasta do dossier. Trata-se da continuação independente New Dawn, **não da biblioteca WunderLib original**; fontes da original não são automaticamente changelogs desta versão.

### Artefatos da plataforma alvo — NeoForge / Minecraft 1.21.1
- **21.0.10:** Release de **16/01/2026**, CurseForge **File ID 7469725**, nome `wunderlib-21.0.10.jar`, compatível com 1.21/1.21.1 e loader NeoForge; a página cita requisito NeoForge >= 176. Este é o JAR documentado como fisicamente instalado.
- **21.0.11:** Release de **14/10/2026**, nome de arquivo publicado **`wunderlib-21.0.11.jar`**, alvo **NeoForge 1.21 e 1.21.1**; a listagem pública do mesmo projeto confirma publicação posterior, sem artefato 1.21.1 adicional até 23/10. Outras linhas `21.11.x`, `26.1`–`26.3` e a `21.0.10-fabric` **não são atualizações da mesma combinação de jogo/loader**.

Fonte de inventário oficial: https://www.curseforge.com/minecraft/mc-mods/wunderlib-neoforge/files/all ; fonte da versão física: https://www.curseforge.com/minecraft/mc-mods/wunderlib-neoforge/files/7469725 .

### O que foi alterado no repositório oficial desde a distribuição 21.0.10
O link oficial de código do projeto aponta para **`Reijin2312/WunderLib-New-Dawn`**, branch **`master`** para NeoForge 1.21.1. O commit público de **14/10/2026**, SHA **`28f1ae8c02156a3ba5f3a9ea5bb088b45b09dba2`**, intitulado `Update release metadata and composite builds`, contém explicitamente **`mod_version=21.0.10` → `mod_version=21.0.11`** em `gradle.properties`. No mesmo commit:
- Corrige `curseforge_id` para **1422273**, identificando o projeto correto New Dawn no publisher;
- Atualiza autoria declarada em `META-INF/neoforge.mods.toml` de `Quiqueck` para `Quiqueck, Raijin2312`; preserva o mod id e o escopo de ambiente `BOTH` no template;
- Ajusta `wunderlib-composit.gradle` para **ler explicitamente o `gradle.properties` da biblioteca e expor suas propriedades ao build composto**; não equivale a introduzir APIs de gameplay ou networking.

**Fonte do diff por release:** https://github.com/Reijin2312/WunderLib-New-Dawn/commit/28f1ae8c02156a3ba5f3a9ea5bb088b45b09dba2 .

Outra mudança ocorrida na branch no intervalo entre **16/01 e 14/10**, commit **`4ae3bfb9ce0cb5926915443b1094a7292f0d235f`** de **24/07**, remove `dependsOn(runData)` da tarefa de empacotamento `jar` em `wunderlib.gradle`, **tornando datagen opt-in no build**. Trata-se de uma mudança no processo de empacotamento, não de uma correção de API no runtime demonstrada pela evidência. Fonte: https://github.com/Reijin2312/WunderLib-New-Dawn/commit/4ae3bfb9ce0cb5926915443b1094a7292f0d235f . Outras alterações de README/workflows/launcher no mesmo período são de suporte/desenvolvimento e não foram apresentadas como novas funcionalidades binárias.

**Limite de afirmação:** a versão foi atribuída diretamente no source, mas **o binário publicado não foi submetido a diff de classes, manifest ou ABI**. Não concluir que 21.0.10 e 21.0.11 sejam byte-a-byte equivalentes nem inventar correção de UI/networking ausente dos commits. O changelog técnico recuperável deste delta é essencialmente **metadados do mod/publicação e infraestrutura de build**.

### Consumers e QA separados da pesquisa documental
- [ ] Confirmar via manifest e testes no pack que BetterEnd: New Dawn, BetterNether: New Dawn, BCLib e WorldWeaver permanecem compatíveis com o artifact NeoForge `21.0.11`.
- [ ] Subir **cliente e dedicated server**; abrir e salvar telas de configuração dos consumers, reiniciar, relogar, trocar dimensão e fazer resource reload sem `NoSuchMethodError`, packet mismatch ou client-only classloading no servidor.
- [ ] Comparar manifests/mod IDs e hash do JAR antigo/novo, mantendo no mínimo os bounds de dependência do consumer e Java 21.
- [ ] Não promover isoladamente junto de BetterEnd 21.0.38, BetterNether e outras bibliotecas New Dawn: reconciliar essas cadeias por separado e testar em cópia do mundo.

**Resultado:** a **única release posterior 21.0.11** foi identificada com fonte oficial e seu **bump explícito no source** reconciliado; diferenças publicamente observáveis do código foram descritas sem inventar alterações funcionais. Para o escopo de **pesquisa documental do changelog publicado**, o item **#79 pode sair da fila**. Essa conclusão **não certifica a equivalência binária**, nem aprova a instalação/compatibilidade com os consumers. Nenhum JAR, pasta ou arquivo além deste dossier/índice foi modificado.

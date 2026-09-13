# WunderLib NeoForge

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81629a10f6d36bae6d86
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** WunderLib NeoForge
- **Arquivo JAR:** `wunderlib-21.0.10.jar`
- **Versão 1.21.1:** `21.0.10`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; UI/network utility surface, consumers e library boundaries catalogados.
- **Categoria:** Biblioteca
- **Compatibilidade/Riscos:** Riscos de consumer/API drift, client/server leakage e remoção indevida por assumir que outra config library substitui WunderLib. Não remover sem dependency graph real.
- **Decisão:** Dependência
- **Dependências:** Consumidores atuais registrados no pack: BetterEnd 21.0.34 e BetterNether 21.0.26; stack compartilhado com BCLib/WorldWeaver.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/wunderlib-neoforge
- **Função:** Biblioteca BetterX/New Dawn com automated config screens, grid-based UI, simplified networking e utilities compartilhadas; gameplay/conteúdo pertencem aos consumers.
- **Histórico da decisão:** 2026-08-27 — classificado como Dependência após confirmação de consumidores atuais BetterEnd/BetterNether New Dawn.
- **Observações:** Mod id `wunderlib`, runtime 21.0.10, Client & Server. Decisão `Dependência` preservada enquanto BetterX consumers atuais exigirem a API.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial WunderLib 21.0.10 + Guia Gameplay/Notion do stack BetterX.
- **Sobreposição:** Não é redundante com BCLib, WorldWeaver, Cloth Config ou YACL; APIs/consumers distintos podem coexistir no mesmo stack.
- **Data da última decisão:** 2026-08-27

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

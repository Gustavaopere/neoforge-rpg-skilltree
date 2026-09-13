# Thirst Was Reclaimed

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db81bb8529d4f7a2b5c7e8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `ThirstWasReclaimed-1.21.1-3.0.4.jar`, mod id `thirst`, runtime `3.0.4`; Thirst Was Fixed 2.1.6 e Sophisticated Thirst Upgrade 0.1.8 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Thirst Was Reclaimed 3.0.4 e seus addons citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Thirst Was Reclaimed
- **Arquivo JAR:** `ThirstWasReclaimed-1.21.1-3.0.4.jar`
- **Versão 1.21.1:** 1.21.1-3.0.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Categoria:** Comida, RPG
- **Função:** Provider principal de sede/hidratação do pack: mantém thirst do jogador, purity da água/containers, item settings e sincronização server→client; 3.0.4 expõe thirst via persistent data e refaz Jade.
- **Dependências:** NeoForge 1.21.1. Extensions presentes: Thirst Was Fixed 2.1.6 e Sophisticated Thirst Upgrade 0.1.8. Integrações relevantes no stack incluem Create 6.0.10, Jade e Cold Sweat 2.4.2.
- **Sobreposição:** Não é um segundo sistema de fome ou nutrição; adiciona a necessidade separada de hidratação.
- **Compatibilidade/Riscos:** Purity migration/propagation, config hash/sync stale, addon double ownership e HUD overlap. Linha 3.0.x trata purity ausente como max, permite disable global e sincroniza settings; testar transfers Bottle/Bucket/Pipes/Create e addons.
- **Observações:** mod id `thirst`; runtime 3.0.4. É a única authority base de thirst do pack. Config física/item hydration values não foram lidos; addons não devem ser tratados como providers paralelos.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Thirst Was Reclaimed 3.0.4 + source oficial 1.21.1 + addons físicos Thirst Was Fixed 2.1.6 e Sophisticated Thirst Upgrade 0.1.8. Dossiê de 09/09 preservado; config, purity e transferências runtime não foram testadas.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed/files/8391910 ; https://github.com/mlus-asuka/Thirst-Was-Reclaimed
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Thirst Was Reclaimed 3.0.4 permanece exatamente instalado; thirst/purity, transfer propagation, config sync, Jade/persistent data, addons, lifecycle, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ThirstWasReclaimed-1.21.1-3.0.4.jar`, mod id `thirst`, versão `1.21.1-3.0.4`. Thirst Was Reclaimed é o **provider principal de sede/hidratação** do pack: mantém thirst do jogador, regras de consumo e sistema de purity da água.

## 1. Identidade, versão e papel
- **Mod:** Thirst Was Reclaimed.
- **JAR:** `ThirstWasReclaimed-1.21.1-3.0.4.jar`.
- **Mod id:** `thirst`.
- **Versão:** `3.0.4` para MC 1.21.1.
- **Loader:** NeoForge.
- **Ambiente:** Client & Server.
- **Papel:** necessidade separada de hidratação, projetada para compatibilidade com modpacks.

## 2. Authority e ownership
Thirst Was Reclaimed é authority de:
- valor de thirst do jogador;
- efeitos/regras base de hidratação;
- purity de água/containers quando habilitada;
- item settings e regras sincronizadas do sistema.

Thirst Was Fixed e Sophisticated Thirst Upgrade são extensões; não devem manter uma segunda barra/state concorrente.

## 3. Thirst state do jogador
O mod adiciona um recurso separado da fome. O servidor deve ser a fonte de verdade do nível de thirst; HUD e integrations apenas representam/consomem esse state.

A 3.0.4 passou a expor o thirst level também via `player.getPersistentData()`, criando uma superfície oficial para addons/scripts lerem o recurso sem duplicá-lo.

## 4. Purity — semântica da linha 3.0.x
O refactor 3.0.0 alterou profundamente purity:
- removeu default purity configurável anterior;
- items/fluids sem tag de purity passam a ser tratados como **max purity** por padrão;
- valores inválidos são sanitizados;
- `enablePurity` permite desativar completamente o sistema;
- com purity desativada, tags/effects de purity não devem ser aplicados.

Addons como Thirst Was Fixed precisam obedecer esta semântica.

## 5. Propagação de purity em containers e fluids
A linha 3.0.0 corrigiu propagation para:
- bottles;
- buckets;
- pipes;
- campfire recipes;
- Create integration;
- Tough As Nails compatibility;
- mountain/running water cases.

Isso demonstra que purity é data que acompanha água através de múltiplos caminhos de transferência, não apenas atributo visual do item.

## 6. Item settings e keyword rules
O provider possui server item settings e keyword rules para definir quanto itens/fluidos hidratam e como são reconhecidos.

A linha 3.0.x sincroniza essas regras com clients. A configuração física do pack não foi lida; não registrar valores de hydration específicos sem o arquivo runtime.

## 7. Config synchronization
3.0.0 introduziu/expandiu sync de:
- server item settings;
- keyword rules;
- purity enablement;
- custom purity containers.

O protocolo usa hash SHA-256 para que cliente só solicite config completa quando a versão do servidor mudou.

Isso exige limpeza/reload correto ao trocar de servidor para evitar regras stale.

## 8. HUD e AppleSkin
A linha 3.0.0 inclui correções de coordenadas para thirst HUD/tooltip em integração AppleSkin, evitando overlap visual.

O pack possui múltiplos HUD/QoL mods; qualquer ausência de sede visual deve ser separada entre state do servidor e renderer do cliente.

## 9. Jade — release 3.0.4
A mudança principal 3.0.4 refatora integração Jade para exibir:
- fluid storage;
- purity.

O pack usa Jade no ecossistema; tanks/containers suportados devem apresentar valor coerente com o state real, sem converter tooltip em authority.

## 10. Create e fluid ecosystem
O source 1.21.1 inclui Create como integration/runtime de desenvolvimento e a linha 3.0.0 menciona fix explícito de purity propagation com Create.

No pack com Create 6.0.10 e muitos fluid addons, validar bucket/bottle/pipe/tank transitions e evitar perda de purity durante transferências.

## 11. Cold Sweat e outros survival mods
O source 1.21.1 inclui Cold Sweat entre integrações de desenvolvimento. O pack possui Cold Sweat 2.4.2.

Isto não significa que Thirst e temperature sejam o mesmo recurso: cada sistema mantém authority própria. Testar apenas a integração publicada/runtime, sem fundir balanceamento de thirst e body temperature na documentação.

## 12. Addons concretos no pack
- **Thirst Was Fixed 2.1.6:** corrige cauldrons e integra Ars/Ultimine/ParCool/Amendments.
- **Sophisticated Thirst Upgrade 0.1.8:** extensão para storage/backpacks sofisticados, catalogada separadamente.
- **Cold Sweat 2.4.2:** sistema térmico separado.
- **Create 6.0.10:** fluid transfer integration relevante.
- **Jade:** inspection de storage/purity na 3.0.4.

## 13. Client / server e multiplayer
- Servidor: thirst, purity e regras configuradas.
- Cliente: HUD/tooltips e caches sincronizados.
- Cliente não pode escolher hydration/purity local para superar regra do servidor.
- Troca de servidor deve invalidar hash/config anterior quando necessário.

## 14. Lifecycle
Validar:
- criação/login do player;
- beber item/fluido e morte/respawn;
- relog/server restart;
- bucket↔bottle↔tank↔pipe;
- Create transfer;
- purity ON/OFF em cópia de teste;
- troca entre servidores com configs distintas;
- Jade inspection após mudança de purity;
- addons lendo persistent thirst data.

## 15. Riscos técnicos
1. **Purity migration:** worlds/items antigos podem ter tags da semântica anterior.
2. **Transfer propagation:** qualquer ponte de fluid pode perder/sanitizar purity incorretamente.
3. **Config desync:** hydration/purity rules stale no cliente.
4. **Addon double ownership:** extensions não podem criar state paralelo.
5. **HUD overlap:** AppleSkin/outros HUDs podem esconder ou deslocar indicador.
6. **PersistentData consumers:** addon pode interpretar escala/ausência de dado incorretamente; validar contrato antes de scripting.
7. **Purity disabled:** addons precisam respeitar modo global sem reintroduzir tags/effects.

## 16. Matriz de testes
- [ ] Dedicated server boot com TWR 3.0.4.
- [ ] Thirst reduz/aumenta e persiste conforme gameplay esperado.
- [ ] Morte/respawn/relog não duplica ou zera state indevidamente.
- [ ] Purity ausente é interpretada segundo semântica 3.0.x.
- [ ] Purity inválida é sanitizada sem crash.
- [ ] `enablePurity` OFF remove efeitos/tags esperados em ambiente de teste.
- [ ] Bottle/bucket/pipe/Create transfer preserva purity.
- [ ] Cliente recebe item settings/keyword/purity config do servidor.
- [ ] Troca de servidor não mantém config/hash stale.
- [ ] Jade mostra fluid storage/purity coerentes.
- [ ] Thirst Was Fixed e Sophisticated Thirst Upgrade usam o mesmo state base.
- [ ] Persistent player data expõe thirst para integração sem criar segunda authority.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 17. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e addons/integrations presentes.
- CurseForge oficial 3.0.4: build NeoForge 1.21.1, Jade refactor e persistent thirst data.
- Changelog oficial 3.0.0: purity refactor, transfer fixes, config sync/hash e AppleSkin HUD fixes.
- Repositório oficial branch 1.21.1: modpack-oriented thirst provider e integration surface; source usado para arquitetura, não para inventar valores de config locais.

## 18. Revalidação física — 11/09/2026
O runtime físico continua exatamente `ThirstWasReclaimed-1.21.1-3.0.4.jar`, mod id `thirst`, versão `3.0.4`. A release oficial 3.0.4 permanece a build NeoForge 1.21.1 pertinente e continua sendo a authority única de thirst/purity do pack.

Thirst Was Fixed `2.1.6` e Sophisticated Thirst Upgrade `0.1.8` permanecem extensões separadas. Nenhum teste de purity migration, Bottle/Bucket/Pipe/Create transfer, Jade, config hash/sync ou persistent-data consumer foi executado nesta recatalogação.

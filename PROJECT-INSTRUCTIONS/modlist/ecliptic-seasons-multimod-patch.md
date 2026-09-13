# Ecliptic Seasons: MultiMod Patch

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ae9a76d81c5608a16f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Ecliptic Seasons: MultiMod Patch
- **Arquivo JAR:** `Ecliptic-Seasons-MultiMod-Patch-1.21.1-neoforge-0.32.1.jar`
- **Versão 1.21.1:** 0.32.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Clima
- **Função:** Bridge de compatibilidade do Ecliptic Seasons que injeta comportamento sazonal/meteorológico em mods terceiros sem criar um segundo sistema de estações.
- **Dependências:** Ecliptic Seasons. Integrações 1.21.1 oficialmente suportadas e fisicamente ativas no pack: Presence Footsteps 1.12.0-beta.1, JourneyMap 6.0.7, Cold Sweat 2.4.2, Dynamic Trees 1.7.2 e MineColonies 1.1.1381 snapshot.
- **Sobreposição:** Não substitui Ecliptic Seasons nem os mods-alvo. Atua somente como adapter entre o provider sazonal e sistemas externos; Bundles cobre outra camada, baseada em datapacks/resource packs.
- **Compatibilidade/Riscos:** Acoplamento entre Ecliptic Seasons e APIs dos mods-alvo. Riscos concretos: cálculo sazonal de temperatura/chuva divergente, snow overlay/map stale, crescimento Dynamic Trees fora do ciclo sazonal, sleep timing MineColonies inconsistente, Presence Footsteps usando som incorreto em snowy blocks e patch aplicado a versão upstream diferente.
- **Observações:** Runtime físico confirmado: 0.32.1. A referência antiga `0.32.0-beta` estava obsoleta e foi removida. A tabela oficial 1.21.1 também lista In Control, Pretty Rain, Snowy Spirit, Haunted Harvest, Touhou Little Maid e Diagonal Blocks; eles não são tratados como integrações ativas sem presença física confirmada.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `Ecliptic-Seasons-MultiMod-Patch-1.21.1-neoforge-0.32.1.jar`, mod id `eclipticseasons_multimodpatch` e versão 0.32.1. CurseForge oficial confirma build Beta 0.32.1 de 05/09/2026 e a matriz de patches 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ecliptic-seasons-multimod-patch
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — MultiMod Patch 0.32.1; adapters sazonais, providers ativos no pack, lifecycle, client/server, riscos e matriz de regressão catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `Ecliptic-Seasons-MultiMod-Patch-1.21.1-neoforge-0.32.1.jar` · mod id `eclipticseasons_multimodpatch` · versão `0.32.1` · NeoForge 1.21.1.

## 1. Papel no modpack
Ecliptic Seasons: MultiMod Patch é a camada de **compatibilidade sazonal** entre Ecliptic Seasons e outros mods. Ele não calcula um calendário paralelo: consome a estação/termo solar/clima do Ecliptic Seasons e adapta comportamentos de terceiros que, sem patch, ignorariam essas mudanças.

## 2. Authority / ownership
- **Ecliptic Seasons:** estação, termo solar, regras sazonais e clima-base.
- **MultiMod Patch:** adapters/mixins para traduzir esse estado a APIs de outros mods.
- **Mods-alvo:** continuam authority de seus próprios sistemas, como temperatura corporal, mapas, árvores e cidadãos.

A bridge nunca deve se tornar uma segunda fonte de verdade para temperatura, crescimento, sono ou precipitação.

## 3. Build 0.32.1
A build física 0.32.1 é a release Beta oficial para NeoForge 1.21.1 publicada em 05/09/2026. A referência antiga da ficha a `0.32.0-beta` estava obsoleta e foi retirada.

## 4. Integrações 1.21.1 oficialmente suportadas
A matriz oficial 1.21.1 marca patches para In Control, Pretty Rain, Presence Footsteps, Snowy Spirit, JourneyMap, Cold Sweat, Dynamic Trees, Haunted Harvest, MineColonies, Touhou Little Maid e Diagonal Blocks. Fey Wild não tem patch 1.21.1, Ambient Sounds não tem patch 1.21.1 e Simple Clouds aparece sem suporte 1.21.1.

Esta lista é **capacidade do mod**, não prova de que todos esses providers estão instalados no pack.

## 5. Providers ativos confirmados no pack
A modlist física confirma cinco alvos relevantes da matriz oficial:
- **Presence Footsteps 1.12.0-beta.1**;
- **JourneyMap 6.0.7**;
- **Cold Sweat 2.4.2**;
- **Dynamic Trees 1.7.2**;
- **MineColonies 1.1.1381-1.21.1-snapshot**.

Esses cinco devem compor a regressão prioritária do patch nesta instalação.

## 6. Cold Sweat
O patch oficial adiciona **variação sazonal de temperatura** e corrige cálculo de rainfall usado na integração. Cold Sweat continua controlando temperatura corporal/efeitos térmicos; o patch apenas injeta contexto sazonal.

Risco: dupla aplicação caso outro adapter converta a estação em offset térmico novamente.

## 7. Dynamic Trees
A integração adiciona um **seasonal provider** para que crescimento de árvores siga ciclos sazonais. Dynamic Trees continua authority de species, growth, branches e worldgen; o patch influencia o crescimento por estação.

Regression gate: mesma species em termos solares distintos, crescimento após reload e ausência de dupla penalização sazonal.

## 8. JourneyMap
O patch faz o mapa representar corretamente **blocos cobertos por neve**. Isso é apresentação cartográfica; não cria neve nem altera block state server-side.

Cache de tiles precisa ser revalidado quando a cobertura sazonal muda.

## 9. Presence Footsteps
Em snowy blocks, a compatibilidade troca o som de passo para o equivalente de **snow layer**. A mudança é de áudio/ambiente e deve acompanhar o estado visual/seasonal do bloco sem afetar colisão ou gameplay.

## 10. MineColonies
O patch ajusta dinamicamente o **horário de sono dos cidadãos** conforme a estação. MineColonies continua authority do citizen AI/schedule; a bridge injeta a referência sazonal.

Validar mudança de estação durante uma colônia ativa, restart e citizens já dormindo.

## 11. Outros adapters oficiais
A matriz também documenta, entre outros, sandstorm behavior para Pretty Rain em desert rain, winter skiing para Snowy Spirit, Halloween events para Haunted Harvest, snowy sweeping por Touhou Little Maid e suporte limitado a snowy definitions em Diagonal Blocks.

Nada disso é tratado como ativo neste pack sem confirmação física do provider correspondente.

## 12. Client / Server
- estação/clima e efeitos gameplay: server/common-authoritative conforme Ecliptic Seasons/provider-alvo;
- map/sound/visual snow representation: client-facing;
- mixins/adapters precisam ser condicionais ao mod/version corretos;
- dedicated server não deve carregar classes puramente de render/map/audio quando o alvo não está presente.

## 13. Lifecycle
Validar:
- bootstrap com todos os alvos instalados;
- world load/restart;
- mudança de termo solar/estação;
- chuva/neve;
- datapack/config reload quando suportado;
- conexão/reconexão multiplayer;
- chunk unload/reload;
- atualização isolada de qualquer provider integrado.

## 14. Multiplayer / idempotência
O servidor deve produzir um único resultado sazonal. Cliente não pode aplicar novamente temperatura, crescimento ou schedule. Patches visuais precisam apenas refletir o state final sincronizado.

## 15. Riscos
1. mixin targeting mudar após update do mod-alvo;
2. seasonal temperature aplicada duas vezes;
3. rainfall/precipitation divergir entre providers;
4. Dynamic Trees crescer fora do ciclo esperado;
5. JourneyMap manter snow tiles stale;
6. Presence Footsteps usar som de neve em estado incorreto;
7. MineColonies sleep time não atualizar;
8. adapter carregar sem provider opcional;
9. patch funcionar em boot mas degradar após mudança de estação;
10. confundir capacidade listada com mod realmente instalado.

## 16. Matriz de testes
1. Dedicated server boot com Ecliptic Seasons + cinco alvos ativos confirmados.
2. Cold Sweat: medir resposta em termos sazonais quentes/frios sem double offset.
3. Dynamic Trees: growth em duas estações e após restart.
4. JourneyMap: área com/sem cobertura de neve após mudança sazonal.
5. Presence Footsteps: som em snowy/non-snowy state.
6. MineColonies: citizen sleep schedule em mudança de estação.
7. Chuva/neve durante transição de termo solar.
8. Reconnect de cliente no meio de condição sazonal.
9. Atualizar um provider em instância de teste e conferir mixin/adapter.
10. Remover um provider opcional em cópia descartável e confirmar ausência de classloading indevido.

**Esta catalogação não afirma que esses testes foram executados.**

## 17. Evidências
- modlist física canônica de 08/09/2026: JAR, mod id, versão 0.32.1 e providers ativos;
- CurseForge oficial 0.32.1: build NeoForge 1.21.1 Beta;
- tabela oficial de compatibilidade 1.21.1: conteúdos patchados por mod.

> **Boundary canônico:** Ecliptic Seasons fornece o **estado sazonal**; MultiMod Patch apenas o traduz para os sistemas de terceiros.

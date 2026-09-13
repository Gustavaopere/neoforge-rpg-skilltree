# Ritchie's Projectile Library

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81fdbd5ac0df058cd39f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar`, mod id `ritchiesprojectilelib`, runtime `2.1.2`, mixins `ritchiesprojectilelib.mixins.json` e `ritchiesprojectilelib-forge.mixins.json`; Create Big Cannons 5.11.7 presente como consumer causal
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Ritchie's Projectile Library 2.1.2 e Create Big Cannons 5.11.7 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Ritchie's Projectile Library
- **Arquivo JAR:** `ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar`
- **Versão 1.21.1:** 2.1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, RPG, Tecnologia
- **Função:** Biblioteca compartilhada para projéteis modded rápidos/long-range, com sincronização de movimento, chunkloading, projectile bursts e screen shake.
- **Dependências:** Consumer causal confirmado: Create Big Cannons 5.11.7 usa RPL 2.1.2 como infraestrutura física de projéteis no stack atual.
- **Sobreposição:** Não é equivalente a NukaTeam's Gun Lib nem substitui a lógica de projectile do CBC; fornece infraestrutura compartilhada aos consumers.
- **Compatibilidade/Riscos:** Riscos: network registry drift, precise-motion desync, forced-chunk leak/premature unload, burst/shake duplicados, consumer API drift e pressão de rede/chunks sob alto volume de projectiles.
- **Observações:** Release 2.1.2 NeoForge 1.21.1. Changelog exato corrige network registry e cannon shake effects no NeoForge. Mixin configs físicos: `ritchiesprojectilelib.mixins.json` e `ritchiesprojectilelib-forge.mixins.json`.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + CurseForge oficial RPL 2.1.2 + README oficial RPL + dossiê Create Big Cannons 5.11.7 já auditado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ritchies-projectile-library
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Ritchie's Projectile Library 2.1.2 reconstruído: precise motion, projectile chunkloading/bursts, screen shake, networking, CBC dependency, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-09-10 — Sem decisão → Dependência. Create Big Cannons 5.11.7 está presente e sua ficha canônica confirma RPL 2.1.2 como dependência física do stack atual.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar`, mod id `ritchiesprojectilelib`, versão `2.1.2`. A modlist atual contém Create Big Cannons 5.11.7, cuja ficha canônica confirma Ritchie's Projectile Library como dependência física concreta para infraestrutura de projéteis. Portanto RPL é **dependência load-bearing do CBC atual**, não uma library sem consumidor confirmado.

## 1. Identidade e papel
- **Mod:** Ritchie's Projectile Library.
- **JAR:** `ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar`.
- **Mod id:** `ritchiesprojectilelib`.
- **Versão:** `2.1.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Mixin configs físicos:** `ritchiesprojectilelib.mixins.json` e `ritchiesprojectilelib-forge.mixins.json`.
- **Papel:** biblioteca compartilhada para projéteis rápidos/long-range, sincronização, chunkloading, bursts e screen shake.

## 2. Consumidor causal confirmado
O pack contém `createbigcannons-5.11.7+mc.1.21.1.jar`. A ficha completa de Create Big Cannons registra RPL 2.1.2 como dependência física concreta da linha atual.

Consequência: RPL não deve ser removida ou atualizada isoladamente sem regressão de CBC e de qualquer outro consumer que possa usar a mesma infraestrutura.

## 3. Autoridade e ownership
- **RPL:** infraestrutura compartilhada de movimento/sync, chunkloading de projéteis, bursts e screen shake.
- **Create Big Cannons:** cannon/munition/projectile semantics, dano, impacto, fuzes, recoil e conteúdo próprio.
- **Outros consumers:** continuam donos de seus projéteis e regras de gameplay.

RPL fornecer a entidade/tag/utilidade não a torna authority do dano ou da balística específica do consumer.

## 4. Precise motion
O projeto documenta a tag de entity type `#ritchiesprojectilelib:precise_motion`, que envia dados mais detalhados de movimento e posição aos clientes.

Essa superfície existe para reduzir problemas visuais/de sincronização em projéteis rápidos. O servidor continua authority da posição funcional/collision; dados mais precisos enviados ao cliente não devem criar uma segunda simulação autoritativa.

## 5. Chunkloading para projéteis
RPL fornece um sistema configurável de chunkloading voltado a projéteis modded de alta velocidade e longo alcance. A documentação oficial descreve duas propriedades operacionais importantes:
- chunks forceloaded que deixam de ser necessários são descarregados;
- apenas uma parcela dos chunks necessários é carregada por vez para reduzir impacto de performance quando há muitos projéteis long-range.

Essa camada é crítica para projéteis CBC que atravessam fronteiras de chunk rapidamente.

## 6. Projectile bursts
A biblioteca fornece **projectile bursts** para simular fragmentação, pellets de shotgun e shrapnel sem depender de grandes quantidades de entidades individuais.

Ownership do burst permanece no consumer: RPL oferece a infraestrutura, mas quantidade, dano, dispersão e efeito concreto pertencem ao mod que dispara o burst.

## 7. Screen shake
RPL fornece screen shake para mods focados em armas/artilharia. É uma superfície principalmente client-facing, mas o evento causal precisa derivar de um disparo/impacto válido do servidor ou do consumer.

A build 2.1.2 corrige especificamente **cannon shake effects no NeoForge**, tornando esse fluxo regression gate da versão instalada.

## 8. Networking — delta 2.1.2
O changelog oficial da 2.1.2 registra:
- correção do **network registry no NeoForge**;
- correção de **cannon shake effects no NeoForge**.

Portanto startup/handshake de rede e propagação de shake são os dois gates de regressão diretamente vinculados à build física.

## 9. Client / Server
Spawn, posição funcional, collision e efeitos de gameplay dos projéteis devem permanecer server-authoritative. Interpolation e screen shake são client-facing.

Um packet perdido/duplicado não pode criar projectile funcional extra nem processar impacto duas vezes.

## 10. Lifecycle de projéteis
Validar:
- spawn e registration;
- entrada em `precise_motion` quando o consumer utiliza a tag;
- travessia de chunks carregados/não carregados;
- descarte do projectile;
- liberação dos chunks forceloaded;
- reconnect de cliente enquanto projéteis existem;
- server restart sem projectile/chunk ticket órfão;
- dimension boundary quando aplicável ao consumer.

## 11. Integração concreta com Create Big Cannons
CBC 5.11.7 usa RPL como infraestrutura de projectile. A própria ficha CBC já trata como riscos:
- drift RPL quebrando spawn/collision;
- projectile atravessando chunk/unload e perdendo collision/detonação;
- desync multiplayer de impacto.

Esses riscos devem ser testados dos dois lados da boundary: RPL infrastructure e CBC semantics.

## 12. Performance
RPL tenta evitar a solução ingênua de manter todos os chunks ou criar grande volume de entidades. Ainda assim, muitos projéteis long-range podem pressionar:
- chunk tickets;
- rede;
- collision checks;
- client interpolation;
- bursts simultâneos.

Benchmark deve usar o pack real, não presumir custo baixo apenas porque a infraestrutura é otimizada.

## 13. Sobreposição
RPL não é equivalente a NukaTeam's Gun Lib. RPL é uma biblioteca focada em infraestrutura de projéteis; NukaTeam's Gun Lib é um framework amplo de armas/gun packs.

Também não substitui projectile logic de CBC; apenas fornece primitives compartilhadas usadas pelo consumer.

## 14. Riscos técnicos
1. **Network registry regression:** área corrigida na 2.1.2.
2. **Precise-motion desync:** cliente recebe posição divergente/stale.
3. **Forced-chunk leak:** ticket permanece após projectile terminar.
4. **Premature unload:** chunk descarrega antes do projectile concluir collision.
5. **Burst duplication:** fragmentação processada mais de uma vez.
6. **Screen-shake duplication:** shake repetido por packet/client listener duplicado.
7. **Consumer API drift:** CBC espera assinatura/comportamento diferente.
8. **High-load pressure:** muitos projectiles saturam chunk/network budget.

## 15. Matriz de testes
- [ ] Dedicated server inicia com RPL 2.1.2 + CBC 5.11.7 sem network registry error.
- [ ] Cliente conecta sem handshake/registry mismatch.
- [ ] Projectile CBC normal nasce e impacta uma única vez.
- [ ] Projectile rápido cruza múltiplos chunks sem sumir, duplicar ou atravessar collision válida.
- [ ] Chunks forceloaded são liberados após o projectile deixar de precisar deles.
- [ ] Entidade em `precise_motion` permanece visualmente coerente em multiplayer remoto.
- [ ] Burst gera o efeito esperado sem criar evento duplicado.
- [ ] Cannon shake aparece uma única vez no cliente e não carrega classe client-only no dedicated server.
- [ ] Reconnect durante projectile ativo não cria entidade fantasma.
- [ ] Stress com vários projectiles não deixa tickets órfãos após todos terminarem.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física canônica de 10/09/2026: RPL 2.1.2 e CBC 5.11.7 presentes.
- CurseForge oficial 2.1.2: arquivo exato e fixes de network registry/cannon shake no NeoForge.
- README oficial RPL: `precise_motion`, chunkloading, screen shake e projectile bursts.
- Dossiê canônico de Create Big Cannons: confirma RPL como dependência física concreta.
- **Limite:** parâmetros de chunkloading, formato de packets e classes internas não foram inventados sem source pin específico.

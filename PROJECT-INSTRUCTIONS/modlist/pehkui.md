# Pehkui

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81d7b014ccfdb0981ccd
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Pehkui-3.8.3+1.21-neoforge.jar`, mod id `pehkui`, runtime `3.8.3+1.21-neoforge`, mixin `pehkui.mixins.json`; `epicfightpehkuiincompatibilityfix-1.0.2.jar` presente e Identity2 ausente no snapshot físico
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Pehkui 3.8.3 e o fix Epic Fight↔Pehkui 1.0.2 estão presentes, enquanto Identity2 está ausente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Pehkui
- **Arquivo JAR:** `Pehkui-3.8.3+1.21-neoforge.jar`
- **Versão 1.21.1:** 3.8.3+1.21-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, RPG
- **Função:** Framework/sistema de escala de entidades que permite alterar tamanho e escalas associadas de jogadores e mobs, incluindo dimensões, alcance e propriedades derivadas usadas por mods consumidores.
- **Dependências:** NeoForge 1.21/1.21.1. Integração física confirmada: Epic Fight - Pehkui Incompatibility FIX 1.0.2. Identity2 NÃO consta na modlist física atual e foi removido como consumer.
- **Sobreposição:** Framework de escala, não morph/combat system. Epic Fight fix é bridge separado; Identity2 não está presente na instalação atual.
- **Compatibilidade/Riscos:** Build NeoForge 3.8.3 é Beta. Riscos: loader drift (upstream testou 21.0.20-beta; pack usa 21.1.248), hitbox/camera, passenger offsets, persistence e armature Epic Fight. Fix 1.0.2 está instalado para a interseção Epic Fight↔Pehkui.
- **Observações:** File ID 5446174, Beta NeoForge para 1.21/1.21.1. Changelog da build: update para 1.21, sem additions/changes/bugfixes específicos. Não substituir pela Release Fabric apenas para obter canal estável.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da build NeoForge 3.8.3 + presença física do Epic Fight-Pehkui fix 1.0.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/pehkui
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Pehkui 3.8.3 NeoForge reconstruído: Beta boundary, scale authority, Epic Fight fix, camera/hitbox/passengers, persistence, riscos e testes; Identity2 stale removido.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Pehkui-3.8.3+1.21-neoforge.jar`, mod id `pehkui`, versão literal `3.8.3+1.21-neoforge`, NeoForge 1.21/1.21.1. A build NeoForge é oficialmente **Beta**, embora 3.8.3 também tenha release estável em outros loaders. O pack contém `epicfightpehkuiincompatibilityfix-1.0.2.jar`; **Identity2 não está presente na modlist física atual** e foi removido desta ficha como consumer.

## 1. Identidade e papel
- **Mod:** Pehkui.
- **JAR físico:** `Pehkui-3.8.3+1.21-neoforge.jar`.
- **Mod id:** `pehkui`.
- **Runtime:** `3.8.3+1.21-neoforge`.
- **Loader/jogo:** NeoForge 1.21/1.21.1.
- **Autor:** Virtuoel.
- **CurseForge project ID:** 319596.
- **Licença:** MIT.
- **Canal NeoForge:** Beta.
- **Papel:** framework/sistema para redimensionar entidades, permitindo torná-las menores ou maiores e oferecendo infraestrutura de escala para mods consumidores.
- **Decisão:** Sem decisão.

## 2. Escala como estado funcional
Pehkui não é apenas um transform visual. Redimensionar uma entidade precisa permanecer coerente com suas dimensões funcionais e propriedades derivadas suportadas pela implementação.

Superfícies críticas incluem:
- tamanho/volume visual;
- bounding/dimensions;
- posição dos olhos/câmera;
- interação física com blocos/entidades;
- offsets de passageiros/mounts;
- alcance/propriedades que consumers possam associar à escala.

Esta ficha não enumera scale types internos sem necessidade/source pinado para cada um.

## 3. API e consumers
O valor principal de Pehkui em modpacks é fornecer uma API comum para outros mods alterarem escala sem implementar um sistema paralelo.

Isso significa que remover Pehkui deve ser precedido por dependency graph real. Nesta modlist atual, o consumer/integração física claramente identificado é o **Epic Fight - Pehkui Incompatibility FIX 1.0.2**, que existe justamente porque Pehkui e Epic Fight coexistem.

A antiga referência a **Identity2** não é mais válida: não há JAR Identity/Identity2 no snapshot físico atual.

## 4. Build NeoForge 3.8.3
O arquivo oficial `[1.21] [NeoForge] Pehkui 3.8.3` é Beta, file ID `5446174`, com suporte publicado a Minecraft 1.21 e 1.21.1.

O changelog desta build é deliberadamente mínimo:
- update para 1.21;
- Bugfixes: N/A;
- Changes: N/A;
- Additions: N/A;
- Removals: N/A.

Portanto não há base para inventar uma feature nova específica da build NeoForge além do port/update de compatibilidade.

## 5. Test baseline upstream
A publicação registra último teste bem-sucedido upstream em Minecraft 1.21 com NeoForge `21.0.20-beta`.

O pack usa NeoForge **21.1.248**, muito mais novo. Isso não implica incompatibilidade, mas torna smoke/regressão da build atual obrigatória; não se deve confundir “publicado para 1.21.1” com “testado pelo autor em exatamente 21.1.248”.

## 6. Epic Fight incompatibility fix
O pack contém `epicfightpehkuiincompatibilityfix-1.0.2.jar`. A finalidade já catalogada desse bridge é mitigar problema conhecido de escala/altura dos braços/olhos na coexistência Pehkui↔Epic Fight.

Boundary:
- Pehkui continua authority da escala;
- Epic Fight continua authority da armature/combat animation;
- o fix apenas adapta a interseção e deve ter página própria.

Não atribuir ao Pehkui base o comportamento do fix.

## 7. Hitbox, câmera e primeira pessoa
Escala de player/mob é particularmente sensível a câmera e collision perception.

Testar:
- player pequeno/grande em primeira pessoa;
- eye height vs camera position;
- passagem por espaços baixos/estreitos;
- ataque/interaction range aparente vs real;
- First Person Model/animation stack;
- spectator/third-person transitions.

Visual correto com hitbox errada ou vice-versa é falha relevante.

## 8. Mounts e passageiros
Mudança de escala pode alterar offsets de mount/passenger. O histórico do projeto já inclui fixes em versões anteriores para riding offsets escalados, então essa superfície é um regression gate legítimo mesmo sem afirmar bug atual.

Testar horses, boats, seats/vehicles modded e entidades em contraptions quando aplicável.

## 9. Multiplayer e server authority
Escala que afeta collision/interação precisa convergir no servidor. Dois clientes não podem discordar funcionalmente sobre o tamanho usado para hit/collision.

Testes multiplayer devem cobrir:
- mudança de escala observada por outro player;
- reconnect;
- dimension change;
- death/respawn;
- chunk unload/reload;
- entidade escalada entrando em mount.

## 10. Persistência
Se um consumer persiste escala, o estado precisa sobreviver ao lifecycle previsto e ser limpo quando a origem do modifier deixa de existir.

Riscos:
- scale duplicada após relog;
- reset inesperado;
- modifier temporário virando permanente;
- entidade salva com dimensions inconsistentes.

A ficha não inventa formato NBT/component/capability da 3.8.3.

## 11. Composição com morph/camera/combat
Mesmo sem Identity2, o pack possui múltiplas camadas que podem tocar player model/camera/combat. O teste deve separar:
- escala real de Pehkui;
- pose/armature de Epic Fight;
- render de first-person/third-person;
- qualquer morph/size mechanic de outros mods.

O fix Epic Fight-Pehkui reduz um conflito conhecido, mas não garante todas as combinações.

## 12. Riscos
1. **Beta NeoForge:** única build oficial NeoForge 1.21/1.21.1 é Beta.
2. **Loader drift:** upstream testou NeoForge 21.0.20-beta; pack usa 21.1.248.
3. **Epic Fight armature:** mitigado por fix 1.0.2, mas precisa regressão.
4. **Hitbox/camera mismatch:** escala visual e funcional podem divergir em integração ruim.
5. **Passenger offsets:** mounts/vehicles são superfície histórica sensível.
6. **Persistence:** scale temporária pode duplicar/resetar.
7. **Consumer attribution:** erro em API pode ser causado por consumer incompatível.
8. **Stale catalog references:** Identity2 foi removido da modlist e não deve mais justificar Pehkui.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Pehkui 3.8.3 NeoForge no loader 21.1.248.
- [ ] Escalar player menor/maior altera visual e collision de forma coerente.
- [ ] Eye height/camera acompanham escala sem clipping extremo.
- [ ] Outro cliente observa escala correta e hit/collision converge ao servidor.
- [ ] Epic Fight + fix 1.0.2 mantém braços/olhos/armature coerentes em escalas representativas.
- [ ] Attack/interact em player pequeno/grande não usa range visual enganoso sem entendimento do consumer.
- [ ] Mount/passenger offsets permanecem válidos.
- [ ] Death/relog/dimension/restart preservam ou limpam scale conforme origem real.
- [ ] First-person/third-person stack não deixa câmera presa após reset de escala.
- [ ] Remover/reaplicar modifier de consumer não acumula escala.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `Pehkui-3.8.3+1.21-neoforge.jar`, mod id/runtime e `pehkui.mixins.json`.
- CurseForge oficial: file ID 5446174, Beta NeoForge, suporta 1.21/1.21.1, upload 19/06/2024.
- Changelog oficial: update para 1.21, sem additions/changes/bugfixes específicos; baseline test NeoForge 21.0.20-beta.
- Pack físico: `epicfightpehkuiincompatibilityfix-1.0.2.jar` presente; **Identity2 ausente**.
- **Limite:** scale types internos, comandos e cada propriedade numérica não foram enumerados sem necessidade/source específico; compatibilidade com NeoForge 21.1.248 precisa de runtime smoke.

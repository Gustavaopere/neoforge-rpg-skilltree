# Sable Assembly Fix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db8187a5f5cbf9f648ae34
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `SableAssemblyFix-1.0.0.jar`, mod id `sableassemblyfix`, runtime `1.0.0`, mixin `sableassemblyfix.mixins.json`; NeoForge 21.1.248, Sable 2.0.5, Create Aeronautics 1.3.2 e Create 6.0.10 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sable Assembly Fix 1.0.0 e todos os requisitos físicos citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable Assembly Fix
- **Arquivo JAR:** `SableAssemblyFix-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Compat
- **Função:** Patch server-side que impede duplicação de inventários em block entities não-Clearable durante assembly/disassembly Sable, interceptando a limpeza/remoção no SubLevelAssemblyHelper.
- **Dependências:** Requisitos oficiais satisfeitos fisicamente: NeoForge >=21.1.228 (pack 21.1.248), Sable >=2.0 (pack 2.0.5) e Create Aeronautics >=1.0.2 (pack 1.3.2). Create 6.0.10 também está presente.
- **Sobreposição:** Não adiciona storage nem física paralela; corrige a boundary de remoção/clear no lifecycle Sable/Aeronautics.
- **Compatibilidade/Riscos:** Patch server-side invasivo no call-site de assembly. Riscos: false positive em BE com lifecycle próprio, whitelist incorreta, API drift de Sable, double-removal e state loss em containers custom. Config whitelist exige restart para recarregar.
- **Observações:** A ficha antiga dizia 'sem configuração'; a documentação atual descreve `config/sableassemblyfix/whitelist.json`, criado automaticamente, com reload somente após restart.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sable Assembly Fix 1.0.0 e descrição técnica do mecanismo/whitelist.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-assembly-fix
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sable Assembly Fix 1.0.0 reconstruído: causa do dupe, Clearable boundary, @Redirect/removeBlockEntity, whitelist, lifecycle, riscos e testes.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `SableAssemblyFix-1.0.0.jar`, mod id `sableassemblyfix`, versão `1.0.0`, NeoForge 1.21.1. É um patch server-side específico do ciclo de assembly/disassembly do Sable para impedir duplicação de inventários em block entities que não implementam `Clearable`.

## 1. Identidade e papel
- **Mod:** Sable Assembly Fix.
- **JAR:** `SableAssemblyFix-1.0.0.jar`.
- **Mod id:** `sableassemblyfix`.
- **Versão:** `1.0.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Server.
- **Mixin físico:** `sableassemblyfix.mixins.json`.
- **Papel:** corrigir dupe no transporte de block entities durante montagem/desmontagem de estruturas Sable.

## 2. Dependências físicas
A publicação exige NeoForge `>=21.1.228`, Sable `>=2.0` e Create: Aeronautics `>=1.0.2`. O pack atual satisfaz os três contratos com NeoForge 21.1.248, Sable 2.0.5 e Create Aeronautics 1.3.2.

Create 6.0.10 está presente como provider-base do ecossistema de contraptions.

## 3. Bug que o patch corrige
Durante `SubLevelAssemblyHelper.moveBlocks()`, Sable chama `Clearable.tryClear()` em block entities transferidas. Para BEs que implementam `Clearable`, o inventário é limpo antes da remoção. Para BEs que **não** implementam essa interface, `tryClear()` não faz nada.

Se o bloco é então destruído, seu conteúdo pode cair no mundo, enquanto o NBT preserva o mesmo inventário. Ao recriar a BE no sublevel, os itens voltam do NBT: uma cópia fica no chão e outra permanece no bloco.

## 4. Severidade e gatilho
O upstream classifica o exploit como barato: alternar repetidamente a estrutura entre modos que provoquem assembly/disassembly pode repetir a duplicação.

Exemplos explicitamente citados incluem TableCloth e Mechanical Arm; o problema é genérico para inventories modded que não implementam `Clearable`.

## 5. Mecanismo exato do fix
A implementação oficial usa um único `@Redirect` no call-site de `Clearable.tryClear()` dentro de `SubLevelAssemblyHelper.moveBlocks()`:
- se a BE implementa `Clearable`, a chamada original passa normalmente;
- se não implementa, o patch chama `level.removeBlockEntity(pos)` antes da destruição do bloco, evitando que a BE antiga participe do drop de conteúdo.

O patch não cria storage próprio, não copia inventário e não altera a física do Sable.

## 6. Authority e ownership
- **Sable:** assembly/disassembly, sublevels e lifecycle físico.
- **Create Aeronautics/Create:** estruturas e integrações correspondentes.
- **Mod da block entity:** inventory/NBT e regras próprias do bloco.
- **Sable Assembly Fix:** somente intercepta o ponto inseguro de limpeza/remoção.

A correção deve executar uma única vez por transferência e não assumir ownership permanente do conteúdo.

## 7. Configuração — correção da ficha antiga
A página atual documenta um whitelist em `config/sableassemblyfix/whitelist.json`, criado automaticamente no primeiro launch.

IDs de block entities adicionados ali **pulam o fix**, útil quando um mod já trata a própria remoção de forma segura. A documentação informa que a whitelist exige **restart do jogo/servidor para recarregar**.

Portanto a frase antiga “não requer configuração” não deve ser tratada como descrição completa da build atual.

## 8. Client / server
O mod é server-side. O cliente não precisa decidir se a BE deve ser limpa/removida. Drops, inventory state e assembly são resultados autoritativos do servidor.

Um cliente modificado não deve conseguir provocar duas liquidações do mesmo inventário; o patch atua no caminho server-side de transferência.

## 9. Lifecycle crítico
Validar:
- assembly com BE `Clearable`;
- assembly com BE inventory não-`Clearable`;
- disassembly;
- alternância repetida entre modos;
- BE vazia e cheia;
- stack parcial e stack completo;
- chunk unload/reload durante estrutura montada;
- restart com estrutura persistente;
- BE adicionada/removida da whitelist.

## 10. Compatibilidade com block entities modded
O upstream cita explicitamente compatibilidade genérica com inventories como kegs, cabinets, baskets, Item Drains e Sophisticated Storage. Isso significa que o patch é orientado ao contrato `Clearable`, não a uma lista rígida de mods.

Ainda assim, BEs com lifecycle incomum — containers externos, inventories virtuais ou lógica custom de break — precisam de regressão individual antes de serem colocadas na whitelist.

## 11. Riscos técnicos
1. **False positive:** uma BE não-`Clearable` possui lógica própria e o remove antecipado interfere nela.
2. **Whitelist incorreta:** excluir BE vulnerável reabre o dupe.
3. **API drift:** assinatura/call-site de `SubLevelAssemblyHelper.moveBlocks()` muda em Sable.
4. **Double removal:** outro patch remove a BE no mesmo lifecycle.
5. **State loss:** BE custom depende de callback que deixa de ocorrer após `removeBlockEntity`.
6. **Config stale:** operador edita whitelist mas não reinicia e interpreta o comportamento antigo como bug.

## 12. Matriz de testes
- [ ] Dedicated server inicia com NeoForge 21.1.248 + Sable 2.0.5 + Aeronautics 1.3.2 + patch 1.0.0.
- [ ] Chest/hopper `Clearable` mantém comportamento vanilla esperado.
- [ ] Mechanical Arm com stack cheio não duplica ao montar/desmontar.
- [ ] BE não-`Clearable` de terceiro preserva exatamente um inventário após ciclo completo.
- [ ] Alternar assembly/disassembly repetidamente não aumenta contagem de itens.
- [ ] Break/drop depois do disassembly produz somente os itens legítimos.
- [ ] BE whitelisted ignora o patch conforme configuração.
- [ ] Restart recarrega alteração da whitelist.
- [ ] Chunk unload/reload não reintroduz BE antiga ou inventário duplicado.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física atual: JAR/mod id/version, NeoForge 21.1.248, Sable 2.0.5 e Aeronautics 1.3.2.
- CurseForge oficial 1.0.0: causa do dupe, `Clearable.tryClear()`, uso de `@Redirect`, `removeBlockEntity`, whitelist e requisitos mínimos.
- **Limite:** não foi executado teste runtime nem inventariado o comportamento interno de cada BE modded; compatibilidade individual continua dependente de teste.

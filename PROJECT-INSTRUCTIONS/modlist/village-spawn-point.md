# Village Spawn Point

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db81a193bce8ada1ab2fd6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `villagespawnpoint-1.21.1-4.6.jar`, mod id `villagespawnpoint`, runtime `4.6`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, `villagespawnpoint-1.21.1-4.6.jar` está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Village Spawn Point
- **Arquivo JAR:** `villagespawnpoint-1.21.1-4.6.jar`
- **Versão 1.21.1:** 4.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Worldgen, Exploração
- **Função:** Utilidade server-side que escolhe o world spawn inicial de mundos novos no centro de uma village encontrada; não gera villages nem altera seu conteúdo.
- **Dependências:** NeoForge 1.21.1 e dependências declaradas pelo projeto. O resultado depende do worldgen/villages realmente reconhecidos no runtime.
- **Sobreposição:** Não substitui Integrated Villages, Dynamic Village, Stoneholm ou outros settlements; apenas consome uma village reconhecida como destino de spawn.
- **Compatibilidade/Riscos:** Validar custo de busca inicial, posição segura, seed determinism e quais villages modded são realmente reconhecidas. Não assumir que toda estrutura visualmente semelhante a village é candidata. Existing worlds não devem ser reposicionados por inferência.
- **Observações:** Mod id `villagespawnpoint`, runtime 4.6, server-side. Atua apenas na seleção do spawn inicial; providers de settlements continuam authorities do conteúdo da village.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Village Spawn Point 4.6. A versão 4.6 permanece a linha aplicável a Minecraft 1.21.1; releases 4.7 pertencem a linhas posteriores e não são atualização aplicável automática. Nenhum world-creation/runtime test executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/village-spawn-point
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Village Spawn Point 4.6 permanece exatamente instalado e continua a release aplicável a Minecraft 1.21.1; world-spawn boundary, seed/worldgen, riscos e testes preservados.
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

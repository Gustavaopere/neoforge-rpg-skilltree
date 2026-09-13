# Clumps

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81ce805cc3540cc0d1c2  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist.txt`, 595 mods top-level  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Clumps
- **Arquivo JAR:** `Clumps-neoforge-1.21.1-19.0.0.1.jar`
- **Versão 1.21.1:** `19.0.0.1`
- **Categoria:** Performance; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/clumps
- **Função:** Otimização específica de XP orbs: agrupa orbes próximos para reduzir a quantidade de entidades e o custo de tick/coleta sem transformar XP em uma moeda paralela.
- **Dependências:** Sem hard dependency adicional confirmada para a build NeoForge 19.0.0.1. Atua sobre a entidade/fluxo de experiência vanilla; compat precisa ser medida com mods que também alterem XP orbs ou coleta.
- **Compatibilidade/Riscos:** Riscos com mods que substituem XP orb merge/pickup/value settlement, vacuum/XP storage, magnetism ou perks acionados por pickup. 19.0.0.1 é o port 1.21.1 que restaura/corrige o funcionamento Forge/NeoForge da linha; XP total não pode ser perdido ou creditado duas vezes.
- **Sobreposição:** Não substitui otimizações gerais de memória/render/culling. Sobreposição concreta existe apenas com mods que alteram diretamente XP orb spawning, merging, pickup ou settlement.
- **Observações:** mod id `clumps`; runtime 19.0.0.1. Objetivo oficial: clump XP orbs together to reduce lag. Release 19.0.0.1 porta para 1.21.1 e corrige/restaura suporte Forge.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Clumps 19.0.0.1 para NeoForge 1.21.1 + source oficial do projeto usado para o contrato de agrupamento de XP orbs.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Clumps 19.0.0.1 foi reconfirmado no JAR físico e reconstruído como otimização específica de XP orbs. A presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Clumps 19.0.0.1 físico confirmado; XP-orb merge/value authority, pickup interoperability, lifecycle, multiplayer e regressão da linha 19.0.0.1 confirmados no QC global #98. Runtime QA/benchmark não executado.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `Clumps-neoforge-1.21.1-19.0.0.1.jar`, mod id `clumps`, runtime `19.0.0.1`. A função é deliberadamente estreita: **agrupar XP orbs para reduzir lag**, preservando a economia de experiência.

## 1. Papel e authority

Clumps reduz a quantidade de entidades XP orb ao combinar orbes próximos. O valor total de experiência continua sendo a economia vanilla/provider; Clumps não deve criar um segundo saldo de XP.

A otimização é válida somente se merge e pickup preservarem exatamente o valor que existiria sem o mod.

## 2. Merge semantics

Quando vários XP orbs elegíveis se agrupam, o resultado representa a soma/estado equivalente dos orbes de origem. A operação precisa ser idempotente: um orb absorvido não pode continuar tickando/coletável como entidade independente.

Não inferir radius/timing numéricos sem config/source pin específico da build.

## 3. Pickup

O jogador deve receber XP uma única vez do clump resultante. Mods de magnet, vacuum, backpack, XP storage ou collection hooks podem interceptar pickup; a integração correta preserva o valor final e não dispara o settlement para cada orb histórico + clump.

## 4. Performance model

A redução de lag vem da diminuição do número de entidades/ticks de orbes, não de uma otimização global do jogo. Benefício depende da quantidade de XP entities presentes no cenário real.

Não atribuir a Clumps ganhos de render/worldgen/memory que pertencem a outros mods.

## 5. Release 19.0.0.1

A release 1.21.1 documenta port e correção/restauração do suporte Forge. Para o pack NeoForge, o gate principal é confirmar que merge realmente ocorre e não apenas que o JAR carrega.

Nenhum novo sistema de gameplay é atribuído à build por ausência de changelog adicional.

## 6. XP sources modded

Mobs, farms, machines, quests ou outros providers podem gerar XP orbs vanilla/custom. Clumps só deve agrupar entidades compatíveis com seu pipeline.

Não converter automaticamente XP líquido, stored XP, levels de outro mod ou currencies em XP orbs para que Clumps as processe.

## 7. Interoperabilidade com XP storage

Mods de tanks/crystals/XP networks podem sugar orbes. Se a coleta ocorrer antes/depois do merge, o saldo final precisa ser o mesmo.

Retry, chunk unload ou concorrência de collectors não pode creditar um clump em dois inventories.

## 8. Client/server e multiplayer

Spawn, merge, valor do orb e pickup são server-authoritative. Render/particles do orb são apresentação.

Em multiplayer, dois jogadores/collectors competindo pelo mesmo clump precisam resolver em uma única coleta server-side.

## 9. Lifecycle

Validar spawn, merge, chunk unload/reload, server restart, death drops, farm contínua e coleta durante lag. Orbs parcialmente fundidos não podem reaparecer duplicados após reload.

## 10. Riscos

1. XP total perdido durante merge.
2. Double-credit em pickup hooks.
3. Magnet/vacuum coletar origem e clump.
4. Custom XP orb incompatível.
5. Chunk reload duplicar entidades.
6. Perks dispararem por contagem de orbs e mudarem comportamento ao clumpar.
7. Confundir redução de entity count com otimização geral.

## 11. Matriz de testes

1. Spawn controlado de vários XP orbs e comparar soma antes/depois.
2. Pickup manual: XP final idêntico ao controle.
3. Farm de mobs com grande volume de XP.
4. Magnet/vacuum/XP storage presente.
5. Dois jogadores tentando coletar o mesmo clump.
6. Chunk unload/reload com orbs no chão.
7. Server restart antes da coleta.
8. Death XP drops.
9. Perks/advancements acionados por ganho de XP sem double trigger.
10. Confirmar redução real de entity count em cenário de stress.

## 12. Evidência

- modlist física atual: Clumps 19.0.0.1 NeoForge;
- CurseForge oficial: agrupa XP orbs para reduzir lag;
- release 19.0.0.1: port para 1.21.1 e correção/restauração do suporte Forge da linha;
- nenhum valor de merge/radius foi inventado sem pin de config/source da build.

> ✨ Boundary canônico: Clumps **otimiza a representação das XP orbs**; o valor econômico de experiência não deve mudar.

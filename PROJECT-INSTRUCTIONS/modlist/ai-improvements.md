# AI-Improvements

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814e82add353b6da5ce8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AI-Improvements
- **Arquivo JAR:** `AI-Improvements-1.21-0.5.3.jar`
- **Versão 1.21.1:** `0.5.3`
- **Estado no pack:** Integrado ao Github
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #17: `AI-Improvements-1.21-0.5.3.jar` / `0.5.3` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Categoria:** Performance
- **Compatibilidade/Riscos:** Pack também contém Enhanced AI 4.2.3.0. Papéis são distintos, mas ambos tocam IA; validar goals/pathfinding e qualquer toggle de desativação. Ganho de performance deve ser medido, não presumido. Nenhum conflito confirmado nesta auditoria.
- **Decisão:** Sem decisão
- **Dependências:** Nenhuma dependência funcional obrigatória relevante identificada para a build 1.21.x 0.5.3.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ai-improvements
- **Função:** Otimização low-level da IA vanilla para reduzir overhead de processamento e permitir desativação configurável de certos comportamentos; foco server-side/singleplayer.
- **Histórico da decisão:**
- **Observações:** Não atribuir ganho genérico sem benchmark do pack. Testar mobs vanilla e modded, especialmente quando qualquer opção de desativação de AI for alterada.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth/CurseForge oficiais AI Improvements 0.5.3 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `AI-Improvements-1.21-0.5.3.jar` / `0.5.3`; sem divergência física.
- **Sobreposição:** Não é duplicata de Enhanced AI: AI-Improvements otimiza/reduz trabalho; Enhanced AI acrescenta comportamento. Sobreposição existe apenas na superfície técnica de AI tick/goals/pathfinding.
- **Data da última decisão:**

## Escopo e papel
Mod de **otimização de IA vanilla**. O objetivo upstream é reduzir custo de processamento e permitir desativar certos comportamentos de AI, com foco em server-side/singleplayer. Ele não tenta tornar mobs mais inteligentes; atua no orçamento/execução da IA existente.

## Runtime e autoridade
- JAR físico: `AI-Improvements-1.21-0.5.3.jar`.
- Mod ID: `aiimprovements`.
- Runtime: `0.5.3`.
- A build 1.21.x 0.5.3 é publicada para Minecraft 1.21–1.21.4 em NeoForge.

## Dependências
Nenhuma dependência funcional obrigatória relevante foi identificada na publicação da build 1.21.x. É um mod de infraestrutura de performance, não uma biblioteca consumida por outros sistemas do pack.

## Relações no pack
O snapshot atual contém também **Enhanced AI 4.2.3.0**. Os papéis são diferentes: AI-Improvements reduz/evita trabalho da IA vanilla; Enhanced AI acrescenta/expande capacidades e decisões. Isso não prova incompatibilidade, mas cria uma superfície de composição real porque ambos podem tocar ciclo de tick, goals, pathfinding ou decisões de entidades.

## Compatibilidade, sobreposição e riscos
O ganho de performance depende de cenário e configuração; não deve ser assumido sem profiling. Em um pack com muitos mods de mobs e comportamento, qualquer opção que desative AI precisa ser verificada contra entidades modded e Enhanced AI para evitar perda silenciosa de mecânicas. Conflito confirmado não foi encontrado nesta auditoria.

## Limites
Não é substituto de Enhanced AI, não adiciona novos ataques, pathfinding sofisticado ou dificuldade. Também não resolve automaticamente custo de render, ticking de block entities ou worldgen.

## Testes recomendados
1. Benchmark com Spark/observable profiler em cenário controlado com e sem AI-Improvements.
2. Massa de entidades vanilla e modded; medir MSPT e tempo gasto em AI/pathfinding.
3. Validar comportamentos do Enhanced AI com as opções padrão e, depois, com qualquer AI toggle alterado.
4. Testar mobs de mods com goals próprios para detectar AI desativada indevidamente.
5. Dedicated server com carga prolongada e comparação de TPS/MSPT, GC e entity tick time.

## Evidências
- [Modrinth oficial — AI Improvements 1.21.x 0.5.3](https://modrinth.com/mod/ai-improvements/version/dGNP90t0)
- Modlist física atual e guia consolidado de Gameplay/Sistemas.

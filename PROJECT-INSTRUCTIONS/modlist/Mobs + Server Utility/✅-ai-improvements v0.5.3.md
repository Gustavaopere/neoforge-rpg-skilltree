# AI-Improvements

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#17**: `AI-Improvements-1.21-0.5.3.jar`, mod id `aiimprovements`, runtime `0.5.3`, SHA-1 `b4a8e11384454bcc341043b251db7fb5afdfdf45`.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 16/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AI-Improvements
- **Arquivo JAR:** `AI-Improvements-1.21-0.5.3.jar`
- **Versão 1.21.1:** `0.5.3`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 14/09/2026 — ownership/performance scope, config fail-closed, ambiente server-side, lifecycle, profiling gates e fingerprint físico documentados; coexistência com Enhanced AI preservada sem declarar conflito inexistente.
- **Categoria:** Performance
- **Compatibilidade/Riscos:** Pack também contém Enhanced AI 4.2.3.0. Papéis são distintos, mas ambos tocam IA; validar goals/pathfinding e qualquer toggle de desativação. Ganho de performance deve ser medido, não presumido. Nenhum conflito confirmado nesta auditoria.
- **Decisão:** Sem decisão
- **Dependências:** Nenhuma dependência funcional obrigatória relevante identificada para a build 1.21.x 0.5.3.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ai-improvements
- **Função:** Otimização low-level da IA vanilla para reduzir overhead de processamento e permitir desativação configurável de certos comportamentos; foco server-side/singleplayer.
- **Histórico da decisão:** vazio
- **Observações:** Ganho de performance deve ser medido, não presumido. Config efetiva da instância não foi fornecida; não afirmar quais toggles estão ativos. Validar mobs vanilla/modded e Enhanced AI em dedicated server com profiling.
- **Procedência:** modlist.txt física do projeto consultada em 14/09/2026 + publicação oficial AI Improvements 0.5.3 para NeoForge 1.21.x. Artefato `AI-Improvements-1.21-0.5.3.jar`, runtime `0.5.3`, SHA-1 `b4a8e11384454bcc341043b251db7fb5afdfdf45`.
- **Sobreposição:** Não é duplicata de Enhanced AI: AI-Improvements otimiza/reduz trabalho; Enhanced AI acrescenta comportamento. Sobreposição existe apenas na superfície técnica de AI tick/goals/pathfinding.
- **Data da última decisão:** vazio
- **Estado no pack:** Integrado ao Github

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


## Ownership técnico e fronteira de responsabilidade
- **Ownership primário:** reduzir overhead de IA vanilla e permitir que determinados comportamentos sejam desativados/configurados; não adiciona novas decisões, ataques ou dificuldade.
- **Mod ID físico:** `aiimprovements`.
- O inventário físico não expôs nome de mixin config para este JAR. Isso **não autoriza concluir que o mod não usa patches internos**; significa apenas que o extrator da modlist não listou um arquivo de mixin associado.
- A fronteira com `Enhanced AI 4.2.3.0` é funcionalmente distinta: AI-Improvements busca reduzir custo/trabalho, enquanto Enhanced AI adiciona/expande comportamento.
## Configuração e dados
A publicação upstream documenta capacidade de desativar certos comportamentos de IA, mas a lista exata de chaves e valores efetivos desta instância **não foi fornecida nem lida neste lote**. Portanto, qualquer conclusão sobre quais otimizações/toggles estão ativos permanece fail-closed até inspeção do config gerado da instância.
## Client/server e multiplayer
A build 0.5.3 é publicada para ambiente server-side/singleplayer. O efeito relevante deve ser medido no servidor lógico: entity tick, goals, pathfinding, TPS/MSPT e GC. Em cliente conectado, não se deve atribuir melhora de renderização, partículas ou GPU a este mod. Dedicated server é o cenário prioritário para medir ganho e detectar divergência de comportamento.
## Lifecycle e regressões
Validar bootstrap, carregamento de mundo, spawn massivo, unload/reload de chunks e sessões prolongadas. O risco principal não é corrupção de save, mas alteração silenciosa de AI quando uma opção interfere em goals de mobs vanilla/modded ou quando outro mod assume um comportamento vanilla que foi reduzido/desativado.
## Fingerprint físico
- JAR: `AI-Improvements-1.21-0.5.3.jar`
- Runtime: `0.5.3`
- SHA-1: `b4a8e11384454bcc341043b251db7fb5afdfdf45`

## Testes recomendados
1. Benchmark com Spark/observable profiler em cenário controlado com e sem AI-Improvements.
2. Massa de entidades vanilla e modded; medir MSPT e tempo gasto em AI/pathfinding.
3. Validar comportamentos do Enhanced AI com as opções padrão e, depois, com qualquer AI toggle alterado.
4. Testar mobs de mods com goals próprios para detectar AI desativada indevidamente.
5. Dedicated server com carga prolongada e comparação de TPS/MSPT, GC e entity tick time.

## Evidências
- [Modrinth oficial — AI Improvements 1.21.x 0.5.3](https://modrinth.com/mod/ai-improvements/version/dGNP90t0)
- Modlist física atual e guia consolidado de Gameplay/Sistemas.

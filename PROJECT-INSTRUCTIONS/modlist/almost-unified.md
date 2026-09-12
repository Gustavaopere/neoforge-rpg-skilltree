# Almost Unified

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81738bd3d77cec1462a8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Almost Unified
- **Arquivo JAR:** `almostunified-neoforge-1.21.1-1.4.2.jar`
- **Versão 1.21.1:** 1.21.1-1.4.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia, Metalurgia
- **Função:** Camada de unificação data-driven de recursos equivalentes entre mods. Usa tags configuradas para escolher um item dominante por material, reescreve ingredientes/outputs de receitas para aceitar e produzir o recurso canônico, trata receitas especiais incluindo Create Sequenced Assembly, oferece diagnósticos/logs e integra recipe viewers. Não altera ore generation; normaliza os materiais depois que múltiplos providers já existem.
- **Dependências:** Não foi localizada dependência hard externa obrigatória além do loader na release 1.4.2. Integrações com recipe viewers e ingredientes/mods específicos são condicionais. Para Immersive Engineering, o unifier embutido foi removido na linha 1.3.0 e passou a exigir addon separado se essa integração específica for necessária.
- **Sobreposição:** Sobrepõe parcialmente scripts/compat packs que convertem cobre/zinco/bronze/aços/plates/dusts entre mods, mas seu papel é sistêmico e data-driven. Não substitui bridges que precisam de semântica específica de máquina, fluidos, recipes especiais ou APIs próprias.
- **Compatibilidade/Riscos:** Risco principal é escolher o provider dominante errado: máquinas, recipes, quests ou estética podem passar a produzir o item de outro mod. `modPriorities`/priority overrides precisam refletir a autoridade desejada do pack. Ingredientes não canônicos permanecem utilizáveis quando são tagificados, mas outputs devem convergir para o dominante. Validar custom ingredients, tags conflitantes, recipes sem condições aplicáveis, Create Sequenced Assembly e eventuais integrações de mods específicos. Não confundir unificação com remoção de minério/worldgen.
- **Observações:** A configuração efetiva do usuário deve ser lida antes de qualquer decisão sobre qual item é canônico. Exemplos públicos de config servem apenas como referência de formato; não presumir que prioridades de outros modpacks correspondem a este pack.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/wiki/source oficiais Almost Unified 1.4.2 + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/almostunified
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — unificação data-driven, priorities, custom ingredients, Create Sequenced Assembly, reload e config-bound authority confirmados no QC global #22.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `almostunified-neoforge-1.21.1-1.4.2.jar`, Minecraft 1.21.1/NeoForge. Esta ficha documenta o motor de unificação e os riscos de configuração. Ela **não presume** quais materiais o seu pack escolheu como dominantes sem ler a configuração efetiva.

## 1. O que Almost Unified faz
Almost Unified resolve o problema clássico de packs grandes em que vários mods registram materiais conceitualmente equivalentes — por exemplo, um mesmo metal em formas de ingot, nugget, plate, dust, raw material ou storage block — e cada mod tende a produzir a própria variante. Em vez de apagar registries ou impedir que os mods carreguem, Almost Unified usa **tags e prioridades configuráveis** para definir um recurso dominante e reescrever receitas para convergir para ele.

O comportamento central é composto por três etapas:
1. identificar grupos de recursos equivalentes pelas tags configuradas;
2. escolher o **dominant item** segundo `modPriorities`, overrides e regras de ownership/prioridade;
3. reescrever ingredientes e outputs elegíveis para que receitas aceitem equivalentes via tags, mas retornem preferencialmente o item canônico.

Isso significa que itens não dominantes normalmente continuam existindo no registry e podem continuar entrando em receitas quando pertencem às tags corretas. O objetivo é impedir que o fluxo normal de crafting/máquinas multiplique versões incompatíveis do mesmo recurso.

## 2. O que ele NÃO faz
- Não remove mods ou registries de metais.
- Não substitui o worldgen de minério.
- Não decide sozinho qual material é “melhor” para este modpack; isso depende da configuração.
- Não garante compatibilidade semântica quando dois itens têm o mesmo nome/tag mas propriedades especiais diferentes.
- Não substitui bridges que precisam traduzir energia, fluid capabilities, recipe serializers, componentes de item ou lógica proprietária.

## 3. Seleção do item dominante
A wiki upstream documenta prioridade de mods como o principal mecanismo de escolha. Mods listados mais acima têm preferência; conteúdo não listado tende a ficar abaixo da prioridade explícita. Overrides podem resolver exceções por tag/material.

Para este pack, qualquer auditoria futura deve responder, por material: **qual provider deve ser autoridade?** Exemplo: se dois mods fornecem uma plate de bronze, escolher a variante dominante por estética apenas pode quebrar uma máquina que consulta item ID próprio em vez de tag. Portanto a ordem correta é: verificar consumidores reais → escolher autoridade → configurar prioridade → testar recipes.

## 4. Superfícies de receita
Almost Unified atua sobre recipes recarregáveis e possui unifiers para formatos suportados. A linha 1.21.1 expandiu suporte a ingredientes customizados/compound ingredients do NeoForge e a recipe types de mods.

Na linha instalada:
- **1.4.0:** adicionou suporte a **Create Sequenced Assembly**.
- **1.4.1:** corrigiu recipes de Sequenced Assembly que não pertenciam diretamente ao Create e deixavam de ser unificadas.
- **1.4.2:** corrigiu unificação que falhava quando placeholder definitions estavam vazias.

A linha 1.3.0 também trouxe otimizações importantes de carregamento/config ignore e passou a ignorar recipes cujas condições não correspondem ao ambiente atual, evitando transformar recipes que nem deveriam estar ativos.

## 5. Integração com ingredientes e recipe viewers
A linha moderna possui suporte a custom ingredients e API para unifiers de ingredientes próprios. Isso é relevante em NeoForge porque addons podem usar compound/custom ingredient types em vez de simples `Ingredient` vanilla.

Almost Unified também consegue expor o resultado da unificação a recipe viewers, permitindo identificar recipes alteradas e, conforme configuração, reduzir ruído de variantes redundantes. A presença de JEI/EMI/REI não deve ser confundida com dependência de gameplay: o viewer é camada de inspeção.

## 6. Diagnóstico e logs
O mod registra diagnósticos úteis para pack authors, incluindo situações como:
- item atribuído a múltiplas tags de unificação;
- recipe types inválidos/não suportados;
- custom tags ou ownerships incoerentes;
- recipes que não podem ser transformadas com segurança.

Esses logs são parte da auditoria. Silenciar warning sem entender a tag pode esconder uma divergência de material.

## 7. Configuração — categorias que precisam ser auditadas
A nomenclatura/estrutura de arquivos mudou ao longo das versões; portanto **não copiar caminhos de versões antigas como se fossem canônicos para 1.4.2**. Conceitualmente, a configuração cobre:
- prioridades de mods;
- priority overrides por material/tag;
- tags/materiais a unificar;
- stone variants/strata quando aplicável;
- custom tags;
- tag ownership/inheritance;
- itens/tags/recipe types/recipes ignorados;
- comportamento de recipe viewer/hiding conforme a configuração da versão.

Exemplos de outros modpacks são úteis para aprender o formato, mas não são evidência da configuração desta instância.

## 8. Reload e datapack lifecycle
A unificação trabalha no pipeline recarregável de recipes/dados. Mudanças de configuração/dados devem ser validadas após o reload apropriado/reentrada conforme exigido pela versão. Para qualquer script/datapack que gere recipes depois do processo de unificação, verificar a ordem de reload; recipes criadas fora da fase esperada podem escapar do transform.

## 9. Immersive Engineering
Na linha 1.3.0 o unifier específico embutido de **Immersive Engineering** foi removido e separado em addon dedicado. Consequência: não assumir que a presença de Almost Unified base garante todas as peculiaridades de recipes do IE. Se o pack depender dessa integração, verificar se o addon separado está instalado e ativo.

Isso não significa que tags simples de IE deixem de funcionar; significa que suporte especial não deve ser inferido do core.

## 10. Riscos específicos do pack
1. **Autoridade errada de material:** o output canônico pode vir do mod menos desejado.
2. **ID-hardcoding:** máquina/quest/script pode exigir `modid:item` e rejeitar equivalente por tag.
3. **Properties não equivalentes:** itens visualmente equivalentes podem carregar components/capabilities diferentes.
4. **Recipe loops ou perdas:** transforms de recipes complexas precisam ser verificados especialmente em sequências industriais.
5. **Create Sequenced Assembly:** suporte existe, mas deve ser testado com addons Create do pack, não apenas recipes base.
6. **Scripts tardios:** recipes injetadas depois do processo podem não ser unificadas.
7. **Recipe viewer:** esconder variante não significa que ela deixou de existir; troubleshooting deve inspecionar registry/tag.

## 11. Sobreposição funcional
Quase qualquer compat pack que converta “metal A de mod X” em “metal A de mod Y” toca o mesmo problema. Almost Unified é preferível para a **normalização genérica**, enquanto bridges específicas continuam necessárias quando precisam traduzir:
- recipes proprietárias;
- fluidos;
- máquinas;
- capabilities/components;
- progressão ou gates;
- itens com comportamento especial.

Portanto “há Almost Unified” nunca deve ser usado como justificativa automática para remover uma compat bridge.

## 12. Matriz mínima de validação
1. Ler a configuração efetiva e listar todos os materiais/tags unificados.
2. Para cada material, registrar o provider dominante esperado e o efetivamente escolhido.
3. Verificar logs de itens em múltiplas tags/conflitos.
4. Testar crafting vanilla de input não dominante → output dominante.
5. Testar smelting/blasting e recipes de máquinas presentes no pack.
6. Testar **Create Sequenced Assembly** e pelo menos uma sequência de addon Create.
7. Testar recipes com custom/compound ingredients.
8. Confirmar que item legado/não dominante existente em baús continua aceito onde deveria.
9. Confirmar que quests/scripts que usam IDs específicos não foram quebrados.
10. Inspecionar JEI e distinguir “oculto no viewer” de “removido do jogo”.
11. Executar reload/reentrada e confirmar resultado idempotente, sem recipes mudarem a cada reload.
12. Se Immersive Engineering estiver em uso, validar a integração especial separadamente.

## 13. Mudanças relevantes da linha 1.21.1
- 1.1.x: ampliação de diagnósticos/logging e correções para outputs de mods.
- 1.2.x: custom ingredients/compound ingredients, API de unifiers, correções de integração.
- 1.3.0: otimização de carga/ignores, recipe conditions e separação da integração especial de Immersive Engineering.
- 1.4.0: Create Sequenced Assembly.
- 1.4.1: correção para Sequenced Assembly de terceiros.
- **1.4.2 instalada:** correção para empty placeholder definitions.

## 14. Fontes e confiança
**Authority física:** modlist do pack em 07/09/2026.

**Fontes upstream:** [CurseForge — Almost Unified](https://www.curseforge.com/minecraft/mc-mods/almostunified) e documentação/wiki/source oficiais do AlmostReliable.

**Confiança:** alta para versão, finalidade, pipeline conceitual e changelog. A escolha concreta de materiais canônicos do usuário permanece **não determinada** até leitura da configuração efetiva; nenhum exemplo público de outro pack deve preencher essa lacuna.

# Integrated Patches — 1.2.0

> **Registro preliminar, não certificado.** Fora da modlist auditada. Não atribui posição física, selo ou homologação. Não é um segundo dossier canônico.

## Estado documental — 27/11/2026

- **Mod:** Integrated Patches, Aarods.
- **Projeto CurseForge:** 1690092, licença All Rights Reserved.
- **Versão instalada declarada:** 1.2.0 (Release, Minecraft 1.21.1 / NeoForge); declaração do usuário, sem leitura do JAR local.
- **Arquivo JAR oficial:** `integrated_patches v1.2.0-1.21.1.jar`, file ID 8995436, publicado 27/09/2026.
- **Versão anterior instalada:** não registrada; não inferir que 1.0.0 ou 1.1.0 foi instalada.
- **Estado no pack:** instalação declarada; inclusão documental inicial pendente.
- **Estado da pesquisa:** identidade, publicação, basename, categorias, dependência publicada e changelogs revisados. Metadata binário, hash, implementação e posição física não verificados.
- **Ambiente publicado:** servidor.
- **Categorias oficiais:** API and Library + Bug Fixes + Server Utility + Utility & QoL + World Gen. Categorias registradas como evidência; nenhuma movimentação estrutural autorizada.
- **Atualização/Status:** 27/11/2026 — versão declarada 1.2.0 revalidada; pesquisa de publicações e sincronização documental concluídas, sem novo bump. Inclusão na tabela ordinal e certificação integral permanecem bloqueadas. O registro preliminar é o único dossier individual existente.

## Função e limite operacional

Correções de worldgen para Integrated API e estruturas de seus consumers. A descrição oficial não demonstra que este mod seja proprietário das estruturas ou que acrescente itens, blocos, entidades ou sistemas de progressão. Não inferir inexistência desses elementos sem inspeção do JAR. A versão 1.2.0 atua sobre problemas associados à adaptação do terreno, incluindo margens de água e fronteiras de chunks. A descrição oficial também registra correção de peças de estruturas que surgiam suspensas, sobretudo em aldeias; não confundir essa correção geral com a limpeza de blocos decorativos suspensos da 1.2.0, nem atribuir sua introdução a uma versão específica sem changelog. O autor credita Integrated API de Craisin Lord como base das correções.

## Publicações 1.21.1 / NeoForge

| Versão | Data | File ID | JAR | Evidência funcional |
|---|---|---:|---|---|
| 1.0.0 | 10/09/2026 | [8853826](https://www.curseforge.com/minecraft/mc-mods/integrated-patches/files/8853826) | `integrated_patches v1.0.0-1.21.1.jar` | Publicação inicial; changelog não discrimina todas as correções. |
| 1.1.0 | 19/09/2026 | [8917411](https://www.curseforge.com/minecraft/mc-mods/integrated-patches/files/8917411) | `integrated_patches v1.1.0-1.21.1.jar` | Corrige interrupção da geração de estruturas grandes, com exemplos Tinker Citadel e Collector Museum do IDAS. |
| 1.2.0 | 27/09/2026 | [8995436](https://www.curseforge.com/minecraft/mc-mods/integrated-patches/files/8995436) | `integrated_patches v1.2.0-1.21.1.jar` | Corrige geração um bloco abaixo do esperado com enhanced_terrain_adaptation, descontinuidade nas bordas de chunks e decorações naturais suspensas. |

A variante Forge 1.20.1 não pertence ao alvo. O catálogo oficial consultado em 27/11/2026 lista seis arquivos (três releases por loader/alvo), sem publicação posterior à 1.2.0 para 1.21.1. Os tamanhos publicados para NeoForge são 51,8 KB (1.0.0), 45,7 KB (1.1.0) e 58,8 KB (1.2.0); tamanho arredondado não identifica conteúdo ou delta binário. A ficha oficial 8995436 confirma a coordenada CurseMaven `curse.maven:integrated-patches-1690092:8995436`. O download por CurseMaven falhou por resolução DNS no ambiente disponível; a tentativa via CDN não forneceu o binário. Portanto não há hash, metadata, versão runtime ou inspeção de classes certificada.

## Dependências e compatibilidade

[Integrated API](https://www.curseforge.com/minecraft/mc-mods/integrated-patches/relations/dependencies) é obrigatório. O [índice mestre](../modlist/modlist.md) registra Integrated API #332, versão 1.10.0. O intervalo efetivo do metadata, eventual incompatibilidade de mixins e a interação com outros mods de worldgen não foram inspecionados. A presença documental da dependência não comprova ABI ou runtime.

## Riscos e verificação restante

- A alteração na geração de estruturas pode afetar chunks novos; não presumir correção retroativa de estruturas existentes.
- Verificar metadata NeoForge, mod ID, dependências, pontos de injeção, coexistência com outros patches, logs e geração em fronteiras de chunks.
- Comparar estruturas de grandes dimensões e áreas costeiras em mundo de QA, sem afirmar testes realizados.
- Estabelecer posição física real antes de inserir na tabela mestre; o número 17 é somente o número na lista de solicitação. O índice histórico registra Integrated API em #332, Integrated Cataclysm em #333 e Integrated Simply Swords em #334; não usar essa vizinhança alfabética para inventar posição, deslocar números existentes ou criar lacuna. A instalação declarada é suficiente para sincronizar a versão, mas não contém ordinal físico.
- Não existe autorização para atribuir selo de auditoria completa à presente ficha.

## Fontes

- [Projeto oficial](https://www.curseforge.com/minecraft/mc-mods/integrated-patches)
- [Lista de arquivos](https://www.curseforge.com/minecraft/mc-mods/integrated-patches/files/all)
- [Dependências oficiais](https://www.curseforge.com/minecraft/mc-mods/integrated-patches/relations/dependencies)
- [Índice e registro de pendência](../modlist/modlist.md)

## Encerramento do controle de atualizações

O item 17 (Integrated Patches) pode sair exclusivamente da fila de pesquisa/sincronização de versões: a instalação declarada 1.2.0 já está documentada, com JAR oficial, fontes e histórico. A inclusão inicial na tabela auditada fica em fila separada, condicionada à posição física real e à elegibilidade documental conforme ORGANIZACAO_MODLIST_AUDITADA.md. Não conceder selo nem mover o dossier até satisfazer esses critérios. Reabrir a pesquisa de versões somente com nova publicação ou evidência material.

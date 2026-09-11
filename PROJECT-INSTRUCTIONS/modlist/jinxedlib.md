# JinxedLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8123891acc138be29941
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR/mod id/runtime confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** JinxedLib
- **Arquivo JAR:** `jinxedlib-neoforge-1.21.1-1.0.4.jar`
- **Versão 1.21.1:** 1.0.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca/toolkit para desenvolvimento multi-loader e modpacks, fornecendo infraestrutura compartilhada e ferramentas consumidas por outros projetos, com foco em abstrações comuns e data-oriented integration.
- **Dependências:** NeoForge 1.21.1. CurseForge does not list additional required projects for file 6727693. Actual consumers, if any, must be identified from their dependency metadata before removal.
- **Sobreposição:** Specific library, not interchangeable with generic APIs without consumer support. Apparent feature similarity with other libraries does not imply ABI compatibility.
- **Compatibilidade/Riscos:** Library with shared registry/data utilities. Main risks: multiple consumers registering simultaneously, ABI/version drift, registry bootstrap/order, data reload and removing the library while a consumer remains. 1.0.4 specifically fixes registry crashes when multiple mods use JinxedLib.
- **Observações:** The 1.21.1 line remains at release 1.0.4 even though the project has newer builds for newer Minecraft versions; this does not make the installed 1.21.1 build obsolete. Exact fix in 1.0.4: registries crashing when multiple mods use JinxedLib.
- **Procedência:** modlist.txt física atual + CurseForge official JinxedLib file 6727693, Release NeoForge 1.21.1 from 04/07/2025 + exact 1.0.4 changelog + official project/source link used only for general architecture; exact source commit for 1.0.4 was not pinned.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/jinxedlib/files/6727693
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — JinxedLib 1.0.4 release-pinned; multi-loader/modpack developer toolkit, registry/data boundaries, exact 1.0.4 multi-mod registry crash fix, lifecycle/server compatibility risks and tests cataloged; source exact not pinned.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `jinxedlib-neoforge-1.21.1-1.0.4.jar`, mod id `jinxedlib`, versão `1.0.4`. CurseForge file 6727693 confirma Release NeoForge 1.21.1 de 04/07/2025. O projeto possui source público, mas um commit/tag exato 1.0.4 não foi pinado nesta auditoria; detalhes internos version-sensitive permanecem fail-closed.

## 1. Papel e authority
JinxedLib é uma biblioteca/toolkit para autores multi-loader e desenvolvedores de modpacks. Ela fornece infraestrutura compartilhada a consumers; não é um content mod autônomo e não deve ser considerada owner do gameplay específico de quem a utiliza.

## 2. Multi-loader boundary
O projeto existe em NeoForge/Fabric/Quilt/Forge em diferentes linhas. Abstrações comuns podem esconder diferenças de loader, mas não eliminá-las. Nesta ficha, apenas a build **NeoForge 1.21.1-1.0.4** física é authority de runtime; comportamento de versões 1.21.11/26.1.2 não é retroprojetado.

## 3. Registry utilities
A mudança específica da 1.0.4 é um **fix para crashes de registries quando múltiplos mods usam JinxedLib**. Portanto registration order, namespace isolation e múltiplos consumers são regression gates centrais. A library não deve registrar conteúdo do consumer duas vezes nem compartilhar um registry owner incorreto.

## 4. Data/modpack tooling
O projeto se apresenta como toolkit para modpack/multi-loader development e inclui ferramentas orientadas a data/datapacks. Data carregado por um consumer continua pertencendo semanticamente ao consumer; JinxedLib fornece mecanismo, não segunda authority do conteúdo.

## 5. Consumers
A página oficial não declara hard dependency adicional para a própria build. Isso não significa que seja removível: consumers podem declarar JinxedLib como requisito. Antes de remover, mapear metadata do loader e referências de código/data; ausência de bloco/item visível não é evidência de inutilidade.

## 6. Client / server
A página do arquivo não estabelece uma gameplay feature client-only específica. Registry/data bootstrap é comum/server-relevant. Código visual pertencente a eventual consumer não deve ser inferido como parte da library sem source exato.

## 7. Lifecycle
Validar mod construction, registry creation/freeze, data load/reload, world join, server restart e coexistência de mais de um consumer. O fix 1.0.4 precisa ser exercitado justamente com múltiplos mods dependentes carregando no mesmo processo.

## 8. Atualizações e version drift
A existência de JinxedLib 1.0.5/1.0.7 para Minecraft mais novo não torna 1.0.4 obsoleto para 1.21.1. Troca de versão entre linhas de Minecraft não é update compatível por definição; manter o pin correspondente ao jogo físico.

## 9. Riscos técnicos
- dois consumers causarem duplicate/shared registry registration;
- regression do crash corrigido na 1.0.4;
- ABI drift entre library e consumer;
- data schema incompatível após update;
- ordem de bootstrap variar entre loaders;
- remover JinxedLib mantendo consumer obrigatório;
- assumir API de release Minecraft mais nova;
- atribuir gameplay do consumer à library.

## 10. Matriz de testes obrigatória
- [ ] Dedicated server e cliente iniciam com JinxedLib 1.0.4.
- [ ] Todos os consumers reais, quando identificados, carregam sem linkage error.
- [ ] Dois ou mais consumers coexistem sem registry crash — regressão 1.0.4.
- [ ] Registry freeze ocorre sem duplicate IDs.
- [ ] Data/datapack de consumers carrega sem codec/schema error.
- [ ] `/reload` não duplica registrations persistentes.
- [ ] Restart não altera registry mapping/state.
- [ ] Remoção experimental só é testada depois de confirmar ausência de consumers obrigatórios.

## 11. Evidências e limites
- **Modlist física:** JAR/mod id/version exatos.
- **CurseForge oficial:** file 6727693, Release NeoForge 1.21.1 e descrição como toolkit multi-loader/modpack.
- **Changelog 1.0.4:** fix de registry crash com múltiplos mods usando JinxedLib.
- **Limite:** source commit exato 1.0.4 não foi pinado; classes/methods específicas não são inventadas.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

# Create Aquatic Ambitions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db811eacebcf854e89a18d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Aquatic Ambitions
- **Arquivo JAR:** `create_aquatic_ambitions-1.21.1-2.0.4.jar`
- **Versão 1.21.1:** 2.0.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Expande automação aquática do Create, com processamento em massa e cadeias para prismarine, coral, cobre e materiais relacionados.
- **Dependências:** Create 6.0.10 físico. O addon adiciona processos/recipes Create para recursos aquáticos; demais providers de coral/prismarine/cobre continuam owners dos próprios itens/blocos.
- **Sobreposição:** Expande automação Create para recursos aquáticos. Sobreposição deve ser comparada por recipe/output com outras cadeias presentes, não por categoria ampla.
- **Compatibilidade/Riscos:** Riscos: recipe overlap, geração excessiva de prismarine/coral/cobre, loops de conversão, tags inconsistentes, `/reload` stale e balance drift com outros addons de processamento. Não assumir recipe IDs/ratios além dos dados efetivos da build.
- **Observações:** JAR físico `create_aquatic_ambitions-1.21.1-2.0.4.jar`, mod id `create_aquatic_ambitions`, runtime 2.0.4. Release oficial NeoForge 1.21.1 de 19/07/2026, Client & Server.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create Aquatic Ambitions 2.0.4.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aquatic-ambitions
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; bulk aquatic processing, prismarine/coral/copper economy, recipes, Create lifecycle, integrations e tests catalogados para 2.0.4.
- **Histórico da decisão:**
- **Data da última decisão:**

> 🌊 **ESCOPO CANÔNICO.** Runtime físico 2.0.4. Create Aquatic Ambitions adiciona processos Create voltados a recursos aquáticos, com foco público em automação de Prismarine, Coral e Copper.

## 1. Authority
Create continua owner das máquinas/process types; o addon owns recipes e conteúdo adicional que registra para esses fluxos. Blocos/itens vanilla ou de outros mods continuam pertencendo aos respectivos providers.

## 2. Cadeias de processamento
A descrição oficial confirma novos bulk processes e cadeias para recursos aquáticos. O valor operacional é transformar materiais que normalmente exigem exploração/coleta manual em rotas automatizáveis de Create.
A documentação pública consultada não fornece um inventário exato de todos os recipe IDs/ratios da 2.0.4; portanto a ficha não inventa entradas/outputs. Para automação e balanceamento, os datapacks/recipes efetivamente carregados são authority.

## 3. Economia e loops
Prismarine, coral e cobre possuem outras fontes no pack. Qualquer cadeia nova deve ser avaliada por custo líquido, renovabilidade e possibilidade de ciclo positivo. A existência de uma segunda recipe não é conflito por si; conflito ocorre se houver loop, output excessivo ou bypass de progressão pretendida.

## 4. Data/reload
Recipes Create são data-driven. `/reload`, KubeJS e datapacks podem alterar disponibilidade sem reinício. Cache de recipe viewer e máquinas em andamento precisam convergir após reload; não manter referência stale a recipe removida.

## 5. Client/server
Ponder/JEI/visualização são apresentação; consumo de input, processamento e output são server-authoritative. Uma animação concluída no cliente não é evidência suficiente para conceder quest/progressão.

## 6. Integrações concretas
Create 6.0.10 é o provider físico. O pack contém várias cadeias metalúrgicas/culinárias/automação e worldgen que podem fornecer os mesmos recursos; comparação deve usar recipe IDs/tags concretos, não assumir redundância ampla.

## 7. Riscos
1. Recipe loop positivo ou recurso infinito não intencional.
2. Tags aceitam material semanticamente incorreto.
3. Coral/prismarine economy trivializa exploração pretendida.
4. Copper route compete com outros processos e produz custo inconsistente.
5. `/reload` deixa machine/JEI state stale.
6. Addon/Create version drift invalida recipe type.

## 8. Matriz de testes
- [ ] Dedicated server inicia com 2.0.4 + Create 6.0.10.
- [ ] Recipes aquáticas aparecem uma única vez no viewer.
- [ ] Cada processo consome inputs e produz outputs exatamente uma vez.
- [ ] Nenhum ciclo fechado gera ganho líquido não documentado.
- [ ] `/reload` remove/adiciona recipes sem state stale.
- [ ] Automação com belts/basins/fans relevantes não duplica stacks.
- [ ] Multiplayer observa o mesmo recipe/output state.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
A Release 2.0.4 e o escopo Prismarine/Coral/Copper são confirmados oficialmente. Sem source pin ou recipe dump exato da build, esta ficha mantém contagens, ratios e IDs específicos fail-closed.

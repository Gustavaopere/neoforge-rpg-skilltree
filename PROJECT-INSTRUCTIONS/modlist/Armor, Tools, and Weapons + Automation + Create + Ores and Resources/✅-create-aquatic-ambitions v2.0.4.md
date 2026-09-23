# Create Aquatic Ambitions

## Propriedades do registro

- **Mod:** Create Aquatic Ambitions
- **Arquivo JAR:** create_aquatic_ambitions-1.21.1-2.0.4.jar
- **Versão 1.21.1:** 2.0.4
- **Categoria:** Tecnologia, Automação
- **Função:** Expande automação aquática do Create, com processamento em massa e cadeias para prismarine, coral, cobre e materiais relacionados.
- **Dependências:** Create 6.0.10 físico. A linha 2.0.x requer Create 6.0.6+; a 2.0.4 mantém JEI totalmente opcional e apenas ajusta o version floor para compatibilidade. Demais providers aquáticos continuam owners dos próprios itens/blocos.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: recipe overlap, geração excessiva de prismarine/coral/cobre, loops de conversão, tags inconsistentes, `/reload` stale e balance drift com outros addons de processamento. Não assumir recipe IDs/ratios além dos dados efetivos da build.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aquatic-ambitions
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_aquatic_ambitions` 2.0.4 + CurseForge/Modrinth oficiais 2.0.4 revalidados em 20/09/2026.
- **Observações:** JAR físico `create_aquatic_ambitions-1.21.1-2.0.4.jar`, mod id `create_aquatic_ambitions`, runtime 2.0.4. Release oficial NeoForge 1.21.1 de 19/07/2026; 2.0.4 corrige falha de load com versões mais antigas do JEI e mantém JEI opcional.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — registro histórico do lote físico #132 na snapshot então vigente; posição física atual #133: create_aquatic_ambitions-1.21.1-2.0.4.jar / 2.0.4 confirmados; bulk aquatic processing e fix 2.0.4 de compatibilidade/version floor mantendo JEI opcional permanecem atuais.
- **Decisão:** Sem decisão
- **Sobreposição:** Expande automação Create para recursos aquáticos. Sobreposição deve ser comparada por recipe/output com outras cadeias presentes, não por categoria ampla.

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
Create 6.0.10 é o provider físico. A linha 2.0.x exige Create 6.0.6+; portanto a build física 6.0.10 está acima do baseline publicado. A 2.0.4 corrige recusa de carregamento com versões mais antigas do JEI ao **baixar o version floor para acompanhar o Create**, mantendo JEI **totalmente opcional**. Logo, JEI é integração de visualização/recipe discovery, não hard dependency operacional do addon.
O pack contém várias cadeias metalúrgicas/culinárias/automação e worldgen que podem fornecer os mesmos recursos; comparação deve usar recipe IDs/tags concretos, não assumir redundância ampla.
## 7. Riscos
1. Recipe loop positivo ou recurso infinito não intencional.
2. Tags aceitam material semanticamente incorreto.
3. Coral/prismarine economy trivializa exploração pretendida.
4. Copper route compete com outros processos e produz custo inconsistente.
5. `/reload` deixa machine/JEI state stale.
6. JEI ser tratado incorretamente como hard dependency apesar da 2.0.4 mantê-lo opcional.
7. Addon/Create version drift invalida recipe type.
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
A Release 2.0.4, o escopo Prismarine/Coral/Copper, o baseline Create 6.0.6+ e o fix de compatibilidade que mantém JEI opcional são confirmados oficialmente. Sem source pin ou recipe dump exato da build, esta ficha mantém contagens, ratios e IDs específicos fail-closed.

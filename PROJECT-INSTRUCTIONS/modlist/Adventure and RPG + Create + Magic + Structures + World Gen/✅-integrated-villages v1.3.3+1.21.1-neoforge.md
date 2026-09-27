# Integrated Villages

## Propriedades do registro

- **Mod:** Integrated Villages
- **Arquivo JAR:** `integrated_villages-1.3.3+1.21.1-neoforge.jar`
- **Versão 1.21.1:** `1.3.3+1.21.1-neoforge`
- **Categoria:** Worldgen, Exploração
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/integrated-villages
- **Função:** Overhaul das vilas vanilla com 11 variantes detalhadas, integração de blocos/conteúdo de outros mods e configuração data-driven de geração, biomas e workstations.
- **Dependências:** Obrigatórias oficiais: Integrated API 1.8.0, Create 6.0.10 e Supplementaries 3.9.8. Quark e Farmer's Delight são integrações altamente recomendadas e estão presentes; outras integrações opcionais dependem da modlist/data real.
- **Compatibilidade/Riscos:** Village overhaul com impacto em worldgen/economia. Riscos: vanilla villages desativadas sem replacement válido, structure-set/biome collisions, workstation/trade drift, optional registry refs, loot/trade inflation e chunks híbridos. Ice and Fire CE é fork e compat não é presumida só pela lista upstream.
- **Sobreposição:** Substitui/expande o escopo de vilas vanilla e pode competir com outros village/worldgen providers. A comparação correta é por village/structure sets, biome placement, workstations, trades e densidade, não apenas por estética.
- **Observações:** Release 1.3.3 NeoForge 1.21.1 de 28/05/2026 corrige texturas quebradas das book piles e a porta da biblioteca. O projeto desabilita por padrão o spawn das vilas vanilla, controlável por configuração.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial Integrated Villages 1.3.3 NeoForge 1.21.1 + configuração datapack 1.3.3 + descrição/changelog oficiais já auditados. Revalidação em 12/09/2026 não encontrou build 1.21.1 posterior.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Integrated Villages 1.3.3+1.21.1-neoforge/JAR físico reconfirmado; 1.3.3 permanece a release NeoForge 1.21.1 mais recente localizada. 11 village variants, vanilla-village toggle, structure-set/biome/workstation data, optional integrations, economy/worldgen lifecycle, riscos e testes preservados.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #336: JAR `integrated_villages-1.3.3+1.21.1-neoforge.jar`, mod id `integrated_villages`, runtime `1.3.3+1.21.1-neoforge`, SHA-1 `4ba7360abf671b1f0f40c43c590977da2048490e`.

<callout icon="🏘️" color="green_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `integrated_villages-1.3.3+1.21.1-neoforge.jar`, mod id `integrated_villages`, versão `1.3.3+1.21.1-neoforge`. A release oficial NeoForge 1.21.1 é 1.3.3 de 28/05/2026.
</callout>
## 1. Papel e authority
Integrated Villages reformula as vilas vanilla com construções maiores e integrações modded. O addon é authority dos layouts/templates e data de worldgen que fornece; Minecraft continua owner dos villagers/trades-base, enquanto Create, Supplementaries e outros providers continuam owners de seus blocos e mecânicas.
## 2. Dependências e recomendações
A linha atual exige Integrated API, Create e Supplementaries. Quark e Farmer's Delight são fortemente recomendados pelo projeto; ambos estão presentes fisicamente. Integrações adicionais devem ser tratadas como condicionais, nunca como requisito implícito só porque aparecem na lista upstream.
## 3. Catálogo público
O projeto anuncia **11 vilas fortemente detalhadas**. Essa contagem é cobertura documental de variantes, não registry count inferido do JAR. Cada variante pode combinar providers diferentes e deve ser testada nos biomas realmente elegíveis.
## 4. Vanilla village toggle
A documentação informa que o mod **desabilita por padrão o spawn das vilas vanilla**, com controle por configuração. Essa opção é crítica: configuração incorreta pode reduzir ou duplicar vilas. Não afirmar o valor efetivamente ativo no pack sem ler o arquivo de configuração instalado.
## 5. Structure sets e frequência
Como no ecossistema Integrated, spacing/separation e structure-set data controlam distribuição. Alterar frequência pode multiplicar villagers, beds, workstations, loot e farms; portanto balance deve considerar economia e não apenas performance de worldgen.
## 6. Biome tags
Biome targeting é data-driven. Com Terralith e outros providers de biomas/terreno, tags precisam ser verificadas contra o datapack final carregado. Bioma inexistente, renomeado ou tag ampla demais pode resultar em ausência de vilas ou placement inadequado.
## 7. Workstations Integrated
A documentação do projeto expõe JSONs de workstations via Integrated API. Workstation placement pode afetar profissão/trades de villagers. Um bloco decorativo visualmente parecido não deve ser tratado como workstation sem registry/data real.
## 8. Integrações opcionais físicas
O upstream lista muitas integrações. No pack estão, entre outras, Ars Nouveau 5.13.1, Iron's Spells 3.16.3 e Waystones 21.1.44, além dos requisitos/recomendados. O pack usa Ice and Fire CE 2.1.2; como é fork, não se presume automaticamente que todas as referências previstas para Ice and Fire original resolvam sem teste.
## 9. Economia, loot e trades
Mais villages/workstations significam mais acesso a trades e recursos. Quests e sistemas próprios não devem recompensar duas vezes a mesma descoberta/profissão. Alterações de trades devem ser testadas com economia do pack, principalmente quando vários mods adicionam profissões, mapas ou loot.
## 10. Release 1.3.3
A build instalada corrige **texturas quebradas das book piles** e **a porta da biblioteca que não funcionava**. Ambos são regression gates concretos: a estrutura de biblioteca precisa renderizar corretamente e sua porta deve operar/persistir após reload.
## 11. Client / server
Worldgen, villagers, trades, workstation assignment, loot e block state são server-authoritative. Texturas/modelos são client-side. Uma textura corrigida em 1.3.3 não altera a authority do state da book pile/porta.
## 12. Lifecycle e persistência
Validar world creation, geração em chunks novos, spawn/profissão de villagers, workstations, portas/mecanismos, chunk unload/reload, server restart e `/reload`. Alterações de village data não transformam vilas já persistidas e podem produzir regiões híbridas entre versões/configs.
## 13. Riscos técnicos
- vanilla villages desativadas sem substitutes suficientes;
- vanilla e Integrated villages gerarem em duplicidade após config change;
- biome/structure-set drift;
- workstation inválida ou profissão inconsistente;
- optional integration referenciar registry ausente;
- trade/loot inflation;
- door/Create mechanism perder state após unload;
- book-pile asset regression;
- update unilateral de Integrated API/provider;
- chunks antigos/novos divergirem.
## 14. Matriz de testes obrigatória
- [ ] Dedicated server inicia com Integrated Villages 1.3.3 e hard dependencies.
- [ ] Config de vanilla villages produz exatamente o comportamento pretendido.
- [ ] Amostra das variantes gera em biomas válidos sem terrain corruption crítica.
- [ ] Villagers e workstations resolvem profissões/trades corretamente.
- [ ] Library door funciona e persiste após unload/restart.
- [ ] Book piles não exibem missing/broken textures.
- [ ] Optional integrations presentes resolvem sem missing registry.
- [ ] Ice and Fire CE é validado separadamente, sem assumir equivalência ao upstream original.
- [ ] Spacing/separation não causa densidade/economia excessivas.
- [ ] `/reload` e server restart preservam data/state válido.
## 15. Evidências e limites
- **Modlist física:** JAR/mod id/version e providers atuais.
- **CurseForge oficial:** release 1.3.3, 11 villages, dependencies/recommendations e modelo de configuração.
- **Changelog 1.3.3:** book-pile texture e library-door fixes.
- **Limite:** source code/template IDs exatos da 1.3.3 não foram pinados; integração de cada opcional deve ser provada pelo runtime/data real.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

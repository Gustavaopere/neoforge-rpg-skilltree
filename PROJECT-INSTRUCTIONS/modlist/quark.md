# Quark

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na origem:** Integrado ao Github
- **Autoridade física atual:** `modlist(1).txt` anexada em 16/09/2026 — `Quark-4.1-484.jar`, mod id `quark`, runtime `4.1-484`, mixins `quark.mixins.json` e `quark_integrations.mixins.json`; Zeta 1.1-40, Dynamic Trees 1.7.2 e Dynamic Trees for Quark 2.6.1 presentes; Biolith 3.0.10 embutido e Biolith 3.0.14 também presente como JAR top-level
- **Data da reauditoria:** 2026-09-16

## Divergências documentais reconciliadas

- O Notion de 11/09/2026 registrava `Quark-4.1-483.jar` como físico e 4.1-484 apenas como hotfix externo disponível. A modlist física atual confirma `Quark-4.1-484.jar`; portanto o antigo version gate foi resolvido pela instalação da 4.1-484.
- O JAR Quark continua contendo **Biolith 3.0.10 como JarJar interno**, enquanto a instalação também contém **`biolith-neoforge-3.0.14.jar` como JAR top-level**. A cópia embarcada não recebe ordinal próprio; a cópia top-level é uma instalação independente e não pode ser apagada documentalmente pela regra aplicada ao JarJar do Quark.

## Propriedades do banco

- **Mod:** Quark
- **Arquivo JAR:** `Quark-4.1-484.jar`
- **Versão 1.21.1:** 4.1-484
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** QoL, Worldgen, Exploração
- **Função:** Grande coleção modular de melhorias vanilla+, blocos, QoL, worldgen e mecânicas configuráveis.
- **Dependências:** Zeta 1.1-40 é required dependency. Consumer/bridge causal mantido no pack: Dynamic Trees - Quark 2.6.1, que depende de Quark + Dynamic Trees. Dynamic Trees físico está em 1.7.2. Biolith 3.0.10 permanece embedded no JAR; Biolith 3.0.14 também existe fisicamente como top-level separado.
- **Sobreposição:** Grande mod modular; sobreposição deve ser decidida por módulo. Dynamic Trees - Quark torna a base Quark necessária enquanto o bridge for mantido.
- **Compatibilidade/Riscos:** Riscos: DT-Quark/worldgen drift, module overlap, Zeta coupling, item-handler automation e coexistência de Biolith embedded/top-level. A build física 4.1-484 é o hotfix publicado para corrigir o filtering de enchantments que quebrava Mending na 4.1-483.
- **Observações:** Runtime instalado `4.1-484`. Em 10/09/2026, 4.1-484 foi publicada como hotfix para Mending/filtering e agora está confirmada na instalação física. A 4.1-483 permanece relevante como release-base imediatamente anterior, responsável pelas mudanças de Azalea Wood, Golden Tools Have Fortune, Totem of Holding/Oddities e item-handler capabilities documentadas no dossiê.
- **Procedência:** modlist física atual anexada em 16/09/2026 + CurseForge oficial Quark 4.1-484 file ID 8847564 + dossiê/source já auditado da 4.1-483 + Dynamic Trees - Quark 2.6.1 e dependências físicas atuais.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/quark/files/8847564
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 16/09/2026 — Quark 4.1-484 físico confirmado e decisão Dependência preservada: modularidade, Zeta, DT-Quark consumer mantido, Azalea/enchants/item handlers, Biolith embedded + top-level coexistente, hotfix de Mending, riscos e testes.
- **Histórico da decisão:** 2026-09-10 — reclassificado de Sem decisão para Dependência porque Dynamic Trees - Quark 2.6.1 está Mantido/Integrado ao Github e exige Quark como base funcional. 2026-09-11 — Notion registrava 4.1-483 instalada e 4.1-484 como update externo. 2026-09-16 — modlist física confirma 4.1-484 instalada; decisão Dependência preservada.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico atual: `Quark-4.1-484.jar`, mod id `quark`, versão `4.1-484`, NeoForge 1.21.1. Quark é um grande mod **modular vanilla+**. O pack mantém `Dynamic Trees - Quark 2.6.1` com decisão **Manter**, e esse bridge depende de Quark; por isso Quark permanece **Dependência**. O JAR embute Biolith 3.0.10 como JarJar e a instalação também contém Biolith 3.0.14 top-level. A 4.1-484 é o hotfix de Mending/filtering que no Notion anterior ainda constava apenas como update externo.

## 1. Identidade e papel
- **Mod:** Quark.
- **JAR físico:** `Quark-4.1-484.jar`.
- **Mod id:** `quark`.
- **Runtime:** `4.1-484`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Papel:** coleção modular de melhorias vanilla+, construção, QoL, exploração e worldgen.
- **Decisão:** Dependência.

## 2. Causalidade da dependência
O pack contém **Dynamic Trees - Quark 2.6.1**, já catalogado com decisão `Manter` e `Integrado ao Github`. Esse addon existe para integrar conteúdo/worldgen de Quark ao Dynamic Trees e declara Quark como base funcional.

Enquanto o bridge for mantido, remover Quark torna o dependency graph incoerente. A classificação `Dependência` deriva dessa relação concreta, não do tamanho/popularidade do Quark.

## 3. Dependência Zeta
A publicação oficial do Quark exige **Zeta**. A modlist física atual contém `Zeta-1.1-40.jar`.

Zeta é owner da infraestrutura compartilhada; Quark permanece owner de seus módulos/conteúdo. Não atualizar/remover Zeta isoladamente sem validar Quark e demais consumers.

## 4. Modularidade
Quark não deve ser avaliado como uma única feature. Módulos podem ser habilitados/desabilitados e cobrem domínios diferentes.

Curadoria correta é **feature-by-feature**, especialmente quando outro mod já cobre a mesma mecânica. Desabilitar um módulo pode ser preferível a remover o mod inteiro quando addons dependem do core.

## 5. Construção e decoração
Quark adiciona variantes/blocos e melhorias de construção vanilla+. A superfície exata depende dos módulos ativos.

Em um pack com Rechiseled, Domum Ornamentum e muitos decorative mods, overlap deve ser medido por blocos/recipes efetivamente usados, não por categoria “decoração”.

## 6. QoL e utilidades
Módulos QoL podem alterar interações de inventário, placement, tool behavior, HUD ou pequenas regras vanilla. Cada módulo precisa ser testado contra mods que interceptam o mesmo evento.

Não atribuir ao Quark uma feature só porque ela existe no projeto se o módulo estiver desativado na configuração local.

## 7. Worldgen e vegetação
Quark possui módulos de worldgen/vegetação. O pack mantém **Dynamic Trees - Quark** justamente para traduzir/cancelar/substituir partes compatíveis no modelo Dynamic Trees.

A combinação física atual é Quark 4.1-484 + Dynamic Trees 1.7.2 + dtquark 2.6.1. Como o baseline do bridge é version-sensitive, qualquer update exige regressão de trees, Glow Shroom, seeds, loot e feature cancellation.

## 8. Azalea Wood e release 4.1-483
A 4.1-484 é um hotfix sobre a linha 4.1-483; portanto as mudanças documentadas da 4.1-483 permanecem relevantes. O changelog da 4.1-483 inclui mudança importante: se o **Azalea Wood Module** estiver desabilitado manualmente ou por anti-overlap, Quark não força mais a configured feature vanilla de azalea a usar Oak Logs.

Isso é regression gate direto para worldgen e para Dynamic Trees - Quark.

## 9. Golden Tools Have Fortune e hotfix 4.1-484
A 4.1-483 corrigiu partes do módulo “Golden Tools Have Fortune” e mutual exclusions de enchantments, mas introduziu um problema de filtering que podia quebrar **Mending**. Em 10/09/2026, Quark 4.1-484 foi publicada especificamente como hotfix para esse problema.

Autoridade atual do catálogo:
- instalado = **4.1-484**;
- a 4.1-483 = release-base anterior documentada;
- o antigo estado “4.1-484 disponível, não instalada” foi superado pela modlist física de 16/09/2026.

O regression gate agora é confirmar que Mending e o filtering do módulo funcionam corretamente na 4.1-484.

## 10. Totem of Holding e Oddities
A 4.1-483 adicionou opção de config para Totem of Holding/Oddities permitir drop de todos os itens quando atingido. Essa feature só é relevante se o módulo/integração correspondente estiver ativo.

Não assumir comportamento de morte/inventário sem ler config e confirmar presença funcional.

## 11. Item-handler capabilities
A 4.1-483 adicionou capabilities de item handler a **Backpacks** e **Feeding Trough**, permitindo potencial interação com ferramentas/máquinas/pipes de outros mods.

Isso amplia superfície de automação: testar insert/extract, side rules, loops e acesso indevido a itens quando integrado a Create/outros handlers.

## 12. Biolith embedded e Biolith top-level
O JAR físico do Quark inclui `/META-INF/jarjar/biolith-neoforge-3.0.10.jar`, mod id `biolith`, versão `3.0.10`.

Regra canônica para **essa cópia embarcada**:
- não recebe ordinal próprio;
- não recebe registro top-level separado como se fosse outro arquivo físico;
- não deve ser atualizada isoladamente dentro do Quark;
- problemas de resolução da cópia interna devem ser triados sob o host Quark, salvo evidência de conflito com outra cópia.

A instalação atual também contém `biolith-neoforge-3.0.14.jar` como **JAR top-level real**. Isso é uma entidade física separada da cópia JarJar 3.0.10 e deve ser tratada como tal no catálogo geral. A coexistência 3.0.10 embedded + 3.0.14 top-level é uma superfície de resolução/versionamento que precisa ser observada, não apagada pela regra acima.

## 13. Dynamic Trees - Quark
O bridge mantido adapta families/species, worldgen, seeds/loot e features especiais do Quark para Dynamic Trees. A combinação física atual cria acoplamento de três versões:
- Quark 4.1-484;
- Dynamic Trees 1.7.2;
- dtquark 2.6.1.

Regression test após qualquer update de um deles é obrigatório.

## 14. Client/server e persistência
Conteúdo/worldgen e interações funcionais precisam ser server-authoritative. Configs client-only podem alterar apresentação, mas não podem divergir em registry/gameplay state.

Testar dedicated server, world load, datapack/resource reload, chunk generation, inventories/capabilities e reconnect.

## 15. Sobreposição e curadoria
Quark pode coincidir parcialmente com:
- decorative/building mods;
- inventory/QoL mods;
- worldgen mods;
- vegetation systems;
- enchantment tweaks.

A resposta correta não é “Quark duplica tudo”, mas mapear módulo → provider concorrente → prioridade/config. O bridge DT-Quark impede tratar Quark como removível globalmente enquanto mantido.

## 16. Riscos
1. **Dependency chain:** Dynamic Trees - Quark mantido depende do Quark.
2. **Mending/filtering:** 4.1-484 é o hotfix da falha da 483; validar regressão no runtime instalado.
3. **Module overlap:** dois mods alteram mesma mecânica.
4. **Worldgen drift:** azalea/Glow Shroom/feature cancellers.
5. **Item-handler automation:** backpacks/troughs expostos a pipes/tools.
6. **Biolith coexistente:** Quark embute 3.0.10 e o pack contém 3.0.14 top-level; observar resolução/versionamento e não confundir as duas cópias.
7. **Zeta coupling:** core e library precisam permanecer compatíveis.
8. **Config-dependent behavior:** projeto documenta features que podem estar desligadas localmente.

## 17. Matriz de testes
- [ ] Dedicated server/cliente iniciam com Quark 4.1-484 + Zeta 1.1-40.
- [ ] Dynamic Trees - Quark inicia e registra conteúdo sem missing IDs.
- [ ] Mundo novo não duplica árvores/Glow Shroom estático+dinâmico.
- [ ] Azalea behavior respeita módulo/anti-overlap configurado.
- [ ] Golden Tools/enchants preservam Mending corretamente na build 4.1-484.
- [ ] Backpack item handler aceita/rejeita automação conforme esperado.
- [ ] Feeding Trough capability não duplica item em insert/extract.
- [ ] Desabilitar módulo específico não quebra bridge ou registry.
- [ ] Resource/datapack reload não duplica hooks.
- [ ] Biolith embedded 3.0.10 e top-level 3.0.14 resolvem sem conflito de carregamento/registry.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- **Modlist física atual:** Quark 4.1-484, Zeta 1.1-40, Dynamic Trees 1.7.2, dtquark 2.6.1, Biolith 3.0.10 embedded e Biolith 3.0.14 top-level.
- **CurseForge oficial:** Quark 4.1-484, file ID 8847564, Release NeoForge 1.21.1 de 10/09/2026; hotfix de Mending/filtering.
- **Histórico preservado do Notion/dossiê:** 4.1-483, mudanças de Azalea/enchantments/Totem/item handlers e o antigo version gate para 4.1-484.
- **Notion/source já auditado:** Dynamic Trees - Quark 2.6.1 está `Manter` e depende de Quark.
- **Limite:** config modular local não foi lida neste lote; nenhuma feature foi declarada ativa apenas por constar na documentação; nenhum teste de runtime acima foi executado.

# Quark

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `Quark-4.1-483.jar`
- **Versão 1.21.1:** 4.1-483
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** QoL, Worldgen, Exploração
- **Função:** Grande coleção modular de melhorias vanilla+, blocos, QoL, worldgen e mecânicas configuráveis.
- **Dependências:** Zeta 1.1-40 é required dependency. Consumer/bridge causal mantido no pack: Dynamic Trees - Quark 2.6.1, que depende de Quark + Dynamic Trees. Quark embute Biolith 3.0.10; a modlist física contém também `biolith-neoforge-3.0.14.jar` como JAR top-level separado.
- **Sobreposição:** Grande mod modular; sobreposição deve ser decidida por módulo. Dynamic Trees - Quark torna a base Quark necessária enquanto o bridge for mantido.
- **Compatibilidade/Riscos:** Riscos: DT-Quark/worldgen drift, module overlap, Zeta coupling, item-handler automation e coexistência da cópia Biolith 3.0.10 embutida com Biolith 3.0.14 top-level. A build física 4.1-483 tem atualização externa 4.1-484 disponível com hotfix para Mending/filtering; não trocar versão no catálogo sem modlist física nova.
- **Observações:** Runtime instalado permanece 4.1-483. Em 10/09/2026, 4.1-484 foi publicada como hotfix posterior para filtering que afetava Mending; registrar como update disponível, não como instalada. A modlist física também confirma Biolith 3.0.14 top-level além do JarJar 3.0.10 interno de Quark.
- **Procedência:** modlist.txt física atual reconfirmada nesta auditoria (`Quark-4.1-483.jar`, Biolith 3.0.14 top-level e Biolith 3.0.10 JarJar) + CurseForge oficial Quark 4.1-483 + pesquisa da release externa 4.1-484 + dossiê/source já auditado de Dynamic Trees - Quark.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/quark
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Quark 4.1-483 reconstruído e reclassificado como Dependência: modularidade, Zeta, DT-Quark consumer mantido, Azalea/enchants/item handlers, Biolith embedded, update 4.1-484 externo, riscos e testes. Autoridade física adicional revalidada na auditoria de migração: Biolith 3.0.14 existe também como JAR top-level.
- **Histórico da decisão:** 2026-09-10 — reclassificado de Sem decisão para Dependência porque Dynamic Trees - Quark 2.6.1 está Mantido/Integrado ao Github e exige Quark como base funcional.
- **Data da última decisão:** 2026-09-10

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `Quark-4.1-483.jar`, mod id `quark`, versão `4.1-483`, NeoForge 1.21.1. Quark é um grande mod **modular vanilla+**. O pack mantém `Dynamic Trees - Quark 2.6.1` com decisão **Manter**, e esse bridge depende de Quark; por isso Quark passa de `Sem decisão` para **Dependência**. O JAR de Quark embute Biolith 3.0.10 como JarJar. A modlist física atual também possui `biolith-neoforge-3.0.14.jar` como top-level separado. Existe uma release externa Quark 4.1-484 mais nova, mas a autoridade instalada continua 4.1-483 até a modlist física mudar.

## 1. Identidade e papel
- **Mod:** Quark.
- **JAR físico:** `Quark-4.1-483.jar`.
- **Mod id:** `quark`.
- **Runtime:** `4.1-483`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Papel:** coleção modular de melhorias vanilla+, construção, QoL, exploração e worldgen.
- **Decisão:** Dependência.

## 2. Causalidade da dependência
O pack contém **Dynamic Trees - Quark 2.6.1**, já catalogado com decisão `Manter` e `Integrado ao Github`. Esse addon existe para integrar conteúdo/worldgen de Quark ao Dynamic Trees e declara Quark como base funcional.

Enquanto o bridge for mantido, remover Quark torna o dependency graph incoerente. A classificação `Dependência` deriva dessa relação concreta, não do tamanho/popularidade do Quark.

## 3. Dependência Zeta
A publicação oficial do Quark exige **Zeta**. A modlist física contém `Zeta-1.1-40.jar`.

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

O bridge atual trabalha com Quark 4.1-483 e Dynamic Trees 1.7.2, embora seu baseline de source seja anterior; portanto qualquer update exige regressão de trees, Glow Shroom, seeds, loot e feature cancellation.

## 8. Azalea Wood e release 4.1-483
O changelog exato da 4.1-483 inclui mudança importante: se o **Azalea Wood Module** estiver desabilitado manualmente ou por anti-overlap, Quark não força mais a configured feature vanilla de azalea a usar Oak Logs.

Isso é regression gate direto para worldgen e para Dynamic Trees - Quark.

## 9. Golden Tools Have Fortune
A 4.1-483 corrige partes do módulo “Golden Tools Have Fortune” e mutual exclusions de enchantments. Porém em 10/09/2026 surgiu **Quark 4.1-484**, hotfix posterior para um problema de filtering que quebrava Mending na build anterior.

Autoridade do catálogo:
- instalado = **4.1-483**;
- 4.1-484 = atualização externa disponível, não instalada;
- não alterar `Arquivo JAR`/versão no catálogo até a pasta física/modlist confirmar a troca.

Enquanto 483 permanecer, testar Mending/golden-tool enchantment filtering como risco conhecido.

## 10. Totem of Holding e Oddities
A 4.1-483 adiciona opção de config para Totem of Holding/Oddities permitir drop de todos os itens quando atingido. Essa feature só é relevante se o módulo/integração correspondente estiver ativo.

Não assumir comportamento de morte/inventário sem ler config e confirmar presença funcional.

## 11. Item-handler capabilities
A 4.1-483 adiciona capabilities de item handler a **Backpacks** e **Feeding Trough**, permitindo potencial interação com ferramentas/máquinas/pipes de outros mods.

Isso amplia superfície de automação: testar insert/extract, side rules, loops e acesso indevido a itens quando integrado a Create/outros handlers.

## 12. Biolith embedded e top-level físico
O JAR físico de Quark inclui `/META-INF/jarjar/biolith-neoforge-3.0.10.jar`, mod id `biolith`, versão `3.0.10`.

Para **essa cópia embutida**:
- não recebe ordinal próprio por ser JarJar do host Quark;
- não recebe página top-level separada por causa da cópia 3.0.10;
- não deve ser atualizada isoladamente dentro do host.

Entretanto, a autoridade física da modlist confirma também **`biolith-neoforge-3.0.14.jar` como JAR top-level separado**. Essa entrada top-level é independente da cópia JarJar 3.0.10 para fins de presença/ordem física e deve ser tratada conforme sua própria posição no catálogo. Ao diagnosticar resolução/classloading, considerar explicitamente a coexistência das duas versões em vez de presumir ausência de Biolith top-level.

## 13. Dynamic Trees - Quark
O bridge mantido adapta families/species, worldgen, seeds/loot e features especiais do Quark para Dynamic Trees. A combinação cria acoplamento de três versões:
- Quark 4.1-483;
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
2. **Update disponível:** 4.1-484 corrige problema de Mending/filtering da 483.
3. **Module overlap:** dois mods alteram mesma mecânica.
4. **Worldgen drift:** azalea/Glow Shroom/feature cancellers.
5. **Item-handler automation:** backpacks/troughs expostos a pipes/tools.
6. **Biolith dual presence:** Quark embute 3.0.10 e a modlist possui 3.0.14 top-level; resolução/compatibilidade precisa ser testada, não inferida.
7. **Zeta coupling:** core e library precisam permanecer compatíveis.
8. **Config-dependent behavior:** projeto documenta features que podem estar desligadas localmente.

## 17. Matriz de testes
- [ ] Dedicated server/cliente iniciam com Quark 4.1-483 + Zeta 1.1-40.
- [ ] Dynamic Trees - Quark inicia e registra conteúdo sem missing IDs.
- [ ] Mundo novo não duplica árvores/Glow Shroom estático+dinâmico.
- [ ] Azalea behavior respeita módulo/anti-overlap configurado.
- [ ] Golden Tools/enchants não removem Mending indevidamente na build 483.
- [ ] Backpack item handler aceita/rejeita automação conforme esperado.
- [ ] Feeding Trough capability não duplica item em insert/extract.
- [ ] Desabilitar módulo específico não quebra bridge ou registry.
- [ ] Resource/datapack reload não duplica hooks.
- [ ] Biolith 3.0.10 JarJar e Biolith 3.0.14 top-level resolvem sem conflito crítico.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- Modlist física: Quark 4.1-483, Zeta 1.1-40, Biolith 3.0.14 top-level e Biolith 3.0.10 embedded no Quark.
- CurseForge oficial: Quark 4.1-483 Release NeoForge 1.21.1 de 08/09/2026; changelog de enchantments, azalea, Totem option e item-handler capabilities.
- Pesquisa externa atual já registrada no Notion: Quark 4.1-484 lançado em 10/09/2026 como hotfix de Mending/filtering; **não instalado**.
- Notion/source já auditado: Dynamic Trees - Quark 2.6.1 está `Manter` e depende de Quark.
- **Limite:** config modular local não foi lida neste lote; nenhuma feature foi declarada ativa apenas por constar na documentação. A coexistência Biolith 3.0.10 embedded + 3.0.14 top-level foi confirmada fisicamente, mas não testada em runtime nesta auditoria.

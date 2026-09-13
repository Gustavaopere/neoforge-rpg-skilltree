# Nature's Compass

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8199a405cd1e4d3b97a8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `NaturesCompass-1.21.1-3.4.0-neoforge.jar`, mod id `naturescompass`, runtime literal `1.21.1-3.4.0-neoforge` confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Nature's Compass 3.4.0 está confirmado.

## Propriedades do banco

- **Mod:** Nature's Compass
- **Arquivo JAR:** `NaturesCompass-1.21.1-3.4.0-neoforge.jar`
- **Versão 1.21.1:** 1.21.1-3.4.0-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL, Exploração
- **Função:** Locator de biomas com seleção em GUI, busca por ocorrência/next instance e custos opcionais de XP/durabilidade; consulta o worldgen existente sem gerar biomas.
- **Dependências:** Nenhuma dependency externa publicada para a release NeoForge 1.21.1; requer a plataforma/jogo suportados.
- **Sobreposição:** Compartilha objetivo de navegação com mapas/locators, mas é especializado em biomas. O impacto principal é curatorial/progressão, não redundância técnica automática.
- **Compatibilidade/Riscos:** Não altera worldgen; consulta biome registry/distribuição. Riscos principais: shortcut de exploração, biome-tag drift, custo de buscas raras/simultâneas, dimension ambiguity e cobrança XP/durability. Nenhum conflito técnico concreto confirmado.
- **Observações:** Runtime literal 1.21.1-3.4.0-neoforge, file ID 7892954, Release 08/04/2026. 3.4.0 corrige `c:hidden_from_locator_selection` e adiciona next-instance search, XP cost e durability/repair.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 3.4.0 e changelog correspondente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/natures-compass
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Nature's Compass 3.4.0 reconstruído: biome search, next-instance search, XP/durability costs, hidden biome tag, GUI, worldgen boundary, performance, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `NaturesCompass-1.21.1-3.4.0-neoforge.jar`, mod id `naturescompass`, versão literal `1.21.1-3.4.0-neoforge`. A release exata é o CurseForge file ID `7892954`, publicada em 08/04/2026 para NeoForge 1.21.1. Nature's Compass **localiza** biomas registrados; não gera, move ou substitui worldgen.

## 1. Identidade e papel
- **Mod:** Nature's Compass.
- **JAR físico:** `NaturesCompass-1.21.1-3.4.0-neoforge.jar`.
- **Mod id:** `naturescompass`.
- **Runtime:** `1.21.1-3.4.0-neoforge`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** Chaosyr.
- **CurseForge project ID:** 252848; file ID 7892954.
- **Papel:** fornecer item + interface para pesquisar biomas e apontar ao resultado encontrado.
- **Dependências externas:** nenhuma publicada para a release.

## 2. Modelo funcional
O compass consulta os biomas registrados/disponíveis e permite que o jogador selecione um alvo. A busca retorna uma localização adequada e o item fornece orientação ao alvo.

Boundary importante:
- o mod **consulta** o worldgen/biome registry;
- ele não cria o bioma encontrado;
- não garante que um bioma exista em qualquer raio arbitrário;
- mods de worldgen continuam authority da distribuição real.

## 3. Busca por próxima instância
A 3.4.0 backporta a possibilidade de buscar a **próxima instância de um bioma já localizado**. Isso permite repetir a busca em vez de ficar preso ao primeiro resultado conhecido.

Esse comportamento altera o custo de exploração: um jogador pode deliberadamente procurar outra ocorrência do mesmo tipo de bioma. Quests/progressão devem considerar esse poder de navegação.

## 4. Custos configuráveis: XP
A release 3.4.0 adiciona opções para **consumir níveis de XP ao pesquisar**. Isso permite transformar a busca em recurso/progressão, em vez de locator gratuito.

Para o pack:
- não assumir o custo default sem ler o config físico;
- validar cobrança apenas quando a busca realmente é executada;
- impedir cobrança dupla em lag/retry;
- garantir comportamento claro quando XP é insuficiente.

## 5. Durabilidade e reparo
Outra opção adicionada permite atribuir **durabilidade** ao Nature's Compass. A release também adiciona recipe para reparar o compass quebrado quando essa mecânica está ativa.

Isso cria um segundo gate de uso. XP e durabilidade podem coexistir conforme configuração; testes devem cobrir as combinações realmente configuradas.

## 6. Seleção de biomas e hidden tag
A 3.4.0 corrige o uso da tag **`c:hidden_from_locator_selection`**, que deve impedir determinados biomas de aparecerem na seleção do locator.

Esse é um contrato relevante para datapacks/mods:
- esconder na seleção não remove o bioma do mundo;
- alteração da tag deve ser retestada após `/reload`;
- um biome modded com tag incorreta pode aparecer ou desaparecer indevidamente da GUI.

## 7. Interface
A release traz melhorias na **biome selection GUI**. A UI é client-facing, mas a localização precisa corresponder ao estado/world data autoritativo.

Problemas de UI a distinguir de problemas de busca:
- biome não aparece na lista → registry/tag/filter;
- aparece mas busca falha → search/world distribution;
- busca encontra mas indicação visual é incorreta → presentation/target state.

## 8. Dimensões e worldgen modded
Nature's Compass é particularmente sensível ao conjunto final de biomas e dimensões. Em packs grandes:
- datapacks podem alterar biome tags;
- worldgen mods podem mudar disponibilidade/distribuição;
- biomas podem existir só em dimensões específicas;
- mundos existentes podem ter chunks antigos e novos com distribuição diferente.

Não assumir cross-dimension search ou regras de dimensão sem validar o comportamento da build/config.

## 9. Performance
Buscar biomas pode ser computacionalmente mais caro do que uma interação comum, especialmente em mundos com grande variedade/worldgen complexo. Esta ficha não inventa raio, algoritmo ou custo de CPU da 3.4.0.

Prática de teste:
- medir comportamento em servidor real;
- evitar spam simultâneo de buscas por múltiplos jogadores;
- observar tick time durante searches de alvos raros.

## 10. Client/server e persistência
O item/UI possui componente client-facing; a busca e qualquer custo que afete XP/durabilidade precisam permanecer coerentes com server authority em multiplayer.

Estado relevante inclui alvo localizado no item/session conforme implementação. Esta auditoria não atribui NBT/component keys específicos sem source/JAR pinado.

## 11. Impacto curatorial no pack
O principal risco não é incompatibilidade técnica, mas **quebra de descoberta**: localizar qualquer bioma pode reduzir exploração orgânica e contornar quests/cartas/mapas planejados para revelar lugares.

Por isso `Sem decisão` é apropriado até a curadoria definir se:
- o item fica craftável normalmente;
- recebe custo elevado de XP/durabilidade;
- fica gated por quests;
- ou é removido por conflito com a experiência desejada.

## 12. Riscos
1. **Exploration shortcut:** reduz descoberta orgânica.
2. **Biome-tag drift:** `c:hidden_from_locator_selection` ou tags de worldgen podem mudar via datapack.
3. **Search cost/performance:** buscas raras e simultâneas podem pressionar servidor.
4. **Dimension ambiguity:** alvo pode não existir na dimensão esperada.
5. **Existing-world mismatch:** chunks antigos podem não refletir distribuição atual.
6. **XP/durability economy:** custos precisam ser transacionados exatamente uma vez.
7. **GUI vs registry:** lista client-side precisa refletir filtros válidos.

## 13. Matriz de testes
- [ ] Cliente + dedicated server iniciam com Nature's Compass 3.4.0.
- [ ] GUI lista biomas vanilla e modded válidos do pack.
- [ ] Bioma marcado `c:hidden_from_locator_selection` não aparece.
- [ ] `/reload` atualiza a seleção conforme tags sem estado stale.
- [ ] Busca encontra um bioma comum e orientação é coerente.
- [ ] “Próxima instância” encontra outra ocorrência válida do mesmo bioma.
- [ ] Busca por alvo raro/inexistente falha de forma segura.
- [ ] XP é cobrado exatamente uma vez conforme config, inclusive sob lag.
- [ ] Durabilidade/reparo funcionam conforme config quando habilitados.
- [ ] Dois jogadores buscando simultaneamente não causam desync significativo.
- [ ] Testar dimensão diferente e mundo com chunks antigos/novos.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: JAR/mod id/runtime literais.
- CurseForge oficial: project 252848, file ID 7892954, Release NeoForge 1.21.1 de 08/04/2026.
- Changelog 3.4.0: próxima instância, custos de XP, durabilidade/reparo, hidden-biome tag e melhorias da GUI.
- Relations oficiais: zero dependencies publicadas.
- **Limite:** algoritmo, raio, cache e formato de estado interno não foram inventados sem source/JAR específico.

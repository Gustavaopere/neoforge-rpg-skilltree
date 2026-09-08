# Apothic Compats

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81f9a8f3dbba4d755acb  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Apothic Compats
- **Arquivo JAR:** `apothic_compats-0.2.4.2.jar`
- **Versão 1.21.1:** `0.2.4.2`
- **Categoria:** Compat; RPG
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apothic-compats
- **Função:** Pacote data-driven de compatibilidade para o ecossistema Apotheosis/Apothic: adiciona, conforme o mod-alvo, affixed loot entries, gear sets, invaders, affixes, gems, loot categories, enchanting stats e regras específicas de affixability. Não é um segundo provider de affixes/gems.
- **Dependências:** Stack Apotheosis/Apothic correspondente; cada compat efetiva exige o mod-alvo presente. Ancient Reforging é suporte opcional upstream. Validar providers individualmente após updates.
- **Compatibilidade/Riscos:** Risco principal é drift de datapack após updates dos mods-alvo. A 0.2.4.2 foi atualizada para Apotheosis 8.6.0 e versões recentes dos providers, mas o próprio changelog avisa que algumas integrações ainda requerem touch-up. Evitar datapacks locais que dupliquem loot categories, affixed loot, gems ou invaders já fornecidos.
- **Sobreposição:** Complementar ao Apothic Category Compat. Category Compat trata roteamento/categorias; Apothic Compats entrega datapacks de integração com terceiros. Também não substitui Apotheosis, Apothic Attributes, Enchanting ou Spawners.
- **Observações:** mod id: apothic_compats; runtime name: Apothic Compats. O corpo da página lista tipos de compatibilidade, providers relevantes ao pack, boundaries com os demais módulos Apothic, riscos de drift e matriz de teste.
- **Procedência:** Runtime/JAR: modlist física auditada em 07/09/2026. Conteúdo e suporte: CurseForge oficial Apothic Compats + changelog 0.2.4.2. Arquitetura local: guia consolidado de Magia 07/09/2026.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita como dossiê operacional. O pack contém vários providers cobertos explicitamente por Apothic Compats, tornando o módulo útil como camada data-driven de compatibilidade; não confundir com Apothic Category Compat.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026. Runtime confirmado: apothic_compats-0.2.4.2.jar / 0.2.4.2. Dossiê exaustivo em nível data-driven: tipos de compat, providers suportados relevantes ao pack, affixed loot/gear sets/invaders/affixes/gems/loot categories/enchanting stats/affixability, boundaries com demais módulos Apothic, drift de datapack, double classification/loot e matriz de validação.
- **Data da última decisão:** 2026-09-07.

## Dossiê operacional — padrão Alex's Mobs

> 🔗 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta ficha documenta `apothic_compats-0.2.4.2.jar`, mod id `apothic_compats`, no runtime NeoForge 1.21.1. O inventário separa tipos de datapack, providers suportados, integrações efetivamente relevantes ao pack, boundaries com Category Compat/Attributes/Enchanting/Spawners, drift de versões e anti-duplicação. É uma **camada de compatibilidade data-driven**, não um segundo sistema de affixes, gems ou progressão.

## 1. Identidade, versão e papel
- **Mod:** Apothic Compats.
- **Runtime instalado:** `0.2.4.2`.
- **JAR físico:** `apothic_compats-0.2.4.2.jar`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** cliente + servidor.
- **Licença upstream:** MIT.
- **Papel no pack:** integrar conteúdo de outros mods ao ecossistema Apotheosis por datapacks e definições de compatibilidade já prontas.
- **Decisão:** **Manter** enquanto o ecossistema Apotheosis e os mods-alvo suportados permanecerem no pack.

## 2. O que ele realmente faz
O projeto existe para gerar e distribuir, como mod instalável, **datapacks de compatibilidade para Apotheosis**. Em vez de cada modpack recriar manualmente loot categories, affixed loot, gear sets, invaders, affixes e gems para conteúdo externo, Apothic Compats fornece essas definições para uma lista de mods suportados.

Isso significa que seu efeito é condicionado à presença do mod-alvo. A instalação do JAR não transforma conteúdo ausente em conteúdo disponível; cada bloco de compatibilidade só tem efeito quando o provider correspondente existe.

A versão `0.2.4.2`, publicada para 1.21.1 em 22/07/2026, foi atualizada para a linha Apotheosis 8.6.0 e para versões mais recentes dos mods suportados. O changelog do próprio autor ressalva que algumas integrações ainda precisariam de retoques diante de adições novas; portanto “suportado” não significa cobertura futura infinita.

## 3. Tipos de integração fornecidos
Dependendo do mod, o pacote pode adicionar:
- **affixed loot entries** — conteúdo externo pode aparecer nas tabelas de loot afixado;
- **gear sets** — conjuntos de equipamento externos tornam-se elegíveis ao sistema de loot do Apotheosis;
- **affixes** — regras específicas para tipos de item de terceiros;
- **gems** — gems próprias ou compatíveis ligadas ao conteúdo externo;
- **invaders** — entidades de outros mods podem participar das regras de Apothic Invaders;
- **loot categories** — classificação de tipos de item, inclusive slots Curios;
- **enchanting stats** — alguns blocos externos recebem estatísticas para o sistema Apothic Enchanting;
- **affixability específica** — armas ou ferramentas incomuns podem ser reconhecidas sem reclassificação genérica perigosa.

## 4. Compatibilidades upstream relevantes ao pack
A página oficial da versão 1.21.1 declara, entre outras, as seguintes integrações que cruzam diretamente com sistemas usados no pack:
- **Amendments:** skull piles e skull candles recebem enchanting stats.
- **Applied Energistics 2:** affixed loot entries e gear sets.
- **Alex's Caves:** invaders.
- **Alex's Mobs:** invaders.
- **Ars Nouveau:** affixed loot entries, affixes, gear sets, uma gem, invaders e suporte a focus curios.
- **Cataclysm:** affixed loot entries, gear sets e invaders.
- **Create:** Potato Cannons tornam-se affixable.
- **Curios:** categorias de loot para tipos-base de Curios, alterações de stats e curios afixados especiais encontrados em loot.
- **Farmer's Delight:** affixed loot entries.
- **Malum:** affixes, três gems, suporte a scythes/staves e a brooch/rune curios.
- **Mowzie's Mobs:** invaders.
- **Supplementaries:** candle holders recebem enchanting stats.

O upstream também suporta vários providers que podem não estar instalados. Eles não devem ser contabilizados como integração ativa apenas por aparecerem na lista oficial do mod.

## 5. Relação com os outros módulos Apothic
Esta distinção é obrigatória para não duplicar responsabilidade:
- **Apotheosis:** sistema principal de progression/affixes/gems/world tiers/loot relacionado.
- **Apothic Attributes:** atributos e pipeline de combate/atributos.
- **Apothic Enchanting:** enchanting, shelves, Eterna/Quanta/Arcana e infusion.
- **Apothic Spawners:** spawners e seus modifiers/stats.
- **Apothic Category Compat:** roteamento/classificação de categorias de item.
- **Apothic Compats:** **datapacks de integração com mods externos**.

Logo, Apothic Compats não deve ser tratado como provider autônomo de atributo, encantamento ou spawner.

## 6. Dependências e authority
- A compatibilidade exige o stack Apothic/Apotheosis correspondente à linha suportada.
- Cada integração adicional depende do mod-alvo estar realmente carregado.
- **Ancient Reforging** possui suporte adicional, mas é opcional segundo o upstream.
- A authority de presença/versão no pack é a modlist física; a authority de quais integrações o projeto declara é a documentação/source do Apothic Compats.

## 7. Riscos e pontos de validação
1. **Drift de versão dos providers:** uma definição data-driven pode continuar carregando e ainda assim estar semanticamente desatualizada após mudança de item/tag/categoria do mod-alvo.
2. **Cobertura incompleta por design:** a 0.2.4.2 declara que algumas integrações ainda precisavam de touch-up para adições recentes.
3. **Double classification:** não criar datapack local concorrente para a mesma categoria/affix sem comparar primeiro os arquivos fornecidos por este mod.
4. **Loot duplicado:** integrações customizadas do modpack devem verificar se a entrada já foi injetada por Apothic Compats antes de acrescentar nova regra.
5. **Curios:** categorias e itens afixados precisam ser validados junto ao stack Curios + Apothic Attributes, sem assumir que todo slot modded é coberto automaticamente.
6. **Provider ausente:** integração dependente deve simplesmente não existir; não inventar fallback de gameplay.

## 8. Sobreposição e decisão
A sobreposição aparente com Apothic Category Compat é apenas temática. Category Compat corrige/classifica categorias; Apothic Compats distribui um conjunto muito mais amplo de integrações data-driven com terceiros. No pack, ambos podem coexistir e atender responsabilidades diferentes.

**Decisão operacional: manter.** O ganho é especialmente relevante porque o pack contém vários dos providers explicitamente suportados, e remover este módulo reduziria a integração de loot/equipamentos sem oferecer substituto automático.

## 9. Matriz mínima de teste
- inicialização cliente e dedicated server sem erros de datapack;
- reload de datapacks sem conflitos/duplicate keys;
- validar exemplos de AE2, Ars Nouveau, Create, Curios, Malum e mobs suportados presentes;
- verificar que itens externos recebem **uma** categoria/affix path coerente, não duas;
- testar loot/invaders em amostra controlada e confirmar ausência de duplicação;
- repetir após updates de Apotheosis ou de qualquer provider coberto.

## 10. Fontes
- CurseForge — Apothic Compats: https://www.curseforge.com/minecraft/mc-mods/apothic-compats
- Arquivo 0.2.4.2 / changelog: https://www.curseforge.com/minecraft/mc-mods/apothic-compats/files/8483936
- Modlist física do projeto — authority do JAR e runtime instalados.

# Apothic Compats

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#33**: `apothic_compats-0.2.4.2.jar`, mod id `apothic_compats`, runtime `0.2.4.2`, SHA-1 `46d3699a4af63531fe84c69fdd2623fbe71fbc75`.

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 22/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apothic Compats
- **Arquivo JAR:** `apothic_compats-0.2.4.2.jar`
- **Versão 1.21.1:** 0.2.4.2
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, RPG
- **Função:** Pacote data-driven de compatibilidade para o ecossistema Apotheosis/Apothic: adiciona, conforme o mod-alvo, affixed loot entries, gear sets, invaders, affixes, gems, loot categories, enchanting stats e regras específicas de affixability. Não é um segundo provider de affixes/gems.
- **Dependências:** Stack Apotheosis/Apothic correspondente; cada compat efetiva exige o mod-alvo presente. O pack físico está em Apotheosis 8.8.0, enquanto Apothic Compats 0.2.4.2 foi publicada para a linha 8.6.0. Upstream 0.2.4.3 alinha o compat a 8.8.0; a linha 0.2.5.2+ já mira Apotheosis 8.9.0.
- **Sobreposição:** Complementar ao Apothic Category Compat. Category Compat trata roteamento/categorias; Apothic Compats entrega datapacks de integração com terceiros. Também não substitui Apotheosis, Apothic Attributes, Enchanting ou Spawners.
- **Compatibilidade/Riscos:** Runtime físico 0.2.4.2. Upstream avançou 0.2.4.3→0.2.5→0.2.5.1→0.2.5.2→0.2.5.3→0.2.5.4→0.2.5.5. A linha migra para Apotheosis 8.8.0/8.9.0, amplia integrações data-driven e contém um regression/fix explícito de mob detection/hostile spawning na 0.2.5.5.
- **Observações:** mod id `apothic_compats`, runtime físico 0.2.4.2. A latest 1.21.1 é 0.2.5.5 (28/09/2026). Iron's Artifice não foi encontrado na modlist física atual, então as novas integrações específicas dele permanecem dormentes nesta instância.
- **Procedência:** modlist física atual confirma `apothic_compats-0.2.4.2.jar` / 0.2.4.2. CurseForge oficial e source `ianm1647/apothic-compats` revalidados em 02/10/2026 confirmam a cadeia publicada até 0.2.5.5 e os commits de migração/fixes.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apothic-compats
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 02/10/2026 — runtime físico permanece 0.2.4.2. Todas as releases 1.21.1 posteriores até 0.2.5.5 foram percorridas; deltas verificáveis e lacunas de changelog são registrados abaixo.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita como dossiê operacional. O pack contém vários providers cobertos explicitamente por Apothic Compats, tornando o módulo útil como camada data-driven de compatibilidade; não confundir com Apothic Category Compat.
- **Data da última decisão:** 2026-09-07

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
- **Applied Energistics 2:** o suporte upstream existe para affixed loot entries e gear sets, porém **AE2 está ausente do snapshot físico atual de 08/09/2026**; portanto esta integração está dormente nesta instância.
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
- validar exemplos de Ars Nouveau, Create, Curios, Malum e mobs suportados presentes; se AE2 voltar ao pack, validar affixed loot/gear sets dessa integração em lote próprio;
- verificar que itens externos recebem **uma** categoria/affix path coerente, não duas;
- testar loot/invaders em amostra controlada e confirmar ausência de duplicação;
- repetir após updates de Apotheosis ou de qualquer provider coberto.

## 10. Fontes
- [CurseForge — Apothic Compats](https://www.curseforge.com/minecraft/mc-mods/apothic-compats)
- [Arquivo 0.2.4.2 / changelog](https://www.curseforge.com/minecraft/mc-mods/apothic-compats/files/8483936)
- Modlist física do projeto — authority do JAR e runtime instalados.

## 11. Atualizações upstream 0.2.4.3 → 0.2.5.5 — não instaladas

A autoridade física continua em **Apothic Compats 0.2.4.2**. A sequência publicada para NeoForge 1.21.1 é:

`0.2.4.3 → 0.2.5 → 0.2.5.1 → 0.2.5.2 → 0.2.5.3 → 0.2.5.4 → 0.2.5.5`.

### 0.2.4.3
O changelog oficial registra atualização para **Apotheosis 8.8.0**. O source da mesma linha também contém refactor do gerador/data providers.

### 0.2.5
O source adiciona integração com **Iron's Artifice**, incluindo invader/gear-set data e lógica para entidades compatíveis utilizarem esse conteúdo. Iron's Artifice não foi localizado na modlist física atual; portanto essa superfície é considerada dormente nesta instância.

### 0.2.5.1
O source corrige aplicação do conteúdo de Iron's Artifice em bosses de perfil melee e inclui fixes ligados aos issues #31/#32.

### 0.2.5.2
A release existe na cadeia pública, mas não foi localizado changelog funcional distinto confiável além do bump posterior aos fixes da linha 0.2.5.1. Nenhuma mudança adicional é inventada.

### 0.2.5.3
O source corrige o issue #33 e amplia **extra gem bonuses** ligados a Iron's Artifice.

### 0.2.5.4
Atualiza a linha de affixes para **Apotheosis 8.9.0** e regenera dados correspondentes. A promoção exige compatibilidade com essa linha do provider principal.

### 0.2.5.5
A latest pública 1.21.1. O changelog oficial diz que a release **reverte a mudança de mixin usada para detectar mobs**. O source imediatamente anterior registra o problema como **hostile entities not spawning**, portanto esta build é o baseline da cadeia 0.2.5.x para evitar esse regression.

### Gate de promoção 0.2.4.2 → 0.2.5.5
- [ ] Atualizar/revalidar o stack Apotheosis/Apothic contra a linha requerida pela 0.2.5.5.
- [ ] Dedicated server inicia sem datapack/mixin/registry errors.
- [ ] Entidades hostis continuam spawnando normalmente — regressão explícita fechada pela 0.2.5.5.
- [ ] Providers presentes recebem apenas um path de affix/loot/category, sem duplicação.
- [ ] Providers ausentes não geram missing-registry ou fallback indevido.
- [ ] Iron's Artifice permanece dormente enquanto ausente; se for adicionado, validar invaders, gear sets e gem bonuses em lote próprio.
- [ ] `/reload` não duplica affixes, loot entries, invaders ou gem bonuses.
- [ ] Curios/Ars/Create/Malum e demais providers usados no pack continuam semanticamente alinhados após a migração para Apotheosis 8.9.x.

Fontes upstream: CurseForge Apothic Compats 0.2.4.3 (file ID 8936047), sequência pública 0.2.5–0.2.5.5 e latest 0.2.5.5 (file ID 8996735); source oficial `ianm1647/apothic-compats`. Nenhum teste acima foi executado nesta atualização documental.

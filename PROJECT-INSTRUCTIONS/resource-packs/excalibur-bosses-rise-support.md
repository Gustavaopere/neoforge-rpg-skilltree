# Excalibur | Bosses'Rise Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81299c56c8925ac4aa70
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Bosses'Rise 1.5 (Neoforge).zip`
- **Versão 1.21.1:** 1.5
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Bosses'Rise 1.5 (Neoforge).zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Bosses'Rise `2.1.2` e ETF presente. A descrição upstream usa “most entities”, portanto cobertura de entidades permanece explicitamente parcial e não é convertida em 100%.

## Propriedades do banco

- **Mod:** Excalibur | Bosses'Rise Support
- **Arquivo JAR:** `Excalibur Bosses'Rise 1.5 (Neoforge).zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Mobs
- **Função:** Support pack 16x/medieval que retexturiza Bosses'Rise para o estilo Excalibur, cobrindo weapons/armor, blocks, spawn eggs e a maioria das entidades.
- **Dependências:** Uso visual pretendido: Excalibur + Bosses'Rise. Stack físico atual: Bosses'Rise 2.1.2; ETF também está presente e é usado por variantes visuais documentadas.
- **Sobreposição:** Sobrepõe somente recursos visuais de Bosses'Rise/ETF. Outro pack com os mesmos asset paths pode vencer conforme prioridade; não há sobreposição de gameplay.
- **Compatibilidade/Riscos:** Cobertura de entidades é descrita como 'most entities', não 100%. Riscos de drift com Bosses'Rise 2.1.2, load order, colisão com outros retextures e seleção ETF incorreta.
- **Observações:** Arquivo instalado `Excalibur Bosses'Rise 1.5 (Neoforge).zip`. 1.5 é a release atual 1.21.1 em 10/08/2026. O pack documenta variante de cadáveres esqueléticos no Nether via ETF.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial da release 1.5 NeoForge para Minecraft 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-bossesrise
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; cobertura Bosses'Rise 2.1.2, ETF variants, load order, lifecycle, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Bosses'Rise 1.5 (Neoforge).zip`, versão `1.5`, Release para Minecraft 1.21.1. O projeto é um support pack 16x/medieval para Bosses'Rise.

## 1. Papel e authority
Excalibur | Bosses'Rise Support substitui assets visuais de **Bosses'Rise** para aproximar o mod do estilo Excalibur. Bosses'Rise continua authority de bosses, entidades, armas, armaduras, blocos, IA, dano, loot e progression.
O resource pack não deve ser tratado como mod de conteúdo nem como fonte de IDs de gameplay.

## 2. Cobertura confirmada
A descrição oficial da 1.5 afirma redesign de **todas as armas/armaduras, blocos e a maioria das entidades** para o estilo Excalibur.
O projeto também altera spawn eggs e possui variantes visuais contextuais. Não converter “most entities” em cobertura 100% de entidades; o próprio wording upstream preserva uma cobertura parcial nessa categoria.

## 3. ETF e variantes contextuais
O pack documenta que cadáveres de tipo esqueleto podem assumir aparência semelhante a Wither quando estão no Nether, com suporte de **Entity Texture Features (ETF)**.
ETF está fisicamente presente no modpack. Essa camada é visual: biome/dimension/entity state continua pertencendo ao mod/vanilla; ETF apenas seleciona/renderiza variante quando o contract de textura permite.

## 4. Stack físico atual
- Bosses'Rise `2.1.2`.
- Resource pack `1.5`.
- ETF presente no pack.
A build visual 1.5 é a release NeoForge 1.21.1 atual publicada em 10/08/2026, portanto não há drift de versão conhecido do próprio resource pack neste momento.

## 5. Load order
Para que o visual de suporte prevaleça, o pack deve ter prioridade acima do Excalibur base e de assets padrão de Bosses'Rise. Outro retexture do mesmo namespace pode substituí-lo se estiver acima.

## 6. Client e resource reload
Entidades, armor/item textures, blocks, spawn eggs e variantes ETF são resolvidos no cliente. Resource reload/relog deve atualizar o visual sem tocar boss state, health, AI, loot ou save.

## 7. Sobreposição
Pode sobrepor outros packs para Bosses'Rise, ETF rules ou assets compartilhados. Sobreposição visual não implica conflito de gameplay.
Se outro pack alterar o mesmo boss/entity, a ordem de resource packs precisa ser registrada para evitar mistura parcial de modelos/texturas.

## 8. Riscos
1. “Most entities” deixar alguns mobs no estilo original.
2. Bosses'Rise 2.1.2 adicionar asset não coberto pela 1.5.
3. ETF variant não carregar ou selecionar texture errada.
4. Load order gerar mistura Excalibur/default.
5. Outro pack sobrescrever armor/weapon/entity assets.
6. Resource reload deixar entidade já renderizada com cache visual stale até refresh.

## 9. Matriz de testes
- [ ] Bosses principais renderizam no estilo Excalibur.
- [ ] Weapons/armor e blocks do mod usam os assets do support pack.
- [ ] Spawn eggs retexturizados aparecem corretamente.
- [ ] Variante de cadáver esquelético no Nether funciona com ETF.
- [ ] Entidades fora do Nether mantêm variante esperada.
- [ ] Resource reload não produz missing textures/models.
- [ ] Comparar uma amostra ampla de entidades para detectar cobertura parcial.
- [ ] Confirmar prioridade acima do Excalibur e de outros retextures do mesmo mod.

Nenhum teste foi marcado como aprovado.

## 10. Evidências e limite
- captura CurseForge do perfil: 1.5 NeoForge instalada;
- modlist física: Bosses'Rise 2.1.2 + ETF presente;
- CurseForge oficial: 1.5 para 1.21.1, redesign de weapons/armor, blocks e most entities, além da variante esquelética/Nether via ETF.
Não foi auditado o ZIP internamente; lista exata de asset paths permanece não confirmada.

> Boundary canônico: **Bosses'Rise controla combate e conteúdo; este pack controla apenas aparência/seleção de assets visuais**.

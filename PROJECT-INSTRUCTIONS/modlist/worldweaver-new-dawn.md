# WorldWeaver: New Dawn

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814da1f8efc45c35c92a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** WorldWeaver: New Dawn
- **Arquivo JAR:** `worldweaver-21.0.25.jar`
- **Versão 1.21.1:** `21.0.25`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; BetterX stack, changelog 21.0.25, troubleshooting e worldgen boundaries catalogados.
- **Categoria:** Biblioteca; Worldgen
- **Compatibilidade/Riscos:** Riscos: biome distribution/preset drift, mixin interaction e consumer coupling. Troubleshooting histórico não provou defeito isolado do WorldWeaver; não continuar downgrade sem reprodução. 21.0.25 corrige TerraBlender/Nether Descent e distribuição BetterNether/vanilla.
- **Decisão:** Manter
- **Dependências:** Stack atual: BetterEnd 21.0.34, BetterNether 21.0.26, BCLib 21.0.26 e WunderLib 21.0.10. WorldWeaver 21.0.25 é componente complementar, não redundante automático.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/worldweaver-neoforge
- **Função:** Biblioteca de worldgen do stack BetterX/New Dawn, responsável por infraestrutura e compatibilidade de distribuição de biomas/presets consumida por BetterEnd/BetterNether.
- **Histórico da decisão:** Durante troubleshooting em 22/08/2026, 21.0.25 e 21.0.24 foram testados enquanto uma camada externa de worldgen ainda participava da falha. Após remover essa camada, BetterX/New Dawn voltou a funcionar e WorldWeaver 21.0.25 foi restaurado. Decisão vigente: MANTER; não continuar downgrade.
- **Observações:** Mod id `wover`, runtime 21.0.25. Decision `Manter` preservada. Worldgen infrastructure não gera Mastery; quests devem usar identity final do biome/provider.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial WorldWeaver 21.0.25 + histórico de troubleshooting/decisão vigente no Notion.
- **Sobreposição:** Infraestrutura complementar a BCLib/WunderLib/TerraBlender; não é substituto de BetterEnd/BetterNether nem redundância automática por categoria.
- **Data da última decisão:** 2026-08-22

> 🌍 **ESCOPO CANÔNICO.** Runtime físico: `worldweaver-21.0.25.jar`, mod id `wover`, versão `21.0.25`. WorldWeaver: New Dawn é uma **biblioteca de worldgen do stack BetterX/New Dawn**, usada para biome distribution e infraestrutura compartilhada. A decisão vigente é **Manter**; o troubleshooting histórico não demonstrou defeito isolado do WorldWeaver.

## 1. Identidade e maturidade
A build 21.0.25 é Release NeoForge 1.21.1 publicada em 07/08/2026 e permanece a release 1.21.1 atual do projeto.

O projeto se descreve como continuação mantida do WorldWeaver original, com port NeoForge e correções/manutenção do sistema de world generation.

## 2. Stack BetterX atual
A modlist física confirma:
- BetterEnd 21.0.34;
- BetterNether 21.0.26;
- BCLib 21.0.26;
- WorldWeaver 21.0.25;
- WunderLib 21.0.10.

Essas libraries/mods possuem papéis complementares. A presença de BCLib/WunderLib/TerraBlender não torna WorldWeaver redundante por nome ou categoria.

## 3. Função e authority
WorldWeaver participa da distribuição/compatibilidade de biomas e outras primitives de worldgen consumidas pelo BetterX New Dawn.

Authority:
- WorldWeaver: regras/infraestrutura que expõe aos consumers;
- BetterEnd/BetterNether: biomas, features e conteúdo próprio;
- Minecraft/NeoForge/TerraBlender e demais providers: respectivas pipelines/registries.

Não recriar placement/distribution do consumer em mod próprio quando a library já fornece o contrato necessário.

## 4. Mudanças confirmadas da 21.0.25
O changelog oficial da build exata registra:
- fix para biomas TerraBlender e Nether Descent não gerarem com preset BetterX quando BetterNether está ausente;
- compatibilidade de biomas respeitando configuração de biomas desabilitados;
- preservação da distribuição BetterNether existente quando seus biomas estão habilitados;
- prevenção de Warped Forest e Basalt Deltas serem completamente deslocados por biomas BetterNether no preset Default.

Esses pontos tornam 21.0.25 diretamente relevante à composição de Nether/world presets do pack.

## 5. Troubleshooting histórico preservado
Durante troubleshooting anterior, 21.0.25/21.0.24 foram testados enquanto outra camada de worldgen ainda participava da falha `ModelProviderMixin`. Após remover a camada problemática, BetterX/New Dawn voltou a funcionar e 21.0.25 foi restaurado.

Conclusão canônica: **não atribuir aquela falha histórica ao WorldWeaver isoladamente** e não continuar downgrade sem nova evidência.

## 6. Client/server e mixin surface
O JAR atual possui ampla superfície de mixins common/client envolvendo biome, item, core, surface, datagen, POI, block, generator, preset, recipe, feature, tag, UI e structure.

Isso aumenta a importância de dedicated-server/client boot e data-generation/resource reload smoke. A presença de mixins não prova conflito; conflitos devem ser reproduzidos e atribuídos.

## 7. Worldgen lifecycle
Validar especialmente:
- criação de mundo com presets usados pelo pack;
- chunks novos;
- biome config enable/disable;
- datapack reload quando suportado;
- save/restart;
- atualização de versão em mundo existente;
- coexistência BetterEnd/BetterNether;
- ausência de displacement anormal de biomas vanilla.

Worldgen existente em chunks já gerados não deve ser reinterpretado como se a nova distribuição fosse retroativa.

## 8. Boundary para quests/perks
WorldWeaver é infraestrutura e não evento de progressão. Não conceder Mastery por biome placement, chunk generation ou library hooks.

Se uma quest depender de um biome BetterX, usar identity do biome/provider final, não WorldWeaver como proxy causal.

## 9. Riscos
1. **Biome distribution drift:** updates mudam compatibilidade/placement.
2. **Preset interaction:** BetterX/Default/TerraBlender paths divergem.
3. **Consumer coupling:** BetterEnd/BetterNether esperam contratos específicos.
4. **Mixin interaction:** outro worldgen mod altera a mesma superfície.
5. **False blame from historic troubleshooting:** correlação antiga tratada como causa isolada sem reprodução.
6. **World upgrade:** novas regras só afetam chunks futuros de forma normal.

## 10. Matriz de testes
- [ ] Dedicated server/client boot com WorldWeaver 21.0.25 + BetterX atual.
- [ ] Criar mundo com preset efetivamente usado no pack.
- [ ] BetterEnd/BetterNether biomes geram sem registry/data errors.
- [ ] Config de biome disabled é respeitada.
- [ ] Warped Forest/Basalt Deltas não são indevidamente eliminados.
- [ ] TerraBlender/Nether Descent paths relevantes geram corretamente.
- [ ] Save/restart e chunk generation incremental permanecem determinísticos dentro do esperado.
- [ ] Upgrade em cópia de mundo existente não corrompe chunks antigos.
- [ ] Regressão futura é reproduzida antes de culpar/downgradear WorldWeaver isoladamente.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências
- **Modlist física 08/09/2026:** WorldWeaver 21.0.25, BetterEnd 21.0.34, BetterNether 21.0.26, BCLib 21.0.26 e WunderLib 21.0.10.
- CurseForge oficial WorldWeaver: continuation/port de library de worldgen; Release 21.0.25 e changelog de biome compatibility/distribution.
- Histórico do projeto/Notion: troubleshooting anterior e decisão `Manter` após restauração do stack funcional.

## 12. Limitação
Não foram decompilados os algoritmos internos de distribuição nem comparados todos os providers worldgen do pack nesta etapa. Compatibilidade concreta deve ser validada em seed/preset real.

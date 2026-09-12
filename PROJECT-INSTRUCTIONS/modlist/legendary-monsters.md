# Legendary Monsters

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81588456c17391c19c3a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — filename/publicação `2.2.2`, metadata runtime divergente `1.21.1` e Legendary Spellbooks `0.3.2` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Legendary Monsters
- **Arquivo JAR:** `legendary_monsters-2.2.2 MC 1.21.1.jar`
- **Versão 1.21.1:** 2.2.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs, RPG, Worldgen, Exploração
- **Função:** Provider de criaturas/encounters, estruturas, loot/equipamentos, archaeology e lógica própria de AI/combate, com efeitos/render associados.
- **Dependências:** NeoForge 1.21.1. Integração física Legendary Spellbooks 0.3.2 existe separadamente; dependências hard adicionais não foram inventadas sem metadata exata da build 2.2.2.
- **Sobreposição:** Sobrepõe-se funcionalmente a outros mods de bosses/worldgen apenas em domínio, não em ownership. Bridges/quests devem reagir ao provider em vez de duplicar spawn, damage ou loot.
- **Compatibilidade/Riscos:** Source público mais novo localizado ainda está em 2.1.15, então 2.2.2 tem source gap. Riscos: worldgen/loot duplication, BlockEntity lifecycle, encounter duplication, combat bridge drift e skew com Legendary Spellbooks.
- **Observações:** JAR/publicação oficial confirmam 2.2.2, enquanto a metadata runtime exposta na modlist registra `1.21.1`; o campo de versão foi corrigido para a release efetivamente instalada, preservando a discrepância como evidência.
- **Procedência:** modlist.txt física atual + CurseForge oficial Legendary Monsters 2.2.2 + repositório público oficial Miauczel/Legendary-Monsters-1.21.1-NeoForge predecessor 2.1.15.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/legendary-monsters
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — release 2.2.2 reconciliada com metadata interna divergente; source-gap 2.1.15, worldgen/archaeology, AI/combat, BlockEntity, lifecycle, risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `legendary_monsters-2.2.2 MC 1.21.1.jar`, mod id `legendary_monsters`. A release/publicação instalada é **2.2.2** para Minecraft 1.21.1; a metadata interna exposta pela modlist registra `1.21.1`, portanto o número de publicação/JAR oficial é a referência de versão desta ficha.

## 1. Identidade e version authority
CurseForge confirma a release 2.2.2 para NeoForge 1.21.1, publicada em 23/08/2026 com o mesmo filename físico. O changelog 2.2.2 corrige o modelo do Mossy Hammer e suspicious sand/gravel vazios em estruturas novas. Como o JAR/arquivo oficial e a publicação convergem, `Versão 1.21.1` deve registrar 2.2.2, com a discrepância da metadata interna explicitamente documentada.

## 2. Source gap
O repositório público `Miauczel/Legendary-Monsters-1.21.1-NeoForge` é a fonte oficial disponível, mas sua revisão mais recente localizada (`f412a3e5a583705502dd924aee9a0d672a607e76`, 15/07/2026) ainda declara `mod_version=2.1.15 MC 1.21.1`. Portanto ele é **predecessor técnico**, não source exato da build 2.2.2. Nenhuma classe/registry nova exclusiva de 2.2.2 é afirmada a partir desse source.

## 3. Papel no modpack
Legendary Monsters é um provider de criaturas hostis/boss-like encounters, estruturas associadas, loot/equipamentos e efeitos de combate. Seu impacto é maior que o de um simples mob pack: spawn, AI, block interaction, projectiles/VFX, estruturas e recompensas precisam ser tratados como subsistemas autoritativos do mod.

## 4. Entidades, AI e combate
O source predecessor confirma registro próprio de entidades e evolução ativa de VFX/AI, incluindo trabalho recente no Cloud Golem, boss tags e mecanismos anti-block-cheese. Essas evidências sustentam que os encounters usam lógica própria de movimento/combate e não devem ser reinterpretados por um addon como mobs vanilla genéricos.

## 5. Estruturas e worldgen
A release 2.2.2 menciona explicitamente estruturas novas com suspicious sand/gravel, e o histórico do source contém correções de feature spawn. Isso estabelece worldgen/estrutura como parte material do mod. A distribuição exata de cada estrutura da 2.2.2 não foi enumerada nesta passagem porque o source público correspondente à build não está disponível.

## 6. Loot e arqueologia
O fix de 2.2.2 para suspicious sand/gravel vazio é economicamente relevante: blocos arqueológicos em estruturas precisam produzir loot conforme o datapack/loot table da versão. Quests ou scripts não devem conceder recompensa adicional apenas por detectar o bloco visualmente; o resultado real do loot é a autoridade causal.

## 7. Block entities
O source predecessor registra pelo menos o sistema `ender_anchor` como BlockEntity. Isso comprova que certos encounters/estruturas dependem de state de bloco persistente e lifecycle de chunk. Não assumir que mover/quebrar esses blocos por contraptions preserve state sem teste específico.

## 8. Client / server
Spawn, AI, dano, loot, block/entity state e progressão de encounter pertencem ao servidor. VFX/model/render permanecem client-side. Como o desenvolvimento recente inclui VFX upgrades, mods de render/animação podem interagir visualmente sem ter authority sobre hit/kill.

## 9. Integração com Legendary Spellbooks
O pack contém `legendary_spellbooks-1.21.1+neo-0.3.2.jar`, integração direta com Iron's Spells. O source dessa integração foi pinado contra uma linha anterior de Legendary Monsters, enquanto o runtime atual está em 2.2.2. Portanto a compatibilidade precisa ser regressada em runtime, especialmente loot modifiers, entity IDs e summons.

## 10. Lifecycle crítico
Validar cold boot, geração de chunks, spawn/despawn, boss death, chunk unload/reload e salvamento de BlockEntities. Estruturas com archaeology blocks devem manter loot exatamente uma vez; encounters não devem duplicar mob/reward após relog ou unload.

## 11. Riscos técnicos
1. **Source drift:** source público 2.1.15 não representa integralmente 2.2.2.
2. **Worldgen overlap:** estruturas podem disputar espaço com Cataclysm/IDAS/Integrated Dungeons.
3. **Loot duplication:** archaeology/loot injection por addons ou scripts.
4. **Encounter duplication:** chunk reload ou structure respawn produzindo múltiplas entidades.
5. **BlockEntity lifecycle:** `ender_anchor` ou sistemas equivalentes perdendo/duplicando state.
6. **Combat bridge drift:** Epic Fight/magic integrations reagindo ao mesmo hit duas vezes.
7. **Legendary Spellbooks version skew:** integração compilada contra predecessor do provider.

## 12. Matriz de testes
- [ ] Dedicated server inicia com Legendary Monsters 2.2.2.
- [ ] Estruturas novas geram com archaeology loot não vazio.
- [ ] Chunk unload/reload não duplica encounter ou loot.
- [ ] Boss/elite death concede recompensa exatamente uma vez em MP.
- [ ] BlockEntities persistem corretamente após restart.
- [ ] Mossy Hammer renderiza sem modelo quebrado.
- [ ] Legendary Spellbooks 0.3.2 resolve entidades/loot modifiers contra 2.2.2.
- [ ] Epic Fight e outros bridges não causam double-hit/double-stagger.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- modlist física: filename 2.2.2, mod id e runtime metadata discrepante;
- CurseForge oficial: release 2.2.2 NeoForge 1.21.1 e changelog da versão;
- source oficial público predecessor: commit `f412a3e…`, versão 2.1.15, usado apenas para contratos que podem ser confirmados sem fingir equivalência com 2.2.2.
A ausência de source público exato da 2.2.2 fica registrada como limite de verificabilidade, não preenchida por suposição.

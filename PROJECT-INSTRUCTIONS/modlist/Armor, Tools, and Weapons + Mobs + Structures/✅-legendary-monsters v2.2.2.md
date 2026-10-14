# Legendary Monsters

## Propriedades do registro

- **Mod:** Legendary Monsters
- **Arquivo JAR:** legendary_monsters-2.2.2 MC 1.21.1.jar
- **Versão 1.21.1:** 2.2.2
- **Categoria:** Mobs, RPG, Worldgen, Exploração
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/legendary-monsters
- **Função:** Provider de criaturas/encounters, estruturas, loot/equipamentos, archaeology e lógica própria de AI/combate, com efeitos/render associados.
- **Dependências:** NeoForge 1.21.1. Integração física Legendary Spellbooks 0.3.2 existe separadamente; dependências hard adicionais não foram inventadas sem metadata exata da build 2.2.2.
- **Compatibilidade/Riscos:** Source público mais novo localizado ainda está em 2.1.15; as releases distribuídas 2.2.2 e 2.2.3 têm source gap. Riscos: worldgen/loot duplication, BlockEntity lifecycle, encounter duplication, combat bridge drift, render Create e skew com Legendary Spellbooks.
- **Sobreposição:** Sobrepõe-se funcionalmente a outros mods de bosses/worldgen apenas em domínio, não em ownership. Bridges/quests devem reagir ao provider em vez de duplicar spawn, damage ou loot.
- **Observações:** JAR/publicação oficial confirmam 2.2.2 como último estado físico verificado, enquanto a metadata runtime exposta na modlist registra `1.21.1`; a publicação 2.2.3 de 17/09/2026 está disponível, mas não instalada/certificada.
- **Procedência:** modlist(1).txt snapshot de 25/09/2026 + CurseForge oficial Legendary Monsters File 8715533 (2.2.2), File 8902342 (2.2.3 de 17/09/2026) NeoForge 1.21.1 + source oficial público predecessor 2.1.15, usado somente dentro dos limites já documentados.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/10/2026 — último snapshot físico de 25/09/2026: Legendary Monsters 2.2.2/JAR histórico; upstream **2.2.3** (17/09/2026, NeoForge 1.21.1) registrado na seção 14 como **não instalado**. Discrepância metadata interna `1.21.1` vs release/JAR 2.2.2 e source gap 2.1.15 preservados; QA de Create/Spellbooks pendente.
- **Data da última decisão:** 2026-08-26

> **Última autoridade física verificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #371: JAR `legendary_monsters-2.2.2 MC 1.21.1.jar`, mod id `legendary_monsters`, metadata runtime `1.21.1`; a release/filename instalada é `2.2.2`, conforme discrepância preservada no dossiê; SHA-1 `8910859ba94190dd8cbc8c2c1e2f07562db729b4`.

> **ESCOPO CANÔNICO.** O snapshot físico de 25/09/2026 registra `legendary_monsters-2.2.2 MC 1.21.1.jar` (mod ID `legendary_monsters`, distribuição **2.2.2**) e metadata interna **`1.21.1`**. A discrepância faz parte da evidência histórica. Existe uma distribuição posterior **2.2.3 (17/09/2026)**, mas não foi verificada como instalada e seu binário não foi comparado ao JAR histórico.
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

## 14. Reauditoria upstream 2.2.3 e impacto Create/Spellbooks — 14/10/2026

### 14.1. Distinção entre release histórica e latest NeoForge 1.21.1

- **Fonte primária do estado físico histórico #371 (25/09/2026):** `legendary_monsters-2.2.2 MC 1.21.1.jar`, mod ID `legendary_monsters`, metadata interna `1.21.1` (não 2.2.2), SHA-1 `8910859ba94190dd8cbc8c2c1e2f07562db729b4`. Publicação correspondente nominal: [CurseForge file `8715533`](https://www.curseforge.com/minecraft/mc-mods/legendary-monsters/files/8715533), **23/08/2026**, release 2.2.2; filename oficial igual ao inventário histórico. **Hash físico não comparado ao download do portal** neste ciclo.
- **Atualização oficial disponível:** [CurseForge file `8902342`](https://www.curseforge.com/minecraft/mc-mods/legendary-monsters/files/8902342), **17/09/2026**, Minecraft **1.21.1 NeoForge**, release **2.2.3**, arquivo `legendary_monsters-2.2.3 MC 1.21.1.jar`, ambiente divulgado pelo projeto como **Client & Server**. A listagem [CurseForge oficial](https://www.curseforge.com/minecraft/mc-mods/legendary-monsters/files/all) confirma a 2.2.3 como release mais recente encontrada para esse loader e MC. **Não instalada no último snapshot**; a data do snapshot (25/09) posterior à publicação não autoriza mudar o estado físico de 2.2.2.
- **Changelog 2.2.3 exclusivamente confirmado pelo autor:** “New Possessed Paladin's soulstrike animation sprite”, “Fixed a few bugs”, “Fixed Create render issue”. Isto confirma **uma nova sprite/animação Soulstrike do Possessed Paladin**, **correções gerais não especificadas** e **uma correção de renderização ligada ao Create**. Não ampliar “a few bugs” para alterações de boss AI, spawn, hitboxes, loot, cooldowns, estruturas ou compatibilidade de runtime sem evidência independente.
- **Source oficial acessível ainda anterior:** [`Miauczel/Legendary-Monsters-1.21.1-NeoForge`, commit `f412a3e5a583705502dd924aee9a0d672a607e76`](https://github.com/Miauczel/Legendary-Monsters-1.21.1-NeoForge/tree/f412a3e5a583705502dd924aee9a0d672a607e76); [`gradle.properties`](https://github.com/Miauczel/Legendary-Monsters-1.21.1-NeoForge/blob/f412a3e5a583705502dd924aee9a0d672a607e76/gradle.properties) declara `mod_version=2.1.15 MC 1.21.1`, NeoForge de compilação `21.1.219`, `mod_id=legendary_monsters` e licença declarada `All Rights Reserved`. Esse source **não representa exatamente o binário distribuído 2.2.2 ou 2.2.3** e não pode certificar APIs, animations, attack triggers e registries específicos de nenhuma das duas releases.

### 14.2. Autoridade dos sistemas, integração com Create e arquitetura de perks

Legendary Monsters continua provider autoritativo para **entidades, bosses, AI, mortes, projéteis, loot, suspicious sand/gravel, estruturas, efeitos server-side e BlockEntities**. Create #116 é provider de **contraptions e rendering próprio**, mas o changelog 2.2.3 apenas relata um “Create render issue”; não demonstra um bridge novo, nem informa quais entities, shaders ou block renderers foram corrigidos. Sem comparação de classes e reprodução em cliente, essa melhoria deve permanecer **pendente de validação**.

O snapshot contém **Create #116 `create-1.21.1-6.0.10.jar`**, **Legendary Spellbooks #372 `legendary_spellbooks-1.21.1+neo-0.3.2.jar`**, **Iris #338 `iris-neoforge-1.8.14-beta.1+mc1.21.1.jar`** e **Sodium #512 `sodium-neoforge-0.8.13+mc1.21.1.jar`**. São superfícies plausíveis de regressão gráfica ou de efeitos, **não prova de incompatibilidade instalada**. A distribuição oficial CurseForge 2.2.3 não lista relações required na página de dependencies consultada; **não deduzir que todas as dependências carregadas são opcionais sem a metadata do JAR 2.2.3**.

**Boundary para o RPG Skill Tree e especializações:** nenhuma habilidade/perk pode ser vinculada a um boss ou golpe por nome de animação (por exemplo, `soulstrike`) ou VFX client-side. Condições de dano, cooldown, knockback, status, source de magia e kill credit exigem contratos reais de eventos/classes do binário em uso, mais contexto server-side, causalidade idempotente e version pins. **Não inventar APIs de 2.2.2/2.2.3** a partir do source público 2.1.15.

### 14.3. Gate de atualização e rollback — NÃO EXECUTADO

- [ ] Reabrir JAR físico de outubro, comparar SHA-1/SHA-256, `META-INF/neoforge.mods.toml` e versão interna `1.21.1` contra snapshot. Obter/hash do file 8902342 antes de afirmar equivalência byte-a-byte de 2.2.3 e fonte pública.
- [ ] Comparar os dois artefatos oficiais 2.2.2 vs 2.2.3 por manifesto, JAR entries, classes, assets de animation/sprite, recipes, loot tables, estruturas, tags, mixins e registries; não inferir o escopo real do changelog genérico.
- [ ] Client/dedicated server cold boot com Create 6.0.10, Legendary Spellbooks 0.3.2 e render stack real; validar classloading, crashes e inicialização de registries.
- [ ] Testar render do **Possessed Paladin Soulstrike** em cliente normal e com Iris/Sodium, sem alterar dano/temporização/desync do servidor.
- [ ] Reproduzir cenário de **Create render issue** identificado pelo autor se houver informação suficiente; validar contraptions/mobs/structures afetadas sem supor correção universal de render.
- [ ] Em mundo duplicado/teste, gerar estruturas novas, verificar suspicious sand/gravel, loot/archaeology, boss triggers, checkpoints, BlockEntities e persistência após unload/reload; proteger saves de migrações não reversíveis.
- [ ] Verificar Legendary Spellbooks 0.3.2 com entidade IDs, loot, summons, effects e spell hooks contra 2.2.3, mantendo server authority; em multiplayer, loot e kill credit exatamente uma vez.
- [ ] Somente promover a release após regressões, backup de mundo/config, verificação de JAR e rollback documentado; **atualizar depois o filename e a versão dos catálogos** quando a mudança física estiver realmente confirmada.

**Decisão:** preservar a propriedade histórica **Sem decisão**; registrar **2.2.3 disponível, mas não instalado**. Nenhum mod JAR, script, datapack, shader, resource pack, mundo, config ou `modlist.md` foi alterado nesta reauditoria, e nenhum teste runtime foi declarado aprovado.

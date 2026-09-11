# ISS: Magic From The East

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e282ffd5294f5e0fde
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR e Iron's Spells físico atual confirmados
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** ISS: Magic From The East
- **Arquivo JAR:** `iss_magicfromtheeast-1.1.5.jar`
- **Versão 1.21.1:** 1.1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG, Worldgen, Exploração
- **Função:** Addon amplo de Iron's Spells com duas escolas ativas — Symmetry e Spirit —, 22 spells exatos, equipamentos/artefatos/encantamentos, efeitos próprios, Jiangshi, Jadestone e worldgen/progressão inspirados em tradições asiáticas e do Oriente Médio.
- **Dependências:** Iron's Spells 'n Spellbooks é requisito oficial. Source 1.1.5 foi desenvolvido contra Iron's 1.21.1-3.16.2; pack físico usa 3.16.3. Demais infrastructure requirements seguem o ecossistema do provider; compatibilidade prática 3.16.3 exige smoke test.
- **Sobreposição:** Estende Iron's; não substitui o core. Contratos próprios incluem reflection, healing inversion, summons, linked soul damage/Soulburn, cloud ride, teleport-linked effects e projectile splitting. Bridges externas devem ser provider-native first e evitar duplicar esses settlements.
- **Compatibilidade/Riscos:** Addon source-pinned, mas com drift de Iron's 3.16.2→3.16.3 e divergências estáticas já auditadas em Anchoring Kunai, Phantom Charge, Cloud Ride e Soul Catalyst. Riscos: damage/heal forwarding duplicado, reflection recursion, summon lifecycle, teleport anchor, iFrame reset, projectile split e tooltip/runtime mismatch.
- **Observações:** Registry exato: Symmetry 11 — Sword Dance, Bagua Array Circle, Dragon Glide, Jade Judgement, Jiangshi Invoke, Underworld Aid, Punishing Heaven, Drapes of Reflection, Cloud Ride, Nephrite Slash, Jade Bullet; Spirit 11 — Soul Catalyst, Soul Burst, Spirit Challenging, Bone Hands, Calamity Cut, Kitsune Pack, Revenant of Honor, Ashigaru Squad, Phantom Charge, Anchoring Kunai, Splitting Shuriken. Launch/Qigong Controlling comentados; Dune sem registrations ativas. Naming drift upstream: changelog 1.1.5 chama o novo Symmetry spell de “Nephrite Splash”, enquanto o source pin registra `NEPHRITE_SLASH_SPELL` / `NephriteSlashSpell`; tratado como o mesmo spell, não duas registrations.
- **Procedência:** modlist.txt física atual + source oficial WarPhan78/ISS_MagicFromTheEast-1.21.x@13208302c9fdf5beb171a328558cbef07a25ba46 + Modrinth oficial release 1.1.5 NeoForge 1.21.1 + changelog exato 1.1.5 + registry `MFTESpellRegistries.java`.
- **Fonte:** https://github.com/WarPhan78/ISS_MagicFromTheEast-1.21.x
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — ISS: Magic From The East 1.1.5 source-pinned ao commit 1320830…; registry exato 22 spells (Symmetry 11 + Spirit 11), equipment/effects/Jiangshi/Jadestone/worldgen/config, static QA divergences, Iron's 3.16.2→3.16.3 drift, lifecycle/multiplayer, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `iss_magicfromtheeast-1.1.5.jar`, mod id `iss_magicfromtheeast`, versão `1.1.5`. Source pin canônico desta ficha: `WarPhan78/ISS_MagicFromTheEast-1.21.x@13208302c9fdf5beb171a328558cbef07a25ba46`. O registry ativo possui **22 spells exatos: Symmetry 11 + Spirit 11**.

## 1. Papel e authority
Magic From The East é um addon de Iron's Spells que registra schools/spells e conteúdo próprio de equipamento, efeitos, mob e worldgen. Iron's continua authority de mana/cast/cooldown e da infraestrutura escolar; MFTE controla seus próprios spell IDs, efeitos, summons, items e world content.

## 2. Version matrix
O source 1.1.5 declara dependência de Iron's Spells 1.21.1-3.16.2. O pack usa Iron's 3.16.3. A diferença não é tratada como incompatibilidade automática, mas exige QA porque APIs/cast data podem mudar entre patch versions.

## 3. Registry Symmetry — 11/11
Registrations ativas: **Sword Dance, Bagua Array Circle, Dragon Glide, Jade Judgement, Jiangshi Invoke, Underworld Aid, Punishing Heaven, Drapes of Reflection, Cloud Ride, Nephrite Slash e Jade Bullet**. `Launch` e `Qigong Controlling` permanecem comentados no source e não fazem parte da build ativa.

## 4. Registry Spirit — 11/11
Registrations ativas: **Soul Catalyst, Soul Burst, Spirit Challenging, Bone Hands, Calamity Cut, Kitsune Pack, Revenant of Honor, Ashigaru Squad, Phantom Charge, Anchoring Kunai e Splitting Shuriken**. A seção Dune não possui registrations ativas no registry pinado.

## 5. Release 1.1.5 — spells
A release adiciona Jade Bullet e chama o segundo novo Symmetry spell de **Nephrite Splash**, além de Anchoring Kunai/Splitting Shuriken como novos Spirit; porém o source pin registra o spell ativo como **Nephrite Slash** (`NEPHRITE_SLASH_SPELL` / `NephriteSlashSpell`). Tratar isso como **naming drift upstream**, não como dois spells distintos. A release também reworka Bagua Array Circle, Jiangshi Invoke, Spirit Challenging e Cloud Ride e remove Qigong Controlling/Launch. O registry source pin é a authority final para o conjunto realmente ativo.

## 6. Equipamentos e artefatos
O changelog 1.1.5 adiciona **Spirit Crusher**, Elemental Commander Robes e Boots of Mist; reworka/refunciona Soul Breaker, Soulpiercer, Repeating Crossbow, Jade Pendant e Rusted Coin Sword; adiciona os encantamentos Inner Impact e Expanding e o Soulward Ring, que protege contra soul damage. Stats numéricos não são inventados sem tabela/source específico.

## 7. Jadestone e worldgen
A release renomeia Jade para **Jadestone**, muda obtenção/mineração e adiciona bloco de construção associado. Jadestone Ore foi ajustado para geração própria em jungles e mountains e para não dominar outros ores. Mudanças são relevantes para worldgen, tags/recipes e progressão em mundos existentes.

## 8. Jiangshi
A 1.1.5 adiciona Jiangshi ao mundo como undead hostil raro de spawn noturno que procura jogadores e villagers. Spawn rules e AI pertencem ao addon; quests não devem creditar encounter/kill por simples proximidade ou VFX.

## 9. Efeitos próprios
A release adiciona **Reversal Healing** e **Anchored Soul**. O source auditado também possui mecânicas de Soulburn, linked proxy soul damage e teleport-linked behavior. Damage/healing inversion e forwarding são áreas de alto risco para double processing com outros mods RPG.

## 10. Reflection, summons e movement
O catálogo source-pinned inclui reflection shield, summon formations, Cloud Ride, teleport anchors e hit-triggered projectile splitting. Esses sistemas precisam preservar caster/owner, friendly-fire, lifecycle e attribution em multiplayer; animação não é authority.

## 11. Configuração
A 1.1.5 adiciona configuração ao mod. Sem leitura do arquivo local efetivamente usado no pack, nenhum valor é afirmado como ativo. Balance de spawn, damage ou outras opções deve ser lido do runtime/config antes de criar integração rígida.

## 12. Static QA — Anchoring Kunai
A auditoria do source já registrou divergência entre texto/tooltip anunciado de linked damage em faixa percentual e o valor armazenado no event path, aparentemente em escala diferente. Isso é **observação estática**, não bug runtime confirmado; permanecer fail-closed até teste controlado.

## 13. Static QA — Phantom Charge
Foi observado que o tooltip usa metade do spell power enquanto cavalry entities recebem full spell power no path auditado. Novamente, tratar como divergência source/UI a validar, não como resultado de gameplay já medido.

## 14. Static QA — Cloud Ride e Soul Catalyst
`Cloud Ride` ignora o `Vec3` retornado por `spawn.add(...)` no trecho auditado; `Soul Catalyst` chama `super.onCast` dentro do loop de 2–6 projectiles. Ambos são regression gates para comportamento/multiplicidade, sem afirmar efeito final antes de runtime QA.

## 15. Client / server
Cast, damage, healing, summon state, mob spawn, loot e worldgen são server-authoritative. Cliente renderiza animations, particles, sounds, models e heart UI. Um callback visual não deve disparar progresso, damage ou summon adicional.

## 16. Lifecycle e multiplayer
Validar spell cast/cancel, summon spawn/despawn, Cloud Ride mount/dismount, teleport, projectile hit, death/respawn, Jiangshi spawn, chunk unload, config restart/reload e dimension transfer. Linked soul effects e temporary entities não podem sobreviver a owner invalidation de forma indevida.

## 17. Riscos técnicos
- Iron's 3.16.2 source expectation contra core 3.16.3 físico;
- reflection recursiva;
- damage/healing forwarding duplicado;
- linked soul damage/Soulburn aplicado em duplicidade;
- summon ownership/friendly-fire incorreto;
- Cloud Ride movement/fall-damage regressão;
- projectile split/multi-cast duplicado;
- teleport anchor stale;
- tooltip/runtime divergence;
- Jadestone worldgen/data drift em mundo existente.

## 18. Matriz de testes obrigatória
- [ ] Dedicated server + cliente iniciam com MFTE 1.1.5 e Iron's 3.16.3.
- [ ] Registry contém exatamente 22 spells MFTE ativos e nenhum Launch/Qigong/Dune registration inesperado.
- [ ] Symmetry/Spirit cast usam mana/cooldown uma única vez pelo core Iron's.
- [ ] Drapes/reflection não entra em recursive damage loop.
- [ ] Reversal Healing e soul damage compõem com stack RPG sem double forwarding.
- [ ] Anchoring Kunai valida proporção real vs tooltip.
- [ ] Phantom Charge valida spell power real vs tooltip.
- [ ] Cloud Ride não gera movement/fall-damage inconsistente.
- [ ] Soul Catalyst produz quantidade/settlement coerente sem multi-super side effect.
- [ ] Summons preservam owner em relog/dimension changes.
- [ ] Jiangshi spawn e Jadestone ore generation são amostrados em chunks novos.
- [ ] Config local é lida antes de qualquer balance integration.

## 19. Evidências e limites
- **Modlist física:** JAR/mod id/version.
- **Source exato:** commit `13208302…`, registry 22/22 e paths estáticos auditados.
- **Modrinth oficial 1.1.5:** changelog de spells, equipment, effects, Jiangshi, Jadestone, config e fixes.
- **Limite:** static QA não equivale a runtime failure; discrepâncias acima permanecem gates de teste.
- **Runtime:** nenhum teste da matriz foi executado nesta catalogação.

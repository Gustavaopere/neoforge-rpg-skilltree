# A0103 — Proteção Ambiental

**Estado Chat 1:** DESIGN APROVADO após fechamento do allowlist.  
**Runtime atual:** consumer/tag ainda ausentes; availability deve permanecer fail-closed até implementação do Chat 2.  
**Notion:** https://app.notion.com/p/3c569db9f0db811fbc81dc88b3784913

## Identidade e posição

- Domínio: `VITALITY`.
- Árvore: Principal — VITALITY ↔ SURVIVAL.
- Ramo: Mitigação por Tipo — Ambiental.
- Camada: 2; função: Ponte.
- Ranks: 4; custo 1 PP/rank.
- Pré-requisitos: A0088 Constituição ≥2 + acesso real ao corredor SURVIVAL + Gateway VITALITY.

## Contrato congelado

Cada rank concede **+2% de redução**, até **+8%**, somente para fontes externas ambientais **não elementais** explicitamente allowlisted. Uma contribuição A0103 por `DamageContainer`/root.

### Allowlist vanilla inicial canônico

- `minecraft:cactus`
- `minecraft:sweet_berry_bush`
- `minecraft:stalagmite`
- `minecraft:falling_block`
- `minecraft:falling_anvil`
- `minecraft:falling_stalactite`
- `minecraft:fly_into_wall`

### Exclusões explícitas

`minecraft:fall`, `minecraft:cramming`, `minecraft:in_wall`, `minecraft:drown`, `minecraft:starve`, `minecraft:freeze`, fogo/calor/lava, lightning, Void/kill/world-border, explosões, resource costs e qualquer fisiologia/proteção própria ficam fora.

Dano ambiental também explicitamente físico pode compor multiplicativamente com A0092 quando **ambas** as classificações forem verdadeiras; não há exclusão artificial entre categorias distintas.

## Provider, hook e authority

Owner: Minecraft/NeoForge `21.1.248` + RPG Skill Tree. Consumer em `LivingDamageEvent.Pre`, usando tag data-driven `rpgskilltree:environmental` ou classifier equivalente fiel ao allowlist.

Mods só entram por adapter versionado que mapeie um `DamageType` específico. Ausência de atacante, namespace, posição no mundo ou tema visual não são classifier.

Volcanoes, Enshrouded, Cold Sweat, Thirst e outros sistemas ambientais mantêm suas próprias authorities; pressão, gás, temperatura, Shroud/Exposure, hidratação e afins não são reclassificados genericamente.

## Causalidade, deduplicação e anti-abuso

- uma aplicação por DamageContainer/root;
- tag/adapter explícito é a única autorização;
- fonte desconhecida falha fechado;
- um adapter modded não pode apagar a semântica nativa do provider;
- não inferir `environmental` por `source.getEntity()==null`;
- não transformar dano ambiental em autoria ofensiva/Mastery/sustain.

## Availability e fail-closed

Sem tag/classifier + consumer, A0103 é indisponível/não comprável. Purchase falha antes de gastar PP. Fonte fora do allowlist permanece inelegível, mesmo se parecer ambiental.

## Projetos próprios / cobertura provider → árvore

- Volcanoes: pressão/gás/lava/calor/geologia permanecem provider-owned e não entram no allowlist atual.
- Enshrouded: Shroud/Exposure/Madness permanecem próprios; Stage 07.03 client audio/particles é irrelevante para gameplay authority.
- Black Arcana: hazards arcanos/Backlash permanecem fora.
- RPG Skill Tree: `ProgressionService` governa gateway/PP; DamageMitigationResolver governa a única contribuição da perk.

## Nove eixos / critérios de aprovação

1. Dependências/Gates — PASS.
2. Integração global — PASS, classifier data-driven + resolver único.
3. Qualidade/identidade — PASS após enumerar o allowlist; não duplica temperatura/elementos/fisiologia.
4. Topologia — PASS, ponte VITALITY↔SURVIVAL.
5. Especializações — PASS/N/A.
6. PT-BR — PASS.
7. Notion — PASS após correção e re-fetch persistido em 2026-08-31.
8. NeoVitae — N/A/ausente.
9. Cobertura providers — PASS; projetos ambientais próprios receberam disposição explícita.

Causalidade, dedup, anti-abuso, fallback, authority, versões, purchase fail-closed e composição estão congelados.

## Pendências para Chat 2

- `P-A0103-01`: materializar exatamente o allowlist acima em tag/classifier data-driven e consumer once/root.
- `P-A0103-02`: availability/purchase fail-closed enquanto o consumer não estiver operacional.
- `P-A0103-03`: testes positivos para os sete IDs e negativos para todas as classes excluídas, incluindo hazards dos projetos próprios.
- `P-A0103-04`: provar composição/dedup com A0092 sem criar cap defensivo oculto.

## Testes exigidos ao Chat 3

Validar allowlist exato, exclusões, unknown modded fail-closed, nenhuma inferência por ausência de atacante, provider boundaries Volcanoes/Enshrouded/Black Arcana, one-application/root, rank/respec/reload, multiplayer, GameTests, build, JAR e dedicated-server smoke.

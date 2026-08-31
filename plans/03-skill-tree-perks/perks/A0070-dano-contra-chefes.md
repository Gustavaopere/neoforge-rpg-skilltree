# A0070 — Dano contra Chefes

## Estado de design

**APROVADA COM BOUNDARY E COBERTURA DE PROVIDERS PENDENTE.** O efeito é válido; a taxonomia de bosses precisa ser explícita e completa para a modlist.

## Contrato final

- **Ranks:** 5; **custo:** 1/rank.
- **Dependência:** A0061 ≥ 1 rank + gateway MARTIAL.
- **Efeito:** +3% de dano físico elegível por rank, máximo +15%, quando `MartialTargetClassifier` classifica o alvo como `BOSS` antes do impacto.
- Boss e Elite são mutuamente exclusivos para esta resolução; A0070 não acumula A0071 no mesmo alvo/root.
- Nunca classificar por nome exibido, boss bar visual, vida máxima, tamanho, modelo ou namespace sozinho.

## Authority de classificação

- `rpgskilltree:bosses` é o catálogo datapack explícito para EntityTypes verificados.
- Marcadores Apothic/Apotheosis já suportados pelo classifier permanecem uma rota provider-owned versionada.
- Vanilla Wither/Ender Dragon e bosses Cataclysm atualmente listados no tag são cobertura comprovada.

## Delta Enshrouded

O delta fresco do provider adicionou um boss nativo real: `enshrouded:shroud_lich` (`NativeShroudLichEntity`). O ID foi verificado no código do provider e **deve ser incluído explicitamente na taxonomia A0070** como entrada opcional do tag/catálogo. A existência da entidade não autoriza tratar qualquer mob Enshrouded como boss.

## Outros providers de boss da modlist

Born in Chaos, Legendary Monsters, Mowzie's Mobs, Ice And Fire, Companions e Mobstein documentam bosses/minibosses, mas IDs individuais não devem ser inventados. `P-A0070-02` exige inventário provider-present/versionado para expandir a taxonomia. Witherstein/Mobstein preserva o blocker histórico de identidade até registro exato comprovado.

## Simply Swords

Traits/abilities Cataclysm de armas não classificam o alvo como boss e não recebem A0070 como derived root. Somente o root MARTIAL direto contra EntityType classificado recebe a parcela RPG.

## Pendências

- `P-A0070-01`: adicionar `enshrouded:shroud_lich` à classificação canônica de boss e testar presença/ausência do provider.
- `P-A0070-02`: matriz provider-present dos bosses relevantes da modlist; cada ID/tag deve ter evidência da versão instalada. Desconhecido = HOSTILE/fail-closed para A0070, nunca heuristic boss.
- Herda `P-A0061-01` para classificação da ação melee.

## Testes obrigatórios

1. vanilla + Cataclysm tag + Apothic markers qualificam uma vez.
2. `enshrouded:shroud_lich` qualifica quando provider presente e datapack resolvido.
3. boss não recebe A0071 Elite no mesmo root.
4. mob com muita vida/nome custom/boss bar não qualifica sem identidade canônica.
5. derived/companion/hazard = neutro.

## Nove eixos

1. Gates: PASS — A0061≥1.
2. Integração: PASS no design; taxonomia provider-present deve ser fechada.
3. Qualidade: PASS — escolha anti-boss/endgame.
4. Topologia: PASS — ramo contextual de Força.
5. Especialização: PASS — universal MARTIAL anti-boss.
6. PT-BR: PASS.
7. Registro: GitHub.
8. NeoVitae: PASS.
9. Providers/modlist: PARCIAL operacionalmente por `P-A0070-01/-02`; design e fail-closed fechados.
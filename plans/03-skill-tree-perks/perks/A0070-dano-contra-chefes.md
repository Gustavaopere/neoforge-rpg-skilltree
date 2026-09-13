# A0070 — Dano contra Chefes

## Estado

- **Design:** APROVADO após correção de cobertura/provider em 2026-08-31.
- **Notion:** `3c569db9-f0db-81ce-8539-c9fda312469b`; Provider/Mods, Hook, Fallback e Regra corrigidos; re-fetch pós-escrita PASS.
- **Runtime observado:** IMPLEMENTAÇÃO CONFIRMADA para vanilla/Cataclysm/Apothic e para a identidade exata opcional `enshrouded:shroud_lich`; demais candidatos permanecem fail-closed.

## Contrato canônico

- Gateway MARTIAL + A0061 Força Aplicada ≥ 1 rank.
- 5 ranks, 1 ponto por rank.
- +3% de dano físico direto elegível contra BOSS por rank, máximo próprio +15%.
- Uma única classificação canônica por alvo/root, precedência obrigatória **BOSS > ELITE > HOSTILE**.
- A0070 e A0071 não podem aplicar simultaneamente ao mesmo evento por classificações secundárias.
- O bônus nunca ignora imunidade, fase, coeficiente defensivo ou script do boss.

## Provider / authority / boundary

### Cobertura comprovada

- Vanilla: `minecraft:ender_dragon`, `minecraft:wither`.
- L_Ender's Cataclysm 3.33: somente identities explicitamente presentes na tag `rpgskilltree:bosses` do runtime (`netherite_monstrosity`, `ender_guardian`, `the_harbinger`, `ancient_remnant`, `the_leviathan`, `scylla`, `maledictus`, `ignis`).
- Apothic: markers canônicos reconhecidos pelo `MartialTargetClassifier`; ELITE continua separado de BOSS.
- Enshrouded: identidade registry nativa comprovada `enshrouded:shroud_lich`, materializada na tag opcional `rpgskilltree:bosses` com `required: false` pela PR #391.

### Cobertura ainda não promovida

Mowzie's Mobs 1.8.2, Legendary Monsters 2.2.2, Born in Chaos 1.7.6 e Mobstein 5.4.4 permanecem candidatos. Sem registry ID/adapter exato verificado, ficam **FAIL-CLOSED**. Bossbar, nome, tamanho, max health, equipamento, estrutura/origem e aparência não são classificadores.

## Boundary Enshrouded

A0070 apenas lê a identidade nativa `enshrouded:shroud_lich` por tag opcional. Enshrouded permanece authority exclusiva de manifestação, arena, fase, Exposure, death marker, Story, Lich Skull, reward issuance e ritual. A skill tree não lê bossbar/fase para provar BOSS e não grava nenhum estado do Enshrouded.

## Evidência runtime

`MartialTargetClassifier` classifica a tag `rpgskilltree:bosses`, marker Apothic de boss e marker/ID Apothic de elite. A PR #391 acrescenta `enshrouded:shroud_lich` a `data/rpgskilltree/tags/entity_type/bosses.json` com `required: false`, preservando compatibilidade quando o provider não estiver instalado.

## Fallback e fail-closed

Sem tag, registry ID ou adapter confiável, classificar como BOSS é proibido. A contribuição A0070 fica zero; nunca promover boss por heurística visual/estatística.

## Anti-abuso, causalidade e deduplicação

- BOSS domina ELITE para impedir double-dipping A0070+A0071.
- Somente dano físico direto causalmente atribuído ao jogador.
- Summons, companions, fake players, hazards, reflexão e procs derivados não herdam A0070.
- Não gera Mastery ou reward de boss.

## Validação Chat 3

- `P-A0070-01`: RESOLVIDA na PR #391 pela tag opcional exata `enshrouded:shroud_lich`; nenhuma bossbar/fase/Story é usada como heurística.
- `P-A0070-02`: permanece política fail-closed para Mowzie/Legendary Monsters/Born in Chaos/Mobstein até registry IDs/adapters exatos serem tecnicamente comprovados; não é blocker do binding já implementado.
- `P-A0070-03`: RESOLVIDA por `A0061A0070Chat3CoverageJUnitTest.bossTakesPrecedenceOverEliteAndNeverDoubleStacks`, que fixa BOSS > ELITE e multiplicador único de A0070.
- `P-A0070-04`: preservada por arquitetura read-only/tag; nenhuma fase, imunidade, reward ou lifecycle provider-native é alterado pela skill tree.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | A0061 ≥1 + classificação BOSS canônica. |
| 2. Integração global | PASS | Não contorna boss lifecycle, Story, rewards ou hazards. |
| 3. Qualidade/identidade | PASS | Especialização clara contra alvo prioritário. |
| 4. Topologia | PASS | Camada 2, `MARTIAL/PRIORITY_TARGETS`. |
| 5. Especializações | PASS | Universal MARTIAL; provider do boss mantém authority. |
| 6. PT-BR | PASS | Nome/efeito/requisitos em PT-BR. |
| 7. Notion completo | PASS após correção | Re-fetch confirmou persistência. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS com fail-closed explícito | Vanilla/Cataclysm/Apothic/Enshrouded comprovados; demais candidatos não são promovidos sem ID real. |

Os 18 critérios técnicos cumulativos passam no estado implementado/fail-closed validado pelo Chat 3.
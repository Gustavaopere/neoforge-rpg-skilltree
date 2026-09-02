# A0070 — Dano contra Chefes

## Estado

- **Design:** APROVADO após correção de cobertura/provider em 2026-08-31.
- **Notion:** `3c569db9-f0db-81ce-8539-c9fda312469b`; Provider/Mods, Hook, Fallback e Regra corrigidos; re-fetch pós-escrita PASS.
- **Runtime observado:** IMPLEMENTAÇÃO PARCIAL: classificador canônico cobre tag `rpgskilltree:bosses` + markers Apothic; bridge exata do Shroud Lich ainda precisa ser materializada pelo Chat 2.

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
- Enshrouded `main@391ea82203d30cb392a3397f92e2a3cbe7fb6128`: identidade registry nativa comprovada `enshrouded:shroud_lich`.

### Cobertura ainda não promovida

Mowzie's Mobs 1.8.2, Legendary Monsters 2.2.2, Born in Chaos 1.7.6 e Mobstein 5.4.4 permanecem candidatos. Sem registry ID/adapter exato verificado, ficam **FAIL-CLOSED**. Bossbar, nome, tamanho, max health, equipamento, estrutura/origem e aparência não são classificadores.

## Boundary Enshrouded

A0070 pode apenas ler a identidade nativa `enshrouded:shroud_lich`. Enshrouded permanece authority exclusiva de manifestação, arena, fase, Exposure, death marker, Story, Lich Skull, reward issuance e ritual. A skill tree não lê bossbar/fase para provar BOSS e não grava nenhum estado do Enshrouded.

## Evidência runtime

`MartialTargetClassifier` hoje classifica a tag `rpgskilltree:bosses`, marker Apothic de boss e marker/ID Apothic de elite. `bosses.json` atualmente contém vanilla + oito Cataclysm. O código Enshrouded atual registra `enshrouded:shroud_lich`; portanto existe identidade estável para um adapter read-only, mas ela ainda não está no classificador do RPG.

## Fallback e fail-closed

Sem tag, registry ID ou adapter confiável, classificar como BOSS é proibido. A contribuição A0070 fica zero; nunca promover boss por heurística visual/estatística.

## Anti-abuso, causalidade e deduplicação

- BOSS domina ELITE para impedir double-dipping A0070+A0071.
- Somente dano físico direto causalmente atribuído ao jogador.
- Summons, companions, fake players, hazards, reflexão e procs derivados não herdam A0070.
- Não gera Mastery ou reward de boss.

## Pendências para Chat 2

- **P-A0070-01:** adicionar adapter/tag explícita para `enshrouded:shroud_lich`, read-only, sem dependência de bossbar/fase/Story.
- **P-A0070-02:** manter Mowzie/Legendary Monsters/Born in Chaos/Mobstein fail-closed até registry IDs/adapters exatos serem tecnicamente verificados; não adivinhar Witherstein por nome.
- **P-A0070-03:** GameTest deve provar BOSS > ELITE e ausência de A0070+A0071 no mesmo root.
- **P-A0070-04:** regression deve provar que fases/imunidades do Shroud Lich/Cataclysm continuam provider-native.

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

Os 18 critérios técnicos cumulativos passam **no design**. A cobertura runtime de Enshrouded e demais adapters continua responsabilidade de implementação do Chat 2.

## Atualização de implementação — Chat 2 (2026-09-02)

- **Estado técnico:** **CÓDIGO PRESENTE COM COBERTURA PARCIAL FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **PR/branch:** #391 / `feat/chat2-a0061-a0070-stacked-handoff`.
- **P-A0070-01 RESOLVIDA EM CÓDIGO:** `src/main/resources/data/rpgskilltree/tags/entity_type/bosses.json` inclui agora a entrada opcional exata `enshrouded:shroud_lich`. A integração continua somente read-only por registry identity.
- **P-A0070-02 preservada em fail-closed:** Mowzie's Mobs, Legendary Monsters, Born in Chaos e Mobstein não são promovidos sem registry ID/adapter exato.
- Nenhuma heurística por bossbar, nome, tamanho, max health, estrutura ou aparência foi criada; Story/fases/rewards/imunidades continuam provider-native.
- As frases históricas acima dizendo que a bridge Enshrouded ainda faltava estão superadas por esta atualização. A confirmação final é exclusiva do Chat 3.

### Checklist de implementação
- [x] Design aprovado pelo Chat 1
- [x] Vanilla/Cataclysm/Apothic preservados
- [x] `enshrouded:shroud_lich` materializado por identity exata
- [x] Demais providers sem identidade provada permanecem fail-closed
- [x] Boundary Enshrouded read-only preservado
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** BOSS > ELITE > HOSTILE e ausência de A0070+A0071 no mesmo root
- [ ] **VALIDAÇÃO CHAT 3:** fases/imunidades/Story/rewards permanecem provider-native
- [ ] **VALIDAÇÃO CHAT 3:** testes/GameTests/build/smoke/CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

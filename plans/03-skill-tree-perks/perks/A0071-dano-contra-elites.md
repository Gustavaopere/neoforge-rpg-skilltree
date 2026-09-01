# A0071 — Dano contra Elites

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-81e6-917f-fc78417b1a08`; fetch fresco em 2026-08-31 sem drift funcional.
- **Runtime observado:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. O classifier e a precedência BOSS > ELITE foram preservados; o Chat 2 não introduziu heurísticas adicionais de elite.

## Contrato canônico

- Gateway MARTIAL + A0061 Força Aplicada ≥ 1 rank.
- 5 ranks, 1 ponto por rank.
- +3% de dano físico direto elegível contra ELITE por rank, máximo próprio +15%.
- Precedência única **BOSS > ELITE > HOSTILE**; A0070 e A0071 nunca acumulam no mesmo root.

## Provider / authority / boundary

- RPG Skill Tree possui a classificação canônica consumida pela perk.
- Apothic/Apotheosis alimenta ELITE somente por markers/identidade explícitos reconhecidos (`apoth.miniboss` ou elite key capturada).
- Outros providers só entram mediante mapping/tag/adapter exato comprovado; vida, tamanho, nome, equipamento, bossbar ou aparência não bastam.

## Evidência runtime

`MartialTargetClassifier` classifica primeiro a tag BOSS, depois `apoth.boss`, depois elite key/`apoth.miniboss`. `A0061A0080CombatPolicy.beforePhysicalHit(...)` usa `if boss ... else if elite`, preservando precedência e impedindo double-dipping.

## Fallback e anti-abuso

Sem identidade confiável de ELITE, contribuição A0071 = zero. Somente dano físico direto causalmente atribuído ao jogador; summons, fake players, companions, hazards, reflexão, DoT e procs derivados são inelegíveis.

## Implementação Chat 2 — 2026-09-01

- O core/classifier existente foi mantido como implementação canônica; nenhuma classificação por aparência/vida/tamanho foi adicionada.
- O novo `A0061A0080RuntimeState` invalida estado transitório quando ranks efetivos mudam, evitando resíduos após respec/reconcile.
- Nenhum provider adicional foi promovido sem identidade auditada.

## Pendências para Chat 3

- Validar provider-present/provider-absent para Apothic elite e a precedência BOSS > ELITE.
- Validar ausência de double-dipping por root e exclusão de summons/fake players/procs.
- Unit tests/GameTests/build/dedicated-server smoke/CI permanecem **não executados pelo Chat 2**.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | A0061 ≥1 + classificação ELITE canônica. |
| Integração global | PASS | Não cria estado paralelo de elite. |
| Qualidade/identidade | PASS | Especialização distinta de boss. |
| Topologia | PASS | Camada 2, `MARTIAL/PRIORITY_TARGETS`. |
| Especializações | PASS | Universal MARTIAL; provider mantém authority. |
| PT-BR | PASS | Conteúdo player-facing em PT-BR. |
| Notion | PASS | Fetch fresco confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | Apothic real; demais fail-closed sem identidade comprovada. |

Os 18 critérios técnicos cumulativos passam **no design**. A confirmação de implementação pertence ao Chat 3.
# AUDITORIA CHAT 2 — IMPLEMENTAÇÃO A0061–A0070

## Registro do lote

- **INÍCIO:** A0061
- **FIM:** A0070
- **Quantidade:** 10 perks consecutivas
- **Branch:** `feat/chat2-a0061-a0070-stacked-handoff`
- **PR:** #391
- **Minecraft:** NeoForge 1.21.1
- **Java:** 21
- **Responsabilidade:** Chat 2 — implementação do contrato já aprovado pelo Chat 1; sem redesign, sem bateria final de testes, sem `IMPLEMENTAÇÃO CONFIRMADA` e sem merge.
- **Estado de handoff:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

## Base de design

O contrato canônico permanece `audits/AUDITORIA-A0061-A0070.md` e os dez dossiês A0061–A0070 fechados pelo Chat 1 na PR #298. Nenhum pipeline paralelo foi criado para substituir runtime já existente.

## Estado real por perk

| Código | Estado Chat 2 | Evidência / decisão | Handoff Chat 3 |
|---|---|---|---|
| A0061 | CÓDIGO PRESENTE | pipeline físico canônico já presente para melee Epic Fight + projectile físico; preservado sem duplicação de provider-native Simply Swords | validar uma contribuição por root e coexistência de bridges |
| A0062 | CÓDIGO PRESENTE | resolvedor crítico canônico já presente; nenhuma segunda rolagem adicionada | validar uma rolagem/root e convergência Apothic |
| A0063 | CÓDIGO PRESENTE | multiplicador aplica somente sobre `canonicalCritical` | validar uma aplicação/root e ausência de double multiplier |
| A0064 | CÓDIGO PRESENTE | `ModifyAttackSpeedEvent`/boundary provider-native já presente | validar provider-present/absent e movesets sem binding |
| A0065 | CÓDIGO PRESENTE | penetração usa o backend físico já existente; não foi convertida em shred/debuff | validar backend único e Simply Swords armor-ignore provider-native |
| A0066 | CÓDIGO PRESENTE COM COMPONENTE FAIL-CLOSED | Impact melee Epic Fight preservado; projectile continua sem Impact sintético | validar melee e confirmar neutralidade de projectile sem receipt |
| A0067 | CÓDIGO PRESENTE EM FAIL-CLOSED | `CombatPerkAvailabilityRuntime` marca A0067 como `UNAVAILABLE_NODE`; purchase é rejeitado e rank legado indisponível é mascarado para rank efetivo 0 no gameplay | validar no-op purchase impossível, recovery/refund de rank legado e ausência de STUN_ARMOR permanente |
| A0068 | CÓDIGO PRESENTE | condição usa snapshot pré-impacto `< 35%`; melee/projectile reutilizam pipeline canônico | validar borda, não retroatividade e dedup/root |
| A0069 | CÓDIGO PRESENTE | condição usa snapshot pré-impacto `> 85%`; melee/projectile reutilizam pipeline canônico | validar borda, dano anterior e dedup/root |
| A0070 | CÓDIGO PRESENTE COM COBERTURA PARCIAL FAIL-CLOSED | tag canônica `rpgskilltree:bosses` recebeu a identity opcional exata `enshrouded:shroud_lich`; vanilla/Cataclysm/Apothic permanecem cobertos; Mowzie/Legendary/Born in Chaos/Mobstein continuam fail-closed sem IDs/adapters provados | validar `BOSS > ELITE`, ausência de A0070+A0071 no mesmo root e preservação de fases/imunidades provider-native |

## Pendências resolvidas pelo Chat 2

### P-A0067-01 — RESOLVIDA

O invariant de disponibilidade foi materializado no purchase/runtime:

- A0067 está explicitamente em `UNAVAILABLE_NODES`;
- tentativa de aquisição é rejeitada antes da mutação de progresso;
- ranks legados indisponíveis não produzem efeito: o runtime expõe rank efetivo `0` para gameplay, preservando armazenamento somente para recuperação/refund;
- nenhuma heurística de attack-window, STUN_ARMOR permanente ou super armor foi criada.

`P-A0067-02` permanece uma capacidade futura provider-dependent: sem hook seguro, A0067 deve continuar indisponível. Isso não é redesign nem blocker do handoff porque o contrato aprovado é exatamente fail-closed.

### P-A0070-01 — RESOLVIDA

`src/main/resources/data/rpgskilltree/tags/entity_type/bosses.json` passou a incluir, como entrada opcional, `enshrouded:shroud_lich`. A integração é somente leitura de registry identity; a skill tree não lê nem grava Story, fase, arena, Exposure, rewards ou ritual do Enshrouded.

### P-A0070-02 — FAIL-CLOSED PRESERVADO

Mowzie's Mobs, Legendary Monsters, Born in Chaos e Mobstein continuam sem promoção automática. Nome, bossbar, tamanho, max health, equipamento, estrutura ou tema não classificam BOSS.

## Pipelines preservados

- uma ação causal física continua a ter uma única contribuição A0061/A0065/A0066/A0068/A0069/A0070;
- A0062/A0063 permanecem sobre uma única resolução crítica;
- A0066 não fabrica Impact para projectile;
- A0067 não cria atributo substituto;
- A0070 é classificador read-only e não concede reward/Mastery;
- Simply Swords/Simply More mantêm Implicits, Awakening, Runic Powers, sockets/gems e traits provider-native.

## Testes e validações

O Chat 2 **não executou a bateria final de validação deste lote nesta continuação**. O arquivo de regressão focal já presente na PR #391 foi mantido como seam para o Chat 3; nenhum resultado dele é promovido aqui a evidência de fechamento.

Permanecem para o Chat 3, conforme aplicável:

- [ ] testes unitários;
- [ ] GameTests;
- [ ] testes de integração/provider;
- [ ] deduplicação/idempotência;
- [ ] fail-closed/fallback;
- [ ] build NeoForge;
- [ ] dedicated-server smoke;
- [ ] CI GREEN;
- [ ] `IMPLEMENTAÇÃO CONFIRMADA`.

## Retorno ao Chat 1

**Nenhum ponto exige redesign neste handoff.** As limitações de A0067 e da cobertura externa de A0070 já fazem parte do design aprovado e foram implementadas como fail-closed.

## Fechamento do Chat 2

A0061–A0070 estão em **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. A PR #391 deve permanecer aberta para o Chat 3. O Chat 2 não inicia A0071+, não declara `IMPLEMENTAÇÃO CONFIRMADA` e não faz merge.

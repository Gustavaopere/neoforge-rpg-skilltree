# A0068 — Dano contra Feridos

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-819e-bda6-d5649adf8ae4`; fetch fresco em 2026-08-31 sem drift.
- **Runtime observado:** CÓDIGO PRESENTE em melee e projéteis físicos; confirmação definitiva pertence ao Chat 2.

## Contrato canônico

- Gateway MARTIAL + A0061 Força Aplicada ≥ 1 rank.
- 3 ranks, 1 ponto por rank.
- +4% de dano físico direto elegível contra alvo hostil abaixo de 35% da vida máxima por rank, máximo +12%.
- A condição é avaliada server-side **imediatamente antes** do impacto.
- O próprio dano do hit atual não pode retroativamente tornar o alvo elegível.

## Provider / authority / boundary

- Minecraft/NeoForge fornece vida atual/máxima da entidade.
- Epic Fight 21.17.3.1 fornece root action quando aplicável.
- RPG Skill Tree aplica a contribuição no resolvedor físico canônico.
- Não há provider externo de “estado ferido” paralelo.

## Evidência runtime

`A0061A0080CombatPolicy.beforePhysicalHit(...)` adiciona A0068 somente quando `preImpactHealthFraction < 0.35`. O Epic Fight bridge e a ponte de projéteis constroem esse snapshot antes do dano.

## Fallback e fail-closed

A API de vida vanilla é o fallback suficiente para entidades vivas válidas. Alvo inválido/não hostil ou provenance indireta torna a contribuição zero.

## Anti-abuso, causalidade e deduplicação

- Somente dano físico direto do jogador.
- Exclui DOT, ambiente, reflexão, summon, fake player, proc encadeado e callback duplicado.
- Alvo de treino/inválido não qualifica.
- Não gera Mastery.

## Pendências para Chat 2

- **P-A0068-01:** testes devem fixar o snapshot pré-impacto e provar as bordas 35%, inclusive que o próprio golpe não ativa A0068 retroativamente.
- **P-A0068-02:** validar uma aplicação por root em melee/projectile.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | A0061 ≥1 + alvo hostil <35%. |
| 2. Integração global | PASS | Usa vida canônica, sem segundo estado corporal. |
| 3. Qualidade/identidade | PASS | Ramo de execução condicionada. |
| 4. Topologia | PASS | Camada 2, `MARTIAL/EXECUTION`. |
| 5. Especializações | PASS | Universal MARTIAL. |
| 6. PT-BR | PASS | Texto em PT-BR. |
| 7. Notion completo | PASS | Fetch fresco confirmado. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS | Vanilla/Epic Fight/RPG suficientes; nenhum provider inventado. |

Os 18 critérios técnicos cumulativos passam **no design**.

## Atualização de implementação — Chat 2 (2026-09-02)

- **Estado técnico:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **PR/branch:** #391 / `feat/chat2-a0061-a0070-stacked-handoff`.
- O snapshot pré-impacto `< 35%` e os consumers melee/projectile já presentes foram preservados; nenhum estado corporal paralelo foi criado.
- A confirmação final é exclusiva do Chat 3.

### Checklist de implementação
- [x] Design aprovado pelo Chat 1
- [x] Snapshot pré-impacto implementado
- [x] Gate/alvo hostil preservados
- [x] Fallback vanilla/fail-closed preservado
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** borda 35% e não retroatividade
- [ ] **VALIDAÇÃO CHAT 3:** uma aplicação por root melee/projectile
- [ ] **VALIDAÇÃO CHAT 3:** testes/GameTests/build/smoke/CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

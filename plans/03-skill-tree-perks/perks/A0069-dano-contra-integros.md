# A0069 — Dano contra Íntegros

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-8123-9939-c1e0e71a08c0`; fetch fresco em 2026-08-31 sem drift.
- **Runtime observado:** CÓDIGO PRESENTE em melee e projéteis físicos; confirmação definitiva pertence ao Chat 2.

## Contrato canônico

- Gateway MARTIAL + A0061 Força Aplicada ≥ 1 rank.
- 3 ranks, 1 ponto por rank.
- +4% de dano físico direto elegível contra alvo hostil acima de 85% da vida máxima por rank, máximo +12%.
- A condição usa o snapshot server-side imediatamente antes do impacto e recompensa abertura/primeiro contato.
- Dano anterior de terceiros pode remover a elegibilidade; A0069 não “reserva” o estado do alvo.

## Provider / authority / boundary

- Minecraft/NeoForge fornece vida atual/máxima.
- Epic Fight 21.17.3.1 fornece root action quando aplicável.
- RPG Skill Tree aplica uma única contribuição no pipeline físico canônico.

## Evidência runtime

`A0061A0080CombatPolicy.beforePhysicalHit(...)` adiciona A0069 somente quando `preImpactHealthFraction > 0.85`. Melee Epic Fight e projéteis físicos constroem o snapshot antes do impacto.

## Fallback e fail-closed

Entidades vivas válidas usam a API vanilla de vida. Alvo não hostil/inválido, source indireta ou callback sem provenance segura não qualifica.

## Anti-abuso, causalidade e deduplicação

- Somente root action físico direto do jogador.
- Exclui DOT, ambiente, reflexão, summons, fake players e procs derivados.
- Não gera Mastery.

## Pendências para Chat 2

- **P-A0069-01:** testes devem fixar a borda 85% e provar uso do snapshot pré-impacto.
- **P-A0069-02:** validar deduplicação melee/projectile e perda de elegibilidade por dano anterior real.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | A0061 ≥1 + alvo hostil >85%. |
| 2. Integração global | PASS | Usa vida canônica; sem recurso paralelo. |
| 3. Qualidade/identidade | PASS | Ramo de abertura com condição real. |
| 4. Topologia | PASS | Camada 2, `MARTIAL/OPENING`. |
| 5. Especializações | PASS | Universal MARTIAL. |
| 6. PT-BR | PASS | Texto em PT-BR. |
| 7. Notion completo | PASS | Fetch fresco confirmado. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS | Vanilla/Epic Fight/RPG suficientes; sem integração artificial. |

Os 18 critérios técnicos cumulativos passam **no design**.

## Atualização de implementação — Chat 2 (2026-09-02)

- **Estado técnico:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **PR/branch:** #391 / `feat/chat2-a0061-a0070-stacked-handoff`.
- O snapshot pré-impacto `> 85%` e os consumers melee/projectile já presentes foram preservados; dano anterior real continua removendo a elegibilidade.
- A confirmação final é exclusiva do Chat 3.

### Checklist de implementação
- [x] Design aprovado pelo Chat 1
- [x] Snapshot pré-impacto implementado
- [x] Gate/alvo hostil preservados
- [x] Fallback vanilla/fail-closed preservado
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** borda 85% e dano anterior real
- [ ] **VALIDAÇÃO CHAT 3:** uma aplicação por root melee/projectile
- [ ] **VALIDAÇÃO CHAT 3:** testes/GameTests/build/smoke/CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

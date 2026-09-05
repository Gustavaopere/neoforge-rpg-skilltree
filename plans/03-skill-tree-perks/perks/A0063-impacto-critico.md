# A0063 — Impacto Crítico

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-81b2-a257-da4e0d5f274b`; fetch fresco em 2026-08-31 sem drift.
- **Runtime observado:** CÓDIGO PRESENTE sobre crítico canônico; confirmação definitiva pertence ao Chat 2.

## Contrato canônico

- Gateway MARTIAL + A0062 Golpe Preciso ≥ 2 ranks.
- 3 ranks, 1 ponto por rank.
- +5% de dano crítico físico por rank, máximo próprio de +15%.
- Só aplica quando o root action já foi legitimamente classificado como crítico.
- Não aumenta chance de crítico e não reclassifica golpe comum.

## Provider / authority / boundary

- **Authority:** resultado crítico canônico já resolvido.
- Epic Fight 21.17.3.1 pode fornecer o root action/critical state.
- Apothic Attributes 2.10.1 pode ser backend de critical strike damage apenas dentro da mesma resolução; não pode produzir segundo evento.
- O RPG Skill Tree adiciona sua contribuição uma vez após `canonicalCritical=true`.

## Evidência runtime

`A0061A0080CombatPolicy.criticalDamageMultiplier(...)` retorna bônus apenas para crítico canônico. O adapter Epic Fight de famílias físicas multiplica A0063 sobre o resultado crítico já correlacionado, e a ponte de projéteis reutiliza o mesmo estado crítico.

## Fallback e fail-closed

Sem classificação crítica segura, multiplicador A0063 = 1.0. Nunca criar crítico sintético, segundo hit ou cap global inventado para manter o node ativo.

## Anti-abuso, causalidade e deduplicação

- A0063 acompanha o mesmo root de A0062; não abre nova rolagem.
- Dano periódico, reflexão, summons, fake players, hazards e procs derivados são inelegíveis.
- Não gera Mastery.

## Pendências para Chat 2

- **P-A0063-01:** regressão deve provar uma única aplicação do multiplicador crítico em melee e projectile, inclusive quando provider já marcou o golpe crítico.
- **P-A0063-02:** adapters Apothic devem convergir na mesma resolução crítica e nunca duplicar o multiplicador.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | A0062 ≥2 cria progressão coerente. |
| 2. Integração global | PASS | Só toca crítico físico já resolvido. |
| 3. Qualidade/identidade | PASS | Aprofunda crit damage sem duplicar chance. |
| 4. Topologia | PASS | Camada 2, `MARTIAL/CORE_CRIT`. |
| 5. Especializações | PASS | Foundation avançada, não invade escola/provider. |
| 6. PT-BR | PASS | Texto em PT-BR. |
| 7. Notion completo | PASS | Campos completos, fetch fresco. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS | Epic Fight/Apothic/RPG reconciliados com pipeline único. |

Os 18 critérios técnicos cumulativos passam **no design**.

## Atualização de implementação — Chat 2 (2026-09-02)

- **Estado técnico:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **PR/branch:** #391 / `feat/chat2-a0061-a0070-stacked-handoff`.
- O multiplicador continua condicionado ao resultado crítico canônico; nenhum segundo hit, reroll ou multiplicador Apothic paralelo foi introduzido.
- A “confirmação definitiva” citada historicamente acima é reservada ao Chat 3 no protocolo atual.

### Checklist de implementação

- [x] Design aprovado pelo Chat 1
- [x] `canonicalCritical` preservado como gate do efeito
- [x] Gate/dependências preservados
- [x] Provider-native preservado
- [x] Fail-closed sem classificação crítica segura
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** uma aplicação A0063 por root em melee/projectile
- [ ] **VALIDAÇÃO CHAT 3:** provider critical/Apothic sem double multiplier
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/GameTests/integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge / dedicated-server smoke / CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

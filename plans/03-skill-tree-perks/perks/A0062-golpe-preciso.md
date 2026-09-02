# A0062 — Golpe Preciso

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-81b2-a171-c350f1a8f3a2`; fetch fresco em 2026-08-31 sem drift.
- **Runtime observado:** CÓDIGO PRESENTE no resolvedor crítico canônico; confirmação definitiva pertence ao Chat 2.

## Contrato canônico

- Gateway MARTIAL desbloqueado.
- 4 ranks, 1 ponto por rank.
- +2% de chance de crítico físico por rank, máximo próprio de +8%.
- Uma única resolução de crítico por root action. A0062 adiciona chance ao resolver; não realiza segunda rolagem.

## Provider / authority / boundary

- **Authority:** resolução crítica canônica do RPG Skill Tree, preservando o crítico já declarado pelo provider.
- Epic Fight 21.17.3.1 entra pelo adapter do root action.
- Apothic Attributes 2.10.1 é backend possível de critical strike chance quando integrado ao mesmo resolvedor; não pode executar roll paralelo.
- Pufferfish's Attributes 0.8.3 não é provider de crítico para A0062.
- Simply Swords mantém procs/Implicits/Awakening próprios; nenhum deles é reinterpretado como segunda chance A0062.

## Evidência runtime

`A0001A0020EpicFightHooks.onCriticalHit(...)` e o PRE Epic Fight chamam `NotionCombatPerkRules.criticalChanceBonus(...)`, que inclui a contribuição de A0062. O resultado é correlacionado ao root action e reutilizado em vez de rerrolado. A ponte de projéteis usa o mesmo resolver para arco/besta.

## Fallback e fail-closed

Sem estágio de crítico estável para uma família/provider, a contribuição fica inativa naquele caso. É proibido simular crítico como segundo evento de dano ou inventar cap global ausente.

## Anti-abuso, causalidade e deduplicação

- Um root action = uma resolução crítica.
- Provider critical verdadeiro é preservado; A0062 não o reexecuta.
- Dano periódico, summons, fake players, hazards, reflexão e efeitos derivados não recebem roll A0062.
- A0062 não gera Mastery.

## Pendências para Chat 2

- **P-A0062-01:** testes transversais devem provar que CriticalHitEvent e Epic Fight PRE compartilham o mesmo resultado/root sem double-roll.
- **P-A0062-02:** qualquer adapter Apothic futuro deve contribuir para o mesmo resolver, nunca criar pipeline concorrente.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | Gateway MARTIAL é suficiente para foundation crit. |
| 2. Integração global | PASS | Crítico físico permanece separado de magia/hazards. |
| 3. Qualidade/identidade | PASS | Small foundation necessário ao ramo crítico. |
| 4. Topologia | PASS | Camada 1, `MARTIAL/CORE_CRIT`. |
| 5. Especializações | PASS | Universal MARTIAL, sem lock de classe. |
| 6. PT-BR | PASS | Texto visível em PT-BR. |
| 7. Notion completo | PASS | Campos completos e fetch fresco. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS | Epic Fight, Apothic, Pufferfish e Simply Swords classificados pelo papel real. |

Os 18 critérios técnicos cumulativos passam **no design**, condicionados ao pipeline crítico único.

## Atualização de implementação — Chat 2 (2026-09-02)

- **Estado técnico:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **PR/branch:** #391 / `feat/chat2-a0061-a0070-stacked-handoff`.
- O resolvedor crítico canônico existente foi preservado; nenhuma segunda rolagem ou pipeline Apothic concorrente foi adicionado.
- A “confirmação definitiva” citada historicamente acima é reservada ao Chat 3 no protocolo atual.

### Checklist de implementação

- [x] Design aprovado pelo Chat 1
- [x] Resolvedor crítico canônico presente
- [x] Gate/dependências preservados
- [x] Provider-native preservado
- [x] Fail-closed para família sem estágio crítico seguro
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** uma rolagem por root entre CriticalHitEvent/Epic Fight/projectile
- [ ] **VALIDAÇÃO CHAT 3:** convergência Apothic sem roll paralelo
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/GameTests/integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge / dedicated-server smoke / CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

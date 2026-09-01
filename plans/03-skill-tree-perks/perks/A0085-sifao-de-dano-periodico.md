# A0085 — Sifão de Dano Periódico

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability/autoria em 2026-08-31.
- **Notion:** `3c569db9-f0db-8148-9fef-e7b4a708330b`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- A0085 permanece **indisponível/não comprável** porque nenhum provider auditado fornece o conjunto owner + applicationId + pulseId exigido.

## Contrato canônico

- Gateway OCCULT + ao menos uma fonte de DoT reconhecida + adapter seguro.
- 3 ranks: 0,35% / 0,70% / 1,05% do dano periódico pós-mitigação.
- Na aplicação do efeito é registrado um `applicationId`, owner jogador, alvo e source key. Cada tick elegível possui `pulseId` causal e resolve no máximo uma vez no `SustainResolver`.
- Cap global compartilhado: 3% da vida máxima/20 ticks; overkill e missing health continuam limitando pagamento.

## Receipt obrigatório

O adapter por provider deve produzir equivalente a:

`PeriodicSustainReceipt(applicationId, pulseId, ownerPlayerUuid, targetUuid, providerSourceKey, postMitigationDamage)`.

`owner` do summon, proximidade do jogador, efeito aplicado por área, namespace ou último atacante não substituem esse receipt.

## Cobertura de providers

- Goety 3.1.4: sem integração enquanto aplicação direta + pulses não forem correlacionáveis.
- Malum 1.8.2: sem integração enquanto aplicação/pulse provider-native não forem comprovadas.
- Eidolon: Repraised 0.5.0.2: mesma exigência.
- Iron's 1.21.1-3.16.3 / Ars Nouveau 5.13.1: hit direto pertence a A0083/A0084; ticks derivados não são convertidos em A0085 automaticamente.
- Vampirism 1.10.12: heal nativo não entra sem correlação ao mesmo pulso.
- Black Arcana `ARCANE_BACKLASH`, Enshrouded/Shroud, Volcanoes hazards, fogo/lava/ambiente e máquinas/turrets/fake players: inelegíveis.

## Implementação Chat 2 — 2026-09-01

- `CombatPerkAvailabilityRuntime` marca A0085 unavailable e `effectiveRanks` remove qualquer contribuição de rank persistido;
- purchase server-authoritative recusa o node antes de mutação de pontos;
- `A0081A0100CombatPolicy` mantém a fórmula pura, mas nenhum adapter chama o resolver com `periodic=true`;
- o adapter A0083 do Iron's aceita somente `SpellDamageSource.isDirect()==true`, portanto não transforma automaticamente `indirect()`/DoT em sustain periódico;
- nenhum ledger de application/pulse foi inventado porque o dossiê exige que sua identidade venha do provider real;
- summons/companions/hazards continuam sem owner inheritance.

## Checklist Chat 2

- [x] Availability fail-closed implementada
- [x] Rank efetivo mascarado
- [x] Purchase sem gasto/rank fantasma
- [x] Fórmula latente preservada sem producer falso
- [x] Direct magic não é reciclada como DoT
- [x] Sem owner inheritance indireto
- [x] Código presente em fail-closed
- [ ] **PENDÊNCIA / RETORNO AO CHAT 1 QUANDO HOUVER PROVIDER:** aprovar primeiro provider que exponha applicationId+pulseId+owner persistente
- [ ] **VALIDAÇÃO CHAT 3:** purchase unavailable/rank persistido=efeito zero
- [ ] **VALIDAÇÃO CHAT 3:** DoT/summon/hazard não chama SustainResolver
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/testes de integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | OCCULT + fonte DoT + receipt causal. |
| Integração global | PASS | um SustainResolver, sem bucket paralelo. |
| Qualidade/identidade | PASS | sustain de DoT autorado, não de ambiente/summons. |
| Topologia | PASS | OCCULT/SUSTAIN, Camada 4. |
| Especializações | PASS | PP por mapeamento semântico. |
| PT-BR | PASS | termos de aplicação/pulso documentados. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | Goety/Malum/Eidolon/Iron's/Ars somente com receipt real. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
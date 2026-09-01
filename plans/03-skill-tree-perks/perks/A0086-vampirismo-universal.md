# A0086 — Vampirismo Universal

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability transitiva em 2026-08-31.
- **Notion:** `3c569db9-f0db-819f-8216-fbbafe17b035`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- A0086 permanece **indisponível/não comprável** enquanto A0085 não possuir binding causal, mesmo com A0083 agora disponível para Iron's exato.

## Contrato canônico

- Keystone híbrida: A0082=3 + A0083=3 + A0085≥2, todos legitimamente disponíveis/adquiridos.
- 1 rank, custo 3.
- Dano físico direto, mágico direto e periódico elegível convergem no mesmo `SustainResolver`.
- Se houver coeficiente especializado elegível, usa-se o maior. A fonte universal de 1% só cobre root elegível sem coeficiente especializado.
- Uma root/pulse cura no máximo uma vez; cap global 3% max health/20 ticks; sem carry-over.

## Implementação Chat 2 — 2026-09-01

- `CombatPerkAvailabilityRuntime` mantém A0086 unavailable por dependência estrutural de A0085;
- `effectiveRanks` mascara rank persistido, impedindo que a fórmula universal seja usada como bypass dos classifiers ausentes;
- `A0081A0100CombatPolicy.sustainCoefficient(...)` continua usando `max(...)` e só considera universal 1% quando a root já foi classificada como weapon/magic/elemental/periodic;
- A0082 físico e A0083 Iron's convergem no mesmo `SustainResolver`; nenhuma soma integral foi criada;
- A0086 não classifica origem desconhecida e não converte hazard/summon/machine/source ambígua em sustain.

## Checklist Chat 2

- [x] Availability transitiva implementada
- [x] Purchase sem bypass universal
- [x] Rank efetivo mascarado
- [x] `max coefficient` preservado
- [x] Universal 1% continua dependente de root previamente elegível
- [x] Um único `SustainResolver` preservado
- [x] Código presente em fail-closed
- [ ] **PENDÊNCIA:** A0085 continua sem provider receipt e bloqueia a keystone
- [ ] **VALIDAÇÃO CHAT 3:** A0086 purchase recusada enquanto A0085 unavailable
- [ ] **VALIDAÇÃO CHAT 3:** rank persistido A0086 produz zero efeito
- [ ] **VALIDAÇÃO CHAT 3:** roots A0082/A0083 não somam coeficientes
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/testes de integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0082=3 + A0083=3 + A0085≥2 e availability real. |
| Integração global | PASS | keystone converge, não cria pipeline paralelo. |
| Qualidade/identidade | PASS | universalidade vem de cobertura, não de heurística. |
| Topologia | PASS | HYBRID/SUSTAIN_CONVERGENCE. |
| Especializações | PASS | não satisfaz Specialist por si só. |
| PT-BR | PASS | contrato explícito. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | apenas providers já integrados causalmente. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.
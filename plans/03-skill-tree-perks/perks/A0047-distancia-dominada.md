# A0047 — Distância Dominada

## Estado

- **Design:** APROVADO após explicitar a propagação de availability de A0044 no review da PR #243.
- **Implementação:** **NÃO CONFIRMADA / FAIL-CLOSED CORRETO VALIDADO PELO CHAT 3**.
- **Disponibilidade:** `UNAVAILABLE_NODE` enquanto A0044 estiver indisponível; A0048 herda esse bloqueio estrutural.
- **Notion:** `3c569db9-f0db-8125-8454-da7f3c95587f`; corrigido e re-fetch PASS em 2026-08-30.

## Contrato canônico

- A0044 ≥2 + A0045 ≥1 + gateway `epic_bow`; 2 ranks.
- A dependência de A0044 é estrutural. Se A0044 estiver indisponível/não comprável por ausência de draw/preparation-speed binding, A0047 também fica indisponível/não comprável; não existe bypass automático.
- Disparo totalmente tensionado + ≥25 Foco + ≥0,5 s de mira estável pode comprometer 25 Foco.
- Com provider seguro de launch/projectile speed: +10%/+15% no lançamento.
- No impacto do mesmo projétil a ≥12 blocos da origem registrada: +8%/+12% de penetração física per-hit.
- O custo só ocorre quando ao menos um componente seguro pode ser aplicado e o próprio nó estiver legitimamente disponível.

## Evidência runtime

- `CombatPerkAvailabilityRuntime` marca A0047 indisponível por dependência estrutural de A0044.
- `A0041A0060RuntimeState.ranks(...)` mascara qualquer rank legado de A0047.
- `A0041A0060ProjectileEvents.onArrowLoose(...)` passa `projectileSpeedAvailable=false`; presença de `AbstractArrow` não é tratada como capability de launch speed.
- o bridge não chama `arrow.setDeltaMovement(...scale(...))` para A0047.
- a penetração per-hit permanece modelada no policy para futura reativação legítima, sem modifier persistente.

## Provider→árvore

- Nenhum projeto próprio retroauditado fornece projectile-speed BOW para A0047.
- Stage 11 itemização não projeta esse efeito.
- Pufferfish/Apothic só poderão participar por adapter semântico/versionado e sem double-processing.
- Backlash, Shroud e companions não herdam a marca do disparo.

## Pendências históricas

### P-A0047-01 — speed sem provider

Resolvida no runtime atual por omissão: speed declarado como indisponível e fallback `setDeltaMovement` removido.

### Dependência de P-A0044-01

Continua impondo indisponibilidade integral de A0047 até A0044 ser legitimamente habilitada por provider de preparation speed.

## Testes exigidos / estado atual

- A0044 indisponível → A0047 indisponível/não comprável: **PASS**.
- rank legado mascarado e sem gasto de Focus enquanto indisponível: **PASS**.
- ausência de projectile-speed sintético/double-scale: **PASS**.
- provider speed futuro presente: **N/A — provider semântico ainda inexistente; permanece fail-closed**.
- hit <12 / ≥12 e penetração funcional: **N/A enquanto a cadeia estrutural estiver indisponível**.

## Fechamento Chat 2 — 2026-09-01

O Chat 2 removeu o fallback sintético e materializou a propagação de availability. Nenhum bypass de A0044 foi criado.

## Fechamento Chat 3 — 2026-09-05

- contrato revisado contra o runtime atual; nenhum redesign necessário;
- availability transitiva A0044→A0047, masking de rank legado e ausência do fallback sintético foram validados;
- `RPG Skill Tree CI` #3467 / run `33986475213`: **SUCCESS**, incluindo JUnit 5, NeoForge JUnit adapter tests, NeoForge GameTests, provider-present GameTests, build e dedicated-server smoke;
- `SonarQube Cloud` #703 / run `33986475341`: **SUCCESS**;
- **estado final:** `NÃO CONFIRMADA / FAIL-CLOSED CORRETO`; reativação exige provider semântico real, sem bypass.

## Checklist Chat 3

- [x] Design aprovado pelo Chat 1
- [x] Código/fail-closed presente pelo Chat 2
- [x] Contrato revisado contra o código
- [x] Provider-native confirmado
- [x] Gate/dependências confirmados
- [x] Fallback/fail-closed confirmado
- [x] Deduplicação/ausência de double-scale confirmada
- [x] Autoria causal preservada
- [x] Testes unitários verdes
- [x] GameTests verdes
- [x] Build NeoForge verde
- [x] Dedicated-server smoke verde
- [x] CI e Sonar verdes
- [ ] IMPLEMENTAÇÃO CONFIRMADA — **N/A enquanto A0044/provider obrigatório permanecer indisponível**

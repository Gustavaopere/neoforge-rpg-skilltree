# A0081 — Recuperação de Combate

## Estado

- **Design:** APROVADO EM FAIL-CLOSED após correção de availability em 2026-08-31.
- **Notion:** `3c569db9-f0db-81cb-a393-d34c2a87af0d`; Gate/Fallback/Regra corrigidos; re-fetch PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Estado Chat 3:** **NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA / AGUARDANDO MERGE DA PR #372**.
- A0081 permanece **indisponível/não comprável** enquanto A0075 estiver indisponível.

## Contrato canônico

- MARTIAL + A0075 Ritmo Sustentado ≥1 legitimamente disponível e ativo.
- 3 ranks: 15% / 20% / 25% do dano corpo a corpo direto pós-mitigação realmente causado vira reserva.
- Reserva máxima: 8% da vida máxima; overkill é cortado por `min(dano, vida pré-impacto do alvo)`.
- Após 3 s sem receber dano hostil, congela `recovery_snapshot`; até 25% do snapshot por segundo, no máximo quatro parcelas.
- Só vida realmente restaurada reduz a reserva; sem overheal. Novo dano hostil interrompe a fase; reserva expira após 10 s fora de combate.
- Recuperação é pipeline próprio e **não** usa o bucket do `SustainResolver`.

## Availability transitiva

A0075 está atualmente indisponível/não comprável porque o contrato all-or-nothing de Stamina regen + contribuição térmica Cold Sweat + exhaustion ainda não possui binding térmico seguro. Logo A0081 herda a indisponibilidade. Matemática presente não autoriza compra, rank fantasma nem gasto de pontos.

Perda de A0075 por rank loss/respec/rules reload também invalida imediatamente qualquer reserva/snapshot A0081.

## Cobertura de providers

- Minecraft/NeoForge: dano/vida/cura e timing server-side.
- Epic Fight 21.17.3.1: classificação provider-native de melee e root de ação quando comprovada.
- Simply Swords 1.70.2, Simply More 1.3.0 ALPHA e bridges: podem fornecer armas, mas habilidades/procs provider-native não viram melee direto apenas porque o jogador segura uma arma.
- Cold Sweat 2.4.2 e Thirst Was Reclaimed 3.0.4: não modulam A0081; o bloqueio vem somente da dependência A0075.
- Magia, summons, companions, Black Arcana Backlash, Enshrouded/Volcanoes hazards e dano de máquinas/contraptions: inelegíveis.

## Implementação Chat 2 — 2026-09-01

- `CombatPerkAvailabilityRuntime` mascara A0081 e rejeita purchase antes de replay/custo enquanto A0075 estiver indisponível;
- `A0081A0100RuntimeState.ranks(...)` passou a usar `effectiveRanks` e limpa `SustainResolver`, `CombatRecoveryService`, defense state, Blood Thirst e receipts provider quando a snapshot efetiva muda;
- `CombatRecoveryService` existente continua como owner da reserva, snapshot, quatro parcelas, clipping de overkill, confirmação do heal e dedup por root;
- Epic Fight entrega o `rootActionId` provider-native ao `A0081A0090ProviderHitRegistry`; o pagamento só ocorre em `LivingDamageEvent.Post`, com dano pós-mitigação real;
- fallback vanilla aceita melee somente para `minecraft:player_attack` no `DamageSource` vanilla direto; sources custom/provider não são promovidas por mera presença de item na mão;
- arrows de bow/crossbow exigem launch receipt server-side; siblings gerados pela mesma janela de lançamento compartilham uma root, impedindo multiplicação artificial por Multishot;
- como o rank efetivo de A0081 é zero no estado atual, nenhuma reserva/parcelamento pode vazar enquanto A0075 permanecer indisponível.

## Checklist Chat 2

- [x] Hook/runtime já presente e reconciliado com POST pós-mitigação
- [x] Gate A0075→A0081 implementado em availability
- [x] Purchase fail-closed sem gasto/rank fantasma
- [x] Root Epic Fight provider-native preservada
- [x] Fallback vanilla estreitado para `minecraft:player_attack`
- [x] Projectile launch/root dedup implementado
- [x] Cleanup em rank/availability change e lifecycle implementado
- [x] Código presente em fail-closed
- [ ] **VALIDAÇÃO CHAT 3:** overkill/cap/snapshot/quatro parcelas
- [ ] **VALIDAÇÃO CHAT 3:** dano hostil interrompe fase e expiry 10 s
- [ ] **VALIDAÇÃO CHAT 3:** dedup Epic Fight/vanilla e Multishot
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/testes de integração
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Pendências para Chat 3

- provar dinamicamente que A0075 indisponível impede purchase/rank efetivo A0081;
- exercitar rank loss/respec/rules reload e confirmar reserva/snapshot zerados;
- testar melee vanilla, Epic Fight e tentativa de ability/proc com item na mão;
- testar Multishot, cancelamento/dano zero e ausência de múltiplas reservas para uma única root.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0075≥1 e availability transitiva. |
| Integração global | PASS | Recuperação separada de vampirismo/SustainResolver. |
| Qualidade/identidade | PASS | sustain diferido pós-combate, distinto de lifesteal. |
| Topologia | PASS | MARTIAL/SUSTAIN, Camada 4. |
| Especializações | PASS | PP apenas por mapeamento semântico. |
| PT-BR | PASS | contrato e UI em PT-BR. |
| Notion | PASS após correção | re-fetch confirmado. |
| NeoVitae | PASS | removido/ausente. |
| Providers | PASS no design | provider-native first; fontes indiretas/hazards fail-closed. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.

## Validação Chat 3 — 2026-09-07

- `A0081A0100CombatPolicyTest` confirmou cap de reserva de 8% da vida máxima, atraso de 3 s, snapshot congelado em quatro parcelas, desconto apenas da cura real, interrupção por dano hostil e expiração após 10 s.
- `A0081A0090RuntimeCoverageJUnitTest` confirmou availability A0075→A0081, rank persistido mascarado para rank efetivo zero e limpeza de claims/estado quando a snapshot efetiva muda.
- `A0081A0090EventCoverageJUnitTest` confirmou cleanup em morte, logout, troca de dimensão, respawn e server stop, além da correlação projectile/root e descarte de dano zero.
- Baseline de validação de código `4502d1d253863f6f25e13e6a7284a0d53a3b0fd5`: RPG Skill Tree CI run `34147843664` SUCCESS, incluindo JUnit 5, NeoForge JUnit, GameTests, build, JAR e dedicated-server smoke; Sonar run `34147843581` SUCCESS; CodeQL run `34147843620` SUCCESS.
- **Resultado:** o comportamento fail-closed aprovado está confirmado. A perk não é promovida a jogável enquanto A0075 continuar indisponível.

### Checklist final Chat 3

- [x] Design aprovado pelo Chat 1
- [x] Código presente pelo Chat 2
- [x] Contrato revisado contra o código
- [x] Availability/gate transitivo confirmado
- [x] Fail-closed e rank efetivo zero confirmados
- [x] Deduplicação/lifecycle confirmados
- [x] Testes unitários/NeoForge JUnit verdes
- [x] GameTests verdes
- [x] Build NeoForge verde
- [x] Dedicated-server smoke verde
- [x] SonarQube e CodeQL verdes no baseline funcional
- [x] **IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA**
- [x] **JOGABILIDADE N/A no estado atual — node estruturalmente indisponível por contrato**

O HEAD documental final deve ser revalidado em CI antes do merge da PR #372.
# A0028 — Abalo Crescente

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** PARCIAL / FAIL-CLOSED VALIDADO EM CI na PR #242; `P-A0028-01` permanece aberta.
- **Notion:** `3c569db9-f0db-8150-81cb-e559d7123a3d`.

## Contrato canônico

- A0027 ≥2 + gateway `epic_hammer`.
- Hit direto HAMMER/heavy do jogador gera 1 Abalo ator→alvo, cap 3.
- Rank 1/2: cada carga aumenta em +8%/+12% a pressão de guarda/postura do próximo golpe de martelo contra o mesmo alvo.
- Sem dano bruto adicional; estado do alvo expira 6 s após o último ganho e não transfere.
- Se pressão de guarda/postura não estiver disponível por receipt seguro, o benefício fica inativo; não converter para impacto/dano/knockback/crítico/Armor Negation.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS de design — estado RPG + pressão provider-native.
3. Identidade: PASS — preparação crescente contra postura.
4. Topologia: PASS — Notable.
5. Especializações: PASS — exterior.
6. PT-BR: PASS.
7. Notion: PASS após boundary de guarda.
8. NeoVitae: PASS.
9. Providers: PASS de design; benefício de pressão permanece fail-closed por falta de receipt causal seguro.

## Evidência e pendência

- `A0021A0040CombatState`/policy registram Abalo por alvo e cap/expiração.
- O adapter mantém `guardPressureAvailable=false`; portanto as cargas existem, mas a parcela +8%/+12% não é aplicada.
- Na fonte real do Epic Fight 21.17.3.1, `GuardSkill` calcula o custo de guarda como `penalty × impact`, e o mesmo `impact` também influencia knockback. Usar o modificador de impact para representar pressão de guarda alteraria impacto/knockback e violaria o contrato.
- `SkillConsumeEvent` permite modificar stamina, porém não fornece `DamageSource`; o evento de consumo emitido dentro de `GuardSkill` não oferece receipt público que ligue causalmente o custo de guarda ao mesmo HAMMER/Abalo atacante. Correlacionar por timing/contexto seria heurística frágil.
- **P-A0028-01 permanece aberta:** sem receipt provider-native causal de pressão de guarda/postura, o benefício fica inativo. Não converter para dano/impacto/knockback/crítico/Armor Negation.
- Arcane Resistance/Corruption Resistance, Shroud/Exposure/Madness não são guarda/postura.
- `ARCANE_BACKLASH`, hazards e companions Mobstein não geram Abalo para o jogador.

`P-A0028-01` continua bloqueando a confirmação do efeito completo, não o design.

## Chat 2 — implementação e regressão — PR #242

- A família HAMMER usada para gerar Abalo foi endurecida para provider-native Epic Fight; a tag HAMMER paralela foi removida.
- Regressão JUnit prova que, sem `guardPressureAvailable`, A0028 não substitui o benefício por dano ou impacto e não consome as cargas indevidamente.
- CI #2192 validou o fail-closed junto de JUnit, GameTests, build, JAR e dedicated-server smoke.
- Estado pós-merge deve permanecer `IMPLEMENTAÇÃO PARCIAL / FAIL-CLOSED`, não `IMPLEMENTAÇÃO CONFIRMADA`, até existir receipt seguro.

# A0023 — Ataque ao Ponto Cego

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** VALIDADA EM CI na PR #242; confirmação definitiva após merge em `main`.
- **Notion:** `3c569db9-f0db-814d-9dee-e20bca763f8c`.

## Contrato canônico

- A0020 ≥2 + A0021 ≥1 + gateway `epic_dagger`.
- Com ≥2 Fluxo, hit direto de adaga por flanco/traseira pode consumir 2 Fluxo.
- Rank 1: +15% dano crítico elegível e até +6% penetração física.
- Rank 2: +25% e até +10%.
- Cooldown por alvo: 4 s.
- Orientação deve ser server-authoritative; sem receipt seguro, fail-closed.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS — usa Fluxo e crítico/penetração canônicos.
3. Identidade: PASS — recompensa ponto cego real.
4. Topologia: PASS — Notable de posicionamento.
5. Especializações: PASS — exterior.
6. PT-BR: PASS.
7. Notion: PASS após boundary causal.
8. NeoVitae: PASS.
9. Providers: PASS — nenhum projeto próprio substitui orientação Epic Fight.

## Evidência e boundaries

- `A0021A0040CombatPolicy` exige Fluxo, flank/rear e cooldown antes do consumo.
- `A0021A0040EpicFightHooks` calcula orientação/posição server-side e evita fallback client-only.
- `ARCANE_BACKLASH`, dano secundário e companions Mobstein não consomem Fluxo nem recebem o bônus.
- Volcanoes/Enshrouded não fornecem receipt geométrico.

## Pendências

Nenhuma de design ou implementação bloqueante. A orientação server-side e autoria direta permanecem obrigatórias; nenhuma heurística por câmera, dano ou movimento genérico foi adicionada.

## Chat 2 — implementação e regressão — PR #242

- O pipeline de A0023 foi preservado sem nova rolagem crítica.
- A correção de A0022 tornou a rota geométrica server-side disponível sem relaxar os gates de A0023.
- O consumo de Fluxo continua condicionado a flank/rear, cooldown por alvo e hooks físicos disponíveis.
- CI #2192 validou JUnit, GameTests, build, JAR e dedicated-server smoke antes do fechamento documental.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura:** Dagger/Sai e armas do stack Simply só participam quando Epic Fight Compat resolve a capability como `DAGGER`; tipo Simply, namespace, nome e tooltip não classificam a arma.
- **Overlap legítimo:** o backstab Implicit provider-native pode coexistir no mesmo root direto com A0023, mas cada sistema aplica somente sua parcela uma vez.
- **Anti-double-dip:** A0023 não rerrola, reaplica, escala nem converte o backstab Implicit. Unique ability, gem power, Runic Power, Awakening, delegated/derived hit ou outro efeito Simply não cria segundo `rootActionId` A0023 e não consome Fluxo novamente.
- **Simply More alpha:** Unique/efeito não comprovado no artifact `1.3.0 ALPHA` permanece fail-closed.
- **Simply Tooltips:** `NÃO DEVE SER INTEGRADO`; apresentação não fornece família nem orientação.
- **Notion:** `Provider/Mods`, `Hook`, `Fallback` e `Regra` atualizados; re-fetch confirmou persistência.
- **Resultado:** design preservado, com boundary explícita para evitar dupla aplicação do mesmo conceito posicional.

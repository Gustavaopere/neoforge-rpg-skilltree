# A0024 — Maestria de Adagas — Dança das Sombras

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** presente com fallback canônico para a parcela de stamina.
- **Notion:** `3c569db9-f0db-81c1-9774-e31e4f891e57`.

## Contrato canônico

- A0022 + A0023 + mastery `epicfight:dagger` ≥80.
- Com 4 Fluxo, ataque de adaga em até 2 s após dodge/reposicionamento válido consome todo o Fluxo e ativa Dança por 4 s; mastery 90/100 amplia para 4,5/5 s.
- Primeiro reposicionamento válido: custo de stamina Epic Fight 30% menor, somente se o custo puder ser modulado com receipt seguro.
- Primeiro hit lateral/traseiro: +20% impacto e +15% dano físico elegível.
- Cada benefício ocorre no máximo uma vez por ativação.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS — Epic Fight permanece owner da stamina.
3. Identidade: PASS — capstone de mobilidade e posicionamento.
4. Topologia: PASS — terminal de adagas.
5. Especializações: PASS — `TERMINAL_EXTERIOR: MARTIAL/ADAGAS`.
6. PT-BR: PASS.
7. Notion: PASS após boundary de recursos.
8. NeoVitae: PASS.
9. Providers: PASS/FALLBACK — sem stamina hook seguro, omite-se somente esse componente.

## Evidência e boundaries

- `A0021A0040CombatPolicy`/state modelam ativação e consumo único.
- `A0021A0040EpicFightHooks.onSkillConsume` pode reduzir o primeiro custo elegível da Dança quando correlacionado ao movimento/dodge.
- A perk não converte Fúria, hunger/exhaustion, Cold Sweat CORE, Shroud/Exposure ou Arcane Strain em stamina.
- `ARCANE_BACKLASH` e companions Mobstein não consomem Dança nem recebem os benefícios do dono.

## Pendências

Nenhuma de design. Se o receipt exato de stamina não estiver disponível para uma ação, a redução de stamina permanece legitimamente omitida sem alterar os demais componentes seguros.

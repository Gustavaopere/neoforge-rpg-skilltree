# A0024 — Maestria de Adagas — Dança das Sombras

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** VALIDADA EM CI NO FALLBACK CANÔNICO na PR #242; confirmação definitiva após merge em `main`.
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
9. Providers: PASS/FALLBACK — sem stamina hook seguro para uma ação concreta, omite-se somente esse componente.

## Evidência e boundaries

- `A0021A0040CombatPolicy`/state modelam ativação e consumo único.
- `A0021A0040EpicFightHooks.onSkillConsume` reduz o primeiro custo elegível da Dança quando existe receipt de movimento/dodge compatível.
- A rota de reposicionamento geométrico de A0022 foi implementada server-side na PR #242 e agora pode satisfazer a janela sem depender de câmera.
- A perk não converte Fúria, hunger/exhaustion, Cold Sweat CORE, Shroud/Exposure ou Arcane Strain em stamina.
- `ARCANE_BACKLASH` e companions Mobstein não consomem Dança nem recebem os benefícios do dono.

## Pendências

Nenhuma bloqueante dentro do contrato aprovado. Quando a ação concreta não expõe custo de stamina correlacionável com segurança, a redução de stamina permanece legitimamente omitida; os demais componentes seguros continuam ativos.

## Chat 2 — implementação e regressão — PR #242

- A dependência técnica da rota geométrica de A0022 foi resolvida sem heurística client-side.
- Consumo único de Fluxo e benefícios únicos por ativação permanecem no state/policy canônicos.
- O fallback de stamina não foi redesenhado nem convertido para outro recurso.
- CI #2192 validou o runtime antes do fechamento documental.

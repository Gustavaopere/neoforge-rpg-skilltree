# A0024 — Maestria de Adagas — Dança das Sombras

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** CONFIRMADA NO FALLBACK CANÔNICO na `main` pela PR #248; reauditoria técnica abriu `P-A0024-01` para o Chat 2.
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
- A rota de reposicionamento geométrico de A0022 foi implementada server-side e pode satisfazer a janela sem depender de câmera.
- A perk não converte Fúria, hunger/exhaustion, Cold Sweat CORE, Shroud/Exposure ou Arcane Strain em stamina.
- `ARCANE_BACKLASH` e companions Mobstein não consomem Dança nem recebem os benefícios do dono.

## Pendências

- `P-A0024-01` — **ABERTA PARA CHAT 2**: o runtime atual consome 4 Fluxo/ativa Dança no PRE e também consome no PRE o one-shot do primeiro hit lateral/traseiro. Deve usar reservation→commit por `rootActionId`: ativação e benefício são apenas reservados no PRE e commitados no POST direto/hostil/com dano >0; cancelamento/dano zero preserva Fluxo e one-shot. No hit de ativação, A0024 deve commitar antes de A0022 produzir Fluxo, resultando `4 → 0 → 1`.
- A redução de stamina continua fallback legítimo: se a ação concreta não expõe custo correlacionável com segurança, omitir apenas esse componente; não converter para outro recurso.

## Chat 2 — implementação e regressão — PR #248

- A dependência técnica da rota geométrica de A0022 foi resolvida sem heurística client-side.
- O fallback de stamina não foi redesenhado nem convertido para outro recurso.
- A implementação foi mergeada pela PR #248.

## Auditoria técnica pré-Chat 2 — 2026-08-31

- Reprodução transitória em CI #2302 confirmou o consumo causal prematuro de A0024; a implementação experimental usada na validação foi descartada e não integra esta entrega.
- O Chat 2 deve implementar `P-A0024-01` com testes RED→GREEN para ativação, one-shot e cancelamento/dano zero.
- A ordem consumer→producer com A0022 é requisito de aceite.
- O merge/fechamento não pertence a este chat.

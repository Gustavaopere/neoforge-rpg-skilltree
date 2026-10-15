# A0324 — Esquiva Econômica

## Estado

- **Chat 1:** DESIGN APROVADO.
- **Disponibilidade atual:** IMPLEMENTÁVEL via Epic Fight.
- **Fonte canônica:** Notion `A0324` — https://app.notion.com/3c569db9f0db81d8acf5d207dcd741b2
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0324 reduz **somente o custo NATIVO de STAMINA de uma ação DODGE real**, em −4% por rank:

- rank 1: custo ×0,96;
- rank 2: ×0,92;
- rank 3: ×0,88;
- rank 4: ×0,84.

Não cria stamina, não converte outra ação em esquiva e não reduz METABOLIC ou HYDRATION.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0318 Passo Leve ≥1.

Rota segura atual: evento `Player.CONSUME_SKILL` / `SkillConsumeEvent` do Epic Fight 21.17.3.1, server-side, referente a skill `SkillCategories.DODGE`, resource `STAMINA` e amount nativo > 0.

Slide, sprint, wall jump, vault, roll de queda, dash ofensivo e outras skills não entram por aproximação.

## Providers e authority

- Epic Fight 21.17.3.1: authority de sua stamina e do debit de skill; `SkillConsumeEvent` expõe skill/resource/amount mutável antes de `consume()`.
- ParCool 4.0.0.3: possui DODGE/stamina, porém o fluxo 1.21.1-v4 auditado não fornece authority server-side precommit segura; rota permanece FAIL-CLOSED.
- Epic ParCool 21.0.0: bridge, nunca segundo owner do mesmo debit.
- RPG Skill Tree: owner da contribuição A0324 e da deduplicação cross-bridge quando necessária.

A `main` auditada não possui serviço genérico de `debit claim`; isso não bloqueia a rota Epic Fight porque o próprio evento provider-native é o boundary primário.

## Hook e pipeline canônico

No `Player.CONSUME_SKILL` do Epic Fight, antes do consumo:

1. confirmar categoria DODGE;
2. confirmar resource STAMINA;
3. capturar amount nativo;
4. calcular `native_amount × (1 − 0.04 × rank)`;
5. escrever de volta uma única vez no mesmo evento/provider;
6. respeitar `native_min_cost` somente se o provider expuser um piso real.

Se o provider não expuser piso, não inventar um.

Custo nativo zero continua zero; nunca gerar refund.

## Deduplicação

Cada debit Epic Fight deve receber A0324 uma vez. Se adapters futuros convergirem na mesma pool/debit, introduzir identidade explícita `debit_id + pool_id` antes de compor. Epic ParCool não pode reaplicar o desconto observado no mesmo consumo.

## Fallback / fail-closed

- Epic Fight: rota segura atual.
- ParCool: sem efeito até existir boundary PRECOMMIT server-authoritative.
- Provider futuro sem custo nativo: custo zero permanece zero.

É proibido criar stamina paralela, pollar barra, conceder regen/refund compensatório ou aplicar desconto pós-fato.

## Boundaries adicionais

A0324 não altera:

- i-frames;
- distância/velocidade da esquiva;
- recovery;
- cooldown;
- animação;
- custo de outras skills;
- Mastery.

## Testes destinados ao Chat 3

1. Epic Fight DODGE/STAMINA em ranks 1–4 resulta ×0,96/0,92/0,88/0,84;
2. skill não-DODGE não é alterada;
3. resource diferente de STAMINA não é alterado;
4. custo zero permanece zero;
5. exatamente uma mutação por debit/evento;
6. Epic ParCool presente não duplica desconto;
7. ParCool-only permanece fail-closed sem polling/refund;
8. nenhum i-frame/distância/recovery/cooldown é alterado;
9. respec/rank loss deixa de modificar novos debits;
10. dedicated-server + multiplayer com Epic Fight 21.17.3.1.

## Handoff Chat 2

Implementar provider-native first no `SkillConsumeEvent` do Epic Fight. ParCool permanece sem rota até existir precommit server-authoritative; não criar workaround para ampliar cobertura.

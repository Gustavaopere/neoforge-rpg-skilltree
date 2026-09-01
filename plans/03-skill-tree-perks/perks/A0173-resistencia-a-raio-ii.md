# A0173 — Resistência a Raio II

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO MESMO RESOLVER DE A0172.**

Chat 1 não implementa runtime. A0173 não cria um segundo sistema defensivo; ela apenas adiciona contribuição condicional ao mesmo bucket `RPG_LIGHTNING_RESISTANCE` de A0172.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db815bb265e47a163fdf4d`.

## Contrato

- VITALITY ↔ ARCANE; camada 5; Ramo; 3 ranks; 1 PP/rank.
- Pré-requisito: A0172 rank ≥2.
- Se a vida **imediatamente anterior ao impacto** estiver estritamente abaixo de 50% da vida máxima: +4% de Resistência a Raio por rank.
- Escalonamento adicional: +4% / +8% / +12%.
- Exatamente 50% não ativa.
- Com A0172 max + A0173 max, a contribuição local da família chega a 28%; isso não é cap defensivo global.

## Boundary e authority

Usar o mesmo `LivingDamageEvent.Pre`, classifier LIGHTNING e `ElementalDamageMitigationResolver` definidos para A0172.

A leitura de vida deve ocorrer antes da mutação do dano do evento e antes da perda final de vida. Não usar vida projetada após o golpe para decidir o gate.

## Pipeline canônico

`LivingDamageEvent.Pre -> capturar health/maxHealth PRE-impacto -> classificar LIGHTNING -> somar A0172 + (A0173 se health/maxHealth < 0.5) no mesmo bucket -> clamp matemático [0,1] -> uma única mutação do dano`.

## Deduplicação

- não criar listener específico para A0173;
- não aplicar A0172 e A0173 sequencialmente como duas multiplicações independentes;
- somar contribuições primeiro e aplicar uma vez;
- o mesmo root/evento recebe a família `RPG_LIGHTNING_RESISTANCE` uma única vez.

## Fallback

- source desconhecida/não versionada: inelegível;
- vida exatamente 50%: A0173 = 0;
- FE/Create/Oritech/eletricidade genérica não classificam LIGHTNING;
- temperatura/Afinidade não influenciam o gate.

## Handoff Chat 2

Implementar A0173 como extensão do cálculo de `RPG_LIGHTNING_RESISTANCE`. O resolver deve ler vida pré-impacto, não pós-mitigação, e manter a família em uma passagem única.

## Testes obrigatórios para Chat 3

1. ranks 0–3 = +0/+4/+8/+12% quando elegível;
2. 49,999...% de vida ativa e 50% exato não ativa;
3. acima de 50% A0173 contribui 0;
4. A0172 max + A0173 max = 28% local;
5. source não-LIGHTNING não ativa;
6. classifier Iron's/vanilla consistente com A0172;
7. uma única aplicação do bucket;
8. vida capturada PRE-impacto;
9. provider mismatch fail-closed;
10. reload/respec/dedicated-server safety.

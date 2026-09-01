# A0180 — Resistência a Natureza II

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO MESMO RESOLVER DE A0179.**

Chat 1 não implementa runtime. A0180 apenas adiciona contribuição condicional ao bucket `RPG_NATURE_RESISTANCE`; não cria classifier nem reducer próprio.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db812981ebfaae9565e401`.

## Contrato

- VITALITY ↔ SURVIVAL ↔ ARCANE; camada 5; Ramo; 3 ranks; 1 PP/rank.
- Pré-requisito: A0179 rank ≥2.
- Se a vida imediatamente anterior ao evento estiver **estritamente abaixo de 50%** da vida máxima: +4% de Resistência a Natureza por rank.
- Escalonamento adicional: +4% / +8% / +12%.
- Exatamente 50% não ativa.
- A0179 max + A0180 max = 28% de contribuição local da família; não é cap defensivo global.

## Boundary e authority

Usar o mesmo `LivingDamageEvent.Pre`, classifier NATURE e `ElementalDamageMitigationResolver` de A0179.

Vida é capturada no estado pré-impacto. Não usar a vida projetada depois da mitigação ou depois do dano para decidir o gate.

## Pipeline canônico

`LivingDamageEvent.Pre -> capturar health/maxHealth PRE-impacto -> classificar NATURE -> somar A0179 + (A0180 se health/maxHealth < 0.5) -> clamp matemático [0,1] -> uma única mutação do dano`.

## Deduplicação

- não criar listener exclusivo A0180;
- não aplicar A0179 e A0180 como multiplicadores sequenciais independentes;
- somar contribuições no mesmo bucket antes de modificar o evento;
- uma passagem por root/evento.

## Fallback e exclusões

- provider desconhecido/não versionado: inelegível;
- exatamente 50% de vida: contribuição A0180 = 0;
- poison, planta, fauna, clima, fome e ambiente não classificam NATURE;
- vida projetada após o golpe não ativa;
- acesso topológico SURVIVAL não altera classifier.

## Handoff Chat 2

Implementar A0180 como extensão do cálculo de `RPG_NATURE_RESISTANCE` no mesmo resolver de A0179. Manter leitura pré-impacto e uma única mutação do dano.

## Testes obrigatórios para Chat 3

1. ranks 0–3 = +0/+4/+8/+12% quando elegível;
2. 49,999...% ativa e 50% exato não ativa;
3. acima de 50% A0180 contribui 0;
4. A0179 max + A0180 max = 28% local;
5. Iron's `nature_magic` segue o mesmo classifier de A0179;
6. poison/planta/fauna/ambiente negativos;
7. uma única aplicação do bucket;
8. vida capturada PRE-impacto;
9. provider mismatch fail-closed;
10. reload/respec/dedicated-server safety.

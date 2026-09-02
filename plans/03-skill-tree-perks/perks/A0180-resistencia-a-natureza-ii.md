# A0180 — Resistência a Natureza II

## Estado Chat 1

**DESIGN APROVADO / IMPLEMENTÁVEL NO MESMO RESOLVER DE A0179, CONDICIONADO À DISPONIBILIDADE DA FAMÍLIA NATURE.**

Chat 1 não implementa runtime. A0180 apenas adiciona contribuição condicional ao bucket `RPG_NATURE_RESISTANCE`; não cria classifier nem reducer próprio. Ela herda integralmente o gate de disponibilidade de A0179: sem pelo menos um classifier NATURE allowlisted/version-compatible ativo, A0179/A0180 ficam `UNAVAILABLE_NODE` e nenhuma compra nova pode gastar PP.

Notion corrigido e revalidado em 2026-09-02: `https://app.notion.com/p/3c569db9f0db812981ebfaae9565e401`.

## Contrato

- VITALITY ↔ SURVIVAL ↔ ARCANE; camada 5; Ramo; 3 ranks; 1 PP/rank.
- Pré-requisito: A0179 rank ≥2.
- **Gate de disponibilidade herdado:** `hasActiveNatureClassifier() == true` no mesmo registry/adapter set de A0179.
- Se nenhum classifier NATURE compatível estiver ativo: A0179/A0180 = `UNAVAILABLE_NODE`; compra falha antes do gasto.
- Allocation legado de A0180 enquanto indisponível conta 0 PP para gates/thresholds e permanece reembolsável/migrável.
- Se a vida imediatamente anterior ao evento estiver **estritamente abaixo de 50%** da vida máxima: +4% de Resistência a Natureza por rank.
- Escalonamento adicional: +4% / +8% / +12%.
- Exatamente 50% não ativa.
- A0179 max + A0180 max = 28% de contribuição local da família; não é cap defensivo global.

## Boundary e authority

Usar o mesmo `LivingDamageEvent.Pre`, classifier NATURE, availability predicate e `ElementalDamageMitigationResolver` de A0179.

A0180 **não** mantém seu próprio classifier registry, flag de availability ou listener. `hasActiveNatureClassifier()` é derivado do mesmo conjunto de adapters que o resolver usa para classificar fontes NATURE.

Vida é capturada no estado pré-impacto. Não usar a vida projetada depois da mitigação ou depois do dano para decidir o gate de baixa vida.

## Pipeline canônico

### Aquisição / disponibilidade

`purchase A0180 -> validar A0179>=2 -> validar hasActiveNatureClassifier() -> somente então debitar PP`.

Se a família perder todos os classifiers após reload/config/version mismatch, A0180 fica `UNAVAILABLE_NODE`; allocation legado não é apagado, mas vale 0 PP para gates/thresholds até respec/migração ou retorno de classifier válido.

### Dano

`LivingDamageEvent.Pre -> capturar health/maxHealth PRE-impacto -> classificar NATURE -> somar A0179 + (A0180 se health/maxHealth < 0.5) -> clamp matemático [0,1] -> uma única mutação do dano`.

## Deduplicação

- não criar listener exclusivo A0180;
- não criar classifier registry exclusivo A0180;
- não aplicar A0179 e A0180 como multiplicadores sequenciais independentes;
- somar contribuições no mesmo bucket antes de modificar o evento;
- uma passagem por root/evento;
- availability e classificação usam a mesma fonte de verdade de A0179.

## Fallback e exclusões

### Família sem classifier ativo

- A0179/A0180 = `UNAVAILABLE_NODE`;
- compra fail-before-spend;
- allocations legadas indisponíveis = 0 PP para gates/thresholds;
- allocations legadas permanecem reembolsáveis/migráveis;
- não converter A0180 em resistência genérica de baixa vida.

### Família disponível

- provider desconhecido/não versionado: inelegível;
- exatamente 50% de vida: contribuição A0180 = 0;
- poison, planta, fauna, clima, fome e ambiente não classificam NATURE;
- vida projetada após o golpe não ativa;
- acesso topológico SURVIVAL não altera classifier.

## Handoff Chat 2

Implementar A0180 como extensão do cálculo de `RPG_NATURE_RESISTANCE` no mesmo resolver de A0179. Reutilizar `hasActiveNatureClassifier()` para aquisição/ativação; manter leitura pré-impacto e uma única mutação do dano.

Não permitir compra A0180 se a família estiver unavailable, mesmo que exista allocation legado de A0179.

## Testes obrigatórios para Chat 3

1. ranks 0–3 = +0/+4/+8/+12% quando elegível e família disponível;
2. 49,999...% ativa e 50% exato não ativa;
3. acima de 50% A0180 contribui 0;
4. A0179 max + A0180 max = 28% local;
5. Iron's `nature_magic` segue o mesmo classifier de A0179;
6. poison/planta/fauna/ambiente negativos;
7. uma única aplicação do bucket;
8. vida capturada PRE-impacto;
9. provider mismatch individual fail-closed;
10. **nenhum classifier NATURE ativo -> A0179/A0180 `UNAVAILABLE_NODE`; compra A0180 falha antes do gasto**;
11. mudança de available -> unavailable preserva allocation legado, mas o torna 0 PP para gates/thresholds;
12. segundo classifier allowlisted válido mantém a família disponível mesmo sem Iron's;
13. reload/respec/dedicated-server safety.

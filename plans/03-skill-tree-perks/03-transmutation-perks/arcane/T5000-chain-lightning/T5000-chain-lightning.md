# T5000 — Chain Lightning

- **Família:** Transmutation Perk
- **Owner tree:** ARCANE
- **Ability alvo:** Chain Lightning
- **Provider candidato:** Iron's Spells 'n Spellbooks
- **Requisito conceitual:** Ability conhecida/desbloqueada
- **Status:** piloto conceitual; auditoria técnica do provider fica para a Fase 6

## Regra de identidade

`T5000` representa Chain Lightning como uma única Ability.

Todos os efeitos abaixo são filhos `T5000.n`. Um novo inteiro, como `T5001`, será usado para outra Ability.

## Modificadores

### Potência/impacto
- [T5000.1 — Cadeia Adicional](./T5000.1-cadeia-adicional.md)
- [T5000.2 — Primeiro Impacto](./T5000.2-primeiro-impacto.md)

### Forma/eficiência
- [T5000.3 — Alcance de Condução](./T5000.3-alcance-de-conducao.md)
- [T5000.4 — Condução Econômica](./T5000.4-conducao-economica.md)

### Metamorfoses
- [T5000.5 — Cadeia Frenética](./T5000.5-cadeia-frenetica.md)
- [T5000.6 — Fluxo de Retorno](./T5000.6-fluxo-de-retorno.md)
- [T5000.7 — Transmutação Elemental](./T5000.7-transmutacao-elemental.md)

## Stacking

Os modificadores são acumuláveis por padrão.

Exemplo: o jogador pode usar `T5000.1 + T5000.2 + T5000.7` simultaneamente, obtendo mais capacidade de cadeia, bônus no primeiro impacto e afinidade transmutada.

Somente efeitos realmente incompatíveis recebem conflito explícito.

## Referência de design

A estrutura 2/2/3 serve como inspiração de organização. Comportamento e números finais serão definidos pelo design do modpack e depois auditados contra o provider instalado.

A referência de metamorfose elemental permanece Chain of Cold como inspiração conceitual, sem transformar Diablo IV em authority técnica ou de balanceamento.

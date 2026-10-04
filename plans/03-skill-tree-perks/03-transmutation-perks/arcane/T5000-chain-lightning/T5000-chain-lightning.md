# T5000 — Chain Lightning

- **Família:** Transmutation Perk
- **Owner tree:** ARCANE
- **Ability alvo:** Chain Lightning
- **Provider candidato:** Iron's Spells 'n Spellbooks
- **Requisito conceitual:** Ability conhecida/desbloqueada
- **Status:** piloto conceitual; auditoria técnica do provider fica para a Fase 6

## Regra de identidade

`T5000` representa Chain Lightning como uma única Ability.

Ela possui uma única raiz no catálogo e três grupos de escolha.

## LEFT — escolher 1

- [T5000.11 — Redução de Custo](./T5000.11-reducao-de-custo.md)
- [T5000.12 — Crítico Condutivo](./T5000.12-critico-condutivo.md)

## RIGHT — escolher 1

- [T5000.21 — Cadeia Adicional](./T5000.21-cadeia-adicional.md)
- [T5000.22 — Primeiro Impacto](./T5000.22-primeiro-impacto.md)

## METAMORPHOSIS — escolher 1

- [T5000.31 — Cadeia Frenética](./T5000.31-cadeia-frenetica.md)
- [T5000.32 — Fluxo de Retorno](./T5000.32-fluxo-de-retorno.md)
- [T5000.33 — Transmutação Elemental](./T5000.33-transmutacao-elemental.md)

## Exemplo de configuração

`T5000.11 + T5000.22 + T5000.33`.

Isso significa: uma opção da esquerda, uma da direita e uma metamorfose ativas ao mesmo tempo.

Não é permitido usar `T5000.11 + T5000.12` simultaneamente, nem duas opções RIGHT ou duas METAMORPHOSIS.

## Referência de design

O padrão 2/2/3 do Diablo IV é uma referência útil porque produz decisões em três eixos sem exigir que a skill seja concedida pela própria árvore.

As opções exatas de Diablo mudam entre patches; portanto nomes, números e efeitos externos são fonte de ideias, não contrato do RPG Skill Tree.

A conversão elemental é especialmente relevante para este projeto porque permite adaptar uma Ability a uma build de outra afinidade. No nosso sistema essa conversão pode ser generalizada por ritual em vez de ficar limitada a uma única escola.

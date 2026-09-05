# 17.06 — Teocracia, fé e doutrinas

## Autoridade

Teocracia define fonte religiosa de legitimidade/cargos. O sistema deve integrar providers religiosos/mágicos apenas por fatos comprovados; não inventar “fé” a partir de item aleatório.

## Institution

Pode existir instituição religiosa com treasury/account próprio, templo do Stage 18, clergy offices e law proposal authority.

## Doutrinas

`DoctrineDefinition` é data-driven e pode afetar:

- eligibility para cargo;
- tithes/contribuições;
- calendário/serviços;
- permissões/proibições econômicas;
- welfare religioso;
- diplomacy modifiers do Stage 20.

Todo efeito exige adapter real.

## Dízimo

É transaction via Stage 16, com payer/base/recipient explícitos; não é remoção silenciosa de saldo.

## Pluralidade

O modelo não deve assumir uma religião real. IDs/lore são fictícios/provider-derived e localizados. Ausência de provider religioso mantém teocracia disponível apenas com definitions próprias suficientes ou marca funcionalidades específicas indisponíveis.

## Testes

- clergy eligibility;
- tithe;
- doctrine conflict;
- provider absent;
- transition removing religious office preserves history;
- district religious policy.

## Acceptance

Teocracia muda instituições/leis/finanças por contratos claros, não por bônus genérico de “fé”.
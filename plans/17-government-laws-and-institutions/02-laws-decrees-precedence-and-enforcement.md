# 17.02 — Leis, decretos, precedência e enforcement

## LawDefinition

Cada lei declara:

- ID namespaced;
- domínio (tax, labor, property, suffrage, zoning, heat, trade, religion etc.);
- parâmetros tipados;
- scope permitido;
- delegabilidade;
- conflicts/requirements;
- enforcement adapter;
- localization pt-BR.

## Resolver

Para uma consulta contextual:

1. aplicar invariantes não delegáveis;
2. lei geral realm/colony;
3. override do distrito;
4. decreto específico válido;
5. produzir `EffectiveLawSnapshot` com provenance de cada valor.

Decreto possui autoridade emissora, scope, início, expiração e motivo. Expiração é determinística e não exige apagar histórico.

## Enforcement

Lei não pode prometer consequência sem hook. Exemplos:

- imposto → Stage 16 transaction;
- zoning → Stage 15/18 validation;
- wage floor → payroll resolver;
- heat priority → Stage 19;
- suffrage → election resolver.

Sem adapter real, lei fica indisponível/diagnosticada, não “ativa sem efeito”.

## Testes

- precedence;
- nondelegable clause;
- conflicting laws;
- decree expiry;
- reload last-known-good;
- missing enforcement adapter;
- district change invalidates cache.

## Acceptance

A UI consegue explicar cada valor efetivo e sua fonte jurídica.
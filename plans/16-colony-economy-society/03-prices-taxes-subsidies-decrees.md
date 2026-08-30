# 16.03 — Preços, impostos, subsídios e decretos

## Preços

Cada `GoodCategory`/item elegível pode resolver:

```text
effectivePrice = bounded(basePrice × scarcityFactor × policyFactor + flatTaxes - subsidies)
```

A fórmula exata e seus caps são data-driven e versionados. Não usar oscilação por tick. Oferta/procura é calculada em janelas econômicas agregadas.

## Impostos

Tipos extensíveis incluem:

- renda;
- venda/consumo;
- propriedade;
- lucro/empresa;
- tarifa/tributo externo quando Stage 20 aplicar.

Cada imposto define base, alíquota, isenções, teto/piso, destino do recurso e law revision.

## Subsídios

Podem reduzir preço, reembolsar estabelecimento, apoiar setor/distrito ou financiar consumo essencial. O dinheiro sai do tesouro; ausência de caixa não gera subsídio fictício.

## Distritos e decretos

Resolver policy conforme Stage 15/17:

```text
lei geral → política distrital → decreto específico/temporário
```

A camada específica só muda parâmetros delegáveis. Decretos possuem início, expiração e provenance.

## Cobrança

Tax transaction usa receipt independente da compra, mas ambos fazem parte de uma operação composta: se a compra falhar, imposto correspondente não pode permanecer cobrado.

## Testes

- sales tax;
- exemption;
- district override;
- temporary decree expiry;
- treasury receives exact amount;
- subsidy with/without funds;
- price cap/floor;
- retry não duplica tributo.

## Acceptance

Preço final é explicável e a UI consegue decompor base, imposto, subsídio e policy.
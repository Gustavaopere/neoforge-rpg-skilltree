# A0108 — Pele de Pedra

## Estado

- **Design:** APROVADO EM FAIL-CLOSED TRANSITIVO em 2026-08-31.
- **Notion:** `3c569db9-f0db-81be-9186-f9195a54b0d6`; corrigido e verificado pós-escrita.
- **Runtime:** `UNAVAILABLE_NODE` porque A0100 Anti-Crítico permanece indisponível.

## Contrato canônico

- Gateway VITALITY + A0092 Resistência Física ≥3 + A0100 Anti-Crítico ≥2 + A0090 Têmpera ≥2.
- Quando adquirível: +15% de redução física própria, uma vez por root, e −8% movement speed.
- Benefício e penalidade são atômicos e inseparáveis.
- A contribuição física não altera Armor/Toughness e não substitui A0092.

## Bloqueio atual

A0100 exige incoming critical receipt decomposto e foi fechada como `UNAVAILABLE_NODE`. A0108 não pode eliminar ou ignorar esse predecessor; portanto nenhuma implementação parcial torna o node comprável.

## Boundary futuro

- `DamageMitigationResolver` em `LivingDamageEvent.Pre` para +15% físico.
- modifier estável em `Attributes.MOVEMENT_SPEED` para −8%.
- apply/remove/reload/respec reconciliam os dois lados na mesma transação lógica.

Protection Pixel 2.2.1 é equipamento próprio e não é provider da keystone. Epic Fight só contribui à classificação física via adapter real.

## Pendências para Chat 2

- `P-A0108-01` **BLOQUEANTE:** availability transitiva A0100→A0108.
- `P-A0108-02`: preparar/testar composição atômica benefit+penalty sem habilitar acquisition enquanto A0100 estiver indisponível.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS em fail-closed | A0100 preservado. |
| Integração global | PASS | física ≠ Armor/Toughness. |
| Qualidade/identidade | PASS | keystone com tradeoff real. |
| Topologia | PASS | convergência defensiva coerente. |
| Especializações | PASS | região Fortress. |
| PT-BR | PASS | benefício/penalidade explícitos. |
| Notion | PASS | blocker persistido. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | hooks futuros comprovados, predecessor não. |

Os 18 critérios passam em fail-closed; estado parcial é proibido.
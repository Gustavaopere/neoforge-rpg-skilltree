# A0109 — Fortaleza Ambulante

## Estado

- **Design:** APROVADO EM FAIL-CLOSED DUPLO em 2026-08-31.
- **Notion:** `3c569db9-f0db-81a5-bdcb-db7fc11c5b56`; corrigido e verificado pós-escrita.
- **Runtime:** `UNAVAILABLE_NODE` por A0108 indisponível e ausência de provider real de encumbrance corporal.

## Contrato canônico

- Gateway VITALITY + A0108 Pele de Pedra + A0091 Base Firme ≥3.
- Exige provider corporal server-authoritative com estados mapeáveis a HEAVY_LOAD e EXTREME_LOAD e provider real de Stamina regen.
- HEAVY_LOAD: +4% redução física, +0,10 knockback resistance, −10% Stamina regen.
- EXTREME_LOAD: +8%, +0,20, −20%.
- Os três eixos do estágio ativo são atômicos.

## Bloqueios atuais

1. A0108 é indisponível por A0100, portanto A0109 é transitivamente indisponível.
2. A modlist auditada não possui provider aprovado de encumbrance corporal do jogador.

Create Aeronautics Weight mede massa/física da contraption, inclusive carga transportada, mas o guia declara explicitamente que **não é um sistema de encumbrance**. Protection Pixel é equipamento e não fornece estado corporal de carga.

## Proibições

Não inferir HEAVY/EXTREME por número de itens, slots ocupados, Armor, velocidade, inventário, massa de veículo ou Sable/contraption. Epic Fight Stamina não cria o estado de carga.

## Pendências para Chat 2

- `P-A0109-01` **BLOQUEANTE:** availability A0108→A0109.
- `P-A0109-02` **BLOQUEANTE:** sem provider corporal real, node não comprável.
- `P-A0109-03`: futuro adapter deve expor estágio nativo e Stamina regen modificável; testes de atomicidade/lifecycle.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS em fail-closed | dois blockers explícitos. |
| Integração global | PASS | veículo ≠ corpo. |
| Qualidade/identidade | PASS | fortaleza condicionada a carga real. |
| Topologia | PASS | capstone/terminal Fortress. |
| Especializações | PASS | terminal exterior governado. |
| PT-BR | PASS | estados e números congelados. |
| Notion | PASS | blockers persistidos. |
| NeoVitae | PASS | ausente. |
| Providers | PASS em fail-closed | encumbrance ausente comprovado. |

Os 18 critérios passam no design pela indisponibilidade estrutural correta.
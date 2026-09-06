# A0077 — Postura Cautelosa

## Estado

- **Design:** APROVADO após correção de availability/boundary em 2026-08-31.
- **Notion:** `3c569db9-f0db-81b7-aaac-d1f391ec10aa`; Gate/Hook/Fallback/Provider/Regra corrigidos; re-fetch PASS.
- **Runtime observado:** state puro existe, mas a perk está bloqueada por A0067 indisponível e pelo binding de postura ainda ausente.

## Contrato canônico

- MARTIAL + A0067 Firmeza Ofensiva ≥2.
- 1 rank, custo 1.
- Enquanto `CAUTIOUS`: +8% resistência física elegível e −5% dano físico.
- Ocupa exclusivamente `MARTIAL_STANCE`; mutuamente exclusiva com A0076; cooldown 1,5 s.

## Availability e ativação

A0067 continua indisponível/não comprável até existir attack-window binding seguro, então A0077 herda esse bloqueio. Mesmo depois de A0067, A0077 só pode ser comprável quando o comando server-authoritative de postura definido em A0076 existir.

O mesmo controle `Alternar Postura Marcial` usa payload serverbound e validação do servidor. Com ambas disponíveis: `NONE → AGGRESSIVE → CAUTIOUS → NONE`.

## Fallback e lifecycle

Sem A0067 ou sem binding de stance: node indisponível/não comprável. Nenhum fallback contorna dependência. Resistência física não equivale a Armor, Stun Armor, Magic/Arcane/Corruption/Shroud Resistance. Limpar stance em todo lifecycle e invalidação de rank/rules.

## Pendências para Chat 2

- **P-A0077-01 BLOQUEANTE:** propagar availability de A0067 e do stance binding para purchase/gate.
- **P-A0077-02:** implementar junto ao protocolo A0076, com exclusividade/cooldown/cleanup e resistência física correta.
- **P-A0077-03:** testes de cadeia A0067→A0077, troca de stance e ausência de resíduos.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0067 + binding são obrigatórios. |
| Integração global | PASS | canal físico separado de outros sistemas. |
| Qualidade/identidade | PASS | stance defensiva com tradeoff ofensivo. |
| Topologia | PASS | Camada 3, `MARTIAL/POSTURE`. |
| Especializações | PASS | região de posturas explícita. |
| PT-BR | PASS | Texto em PT-BR. |
| Notion | PASS após correção | Re-fetch confirmado. |
| NeoVitae | PASS | Ausente. |
| Providers | PASS | RPG authority; Epic Fight não duplicado. |

Os 18 critérios passam **no design**, com indisponibilidade estrutural explícita.
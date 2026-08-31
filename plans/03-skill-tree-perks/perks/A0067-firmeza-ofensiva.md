# A0067 — Firmeza Ofensiva

## Estado de design

**APROVADA EM FAIL-CLOSED / INDISPONÍVEL ATÉ BINDING SEGURO.** O efeito é válido, mas o provider atual não expõe receipt suficiente para habilitá-lo sem heurística.

## Contrato final

- **Ranks:** 4; **custo nominal:** 1/rank.
- **Dependência:** A0066 ≥ 1 rank + gateway MARTIAL.
- **Efeito pretendido:** +4 pontos percentuais por rank, máximo 16%, de resistência à interrupção **durante uma janela ofensiva realmente ativa**, sem fornecer resistência defensiva permanente.
- O conceito não autoriza imunidade a stagger, redução de dano ou `stun armor` permanente fora do ataque.

## Availability invariant

Enquanto Epic Fight 21.17.3.1/adapter não fornecer uma combinação server-authoritative capaz de provar a janela ofensiva e modular a resistência à interrupção sem duplicar Stun Armor, **A0067 deve ficar indisponível/não comprável**. Não pode gastar pontos, armazenar rank fantasma nem aparecer como adquirida com efeito zero.

## Pendência

`P-A0067-01`: implementar binding versionado para `attack_window + offensive interruption/stun-armor` ou manter availability=false. Não inferir janela por swing visual, animação client-side, dano recebido ou movimento.

## Simply Swords

Deflect, stuns, shields, Grandsword shield-disable e abilities próprias não substituem o receipt de Firmeza Ofensiva. Provider effects continuam provider-owned.

## Testes obrigatórios

1. Sem binding: nó indisponível e compra rejeitada atomicamente sem custo.
2. Quando binding existir: benefício apenas durante janela ofensiva confirmada.
3. Fora da janela: neutro; logout/respec/rules reload não deixam modifier residual.
4. Simply/abilities não armam Firmeza por inferência.

## Nove eixos

1. Gates: PASS — A0066≥1; availability adicional obrigatória.
2. Integração: PASS apenas fail-closed hoje.
3. Qualidade: PASS — altera compromisso ofensivo, não bônus genérico permanente.
4. Topologia: PASS — continuação do corredor Impact.
5. Especialização: PASS — MARTIAL ofensiva.
6. PT-BR: PASS.
7. Registro: GitHub.
8. NeoVitae: PASS.
9. Providers: BLOQUEADA operacionalmente por `P-A0067-01`, design fechado.
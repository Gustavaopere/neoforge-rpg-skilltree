# A0068 — Dano contra Feridos

## Estado de design

**APROVADA COM BOUNDARY.** Especialização de execução sustentada contra alvos já enfraquecidos, sem substituir A0073.

## Contrato final

- **Ranks:** 3; **custo:** 1/rank.
- **Dependência:** A0061 ≥ 1 rank + gateway MARTIAL.
- **Efeito:** se a vida do alvo **antes do impacto** for <35% do máximo, o root MARTIAL direto recebe +4% de dano físico elegível por rank, máximo +12%.
- Exatamente 35% não qualifica. A leitura é server-side pre-impact; dano do próprio root não pode empurrar o alvo abaixo do limiar e retroativamente qualificar o mesmo hit.

## Authority / deduplicação

A vida é lida do `LivingEntity` no PRE/incoming stage associado ao root. Projectile usa metadata de launch/root canônica. Um root recebe o bônus no máximo uma vez, independentemente de fan-out/provider callbacks.

## Simply Swords

Execute de Scythe, Bleed, ability damage e outros resultados derivados não recebem A0068 como novo root. Se um direct root elegível já encontra o alvo <35%, A0068 aplica sua parcela uma vez; o execute provider-native resolve separadamente.

## Fail-closed

Sem snapshot pre-impact confiável ou sem autoria física direta, neutro. Herda `P-A0061-01` para classificação melee.

## Testes obrigatórios

- 34,999% qualifica; 35% não;
- a queda abaixo de 35% causada pelo hit atual não qualifica esse mesmo hit;
- melee/projectile direct roots; secondary/DoT/companion/hazard neutros;
- sem duplicação com Simply execute.

## Nove eixos

1. Gates: PASS — A0061≥1.
2. Integração: PASS — health snapshot canônico.
3. Qualidade: PASS — muda seleção/timing contra alvo ferido.
4. Topologia: PASS — ramo contextual de Força.
5. Especialização: PASS — MARTIAL universal contextual.
6. PT-BR: PASS.
7. Registro: GitHub.
8. NeoVitae: PASS.
9. Providers: PASS com derived effects provider-owned.
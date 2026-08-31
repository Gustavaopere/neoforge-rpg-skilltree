# A0111 — Conservação de Equipamento II

## Estado
**DESIGN APROVADO — FAIL-CLOSED / NÃO COMPRÁVEL enquanto A0110/P-0036 estiver indisponível.**

## Contrato
Ponte ENGINEERING↔SURVIVAL. Até 5 ranks, 1 PP/rank. Cada rank concede 1,5% de chance de preservar exatamente 1 ponto de durabilidade de equipamento tecnológico portátil elegível, até 7,5%. FE, combustível, pressão, munição e demais recursos continuam integralmente cobrados.

## Gate e provider
Exige Gateway ENGINEERING + A0110 ≥2. Como A0110 está provider-gated, A0111 herda o bloqueio. Depois de A0110 existir, cada família ainda precisa de adapter versionado pós-regras nativas/pré-decremento. Oritech 1.2.11 e Protection Pixel só entram por item durável concreto; presença do mod não basta.

## Hook
Mesmo boundary residual de durabilidade de A0110: prevenção nativa/Unbreaking primeiro, uma única rolagem A0111 sobre decremento final confirmado de 1. Nunca converter energia em durability-equivalent.

## Chat 2
Implementar availability transitiva; allocation legado vale 0 PP enquanto gate ausente. Testar FE-only, unbreakable, Unbreaking e callbacks duplicados.
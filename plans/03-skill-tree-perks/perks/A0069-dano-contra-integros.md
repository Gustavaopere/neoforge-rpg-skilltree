# A0069 — Dano contra Íntegros

## Estado de design

**APROVADA COM BOUNDARY.** Especialização de abertura contra alvos quase íntegros.

## Contrato final

- **Ranks:** 3; **custo:** 1/rank.
- **Dependência:** A0061 ≥ 1 rank + gateway MARTIAL.
- **Efeito:** se a vida do alvo **antes do impacto** for >85% do máximo, o root MARTIAL direto recebe +4% de dano físico elegível por rank, máximo +12%.
- Exatamente 85% não qualifica. O estado é amostrado antes do dano do próprio root.

## Authority / deduplicação

Snapshot server-side de vida + rootActionId canônico. O bônus não é uma marca persistente e não precisa ser guardado no alvo após a resolução.

## Simply Swords

Double damage, backstab, ability damage, gem powers ou hits delegados não abrem um novo A0069 por serem originados da mesma arma. O direct root pode receber A0069 uma vez se o alvo já estava >85%; resultados derivados permanecem provider-owned.

## Fail-closed

Sem pre-impact health confiável ou sem autoria física direta, neutro. Herda `P-A0061-01` para classificação melee.

## Testes obrigatórios

- >85% qualifica; 85% exato não;
- snapshot antes do hit;
- direct melee/projectile apenas;
- fan-out/derived Simply não duplica o bônus.

## Nove eixos

1. Gates: PASS — A0061≥1.
2. Integração: PASS — health snapshot canônico.
3. Qualidade: PASS — privilegia opener e escolha de alvo íntegro.
4. Topologia: PASS — ramo contextual de Força.
5. Especialização: PASS — MARTIAL universal contextual.
6. PT-BR: PASS.
7. Registro: GitHub.
8. NeoVitae: PASS.
9. Providers: PASS com provider-derived exclusões.
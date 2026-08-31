# A0066 — Impacto Marcial

## Estado de design

**APROVADA COM BOUNDARY.** Corredor de controle físico por Impact.

## Contrato final

- **Ranks:** 4; **custo:** 1/rank.
- **Dependência:** A0061 ≥ 1 rank + gateway MARTIAL.
- **Efeito:** +3% de Impact provider-native por rank, máximo +12%, somente quando a ação direta expõe uma surface de Impact semanticamente segura.
- Não altera dano, knockback vanilla, velocidade de projétil ou stun por heurística.

## Authority / hooks

Epic Fight `EpicFightDamageSource.attachImpactModifier` é a surface canônica melee. No pipeline projectile vanilla atual não há receipt de Impact; nessa rota A0066 é neutra, sem fabricar equivalência.

## Simply Swords

Stun, knockback, Mecha Smite/Pulse, ability hit e traits de arma não são automaticamente `Impact` RPG. Se o provider já modifica Impact no root, A0066 acrescenta apenas sua parcela uma vez no mesmo pipeline.

## Fail-closed

Sem Impact provider-native, efeito daquela ação = neutro. Herda `P-A0061-01` para classificação melee.

## Testes obrigatórios

- multiplicadores 1.03/1.06/1.09/1.12 em melee compatível;
- projectile sem Impact = neutro;
- não converter knockback/stun/ability Simply em Impact;
- uma aplicação/root.

## Nove eixos

1. Gates: PASS — A0061≥1.
2. Integração: PASS — Impact Epic Fight.
3. Qualidade: PASS — corredor de stagger/controle, distinto de dano e penetração.
4. Topologia: PASS — conduz a A0067.
5. Especialização: PASS — MARTIAL Impact.
6. PT-BR: PASS.
7. Registro: GitHub.
8. NeoVitae: PASS.
9. Providers: PASS com fail-closed em rotas sem Impact.
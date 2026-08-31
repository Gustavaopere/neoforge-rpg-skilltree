# A0065 — Penetração Física

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-8161-a88b-f54c0f924ae1`; fetch fresco em 2026-08-31 sem drift.
- **Runtime observado:** CÓDIGO PRESENTE para Epic Fight e projéteis físicos; confirmação definitiva pertence ao Chat 2.

## Contrato canônico

- Gateway MARTIAL + A0061 Força Aplicada ≥ 2 ranks.
- 4 ranks, 1 ponto por rank.
- +2% de penetração física por rank, máximo próprio de +8%.
- Só reduz a parcela de mitigação física elegível do impacto atual.
- Não remove Armor do alvo e não aplica debuff/shred persistente.

## Provider / authority / boundary

- Epic Fight 21.17.3.1 pode usar `ARMOR_NEGATION`/armor-negation do próprio `EpicFightDamageSource`.
- Apothic Attributes 2.10.1 armor pierce pode ser backend alternativo do resolvedor físico, mas a mesma contribuição A0065 não pode entrar por dois backends.
- Pufferfish's Attributes shred é grandeza distinta; não substitui penetração.
- Rapier/Spear de Simply Swords mantêm armor ignore provider-native; A0065 acrescenta apenas sua contribuição própria, uma vez.

## Evidência runtime

`A0061A0080CombatPolicy.beforePhysicalHit(...)` calcula a contribuição A0065. O adapter Epic Fight usa `attachArmorNegationModifier(...)`; a ponte de projéteis reduz somente a redução de Armor no `DamageContainer`, sem mutar permanentemente o atributo do alvo.

## Fallback e fail-closed

Sem estágio seguro de penetração/armor-negation, A0065 fica inativa naquele provider. Nunca converter para dano, armor shred, debuff ou redução permanente.

## Anti-abuso, causalidade e deduplicação

- Uma contribuição A0065 por impacto causal elegível.
- Armor ignore provider-native e A0065 são identidades distintas; cada contribuição autorizada entra exatamente uma vez.
- Não gera Mastery.

## Pendências para Chat 2

- **P-A0065-01:** validar composição Epic Fight/projétil e provar que a contribuição A0065 não é aplicada por dois backends simultâneos.
- **P-A0065-02:** regression com Simply Swords deve provar que armor ignore nativo não é copiado/retraduzido pela árvore.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | A0061 ≥2 cria avanço coerente. |
| 2. Integração global | PASS | Penetração permanece distinta de shred e mitigação mágica. |
| 3. Qualidade/identidade | PASS | Especializa ofensiva contra Armor sem debuff persistente. |
| 4. Topologia | PASS | Camada 2, `MARTIAL/CORE_PENETRATION`. |
| 5. Especializações | PASS | Universal MARTIAL; não invade provider. |
| 6. PT-BR | PASS | Texto em PT-BR. |
| 7. Notion completo | PASS | Campos completos e fetch fresco. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS | Epic Fight/Apothic/Pufferfish/Simply Swords reconciliados. |

Os 18 critérios técnicos cumulativos passam **no design**, com fail-closed obrigatório sem backend seguro.
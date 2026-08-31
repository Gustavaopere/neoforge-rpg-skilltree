# A0066 — Impacto Marcial

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-81d2-856d-fa8e74f25fa0`; fetch fresco em 2026-08-31 sem drift.
- **Runtime observado:** CÓDIGO PRESENTE para melee Epic Fight; projéteis permanecem corretamente fail-closed sem receipt de Impact.

## Contrato canônico

- Gateway MARTIAL + A0061 Força Aplicada ≥ 1 rank.
- 4 ranks, 1 ponto por rank.
- +3% de impacto/interrupção causada por rank, máximo próprio de +12%.
- Só existe quando o golpe físico concreto possui grandeza provider-native semanticamente equivalente a Impact/interrupção.
- Impact não é knockback, dano adicional, Stun Armor ou stun garantido.

## Provider / authority / boundary

- Epic Fight 21.17.3.1 é provider principal através de `EpicFightAttributes.IMPACT`/impact modifier no damage source.
- Weapons of Miracles 2.0.176 só participa para armas/skills concretas cujo golpe use semântica de Impact compatível.
- Punchy 2.7d e Punchy Epic Fight Compat são visuais/compatibilidade, não providers mecânicos.
- Para projéteis vanilla, ausência de receipt de Impact mantém esta parcela inativa.

## Evidência runtime

`A0061A0080CombatPolicy.beforePhysicalHit(...)` calcula o multiplicador A0066. `A0061A0080EpicFightHooks` o aplica via `attachImpactModifier(...)` no PRE do Epic Fight. `A0041A0060ProjectileEvents` documenta explicitamente que não fabrica Impact para projéteis sem receipt provider-native.

## Fallback e fail-closed

Sem contrato equivalente de Impact/interrupção, multiplicador A0066 = 1.0. É proibido substituir por knockback, dano, stagger artificial ou outro efeito aproximado.

## Anti-abuso, causalidade e deduplicação

- Uma contribuição de Impact por root action físico elegível.
- Procs/skills provider-native continuam no próprio provider; A0066 não os reexecuta.
- Não gera Mastery.

## Pendências para Chat 2

- **P-A0066-01:** validar melee provider-present e deduplicação de `attachImpactModifier`.
- **P-A0066-02:** preservar fail-closed para projectile ou qualquer provider sem receipt explícito de Impact.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | A0061 ≥1 + MARTIAL. |
| 2. Integração global | PASS | Impact permanece separado de knockback/stun/dano. |
| 3. Qualidade/identidade | PASS | Cria ramo de controle físico provider-native. |
| 4. Topologia | PASS | Camada 2, `MARTIAL/CORE_CONTROL`. |
| 5. Especializações | PASS | WoM só quando ação concreta expõe semântica compatível. |
| 6. PT-BR | PASS | Texto em PT-BR. |
| 7. Notion completo | PASS | Campos completos e fetch fresco. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS | Epic Fight/WoM/Punchy classificados corretamente; projectile fail-closed. |

Os 18 critérios técnicos cumulativos passam **no design**.
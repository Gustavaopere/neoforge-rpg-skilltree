# A0105 — Casca Reativa

## Estado

- **Design:** APROVADO após hardening em 2026-08-31.
- **Notion:** `3c569db9-f0db-8177-94c5-f243b0de0e26`; corrigido e verificado pós-escrita.
- **Runtime:** state consumer e modifiers temporários ainda precisam ser implementados.

## Contrato canônico

- Gateway VITALITY + A0089≥3 + A0090≥2.
- 3 eventos de dano direto hostil elegível confirmados em janela deslizante de 80 ticks.
- Ativação ocorre depois do terceiro hit: por 120 ticks, +15% relativo sobre Armor existente e +8% relativo sobre Toughness existente.
- O terceiro hit não é mitigado retroativamente.
- Cooldown 400 ticks; hits durante o estado não renovam duração.
- Base Armor/Toughness zero permanece zero.

## Boundary de implementação

Contar em `LivingDamageEvent.Post` somente perda de vida >0 com atacante causal hostil. Cada `DamageContainer` conta uma vez. Adapter com `rootActionId` provider-native pode deduplicar sequências comprovadamente pertencentes à mesma ação; não criar dedup por mesmo tick/atacante/animação.

Modifiers temporários devem reutilizar a semântica relativa já adotada por A0089/A0090 e IDs estáveis, com apply/remove atômicos.

## Cobertura de providers

- Minecraft/NeoForge: Armor/Toughness e Post.
- Epic Fight: fontes de combate convergem ao mesmo pipeline; Stun Armor não é Toughness.
- Protection Pixel: equipamento próprio, não provider genérico do estado reativo.

## Pendências para Chat 2

- `P-A0105-01`: janela 3/80 ticks, ativação pós-terceiro hit, cooldown e dedup causal.
- `P-A0105-02`: modifiers +15/+8 relativos com lifecycle morte/logout/dimensão/rank loss/respec/rules reload.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Armor/Toughness progressão coerente. |
| Integração global | PASS | sem Stun Armor/guard paralelo. |
| Qualidade/identidade | PASS | defesa reativa temporal. |
| Topologia | PASS | notable anti-burst. |
| Especializações | PASS | N/A. |
| PT-BR | PASS | ordem temporal explícita. |
| Notion | PASS | hardening persistido. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | Post/attributes reais. |

Os 18 critérios passam; não há dedup heurístico nem bônus retroativo.
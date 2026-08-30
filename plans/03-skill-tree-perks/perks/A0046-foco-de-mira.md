# A0046 — Foco de Mira

## Estado

- **Design:** APROVADO após boundary retroativo de BOW/corpo/impacto.
- **Implementação:** PARCIAL; `P-A0046-01` e `P-A0046-02` abertas.
- **Notion:** `3c569db9-f0db-8116-9caa-e1ef0b2867d5`.

## Contrato canônico

- A0045 ≥2 + gateway `epic_bow`; 2 ranks.
- Foco é recurso RPG transitório 0–100.
- Mira estável com BOW tensionado, sem sprint/interrupção: +8 Foco/s no rank 1; rank 2 aplica +25% apenas aos ganhos.
- Hit BOW confirmado a ≥12 blocos da origem registrada: +10/+12,5 Foco uma vez por projétil.
- Perdas: heavy impact hostil −25; cancelamento ≥80% −15; sprint com arco em uso −12/s; mudança angular acumulada >45°/5 ticks −10 com intervalo interno de 0,5 s.
- Temperatura/hidratação/exhaustion só modulam via estado corporal canônico e nunca se substituem entre si.

## Evidência runtime

- `A0041A0060ProjectileEvents.tickAim(...)` implementa stable aim, sprint drain, cancelamento ≥80% e abrupt aim.
- `onDamagePost(...)` credita acerto distante uma vez por projétil/root.
- `A0041A0060CombatPolicy.loseFocusForHeavyImpact(...)` existe, mas não há caller produtivo comprovado.
- Não há adapter de Cold Sweat/Thirst/body scalar no bridge A0041–A0060.

## Provider→árvore

- **Volcanoes:** pode influenciar temperatura somente indiretamente pelo estado Cold Sweat já atualizado por sua bridge; A0046 não lê Atmosphere, gases, pressão ou heat source diretamente.
- **Enshrouded:** Shroud/Exposure/Madness não são Foco nem escalares corporais desta perk.
- **Black Arcana:** Strain/Corruption/Backlash não substituem temperatura, hidratação, exhaustion ou Focus.
- **Mobstein:** companion projectile não gera Foco para o dono.
- **RPG Skill Tree:** owner exclusivo de Foco; Stage 11 itemização não é producer.

## Pendências Chat 2

### P-A0046-01 — heavy impact receipt

Conectar receipt hostil server-authoritative de impacto/stagger pesado e aplicar exatamente −25 uma vez por outcome. Dano bruto/hit comum não qualifica.

### P-A0046-02 — escalares do estado corporal

Conectar somente adapters reais/canônicos de Cold Sweat temperatura, Thirst hidratação e Minecraft/NeoForge exhaustion. Na ausência de qualquer eixo, ignorar apenas seu escalar. Não inferir hidratação de exhaustion nem temperatura de clima/bioma/Volcanoes direto.

## Testes exigidos

- todos os producers/perdas isolados e combinados;
- heavy comum vs confirmado;
- BOW classifier único;
- 12 blocos/origem registrada;
- corpo provider presente/ausente por eixo;
- Volcanoes apenas via Cold Sweat;
- lifecycle logout/dimension;
- multiplayer/dedup.

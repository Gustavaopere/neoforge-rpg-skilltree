# A0046 — Foco de Mira

## Estado

- **Design:** APROVADO após boundary retroativo de BOW/corpo/impacto.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**, com componentes heavy-impact/body scalar mantidos fail-closed onde não há receipt seguro.
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
- `A0041A0060CombatPolicy.loseFocusForHeavyImpact(...)` existe, mas permanece sem caller produtivo porque não há receipt heavy-impact seguro no boundary atual.
- não há adapter corporal completo para Cold Sweat/Thirst/exhaustion neste bridge; cada eixo ausente continua simplesmente omitido, conforme contrato.
- esses componentes ausentes não anulam os producers/perdas independentes já seguros, portanto A0046 permanece comprável e funcional sem inventar substitutos.

## Provider→árvore

- **RPG Skill Tree:** owner exclusivo de Foco e de seus consumers seguros.
- **Minecraft/NeoForge:** bow use, sprint, rotação, release e POST de projétil.
- **Volcanoes:** pode influenciar temperatura somente indiretamente pelo estado Cold Sweat; A0046 não lê Atmosphere, gases, pressão ou heat source diretamente.
- **Enshrouded:** Shroud/Exposure/Madness não são Foco nem escalares corporais desta perk.
- **Black Arcana:** Strain/Corruption/Backlash não substituem temperatura, hidratação, exhaustion ou Focus.
- **Mobstein:** companion projectile não gera Foco para o dono.
- **Stage 11 itemização:** não é producer.

## Pendências técnicas preservadas

### P-A0046-01 — heavy impact receipt

Continua fail-closed. Conectar somente quando houver receipt hostil server-authoritative de impacto/stagger pesado; dano bruto/hit comum não qualifica.

### P-A0046-02 — escalares do estado corporal

Conectar somente adapters reais/canônicos de Cold Sweat temperatura, Thirst hidratação e Minecraft/NeoForge exhaustion. Na ausência de qualquer eixo, ignorar apenas seu escalar.

## Pendência Chat 3

- validar todos os producers já ativos, dedup do distant hit e limites 0–100;
- validar que hit comum não dispara heavy-impact loss;
- validar ausência de escalares corporais sem efeitos colaterais;
- validar lifecycle logout/dimension e multiplayer.

## Testes exigidos

- todos os producers/perdas isolados e combinados;
- heavy comum vs confirmado;
- BOW classifier único;
- 12 blocos/origem registrada;
- corpo provider presente/ausente por eixo;
- Volcanoes apenas via Cold Sweat;
- lifecycle logout/dimension;
- multiplayer/dedup.

## Fechamento Chat 2 — 2026-09-01

O Chat 2 preservou fail-closed componente a componente em vez de bloquear a perk inteira ou fabricar receipts. A bateria final permanece do Chat 3.

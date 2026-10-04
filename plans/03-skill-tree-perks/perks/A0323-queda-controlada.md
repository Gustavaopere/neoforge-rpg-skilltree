# A0323 — Queda Controlada

## Estado

- **Chat 1:** DESIGN APROVADO.
- **Disponibilidade atual:** IMPLEMENTÁVEL.
- **Fonte canônica:** Notion `A0323` — https://app.notion.com/3c569db9f0db81d7b3e1f2a9dc7e9214
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0323 reduz exclusivamente dano explicitamente classificado como `FALL`, em **−6% por rank**, até −24%.

- rank 1: amount ×0,94;
- rank 2: amount ×0,88;
- rank 3: amount ×0,82;
- rank 4: amount ×0,76.

A perk atua no dano de queda que efetivamente chega ao boundary mutável. Não reexecuta breakfall/roll, enchantment, armor ou qualquer proteção upstream.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0318 Passo Leve ≥2.

Gate runtime: `LivingDamageEvent.Pre` server-side, amount > 0 e `DamageSource` explicitamente classificado/tagueado como `DamageTypes.FALL`.

Void, fly-into-wall, esmagamento, projétil, veículo e danos custom não entram por nome ou similaridade.

## Providers e authority

- Minecraft/NeoForge 1.21.1: authority de `DamageTypes.FALL` e do boundary `LivingDamageEvent.Pre`.
- ParCool 4.0.0.3 pode executar breakfall/roll upstream; A0323 não imita nem duplica essa mecânica.
- Epic ParCool 21.0.0 / Epic Fight 21.17.3.1 só alteram o resultado se sua mecânica real já tiver produzido efeito upstream.
- RPG Skill Tree: owner da contribuição A0323 e de sua aplicação única.

A `main` auditada não possui serviço genérico reutilizável de claim/idempotência por incoming outcome. Portanto Chat 2 deve garantir uma única contribuição A0323 no próprio adapter/event boundary, sem depender de helper inexistente e sem criar segundo pipeline de dano.

## Hook e pipeline canônico

Em `LivingDamageEvent.Pre`, após providers upstream determinarem o dano que chegou ao evento e antes do commit final de vida:

1. classificar explicitamente `DamageTypes.FALL`;
2. ler rank A0323;
3. calcular `amount × (1 − 0.06 × rank)`;
4. substituir o amount uma única vez;
5. nunca produzir valor negativo ou cura.

Dano zero permanece zero.

## Deduplicação

Na rota atual, o próprio `LivingDamageEvent.Pre` é o boundary primário. Se múltiplos adapters futuros convergirem no mesmo incoming outcome, deve existir identidade local explícita antes de compor; não presumir serviço genérico já existente.

A mesma queda não pode receber A0323 duas vezes por bridges paralelas.

## Fallback / fail-closed

Se o source não puder ser classificado como FALL com segurança ou a aplicação única não puder ser garantida, omitir A0323 naquele outcome.

É proibido substituir por Feather Falling, Slow Falling, roll automático, alteração de `fallDistance`, cancelamento de landing ou invulnerabilidade.

## Anti-abuso e boundaries

- não reconstruir queda por delta de HP pós-fato;
- não reexecutar breakfall/roll;
- não aplicar em void ou fly-into-wall;
- nenhuma redução gera Mastery;
- dano já reduzido a zero nunca vira heal/refund;
- bridges ParCool/Epic Fight não reaplicam o multiplicador.

## Testes destinados ao Chat 3

1. FALL positivo em ranks 1–4 resulta ×0,94/0,88/0,82/0,76;
2. rank 0 não altera amount;
3. dano zero permanece zero;
4. void/fly-into-wall/projétil/esmagamento não recebem A0323;
5. breakfall/roll upstream é respeitado e não reexecutado;
6. evento/outcome recebe exatamente uma contribuição;
7. nenhuma cura/valor negativo é produzido;
8. respec/rank loss remove efeito imediatamente;
9. multiplayer: aplicação isolada por vítima/rank;
10. dedicated-server smoke com ParCool/Epic Fight presentes e ausentes.

## Handoff Chat 2

Implementar no boundary NeoForge real `LivingDamageEvent.Pre`, classificar somente `DamageTypes.FALL` e manter exatamente uma multiplicação A0323 por incoming outcome. Não criar pipeline paralelo de mitigação.

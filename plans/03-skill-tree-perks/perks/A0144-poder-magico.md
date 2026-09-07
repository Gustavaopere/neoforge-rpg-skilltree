# A0144 — Poder Mágico

## Estado Chat 1

**DESIGN APROVADO.**

A perk é implementável nos canais provider-native comprovados. O design não cria um segundo atributo global `MAGIC_POWER`; ele reutiliza a authority nativa de cada provider e aplica A0144 uma única vez por outcome elegível.

## Contrato

- Domínio/árvore: ARCANE / Principal — ARCANE.
- Ramo: Fundamentos — Potência Mágica; camada 1; Tronco.
- Até 5 ranks; 1 PP/rank.
- Gateway ARCANE obrigatório.
- +2% de potência mágica por rank: +2% / +4% / +6% / +8% / +10%.
- Cada outcome mágico elegível diretamente atribuível ao jogador recebe A0144 no máximo uma vez antes de camadas específicas de escola/especialização.

## Authority e providers

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot auditado: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

`AbstractSpell.getSpellPower()` e `getEntityPowerMultiplier()` consomem `AttributeRegistry.SPELL_POWER`. Portanto o caminho canônico é adicionar um modifier estável sobre o atributo nativo; não registrar atributo paralelo no Skill Tree.

### Ars Nouveau 5.13.1

Snapshot auditado: `baileyholl/Ars-Nouveau@112920ff774831f204031da75b4c4e73d3765157`.

`SpellDamageEvent.Pre` expõe `caster`, `context`, `target` e `damage` mutável. Esse é o canal aprovado para dano de spell. Cura/utility do Ars não possuem seam equivalente aprovado neste ciclo e permanecem fora da perk.

### Outros providers

Goety, Malum, Eidolon: Repraised, Hexalia, Black Arcana e demais sistemas só entram quando adapter explícito provar outcome mágico server-authoritative, owner_player e deduplicação. Instalação do mod não basta.

## Hooks aprovados

### Iron's

`NodeEffectRuntime → modifier estável em AttributeRegistry.SPELL_POWER`.

Operação percentual semanticamente equivalente a `ADD_MULTIPLIED_BASE`, valor `0,02 × rank`. O effectId deve ser estável e refresh idempotente.

### Ars Nouveau

`SpellDamageEvent.Pre` server-side; o caster deve corresponder ao jogador elegível.

`event.damage = event.damage × (1 + 0,02 × rank)`

Aplicar uma vez. Não repetir a mesma contribuição em `LivingHurt`, DamageSource genérico ou callback derivado do mesmo hit.

## Causalidade e deduplicação

Adapters adicionais devem preservar `provider_id`, `cast/action_id`, `owner_player` e `outcome_id` quando essas identidades existirem. Lacaios, servants, automação, rituais persistentes e outcomes derivados não herdam A0144 por aproximação.

## Fallback

Sem hook seguro para provider/outcome, omitir apenas aquele canal. Se nenhum canal suportado estiver presente, a perk não deve ser apresentada como funcional. Nunca substituir por dano físico genérico, mana, Source, Soul Energy, HP ou outro recurso.

## Handoff Chat 2

- Iron's: usar somente `SPELL_POWER` nativo;
- Ars: handler de `SpellDamageEvent.Pre` com dedup e player authority;
- não implementar Ars healing/utility sem novo contrato;
- não adicionar segundo atributo global MAGIC_POWER;
- providers adicionais somente por adapters explícitos.

## Testes Chat 3

1. ranks +2/+4/+6/+8/+10%;
2. Iron's modifier estável e idempotente após login/respec/reload/equipment refresh;
3. Ars `SpellDamageEvent.Pre` multiplicado uma vez;
4. nenhum double-apply via LivingHurt/DamageSource;
5. caster não-player/owner indireto não recebe bônus sem adapter específico;
6. Ars healing/utility permanece inalterado;
7. provider absent/removed fail-soft por canal;
8. school/specialization layers continuam separadas;
9. multiplayer e callbacks duplicados preservam dedup.
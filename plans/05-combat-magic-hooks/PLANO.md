# PLANO — 05 Combat & Magic Hooks

Estado: **EM ANDAMENTO**.

## Objetivo

Definir pontos canônicos para melee, projéteis, magia, cura e summons, garantindo que perks sejam aplicadas uma vez e que integrações possam compor sem double-dipping.

## Dependências

01 RPG Core e efeitos do 03.

## Etapas de implementação

### 1 — Modelo de contexto
- [ ] representar atacante/source/owner/alvo/tipo de ação;
- [ ] distinguir dano base, bônus aditivo, multiplicador e dano final;
- [ ] preservar origem de projéteis e summons.

### 2 — Melee
- [ ] escolher evento canônico vanilla;
- [ ] Epic Fight substitui o fallback quando ele é a fonte confirmada;
- [ ] exatamente uma rolagem para efeitos once-per-hit.

### 3 — Projéteis
- [ ] snapshot ou resolução no impacto definidos explicitamente;
- [ ] evitar perda de owner e bônus após longa distância/dimension edge cases.

### 4 — Magia
- [ ] contratos comuns para spell power, mana, cooldown, cast speed e crit;
- [ ] harmonizar Ars + Iron's simultâneos sem aplicar o mesmo bônus duas vezes;
- [ ] casts cancelados/falhos não concedem mastery.

### 5 — Cura e suporte
- [ ] origem da cura e receptor preservados;
- [ ] healing modifiers e overheal têm ordem definida.

### 6 — Summons
- [ ] owner é fonte de perks/mastery quando apropriado;
- [ ] entidades sem owner têm fallback neutro;
- [ ] despawn/reload não duplica estado.

## Testes

- [ ] vanilla isolado;
- [ ] Epic Fight isolado e junto de outras integrações;
- [ ] Iron's + Ars simultâneos;
- [ ] projectile/summon ownership;
- [ ] canceled events.

## Definição de concluído

Pipelines canônicos e cobertura de double-application fechados; então `PLANO-✅.md`.
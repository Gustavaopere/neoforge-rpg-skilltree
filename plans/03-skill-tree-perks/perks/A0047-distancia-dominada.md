# A0047 — Distância Dominada

## Estado

- **Design:** APROVADO; sem mutação no Notion nesta retroauditoria.
- **Implementação:** DIVERGENTE/PARCIAL; `P-A0047-01` aberta para projectile-speed provider.
- **Notion:** `3c569db9-f0db-8125-8454-da7f3c95587f`.

## Contrato canônico

- A0044 ≥2 + A0045 ≥1 + gateway `epic_bow`; 2 ranks.
- Disparo totalmente tensionado + ≥25 Foco + ≥0,5 s de mira estável pode comprometer 25 Foco.
- Com provider seguro de launch/projectile speed: +10%/+15% no lançamento.
- No impacto do mesmo projétil a ≥12 blocos da origem registrada: +8%/+12% de penetração física per-hit.
- O custo só ocorre quando ao menos um componente seguro pode ser aplicado; errar/acertar curto não devolve o custo de um disparo especial validamente criado.

## Evidência runtime

- `tryDominatedShot(...)` modela corretamente a condição `projectileSpeedAvailable || penetrationAvailable`, custo e root action.
- `A0041A0060ProjectileEvents.onArrowLoose(...)` atualmente passa `projectileSpeedAvailable=true` e `penetrationAvailable=true` incondicionalmente para BowItem.
- `onEntityJoin(...)` aplica `arrow.setDeltaMovement(...scale(launchSpeedMultiplier))` diretamente.
- O impacto usa redução ARMOR per-hit, sem modifier persistente, e mantém origem registrada.

## Provider→árvore

- Nenhum projeto próprio retroauditado fornece projectile-speed BOW para A0047.
- Stage 11 itemização não projeta esse efeito.
- Pufferfish/Apothic citados no design só podem participar por adapter semântico/versionado e sem double-processing.
- Backlash, Shroud e companions não herdam a marca do disparo.

## Pendência Chat 2

### P-A0047-01 — omitir speed sem provider semântico seguro

Não declarar `projectileSpeedAvailable=true` por existir um `AbstractArrow`. Integrar capability/atributo server-authoritative realmente selecionado para launch speed ou manter `false`. Sem esse provider, preservar apenas a penetração segura; o disparo ainda pode consumir 25 Foco porque ao menos esse componente continua disponível. Não usar `setDeltaMovement` como substituto genérico do provider contratado.

## Testes exigidos

- provider speed ausente → sem speed, penetração ativa;
- provider presente → +10/+15 exatamente uma vez;
- coexistência sem double-scale;
- hit <12 / ≥12;
- miss sem refund;
- derived/ricochet/spell/companion sem herança;
- mesma origem/root action.

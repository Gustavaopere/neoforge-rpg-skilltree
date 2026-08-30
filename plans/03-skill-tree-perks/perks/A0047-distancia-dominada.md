# A0047 — Distância Dominada

## Estado

- **Design:** APROVADO após explicitar a propagação de availability de A0044 no review da PR #243.
- **Implementação:** DIVERGENTE/PARCIAL; `P-A0047-01` aberta para projectile-speed provider e a aquisição permanece condicionada ao availability gate de A0044.
- **Notion:** `3c569db9-f0db-8125-8454-da7f3c95587f`; corrigido e re-fetch PASS em 2026-08-30.

## Contrato canônico

- A0044 ≥2 + A0045 ≥1 + gateway `epic_bow`; 2 ranks.
- A dependência de A0044 é estrutural. Se A0044 estiver indisponível/não comprável por ausência de draw/preparation-speed binding, A0047 também fica indisponível/não comprável; não existe bypass automático.
- Disparo totalmente tensionado + ≥25 Foco + ≥0,5 s de mira estável pode comprometer 25 Foco.
- Com provider seguro de launch/projectile speed: +10%/+15% no lançamento.
- No impacto do mesmo projétil a ≥12 blocos da origem registrada: +8%/+12% de penetração física per-hit.
- O custo só ocorre quando ao menos um componente seguro pode ser aplicado e o próprio nó estiver legitimamente disponível; errar/acertar curto não devolve o custo de um disparo especial validamente criado.

## Evidência runtime

- `tryDominatedShot(...)` modela corretamente a condição `projectileSpeedAvailable || penetrationAvailable`, custo e root action.
- `A0041A0060ProjectileEvents.onArrowLoose(...)` atualmente passa `projectileSpeedAvailable=true` e `penetrationAvailable=true` incondicionalmente para BowItem.
- `onEntityJoin(...)` aplica `arrow.setDeltaMovement(...scale(launchSpeedMultiplier))` diretamente.
- O impacto usa redução ARMOR per-hit, sem modifier persistente, e mantém origem registrada.
- O runtime ainda não possui availability gate provider-aware para A0044; portanto a cadeia A0044→A0047 não representa corretamente o estado indisponível exigido pelo design.

## Provider→árvore

- Nenhum projeto próprio retroauditado fornece projectile-speed BOW para A0047.
- Stage 11 itemização não projeta esse efeito.
- Pufferfish/Apothic citados no design só podem participar por adapter semântico/versionado e sem double-processing.
- Backlash, Shroud e companions não herdam a marca do disparo.

## Pendências Chat 2

### P-A0047-01 — omitir speed sem provider semântico seguro

Não declarar `projectileSpeedAvailable=true` por existir um `AbstractArrow`. Integrar capability/atributo server-authoritative realmente selecionado para launch speed ou manter `false`. Sem esse provider, preservar apenas a penetração segura quando A0047 estiver disponível; não usar `setDeltaMovement` como substituto genérico do provider contratado.

### Dependência de P-A0044-01

A0047 não pode ser comprada se A0044 não puder ser legitimamente comprada. Implementar o availability gate de A0044 antes de considerar a aquisição de A0047 conforme. Qualquer reroute de A0047 para contornar A0044 exige novo design do Chat 1; Chat 2 não pode inventá-lo.

## Testes exigidos

- A0044 indisponível → A0047 indisponível/não comprável;
- provider speed ausente, mas A0044 legitimamente disponível por provider próprio → sem speed, penetração ativa;
- provider speed presente → +10/+15 exatamente uma vez;
- coexistência sem double-scale;
- hit <12 / ≥12;
- miss sem refund;
- derived/ricochet/spell/companion sem herança;
- mesma origem/root action.

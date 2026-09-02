# A0047 — Distância Dominada

## Estado

- **Design:** APROVADO após explicitar a propagação de availability de A0044 no review da PR #243.
- **Implementação:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Disponibilidade:** `UNAVAILABLE_NODE` enquanto A0044 estiver indisponível; A0048 herda esse bloqueio estrutural.
- **Notion:** `3c569db9-f0db-8125-8454-da7f3c95587f`; corrigido e re-fetch PASS em 2026-08-30.

## Contrato canônico

- A0044 ≥2 + A0045 ≥1 + gateway `epic_bow`; 2 ranks.
- A dependência de A0044 é estrutural. Se A0044 estiver indisponível/não comprável por ausência de draw/preparation-speed binding, A0047 também fica indisponível/não comprável; não existe bypass automático.
- Disparo totalmente tensionado + ≥25 Foco + ≥0,5 s de mira estável pode comprometer 25 Foco.
- Com provider seguro de launch/projectile speed: +10%/+15% no lançamento.
- No impacto do mesmo projétil a ≥12 blocos da origem registrada: +8%/+12% de penetração física per-hit.
- O custo só ocorre quando ao menos um componente seguro pode ser aplicado e o próprio nó estiver legitimamente disponível.

## Evidência runtime

- `CombatPerkAvailabilityRuntime` marca A0047 indisponível por dependência estrutural de A0044.
- `A0041A0060RuntimeState.ranks(...)` mascara qualquer rank legado de A0047.
- `A0041A0060ProjectileEvents.onArrowLoose(...)` agora passa `projectileSpeedAvailable=false`; presença de `AbstractArrow` não é mais tratada como capability de launch speed.
- o bridge não chama mais `arrow.setDeltaMovement(...scale(...))` para A0047.
- a penetração per-hit existente permanece modelada no policy para uma futura reativação legítima, sem modifier persistente.

## Provider→árvore

- Nenhum projeto próprio retroauditado fornece projectile-speed BOW para A0047.
- Stage 11 itemização não projeta esse efeito.
- Pufferfish/Apothic só poderão participar por adapter semântico/versionado e sem double-processing.
- Backlash, Shroud e companions não herdam a marca do disparo.

## Pendências históricas

### P-A0047-01 — speed sem provider

Resolvida no runtime atual por omissão: speed declarado como indisponível e fallback `setDeltaMovement` removido.

### Dependência de P-A0044-01

Continua impondo indisponibilidade integral de A0047 até A0044 ser legitimamente habilitada por provider de preparation speed.

## Pendência Chat 3

- validar A0047 não comprável enquanto A0044 estiver unavailable;
- validar nenhum gasto de Focus/rank legado enquanto indisponível;
- validar ausência de double-scale/projectile-speed sintético;
- quando houver provider futuro, validar penetração e speed separadamente conforme capability real.

## Testes exigidos

- A0044 indisponível → A0047 indisponível/não comprável;
- provider speed ausente, mas A0044 futuramente disponível por provider próprio → sem speed, penetração apenas se contrato permitir;
- provider speed presente → +10/+15 exatamente uma vez;
- coexistência sem double-scale;
- hit <12 / ≥12;
- miss sem refund;
- derived/ricochet/spell/companion sem herança;
- mesma origem/root action.

## Fechamento Chat 2 — 2026-09-01

O Chat 2 removeu o fallback sintético e materializou a propagação de availability. Nenhum bypass de A0044 foi criado.

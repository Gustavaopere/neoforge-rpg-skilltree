# A0149 — Recuperação de Feitiço

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL EM IRON'S; OUTROS PROVIDER-GATED.**

## Contrato
Notable ARCANE/CASTING, 1 rank, 2 PP. Concluir cast não instantâneo abre janela de 5 s. Concluir segunda magia elegível de ID diferente aplica ×0,85 ao cooldown final da segunda uma vez, consome janela e inicia cooldown interno de 8 s.

## Boundary
Iron's 3.16.3 oferece spell_id/cast events e `SpellCooldownAddedEvent.Pre#setEffectiveCooldown`, permitindo alteração pré-commit. Outros providers exigem conclusão real + ID estável + cooldown pré-commit equivalente.

## Exclusões
Mesma magia, cast cancelado, proc, summon, automação, callback duplicado ou cooldown não redutível não contam. Não criar scheduler paralelo de cooldown.

## Chat 2
Persistir janela/cooldown interno contra relog/dimensão, deduplicar casts e modificar exclusivamente o segundo cooldown no evento Pre.
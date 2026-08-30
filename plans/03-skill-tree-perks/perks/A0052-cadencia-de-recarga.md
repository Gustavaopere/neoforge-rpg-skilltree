# A0052 — Cadência de Recarga

## Estado

- **Design:** APROVADO após correção estrutural de availability e deduplicação por root action.
- **Notion:** `3c569db9-f0db-81a0-a005-cc586dfc6395`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL; não pode ser considerada adquirível enquanto A0050 estiver indisponível e ainda possui gaps de identidade da arma e Multishot.

## Contrato canônico

- Depende de A0050 ≥2 + A0051 ≥2 + gateway `epic_crossbow`.
- Como A0050 exige binding semântico de reload/preparation speed e hoje está indisponível/não comprável sem esse binding, **A0052 herda essa indisponibilidade**.
- A cadeia também herda de A0049 o producer finite-discovery ausente e a reconciliação da ledger CROSSBOW; sem Mastery legítima A0049/A0051 não são alcançáveis.
- Quando a cadeia estiver disponível: hit CROSSBOW confirmado + recarga completa da mesma besta/ItemStack dentro de 6/8 s gera 1 Cadência, cap 3.
- Só conta recarga com ação nativa e consumo real de munição/recurso.
- Miss, cancelamento após >50% ou troca de arma elegível remove 1 carga.
- Um disparo Multishot constitui **uma única root action/outcome**: projéteis irmãos podem confirmar no máximo um sucesso/falha do root; nunca remover múltiplas Cadências pela mesma tentativa e nunca reclassificar como miss após um irmão já confirmar hit.
- Exhaustion/fome pode modular apenas a tolerância quando houver leitura configurada; nunca usar Stamina como substituto.

## Evidência runtime

`A0041A0060ProjectileEvents` registra hit CROSSBOW pós-dano e detecta transição descarregada→carregada durante uso real. Porém o receipt de hit guardado em `A0041A0060CombatState` não registra a identidade da besta; `onCrossbowReloadComplete(...)` recebe `weaponId`, mas a policy não o correlaciona com o hit anterior. Assim, trocar de besta pode deixar um receipt antigo capaz de ser consumido por uma recarga posterior.

Há ainda um defeito explícito de Multishot: cada `AbstractArrow` mantém seu próprio `ProjectileMeta.failureRecorded`, embora os três projéteis compartilhem o mesmo `rootActionId`. Portanto, irmãos que atingem blocos podem chamar `onCrossbowFailure(...)` separadamente e remover até três Cadências por um único disparo; também podem remover carga depois de outro irmão já ter confirmado o hit do root. O outcome precisa ser centralizado/deduplicado por root action.

## Pendências para Chat 2

- **P-A0052-01:** availability server-authoritative deve propagar A0050→A0052; sem A0050 comprável, compra/rank de A0052 deve ser impossível.
- **P-A0052-02:** correlacionar hit e reload pela mesma identidade causal de `ItemStack`/arma; limpar receipt ao trocar/clonar a besta e rejeitar reload de outra arma.
- **P-A0052-03:** testar cancelamento >50%, miss, troca, reload legítimo, estado carregado externo e callbacks duplicados.
- **P-A0052-04:** deduplicar sucesso/falha de Multishot pelo `rootActionId`: no máximo um outcome e uma perda de Cadência por disparo; sucesso de qualquer projétil encerra a possibilidade de irmãos registrarem failure do mesmo root.
- **Herdadas de A0049:** producer finite-discovery `epicfight:crossbow` e reconciliação `combat:crossbow` ↔ `epicfight:crossbow` permanecem blockers de aquisição da cadeia.

## Boundaries

Black Arcana/Enshrouded/Volcanoes não fornecem reload receipt nem Cadência. Companion projectile Mobstein não cria hit receipt para o dono.

## Notion

`Dependências Obrigatórias`, `Gate`, `Hook`, `Fallback` e `Regra` corrigidos no fechamento inicial. Após review da PR #249, `Hook`, `Fallback` e `Regra` receberam a invariável Multishot/root-outcome; re-fetch pós-review PASS em 2026-08-30.

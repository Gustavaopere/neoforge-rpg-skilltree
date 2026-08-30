# A0052 — Cadência de Recarga

## Estado

- **Design:** APROVADO após correção estrutural de availability.
- **Notion:** `3c569db9-f0db-81a0-a005-cc586dfc6395`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL; não pode ser considerada adquirível enquanto A0050 estiver indisponível.

## Contrato canônico

- Depende de A0050 ≥2 + A0051 ≥2 + gateway `epic_crossbow`.
- Como A0050 exige binding semântico de reload/preparation speed e hoje está indisponível/não comprável sem esse binding, **A0052 herda essa indisponibilidade**.
- Quando a cadeia estiver disponível: hit CROSSBOW confirmado + recarga completa da mesma besta/ItemStack dentro de 6/8 s gera 1 Cadência, cap 3.
- Só conta recarga com ação nativa e consumo real de munição/recurso.
- Miss, cancelamento após >50% ou troca de arma elegível remove 1 carga.
- Exhaustion/fome pode modular apenas a tolerância quando houver leitura configurada; nunca usar Stamina como substituto.

## Evidência runtime

`A0041A0060ProjectileEvents` registra hit CROSSBOW pós-dano e detecta transição descarregada→carregada durante uso real. Porém o receipt de hit guardado em `A0041A0060CombatState` não registra a identidade da besta; `onCrossbowReloadComplete(...)` recebe `weaponId`, mas a policy não o correlaciona com o hit anterior. Assim, trocar de besta pode deixar um receipt antigo capaz de ser consumido por uma recarga posterior.

## Pendências para Chat 2

- **P-A0052-01:** availability server-authoritative deve propagar A0050→A0052; sem A0050 comprável, compra/rank de A0052 deve ser impossível.
- **P-A0052-02:** correlacionar hit e reload pela mesma identidade causal de `ItemStack`/arma; limpar receipt ao trocar/clonar a besta e rejeitar reload de outra arma.
- **P-A0052-03:** testar cancelamento >50%, miss, troca, reload legítimo, estado carregado externo e callbacks duplicados.

## Boundaries

Black Arcana/Enshrouded/Volcanoes não fornecem reload receipt nem Cadência. Companion projectile Mobstein não cria hit receipt para o dono.

## Notion

`Dependências Obrigatórias`, `Gate`, `Hook`, `Fallback` e `Regra` corrigidos; re-fetch PASS.

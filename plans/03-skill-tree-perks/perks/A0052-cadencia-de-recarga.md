# A0052 — Cadência de Recarga

## Estado

- **Design:** APROVADO após correção estrutural de availability, provenance, deduplicação por root action e lifecycle.
- **Notion:** `3c569db9-f0db-81a0-a005-cc586dfc6395`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL; não pode ser considerada adquirível enquanto A0050 estiver indisponível e ainda possui gaps de identidade da arma, launch provenance, Multishot e limpeza de estado em reconciliação.

## Contrato canônico

- Depende de A0050 ≥2 + A0051 ≥2 + gateway `epic_crossbow`.
- Como A0050 exige binding semântico de reload/preparation speed e hoje está indisponível/não comprável sem esse binding, **A0052 herda essa indisponibilidade**.
- A cadeia também herda de A0049 o producer finite-discovery ausente e a reconciliação da ledger CROSSBOW.
- Quando a cadeia estiver disponível: launch CROSSBOW confirmado → hit correlacionado → recarga completa da **mesma** besta/ItemStack dentro de 6/8 s gera 1 Cadência, cap 3.
- Só conta recarga com ação nativa e consumo real de munição/recurso.
- Miss, cancelamento após >50% ou troca de arma elegível remove 1 carga.
- Um disparo Multishot constitui **uma única root action/outcome**: projéteis irmãos podem confirmar no máximo um sucesso/falha do root; nunca remover múltiplas Cadências pela mesma tentativa e nunca reclassificar como miss após um irmão confirmar hit.
- Rank loss, respec ou rules reload que invalide A0052/A0051/A0050/gateway deve limpar Cadência e receipts/root outcomes pertencentes à perk; recompra nunca recupera cargas antigas.
- Exhaustion/fome pode modular apenas a tolerância quando houver leitura configurada; nunca usar Stamina como substituto.

## Evidência runtime

`A0041A0060ProjectileEvents` registra hit CROSSBOW pós-dano e detecta transição descarregada→carregada durante uso real. Porém o receipt de hit em `A0041A0060CombatState` não registra a identidade da besta; `onCrossbowReloadComplete(...)` recebe `weaponId`, mas a policy não o correlaciona com o hit anterior. Assim, trocar de besta pode deixar receipt antigo consumível por recarga posterior.

Há defeito explícito de Multishot: cada `AbstractArrow` mantém seu próprio `ProjectileMeta.failureRecorded`, embora os projéteis compartilhem `rootActionId`; irmãos que atingem blocos podem remover várias Cadências e até registrar failure após outro irmão confirmar hit.

Também há gap de provenance herdado do bridge CROSSBOW: projectile com owner/metadata de besta, mas sem launch receipt real, não pode gerar hit receipt para A0052. Finalmente, o state auditado é limpo em lifecycle amplo (logout/dimensão/respawn/server stop), mas não há reconciliação específica quando ranks/pré-requisitos são removidos; isso permitiria carry-over de Cadência após respec/recompra.

## Pendências para Chat 2

- **P-A0052-01:** availability server-authoritative deve propagar A0050→A0052; sem A0050 comprável, compra/rank de A0052 é impossível.
- **P-A0052-02:** correlacionar hit e reload pela mesma identidade causal de `ItemStack`/arma; limpar receipt ao trocar/clonar a besta e rejeitar reload de outra arma.
- **P-A0052-03:** testar cancelamento >50%, miss, troca, reload legítimo, estado carregado externo e callbacks duplicados.
- **P-A0052-04:** deduplicar sucesso/falha de Multishot pelo `rootActionId`: no máximo um outcome e uma perda de Cadência por disparo; sucesso de qualquer projétil encerra possibilidade de failure por irmãos.
- **P-A0052-05:** só criar hit receipt a partir de projectile correlacionado a launch CROSSBOW confirmado; owner/metadata isolados ficam fail-closed.
- **P-A0052-06:** em rank loss, respec ou rules reload bem-sucedido que invalide A0052/pré-requisitos, limpar/reconciliar Cadência, hit receipts e root outcomes antes de qualquer recompra.
- **Herdadas de A0049:** producer finite-discovery `epicfight:crossbow` e reconciliação `combat:crossbow` ↔ `epicfight:crossbow` permanecem blockers de aquisição.

## Boundaries

Black Arcana/Enshrouded/Volcanoes não fornecem reload receipt nem Cadência. Companion projectile Mobstein não cria hit receipt para o dono.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0050 ≥2 + A0051 ≥2 + `epic_crossbow`; indisponibilidade de A0050 e blockers A0049 propagam-se sem bypass. |
| 2. Integração global | **PASS** | Cadência é recurso próprio do RPG; exhaustion/fome só modulam opcionalmente a janela, Stamina não substitui; magia/hazards/companions não geram receipt. |
| 3. Qualidade e identidade | **PASS** | Notable de execução que recompensa ciclo hit→reload e cria decisão/ritmo, não mero percentual genérico. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 3 no ramo de Bestas, dependente da progressão A0049–A0051; sem atalho topológico. |
| 5. Especializações | **PASS** | É progressão MARTIAL/BESTAS, não classe automática; mantém authority de reload/arma no provider. |
| 6. PT-BR | **PASS** | Nome, efeito, requisitos e mensagens conceituais em PT-BR; termos técnicos/IDs só na documentação. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra preenchidos; Multishot, launch provenance e lifecycle adicionados e re-fetch pós-review confirmado. |
| 8. NeoVitae | **PASS** | Nenhuma referência ou dependência ativa. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/Minecraft/RPG e boundaries Black Arcana/Enshrouded/Volcanoes/Mobstein dispostos; nenhum provider periférico foi promovido artificialmente a Cadência. |

Os 18 critérios técnicos cumulativos passam **no design**; blockers runtime permanecem explícitos, fail-closed e testáveis, portanto não são tratados como implementação confirmada.

## Notion

`Dependências Obrigatórias`, `Gate`, `Hook`, `Fallback` e `Regra` foram corrigidos no fechamento inicial. Reviews da PR #249 adicionaram Multishot/root-outcome, launch provenance e lifecycle de rank/respec/rules reload; re-fetch pós-review PASS em 2026-08-30.

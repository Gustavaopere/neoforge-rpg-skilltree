# A0052 — Cadência de Recarga

## Estado

- **Design:** APROVADO após correção estrutural de availability, provenance, deduplicação por root action e lifecycle.
- **Notion:** `3c569db9-f0db-81a0-a005-cc586dfc6395`.
- **Runtime:** **NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED CORRETO**. A0052 herda `UNAVAILABLE_NODE` de A0050, portanto não pode criar ghost rank nem ativar Cadência enquanto faltar binding semântico de reload/preparation speed. A pendência técnica interna de Multishot P-A0052-04 foi resolvida e validada pelo Chat 3, deixando o seam pronto para futura habilitação sem alterar a indisponibilidade atual.

## Contrato canônico

- Depende de A0050 ≥2 + A0051 ≥2 + gateway `epic_crossbow`.
- Como A0050 exige binding semântico de reload/preparation speed e hoje está indisponível/não comprável sem esse binding, **A0052 herda essa indisponibilidade**.
- Quando a cadeia estiver disponível: launch CROSSBOW confirmado → hit correlacionado → recarga completa da **mesma** besta/ItemStack dentro de 6/8 s gera 1 Cadência, cap 3.
- Só conta recarga com ação nativa e consumo real de munição/recurso.
- Miss, cancelamento após >50% ou troca de arma elegível remove 1 carga.
- Um disparo Multishot constitui **uma única root action/outcome**: projéteis irmãos podem confirmar no máximo um sucesso/falha do root; um hit de qualquer irmão vence falhas anteriores e impede perda por aquele root; um root all-fail remove no máximo uma Cadência.
- Rank loss, respec ou rules reload que invalide A0052/A0051/A0050/gateway limpa Cadência e receipts/root outcomes pertencentes à perk; recompra nunca recupera cargas antigas.
- Exhaustion/fome pode modular apenas a tolerância quando houver leitura configurada; nunca usar Stamina como substituto.

## Evidência runtime

O receipt CROSSBOW carrega `weaponId` estável do `CrossbowTrack`: hit e reload só fecham Cadência quando pertencem à mesma identidade de besta. Troca/remoção da arma limpa receipt pendente, e hit só é registrado quando `ProjectileMeta.launchConfirmed` prova um lançamento CROSSBOW correlacionado.

`CombatPerkAvailabilityRuntime` mascara A0052 enquanto A0050 estiver indisponível, tanto para compra direta quanto para request network/server-authoritative. `A0041A0060RuntimeState.ranks(...)` reconcilia o state transiente e limpa Cadência/receipts quando rank/pré-requisito efetivo deixa de existir.

### Fechamento P-A0052-04 — Chat 3

O RED reproduzível mostrou que a implementação anterior tratava cada `AbstractArrow` isoladamente: o primeiro irmão do Multishot que atingisse bloco podia executar a perda antes de um segundo irmão do mesmo disparo acertar. O Chat 3 corrigiu isso com um arbiter transient bounded por `actor + rootActionId`:

- `registerCrossbowProjectile(...)` registra siblings reais correlacionados ao root;
- `recordCrossbowProjectileFailure(...)` acumula misses sem liquidar prematuramente;
- `recordCrossbowProjectileSuccess(...)` torna sucesso terminal para a arbitragem de falha;
- `sealCrossbowRoot(...)` fecha a janela de registro e só liquida failure quando todos os siblings registrados falharam;
- settlement é emitido no máximo uma vez; callbacks duplicados e replay não removem Cadência novamente;
- root outcomes são limpos por TTL, rank reconciliation, logout/lifecycle e `clearAll`.

A bridge runtime registra siblings em `EntityJoinLevelEvent`, marca hit no `LivingDamageEvent.Post`, registra miss em `ProjectileImpactEvent` e sela o root ao expirar a janela de correlação. O teste `multishotRootUsesSuccessWinsAndAtMostOneFailure()` cobre sucesso posterior a miss, all-fail e settlement pós-seal.

## Pendências técnicas

- **RESOLVIDA P-A0052-01:** availability A0050→A0052 propagada server-authoritative.
- **RESOLVIDA P-A0052-02:** hit/reload correlacionados pela mesma identidade de besta.
- **VALIDADA P-A0052-03:** hooks de cancelamento >50%, miss, troca e reload legítimo permanecem no pipeline e passaram a bateria do lote.
- **RESOLVIDA P-A0052-04:** outcome Multishot agregado `success-wins`, all-fail único e dedup implementados e testados.
- **RESOLVIDA P-A0052-05:** hit receipt só nasce de projectile com launch CROSSBOW confirmado.
- **RESOLVIDA P-A0052-06:** reconciliation de rank/pré-requisito limpa Cadência/receipts/reservas/root outcomes próprios.
- **PENDÊNCIA NÃO BLOQUEANTE PARA O MERGE:** A0050 continua sem binding semântico de reload/preparation speed; por isso A0052 permanece indisponível/fail-closed, sem ser promovida artificialmente a IMPLEMENTAÇÃO CONFIRMADA jogável.

## Implementação e validação

- [x] Availability/fail-closed implementada.
- [x] Identidade causal da besta implementada.
- [x] Launch provenance implementada.
- [x] Lifecycle rank/respec/rules reload implementado no owner transiente.
- [x] Outcome Multishot agregado por root com `success-wins` e all-fail único.
- [x] Código presente.
- [x] **VALIDAÇÃO CHAT 3:** JUnit/core, incluindo Multishot root arbitration.
- [x] **VALIDAÇÃO CHAT 3:** NeoForge adapter/GameTests do lote verdes.
- [x] **VALIDAÇÃO CHAT 3:** build NeoForge e dedicated-server smoke verdes.
- [x] **VALIDAÇÃO CHAT 3:** CI funcional GREEN em `6826c50896c4ad586b8942031465b6a0a3ce44af` (run `34006356029`).
- [x] **VALIDAÇÃO CHAT 3:** fail-closed atual confirmado.
- [ ] **IMPLEMENTAÇÃO CONFIRMADA JOGÁVEL:** bloqueada por A0050 sem binding semântico real.

## Boundaries

Black Arcana/Enshrouded/Volcanoes não fornecem reload receipt nem Cadência. Companion projectile Mobstein não cria hit receipt para o dono.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design e código fail-closed** | A0050 ≥2 + A0051 ≥2 + `epic_crossbow`; `UNAVAILABLE_NODE` impede bypass enquanto A0050 não possui binding seguro. |
| 2. Integração global | **PASS** | Cadência é recurso próprio do RPG; Stamina não substitui; magia/hazards/companions não geram receipt. |
| 3. Qualidade e identidade | **PASS** | Notable de execução que recompensa ciclo hit→reload. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 3 no ramo de Bestas, dependente de A0049–A0051. |
| 5. Especializações | **PASS** | MARTIAL/BESTAS; authority de reload/arma permanece no provider. |
| 6. PT-BR | **PASS** | Player-facing em PT-BR. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra preenchidos; Multishot/lifecycle documentados. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Providers e boundaries pertinentes dispostos sem provider periférico artificial. |

## Notion

`Dependências Obrigatórias`, `Gate`, `Hook`, `Fallback` e `Regra` foram corrigidos no fechamento de design; re-fetch pós-review PASS em 2026-08-30.

## Fechamento Chat 3 — PR #387

P-A0052-04 saiu de RED reproduzível para GREEN no HEAD funcional `6826c50896c4ad586b8942031465b6a0a3ce44af`, cuja bateria passou JUnit 5, NeoForge JUnit adapter, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke. A perk permanece **NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED CORRETO** exclusivamente pela cadeia de availability de A0050; isso não bloqueia o merge porque o comportamento indisponível é intencional, explícito e seguro.
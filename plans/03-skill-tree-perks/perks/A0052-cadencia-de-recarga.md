# A0052 — Cadência de Recarga

## Estado

- **Design:** APROVADO após correção estrutural de availability, provenance, deduplicação por root action e lifecycle.
- **Notion:** `3c569db9-f0db-81a0-a005-cc586dfc6395`.
- **Runtime:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**. A0052 agora herda `UNAVAILABLE_NODE` de A0050, portanto não pode criar ghost rank nem ativar Cadência enquanto faltar binding semântico de reload/preparation speed.

## Contrato canônico

- Depende de A0050 ≥2 + A0051 ≥2 + gateway `epic_crossbow`.
- Como A0050 exige binding semântico de reload/preparation speed e hoje está indisponível/não comprável sem esse binding, **A0052 herda essa indisponibilidade**.
- Quando a cadeia estiver disponível: launch CROSSBOW confirmado → hit correlacionado → recarga completa da **mesma** besta/ItemStack dentro de 6/8 s gera 1 Cadência, cap 3.
- Só conta recarga com ação nativa e consumo real de munição/recurso.
- Miss, cancelamento após >50% ou troca de arma elegível remove 1 carga.
- Um disparo Multishot constitui **uma única root action/outcome**: projéteis irmãos podem confirmar no máximo um sucesso/falha do root; nunca remover múltiplas Cadências pela mesma tentativa e nunca reclassificar como miss após um irmão confirmar hit.
- Rank loss, respec ou rules reload que invalide A0052/A0051/A0050/gateway deve limpar Cadência e receipts/root outcomes pertencentes à perk; recompra nunca recupera cargas antigas.
- Exhaustion/fome pode modular apenas a tolerância quando houver leitura configurada; nunca usar Stamina como substituto.

## Evidência runtime

O Chat 2 passou o receipt CROSSBOW a carregar `weaponId` estável do `CrossbowTrack`: hit e reload só fecham Cadência quando pertencem à mesma identidade de besta. Troca/remoção da arma limpa receipt pendente, e hit só é registrado quando `ProjectileMeta.launchConfirmed` prova um lançamento CROSSBOW correlacionado.

`CombatPerkAvailabilityRuntime` mascara A0052 enquanto A0050 estiver indisponível, tanto para compra direta quanto para request network/server-authoritative. `A0041A0060RuntimeState.ranks(...)` também reconcilia o state transiente e limpa Cadência/receipts quando rank/pré-requisito efetivo deixa de existir.

Para Multishot, o Chat 2 endureceu a perda com claim `A0052:failure` por `rootActionId`, impedindo múltiplas perdas idênticas pelo mesmo root. Ainda existe uma pendência técnica futura: um projétil irmão pode atingir bloco antes de outro irmão confirmar hit; o outcome agregado `success-wins` deve ser fechado antes de A0052 ser habilitada. Como A0050 mantém a cadeia `UNAVAILABLE_NODE`, essa lacuna permanece fail-closed e não é explorável no estado atual.

## Pendências para Chat 2

- **RESOLVIDA P-A0052-01:** availability A0050→A0052 propagada server-authoritative.
- **RESOLVIDA P-A0052-02:** hit/reload correlacionados pela mesma identidade de besta.
- **PARCIAL P-A0052-03:** hooks de cancelamento >50%, miss, troca e reload legítimo estão presentes; execução final/regressões ficam para Chat 3.
- **PENDÊNCIA TÉCNICA P-A0052-04:** outcome Multishot agregado ainda precisa de política `success-wins` antes de futura habilitação; o estado atual permanece indisponível por A0050.
- **RESOLVIDA P-A0052-05:** hit receipt só nasce de projectile com launch CROSSBOW confirmado.
- **RESOLVIDA P-A0052-06:** reconciliation de rank/pré-requisito limpa Cadência/receipts/reservas próprias.
- As pendências antigas de producer/namespace CROSSBOW de A0049 já estão resolvidas na linha predecessora.

## Implementação Chat 2 — PR #386

- [x] Availability/fail-closed implementada.
- [x] Identidade causal da besta implementada.
- [x] Launch provenance implementada.
- [x] Lifecycle rank/respec/rules reload implementado no owner transiente.
- [x] Deduplicação de perda por root implementada.
- [x] Código presente.
- [ ] **PENDÊNCIA TÉCNICA:** agregar outcome Multishot `success-wins` antes de qualquer futura habilitação de A0052.
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/JUnit e cenários miss/cancel/switch/reload.
- [ ] **VALIDAÇÃO CHAT 3:** GameTests/integração Multishot.
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge / dedicated-server smoke / CI GREEN.
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA.

## Boundaries

Black Arcana/Enshrouded/Volcanoes não fornecem reload receipt nem Cadência. Companion projectile Mobstein não cria hit receipt para o dono.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design e código fail-closed** | A0050 ≥2 + A0051 ≥2 + `epic_crossbow`; `UNAVAILABLE_NODE` impede bypass enquanto A0050 não possui binding seguro. |
| 2. Integração global | **PASS** | Cadência é recurso próprio do RPG; Stamina não substitui; magia/hazards/companions não geram receipt. |
| 3. Qualidade e identidade | **PASS** | Notable de execução que recompensa ciclo hit→reload e cria decisão/ritmo, não mero percentual genérico. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 3 no ramo de Bestas, dependente da progressão A0049–A0051; sem atalho topológico. |
| 5. Especializações | **PASS** | É progressão MARTIAL/BESTAS, não classe automática; mantém authority de reload/arma no provider. |
| 6. PT-BR | **PASS** | Nome, efeito, requisitos e mensagens conceituais em PT-BR; termos técnicos/IDs só na documentação. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra preenchidos; Multishot, launch provenance e lifecycle adicionados e re-fetch pós-review confirmado. |
| 8. NeoVitae | **PASS** | Nenhuma referência ou dependência ativa. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/Minecraft/RPG e boundaries Black Arcana/Enshrouded/Volcanoes/Mobstein dispostos; nenhum provider periférico foi promovido artificialmente a Cadência. |

Os critérios técnicos permanecem satisfeitos no design; o runtime atual falha fechado até A0050 possuir capability real e até a pendência Multishot ser resolvida/validada.

## Notion

`Dependências Obrigatórias`, `Gate`, `Hook`, `Fallback` e `Regra` foram corrigidos no fechamento inicial. Reviews da PR #249 adicionaram Multishot/root-outcome, launch provenance e lifecycle de rank/respec/rules reload; re-fetch pós-review PASS em 2026-08-30.
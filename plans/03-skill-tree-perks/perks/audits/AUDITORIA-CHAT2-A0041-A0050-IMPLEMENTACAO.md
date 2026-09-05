# AUDITORIA CHAT 2 — IMPLEMENTAÇÃO A0041–A0050

Data: 2026-09-01  
Branch: `feat/chat2-a0041-a0050-retro-implementation`  
PR: #360

## Escopo

Lote exato de 10 perks A0041–A0050, selecionado após varredura retroativa A0001→A0040 confirmar que os lotes anteriores já possuíam passagem de Chat 2. O Chat 2 implementou apenas o contrato já aprovado pelo Chat 1 e não redesenhou efeitos/providers/gates.

## Evidência TDD focal

A PR foi aberta inicialmente com contratos RED. `RPG Skill Tree CI #3031` falhou em **Core tests** como esperado antes da correção de produção, provando o defeito de A0041: `scytheCut(...)` consumia a Marca Madura no PRE.

Após o RED, o Chat 2 implementou os boundaries descritos abaixo. Essa utilização do CI é evidência de desenvolvimento; não substitui a bateria final do Chat 3.

## Estado final por perk

| Perk | Estado Chat 2 | Resultado |
|---|---|---|
| A0041 | CÓDIGO PRESENTE | PRE reserva Marca Madura; POST Epic Fight confirmado consome; zero-damage descarta reserva; reserva exclusiva actor+target/root |
| A0042 | CÓDIGO PRESENTE EM FAIL-CLOSED | `UNAVAILABLE_NODE`; não existe `eligible_kill` anti-abuso canônico compartilhado, portanto producers históricos ficam inertes |
| A0043 | CÓDIGO PRESENTE | damage BOW já existia; Mastery/discovery e namespace `epicfight:bow` já foram resolvidos por evolução posterior da main |
| A0044 | CÓDIGO PRESENTE EM FAIL-CLOSED | `UNAVAILABLE_NODE`; draw/preparation speed sem provider semântico seguro |
| A0045 | CÓDIGO PRESENTE | resolver crítico canônico preservado; gate Mastery BOW agora alcançável |
| A0046 | CÓDIGO PRESENTE | stable aim/sprint/cancel/abrupt/distant hit ativos; heavy-impact e body scalar permanecem component-wise fail-closed |
| A0047 | CÓDIGO PRESENTE EM FAIL-CLOSED | herda A0044; projectile speed sintético removido; `projectileSpeedAvailable=false` |
| A0048 | CÓDIGO PRESENTE EM FAIL-CLOSED | core Tiro Preparado preservado, mas availability herda A0047 estruturalmente |
| A0049 | CÓDIGO PRESENTE | damage CROSSBOW já existia; Mastery/discovery e namespace `epicfight:crossbow` já resolvidos na main |
| A0050 | CÓDIGO PRESENTE EM FAIL-CLOSED | `UNAVAILABLE_NODE`; reload/preparation speed sem provider semântico seguro |

## Mudanças de runtime

### Availability server-authoritative

Foi criado `CombatPerkAvailabilityRuntime` para representar indisponibilidade conhecida antes da compra e antes de efeitos:

- A0042 = indisponível por ausência de `eligible_kill` canônico;
- A0044 = indisponível por ausência de draw/preparation speed;
- A0047 = indisponível por dependência estrutural de A0044;
- A0048 = indisponível por dependência estrutural de A0047;
- A0050 = indisponível por ausência de reload/preparation speed.

`NodePurchaseResult` recebeu `UNAVAILABLE_NODE`. `NodePurchaseRequestProcessor` consulta availability antes de reservar request-id e antes de mutar pontos/ranks. O caminho trusted de `PlayerProgressionRuntime.purchaseNode(...)` usa o mesmo gate. `A0041A0060RuntimeState.ranks(...)` mascara ranks persistidos de nodes indisponíveis, preservando o dado para futura recuperação/refund sem produzir efeito gameplay.

### A0041 reservation → POST commit

`A0041A0060CombatPolicy.scytheCut(...)` não chama mais `consumeMatureReap(...)` no PRE. `A0041A0060CombatState` reserva uma Marca por `actor + target + rootActionId` com retenção curta, impedindo dois roots concorrentes de receberem o mesmo benefício.

`A0041ScytheCommitHooks` registra um consumer dedicado em `EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST`:

- dano modificado `> 0` + identidade hostil válida → consome a Marca reservada;
- dano zero/outcome inválido → descarta reserva sem consumo;
- hit letal é permitido porque o commit não exige `target.isAlive()`;
- logout/dimension/respawn/server stop limpam estado pelo owner A0041–A0060.

### A0047 sem projectile-speed fabricado

`A0041A0060ProjectileEvents` não declara mais `projectileSpeedAvailable=true` por existir `AbstractArrow` e não chama mais `arrow.setDeltaMovement(...scale(...))` para Distância Dominada. O policy continua capaz de representar uma capability futura, mas no runtime atual o componente speed permanece ausente.

### Mastery BOW/CROSSBOW reaproveitada

Não foi criado producer novo. A main atual já possui `PhysicalProjectileMasteryEvents`, `WeaponMasteryMilestonePolicy` e `WeaponMasteryMilestoneRuntime`, com release→projectile→POST correlation e discovery persistente por tipo hostil. `tree_architecture/combat.json` já usa `epicfight:bow` e `epicfight:crossbow`, resolvendo as pendências históricas de namespace A0043/A0049.

## Pendências técnicas não inventadas

- A0042: falta `eligible_kill` anti-abuso central real; não usar `Enemy || Player` como substituto.
- A0044: falta provider semântico de draw/preparation speed.
- A0046: falta heavy-impact receipt e adapters corporais canônicos por eixo; componentes já seguros permanecem funcionais.
- A0047: falta provider semântico de launch speed; além disso a perk permanece indisponível pela A0044.
- A0048: bloqueada transitivamente por A0047; não criar bypass.
- A0050: falta provider semântico de reload/preparation speed.

## Testes focais adicionados

- `A0041A0060CombatPolicyTest`: PRE deve preservar a Marca.
- `A0041ScytheReservationJUnitTest`: reserva exclusiva, commit exatamente uma vez e rollback preservando Marca.
- `A0041A0050AvailabilityJUnitTest`: `UNAVAILABLE_NODE`, availability A0042/A0044/A0047/A0048/A0050 e ausência do fallback sintético de projectile speed.

## Testes obrigatórios para o Chat 3

1. A0041: Marca madura/imatura, >50/≤50, positivo/zero/cancelado, letal, callback duplicado, roots concorrentes, multiplayer/lifecycle.
2. Availability: compra network e trusted de todos os nodes indisponíveis sem gasto de pontos, sem ghost rank e sem consumo indevido de request-id; rank legado mascarado.
3. A0043/A0049: 6/8 tipos hostis, gate 59/60 e 79/80, dedup BOW/CROSSBOW, fake/summon/spell/derived/companion.
4. A0045: provider-critical true/false, uma rolagem por root e ausência de double multiplier.
5. A0046: stable gain, sprint drain, draw cancel, abrupt aim e distant hit; heavy/body ausentes não podem ser inferidos.
6. A0047/A0048: nenhuma ativação enquanto cadeia A0044→A0047 estiver unavailable; nenhuma alteração de projectile velocity.
7. A0050: indisponibilidade propaga para dependências posteriores auditadas pelo lote seguinte.
8. Testes unitários/GameTests pertinentes, NeoForge build, dedicated-server smoke e CI final conforme protocolo Chat 3.

## Handoff

Estado do lote: **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**, com A0042/A0044/A0047/A0048/A0050 explicitamente em fail-closed operacional.

O Chat 2 não declara `IMPLEMENTAÇÃO CONFIRMADA`, não executa a bateria final e não faz merge.

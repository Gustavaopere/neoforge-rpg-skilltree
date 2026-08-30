# A0054 — Maestria de Bestas — Mecanismo Ajustado

## Estado

- **Design:** APROVADO após correção estrutural, reservation→commit e lifecycle.
- **Notion:** `3c569db9-f0db-814f-96e0-ee616a448f0d`.
- **Runtime:** IMPLEMENTAÇÃO PARCIAL / atualmente não adquirível pela cadeia A0050→A0052/A0053; consumo da janela/Cadência ainda precisa ser transacional no lançamento real.

## Contrato canônico

- A0052 ≥2 + A0053 ≥1 + `epicfight:crossbow` ≥80 + gateway `epic_crossbow`.
- Todos os pré-requisitos precisam estar disponíveis/compráveis.
- Em 3 Cadências, recarga completa arma janela de 8/9/10 s.
- O **próximo disparo efetivamente materializado** na janela consome as 3 cargas e recebe +15% dano.
- A tentativa segue **reservation→commit**: reservar janela/recursos durante o lançamento, mas só commitá-los quando criação do projectile/root correlacionado a launch receipt CROSSBOW for confirmada. Cancelamento tardio, ausência de spawn ou falha equivalente faz rollback e preserva Cadência/janela.
- A recarga seguinte pode ser 15% mais rápida uma vez somente se surgir hook semântico seguro de reload/preparation speed.
- Sem esse hook, omitir apenas aceleração; nunca usar projectile speed/timer heurístico.
- Rank loss/respec/rules reload que invalide A0054 limpa janela/reservas do capstone; Cadência base pertence a A0052 e só é limpa quando A0052/pré-requisitos forem invalidados.

## Evidência runtime

A policy possui `armAdjustedMechanismOnReload(...)` e `tryAdjustedCrossbowShot(...)`. Porém `A0041A0060CombatState.armAdjustedMechanism(...)` zera `cadence` ao armar a janela. O contrato exige manter as 3 cargas até o disparo que efetivamente consome a janela ou até expiração; armar não pode consumir antecipadamente.

`tryAdjustedCrossbowShot(...)` também é chamado no `ArrowLooseEvent` antes de a criação do projétil estar confirmada e consome janela naquele momento. Listener posterior pode cancelar o lançamento, deixando ativação perdida sem projectile/root. A implementação precisa reservation→commit/rollback e launch provenance real.

A parcela de reload acelerado permanece corretamente omitida por ausência de provider seguro. O state específico do capstone também precisa ser reconciliado em rank loss/respec/rules reload para não sobreviver à perda do terminal.

## Pendências para Chat 2

- **P-A0054-01:** mover consumo das 3 Cadências do arm/reload para o disparo que consome Mecanismo Ajustado; expiração sem disparo não simula consumo antecipado.
- **P-A0054-02:** propagar availability da cadeia A0050/A0052/A0053 e impedir compra enquanto pré-requisitos forem indisponíveis.
- **P-A0054-03:** reconciliar ledger `epicfight:crossbow` com architecture catalog; `combat:crossbow` não atua como ledger paralela.
- **P-A0054-04:** reservation→commit/rollback da janela de Mecanismo Ajustado; cancelamento tardio/ausência de projectile spawn não queima ativação nem Cadências.
- **P-A0054-05:** exigir launch receipt CROSSBOW confirmado para o disparo consumidor; projectile derivado/reemitido sem correlação é inelegível.
- **P-A0054-06:** limpar janela/reservas próprias do capstone em rank loss, respec ou rules reload que invalide A0054; reconciliar Cadência pelo owner A0052.
- Herdar `P-A0049-01` e demais blockers da cadeia CROSSBOW; sem producer de Mastery legítimo e A0050 comprável, A0054 não é alcançável.

## Boundaries

`ARCANE_BACKLASH`, spell/derived projectiles e companions Mobstein não armam/consomem o capstone.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | A0052 ≥2 + A0053 ≥1 + Mastery `epicfight:crossbow` ≥80 + gateway; toda indisponibilidade herdada permanece bloqueante. |
| 2. Integração global | **PASS** | Cadência e crítico/dano usam pipelines próprios/canônicos; reload speed só existe com hook semântico real; nenhum recurso mágico/ambiental é usado como proxy. |
| 3. Qualidade e identidade | **PASS** | Capstone conclui a fantasia mecânica hit→reload→janela→disparo preparado, com decisão temporal e fallback que preserva identidade. |
| 4. Ramificação, distância e topologia | **PASS** | Camada 4 terminal do ramo de Bestas, dependências convergentes e Mastery 80 compatíveis com posição de Capstone. |
| 5. Especializações | **PASS** | `TERMINAL_EXTERIOR: MARTIAL/BESTAS`; só satisfaz Gate C por mapeamento explícito e não vira classe/mod specialization automática. |
| 6. PT-BR | **PASS** | Nome, efeito e requisitos em PT-BR; IDs e hooks técnicos em inglês apenas onde necessário. |
| 7. Notion completo | **PASS** | Dependências/Gate/Hook/Fallback/Regra completos; consumo tardio, reservation→commit, launch provenance e lifecycle re-fetched. |
| 8. NeoVitae | **PASS** | Nenhuma dependência residual. |
| 9. Cobertura modlist/providers | **PASS** | RPG/Epic Fight/WoM e providers globais foram classificados; Pufferfish projectile speed e Wayward Attributes foram rejeitados como substitutos de reload speed; own-projects/Mobstein não integram. |

Os 18 critérios técnicos cumulativos passam **no design**; componentes sem hook seguro permanecem omitidos/fail-closed e os blockers runtime estão destinados ao Chat 2.

## Notion

Dependências, Gate, Hook, Fallback e Regra foram corrigidos no fechamento inicial. Reviews da PR #249 adicionaram reservation→commit, launch provenance e lifecycle de rank/respec/rules reload; re-fetch pós-review PASS em 2026-08-30.

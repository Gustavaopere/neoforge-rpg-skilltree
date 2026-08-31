# A0042 — Maestria de Foices — Colheita de Batalha

## Estado

- **Design:** APROVADO após endurecimento de anti-abuso/provider boundary.
- **Implementação:** PARCIAL; `P-A0042-01` e `P-A0042-02` abertas; refund de Stamina permanece fail-closed correto.
- **Notion:** `3c569db9-f0db-8190-85b6-e883bb4140de`.

## Contrato canônico

- A0040 + A0041 + `combat:scythe` ≥80; terminal exterior de Foices.
- Um `eligible_kill` canônico por golpe direto SCYTHE contra inimigo com Marca Madura arma Colheita de Batalha por 6 s.
- O próximo hit direto SCYTHE contra alvo diferente aplica imediatamente Marca da Ceifa e encerra a janela.
- Cooldown 10/9/8 s em mastery 80/90/100.
- Restituição de 10% de Stamina só existe com receipt causal pós-consumo do custo real; sem receipt, omitir somente esse componente.

## Evidência runtime

- `A0042ScytheKillHooks.legitimateDeadTarget(...)` hoje aceita `Enemy`/`Player` não aliado; não consulta proteção anti-farm/training/summon/repetição.
- Há dois caminhos de morte que podem armar o mesmo estado: `A0042ScytheKillHooks.onDeath(...)` e `A0041A0060EpicFightHooks.onDeath(...)`.
- `A0041A0060CombatState.armBattleHarvest(...)` evita duas janelas simultâneas pelo cooldown, mas os dois producers de legitimidade precisam compartilhar o mesmo receipt/dedup para que um não bypass o outro.
- `consumeBattleHarvestOnHit(...)` exige alvo diferente e o POST aplica a Marca.
- Refund de Stamina não é fabricado no runtime atual.

## Provider→árvore

- **RPG Skill Tree:** authority do `eligible_kill`, anti-abuso, janela, cooldown e dedup.
- **Mobstein 5.4.4:** kill por ally/bodyguard não recebe autoria SCYTHE do dono; kill direto do jogador contra Mobstein pode qualificar após anti-abuse.
- **Black Arcana:** Backlash não é kill de foice.
- **Volcanoes / Enshrouded:** hazards/estados ambientais não armam a janela.
- **Stage 11.01 itemização:** não fornece kill receipt nem Stamina.

## Pendências Chat 2

### P-A0042-01 — `eligible_kill` anti-farm real

Substituir o predicado genérico `Enemy || Player` por receipt do serviço anti-abuso canônico, rejeitando training targets, own summons/companions, kills triviais e farming repetitivo conforme a policy central.

### P-A0042-02 — unificar producers de kill

Consolidar ou fazer ambos os `onDeath` consumirem o mesmo `eligible_kill/rootActionId` deduplicado. Uma correção em apenas um listener não pode deixar o outro como bypass.

### Fallback de Stamina

Sem receipt real de Stamina, a transferência da Marca continua válida e o refund permanece 0; isso é comportamento canônico, não substituição.

## Testes exigidos

- kill legítima, trivial, repetida, training, summon/companion, fake player;
- Marca Madura preexistente ao killing root;
- dois listeners sem dupla ativação/bypass;
- alvo diferente obrigatório;
- cooldown 10/9/8;
- refund omitido sem receipt causal.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura SCYTHE:** Scythe Simply só entra quando Epic Fight Compat resolve `SCYTHE`.
- **Execute provider-owned:** o execute Implicit não cria por si só `eligible_kill` nem uma segunda ativação de Colheita.
- **Causalidade permitida:** se o provider comprovar que a morte por execute pertence inequivocamente ao mesmo root direto SCYTHE do jogador, esse único abate pode ser submetido uma vez ao serviço anti-abuso `eligible_kill`; evento execute/derived separado ou apenas ownership da arma permanece inelegível.
- **Deduplicação:** `P-A0042-02` continua obrigatória inclusive nessa rota; dois listeners não podem armar duas vezes a mesma morte.
- **Stamina:** execute/ability do Simply não fornece receipt do custo de stamina da ação consumidora.
- **Notion:** `Provider/Mods`, `Hook`, `Fallback` e `Regra` corrigidos; re-fetch PASS.

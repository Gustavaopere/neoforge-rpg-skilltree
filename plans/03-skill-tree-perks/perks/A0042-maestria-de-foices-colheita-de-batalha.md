# A0042 — Maestria de Foices — Colheita de Batalha

## Estado

- **Design:** APROVADO após endurecimento de anti-abuso/provider boundary.
- **Implementação:** **CÓDIGO PRESENTE EM FAIL-CLOSED / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Disponibilidade:** `UNAVAILABLE_NODE` enquanto não existir `eligible_kill` anti-abuso canônico compartilhado pelos producers de morte.
- **Notion:** `3c569db9-f0db-8190-85b6-e883bb4140de`.

## Contrato canônico

- A0040 + A0041 + `combat:scythe` ≥80; terminal exterior de Foices.
- Um `eligible_kill` canônico por golpe direto SCYTHE contra inimigo com Marca Madura arma Colheita de Batalha por 6 s.
- O próximo hit direto SCYTHE contra alvo diferente aplica imediatamente Marca da Ceifa e encerra a janela.
- Cooldown 10/9/8 s em mastery 80/90/100.
- Restituição de 10% de Stamina só existe com receipt causal pós-consumo do custo real; sem receipt, omitir somente esse componente.

## Evidência runtime

- `A0042ScytheKillHooks.legitimateDeadTarget(...)` ainda aceita `Enemy`/`Player` não aliado e não prova a policy central de anti-farm/training/summon/repetição.
- Há dois caminhos históricos de morte: `A0042ScytheKillHooks.onDeath(...)` e `A0041A0060EpicFightHooks.onDeath(...)`.
- O repositório atual não publica um `eligible_kill` canônico reutilizável que permita ao Chat 2 unificar os dois sem inventar regras locais.
- `CombatPerkAvailabilityRuntime` marca A0042 indisponível antes da compra e `A0041A0060RuntimeState.ranks(...)` mascara qualquer rank legado, impedindo ambos os producers de armar a janela.
- `consumeBattleHarvestOnHit(...)` e o estado de janela/cooldown permanecem presentes para futura reativação sem redesign.
- Refund de Stamina continua 0 sem receipt causal.

## Provider→árvore

- **RPG Skill Tree:** authority futura do `eligible_kill`, anti-abuso, janela, cooldown e dedup.
- **Epic Fight 21.17.3.1:** pode fornecer causalidade do golpe/morte, mas não a classificação anti-abuso inteira.
- **Mobstein 5.4.4:** kill por ally/bodyguard não recebe autoria SCYTHE do dono; kill direto do jogador só poderá qualificar após `eligible_kill` central.
- **Black Arcana:** Backlash não é kill de foice.
- **Volcanoes / Enshrouded:** hazards/estados ambientais não armam a janela.
- **Stage 11.01 itemização:** não fornece kill receipt nem Stamina.

## Pendência técnica devolvida ao futuro provider/Chat 1

### P-A0042-01 — `eligible_kill` anti-farm real

Continua sem capability concreta. Não foi implementado substituto por `Enemy || Player`, tags locais ou heurística.

### P-A0042-02 — unificar producers de kill

Fica bloqueada pelo mesmo boundary: enquanto A0042 estiver indisponível, nenhum dos dois producers produz efeito. Quando `eligible_kill` existir, ambos deverão consumir o mesmo receipt/dedup.

## Pendência Chat 3

- testar compra de A0042 retornando `UNAVAILABLE_NODE` sem gasto de ponto/replay reservation;
- testar rank legado mascarado sem janela/Marca transferida;
- testar que os dois listeners permanecem inertes enquanto a availability for false.

## Testes exigidos

- kill legítima, trivial, repetida, training, summon/companion, fake player quando o provider existir;
- Marca Madura preexistente ao killing root;
- dois listeners sem dupla ativação/bypass;
- alvo diferente obrigatório;
- cooldown 10/9/8;
- refund omitido sem receipt causal.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura SCYTHE:** Scythe Simply só entra quando Epic Fight Compat resolve `SCYTHE`.
- **Execute provider-owned:** o execute Implicit não cria por si só `eligible_kill` nem uma segunda ativação de Colheita.
- **Causalidade permitida:** se o provider comprovar que a morte por execute pertence inequivocamente ao mesmo root direto SCYTHE do jogador, esse único abate pode ser submetido uma vez ao serviço anti-abuso `eligible_kill`; evento execute/derived separado ou apenas ownership da arma permanece inelegível.
- **Stamina:** execute/ability do Simply não fornece receipt do custo de stamina da ação consumidora.
- **Notion:** `Provider/Mods`, `Hook`, `Fallback` e `Regra` corrigidos; re-fetch PASS.

## Fechamento Chat 2 — 2026-09-01

O Chat 2 escolheu fail-closed integral em vez de fabricar a policy anti-abuso ausente. Isso encerra a implementação do contrato disponível, mas não equivale a `IMPLEMENTAÇÃO CONFIRMADA`.

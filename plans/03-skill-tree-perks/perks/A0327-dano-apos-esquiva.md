# A0327 — Dano Após Esquiva

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0327` — https://app.notion.com/3c569db9f0db81d78753c1e1515e3d11
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

Quando um `PERFECT_DODGE_RECEIPT_V1` válido provar que uma ação DODGE própria evitou uma ameaça hostil real, A0327 ativa `RPG_POST_DODGE_OFFENSE` por **40 ticks (2 s)**.

Durante a janela, outcomes ofensivos **DIRETOS** elegíveis causados pelo jogador recebem:

- rank 1: dano ×1,05;
- rank 2: ×1,10;
- rank 3: ×1,15.

A janela não possui cargas, não é consumida no primeiro ataque e não acumula magnitude. Novo perfect dodge válido apenas renova `expires_at` para `now + 40`.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0324 Esquiva Econômica ≥2 + A0325 Janela de Esquiva ≥1.

A0325 está `UNAVAILABLE_NODE` no snapshot atual; portanto A0327 também está indisponível por **closure transitiva**.

Mesmo após A0325 abrir, A0327 exige:

- receipt claimável de perfect dodge sobre ameaça hostil real;
- compositor ofensivo direto seguro para o outcome elegível.

A auditoria atual não comprovou uma lane genérica pronta na `main` que cubra melee + `RANGED_PHYSICAL` + `MAGIC` direto para esta perk sem criar um pipeline paralelo.

Compra deve falhar antes de gastar PP. Allocation legada indisponível é refundável/migrável e vale 0 PP para gates/thresholds.

## Providers e authority

- Epic Fight 21.17.3.1 expõe `ON_DODGE` server-side, mas isso não contorna A0325 nem prova perfect dodge ampliável.
- ParCool 4.0.0.3 / Epic ParCool 21.0.0 não ativam a perk por animação ou i-frame.
- RPG Skill Tree é owner da janela buff e da contribuição de dano, mas não pode inventar receipt de perfect dodge ou compositor universal sem binding real.

## Contrato futuro — receipt

`PERFECT_DODGE_RECEIPT_V1` deve provar no mínimo:

- owner correto;
- ação DODGE real;
- ameaça hostil real evitada;
- identidade estável do threat/outcome;
- claim-once.

Block, parry, guard, invulnerabilidade externa, dodge sem ameaça ou animação isolada não ativam.

## Contrato futuro — janela

Receipt válido cria ou renova:

`RPG_POST_DODGE_OFFENSE {owner_uuid, source_receipt_id, expires_at = now + 40}`

Novo receipt durante a janela **não soma magnitude e não acumula duração** além de `now + 40` a partir do evento novo.

## Contrato futuro — dano

Em outcome ofensivo DIRETO elegível enquanto `now < expires_at`, aplicar uma única contribuição:

`RPG_POST_DODGE_DIRECT_DAMAGE = ×1.05 / ×1.10 / ×1.15`

antes da mitigação final apropriada do pipeline daquele provider.

Deduplicar por `root/direct_outcome_id + RPG_POST_DODGE_DIRECT_DAMAGE` ou identidade equivalente concreta.

## Exclusões

Não aplicar a:

- DoT;
- aura;
- retaliação;
- chain/ricochet derived;
- summon/companion;
- máquina/fake-player;
- dano recursivo criado pela própria perk.

Esses outcomes não recebem segunda aplicação, crítico, Mastery ou proc por causa de A0327.

## Fallback / fail-closed

Enquanto A0325 estiver indisponível, A0327 não é comprável. Depois que A0325 abrir, provider/outcome sem compositor direto seguro simplesmente omite A0327 naquele outcome.

Não usar Strength/MobEffect, dano derivado separado ou inferência por ataque que errou.

## Anti-abuso e deduplicação

- um `threat_outcome_id` arma/renova no máximo uma vez;
- janela não stacka;
- mesmo direct outcome recebe uma contribuição;
- derived não herda do root automaticamente;
- companions não herdam autoria do jogador;
- zero damage/cancelamento não cria outcome ofensivo válido.

## Testes destinados ao Chat 3

1. snapshot atual: indisponível por closure A0325;
2. allocation legada indisponível = 0 PP e refundável/migrável;
3. receipt futuro válido abre 40 ticks exatos;
4. novo receipt renova para `now+40` sem stack de magnitude;
5. todos os direct outcomes elegíveis na janela recebem ×1,05/1,10/1,15;
6. janela não é consumida no primeiro ataque;
7. DoT/aura/retaliação/derived/summon/máquina não recebem efeito;
8. mesmo outcome não recebe multiplicador duas vezes;
9. block/parry/invulnerabilidade/dodge sem ameaça não ativam;
10. multiplayer/dedicated server, cleanup e expiry determinísticos.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`. Não bypassar a dependência A0325 e não implementar buff genérico de Strength ou listener universal de dano sem compositor direto seguro.

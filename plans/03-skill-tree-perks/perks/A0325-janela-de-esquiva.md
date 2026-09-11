# A0325 — Janela de Esquiva

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0325` — https://app.notion.com/3c569db9f0db81e09a6de0ec4f273e28
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0325 amplia **a tolerância relativa da janela semântica de reconhecimento de PERFECT_DODGE**, em +4% por rank:

- rank 1: janela ×1,04;
- rank 2: ×1,08;
- rank 3: ×1,12.

A perk não adiciona invulnerabilidade, não estende i-frames, não cancela dano e não altera recovery/cooldown/distância da ação DODGE.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0324 Esquiva Econômica ≥2.

Gate técnico futuro: ação DODGE server-authoritative com **janela/tolerância nativa positiva e mutável de PERFECT_DODGE** vinculada ao mesmo `dodge_action_id`.

Epic Fight 21.17.3.1 expõe `ON_DODGE` como prova de dodge bem-sucedido, mas isso não torna a perk implementável porque não foi encontrada API pública segura que permita ampliar a janela de reconhecimento.

Compra deve falhar antes de gastar PP. Allocation legada indisponível permanece refundável/migrável e vale 0 PP para gates/thresholds.

## Providers e authority

- Epic Fight 21.17.3.1: pode provar sucesso da esquiva via `ON_DODGE`, mas a duração/janela relevante é interna/fixa no snapshot auditado.
- ParCool 4.0.0.3: cancela dano nos primeiros ticks de Dodge em sua própria mecânica; isso representa i-frame/invulnerabilidade da ação e **não pode ser reinterpretado como janela semântica de perfect dodge**.
- Epic ParCool 21.0.0: bridge, não cria segundo owner da janela.
- RPG Skill Tree: owner da perk, mas não pode fabricar o conceito que o provider não expõe.

## Contrato futuro obrigatório

Um provider deve publicar algo equivalente a `PERFECT_DODGE_WINDOW_V1`:

`{dodge_action_id, window_owner_id, native_start, native_end, provider_action_start, provider_action_end, provider_max_window?}`

Calcular:

`candidate = native_window × (1 + 0.04 × rank)`

`final_window = min(candidate, provider_max_window)` quando houver cap provider-native.

A janela final deve permanecer dentro dos limites temporais da própria ação DODGE. Nenhum tick adicional é convertido em invulnerabilidade.

O sucesso pode produzir um receipt separado `PERFECT_DODGE_RECEIPT_V1` para consumidores posteriores, como A0327.

## Fallback / fail-closed

Sem janela semântica mutável, node indisponível. Não substituir por:

- i-frames;
- invulnerabilidade;
- recovery/cooldown;
- stamina refund;
- dano;
- Slow Time;
- cancelamento genérico de dano;
- extensão de `DodgeLocationIndicator` por heurística.

## Anti-abuso e deduplicação

- mesma ação DODGE = uma janela owner/provider;
- bridges não ampliam cumulativamente o mesmo intervalo;
- sucesso `ON_DODGE` não é usado como prova retroativa de uma janela maior;
- A0325 não cria receipt de perfect dodge a partir de dodge comum sem ameaça elegível;
- nenhum efeito gera Mastery.

## Testes destinados ao Chat 3

1. snapshot atual: compra fail-before-spend;
2. allocation legada indisponível = 0 PP e refundável/migrável;
3. `ON_DODGE` isolado não abre availability;
4. ParCool i-frame não é reinterpretado como perfect-dodge window;
5. provider futuro: ranks 1–3 aplicam ×1,04/1,08/1,12 sobre a janela nativa;
6. clamp ao action interval/provider max window;
7. nenhuma alteração de i-frame, recovery, cooldown ou stamina;
8. mesma `dodge_action_id` não recebe ampliação duplicada por bridges;
9. mismatch/ausência do provider mantém fail-closed;
10. multiplayer/dedicated server com availability provider-present/absent.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`. Não implementar A0325 pela extensão de invulnerabilidade nem pelo simples fato de Epic Fight emitir `ON_DODGE`.

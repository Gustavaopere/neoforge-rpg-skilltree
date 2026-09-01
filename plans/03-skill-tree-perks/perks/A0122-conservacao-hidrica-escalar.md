# A0122 — Conservação Hídrica: Escalar

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED.  
**Runtime atual:** `UNAVAILABLE_NODE`; A0116/A0118 dependem de P-0037 e não há `METABOLIC_CLIMB`/`HYDRATION_CLIMB` causal.  
**Notion:** https://app.notion.com/p/3c569db9f0db81738cb3fdd42e49782e

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Conservação Hídrica — Mobilidade Avançada; camada 2; função: Ramo.
- 4 ranks; 1 PP/rank.
- Gate: A0116 ≥2 + A0118 ≥2 + Gateway SURVIVAL + provider HYDRATION real + parcela causal de escalada.

## Contrato congelado

Reduz em **3% por rank**, até **12%**, somente `HYDRATION` positiva causada pela mesma escalada legítima. Todas as eficiências HYDRATION do evento compartilham teto de **30%**.

A0122 não cria sede artificial. Como Thirst Was Reclaimed deriva seu custo corporal de sinais reais, a mera classificação ParCool não cria `HYDRATION_CLIMB` se não existir custo corporal causal da escalada.

## Authority e pipeline

- HYDRATION owner: **Thirst Was Reclaimed 3.0.4**.
- Thirst Was Fixed 2.1.5 é compat/fix, nunca owner paralelo.
- ParCool **4.0.0.3** + Epic ParCool 21.0.0 apenas identificam/classificam a ação.
- RPG Skill Tree pode agregar a eficiência somente por adapter causal versionado.

Ordem: `CLIMB action_id -> METABOLIC causal resolvido -> TWR produz/cota HYDRATION da mesma ação -> agregar eficiências HYDRATION -> cap 30% -> commit provider uma vez`.

Não escrever diretamente em thirst, não observar delta da barra para inferir causalidade e não converter Stamina em hidratação.

## Availability / fail-closed

Enquanto A0116/A0118 forem indisponíveis, `BodyCostResolver`/P-0037 faltar, adapter TWR obrigatório faltar ou `HYDRATION_CLIMB` não puder existir causalmente, A0122 é não comprável; allocation legado = 0 PP efetivo e continua reembolsável/migrável.

Após bindings globais existirem, receipt hídrico ausente em uma ação específica apenas omite o proc daquele evento.

## Anti-abuso / dedup

Uma escalada causal gera no máximo uma resolução hídrica. Movimento forçado/passivo, veículos, correntes, contraptions, teleports e callbacks duplicados não geram custo.

## Projetos próprios

RPG Skill Tree é consumer/resolver futuro; Volcanoes não converte pressão/calor em HYDRATION de escalada; Enshrouded e Black Arcana não fornecem essa parcela.

## Pendências para Chat 2

- `P-A0122-01` — availability transitiva A0116/A0118 + `HYDRATION_CLIMB`.
- `P-A0122-02` — adapter causal TWR 3.0.4, sem polling/direct writes.
- `P-A0122-03` — correlacionar exatamente a mesma `action_id` após METABOLIC; cap 30% e uma cobrança.
- `P-A0122-04` — lifecycle/respec/rules reload e regressões forced/passive/dedup.

## Testes exigidos ao Chat 3

Purchase fail-before-spend; PP legado 0; predecessor/provider absent; ParCool 4.0.0.3; escalada com e sem parcela corporal; TWF não-owner; Stamina não convertida; mesma action METABOLIC→HYDRATION; cap 30%; dedup; rollback; multiplayer; lifecycle; GameTests/build/JAR/dedicated-server smoke quando aplicável.

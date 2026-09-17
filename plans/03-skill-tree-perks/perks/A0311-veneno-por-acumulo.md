# A0311 — Veneno por Acúmulo

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE` por Specialist Natureza/A0183 e dependências locais.
- **Fonte canônica:** Notion `A0311` — https://app.notion.com/3c569db9f0db81dba9b6dc3cf810e0fb
- **Persistência:** fetch 2026-09-05.

## Contrato aprovado

A0311 mantém um ledger por `owner + target` das **ações NATURE diretas, distintas e elegíveis** confirmadas nos últimos 100 ticks. O limiar é:

- rank 1: 6 créditos;
- rank 2: 5 créditos.

Ao atingir o limiar, e somente se o target continuar válido, é emitida uma única tentativa provider-native de aplicar/renovar **POISON real** por 60t / 72t. Um commit positivo inicia cooldown de 80t para `owner + target`. A duração pode receber o modificador de A0302 **uma única vez** quando A0302 estiver operacional; A0311 nunca altera amplifier nem cadence/pulses do provider.

## Gate e closure

Compra exige Specialist Natureza/A0183, A0301 ≥2 e (A0302 ≥1 **ou** A0310 ≥1), conforme catálogo. A0183 permanece indisponível e A0301 também está na mesma closure; logo A0311 é `UNAVAILABLE_NODE` e falha antes do gasto.

## Providers e pipeline canônico

- RPG Skill Tree: ledger, threshold, claim-once e composição.
- Provider de POISON: authority da aplicação/renovação e lifecycle do efeito. NeoForge `MobEffectEvent.Added` pode observar lifecycle/source, mas não substitui um receipt de aplicação com identidade/owner.
- Iron's/Ars/Toxony/vanilla só entram por adapters explícitos quando uma ação puder ser classificada NATURE e causalmente atribuída.
- A0302, quando disponível, atua no candidate-duration precommit; nunca remove/reaplica Poison para simular duração.

## Deduplicação e anti-abuso

Crédito é por ação/root distinta. Retransmissão, hit duplicado, derived damage, pulse de DoT e callback duplicado da mesma ação não aumentam o ledger. O ledger é rolling 100t, bounded e por target. Cooldown só começa após aplicação/renewal efetivamente commitada; falha de provider não consome limiar como sucesso.

## Fallback

Sem closure aberta ou sem receipt NATURE/application identity: node indisponível ou contribuição 0 conforme a fase. Não aplicar Poison por `addEffect` ad hoc como substituto de adapter provider-native, não aumentar amplifier e não converter outros toxics em POISON sem classifier.

## Testes obrigatórios para Chat 3

1. fail-before-spend por A0183/dependencies;
2. thresholds 6/5 em janela 100t;
3. ação duplicada/root repetido conta uma vez;
4. callbacks derived/DoT não contam;
5. aplicação real dura 60/72t e não altera amplifier/cadence;
6. cooldown 80t começa somente após commit positivo;
7. falha de aplicação não produz falso cooldown/sucesso;
8. A0302, quando presente, modifica duração uma única vez;
9. provider absent/version mismatch e target inválido falham fechado;
10. multiplayer/dedicated server com ledgers owner+target independentes.

## Handoff Chat 2

Não inventar application receipt nem polling de PotionEffect. A abertura futura do Specialist não elimina os blockers de causalidade/identity.
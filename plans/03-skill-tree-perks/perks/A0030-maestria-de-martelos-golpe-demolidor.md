# A0030 — Maestria de Martelos — Golpe Demolidor

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** NÃO CONFIRMADA; `P-A0030-01` aberta e depende também do receipt heavy de A0029.
- **Notion:** `3c569db9-f0db-816e-a298-ddacef4793c4`.

## Contrato canônico

- A0028 + A0029 + mastery `epicfight:heavy` ≥80 + gateway `epic_hammer`.
- Mastery é finita: +10 por tipo hostil inédito em hit direto provider-native HAMMER/heavy, via `DiscoveryProgress`; 8 tipos distintos satisfazem 80.
- Quebra real de guarda/postura causada pelo próprio jogador com martelo abre Janela Demolidora de 4 s no alvo.
- Próximo hit heavy direto no mesmo alvo: +20% dano físico elegível e +25% impacto; encerra a janela.
- O mesmo resultado não pode reabrir a janela.
- Lockout por alvo: 12 s; mastery 90/100 reduz para 11/10 s.
- Sem receipt nativo de guard-break, capstone indisponível; sem heavy receipt, hit comum não consome a janela.

## Auditoria — 9 eixos

1. Gates: PASS após correção da Mastery.
2. Integração: PASS de design — guard-break/heavy/impact provider-native.
3. Identidade: PASS — capstone de demolição causal.
4. Topologia: PASS — terminal camada 4.
5. Especializações: PASS — `TERMINAL_EXTERIOR: MARTIAL/MARTELOS`.
6. PT-BR: PASS.
7. Notion: PASS após hardening causal/anti-farm.
8. NeoVitae: PASS.
9. Providers: PASS de design; runtime carece dos receipts principais.

## Evidência e pendências

- `A0021A0040CombatPolicy.onConfirmedGuardBreak(...)` existe para armar a janela, e `beforeHit(...)` sabe consumir Demolição.
- Não há caller runtime identificado para `onConfirmedGuardBreak(...)` no adapter atual.
- O adapter também envia `heavyConfirmed=false`.
- **P-A0030-01:** Chat 2 deve integrar um receipt server-authoritative de quebra real de guarda/postura, correlacionado ao hit direto HAMMER do jogador, e o heavy receipt seguro usado por A0029. Sem isso, A0030 permanece fail-closed e não pode ser marcada como implementada.
- É proibido inferir guard-break por dano alto, stagger genérico, Armor, vida, animação ou queda de stamina.
- `ARCANE_BACKLASH` e companions Mobstein não abrem/consomem Demolição nem concedem Mastery ao dono.

`P-A0030-01` bloqueia `IMPLEMENTAÇÃO CONFIRMADA`, não o design.

# A0030 — Maestria de Martelos — Golpe Demolidor

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** NÃO CONFIRMADA / FAIL-CLOSED VALIDADO EM CI na PR #242; `P-A0030-01` permanece aberta e depende também do receipt heavy de A0029.
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
9. Providers: PASS de design; runtime permanece fail-closed por ausência dos receipts principais.

## Evidência e pendências

- `A0021A0040CombatPolicy.onConfirmedGuardBreak(...)` existe para armar a janela, e `beforeHit(...)` sabe consumir Demolição.
- Na fonte real do Epic Fight 21.17.3.1, `GuardSkill` calcula `blockType = canAfford ? GUARD : GUARD_BREAK`, mas `blockType` é variável interna do método de guarda.
- `GuardSkill.dealEvent(...)` expõe o resultado geral `BLOCKED` e chama `onAttackBlocked(...)` no attacker patch, sem publicar o `BlockType.GUARD_BREAK` ou um receipt causal attacker-side equivalente.
- Inferir quebra por animação, som, queda de stamina, dano alto, Armor ou stagger genérico é proibido pelo contrato.
- O provider também não oferece heavy receipt inequívoco; `shouldChargeWeapon()` representa combo/charge de Weapon Innate, não heavy.
- **P-A0030-01 permanece aberta:** sem receipt de guard-break da mesma ação HAMMER + heavy receipt seguro, A0030 permanece indisponível/fail-closed.
- `ARCANE_BACKLASH` e companions Mobstein não abrem/consomem Demolição nem concedem Mastery ao dono.

`P-A0030-01` continua bloqueando `IMPLEMENTAÇÃO CONFIRMADA`, não o design.

## Chat 2 — implementação e regressão — PR #242

- A Mastery `epicfight:heavy` tornou-se finita/anti-farm conforme A0025, tornando o gate 80 alcançável por 8 tipos hostis distintos.
- Regressão JUnit prova que heavy isolado, sem uma janela previamente armada por guard-break causal, não ativa A0030.
- CI #2192 validou o fail-closed e o restante do runtime.
- Estado pós-merge permanece `NÃO CONFIRMADA / FAIL-CLOSED` até os receipts provider-native existirem; não há redesenho silencioso.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura:** Hammer/Greathammer Simply só participam quando Epic Fight Compat resolve `HAMMER`.
- **Sunder não é guard-break:** armor sunder/ignore e demais Implicits/traits do Simply Swords não constituem quebra real de guarda/postura, heavy receipt ou causalidade para abrir/consumir Janela Demolidora.
- **Mastery:** permanece finita e só avança por root HAMMER direto elegível conforme o contrato já aprovado; proc/ability/derived hit Simply não concede descoberta adicional.
- **Fail-closed preservado:** a chegada do stack Simply não resolve `P-A0030-01`; inferência por queda de Armor, stun, dano, tooltip, animação ou stamina continua proibida.
- **Ownership:** Runic/Awakening/sockets/gems/Cataclysm traits continuam provider-owned e não são escalados/reexecutados pelo capstone.
- **Notion:** boundary Simply persistida em quatro propriedades e re-fetch PASS.

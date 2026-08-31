# A0030 — Maestria de Martelos — Golpe Demolidor

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** NÃO CONFIRMADA / FAIL-CLOSED; `P-A0030-01` permanece aberta. Reauditoria técnica também abriu `P-A0030-02` para o Chat 2.
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

- `A0021A0040CombatPolicy.onConfirmedGuardBreak(...)` existe para armar a janela.
- Na fonte real do Epic Fight 21.17.3.1, `GuardSkill` calcula `blockType = canAfford ? GUARD : GUARD_BREAK`, mas `blockType` é variável interna do método de guarda.
- `GuardSkill.dealEvent(...)` expõe o resultado geral `BLOCKED` e chama `onAttackBlocked(...)` no attacker patch, sem publicar o `BlockType.GUARD_BREAK` ou um receipt causal attacker-side equivalente.
- Inferir quebra por animação, som, queda de stamina, dano alto, Armor ou stagger genérico é proibido pelo contrato.
- O provider também não oferece heavy receipt inequívoco; `shouldChargeWeapon()` representa combo/charge de Weapon Innate, não heavy.
- **P-A0030-01 permanece ABERTA / BLOQUEANTE:** sem receipt de guard-break da mesma ação HAMMER + heavy receipt seguro, A0030 permanece indisponível/fail-closed.
- `P-A0030-02` — **ABERTA PARA CHAT 2**: o código latente consome a Janela Demolidora no PRE. Deve migrar para reservation→commit por actor/target/root; PRE apenas reserva a oportunidade, POST direto/hostil/com dano >0 consome. Cancelamento/dano zero deve preservar a janela.
- `ARCANE_BACKLASH` e companions Mobstein não abrem/consomem Demolição nem concedem Mastery ao dono.

`P-A0030-01` continua bloqueando `IMPLEMENTAÇÃO CONFIRMADA`. Corrigir `P-A0030-02` não autoriza remover o fail-closed provider-side.

## Chat 2 — implementação e regressão — PR #248

- A Mastery `epicfight:heavy` tornou-se finita/anti-farm conforme A0025, tornando o gate 80 alcançável por 8 tipos hostis distintos.
- Heavy isolado, sem uma janela previamente armada por guard-break causal, não ativa A0030.
- A implementação/fail-closed foi mergeada pela PR #248.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura:** Hammer/Greathammer Simply só participam quando Epic Fight Compat resolve `HAMMER`.
- **Sunder não é guard-break:** armor sunder/ignore e demais Implicits/traits do Simply Swords não constituem quebra real de guarda/postura, heavy receipt ou causalidade para abrir/consumir Janela Demolidora.
- **Mastery:** permanece finita e só avança por root HAMMER direto elegível conforme o contrato já aprovado; proc/ability/derived hit Simply não concede descoberta adicional.
- **Fail-closed preservado:** a chegada do stack Simply não resolve `P-A0030-01`; inferência por queda de Armor, stun, dano, tooltip, animação ou stamina continua proibida.
- **Ownership:** Runic/Awakening/sockets/gems/Cataclysm traits continuam provider-owned e não são escalados/reexecutados pelo capstone.
- **Notion:** boundary Simply persistida em quatro propriedades e re-fetch PASS.

## Auditoria técnica pré-Chat 2 — 2026-08-31

- Reprodução transitória em CI #2302 confirmou `P-A0030-02`; a implementação experimental posterior foi descartada e não integra esta entrega.
- O Chat 2 deve corrigir o sequencing latente sem inventar receipts para resolver `P-A0030-01`.
- Testes exigidos: PRE preserva janela, POST válido consome, POST zero/cancelado preserva e root concorrente não pode consumir reserva alheia.
- O merge/fechamento não pertence a este chat.

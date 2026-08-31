# A0029 — Quebra de Postura

## Estado

- **Design:** APROVADO após auditoria retroativa.
- **Implementação:** NÃO CONFIRMADA / FAIL-CLOSED; `P-A0029-01` permanece aberta. Reauditoria técnica também abriu `P-A0029-02` para o Chat 2.
- **Notion:** `3c569db9-f0db-81eb-8a3e-c67450cbc041`.

## Contrato canônico

- A0026 ≥2 + A0027 ≥1 + gateway `epic_hammer`.
- Com 3 Abalo, o próximo hit direto HAMMER/heavy inequivocamente confirmado consome as 3 cargas.
- Rank 1/2: +30%/+45% pressão de guarda/postura e +10%/+15% impacto.
- Se heavy for confirmado, mas pressão de guarda não existir, manter impacto-only.
- Restauração de 10% do custo de stamina somente se a mesma ação causar quebra real e o custo exato pago for observável; cooldown 8 s. Sem receipt de custo, omitir apenas a restauração.

## Auditoria — 9 eixos

1. Gates: PASS.
2. Integração: PASS de design — Epic Fight owner de heavy/impact/stamina/guarda.
3. Identidade: PASS — gasto de preparação em ataque pesado real.
4. Topologia: PASS — Notable convergente.
5. Especializações: PASS — exterior.
6. PT-BR: PASS.
7. Notion: PASS após endurecimento causal.
8. NeoVitae: PASS.
9. Providers: PASS de design; heavy receipt público inequívoco permanece indisponível no provider auditado.

## Evidência e pendências

- O adapter continua enviando `heavyConfirmed=false`; A0029 não ativa no caminho Epic Fight real sem receipt seguro.
- Na fonte real do Epic Fight 21.17.3.1, `PlayerPatch.getDamageSource(...)` define `chargeWeapon` quando a animação é combo/`ComboAttacks.COMBO`; `ServerPlayerPatch` usa `shouldChargeWeapon()` somente para carregar o recurso da Weapon Innate após o hit. Isso não é receipt de heavy attack.
- **P-A0029-01 permanece ABERTA / BLOQUEANTE:** é proibido promover `shouldChargeWeapon`, animação, dano, arma lenta, impacto ou charge time estimado a heavy receipt.
- `P-A0029-02` — **ABERTA PARA CHAT 2**: o código latente consome 3 Abalos no PRE quando `heavyConfirmed=true`. Mesmo enquanto A0029 permanece fail-closed, o state/policy deve ser endurecido para reservation→commit por actor/target/root; consumo apenas no POST direto/hostil/com dano >0. O consumer deve ocorrer antes de A0028 produzir Abalo do mesmo hit, resultando `3 → 0 → 1` quando ambos forem válidos.
- A parcela de stamina continua fallback legítimo: sem custo exato da mesma ação, omitir somente a restauração de 10% sem redesenhar a perk.
- `ARCANE_BACKLASH` e companions Mobstein não satisfazem heavy, guard-break ou stamina receipt.

`P-A0029-01` continua bloqueando `IMPLEMENTAÇÃO CONFIRMADA`. Corrigir `P-A0029-02` não autoriza remover o fail-closed provider-side.

## Chat 2 — implementação e regressão — PR #248

- A classificação HAMMER foi corrigida para provider-native, eliminando uma fonte paralela de falso positivo.
- A rota real continua fail-closed quando `heavyConfirmed=false`.
- A implementação/fail-closed foi mergeada pela PR #248.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura:** Hammer/Greathammer Simply só entram quando Epic Fight Compat resolve a arma como `HAMMER`.
- **Receipts não herdados:** armor sunder/ignore, attack-speed proc, Unique ability, Runic Power, Awakening, gem power ou trait Cataclysm não satisfaz `heavyConfirmed`, guard pressure, guard break ou custo causal de stamina.
- **Sem inferência por efeito:** queda de Armor, stun, dano elevado, tooltip, animação ou o próprio sunder do provider não podem ativar A0029.
- **Coexistência:** efeitos Simply podem ocorrer no mesmo root por seu pipeline, mas não consomem nem financiam as 3 cargas de Abalo e não criam uma segunda Quebra de Postura.
- **Fail-closed:** `P-A0029-01` permanece integralmente aberta após esta reauditoria.
- **Notion:** boundary Simply persistida e re-fetch PASS.

## Auditoria técnica pré-Chat 2 — 2026-08-31

- Reprodução transitória em CI #2302 confirmou `P-A0029-02`; a implementação experimental posterior foi descartada e não integra esta entrega.
- O Chat 2 deve corrigir o sequencing latente mesmo sem resolver `P-A0029-01`, para que a futura disponibilidade de heavy receipt não ative consumo PRE incorreto.
- Testes exigidos: PRE não destrutivo, POST válido, POST zero/cancelado e ordem A0029→A0028.
- O merge/fechamento não pertence a este chat.

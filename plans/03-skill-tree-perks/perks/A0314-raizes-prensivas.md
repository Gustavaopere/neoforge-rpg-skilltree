# A0314 — Raízes Prensivas

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0314` — https://app.notion.com/3c569db9f0db81c9bf90e5da7bfad82e
- **Persistência:** fetch 2026-09-05.

## Contrato aprovado

A0314 acumula por `owner + target` créditos de **ações NATURE diretas e distintas** numa janela de 100t:

- rank 1: 5 créditos;
- rank 2: 4 créditos.

`GROVE_ROOT_ASSIST` futuro de A0317 pode contribuir com no máximo 2 créditos por `target + aura`, nunca é ação direta e jamais pode satisfazer sozinho o limiar.

Ao alcançar o threshold, tenta um controle real `ROOT/IMMOBILIZE` por 25t / 35t. Diminishing returns por `owner+target`:

1. primeiro commit: ×1,00;
2. segundo dentro de 400t: ×0,50;
3. terceiro: ×0,25;
4. enquanto a faixa ×0,25 estiver vigente, não há novo reapply;
5. reset após 400t sem controle commitado.

Boss/Elite não recebe root completo: usa **Slowness II** com coeficiente especial ×0,50 aplicado depois do DR. Posture só pode participar por adapter seguro e não é requisito da identidade da perk.

## Gate e closure

Compra exige Specialist Natureza/A0183, A0307 ≥2 e Nature Mastery ≥60. A0307 e A0183 estão indisponíveis; logo purchase fail-before-spend.

## Authority e boundaries

- RPG Skill Tree: ledger de créditos, DR, claim e composição.
- Provider de controle: authority de root/immobilize/slowness e commit real.
- Boss/Elite precisa de classifier explícito.
- Ação NATURE exige root/action identity; VFX, bloco de grama ou escola genérica não bastam.

## Anti-abuso

Créditos repetidos da mesma root action não acumulam. Assist de A0317 é bounded. DR só avança após controle realmente commitado; falha de aplicação não pune o alvo. Reentrada/reload não reseta indevidamente o DR persistente exigido pelo contrato.

## Fallback

Sem control seam/classifier seguro, tentativa falha fechada. Boss/Elite ambíguo não deve receber root completo por aproximação. Não substituir root por dano, Mastery ou outro CC genérico.

## Testes obrigatórios para Chat 3

1. fail-before-spend;
2. thresholds 5/4 com ações distintas em 100t;
3. root 25/35t após threshold;
4. A0317 assist limitado a 2 por target+aura e insuficiente sozinho;
5. DR ×1/×0,5/×0,25 e bloqueio de reapply na faixa mínima;
6. reset após 400t sem commit;
7. falha de provider não avança DR;
8. Boss/Elite usa Slowness II + coeficiente ×0,50 pós-DR, nunca root completo;
9. duplicate/root replay não acumula;
10. provider/classifier absent, multiplayer e dedicated server.

## Handoff Chat 2

Não criar um segundo CC pipeline nem inferir boss/elite ou NATURE por heurística.
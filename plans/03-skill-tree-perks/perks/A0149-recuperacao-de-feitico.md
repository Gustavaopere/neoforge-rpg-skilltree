# A0149 — Recuperação de Feitiço

## Estado Chat 1

**DESIGN APROVADO COM HOOK IRON'S COMPROVADO.**

A perk é implementável no Iron's 3.16.3 usando a conclusão real do cast e a janela mutável pré-commit do cooldown, com confirmação pós-commit. Ars Nouveau e outros providers permanecem fail-closed até oferecerem contratos equivalentes.

## Contrato

- Domínio ARCANE; ramo Técnica de Conjuração — Rotação; camada 3; Notable.
- 1 rank; 2 PP.
- Pré-requisitos: A0148 ≥2 + A0147 ≥2.
- Primeira magia elegível não-INSTANT concluída abre janela de 5 s / 100 ticks.
- Segunda magia elegível com `spell_id` diferente, concluída dentro da janela, pode receber `cooldown_final ×0,85` uma única vez.
- A primeira magia não é alterada.
- A0149 entra em cooldown interno de 8 s / 160 ticks somente após commit confirmado da redução.

## Iron's Spells 'n Spellbooks 3.16.3

Snapshot: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

Hooks comprovados:

1. `SpellOnCastEvent` fornece `spell_id`, `castSource` e conclusão efetiva;
2. `SpellCooldownAddedEvent.Pre` expõe `effectiveCooldown` mutável antes do commit;
3. `SpellCooldownAddedEvent.Post` confirma que o cooldown foi efetivamente registrado.

`MagicManager.addCooldown(...)` calcula primeiro o cooldown efetivo nativo, publica `Pre`, comita em `PlayerCooldowns`, sincroniza e então publica `Post`.

## Pipeline canônico

1. primeira `SpellOnCastEvent` elegível;
2. resolver `spell_id` no registry e exigir `CastType != INSTANT`;
3. abrir token server-side `{first_spell_id, expires_at}`;
4. segunda `SpellOnCastEvent` distinta dentro da janela arma candidate `{second_spell_id, castSource}` sem consumir a janela;
5. `SpellCooldownAddedEvent.Pre` correspondente: aplicar `max(provider_floor, floor(effectiveCooldown × 0,85))` uma vez, sem cancelar o evento;
6. `SpellCooldownAddedEvent.Post` correspondente confirma commit;
7. somente no Post: consumir janela/candidate e iniciar cooldown interno de 160 ticks.

Se o Post não ocorrer, rollback do candidate; a janela original permanece válida até expirar.

## Gates e exclusões

Não contam:

- primeira magia INSTANT;
- cast cancelado/falhado;
- segunda magia com mesmo ID;
- automação, lacaio ou proc derivado;
- `SCROLL` quando o provider não comita cooldown;
- callback duplicado;
- cooldown zero/não redutível quando não houver commit real.

## Persistência e anti-abuso

- tokens são server-authoritative e bounded por jogador;
- janela usa tempo monotônico do servidor;
- cooldown interno precisa sobreviver relog/dimensão de modo consistente com o contrato da perk;
- nunca iniciar cooldown interno em reservation/pre;
- dedup por player + primeira identidade + segunda identidade + cooldown commit.

## Fallback

Sem conclusão real, ID estável, evento mutável pré-commit ou confirmação pós-commit, omitir aquele provider/spell. Não manter scheduler/map paralelo de cooldown e não reduzir cooldown globalmente.

## Handoff Chat 2

- implementar Iron's exatamente no pipeline reservation→Pre→Post;
- consumir janela e iniciar lockout somente no Post;
- não cancelar `SpellCooldownAddedEvent.Pre`;
- preservar `effectiveCooldown` já calculado pelo provider e modifiers anteriores;
- Ars/outros permanecem fail-closed.

## Testes Chat 3

1. primeira magia não-INSTANT abre janela de 100 ticks;
2. mesma magia não qualifica;
3. segunda magia distinta fora da janela não qualifica;
4. segunda válida recebe ×0,85 uma vez;
5. primeira magia não é alterada;
6. Pre sem Post não consome janela nem inicia cooldown interno;
7. Post confirmado inicia 160 ticks e impede novo proc;
8. SCROLL/no cooldown commit não consome a janela;
9. callbacks duplicados deduplicados;
10. relog/dimensão/restart/persistence do cooldown interno;
11. coexistência com `COOLDOWN_REDUCTION` nativo sem dupla aplicação.
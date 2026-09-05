# Auditoria Chat 1 — bloco residual A0191–A0199

## Resultado

**DESIGN APROVADO / BLOCO RESIDUAL FECHADO PELO CHAT 1**, condicionado à materialização desta documentação na PR do ciclo.

Escopo excepcional explicitamente autorizado: **A0191–A0199 (9 perks)**. A regra geral continua sendo lote exato de 10; esta exceção existe apenas porque A0200+ já foi fechada anteriormente em auditoria especial e não pode ser reutilizada como décima perk.

A0200+ não foi reaberta nem modificada.

| Código | Perk | Resultado Chat 1 | Implementação permitida agora |
|---|---|---|---|
| A0191 | Dano de Sangue I | `UNAVAILABLE_NODE` | não |
| A0192 | Dano de Sangue II | `UNAVAILABLE_NODE` | não |
| A0193 | Resistência a Sangue I | DESIGN IMPLEMENTÁVEL | sim |
| A0194 | Resistência a Sangue II | DESIGN IMPLEMENTÁVEL component-wise | sim: somente resistência `<50%`; BLEED duration fail-closed |
| A0195 | Imbuimento de Sangue | `UNAVAILABLE_NODE` | não |
| A0196 | Disciplina Hemática | `UNAVAILABLE_NODE` all-or-nothing | não |
| A0197 | Maestria de Sangue | `UNAVAILABLE_NODE` transitivo | não |
| A0198 | Dano de Eldritch I | `UNAVAILABLE_NODE` | não |
| A0199 | Dano de Eldritch II | `UNAVAILABLE_NODE` all-or-nothing | não |

**Total:** 7/9 fail-closed, 2/9 implementáveis sem redesign.

## Fontes obrigatórias e modlist

Antes da auditoria foram conferidos os critérios obrigatórios, o protocolo Chat 1 e os quatro guias consolidados de gameplay/sistemas, magia, tecnologia e projetos próprios.

A modlist atual foi conferida na File Library e no Notion. O inventário operacional reconciliado permanece com **573 entradas top-level incluindo NeoForge**. A Auditoria Mestre do Notion registra 572 instalados, 2 removidos e 1 futuro/planejado; TFC permanece removido.

O Catálogo Mestre — Atributos e Passivos (`collection://ade1ec0c-b055-4b84-8004-45ae80c45119`) foi consultado integralmente para A0191–A0199, corrigido onde havia divergência de provider/hook/authority e re-fetched após as escritas: **9/9 persistências confirmadas**.

## Correções materiais no Notion

### BLOOD

- A0191: Iron's 3.16.3 reconhecido como provider BLOOD nativo sem confundir DamageType com DIRECT outcome.
- A0192: contrato reconciliado para janela 120t, segundo `spell_id` diferente, ×1,15 ou ×1,20 quando alvo PRE-HP `<50%`, CD140 apenas no commit; state receipt real obrigatório.
- A0193: classifier defensivo fixado em `BLOOD_MAGIC` exato e bucket único `RPG_BLOOD_RESISTANCE`.
- A0194: preservadas duas parcelas: resistência low-HP implementável e duração BLEED component-wise fail-closed; proibido timer shaving por tick.
- A0195: same-outcome derived BLOOD, 4/8/12% ou 5/10/15% em baixa vida do atacante, sem segundo DamageSource.
- A0196: `HEMATIC_DISCIPLINE` corrigida como pacote inseparável +dano BLOOD / -cura externa; sem external-heal attribution a perk inteira fica unavailable.
- A0197: removida a dependência conceitual de `SpecialistGateResolver`; o contrato agora reutiliza `TreeUnlockResolver`/`TreeUnlockDefinition`/Stage04.01.

### ELDRITCH

- A0198: confirmado que Iron's 3.16.3 **base** já possui school/DamageType ELDRITCH nativos; addons não são necessários para a identidade, apenas ampliam conteúdo.
- A0199: reconciliado o contrato canônico all-or-nothing: janela 120t, segundo spell ELDRITCH diferente ×1,18 e, no mesmo commit, regen nativa positiva do recurso primário ×0,85 por 80t; CD160 apenas no commit conjunto.

## Evidência provider-native

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

`SchoolRegistry` confirma:

- `blood` com `ISSDamageTypes.BLOOD_MAGIC`;
- `eldritch` com `ISSDamageTypes.ELDRITCH_MAGIC`.

Essas identidades são prova de **classificação elemental**. Elas não provam automaticamente:

- jogador autor do cast;
- direct-vs-derived;
- root/action/outcome identity;
- state ownership;
- primary resource regen;
- deduplicação.

Por isso são suficientes para A0193/A0194 defensivas e insuficientes, isoladamente, para A0191/A0192/A0195/A0196/A0198/A0199 ofensivas.

### Vampirism

Vampirism 1.10.12 e compatibilidades mantêm economia de sangue, lifesteal, custos e curas próprios. Nenhum desses conceitos vira automaticamente:

- `BLOOD_MAGIC`;
- `DIRECT_MAGIC_OUTCOME_V1`;
- estado BLOOD do Skill Tree;
- recurso hemático paralelo;
- external healing attribution.

### Addons Eldritch

Discerning The Eldritch e Deeper and Darker Spellbooks podem ampliar a school ELDRITCH. Eles continuam subordinados ao classifier/adapter exato e aos mesmos contracts DIRECT/state/resource; presença do addon não bypassa gates.

### NeoForge

`LivingDamageEvent.Pre` permanece boundary mutável aprovado para A0193/A0194. Os dois nodes devem usar o mesmo `ElementalDamageMitigationResolver` e o mesmo bucket `RPG_BLOOD_RESISTANCE`.

## Black Arcana Stage 06

Freshness auditada: `6b77b5c0ec4f0ff4a8688bb105cef055860c061c`.

Stage 06 é capability real: ritual engine, eventos start/precommit/postcommit/cancel, completion ledger exactly-once, reservas/rollback e providers transacionais.

Não publica, no snapshot atual:

- `BLACK_ARCANA_BLOOD_OUTCOME`;
- direct ELDRITCH outcome do Skill Tree;
- BLOOD/ELDRITCH state-window receipt;
- primary casting resource regen modifier.

Portanto ritual, corruption, strain, soul ou estética ocultista não são promovidos a BLOOD/ELDRITCH por tema.

## Auditoria individual

### A0191 — Dano de Sangue I

- +3%/rank, máximo +12%, direct BLOOD player-owned.
- A0144≥2 + Gateway ARCANE.
- Iron's BLOOD identity real, mas DIRECT ausente.
- Resultado: `UNAVAILABLE_NODE` até `DIRECT_MAGIC_OUTCOME_V1`.

### A0192 — Dano de Sangue II

- A0191≥3 + Blood Mastery≥20.
- real BLOOD state abre janela 120t.
- segundo direct BLOOD spell diferente no mesmo alvo: ×1,15; ×1,20 se alvo PRE-HP `<50%`.
- CD140 só no commit.
- exige DIRECT + `BLOOD_STATE_WINDOW_RECEIPT_V1`.
- Resultado: `UNAVAILABLE_NODE`.

### A0193 — Resistência a Sangue I

**Implementável.**

- A0191≥1 ou Gateway VITALITY; caminho atual = Gateway VITALITY.
- +4%/rank, máximo +16%.
- Iron's `BLOOD_MAGIC` classifier exato.
- `LivingDamageEvent.Pre` + bucket único `RPG_BLOOD_RESISTANCE`.
- generic bleed/lifesteal/Vampirism físico não qualificam.

### A0194 — Resistência a Sangue II

**Implementável component-wise.**

Parcela atual:

- A0193≥2;
- +4/8/12% no mesmo bucket quando PRE-HP `<50%`;
- exatamente 50% = falso;
- total local A0193+A0194 = 28% max.

Parcela BLEED futura:

- PRE-aplicação `<30%`;
- duração-base ×0,98/0,96/0,94 uma vez por aplicação/renovação;
- exige `BLEED_DURATION_APPLICATION_V1`;
- sem receipt, nenhuma alteração de BLEED; não reduzir tempo restante a cada tick.

### A0195 — Imbuimento de Sangue

- A0191≥2 + Blood Mastery≥15 + mastery melee lane≥20.
- direct BLOOD commit arma 120t.
- melee same-outcome adiciona BLOOD 4/8/12% ou 5/10/15% se atacante PRE-HP `<50%`.
- base canônica pre-target/pre-critical/pre-added-elements.
- exige DIRECT commit + `DERIVED_DAMAGE_COMPONENT_V1`.
- resultado: `UNAVAILABLE_NODE`.

### A0196 — Disciplina Hemática

- A0191≥3 + Blood Mastery≥30 + rota A0192/A0194/A0195.
- primeiro direct BLOOD accepted outcome/action com snapshot `<60%` concede 1 carga; max3; 1/action; 160t shared.
- cada carga: +4% direct BLOOD e -4% external effective healing.
- exige DIRECT + external-heal attribution confiável.
- benefício/tradeoff inseparáveis.
- resultado: `UNAVAILABLE_NODE`.

### A0197 — Maestria de Sangue

- terminal exterior; somente Gate C.
- compra A0196 + Blood Mastery≥80 + rota secundária BLOOD.
- A0196 unavailable torna A0197 unavailable transitivamente.
- reutilizar `TreeUnlockResolver`/`TreeUnlockDefinition` + Stage04.01.
- futuro Gate A/B/C com ≥100 PP `SPECIALIST_REGION:BLOOD`.
- nenhum pacote de poder.

### A0198 — Dano de Eldritch I

- Gateway ARCANE + A0144≥2 + (OCCULT≥2 OU Eldritch Mastery≥10).
- +3%/rank direct ELDRITCH, max +12%.
- Iron's ELDRITCH identity real, DIRECT ausente.
- resultado: `UNAVAILABLE_NODE`.

### A0199 — Dano de Eldritch II

- A0198≥3 + Eldritch Mastery≥20.
- state real abre 120t; segundo spell ELDRITCH diferente mesmo alvo consome.
- commit conjunto: ×1,18 ELDRITCH + regen positiva do recurso primário ×0,85 por 80t.
- CD160 após commit conjunto.
- exige DIRECT + ELDRITCH state window + primary-resource regen modifier.
- resultado: `UNAVAILABLE_NODE` all-or-nothing.

## Authority / causalidade / deduplicação

1. unavailable purchase sempre falha antes do gasto;
2. allocation legado unavailable conta 0 PP para gates/thresholds e permanece refund/migration-safe;
3. school/DamageType classifica, mas não substitui DIRECT/root/outcome;
4. `outcome_id` recebe cada modifier no máximo uma vez;
5. state windows exigem state receipt real, não inferência temática;
6. cooldowns de A0192/A0199 só iniciam no commit elegível;
7. derived BLOOD de A0195 pertence ao mesmo outcome pai e não gera segundo dano/crítico/Mastery/proc;
8. adapters defensivos classificam e um único resolver aplica mitigação;
9. `RPG_BLOOD_RESISTANCE` é bucket único;
10. low-health gates usam PRE state;
11. BLOOD não implica BLEED;
12. A0196/A0199 são all-or-nothing; ausência do tradeoff mantém o node inteiro unavailable;
13. resource authorities dos providers permanecem separadas;
14. Black Arcana ritual authority não é promovida a magic outcome por tema;
15. A0197 usa TreeUnlock canônico, não resolver Specialist paralelo.

## Gate dos quatro projetos próprios

Detalhes completos em `guides/projects/22-capability-delta-a0191-a0199.md`.

- RPG Skill Tree final freshness observada: `be5ddad0b47b47c8a6d724574e1220684b668413`; avanço #381 é CI/cache-only, sem capability deste bloco.
- Volcanoes complete-source provenance: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`; runtime consolidado, sem BLOOD/ELDRITCH delta.
- Enshrouded: `5671114c361be8cbb6fd2dadafdaa05f27d1fe2c`; sem delta pertinente.
- Black Arcana: `6b77b5c0ec4f0ff4a8688bb105cef055860c061c`; Stage06 real, mas authority separada conforme acima.

Nenhuma capability pertinente ficou sem disposição explícita.

## Matriz obrigatória de testes Chat 3

### Todos os unavailable

A0191/A0192/A0195/A0196/A0197/A0198/A0199:

- purchase fail-before-spend;
- legacy rank unavailable =0 PP + refund/migration;
- provider absent/version mismatch/adapter reject;
- reload/login/respec mantém fail-closed;
- ausência de benefício oculto/parcial.

### A0193/A0194

- Iron's BLOOD_MAGIC positivo;
- generic bleed/lifesteal/Vampirism físico negativo;
- A0193 ranks 4/8/12/16%;
- A0194 ranks 4/8/12% com PRE-HP estritamente `<50%`;
- exatamente 50% falso;
- A0193+A0194 max =28% local;
- uma mutação/bucket por evento/root;
- sem BLEED receipt, nenhum timer é alterado;
- futuro BLEED: `<30%` estrito, exatamente30 falso, base-duration once/application, sem tick shaving.

### A0192

- state real/direct-only;
- janela120;
- different spell same target;
- ×1,15 vs ×1,20 por PRE target HP;
- CD140 commit-only;
- dedup/rollback.

### A0195

- committed BLOOD action abre 120t;
- lane allowlist;
- normal 4/8/12 vs low-player-HP 5/10/15;
- exact50 normal;
- same-outcome, canonical base, no second hurt/DamageSource/critical/proc/Mastery;
- spear thrown/ranged/empty hand negative; fist P-0032 fail-closed.

### A0196

- snapshot `<60%` estrito;
- max1 carga/action, max3, shared160;
- damage/heal multipliers exatos;
- external heal only; self/sustain/ambiguous excluded;
- all-or-nothing;
- lifecycle cleanup.

### A0197

- existing TreeUnlock reuse;
- A0196 unavailable blocks purchase;
- Gate C isolated no unlock;
- futuro 99/100 PP boundary, Gate A/B/C independentes, bridge no double count, safe respec;
- nenhum power package/resolver paralelo.

### A0198/A0199

- exact ELDRITCH classifier sem theme inference;
- DIRECT required;
- addons não bypassam receipt;
- Black Arcana ritual negative absent bridge;
- A0199 window120 + different spell + ×1,18 and regen×0,85/80t all-or-nothing + CD160 commit-only;
- no resource conversion/invented regen.

### Validação final

Chat 3 executará, quando aplicável, unit tests, GameTests, provider-present/provider-absent integrations, NeoForge build, dedicated-server smoke, lifecycle/multiplayer regressions e CI completo.

## Handoff Chat 2

Continuar **a mesma branch/PR deste bloco**.

Implementar somente:

- A0193 no único `ElementalDamageMitigationResolver` / `RPG_BLOOD_RESISTANCE`;
- A0194 somente pela parcela defensiva PRE-HP `<50%` no mesmo resolver/bucket.

Preservar fail-closed:

- parcela BLEED duration de A0194;
- A0191/A0192/A0195/A0196/A0197/A0198/A0199.

Não criar localmente:

- direct magic producer;
- BLOOD/ELDRITCH state producer;
- derived damage pipeline;
- external-heal attribution resolver;
- primary-resource regen modifier;
- Specialist resolver paralelo.

Divergência de API/código que altere identidade, efeito, provider, gate, dependência, topologia, authority ou semântica essencial retorna ao Chat 1.

Chat 1 não executa bateria final, não declara `IMPLEMENTAÇÃO CONFIRMADA` e não faz merge.
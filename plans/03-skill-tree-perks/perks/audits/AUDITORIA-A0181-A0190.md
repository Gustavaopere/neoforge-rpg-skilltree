# Auditoria Chat 1 — A0181–A0190

## Resultado do lote

**DESIGN APROVADO / LOTE FECHADO PELO CHAT 1**, condicionado à materialização deste pacote documental na PR do lote.

Escopo exato: **A0181–A0190**. Nenhuma perk A0191+ foi auditada neste ciclo.

Resumo operacional:

| Código | Perk | Resultado Chat 1 | Implementação permitida no snapshot atual |
|---|---|---|---|
| A0181 | Imbuimento de Natureza | `UNAVAILABLE_NODE` | não |
| A0182 | Afinidade de Natureza | `UNAVAILABLE_NODE` | não |
| A0183 | Maestria de Natureza | `UNAVAILABLE_NODE` transitivo | não |
| A0184 | Dano de Sagrado I | `UNAVAILABLE_NODE` | não |
| A0185 | Dano de Sagrado II | `UNAVAILABLE_NODE` | não |
| A0186 | Resistência a Sagrado I | DESIGN IMPLEMENTÁVEL | sim |
| A0187 | Resistência a Sagrado II | DESIGN IMPLEMENTÁVEL com subrota de cura fail-closed | sim, somente rota `<50% HP` |
| A0188 | Imbuimento de Sagrado | `UNAVAILABLE_NODE` | não |
| A0189 | Afinidade de Sagrado | `UNAVAILABLE_NODE` | não |
| A0190 | Maestria de Sagrado | `UNAVAILABLE_NODE` transitivo | não |

**Total:** 8/10 fail-closed, 2/10 implementáveis sem redesign.

## Fontes obrigatórias e estado do pack

Antes do lote foram relidos os critérios obrigatórios, o protocolo Chat 1 e os quatro guias consolidados de gameplay/sistemas, magia, tecnologia e projetos próprios.

A modlist runtime foi conferida na File Library e contra o espelho operacional no Notion antes da auditoria; o inventário reconciliado permanece com **573 entradas top-level incluindo NeoForge**.

O Catálogo Mestre do Notion (`collection://ade1ec0c-b055-4b84-8004-45ae80c45119`) foi consultado para as dez perks, corrigido onde o design nomeava services como se já existissem em runtime e reconsultado após as escritas: **10/10 persistências confirmadas**.

## Provider evidence exata

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot auditado: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

Evidência relevante:

- `SchoolRegistry.HOLY_RESOURCE = irons_spellbooks:holy`;
- `SchoolRegistry.NATURE_RESOURCE = irons_spellbooks:nature`;
- `ISSDamageTypes.HOLY_MAGIC` é DamageType da school HOLY;
- `DamageTypeTagGenerator.HOLY_MAGIC` contém `ISSDamageTypes.HOLY_MAGIC`;
- `HOLY_MAGIC` e `NATURE_MAGIC` integram `Tags.DamageTypes.IS_MAGIC`.

Conclusão: a identidade HOLY/NATURE do provider é real. Essa evidência é suficiente para **classificação defensiva de DamageSource** quando a tag/type exata é observável. Ela **não prova** `DIRECT_MAGIC_OUTCOME_V1`, autoria do cast, direct-vs-derived, spell correlation ou same-outcome composition.

### NeoForge 1.21.1

`LivingDamageEvent.Pre` permanece o boundary mutável aprovado para mitigação defensiva. A0186/A0187 devem entrar na mesma pipeline de mitigação elemental compartilhada já desenhada para FIRE/ICE/LIGHTNING/NATURE.

### Cold Sweat 2.4.2

Cold Sweat permanece owner único da temperatura corporal. A0182/A0189 exigem `MAGIC_THERMAL_PARCEL_V1` antes da mutação térmica; delta global de BODY não preserva action/origin e não pode ser usado como inferência causal.

### Eidolon: Repraised 0.5.0.2

Só participa de HOLY quando uma ação/estado concreto for mapeado por adapter versionado. Ritual, oração, reputação, luz ou tema religioso não são classificadores HOLY por si só.

### Ars Nouveau 5.13.1 / Ars Elemental 0.7.10.1 / Hexalia 1.3.5

Podem participar de NATURE somente por adapter causal/versionado que exponha outcome compatível. Presença temática de natureza/ecologia não autoriza classificação, sustain ou thermal parcel.

### Epic Fight 21.17.3.1 e lanes melee

A0181/A0188 dependem de melee lane canônica + same-outcome derived component. `combat_fist` continua fail-closed conforme P-0032; mão vazia não é fallback.

## Authority e contracts ausentes

O snapshot atual **não contém** os seguintes contracts/services como runtime canônico:

- `DIRECT_MAGIC_OUTCOME_V1`;
- `DERIVED_DAMAGE_COMPONENT_V1`;
- `MAGIC_THERMAL_PARCEL_V1`;
- `HOLY_HEAL_RECEIPT_V1` ou equivalente aprovado;
- serviço canônico identificado de sustain para A0181;
- serviço canônico de contribuição de absorção para A0188.

Também não existe `SpecialistGateResolver` runtime; isso não é blocker por si só. `TreeUnlockResolver`, `TreeUnlockDefinition` e a projeção canônica de investimento do Stage 04.01 já são a pipeline a reutilizar. A0183/A0190 são unavailable por dependency closure, não por ausência de um novo resolver.

## Auditoria individual

### A0181 — Imbuimento de Natureza

- Dependency: A0177≥2 + mastery melee ≥20.
- A0177 permanece unavailable.
- Faltam `DIRECT_MAGIC_OUTCOME_V1` + `DERIVED_DAMAGE_COMPONENT_V1`.
- Sustain não pode ser inventado; serviço canônico não existe na main.
- Resultado: `UNAVAILABLE_NODE`.

Contrato futuro preservado: cast NATURE direto arma 120t; melee outcome elegível adiciona 3/6/9% do `base_weapon_damage_pre_target_mitigation_pre_critical_pre_added_elements` como componente NATURE do mesmo outcome. Sustain futuro somente após dano pai positivo, no máximo 1/40t, pela mesma identidade causal.

### A0182 — Afinidade de Natureza

- Requer A0177≥3 + Nature Mastery≥30 + rota secundária objetiva.
- Requer `MAGIC_THERMAL_PARCEL_V1`.
- NATURE é termicamente neutro por padrão.
- Resultado: `UNAVAILABLE_NODE`.

Contrato futuro: self parcel NATURE explícito ×0,80; exposição mágica NATURE externa explícita ×0,90; sinal quente/frio preservado.

### A0183 — Maestria de Natureza

- Terminal exterior NATURE; somente Gate C.
- A0182 está unavailable.
- Não criar resolver Specialist paralelo; reutilizar unlock/investment canônicos.
- Resultado: `UNAVAILABLE_NODE` transitivo.

### A0184 — Dano de Sagrado I

- +3% HOLY/rank, máximo +12%, apenas em direct magic outcome HOLY do jogador.
- Iron's fornece classifier HOLY real, mas não autoria/direct outcome canônico do Skill Tree.
- Resultado: `UNAVAILABLE_NODE` até `DIRECT_MAGIC_OUTCOME_V1`.

### A0185 — Dano de Sagrado II

- Dependency A0184≥3 + Holy Mastery≥20.
- `RPG_JUDGMENT` é estado transitório próprio, nunca debuff nativo fictício.
- Primeiro direct HOLY pode armar 120t se estado HOLY allowlisted já existia ou target for autoritativamente undead; segundo spell HOLY direto diferente no mesmo target consome e aplica ×1,18; CD 140t.
- `EntityTypeTags.UNDEAD` é rota autoritativa futura; não transforma dano comum contra undead em HOLY.
- Resultado: `UNAVAILABLE_NODE` por A0184/direct-outcome closure.

### A0186 — Resistência a Sagrado I

**Implementável.**

- OR dependency: A0184≥1 ou Gateway VITALITY.
- Como A0184 está unavailable, caminho operacional = Gateway VITALITY.
- `LivingDamageEvent.Pre` server-side.
- classifier base aprovado: Iron's HOLY_MAGIC exato/tag `irons_spellbooks:holy_magic`.
- não existe classifier vanilla HOLY genérico aprovado.
- bucket único `RPG_HOLY_RESISTANCE`.
- +4% por rank, máximo +16%.
- adapters classificam; não aplicam reducers paralelos.

### A0187 — Resistência a Sagrado II

**Implementável parcialmente sem redesign.**

- A0186≥2.
- mesmo `RPG_HOLY_RESISTANCE`.
- +4%/rank adicional, máximo +12%.
- condição implementável agora: vida **PRE-impacto estritamente `<50%`**.
- exatamente 50% = falso.
- futura `holy_heal_window` de 80t só pode existir após `HOLY_HEAL_RECEIPT_V1` com cura HOLY efetivamente aplicada >0.
- ausência do receipt desativa somente a subrota de cura; a perk continua válida pela condição de baixa vida.
- A0186 max + A0187 max = 28% de contribuição local da família quando condição válida; não é cap defensivo global.

### A0188 — Imbuimento de Sagrado

- Dependency A0184≥2 + mastery melee≥20.
- faltam direct outcome + derived same-outcome.
- serviço canônico de contribuição de absorção também não existe.
- resultado: `UNAVAILABLE_NODE`.

Contrato futuro: cast HOLY direto arma 120t; melee outcome adiciona 3/6/9% HOLY do base pre-target/pre-critical/pre-added-elements ao mesmo outcome; absorção própria A0188 0,5/1,0/1,5% max HP por 80t, no máximo 1/60t, refresh-only e sem tocar absorção alheia.

### A0189 — Afinidade de Sagrado

- Requer A0184≥3 + Holy Mastery≥30 + rota objetiva.
- A0184 unavailable.
- `MAGIC_THERMAL_PARCEL_V1` ausente.
- HOLY é termicamente neutro por padrão.
- resultado: `UNAVAILABLE_NODE`.

Contrato futuro: somente thermal parcel HOLY **positivo** explícito; self ×0,80; external magic ×0,90. Delta zero/negativo não é modulado por A0189.

### A0190 — Maestria de Sagrado

- Terminal exterior HOLY; somente Gate C.
- A0189 unavailable.
- `TreeUnlockResolver`/`TreeUnlockDefinition` + Stage 04.01 são pipeline a reutilizar.
- resultado: `UNAVAILABLE_NODE` transitivo.

## Deduplicação, causalidade e anti-abuso

Regras transversais aprovadas:

1. unavailable purchase sempre falha **antes** do gasto;
2. allocation legado de node unavailable conta 0 PP para gates/thresholds e deve permanecer reembolsável/migrável;
3. um `outcome_id` recebe cada perk no máximo uma vez;
4. derived component herda a decisão crítica do pai e nunca cria segundo DamageSource/hurt/proc/Mastery;
5. damage adapters somente classificam/provam; mitigação ocorre em um único resolver;
6. thermal affinity nunca infere ação por BODY before/after;
7. tema, nome, partícula, alvo, provider instalado ou lore não substituem classifier/autoria;
8. fake players, automação, summons, DoT e derived outcomes ficam fora onde o contrato exige direct player outcome;
9. `RPG_HOLY_RESISTANCE` é um único bucket; A0186/A0187 não podem ser aplicadas por reducers paralelos;
10. low-health gate usa estado PRE-impacto, não HP após o dano.

## Gate dos quatro projetos próprios

Detalhes completos em `guides/projects/21-capability-delta-a0181-a0190.md`.

Resumo:

- RPG Skill Tree: avanço de main durante a auditoria foi hardening/refactor Sonar do parser de investment metadata, sem nova capability do lote; unlock canônico existente deve ser reutilizado.
- Volcanoes `eaddc3232dfc600780769f4a5e7e45ff1e50181c`: sem delta relevante; heat/geothermal não viram NATURE/HOLY.
- Enshrouded `5671114c361be8cbb6fd2dadafdaa05f27d1fe2c`: sem delta relevante para o lote.
- Black Arcana `6b77b5c0ec4f0ff4a8688bb105cef055860c061c`: Stage 06 Rituals tornou-se canônico. É capability real, porém **não deve ser promovida** a NATURE/HOLY damage/Mastery/affinity: o sistema possui engine/ledger/reservas próprios; o bridge Eidolon é anchor-scoped e explicitamente não adivinha caster; Malum fornece componentes transacionais de ritual.

## Matriz obrigatória de testes para Chat 3

### Unavailable nodes

Para A0181/A0182/A0183/A0184/A0185/A0188/A0189/A0190:

- purchase fail-before-spend;
- legacy rank = 0 PP em gates + refund/migration;
- provider absent/version mismatch;
- reload/login/respec mantém fail-closed;
- nenhuma ativação por tema/nome/partícula/provider presence;
- dependency closure correta.

### A0186/A0187

- Iron's HOLY_MAGIC positivo;
- dano magic não-HOLY negativo;
- provider ausente = classifier ausente, sem crash;
- A0186 4/8/12/16%;
- A0187 4/8/12% adicionais somente com condição válida;
- exatamente 50% HP = A0187 false;
- 49,999...% = true conforme representação efetiva do runtime;
- estado PRE-impacto, não pós-impacto;
- A0186 max + A0187 max = 28% local;
- uma aplicação por evento/root;
- nenhuma dupla mitigação por adapter + resolver;
- `holy_heal_window` permanece inexistente sem receipt;
- overheal zero/absorção/regen/cura não-HOLY não armam janela;
- se receipt futuro existir, condições `<50%` e heal-window são OR e nunca somam magnitude.

### Specialist terminals

Quando dependencies deixarem de estar unavailable:

- Gate A/B/C independentes;
- 99 PP falha, 100 PP passa com A/C válidos;
- bridge PP sem dupla contagem;
- respec seguro;
- topologia/UI não libera Specialist.

### Integração final

Chat 3 deve executar quando aplicável: unit tests, GameTests, integração provider-present/provider-absent, NeoForge build, dedicated-server smoke, regressões de lifecycle/multiplayer e CI completo.

## Handoff Chat 2

Chat 2 deve continuar **a mesma branch/PR deste lote** e:

- implementar A0186/A0187 no único `ElementalDamageMitigationResolver`/bucket `RPG_HOLY_RESISTANCE`;
- em A0187 implementar agora somente a rota de vida `<50%`;
- manter `holy_heal_window` fail-closed até receipt canônico aprovado;
- preservar as outras 8 perks como unavailable;
- não criar direct magic producer, thermal parcel, derived damage pipeline, sustain, absorption service ou Specialist resolver local por perk.

Qualquer divergência de API que altere identidade, efeito, provider, gate, dependência, topologia, authority ou semântica essencial volta ao Chat 1.

Chat 1 não executa bateria final de testes, não declara `IMPLEMENTAÇÃO CONFIRMADA` e não faz merge.
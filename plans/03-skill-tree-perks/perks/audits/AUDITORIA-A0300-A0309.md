# AUDITORIA — CHAT 1 — A0300–A0309

Data/freshness: 2026-09-02.

Escopo: **exatamente 10 perks consecutivas, A0300–A0309**.

Responsabilidade: auditoria, design, integração documental e handoff. Nenhum runtime/catálogo executável foi alterado; Chat 1 não realiza merge.

## 1. Determinação do lote

A0181–A0190 já foi fechado pelo Chat 1 na PR #380. A0191–A0199 foi fechado excepcionalmente na PR #383. A0200–A0299 já está mergeado pela PR #346 e essa PR preservou explicitamente A0300 fora do escopo. Busca de branches/PRs não encontrou lote A0300 previamente iniciado. Portanto o primeiro intervalo elegível é **A0300–A0309**.

A0310+ permanece fora do escopo e não deve ser iniciado automaticamente.

## 2. Fontes obrigatórias e modlist

Aplicados integralmente:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- protocolo `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`.

A modlist atual foi conferida na File Library e no catálogo Notion antes da auditoria. O pacote permanece NeoForge 1.21.1 / Java 21. O catálogo Notion possui pequena dívida de reconciliação de estado em alguns registros, mas isso não foi usado como prova de ausência de jars; a auditoria usa presença/versionamento real quando pertinente.

## 3. Notion

- Fetch inicial: **10/10**.
- Páginas corrigidas: **10/10**.
- Re-fetch pós-escrita: **10/10 PASS**.

Correções transversais persistidas:

1. `SPECIALIST_GATE_RESOLVER_V1` legado removido como authority; usar `TreeUnlockResolver`/`TreeUnlockDefinition`/`TreeUnlockCatalog`.
2. `UNAVAILABLE_NODE` transitivo com purchase fail-before-spend.
3. Allocation legado unavailable = 0 PP para gates/thresholds e reembolsável/migrável.
4. Blockers locais mantidos mesmo quando uma dependency externa já fecha o node.
5. Nenhum provider, VFX, namespace ou primitive genérica foi promovido a receipt/classifier sem prova.

## 4. Dependency closure global

### LIGHTNING

A0300 exige `SPECIALIST_UNLOCK:LIGHTNING` e A0299. Gate C do Specialist Lightning é A0176. A0176 permanece `UNAVAILABLE_NODE` transitivamente por A0175. A0299 também exige o mesmo unlock. Logo A0300 é inalcançável no snapshot atual.

### NATURE

A0301–A0309 exigem `SPECIALIST_UNLOCK:NATURE`. Gate C é A0183, que permanece `UNAVAILABLE_NODE` transitivamente por A0182. Dependências internas adicionais ampliam a closure:

- A0302 → A0301;
- A0306/A0307/A0308 → A0304;
- A0309 → A0307.

Resultado operacional: **10/10 `UNAVAILABLE_NODE`**.

Essa conclusão não autoriza reduzir os dossiês a uma única causa upstream: cada blocker local foi preservado para futura reabertura do Specialist.

## 5. Resultado por perk

| Código | Perk | Decisão de design | Estado técnico | Blockers locais além da closure |
|---|---|---|---|---|
| A0300 | Passo do Trovão | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | voluntary movement/dodge receipt, target query, derived outcome, boss/PvP, CHARGED correlation |
| A0301 | Toxicidade | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | poison application identity/owner/pulse attribution, boss classifier |
| A0302 | Virulência | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | mutable precommit poison duration boundary |
| A0303 | Inoculação | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | canonical POISON classifier + shared mitigation lane |
| A0304 | Crescimento | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | healing source/category receipt |
| A0305 | Predador Natural | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | `NATURAL_HOSTILE` + boss/PvP classifier |
| A0306 | Simbiose | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | natural companion classifier + unique owner receipt |
| A0307 | Raiz Profunda | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | authoritative support context + `NATURAL_GROUND` |
| A0308 | Seiva Arcana | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | `NATURAL_TERRITORY` + native positive MANA regen modifier |
| A0309 | Espinhos | APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` | hostile direct-melee receipt + derived outcome + boss/PvP |

## 6. Authority de unlock

A inspeção da `main@5213d068a91c95f45b9e119dec0be0636abc426d` confirmou `TreeUnlockDefinition`, `TreeUnlockResolver` e `TreeUnlockCatalog`. A Stage 04.01 já fornece a projeção de investimento que deve alimentar Gate B.

Portanto:

- nenhum node do lote cria resolver Specialist concorrente;
- Gate A/B/C é avaliado pela pipeline canônica;
- topologia/UI não substitui os gates;
- dependency loss/respec deve reconciliar availability e estado interno.

## 7. Provider coverage — Gameplay/Sistemas

### NeoForge / Minecraft

- `MobEffectEvent.Added`: útil para observar lifecycle/source de efeito; insuficiente para application ledger, pulse attribution ou duration precommit.
- `LivingDamageEvent.Pre`: boundary apropriado para mutação de dano depois de classifier; não prova POISON sozinho.
- `LivingDamageEvent.Post`: primitive para actual damage recebido; não prova sozinho hostilidade/direct-melee/root action.
- `LivingHealEvent`: boundary de amount; não contém categoria/source suficiente para NATURE/REGEN.

### Boss identity

Infraestrutura de boss/reward existe, mas `BossIdentity` não deve ser tratado como classifier universal de qualquer entidade-alvo em combate. Mapping runtime explícito continua necessário onde o coeficiente BOSS alterar resultado.

### Atributos

`AttributeNodeEffectRuntime` já usa modifiers transitórios estáveis/idempotentes. Isso pode servir A0300/A0307 depois que as condições semânticas forem provadas; a primitive não cria as condições.

## 8. Provider coverage — Magia

### Iron's / Ars

Podem originar LIGHTNING e alguns efeitos/cura somente por action/school IDs concretos e adapters versionados. `magic`, tema Nature, VFX elétrico ou nome de spell não bastam.

### Toxony / venenos

Pode ser candidato a produtor POISON, mas cada aplicação precisa de identidade, owner e atribuição de pulse. Presença de efeito Poison não transfere autoria ao jogador.

### Hexalia e outros conteúdos Nature

Tema natural não classifica automaticamente healing ou territory. Apenas outcomes explicitamente classificados `NATURE_HEALING`/`REGEN_HEALING` entram em A0304.

### Recursos

MANA não é Ars Source, Malum Soul/Spirit, FE ou outro recurso. A0308 só modifica taxa nativa positiva/modificável do próprio provider de MANA.

## 9. Provider coverage — fauna/companions

Animal Husbandry/Animal Wellness, tame vanilla, Ars familiars, Iron's summons e outros companions são candidatos somente por adapter explícito que prove simultaneamente:

1. `NATURAL_COMPANION`;
2. owner único = jogador;
3. lifecycle válido.

Tame status, equipe, proximidade, summon visual e namespace não bastam.

## 10. Territory/sublevels

As tags planejadas `natural_hostiles`, `natural_companion`, `natural_ground` e `natural_biomes` não existem na `main` auditada.

Sable/Aeronautics são authorities de transformação/contexto de sublevel, não de semântica Nature. Para A0307/A0308:

- resolver espaço authoritative primeiro;
- não usar bloco/biome do parent Level como aproximação;
- classifier natural deve ser data-driven/allowlisted.

## 11. Tecnologia

Nenhum mod tecnológico é provider positivo do lote.

- FE não é LIGHTNING magic nem MANA;
- Create/Oritech/contraptions não geram movement receipt voluntário para A0300;
- maquinaria/fake player não transfere ownership de poison ou companion;
- tecnologia espacial/sublevel pode fornecer transformação espacial, não semântica do efeito.

## 12. Projetos próprios

O delta completo está em `guides/projects/23-capability-delta-a0300-a0309.md`.

- RPG Skill Tree `5213d068...`: owner/consumer; sem receipts/classifiers faltantes.
- Volcanoes `29835297...`: standalone tombstone; subsystem nativo não cria semântica deste lote.
- Enshrouded `03db9404...`: Stage 08.04 discovery/JourneyMap; `ProgressionOwner` não é ownership genérico de companion.
- Black Arcana `6b77b5c0...`: Stage 06 Rituals; transactional lifecycle não é receipt POISON/NATURE/LIGHTNING.

Nenhum delta abre uma perk A0300–A0309.

## 13. Causalidade, deduplicação e anti-abuso

Regras transversais:

1. uma action/outcome recebe no máximo uma contribuição de cada perk;
2. derived outcomes carregam parent/root identity e não reentram em crítico/proc/Mastery;
3. aplicação de veneno exige identity + owner; pulse sem vínculo não recebe bônus;
4. duration modifier atua antes do commit, nunca por remove/re-add;
5. target-specific modifiers não são reaproveitados para outro alvo;
6. movement imposto/knockback/vehicle/contraption não ativa A0300;
7. companion sem owner único recebe 0 benefício;
8. support/territory ambíguo recebe 0 benefício;
9. A0309 exclui projectile/DoT/environment/self/ally/thorns/derived/zero/fatal;
10. reload/respec/dependency loss limpa ou torna inerte qualquer estado incompatível.

## 14. Fallback e fail-closed

Ausência de capability obrigatória nunca autoriza:

- comprar node como no-op;
- gastar PP e deixar efeito inerte;
- transformar POISON em generic MAGIC/NATURE;
- transformar qualquer heal em Nature;
- inferir fauna/ground/territory por aparência;
- converter outro recurso em MANA;
- usar deslocamento observado como movement receipt;
- usar `hurt()` ad hoc como derived pipeline.

O comportamento correto é `UNAVAILABLE_NODE`/0 contribuição conforme o contrato específico.

## 15. Matriz de testes destinada ao Chat 3

Transversal:

- purchase fail-before-spend para os dez nodes no snapshot atual;
- legacy unavailable = 0 PP em gates/thresholds e respec/migration preservados;
- provider absent/version mismatch;
- dependency loss/reload/login/dimensão;
- multiplayer e dedicated server;
- nenhuma regressão em TreeUnlock/PP/Mastery.

Específicos:

- A0300: voluntary movement, 15t, LOS, coefficients, derived/no cascade, CHARGED commit-only;
- A0301: application owner + pulse attribution + boss coefficient;
- A0302: duration precommit, renewal sem exponencial e pulse cadence preservada;
- A0303: POISON-only mitigation e dedup de reducer;
- A0304: healing categories/source e exclusões food/lifesteal/potion;
- A0305: natural-hostile allowlist, target-specific e boss coefficient;
- A0306: natural companion + unique owner + lifecycle;
- A0307: support surface real/sublevel + modifier cleanup;
- A0308: territory authoritative + native positive MANA regen only;
- A0309: actual damage/survival, direct hostile melee, cap/coeficiente e derived no-cascade.

## 16. Handoff Chat 2

Chat 2 deve continuar **o mesmo branch/PR**.

- Não reauditar/redesenhar o lote.
- Não criar `SPECIALIST_GATE_RESOLVER_V1`.
- Não fabricar adapters ou classifiers por heurística.
- Implementar somente o que o contrato e a API real permitirem.
- Enquanto a closure externa persistir, manter node não comprável e registrar o estado real.
- Divergência de API que altere identity/effect/provider/gate/topologia/authority volta ao Chat 1.

## 17. Estado final Chat 1

Os dez designs estão suficientemente especificados, os registros Notion foram corrigidos e persistidos, os providers e quatro projetos próprios foram dispostos, e os testes futuros estão definidos.

**A0300–A0309 — DESIGN APROVADO / LOTE FECHADO PELO CHAT 1.**

Chat 1 para aqui. Não implementa runtime, não executa a bateria final, não declara `IMPLEMENTAÇÃO CONFIRMADA`, não faz merge e não inicia A0310+.
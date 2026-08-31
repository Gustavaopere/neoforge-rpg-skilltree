# Auditoria Chat 1 — A0111–A0120

**Intervalo:** A0111–A0120, exatamente 10 perks consecutivas.  
**Data:** 2026-08-31.  
**Base:** `main@66fcec7b163320cfb0d79943969aae33f3adf862`.  
**Responsabilidade:** auditoria/design/documentação; nenhum runtime implementado por este Chat 1.

## Fontes obrigatórias

Foram lidos/reconsultados o protocolo Chat 1, `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, os guias completos de Gameplay/Sistemas, Magia, Tecnologia e Projetos Próprios. O Catálogo Mestre do Notion foi consultado diretamente em 10/10 páginas e os quatro projetos próprios tiveram `main` + `plans/STATUS.md` re-fetched.

## Resultado executivo

| Código | Perk | Resultado Chat 1 | Runtime esperado após Chat 1 |
|---|---|---|---|
| A0111 | Conservação de Equipamento II | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` — A0110/P-0036 |
| A0112 | Auto-Manutenção | DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO | `UNAVAILABLE_NODE` — A0111→A0110/P-0036 |
| A0113 | Reforço de Campo | DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO | `UNAVAILABLE_NODE` — A0110/P-0036; identity/repair runtime ausentes |
| A0114 | Manutenção de Relíquia Vinculada | DESIGN APROVADO EM FAIL-CLOSED DUPLO | `UNAVAILABLE_NODE` — cadeia de manutenção + Attunement Socket ausente |
| A0115 | Economia Metabólica: Correr | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE` — P-0037/BodyCostResolver METABOLIC ausente |
| A0116 | Conservação Hídrica: Correr | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE` — P-0037 HYDRATION + TWR adapter ausentes |
| A0117 | Economia Metabólica: Saltar | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE` — A0115/P-0037 |
| A0118 | Conservação Hídrica: Saltar | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE` — A0116/P-0037/TWR |
| A0119 | Economia Metabólica: Nadar | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE` — A0115/P-0037 |
| A0120 | Conservação Hídrica: Nadar | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE` — A0116/P-0037/TWR |

`UNAVAILABLE_NODE` significa purchase falha antes do gasto e allocation legado vale 0 PP para gates. Nenhuma perk recebeu bônus substituto.

## Notion — fetch, correção e persistência

Fetch fresco: **10/10**.

A0111–A0114 já continham provider gate estrutural correto. A0115–A0120 ainda descreviam ausência de P-0037 como “efeito inativo para o evento”, apesar de o resolver ser obrigatório para o único efeito da perk. Isso permitiria rank no-op e contrariaria o unavailable-node invariant.

### Hardening aplicado A0115–A0120

- P-0037/BodyCostResolver ausente ou incompatível => node estruturalmente indisponível/não comprável/0 PP legado.
- A0117/A0119 herdam availability de A0115.
- A0118/A0120 herdam availability de A0116.
- Lanes HYDRATION também exigem adapter versionado Thirst Was Reclaimed `3.0.4`.
- Depois que os bindings existirem, um receipt ausente apenas para uma ação específica pode omitir o proc; isso é diferente de permitir compra sem consumer global.

Re-fetch pós-escrita: **6/6 PASS** em A0115–A0120; persistência confirmada.

## Capability delta dos quatro projetos próprios

Heads frescos são idênticos aos checkpoints do lote anterior:

- RPG Skill Tree `66fcec7b163320cfb0d79943969aae33f3adf862`
- Volcanoes source/provenance `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Enshrouded `29ae2d9b7a13bbdffd3291d2fe4213e0705eb8e3`
- Black Arcana `e89df6dc2c204c269d8f1811c6b3f309644c864a`

Resultado: **SEM DELTA DE CAPABILITY**. A matriz completa está em `guides/projects/17-capability-delta-a0111-a0120.md`.

## Decisões estruturais

### A0111 — conservação técnica

Mesmo seam residual de A0110: prevenção nativa/Unbreaking/provider primeiro; só decremento final confirmado de 1 pode ser cancelado. FE/fuel/pressure/ammo não são durabilidade. Oritech/Protection Pixel só por item concreto + adapter seguro.

### A0112 — manutenção transacional

Um ciclo/player, 200 ticks fora de dano hostil, intervalo 600/480/360. Somente posições ativas; sem inventory scan. Seleção deterministic por menor durability ratio; provider define custo/reparo. Debit-before-repair, zero cooldown em falha.

### A0113 — identidade de ferramenta

`player_uuid + tool_instance_id`; 12 coletas legítimas abrem 600 ticks; próximo reparo da mesma instância recebe +15/+25/+35% sobre quantidade realmente restaurada, com custo integral. Anti-clone e anti-rebuild obrigatórios.

### A0114 — attunement real

Keystone só funciona com Attunement Socket live. Curios/Relics/Artifacts não provam vínculo. Um ciclo/400 ticks, item realmente attuned + ativo + reparável + custo pagável; resource debit antes de repair.

### A0115–A0120 — BodyCostResolver tipado

METABOLIC usa somente parcel positivo FoodData causal da ação. HYDRATION usa receipt provider-owned TWR da mesma action_id, após METABOLIC e antes do debit hídrico. Caps por lane = 30%/evento. Corrida/salto/natação precisam ser autopropelidos e realmente custosos; movimento visual/forçado/passivo não cria custo.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | PASS | availability transitiva completa; todos os blockers atuais resultam em node não comprável |
| 2. Integração global | PASS | durability/repair/BodyCost usam owners canônicos; nenhum pipeline paralelo |
| 3. Qualidade e identidade | PASS | conservação, reparo, reforço, attunement, metabolismo e hidratação são mecanismos distintos |
| 4. Ramificação/distância/topologia | PASS | ENGINEERING/SURVIVAL/LOGISTICS e bridges coerentes |
| 5. Especializações | PASS/N/A | PP só conta quando mapeado semanticamente; nenhum grant paralelo |
| 6. Tradução PT-BR | PASS | nomes/regras/effects mantidos em PT-BR |
| 7. Notion completo | PASS | 10/10 fetch; 6 páginas endurecidas; 6/6 re-fetch |
| 8. Remoção NeoVitae | PASS/N/A | nenhum contrato depende de NeoVitae |
| 9. Cobertura modlist/providers | PASS | Oritech, Protection Pixel, Relics/Artifacts, ParCool, TWR/TWF e projetos próprios classificados |

## Checklist técnico — 18/18

| # | Critério | Resultado |
|---:|---|---|
| 1 | efeito real | PASS — só ativa com seam/receipt real; hoje unavailable onde falta |
| 2 | provider-native first | PASS — durability/repair/hydration owners preservados |
| 3 | sem mecânica inventada disfarçada | PASS — sem FE→durability, Curios→attunement, movement→cost heurístico |
| 4 | fail-closed | PASS — 10/10 nodes indisponíveis no snapshot atual |
| 5 | fallback não muda identidade | PASS — ausência omite/habilita unavailable, nunca troca efeito |
| 6 | Mastery por feitos discretos | N/A — lote não concede Mastery |
| 7 | anti-farm/rebuild | PASS — A0113 bloqueia placed/automation/clone; actions deduplicadas |
| 8 | atribuição causal | PASS — harvest/action_id/player_uuid explícitos |
| 9 | não duplicar pipelines | PASS — owners únicos e lanes tipadas |
| 10 | custos reais | PASS — resource debit provider-native e FoodData/TWR reais |
| 11 | sem geração gratuita | PASS — zero free repair/hydration/energy |
| 12 | read-only correto | PASS/N/A — queries de providers não viram mutação authority |
| 13 | versionamento explícito | PASS — NeoForge 21.1.248; Oritech 1.2.11; Protection Pixel 2.2.1; Relics 0.12.8; Artifacts 13.2.3; Reliquified 1.0.8; TWR 3.0.4; TWF 2.1.5; ParCool 4.0.0.2; Epic ParCool 21.0.0 |
| 14 | coerência estrutural | PASS | ranks/custos/camadas/gates preservados |
| 15 | dependências semânticas | PASS | predecessors indisponíveis propagam availability |
| 16 | sem sobreposição indevida | PASS | METABOLIC ≠ HYDRATION; slot ≠ attunement; FE ≠ durability |
| 17 | implementável posteriormente | PASS | cada dossier fecha hooks/gates/order/authority/pending/tests |
| 18 | verificação pós-escrita | PASS | 6/6 Notion re-fetch após hardening |

## Handoff Chat 2

O Chat 2 deve implementar exatamente os contracts dos dossiês. Prioridade imediata no snapshot atual: materializar availability fail-closed para todos os dez nodes. P-0036/P-0037 ou Attunement só podem ser implementados se código/API real suportar exatamente os boundaries documentados; não usar repair/refund, polling, direct thirst writes, heurística de movimento, Curios-as-attunement ou resource substitution.

Se a API real exigir mudança de identidade/provider/gate/semântica, registrar evidência e devolver ao Chat 1.

## Testes para Chat 3

- purchase fail-before-spend e legacy PP=0 em todos os blockers;
- availability transitiva A0110→A0111→A0112→A0114 e A0115/A0116→A0117–A0120;
- provider absent/version mismatch;
- one-use/action/cycle dedup;
- debit-before-repair e rollback;
- A0113 anti-clone/anti-rebuild;
- METABOLIC/HYDRATION ordering, cap 30% e action identity;
- forced/passive movement exclusions;
- no direct thirst writes/polling/resource substitution;
- lifecycle/respec/rules reload/logout/dimension/multiplayer;
- unit tests, NeoForge GameTests, validators pertinentes, build, JAR e dedicated-server smoke.

## Estado final Chat 1

**DESIGN APROVADO / LOTE A0111–A0120 FECHADO PELO CHAT 1 / AGUARDANDO IMPLEMENTAÇÃO CHAT 2.**

A0121+ não foi iniciado.
# Auditoria Chat 1 — A0111–A0120

**Intervalo:** A0111–A0120, exatamente 10 perks consecutivas.  
**Data:** 2026-08-31.  
**Base:** `main@66fcec7b163320cfb0d79943969aae33f3adf862`.  
**Responsabilidade:** auditoria/design/documentação; nenhum runtime implementado por este Chat 1.

## Fontes obrigatórias

Foram lidos/reconsultados o protocolo Chat 1, `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, os guias completos de Gameplay/Sistemas, Magia, Tecnologia e Projetos Próprios. O Catálogo Mestre do Notion foi consultado diretamente em 10/10 páginas e os quatro projetos próprios tiveram `main` + `plans/STATUS.md` re-fetched.

A autoridade de presença/JAR/versão dos guias atuais confirma Oritech `1.2.11`, Thirst Was Reclaimed `3.0.4`, Thirst Was Fixed `2.1.5`, Relics `0.12.8`, Artifacts `13.2.3`, Reliquified Artifacts `1.0.8`, ParCool `4.0.0.2` e Epic ParCool `21.0.0`. O capítulo tecnológico que ainda menciona Oritech `1.2.10` é histórico e é superado pelo `CURRENT-MODLIST.md`.

## Resultado executivo

| Código | Perk | Resultado Chat 1 | Runtime esperado após Chat 1 |
|---|---|---|---|
| A0111 | Conservação de Equipamento II | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` — A0110/P-0036 |
| A0112 | Auto-Manutenção | DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO | `UNAVAILABLE_NODE` — A0111→A0110/P-0036 |
| A0113 | Reforço de Campo | DESIGN APROVADO APÓS HARDENING ANTI-CLONE V2 | `UNAVAILABLE_NODE` — A0110/P-0036; identity/lease/repair runtime ausentes |
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

### Hardening estrutural A0115–A0120

A0115–A0120 ainda descreviam ausência de P-0037 como “efeito inativo para o evento”, apesar de o resolver ser obrigatório para o único efeito da perk. Isso permitiria rank no-op e contrariaria o unavailable-node invariant.

Foi congelado:

- P-0037/BodyCostResolver ausente ou incompatível => node estruturalmente indisponível/não comprável/0 PP legado;
- A0117/A0119 herdam availability de A0115;
- A0118/A0120 herdam availability de A0116;
- lanes HYDRATION também exigem adapter versionado Thirst Was Reclaimed `3.0.4`;
- depois que os bindings existirem, um receipt ausente apenas para uma ação específica pode omitir o proc; isso é diferente de permitir compra sem consumer global.

### Hardening anti-clone A0113 — V2 após review da PR #341

A versão inicial do hardening ainda prometia preservar a “cópia original” em um conflito de `tool_instance_id`. O review apontou corretamente que duas cópias idênticas do mesmo DataComponent não permitem distinguir qual stack físico era o original.

O contrato foi substituído por uma **linhagem lógica single-writer**:

- DataComponents: `tool_instance_id` + `tool_lease_nonce`;
- ledger: `owner_uuid + tool_instance_id + current_nonce`;
- antes de cada ação A0113, owner e nonce precisam coincidir com a lease vigente;
- após uma ação A0113 aceita, a nonce rotaciona atomicamente e a nova nonce é escrita somente no stack atuante;
- qualquer cópia com nonce anterior fica stale e, ao tentar participar de A0113, recebe novo id/nonce e começa com contador 0 e sem `Reforço Pronto`;
- owner mismatch também reidentifica/reset;
- não existe promessa de reconhecer o stack físico original;
- no máximo uma cópia continua a linhagem prévia, inclusive quando clones não estão simultaneamente carregados.

Páginas distintas alteradas no lote: **7/10** — A0113 e A0115–A0120. Re-fetch pós-escrita das sete páginas: **7/7 PASS**; A0113 recebeu ainda re-fetch específico após o hardening V2 e persistiu corretamente.

## Capability delta dos quatro projetos próprios

Heads frescos são idênticos aos checkpoints do lote anterior:

- RPG Skill Tree `66fcec7b163320cfb0d79943969aae33f3adf862`
- Volcanoes source/provenance `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Enshrouded `29ae2d9b7a13bbdffd3291d2fe4213e0705eb8e3`
- Black Arcana `e89df6dc2c204c269d8f1811c6b3f309644c864a`

Resultado: **SEM DELTA DE CAPABILITY**. A matriz completa está em `guides/projects/17-capability-delta-a0111-a0120.md`.

## Gate global de implementação — AGENTS.md / predecessores

O review da PR #341 também apontou que `AGENTS.md` proíbe expansão de conteúdo antes das fundações mínimas e que A0091–A0110 ainda estão em PRs documentais abertas.

A distinção operacional fica congelada assim:

1. **Chat 1 pode fechar o design** A0111–A0120 porque o protocolo do projeto seleciona o próximo lote pela condição “ainda não fechado pelo Chat 1”, não por merge; Chat 1 é expressamente proibido de fazer merge.
2. **Chat 2 NÃO deve iniciar a implementação A0111–A0120 ainda.** Antes disso, a `main` corrente deve demonstrar, por código/testes/CI, os requisitos mínimos de `AGENTS.md` em “Before expanding content”: baseline reprodutível, correções 1.21.1, save reconciliation/migration seguro, rules snapshot atômico + view server→client, canonical stat runtime, mutation/sync coalescido e dedupe central.
3. Os lotes predecessores **A0091–A0100 (#326) e A0101–A0110 (#340)** devem antes passar pelos seus Chat 2 + Chat 3 e chegar à `main`, porque A0111–A0114 dependem diretamente da cadeia que culmina em A0110.
4. Quando ambos os gates estiverem satisfeitos, o Chat 2 deve reconciliar esta mesma branch/PR #341 com a `main` então corrente e somente depois implementar A0111–A0120.
5. Este gate não autoriza Chat 1 a mergear #326/#340/#341 nem a implementar fundações/runtime neste ciclo.

Assim, o lote fica **fechado no design**, mas o handoff de implementação permanece **BLOQUEADO POR PRÉ-REQUISITOS GLOBAIS/PREDECESSORES** até a evidência acima existir.

## Decisões estruturais

### A0111 — conservação técnica

Mesmo seam residual de A0110: prevenção nativa/Unbreaking/provider primeiro; só decremento final confirmado de 1 pode ser cancelado. FE/fuel/pressure/ammo não são durabilidade. Oritech/Protection Pixel só por item concreto + adapter seguro.

### A0112 — manutenção transacional

Um ciclo/player, 200 ticks fora de dano hostil, intervalo 600/480/360. Somente posições ativas; sem inventory scan. Seleção determinística por menor durability ratio; provider define custo/reparo. Debit-before-repair, zero cooldown em falha.

### A0113 — identidade de ferramenta

`owner_uuid + tool_instance_id + current_nonce`; 12 coletas legítimas com lease válida abrem 600 ticks; próximo reparo com lease vigente recebe +15/+25/+35% sobre quantidade realmente restaurada, com custo integral. A lease rotativa garante uma única continuidade lógica sem alegar distinguir clone físico de original.

### A0114 — attunement real

Keystone só funciona com Attunement Socket live. Curios/Relics/Artifacts não provam vínculo. Um ciclo/400 ticks, item realmente attuned + ativo + reparável + custo pagável; resource debit antes de repair.

### A0115–A0120 — BodyCostResolver tipado

METABOLIC usa somente parcel positivo FoodData causal da ação. HYDRATION usa receipt provider-owned TWR da mesma action_id, após METABOLIC e antes do debit hídrico. Caps por lane = 30%/evento. Corrida/salto/natação precisam ser autopropelidos e realmente custosos; movimento visual/forçado/passivo não cria custo.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | PASS | availability transitiva + gate global de implementação explícitos |
| 2. Integração global | PASS | durability/repair/BodyCost usam owners canônicos; nenhum pipeline paralelo |
| 3. Qualidade e identidade | PASS | conservação, reparo, reforço, attunement, metabolismo e hidratação são mecanismos distintos |
| 4. Ramificação/distância/topologia | PASS | ENGINEERING/SURVIVAL/LOGISTICS e bridges coerentes |
| 5. Especializações | PASS/N/A | PP só conta quando mapeado semanticamente; nenhum grant paralelo |
| 6. Tradução PT-BR | PASS | nomes/regras/effects mantidos em PT-BR |
| 7. Notion completo | PASS | 10/10 fetch; 7 páginas distintas endurecidas; 7/7 re-fetch + A0113 V2 re-fetch |
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
| 7 | anti-farm/rebuild | PASS — A0113 usa lease single-writer e bloqueia placed/automation; actions deduplicadas |
| 8 | atribuição causal | PASS — harvest/action_id/owner explícitos |
| 9 | não duplicar pipelines | PASS — owners únicos e lanes tipadas |
| 10 | custos reais | PASS — resource debit provider-native e FoodData/TWR reais |
| 11 | sem geração gratuita | PASS — zero free repair/hydration/energy |
| 12 | read-only correto | PASS/N/A — queries de providers não viram mutação authority |
| 13 | versionamento explícito | PASS — NeoForge 21.1.248; Oritech 1.2.11; Protection Pixel 2.2.1; Relics 0.12.8; Artifacts 13.2.3; Reliquified 1.0.8; TWR 3.0.4; TWF 2.1.5; ParCool 4.0.0.2; Epic ParCool 21.0.0 |
| 14 | coerência estrutural | PASS | ranks/custos/camadas/gates preservados |
| 15 | dependências semânticas | PASS | predecessors indisponíveis propagam availability; predecessors documentais bloqueiam implementação até integração |
| 16 | sem sobreposição indevida | PASS | METABOLIC ≠ HYDRATION; slot ≠ attunement; FE ≠ durability |
| 17 | implementável posteriormente | PASS | contracts fechados; execução explicitamente diferida até gates globais/predecessores |
| 18 | verificação pós-escrita | PASS | 7/7 páginas distintas + A0113 V2 re-fetch |

## Handoff Chat 2 — DIFERIDO

O contrato dos dez dossiês está fechado, mas **não iniciar runtime de A0111–A0120 enquanto o gate global acima estiver aberto**.

Quando os requisitos de `AGENTS.md` e a integração dos lotes A0091–A0110 estiverem comprovados na `main`, o Chat 2 deve continuar **esta mesma branch/PR #341**, primeiro reconciliá-la com a `main` e então implementar exatamente os contracts aprovados.

P-0036/P-0037 ou Attunement só podem ser implementados se código/API real suportar exatamente os boundaries documentados; não usar repair/refund, polling, direct thirst writes, heurística de movimento, Curios-as-attunement ou resource substitution.

Para A0113, não existe “detecção do original”: implementar a lease single-writer e reidentificar/resetar somente uma cópia que apresente lease stale ou owner incompatível.

Se a API real exigir mudança de identidade/provider/gate/semântica, registrar evidência e devolver ao Chat 1.

## Testes para Chat 3

- gate global/predecessores não pode ser bypassado por implementação antecipada;
- purchase fail-before-spend e legacy PP=0 em todos os blockers;
- availability transitiva A0110→A0111→A0112→A0114 e A0115/A0116→A0117–A0120;
- provider absent/version mismatch;
- one-use/action/cycle dedup;
- debit-before-repair e rollback;
- A0113 lease single-writer: stale nonce, owner mismatch, clone não simultâneo, rotação atômica, uma única continuidade lógica;
- METABOLIC/HYDRATION ordering, cap 30% e action identity;
- forced/passive movement exclusions;
- no direct thirst writes/polling/resource substitution;
- lifecycle/respec/rules reload/logout/dimension/multiplayer;
- unit tests, NeoForge GameTests, validators pertinentes, build, JAR e dedicated-server smoke.

## Estado final Chat 1

**DESIGN APROVADO / LOTE A0111–A0120 FECHADO PELO CHAT 1 / IMPLEMENTAÇÃO DIFERIDA POR GATES GLOBAIS E PREDECESSORES.**

Chat 1 **não faz merge**. A PR #341 permanece aberta; após os gates, o Chat 2 continua nela. O Chat 3 executa validação final/CI/merge. A0121+ não foi iniciado.

# Auditoria Chat 1 — A0101–A0110

**Intervalo fechado:** A0101–A0110, exatamente 10 perks consecutivas.  
**Data:** 2026-08-31.  
**Base de abertura:** `main@2e1c5b62f89d2311eb645882e3547944d0f68869`.  
**Responsabilidade:** auditoria/design/documentação. Nenhum runtime foi implementado por este Chat 1.

## Fontes obrigatórias

Foram reconsultados o protocolo do Chat 1, `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, os guias completos de Gameplay/Sistemas, Magia, Tecnologia e Projetos Próprios, além de `main`/`plans/STATUS.md` frescos dos quatro projetos próprios.

A fonte canônica de cada perk continuou sendo o Catálogo Mestre do Notion, com fetch fresco 10/10.

## Resultado executivo

| Código | Perk | Resultado Chat 1 | Runtime esperado após Chat 1 |
|---|---|---|---|
| A0101 | Fortificação contra Projéteis | DESIGN APROVADO | unavailable até consumer/classifier Chat 2 |
| A0102 | Proteção Arcana | DESIGN APROVADO | unavailable até consumer/classifier; drift Ars fixture pendente |
| A0103 | Proteção Ambiental | DESIGN APROVADO APÓS CORREÇÃO | unavailable até tag/consumer; allowlist congelado |
| A0104 | Segundo Vento | DESIGN APROVADO | unavailable até scheduler/state consumer |
| A0105 | Casca Reativa | DESIGN APROVADO | unavailable até state/attribute lifecycle |
| A0106 | Guarda de Emergência | DESIGN APROVADO | hook comprovado; resolver/state ainda Chat 2 |
| A0107 | Conversão de Impacto | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` — A0093 + P-0035 não canônico |
| A0108 | Pele de Pedra | DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO | `UNAVAILABLE_NODE` — A0100 unavailable |
| A0109 | Fortaleza Ambulante | DESIGN APROVADO EM FAIL-CLOSED DUPLO | `UNAVAILABLE_NODE` — A0108 + body encumbrance ausente |
| A0110 | Conservação de Equipamento I | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE` — P-0036 sem seam seguro |

Nenhuma perk recebeu bônus genérico substituto. `UNAVAILABLE_NODE` significa não comprável e sem gasto de PP.

## Notion — fetch, correção e persistência

Fetch fresco realizado em **10/10** páginas.

Durante a auditoria, A0103 ainda dizia “allowlist auditado” sem enumerar os IDs. Isso obrigaria o Chat 2 a tomar uma decisão de design. Foi a única mutação funcional necessária neste ciclo.

### Correção A0103

Allowlist vanilla inicial congelado:

- `minecraft:cactus`
- `minecraft:sweet_berry_bush`
- `minecraft:stalagmite`
- `minecraft:falling_block`
- `minecraft:falling_anvil`
- `minecraft:falling_stalactite`
- `minecraft:fly_into_wall`

Exclusões explícitas incluem fall/cramming/in-wall, drown/starve/freeze, fogo/calor/lava, lightning, Void/kill/border, explosões e resource costs.

A página foi re-fetched após a escrita e a persistência foi confirmada. As outras nove já apresentavam contrato suficiente no fetch fresco e não foram regravadas desnecessariamente.

## Decisões estruturais por perk

### A0101 — projétil físico

Classifier deve exigir simultaneamente `PROJECTILE + PHYSICAL`; projétil mágico não herda A0101. Uma contribuição/root no `DamageMitigationResolver`. Unknown modded fail-closed.

### A0102 — magia genérica

Owner primário: `neoforge:is_magic`, adapters Iron's/Ars somente por fonte causal explícita. Arcane/Corruption Resistance, school-specific resistance, `ARCANE_BACKLASH` e `BLOOD_MAGIC_COST` não são A0102. O Black Arcana forecast é read-only. A fixture Ars `5.13.0` do repo diverge do canônico `5.13.1`; Chat 2 deve reconciliar API/build, não redesenhar.

### A0103 — ambiente não elemental

Somente o allowlist enumerado é canônico inicialmente. Não usar `source.getEntity()==null`, namespace ou tema. Volcanoes, Enshrouded, Cold Sweat e Thirst mantêm suas authorities.

### A0104 — crossing confirmado

Ativa somente em `LivingDamageEvent.Post` quando `preRatio > .25 && postRatio < .25`; cinco pulsos de 2,4%, roots hostis posteriores cancelam o próximo pulso, uma vez/root. Zero/environment/self/resource não arma.

### A0105 — três hits confirmados

Três eventos hostis diretos confirmados em 80 ticks ativam por 120 ticks +15% Armor relativo e +8% Toughness relativo. O terceiro hit não recebe benefício retroativo. Cooldown 400 ticks, sem refresh.

### A0106 — emergency gate

`LivingDamageEvent.Pre` é boundary real. Ordem fechada: reducers anteriores → reducers RPG tipados/gerais → threshold A0106 → `0.65` → token letal único/clamp a 1 HP. Sem resurrection pós-fato ou prediction de absorption.

### A0107 — conversão impacto→Stamina

Sem taxa universal. Exige quote provider-native versionado e débito/redução atômicos. A0093 unavailable e P-0035 permanece apenas no draft PR #15; node atual indisponível.

### A0108 — tradeoff inseparável

+15% redução física + −8% movement speed somente juntos. A0100 unavailable torna A0108 transitivamente unavailable; generic hooks existentes não bypassam predecessor.

### A0109 — encumbrance corporal real

HEAVY/EXTREME numbers ficam congelados, mas nenhum threshold é inventado. Weight/Create Aeronautics/Sable/Armor/inventário/velocidade não são provider de carga corporal. A0108 + provider ausente bloqueiam o node.

### A0110 — conservação antes do decremento final

Precisa seam após prevenção nativa/Unbreaking e antes do `setDamageValue`/break. NeoForge atual não expõe evento global nesse ponto. `IItemExtension#damageItem`, repair/refund, polling ou ArmorHurtEvent não substituem o boundary. P-0036 permanece bloqueante.

## Nove eixos obrigatórios — matriz do lote

| Eixo | Resultado | Evidência/decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | PASS | predecessors/gateways e availability transitiva definidos; A0107–A0110 não gastam PP indisponíveis |
| 2. Integração global | PASS | um DamageMitigationResolver, healing/attribute/progression pipelines canônicos; sem segundo ledger |
| 3. Qualidade e identidade | PASS | todas possuem identidade mecânica; fail-closed preserva a identidade em vez de trocar por bônus |
| 4. Ramificação/distância/topologia | PASS | VITALITY, bridges ARCANE/SURVIVAL/MARTIAL, Fortaleza e início SURVIVAL coerentes |
| 5. Especializações | PASS/N/A | nenhuma perk cria grant paralelo; gateways continuam ProgressionService/Stage04 |
| 6. Tradução PT-BR | PASS | nomes/efeitos/regras player-facing em PT-BR |
| 7. Notion completo | PASS | 10/10 fetch; A0103 corrigida e re-fetch persistido |
| 8. Remoção total do NeoVitae | PASS/N/A | nenhum contrato do lote depende de NeoVitae |
| 9. Cobertura completa da modlist/integrations | PASS | providers pertinentes auditados; capability delta e matriz bidirecional registradas |

## Checklist técnico consolidado — 18/18

| # | Critério obrigatório | Resultado |
|---:|---|---|
| 1 | O efeito precisa existir de verdade | PASS — hooks reais especificados; o que não existe permanece unavailable |
| 2 | Provider-native first | PASS — magia, Stamina, hazards, body state e durability preservam owners |
| 3 | Sem mecânica inventada disfarçada de integração | PASS — nenhum 1:1 impact/Stamina, encumbrance inventado ou repair pós-fato |
| 4 | Fail-closed | PASS — A0107/A0108/A0109/A0110 explicitamente unavailable; demais nodes não compráveis antes dos consumers |
| 5 | Fallback não muda identidade | PASS — unknown sources são omitidas; nenhum bônus genérico substitui hook ausente |
| 6 | Mastery somente por feitos discretos | PASS/N/A — lote não concede Mastery |
| 7 | Anti-farm e anti-rebuild | PASS/N/A + causal dedup — nenhum loop de construção/throughput; roots/uses deduplicados |
| 8 | Atribuição causal ao jogador | PASS — hostilidade/root/use causal explícitos quando aplicável |
| 9 | Não duplicar pipelines canônicos | PASS — mitigation/healing/attributes/progression únicos |
| 10 | Custos e recursos têm que ser reais | PASS — Stamina A0107 só provider-native; durabilidade A0110 só decremento real |
| 11 | Sem geração gratuita ou duplicação acidental | PASS — healing bounded, modifier uniqueness, no repair/refund/free resources |
| 12 | Read-only realmente read-only | PASS — Black Arcana forecast permanece somente leitura |
| 13 | Versionamento explícito | PASS — NeoForge 21.1.248, Epic Fight 21.17.3.1, Iron's 3.16.3, Ars 5.13.1; drift de fixture registrado |
| 14 | Coerência estrutural da árvore | PASS — função/camada/custo/ranks/power/topologia revisados |
| 15 | Dependências com semântica correta | PASS — blockers A0093/A0100 e gateways não são bypassados |
| 16 | Sem sobreposição indevida | PASS — magia genérica ≠ Arcane Resistance; ambiente ≠ temperatura/pressão/Shroud; weight ≠ body encumbrance |
| 17 | Perk implementável posteriormente | PASS — cada dossiê fecha Hook/Gate/Efeito/Escalonamento/authority/pending/tests sem exigir redesign |
| 18 | Verificação pós-escrita obrigatória | PASS — única mutação Notion A0103 teve re-fetch e persistência confirmada |

## Capability delta e provider → árvore

O suplemento `guides/projects/16-capability-delta-a0101-a0110.md` registra `main` + `plans/STATUS.md` frescos e disposição de toda capability nova/alterada encontrada. Nenhuma capacidade ficou sem classificação.

Resumo:

- RPG Skill Tree: progressão/classes/gateways = **PROGRESSÃO NATIVA AUTORITATIVA**; nenhum novo hook fecha A0107/A0109/A0110.
- Volcanoes: **SEM DELTA**; hazards não entram em A0103 por analogia.
- Enshrouded: Stage07.03 áudio/partículas = **NÃO DEVE SER INTEGRADO** como gameplay.
- Black Arcana: Arcane Resistance forecast = provider próprio **READ-ONLY**; não é reducer A0102.

## Handoff obrigatório ao Chat 2

O Chat 2 deve implementar exatamente os contracts dos dez dossiês, sem reabrir balance/design e sem transformar `UNAVAILABLE_NODE` em bônus substituto. Pontos principais:

- implementar consumers/availability de A0101–A0106 conforme dossiê;
- reconciliar Ars fixture 5.13.0 → design 5.13.1 com evidência de API;
- preservar A0107/A0108/A0109/A0110 fail-closed enquanto blockers reais persistirem;
- não promover draft PR #15, Weight/Create/Sable ou `damageItem` como hooks suficientes;
- atualizar dossiês/auditoria/STATUS com o estado real do código e pendências técnicas.

Se API/código real divergir semanticamente, Chat 2 não inventa solução: registra evidência, aplica fail-closed e devolve redesign ao Chat 1 quando necessário.

## Testes que o Chat 3 deve validar

Além dos testes específicos em cada dossiê:

- availability/purchase fail-before-spend para todos os nodes sem consumer/provider;
- causalidade e one-root/one-use dedup;
- provider present/absent/version mismatch;
- ordering de mitigation A0101/A0102/A0103 → A0106;
- A0104 scheduler/cancelamento/reload;
- A0105 attribute uniqueness/lifecycle;
- A0106 token/cooldown/exclusions/anti-reset;
- A0107 atomic quote/debit caso venha a existir; caso contrário unavailable;
- A0108/A0109 availability transitiva e atomicidade de tradeoffs;
- A0110 ausência de hook falso e, futuramente, ordem pós-Unbreaking/pre-write;
- unit tests, NeoForge GameTests, validators pertinentes, NeoForge build, JAR e dedicated-server smoke.

## Estado final Chat 1

**DESIGN APROVADO / LOTE A0101–A0110 FECHADO PELO CHAT 1 / AGUARDANDO IMPLEMENTAÇÃO CHAT 2.**

A0111+ não foi iniciado.

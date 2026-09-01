# AUDITORIA — CHAT 1 — A0200–A0209

Data: 2026-08-31

Freshness final: 2026-09-01

Escopo: **exatamente 10 perks consecutivas, A0200–A0209**.

Responsabilidade: auditoria/design; nenhum runtime alterado.

## 1. Delimitação adiantada solicitada

O lote começa exatamente em A0200 por ordem do usuário, embora os outros chats ainda estejam trabalhando na faixa dos 100. Esta auditoria:

- não fecha nem pula A0091–A0199;
- não presume implementação de A0144, A0148–A0155, A0198 ou A0199;
- trata todas essas referências como dependencies externas bloqueantes;
- não adiciona uma 11ª perk para “resolver” upstream;
- entrega A0200–A0209 especificadas para implementação futura, com unavailable-node explícito.

Esta é uma exceção localizada à regra permanente 26, autorizada pela ordem específica e posterior do usuário para atuar a partir de A0200. Ela não altera a regra geral: este Chat 1 adiciona apenas documentação/design, não cria catálogo/runtime, não habilita compra, não fecha as Fases 0–4 e não autoriza A0210+. Todos os dez nodes permanecem inertes até o fechamento causal dos blockers.

## 2. Fontes obrigatórias

Foram aplicados os critérios obrigatórios, o protocolo integral do Chat 1, os guias consolidados de Gameplay/Sistemas, Magia e Tecnologia e o guia de Projetos Próprios. A cobertura foi executada nos dois sentidos:

1. perk → providers pertinentes;
2. provider → árvore, incluindo exclusões justificadas.

Provider sem relação causal é N/A; provider pertinente sem API/hook seguro fica fail-closed. Não foram inventados adapters por nome, partículas, tema, namespace, dimensão ou evento genérico.

## 3. Gate de delta dos projetos próprios

O delta canônico está em guides/projects/15-capability-delta-a0200-a0209.md.

- RPG Skill Tree main 54b6cdc1: transações PRE→POST de A0023/A0024/A0029/A0030, infraestrutura de Mastery/specialization/class, consolidação nativa e reconciliação pós-merge do Volcanoes e manutenção de CI foram dispostas capability por capability; nenhuma adiciona classifier/hook Eldritch/Ender do lote.
- Volcanoes standalone main eaddc323: head de source sem avanço; a PR RPG #308 incorporou esse source como subsistema nativo e expôs `NativeVolcanoesServices` read-only. Geologia, atmosfera, pressão e integrações continuam authority Volcanoes e não classificam Eldritch/Ender.
- Enshrouded main 5a25b03a: fog/audio/particles e documentação; apresentação não vira gameplay authority.
- Black Arcana main e89df6dc: integração RPG e forecast são reais, porém Arcane/Corruption Resistance e Backlash continuam contratos próprios; não há BLACK_ARCANA_ELDRITCH_OUTCOME.

Nenhuma capability própria torna qualquer node deste lote comprável.

A matriz registra separadamente estado, decisão, ação, boundary e fail-closed de cada mudança relevante. Em especial, as correções A0023/A0024/A0029/A0030 continuam cobertas pelas perks originais; A0029/A0030 preservam seus blockers provider-native e não foram convertidas em conteúdo A0200+.

## 4. Resultado por perk

| Código | Perk | Decisão de design | Estado técnico auditado | Blocker principal |
|---|---|---|---|---|
| A0200 | Resistência a Eldritch I | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE | hostile ELDRITCH classifier + bucket; A0198 externa |
| A0201 | Resistência a Eldritch II | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE transitivo | dois outcome IDs distintos + transaction; A0200 |
| A0202 | Imbuimento de Eldritch | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE | school lane addon, HealingResolver, direct action/melee hooks; A0198 |
| A0203 | Conhecimento Proibido | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE | 3 categorias reais, HealingResolver, mastery lane e upstream |
| A0204 | Maestria de Eldritch | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE transitivo | A0203, exact lane 80, Specialist closure |
| A0205 | Dano de Ender I | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE | direct ENDER classifier + A0144/A0148–A0155 |
| A0206 | Dano de Ender II | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE | exact lane + causal self-displacement |
| A0207 | Resistência a Ender I | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE | hostile ENDER classifier + bucket; A0205 |
| A0208 | Resistência a Ender II | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE transitivo | self-displacement receipt + hostile classifier |
| A0209 | Imbuimento de Ender | APROVADO EM FAIL-CLOSED | UNAVAILABLE_NODE | ENDER producer + direct melee component; A0205 |

## 5. Notion

- Fetch fresco: **10/10**.
- Páginas mutadas: **10/10**.
- Re-fetch pós-escrita: **10/10 PASS**.

Correções transversais:

1. UNAVAILABLE_NODE/não comprável quando faltar capability obrigatória.
2. Dependencies anteriores ao lote ficam fechadas, não presumidas.
3. “Eldritch Mastery” e “Ender Mastery” viraram school lanes exatas, sem agregação.
4. Lanes melee usam ledgers canônicas, não IDs de gateway.
5. EntityTeleportEvent genérico não prova deslocamento próprio.
6. reservation→commit/rollback para consumos reativos.
7. cleanup em morte, logout, dimensão, rank/dependency loss, respec e rules reload.
8. Black Arcana atual não é classifier ELDRITCH.

## 6. Mastery — finding estrutural

IronsSpellbookProgressionEvents normaliza schools de addons como namespace/path. MasteryLaneCatalog.ironsDiscipline valida atualmente apenas token sem barra. Portanto a main não sustenta com segurança gates de escola addon como Discerning/Deeper Darker/Fire's/Somake.

Decisão:

- não criar uma ledger genérica por tema;
- não remover namespace e arriscar colisão;
- não somar escolas distintas;
- manter A0202/A0203/A0204/A0206 indisponíveis;
- exigir mapping exato, formato canônico aceito, migração e teste.

As lanes melee do lote são epicfight:sword, epicfight:axe, epicfight:spear, epicfight:dagger, epicfight:heavy, combat:mace, combat:scythe e combat:fist condicional.

## 7. Provider coverage — Magia

### Iron's 3.16.3

SchoolType.getId(), cast events e spell identity fornecem superfície real. Não fornecem sozinhos semântica ELDRITCH/ENDER. O adapter deve mapear school/action IDs exatos e preservar owner/action/outcome.

### Discerning The Eldritch 1.4.3 e Deeper and Darker: Spellbooks 1.3.3

Candidatos para A0200–A0204. Como seus school IDs exatos ainda não estão reconciliados com o catálogo e não há classifier/outcome adapter no RPG, permanecem fail-closed.

### Fire's Ender Expansion 2.4.1 e Somake Spells 1.0.8

Candidatos para A0205–A0209. Tema Ender, spell name, teleport ou namespace não bastam. São necessários school/action IDs, direct/hostile outcome adapters e receipt causal para deslocamento.

### Goety, Malum e Eidolon

Podem participar somente de ELDRITCH outcomes concretos mapeados. Curse, spirits, Soul Energy, corruption ou ocultismo não são classifiers automáticos.

### Ars e addons

N/A até adapter versionado da ação concreta. Magic/Void/teleport genéricos não herdam ENDER/ELDRITCH.

## 8. Provider coverage — Gameplay/Sistemas

- Epic Fight 21.17.3.1: classificação melee/categorias reais quando o direct melee outcome estiver disponível.
- Minecraft/NeoForge: damage/teleport events são boundaries auxiliares; não fornecem semântica Eldritch/Ender nem causalidade de deslocamento sozinhos.
- VITALITY/ARCANE/AGILITY/OCCULT: topologia e PP regions; nenhum gateway fabrica backend.
- Summons, fake players, automação, projectiles/ranged e derived components: excluídos conforme cada contrato.
- Void, cold, dimension, End mobs/items e VFX: classificações temáticas insuficientes.

## 9. Provider coverage — Tecnologia

Nenhum mod tecnológico dos guias é provider positivo deste lote.

- máquina/turret/contraption/fake player não transfere autoria direta;
- teleporte tecnológico só poderia armar A0206/A0208/A0209 mediante receipt causal versionado que declare explicitamente a mesma semântica ENDER;
- energia, radiação, pressão e dimensão de máquina não viram Eldritch/Ender.

Resultado atual: N/A/NÃO DEVE SER INTEGRADO.

## 10. Projetos próprios

- Black Arcana: hazard/progression/mastery integration e forecast permanecem Black Arcana-owned; Backlash não dá offensive credit; nenhuma equivalência ELDRITCH.
- Enshrouded: Shroud/Exposure/Madness/fog/audio/particles não são ELDRITCH/ENDER.
- Volcanoes nativo no RPG: `NativeVolcanoesServices` expõe deposits/regions/tectonics/atmosphere/pressure em leitura, sem duplicar simulação; calor/gás/pressão/geologia não são ELDRITCH/ENDER.
- Mobstein/companions: autoria indireta não qualifica.

## 11. Invariantes transversais para Chat 2

1. Nenhuma compra/rank em node estruturalmente indisponível.
2. Availability transitiva acompanha dependency closure.
3. School/mastery IDs são exatos e persistentes.
4. Uma action/outcome recebe no máximo uma aplicação de cada camada.
5. Derived component pertence ao pai e não cria novos créditos.
6. Estados consumíveis usam reservation→commit/rollback.
7. Provider absent/mismatch falha fechado sem desativar o servidor.
8. Client/VFX/presentation nunca é authority.
9. Reload/respec/dependency loss remove estado inválido.
10. Multiplayer e dedicated server são obrigatórios.

## 12. Pendências destinadas ao Chat 2

1. P-A0200-01/-05 — availability, classifier e bucket ELDRITCH.
2. P-A0201-01/-05 — outcomes distintos, Anchor transaction e lifecycle.
3. P-A0202-01/-06 — A0198, addon school lane, HealingResolver, direct action/melee.
4. P-A0203-01/-06 — upstream, categories registry, healing/state hooks.
5. P-A0204-01/-05 — terminal availability, lane80, Specialist Gate A/B/C e respec.
6. P-A0205-01/-05 — upstream A0144/A0148–A0155, direct ENDER classifier/pipeline.
7. P-A0206-01/-05 — exact lane, causal displacement e rupture transaction.
8. P-A0207-01/-04 — transitive availability, hostile classifier e defense bucket.
9. P-A0208-01/-05 — Veil transaction, displacement ordering e hostile classifier.
10. P-A0209-01/-05 — ENDER producer, melee component, lanes e lifecycle.
11. **P-A0200-09-01 BLOQUEANTE TRANSVERSAL:** reconciliar MasteryLaneCatalog com namespace/path de school addon, incluindo migração/testes.
12. **P-A0200-09-TEST-01:** harness/GameTests provider-present/absent, availability, upstream closure, school IDs, mixed components, transaction rollback, teleport provenance, healing, lifecycle, multiplayer e dedicated server.

## 13. Nove eixos / 18 critérios

Todos os dez dossiês registram individualmente os nove eixos. Resultado: **10/10 PASS no design**, usando fail-closed/unavailable-node como estado correto quando a main não prova backend obrigatório.

Nenhuma aprovação decorre apenas de matemática, nome, VFX, namespace, origem dimensional, presence check ou proximidade temática.

## 14. Encerramento do design

O lote A0200–A0209 está especificado sem redesign pendente de regra. A implementação futura depende dos blockers listados e das perks upstream. Nenhum runtime foi alterado por este Chat 1.

O fechamento operacional exige review, CI GREEN, merge e confirmação da main. Após o merge, este Chat 1 deve parar; A0210+ não pode começar automaticamente.

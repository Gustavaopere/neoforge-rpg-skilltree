# 16 — Capability Delta — A0200–A0299

Data da reconciliação: 2026-09-01.

Este suplemento amplia o checkpoint histórico A0200–A0209 para a faixa especial A0200–A0299 autorizada pelo usuário. Ele dispõe toda capability detectada nos quatro projetos próprios antes de fechar o design das 100 perks. Não implementa runtime e não inicia A0300.

## Baselines de entrada e heads frescos

| Projeto | Baseline anterior | Head fresco | Delta |
|---|---|---|---|
| RPG Skill Tree | <code>54b6cdc1de923732c3ec7d99c660f8fdefdb0610</code> | <code>c1597a34787b602e85139d565b9c1e1eb3481cda</code> | somente documentação A0200–A0209 e WIP marker removido; nenhum provider/runtime novo para A0210–A0299 |
| Volcanoes | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | sem delta |
| Enshrouded | <code>5a25b03a23ae81c111bbe1d5c23f85d8abd066ec</code> | <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code> | accessibility profiles client-side e hardening do teste de reload da corrupção; sem gameplay boundary novo para a faixa |
| Black Arcana | <code>e89df6dc2c204c269d8f1811c6b3f309644c864a</code> | <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code> | preflight server-owned read-only, forecast packet, HUD/tooltip e documentação; nenhuma assinatura ELDRITCH/elemental nova |

## Disposição das capabilities detectadas

| Projeto/capability | Estado real | Cobertura/decisão | Fail-closed |
|---|---|---|---|
| RPG — catálogo/implementação A0200–A0209 | somente dossiês; runtime não criado | preservar UNAVAILABLE_NODE já aprovado | nenhum rank/efeito até capabilities e upstream |
| RPG — A0210–A0299 | nenhum runtime encontrado | 100 dossiês congelam contratos; Chat 2 implementará posteriormente | adapter/contract ausente produz zero ou node não comprável |
| RPG — SpecialistGateResolver e ledgers elementais futuros | contratos de design, não APIs comprovadas | 59 perks citam gate V1; convergência usa registry/ledger nomeados | Gate A/B/C e PP regionais não podem ser simulados por topologia |
| Volcanoes — geologia/vulcanismo/atmosfera/pressão | canônico, sem delta | NÃO INTEGRAR como classificador EARTH/FIRE; perks de lava/temperatura seguem owner indicado no dossiê | tema geológico ou calor não cria magia/receipt |
| Enshrouded — accessibility e corruption reload | apresentação/hardening | NÃO INTEGRAR; Shroud/Exposure/ecologia continuam sem relação semântica automática | VFX, fog, áudio e client config geram zero |
| Black Arcana — gate preflight/forecast/HUD | read-only e server-authored | NÃO INTEGRAR como outcome; útil apenas como prova de que forecast não é authority de gameplay | preflight/tooltip não classifica ELDRITCH/FIRE/ICE/LIGHTNING |
| Black Arcana — Arcane Danger/progression/mastery | canônico em seu domínio | A0200–A0204 permanecem sem BLACK_ARCANA_ELDRITCH_OUTCOME; demais elementos também sem bridge aprovada | contribution zero fora de boundary explícito |

## Mapa provider → família

| Família | Providers pertinentes | Projetos próprios |
|---|---|---|
| Eldritch | Iron's/addons, Discerning, Deeper/Darker, Goety/Malum/Eidolon somente por outcome explícito | Black Arcana auditado e recusado como outcome atual |
| Ender | Fire's Ender Expansion, Somake, Iron's; Cold Sweat somente parcel térmico explícito | nenhum projeto próprio publica ENDER |
| Terra | Geomancy, Iron's, Ars Elemental, Somake | Volcanoes recusado como classificador mágico |
| Água | Somake Aqua, Iron's, Ars Elemental | nenhum projeto próprio publica WATER |
| Vento | Aeromancy, Wind's, Iron's; ParCool/Epic Fight só bridge | nenhum projeto próprio publica WIND |
| Convergência | RPG registry/ledger + adapters de todos os providers elementais | RPG é owner; outros três não são authority de diversidade/PP |
| Fogo | Iron's, Ars/Ars Elemental, Somake, Minecraft/Cold Sweat conforme subcontrato | Volcanoes não classifica FIRE; Black Arcana black flame é domínio planejado |
| Gelo | Iron's, Ars/Ars Elemental, Minecraft freeze, Cold Sweat só eixo BODY | nenhum projeto próprio cria ICE/CHILL |
| Relâmpago | Iron's, Ars/Ars Elemental, Epic Fight/ParCool só receipts permitidos | FE/tecnologia e Black Arcana não classificam LIGHTNING |

## Inventário de contratos nomeados

| Contrato | Ocorrências | Estado |
|---|---:|---|
| <code>SPECIALIST_GATE_RESOLVER_V1</code> | 59 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>FIRE_IGNITION_RESOLVER_V1</code> | 11 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>ELEMENT_SIGNATURE_REGISTRY_V1</code> | 10 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>DAMAGE_MITIGATION_RESOLVER_V1</code> | 8 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code> | 8 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code> | 8 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>FIRE_DERIVED_OUTCOME_PIPELINE_V1</code> | 6 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>FREEZE_BUILDUP_ADAPTER_V1</code> | 6 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>CHILL_STATE_REGISTRY_V1</code> | 5 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>CHARGED_STATE_LEDGER_V1</code> | 4 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>FULL_FREEZE_STATE_V1</code> | 4 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>THERMAL_PARCEL_PIPELINE_V1</code> | 4 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>CHILL_APPLICATION_RESOLVER_V1</code> | 3 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>LIGHTNING_CHAIN_QUERY_V1</code> | 3 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>MANA_REGEN_MODIFIER_V1</code> | 3 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>TRANSIENT_ATTRIBUTE_MODIFIER_V1</code> | 3 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>BODY_HEAT_STATE_V1</code> | 2 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>COMBAT_TARGET_QUERY_V1</code> | 2 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>DODGE_AVOID_RECEIPT_V1</code> | 2 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>FULL_FREEZE_TRANSITION_RECEIPT_V1</code> | 2 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>LIGHTNING_CHAIN_DAMAGE_V1</code> | 2 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>TEMPORARY_WORLD_MUTATION_GUARD_V1</code> | 2 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>WET_STATE_V1</code> | 2 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>ABSORPTION_SOURCE_LEDGER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>BODY_COLD_STATE_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>BOSS_CLASSIFIER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>CHILL_DURATION_MODIFIER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>DAMAGE_VULNERABILITY_RESOLVER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>DODGE_CONTROL_MODIFIER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>FREEZE_DECAY_MODIFIER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>FULL_FREEZE_CONSUME_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>FULL_FREEZE_DURATION_MODIFIER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>GROUND_SURFACE_CONTEXT_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>HOSTILE_DAMAGE_RECEIPT_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>LAVA_SWIM_MOVEMENT_BRIDGE_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>LIGHTNING_CHAIN_CONTEXT_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>POSTURE_PRESSURE_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>RESOURCE_COST_MODIFIER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>RESOURCE_DEBIT_RECEIPT_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>ROOT_ACTION_CARDINALITY_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>ROOT_ACTION_TARGET_LEDGER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>SLIPPERY_SURFACE_REGISTRY_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>STAMINA_REGEN_MODIFIER_V1</code> | 1 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |

## Dependências externas e ordem de trabalho

| <code>A0144</code> | <code>A0205</code>, <code>A0212</code>, <code>A0219</code>, <code>A0226</code>, <code>A0233</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0145</code> | <code>A0252</code>, <code>A0272</code>, <code>A0291</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0148</code> | <code>A0205</code>, <code>A0212</code>, <code>A0219</code>, <code>A0226</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0149</code> | <code>A0212</code>, <code>A0219</code>, <code>A0226</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0150</code> | <code>A0212</code>, <code>A0219</code>, <code>A0226</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0151</code> | <code>A0212</code>, <code>A0219</code>, <code>A0226</code>, <code>A0247</code>, <code>A0267</code>, <code>A0290</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0152</code> | <code>A0212</code>, <code>A0219</code>, <code>A0226</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0153</code> | <code>A0212</code>, <code>A0219</code>, <code>A0226</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0154</code> | <code>A0212</code>, <code>A0219</code>, <code>A0226</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0155</code> | <code>A0205</code>, <code>A0212</code>, <code>A0219</code>, <code>A0226</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0157</code> | <code>A0246</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0158</code> | <code>A0250</code>, <code>A0256</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0159</code> | <code>A0250</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0160</code> | <code>A0255</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0161</code> | <code>A0250</code>, <code>A0251</code>, <code>A0259</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0162</code> | <code>A0243</code>, <code>A0246</code>, <code>A0259</code>, <code>A0261</code>, <code>A0262</code>, <code>A0263</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0165</code> | <code>A0268</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0167</code> | <code>A0273</code>, <code>A0275</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0168</code> | <code>A0268</code>, <code>A0279</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0169</code> | <code>A0264</code>, <code>A0265</code>, <code>A0266</code>, <code>A0267</code>, <code>A0268</code>, <code>A0269</code>, <code>A0270</code>, <code>A0271</code>, <code>A0272</code>, <code>A0273</code>, <code>A0274</code>, <code>A0275</code>, <code>A0276</code>, <code>A0277</code>, <code>A0278</code>, <code>A0279</code>, <code>A0280</code>, <code>A0281</code>, <code>A0282</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0172</code> | <code>A0289</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0173</code> | <code>A0289</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0174</code> | <code>A0292</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0176</code> | <code>A0283</code>, <code>A0284</code>, <code>A0285</code>, <code>A0286</code>, <code>A0287</code>, <code>A0288</code>, <code>A0289</code>, <code>A0290</code>, <code>A0291</code>, <code>A0292</code>, <code>A0293</code>, <code>A0294</code>, <code>A0295</code>, <code>A0296</code>, <code>A0297</code>, <code>A0298</code>, <code>A0299</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0198</code> | <code>A0200</code>, <code>A0202</code>, <code>A0203</code>, <code>A0204</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0199</code> | <code>A0203</code>, <code>A0204</code> | Upstream ou referência além do lote; não presumida como concluída. |
| <code>A0300</code> | <code>A0298</code> | Upstream ou referência além do lote; não presumida como concluída. |

A0200–A0299 não fecham nem simulam essas dependencies. O estado dos outros chats fica preservado. A0300 é referência posterior da árvore Specialist e não foi criada.

## Notion e artefatos

- fetch pré-auditoria: 100/100;
- Custo Extra vazio detectado: 100/100;
- update Custo Extra=0: 100/100;
- refetch pós-escrita: 100/100;
- dossiês completos: 100/100;
- runtime alterado: nenhum;
- A0300 criada: não.

## Baselines promovidos

Após esta disposição, os heads abaixo são o novo baseline documental para auditorias posteriores:

- RPG Skill Tree: <code>c1597a34787b602e85139d565b9c1e1eb3481cda</code>
- Volcanoes: <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code>
- Enshrouded: <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code>
- Black Arcana: <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code>

Mudança futura em qualquer head exige novo delta antes de promover disponibilidade runtime.

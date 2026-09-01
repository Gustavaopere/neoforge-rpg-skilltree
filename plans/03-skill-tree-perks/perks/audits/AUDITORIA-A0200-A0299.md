# Auditoria Chat 1 — A0200–A0299

Data: 2026-09-01

## Resultado

A faixa A0200–A0299 foi auditada perk por perk contra o Catálogo Mestre do Notion, os critérios obrigatórios e os guias completos de gameplay, magia, tecnologia e projetos próprios.

- **Faixa:** A0200 até A0299, inclusive.
- **Quantidade:** 100 perks consecutivas.
- **Exceção de ciclo:** o usuário autorizou explicitamente esta entrega especial de 100 perks para o Chat 1.
- **Notion:** 100 páginas individuais buscadas antes da decisão.
- **Correção persistida:** Custo Extra estava vazio em 100/100; foi normalizado para 0 em 100/100.
- **Re-fetch pós-escrita:** 100/100 páginas confirmaram Custo Extra=0; A0210, A0211 e A0218 também foram relidas após a correção dos contratos pós-review.
- **Dossiês individuais:** 100/100 presentes em plans/03-skill-tree-perks/perks.
- **Runtime:** nenhum arquivo de implementação foi alterado; este ciclo é exclusivamente auditoria/design.
- **Limite:** A0300 não foi iniciada. A menção em A0298 identifica somente a sucessora downstream/capstone final, não uma dependência de A0298.
- **Critérios:** cada dossiê contém as 21 propriedades materiais, nove eixos, 18 critérios, providers, quatro projetos próprios, causalidade, recursos, testes, proibições e pendências.

## Classificação operacional

| Estado | Quantidade | Faixa/códigos |
|---|---:|---|
| Design aprovado, UNAVAILABLE_NODE atual | 13 | A0200–A0211 e A0218 |
| Design aprovado com capability/contrato futuro fail-closed | 68 | ver tracker individual |
| Design aprovado, implementação ainda não confirmada | 19 | A0212–A0217, A0219–A0223, A0226–A0230, A0273, A0275 e A0296 |
| Implementação confirmada neste ciclo | 0 | Chat 1 não implementa runtime |

Os 81 registros com bloqueio de capability não foram redesenhados para um bônus genérico. O contrato permanece congelado e a compra/parcela dependente falha fechado até a prova do provider real.

## Cobertura por família

| Família | Intervalo | Perks | Bloqueadas por capability atual | Sem blocker nomeado, mas implementação pendente |
|---|---|---:|---:|---:|
| Eldritch | <code>A0200</code>–<code>A0204</code> | 5 | 5 | 0 |
| Ender | <code>A0205</code>–<code>A0211</code> | 7 | 7 | 0 |
| Terra | <code>A0212</code>–<code>A0218</code> | 7 | 1 | 6 |
| Água | <code>A0219</code>–<code>A0225</code> | 7 | 2 | 5 |
| Vento | <code>A0226</code>–<code>A0232</code> | 7 | 2 | 5 |
| Convergência Elemental | <code>A0233</code>–<code>A0242</code> | 10 | 10 | 0 |
| Especialista Fogo | <code>A0243</code>–<code>A0263</code> | 21 | 21 | 0 |
| Especialista Gelo | <code>A0264</code>–<code>A0282</code> | 19 | 17 | 2 |
| Especialista Relâmpago | <code>A0283</code>–<code>A0299</code> | 17 | 16 | 1 |

## Fontes e freshness

| Fonte | Evidência usada | Decisão |
|---|---|---|
| Catálogo Mestre do Notion | data source collection://ade1ec0c-b055-4b84-8004-45ae80c45119; fetch e refetch individual | autoridade para propriedades canônicas |
| RPG Skill Tree | <code>eed066e418a9968bcfbbd61df32dcfbf2683ca37</code> | owner de perks/gates/ledgers; delta posterior limitado ao workflow Sonar, sem implementação A0210–A0299 |
| Volcanoes | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | sem delta; geologia/vulcanismo/atmosfera não classificam magia EARTH/FIRE |
| Enshrouded | <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code> | delta de accessibility client-side e hardening de reload; não cria provider para a faixa |
| Black Arcana | <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code> | delta de preflight/forecast/HUD read-only; não cria outcome ELDRITCH/elemental |
| Guias anexos | critérios + gameplay + magia + tecnologia + projetos próprios, lidos integralmente | modlist, versões, authority, anti-farm e proibições |

## Dependências fora da faixa

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
A0198/A0199 e as famílias A0144–A0176 são upstream real dos novos ramos. A0300 é sucessora downstream de A0298 e permanece fora do escopo; portanto, não aparece como dependência. Nenhuma referência upstream foi marcada como implementada por este ciclo.

## Contratos/capabilities nomeados

| Contrato | Perks que o citam | Política |
|---|---:|---|
| <code>SPECIALIST_GATE_RESOLVER_V1</code> | 61 | Contrato nomeado; só é considerado disponível após prova no runtime/API e testes da versão exata. |
| <code>ENDER_MASTERY_LANE_V1</code> | 2 | Lane exata <code>rpgskilltree:ender</code>; exige IDs completos/versionados, causalidade e dedup sem alias genérico. |
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

Os nomes acima são especificações de boundary. Eles não são afirmações de que a API já existe. Quando o dossiê usa FUTURE_PROVIDER_CONTRACT ou registra VERSION-STATUS ausente, o node/parcela permanece fail-closed.

## Decisões semânticas por família

- **Eldritch:** outcome explícito; não inferir por curse, Void, Corruption, Strain, Backlash ou estética. Black Arcana não publica BLACK_ARCANA_ELDRITCH_OUTCOME.
- **Ender:** não inferir por teleporte, dimensão End, Void ou frio. Deslocamento exige receipt causal da própria ação.
- **Terra:** não inferir por pedra, mineração, impacto ou worldgen. Volcanoes mantém authority geológica.
- **Água:** não inferir por chuva, submersão, WET ou Cold Sweat. Refund/custo usa o recurso nativo exato.
- **Vento:** não inferir por knockback, voo, queda ou velocidade. ParCool/Epic Fight são bridges, não owners WIND.
- **Convergência:** cada node possui um elemento canônico; shared/bridge PP não é contado duas vezes.
- **Fogo:** FIRE, burn, lava, temperatura corporal e vulcanismo são eixos separados.
- **Gelo:** ICE, CHILL, FULLY_FROZEN, freeze buildup, COLD damage e BODY cold são estados distintos.
- **Relâmpago:** LIGHTNING não equivale a FE/Oritech/Create; WET, CHARGED, chain, dodge, mana e stamina conservam owners próprios.

## Tracker perk por perk

| Código | Nome | Família | Estado do design/runtime | Blockers/dependências relevantes | Arquivo |
|---|---|---|---|---|---|
| <code>A0200</code> | Resistência a Eldritch I | Eldritch | UNAVAILABLE_NODE | <code>A0198</code> | [dossiê](../A0200-resistencia-a-eldritch-i.md) |
| <code>A0201</code> | Resistência a Eldritch II | Eldritch | UNAVAILABLE_NODE | prova do adapter/hook descrito no dossiê | [dossiê](../A0201-resistencia-a-eldritch-ii.md) |
| <code>A0202</code> | Imbuimento de Eldritch | Eldritch | UNAVAILABLE_NODE | <code>A0198</code> | [dossiê](../A0202-imbuimento-de-eldritch.md) |
| <code>A0203</code> | Conhecimento Proibido | Eldritch | UNAVAILABLE_NODE | <code>A0198</code>, <code>A0199</code> | [dossiê](../A0203-conhecimento-proibido.md) |
| <code>A0204</code> | Maestria de Eldritch | Eldritch | UNAVAILABLE_NODE | <code>A0199</code>, <code>A0198</code> | [dossiê](../A0204-maestria-de-eldritch.md) |
| <code>A0205</code> | Dano de Ender I | Ender | UNAVAILABLE_NODE | <code>A0144</code>, <code>A0148</code>, <code>A0155</code> | [dossiê](../A0205-dano-de-ender-i.md) |
| <code>A0206</code> | Dano de Ender II | Ender | UNAVAILABLE_NODE | prova do adapter/hook descrito no dossiê | [dossiê](../A0206-dano-de-ender-ii.md) |
| <code>A0207</code> | Resistência a Ender I | Ender | UNAVAILABLE_NODE | prova do adapter/hook descrito no dossiê | [dossiê](../A0207-resistencia-a-ender-i.md) |
| <code>A0208</code> | Resistência a Ender II | Ender | UNAVAILABLE_NODE | prova do adapter/hook descrito no dossiê | [dossiê](../A0208-resistencia-a-ender-ii.md) |
| <code>A0209</code> | Imbuimento de Ender | Ender | UNAVAILABLE_NODE | prova do adapter/hook descrito no dossiê | [dossiê](../A0209-imbuimento-de-ender.md) |
| <code>A0210</code> | Afinidade de Ender | Ender | UNAVAILABLE_NODE | <code>ENDER_MASTERY_LANE_V1</code> | [dossiê](../A0210-afinidade-de-ender.md) |
| <code>A0211</code> | Maestria de Ender | Ender | UNAVAILABLE_NODE | <code>ENDER_MASTERY_LANE_V1</code>, <code>SPECIALIST_GATE_RESOLVER_V1</code> | [dossiê](../A0211-maestria-de-ender.md) |
| <code>A0212</code> | Dano de Terra I | Terra | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | <code>A0144</code>, <code>A0148</code>, <code>A0149</code>, <code>A0150</code>, <code>A0151</code>, <code>A0152</code>, <code>A0153</code>, <code>A0154</code>, <code>A0155</code> | [dossiê](../A0212-dano-de-terra-i.md) |
| <code>A0213</code> | Dano de Terra II | Terra | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0213-dano-de-terra-ii.md) |
| <code>A0214</code> | Resistência a Terra I | Terra | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0214-resistencia-a-terra-i.md) |
| <code>A0215</code> | Resistência a Terra II | Terra | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0215-resistencia-a-terra-ii.md) |
| <code>A0216</code> | Imbuimento de Terra | Terra | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0216-imbuimento-de-terra.md) |
| <code>A0217</code> | Domínio Geomântico | Terra | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0217-dominio-geomantico.md) |
| <code>A0218</code> | Maestria de Terra | Terra | UNAVAILABLE_NODE | <code>SPECIALIST_GATE_RESOLVER_V1</code> | [dossiê](../A0218-maestria-de-terra.md) |
| <code>A0219</code> | Dano de Água I | Água | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | <code>A0144</code>, <code>A0148</code>, <code>A0149</code>, <code>A0150</code>, <code>A0151</code>, <code>A0152</code>, <code>A0153</code>, <code>A0154</code>, <code>A0155</code> | [dossiê](../A0219-dano-de-agua-i.md) |
| <code>A0220</code> | Dano de Água II | Água | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0220-dano-de-agua-ii.md) |
| <code>A0221</code> | Resistência a Água I | Água | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0221-resistencia-a-agua-i.md) |
| <code>A0222</code> | Resistência a Água II | Água | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0222-resistencia-a-agua-ii.md) |
| <code>A0223</code> | Imbuimento de Água | Água | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0223-imbuimento-de-agua.md) |
| <code>A0224</code> | Domínio Fluido | Água | DESIGN APROVADO / CAPABILITY FUTURA | <code>RESOURCE_DEBIT_RECEIPT_V1</code> | [dossiê](../A0224-dominio-fluido.md) |
| <code>A0225</code> | Maestria de Água | Água | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code> | [dossiê](../A0225-maestria-de-agua.md) |
| <code>A0226</code> | Dano de Vento I | Vento | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | <code>A0144</code>, <code>A0148</code>, <code>A0149</code>, <code>A0150</code>, <code>A0151</code>, <code>A0152</code>, <code>A0153</code>, <code>A0154</code>, <code>A0155</code> | [dossiê](../A0226-dano-de-vento-i.md) |
| <code>A0227</code> | Dano de Vento II | Vento | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0227-dano-de-vento-ii.md) |
| <code>A0228</code> | Resistência a Vento I | Vento | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0228-resistencia-a-vento-i.md) |
| <code>A0229</code> | Resistência a Vento II | Vento | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0229-resistencia-a-vento-ii.md) |
| <code>A0230</code> | Imbuimento de Vento | Vento | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | prova do adapter/hook descrito no dossiê | [dossiê](../A0230-imbuimento-de-vento.md) |
| <code>A0231</code> | Domínio Aerocinético | Vento | DESIGN APROVADO / CAPABILITY FUTURA | <code>RESOURCE_COST_MODIFIER_V1</code> | [dossiê](../A0231-dominio-aerocinetico.md) |
| <code>A0232</code> | Maestria de Vento | Vento | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code> | [dossiê](../A0232-maestria-de-vento.md) |
| <code>A0233</code> | Fundamento Elemental | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENT_SIGNATURE_REGISTRY_V1</code>, <code>A0144</code> | [dossiê](../A0233-fundamento-elemental.md) |
| <code>A0234</code> | Fundamento Defensivo Elemental | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENT_SIGNATURE_REGISTRY_V1</code> | [dossiê](../A0234-fundamento-defensivo-elemental.md) |
| <code>A0235</code> | Ressonância Elemental | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENT_SIGNATURE_REGISTRY_V1</code>, <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code> | [dossiê](../A0235-ressonancia-elemental.md) |
| <code>A0236</code> | Memória Elemental | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENT_SIGNATURE_REGISTRY_V1</code>, <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code> | [dossiê](../A0236-memoria-elemental.md) |
| <code>A0237</code> | Cadeia Prismática | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENT_SIGNATURE_REGISTRY_V1</code>, <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code> | [dossiê](../A0237-cadeia-prismatica.md) |
| <code>A0238</code> | Adaptação Cruzada | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENT_SIGNATURE_REGISTRY_V1</code>, <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code> | [dossiê](../A0238-adaptacao-cruzada.md) |
| <code>A0239</code> | Convergência Ofensiva | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENT_SIGNATURE_REGISTRY_V1</code>, <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code> | [dossiê](../A0239-convergencia-ofensiva.md) |
| <code>A0240</code> | Matriz Adaptativa | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENT_SIGNATURE_REGISTRY_V1</code>, <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code> | [dossiê](../A0240-matriz-adaptativa.md) |
| <code>A0241</code> | Prisma Ofensivo | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code>, <code>ELEMENT_SIGNATURE_REGISTRY_V1</code> | [dossiê](../A0241-prisma-ofensivo.md) |
| <code>A0242</code> | Prisma Defensivo | Convergência Elemental | DESIGN APROVADO / CAPABILITY FUTURA | <code>ELEMENTAL_DIVERSITY_LEDGER_V1</code>, <code>ELEMENT_SIGNATURE_REGISTRY_V1</code> | [dossiê](../A0242-prisma-defensivo.md) |
| <code>A0243</code> | Ignição | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>, <code>A0162</code> | [dossiê](../A0243-ignicao.md) |
| <code>A0244</code> | Combustão | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code> | [dossiê](../A0244-combustao.md) |
| <code>A0245</code> | Queima Persistente | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code> | [dossiê](../A0245-queima-persistente.md) |
| <code>A0246</code> | Calor Crescente | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>THERMAL_PARCEL_PIPELINE_V1</code>, <code>A0157</code>, <code>A0162</code> | [dossiê](../A0246-calor-crescente.md) |
| <code>A0247</code> | Fagulha Crítica | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>, <code>A0151</code> | [dossiê](../A0247-fagulha-critica.md) |
| <code>A0248</code> | Fogo em Alvo Ferido | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code> | [dossiê](../A0248-fogo-em-alvo-ferido.md) |
| <code>A0249</code> | Fogo em Alvo Íntegro | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code> | [dossiê](../A0249-fogo-em-alvo-integro.md) |
| <code>A0250</code> | Resistência ao Calor | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>A0158</code>, <code>A0159</code>, <code>A0161</code> | [dossiê](../A0250-resistencia-ao-calor.md) |
| <code>A0251</code> | Passos Quentes | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>BODY_HEAT_STATE_V1</code>, <code>A0161</code> | [dossiê](../A0251-passos-quentes.md) |
| <code>A0252</code> | Mana Incandescente | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>, <code>MANA_REGEN_MODIFIER_V1</code>, <code>A0145</code> | [dossiê](../A0252-mana-incandescente.md) |
| <code>A0253</code> | Propagação de Chamas | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>, <code>FIRE_DERIVED_OUTCOME_PIPELINE_V1</code> | [dossiê](../A0253-propagacao-de-chamas.md) |
| <code>A0254</code> | Aura de Brasas | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_DERIVED_OUTCOME_PIPELINE_V1</code> | [dossiê](../A0254-aura-de-brasas.md) |
| <code>A0255</code> | Condução Incandescente | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>, <code>FIRE_DERIVED_OUTCOME_PIPELINE_V1</code>, <code>A0160</code> | [dossiê](../A0255-conducao-incandescente.md) |
| <code>A0256</code> | Armadura Incandescente | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>FIRE_DERIVED_OUTCOME_PIPELINE_V1</code>, <code>A0158</code> | [dossiê](../A0256-armadura-incandescente.md) |
| <code>A0257</code> | Marca Carbonizada | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_DERIVED_OUTCOME_PIPELINE_V1</code> | [dossiê](../A0257-marca-carbonizada.md) |
| <code>A0258</code> | Combustão Súbita | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>, <code>FIRE_DERIVED_OUTCOME_PIPELINE_V1</code> | [dossiê](../A0258-combustao-subita.md) |
| <code>A0259</code> | Nascido das Chamas | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>BODY_HEAT_STATE_V1</code>, <code>THERMAL_PARCEL_PIPELINE_V1</code>, <code>A0161</code>, <code>A0162</code> | [dossiê](../A0259-nascido-das-chamas.md) |
| <code>A0260</code> | Nadador de Lava | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>LAVA_SWIM_MOVEMENT_BRIDGE_V1</code> | [dossiê](../A0260-nadador-de-lava.md) |
| <code>A0261</code> | Passos de Obsidiana | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>TEMPORARY_WORLD_MUTATION_GUARD_V1</code>, <code>A0162</code> | [dossiê](../A0261-passos-de-obsidiana.md) |
| <code>A0262</code> | Coração de Magma | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>A0162</code> | [dossiê](../A0262-coracao-de-magma.md) |
| <code>A0263</code> | Inferno Ambulante | Especialista Fogo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>FIRE_IGNITION_RESOLVER_V1</code>, <code>A0162</code> | [dossiê](../A0263-inferno-ambulante.md) |
| <code>A0264</code> | Frio Cortante | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHILL_STATE_REGISTRY_V1</code>, <code>A0169</code> | [dossiê](../A0264-frio-cortante.md) |
| <code>A0265</code> | Geada | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHILL_STATE_REGISTRY_V1</code>, <code>CHILL_APPLICATION_RESOLVER_V1</code>, <code>A0169</code> | [dossiê](../A0265-geada.md) |
| <code>A0266</code> | Congelamento Progressivo | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FREEZE_BUILDUP_ADAPTER_V1</code>, <code>A0169</code> | [dossiê](../A0266-congelamento-progressivo.md) |
| <code>A0267</code> | Gelo Crítico | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FREEZE_BUILDUP_ADAPTER_V1</code>, <code>A0151</code>, <code>A0169</code> | [dossiê](../A0267-gelo-critico.md) |
| <code>A0268</code> | Pele Glacial | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>A0169</code>, <code>A0168</code>, <code>A0165</code> | [dossiê](../A0268-pele-glacial.md) |
| <code>A0269</code> | Passo Seguro no Gelo | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>SLIPPERY_SURFACE_REGISTRY_V1</code>, <code>GROUND_SURFACE_CONTEXT_V1</code>, <code>A0169</code> | [dossiê](../A0269-passo-seguro-no-gelo.md) |
| <code>A0270</code> | Frio Persistente | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHILL_STATE_REGISTRY_V1</code>, <code>CHILL_DURATION_MODIFIER_V1</code>, <code>A0169</code> | [dossiê](../A0270-frio-persistente.md) |
| <code>A0271</code> | Quebra de Gelo | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FULL_FREEZE_STATE_V1</code>, <code>A0169</code> | [dossiê](../A0271-quebra-de-gelo.md) |
| <code>A0272</code> | Mana Frígida | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FULL_FREEZE_STATE_V1</code>, <code>FULL_FREEZE_TRANSITION_RECEIPT_V1</code>, <code>MANA_REGEN_MODIFIER_V1</code>, <code>A0145</code>, <code>A0169</code> | [dossiê](../A0272-mana-frigida.md) |
| <code>A0273</code> | Arma Gélida | Especialista Gelo | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FREEZE_BUILDUP_ADAPTER_V1</code>, <code>A0167</code>, <code>A0169</code> | [dossiê](../A0273-arma-gelida.md) |
| <code>A0274</code> | Retaliação Gélida | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHILL_STATE_REGISTRY_V1</code>, <code>CHILL_APPLICATION_RESOLVER_V1</code>, <code>FREEZE_BUILDUP_ADAPTER_V1</code>, <code>A0169</code> | [dossiê](../A0274-retaliacao-gelida.md) |
| <code>A0275</code> | Congelamento por Sequência | Especialista Gelo | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FREEZE_BUILDUP_ADAPTER_V1</code>, <code>A0169</code>, <code>A0167</code> | [dossiê](../A0275-congelamento-por-sequencia.md) |
| <code>A0276</code> | Estilhaçar | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FULL_FREEZE_STATE_V1</code>, <code>FULL_FREEZE_CONSUME_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>A0169</code> | [dossiê](../A0276-estilhacar.md) |
| <code>A0277</code> | Aura de Geada | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FREEZE_BUILDUP_ADAPTER_V1</code>, <code>CHILL_STATE_REGISTRY_V1</code>, <code>CHILL_APPLICATION_RESOLVER_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>A0169</code> | [dossiê](../A0277-aura-de-geada.md) |
| <code>A0278</code> | Escudo de Gelo | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>ABSORPTION_SOURCE_LEDGER_V1</code>, <code>A0169</code> | [dossiê](../A0278-escudo-de-gelo.md) |
| <code>A0279</code> | Coração Glacial | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>BODY_COLD_STATE_V1</code>, <code>THERMAL_PARCEL_PIPELINE_V1</code>, <code>A0168</code>, <code>A0169</code> | [dossiê](../A0279-coracao-glacial.md) |
| <code>A0280</code> | Caminho Congelante | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>TEMPORARY_WORLD_MUTATION_GUARD_V1</code>, <code>A0169</code> | [dossiê](../A0280-caminho-congelante.md) |
| <code>A0281</code> | Permafrost | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FULL_FREEZE_DURATION_MODIFIER_V1</code>, <code>FREEZE_DECAY_MODIFIER_V1</code>, <code>THERMAL_PARCEL_PIPELINE_V1</code>, <code>A0169</code> | [dossiê](../A0281-permafrost.md) |
| <code>A0282</code> | Zero Absoluto | Especialista Gelo | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>FULL_FREEZE_STATE_V1</code>, <code>FULL_FREEZE_TRANSITION_RECEIPT_V1</code>, <code>DAMAGE_VULNERABILITY_RESOLVER_V1</code>, <code>BOSS_CLASSIFIER_V1</code>, <code>A0169</code> | [dossiê](../A0282-zero-absoluto.md) |
| <code>A0283</code> | Condutividade | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>WET_STATE_V1</code>, <code>A0176</code> | [dossiê](../A0283-condutividade.md) |
| <code>A0284</code> | Carga | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHARGED_STATE_LEDGER_V1</code>, <code>A0176</code> | [dossiê](../A0284-carga.md) |
| <code>A0285</code> | Arco Elétrico | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>LIGHTNING_CHAIN_QUERY_V1</code>, <code>A0176</code> | [dossiê](../A0285-arco-eletrico.md) |
| <code>A0286</code> | Sobrecarga | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>LIGHTNING_CHAIN_DAMAGE_V1</code>, <code>A0176</code> | [dossiê](../A0286-sobrecarga.md) |
| <code>A0287</code> | Impulso Elétrico | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>TRANSIENT_ATTRIBUTE_MODIFIER_V1</code>, <code>A0176</code> | [dossiê](../A0287-impulso-eletrico.md) |
| <code>A0288</code> | Reflexo Carregado | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>DODGE_AVOID_RECEIPT_V1</code>, <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>LIGHTNING_CHAIN_QUERY_V1</code>, <code>A0176</code> | [dossiê](../A0288-reflexo-carregado.md) |
| <code>A0289</code> | Resistência Elétrica | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>A0176</code>, <code>A0172</code>, <code>A0173</code> | [dossiê](../A0289-resistencia-eletrica.md) |
| <code>A0290</code> | Crítico Condutor | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>LIGHTNING_CHAIN_QUERY_V1</code>, <code>A0151</code>, <code>A0176</code> | [dossiê](../A0290-critico-condutor.md) |
| <code>A0291</code> | Mana Estática | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>ROOT_ACTION_TARGET_LEDGER_V1</code>, <code>MANA_REGEN_MODIFIER_V1</code>, <code>A0145</code>, <code>A0176</code> | [dossiê](../A0291-mana-estatica.md) |
| <code>A0292</code> | Arma Carregada | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>A0174</code>, <code>A0176</code> | [dossiê](../A0292-arma-carregada.md) |
| <code>A0293</code> | Raio Ramificado | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>ROOT_ACTION_CARDINALITY_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>A0176</code> | [dossiê](../A0293-raio-ramificado.md) |
| <code>A0294</code> | Sobrecarga Crítica | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHARGED_STATE_LEDGER_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>POSTURE_PRESSURE_V1</code>, <code>A0176</code> | [dossiê](../A0294-sobrecarga-critica.md) |
| <code>A0295</code> | Campo Estático | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>CHARGED_STATE_LEDGER_V1</code>, <code>COMBAT_TARGET_QUERY_V1</code>, <code>A0176</code> | [dossiê](../A0295-campo-estatico.md) |
| <code>A0296</code> | Condução pela Água | Especialista Relâmpago | DESIGN APROVADO / IMPLEMENTAÇÃO PENDENTE | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>WET_STATE_V1</code>, <code>DERIVED_COMBAT_OUTCOME_PIPELINE_V1</code>, <code>COMBAT_TARGET_QUERY_V1</code>, <code>A0176</code> | [dossiê](../A0296-conducao-pela-agua.md) |
| <code>A0297</code> | Passo de Relâmpago | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>DODGE_AVOID_RECEIPT_V1</code>, <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>TRANSIENT_ATTRIBUTE_MODIFIER_V1</code>, <code>CHARGED_STATE_LEDGER_V1</code>, <code>DODGE_CONTROL_MODIFIER_V1</code>, <code>A0176</code> | [dossiê](../A0297-passo-de-relampago.md) |
| <code>A0298</code> | Tempestade Encadeada | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>LIGHTNING_CHAIN_CONTEXT_V1</code>, <code>LIGHTNING_CHAIN_DAMAGE_V1</code>, <code>A0176</code> | [dossiê](../A0298-tempestade-encadeada.md) |
| <code>A0299</code> | Corpo de Tempestade | Especialista Relâmpago | DESIGN APROVADO / CAPABILITY FUTURA | <code>SPECIALIST_GATE_RESOLVER_V1</code>, <code>HOSTILE_DAMAGE_RECEIPT_V1</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>TRANSIENT_ATTRIBUTE_MODIFIER_V1</code>, <code>STAMINA_REGEN_MODIFIER_V1</code>, <code>A0176</code> | [dossiê](../A0299-corpo-de-tempestade.md) |

## Auditoria consolidada dos nove eixos

1. **Dependências/gates:** 100/100 com closure, prerequisitos, gate, availability e upstream/downstream explícitos.
2. **Integração global:** 100/100 com provider/modlist, authority e exclusões.
3. **Qualidade/identidade:** 100/100 preservam a fantasia sem fallback substituto.
4. **Topologia:** 100/100 registram árvore, ramo, camada, função, PP regionais e border hopping.
5. **Especializações:** terminals/fundamentals/Gate A-B-C e refund order preservados quando aplicáveis.
6. **PT-BR:** 100/100.
7. **Notion completo:** 100/100 após Custo Extra=0 e refetch; A0210/A0211/A0218 confirmadas novamente após congelar <code>ENDER_MASTERY_LANE_V1</code>/<code>SPECIALIST_GATE_RESOLVER_V1</code>.
8. **NeoVitae:** 0 referências operacionais.
9. **Providers/projetos próprios:** perk→provider e provider→árvore documentados em 100/100; capabilities ausentes não foram inventadas.

## Auditoria consolidada dos 18 critérios

1. efeito real e mensurável;
2. provider-native first;
3. nenhuma API/mecânica declarada existente sem prova;
4. fail-closed;
5. fallback preserva identidade;
6. Mastery discreta e atribuível;
7. anti-farm/anti-rebuild;
8. causalidade por action/outcome/root/receipt;
9. owner/ledger/bucket único;
10. custo e recursos reais;
11. nenhuma geração gratuita;
12. queries read-only e mutações apenas pelo owner;
13. versões externas e SHAs próprios pinados;
14. coerência estrutural;
15. dependências semânticas;
16. sem overlap/double-dip;
17. implementabilidade do contrato, com capability futura explicitamente bloqueante;
18. refetch pós-escrita comprovado.

O detalhamento e a evidência individual dos 18 critérios estão dentro de cada um dos 100 dossiês; este consolidado não substitui essa leitura.

## Handoff obrigatório para implementação futura

O Chat 2 deverá, para cada perk:

1. confirmar a existência e assinatura real de todos os contracts/adapters;
2. manter node não comprável ou parcela inativa quando faltar capability;
3. não redesenhar o efeito;
4. validar provider present/absent, causalidade, dedup, rollback, lifecycle, multiplayer e dedicated server;
5. devolver ao Chat 1 qualquer contradição entre contrato e API real;
6. preservar os upstream ainda em trabalho por outros chats;
7. não iniciar A0300 como consequência desta entrega.

## Validação documental exigida

- exatamente 100 arquivos A0200–A0299;
- nenhum arquivo A0300;
- títulos/códigos/nomes coincidem com o Notion;
- 21 propriedades materiais por arquivo;
- 16 seções obrigatórias por arquivo;
- nove eixos e 18 critérios individualizados;
- links do Notion e SHAs dos quatro projetos;
- Custo Extra=0;
- nenhuma alteração runtime.

**Decisão final do Chat 1:** A0200–A0299 estão fechadas no design e documentadas individualmente. Implementação não está confirmada. Blocker presente mantém node/parcela fail-closed.

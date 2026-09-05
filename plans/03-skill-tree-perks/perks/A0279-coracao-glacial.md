# A0279 — Coração Glacial

## 1. Estado, origem e decisão

- **Decisão do Chat 1:** DESIGN APROVADO COM BLOQUEIO DE CAPABILITY.
- **Disponibilidade operacional:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Escopo desta entrega:** auditoria e design apenas; nenhum catálogo/runtime, compra, atributo ou integração foi implementado.
- **Fonte canônica:** [registro A0279 no Catálogo Mestre do Notion](https://app.notion.com/3c569db9f0db81e88805d0a7fe04b357).
- **Leitura fresca do registro:** 2026-09-01; página individual buscada antes da auditoria.
- **Persistência verificada:** Custo Extra normalizado para 0 e página individual relida após a escrita.
- **Dependências externas à faixa:** <code>A0168</code>, <code>A0169</code>. Elas permanecem sinalizadas e não são presumidas como concluídas.
- **Identidade preservada:** ICE, CHILL, FULLY_FROZEN, freeze buildup, dano COLD e frio corporal são semânticas distintas e só se relacionam por adapters explícitos.

## 2. Registro canônico completo do catálogo

| Propriedade | Valor persistido |
|---|---|
| Código | A0279 |
| Nome | Coração Glacial |
| Domínio | ARCANE/ICE |
| Árvore | Especialista — Gelo |
| Ramo | Criomancia — Coração Glacial |
| Camada | 6 |
| Função na Árvore | Keystone |
| Tier | Grande |
| Faixa de Poder | Transformativo |
| Ranks Máx. | 1 |
| Custo por Rank | 3 Passive Point(s) |
| Custo Extra | 0 — nenhum custo extra de compra |
| Dependências Obrigatórias | SPECIALIST_UNLOCK:ICE confirmado server-side por Gate A/B/C + A0268 Pele Glacial ≥3 ranks + A0278 Escudo de Gelo + A0168 Afinidade de Gelo + Ice Mastery ≥120. Requisitos locais não substituem fundamentos ICE + ≥100 PP válidos em SPECIALIST_REGION:ICE + terminal A0169. |
| Pré-requisitos | Specialist Gelo desbloqueada (SPECIALIST_UNLOCK:ICE) + A0268 ≥3 + A0278 + A0168 Afinidade de Gelo + Ice Mastery ≥120. |
| Provider/Mods | RPG Skill Tree + pipeline canônica TreeUnlockResolver + TreeUnlockDefinition + Stage 04.01 + FUTURE_PROVIDER_CONTRACT DAMAGE_MITIGATION_RESOLVER_V1 + classificadores ICE/COLD_DAMAGE + Cold Sweat 2.4.2 por contratos independentes: BODY_COLD_STATE_V1 (design P-0054) para eventual ADVERSE_COLD_SEVERE e THERMAL_PARCEL_PIPELINE_V1 (design P-0057) para novos parcels térmicos frios. Iron's/Ars e outros providers ICE só alimentam o bucket ICE quando a ação/dano estiver explicitamente classificada; Cold Sweat ModDamageSources.COLD pode mapear para COLD_DAMAGE, nunca para ICE. VERSION-STATUS: P-0054/P-0057 não foram encontrados como APIs runtime na main auditada em 2026-08-29; gatilho térmico e modificação de parcel permanecem fail-closed até adapter real. |
| Efeito | Ao sofrer dano hostil elegível classificado ICE ou COLD_DAMAGE OU, quando o adapter versionado suportar, ao entrar legitimamente em estado corporal adverso/severo de frio, ativa Coração Glacial por 5 s fora da recarga. Durante a janela, dano ICE e COLD_DAMAGE reconhecidos são reduzidos em 90%, mantendo buckets separados; novos parcels térmicos frios recebem ×0,50 antes de entrar no estado corporal. Temperatura e penalidades já acumuladas permanecem. Recarga base de 60 s. |
| Escalonamento | 1 rank. Janela defensiva: 100 ticks (5 s). ICE hostil: ×0,10. COLD_DAMAGE hostil: ×0,10. Novo deslocamento térmico frio positivo: ×0,50 quando THERMAL_PARCEL_PIPELINE_V1 existir. Recarga completa: 1200 ticks (60 s) iniciada após o fim da janela; nova ativação somente após activation+1300. Sem stacks ou extensão por novos gatilhos durante janela/recarga. |
| Gate | SPECIALIST_UNLOCK:ICE válido + requisitos locais + cooldown livre + (hostile_direct_damage_outcome positivo com componente ICE ou COLD_DAMAGE reconhecido OU transição real para ADVERSE_COLD_SEVERE fornecida por BODY_COLD_STATE_V1). ADVERSE_COLD_SEVERE não pode ser substituído por BODY<=-100, COLD DamageSource, WORLD<FREEZING_POINT, ICE damage, bioma/neve ou comparação inventada. Enquanto P-0054 não comprovar essa semântica, somente o gatilho por dano explícito permanece elegível. Auto-dano, custo, fake player, callback duplicado e permanência contínua em frio não rearmam. |
| Hook | No primeiro gatilho elegível fora da recarga, deduplicar identidade causal e criar RPG_GLACIAL_HEART com expiry_tick=now+100. A recarga de 1200 ticks começa APÓS o fim da janela, logo nova ativação só é possível após activation_tick+1300. No DAMAGE_MITIGATION_RESOLVER_V1, aplicar ×0,10 somente a componentes ICE ou COLD_DAMAGE reconhecidos de outcomes posteriores, preservando buckets separados. No THERMAL_PARCEL_PIPELINE_V1, aplicar ×0,50 somente a novos parcels térmicos frios positivos, antes de entrarem no estado corporal, sem escrever BODY/CORE/WORLD/threshold/resistance/dampening. O gatilho térmico exige exatamente ADVERSE_COLD_SEVERE do adapter versionado. |
| Fallback | FAIL-CLOSED se Specialist Gelo não estiver desbloqueada. Sem ADVERSE_COLD_SEVERE, omitir somente o gatilho térmico. Sem THERMAL_PARCEL_PIPELINE_V1, omitir somente ×0,50 sobre novos parcels. A janela ainda pode ser armada por dano ICE/COLD_DAMAGE explícito quando classificação/mitigação forem seguras. Não conceder imunidade universal, não usar BODY±100 como proxy, não converter ICE↔COLD_DAMAGE e não remover temperatura/penalidades já acumuladas. |
| Regra | COUNTS_AS_SPECIALIST_PERK:ICE=YES. Keystone INTERNA da Specialist Gelo, não terminal exterior A0169. ICE, COLD_DAMAGE, ADVERSE_COLD_SEVERE e novo deslocamento térmico frio são eixos distintos. Janela =100 ticks; cooldown =1200 ticks iniciado ao término. ×0,10 age apenas em ICE/COLD_DAMAGE posteriores; ×0,50 apenas em novo parcel térmico frio via P-0057. Temperatura, fome, exaustão, penalidades ambientais e custos mágicos já existentes não são removidos. Gate A/B/C permanece obrigatório durante ownership/respec. |

As propriedades-formula Árvore Efetiva, Ramo Efetivo, Camada Efetiva, Função Efetiva, Provider Efetivo, Gate Efetivo, Hook Efetivo, Fallback Efetivo, Pré-requisitos Efetivos e Status Estrutural continuam sob autoridade do schema do Notion. Este dossiê não duplica nem falsifica o cálculo dessas fórmulas.

## 3. Contrato final do efeito

### Efeito aprovado

Ao sofrer dano hostil elegível classificado ICE ou COLD_DAMAGE OU, quando o adapter versionado suportar, ao entrar legitimamente em estado corporal adverso/severo de frio, ativa Coração Glacial por 5 s fora da recarga. Durante a janela, dano ICE e COLD_DAMAGE reconhecidos são reduzidos em 90%, mantendo buckets separados; novos parcels térmicos frios recebem ×0,50 antes de entrar no estado corporal. Temperatura e penalidades já acumuladas permanecem. Recarga base de 60 s.

### Escalonamento aprovado

1 rank. Janela defensiva: 100 ticks (5 s). ICE hostil: ×0,10. COLD_DAMAGE hostil: ×0,10. Novo deslocamento térmico frio positivo: ×0,50 quando THERMAL_PARCEL_PIPELINE_V1 existir. Recarga completa: 1200 ticks (60 s) iniciada após o fim da janela; nova ativação somente após activation+1300. Sem stacks ou extensão por novos gatilhos durante janela/recarga.

### Gate de compra/ativação

SPECIALIST_UNLOCK:ICE válido + requisitos locais + cooldown livre + (hostile_direct_damage_outcome positivo com componente ICE ou COLD_DAMAGE reconhecido OU transição real para ADVERSE_COLD_SEVERE fornecida por BODY_COLD_STATE_V1). ADVERSE_COLD_SEVERE não pode ser substituído por BODY<=-100, COLD DamageSource, WORLD<FREEZING_POINT, ICE damage, bioma/neve ou comparação inventada. Enquanto P-0054 não comprovar essa semântica, somente o gatilho por dano explícito permanece elegível. Auto-dano, custo, fake player, callback duplicado e permanência contínua em frio não rearmam.

### Hook e ordem de execução

No primeiro gatilho elegível fora da recarga, deduplicar identidade causal e criar RPG_GLACIAL_HEART com expiry_tick=now+100. A recarga de 1200 ticks começa APÓS o fim da janela, logo nova ativação só é possível após activation_tick+1300. No DAMAGE_MITIGATION_RESOLVER_V1, aplicar ×0,10 somente a componentes ICE ou COLD_DAMAGE reconhecidos de outcomes posteriores, preservando buckets separados. No THERMAL_PARCEL_PIPELINE_V1, aplicar ×0,50 somente a novos parcels térmicos frios positivos, antes de entrarem no estado corporal, sem escrever BODY/CORE/WORLD/threshold/resistance/dampening. O gatilho térmico exige exatamente ADVERSE_COLD_SEVERE do adapter versionado.

### Fallback sem trocar a identidade

FAIL-CLOSED se Specialist Gelo não estiver desbloqueada. Sem ADVERSE_COLD_SEVERE, omitir somente o gatilho térmico. Sem THERMAL_PARCEL_PIPELINE_V1, omitir somente ×0,50 sobre novos parcels. A janela ainda pode ser armada por dano ICE/COLD_DAMAGE explícito quando classificação/mitigação forem seguras. Não conceder imunidade universal, não usar BODY±100 como proxy, não converter ICE↔COLD_DAMAGE e não remover temperatura/penalidades já acumuladas.

### Invariantes semânticos

- ICE, CHILL, FULLY_FROZEN, freeze buildup, dano COLD e frio corporal são semânticas distintas e só se relacionam por adapters explícitos.
- Impede duplicar medidor de congelamento, confundir COLD com ICE ou apropriar Absorption/estado externo.
- A parcela dependente de provider só existe quando o provider e o adapter da versão auditada entregarem a evidência exigida.
- Ausência de hook não autoriza converter a perk em bônus genérico, atributo vanilla, dano físico, resistência genérica ou outro recurso.

## 4. Topologia, dependências e especialização

| Item | Decisão |
|---|---|
| Região | Especialista — Gelo / Criomancia — Coração Glacial |
| Camada e papel | Camada 6; Keystone |
| Pré-requisito visual/estrutural | Specialist Gelo desbloqueada (SPECIALIST_UNLOCK:ICE) + A0268 ≥3 + A0278 + A0168 Afinidade de Gelo + Ice Mastery ≥120. |
| Dependência semântica completa | SPECIALIST_UNLOCK:ICE confirmado server-side por Gate A/B/C + A0268 Pele Glacial ≥3 ranks + A0278 Escudo de Gelo + A0168 Afinidade de Gelo + Ice Mastery ≥120. Requisitos locais não substituem fundamentos ICE + ≥100 PP válidos em SPECIALIST_REGION:ICE + terminal A0169. |
| Custo topológico | 3 PP por rank; 1 rank(s); extra 0 |
| Regra de região/PP | COUNTS_AS_SPECIALIST_PERK:ICE=YES. Keystone INTERNA da Specialist Gelo, não terminal exterior A0169. ICE, COLD_DAMAGE, ADVERSE_COLD_SEVERE e novo deslocamento térmico frio são eixos distintos. Janela =100 ticks; cooldown =1200 ticks iniciado ao término. ×0,10 age apenas em ICE/COLD_DAMAGE posteriores; ×0,50 apenas em novo parcel térmico frio via P-0057. Temperatura, fome, exaustão, penalidades ambientais e custos mágicos já existentes não são removidos. Gate A/B/C permanece obrigatório durante ownership/respec. |
| Border hopping | Proibido contar a mesma compra em regiões incompatíveis ou usar bridge para satisfazer dois thresholds, salvo whitelist explícita de um único lado semântico. |
| Respec | O refund deve respeitar dependency closure, gate de região/terminal e estado owned pela perk; perks internas dependentes são reembolsadas antes de quebrar o gate. |

A topologia não concede a mecânica por si só. Gateway, proximidade visual, atributo secundário ou investimento em bridge não substituem o provider/hook causal.

## 5. Providers, autoridade e boundaries

### Provider/modlist aprovado

RPG Skill Tree + pipeline canônica TreeUnlockResolver + TreeUnlockDefinition + Stage 04.01 + FUTURE_PROVIDER_CONTRACT DAMAGE_MITIGATION_RESOLVER_V1 + classificadores ICE/COLD_DAMAGE + Cold Sweat 2.4.2 por contratos independentes: BODY_COLD_STATE_V1 (design P-0054) para eventual ADVERSE_COLD_SEVERE e THERMAL_PARCEL_PIPELINE_V1 (design P-0057) para novos parcels térmicos frios. Iron's/Ars e outros providers ICE só alimentam o bucket ICE quando a ação/dano estiver explicitamente classificada; Cold Sweat ModDamageSources.COLD pode mapear para COLD_DAMAGE, nunca para ICE. VERSION-STATUS: P-0054/P-0057 não foram encontrados como APIs runtime na main auditada em 2026-08-29; gatilho térmico e modificação de parcel permanecem fail-closed até adapter real.

### Disposição por família

- **Providers/mods pertinentes:** Iron's Spells e Ars Nouveau/Ars Elemental fornecem ações ICE quando mapeadas; Minecraft/NeoForge fornece freeze/Absorption/world state; Cold Sweat só possui o eixo térmico corporal explicitamente contratado.
- **Exclusões obrigatórias:** Slowness, bioma frio, neve, estar congelando, temperatura BODY e aparência de gelo não substituem CHILL/FULLY_FROZEN. Sable/Aeronautics apenas resolvem espaço/sublevel.
- **Contratos/capabilities nomeados no registro:** <code>pipeline canônica TreeUnlockResolver + TreeUnlockDefinition + Stage 04.01</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>BODY_COLD_STATE_V1</code>, <code>THERMAL_PARCEL_PIPELINE_V1</code>.
- **Estado:** nenhum nome de API é tratado como existente apenas por aparecer no design; FUTURE_PROVIDER_CONTRACT permanece bloqueador até prova em código/API da versão exata.

### Matriz dos quatro projetos próprios

| Projeto | Head auditado | Decisão para A0279 | Authority/boundary |
|---|---|---|---|
| RPG Skill Tree | <code>c92304bb22c0c5cec3358a6cc6bc0dbb24cc15c9</code> | OWNER/CONSUMER CANÔNICO | Possui a perk, gates, PP/Mastery e composição RPG; deve delegar ao provider nativo e falhar fechado. |
| Volcanoes | <code>eaddc3232dfc600780769f4a5e7e45ff1e50181c</code> | NÃO INTEGRAR — nenhuma capability do head auditado publica a assinatura/receipt exigida por esta perk. | Nenhuma escrita em geologia, atmosfera, pressão ou worldgen é autorizada. |
| Enshrouded | <code>a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2</code> | NÃO INTEGRAR | Shroud/Exposure, apresentação e ecologia são semânticas próprias; não classificam esta ação/perk. |
| Black Arcana | <code>d069190fedea1f7cb788a2c67e517eed6a9b3729</code> | NÃO INTEGRAR — cast/danger/forecast do head atual não publica a assinatura desta perk; apresentação e domínios planejados não criam provider. | Arcane/Corruption Resistance, Strain, Backlash e forecast permanecem provider-owned e distintos. |

### Contrato obrigatório para qualquer projeto próprio ou mod externo

- **Relação:** produtor de evidência nativa → adapter versionado → consumidor RPG da perk.
- **Estado autoritativo:** permanece no provider dono; o RPG só mantém estado próprio da perk/ledger explicitamente descrito.
- **Boundary:** evento/query/receipt/operação atômica indicada em Hook, nunca leitura de internals nem heurística visual.
- **Evidência causal:** <code>transaction_id/identidade canônica do hook</code>.
- **Deduplicação:** uma aplicação/claim/commit por identidade canônica; callbacks auxiliares não criam um segundo resultado.
- **Fallback:** a parcela dependente é omitida ou o node fica não comprável, conforme o contrato canônico.
- **Escritas proibidas:** nada de escrever diretamente recursos, temperatura, freeze, mundo, progressão, claims, hazard ou estado privado do provider fora da operação pública versionada.

## 6. Causalidade, deduplicação e ordem de composição

- **Chaves que o adapter precisa preservar:** <code>transaction_id/identidade canônica do hook</code>.
- **Produtor:** o provider que confirma a ação/estado/componente nativo descrito em Provider/Mods.
- **Consumidor:** o serviço RPG indicado no Hook; ele aplica somente a contribuição de A0279.
- **Ordem:** classificar e validar pré-condições → obter receipt/estado autoritativo → aplicar a parcela uma única vez no ponto de composição indicado → confirmar commit → só então consumir marca/custo/claim próprio.
- **Rollback:** cancelamento, dano zero, target inválido, provider ausente, falha de commit ou mudança de autoridade descartam a reserva/parcela sem benefício fantasma.
- **Double-dip:** root, hit, projectile, spell callback, derived component, DoT e evento auxiliar não podem contar o mesmo outcome duas vezes.
- **Derived outcomes:** somente entram quando o contrato os allowlistar e preservar parent/root id; caso contrário contribuem zero.

## 7. Custos, recursos e economia

- **Compra:** 3 Passive Point(s) por rank, máximo de 1 rank(s).
- **Custo extra:** 0; não há débito adicional para comprar esta perk.
- **Recursos/eixos tocados pelo contrato:** <code>PARCEL TÉRMICO</code>.
- Qualquer débito, reembolso, regeneração, custo reduzido ou consumo usa o mesmo resource_id, quantum e pipeline do provider.
- Não existe geração gratuita, conversão silenciosa entre MANA/STAMINA/FE/Soul Energy/Source/Spirit, nem crédito baseado em custo nominal quando o débito real falha.
- Ranks e PP não são recurso de combate e não podem ser reembolsados por callbacks de gameplay.

## 8. Fail-closed, lifecycle e perda de capability

- **Decisão atual:** O contrato está congelado e suficientemente especificado para implementação, mas a compra ou parcela dependente deve falhar fechado até todos os contracts/adapters indicados existirem e passarem os testes.
- **Fallback normativo:** FAIL-CLOSED se Specialist Gelo não estiver desbloqueada. Sem ADVERSE_COLD_SEVERE, omitir somente o gatilho térmico. Sem THERMAL_PARCEL_PIPELINE_V1, omitir somente ×0,50 sobre novos parcels. A janela ainda pode ser armada por dano ICE/COLD_DAMAGE explícito quando classificação/mitigação forem seguras. Não conceder imunidade universal, não usar BODY±100 como proxy, não converter ICE↔COLD_DAMAGE e não remover temperatura/penalidades já acumuladas.
- Reavaliar gate/availability em login, load/save, datapack/rules reload, respec, mudança/remoção de provider, alteração de capability e migração de schema.
- Perda de provider ou dependência remove/desativa somente a parcela dependente e executa cleanup do estado próprio, sem tocar estado autoritativo de terceiros.
- Estado desconhecido ou erro de query nunca concede compra, unlock, proteção, dano, recurso, temperatura favorável ou progressão.
- Cliente, HUD, tooltip, forecast e animação são apresentação/read-only; o servidor confirma toda decisão jogável.

## 9. Mastery, anti-farm e anti-rebuild

- Mastery usada em qualquer gate vem apenas de ação discreta, atribuível e confirmada do lane/provider correto.
- Tick, AFK, equipamento vestido, aura passiva, permanência em área, temperatura, mineração/automação, throughput de máquina e callback visual concedem 0 Mastery.
- Um action_id/outcome_id/receipt concede no máximo um crédito no consumidor autorizado; procs derivados não voltam ao produtor de Mastery.
- Rebuild, relog, restart, chunk unload, troca de dimensão e respec não recriam stacks, refunds, one-time rewards ou cooldowns consumidos.
- Dependências/PP são recalculados a partir do estado autoritativo; caches não mantêm unlock órfão.
- Quando a ação não puder ser atribuída inequivocamente ao jogador, a contribuição é zero.

## 10. Integração global e cobertura da modlist

- A busca de providers considera a modlist e os guias completos de gameplay, magia, tecnologia e projetos próprios.
- Iron's Spells e Ars Nouveau/Ars Elemental fornecem ações ICE quando mapeadas; Minecraft/NeoForge fornece freeze/Absorption/world state; Cold Sweat só possui o eixo térmico corporal explicitamente contratado.
- Slowness, bioma frio, neve, estar congelando, temperatura BODY e aparência de gelo não substituem CHILL/FULLY_FROZEN. Sable/Aeronautics apenas resolvem espaço/sublevel.
- Relações somente temáticas foram deliberadamente recusadas; cada provider listado possui papel limitado ao subcontrato descrito.
- Provider não listado não é automaticamente incompatível: ele só entra futuramente após adapter versionado, classificação explícita, authority definida, testes e atualização do catálogo/dossiê.
- NeoVitae não é requisito, provider, fallback nem authority desta perk.

## 11. Plano obrigatório de testes para o Chat 2

1. **Compra válida:** provar ranks, PP, dependencies, mastery/gateway e provider/capability; então comprar exatamente até o máximo.
2. **Compra inválida:** faltar cada requisito isoladamente e comprovar recusa sem gasto, estado ou unlock residual.
3. **Escalonamento:** validar cada rank/coeficiente/TTL/cap exato de 1 rank. Janela defensiva: 100 ticks (5 s). ICE hostil: ×0,10. COLD_DAMAGE hostil: ×0,10. Novo deslocamento térmico frio positivo: ×0,50 quando THERMAL_PARCEL_PIPELINE_V1 existir. Recarga completa: 1200 ticks (60 s) iniciada após o fim da janela; nova ativação somente após activation+1300. Sem stacks ou extensão por novos gatilhos durante janela/recarga..
4. **Provider positivo:** executar uma fonte explicitamente mapeada e confirmar somente o componente/estado correto.
5. **Provider negativo:** mesma temática sem adapter, source diferente, derived não allowlisted ou classifier unknown deve produzir zero.
6. **Causalidade:** correlacionar <code>transaction_id/identidade canônica do hook</code>; callback duplicado, reordenado, cancelado ou target trocado não reaplica.
7. **Composição:** provar ordem com bônus/mitigadores distintos e ausência de bucket/crit/dano/estado duplicado.
8. **Rollback:** cancelar/falhar antes do commit e verificar ausência de custo, consumo, cooldown, stack ou benefício fantasma.
9. **Recursos:** quando aplicável, confirmar débito/regen/refund somente no mesmo recurso e quantum; provider ausente gera zero.
10. **Lifecycle:** save/load, relog, restart, respec, datapack reload e remoção/reentrada do provider.
11. **Exploit:** AFK, tick spam, equipamento, automação, multi-projectile, DoT, summon, rebuild e dupla emissão de evento.
12. **Projetos próprios:** confirmar que Volcanoes, Enshrouded e Black Arcana permanecem fora salvo boundary explicitamente aprovado; ausência não impede o restante independente.
13. **Topologia/Specialist:** validar PP regionais, terminal, fundamentals, bridges e refund order sem border hopping.
14. **Fallback:** remover cada capability indicada e comprovar exatamente o comportamento fail-closed descrito, nunca um bônus substituto.

## 12. Proibições de implementação

- Não inventar evento, capability, classificador, state id, resource id, metric ou API.
- Não duplicar dano, mitigação, resistência, crítico, freeze, temperatura, world mutation, resource ledger ou progressão owned por provider.
- Não usar client/tooltip/forecast/animação como authority.
- Não inferir provider por nome, namespace, dimensão, bioma, bloco, item, cor, VFX ou tema.
- Não trocar fallback por bônus vanilla/genérico parecido.
- Não conceder Mastery/proc/sustain a partir de Backlash, derived outcome não autorizado ou callback intermediário.
- Não escrever diretamente internals de Cold Sweat, Black Arcana, Enshrouded, Volcanoes, claims, recursos mágicos ou estados de outros mods.
- Não adicionar dependência de NeoVitae.

## 13. Pendências técnicas e dependências futuras

- **Implementação:** não confirmada neste trabalho; responsabilidade futura do Chat 2.
- **Capabilities/contracts a provar:** <code>pipeline canônica TreeUnlockResolver + TreeUnlockDefinition + Stage 04.01</code>, <code>DAMAGE_MITIGATION_RESOLVER_V1</code>, <code>BODY_COLD_STATE_V1</code>, <code>THERMAL_PARCEL_PIPELINE_V1</code>.
- **Dependências fora desta faixa:** <code>A0168</code>, <code>A0169</code>.
- **Referências internas posteriores:** nenhuma.
- **Referência além do escopo:** nenhuma além das dependências listadas.
- Estas pendências não autorizam redesign silencioso. Se API/código real contradizer o contrato, implementar fail-closed e devolver o ponto ao Chat 1.

## 14. Auditoria dos nove eixos obrigatórios

| Eixo | Veredito e evidência |
|---|---|
| 1. Dependências e gates | **APROVADO NO DESIGN** — dependências, pré-requisitos, gate, PP/Mastery e comportamento na ausência estão explícitos. |
| 2. Integração global | **APROVADO NO DESIGN** — provider/modlist, authority, projetos próprios e exclusões foram dispostos; Slowness, bioma frio, neve, estar congelando, temperatura BODY e aparência de gelo não substituem CHILL/FULLY_FROZEN. Sable/Aeronautics apenas resolvem espaço/sublevel. |
| 3. Qualidade e identidade | **APROVADO** — ICE, CHILL, FULLY_FROZEN, freeze buildup, dano COLD e frio corporal são semânticas distintas e só se relacionam por adapters explícitos. |
| 4. Topologia/distância/ramificação | **APROVADO** — árvore, ramo, camada, função, região de PP e border-hopping estão documentados. |
| 5. Especializações | **APROVADO** — terminal/fundamentals/PP regionais são preservados quando aplicáveis; nodes externos/bridges não viram perks internas. |
| 6. PT-BR | **APROVADO** — nome, efeito, gate, hook, fallback, testes e proibições estão em PT-BR. |
| 7. Campos completos no Notion | **APROVADO APÓS CORREÇÃO** — 21 propriedades materiais preenchidas; Custo Extra=0 relido após escrita. |
| 8. Ausência de NeoVitae | **APROVADO** — sem dependência, provider, fallback ou referência operacional. |
| 9. Providers e projetos próprios | **APROVADO NO DESIGN / FAIL-CLOSED NO RUNTIME** — cobertura perk→provider e provider→árvore explícita; capability ausente não é inventada. |

## 15. Auditoria dos 18 critérios técnicos

| # | Critério | Veredito |
|---:|---|---|
| 1 | Efeito real | **APROVADO NO DESIGN** — efeito mensurável, coeficiente/rank e alvo de composição definidos. |
| 2 | Provider-native first | **APROVADO** — authority permanece no provider; RPG consome boundary/receipt. |
| 3 | Sem mecânica inventada | **APROVADO** — contratos futuros estão nomeados e bloqueados, não declarados como API existente. |
| 4 | Fail-closed | **APROVADO** — ausência/unknown/erro produz zero ou node não comprável conforme o fallback. |
| 5 | Fallback preserva identidade | **APROVADO** — não converte a perk em bônus genérico ou outro eixo. |
| 6 | Mastery discreta/atribuível | **APROVADO** — somente ação confirmada do lane correto; passividade concede zero. |
| 7 | Anti-farm/anti-rebuild | **APROVADO** — dedup, lifecycle e persistência impedem AFK/relog/rebuild. |
| 8 | Atribuição causal | **APROVADO** — chaves/receipt exigidos: <code>transaction_id/identidade canônica do hook</code>. |
| 9 | Sem pipelines duplicados | **APROVADO** — um owner, um bucket/ledger/commit e composição única. |
| 10 | Custos/recursos reais | **APROVADO** — PP e recursos nativos usam débito/quantum/provider reais. |
| 11 | Sem geração gratuita | **APROVADO/NÃO APLICÁVEL** — não há recurso grátis; ganho/refund eventual exige receipt e mesmo resource_id. |
| 12 | Read-only verdadeiro | **APROVADO/NÃO APLICÁVEL** — queries/forecast são read-only; mutações só pela operação pública do owner. |
| 13 | Versões exatas | **APROVADO NO DESIGN** — versões externas permanecem no campo Provider/Mods; projetos próprios estão pinados por SHA. |
| 14 | Coerência estrutural | **APROVADO** — domínio, árvore, ramo, camada, função e custo são coerentes. |
| 15 | Dependências semânticas | **APROVADO** — closure e upstream/future refs estão explícitos e não presumidos. |
| 16 | Sem sobreposição/double-dip | **APROVADO** — Impede duplicar medidor de congelamento, confundir COLD com ICE ou apropriar Absorption/estado externo. |
| 17 | Implementável | **APROVADO COMO CONTRATO** — hook, estado, owner, fallback e testes estão fechados; capability futura bloqueia runtime sem bloquear o design. |
| 18 | Pós-escrita relido | **APROVADO** — registro individual foi relido após Custo Extra=0; nenhum sucesso foi presumido. |

## 16. Evidência de persistência no Notion

- Página: [A0279 — Coração Glacial](https://app.notion.com/3c569db9f0db81e88805d0a7fe04b357)
- Data source: collection://ade1ec0c-b055-4b84-8004-45ae80c45119
- Operação material desta auditoria: Custo Extra, vazio para 0.
- Verificação: fetch individual pós-escrita em 2026-09-01 confirmou Custo Extra=0.
- Os demais valores materiais desta página são transcritos integralmente na seção 2.
- A página não possui corpo editorial; a autoridade é o conjunto de propriedades do catálogo e suas fórmulas.

---

**Resultado final do Chat 1 para A0279:** design suficientemente especificado para implementação sem redesign; qualquer capability/dependência ausente mantém a perk ou sua parcela dependente fail-closed.
